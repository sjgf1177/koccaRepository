//**********************************************************
//1. ��      ��: ���� - ������
//2. ���α׷���: SulmunContentsPaperBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-18
//7. ��      ��: �̳��� _ 05.11.21 _ ������ ����â ��������
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
public class SulmunContentsPaperBean {
    public final static String SPLIT_COMMA = ",";
    public final static String SPLIT_COLON = ":";

    public final static String DEFAULT_GRCODE = "N000001";
    public final static String DEFAULT_SUBJ   = "CONTENTS";

    public final static String DIST_CODE        = "0010";
    public final static String SUL_TYPE         = "0011";

    public final static String OBJECT_QUESTION  = "1";
    public final static String MULTI_QUESTION   = "2";
    public final static String SUBJECT_QUESTION = "3";
	public final static String COMPLEX_QUESTION = "4";
    public final static String FSCALE_QUESTION = "5";
    public final static String SSCALE_QUESTION = "6";
    
    
    public final static String F_GUBUN        = "";
    public final static String SUBJECT_SULMUN  = "SUBJ";
    public final static String TARGET_SULMUN = "TARGET";
    public final static String COMMON_SULMUN  = "COMMON";
    public final static String CONTENTS_SULMUN   = "CONTENTS";

    public SulmunContentsPaperBean() {}

    public ArrayList selectQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String v_gubun     ="CONTENTS";
        String v_grcode = "ALL";
		String v_subj     = box.getStringDefault("s_subjcourse", "ALL");
        String v_distcode = box.getStringDefault("s_distcode","ALL");
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
    ���������� ������ ����Ʈ 
    @param box          receive from the form object and session
    @return ArrayList   
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
            sql+= "   and a.grcode    = 'ALL'";
            sql+= "   and a.subj    = 'CONTENTS'";
			sql+= "   and b.gubun    = " + SQLString.Format(SulmunContentsBean.DIST_CODE);
            sql+= "   and c.gubun    = " + SQLString.Format(SulmunContentsBean.SUL_TYPE);
            sql+= "   and c.levels    =  1 ";
   
			if (!p_distcode.equals("ALL")) {
                sql+= "   and a.distcode = " + SQLString.Format(p_distcode);
            }
            sql+= " order by a.sulnum ";
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
    ���������� ������ ����Ʈ 
    @param box          receive from the form object and session
    @return ArrayList   
    */
    public ArrayList selectPaperQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String v_gubun     ="CONTENTS";
        String v_grcode      = "ALL";
        String v_subj        = box.getString("p_subj");
        int    v_sulpapernum = box.getInt("p_sulpapernum");

        try {
            connMgr = new DBConnectionManager();
                    v_subj = v_gubun;
            list = selectPaperQuestionList(connMgr, v_grcode, v_gubun, v_sulpapernum, box);
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
    ���������� ������ ����Ʈ 
    @param box          receive from the form object and session
    @return ArrayList   
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
                sql+= "   and b.gubun    = " + SQLString.Format(SulmunContentsBean.DIST_CODE);
                sql+= "   and c.gubun    = " + SQLString.Format(SulmunContentsBean.SUL_TYPE);
                sql+= "   and a.subj     = " + SQLString.Format(p_subj);
                sql+= "    and c.levels  = 1  ";
                if (v_sulnums.equals("")) v_sulnums = "-1";
                sql+= "   and a.sulnum in (" + v_sulnums + ")";

                sql+= " order by a.sulnum ";

                ls.close();
                ls = connMgr.executeQuery(sql);

                st = new StringTokenizer(v_sulnums,SulmunContentsBean.SPLIT_COMMA);

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
    ���������� ������ SQL�� 
    @param box          receive from the form object and session
    @return String   
    */
    public String getPaperListSQL(String p_grcode, String p_subj, String p_gyear, String p_subjsel, String p_upperclass, int p_sulpapernum) throws Exception {
   
		String sql = "";
        
			sql = "select grcode,       subj,    subjseq,     ";
            sql+= "       sulpapernum,  sulpapernm, year, ";
            sql+= "       totcnt,       sulnums, sulmailing, sulstart, sulend, ";
            sql+= "       'CONTENTS'      subjnm ";
            sql+= "  from tz_sulpaper ";
            sql+= " where grcode = " + SQLString.Format(p_grcode);
            sql+= "   and subj   = " + SQLString.Format(p_subj);
            if (p_sulpapernum > 0) {
                sql+= "   and sulpapernum   = " + SQLString.Format(p_sulpapernum);
            }
            sql+= " order by subj, sulpapernum ";
  
		return sql;
    }

    /**
    ���������� ������ ����Ʈ 
    @param box          receive from the form object and session
    @return ArrayList   
    */
    public ArrayList selectPaperList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox     dbox = null;

        String v_grcode    = "ALL";
        java.util.Date d_now = new java.util.Date();
        String v_gyear    = box.getStringDefault("p_gyear", String.valueOf(d_now.getYear()+1900));
        String v_action    = box.getStringDefault("p_action", "change");
        String v_gubun     ="CONTENTS";

        String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
        String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
        String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
        String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

           try {
                    ss_subjcourse = v_gubun;
                    sql = getPaperListSQL(v_grcode, v_gubun, v_gyear, ss_subjcourse, ss_upperclass, -1);

                connMgr = new DBConnectionManager();
                ls = connMgr.executeQuery(sql);

                String v_null_test = "";
                String v_subj_bef = "";

                while(ls.next()) {
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
    ���������� ������ ����Ÿ  
    @param box          receive from the form object and session
    @return ArrayList   
    */
    public DataBox getPaperData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        DataBox     dbox = null;

        String v_gubun     ="CONTENTS";
        String v_grcode      = "ALL";
        java.util.Date d_now = new java.util.Date();
        String v_gyear    = box.getStringDefault("p_gyear", String.valueOf(d_now.getYear()+1900));
        String v_subjsel   = box.getString("p_subjsel");
        String v_upperclass= box.getStringDefault("p_upperclass","ALL");

        int    v_sulpapernum = box.getInt("p_sulpapernum");

        try {
            connMgr = new DBConnectionManager();
            dbox =  getPaperData(connMgr, v_grcode, v_gubun, v_gyear, v_subjsel, v_upperclass, v_sulpapernum);

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
    ���������� ������ ����Ÿ  
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
    ���������� ������ ���   
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
            sql+=  " year,      subjseq,     ";
            sql+=  " totcnt,       sulnums,     sulmailing,   ";
            sql+=  " sulstart, sulend,  luserid,  ldate )   ";
            sql+=  " values ";
            sql+=  "(?,         ?,       ?,         ?,   ";
            sql+=  " ?,         ?,            ";
            sql+=  " ?,         ?,       ?, ";
            sql+=  " ?,         ?,       ?,         ?) ";

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
            pstmt.setString(10, "");
            pstmt.setString(11, "");
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
    ���������� ������ ����    
    @param box          receive from the form object and session
    @return int   
    */
    public int updateTZ_sulpaper(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, String p_sulpapernm, int p_totcnt, String p_sulnums, String p_sulmailing, String p_sulstart, String p_sulend, String p_luserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_SULPAPER table
            sql =  "update TZ_SULPAPER ";
            sql+=  "   set totcnt    = ?, ";
            sql+=  "       sulnums        = ?, ";
            sql+=  "       luserid    = ?, ";
            sql+=  "       ldate      = ?  ";
            sql+=  " where grcode     = ?  ";
            sql+=  "   and subj       = ?  ";
            sql+=  "   and sulpapernum= ?  ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt( 1, p_totcnt);
            pstmt.setString( 2, p_sulnums);
            pstmt.setString( 3, p_luserid);
            pstmt.setString( 4, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(5, p_grcode);
            pstmt.setString(6, p_subj);
            pstmt.setInt   (7, p_sulpapernum);

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
    ���������� ������ ����    
    @param box          receive from the form object and session
    @return int   
    */
    public int deleteTZ_sulpaper(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, String p_duserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        ListSet ls = null;
        int isOk = 0;

        try {
            // ����üũ
            sql = " select USERID from tz_suleach where grcode='ALL' AND SUBJ!='CP' AND SULPAPERNUM="+p_sulpapernum+" ";
            ls = connMgr.executeQuery(sql);
    		if (ls.next()) {
    		    isOk = -2;
    		}
    		
            if(isOk==0){  

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
    ���������� ������ ���   
    @param box          receive from the form object and session
    @return int   
    */
    public int insertPaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        int isOk = 0;

        String v_grcode       = "ALL";
        String v_gubun     ="CONTENTS";
        String v_subj         = box.getStringDefault("s_subjcourse", "ALL");
            v_subj = v_gubun;
 
        String v_sulpapernm = box.getString("p_sulpapernm");
        int    v_totcnt       = box.getInt("p_totcnt");
        String v_sulnums      = box.getString("p_sulnums");
        int    v_sulpapernum  = 0;

        String v_sulstart = box.getString("p_sulstart");
        String v_sulend   = box.getString("p_sulend");
        String v_sulmailing = "N";

        String v_luserid   = box.getSession("userid");


        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            v_sulpapernum = getPapernumSeq(v_gubun, v_grcode);

            isOk = insertTZ_sulpaper(connMgr,  v_grcode, v_gubun, v_sulpapernum, v_sulpapernm, v_totcnt, v_sulnums, v_sulmailing, v_sulstart, v_sulend, v_luserid);

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
    ���������� ������ ����    
    @param box          receive from the form object and session
    @return int   
    */
    public int updatePaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        int isOk = 0;

        String v_grcode       = "ALL";
        String v_gubun     ="CONTENTS";

		int    v_sulpapernum  = box.getInt("p_sulpapernum");
        String v_sulpapernm = box.getString("p_sulpapernm");
        int    v_totcnt       = box.getInt("p_totcnt");
        String v_sulnums      = box.getString("p_sulnums");

        String v_sulstart = box.getString("p_sulstart");
        String v_sulend   = box.getString("p_sulend");
        String v_sulmailing = "N";

        String v_luserid  = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            isOk = updateTZ_sulpaper(connMgr,  v_grcode, v_gubun, v_sulpapernum, v_sulpapernm, v_totcnt, v_sulnums, v_sulmailing, v_sulstart, v_sulend, v_luserid);

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
    ���������� ������ ����    
    @param box          receive from the form object and session
    @return int   
    */
    public int deletePaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        String sql = "";
        int isOk = 0;

        String v_grcode       = "ALL";
        String v_gubun     ="CONTENTS";

        String v_subj         = box.getStringDefault("s_subjcourse", "ALL");
            v_subj = v_gubun;
     
		int    v_sulpapernum  = box.getInt("p_sulpapernum");
        String v_duserid  = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            isOk = deleteTZ_sulpaper(connMgr,  v_grcode, v_gubun, v_sulpapernum, v_duserid);
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


    /**
    �Ϸù�ȣ 
    @param box          receive from the form object and session
    @return int   
    */
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
    ���������� �������� ������ȣ ���ϱ�    
    @param box          receive from the form object and session
    @return Vector   
    */
	public Vector getSulnums(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum) throws Exception {
        ListSet ls = null;
        String sql  = "";
        Vector v_sulnums = new Vector();
        String v_tokens  = "";
        StringTokenizer st = null;

        try {
            sql = "select sulnums  ";
            sql+= "  from tz_sulpaper ";
            sql+= " where grcode      = " + SQLString.Format(p_grcode);
  //          sql+= " where grcode      = " + SQLString.Format(SulmunContentsBean.DEFAULT_GRCODE);
            sql+= "   and subj        = " + SQLString.Format(p_subj);
            sql+= "   and sulpapernum = " + SQLString.Format(p_sulpapernum);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_tokens = ls.getString("sulnums");
            }

            st = new StringTokenizer(v_tokens,SulmunContentsBean.SPLIT_COMMA);
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
    ���������� ������ �������� ���� 
    @param box          receive from the form object and session
    @return ArrayList   
    */
    public ArrayList getSelnums(DBConnectionManager connMgr, String p_grcode, String p_subj, Vector p_sulnums) throws Exception {
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
            sql+= "       a.sultext,  b.selnum, b.seltext, b.selpoint ";
            sql+= "  from tz_sul     a, ";
            sql+= "       tz_sulsel  b, ";
            sql+= "       tz_code    c, ";
            sql+= "       tz_code    d  ";
			// ������ : 05.11.04 ������ : �̳��� _(+)  ����
//            sql+= " where a.subj     = b.subj(+)    ";
//            sql+= "   and a.sulnum   = b.sulnum(+)  ";
			sql+= " where a.subj     =  b.subj(+)    ";
            sql+= "   and a.sulnum   =  b.sulnum(+)  ";
			
			// ������ : 05.11.21 ������ : �̳��� _ �ܺ������������� 
//            sql+= "   and a.grcode   = b.grcode ";
			sql+= "   and a.grcode    =  b.grcode(+) ";
			sql+= "   and a.distcode = c.code ";
            sql+= "   and a.sultype  = d.code ";
            sql+= "   and a.grcode   = " + SQLString.Format(p_grcode);
			sql+= "   and a.subj     = " + SQLString.Format(p_subj);
            sql+= "   and a.sulnum   = " + sulnum ;
            sql+= "   and c.gubun    = " + SQLString.Format(SulmunContentsBean.DIST_CODE);
            sql+= "   and d.gubun    = " + SQLString.Format(SulmunContentsBean.SUL_TYPE);
            sql+= "   and d.levels   =  1 ";
            sql+= " order by a.subj, a.sulnum, b.selnum ";

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
    �̸����� 
    @param box          receive from the form object and session
    @return ArrayList   �������� ����Ʈ
    */
    public ArrayList selectPaperQuestionExampleList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String v_grcode       = "ALL";
        String v_gubun     ="CONTENTS";
        int    v_sulpapernum = box.getInt("p_sulpapernum");

        try {
            connMgr = new DBConnectionManager();
            list = selectPaperQuestionExampleList(connMgr, v_grcode, v_gubun, v_sulpapernum, box);
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
    @return ArrayList   �������� ����Ʈ
    */
    public ArrayList selectPaperQuestionExampleList(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, RequestBox box) throws Exception {
        Vector    v_sulnums = null;
        ArrayList SulmunQuestionExampleDataList  = null;

        try {
            // ��������ȣ�� �ش��ϴ� ������ȣ�� vector�� �޾ƿ´�. ����(������ȣ1,3,5 ....)
            v_sulnums = getSulnums(connMgr, p_grcode, p_subj, p_sulpapernum);
            if (!v_sulnums.equals("")) {
                // ������ȣ�� �ش��ϴ� ��������Ʈ�� �����. ����Ʈ((������ȣ1, ����1,2,3..))
                SulmunQuestionExampleDataList = getSelnums(connMgr, p_grcode, p_subj, v_sulnums);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }

        return SulmunQuestionExampleDataList;
    }
 

}