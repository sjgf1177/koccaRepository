//**********************************************************
//1. ��      ��:
//2. ���α׷���: CommunityFrBoardBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-29
//7. ��      ��:
//
//**********************************************************

package com.credu.community;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;
import com.credu.community.*;
import com.namo.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommunityMultiAttachBean {
    private ConfigSet config;
    private static int row=10;
    private String v_type = "PQ";
    private static final String FILE_TYPE = "p_file";           //      ���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 1;                    //    �������� ���õ� ����÷�� ����


    public void CommunityFrBoardBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));  //�� ����� �������� row ���� �����Ѵ�
            row = 10; //������ ����
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
     
     /**
      * ÷������ ����Ʈ
      * @param box          receive from the form object and session
      * @return ArrayList   ÷������ ����Ʈ
      * @throws Exception
      */
      public DataBox selectList(RequestBox box) throws Exception {
    	  System.out.println("selectList ����");
          DBConnectionManager connMgr = null;
          ListSet             ls      = null;
          StringBuffer sql    	   		= new StringBuffer();
  		
          DataBox             dbox    = null;
          
          String v_cmuno         = box.getString("p_cmuno");
          String v_brdno         = box.getString("p_brdno");
          String v_menuno        = box.getString("p_menuno");
          
          Vector realfileVector = new Vector();
  		  Vector savefileVector = new Vector();
  		  Vector fileseqVector  = new Vector();

          try {
              connMgr = new DBConnectionManager();

              sql.append(" select  a.cmuno, a.menuno, a.brdno, a.fileno              \n ");
              sql.append("         , a.realfile, a.savefile                         \n ");
              sql.append("   from tz_cmuboardfile a         \n ");
              sql.append("  where a.cmuno           = '").append(v_cmuno ).append("' \n ");
              sql.append("    and a.menuno          = '").append(v_menuno).append("' \n ");
              sql.append("    and a.brdno           = '").append(v_brdno ).append("' \n ");
              sql.append("  order by a.fileno desc");                                             


  			ls = connMgr.executeQuery(sql.toString());		
  			
              while (ls.next()) {
                  dbox = ls.getDataBox();
                  realfileVector.addElement(dbox.getString("d_realfile"));
	              savefileVector.addElement(dbox.getString("d_savefile"));
	              fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileno")));
              }
              
              if (realfileVector.size() > 0) dbox.put("d_realfileVector", realfileVector);
  			  if (savefileVector.size() > 0) dbox.put("d_savefileVector", savefileVector);
  			  if (fileseqVector.size()  > 0) dbox.put("d_filenoVector", fileseqVector);
          }
          catch (Exception ex) {
                 ErrorManager.getErrorStackTrace(ex, null, sql.toString());
              throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
          }
          finally {
              if(ls != null) { try { ls.close(); }catch (Exception e) {} }
              if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
          }
          System.out.println("selectList ����");
          return dbox;
      }
      
       /**
        * ÷�����ϻ����ϱ�
        * @param box      receive from the form object and session
        * @return isOk    1:insert success,0:insert fail
        * @throws Exception
        */
        public int delete(RequestBox box) throws Exception {
        	DBConnectionManager connMgr = null;
            PreparedStatement   pstmt 	= null;
            ListSet     		ls     	= null;
            StringBuffer      	sql     = new StringBuffer();

            int		isOk        = 0;

            String v_cmuno          = box.getString("p_cmuno");
            String v_menuno         = box.getString("p_menuno");
            String v_brdno          = box.getString("p_brdno");
            int    v_fileno         = box.getInt("p_fileno"); 
            String v_savefile		= box.getString("p_savefile");

            try {
               connMgr = new DBConnectionManager();
               connMgr.setAutoCommit(false); 

               sql.append(" DELETE FROM    TZ_CMUBOARDFILE                   \n ");
               sql.append(" WHERE   CMUNO   = '").append(v_cmuno ).append("' \n ");
               sql.append(" AND     MENUNO  = '").append(v_menuno).append("' \n ");
               sql.append(" AND     BRDNO   = '").append(v_brdno ).append("' \n ");
               sql.append(" AND     FILENO  =  ").append(v_fileno );                 

                pstmt = connMgr.prepareStatement(sql.toString());
                isOk = pstmt.executeUpdate();
                if(isOk > 0) {
                	FileManager.deleteFile(v_savefile);			//	 ÷������ ����
                    if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
                }
            }
            catch (Exception ex) {
                ErrorManager.getErrorStackTrace(ex, box, sql.toString());
                throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
            }
            finally {
                if(ls != null) { try { ls.close(); } catch (Exception e) {} }
                if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
                if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            }
            return isOk;
        }
}
