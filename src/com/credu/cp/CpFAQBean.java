//**********************************************************
//1. 제      목: 외주관리시스템의 FAQ빈 클래스
//2. 프로그램명: CpFAQBean.java
//3. 개      요: FAQ빈
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 박준현 2004.
//7. 수      정: 이나연 05.11.17 _ totalcount 하기위한 쿼리수정
//**********************************************************
package com.credu.cp;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;


public class CpFAQBean {
	private ConfigSet config;
    private int row;
	private int gubun = 1;
	public CpFAQBean() {
    }



    /**
    Faq화면 리스트
    @param box          receive from the form object and session
    @return ArrayList   Faq 리스트
    */
    public ArrayList selectListFaq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
		// 수정일 : 05.11.17 수정자 : 이나연 _ totalcount 하기위한 수정
        String sql = "";
		String head_sql = "";
		String body_sql = "";
		String group_sql = "";
		String order_sql = "";
		String count_sql = "";
		
        DataBox dbox = null;

        String v_faqcategory = box.getString("p_faqcategory");
        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
		int v_pageno        = box.getInt("p_pageno");
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            head_sql  = " select a.fnum, a.title, a.contents, a.indate, a.luserid, a.ldate  ";
            body_sql += "   from TZ_FAQ  a,tz_faq_category b                                       ";
            body_sql += "	where a.faqcategory = b.faqcategory ";
			body_sql += "     and  b.faqgubun   = " + gubun;
			body_sql += "     and a.faqcategory   = " + SQLString.Format(v_faqcategory);
            order_sql += " order by a.faqcategory desc";
			
			sql= head_sql+ body_sql+ group_sql+ order_sql;
			count_sql="select count(*) "+ body_sql;
			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);    //     전체 row 수를 반환한다
			int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
			
			ls = connMgr.executeQuery(sql);
			ls.setPageSize(row);             				//  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);   //     현재페이지번호를 세팅한다.

           while (ls.next()) {
                dbox = ls.getDataBox();
				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));             
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

    /**
     * FAQ 카테고리 셀렉트박스
	 * @param name           셀렉트박스명
	 * @param selected       선택값
	 * @param event          이벤트명
	 * @param allcheck       전체유무
	 * @return
	 * @throws Exception
	 */
    public static String getFaqCategorySelecct (String name, String selected, String event, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        FaqCategoryData data = null;

        result = "  <SELECT name= " + "\"" +name+ "\"" + " " + event + " > \n";
        if (allcheck == 1) {
          result += "    <option value=''>== 선택하세요 ==</option> \n";
        }
        try {
            connMgr = new DBConnectionManager();

            sql  = " select faqcategory,faqcategorynm from TZ_FAQ_CATEGORY where faqgubun = '1' ";
            sql += " order by faqcategory asc                        ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new FaqCategoryData();
                data.setFaqCategory(ls.getString("faqcategory"));
                data.setFaqCategorynm(ls.getString("faqcategorynm"));
							
                result += "    <option value=" + "\"" +data.getFaqCategory()  + "\"";
                if (selected.equals(data.getFaqCategory())) {
                    result += " selected ";
                }               
                result += ">" + data.getFaqCategorynm() + "</option> \n";
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        result += "  </SELECT> \n";
        return result;
    }
}
