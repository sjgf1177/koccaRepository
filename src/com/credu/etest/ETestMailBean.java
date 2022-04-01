//**********************************************************
//1. 제      목: 설문 발송 관리
//2. 프로그램명: ETestMailBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정:
//
//**********************************************************

package com.credu.etest;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.system.*;
import com.credu.common.*;
import com.dunet.common.util.StringUtil;

import java.text.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ETestMailBean {

    public ETestMailBean() {}


    /**
    온라인테스트 대상자 리스트
    @param box          receive from the form object and session
    @return ArrayList   온라인테스트 대상자
    */
    public ArrayList selectDamunMailList(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ArrayList list = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";        
        
        try {      
            String ss_grcode    = box.getString("s_grcode");
            String ss_subjcourse    = box.getString("s_subjcourse");
            String ss_year    = box.getString("s_gyear");
            String v_action  = box.getString("p_action");
            int v_damunpapernum    = box.getInt("s_damunpapernum");
            String v_mailgubun  = box.getString("s_mailgubun");
         
            list = new ArrayList();
            
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
                
                sql = "select a.subj,  a.grcode,   a.year,   a.subjseq, a.damunpapernum, a.mailnum, a.mailgubun, a.userid, a.freturn, a.freturntime, a.sreturn, a.sreturntime, ";
                sql+= "       b.comp  asgn,  get_compnm(b.comp,2,4)       asgnnm, ";
                sql+= "	   	  b.jikup,       get_jikupnm(b.jikup, b.comp) jikupnm, ";
                sql+= "	   	  b.cono,     b.name ";
                sql+= "  from tz_damunmail   a, ";
                sql+= "       tz_member     b  ";
                sql+= " where a.userid  = b.userid ";
                sql+= "   and a.subj    = " + SQLString.Format(ss_subjcourse);
                sql+= "   and a.grcode    = " + SQLString.Format(ss_grcode);
                sql+= "   and a.year    = " + SQLString.Format(ss_year);
                sql+= "   and a.damunpapernum    = " + SQLString.Format(v_damunpapernum);
                sql+= "   and a.mailgubun    = " + SQLString.Format(v_mailgubun);
                sql+= " order by a.userid ";
     
                ls = connMgr.executeQuery(sql);
                
                while (ls.next()) {
                    dbox = ls.getDataBox();
                    
                    list.add(dbox);
                }
            }   
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    public String getMailContent(RequestBox box, String p_userid, String p_name) throws Exception {

        ArrayList blist = null;
        StringBuffer sb = null;

        try {
		ConfigSet config = new ConfigSet();
            sb = new StringBuffer();

            String v_userid = p_userid;
            String v_name = p_name;
			String v_content = StringUtil.removeTag(box.getString("p_content"));

            String  v_fromEmail = box.getSession("email");
            String  v_fromName = box.getSession("name");
            String  v_comptel = box.getSession("comptel");
            String v_url = "";
            v_url          = config.getProperty("hkmotor.url.value");

			int v_isobserver = 1;
			
            sb.append(" <html>                                                                   \r\n");
            sb.append("     <head>                                                                   \r\n");
            sb.append("         <title>ETest 안내 메일</title>                                \r\n");
            sb.append("         <meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>     \r\n");
            sb.append("         <link rel='stylesheet' href='http://www.hkhrd.com/css/mail_style.css' type='text/css'> \r\n");
            sb.append("         <script language = 'javascript' src = 'http://www.hkhrd.com/script/cresys_lib.js'></script>  \r\n");
            sb.append("         <script language = 'VBScript' src = 'http://www.hkhrd.com/script/cresys_lib.vbs'></script>   \r\n");
            sb.append("         <SCRIPT LANGUAGE='JavaScript'>                                           \r\n ");
            sb.append("         <!--                                                                     \r\n");
			sb.append("           function whenSubmit(){                                                                 \r\n");
			sb.append("             document.form2.p_process.value = 'ETestUserListPage';                                                               \r\n");
			sb.append("             document.form2.submit();                                                               \r\n");
			sb.append("           }                                                                \r\n");
			sb.append("         -->                                                                     \r\n");
            sb.append("         </SCRIPT>                                                    \r\n");
            sb.append("                                                                                 \r\n");
            sb.append("     </head>                                                      \r\n");
            sb.append("     <body topmargin='0' leftmargin='0'>                                                     \r\n");
            sb.append("         <form name='form2' method='post' action='http://www.hkhrd.com/servlet/controller.etest.ETestUserServlet'>\r\n");
            sb.append("             <input type='hidden' name='p_process'    value='ETestUserListPage'>            \r\n");
            sb.append("             <input type='hidden' name='p_userid'      value='" + v_userid + "'>      \r\n");
            sb.append("         </form>                                                                   \r\n");
            sb.append("         <!----------------- 타이틀 시작 ----------------->                                                    \r\n");
            sb.append("             <table width='640' border='0' cellspacing='0' cellpadding='0'>                        \r\n");
            sb.append("               <tr>                                                                                               \r\n");
            sb.append("                 <td><img src='http://www.hkhrd.com/images/admin/research/Mform_top.gif' ></td>                                         \r\n");
            sb.append("               </tr>                                                                                              \r\n");
            sb.append("               <tr>                                                                                              \r\n");
            sb.append("                 <td class=sulmun_bg height=8>&nbsp;</td>                                                                                         \r\n");
            sb.append("               </tr>                                                                                              \r\n");
            sb.append("               <tr>                                                                                              \r\n");
			sb.append("                   <td align='center' class=sulmun_bg><table width='600' border='0' cellspacing='0' cellpadding='0'>                                                                                             \r\n");
            sb.append("                     <tr>                                                                                        \r\n");
            sb.append("                       <td class=sulmun_con>"+v_content+"</td>                                                                                   \r\n");
            sb.append("                     </tr>                                                                                       \r\n");
            sb.append("                     <tr>                                                                                       \r\n");
            sb.append("                       <td height=8></td>                                                                                   \r\n");
            sb.append("                     </tr>                                                                                       \r\n");
            sb.append("                 </table>                                                                                           \r\n");
            sb.append("                 <table width='600' align='center' cellspacing='1' cellpadding='2'>                                                                                               \r\n");
			sb.append("                   <tr>                                                                              \r\n");
			//sb.append("                      <td align=center><img src='http://www.hkhrd.com/images/admin/research/icon2.gif' ><a href='http://www.hkhrd.com/servlet/controller.etest.ETestUserServlet?p_process=ETestUserListPage&p_userid=" + v_userid + "' target='_blank'><b>클릭하시면 e-Test 시험에 응시하실 수 있습니다.</b></a></td>        \r\n");
			sb.append("                      <td align=center><img src='http://www.hkhrd.com/images/admin/research/icon2.gif' ><a href='"+v_url+"/servlet/controller.course.MailLoginServlet?p_process=EtestLogin&p_userid=" + v_userid + "' target='_blank'><b>클릭하시면 e-Test 시험에 응시하실 수 있습니다.</b></a></td>        \r\n");
			sb.append("                   </tr>                                                                              \r\n");
			sb.append("                   </table>		                                                                      \r\n");
			sb.append("                   </td>                                                                    \r\n");
			sb.append("                   </tr>                                                                              \r\n");
			sb.append("                   <tr>                                                                              \r\n");
			sb.append("                      <td class=sulmun_bg>&nbsp;</td>                                                                              \r\n");
			sb.append("                   </tr>                                                                              \r\n");
            sb.append("             <tr>                                                                                            \r\n");
            sb.append("               <td><img src='http://www.hkhrd.com/images/admin/research/Mform_bottom.gif' ></td>                                                                                           \r\n");
            sb.append("             </tr>                                                                                           \r\n");
            sb.append("           </table>                                                                                           \r\n");
            sb.append("     </body>                                            \r\n");
            sb.append("</html>                                            \r\n");

        }
        catch (Exception ex) {ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
        }
		return sb.toString();

    }

    public boolean insertMailSend(RequestBox box) throws Exception {
    
        Vector v_vchecks = box.getVector("p_userids");
        Vector v_names = box.getVector("p_names");
        String v_schecks = "";	
        String v_snames = "";	
        boolean isMailed = false;
		int isOk = 0;
        int cnt = 0;
        int next = 0;

        try {

			for(int i=0;i<v_vchecks.size();i++) {
                v_schecks = (String)v_vchecks.elementAt(i);
                v_snames = (String)v_names.elementAt(i);

                isMailed = sendFreeMail(box, v_schecks, v_snames); 
                
				if (isMailed){

				   //  isOk = insertDamunMail(box, v_schecks);
             
				}  
            }

        }
        catch (Exception ex) {ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
        }
        return isMailed;
	
	
	}

    public boolean sendFreeMail(RequestBox box, String p_userid, String p_name) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
        ListSet ls = null;
        String sql = "";
        boolean isMailed = false;
		int isOk = 0;

        try {
            connMgr = new DBConnectionManager();

////////////////////  프리메일 발송 //////////////////////////////////////////////////////////////////////////////////////////////////
			String v_sendhtml = "freeMailForm.html";
			FormMail fmail = new FormMail();

            MailSet mset = new MailSet(box);        //      메일 세팅 및 발송
        //    mset.setSender(fmail);     //  메일보내는 사람 세팅
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            String v_mailTitle = StringUtil.removeTag(box.getString("p_title"));
			String v_content = StringUtil.removeTag(box.getString("p_content"));
			String v_userid = p_userid;
			String v_name = p_name;
      //      fmail.setVariable("content", box.getString("p_content"));
            String v_mailContent = "";
            
                sql  = " select userid, email, cono, ismailing   ";
                sql += "   from tz_member               ";
                sql += "  where userid = " + StringManager.makeSQL(v_userid);
               // sql += "  where userid = 'lee1'";
                ls = connMgr.executeQuery(sql); 

                v_mailContent = getMailContent(box, v_userid, v_name);//fmail.getNewMailContent();

                while (ls.next()) {                    
                    String v_toCono =  ls.getString("cono");
                    String v_toEmail =  ls.getString("email");
                    String v_toUser =  ls.getString("userid");

                   /* isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, ls.getString("ismailing"), v_sendhtml); */
                   /* test */isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, "1", v_sendhtml); 

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
        return isMailed;
    }  

    public int insertDamunMail(RequestBox box, String p_userid) throws Exception {
        DBConnectionManager connMgr = null;        
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        Vector v_checks = new Vector();
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;   

		String  ss_subj      = box.getString("p_subj");
        String ss_grcode    = box.getString("p_grcode");
        String  ss_year      = box.getString("p_gyear");
        String  ss_subjseq   =box.getStringDefault("p_subjseq", "0001");
        int v_damunpapernum    = box.getInt("p_damunpapernum");
        String  v_mailgubun      = box.getString("p_mailgubun");

        String  v_luserid   = box.getSession("userid");
        int    v_mailnum = 0;

		String  v_userid  = p_userid;
        int cnt = 0;
        int next = 0;

        try {
            connMgr = new DBConnectionManager();                             
            connMgr.setAutoCommit(false);  

            v_mailnum = getMailnumSeq(ss_subj, ss_grcode, ss_year, v_damunpapernum);

			if(v_mailgubun.equals("1")){

			sql1 = "select userid from TZ_DAMUNMAIL";
            sql1+=  " where subj = ? and grcode = ? and year = ? and subjseq = ? and damunpapernum = ? and userid = ? "; 
            pstmt1 = connMgr.prepareStatement(sql1);   
            
            sql2 =  "insert into TZ_DAMUNMAIL(subj, grcode, year, subjseq, damunpapernum, mailnum, userid, freturn, freturntime, sreturn, sreturntime, mailgubun) ";
            sql2+=  " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt2 = connMgr.prepareStatement(sql2);

                pstmt1.setString( 1, ss_subj);   
                pstmt1.setString( 2, ss_grcode);   				
                pstmt1.setString( 3, ss_year);       
                pstmt1.setString( 4, ss_subjseq);
                pstmt1.setInt( 5, v_damunpapernum);   				
                pstmt1.setString( 6, v_userid);
         
                try {
                    rs = pstmt1.executeQuery();
          
                    if(!rs.next()) {    
                        pstmt2.setString( 1, ss_subj);      
                        pstmt2.setString( 2, ss_grcode);       
                        pstmt2.setString( 3, ss_year);       
                        pstmt2.setString( 4, ss_subjseq);
                        pstmt2.setInt( 5, v_damunpapernum);    
                        pstmt2.setInt( 6, v_mailnum);				
                        pstmt2.setString( 7, v_userid);
                        pstmt2.setString( 8, "N");
                        pstmt2.setString( 9, "");      
                        pstmt2.setString( 10, "N");
                        pstmt2.setString( 11, "");      				
                        pstmt2.setString( 12, v_mailgubun);	
						
						isOk = pstmt2.executeUpdate();

					}
                }catch(Exception ex) {
			            ErrorManager.getErrorStackTrace(ex);
                       throw new Exception(ex.getMessage());
				}
                finally { if (rs != null) { try { rs.close(); } catch (Exception e) {} }} 
				
			}

        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        }
        finally {            
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if (pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }            
            if (pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }            
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    public int getMailnumSeq(String p_subj, String p_grcode, String p_gyear, int p_damunpapernum) throws Exception {
        Hashtable maxdata = new Hashtable();
        maxdata.put("seqcolumn","mailnum");
        maxdata.put("seqtable","tz_damunmail");
        maxdata.put("paramcnt","4");
        maxdata.put("param0","subj");
        maxdata.put("param1","grcode");
        maxdata.put("param2","year");
        maxdata.put("param3","damunpapernum");
        maxdata.put("subj",   SQLString.Format(p_subj));
        maxdata.put("grcode",   SQLString.Format(p_grcode));
        maxdata.put("year",   SQLString.Format(p_gyear));
        maxdata.put("damunpapernum",   SQLString.Format(p_damunpapernum));

        return SelectionUtil.getSeq(maxdata);
    }

    /**
    *  대상자 설문지 셀렉트박스 (RequestBox, 셀렉트박스명,선택값,이벤트명)
	*  TZ_SULPAPER 이용
    */
    public static String getSulpaperSelect (String p_grcode, String p_gyear, String p_subj, String name, int selected, String event) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String redamunt = null;
        String sql = "";
        DataBox     dbox = null;

        redamunt = "  <SELECT name=" + name + " " + event + " > \n";

        redamunt += " <option value='0'>설문지를 선택하세요.</option> \n";

        try {
            connMgr = new DBConnectionManager();

			sql = "select grcode,       subj,         ";
            sql+= "       damunpapernum,  damunpapernm, year, ";
            sql+= "       totcnt,       damunnums, damunmailing, damunstart, damunend, ";
            sql+= "       'TARGET'      subjnm ";
            sql+= "  from tz_damunpaper ";
            sql+= " where  grcode = " + StringManager.makeSQL(p_grcode);
            sql+= "   and subj   = " +StringManager.makeSQL(p_subj);
            sql+= "   and year   = " + StringManager.makeSQL(p_gyear);
            sql+= "   and damunmailing   != 'N' " ;
            sql+= " order by subj, damunpapernum asc";

            ls = connMgr.executeQuery(sql);

                String v_null_test = "";
                String v_subj_bef = "";

            while (ls.next()) {

                    dbox = ls.getDataBox();

                redamunt += " <option value=" + dbox.getInt("d_damunpapernum");
                if (selected==dbox.getInt("d_damunpapernum")) {
                    redamunt += " selected ";
                }
                
                redamunt += ">" + dbox.getString("d_damunpapernm") + "</option> \n";
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

        redamunt += "  </SELECT> \n";
        return redamunt;
    } 
}