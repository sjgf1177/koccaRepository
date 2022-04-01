//**********************************************************
//  1. 제      목:
//  2. 프로그램명: SubjComBean.java
//  3. 개      요:
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: 이창훈 2005. 7. 16
//  7. 수      정:
//**********************************************************
package com.credu.common;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.SQLString;

public class SubjComBean {

	public SubjComBean() {}

	public boolean IsMasterPropose(String p_master, String p_userid) throws Exception {
		DBConnectionManager connMgr = null;
		connMgr = new DBConnectionManager();

		ListSet ls = null;
		boolean isMasterPropose = true;

		String sql  = "";

		try {
			sql+= " select \n";
			sql+= "   count(subjseq) CNTS\n";
			sql+= " from \n";
			sql+= "   vz_mastersubjseq a\n";
			sql+= " where\n";
			sql+= "   a.mastercd = '"+p_master+"'\n";
			sql+= "   and (select 'Y' from tz_propose where userid = '"+p_userid+"' and subj= a.scsubj and subjseq = a.scsubjseq and year=a.year and (isproposeapproval !='N'and chkfinal != 'N') ) = 'Y'\n";
			//System.out.println("마스터과정 신청여부 ==>"+sql);
			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				if(ls.getInt("CNTS")>0){
					isMasterPropose = false;
					//System.out.println("신청완료");
				}else{
					//System.out.println("신청가능");
				}
			}
		}

		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isMasterPropose;
	}


	public String IsEduTargetUserid(String p_mastercd, String p_userid) throws Exception {
		DBConnectionManager connMgr = null;
		connMgr = new DBConnectionManager();

		String errCode = "0";

		try {
			errCode = IsEduTargetUserid(connMgr, p_mastercd, p_userid);
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex);
			throw new Exception(ex.getMessage());
		}
		finally {
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return errCode;
	}

	public String IsEduTargetUserid(DBConnectionManager connMgr, String p_mastercd, String p_userid) throws Exception {
		ListSet ls = null;
		String errCode = "0";

		String sql  = "";

		try {
			sql  = " select \n";
			sql += "   count(userid) CNTS            \n";
			sql += " from                            \n";
			sql += "   tz_edutarget                  \n";
			sql += " where  \n";
			sql += "   userid   = "+SQLString.Format(p_userid)+"\n";
			sql += "   and mastercd = "+SQLString.Format(p_mastercd)+"\n";

			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				if(ls.getInt("CNTS")>0){
					errCode = "36";
				}
			}

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
		}
		return errCode;
	}




	public boolean IsEduTarget(String p_subj, String p_userid, String p_gyear) throws Exception {
		DBConnectionManager connMgr = null;
		connMgr = new DBConnectionManager();

		boolean isEduTarget = false;

		try {
			isEduTarget = IsEduTarget(connMgr, p_subj, p_userid, p_gyear);
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex);
			throw new Exception(ex.getMessage());
		}
		finally {
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isEduTarget;
	}

	public boolean IsEduTarget(DBConnectionManager connMgr, String p_subj, String p_userid, String p_gyear) throws Exception {
		ListSet ls = null;
		boolean isEduTarget = false;

		String sql  = "";

		try {
			sql  = " select \n";
			sql += "   count(userid) CNTS            \n";
			sql += " from                            \n";
			sql += "   tz_edutarget a, tz_mastersubj b \n";
			sql += " where b.subjcourse  = " + SQLString.Format(p_subj)+"   \n";
			sql += "   and a.userid  = " +SQLString.Format(p_userid)+"  \n";
			sql += "   and a.mastercd = b.mastercd \n";
			sql += "   and a.gyear = "+SQLString.Format(p_gyear)+" \n";
			//System.out.println(sql);

			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				if(ls.getInt("CNTS")>0){
					isEduTarget = true;
				}
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
		}
		return isEduTarget;
	}


	public boolean IsEduTarget(String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
		DBConnectionManager connMgr = null;
		connMgr = new DBConnectionManager();

		boolean isEduTarget = false;

		try {

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex);
			throw new Exception(ex.getMessage());
		}
		finally {
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isEduTarget;
	}


	public boolean IsEduTarget(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {

		ListSet ls = null;
		boolean isEduTarget = false;

		String sql  = "";

		try {
			sql  = " select \n";
			sql += "   count(userid) CNTS            \n";
			sql += " from                            \n";
			sql += "   tz_edutarget a, tz_mastersubj b \n";
			sql += " where b.subjcourse  = " + SQLString.Format(p_subj)+"   \n";
			sql += "   and b.subjseq     = " + SQLString.Format(p_subjseq)+"  \n";
			sql += "   and b.year        = " + SQLString.Format(p_year)+"   \n";
			sql += "   and a.userid      = " + SQLString.Format(p_userid)+"  \n";
			sql += "   and a.mastercd    = b.mastercd \n";
			ls = connMgr.executeQuery(sql);
			if (ls.next()) {
				if(ls.getInt("CNTS")>0){
					isEduTarget = true;
					//System.out.println("선정된대상자");
				}else{
					//System.out.println("미선정자");
				}
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
		}
		return isEduTarget;
	}



	public boolean IsMasterSubj(String p_subj, String p_gyear) throws Exception {
		DBConnectionManager connMgr = null;
		connMgr = new DBConnectionManager();

		ListSet ls = null;
		boolean isMasterSubj = false;

		String sql  = "";

		try {
			sql  = " select \n";
			sql += "   count(subjseq) CNTS            \n";
			sql += " from                            \n";
			sql += "   vz_mastersubjseq where subj = '"+SQLString.Format(p_subj)+"' and gyear = '"+p_gyear+"' ";

			//System.out.println(sql);

			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				if(ls.getInt("CNTS")>0){
					isMasterSubj = true;
				}
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isMasterSubj;
	}




	public boolean IsMasterSubj(String p_subj, String p_year, String p_subjseq) throws Exception {
		DBConnectionManager connMgr = null;
		connMgr = new DBConnectionManager();

		ListSet ls = null;
		boolean isMasterSubj = false;

		String sql  = "";

		try {
			sql  = " select \n";
			sql += "   count(subjseq) CNTS            \n";
			sql += " from                            \n";
			sql += "   tz_mastersubj   \n";
			sql += " where subjcourse  = " + SQLString.Format(p_subj)+"   \n";
			sql += "   and subjseq = " + SQLString.Format(p_subjseq)+"  \n";
			sql += "   and year    = " + SQLString.Format(p_year)+"   \n";

			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				if(ls.getInt("CNTS")>0){
					isMasterSubj = true;
				}
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isMasterSubj;
	}

	public String getMasterCode(String p_subj, String p_year, String p_subjseq) throws Exception {
		DBConnectionManager connMgr = null;
		connMgr = new DBConnectionManager();

		ListSet ls = null;
		String     mastercode     = "";

		String sql  = "";

		try {
			sql  = " select            \n";
			sql += "   mastercd        \n";
			sql += " from              \n";
			sql += "   tz_mastersubj   \n";
			sql += " where subjcourse  = " + SQLString.Format(p_subj)+"   \n";
			sql += "   and subjseq = " + SQLString.Format(p_subjseq)+"  \n";
			sql += "   and year    = " + SQLString.Format(p_year)+"   \n";
			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				mastercode = ls.getString("mastercd");
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return mastercode;
	}


	public boolean IsPropose(String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
		DBConnectionManager connMgr = null;
		connMgr = new DBConnectionManager();

		ListSet ls = null;
		boolean isPropose = false;
		//        int v_propCnt = 0;

		String sql  = "";

		try {
			sql  = " select \n";
			sql += "   count(userid) CNTS\n";
			sql += " from \n";
			sql += "   tz_propose\n";
			sql += " where subj  = " + SQLString.Format(p_subj)+"   \n";
			sql += "   and subjseq = " + SQLString.Format(p_subjseq)+"  \n";
			sql += "   and year    = " + SQLString.Format(p_year)+"   \n";
			sql += "   and userid  = " + SQLString.Format(p_userid)+"  \n";
			sql += "   and to_char(sysdate,'YYYYMMDDHH24') between b.propstart and b.propend \n";
			sql += "   and a.chkfinal = 'Y'                              \n";

			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				if(ls.getInt("CNTS")>0){
					isPropose = true;
				}
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isPropose;
	}



	///////////////////////////////////////////////현재교육기간별 리턴value//////////////////////////////////////////////////////
	// @return value
	// 0: 교육일 정보 미입력
	// 1: 수강신청전
	// 2: 수강신청기간
	// 3: 운영자승인기간
	// 4: 학습기간
	// 5: 학습종료
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getEduTerm(String p_subj, String p_subjseq, String p_year) throws Exception {
		DBConnectionManager connMgr = null;
		String v_eduterm = "";
		try {
			connMgr = new DBConnectionManager();
			v_eduterm = getEduTerm(connMgr,p_subj, p_subjseq, p_year);
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex);
			throw new Exception(ex.getMessage());
		}
		finally {
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return v_eduterm;
	}

	public String getEduTerm(DBConnectionManager connMgr, String p_subj, String p_subjseq, String p_year) throws Exception {
		ListSet ls = null;
		String v_eduterm = "";
		//        String v_isclosed = "";

		int    v_today = 0;
		int    v_propstart = 0;
		int    v_propend = 0;
		int    v_edustart = 0;
		int    v_eduend = 0;

		String sql  = "";
		System.out.println("-------getEduTerm--------------------------------------start");
		try {

			sql+= " select to_char(sysdate, 'YYYYMMDDHH') today,     ";
			sql+= "        propstart, propend,  edustart, eduend, isclosed ";
			sql+= "   from tz_subjseq                                      ";
			sql+= "  where subj    = '"+p_subj+"'";
			sql+= "    and subjseq ='"+p_subjseq+"'";
			sql+= "    and year    = '"+p_year+"'";

			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				v_today     = ls.getInt("today");

				if(!ls.getString("eduend").equals("")) {
					v_eduend = ls.getInt("eduend");
				}else{
					v_eduend = 0;
				}
				if(!ls.getString("propstart").equals("")) {
					v_propstart = ls.getInt("propstart");
				}else{
					v_propstart = 0;
				}
				if(!ls.getString("propend").equals("")){
					v_propend = ls.getInt("propend");
				}else{
					v_propend = 0;
				}
				if(!ls.getString("edustart").equals("")){
					v_edustart = ls.getInt("edustart");
				}else{
					v_edustart = 0;
				}

				if(!(v_propstart == 0) && !(v_propend == 0)) {
					if(v_today < v_propstart){
						v_eduterm = "1";           //수강신청전
					}else if(v_propstart <= v_today && v_propend > v_today){
						v_eduterm = "2";           //수강신청기간
					}else if(v_propend <= v_today && v_edustart > v_today){
						v_eduterm = "3";           //교육대기기간
					}else if(v_edustart <= v_today && v_eduend > v_today){
						v_eduterm = "4";           //교육기간
					}else if(v_eduend <= v_today){
						v_eduterm = "5";            //교육종료후
					}
				}else{
					v_eduterm = "0";
				}
			}else{
				v_eduterm = "0";
			}

		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		} finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
		}
		return v_eduterm;
	}


	public String getCpApproval(String p_subj) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String v_cpapproval = "";
		String sql  = "";

		try {
			connMgr = new DBConnectionManager();
			sql = "select cpapproval from tz_subj where subj = '"+p_subj+"'";

			System.out.println(sql);
			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				v_cpapproval = ls.getString("cpapproval");
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return v_cpapproval;
	}

}