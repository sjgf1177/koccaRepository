//***********************************++++++++++++++++++***********************
//1. ��      ��: Ŀ�´�Ƽ ����
//2. ���α׷���: CommunityCreateBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-29
//7. ��      ��: �̳��� 05.11.22 _ Ŀ�´�Ƽ ���� 
//
//**********************************************************

package com.credu.community;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.common.GetCodenm;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.dunet.common.util.Constants;
import com.dunet.common.util.EncryptUtil;
import com.dunet.common.util.StringUtil;
import com.dunet.common.util.UploadUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommunityCreateBean {
    private ConfigSet config;
    private int row;

    public CommunityCreateBean() {
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
 Ŀ�´�Ƽ �з� ��ȸ
 @param gubun        �ڵ屸��
 @return ArrayList   Ŀ�´�Ƽ �з�����Ʈ
 */     
    public ArrayList selectCodeType_L(String gubun) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = new ArrayList();
        ListSet   ls   = null;
        String    sql  = "";
        DataBox dbox = null;
        try {
            connMgr = new DBConnectionManager();
            sql = " select gubun, levels, code, codenm, upper, parent, luserid, ldate"
                 +"   from tz_code "
                 +"  where gubun = "+ SQLString.Format(gubun)
                 ;
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
			//2005.12.01_�ϰ��� : conn ���� ����
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    /**
    * Ŀ�´�Ƽ Į������
    * @param  box          receive from the form object and session
    * @return ArrayList   Ŀ�´�Ƽ �޴�����
    * @throws Exception
    */
    public String getSingleColumn(String v_cmuno,String v_column) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();
        String              sql     = "";
        DataBox             dbox    = null;

        String              v_ret   ="";
        try {
            connMgr = new DBConnectionManager();

            sql  = "\n select "+v_column
                  +"\n   from tz_cmubasemst"
                  +"\n  where cmuno        = '"+v_cmuno     +"'" ;
            System.out.println(sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_ret = ls.getString(1);
            }
        }
        catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return v_ret;
    }

 /**
 Ŀ�´�Ƽ�� �ߺ�üũ ��ȸ
 @param box     receive from the form object and session
 @return int    row count
 */     
    public int selectCmuNmRowCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = new ArrayList();
        ListSet   ls   = null;
        String    sql  = "";
	    String  s_grcode	 = box.getSession("tem_grcode");
		String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);
		
        int       iRowCnt=0;
        try {
            connMgr = new DBConnectionManager();
            sql = " select count(*) rowcnt"
                 +"   from tz_cmubasemst "
                 +"  where cmu_nm = "+ SQLString.Format(box.getStringDefault("p_cmu_nm",""));
                // +"		and grtype ="+ SQLString.Format(s_grtype);
					
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                  iRowCnt = ls.getInt("rowcnt");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
			//2005.12.01_�ϰ��� : conn ���� ����
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return iRowCnt;
    }

    /**
    * Ŀ�´�Ƽ �����
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int insertBaseMst(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt   = null;
        ListSet     	ls          = null;
        StringBuffer    sql         = new StringBuffer();
        String      	createCmuno = "";
        int         	isOk        = 0;
        int         	isOk1        = 0;
        int         	isOk2        = 0;
        int         	isOk3        = 0;
        int         	isOk4        = 0;
        

        String v_intro 		= StringUtil.removeTag(box.getString("p_content"));
        String s_userid    	= box.getSession("userid");

	    String  s_grcode	= box.getSession("tem_grcode");
		//String  s_grtype	= GetCodenm.get_grtype(box,s_grcode);

        String v_grcode ="";
        String thisYear="";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            
            thisYear = FormatDate.getDate("yyyyMMdd").substring(0,4);
		    
		    // ������ : 05.11.23 ������ : �̳��� _ �������ǹ����� ��¦ ���� (Ŀ�´�Ƽ ������ȣ)
		    createCmuno = thisYear+"000000";
            
            sql.append("select isnull(max(cmuno),'"+thisYear+"000000') from tz_cmubasemst where cmuno like  '"+thisYear+"%'");
            
            ls = connMgr.executeQuery(sql.toString());
            while (ls.next()) {
                createCmuno = String.valueOf(ls.getInt(1) + 1);
            }
            
            sql.setLength(0);
            ls.close();
            
            isOk4 = UploadUtil.fnRegisterAttachFileForSingular(box); // ���� ���� �̹��� ���ε�
            
            String v_img_path = box.getString("p_img_path_savefile");
            
            sql.append(" INSERT INTO TZ_CMUBASEMST                               \n ");
            sql.append(" (                                                       \n ");
            sql.append("     CMUNO, CMU_NM, IN_METHOD_FG, SEARCH_FG              \n ");
            sql.append("     , DATA_PASSWD_FG, DISPLAY_FG, TYPE_L                \n ");
            sql.append("     , TYPE_M, INTRO, IMG_PATH, LAYOUT_FG                \n ");
            sql.append("     , HTML_SKIN_FG, READ_CNT, MEMBER_CNT                \n ");
            sql.append("     , CLOSE_FG, MODIFIER_USERID, REGISTER_USERID        \n ");
            sql.append("     , REGISTER_DTE, MODIFIER_DTE                        \n ");
            sql.append(" )                                                       \n ");
            sql.append(" VALUES                                                  \n ");
            sql.append(" (                                                       \n ");
            sql.append("     ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 1,'0', ?, ?  \n ");
            sql.append("     ,TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')               \n ");
            sql.append("     ,TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')               \n ");
            sql.append(" )                                                       \n ");
            
            
            int index = 1;
            pstmt = connMgr.prepareStatement(sql.toString());
            
            pstmt.setString(index++, createCmuno                       );//Ŀ�´�Ƽ��ȣ
            pstmt.setString(index++, box.getString("p_cmu_nm")          );//Ŀ�´�Ƽ��
            pstmt.setString(index++, box.getString("p_in_method_fg")   );//ȸ�����Թ��
            pstmt.setString(index++, box.getString("p_search_fg")      );//�ڷ�ǰ˻����
            pstmt.setString(index++, box.getStringDefault("p_data_passwd_fg","N") );//�ڷ��ȣȭ����
            pstmt.setString(index++, box.getString("p_display_fg")     );//Ŀ�´�Ƽ��������
            pstmt.setString(index++, box.getString("p_type_l")         );//��з�
            pstmt.setString(index++, box.getString("p_type_m")         );//�ߺз�
            pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
            pstmt.setString(index++, v_img_path);//�̹���
            pstmt.setString(index++, box.getString("p_layout_fg")     );//���̾ƿ�
            pstmt.setString(index++, box.getString("p_html_skin_fg")  );//ȭ�齺Ų
            pstmt.setString(index++, s_userid                         );//�����
            pstmt.setString(index++, s_userid                         );//������
		               
            isOk = pstmt.executeUpdate();
            pstmt.close();
            sql.setLength(0);
            
            //Ŀ�´�Ƽ ����ڵ����
		    sql.append(" select code,codenm from tz_code where gubun ='0053' order by code asc");
	        ls = connMgr.executeQuery(sql.toString());
			
	        sql.setLength(0);
	        
	        sql.append(" INSERT INTO TZ_CMUGRDCODE                           \n ");
	        sql.append(" (                                                   \n ");
	        sql.append("     CMUNO, GRCODE, KOR_NM, ENG_NM, DESCRIPT         \n ");
	        sql.append("     ,REGISTER_USERID, MODIFIER_USERID, DEL_FG       \n ");
	        sql.append("     , REGISTER_DTE,MODIFIER_DTE                     \n ");
	        sql.append(" )                                                   \n ");
	        sql.append(" VALUES                                              \n ");
	        sql.append(" (                                                   \n ");
	        sql.append("     ?, ?, ?, '', '' , ?, ?,'n'                      \n ");
	        sql.append("     , TO_CHAR(SYSDATE,'yyyymmddhh24miss')           \n ");
	        sql.append("     , TO_CHAR(SYSDATE, 'yyyymmddhh24miss')          \n ");
	        sql.append(" )                                                   \n ");

            pstmt = connMgr.prepareStatement(sql.toString());
            
            while (ls.next()) {
                 v_grcode=ls.getString("code") ;
                 pstmt.setString(1, createCmuno            );
                 pstmt.setString(2, ls.getString("code")   );
                 pstmt.setString(3, ls.getString("codenm") );
                 pstmt.setString(4, s_userid               );
                 pstmt.setString(5, s_userid               );
                 
                 isOk4 = pstmt.executeUpdate();
            }
            pstmt.close();
            ls.close();
            sql.setLength(0);
            
            //	ȸ�����
            sql.append(" INSERT INTO TZ_CMUUSERMST                     \n ");
            sql.append(" (                                             \n ");
            sql.append("     CMUNO, USERID, KOR_NAME, ENG_NAME, EMAIL  \n ");
            sql.append("     , TEL, MOBILE, OFFICE_TEL, DUTY, WK_AREA  \n ");
            sql.append("     , GRADE, REQUEST_DTE, LICENSE_DTE         \n ");
            sql.append("     , LICENSE_USERID, CLOSE_FG, CLOSE_REASON  \n ");
            sql.append("     , CLOSE_DTE, INTRO, RECENT_DTE, VISIT_NUM \n ");
            sql.append("     , SEARCH_NUM, REGISTER_NUM, MODIFIER_DTE  \n ");
            sql.append(" )                                             \n ");
            sql.append(" VALUES                                        \n ");
            sql.append(" (                                             \n ");
            sql.append("     ?, ?, ?, '', ?, ?, ?, ?, '', ?,'01'       \n ");
            sql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n ");
            sql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n ");
            sql.append("     , ?, '1', '', '', '', '', 0, 0,0          \n ");
            sql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n ");
            sql.append(" )                                             \n ");

            pstmt = connMgr.prepareStatement(sql.toString());
            
            sql.setLength(0);
            
            sql.append(" SELECT USERID,NAME,EMAIL,HOMETEL,HANDPHONE,COMPTEL,WORK_PLCNM FROM TZ_MEMBER WHERE USERID = '" + s_userid + "' \n");
            sql.append("    AND GRCODE = '" + s_grcode + "' ");
           
            ls = connMgr.executeQuery(sql.toString());
            EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY,Constants.APP_IV);
            
            while (ls.next()) {
                pstmt.setString(1, createCmuno            );	
                pstmt.setString(2, ls.getString("userid")   ); 
                pstmt.setString(3, ls.getString("name") );     
                pstmt.setString(4, encryptUtil.decrypt(ls.getString("email")) );
                pstmt.setString(5, encryptUtil.decrypt(ls.getString("hometel")) );
                pstmt.setString(6, encryptUtil.decrypt(ls.getString("handphone")) );
                pstmt.setString(7, ls.getString("comptel") );
                pstmt.setString(8, ls.getString("work_plcnm") );
                pstmt.setString(9, s_userid );

                isOk1 = pstmt.executeUpdate();
            }   
            pstmt.close();
            ls.close();
            sql.setLength(0);
            
            //�������� �Խ��Ǹ޴�����
            sql.append(" INSERT INTO TZ_CMUMENU                                     \n ");
            sql.append(" (                                                          \n ");
            sql.append("     CMUNO, MENUNO, TITLE, READ_CD, WRITE_CD                \n ");
            sql.append("     , ARRANGE, FILEADD_FG, FILECNT, DIRECTORY_FG           \n ");
            sql.append("     , DIRECTORY_MEMO, BRD_FG, ROOT, PARENT, LV             \n ");
            sql.append("     , POSITION, LIMIT_LIST, REGISTER_USERID, REGISTER_DTE  \n ");
            sql.append("  	, MODIFIER_USERID, MODIFIER_DTE, DEL_FG                 \n ");
            sql.append(" )                                                          \n ");
            sql.append(" VALUES                                                     \n ");
            sql.append(" (                                                          \n ");
            sql.append("     ?, 1, '????????', ?, ?, 'TITLE', 'Y', 5, 'FILE'        \n ");
            sql.append("     , ?, '0', 1, 1, 1, 1 , ? , ?                           \n ");
            sql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), ?              \n ");
            sql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'),'N'             \n ");
            sql.append(" )                                                          \n ");

            pstmt = connMgr.prepareStatement(sql.toString());
            index = 1;
            
            pstmt.setString(index++ , createCmuno 	);//Ŀ�´�����ȣ
            //pstmt.setInt   (index++ , 1            );//�޴��Ϸù�ȣ
            //pstmt.setString(index++ , "��������"   	);//����
            pstmt.setString(index++ , v_grcode     );//�б����
            pstmt.setString(index++ , v_grcode     );//�������
            //pstmt.setString(index++ , "title"      );//����
            //pstmt.setString(index++ , "Y"          );//����÷�α���
            //pstmt.setInt   (index++ , 5            );//÷�����ϰ���
            //pstmt.setString(index++ , "file"       );
            pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
            //pstmt.setString(index++, "0"          );//�ڷ�Ǳ���
            //pstmt.setInt   (index++, 1            );//Root
            //pstmt.setInt   (index++, 1            );//Parent
            //pstmt.setInt   (index++, 1            );//
            //pstmt.setInt   (index++, 1            );//
            pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
            pstmt.setString(index++, s_userid     );//�Խ���
            pstmt.setString(index++, s_userid     );//������

            isOk2 = pstmt.executeUpdate();
            pstmt.close();
            sql.setLength(0);

            //�ٹ��Խ��ǻ���
            sql.append(" INSERT INTO TZ_CMUMENU                                     \n ");
            sql.append(" (                                                          \n ");
            sql.append("     CMUNO, MENUNO, TITLE, READ_CD, WRITE_CD, ARRANGE       \n ");
            sql.append("     , FILEADD_FG, FILECNT, DIRECTORY_FG, DIRECTORY_MEMO    \n ");
            sql.append("     , BRD_FG, ROOT, PARENT, LV, POSITION, LIMIT_LIST       \n ");
            sql.append("     , REGISTER_USERID, REGISTER_DTE, MODIFIER_USERID       \n ");
            sql.append("     , MODIFIER_DTE, DEL_FG                                 \n ");
            sql.append(" )                                                          \n ");
            sql.append(" VALUES                                                     \n ");
            sql.append(" (                                                          \n ");
            sql.append("     ?, 2, '????', ?, ?, 'TITLE', 'Y', 1, 'FILE', ?         \n ");
            sql.append("     , 3, 2, 2, 1, 2, ?, ?                                  \n ");
            sql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), ?              \n ");
            sql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), 'N'            \n ");
            sql.append(" )                                                          \n ");

            pstmt = connMgr.prepareStatement(sql.toString());
            
            index = 1;
            pstmt.setString(index++ , createCmuno      );//Ŀ�´�����ȣ
            //pstmt.setInt   (index++ , 2            );//�޴��Ϸù�ȣ
            //pstmt.setString(index++ , "�ٹ�"     );//����
            pstmt.setString(index++ , v_grcode     );//�б����
            pstmt.setString(index++ , v_grcode     );//�������
            //pstmt.setString(index++ , "title"      );//����
            //pstmt.setString(index++ , "Y"          );//����÷�α���
            //pstmt.setInt   (index++ , 1            );//÷�����ϰ���
            //pstmt.setString(index++ , "file"       );//���丮����
            pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());/** ������ �� �� ����. */
            //pstmt.setString(index++,"3"           );//�ڷ�Ǳ���
            //pstmt.setInt   (index++, 2            );//Root
            //pstmt.setInt   (index++, 2            );//Parent
            //pstmt.setInt   (index++, 1            );//
            //pstmt.setInt   (index++, 2            );//
            pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
            pstmt.setString(index++, s_userid     );//�Խ���
            pstmt.setString(index++, s_userid     );//������

            isOk3 = pstmt.executeUpdate();
            
            if(isOk > 0 && isOk1 > 0 && isOk2 > 0 && isOk3 > 0 && isOk4 > 0) {
                connMgr.commit();
                isOk = 1;
            } else {
            	connMgr.rollback();
            	isOk = 0;
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
        } finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        
        return isOk;
    }



    /**
    * Ŀ�´�Ƽ ��������
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int updateBaseMst(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";
        int         isOk        = 0;
        int         isOk2       = 0;
        int         v_seq       = 0;

        String v_cmuno     = box.getString("p_cmuno");
        String v_intro     = StringUtil.removeTag(box.getString("p_content"));
        
        System.out.println("v_intro : "+v_intro);
        String s_userid    = box.getSession("userid");

        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false); 
           
           isOk2 = UploadUtil.fnRegisterAttachFileForSingular(box); // ���� ���� �̹��� ���ε�
           
           String v_img_path = box.getString("p_img_path_savefile");
           
           Vector v_del_file = box.getVector("p_del_savefile");

           sql  =" update tz_cmubasemst set  cmu_nm             =?   "
                +" 		, in_method_fg       =?   "
                +"      , search_fg          =?   "
                +"      , data_passwd_fg     =?   "
                +"      , display_fg         =?   "
                +"      , type_l             =?   "  
                +"      , type_m             =?   "
                +"      , intro              =?   "
                +"      , img_path           =?   "
                +"      , layout_fg          =?   "
                +"      , html_skin_fg       =?   "
                +"      , modifier_dte       =to_char(sysdate, 'YYYYMMDDHH24MISS')"
                +"      , modifier_userid    =?   "
                +" where cmuno = ?";
int index = 1;

           pstmt = connMgr.prepareStatement(sql);

           pstmt.setString(index++, box.getString("p_cmu_nm")          );//Ŀ�´�Ƽ��
           pstmt.setString(index++, box.getString("p_in_method_fg")   );//ȸ�����Թ��
           pstmt.setString(index++, box.getString("p_search_fg")      );//�ڷ�ǰ˻���뤷
           pstmt.setString(index++, box.getStringDefault("p_data_passwd_fg","N") );//�ڷ��ȣȭ����
           pstmt.setString(index++, box.getString("p_display_fg")     );//Ŀ�´�Ƽ��������
           pstmt.setString(index++, box.getString("p_type_l")         );//��з�
           pstmt.setString(index++, box.getString("p_type_m")         );//�ߺз�
           pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
           pstmt.setString(index++, v_img_path );//�̹���
           pstmt.setString(index++, box.getString("p_layout_fg")     );//���̾ƿ�
           pstmt.setString(index++, box.getString("p_html_skin_fg")  );//ȭ�齺Ų
           pstmt.setString(index++, s_userid                         );//������
           pstmt.setString(index++, v_cmuno                          );//Ŀ�´�Ƽ��ȣ
           isOk = pstmt.executeUpdate();


           //Ŀ�´�Ƽ ���� ���� 
//           sql1 = "select intro from tz_cmubasemst where cmuno = '" + v_cmuno + "'";  
//           connMgr.setOracleCLOB(sql1, v_intro);

           if(isOk > 0 ){
				connMgr.commit();
				if (v_del_file != null)	{
					FileManager.deleteFile(v_del_file);	//	 DB	���� ���ó����	�Ϸ�Ǹ� �ش� ÷������ ����
				}
				isOk =1;
			}
			else{
				connMgr.rollback();
				isOk =0;
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

    /**
    * �⺻���� ��ȸ
    * @param box          receive from the form object and session
    * @return ArrayList   �⺻���� ������
    * @throws Exception
    */
    public static DataBox selectBaseMst(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;

        String              sql     = "";
        DataBox             dbox    = null;

        String  v_cmuno = box.getString("p_cmuno"); 
 
        try {
            connMgr = new DBConnectionManager();
  
            sql  =  " select a.cmuno cmuno, a.cmu_nm cmu_nm, a.in_method_fg in_method_fg, a.search_fg search_fg, a.data_passwd_fg data_passwd_fg, a.display_fg display_fg, a.type_l type_l "
                  + "       , a.type_m, a.intro intro, a.img_path img_path, a.layout_fg layout_fg, a.html_skin_fg html_skin_fg, a.read_cnt read_cnt, a.member_cnt member_cnt, a.close_fg close_fg"
                  + "       , a.close_reason close_reason, a.close_dte close_dte, a.close_userid close_userid, a.hold_fg hold_fg, a.accept_dte accept_dte, a.accept_userid accept_userid, a.register_dte register_dte"
                  + "       , a.register_userid  register_userid, a.modifier_dte modifier_dte, a.modifier_userid modifier_userid"
				  + "       , isnull(d.savefile,'')  hongbo_savefile , isnull(d.contents,'���Է�')  hongbo_contents"
				  + "        "
                  + "   from  tz_cmubasemst a"
                  + "        ,tz_cmuhongbo d"
                  + "  where a.cmuno   = '"+v_cmuno+"'"
				  + "    and a.cmuno   =  d.cmuno(+) "
                  ;
            ls = connMgr.executeQuery(sql);
			
            while (ls.next()) {
                dbox = ls.getDataBox();
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
        return dbox;
    }

	/**
	 * ���õ� �ڷ����� DB���� ����
	 * @param  connMgr   DB Connection Manager
	 * @param box    receive from the form object and session
	 * @return
	 * @throws Exception
	 */
	public int deleteSingleFile( RequestBox box) throws Exception {
		 DBConnectionManager connMgr = null;
		 PreparedStatement   pstmt   = null;
		 ListSet             ls      = null;
		 String              sql     = "";
		 int                 isOk2   = 1;
		 String v_cmuno  = box.getString("p_cmuno");
		 
		 try {
			 connMgr = new DBConnectionManager();
			 connMgr.setAutoCommit(false); 
	
			 sql  =" update tz_cmubasemst set img_path='' "
				 + "  where cmuno        = ?";
			 pstmt = connMgr.prepareStatement(sql);
			 pstmt.setString(1, v_cmuno       );//Ŀ�´�Ƽ��ȣ
			 isOk2 = pstmt.executeUpdate();
	
			 if(isOk2 > 0) {
				 if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			 }
		 }
		 catch (Exception ex) {
			 ErrorManager.getErrorStackTrace(ex, box, sql);
			 throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		 }
		 finally {
			 if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			 if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			 if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			 if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		 }
		 return isOk2;
	}

}
