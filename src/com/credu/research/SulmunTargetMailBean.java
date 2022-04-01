//**********************************************************
//1. 제      목: 설문 발송 관리
//2. 프로그램명: SulmunTargetMailBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정:
//
//**********************************************************

package com.credu.research;

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
public class SulmunTargetMailBean {

    public SulmunTargetMailBean() {}


    /**
    설문 대상자 리스트
    @param box          receive from the form object and session
    @return ArrayList   설문 대상자
    */
    public ArrayList selectSulmunMailList(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ArrayList list = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";        
        
        try {      
            String ss_grcode    = box.getString("s_grcode");
            String ss_year    = box.getString("s_gyear");
            String v_action  = box.getString("p_action");
            int v_sulpapernum    = box.getInt("s_sulpapernum");
         
            list = new ArrayList();
            
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
				//수정:lyh 일자: 2005-11-16  내용:decode를 case  when 으로.. 
                sql = "select a.subj,  a.grcode,    a.year,   a.subjseq, a.sulpapernum, b.mailnum,  a.userid, case b.return  when  '' then 'N'  else  'Y'  end as ismailsend, b.return, b.returntime, ";
                sql+= "       a.asgn,  a.companynm, a.asgnnm, ";
                sql+= "	   	  a.jikup,       a.jikupnm, a.jikwi,       a.jikwinm,";
                sql+= "	   	  a.cono,     a.name from";
                sql+= " (select a.subj,  a.grcode,   a.year,   a.subjseq, a.sulpapernum, a.userid, ";
                sql+= "       b.comp  asgn,  get_compnm(b.comp,2,2) companynm, get_compnm(b.comp,2,4)  asgnnm, ";
                sql+= "	   	  b.jikup,       b.jikupnm, b.jikwi,   b.jikwinm,";
                sql+= "	   	  b.cono,     b.name ";
                sql+= "  from tz_sulmember   a, ";
                sql+= "       tz_member     b  ";
                sql+= " where a.userid  = b.userid ";
                sql+= "   and a.subj    = 'TARGET' ";
                sql+= "   and a.grcode    = " + SQLString.Format(ss_grcode);
                sql+= "   and a.year    = " + SQLString.Format(ss_year);
                sql+= "   and a.sulpapernum    = " + SQLString.Format(v_sulpapernum);
                sql+= " ) a, ";
                sql+= " (select return, returntime, mailnum, subj, subjseq, grcode, year, sulpapernum, userid from tz_sulmail   ";
                sql+= " where subj    = 'TARGET' ";
                sql+= "   and grcode    = " + SQLString.Format(ss_grcode);
                sql+= "   and year    = " + SQLString.Format(ss_year);
                sql+= "   and sulpapernum    = " + SQLString.Format(v_sulpapernum);
                sql+= " ) b";
				//
//              sql+= " where a.subj = b.subj(+) and a.subjseq = b.subjseq(+) and a.grcode = b.grcode(+) and a.sulpapernum = b.sulpapernum(+) and a.year = b.year(+) and a.userid = b.userid(+)";
				sql+= " where a.subj  =  b.subj(+) and a.subjseq  =  b.subjseq(+) and a.grcode  =  b.grcode(+) " ;
				sql+= "   and a.sulpapernum  =  b.sulpapernum(+) and a.year  =  b.year(+) and a.userid  =  b.userid(+)";
                
                if(box.getString("p_orderColumn").equals("resposetime")) {
                    sql+= " order by b.returntime " + box.getString("p_flag");
                }  
                else {                          
                    sql+= " order by a.name ";
                }
                
                ls = connMgr.executeQuery(sql);
                
                while (ls.next()) {
                    dbox = ls.getDataBox();
                    
                    list.add(dbox);
                }
                ls.close();
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
        SulmunTargetPaperBean bean = null;
		DataBox dbox0 = null;

        try {
			bean = new SulmunTargetPaperBean();
			blist = bean.selectPaperQuestionExampleList(box);
            
			box.put("p_subjsel",box.getString("p_subj"));
			box.put("p_upperclass","ALL");
			dbox0 = bean.getPaperData(box);   

            sb = new StringBuffer();

            String v_grcode     = dbox0.getString("d_grcode"); 
	        String v_subj     = dbox0.getString("d_subj"); 
	        String v_subjseq     = dbox0.getString("d_subjseq"); 
            String v_gyear     = dbox0.getString("d_year"); 
            String v_sulpapernum = dbox0.getString("d_sulpapernum"); 
            String v_sulpapernm = dbox0.getString("d_sulpapernm"); 
            String v_sulnums = dbox0.getString("d_sulnums"); 

            String v_userid = p_userid;
            String v_name = p_name;
			String v_content = StringUtil.removeTag(box.getString("p_content"));

            String  v_fromEmail = box.getSession("email");
            String  v_fromName = box.getSession("name");
            String  v_comptel = box.getSession("comptel");

			String v_mail = "javascript:self.whenSubmit()";

			
            sb.append(" <html>                                                                   \r\n");
            sb.append("     <head>                                                                   \r\n");
            sb.append("         <title>설문 메일</title>                                \r\n");
            sb.append("         <meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>     \r\n");
            sb.append("         <link rel='stylesheet' href='http://www.hkhrd.com/css/mail_style.css' type='text/css'> \r\n");
            sb.append("         <script language = 'javascript' src = 'http://www.hkhrd.com/script/cresys_lib.js'></script>  \r\n");
            sb.append("         <script language = 'VBScript' src = 'http://www.hkhrd.com/script/cresys_lib.vbs'></script>   \r\n");
            sb.append("     </head>                                                      \r\n");
            sb.append("     <body topmargin='0' leftmargin='0'>                                                     \r\n");
            sb.append("         <form name='form2' method='post' action='http://www.hkhrd.com/servlet/controller.etest.ETestUserServlet'>\r\n");
            sb.append("             <input type='hidden' name='p_process'    value='ETestUserListPage'>            \r\n");
            sb.append("             <input type='hidden' name='p_userid'      value='" + v_userid + "'>      \r\n");
            sb.append("         </form>                                                                   \r\n");
            //sb.append("         <!----------------- 타이틀 시작 ----------------->                                                    \r\n");
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
	//		sb.append("                      <td align=center><img src='http://www.hkhrd.com/images/admin/research/icon2.gif' ><a href='http://www.hkhrd.com/servlet/controller.research.SulmunTargetUserServlet?p_process=SulmunUserPaperListPage&p_userid=" + v_userid + "' target='_blank'><b>클릭하시면 설문에 응시하실 수 있습니다.</b></a></td>        \r\n");
	sb.append("                      <td align=center><img src='http://www.hkhrd.com/images/admin/research/icon2.gif' ><a href='http://www.hkhrd.com/servlet/controller.research.SulmunTargetMailResultServlet?p_process=SulmunUserPaperListPage&p_userid=" + v_userid + "&p_subj=" + v_subj + "&p_grcode=" + v_grcode + "&p_gyear=" + v_gyear + "&p_subjseq=" + v_subjseq + "&p_sulpapernum=" + v_sulpapernum + " ' target='_blank'><b>클릭하시면 설문에 응시하실 수 있습니다.</b></a></td>        \r\n");
	//sb.append("                      <td align=center><img src='http://www.hkhrd.com/images/admin/research/icon2.gif' ><a href='http://10.40.37.65:8899/servlet/controller.research.SulmunTargetMailResultServlet?p_process=SulmunUserPaperListPage&p_userid=" + v_userid + "&p_subj=" + v_subj + "&p_grcode=" + v_grcode + "&p_gyear=" + v_gyear + "&p_subjseq=" + v_subjseq + "&p_sulpapernum=" + v_sulpapernum + " ' target='_blank'><b>클릭하시면 설문에 응시하실 수 있습니다.</b></a></td>        \r\n");
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
        boolean isMailsend = false;
		int isOk = 0;
        int cnt = 0;
        int next = 0;

        try {

			for(int i=0;i<v_vchecks.size();i++) {
                v_schecks = (String)v_vchecks.elementAt(i);
                v_snames = (String)v_names.elementAt(i);

                isMailed = sendFreeMail(box, v_schecks, v_snames); 
                
				if (isMailed){

				     isOk = insertSulmunMail(box, v_schecks);
				}  
				if(isMailed) isMailsend = true;
            }
        }
        catch (Exception ex) {ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
        }
        return isMailsend;
	
	
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
                ls.close();
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

    public int insertSulmunMail(RequestBox box, String p_userid) throws Exception {
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
        int v_sulpapernum    = box.getInt("p_sulpapernum");

        String  v_luserid   = box.getSession("userid");
        int    v_mailnum = 0;

		String  v_userid  = p_userid;
        int cnt = 0;
        int next = 0;

        try {
            connMgr = new DBConnectionManager();                             
            connMgr.setAutoCommit(false);////  

            v_mailnum = getMailnumSeq(ss_subj, ss_grcode, ss_year, v_sulpapernum);

			sql1 = "select userid from TZ_SULMAIL";
            sql1+=  " where subj = ? and grcode = ? and year = ? and subjseq = ? and sulpapernum = ? and userid = ? "; 
            pstmt1 = connMgr.prepareStatement(sql1);   
            
            sql2 =  "insert into TZ_SULMAIL(subj, grcode, year, subjseq, sulpapernum, mailnum, userid, return, returntime) ";
            sql2+=  " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt2 = connMgr.prepareStatement(sql2);

                pstmt1.setString( 1, ss_subj);   
                pstmt1.setString( 2, ss_grcode);   				
                pstmt1.setString( 3, ss_year);       
                pstmt1.setString( 4, ss_subjseq);
                pstmt1.setInt( 5, v_sulpapernum);   				
                pstmt1.setString( 6, v_userid);
         
                try {
                    rs = pstmt1.executeQuery();
          
                    if(!rs.next()) {    
                        pstmt2.setString( 1, ss_subj);      
                        pstmt2.setString( 2, ss_grcode);       
                        pstmt2.setString( 3, ss_year);       
                        pstmt2.setString( 4, ss_subjseq);
                        pstmt2.setInt( 5, v_sulpapernum);    
                        pstmt2.setInt( 6, v_mailnum);				
                        pstmt2.setString( 7, v_userid);
                        pstmt2.setString( 8, "N");
                        pstmt2.setString( 9, "");                        
                    
						isOk = pstmt2.executeUpdate();

					}
                }catch(Exception ex) {
			            ErrorManager.getErrorStackTrace(ex);
                       throw new Exception(ex.getMessage());
				}
                finally { if (rs != null) { try { rs.close(); } catch (Exception e) {} }}           

        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        }
        finally {            
            if(connMgr != null) { try { connMgr.commit(); connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if (pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }            
            if (pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }            
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    public int getMailnumSeq(String p_subj, String p_grcode, String p_gyear, int p_sulpapernum) throws Exception {
        Hashtable maxdata = new Hashtable();
        maxdata.put("seqcolumn","mailnum");
        maxdata.put("seqtable","tz_sulmail");
        maxdata.put("paramcnt","4");
        maxdata.put("param0","subj");
        maxdata.put("param1","grcode");
        maxdata.put("param2","year");
        maxdata.put("param3","sulpapernum");
        maxdata.put("subj",   SQLString.Format(p_subj));
        maxdata.put("grcode",   SQLString.Format(p_grcode));
        maxdata.put("year",   SQLString.Format(p_gyear));
        maxdata.put("sulpapernum",   SQLString.Format(p_sulpapernum));

        return SelectionUtil.getSeq(maxdata);
    }

    /**
    *  대상자 설문지 셀렉트박스 (RequestBox, 셀렉트박스명,선택값,이벤트명)
	*  TZ_SULPAPER 이용
    */
    public static String getSulpaperSelect (String p_grcode, String p_gyear, String p_subj, String name, int selected, String event) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        DataBox     dbox = null;

        result = "  <SELECT name=" + name + " " + event + " > \n";

        result += " <option value='0'>설문지를 선택하세요.</option> \n";

        try {
            connMgr = new DBConnectionManager();

			sql = "select grcode,       subj,         ";
            sql+= "       sulpapernum,  sulpapernm, year, ";
            sql+= "       totcnt,       sulnums, sulmailing, sulstart, sulend, ";
            sql+= "       'TARGET'      subjnm ";
            sql+= "  from tz_sulpaper ";
            sql+= " where  grcode = " + StringManager.makeSQL(p_grcode);
            sql+= "   and subj   = " +StringManager.makeSQL(p_subj);
            sql+= "   and year   = " + StringManager.makeSQL(p_gyear);
            sql+= "   and sulmailing   != 'N' " ;
            sql+= " order by subj, sulpapernum asc";

            ls = connMgr.executeQuery(sql);

                String v_null_test = "";
                String v_subj_bef = "";

            while (ls.next()) {

                    dbox = ls.getDataBox();

                result += " <option value=" + dbox.getInt("d_sulpapernum");
                if (selected==dbox.getInt("d_sulpapernum")) {
                    result += " selected ";
                }
                
                result += ">" + dbox.getString("d_sulpapernm") + "</option> \n";
            }
            ls.close();
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        result += "  </SELECT> \n";
        return result;
    } 
}