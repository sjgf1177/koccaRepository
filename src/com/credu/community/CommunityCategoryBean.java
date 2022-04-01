//**********************************************************
//1. 제      목: 커뮤니티 카테고리 관리
//2. 프로그램명: CommunityCategoryBean.java
//3. 개      요: 공지사항
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 2004. 02. 16
//7. 수      정:
//**********************************************************

package com.credu.community;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.community.*;
import com.credu.common.*;
import oracle.jdbc.driver.*;
import oracle.sql.*;
import com.credu.library.*;

public class CommunityCategoryBean {
//private static final String FILE_TYPE = "p_file";           //      파일업로드되는 tag name
//private static final int FILE_LIMIT = 5;                    //    페이지에 세팅된 파일첨부 갯수
private ConfigSet config;
private int row;

public CommunityCategoryBean() {
    try{
        config = new ConfigSet();
        row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
    }
    catch(Exception e) {
        e.printStackTrace();
    }
}

/**
커뮤니티 분류 조회
@param gubun        코드구분
@return ArrayList   커뮤니티 분류리스트
*/     
   public ArrayList selectCodeType_L(String gubun, RequestBox box) throws Exception {
       DBConnectionManager connMgr = null;
       ArrayList list = new ArrayList();
       ListSet   ls   = null;
       StringBuffer    sql  = new StringBuffer();
       DataBox dbox = null;
       
       String v_process	= box.getString("p_process");
       
       try {
           connMgr = new DBConnectionManager();
           sql.append(" SELECT                                             \n ");
           sql.append("         A.CODE PARENT_CODE, A.CODENM PARENT_NAME   \n ");
           sql.append("         , B.CODE, B.CODENM, B.LDATE, B.LEVELS      \n ");
           sql.append(" FROM    TZ_CODE A, TZ_CODE B                       \n ");
           sql.append(" WHERE                                              \n ");
           sql.append("         B.UPPER = A.CODE                           \n ");
           if(v_process.equals("selectList")){
        	   sql.append(" AND     B.LEVELS = 2                           \n ");   
           }
           sql.append(" AND     A.GUBUN = '0052'                           \n ");
           sql.append(" AND     B.GUBUN = '0052'                           \n ");
           sql.append(" ORDER BY A.CODE, B.CODE                            \n ");

           ls = connMgr.executeQuery(sql.toString());
           while (ls.next()) {
                  dbox = ls.getDataBox();
                  list.add(dbox);
           }
       }
       catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex);
           throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
       }
       finally {
           if (ls != null) { try { ls.close(); } catch (Exception e) {} }
			//2005.12.01_하경태 : conn 관련 수정
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
       }
       return list;
   }


/**
* 대분류만 가져오기. select
* @param box          receive from the form object and session
* @return ArrayList   조회한 상세정보
* @throws Exception
*/
public ArrayList selectLevels(String levels, String v_grtype) throws Exception {
    DBConnectionManager connMgr = null;
    ListSet ls = null;
    String sql = "";
    DataBox dbox = null;
    ArrayList list = new ArrayList();
	
    try {
        connMgr = new DBConnectionManager();


        sql  = " select gubun, levels, code, codenm, upper, parent, luserid, ldate ";
        sql += " from TZ_CODE ";
        sql += " where GUBUN = '0052' and levels = " + SQLString.Format(levels);
		//sql += "	and upper=" + SQLString.Format(v_grtype);
		
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
        if(ls != null) {try {ls.close();} catch(Exception e){}}
        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
    }
    return list;
}

/**
* 선택된 자료실 게시물 상세내용 select
* @param box          receive from the form object and session
* @return ArrayList   조회한 상세정보
* @throws Exception
*/
public DataBox selectBoard(RequestBox box) throws Exception {
    DBConnectionManager connMgr = null;
    ListSet ls = null;
    String sql = "";
    DataBox dbox = null;

    String v_levels = box.getString("p_levels");
	String v_code	= box.getString("p_code");

    try {
        connMgr = new DBConnectionManager();


        sql  = " Select gubun, levels, a.code, a.codenm, upper, parent, b.codenm as parentnm, luserid, name,  ldate ";
		sql += " From TZ_CODE a ";
		sql += " 	join (Select code, codenm From TZ_CODE Where GUBUN = '0052' and levels = 1) b ";
		sql += " 		on a.parent = b.code ";
		sql += " 	join (Select name, userid From TZ_MEMBER)  m on m.userid = a.luserid "	;
		sql += " Where GUBUN = '0052' and levels = " + v_levels;
		sql += " 	and a.code ='" + v_code + "' ";
		
        ls = connMgr.executeQuery(sql);

        for (int i = 0; ls.next(); i++) {
            dbox=ls.getDataBox();

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
* 새로운 자료실 내용 등록
* @param box      receive from the form object and session
* @return isOk    1:insert success,0:insert fail
* @throws Exception
*/
 public int insertBoard(RequestBox box) throws Exception {
    DBConnectionManager connMgr = null;
    ResultSet rs1 = null;
    Statement stmt1 = null;
    PreparedStatement pstmt1  = null;
    String sql = "";
    String sql1 = "";
    String sql2 = "";
    int isOk1 = 1;
	int v_tmp_code = 0;
    String v_code		= "";
	String s_userid		= box.getSession("userid");
	
    String v_codenm		= box.getString("p_codenm");
	String v_parent		= box.getString("p_parent");
	String v_grtype		= box.getString("p_grtype");

    try {
        connMgr = new DBConnectionManager();
        connMgr.setAutoCommit(false);
        stmt1 = connMgr.createStatement();

        //------------------------------------------------------------------------------------

        //----------------------   게시판 번호 가져온다 ----------------------------
        //sql = "select NVL(max(code), 0) as tmpnm from TZ_CODE where gubun = '0052' and levels = 2 and parent = '" + v_parent + "'";
        sql = "select nvl(max(substring(code, 3, 4)), 0 ) as tmpnm from TZ_CODE where gubun = '0052' and levels = 2 and parent = '" + v_parent + "'";

        rs1 = stmt1.executeQuery(sql);
        
        if (rs1.next()) {
			v_code = rs1.getString("tmpnm");
			
			v_tmp_code = Integer.parseInt(v_code) + 1; 
			
			if(v_tmp_code < 10) v_code = v_parent + "00" + String.valueOf(v_tmp_code);
			else if(v_tmp_code > 10 && v_tmp_code < 100) v_code = v_parent + "0" + String.valueOf(v_tmp_code);
			else v_code = v_parent + String.valueOf(v_tmp_code);
				
        }
        //////////////////////////////////   code table 에 입력  ///////////////////////////////////////////////////////////////////
        sql1 =  " insert into TZ_CODE(gubun, levels, code, codenm, parent, upper, luserid, ldate)";
		sql1 += " values ('0052', 2, ?, ?, ?, ?, ?, to_char(sysdate,  'YYYYMMDDHH24MISS'))";

        pstmt1 = connMgr.prepareStatement(sql1);

        pstmt1.setString(1, v_code);
        pstmt1.setString(2, v_codenm);
        pstmt1.setString(3, v_parent);
        pstmt1.setString(4, v_parent);
        pstmt1.setString(5, s_userid);

        isOk1 = pstmt1.executeUpdate();
        
		 if(isOk1 > 0 ) {
			 if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
		 }

    }
    catch (Exception ex) {
        if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }  
        ErrorManager.getErrorStackTrace(ex, box, sql1);
        throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
    }
    finally {
        if(rs1 != null) { try { rs1.close(); } catch (Exception e) {} }
        if(stmt1 != null) { try { stmt1.close(); } catch (Exception e1) {} }
        if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
        if(connMgr != null) { try { connMgr.freeConnection();}catch (Exception e10) {} }
    }
    return isOk1;
}

/**
* 선택된 자료 상세내용 수정
* @param box      receive from the form object and session
* @return isOk    1:update success,0:update fail
* @throws Exception
*/
 public int updateBoard(RequestBox box) throws Exception {
    DBConnectionManager connMgr = null;
    ResultSet rs1 = null;
    PreparedStatement pstmt1  = null;
    String sql1 = "";
    int isOk1 = 0;

    String v_code     = box.getString("p_code");
    String v_codenm   = box.getString("p_codenm");

    try {
        connMgr = new DBConnectionManager();

		sql1 = "update TZ_CODE set codenm = ? Where gubun='0052' and levels=2 and code = ? ";

        pstmt1 = connMgr.prepareStatement(sql1);
		pstmt1.setString(1, v_codenm);
        pstmt1.setString(2, v_code);
        isOk1 = pstmt1.executeUpdate();
		
    }catch(Exception ex) {
        ErrorManager.getErrorStackTrace(ex, box, sql1);
        throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
    }
    finally {
        if(rs1 != null) { try { rs1.close(); } catch (Exception e) {} }
        if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
    }
    return isOk1;
}


/**
* 선택된 게시물 삭제
* @param box      receive from the form object and session
* @return isOk    1:delete success,0:delete fail
* @throws Exception
*/
public int deleteBoard(RequestBox box) throws Exception {
    DBConnectionManager connMgr = null;
    PreparedStatement pstmt1 = null;
    String sql1 = "";
    String sql2 = "";
    int isOk1 = 0;
    int isOk2 = 0;

    String v_levels = box.getString("p_levels");
    String v_code = box.getString("p_code");

    try {
        connMgr = new DBConnectionManager();

        isOk1 = 1;
        isOk2 = 1;
        sql1 = "delete from tz_code where gubun='0052' and levels = ? and  code = ? ";
        pstmt1 = connMgr.prepareStatement(sql1);
        pstmt1.setString(1, v_levels);
        pstmt1.setString(2, v_code);
        isOk1 = pstmt1.executeUpdate();

    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, box,    sql1);
        throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
    }
    finally {
        if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
    }
    return isOk1;
}

/**
* 삭제시 하위 답변 유무 체크
* @param seq          게시판 번호
* @return result      0 : 답변 없음,    1 : 답변 있음
* @throws Exception
*/
public int selectBoard(int commid, int seq) throws Exception {
    DBConnectionManager connMgr = null;
    ListSet ls = null;
    String sql = "";
    int result = 0;

    try {
        connMgr = new DBConnectionManager();

        sql  = "  select count(*) cnt                         ";
        sql += "  from                                        ";
        sql += "    (select commid, refseq, levels, position  ";
        sql += "       from TZ_COMMUNITY_NOTICE                          ";
        sql += "      where commid = " + commid;
        sql += "        and seq = " + seq;
        sql += "     ) a, TZ_COMMUNITY_NOTICE b                          ";
        sql += " where a.commid = b.commid                    ";
        sql += "   and a.refseq = b.refseq                    ";
        sql += "   and b.levels = (a.levels+1)                ";
        sql += "   and b.position = (a.position+1)            ";


        ls = connMgr.executeQuery(sql);

        if (ls.next()) {
            result = ls.getInt("cnt");
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
    return result;
}

public static String convertBody(String contents) throws Exception {
    String result = "";

    result = StringManager.replace(contents, "<HTML>","");
    result = StringManager.replace(result, "<HEAD>","");
    result = StringManager.replace(result, "<META NAME=\"GENERATOR\" Content=\"Microsoft DHTML Editing Control\">","");
    result = StringManager.replace(result, "<TITLE>","");
    result = StringManager.replace(result, "</TITLE>","");
    result = StringManager.replace(result, "</HEAD>","");
    result = StringManager.replace(result, "<BODY>","");
    result = StringManager.replace(result, "</BODY>","");
    result = StringManager.replace(result, "</HTML>","");

    return result;
}
}
