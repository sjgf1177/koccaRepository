//**********************************************************
//1. ��      ��: �����׽�
//2. ���α׷���: JindanPaperBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: 2005.12.21
//7. ��      ��:
//
//**********************************************************

package com.credu.jindan;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;
import com.credu.exam.*;

/** * @author Administrator * * To change the template for this generated type 
comment go to * Window>Preferences>Java>Code Generation>Code and Comments */ 
public class JindanPaperBean {


    public JindanPaperBean() {}



    /**
    ���� ����Ʈ  
    @param box
    @return ArrayList
    */
    public ArrayList selectPaperList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String  ss_grcode      = box.getStringDefault("s_grcode", "ALL");           //�����׷�
        String  ss_gyear       = box.getStringDefault("s_gyear", "ALL");            //�⵵
        String  ss_grseq       = box.getStringDefault("s_grseq", "ALL");            //��������
        String  ss_upperclass  = box.getStringDefault("s_upperclass", "ALL");
        String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
        String  ss_lowerclass  = box.getStringDefault("s_lowerclass", "ALL");
        String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");    //����&�ڽ�
        String  ss_year        = "ALL";
        String  ss_subjseq     = box.getStringDefault("s_subjseq","ALL");

        String v_action     = box.getStringDefault("p_action","change");

        try {
            connMgr = new DBConnectionManager();
            if (v_action.equals("go")) {
                list = getPaperList(connMgr, ss_grcode, ss_gyear, ss_grseq, ss_upperclass, ss_middleclass,ss_lowerclass, ss_subjcourse, ss_year, ss_subjseq);
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
    ���� ����Ʈ  
    @param box
    @return ArrayList
    */
    public ArrayList getPaperList(DBConnectionManager connMgr, String p_grcode, String p_gyear,  String p_grseq, String p_upperclass, 
                                  String p_middleclass, String p_lowerclass, String p_subj, String p_year, String p_subjseq) throws Exception {
        ArrayList list = new ArrayList();
        ArrayList blist = null;
        ListSet ls = null;
        String sql  = "";
        DataBox dbox    = null;
        String v_subj_bef = "";

        String sql1 = "";
        String sql2 = "";
        PreparedStatement   pstmt1  = null;
        PreparedStatement   pstmt2  = null;
        int index = 0;
        int v_studentcnt     = 0;
        int v_examstudentcnt = 0;

        try {

            sql = "select b.subj,   a.year,    a.subjseq,   a.lesson, ";
            sql+= "       a.examtype,  a.papernum,   a.lessonstart, a.lessonend,  a.examtime, a.exampoint, a.examcnt, a.totalscore,  ";
            sql+= "       a.cntlevel1, a.cntlevel2, a.cntlevel3, a.isopenanswer, ";
            sql+= "       a.isopenexp,  a.retrycnt, a.progress, b.subjnm,  b.subjseqgr, get_codenm(" + SQLString.Format(JinBean.PTYPE) + ", isnull(a.examtype, '')) examtypenm ";
            sql+= "  from tz_exampaper a, ";
            sql+= "       tz_subjseq b";
			// ������ : 05.11.04 ������ : �̳��� _ (+) ����
//          sql+= " where a.subj(+)     = b.subj  and a.subjseq(+)    = b.subjseq ";
			sql+= " where a.subj        =* b.subj  and a.subjseq       =* b.subjseq ";

            if (!p_subj.equals("ALL"))
                sql+= "   and b.subj       = " + SQLString.Format(p_subj);

            if (!p_year.equals("ALL"))
               sql+= "   and a.year       = " + SQLString.Format(p_year);
            if (!p_subjseq.equals("ALL"))
                sql+= "   and a.subjseq    = " + SQLString.Format(p_subjseq);
            sql+= " order by a.subj, a.year, a.subjseq, a.lesson, a.examtype  ";

            ls = connMgr.executeQuery(sql);//System.out.println(sql);
            while (ls.next()) {
                if (index==0) {
                    sql1 = " select count(*) cnt ";
                    sql1+= "   from tz_student   ";
                    sql1+= "  where subj    = ? ";
                    sql1+= "    and year    = ? ";
                    sql1+= "    and subjseq = ? ";
                    pstmt1 = connMgr.prepareStatement(sql1);

                    sql2 = " select count(*) cnt ";
                    sql2+= "   from tz_examresult   ";
                    sql2+= "  where subj    = ? ";
                    sql2+= "    and year    = ? ";
                    sql2+= "    and subjseq = ? ";
                    sql2+= "    and lesson  = ? ";
                    sql2+= "    and examtype   = ? ";
                    sql2+= "    and papernum= ? ";
                    pstmt2 = connMgr.prepareStatement(sql2);
                }
                dbox = ls.getDataBox();
				v_studentcnt = getStudentCount(pstmt1, p_subj, p_gyear, p_subjseq);

                if (dbox.getInt("d_papernum") == 0) {
                    v_examstudentcnt = 0;
                } else {
                    v_examstudentcnt = getExamStudentCount(pstmt2, dbox);
                }
                    String v_string1 = new String(String.valueOf(v_studentcnt));
                    String v_string2 = new String(String.valueOf(v_examstudentcnt));

                    blist = new ArrayList();
					blist.add(0, dbox);
                    blist.add(1, v_string1);
                    blist.add(2, v_string2);

					
				    list.add(blist);

                index++;
            }
            ls.close();
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
        }
        return list;
    }


    /**
    �л��� ���ϱ� 
    @param box
    @return int
    */
    public int getStudentCount(PreparedStatement pstmt, String p_subj, String p_gyear, String p_subjseq) throws Exception {
        int v_studentcnt = 0;
        ResultSet  rs = null;

        try {
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_gyear);
            pstmt.setString(3, p_subjseq);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                v_studentcnt = rs.getInt("cnt");
            }
            rs.close();
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if (rs != null) { try { rs.close(); } catch (Exception e) {} }
        }

        return v_studentcnt;
    }

    /**
    ������ �л��� ���ϱ� 
    @param box
    @return int
    */
    public int getExamStudentCount(PreparedStatement pstmt, DataBox dbox) throws Exception {
        int v_studentcnt = 0;
        ResultSet  rs = null;

        try {
            pstmt.setString(1, dbox.getString("d_subj"));
            pstmt.setString(2, dbox.getString("d_year"));
            pstmt.setString(3, dbox.getString("d_subjseq"));
            pstmt.setString(4, dbox.getString("d_lesson"));
            pstmt.setString(5, dbox.getString("d_examtype"));
            pstmt.setInt   (6, dbox.getInt("d_papernum"));
            rs = pstmt.executeQuery();
            if (rs.next()) {
                v_studentcnt = rs.getInt("cnt");
            }
            rs.close();
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if (rs != null) { try { rs.close(); } catch (Exception e) {} }
        }

        return v_studentcnt;
    }


    /**
    �������� ����Ÿ 
    @param box
    @return DataBox
    */
    public DataBox selectExamPaperData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;

        String v_subj    = box.getString("p_subj");
        String v_gyear  = box.getString("p_gyear");
        String v_subjseq  = box.getString("p_subjseq");
		String v_lesson  = box.getString("p_lesson");
        String v_examtype   = box.getString("p_examtype");
        int v_papernum  = box.getInt("p_papernum");

        try {
            connMgr = new DBConnectionManager();
            dbox = getExamPaperData(connMgr, v_subj, v_gyear, v_subjseq, v_lesson, v_examtype, v_papernum);
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
    ������ ����
    @param box          receive from the form object and session
    @return DataBox   
    */
    public DataBox getExamPaperData(DBConnectionManager connMgr, String p_subj, String p_gyear, String p_subjseq, String p_lesson, String p_examtype, int p_papernum) throws Exception {
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        try {
            sql = " select a.subj,   a.year,   a.subjseq,   a.lesson, ";
            sql+= "       a.examtype,   a.papernum,  a.lessonstart, a.lessonend, ";
            sql+= "       a.examtime,   a.exampoint,   a.examcnt,  a.totalscore,  a.cntlevel1, a.cntlevel2, a.cntlevel3, a.level1text, a.level2text, a.level3text, ";
            sql+= "       a.isopenanswer,  a.isopenexp, a.retrycnt, a.progress, get_codenm(" + SQLString.Format(JinBean.PTYPE) + ", isnull(a.examtype, '')) examtypenm ";
            sql+= "  from tz_exampaper a ";
            sql+= " where a.subj    = " + SQLString.Format(p_subj);
            sql+= "   and a.year  = " + SQLString.Format(p_gyear);
            sql+= "   and a.subjseq  = " + SQLString.Format(p_subjseq);
			sql+= "   and a.lesson  = " + SQLString.Format(p_lesson);
            sql+= "   and a.examtype   = " + SQLString.Format(p_examtype);
            sql+= "   and a.papernum  = " + SQLString.Format(p_papernum);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
            }
            ls.close();
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
    
    public boolean isExamResult(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql  = "";
        boolean result = false;

        try {
            String v_subj    = box.getString("p_subj");
            String v_gyear  = box.getString("p_gyear");
            String v_subjseq  = box.getString("p_subjseq");
            String v_lesson  = box.getString("p_lesson");
            String v_examtype   = box.getString("p_examtype");
            int v_papernum  = box.getInt("p_papernum");
        
            connMgr = new DBConnectionManager();
            
            sql = "select subj";
            sql+= "  from tz_examresult ";
            sql+= " where subj    = " + SQLString.Format(v_subj);
            sql+= "   and year  = " + SQLString.Format(v_gyear);
            sql+= "   and subjseq  = " + SQLString.Format(v_subjseq);
            sql+= "   and lesson  = " + SQLString.Format(v_lesson);
            sql+= "   and examtype   = " + SQLString.Format(v_examtype);
            sql+= "   and papernum  = " + SQLString.Format(v_papernum);

            ls = connMgr.executeQuery(sql);     //System.out.println(sql);
            if (ls.next()) {
                result = true;
            }
            ls.close();
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return result;
    }
    
    
    /**
    �򰡹�����  ��� 
    @param box
    @return int
    */    
    public int insertExamPaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        ListSet ls = null;
		DataBox dbox = null;
        int isOk = 0;

        String v_subj          = box.getString("p_subj");
        String v_gyear          = box.getString("p_gyear");
        String v_subjseq       = box.getString("p_subjseq");

        String v_luserid    = box.getSession("userid");

        int  v_papernum = 0;

        try {
            connMgr = new DBConnectionManager();
            
            connMgr.setAutoCommit(false);
            v_papernum = getPapernumSeq(v_subj, v_gyear, v_subjseq);

            sql = "select a.subj,      a.lesson, ";
            sql+= "       a.examtype,     a.lessonstart, a.lessonend, ";
            sql+= "       a.examtime,   a.exampoint,   a.examcnt,  a.totalscore,  a.cntlevel1, a.cntlevel2, a.cntlevel3, a.level1text, a.level2text, a.level3text, ";
            sql+= "       a.isopenanswer,  a.isopenexp, a.retrycnt, a.progress ";
            sql+= "  from tz_exammaster a ";
            sql+= " where a.subj    = " + SQLString.Format(v_subj);
    
            ls = connMgr.executeQuery(sql); 

            while (ls.next()) {
                dbox = ls.getDataBox();

                 String v_lesson    = dbox.getString("d_lesson");
                 String v_examtype     = dbox.getString("d_examtype");
                 String v_lessonstart = dbox.getString("d_lessonstart");
                 String v_lessonend   = dbox.getString("d_lessonend");
                 String v_examtime   = dbox.getString("d_examtime");
                 int v_exampoint = dbox.getInt   ("d_exampoint");
                 int v_examcnt = dbox.getInt   ("d_examcnt");
                 int v_totalscore = dbox.getInt   ("d_totalscore");
                 String v_level1text  = dbox.getString("d_level1text");
                 String v_level2text  = dbox.getString("d_level2text");
                 String v_level3text  = dbox.getString("d_level3text");
                 int v_cntlevel1  = dbox.getInt("d_cntlevel1");
                 int v_cntlevel2  = dbox.getInt("d_cntlevel2");
                 int v_cntlevel3  = dbox.getInt("d_cntlevel3");
                 String v_isopenanswer = dbox.getString("d_isopenanswer");
                 String v_isopenexp  = dbox.getString("d_isopenexp");
                 int v_retrycnt = dbox.getInt("d_retrycnt");
                 int v_progress = dbox.getInt("d_progress");

                isOk = insertTZ_exampaper(connMgr, v_subj, v_gyear, v_subjseq, v_lesson, v_examtype, v_papernum, v_lessonstart, v_lessonend, v_examtime, v_exampoint, v_examcnt, v_totalscore, v_cntlevel1, v_cntlevel2, v_cntlevel3, v_level1text, v_level2text, v_level3text, v_isopenanswer, v_isopenexp, v_retrycnt, v_progress, v_luserid);
  
				v_papernum++;
            }
            ls.close();
            
            if (isOk > 0){
							connMgr.commit();
						} else {
							connMgr.rollback();
						}
        }
        catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


	
	  /**
    �򰡹�����  ��� (���������)
    @param subj, year, subseq,userid
    @return int
    */ 
    public int insertExamPaper(String v_subj, String v_gyear, String v_subjseq, String v_luserid) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        ListSet ls = null;
		DataBox dbox = null;
        int isOk = 0;

        int  v_papernum = 0;

        try {
            connMgr = new DBConnectionManager();
            
            connMgr.setAutoCommit(false);
            v_papernum = getPapernumSeq(v_subj, v_gyear, v_subjseq);

            sql = "select a.subj,      a.lesson, ";
            sql+= "       a.examtype,     a.lessonstart, a.lessonend, ";
            sql+= "       a.examtime,   a.exampoint,   a.examcnt,  a.totalscore,  a.cntlevel1, a.cntlevel2, a.cntlevel3, a.level1text, a.level2text, a.level3text, ";
            sql+= "       a.isopenanswer,  a.isopenexp, a.retrycnt, a.progress ";
            sql+= "  from tz_exammaster a ";
            sql+= " where a.subj    = " + SQLString.Format(v_subj);
    
            ls = connMgr.executeQuery(sql); 

            while (ls.next()) {
                dbox = ls.getDataBox();

                 String v_lesson    = dbox.getString("d_lesson");
                 String v_examtype     = dbox.getString("d_examtype");
                 String v_lessonstart = dbox.getString("d_lessonstart");
                 String v_lessonend   = dbox.getString("d_lessonend");
                 String v_examtime   = dbox.getString("d_examtime");
                 int v_exampoint = dbox.getInt   ("d_exampoint");
                 int v_examcnt = dbox.getInt   ("d_examcnt");
                 int v_totalscore = dbox.getInt   ("d_totalscore");
                 String v_level1text  = dbox.getString("d_level1text");
                 String v_level2text  = dbox.getString("d_level2text");
                 String v_level3text  = dbox.getString("d_level3text");
                 int v_cntlevel1  = dbox.getInt("d_cntlevel1");
                 int v_cntlevel2  = dbox.getInt("d_cntlevel2");
                 int v_cntlevel3  = dbox.getInt("d_cntlevel3");
                 String v_isopenanswer = dbox.getString("d_isopenanswer");
                 String v_isopenexp  = dbox.getString("d_isopenexp");
                 int v_retrycnt = dbox.getInt("d_retrycnt");
                 int v_progress = dbox.getInt("d_progress");

                isOk = insertTZ_exampaper(connMgr, v_subj, v_gyear, v_subjseq, v_lesson, v_examtype, v_papernum, v_lessonstart, v_lessonend, v_examtime, v_exampoint, v_examcnt, v_totalscore, v_cntlevel1, v_cntlevel2, v_cntlevel3, v_level1text, v_level2text, v_level3text, v_isopenanswer, v_isopenexp, v_retrycnt, v_progress, v_luserid);
  
				v_papernum++;
            }
            ls.close();
            if (isOk > 0){
							connMgr.commit();
						} else {
							connMgr.rollback();
						}
        }
        catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
                if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


	  /**
    �򰡹�����  ���
    @param connMgr,
    @return int
    */ 
    public int insertTZ_exampaper(DBConnectionManager connMgr, String p_subj, String p_gyear, String p_subjseq, String p_lesson, String p_examtype, int p_papernum, String p_lessonstart, String p_lessonend, 
		                    String p_examtime,    int    p_exampoint,     int p_examcnt,             int p_totalscore, 
                            int    p_cntlevel1,  int    p_cntlevel2,  int    p_cntlevel3,  String p_level1text, String p_level2text, String p_level3text, String p_isopenanswer,
                            String    p_isopenexp,  int     p_retrycnt,       int p_progress,         String p_luserid ) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_EXAMPAPER table
            sql =  " insert into TZ_EXAMPAPER ";
            sql+=  " (subj,   year,   subjseq,   lesson,   ";
            sql+=  "  examtype,  papernum,   lessonstart, lessonend, examtime,  exampoint,  examcnt,  totalscore, ";
            sql+=  "  cntlevel1, cntlevel2, cntlevel3, level1text, level2text, level3text, isopenanswer,  isopenexp, ";
            sql+=  "  retrycnt, progress, luserid,   ldate   ) ";
            sql+=  " values ";
            sql+=  " (?,         ?,        ?,       ?,  ";
            sql+=  "  ?,         ?,         ?,         ?,            ?,               ?,             ?,             ?, ";
            sql+=  "  ?,         ?,         ?,         ?,            ?,                ?,            ?,                 ?,  ";
            sql+=  "  ?,         ?,         ?,       ?  ) ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString( 1, p_subj);
            pstmt.setString( 2, p_gyear);
            pstmt.setString( 3, p_subjseq);
            pstmt.setString( 4, p_lesson);
            pstmt.setString( 5, p_examtype);
			pstmt.setInt   ( 6, p_papernum);
            pstmt.setString( 7, p_lessonstart);
            pstmt.setString( 8, p_lessonend);
            pstmt.setString( 9, p_examtime);
			pstmt.setInt   ( 10, p_exampoint);
            pstmt.setInt   ( 11, p_examcnt);
            pstmt.setInt   (12, p_totalscore);
            pstmt.setInt(13, p_cntlevel1);
            pstmt.setInt(14, p_cntlevel2);
            pstmt.setInt(15, p_cntlevel3);
            pstmt.setString(16, p_level1text);
            pstmt.setString(17, p_level2text);
            pstmt.setString(18, p_level3text);
            pstmt.setString(19, p_isopenanswer);
            pstmt.setString(20, p_isopenexp);
            pstmt.setInt   (21, p_retrycnt);
            pstmt.setInt   (22, p_progress);
            pstmt.setString(23, p_luserid);
            pstmt.setString(24, FormatDate.getDate("yyyyMMddHHmmss"));

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
    �򰡹�����  ���� 
    @param box
    @return int
    */ 
    public int updateExamPaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

		String  v_subj      = box.getString("p_subj");
        String  v_lesson    = box.getString("p_lesson");
        String  v_gyear    = box.getString("p_gyear");
        String  v_subjseq    = box.getString("p_subjseq");
        String  v_examtype     = box.getString("p_examtype");
        int     v_papernum = box.getInt   ("p_papernum");

        String  v_lessonstart = box.getString("p_lessonstart");
        String  v_lessonend   = box.getString("p_lessonend");
        String  v_examtime   = box.getString("p_examtime");
        int     v_exampoint = box.getInt   ("p_exampoint");
        int     v_examcnt = box.getInt   ("p_examcnt");
        int     v_totalscore = box.getInt   ("p_totalscore");
        String  v_level1text  = box.getString("p_level1text");
        String  v_level2text  = box.getString("p_level2text");
        String  v_level3text  = box.getString("p_level3text");
        int  v_cntlevel1  = box.getInt("p_cntlevel1");
        int  v_cntlevel2  = box.getInt("p_cntlevel2");
        int  v_cntlevel3  = box.getInt("p_cntlevel3");
        String  v_isopenanswer = box.getString("p_isopenanswer");
        String  v_isopenexp  = box.getString("p_isopenexp");
        int     v_retrycnt = box.getInt("p_retrycnt");
        int     v_progress = box.getInt("p_progress");

        String  v_luserid   = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            isOk = updateTZ_exampaper(connMgr, v_subj, v_gyear, v_subjseq, v_lesson, v_examtype, v_papernum, v_lessonstart, v_lessonend, v_examtime, v_exampoint, v_examcnt, v_totalscore, v_cntlevel1, v_cntlevel2, v_cntlevel3, v_level1text, v_level2text, v_level3text,  v_isopenanswer, v_isopenexp, v_retrycnt, v_progress, v_luserid);
            
            if (isOk > 0){
							connMgr.commit();
						} else {
							connMgr.rollback();
						}
        }
        catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


	  /**
    �򰡹�����  ���� 
    @param box
    @return int
    */ 
    public int updateTZ_exampaper(DBConnectionManager connMgr, String p_subj, String p_gyear, String p_subjseq, String p_lesson, String p_examtype, int p_papernum, String p_lessonstart, String p_lessonend, 
		                    String p_examtime,    int    p_exampoint,     int p_examcnt,             int p_totalscore, 
                            int    p_cntlevel1,  int    p_cntlevel2,  int    p_cntlevel3,  String p_level1text,  String p_level2text,  String p_level3text,  String p_isopenanswer,
                            String    p_isopenexp,  int     p_retrycnt,          int p_progress,      String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //update TZ_EXAMMASTER table
            sql =  " update TZ_EXAMPAPER ";
            sql+=  "     set   examtime = ?, ";
            sql+=  "        exampoint = ?, ";
            sql+=  "        examcnt = ?, ";
            sql+=  "        totalscore  = ?, ";
            sql+=  "        cntlevel1  = ?, ";
            sql+=  "        cntlevel2 = ?, ";
            sql+=  "        cntlevel3 = ?, ";
            sql+=  "        level1text  = ?, ";
            sql+=  "        level2text = ?, ";
            sql+=  "        level3text = ?, ";
            sql+=  "        isopenanswer  = ?, ";
            sql+=  "        isopenexp = ?, ";
            sql+=  "        retrycnt   = ?, ";
            sql+=  "        progress   = ?, ";
            sql+=  "        luserid     = ?,  ";
            sql+=  "        ldate     = ?  ";
            sql+=  "  where subj      = ?  ";
            sql+=  "    and year    = ?  ";
            sql+=  "    and subjseq    = ?  ";
			sql+=  "    and lesson    = ?  ";
            sql+=  "    and examtype     = ?  ";
            sql+=  "    and papernum     = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString( 1, p_examtime);
            pstmt.setInt   ( 2, p_exampoint);
            pstmt.setInt   ( 3, p_examcnt);
            pstmt.setInt   ( 4, p_totalscore);
            pstmt.setInt( 5, p_cntlevel1);
            pstmt.setInt   ( 6, p_cntlevel2);
            pstmt.setInt   ( 7, p_cntlevel3);
            pstmt.setString( 8, p_level1text);
            pstmt.setString   ( 9, p_level2text);
            pstmt.setString   ( 10, p_level3text);
            pstmt.setString(11, p_isopenanswer);
            pstmt.setString(12, p_isopenexp);
            pstmt.setInt    (13, p_retrycnt);
            pstmt.setInt    (14, p_progress);
            pstmt.setString(15, p_luserid);
            pstmt.setString(16, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(17, p_subj);
            pstmt.setString(18, p_gyear);
            pstmt.setString(19, p_subjseq);
            pstmt.setString(20, p_lesson);
            pstmt.setString(21, p_examtype);
            pstmt.setInt(22, p_papernum);

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
    �򰡹�����  ����  
    @param box
    @return int
    */     
    public int deleteExamPaper(RequestBox box) throws Exception {
         DBConnectionManager connMgr = null;
         int isOk = 0;

	String  v_subj      = box.getString("p_subj");
        String  v_lesson    = box.getString("p_lesson");
        String  v_gyear    = box.getString("p_gyear");
        String  v_subjseq    = box.getString("p_subjseq");
        String  v_examtype     = box.getString("p_examtype");
        int     v_papernum = box.getInt   ("p_papernum");
        String  v_duserid   = box.getSession("userid");

         try {
             connMgr = new DBConnectionManager();
             connMgr.setAutoCommit(false);

             isOk = deleteTZ_exampaper(connMgr, v_subj, v_lesson, v_gyear, v_subjseq, v_examtype, v_papernum);
             
             if (isOk > 0){
							connMgr.commit();
						} else {
							connMgr.rollback();
						}
         }
         catch(Exception ex) {
             isOk = 0;
             connMgr.rollback();
             ErrorManager.getErrorStackTrace(ex);
             throw new Exception(ex.getMessage());
         }
         finally {
             if (isOk > 0) {box.put("p_sulnum", String.valueOf("0"));}
             if (connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
         }
         return isOk;
    }


	  /**
    �򰡹�����  ���� 
    @param box
    @return int
    */ 
    public int deleteTZ_exampaper(DBConnectionManager connMgr, String p_subj, String p_lesson, String p_gyear, String p_subjseq, String p_examtype, int p_papernum) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //update TZ_EXAMMASTER table
            sql =  " delete from TZ_EXAMPAPER ";
            sql+=  "  where subj      = ?  ";
            sql+=  "    and year    = ?  ";
            sql+=  "    and subjseq    = ?  ";
      //      sql+=  "    and lesson    = ?  ";
     //       sql+=  "    and examtype     = ?  ";
       //     sql+=  "    and papernum     = ?  ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_gyear);
            pstmt.setString(3, p_subjseq);
       //     pstmt.setString(4, p_lesson);
       //     pstmt.setString(5, p_examtype);
      //      pstmt.setInt(6, p_papernum);

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
    ���ܹ��� ����Ʈ  
    @param box
    @return Vector
    */     
    public Vector SelectQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
		Vector v_examnums = null;

		
        String v_subj    = box.getString("p_subj");
        String v_gyear    = box.getString("p_gyear");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson  = box.getString("p_lesson");
        String v_examtype   = box.getString("p_examtype");
        int    v_papernum= box.getInt("p_papernum");
        String v_lessonstart = box.getString("p_lessonstart");
        
        try {
            connMgr = new DBConnectionManager();

			//���ܹ�����ȣ
            v_examnums = getExamnums(connMgr, v_subj, v_gyear, v_subjseq, v_lesson, v_lessonstart, v_examtype,  v_papernum, box);
   
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
    ����� - �����׽�Ʈ���� ������������  ------------------------------#1�ӽ�
    @param box          receive from the form object and session
    @return ArrayList   
    */	
	public ArrayList SelectPaperQuestionJindanList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
	    	Vector v_examnums = null;
        ArrayList QuestionJindanDataList  = null;
  
		
        try {

            connMgr = new DBConnectionManager();

			//-------------------------------------------------------------------
            // ���ܹ��� ��ȣ
            //v_examnums = getExamnums(connMgr,  v_subjclass, box);  //�򰡽� ���
			//v_examnums = getJindannums(connMgr,  v_subjclass, box);	//���ܽ� ��� - �۾���

			//-------------------------------------------------------------------
            // �򰡹�ȣ�� �ش��ϴ� ��������Ʈ�� �����. ����Ʈ((������ȣ1, ����1,2,3..))
            QuestionJindanDataList = getJindanData(connMgr, box );


			if(QuestionJindanDataList==null){
			    QuestionJindanDataList = new ArrayList();
			}
        }
        catch (Exception ex) {ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) {} }
        }
        return QuestionJindanDataList;
    }


    /**
    ���ܹ�ȣ�� �ش��ϴ� ��������Ʈ ��������  ------------------#2
    @param box          receive from the form object and session
    @return QuestionExampleData   
    */
    public ArrayList getJindanData(DBConnectionManager connMgr, RequestBox box) throws Exception {

		ArrayList list = null;	//�ϳ��� ����,����
		ArrayList bList = new ArrayList();	//�ϳ��� ����,����
		ArrayList subjList = new ArrayList();
		Vector v_realnums = new Vector();
		Vector v_examnums = new Vector();

		String sql = "";
		String sql2 = "";
		String sql3 = "";				//�ش������ ���ܹ��� ����
		String v_class1 = box.getStringDefault("class1","");
		String v_class2 = box.getStringDefault("class2","");
		String v_class3 = box.getStringDefault("class3","");
		ListSet ls = null;
		ListSet ls2 = null;
		ListSet ls3 = null;
		DataBox dbox = null;
		
		try{

			//------------------------------------------------------------------------
			//�ش����class�� �ش��ϴ� ����list
			sql2 += " select distinct c.subj  from tz_subjatt a, tz_subj  b,  tz_jin c   \n" ;
			sql2 +=" where a.subjclass = b.subjclass  \n" ;
			sql2 +=" and  b.subj = c.subj  \n" ;
			if(!v_class1.equals("")){
			sql2 +=" and b.upperclass = "+ SQLString.Format(v_class1);
			}
			if(!v_class2.equals("")){
			sql2 +=" and b.middleclass = "+ SQLString.Format(v_class2);
			}
			if(!v_class3.equals("")){
			sql2 +=" and b.lowerclass =  "+ SQLString.Format(v_class3);
			}
			System.out.print(sql2);

			ls2 = connMgr.executeQuery(sql2);

			while(ls2.next()){
				subjList.add(ls2.getString(1));

			}
			ls2.close();

			
			//------------------------------------------------------------------------
			//  ���� �������� ���鼭 5������ �������� ���� ��������
			String subjNo = "";

			for( int i=0 ; i <subjList.size() ; i++){

				subjNo = (String)subjList.get(i);
				System.out.println("Ȯ�� subjNo:"+subjNo);

				//�ش������ ����  ���ܹ�����ȣ ��δ��

				sql3   = " select  jinnum   from  tz_jin    \n  ";
				sql3 +="	 where subj =  "+ SQLString.Format(subjNo);
				sql3 +="	 order by  jinnum   \n";

				ls3 = connMgr.executeQuery(sql3);

				while(ls3.next()){
					v_realnums.add(ls3.getString(1));

				}
				ls3.close();

			
				int  ss = v_realnums.size();
				System.out.print("ss:"+ss);

				// ���� �ѹ��� index���� �������� ���� index ��������
				 Random ran = new Random();
				 int jinCnt = 5;		// ���� 5������
				 int [] num =new int [jinCnt];
				 int bun = 0;

				
				 for(int q = 0 ; q < jinCnt ; q++){ 
					 System.out.println("���������� q:"+q);
					 bun = ran.nextInt(ss); 
					 System.out.println("���������� bun:"+bun);
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
				 
					 System.out.println("���������� num.length:"+num.length);
					 System.out.println("���������� num:"+num);
				 for (int p=0; p < num.length ; p++){
					 v_examnums.add((String)v_realnums.get(num[p]));
				 }
				
				System.out.println("v_examnums.size():"+v_examnums.size());

				// ���� ������ 5���� ���
				for(int j=0; j < v_examnums.size() ; j++){
					System.out.println("j ����: "+j);

						//�ӽ����������� ��ü
						/*
						sql = " select   a.subj,     a.jinnum,  a.lesson,  a.jintype,  a.jintext,   \n  ";
						sql+="		a.exptext,   a.levels,  a.selcount,  a.saveimage,   a.saveaudio,  \n";
						sql+="		a.savemovie,  a.saveflash, a.realimage,   a.realaudio, a.realmovie, a.realflash,  \n";
						sql+="		b.selnum,   b.seltext,  b.isanswer  \n";
						sql+="		from  tz_jin      a,           tz_jinsel   b  \n";
						sql+="		where a.subj      =  b.subj (+) \n";
						sql+="		and a.jinnum   =  b.jinnum (+) \n";
						sql+="	 	and a.subj = "+ SQLString.Format(subjNo);
						sql+="		order by  a.subj, a.jinnum, b.selnum ";
						*/

						sql = " select a.subj,     a.jinnum,  a.lesson,  a.jintype,   \n";
						sql+= "       a.jintext,      a.exptext,   a.levels,  a.selcount,  a.saveimage,   a.saveaudio, \n";
						sql+= "       a.savemovie,  a.saveflash, a.realimage,   a.realaudio, a.realmovie, a.realflash, \n";
						sql+= "       b.selnum,   b.seltext,  b.isanswer,  \n";
						sql+= "       c.codenm    jintypenm, \n";
						sql+= "       d.codenm    levelsnm    \n";
						sql+= "  from tz_jin      a, \n";
						sql+= "       tz_jinsel   b, \n";
						sql+= "       tz_code      c, \n";
						sql+= "       tz_code      d  \n";
						sql+= " where a.subj      =  b.subj(+) \n";
						sql+= "   and a.jinnum   =  b.jinnum(+) \n";
						sql+= "   and a.jintype  = c.code \n";
						sql+= "   and a.levels    = d.code \n";
						sql+= "   and a.subj      = " + SQLString.Format(subjNo);
						sql+= "   and a.jinnum   = " + (String)v_examnums.get(j);
						sql+= "   and c.gubun     = " + SQLString.Format(JinBean.JIN_TYPE);
						sql+= "   and d.gubun     = " + SQLString.Format(JinBean.JIN_TYPE);
						sql+= "  order by a.jinnum, b.selnum \n";

						System.out.println(sql);
						list =  new ArrayList();


						ls = connMgr.executeQuery(sql);

						while (ls.next()) {
							dbox = ls.getDataBox();
							list.add(dbox);
						}
						ls.close();
						
						bList.add(list);


					}
						v_examnums.clear()  ;		//����

			}

		}catch (Exception ex) {
		ErrorManager.getErrorStackTrace(ex);
		throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(ls3 != null) { try { ls3.close(); } catch (Exception e) {} }
        }

					//System.out.println("//JindanPaperBean���ܹ��� list: "+list.size());
					//System.out.println("//JindanPaperBean���ܹ��� list: "+list);
		return bList;
    }



    /**
    �򰡹�ȣ�� �ش��ϴ� ��������Ʈ 
    @param box          receive from the form object and session
    @return QuestionExampleData   �򰡹���
    */
    public ArrayList getExampleData(DBConnectionManager connMgr, String p_subj,  Vector p_examnums) throws Exception {
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

            sql = "select a.subj,     a.examnum,  a.lesson,  a.examtype, ";
            sql+= "       a.examtext,      a.exptext,   a.levels,  a.selcount,  a.saveimage,   a.saveaudio, ";
            sql+= "       a.savemovie,  a.saveflash, a.realimage,   a.realaudio, a.realmovie, a.realflash, ";
            sql+= "       b.selnum,   b.seltext,  b.isanswer,  ";
            sql+= "       c.codenm    examtypenm, ";
            sql+= "       d.codenm    levelsnm    ";
            sql+= "  from tz_exam      a, ";
            sql+= "       tz_examsel   b, ";
            sql+= "       tz_code      c, ";
            sql+= "       tz_code      d  ";
			sql+= " where a.subj      =  b.subj(+) ";
            sql+= "   and a.examnum   =  b.examnum(+) ";
            sql+= "   and a.examtype  = c.code ";
            sql+= "   and a.levels    = d.code ";
            sql+= "   and a.subj      = " + SQLString.Format(p_subj);
            sql+= "   and a.examnum   = " + examnum;
            sql+= "   and c.gubun     = " + SQLString.Format(JinBean.JIN_TYPE);
            sql+= "   and d.gubun     = " + SQLString.Format(JinBean.JIN_TYPE);
            sql+= " order by a.examnum, b.selnum ";

            ls = connMgr.executeQuery(sql);
			list =  new ArrayList();

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);

			}
			ls.close();
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
    �� ��ȣ ���ϱ�
    @param box          receive from the form object and session
    @return Vector
    */
    public Vector getExamnums(DBConnectionManager connMgr,
                        String p_subj, String p_gyear, String p_subjseq, String p_lesson, String p_lessonstart, String p_examtype,
                        int p_papernum, RequestBox box) throws Exception {
   
		ArrayList examlist   = new ArrayList();
		ArrayList list = null;
		ArrayList list2 = null;
		Vector v_lessons = null;
        Vector v_examnums = new Vector();
        Vector v_realrkeys = null; //jkh 0228
		Vector v = null;
        Vector v_realnums = null;
		Vector v_level1Obnums = null;
		Vector v_level2Obnums = null;
		Vector v_level3Obnums = null;
		Vector v_level1Subnums = null;
		Vector v_level2Subnums = null;
		Vector v_level3Subnums = null;
		Vector v_level1OXnums = null;
		Vector v_level2OXnums = null;
		Vector v_level3OXnums = null;
		
		int lessonstart = Integer.parseInt(p_lessonstart);

		try {
     //       examlist   = getPaperQuestionList(connMgr, p_subj, p_gyear, p_subjseq, p_lesson, p_examtype, p_papernum, box);
	          list = getLevelQuestionList(connMgr, p_subj, p_gyear, p_subjseq, p_lesson, p_examtype, p_papernum, box);

			  v_lessons = (Vector)list.get(0);
              v_level1Obnums = (Vector)list.get(1);
              v_level2Obnums = (Vector)list.get(2);
              v_level3Obnums = (Vector)list.get(3);
              v_level1Subnums = (Vector)list.get(4);
              v_level2Subnums = (Vector)list.get(5);
              v_level3Subnums = (Vector)list.get(6);
              v_level1OXnums = (Vector)list.get(7);
              v_level2OXnums = (Vector)list.get(8);
              v_level3OXnums = (Vector)list.get(9);


              v_realnums = new Vector();
              v_realrkeys = new Vector();
              String numandrkey =""; 
              StringTokenizer st1= null;	


              for (int i = 0; i < v_lessons.size() ; i++){
				  for (int j = 1; j <= 3; j++){
					  Integer type = new Integer(j);
					  for (int k = 1; k <= 3 ; k++){
						  Integer level = new Integer(k);

             			  v = getQuestionList(connMgr, p_subj, (String)v_lessons.get(i), type.toString() , level.toString());
						  Random ran = new Random();

                                
                          if(j==1 && k ==1){
						     int  ss = Integer.parseInt((String)v_level1Obnums.get(i));
								     
							 if(v.size() > 0 && ss > 0){//System.out.println("ss" + ss);
							     for (int p=0; p < ss ; p++){							        
							     	numandrkey = (String)v.get(p); //System.out.println("numandrkey" + numandrkey);
							     	st1 = new StringTokenizer(numandrkey, ",");
							     	v_realnums.add(st1.nextToken());
							     	v_realrkeys.add(st1.nextToken());
     	
							     }
							 }
						  } else if(j==1 && k ==2){
						     int  ss = Integer.parseInt((String)v_level2Obnums.get(i));
							 if(v.size() > 0 && ss > 0){
							     for (int p=0; p < ss ; p++){
									 numandrkey = (String)v.get(p); 
							     	st1 = new StringTokenizer(numandrkey, ",");
							     	v_realnums.add(st1.nextToken());
							     	v_realrkeys.add(st1.nextToken());
							     }
							 }
						  } else if(j==1 && k ==3){
						     int  ss = Integer.parseInt((String)v_level3Obnums.get(i));
							 if(v.size() > 0 && ss > 0){
							     for (int p=0; p < ss ; p++){
									 numandrkey = (String)v.get(p); 
							     	st1 = new StringTokenizer(numandrkey, ",");
							     	v_realnums.add(st1.nextToken());
							     	v_realrkeys.add(st1.nextToken());
							     }
							 }
						  } else if(j==2 && k ==1){
						     int  ss = Integer.parseInt((String)v_level1Subnums.get(i));
							 if(v.size() > 0 && ss > 0){
							     for (int p=0; p < ss ; p++){
									 numandrkey = (String)v.get(p); 
							     	st1 = new StringTokenizer(numandrkey, ",");
							     	v_realnums.add(st1.nextToken());
							     	v_realrkeys.add(st1.nextToken());
							     }
							 }
						  } else if(j==2 && k ==2){
						     int  ss = Integer.parseInt((String)v_level2Subnums.get(i));
							 if(v.size() > 0 && ss > 0){
							     for (int p=0; p < ss ; p++){
									 numandrkey = (String)v.get(p); 
							     	st1 = new StringTokenizer(numandrkey, ",");
							     	v_realnums.add(st1.nextToken());
							     	v_realrkeys.add(st1.nextToken());
							     }
							 }
						  } else if(j==2 && k ==3){
						     int  ss = Integer.parseInt((String)v_level3Subnums.get(i));
							 if(v.size() > 0 && ss > 0){
							     for (int p=0; p < ss ; p++){
									 numandrkey = (String)v.get(p); 
							     	st1 = new StringTokenizer(numandrkey, ",");
							     	v_realnums.add(st1.nextToken());
							     	v_realrkeys.add(st1.nextToken());
							     }
							 }
							 
                         }else if(j==3 && k ==1){
						     int  ss = Integer.parseInt((String)v_level1OXnums.get(i));
								     
							 if(v.size() > 0 && ss > 0){//System.out.println("ss" + ss);
							     for (int p=0; p < ss ; p++){							        
							     	numandrkey = (String)v.get(p); //System.out.println("numandrkey" + numandrkey);
							     	st1 = new StringTokenizer(numandrkey, ",");
							     	v_realnums.add(st1.nextToken());
							     	v_realrkeys.add(st1.nextToken());
     	
							     }
							 }
						  } else if(j==3 && k ==2){
						     int  ss = Integer.parseInt((String)v_level2OXnums.get(i));
							 if(v.size() > 0 && ss > 0){
							     for (int p=0; p < ss ; p++){
									 numandrkey = (String)v.get(p); 
							     	st1 = new StringTokenizer(numandrkey, ",");
							     	v_realnums.add(st1.nextToken());
							     	v_realrkeys.add(st1.nextToken());
							     }
							 }
						  } else if(j==3 && k ==3){
						     int  ss = Integer.parseInt((String)v_level3OXnums.get(i));
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
              }

			  int  ss = v_realnums.size();
		  
							 //���� ���ϱ�
                      /*       Random ran = new Random();
                             boolean[] b = new boolean[ss];
							 int [] i = new int [ss];
							 int cnt=0;
							 while (cnt<ss) {
								 int r = ran.nextInt(ss); 
								 if (b[r]) continue;
								 else {
									 b[r] = true;
									 i[cnt] = r; 
									 cnt++;
                                 }
                             }

                             int [] num =new int [ss];
							 int idx = 0;
							 for (int k=0; k<b.length; k++) {
								 if (b[k]) num[idx++] = i[k];
                             }*/
                             
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
							 

							     for (int p=0; p < num.length ; p++){
									 v_examnums.add((String)v_realnums.get(num[p]));
							     }

        }
        catch (Exception ex) {ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
      
        return v_examnums;
    }

    /**
    �����׽�Ʈ ��ȣ ���ϱ�   @@@--------------------------------------------------------------------------
    @param box          receive from the form object and session
    @return Vector
    */
    public Vector getJindannums(DBConnectionManager connMgr,
                        String p_subjclass,   RequestBox box) throws Exception {


		Vector v_examnums = new Vector();
		ArrayList subjList = new ArrayList();
		String sql = "";
		String sql2 = "";
		String v_subjclass = "";
		PreparedStatement   pstmt  = null;
		PreparedStatement   pstmt2  = null;
		ListSet ls = null;
		DataBox dbox = null;


		// p_subjclass(���Ǻз��ڵ�)�� �޾� �ش� �����ڵ� list ��������		
		sql="  select subj  from TZ_SUBJ	where subjclass ='G01001001' and isonoff='ON'  ";
	  
		ls = connMgr.executeQuery(sql);
        while (ls.next()) {
					
                dbox = ls.getDataBox();
				subjList.add(dbox.getString("d_subj"));
		}



		//�ӽ÷����������� ��ü
		sql2 = "select   a.subj,     a.jinnum,  a.lesson,  a.jintype,  a.jintext,   \n  "
                +"		a.exptext,   a.levels,  a.selcount,  a.saveimage,   a.saveaudio,  \n"
				+"		a.savemovie,  a.saveflash, a.realimage,   a.realaudio, a.realmovie, a.realflash,  \n"
                +"		b.selnum,   b.seltext,  b.isanswer  \n"
				+"		from  tz_jin      a,           tz_jinsel   b  \n"
				+"		where a.subj      =  b.subj (+) \n"
				+"		and a.jinnum   =  b.jinnum (+) \n"
				+"	 	and a.subj='2879'  \n" 
				+"		order by  a.subj, a.jinnum, b.selnum ";
		ls = connMgr.executeQuery(sql2);

			while (ls.next()) {
						
					dbox = ls.getDataBox();
					v_examnums.add(dbox);
			}
			ls.close();


        return v_examnums;
    }


    /**
    �� ���� ����  ���ϱ�
    @param box          receive from the form object and session
    @return ArrayList
    */
    public ArrayList getLevelQuestionList(DBConnectionManager connMgr,
                        String p_subj, String p_gyear, String p_subjseq, String p_lesson, String p_examtype,
                        int p_papernum, RequestBox box) throws Exception {
   
		DataBox dbox = null;
		ArrayList list = null;
        Vector v_lessons1 = new Vector();
        Vector v_level1Obnums = new Vector();
        Vector v_level2Obnums = new Vector();
        Vector v_level3Obnums = new Vector();
        Vector v_level1Subnums = new Vector();
        Vector v_level2Subnums = new Vector();
        Vector v_level3Subnums = new Vector();
        Vector v_level1OXnums = new Vector();
        Vector v_level2OXnums = new Vector();
        Vector v_level3OXnums = new Vector();
        
        Vector v_sulnums2 = new Vector();
        StringTokenizer st = null;
        StringTokenizer st2 = null;
        StringTokenizer st3= null;	


		try {

            dbox = getExamPaperData(connMgr, p_subj, p_gyear, p_subjseq, p_lesson, p_examtype, p_papernum);

            list = new ArrayList(); 

			System.out.println("level1text"+dbox.getString("d_level1text"));
			st = new StringTokenizer(dbox.getString("d_level1text"), "/");
			
			System.out.println("���信���ý����� �򰡹���1-======="+st);
			while (st.hasMoreElements()){
        
        String lst = st.nextToken();
				st2 = new StringTokenizer(lst, "|");		
				System.out.println("���信���ý����� �򰡹���2-======="+st2);	
				
        String lst2 = st2.nextToken();
				st3 = new StringTokenizer(lst2, ",");
				System.out.println("���信���ý����� �򰡹���3-======="+st3);
				
				String ss = st3.nextToken();
				if(ss.length()==1){
				    ss = "00" + ss; 
			   } else if(ss.length()==2){
			   		ss = "0" + ss;	
			  }
			  System.out.println("���信���ý����� ������ȸ=========="+ss);
				v_lessons1.add(ss);
				v_level1Obnums.add(st3.nextToken());

                // �ְ���
                lst2 = st2.nextToken();
		        st3 = new StringTokenizer(lst2, ",");
                String s = st3.nextToken();                
			  System.out.println("���信���ý����� ������ȸ_s=========="+s);                
			    v_level1Subnums.add(st3.nextToken());

                // OX��
                lst2 = st2.nextToken();
		        st3 = new StringTokenizer(lst2, ",");
                String sOX1 = st3.nextToken();                
                			  System.out.println("���信���ý����� ������ȸ_sOX1=========="+sOX1);
			    v_level1OXnums.add(st3.nextToken());			    
			}

						System.out.println("level1text2"+dbox.getString("d_level2text"));
			st = new StringTokenizer(dbox.getString("d_level2text"), "/");
			while (st.hasMoreElements()){
                String lst = st.nextToken();
				st2 = new StringTokenizer(lst, "|");
                  String lst2 = st2.nextToken();

			      st3 = new StringTokenizer(lst2, ",");
				  String ss = st3.nextToken();
				  v_level2Obnums.add(st3.nextToken());

                  lst2 = st2.nextToken();
			      st3 = new StringTokenizer(lst2, ",");
                  String s = st3.nextToken();
				  v_level2Subnums.add(st3.nextToken());

                  lst2 = st2.nextToken();
			      st3 = new StringTokenizer(lst2, ",");
                  String sOX2 = st3.nextToken();
				  v_level2OXnums.add(st3.nextToken());
				  
				  
			}
						System.out.println("level1text3"+dbox.getString("d_level3text"));
			st = new StringTokenizer(dbox.getString("d_level3text"), "/");
			while (st.hasMoreElements()){
                String lst = st.nextToken();
				st2 = new StringTokenizer(lst, "|");
                      String lst2 = st2.nextToken();

				      st3 = new StringTokenizer(lst2, ",");
					  String ss = st3.nextToken();

					  v_level3Obnums.add(st3.nextToken());

                      lst2 = st2.nextToken();
				      st3 = new StringTokenizer(lst2, ",");
                      String s = st3.nextToken();
					  v_level3Subnums.add(st3.nextToken());
					  
                      lst2 = st2.nextToken();
				      st3 = new StringTokenizer(lst2, ",");
                      String sOX3 = st3.nextToken();
					  v_level3OXnums.add(st3.nextToken());					  
			}

            list.add(v_lessons1);
            list.add(v_level1Obnums);  // �� - ������
            list.add(v_level2Obnums);  // �� - ������
            list.add(v_level3Obnums);  // �� - ������
            list.add(v_level1Subnums); // �� - �ְ���  
            list.add(v_level2Subnums); // �� - �ְ��� 
            list.add(v_level3Subnums); // �� - �ְ��� 
            list.add(v_level1OXnums); // �� - OX�� 
            list.add(v_level2OXnums); // �� - OX�� 
            list.add(v_level3OXnums); // �� - OX��             
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
     
        return list;
    }

    /**
    �򰡹��� ����Ʈ 
    @param box          receive from the form object and session
    @return Vector   
    */
    public Vector getQuestionList(DBConnectionManager connMgr, String p_subj, String p_lesson, String p_examtype, String p_levels) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;
        Vector v_examnums = new Vector();

        try {

			
			sql = "select a.subj,   a.examnum, a.lesson,  a.examtype, ";
            sql+= "       a.examtext,    a.exptext,     ";
            sql+= "       a.levels,   a.selcount,  a.saveimage,   a.saveaudio, a.savemovie, a.saveflash,  a.realimage, ";
			sql+= " a.realaudio, a.realmovie, a.realflash, DBMS_RANDOM.RANDOM rkey ";
            sql+= "  from tz_exam   a ";
            sql+= "   where a.subj     = " + SQLString.Format(p_subj);
            sql+= "   and a.lesson    = " + SQLString.Format(p_lesson);          
			sql+= "   and a.examtype    = " + SQLString.Format(p_examtype);
            sql+= "   and a.levels    = " + SQLString.Format(p_levels);
            sql+= " order by rkey "; 

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                //v_examnums.add(dbox.getString("d_examnum"));
                v_examnums.add(dbox.getString("d_examnum")+","+dbox.getString("d_rkey"));
            }
            ls.close();
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
    ������ ����Ÿ 
    @param box          receive from the form object and session
    @return PaperData
    */
    public DataBox getPaperData(RequestBox box) throws Exception {
    
		DBConnectionManager connMgr = null;

        DataBox     dbox = null;

        String v_grcode    = box.getString("p_grcode");
        String v_subj      = box.getString("p_subj");
        java.util.Date d_now = new java.util.Date();
        String v_gyear    = box.getStringDefault("p_gyear", String.valueOf(d_now.getYear()+1900));
        String v_subjseq   = box.getString("p_subjseq");
		int v_papernum = box.getInt("p_papernum");


        int    v_sulpapernum = box.getInt("p_sulpapernum");

        try {
            connMgr = new DBConnectionManager();
            dbox =  getPaperData(connMgr, v_grcode, v_subj, v_gyear, v_subjseq, v_papernum);

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
    ������ ����Ÿ 
    @param box          receive from the form object and session
    @return PaperData
    */
    public DataBox getPaperData(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_gyear, String p_subjseq, int p_papernum) throws Exception {
        ListSet ls = null;
        String sql  = "";
        DataBox     dbox = null;

        try {
                sql = getPaperListSQL(p_grcode, p_subj, p_gyear, p_subjseq, p_papernum);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
				dbox = ls.getDataBox();
            
            }
            ls.close();
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
    ����Ʈ sql�� 
    @param box          receive from the form object and session
    @return String
    */
    public String getPaperListSQL(String p_grcode, String p_subj, String p_gyear, String p_subjseq, int p_papernum) throws Exception {
        String sql = "";
   
            sql = " select b.subj,   a.year,    a.subjseq,   a.lesson, ";
            sql+= "       a.examtype,  a.papernum,   a.lessonstart, a.lessonend,  a.examtime, a.exampoint, a.examcnt, a.totalscore,  ";
            sql+= "       a.cntlevel1, a.cntlevel2, a.cntlevel3, a.isopenanswer, ";
            sql+= "       a.isopenexp,  a.retrycnt,  a.progress, b.subjnm,  get_codenm(" + SQLString.Format(JinBean.PTYPE) + ", isnull(a.examtype, '')) examtypenm ";
            sql+= "  from tz_exampaper a, ";
            sql+= "       tz_subj       b  ";
			// ������ : 05.11.04 ������ : �̳��� _ (+) ����
//          sql+= " where a.subj(+)    =  b.subj  ";
			sql+= " where a.subj       =* b.subj  ";
            sql+= "   and a.subj       = " + SQLString.Format(p_subj);
            sql+= "   and a.year       = " + SQLString.Format(p_gyear);
            sql+= "   and a.subjseq    = " + SQLString.Format(p_subjseq);
            sql+= "   and a.papernum   = " + SQLString.Format(p_papernum);
            sql+= " order by a.subj, a.year, a.subjseq, a.lesson, a.examtype  ";	

        return sql;
    }

    /**
    �Ϸù�ȣ ���ϱ� 
    @param box          receive from the form object and session
    @return int
    */
    public int getPapernumSeq(String p_subj, String p_gyear, String p_subjseq) throws Exception {
        Hashtable maxdata = new Hashtable();
        maxdata.put("seqcolumn","papernum");
        maxdata.put("seqtable","tz_exampaper");
        maxdata.put("paramcnt","3");
        maxdata.put("param0","subj");
        maxdata.put("subj",   SQLString.Format(p_subj));
        maxdata.put("param1","year");
        maxdata.put("year",   SQLString.Format(p_gyear));
        maxdata.put("param2","subjseq");
        maxdata.put("subjseq",   SQLString.Format(p_subjseq));

        return SelectionUtil.getSeq(maxdata);
    }
}
