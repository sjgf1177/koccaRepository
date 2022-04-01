//**********************************************************
//1. ��      ��:
//2. ���α׷���: CommunityMsMenuBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-29
//7. ��      ��:
//
//**********************************************************

package com.credu.community;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommunityMsMenuBean {
    private ConfigSet config;
    private static int row=10;
    private String v_type = "PQ";
    private static final String FILE_TYPE = "p_file";           //      ���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 1;                    //    �������� ���õ� ����÷�� ����


    public void CommunityMsMenuBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));  //�� ����� �������� row ���� �����Ѵ�
            row = 10; //������ ����
//System.out.println("....... row.....:"+row);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

 /**
 �޴��� �ߺ�üũ ��ȸ
 @param box     receive from the form object and session
 @return int    row count
 */
    public int selectCmuNmRowCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = new ArrayList();
        ListSet   ls   = null;
        String    sql  = "";
        int       iRowCnt=0;
        try {
            connMgr = new DBConnectionManager();
            sql = " select count(*) rowcnt"
                 +"   from tz_cmumenu "
                 +"  where cmuno = '"+box.getStringDefault("p_cmuno","")+"'"
                 +"    and brd_fg ='"+box.getStringDefault("p_brd_fg","")+"'"
                 +"    and title = "+ SQLString.Format(box.getStringDefault("p_title",""))
                 ;
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
			//2005.12.01_�ϰ���  : conn ���� �߰�.
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return iRowCnt;
    }

    /**
    * Ŀ�´�Ƽ �޴�����
    * @param box          receive from the form object and session
    * @return ArrayList   Ŀ�´�Ƽ �޴�����
    * @throws Exception
    */
    public String getSingleColumn(RequestBox box,String v_cmuno,String v_menuno,String v_column) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();
        String              sql     = "";
        DataBox             dbox    = null;

        String              v_ret   ="";
        try {
            connMgr = new DBConnectionManager();

            sql  = "\n select "+v_column
                  +"\n   from tz_cmumenu"
                  +"\n  where cmuno        = '"+v_cmuno     +"'"
                  +"\n    and menuno       = '"+v_menuno+"'";
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_ret = ls.getString(1);
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
        return v_ret;
    }

    /**
    * Ŀ�´�Ƽ �޴����̺� Į������
    * @param box          receive from the form object and session
    * @return ArrayList   Ŀ�´�Ƽ �޴����̺� Į������
    * @throws Exception
    */
    public String getSingleColumn1(String v_cmuno,String v_menuno,String v_column) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();
        String              sql     = "";
        DataBox             dbox    = null;

        String              v_ret   ="";
        try {
            connMgr = new DBConnectionManager();

            sql  = "\n select "+v_column
                  +"\n   from tz_cmumenu"
                  +"\n  where cmuno        = '"+v_cmuno     +"'"
                  +"\n    and menuno       = '"+v_menuno    +"'";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_ret = ls.getString(1);
            }
        }
        catch (Exception ex) {
//               ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return v_ret;
    }

	public String getSingleColumn2(String v_cmuno,String v_menuno,String v_column,String v_brd_fg) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();
        String              sql     = "";
        DataBox             dbox    = null;

        String              v_ret   ="";
        try {
            connMgr = new DBConnectionManager();

            sql  = "\n select "+v_column
                  +"\n   from tz_cmumenu"
                  +"\n  where cmuno        = '"+v_cmuno     +"'"
                  +"\n    and menuno       = '"+v_menuno    +"'"
                  +"\n    and brd_fg       = '"+v_brd_fg    +"'";
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_ret = ls.getString(1);
            }
        }
        catch (Exception ex) {
//               ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return v_ret;
    }

    /**
    * Ŀ�´�Ƽ ������ �޴�����Ʈ
    * @param box          receive from the form object and session
    * @return ArrayList   Ŀ�´�Ƽ �޴�����Ʈ
    * @throws Exception
    */
    public ArrayList selectleftList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();
        StringBuffer        sql     = new StringBuffer();
        DataBox             dbox    = null;

        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");
        String v_brd_fg        = box.getStringDefault("p_brd_fg","2");

        String s_userid        = box.getSession("userid");
        String s_name          = box.getSession("name");

        try {
            connMgr = new DBConnectionManager();

            sql.append(" SELECT  CMUNO, MENUNO, TITLE, READ_CD, WRITE_CD, ARRANGE, BRD_FG               \n ");
            sql.append("         , FILEADD_FG,FILECNT , DIRECTORY_FG, DIRECTORY_MEMO AS DIRECTORY_MEMO  \n ");
            sql.append("         , ROOT, PARENT, LV, POSITION, LIMIT_LIST AS LIMIT_LIST                 \n ");
            sql.append("         , REGISTER_USERID, REGISTER_DTE, MODIFIER_USERID, MODIFIER_DTE, DEL_FG \n ");
            sql.append("         , DECODE(BRD_FG, '1', '�ڷ��', '2', '�Խ���') BRD_NM                   \n ");
            sql.append(" FROM TZ_CMUMENU                                                                \n ");
            sql.append(" WHERE CMUNO    = "+StringManager.makeSQL(v_cmuno)+"                            \n ");
            sql.append(" AND BRD_FG   IN ( '1' , '2')                                                   \n ");
            sql.append(" AND DEL_FG   ='N'                                                              \n ");
            sql.append(" ORDER BY BRD_FG DESC, ROOT ASC,POSITION ASC                                    \n ");

            ls = connMgr.executeQuery(sql.toString());
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        }
        catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    /**
    * Ŀ�´�Ƽ �Խ��� �޴�����
    * @param box          receive from the form object and session
    * @return ArrayList   Ŀ�´�Ƽ �޴�������
    * @throws Exception
    */
    public ArrayList selectSingleMenu(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();
        String              sql     = "";
        String              sql1    = "";
        String              sql2    = "";
        DataBox             dbox    = null;

        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");
        String v_menuno        = box.getString("p_menuno");

        String v_brd_fg    = box.getStringDefault("p_brd_fg","2");

        String s_userid        = box.getSession("userid");
        String s_name          = box.getSession("name");

        try {
            connMgr = new DBConnectionManager();

            sql  = "\n         select  cmuno, menuno, title, read_cd, write_cd, arrange, fileadd_fg,filecnt "
//				2005.11.21_�ϰ��� : Underlying input stream returned zero bytes(Text ���� null or 0 byte �� ��쿡�����Ƿ� ����.
				//+"\n               , directory_FG, directory_memo,brd_fg, root, parent, lv, position,limit_list"
				+"\n               , directory_FG, directory_memo as directory_memo, "
				+ " brd_fg, root, parent, lv, position, limit_list as limit_list"

                  +"\n               , register_userid, register_dte, modifier_userid    "
                  +"\n               , modifier_dte, del_fg "
                  +"\n           from tz_cmumenu "
                  +"\n           where cmuno    = '"+v_cmuno     +"'"
                  +"\n             and menuno   = '"+v_menuno+"'"
                  +"\n             and del_fg   ='N'"
                  ;
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
    * Ŀ�´�Ƽ �޴�����Ʈ
    * @param box          receive from the form object and session
    * @return ArrayList   Ŀ�´�Ƽ �޴�����Ʈ
    * @throws Exception
    */

    public static ArrayList selectleftCbxList(String v_cmuno,String v_brd_fg) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();
        String              sql     = "";
        String              sql1    = "";
        String              sql2    = "";
        DataBox             dbox    = null;

        try {
            connMgr = new DBConnectionManager();

			/* 2005.11.16_�ϰ��� : Oracle -> Mssql rownum   ����
            sql  = "\n select a.*,rownum rowseq "
                  +"\n   from (select  cmuno, menuno, title, read_cd, write_cd, arrange, fileadd_fg,filecnt "
                  +"\n               , directory_FG, directory_memo,brd_fg, root, parent, lv, position,limit_list"
                  +"\n               , register_userid, register_dte, modifier_userid    "
                  +"\n               , modifier_dte, del_fg "
                  +"\n           from tz_cmumenu "
                  +"\n           where cmuno    = '"+v_cmuno     +"'"
                  +"\n             and brd_fg   = '"+v_brd_fg+"'"
                  +"\n             and del_fg   ='N'"
                  +"\n           order by root asc,position asc"
                 + "\n ) a";
             */
			sql  = "select  cmuno, menuno, title, read_cd, write_cd, arrange, fileadd_fg,filecnt "
                //2005.11.21_�ϰ��� : Underlying input stream returned zero bytes(Text ���� null or 0 byte �� ��쿡�����Ƿ� ����.
				//+"\n               , directory_FG, directory_memo,brd_fg, root, parent, lv, position,limit_list"
				+"\n               , directory_FG, directory_memo as directory_memo, "
				+ " brd_fg, root, parent, lv, position, limit_list as limit_list"

                +"\n               , register_userid, register_dte, modifier_userid    "
                +"\n               , modifier_dte, del_fg "
                +"\n           from tz_cmumenu "
                +"\n           where cmuno    = '"+v_cmuno     +"' " ;
//			 	+"\n             and brd_fg   = '"+v_brd_fg+"' ";
			// 05.12.06 �̳��� �߰�
                if(!"0".equals(v_brd_fg)){
					sql += "\n             and brd_fg   = '"+v_brd_fg+"' ";
                }else{}
                sql += "\n             and del_fg   ='N'"
                +"\n           order by root asc,position asc";
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        }
        catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    /**
    * �޴�����ϱ�
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int insertMenu(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";

        int         isOk1       = 1;
        int         isOk2       = 1;
        int         v_seq       = 0;
        int         v_menuno    = 0;
        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");
		// �Խ����� Default�� ���
		String v_brd_fg        = box.getStringDefault("p_brd_fg","2");
        int    v_ins_position  = box.getInt("p_ins_position");

        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");
        int index = 1;

		try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false);

/*
           sql  =" update tz_cmumenu set    position = position+1"
                +"  where cmuno          = ?"
                +"    and brd_fg          = ?"
                +"    and position        > ?"
                ;
           pstmt = connMgr.prepareStatement(sql);

           pstmt.setString(1, v_cmuno              );
           pstmt.setString(2, v_brd_fg   );
           pstmt.setInt   (3, v_ins_position       );
           pstmt.executeUpdate();
*/

           sql = "select NVL(max(menuno), 0) from tz_cmumenu where cmuno = '" +v_cmuno+ "'";
           ls = connMgr.executeQuery(sql);
           while (ls.next()) {
               v_menuno = ls.getInt(1) + 1;
           }
		   // 05.12.06 �̳��� _ �������װ� �ڷ�� ���� �Ͽ� menuno ����
		   String v_tmp_menuno = String.valueOf(v_menuno);
		   if("1".equals(v_tmp_menuno)){
			   v_menuno = Integer.parseInt(v_tmp_menuno);
			   v_menuno= v_menuno+1 ;
		   }
System.out.println("�߰�");
           sql  =" insert into tz_cmumenu ( cmuno          ,menuno         ,title          ,read_cd        "
                +"                         ,write_cd       ,arrange        ,fileadd_fg     ,filecnt        "
                +"                         ,directory_fg   ,directory_memo ,brd_fg         ,root           ,parent         "
                +"                         ,lv,position,limit_list ,register_userid       ,register_dte   "
                +"                         ,modifier_userid,modifier_dte   ,del_fg                         )"
                +"                 values  (?,?,?,?"
                +"                         ,?,'title','N',0"
                 +"                         ,?,?,?,?,?"
				+"                         ,?,?,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS')"
                +"                         ,?,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N')"
                ;
           pstmt = connMgr.prepareStatement(sql);

           pstmt.setString(index++ , v_cmuno                            );//Ŀ�´�����ȣ
           pstmt.setInt   (index++ , v_menuno                           );//�޴��Ϸù�ȣ
		   pstmt.setString(index++ , box.getString("p_ins_title" )      );//����
           pstmt.setString(index++ , box.getString("p_ins_read_cd" )    );//�б����

           pstmt.setString(index++ , box.getString("p_ins_write_cd" )   );//�������
//           pstmt.setString(index++ , "title"     );//����
//           pstmt.setString(index++ , "N"                                );//����÷�α���
//           pstmt.setInt   (index++ , 0                              );//÷�����ϰ���

           pstmt.setString(index++ , box.getString("p_ins_directory_fg"));//���丮����
pstmt.setCharacterStream(index++, new StringReader(box.getString("p_ins_directory_memo" )), box.getString("p_ins_directory_memo" ).length());
//		   pstmt.setString(index++, box.getString("p_ins_directory_memo" )        );
		   pstmt.setString(index++, box.getString("p_brd_fg" )         );//�ڷ�Ǳ���
		   if(box.getInt("p_lv" )+1 ==1){
              pstmt.setInt   (index++, v_menuno         );//
              pstmt.setInt   (index++, v_menuno        );//
           } else {
              pstmt.setInt   (index++, box.getInt("p_root" )          );//
              pstmt.setInt   (index++, box.getInt("p_menuno" )        );//
           }

		   pstmt.setInt   (index++, box.getInt("p_lv" )+1          );//
           pstmt.setInt   (index++, box.getInt("p_position" )+1          );//
//           pstmt.setString(index++, box.getString("p_ins_limit_list" ));
pstmt.setCharacterStream(index++, new StringReader(box.getString("p_ins_limit_list" )),box.getString("p_ins_limit_list" ).length());
           pstmt.setString(index++, s_userid);//�Խ���

           pstmt.setString(index++, s_userid );//������

           isOk1 = pstmt.executeUpdate();

//           String sql1 = "select directory_memo from tz_cmumenu where cmuno = '" +v_cmuno+ "' and menuno ="+v_menuno;
//           connMgr.setOracleCLOB(sql1, box.getString("p_ins_directory_memo" ));
//           String sql2 = "select limit_list from tz_cmumenu where cmuno = '" +v_cmuno+ "' and menuno ="+v_menuno;
//           connMgr.setOracleCLOB(sql2, box.getString("p_ins_limit_list" ));
		   /* 2005.11.16_�ϰ��� : Oracle -> Mssql empty_clob() ����
		   */
            if(isOk1 > 0 ) {
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
           }

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, index + " : " +sql);
            throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2;
    }



    /**
    * �޴������ϱ�
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int updateMenu(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";

        int         isOk1       = 1;
        int         isOk2       = 1;
        int         v_seq       = 0;

        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");
        int    v_menuno        = box.getInt("p_menuno");
		//
        String v_brd_fg        = box.getStringDefault("p_brd_fg","2");
//		String v_brd_fg        = box.getStringDefault("p_brd_fg","1");
        int    v_ins_position  = box.getInt("p_ins_position");
		// 2005.11.29_�ϰ��� : ������ �޴��� �޾ƿ�.
		String v_title 		   = box.getString("p_title" ) ;//����

        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false);

           sql  =" update tz_cmumenu  set write_cd     =?"
                +"                       ,read_cd  =?"
                +"                       ,arrange  =?"
                +"                       ,fileadd_fg  =?"
                +"                       ,filecnt  =?"
                +"                       ,modifier_userid  =?"
                +"                       ,modifier_dte=to_char(sysdate, 'YYYYMMDDHH24MISS')"
                //2005.11.16_�ϰ��� : Oracle -> Mssql empty_clob() ����
//                +"                       ,limit_list=empty_clob()"
                +"                       ,limit_list=?"
				+" 						, title = ?"
                +"            where cmuno = ?"
                +"              and menuno=?";
int index = 1;
           pstmt = connMgr.prepareStatement(sql);

           pstmt.setString(index++ , box.getString("p_write_cd" )   );//�������
           pstmt.setString(index++ , box.getString("p_read_cd" )    );//�б����
           pstmt.setString(index++ , box.getString("p_arrange" )    );//����
           pstmt.setString(index++ , box.getStringDefault("p_fileadd_fg","N" )    );//����÷�α���
           pstmt.setString(index++ , box.getStringDefault("p_filecnt","0" )    );//÷�����ϰ���
           pstmt.setString(index++,  s_userid );//������
pstmt.setCharacterStream(index++, new StringReader(box.getString("p_limit_list" )), box.getString("p_limit_list" ).length());
//           pstmt.setString(index++ , box.getString("p_limit_list" ) );
		   // 2005.11.29_�ϰ���  : ������ �޴��� �޾ƿ�.
		   pstmt.setString(index++ , v_title);

		   pstmt.setString(index++ , v_cmuno      );//Ŀ�´�����ȣ
           pstmt.setInt   (index++ , v_menuno     );//�޴��Ϸù�ȣ

		   isOk1 = pstmt.executeUpdate();

//           String sql2 = "select limit_list from tz_cmumenu where cmuno = '" +v_cmuno+ "' and menuno ="+v_menuno;
//           connMgr.setOracleCLOB(sql2, box.getString("p_limit_list" ));
		   /*2005.11.16_�ϰ��� : Oracle -> Mssql empty_clob() ����
            if(isOk1 > 0 ) {
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
            }
            */
		 if(isOk1 > 0 ) {
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
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
        return isOk1*isOk2;
    }


    /**
    * �޴������ϱ�
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */

    public int deleteMenu(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";

        int         isOk1       = 1;
        int         isOk2       = 1;
        int         v_seq       = 0;
        int         v_menuno    = 0;
        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");
        String v_brd_fg        = box.getStringDefault("p_brd_fg","2");
        int    v_ins_position  = box.getInt("p_ins_position");

        String s_userid        = box.getSession("userid");
        String s_name          = box.getSession("name");

        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false);

           sql  =" update tz_cmumenu  set del_fg     ='Y'"
                +"                       ,modifier_userid  =?"
                +"                       ,modifier_dte=to_char(sysdate, 'YYYYMMDDHH24MISS')"
                +"            where cmuno = ?"
                +"              and menuno=?";
//System.out.println(sql+v_cmuno+v_menuno);
           pstmt = connMgr.prepareStatement(sql);
           pstmt.setString(1, s_userid );//������
           pstmt.setString(2 , box.getString("p_cmuno")                            );//Ŀ�´�����ȣ
           pstmt.setInt   (3 , box.getInt("p_menuno")                           );//�޴��Ϸù�ȣ
           isOk1 = pstmt.executeUpdate();

           if(isOk1 > 0 ) {
               if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }

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
        return isOk1*isOk2;
    }

}
