
//**********************************************************
//1. 제      목: 온라인테스트 대상자 관리
//2. 프로그램명: ETestMemberBean.java
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
import com.credu.common.*;
import java.text.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ETestMemberBean {

    public ETestMemberBean() {}


    /**
    온라인테스트 대상자 리스트
    @param box          receive from the form object and session
    @return ArrayList   온라인테스트 대상자
    */
    public ArrayList selectETestMemberList(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ArrayList list = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";        
        
        try {      
            String ss_etestsubj    = box.getString("s_etestsubj");
            String ss_gyear    = box.getString("s_gyear");
            String ss_etestcode = box.getString("s_etestcode");
            String v_action  = box.getString("p_action");
             
            list = new ArrayList();
            
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();                
          
                sql = "select a.etestsubj,     a.year,   a.etestcode, a.userid, b.name,  \n";
				sql+= "	case when  b.membergubun = 'P' then  '개인'    \n";   
				sql+= "	when  b.membergubun = 'C' then  '기업'         \n";
				sql+= "	when  b.membergubun = 'U' then  '대학교'       \n";
				sql+= "	else '-' end   as membergubunnm     \n";
                sql+= "  from tz_etestmember   a, \n";
                sql+= "       tz_member     b  \n";
                sql+= " where a.userid  = b.userid \n";
                sql+= "   and a.etestsubj    = " + SQLString.Format(ss_etestsubj);
                sql+= "   and a.year    = " + SQLString.Format(ss_gyear);
                sql+= "   and a.etestcode = " + SQLString.Format(ss_etestcode);
                sql+= " order by a.userid   \n";
				
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
    
    /**
    온라인테스트 마스터 상세보기
    @param box                receive from the form object and session
    @return ETestMasterData  조회한 마스터정보
    */
    public DataBox selectETestMasterData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql  = "";
        String sql2 = "";
        DataBox dbox = null;        

        try {    
            String ss_etestsubj    = box.getString("s_etestsubj");
            String ss_gyear    = box.getString("s_gyear");
            String ss_etestcode = box.getString("s_etestcode");
            
            String v_action  = box.getString("p_action");
            
            if (v_action.equals("go")) {   
                connMgr = new DBConnectionManager();
    
                sql = "select a.etestsubj, b.etestsubjnm,    a.year,      a.etestcode, a.etesttext,  \n";
                sql+= "       a.etestlimit,   \n";
                sql+= "       a.startdt, a.enddt  \n";
                sql+= "  from tz_etestmaster a, tz_etestsubj b                                     \n";
				sql+= " where a.etestsubj    = " + SQLString.Format(ss_etestsubj);
                sql+= "   and a.year    = " + SQLString.Format(ss_gyear);
                sql+= "   and a.etestcode = " + SQLString.Format(ss_etestcode);
                sql+= "   and a.etestsubj = b.etestsubj";
				//System.out.println("온라인테스트 마스터 상세:"+sql);
                ls = connMgr.executeQuery(sql);

                sql2 = "select count(userid) from tz_etestmember ";
				sql2+= " where etestsubj = ? and etestcode = ? and year = ? ";
                pstmt = connMgr.prepareStatement(sql2);
    
                if (ls.next()) {
                    dbox = ls.getDataBox();

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
				}
                else {
                    dbox = new DataBox("requestbox");
                }
            }
            else {
                    dbox = new DataBox("requestbox");
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
        return dbox;
    }

     /**
    대상자를 찾기위한 회원 리스트
    @param box          receive from the form object and session
    @return ArrayList   대상자를 찾기위한 회원 리스트
    */
    public ArrayList selectMemberTargetList(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ArrayList list = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";        
        
        try {      

			String  ss_gpm  = box.getStringDefault("s_gpm", "ALL");     
            
            String ss_searchtype = box.getString("s_searchtype");
            String ss_searchtext = box.getString("s_searchtext");
            
            String v_action  = box.getString("p_action");
             
            list = new ArrayList();
            
            if (v_action.equals("go")) {   
                connMgr = new DBConnectionManager();
                
                sql = "select userid, name , \n";
				sql+= "case when  membergubun = 'P' then  '개인'     \n";
				sql+= "when  membergubun = 'C' then  '기업'          \n";
				sql+= "when  membergubun = 'U' then  '대학교'        \n";
				sql+= "else '-' end   as membergubunnm     	 \n";			
                sql+= "  from tz_member  where 1 = 1";
                
                if (ss_searchtype.equals("1")) {  // 사번
                    sql+= "  and cono like " + SQLString.Format("%"+ss_searchtext+"%");
                } 
                else if (ss_searchtype.equals("2")) {  // id
                    sql+= "  and userid like " + SQLString.Format("%"+ss_searchtext+"%");
                } 
                else if (ss_searchtype.equals("3")) {  // 성명
                    sql+= "  and name like " + SQLString.Format("%"+ss_searchtext+"%");
                }
				
                sql+= " order by userid";
				
				System.out.println("ETestMemberBean 대상자를 찾기위한 회원 리스트:"+sql);
                
                ls = connMgr.executeQuery(sql);System.out.println(sql);
                
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
    
     /**
    과정 대상자를 찾기위한 회원 리스트
    @param box          receive from the form object and session
    @return ArrayList   대상자를 찾기위한 회원 리스트
    */
    public ArrayList selectSubjMemberTargetList(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ArrayList list = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";        
        
        try {      
       
		    String  ss_grcode      = box.getStringDefault("s_grcode", "ALL");           //교육그룹
            String  ss_gyear       = box.getStringDefault("s_gyear", "ALL");            //년도
            String  ss_grseq       = box.getStringDefault("s_grseq", "ALL");            //교육차수
            String  ss_upperclass  = box.getStringDefault("s_upperclass", "ALL");
            String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
            String  ss_lowerclass  = box.getStringDefault("s_lowerclass", "ALL");
            String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");    //과정&코스
            String  ss_subjseq     = box.getStringDefault("s_subjseq","ALL");
            
            String v_action  = box.getString("p_action");
             
            list = new ArrayList();
            
            if (v_action.equals("go")) {   
                connMgr = new DBConnectionManager();
                
                sql = "select b.userid,  ";
                sql+= "       a.comp  asgn,   ";
                sql+="        a.deptnam, get_compnm(a.comp,2,2) companynm, ";
                sql+= "	   	  a.jikwi,       get_jikwinm(a.jikwi, a.comp) jikwinm, ";
                sql+= "	   	  a.cono,        a.name ";
                sql+= "  from tz_member a, tz_student b  where 1 = 1";
				sql+= " and  a.userid = b.userid ";
                
                if( !ss_subjcourse.equals("ALL")) {
                    sql += " and b.subj = " + SQLString.Format(ss_subjcourse);
                }
                if( !ss_gyear.equals("ALL")) {
                    sql += " and b.year = " + SQLString.Format(ss_gyear);
                }
                if( !ss_subjseq.equals("ALL")) {
                    sql += " and b.subjseq = " + SQLString.Format(ss_subjseq);
                }
                sql+= " order by a.jikwi";
             
                ls = connMgr.executeQuery(sql);//System.out.println(sql);
                
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


    /**
    대상자 등록
    @param  box          receive from the form object and session
    @return int  
    */	
	public int insertETestMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;        
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;   
        
        String  ss_etestsubj      = box.getString("s_etestsubj");
        String  ss_gyear      = box.getString("s_gyear");
        String  ss_etestcode   = box.getString("s_etestcode");
        String  v_luserid   = box.getSession("userid");
        
        Vector  v_checks    = box.getVector("p_checks");
        String  v_userid  = "";
        
        // jkh 과정연결 0312
        String  ss_subj   = box.getString("s_subjcourse");
        String  ss_subjseq   = box.getString("s_subjseq");
        
        int cnt = 0;
        int next = 0;
        
        try {
            connMgr = new DBConnectionManager();                             
            connMgr.setAutoCommit(false);  

            sql1 = "select userid from tz_etestmember";
            sql1+=  " where etestsubj = ? and year = ? and etestcode = ? and userid = ?  "; 
            pstmt1 = connMgr.prepareStatement(sql1);   
            
            /*
            sql2 =  " insert into tz_etestmember "; 
            sql2+=  " (etestsubj,   etestcode,    year,     userid,   ";
            sql2+=  "  luserid,   ldate   ) ";
            sql2+=  " values "; 
            sql2+=  " (?,         ?,         ?,         ?, "; 
            sql2+=  "  ?,         ?         )  ";
            */
            //수정 과정연결 jkh 0312
            sql2 =  " insert into tz_etestmember "; 
            sql2+=  " (etestsubj,   etestcode,    year,     userid,   ";
            sql2+=  "  luserid,   ldate,subj,subjseq   ) ";
            sql2+=  " values "; 
            sql2+=  " (?,         ?,         ?,         ?, "; 
            sql2+=  "  ?,         ?,?,?         )  ";
            pstmt2 = connMgr.prepareStatement(sql2);                           
            
            for(int i=0; i < v_checks.size(); i++){
                v_userid = (String)v_checks.elementAt(i);  
                
                pstmt1.setString( 1, ss_etestsubj);      
                pstmt1.setString( 2, ss_gyear);       
                pstmt1.setString( 3, ss_etestcode);    
                pstmt1.setString( 4, v_userid);

                
                try {
                    rs = pstmt1.executeQuery();
                    
                    if(!rs.next()) {     //     과거에 등록된 userid 를 확인하고 없을 경우에만 등록          
                        pstmt2.setString( 1, ss_etestsubj);      
                        pstmt2.setString( 2, ss_etestcode);       
                        pstmt2.setString( 3, ss_gyear);    
                        pstmt2.setString( 4, v_userid);
                        pstmt2.setString( 5, v_luserid);
                        pstmt2.setString( 6, FormatDate.getDate("yyyyMMddHHmmss"));
                        //jkh 0312
                        pstmt2.setString( 7, ss_subj);
                        pstmt2.setString( 8, ss_subjseq);
                        
                        isOk = pstmt2.executeUpdate();
                        cnt += isOk;
                        next++;
                    }
                }catch(Exception e) {}
                finally { if (rs != null) { try { rs.close(); } catch (Exception e) {} }}           
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
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        }
        finally {            
            if (pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }            
            if (pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    대상자 삭제
    @param  box          receive from the form object and session
    @return int  
    */	    
    public int deleteETestMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;        
        PreparedStatement pstmt = null;
		StringTokenizer st = null;
        String sql = "";
        int isOk = 0;   
        
        String  ss_etestsubj      = box.getString("s_etestsubj");
        String  ss_gyear      = box.getString("s_gyear");
        String  ss_etestcode   = box.getString("s_etestcode");
        String  v_luserid   = box.getSession("userid");
        
        Vector  v_checks    = box.getVector("p_checks");
        String  v_schecks = "";       
		String  v_userid  = "";
        int cnt = 0;
        
        try {
            connMgr = new DBConnectionManager();                             
            connMgr.setAutoCommit(false);  
            
            sql =  " delete from tz_etestmember ";  
            sql+=  " where etestsubj = ? and year = ? and etestcode = ? and userid = ?"; 

              
            pstmt = connMgr.prepareStatement(sql);            
        
            for(int i=0; i < v_checks.size(); i++){

                v_schecks = (String)v_checks.elementAt(i);  
                st = new StringTokenizer(v_schecks,",");
                v_userid = (String)st.nextToken();


		
                pstmt.setString( 1, ss_etestsubj);      
                pstmt.setString( 2, ss_gyear);       
                pstmt.setString( 3, ss_etestcode);    
                pstmt.setString( 4, v_userid);
                
                isOk = pstmt.executeUpdate();
                cnt += isOk;
            }
            
            if (v_checks.size() == cnt) {
                connMgr.commit();
                isOk = cnt;
            }
            else {
                connMgr.rollback();
                isOk = 0;
            }
        }
        catch(Exception ex) {
            isOk = 0;
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {            
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }	

    /**
    *  대상자 설문지 셀렉트박스 (RequestBox, 셀렉트박스명,선택값,이벤트명)
	*  TZ_SULPAPER 이용
    */
    public static String getETestCode (String p_etestsubj, String p_gyear, String name, String selected, String event) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        DataBox     dbox = null;

        result = "  <SELECT name=" + name + " " + event + " > \n";

        result += " <option value='0'>테스트명을 선택하세요.</option> \n";

        try {
            connMgr = new DBConnectionManager();

            sql = "select distinct etestcode, etesttext";
            sql += " from tz_etestmaster where  1 = 1";	
            sql += " and etestsubj = " + SQLString.Format(p_etestsubj);
            sql += " and year = " + SQLString.Format(p_gyear);
            sql += " order by etestcode";  	

            ls = connMgr.executeQuery(sql);

                String v_null_test = "";
                String v_etestsubj_bef = "";

            while (ls.next()) {

                    dbox = ls.getDataBox();

                result += " <option value=" + dbox.getString("d_etestcode");
                if (selected.equals(dbox.getString("d_etestcode"))) {
                    result += " selected ";
                }
                
                result += ">" + dbox.getString("d_etesttext") + "</option> \n";
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


    /**
    SELECT HTML
    @param box          receive from the form object and session
    @return String
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
            else {
                sb.append("<option value = \"----\">----</option>\r\n");  
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
}