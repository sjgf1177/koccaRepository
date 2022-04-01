//**********************************************************
//1. ��      ��: ī�����  ���� (����)
//2. ���α׷���: SettlementAdminBean.java
//3. ��      ��: �Խ���
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: �ϰ��� 2006.01.10
//7. ��      ��:
//**********************************************************
package com.credu.polity;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

import oracle.jdbc.driver.*;
import oracle.sql.*;
import com.credu.common.*;
import com.credu.library.*;
import com.credu.study.*;
import com.credu.system.*;
import com.namo.*;
import com.credu.polity.*;


public class SettlementAdminBean {
	
	private ConfigSet config;
	private int row;
	private static final String FILE_TYPE = "p_file";           //      ���Ͼ��ε�Ǵ� tag name
	private static final int FILE_LIMIT = 1;                    //    �������� ���õ� ����÷�� ����
	
	public SettlementAdminBean() {
	  try{
	      config = new ConfigSet();
	      row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
	  }
	  catch(Exception e) {
	      e.printStackTrace();
	  }
}
	
	/**
	*  ����Ʈȭ�� select
	* @param  box receive from the form object and session
	* @return ArrayList  
	* @throws Exception
	*/
	public ArrayList selectBoardList(RequestBox box) throws Exception {
			PreparedStatement pstmt = null;  
			DBConnectionManager connMgr = null;
			ListSet ls = null;
			ArrayList list = null;
			DataBox dbox = null;
	//		2005.11.15_�ϰ��� : TotalCount ���� ���� ���� 
			String sql    	  = "";
			String count_sql  = "";
			String head_sql   = "";
			String body_sql   = "";		
			String group_sql  = "";
			String order_sql  = "";
	
			int 	v_pageno 	= box.getInt("p_pageno");
			
			String v_grcode		= box.getString("s_grcode");
			String v_year		= box.getString("s_gyear");
			String v_grseq      = box.getStringDefault("s_grseq","ALL");
			String v_uclass    	= box.getStringDefault("s_upperclass","ALL"); 	//�����з�
			String v_mclass    	= box.getStringDefault("s_middleclass","ALL");	//�����з�
			String v_lclass    	= box.getStringDefault("s_lowerclass","ALL");	//�����з�
			String v_subjcourse	= box.getStringDefault("s_subjcourse","ALL");
			String v_subjseq	= box.getString("s_subjseq");
			String v_subj		= box.getString("s_subj");
			String v_settlementstart	= box.getString("s_settlementstart");
			String v_settlementend		= box.getString("s_settlementend");
			
	    	try {
	        	connMgr = new DBConnectionManager();
	
				list = new ArrayList();
	
				head_sql += " Select ";
				head_sql += "	b.subj, v.subjnm, b.year, b.subjseq, b.userid, b.subjprice, b.usepoint, b.discountrate, b.paymoney,  ";
				head_sql += "	b.realpaymoney, b.userid, b.accountname, b.paydate, b.paytype, b.paystat, b.CARDNO, ";
				head_sql += "	b.CARDAPPROVALNO, b.cardyn, v.propstart, v.propend, ";
				head_sql += "	v.edustart, v.eduend, m.name, p.appdate ";
				body_sql += " From TZ_BILLING b ";
				body_sql += "	join VZ_SCSUBJSEQ v on v.subj = b.subj and v.year = b.year and v.subjseq = b.subjseq ";
				body_sql += " 	join TZ_MEMBER M on m.userid = b.userid ";
				body_sql += " 	join TZ_PROPOSE P on p.subj = b.subj and p.year = b.year ";
				body_sql += "		and p.subjseq = b.subjseq and p.userid = b.userid ";
				body_sql += " Where paytype = 'D' ";
				body_sql += "	and v.grcode = " + SQLString.Format(v_grcode);
				body_sql += " 	and v.gyear = " + SQLString.Format(v_year);
				
				if (!v_grseq.equals("ALL")) {sql += "    and v.grseq="  + SQLString.Format(v_grseq);}
				
	//				�˻���Ŀ� ���� ���� �˻����� �б�
				if (!v_uclass.equals("ALL"))		{body_sql +=" and v.scupperclass ='"+ v_uclass +"' ";}
				if (!v_mclass.equals("ALL"))		{body_sql +=" and v.scmiddleclass='"+ v_mclass +"' ";}
				if (!v_lclass.equals("ALL"))		{body_sql +=" and v.sclowerclass ='"+ v_lclass +"' ";}
				if (!v_subjcourse.equals("ALL")) 	{body_sql +=" and v.scsubj='"+ v_subjcourse +"' ";}
				if (!v_subjseq.equals("ALL"))    	{body_sql +=" and v.scsubjseq='"+ v_subjseq +"' ";}
				if (!v_settlementstart.equals(""))		{body_sql +=" and substring(b.paydate, 1,8)  >= to_date(" + v_settlementstart + ",'YYYYMMDD')  ";}
		        if (!v_settlementend.equals(""))		{body_sql +=" and substring(b.paydate, 1,8) <=  to_date("+ v_settlementend +",'YYYYMMDD') ";}
	           
				order_sql += " order by b.ldate desc ";
					
				sql= head_sql+ body_sql+group_sql+ order_sql;
System.out.println("sql = " + sql);				
				ls = connMgr.executeQuery(sql);
								
				count_sql= "select count(*) "+ body_sql;
	
				int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);
			
	        ls.setPageSize(row);                       //  �������� row ������ �����Ѵ�
	        ls.setCurrentPage(v_pageno, total_row_count);	// ������������ȣ�� �����Ѵ�.
	        int totalpagecount = ls.getTotalPage();    		// ��ü ������ ���� ��ȯ�Ѵ�
	
	        while (ls.next()) {
	            dbox = ls.getDataBox();
	
	            dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
	            dbox.put("d_totalpage", new Integer(totalpagecount));
	            dbox.put("d_rowcount", new Integer(row));
	            dbox.put("d_total_rowcount", new Integer(total_row_count));
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
	*  ����Ʈȭ�� select
	* @param  box receive from the form object and session
	* @return ArrayList  
	* @throws Exception
	*/
	public ArrayList selectExcelBoardList(RequestBox box) throws Exception {
			PreparedStatement pstmt = null;  
			DBConnectionManager connMgr = null;
			ListSet ls = null;
			ArrayList list = null;
			DataBox dbox = null;
	//		2005.11.15_�ϰ��� : TotalCount ���� ���� ���� 
			String sql    	  = "";
			String count_sql  = "";
			String head_sql   = "";
			String body_sql   = "";		
			String group_sql  = "";
			String order_sql  = "";
	
			int 	v_pageno 	= box.getInt("p_pageno");
			
			String v_grcode		= box.getString("s_grcode");
			String v_year		= box.getString("s_gyear");
			String v_grseq      = box.getStringDefault("s_grseq","ALL");
			String v_uclass    	= box.getStringDefault("s_upperclass","ALL"); 	//�����з�
			String v_mclass    	= box.getStringDefault("s_middleclass","ALL");	//�����з�
			String v_lclass    	= box.getStringDefault("s_lowerclass","ALL");	//�����з�
			String v_subjcourse	= box.getStringDefault("s_subjcourse","ALL");
			String v_subjseq	= box.getString("s_subjseq");
			String v_subj		= box.getString("s_subj");
			String v_accountstart	= box.getString("s_accountstart");
			String v_accountend		= box.getString("s_accountend");
			String v_accountstat	= box.getString("s_accountstat");
			
	    	try {
	        	connMgr = new DBConnectionManager();
	
				list = new ArrayList();
	
				head_sql += " Select ";
				head_sql += "	b.subj, v.subjnm, b.year, b.subjseq, b.userid, b.subjprice, b.usepoint, b.discountrate, b.paymoney,  ";
				head_sql += "	b.realpaymoney, b.userid, b.accountname, b.paydate, b.paytype, b.paystat, b.CARDNO, ";
				head_sql += "	b.CARDAPPROVALNO, b.cardyn, v.propstart, v.propend, ";
				head_sql += "	v.edustart, v.eduend, m.name, p.appdate ";
				body_sql += " From TZ_BILLING b ";
				body_sql += "	join VZ_SCSUBJSEQ v on v.subj = b.subj and v.year = b.year and v.subjseq = b.subjseq ";
				body_sql += " 	join TZ_MEMBER M on m.userid = b.userid ";
				body_sql += " 	join TZ_PROPOSE P on p.subj = b.subj and p.year = b.year ";
				body_sql += "		and p.subjseq = b.subjseq and p.userid = b.userid ";
				body_sql += " Where paytype = 'D' ";
				body_sql += "	and v.grcode = " + SQLString.Format(v_grcode);
				body_sql += " 	and v.gyear = " + SQLString.Format(v_year);
				
				if (!v_grseq.equals("ALL")) {sql += "    and v.grseq="  + SQLString.Format(v_grseq);}
				
	//				�˻���Ŀ� ���� ���� �˻����� �б�
				if (!v_uclass.equals("ALL"))		{body_sql +=" and v.scupperclass ='"+ v_uclass +"' ";}
				if (!v_mclass.equals("ALL"))		{body_sql +=" and v.scmiddleclass='"+ v_mclass +"' ";}
				if (!v_lclass.equals("ALL"))		{body_sql +=" and v.sclowerclass ='"+ v_lclass +"' ";}
				if (!v_subjcourse.equals("ALL")) 	{body_sql +=" and v.scsubj='"+ v_subjcourse +"' ";}
				if (!v_subjseq.equals("ALL"))    	{body_sql +=" and v.scsubjseq='"+ v_subjseq +"' ";}
				
				order_sql += " order by b.ldate desc ";
					
				sql= head_sql+ body_sql+group_sql+ order_sql;

				ls = connMgr.executeQuery(sql);
	
	        while (ls.next()) {
	            dbox = ls.getDataBox();

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
	
}