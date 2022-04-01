//**********************************************************
//  1. ��      ��: ������ ����
//  2. ���α׷���: DicSubjAdminBean.java
//  3. ��      ��: ������ ����
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2005. 9. 1
//  7. ��      ��:
//**********************************************************

package com.credu.course;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.namo.active.NamoMime;
/**
 * ������ ����(ADMIN)
 *
 * @date   : 2003. 7
 * @author : s.j Jung
 */
public class DicSubjAdminBean {

    public DicSubjAdminBean() {}



    /**
    ������ ȭ�� ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   ������  ����Ʈ
    */
    public ArrayList selectListDic(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_gubun      = "1";
        //2005.12.06_�ϰ��� : ������ ���� (tz_subj -> tz_grcode �� ��ü.)
		//String ss_subj          = box.getString("s_subjcourse");    // �����ڵ�
		String ss_subj          = box.getString("s_grcode");    	// �׷��ڵ�

        String v_searchtext = box.getString("p_searchtext");

        String  v_orderColumn   = box.getString("p_orderColumn");           	//������ �÷���
        String  v_orderType     = box.getString("p_orderType");           		//������ ����

        String v_groups     = box.getStringDefault("p_group","");        // ��,��,�� ....
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

			sql  = " select a.seq seq, a.subj subj, b.grcodenm grcodenm, a.words words,          ";
			sql += "        descs as  descs, a.groups groups, a.luserid luserid, a.ldate ldate ";
			sql += " from TZ_DIC a, tz_grcode b, TZ_DICGROUP c                               ";
            sql += " where a.subj = b.grcode ";

            sql += "    and a.groups = c.groups                                                  ";
            sql += "    and a.gubun = " + StringManager.makeSQL(v_gubun);

System.out.println("ss_subj = " + ss_subj);
            if (!ss_subj.equals("") && !ss_subj.equals("ALL")) {                               // �����ڵ� ������
                sql += "  and a.subj   = " + StringManager.makeSQL(ss_subj);
            }

            if ( !v_searchtext.equals("")) {                            //    �˻�� ������
                //sql += " and a.words like " + StringManager.makeSQL("%" + v_searchtext + "%");
                sql += " and (lower(a.words) like "+StringManager.makeSQL("%" + v_searchtext + "%")+" OR upper(a.words) like "+StringManager.makeSQL("%" + v_searchtext + "%")+" ) ";
            }

            if ( !v_groups.equals("")) {                            //    ���з��� �˻��Ҷ�
                sql += " and a.groups = " + StringManager.makeSQL(v_groups);
            }

            if(v_orderColumn.equals("")) {
            	sql += " order by a.subj asc, a.groups asc, a.words asc ";
			} else {
			    sql+= " order by " + v_orderColumn + v_orderType;
			}

System.out.println("selectListDic.sql = " + sql);
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
    ������ ȭ�� �󼼺���
    @param box          receive from the form object and session
    @return DicData     ��ȸ�� ������
    */
    public DataBox selectViewDic(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DicData data = null;
        DataBox dbox = null;
        String v_gubun      = "1";
        String v_subj       = box.getString("p_subj");
        String v_seq        = box.getString("p_seq");

        try {
            connMgr = new DBConnectionManager();

			 // 2005.12.06_�ϰ��� : ������ ���� (tz_subj -> tz_grcode �� ��ü.)
            sql  = " select a.seq seq, a.subj subj, b.grcodenm grcodenm, a.words words,          ";
            //
			//sql += "        a.descs descs, a.groups groups, a.luserid luserid, a.ldate ldate ";
			sql += "        a.descs as descs, a.groups groups, a.luserid luserid, a.ldate ldate ";
            sql += "   from TZ_DIC a, tz_grcode b  ";
            sql += "  where a.subj = b.grcode   ";
            sql += "    and a.gubun  = " + StringManager.makeSQL(v_gubun);
            sql += "    and a.subj   = " + StringManager.makeSQL(v_subj);
            sql += "    and a.seq    = " + v_seq;

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();
            }
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
    ������  ����Ҷ�
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
    public int insertDic(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;
        String v_menu = "";

        String v_gubun  = "1";
        String v_subj   = box.getString("p_subj");
        int    v_seq    = 0;
        String v_words  = box.getString("p_words");
        String v_descs  = box.getString("p_descs");
        String v_contents  = box.getString("p_contents");
        String v_groups = box.getString("p_groups");
        String s_userid = box.getSession("userid");

		   /*********************************************************************************************/
		   // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�. 
		   try {
			   v_contents =(String) NamoMime.setNamoContent(v_contents);
		   } catch(Exception e) {
			   System.out.println(e.toString());
			   return 0;
		   }
		   /*********************************************************************************************/       

        /*********************************************************************************************/
        // ���𿡵��� ���� ó��  (Mime Document Parsing �� �̹��� ���ε�)
        //ConfigSet conf = new ConfigSet();
        //SmeNamoMime namo = new SmeNamoMime(v_contents); // ��ü����
        //boolean result = namo.parse(); // ���� �Ľ� ����
        //if ( !result ) { // �Ľ� ���н�
        //    System.out.println( namo.getDebugMsg() ); // ����� �޽��� ���
        //    return 0;
        //}
        //if ( namo.isMultipart() ) { // ������ ��Ƽ��Ʈ���� �Ǵ�
        //    String v_server = conf.getProperty("autoever.url.value");
        //    String fPath = conf.getProperty("dir.namo");   // ���� ���� ��� ����
        //    String refUrl = conf.getProperty("url.namo");; // ������ ����� ������ �����ϱ� ���� ���
        //    String prefix = "dic" + v_subj;         // ���ϸ� ���ξ�
        //    result = namo.saveFile(fPath, v_server+refUrl, prefix); // ���� ���� ����
        //}
        //if ( !result ) { // �������� ���н�
        //    System.out.println( namo.getDebugMsg() ); // ����� �޽��� ���
        //    return 0;
        //}
        //v_contents = namo.getContent(); // ���� ����Ʈ ���
        /*********************************************************************************************/

        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false);
           sql  = "select max(seq) from TZ_DIC ";
           sql += " where gubun = " + StringManager.makeSQL(v_gubun);
           sql += "   and subj  = " + StringManager.makeSQL(v_subj);
           ls = connMgr.executeQuery(sql);
           if (ls.next()) {
               v_seq = ls.getInt(1) + 1;
           } else {
               v_seq = 1;
           }

           sql1 =  " insert into TZ_DIC(gubun, subj, seq, words, descs, groups, luserid, ldate) ";
		   // ������ : 05.11.15 ������ : �̳���_ empty_clob() ����
//           sql1 += " values (?, ?, ?, ?, empty_clob(), ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))  ";
           sql1 += " values (?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))  ";

int index = 1;
           pstmt = connMgr.prepareStatement(sql1);

           pstmt.setString(index++,  v_gubun);
           pstmt.setString(index++,  v_subj);
           pstmt.setInt(index++,  v_seq);
           pstmt.setString(index++,  v_words);
pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
           pstmt.setString(index++,  v_groups);
           pstmt.setString(index++ , s_userid);
           isOk = pstmt.executeUpdate();

           sql2 = "select descs from TZ_DIC";
           sql2 += "  where gubun    = " + StringManager.makeSQL(v_gubun);
           sql2 += "    and subj     = " + StringManager.makeSQL(v_subj);
           sql2 += "    and seq      = " + v_seq;

           /* 05.11.15 �̳��� */
//		   //connMgr.setOracleCLOB(sql2, v_contents);   // CLOB ó��

           if(isOk > 0){
        	   if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
           }else{
               if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
           }

        }
        catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    /**
    ������  ����Ҷ� Excel
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
    public int insertDicExcel(String v_subj, String v_words, String v_groups,String v_descs, String s_userid) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;
        String v_menu = "";

        String v_gubun  = "1";
        //String v_subj   = box.getString("p_subj");
        int    v_seq    = 0;
        //String v_words  = box.getString("p_words");
        //String v_descs  = box.getString("p_descs");
        //String v_groups = box.getString("p_groups");
        //String v_groups = "";
		//String v_descs  = box.getString("p_descs");
        //String v_contents  = box.getString("p_contents");
        //String s_userid = box.getSession("userid");

        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false);

           sql  = "select max(seq) from TZ_DIC ";
           sql += " where gubun = " + StringManager.makeSQL(v_gubun);
           sql += "   and subj  = " + StringManager.makeSQL(v_subj);

           ls = connMgr.executeQuery(sql);

           if (ls.next()) {
               v_seq = ls.getInt(1) + 1;
           } else {
               v_seq = 1;
           }
           if(ls != null) { try { ls.close(); } catch (Exception e) {} }

           sql1 =  " insert into TZ_DIC(gubun, subj, seq, words, descs, groups, luserid, ldate)      ";
           // ������ : 05.11.15 ������ : �̳��� _ empty_clob() ����
//           sql1 += "            values (?, ?, ?, ?, empty_clob(), ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))  ";
           sql1 += "            values (?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))  ";

int index = 1;
		   pstmt = connMgr.prepareStatement(sql1);
           pstmt.setString(index++,  v_gubun);
           pstmt.setString(index++,  v_subj);
           pstmt.setInt(index++,  v_seq);
           pstmt.setString(index++,  v_words);
pstmt.setCharacterStream(index++, new StringReader(v_descs), v_descs.length());
           //pstmt.setString(index++,  v_descs);
           pstmt.setString(index++,  v_groups);
           pstmt.setString(index++, s_userid);
           isOk = pstmt.executeUpdate();

           sql2 = "select descs from TZ_DIC";
           sql2 += "  where gubun    = " + StringManager.makeSQL(v_gubun);
           sql2 += "    and subj     = " + StringManager.makeSQL(v_subj);
           sql2 += "    and seq      = " + v_seq;
//	       /* 05.11.15 �̳��� */
//		   connMgr.setOracleCLOB(sql2, v_descs);

           if(isOk > 0){
               if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
           }else{
               if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
           }
		}
        catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
          if(connMgr != null) { try { connMgr.setAutoCommit(true); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    ������  �����Ҷ�
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail
    */
     public int updateDic(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_gubun  = "1";
        String v_subj   = box.getString("p_subj");
        int    v_seq    = box.getInt("p_seq");
        String v_words  = box.getString("p_words");
        String v_descs  = box.getString("p_descs");
        String v_contents  = box.getString("p_contents");
        String v_groups = box.getString("p_groups");
        String s_userid = box.getSession("userid");

        
		   /*********************************************************************************************/
		   // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�. 
		   try {
			   v_contents =(String) NamoMime.setNamoContent(v_contents);
		   } catch(Exception e) {
			   System.out.println(e.toString());
			   return 0;
		   }
		   /*********************************************************************************************/       

        
        /*********************************************************************************************/
        // ���𿡵��� ���� ó��  (Mime Document Parsing �� �̹��� ���ε�)
        //ConfigSet conf = new ConfigSet();
        //SmeNamoMime namo = new SmeNamoMime(v_contents); // ��ü����
        //boolean result = namo.parse(); // ���� �Ľ� ����
        //if ( !result ) { // �Ľ� ���н�
        //    System.out.println( namo.getDebugMsg() ); // ����� �޽��� ���
        //    return 0;
        //}
        //if ( namo.isMultipart() ) { // ������ ��Ƽ��Ʈ���� �Ǵ�
        //    String v_server = conf.getProperty("autoever.url.value");
        //    String fPath = conf.getProperty("dir.namo");   // ���� ���� ��� ����
        //    String refUrl = conf.getProperty("url.namo");; // ������ ����� ������ �����ϱ� ���� ���
        //    String prefix = "dic" + v_subj;         // ���ϸ� ���ξ�
        //    result = namo.saveFile(fPath, v_server+refUrl, prefix); // ���� ���� ����
        //}
        //if ( !result ) { // �������� ���н�
        //    System.out.println( namo.getDebugMsg() ); // ����� �޽��� ���
        //    return 0;
        //}
        //v_contents = namo.getContent(); // ���� ����Ʈ ���
        /*********************************************************************************************/

        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false);
//		   sql  = " update TZ_DIC set words = ?, descs = empty_clob(), groups= ?, luserid = ?, ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
		   sql  = " update TZ_DIC set words = ?, descs = ?, groups= ?, luserid = ?, ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
           sql += "  where gubun = ? and subj = ? and seq = ?    ";

           pstmt = connMgr.prepareStatement(sql);
int index = 1;

           pstmt.setString(index++,  v_words);
pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
           //pstmt.setString(index++,  v_contents);
           pstmt.setString(index++,  v_groups);
           pstmt.setString(index++, s_userid);
           pstmt.setString(index++,  v_gubun);
           pstmt.setString(index++,  v_subj);
           pstmt.setInt(index++,  v_seq);

           isOk = pstmt.executeUpdate();

           sql = "select descs from TZ_DIC";
           sql += "  where gubun    = " + StringManager.makeSQL(v_gubun);
           sql += "    and subj     = " + StringManager.makeSQL(v_subj);
           sql += "    and seq      = " + v_seq;
//		   connMgr.setOracleCLOB(sql, v_contents);       //      (ORACLE 9i ����)
           if(isOk > 0){
               if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
           }else{
               if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
           }
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    /**
    ������ �����Ҷ�
    @param box      receive from the form object and session
    @return isOk    1:delete success,0:delete fail
    */
    public int deleteDic(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_gubun  = "1";
        String v_subj   = box.getString("p_subj");
        int    v_seq    = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();

            sql  = " delete from TZ_DIC   ";
            sql += "  where gubun = ? and subj = ? and seq = ? ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1,  v_gubun);
            pstmt.setString(2,  v_subj);
            pstmt.setInt(3,  v_seq);
            isOk = pstmt.executeUpdate();

            //Log.info.println(this, box, "delete TZ_DIC where gubun" + v_gubun + " and subj " +v_subj+ " and seq" + v_seq);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n"  + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    /**
     * ���з�����Ʈ�ڽ�
     * @param selected      ���ð�
     * @return result       select box ���� String
     * @throws Exception
     */
    public static String getDicGroupSelect (String selected) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";

        String value = "";


        result  = "  <SELECT name='p_groups' > \n";

        try {
            connMgr = new DBConnectionManager();

            sql  = " select groups from TZ_DICGROUP ";
            sql += " order by seq asc               ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                value = ls.getString("groups");

                result += " <option value=" + value;
                if (selected.equals(value)) {
                    result += " selected ";
                }

                result += "> " + value + " </option> \n";
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

        result += "  </SELECT> \n";
        return result;
    }

}

