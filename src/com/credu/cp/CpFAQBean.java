//**********************************************************
//1. ��      ��: ���ְ����ý����� FAQ�� Ŭ����
//2. ���α׷���: CpFAQBean.java
//3. ��      ��: FAQ��
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: ������ 2004.
//7. ��      ��: �̳��� 05.11.17 _ totalcount �ϱ����� ��������
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
    Faqȭ�� ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   Faq ����Ʈ
    */
    public ArrayList selectListFaq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
		// ������ : 05.11.17 ������ : �̳��� _ totalcount �ϱ����� ����
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
			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);    //     ��ü row ���� ��ȯ�Ѵ�
			int total_page_count = ls.getTotalPage();       //     ��ü ������ ���� ��ȯ�Ѵ�
			
			ls = connMgr.executeQuery(sql);
			ls.setPageSize(row);             				//  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count);   //     ������������ȣ�� �����Ѵ�.

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
     * FAQ ī�װ� ����Ʈ�ڽ�
	 * @param name           ����Ʈ�ڽ���
	 * @param selected       ���ð�
	 * @param event          �̺�Ʈ��
	 * @param allcheck       ��ü����
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
          result += "    <option value=''>== �����ϼ��� ==</option> \n";
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
