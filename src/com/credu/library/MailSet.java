
package com.credu.library;

import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * <p>����: Mail ���� ���̺귯��</p>
 * <p>����: ����ȯ�漼��</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author ������
 *@date 2003. 12
 *@version 1.0
 */
public class MailSet {
	private ConfigSet conf;
	//    private String v_host_ip;
	private String v_mailServer;
	private boolean v_singleSender;
	private String v_fromEmail;
	private String v_fromName;
	private String v_comptel;
	private String v_fromCono;
	private Mailing domail;
	private SmsBean sms;
	//    private String v_tempPath;
	//    private MemoBean memo;
	//    private int sendOk = 0;
	//    private int sendFail = 0;
	//    private LogMailWriter mail = Log.mail;

	/**
	 * ����ȯ����� ������ �Ѵ�
    @param  box  �߿� session ������ ��´�.
	 */
	public MailSet(RequestBox box) throws Exception{
		try{
			conf = new ConfigSet();

			v_mailServer = conf.getProperty("mail.server");
			//v_singleSender = conf.getBoolean("mail.admin.singlesender");
			//            v_tempPath = conf.getProperty("mail.hhioffice.temp");

			v_singleSender = false;

			//Box�� �߽��� �����ּҰ� ������ �����
			if (!box.getStringDefault("p_fromEmail","").equals("") ) {
				v_fromEmail = box.getString("p_fromEmail");
				v_fromName = box.getStringDefault("p_fromName","");
				v_comptel = box.getStringDefault("p_comptel","");
			} else {

				if(v_singleSender) {       //      ������ ��� �̸���, ����, ȸ����ȭ��ȣ�� �����ϰ� �Ҷ�
					v_fromEmail = conf.getProperty("mail.admin.email");
					v_fromName = conf.getProperty("mail.admin.name");
					v_comptel = conf.getProperty("mail.admin.comptel");
				}
				else {
					v_fromEmail = box.getSession("email");
					v_fromName = box.getSession("name");
					v_comptel = box.getSession("comptel");
				}
			}
			v_fromCono = box.getSession("userid"); // ����� �� userid �� ��ü
			//v_fromCono = "lee1";     //  test

			domail = new Mailing();
			sms = new SmsBean();
			//            memo = new MemoBean();     //      ���� �߼�

		}
		catch(Exception ex) {
			Log.sys.println(this, ex, "Happen to MailSet(RequestBox box)");
		}
	}

	/**
	 * ��� ������ ���� �Ѵ�
    @param  fmail  FormMail �� ������ �߼��ϴ� ��� ������ �����Ѵ�.
	 */
	public void setSender(FormMail fmail) throws Exception {
		fmail.setVariable("fromname", v_fromName);
		fmail.setVariable("fromemail", v_fromEmail);
		fmail.setVariable("comptel", v_comptel);
	}

	/**
	 * ���Ϲ߼�
    @param  p_toCono  ���Ϲ߼��� ���
    @param  p_toEmail  �н��� �̸���
    @param  p_mailTitle  ���� ����
    @param  p_mailContent  ���� ����
    @param  p_isMailing  ���� ����
    @param  p_sendHtml  Html ���ϸ�
    @return  isMailed   �������� ��������
	 */
	public boolean sendMail(String p_toCono, String p_toEmail, String p_mailTitle, String p_mailContent, String p_isMailing, String p_sendHtml) throws Exception {
		boolean isMailed = false;
		//        int isEnd = 0;

		try{
			if(p_isMailing.equals("1")) {      //      �Ϲ� ���Ϸ� �ޱ� ���Ұ��
				isMailed = domail.send(v_mailServer, v_fromEmail, v_fromName, p_toEmail, p_mailTitle, p_mailContent);  //  ����IP, from �̸���,
			}
			else if(p_isMailing.equals("2")) {      //       SMS ���Ϲ߼�

				isMailed = sms.sendSMSMsg(p_toCono,p_toEmail,p_mailTitle);

			}
			else if(p_isMailing.equals("3")) {    //      ������ �ޱ� ���Ұ��
				//isMailed = memo.insertMemoByMail(v_fromCono, p_toCono, p_mailTitle, p_mailContent);    //  ������ �־�ߵȴ�
				//isMailed = memo.insertMemoByMail(p_toCono, p_toEmail, p_mailTitle, p_mailContent);    //  ������ �־�ߵȴ�
				//                isMailed = memo.insertMemoByMail(p_toCono, p_toEmail, p_mailTitle, p_mailContent);    //  ������ �־�ߵȴ�
			}
		}catch(Exception ex) {
			isMailed = false;
			Log.sys.println(this, ex, "Happen to MailSet.sendMail(), from " + v_fromCono + " to " + p_toCono);
		}
		return isMailed;
	}




	@SuppressWarnings("unchecked")
	public int insertMailData(ArrayList list) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		ListSet ls = null;

		String sql = "";
		String sql1 = "";
		String maxseq = "";
		int isOk = 0;
		int a = 0;

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			////seq ����
			sql1  =" select NVL(ltrim(rtrim(to_char(to_number(max(seq))+1,'0000000'))),'0000001') MSTCD " ;
			sql1 += "From tz_humantouch ";

			ls = connMgr.executeQuery(sql1);
			if(ls.next()){
				maxseq = ls.getString("MSTCD");
			}
			else{
				maxseq = "0000001";
			}

			sql =  "insert into tz_humantouch(subj, year, subjseq, userid, touch, seq, ismail, edustart, eduend, title, sdesc, isok, reason, ismailopen, luserid, ldate)";
			sql += " values (?, ?, ?, ?, ?, '"+maxseq+"', ?, ?, ?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

			pstmt = connMgr.prepareStatement(sql);

			for(int i = 0; i < list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);

				pstmt.setString(1,  dbox.getString("d_subj"));
				pstmt.setString(2,  dbox.getString("d_year"));
				pstmt.setString(3,  dbox.getString("d_subjseq"));
				pstmt.setString(4,  dbox.getString("d_userid"));
				pstmt.setString(5,  dbox.getString("d_touch"));
				pstmt.setString(6,  dbox.getString("d_ismail"));
				pstmt.setString(7,  dbox.getString("d_edustart"));
				pstmt.setString(8,  dbox.getString("d_eduend"));
				pstmt.setString(9,  dbox.getString("d_title"));
				pstmt.setString(10, dbox.getString("d_sdesc"));
				pstmt.setString(11, dbox.getString("d_isok"));
				pstmt.setString(12, dbox.getString("d_reason"));
				pstmt.setString(13, dbox.getString("d_ismailopen"));
				pstmt.setString(14, v_fromCono);
				pstmt.setString(15, dbox.getString("d_subjnm"));
				pstmt.setString(16, dbox.getString("d_seqgrnm"));

				a += pstmt.executeUpdate();     System.out.println(a);
			}
			if(list.size() == a) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
				isOk = 1;
			}
			else {
				if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			}
		}
		catch (Exception ex) {         ex.printStackTrace();
		if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
		ErrorManager.getErrorStackTrace(ex);
		throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}


	public int insertHumanTouch(DataBox dbox) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		String sql = "";
		String sql1 = "";
		int isOk = 0;
		//        int a = 0;
		String seq = "";

		ListSet ls1 = null;

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);


			sql1  =" select NVL(ltrim(rtrim(to_char(to_number(max(SEQ))+1,'0000000'))),'0000001') MSTCD " ;
			sql1 += "From TZ_HUMANTOUCH ";

			ls1 = connMgr.executeQuery(sql1);
			if(ls1.next()){
				seq = ls1.getString("MSTCD");
			}
			else{
				seq = "0000001";
			}


			sql =  "insert into tz_humantouch(subj, year, subjseq, userid, touch, seq, ismail, edustart, eduend, title, sdesc, isok, reason, ismailopen, luserid, ldate, subjnm, seqgrnm, cmode, contents, ISONOFF)";
			sql += " values (?, ?, ?, ?, ?, '"+seq+"', ?, ?, ?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, ?,?)";
			System.out.println(sql);

			pstmt = connMgr.prepareStatement(sql);
			pstmt.setString(1,  "".equals(dbox.getString("d_subj")) ? "ALL" : dbox.getString("d_subj"));
			pstmt.setString(2,  "".equals(dbox.getString("d_year")) ? FormatDate.getDate("yyyy") : dbox.getString("d_year"));
			pstmt.setString(3,  "".equals(dbox.getString("d_subjseq")) ? "ALL" : dbox.getString("d_subjseq"));
			pstmt.setString(4,  dbox.getString("d_userid"));
			pstmt.setString(5,  dbox.getString("d_touch"));
			pstmt.setString(6,  dbox.getString("d_ismail"));
			pstmt.setString(7,  dbox.getString("d_edustart"));
			pstmt.setString(8,  dbox.getString("d_eduend"));
			pstmt.setString(9,  dbox.getString("d_title"));
			pstmt.setString(10, dbox.getString("d_sdesc"));
			pstmt.setString(11, dbox.getString("d_isok"));
			pstmt.setString(12, dbox.getString("d_reason"));
			pstmt.setString(13, dbox.getString("d_ismailopen"));
			pstmt.setString(14, v_fromCono);
			pstmt.setString(15, dbox.getString("d_subjnm"));
			pstmt.setString(16, dbox.getString("d_seqgrnm"));
			pstmt.setString(17, dbox.getString("d_mcode"));
			pstmt.setString(18, dbox.getString("d_mailcontent"));
			pstmt.setInt(19, dbox.getBoolean("p_offlinemail")?1:0);
			/*
                System.out.println("1, "+dbox.getString("d_subj"));
                System.out.println("2, "+dbox.getString("d_year"));
                System.out.println("3, "+dbox.getString("d_subjseq"));
                System.out.println("4, "+dbox.getString("d_userid"));
                System.out.println("5, "+dbox.getString("d_touch"));
                System.out.println("6, "+dbox.getString("d_ismail"));
                System.out.println("7, "+dbox.getString("d_edustart"));
                System.out.println("8, "+dbox.getString("d_eduend"));
                System.out.println("9, "+dbox.getString("d_title"));
                System.out.println("10,"+dbox.getString("d_sdesc"));
                System.out.println("11,"+dbox.getString("d_isok"));
                System.out.println("12,"+dbox.getString("d_reason"));
                System.out.println("13,"+dbox.getString("d_ismailopen"));
                System.out.println("14,"+v_fromCono);
                System.out.println("15,"+dbox.getString("d_subjnm"));
                System.out.println("16,"+dbox.getString("d_seqgrnm"));
                System.out.println("17,"+dbox.getString("d_mcode"));
                System.out.println("18,"+dbox.getString("d_mailcontent"));
			 */
			isOk = pstmt.executeUpdate();

			if(isOk > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}
			else {
				if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			}
		}
		catch (Exception ex) {         ex.printStackTrace();
		if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
		ErrorManager.getErrorStackTrace(ex);
		throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}




}