 
//**********************************************************
//  1. ��      ��: ���ְ����ý����� �������� �ڷ�� ��
//  2. ���α׷��� : CpNoticeBean.java
//  3. ��      ��: ���������ڷ�� ��
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �̿��� 2005. 7. 19
//  7. ��      ��: �̳��� 05.11.16 _ Oracle -> MSSQL (OuterJoin) ����
//**********************************************************

package com.credu.cp;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;


public class CpNoticeBean {
    private ConfigSet config;
    private int row;
    private String v_type = "AE";	
    public CpNoticeBean() {
        try{
            config = new ConfigSet();  
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
  
    /**
    * ���������ڷ�� ȭ�� ����Ʈ
    * @param box          receive from the form object and session
    * @return ArrayList   �������� ����Ʈ
    */
    public ArrayList selectPdsList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
		
		String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
		String s_cpseq      = box.getSession("cpseq");
		String s_gadmin     = box.getSession("gadmin");

        int v_pageno = box.getInt("p_pageno");      
        String isLogin = "Y";
        
        if(s_cpseq.equals("") || s_cpseq == null){
          	isLogin = "N";
        }

        try {
			
            connMgr = new DBConnectionManager();            
            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------

            list = new ArrayList();

            sql  = "select a.seq, a.adname, a.adtitle, count(b.realfile) filecnt, a.addate, a.cnt ";
            sql += " from tz_Notice a, tz_boardfile b ";
			// ������ : 05.11.17 ������ : �̳��� _(+)  ����
//          sql += " where a.seq = b.seq(+) and a.tabseq = b.tabseq(+) and a.tabseq =" + v_tabseq;
			sql += " where a.seq  =  b.seq(+) and a.tabseq  =  b.tabseq(+) and a.tabseq =" + v_tabseq;
            //sql += "    and compcd like '%" +  s_cpseq +"%' ";
            
            if(isLogin.equals("Y")){
              sql += "   and loginyn = 'Y'" ;
              if(s_gadmin.equals("M1")||s_gadmin.equals("S1")||s_gadmin.equals("T1")){
                sql += "   and compcd like '%"+s_cpseq+"%'" ;
              }
            }else{
              sql += "   and loginyn = 'N'" ;
            }
			

            if ( !v_searchtext.equals("")) {      //    �˻�� ������
                v_pageno = 1;   //      �˻��� ��� ù��° �������� �ε��ȴ�
                
               if (v_select.equals("title")) {     //    �������� �˻��Ҷ�
                    sql += " and a.adtitle like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } 
                else if (v_select.equals("content")) {     //    �������� �˻��Ҷ�
                    sql += " and a.adcontent like " + StringManager.makeSQL("%" + v_searchtext + "%");            //   Oracle 9i ��
                }
            }
            sql += " group by a.seq, a.adname, a.adtitle, a.addate, a.cnt ";
            sql += " order by a.seq desc ";  		
           
            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
           
            ls = new ListSet(pstmt);        //      ListSet (ResultSet) ��ü����

            ls.setPageSize(row);             //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno);                    //     ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage();       //     ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls.getTotalCount();    //     ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {
				
                //-------------------   2003.12.25  ����     -------------------------------------------------------------------
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
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      �� �ݾ��ش�
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }  
  
  /**
    * ���������ڷ�� �󼼺��� 
    * @param box          receive from the form object and session
    * @return DataBox	  ��ȸ�� ���� DataBox�� ��� ����
 
   public DataBox selectPds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
		
        DataBox dbox = null;
                
        int v_seq = box.getInt("p_seq");
		
        int v_upfilecnt = (box.getInt("p_upfilecnt")>0?box.getInt("p_upfilecnt"):1);

        Vector realfileVector = new Vector();
        Vector savefileVector = new Vector();
        Vector fileseqVector  = new Vector();
        
        try {
            connMgr = new DBConnectionManager();
			//----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------

			
            sql = "select a.seq, a.adname, a.adtitle, a.adcontent, b.fileseq, b.realfile, b.savefile, a.addate, a.cnt";
            sql += " from tz_notice a, tz_boardfile b";   
            sql += " where a.seq = b.seq(+) and a.tabseq = b.tabseq(+) and a.tabseq = " + v_tabseq + "and a.seq = " + v_seq;
            ls = connMgr.executeQuery(sql); 
			
            while(ls.next()) {
            //-------------------   2003.12.25  ����     -------------------------------------------------------------------
                dbox = ls.getDataBox();

                realfileVector.addElement(ls.getString("realfile"));
                savefileVector.addElement(ls.getString("savefile"));
            }   
            dbox.put("d_realfile", realfileVector);
            dbox.put("d_savefile", savefileVector);
           
           
           
            //------------------------------------------------------------------------------------------------------------------------------------
           
            connMgr.executeUpdate("update tz_notice set cnt = cnt + 1 where seq = " + v_seq );        
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
  
 */
    public DataBox selectPds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
                
        int v_seq = box.getInt("p_seq");
		
        String v_fileseq      = box.getString("p_fileseq");
        Vector realfileVector = new Vector();
        Vector savefileVector = new Vector();
        Vector fileseqVector  = new Vector();  
        int    v_upfilecnt    = (box.getInt("p_upfilecnt")>0?box.getInt("p_upfilecnt"):1);
		
        try {
            connMgr = new DBConnectionManager();
			//----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------

			
            sql = "select a.seq, a.adname, a.adtitle, a.adcontent, b.fileseq, b.realfile, b.savefile, a.addate, a.cnt " ;
            sql += " from tz_notice a, tz_boardfile b ";   
//          sql += " where a.seq = b.seq(+) and a.tabseq = b.tabseq(+) and a.tabseq = " + v_tabseq + "and a.seq = " + v_seq;
			sql += " where a.seq  =  b.seq(+) and a.tabseq  =  b.tabseq(+) and a.tabseq = " + v_tabseq + "and a.seq = " + v_seq;
            ls = connMgr.executeQuery(sql); 
       
            while(ls.next()) {

                dbox = ls.getDataBox();
                realfileVector.addElement(ls.getString("realfile"));
                savefileVector.addElement(ls.getString("savefile"));
                fileseqVector.addElement(String.valueOf(ls.getInt("fileseq")));
            }
            if (realfileVector  != null) dbox.put("d_realfile", realfileVector);
            if (savefileVector  != null) dbox.put("d_savefile", savefileVector);
            if (fileseqVector   != null) dbox.put("d_fileseq", fileseqVector);           
           
            //------------------------------------------------------------------------------------------------------------------------------------
           
            connMgr.executeUpdate("update tz_notice set cnt = cnt + 1 where seq = " + v_seq );        
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
    * ����ȭ�� �������׸���Ʈ
    * @param box          receive from the form object and session
    * @return ArrayList   �������� ����Ʈ
    */
	   
  
    public ArrayList selectNoticeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
		
        int v_pageno = box.getInt("p_pageno");
		
        try {
			
            connMgr = new DBConnectionManager();            
            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------

            list = new ArrayList();

            sql = "select a.seq, a.adname, a.adtitle, count(b.realfile) filecnt, a.addate, a.cnt ";
            sql += " from tz_Notice a, tz_boardfile b ";
//          sql += " where a.seq = b.seq(+) and a.tabseq = b.tabseq(+) and a.tabseq =" + v_tabseq;
			sql += " where a.seq  =  b.seq(+) and a.tabseq  =  b.tabseq(+) and a.tabseq = " + v_tabseq;
            sql += " group by a.seq, a.adname, a.adtitle, a.addate, a.cnt ";
            sql += " order by a.seq desc ";  		
            
            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            ls = new ListSet(pstmt);        //      ListSet (ResultSet) ��ü����
            ls.setPageSize(row);             //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno);                    //     ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage();       //     ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls.getTotalCount();    //     ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {
				
                //-------------------   2003.12.25  ����     -------------------------------------------------------------------
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));
                
                list.add(dbox);
				if (list.size() == 5){
					break;
				}            
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      �� �ݾ��ش�
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

		/**
    * ����ȭ�� �������׸���Ʈ 3������ ����Ʈ
    * @param box          receive from the form object and session
    * @return ArrayList   �������� ����Ʈ
    */
	   
  
    public ArrayList selectNoticeListNum(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
        
        String v_cpseq = box.getSession("cpseq");
        String v_gadmin = box.getSession("gadmin");
        String isLogin = "Y";
        
        if(v_cpseq.equals("") || v_cpseq == null){
          	isLogin = "N";
        }
		
        int v_pageno = box.getInt("p_pageno");
		

        try {
			
            connMgr = new DBConnectionManager();            
            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------

            list = new ArrayList();
			// ������ : 05.11.17 ������ : �̳��� _ rownum ����
			sql  = " select * from ( select rownum rnum,  seq, adname, adtitle, addate, cnt ";
            sql += "   from tz_Notice ";
            sql += "   where tabseq =" + v_tabseq;
            if(isLogin.equals("Y")){
              sql += "   and loginyn = 'Y'" ;
              if(v_gadmin.equals("M1")||v_gadmin.equals("S1")||v_gadmin.equals("T1")){
                sql += "   and compcd like '%"+v_cpseq+"%'" ;
              }
            }else{
              sql += "   and loginyn = 'N'" ;
            }
            sql += "   order by seq desc ";
			sql += " ) where rownum < 4                                                                              ";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            ls = new ListSet(pstmt);        			//      ListSet (ResultSet) ��ü����
            ls.setPageSize(row);             			//  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno);                //     ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage();   //     ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls.getTotalCount();   //     ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {
				
                //-------------------   2003.12.25  ����     -------------------------------------------------------------------
                dbox = ls.getDataBox();           
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));
                
                list.add(dbox);
				if (list.size() == 5){
					break;
				}              
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      �� �ݾ��ش�
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }
}
