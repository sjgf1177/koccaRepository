//**********************************************************
//1. 제      목: SO평가 문제 관리
//2. 프로그램명: SoEventBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: 이창훈 2005.8.22
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
public class SoEventBean {


    public SoEventBean() {}

    /**
    SO이벤트 리스트
    @param box          receive from the form object and session
    @return ArrayList   SO이벤트 리스트
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
    @return ArrayList   SO이벤트 리스트
    */
    public ArrayList getQuestionList(DBConnectionManager connMgr, String p_distcode) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        try {

            sql = " select a.distcode, a.soeventnum, a.eventnm, a.explain,    a.special, ";
            sql+= "       a.regname, a.reguserid   ";
            sql+= " from tz_soevent   a ";
            sql+= " where  ";
            sql+= "   a.distcode like '"+p_distcode+"%' ";            
            sql+= " order by a.soeventnum ";
            

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
    내용 보기
    @param box          receive from the form object and session
    @return QuestionSopleData   
    */
    public DataBox selectSoEventData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;
        DataBox dbox = null;

        int    v_sonum  = box.getInt("p_soeventnum");
        String v_action   = box.getStringDefault("p_action","change");
        try {
            if (v_sonum > 0) {
                connMgr = new DBConnectionManager();
                dbox = getSoEventData(connMgr,  v_sonum);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) {} }
        }
        return dbox;
    }

    /**
    내용 보기
    @param  box receive from the form object and session
    @return QuestionSopleData   
    */
    public DataBox getSoEventData(DBConnectionManager connMgr, int p_sonum) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        try {
            sql = " select ";
            sql+= "  a.distcode, a.soeventnum, ";
            sql+= "  a.eventnm,  a.explain,     ";
            sql+= "  a.special,  a.regname,    ";
            sql+= "  a.reguserid               ";
            sql+= "  from tz_soevent a         ";
            sql+= " where                      ";
            sql+= "   a.soeventnum  = " + SQLString.Format(p_sonum);
            System.out.println("1234567890===>>>>>"+sql);

            ls = connMgr.executeQuery(sql);
//Log.info.println("sql>>>"+sql);
            if(ls.next()) {
                dbox = ls.getDataBox();
              //  list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return dbox;
    }


    /**
    이벤트등록
    @param box          receive from the form object and session
    @return int
    */
    public int insertSoEvent(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        int v_soeventnum = 0;
        String  v_luserid  = box.getSession("userid");
        String  v_sotype   = box.getString("p_sotype");
        String  v_sotext   = box.getString("p_sotext");
        String  v_exptext  = box.getString("p_exptext");
        String  v_levels   = box.getString("p_levels");

        String  v_distcode  = box.getString("p_distcode");
        String  v_eventnm   = box.getString("p_eventnm");
        String  v_explain   = box.getString("p_explain");
        String  v_special   = box.getString("p_special");
        String  v_reguserid = box.getSession("userid");
        String  v_regname   = box.getSession("name");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            // 문제번호 구하기
            sql = " select NVL(max(soeventnum),0)+1 from TZ_SOEVENT ";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_soeventnum = ls.getInt(1);
            }
            
            sql  =" insert into TZ_SOEVENT ( ";
            sql +=" soeventnum , eventnm, explain, special, ";
            sql +=" reguserid  , regname, luserid, ldate,   distcode ";
            sql +=" ) ";
            sql +=" values (?, ?, ?, ?, ";
            sql +=" ?, ?, ?, ?, ?)";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt   (1, v_soeventnum);
            pstmt.setString(2, v_eventnm);
            pstmt.setString(3, v_explain);
            pstmt.setString(4, v_special);
            pstmt.setString(5, v_reguserid);
            pstmt.setString(6, v_regname);
            pstmt.setString(7, v_luserid);
            pstmt.setString(8, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(9, v_distcode);

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
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    이벤트 등록
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
    이벤트 등록
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
    이벤트 수정
    @param box          receive from the form object and session
    @return int
    */
    public int updateEvent(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

		int     v_soeventnum = box.getInt("p_soeventnum");
        String  v_eventnm = box.getString("p_eventnm");
        String  v_explain = box.getString("p_explain");
        String  v_special = box.getString("p_special");
        String  v_distcode= box.getString("p_idstcode");
        String  v_luserid    = box.getSession("userid");

		
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            sql =  " update TZ_SOEVENT ";
            sql+=  "    set ";
            sql+=  "     eventnm    = ?,";
            sql+=  "     explain    = ?,";
            sql+=  "     special    = ?,";
            sql+=  "     luserid    = ?,"; 
            sql+=  "     ldate      = ?,";
            sql+=  "     distcode   = ? ";
            sql+=  "  where ";
            sql+=  "    soeventnum  = ?    ";

            pstmt = connMgr.prepareStatement(sql);
            
            pstmt.setString(1, v_eventnm);
            pstmt.setString(2, v_explain);
            pstmt.setString(3, v_special);
            pstmt.setString(4, v_luserid);
            pstmt.setString(5, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(6, v_distcode);
            pstmt.setInt   (7, v_soeventnum);
            
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



     public int deleteEvent(RequestBox box) throws Exception {
          DBConnectionManager connMgr = null;
          int isOk = 0;
          String sql = "";
          String v_subj       = box.getString("p_subj");
          int    v_soeventnum = box.getInt("p_soeventnum");
          String v_duserid    = box.getSession("userid");
          PreparedStatement pstmt = null;

          try {
              connMgr = new DBConnectionManager();
              connMgr.setAutoCommit(false);////
              
              //update TZ_EXAM table
              sql =  " delete from       ";
              sql+=  "   TZ_SOEVENT        ";
              sql+=  " where             ";
              sql+=  "   soeventnum  = ? ";

              pstmt = connMgr.prepareStatement(sql);

              pstmt.setInt   (1, v_soeventnum);
              
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



