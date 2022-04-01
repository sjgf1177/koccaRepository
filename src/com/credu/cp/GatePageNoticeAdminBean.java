//**********************************************************
//  1. ��      ��: ���ְ����ý����� �������׺�
//  2. ���α׷���: GatePageNoticeAdminBean.java
//  3. ��      ��: ���ְ����ý��� �������׺� ����
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �̿��� 2005. 08.  05
//  7. ��      ��: �̳��� 05.11.16 _ ����Ŭ -> MSSQL (OuterJoin) ����
//**********************************************************
package com.credu.cp;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.dunet.common.util.StringUtil;
import com.namo.*;

public class GatePageNoticeAdminBean {
    private ConfigSet config;
    private int row;
    private String v_type = "AE";
    private static final String FILE_TYPE = "p_file";           //      ���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 5;                    //    �������� ���õ� ����÷�� ����

    public GatePageNoticeAdminBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

  /**
    * gatepage�������װ��� ȭ�� ����Ʈ
    * @param box          receive from the form object and session
    * @return ArrayList   �������� ����Ʈ
    */
    public ArrayList selectPdsList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
		String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
        int v_pageno = box.getInt("p_pageno");
		// v_type = box.getString("p_gubun");
		 //if (v_type.equals("")){
		//	 v_type = "AE";
		// }

        try {
            connMgr = new DBConnectionManager();
            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			////------------------------------------------------------------------------------------

            list = new ArrayList();

            sql = "select a.seq, a.adname, a.adtitle, count(b.realfile) filecnt, a.addate, a.cnt";
            sql += " from tz_Notice a, tz_boardfile b";
			// ������ : 05.11.16 ������ : �̳��� _(+)  ����
//          sql += " where a.seq = b.seq(+) and a.tabseq = b.tabseq(+) and  a.tabseq =" + v_tabseq;
			sql += " where a.seq  =  b.seq(+) and a.tabseq  =  b.tabseq(+) and  a.tabseq =" + v_tabseq;

            if ( !v_searchtext.equals("")) {      //    �˻�� ������
                v_pageno = 1;   //      �˻��� ��� ù��° �������� �ε��ȴ�

               if (v_select.equals("title")) {     //    �������� �˻��Ҷ�
                    sql += " and a.adtitle like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
                else if (v_select.equals("content")) {     //    �������� �˻��Ҷ�
                    sql += " and a.content like " + StringManager.makeSQL("%" + v_searchtext + "%");            //   Oracle 9i ��
                }
            }
            sql += " group by a.seq, a.adname, a.adtitle, a.addate, a.cnt";
            sql += " order by a.seq desc";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ls = new ListSet(pstmt);        //      ListSet (ResultSet) ��ü����

            ls.setPageSize(row);             //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno);                    //     ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage();       //     ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls.getTotalCount();   	//     ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {
                //-------------------   2003.12.25  ����     -------------------------------------------------------------------
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));
                list.add(dbox);
               }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      �� �ݾ��ش�
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

  /**
    * gatepage�������װ��� �󼼺���
    * @param box          receive from the form object and session
    * @return DataBox	  ��ȸ�� ���� DataBox�� ��� ����
    */
   public DataBox selectPds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_fileseq      = box.getString("p_fileseq");
        Vector realfileVector = new Vector();
        Vector savefileVector = new Vector();
        Vector fileseqVector  = new Vector();
        int    v_upfilecnt    = (box.getInt("p_upfilecnt")>0?box.getInt("p_upfilecnt"):1);

        try {
            connMgr = new DBConnectionManager();
			//----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------

            sql = "select a.seq, a.adname, a.adtitle, a.adcontent, b.fileseq, b.realfile, b.savefile, a.addate, a.cnt";
            sql += " from tz_notice a, tz_boardfile b";
			sql += " where a.seq  =  b.seq(+) and a.tabseq  =  b.tabseq(+) and a.tabseq = " + v_tabseq + "and a.seq = " + v_seq;
            ls = connMgr.executeQuery(sql);

            while(ls.next()) {
                dbox = ls.getDataBox();
                realfileVector.addElement(ls.getString("realfile"));
                savefileVector.addElement(ls.getString("savefile"));
                fileseqVector.addElement(String.valueOf(ls.getInt("fileseq")));
            }
            if (realfileVector  != null) dbox.put("d_realfile", realfileVector);
            if (savefileVector  != null) dbox.put("d_savefile", savefileVector);
            if (fileseqVector   != null) dbox.put("d_fileseq", fileseqVector);

            //------------------------------------------------------------------------------------------------------------------------------------

			connMgr.executeUpdate("update tz_notice set cnt = cnt + 1 where seq = " + v_seq );
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
    * gatepage�������װ��� ����ϱ�
    * @param box          receive from the form object and session
    * @return isOk1*isOk2	  ��ȸ�� ���� DataBox�� ��� ����
    */
     public int insertPds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 1;
        int isOk2 = 1;

        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_content  = StringUtil.removeTag(box.getString("p_content"));
        String v_realMotionName = box.getRealFileName("p_motion");
        String v_newMotionName = box.getNewFileName("p_motion");

        String s_userid = "";
        String s_usernm = "";
		String s_gadmin = box.getSession("gadmin");

		if (s_gadmin.equals("A1")){
			s_usernm = "���";
		}else{
			s_usernm = box.getSession("name");
		}

		if (s_gadmin.equals("A1")){
			s_userid = "���";
		}else{
			s_userid = box.getSession("userid");
		}

		//v_type = box.getString("p_gubun");
		String v_gubun = "Y";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format( v_type) ;
				ls = connMgr.executeQuery(sql);
				ls.next();
				int v_tabseq = ls.getInt(1);
				ls.close();
			//------------------------------------------------------------------------------------
            //----------------------   �Խ��� ��ȣ �����´� ----------------------------
            sql = "select NVL(max(seq), 0) from tz_notice";
	            ls = connMgr.executeQuery(sql);
	            ls.next();
	            int v_seq = ls.getInt(1) + 1;
	            ls.close();
            //------------------------------------------------------------------------------------
				/*********************************************************************************************/
				// ���𿡵��� ���� ó��  (Mime Document Parsing �� �̹��� ���ε�)
				ConfigSet conf = new ConfigSet();
				SmeNamoMime namo = new SmeNamoMime(v_content); // ��ü����
				boolean result = namo.parse(); // ���� �Ľ� ����
				if ( !result ) { // �Ľ� ���н�
					System.out.println( namo.getDebugMsg() ); // ����� �޽��� ���
					return 0;
				}
				if ( namo.isMultipart() ) { // ������ ��Ƽ��Ʈ���� �Ǵ�
					String v_server = conf.getProperty("autoever.url.value");
					String fPath = conf.getProperty("dir.namo");   // ���� ���� ��� ����
					String refUrl = conf.getProperty("url.namo");; // ������ ����� ������ �����ϱ� ���� ���
					String prefix =  "cpNotice" + v_seq;         // ���ϸ� ���ξ�
					result = namo.saveFile(fPath, v_server+refUrl, prefix); // ���� ���� ����
				}
				if ( !result ) { // �������� ���н�
					System.out.println( namo.getDebugMsg() ); // ����� �޽��� ���
					return 0;
				}
				v_content = namo.getContent(); // ���� ����Ʈ ���
				/*********************************************************************************************/

            //////////////////////////////////   �Խ��� table �� �Է�  ///////////////////////////////////////////////////////////////////
            sql1 =  "insert into tz_notice (tabseq,seq,gubun,addate,adtitle,adname,adcontent,luserid,ldate)";

            sql1 += " values (?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),? , ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";
            //<---  empty_clob() ����(Oracle �� ���)

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt   (1, v_tabseq);
            pstmt1.setInt   (2, v_seq);
			pstmt1.setString(3, v_gubun);
			pstmt1.setString(4, v_title);
            pstmt1.setString(5, s_usernm);
			pstmt1.setCharacterStream(6, new StringReader(v_content), v_content.length());
            pstmt1.setString(7, s_userid);

			isOk1 = pstmt1.executeUpdate();     //      ���� �ش� content �� empty_clob()�� �����ϰ� ���� ���� ��Ʈ������ ġȯ�Ѵ�.
            isOk2 = this.insertUpFile(connMgr,v_tabseq, v_seq, box);
            if(isOk1 > 0 && isOk2 > 0) {
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
            }
        }
        catch (Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            FileManager.deleteFile(v_newMotionName);
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2;
    }

    /**
    * gatepage�������װ��� �����ϱ�
    * @param box          receive from the form object and session
    * @return isOk1*isOk2*isOk3	  ������ �����ϸ� 1�� �����Ѵ�
    */
     public int updatePds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
		ListSet ls = null;
        Connection conn = null;
        PreparedStatement pstmt1 = null;
		String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 1;
        int isOk2 = 1;
        int isOk3 = 1;

        int v_seq = box.getInt("p_seq");
        int v_upfilecnt = box.getInt("p_upfilecnt");    //  ������ ������ִ� ���ϼ�
        String v_title = box.getString("p_title");
        String v_content =  StringManager.replace(StringUtil.removeTag(box.getString("p_content")),"&","&amp;");
        Vector v_savefile     = box.getVector("p_savefile"); //���û�������
        Vector v_filesequence = box.getVector("p_fileseq");  //���û������� sequence
        Vector v_realfile     = box.getVector("p_file");     //���� ��� ����
        for(int i = 0; i < v_upfilecnt; i++) {
            if( !box.getString("p_fileseq" + i).equals("")) {
                v_savefile.addElement(box.getString("p_savefile" + i));         //      ������ ������ִ� ���ϸ� �߿��� ������ ���ϵ�
                v_filesequence.addElement(box.getString("p_fileseq" + i));       //     ������  ������ִ� ���Ϲ�ȣ �߿��� ������ ���ϵ�
            }
        }

       // String v_realMotionName = box.getRealFileName("p_motion");
      //  String v_newMotionName = box.getNewFileName("p_motion");

        String s_userid = "";
        String s_usernm = "";
		String s_gadmin = box.getSession("gadmin");

		if (s_gadmin.equals("A1")){
			s_usernm = "���";
		}else{
			s_usernm = box.getSession("name");
		}
		s_userid = box.getSession("userid");

        /*********************************************************************************************/
        // ���𿡵��� ���� ó��  (Mime Document Parsing �� �̹��� ���ε�)
        ConfigSet conf = new ConfigSet();
        SmeNamoMime namo = new SmeNamoMime(v_content); // ��ü����
        boolean result = namo.parse(); // ���� �Ľ� ����
        System.out.println(" gatePageNoticeAdmin result = "+result);

        if ( !result ) { // �Ľ� ���н�
            System.out.println( namo.getDebugMsg() ); // ����� �޽��� ���
            return 0;
        }
        if ( namo.isMultipart() ) { // ������ ��Ƽ��Ʈ���� �Ǵ�
            String v_server = conf.getProperty("autoever.url.value");
            String fPath  = conf.getProperty("dir.namo");   // ���� ���� ��� ����
            String refUrl = conf.getProperty("url.namo");; // ������ ����� ������ �����ϱ� ���� ���
            String prefix = "cpNotice" + v_seq;         // ���ϸ� ���ξ�
            result = namo.saveFile(fPath, v_server+refUrl, prefix); // ���� ���� ����
            System.out.println(result);
        }
        if ( !result ) { // �������� ���н�
            System.out.println( namo.getDebugMsg() ); // ����� �޽��� ���
            return 0;
        }
        v_content = namo.getContent(); // ���� ����Ʈ ���
		/*********************************************************************************************/

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------

//              sql1 = "update tz_notice set adtitle = ?, adname = ?, adcontent = empty_clob(), luserid = ?, ldate =  to_char(sysdate, 'YYYYMMDDHH24MISS')";
              sql1 = "update tz_notice set adtitle = ?, adname = ?, adcontent = ?, luserid = ?, ldate =  to_char(sysdate, 'YYYYMMDDHH24MISS')";
                sql1 += "  where tabseq = ? and seq = ?";
int index = 1;

				pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setString(index++, v_title);
                pstmt1.setString(index++, s_usernm);
pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
//				pstmt1.setString(index++, v_content);
				pstmt1.setString(index++, s_userid);
				pstmt1.setInt   (index++, v_tabseq);
				pstmt1.setInt   (index++, v_seq);

            isOk1 = pstmt1.executeUpdate();

//            sql2 = "select adcontent from tz_notice where tabseq = " + v_tabseq + " and seq = " + v_seq;
//            connMgr.setOracleCLOB(sql2, v_content);       //      clob �ʵ忡 ��Ʈ���� �̿�,  data �� �ִ´�(Weblogic �� ���)
			/* 05.11.15 �̳���
            //WebLogic 6.1�ΰ��
            */

            isOk2 = this.insertUpFile(connMgr, v_tabseq, v_seq, box);       //      ����÷���ߴٸ� ����table��  insert
            isOk3 = this.deleteUpFile(connMgr, box);        //     ������ ������ �ִٸ� ����table���� ����

            if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
                connMgr.commit();
                if (v_savefile != null) {
                    FileManager.deleteFile(v_savefile);         //   DB ���� ���ó���� �Ϸ�Ǹ� �ش� ÷������ ����
                }
            } else connMgr.rollback();
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2*isOk3;
    }

    /**
    * gatepage�������װ��� �����ϱ�
    * @param box          receive from the form object and session
    * @return isOk1*isOk2 ������ �����ϸ� 1�� �����Ѵ�
    */
    public int deletePds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
		ListSet ls = null;
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
		String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 1;
        int isOk2 = 1;

        int v_seq = box.getInt("p_seq");
        Vector savefile  = box.getVector("p_savefile");
        String v_savemotion = box.getString("p_savemotion");
        //v_type = box.getString("p_gubun");
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------
            sql1 = "delete from tz_notice where tabseq = " + v_tabseq + " and  seq = ?";
            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(1, v_seq);
            isOk1 = pstmt1.executeUpdate();

            for (int i = 0; i < savefile.size() ;i++ ){
				String str = (String)savefile.elementAt(i);
				if (!str.equals("")){
					isOk2 = this.deleteUpFile(connMgr, box);
				}
			}

            if (isOk1 > 0 && isOk2 > 0) {
                if (savefile != null) {
                    FileManager.deleteFile(savefile);         //	 ÷������ ����
                }
                if (v_savemotion != null) {
                    FileManager.deleteFile(v_savemotion);
                }

                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
                Log.info.println(this, box, "delete process to " + v_seq);
            }
        }
        catch (Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2;
    }

    /**
    * QNA ���ο� �ڷ����� ���
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */

     public int insertUpFile(DBConnectionManager connMgr, int p_tabseq, int p_seq, RequestBox   box) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        int isOk2 = 1;
       // String v_type = box.getString("p_type");
        //----------------------   ���ε�Ǵ� ������ ������ �˰� �ڵ��ؾ��Ѵ�  --------------------------------

        String [] v_realFileName = new String [FILE_LIMIT];
        String [] v_newFileName = new String [FILE_LIMIT];

        for(int i = 0; i < FILE_LIMIT; i++) {
            v_realFileName [i] = box.getRealFileName(FILE_TYPE + (i+1));
            v_newFileName [i] = box.getNewFileName(FILE_TYPE + (i+1));
        }
        //----------------------------------------------------------------------------------------------------------------------------

        String s_userid = box.getSession("userid");

        try {
             //----------------------   �ڷ� ��ȣ �����´� ----------------------------
            sql = "select NVL(max(fileseq), 0) from TZ_BOARDFILE    where tabseq = "+p_tabseq+" and seq =   " + p_seq;

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_fileseq = ls.getInt(1) + 1;
            ls.close();
            //------------------------------------------------------------------------------------

            //////////////////////////////////   ���� table �� �Է�  ///////////////////////////////////////////////////////////////////
            sql2 =  "insert into TZ_BOARDFILE(tabseq, seq, fileseq, realfile, savefile, luserid,    ldate)";
            sql2 += " values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            pstmt2 = connMgr.prepareStatement(sql2);

            for(int i = 0; i < FILE_LIMIT; i++) {
                if( !v_realFileName [i].equals("")) {       //      ���� ���ε� �Ǵ� ���ϸ� üũ�ؼ� db�� �Է��Ѵ�
                    pstmt2.setInt(1, p_tabseq);
                    pstmt2.setInt(2, p_seq);
                    pstmt2.setInt(3, v_fileseq);
                    pstmt2.setString(4, v_realFileName[i]);
                    pstmt2.setString(5, v_newFileName[i]);
                    pstmt2.setString(6, s_userid);

                    isOk2 = pstmt2.executeUpdate();
                    v_fileseq++;
                }
            }
        }
        catch (Exception ex) {
            FileManager.deleteFile(v_newFileName, FILE_LIMIT);      //  �Ϲ�����, ÷������ ������ ����..
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
        }
        return isOk2;
    }


///////////////////////////////////////////////////////  ���� ���̺�   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     /**
    * gatepage�������װ��� ���� �Է��ϱ�
    * @param box          receive from the form object and session
    * @return isOk2 �Է¿� �����ϸ� 1�� �����Ѵ�

     public int insertUpFile(DBConnectionManager connMgr, int p_seq, RequestBox box) throws Exception {
        ListSet ls = null;

        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        int isOk2 = 1;

        //----------------------   ���ε�Ǵ� ���� --------------------------------
        Vector realFileNames = box.getRealFileNames("p_file");
        Vector newFileNames = box.getNewFileNames("p_file");
        //----------------------------------------------------------------------------------------------------------------------------

        String s_userid = box.getSession("userid");

         try {
            if(realFileNames != null) {          //
				//----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
				sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
				ls = connMgr.executeQuery(sql);
				ls.next();
				int v_tabseq = ls.getInt(1);
				ls.close();
			//------------------------------------------------------------------------------------
                 //----------------------   �ڷ� ��ȣ �����´� ----------------------------
                sql = "select NVL(max(fileseq), 0) from tz_boardfile where tabseq = " + v_tabseq;
                ls = connMgr.executeQuery(sql);
                ls.next();
                int v_fileseq = ls.getInt(1) + 1;
                ls.close();
                //------------------------------------------------------------------------------------

                //////////////////////////////////   ���� table �� �Է�  ///////////////////////////////////////////////////////////////////
                sql2 =  "insert into tz_boardfile(tabseq, seq, fileseq, realfile, savefile, luserid, ldate)";
                sql2 += " values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

                pstmt2 = connMgr.prepareStatement(sql2);

                for(int i = 0; i < realFileNames.size(); i++) {

		            pstmt2.setInt(1, v_tabseq);
                    pstmt2.setInt(2, p_seq);
                    pstmt2.setInt(3, v_fileseq);
                    pstmt2.setString(4, (String)realFileNames.elementAt(i));
                    pstmt2.setString(5, (String)newFileNames.elementAt(i));
                    pstmt2.setString(6, s_userid);

                    isOk2 = pstmt2.executeUpdate();
                    v_fileseq++;
                }
            }
        }
        catch (Exception ex) {
            FileManager.deleteFile(newFileNames);		//  �Ϲ�����, ÷������ ������ ����..
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
        }
        return isOk2;
    }
    */
    /**
    * gatepage�������װ��� ���� �����ϱ�
    * @param box          receive from the form object and session
    * @return isOk3 ������ �����ϸ� 1�� �����Ѵ�
    */
    public int deleteUpFile(DBConnectionManager connMgr, RequestBox box) throws Exception {
        PreparedStatement pstmt3 = null;
		String sql = "";
        String sql3 = "";
        int isOk3 = 1;
        ListSet ls = null;
        int v_seq = box.getInt("p_seq");
        Vector v_savefileVector = box.getVector("p_savefile");

        try {
			//----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------
            sql3 = "delete from tz_boardfile where tabseq = " + v_tabseq + " and seq = ? and savefile = ?";

            pstmt3 = connMgr.prepareStatement(sql3);

            for(int i = 0; i < v_savefileVector.size(); i++) {
                String v_savefile = (String)v_savefileVector.elementAt(i);

                pstmt3.setInt(1, v_seq);
                pstmt3.setString(2, v_savefile);

                isOk3 = pstmt3.executeUpdate();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql3);
            throw new Exception("sql = " + sql3 + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e) {} }
        }
        return isOk3;
    }
}
