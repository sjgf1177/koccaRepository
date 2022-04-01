//**********************************************************
//  1. 제      목: 운영본부 메뉴 OPERATION BEAN
//  2. 프로그램명: MenuBean.java
//  3. 개      요:
//  4. 환      경: JDK 1.4
//  5. 버      젼: 0.1
//  6. 작      성: S.W.Kang 2004. 12. 18
//  7. 수      정:
//**********************************************************
package com.credu.system;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

@SuppressWarnings("unchecked")
public class MenuBean {
	public MenuBean() {	}
	/** 권한별 사용메뉴리스트 Select
	@param box          receive from the form object and session
	@return ArrayList
	Menu는 2Level로 한정함을 원칙으로 함
	 */
	public ArrayList SelectList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list1 = null;
		ArrayList list2 = null;
		String sql  = "";
		MenuData data = null;
		String	 v_upper="";
		//		int		 v_rownum=0;

		String v_systemgubun = box.getString("p_systemgubun");
		System.out.println("v_systemgubun="+v_systemgubun);

		try {
			//sql 	= getSqlString(box);
			connMgr = new DBConnectionManager();
			list1 	= new ArrayList();

			sql += "SELECT  *                                                      \n";
			sql += "FROM    (                                                      \n";
			sql += "            select  (                                          \n";
			sql += "                        SELECT  D.ORDERS                       \n";
			sql += "                        FROM    TZ_MENU D                      \n";
			sql += "                        WHERE   D.GRCODE    = A.GRCODE         \n";
			sql += "                        AND     D.UPPER     = A.UPPER          \n";
			sql += "                        AND     D.PARENT    = A.PARENT         \n";
			sql += "                        AND 	D.UPPER = D.MENU               \n";
			sql += "                    ) PARENT_ORDERS                            \n";
			sql += "                    , A.MENU, A.MENUNM, PARA1, PARA2, PARA3    \n";
			sql += "                    , PARA4, PARA5, PARA6, PARA7, PARA8, PARA9 \n";
			sql += "                    , PARA10, PARA11, PARA12, LEVELS, UPPER    \n";
			sql += "                    , PARENT, PGM, ISDISPLAY, ORDERS           \n";
			sql += "            FROM    TZ_MENU A, TZ_MENUAUTH B, TZ_MENUSUB C     \n";
			sql += "            WHERE   A.GRCODE=B.GRCODE                          \n";
			sql += "            AND     A.MENU=B.MENU                              \n";
			sql += "            AND     A.GRCODE=C.GRCODE                          \n";
			sql += "            AND     A.MENU=C.MENU                              \n";
			sql += "            AND     B.MENUSUBSEQ=C.SEQ                         \n";
			sql += "            AND     C.SEQ=0                                    \n";
			sql += "   and b.gadmin='"+box.getSession("gadmin")+"'";
			//			sql += "            AND     B.GADMIN='A1'                              \n";
			sql += "            AND     B.CONTROL LIKe '%r%'                       \n";
			sql += "            AND     A.ISDISPLAY = 'Y'                          \n";
			sql += "        )                                                      \n";
			sql += "ORDER BY PARENT_ORDERS, PARENT, UPPER, LEVELS, ORDERS, MENU    \n";

			//sql  = "select a.menu menu,";
			//sql += "       a.menunm menunm,";
			//sql += "       para1,";
			//sql += "       para2,";
			//sql += "       para3,";
			//sql += "       para4,";
			//sql += "       para5,";
			//sql += "       para6,";
			//sql += "       para7,";
			//sql += "       para8,";
			//sql += "       para9,";
			//sql += "       para10,";
			//sql += "       para11,";
			//sql += "       para12,";
			//sql += "       levels,";
			//sql += "       upper,";
			//sql += "       parent,";
			//sql += "       pgm,";
			//sql += "       isdisplay";
			//sql += "  from TZ_MENU a, ";
			////sql += "       TZ_ADMINMENUAUTH b, ";
			//sql += "       TZ_MENUAUTH b, ";
			//sql += "       TZ_MENUSUB c ";
			///* 쿼리 수정 : 2005.11.02_하경태
			//	sql += " where "
			//	a.grcode=NVL('"+box.getString("s_grcode")+"','N000001') ";
			//	sql += "   and a.grcode=b.grcode and a.menu=b.menu ";
			// */
			//sql += " where a.grcode=b.grcode and a.menu=b.menu ";
			//sql += "   and a.grcode=c.grcode and a.menu=c.menu ";
			//sql += "   and b.menusubseq=c.seq and c.seq=0 ";
			////sql += "   and b.userid='"+box.getSession("userid")+"'";
			//sql += "   and b.gadmin='"+box.getSession("gadmin")+"'";
			//sql += "   and b.control like '%r%' and a.isdisplay = 'Y'  ";
			////if (v_systemgubun.equals("1") || v_systemgubun.equals("2")){
			////	sql += "   and a.systemgubun='"+v_systemgubun+"'";
			////}
			////sql += "   and a.systemgubun='1' ";	//tz_menu테이블에서 메인시스템과 4개시스템의 메뉴를 모두 관리하기때문에 구분을 두기 위함.
			//sql += " order by a.parent,a.upper,a.levels,a.orders,a.menu ";


			ls 		= connMgr.executeQuery(sql);

			while (ls.next()) {
				data=new MenuData();
				data.setMenu      (ls.getString("menu"));
				data.setMenunm    (ls.getString("menunm"));
				data.setPara1     (ls.getString("para1"));
				data.setPara2     (ls.getString("para2"));
				data.setPara3     (ls.getString("para3"));
				data.setPara4     (ls.getString("para4"));
				data.setPara5     (ls.getString("para5"));
				data.setPara6     (ls.getString("para6"));
				data.setPara7     (ls.getString("para7"));
				data.setPara8     (ls.getString("para8"));
				data.setPara9     (ls.getString("para9"));
				data.setPara10    (ls.getString("para10"));
				data.setPara11    (ls.getString("para11"));
				data.setPara12    (ls.getString("para12"));
				data.setLevels    (ls.getInt("levels"));
				data.setUpper     (ls.getString("upper"));
				data.setParent    (ls.getString("parent"));
				data.setPgm       (ls.getString("pgm"));
				data.setIsdisplay (ls.getString("isdisplay"));
				list1.add(data);
			}

			list2 = new ArrayList();
			for (int i=0; i<list1.size(); i++) {
				data = (MenuData)list1.get(i);
				// 대분류 코드
				if (!data.getUpper().equals(v_upper)){
					v_upper = data.getUpper();
					data.setRowspannum(getUpperCodeCnt(list1,data.getUpper()));
				} else {
					data.setRowspannum(1);
				}
				list2.add(data);
			}
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list2;
	}
	/**
	하위메뉴코드수 Count
	@param ArrayList   메뉴코드 리스트
	@param String      상위메뉴코드
	@return int   	   하위메뉴코드 갯수
	 */
	public int getUpperCodeCnt(ArrayList list1, String uppercode) {
		int ncnt = 0;
		MenuData data = null;

		for (int i=0; i<list1.size(); i++) {
			data = (MenuData)list1.get(i);

			if (data.getUpper().equals(uppercode)) {
				ncnt++;
			}
		}
		return ncnt;
	}

	/**
	Make SQL Query String
	@param box   		parameterBox
	@return String   	Sql String
	 */
	public String getSqlString(RequestBox box) {
		String sqlTxt = "";
		sqlTxt = "select a.menu menu,a.menunm menunm,para1,para2,para3,para4,para5,para6,para7,para8,para9,para10,para11,para12,levels,upper, "
			+ "       parent,pgm,isdisplay"
			+ "  from TZ_MENU a, TZ_ADMINMENUAUTH b, TZ_MENUSUB c "
			+ " where a.grcode = NVL('"+box.getString("s_grcode")+"','N000001') "
			+ "   and a.grcode = b.grcode and a.menu=b.menu "
			+ "   and a.grcode = c.grcode and a.menu=c.menu "
			+ "   and b.menusubseq = c.seq and c.seq=0 "
			+ "   and b.userid = '"+box.getSession("userid")+"'"
			+ "   and b.control like '%r%' and a.isdisplay = 'Y'  "
			//+ "   and a.systemgubun='"+box.getString("p_systemgubun")+"'"
			//+ "   and a.systemgubun='1' "	//tz_menu테이블에서 메인시스템과 4개시스템의 메뉴를 모두 관리하기때문에 구분을 두기 위함.
			+ " order by a.parent,a.upper,a.levels,a.orders,a.menu ";
		return sqlTxt;
	}

}


