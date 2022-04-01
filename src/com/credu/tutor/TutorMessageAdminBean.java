//**********************************************************
//1. 제      목: 교수 메세지
//2. 프로그램명: TutorMessageAdminBean.java
//3. 개      요: 교수 메세지
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성: 정상진 2005. 12. 20
//7. 수      정:
//**********************************************************

package com.credu.tutor;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.dunet.common.util.StringUtil;
import com.namo.active.NamoMime;

public class TutorMessageAdminBean {
	
	private	ConfigSet config;
	private	int	row;

	public TutorMessageAdminBean() {
		try{
			config = new ConfigSet();
			row	= Integer.parseInt(config.getProperty("page.bulletin.row"));		//		  이 모듈의	페이지당 row 수를 셋팅한다
		}
		catch(Exception	e) {
			e.printStackTrace();
		}
	}

	/**
	 * 새로운 메세지 내용 등록
	 * @param box        receive from the form object and session
	 * @return isOk      1:insert success, 0:insert fail
	 * @throws Exception
	 */
	public int insertMessage(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt1 = null;
		ListSet ls = null;		
		String sql = "";
		String sql1 = "";
		int isOk1 = 0;

		String v_title   = StringUtil.removeTag(box.getString("p_title"));
		String v_content = StringUtil.removeTag(box.getString("p_content"));
		String v_subj = box.getString("p_subjcourse");
		String s_userid = box.getSession("userid");
		int v_seq  = 0;
		
		try {
			connMgr = new DBConnectionManager();

			// ---------------------- 최대값 가져온다 ----------------------------
			sql = "select NVL(max(seq),	0) from TZ_TUTORMESSAGE";
			ls = connMgr.executeQuery(sql);
			if (ls.next()) {
			   v_seq = ls.getInt(1) + 1;
			}
			// ------------------------------------------------------------------------------------

			   /*********************************************************************************************/
			   // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다. 
			   try {
				   v_content =(String) NamoMime.setNamoContent(v_content);
			   } catch(Exception e) {
				   System.out.println(e.toString());
				   return 0;
			   }
			   /*********************************************************************************************/

			// //////////////////////////////// 게시판 table 에 입력
			sql1 = "insert	into TZ_TUTORMESSAGE(SEQ, SUBJ, USERID, TITLE, CONTENT, INDATE, CNT, LDATE)               ";
			sql1 += " values (?, ?, ?, ?, ?, to_char(sysdate,	'YYYYMMDDHH24MISS'), ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

			pstmt1 = connMgr.prepareStatement(sql1);

			pstmt1.setInt(1, v_seq);
			pstmt1.setString(2, v_subj);
			pstmt1.setString(3, s_userid);
			pstmt1.setString(4, v_title);
			pstmt1.setString(5, v_content);
			pstmt1.setInt(6, 0);

			isOk1 = pstmt1.executeUpdate();
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		} finally {
			if(ls != null) {try	{ls.close();} catch(Exception e){}}
			if (pstmt1 != null) { try { pstmt1.close();} catch (Exception e1) {}}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1;
	}
	
	/**
	* 선택된 자료 상세내용 수정
	* @param box      receive from the form object and session
	* @return isOk    1:update success,0:update fail
	* @throws Exception
	*/  	
	 public	int	updateMessage(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		PreparedStatement pstmt1 = null;
		String sql1	= "";
		int	isOk1 =	1;

		int	v_seq =	box.getInt("p_seq");
		String v_title = StringUtil.removeTag(box.getString("p_title"));
		String v_content = StringUtil.removeTag(box.getString("p_content"));
		String s_userid	= box.getSession("userid");
		
		try	{
			connMgr	= new DBConnectionManager();
			
			   /*********************************************************************************************/
			   // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다. 
			   try {
				   v_content =(String) NamoMime.setNamoContent(v_content);
			   } catch(Exception e) {
				   System.out.println(e.toString());
				   return 0;
			   }
			   /*********************************************************************************************/

			sql1  = " update TZ_TUTORMESSAGE set title = ?, content = ?, userid = ?, indate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
			sql1 +=	"  where seq = ? ";

			pstmt1 = connMgr.prepareStatement(sql1);
			pstmt1.setString(1,	v_title);
			pstmt1.setString(2,	v_content);
			pstmt1.setString(3,	s_userid);			
			pstmt1.setInt(4, v_seq);

			isOk1 =	pstmt1.executeUpdate();

		}
		catch(Exception	ex)	{
			ErrorManager.getErrorStackTrace(ex,	box, sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {}	}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1;
	}
	
	/**
	* 선택된 게시물	삭제
	* @param box      receive from the form object and session
	* @return isOk    1:delete success,0:delete fail
	* @throws Exception
	*/    	
	public int deleteMessage(RequestBox	box) throws	Exception {
		DBConnectionManager	connMgr	= null;
		PreparedStatement pstmt1 = null;
		String sql1	= "";
		// String sql2	= "";
		int	isOk1 =	0;
		int	v_seq =	box.getInt("p_seq");
		
		try	{
			connMgr	= new DBConnectionManager();

			sql1 = "delete from	TZ_TUTORMESSAGE	where seq = ? ";
			pstmt1 = connMgr.prepareStatement(sql1);
			pstmt1.setInt(1, v_seq);
			isOk1 =	pstmt1.executeUpdate();

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box,	sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {}	}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1;
	}
	
	   /**
	   * 선택된	공지사항 게시물 상세내용 select
	   * @param box          receive from the form object and session
	   * @return ArrayList   조회한 상세정보
	   * @throws Exception
	   */   
	   public DataBox selectMessage(RequestBox box)	throws Exception {
			DBConnectionManager	connMgr	= null;
			ListSet	ls = null;
			String sql = "";
			DataBox dbox = null;

			int	v_seq =	box.getInt("p_seq");

			try	{
				connMgr	= new DBConnectionManager();
				
				sql = " select seq, subj, userid, get_name(userid) name, "; 
				sql+= "        title, content, indate, cnt                   ";
	            sql+= "   from TZ_tutormessage                               ";
	            sql+= "  where seq =   " + v_seq;
				ls = connMgr.executeQuery(sql);
				
				if (ls.next()) {
	                dbox = ls.getDataBox();
	            }
				
				connMgr.executeUpdate("update TZ_TUTORMESSAGE set cnt = cnt + 1 where seq = "+v_seq);
			}
			catch (Exception ex) {
				ErrorManager.getErrorStackTrace(ex,	box, sql);
				throw new Exception("sql = " + sql + "\r\n"	+ ex.getMessage());
			}
			finally	{
				if(ls != null) {try	{ls.close();} catch(Exception e){}}
				if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
			}
			return dbox;
		}


	/**
	 * 교수메세지 리스트화면 select
	 * @param box        receive from the form object and session
	 * @return ArrayList 공지사항 리스트
	 * @throws Exception
	 */
	public ArrayList<DataBox> selectMessageList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		String sql = "";
		DataBox dbox = null;

		int v_pageno = box.getInt("p_pageno");
		
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");    //과정분류
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");   //과정분류
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");    //과정분류
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//과정&코스
        String  ss_action   = box.getString("s_action");

		try {
            if(ss_action.equals("go")){
				connMgr = new DBConnectionManager();
				list = new ArrayList<DataBox>();

				sql = " select a.seq, a.userid, get_name(userid) name, "; 
				sql+= "        a.title, a.indate, a.cnt, c.subjnm          ";
	            sql+= "  from TZ_tutormessage a, TZ_SUBJ c                 ";
	            sql+= "  where a.subj=c.subj                               ";
	
	            if (!ss_uclass.equals("ALL"))     sql+= " and c.upperclass = "+SQLString.Format(ss_uclass);
	            if (!ss_mclass.equals("ALL"))     sql+= " and c.middleclass = "+SQLString.Format(ss_mclass);
	            if (!ss_lclass.equals("ALL"))     sql+= " and c.lowerclass = "+SQLString.Format(ss_lclass);
	            if (!ss_subjcourse.equals("ALL")) sql+= " and c.subj       = "+SQLString.Format(ss_subjcourse);
	
				sql+= " order by a.seq desc                                                               \n";			
				
	//			System.out.println("TutorMessageAdminBean 교수메세지 리스트화면 : "+sql);
	
				ls = connMgr.executeQuery(sql);
	
				ls.setPageSize(row);                          //  페이지당 row 갯수를 세팅한다
	            ls.setCurrentPage(v_pageno);                //     현재페이지번호를 세팅한다.
	            int total_page_count = ls.getTotalPage();   //     전체 페이지 수를 반환한다
	            int total_row_count = ls.getTotalCount();   //     전체 row 수를 반환한다
	
				while (ls.next()) {
	                dbox = ls.getDataBox();
	                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
	                dbox.put("d_totalpagecount", new Integer(total_page_count));
	                dbox.put("d_rowcount",new Integer(row));					
	                list.add(dbox);
				}
            }
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		} finally {
			if (ls != null) { try {ls.close();} catch (Exception e) {}}
			if (connMgr != null) {try {	connMgr.freeConnection();} catch (Exception e10) {}}
		}
		return list;
	}

}
