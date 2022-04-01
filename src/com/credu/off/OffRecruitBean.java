//**********************************************************
//  1. 제	  목:  관리
//  2. 프로그램명 : Bean.java
//  3. 개	  요:  관리
//  4. 환	  경: JDK 1.5
//  5. 버	  젼: 1.0
//  6. 작	  성: __누구__ 2009. 10. 19
//  7. 수	  정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.off;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.List;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.namo.active.NamoMime;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@SuppressWarnings("unchecked")
public class OffRecruitBean {

    public OffRecruitBean() {
    }

    /**
     * 과정코드 체크
     * 
     * @param box receive from the form object and session
     * @return String 1:insert success,0:insert fail
     */
    private boolean checkSubjcode(DBConnectionManager connMgr, String v_subj) throws Exception {
        boolean result = true;
        int cnt = 0;
        ListSet ls = null;
        String sql = "";
        try {
            sql = " select count(*) cnt ";
            sql += "   from TZ_OFFSUBJ	 \n";
            sql += " where subj = " + StringManager.makeSQL(v_subj);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                cnt = ls.getInt("cnt");
            }

            if (cnt == 0) {
                result = false;
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    /**
     * 삭제할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int delete(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_subj = box.getString("p_subj");

        try {
            if (v_subj.length() > 0) {
                connMgr = new DBConnectionManager();
                sql = " delete tz_offsubjtutor A where SUBJ=? ";
                sql += "AND	0	= (SELECT COUNT(subjseq) FROM TZ_OFFSUBJSEQ WHERE SUBJ=A.SUBJ)\n";
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, v_subj);
                pstmt.executeUpdate();

                sql = " delete from TZ_OFFSUBJ A\n";
                sql += "   where subj = ?\n";
                sql += "AND	0	= (SELECT COUNT(subjseq) FROM TZ_OFFSUBJSEQ WHERE SUBJ=A.SUBJ)\n";

                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, v_subj);
                isOk = pstmt.executeUpdate();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql + "\r\n");
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 새로운 과정코드 등록 - 오프라인
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int InsertSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        // PreparedStatement pstmt2 = null;

        StringBuffer sql = new StringBuffer();
        // String sql2 = "";
        // String sql3 = "";

        int isOk = 0;
        // int isOk2 = 0;

        String v_subj = "";
        String v_explain = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // v_subj = getMaxSubjcode(connMgr, box.getString("p_upperclass"), box.getString("p_middleclass"));
            v_subj = box.getString("p_subj");
            v_subj = getSubjcode(connMgr, box.getString("p_area"));
            box.put("p_subj", v_subj);
            v_explain = box.getString("p_explain");

            box.sync("p_isvisible");

            /*********************************************************************************************/
            // 나모에디터 본문 처리
            //ConfigSet conf = new ConfigSet();
            //SmeNamoMime namo = new SmeNamoMime(box.getString("p_explain")); // 객체생성
            //boolean result = namo.parse(); // 실제 파싱 수행
            //if (!result) { // 파싱 실패시
            //	System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
            //	return 0;
            //}
            //if (namo.isMultipart()) { // 문서가 멀티파트인지 판단
            //	String v_server = conf.getProperty("autoever.url.value");
            //	String fPath = conf.getProperty("dir.namo"); // 파일 저장 경로 지정
            //	String refUrl = conf.getProperty("url.namo");
            //	; // 웹에서 저장된 파일을 접근하기 위한 경로
            //	String prefix = "offSubj" + v_subj; // 파일명 접두어
            //	result = namo.saveFile(fPath, v_server + refUrl, prefix); // 실제 파일 저장
            //}
            //if (!result) { // 파일저장 실패시
            //	System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
            //	return 0;
            //}
            // box.put("p_explain", namo.getContent()); // 최종 컨텐트 얻기
            /*********************************************************************************************/

            /*********************************************************************************************/
            // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다.
            try {
                v_explain = (String) NamoMime.setNamoContent(v_explain);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/

            //			System.out.println(box.getString("p_explain").getBytes().length);
            // 중복 체크
            if (!checkSubjcode(connMgr, v_subj)) {
                sql.append("INSERT INTO TZ_OFFSUBJ (\n");
                sql.append("	SUBJ, SUBJNM, UPPERCLASS, MIDDLECLASS, LOWERCLASS, SPECIALS, ISUSE, BIYONG, STUDENTLIMIT,STUDENTWAIT,\n");
                sql.append("	GRADSCORE, GRADSTEP, GRADREPORT, GRADEXAM, GRADFTEST, GRADHTEST, GRADETC1, GRADETC2,\n");
                sql.append("	WSTEP, WMTEST, WHTEST, WFTEST, WREPORT, WETC1, WETC2,\n");
                sql.append("	PLACE, SUBJTARGET, INTRO, EXPLAIN, ISVISIBLE, ISINTRODUCTION,\n");
                //				sql.append("	PLACE, INTRO, EXPLAIN, ISVISIBLE, ISINTRODUCTION,\n");
                sql.append("	GOYONGPRICEMAJOR, ISGOYONG, GOYONGPRICEMINOR, EDUDAYSTYPE, SURYOTITLE,\n");
                sql.append("	SUBJCLASS, ISTERM, EDUDAYS, NEEDINPUT, LDATE, LUSERID, submainfilenamereal, submainfilenamenew, AREA, SUBJYEAR,hitnumber, SEARCH_NM )\n");
                sql.append("VALUES (\n");
                sql.append("	 ':p_subj',':p_subjnm',':p_upperclass',':p_middleclass',':p_lowerclass',':p_specials',':p_isuse',':p_biyong',':p_studentlimit',':p_studentwait'\n");
                sql.append("	,':p_gradscore',':p_gradstep',':p_gradreport',':p_gradexam',':p_gradftest',':p_gradhtest',':p_gradetc1',':p_gradetc2'\n");
                sql.append("	,':p_wstep',':p_wmtest',':p_whtest',':p_wftest',':p_wreport',':p_wetc1',':p_wetc2'\n");
                sql.append("	,':p_place',':p_edumans',':p_intro',?,NVL(':p_isvisible', 'N'),':p_isintroduction'\n");
                //				sql.append("	,':p_place',':p_intro',?,NVL(':p_isvisible', 'N'),':p_isintroduction'\n");
                sql.append("	,':p_goyongpricemajor',':p_isgoyong',':p_goyongpriceminor',':p_edudaystype',':p_suryotitle'\n");
                sql.append("	,':p_upperclass' || ':p_middleclass' || ':p_lowerclass',':p_isterm',':p_edudays',':p_needinput',TO_CHAR(SYSDATE, 'YYYYMMDDHHMISS'),?, ?, ?, ':p_area', TO_CHAR(SYSDATE, 'YYYY'),':p_hitnumber', ':p_search_nm' \n");
                sql.append(")\n");

                pstmt = connMgr.prepareStatement(connMgr.replaceParam(sql.toString(), box, true));
                int i = 1;
                pstmt.setCharacterStream(i++, new StringReader(v_explain), v_explain.length());
                pstmt.setString(i++, box.getSession("userid"));
                pstmt.setString(i++, box.getRealFileName("p_submainfile")); //서브메인 화면용 이미지
                pstmt.setString(i++, box.getNewFileName("p_submainfile")); //서브메인 화면용 이미지

                isOk = pstmt.executeUpdate();

                Object muser = box.getObject("muser");
                if (muser != null) { // 교수등록
                    saveMuser(connMgr, v_subj, muser, box.getSession("userid"));
                }

                if (isOk == 1) {
                    connMgr.commit();
                } else {
                    connMgr.rollback();
                }
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, connMgr.replaceParam(sql.toString(), box, true));
            throw new Exception("sql = " + connMgr.replaceParam(sql.toString(), box, true) + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e) {
                }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 설명 :
     * 
     * @param box
     * @return
     * @throws Exception
     * @author swchoi
     */
    public List<DataBox> listPage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        List<DataBox> result = null;
        String t = null;
        box.sync("s_subjyear");

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT\n");
            sql.append("	A.SUBJ, SUBJNM, UPPERCLASS,\n");
            sql.append("	MIDDLECLASS, LOWERCLASS, SPECIALS,\n");
            sql.append("	ISUSE, STUDENTLIMIT, STUDENTWAIT,SUBJCLASS, C.NAME, (SELECT CODENM FROM TZ_CODE WHERE GUBUN = '0101' AND CODE = A.AREA) AREA, A.SUBJYEAR, A.SEARCH_NM \n");
            sql.append("FROM TZ_OFFSUBJ A, TZ_OFFSUBJTUTOR B, TZ_MEMBER C\n");
            sql.append("WHERE 1=1	\n");
            sql.append("	AND 1 = DECODE(NVL(:s_upperclass, UPPERCLASS), UPPERCLASS, 1, 'ALL', 1, 0)	\n");
            sql.append("	AND 1 = DECODE(NVL(:s_middleclass, MIDDLECLASS), MIDDLECLASS, 1, 'ALL', 1, 0)	\n");
            sql.append("	AND 1 = DECODE(NVL(:s_lowerclass, LOWERCLASS), LOWERCLASS, 1, 'ALL', 1, 0)	\n");
            sql.append("	AND 1 = DECODE(NVL(:s_subjyear, A.SUBJYEAR), A.SUBJYEAR, 1, 'ALL', 1, 0)	\n");
            sql.append("	AND SUBJNM LIKE '%' || nvl(:s_subjsearchkey, SUBJNM) || '%'\n");
            sql.append("	AND A.SUBJ = B.SUBJ(+)\n");
            sql.append("	AND B.SEQ(+) = 1\n");
            sql.append("	AND B.USERID = C.USERID(+)\n");
            sql.append("ORDER BY A.SUBJ DESC");

            t = connMgr.replaceParam(sql.toString(), box);
            ls = connMgr.executeQuery(t);

            result = ls.getAllDataList();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, t);
            throw new Exception("sql = " + t + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return result;
    }

    private boolean saveMuser(DBConnectionManager connMgr, String v_subj, Object muser, String v_luserid) throws Exception {
        boolean result = false;
        String sql = " delete tz_offsubjtutor where SUBJ=? ";
        PreparedStatement pstmt = connMgr.prepareStatement(sql);
        pstmt.setString(1, v_subj);
        pstmt.executeUpdate();

        sql = " insert into tz_offsubjtutor(SUBJ,USERID,SEQ,LUSERID,LDATE) values (?,?,(select nvl(max(SEQ),0)+1 from tz_offsubjtutor where SUBJ=?),?,to_char(sysdate, 'YYYYMMDDHHMISS'))";
        pstmt = connMgr.prepareStatement(sql);
        if (muser.getClass().isArray()) {
            Object[] temp = (Object[]) muser;
            for (Object muserid : temp) {
                pstmt.setString(1, v_subj);
                pstmt.setString(2, (String) muserid);
                pstmt.setString(3, v_subj);
                pstmt.setString(4, v_luserid);
                pstmt.executeUpdate();
            }
        } else {
            pstmt.setString(1, v_subj);
            pstmt.setString(2, (String) muser);
            pstmt.setString(3, v_subj);
            pstmt.setString(4, v_luserid);
            pstmt.executeUpdate();
        }
        result = true;

        return result;
    }

    /**
     * 과정 코드 만들기
     * 
     * @param connMgr
     * @param v_area
     * @return
     * @throws Exception
     */
    private String getSubjcode(DBConnectionManager connMgr, String v_area) throws Exception {
        String v_subjcode = "";

        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        try {

            sql.append(" SELECT \n");
            sql.append(" 'S' || SUBSTR('" + v_area + "',1,1) || TO_CHAR(SYSDATE, 'YY') || LPAD(TO_NUMBER(NVL(MAX(SUBSTR(SUBJ, -3)),0)) + 1, 3, 0) AS MAXVAL \n");
            sql.append("   FROM TZ_OFFSUBJ \n");
            sql.append("  WHERE SUBJ LIKE 'S' || SUBSTR('" + v_area + "',1,1) || TO_CHAR(SYSDATE, 'YY') || '%' ");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                v_subjcode = ls.getString("maxval");
            }

            sql.setLength(0);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }

        return v_subjcode;
    }

    /**
     * 설명 : 상세 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     * @author swchoi
     */
    public DataBox selectPage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox result = null;

        if (!box.get("p_subjcode1").toString().equals(""))
            box.put("p_subj", box.get("p_subjcode1"));

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT\n");
            sql.append("	SUBJ, SUBJNM, UPPERCLASS, MIDDLECLASS, LOWERCLASS, SPECIALS, ISUSE, BIYONG, STUDENTLIMIT,NVL(STUDENTWAIT,0) as STUDENTWAIT,\n");
            sql.append("	GRADSCORE, GRADSTEP, GRADREPORT, GRADEXAM, GRADFTEST, GRADHTEST, GRADETC1, GRADETC2,\n");
            sql.append("	WSTEP, WMTEST, WHTEST, WFTEST, WREPORT, WETC1, WETC2,\n");
            sql.append("	PLACE, SUBJTARGET, INTRO, EXPLAIN, ISVISIBLE, ISINTRODUCTION,\n");
            sql.append("	GOYONGPRICEMAJOR, ISGOYONG, GOYONGPRICEMINOR, EDUDAYSTYPE, SURYOTITLE,\n");
            sql.append("	SUBJCLASS, ISTERM, EDUDAYS, NEEDINPUT, SUBMAINFILENAMEREAL, SUBMAINFILENAMENEW, AREA,hitnumber, search_nm \n");
            sql.append("FROM TZ_OFFSUBJ A\n");
            sql.append("WHERE A.SUBJ = :p_subj	\n");

            ls = connMgr.executeQuery(sql.toString(), box);

            if (ls.next())
                result = ls.getDataBox();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return result;
    }

    /**
     * 과정정보 수정 - 오프라인
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int update(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;

        StringBuffer sql = new StringBuffer();

        int isOk = 0;

        String v_subj = "";
        String v_explain = "";

        box.sync("p_isvisible");
        String v_submainfilenamereal = box.getRealFileName("p_submainfile");
        String v_submainfilenamenew = box.getNewFileName("p_submainfile");

        String v_submainfile0 = box.getStringDefault("p_submainfile0", "0");

        if (v_submainfilenamereal.length() == 0) {
            if (v_submainfile0.equals("1")) {
                v_submainfilenamereal = "";
                v_submainfilenamenew = "";
            } else {
                v_submainfilenamereal = box.getString("p_submainfile1");
                v_submainfilenamenew = box.getString("p_submainfile2");
            }
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //v_subj = getMaxSubjcode(connMgr, box.getString("p_upperclass"), box.getString("p_middleclass"));
            v_subj = box.getString("p_subj");
            v_explain = box.getString("p_explain");

            /*********************************************************************************************/
            // 나모에디터 본문 처리
            //ConfigSet conf = new ConfigSet();
            //SmeNamoMime namo = new SmeNamoMime(box.getString("p_explain")); // 객체생성
            //boolean result = namo.parse(); // 실제 파싱 수행
            //if (!result) { // 파싱 실패시
            //	System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
            //	return 0;
            //}
            //if (namo.isMultipart()) { // 문서가 멀티파트인지 판단
            //	String v_server = conf.getProperty("autoever.url.value");
            //	String fPath = conf.getProperty("dir.namo"); // 파일 저장 경로 지정
            //	String refUrl = conf.getProperty("url.namo");
            //	; // 웹에서 저장된 파일을 접근하기 위한 경로
            //	String prefix = "offSubj" + v_subj; // 파일명 접두어
            //	result = namo.saveFile(fPath, v_server + refUrl, prefix); // 실제 파일 저장
            //}
            //if (!result) { // 파일저장 실패시
            //	System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
            //	return 0;
            //}
            //			box.put("p_explain", namo.getContent()); // 최종 컨텐트 얻기
            /*********************************************************************************************/
            /*********************************************************************************************/
            // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다.
            try {
                v_explain = (String) NamoMime.setNamoContent(v_explain);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/

            //			System.out.println(box.getString("p_explain").getBytes().length);
            // 중복 체크
            if (checkSubjcode(connMgr, v_subj)) {
                sql.append("UPDATE TZ_OFFSUBJ SET\n");
                sql.append("	SUBJNM			=	':p_subjnm',\n");
                sql.append("	UPPERCLASS		=	':p_upperclass',\n");
                sql.append("	MIDDLECLASS		=	':p_middleclass',\n");
                sql.append("	LOWERCLASS		=	':p_lowerclass',\n");
                sql.append("	SPECIALS		=	':p_specials',\n");
                sql.append("	ISUSE			=	':p_isuse',\n");
                sql.append("	BIYONG			=	':p_biyong',\n");
                sql.append("	STUDENTLIMIT	=	':p_studentlimit',\n");
                sql.append("	STUDENTWAIT	=	':p_studentwait',\n");
                sql.append("	GRADSCORE		=	':p_gradscore',\n");
                sql.append("	GRADSTEP		=	':p_gradstep',\n");
                sql.append("	GRADREPORT		=	':p_gradreport',\n");
                sql.append("	GRADEXAM		=	':p_gradexam',\n");
                sql.append("	GRADFTEST		=	':p_gradftest',\n");
                sql.append("	GRADHTEST		=	':p_gradhtest',\n");
                sql.append("	GRADETC1		=	':p_gradetc1',\n");
                sql.append("	GRADETC2		=	':p_gradetc2',\n");
                sql.append("	WSTEP			=	':p_wstep',\n");
                sql.append("	WMTEST			=	':p_wmtest',\n");
                sql.append("	WHTEST			=	':p_whtest',\n");
                sql.append("	WFTEST			=	':p_wftest',\n");
                sql.append("	WREPORT			=	':p_wreport',\n");
                sql.append("	WETC1			=	':p_wetc1',\n");
                sql.append("	WETC2			=	':p_wetc2',\n");
                sql.append("	PLACE			=	':p_place',\n");
                sql.append("	SUBJTARGET		=	':p_subjtarget',\n");
                sql.append("	INTRO			=	':p_intro',\n");
                sql.append("	EXPLAIN			=	?,\n");
                sql.append("	ISVISIBLE		=	NVL(':p_isvisible', 'N'),\n");
                sql.append("	ISINTRODUCTION	=	':p_isintroduction',\n");
                sql.append("	GOYONGPRICEMAJOR=	':p_goyongpricemajor',\n");
                sql.append("	ISGOYONG		=	':p_isgoyong',\n");
                sql.append("	GOYONGPRICEMINOR=	':p_goyongpriceminor',\n");
                sql.append("	EDUDAYSTYPE		=	':p_edudaystype',\n");
                sql.append("	SURYOTITLE		=	':p_suryotitle',\n");
                sql.append("	SUBJCLASS		=	':p_upperclass' || ':p_middleclass' || ':p_lowerclass',\n");
                sql.append("	ISTERM			=	':p_isterm',\n");
                sql.append("	EDUDAYS			=	':p_edudays',\n");
                sql.append("	NEEDINPUT		=	':p_needinput',\n");
                sql.append("	AREA		=	':p_area',\n");
                sql.append("	hitnumber     =	':p_hitnumber',\n");
                sql.append("	submainfilenamereal = ? ,\n ");
                sql.append("	submainfilenamenew = ? ,\n ");
                sql.append("	LDATE			=	TO_CHAR(SYSDATE, 'YYYYMMDDHHMISS'),\n");
                sql.append("	LUSERID 		=	?, \n");
                sql.append("	SEARCH_NM 		=':p_search_nm' \n");
                sql.append("WHERE\n");
                sql.append("	SUBJ			=	':p_subj'\n");

                pstmt = connMgr.prepareStatement(connMgr.replaceParam(sql.toString(), box, true));
                int i = 1;
                pstmt.setCharacterStream(i++, new StringReader(v_explain), v_explain.length());
                pstmt.setString(i++, v_submainfilenamereal); //서브메인 화면용 이미지
                pstmt.setString(i++, v_submainfilenamenew); //서브메인 화면용 이미지
                pstmt.setString(i++, box.getSession("userid"));

                isOk = pstmt.executeUpdate();

                Object muser = box.getObject("muser");
                if (muser != null) { // 교수등록
                    saveMuser(connMgr, v_subj, muser, box.getSession("userid"));
                }

                if (isOk == 1) {
                    connMgr.commit();
                } else {
                    connMgr.rollback();
                }
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, connMgr.replaceParam(sql.toString(), box, true));
            throw new Exception("sql = " + connMgr.replaceParam(sql.toString(), box, true) + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e) {
                }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

}
