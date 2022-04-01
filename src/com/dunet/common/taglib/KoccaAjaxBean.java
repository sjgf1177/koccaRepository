//**********************************************************
//  1. 제      목: Ajax용
//  2. 프로그램명: KoccaAjaxBean
//  3. 개      요:
//  4. 환      경: JDK 1.5
//  5. 버      젼: 0.1
//  6. 작      성: swchoi, 2009.11.26
//  7. 수      정:
//**********************************************************
package com.dunet.common.taglib;

import java.sql.PreparedStatement;
import java.util.StringTokenizer;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class KoccaAjaxBean {

	public KoccaAjaxBean() {}

	private String getQuery(ConfigSet cs, String sqlNum) {
		String result = "";
		StringTokenizer st = new StringTokenizer(sqlNum, "|");
		while(st.hasMoreTokens()) {
			result += cs.getProperty("ajax.list." + st.nextToken());
		}
		return result;
	}
	public Object save(RequestBox box) {
		DBConnectionManager connMgr = null;
		boolean result = true;
		ListSet ls = null;

		try {
			ConfigSet cs = new ConfigSet();
			connMgr = new DBConnectionManager();
			if(box.getSession("userid").length()==0) throw new Exception("로그인이 필요합니다.");
			if(box.getBoolean("isArray")) {
				execArray(cs, connMgr, box);
				if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
				return result;
			}
			if (box.get("checkSqlNum").length()>0) {
				ls = connMgr.executeQuery(connMgr.replaceParam(getQuery(cs, box.get("checkSqlNum")), box));
				if(ls.next()) {
					if(ls.getInt(1) != 0) {
						if(box.get("elseSqlNum").length()>0){
							exec(cs, connMgr, box, "elseSqlNum");
							if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
							return result;
						}
						else {
							if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
							throw new Exception("이미 등록된 내용입니다.");
						}
					}
				}
			}
			else if (box.get("deleteSqlNum").length()>0) {
				exec(cs, connMgr, box, "deleteSqlNum");
			}

			exec(cs, connMgr, box, "sqlNum");
		}
		catch (Exception ex) {
			System.out.println(ex);
			return ex.getMessage();
		}
		finally {
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}
	private void exec(ConfigSet cs, DBConnectionManager connMgr, RequestBox box, String sqlNum) throws Exception{
		String sql = connMgr.replaceParam(getQuery(cs, box.get(sqlNum)), box);
		if(cs.getBoolean("sql.debugView")) {
			System.out.println("\n-------------------- KoccaAjaxBean SQL["+box.get(sqlNum)+"] Start -------------------- \n"+sql+"\n-------------------- KoccaAjaxBean SQL End -------------------- \n");
		}
		connMgr.executeUpdate(sql);
	}
	private void execArray(ConfigSet cs, DBConnectionManager connMgr, RequestBox box) throws Exception{
		connMgr.setAutoCommit(false);
		String[]keyList = box.getStringArray("keyList");
		String[]masterKey = box.getStringArray(keyList[0]);

		String sql = connMgr.replaceParam(getQuery(cs, box.get("sqlNum")), box);
		PreparedStatement pstmt = connMgr.prepareStatement(sql);
		try {
			int index = 1;
			for (int i = 0; i < masterKey.length; i++) {
				for (String key : keyList)
					pstmt.setString(index++, box.getStringArray(key)[i]);
				index = 1;
				pstmt.addBatch();
			}
			if(cs.getBoolean("sql.debugView")) {
				System.out.println("\n-------------------- KoccaAjaxBean SQL["+box.get("sqlNum")+"] Start -------------------- \n"+sql+"\n-------------------- KoccaAjaxBean SQL End -------------------- \n");
			}
			pstmt.executeBatch();
			connMgr.commit();
		} catch(Exception e) {
			connMgr.rollback();
			if(pstmt != null) pstmt.close();
			throw e;
		}
		connMgr.setAutoCommit(true);
	}
}
