//**********************************************************
//  1. 제      목: SELECT BOX
//  2. 프로그램명: SelectBoxBean.java
//  3. 개      요: SELECT BOX 구성
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2004. 1. 30
//  7. 수      정: 이정한 2004. 1. 30
//**********************************************************

package com.credu.common;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.SQLString;

/**
 * SELECT BOX 구성  Class
 *
 * @date   : 2003. 5
 * @author : J. S. J.
 */
@SuppressWarnings("unchecked")
public class SelectBoxBean {

	public SelectBoxBean(){}

	/**
	 *  셀렉트박스구성 (리스트, 셀렉트박스명,선택값,이벤트명)
	 */
	public static String getSelectBoxString (ArrayList list, String name, String selected, String event)  {
		StringBuffer result = new StringBuffer();

		result.append("  <SELECT name=\"");
		result.append(name);
		result.append("\" ");
		result.append(event);
		result.append(" > \n");

		for(int i = 0; i < list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);
			result.append("<option value=");
			result.append(dbox.getString("d_value"));
			if (selected.equals(dbox.getString("d_value"))) result.append(" selected ");
			result.append(">");
			result.append(dbox.getString("d_name"));
			result.append("</option> \n");
		}
		result.append("  </SELECT> \n");
		return result.toString();
	}
	/**
	 * 셀렉트박스가 나타나면 안되면서 해당 값을 가져올 때 사용(리스트, 셀렉트박스명,선택값,이벤트명)
	 * @param list
	 * @param name
	 * @param selected
	 * @param event
	 * @return
	 */
	public static String getSelectedString (ArrayList list, String name, String selected, String event)  {
		StringBuffer result = new StringBuffer();
		for(int i = 0; i < list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);
			if (selected.equals(dbox.getString("d_value"))) {
				result.append("  <b><input type='hidden' name=\"");
				result.append(name);
				result.append("\" ");
				result.append(" value=\"");
				result.append(dbox.getString("d_value"));
				result.append("\"/>");
				result.append(dbox.getString("d_name"));
				result.append("&nbsp;</b>");
			}
		}
		if (result.length() == 0) result.append("-");
		return result.toString();
	}

	public DataBox setAllSelectBox(ListSet ls) throws Exception {
		DataBox dbox = null;
		int columnCount = 0;
		try {
			dbox = new DataBox("selectbox");

			ResultSetMetaData meta = ls.getMetaData();
			columnCount = meta.getColumnCount();//System.out.println("columnCount : " + columnCount);
			for(int i = 1; i <= columnCount; i ++) {
				String columnName = meta.getColumnName(i).toLowerCase();//System.out.println("columnName : " + columnName);

				dbox.put("d_" + columnName, "ALL");
			}
		}
		catch (Exception ex) {ex.printStackTrace();
		throw new Exception("SelectBean.setAllDataBox()\r\n\"" + ex.getMessage());
		}
		return dbox;
	}


	/**
	 * 과정차시 SELECT
	 * @param box          receive from the form object and session
	 * @return result      SELECT 구성 문자
	 */
	public static String get_SelectSubjLesson(String name, String selected, String subj) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;
		String result = "";
		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();

			// 수정일 : 05.11.04 수정자 : 이나연 _ ||  수정
			//          sql  = " select lesson value,  lesson || ' ' ||sdesc name   ";
			sql  = " select lesson value,  lesson || ' ' ||sdesc name   ";
			sql += "   from tz_subjlesson              ";
			sql += "  where subj = " + SQLString.Format(subj);
			sql += "  order by lesson        ";

			ls = connMgr.executeQuery(sql);

			//            dbox = bean.setAllSelectBox(ls);
			//            list.add(dbox);

			while (ls.next()) {
				dbox = ls.getDataBox();
				list.add(dbox);
			}
			result =  getSelectBoxString (list, name, selected, "");
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex);
			throw new Exception(ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}


	/**
	 * 위탁교육기관 SELECT
	 * @param box          receive from the form object and session
	 * @return result      SELECT 구성 문자
	 */
	public static String get_SelectConsignCom(String name, String selected, String event) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;
		String result = "";
		SelectBoxBean bean = new SelectBoxBean();
		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();

			sql  = " select companyno value,  companyname name   ";
			sql += "   from tz_consigncom              ";
			//sql += "  where subj = " + SQLString.Format(subj);
			sql += "  order by companyno        ";

			ls = connMgr.executeQuery(sql);

			dbox = bean.setAllSelectBox(ls);
			list.add(dbox);

			while (ls.next()) {
				dbox = ls.getDataBox();
				list.add(dbox);
			}
			result =  getSelectBoxString (list, name, selected, event);
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex);
			throw new Exception(ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}

}
