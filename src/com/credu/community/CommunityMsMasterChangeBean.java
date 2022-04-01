//**********************************************************
//1. ��      ��:
//2. ���α׷���: CommunityMsMasterChangeBean.java
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
public class CommunityMsMasterChangeBean {
    private ConfigSet config;
    private static int row=10;
    private String v_type = "PQ";
    private static final String FILE_TYPE = "p_file";           //      ���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 1;                    //    �������� ���õ� ����÷�� ����


    public void CommunityMsMasterChangeBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));  //�� ����� �������� row ���� �����Ѵ�
            row = 10; //������ ����
            System.out.println("....... row.....:"+row);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
    * Ŀ�´�Ƽ ����Ÿ����
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int updateMasterChange(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";
        int         isOk        = 0;
        int         v_seq       = 0;

        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");

        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        try {
           connMgr = new DBConnectionManager();
          connMgr.setAutoCommit(false); 

           sql  =" update tz_cmuusermst set  grade               =?   "
                +"                          ,modifier_dte        =to_char(sysdate, 'YYYYMMDDHH24MISS')"
                +"                where cmuno  = ?"
                +"                  and userid = ?"
                ;
           pstmt = connMgr.prepareStatement(sql);
           pstmt.setString(1, "02");
           pstmt.setString(2, v_cmuno );
           pstmt.setString(3, s_userid) ;
           isOk = pstmt.executeUpdate();
if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }

           sql  =" update tz_cmuusermst set  grade               =?   "
                +"                          ,modifier_dte        =to_char(sysdate, 'YYYYMMDDHH24MISS')"
                +"                where cmuno  = ?"
                +"                  and userid = ?"
                ;
           pstmt = connMgr.prepareStatement(sql);
           pstmt.setString(1, "01");
           pstmt.setString(2, v_cmuno );
           pstmt.setString(3, box.getString("p_tmp_userid")) ;
           isOk = pstmt.executeUpdate();
if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
           if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


}
