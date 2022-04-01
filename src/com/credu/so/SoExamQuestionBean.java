//**********************************************************
//1. 제      목: SO평가 문제 관리
//2. 프로그램명: SoExamQuestionBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: 정은년 2005.8.22
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
public class SoExamQuestionBean {


    public SoExamQuestionBean() {}

    /**
    SO평가 문제 리스트 
    @param box          receive from the form object and session
    @return ArrayList   평가문제 리스트
    */
    public ArrayList selectQuestionList(RequestBox box) throws Exception {
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
                list = getQuestionList(connMgr, p_distcode);
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

    /**
    @param box          receive from the form object and session
    @return ArrayList   평가문제 리스트
    */
    public ArrayList getQuestionList(DBConnectionManager connMgr, String p_distcode) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        try {
            sql = " select a.distcode, a.soexamnum, a.soexamtype, a.soexamtext,    a.soexptext,     ";
            sql+= "       a.levels,   a.selcount,  a.saveimage,   a.saveaudio, a.savemovie, a.saveflash,  a.realimage,   a.realaudio, a.realmovie, a.realflash, ";
            sql+= "       b.codenm    sotypenm,   ";
            sql+= "       c.codenm    levelsnm    ";
            sql+= " from tz_soexam   a, ";
            sql+= "      tz_code     b, ";
            sql+= "      tz_code     c  ";
            sql+= " where a.soexamtype   = b.code ";
            sql+= "   and a.levels       = c.code ";
            sql+= "   and b.gubun        = " + SQLString.Format(SoBean.EXAM_TYPE);
            sql+= "   and c.gubun        = " + SQLString.Format(SoBean.EXAM_LEVEL);
            sql+= "   and a.distcode like '"+p_distcode+"%' ";            
            sql+= " order by a.soexamnum ";

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
    문제 내용 보기
    @param box          receive from the form object and session
    @return QuestionSopleData   평가문제
    */
    public ArrayList selectSoExamData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        int    v_sonum  = box.getInt("p_sonum");
        String v_action   = box.getStringDefault("p_action","change");
        try {
            if (v_sonum > 0) {
                connMgr = new DBConnectionManager();
                list = getSoExamData(connMgr,  v_sonum);
            } else {
                list = new ArrayList();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) {} }
        }
        return list;
    }

    /**
    문제 내용 보기    
    @param box          receive from the form object and session
    @return QuestionSopleData   평가문제
    */
    public ArrayList getSoExamData(DBConnectionManager connMgr, int p_sonum) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        try {
            sql = "select a.soexamnum,  a.soexamtype, ";
            sql+= "       a.soexamtext, a.soexptext, a.levels,      a.selcount,  a.saveimage,   a.saveaudio, ";
            sql+= "       a.savemovie,  a.saveflash, a.realimage,   a.realaudio, a.realmovie, a.realflash, ";
            sql+= "       b.selnum,     b.seltext,   b.isanswer,  ";
            sql+= "       c.codenm    sotypenm, ";
            sql+= "       d.codenm    levelsnm    ";
            sql+= "  from tz_soexam      a, ";
            sql+= "       tz_soexamsel   b, ";
            sql+= "       tz_code      c, ";
            sql+= "       tz_code      d  ";
			// 수정일 : 05.11.11 수정자 : 이나연 _(+)  수정
//          sql+= " where a.soexamnum  = b.soexamnum(+) ";
            sql+= " where a.soexamnum   =  b.soexamnum(+) ";
            sql+= "   and a.soexamtype = c.code ";
            sql+= "   and a.levels     = d.code ";
            sql+= "   and a.soexamnum  = " + SQLString.Format(p_sonum);
            sql+= "   and c.gubun      = " + SQLString.Format(SoBean.EXAM_TYPE);
            sql+= "   and d.gubun      = " + SQLString.Format(SoBean.EXAM_LEVEL);
            sql+= " order by a.soexamnum, b.selnum ";

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
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return list;
    }


    /**
    평가등록
    @param box          receive from the form object and session
    @return int
    */
    public int insertSoExamQuestion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        
        int v_soexamnum = 0;  // 문제번호      
        int v_selnum    = 0;  // 보기번호
        String  v_luserid  = box.getSession("userid");
        String  v_sotype   = box.getString("p_sotype");
        String  v_sotext   = box.getString("p_sotext");
        String  v_exptext  = box.getString("p_exptext");
        String  v_levels   = box.getString("p_levels");
        String  v_distcode = box.getString("p_distcode");

        Vector v_seltexts   = box.getVector("p_seltext");
        String v_seltext    = "";
        Vector v_isanswers  = box.getVector("p_isanswer");
        String v_isanswer   = "";

		int    v_selcount    = box.getInt("p_selcount1");
		String v_saveimage   = box.getNewFileName ("p_img1");
		String v_realimage   = box.getRealFileName("p_img1");
        String v_saveaudio   = box.getNewFileName ("p_audio1");
		String v_realaudio   = box.getRealFileName("p_audio1");
        String v_savemovie   = box.getNewFileName("p_movie1");
		String v_realmovie   = box.getRealFileName("p_movie1");
		String v_saveflash   = box.getNewFileName ("p_flash1");
		String v_realflash   = box.getRealFileName("p_flash1");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            // 문제번호 구하기
            sql = " select NVL(max(soexamnum),0)+1 from TZ_SOEXAM ";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_soexamnum = ls.getInt(1);
            }
            
            // SO평가문제 등록
            isOk = insertTZ_SOEXAM(connMgr, v_soexamnum, v_sotype,    v_sotext,     v_exptext,    v_levels,  v_selcount,   v_saveimage,   v_saveaudio, v_savemovie,   v_saveflash,   v_realimage,   v_realaudio, v_realmovie,   v_realflash, v_luserid, v_distcode );

            // 평가보기 등록
            sql =  " insert into TZ_SOEXAMSEL(soexamnum, selnum, seltext, isanswer, luserid, ldate) ";
            sql+=  " values (?, ?, ?, ?, ?, ?)";
            pstmt = connMgr.prepareStatement(sql);

            for (int i=1; i<=10; i++) {
                v_seltext  = box.getString("p_seltext"+String.valueOf(i));
                if (!v_seltext.trim().equals("")) {
                    v_selnum++;
                    v_isanswer = box.getString("p_isanswer"+String.valueOf(i));
                    isOk = insertTZ_SOEXAMSEL(pstmt, v_soexamnum, v_selnum, v_seltext, v_isanswer, v_luserid);
                }
            }

        }
        catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (isOk > 0) {connMgr.commit();}
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }            
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    평가문제 등록
    @param box          receive from the form object and session
    @return int
    */
    public int insertTZ_SOEXAM(DBConnectionManager connMgr, int p_soexamnum, String  p_sotype, String  p_sotext,     String  p_exptext,   String  p_levels,   int p_selcount, String  p_saveimage,   String  p_saveaudio, 
                            String  p_savemovie, String p_saveflash, String  p_realimage,   String  p_realaudio, String p_realmovie, String p_realflash, String p_luserid, String p_distcode ) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_SOEXAM table
            sql =  " insert into TZ_SOEXAM ";
            sql+=  "            ( soexamnum ,soexamtype,soexamtext,soexptext ,levels    , ";
            sql+=  "              selcount  ,saveimage ,realimage ,saveaudio ,realaudio , ";
            sql+=  "              savemovie ,realmovie ,saveflash ,realflash ,            ";  
            sql+=  "              luserid   ,ldate     ,distcode   )                      ";  
            sql+=  " values ";
            sql+=  "            ( ?,  ?,  ?,  ?,  ?, ";
            sql+=  "              ?,  ?,  ?,  ?,  ?, ";
            sql+=  "              ?,  ?,  ?,  ?,     ";
            sql+=  "              ?,  ?,  ?        ) ";                                    

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt   ( 1, p_soexamnum);
            pstmt.setString( 2, p_sotype);
            pstmt.setString( 3, p_sotext);
            pstmt.setString( 4, p_exptext);
            pstmt.setString( 5, p_levels);
            pstmt.setInt   ( 6, p_selcount);
            pstmt.setString( 7, p_saveimage);
            pstmt.setString( 8, p_realimage);            
            pstmt.setString( 9, p_saveaudio);
            pstmt.setString(10, p_realaudio);            
            pstmt.setString(11, p_savemovie);
            pstmt.setString(12, p_realmovie);            
            pstmt.setString(13, p_saveflash);
            pstmt.setString(14, p_realflash);
            pstmt.setString(15, p_luserid);
            pstmt.setString(16, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(17, p_distcode);            

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
    평가보기 등록
    @param box          receive from the form object and session
    @return int
    */
    public int insertTZ_SOEXAMSEL(PreparedStatement pstmt, int p_soexamnum, int p_selnum, String p_seltext, String p_isanswer, String p_luserid) throws Exception {
        int isOk = 0;                                          
                                                               
        try {            
            pstmt.setInt   (1, p_soexamnum);                                                   
            pstmt.setInt   (2, p_selnum);                      
            pstmt.setString(3, p_seltext);                     
            pstmt.setString(4, p_isanswer);                    
            pstmt.setString(5, p_luserid);                     
            pstmt.setString(6, FormatDate.getDate("yyyyMMddHHmmss"));
            isOk = pstmt.executeUpdate();                      
        }                                                      
        catch(Exception ex) {                                  
            ErrorManager.getErrorStackTrace(ex);               
            throw new Exception(ex.getMessage());              
        }                                                      
        return isOk;
    }

    public int getSonumSeq(String p_subj) throws Exception {
        Hashtable maxdata = new Hashtable();
        maxdata.put("seqcolumn","sonum");
        maxdata.put("seqtable","tz_soexam");
        maxdata.put("paramcnt","1");
        maxdata.put("param0","subj");
        maxdata.put("subj",   SQLString.Format(p_subj));

        return SelectionUtil.getSeq(maxdata);
    }


    /**
    문제 수정
    @param box          receive from the form object and session
    @return int  
    */
    public int updateQuestion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        int    v_soexamnum     = box.getInt("p_examnum");     // 번호
        String v_distcode    = box.getString("p_distcode"); //속성

        String  v_subj     = box.getString("p_subj");
        String  v_grcode    = box.getString("p_grcode");
		int    v_sonum     = box.getInt("p_sonum");
        String  v_lesson   = box.getString("p_lesson");
        String  v_sotype = box.getString("p_sotype");
        String  v_sotext     = box.getString("p_sotext");
        String  v_exptext  = box.getString("p_exptext");
        String  v_levels   = box.getString("p_levels");

        int    v_selnum     = 0;
        Vector v_seltexts   = box.getVector("p_seltext");
        String v_seltext    = "";
        Vector v_isanswers  = box.getVector("p_isanswer");
        String v_isanswer   = "";

        int v_checked_selnum = 0;

        String v_luserid    = box.getSession("userid");

		int    v_selcount     = box.getInt("p_selcount1");

		String v_saveimage       = box.getNewFileName ("p_img1");
		String v_realimage   = box.getRealFileName("p_img1");
        String v_saveaudio     = box.getNewFileName ("p_audio1");
		String v_realaudio   = box.getRealFileName("p_audio1");
        String v_savemovie  = box.getNewFileName("p_movie1");
		String v_realmovie   = box.getRealFileName("p_movie1");
		String v_saveflash      = box.getNewFileName ("p_flash1");
		String v_realflash   = box.getRealFileName("p_flash1");

        String v_imgfile      = box.getString("p_img2");
        String v_audiofile      = box.getString("p_audio2");
        String v_moviefile      = box.getString("p_movie2");
        String v_flashfile      = box.getString("p_flash2");
        String v_realimgfile  = box.getString("p_img3");
        String v_realaudiofile  = box.getString("p_audio3");
        String v_realmoviefile  = box.getString("p_movie3");
        String v_realflashfile  = box.getString("p_flash3");
        int sulcnt  = 10;  // 2005.8.20 by 정은년 
        
        if(v_saveimage.length()       == 0) { v_saveimage       = v_imgfile;     }
        if(v_saveaudio.length()     == 0) { v_saveaudio     = v_audiofile;     }
        if(v_savemovie.length()      == 0) { v_savemovie      = v_moviefile;     }
        if(v_saveflash.length()      == 0) { v_saveflash      = v_flashfile;     }
        if(v_realimage.length()      == 0) { v_realimage      = v_realimgfile;     }
        if(v_realaudio.length()   == 0) { v_realaudio   = v_realaudiofile; }
        if(v_realmovie.length() == 0) { v_realmovie = v_realmoviefile; }
        if(v_realflash.length()  == 0) { v_realflash  = v_realflashfile; }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            isOk = updateTZ_soexam(connMgr,  v_sotype,    v_sotext,     v_exptext,    v_levels,  v_selcount,   v_saveimage,   v_saveaudio, v_savemovie,   v_saveflash,   v_realimage,   v_realaudio, v_realmovie,   v_realflash, v_luserid, v_distcode, v_soexamnum );

            isOk = deleteTZ_soexamsel(connMgr, v_soexamnum);

            // 평가보기 등록
            sql =  " insert into TZ_SOEXAMSEL(soexamnum, selnum, seltext, isanswer, luserid, ldate) ";
            sql+=  " values (?, ?, ?, ?, ?, ?)";
            pstmt = connMgr.prepareStatement(sql);

            for (int i=1; i<=10; i++) {
                v_seltext  = box.getString("p_seltext"+String.valueOf(i));
                if (!v_seltext.trim().equals("")) {
                    v_selnum++;
                    v_isanswer = box.getString("p_isanswer"+String.valueOf(i));
                    isOk = insertTZ_SOEXAMSEL(pstmt, v_soexamnum, v_selnum, v_seltext, v_isanswer, v_luserid);
                }
            }

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
    문제 삭제  
    @param box          receive from the form object and session
    @return int
    */
     public int deleteQuestion(RequestBox box) throws Exception {
          DBConnectionManager connMgr = null;
          int isOk = 0;

          int    v_examnum     = box.getInt("p_examnum");

          try {
              connMgr = new DBConnectionManager();
              connMgr.setAutoCommit(false);////

              isOk = deleteTZ_soexam(connMgr, v_examnum);
              isOk = deleteTZ_soexamsel(connMgr, v_examnum);
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

    // 문제수정
    public int updateTZ_soexam(DBConnectionManager connMgr,
                            String  p_sotype,
                            String  p_sotext,     String  p_exptext,   String  p_levels,   int p_selcount, String  p_saveimage,   String  p_saveaudio, String p_savemovie, 
		                    String p_saveflash, String  p_realimage,   String  p_realaudio, String p_realmovie, String p_realflash, String p_luserid, String p_distcode, int p_soexamnum ) throws Exception {


        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //update TZ_EXAM table

            sql =  " update TZ_SOEXAM ";
            sql+=  "    set soexamtype = ?, ";
            sql+=  "        soexamtext     = ?, ";
            sql+=  "        soexptext  = ?, ";
            sql+=  "        levels   = ?, ";
            sql+=  "        selcount   = ?, ";
            sql+=  "        saveimage   = ?, ";
            sql+=  "        saveaudio = ?, ";
            sql+=  "        savemovie = ?, ";
            sql+=  "        saveflash      = ?, ";
            sql+=  "        realimage   = ?, ";
            sql+=  "        realaudio = ?, ";
            sql+=  "        realmovie  = ?, ";
            sql+=  "        realflash  = ?, ";
            sql+=  "        luserid  = ?, ";
            sql+=  "        ldate    = ?,  ";
            sql+=  "        distcode    = ?  ";
            sql+=  "  where soexamnum     = ?  ";
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString( 1, p_sotype);
            pstmt.setString( 2, p_sotext);
            pstmt.setString( 3, p_exptext);
            pstmt.setString( 4, p_levels);
            pstmt.setInt   ( 5, p_selcount);
            pstmt.setString( 6, p_saveimage);
            pstmt.setString( 7, p_saveaudio);
            pstmt.setString( 8, p_savemovie);
            pstmt.setString( 9, p_saveflash);
            pstmt.setString(10, p_realimage);
            pstmt.setString(11, p_realaudio);
            pstmt.setString(12, p_realmovie);
            pstmt.setString(13, p_realflash);
            pstmt.setString(14, p_luserid);
            pstmt.setString(15, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(16, p_distcode);
            pstmt.setInt   (17, p_soexamnum);

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

    // 문제삭제
    public int deleteTZ_soexam(DBConnectionManager connMgr, int p_sonum) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //update TZ_EXAM table
            sql =  " delete from TZ_SOEXAM ";
            sql+=  "  where soexamnum     = ?  ";

            pstmt = connMgr.prepareStatement(sql);            
            pstmt.setInt   (1, p_sonum);

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

    // 문제보기삭제
    public int deleteTZ_soexamsel(DBConnectionManager connMgr, int p_sonum) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //delete TZ_EXAMSEL table
            sql =  " delete from tz_soexamsel ";
            sql+=  "  where soexamnum     = ?  ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt   (1, p_sonum);

            isOk = pstmt.executeUpdate();
       }
       catch(Exception ex) {
           ErrorManager.getErrorStackTrace(ex);
           throw new Exception(ex.getMessage());
       }
       finally {
           if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
       }
//     return isOk; // 주관식,논술식일경우 없을수 있으므로
       return 1;
    }

}
