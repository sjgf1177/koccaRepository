
//**********************************************************
//1. ��      ��: �����׽�Ʈ �����ȸ
//2. ���α׷���: JindanResultBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 
//**********************************************************

package com.credu.jindan;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.system.*;
import com.credu.common.*;
import com.credu.jindan.*;

import java.text.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JindanResultBean {

	private ConfigSet config;
    private int row;

    public JindanResultBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.manage.row"));        //        �� ����� �������� row ���� �����Ѵ�
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }



	/**
	�����׽�Ʈ ����м�����Ʈ 
	@param 
	@return ArrayList
	*/
    public ArrayList SelectReaultList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet		ls			= null;
        ArrayList	list		= null;
        DataBox		dbox		= null;
        String		head_sql	= "";
		String		body_sql	= "";		
        String		group_sql	= "";
        String		order_sql	= "";
		String		count_sql	= "";
        String		sql			= "";

        int			v_pageno	= box.getInt("p_pageno");
        int			v_pagesize	= box.getInt("p_pagesize");
		
		//�˻�����
		String	v_grcode		= box.getString("s_grcode");
		String	v_upperclass	= box.getString("s_upperclass");
		String	v_middleclass	= box.getString("s_middleclass");
		String	v_lowerclass	= box.getString("s_lowerclass");
		String	v_startdate		= box.getString("p_startdate");			//�˻��Ⱓ ����
		String	v_enddate		= box.getString("p_enddate");				//�˻��Ⱓ ��
		String	v_name			= box.getString("p_name");		
        String	v_action		= box.getStringDefault("p_action",  "change");		

        try {

            if (v_action.equals("go")) {

				connMgr = new DBConnectionManager();

		        list = new ArrayList();
		
					head_sql = " select a.userid,  b.name , a.upperclass, a.middleclass, a.lowerclass, \n";  
					head_sql += " (select distinct classname  from  tz_subjatt where upperclass = a.upperclass and middleclass='000' and lowerclass='000'  ) upperclassnm,   \n"; 
					head_sql += "  (select distinct classname  from  tz_subjatt where upperclass = a.upperclass and middleclass= a.middleclass and lowerclass='000'  ) middleclassnm, \n"; 
					head_sql += " (select distinct classname  from  tz_subjatt where upperclass = a.upperclass and middleclass=a.middleclass and  lowerclass= a.lowerclass  ) lowerclassnm, \n"; 
					head_sql += " a.ldate ,  sum(a.score) score   , count(a.userid) cnt   \n";  
					body_sql += " from tz_jindanresult a   , tz_member b  , tz_grsubj  c   \n"; 
					body_sql += " where 1=1 	 \n";
					body_sql += " and a.userid = b.userid  \n";
					body_sql += " and a.subj = c.subjcourse  \n"; 
					
					if(!v_grcode.equals("")){		//�׷��ڵ尡 ������
						body_sql += " and c.grcode = "+ SQLString.Format(v_grcode);
					}			
					if(!v_upperclass.equals("") && !v_upperclass.equals("ALL")){	//��з��� ������
						body_sql += " and a.upperclass = "+ SQLString.Format(v_upperclass);
					}
					if(!v_middleclass.equals("") && !v_middleclass.equals("ALL")){	//�ߺз��� ������
						body_sql += " and a.middleclass = "+ SQLString.Format(v_middleclass);
					}
					if(!v_lowerclass.equals("") && !v_lowerclass.equals("ALL") ){	//�Һз��� ������
						body_sql += "and a.lowerclass = "+ SQLString.Format(v_lowerclass);
					}			
					if(!v_startdate.equals("")){		//�Ⱓ�˻� �������� ������
						body_sql += "and a.ldate  >= "+ SQLString.Format(v_startdate);
					}			
					if(!v_enddate.equals("")){			//�Ⱓ�˻� �������� ������
						body_sql += "and a.ldate <= "+ SQLString.Format(v_enddate);
					}			
					if(!v_name.equals("")){			//������ ������
						body_sql += "and b.name LIKE " + StringManager.makeSQL(v_name + "%");
					}						
					
					group_sql += " group by  a.userid, b.name, upperclass, middleclass,  lowerclass, a.ldate \n";  
					order_sql += " order by a.upperclass,  a.middleclass, lowerclass   \n";
					
		        	sql = head_sql+ body_sql+ group_sql+ order_sql;

					System.out.println("#������ ���ܰ���м�#:"+sql);
					
		            ls = connMgr.executeQuery(sql);

					count_sql = "select count(*) from (" + head_sql + body_sql + group_sql + ")";
					System.out.println("count_sql"+count_sql);
					int totalrowcount = BoardPaging.getTotalRow(connMgr, count_sql) ;

		            ls.setPageSize(v_pagesize);                 //�������� row ������ �����Ѵ�
		            ls.setCurrentPage(v_pageno, totalrowcount); //������������ȣ�� �����Ѵ�.
		            int total_page_count = ls.getTotalPage();  	//��ü ������ ���� ��ȯ�Ѵ�

		            while (ls.next()) {
		                dbox = ls.getDataBox();

		                dbox.put("d_dispnum",		new Integer(totalrowcount - ls.getRowNum() + 1));
		                dbox.put("d_totalpage",		new Integer(total_page_count));
		                dbox.put("d_rowcount",  	new Integer(row));
		                dbox.put("d_totalrowcount",	new Integer(totalrowcount));

		                list.add(dbox);
		            }
		            ls.close();

            } else {		//action�� go�϶��� ����
                list = new ArrayList();
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
    ���ܰ���м� �̷»���ȸ 
    @param box          receive from the form object and session
    @return ArrayList   �ش��������Ʈ
    */
    public ArrayList SelectJindanResultDetail( RequestBox box) 
		throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list  = null;
        int v_result = 0;
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;
		
		//String v_userid = box.getSession("userid");
		String v_userid = box.getString("p_userid");
		String v_upperclass = box.getString("p_upperclass");
		String v_middleclass = box.getString("p_middleclass");
		String v_lowerclass = box.getString("p_lowerclass");		

        try {
            connMgr = new DBConnectionManager();			
			list = new ArrayList();
			sql = " select a.subj , b.subjnm, a.middleclass, a.lowerclass, a.userid, a.score, a.ldate \n";
			sql+= " from tz_jindanresult  a , tz_subj b   \n";
			sql+= " where 1=1 \n";
			sql+= " and a.subj = b.subj \n"; 
			sql+= " and a.upperclass = "+SQLString.Format(v_upperclass);
			sql+= " and a.middleclass = "+SQLString.Format(v_middleclass);
			if(!v_lowerclass.equals("") && !v_lowerclass.equals("ALL") ){
				sql+= " and a.lowerclass = "+SQLString.Format(v_lowerclass);
			} else {
				sql+= " and trim(a.lowerclass) is null";
			}
			sql+= " and a.userid= "+SQLString.Format(v_userid);
			sql+= " order by a.score \n";
			System.out.println("���ܰ�� �ٷκ���: "+sql);
				
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
				list.add(dbox);
			
			}
			ls.close();

		}
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) {} }			
       }
        return list;
    }
	



}