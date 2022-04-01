//**********************************************************
//  1. 제      목: 공통데이터관리 빈
//  2. 프로그램명: CPCommonBean.java
//  3. 개      요: 공통데이터관리 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004. 12. 22
//  7. 수      정:
//**********************************************************

package com.credu.cp;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.beta.*;

/**
*공통데이터관리
*<p>제목:CPCommonBean.java</p>
*<p>설명:공통데이터관리 빈</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 이창훈
*@version 1.0
*/

public class CPCommonBean {
    private ConfigSet config;
    private int row;
	    	
    public CPCommonBean() {}

    /**
    외주업체 리스트 설정
    @param box          receive from the form object and session
    @param isChange		선택했을때 페이지 리로드여부
    @param isALL		ALL 표시여부
    @return String   	외주업체리스트 셀렉트태그
    */
	public static String getCP(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        String sql = "";
        String result = " ";
        String v_cp = "";

        try {   
            v_cp = box.getString("p_cp");
            
	    	connMgr = new DBConnectionManager();                 
            
            String s_gadmin = box.getSession("gadmin");
            String s_userid = box.getSession("userid");
            
            
            //외주업체 리스트
            if(s_gadmin.equals("S1") || s_gadmin.equals("T1") || s_gadmin.equals("M1")){
            	//외주업체 담당자일경우(해당업체의 정보만보여줌)
//	            sql = "select cpseq, cpnm ";
//	            sql += " from tz_cpinfo where userid = " + SQLString.Format(s_userid);	
//	            sql += " order by cpnm";
				sql  = " select  comp, compnm from tz_comp            ";
				sql += "  where comp in (select comp from tz_compman  ";
				sql += "                  where gadmin = 'T1'         ";
				sql += "                    and userid = " + SQLString.Format(s_userid) + " )";				
	            isALL = false;
           	}
            else{
            	//외주업체 담당자가아닐경우(모든업체 정보를 보여줌)
//	            sql = "select cpseq, cpnm ";
//	            sql += " from tz_cpinfo ";	
//	            sql += " order by cpnm";
	            sql = "select comp, compnm ";
	            sql += " from tz_comp      ";	
	            sql += " order by comp    ";				
	        }
            
            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            ls = new ListSet(pstmt);

            result += getSelectTag(ls, isChange, isALL, "p_cp", v_cp);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }    
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return result;
    }

    /**
    년도 가져오기
    @param box          receive from the form object and session
    @param isChange		선택했을때 페이지 리로드여부
    @param isALL		ALL 표시여부
    @param syear		시작년도
    @param selname		셀렉트박스 네임
    @return String   	년도 셀렉트태그
    */
	public static String getYear(RequestBox box, boolean isChange, boolean isALL, String syear, String selname) throws Exception {
		StringBuffer sb = null;
        String ss_year = "";
        int v_year = 0;
        String sb_year = "";
        
        try {
            sb = new StringBuffer();  

			ss_year = box.getString("p_year");
			
			//if(ss_year == "") ss_year = StringManager.toInt(FormatDate.getDate("yyyy"));
			if(ss_year.equals("")){
				ss_year = FormatDate.getDate("yyyy");				
			}
			v_year = StringManager.toInt(syear);
			sb_year = "" + v_year;
			
            sb.append(" <select name = \"" + selname + "\"");
            if(isChange) sb.append(" onChange = \"javascript:whenSelection('change')\"");  
            sb.append(">\r\n");
            if(isALL) {
                sb.append("<option value = \"ALL\">ALL</option>\r\n");  
            }
            //else if(isChange) {
            //    if(selname.indexOf("year") == -1) 
            //        sb.append("<option value = \"----\">----</option>\r\n");  
            //}
			
            while (v_year<=StringManager.toInt(FormatDate.getDate("yyyy"))+1) {    

                sb.append("<option value = \"" + v_year + "\"");

                if (sb_year.equals(ss_year)) sb.append(" selected");
               
                sb.append(">" + v_year + " 년</option>\r\n");
                
                v_year = v_year + 1;
                sb_year = "" + v_year;
                
            }
            
            sb.append("</select>\r\n");
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception(ex.getMessage());
        }
        return sb.toString();
	}

    /**
    외주 업체 select box 태그
    @param ls          화사정보 리스트셋
    @param isChange		선택했을때 페이지 리로드여부
    @param isALL		ALL 표시여부
    @param selname		셀렉트박스 네임    
    @param optionselected		선택여부
    @return String   	년도 셀렉트태그
    */
    public static String getSelectTag(ListSet ls, boolean isChange, boolean isALL, String selname, String optionselected) throws Exception {
        StringBuffer sb = null;
        
        try {
            sb = new StringBuffer();  

            sb.append("<select name = \"" + selname + "\"");
            if(isChange) sb.append(" onChange = \"javascript:whenSelection('change')\"");  
            sb.append(">\r\n");
            if(isALL) {
                sb.append("<option value = \"ALL\">ALL</option>\r\n");  
            }
            else if(isChange) {
                    //if(selname.indexOf("year") == -1) 
                    //sb.append("<option value = \"----\">== 선택 ==</option>\r\n");  
            }

            while (ls.next()) {    
                ResultSetMetaData meta = ls.getMetaData();
                int columnCount = meta.getColumnCount();
                
                sb.append("<option value = \"" + ls.getString(1) + "\"");

                if (optionselected.equals(ls.getString(1))) sb.append(" selected");
               
                sb.append(">" + ls.getString(columnCount) + "</option>\r\n");
            }
            sb.append("</select>\r\n");
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception(ex.getMessage());
        }
        return sb.toString();
    }


    /**
    외주 업체 select box 태그
    @param ls          화사정보 리스트셋
    @param isChange		선택했을때 페이지 리로드여부
    @param isALL		ALL 표시여부
    @param selname		셀렉트박스 네임    
    @param optionselected		선택여부
    @return String   	년도 셀렉트태그
    */
    public static String getSelectCourse(RequestBox box, boolean isChange, boolean isALL, String selname, String optionselected) throws Exception {
        StringBuffer sb = null;
		BPCourseBean bean = new BPCourseBean();
		ArrayList list     = bean.selectCourseList(box);

        try {
            sb = new StringBuffer();  

            sb.append("<select name = \"" + selname + "\"");
            if(isChange) sb.append(" onChange = \"javascript:whenSelection('change')\"");  
            sb.append(">\r\n");
            if(isALL) {
                sb.append("<option value = \"ALL\">ALL</option>\r\n");  
            }
            else if(isChange) {
                if(selname.indexOf("year") == -1) 
                    sb.append("<option value = \"----\">== 선택 ==</option>\r\n");  
            }
			
			if (list != null && list.size() > 0){

				for(int i = 0; i < list.size(); i++) {
					DataBox dbox = (DataBox)list.get(i);  

					sb.append("<option value = \"" + dbox.getString("d_subj") + "\"");

					if (optionselected.equals(dbox.getString("d_subj"))) sb.append(" selected");
				   
					sb.append(">" + dbox.getString("d_subjnm") + "</option>\r\n");
				}
				sb.append("</select>\r\n");
			}
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception(ex.getMessage());
        }
        return sb.toString();
    }

    
    /**
    *  대상자 설문지 셀렉트박스 (RequestBox, 셀렉트박스명,선택값,이벤트명)
	*  TZ_SULPAPER 이용
    */
    public static String getSulpaperSelect(String p_grcode, String p_gyear, String p_subj, String name, int selected, String event) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        DataBox     dbox = null;

        result = "  <SELECT name=" + name + " " + event + " > \n";
        result += " <option value='0'>설문지를 선택하세요.</option> \n";

        try {
            connMgr = new DBConnectionManager();
			sql = "select grcode,       subj,         \n";
            sql+= "       sulpapernum,  sulpapernm, year, \n";
            sql+= "       totcnt,       sulnums, sulmailing, sulstart, sulend, \n";
            sql+= "       'COMMON'      subjnm \n";
            sql+= "  from tz_sulpaper  \n";
            sql+= " where grcode = " + StringManager.makeSQL(p_grcode);
            sql+= "   and subj   = " +StringManager.makeSQL(p_subj);
            sql+= "   and year   = " + StringManager.makeSQL(p_gyear);
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