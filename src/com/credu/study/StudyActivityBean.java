//**********************************************************
//  1. 제      목: Activity
//  2. 프로그램명 : Activity
//  3. 개      요: Activity
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 2007.4.21 NOH HEE SUNG
//  7. 수      정: 
//**********************************************************

package com.credu.study;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.PageList;
import com.credu.library.RequestBox;
import com.dunet.common.util.StringUtil;

/**
* @author Administrator
*
* To change the template for this generated type comment go to
* Window>Preferences>Java>Code Generation>Code and Comments
*/
public class StudyActivityBean {
private ConfigSet config;
private static int row=10;


public void StudyActivityBean() {
    try{
        config = new ConfigSet();
        row = Integer.parseInt(config.getProperty("page.bulletin.row"));  //이 모듈의 페이지당 row 수를 셋팅한다
        row = 10; //강제로 지정
    }
    catch(Exception e) {
        e.printStackTrace();
    }
}


/**
 * 홈페이지 공지사항 메인사용 리스트
 * @param box          receive from the form object and session
 * @return ArrayList   공지사항 리스트
 * @throws Exception
 */
 public ArrayList selectList(RequestBox box) throws Exception {
	  DBConnectionManager	connMgr	= null;
	  Connection conn	= null;
	  ListSet	ls = null;
	  ArrayList list = null;
	  String sql     = "";
	  String count_sql = "";
    String head_sql  = "";
		String body_sql  = "";		
    String group_sql = "";
    String order_sql = "";

	  DataBox dbox = null;

		int		v_pageno 		= box.getInt("p_pageno");		
		
    String v_subj    = box.getSession("subj");
    String v_year    = box.getSession("year");
    String v_subjseq = box.getSession("subjseq");

		try	{
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			head_sql	= "select (select name from tz_member where userid=a.resno) name,resno,seq,input01,ldate  ";
			body_sql	+= " from ty_common_act a                                             ";
			body_sql	+= " where subj = '"+ v_subj +"' and year='"+ v_year +"' and subjseq='"+ v_subjseq +"' ";
			order_sql	+= " order by ldate desc   ";

			sql= head_sql+ body_sql+ group_sql+ order_sql;
			ls = connMgr.executeQuery(sql);
			
			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

			System.out.println("total_row_count = " + total_row_count);

			ls.setPageSize(row);			              	//	 페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, total_row_count);	//	   현재페이지번호를	세팅한다.
			int	total_page_count = ls.getTotalPage();		  	//	 전체 페이지 수를 반환한다
			
			System.out.println("total_page_count = " + total_page_count);
			
			while (ls.next()) {
					dbox = ls.getDataBox();
          dbox.put("d_dispnum"  , new Integer(total_row_count - ls.getRowNum() + 1));
          dbox.put("d_totalpage", new Integer(total_page_count));
          dbox.put("d_rowcount" , new Integer(row));
          list.add(dbox);
			}
		}
		catch (Exception ex) {
			   ErrorManager.getErrorStackTrace(ex, box,	sql);
			throw new Exception("sql = " + sql + "\r\n"	+ ex.getMessage());
		}
		finally	{
			if(ls != null) { try { ls.close(); }catch (Exception e)	{} }
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return list;
	}

/**
	* 등록
	* @param box      receive from the form object and session
	* @return isOk    1:insert success,0:insert fail
	* @throws Exception
	*/    
	 public int insertActivity(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		ListSet	ls = null;
		PreparedStatement pstmt1 = null;
		String sql = "";
		String sql1	= "",sql2 = "";
		int	isOk1 =	1;
		int	isOk2 =	1;

		String v_content  = StringUtil.removeTag(box.getString("p_content"));

		String s_userid = box.getSession("userid");
		
		
		String v_subj    = box.getSession("subj");
    String v_year    = box.getSession("year");
    String v_subjseq = box.getSession("subjseq");
    
    int v_maxseq = 0;

		try	{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql= "select nvl(max(seq),0) + 1 maxseq from ty_common_act where subj='"+v_subj+"' and year='"+v_year+"' and subjseq='"+v_subjseq+"' and gubun='ARCADE'";
			ls = connMgr.executeQuery(sql);
			if (ls.next()) {
				  v_maxseq = ls.getInt("maxseq");
			}
			ls.close();
			
			sql1 =	"insert	into ty_common_act(subj, year, subjseq, dates, seq, subseq, gubun, input01, resno, lresno, ldate)               ";
			sql1 +=	" values (?, ?, ?,'01', ?, ?, ?, ?, ?, ?, dbo.to_date(getdate(), 'YYYYMMDDHH24MISS')) ";

			pstmt1 = connMgr.prepareStatement(sql1);

			pstmt1.setString(1, v_subj);
			pstmt1.setString(2, v_year);
			pstmt1.setString(3,	v_subjseq);
			pstmt1.setInt(4,	v_maxseq);
			pstmt1.setInt(5,	0);
			pstmt1.setString(6,	"ARCADE");
			pstmt1.setString(7,	v_content);
			pstmt1.setString(8,	s_userid);
			pstmt1.setString(9,	s_userid);


			isOk1 =	pstmt1.executeUpdate();
			
			if(isOk1 > 0) connMgr.commit();
			else 		               connMgr.rollback();
		}
		catch (Exception ex) {
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex,	box, sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(ls != null)      { try { ls.close(); } catch (Exception e) {}	}
			if(pstmt1 != null)  { try { pstmt1.close(); } catch (Exception e1) {} }
			if(connMgr != null)   try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1;
	}
	
	
		/**
	* 삭제
	* @param box      receive from the form object and session
	* @return isOk    1:delete success,0:delete fail
	* @throws Exception
	*/    	
	public int deleteActivity(RequestBox	box) throws	Exception {
		DBConnectionManager	connMgr	= null;
		Connection conn	= null;
		PreparedStatement pstmt1 = null;
		String sql1	= "";
		int	isOk1 =	1;

		int	v_seq =	box.getInt("p_seq");
		String v_subj    = box.getSession("subj");
    String v_year    = box.getSession("year");
    String v_subjseq = box.getSession("subjseq");
		
		String s_userid = box.getSession("userid");
		
		try	{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			//if (v_subj.equals("") || v_year.equals("") || v_subjseq.equals("") || s_userid.equals("")) {
				sql1 = "delete from	ty_common_act	where subj = ? and year= ? and subjseq = ? and seq = ? and subseq=0 and gubun='ARCADE' and resno=?";
	
				pstmt1 = connMgr.prepareStatement(sql1);
	
				pstmt1.setString(1, v_subj);
				pstmt1.setString(2, v_year);
				pstmt1.setString(3, v_subjseq);
				pstmt1.setInt(4, v_seq);
				pstmt1.setString(5, s_userid);
	
				isOk1 =	pstmt1.executeUpdate();
			//} else {
				//isOk1 = 0;
			//}

            
			if (isOk1 >	0)	{
				connMgr.commit();
			} else connMgr.rollback();
		}
		catch (Exception ex) {
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box,	sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {}	}
            if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1;
	}
	
	/**
    * 게시판 번호달기
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */

     public static String printPageList(int totalPage, int currPage, int blockSize) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str="";
        if(totalPage > 0) {
            PageList pagelist = new PageList(totalPage,currPage,blockSize);

            str += "<table border='0' width='100%' align='center'>";
            str += "<tr>";
            //str += "    <td width='100%' align='center' valign='middle'>";

            if (pagelist.previous()) {
                str += "<td align='center' valign='middle'><a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\"><img src=\"/images/user/button/pre.gif\" border=\"0\" align=\"middle\"></a></td>  ";
            }else{
                str += "<td align='center' valign='middle'><img src=\"/images/user/button/pre.gif\" border=\"0\" align=\"middle\"></td>";
            }


            for (int i=pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += "<td align='center' valign='middle'><strong>" + i + "</strong>" + "</td>";
                } else {
                    str += "<td align='center' valign='middle'><a href=\"javascript:goPage('" + i + "')\">" + i + "</a></td> ";
                }
            }

            if (pagelist.next()) {
                str += "<td align='center' valign='middle'><a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\"><img src=\"/images/user/button/next.gif\"  border=\"0\" align=\"middle\"></a></td>";
            }else{
                str += "<td align='center' valign='middle'><img src=\"/images/user/button/next.gif\" border=\"0\" align=\"middle\"></td>";
            }

           /* if (str.equals("")) {
                str += "<자료가 없습니다.";
            }
			*/
           // str += "    </td>";
           // str += "    <td width='15%' align='center'>";
           // str += "    </td>";
            str += "</tr>";
            str += "</table>";
        }
        return str;
    }

	
}