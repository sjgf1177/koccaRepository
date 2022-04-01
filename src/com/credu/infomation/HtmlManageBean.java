//**********************************************************
//  1. ��      ��: �����������ø� ����
//  2. ���α׷��� : NoticeTempletBean.java
//  3. ��      ��: �����������ø� ����
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0��������
//  6. ��      ��: ������ 2005. 7.  14
//  7. ��      ��:
//**********************************************************
package com.credu.infomation;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.dunet.common.util.StringUtil;
import com.namo.active.NamoMime;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class HtmlManageBean {
    private ConfigSet config;
    private int row;

    public HtmlManageBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.manage.row"));  //�� ����� �������� row ���� �����Ѵ�
            row = 10; //������ ����
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * HTML ���� ����Ʈ
    * @param box          receive from the form object and session
    * @return ArrayList   �����������ø� ����Ʈ
    * @throws Exception
    */
    public ArrayList selectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
		
        String sql = "";
		
        DataBox dbox        = null;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " SELECT  SEQ, TITLE, INDATE, USERID, CODE ";
		    sql += " FROM    TZ_HTMLMANAGE ";
		    sql += " ORDER BY CODE ASC ";

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


    /**
    * HTML ���� �󼼺���
    * @param box          receive from the form object and session
    * @return ArrayList   ��ȸ�� ������
    * @throws Exception
    */
   public DataBox selectView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox        = null;

        int v_seq = box.getInt("p_seq");
        String v_process = box.getString("p_process");
        String v_code = box.getString("p_code");

        try {
            connMgr = new DBConnectionManager();

            sql.append(" SELECT  SEQ, TITLE, INDATE, USERID, CONTENT, CODE  \n");
            sql.append(" FROM    TZ_HTMLMANAGE                    \n");
            
            if(v_process.equals("selectView") ||v_process.equals("updatePage")) sql.append(" WHERE   SEQ    = " + v_seq);
            else sql.append(" WHERE   CODE   = " + StringManager.makeSQL(v_code));
            //sql.append(" WHERE   SEQ    = " + v_seq);

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                dbox = ls.getDataBox();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }
   


    /**
    * HTML ���� ����Ҷ�
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int insert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer insertSql  = new StringBuffer();
        String sql = "";
        int isOk  = 0;
        int v_seq = 0;

        String v_title    = StringUtil.removeTag(box.getString("p_title"));
        String v_content  = StringUtil.removeTag(box.getString("p_content"));
        String s_userid   = box.getSession("userid");
        String v_code     = box.getString("p_code");

        try {
           connMgr = new DBConnectionManager();

           sql  = "select max(seq) from TZ_HTMLMANAGE  ";
           ls = connMgr.executeQuery(sql);
           if (ls.next()) {
               v_seq = ls.getInt(1) + 1;
           } else {
               v_seq = 1;
           }



    	   /*********************************************************************************************/
    	   // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�. 
    	   //try {
    		//
    	   //} catch(Exception e) {
    		//
    		//
    	   //}
    	   /*********************************************************************************************/ 

            insertSql.append(" INSERT INTO TZ_HTMLMANAGE                    \n ");
            insertSql.append(" (                                            \n ");
            insertSql.append("     SEQ, TITLE, CONTENT, CODE, USERID        \n ");
            insertSql.append("     , LUSERID, INDATE, LDATE                 \n ");
            insertSql.append(" )                                            \n ");
            insertSql.append(" VALUES                                       \n ");
            insertSql.append(" (                                            \n ");
            insertSql.append("     ?, ?, ?, ?, ?, ?                         \n ");
            insertSql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')   \n ");
            insertSql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')   \n ");
            insertSql.append(" )                                            \n ");

           pstmt = connMgr.prepareStatement(insertSql.toString());

           pstmt.setInt   (1, v_seq);
           pstmt.setString(2, v_title);
           pstmt.setString(3, v_content);
           pstmt.setString(4, v_code);
           pstmt.setString(5, s_userid);
           pstmt.setString(6, s_userid);

           isOk = pstmt.executeUpdate();

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, insertSql.toString());
            throw new Exception("sql ->" + insertSql.toString() + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    * HTML ���� �����Ͽ� �����Ҷ�
    * @param box      receive from the form object and session
    * @return isOk    1:update success,0:update fail
    * @throws Exception
    */
     public int update(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer updateSql = new StringBuffer();
        int isOk = 0;

        int v_seq         	= box.getInt("p_seq");
        String v_title  	= StringUtil.removeTag(box.getString("p_title"));
        String v_content 	= StringUtil.removeTag(box.getString("p_content"));
        String v_code    	= box.getString("p_code");

        String s_userid   = box.getSession("userid");

        try {


     	   /*********************************************************************************************/
     	   // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�. 
     	   //try {
     		//
     	   //} catch(Exception e) {
     		//
     		//
     	   //}
     	   /*********************************************************************************************/ 

            connMgr = new DBConnectionManager();

            updateSql.append(" UPDATE TZ_HTMLMANAGE SET                                \n ");
            updateSql.append("     TITLE       = ? , CONTENT  = ?                      \n ");
            updateSql.append("     , LUSERID   = ? , CODE	  = ?                      \n ");
            updateSql.append("     , LDATE     = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')  \n ");
            updateSql.append(" WHERE                                                   \n ");
            updateSql.append("     SEQ     = ?                                         \n ");


            pstmt = connMgr.prepareStatement(updateSql.toString());

            pstmt.setString(1, v_title);
            pstmt.setString(2, v_content);
            pstmt.setString(3, s_userid);
            pstmt.setString(4, v_code);
            pstmt.setInt   (5, v_seq);

            isOk = pstmt.executeUpdate();

        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, updateSql.toString());
            throw new Exception("sql = " + updateSql.toString() + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    * HTML ���� �����Ҷ�
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */
    public int delete(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        int v_seq  	= box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();

            sql  = " delete from TZ_HTMLMANAGE   ";
            sql += "   where seq = ?  ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, v_seq);
            
            isOk = pstmt.executeUpdate();
            
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql+ "\r\n");
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    * HTML ���� ����Ʈ
    * @param box          receive from the form object and session
    * @return ArrayList   �����������ø� ����Ʈ
    * @throws Exception
    */
    public ArrayList selectAll() throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox        = null;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql  = " select seq, addate, adtitle, adname, templetfile, cnt, luserid, ldate from TZ_NOTICE_TEMPLET ";
            sql += " order by seq desc ";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        }
        catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }


    /**
    * �����ۼ�
    * @param  path       ����  �н�+�̸�
    * @param  contents   ������ ����
    * @throws Exception
    */
    public void write(String path, String contents) throws Exception {
        File file = null;

        try {
           file = new File(path);
           FileWriter fw = new FileWriter(file);
           BufferedWriter owriter = new BufferedWriter( fw );
           owriter.write(contents);
           owriter.flush();
           owriter.close();
           fw.close();
        } catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * ���� ������ �����Ѵ�
     * @param path        ������ ������ �н�+���ϸ�
     * @throws Exception
     */
    public void delete(String path) throws Exception
    {
        try {
            File file = new File(path);
            file.delete();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * ������ �о ������ �����Ѵ�.
     * @param sPath          ������ �н�+���ϸ�
     * @return result         ���� ������ ��� �ִ� ��Ʈ�� ��ü
     * @throws Exception
     */
    public String read(String path) throws Exception
    {
        String result = "";
        try {
            FileReader fr = new FileReader(path);
            BufferedReader reader = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            int len = 4096; // 4k
            char[] buff = new char[len];
            while (true)
            {
                int rsize = reader.read(buff, 0, len);
                if (rsize < 0)
                {
                    break;
                }
                sb.append(buff, 0, rsize);
            }
            buff = null;
            result = sb.toString();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }

        return result;
    }
    
    /**
     * HTML ���� ����Ʈ
     * @param box          receive from the form object and session
     * @return ArrayList   �����������ø� ����Ʈ
     * @throws Exception
     */
     public ArrayList selectTemplet(String v_gubun) throws Exception {
         DBConnectionManager connMgr = null;
         ListSet ls = null;
         ArrayList list = null;
 		// ������ : 05.11.23 ������ : �̳��� _ totalcount �ϱ����� ��������
 		StringBuffer bodySql = new StringBuffer();
 		
         DataBox dbox        = null;

         try {
             connMgr = new DBConnectionManager();
             list = new ArrayList();

            bodySql.append(" SELECT  A.ADTITLE, A.ADNAME, A.TEMPLETFILE     \n ");
 		    bodySql.append(" FROM    TZ_NOTICE_TEMPLET A                    \n ");
           	bodySql.append(" WHERE   GUBUN = " + StringManager.makeSQL(v_gubun) + " \n ");
             
 			
 			 ls = connMgr.executeQuery(bodySql.toString());
             
             while (ls.next()) {
                 dbox = ls.getDataBox();
                 list.add(dbox);
             }
         }
         catch (Exception ex) {
                ErrorManager.getErrorStackTrace(ex, null, bodySql.toString());
             throw new Exception("sql = " + bodySql.toString() + "\r\n" + ex.getMessage());
         }
         finally {
             if(ls != null) { try { ls.close(); }catch (Exception e) {} }
             if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
         }
         return list;
     }

}

