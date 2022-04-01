
//**********************************************************
//  1. 제      목: 상담 관리(mail/sms)
//  2. 프로그램명 : CounselMailBean.java
//  3. 개      요: 상담 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 신상철 2008. 9. 29
//  7. 수      정:
//**********************************************************

package com.credu.study;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * 상담 관리(ADMIN)
 *
 * @date   : 2008. 9
 * @author : s.c Sinn
 */
@SuppressWarnings("unchecked")
public class CounselMailBean {
	public CounselMailBean() {}

	/**
    상담화면 리스트
    @param box          receive from the form object and session
    @return ArrayList   상담 리스트
	 */
	public ArrayList selectCounselMailList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;
		String v_userid = box.getString("p_userid");
		String v_subj = box.getString("p_subj");
		String v_year = box.getString("p_year");
		String v_subjseq = box.getString("p_subjseq");
		String v_cmode = box.getStringDefault("s_cmode","ALL");
		String v_ismail = box.getString("p_ismail");

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			sql  = " select *  ";
			sql += "   From tz_humantouch         ";
			sql += "  where                       ";
			sql += "    userid  = " + StringManager.makeSQL(v_userid);
			sql += "    and year  = " + StringManager.makeSQL(v_year);
			if (!v_subj.equals("ALL")) {
				sql += "    and subj   = " + StringManager.makeSQL(v_subj);
			}
			if (!v_subjseq.equals("ALL")) {
				sql += "    and subjseq   = " + StringManager.makeSQL(v_subjseq);
			}
			if (!v_cmode.equals("ALL")) {
				sql += "    and cmode   = " + StringManager.makeSQL(v_cmode);
			}
			sql += "    and ismail   = " + StringManager.makeSQL(v_ismail);

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

}
