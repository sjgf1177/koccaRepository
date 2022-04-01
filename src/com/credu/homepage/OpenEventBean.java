package com.credu.homepage;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class OpenEventBean {

	private int row;
	private ConfigSet config;
	
    public OpenEventBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 이벤트 리스트 조회
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public ArrayList selectEventList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		StringBuffer		query	= null;
		ArrayList 			list	= null;
		DataBox				dbox	= null;
		ListSet				ls		= null;
		
		String v_search 	= box.getStringDefault("p_search", "");
		String v_searchtext = box.getStringDefault("p_searchtext", "");
		String v_sdate 		= box.getStringDefault("p_searchSdate", "");
		String v_edate 		= box.getStringDefault("p_searchEdate", "");
		
		int v_pageno		= box.getInt("p_pageno");
		
		try{
			connMgr = new DBConnectionManager();
			query = new StringBuffer();
			list = new ArrayList();
			
			query.append("\n select event_seq, event_title, use_yn, count(event_seq) over() as tot_cnt,		");
			query.append("\n        to_char(to_date(start_date, 'yyyyMMdd'), 'yyyy.MM.dd') as start_date,	");
			query.append("\n        to_char(to_date(end_date, 'yyyyMMdd'), 'yyyy.MM.dd') as end_date,		");
			query.append("\n        study_limit																");
			query.append("\n   from tz_openclass_event														");
			query.append("\n  where 1 = 1																	");
			if(!"".equals(v_search)){
				if("title".equals(v_search)){
					query.append("\n    and event_title like '%").append(v_searchtext).append("%'			");
				}else{
					query.append("\n    and start_date between '").append(v_sdate).append("' and '").append(v_edate).append("'");
				}
			}
			query.append("\n  order by event_seq desc														");
			
			ls = connMgr.executeQuery(query.toString());
			
			//페이징
            ls.setPageSize(row);             			//  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno);                //  현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();   //  전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount();   //	전체 row 수를 반환한다
			
			while(ls.next()){
				dbox = ls.getDataBox();
				
				//페이징
	            dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
	            dbox.put("d_totalpage", new Integer(total_page_count));
	            dbox.put("d_rowcount", new Integer(row));
				
				list.add(dbox);
			}
			
		}catch(Exception ex){
			ErrorManager.getErrorStackTrace(ex, box, query.toString());
            throw new Exception("sql = " + query.toString() + "\r\n" + ex.getMessage());
		}finally{
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
		return list;
	}
	
	/**
	 * 이벤트 상세정보
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public DataBox selectEventInfo(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		StringBuffer		query	= null;
		DataBox				dbox	= null;
		ListSet				ls		= null;
		
		String v_seq 	= box.getString("p_event_seq");
		
		try{
			connMgr = new DBConnectionManager();
			query = new StringBuffer();
			
			query.append("\n select event_seq, event_title, use_yn, study_limit,							");
			query.append("\n        to_char(to_date(start_date, 'yyyyMMdd'), 'yyyy.MM.dd') as start_date,	");
			query.append("\n        to_char(to_date(end_date, 'yyyyMMdd'), 'yyyy.MM.dd') as end_date,		");
			query.append("\n        case when sysdate > to_date(start_date, 'yyyyMMdd') 					");
			query.append("\n             then 'N' else 'Y' end as update_stat,								");
			query.append("\n        to_char(add_months(to_date(start_date, 'yyyyMMdd'), -1), 'yyyyMMdd') as sdate,	");
			query.append("\n        to_char(add_months(to_date(end_date, 'yyyyMMdd'), -1), 'yyyyMMdd') as edate		");
			query.append("\n   from tz_openclass_event														");
			query.append("\n  where event_seq = ").append(v_seq);
			
			ls = connMgr.executeQuery(query.toString());
			while(ls.next()){
				dbox = ls.getDataBox();
			}
			
		}catch(Exception ex){
			ErrorManager.getErrorStackTrace(ex, box, query.toString());
			throw new Exception("sql = " + query.toString() + "\r\n" + ex.getMessage());
		}finally{
			if (ls != null) {
				try {
					ls.close();
				} catch (Exception e) {
				}
			}
			if (connMgr != null) {
				try {
					connMgr.freeConnection();
				} catch (Exception e10) {
				}
			}
		}
		return dbox;
	}
	
	/**
	 * 이벤트강좌별 수강 수 조회
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public ArrayList selectEventCountList(RequestBox box, DataBox info) throws Exception {
		DBConnectionManager connMgr = null;
		StringBuffer		query	= null;
		ArrayList 			list	= null;
		DataBox				dbox	= null;
		ListSet				ls		= null;
		
		String v_seq 	= box.getString("p_event_seq");
		
		String v_sdate 	= box.getStringDefault("p_sdate", info.getString("d_sdate")) ;
		String v_edate 	= box.getStringDefault("p_edate", info.getString("d_edate"));
		
		box.put("p_sdate", v_sdate);
		box.put("p_edate", v_edate);
		
		try{
			connMgr = new DBConnectionManager();
			query = new StringBuffer();
			list = new ArrayList();
			
			query.append("\n select a.seq, b.lecnm,																	");
			query.append("\n        (																				");
			query.append("\n             select sum(cnt)															");
			query.append("\n               from tz_viewcount														");
			query.append("\n              where viewdate between '"+v_sdate+"' and '"+v_edate+"'					");
			query.append("\n                and seq = a.seq															");
			query.append("\n        ) as prev_count,																");
			query.append("\n        (																				");
			query.append("\n             select sum(cnt)															");
			query.append("\n               from tz_viewcount														");
			query.append("\n              where viewdate between c.start_date and c.end_date						");
			query.append("\n                and seq = a.seq															");
			query.append("\n        ) as event_all_count,															");
			query.append("\n        (																				");
			query.append("\n             select count(0)															");
			query.append("\n               from tz_openclass_target													");
			query.append("\n              where event_seq = a.event_seq												");
			query.append("\n                and seq = a.seq															");
			query.append("\n                and study_date between c.start_date||'000000' and c.end_date||'235959'	");
			query.append("\n        ) as login_count																");
			query.append("\n   from tz_openclass_seq a																");
			query.append("\n  inner join tz_goldclass b																");
			query.append("\n     on a.seq = b.seq																	");
			query.append("\n  inner join tz_openclass_event c														");
			query.append("\n     on a.event_seq = c.event_seq														");
			query.append("\n  where a.event_seq = ").append(v_seq);
			query.append("\n  order by a.seq																		");
			
			ls = connMgr.executeQuery(query.toString());
			while(ls.next()){
				dbox = ls.getDataBox();
				list.add(dbox);
			}
			
		}catch(Exception ex){
			ErrorManager.getErrorStackTrace(ex, box, query.toString());
			throw new Exception("sql = " + query.toString() + "\r\n" + ex.getMessage());
		}finally{
			if (ls != null) {
				try {
					ls.close();
				} catch (Exception e) {
				}
			}
			if (connMgr != null) {
				try {
					connMgr.freeConnection();
				} catch (Exception e10) {
				}
			}
		}
		return list;
	}
	
	/**
	 * 이벤트 당첨 대상자 리스트
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public ArrayList selectEventTargetList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		StringBuffer		query	= null;
		ArrayList 			list	= null;
		DataBox				dbox	= null;
		ListSet				ls		= null;
		
		String v_seq 	= box.getString("p_event_seq");
		
		try{
			connMgr = new DBConnectionManager();
			query = new StringBuffer();
			list = new ArrayList();
			
			query.append("\n select userid, name, email, handphone, seq, lecnm, study_date, study_count, row_cnt					");
			query.append("\n   from (																								");
			query.append("\n         select a.userid, b.name, crypto.dec('normal',b.email) as email, e.study_limit, 				");
			query.append("\n         	    crypto.dec('normal',b.handphone) as handphone, a.seq, c.lecnm,  a.study_count,			");
			query.append("\n                to_char(to_date(a.study_date, 'yyyyMMddhh24miss'), 'yyyy.MM.dd hh24 : mi') as study_date,	");
			query.append("\n                count(a.userid) over(partition by a.userid ) as row_cnt									");
			query.append("\n           from tz_openclass_target a																	");
			query.append("\n          inner join tz_member b																		");
			query.append("\n             on a.userid = b.userid																		");
			query.append("\n          inner join tz_goldclass c																		");
			query.append("\n             on a.seq = c.seq																			");
			query.append("\n          inner join tz_openclass_seq d																	");
			query.append("\n             on a.seq = d.seq																			");
			query.append("\n            and a.event_seq = d.event_seq																");
			query.append("\n          inner join tz_openclass_event e																");
			query.append("\n             on d.event_seq = e.event_seq																");
			query.append("\n            and a.study_date between e.start_date || '000000' and e.end_date || '235959'				");
			query.append("\n          where a.event_seq = ").append(v_seq);
			query.append("\n         ) t																							");
			query.append("\n  where row_cnt >= study_limit																			");
			query.append("\n  order by userid, study_date																			");
			
			ls = connMgr.executeQuery(query.toString());
			while(ls.next()){
				dbox = ls.getDataBox();
				list.add(dbox);
			}
			
		}catch(Exception ex){
			ErrorManager.getErrorStackTrace(ex, box, query.toString());
			throw new Exception("sql = " + query.toString() + "\r\n" + ex.getMessage());
		}finally{
			if (ls != null) {
				try {
					ls.close();
				} catch (Exception e) {
				}
			}
			if (connMgr != null) {
				try {
					connMgr.freeConnection();
				} catch (Exception e10) {
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 열린강좌 리스트 조회
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public ArrayList selectOpenclassList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		StringBuffer		query	= null;
		ArrayList 			list	= null;
		DataBox				dbox	= null;
		ListSet				ls		= null;
		
		String v_search = box.getStringDefault("p_search_event", "");
		String v_event_seq = box.getStringDefault("p_event_seq", "");
		
		Vector<String> v_seq = box.getVector("p_seq");
		String inSeq = "";
		
		try{
			connMgr = new DBConnectionManager();
			query = new StringBuffer();
			list = new ArrayList();
			
			for(int i=0; i<v_seq.size(); i++){
				inSeq += (inSeq.equals("") ? "" : ", ") + v_seq.get(i);
			}
			
			query.append("\n select seq, lecnm		");
			query.append("\n   from tz_goldclass	");
			query.append("\n  where useyn = 'Y'		");
			if(!v_search.equals("")){
				query.append("\n    and lecnm like '%"+v_search+"%'		");
			}
			if(!inSeq.equals("")){
				query.append("\n    and seq not in ("+inSeq+")		");
			}
			if(!v_event_seq.equals("")){
				query.append("\n    and seq not in (select seq from tz_openclass_seq where event_seq = "+v_event_seq+")	");
			}
			query.append("\n  order by seq			");
			
			ls = connMgr.executeQuery(query.toString());
			while(ls.next()){
				dbox = ls.getDataBox();
				list.add(dbox);
			}
			
		}catch(Exception ex){
			ErrorManager.getErrorStackTrace(ex, box, query.toString());
			throw new Exception("sql = " + query.toString() + "\r\n" + ex.getMessage());
		}finally{
			if (ls != null) {
				try {
					ls.close();
				} catch (Exception e) {
				}
			}
			if (connMgr != null) {
				try {
					connMgr.freeConnection();
				} catch (Exception e10) {
				}
			}
		}
		return list;
	}
	
	/**
	 * 열린강좌 선택리스트
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public ArrayList selectOpenclassCheckList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		StringBuffer		query	= null;
		ArrayList 			list	= null;
		DataBox				dbox	= null;
		ListSet				ls		= null;

		String v_event_seq = box.getStringDefault("p_event_seq", "");
		
		Vector<String> v_seq = box.getVector("p_seq");
		String inSeq = "";
		
		try{
			connMgr = new DBConnectionManager();
			query = new StringBuffer();
			list = new ArrayList();
			
			for(int i=0; i<v_seq.size(); i++){
				inSeq += (inSeq.equals("") ? "" : ", ") + v_seq.get(i);
			}
			
			if(!inSeq.equals("") || !v_event_seq.equals("")){
				query.append("\n select seq, lecnm			");
				query.append("\n   from tz_goldclass		");
				query.append("\n  where 1 = 1				");
				if(!inSeq.equals("")){
					query.append("\n    and seq in ("+inSeq+")	");
				}
				if(!v_event_seq.equals("")){
					query.append("\n    and seq in (select seq from tz_openclass_seq where event_seq = "+v_event_seq+")	");
				}
				query.append("\n  order by seq				");
				
				ls = connMgr.executeQuery(query.toString());
				while(ls.next()){
					dbox = ls.getDataBox();
					list.add(dbox);
				}
			}
			
		}catch(Exception ex){
			ErrorManager.getErrorStackTrace(ex, box, query.toString());
			throw new Exception("sql = " + query.toString() + "\r\n" + ex.getMessage());
		}finally{
			if (ls != null) {
				try {
					ls.close();
				} catch (Exception e) {
				}
			}
			if (connMgr != null) {
				try {
					connMgr.freeConnection();
				} catch (Exception e10) {
				}
			}
		}
		return list;
	}
	
	/**
	 * 이벤트 등록
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public int insertEvent(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement	pstmt	= null;
		PreparedStatement	pstmt2	= null;
		StringBuffer		query	= null;
		ListSet				ls		= null;
		
		int isOk = 0;
		int pstmtIndex = 1;
		
		String v_event_title = box.getString("p_event_title");
		String v_start_date = box.getString("p_start_date");
		String v_end_date = box.getString("p_end_date");
		String v_use_yn = box.getString("p_use_yn");
		String v_study_limit = box.getString("p_study_limit");
		
		Vector<String> v_seq = box.getVector("p_seq");
		String v_event_seq = "";
		try{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			query = new StringBuffer();
			
			query.append("select seq_openclass_event.nextval as event_seq from dual");
			ls = connMgr.executeQuery(query.toString());
			while(ls.next()){
				v_event_seq = ls.getString("event_seq");
			}
			
			
			query.setLength(0);
			query.append("\n insert into tz_openclass_event(	");
			query.append("\n     event_seq,						");
			query.append("\n 	 event_title, 					");
			query.append("\n     start_date, 					");
			query.append("\n     end_date, 						");
			query.append("\n     use_yn,   						");
			query.append("\n     study_limit					");
			query.append("\n )									");
			query.append("\n values(							");
			query.append("\n     ?, 							");
			query.append("\n     ?, 							");
			query.append("\n     ?, 							");
			query.append("\n     ?, 							");
			query.append("\n     ?, 							");
			query.append("\n     ?	 							");
			query.append("\n )									");
			
			pstmt = connMgr.prepareStatement(query.toString());
			
			pstmt.setString(pstmtIndex++, v_event_seq);
			pstmt.setString(pstmtIndex++, v_event_title);
			pstmt.setString(pstmtIndex++, v_start_date);
			pstmt.setString(pstmtIndex++, v_end_date);
			pstmt.setString(pstmtIndex++, v_use_yn);
			pstmt.setString(pstmtIndex++, v_study_limit);
			
			pstmt.executeUpdate();
			
			
			query.setLength(0);
			query.append("\n insert into tz_openclass_seq(	");
			query.append("\n     event_seq, 				");
			query.append("\n     seq						");
			query.append("\n )								");
			query.append("\n values(						");
			query.append("\n     ?, 						");
			query.append("\n     ? 							");
			query.append("\n )								");
			
			pstmt2 = connMgr.prepareStatement(query.toString());
			
			for(int i=0; i<v_seq.size(); i++){
				pstmtIndex = 1;
				pstmt2.setString(pstmtIndex++, v_event_seq);
				pstmt2.setString(pstmtIndex++, v_seq.get(i));
				pstmt2.addBatch();
				pstmt2.clearParameters();
				pstmt2.clearWarnings();
			}
			
			if(pstmt2 != null){
				pstmt2.executeBatch();
			}
			
			
			isOk = 1;
			if(connMgr != null){
				connMgr.commit();
			}
		}catch(Exception ex){
			isOk = 0;
			if(connMgr != null){
				connMgr.rollback();
			}
			ex.printStackTrace();
			ErrorManager.getErrorStackTrace(ex, box, query.toString());
			throw new Exception("sql = " + query.toString() + "\r\n" + ex.getMessage());
		}finally{
			if (ls != null) {
				try {
					ls.close();
				} catch (Exception e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
				}
			}
			if (pstmt2 != null) {
				try {
					pstmt2.close();
				} catch (Exception e) {
				}
			}
			if (connMgr != null) {
				try {
					connMgr.freeConnection();
				} catch (Exception e10) {
				}
			}
		}
		return isOk;
	}
	
	/**
	 * 이벤트 수정
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public int updateEvent(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement	pstmt	= null;
		PreparedStatement	pstmt2	= null;
		StringBuffer		query	= null;
		
		int isOk = 0;
		int pstmtIndex = 1;
		
		String v_event_seq = box.getString("p_event_seq");
		String v_event_title = box.getString("p_event_title");
		String v_start_date = box.getString("p_start_date");
		String v_end_date = box.getString("p_end_date");
		String v_use_yn = box.getString("p_use_yn");
		String v_study_limit = box.getString("p_study_limit");
		
		Vector<String> v_seq = box.getVector("p_seq");
		try{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			query = new StringBuffer();
			
			
			query.setLength(0);
			query.append("\n update tz_openclass_event	");
			query.append("\n    set event_title = ?, 	");
			query.append("\n        start_date 	= ?, 	");
			query.append("\n        end_date 	= ?, 	");
			query.append("\n        use_yn 		= ?,   	");
			query.append("\n        study_limit = ?		");
			query.append("\n  where event_seq 	= ?		");
			
			pstmt = connMgr.prepareStatement(query.toString());
			
			pstmt.setString(pstmtIndex++, v_event_title);
			pstmt.setString(pstmtIndex++, v_start_date.replaceAll("\\.", ""));
			pstmt.setString(pstmtIndex++, v_end_date.replaceAll("\\.", ""));
			pstmt.setString(pstmtIndex++, v_use_yn);
			pstmt.setString(pstmtIndex++, v_study_limit);
			pstmt.setString(pstmtIndex++, v_event_seq);
			
			pstmt.executeUpdate();
			
			
			query.setLength(0);
			query.append("\n delete tz_openclass_seq	");
			query.append("\n  where event_seq = ?		");
			pstmt = connMgr.prepareStatement(query.toString());
			pstmtIndex = 1;
			pstmt.setString(pstmtIndex++, v_event_seq);
			pstmt.executeUpdate();
			
			
			query.setLength(0);
			query.append("\n insert into tz_openclass_seq(	");
			query.append("\n     event_seq, 				");
			query.append("\n     seq						");
			query.append("\n )								");
			query.append("\n values(						");
			query.append("\n     ?, 						");
			query.append("\n     ? 							");
			query.append("\n )								");
			
			pstmt2 = connMgr.prepareStatement(query.toString());
			
			for(int i=0; i<v_seq.size(); i++){
				pstmtIndex = 1;
				pstmt2.setString(pstmtIndex++, v_event_seq);
				pstmt2.setString(pstmtIndex++, v_seq.get(i));
				pstmt2.addBatch();
				pstmt2.clearParameters();
				pstmt2.clearWarnings();
			}
			
			if(pstmt2 != null){
				pstmt2.executeBatch();
			}
			
			
			isOk = 1;
			if(connMgr != null){
				connMgr.commit();
			}
		}catch(Exception ex){
			isOk = 0;
			if(connMgr != null){
				connMgr.rollback();
			}
			ex.printStackTrace();
			ErrorManager.getErrorStackTrace(ex, box, query.toString());
			throw new Exception("sql = " + query.toString() + "\r\n" + ex.getMessage());
		}finally{
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
				}
			}
			if (pstmt2 != null) {
				try {
					pstmt2.close();
				} catch (Exception e) {
				}
			}
			if (connMgr != null) {
				try {
					connMgr.freeConnection();
				} catch (Exception e10) {
				}
			}
		}
		return isOk;
	}
	
	/**
	 * 이벤트 삭제
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public int deleteEvent(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement	pstmt	= null;
		StringBuffer		query	= null;
		
		int isOk = 0;
		int pstmtIndex = 1;
		
		String v_event_seq = box.getString("p_event_seq");
		try{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			query = new StringBuffer();
			
			query.setLength(0);
			query.append("\n delete tz_openclass_seq	");
			query.append("\n  where event_seq = ?		");
			pstmt = connMgr.prepareStatement(query.toString());
			pstmtIndex = 1;
			pstmt.setString(pstmtIndex++, v_event_seq);
			pstmt.executeUpdate();
			
			query.setLength(0);
			query.append("\n delete tz_openclass_event	");
			query.append("\n  where event_seq 	= ?		");
			pstmt = connMgr.prepareStatement(query.toString());
			pstmtIndex = 1;
			pstmt.setString(pstmtIndex++, v_event_seq);
			pstmt.executeUpdate();
			
			
			isOk = 1;
			if(connMgr != null){
				connMgr.commit();
			}
		}catch(Exception ex){
			isOk = 0;
			if(connMgr != null){
				connMgr.rollback();
			}
			ex.printStackTrace();
			ErrorManager.getErrorStackTrace(ex, box, query.toString());
			throw new Exception("sql = " + query.toString() + "\r\n" + ex.getMessage());
		}finally{
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
				}
			}
			if (connMgr != null) {
				try {
					connMgr.freeConnection();
				} catch (Exception e10) {
				}
			}
		}
		return isOk;
	}
}
