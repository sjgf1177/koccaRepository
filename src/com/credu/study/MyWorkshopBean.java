//**********************************************************
//  1. ��	  ��: MY WORKSHOP BEAN
//  2. ���α׷���: MyWorkshopBean.java
//  3. ��	  ��: ���� ��ũ�� bean
//  4. ȯ	  ��: JDK 1.5
//  5. ��	  ��: 1.0
//  6. ��	  ��:
//  7. ��	  ��:
//**********************************************************
package com.credu.study;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

import com.credu.study.*;
import com.credu.common.*;
import com.credu.library.*;
import com.credu.system.*;

public class MyWorkshopBean {
	
    private ConfigSet config;
    private int row;

    public MyWorkshopBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
        }
        catch(Exception e) {
            e.printStackTrace();
        }        
        
    }

    /**
    ���� ��ũ�� �н����� ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList ����Ʈ
    */
     public ArrayList SelectMyWorkshopList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls          = null;
        ArrayList list1     = null;
		// ������ : 05.11.14 ������ : �̳��� _ totalcount �ϱ� ���� ��������
        String sql         = "";
		String head_sql="";
		String body_sql="";
		String body_wsql="";
		String group_sql="";
		String order_sql="";
		String count_sql="";
		
        String wsql         = "";
        DataBox dbox        = null;

        String v_userid   = box.getSession("userid");
        int    v_pageno    = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            head_sql = " SELECT   a.seq, a.seminargubun, a.seminarnm, a.tname, a.startdate, a.enddate, ";
            head_sql += "         a.propstart, a.propend, a.limitmember, b.code, ";
            head_sql += "         b.codenm seminargubunnm, ";
            head_sql += "         a.pass_yn, ";
            head_sql += "         a.cnt, c.userid, c.indate ";
        	body_sql += "    FROM tz_seminar a, tz_code b, tz_seminar_apply c ";
        	body_sql += "   WHERE a.seminargubun = b.code(+) ";
        	body_sql += "     AND b.gubun(+) = '0061' ";
        	body_sql += "     AND a.seq = c.seq ";
        	body_sql += "     AND c.userid = "+SQLString.Format(v_userid);
        	order_sql += "ORDER BY a.seq DESC ";
            
			sql= head_sql+ body_sql+ body_wsql+ group_sql+ order_sql;
                 System.out.println(" MyWorkshopBean   sql >>> "+sql);
            ls = connMgr.executeQuery(sql);
			
			count_sql= "select count(*) " + body_sql;
			//System.out.println(" MyWorkshopBean   count >>> "+ count_sql);
			int totalrowcount= BoardPaging.getTotalRow(connMgr, count_sql); // ��ü row ���� ��ȯ�Ѵ�

            ls.setPageSize(row);                            	// �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, totalrowcount);      	// ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage();       	// ��ü ������ ���� ��ȯ�Ѵ�

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum",   new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount",  new Integer(row));

                list1.add(dbox);
            }          
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    } 
 
    /**
    ���� ��ũ�� ��� �󼼺���
    @param box      receive from the form object and session
    @return ArrayList ����Ʈ
    */
    public DataBox selectMyWorkshop(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        int		v_seq		= box.getInt("p_seq");
		String	v_process	= box.getString("p_process");

        try {
            connMgr = new DBConnectionManager();            

            sql = " SELECT a.seq, a.seminargubun, a.seminarnm, a.content, a.startdate, a.enddate, ";
        	sql += "       a.starttime, a.endtime, a.propstart, a.propend, a.limitmember, a.tname, ";
        	sql += "       a.useyn, a.target, a.pass_content, a.cnt, a.userid, a.indate, ";
        	sql += "       a.luserid, a.ldate, ";
        	sql += "       a.pass_yn, ";
        	sql += "       pass_content ";
        	sql += "  FROM tz_seminar a ";
        	sql += " WHERE a.seq = "+SQLString.Format(v_seq);

            ls = connMgr.executeQuery(sql);
            while(ls.next()) {
                dbox = ls.getDataBox();
            }

            // ��ȸ�� ����
			if(!v_process.equals("MyWorkshopViewPage")){
			  connMgr.executeUpdate("update TZ_SEMINAR set cnt = cnt + 1 where seq = " + v_seq);
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

}