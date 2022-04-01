//**********************************************************
//1. 제      목: 수강신청관련 테이블 Insert/delete/update
//2. 프로그램명: CPProposeBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: 이창훈 2004-11-5
//7. 수      정: 
//
//**********************************************************

package com.credu.cp;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;
import com.credu.propose.*;
import com.credu.cp.*;
/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CPProposeBean {
    public ArrayList SelectAcceptTargetMember(RequestBox box) throws Exception {   //직접입과처리시 화면 List
        ArrayList list = new ArrayList();

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ListSet ls = null;
        String sql1  = "";
        String sql2  = "";
        ProposeMemberData data = null;
        DataBox dbox = null;
        
        String v_searchtext = box.getString("p_searchtext");
        String v_grcode   = box.getString("s_grcode");
        String v_gyear    = box.getString("s_gyear");
        String v_grseq    = box.getString("s_grseq");
        String v_subj     = box.getString("s_subjcourse");
        String v_subjseq  = box.getString("s_subjseq");
        String v_year     = getSubjYear(v_grcode, v_gyear, v_grseq, v_subj, v_subjseq);
        String v_chkfirst = "N";
        String v_chkfinal = "N";
        String v_isdinsert = "";
        String v_userid = "";
        String v_isproposeapproval = "";
        String v_useproposeapproval = "";
        String v_cancelkind = "";
        
        v_useproposeapproval = getProposeApproval(v_subj,v_gyear,v_subjseq);

        //System.out.println("v_useproposeapproval="+v_useproposeapproval);
        //System.out.println("v_gyear="+v_gyear);
        //System.out.println("v_grseq="+v_grseq);
        //System.out.println("v_subj="+v_subj);
        //System.out.println("v_subjseq="+v_subjseq);

        if (v_year.equals("")) v_year = v_gyear;

        try {
            if (box.getString("p_action").equals("go")) {
                sql1 = "select a.comp asgn, a.jikwinm, get_compnm(a.comp,3,5) asgnnm, a.jikup, get_jikupnm(a.jikup, a.comp) jikupnm, a.userid, a.cono, a.name ";
                sql1+= "     from tz_member  a  ";
                sql1+= " where (a.userid like " + SQLString.Format("%"+v_searchtext+"%") + " or a.name like " + SQLString.Format("%"+v_searchtext+"%") + ")";
                sql1+= " order by a.name ";
                
                //System.out.println("sql1="+sql1);

                connMgr = new DBConnectionManager();
                ls = connMgr.executeQuery(sql1);

                sql2 = "select a.subj, a.year, a.subjseq, a.course, a.cyear, a.courseseq, b.chkfirst, b.chkfinal,b.cancelkind, b.isproposeapproval, b.isdinsert ";
                sql2+= "  from tz_subjseq  a,  ";
                sql2+= "       tz_propose  b ";
                sql2+= " where a.subj = b.subj ";
                sql2+= "   and a.year = b.year ";
                sql2+= "   and a.subjseq = b.subjseq ";
                sql2+= "   and b.userid =  ? ";
                
                //System.out.println("sql2="+sql2);

                pstmt = connMgr.prepareStatement(sql2);

                while (ls.next()) {
                    v_chkfirst = "N";
                    v_chkfinal = "N";
                    v_isproposeapproval = "";
                    v_isdinsert   = "";
                    
                    pstmt.setString(1, ls.getString("userid"));
                    rs = pstmt.executeQuery(); 
                    while (rs.next()) {
                        if ((v_subj.equals(rs.getString("subj")) && v_year.equals(rs.getString("year")) && v_subjseq.equals(rs.getString("subjseq")) ) ||
                            (v_subj.equals(rs.getString("course")) && v_year.equals(rs.getString("cyear")) && v_subjseq.equals(rs.getString("courseseq")))) {
                            v_chkfirst = rs.getString("chkfirst");
                            v_chkfinal = rs.getString("chkfinal");
                            v_cancelkind = StringManager.chkNull(rs.getString("cancelkind"));
                            v_isproposeapproval = rs.getString("isproposeapproval");
                            v_isdinsert   = rs.getString("isdinsert");
                            break;
                        }
                    }
                    dbox = ls.getDataBox();
                    dbox.put("d_chkfirst", v_chkfirst);
                    dbox.put("d_chkfinal", v_chkfinal);
                    dbox.put("d_cancelkind", v_cancelkind);
                    dbox.put("d_isdinsert", v_isdinsert);
                    dbox.put("d_isproposeapproval", v_isproposeapproval);
                    dbox.put("d_useproposeapproval", v_useproposeapproval);
                    list.add(dbox);
                }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    /**
    새로운 과정코드 등록
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
     public int Accept(RequestBox box) throws Exception {
        // 등록권한 체크
        DBConnectionManager connMgr = null;

        int isOk = 0;
        String v_grcode   = box.getString("s_grcode");
        String v_gyear    = box.getString("s_gyear");
        String v_grseq    = box.getString("s_grseq");
        String v_subj     = box.getString("s_subjcourse");
        String v_subjseq  = box.getString("s_subjseq");

        String v_isdinsert  = "Y";                     // 강제입력여부

        // 등록권한별 1차승인, 2차승인 결정
        String v_chkfirst   = "Y";
        String v_chkfinal   = "Y";
        String v_useproposeapproval = "N";
        String v_isproposeapproval = "L";

        String  v_year = getSubjYear(v_grcode, v_gyear, v_grseq, v_subj, v_subjseq);
        if (v_year.equals("")) v_year = v_gyear;
        
        v_useproposeapproval = getProposeApproval(v_subj,v_year,v_subjseq);
        if (v_useproposeapproval.equals("N")){
        	v_isproposeapproval = "L";               //팀장승인여부 L:불필요 B:상신중 Y:승인 N:반려
        }
        else if (v_useproposeapproval.equals("Y")){
        	v_isproposeapproval = "Y";
        }

        //System.out.println("v_isproposeapproval="+v_isproposeapproval);
        
        //p_subj로 넘어온 다수의 value를 처리하기 위해 vector로 구현
        
        String v_userid  = "";
        Vector v_checks  = box.getVector("p_checks");
        Enumeration em  = v_checks.elements();
        Hashtable insertData = new Hashtable();

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            
            while(em.hasMoreElements()){
                v_userid = (String)em.nextElement();
            
                insertData.clear();
                insertData.put("connMgr",  connMgr);
                insertData.put("subj",     v_subj);
                insertData.put("year",     v_year);
                insertData.put("subjseq",  v_subjseq);
                insertData.put("userid",   v_userid);
                insertData.put("isdinsert",v_isdinsert);
                insertData.put("isproposeapproval",v_isproposeapproval);
                insertData.put("isdinsert",v_isdinsert);
                insertData.put("chkfirst", v_chkfirst);
                insertData.put("chkfinal", v_chkfinal);
                insertData.put("luserid",  box.getSession("userid"));
                insertData.put("box",      box);
            
                isOk = insertPropose(insertData);
                isOk = insertStudent(insertData);
            }
        }
        catch(Exception ex) {
            if(connMgr != null) { connMgr.rollback(); }
            ErrorManager.getErrorStackTrace(ex, box, "Propose.Cancel");
            throw new Exception("ProposeBean.Accept" + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { connMgr.commit(); }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    public int insertEduTarget(Hashtable data) throws Exception {
        int isOk = 0;
        boolean v_CreateConnManager = false;
        boolean v_CreatePreparedStatement = false;

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        
        String v_grcode            = (String)data.get("grcode");           //교육그룹
        String v_gyear             = (String)data.get("gyear");            //년도
        String v_grseq             = (String)data.get("grseq");            //교육차수
        String v_subj              = (String)data.get("subjcourse");
        String v_mastercd          = (String)data.get("mastercd");
        String v_subjseq           = (String)data.get("subjseq");
        String v_subjnm            = (String)data.get("subjnm");
        String v_isproposeapproval = (String)data.get("isproposeapproval");  
        String v_userid            = (String)data.get("userid");
        String v_luserid           = (String)data.get("luserid");
        
        ArrayList list = null;
        CPSubjseqData v_subjseqdata = null;

        //insert TZ_EDUTARGET table
        sql =  "insert into TZ_EDUTARGET ( " ;
        sql+=  " mastercd,  userid,  isproposeapproval, ";
        sql+=  " luserid, ldate ) " ;
        sql+=  " values ( ";
        sql+=  " ?, ?, ?, ";
        sql+=  " ?, ? ) ";

        try {
            connMgr = (DBConnectionManager)data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }

            pstmt = (PreparedStatement)data.get("propose_pstmt");
            if (pstmt == null) {
                pstmt = connMgr.prepareStatement(sql);
                v_CreatePreparedStatement = true;
            }

                pstmt.setString( 1, v_mastercd);
                pstmt.setString( 2, v_userid);
                pstmt.setString( 3, "M");
                pstmt.setString( 4, v_luserid);
                pstmt.setString( 5, FormatDate.getDate("yyyyMMddHHmmss")); // ldate

                isOk = pstmt.executeUpdate();
            //}
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if (v_CreateConnManager) {
                if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            }
            if (v_CreatePreparedStatement) {
                if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            }
        }
        return isOk;
    }



    public int insertPropose(Hashtable data) throws Exception {
        int isOk = 0;
        boolean v_CreateConnManager = false;
        boolean v_CreatePreparedStatement = false;

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";

        String v_subj = (String)data.get("subj");
        String v_year = (String)data.get("year");
        String v_subjseq = (String)data.get("subjseq");

        String v_userid = (String)data.get("userid");
        String v_isdinsert = (String)data.get("isdinsert");
        String v_isproposeapproval = (String)data.get("isproposeapproval");
        String v_chkfirst = (String)data.get("chkfirst");
        String v_chkfinal = (String)data.get("chkfinal");
        String v_luserid  = (String)data.get("luserid");
        Hashtable v_member = null;

        String v_isb2c      = "N";  // tz_grcode에서 찾아온다.
        String v_ischkfirst = "N";  // tz_subjseq에서 찾아온다.

        ArrayList list = null;
        CPSubjseqData v_subjseqdata = null;

        //insert TZ_PROPOSE table
        sql =  "insert into TZ_PROPOSE ( " ;
        sql+=  " subj,    year,          subjseq,      userid,     ";
        sql+=  " comp,    jik,           appdate,      isdinsert,  ";
        sql+=  " isb2c,   ischkfirst,    chkfirst,     chkfinal,   ";
        //sql+=  " proptxt, chkfirsttxt,   chkfirstmail, chkfirstuserid, ";
        //sql+=  " chkfirstdate, billstat, ordcode,      cancelkind, ";
        sql+=  " proptxt, billstat, ordcode,      cancelkind, ";
        sql+=  " isproposeapproval, luserid, ldate ) " ;
        sql+=  " values ( ";
        sql+=  " ?, ?, ?, ?, ";
        sql+=  " ?, ?, ?, ?, ";
        sql+=  " ?, ?, ?, ?, ";
        //sql+=  " ?, ?, ?, ?, ";
        sql+=  " ?, ?, ?, ?, ";
        sql+=  " ?, ?, ? ) ";
        //System.out.println("subj="+v_subj);
        //System.out.println("year="+v_year);
        //System.out.println("subjseq="+v_subjseq);
        //System.out.println("userid="+v_userid);
        
        //update TZ_PROPOSE table
        sql1 =  "update TZ_PROPOSE set cancelkind='', isdinsert=?, chkfirst='Y',chkfinal='Y' " ;
        sql1+=  " where subj= ? and year=? and subjseq=? and userid =? ";

        try {
            connMgr = (DBConnectionManager)data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }

            pstmt = (PreparedStatement)data.get("propose_pstmt");
            if (pstmt == null) {
                pstmt = connMgr.prepareStatement(sql);
                v_CreatePreparedStatement = true;
            }

            pstmt1 = connMgr.prepareStatement(sql1);
            v_member = getMeberInfo(connMgr, v_userid);

            list = getCourseToSubj(connMgr, v_subj, v_year, v_subjseq);
            for (int i=0; i<list.size(); i++) {
                v_subjseqdata = (CPSubjseqData)list.get(i);
                if (isPropose(connMgr, v_subjseqdata.getSubj(), v_subjseqdata.getYear(), v_subjseqdata.getSubjseq(), v_userid)) {
                    pstmt1.setString( 1, v_isdinsert);
                    pstmt1.setString( 2, v_subjseqdata.getSubj());
                    pstmt1.setString( 3, v_subjseqdata.getYear());
                    pstmt1.setString( 4, v_subjseqdata.getSubjseq());
                    pstmt1.setString( 5, v_userid);
                    isOk = pstmt1.executeUpdate();
                    continue;  //조건문이 성립되면 이하 실행은 되지 않고 반복문으로 돌아갑니다.(기존 propose 테이블에 존재할시 Update)
                }

                pstmt.setString( 1, v_subjseqdata.getSubj());
                pstmt.setString( 2, v_subjseqdata.getYear());
                pstmt.setString( 3, v_subjseqdata.getSubjseq());
                pstmt.setString( 4, v_userid);
                pstmt.setString( 5, (String)v_member.get("comp"));
                pstmt.setString( 6, (String)v_member.get("jikup"));
                pstmt.setString( 7, FormatDate.getDate("yyyyMMddHHmmss"));  // appdate
                pstmt.setString( 8, v_isdinsert );
                pstmt.setString( 9, v_isb2c     );
                pstmt.setString(10, v_ischkfirst);
                pstmt.setString(11, v_chkfirst  );
                pstmt.setString(12, v_chkfinal  );
                pstmt.setString(13, "");   // v_proptxt
                //pstmt.setString(14, "");   // v_chkfirsttxt
                //pstmt.setString(15, "");   // v_chkfirstmail
                //pstmt.setString(16, "");   // v_chkfirstuserid
                //pstmt.setString(17, FormatDate.getDate("yyyyMMddHHmmss"));  // chkfirstdate
                pstmt.setString(14, "");   // v_billstat
                pstmt.setString(15, "");   // v_ordcode
                pstmt.setString(16, "");   // v_cancelkind
                pstmt.setString(17, v_isproposeapproval);   // v_isproposeapproval
                pstmt.setString(18, v_luserid);
                pstmt.setString(19, FormatDate.getDate("yyyyMMddHHmmss")); // ldate

                isOk = pstmt.executeUpdate();
            }
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if (v_CreateConnManager) {
                if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            }
            if (v_CreatePreparedStatement) {
                if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            }
        }
        return isOk;
    }



    public boolean isStudent(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
        ListSet ls = null;
        String sql  = "";
        String v_userid = "";

        try {
            sql = "select userid ";
            sql+= "  from tz_student ";
            sql+= " where subj   = " + SQLString.Format(p_subj);
            sql+= "   and year   = " + SQLString.Format(p_year);
            sql+= "   and subjseq= " + SQLString.Format(p_subjseq);
            sql+= "   and userid = " + SQLString.Format(p_userid);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                v_userid = ls.getString("userid");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }

        return v_userid.equals(p_userid);
    }

    public boolean isPropose(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
        ListSet ls = null;
        String sql  = "";
        String v_userid = "";

        try {
            sql = "select userid ";
            sql+= "  from tz_propose ";
            sql+= " where subj   = " + SQLString.Format(p_subj);
            sql+= "   and year   = " + SQLString.Format(p_year);
            sql+= "   and subjseq= " + SQLString.Format(p_subjseq);
            sql+= "   and userid = " + SQLString.Format(p_userid);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                v_userid = ls.getString("userid");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }

        return v_userid.equals(p_userid);
    }

    public int insertStudent(Hashtable data) throws Exception {
        int isOk = 0;
        boolean v_CreateConnManager = false;
        boolean v_CreatePreparedStatement = false;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";

        String v_subj = (String)data.get("subj");
        String v_year = (String)data.get("year");
        String v_subjseq = (String)data.get("subjseq");
        String v_userid = (String)data.get("userid");
        String v_isdinsert = (String)data.get("isdinsert");
        String v_chkfirst = (String)data.get("chkfirst");
        String v_chkfinal = (String)data.get("chkfinal");
        String v_luserid  = (String)data.get("luserid");
        Hashtable v_member = null;

        String v_isb2c    = "N";  // tz_grcode에서 찾아온다.
        // 학습기간 설정
        String v_edustart = "";
        String v_eduend   = "";

        ArrayList list = null;
        CPSubjseqData v_subjseqdata = null;

        boolean isMailed = false;

        RequestBox box = null;
        box = (RequestBox)data.get("box");

        //insert TZ_STUDENT table
        sql =  "insert into TZ_STUDENT ( " ;
        sql+=  " subj,        year,     subjseq,     userid,  ";
        sql+=  " class,       comp,     isdinsert,   score,   ";
        sql+=  " tstep,       mtest,    ftest,       report,  ";
        sql+=  " act,         etc1,     etc2,        avtstep, ";
        sql+=  " avmtest,     avftest,  avreport,    avact,   ";
        sql+=  " avetc1,      avetc2,   isgraduated, isrestudy, ";
        sql+=  " isb2c,       edustart, eduend,      branch,   ";
        sql+=  " confirmdate, eduno,    luserid,     ldate )  ";
        sql+=  " values ( ";
        sql+=  " ?, ?, ?, ?, ";
        sql+=  " ?, ?, ?, ?, ";
        sql+=  " ?, ?, ?, ?, ";
        sql+=  " ?, ?, ?, ?, ";
        sql+=  " ?, ?, ?, ?, ";
        sql+=  " ?, ?, ?, ?, ";
        sql+=  " ?, ?, ?, ?, ";
        sql+=  " ?, ?, ?, ? ) ";

        try {
            connMgr = (DBConnectionManager)data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }

            pstmt = (PreparedStatement)data.get("student_pstmt");
            if (pstmt == null) {
                pstmt = connMgr.prepareStatement(sql);
                v_CreatePreparedStatement = true;
            }

            v_member = getMeberInfo(connMgr, v_userid);

            list = getCourseToSubj(connMgr, v_subj, v_year, v_subjseq);
            for (int i=0; i<list.size(); i++) {
                v_subjseqdata = (CPSubjseqData)list.get(i);
                if (isStudent(connMgr, v_subjseqdata.getSubj(), v_subjseqdata.getYear(), v_subjseqdata.getSubjseq(), v_userid)) {
                    isOk = 1;
                    continue;
                }

                pstmt.setString( 1, v_subjseqdata.getSubj());
                pstmt.setString( 2, v_subjseqdata.getYear());
                pstmt.setString( 3, v_subjseqdata.getSubjseq());
                pstmt.setString( 4, v_userid);
                pstmt.setString( 5, "");   // v_class
                pstmt.setString( 6, (String)v_member.get("comp"));
                pstmt.setString( 7, "Y");   // v_isdinsert
                pstmt.setDouble( 8, 0);     // v_score
                pstmt.setDouble( 9, 0);     // v_tstep
                pstmt.setDouble(10, 0);     // v_mtest
                pstmt.setDouble(11, 0);     // v_ftest
                pstmt.setDouble(12, 0);     // v_report
                pstmt.setDouble(13, 0);     // v_act
                pstmt.setDouble(14, 0);     // v_etc1
                pstmt.setDouble(15, 0);     // v_etc2
                pstmt.setDouble(16, 0);     // v_avtstep
                pstmt.setDouble(17, 0);     // v_avmtest
                pstmt.setDouble(18, 0);     // v_avftest
                pstmt.setDouble(19, 0);     // v_avreport
                pstmt.setDouble(20, 0);     // v_avact
                pstmt.setDouble(21, 0);     // v_avetc1
                pstmt.setDouble(22, 0);     // v_avetc2
                pstmt.setString(23, "N");   // v_isgraduated
                pstmt.setString(24, "N");   // v_isrestudy)
                pstmt.setString(25, v_isb2c);
                pstmt.setString(26, v_edustart);
                pstmt.setString(27, v_eduend);
                pstmt.setString(28, "99");  //v_branch
                pstmt.setString(29, "");    // v_confirmdate
                pstmt.setInt   (30, 0);     // v_eduno
                pstmt.setString(31, v_luserid);
                pstmt.setString(32, FormatDate.getDate("yyyyMMddHHmmss")); // ldate

                isOk = pstmt.executeUpdate();

                // 신청승인 및 입과 메일 보낼 자리
                //isMailed = sendAcceptMail(connMgr, box, v_subj, v_year, v_subjseq, v_userid);
            }
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (v_CreateConnManager) {
                if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            }
            if (v_CreatePreparedStatement) {
                if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            }
        }
        return isOk;
    }

    public boolean  sendAcceptMail(DBConnectionManager connMgr, RequestBox box, String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
        boolean isMailed = false;
        String v_mailContent = "";

///////////////////////////////////////////////////////////////////////////////
        String  v_toCono   = "";
        String  v_toEmail  = "";
        String  v_ismailing= "";
        String  v_toname   = ""; //님이 tz_member

        String  v_isonoff  = ""; // tz_subj + tz_subjseq
        String  v_subjnm   = "";
        String  v_grseqnm  = ""; // ◈ 교육차수명
        String  v_edustrart= "";
        String  v_eduend   = ""; // ◈ 교육기간
        int     v_edudays  = 0;  // ◈ 교육일수
        String  v_place     = ""; // ◈ 교육장소
        int  v_gradstep     = 0;     // ◈ 수료기준 진도율
        int  v_wgradstep    = 0;    // ◈ 수료기준 진도율 가중치
        int  v_wreport      = 0;     // ◈ 리포트
        int  v_wmtest       = 0;     // ◈ 중간평가
        int  v_wftest       = 0;     // ◈ 최종평가
        int  v_wact         = 0;     // ◈ Activity
        int  v_gradscore    = 0;     // 총 수료점수 점
        int  v_biyong       = 0;     // 수강료

        int     v_reportcnt= 0;  //    리포트 회
        int     v_testcnt  = 0;  //    평가 회
        int     v_actcnt   = 0;  //    평가 회


        ListSet ls  = null;
        String  sql = "";

        String  v_sql01 = "";
        v_sql01+= "select cono, email, name, ismailing ";
        v_sql01+= "  from tz_member ";
        v_sql01+= " where userid = " + SQLString.Format(p_userid);

        String  v_sql02 = "";
        v_sql02 = "select c.codenm, b.subjnm, b.edustart, b.eduend, a.edudays, a.place, ";
        v_sql02+= "       b.gradstep,b.wstep, b.wreport, b.wmtest, b.wftest, b.wact, b.gradscore, b.biyong, ";
        v_sql02+= "       (select grseqnm from tz_grseq where grcode = b.grcode and gyear = b.gyear and grseq=b.grseq) grseqnm ";
        v_sql02+= "  from tz_subj    a,  ";
        v_sql02+= "       tz_subjseq b,  ";
        v_sql02+= "       tz_code    c   ";
        v_sql02+= " where a.subj    = b.subj  ";
        v_sql02+= "   and a.isonoff = c.code  ";
        v_sql02+= "   and c.gubun   = '0004'  ";
        v_sql02+= "   and b.subj    = " + SQLString.Format(p_subj);
        v_sql02+= "   and b.year    = " + SQLString.Format(p_year);
        v_sql02+= "   and b.subjseq = " + SQLString.Format(p_subjseq);

        String  v_sql03 = "";
        v_sql03 = "select count(*) testcnt  ";
        v_sql03+= "  from tz_exampaper  ";
        v_sql03+= " where subj    = " + SQLString.Format(p_subj);
        v_sql03+= "   and year    = " + SQLString.Format(p_year);
        v_sql03+= "   and subjseq = " + SQLString.Format(p_subjseq);

        String  v_sql04 = "";
        v_sql04 = " select count(*) reportcnt";
        v_sql04+= "   from tz_projord ";
        v_sql04+= "  where subj    = " + SQLString.Format(p_subj);
        v_sql04+= "    and year    = " + SQLString.Format(p_year);
        v_sql04+= "    and subjseq = " + SQLString.Format(p_subjseq);

        String  v_sql05 = "";
        v_sql05 = "select count(*) actcnt ";
        v_sql05+= "  from tz_activity  ";
        v_sql05+= " where subj = " + SQLString.Format(p_subj);

        try {
////////////////////  폼메일 발송 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            String v_sendhtml = "mail1.html";
            FormMail fmail = new FormMail(v_sendhtml);     //      폼메일발송인 경우
            MailSet mset = new MailSet(box);        //      메일 세팅 및 발송
            String v_mailTitle = "안녕하세요? 사이버연수원 운영자입니다.(신청승인안내)";
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            mset.setSender(fmail);     //  메일보내는 사람 세팅

            sql = v_sql01;
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_toCono  = ls.getString("cono");
                v_toEmail = ls.getString("email");
                v_toname  = ls.getString("name");
                v_ismailing = ls.getString("ismailing");
            }

            sql = v_sql02;
            ls.close();
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_isonoff   = ls.getString("codenm");
                v_subjnm    = ls.getString("subjnm");
                v_grseqnm   = ls.getString("grseqnm");
                v_edustrart = ls.getString("edustart");
                v_eduend    = ls.getString("eduend");
                v_edudays   = ls.getInt("edudays");
                v_place     = ls.getString("place");
                
                if(v_place.equals(""))      {  
                	v_place = "사이버교육";    
                }
                
                v_gradstep  = ls.getInt("gradstep");
                v_wgradstep = ls.getInt("wstep");
                v_wreport   = ls.getInt("wreport");
                v_wmtest    = ls.getInt("wmtest");
                v_wftest    = ls.getInt("wftest");
                v_wact      = ls.getInt("wact");
                v_gradscore = ls.getInt("gradscore");
                v_biyong    = ls.getInt("biyong");
            }

            sql = v_sql03;
            ls.close();
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_testcnt  = ls.getInt("testcnt");
            }

            sql = v_sql04;
            ls.close();
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_reportcnt  = ls.getInt("reportcnt");
            }

            sql = v_sql05;
            ls.close();
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_actcnt  = ls.getInt("actcnt");
            }

            fmail.setVariable("toname",   v_toname);
            fmail.setVariable("isonoff",  v_isonoff);
            fmail.setVariable("subjnm",   v_subjnm);
            fmail.setVariable("grseqnm",  v_grseqnm);
            fmail.setVariable("edustart", FormatDate.getFormatDate(v_edustrart, "yyyy-MM-dd"));
            fmail.setVariable("eduend",   FormatDate.getFormatDate(v_eduend, "yyyy-MM-dd"));
            fmail.setVariable("edudays",  String.valueOf(v_edudays));
            fmail.setVariable("place",    v_place);

            fmail.setVariable("gradstep",   String.valueOf(v_gradstep));
            fmail.setVariable("wgradstep",  String.valueOf(v_wgradstep));
            fmail.setVariable("wreport",    String.valueOf(v_wreport));
            fmail.setVariable("wmtest",     String.valueOf(v_wmtest));
            fmail.setVariable("wftest",     String.valueOf(v_wftest));
            fmail.setVariable("wact",       String.valueOf(v_wact));
            fmail.setVariable("gradscore",  String.valueOf(v_gradscore));
            fmail.setVariable("biyong",     String.valueOf(v_biyong));

            fmail.setVariable("reportcnt", String.valueOf(v_reportcnt));
            fmail.setVariable("testcnt",   String.valueOf(v_testcnt));
            fmail.setVariable("actcnt",    String.valueOf(v_actcnt));

            v_mailContent = fmail.getNewMailContent();

            isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent,v_ismailing, v_sendhtml);
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return isMailed;
    }


    public ArrayList getCourseToSubj(String p_subj, String p_year, String p_subjseq) throws Exception {
        ArrayList list = null;
        DBConnectionManager connMgr = null;

        try {
            connMgr = new DBConnectionManager();
            list = getCourseToSubj(connMgr, p_subj, p_year, p_subjseq);
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

    public ArrayList getCourseToSubj(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq) throws Exception {
        ArrayList list = new ArrayList();
        CPSubjseqData data = null;
        ListSet ls = null;
        String sql  = "";

        try {
            String temp = GetCodenm.get_upperclass(p_subj,p_year,p_subjseq);
            if (temp.equals("COUR")) {
//            if (p_subj.startsWith("C")) {
                sql = "select grcode, gyear, grseq, subj, year, subjseq  ";
                sql+= "  from tz_subjseq ";
                sql+= " where course   = " + SQLString.Format(p_subj);
                sql+= "   and cyear    = " + SQLString.Format(p_year);
                sql+= "   and courseseq= " + SQLString.Format(p_subjseq);

                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    data = new CPSubjseqData();
                    data.setGrcode (ls.getString("grcode"));
                    data.setGyear  (ls.getString("gyear"));
                    data.setGrseq  (ls.getString("grseq"));
                    data.setSubj   (ls.getString("subj"));
                    data.setYear   (ls.getString("year"));
                    data.setSubjseq(ls.getString("subjseq"));
                    list.add(data);
                }
            } else {
                data = new CPSubjseqData();
                data.setSubj(p_subj);
                data.setYear(p_year);
                data.setSubjseq(p_subjseq);
                list.add(data);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return list;
    }

    public String getSubjYear(String p_grcode, String p_gyear, String p_grseq, String p_subj, String p_subjseq) throws Exception {
        DBConnectionManager connMgr = null;
        String v_year = "";
        try {
            connMgr = new DBConnectionManager();
            v_year = getSubjYear(connMgr, p_grcode, p_gyear, p_grseq, p_subj, p_subjseq);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return v_year;
    }

    public String getSubjYear(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, String p_subj, String p_subjseq) throws Exception {
        ListSet ls = null;
        String sql  = "";
        String v_year = "";
        try {
            sql = "select year ";
            sql+= "  from tz_subjseq ";
            sql+= " where subj   = " + SQLString.Format(p_subj);
            sql+= "   and subjseq= " + SQLString.Format(p_subjseq);
            sql+= "   and grcode = " + SQLString.Format(p_grcode);
            sql+= "   and gyear  = " + SQLString.Format(p_gyear);
            //sql+= "   and grseq  = " + SQLString.Format(p_grseq);
            sql+= " union all ";
            sql+= "select cyear ";
            sql+= "  from tz_courseseq ";
            sql+= " where course   = " + SQLString.Format(p_subj);
            sql+= "   and courseseq= " + SQLString.Format(p_subjseq);
            sql+= "   and grcode   = " + SQLString.Format(p_grcode);
            sql+= "   and gyear    = " + SQLString.Format(p_gyear);
            //sql+= "   and grseq    = " + SQLString.Format(p_grseq);

//System.out.println(" getSubjYear sql ==>"+ sql );


            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                v_year = ls.getString("year");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return v_year;
    }
    
    public String getProposeApproval(String p_subj, String p_year, String p_subjseq) throws Exception {
        DBConnectionManager connMgr = null;
        String v_proposeapp = "";
        try {
            connMgr = new DBConnectionManager();
            v_proposeapp = getProposeApproval(connMgr, p_subj, p_year, p_subjseq);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return v_proposeapp;
    }
    
    public String getProposeApproval(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String v_proposeapp = "";
        
        String sql  = "";
        
        try {
        	sql  = "select useproposeapproval from tz_subjseq ";
        	sql += " where subj = " + SQLString.Format(p_subj);
        	sql += " and year = " + SQLString.Format(p_year);
        	sql += " and subjseq = " + SQLString.Format(p_subjseq);
            //System.out.println(sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                v_proposeapp = ls.getString("useproposeapproval");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return v_proposeapp;
    }

    public Hashtable getMeberInfo(String p_userid) throws Exception {
        Hashtable member = null;
        DBConnectionManager connMgr = null;

        try {
            connMgr = new DBConnectionManager();
            member = getMeberInfo(connMgr, p_userid);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return member;
    }

    public Hashtable getMeberInfo(DBConnectionManager connMgr, String p_userid) throws Exception {
        Hashtable member = new Hashtable();
        ListSet ls = null;
        String sql  = "";

        try {
            sql = "select comp, jikup ";
            sql+= "  from tz_member ";
            sql+= " where userid   = " + SQLString.Format(p_userid);
//System.out.println("getMemberInfo sql ==>" +sql);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                member.put("comp",  ls.getString("comp"));
                member.put("jikup", ls.getString("jikup"));
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return member;
    }

    public ArrayList SelectCancelTargetMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;
        
        String sql  = "";
        ProposeMemberData data = null;
        String v_searchtext = box.getString("p_searchtext");

        String v_course    = "";
        String v_cyear     = "";
        String v_courseseq = "";

        String v_course_bef    = "000000";
        String v_cyear_bef     = "0000";
        String v_courseseq_bef = "0000";

        try {
            list = new ArrayList();
            if (!v_searchtext.equals("")) {

                sql = "select a.grcode, a.gyear, a.grseq, a.subj, a.year, a.subjseq, a.course, a.cyear, a.courseseq, a.useproposeapproval,  ";
                sql+= "       a.subjnm, a.propstart, a.propend, a.edustart, a.eduend,  get_jikwinm(d.jikwi,d.comp) jikwi,";
                sql+= "       d.comp asgn, get_compnm(d.comp,3,5) asgnnm, d.jikup, get_jikupnm(d.jikup, d.comp) jikupnm, d.userid, d.cono, d.name, ";
                sql+= "       c.appdate, c.chkfirst, c.chkfinal, c.isproposeapproval, b.coursenm, ";
                sql+= "      (select grcodenm from tz_grcode where grcode = a.grcode) grcodenm, ";
                sql+= "      (select grseqnm from tz_grseq where grcode = a.grcode and gyear = a.gyear and grseq = a.grseq) grseqnm ";
                sql+= "  from tz_subjseq   a, ";
                sql+= "       tz_courseseq b, ";
                sql+= "       tz_propose   c, ";
                sql+= "       tz_member    d  ";
                sql+= " where a.subj    = c.subj ";
                sql+= "   and a.year    = c.year ";
                sql+= "   and a.subjseq = c.subjseq ";
                sql+= "   and a.course  = b.course  " ;
                sql+= "   and a.cyear   = b.cyear   ";
                sql+= "   and a.courseseq = b.courseseq ";
                sql+= "   and c.userid  = d.userid  ";
                sql+= "   and c.cancelkind  is null";
                sql+= "   and (d.userid like " + SQLString.Format("%"+v_searchtext+"%") + " or d.name like " + SQLString.Format("%"+v_searchtext+"%") + ")";
                sql+= " order by d.name, d.userid, a.course, a.cyear, a.courseseq, a.subj, a.year, a.subjseq ";
System.out.print(sql);
                connMgr = new DBConnectionManager();
                ls = connMgr.executeQuery(sql);
                
                while(ls.next()){
                    dbox = ls.getDataBox();
                    list.add(dbox);
                }

                //while (ls.next()) {
                //    v_course = ls.getString("course");
                //    v_cyear = ls.getString("year");
                //    v_courseseq = ls.getString("courseseq");
                //    if (!v_course.equals("000000") && !v_cyear.equals("0000") && !v_courseseq.equals("0000")) {  //코스일때
                //        if (v_course.equals(v_course_bef) && v_cyear.equals(v_cyear_bef) && v_courseseq.equals(v_courseseq_bef)) {
                //            continue;
                //        }
                //    }
                //    data=new ProposeMemberData();
                //
                //    data.setGrcodenm(ls.getString("grcodenm"));
                //    data.setGrcode(ls.getString("grcode"));
                //    data.setGyear(ls.getString("gyear"));
                //    data.setGrseq(ls.getString("grseq"));
                //    data.setGrseqnm(ls.getString("grseqnm"));
                //    if (v_course.equals("000000")) {
                //        data.setSubj(ls.getString("subj"));
                //        data.setYear(ls.getString("year"));
                //        data.setSubjseq(ls.getString("subjseq"));
                //        data.setSubjtype("S");
                //        data.setSubjnm(ls.getString("subjnm"));
                //    } else {
                //        data.setSubj(v_course);
                //        data.setYear(v_cyear);
                //        data.setSubjseq(v_courseseq);
                //        data.setSubjtype("C");
                //        data.setSubjnm(ls.getString("coursenm"));
                //    }
                //    data.setPropstart(ls.getString("propstart"));
                //    data.setPropend(ls.getString("propend"));
                //    data.setEdustart(ls.getString("edustart"));
                //    data.setEduend(ls.getString("eduend"));
                //    data.setAsgn(ls.getString("asgn"));
                //    data.setAsgnnm(ls.getString("asgnnm"));
                //    data.setJikup(ls.getString("jikup"));
                //    data.setJikupnm(ls.getString("jikupnm"));
                //    data.setUserid(ls.getString("userid"));
                //    data.setCono(ls.getString("cono"));
                //    data.setName(ls.getString("name"));
                //    data.setAppdate(ls.getString("appdate"));
                //    data.setChkfirst(ls.getString("chkfirst"));
                //    data.setChkfinal(ls.getString("chkfinal"));
                //
                //    v_course_bef = v_course;
                //    v_cyear_bef  = v_cyear;
                //    v_courseseq_bef = v_courseseq;
                //
                //    list.add(data);
                //}
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
    직접입과 취소처리
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
    public int Cancel(RequestBox box) throws Exception {
        // 취소권한 체크
        DBConnectionManager connMgr = null;
        int isOk = 0;

        String v_subj     = "";
        String v_year     = "";
        String v_subjseq  = "";
        String v_userid   = "";
        int   v_seq       = 0;
        String v_luserid  = box.getSession("userid");

        //p_checks 넘어온 다수의 value를 처리하기 위해 vector로 구현
        Vector v_checks = box.getVector("p_checks");
        Enumeration em  = v_checks.elements();
        StringTokenizer v_token = null;
        String v_tempStr = "";
        int i=0;
        Hashtable insertData = new Hashtable();
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            while(em.hasMoreElements()){
                insertData.clear();
                insertData.put("connMgr",  connMgr);

                i=0;
                v_token = new StringTokenizer((String)em.nextElement(), ";");
                while (v_token.hasMoreTokens()) {
                    v_tempStr = v_token.nextToken();
                    switch (i) {
                        case 0:
                            insertData.put("subj", v_tempStr);
                            break;
                        case 1:
                            insertData.put("year", v_tempStr);
                            break;
                        case 2:
                            insertData.put("subjseq", v_tempStr);
                            break;
                        case 3:
                            insertData.put("userid", v_tempStr);
                            break;
                    }
                    i++;
                }
                insertData.put("cancelkind", box.getString("p_cancelkind"));
                insertData.put("reason", box.getString("p_reason"));
                insertData.put("luserid", box.getSession("userid"));

                isOk = insertCancel (insertData);
                isOk = deletePropose(insertData);
                isOk = deleteStudent(insertData);
            }
        }
        catch(Exception ex) {
            if(connMgr != null) { connMgr.rollback(); }
            ErrorManager.getErrorStackTrace(ex, box, "Propose.Cancel");
            throw new Exception("Propose.Cancel" + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { connMgr.commit(); }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    public int insertCancel(Hashtable data) throws Exception {
        int isOk = 0;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";

        String v_subj       = (String)data.get("subj");
        String v_year       = (String)data.get("year");
        String v_subjseq    = (String)data.get("subjseq");
        String v_userid     = (String)data.get("userid");
        String v_cancelkind = (String)data.get("cancelkind");
        String v_reason     = (String)data.get("reason");
        String v_luserid    = (String)data.get("luserid");
        int v_seq = 0;

        ArrayList list = null;
        CPSubjseqData v_subjseqdata = null;

        //insert TZ_PROPOSE table
        //insert TZ_CANCEL table
        sql =  "insert into TZ_CANCEL ( " ;
        sql+=  " subj,    year,          subjseq,      userid,  ";
        sql+=  " seq,     cancelkind,    canceldate,   reason,  ";
        sql+=  " luserid, ldate )  ";
        sql+=  " values ( ";
        sql+=  " ?, ?, ?, ?, ";
        sql+=  " ?, ?, ?, ?, ";
        sql+=  " ?, ? ) ";

        try {
            connMgr = (DBConnectionManager)data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }
            pstmt = connMgr.prepareStatement(sql);

            list = getCourseToSubj(v_subj, v_year, v_subjseq);
            for (int i=0; i<list.size(); i++) {
                v_subjseqdata = (CPSubjseqData)list.get(i);

                Hashtable maxdata = new Hashtable();
                maxdata.clear();
                maxdata.put("seqcolumn","seq");
                maxdata.put("seqtable","tz_cancel");
                maxdata.put("paramcnt","4");
                maxdata.put("param0","subj");
                maxdata.put("subj",   SQLString.Format(v_subjseqdata.getSubj()));
                maxdata.put("param1","year");
                maxdata.put("year",   SQLString.Format(v_subjseqdata.getYear()));
                maxdata.put("param2","subjseq");
                maxdata.put("subjseq",SQLString.Format(v_subjseqdata.getSubjseq()));
                maxdata.put("param3","userid");
                maxdata.put("userid", SQLString.Format(v_userid));
                v_seq = SelectionUtil.getSeq(maxdata);

                pstmt.setString( 1, v_subjseqdata.getSubj());
                pstmt.setString( 2, v_subjseqdata.getYear());
                pstmt.setString( 3, v_subjseqdata.getSubjseq());
                pstmt.setString( 4, v_userid);
                pstmt.setInt   ( 5, v_seq);
//              pstmt.setString( 6, v_cancelkind);
                pstmt.setString( 6, "F");
                pstmt.setString( 7, FormatDate.getDate("yyyyMMddHHmmss")); //canceldata
                pstmt.setString( 8, v_reason);
                pstmt.setString( 9, v_luserid);
                pstmt.setString(10, FormatDate.getDate("yyyyMMddHHmmss")); // ldate
                isOk = pstmt.executeUpdate();
            }
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (v_CreateConnManager) {
                if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
        }
        return isOk;
    }

/**
    수강신청정보 삭제
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
    public int deletePropose(Hashtable data) throws Exception {
        int isOk = 0;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";

        String v_subj = (String)data.get("subj");
        String v_year = (String)data.get("year");
        String v_subjseq = (String)data.get("subjseq");
        String v_userid = (String)data.get("userid");

        ArrayList list = null;
        CPSubjseqData v_subjseqdata = null;
        try {
            connMgr = (DBConnectionManager)data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }

            sql = "delete from TZ_PROPOSE where subj = ? and year = ? and subjseq = ? and userid = ? ";
            pstmt = connMgr.prepareStatement(sql);

            list = getCourseToSubj(v_subj, v_year, v_subjseq);
            for (int i=0; i<list.size(); i++) {
                v_subjseqdata = (CPSubjseqData)list.get(i);

                pstmt.setString(1, v_subjseqdata.getSubj());
                pstmt.setString(2, v_subjseqdata.getYear());
                pstmt.setString(3, v_subjseqdata.getSubjseq());
                pstmt.setString(4, v_userid);

                isOk = pstmt.executeUpdate();
            }
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (v_CreateConnManager) {
                if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
        }
        return isOk;
    }


    /**
    대상자정보 삭제
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
    public int deleteEduTarget(Hashtable data) throws Exception {
        int isOk = 0;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        
        String v_grcode = (String)data.get("grcode");
        String v_subjcourse = (String)data.get("subjcourse");
        String v_mastercd = (String)data.get("mastercd");
        String v_gyear = (String)data.get("gyear");
        //String v_subjseq = (String)data.get("subjseq");
        String v_userid = (String)data.get("userid");
        

        ArrayList list = null;
        CPSubjseqData v_subjseqdata = null;
        try {
            connMgr = (DBConnectionManager)data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }

            sql = "delete from TZ_EDUTARGET where mastercd = ?and userid = ? ";
            //System.out.println("v_grcode="+v_grcode);
            //System.out.println("v_subjcourse="+v_subjcourse);
            //System.out.println("v_gyear="+v_gyear);
            //System.out.println("v_userid="+v_userid);
            
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_mastercd);
            pstmt.setString(2, v_userid);

            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (v_CreateConnManager) {
                if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
        }
        return isOk;
    }

    /**
    학습정보 삭제
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
    public int deleteStudent(Hashtable data) throws Exception {
        /*
        // 수강신청정보 삭제
        insert into tz_propose_canceled  select * from tz_propose
        where subj=p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        delete tz_propose
         where subj=p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        // 교육생정보 삭제
        insert into tz_student_canceled  select * from tz_student
        where subj=p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        delete from tz_student
         where subj = p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        // 과거교육생정보 삭제
        insert into tz_stold_canceled  select * from tz_stold
        where subj=p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        delete from tz_stold
         where subj = p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        // 진도정보 삭제
        insert into tz_progress_canceled  select * from tz_progress
        where subj=p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        insert into tz_progressobj_canceled  select * from tz_progressobj
        where subj=p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        delete from tz_progress
         where subj = p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        delete from tz_progressobj
         where subj = p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        // 평가결과정보 삭제
        insert into tz_examresult_canceled  select * from tz_examresult
        where subj=p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        delete from tz_examresult
         where subj = p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        // Activity제출정보 삭제
        for x in c2 loop
            insert into tz_activity_ans_canceled
            values(x.SUBJ,x.YEAR,x.SUBJSEQ,x.DATES,x.SEQ,x.RESNO,x.INDATE,x.CONTENT,x.POINT,x.POINT2,x.BRANCH,x.LRESNO,x.LDATE);
        end loop
            commit;
        delete from tz_activity_ans
         where subj = p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        // 프로젝트정보 삭제
        for x in c1 loop
            insert into tz_projrep_canceled(subj,year,subjseq,ordseq,projgrp,seq,title,
                contents,f_open,f_state,grade,upfile,tutitle,
                tucontents,tuupfile,indate,lresno,ldate,dates,grade_tmp,grade_mas,tudate)
            values(x.subj,x.year,x.subjseq,x.ordseq,x.projgrp,x.seq,x.title,
                x.contents,x.f_open,x.f_state,x.grade,x.upfile,x.tutitle,
                x.tucontents,x.tuupfile,x.indate,x.lresno,x.ldate,x.dates,x.grade_tmp,x.grade_mas,x.tudate);
        end loop
            commit;
        delete from tz_projrep
         where subj = p_subj and year=p_year and subjseq=p_subjseq and projgrp=p_resno;
        delete from tz_projgrp
         where subj = p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        // COP type의 리포트에 대한 조원들의 평가결과 삭제
        insert into tz_coprep_canceled  select * from tz_coprep where subj = p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        delete from tz_coprep where subj = p_subj and year=p_year and subjseq=p_subjseq and resno=p_resno;
        */

        int isOk = 1;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";

        String v_subj = (String)data.get("subj");
        String v_year = (String)data.get("year");
        String v_subjseq = (String)data.get("subjseq");
        String v_userid = (String)data.get("userid");

        ArrayList list = null;
        CPSubjseqData v_subjseqdata = null;
        try {
            connMgr = (DBConnectionManager)data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }

            sql = "delete from TZ_STUDENT where subj = ? and year = ? and subjseq = ? and userid = ? ";
            pstmt = connMgr.prepareStatement(sql);

            list = getCourseToSubj(v_subj, v_year, v_subjseq);
            for (int i=0; i<list.size(); i++) {
                v_subjseqdata = (CPSubjseqData)list.get(i);

                pstmt.setString(1, v_subjseqdata.getSubj());
                pstmt.setString(2, v_subjseqdata.getYear());
                pstmt.setString(3, v_subjseqdata.getSubjseq());
                pstmt.setString(4, v_userid);

                pstmt.executeUpdate();
            }
        }
        catch(Exception ex) {
            isOk = 0;
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (v_CreateConnManager) {
                if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
        }
        return isOk;
    }

    public int updatePropose(Hashtable data) throws Exception {
        int isOk = 0;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        String sql = "";

        String v_subj = (String)data.get("subj");
        String v_year = (String)data.get("year");
        String v_subjseq = (String)data.get("subjseq");
        
        //if EduTargetAssignBean.AcceptAssignMember  call
        //String v_sourcesubj = (String)data.get("sourcesubj");
        //String v_sourceyear = (String)data.get("sourceyear");
        //String v_sourcesubjseq = (String)data.get("sourcesubjseq");

        String v_userid = (String)data.get("userid");
        String v_isdinsert = (String)data.get("isdinsert");
        String v_chkfirst = (String)data.get("chkfirst");
        String v_chkfinal = (String)data.get("chkfinal");
        String v_isproposeapproval = (String)data.get("isproposeapproval");
        String v_luserid  = (String)data.get("luserid");
        Hashtable v_member = null;
        v_member = getMeberInfo(v_userid);

        String v_isb2c      = "N";  // tz_grcode에서 찾아온다.
        String v_ischkfirst = "N";  // tz_subjseq에서 찾아온다.

        String v_appdate = (String)data.get("appdate");
        String v_proptxt = (String)data.get("proptxt");
        String v_chkfirsttxt = (String)data.get("chkfirsttxt");
        String v_chkfirstmail = (String)data.get("chkfirstmail");
        String v_chkfirstuserid = (String)data.get("chkfirstuserid");
        String v_chkfirstdate = (String)data.get("chkfirstdate");
        String v_billstat = (String)data.get("billstat");
        String v_ordcode = (String)data.get("ordcode");
        String v_cancelkind = (String)data.get("cancelkind");

        ArrayList list = null;
        CPSubjseqData v_subjseqdata = null;

        try {
            connMgr = (DBConnectionManager)data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }

            list = getCourseToSubj(v_subj, v_year, v_subjseq);
            for (int i=0; i<list.size(); i++) {
                v_subjseqdata = (CPSubjseqData)list.get(i);

                //insert TZ_PROPOSE table
                sql =  "update TZ_PROPOSE  " ;
                sql+=  "    set            ";
                sql+=  "       subj              = " + (v_subj        == null ? "subj"              : SQLString.Format(v_subj)) + ", ";
                sql+=  "       subjseq           = " + (v_subjseq     == null ? "subjseq"           : SQLString.Format(v_subjseq))    + ", ";
                sql+=  "       year              = " + (v_year        == null ? "year"           : SQLString.Format(v_year))    + ", ";
                sql+=  "       comp              = " + SQLString.Format((String)v_member.get("comp"))+ ", ";
                sql+=  "       jik               = " + SQLString.Format((String)v_member.get("jikup"))+ ", ";
                sql+=  "       appdate           = " + (v_appdate           == null ? "appdate"           : SQLString.Format(v_appdate))    + ", ";
                sql+=  "       isdinsert         = " + (v_isdinsert         == null ? "isdinsert"         : SQLString.Format(v_isdinsert))  + ", ";
                sql+=  "       isb2c             = " + (v_isb2c             == null ? "isb2c"             : SQLString.Format(v_isb2c))      + ", ";
                sql+=  "       ischkfirst        = " + (v_ischkfirst        == null ? "ischkfirst"        : SQLString.Format(v_ischkfirst)) + ", ";
                sql+=  "       chkfirst          = " + (v_chkfirst          == null ? "chkfirst"          : SQLString.Format(v_chkfirst))   + ", ";
                sql+=  "       isproposeapproval = " + (v_isproposeapproval == null ? "isproposeapproval" : SQLString.Format(v_isproposeapproval))   + ", ";
                sql+=  "       chkfinal          = " + (v_chkfinal          == null ? "chkfinal"          : SQLString.Format(v_chkfinal))   + ", ";
                sql+=  "       proptxt           = " + (v_proptxt           == null ? "proptxt"           : SQLString.Format(v_proptxt))    + ", ";
                //sql+=  "       chkfirsttxt       = " + (v_chkfirsttxt       == null ? "chkfirsttxt"       : SQLString.Format(v_chkfirsttxt))    + ", ";
                //sql+=  "       chkfirstmail      = " + (v_chkfirstmail      == null ? "chkfirstmail"      : SQLString.Format(v_chkfirstmail))   + ", ";
                //sql+=  "       chkfirstuserid    = " + (v_chkfirstuserid    == null ? "chkfirstuserid"    : SQLString.Format(v_chkfirstuserid)) + ", ";
                //sql+=  "       chkfirstdate      = " + (v_chkfirstdate      == null ? "chkfirstdate"      : SQLString.Format(v_chkfirstdate))   + ", ";
                sql+=  "       billstat          = " + (v_billstat          == null ? "billstat"          : SQLString.Format(v_billstat))   + ", ";
                sql+=  "       ordcode           = " + (v_ordcode           == null ? "ordcode"           : SQLString.Format(v_ordcode))    + ", ";
                sql+=  "       cancelkind        = " + (v_cancelkind        == null ? "cancelkind"        : SQLString.Format(v_cancelkind)) + ", ";
                sql+=  "       luserid           = " + (v_luserid           == null ? "luserid"           : SQLString.Format(v_luserid))    + ", ";
                sql+=  "       ldate             = " + SQLString.Format(FormatDate.getDate("yyyyMMddHHmmss"));
                sql+=  " where subj              = " + SQLString.Format(v_subjseqdata.getSubj());
                sql+=  "   and year              = " + SQLString.Format(v_subjseqdata.getYear());
                sql+=  "   and subjseq           = " + SQLString.Format(v_subjseqdata.getSubjseq());
                sql+=  "   and userid            = " + SQLString.Format(v_userid);
               // System.out.println("sql_update="+sql);

                isOk = connMgr.executeUpdate(sql);
               //isOk = 1;
            }
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (v_CreateConnManager) {
                if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            }
        }
        return isOk;
    }

    public int updateStudent(Hashtable data) throws Exception {
        int isOk = 0;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        String sql = "";

        String v_subj = (String)data.get("subj");
        String v_year = (String)data.get("year");
        String v_subjseq = (String)data.get("subjseq");
        String v_userid = (String)data.get("userid");
        String v_isdinsert = (String)data.get("isdinsert");
        String v_chkfirst = (String)data.get("chkfirst");
        String v_chkfinal = (String)data.get("chkfinal");
        String v_luserid  = (String)data.get("luserid");
        Hashtable v_member = null;
        v_member = getMeberInfo(v_userid);

        String v_isb2c    = "N";  // tz_grcode에서 찾아온다.
        // 학습기간 설정
        String v_edustart = (String)data.get("edustart");
        String v_eduend   = (String)data.get("eduend");

        String v_class       = (String)data.get("class");
        String v_comp        = (String)data.get("comp"        );
        String v_score       = (String)data.get("score"       );
        String v_tstep       = (String)data.get("tstep"       );
        String v_mtest       = (String)data.get("mtest"       );
        String v_ftest       = (String)data.get("ftest"       );
        String v_report      = (String)data.get("report"      );
        String v_act         = (String)data.get("act"         );
        String v_etc1        = (String)data.get("etc1"        );
        String v_etc2        = (String)data.get("etc2"        );
        String v_avtstep     = (String)data.get("avtstep"     );
        String v_avmtest     = (String)data.get("avmtest"     );
        String v_avftest     = (String)data.get("avftest"     );
        String v_avreport    = (String)data.get("avreport"    );
        String v_avact       = (String)data.get("avact"       );
        String v_avetc1      = (String)data.get("avetc1"      );
        String v_avetc2      = (String)data.get("avetc2"      );
        String v_isgraduated = (String)data.get("isgraduated" );
        String v_isrestudy   = (String)data.get("isrestudy"   );
        String v_branch      = (String)data.get("branch"      );
        String v_confirmdate = (String)data.get("confirmdate" );
        String v_eduno       = (String)data.get("eduno"       );

        ArrayList list = null;
        CPSubjseqData v_subjseqdata = null;

        try {
            connMgr = (DBConnectionManager)data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }
            list = getCourseToSubj(v_subj, v_year, v_subjseq);
            for (int i=0; i<list.size(); i++) {
                v_subjseqdata = (CPSubjseqData)list.get(i);

                sql =  "update TZ_STUDENT " ;
                sql+=  "   set class       =" + (v_class      == null ? "class"       : SQLString.Format(v_class      )) + ", ";
                sql+=  "       comp        =" + (v_comp       == null ? "comp"        : SQLString.Format(v_comp       )) + ", ";
                sql+=  "       isdinsert   =" + (v_isdinsert  == null ? "isdinsert"   : SQLString.Format(v_isdinsert  )) + ", ";
                sql+=  "       score       =" + (v_score      == null ? "score"       : SQLString.Format(v_score      )) + ", ";
                sql+=  "       tstep       =" + (v_tstep      == null ? "tstep"       : SQLString.Format(v_tstep      )) + ", ";
                sql+=  "       mtest       =" + (v_mtest      == null ? "mtest"       : SQLString.Format(v_mtest      )) + ", ";
                sql+=  "       ftest       =" + (v_ftest      == null ? "ftest"       : SQLString.Format(v_ftest      )) + ", ";
                sql+=  "       report      =" + (v_report     == null ? "report"      : SQLString.Format(v_report     )) + ", ";
                sql+=  "       act         =" + (v_act        == null ? "act"         : SQLString.Format(v_act        )) + ", ";
                sql+=  "       etc1        =" + (v_etc1       == null ? "etc1"        : SQLString.Format(v_etc1       )) + ", ";
                sql+=  "       etc2        =" + (v_etc2       == null ? "etc2"        : SQLString.Format(v_etc2       )) + ", ";
                sql+=  "       avtstep     =" + (v_avtstep    == null ? "avtstep"     : SQLString.Format(v_avtstep    )) + ", ";
                sql+=  "       avmtest     =" + (v_avmtest    == null ? "avmtest"     : SQLString.Format(v_avmtest    )) + ", ";
                sql+=  "       avftest     =" + (v_avftest    == null ? "avftest"     : SQLString.Format(v_avftest    )) + ", ";
                sql+=  "       avreport    =" + (v_avreport   == null ? "avreport"    : SQLString.Format(v_avreport   )) + ", ";
                sql+=  "       avact       =" + (v_avact      == null ? "avact"       : SQLString.Format(v_avact      )) + ", ";
                sql+=  "       avetc1      =" + (v_avetc1     == null ? "avetc1"      : SQLString.Format(v_avetc1     )) + ", ";
                sql+=  "       avetc2      =" + (v_avetc2     == null ? "avetc2"      : SQLString.Format(v_avetc2     )) + ", ";
                sql+=  "       isgraduated =" + (v_isgraduated== null ? "isgraduated" : SQLString.Format(v_isgraduated)) + ", ";
                sql+=  "       isrestudy   =" + (v_isrestudy  == null ? "isrestudy"   : SQLString.Format(v_isrestudy  )) + ", ";
                sql+=  "       isb2c       =" + (v_isb2c      == null ? "isb2c"       : SQLString.Format(v_isb2c      )) + ", ";
                sql+=  "       edustart    =" + (v_edustart   == null ? "edustart"    : SQLString.Format(v_edustart   )) + ", ";
                sql+=  "       eduend      =" + (v_eduend     == null ? "eduend"      : SQLString.Format(v_eduend     )) + ", ";
                sql+=  "       branch      =" + (v_branch     == null ? "branch"      : SQLString.Format(v_branch     )) + ", ";
                sql+=  "       confirmdate =" + (v_confirmdate== null ? "confirmdate" : SQLString.Format(v_confirmdate)) + ", ";
                sql+=  "       eduno       =" + (v_eduno      == null ? "eduno"       : SQLString.Format(v_eduno      )) + ", ";
                sql+=  "       luserid     =" + SQLString.Format(v_luserid) + ", ";
                sql+=  "       ldate       =" + FormatDate.getDate("yyyyMMddHHmmss");
                sql+=  " where subj    =   =" + SQLString.Format(v_subjseqdata.getSubj());
                sql+=  "   and year    =   =" + SQLString.Format(v_subjseqdata.getYear());
                sql+=  "   and subjseq =   =" + SQLString.Format(v_subjseqdata.getSubjseq());
                sql+=  "   and userid  =   =" + SQLString.Format(v_userid);

                isOk = connMgr.executeUpdate(sql);
            }
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (v_CreateConnManager) {
                if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            }
        }
        return isOk;
    }

    public Hashtable FileToDB(Hashtable inputdata) {
        Hashtable outputdata = new Hashtable();
        Hashtable insertdata = new Hashtable();

        outputdata.put("p_errorcode", "1");

        DBConnectionManager connMgr = (DBConnectionManager)inputdata.get("connMgr");
        PreparedStatement propose_pstmt = (PreparedStatement)inputdata.get("propose_pstmt");
        PreparedStatement student_pstmt = (PreparedStatement)inputdata.get("student_pstmt");

        String  v_grcode     = (String)inputdata.get("p_grcode");
        String  v_gyear      = (String)inputdata.get("p_gyear");
        String  v_grseq      = (String)inputdata.get("p_grseq");
        String  v_subjcourse = (String)inputdata.get("p_subjcourse");
        String  v_luserid    = (String)inputdata.get("p_luserid");
        String  v_userid     = (String)inputdata.get("p_userid");
        //      String  v_cono       = (String)inputdata.get("p_cono");
        String  v_name       = (String)inputdata.get("p_name");
        String  v_subjseq    = (String)inputdata.get("p_subjseq");
        String  v_inputlevel = (String)inputdata.get("p_inputlevel");
        String v_isdinsert  = "Y";



        // 등록권한별 1차승인, 2차승인 결정
        String v_chkfirst   = "Y";
        String v_chkfinal   = "Y";

        // 수강신청일경우 미처리로 세팅
        if (v_inputlevel.equals("propose")) {
            v_chkfirst   = "B";
            v_chkfinal   = "B";
        }

        try {
        	String  v_year = getSubjYear(v_grcode, v_gyear, v_grseq, v_subjcourse, v_subjseq);
            if (v_year.equals("")) v_year = v_gyear;
            
            //팀장승인여부
            String v_isproposeapproval = "L";    //팀장승인
            String v_useproposeapproval = "";
            v_useproposeapproval = getProposeApproval(v_subjcourse,v_year,v_subjseq);
            //System.out.println("v_useproposeapproval==============>"+v_useproposeapproval);
            if (v_useproposeapproval.equals("N")){
            	v_isproposeapproval = "L";               //팀장승인여부 L:불필요 B:상신중 Y:승인 N:반려
            }
            else if (v_useproposeapproval.equals("Y")){
            	v_isproposeapproval = "Y";               //팀장승인여부 L:불필요 B:상신중 Y:승인 N:반려
            }
            

            insertdata.put("connMgr",  connMgr);
            insertdata.put("subj",     v_subjcourse);
            insertdata.put("year",     v_year);
            insertdata.put("subjseq",  v_subjseq);
            insertdata.put("userid",   v_userid);
            insertdata.put("isdinsert",v_isdinsert);
            insertdata.put("chkfirst", v_chkfirst);
            insertdata.put("chkfinal", v_chkfinal);
            insertdata.put("isproposeapproval", v_isproposeapproval);
            insertdata.put("luserid",  v_luserid);
            if (propose_pstmt != null) {
                insertdata.put("propose_pstmt",  propose_pstmt);
            }
            insertPropose(insertdata);

            if (v_inputlevel.equals("student")) {
                if (student_pstmt != null) {
                    insertdata.put("student_pstmt",  student_pstmt);
                }
                insertStudent(insertdata);
            }
        }
        catch(Exception ex) {
            outputdata.put("p_errorcode", "0");
            outputdata.put("p_exception", ex);
        }
        finally {

        }
        return outputdata;
    }



}
