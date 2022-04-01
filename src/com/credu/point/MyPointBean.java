//**********************************************************
//  1. 제      목: 나의 포인트
//  2. 프로그램명 : MyPointBean.java
//  3. 개      요:  나의 포인트
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009. 11. 30
//  7. 수      정: 2009. 11. 30
//**********************************************************
package com.credu.point;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@SuppressWarnings("unchecked")
public class MyPointBean {


	public MyPointBean() {
	}

	/**
    보유포인트
    @param box      receive from the form object and session
    @return ArrayList
	 **/
	public ArrayList selectHavePointList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls1         = null;
		ArrayList list1     = null;
		DataBox dbox1 = null;
		String sql1         = "";
		String  v_user_id   = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			list1 = new ArrayList();

			sql1 = "select distinct tid   ";
			sql1+= "      , getdate   ";
			sql1+= "      , getpoint   ";
			sql1+= "      , case when expiredate < to_char(sysdate, 'YYYYMMDD') then '유효기간 만료'   ";
			sql1+= "             else rtrim(ltrim(to_char(nvl(usepoint, 0), '9,999,999,999')))   ";
			sql1+= "        end as usepoint   ";
			sql1+= "      , case when expiredate < to_char(sysdate, 'YYYYMMDD') then 0   ";
			sql1+= "             else getpoint - nvl(usepoint, 0) end as lefrpoint   ";
			sql1+= "      , title   ";
			sql1+= "      , subj   ";
			sql1+= "      , year   ";
			sql1+= "      , subjseq   ";
			sql1+= "      , luserid   ";
			sql1+= "      , ldate   ";
			sql1+= "      , expiredate   ";
			sql1+= "from    tz_pointget   ";
			sql1+= "where   userid = " + SQLString.Format(v_user_id);
			sql1+= "order by getdate desc ";

			ls1 = connMgr.executeQuery(sql1);
			//System.out.println("selectHavePointList:"+sql1);
			while (ls1.next()) {
				dbox1 = ls1.getDataBox();

				list1.add(dbox1);
			}
			ls1.close();
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list1;
	}

	/**
     적립포인트
     @param box      receive from the form object and session
     @return ArrayList
	 **/
	public ArrayList selectStoldPointList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls1         = null;
		ArrayList list1     = null;
		DataBox dbox1 = null;
		String sql1         = "";
		String  v_user_id   = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			list1 = new ArrayList();

			sql1 =  " SELECT distinct  tid, userid, getpoint, getdate, title, subj, YEAR, subjseq, luserid, ";
			sql1 += "         ldate, ";
			sql1 += "         (SELECT CASE multiyn ";
			sql1 += "                    WHEN 'Y' ";
			sql1 += "                       THEN (SELECT subjprice ";
			sql1 += "                               FROM tz_billing ";
			sql1 += "                              WHERE tid = a.tid ";
			sql1 += "                                AND subj = a.subj ";
			sql1 += "                                AND year = a.year ";
			sql1 += "                                AND subjseq = a.subjseq ";
			sql1 += "                                AND userid = a.userid) ";
			sql1 += "                    ELSE subjprice ";
			sql1 += "                 END ";
			sql1 += "            FROM tz_billinfo ";
			sql1 += "           WHERE tid = a.tid) subjprice, ";
			sql1 += "         (SELECT CASE multiyn ";
			sql1 += "                    WHEN 'Y' ";
			sql1 += "                       THEN (SELECT price ";
			sql1 += "                               FROM tz_billing ";
			sql1 += "                              WHERE tid = a.tid ";
			sql1 += "                                AND subj = a.subj ";
			sql1 += "                                AND year = a.year ";
			sql1 += "                                AND subjseq = a.subjseq ";
			sql1 += "                                AND userid = a.userid) ";
			sql1 += "                    ELSE price ";
			sql1 += "                 END ";
			sql1 += "            FROM tz_billinfo ";
			sql1 += "           WHERE tid = a.tid) price ";
			sql1 += "    FROM tz_pointget a ";
			sql1 += "   WHERE userid = " + SQLString.Format(v_user_id);
			sql1 += "ORDER BY getdate DESC ";

			ls1 = connMgr.executeQuery(sql1);
			//System.out.println("selectStoldPointList:"+sql1);

			while (ls1.next()) {
				dbox1 = ls1.getDataBox();

				list1.add(dbox1);
			}
			ls1.close();
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list1;
	}

	/**
      사용포인트
      @param box      receive from the form object and session
      @return ArrayList
	 **/
	public ArrayList selectUsePointList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls1         = null;
		ArrayList list1     = null;
		DataBox dbox1 = null;
		String sql1         = "";
		String  v_user_id   = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			list1 = new ArrayList();

			sql1  = " SELECT distinct tid, userid, usepoint, usedate, title, luserid, ldate, ";
			sql1 += "         (SELECT CASE multiyn ";
			sql1 += "                    WHEN 'Y' ";
			sql1 += "                       THEN (SELECT subjprice ";
			sql1 += "                               FROM tz_billing ";
			sql1 += "                              WHERE tid = a.tid ";
			sql1 += "                                AND userid = a.userid) ";
			sql1 += "                    ELSE subjprice ";
			sql1 += "                 END ";
			sql1 += "            FROM tz_billinfo ";
			sql1 += "           WHERE tid = a.tid) subjprice, ";
			sql1 += "         (SELECT CASE multiyn ";
			sql1 += "                    WHEN 'Y' ";
			sql1 += "                       THEN (SELECT price ";
			sql1 += "                               FROM tz_billing ";
			sql1 += "                              WHERE tid = a.tid ";
			sql1 += "                                AND userid = a.userid) ";
			sql1 += "                    ELSE price ";
			sql1 += "                 END ";
			sql1 += "            FROM tz_billinfo ";
			sql1 += "           WHERE tid = a.tid) price ";
			sql1 += "    FROM tz_pointuse a ";
			sql1 += "   WHERE userid = " + SQLString.Format(v_user_id);
			sql1 += "ORDER BY usedate DESC ";

			ls1 = connMgr.executeQuery(sql1);
			System.out.println("selectUsePointList:"+sql1);

			while (ls1.next()) {
				dbox1 = ls1.getDataBox();

				list1.add(dbox1);
			}
			ls1.close();
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list1;
	}

	/**
	 * 나의 적립포인트
	 * @param box          receive from the form object and session
	 * @return int         적립포인트
	 * @throws Exception
	 */
	public int selectGetPoint(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		int result = 0;

		String  v_user_id   = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();

			sql = "select nvl(sum(getpoint - nvl(usepoint, 0)), 0) as getpoint   ";
			sql+= "from   tz_pointget   ";
			sql+= "where  userid = " + SQLString.Format(v_user_id);
			sql+= "and    to_char(sysdate, 'YYYYMMDD') <= nvl(expiredate,TO_CHAR(to_date(substr(getdate,1,8),'yyyymmdd') + 365,'yyyymmdd'))   ";

			ls = connMgr.executeQuery(sql);

			if ( ls.next()) {
				result = ls.getInt("getpoint");
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
		return result;
	}

	/**
	 * 나의 사용포인트
	 * @param box          receive from the form object and session
	 * @return int         사용포인트
	 * @throws Exception
	 */
	public int selectUsePoint(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		int result = 0;

		String  v_user_id   = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();

			//sql = "select  	nvl(sum(usepoint),0) usepoint 		 ";
			sql = "select  	0 usepoint 		 ";
			sql+= "from		tz_pointuse  ";
			sql+= "where	userid = " + SQLString.Format(v_user_id);

			ls = connMgr.executeQuery(sql);

			if ( ls.next()) {
				result = ls.getInt("usepoint");
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
		return result;
	}

	/**
	 * 나의 대기포인트
	 * @param box          receive from the form object and session
	 * @return int         대기포인트
	 * @throws Exception
	 */
	public int selectWaitPoint(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		int result = 0;

		String  v_user_id   = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();

			sql = "select  	nvl(sum(usepoint),0) usepoint 		 ";
			sql+= "from		tz_pointwait  ";
			sql+= "where	userid = " + SQLString.Format(v_user_id);

			ls = connMgr.executeQuery(sql);

			if ( ls.next()) {
				result = ls.getInt("usepoint");
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
		return result;
	}
}
