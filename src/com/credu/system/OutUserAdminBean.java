//**********************************************************
//  1. 제      목: 업체 및 담당자 관련
//  2. 프로그램명 : OutUserAdminBean.java
//  3. 개      요: 업체 및 담당자  관련
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2006. 1. 11
//  7. 수      정:
//**********************************************************

package com.credu.system;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.SerialOneAdd;

@SuppressWarnings("unchecked")
public class OutUserAdminBean {
	/**
    회사명 콤보박스
    @param box			receive from the form object and session
    @param isChange		선택변경시 페이지리로드 여부(true,false)
    @param isALL		ALL 표시여부(true,false)
    @return String		회사콤보박스html문
	 */
	public static String getComp(RequestBox box, boolean isChange, boolean isALL) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		ListSet ls = null;
		String sql = "";
		String result = "";

		try {
			connMgr = new DBConnectionManager();

			sql = "select comp, compnm ";
			sql += " from TZ_COMPCLASS ";
			sql += " where isdeleted = 'N' ";
			sql += " order by compnm ";

			pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			ls = new ListSet(pstmt);

			result += getSelectTag(ls, isChange, false, "p_comp");
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, true);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}
	/**
    옵션만들어주는 메소드
    @param ls			콤보박스 데이터 리스트셋
    @param isChange		선택변경시 페이지리로드 여부(true,false)
    @param isALL		ALL 표시여부(true,false)
    @param selname		콤보박스명
    @return String		콤보박스html문
	 */
	public static String getSelectTag(ListSet ls, boolean isChange, boolean isALL, String selname) throws Exception {
		StringBuffer sb = null;

		try {
			sb = new StringBuffer();

			sb.append("<select name = \"" + selname + "\"");
			if(isChange) {
				sb.append(" onChange = \"javascript:whenSelection('change')\"");
			}
			sb.append(">\r\n");
			if(isALL) {
				sb.append("<option value = \"ALL\">ALL</option>\r\n");
			}
			else if(isChange) {
				if(selname.indexOf("year") == -1) {
					sb.append("<option value = \"----\">----</option>\r\n");
				}
			}

			while (ls.next()) {
				ResultSetMetaData meta = ls.getMetaData();
				int columnCount = meta.getColumnCount();

				sb.append("<option value = \"" + ls.getString(1) + "\"");

				//if (optionselected.equals(ls.getString(1))) sb.append(" selected");

				sb.append(">" + ls.getString(columnCount) + "</option>\r\n");
			}

			sb.append("</select>\r\n");
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, true);
			throw new Exception(ex.getMessage());
		}
		return sb.toString();
	}

	/**
    옵션만들어주는 메소드
    @param box			receive from the form object and session
    @param ls			콤보박스 데이터 리스트셋
    @param isChange		선택변경시 페이지리로드 여부(true,false)
    @param isALL		ALL 표시여부(true,false)
    @param selname		콤보박스명
    @param p_scnt		표시구분
    @return String		콤보박스html문
	 */
	public static String getSelectTag2(RequestBox box, ListSet ls, boolean isChange, boolean isALL, String selname, String p_scnt) throws Exception {
		StringBuffer sb = null;
		int cnt = 0;
		try {
			sb = new StringBuffer();

			if(selname == "0") {
				box.put("p_scnt","0");
			}

			while (ls.next()) {
				//				ResultSetMetaData meta = ls.getMetaData();

				if(cnt == 0 && p_scnt != "0" && selname != "0") {
					sb.append(",\r\n");
				}

				if(selname == "0") {
					box.put("p_scnt","1");
				}

				if(cnt > 0) {
					sb.append(",\r\n");
				}

				sb.append("new Style('" + ls.getString(1) + "', " + selname + ", \"" + ls.getString(2) + "\")");

				cnt++;

			}
			//System.out.println("ls.getRowNum() : " + ls.getRowNum());
			//if(ls.getRowNum() > 1){
			//현대자동차 소속으로 조회된 계열사 및 협력사가 있을경우
			//if(selname.equals("0")) sb.append(",\r\n");
			//}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, true);
			throw new Exception(ex.getMessage());
		}

		return sb.toString();
	}


	private ConfigSet config;


	private int row;


	public OutUserAdminBean() {
		try{
			config = new ConfigSet();
			row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}


	/**
    업체 삭제
    @param	box		receive from the form object and session
    @return	int		정상처리여부(1 : 정상, 2 : 오류)
	 */
	public int deleteoutcomp(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		String sql5 = "";
		int isOk1 = 1;
		int isOk2 = 1;
		int isOk3 = 1;
		int isOk4 = 1;
		int isOk5 = 1;

		String v_comp = box.getString("p_comp");
		String v_userid = box.getString("p_userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);////

			//회사정보 삭제
			//sql1 = "delete from \"TZ_COMPCLASS\" where comp = " + SQLString.Format(v_comp);
			sql1 = "update TZ_COMPCLASS set isdeleted = 'Y' where comp = " + SQLString.Format(v_comp);
			isOk1 = connMgr.executeUpdate(sql1);

			//회사정보 삭제
			//sql1 = "delete from \"TZ_COMP\" where comp = " + SQLString.Format(v_comp);
			sql2 = "update TZ_COMP set isused = 'N' where comp = " + SQLString.Format(v_comp);
			isOk2 = connMgr.executeUpdate(sql2);


			if(!v_userid.equals("")){
				//교육담당자 삭제
				sql3 = "delete from TZ_MEMBER where userid = " + SQLString.Format(v_userid);
				isOk3 = connMgr.executeUpdate(sql3);

				//운영자권한 삭제
				sql4 = "update TZ_MANAGER set isdeleted = 'Y' where userid = " + SQLString.Format(v_userid) + " and gadmin ='K2' and comp = " + SQLString.Format(v_comp);
				isOk4 = connMgr.executeUpdate(sql4);

				//운영자권한 삭제
				sql5 = "delete from TZ_COMPMAN where userid = " + SQLString.Format(v_userid) + " and gadmin ='T1' and comp = " + SQLString.Format(v_comp);
				isOk5 = connMgr.executeUpdate(sql5);
			}else{
				isOk3 =1;
				isOk4 =1;
				isOk5 =1;
			}

			if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0 && isOk4 > 0  && isOk5 > 0){
				connMgr.commit();
			}else{
				connMgr.rollback();
			}

		}catch (Exception ex) {
			if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk1*isOk2;
	}

	/**
    업체 및 담당자 등록
    @param box		receive from the form object and session
    @return int		정상등록여부(1 : 정상, 0 : 오류)
	 */
	public int insertoutcomp(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		String sql = "";
		String sql1 = "";
		//		String sql2 = "";
		int isOk = 1;
		int isOk1 = 1;
		int isOk2 = 1;


		String v_comp = "";
		String v_groups = "01";
		String s_userid = box.getSession("userid");

		box.put( box.getString("p_compnm"), box.getString("p_compnm"));
		box.put( box.getString("p_coname"), box.getString("p_coname"));
		box.put( box.getString("p_compresno"), box.getString("p_compresno"));
		box.put( box.getString("p_telno"), box.getString("p_telno"));
		box.put( box.getString("p_faxno"), box.getString("p_faxno"));
		box.put( box.getString("p_comp_addr1"), box.getString("p_comp_addr1"));
		box.put( box.getString("p_comp_addr2"), box.getString("p_comp_addr2"));
		box.put( box.getString("p_comp_post1"), box.getString("p_comp_post1"));
		box.put( box.getString("p_comp_post2"), box.getString("p_comp_post2"));
		box.put( box.getString("p_gubun"), box.getString("p_gubun"));
		box.put( box.getString("p_homepage"), box.getString("p_homepage"));


		String v_userid = box.getString("p_userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);////////

			//----------------------회사코드를 구함 ----------------------------

			sql = "select NVL(max(SUBSTR(comp,3,2)),'00') as comp from TZ_COMP ";
			ls = connMgr.executeQuery(sql);
			if (ls.next()) {
				v_comp = v_groups + SerialOneAdd.oneAddKey(ls.getString("comp")) + "000000";
			}
			box.put("v_comp", v_comp);
			box.put("v_company", v_comp.substring(2,3));
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			//------------------------------------------------------------------------------------

			// TZ_COMP
			sql1 = "insert into TZ_COMP (comp, comptype, groups, company, gpm, dept, part, companynm, compnm, isused, luserid, ldate)";
			sql1 += " values (':v_comp', '2', '01', ':v_company', '00','00','00', ':p_compnm', ':p_compnm', 'Y', ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))" ;

			pstmt1 = connMgr.prepareStatement(sql1, box);

			int i = 1;
			//			pstmt1.setString(i++, v_comp);
			//			pstmt1.setString(i++, v_groups);
			//			pstmt1.setString(i++, v_comp.substring(2,3));
			//			pstmt1.setString(i++, v_compnm);
			//			pstmt1.setString(i++, v_compnm);
			pstmt1.setString(i++, s_userid);
			isOk = pstmt1.executeUpdate();

			// TZ_COMPCLASS
			sql1 = "insert into TZ_COMPCLASS (comp, userid, compresno, compnm, coname, telno, faxno, addr,addr2, zip1,zip2, gubun, homepage, luserid, ldate,isdeleted) ";
			sql1 += " values (':v_comp', ?, ':p_compresno', ':p_compnm', ':p_coname', ':p_telno', ':p_faxno', ':p_comp_addr1', ':p_comp_addr2', ':p_comp_post1', ':p_comp_post1', ':p_gubun', ':p_homepage', ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),'N')" ;

			pstmt2 = connMgr.prepareStatement(sql1, box);

			i = 1;
			//			pstmt2.setString(i++, v_comp);
			pstmt2.setString(i++, v_userid);
			//			pstmt2.setString(i++, v_compresno);
			//			pstmt2.setString(i++, v_compnm);
			//			pstmt2.setString(i++, v_coname);
			//			pstmt2.setString(i++, v_telno);
			//			pstmt2.setString(i++, v_faxno);
			//			pstmt2.setString(i++, v_addr1);
			//			pstmt2.setString(i++, v_addr2);
			//			pstmt2.setString(i++, v_zip1);
			//			pstmt2.setString(i++, v_zip2);
			//			pstmt1.setString(i++, v_gubun);
			//			pstmt1.setString(i++, v_homepage);
			pstmt2.setString(i++, s_userid);

			isOk1 = pstmt2.executeUpdate();

			if (!v_userid.equals("")) {  //교육담당자 정보를 등록했을경우에 실행
				isOk2 = insertoutuser(connMgr, box, v_comp);	// 회원추가
			}

			if(isOk > 0 && isOk1 > 0 && isOk2 > 0){
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}else{
				if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			}

		}
		catch (Exception ex) {
			if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk*(isOk1);
	}



	/**
    교육담당자 등록
    @param box		receive from the form object and session
    @return int		정상등록여부(1 : 정상, 0 : 오류)
	 */
	public int insertoutuser(DBConnectionManager connMgr, RequestBox box,String v_comp) throws Exception {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		String sql = "";
		String sql1 = "";
		String sql2 = "";
		int isOk = 1;
		int isOk1 = 1;
		int isOk2 = 1;

		String s_userid = box.getSession("userid");

		//String v_gadmin = "T1";  // 베타데스터 관리자
		String v_gadmin = "K1";  // 회사담당자
		String v_userid = box.getString("p_userid");  //교육담당자 ID
		//		String v_name = box.getString("p_name");
		//		String v_pwd = box.getString("p_pwd");

		//		String v_comptext = box.getString("p_compnm");
		//		String v_resno = box.getString("p_resno");
		//		String v_email = box.getString("p_email");
		//		String v_hometel = box.getString("p_hometel");
		//		String v_telno = box.getString("p_telno");
		//		String v_handphone = box.getString("p_handphone");

		//		String v_addr1 = box.getString("p_home_addr1");
		//		String v_addr2 = box.getString("p_home_addr2");
		//		String v_post1 = box.getString("p_home_post1");
		//		String v_post2 = box.getString("p_home_post2");

		//		String v_compaddr1 = box.getString("p_comp_addr1");
		//		String v_compaddr2 = box.getString("p_comp_addr2");
		//		String v_zip1 = box.getString("p_comp_post1");
		//		String v_zip2 = box.getString("p_comp_post2");
		String v_tem_grcode = box.getSession("tem_grcode");
		String v_grtype		= "";

		try {

			if (v_tem_grcode.equals("N000001")) {
				v_grtype = "KOCCA";
			} else {
				v_grtype = "KGDI";
			}

			sql = "SELECT COUNT(gadmin) cnt FROM TZ_MANAGER WHERE userid = ':p_userid'";
			ListSet ls = connMgr.executeQuery(sql);

			int existCount = 0;
			if(ls.next()) {
				existCount = ls.getDataBox().getInt("cnt");
			}
			ls.close();
			if(existCount!=0) {
				sql = "DELETE TZ_MANAGER WHERE USERID = ':p_userid' AND gadmin = 'K1' and grtype = ?";
				pstmt1 = connMgr.prepareStatement(sql1);
				pstmt1.setString(1, v_grtype);
				pstmt1.executeUpdate();
			}

			sql = "SELECT COUNT(gadmin) cnt FROM TZ_COMPMAN WHERE userid = ':p_userid' and comp = " + SQLString.Format(v_comp) ;
			ls = connMgr.executeQuery(sql);

			existCount = 0;
			if(ls.next()) {
				existCount = ls.getDataBox().getInt("cnt");
			}
			ls.close();
			if(existCount!=0) {
				sql = "DELETE TZ_COMPMAN WHERE userid = ':p_userid' and comp = " + SQLString.Format(v_comp) ;
				pstmt1 = connMgr.prepareStatement(sql1);
				pstmt1.executeUpdate();
			}

			if ( !v_userid.equals("")) {  //교육담당자 정보를 등록했을경우에 실행
				// TZ_MEMBER INSERT
				//				sql  = "insert into TZ_MEMBER (comp, userid, resno, name, pwd, email, hometel, comptel, handphone, ";
				//				sql += "                       post1, post2, addr, addr2, comp_post1, comp_post2, comp_addr1, comp_addr2, ";
				//				sql += "                       comptext, membergubun, state, validation, registgubun, lgfirst, indate, ldate) ";
				//				sql += "               values (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, 'C','Y', 1, 'ALL', '' ";
				//				sql += "                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'), to_char(sysdate, 'YYYYMMDDHH24MISS'))";
				//				pstmt = connMgr.prepareStatement(sql);
				//				pstmt.setString(1,  v_comp);
				//				pstmt.setString(2,  v_userid);
				//				pstmt.setString(3,  v_resno);
				//				pstmt.setString(4,  v_name);
				//				pstmt.setString(5,  v_pwd);
				//				pstmt.setString(6,  v_email);
				//				pstmt.setString(7,  v_hometel);
				//				pstmt.setString(8,  v_telno);
				//				pstmt.setString(9,  v_handphone);
				//				pstmt.setString(10,  v_post1);
				//				pstmt.setString(11, v_post2);
				//				pstmt.setString(12, v_addr1);
				//				pstmt.setString(13, v_addr2);
				//				pstmt.setString(14, v_zip1);
				//				pstmt.setString(15, v_zip2);
				//				pstmt.setString(16, v_compaddr1);
				//				pstmt.setString(17, v_compaddr2);
				//				pstmt.setString(18, v_comptext);
				//				isOk = pstmt.executeUpdate();

				// TZ_MANAGER INSERT
				// 중복 데이터 검색
				sql = "";
				int tzMngCnt = 0;

				sql = "SELECT COUNT(*) cnt FROM TZ_MANAGER WHERE userid = " + SQLString.Format(v_userid) + " AND gadmin = " + SQLString.Format(v_gadmin);
				
				ListSet lsMng = connMgr.executeQuery(sql);

				if(lsMng.next()) {
					tzMngCnt = lsMng.getDataBox().getInt("d_cnt");
				}
				
				lsMng.close();				

				if(tzMngCnt == 0){
					sql1 = "insert into TZ_MANAGER (userid, gadmin, grtype, fmon, tmon, luserid, ldate, isdeleted) ";
					sql1 += " values (?, ?, ?, to_char(sysdate, 'YYYYMMDD'), '20301231', ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),'N')";

					pstmt1 = connMgr.prepareStatement(sql1, box);
	
					pstmt1.setString(1, v_userid);
					pstmt1.setString(2, v_gadmin);
					pstmt1.setString(3, v_grtype);
					pstmt1.setString(4, s_userid);

					isOk1 = pstmt1.executeUpdate();			
				}
				
				// TZ_COMPMAN INSERT
				sql2 = "insert into TZ_COMPMAN (userid, gadmin, comp, luserid, ldate) ";
				sql2 += " values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

				pstmt2 = connMgr.prepareStatement(sql2);
				pstmt2.setString(1, v_userid);
				pstmt2.setString(2, v_gadmin);
				pstmt2.setString(3, v_comp);
				pstmt2.setString(4, s_userid);

				isOk2 = pstmt2.executeUpdate();
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
		}
		return isOk*isOk1*isOk2;
	}


	/**
    담당자 등록
    @param box		receive from the form object and session
    @return int		정상등록여부(1 : 정상, 0 : 오류)
	 */
	public int insertoutuser(RequestBox box,String v_comp) throws Exception {
		DBConnectionManager connMgr = null;
		int isOk = 1;

		try {
			connMgr = new DBConnectionManager();

			isOk = insertoutuser(connMgr,box, v_comp);
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex);
			throw new Exception(ex.getMessage());
		}
		finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}




	/**
    업체 정보 페이지
    @param box          receive from the form object and session
    @return DataBox	업체 정보
	 */
	public DataBox select2outcomp(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		DataBox dbox = null;
		ListSet ls = null;
		String sql = "";

		String v_comp = box.getString("p_comp");

		try {
			connMgr = new DBConnectionManager();
			sql  = " select a.comp, a.compnm, a.compresno, a.coname, a.telno, a.faxno,\n";
			sql += "        a.addr compaddr1, a.addr2 compaddr2, a.zip1, a.zip2, a.gubun, a.homepage,\n";
			sql += "        b.userid, b.name, b.resno, b.pwd, b.hometel, b.handphone, b.comptel,\n";
			sql += "        b.email, b.post1, b.post2, b.addr, b.addr2, b.comptext\n";
			sql += "   from TZ_COMPCLASS a, TZ_MEMBER b\n";
			sql += "  where a.userid = b.userid(+)\n";
			sql += "    and a.comp = " + SQLString.Format(v_comp) ;
			sql += "\n    and a.isdeleted = 'N' ";

			ls = connMgr.executeQuery(sql);
			if(ls.next()){
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
    업체 리스트
    @param box          receive from the form object and session
    @return ArrayList	업체 리스트
	 */
	public ArrayList selectoutcomp(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ArrayList list = null;
		DataBox dbox = null;
		ListSet ls = null;

		String sql     = "";
		String count_sql  = "";
		String head_sql   = "";
		String body_sql   = "";
		String group_sql  = "";
		String order_sql  = "";

		int		v_pageno = box.getInt("p_pageno");
		String	v_searchtext = box.getString("p_searchtext");

		String	v_orderColumn	= box.getString("p_orderColumn");           	//정렬할 컬럼명
		String	v_orderType     = box.getString("p_orderType");           		//정렬할 순서

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
			head_sql  = " select a.comp, a.compnm, a.compresno, a.coname, a.telno, a.faxno,\n";
			head_sql += "        a.addr compaddr1, a.addr2 compaddr2, a.zip1, a.zip2, a.gubun, a.homepage,a.ldate,\n";
			head_sql += "        b.userid, b.name, b.resno, b.pwd, b.hometel, b.handphone, b.comptel,\n";
			head_sql += "        b.email, b.post1, b.post2, b.addr, b.addr2, b.comptext\n";
			body_sql += "   from TZ_COMPCLASS a, TZ_MEMBER b\n";
			body_sql += "  where a.userid = b.userid(+)\n";
			body_sql +="  and a.isdeleted = 'N' \n";
			if(!v_searchtext.equals("")) {
				body_sql += " and upper(a.compnm) like upper('%" + v_searchtext +"%')\n";
			}
			if(v_orderColumn.equals("")) {
				order_sql+= " order by a.comp";
			} else {
				order_sql+= " order by " + v_orderColumn + v_orderType;
			}
			sql= head_sql+ body_sql+ group_sql+ order_sql;
			ls = connMgr.executeQuery(sql);
			//System.out.println("outUser sql >>>>>> "+sql);

			count_sql= "select count(*) "+ body_sql ;
			int totalrowcount= BoardPaging.getTotalRow(connMgr, count_sql);

			ls.setPageSize(row);            				 //  페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, totalrowcount);      //     현재페이지번호를 세팅한다.
			int total_row_count = ls.getTotalCount();        //     전체 row 수를 반환한다

			int v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");
			ls.setPageSize(v_pagesize);             		//  페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, total_row_count);   //     현재페이지번호를 세팅한다.

			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
				dbox.put("d_rowcount", new Integer(v_pagesize));
				dbox.put("d_totalrowcount",	new Integer(total_row_count));
				list.add(dbox);
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
		return list;
	}



	/**
    업체 수정
    @param	box		receive from the form object and session
    @return	int		정상처리여부(1 : 정상, 2 : 오류)
	 */
	public int updateoutcomp(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		String sql1 = "";
		//		String sql2 = "";

		int isOk1 = 1;
		int isOk2 = 1;

		//		box.put( box.getString("p_compnm"), box.getString("p_compnm"));
		//		box.put( box.getString("p_coname"), box.getString("p_coname"));
		//		box.put( box.getString("p_compresno"), box.getString("p_compresno"));
		//		box.put( box.getString("p_telno"), box.getString("p_telno"));
		//		box.put( box.getString("p_faxno"), box.getString("p_faxno"));
		//		box.put( box.getString("p_comp_addr1"), box.getString("p_comp_addr1"));
		//		box.put( box.getString("p_comp_addr2"), box.getString("p_comp_addr2"));
		//		box.put( box.getString("p_comp_post1"), box.getString("p_comp_post1"));
		//		box.put( box.getString("p_comp_post2"), box.getString("p_comp_post2"));
		//		box.put( box.getString("p_gubun"), box.getString("p_gubun"));
		//		box.put( box.getString("p_homepage"), box.getString("p_homepage"));
		String v_comp = box.getString("p_comp");
		String v_coname = box.getString("p_coname");
		String v_compnm = box.getString("p_compnm");
		String v_compresno = box.getString("p_compresno");
		String v_telno = box.getString("p_telno");
		String v_faxno = box.getString("p_faxno");

		String v_compaddr1 = box.getString("p_comp_addr1");
		String v_compaddr2 = box.getString("p_comp_addr2");
		String v_zip1 = box.getString("p_comp_post1");
		String v_zip2 = box.getString("p_comp_post2");

		String v_userid = box.getString("p_userid");  //교육담당자 ID
		//		String v_name = box.getString("p_name");
		//		String v_pwd = box.getString("p_pwd");

		//		String v_comptext = box.getString("p_compnm");
		//		String v_resno = box.getString("p_resno");
		//		String v_email = box.getString("p_email");

		//		String v_hometel = box.getString("p_hometel");
		//		String v_handphone = box.getString("p_handphone");

		//		String v_addr1 = box.getString("p_home_addr1");
		//		String v_addr2 = box.getString("p_home_addr2");
		//		String v_post1 = box.getString("p_home_post1");
		//		String v_post2 = box.getString("p_home_post2");

		String v_gubun = box.getString("p_gubun");
		String v_homepage = box.getString("p_homepage");
		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);////

			sql1 = "update TZ_COMPCLASS   ";
			sql1+= "   set userid = ?,    ";
			sql1+= "       compnm = ?,    ";
			sql1+= "       compresno = ?, ";
			sql1+= "       coname = ?,    ";
			sql1+= "       telno = ?,     ";
			sql1+= "       faxno = ?,     ";
			sql1+= "       addr  = ?,     ";
			sql1+= "       addr2 = ?,     ";
			sql1+= "       zip1  = ?,     ";
			sql1+= "       zip2  = ?,      ";
			sql1+= "       gubun  = ?,      ";
			sql1+= "       homepage  = ?      ";
			sql1 += " where comp = " + SQLString.Format(v_comp);
			pstmt1 = connMgr.prepareStatement(sql1);

			pstmt1.setString(1, v_userid);
			pstmt1.setString(2, v_compnm);
			pstmt1.setString(3, v_compresno);
			pstmt1.setString(4, v_coname);
			pstmt1.setString(5, v_telno);
			pstmt1.setString(6, v_faxno);
			pstmt1.setString(7, v_compaddr1);
			pstmt1.setString(8, v_compaddr2);
			pstmt1.setString(9, v_zip1);
			pstmt1.setString(10, v_zip2);
			pstmt1.setString(11, v_gubun);
			pstmt1.setString(12, v_homepage);
			isOk1 = pstmt1.executeUpdate();

			if (!v_userid.equals("")) {  //교육담당자 정보를 수정했을경우에 실행
				isOk2 = insertoutuser(box, v_comp);	// 회원추가
			}

			if(isOk1 > 0 && isOk2 > 0){
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}else{
				if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			}
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
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk1*isOk2;
	}

	/**
    등록된 유저아이디인지 체크한다.
    @param box		receive from the form object and session
    @return DataBox	카운트
	 */
	public DataBox userCheck(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		DataBox dbox = null;

		String v_userid = box.getString("p_userid");

		try {
			connMgr = new DBConnectionManager();

			sql = "select count(userid) as cnt from tz_member where userid = " + SQLString.Format(v_userid);

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

	public List userList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		List result = null;
		DataBox dbox = null;

		try {
			connMgr = new DBConnectionManager();

			sql = connMgr.replaceParam("SELECT /*	OutUserAdminBean.java:821	*/\nMEMBERGUBUN, USERID, NAME, COMPTEXT, HOMETEL, HANDPHONE, RESNO, POST1, POST2, EMAIL, ADDR, ADDR2,PWD\n	FROM TZ_MEMBER \nWHERE :p_param2 is not null and DECODE(:p_param1, 'NM', NAME, 'ID', USERID) like '%'||:p_param2||'%'", box);

			ls = connMgr.executeQuery(sql);
			int total_row_count = BoardPaging.getTotalRow(connMgr, sql, true); //     전체 row 수를 반환한다
			int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box
					.getInt("p_pageno");
			int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box
					.getInt("p_pagesize");
			result = new ArrayList();
			ls.setPageSize(v_pagesize); //  페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.

			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum", new Integer(total_row_count
						- ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
				dbox.put("d_rowcount", new Integer(v_pagesize));
				dbox.put("d_totalrowcount", new Integer(total_row_count));
				result.add(dbox);
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