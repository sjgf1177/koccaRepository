//**********************************************************
//  1. 제      목: 외주관리시스템의 공지사항빈
//  2. 프로그램명: GatePageNoticeAdminBean.java
//  3. 개      요: 외주관리시스템 공지사항빈 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 08.  05
//  7. 수      정: 이나연 05.11.16 _ 오라클 -> MSSQL (OuterJoin) 수정
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
    private static final String FILE_TYPE = "p_file";           //      파일업로드되는 tag name
    private static final int FILE_LIMIT = 5;                    //    페이지에 세팅된 파일첨부 갯수

    public GatePageNoticeAdminBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

  /**
    * gatepage공지사항관리 화면 리스트
    * @param box          receive from the form object and session
    * @return ArrayList   공지사항 리스트
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
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			////------------------------------------------------------------------------------------

            list = new ArrayList();

            sql = "select a.seq, a.adname, a.adtitle, count(b.realfile) filecnt, a.addate, a.cnt";
            sql += " from tz_Notice a, tz_boardfile b";
			// 수정일 : 05.11.16 수정자 : 이나연 _(+)  수정
//          sql += " where a.seq = b.seq(+) and a.tabseq = b.tabseq(+) and  a.tabseq =" + v_tabseq;
			sql += " where a.seq  =  b.seq(+) and a.tabseq  =  b.tabseq(+) and  a.tabseq =" + v_tabseq;

            if ( !v_searchtext.equals("")) {      //    검색어가 있으면
                v_pageno = 1;   //      검색할 경우 첫번째 페이지가 로딩된다

               if (v_select.equals("title")) {     //    제목으로 검색할때
                    sql += " and a.adtitle like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
                else if (v_select.equals("content")) {     //    내용으로 검색할때
                    sql += " and a.content like " + StringManager.makeSQL("%" + v_searchtext + "%");            //   Oracle 9i 용
                }
            }
            sql += " group by a.seq, a.adname, a.adtitle, a.addate, a.cnt";
            sql += " order by a.seq desc";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ls = new ListSet(pstmt);        //      ListSet (ResultSet) 객체생성

            ls.setPageSize(row);             //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno);                    //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount();   	//     전체 row 수를 반환한다

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
    * gatepage공지사항관리 상세보기
    * @param box          receive from the form object and session
    * @return DataBox	  조회한 값을 DataBox에 담아 리턴
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
			//----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
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
    * gatepage공지사항관리 등록하기
    * @param box          receive from the form object and session
    * @return isOk1*isOk2	  조회한 값을 DataBox에 담아 리턴
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
			s_usernm = "운영자";
		}else{
			s_usernm = box.getSession("name");
		}

		if (s_gadmin.equals("A1")){
			s_userid = "운영자";
		}else{
			s_userid = box.getSession("userid");
		}

		//v_type = box.getString("p_gubun");
		String v_gubun = "Y";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format( v_type) ;
				ls = connMgr.executeQuery(sql);
				ls.next();
				int v_tabseq = ls.getInt(1);
				ls.close();
			//------------------------------------------------------------------------------------
            //----------------------   게시판 번호 가져온다 ----------------------------
            sql = "select NVL(max(seq), 0) from tz_notice";
	            ls = connMgr.executeQuery(sql);
	            ls.next();
	            int v_seq = ls.getInt(1) + 1;
	            ls.close();
            //------------------------------------------------------------------------------------
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
					String prefix =  "cpNotice" + v_seq;         // 파일명 접두어
					result = namo.saveFile(fPath, v_server+refUrl, prefix); // 실제 파일 저장
				}
				if ( !result ) { // 파일저장 실패시
					System.out.println( namo.getDebugMsg() ); // 디버깅 메시지 출력
					return 0;
				}
				v_content = namo.getContent(); // 최종 컨텐트 얻기
				/*********************************************************************************************/

            //////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
            sql1 =  "insert into tz_notice (tabseq,seq,gubun,addate,adtitle,adname,adcontent,luserid,ldate)";

            sql1 += " values (?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),? , ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";
            //<---  empty_clob() 적용(Oracle 인 경우)

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt   (1, v_tabseq);
            pstmt1.setInt   (2, v_seq);
			pstmt1.setString(3, v_gubun);
			pstmt1.setString(4, v_title);
            pstmt1.setString(5, s_usernm);
			pstmt1.setCharacterStream(6, new StringReader(v_content), v_content.length());
            pstmt1.setString(7, s_userid);

			isOk1 = pstmt1.executeUpdate();     //      먼저 해당 content 에 empty_clob()을 적용하고 나서 값을 스트림으로 치환한다.
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
    * gatepage공지사항관리 수정하기
    * @param box          receive from the form object and session
    * @return isOk1*isOk2*isOk3	  수정에 성공하면 1을 리턴한다
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
        int v_upfilecnt = box.getInt("p_upfilecnt");    //  서버에 저장되있는 파일수
        String v_title = box.getString("p_title");
        String v_content =  StringManager.replace(StringUtil.removeTag(box.getString("p_content")),"&","&amp;");
        Vector v_savefile     = box.getVector("p_savefile"); //선택삭제파일
        Vector v_filesequence = box.getVector("p_fileseq");  //선택삭제파일 sequence
        Vector v_realfile     = box.getVector("p_file");     //새로 등록 파일
        for(int i = 0; i < v_upfilecnt; i++) {
            if( !box.getString("p_fileseq" + i).equals("")) {
                v_savefile.addElement(box.getString("p_savefile" + i));         //      서버에 저장되있는 파일명 중에서 삭제할 파일들
                v_filesequence.addElement(box.getString("p_fileseq" + i));       //     서버에  저장되있는 파일번호 중에서 삭제할 파일들
            }
        }

       // String v_realMotionName = box.getRealFileName("p_motion");
      //  String v_newMotionName = box.getNewFileName("p_motion");

        String s_userid = "";
        String s_usernm = "";
		String s_gadmin = box.getSession("gadmin");

		if (s_gadmin.equals("A1")){
			s_usernm = "운영자";
		}else{
			s_usernm = box.getSession("name");
		}
		s_userid = box.getSession("userid");

        /*********************************************************************************************/
        // 나모에디터 본문 처리  (Mime Document Parsing 및 이미지 업로드)
        ConfigSet conf = new ConfigSet();
        SmeNamoMime namo = new SmeNamoMime(v_content); // 객체생성
        boolean result = namo.parse(); // 실제 파싱 수행
        System.out.println(" gatePageNoticeAdmin result = "+result);

        if ( !result ) { // 파싱 실패시
            System.out.println( namo.getDebugMsg() ); // 디버깅 메시지 출력
            return 0;
        }
        if ( namo.isMultipart() ) { // 문서가 멀티파트인지 판단
            String v_server = conf.getProperty("autoever.url.value");
            String fPath  = conf.getProperty("dir.namo");   // 파일 저장 경로 지정
            String refUrl = conf.getProperty("url.namo");; // 웹에서 저장된 파일을 접근하기 위한 경로
            String prefix = "cpNotice" + v_seq;         // 파일명 접두어
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
//            connMgr.setOracleCLOB(sql2, v_content);       //      clob 필드에 스트림을 이용,  data 를 넣는다(Weblogic 인 경우)
			/* 05.11.15 이나연
            //WebLogic 6.1인경우
            */

            isOk2 = this.insertUpFile(connMgr, v_tabseq, v_seq, box);       //      파일첨부했다면 파일table에  insert
            isOk3 = this.deleteUpFile(connMgr, box);        //     삭제할 파일이 있다면 파일table에서 삭제

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
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2*isOk3;
    }

    /**
    * gatepage공지사항관리 삭제하기
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
        Vector savefile  = box.getVector("p_savefile");
        String v_savemotion = box.getString("p_savemotion");
        //v_type = box.getString("p_gubun");
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
                    FileManager.deleteFile(savefile);         //	 첨부파일 삭제
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


///////////////////////////////////////////////////////  파일 테이블   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     /**
    * gatepage공지사항관리 파일 입력하기
    * @param box          receive from the form object and session
    * @return isOk2 입력에 성공하면 1을 리턴한다

     public int insertUpFile(DBConnectionManager connMgr, int p_seq, RequestBox box) throws Exception {
        ListSet ls = null;

        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        int isOk2 = 1;

        //----------------------   업로드되는 파일 --------------------------------
        Vector realFileNames = box.getRealFileNames("p_file");
        Vector newFileNames = box.getNewFileNames("p_file");
        //----------------------------------------------------------------------------------------------------------------------------

        String s_userid = box.getSession("userid");

         try {
            if(realFileNames != null) {          //
				//----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
				sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
				ls = connMgr.executeQuery(sql);
				ls.next();
				int v_tabseq = ls.getInt(1);
				ls.close();
			//------------------------------------------------------------------------------------
                 //----------------------   자료 번호 가져온다 ----------------------------
                sql = "select NVL(max(fileseq), 0) from tz_boardfile where tabseq = " + v_tabseq;
                ls = connMgr.executeQuery(sql);
                ls.next();
                int v_fileseq = ls.getInt(1) + 1;
                ls.close();
                //------------------------------------------------------------------------------------

                //////////////////////////////////   파일 table 에 입력  ///////////////////////////////////////////////////////////////////
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
            FileManager.deleteFile(newFileNames);		//  일반파일, 첨부파일 있으면 삭제..
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
    * gatepage공지사항관리 파일 삭제하기
    * @param box          receive from the form object and session
    * @return isOk3 삭제에 성공하면 1을 리턴한다
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
			//----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
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
