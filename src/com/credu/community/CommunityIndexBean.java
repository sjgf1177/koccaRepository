//**********************************************************
//1. 제      목: 커뮤니티 메인관련
//2. 프로그램명: CommunityIndexBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정: 이나연 05.12.07 _ 탈퇴회원관리 수정
//
//**********************************************************

package com.credu.community;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;
import com.credu.community.*;
import com.namo.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommunityIndexBean {
    private ConfigSet config;
    private static int row=10;
    private String v_type = "PQ";
    private static final String FILE_TYPE = "p_file";           //      파일업로드되는 tag name
    private static final int FILE_LIMIT = 1;                    //    페이지에 세팅된 파일첨부 갯수


    public void CommunityIndexBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));  //이 모듈의 페이지당 row 수를 셋팅한다
            row = 10; //강제로 지정
//System.out.println("....... row.....:"+row);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    

    /**
    * 커뮤니티 메인 화면
    * @param box          receive from the form object and session
    * @return ArrayList   커뮤니티 메인화면 데이터 리스트
    * @throws Exception
    */
    public ArrayList selectMainIndex(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();

        ArrayList           list1    = new ArrayList();  // 커뮤니티 공지사항
        ArrayList           list2    = new ArrayList();  // 홍보방
        ArrayList           list3    = new ArrayList();  // 신규커뮤니티
        ArrayList           list4    = new ArrayList();  // 인기커뮤니티
        ArrayList           list5    = new ArrayList();  // 대분류 리스트
		
        String sql         = "";                                                                                                                            
        StringBuffer headSql = new StringBuffer();                                                                                                          
        StringBuffer bodySql = new StringBuffer();                                                                                                          
        String countSql   = "";                                                                                                                             
        String orderSql   = "";                        
        
        int total_row_count = 0; 
                                                                                                                                                            
        DataBox    dbox    = null;

        try {
            connMgr = new DBConnectionManager();

            headSql.append(" SELECT  A.FAQNO, A.TITLE, A.REGISTER_DTE \n ");                                                                               
			bodySql.append(" FROM    TZ_CMUFAQ A                      \n ");                                                                               
			bodySql.append(" WHERE   A.FAQ_TYPE = 'DIRECT'            \n ");                                                                               
			                                                                                                                                                
			orderSql    = " ORDER BY A.ROOT DESC,A.POSITION ASC ";                                                                                         
                                                                                                                                                           
			sql = headSql.toString() + bodySql.toString() + orderSql;		                                                                                
				                                                                                                                                            
            ls = connMgr.executeQuery(sql);                                                                                                                 
			                                                                                                                                                
			countSql = "select count(*) " + bodySql.toString();      		                                                                                
			total_row_count = BoardPaging.getTotalRow(connMgr, countSql);                                                                                   
			                                                                                                                                                
            ls.setPageSize(3);                         // 페이지당 row 갯수를 세팅한다                                                                      
            ls.setCurrentPage(1, total_row_count);     // 현재페이지번호를 세팅한다.                                                       
            while (ls.next()) {                                                                                                                             
                dbox = ls.getDataBox();                                                                                                                     
                list1.add(dbox);                                                                                                                            
            }
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            headSql.setLength(0);                                                                                                                           
            bodySql.setLength(0); 

            // 홍보방
            headSql.append(" SELECT  A.CMUNO , A.CMU_NM, A.MEMBER_CNT, A.REGISTER_DTE \n ");
            headSql.append("         , B.SAVEFILE, B.CONTENTS                         \n ");
            headSql.append(" FROM    TZ_CMUBASEMST A,TZ_CMUHONGBO B                   \n ");
            headSql.append(" WHERE   A.CMUNO     = B.CMUNO                            \n ");
            headSql.append(" AND     A.CLOSE_FG  ='1'                                 \n ");
            headSql.append(" ORDER BY B.MODIFIER_DTE DESC                             \n ");

            ls = connMgr.executeQuery(headSql.toString());
			
            while (ls.next()) {
                dbox = ls.getDataBox();
                list2.add(dbox);
            }
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            headSql.setLength(0);                                                                                                                           
            bodySql.setLength(0); 


            //신규커뮤니티
            headSql.append(" SELECT  A.CMUNO, A.CMU_NM, A.MEMBER_CNT \n ");                                                                                        
			bodySql.append(" FROM    TZ_CMUBASEMST A           \n ");                                                                                        
			bodySql.append(" WHERE   A.CLOSE_FG ='1'           \n ");                                                                                        
                                                                                                                                                            
            orderSql	= "ORDER BY A.REGISTER_DTE DESC      \n ";                                                                                          
                                                                                                                                                            
			sql = headSql.toString() + bodySql.toString() + orderSql;		                                                                                
                                                                                                                                                            
            ls = connMgr.executeQuery(sql);                                                                                                                 
			                                                                                                                                                
            countSql= "select count(*) " + bodySql.toString();                                                                                              
            total_row_count = BoardPaging.getTotalRow(connMgr, countSql);                                                                                   
			                                                                                                                                                
            ls.setPageSize(4);                         // 페이지당 row 갯수를 세팅한다                                                                      
            ls.setCurrentPage(1, total_row_count);                      // 현재페이지번호를 세팅한다.                                                       
            while (ls.next()) {                                                                                                                             
                dbox = ls.getDataBox();                                                                                                                     
                list3.add(dbox);                                                                                                                            
            }     
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            headSql.setLength(0);                                                                                                                           
            bodySql.setLength(0); 

            //인기커뮤니티
            headSql.append(" SELECT  A.CMUNO, A.CMU_NM, A.MEMBER_CNT         \n ");                                                                                       
            bodySql.append(" FROM    TZ_CMUBASEMST A           \n ");                                                                                       
            bodySql.append(" WHERE   A.CLOSE_FG  ='1'          \n ");                                                                                       
            bodySql.append(" AND     A.HOLD_FG   ='1'          \n ");                                                                                       
            orderSql    = " ORDER BY A.REGISTER_DTE DESC ";                                                                                                 
                                                                                                                                                            
            sql = headSql.toString() + bodySql.toString() + orderSql;                                                                                       
                                                                                                                                                            
            ls = connMgr.executeQuery(sql);                                                                                                                 
            countSql	= "select count(*) "+ bodySql.toString();                                                                                           
            total_row_count = BoardPaging.getTotalRow(connMgr, countSql);                                                                                   
            ls.setPageSize(4);                         		// 페이지당 row 갯수를 세팅한다                                                                 
            ls.setCurrentPage(1,total_row_count);             // 현재페이지번호를 세팅한다.                                                                 
            while (ls.next()) {                                                                                                                             
                dbox = ls.getDataBox();                                                                                                                     
                list4.add(dbox);                                                                                                                            
            }
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            headSql.setLength(0);                                                                                                                           
            bodySql.setLength(0); 
            
            //커뮤니티 대분류
            headSql.append("SELECT  'ALL' CODE, '전체' CODENM, COUNT(B.CMUNO) CNT\n");
            headSql.append("FROM    TZ_CODE A, TZ_CMUBASEMST B            \n");
            headSql.append("WHERE   A.CODE     =  B.TYPE_L(+)             \n");
            headSql.append("AND     A.GUBUN    = '0052'                   \n");
            headSql.append("AND     A.LEVELS   = 1                        \n");
            headSql.append("AND     B.CLOSE_FG(+) = '1'                   \n");
            headSql.append(" UNION ALL                                    \n");
            headSql.append(" SELECT  A.CODE, A.CODENM, COUNT(B.CMUNO) CNT \n");
            headSql.append(" FROM    TZ_CODE A, TZ_CMUBASEMST B           \n");
            headSql.append(" WHERE   A.CODE     =  B.TYPE_L(+)            \n");
            headSql.append(" AND     A.GUBUN    = '0052'                  \n");
            headSql.append(" AND     A.LEVELS   = 1                       \n");
            headSql.append(" AND	 B.CLOSE_FG(+) = '1'                  \n");
            headSql.append(" GROUP BY  CODE, CODENM                       \n");
            
            ls = connMgr.executeQuery(headSql.toString());
			
            while (ls.next()) {
                dbox = ls.getDataBox();
                list5.add(dbox);
            }
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }

            list.add(list1);
            list.add(list2);
            list.add(list3);
            list.add(list4);
            list.add(list5);
        }
        catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    /**
    * 나의커뮤니티
    * @param box          receive from the form object and session
    * @return String   나의커뮤니티 리스트
    * @throws Exception
    */
    public String  selectMyCuminity(String v_userid,String v_cmuno) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;

        StringBuffer        sql     = new StringBuffer();
        String              ret     = "";

        try {
            connMgr = new DBConnectionManager();

            sql.append(" SELECT  B.CMUNO CMUNO,B.CMU_NM CMU_NM    \n ");
            sql.append(" FROM    TZ_CMUUSERMST A,TZ_CMUBASEMST B  \n ");
            sql.append(" WHERE   A.CMUNO     = B.CMUNO            \n ");
            sql.append(" AND     A.USERID    = '").append(v_userid).append("' \n ");
            sql.append(" AND     B.CLOSE_FG  IN ('1', '4')        \n ");
            sql.append(" AND     A.CLOSE_FG  ='1'                 \n ");
            sql.append(" ORDER BY B.CMU_NM ASC                    \n ");                                                                        
            // + "	and b.grtype ='" + v_grtype + "'" // gytype 삭제 (통합프로젝트)
            ls = connMgr.executeQuery(sql.toString());
            while (ls.next()) {
                if(ls.getString("cmuno").equals(v_cmuno)){
                  ret += " <option value='"+ls.getString("cmuno")+"' selected>"+ls.getString("cmu_nm")+"</option>";
                } else {
                  ret += " <option value='"+ls.getString("cmuno")+"'>"+ls.getString("cmu_nm")+"</option>";
                }
            }
            ls.close();

        }
        catch (Exception ex) {
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return ret;
    }

    /**
    * 메일전송시 사용하는 사용자정보
    * @param box          receive from the form object and session
    * @return ArrayList   사용자 정보 데이터
    * @throws Exception
    */
    public ArrayList selectSendMailData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();

        String              sql     = "";
        String              sql1    = "";
        DataBox             dbox    = null;
        int                 isOK    =1;
        String v_parent_userid         = box.getString("p_parent_userid");
        String s_userid        = box.getSession("userid");
        String s_name          = box.getSession("name");
        try {
            connMgr = new DBConnectionManager();
            sql  =  " select a.userid         userid  , a.resno          resno     , a.pwd          pwd"
                   +"      , a.name           name    , a.email          email     , a.cono         cono"
                   +"      , a.post1          post1   , a.post2          post2     , a.addr         addr"
                   +"      , a.hometel        hometel , a.handphone      handphone , a.comptel      comptel"
                   +"      , a.tel_line       tel_line, a.comp           comp      , a.indate       indate"
                   +"      , a.lgcnt          lgcnt   , a.lglast         lglast    , a.lgip         lgip" 
                   +"      , a.jikup          jikup   , a.jikupnm        jikupnm   , a.jikwi        jikwi"
                   +"      , a.jikwinm        jikwinm , a.office_gbn     office_gbn, a.office_gbnnm office_gbnnm"
                   +"      , a.work_plc       work_plc, a.work_plcnm     work_plcnm, a.deptcod      deptcod"
                   +"      , a.deptnam        deptnam , a.ldate          ldate     , a.lgfirst      lgfirst"
                   +"      , a.ismailing      ismailing,a.addr2          addr2     , b.gubun        gubun"
                   + "   from tz_member a,tz_compclass b"
                   + "  where a.comp = b.comp "
                   +"     and a.userid        = '"+v_parent_userid+"'"
                  ;
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
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
        return list;
    }



    /**
    * 사용자 정보 조회
    * @param box          receive from the form object and session
    * @return ArrayList   사용자 정보 데이터
    * @throws Exception
    */
    public DataBox selectTz_Member(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet             ls      = null;

        StringBuffer        sql     = new StringBuffer();
        DataBox             dbox    = null;
        int                 isOK    =1;
        String s_userid        = box.getSession("userid");
        String s_name          = box.getSession("name");
        
        String v_cmuno		   = box.getString("p_cmuno");
		
        try {
            connMgr = new DBConnectionManager();
            sql.append(" select  a.userid     , a.resno         , a.pwd                    \n ");
            sql.append("         , a.name     , a.email         , a.cono                   \n ");
            sql.append("         , a.post1    , a.post2         , a.addr                   \n ");
            sql.append("         , a.hometel  , a.handphone     , a.comptel                \n ");
            sql.append("         , a.tel_line , a.comp          , a.indate                 \n ");
            sql.append("         , a.lgcnt    , a.lglast        , a.lgip                   \n ");
            sql.append("         , a.jikup    , a.jikupnm       , a.jikwi                  \n ");
            sql.append("         , a.jikwinm  , a.office_gbn    , a.office_gbnnm           \n ");
            sql.append("         , a.work_plc , a.work_plcnm    , a.deptcod                \n ");
            sql.append("         , a.deptnam  , a.ldate         , a.lgfirst                \n ");
            sql.append("         , a.ismailing, a.addr2         , isnull(c.gubun,'') gubun \n ");
            sql.append("         , decode(b.userid, null, 'N', 'Y') flagYn                 \n ");
            sql.append(" from    tz_member a, tz_cmuusermst b, tz_compclass c              \n ");
            sql.append(" where   a.userid    = b.userid(+)                                 \n ");
            sql.append(" and     a.comp      = c.comp(+)                                   \n ");
            sql.append(" and     b.cmuno(+)  = '").append(v_cmuno).append("'               \n ");
            sql.append(" and     a.userid    = '").append(s_userid).append("'              \n ");

            ls = connMgr.executeQuery(sql.toString());
            while (ls.next()) {
                dbox = ls.getDataBox();
            }

        }
        catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }


    /**
    * 커뮤니티 등급조회
    * @param box          receive from the form object and session
    * @return ArrayList   커뮤니티 등급 데이터
    * @throws Exception
    */
    public ArrayList selectTz_Cmugrdcode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();

        String              sql     = "";
        String              sql1    = "";
        DataBox             dbox    = null;
        int                 isOK    =1;

        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");

        String s_userid        = box.getSession("userid");
        String s_name          = box.getSession("name");
 
        try {
            connMgr = new DBConnectionManager();
            sql  =  " select cmuno,grcode,kor_nm,eng_nm,descript,register_userid,register_dte,modifier_userid,modifier_dte"
                  + "   from tz_cmugrdcode"
                  + "  where cmuno        = '"+v_cmuno+"' "
              	  + " order by grcode asc" ;                  
            ls = connMgr.executeQuery(sql);			
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
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
        return list;
    }



    /**
    * 커뮤니티 탈퇴회원 관리
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int updateCmuUserCloseFg(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet   ls          = null;
        String    sql         = "";
        String    sql1        = "";
        int       isOk        = 0;
        int       v_seq       = 0;

        String    v_cmuno         = box.getString("p_cmuno");
        String    v_close_fg      = box.getString("p_close_fg");
        String    v_intro     = StringManager.replace(box.getString("content"),"<br>","\n");
        String    s_userid        = box.getSession("userid");
        String    s_name          = box.getSession("name");

        try {
             connMgr = new DBConnectionManager();
             connMgr.setAutoCommit(false); 
			 
			 sql= " delete tz_cmuusermst where cmuno='"+v_cmuno+"' and userid='"+s_userid+"' ";
			 isOk=connMgr.executeUpdate(sql);
			 
			 /* 2005.11.16_하경태 : Oracle -> Mssql empty_clob() 제거  
             sql1 = "select close_reason from tz_cmuusermst where cmuno = '" + v_cmuno + "' and userid='"+s_userid+"'";
             connMgr.setOracleCLOB(sql1, v_intro);*/  
			 
			 // 05.12.08 이나연 추가 
			 sql1  = " update tz_cmubasemst set MEMBER_CNT = MEMBER_CNT-1 where cmuno ='"+v_cmuno+"'";
             pstmt = connMgr.prepareStatement(sql1);
             pstmt.executeUpdate();
			 
            if(isOk > 0 ) {
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
            }

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
           if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    /**
     * 커뮤니티 홍보
     * @param box          receive from the form object and session
     * @return ArrayList   커뮤니티 홍보
     * @throws Exception
     */
     public DataBox selectHongbo(RequestBox box) throws Exception {
         DBConnectionManager connMgr = null;
         ListSet             ls      = null;
 		
         StringBuffer sql = new StringBuffer();                                                                                                          
         
         DataBox    dbox    = null;
         
         String v_cmuno = box.getString("p_cmuno");

         try {
             connMgr = new DBConnectionManager();

             // 홍보방
             sql.append(" SELECT  A.CMUNO , A.CMU_NM, A.MEMBER_CNT, A.REGISTER_DTE \n ");
             sql.append("         , B.SAVEFILE, B.CONTENTS                         \n ");
             sql.append(" FROM    TZ_CMUBASEMST A,TZ_CMUHONGBO B                   \n ");
             sql.append(" WHERE   A.CMUNO     	= B.CMUNO                          \n ");
             sql.append(" AND     A.CMUNO  		='").append(v_cmuno).append("'     \n ");

             ls = connMgr.executeQuery(sql.toString());
 			
             while (ls.next()) {
                 dbox = ls.getDataBox();
             }
         }
         catch (Exception ex) {
                ErrorManager.getErrorStackTrace(ex, box, sql.toString());
             throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
         }
         finally {
             if(ls != null) { try { ls.close(); }catch (Exception e) {} }
             if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
         }
         return dbox;
     }

}
