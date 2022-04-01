
//**********************************************************
//1. 제      목: 온라인테스트 시험지 관리
//2. 프로그램명: ETestPaperBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: lyh
//**********************************************************

package com.credu.etest;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.system.*;
import com.credu.etest.*;
import java.text.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ETestPaperBean {

    private ConfigSet conf;

    public ETestPaperBean() {
        try{
            conf = new ConfigSet();  
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
    온라인테스트 시험지 리스트
    @param box          receive from the form object and session
    @return ArrayList   온라인테스트 시험지
    */
    public ArrayList selectETestPaperList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql  = "";
        String sql2 = "";
        ResultSet rs = null;
        ArrayList list = null;
        ArrayList blist = null;
        DataBox dbox = null;
        DataBox dbox2 = null;
        String v_etestsubj_bef = "";

        String ss_upperclass = box.getStringDefault("s_upperclass","ALL");
        String ss_etestsubj       = box.getString("s_etestsubj");
        String ss_etestcode       = box.getString("s_etestcode");
        String ss_gyear       = box.getString("s_gyear");
        String v_action      = box.getStringDefault("p_action","change");

        try {
            connMgr = new DBConnectionManager();
            
            blist = new ArrayList();

            if (v_action.equals("go")) {
                sql = "select b.etestsubj,      b.upperclass,  a.etestcode, a.year, a.etestnum, a.etest, a.wherepaper,  a.startdt, a.enddt, a.etestlimit, a.totalscore, a.etestpoint,  a.etestcnt,  ";
                sql+= "       a.level1text, a.level2text, a.level3text, a.cntlevel1, a.cntlevel2, a.cntlevel3, a.isopenanswer, a.isopenexp,  a.etesttime,   ";
                sql+= "       b.etestsubjnm   \n";
                sql+= " from tz_etestpaper a,  tz_etestsubj b                        \n";
				sql+= " where   a.etestsubj           = b.etestsubj                  \n";	// 기존 outer join을 없앰 2006.1.18 년도조회시 없는값이 출력됨.
                if (!ss_upperclass.equals("ALL"))
                    sql+= "   and b.upperclass = " + SQLString.Format(ss_upperclass);
                if (!ss_etestsubj.equals("ALL"))
                    sql+= "   and b.etestsubj       = " + SQLString.Format(ss_etestsubj);
                if (!ss_etestcode.equals("ALL"))
                    sql+= "   and a.etestcode       = " + SQLString.Format(ss_etestcode);
                    sql+= "   and a.year       = " + SQLString.Format(ss_gyear);
                sql+= " order by b.etestsubj, a.year ";

                ls = connMgr.executeQuery(sql);
				System.out.println("ETestPaperBean E-test 시험지 리스트:"+sql);
                
                sql2 = "select count(userid) from tz_etestmember where etestsubj = ? and etestcode = ? and year = ? ";
                pstmt = connMgr.prepareStatement(sql2);

                while (ls.next()) {    
                    dbox = ls.getDataBox();
                    
                    if (!v_etestsubj_bef.equals(ls.getString("etestsubj"))) {
                        list = new ArrayList();
                        dbox2 = new DataBox("responsebox");
                        dbox2.put("d_etestsubj", ls.getString("etestsubj"));
                        dbox2.put("d_etestsubjnm", ls.getString("etestsubjnm"));
                        dbox2.put("d_upperclass", ls.getString("upperclass"));
                    }                    

                    dbox.put("d_etestsubj", dbox2.getString("d_etestsubj"));
                    dbox.put("d_etestsubjnm", dbox2.getString("d_etestsubjnm"));
                    dbox.put("d_upperclass", dbox2.getString("d_upperclass"));
                    
                    
                    //--------------------------------------------------------------------------------------------
                    pstmt.setString(1, dbox.getString("d_etestsubj"));
                    pstmt.setString(2, dbox.getString("d_etestcode"));
                    pstmt.setString(3, dbox.getString("d_year"));
                    try{
                        rs = pstmt.executeQuery(); 
                        rs.next();
                        dbox.put("d_membercnt", new Integer(rs.getInt(1)));
                    }catch(Exception e) {
                    }finally {
                        if (rs != null) { try { rs.close(); } catch (Exception e) {} }
                    }
                    //---------------------------------------------------------------------------------------------
                    
                    list.add(dbox);

                    if (!v_etestsubj_bef.equals(dbox.getString("d_etestsubj"))) {
                        blist.add(list);
                        v_etestsubj_bef = dbox.getString("d_etestsubj");
                    }                               
                }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return blist;
    }


    /**
    온라인테스트 시험지 상세보기
    @param box                receive from the form object and session
    @return ETestPaperData  조회한 시험지정보
    */
    public DataBox selectETestPaperData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;

        String v_etestsubj    = box.getString("p_etestsubj");
        String v_gyear    = box.getString("p_gyear");
        String v_etestcode = box.getString("p_etestcode");

        try {
            connMgr = new DBConnectionManager();
            dbox = getETestPaperData(connMgr, v_etestsubj, v_etestcode, v_gyear);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }


    /**
    온라인테스트 시험지 데이타 
    @param box                receive from the form object and session
    @return DataBox
    */
    public DataBox getETestPaperData(DBConnectionManager connMgr, String p_etestsubj, String p_etestcode, String p_gyear) throws Exception {
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        try {
            sql = "select a.etestsubj,     a.etestcode, a.year,  a.etesttext, a.startdt, a.enddt, a.etestlimit, a.totalscore, a.etestpoint,  a.etestcnt,  ";
            sql+= "       a.level1text, a.level2text, a.level3text, a.cntlevel1, a.cntlevel2, a.cntlevel3, a.isopenanswer, a.isopenexp,  a.papercnt , a.etesttime    ";
            sql+= "  from tz_etestmaster a ";
            sql+= " where a.etestsubj    = " + SQLString.Format(p_etestsubj);
            sql+= "   and a.etestcode  = " + SQLString.Format(p_etestcode);
            sql+= "   and a.year  = " + SQLString.Format(p_gyear);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
        }
        return dbox;
    }


    /**
    온라인테스트 시험지 등록  ##
    @param box                receive from the form object and session
    @return int
    */
    public int insertETestPaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        ListSet ls = null;
		DataBox dbox = null;
        int isOk = 0;

        int cnt = 0;
        int next = 0;

		Vector v_examnums = null;

        String v_etestsubj          = box.getString("p_etestsubj");
        String v_gyear          = box.getString("p_gyear");
        String v_etestcode       = box.getString("p_etestcode");

        String v_luserid    = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            
            connMgr.setAutoCommit(false);

            sql = "select a.etestsubj,      a.etestcode,  a.year,   a.etesttext,   a.startdt,   a.enddt,    a.etestlimit,    a.totalscore,    a.etestpoint,   a.etestcnt, ";
            sql+= "       a.cntlevel1, a.cntlevel2, a.cntlevel3, a.level1text, a.level2text, a.level3text, ";
            sql+= "       a.isopenanswer,  a.isopenexp, a.papercnt, a.etesttime ";
            sql+= "  from tz_etestmaster a ";
            sql+= " where a.etestsubj    = " + SQLString.Format(v_etestsubj);
            sql+= " and a.etestcode    = " + SQLString.Format(v_etestcode);
            sql+= " and a.year    = " + SQLString.Format(v_gyear);
    
            ls = connMgr.executeQuery(sql); 

            while (ls.next()) {
                dbox = ls.getDataBox();

                 String v_etesttext    = dbox.getString("d_etesttext");
                 String v_startdt     = dbox.getString("d_startdt");
                 String v_enddt = dbox.getString("d_enddt");
                 int v_etestlimit = dbox.getInt   ("d_etestlimit");
                 int v_totalscore = dbox.getInt   ("d_totalscore");
				 int v_etestpoint = dbox.getInt   ("d_etestpoint");
                 int v_etestcnt = dbox.getInt   ("d_etestcnt");
                 String v_level1text  = dbox.getString("d_level1text");
                 String v_level2text  = dbox.getString("d_level2text");
                 String v_level3text  = dbox.getString("d_level3text");
                 int v_cntlevel1  = dbox.getInt("d_cntlevel1");
                 int v_cntlevel2  = dbox.getInt("d_cntlevel2");
                 int v_cntlevel3  = dbox.getInt("d_cntlevel3");
                 String v_isopenanswer = dbox.getString("d_isopenanswer");
                 String v_isopenexp  = dbox.getString("d_isopenexp");
                 int v_papercnt = dbox.getInt("d_papercnt");
                 int v_etesttime = dbox.getInt("d_etesttime");

				 for ( int v_papernum = 1; v_papernum <= v_papercnt ; v_papernum++ ) {
				//온라인테스트 시험지 문제번호 구하기 
                v_examnums = getETestnums(connMgr, v_etestsubj, v_gyear, v_etestcode, box);

                 String v_examnums1 = "";
                 for (int i=0; i < v_examnums.size(); i++) {
                      v_examnums1 += (String)v_examnums.get(i);
                      if (i<v_examnums.size()-1) {
                            v_examnums1 += ",";
                      }
                 }
				System.out.println("문제번호 구하기 v_examnums1: "+v_examnums1);
				String v_wherepaper = "";

				// 평가문제 등록
				System.out.println("insertTZ_etestpaper(connMgr, v_etestsubj, v_gyear, v_etestcode, v_papernum, v_examnums1, v_wherepaper, v_startdt, v_enddt, v_etestlimit, v_totalscore, v_etestpoint, v_etestcnt, v_cntlevel1, v_cntlevel2, v_cntlevel3, v_level1text, v_level2text, v_level3text, v_isopenanswer, v_isopenexp, v_etesttime, v_luserid);"+
						connMgr+"/"+ v_etestsubj+"/"+ v_gyear+"/"+ v_etestcode+"/"+ v_papernum+"/"+ v_examnums1+"/"+ v_wherepaper+"/"+ v_startdt+"/"+ v_enddt+"/"+ v_etestlimit+"/"+ v_totalscore+"/"+v_etestpoint+"/"+ v_etestcnt+"/"+ v_cntlevel1+"/"+ v_cntlevel2+"/"+ v_cntlevel3+"/"+ v_level1text+"/"+ v_level2text+"/"+ v_level3text+"/"+ v_isopenanswer+"/"+v_isopenexp+"/"+ v_etesttime+"/"+ v_luserid);
                isOk = insertTZ_etestpaper(connMgr, v_etestsubj, v_gyear, v_etestcode, v_papernum, v_examnums1, v_wherepaper, v_startdt, v_enddt, v_etestlimit, v_totalscore, v_etestpoint, v_etestcnt, v_cntlevel1, v_cntlevel2, v_cntlevel3, v_level1text, v_level2text, v_level3text, v_isopenanswer, v_isopenexp, v_etesttime, v_luserid);

				if(isOk > 0){
                      //jsp 파일 만들기 -- 추후 작업 요망
					System.out.println("ETestPaperBean ## jsp 파일 만들기 insertETestPaper -> makeFileEtestPaper");
					System.out.println("makeFileEtestPaper(box, v_papernum, v_examnums1, v_examnums)/n"+
							 v_papernum+"/"+ v_examnums1+"/"+ v_examnums);					
                    v_wherepaper  = this.makeFileEtestPaper(box, v_papernum, v_examnums1, v_examnums);
					 
					System.out.println("updateTZ_etestpaper(connMgr, v_etestsubj, v_gyear, v_etestcode, v_papernum, v_examnums1, v_wherepaper, v_startdt, v_enddt, v_etestlimit, v_totalscore, v_etestpoint, v_etestcnt, v_cntlevel1, v_cntlevel2, v_cntlevel3, v_level1text, v_level2text, v_level3text, v_isopenanswer, v_isopenexp, v_etesttime, v_luserid)"+
							v_etestsubj+"/"+ v_gyear+"/"+ v_etestcode+"/"+ v_papernum+"/"+ v_examnums1+"/"+ v_wherepaper+"/"+ v_startdt+"/"+ v_enddt+"/"+ v_etestlimit+"/"+ v_totalscore+"/"+ v_etestpoint+"/"+ v_etestcnt+"/"+ v_cntlevel1+"/"+ v_cntlevel2+"/"+ v_cntlevel3+"/"+ v_level1text+"/"+ v_level2text+"/"+ v_level3text+"/"+ v_isopenanswer+"/"+ v_isopenexp+"/"+ v_etesttime+"/"+ v_luserid);
					isOk = updateTZ_etestpaper(connMgr, v_etestsubj, v_gyear, v_etestcode, v_papernum, v_examnums1, v_wherepaper, v_startdt, v_enddt, v_etestlimit, v_totalscore, v_etestpoint, v_etestcnt, v_cntlevel1, v_cntlevel2, v_cntlevel3, v_level1text, v_level2text, v_level3text, v_isopenanswer, v_isopenexp, v_etesttime, v_luserid);
                 	 this.makeFileForEtestResultPaper(box, v_papernum, v_examnums1, v_examnums);
				}
						cnt += isOk;
                        next++;				
				}
            }

            if (next == cnt) {
                connMgr.commit();
                isOk = cnt;
            }
            else {
                connMgr.rollback();
                isOk = -1;
            }
        }
        catch(Exception ex) {
            isOk = 0;
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (isOk > 0) {connMgr.commit();}
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

     /**
    이테스트 평가문제지 Jsp 생성
    @param box          receive from the form object and session
    */
    public String makeFileEtestPaper(RequestBox box, int p_etestnum, String p_etest, Vector p_etestnums) throws Exception {
       ArrayList blist = null;
        StringBuffer sb = null;
        PrintWriter writer = null;
		DataBox dbox0 = null;
		String v_wherepaper = "";

        try {
			blist = this.SelectPaperQuestionExampleList2(box, p_etestnums); 

			dbox0 = this.selectETestPaperData(box);               

	       String v_etestsubj     = dbox0.getString("d_etestsubj"); 
	       String v_etestcode     = dbox0.getString("d_etestcode"); 
           String v_gyear     = dbox0.getString("d_year"); 
           int v_etestcnt = dbox0.getInt("d_etestcnt"); 
           int v_etestpoint = dbox0.getInt("d_etestpoint"); 
           int    v_etesttime  =  dbox0.getInt("d_etesttime"); 
           String v_etestext = dbox0.getString("d_etesttext"); 
           int    v_etestnum  =  p_etestnum;
           
           String v_enddt = dbox0.getString("d_enddt");
		   
		   // 문화콘텐츠/게임 구분값으로 사용 
		   String v_grcode = box.getString("s_grcode");
 
		   String v_etest = p_etest;

            //String v_updir = conf.getProperty("dir.upload.etest");    
            String v_updir = conf.getProperty("url.upload")+"etest/";//jkh 0224 수정    
            sb = new StringBuffer();
            			
            sb.append(" <%@ page contentType = 'text/html;charset=MS949' %>                                \r\n");
            sb.append(" <%@ page errorPage = '/learn/library/error.jsp' %>                                    \r\n");
            sb.append(" <%@ page import = 'java.util.*' %>                                                  \r\n");
            sb.append(" <%@ page import = 'java.text.*' %>                                                  \r\n");
            sb.append(" <%@ page import = 'com.credu.system.*' %>                                           \r\n");
            sb.append(" <%@ page import = 'com.credu.library.*' %>                                          \r\n");
            sb.append(" <%@ page import = 'com.credu.etest.*' %>                                            \r\n");
            sb.append(" <jsp:useBean id = 'conf' class = 'com.credu.library.ConfigSet'  scope = 'page' />   \r\n");
            sb.append(" <%                                                                                  \r\n");
            sb.append("      RequestBox box = null;                                                     \r\n");
            sb.append("      box = (RequestBox)request.getAttribute(\"requestbox\");                     \r\n");
			sb.append("      String v_started = FormatDate.getDate(\"yyyyMMddHHmmss\");                  \r\n");
			sb.append("      String v_enddt = \"" + v_enddt + "\"; ");
			// 이부분은 이렇게 수정한다 2006.1.
			// form에 action에 직접 jsp파일을 호출하므로 box에서 꺼내지 않고 request객체에서 getParameter하여 꺼낸다.			
			//sb.append("		 v_enddt = box.getString(\"p_enddt\"); ");
			sb.append("		 v_enddt = request.getParameter(\"p_enddt\"); ");			
			sb.append("		 int v_etesttime = " + v_etesttime + ";    \r\n");
			sb.append("      try { int v_remaintime = FormatDate.getMinDifference(v_started+\"001\",v_enddt+\"001\")+2;  \r\n");
			sb.append("      if(v_remaintime < v_etesttime){     v_etesttime = v_remaintime; } }catch (Exception ex) { }   \r\n");
			// 이부분은 이렇게 수정한다 2006.1.
			// form에 action에 직접 jsp파일을 호출하므로 box에서 꺼내지 않고 request객체에서 getParameter하여 꺼낸다.			
			//sb.append("      String v_userid = box.getSession(\"userid\");                                       \r\n");
            sb.append("      String v_userid = request.getParameter(\"p_userid\");                                       \r\n");			
            sb.append(" %>                                                                                  \r\n");                        
            sb.append(" <html>                                                                   \r\n");
            sb.append("     <head>                                                                   \r\n");
			if(v_grcode.equals("N000001")){
				sb.append("         <title>::: 문화콘텐츠 :::</title>                                \r\n");
			}else if(v_grcode.equals("N000002")){
				sb.append("         <title>::: 게임아카데미 :::</title>                                \r\n");
			}else{
				sb.append("         <title>::: E-Test 시험 :::</title>                                \r\n");				
			}
            sb.append("         <meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>     \r\n");
            //sb.append("         <link rel='stylesheet' href='/css/user_style_research.css' type='text/css'> \r\n");
            sb.append("         <link href='/css/user_style_study.css' rel='stylesheet' type='text/css'> \r\n");			
            sb.append("         <script language = 'javascript' src = '/script/cresys_lib.js'></script>  \r\n");
            sb.append("         <script language = 'VBScript' src = '/script/cresys_lib.vbs'></script>   \r\n");
            sb.append("         <SCRIPT LANGUAGE='JavaScript'>                                           \r\n ");
            sb.append("         <!--                                                                     \r\n");
            sb.append("                                                       \r\n");
            sb.append("             function init() {                                            \r\n");
            sb.append("                    this.moveTo(0,0);                                   \r\n");
            sb.append("                    this.resizeTo(screen.availWidth,screen.availHeight);                                   \r\n");
            sb.append("                 var v_current = new Date();                                    \r\n");
            sb.append("                 var v_started = '';                                    \r\n");
            sb.append("                 var v_temp = '';                                    \r\n");
            sb.append("                                                     \r\n");
            sb.append("                 v_started += String(v_current.getFullYear());                                   \r\n");
            sb.append("                 v_temp = String((v_current.getMonth()+1));                                    \r\n");
            sb.append("                 if(v_temp.length == 1){                                    \r\n");
            sb.append("                          v_started += '0' + v_temp;                                   \r\n");
            sb.append("                 }else{                                    \r\n");
            sb.append("                          v_started += v_temp;                           \r\n");
            sb.append("                 }                                    \r\n");
            sb.append("                 v_temp = String(v_current.getDate());                                    \r\n");
            sb.append("                 if(v_temp.length == 1){                                    \r\n");
            sb.append("                          v_started += '0' + v_temp;                                 \r\n");
            sb.append("                 }else{                                    \r\n");
            sb.append("                          v_started += v_temp;                           \r\n");
            sb.append("                 }                                     \r\n");
            sb.append("                 v_temp = String(v_current.getHours());                                     \r\n");
            sb.append("                 if(v_temp.length == 1){                                     \r\n");
            sb.append("                          v_started += '0' + v_temp;                                  \r\n");
            sb.append("                 }else{                                     \r\n");
            sb.append("                          v_started += v_temp;                           \r\n");
            sb.append("                 }                                     \r\n");
            sb.append("                 v_temp = String(v_current.getMinutes());                                    \r\n");
            sb.append("                 if(v_temp.length == 1){                                     \r\n");
            sb.append("                          v_started += '0' + v_temp;                                  \r\n");
            sb.append("                 }else{                                   \r\n");
            sb.append("                          v_started += v_temp;                           \r\n");
            sb.append("                 }                                    \r\n");
            sb.append("                 v_temp = String(v_current.getSeconds());                                    \r\n");
            sb.append("                 if(v_temp.length == 1){                                     \r\n");
            sb.append("                          v_started += '0' + v_temp;                               \r\n");
            sb.append("                 }else{                                    \r\n");
            sb.append("                          v_started += v_temp;                           \r\n");
            sb.append("                 }                                     \r\n");
            sb.append("        document.form2.p_started.value = v_started;                                            \r\n");
            sb.append("             }                                            \r\n");
            sb.append("                                                       \r\n");
			sb.append("             var limit = <%=v_etesttime%>+':01'                                                   \r\n");
            sb.append("                                                                                             \r\n");
            sb.append("             if (document.images){                                            \r\n");
            sb.append("                var parselimit=limit.split(':')                                     \r\n");
            sb.append("                parselimit=parselimit[0]*60+parselimit[1]*1                                    \r\n");
            sb.append("             }                                        \r\n");
            sb.append("             function begintimer(){                                        \r\n");
            sb.append("                 if (!document.images)                                    \r\n");
            sb.append("                     return                                \r\n");
            sb.append("                 if (parselimit==1)                                    \r\n");
            sb.append("                     whenAutoSubmit();                                \r\n");
            sb.append("                 else{                                     \r\n");
            sb.append("                     parselimit-=1                               \r\n");
            sb.append("                     curmin=Math.floor(parselimit/60)                                \r\n");
            sb.append("                     cursec=parselimit%60                                \r\n");
            sb.append("                     if (curmin!=0)                                \r\n");
            sb.append("                          curtime=curmin+'분 '+cursec+'초 '                          \r\n");
            sb.append("                     else                                \r\n");
            sb.append("                          curtime='0 : '+cursec                          \r\n");
            sb.append("                     numberCountdown.innerText=curtime                                 \r\n");
            sb.append("                     setTimeout('begintimer()',1000)                                \r\n");
            sb.append("                 }                                     \r\n");
            sb.append("             }                                        \r\n");
            sb.append("                                                     \r\n");
            sb.append("             function whenAutoSubmit(){                                        \r\n");
            sb.append("                 var i=0;                                     \r\n");
            sb.append("                 var b_name='';                                     \r\n");
            sb.append("                 var c_name='';                                    \r\n");
            sb.append("                 var c_value='';                                    \r\n");
            sb.append("                 var b_type='';                                     \r\n");
            sb.append("                 var result='';                                    \r\n");
            sb.append("                 var temp='';                                      \r\n");
            sb.append("                 var textarr ='';                                     \r\n");
            sb.append("                 var answercnt=0;                                    \r\n");
            sb.append("                 var replycnt =0;                                    \r\n");
            sb.append("                                                     \r\n");
            sb.append("                 var v_current = new Date();                                    \r\n");
            sb.append("                 var v_ended = '';                                    \r\n");
            sb.append("                 var v_temp = '';                                    \r\n");
            sb.append("                                                     \r\n");
            sb.append("                 v_ended += String(v_current.getFullYear());                                   \r\n");
            sb.append("                 v_temp = String((v_current.getMonth()+1));                                    \r\n");
            sb.append("                 if(v_temp.length == 1){                                    \r\n");
            sb.append("                          v_ended += '0' + v_temp;                                   \r\n");
            sb.append("                 }else{                                    \r\n");
            sb.append("                          v_ended += v_temp;                           \r\n");
            sb.append("                 }                                    \r\n");
            sb.append("                 v_temp = String(v_current.getDate());                                    \r\n");
            sb.append("                 if(v_temp.length == 1){                                    \r\n");
            sb.append("                          v_ended += '0' + v_temp;                                 \r\n");
            sb.append("                 }else{                                    \r\n");
            sb.append("                          v_ended += v_temp;                           \r\n");
            sb.append("                 }                                     \r\n");
            sb.append("                 v_temp = String(v_current.getHours());                                     \r\n");
            sb.append("                 if(v_temp.length == 1){                                     \r\n");
            sb.append("                          v_ended += '0' + v_temp;                                  \r\n");
            sb.append("                 }else{                                     \r\n");
            sb.append("                          v_ended += v_temp;                           \r\n");
            sb.append("                 }                                     \r\n");
            sb.append("                 v_temp = String(v_current.getMinutes());                                    \r\n");
            sb.append("                 if(v_temp.length == 1){                                     \r\n");
            sb.append("                          v_ended += '0' + v_temp;                                  \r\n");
            sb.append("                 }else{                                   \r\n");
            sb.append("                          v_ended += v_temp;                           \r\n");
            sb.append("                 }                                    \r\n");
            sb.append("                 v_temp = String(v_current.getSeconds());                                    \r\n");
            sb.append("                 if(v_temp.length == 1){                                     \r\n");
            sb.append("                          v_ended += '0' + v_temp;                               \r\n");
            sb.append("                 }else{                                    \r\n");
            sb.append("                          v_ended += v_temp;                           \r\n");
            sb.append("                 }                                     \r\n");
            sb.append("                                                     \r\n");
            sb.append("                 for(i=0;i<document.form3.length;i++) {                                     \r\n");
            sb.append("                      c_name  = document.form3.elements[i].name;                               \r\n");
            sb.append("                      c_value = document.form3.elements[i].value;                               \r\n");
            sb.append("                      if (i == 0) {                               \r\n");
            sb.append("                             b_name=c_name;                        \r\n");
            sb.append("                      } else {                               \r\n");
            sb.append("                             if (c_name != b_name) {                         \r\n");
            sb.append("                                    answercnt++;                 \r\n");
            sb.append("                                    if (b_type=='text' || temp !='') {                 \r\n");
            sb.append("                                           replycnt++;          \r\n");
            sb.append("                                    }                 \r\n");
            sb.append("                                    if (answercnt==1) {                  \r\n");
            sb.append("                                           result = temp;         \r\n");
            sb.append("                                    } else {                 \r\n");
            //sb.append("                                           result = result + ' ,'+ temp;          \r\n");
            sb.append("                                           result = result + ','+ temp;          \r\n");
            sb.append("                                    }                 \r\n");
            sb.append("                                    b_name = c_name;                 \r\n");
            sb.append("                                    temp = '';                 \r\n");
            sb.append("                             }                        \r\n");
            sb.append("                      }                                \r\n");
            sb.append("                      if (document.form3.elements[i].type=='checkbox') {                               \r\n");
            sb.append("                             b_type='checkbox';                         \r\n");
            sb.append("                             if (document.form3.elements[i].checked==true) {                        \r\n");
            sb.append("                                   temp = temp+c_value+':';                  \r\n");
            sb.append("                             }else{                        \r\n");
            sb.append("                                   temp = 0;                  \r\n");
            sb.append("                             }                        \r\n");
            sb.append("                      } else if (document.form3.elements[i].type=='radio') {                               \r\n");
            sb.append("                             b_type='radio';                        \r\n");
            sb.append("                             if (document.form3.elements[i].checked==true) {                        \r\n");
            sb.append("                                   temp = c_value;                  \r\n");
            sb.append("                             }else{                        \r\n");
            sb.append("                                   if(temp.length < 1){ temp = 0; }else{  temp = temp;}                  \r\n");
            sb.append("                             }                        \r\n");
            sb.append("                      } else if (document.form3.elements[i].type=='text'||document.form3.elements[i].type=='textarea') {                               \r\n");
            sb.append("                             b_type='text';                        \r\n");
            sb.append("                             temp  = '';                        \r\n");
            sb.append("                             textarr = c_value.split(',');                        \r\n");
            sb.append("                             for(var j=0; j<textarr.length; j++) {                        \r\n");
            sb.append("                                   temp = temp + textarr[j];                  \r\n");
            sb.append("                             }                        \r\n");
            sb.append("                             temp = temp + ' ';                        \r\n");
            sb.append("                      }                               \r\n");
            sb.append("                 }                                    \r\n");
            sb.append("                 if (b_type=='text' || temp !='') {                                    \r\n");
            sb.append("                       replycnt++;                              \r\n");
            sb.append("                 }                                    \r\n");
            sb.append("                 answercnt++;                                    \r\n");
            sb.append("                 if (answercnt==1) {                                    \r\n");
            sb.append("                        result = temp;                                 \r\n");
            sb.append("                 } else {                                    \r\n");
            //sb.append("                        result = result + ' ,'+ temp + ' ,';                             \r\n");
            sb.append("                        result = result + ','+ temp + ',';                              \r\n");
            sb.append("                 }                                     \r\n");
            sb.append("                                                     \r\n");
            sb.append("        document.form2.p_answer.value = result;   //alert(result);                                          \r\n");
            sb.append("        document.form2.p_ended.value = v_ended;                                            \r\n");
            sb.append("        document.form2.p_process.value = 'ETestUserResultInsert';                                             \r\n");
            sb.append("        document.form2.submit();                                             \r\n");
            sb.append("        }                                                   \r\n");
            sb.append("        function whenSubmit(){                                            \r\n");
            sb.append("                 var i=0;                                    \r\n");
            sb.append("                 var b_name='';                                    \r\n");
            sb.append("                 var c_name='';                                    \r\n");
            sb.append("                 var c_value='';                                    \r\n");
            sb.append("                 var b_type='';                                     \r\n");
            sb.append("                 var result='';                                    \r\n");
            sb.append("                 var temp='';                                      \r\n");
            sb.append("                 var textarr ='';                                      \r\n");
            sb.append("                 var answercnt=0;                                     \r\n");
            sb.append("                 var replycnt =0;                                     \r\n");
            sb.append("                                                     \r\n");
            sb.append("                 var v_current = new Date();                                    \r\n");
            sb.append("                 var v_ended = '';                                    \r\n");
            sb.append("                 var v_temp = '';                                     \r\n");
            sb.append("                 v_ended += String(v_current.getFullYear());                                    \r\n");
            sb.append("                                                     \r\n");
            sb.append("                 v_temp = String((v_current.getMonth()+1));                                    \r\n");
            sb.append("                 if(v_temp.length == 1){                                     \r\n");
            sb.append("                          v_ended += '0' + v_temp;                                  \r\n");
            sb.append("                 }else{                                    \r\n");
            sb.append("                          v_ended += v_temp;                            \r\n");
            sb.append("                 }                                    \r\n");
            sb.append("                 v_temp = String(v_current.getDate());                                    \r\n");
            sb.append("                 if(v_temp.length == 1){                                    \r\n");
            sb.append("                          v_ended += '0' + v_temp;                                  \r\n");
            sb.append("                 }else{                                     \r\n");
            sb.append("                          v_ended += v_temp;                           \r\n");
            sb.append("                 }                                     \r\n");
            sb.append("                 v_temp = String(v_current.getHours());                                    \r\n");
            sb.append("                 if(v_temp.length == 1){                                    \r\n");
            sb.append("                          v_ended += '0' + v_temp;                                   \r\n");
            sb.append("                 }else{                                    \r\n");
            sb.append("                          v_ended += v_temp;                            \r\n");
            sb.append("                 }                                    \r\n");
            sb.append("                 v_temp = String(v_current.getMinutes());                                    \r\n");
            sb.append("                 if(v_temp.length == 1){                                    \r\n");
            sb.append("                          v_ended += '0' + v_temp;                                   \r\n");
            sb.append("                 }else{                                    \r\n");
            sb.append("                          v_ended += v_temp;                            \r\n");
            sb.append("                 }                                    \r\n");
            sb.append("                 v_temp = String(v_current.getSeconds());                                    \r\n");
            sb.append("                 if(v_temp.length == 1){                                     \r\n");
            sb.append("                          v_ended += '0' + v_temp;                                   \r\n");
            sb.append("                 }else{                                   \r\n");
            sb.append("                          v_ended += v_temp;                           \r\n");
            sb.append("                 }                                     \r\n");
            sb.append("                                                     \r\n");
            sb.append("                 for(i=0;i<document.form3.length;i++) {                                    \r\n");
            sb.append("                          c_name  = document.form3.elements[i].name;                            \r\n");
            sb.append("                          c_value = document.form3.elements[i].value;                           \r\n");
            sb.append("                          if (i == 0) {                             \r\n");
            sb.append("                                  b_name=c_name;                   \r\n");
            sb.append("                          } else {                           \r\n");
            sb.append("                                  if (c_name != b_name) {                   \r\n");
            sb.append("                                          answercnt++;           \r\n");
            sb.append("                                          if (b_type=='text' || temp !='') {           \r\n");
            sb.append("                                                replycnt++;     \r\n");
            sb.append("                                          }           \r\n");
            sb.append("                                          if (answercnt==1) {           \r\n");
            sb.append("                                                result = temp;     \r\n");
            sb.append("                                          } else {           \r\n");
            sb.append("                                                result = result + ','+ temp;     \r\n");
            sb.append("                                          }           \r\n");
            sb.append("                                                b_name = c_name;     \r\n");
            sb.append("                                                temp = '';     \r\n");
            sb.append("                                          }           \r\n");
            sb.append("                                  }                   \r\n");
            sb.append("                           if (document.form3.elements[i].type=='checkbox') {                           \r\n");
            sb.append("                                 b_type='checkbox';                    \r\n");
            sb.append("                                 if (document.form3.elements[i].checked==true) {                     \r\n");
            sb.append("                                          temp = temp+c_value+':';           \r\n");
            sb.append("                                 }                    \r\n");
            sb.append("                           } else if (document.form3.elements[i].type=='radio') {                           \r\n");
            sb.append("                                  b_type='radio';                   \r\n");
            sb.append("                                  if (document.form3.elements[i].checked==true) {                    \r\n");
            sb.append("                                          temp = c_value;           \r\n");
            sb.append("                                  }                   \r\n");
            sb.append("                           } else if (document.form3.elements[i].type=='text'||document.form3.elements[i].type=='textarea') {                          \r\n");
            sb.append("                                  b_type='text';                   \r\n");
            sb.append("                                  temp  = '';                    \r\n");
            sb.append("                                  textarr = c_value.split(',');                    \r\n");
            sb.append("                                  for(var j=0; j<textarr.length; j++) {                    \r\n");
            sb.append("                                         temp = temp + textarr[j];             \r\n");
            sb.append("                                  }                   \r\n");
            sb.append("                                  temp = temp + ' ';                   \r\n");
            sb.append("                           }                          \r\n");
            sb.append("                 }                                      \r\n");
            sb.append("                 if (b_type=='text' || temp !='') {                                    \r\n");
            sb.append("                          replycnt++;                           \r\n");
            sb.append("                 }                                    \r\n");
            sb.append("                 answercnt++;                                    \r\n");
            sb.append("                 if (answercnt==1) {                                     \r\n");
            sb.append("                         result = temp;                                \r\n");
            sb.append("                 } else {                                    \r\n");
            sb.append("                         result = result + ','+ temp + ',';                            \r\n");
            sb.append("                 }                                    \r\n");
            sb.append("                                                     \r\n");
            sb.append("                 if (replycnt < answercnt) {                                    \r\n");
            sb.append("                         alert(\"응답하지 않은 문제가 있습니다.모든 문제에 응답해주시길 바랍니다.\");                           \r\n");
            sb.append("                         return;                            \r\n");
            sb.append("                 }                                     \r\n");
            sb.append("                                                     \r\n");
            sb.append("        document.form2.p_answer.value = result;                                               \r\n");
            sb.append("        document.form2.p_ended.value = v_ended;                                             \r\n");
            sb.append("        document.form2.p_process.value = 'ETestUserResultInsert';                                             \r\n");
            sb.append("        document.form2.submit();                                            \r\n");
            sb.append("        }                                              \r\n");
            sb.append("         -->                                                          \r\n");
            sb.append("         </SCRIPT>                                                    \r\n");
            sb.append("                                                                                 \r\n");
            sb.append("     </head>                                                      \r\n");
//            sb.append("     <body onLoad='init();begintimer();'>                                                     \r\n");
		    sb.append(" <body leftmargin='0' topmargin='0' bottommargin='0' marginwidth='0' marginheight='0' onLoad='init();begintimer();'> ");
			if(v_grcode.equals("N000001")){
				sb.append("         <form name='form2' method='post' action='/servlet/controller.etest.KETestUserServlet'>\r\n");
			}else{
				sb.append("         <form name='form2' method='post' action='/servlet/controller.etest.ETestUserServlet'>\r\n");				
			}
            sb.append("             <input type='hidden' name='p_etestsubj'    value='" + v_etestsubj + "'>            \r\n");
            sb.append("             <input type='hidden' name='p_gyear'      value='" + v_gyear + "'>          \r\n");
            sb.append("             <input type='hidden' name='p_etestcode'    value='" + v_etestcode + "'>      \r\n");
            sb.append("             <input type='hidden' name='p_etestnum'      value='" + v_etestnum + "'>  \r\n");
            sb.append("             <input type='hidden' name='p_etest'      value='" + v_etest + "'>      \r\n");
            sb.append("             <input type='hidden' name='p_started'      value=''>      \r\n");
            sb.append("             <input type='hidden' name='p_etestcnt'      value='" + v_etestcnt + "'>      \r\n");
            sb.append("             <input type='hidden' name='p_etestpoint'      value='" + v_etestpoint + "'>      \r\n");
            sb.append("             <input type='hidden' name='p_ended'      value=''>  \r\n");
            sb.append("             <input type='hidden' name='p_answer'      value=''>                  \r\n");
            sb.append("             <input type='hidden' name='p_process'      value=''>                  \r\n");
            sb.append("         </form>                                                                   \r\n");
            sb.append("         <form name='form3' method='post' action='/servlet/controller.etest.ETestUserServlet'>                \r\n");
            /*sb.append("         <!----------------- 타이틀 시작 ----------------->                                                    \r\n");
            sb.append("             <table cellspacing='0' cellpadding='0' class='open_table_out' align='center'>                        \r\n");
            sb.append("                 <tr>                                                                                               \r\n");
            sb.append("                     <td background='/images/user/research/b_title_dot.gif'>                                         \r\n");
            sb.append("                     <img src='/images/user/study/exam_title1.gif' width='240' height='37' border='0'></td>          \r\n");
            sb.append("                     <td background='/images/user/research/b_title_dot.gif'>&nbsp;</td>                              \r\n");
            sb.append("                     <td background='/images/user/research/b_title_dot.gif' width= 100 align='right'>                \r\n");
            sb.append("                     <b><span id=time></span><b></td>                                                                      \r\n");
            sb.append("                 </tr>                                                                                              \r\n");
            sb.append("                 <tr><td height='7'></td></tr>                                                                      \r\n");
            sb.append("                 <tr><td></td><td height='7' align='right'></td></tr>                                                                                              \r\n");
            sb.append("             </table>                                                                                             \r\n");
            sb.append("             <!----------------- 타이틀 끝 ---------------->                                                      \r\n");
            sb.append("             <br>                                                                                                 \r\n");
            sb.append("             <table cellspacing='0' cellpadding='0' class='open_board_table_out1' align='center'>                 \r\n");
            sb.append("                 <tr>                                                                                                \r\n");
            sb.append("                     <td width='13'><img src='/images/user/research/icon2.gif' width='13' height='11' border='0'></td>\r\n");
            sb.append("                     <td><b>[테스트명] : <font color=blue>" + v_etestext + "</font> / [연도] : " + v_gyear + " </b></td>       \r\n");
            sb.append("                 </tr>                                                                                               \r\n");
            sb.append("                 <tr>                                                                                               \r\n");
            sb.append("                    <td height='5'></td>                                                                             \r\n");
            sb.append("                 </tr>                                                                                              \r\n");
            sb.append("             </table>                                                                                             \r\n");
            sb.append("             <table cellspacing='0' cellpadding='0' class='open_board_table_out1' align='center'>                      \r\n");
            sb.append("                 <tr>                                            \r\n");
            sb.append("                     <td width='23'></td><td align='left'>시험시간 - </td><td align='left'><b>"+v_etesttime+"분 00초</b></td>                                    \r\n");
            sb.append("                     <td align='right' width='85%'></td>                                   \r\n");
            sb.append("                 </tr>                                         \r\n");
            sb.append("                 <tr>                                         \r\n");
            sb.append("                     <td width='23'></td><td align='left'>남은시간 - </td><td align='left'><b><font color='red'><div id='numberCountdown'></div></font></b></td>                                    \r\n");
            sb.append("                     <td align='right' width='85%'></td>                                    \r\n");
            sb.append("                 </tr>                                        \r\n");
            sb.append("                 <tr>                                         \r\n");
            sb.append("                     <td width='23'></td><td align='left'>문제수 - </td><td align='left'><b>"+v_etestcnt+"</b></td>                                    \r\n");
            sb.append("                     <td align='right' width='85%'></td>                                     \r\n");
            sb.append("                 </tr>                                         \r\n");
            sb.append("                 <tr>                                          \r\n");
            sb.append("                     <td height='5'></td>                                     \r\n");
            sb.append("                 </tr>                                          \r\n");
            sb.append("             </table>                                             \r\n");
            sb.append("             <table cellspacing='0' cellpadding='0' class='open_board_table_out1' align='center'>                                            \r\n");
            sb.append("                 <tr>                                         \r\n");
            sb.append("                    <td width='13'></td>                                     \r\n");
            sb.append("                    <td><>시험시간이 경과하면 자동으로 시험지가 제출되오니 유의하시기 바랍니다. 주관식 문제의 경우 답을 적으실 때 띄어쓰기를 하지 마십시오.</td>                                      \r\n");
            sb.append("                </tr>                                         \r\n");
			sb.append("             </table>                                     \r\n");
            sb.append("             <!----------------- 학습평가 시작 ---------------->                                                  \r\n");
            sb.append("             <table cellspacing='0' cellpadding='0' class='open_board_table_out1' align='center'>                 \r\n");
            sb.append("                 <tr>                                                                                               \r\n");
            sb.append("                     <td class='board_color_line'></td>                                                               \r\n");
            sb.append("                 </tr>                                                                                              \r\n");
            sb.append("             </table>                                                                                             \r\n");
            sb.append("             <table cellspacing='0' cellpadding='0' class='open_board_table_out2' align='center'>                 \r\n");
            sb.append("                 <tr>                                                                                               \r\n");
            sb.append("                     <td>                                                                                             \r\n");
            sb.append("                         <table cellspacing='1' cellpadding='3' class='table2'>                                         \r\n");			
            */

            sb.append("             <table cellpadding='0' cellspacing='10' bgcolor='#EEEEEE' width='670' height='100%'>                                                     \r\n"); 
            sb.append("                 <tr>                                                                                                                                 \r\n"); 
            sb.append("                     <td>                                                                                                                             \r\n"); 
            sb.append("                         <table cellpadding='0' cellspacing='1' bgcolor='#BEBEBE' class='table2' height='100%'>                                       \r\n"); 
            sb.append("                             <tr>                                                                                                                     \r\n");
            sb.append("                                 <td align='center' valign='top' class='body_color'>                                                                  \r\n");
            sb.append("                                                                                                                                                      \r\n");
            sb.append("             <!----------------- 외곽   ---------------->                                                                                             \r\n");
            sb.append("             <table width='100%' border='0' cellspacing='0' cellpadding='0'>                                                                          \r\n");
            sb.append("             <!----------------- 타이틀   ---------------->                                                                                           \r\n");
            sb.append("               <tr>                                                                                                                                   \r\n");
            sb.append("                 <td >  &nbsp;  &nbsp; &nbsp; <img src='/images/user/etest/eTest_pop01.gif' ></td>                                                    \r\n");
            sb.append("               </tr>                                                                                                                                  \r\n");
            sb.append("               <tr>                                                                                                                                   \r\n");
            sb.append("                 <td>&nbsp;</td>                                                                                                                      \r\n");
            sb.append("               </tr>                                                                                                                                  \r\n");
            sb.append("             <!----------------- 타이틀 끝  ---------------->                                                                                         \r\n");
            sb.append("               <tr>                                                                                                                                   \r\n");
            sb.append("                 <td align='center'>                                                                                                                  \r\n");
            sb.append("                   <table border='0' cellpadding='0' cellspacing='0' width='630'  align='center'>                                                     \r\n");
            sb.append("                     <tr>                                                                                                                             \r\n");
            sb.append("                       <td valign='top' align='center'>                                                                                               \r\n");
            sb.append("             		  <table width='630' border='0' cellpadding='0' cellspacing='0' background='/images/user/etest/test_box_bg.gif'>                 \r\n");
            sb.append("                           <tr>                                                                                                                       \r\n");
            sb.append("                             <td><img src='/images/user/etest/test_box_top.gif' ></td>                                                                \r\n");
            sb.append("                           </tr>                                                                                                                      \r\n");
            sb.append("                           <tr>                                                                                                                       \r\n");
            sb.append("                             <td align='center'>                                                                                                      \r\n");
            sb.append("             				<table width='600' border='0' cellspacing='0' cellpadding='0'>                                                           \r\n");
            sb.append("                                 <tr>                                                                                                                 \r\n");
            sb.append("                                   <td width='87' rowspan='5'><img src='/images/user/etest/test_img.jpg'></td>                                        \r\n");
            sb.append("                                   <td width='73' height='33'><img src='/images/user/etest/text_name.gif'></td>                                       \r\n");
            sb.append("                                   <td width='226' class='tblfont_sindotit'>" + v_etestext + "</td>                                                   \r\n");
            sb.append("                                   <td width='61'><img src='/images/user/etest/text_year.gif'></td>                                                   \r\n");
            sb.append("                                   <td width='153' class='tblfont_gleft2'>" + v_gyear + " </td>                                                       \r\n");
            sb.append("                                 </tr>                                                                                                                \r\n");
            sb.append("                                 <tr>                                                                                                                 \r\n");
            sb.append("                                   <td height='5' colspan='4' background='/images/user/etest/stu_box_vline2.gif'></td>                                \r\n");
            sb.append("                                 </tr>                                                                                                                \r\n");
            sb.append("                                 <tr>                                                                                                                 \r\n");
            sb.append("                                   <td colspan='5' class='tblfont_gleft2'>시험시간 -  <b>"+v_etesttime+"분 00초</b></td>                              \r\n");
            sb.append("                                 </tr>                                                                                                                \r\n");
            sb.append("                                 <tr>                                                                                                                 \r\n");
            sb.append("                                   <td colspan='5' class='tblfont_gleft2'>남은시간 - <b><font color='red'><span id='numberCountdown'></span></font></b></td>  \r\n");  
			sb.append("                                 </tr>                                                                                                                        \r\n");
            sb.append("                                     <tr>                                                                                                                     \r\n");
            sb.append("             	                      <td colspan='5' class='tblfont_gleft2'>문제수 - <b>"+v_etestcnt+"</b></td>                                             \r\n");
            sb.append("                                     </tr>                                                                                                                    \r\n");
            sb.append("                                   </table>                                                                                                                   \r\n");
            sb.append("                                   </td>                                                                                                                      \r\n");
            sb.append("                               </tr>                                                                                                                          \r\n");
            sb.append("                               <tr>                                                                                                                           \r\n");
            sb.append("             	                <td><img src='/images/user/etest/test_box_bo.gif' ></td>                                                                     \r\n");
            sb.append("                               </tr>                                                                                                                          \r\n");
            sb.append("                           </table>                                                                                                                           \r\n");
            sb.append("                           </td>                                                                                                                              \r\n");
            sb.append("                         </tr>                                                                                                                                \r\n");
            sb.append("                         <tr>                                                                                                                                 \r\n");
            sb.append("                           <td height=8>                                                                                                                      \r\n");
            sb.append("                           <table width='100%' border='0' cellspacing='5' cellpadding='10'                                                                    \r\n");
            sb.append("                               <tr>                                                                                                                           \r\n");
            sb.append("                                 <td class='tblfont_gleft2'><b><> 시험시간이 경과하면 자동으로 시험지가 제출되오니 유의하시기 바랍니다.                       \r\n");
            sb.append("                                 <br> &nbsp; &nbsp;  주관식 문제의 경우 답을 적으실 때 띄어쓰기를 하지 마십시오. </b></td>                                    \r\n");
            sb.append("                               </tr>                                                                                                                          \r\n");
            sb.append("                           </table>                                                                                                                           \r\n");
            sb.append("                           </td>                                                                                                                              \r\n");
            sb.append("                         </tr>                                                                                                                                \r\n");
            sb.append("                         <tr>                                                                                                                                 \r\n");
            sb.append("                           <td>                                                                                                                               \r\n");
			sb.append("                           <!----------------- 학습평가 시작 ---------------->                                                                                \r\n");
			sb.append("                           <table width='630' border='0' cellspacing='1' cellpadding='0' class=boardskin1_out>                                                \r\n");
			sb.append("                           <tr>                                                                                                                               \r\n");
			sb.append("                               <td>                                                                                                                           \r\n"); 
			sb.append("                                   <table cellspacing='1' cellpadding='3' class='table2'>                                                                     \r\n");

            for (int i=0; i<blist.size(); i++) {
                ArrayList list = (ArrayList)blist.get(i);
                DataBox dbox = (DataBox)list.get(0);

                sb.append("                         <tr>                                                                                                                     \r\n");
                sb.append("                             <td class='board_title_bg0'>[" +(i+1)+ "] " + dbox.getString("d_etesttext") + "</td>                                                                                                                     \r\n");
                sb.append("                         </tr>                                                                                                                     \r\n");
                if (!dbox.getString("d_saveimage").equals("")) {        //      문제관련 이미지, 음성, 동영상 파일 시작
                    sb.append("                     <tr>                                                                                                                     \r\n");
                    sb.append("                         <td bgcolor='#FFFFFF'>                                                                                                 \r\n");
                    sb.append("                             <table cellspacing='0' cellpadding='0' class='img_table_out'>                                                        \r\n");
                    sb.append("                                 <tr>                                                                                                               \r\n");
                    sb.append("                                     <td>                                                                                                            \r\n");
                    sb.append("                                         <table cellspacing='2' cellpadding='2'>                                                                        \r\n");
                    sb.append("                                             <tr>                                                                                                         \r\n");
                    sb.append("                                                 <td class='img_table' height='26'><img src='" + v_updir+dbox.getString("d_saveimage") + "'></td>             \r\n");
                    sb.append("                                             </tr>                                                                                                        \r\n");
                    sb.append("                                         </table>                                                                                                       \r\n");
                    sb.append("                                     </td>                                                                                                            \r\n");
                    sb.append("                                 </tr>                                                                                                              \r\n");
                    sb.append("                             </table>                                                                                                             \r\n");
                    sb.append("                         </td>                                                                                                                  \r\n");
                    sb.append("                     </tr>                                                                                                                    \r\n");
                }
                if (!dbox.getString("d_saveaudio").equals("")) {
                    sb.append("                     <tr>                                                                                                          \r\n");
                    sb.append("                         <td class='board_text4'> <img src='/images/user/study/voice_icon1.gif' width='20' height='17' border='0'>              \r\n");
                    sb.append("                         <a href='" + v_updir+dbox.getString("d_saveaudio") + "' target='_new'>" + dbox.getString("d_realaudio") + "</a></td>        \r\n");
                    sb.append("                     </tr>                                                                                                                    \r\n");
                }
                if (!dbox.getString("d_savemovie").equals("")) {
                    sb.append("                     <tr>                                                                                                          \r\n");                                                                                                                                                                                                                         
                    sb.append("                         <td class='board_text3'> <img src='/images/user/study/meida_icon1.gif' width='20' height='17' border='0'>              \r\n");
                    sb.append("                         <a href='" + v_updir+dbox.getString("d_savemovie") + "' target='_new'>" + dbox.getString("d_realmovie") + "</a></td>      \r\n");
                    sb.append("                     </tr>                                                                                                                    \r\n");
                }
                if (dbox.getString("d_etesttype").equals(ETestBean.OBJECT_QUESTION)) {   // 객관식
                    for (int j=0; j < list.size(); j++) {
                        dbox  = (DataBox)list.get(j);
                        if (dbox != null) {
                            sb.append("             <tr>                                                                                                                     \r\n");
                            sb.append("                 <td class='board_text4'>                                                                                               \r\n");
                            sb.append("                 &nbsp;&nbsp;<input type='radio' name='" + dbox.getInt("d_etestnum") + "' value='" + dbox.getInt("d_selnum") +"' class='inputradio'>   \r\n");
                            sb.append(dbox.getInt("d_selnum") +"." + dbox.getString("d_seltext") + " </td>                                                  \r\n");
                         //   sb.append("                 <input type='hidden' name='" + dbox.getInt("d_etestnum") + "' value='" + StringManager.BASE64Encode(dbox.getString("d_isanswer")) + "'>\r\n");
                            sb.append("             </tr>                                                                                                                    \r\n");
                        }
                    }
                }else if (dbox.getString("d_etesttype").equals(ETestBean.MULTI_QUESTION)) {  // 다답식
                    for (int j=0; j < list.size(); j++) {
                        dbox  = (DataBox)list.get(j);
                        if (dbox != null) {
                            sb.append("             <tr>                                                                                                                     \r\n");
                            sb.append("                 <td class='board_text4'>                                                                                               \r\n");
                            sb.append("                 <input type='checkbox' name='" + dbox.getInt("d_etestnum") + "' value='" + dbox.getInt("d_selnum") + "' class='inputradio'>\r\n");
                            sb.append(dbox.getInt("d_selnum") + "." + dbox.getString("d_seltext") + "</td>                           \r\n");
                   //         sb.append("                 <input type='hidden' name='" + dbox.getInt("d_etestnum") + "' value='" + StringManager.BASE64Encode(dbox.getString("d_isanswer")) + "'>\r\n");
                            sb.append("             </tr>                                                                                             \r\n");
                        }
                    }
                }else if (dbox.getString("d_etesttype").equals(ETestBean.SUBJECT_QUESTION)) {  // 주관식
                    sb.append("                     <tr>                                                                                              \r\n");
                    sb.append("                         <td class='board_text3'>                                                                        \r\n");
                    sb.append("                         <textarea name='" + dbox.getInt("d_etestnum") + "' cols='83' rows='5' class='input'></textarea> \r\n");
              //      sb.append("                         <input type='hidden' name='" + dbox.getInt("d_etestnum") + "' value='" + StringManager.BASE64Encode(dbox.getString("d_seltext")) + "'>\r\n");
                    sb.append("                         </td>                                                                                           \r\n");
                    sb.append("                     </tr>                                                                                             \r\n");
                }
            }
            sb.append("                         </table>                                                                                            \r\n");
            sb.append("                     </td>                                                                                                 \r\n");
            sb.append("                 </tr>                                                                                                   \r\n");
            sb.append("             </table>                                                                                                  \r\n");
            sb.append("         </form>                                                                                             \r\n");
            sb.append("<!----------------- 학습평가 끝 ---------------->                                                         \r\n");
            sb.append("         <br>                                                                                                      \r\n");
            sb.append("<!----------------- 제출 버튼 시작 ---------------->                                                      \r\n");
            sb.append("         <table cellspacing='0' cellpadding='0' class='open_board_table_out1' align='center'>                      \r\n");
            sb.append("             <tr>                                                                                                  \r\n");
            sb.append("                 <td align='right'><a href='javascript:whenSubmit()'><img src='/images/user/study/presentation_butt1.gif' width='54' height='20' border='0'></a></td>\r\n");
            sb.append("                 <td align='right' width='65'><a href='javascript:self.close()'><img src='/images/user/study/close_butt.gif' width='54' height='20' border='0'></a></td>\r\n");
            sb.append("             </tr>                                         \r\n ");
            sb.append("         </table>                                           \r\n");
            sb.append("<!----------------- 제출 버튼 끝 ----------------->\r\n");
            sb.append("         <br>                                               \r\n");
            sb.append("                    </td>                                             \r\n");
            sb.append("                </tr>                                             \r\n");
            sb.append("            </table>                                             \r\n");
            sb.append("                                             \r\n");
            sb.append("        </td>                                             \r\n");
            sb.append("    </tr>                                             \r\n");
            sb.append("</table>                                             \r\n");
            sb.append("<!----------------- 외곽   ---------------->                                               \r\n");
            sb.append("                    </td>                                             \r\n");
            sb.append("                </tr>                                             \r\n");
            sb.append("            </table>                                             \r\n");
            sb.append("                                             \r\n");
            sb.append("        </td>                                             \r\n");
            sb.append("    </tr>                                             \r\n");
            sb.append("</table>                                             \r\n");
            sb.append("     <br></body>                                            \r\n");
            sb.append("</html>                                            \r\n");

            String dir = conf.getProperty("dir.etest.paper");
            String dirpath = conf.getProperty("dir.etest.paperpath");
            if(dirpath.length() < 2){ dirpath = "/dp/lms_data/etestpaper/"; }
            
            File directory = new java.io.File(dir + File.separator + v_gyear + "_" + v_etestsubj);  
            directory.mkdir();                                                                                                                 //      '연도_과정그룹' 디렉토리 생성

		    //String v_filename = v_etestsubj + v_gyear + v_etestcode + v_etestnum + ".html";
		    String v_filename = v_etestsubj + v_gyear + v_etestcode + v_etestnum + ".jsp";
            
            File jspFile = new File(directory, v_filename);
            if(jspFile.isFile()) jspFile.delete();
            java.io.FileWriter fw =  new java.io.FileWriter(jspFile.getAbsolutePath(), true);          
            writer = new PrintWriter(new java.io.BufferedWriter(fw), true);                  //         문제지 jsp 파일 생성

            writer.println(sb.toString());
            //jkh 0313
            writer.flush();
            fw.close();
            writer.close();

			//v_wherepaper = "/dp/lms_data/etestpaper/" + v_gyear + "_" + v_etestsubj + "/" + v_etestsubj + v_gyear + v_etestcode + v_etestnum + ".html";
			//v_wherepaper = dirpath + v_gyear + "_" + v_etestsubj + "/" + v_etestsubj + v_gyear + v_etestcode + v_etestnum + ".html";
			v_wherepaper = dirpath + v_gyear + "_" + v_etestsubj + "/" + v_etestsubj + v_gyear + v_etestcode + v_etestnum + ".jsp";
			System.out.println("v_wherepaper=="+v_wherepaper);
        }
        catch (Exception ex) {ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(writer != null) try{ writer.close(); }catch(Exception e1){ e1.printStackTrace(); }
        }
		return v_wherepaper;
    }

     /**
    이테스트 문제답안을 위한 .txt 생성
    @param box          receive from the form object and session
    */
    public void makeFileForEtestResultPaper(RequestBox box, int p_etestnum, String p_etest, Vector p_etestnums) throws Exception {
        ArrayList blist = null;
        StringBuffer sb = null;
        ETestPaperBean bean = null;
        PrintWriter writer = null;
        String txt = "";
		DataBox dbox0 = null;

        try {

			dbox0 = this.selectETestPaperData(box);               

	       String v_etestsubj     = dbox0.getString("d_etestsubj"); 
	       String v_etestcode     = dbox0.getString("d_etestcode"); 
           String v_gyear     = dbox0.getString("d_year"); 
           int v_etestcnt = dbox0.getInt("d_etestcnt"); 
           int v_etestpoint = dbox0.getInt("d_etestpoint"); 
           int    v_etesttime  =  dbox0.getInt("d_etesttime"); 
           String v_etestext = dbox0.getString("d_etesttext"); 
           int    v_etestnum  =  p_etestnum;
 
	      String v_etest = p_etest;

            String dir = conf.getProperty("dir.etest.resultpaper");             
      
			File directory = new java.io.File(dir + File.separator + v_gyear + "_" + v_etestsubj);                //      '연도_과정그룹' 디렉토리 생성
            directory.mkdir();          
            
            String v_filename = v_etestsubj + v_gyear + v_etestcode + v_etestnum + ".txt";
            
            File txtFile = new File(directory, v_filename);
            if(txtFile.isFile()) txtFile.delete();
            java.io.FileWriter fw =  new java.io.FileWriter(txtFile.getAbsolutePath(), true);          
            writer = new PrintWriter(new java.io.BufferedWriter(fw), true);                  //         답안 jsp 파일 생성

            blist = this.getETestPaperQuestion(v_etestsubj, v_etest);            
            
            for (int i=0; i<blist.size(); i++) {
                
                 txt += (String)blist.get(i) + ",";
                 System.out.println("txt=="+txt);
            }            
                writer.println(txt);

		}
        catch (Exception ex) {ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(writer != null) try{ writer.close(); }catch(Exception e1){ e1.printStackTrace(); }
        }
    }


    /**
    온라인테스트 시험지 문제 가져오기 
    @param box                receive from the form object and session
    @return ArrayList
    */
    public ArrayList getETestPaperQuestion(String p_etestsubj, String p_etest) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql  = "";
		ArrayList list = null;
        StringTokenizer st= null;	

        String v_examtype = "";

        try {

            connMgr = new DBConnectionManager();

			st = new StringTokenizer(p_etest, ",");

		  	list =  new ArrayList();

            while(st.hasMoreElements()) {

            int etestnum = Integer.parseInt(st.nextToken());

            sql = "select etesttype ";
            sql+= "  from tz_etest ";
            sql+= " where etestsubj      = " + SQLString.Format(p_etestsubj);
            sql+= " and etestnum        = " + SQLString.Format(etestnum);

			ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_examtype = ls.getString("etesttype");
            }

            if (v_examtype.length() > 0) {

				if (v_examtype.equals("1")) {

                sql = "select b.selnum sel  ";
                sql+= "  from tz_etest a, tz_etestsel b, tz_code c, tz_code d                                      ";
                sql += "   where b.isanswer    =   'Y'   ";
				// 수정일 : 05.11.04 수정자 : 이나연 _ (+) 수정
//              sql+= "   and   a.etestsubj     = b.etestsubj(+)                                                             ";
				sql+= "   and   a.etestsubj     =  b.etestsubj                                                            (+) ";
//              sql+= "   and a.etestnum   = b.etestnum(+)                                                          ";
				sql+= "   and a.etestnum   =  b.etestnum                                                         (+) ";
                sql+= "   and a.etesttype = c.code                                                                ";
                sql+= "   and a.levels   = d.code                                                                ";
                sql+= "   and a.etestsubj     = " + SQLString.Format(p_etestsubj);
                sql+= "   and a.etestnum  = " + etestnum;
                sql+= "   and c.gubun    = " + SQLString.Format(ETestBean.ETEST_TYPE);
                sql+= "   and d.gubun    = " + SQLString.Format(ETestBean.ETEST_LEVEL);
                sql+= " order by a.etestnum, b.selnum              ";

                ls.close();
                ls = connMgr.executeQuery(sql);

                   if (ls.next()) {
                       String v_sel = String.valueOf(ls.getInt("sel"));
                       list.add(v_sel);
    		      }
             	
				} else if (v_examtype.equals("2")) {
				
                sql = "select b.seltext  ";
                sql+= "  from tz_etest a, tz_etestsel b, tz_code c, tz_code d                                      ";
				// 수정일 : 05.11.04 수정자 : 이나연 _ (+) 수정
//              sql+= "   where   a.etestsubj     = b.etestsubj(+)                                                             ";
//              sql+= "   and a.etestnum  = b.etestnum(+)                                                          ";
				sql+= "   where   a.etestsubj     =  b.etestsubj                                                            (+) ";
                sql+= "   and a.etestnum  		  =  b.etestnum                                                         (+) ";
                sql+= "   and a.etesttype = c.code                                                                ";
                sql+= "   and a.levels   = d.code                                                                ";
                sql+= "   and a.etestsubj     = " + SQLString.Format(p_etestsubj);
                sql+= "   and a.etestnum  = " + etestnum;
                sql+= "   and c.gubun    = " + SQLString.Format(ETestBean.ETEST_TYPE);
                sql+= "   and d.gubun    = " + SQLString.Format(ETestBean.ETEST_LEVEL);
                sql+= " order by a.etestnum, b.selnum              ";

                ls.close();
                ls = connMgr.executeQuery(sql);

                   String v_seltext = "";
                   while (ls.next()) {
                       //v_seltext += String.valueOf(ls.getInt("seltext")) + "|";
                       v_seltext += ls.getString("seltext"); //주관식인 경우 수자가 아님 0302 jkh
    		       }		
                       list.add(v_seltext);				
				}

            }
			ls.close();
		  }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) {} }
        }

        return list;
    }



    /**
    온라인테스트 시험지 문제번호 구하기 
    @param box                receive from the form object and session
    @return Vector
    */
    public Vector getETestnums(DBConnectionManager connMgr, String p_etestsubj, String p_gyear, String p_etestcode, RequestBox box) throws Exception {
   
		ArrayList list = null;
		ArrayList list2 = null;
		Vector v_lessons = null;
        Vector v_examnums = new Vector();
		Vector v = null;
        Vector v_realnums = null;
        Vector v_realrkeys = null; //jkh 0228
		Vector v_level1Obnums = null;
		Vector v_level2Obnums = null;
		Vector v_level3Obnums = null;
		Vector v_level1Subnums = null;
		Vector v_level2Subnums = null;
		Vector v_level3Subnums = null;


		try {
	          list = getLevelQuestionList(connMgr, p_etestsubj, p_gyear, p_etestcode, box);
			  //System.out.println("난이도별 리스트 list:"+list);

              v_level1Obnums = (Vector)list.get(0);
              v_level2Obnums = (Vector)list.get(1);
              v_level3Obnums = (Vector)list.get(2);
              v_level1Subnums = (Vector)list.get(3);
              v_level2Subnums = (Vector)list.get(4);
              v_level3Subnums = (Vector)list.get(5);

              v_realnums = new Vector();
              v_realrkeys = new Vector();
              String numandrkey =""; 
              StringTokenizer st1= null;	

				  for (int j = 1; j <= 2; j++){
					  Integer type = new Integer(j);
					  for (int k = 1; k <= 3 ; k++){
						  Integer level = new Integer(k);

             			  v = getQuestionList(connMgr, p_etestsubj, type.toString() , level.toString());
						 Random ran = new Random();
					
						  if(j==1 && k ==1){
						     int  ss = Integer.parseInt((String)v_level1Obnums.get(0));
							 if(v.size() > 0 && ss > 0){
							     for (int p=0; p < ss ; p++){
							     	numandrkey = (String)v.get(p); 
							     	st1 = new StringTokenizer(numandrkey, ",");
							     	v_realnums.add(st1.nextToken());
							     	v_realrkeys.add(st1.nextToken());
								 }
							 }
						  } else if(j==1 && k ==2){
						     int  ss = Integer.parseInt((String)v_level2Obnums.get(0));
							 if(v.size() > 0 && ss > 0){
							     for (int p=0; p < ss ; p++){
									 numandrkey = (String)v.get(p); 
							     	st1 = new StringTokenizer(numandrkey, ",");
							     	v_realnums.add(st1.nextToken());
							     	v_realrkeys.add(st1.nextToken());
							     }
							 }
						  } else if(j==1 && k ==3){
						     int  ss = Integer.parseInt((String)v_level3Obnums.get(0));
							 if(v.size() > 0 && ss > 0){
							     for (int p=0; p < ss ; p++){
									 numandrkey = (String)v.get(p); 
							     	st1 = new StringTokenizer(numandrkey, ",");
							     	v_realnums.add(st1.nextToken());
							     	v_realrkeys.add(st1.nextToken());
							     }
							 }
						  } else if(j==2 && k ==1){
						     int  ss = Integer.parseInt((String)v_level1Subnums.get(0));
							 if(v.size() > 0 && ss > 0){
							     for (int p=0; p < ss ; p++){
									 numandrkey = (String)v.get(p); 
							     	st1 = new StringTokenizer(numandrkey, ",");
							     	v_realnums.add(st1.nextToken());
							     	v_realrkeys.add(st1.nextToken());
							     }
							 }
						  } else if(j==2 && k ==2){
						     int  ss = Integer.parseInt((String)v_level2Subnums.get(0));
							 if(v.size() > 0 && ss > 0){
							     for (int p=0; p < ss ; p++){
									 numandrkey = (String)v.get(p); 
							     	st1 = new StringTokenizer(numandrkey, ",");
							     	v_realnums.add(st1.nextToken());
							     	v_realrkeys.add(st1.nextToken());
							     }
							 }
						  } else if(j==2 && k ==3){
						     int  ss = Integer.parseInt((String)v_level3Subnums.get(0));
							 if(v.size() > 0 && ss > 0){
							     for (int p=0; p < ss ; p++){
									 numandrkey = (String)v.get(p); 
							     	st1 = new StringTokenizer(numandrkey, ",");
							     	v_realnums.add(st1.nextToken());
							     	v_realrkeys.add(st1.nextToken());
							     }
							 }
						  }
					  }
				  }
							
			  				int  ss = v_realnums.size();
			  				
			  				/*
							 //랜덤 구하기
							 Random ran = new Random();
                             int [] num =new int [ss];
							 int bun = 0;

                             for(int q = 0 ; q < ss ; q++){ 
                                 bun = ran.nextInt(ss); 
                                 for(int a = 0 ; a < q ; a++){ 
                                 	  int breakint = 0;
                                      while(num[a]==bun) { 
                                              bun = ran.nextInt(ss); 
                                              breakint++;
                                              if(breakint > 10000){ break; }
                                      } 
                                 } 
								 num[q] = bun;
							 }
							 */
							 
							 //랜덤 구하기
							 Random ran = new Random();
                             int [] num =new int [ss];
							 int bun = 0;
							
                             for(int q = 0 ; q < ss ; q++){ 
                                 bun = ran.nextInt(ss); 
                                 
                                 	  int breakint = 0;
                                 	  int isequal = 0;
                                      while(isequal < 1) { 
                                      	bun = ran.nextInt(ss); 
                                      	for(int a = 0 ; a < q ; a++){ 
                                      		if(	num[a]==bun){ 
                                      			isequal = 0;
                                      			break;
                                      		}else{ 	isequal = 1;  }
                                      	}	
                                              breakint++;
                                              if(breakint > 10000){ break; }
                                      } 
                                  
								 num[q] = bun;
							 }
							 
							 /*
							 
							 	int[] a = {1. 2. 3. 4. 5};
								int[] b = new int[5];
								Random rnd = new Random();
								
								for(int i = o; i < a.length; i++){
									 int x = 1 + Math.abs(rnd.nextInt())%5;
									 b[i] = x;
									 for(int j = x; j < a.length-1; j++){
									 	a[j] = a[j+1];
									 }
								}
							 */
							 

							 for (int p=0; p < ss ; p++){
								 v_examnums.add((String)v_realnums.get(num[p]));
							 }

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return v_examnums;
        //return v_realnums;
    }

	
	 /**
    온라인테스트 시험지 문제번호 구하기 (구버전)
    @param box                receive from the form object and session
    @return Vector
    */
    public Vector getETestnumsOld(DBConnectionManager connMgr, String p_etestsubj, String p_gyear, String p_etestcode, RequestBox box) throws Exception {
   
		ArrayList list = null;
		ArrayList list2 = null;
		Vector v_lessons = null;
        Vector v_examnums = new Vector();
		Vector v = null;
        Vector v_realnums = null;
		Vector v_level1Obnums = null;
		Vector v_level2Obnums = null;
		Vector v_level3Obnums = null;
		Vector v_level1Subnums = null;
		Vector v_level2Subnums = null;
		Vector v_level3Subnums = null;


		try {
	          list = getLevelQuestionList(connMgr, p_etestsubj, p_gyear, p_etestcode, box);

              v_level1Obnums = (Vector)list.get(0);
              v_level2Obnums = (Vector)list.get(1);
              v_level3Obnums = (Vector)list.get(2);
              v_level1Subnums = (Vector)list.get(3);
              v_level2Subnums = (Vector)list.get(4);
              v_level3Subnums = (Vector)list.get(5);

              v_realnums = new Vector();

				  for (int j = 1; j <= 2; j++){
					  Integer type = new Integer(j);
					  for (int k = 1; k <= 3 ; k++){
						  Integer level = new Integer(k);

             			  v = getQuestionList(connMgr, p_etestsubj, type.toString() , level.toString());
						 Random ran = new Random();
					
						  if(j==1 && k ==1){
						     int  ss = Integer.parseInt((String)v_level1Obnums.get(0));
							 //랜덤 구하기

							 if(v.size() > 0 && ss > 0){
                             int [] num =new int [ss];
							 int bun = 0;
                          //   num[0] = ran.nextInt(v.size()); 
	                             for(int q = 0 ; q < ss ; q++){ 
									 bun = ran.nextInt(v.size()); 
	                                // num[q] = ran.nextInt(v.size()); 
	                                 for(int a = 0 ; a < q ; a++){ 
	                                      while(num[a]==bun) { 
	                                              bun = ran.nextInt(v.size()); 
	                                      } 
	                                 } 
	
									 num[q] = bun;
	                             }
							     for (int p=0; p < ss ; p++){
									 v_realnums.add((String)v.get(num[p]));
							     }
							 }

						  } else if(j==1 && k ==2){
						     int  ss = Integer.parseInt((String)v_level2Obnums.get(0));
							 //랜덤 구하기
							 if(v.size() > 0 && ss > 0){
                             int [] num =new int [ss];
							 int bun = 0;
                          //   num[0] = ran.nextInt(v.size()); 
                             for(int q = 0 ; q < ss ; q++){ 
								 bun = ran.nextInt(v.size()); 
                                // num[q] = ran.nextInt(v.size()); 
                                 for(int a = 0 ; a < q ; a++){ 
                                      while(num[a]==bun) { 
                                              bun = ran.nextInt(v.size()); 
                                      } 
                                 } 

								 num[q] = bun;
                             }
							     for (int p=0; p < ss ; p++){
									 v_realnums.add((String)v.get(num[p]));
							     }
							 }
						  } else if(j==1 && k ==3){
						     int  ss = Integer.parseInt((String)v_level3Obnums.get(0));
							 //랜덤 구하기
							 if(v.size() > 0 && ss > 0){
                             int [] num =new int [ss];
							 int bun = 0;
                          //   num[0] = ran.nextInt(v.size()); 
                             for(int q = 0 ; q < ss ; q++){ 
								 bun = ran.nextInt(v.size()); 
                                // num[q] = ran.nextInt(v.size()); 
                                 for(int a = 0 ; a < q ; a++){ 
                                      while(num[a]==bun) { 
                                              bun = ran.nextInt(v.size()); 
                                      } 
                                 } 

								 num[q] = bun;
                             }
							     for (int p=0; p < ss ; p++){
									 v_realnums.add((String)v.get(num[p]));
							     }
							 }
						  } else if(j==2 && k ==1){
						     int  ss = Integer.parseInt((String)v_level1Subnums.get(0));
							 //랜덤 구하기
							 if(v.size() > 0 && ss > 0){
                             int [] num =new int [ss];
							 int bun = 0;
                          //   num[0] = ran.nextInt(v.size()); 
                             for(int q = 0 ; q < ss ; q++){ 
								 bun = ran.nextInt(v.size()); 
                                // num[q] = ran.nextInt(v.size()); 
                                 for(int a = 0 ; a < q ; a++){ 
                                      while(num[a]==bun) { 
                                              bun = ran.nextInt(v.size()); 
                                      } 
                                 } 

								 num[q] = bun;
                             }
							     for (int p=0; p < ss ; p++){
									 v_realnums.add((String)v.get(num[p]));
							     }
							 }
						  } else if(j==2 && k ==2){
						     int  ss = Integer.parseInt((String)v_level2Subnums.get(0));
							 //랜덤 구하기
							 if(v.size() > 0 && ss > 0){
                             int [] num =new int [ss];
							 int bun = 0;
                          //   num[0] = ran.nextInt(v.size()); 
                             for(int q = 0 ; q < ss ; q++){ 
								 bun = ran.nextInt(v.size()); 
                                // num[q] = ran.nextInt(v.size()); 
                                 for(int a = 0 ; a < q ; a++){ 
                                      while(num[a]==bun) { 
                                              bun = ran.nextInt(v.size()); 
                                      } 
                                 } 

								 num[q] = bun;
                             }
							     for (int p=0; p < ss ; p++){
									 v_realnums.add((String)v.get(num[p]));
							     }
							 }
						  } else if(j==2 && k ==3){
						     int  ss = Integer.parseInt((String)v_level3Subnums.get(0));
							 //랜덤 구하기
							 if(v.size() > 0 && ss > 0){
                             int [] num =new int [ss];
							 int bun = 0;
                          //   num[0] = ran.nextInt(v.size()); 
                             for(int q = 0 ; q < ss ; q++){ 
								 bun = ran.nextInt(v.size()); 
                                // num[q] = ran.nextInt(v.size()); 
                                 for(int a = 0 ; a < q ; a++){ 
                                      while(num[a]==bun) { 
                                              bun = ran.nextInt(v.size()); 
                                      } 
                                 } 

								 num[q] = bun;
                             }
							     for (int p=0; p < ss ; p++){
									 v_realnums.add((String)v.get(num[p]));
							     }
							 }
						  }
					  }
				  }

			  int  ss = v_realnums.size();
							 //랜덤 구하기
							 Random ran = new Random();
                             int [] num =new int [ss];
							 int bun = 0;
                           //  num[0] = ran.nextInt(ss); 
                             for(int q = 0 ; q < ss ; q++){ 
                                 bun = ran.nextInt(ss); 
                                 for(int a = 0 ; a < q ; a++){ 
                                      while(num[a]==bun) { 
                                              bun = ran.nextInt(ss); 
                                      } 
                                 } 
								 num[q] = bun;
							 }
							     for (int p=0; p < ss ; p++){
									 v_examnums.add((String)v_realnums.get(num[p]));
							     }

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return v_examnums;
    }

    /**
	평가문제 리스트 
    @param box          receive from the form object and session
    @return Vector
    */
    public Vector getQuestionList(DBConnectionManager connMgr, String p_etestsubj, String p_etesttype, String p_levels) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;
        Vector v_examnums = new Vector();

        try {
            sql = "select a.etestsubj,   a.etestnum,  a.etesttype,  \n";
            sql+= "       a.etesttext,    a.exptext,      \n";
            sql+= "       a.levels,   a.selcnt,  a.saveimage,   a.saveaudio, a.savemovie, a.saveflash,  a.realimage,  \n";
			sql+= "		  a.realaudio, a.realmovie, a.realflash, DBMS_RANDOM.RANDOM rkey    \n";
            sql+= "  from tz_etest   a    \n";
            sql+= "   where a.etestsubj     = " + SQLString.Format(p_etestsubj);
            sql+= "   and a.etesttype    = " + SQLString.Format(p_etesttype);
			sql+= "   and a.levels    = " + SQLString.Format(p_levels);
			sql+= " order by rkey   \n"; 

            ls = connMgr.executeQuery(sql);
			System.out.println("ETestPaperBean 평가문제리스트: "+sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                v_examnums.add(dbox.getString("d_etestnum")+","+dbox.getString("d_rkey"));
				//System.out.println(dbox.getString("d_etestnum")+","+dbox.getString("d_rkey"));
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
        }
        return v_examnums;
    }


    /**
	평가문제 난이도별 리스트 
    @param box          receive from the form object and session
    @return Vector
    */
    public ArrayList getLevelQuestionList(DBConnectionManager connMgr, String p_etestsubj, String p_gyear, String p_etsetcode, RequestBox box) throws Exception {
   
		DataBox dbox = null;
		ArrayList list = null;
        Vector v_level1Obnums = new Vector();
        Vector v_level2Obnums = new Vector();
        Vector v_level3Obnums = new Vector();
        Vector v_level1Subnums = new Vector();
        Vector v_level2Subnums = new Vector();
        Vector v_level3Subnums = new Vector();
        StringTokenizer st3= null;	


		try {
            dbox = getETestPaperData(connMgr, p_etestsubj, p_etsetcode, p_gyear);
            list = new ArrayList(); 

			st3 = new StringTokenizer(dbox.getString("d_level1text"), ",");
			 v_level1Obnums.add(st3.nextToken());
    		 v_level1Subnums.add(st3.nextToken());

			st3 = new StringTokenizer(dbox.getString("d_level2text"), ",");
			 v_level2Obnums.add(st3.nextToken());
    		 v_level2Subnums.add(st3.nextToken());

			st3 = new StringTokenizer(dbox.getString("d_level3text"), ",");
			 v_level3Obnums.add(st3.nextToken());
    		 v_level3Subnums.add(st3.nextToken());

            list.add(v_level1Obnums);
            list.add(v_level2Obnums);
            list.add(v_level3Obnums);
            list.add(v_level1Subnums);
            list.add(v_level2Subnums);
            list.add(v_level3Subnums);

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return list;
    }


    /**
	평가문제 등록 
    @param box          receive from the form object and session
    @return int
    */
    public int insertTZ_etestpaper(DBConnectionManager connMgr, String p_etestsubj, String p_gyear, String p_etestcode, int p_etestnum, String p_etest, String p_whererpaper, 
	                    	String p_startdt, String p_enddt,
		                    int p_etestlimit,  int p_totalscore,    int    p_etestpoint,     int p_etestcnt,    
                            int    p_cntlevel1,  int    p_cntlevel2,  int    p_cntlevel3,  String p_level1text, String p_level2text, String p_level3text, String p_isopenanswer,
                            String    p_isopenexp, int p_etesttime, String p_luserid ) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_ETESTPAPER table
            sql =  " insert into TZ_ETESTPAPER ";
            sql+=  " ( etestsubj,   year,   etestcode,   etestnum,  etest,  wherepaper,  startdt,  enddt,  etestlimit,  totalscore, etestpoint,  etestcnt,   ";
            sql+=  "  cntlevel1, cntlevel2, cntlevel3, level1text, level2text, level3text, isopenanswer,  isopenexp, etesttime, ";
            sql+=  "   luserid,   ldate   ) ";
            sql+=  " values ";
            sql+=  " (?,         ?,        ?,       ?,           ?,            ?,           ?,             ?,              ?,             ?,             ?,               ?, ";
            sql+=  "  ?,         ?,         ?,         ?,            ?,               ?,             ?,             ?,               ?,     ";
            sql+=  "  ?,         ?       ) ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString( 1, p_etestsubj);
            pstmt.setString( 2, p_gyear);
            pstmt.setString( 3, p_etestcode);
			pstmt.setInt   ( 4, p_etestnum);
			pstmt.setString( 5, p_etest);
            pstmt.setString( 6, p_whererpaper);
            pstmt.setString( 7, p_startdt);
            pstmt.setString( 8, p_enddt);
			pstmt.setInt   ( 9, p_etestlimit);
            pstmt.setInt   ( 10, p_totalscore);
            pstmt.setInt   (11, p_etestpoint);
            pstmt.setInt   (12, p_etestcnt);
            pstmt.setInt(13, p_cntlevel1);
            pstmt.setInt(14, p_cntlevel2);
            pstmt.setInt(15, p_cntlevel3);
            pstmt.setString(16, p_level1text);
            pstmt.setString(17, p_level2text);
            pstmt.setString(18, p_level3text);
            pstmt.setString(19, p_isopenanswer);
            pstmt.setString(20, p_isopenexp);
            pstmt.setInt(21, p_etesttime);
            pstmt.setString(22, p_luserid);
            pstmt.setString(23, FormatDate.getDate("yyyyMMddHHmmss"));

            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
        }
        return isOk;
    }


    /**
	평가문제 수정 
    @param box          receive from the form object and session
    @return int
    */
    public int updateTZ_etestpaper(DBConnectionManager connMgr, String p_etestsubj, String p_gyear, String p_etestcode, int p_etestnum, String p_etest, String p_whererpaper, 
	                    	String p_startdt, String p_enddt,
		                    int p_etestlimit,  int p_totalscore,    int    p_etestpoint,     int p_etestcnt,    
                            int    p_cntlevel1,  int    p_cntlevel2,  int    p_cntlevel3,  String p_level1text, String p_level2text, String p_level3text, String p_isopenanswer,
                            String    p_isopenexp, int p_etesttime, String p_luserid ) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_ETESTPAPER table
            sql =  " update tz_etestpaper ";
            sql+=  "    set etest = ?, wherepaper = ?, startdt = ?, enddt = ?, etestlimit = ?, totalscore = ?, etestpoint = ?, etestcnt = ?, level1text = ?, level2text = ?, level3text = ?, ";
            sql+=  "        cntlevel1  = ?,  cntlevel2  = ?,  cntlevel3 = ?,  isopenanswer = ?, isopenexp  = ?, etesttime = ?, ";
            sql+=  "        luserid   = ?,  ldate     = ?   ";
            sql+=  "  where etestsubj = ? and etestcode = ? and year = ? and etestnum = ? ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString( 1, p_etest);
            pstmt.setString( 2, p_whererpaper);
            pstmt.setString( 3, p_startdt);
			pstmt.setString   ( 4, p_enddt);
			pstmt.setInt( 5, p_etestlimit);
            pstmt.setInt( 6, p_totalscore);
            pstmt.setInt( 7, p_etestpoint);
            pstmt.setInt( 8, p_etestcnt);
			pstmt.setString   ( 9, p_level1text);
            pstmt.setString   ( 10, p_level2text);
            pstmt.setString   (11, p_level3text);
            pstmt.setInt(12, p_cntlevel1);
            pstmt.setInt(13, p_cntlevel2);
            pstmt.setInt(14, p_cntlevel3);
            pstmt.setString(15, p_isopenanswer);
            pstmt.setString(16, p_isopenexp);
            pstmt.setInt(17, p_etesttime);
            pstmt.setString(18, p_luserid);
            pstmt.setString(19, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString( 20, p_etestsubj);
            pstmt.setString( 21, p_etestcode);
            pstmt.setString( 22, p_gyear);
			pstmt.setInt( 23, p_etestnum);

            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
        }
        return isOk;
    }


    /**
    온라인테스트시험지 시퀀스
    @param connMgr         Connection Manager
    @param s_subj          과정코드
    @param p_gyear          년도
    @param p_month         월
    @return getETestnumSeq  문제번호
    */
    public String getMaxSubjseq(DBConnectionManager connMgr, String p_etestsubj, String p_gyear, String p_month) throws Exception {
        String v_etestsubjseqcode = "";
        String v_maxsubjseq = "";
        int    v_maxno   = 0;

        ListSet ls = null;
        String sql  = "";
        try {
            sql = " select max(etestcode) maxetestcode";
            sql+= "   from tz_etestmaster ";
            sql+= "  where etestsubj = "  + SQLString.Format(p_etestsubj);
            sql+= "    and year = "  + SQLString.Format(p_gyear);
            sql+= "    and etestcode like "  + SQLString.Format(p_month+"%");

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                 v_maxsubjseq = ls.getString("maxetestcode");
            }

            if (v_maxsubjseq.equals("")) {
                v_etestsubjseqcode = p_month + "01";
            } else {
                v_maxno = Integer.valueOf(v_maxsubjseq.substring(2,4)).intValue();
                v_etestsubjseqcode = p_month + new DecimalFormat("00").format(v_maxno+1);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return v_etestsubjseqcode;
    }
    
    /**
    온라인테스트 시험지 상중하 문제수 가져오기
    @param box                receive from the form object and session
    @return ETestPaperData  조회한 시험지정보
    */
    public ArrayList selectETestLevels(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList typelist = new ArrayList();
        String sql  = "";
        DataBox dbox = null;
		int v_etesttype = 0;
		int v_levels = 0;

        String v_etestsubj    = box.getString("p_etestsubj");

        try {
            connMgr = new DBConnectionManager();
				
                 for ( int j = 1; j <= 2; j++ ){
                     v_etesttype = j;
                     ArrayList levellist = new ArrayList();
                     for ( int k =1; k <= 3 ; k++){
                         v_levels = k;

						 sql = "select count(levels) levelscount  ";
                         sql+= " from tz_etest ";
                         sql+= "  where etestsubj  = " + SQLString.Format(v_etestsubj) ;
                         sql+= "  and etesttype = " + SQLString.Format(v_etesttype) ;
                         sql+= "  and levels  = " + SQLString.Format(v_levels) ;  

						 ls = connMgr.executeQuery(sql); 

			             while (ls.next()) {
                              dbox = ls.getDataBox();
                              levellist.add(dbox);
                         }
					 }
					 typelist.add(levellist);
                 }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return typelist;
    }

    /**
	응시 뷰
    @param box          receive from the form object and session
    @return ArrayList   평가문제 리스트
    */
    public String getPaperPathData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
		String v_wherepaper = "";

        String v_etestsubj    = box.getString("p_etestsubj");
        String v_gyear    = box.getString("p_gyear");
        String v_etestcode = box.getString("p_etestcode");
        int v_etestnum  = box.getInt("p_etestnum");

        try {
            connMgr = new DBConnectionManager();

            v_wherepaper = getPaperPathData(connMgr, v_etestsubj, v_gyear, v_etestcode, v_etestnum);
   
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) {} }
        }
        return v_wherepaper;
    }

    /**
	응시 뷰
    @param box          receive from the form object and session
    @return ArrayList   평가문제 리스트
    */
    public String getPaperPathData(DBConnectionManager connMgr, String p_etestsubj, String p_gyear, String p_etestcode, int p_etestnum) throws Exception {
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;
        String v_wherepaper = "";

        try {
            sql = "select wherepaper  ";
            sql+= "  from tz_etestpaper ";
            sql+= " where etestsubj      = " + SQLString.Format(p_etestsubj);
            sql+= "   and year        = " + SQLString.Format(p_gyear);
            sql+= "   and etestcode = " + SQLString.Format(p_etestcode);
            sql+= "   and etestnum = " + SQLString.Format(p_etestnum);

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                dbox = ls.getDataBox();

                v_wherepaper = dbox.getString("d_wherepaper");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
        }
        return v_wherepaper;
    }


    /**
	평가문제 가져오기 
    @param box          receive from the form object and session
    @return Vector
    */
    public Vector SelectQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
		Vector v_examnums = null;

        String v_etestsubj    = box.getString("p_etestsubj");
        String v_gyear    = box.getString("p_gyear");
        String v_etestcode = box.getString("p_etestcode");
        int v_etestnum  = box.getInt("p_etestnum");

        try {
            connMgr = new DBConnectionManager();

            v_examnums = getExamnums(connMgr, v_etestsubj, v_gyear, v_etestcode, v_etestnum);
   
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) {} }
        }
        return v_examnums;
    }


    /**
	평가문제지 리스트 
    @param box          receive from the form object and session
    @return ArrayList
    */
	public ArrayList SelectPaperQuestionExampleList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
		Vector v_examnums = null;
        ArrayList QuestionExampleDataList  = null;

        String v_etestsubj    = box.getString("p_etestsubj");
        String v_gyear    = box.getString("p_gyear");
        String v_etestcode = box.getString("p_etestcode");
        int v_etestnum  = box.getInt("p_etestnum");
        String v_etestsubjnm   = box.getString("p_etestsubjnm");

        try {
            connMgr = new DBConnectionManager();

            v_examnums = getExamnums(connMgr, v_etestsubj, v_gyear, v_etestcode, v_etestnum);
   
			if (!v_examnums.equals("")) {
                // 설문번호에 해당하는 문제리스트를 만든다. 리스트((설문번호1, 보기1,2,3..))

                QuestionExampleDataList = getExampleData(connMgr, v_etestsubj, v_examnums);
            }

			if(QuestionExampleDataList==null){
			QuestionExampleDataList = new ArrayList();
			}
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) {} }
        }
        return QuestionExampleDataList;
    }

    /**
	평가문제지 리스트 
    @param box          receive from the form object and session
    @return ArrayList
    */
	public ArrayList SelectPaperQuestionExampleList2(RequestBox box, Vector p_etestnums) throws Exception {
        DBConnectionManager connMgr = null;
		Vector v_examnums = null;
        ArrayList QuestionExampleDataList  = null;

        String v_etestsubj    = box.getString("p_etestsubj");
        String v_gyear    = box.getString("p_gyear");
        String v_etestcode = box.getString("p_etestcode");
        int v_etestnum  = box.getInt("p_etestnum");
        String v_etestsubjnm   = box.getString("p_etestsubjnm");

        try {
            connMgr = new DBConnectionManager();

            v_examnums = p_etestnums;
   
			if (!v_examnums.equals("")) {
                // 설문번호에 해당하는 문제리스트를 만든다. 리스트((설문번호1, 보기1,2,3..))

                QuestionExampleDataList = getExampleData(connMgr, v_etestsubj, v_examnums);
            }

			if(QuestionExampleDataList==null){
			QuestionExampleDataList = new ArrayList();
			}
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) {} }
        }
        return QuestionExampleDataList;
    }

    /**
	평가문제번호 생성 
    @param box          receive from the form object and session
    @return Vector
    */
	public Vector getExamnums(DBConnectionManager connMgr, String p_etestsubj, String p_gyear, String p_etestcode, int p_etestnum) throws Exception {
        ListSet ls = null;
        String sql  = "";
        Vector v_sulnums = new Vector();
        String v_tokens  = "";
        StringTokenizer st = null;

        try {
            sql = "select etest  ";
            sql+= "  from tz_etestpaper ";
            sql+= " where etestsubj      = " + SQLString.Format(p_etestsubj);
            sql+= "   and year        = " + SQLString.Format(p_gyear);
            sql+= "   and etestcode = " + SQLString.Format(p_etestcode);
            sql+= "   and etestnum = " + SQLString.Format(p_etestnum);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_tokens = ls.getString("etest");
            }

            st = new StringTokenizer(v_tokens,",");
            while (st.hasMoreElements()) {
                v_sulnums.add((String)st.nextToken());
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return v_sulnums;
    }

    /**
	평가문제 데이타 
    @param box          receive from the form object and session
    @return ArrayList
    */
    public ArrayList getExampleData(DBConnectionManager connMgr, String p_etestsubj,  Vector p_examnums) throws Exception {
        Hashtable hash = new Hashtable();
        ArrayList blist = new ArrayList();
		ArrayList list = null;

        ListSet ls  = null;
        String sql  = "";
        DataBox dbox = null;
        StringTokenizer st = null;

        String v_examnums = "";
        for (int i=0; i < p_examnums.size(); i++) {
            v_examnums += (String)p_examnums.get(i);
            if (i<p_examnums.size()-1) {
                v_examnums += ",";
            }
        }
        if (v_examnums.equals("")) v_examnums = "-1";

        try {

			st = new StringTokenizer(v_examnums, ",");

            while(st.hasMoreElements()) {

                int examnum = Integer.parseInt(st.nextToken());

            sql = "select a.etestsubj,     a.etestnum,  a.etesttype, ";
            sql+= "       a.etesttext,      a.exptext,   a.levels,  a.selcnt,  a.saveimage,   a.saveaudio, ";
            sql+= "       a.savemovie,  a.saveflash, a.realimage,   a.realaudio, a.realmovie, a.realflash, ";
            sql+= "       b.selnum,   b.seltext,  b.isanswer,  ";
            sql+= "       c.codenm    etesttypenm, ";
            sql+= "       d.codenm    levelsnm    ";
            sql+= "  from tz_etest      a, ";
            sql+= "       tz_etestsel   b, ";
            sql+= "       tz_code      c, ";
            sql+= "       tz_code      d  ";
			// 수정일 : 05.11.04 수정자 : 이나연 _ (+) 수정
//          sql+= " where a.etestsubj     = b.etestsubj(+) ";
//          sql+= "   and a.etestnum  = b.etestnum(+) ";
			sql+= " where a.etestsubj      =  b.etestsubj(+) ";
            sql+= "   and a.etestnum  	   =  b.etestnum(+) ";
            sql+= "   and a.etesttype = c.code ";
            sql+= "   and a.levels   = d.code ";
            sql+= "   and a.etestsubj     = " + SQLString.Format(p_etestsubj);
            sql+= "   and a.etestnum  = " + examnum;
            sql+= "   and c.gubun    = " + SQLString.Format(ETestBean.ETEST_TYPE);
            sql+= "   and d.gubun    = " + SQLString.Format(ETestBean.ETEST_LEVEL);
            sql+= " order by a.etestnum, b.selnum ";

            ls = connMgr.executeQuery(sql);
			list =  new ArrayList();

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);

			}
			   blist.add(list);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return blist;
    }

    /**
	평가지 데이타 
    @param box          receive from the form object and session
    @return PaperData
    */
    public DataBox getPaperData(RequestBox box) throws Exception {
    
		DBConnectionManager connMgr = null;

        DataBox     dbox = null;

        String v_etestsubj    = box.getString("p_etestsubj");
        String v_gyear    = box.getString("p_gyear");
        String v_etestcode = box.getString("p_etestcode");
        int v_etestnum  = box.getInt("p_etestnum");


        try {
            connMgr = new DBConnectionManager();
            dbox =  getPaperData(connMgr, v_etestsubj, v_gyear, v_etestcode, v_etestnum);

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }

    /**
	평가지 데이타 
    @param box          receive from the form object and session
    @return PaperData
    */
    public DataBox getPaperData(DBConnectionManager connMgr, String p_etestsubj, String p_gyear, String p_etestcode, int p_etestnum) throws Exception {
        ListSet ls = null;
        String sql  = "";
        DataBox     dbox = null;

        try {
                sql = getPaperListSQL(p_etestsubj, p_gyear, p_etestcode, p_etestnum);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
				dbox = ls.getDataBox();
            
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }

        if (dbox==null) dbox = new DataBox("resoponsebox");

        return dbox;
    }

    /**
	평가문제지 SQL문 
    @param box          receive from the form object and session
    @return String
    */
    public String getPaperListSQL(String p_etestsubj, String p_gyear, String p_etestcode, int p_etestnum) throws Exception {
        String sql = "";
   
            sql = "select b.etestsubj,   a.year,    a.etestcode,   a.etestnum, ";
            sql+= "       a.etest,  a.wherepaper,   a.startdt, a.enddt,  a.etestlimit, a.etestpoint, a.etestcnt, a.totalscore,  ";
            sql+= "       a.cntlevel1, a.cntlevel2, a.cntlevel3, a.isopenanswer, ";
            sql+= "       a.isopenexp,  a.etesttime,  b.etestsubjnm ";
            sql+= "  from tz_etestpaper a, ";
            sql+= "       tz_etestsubj       b  ";
			// 수정일 : 05.11.04 수정자 : 이나연 _ (+) 수정
//          sql+= " where a.etestsubj(+)       =  b.etestsubj  ";
			sql+= " where a.etestsubj          =* b.etestsubj  ";
			sql+= "   and a.etestsubj   = " + SQLString.Format(p_etestsubj);
            sql+= "   and a.year   = " + SQLString.Format(p_gyear);
            sql+= "   and a.etestcode   = " + SQLString.Format(p_etestcode);
            sql+= "   and a.etestnum   = " + SQLString.Format(p_etestnum);
            sql+= " order by a.etestsubj, a.year, a.etestcode, a.etestnum  ";	

        return sql;
    }
}