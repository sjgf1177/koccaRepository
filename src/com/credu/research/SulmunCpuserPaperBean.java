//**********************************************************
//1. 제      목:
//2. 프로그램명: SulmunCpuserPaperBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-18
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


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SulmunCpuserPaperBean {

    public SulmunCpuserPaperBean() {}


    /**
    업체설문 문제지 리스트 
    @param box          receive from the form object and session
    @return ArrayList   설문문제 리스트
    */
    public ArrayList selectQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String v_gubun    = box.getStringDefault("p_gubun", SulmunCpBean.DEFAULT_SUBJ);
        String v_subj     = box.getStringDefault("s_subjcourse", "ALL");
        String v_distcode = box.getStringDefault("s_distcode","ALL");
        String v_grcode   = box.getStringDefault("s_grcode", SulmunCpBean.DEFAULT_GRCODE);
        String v_action   = box.getStringDefault("p_action",  "change");

        try {
                connMgr = new DBConnectionManager();
                v_subj = v_gubun;
                list = selectQuestionList(connMgr, v_grcode, v_subj, v_distcode);
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
    업체설문 문제지 리스트 
    @param box          receive from the form object and session
    @return ArrayList   설문문제 리스트
    */
    public ArrayList selectQuestionList(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_distcode) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        try {
            sql = "select a.subj,     a.sulnum,  a.grcode, ";
            sql+= "       a.distcode, b.codenm  distcodenm, ";
            sql+= "       a.sultype,  c.codenm  sultypenm,  ";
            sql+= "       a.sultext    ";
            sql+= "  from tz_sul    a, ";
            sql+= "       tz_code   b, ";
            sql+= "       tz_code   c  ";
            sql+= "   where a.distcode  = b.code ";
			sql+= "   and a.sultype  = c.code ";
			sql+= "   and a.subj      = " + SQLString.Format(SulmunCpBean.DEFAULT_SUBJ);
            sql+= "   and a.grcode    = " + SQLString.Format(p_grcode);
			sql+= "   and b.gubun    = " + SQLString.Format(SulmunCpBean.DIST_CODE);
            sql+= "   and c.gubun    = " + SQLString.Format(SulmunCpBean.SUL_TYPE);
            sql+= "   and c.levels    =  1 ";
            if (!p_subj.equals("ALL")) {
                sql+= "   and a.subj     = " + SQLString.Format(p_subj);
            }
            if (!p_distcode.equals("ALL")) {
                sql+= "   and a.distcode = " + SQLString.Format(p_distcode);
            }
            sql+= " order by a.sulnum ";
            System.out.println("sql_cpsul====>>>>>"+sql);
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
    업체설문 문제지 리스트 
    @param box          receive from the form object and session
    @return ArrayList   설문문제 리스트
    */
    public ArrayList selectPaperQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String v_gubun    = box.getStringDefault("p_gubun", SulmunCpBean.DEFAULT_SUBJ);
        String v_grcode      = box.getString("p_grcode");
        String v_subj        = box.getString("p_subj");
        int    v_sulpapernum = box.getInt("p_sulpapernum");

        try {
            connMgr = new DBConnectionManager();
                    v_subj = v_gubun;
            list = selectPaperQuestionList(connMgr, v_grcode, v_subj, v_sulpapernum, box);
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
    업체설문 문제지 리스트 
    @param box          receive from the form object and session
    @return ArrayList   설문문제 리스트
    */
    public ArrayList selectPaperQuestionList(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, RequestBox box) throws Exception {
        ArrayList list = new ArrayList();
        Hashtable hash = new Hashtable();
        StringTokenizer st = null;

        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        String v_sulnums = "";
        String v_sulnum  = "";
        String v_sulpapernm = "";
        try {
            sql = "select sulpapernm, totcnt, sulnums";
            sql+= "  from tz_sulpaper ";
            sql+= " where grcode      = " + SQLString.Format(p_grcode);
            sql+= " and subj        = " + SQLString.Format(p_subj);
            sql+= "   and sulpapernum = " + SQLString.Format(p_sulpapernum);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_sulnums = ls.getString("sulnums");
                v_sulpapernm = ls.getString("sulpapernm");
            }
            if (box != null) {
                box.put("p_sulpapernm",v_sulpapernm);
            }

            if (v_sulnums.length() > 0) {
                sql = "select a.subj,     a.sulnum,  ";
                sql+= "       a.distcode, b.codenm  distcodenm, ";
                sql+= "       a.sultype,  c.codenm  sultypenm,  ";
                sql+= "       a.sultext    ";
                sql+= "  from tz_sul    a, ";
                sql+= "       tz_code   b, ";
                sql+= "       tz_code   c  ";
                sql+= " where a.distcode = b.code ";
                sql+= "   and a.sultype  = c.code ";
                sql+= "   and b.gubun    = " + SQLString.Format(SulmunCpBean.DIST_CODE);
                sql+= "   and c.gubun    = " + SQLString.Format(SulmunCpBean.SUL_TYPE);
                sql+= "   and a.subj     = " + SQLString.Format(p_subj);
                sql+= "    and c.levels  = 1  ";
                if (v_sulnums.equals("")) v_sulnums = "-1";
                sql+= "   and a.sulnum in (" + v_sulnums + ")";

                sql+= " order by a.sulnum ";

                ls.close();
                ls = connMgr.executeQuery(sql);

                st = new StringTokenizer(v_sulnums,SulmunCpBean.SPLIT_COMMA);

                while (ls.next()) {
                    dbox = ls.getDataBox();

                    v_sulnum = String.valueOf(ls.getInt("sulnum"));

                    hash.put(v_sulnum, dbox);
                }

                while (st.hasMoreElements()) {
                    v_sulnum = (String)st.nextToken();
                    dbox = (DataBox)hash.get(v_sulnum);
                    if (dbox != null) {
                        list.add(dbox);
                    }
                }
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
    업체설문 문제지 리스트 sql문
    @param box          receive from the form object and session
    @return String
    */
    public String getPaperListSQL(String p_grcode, String p_subj, String p_gyear, String p_subjsel, String p_upperclass, int p_sulpapernum) throws Exception {
        String sql = "";

			sql = "select a.grcode,       a.subj,    a.subjseq,     ";
            sql+= "       a.sulpapernum,  a.sulpapernm, a.year, a.subjseq, ";
            sql+= "       a.totcnt,       a.sulnums, a.sulmailing, a.sulstart, a.sulend, ";
            sql+= "       'CP'      subjnm, ";
            sql+= "       (select count(*) from tz_sulpapercp x where ";
            sql+= "         x.subj = a.subj and x.grcode = a.grcode ";
            sql+= "         and x.subjseq = a.subjseq and x.year = a.year ";
            sql+= "         and x.sulpapernum = a.sulpapernum ";
            sql+= "         ) cpcnt";
            sql+= "  from tz_sulpaper a";
            sql+= " where a.grcode = " + SQLString.Format(p_grcode);
            sql+= "   and a.subj   = " + SQLString.Format(p_subj);
            if (p_sulpapernum > 0) {
                sql+= "   and sulpapernum   = " + p_sulpapernum;
            }
            sql+= " order by subj, sulpapernum ";
            System.out.println("sql1111===>>>>"+sql);

		return sql;
    }

    /**
    업체설문 문제지 리스트 
    @param box          receive from the form object and session
    @return ArrayList   설문문제 리스트
    */
    public ArrayList selectPaperList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox     dbox = null;

        String v_grcode    = box.getStringDefault("p_grcode", SulmunCpBean.DEFAULT_GRCODE);
        String v_gyear    = box.getStringDefault("p_gyear", box.getString("s_gyear"));
        String v_action    = box.getStringDefault("p_action", "change");
        String v_gubun     = box.getStringDefault("p_gubun",   SulmunCpBean.DEFAULT_SUBJ);

        String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
        String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
        String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
        String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

           try {

            //if (v_action.equals("go")) {

    				ss_subjcourse = v_gubun;
                    sql = getPaperListSQL(v_grcode, v_gubun, v_gyear, ss_subjcourse, ss_upperclass, -1);

                connMgr = new DBConnectionManager();
                ls = connMgr.executeQuery(sql);

                while(ls.next()) {
                    dbox = ls.getDataBox();

                    dbox.put("d_membercount", new Integer(this.getSulmemberCount(connMgr, v_grcode, v_gyear, dbox.getInt("d_sulpapernum"))));
                     list.add(dbox);
                }
            //}

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
    업체설문 문제지 데이타  
    @param box          receive from the form object and session
    @return DataBox
    */
    public DataBox getPaperData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        DataBox     dbox = null;

        String v_grcode    = box.getString("p_grcode");
        String v_subj      = box.getStringDefault("p_subj", "CP");
        String v_gyear    = box.getStringDefault("p_gyear", box.getString("s_gyear"));
        String v_subjsel   = box.getString("p_subjsel");
        String v_upperclass= box.getStringDefault("p_upperclass","ALL");

        int    v_sulpapernum = box.getInt("p_sulpapernum");

        try {
            connMgr = new DBConnectionManager();
            dbox =  getPaperData(connMgr, v_grcode, v_subj, v_gyear, v_subjsel, v_upperclass, v_sulpapernum);

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
    업체설문 문제지 데이타  
    @param box          receive from the form object and session
    @return DataBox
    */
    public DataBox getPaperData(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_gyear, String p_subjsel, String p_upperclass, int p_sulpapernum) throws Exception {
        ListSet ls = null;
        String sql  = "";
        DataBox     dbox = null;

        try {
                sql = getPaperListSQL(p_grcode, p_subj, p_gyear, p_subjsel, p_upperclass, p_sulpapernum);

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
    업체설문 대상자 수 
    @param box          receive from the form object and session
    @return int
    */
    public int getSulmemberCount(DBConnectionManager connMgr, String p_grcode, String p_gyear, int p_sulpapernum) throws Exception {
        ListSet ls = null;
        String sql  = "";
        int result = 0;

        try {
            sql = "select count(*) from tz_sulmember";
            sql+= " where grcode = " + SQLString.Format(p_grcode);
            sql+= "   and subj   = 'TARGET'";
            sql+= "   and year   = " + SQLString.Format(p_gyear);
            if (p_sulpapernum > 0)
                sql+= "   and sulpapernum   = " + p_sulpapernum;

            ls = connMgr.executeQuery(sql);System.out.println(sql);

            if (ls.next()) {
		result = ls.getInt(1);

            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return result;
    }


    /**
    업체설문 설문지 등록 
    @param box          receive from the form object and session
    @return int
    */
    public int insertTZ_sulpaper(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, String p_sulpapernm, int p_totcnt, String p_sulnums, String p_sulmailing, String p_sulstart, String p_sulend, String p_luserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_SULPAPER table
            sql =  "insert into TZ_SULPAPER ";
            sql+=  "(grcode,    subj,     sulpapernum, sulpapernm, ";
            sql+=  " year,      subjseq,                           ";
            sql+=  " totcnt,       sulnums,     sulmailing,        ";
            sql+=  " sulstart, sulend,  luserid,  ldate )          ";
            sql+=  " values ";
            sql+=  "(?,         ?,       ?,         ?,             ";
            sql+=  " ?,         ?,                                 ";
            sql+=  " ?,         ?,       ?,                        ";
            sql+=  " ?,         ?,       ?,         ?)             ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString( 1, p_grcode);
            pstmt.setString( 2, p_subj);
            pstmt.setInt   ( 3, p_sulpapernum);
            pstmt.setString( 4, p_sulpapernm);
            pstmt.setString( 5, FormatDate.getDate("yyyy"));
            pstmt.setString( 6, "0001");
            pstmt.setInt( 7, p_totcnt);
            pstmt.setString( 8, p_sulnums);
            pstmt.setString( 9, p_sulmailing);
            pstmt.setString(10, p_sulstart);
            pstmt.setString(11, p_sulend);
            pstmt.setString(12, p_luserid);
            pstmt.setString(13, FormatDate.getDate("yyyyMMddHHmmss"));

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
    업체설문 설문지 수정  
    @param box          receive from the form object and session
    @return int
    */
    public int updateTZ_sulpaper(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, String p_sulpapernm, int p_totcnt, String p_sulnums, String p_sulmailing, String p_sulstart, String p_sulend, String p_luserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //update TZ_SULPAPER table
            sql = " update TZ_SULPAPER ";
            sql+= "    set sulpapernm = ?, ";
            sql+= "        year       = ?, ";
			sql+= "        totcnt       = ?, ";
            sql+= "        sulnums      = ?, ";
            sql+= "        sulmailing       = ?, ";
            sql+= "        sulstart       = ?, ";
            sql+= "        sulend       = ?, ";
			sql+= "        luserid      = ?, ";
            sql+= "        ldate        = ?  ";
            sql+= "  where grcode       = ?  ";
            sql+= "    and subj         = ?  ";
            sql+= "    and sulpapernum  = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, p_sulpapernm);
            pstmt.setString(2, p_sulstart.substring(0,4));
			pstmt.setInt   (3, p_totcnt);
            pstmt.setString(4, p_sulnums);
            pstmt.setString(5, p_sulmailing);
            pstmt.setString(6, p_sulstart);
            pstmt.setString(7, p_sulend);
            pstmt.setString(8, p_luserid);
            pstmt.setString(9, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(10, p_grcode);
            pstmt.setString(11, p_subj);
            pstmt.setInt   (12, p_sulpapernum);

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
    업체설문 설문지 삭제  
    @param box          receive from the form object and session
    @return int
    */
    public int deleteTZ_sulpaper(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, String p_duserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //delete TZ_SULPAPER table

            sql =  "delete from TZ_SULPAPER ";
            sql+=  " where grcode     = ?  ";
            sql+=  "   and subj       = ?  ";
            sql+=  "   and sulpapernum= ?  ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_grcode);
            pstmt.setString(2, p_subj);
            pstmt.setInt   (3, p_sulpapernum);

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
    업체설문 설문지 등록 
    @param box          receive from the form object and session
    @return int
    */
    public int insertPaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        int isOk = 0;

        String v_grcode = box.getStringDefault("p_grcode",SulmunCpBean.DEFAULT_GRCODE);
        String v_gubun  = box.getStringDefault("p_gubun", SulmunCpBean.DEFAULT_SUBJ);
        String v_subj   = box.getStringDefault("s_subjcourse", "ALL");
        v_subj = v_gubun;

        String v_sulpapernm = box.getString("p_sulpapernm");
        int    v_totcnt       = box.getInt("p_totcnt");
        String v_sulnums      = box.getString("p_sulnums");
        String v_sulmailing      = box.getString("p_sulmailing");

        int    v_sulpapernum  = 0;

        String v_sulstart = box.getString("p_sulstart");
        String v_sulend   = box.getString("p_sulend");

        String v_luserid   = box.getSession("userid");


        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            v_sulpapernum = getPapernumSeq(v_subj, v_grcode);

            isOk = insertTZ_sulpaper(connMgr,  v_grcode, v_subj, v_sulpapernum, v_sulpapernm, v_totcnt, v_sulnums, v_sulmailing, v_sulstart, v_sulend, v_luserid);

		}
        catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if (isOk > 0) {connMgr.commit();}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    업체설문 설문지 수정  
    @param box          receive from the form object and session
    @return int
    */
    public int updatePaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        int isOk = 0;

        String v_grcode = box.getString("p_grcode");
        String v_gubun        = box.getStringDefault("p_gubun", SulmunCpBean.DEFAULT_SUBJ);
        String v_subj         = box.getStringDefault("s_subjcourse", "ALL");
            v_subj = v_gubun;

		int    v_sulpapernum  = box.getInt("p_sulpapernum");
        String v_sulpapernm = box.getString("p_sulpapernm");
        int    v_totcnt       = box.getInt("p_totcnt");
        String v_sulnums      = box.getString("p_sulnums");
        String v_sulmailing      = box.getString("p_sulmailing");

        String v_sulstart = box.getString("p_sulstart");
        String v_sulend   = box.getString("p_sulend");

        String v_luserid  = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            isOk = updateTZ_sulpaper(connMgr,  v_grcode, v_subj, v_sulpapernum, v_sulpapernm, v_totcnt, v_sulnums, v_sulmailing, v_sulstart, v_sulend, v_luserid);

		}
        catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if (isOk > 0) {connMgr.commit();}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    업체설문 설문지 삭제  
    @param box          receive from the form object and session
    @return int
    */
    public int deletePaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        String sql = "";
        int isOk = 0;

        String v_grcode = box.getString("p_grcode");
        String v_gubun        = box.getStringDefault("p_gubun", SulmunCpBean.DEFAULT_SUBJ);
        String v_subj         = box.getStringDefault("s_subjcourse", "ALL");
            v_subj = v_gubun;

		int    v_sulpapernum  = box.getInt("p_sulpapernum");
        String v_duserid  = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            isOk = deleteTZ_sulpaper(connMgr,  v_grcode, v_subj, v_sulpapernum, v_duserid);

        }
        catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (isOk > 0) {connMgr.commit();}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    public int getPapernumSeq(String p_subj, String p_grcode) throws Exception {
        Hashtable maxdata = new Hashtable();
        maxdata.put("seqcolumn","sulpapernum");
        maxdata.put("seqtable","tz_sulpaper");
        maxdata.put("paramcnt","2");
        maxdata.put("param0","subj");
        maxdata.put("param1","grcode");
        maxdata.put("subj",   SQLString.Format(p_subj));
        maxdata.put("grcode",   SQLString.Format(p_grcode));

        return SelectionUtil.getSeq(maxdata);
    }


    /**
    업체설문 설문지 설문번호 구하기  
    @param box          receive from the form object and session
    @return Vector
    */
	public Vector getSulnums(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, String p_year, RequestBox box) throws Exception {
        ListSet ls = null;
        ListSet ls1 = null;
        String sql  = "";
        Vector v_sulnums = new Vector();
        String v_tokens  = "";
        StringTokenizer st = null;
        String v_sulnum = "";
        String sulnum = "";
        int i_sulnum = 0;
        String s_gadmin = box.getSession("gadmin");
        String v_gadmin = StringManager.substring(s_gadmin, 0, 1);

        try {
            sql = "select sulnums  ";
            sql+= "  from tz_sulpaper ";
            sql+= " where grcode      = " + SQLString.Format(p_grcode);
            sql+= "   and subj        = " + SQLString.Format(p_subj);
            sql+= "   and year        = " + SQLString.Format(p_year);
            sql+= "   and sulpapernum = " + p_sulpapernum;
            System.out.println("sql_sulnum1111===>>>>"+sql);
			ls = connMgr.executeQuery(sql);
			
            while (ls.next()) {
                v_tokens = ls.getString("sulnums");
            }
            System.out.println("v_tokens====>>>>>>"+v_tokens);

            st = new StringTokenizer(v_tokens,SulmunCpBean.SPLIT_COMMA);
            while (st.hasMoreElements()) {
            	//sulnum = (String)st.nextToken();
                //
            	//sql = "select sulnum  ";
                //sql+= "  from tz_sul ";
                //sql+= " where grcode = "+SQLString.Format(p_grcode);
                //sql+= "   and subj   = "+SQLString.Format(p_subj);
                //sql+= "   and sulnum = "+SQLString.Format(sulnum);
                //if(v_gadmin.equals("S") || v_gadmin.equals("T") || v_gadmin.equals("M")){
                //  sql+= "   and apptarget   = '1'" ;
                //}
                //System.out.println("sql_num====>>"+sql);
                //ls1 = connMgr.executeQuery(sql);
                //if(ls1.next()){
                //	i_sulnum = ls1.getInt("sulnum");
                //	v_sulnum = String.valueOf(i_sulnum);
                //}
                //if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
                //v_sulnums.add(v_sulnum);
                
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
    
    
    
    public String getCpReplaycnt(RequestBox box) throws Exception {
    	DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        String sql  = "";
        String v_sulnums = "";
        String v_cnt  = "";
        String sulnum = "";
        int i_sulnum = 0;
        String s_gadmin = box.getSession("gadmin");
        String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
        
        String p_grcode      = box.getString("p_grcode");
        String p_gyear       = box.getString("p_gyear");
        String p_subj        = box.getString("p_subj");
        int    p_sulpapernum = box.getInt("p_sulpapernum");
        String p_cpseq       = box.getString("p_cpseq");

        try {
        	connMgr = new DBConnectionManager();
        	
            sql = "select count(*) cnt";
            sql+= "  from tz_suleach ";
            sql+= " where grcode      = " + SQLString.Format(p_grcode);
            sql+= "   and subj        = " + SQLString.Format(p_subj);
            sql+= "   and year        = " + SQLString.Format(p_gyear);
            sql+= "   and sulpapernum = " + p_sulpapernum;
            sql+= "   and userid      = " + SQLString.Format(p_cpseq);
            
            //System.out.println("sql_sulnum1111===>>>>"+sql);
			ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                v_cnt = ls.getString("cnt");
            }
            //System.out.println("v_tokens====>>>>>>"+v_tokens);

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return v_cnt;
    }
    
    /**
    업체설문 설문지 설문보기  구하기  
    @param box          receive from the form object and session
    @return ArrayList
    */
    public ArrayList getSelnums(DBConnectionManager connMgr, String p_grcode, String p_subj, Vector p_sulnums, String p_year, int p_sulpapernum) throws Exception {
        Hashtable hash = new Hashtable();
        ArrayList blist = new ArrayList();
		ArrayList list = null;

        ListSet ls  = null;
        String sql  = "";
        DataBox dbox = null;
        StringTokenizer st = null;

        String v_sulnums = "";
        for (int i=0; i < p_sulnums.size(); i++) {
            v_sulnums += (String)p_sulnums.get(i);
            if (i<p_sulnums.size()-1) {
                v_sulnums += ",";
            }
        }
        
        if (v_sulnums.equals("")) v_sulnums = "-1";

        try {

			st = new StringTokenizer(v_sulnums, ",");

            while(st.hasMoreElements()) {

                int sulnum = Integer.parseInt(st.nextToken());

            sql = "select a.subj,     a.sulnum, a.selmax, ";
            sql+="        a.distcode, c.codenm distcodenm, ";
            sql+= "       a.sultype,  d.codenm sultypenm, ";
            sql+= "       a.sultext,  b.selnum, b.seltext, ";
            sql+= "       b.selpoint, a.apptarget";
            sql+= "  from tz_sul     a, ";
            sql+= "       tz_sulsel  b, ";
            sql+= "       tz_code    c, ";
            sql+= "       tz_code    d  ";
			// 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
//            sql+= " where a.subj     = b.subj(+)   ";
//            sql+= "   and a.sulnum   = b.sulnum(+) ";
//            sql+= "   and a.grcode   = b.grcode(+) ";
			sql+= " where a.subj      =  b.subj(+)   ";
            sql+= "   and a.sulnum    =  b.sulnum(+) ";
            sql+= "   and a.grcode    =  b.grcode(+) ";
			sql+= "   and a.distcode = c.code ";
            sql+= "   and a.sultype  = d.code ";
            sql+= "   and a.grcode     = " + SQLString.Format(p_grcode);
			sql+= "   and a.subj     = " + SQLString.Format(p_subj);
            sql+= "   and a.sulnum = " + sulnum ;
            sql+= "   and c.gubun    = " + SQLString.Format(SulmunCpBean.DIST_CODE);
            sql+= "   and d.gubun    = " + SQLString.Format(SulmunCpBean.SUL_TYPE);
            sql+= "   and d.levels    =  1 ";
            sql+= " order by a.subj, a.sulnum, b.selnum ";
System.out.println("sql_examlist===>>>"+sql);
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
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return blist;
    }

    /**
     설문문제 리스트
    @param box          receive from the form object and session
    @return ArrayList  
    */
    public ArrayList selectPaperQuestionExampleList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String v_grcode      = box.getString("p_grcode");
        String v_gyear       = box.getString("p_gyear");
        String v_subj        = box.getString("p_subj");
        int    v_sulpapernum = box.getInt("p_sulpapernum");

        try {
        	//System.out.println("1111111111111111111111111111111111111");
            if (v_sulpapernum == 0) {
                //System.out.println("222222112222222222222222222222222222222222222");
                v_sulpapernum = getPapernumSeq(v_subj, v_grcode)-1;
                box.put("p_sulpapernum", String.valueOf(v_sulpapernum));
            }

            connMgr = new DBConnectionManager();
            list = selectPaperQuestionExampleList(connMgr, v_grcode, v_subj, v_sulpapernum, v_gyear, box);
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
    @return ArrayList   설문문제 리스트
    */
    public ArrayList selectPaperQuestionExampleList(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, String p_year, RequestBox box) throws Exception {
        Vector    v_sulnums = null;
        ArrayList QuestionExampleDataList  = null;

        try {
                // 설문지번호에 해당하는 설문번호를 vector로 받아온다. 벡터(설문번호1,3,5 ....)
            v_sulnums = getSulnums(connMgr, p_grcode, p_subj, p_sulpapernum, p_year, box);
            if (!v_sulnums.equals("")) {
                // 설문번호에 해당하는 문제리스트를 만든다. 리스트((설문번호1, 보기1,2,3..))
                QuestionExampleDataList = getSelnums(connMgr, p_grcode, p_subj, v_sulnums, p_year, p_sulpapernum);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }

        return QuestionExampleDataList;
    }
    
    
    /**
    외주업체리스트
    @param box          receive from the form object and session
    @return ArrayList	외주업체 리스트
    */
    public ArrayList selectSulCpCompList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        //BulletinData data = null;
        DataBox dbox = null;
        
        String v_subj         = box.getString("p_subj");
        String v_grcode       = box.getString("p_grcode");
        String v_year         = box.getString("p_year");
        String v_subjseq      = box.getString("p_subjseq");
        int    v_sulpapernum  = box.getInt("p_sulpapernum");

        //int v_pageno = box.getInt("p_pageno");
        int v_pageno = 1;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

			sql = "select         \n";
			sql += " a.cpseq,     \n";
			sql += " a.cpnm,      \n";
			sql += " a.cpresno,   \n";
			sql += " a.homesite,  \n";
			sql += " a.address,   \n";
			sql += " a.ldate,     \n";
			sql += " b.userid,    \n";
			sql += " b.pwd,       \n";
			sql += " b.name,      \n";
			sql += " b.email,     \n";
			sql += " b.comptel,   \n";
			sql += " a.compgubun, \n";
			sql += " a.handphone, \n";
			sql += " (select 'checked' from tz_SULPAPERCP x where x.subj='"+v_subj+"' and x.grcode = '"+v_grcode+"' and x.year='"+v_year+"' and x.subjseq = '"+v_subjseq +"' and x.sulpapernum = "+v_sulpapernum+" and x.cpseq = a.cpseq) ischeck \n";
			sql += " from tz_cpinfo a, tz_outmember b \n";
			sql += " where a.userid = b.userid \n";
			sql += " order by a.cpnm asc \n";
			System.out.println(sql);

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
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }


    /**
    업체설문 설문지 등록 
    @param box          receive from the form object and session
    @return int
    */
    public int insertCpinsert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        int isOk = 0;

        String v_subj         = box.getString("p_subj");
        String v_grcode       = box.getString("p_grcode");
        String v_year         = box.getString("p_year");
        String v_subjseq      = box.getString("p_subjseq");
        int    v_sulpapernum  = box.getInt("p_sulpapernum");
        String v_luserid  = box.getSession("userid");
        String v_cpseq = "";



        Vector v_check1     = new Vector();
        v_check1            = box.getVector("p_checks");
        Enumeration em1     = v_check1.elements();

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////
            
            isOk = deleteTZ_sulpaper(connMgr, v_subj, v_grcode, v_year, v_sulpapernum);
            System.out.println("isOk===>>>>"+isOk);

            while(em1.hasMoreElements()){
              v_cpseq    = (String)em1.nextElement();
              isOk = insertTZ_SULPAPERCP(connMgr, v_subj, v_grcode, v_year, v_sulpapernum, v_luserid, v_cpseq);
            }
            if (isOk > 0){
						connMgr.commit();
					} else {
						connMgr.rollback();
					}

		}catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    업체설문 설문지 등록 
    @param box          receive from the form object and session
    @return int
    */
    public int insertTZ_SULPAPERCP(DBConnectionManager connMgr, String p_subj, String p_grcode, String p_year,  int p_sulpapernum, String p_luserid, String p_cpseq) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_SULPAPER table
            sql =  "insert into TZ_SULPAPERCP ";
            sql+=  "(grcode,    subj,     sulpapernum,     ";
            sql+=  " year,      subjseq,                   ";
            sql+=  " luserid,  ldate , cpseq)              ";
            sql+=  " values                                ";
            sql+=  "(?,         ?,       ?,                ";
            sql+=  " ?,         ?,                         ";
            sql+=  " ?,         ?,       ?)                ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString( 1, p_grcode);
            pstmt.setString( 2, p_subj);
            pstmt.setInt   ( 3, p_sulpapernum);
            pstmt.setString( 4, p_year);
            pstmt.setString( 5, "0001");
            pstmt.setString( 6, p_luserid);
            pstmt.setString( 7, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString( 8, p_cpseq);

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
    업체설문 설문지 삭제  
    @param box          receive from the form object and session
    @return int
    */    
    public int deleteTZ_sulpaper(DBConnectionManager connMgr, String p_subj, String p_grcode, String p_year,  int p_sulpapernum) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //delete TZ_SULPAPER table

            sql =  "delete from TZ_SULPAPERCP ";
            sql+=  " where grcode     = ?  ";
            sql+=  "   and subj       = ?  ";
            sql+=  "   and year       = ?  ";
            sql+=  "   and subjseq    = ?  ";
            sql+=  "   and sulpapernum= ?  ";
            


            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_grcode);
            pstmt.setString(2, p_subj);
            pstmt.setString(3, p_year);
            pstmt.setString(4, "0001");
            pstmt.setInt   (5, p_sulpapernum);

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
    업체설문 답변 구하기 
    @param box          receive from the form object and session
    @return String
    */    
    public String getAnswer(RequestBox box) throws Exception {
    	DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls1 = null;
        String sql  = "";
        String v_sulnums = "";
        String v_tokens  = "";
        
        String p_grcode      = box.getString("p_grcode");
        String p_gyear       = box.getString("p_gyear");
        String p_subj        = box.getString("p_subj");
        String p_subjseq     = box.getString("p_subjseq");
        int    p_sulpapernum = box.getInt("p_sulpapernum");
        String p_cpseq       = box.getString("p_cpseq");        

        try {
        	connMgr = new DBConnectionManager();
        	
            sql = "select answers ";
            sql+= "  from tz_suleach ";
            sql+= " where grcode      = " + SQLString.Format(p_grcode);
            sql+= "   and subj        = " + SQLString.Format(p_subj);
            sql+= "   and subjseq     = " + SQLString.Format(p_subjseq);
            sql+= "   and year        = " + SQLString.Format(p_gyear);
            sql+= "   and sulpapernum = " + p_sulpapernum;
            sql+= "   and userid      = " + SQLString.Format(p_cpseq);
            
            System.out.println("sql====>>>>>>"+sql);
            
			ls = connMgr.executeQuery(sql);

            while (ls.next()) {
              v_tokens = ls.getString("answers");
            }
            System.out.println("v_tokens====>>>>>>"+v_tokens);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return v_tokens;
    }

}