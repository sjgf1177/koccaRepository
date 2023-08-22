package com.credu.library;

import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.util.Vector;

import com.dunet.common.util.Constants;
import com.dunet.common.util.EncryptUtil;
import com.dunet.common.util.StringUtil;
import com.mnwise.lts.client.TcpipClient;

@SuppressWarnings("unchecked")
public class FreeMailBean {

    public FreeMailBean() {
    }

    /**
     * ��뷮 ���Ϲ߼� - WiseU ���� �߼� �ý����� ����ϰ� �ִ�.
     * 
     * @param box RequestBox class
     * @param aid eCare ��ȣ. ���� �߼� �ý��ۿ��� �ǽð� �������� ���Ǵ� ��ȣ�̴�. ���� ������� ���� 34
     */
    public int sendFreeMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls1 = null;
        String sql = "";
        String sql1 = "";
        boolean isMailed = false;

        int isCnt = 0; //�߼۰� ��

        DataBox dbox = null;

        Vector v_vchecks = box.getVector("p_checks");
        String v_isMailing = box.getString("p_isMailing");
        String v_aid = box.getString("p_aid");
        String v_schecks = "";

        if ("".equals(v_aid)) {
            v_aid = "34";
        }

        //2010.02.03 subj,year,subjseq �߰�
        //��������  v_schecks �� userid �� ������ ����
        //�� ��� tz_humantouch �� �����Ͱ� ȭ�鿡�� �˻��������� ������ subj, year, subjseq �� ���� �־���
        //��]subjseq = '<!-- ALL -->' �̷������� ��
        Vector v_vsubj = box.getVector("p_subj");
        Vector v_vyear = box.getVector("p_year");
        Vector v_vsubjseq = box.getVector("p_subjseq");

        String v_subj = "";
        String v_msubj = box.get("p_msubj");
        String v_myear = box.get("p_myear");
        String v_msubjseq = box.get("p_msubjseq");
        String v_year = "";
        String v_subjseq = "";
        String v_subjnm = "";
        String v_seqgrnm = "";
        String v_grcode = "";

        String v_touch = box.getString("p_touch");
        String v_mcode = box.getString("s_mcode");

        String v_title = "";
        String v_content = "";

        //Log.info.println(">>>>>>>>>>v_isMailing=>"+v_isMailing);

        try {
            // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

            connMgr = new DBConnectionManager();

            TcpipClient tc = new TcpipClient(); // ���Ϲ߼� socket connection open

            MailSet mset = new MailSet(box); //      ���� ���� �� �߼�, ������ ���� ���Ϲ߼������� SMS �� ���� ����

            tc.open("mail2.kocca.kr", 9110); //9100 ���ĺ���

            for (int i = 0; i < v_vchecks.size(); i++) {

                //��������
                v_subj = (String) v_vsubj.elementAt(i);
                v_year = (String) v_vyear.elementAt(i);
                v_subjseq = (String) v_vsubjseq.elementAt(i);

                if (box.getBoolean("p_offlinemail")) {
                    sql1 = " SELECT B.subj, B.year, B.subjseq, A.subjnm, b.SUBJNM GRSEQNM, 'N000001' GRCODE \n";
                    sql1 += "   FROM TZ_OFFSUBJ A, TZ_OFFSUBJSEQ B\n";
                    sql1 += "  where A.SUBJ = B.SUBJ \n";
                } else {
                    sql1 = " select a.subj, a.year, a.subjseq, a.subjnm, b.grseqnm,a.grcode \n";
                    sql1 += "   from tz_subjseq a, tz_grseq b\n";
                    sql1 += "  where a.grcode = b.grcode\n ";
                    sql1 += "    and a.gyear = b.gyear\n ";
                    sql1 += "    and a.grseq = b.grseq\n ";
                }
                sql1 += "    and A.subj = nvl(" + StringManager.makeSQL(v_subj) + ", " + StringManager.makeSQL(v_msubj) + ")";
                sql1 += "\n    and year = nvl(" + StringManager.makeSQL(v_year) + ", " + StringManager.makeSQL(v_myear) + ")";
                sql1 += "\n    and subjseq = nvl(" + StringManager.makeSQL(v_subjseq) + ", " + StringManager.makeSQL(v_msubjseq) + ")";


                ls1 = connMgr.executeQuery(sql1);
                while (ls1.next()) {
                    v_subjnm = ls1.getString("subjnm");
                    v_seqgrnm = ls1.getString("grseqnm");
                    v_grcode = ls1.getString("grcode");
                }

                //���������
                v_schecks = (String) v_vchecks.elementAt(i);//userid

                sql = " select userid, name, crypto.dec('normal',email) email, cono, crypto.dec('normal',handphone) handphone, grcode\n";
                sql += "   from tz_member\n";
                sql += "  where userid = " + StringManager.makeSQL(v_schecks);
                sql += "and grcode = " + StringManager.makeSQL(v_grcode);
                //				if(box.getBoolean("p_offlinemail")) sql += "\nand grcode='N000001'";

                //Log.info.println(">>>>>>>>>>mail_sql=>"+sql);

                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    String v_toEmail = ls.getString("email");
                    String v_handphone = ls.getString("handphone");
                    String v_userid = ls.getString("userid");
                    String v_name = ls.getString("name");

                    //if (v_grcode.equals("N000001")) {
                    //====================================================
                    // �������� ��ȣȭ - HTJ
                    /*
                     * SeedCipher seed = new SeedCipher(); if
                     * (!v_toEmail.equals("")) v_toEmail =
                     * seed.decryptAsString(Base64.decode(v_toEmail),
                     * seed.key.getBytes(), "UTF-8"); if
                     * (!v_handphone.equals("")) v_handphone =
                     * seed.decryptAsString(Base64.decode(v_handphone),
                     * seed.key.getBytes(), "UTF-8");
                     */

                    //if (!v_toEmail.equals("")) v_toEmail = encryptUtil.decrypt(v_toEmail);
                    //if (!v_handphone.equals("")) v_handphone = encryptUtil.decrypt(v_handphone);
                    //====================================================
                    //}

                    v_title = StringUtil.removeTag(box.getString("p_title"));
                    //v_content	= StringUtil.removeTag(box.getString("p_content"));
                    v_content = box.getString("p_content");

                    v_title = StringManager.replace(v_title, "v_subjnm", v_subjnm);
                    v_title = StringManager.replace(v_title, "v_name", v_name);
                    v_content = StringManager.replace(v_content, "v_subjnm", v_subjnm);
                    v_content = StringManager.replace(v_content, "v_name", v_name);


                    if (v_isMailing.equals("1") && !v_toEmail.equals("")) {
                        tc.setAID(v_aid);
                        tc.setArg("INAME", v_name);
                        tc.setArg("IUSERID", v_userid);
                        tc.setArg("IEMAIL", v_toEmail);
                        tc.setArg("TITLE", v_title);
                        tc.setData("CONTENT", v_content);
                        //System.out.println("#######"+i+" : "+v_name);
                        //tc.setAID("1");
                        //tc.setArg("INAME", "����ȯ");
                        //tc.setArg("IUSERID", "kks0404");
                        //tc.setArg("IEMAIL", "kks0404@dunet.co.kr");
                        //tc.setData("CONTENT", "sdlfksdfl\r\n111111\r\n");

                        tc.commit();
                    } else if (v_isMailing.equals("2") && !v_handphone.equals("")) {
                        /* test */
                        isMailed = mset.sendMail(v_handphone, "�����������", v_title, "", "2", "");
                    }

                    dbox = ls.getDataBox();
                    //dbox.put("d_subj"   , v_msubj);
                    //dbox.put("d_year"   , v_myear);
                    //dbox.put("d_subjseq", v_msubjseq);
                    //dbox.put("d_subjnm", v_msubjnm);
                    //dbox.put("d_seqgrnm", v_mseqgrnm);

                    dbox.put("d_subj", v_subj);
                    dbox.put("d_year", v_year);
                    dbox.put("d_subjseq", v_subjseq);
                    dbox.put("d_userid", v_schecks);
                    dbox.put("d_touch", v_touch);
                    dbox.put("d_ismail", v_isMailing);
                    dbox.put("d_title", v_title);
                    dbox.put("d_subjnm", v_subjnm);
                    dbox.put("d_seqgrnm", v_seqgrnm);
                    dbox.put("p_offlinemail", box.get("p_offlinemail"));
                    dbox.put("d_mailcontent", v_content);

                    if (isMailed) {
                        isCnt++;
                        dbox.put("d_isok", "Y");
                    } else {
                        isCnt++;
                        dbox.put("d_isok", "N");
                    }

                    dbox.put("d_ismailopen", "N");
                    dbox.put("d_mcode", v_mcode);
                }

                mset.insertHumanTouch(dbox);
                ls.close();
                ls1.close();
            }

            tc.quit(); // ���Ϲ߼� socket connection close

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isCnt;
    }

    /**
     * ��뷮 ���Ϲ߼�( Id ã�� ���� )
     * 
     * @param box RequestBox class
     * @param aid eCare ��ȣ
     */
    public String findIdFreeMail(RequestBox box) throws Exception {

        String return_Tmp = "";

        String v_email = box.getString("p_email");
        String v_aid = box.getString("p_aid");

        if ("".equals(v_aid)) {
            v_aid = "34";
        }

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        String v_title = "";
        String v_content = "";

        try {
            connMgr = new DBConnectionManager();
            TcpipClient tc = new TcpipClient(); // ���Ϲ߼� socket connection open
            tc.open("mail2.kocca.kr", 9110); //9100 ���ĺ���

            //�̸��� ��ȣȭ �� Ȯ��
            //EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);
            //v_email = encryptUtil.encrypt(v_email);			

            //���������			
            sql = " select userid,crypto.dec('normal',email)as email\n";
            sql += "   from tz_member\n";
            sql += "  where crypto.dec('normal',email) = " + StringManager.makeSQL(v_email);
            sql += "and grcode = " + StringManager.makeSQL(box.getSession("tem_grcode"));

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {

                //v_email = encryptUtil.decrypt(v_email);	

                //String	v_toEmail	= encryptUtil.decrypt(ls.getString("email"));
                String v_toEmail = v_email;
                String v_userid = ls.getString("userid");

                v_title = "����� ���̵� ã��";
/*
                v_content = "<br/>"
                        + v_toEmail
                        + "�� ���� ���̵� ����Ǿ� �ֽ��ϴ�.<br/><br/>���̵� : "
                        + v_userid
                        + "<br/><br/>��û���� �ʾҴµ� �� ������ ���ŵ� ��� �ٸ� ����ڰ� �ڽ��� ����� ���̵��� ����Ϸ��� �ϴ� �������� �Ǽ��� ������ �̸��� �ּҸ� �Է����� �� �ֽ��ϴ�.<br>��û���� �ʾ����� �ƹ� ��ġ�� ���� �ʿ� ���� �̸����� �����ϼŵ� �˴ϴ�.";
*/

                v_content = "<br/>"
                        + "ȸ���Բ��� " + v_toEmail + "�� ��ȸ�� ���̵�� ������ �����ϴ�.<br/><br/>���̵� : "
                        + v_userid
                        + "<br/><br/>����, ������ī(EDUKOCCA) �¶��� ��ü ���� ����Ʈ�� ������ ���� ���ų�<br>���� ã�� ��û�� ���� ������ ��� �� ������ ���� �Ǵ� ������ �ֽñ� �ٶ��ϴ�.";


                if (!v_toEmail.equals("")) {
                    tc.setAID(v_aid);
                    tc.setArg("IUSERID", v_userid);
                    tc.setArg("IEMAIL", v_toEmail);
                    tc.setArg("TITLE", v_title);
                    tc.setData("CONTENT", v_content);

                    tc.commit();
                    return_Tmp = "true:" + v_email;
                }
            } else {
                return_Tmp = "false:" + v_email;
            }
            ls.close();

            tc.quit(); // ���Ϲ߼� socket connection close			

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return return_Tmp;
    }

    /**
     * ��뷮 ���Ϲ߼�( Id ã�� ���� )
     * 
     * @param box RequestBox class
     * @param aid eCare ��ȣ
     */
    public String findPwFreeMail(RequestBox box) throws Exception {

        String return_Tmp = "";

        String v_email = box.getString("p_email");
        String v_aid = box.getString("p_aid");

        if ("".equals(v_aid)) {
            v_aid = "34";
        }

        DBConnectionManager connMgr = null;
        PreparedStatement 	pstmt	= null;
        ListSet ls = null;
        String sql = "";

        String v_title = "";
        String v_content = "";
        
        int pstmtIndex = 1;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            TcpipClient tc = new TcpipClient(); // ���Ϲ߼� socket connection open
            tc.open("mail2.kocca.kr", 9110); //9100 ���ĺ���

            //�̸��� ��ȣȭ �� Ȯ��
            EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);
            //v_email = encryptUtil.encrypt(v_email);

            //���������			
            sql = " select userid,crypto.dec('normal',email) as email,pwd\n";
            sql += "   from tz_member\n";
            sql += "  where crypto.dec('normal',email) = " + StringManager.makeSQL(v_email);
            sql += "and grcode = " + StringManager.makeSQL(box.getSession("tem_grcode"));

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {

                //v_email = encryptUtil.decrypt(v_email);

                //String	v_toEmail	= encryptUtil.decrypt(ls.getString("email"));
                String v_toEmail = ls.getString("email");
                String v_userid = ls.getString("userid");
                
                
                // 2. �ӽ� ��й�ȣ�� �����Ѵ�.(��+��+��+��+��+��=6�ڸ�)
                String newpassword = "";
                for (int i = 1; i <= 6; i++) {
                	// ����
                	if (i % 3 != 0) {
                		newpassword += getRandomStr('a', 'z');
                		// ����
                	} else {
                		newpassword += getRandomNum(0, 9);
                	}
                }
                String v_pwd = HashCipher.createHash(newpassword);

                sql  = "\n update tz_member		";
                sql += "\n    set pwd 	 = ?	";
                sql += "\n      , ldate  = to_char(sysdate, 'yyyymmddhh24miss')		";
                sql += "\n  where userid = ?	";
                
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(pstmtIndex++, v_pwd);
                pstmt.setString(pstmtIndex++, v_userid);
                pstmt.executeUpdate();

                v_title = "�����  ��й�ȣ  ã��";
/*
                v_content = "<br/>"
                        + v_toEmail
                        + "�� ����� ���̵��� �ӽú�й�ȣ �Դϴ�.<br/><br/>�ӽú�й�ȣ : "
                        + newpassword
                        + "<br/><br/>��û���� �ʾҴµ� �� ������ ���ŵ� ��� �ٸ� ����ڰ� �ڽ��� ����� ���̵��� ����Ϸ��� �ϴ� �������� �Ǽ��� ������ �̸��� �ּҸ� �Է����� �� �ֽ��ϴ�.<br>��û���� �ʾ����� �ƹ� ��ġ�� ���� �ʿ� ���� �̸����� �����ϼŵ� �˴ϴ�.";
*/

                v_content = "<br/>"
                        + "ȸ���Բ��� " + v_toEmail + "�� ����� ���̵��� �ӽú�й�ȣ�� �߱޵Ǿ����ϴ�.<br/><br/>�ӽú�й�ȣ : "
                        + newpassword
                        + "<br/><br/>����, ������ī(EDUKOCCA) �¶��� ��ü ���� ����Ʈ�� ������ ���� ���ų�<br>���� ã�� ��û�� ���� ������ ��� �� ������ ���� �Ǵ� ������ �ֽñ� �ٶ��ϴ�.";


                if (!v_toEmail.equals("")) {
                    tc.setAID(v_aid);
                    tc.setArg("IUSERID", v_userid);
                    tc.setArg("IEMAIL", v_toEmail);
                    tc.setArg("TITLE", v_title);
                    tc.setData("CONTENT", v_content);

                    tc.commit();
                    return_Tmp = "true:" + v_email;
                }
            } else {
                return_Tmp = "false:" + v_email;
            }
            ls.close();

            tc.quit(); // ���Ϲ߼� socket connection close			
            
            connMgr.commit();

        } catch (Exception ex) {
        	if(connMgr != null){
        		try {
        			connMgr.rollback();
                } catch (Exception e10) {
                }
        	}
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
            	try {
            		pstmt.close();
            	} catch (Exception e) {
            	}
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return return_Tmp;
    }

    public int sendFreeMailOld(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        boolean isMailed = false;

        int isCnt = 0; //�߼۰� ��

        DataBox dbox = null;

        Vector v_vchecks = box.getVector("p_checks");
        String v_isMailing = box.getString("p_isMailing");
        String v_schecks = "";

        String v_touch = box.getString("p_touch");
        String v_msubjnm = box.getString("p_msubjnm");
        String v_mseqgrnm = box.getString("p_mseqgrnm");
        String v_msubj = box.getString("p_msubj");
        String v_myear = box.getString("p_myear");
        String v_msubjseq = box.getString("p_msubjseq");
        String v_mcode = box.getString("s_mcode");

        Log.info.println(">>>>>>>>>>v_isMailing=>" + v_isMailing);

        try {
            connMgr = new DBConnectionManager();

            ////////////////////  �������� �߼� //////////////////////////////////////////////////////////////////////////////////////////////////
            String v_sendhtml = box.getString("p_grcode") + "/freeMailForm.html";
            FormMail fmail = new FormMail(v_sendhtml);

            MailSet mset = new MailSet(box); //      ���� ���� �� �߼�
            mset.setSender(fmail); //  ���Ϻ����� ��� ����
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            String v_mailTitle = box.getString("p_title");
            fmail.setVariable("content", box.getString("p_content"));
            fmail.getNewMailContent();
            for (int i = 0; i < v_vchecks.size(); i++) {
                v_schecks = (String) v_vchecks.elementAt(i);


                sql = " select email, cono, handphone ";
                sql += "   from tz_member               ";
                sql += "  where userid = " + StringManager.makeSQL(v_schecks);
                Log.info.println(">>>>>>>>>>mail_sql=>" + sql);
                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    String v_toEmail = ls.getString("email");
                    String v_handphone = ls.getString("handphone");

                    /*
                     * isMailed = mset.sendMail(v_toCono, v_toEmail,
                     * v_mailTitle, v_mailContent, ls.getString("ismailing"),
                     * v_sendhtml);
                     */
                    if (v_isMailing.equals("1") && !v_toEmail.equals("")) {
                        //isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, "1", v_sendhtml);
                    } else if (v_isMailing.equals("2") && !v_handphone.equals("")) {
                        /* test */isMailed = mset.sendMail(v_handphone, "HKMC ���", v_mailTitle, "", "2", "");
                    }

                    dbox = ls.getDataBox();
                    dbox.put("d_subj", v_msubj);
                    dbox.put("d_year", v_myear);
                    dbox.put("d_subjseq", v_msubjseq);
                    dbox.put("d_userid", v_schecks);
                    dbox.put("d_touch", v_touch);
                    dbox.put("d_ismail", v_isMailing);
                    dbox.put("d_title", v_mailTitle);
                    if (isMailed) {
                        isCnt++;
                        dbox.put("d_isok", "Y");
                    } else {
                        isCnt++;
                        dbox.put("d_isok", "N");
                    }
                    dbox.put("d_ismailopen", "N");
                    dbox.put("d_subjnm", v_msubjnm);
                    dbox.put("d_seqgrnm", v_mseqgrnm);
                    dbox.put("d_mcode", v_mcode);
                }

                mset.insertHumanTouch(dbox);
                ls.close();

                /*
                 * isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle,
                 * v_mailContent, ls.getString("ismailing"), v_sendhtml);
                 */
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isCnt;
    }
    
    
    public static String getRandomStr(char startChr, char endChr) {
    	
    	int randomInt;
    	String randomStr = null;
    	
    	// ���۹��� �� ���Ṯ�ڸ� �ƽ�Ű���ڷ� ��ȯ�Ѵ�.
    	int startInt = Integer.valueOf(startChr);
    	int endInt = Integer.valueOf(endChr);
    	
    	// ���۹��ڿ��� ���Ṯ�ڿ����� Ŭ���
    	if (startInt > endInt) {
    		throw new IllegalArgumentException("Start String: " + startChr + " End String: " + endChr);
    	}
    	
    	try {
    		// ���� ��ü ����
    		SecureRandom rnd = new SecureRandom();
    		
    		do {
    			// ���۹��� �� ���Ṯ�� �߿��� ���� ���ڸ� �߻���Ų��.
    			randomInt = rnd.nextInt(endInt + 1);
    		} while (randomInt < startInt); // �Է¹��� ���� 'A'(65)���� ������ �ٽ� ���� ���� �߻�.
    		
    		// ���� ���ڸ� ���ڷ� ��ȯ �� ��Ʈ������ �ٽ� ��ȯ
    		randomStr = (char)randomInt + "";
    	} catch (Exception e) {
    		//e.printStackTrace();
    		throw new RuntimeException(e);	// 2011.10.10 �������� �ļ���ġ
    	}
    	
    	// �������ڿ��� ����
    	return randomStr;
    }
    
    public static int getRandomNum(int startNum, int endNum) {
    	int randomNum = 0;
    	
    	try {
    		// ���� ��ü ����
    		SecureRandom rnd = new SecureRandom();
    		
    		do {
    			// ������ڳ����� ���� ���ڸ� �߻���Ų��.
    			randomNum = rnd.nextInt(endNum + 1);
    		} while (randomNum < startNum); // ���� ���ڰ� ���ۼ��ں��� ������� �ٽ� �������ڸ� �߻���Ų��.
    	} catch (Exception e) {
    		//e.printStackTrace();
    		throw new RuntimeException(e);	// 2011.10.10 �������� �ļ���ġ
    	}
    	
    	return randomNum;
    }
}
