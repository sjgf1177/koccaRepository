//**********************************************************
//1. 제      목: 평가문제지 
//2. 프로그램명: SoExamPaperBean.java
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
public class SoExamPaperBean {

    public SoExamPaperBean() {}


    public ArrayList selectSoExamPaperList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String v_distcode   = box.getString("p_distcode");
        int    v_levels     = box.getInt("p_levels");        
        String v_action     = box.getStringDefault("p_action",  "change"); 
        String p_distcode   = "";

        try {

            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();                
                p_distcode = v_distcode.substring(0, v_levels*3);
                list = getSoExamPaperList(connMgr, p_distcode);
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

    public ArrayList getSoExamPaperList(DBConnectionManager connMgr, String p_distcode) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox    = null;
        String v_subj_bef = "";

        try {
            sql = " select papernum, papernm, distcode, cntlevel1, cntlevel2, cntlevel2, examcnt, ldate  ";
            sql+= " from tz_soexampaper ";
            sql+= " where distcode like '"+p_distcode+"%' ";               
            sql+= " order by papernum   ";

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
                
        try {
            connMgr = new DBConnectionManager();
                        
            sql = " select a.distcode, a.soexamnum, a.soexamtype, a.soexamtext,    a.levels,    ";
            sql+= "       b.codenm    sotypenm,   ";
            sql+= "       c.codenm    levelsnm    ";
            sql+= " from tz_soexam   a, ";
            sql+= "      tz_code     b, ";
            sql+= "      tz_code     c  ";
            sql+= " where a.soexamtype   = b.code ";
            sql+= "   and a.levels       = c.code ";
            sql+= "   and b.gubun        = " + SQLString.Format(SoBean.EXAM_TYPE);
            sql+= "   and c.gubun        = " + SQLString.Format(SoBean.EXAM_LEVEL);

            //if(v_action.equals("go")){
            //    sql += " and a.levels like " + SQLString.Format(ss_levels);
            //    sql += " and a.soexamtype like " + SQLString.Format(ss_type);            
            //}
                       
            sql+= " order by a.soexamnum ";

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

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            //insert TZ_SOEXAMPAPER table
            sql =  " insert into TZ_SOEXAMPAPER (                 ";
            sql+=  "     papernum      , ";
            sql+=  "     papernm       , lessonstart   ,lessonend     ,   exampoint     ,examcnt       ,totalscore    , ";
            sql+=  "     level1text    ,level2text    ,level3text    ,   cntlevel1     ,cntlevel2     ,cntlevel3     , ";
            sql+=  "     luserid       ,ldate         ,distcode   ";
            sql+=  " ) values ( ";
            sql+=  "     (select NVL(max(papernum)+1,1) from TZ_SOEXAMPAPER),        ";
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
            pstmt.setString(15, v_distcode);

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
    
    /**
    평가지  삭제
    @param box    receive from the form object and session
    @return int  
    */    
    public int deleteSoExamPaper(RequestBox box) throws Exception {
          DBConnectionManager connMgr = null;
          int isOk = 0;
          String sql = "";
          int    v_papernum = box.getInt("p_papernum");
          PreparedStatement pstmt = null;

          try {
              connMgr = new DBConnectionManager();
              
              sql =  " delete from          ";
              sql+=  "   tz_soexampaper  ";
              sql+=  " where                ";
              sql+=  "   papernum  = ?    ";

              pstmt = connMgr.prepareStatement(sql);
              pstmt.setInt   (1, v_papernum);
              isOk = pstmt.executeUpdate();
          }
          catch(Exception ex) {
              isOk = 0;
              ErrorManager.getErrorStackTrace(ex);
              throw new Exception(ex.getMessage());
          }
          finally {
              if (pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }            
              if (connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
          }
          return isOk;
      }    
      

    /**
    선택된 문제 리스트
    @param box          receive from the form object and session
    @return ArrayList  
    */
    public ArrayList selectPaperQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        int    v_papernum = box.getInt("p_papernum");

        try {
            connMgr = new DBConnectionManager();
            list = selectPaperQuestionList(connMgr, v_papernum, box);
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

    /**
    선택된 문제 리스트
    @param box          receive from the form object and session
    @return ArrayList   
    */
    public ArrayList selectPaperQuestionList(DBConnectionManager connMgr, int p_papernum, RequestBox box) throws Exception {
        ArrayList list = new ArrayList();
        Hashtable hash = new Hashtable();
        StringTokenizer st = null;

        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        String v_sulnums = "";
        String v_sulnum  = "";
        String v_sulpapernm = "";
        int    v_exampoint = 0; 
        try {
            sql = "select papernm, examcnt, exampoint, level1text,  level2text,  level3text  ";
            sql+= "  from TZ_SOEXAMPAPER ";
            sql+= " where papernum = " + SQLString.Format(p_papernum);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                if(!ls.getString("level1text").equals("")) v_sulnums = ls.getString("level1text");
                if(!ls.getString("level2text").equals("")) v_sulnums = v_sulnums+","+ls.getString("level2text");
                if(!ls.getString("level3text").equals("")) v_sulnums = v_sulnums+","+ls.getString("level3text");
                v_sulnums = v_sulnums+",0";
                
                v_sulpapernm = ls.getString("papernm");
                v_exampoint = ls.getInt("exampoint");
            }
            
System.out.println("v_sulnums>>"+v_sulnums);            
System.out.println("v_sulpapernm>>"+v_sulpapernm);     
System.out.println("v_exampoint>>"+v_exampoint);  
       
            if (box != null) {
                box.put("p_papernm", v_sulpapernm);
                box.put("p_exampoint", v_exampoint+"");                
            }

            if (v_sulnums.length() > 0) {
                /*sql = "select a.subj,     a.sulnum,  ";
                sql+= "       a.distcode, b.codenm  distcodenm, ";
                sql+= "       a.sultype,  c.codenm  sultypenm,  ";
                sql+= "       a.sultext    ";
                sql+= "  from tz_sul    a, ";
                sql+= "       tz_code   b, ";
                sql+= "       tz_code   c  ";
                sql+= " where a.distcode = b.code ";
                sql+= "   and a.sultype  = c.code ";
                sql+= "   and b.gubun    = " + SQLString.Format(SulmunAllBean.DIST_CODE);
                sql+= "   and c.gubun    = " + SQLString.Format(SulmunAllBean.SUL_TYPE);
                sql+= "   and a.subj     = " + SQLString.Format(p_subj);
                sql+= "    and c.levels  = 1  ";
                if (v_sulnums.equals("")) v_sulnums = "-1";
                sql+= "   and a.sulnum in (" + v_sulnums + ")";
                sql+= " order by a.sulnum ";
                */
                
                sql = " select a.distcode, a.soexamnum, a.soexamtype, a.soexamtext,    a.levels,    ";
                sql+= "       b.codenm    sotypenm,   ";
                sql+= "       c.codenm    levelsnm    ";
                sql+= " from tz_soexam   a, ";
                sql+= "      tz_code     b, ";
                sql+= "      tz_code     c  ";
                sql+= " where a.soexamtype   = b.code ";
                sql+= "   and a.levels       = c.code ";
                sql+= "   and b.gubun        = " + SQLString.Format(SoBean.EXAM_TYPE);
                sql+= "   and c.gubun        = " + SQLString.Format(SoBean.EXAM_LEVEL);    
                if (v_sulnums.equals("")) v_sulnums = "-1";
                sql+= "   and a.soexamnum in (" + v_sulnums + ")";
                sql+= " order by a.soexamnum ";
System.out.println(sql);            

                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    dbox = ls.getDataBox();
    
                    list.add(dbox);
                }   
                
            
                /*ls.close();
                ls = connMgr.executeQuery(sql);

                st = new StringTokenizer(",");

                while (ls.next()) {
                    dbox = ls.getDataBox();

                    v_sulnum = String.valueOf(ls.getInt("soexamnum"));

                    hash.put(v_sulnum, dbox);
                }

                while (st.hasMoreElements()) {
                    v_sulnum = (String)st.nextToken();
                    dbox = (DataBox)hash.get(v_sulnum);
                    if (dbox != null) {
                        list.add(dbox);
                    }
                }*/
                
             
                
                
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return list;
    }      
    
        
    /**
    평가지  수정
    @param box                receive from the form object and session
    @return int  
    */
    public int updateSoExamPaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        int     v_papernum 	    = box.getInt   ("p_papernum");
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

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            sql = " update TZ_SOEXAMPAPER  set  ";
            sql+=  "     papernm=?       , lessonstart=?   ,lessonend=?     ,   exampoint=?     ,examcnt=?       ,totalscore=?    , ";
            sql+=  "     level1text=?    ,level2text=?    ,level3text=?    ,   cntlevel1=?     ,cntlevel2=?     ,cntlevel3=?     ,  ";
            sql+=  "     luserid=?       ,ldate=?         ,distcode=?   ";          
            sql+= "  where papernum       = ?  ";


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
            pstmt.setString(15, v_distcode);
            pstmt.setInt   (16, v_papernum);            			

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
}
