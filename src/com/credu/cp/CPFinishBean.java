//**********************************************************
//1. ��      ��: �н� ���� ���� BEAN 
//2. ���α׷���: CPFinishBean.java
//3. ��      ��: �н� ���� ���� BEAN
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 0.1
//6. ��      ��: S.W.Kang 2004. 12. 5
//7. ��      ��: ��â�� 2004.12.23
//
//**********************************************************

package com.credu.cp;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;
import com.credu.course.*;
import com.credu.complete.*;
import com.credu.cp.*;

/**
*����������
*<p>����:CPFinishBean.java</p>
*<p>����:���������� ��</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author ��â��
*@version 1.0
*/

public class CPFinishBean {
    public static final int FINISH_COMPLETE = 0;   // ����ó�� ����
    public static final int FINISH_CANCEL   = 1;   // ������� ����
    public static final int FINISH_PROCESS  = 3;   // ����ó��
    public static final int SCORE_COMPUTE   = 4;   // ��������

    public static final String ONOFF_GUBUN  = "0004";
    public static final String SUBJ_NOT_INCLUDE_COURSE = "000000";

    public CPFinishBean() {}


    /**
    �������� �������� ����(�̼�����,����ġ �� ��������)
    @param connMgr         DB���ᰳü
    @param p_subj          �����ڵ�
    @param p_year          �����⵵
    @param p_subjseq       ��������
    @return CPSubjseqData	������������
    */
    public CPSubjseqData getSubjseqInfo(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq) throws Exception {
        ListSet ls = null;
        String sql = "";
        CPSubjseqData data = new CPSubjseqData();
    
        // �������� ����
        sql = " select b.isgoyong,   b.isclosed,  b.edustart, b.eduend,  ";
        sql+= "        b.wstep,      b.wmtest,    b.wftest,   b.wreport, ";
        sql+= "        b.wact,       b.wetc1,     b.wetc2,	  b.whtest, ";
        sql+= "        b.gradscore,  b.gradstep,  b.gradexam, b.gradftest, ";
        sql+= "        b.gradhtest,  b.gradreport, ";
        sql+= "        b.grcode,     b.grseq,     b.gyear,    b.subjnm, ";
        sql+= "        a.isonoff,    b.biyong  ";
        sql+= "   from tz_subj     a, ";
        sql+= "        tz_subjseq  b";
        sql+= "  where a.subj    = b.subj ";
        sql+= "    and b.subj    = " + SQLString.Format(p_subj);
        sql+= "    and b.year    = " + SQLString.Format(p_year);
        sql+= "    and b.subjseq = " + SQLString.Format(p_subjseq);            
            
        try {
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data.setIsgoyong(ls.getString("isgoyong"));
                data.setIsclosed(ls.getString("isclosed"));
                data.setEdustart(ls.getString("edustart"));
                data.setEduend  (ls.getString("eduend"));
    
                data.setWstep   ((int)ls.getDouble("wstep"));
                data.setWmtest  ((int)ls.getDouble("wmtest"));
                data.setWftest  ((int)ls.getDouble("wftest"));
                data.setWhtest  ((int)ls.getDouble("whtest"));
                data.setWreport ((int)ls.getDouble("wreport"));
                data.setWact    ((int)ls.getDouble("wact"));
                data.setWetc1   ((int)ls.getDouble("wetc1"));
                data.setWetc2   ((int)ls.getDouble("wetc2"));
    
                data.setGradscore(ls.getInt("gradscore"));
                data.setGradstep(ls.getInt("gradstep"));
                data.setGradexam(ls.getInt("gradexam"));
                data.setGradftest(ls.getInt("gradftest"));
                data.setGradhtest(ls.getInt("gradhtest"));
                data.setGradreport(ls.getInt("gradreport"));
	
                data.setGrcode(ls.getString("grcode"));
                data.setGyear(ls.getString("gyear"));
                data.setGrseq(ls.getString("grseq"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setGrcodenm(GetCodenm.get_grcodenm(data.getGrcode()));
                data.setGrseqnm(GetCodenm.get_grseqnm(data.getGrcode(), data.getGyear(), data.getGrseq()));
    
                data.setIsonoff(ls.getString("isonoff"));
                data.setBiyong(ls.getInt("biyong"));
            }
        }
        catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
        }
        return data;
    }
    
    
    /**
    �������� �������� ����(�̼�����,����ġ �� ��������)
    @param connMgr         DB���ᰳü
    @param p_subj          �����ڵ�
    @param p_year          �����⵵
    @param p_subjseq       ��������
    @return CPSubjseqData	������������
    */
    public DataBox getSubjseqInfoDbox(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq) throws Exception {
        ListSet ls = null;
        String sql = "";
        CPSubjseqData data = new CPSubjseqData();
        DataBox dbox = null;
    
        // �������� ����
        sql = " select b.isgoyong,   b.isclosed,  b.edustart, b.eduend,  ";
        sql+= "        b.wstep,      b.wmtest,    b.wftest,   b.wreport, ";
        sql+= "        b.wact,       b.wetc1,     b.wetc2,	  b.whtest, ";
        sql+= "        b.gradscore,  b.gradstep,  b.gradexam, b.gradftest, ";
        sql+= "        b.gradhtest,  b.gradreport, ";
        sql+= "        b.grcode,     b.grseq,     b.gyear,    b.subjnm, ";
        sql+= "        a.isonoff,    b.biyong  ";
        sql+= "   from tz_subj     a, ";
        sql+= "        tz_subjseq  b";
        sql+= "  where a.subj    = b.subj ";
        sql+= "    and b.subj    = " + SQLString.Format(p_subj);
        sql+= "    and b.year    = " + SQLString.Format(p_year);
        sql+= "    and b.subjseq = " + SQLString.Format(p_subjseq);            
            
        try {
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_grcodenm", GetCodenm.get_grcodenm(data.getGrcode()));
                dbox.put("d_grseqnm", GetCodenm.get_grseqnm(data.getGrcode(), data.getGyear(), data.getGrseq()));
            }
        }
        catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
        }
        return dbox;
    }


    /**
    ���������� ������Ʈ
    @param connMgr         DB���ᰳü
    @param p_gubun         ���౸��
    @param p_command       �׼Ǳ���
    @return PreparedStatement	PreparedStatement
    */
    public PreparedStatement getPreparedStatement(DBConnectionManager connMgr, String p_gubun, String p_command) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";

        if (p_gubun.equals("SUBJECT_STUDENT")) {
            if (p_command.equals("update_result")) {
                sql = " update tz_student   ";
                sql+= "    set score   = ?, ";
                sql+= "        tstep   = ?, ";
                sql+= "        mtest   = ?, ";
                sql+= "        ftest   = ?, ";
                sql+= "        htest   = ?, ";
                sql+= "        report  = ?, ";
                sql+= "        act     = ?, ";
                sql+= "        etc1    = ?, ";
                sql+= "        etc2    = ?, ";
                sql+= "        avtstep = ?, ";
                sql+= "        avmtest = ?, ";
                sql+= "        avftest = ?, ";
                sql+= "        avreport= ?, ";
                sql+= "        avact   = ?, ";
                sql+= "        avetc1  = ?, ";
                sql+= "        avetc2  = ?, ";
                sql+= "        isgraduated= ?, ";
                sql+= "        notgraduetc= ?, ";
                sql+= "        luserid = ?, ";
                sql+= "        ldate   = ?, ";
                sql+= "        rank    = ?  ";
                sql+= "  where subj    = ? ";
                sql+= "    and year    = ? ";
                sql+= "    and subjseq = ? ";
                sql+= "    and userid  = ? ";
            }
            else if (p_command.equals("update_status")) {
                sql = " update tz_student   ";
                sql+= "    set score   = ?, ";
                sql+= "        tstep   = ?, ";
                sql+= "        mtest   = ?, ";
                sql+= "        ftest   = ?, ";
                sql+= "        htest   = ?, ";
                sql+= "        report  = ?, ";
                sql+= "        act     = ?, ";
                sql+= "        etc1    = ?, ";
                sql+= "        etc2    = ?, ";
                sql+= "        avtstep = ?, ";
                sql+= "        avmtest = ?, ";
                sql+= "        avftest = ?, ";
                sql+= "        avreport= ?, ";
                sql+= "        avact   = ?, ";
                sql+= "        avetc1  = ?, ";
                sql+= "        avetc2  = ?, ";
                sql+= "        luserid = ?, ";
                sql+= "        ldate   = ? ";
                sql+= "  where subj    = ? ";
                sql+= "    and year    = ? ";
                sql+= "    and subjseq = ? ";
                sql+= "    and userid  = ? ";
            }
        } else if (p_gubun.equals("MEMBER")) {
            if (p_command.equals("select")) {
                sql = " select name, comp, jikup, jikwi, jikwinm ";
                sql+= "   from tz_member ";
                sql+= "  where userid = ? ";
            }
        } else if (p_gubun.equals("SUBJECT_SANGDAM")) {
            if (p_command.equals("insert")) {
            	sql+= " insert into tz_sangdam(                  \n";
                sql+= "   NO,       USERID,   CUSERID,  CTEXT,   \n";
                sql+= "   STATUS,   SDATE,    LDATE,    MCODE,   \n";
                sql+= "   SCODE,    ETIME,    TITLE,    FTEXT,   \n";
                sql+= "   RSEQ,     SUBJ,     YEAR,     SUBJSEQ, \n";
                sql+= "   GUBUN                                  \n";
            	sql+= " )values(                                 \n";
            	sql+= "   ?,  ?,  ?,  ?,                         \n";
            	sql+= "   ?,  ?,  ?,  ?,                         \n";
            	sql+= "   ?,  ?,  ?,  ?,                         \n";
            	sql+= "   ?,  ?,  ?,  ?,                         \n";
            	sql+= "   ?                                      \n";
            	sql+= " )                                        \n";
            }
        }
        
        try {
            pstmt = connMgr.prepareStatement(sql);
        }
        catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
        }
        return pstmt;
    }

    /**
    ����������ó��
    @param	pstmt_update_student	PreparedStatement
    @param	data					���������
    @param	p_luserid				����ھ��̵�
    @return	int						������࿩��
    */
    public int update_subj_score(PreparedStatement pstmt_update_student, CPStoldData data, String p_luserid)  throws Exception {

        ResultSet rs = null;
        int isOk = 0;

        int v_count = 0;

        try {

            // 1.tz_student update
            pstmt_update_student.setDouble( 1, data.getScore());
            pstmt_update_student.setDouble( 2, data.getTstep());
            pstmt_update_student.setDouble( 3, data.getMtest());
            pstmt_update_student.setDouble( 4, data.getFtest());
            pstmt_update_student.setDouble( 5, data.getHtest());
            pstmt_update_student.setDouble( 6, data.getReport());
            pstmt_update_student.setDouble( 7, data.getAct());
            pstmt_update_student.setDouble( 8, data.getEtc1());
            pstmt_update_student.setDouble( 9, data.getEtc2());
            pstmt_update_student.setDouble(10, data.getAvtstep());
            pstmt_update_student.setDouble(11, data.getAvmtest());
            pstmt_update_student.setDouble(12, data.getAvftest());
            pstmt_update_student.setDouble(13, data.getAvreport());
            pstmt_update_student.setDouble(14, data.getAvact());
            pstmt_update_student.setDouble(15, data.getAvetc1());
            pstmt_update_student.setDouble(16, data.getAvetc2());
            pstmt_update_student.setString(17, data.getIsgraduated());            
            pstmt_update_student.setString(18, data.getNotgraduetc());
            pstmt_update_student.setString(19, p_luserid);
            pstmt_update_student.setString(20, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt_update_student.setString(21, data.getRank());
            pstmt_update_student.setString(22, data.getSubj());
            pstmt_update_student.setString(23, data.getYear());
            pstmt_update_student.setString(24, data.getSubjseq());
            pstmt_update_student.setString(25, data.getUserid());
            
            isOk = pstmt_update_student.executeUpdate();
            
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        finally {
            if(rs != null) { try { rs.close(); } catch (Exception e) {} }
        }
        return isOk;
    }
    
    
    /**
    ������Ȳ���ó��
    @param	pstmt_update_student	PreparedStatement
    @param	data					���������
    @param	p_luserid				����ھ��̵�
    @return	int						������࿩��
    */
    public int update_subjstatus_score(PreparedStatement pstmt_update_student, CPStoldData data, String p_luserid)  throws Exception {

        ResultSet rs = null;
        int isOk = 0;

        int v_count = 0;

        try {

            // 1.tz_student update
            pstmt_update_student.setDouble( 1, data.getScore());
            pstmt_update_student.setDouble( 2, data.getTstep());
            pstmt_update_student.setDouble( 3, data.getMtest());
            pstmt_update_student.setDouble( 4, data.getFtest());
            pstmt_update_student.setDouble( 5, data.getHtest());
            pstmt_update_student.setDouble( 6, data.getReport());
            pstmt_update_student.setDouble( 7, data.getAct());
            pstmt_update_student.setDouble( 8, data.getEtc1());
            pstmt_update_student.setDouble( 9, data.getEtc2());
            pstmt_update_student.setDouble(10, data.getAvtstep());
            pstmt_update_student.setDouble(11, data.getAvmtest());
            pstmt_update_student.setDouble(12, data.getAvftest());
            pstmt_update_student.setDouble(13, data.getAvreport());
            pstmt_update_student.setDouble(14, data.getAvact());
            pstmt_update_student.setDouble(15, data.getAvetc1());
            pstmt_update_student.setDouble(16, data.getAvetc2());
            pstmt_update_student.setString(17, p_luserid);
            pstmt_update_student.setString(18, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt_update_student.setString(19, data.getSubj());
            pstmt_update_student.setString(20, data.getYear());
            pstmt_update_student.setString(21, data.getSubjseq());
            pstmt_update_student.setString(22, data.getUserid());

            
            isOk = pstmt_update_student.executeUpdate();
            
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        finally {
            if(rs != null) { try { rs.close(); } catch (Exception e) {} }
        }
        return isOk;
    }
    
    /**
    ��㳻�����
    @param	pstmt_update_student	PreparedStatement
    @param	data					���������
    @param	p_luserid				����ھ��̵�
    @return	int						������࿩��
    */
    public int insert_sangdam(DBConnectionManager connMgr, PreparedStatement pstmt_insert_sangdam, CPStoldData data, String p_luserid, String p_sangdamtxt)  throws Exception {

        ResultSet rs = null;
        int isOk = 0;

        int v_maxno = 0;
        String sql = "";
        String v_luserid = p_luserid;
        String v_sangdamtxt = p_sangdamtxt;
        
        ListSet ls = null;

        try {
        	
        	sql = "select max(no)+1 mxno from tz_sangdam";
            
            ls = connMgr.executeQuery(sql);

            if(ls.next()) {
                 v_maxno = ls.getInt("mxno");
            }else{
                 v_maxno = 1;
            }
            
        	/*
        	sql+= "   NO,       USERID,   CUSERID,  CTEXT,   \n";
            sql+= "   STATUS,   SDATE,    LDATE,    MCODE,   \n";
            sql+= "   SCODE,    ETIME,    TITLE,    FTEXT,   \n";
            sql+= "   RSEQ,     SUBJ,     YEAR,     SUBJSEQ, \n";
            sql+= "   GUBUN                                  \n";
            */
            
            // 1.tz_sangdam insert
            pstmt_insert_sangdam.setInt(1, v_maxno);
            pstmt_insert_sangdam.setString(2, data.getUserid());
            pstmt_insert_sangdam.setString(3, v_luserid);
            pstmt_insert_sangdam.setString(4, v_sangdamtxt);
            pstmt_insert_sangdam.setString(5, "3");
            pstmt_insert_sangdam.setString(6, FormatDate.getDate("yyyyMMddHH"));
            pstmt_insert_sangdam.setString(7, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt_insert_sangdam.setString(8, "6");
            pstmt_insert_sangdam.setString(9, "");
            pstmt_insert_sangdam.setString(10, "0");
            pstmt_insert_sangdam.setString(11, v_sangdamtxt);
            pstmt_insert_sangdam.setString(12, v_sangdamtxt);
            pstmt_insert_sangdam.setInt(13, 0);
            pstmt_insert_sangdam.setString(14, data.getSubj());
            pstmt_insert_sangdam.setString(15, data.getYear());
            pstmt_insert_sangdam.setString(16, data.getSubjseq());
            pstmt_insert_sangdam.setString(17, "out");
            
            isOk = pstmt_insert_sangdam.executeUpdate();
            
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        finally {
            if(rs != null) { try { rs.close(); } catch (Exception e) {} }
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
        }
        return isOk;
    }

	
    /**
    ����ġ ���� score ���
    @param	connMgr		DB���ᰳü
    @param	data			���������
    @return	int			������࿩��
    */
    public int calc_subj_score(DBConnectionManager connMgr, CPStoldData data) throws Exception {
        int isOk = 1;
        try {
            data.setAvtstep((double)Math.round(data.getTstep()*data.getWstep())/100);
            data.setAvreport((double)Math.round(data.getReport()*data.getWreport())/100);
            data.setAvmtest((double)Math.round(data.getMtest()*data.getWmtest())/100);
            data.setAvftest((double)Math.round(data.getFtest()*data.getWftest())/100);
            data.setAvhtest((double)Math.round(data.getHtest()*data.getWhtest())/100);
            data.setAvact ((double)Math.round(data.getAct()*data.getWact())/100);
            data.setAvetc1((double)Math.round(data.getEtc1()*data.getWetc1())/100);
            data.setAvetc2((double)Math.round(data.getEtc2()*data.getWetc2())/100);
            
            
			System.out.println(" data.getAvtstep : " +  data.getAvtstep());
			System.out.println(" data.getAvreport : " +  data.getAvreport());
			System.out.println(" data.getAvmtest : " +  data.getAvmtest());
			System.out.println(" data.getAvftest : " +  data.getAvftest());
			System.out.println(" data.getAvact : " +  data.getAvact());
			System.out.println(" data.getAvetc1 : " +  data.getAvetc1());
			System.out.println(" data.getAvetc2 : " +  data.getAvetc2());
        	/*-------- Calc  Final Grade  ----------------------------------------------------*/
        	data.setScore(data.getAvtstep() + data.getAvmtest() + data.getAvftest() + data.getAvhtest() + data.getAvreport() + data.getAvact()   + data.getAvetc1()  + data.getAvetc2());
                      
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        finally {
        }
        return isOk;
    }

}
