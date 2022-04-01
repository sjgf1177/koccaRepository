//**********************************************************
//1. 제      목:
//2. 프로그램명: SoDiagnosPaperBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정:
//
//**********************************************************

package com.credu.so;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;
import com.credu.so.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SoDiagnosPaperBean {

    public SoDiagnosPaperBean() {}


    public ArrayList selectSoExamPaperList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
        String ss_middleclass = box.getStringDefault("s_middleclass","ALL");
        String ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
        String ss_subjcourse  = box.getString("s_subjcourse");
        //String v_action      = box.getStringDefault("p_action","change");
        String v_action      = "go";

        try {
            connMgr = new DBConnectionManager();
            if (v_action.equals("go")) {
                list = getSoExamPaperList(connMgr, box);
            } else {
                list = new ArrayList();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    public ArrayList getSoExamPaperList(DBConnectionManager connMgr,RequestBox box) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox    = null;
        String v_subj_bef = "";
        
        String v_distcode   = box.getString("p_distcode");
        int    v_levels     = box.getInt("p_levels");      
        v_distcode = v_distcode.substring(0, v_levels*3);  

        try {
            sql = " select     ";
            sql+= "   papernum,  ";
            sql+= "   papernm,   ";
            sql+= "   distcode,  ";
            sql+= "   cntlevel1, ";
            sql+= "   cntlevel2, ";
            sql+= "   cntlevel2, ";
            sql+= "   diagnoscnt,"; 
            sql+= "   ldate      ";    
            sql+= " from         ";
            sql+= "   TZ_SODIAGNOSPAPER ";
            sql+= " where ";
            sql+= "   distcode like '"+v_distcode+"%' ";            
            sql+= " order by papernum   ";
System.out.println(sql);
			ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
        }
        return list;
    }

    /**
    평가 문제 상중하 문제수 가져오기
    @param box                receive from the form object and session
    @return ArrayList
    */
    /*public ArrayList selectExamLevels(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList lessonlist = new ArrayList();
		ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        int v_startlesson = 1;
        int v_endlesson   = 1;

		int v_lesson = 0;
		int v_examtype = 0;
		int v_levels = 0;

        try {
            connMgr = new DBConnectionManager();

            for ( int i = v_startlesson; i <= v_endlesson ;  i ++ ){
                 v_lesson = i;
				 ArrayList levelslist = new ArrayList();
                 for ( int j = 1; j <= 3; j++ ){
                     v_levels = j;
                     ArrayList typelist = new ArrayList();
                     for ( int k =1; k <= 3 ; k++){  // by 정은년 (2005.8.20)
                         v_examtype = k;

						 sql = "select count(levels) levelscount  ";
                         sql+= " from TZ_SOEXAM ";
                         sql+= "  where soexamtype = " + SQLString.Format(v_examtype) ;
                         sql+= "  and   levels  = " + SQLString.Format(v_levels) ;  

						 ls = connMgr.executeQuery(sql); 
System.out.println(sql);

			             while (ls.next()) {
                              dbox = ls.getDataBox();
                              typelist.add(dbox);
                         }
					 }
					 levelslist.add(typelist);
                     
                 }
                 lessonlist.add(levelslist);
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
        return lessonlist;
    }*/
    
    
    /**
    SO평가 문제 리스트 
    @param box          receive from the form object and session
    @return ArrayList   평가문제 리스트
    */
    public ArrayList selectExamLevels(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = new ArrayList();
        ListSet ls = null;
        DataBox dbox    = null;
        String p_distcode   = "";
        String sql   = "";

        String v_action     = box.getString("p_action");                                
        String v_distcode   = box.getString("p_distcode");
        int    v_levels     = box.getInt("p_levels");        

        // 검색
        String ss_levels    = box.getString("ss_levels");  
        String ss_type      = box.getString("ss_type");  
        
        p_distcode = v_distcode.substring(0, v_levels*3);
                
        try {
            connMgr = new DBConnectionManager();
                        
            sql = " select a.distcode, a.sodiagnosnum, a.sodiagnostype, a.sodiagnostext,    a.levels,    ";
            sql+= "       b.codenm    sotypenm,   ";
            sql+= "       c.codenm    levelsnm    ";
            sql+= " from tz_soDIAGNOS   a, ";
            sql+= "      tz_code     b, ";
            sql+= "      tz_code     c  ";
            sql+= " where a.sodiagnostype   = b.code ";
            sql+= "   and a.levels       = c.code ";
            sql+= "   and b.gubun        = " + SQLString.Format(SoBean.EXAM_TYPE);
            sql+= "   and c.gubun        = " + SQLString.Format(SoBean.EXAM_LEVEL);
            sql+= "   and a.distcode like '"+p_distcode+"%' ";            

            if(v_action.equals("go")){
                sql += " and a.levels like " + SQLString.Format(ss_levels);
                sql += " and a.sodiagnostype like " + SQLString.Format(ss_type);            
            }
            
            sql+= " order by a.sodiagnosnum ";
System.out.println(sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }            
            if (connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    
        
    /**
    평가지  등록
    @param box                receive from the form object and session
    @return int  
    */
    public int insertSoExamPaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String  v_papernm 	    = box.getString("p_papernm");
        String  v_lessonstart 	= box.getString("p_lessonstart");
        String  v_lessonend   	= box.getString("p_lessonend");
        int     v_exampoint 	= box.getInt   ("p_exampoint");
        int     v_examcnt 		= box.getInt   ("p_examcnt");
        int     v_totalscore 	= box.getInt   ("p_totalscore");
        String  v_level1text  	= box.getString("p_level1text");
        String  v_level2text  	= box.getString("p_level2text");
        String  v_level3text  	= box.getString("p_level3text");
        int  	v_cntlevel1  	= box.getInt("p_cntlevel1");
        int  	v_cntlevel2  	= box.getInt("p_cntlevel2");
        int  	v_cntlevel3  	= box.getInt("p_cntlevel3");
        String  v_distcode  	= box.getString("p_distcode");        
        String  v_luserid   	= box.getSession("userid");
        
        String  v_distcode_list = box.getString("p_distcode_list");        
        System.out.println("v_distcode_list====>>>>>>>>"+v_distcode_list);

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            //insert TZ_SOEXAMPAPER table
            sql =  " insert into TZ_SODIAGNOSPAPER (                 ";
            sql+=  "     papernum      , ";
            sql+=  "     papernm       , lessonstart   ,lessonend     ,   diagnospoint     ,diagnoscnt       ,totalscore    , ";
            sql+=  "     level1text    ,level2text    ,level3text    ,   cntlevel1     ,cntlevel2     ,cntlevel3     , ";
            sql+=  "     luserid       ,ldate         ,distcode   ";
            sql+=  " ) values ( ";
            sql+=  "     (select NVL(max(papernum)+1,1) from TZ_SODIAGNOSPAPER),        ";
            sql+=  "     ?,         ?,         ?,        ?,            ?,               ?,             ";
            sql+=  "     ?,         ?,         ?,        ?,            ?,               ?,             ";
            sql+=  "     ?,         ?,         ?     ) ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString( 1, v_papernm);
            pstmt.setString( 2, v_lessonstart);
            pstmt.setString( 3, v_lessonend);
			pstmt.setInt   ( 4, v_exampoint);
            pstmt.setInt   ( 5, v_examcnt);
            pstmt.setInt   ( 6, v_totalscore);
            pstmt.setString( 7, v_level1text);
            pstmt.setString( 8, v_level2text);
            pstmt.setString( 9, v_level3text);
            pstmt.setInt   (10, v_cntlevel1);
            pstmt.setInt   (11, v_cntlevel2);
            pstmt.setInt   (12, v_cntlevel3);
            pstmt.setString(13, v_luserid);
            pstmt.setString(14, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(15, v_distcode_list);

            isOk = pstmt.executeUpdate();
                       
        }
        catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (isOk > 0) {connMgr.commit();}
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }  
    
    
    
    public int deleteSoExamPaper(RequestBox box) throws Exception {
          DBConnectionManager connMgr = null;
          int isOk = 0;
          String sql = "";
          int    v_papernum = box.getInt("p_papernum");
          PreparedStatement pstmt = null;

          try {
              connMgr = new DBConnectionManager();
              connMgr.setAutoCommit(false);////
              
              //update TZ_EXAM table
              sql =  " delete from          ";
              sql+=  "   TZ_SODIAGNOSPAPER  ";
              sql+=  " where                ";
              sql+=  "   papernum  = ?    ";

              pstmt = connMgr.prepareStatement(sql);

              pstmt.setInt   (1, v_papernum);
              
              isOk = pstmt.executeUpdate();

          }
          catch(Exception ex) {
              isOk = 0;
              connMgr.rollback();
              ErrorManager.getErrorStackTrace(ex);
              throw new Exception(ex.getMessage());
          }
          finally {
              if (isOk > 0) {connMgr.commit(); box.put("p_sulnum", String.valueOf("0"));}
              if (connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
          }
          return isOk;
      }
}
