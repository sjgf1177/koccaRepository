//**********************************************************
//1. ��      ��:
//2. ���α׷���: CommunityFrInvitationBean.java
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
public class CommunityFrInvitationBean {
    private ConfigSet config;
    private static int row=10;
    private String v_type = "PQ";
    private static final String FILE_TYPE = "p_file";           //      ���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 1;                    //    �������� ���õ� ����÷�� ����


    public void CommunityFrInvitationBean() {
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
    * ȸ���ʴ� ��������
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int sendInvitation(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";
        int         isOk        = 1;
        int         v_seq       = 0;

        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");
        String v_title         = box.getString("p_title");
        String v_intro     	   = StringManager.replace(box.getString("content"),"<br>","\n");

        Vector  v_p_right_userid      = box.getVector("p_right_userid"  );
        Vector  v_p_right_name        = box.getVector("p_right_name"    );
        Vector  v_p_right_email       = box.getVector("p_right_email"   );
        Vector  v_p_right_jikwinm     = box.getVector("p_right_jikwinm" );
        Vector  v_p_right_deptnam     = box.getVector("p_right_deptnam" );
        Vector  v_p_right_jikupnm     = box.getVector("p_right_jikupnm" );

        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        try {
        	connMgr = new DBConnectionManager();
           	connMgr.setAutoCommit(false); 

           	//�Ϸù�ȣ ���ϱ�
           	int v_mailno=0;
           	sql1 = "select isnull(max(MAILNO), 0)   from TZ_CMUMAIL ";
           	ls = connMgr.executeQuery(sql1);
           	while (ls.next()) v_mailno = ls.getInt(1);
           
           	if(ls != null) { try { ls.close(); } catch (Exception e) {} }

           	sql  =" insert into TZ_CMUMAIL ( mailno, userid, kor_nm, recv_email"
                  +"                       ,cmuno, cmu_nm, SEND_USERID,send_email, title, content"
                  +"                       ,loc_fg,loc_nm,regster_dte, send_fg)"
                  +"               values  (?,?,?,?"
//                  +"                       ,?,?,?,?,?,empty_clob()"
                  +"                       ,?,?,?,?,?,?"
                  +"                       ,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N')"
                  ;
             pstmt = connMgr.prepareStatement(sql);

             for(int i=0;i<v_p_right_userid.size();i++){
            
               //�����ڸ��ϱ�
               String v_tmp_nm="";
               sql1 = "select name   from tz_member where userid = '"+(String)v_p_right_userid.elementAt(i)+"' ";
               System.out.println(sql1);
               ls = connMgr.executeQuery(sql1);
               while (ls.next()) v_tmp_nm = ls.getString(1);

               //Ŀ�´�Ƽ���ϱ�
               String v_tmp_cmu_nm="";
               sql1 = "select cmu_nm   from tz_cmubasemst where cmuno = '"+v_cmuno+"' ";
               System.out.println(sql1);
               ls = connMgr.executeQuery(sql1);
               while (ls.next()) v_tmp_cmu_nm = ls.getString(1);
               
               if(ls != null) { try { ls.close(); } catch (Exception e) {} }

               //�߽��� �̸���
               String v_tmp_send_email="";
               sql1 = "select email   from tz_member where userid = '"+s_userid+"' ";
               System.out.println(sql1);
               ls = connMgr.executeQuery(sql1);
               while (ls.next()) v_tmp_send_email = ls.getString(1);
               
               if(ls != null) { try { ls.close(); } catch (Exception e) {} }
int index = 1;
               v_mailno =v_mailno+1;
               pstmt.setInt   (index++, v_mailno                                );//�Ϸù�ȣ
               pstmt.setString(index++, (String)v_p_right_userid.elementAt(i)   );//�����ھ��̵�
               pstmt.setString(index++, v_tmp_nm                                );//�����ڸ�
               pstmt.setString(index++, (String)v_p_right_email.elementAt(i)    );//�������̸���
               pstmt.setString(index++, v_cmuno                                 );//Ŀ�´�Ƽ��ȣ
               pstmt.setString(index++, v_tmp_cmu_nm                            );//Ŀ�´�Ƽ��
               pstmt.setString(index++ ,s_userid                                );//�߽��ھ��̵�
               pstmt.setString(index++ ,v_tmp_send_email                        );//�߽����̸���
               pstmt.setString(index++ , v_title                                );//����
pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
//			   pstmt.setString(index++, v_intro						);
               pstmt.setString(index++, "1"                                    );//����
               pstmt.setString(index++, "�ʴ�޼���"                           );//���и�
               isOk = pstmt.executeUpdate();

//			   sql1 = "select content  from TZ_CMUMAIL where mailno = '"+v_mailno+"'";
//               connMgr.setOracleCLOB(sql1, v_intro);
               if(isOk > 0 ) {
                   if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
               }
           }

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
