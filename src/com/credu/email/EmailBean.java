package com.credu.email;

import java.sql.Date;
import java.sql.*;
import java.util.ArrayList;

import java.util.Hashtable;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.email.*;


public class EmailBean {
	
	public static String SINGLE_CLASS = "1";
    public static String PLURAL_CLASS = "2";

    public static String CLASSNM = "클래스";
    public static String SINGLE_CLASS_CODE = "0001";

    public EmailBean() {}
    
    public ArrayList SelectEmailList(RequestBox box) throws Exception{
    	
    	ArrayList list = new ArrayList();
    	
    	DBConnectionManager connMgr = null;
    	ListSet ls = null;
    	String sql = "";
    	EmailData  data = null;
    	Hashtable classinfo = null;
    	    	
    	String e_className = box.getStringDefault("className", "ALL"); // 주제별 정렬
    	
    	try{
    		sql = "select seq, userId, className, to_char(sysdate, 'YYYY-MM-DD) as date from tz_email order by seq";
    		
    		if(!e_className.equals("ALL")) sql += " and className = "+ SQLString.Format(e_className);
    		
    		System.out.println("class sql = "+sql);
    		
    		connMgr = new DBConnectionManager();    		   		
    		ls = connMgr.executeQuery(sql);
    		int seq =0;
    		String userId, className, requestDate;
    		userId = className = requestDate =null;
    		while (ls.next()){
    			System.out.println("while 1; : ");
    			
    			data = new EmailData();
    			
    			seq = ls.getInt(1);    			
    			userId = ls.getString(2);    			
    			className = ls.getString(3);
    			requestDate = ls.getString(4);
    			data.setSeq(seq);
    			data.setUserId(userId);
    			data.setClassname(className);
    			data.setRequestDate(requestDate);
    			
//    			
    			System.out.println("setRequestDate "+data.getRequestDate());
    			list.add(data);   			
    		}
    	}catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, box, sql);
    		throw new Exception("sql = "+sql+"\r\n"+ex.getMessage());
		}finally{
				if (ls != null) {try{ls.close();}catch (Exception e) {} }
				if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
			}
		return list;
    	
    }

	/**
	이벤트신청등록
	@param box      receive from the form object
	@return int
	*/
    public int InsertReturn(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        int isOk = 0;

        String v_userId      = box.getString("userId");
        String v_className      = box.getString("className");
        System.out.println("v_userId:"+v_userId);
        System.out.println("v_className:"+v_className);
        
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

			isOk = InsertTZ_EMAIL(connMgr, v_userId, v_className);

        }
        catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            connMgr.commit();
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }



	/**
	이벤트신청 등록 처리
	@param box      receive from the form object
	@return int
	*/
    public int InsertTZ_EMAIL(DBConnectionManager connMgr,
                                String p_userId,     String p_className) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_EXAMRESULT table
            sql =  " insert into TZ_EMAIL ";
            sql+=  "  (userId, className, requestDate) ";
            sql+=  " values ";
            sql+=  " (?,         ?,         sysdate) ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString( 1, p_userId);
            pstmt.setString( 2, p_className);

            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
        }
        return isOk;
    }
}
