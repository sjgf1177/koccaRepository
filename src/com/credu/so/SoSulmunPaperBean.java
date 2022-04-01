//**********************************************************
//1. ��      ��:
//2. ���α׷���: SoSulmunPaperBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2005.9
//7. ��      ��:
//
//**********************************************************

package com.credu.so;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;
import com.credu.so.*;



/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SoSulmunPaperBean {

    public SoSulmunPaperBean() {}

    /**
    ����Ʈ 
    @param box          receive from the form object and session
    @return ArrayList   ����Ʈ
    */
    public ArrayList selectPaperList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String v_distcode   = box.getString("p_distcode");
        int    v_levels     = box.getInt("p_levels");        
        String v_action     = box.getStringDefault("p_action",  "change"); 
        String p_distcode   = "";

        try {
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();                
                p_distcode = v_distcode.substring(0, v_levels*3);
                list = getPaperList(connMgr, p_distcode);
            } else {
                list = new ArrayList();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    /**
    @param box          receive from the form object and session
    @return ArrayList   ����������Ʈ
    */
    public ArrayList getPaperList(DBConnectionManager connMgr, String p_sodistcode) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        try {
           
            sql = " select sulpapernum, totcnt, sulnums, sulstart, sulend,luserid, ldate , ";
            sql+= " sulpapernm, sodistcode  ";
            sql+= " from   tz_sosulpaper      ";
            sql+= " where  sodistcode like '"+p_sodistcode+"%' ";                   

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
        }
        return list;
    }


//select * from tz_sosul;
//select * from tz_sosulsel;
//select * from TZ_sosulpaper;

    /**
    SO��  ����Ʈ 
    @param box          receive from the form object and session
    @return ArrayList   �򰡹��� ����Ʈ
    */
    public ArrayList selectExamLevels(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = new ArrayList();
        ListSet ls = null;
        DataBox dbox    = null;
        String p_distcode   = "";
        String sql   = "";

        String v_action     = box.getString("p_action");                                
        String v_distcode   = box.getString("p_distcode");
        int    v_levels     = box.getInt("p_levels");        

        // �˻�
        String ss_levels    = box.getString("ss_levels");  
        String ss_type      = box.getString("ss_type");  
                
        try {
            connMgr = new DBConnectionManager();
                        
            sql = " select a.distcode, a.sulnum, a.sultype, a.sultext,  a.scalecode,    ";
            sql+= "       b.codenm    sultypenm    ";
            sql+= " from tz_sosul    a, ";
            sql+= "      tz_code     b  ";
            sql+= " where a.sultype   = b.code ";
            sql+= "   and b.gubun        = " + SQLString.Format(SoBean.SULMUN_TYPE);


            //if(v_action.equals("go")){
            //    sql += " and a.levels like " + SQLString.Format(ss_levels);
            //    sql += " and a.soexamtype like " + SQLString.Format(ss_type);            
            //}
                       
            sql+= " order by a.sulnum ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }            
            if (connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }



    
    /**
    @param box          receive from the form object and session
    @return ArrayList   �������� ����Ʈ
    */
    public ArrayList selectQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;
      
        try {
            connMgr = new DBConnectionManager();

            sql = "select a.sulnum, a.distcode, a.sultype, a.sultext,a.selcount, a.scalecode, a.sodistcode, ";
            sql+= "       b.codenm  sultypenm   ";
            sql+= "  from tz_sosul    a, ";
            sql+= "       tz_code   b    ";
            sql+= "   where a.sultype  = b.code ";
			sql+= "   and b.gubun        = " + SQLString.Format(SoBean.SULMUN_TYPE);
            sql+= "   and b.levels    =  1 ";
            sql+= " order by a.sulnum ";
            
System.out.println(sql);                        
            ls = connMgr.executeQuery(sql);

			while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }            
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }



	/**
	���� ������ ���
	@param  connMgr
	@param  p_grcode       �����׷�
	@param  p_subj         ����
	@param  p_sulpapernum  ��������ȣ
	@param  p_sulpapernm   �������̸�			 	
	@param  p_totcnt	   ��ü������	
	@param  p_sulnums	   ������ȣ
	@param  p_luserid	   �ۼ���				
	@return isOk    
    @param box                receive from the form object and session
    @return int  
    */
    public int insertSoSulmunPaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        int v_totcnt          = box.getInt   ("p_totcnt");                    
        String v_sulnums      = box.getString("p_sulnums"); 
        String v_sulstart     = box.getString("p_sulstart"); 
        String v_sulend       = box.getString("p_sulend"); 
        String v_luserid      = box.getSession("luserid");   
        String v_sulpapernm   = box.getString("p_sulpapernm");   
        String v_sodistcode   = box.getString("p_sodistcode");       

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            sql =  "insert into TZ_SOSULPAPER ";
            sql+=  "( sulpapernum, totcnt, sulnums, sulstart, sulend, luserid, ldate, sulpapernm, sodistcode )   ";
            sql+=  " values ";
            sql+=  "( (select NVL(max(sulpapernum)+1,1) from TZ_SOSULPAPER) , ?, ?,  ?,  ?,  ?,  ?, ?, ? ) ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setInt   ( 1, v_totcnt  );
            pstmt.setString( 2, v_sulnums );
            pstmt.setString( 3, v_sulstart);
            pstmt.setString( 4, v_sulend  );
            pstmt.setString( 5, v_luserid );            
            pstmt.setString( 6, FormatDate.getDate("yyyyMMddHHmmss"));            
            pstmt.setString( 7, v_sulpapernm);
            pstmt.setString( 8, v_sodistcode);
            isOk = pstmt.executeUpdate();
                       
        }
        catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (isOk > 0) {connMgr.commit();}
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }  
    
}