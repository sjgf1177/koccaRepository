//**********************************************************
//  1. 제      목: 외주업체관리 빈
//  2. 프로그램명: CPCompBean.java
//  3. 개      요: 외주업체관리 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004. 12. 15
//  7. 수      정:
//**********************************************************

package com.credu.cp;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.system.*;

/**
*외주업체관리
*<p>제목:CPCompBean.java</p>
*<p>설명:외주업체관리 빈</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 이창훈
*@version 1.0
*/

public class CPCompBean {

    public CPCompBean() {}

    /**
    외주업체리스트
    @param box          receive from the form object and session
    @return ArrayList	외주업체 리스트
    */
    public ArrayList selectCompList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        //BulletinData data = null;
        DataBox dbox = null;
        String s_cpseq = box.getSession("cpseq");
        String s_gadmin = box.getSession("gadmin");

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
			sql += " a.handphone  \n";
			sql += " from tz_cpinfo a, tz_outmember b \n";
			sql += " where a.userid = b.userid \n";
			if(s_gadmin.equals("S1") || s_gadmin.equals("T1") || s_gadmin.equals("M1")){
			  sql += " and a.cpseq = '"+s_cpseq+"'\n";
		    }
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
    선택된 회사정보 상세내용
    @param box          receive from the form object and session
    @return DataBox		외주업체 상세정보
    */
	public DataBox selectComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String v_seq = box.getString("p_cpseq");

        if(box.getSession("gadmin").equals("S1") || box.getSession("gadmin").equals("M1") || box.getSession("gadmin").equals("T1")){
        	//외주업체 담당자라면 해당 회사정보코드를 알아낸다.
        	//v_seq = this.selectCPseq(box.getSession("userid"));
        	//v_seq = CodeConfigBean.addZero(StringManager.toInt(v_seq), 5);
        	//v_seq = CodeConfigBean.addZero(StringManager.toInt(v_seq), 5);
    	}
    	else{
        	//v_seq = CodeConfigBean.addZero(StringManager.toInt(box.getString("p_cpseq")), 5);
        	//v_seq = box.getString("p_cpseq");
        }

        try {
            connMgr = new DBConnectionManager();

			sql = "select                                                                       \n";
			sql += "   a.cpseq,           a.cpnm,            a.cpresno,        a.homesite,      \n";
			sql += "   a.address,         a.ldate,           b.userid,          b.pwd,          \n";
			sql += "   b.name,            b.email,           b.comptel,         a.compgubun,    \n";
			sql += "   a.handphone,                             \n";
			sql += "   a.captinnm,        a.busicategory,    a.employeetot,     a.employeeje,   \n"; 
            sql += "   a.employeebi,      a.estabyear,       a.estabmon,        a.estabday,     \n"; 
            sql += "   a.capital,         a.capitalplan,     a.capitalresult,   a.ownsubj,      \n"; 
            sql += "   a.goyonownsubj,    a.bigoyonownsubj,  a.devsubj,         a.goyondevsubj, \n"; 
            sql += "   a.bigoyondevsubj,  a.devsystem,       a.familysite,      a.vision,       \n"; 
            sql += "   a.mission,         a.mainbusi,        a.subbusi,         a.ownsolution,  \n"; 
            sql += "   a.prizerecord,     a.auth,            a.systemnm,        a.special,      \n"; 
            sql += "   a.bigo,            a.resno                                               \n";
			sql += " from ";
			sql += " tz_cpinfo a, tz_outmember b ";
			sql += "   where ";
			sql += "   a.userid = b.userid and a.cpseq = " + SQLString.Format(v_seq);

            ls = connMgr.executeQuery(sql);

        	//관리자가 외주업체계정으로 접속했을경우의 처리

            while(ls.next()) {
                dbox = ls.getDataBox();
                box.put("vflag","1");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }


    /**
    새로운 외주업체 정보 등록
    @param box		receive from the form object and session
    @return int		외주업체 등록 정상처리여부(1:update success,0:update fail)
    */
     public int insertComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        PreparedStatement pstmt5 = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String sql4 = "";
        String sql5 = "";
        int isOk  = 1;
        int isOk1 = 1;
        int isOk2 = 1;
        int isOk3 = 1;
        int isOk4 = 1;
        int isOk5 = 1;
        String v_cpresno = box.getString("p_cpresno1") + "-" + box.getString("p_cpresno2") + "-" + box.getString("p_cpresno3");
        String v_cpnm = box.getString("p_cpnm");
        String v_homesite = box.getString("p_homesite");
        String v_address = box.getString("p_address");
        String v_compgubun = box.getString("p_compgubun");
        String v_userid = box.getString("p_userid");
        String v_pwd = box.getString("p_pwd");
        String v_name = box.getString("p_name");
        String v_resno = box.getString("p_resno");
        String v_email = box.getString("p_email");
        String v_comtel = box.getString("p_comtel1") + "-" + box.getString("p_comtel2") + "-" + box.getString("p_comtel3");
        String v_handphone = box.getString("p_handphone1") + "-" + box.getString("p_handphone2") + "-" + box.getString("p_handphone3");

        String v_captinnm       = box.getString("p_captinnm");
        String v_busicategory   = box.getString("p_busicategory");
        int    v_employeetot    = box.getInt("p_employeetot");
        int    v_employeeje     = box.getInt("p_employeeje");
        int    v_employeebi     = box.getInt("p_employeebi");
        String v_estabyear      = box.getString("p_estabyear");
        String v_estabmon       = box.getString("p_estabmon");
        String v_estabday       = box.getString("p_estabday");
        int    v_capital        = box.getInt("p_capital");
        int    v_capitalplan    = box.getInt("p_capitalplan");
        int    v_capitalresult  = box.getInt("p_capitalresult");
        int    v_ownsubj        = box.getInt("p_ownsubj");
        int    v_goyonownsubj   = box.getInt("p_goyonownsubj");
        int    v_bigoyonownsubj = box.getInt("p_bigoyonownsubj");
        int    v_devsubj        = box.getInt("p_devsubj");
        int    v_goyondevsubj   = box.getInt("p_goyondevsubj");
        int    v_bigoyondevsubj = box.getInt("p_bigoyondevsubj");
        int    v_devsystem      = box.getInt("p_devsystem");
        String v_familysite     = box.getString("p_familysite");
        String v_vision         = box.getString("p_vision");
        String v_mission        = box.getString("p_mission");
        String v_mainbusi       = box.getString("p_mainbusi");
        String v_subbusi        = box.getString("p_subbusi");
        String v_ownsolution    = box.getString("p_ownsolution");
        String v_prizerecord    = box.getString("p_prizerecord");
        String v_auth           = box.getString("p_auth");
        String v_systemnm       = box.getString("p_systemnm");
        String v_special        = box.getString("p_special");
        String v_bigo           = box.getString("p_bigo");

        String s_userid = box.getSession("userid");
        String s_usernm = box.getSession("username");
        String v_gadmin = v_compgubun + "1";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //----------------------   외주업체 seq번호를 가져온다 ----------------------------
            sql = "select NVL(max(cpseq), '00000') from tz_cpinfo";
            ls = connMgr.executeQuery(sql);
            ls.next();
            String v_seq = CodeConfigBean.addZero(StringManager.toInt(ls.getString(1)) + 1, 5);
            ls.close();
            //------------------------------------------------------------------------------------

            //////////////////////////////////   외주업체정보 table 에 입력  ///////////////////////////////////////////////////////////////////
            sql1 =  " insert into tz_cpinfo(cpseq, userid, cpresno, cpnm, homesite, address, luserid, ldate, compgubun, handphone, \n";
            sql1+=  " captinnm,       busicategory,   employeetot,    employeeje,   \n";
            sql1+=  " employeebi,     estabyear,      estabmon,       estabday,     \n";
            sql1+=  " capital,        capitalplan,    capitalresult,  ownsubj,      \n";
            sql1+=  " goyonownsubj,   bigoyonownsubj, devsubj,        goyondevsubj, \n";
            sql1+=  " bigoyondevsubj, devsystem,      familysite,     vision,       \n";
            sql1+=  " mission,        mainbusi,       subbusi,        ownsolution,  \n";
            sql1+=  " prizerecord,    auth,           systemnm,       special,      \n";
            sql1+=  " bigo,           resno                                         \n";
            sql1+= " )";
            sql1+= " values (?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ";
            sql1+= " ?,  ?,  ?,  ?, ";
            sql1+= " ?,  ?,  ?,  ?, ";
            sql1+= " ?,  ?,  ?,  ?, ";
            sql1+= " ?,  ?,  ?,  ?, ";
            sql1+= " ?,  ?,  ?,  ?, ";
            sql1+= " ?,  ?,  ?,  ?, ";
            sql1+= " ?,  ?,  ?,  ?, ";
            sql1+= " ?,  ?          ";
            sql1+= " )";
            
            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1,  v_seq);
            pstmt1.setString(2,  v_userid);
            pstmt1.setString(3,  v_cpresno);
            pstmt1.setString(4,  v_cpnm);
            pstmt1.setString(5,  v_homesite);
            pstmt1.setString(6,  v_address);
            pstmt1.setString(7,  s_userid);
            pstmt1.setString(8,  v_compgubun);
            pstmt1.setString(9,  v_handphone);
            pstmt1.setString(10,  v_captinnm);
            pstmt1.setString(11, v_busicategory);
            pstmt1.setInt   (12, v_employeetot);
            pstmt1.setInt   (13, v_employeeje);
            pstmt1.setInt   (14, v_employeebi);
            pstmt1.setString(15, v_estabyear);
            pstmt1.setString(16, v_estabmon);
            pstmt1.setString(17, v_estabday);
            pstmt1.setInt   (18, v_capital);
            pstmt1.setInt   (19, v_capitalplan);
            pstmt1.setInt   (20, v_capitalresult);
            pstmt1.setInt   (21, v_ownsubj);
            pstmt1.setInt   (22, v_goyonownsubj);
            pstmt1.setInt   (23, v_bigoyonownsubj);
            pstmt1.setInt   (24, v_devsubj);
            pstmt1.setInt   (25, v_goyondevsubj);
            pstmt1.setInt   (26, v_bigoyondevsubj);
            pstmt1.setInt   (27, v_devsystem);
            pstmt1.setString(28, v_familysite);
            pstmt1.setString(29, v_vision);
            pstmt1.setString(30, v_mission);
            pstmt1.setString(31, v_mainbusi);
            pstmt1.setString(32, v_subbusi);
            pstmt1.setString(33, v_ownsolution);
            pstmt1.setString(34, v_prizerecord);
            pstmt1.setString(35, v_auth);
            pstmt1.setString(36, v_systemnm);
            pstmt1.setString(37, v_special);
            pstmt1.setString(38, v_bigo);
            pstmt1.setString(39, v_resno);

            isOk1 = pstmt1.executeUpdate();

            //////////////////////////////////   member table 에 회원정보 입력  ///////////////////////////////////////////////////////////////////
            sql2 =  "insert into tz_outmember(userid, pwd, name, email, comptel, handphone, resno)";
            sql2 += " values (?, ?, ?, ?, ?, ?, ?) ";

            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_userid);
            pstmt2.setString(2, v_pwd);
            pstmt2.setString(3, v_name);
            pstmt2.setString(4, v_email);
            pstmt2.setString(5, v_comtel);
            pstmt2.setString(6, v_handphone);
            pstmt2.setString(7, v_resno);

            isOk2 = pstmt2.executeUpdate();

			//////////////////////////////////   tz_manager table 에 회원정보 입력  ///////////////////////////////////////////////////////////////////
            sql3 =  "insert into tz_manager(userid, gadmin, fmon, tmon, commented, luserid, ldate)";
            sql3 += " values (?, ?, to_char(sysdate, 'YYYYMMDD'), to_char(sysdate+1825, 'YYYYMMDD'), '";
            sql3 += v_cpnm + " ', ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

            pstmt3 = connMgr.prepareStatement(sql3);

            pstmt3.setString(1, v_userid);
            pstmt3.setString(2, v_gadmin);
            pstmt3.setString(3, s_userid);

            isOk3 = pstmt3.executeUpdate();


            //////////////////////////////////   tz_outcompman table 에 정보 입력  ///////////////////////////////////////////////////////////////////
            sql4 =  "insert into TZ_OUTCOMPMAN(userid, gadmin, comp, luserid, ldate)             ";
            sql4 += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

            pstmt4 = connMgr.prepareStatement(sql4);
            pstmt4.setString(1, v_userid);
            pstmt4.setString(2, v_gadmin);
            pstmt4.setString(3, v_seq);
            pstmt4.setString(4, s_userid);

            isOk4 = pstmt4.executeUpdate();
            
            
            ////추가 -->> 상담이력 insert로 인해 추가
            //////////////////////////////////   member table 에 회원정보 입력  ///////////////////////////////////////////////////////////////////
            sql5 =  "insert into tz_member(userid, pwd, name, email, comptel, handphone, resno, comp)";
            sql5 += " values (?, ?, ?, ?, ?, ?, ?, ?) ";

            pstmt5 = connMgr.prepareStatement(sql5);

            pstmt5.setString(1, v_userid);
            pstmt5.setString(2, v_pwd);
            pstmt5.setString(3, v_name);
            pstmt5.setString(4, v_email);
            pstmt5.setString(5, v_comtel);
            pstmt5.setString(6, v_handphone);
            pstmt5.setString(7, v_resno);
            pstmt5.setString(8, v_seq);

            isOk5 = pstmt5.executeUpdate();

            if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0 && isOk4 > 0 && isOk5 > 0) {
            	isOk = 1;
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
            }
            else{
                isOk = 0;
                if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            }
        }
        catch (Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt1 != null) { try { pstmt1.close(); }catch (Exception e) {} }
            if(pstmt2 != null) { try { pstmt2.close(); }catch (Exception e) {} }
            if(pstmt3 != null) { try { pstmt3.close(); }catch (Exception e) {} }
            if(pstmt4 != null) { try { pstmt4.close(); }catch (Exception e) {} }
            if(pstmt5 != null) { try { pstmt5.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    /**
    선택된 회사정보 수정
    @param box		receive from the form object and session
    @return int		외주업체 수정 정상처리여부(1:update success,0:update fail)
    */
     public int updateComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        PreparedStatement pstmt5 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String sql4 = "";
        String sql5 = "";

        int isOk  = 1;
        int isOk1 = 1;
        int isOk2 = 1;
        int isOk3 = 1;
        int isOk4 = 1;
        int isOk5 = 1;

        //String v_seq = box.getString("p_cpseq");
		//String v_seq = CodeConfigBean.addZero(StringManager.toInt(box.getString("p_cpseq")), 5);
		String v_seq = box.getString("p_cpseq");
        String v_cpresno = box.getString("p_cpresno1") + "-" + box.getString("p_cpresno2") + "-" + box.getString("p_cpresno3");
        String v_cpnm = box.getString("p_cpnm");
        String v_homesite = box.getString("p_homesite");
        String v_address = box.getString("p_address");
        String v_userid = box.getString("p_userid");
        String v_pwd = box.getString("p_pwd");
        String v_name = box.getString("p_name");
        String v_resno = box.getString("p_resno");
        String v_email = box.getString("p_email");
        String v_comtel = box.getString("p_comtel1") + "-" + box.getString("p_comtel2") + "-" + box.getString("p_comtel3");
        String v_handphone = box.getString("p_handphone1") + "-" + box.getString("p_handphone2") + "-" + box.getString("p_handphone3");

        String v_compgubun = box.getString("p_compgubun");
        String v_sourcecomp = box.getString("p_sourcecomp");
        
        String v_captinnm       = box.getString("p_captinnm");
        String v_busicategory   = box.getString("p_busicategory");
        int    v_employeetot    = box.getInt("p_employeetot");
        int    v_employeeje     = box.getInt("p_employeeje");
        int    v_employeebi     = box.getInt("p_employeebi");
        String v_estabyear      = box.getString("p_estabyear");
        String v_estabmon       = box.getString("p_estabmon");
        String v_estabday       = box.getString("p_estabday");
        int    v_capital        = box.getInt("p_capital");
        int    v_capitalplan    = box.getInt("p_capitalplan");
        int    v_capitalresult  = box.getInt("p_capitalresult");
        int    v_ownsubj        = box.getInt("p_ownsubj");
        int    v_goyonownsubj   = box.getInt("p_goyonownsubj");
        int    v_bigoyonownsubj = box.getInt("p_bigoyonownsubj");
        int    v_devsubj        = box.getInt("p_devsubj");
        int    v_goyondevsubj   = box.getInt("p_goyondevsubj");
        int    v_bigoyondevsubj = box.getInt("p_bigoyondevsubj");
        int    v_devsystem      = box.getInt("p_devsystem");
        String v_familysite     = box.getString("p_familysite");
        String v_vision         = box.getString("p_vision");
        String v_mission        = box.getString("p_mission");
        String v_mainbusi       = box.getString("p_mainbusi");
        String v_subbusi        = box.getString("p_subbusi");
        String v_ownsolution    = box.getString("p_ownsolution");
        String v_prizerecord    = box.getString("p_prizerecord");
        String v_auth           = box.getString("p_auth");
        String v_systemnm       = box.getString("p_systemnm");
        String v_special        = box.getString("p_special");
        String v_bigo           = box.getString("p_bigo");

        
        String v_gadmin = v_compgubun+"1";
        String v_sourcegadmin = v_sourcecomp+"1";


        String s_userid = box.getSession("userid");
        String s_usernm = box.getSession("username");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //////////////////////////////////   외주업체정보 table 수정  ///////////////////////////////////////////////////////////////////
            sql1 =  "update tz_cpinfo set ";
            sql1 += "  cpresno = ?,"; 
            sql1 += "  cpnm = ?, ";
            sql1 += "  homesite = ?, ";
            sql1 += "  address = ?, ";
            sql1 += "  luserid = ?, ";
            sql1 += "  ldate = to_char(sysdate, 'YYYYMMDDHH24MISS'), ";
            sql1 += "  compgubun= ?,  ";
            sql1 += "  handphone=?, ";
            sql1 += "  captinnm       = ?, ";
            sql1 += "  busicategory   = ?, ";
            sql1 += "  employeetot    = ?, ";
            sql1 += "  employeeje     = ?, ";
            sql1 += "  employeebi     = ?, ";
            sql1 += "  estabyear      = ?, ";
            sql1 += "  estabmon       = ?, ";
            sql1 += "  estabday       = ?, ";
            sql1 += "  capital        = ?, ";
            sql1 += "  capitalplan    = ?, ";
            sql1 += "  capitalresult  = ?, ";
            sql1 += "  ownsubj        = ?, ";
            sql1 += "  goyonownsubj   = ?, ";
            sql1 += "  bigoyonownsubj = ?, ";
            sql1 += "  devsubj        = ?, ";
            sql1 += "  goyondevsubj   = ?, ";
            sql1 += "  bigoyondevsubj = ?, ";
            sql1 += "  devsystem      = ?, ";
            sql1 += "  familysite     = ?, ";
            sql1 += "  vision         = ?, ";
            sql1 += "  mission        = ?, ";
            sql1 += "  mainbusi       = ?, ";
            sql1 += "  subbusi        = ?, ";
            sql1 += "  ownsolution    = ?, ";
            sql1 += "  prizerecord    = ?, ";
            sql1 += "  auth           = ?, ";
            sql1 += "  systemnm       = ?, ";
            sql1 += "  special        = ?, ";
            sql1 += "  bigo           = ?, ";
            sql1 += "  resno          = ?  ";
            sql1 += "where cpseq   = ? ";

            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_cpresno);
            pstmt1.setString(2, v_cpnm);
            pstmt1.setString(3, v_homesite);
            pstmt1.setString(4, v_address);
            pstmt1.setString(5, s_userid);
            pstmt1.setString(6, v_compgubun);
            pstmt1.setString(7, v_handphone);
            pstmt1.setString(8,  v_captinnm);
            pstmt1.setString(9, v_busicategory);
            pstmt1.setInt   (10, v_employeetot);
            pstmt1.setInt   (11, v_employeeje);
            pstmt1.setInt   (12, v_employeebi);
            pstmt1.setString(13, v_estabyear);
            pstmt1.setString(14, v_estabmon);
            pstmt1.setString(15, v_estabday);
            pstmt1.setInt   (16, v_capital);
            pstmt1.setInt   (17, v_capitalplan);
            pstmt1.setInt   (18, v_capitalresult);
            pstmt1.setInt   (19, v_ownsubj);
            pstmt1.setInt   (20, v_goyonownsubj);
            pstmt1.setInt   (21, v_bigoyonownsubj);
            pstmt1.setInt   (22, v_devsubj);
            pstmt1.setInt   (23, v_goyondevsubj);
            pstmt1.setInt   (24, v_bigoyondevsubj);
            pstmt1.setInt   (25, v_devsystem);
            pstmt1.setString(26, v_familysite);
            pstmt1.setString(27, v_vision);
            pstmt1.setString(28, v_mission);
            pstmt1.setString(29, v_mainbusi);
            pstmt1.setString(30, v_subbusi);
            pstmt1.setString(31, v_ownsolution);
            pstmt1.setString(32, v_prizerecord);
            pstmt1.setString(33, v_auth);
            pstmt1.setString(34, v_systemnm);
            pstmt1.setString(35, v_special);
            pstmt1.setString(36, v_bigo);
            pstmt1.setString(37, v_resno);
            pstmt1.setString(38, v_seq);
            
            isOk1 = pstmt1.executeUpdate();
            
            //////////////////////////////////   OUTmember table 회원정보 수정  ///////////////////////////////////////////////////////////////////
            sql2 =  "update tz_outmember set pwd =?, name =?, email =?, comptel =?, handphone=?, resno=?";
            sql2 += " where userid = ? ";
            
            pstmt2 = connMgr.prepareStatement(sql2);
            
            pstmt2.setString(1, v_pwd);
            pstmt2.setString(2, v_name);
            pstmt2.setString(3, v_email);
            pstmt2.setString(4, v_comtel);
            pstmt2.setString(5, v_handphone);
            pstmt2.setString(6, v_resno);
            pstmt2.setString(7, v_userid);
            
            
            isOk2 = pstmt2.executeUpdate();
            
            
            //////////////////////////////////   tz_manager table 수정  ///////////////////////////////////////////////////////////////////
            
            // manager테이블 삭제
            sql3 = "delete from tz_manager where userid = ? and gadmin = '"+v_sourcegadmin+"' ";
            pstmt3 = connMgr.prepareStatement(sql3);
            pstmt3.setString(1, v_userid);
            isOk3 = pstmt3.executeUpdate();

            System.out.println(isOk3);
            if(isOk3 > 0){
              sql3 =  "insert into tz_manager(userid, gadmin, fmon, tmon, commented, luserid, ldate)";
              sql3 += " values (?, ?, to_char(sysdate, 'YYYYMMDD'), to_char(sysdate+1825, 'YYYYMMDD'), '";
              sql3 += v_cpnm + " ', ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

              pstmt4 = connMgr.prepareStatement(sql3);
              pstmt4.setString(1, v_userid);
              pstmt4.setString(2, v_gadmin);
              pstmt4.setString(3, s_userid);

              isOk3 = pstmt4.executeUpdate();
            }

            //////////////////////////////////   tz_outcompman table 수정  ///////////////////////////////////////////////////////////////////

            // manager테이블 삭제
            sql4 = "delete from tz_outcompman where userid = ? and comp = '"+v_seq+"' ";
            pstmt4 = connMgr.prepareStatement(sql4);
            pstmt4.setString(1, v_userid);
            isOk4 = pstmt4.executeUpdate();

            if(isOk4 > 0){

              sql4 =  "insert into TZ_OUTCOMPMAN(userid, gadmin, comp, luserid, ldate)             ";
              sql4 += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
              pstmt4 = connMgr.prepareStatement(sql4);
              pstmt4.setString(1, v_userid);
              pstmt4.setString(2, v_gadmin);
              pstmt4.setString(3, v_seq);
              pstmt4.setString(4, s_userid);
              isOk4 = pstmt4.executeUpdate();
            }
            
            
            
            //////////////////////////////////   OUTmember table 회원정보 수정  ///////////////////////////////////////////////////////////////////
            sql5 =  "update tz_member set pwd =?, name =?, email =?, comptel =?, handphone=?, resno=?";
            sql5 += " where userid = ? ";
            
            pstmt5 = connMgr.prepareStatement(sql5);
            
            pstmt5.setString(1, v_pwd);
            pstmt5.setString(2, v_name);
            pstmt5.setString(3, v_email);
            pstmt5.setString(4, v_comtel);
            pstmt5.setString(5, v_handphone);
            pstmt5.setString(6, v_resno);
            pstmt5.setString(7, v_userid);
            
            
            isOk5 = pstmt5.executeUpdate();
            
            System.out.println(isOk1);
            System.out.println(isOk2);
            System.out.println(isOk3);
            System.out.println(isOk4);
            System.out.println(isOk5);


            if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0 && isOk4 > 0 && isOk5 > 0) {
                connMgr.commit();
                isOk = 1;
            }
            else{
                connMgr.rollback();
                isOk = 0;
            }
            Log.info.println(this, box, "update process to " + v_seq);
        }
        catch(Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
            if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e) {} }
            if(pstmt4 != null) { try { pstmt4.close(); } catch (Exception e) {} }
            if(pstmt5 != null) { try { pstmt5.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    /**
    선택된 회사정보 삭제
    @param box		receive from the form object and session
    @return int		외주업체 삭제 정상처리여부(1:update success,0:update fail)
    */
    public int deleteComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        PreparedStatement pstmt5 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String sql4 = "";
        String sql5 = "";
        int isOk  = 1;
        int isOk1 = 1;
        int isOk2 = 1;
        int isOk3 = 1;
        int isOk4 = 1;
        int isOk5 = 1;

        //String v_seq = CodeConfigBean.addZero(StringManager.toInt(box.getString("p_cpseq")), 5);

        String v_seq = box.getString("p_cpseq");
        String v_userid    = box.getString("p_userid");
        String v_compgubun = box.getString("p_compgubun");
        String v_cpseq     = box.getString("p_cpseq");
        String v_gadmin    = v_compgubun + "1";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql1 = "delete from tz_cpinfo where cpseq = ?";

            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_seq);

            isOk1 = pstmt1.executeUpdate();

            // 유저테이블 삭제
            sql2 = "delete from tz_outmember where userid = ?";

            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_userid);

            isOk2 = pstmt2.executeUpdate();

            // manager테이블 삭제
            sql3 = "delete from tz_manager where userid = ? and gadmin = '"+v_gadmin+"' ";
            pstmt3 = connMgr.prepareStatement(sql3);

            pstmt3.setString(1, v_userid);

            isOk3 = pstmt3.executeUpdate();


            // tz_outcompman테이블 삭제
            sql4 = "delete from tz_outcompman where userid = ? ";
            pstmt4 = connMgr.prepareStatement(sql4);

            pstmt4.setString(1, v_userid);
            isOk4 = pstmt4.executeUpdate();
            
            
            // 유저테이블 삭제
            sql5 = "delete from tz_member where userid = ?";
            pstmt5 = connMgr.prepareStatement(sql5);
            pstmt5.setString(1, v_userid);
            isOk5 = pstmt5.executeUpdate();

            System.out.println("isOk1="+isOk1);
            System.out.println("isOk2="+isOk2);
            System.out.println("isOk3="+isOk3);
            System.out.println("isOk4="+isOk4);
            System.out.println("isOk5="+isOk5);

            if (isOk1 > 0 && isOk2 > 0 && isOk3 > 0 && isOk4 > 0 && isOk5 > 0) {
            	isOk = 1;
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
            }
            else{
                isOk = 0;
                if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            }

            Log.info.println(this, box, "delete process to " + v_seq);
        }
        catch (Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
            if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e) {} }
            if(pstmt4 != null) { try { pstmt4.close(); } catch (Exception e) {} }
            if(pstmt5 != null) { try { pstmt5.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    등록된 유저아이디인지 체크한다.
    @param box		receive from the form object and session
    @return DataBox	카운트
    */
    public DataBox userCheck(RequestBox box) throws Exception {
    	DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        int isOk = 0;

        String v_userid = box.getString("p_userid");

        try {
        	connMgr = new DBConnectionManager();

            sql = "select count(userid) as cnt from vz_member where lower(userid) = lower(" + SQLString.Format(v_userid)+")";

            ls = connMgr.executeQuery(sql);

            while(ls.next()) {
                dbox = ls.getDataBox();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }

    /**
    외주업체회사코드 조회
    @param	p_userid	외주업체담당자 아이디
    @return	String		외주업체코드
    */
   public String selectCPseq(String p_userid) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String v_cpseq = "";

        try {
            connMgr = new DBConnectionManager();

			sql = "select cpseq, cpnm ";
			sql += " from tz_cpinfo ";
			sql += " where userid = " + SQLString.Format(p_userid);

            ls = connMgr.executeQuery(sql);

            while(ls.next()) {
				//dbox = ls.getDataBox();
				v_cpseq = ls.getString("cpseq");
            }
        }
        catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return v_cpseq;
    }
}
