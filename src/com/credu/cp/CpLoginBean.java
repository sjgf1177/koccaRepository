//**********************************************************
//  1. ��      ��: ���ְ����ý��� �α����
//  2. ���α׷��� : CpLoginBean.java
//  3. ��      ��: �α���,�н�����ã��
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ��â�� 2004. 11.  14
//  7. ��      ��: �̳��� 05.11.17 _ Oracle -> MSSQL (OuterJoin) ����
//**********************************************************
package com.credu.cp;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.system.*;
import com.credu.cp.*;

public class CpLoginBean {

    public CpLoginBean() {}

    /**
     * �α��� (���� ����)
     * @param box       receive from the form object and session
     * @return is_Ok    1 : login ok      2 : login fail
     * @throws Exception
     */
   public int login(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        MemberData data = null;
        int is_Ok = 0;        
        int is_Ok2 = 0;
        String v_userid = box.getString("p_userid");
        String v_pwd    = box.getString("p_pwd");
        String v_userip = box.getString("p_userip");
        
        v_userid = v_userid.toLowerCase();

        try {
            connMgr = new DBConnectionManager();
           //userid,resno,name,email, comp, jikup, cono
   
            sql1  = " select a.userid, a.resno, a.name, a.email, ";
            sql1 += "        a.comp, a.jikup, a.cono, a.jikwi, b.gadmin, c.cpseq  ";
            sql1 += " from TZ_MEMBER a, TZ_MANAGER b, TZ_CPINFO c                   ";
            sql1 += " where a.userid = "+ StringManager.makeSQL(v_userid);
            sql1 += "   and a.pwd    = "+ StringManager.makeSQL(v_pwd);
            sql1 += "   and a.userid = b.userid ";
            sql1 += "   and b.gadmin in ('T1', 'S1', 'M1','A1','A2','H1' )  ";
            sql1 += "   and b.isdeleted = 'N' ";
            sql1 += "   and to_char(sysdate,'yyyymmdd') between b.fmon and b.tmon ";
			// ������ : 05.11.17 ������ : �̳��� _(+)  ����
//          sql1 += "   and b.userid = c.userid(+) ";
			sql1 += "   and b.userid  =  c.userid(+) ";
			sql1 += "	order by b.gadmin";
            //sql1 += "   and upper(a.usergubun)    = 'O' ";

            ls = connMgr.executeQuery(sql1);

            if (ls.next()) {
                data=new MemberData();
                data.setUserid(ls.getString("userid"));
                data.setResno(ls.getString("resno"));
                data.setName(ls.getString("name"));
                data.setEmail(ls.getString("email"));
                data.setComp(ls.getString("comp"));
                data.setJikup(ls.getString("jikup"));
                data.setJikwi(ls.getString("jikwi"));
                data.setCono(ls.getString("cono"));
				//data.setUsergubun(ls.getString("usergubun"));
				box.setSession("gadmin",ls.getString("gadmin"));
				box.setSession("cpseq",ls.getString("cpseq"));

                is_Ok = 1;
            }
            ls.close();

			// USERID �� ������� CONO���� üũ

            if (is_Ok == 0) {
	            sql2  = " select a.userid, a.resno, a.name, a.email, ";
	            sql2 += "        a.comp, a.jikup, a.cono, a.jikwi,  b.gadmin, c.cpseq  ";
	            sql2 += " from VZ_MEMBER a, TZ_MANAGER b, TZ_CPINFO c                    ";
	            sql2 += " where upper(a.cono)   = upper("+ StringManager.makeSQL(v_userid) + " )";
	            sql2 += "   and a.pwd    = "+ StringManager.makeSQL(v_pwd);
	            sql2 += "   and a.userid = b.userid ";
	            sql2 += "   and b.gadmin = 'S1' ";
	            sql2 += "   and b.isdeleted = 'N' ";
	            sql2 += "   and to_char(sysdate,'yyyymmdd') between b.fmon and b.tmon ";
				// ������ : 05.11.17 ������ : �̳��� _(+)  ����
//            	sql2 += "   and b.userid = c.userid(+) ";
				sql2 += "   and b.userid  =  c.userid(+) ";
	            
                ls = connMgr.executeQuery(sql2);

                if (ls.next()) {
                    data=new MemberData();
                    data.setUserid(ls.getString("userid"));
                    data.setResno(ls.getString("resno"));
                    data.setName(ls.getString("name"));
                    data.setEmail(ls.getString("email"));
                    data.setComp(ls.getString("comp"));
                    data.setJikup(ls.getString("jikup"));
	                data.setJikwi(ls.getString("jikwi"));
                    data.setCono(ls.getString("cono"));
					//data.setUsergubun(ls.getString("usergubun"));
					box.setSession("gadmin",ls.getString("gadmin"));
					box.setSession("cpseq",ls.getString("cpseq"));
					
                    is_Ok = 1;
                }
                ls.close();
            }

           if (is_Ok != 0) {
                box.setSession("cpuserid", data.getUserid());
                box.setSession("userid", data.getUserid());
                box.setSession("resno", data.getResno());
                box.setSession("name", data.getName());
                box.setSession("email", data.getEmail());
                box.setSession("comp", data.getComp());
                box.setSession("jikup", data.getJikup());
                box.setSession("jikwi", data.getJikwi());
                box.setSession("cono", data.getCono());
				//box.setSession("usergubun", data.getUsergubun());

                //box.setSession("gadmin","ZZ");

                //LOGIN DATA UPDATE
                //is_Ok2 = updateLoginData(v_userid, v_userip);  
        	}
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return is_Ok;
    }


    /**
     * Login ���� ���� (lgcnt:�α���Ƚ��, lglast:�����α��νð�, lgip:�α���ip
     * @param box       receive from the form object and session
     * @return is_Ok    1 : success      2 : fail 
     */
    public int updateLoginData(String p_userid, String p_userip) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;
        String v_userid = p_userid;
        String v_userip = p_userip;

        try {
              connMgr = new DBConnectionManager();

              sql  = " update TZ_MEMBER                       ";
              sql += " set lgcnt=lgcnt+1, lglast= to_char(sysdate, 'YYYYMMDDHH24MISS'), lgip="+StringManager.makeSQL(v_userip);
              sql += " where userid = "+ StringManager.makeSQL(v_userid);
    
            is_Ok = connMgr.executeUpdate(sql);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return is_Ok;
    }

   /**
     * ó���α� üũ
     * @param box       receive from the form object and session
     * @return is_Ok    0 : ó���α�      2 : ó���α� �ƴ�
     * @throws Exception
     */
    public int firstCheck(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;
        String v_userid = box.getString("p_userid");
        String v_pwd    = box.getString("p_pwd");


        try {
              connMgr = new DBConnectionManager();

              sql  = " select NVL(validation,'0') validation  ";
              sql += " from TZ_MEMBER                       ";
              sql += " where userid = "+ StringManager.makeSQL(v_userid);
              sql += "   and pwd    = "+ StringManager.makeSQL(v_pwd);

              ls = connMgr.executeQuery(sql);

              if (ls.next()) {
                is_Ok = StringManager.toInt(ls.getString("validation"));
              } else {
                is_Ok = 1;
              }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return is_Ok;
    }
    
    /**
     * �̸��� �ҷ�����
     * @param box       receive from the form object and session
     * @return return
     * @throws Exception
     */
    public String emailOpen(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String v_email = "" ;
        String v_userid = box.getString("p_userid");
        String v_pwd    = box.getString("p_pwd");

        try {
              connMgr = new DBConnectionManager();

              sql  = " select email  ";
              sql += " from TZ_MEMBER                       ";
              sql += " where userid = "+ StringManager.makeSQL(v_userid);
              //sql += "   and pwd    = "+ StringManager.makeSQL(v_pwd);
              ls = connMgr.executeQuery(sql);

              if (ls.next()) {
                v_email = ls.getString("email");
              }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return v_email;
    }
    
    /**
     * ó���α�� Ȯ������ (��й�ȣ ����)
     * @param box       receive from the form object and session
     * @return is_Ok    1 : login ok      2 : login fail
     * @throws Exception
     */
    public int firstLogin(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int is_Ok = 0;

        String v_userid    = box.getString("p_userid");
        String v_pwd       = box.getString("p_pwd");
        String v_name      = box.getString("p_name");
        String v_resno     = box.getString("p_resno");
        String v_ismailing = box.getString("p_ismailing");
        String v_email     = box.getString("p_email");
        String v_userip    = box.getString("p_userip");

        try {
             connMgr = new DBConnectionManager();

            // ���Ź���� 1(email)�� ��츸 email ������Ʈ
            if (v_ismailing.equals("1")) {
                sql  = " update TZ_MEMBER set validation = ? ,pwd = ? ,ismailing = ?, email = ? ";
                sql += "  where userid = ? and resno = ? and name = ?          ";

                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, "1");
                pstmt.setString(2, v_pwd);
                pstmt.setString(3, v_ismailing);
                pstmt.setString(4, v_email);
                pstmt.setString(5, v_userid);
                pstmt.setString(6, v_resno);
                pstmt.setString(7, v_name);

                is_Ok = pstmt.executeUpdate();
            } else {
                sql  = " update TZ_MEMBER set validation = ? ,pwd = ? ,ismailing = ? ";
                sql += "  where userid = ? and resno = ? and name = ?                ";

                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, "1");
                pstmt.setString(2, v_pwd);
                pstmt.setString(3, v_ismailing);
                pstmt.setString(4, v_userid);
                pstmt.setString(5, v_resno);
                pstmt.setString(6, v_name);

                is_Ok = pstmt.executeUpdate();
            }
         }
         catch(Exception ex) {
             ErrorManager.getErrorStackTrace(ex, box, sql);
             throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
         }
         finally {
             if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
             if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
         }
        return is_Ok;
    }


    /**
     * ��й�ȣ �н� ������ �߼�
     * @param box       receive from the form object and session
     * @return is_Ok    1 : send ok      2 : send fail
     * @throws Exception
     */
    public int sendFormMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok  = 0;    //  ���Ϲ߼� ���� ����

        String v_userid = box.getString("p_userid");
        String v_resno  = box.getString("p_resno");

        try {
            connMgr = new DBConnectionManager();

            sql  = " select name, email, pwd, cono,ismailing ";
            sql += " from TZ_MEMBER                          ";
            sql += " where userid = "+ StringManager.makeSQL(v_userid);
            sql += "   and resno  = "+ StringManager.makeSQL(v_resno);
            ls = connMgr.executeQuery(sql);

////////////////////  ������ �߼� //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            String v_sendhtml = "LossPwd.html";

            FormMail fmail = new FormMail(v_sendhtml);     //      �����Ϲ߼��� ���

            MailSet mset = new MailSet(box);               //      ���� ���� �� �߼�

            String v_mailTitle = "�н����� �нǿ� ���� ���Ǵ亯�Դϴ�.";
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            if (ls.next()) {

//                String v_isMailing =  ls.getString("ismailing");
//                if (v_isMailing.equals("3") || v_isMailing.equals("")) v_isMailing = "1";
                String v_isMailing =  "1";              // email�θ� ����

                String v_name      =  ls.getString("name");
                String v_pwd       =  ls.getString("pwd");
                String v_toEmail   =  ls.getString("email");
                String v_toCono    =  ls.getString("cono");

                mset.setSender(fmail);     //  ���Ϻ����� ��� ����

                fmail.setVariable("userid", v_userid);
                fmail.setVariable("name", ls.getString("name"));
                fmail.setVariable("pwd", ls.getString("pwd"));

                String v_mailContent = fmail.getNewMailContent();

                boolean isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, v_isMailing, v_sendhtml);

                if(isMailed) is_Ok = 1;     //      ���Ϲ߼ۿ� �����ϸ�
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return is_Ok;
    }



    /**
     * ���� ����Ʈ �ڽ�
     * @param userid      �������̵�
     * @param name        ����Ʈ�ڽ���
     * @param selected    ���ð�
     * @param event       �̺�Ʈ��
     * @return
     * @throws Exception
     */
     public static String getAuthSelect (String userid, String name, String selected, String event) throws Exception {
         DBConnectionManager connMgr = null;
         ListSet ls = null;
         String result = null;
         String sql = "";
         int cnt = 0;

         result = "  <SELECT name='" + name + "' " + event + "  style='border-style:solid;border-width: 1px 1px 1px 1px;border-color:cccccc;color:333333;font-size:9pt;background-color:none;width : 120px;height:19px;font-size:9pt;'>  \n";

         try {
             connMgr = new DBConnectionManager();

             sql  = " select b.gadmin gadmin, b.gadminnm gadminnm    ";
             sql += "   from tz_manager a, tz_gadmin b               ";
             sql += "  where a.gadmin    = b.gadmin                  ";
             sql += "    and a.userid    = " + StringManager.makeSQL(userid);
             sql += "    and a.isdeleted = 'N'                       ";
             sql += "    and to_char(sysdate,'yyyymmdd') between a.fmon and a.tmon ";
             sql += " order by b.gadmin asc";

             ls = connMgr.executeQuery(sql);

             while (ls.next()) {

                 result += " <option value=" + ls.getString("gadmin");
                 if (selected.equals(ls.getString("gadmin"))) {
                     result += " selected ";
                 }
                 result += ">" + ls.getString("gadminnm") + "</option>  \n";
                 cnt++;
             }
         }
         catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex);
             throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
         }
         finally {
             if(ls != null) { try { ls.close(); }catch (Exception e) {} }
             if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
         }
         result += "  <option value='ZZ'";
		 
        if (selected.equals("ZZ") || selected.equals("")) {
           result += " selected ";
        }
         result += ">�н���</option>";
         result += "  </SELECT> ";

         if (cnt == 0) result = "";
         return result;
     }
}
