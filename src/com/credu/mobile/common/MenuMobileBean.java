package com.credu.mobile.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * ������Ʈ�� : kocca_java
 * ��Ű���� : com.credu.mobile.common
 * ���ϸ� : MenuMobileBean.java
 * �ۼ���¥ : 2011. 9. 26.
 * ó������ : 
 * �������� : 
 
 * Copyright by CREDU.Co., LTD. ALL RIGHTS RESERVED.    
 */

public class MenuMobileBean 
{
	public MenuMobileBean(){}
	
	private StringBuffer strQuery = null;
	private int x = 1; 
	
	/**
	 * ���θ� �� �޴� ���� ��������
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public DataBox getMenuUserData(RequestBox box) throws Exception
	{
		DBConnectionManager connMgr         = null;
        PreparedStatement 	pstmt = null;
        ListSet ls = null;
        
        DataBox dbox = null;
        
        try
        {
        	connMgr = new DBConnectionManager();
        	
        	strQuery = new StringBuffer();
        	strQuery.append("select \n");
        	strQuery.append("    a.menuid, a.depth1, a.depth2, a.depth3, a.depth4, a.menunm, a.menuimg, a.pgmpath,  \n");
        	strQuery.append("    a.para, a.distcode, a.popup, a.display, a.levels, a.etc, \n");
        	strQuery.append("    decode(a.levels, 1, '', (select menunm from tz_menu_mobile s1 where s1.depth1 = a.depth1 and s1.levels = 1 and rownum = 1)) depth1nm,  \n");
        	strQuery.append("    decode(a.levels, 2, '', (select menunm from tz_menu_mobile s1 where s1.depth1 = a.depth1 and s1.depth2 = a.depth2 and s1.levels = 2 and rownum = 1)) depth2nm,  \n");
        	strQuery.append("    decode(a.levels, 3, '', (select menunm from tz_menu_mobile s1 where s1.depth1 = a.depth1 and s1.depth2 = a.depth2 and s1.depth3 = a.depth3 and s1.levels = 3 and rownum = 1)) depth3nm,  \n");
        	strQuery.append("    decode(a.levels, 4, '', (select menunm from tz_menu_mobile s1 where s1.depth1 = a.depth1 and s1.depth2 = a.depth2 and s1.depth3 = a.depth3 and s1.depth4 = a.depth4 and s1.levels = 4 and rownum = 1)) depth4nm  \n");
        	strQuery.append("from \n");
        	strQuery.append("    tz_menu_mobile a \n");
        	strQuery.append("where \n");
        	strQuery.append("   a.menuid = ?  \n");
        	
        	pstmt = connMgr.prepareStatement(strQuery.toString());
        	pstmt.setString(x++, box.getString("p_menuid"));
        	ls = new ListSet(pstmt);
			ls.executeQuery();
			
			if(ls.next())
				dbox = ls.getDataBox();	
			
        }
        catch ( SQLException e ) 
    	{
            ErrorManager.getErrorStackTrace(e, box, strQuery.toString());
            throw new Exception("\n SQL : [\n" + strQuery.toString() + "]\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } 
    	catch ( Exception e ) 
    	{
    		
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } 
    	finally 
    	{
       	 	if ( pstmt != null ) { try { pstmt.close(); pstmt = null;} catch ( Exception e ){}}
            if ( ls != null ) { try { ls.close(); ls = null;} catch ( Exception e ) {}}
            if ( connMgr != null ) { try { connMgr.freeConnection(); connMgr = null;} catch ( Exception e ) {}}
            
        }
    	
    	return dbox;
	}
	
	/**
	 * ����� �޴� ����
	 * @param box
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public ArrayList getUserMenuList(RequestBox box) throws Exception
	{
		DBConnectionManager connMgr         = null;
        PreparedStatement 	pstmt = null;
        ListSet ls = null;
        
        ArrayList list = new ArrayList();
        DataBox dbox = null;
        
        try
        {
        	connMgr = new DBConnectionManager();
        	
        	strQuery = new StringBuffer();
        	strQuery.append("select  \n");
        	strQuery.append("    a.menuid, a.menunm, a.pgmpath, a.distcode, a.levels, a.login \n");
        	strQuery.append("from  \n");
        	strQuery.append("    tz_menu_mobile a \n");
        	strQuery.append("where \n");
        	strQuery.append("    a.display = 'Y' \n");
        	strQuery.append("order by a.orders \n");

        	pstmt = connMgr.prepareStatement(strQuery.toString());
        	ls = new ListSet(pstmt);
			ls.executeQuery();
			
			while(ls.next())
			{
				dbox = ls.getDataBox();	
				list.add(dbox);
			}
			
        }
        catch ( SQLException e ) 
    	{
            ErrorManager.getErrorStackTrace(e, box, strQuery.toString());
            throw new Exception("\n SQL : [\n" + strQuery.toString() + "]\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } 
    	catch ( Exception e ) 
    	{
    		
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } 
    	finally 
    	{
       	 	if ( pstmt != null ) { try { pstmt.close(); pstmt = null;} catch ( Exception e ){}}
            if ( ls != null ) { try { ls.close(); ls = null;} catch ( Exception e ) {}}
            if ( connMgr != null ) { try { connMgr.freeConnection(); connMgr = null;} catch ( Exception e ) {}}
            
        }
    	
    	return list;
	}
	
	/**
	 * �޴�id�� distcode ã�Ƴ���
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public DataBox getMenuDistCodeData(RequestBox box) throws Exception
	{
		DBConnectionManager connMgr         = null;
        PreparedStatement 	pstmt = null;
        ListSet ls = null;
        
        DataBox dbox = null;
        
        try
        {
        	connMgr = new DBConnectionManager();
        	
        	strQuery = new StringBuffer();
        	strQuery.append("select \n");
        	strQuery.append("    a.menuid, a.distcode, a.levels  \n");
        	strQuery.append("from \n");
        	strQuery.append("    tz_menu_mobile a \n");
        	strQuery.append("where \n");
        	strQuery.append("   a.menuid = ?  \n");
        	
        	pstmt = connMgr.prepareStatement(strQuery.toString());
        	pstmt.setString(x++, box.getString("p_menuid"));
        	ls = new ListSet(pstmt);
			ls.executeQuery();
			
			if(ls.next())
				dbox = ls.getDataBox();	
			
        }
        catch ( SQLException e ) 
    	{
            ErrorManager.getErrorStackTrace(e, box, strQuery.toString());
            throw new Exception("\n SQL : [\n" + strQuery.toString() + "]\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } 
    	catch ( Exception e ) 
    	{
    		
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } 
    	finally 
    	{
       	 	if ( pstmt != null ) { try { pstmt.close(); pstmt = null;} catch ( Exception e ){}}
            if ( ls != null ) { try { ls.close(); ls = null;} catch ( Exception e ) {}}
            if ( connMgr != null ) { try { connMgr.freeConnection(); connMgr = null;} catch ( Exception e ) {}}
            
        }
    	
    	return dbox;
	}
}
