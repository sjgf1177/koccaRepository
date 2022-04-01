
//**********************************************************
//  1. 제      목: 외주관리시스템의 회사별게시판빈
//  2. 프로그램명: CpWorkBean.java
//  3. 개      요: 외주관리시스템 회사별게시판 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 08.  05
//  7. 수      정: 이나연 05.11.16 _ connMgr.setOracleCLOB 수정
//**********************************************************
package com.credu.cp;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;
import com.namo.SmeNamoMime;

public class CpWorkBean {
    private ConfigSet config;
    private int row;
//  private String v_type = "AF";
    private static final String FILE_TYPE = "p_file";           //      파일업로드되는 tag name
    private static final int FILE_LIMIT = 5;                    //    페이지에 세팅된 파일첨부 갯수

    public CpWorkBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
  /**
    * 회사별게시판 화면 리스트
    * @param box          receive from the form object and session
    * @return ArrayList   QNA 리스트
    */
    public ArrayList selectPdsList(RequestBox box,String v_type) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
		/* 2005.11.10_하경태 : TotalCount 해주기 위한 쿼리를 담을 변수 "*/
        String sql     = "";
        String count_sql     = "";
        String head_sql     = "";
		String body_sql     = "";
        String group_sql     = "";
        String order_sql     = "";

        DataBox dbox = null;
        String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
        int v_pageno = box.getInt("p_pageno");

        String s_userid = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");
        String s_cpseq = box.getSession("cpseq");
        String v_cpseq = box.getStringDefault("p_cpseq","ALL");

        try {
            connMgr = new DBConnectionManager();
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------

            list = new ArrayList();
            head_sql =  " select a.seq, a.userid, a.name, a.title, a.indate, a.cnt,a.okyn1,a.okuserid1,a.okdate1,a.okyn2,a.okuserid2,a.okdate2,";
			head_sql += "(select count(realfile) from TZ_BOARDfile where tabseq = a.tabseq and seq = a.seq) filecnt, ";
			head_sql += " c.cpnm ";
            body_sql += " from TZ_BOARD a, tz_cpinfo c ";
			// 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
//          sql += " where a.cpseq = c.cpseq(+) ";
			body_sql += " where a.cpseq  =  c.cpseq(+) ";
			body_sql += " and  a.tabseq = " + v_tabseq ;

            if (s_gadmin.equals("A1")  ||  s_gadmin.equals("A2") || s_gadmin.equals("H1")){ //총괄
                if (!"ALL".equals(v_cpseq)){
					body_sql += " and a.cpseq = " + SQLString.Format(v_cpseq);
                }
            }
            else{                                                   //업체담당자
                if("".equals(s_cpseq)){
					body_sql += " and a.cpseq = " + SQLString.Format(v_cpseq);

                }else{
					body_sql += " and a.cpseq = " + SQLString.Format(s_cpseq);
                }
            }

            //if (!("ALL".equals(v_cpseq) || "".equals(v_cpseq))){
            //  System.out.println("v_cpseq:::"+v_cpseq);
            //  sql += " and c.cpseq = " + SQLString.Format(v_cpseq);
            //}
            if ( !v_searchtext.equals("ALL")) {      //    검색어가 있으면

                if (v_select.equals("name")) {      //    이름으로 검색할때
					body_sql += " and a.name like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
                else if (v_select.equals("title")) {     //    제목으로 검색할때
					body_sql += " and a.title like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
                else if (v_select.equals("content")) {     //    내용으로 검색할때
					body_sql += " and a.content like " + StringManager.makeSQL("%" + v_searchtext + "%");            //   Oracle 9i 용
                }
            }
//          sql += " group by a.seq, a.userid, a.name, a.title, a.indate, a.cnt";
            order_sql += " order by a.seq desc";

			sql = head_sql + body_sql + group_sql + order_sql;

            ls = connMgr.executeQuery(sql);
			count_sql = "select count(*) " + body_sql;
			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);	//     전체 row 수를 반환한다
			int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다

            ls.setPageSize(row);             //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);                    //     현재페이지번호를 세팅한다.

            while (ls.next()) {
                //-------------------   2003.12.25  변경     -------------------------------------------------------------------
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
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

   /**
   * 회사별게시판 상세보기
   * @param box          receive from the form object and session
   * @return DataBox     조회한 값을 DataBox에 담아 리턴
   */

   public DataBox selectPds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_type         = box.getString("p_type");
        String s_cpseq        = box.getSession("cpseq");
        String v_fileseq      = box.getString("p_fileseq");
        Vector realfileVector = new Vector();
        Vector savefileVector = new Vector();
        Vector fileseqVector  = new Vector();
        int    v_upfilecnt    = (box.getInt("p_upfilecnt")>0?box.getInt("p_upfilecnt"):1);

        int [] fileseq = new int [v_upfilecnt];
        try {
            connMgr = new DBConnectionManager();
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------
            sql = "select a.seq, a.userid, a.name, a.title, a.content, a.indate, a.cnt,a.okyn1, a.okuserid1, a.okyn2, a.okuserid2, a.okdate1, a.okdate2,b.fileseq, b.realfile, b.savefile, ";
            sql += "(select cpnm from tz_cpinfo x where x.cpseq = a.cpseq) cpnm";
            sql += " from TZ_BOARD a, TZ_BOARDfile b";
			// 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
//          sql += " where a.tabseq = b.tabseq(+) and a.seq = b.seq(+) and a.tabseq = " + v_tabseq + " and a.seq = "+ v_seq;
			sql += " where a.tabseq  =  b.tabseq(+) and a.seq  =  b.seq(+) and a.tabseq = " + v_tabseq + " and a.seq = "+ v_seq;

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

            connMgr.executeUpdate("update TZ_BOARD set cnt = cnt + 1 where tabseq = " + v_tabseq + " and seq = "+v_seq );
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
    * 회사별게시판 등록하기
    * @param box          receive from the form object and session
    * @return isOk1*isOk2     조회한 값을 DataBox에 담아 리턴
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
		int isOk3 = 1;
		int isOk4 = 1;

        String v_type  = box.getString("p_type");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_realMotionName = box.getRealFileName("p_motion");
        String v_newMotionName = box.getNewFileName("p_motion");
        String v_content  = StringUtil.removeTag(box.getString("p_content"));
        String v_content1  = "";

        String s_gadmin   = box.getSession("gadmin");
        String s_userid   = box.getSession("userid");
        String s_usernm   = "";
        String v_cpseq = "";
        Vector v_cpseqVec     = new Vector();

        v_cpseqVec  = box.getVector("p_cpseq");
        Enumeration em   = v_cpseqVec.elements();

        if (s_gadmin.equals("A1")){
            s_usernm = "운영자";
        }else{
            s_usernm = box.getSession("name");
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

           //////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
            sql1 =  "insert into TZ_BOARD(TABSEQ,SEQ,TITLE,USERID,NAME,CONTENT,INDATE,REFSEQ,LEVELS,POSITION,CPSEQ)";
//          sql1 += " values (?, ?, ?, ?, ?, empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, 1, 1, ?)";
          sql1 += " values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, 1, 1, ?)";

            while(em.hasMoreElements()){
			v_cpseq    = (String)em.nextElement();

	 //----------------------   게시판 번호 가져온다 ----------------------------
	 sql = "select NVL(max(seq), 0) from TZ_BOARD";
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_seq = ls.getInt(1) + 1;
			ls.close();
			/*********************************************************************************************/
			// 나모에디터 본문 처리  (Mime Document Parsing 및 이미지 업로드)
			ConfigSet conf = new ConfigSet();
			SmeNamoMime namo = new SmeNamoMime(v_content); // 객체생성
			boolean result = namo.parse(); // 실제 파싱 수행
			if ( !result ) { // 파싱 실패시
				System.out.println( namo.getDebugMsg() ); // 디버깅 메시지 출력
				return 0;
			}
			if ( namo.isMultipart() ) { // 문서가 멀티파트인지 판단
				String v_server = conf.getProperty("autoever.url.value");
				String fPath = conf.getProperty("dir.namo");   // 파일 저장 경로 지정
				String refUrl = conf.getProperty("url.namo");; // 웹에서 저장된 파일을 접근하기 위한 경로
				String prefix =  "cpCompany" + v_seq;         // 파일명 접두어
				result = namo.saveFile(fPath, v_server+refUrl, prefix); // 실제 파일 저장
			}
			if ( !result ) { // 파일저장 실패시
				System.out.println( namo.getDebugMsg() ); // 디버깅 메시지 출력
				return 0;
			}
			v_content1 = namo.getContent(); // 최종 컨텐트 얻기
			/*********************************************************************************************/
int index = 1;

			pstmt1 = connMgr.prepareStatement(sql1);
			pstmt1.setInt   (index++,v_tabseq);
			pstmt1.setInt   (index++, v_seq);
			pstmt1.setString(index++, v_title);
			pstmt1.setString(index++, s_userid);
			pstmt1.setString(index++, s_usernm);
			pstmt1.setCharacterStream(index++, new StringReader(v_content1), v_content1.length());
//			pstmt1.setString(index++, v_content);
			pstmt1.setInt   (index++, v_seq);
			pstmt1.setString(index++, v_cpseq);

			isOk1 = pstmt1.executeUpdate();     //      먼저 해당 content 에 empty_clob()을 적용하고 나서 값을 스트림으로 치환한다.
//			 sql2 = "select content from TZ_BOARD where tabseq = " + v_tabseq + " and seq = " + v_seq ;
//			 connMgr.setOracleCLOB(sql2, v_content1);       //      (기타 서버 경우)
			 /* 05.11.15 이나연
			 */
			isOk2 = this.insertUpFile(connMgr,v_tabseq, v_seq, box);
			isOk3 = isOk1*isOk3;
			isOk4 = isOk2*isOk4;
        }
            if(isOk3 > 0 && isOk4 > 0) {
				isOk1 = 1;
                connMgr.commit();
            }else{
				isOk1 = 0;
				connMgr.rollback();
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
        return isOk1;
    }


   /**
    * 회사별게시판 수정하기
    * @param box          receive from the form object and session
    * @return isOk1*isOk2*isOk3   수정에 성공하면 1을 리턴한다

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
        int v_upfilecnt = box.getInt("p_upfilecnt");    //  서버에 저장되있는 파일수

        String v_title = box.getString("p_title");
        String v_content =  StringManager.replace(box.getString("content"),"&","&amp;");
        String v_savemotion = box.getString("p_savemotion");
        String v_userid = box.getString("p_userid");

        String v_realMotionName = box.getRealFileName("p_motion");
        String v_newMotionName = box.getNewFileName("p_motion");

        String s_gadmin = box.getSession("gadmin");
        String s_userid = "";
        String s_usernm = "";
        if (s_gadmin.equals("A1")){
            s_usernm = "운영자";
        }else{
            s_usernm = box.getSession("name");
        }

        if (s_gadmin.equals("A1")){
            s_userid = "운영자";
        }else{
            s_userid = box.getSession("userid");
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
                sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
                ls = connMgr.executeQuery(sql);
                ls.next();
                int v_tabseq = ls.getInt(1);
                ls.close();
                //------------------------------------------------------------------------------------
            if ( !v_newMotionName.equals("")) {     //      수정할 첨부파일이 있다면..

                sql1 = "update TZ_BOARD set title = ?, userid = ?, name = ?, luserid = ?, content = empty_clob(), indate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
                sql1 += "  where tabseq = " + v_tabseq + " and seq = ?";

                pstmt1 = connMgr.prepareStatement(sql1);

                pstmt1.setString(1, v_title);
                pstmt1.setString(2, v_userid);
                pstmt1.setString(3, s_usernm);
                pstmt1.setString(4, s_userid);
                pstmt1.setInt(5, v_seq);



            }
            else {     //       첨부파일이 없다면..
                sql1 = "update TZ_BOARD set title = ?, userid = ?, name = ?, luserid = ?, content = empty_clob(), indate = to_char(sysdate, 'YYYYMMDDHH24MISS'),ldate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
                sql1 += "  where tabseq = " + v_tabseq + " and seq = ? ";

                pstmt1 = connMgr.prepareStatement(sql1);

                pstmt1.setString(1, v_title);
                pstmt1.setString(2, s_userid);
                pstmt1.setString(3, s_usernm);
                pstmt1.setString(4, s_userid);
                pstmt1.setInt(5, v_seq);

            }

            isOk1 = pstmt1.executeUpdate();

            sql2 = "select content from TZ_BOARD where tabseq = "+ v_tabseq +" and seq = " + v_seq ;


                connMgr.setOracleCLOB(sql2, v_content);       //      (기타 서버 경우)



            if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
                if (v_savemotion != null) {
                    FileManager.deleteFile(v_savemotion);         //     DB 에서 모든처리가 완료되면 해당 첨부파일 삭제
                }
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
            }
            Log.info.println(this, box, "update process to " + v_seq);
        }
        catch(Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            FileManager.deleteFile(v_newMotionName);        // 첨부파일 있으면 삭제..
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

    // */
    /**
    * 회사별게시판 수정하여 저장할때
    * @param box      receive from the form object and session
    * @return isOk    1:update success,0:update fail
    * @throws Exception
    */
     public int updatePds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs1 = null;
        PreparedStatement pstmt1  = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";

        ListSet ls = null;
        int isOk1 = 1, isOk2=1, isOk3=1;
        int    v_tabseq    = box.getInt("p_tabseq");
        int    v_seq       = box.getInt("p_seq");
        int    v_upfilecnt = box.getInt("p_upfilecnt");    //  서버에 저장되있는 파일수
        String v_type      = box.getString("p_type");
        String v_title     = box.getString("p_title");
        String v_content =  StringManager.replace(StringUtil.removeTag(box.getString("p_content")),"<br>","\n");

        Vector v_savefile     = box.getVector("p_savefile"); //선택삭제파일
        Vector v_filesequence = box.getVector("p_fileseq");  //선택삭제파일 sequence
        Vector v_realfile     = box.getVector("p_file");     //새로 등록 파일

        for(int i = 0; i < v_upfilecnt; i++) {
            if( !box.getString("p_fileseq" + i).equals("")) {
                v_savefile.addElement(box.getString("p_savefile" + i));         //      서버에 저장되있는 파일명 중에서 삭제할 파일들
                v_filesequence.addElement(box.getString("p_fileseq" + i));       //     서버에  저장되있는 파일번호 중에서 삭제할 파일들
            }
        }

        String s_userid = box.getSession("userid");
        String s_usernm = box.getSession("name");

        if (box.getSession("gadmin").substring(0,1).equals("A")) {
            s_usernm = "운영자";
        }
        /*********************************************************************************************/
        // 나모에디터 본문 처리  (Mime Document Parsing 및 이미지 업로드)
        ConfigSet conf = new ConfigSet();
        SmeNamoMime namo = new SmeNamoMime(v_content); // 객체생성
        boolean result = namo.parse(); // 실제 파싱 수행
        System.out.println(result);

        if ( !result ) { // 파싱 실패시
            System.out.println( namo.getDebugMsg() ); // 디버깅 메시지 출력
            return 0;
        }
        if ( namo.isMultipart() ) { // 문서가 멀티파트인지 판단
            String v_server = conf.getProperty("autoever.url.value");
            String fPath = conf.getProperty("dir.namo");   // 파일 저장 경로 지정
            String refUrl = conf.getProperty("url.namo");; // 웹에서 저장된 파일을 접근하기 위한 경로
            String prefix = "cpCompany" + v_seq;         // 파일명 접두어
            result = namo.saveFile(fPath, v_server+refUrl, prefix); // 실제 파일 저장
            System.out.println(result);
        }
        if ( !result ) { // 파일저장 실패시
            System.out.println( namo.getDebugMsg() ); // 디버깅 메시지 출력
            return 0;
        }
        v_content = namo.getContent(); // 최종 컨텐트 얻기
        /*********************************************************************************************/

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
                sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
                ls = connMgr.executeQuery(sql);
                ls.next();
                v_tabseq = ls.getInt(1);
                ls.close();
            //------------------------------------------------------------------------------------

//          sql1 = "update TZ_BOARD set title = ?, content=empty_clob(), userid = ?, name = ?, luserid = ?, ldate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
          sql1 = "update TZ_BOARD set title = ?, content=?, userid = ?, name = ?, luserid = ?, ldate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
            sql1 += "  where tabseq = ? and seq = ?";

int index = 1;
            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setString(index++, v_title);
            pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
		//	pstmt1.setString(index++, v_content);
            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, s_usernm);
            pstmt1.setString(index++, s_userid);
            pstmt1.setInt(index++, v_tabseq);
            pstmt1.setInt(index++, v_seq);

            isOk1 = pstmt1.executeUpdate();
//            sql2 = "select content from TZ_BOARD where tabseq = " + v_tabseq + " and seq = " + v_seq;
//            connMgr.setOracleCLOB(sql2, v_content);       //      clob 필드에 스트림을 이용,  data 를 넣는다(Weblogic 인 경우)
			/* 05.11.15 이나연
            //WebLogic 6.1인경우
            */
            isOk2 = this.insertUpFile(connMgr, v_tabseq, v_seq, box);       //      파일첨부했다면 파일table에  insert
            isOk3 = this.deleteUpFile(connMgr, box, v_filesequence);        //     삭제할 파일이 있다면 파일table에서 삭제

            if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
                connMgr.commit();
                if (v_savefile != null) {
                    FileManager.deleteFile(v_savefile);         //   DB 에서 모든처리가 완료되면 해당 첨부파일 삭제
                }
            } else connMgr.rollback();
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(rs1 != null) { try { rs1.close(); } catch (Exception e) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2*isOk3;
    }


		/**
    * 회사별게시판 답변하기
    * @param box          receive from the form object and session
    * @return isOk1*isOk2     조회한 값을 DataBox에 담아 리턴
    */
     public int replyPds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 1;
        int isOk2 = 1;
		int isOk3 = 1;
		int isOk4 = 1;

        String v_type  = box.getString("p_type");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_realMotionName = box.getRealFileName("p_motion");
        String v_newMotionName = box.getNewFileName("p_motion");
        String v_content  = StringUtil.removeTag(box.getString("p_content"));
        String v_content1  = "";

        String s_gadmin   = box.getSession("gadmin");
        String s_userid   = box.getSession("userid");
        String s_usernm   = "";
        String v_cpseq = "";
        Vector v_cpseqVec     = new Vector();

        v_cpseqVec  = box.getVector("p_cpseq");
        Enumeration em   = v_cpseqVec.elements();

        if (s_gadmin.equals("A1")){
            s_usernm = "운영자";
        }else{
            s_usernm = box.getSession("name");
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

           //////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
            sql1 =  "insert into TZ_BOARD(TABSEQ,SEQ,TITLE,USERID,NAME,CONTENT,INDATE,REFSEQ,LEVELS,POSITION,CPSEQ)";
//          sql1 += " values (?, ?, ?, ?, ?, empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, 1, 1, ?)";
          sql1 += " values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, 1, 1, ?)";

            while(em.hasMoreElements()){
			v_cpseq    = (String)em.nextElement();

	 //----------------------   게시판 번호 가져온다 ----------------------------
	 sql = "select NVL(max(seq), 0) from TZ_BOARD";
				ls = connMgr.executeQuery(sql);
				ls.next();
				int v_seq = ls.getInt(1) + 1;
				ls.close();
				/*********************************************************************************************/
				// 나모에디터 본문 처리  (Mime Document Parsing 및 이미지 업로드)
				ConfigSet conf = new ConfigSet();
				SmeNamoMime namo = new SmeNamoMime(v_content); // 객체생성
				boolean result = namo.parse(); // 실제 파싱 수행
				if ( !result ) { // 파싱 실패시
					System.out.println( namo.getDebugMsg() ); // 디버깅 메시지 출력
					return 0;
				}
				if ( namo.isMultipart() ) { // 문서가 멀티파트인지 판단
					String v_server = conf.getProperty("autoever.url.value");
					String fPath = conf.getProperty("dir.namo");   // 파일 저장 경로 지정
					String refUrl = conf.getProperty("url.namo");; // 웹에서 저장된 파일을 접근하기 위한 경로
					String prefix =  "cpCompany" + v_seq;         // 파일명 접두어
					result = namo.saveFile(fPath, v_server+refUrl, prefix); // 실제 파일 저장
				}
				if ( !result ) { // 파일저장 실패시
					System.out.println( namo.getDebugMsg() ); // 디버깅 메시지 출력
					return 0;
				}
				v_content1 = namo.getContent(); // 최종 컨텐트 얻기
				/*********************************************************************************************/

int index = 1;
				pstmt1 = connMgr.prepareStatement(sql1);
				pstmt1.setInt   (index++,v_tabseq);
				pstmt1.setInt   (index++, v_seq);
				pstmt1.setString(index++, v_title);
				pstmt1.setString(index++, s_userid);
				pstmt1.setString(index++, s_usernm);
pstmt1.setCharacterStream(index++, new StringReader(v_content1), v_content1.length());
//				pstmt1.setString(index++, v_content);
				pstmt1.setInt   (index++, v_seq);
				pstmt1.setString(index++, v_cpseq);

				 isOk1 = pstmt1.executeUpdate();     //      먼저 해당 content 에 empty_clob()을 적용하고 나서 값을 스트림으로 치환한다.
//				 sql2 = "select content from TZ_BOARD where tabseq = " + v_tabseq + " and seq = " + v_seq ;
//				 connMgr.setOracleCLOB(sql2, v_content1);       //      (기타 서버 경우)

				 isOk2 = this.insertUpFile(connMgr,v_tabseq, v_seq, box);

				 System.out.println("isOk2:"+isOk2);
				isOk3 = isOk1*isOk3;
				isOk4 = isOk2*isOk4;
            }
            if(isOk3 > 0 && isOk4 > 0) {
				isOk1 = 1;
                connMgr.commit();
            }else{
				isOk1 = 0;
				connMgr.rollback();
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
        return isOk1;
    }


////

   /**
    * 회사별게시판 삭제하기
    * @param box          receive from the form object and session
    * @return isOk1*isOk2 삭제에 성공하면 1을 리턴한다
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
        String v_type       = box.getString("p_type");
        Vector savefile     = box.getVector("p_savefile");
        String v_savemotion = box.getString("p_savemotion");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------
            sql1 = "delete from TZ_BOARD where tabseq = " + v_tabseq + " and seq = ?";

            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setInt(1, v_seq);

            isOk1 = pstmt1.executeUpdate();

            if(savefile.size()>0){
           // for (int i = 0; i < savefile.size() ;i++ ){
             //   String str = (String)savefile.elementAt(i);
             //   if (!str.equals("")){

                    sql2 = "delete from TZ_BOARDFILE where tabseq = "+ v_tabseq +" and seq =? ";

                    pstmt2 = connMgr.prepareStatement(sql2);
                    pstmt2.setInt(1, v_seq);
                    isOk2 = pstmt2.executeUpdate();
               // }
            }
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
                Log.info.println(this, box, "delete process to " + v_seq);

        }
        catch (Exception ex) {
//          if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
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
     * comp 셀렉트박스
     * @param name           셀렉트박스명
     * @param selected       선택값
     * @param event          이벤트명
     * @param allcheck       전체유무
     * @return
     * @throws Exception
     */
    public static String getCompSelecct(String name, String selected, String event, int allcheck, String s_gadmin,String userid) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;

        String sql = "";
        String result = "";
        result = "  <SELECT name= " + "\"" +name+ "\"" + " " + event + " >  \n";
        if(s_gadmin.equals("A1") || s_gadmin.equals("A2")){
            if (allcheck == 1) {
              result += "    <option value='ALL'>ALL</option>  \n";
            }
        }
        try {
            connMgr = new DBConnectionManager();
            sql  = " select cpseq,cpnm from tz_cpinfo ";

            if (!(s_gadmin.equals("A1") || s_gadmin.equals("A2") || s_gadmin.equals("H1")) ){
                System.out.println("s_gadmin:::"+s_gadmin);
                sql += " where cpseq = (select comp from tz_outcompman where userid = " + SQLString.Format(userid)+")";
            }
            sql += " order by cpseq asc                        ";
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                result += "    <option value=" + "\"" +dbox.getString("d_cpseq")  + "\"";
                if (selected.equals(dbox.getString("d_cpseq"))) {
                    result += " selected ";
                }
                result += ">" + dbox.getString("d_cpnm") + "</option>  \n";
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        result += "  </SELECT>  \n";
        return result;
    }


    /**
    업체명 checkbox 리스트
    @param box          receive from the form object and session
    @return ArrayList   Faq 리스트
**/
    public ArrayList selectCpinfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();
            sql  = " select cpseq, cpnm     ";
            sql += "   from tz_cpinfo   ";
            sql += "   order by cpseq asc";
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
    * QNA 새로운 자료파일 등록
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
        //----------------------   업로드되는 파일의 형식을 알고 코딩해야한다  --------------------------------

        String [] v_realFileName = new String [FILE_LIMIT];
        String [] v_newFileName = new String [FILE_LIMIT];

        for(int i = 0; i < FILE_LIMIT; i++) {
            v_realFileName [i] = box.getRealFileName(FILE_TYPE + (i+1));
            v_newFileName [i] = box.getNewFileName(FILE_TYPE + (i+1));
        }
        //----------------------------------------------------------------------------------------------------------------------------

        String s_userid = box.getSession("userid");
        try {
             //----------------------   자료 번호 가져온다 ----------------------------
            sql = "select NVL(max(fileseq), 0) from TZ_BOARDFILE    where tabseq = "+p_tabseq+" and seq =   " + p_seq;
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_fileseq = ls.getInt(1) + 1;
            ls.close();
            //------------------------------------------------------------------------------------

            //////////////////////////////////   파일 table 에 입력  ///////////////////////////////////////////////////////////////////
            sql2 =  "insert into TZ_BOARDFILE(tabseq, seq, fileseq, realfile, savefile, luserid,    ldate)";
            sql2 += " values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            pstmt2 = connMgr.prepareStatement(sql2);
            for(int i = 0; i < FILE_LIMIT; i++) {
                if( !v_realFileName [i].equals("")) {       //      실제 업로드 되는 파일만 체크해서 db에 입력한다
                    pstmt2.setInt(1, p_tabseq);
                    pstmt2.setInt(2, p_seq);
                    pstmt2.setInt(3, v_fileseq);
                    pstmt2.setString(4, v_realFileName[i]);
                    pstmt2.setString(5, v_newFileName[i]);
                    pstmt2.setString(6, s_userid);

                    isOk2 = pstmt2.executeUpdate();
                    v_fileseq++;
                    System.out.println("p_tabseq:::"+p_tabseq);
                    System.out.println("p_seq:::"+p_seq);
                }
            }
        }
        catch (Exception ex) {
            FileManager.deleteFile(v_newFileName, FILE_LIMIT);      //  일반파일, 첨부파일 있으면 삭제..
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
        }
        return isOk2;
    }

  /**
    * 회사별게시판 뷰화면에서 수정하여 저장할때
    * @param box      receive from the form object and session
    * @return isOk    1:update success,0:update fail
    * @throws Exception
    */
    public int viewupdate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        String sql   = "";
        String sql1  = "";
        String sql2  = "";
        String sql3  = "";
        ListSet ls   = null;
        ListSet ls1   = null;
        int isOk     = 0;
        int isOk1     = 0;

        String s_grcode     = box.getString("p_grcode");
        int v_seq           = box.getInt("p_seq");
        String v_type       = box.getString("p_type");
       // String v_categorycd = box.getString("p_categorycd");
        String v_okyn1      = box.getStringDefault("p_okyn1","N");
        String v_okyn2      = box.getStringDefault("p_okyn2","N");
        String v_approval1  = box.getString("p_approval1");
        String v_approval2  = box.getString("p_approval2");

//       String v_isopen  = "Y";
        String s_userid = box.getSession("userid");
        String s_gadmin   = box.getSession("gadmin");

        if (s_gadmin.equals("A1")){
            s_userid = "운영자";
        }else{
            s_userid = box.getSession("userid");
        }

        try {
            connMgr = new DBConnectionManager();
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
                sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);

                ls = connMgr.executeQuery(sql1);
                ls.next();
                int v_tabseq = ls.getInt(1);
                ls.close();
            //----------------분류를 지정하여 업데이트한다.---------------
                sql  = " update TZ_BOARD set ";
                if(v_okyn1.equals("Y") && v_approval1.equals("N")){
                  sql += " okyn1 = '"+v_okyn1+"' ,  \n";
                  sql += " okuserid1 = '"+s_userid+"',   \n";
                  sql += " okdate1 = to_char(sysdate, 'YYYYMMDDHH24MISS')  \n";
                }
                if(v_okyn2.equals("Y") && v_approval1.equals("Y") && v_approval2.equals("N") ){
                  sql += " okyn2 = '"+v_okyn2+"' ,  \n";
                  sql += " okuserid2 = '"+s_userid+"',    \n";
                  sql += " okdate2 = to_char(sysdate, 'YYYYMMDDHH24MISS') \n";
                }
                sql += "  where tabseq = ? and seq = ?   \n";
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setInt(1,  v_tabseq);
                pstmt.setInt(2,  v_seq);
                isOk = pstmt.executeUpdate();
       }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    /**
     * 선택된 자료파일 DB에서 삭제
     * @param connMgr           DB Connection Manager
     * @param box               receive from the form object and session
     * @param p_filesequence    선택 파일 갯수
     * @return
     * @throws Exception
     */
    public int deleteUpFile(DBConnectionManager connMgr, RequestBox box, Vector p_filesequence) throws Exception {
        PreparedStatement pstmt3 = null;
        String sql  = "";
        String sql3 = "";
        ListSet ls = null;
        int isOk3 = 1;
        String v_types   = box.getString("p_types");
        int v_seq = box.getInt("p_seq");
        String v_type = box.getString("p_type");
        try {

            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------

            sql3 = "delete from TZ_BOARDFILE where tabseq = "+ v_tabseq +" and seq =? and fileseq = ?";
            pstmt3 = connMgr.prepareStatement(sql3);
            for(int i = 0; i < p_filesequence.size(); i++) {
                int v_fileseq = Integer.parseInt((String)p_filesequence.elementAt(i));

                pstmt3.setInt(1, v_seq);
                pstmt3.setInt(2, v_fileseq);
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
