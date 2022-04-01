
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
 * <p>����: Mail ���� ���̺귯��</p>
 * <p>����: JavaMail ���̺귯��</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author ������
 *@date 2003. 12
 *@version 1.0
 */
public class Mailing {
	private boolean success = false;
	private String v_fromName;
	private String v_toEmail;

	/**
	 * JavaMail API �� �̿��Ͽ� ������ �߼��Ѵ�.
    @param  p_mailServer  ���ϼ��� �����θ�
    @param  p_fromEmail  ���Ϲ߼��� �̸���
    @param  p_fromName  ���Ϲ߼��� ����
    @param  p_toEmail  ���ϼ����� �̸���
    @param  p_mailTitle  ���� ����
    @param  p_mailContent  Html ������ ���� ����
    @return  success   �������� ��������
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
			//            t.join(5000);         //     ���� �ϳ��� ���Ϲ߼۽ð��� 5�ʰ� ������ �߼ۿ� �����Ѱ����� return �ȴ�.
			//   ���Ϲ߼۽� ������(JSP view)������ ����ð��� ������ �ϱ����ؼ��� join method �� ��������ʴ´�.
		} catch(Exception ex) {
			success = false;
			Log.sys.println(this, ex, "Happen to Mailing.send(), from " + v_fromName + " to " + v_toEmail);
		}
		return success;
	}
}


