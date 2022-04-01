//**********************************************************
//1. 제      목: 교육대상선정 마법사 관리
//2. 프로그램명: ProposeWizardBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: 2004.03. 17
//7. 수      정:
//
//**********************************************************

package com.credu.propose;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.Constants;
import com.dunet.common.util.EncryptUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */


@SuppressWarnings("unchecked")
public class ProposeWizardBean {

	/*선정된 교육대상자 검색 */
	public ArrayList SelectedAcceptTargetMember(RequestBox box) throws Exception {
		ArrayList list = new ArrayList();
		DataBox dbox= null;

		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ListSet ls = null;
		String sql1  = "";
		String sql2  = "";

		/* 검색.params : 본부, 부서, 사원구분(FIX_GUBUN), 입사일(시작~종료)(ENT_DATE), 승격일(시작~종료)-PMT_DATE,
                        직위(jikwi), 직급(jikup), 성별(sex), 호봉 (시작~ 종료)-JIKHO, JIKHONM    */

		String  ss_mastercd       = box.getString("s_mastercd");
		String  ss_company       = box.getString("s_company");

		String p_order      = box.getString("p_order");
		String v_orderType     = box.getString("p_orderType");                 //정렬할 순서

		String  v_subjnm     = "";
		String  v_subj     = "";
		String  v_subjseq  = "";
		String  v_subjseqgr= "";
		String  v_year     = "";
		String  v_chkfinal = "";
		String  v_appdate  = "";
		String  v_isproposeapproval = "";
		//ErrorManager.systemOutPrintln(box);

		try {
			sql1  = " select a.userid, a.subj, a.subjnm, a.subjseq, a.year, a.isproposeapproval, ";
			sql1 += " get_compnm(b.comp,2,2) companynm, b.comp ,";
			sql1 += " get_deptnm(b.deptnam, b.userid) compnm, get_jikwinm(b.jikwi,b.comp) jikwinm, ";
			sql1 += " b.cono, b.resno,b.name, b.email";
			sql1 += " from TZ_EDUTARGET a, TZ_MEMBER b  ";
			sql1 += " where a.userid = b.userid ";
			if(!ss_mastercd.equals("ALL")){
				sql1 += " and a.mastercd="+StringManager.makeSQL(ss_mastercd)+" ";
			}
			if(!ss_company.equals("ALL")){
				sql1 += " and substring(b.comp, 1,4) ="+StringManager.makeSQL(StringManager.substring(ss_company,0,4))+" ";
			}
			//sql1 += " ";

			if (p_order.equals("compnm1"))  sql1 +="order by get_compnm(b.comp,2,2)" + v_orderType;
			if (p_order.equals("name"))     sql1 +="order by b.name" + v_orderType;
			if (p_order.equals("compnm2"))  sql1 +="order by get_deptnm(b.deptnam, b.userid)" + v_orderType;
			if (p_order.equals("jikwinm"))  sql1 +="order by get_jikwinm(b.jikwi,b.comp)" + v_orderType;
			if (p_order.equals("userid"))   sql1 +="order by b.userid" + v_orderType;

			System.out.println(sql1);
			connMgr = new DBConnectionManager();
			ls = connMgr.executeQuery(sql1);


			sql2 = " select b.subj, b.subjseq, b.year, b.chkfinal, b.isproposeapproval, a.scsubjnm  subjnm, a.subjseqgr, b.appdate ";
			//sql2 += "  (select subjnm from tz_subjseq where a.subjcourse = subj and a.subjseq = subjseq and a.year = year) subjnm ";
			sql2 += "from vz_mastersubjseq a, tz_propose b ";
			sql2 += "where  ";
			sql2 += "  a.subj = b.subj ";
			sql2 += "  and a.subjseq = b.subjseq ";
			sql2 += "  and a.year = b.year  ";
			//                sql2 += "  and (b.isproposeapproval = 'Y' or b.isproposeapproval = 'L') ";
			sql2 += "  and a.mastercd = "+StringManager.makeSQL(ss_mastercd)+" ";
			sql2 += "  and b.userid= ?  ";
			sql2 += "  order by b.appdate asc";

			System.out.println("sql2_targe="+sql2);
			pstmt = connMgr.prepareStatement(sql2);

			while(ls.next()){
				pstmt.setString(1, ls.getString("userid"));
				rs = pstmt.executeQuery();
				v_subj     = "";
				v_subjnm   = "";
				v_subjseq  = "";
				v_year     = "";
				v_chkfinal = "";
				v_isproposeapproval = "";

				while (rs.next()) {
					v_isproposeapproval = rs.getString("isproposeapproval");    //현업팀장 결재여부
					v_chkfinal = rs.getString("chkfinal");                      //최종승인 여부
					v_subj     = rs.getString("subj");
					v_subjnm   = rs.getString("subjnm");
					v_subjseq  = rs.getString("subjseq");
					v_subjseqgr= rs.getString("subjseqgr");
					v_appdate  = rs.getString("appdate");
					v_year     = rs.getString("year");
					//System.out.println(v_appdate);
				}

				dbox = ls.getDataBox();

				dbox.put("d_psubj", v_subj);
				dbox.put("d_psubjnm", v_subjnm);
				dbox.put("d_psubjseq", v_subjseq);
				dbox.put("d_psubjseqgr", v_subjseqgr);
				dbox.put("d_pyear", v_year);
				dbox.put("d_pchkfinal", v_chkfinal);
				dbox.put("d_appdate", v_appdate);
				dbox.put("d_pisproposeapproval", v_isproposeapproval);
				//dbox.put("d_tproposecnt", v_tproposecnt);
				list.add(dbox);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(rs != null) { try { rs.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}

	/**
    선정할 교육대상자 검색
    @param box        receive from the form object and session
    @return ArrayList 선정할 교육대상자 리스트
	 */
	public ArrayList SelectAcceptTargetMember(RequestBox box) throws Exception {
		ArrayList list = new ArrayList();
		DataBox dbox= null;

		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql1  = "";
		ProposeBean probean = new ProposeBean();

		String v_grcode           = box.getString("s_grcode");
		String v_gyear            = box.getString("s_gyear");
		String v_grseq            = box.getString("s_grseq");
		String v_subjcourse       = box.getString("s_subjcourse");
		String v_subjseq          = box.getString("s_subjseq");
		String v_mastercd         = box.getString("s_mastercd");
		String v_name             = box.getString("p_name");
		String v_userid           = box.getString("p_userid");

		String v_year       = probean.getSubjYear(v_grcode, v_gyear, v_grseq, v_subjcourse, v_subjseq);

		try {

			sql1  = "select a.userid, a.resno, a.name, a.email, a.membergubun,comptext, HANDPHONE,\n";
			sql1 += " (select 'Y' from tz_edutarget   where  userid= a.userid and mastercd = '"+v_mastercd+"') istarget,\n";
			sql1 += " (select 'Y' from tz_propose     where  userid= a.userid and subj = '"+v_subjcourse+"' and subjseq = '"+v_subjseq+"' and year = '"+v_year+"') ispropose \n";
			sql1 += "  from\n";
			sql1 += "      ( select userid, resno, name, email, membergubun,COMPTEXT,HANDPHONE  \n";
			sql1 += "          from TZ_MEMBER  \n";
			sql1 += "         where 1=1\n";
			if(!v_userid.trim().equals("")) sql1 += " and userid like '%"+v_userid+"%'\n";
			if(!v_name.trim().equals("")) sql1 += "  and name like '%"+v_name+"%'\n";
            if(!v_grcode.trim().equals("")) sql1 += "  and grcode ='"+v_grcode+"'\n";
			sql1 += "      ) a\n ORDER BY name";

			connMgr = new DBConnectionManager();
			ls = connMgr.executeQueryByInsecitive(sql1);
			while(ls.next()){
				dbox = ls.getDataBox();
				list.add(dbox);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}
	public ArrayList SelectMember(RequestBox box) throws Exception {
		ArrayList list = new ArrayList();
		DataBox dbox= null;

		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql1  = "";


		String p_gubun = box.get("p_gubun");
		String p_key1 = box.get("p_key1");

		try {
			sql1  = "select grcode, userid, resno, name, email, membergubun,COMPTEXT,HANDPHONE  \n";
			sql1 += "          from TZ_MEMBER  \n";
			sql1 += "         where 1=1\n";
			if(p_gubun.trim().equals("name"))	sql1 += " and name like '%"+p_key1+"%'\n";
			else if(p_gubun.trim().equals("userid")) sql1 += " and userid like '%"+p_key1+"%'\n";
			else sql1 += "  and 1=2\n";

			connMgr = new DBConnectionManager();
			ls = connMgr.executeQuery(sql1);
			while(ls.next()){
				dbox = ls.getDataBox();

                if (dbox.getString("d_grcode").equals("N000001")) {
                    //====================================================
                    // 개인정보 복호화
                    EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY,Constants.APP_IV);
                    if (!dbox.getString("d_email").equals("")) dbox.put("d_email",encryptUtil.decrypt(dbox.getString("d_email")));
                    if (!dbox.getString("d_handphone").equals("")) dbox.put("d_handphone",encryptUtil.decrypt(dbox.getString("d_handphone")));
                    //====================================================
                }

				list.add(dbox);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}

	/*선정된 교육대상자 삭제처리 */
	public int SelectedDeleteTargetMember(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		String sql1  = "";
		ProposeBean probean = new ProposeBean();
		int isOk =1 ;

		/* 교육 및 과정 차수 정보 selected Params*/
		String v_grcode     = box.getString("s_grcode");           //교육그룹
		String v_gyear      = box.getString("s_gyear");            //년도
		String v_mastercd   = box.getString("s_mastercd");
		String v_subjseq    = box.getString("s_subjseq");
		//String v_year       = probean.getSubjYear(v_grcode, v_gyear, v_grseq, v_subj, v_subjseq);
		//if (v_year.equals("")) v_year = v_gyear;

		//p_subj로 넘어온 다수의 value를 처리하기 위해 vector로 구현
		String v_userid  = "";
		Vector v_checks  = box.getVector("p_checks");
		Enumeration em   = v_checks.elements();
		Hashtable deleteData = new Hashtable();
		String v_luserid     = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);////

			while(em.hasMoreElements()){
				v_userid = (String)em.nextElement();

				deleteData.clear();
				deleteData.put("grcode",     v_grcode);
				deleteData.put("mastercd", v_mastercd);
				//insertData.put("subjnm",   v_subjnm);
				deleteData.put("gyear",      v_gyear);
				deleteData.put("subjseq",    v_subjseq);
				deleteData.put("userid",     v_userid);
				deleteData.put("luserid",    v_luserid);
				isOk = probean.deleteEduTarget(deleteData);
			}

			if(isOk >0) {   connMgr.commit();   }
			else        {   connMgr.rollback(); }

		}
		catch (Exception ex) {
			isOk =0;
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}


	/*선정된 교육대상자 입과처리 */
	public int AcceptTargetMember(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		String sql1  = "";
		ProposeBean probean = new ProposeBean();
		int isOk =1;

		/* 교육 및 과정 차수 정보 selected Params*/
		String v_grcode     = box.getString("s_grcode");           //교육그룹
		String v_gyear      = box.getString("s_gyear");            //년도
		String v_grseq      = box.getString("s_grseq");            //교육차수
		String v_subj       = box.getString("s_subjcourse");
		String v_mastercd   = box.getString("s_mastercd");
		String v_subjseq    = box.getString("s_subjseq");
		String v_subjnm     = box.getString("s_subjnm");
		String v_year       = probean.getSubjYear(v_grcode, v_gyear, v_grseq, v_subj, v_subjseq);
		if (v_year.equals("")) v_year = v_gyear;

		//p_subj로 넘어온 다수의 value를 처리하기 위해 vector로 구현
		String v_userid  = "";
		Vector v_checks  = box.getVector("p_checks");
		Enumeration em   = v_checks.elements();
		Hashtable insertData = new Hashtable();
		//String v_type    ="s";
		String v_isproposeapproval   ="";
		//String v_chkfinal  ="Y";
		String v_luserid     = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);////

			while(em.hasMoreElements()){
				v_userid = (String)em.nextElement();

				insertData.clear();
				insertData.put("grcode",     v_grcode);
				insertData.put("subjcourse", v_subj);
				insertData.put("mastercd",   v_mastercd);
				insertData.put("subjnm",     v_subjnm);
				insertData.put("gyear",      v_year);
				insertData.put("subjseq",    v_subjseq);
				insertData.put("isproposeapproval",  v_isproposeapproval);
				insertData.put("userid",     v_userid);
				insertData.put("luserid",    v_luserid);
				isOk = probean.insertEduTarget(insertData);

			}

			if(isOk >0) {   connMgr.commit();   }
			else        {   connMgr.rollback(); }

		}
		catch (Exception ex) {
			isOk =0;
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}


	/* 호봉 select list */
	public ArrayList getJikho(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;

		try {

			connMgr = new DBConnectionManager();
			list = new ArrayList();

			sql = "select distinct jikho, jikhonm ";
			sql += " from tz_member where jikho is not null";
			sql += " order by jikho";

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
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}

	public int edutargetCount(RequestBox box) throws Exception {

		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql1  = "";

		int  db_cnt = 0;

		String  ss_mastercd       = box.getString("s_mastercd");

		try {
			sql1  = "select count(a.userid) cnt ";
			sql1 += "from TZ_EDUTARGET a, TZ_MEMBER b ";
			sql1 += "where a.userid = b.userid ";
			//sql1 += "and b.comp = c.comp ";
			sql1 += "and a.mastercd="+StringManager.makeSQL(ss_mastercd)+" ";
			//System.out.println("sql11111111="+sql1);

			connMgr = new DBConnectionManager();
			ls = connMgr.executeQuery(sql1);
			if(ls.next()){
				db_cnt = ls.getInt(1);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return db_cnt;
	}

}