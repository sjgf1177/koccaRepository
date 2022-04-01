
package com.credu.library;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * <p>제목: Mail 관련 라이브러리</p>
 * <p>설명: JavaMail 라이브러리</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author 이정한
 *@date 2003. 12
 *@version 1.0
 */
public class Mailing {
	private boolean success = false;
	private String v_fromName;
	private String v_toEmail;

	/**
	 * JavaMail API 를 이용하여 메일을 발송한다.
    @param  p_mailServer  메일서버 도메인명
    @param  p_fromEmail  메일발송자 이메일
    @param  p_fromName  메일발송자 성명
    @param  p_toEmail  메일수신자 이메일
    @param  p_mailTitle  메일 제목
    @param  p_mailContent  Html 형식의 메일 내용
    @return  success   메일전송 성공여부
	 */
	public boolean send(String p_mailServer, String p_fromEmail, String p_fromName, String p_toEmail, String p_mailTitle, String p_mailContent) throws Exception {
		//    boolean success = false;
		v_fromName = StringManager.engEncode(p_fromName);
		v_toEmail = p_toEmail;

		//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		Properties props = new Properties();
		props.put("mail.smtp.host",p_mailServer);
		//props.put("mail.smtp.host","hmc.co.kr");

		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(true);

		try {
			final MimeMessage msg = new MimeMessage(session);

			MimeBodyPart mbp = new MimeBodyPart();

			Multipart mp = new MimeMultipart();

			try{
				msg.setFrom(new InternetAddress(p_fromEmail, v_fromName, "euc-kr"));
			}catch(UnsupportedEncodingException ex) {
				Log.sys.println(this, ex, "Happen to MimeMessage.setFrom()");
			}

			InternetAddress[] address = {new InternetAddress(v_toEmail)};
			msg.setRecipients(Message.RecipientType.TO, address);

			msg.setSubject(p_mailTitle, "euc-kr");
			msg.setHeader("Content-Type","text/html;charset=euc-kr");

			mbp.setDataHandler(new DataHandler(new ByteArrayDataSource(p_mailContent, "text/html; charset=euc-kr")));

			msg.setHeader("FROM","\"" + v_fromName + "\"<" + p_fromEmail + ">");

			mp.addBodyPart(mbp);

			msg.setContent(mp);

			//            Runnable r = new Runnable (){
			//                public void run() {
			//                    try {
			System.out.println("Happen to Transport.send(), from= " + v_fromName + " to= " + v_toEmail + " msg =" + msg.toString());
			Transport.send(msg);
			success = true;
			//                    }
			//                    catch(Exception ex) {
			//                        success = false;
			//                        Log.sys.println(this, ex, "Happen to Transport.send(), from " + v_fromName + " to " + v_toEmail);
			//                    }
			//                }
			//            };
			//            Thread t = new Thread(r);
			//            t.start();
			//            t.join(5000);         //     현재 하나의 메일발송시간이 5초가 지나면 발송에 실패한것으로 return 된다.
			//   메일발송시 웹서버(JSP view)에서의 응답시간을 빠를게 하기위해서는 join method 를 사용하지않는다.
		} catch(Exception ex) {
			success = false;
			Log.sys.println(this, ex, "Happen to Mailing.send(), from " + v_fromName + " to " + v_toEmail);
		}
		return success;
	}
}


