//**********************************************************
//  1. 제      목: 과정분류코드 OPERATION BEAN
//  2. 프로그램명: ClassifySubjectBean.java
//  3. 개      요:
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: anonymous 2003. 6. 30
//  7. 수      정:
//**********************************************************
package com.credu.off;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.credu.course.ClassifySubjectData;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

public class OffClassifySubjectBean {

	private static ArrayList<DataBox>       upperCodeList;
	private static HashMap<String, String>  upperMenuMap;

	public OffClassifySubjectBean() {
		if(upperCodeList == null) upperCodeList = new ArrayList<DataBox>();
		if(upperMenuMap == null) upperMenuMap = new HashMap<String, String>();
	}

	public void resetCodeCollection(){
		upperCodeList = null;
		upperMenuMap  = null;
	}

	/**
    과정분류코드 조회
    @param box          receive from the form object and session
    @return ArrayList   과정분류코드 리스트
	 */
	@SuppressWarnings("unchecked")
	public ArrayList SelectList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list1 = null;
		ArrayList list2 = null;
		String sql  = "";
		ClassifySubjectData data = null;

		try {
			sql = "select a.subjclass, a.upperclass, a.middleclass, a.lowerclass,  b.upperclassname, c.middleclassname, a.classname ";
			sql+= "  from tz_offsubjatt a,  ";
			sql+= "  (select upperclass, classname  upperclassname  ";
			sql+= "     from tz_offsubjatt  ";
			sql+= "    where middleclass = '000'  ";
			sql+= "      and lowerclass  = '000') b,  ";
			sql+= "  (select upperclass, middleclass, classname  middleclassname  ";
			sql+= "     from tz_offsubjatt  ";
			sql+= "    where middleclass != '000'  ";
			sql+= "      and lowerclass  = '000') c  ";
			sql+= " where a.upperclass = b.upperclass  ";
			// 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
			//			sql+= "   and a.upperclass = c.upperclass(+)  ";
			//			sql+= "   and a.middleclass = c.middleclass(+)  ";
			sql+= "   and a.upperclass  =  c.upperclass(+)  ";
			sql+= "   and a.middleclass  =  c.middleclass(+)  ";
			sql+= " order by a.subjclass, b.upperclassname  ";

			//System.out.println("sql=>" + sql);
			connMgr = new DBConnectionManager();
			list1 = new ArrayList();
			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				data=new ClassifySubjectData();
				data.setSubjclass  (ls.getString("subjclass"));
				data.setUpperclass (ls.getString("upperclass"));
				data.setMiddleclass(ls.getString("middleclass"));
				data.setLowerclass (ls.getString("lowerclass"));
				data.setClassname  (ls.getString("classname"));
				data.setUpperclassname(ls.getString("upperclassname"));
				data.setMiddleclassname(ls.getString("middleclassname"));
				data.setLowerclassname(ls.getString("classname"));

				list1.add(data);
			}

			list2 = new ArrayList();
			for (int i=0; i<list1.size(); i++) {
				data = (ClassifySubjectData)list1.get(i);
				if (data.isUpperclass()) {
					if (getUpperCodeCnt(list1, data.getUpperclass()) < 2) {
						list2.add(data);
					}
				} else if (data.isMiddleclass()) {
					if (getMiddleCodeCnt(list1, data.getUpperclass(), data.getMiddleclass()) < 2) {
						list2.add(data);
					}
				} else if (data.isLowerclass()) {
					list2.add(data);
				}
			}

			String upperclass  = "";
			String upperclass2 = "";
			String middleclass = "";
			for (int i=0; i<list2.size(); i++) {
				data = (ClassifySubjectData)list2.get(i);
				if (upperclass.equals(data.getUpperclass())) {
					data.setUpperrowspannum(0);
				} else {
					data.setUpperrowspannum(getUpperCodeCnt(list2, data.getUpperclass()));
					upperclass = data.getUpperclass();
				}

				if (upperclass2.equals(data.getUpperclass()) && middleclass.equals(data.getMiddleclass())) {
					data.setMiddlerowspannum(0);
				} else {
					data.setMiddlerowspannum(getMiddleCodeCnt(list2, data.getUpperclass(), data.getMiddleclass()));
					upperclass2 = data.getUpperclass();
					middleclass = data.getMiddleclass();
				}


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
		return list2;
	}

	/**
	대분류코드 갯수 조회
	@param ArrayList   과정분류코드 리스트
	@param String      대분류코드
	@return int   		대분류코드 갯수
	 */
	@SuppressWarnings("unchecked")
	public int getUpperCodeCnt(ArrayList list1, String uppercode) {
		int ncnt = 0;
		ClassifySubjectData data = null;

		for (int i=0; i<list1.size(); i++) {
			data = (ClassifySubjectData)list1.get(i);

			if (data.getUpperclass().equals(uppercode)) {
				ncnt++;
			}
		}
		return ncnt;
	}

	/**
	중분류코드 갯수 조회
	@param ArrayList   과정분류코드 리스트
	@param String      대분류코드
	@param String      중분류코드
	@return int   		중분류코드 갯수
	 */
	@SuppressWarnings("unchecked")
	public int getMiddleCodeCnt(ArrayList list1, String uppercode, String middlecode) {
		int ncnt = 0;
		ClassifySubjectData data = null;

		for (int i=0; i<list1.size(); i++) {
			data = (ClassifySubjectData)list1.get(i);

			if (data.getUpperclass().equals(uppercode) && data.getMiddleclass().equals(middlecode)) {
				ncnt++;
			}
		}
		return ncnt;
	}
	private boolean checkExistCode(DBConnectionManager connMgr, String v_subjclass) throws Exception {
		ListSet ls = null;
		String sql  = "";
		boolean result = false;

		try {
			sql+= "select count(SUBJCLASS) CNT";
			sql+= "     from tz_offsubjatt  ";
			sql+= "    where SUBJCLASS = '"+v_subjclass+"'";

			connMgr = new DBConnectionManager();

			ls = connMgr.executeQuery(sql);
			if(ls.next() && ls.getDataBox().getInt("d_cnt") > 0)  result = true;
		}
		catch (Exception ex) {
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}

	/**
    새로운 과정분류코드 등록
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
	 */
	public int InsertSubjectClassification(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		String sql = "";
		int isOk = 0;

		String v_upperclass  = "";
		String v_middleclass = "";
		String v_lowerclass  = "";
		String v_classtype   = box.getString("p_classtype");
		String v_classcode   = box.getString("p_classcode");

		if (v_classtype.equals("upper")) {
			v_upperclass  = v_classcode;
			v_middleclass = "000";
			v_lowerclass  = "000";
		} else if (v_classtype.equals("middle")) {
			v_upperclass  = box.getString("p_upperclass");
			v_middleclass = v_classcode;
			v_lowerclass  = "000";
		} else if (v_classtype.equals("lower")) {
			v_upperclass  = box.getString("p_upperclass");
			v_middleclass = box.getString("p_middleclass");
			v_lowerclass  = v_classcode;
		}

		String v_classname   = box.getString("p_classname");
		String v_subjclass   = v_upperclass+v_middleclass+v_lowerclass;
		String v_luserid     = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			if(checkExistCode(connMgr, v_subjclass)) {
				if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
				if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
				return -1;//중복시 -1 반환
			}
			//insert tz_offsubjatt table
			sql =  "insert into tz_offsubjatt(subjclass,upperclass,middleclass,lowerclass,classname,luserid,ldate) ";
			sql+=  " values (?, ?, ?, ?, ?, ?, to_char(sysdate,'YYYYMMDDHH24MISS'))";

			pstmt = connMgr.prepareStatement(sql);
			pstmt.setString(1, v_subjclass);
			pstmt.setString(2, v_upperclass);
			pstmt.setString(3, v_middleclass);
			pstmt.setString(4, v_lowerclass);
			pstmt.setString(5, v_classname);
			pstmt.setString(6, v_luserid);
			isOk = pstmt.executeUpdate();

			this.resetCodeCollection(); //static 초기화
		}
		catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}

	/**
    선택된 과정분류코드 수정
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail
	 */
	public int UpdateSubjectClassification(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		String sql = "";
		int isOk = 0;


		String v_subjclass  = box.getString("p_upperclass") + box.getString("p_middleclass") + box.getString("p_lowerclass");
		String v_classname  = box.getString("p_classname");
		String v_luserid    = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();


			//update tz_offsubjatt table
			sql = "update tz_offsubjatt set classname = ?, luserid = ?, ldate = to_char(sysdate,'YYYYMMDDHH24MISS') ";
			sql+= " where subjclass = ? ";

			pstmt = connMgr.prepareStatement(sql);
			pstmt.setString(1, v_classname);
			pstmt.setString(2, v_luserid);
			pstmt.setString(3, v_subjclass);
			isOk = pstmt.executeUpdate();

			this.resetCodeCollection();  //static 초기화

		}
		catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}

	/**
    선택된 과정분류코드 삭제
    @param box      receive from the form object and session
    @return isOk    1:delete success,0:delete fail
	 */
	public int DeleteSubjectClassification(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		String sql = "";
		int isOk = 1;
		ListSet ls = null;

		String v_upperclass = box.getString("p_upperclass");
		String v_middleclass= box.getString("p_middleclass");
		String v_subjclass  = box.getString("p_upperclass") + box.getString("p_middleclass") + box.getString("p_lowerclass");

		try {
			connMgr = new DBConnectionManager();

			//대분류인 경우 중분류 확인
			if (v_middleclass.equals("000")) {
				sql = "select upperclass from tz_offsubjatt where upperclass = '" + v_upperclass + "' and middleclass!='000'";
				ls = connMgr.executeQuery(sql);

				if (ls.next()) {
					//중분류가 있어 삭제할 수 없음
					isOk = -1;
				}
			} else {

				if(ls != null) { try { ls.close(); }catch (Exception e) {} }

				//중분류인 경우 과정을 확인
				sql = "select subjclass from tz_offsubj where subjclass = '" + v_subjclass + "'";
				ls = connMgr.executeQuery(sql);

				if (ls.next()) {
					//중분류가 있어 삭제할 수 없음
					isOk = -2;
				}

			}

			System.out.println("결과 = " + isOk);
			if (isOk == 1) {
				//delete tz_offsubjatt table
				sql = "delete from tz_offsubjatt where subjclass = ? ";

				pstmt = connMgr.prepareStatement(sql);

				pstmt.setString(1, v_subjclass);
				isOk = pstmt.executeUpdate();
			}

			this.resetCodeCollection(); //static 초기화
		}
		catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}

	/**
	    과정분류코드 조회
	@param box          receive from the form object and session
	@return ArrayList   과정분류코드 리스트
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<DataBox> SelectMiddleClassList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		
		ListSet ls 					= null;
		ArrayList list 				= null;
		
		StringBuffer sql  			= new StringBuffer();
		
		DataBox dbox 				= null;

		try {
			
			sql.append(" SELECT UPPERCLASS, MIDDLECLASS, CLASSNAME  MIDDLECLASSNAME \n");
			sql.append("      FROM TZ_OFFSUBJATT \n");
			sql.append("     WHERE MIDDLECLASS != '000' \n");
			sql.append("       AND LOWERCLASS  = '000' \n");
			sql.append("  ORDER BY UPPERCLASS, MIDDLECLASS ");

			connMgr = new DBConnectionManager();

			ls = connMgr.executeQuery( sql.toString() );

			list = new ArrayList();

			while (ls.next()) {
				dbox = ls.getDataBox();
				list.add(dbox);
			}
			
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
		} finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		
		return list;
	}
	/**
	오프라인과정 사용자 페이지 MenuId 정보
	@param box          receive from the form object and session
	@return HashMap<String, String>   온라인과정 사용자 페이지 MenuId 정보
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> getMenuId(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		List list = new ArrayList();
		String sql  = "";
		DataBox dbox = null;
		HashMap<String, String> map = null;

		if(upperMenuMap!=null) {
			return upperMenuMap;
		}

		String v_grcode = box.getSession("tem_grcode");
		try {
			sql+= "select a.upperclass, a.classname, decode(b.menuid, null, '00', b.menuid) menuid  ";
			sql+= "     from tz_subjatt a, tz_homemenu b  ";
			sql+= "    where middleclass = '000'  ";
			sql+= "      and lowerclass  = '000'  ";
			sql+= "      AND a.UPPERCLASS = SUBSTRING(b.MENUURL(+), LENGTH(b.MENUURL(+))-2 , 3)";
			sql+= "      AND b.GRCODE(+)  = "+StringManager.makeSQL(v_grcode);
			sql+= "      AND b.GUBUN(+) = 2";
			sql+= " order by upperclass, middleclass ";

			connMgr = new DBConnectionManager();

			ls = connMgr.executeQuery(sql);

			map = new HashMap<String, String>();

			while (ls.next()) {
				dbox = ls.getDataBox();
				map.put(dbox.getString("d_upperclass"), dbox.getString("d_menuid"));
			}
			list.add(map);

			upperMenuMap  = map;
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return map;
	}

	/**
	오프라인과정 대분류 리스트
	@param box          receive from the form object and session
	@return ArrayList   과정분류코드 리스트
	 */
	public static ArrayList<DataBox> getUpperClassList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> codeList = null;
		String sql  = "";
		DataBox dbox = null;

		if(upperCodeList != null) {
			return upperCodeList;
		}

		try {
			sql+= "select a.upperclass, a.classname ";
			sql+= "     from tz_offsubjatt a ";
			sql+= "    where middleclass = '000'  ";
			sql+= "      and lowerclass  = '000'  ";
			sql+= " order by upperclass, middleclass ";

			connMgr = new DBConnectionManager();

			ls = connMgr.executeQuery(sql);

			codeList = new ArrayList<DataBox>();

			while (ls.next()) {
				dbox = ls.getDataBox();
				codeList.add(dbox);
			}

			upperCodeList = codeList;
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return codeList;
	}
}
