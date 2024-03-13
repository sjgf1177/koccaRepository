//**********************************************************
//1. 제      목: 학습창 START BEAN
//2. 프로그램명: EduStartBean.java
//3. 개      요: 학습창 START BEAN
//4. 환      경: JDK 1.4
//5. 버      젼: 0.1
//6. 작      성: S.W.Kang 2004. 12. 5
//7. 수      정:
//**********************************************************
package com.credu.contents;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.credu.common.GetCodenm;
import com.credu.library.CalcUtil;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.EduEtc1Bean;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import utils.system;

public class EduStartBean {

    public EduStartBean() {
    }

    /**
     * 마스터폼정보 조회
     *
     * @param box receive from the form object and session
     * @return MasterFormData 마스터폼 정보
     */
    public MasterFormData SelectMasterFormData(RequestBox box) throws Exception {
        MasterFormBean bean = new MasterFormBean();
        return bean.SelectMasterFormData(box);
    }

    /**
     * 마스터폼 Lesson리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList Lesson리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectMfLessonList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList list1 = null;

        StringBuffer sql = new StringBuffer();

        MfLessonData data = null;

        String s_subj = box.getSession("s_subj");
        String s_year = box.getSession("s_year");
        String s_subjseq = box.getSession("s_subjseq");
        String s_userid = box.getSession("userid");

        try {

            connMgr = new DBConnectionManager();

            list1 = new ArrayList();

            sql.append(" /* EduStartBean.SelectMasterFormData (마스터 폼 차시목록조회) */ \n");

            sql.append(" SELECT                                                                 \n");
            sql.append("         A.MODULE                                                       \n");
            sql.append("     ,   A.LESSON                                                       \n");
            sql.append("     ,   A.SDESC                                                        \n");
            sql.append("     ,   A.TYPES                                                        \n");
            sql.append("     ,   A.OWNER                                                        \n");
            sql.append("     ,   A.STARTING                                                     \n");
            sql.append("     ,   CASE COUNT(B.USERID) WHEN 0 THEN 'N' ELSE 'Y' END AS F_EXIST1  \n");
            sql.append("     ,   CASE COUNT(C.LESSON) WHEN 0 THEN 'N' ELSE 'Y' END AS F_EXIST2  \n");
            sql.append("   FROM  TZ_SUBJLESSON A                                                \n");
            sql.append("     ,   TZ_PROGRESS B                                                  \n");
            sql.append("     ,   TZ_EXAMPAPER C                                                 \n");
            sql.append("  WHERE  A.SUBJ = B.SUBJ(+)                                             \n");
            sql.append("    AND  A.LESSON = B.LESSON(+)                                         \n");
            sql.append("    AND  A.SUBJ = C.SUBJ(+)                                             \n");
            sql.append("    AND  A.LESSON = C.LESSON(+)                                         \n");
            sql.append("    AND  A.SUBJ = '").append(s_subj).append("'                          \n");
            sql.append("    AND  B.YEAR(+) = '").append(s_year).append("'                       \n");
            sql.append("    AND  B.SUBJSEQ(+) = '").append(s_subjseq).append("'                 \n");
            sql.append("    AND  B.USERID(+) = '").append(s_userid).append("'                   \n");
            sql.append("    AND  C.YEAR(+) = '").append(s_year).append("'                       \n");
            sql.append("    AND  C.SUBJSEQ(+) = '").append(s_subjseq).append("'                 \n");
            sql.append("  GROUP  BY A.MODULE, A.LESSON, A.SDESC, A.TYPES, A.OWNER, A.STARTING   \n");
            sql.append("  ORDER  BY A.MODULE, A.LESSON                                          \n");

            /*
            sql.append(" SELECT \n");
            sql.append("     A.MODULE,A.LESSON,A.SDESC,A.TYPES,A.OWNER,A.STARTING, \n");
            sql.append("     CASE COUNT(B.USERID) WHEN 0 THEN 'N' ELSE 'Y' END AS F_EXIST1, \n");
            sql.append("     CASE COUNT(C.LESSON) WHEN 0 THEN 'N' ELSE 'Y' END AS F_EXIST2 \n");
            sql.append(" FROM TZ_SUBJLESSON A , TZ_PROGRESS B, TZ_EXAMPAPER C \n");
            sql.append(" WHERE A.SUBJ = B.SUBJ(+) \n");
            sql.append(" AND A.LESSON = B.LESSON(+) \n");
            sql.append(" AND A.SUBJ = C.SUBJ(+) \n");
            sql.append(" AND A.LESSON = C.LESSON(+) \n");
            sql.append(" AND A.SUBJ = " + StringManager.makeSQL(s_subj) + " \n");
            sql.append(" AND B.YEAR(+) = " + StringManager.makeSQL(s_year) + " \n");
            sql.append(" AND B.SUBJSEQ(+) = " + StringManager.makeSQL(s_subjseq) + " \n");
            sql.append(" AND B.USERID(+) = " + StringManager.makeSQL(s_userid) + " \n");
            sql.append(" AND C.YEAR(+) = " + StringManager.makeSQL(s_year) + " \n");
            sql.append(" AND C.SUBJSEQ(+) = " + StringManager.makeSQL(s_subjseq) + " \n");
            sql.append(" GROUP BY A.MODULE, A.LESSON, A.SDESC, A.TYPES, A.OWNER, A.STARTING \n");
            sql.append(" ORDER BY A.MODULE, A.LESSON, A.SDESC, A.TYPES\n");
            */

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {

                data = new MfLessonData();

                data.setModule(ls.getString("module"));
                data.setLesson(ls.getString("lesson"));
                data.setSdesc(ls.getString("sdesc"));
                data.setTypes(ls.getString("types"));
                data.setOwner(ls.getString("owner"));
                data.setStarting(ls.getString("starting"));
                data.setIseducated(ls.getString("f_exist1"));
                data.setIsexam(ls.getString("f_exist2"));

                list1.add(data);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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

        return list1;
    }

    /**
     * 진도체크
     *
     * @param box receive from the form object and session (p_gubun: START-진도시작,
     *        END-진도종료
     * @return int 진도체크 결과
     *
     *         Process 1. 정상 학습자 체크 : 정상 학습자 아니면 진도데이터 미생성 2. 기 체크여부 체크 :
     *         기체크되었으면 tz_progress update 3. 진도제한 체크 3.1. 진도제한차시수 체크 3.2.
     *         고용보험과정이면 진도제한 차시수와 비교, 큰 넘으로 진도제한 설정 4. 고용보험과정이면 이전차시 학습/평가미실시건
     *         존재여부 체크 5. 진도데이터 생성
     */
    public String EduCheck(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null, ls1 = null, ls2 = null, ls3 = null;

        StringBuilder sb = new StringBuilder();

        String sql = "";
        String results = "";
        String s_subj = box.getSession("s_subj");
        String s_year = box.getSession("s_year");
        String s_subjseq = box.getSession("s_subjseq");
        String s_userid = box.getSession("userid");
        String s_eduauth = box.getSession("s_eduauth");
        String s_grcode = box.getSession("tem_grcode");
        String p_lesson = box.getString("p_lesson");
        String p_oid = box.getString("p_oid");
        String p_gubun = box.getString("p_gubun");
        String p_hostAddress = box.getString("hostAddress");
        String progressChkYn = "Y";

        String v_session_time = "", v_total_time = "", v_ldate = "", v_sysdate = "", v_first_end = "";

        int isOk = 1, v_edulimit = 0, v_myeducnt = 0, v_lesson = 0;

        p_oid = p_oid.equals("") ? "1" : p_oid; //oid 없으면 디폴트 OID값 셋팅("1")
        p_gubun = p_gubun.equals("") ? "END" : p_gubun; //구분값 없으면 진도체크

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 1.정상 학습자여부 체크

            if (!s_eduauth.equals("Y")) {
                return "OK";
            }

            if (!EduEtc1Bean.isCurrentStudent(s_subj, s_year, s_subjseq, s_userid).equals("Y")) {
                return "OK";
            }

            sb.append(" SELECT  /* 기 진도체크여부 (EduStartBean.EduCheck) */            		\n");
            sb.append("         *                                                       \n");
            sb.append("   FROM  (                                                       \n");
            sb.append("         SELECT                                                  \n");
            sb.append("                 ROWNUM rnum                                     \n");
            sb.append("             ,   TOTAL_TIME                                      \n");
            sb.append("             ,   LDATE                                           \n");
            sb.append("             ,   TO_CHAR (SYSDATE, 'YYYYMMDDHH24MISS') NOWTIME   \n");
            sb.append("             ,   OID                                             \n");
            sb.append("             ,   FIRST_END                                       \n");
            sb.append("           FROM  TZ_PROGRESS                                     \n");
            sb.append("          WHERE  SUBJ =    '").append(s_subj).append("'          \n");
            sb.append("            AND  YEAR =    '").append(s_year).append("'          \n");
            sb.append("            AND  SUBJSEQ = '").append(s_subjseq).append("'       \n");
            sb.append("            AND  USERID =  '").append(s_userid).append("'        \n");
            sb.append("            AND  LESSON =  '").append(p_lesson).append("'        \n");
            if (!p_oid.equals("1")) {
                sb.append("   AND  OID = '").append(p_oid).append("'                    \n");
            }
            //sb.append("            AND  FIRST_END IS NOT NULL                           \n");
            sb.append("         )                                                       \n");
            sb.append("  WHERE  RNUM < 2                                                \n");
            
            ls = connMgr.executeQuery(sb.toString());

            // 해당 과정 page 진도율 방식 여부 조회
            sql  = "\n /* page 진도율 방식 여부 조회 */ ";
            sql += "\n select nvl(a.page_chk_yn, 'N') as pageChkYn	";
            sql += "\n   from tz_subjseq a							";
            sql += "\n  where a.subj 	= " + StringManager.makeSQL(s_subj);
            sql += "\n    and a.year 	= " + StringManager.makeSQL(s_year);
            sql += "\n    and a.subjseq = " + StringManager.makeSQL(s_subjseq);

            ls2 = connMgr.executeQuery(sql);

            if (ls2.next()) {
                if("Y".equals(ls2.getString("pageChkYn"))){
                    progressChkYn = "N";

                    // 해당 과정 차시 완료 여부 조회
                    sql  = "\n /* 과정 차시 완료 여부 조회 */   ";
                    sql += "\n select chapter_no            ";
                    sql += "\n   from tz_subj_play_chk a    ";
                    sql += "\n  where a.userid     = " + StringManager.makeSQL(s_userid);
                    sql += "\n    and a.grcode     = " + StringManager.makeSQL(s_grcode);
                    sql += "\n    and a.subj       = " + StringManager.makeSQL(s_subj);
                    sql += "\n    and a.subjseq    = " + StringManager.makeSQL(s_subjseq);
                    sql += "\n    and a.year       = " + StringManager.makeSQL(s_year);
                    sql += "\n    and a.chapter_no = " + StringManager.makeSQL(p_lesson);
                    sql += "\n    and a.c_page     = a.t_page   ";
                    sql += "\n    and a.c_time     = a.t_time	";

                    ls3 = connMgr.executeQuery(sql);

                    if (ls3.next()) {
                        progressChkYn = "Y";
                    }
                }
            }

            System.out.println("progressChkYn : " + progressChkYn);

            // 학습시작
            if (p_gubun.equals("START") ) {
            	// 해당학습차시 학습이력이 있는경우
            	if (ls.next()) {
            		if("Y".equals(progressChkYn)) {
                        sql = "\n update tz_progress 												";
                        sql += "\n    set ldate			= to_char(sysdate,'YYYYMMDDHH24MISS') 		";
                        sql += "\n      , lesson_count	= lesson_count+1            				";
                        sql += "\n  where subj=? and year=?  and subjseq=? and userid=?  and lesson=? ";
                        if (!p_oid.equals("1")) {
                            sql += "\n   and oid=" + StringManager.makeSQL(p_oid);
                        }

                        pstmt = connMgr.prepareStatement(sql);
                        pstmt.setString(1, s_subj);
                        pstmt.setString(2, s_year);
                        pstmt.setString(3, s_subjseq);
                        pstmt.setString(4, s_userid);
                        pstmt.setString(5, p_lesson);
                        isOk = pstmt.executeUpdate();
                        if (isOk == 1) {
                            results = "OK";
                        } else {
                            results = "진도체크(" + p_gubun + ") 문제가 발생하였습니다. 운영자에게 문의바랍니다.";
                        }
                    }
            	// 해당학습차시 최초학습 경우
            	}else{
            		
            		// 진도제한 체크
            		sql  = "\n select NVL(a.edulimit,0) limit, count(lesson) as lesson	";
            		sql += "\n   from tz_subjseq a										";
            		sql += "\n  inner join tz_subjlesson b								";
            		sql += "\n     on a.subj 	 = b.subj								";
            		sql += "\n  where a.subj 	 = " + StringManager.makeSQL(s_subj);
            		sql += "\n     and a.year 	 = " + StringManager.makeSQL(s_year);
            		sql += "\n     and a.subjseq = " + StringManager.makeSQL(s_subjseq);
            		sql += "\n  group by a.edulimit										";

                    ls = connMgr.executeQuery(sql);
                    if (ls.next()) {
                        v_edulimit = ls.getInt("limit");
                        v_lesson = ls.getInt("lesson");
                    }
                    if (ls != null) {
                        try {
                            ls.close();
                        } catch (Exception e) {
                        }
                    }
                    
                    if (v_edulimit != 0) {

                        int v_limit = 0;

                        v_limit = v_lesson * v_edulimit / 100;

                        sql = "select count(distinct lesson) CNTS from tz_progress" + " where subj=" + StringManager.makeSQL(s_subj) + "   and year="
                                + StringManager.makeSQL(s_year) + "   and subjseq=" + StringManager.makeSQL(s_subjseq) + "   and userid="
                                + StringManager.makeSQL(s_userid) + "   and lesson !=" + StringManager.makeSQL(p_lesson)
                                + "   and substring(first_end,1,8) = to_char(sysdate, 'YYYYMMDD') ";
                        if (!p_oid.equals("1"))
                            sql += " and oid=" + StringManager.makeSQL(p_oid);

                        ls = connMgr.executeQuery(sql);

                        ls.next();
                        v_myeducnt = ls.getInt("CNTS");//System.out.println(sql);System.out.println("v_myeducnt : " + v_myeducnt);

                        if (v_myeducnt >= v_limit) {
                            return "금일 학습할 수 있는 제한을 넘었으므로 진도체크되지 않습니다.";
                        }
                        if (ls != null) {
                            try {
                                ls.close();
                            } catch (Exception e) {
                            }
                        }
                        // 4. 고용보험과정이면 이전차시 학습/평가미실시건 존재여부 체크
                        //if(v_isgoyong.equals("Y")){
                        //  v_noteducnt = EduEtc1Bean.get_noteducnt(s_subj,s_year,s_subjseq,s_userid,v_contenttype,p_lesson,p_oid);
                        //  System.out.println("v_noteducnt="+v_noteducnt);
                        //
                        //  if(v_noteducnt != 0){
                        //      return  "학습하지 않은 차시 또는 미응시 평가가 있으므로 진도체크되지 않습니다";
                        //  }
                        //}
                    }

                    if("Y".equals(progressChkYn)) {
                        sql = "\n insert into tz_progress(													";
                        sql += "\n     subj, year, subjseq, userid, lesson , oid, session_time, total_time,	";
                        sql += "\n     first_edu, first_end, lesson_count, ldate, indate					";
                        sql += "\n )																		";
                        sql += "\n values(																	";
                        sql += "\n     ?, ?, ?, ?, ?, ?, '00:00:00.00', '00:00:00.00', 						";
                        sql += "\n     '',																	";
                        sql += "\n     '', 																	";
                        sql += "\n     1, 																	";
                        sql += "\n     to_char(sysdate,'YYYYMMDDHH24MISS'),									";
                        sql += "\n     to_char(sysdate,'YYYYMMDDHH24MISS')									";
                        sql += "\n )										 								";
                        pstmt = connMgr.prepareStatement(sql);
                        pstmt.setString(1, s_subj);
                        pstmt.setString(2, s_year);
                        pstmt.setString(3, s_subjseq);
                        pstmt.setString(4, s_userid);
                        pstmt.setString(5, p_lesson);
                        pstmt.setString(6, p_oid);
                        isOk = pstmt.executeUpdate();

                        if (isOk == 1) {
                            results = "OK";
                        } else {
                            results = "진도시작체크(" + p_gubun + ") 문제가 발생하였습니다. 운영자에게 문의바랍니다.";
                        }
                    }
            	}
            	
            // 학습종료
            } else if ( p_gubun.equals("END") ) {
            	if (ls.next()) {
                    if("Y".equals(progressChkYn)) {
                        v_total_time = ls.getString("total_time");
                        v_ldate = ls.getString("ldate");
                        v_sysdate = ls.getString("nowtime");
                        v_session_time = EduEtc1Bean.get_duringtime(v_ldate, v_sysdate);
                        v_total_time = EduEtc1Bean.add_duringtime(v_total_time, v_session_time);

                        sql = "\n update tz_progress 												";
                        sql += "\n    set ldate			= to_char(sysdate,'YYYYMMDDHH24MISS') 		";
                        sql += "\n      , session_time	=" + StringManager.makeSQL(v_session_time);
                        sql += "\n      , total_time  	=" + StringManager.makeSQL(v_total_time);
                        sql += "\n  where subj=? and year=?  and subjseq=? and userid=?  and lesson=? ";
                        if (!p_oid.equals("1")) {
                            sql += "\n   and oid=" + StringManager.makeSQL(p_oid);
                        }

                        pstmt = connMgr.prepareStatement(sql);
                        pstmt.setString(1, s_subj);
                        pstmt.setString(2, s_year);
                        pstmt.setString(3, s_subjseq);
                        pstmt.setString(4, s_userid);
                        pstmt.setString(5, p_lesson);
                        isOk = pstmt.executeUpdate();
                    }
        			results = "OK";
            	} else {
                    if("Y".equals(ls2.getString("pageChkYn"))) {
                        if ("Y".equals(progressChkYn)) {
                            sql = "\n insert into tz_progress(													";
                            sql += "\n     subj, year, subjseq, userid, lesson , oid, session_time, total_time,	";
                            sql += "\n     first_edu, first_end, lesson_count, ldate, indate					";
                            sql += "\n )																		";
                            sql += "\n values(																	";
                            sql += "\n     ?, ?, ?, ?, ?, ?, '00:00:00.00', '00:00:00.00', 						";
                            sql += "\n     to_char(sysdate,'YYYYMMDDHH24MISS'),							    	";
                            sql += "\n     to_char(sysdate,'YYYYMMDDHH24MISS'), 								";
                            sql += "\n     1, 																	";
                            sql += "\n     to_char(sysdate,'YYYYMMDDHH24MISS'),									";
                            sql += "\n     to_char(sysdate,'YYYYMMDDHH24MISS')									";
                            sql += "\n )										 								";
                            pstmt = connMgr.prepareStatement(sql);
                            pstmt.setString(1, s_subj);
                            pstmt.setString(2, s_year);
                            pstmt.setString(3, s_subjseq);
                            pstmt.setString(4, s_userid);
                            pstmt.setString(5, p_lesson);
                            pstmt.setString(6, p_oid);

                            isOk = pstmt.executeUpdate();
                        }
                    }
                }
            	
            // 학습완료(진도적용)
            } else {
            	if (ls.next()) {
            		// 진도종료체크시 세션타임,총학습시간,횟수 update
                    v_total_time = ls.getString("total_time");
                    v_ldate = ls.getString("ldate");
                    v_sysdate = ls.getString("nowtime");
                    v_first_end = ls.getString("first_end");
                    v_session_time = EduEtc1Bean.get_duringtime(v_ldate, v_sysdate);
                    v_total_time = EduEtc1Bean.add_duringtime(v_total_time, v_session_time);
                    
                    if( v_first_end == null || "".equals(v_first_end) ) {
                        if("Y".equals(progressChkYn)) {
                            sql = "\n update tz_progress 												";
                            sql += "\n    set first_edu  	= indate								    ";
                            sql += "\n      , first_end  	= to_char(sysdate,'YYYYMMDDHH24MISS')       ";
                            sql += "\n      , session_time	=" + StringManager.makeSQL(v_session_time);
                            sql += "\n  where subj=? and year=?  and subjseq=? and userid=?  and lesson=? ";
                            if (!p_oid.equals("1")) {
                                sql += "\n   and oid=" + StringManager.makeSQL(p_oid);
                            }

                            pstmt = connMgr.prepareStatement(sql);
                            pstmt.setString(1, s_subj);
                            pstmt.setString(2, s_year);
                            pstmt.setString(3, s_subjseq);
                            pstmt.setString(4, s_userid);
                            pstmt.setString(5, p_lesson);
                            isOk = pstmt.executeUpdate();


                            //점수 조정
                            isOk = CalcUtil.getInstance().calc_score(connMgr, CalcUtil.STEP, s_subj, s_year, s_subjseq, s_userid);

                            if (isOk == 1) {
                                sql = "insert into tz_progress_history (subj, year, subjseq, lesson , oid, userid, session_time, first_edu, first_end, remote_ip) "
                                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, to_char(sysdate,'YYYYMMDDHH24MISS'), ?)";
                                pstmt = connMgr.prepareStatement(sql);
                                pstmt.setString(1, s_subj);
                                pstmt.setString(2, s_year);
                                pstmt.setString(3, s_subjseq);
                                pstmt.setString(4, p_lesson);
                                pstmt.setString(5, p_oid);
                                pstmt.setString(6, s_userid);
                                pstmt.setString(7, v_session_time);
                                pstmt.setString(8, v_ldate);
                                pstmt.setString(9, p_hostAddress);

                                isOk = pstmt.executeUpdate();
                            }
                        }
                    }
            	}
            }

            connMgr.commit();

        } catch (Exception ex) {
            //connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
            System.out.println("EduCheck 끝");
        }
        return results;
    }

    /**
     * 진도/목차 리스트 조회
     *
     * @param box receive from the form object and session
     * @return ArrayList 진도/목차리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectEduList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ListSet ls1 = null;
        ListSet ls2 = null;

        ArrayList list = null;
        EduListData x = null;

        StringBuffer sql = new StringBuffer();

        String p_subj = box.getString("p_subj");
        String p_year = box.getString("p_year");
        String p_subjseq = box.getString("p_subjseq");
        String p_userid = box.getString("p_userid");
        String p_ispreview = box.getString("p_ispreview");

        if (p_subj.equals("") || p_year.equals("") || p_subjseq.equals("")) {
            p_subj = box.getSession("s_subj");
            p_year = box.getSession("s_year");
            p_subjseq = box.getSession("s_subjseq");
            p_userid = box.getSession("userid");
        }

        if (p_userid.equals("")) {
            p_userid = box.getSession("userid");
        }

        try {

            connMgr = new DBConnectionManager();

            connMgr.setAutoCommit(false);

            if (p_ispreview.equals("Y") || p_year.equals("2000")) {
            } else {
                CalcUtil.getInstance().calc_score(connMgr, CalcUtil.ALL, p_subj, p_year, p_subjseq, p_userid);
            }

            list = new ArrayList();

            String v_str2 = " YEAR = " + StringManager.makeSQL(p_year) + " AND SUBJSEQ = " + StringManager.makeSQL(p_subjseq) + " ";

            String v_str1 = v_str2 + " AND USERID = " + StringManager.makeSQL(p_userid) + " ";

            sql.append(" SELECT A.LESSON, A.SDESC, A.MODULE, A.ISBRANCH,A.STARTING, \n");
            sql.append("        (SELECT SDESC FROM TZ_SUBJMODULE WHERE MODULE=A.MODULE AND SUBJ=A.SUBJ) MODULENM, \n");
            sql.append("        (SELECT FIRST_EDU FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1 + " AND LESSON=A.LESSON) FIRST_EDU, \n");
            sql.append("        (SELECT FIRST_END FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1 + " AND LESSON=A.LESSON) FIRST_END, \n");
            sql.append("        (SELECT SESSION_TIME FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1 + " AND LESSON=A.LESSON) SESSION_TIME, \n");
            sql.append("        (SELECT TOTAL_TIME FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1 + " AND LESSON=A.LESSON) TOTAL_TIME, \n");
            sql.append("        (SELECT LESSON_COUNT FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1 + " AND LESSON=A.LESSON) LESSON_COUNT, \n");
            sql.append("        (SELECT COUNT(ORDSEQ) FROM TZ_PROJORD WHERE SUBJ=A.SUBJ AND " + v_str2 + " AND LESSON=A.LESSON) CNTREPORT, \n");
            sql.append("        (SELECT SUBSTRING(NVL(EDUSTART,'00000000'),1,8) FROM TZ_SUBJSEQ WHERE SUBJ=A.SUBJ AND YEAR = '" + p_year
                    + "' AND SUBJSEQ = '" + p_subjseq + "') EDUSTART, \n");
            sql.append("        (SELECT LDATE FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1 + " AND LESSON=A.LESSON) LDATE, \n");
            sql
                    .append("        (SELECT count(subj) cnt FROM TZ_EXAMPAPER WHERE SUBJ=A.SUBJ AND YEAR = '2006' AND SUBJSEQ = '0001' AND LESSON=A.LESSON) EXAMTYPECNT, \n");
            sql.append("        NVL(B.FROMDATE,0) FROMDATE, NVL(B.TODATE,0) TODATE \n");
            sql.append("   FROM TZ_SUBJLESSON A, TZ_SUBJLESSONDATE B \n");
            sql.append("  WHERE A.SUBJ  =  B.SUBJ(+) AND A.MODULE  =  B.MODULE(+) AND A.LESSON  =  B.LESSON(+) \n");
            sql.append("    AND A.SUBJ = " + StringManager.makeSQL(p_subj) + " \n");
            sql.append("  ORDER BY A.LESSON \n");

            ls = connMgr.executeQuery(sql.toString());

            sql.setLength(0);

            while (ls.next()) {
                x = new EduListData();

                x.setRecordType("STEP");
                x.setLesson(ls.getString("lesson"));
                x.setSdesc(ls.getString("sdesc"));
                x.setStarting(ls.getString("starting"));
                x.setModule(ls.getString("module"));
                x.setModulenm(ls.getString("modulenm"));
                x.setIsbranch(ls.getString("isbranch"));
                x.setFirst_edu(ls.getString("first_edu"));
                x.setFirst_end(ls.getString("first_end"));
                x.setLdate(ls.getString("ldate"));

                if (x.getStarting() == null || x.getStarting().equals("")) {
                    x.setIsEducated("P");
                } else if (x.getFirst_edu() != null && !x.getFirst_edu().equals("")) {
                    x.setIsEducated("Y");
                }

                x.setSession_time(ls.getString("session_time"));
                x.setTotal_time(ls.getString("total_time"));
                x.setLesson_count(ls.getInt("lesson_count"));
                x.setCntReport(ls.getInt("cntReport"));
                if (x.getCntReport() > 0) {
                    x.setCntMyReport(get_repCnt(connMgr, p_subj, p_year, p_subjseq, x.getLesson(), p_userid));
                }

                x.setEdustart(ls.getString("edustart"));
                x.setFromdate(ls.getString("fromdate"));
                x.setTodate(ls.getString("todate"));

                list.add(x);

                if (ls.getInt("examtypecnt") > 0) {

                    //평가
                    sql.append(" SELECT EXAMTYPE FROM TZ_EXAMPAPER WHERE SUBJ= " + SQLString.Format(p_subj) + " \n");
                    sql.append("    AND YEAR=" + SQLString.Format(p_year) + " \n");
                    sql.append("    AND SUBJSEQ=" + SQLString.Format(p_subjseq) + " \n");
                    sql.append("    AND LESSON=" + SQLString.Format(x.getLesson()) + " \n");
                    sql.append("  ORDER BY EXAMTYPE \n");

                    if (ls1 != null) {
                        try {
                            ls1.close();
                        } catch (Exception e) {
                        }
                    }

                    ls1 = connMgr.executeQuery(sql.toString());

                    sql.setLength(0);

                    while (ls1.next()) {

                        x = new EduListData();

                        x.setRecordType("EXAM");
                        x.setLesson(ls.getString("lesson"));
                        x.setSdesc(ls.getString("sdesc"));
                        x.setModule(ls.getString("module"));
                        x.setModulenm(ls.getString("modulenm"));
                        x.setExamtype(ls1.getString("examtype"));

                        sql.append(" SELECT SCORE,STARTED,ENDED FROM TZ_EXAMRESULT  WHERE SUBJ=" + SQLString.Format(p_subj) + " \n");
                        sql.append("    AND YEAR=" + SQLString.Format(p_year) + " AND SUBJSEQ=" + SQLString.Format(p_subjseq) + " \n");
                        sql.append("    AND EXAMTYPE='" + ls1.getString("examtype") + "' AND USERID=" + SQLString.Format(p_userid) + " \n");
                        sql.append("    AND LESSON=" + SQLString.Format(x.getLesson()) + " \n");

                        if (ls2 != null) {
                            try {
                                ls2.close();
                            } catch (Exception e) {
                            }
                        }

                        ls2 = connMgr.executeQuery(sql.toString());

                        sql.setLength(0);

                        if (ls2.next()) {
                            x.setFirst_edu(ls2.getString("started"));
                            x.setFirst_end(ls2.getString("ended"));
                            x.setScore(ls2.getDouble("score"));
                            x.setIsEducated("Y");
                        }
                        list.add(x);
                    }
                }
            }

            //set row-span
            String v_module = "";
            for (int i = 0; i < list.size(); i++) {
                x = (EduListData) list.get(i);
                if (!v_module.equals(x.getModule())) {
                    v_module = x.getModule();
                    x.setRowspan(get_count(list, v_module));
                    list.set(i, x);
                }
            }

            connMgr.commit();
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * 진도/목차 리스트 조회-OBC
     *
     * @param box receive from the form object and session
     * @return ArrayList OBC-진도/목차리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectEduListOBC(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ListSet ls1 = null;
        ListSet ls2 = null;

        ArrayList list = null;

        EduListData x = null;

        StringBuffer sql = new StringBuffer();

        //set row-span
        String v_module = "01";
        String v_lesson = "00";

        //for Branching
        String v_sdesc = "";
        String v_isbranch = "N";

        String p_userid = box.getSession("userid");

        String p_subj = box.getString("p_subj");
        String p_year = box.getString("p_year");
        String p_subjseq = box.getString("p_subjseq");
        String p_ispreview = box.getString("p_ispreview");

        if (p_subj.equals("") || p_year.equals("") || p_subjseq.equals("")) {
            p_subj = box.getSession("s_subj");
            p_year = box.getSession("s_year");
            p_subjseq = box.getSession("s_subjseq");
            p_userid = box.getSession("userid");
        }

        //get my Branch
        int v_mybranch = EduEtc1Bean.get_mybranch(p_subj, p_year, p_subjseq, p_userid);
        if (v_mybranch == 0 || v_mybranch == 99)
            v_mybranch = 1;

        try {

            connMgr = new DBConnectionManager();

            connMgr.setAutoCommit(false);

            // 점수 재계산 (ALL)
            if (p_ispreview.equals("Y") || p_year.equals("2000")) {
            } else {
                CalcUtil.getInstance().calc_score(connMgr, CalcUtil.ALL, p_subj, p_year, p_subjseq, p_userid);
            }

            list = new ArrayList();
            String v_str2 = " year=" + StringManager.makeSQL(p_year) + " and subjseq=" + StringManager.makeSQL(p_subjseq) + " ";
            String v_str1 = v_str2 + " and userid=" + StringManager.makeSQL(p_userid) + " ";

            sql.append(" SELECT A.MODULE, B.SDESC MODULENM, A.LESSON, C.SDESC , C.ISBRANCH, A.OID, A.SDESC OIDNM, A.ORDERING, D.STARTING, \n");
            sql.append("        (SELECT FIRST_EDU FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1
                    + " AND LESSON=A.LESSON AND OID=A.OID) FIRST_EDU, \n");
            sql.append("        (SELECT FIRST_END FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1
                    + " AND LESSON=A.LESSON AND OID=A.OID) FIRST_END, \n");
            sql.append("        (SELECT SESSION_TIME FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1
                    + " AND LESSON=A.LESSON AND OID=A.OID) SESSION_TIME, \n");
            sql.append("        (SELECT LDATE FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1 + " AND LESSON=A.LESSON AND OID=A.OID) LDATE, \n");
            sql.append("        (SELECT TOTAL_TIME FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1
                    + " AND LESSON=A.LESSON AND OID=A.OID) TOTAL_TIME, \n");
            sql.append("        (SELECT LESSON_COUNT FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1
                    + " AND LESSON=A.LESSON AND OID=A.OID) LESSON_COUNT, \n");
            sql.append("        (SELECT COUNT(ORDSEQ) FROM TZ_PROJORD WHERE SUBJ=A.SUBJ AND " + v_str2 + " AND LESSON=A.LESSON) CNTREPORT, \n");
            sql.append("        (SELECT SUBSTRING(NVL(EDUSTART,'00000000'),1,8) FROM TZ_SUBJSEQ WHERE SUBJ=A.SUBJ AND YEAR = '" + p_year
                    + "' AND SUBJSEQ = '" + p_subjseq + "') EDUSTART, \n");
            sql.append("         NVL(E.FROMDATE,0) FROMDATE, NVL(E.TODATE,0) TODATE \n");
            sql.append("   FROM TZ_SUBJOBJ A, TZ_SUBJMODULE B, TZ_SUBJLESSON C,TZ_OBJECT D, TZ_SUBJLESSONDATE E \n");
            sql.append("  WHERE A.SUBJ=B.SUBJ AND A.SUBJ=C.SUBJ \n");
            sql.append("    AND A.MODULE = B.MODULE AND A.MODULE = C.MODULE \n");
            sql.append("    AND A.LESSON = C.LESSON \n");
            sql.append("    AND A.OID = D.OID \n");
            sql.append("    AND A.SUBJ  =  E.SUBJ(+) AND A.MODULE  =  E.MODULE(+) AND A.LESSON  =  E.LESSON(+) \n");
            sql.append("    AND A.SUBJ=" + StringManager.makeSQL(p_subj) + " \n");
            sql.append("  ORDER BY A.MODULE, A.LESSON, A.ORDERING, A.OID \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                if (!ls.getString("lesson").equals(v_lesson)) {
                    list = getExamObject(connMgr, list, p_subj, p_year, p_subjseq, p_userid, v_module, v_lesson);
                    v_lesson = ls.getString("lesson");
                    v_module = ls.getString("module");

                    v_isbranch = ls.getString("isbranch");
                    v_sdesc = ls.getString("sdesc");

                }

                x = new EduListData();
                x.setRecordType("STEP");
                x.setModule(ls.getString("module"));
                x.setModulenm(ls.getString("modulenm"));
                x.setLesson(ls.getString("lesson"));
                x.setSdesc(v_sdesc);
                x.setOid(ls.getString("oid"));
                if (v_isbranch.equals("Y")) {
                    x.setOidnm(GetCodenm.get_objectBranchName(connMgr, p_subj, x.getLesson(), ls.getInt("ordering"), v_mybranch));
                } else {
                    x.setOidnm(ls.getString("oidnm"));
                }
                x.setIsbranch(ls.getString("isbranch"));
                x.setFirst_edu(ls.getString("first_edu"));
                x.setFirst_end(ls.getString("first_end"));
                x.setStarting(ls.getString("starting"));

                String str = x.getStarting();

                //by heesung2 2006.07.26(학습진도율 계산방식 변경)
                String str3 = x.getFirst_edu();

                if (str.length() <= 1) {
                    x.setIsEducated("P");
                } else if (str3.length() > 0) {
                    x.setIsEducated("Y");
                } else {
                    x.setIsEducated("N");
                }

                x.setSession_time(ls.getString("session_time"));
                x.setTotal_time(ls.getString("total_time"));
                x.setLesson_count(ls.getInt("lesson_count"));
                x.setCntReport(ls.getInt("cntReport"));
                if (x.getCntReport() > 0) {
                    x.setCntMyReport(get_repCnt(connMgr, p_subj, p_year, p_subjseq, x.getLesson(), p_userid));
                }

                x.setEdustart(ls.getString("edustart"));
                x.setFromdate(ls.getString("fromdate"));
                x.setTodate(ls.getString("todate"));
                x.setLdate(ls.getString("ldate"));

                list.add(x);
            }
            //마지막 Lesson의 평가Object 얻기
            list = getExamObject(connMgr, list, p_subj, p_year, p_subjseq, p_userid, v_module, v_lesson);

            //set row-span
            v_module = "";
            v_lesson = "";
            for (int i = 0; i < list.size(); i++) {
                x = (EduListData) list.get(i);
                if (!v_module.equals(x.getModule())) {
                    v_module = x.getModule();
                    x.setRowspan(get_count(list, v_module));
                    list.set(i, x);
                }
                if (!v_lesson.equals(x.getLesson())) {
                    v_lesson = x.getLesson();
                    x.setRowspan_lesson(get_count_lesson(list, x.getModule(), x.getLesson()));
                    list.set(i, x);
                }
            }

            connMgr.commit();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return list;
    }

    /**
     * 진도/목차 리스트 조회-OBC 맛보기
     *
     * @param box receive from the form object and session
     * @return ArrayList OBC-진도/목차리스트 맛보기
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectEduListOBCPreview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls1 = null;
        ListSet ls2 = null;

        ArrayList list = null;

        EduListData x = null;

        StringBuffer sql = new StringBuffer();

        //set row-span
        String v_module = "01";
        String v_lesson = "00";

        //for Branching
        String v_sdesc = "";
        String v_isbranch = "N";

        String p_userid = box.getSession("userid");

        String p_subj = box.getString("p_subj");
        String p_year = box.getString("p_year");
        String p_subjseq = box.getString("p_subjseq");
        // String p_ispreview = box.getString("p_ispreview");

        if (p_subj.equals("") || p_year.equals("") || p_subjseq.equals("")) {
            p_subj = box.getSession("s_subj");
            p_year = box.getSession("s_year");
            p_subjseq = box.getSession("s_subjseq");
            p_userid = box.getSession("userid");
        }

        //get my Branch
        int v_mybranch = EduEtc1Bean.get_mybranch(p_subj, p_year, p_subjseq, p_userid);
        if (v_mybranch == 0 || v_mybranch == 99)
            v_mybranch = 1;

        try {

            connMgr = new DBConnectionManager();

            connMgr.setAutoCommit(false);

            list = new ArrayList();

            String v_str2 = " year=" + StringManager.makeSQL(p_year) + " and subjseq=" + StringManager.makeSQL(p_subjseq) + " ";
            String v_str1 = v_str2 + " and userid=" + StringManager.makeSQL(p_userid) + " ";

            sql.append(" SELECT A.MODULE, B.SDESC MODULENM, A.LESSON, C.SDESC , C.ISBRANCH, A.OID, A.SDESC OIDNM, A.ORDERING, D.STARTING, \n");
            sql.append("        (SELECT FIRST_EDU FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1
                    + " AND LESSON=A.LESSON AND OID=A.OID) FIRST_EDU, \n");
            sql.append("        (SELECT FIRST_END FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1
                    + " AND LESSON=A.LESSON AND OID=A.OID) FIRST_END, \n");
            sql.append("        (SELECT SESSION_TIME FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1
                    + " AND LESSON=A.LESSON AND OID=A.OID) SESSION_TIME, \n");
            sql.append("        (SELECT TOTAL_TIME FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1
                    + " AND LESSON=A.LESSON AND OID=A.OID) TOTAL_TIME, \n");
            sql.append("        (SELECT LESSON_COUNT FROM TZ_PROGRESS WHERE SUBJ=A.SUBJ AND " + v_str1
                    + " AND LESSON=A.LESSON AND OID=A.OID) LESSON_COUNT, \n");
            sql.append("        (SELECT COUNT(ORDSEQ) FROM TZ_PROJORD WHERE SUBJ=A.SUBJ AND " + v_str2 + " AND LESSON=A.LESSON) CNTREPORT, \n");
            sql.append("        (SELECT SUBSTRING(NVL(EDUSTART,'00000000'),1,8) FROM TZ_SUBJSEQ WHERE SUBJ=A.SUBJ AND YEAR = '" + p_year
                    + "' AND SUBJSEQ = '" + p_subjseq + "') EDUSTART, \n");
            sql.append("         NVL(E.FROMDATE,0) FROMDATE, NVL(E.TODATE,0) TODATE \n");
            sql.append("   FROM TZ_SUBJOBJ A, TZ_SUBJMODULE B, TZ_SUBJLESSON C,TZ_OBJECT D, TZ_SUBJLESSONDATE E , TZ_PREVIEWOBJ F \n");
            sql.append("  WHERE A.SUBJ=B.SUBJ AND A.SUBJ=C.SUBJ \n");
            sql.append("    AND A.MODULE = B.MODULE AND A.MODULE = C.MODULE \n");
            sql.append("    AND A.LESSON = C.LESSON \n");
            sql.append("    AND A.OID = D.OID \n");
            sql.append("    AND A.SUBJ = F.SUBJ \n");
            sql.append("    AND D.OID = F.OID \n");
            sql.append("    AND A.SUBJ  =  E.SUBJ(+) AND A.MODULE  =  E.MODULE(+) AND A.LESSON  =  E.LESSON(+) \n");
            sql.append("    AND A.SUBJ=" + StringManager.makeSQL(p_subj) + " \n");
            sql.append("  ORDER BY A.MODULE, A.LESSON, A.ORDERING, A.OID \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                if (!ls.getString("lesson").equals(v_lesson)) {

                    list = getExamObject(connMgr, list, p_subj, p_year, p_subjseq, p_userid, v_module, v_lesson);
                    v_lesson = ls.getString("lesson");
                    v_module = ls.getString("module");

                    v_isbranch = ls.getString("isbranch");
                    v_sdesc = ls.getString("sdesc");

                }

                x = new EduListData();
                x.setRecordType("STEP");
                x.setModule(ls.getString("module"));
                x.setModulenm(ls.getString("modulenm"));
                x.setLesson(ls.getString("lesson"));
                x.setSdesc(v_sdesc);
                x.setOid(ls.getString("oid"));
                if (v_isbranch.equals("Y")) {
                    x.setOidnm(GetCodenm.get_objectBranchName(connMgr, p_subj, x.getLesson(), ls.getInt("ordering"), v_mybranch));
                } else {
                    x.setOidnm(ls.getString("oidnm"));
                }
                x.setIsbranch(ls.getString("isbranch"));
                x.setFirst_edu(ls.getString("first_edu"));
                x.setFirst_end(ls.getString("first_end"));
                x.setStarting(ls.getString("starting"));

                String str = x.getStarting();

                //by heesung2 2006.07.26(학습진도율 계산방식 변경)
                String str3 = x.getFirst_edu();

                if (str.length() <= 1) {
                    x.setIsEducated("P");
                } else if (str3.length() > 0) {
                    x.setIsEducated("Y");
                } else {
                    x.setIsEducated("N");
                }

                x.setSession_time(ls.getString("session_time"));
                x.setTotal_time(ls.getString("total_time"));
                x.setLesson_count(ls.getInt("lesson_count"));
                x.setCntReport(ls.getInt("cntReport"));
                if (x.getCntReport() > 0) {
                    x.setCntMyReport(get_repCnt(connMgr, p_subj, p_year, p_subjseq, x.getLesson(), p_userid));
                }

                x.setEdustart(ls.getString("edustart"));
                x.setFromdate(ls.getString("fromdate"));
                x.setTodate(ls.getString("todate"));

                list.add(x);
            }
            //마지막 Lesson의 평가Object 얻기
            list = getExamObject(connMgr, list, p_subj, p_year, p_subjseq, p_userid, v_module, v_lesson);

            //set row-span
            v_module = "";
            v_lesson = "";
            for (int i = 0; i < list.size(); i++) {
                x = (EduListData) list.get(i);
                if (!v_module.equals(x.getModule())) {
                    v_module = x.getModule();
                    x.setRowspan(get_count(list, v_module));
                    list.set(i, x);
                }
                if (!v_lesson.equals(x.getLesson())) {
                    v_lesson = x.getLesson();
                    x.setRowspan_lesson(get_count_lesson(list, x.getModule(), x.getLesson()));
                    list.set(i, x);
                }
            }

            connMgr.commit();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return list;
    }

    /**
     * 학습시간, 최근학습일, 강의실접근횟수 - OBC
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectEduTimeCountOBC(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = new ArrayList();
        String sql = "";
        EduListData edudata = null;

        String p_subj, p_year, p_subjseq, p_userid;
        String etime = "";

        p_subj = box.getString("p_subj");
        p_year = box.getString("p_year");
        p_subjseq = box.getString("p_subjseq");
        p_userid = box.getString("p_userid");

        if (p_userid.equals("")) {
            p_userid = box.getSession("userid");
        }

        if (p_subj.equals("") || p_year.equals("") || p_subjseq.equals("")) {
            p_subj = box.getSession("s_subj");
            p_year = box.getSession("s_year");
            p_subjseq = box.getSession("s_subjseq");
            p_userid = box.getSession("userid");
        }

        try {
            connMgr = new DBConnectionManager();

            sql = "\nselect /*  EduStartBean.SelectEduTimeCountOBC : 992    */"
                    + "\n    trunc( ( sum(to_number(SUBSTR(A.total_time, 1, INSTR(A.total_time, ':')-1))) *60*60 + sum(to_number(SUBSTR(A.total_time, INSTR(A.total_time, ':')+1, 2)))*60 + sum(to_number(SUBSTR(A.total_time, INSTR(A.total_time, ':')+4, 2))) ) / (60*60) ,0) total_time, "
                    + "\n trunc( mod( (sum(to_number(SUBSTR(A.total_time, 1, INSTR(A.total_time, ':')-1))*60*60) + sum( to_number(SUBSTR(A.total_time, INSTR(A.total_time, ':')+1, 2))*60) + sum(to_number(SUBSTR(A.total_time, INSTR(A.total_time, ':')+4, 2))) )/60, 60), 0 ) total_minute,"
                    + "\n    mod ( sum(to_number(SUBSTR(A.total_time, 1, INSTR(A.total_time, ':')-1))*60*60 + to_number(SUBSTR(A.total_time, INSTR(A.total_time, ':')+1, 2))*60 + to_number(SUBSTR(A.total_time, INSTR(A.total_time, ':')+4, 2))) , 60) total_sec, "
                    + "\n    max(substr(first_end,1,8))  edudt, " + "\n    count(lesson_count) count   " + "\n from tz_progress  A"
                    + "\n WHERE  SUBJ = '" + p_subj + "' " + "\n   AND YEAR = '" + p_year + "' " + "\n   AND SUBJSEQ = '" + p_subjseq + "' "
                    + "\n   AND USERID = '" + p_userid + "' " + "\n   AND LESSON <> '000' ";

            ls = connMgr.executeQuery(sql);
            //System.out.println("#####################"+sql);
            if (ls.next()) {
                edudata = new EduListData();

                etime = ls.getString("total_time") + " 시간 " + ls.getString("total_minute") + " 분 " + ls.getString("total_sec") + " 초 ";
                edudata.setTotal_time(etime); // 학습시간
                edudata.setFirst_edu(ls.getString("edudt")); // 최근학습일
                edudata.setLesson_count(ls.getInt("count")); // 강의실접근횟수
                list.add(edudata);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return list;
    }

    /** 평가 Object 얻기 **/
    @SuppressWarnings("unchecked")
    public ArrayList getExamObject(DBConnectionManager connMgr, ArrayList list, String p_subj, String p_year, String p_subjseq, String p_userid,
            String p_module, String p_lesson) {
        ListSet ls = null, ls1 = null, ls2 = null;
        ArrayList list2 = list;
        String sql = "";
        EduListData x = null;

        //set row-span
        //      String v_module="";

        try {
            sql = "select examtype from tz_exampaper where subj=" + SQLString.Format(p_subj) + "   and year=" + SQLString.Format(p_year)
                    + "   and subjseq=" + SQLString.Format(p_subjseq) + "   and lesson=" + SQLString.Format(p_lesson) + " order by examtype";
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            ls1 = connMgr.executeQuery(sql);
            while (ls1.next()) {
                x = new EduListData();
                x.setRecordType("EXAM");
                x.setLesson(p_lesson);
                x.setSdesc(p_lesson);
                x.setModule(p_module);
                x.setModulenm(p_module);
                x.setPtype(ls1.getString("ptype"));

                sql = "select score,started,ended from tz_examresult  where subj=" + SQLString.Format(p_subj) + "   and year="
                        + SQLString.Format(p_year) + " and subjseq=" + SQLString.Format(p_subjseq) + "   and examtype='" + ls1.getString("ptype")
                        + "' and userid=" + SQLString.Format(p_userid) + "    and lesson=" + SQLString.Format(p_lesson);
                if (ls2 != null) {
                    try {
                        ls2.close();
                    } catch (Exception e) {
                    }
                }
                ls2 = connMgr.executeQuery(sql);
                if (ls2.next()) {
                    x.setFirst_edu(ls2.getString("started"));
                    x.setFirst_end(ls2.getString("ended"));
                    x.setScore(ls2.getDouble("score"));
                    x.setIsEducated("Y");
                }
                list2.add(x);
            }
        } catch (Exception ex) {
            System.out.println("ERROR=" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
        }
        return list2;
    }

    /** 모듈 Rowspan 얻기 **/
    @SuppressWarnings("unchecked")
    public int get_count(ArrayList list, String p_module) {
        int v_cnt = 0;
        EduListData x;
        for (int i = 0; i < list.size(); i++) {
            x = (EduListData) list.get(i);
            if (x.getModule().equals(p_module))
                v_cnt++;
        }
        return v_cnt;
    }

    /** Lesson Rowspan 얻기 **/
    @SuppressWarnings("unchecked")
    public int get_count_lesson(ArrayList list, String p_module, String p_lesson) {
        int v_cnt = 0;
        EduListData x;
        for (int i = 0; i < list.size(); i++) {
            x = (EduListData) list.get(i);
            if (x.getModule().equals(p_module) && x.getLesson().equals(p_lesson))
                v_cnt++;
        }
        return v_cnt;
    }

    /**
     * 리포트 제출건수 Return
     *
     * @param connMgr
     * @param p_subj  과정,년도,차수,레슨,id
     * @return String 제출한 리포트건수 p_lesson : 'ALL'이면 과정차수전체 리포트제출건수(중복제출 무시)
     *         'ALL'아니면 해당 레슨의 리포트제출건수(중복제출 무시)
     */
    public static int get_repCnt(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_userid)
            throws Exception {

        ListSet ls = null, ls1 = null;
        String sql = "", v_reptype, v_projgrp = p_userid;
        int resulti = 0, v_ordseq = 0;

        try {
            sql = " select  lesson,ordseq,reptype  from tz_projord where subj=" + SQLString.Format(p_subj);
            if (!p_lesson.equals("ALL"))
                sql += " and lesson=" + SQLString.Format(p_lesson);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_reptype = ls.getString("reptype");
                v_ordseq = ls.getInt("ordseq");
                if (v_reptype.equals("P")) { //Project이면 tz_projgrp에서 get projgrp
                    sql = "select projgrp from tz_projgrp where subj=" + SQLString.Format(p_subj) + "   and year=" + SQLString.Format(p_subj)
                            + " and subjseq=" + SQLString.Format(p_subjseq) + "   and ordseq=" + v_ordseq + " and userid="
                            + SQLString.Format(p_userid);
                    if (ls1 != null) {
                        try {
                            ls1.close();
                        } catch (Exception e) {
                        }
                    }
                    ls1 = connMgr.executeQuery(sql);
                    if (ls1.next())
                        v_projgrp = ls1.getString("projgrp");
                    else
                        return 0;
                }
                sql = "select count(distinct ordseq) CNTS from tz_projrep where subj=" + SQLString.Format(p_subj) + "   and year="
                        + SQLString.Format(p_subj) + " and subjseq=" + SQLString.Format(p_subjseq) + "   and ordseq=" + v_ordseq + " and projid="
                        + SQLString.Format(v_projgrp);
                if (ls1 != null) {
                    try {
                        ls1.close();
                    } catch (Exception e) {
                    }
                }
                ls1 = connMgr.executeQuery(sql);
                ls1.next();
                return ls1.getInt("CNTS");
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
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
        }
        return resulti;
    }

    /**
     * 학습점수정보 Return
     *
     * @param box
     * @return String
     */
    public EduScoreData SelectEduScore(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls1 = null;
        ListSet ls2 = null;

        EduScoreData x = null;

        StringBuffer sql = new StringBuffer();

        String p_subj = box.getString("p_subj");
        String p_year = box.getString("p_year");
        String p_subjseq = box.getString("p_subjseq");
        String p_userid = "";

        String p_accessGubun = box.getStringDefault("p_accessGubun", "none");

        if (p_userid.equals(""))
            p_userid = box.getSession("userid");
        if (p_subj.equals("") || p_year.equals("") || p_subjseq.equals("")) {
            p_subj = box.getSession("s_subj");
            p_year = box.getSession("s_year");
            p_subjseq = box.getSession("s_subjseq");
            p_userid = box.getSession("userid");
        }

        try {

            connMgr = new DBConnectionManager();

            connMgr.setAutoCommit(false);

            if (!box.getBoolean("needNoDetailRead")) {
                if ("none".equals(p_accessGubun)) {
                    CalcUtil.getInstance().calc_score(connMgr, CalcUtil.ALL, p_subj, p_year, p_subjseq, p_userid);
                } else {
                    CalcUtil.getInstance().calc_score(connMgr, CalcUtil.EXC, p_subj, p_year, p_subjseq, p_userid); //참여도 가중치에 미포함
                }
            }

            sql.append(" SELECT A.SCORE SCORE,TSTEP,HTEST,MTEST,FTEST,REPORT,ACT,ETC1,ETC2 \n");
            sql.append("        ,AVTSTEP,AVHTEST,AVMTEST,AVFTEST,AVREPORT,AVACT,AVETC1,AVETC2 \n");
            sql.append("        ,GRADSCORE,GRADSTEP,GRADEXAM,GRADHTEST,GRADFTEST,GRADREPORT,WSTEP,WMTEST \n");
            sql.append("        ,WFTEST,WHTEST,WREPORT,WACT,WETC1,WETC2,ISGRADUATED \n");
            sql.append("        ,B.EDUSTART EDUSTART,B.EDUEND EDUEND, REVIEWDAYS, REVIEWTYPE, GRADETC1 \n");
            sql.append("   FROM TZ_STUDENT A, TZ_SUBJSEQ B \n");
            sql.append("  WHERE A.SUBJ = " + StringManager.makeSQL(p_subj) + " \n");
            sql.append("    AND A.YEAR = " + StringManager.makeSQL(p_year) + " \n");
            sql.append("    AND A.SUBJSEQ = " + StringManager.makeSQL(p_subjseq) + " \n");
            sql.append("    AND A.USERID = " + StringManager.makeSQL(p_userid) + " \n");
            sql.append("    AND A.SUBJ=B.SUBJ AND A.YEAR=B.YEAR AND A.SUBJSEQ=B.SUBJSEQ \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                x = new EduScoreData();
                x.setScore(ls.getDouble("score"));
                x.setTstep(ls.getDouble("tstep"));
                x.setHtest(ls.getDouble("htest"));
                x.setMtest(ls.getDouble("mtest"));
                x.setFtest(ls.getDouble("ftest"));
                x.setReport(ls.getDouble("report"));
                x.setAct(ls.getDouble("act"));
                x.setEtc1(ls.getDouble("etc1"));
                x.setEtc2(ls.getDouble("etc2"));
                x.setAvtstep(ls.getDouble("avtstep"));
                x.setAvhtest(ls.getDouble("avhtest"));
                x.setAvmtest(ls.getDouble("avmtest"));
                x.setAvftest(ls.getDouble("avftest"));
                x.setAvreport(ls.getDouble("avreport"));
                x.setAvact(ls.getDouble("avact"));
                x.setAvetc1(ls.getDouble("avetc1"));
                x.setAvetc2(ls.getDouble("avetc2"));
                x.setGradscore(ls.getDouble("gradscore"));
                x.setGradstep(ls.getDouble("gradstep"));
                x.setGradexam(ls.getDouble("gradexam"));
                x.setGradhtest(ls.getDouble("gradhtest"));
                x.setGradftest(ls.getDouble("gradftest"));
                x.setGradreport(ls.getDouble("gradreport"));
                x.setGradetc1(ls.getDouble("gradetc1"));
                x.setWstep(ls.getDouble("wstep"));
                x.setWmtest(ls.getDouble("wmtest"));
                x.setWftest(ls.getDouble("wftest"));
                x.setWhtest(ls.getDouble("whtest"));
                x.setWreport(ls.getDouble("wreport"));
                x.setWact(ls.getDouble("wact"));
                x.setWetc1(ls.getDouble("wetc1"));
                x.setWetc2(ls.getDouble("wetc2"));
                x.setIsgraduated(ls.getString("isgraduated"));
                x.setEdustart(ls.getString("edustart"));
                x.setEduend(ls.getString("eduend"));
                x.setReviewdays(ls.getString("reviewdays"));
                x.setReviewtype(ls.getString("reviewtype"));
                //if (box.getBoolean("simpleRead"))
                x.makeScoreList(); //가중치별 점수 HashTable생성
            }

            connMgr.commit();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return x;
    }

    /**
     * 차시별 분기List Return
     *
     * @param box
     * @return String
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectEduBranch(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        MfBranchData x = null;

        String p_subj, p_year, p_subjseq, p_userid;

        p_subj = box.getSession("s_subj");
        p_year = box.getSession("s_year");
        p_subjseq = box.getSession("s_subjseq");
        p_userid = box.getSession("userid");
        int p_branch = box.getInt("p_branch");
        int v_mybranch = 0, isOk = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 학습자의 분기설정여부 체크, 학습이지만 분기정보 없으면 p_branch로 tz_student update..
            sql = "select NVL(branch,99) branch from tz_student where subj=" + StringManager.makeSQL(p_subj) + "   and year="
                    + StringManager.makeSQL(p_year) + "   and subjseq=" + StringManager.makeSQL(p_subjseq) + "   and userid="
                    + StringManager.makeSQL(p_userid);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_mybranch = ls.getInt("branch");
                if (v_mybranch == 99 && box.getString("p_gubun").equals("CONF")) {
                    sql = "update tz_student set branch=" + p_branch + " where subj=" + StringManager.makeSQL(p_subj) + "   and year="
                            + StringManager.makeSQL(p_year) + "   and subjseq=" + StringManager.makeSQL(p_subjseq) + "   and userid="
                            + StringManager.makeSQL(p_userid);
                    isOk = connMgr.executeUpdate(sql);
                    if (isOk != 1) {
                        //makeLog..
                        isOk = 1;
                    }
                }
            }

            sql = "select a.lesson LESSON,a.sdesc LESSONNM,b.sdesc BRNAME,b.starting STARTING " + "  from tz_subjlesson a, tz_subjbranch b "
                    + " where a.subj=b.subj and a.lesson=b.lesson " + "   and a.isbranch='Y'  and b.branch=" + p_branch + "   and a.subj="
                    + StringManager.makeSQL(p_subj) + " order by a.lesson ";
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            ls = connMgr.executeQuery(sql);
            list = new ArrayList();
            while (ls.next()) {
                x = new MfBranchData();
                x.setLesson(ls.getString("LESSON"));
                x.setLessonnm(ls.getString("LESSONNM"));
                x.setSdesc(ls.getString("BRNAME"));
                x.setStarting(ls.getString("STARTING"));
                list.add(x);
            }

            connMgr.commit();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * [OBC] 마스터폼 Tree Data 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList OBCTreeData리스트
     */

    @SuppressWarnings("unchecked")
    public ArrayList SelectTreeDataList_orignal(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls1 = null, ls2 = null, ls3 = null, ls4 = null, ls5 = null;

        ArrayList list1 = null;
        String sql = "";
        OBCTreeData d = null;
        int j = 1;

        String s_subj = box.getSession("s_subj");
        String s_year = box.getSession("s_year");
        String s_subjseq = box.getSession("s_subjseq");
        String s_userid = box.getSession("userid");
        // String p_tmp1 = box.getString("p_tmp1");
        // int p_branch    = 0;
        // p_branch = box.getInt("p_branch");

        // String f_branchsubj = "", v_lesson_brname = "";
        // int v_mybranch = EduEtc1Bean.get_mybranch(s_subj, s_year, s_subjseq, s_userid);
        // int v_curbranch = 99;

        String v_module = "", v_lesson = "", v_oid = "";

        String f_exist = "N", v_types = "1001";
        String v_extype = "", v_tmp = "", v_ptypenm = "";
        // String v_brtxt = "";
        boolean f_go = true;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            /* 공지사항-- Applet 최초 노드를 빈 0-Level로 할 수 없으므로 별도 아이콘으로 처리한다. */
            /* 맛보기 */
            if (s_year.equals("PREV")) {
                d = new OBCTreeData();
                d.setAp((j - 1) + "||PR||PR||MODULE1234||MO||SC||MODULE||0||1001||N||javascript:whenObj(" + (j - 1) + ")||PREVIEW||N");
                d.setAh("PR,PR,MODULE1234,MO,SC,MODULE,0,N,XXX");
                list1.add(d);
                j++;

                d = new OBCTreeData();
                d.setAp((j - 1) + "||PR||PR||LESSON1234||LE||SC||LESSON||0||1001||N||javascript:whenObj(" + (j - 1) + ")||Preview Lesson||N");
                d.setAh("PR,PR,LESSON1234,LE,SC,LESSON,0,N,XXX");
                list1.add(d);
                j++;

                sql = "select a.oid OID, a.oidnm OIDNM, a.ordering ORDERING, b.filetype FILETYPE, b.npage NPAGE, b.starting STARTING "
                        + "  from tz_previewobj a, tz_object b " + " where a.subj=" + StringManager.makeSQL(s_subj)
                        + "   and a.oid = b.oid order by a.ordering";
                if (ls5 != null) {
                    try {
                        ls5.close();
                    } catch (Exception e) {
                    }
                }
                ls5 = connMgr.executeQuery(sql);
                while (ls5.next()) {
                    d = new OBCTreeData();
                    d.setAp((j - 1) + "||PR||PR||" + ls5.getString("OID") + "||SC||SC||" + ls5.getString("FILETYPE") + "||" + ls5.getInt("NPAGE")
                            + "||1001||N||javascript:whenObj(" + (j - 1) + ")||" + ls5.getString("OIDNM") + "||N");
                    d.setAh("PR,PR," + ls5.getString("OID") + ",SC,SC," + ls5.getString("FILETYPE") + "," + ls5.getInt("NPAGE") + ",N,"
                            + ls5.getString("STARTING"));
                    list1.add(d);
                    j++;
                }
            }
            /*
             * ORACLE sql =
             * " select a.module module, a.sdesc modulenm, b.lesson lesson, b.sdesc lessonnm,    "
             * +
             * "         NVL(b.types,'1001') TYPES, NVL(a.types,'1001') MTYPES                "
             * +
             * "   from tz_subjmodule a, tz_subjlesson b                                      "
             * + "  where a.subj="+StringManager.makeSQL(s_subj) +
             * "    and b.module(+)= a.module                                                 "
             * +
             * "    and b.subj(+)=a.subj                                                      "
             * +
             * "  order by a.module, b.lesson                                                 "
             * ;
             */
            sql = " select a.module module, a.sdesc modulenm, b.lesson lesson, b.sdesc lessonnm, "
                    + "     NVL(b.types,'1001') TYPES, NVL(a.types,'1001') MTYPES, " + "     NVL(b.isbranch,'N') ISBRANCH "
                    + "   from  tz_subjmodule a , tz_subjlesson b                    " + "  where a.subj=" + StringManager.makeSQL(s_subj)
                    + "         and a.module  =  b.module(+)  and a.subj  =  b.subj(+)    "
                    + "  order by a.module, b.lesson                                ";
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            ls = connMgr.executeQuery(sql);
            while (ls.next()) { // ##1

                if (!ls.getString("module").equals(v_module)) {
                    v_module = ls.getString("module");
                    d = new OBCTreeData();
                    d.setAp((j - 1) + "||" + v_module + "||" + ls.getString("lesson") + "||MODULE1234||MO||SC||MODULE||0||"
                            + ls.getString("MTYPES") + "||N||javascript:whenObj(" + (j - 1) + ")||" + ls.getString("modulenm") + "||N");
                    d.setAh(v_module + "," + ls.getString("lesson") + ",MODULE1234,MO,SC,MODULE,0,N,XXX");
                    list1.add(d);
                    j++;
                }

                f_go = true; //Tree 출력 허용

                if (!ls.getString("lesson").equals(v_lesson)) {
                    v_lesson = ls.getString("lesson");

                    d = new OBCTreeData();
                    d.setAp((j - 1) + "||" + v_module + "||" + v_lesson + "||LESSON1234||LE||SC||LESSON||0||" + ls.getString("TYPES")
                            + "||N||javascript:whenObj(" + (j - 1) + ")||" + ls.getString("lessonnm") + "||N");
                    d.setAh(v_module + "," + v_lesson + ",LESSON1234,LE,SC,LESSON,0,N,XXX");
                    list1.add(d);
                    j++;
                }

                if (f_go) {

                    sql = "select a.type TYPE, a.oid OID, a.sdesc SDESC, a.ordering ORDERING," + "       b.starting STARTING,                    "
                            + "       NVL(a.types,'1001') TYPES, b.filetype FILETYPE, b.npage NPAGE               "
                            + "  from tz_subjobj a, tz_object b                                                   " + " where a.subj="
                            + StringManager.makeSQL(s_subj) + "   and a.oid = b.oid " + "   and a.module=" + StringManager.makeSQL(v_module)
                            + "   and lesson=" + StringManager.makeSQL(v_lesson)
                            + "   and type in ('SC','TM','TT')                                                    " + " order by ordering asc ";
                    if (ls1 != null) {
                        try {
                            ls1.close();
                        } catch (Exception e) {
                        }
                    }
                    ls1 = connMgr.executeQuery(sql);

                    while (ls1.next()) {
                        v_oid = ls1.getString("OID");
                        v_types = ls1.getString("TYPES");

                        sql = " select count(*) CNTS from tz_progress " + "  where subj=" + StringManager.makeSQL(s_subj) + "    and year="
                                + StringManager.makeSQL(s_year) + "    and subjseq=" + StringManager.makeSQL(s_subjseq) + "    and userid="
                                + StringManager.makeSQL(s_userid) + "    and lesson=" + StringManager.makeSQL(v_lesson) + "    and oid="
                                + StringManager.makeSQL(v_oid);

                        if (ls2 != null) {
                            try {
                                ls2.close();
                            } catch (Exception e) {
                            }
                        }
                        ls2 = connMgr.executeQuery(sql);
                        ls2.next();
                        if (ls2.getInt("CNTS") > 0)
                            f_exist = "Y";
                        else
                            f_exist = "N";

                        d = new OBCTreeData();
                        d.setAp((j - 1) + "||" + v_module + "||" + v_lesson + "||" + v_oid + "||SC||SC||" + ls1.getString("FILETYPE") + "||"
                                + ls1.getInt("NPAGE") + "||" + v_types + "||" + f_exist + "||javascript:whenObj(" + (j - 1) + ")||"
                                + ls1.getString("SDESC") + "||N");
                        d.setAh(v_module + "," + v_lesson + "," + v_oid + ",SC,SC," + ls1.getString("FILETYPE") + "," + ls1.getInt("NPAGE") + ","
                                + f_exist + "," + ls1.getString("STARTING") + ",NOT");
                        list1.add(d);
                        j++;

                    }

                    //평가 Object
                    sql = " select count(*) CNTS  from tz_exampaper " + "  where subj=" + StringManager.makeSQL(s_subj) + "  and year="
                            + StringManager.makeSQL(s_year) + "  and subjseq=" + StringManager.makeSQL(s_subjseq)
                            //+ "    and year='TEST' "
                            //+ "    and subjseq='TEST' "
                            + "    and lesson=" + StringManager.makeSQL(v_lesson);
                    if (ls4 != null) {
                        try {
                            ls4.close();
                        } catch (Exception e) {
                        }
                    }
                    ls4 = connMgr.executeQuery(sql);
                    ls4.next();
                    if (ls4.getInt("CNTS") > 0) {
                        /*
                         * Oracle sql =
                         * " select  SUBJ,YEAR,SUBJSEQ,LESSON,EXAMTYPE,CNTTOTAL,PARTSTART,PARTEND,   "
                         * +
                         * "          CNTLEVEL1,CNTLEVEL2,CNTLEVEL3,TESTDATE,TESTTIME,JOINTOTAL, "
                         * +
                         * "          F_EXPURL,F_MULTIEX,LRESNO,LDATE,F_USEHTML,F_OPEN,          "
                         * +
                         * "          decode(ptype,'Q',1,'M',2,3) ORD                            "
                         * +
                         * "   from tz_exammaster                                                "
                         * + "   where subj  ="+StringManager.makeSQL(s_subj) +
                         * "     and lesson="+StringManager.makeSQL(v_lesson) +
                         * "     and year='TEST' and subjseq='TEST' order by ORD"
                         * ;
                         */

                        sql = " select  SUBJ,YEAR,SUBJSEQ,LESSON,EXAMTYPE,LESSONSTART,LESSONEND,EXAMTIME,EXAMPOINT,EXAMCNT,TOTALSCORE,  "
                                + "          LEVEL1TEXT,LEVEL2TEXT,LEVEL3TEXT,ISOPENANSWER,ISOPENEXP,CNTLEVEL1,CNTLEVEL2,CNTLEVEL3, "
                                + "          Luserid,LDATE,          "
                                + "          Case examtype When 'Q' Then 1 When 'M' Then 2 Else 3 end as  ORD  "
                                + "   from tz_exampaper                                                " + "   where subj  ="
                                + StringManager.makeSQL(s_subj) + "     and year=" + StringManager.makeSQL(s_year) + "     and subjseq="
                                + StringManager.makeSQL(s_subjseq) + "     and lesson=" + StringManager.makeSQL(v_lesson) + "     order by ORD";

                        if (ls2 != null) {
                            try {
                                ls2.close();
                            } catch (Exception e) {
                            }
                        }
                        ls2 = connMgr.executeQuery(sql);

                        while (ls2.next()) {
                            v_extype = "T" + ls2.getString("EXAMTYPE");
                            v_tmp = v_extype + "000000" + ls2.getString("LESSON");

                            sql = " select count(*) CNTS from tz_examresult " + "  where subj=" + StringManager.makeSQL(s_subj) + "    and year="
                                    + StringManager.makeSQL(s_year) + "    and subjseq=" + StringManager.makeSQL(s_subjseq) + "    and userid="
                                    + StringManager.makeSQL(s_userid) + "    and lesson=" + StringManager.makeSQL(v_lesson) + "    and examtype="
                                    + StringManager.makeSQL(ls2.getString("EXAMTYPE"));

                            if (ls3 != null) {
                                try {
                                    ls3.close();
                                } catch (Exception e) {
                                }
                            }
                            ls3 = connMgr.executeQuery(sql);
                            ls3.next();
                            if (ls3.getInt("CNTS") > 0)
                                f_exist = "Y";
                            else
                                f_exist = "N";

                            if (ls2.getString("EXAMTYPE").equals("M"))
                                v_ptypenm = "Middle";
                            else if (ls2.getString("EXAMTYPE").equals("Q"))
                                v_ptypenm = "QUIZ";
                            else
                                v_ptypenm = "Final";

                            d = new OBCTreeData();
                            d.setAp((j - 1) + "||" + v_module + "||" + v_lesson + "||" + v_tmp + "||SC||" + v_extype + "||TEST||0||" + "0001||"
                                    + f_exist + "||javascript:whenObj(" + (j - 1) + ")||" + v_ptypenm + " Test||N");
                            d.setAh(v_module + "," + v_lesson + "," + v_tmp + ",SC," + v_extype + ",TEST,0," + f_exist + ",zedu_gong.exjumps");
                            list1.add(d);
                            j++;
                        }
                    }
                } // end if f_go==true
                //i++;
            } // End while of ##1

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (ls3 != null) {
                try {
                    ls3.close();
                } catch (Exception e) {
                }
            }
            if (ls4 != null) {
                try {
                    ls4.close();
                } catch (Exception e) {
                }
            }
            if (ls5 != null) {
                try {
                    ls5.close();
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
        return list1;
    }

    /**
     * SCO 매핑 조회
     *
     * @param box receive from the form object and session
     * @return ArrayList Object리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectMappingSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls2 = null;
        ArrayList list1 = null;
        String sql = "";
        SCOData data = null;

        String p_subj = box.getString("p_subj");
        String p_year = box.getString("p_year");
        String p_subjseq = box.getString("p_subjseq");
        String p_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "select distinct a.oid, a.scotitle, a.scolocate, a.sdesc , a.starting, a.highoid, "
                    + " a.parameterstring, a.datafromlms, a.prerequisites, a.masteryscore,  "
                    + " a.maxtimeallowed, a.timelimitaction, b.ordering, b.module , b.lesson , level as scothelevel, "
                    + " (SELECT lessonstatus FROM tz_progress WHERE subj = '"
                    + p_subj
                    + "'  AND YEAR = '"
                    + p_year
                    + "' "
                    + "  AND subjseq = '"
                    + p_subjseq
                    + "'  AND userid = '"
                    + p_userid
                    + "'  AND OID = b.OID  AND lesson = b.lesson) AS jindostatus "
                    + "from tz_object a , tz_subjobj b   "
                    + " where a.oid = b.oid and  b.subj = '"
                    + p_subj
                    + "' "
                    + " connect by a.highoid = prior a.oid start with a.highoid is null  order by b.module asc , b.lesson asc , b.ordering asc , a.oid asc ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new SCOData();
                data.setOid(ls.getString("oid"));
                data.setSdesc(ls.getString("sdesc"));
                data.setScolocate(ls.getString("scolocate"));
                data.setScoTitle(ls.getString("scotitle"));
                data.setOrdering(ls.getInt("ordering"));
                data.setModule(ls.getString("module"));
                data.setLesson(ls.getString("lesson"));
                data.setStarting(ls.getString("starting"));
                data.setParameterstring(ls.getString("parameterstring"));
                data.setDatafromlms(ls.getString("datafromlms"));
                data.setPrerequisites(ls.getString("prerequisites"));
                data.setMasteryscore(ls.getInt("masteryscore"));
                data.setMaxtimeallowed(ls.getString("maxtimeallowed"));
                data.setTimelimitaction(ls.getString("timelimitaction"));
                data.setHighoid(ls.getString("highoid"));
                data.setThelevel(ls.getInt("scothelevel"));
                data.setJindoStatus(ls.getString("jindostatus"));
                list1.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
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
        return list1;
    }

    /**
     * [OBC] 마스터폼 맛보기 Tree Data 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList OBCTreeData리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectTreeDataListPreview_original(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls1 = null, ls2 = null, ls3 = null, ls4 = null, ls5 = null;

        ArrayList list1 = null;
        String sql = "";
        OBCTreeData d = null;
        int j = 1;

        String s_subj = box.getSession("s_subj");
        String s_year = box.getSession("s_year");
        String s_subjseq = box.getSession("s_subjseq");
        String s_userid = box.getSession("userid");
        // String p_tmp1 = box.getString("p_tmp1");
        // String p_ispreview = box.getString("p_ispreview");

        // int p_branch = 0;
        // p_branch = box.getInt("p_branch");

        // String f_branchsubj = "", v_lesson_brname = "";
        // int v_mybranch = EduEtc1Bean.get_mybranch(s_subj, s_year, s_subjseq, s_userid);
        // int v_curbranch = 99;

        String v_module = "", v_lesson = "", v_oid = "";

        String f_exist = "N", v_types = "1001";
        // String v_extype = "", v_tmp = "", v_ptypenm = "", v_brtxt = "";
        boolean f_go = true;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            /* 공지사항-- Applet 최초 노드를 빈 0-Level로 할 수 없으므로 별도 아이콘으로 처리한다. */
            /* 맛보기 */
            //if (p_ispreview.equals("Y")){
            //    d = new OBCTreeData();
            //    d.setAp ( (j-1)+"||PR||PR||MODULE1234||MO||SC||MODULE||0||1001||N||javascript:whenObj("+(j-1)+")||PREVIEW||N");
            //    d.setAh ( "PR,PR,MODULE1234,MO,SC,MODULE,0,N,XXX");
            //    list1.add(d);
            //    j++;
            //
            //    d = new OBCTreeData();
            //    d.setAp ( (j-1)+"||PR||PR||LESSON1234||LE||SC||LESSON||0||1001||N||javascript:whenObj("+(j-1)+")||Preview Lesson||N");
            //    d.setAh ( "PR,PR,LESSON1234,LE,SC,LESSON,0,N,XXX");
            //    list1.add(d);
            //    j++;
            //
            //    sql = "select a.oid OID, a.oidnm OIDNM, a.ordering ORDERING, b.filetype FILETYPE, b.npage NPAGE, b.starting STARTING "
            //        + "  from tz_previewobj a, tz_object b "
            //        + " where a.subj="+StringManager.makeSQL(s_subj)
            //        + "   and a.oid = b.oid order by a.ordering";
            //    if(ls5 != null) { try { ls5.close(); }catch (Exception e) {} }
            //    ls5 = connMgr.executeQuery(sql);
            //    while(ls5.next()){
            //        d = new OBCTreeData();
            //        d.setAp ( (j-1)+"||PR||PR||"+ls5.getString("OID")+"||SC||SC||"+ls5.getString("FILETYPE")
            //                +"||"+ls5.getInt("NPAGE")+"||1001||N||javascript:whenObj("+(j-1)+")||"
            //                +ls5.getString("OIDNM")+"||N");
            //        d.setAh ( "PR,PR,"+ls5.getString("OID")+",SC,SC,"+ls5.getString("FILETYPE")
            //                +","+ls5.getInt("NPAGE")+",N,"+ls5.getString("STARTING"));
            //        list1.add(d);
            //        j++;
            //    }
            //}

            sql = " select distinct a.module module, a.sdesc modulenm, b.lesson lesson, b.sdesc lessonnm, ";
            sql += "     case b.types when null then '1001' else b.types end  TYPES, ";
            sql += "        case a.types when null then '1001' else a.types end MTYPES, ";
            sql += "     case b.isbranch when null then 'N' else b.isbranch end ISBRANCH ";
            sql += "   from  tz_subjmodule a , tz_subjlesson b,";
            sql += "     (  select ";
            sql += "   a.subj, a.module, a.lesson ";
            sql += " from ";
            sql += "   tz_subjobj a, tz_previewobj b ";
            sql += " where";
            sql += " a.subj = b.subj ";
            sql += " and a.oid = b.oid";
            sql += " and a.subj = '" + s_subj + "'";
            sql += "   ) c";
            sql += " where a.subj=" + StringManager.makeSQL(s_subj);
            sql += "   and a.module =* b.module  and a.subj=*b.subj    ";
            sql += "   and b.subj = c.subj                                         ";
            sql += "   and b.module = c.module                                     ";
            sql += "   and b.lesson = c.lesson                                     ";
            sql += " order by a.module, b.lesson                                ";

            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            ls = connMgr.executeQuery(sql);

            while (ls.next()) { // ##1
                if (!ls.getString("module").equals(v_module)) {
                    v_module = ls.getString("module");
                    d = new OBCTreeData();
                    d.setAp((j - 1) + "||" + v_module + "||" + ls.getString("lesson") + "||MODULE1234||MO||SC||MODULE||0||"
                            + ls.getString("MTYPES") + "||N||javascript:whenObj(" + (j - 1) + ")||" + ls.getString("modulenm") + "||N");
                    d.setAh(v_module + "," + ls.getString("lesson") + ",MODULE1234,MO,SC,MODULE,0,N,XXX");
                    list1.add(d);
                    j++;
                }

                f_go = true; //Tree 출력 허용

                if (!ls.getString("lesson").equals(v_lesson)) {
                    v_lesson = ls.getString("lesson");

                    d = new OBCTreeData();
                    d.setAp((j - 1) + "||" + v_module + "||" + v_lesson + "||LESSON1234||LE||SC||LESSON||0||" + ls.getString("TYPES")
                            + "||N||javascript:whenObj(" + (j - 1) + ")||" + ls.getString("lessonnm") + "||N");
                    d.setAh(v_module + "," + v_lesson + ",LESSON1234,LE,SC,LESSON,0,N,XXX");
                    list1.add(d);
                    j++;
                }

                if (f_go) {

                    sql = "select a.type TYPE, a.oid OID, a.sdesc SDESC, a.ordering ORDERING," + "       b.starting STARTING,                    "
                            + "       case a.types when null then '1001' else a.types end TYPES, b.filetype FILETYPE, b.npage NPAGE               "
                            + "  from tz_subjobj a, tz_object b                                                   " + " where a.subj="
                            + StringManager.makeSQL(s_subj) + "   and a.oid = b.oid " + "   and a.module=" + StringManager.makeSQL(v_module)
                            + "   and lesson=" + StringManager.makeSQL(v_lesson)
                            + "   and type in ('SC','TM','TT')                                                    " + " order by ordering asc ";
                    if (ls1 != null) {
                        try {
                            ls1.close();
                        } catch (Exception e) {
                        }
                    }
                    ls1 = connMgr.executeQuery(sql);

                    while (ls1.next()) {
                        v_oid = ls1.getString("OID");
                        v_types = ls1.getString("TYPES");

                        sql = " select count(*) CNTS from tz_progress " + "  where subj=" + StringManager.makeSQL(s_subj) + "    and year="
                                + StringManager.makeSQL(s_year) + "    and subjseq=" + StringManager.makeSQL(s_subjseq) + "    and userid="
                                + StringManager.makeSQL(s_userid) + "    and lesson=" + StringManager.makeSQL(v_lesson) + "    and oid="
                                + StringManager.makeSQL(v_oid) + "    and first_end is not null";

                        if (ls2 != null) {
                            try {
                                ls2.close();
                            } catch (Exception e) {
                            }
                        }
                        ls2 = connMgr.executeQuery(sql);
                        ls2.next();
                        if (ls2.getInt("CNTS") > 0)
                            f_exist = "Y";
                        else
                            f_exist = "N";

                        d = new OBCTreeData();
                        d.setAp((j - 1) + "||" + v_module + "||" + v_lesson + "||" + v_oid + "||SC||SC||" + ls1.getString("FILETYPE") + "||"
                                + ls1.getInt("NPAGE") + "||" + v_types + "||" + f_exist + "||javascript:whenObj(" + (j - 1) + ")||"
                                + ls1.getString("SDESC") + "||N");
                        d.setAh(v_module + "," + v_lesson + "," + v_oid + ",SC,SC," + ls1.getString("FILETYPE") + "," + ls1.getInt("NPAGE") + ","
                                + f_exist + "," + ls1.getString("STARTING") + ",NOT");
                        list1.add(d);
                        j++;

                    }

                } // end if f_go==true
                //i++;
            } // End while of ##1

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (ls3 != null) {
                try {
                    ls3.close();
                } catch (Exception e) {
                }
            }
            if (ls4 != null) {
                try {
                    ls4.close();
                } catch (Exception e) {
                }
            }
            if (ls5 != null) {
                try {
                    ls5.close();
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
        return list1;
    }

    /**
     * SCO 맛보기 매핑 조회
     *
     * @param box receive from the form object and session
     * @return ArrayList Object리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectMappingSubjListPreview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls2 = null;
        ArrayList list1 = null;
        // ArrayList list2 = null;
        String sql = "";
        // String sql2 = "";
        SCOData data = null;

        String p_subj = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "select distinct a.oid, a.scotitle, a.scolocate, a.sdesc , a.starting, a.highoid, "
                    + " a.parameterstring, a.datafromlms, a.prerequisites, a.masteryscore,  "
                    + " a.maxtimeallowed, a.timelimitaction, b.ordering, b.module , b.lesson , level as scothelevel,  "
                    + "  'incomplete' AS jindostatus "
                    + "from tz_object a , tz_subjobj b , tz_previewobj c    "
                    + " where a.oid = b.oid and b.oid = c.oid and  b.subj = '"
                    + p_subj
                    + "' "
                    + " connect by a.highoid = prior a.oid start with a.highoid is null  order by b.module asc , to_number(b.lesson) asc , b.ordering asc , a.oid asc ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new SCOData();
                data.setOid(ls.getString("oid"));
                data.setSdesc(ls.getString("sdesc"));
                data.setScolocate(ls.getString("scolocate"));
                data.setScoTitle(ls.getString("scotitle"));
                data.setOrdering(ls.getInt("ordering"));
                data.setModule(ls.getString("module"));
                data.setLesson(ls.getString("lesson"));
                data.setStarting(ls.getString("starting"));
                data.setParameterstring(ls.getString("parameterstring"));
                data.setDatafromlms(ls.getString("datafromlms"));
                data.setPrerequisites(ls.getString("prerequisites"));
                data.setMasteryscore(ls.getInt("masteryscore"));
                data.setMaxtimeallowed(ls.getString("maxtimeallowed"));
                data.setTimelimitaction(ls.getString("timelimitaction"));
                data.setHighoid(ls.getString("highoid"));
                data.setThelevel(ls.getInt("scothelevel"));
                data.setJindoStatus(ls.getString("jindostatus"));
                list1.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
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
        return list1;
    }

    /**
     * 과정명 조회
     *
     * @param box receive from the form object and session
     * @return ArrayList Object리스트
     */
    public String SelectSubjName(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        // SCOData data = null;
        String subjnm = "";

        String p_subj = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();

            sql = "select subjnm  " + "from tz_subj    " + " where subj = '" + p_subj + "' ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                subjnm = ls.getString("subjnm");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return subjnm;
    }

    /**
     * 과정 제한 차시 조회
     *
     * @param box receive from the form object and session
     * @return ArrayList Object리스트
     */
    public int SelectEudLimit(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int edulimit = 0;

        String p_subj = box.getString("p_subj");
        String p_year = box.getString("p_year");
        String p_subjseq = box.getString("p_subjseq");
        // String p_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();

            sql = "select NVL(edulimit,0) limit from tz_subjseq  " + " where subj= '" + p_subj + "' " + "   and year= '" + p_year + "' "
                    + "   and subjseq='" + p_subjseq + "'";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                edulimit = ls.getInt("limit");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return edulimit;
    }

    /**
     * Oid 갯수 조회
     *
     * @param box receive from the form object and session
     * @return ArrayList Object리스트
     */
    public int SelectOidCount(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int oid_count = 0;

        String p_subj = box.getString("p_subj");
        // String p_year = box.getString("p_year");
        // String p_subjseq = box.getString("p_subjseq");
        // String p_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();

            sql = "select count(*) as oid_count from tz_object a, tz_subjobj b where a.oid =b.oid and  " + " b.subj= '" + p_subj
                    + "' and ltrim(rtrim(a.starting)) is not null ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                oid_count = ls.getInt("oid_count");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return oid_count;
    }

    /**
     * 진도 나간 갯수 조회
     *
     * @param box receive from the form object and session
     * @return ArrayList Object리스트
     */
    public int SelectJindoCount(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int jindo_count = 0;

        String p_subj = box.getString("p_subj");
        String p_year = box.getString("p_year");
        String p_subjseq = box.getString("p_subjseq");
        String p_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();

            sql = "select count(*) as jindo_count from tz_progress   " + " where subj= '" + p_subj + "' " + "   and year= '" + p_year
                    + "' and userid = '" + p_userid + "' " + "   and subjseq='" + p_subjseq + "' "
                    + " and substr(ldate,1,8) = to_char(sysdate, 'YYYYMMDD') and lessonstatus = 'complete' ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                jindo_count = ls.getInt("jindo_count");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return jindo_count;
    }

    /**
     * 과정 제한 차시 조회
     *
     * @param box receive from the form object and session
     * @return ArrayList Object리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectEudLimitLesson(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        ArrayList list1 = null;
        SCOData data = null;

        String s_subj = box.getString("p_subj");
        String s_year = box.getString("p_year");
        String s_subjseq = box.getString("p_subjseq");
        String s_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "select distinct lesson  from tz_progress" + " where subj=" + StringManager.makeSQL(s_subj) + "   and year="
                    + StringManager.makeSQL(s_year) + "   and subjseq=" + StringManager.makeSQL(s_subjseq) + "   and userid="
                    + StringManager.makeSQL(s_userid) + "   and substr(first_end,1,8) = to_char(sysdate, 'YYYYMMDD') ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new SCOData();
                data.setLesson(ls.getString("lesson"));
                list1.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return list1;
    }

    /**
     * 학습기간, 복습기간 체크
     *
     * @param box receive from the form object and session
     * @return ArrayList Object리스트
     */
    public int SelectEudPeriod(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        // ArrayList list1 = null;
        // String s_eduperiod = "";
        int cnt = 0;

        String s_subj = box.getString("p_subj");
        String s_year = box.getString("p_year");
        String s_subjseq = box.getString("p_subjseq");
        // String s_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();
            // list1 = new ArrayList();

            sql = "select count(*) as cnt  from tz_subjseq " + " where subj=" + StringManager.makeSQL(s_subj) + "   and year="
                    + StringManager.makeSQL(s_year) + "   and subjseq=" + StringManager.makeSQL(s_subjseq)
                    + "  and to_char(sysdate,'YYYYMMDDHH24') between edustart and eduend ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                cnt = ls.getInt("cnt");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return cnt;
    }

    /**
     * 해당 ID의 입과생 존재 유무
     *
     * @param box receive from the form object and session
     * @return ProjectData
     */
    public int selectUserPage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        String s_subj = box.getString("p_subj"); //과정
        String s_subjseq = box.getString("p_subjseq"); //과정차수
        String s_year = box.getString("p_year"); //년도
        String s_user_id = box.getString("p_userid"); //입과생ID
        if (s_user_id.equals("")) {
            s_user_id = box.getSession("userid"); //입과생ID
        }

        if (s_subj.equals("") || s_year.equals("") || s_subjseq.equals("")) {
            s_subj = box.getSession("s_subj"); //과정
            s_subjseq = box.getSession("s_subjseq"); //과정차수
            s_year = box.getSession("s_year"); //년도

        }

        /*
         * p_subj = box.getString("p_subj"); p_year = box.getString("p_year");
         * p_subjseq = box.getString("p_subjseq"); p_userid =
         * box.getString("p_userid");
         * if(p_subj.equals("")||p_year.equals("")||p_subjseq.equals("")) {
         * p_subj = box.getSession("s_subj"); p_year = box.getSession("s_year");
         * p_subjseq = box.getSession("s_subjseq"); p_userid =
         * box.getSession("userid"); }else{ System.out.println("안  타자!!!"); }
         */

        int result = 0; //1:입과생임, 0:입과생이 아님
        try {
            connMgr = new DBConnectionManager();

            sql = "select count(userid) cnt ";
            sql += "from TZ_STUDENT ";
            sql += "where subj=" + StringManager.makeSQL(s_subj);
            sql += "  and year=" + StringManager.makeSQL(s_year);
            sql += "  and subjseq=" + StringManager.makeSQL(s_subjseq);
            sql += "  and userid=" + StringManager.makeSQL(s_user_id);

            ls = connMgr.executeQuery(sql);
            if (ls.next() && ls.getInt("cnt") > 0) {
                result = 1; //해당 ID가 입과생인 경우
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
     * 정보동의 체크
     *
     * @param box receive from the form object and session
     * @return is_Ok 0 : 정보동의필요 1 : 정보동의함
     * @throws Exception
     */
    public int firstCheck(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;

        String p_userid = box.getSession("userid");
        String p_subj = box.getString("p_subj");
        String p_year = box.getString("p_year");
        String p_subjseq = box.getString("p_subjseq");

        if (p_subj.equals("") || p_year.equals("") || p_subjseq.equals("")) {
            p_subj = box.getSession("subj");
            p_year = box.getSession("year");
            p_subjseq = box.getSession("subjseq");
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " select NVL(validation,'0') validation  ";
            sql += " from TZ_STUDENT                       ";
            sql += " where userid = " + StringManager.makeSQL(p_userid);
            sql += "   and subj    = " + StringManager.makeSQL(p_subj);
            sql += "   and year    = " + StringManager.makeSQL(p_year);
            sql += "   and subjseq = " + StringManager.makeSQL(p_subjseq);
            ls = connMgr.executeQuery(sql);
            Log.info.println(sql);
            if (ls.next()) {
                is_Ok = StringManager.toInt(ls.getString("validation"));
            } else {
                is_Ok = 1;
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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

        return is_Ok;
    }

    /**
     * 정보보호 확인
     *
     * @param box receive from the form object and session
     * @return is_Ok 1 : login ok 2 : login fail
     * @throws Exception
     */
    public int firstSubj(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int is_Ok = 0;

        String p_userid = box.getSession("userid");
        String p_subj = box.getString("p_subj");
        String p_year = box.getString("p_year");
        String p_subjseq = box.getString("p_subjseq");

        if (p_subj.equals("") || p_year.equals("") || p_subjseq.equals("")) {
            p_subj = box.getSession("subj");
            p_year = box.getSession("year");
            p_subjseq = box.getSession("subjseq");
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = " update TZ_STUDENT set validation = ?                         ";
            sql += "  where userid = ? and subj = ? and year = ? and subjseq = ? ";
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, "1");
            pstmt.setString(2, p_userid);
            pstmt.setString(3, p_subj);
            pstmt.setString(4, p_year);
            pstmt.setString(5, p_subjseq);
            is_Ok = pstmt.executeUpdate();

            if (is_Ok == 1) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return is_Ok;
    }

    /**
     * 과거이력
     *
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> getPastLog(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        ArrayList<DataBox> list1 = new ArrayList<DataBox>();

        // String v_userid = box.getString("p_userid");
        String v_subj = box.getString("p_subj");
        try {
            connMgr = new DBConnectionManager();

            sql += "  select ";
            sql += "    pkey, userid,  subjnm,  edustart, eduend, processpoint, reportpoint, testpoint, finaltestpoint, score, isgraduated ";
            sql += "      from tz_aesstold where pkey=" + SQLString.Format(v_subj);
            //sql+= "     from tz_aesstold where pkey="+SQLString.Format(v_subj)+" and userid="+SQLString.Format(v_userid)+" ";
            //sql+= "     from vz_aesstold where serialno='0183_2003_12315041' and userid="+SQLString.Format(v_userid)+" ";
            System.out.println(sql);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
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
        return list1;
    }

    /**
     * [OBC] 마스터폼 Tree Data 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList OBCTreeData리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectTreeDataList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls1 = null;

        ArrayList list1 = null;
        String sql = "";
        OBCTreeData d = null;
        int j = 1;

        String s_subj = box.getSession("s_subj");
        // String s_year = box.getSession("s_year");
        // String s_subjseq = box.getSession("s_subjseq");
        // String s_userid = box.getSession("userid");
        // String p_tmp1 = box.getString("p_tmp1");

        // Branch 과정 일 경우 사용 되는 변수
        // int p_branch    = 0;
        // p_branch = box.getInt("p_branch");
        // String  f_branchsubj= "", v_lesson_brname= "";
        // int v_mybranch  = EduEtc1Bean.get_mybranch(s_subj, s_year, s_subjseq, s_userid);
        // int v_curbranch = 99;
        // String v_extype = "", v_tmp= "", v_ptypenm= "", v_brtxt= "";

        String v_module = "";
        String v_lesson = "";
        String v_oid = "";
        String v_types = "1001";

        String f_exist = "N";
        boolean f_go = true;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "\r\n  SELECT                                                           "
                    + "\r\n     a.module module, a.sdesc modulenm,                            "
                    + "\r\n     b.lesson lesson, b.sdesc lessonnm,                            "
                    + "\r\n        ISNULL (b.TYPES, '1001') TYPES, ISNULL (a.TYPES, '1001') mtypes, "
                    + "\r\n        ISNULL (b.isbranch, 'N') isbranch                             "
                    + "\r\n  FROM tz_subjmodule a, tz_subjlesson b                            "
                    + "\r\n  WHERE 1=1                                                        " + "\r\n     AND a.subj = "
                    + StringManager.makeSQL(s_subj) + "\r\n     AND a.module  =  b.module(+)                                    "
                    + "\r\n     AND a.subj  =  b.subj(+)                                        "
                    + "\r\n  ORDER BY a.module, b.lesson                                      ";

            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            ls = connMgr.executeQuery(sql);

            while (ls.next()) { // ##1
                if (!ls.getString("lesson").equals("")) {
                    String delimeter = "||";

                    if (!ls.getString("module").equals(v_module)) {
                        v_module = ls.getString("module");

                        d = new OBCTreeData();
                        // Paramter Rule = Index||유형(MODULE,LESSON,SC)||목차명||Type(1001,1002,1003)||Mode(SC,TM,TT)||진도여부(Lesson_OID)
                        d.setAp(j + delimeter + "MODULE" + delimeter + ls.getString("modulenm") + delimeter + ls.getString("MTYPES") + delimeter
                                + "SC" + delimeter);
                        d.setAh(v_module + "," + ls.getString("lesson") + ",MODULE1234,MO,SC,MODULE,0,N,XXX");

                        list1.add(d);
                        j++;
                    }

                    f_go = true; // Tree 출력 허용

                    if (!ls.getString("lesson").equals(v_lesson)) {
                        v_lesson = ls.getString("lesson");

                        d = new OBCTreeData();
                        d.setAp(j + delimeter + "LESSON" + delimeter + ls.getString("lessonnm") + delimeter + ls.getString("TYPES") + delimeter
                                + "SC" + delimeter);
                        d.setAh(v_module + "," + v_lesson + ",LESSON1234,LE,SC,LESSON,0,N,XXX");

                        list1.add(d);
                        j++;
                    }

                    if (f_go) {

                        sql = "\r\n  SELECT                                                               "
                                + "\r\n        a.TYPE TYPE, a.OID OID, a.sdesc sdesc, a.ordering ordering,    "
                                + "\r\n           b.starting starting, ISNULL (a.TYPES, '1001') TYPES,           "
                                + "\r\n           b.filetype filetype, b.npage npage                          "
                                + "\r\n  FROM tz_subjobj a, tz_object b                                       "
                                + "\r\n  WHERE 1 = 1                                                          " + "\r\n    AND a.subj = "
                                + StringManager.makeSQL(s_subj) + "\r\n       AND a.OID = b.OID                                               "
                                + "\r\n       AND a.module = " + StringManager.makeSQL(v_module) + "\r\n       AND lesson = "
                                + StringManager.makeSQL(v_lesson) + "\r\n       AND TYPE IN ('SC', 'TM', 'TT')                                  "
                                + "\r\n  ORDER BY ordering ASC                                                ";

                        if (ls1 != null) {
                            try {
                                ls1.close();
                            } catch (Exception e) {
                            }
                        }
                        ls1 = connMgr.executeQuery(sql);

                        while (ls1.next()) {
                            v_oid = ls1.getString("OID");
                            v_types = ls1.getString("TYPES");

                            /*
                             * sql = "\r\n  SELECT COUNT(*) cnts         " +
                             * "\r\n  FROM tz_progress             " +
                             * "\r\n  WHERE 1 = 1                  " +
                             * "\r\n     AND subj = " +
                             * StringManager.makeSQL(s_subj) +
                             * "\r\n     AND YEAR = " +
                             * StringManager.makeSQL(s_year) +
                             * "\r\n     AND subjseq = " +
                             * StringManager.makeSQL(s_subjseq) +
                             * "\r\n     AND userid =  " +
                             * StringManager.makeSQL(s_userid) +
                             * "\r\n     AND lesson =  " +
                             * StringManager.makeSQL(v_lesson) +
                             * "\r\n     AND OID = " +
                             * StringManager.makeSQL(v_oid) +
                             * "\r\n     AND first_end IS NOT NULL ";
                             *
                             * if ( ls2 != null ) { try { ls2.close(); } catch (
                             * Exception e ) { } } ls2 =
                             * connMgr.executeQuery(sql); ls2.next();
                             *
                             * if ( ls2.getInt("CNTS") > 0) f_exist = "Y"; else
                             * f_exist = "N";
                             */

                            d = new OBCTreeData();
                            d.setAp(j + delimeter + "SC" + delimeter + ls1.getString("SDESC") + delimeter + v_types + delimeter + "SC" + delimeter
                                    + v_lesson + "_" + v_oid);
                            d.setAh(v_module + "," + v_lesson + "," + v_oid + ",SC,SC," + ls1.getString("FILETYPE") + "," + ls1.getInt("NPAGE")
                                    + "," + f_exist + "," + ls1.getString("STARTING") + ",NOT");

                            list1.add(d);
                            j++;
                        }
                    } // end if f_go == true
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
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

        return list1;
    }

    /**
     * [OBC] 마스터폼 맛보기 Tree Data 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList OBCTreeData리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectTreeDataListPreview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls1 = null;

        ArrayList list1 = null;
        String sql = "";
        OBCTreeData d = null;
        int j = 1;

        String s_subj = box.getSession("s_subj");
        // String s_year = box.getSession("s_year");
        // String s_subjseq = box.getSession("s_subjseq");
        // String s_userid = box.getSession("userid");
        // String p_tmp1 = box.getString("p_tmp1");
        // String p_ispreview = box.getString("p_ispreview");

        // int p_branch    = 0;
        // p_branch = box.getInt("p_branch");
        // String  f_branchsubj= "", v_lesson_brname= "";
        // int v_mybranch  = EduEtc1Bean.get_mybranch(s_subj, s_year, s_subjseq, s_userid);
        // int v_curbranch = 99;

        String v_module = "", v_lesson = "", v_oid = "";
        String f_exist = "N", v_types = "1001";
        // String v_extype = "", v_tmp = "", v_ptypenm = "", v_brtxt = "";
        boolean f_go = true;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "\r\n  SELECT /* EduStartBean.SelectTreeDataListPreview:2604 */                     "
                    + "\r\n        a.module module, a.sdesc modulenm, b.lesson lesson, b.sdesc lessonnm,  "
                    + "\r\n           ISNULL (b.TYPES, '1001') TYPES, ISNULL (a.TYPES, '1001') mtypes,    "
                    + "\r\n           ISNULL (b.isbranch, 'N') isbranch                                   "
                    + "\r\n  FROM tz_subjmodule a left outer join tz_subjlesson b                         "
                    + "\r\n       on a.module = b.module and a.subj = b.subj,                           "
                    + "\r\n       (SELECT distinct a.subj, a.module, a.lesson                             "
                    + "\r\n        FROM tz_subjobj a, tz_previewobj b                                     "
                    + "\r\n        WHERE a.subj = b.subj AND a.OID = b.OID AND a.subj = " + StringManager.makeSQL(s_subj) + ") c "
                    + "\r\n  WHERE 1 = 1                                                                  " + "\r\n    AND a.subj = "
                    + StringManager.makeSQL(s_subj) + "\r\n       AND b.subj = c.subj                                                     "
                    + "\r\n       AND b.module = c.module                                                 "
                    + "\r\n       AND b.lesson = c.lesson                                                 "
                    + "\r\n  ORDER BY a.module, b.lesson                                                  ";

            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            ls = connMgr.executeQuery(sql);

            while (ls.next()) { // ##1
                if (!ls.getString("lesson").equals("")) {
                    String delimeter = "||";

                    if (!ls.getString("module").equals(v_module)) {
                        v_module = ls.getString("module");

                        d = new OBCTreeData();
                        // Paramter Rule = Index||유형(MODULE,LESSON,SC)||목차명||Type(1001,1002,1003)||Mode(SC,TM,TT)||진도여부
                        d.setAp(j + delimeter + "MODULE" + delimeter + ls.getString("modulenm") + delimeter + ls.getString("MTYPES") + delimeter
                                + "SC" + delimeter);
                        d.setAh(v_module + "," + ls.getString("lesson") + ",MODULE1234,MO,SC,MODULE,0,N,XXX");

                        list1.add(d);
                        j++;
                    }

                    f_go = true; // Tree 출력 허용

                    if (!ls.getString("lesson").equals(v_lesson)) {
                        v_lesson = ls.getString("lesson");

                        d = new OBCTreeData();
                        d.setAp(j + delimeter + "LESSON" + delimeter + ls.getString("lessonnm") + delimeter + ls.getString("TYPES") + delimeter
                                + "SC" + delimeter);
                        d.setAh(v_module + "," + v_lesson + ",LESSON1234,LE,SC,LESSON,0,N,XXX");

                        list1.add(d);
                        j++;
                    }

                    if (f_go) {

                        sql = "\r\n  SELECT                                                               "
                                + "\r\n        a.TYPE TYPE, a.OID OID, a.sdesc sdesc, a.ordering ordering,    "
                                + "\r\n           b.starting starting, ISNULL (a.TYPES, '1001') TYPES,           "
                                + "\r\n           b.filetype filetype, b.npage npage                          "
                                + "\r\n  FROM tz_subjobj a, tz_object b                                       "
                                + "\r\n  WHERE 1 = 1                                                          " + "\r\n    AND a.subj = "
                                + StringManager.makeSQL(s_subj) + "\r\n       AND a.OID = b.OID                                               "
                                + "\r\n       AND a.module = " + StringManager.makeSQL(v_module) + "\r\n       AND lesson = "
                                + StringManager.makeSQL(v_lesson) + "\r\n       AND TYPE IN ('SC', 'TM', 'TT')                                  "
                                + "\r\n  ORDER BY ordering ASC                                                ";

                        if (ls1 != null) {
                            try {
                                ls1.close();
                            } catch (Exception e) {
                            }
                        }
                        ls1 = connMgr.executeQuery(sql);

                        while (ls1.next()) {
                            v_oid = ls1.getString("OID");
                            v_types = ls1.getString("TYPES");

                            d = new OBCTreeData();
                            d.setAp(j + delimeter + "SC" + delimeter + ls1.getString("SDESC") + delimeter + v_types + delimeter + "SC" + delimeter);
                            d.setAh(v_module + "," + v_lesson + "," + v_oid + ",SC,SC," + ls1.getString("FILETYPE") + "," + ls1.getInt("NPAGE")
                                    + "," + f_exist + "," + ls1.getString("STARTING") + ",NOT");

                            list1.add(d);
                            j++;
                        }
                    } // end if f_go == true
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
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

        return list1;
    }

    /**
     * 진도정보를 가져온다
     *
     * @param box receive from the form object and session
     * @return Map
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map selectProgressData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        String sql = "";
        Map resultMap = new HashMap();

        try {
            connMgr = new DBConnectionManager();

            String s_subj = box.getSession("s_subj");
            String s_year = box.getSession("s_year");
            String s_subjseq = box.getSession("s_subjseq");
            String s_userid = box.getSession("userid");

            sql = "\n  SELECT lesson, oid, lesson_count FROM tz_progress" + "\n  WHERE 1 = 1 " + "\n      AND subj = " + SQLString.Format(s_subj)
                    + "\n      AND year = " + SQLString.Format(s_year) + "\n      AND subjseq = " + SQLString.Format(s_subjseq)
                    + "\n      AND LESSON_COUNT > 0 " + "\n      AND userid = " + SQLString.Format(s_userid);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                resultMap.put(ls.getString("lesson") + "_" + ls.getString("oid"), ls.getString("lesson_count"));
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return resultMap;
    }

    /**
     * 과정명 가져오기
     *
     * @param box receive from the form object and session
     * @return Stringt
     * @throws Exception
     */
    public String selectSubjnm(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;

        String sql = "";
        String subjnm = "";

        try {
            connMgr = new DBConnectionManager();

            String s_subj = box.getSession("s_subj");

            sql = "SELECT subjnm FROM tz_subj WHERE subj = " + StringManager.makeSQL(s_subj);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                subjnm = ls.getString("subjnm");
            }
            ls.close();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
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

        return subjnm;
    }

    /**
     * 방송통신심의위원회 회원이 맞는지 userid를 체크해준다.
     *
     * @param box receive from the form object and session
     * @return ArrayList Lesson리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectKocscUseridCheck(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList list1 = null;

        StringBuffer sql = new StringBuffer();

        MfLessonData data = null;

        String v_userid = box.getString("p_userid");

        try {

            connMgr = new DBConnectionManager();

            list1 = new ArrayList();

            sql.append(" SELECT USERID, NAME, GRCODE \n");
            sql.append(" FROM TZ_MEMBER \n");
            sql.append(" WHERE GRCODE = 'N000041'    \n");
            sql.append("   AND USERID = " + StringManager.makeSQL(v_userid) + " \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {

                data = new MfLessonData();

                data.setModule(ls.getString("USERID"));
                data.setLesson(ls.getString("NAME"));
                data.setSdesc(ls.getString("GRCODE"));

                list1.add(data);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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

        return list1;
    }

    /**
     * 방송통신심의위원회 회원이 맞는지 userid를 체크해준다.
     *
     * @param box receive from the form object and session
     * @return ArrayList Lesson리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectKocscUseridCheck2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList list1 = null;

        StringBuffer sql = new StringBuffer();

        MfLessonData data = null;

        String v_userid = box.getString("p_userid");

        try {

            connMgr = new DBConnectionManager();

            list1 = new ArrayList();

            sql.append(" SELECT USERID, NAME, GRCODE \n");
            sql.append(" FROM TZ_MEMBER \n");
            sql.append(" WHERE GRCODE = 'N000001'    \n");
            sql.append("   AND USERID = " + StringManager.makeSQL(v_userid) + " \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {

                data = new MfLessonData();

                data.setModule(ls.getString("USERID"));
                data.setLesson(ls.getString("NAME"));
                data.setSdesc(ls.getString("GRCODE"));

                list1.add(data);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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

        return list1;
    }

    /**
     * 과정 페이지 수강 이력 저장
     *
     * @param box receive from the form object and session
     * @return is_Ok 1 : login ok 2 : login fail
     * @throws Exception
     */
    public int saveSubjseqPageClassInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ListSet ls = null;
        int is_Ok = 0;

        String p_userid = box.getSession("userid");
        String p_subj = box.getSession("s_subj");
        String p_year = box.getSession("s_year");
        String p_subjseq = box.getSession("s_subjseq");
        String p_grcode = box.getSession("tem_grcode");
        String p_currentTime = box.getString("ct");
        String p_totalTime = box.getString("tt");
        String p_currentChapter = box.getString("cc");
        String p_currentPage = box.getString("cp");
        String p_totalPage = box.getString("tp");
        String p_nextPage = box.getString("np");
        String saveChk = "N";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "\n  /* com.credu.contents.EduStartBean() saveSubjseqPageClassInfo (과정 페이지 수강 정보 조회) */ ";
            sql += "\n select c_time, t_time   ";
            sql += "\n   from tz_subj_play_chk ";
            sql += "\n  where userid     = " + StringManager.makeSQL(p_userid);
            sql += "\n    and grcode     = " + StringManager.makeSQL(p_grcode);
            sql += "\n    and subj       = " + StringManager.makeSQL(p_subj);
            sql += "\n    and year       = " + StringManager.makeSQL(p_year);
            sql += "\n    and subjseq    = " + StringManager.makeSQL(p_subjseq);
            sql += "\n    and chapter_no = " + StringManager.makeSQL(p_currentChapter);
            sql += "\n    and c_page     = " + StringManager.makeSQL(p_currentPage);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                if (ls.getDouble("c_time") < ls.getDouble("t_time")) {
                    saveChk = "Y";

                    sql = "\n  /* com.credu.contents.EduStartBean() saveSubjseqPageClassInfo (과정 페이지 수강 이력 수정) */ ";
                    sql += "\n update tz_subj_play_chk                                      ";
                    sql += "\n    set c_time     = ?                                        ";
                    sql += "\n      , u_date     = to_char(sysdate,'YYYYMMDDHH24MISS')      ";
                    sql += "\n  where userid     = ?                                        ";
                    sql += "\n    and grcode     = ?                                        ";
                    sql += "\n    and subj       = ?                                        ";
                    sql += "\n    and year       = ?                                        ";
                    sql += "\n    and subjseq    = ?                                        ";
                    sql += "\n    and chapter_no = ?                                        ";
                    sql += "\n    and c_page     = ?                                        ";

                    pstmt = connMgr.prepareStatement(sql);

                    pstmt.setString(1, p_currentTime);
                    pstmt.setString(2, p_userid);
                    pstmt.setString(3, p_grcode);
                    pstmt.setString(4, p_subj);
                    pstmt.setString(5, p_year);
                    pstmt.setString(6, p_subjseq);
                    pstmt.setString(7, p_currentChapter);
                    pstmt.setString(8, p_currentPage);
                }
            } else {
                saveChk = "Y";

                sql = "\n  /* com.credu.contents.EduStartBean() saveSubjseqPageClassInfo (과정 페이지 수강 정보 저장) */ ";
                sql += "\n  insert into tz_subj_play_chk		     ";
                sql += "\n  (    userid                              ";
                sql += "\n  ,    grcode                              ";
                sql += "\n  ,    subj                                ";
                sql += "\n  ,    year                                ";
                sql += "\n  ,    subjseq                             ";
                sql += "\n  ,    chapter_no                          ";
                sql += "\n  ,    c_page                              ";
                sql += "\n  ,    t_page                              ";
                sql += "\n  ,    n_page                              ";
                sql += "\n  ,    c_time                              ";
                sql += "\n  ,    t_time                              ";
                sql += "\n  ,    c_date                              ";
                sql += "\n  )		    					         ";
                sql += "\n  values   						         ";
                sql += "\n  (    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    to_char(sysdate,'YYYYMMDDHH24MISS') ";
                sql += "\n  )										 ";

                pstmt = connMgr.prepareStatement(sql);

                pstmt.setString(1, p_userid);
                pstmt.setString(2, p_grcode);
                pstmt.setString(3, p_subj);
                pstmt.setString(4, p_year);
                pstmt.setString(5, p_subjseq);
                pstmt.setString(6, p_currentChapter);
                pstmt.setString(7, p_currentPage);
                pstmt.setString(8, p_totalPage);
                pstmt.setString(9, p_nextPage);
                pstmt.setString(10, p_currentTime);
                pstmt.setString(11, p_totalTime);
            }

            if ("Y".equals(saveChk)) {
                is_Ok = pstmt.executeUpdate();
            } else {
                is_Ok = 1;
            }

            ls.close();

            if (is_Ok == 1) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return is_Ok;
    }
    
    /**
     * 수강 페이지 정보 조회
     *
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectSubjPageInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        ArrayList<DataBox> list1 = new ArrayList<DataBox>();

        String p_userid = box.getSession("userid");
        String p_subj = box.getSession("s_subj");
        String p_year = box.getSession("s_year");
        String p_subjseq = box.getSession("s_subjseq");
        String p_grcode = box.getSession("tem_grcode");
        String p_chap = box.getString("chap");

        try {
            connMgr = new DBConnectionManager();

            sql += "\n /* com.credu.contents.EduStartBean() selectSubjPageInfo (수강 페이지 정보 조회) */ ";
            sql += "\n    select a.c_page                                   ";
            sql += "\n         , a.t_page                                   ";
            sql += "\n         , a.n_page                                   ";
            sql += "\n         , a.c_time                                   ";
            sql += "\n         , a.t_time                                   ";
            sql += "\n         , (select c.page_chk_yn                      ";
            sql += "\n              from tz_subjseq c                       ";
            sql += "\n             where c.subj    = a.subj                 ";
            sql += "\n               and c.year    = a.year                 ";
            sql += "\n               and c.subjseq = a.subjseq              ";
            sql += "\n               and c.grcode  = a.grcode) pagechkyn    ";
            sql += "\n         , case when ((a.c_page = a.t_page) and (a.c_time = a.t_time)) then 'Y' else 'N' end finalpagechkyn ";
            sql += "\n         , case when a.c_time = a.t_time then 'Y' else 'N' end nextpagechkyn    ";
            sql += "\n         , (select max(x.c_page) + 1                            ";
            sql += "\n              from tz_subj_play_chk x                           ";
            sql += "\n             where x.userid     = a.userid                      ";
            sql += "\n               and x.grcode     = a.grcode                      ";
            sql += "\n               and x.subj       = a.subj                        ";
            sql += "\n               and x.subjseq    = a.subjseq                     ";
            sql += "\n               and x.year       = a.year                        ";
            sql += "\n               and x.chapter_no = a.chapter_no) lastChkPage     ";
            sql += "\n      from tz_subj_play_chk a                                   ";
            sql += "\n     where a.userid     = " + SQLString.Format(p_userid)         ;
            sql += "\n       and a.grcode     = " + SQLString.Format(p_grcode)         ;
            sql += "\n       and a.subj       = " + SQLString.Format(p_subj)           ;
            sql += "\n       and a.subjseq    = " + SQLString.Format(p_subjseq)        ;
            sql += "\n       and a.year       = " + SQLString.Format(p_year)           ;
            sql += "\n       and a.chapter_no = " + SQLString.Format(p_chap)           ;
            sql += "\n       and a.c_page     = (select max(b.c_page)                 ";
            sql += "\n                             from tz_subj_play_chk b            ";
            sql += "\n                            where b.userid     = a.userid       ";
            sql += "\n                              and b.grcode     = a.grcode       ";
            sql += "\n                              and b.subj       = a.subj         ";
            sql += "\n                              and b.subjseq    = a.subjseq      ";
            sql += "\n                              and b.year       = a.year         ";
            sql += "\n                              and b.chapter_no = a.chapter_no)  ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
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
        return list1;
    }

    /**
     * 수강 페이지 콘트롤 제어 여부 조회
     *
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectPageControlChk(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        ArrayList<DataBox> list1 = new ArrayList<DataBox>();

        String p_userid = box.getSession("userid");
        String p_subj = box.getSession("s_subj");
        String p_year = box.getSession("s_year");
        String p_subjseq = box.getSession("s_subjseq");
        String p_grcode = box.getSession("tem_grcode");
        String p_chap = box.getString("chap");
        String p_cp = box.getString("cp");

        try {
            connMgr = new DBConnectionManager();

            sql += "\n /* com.credu.contents.EduStartBean() selectPageControlChk (수강 페이지 콘트롤 제어 여부 조회) */              ";
            sql += "\n select a.c_page                                                                                          ";
            sql += "\n      , a.t_page                                                                                          ";
            sql += "\n      , a.n_page                                                                                          ";
            sql += "\n      , a.c_time                                                                                          ";
            sql += "\n      , a.t_time                                                                                          ";
            sql += "\n      , c.page_chk_yn                                                                                     ";
            sql += "\n      , case when a.c_time = a.t_time then 'Y' else 'N' end nextPageChkYn                                 ";
            sql += "\n      , case when ((b.c_page = a.t_page) and (b.c_time = b.t_time)) then 'Y' else 'N' end finishPageYn    ";
            sql += "\n      , (select max(x.c_page) + 1                                                                         ";
            sql += "\n           from tz_subj_play_chk x                                                                        ";
            sql += "\n          where x.userid     = a.userid                                                                   ";
            sql += "\n            and x.grcode     = a.grcode                                                                   ";
            sql += "\n            and x.subj       = a.subj                                                                     ";
            sql += "\n            and x.subjseq    = a.subjseq                                                                  ";
            sql += "\n            and x.year       = a.year                                                                     ";
            sql += "\n            and x.chapter_no = a.chapter_no) lastChkPage                                                  ";
            sql += "\n   from tz_subj_play_chk a                                                                                ";
            sql += "\n   left join tz_subj_play_chk b                                                                           ";
            sql += "\n          on b.userid     = a.userid                                                                      ";
            sql += "\n         and b.grcode     = a.grcode                                                                      ";
            sql += "\n         and b.subj       = a.subj                                                                        ";
            sql += "\n         and b.subjseq    = a.subjseq                                                                     ";
            sql += "\n         and b.year       = a.year                                                                        ";
            sql += "\n         and b.chapter_no = a.chapter_no                                                                  ";
            sql += "\n         and b.c_page     = a.t_page                                                                      ";
            sql += "\n   left join tz_subjseq c                                                                                 ";
            sql += "\n          on c.subj    = a.subj                                                                           ";
            sql += "\n         and c.year    = a.year                                                                           ";
            sql += "\n         and c.subjseq = a.subjseq                                                                        ";
            sql += "\n         and c.grcode  = a.grcode                                                                         ";
            sql += "\n  where a.userid     = " + SQLString.Format(p_userid)                                                      ;
            sql += "\n    and a.grcode     = " + SQLString.Format(p_grcode)                                                      ;
            sql += "\n    and a.subj       = " + SQLString.Format(p_subj)                                                        ;
            sql += "\n    and a.subjseq    = " + SQLString.Format(p_subjseq)                                                     ;
            sql += "\n    and a.year       = " + SQLString.Format(p_year)                                                        ;
            sql += "\n    and a.chapter_no = " + SQLString.Format(p_chap)                                                        ;
            sql += "\n    and a.c_page     = " + SQLString.Format(p_cp)                                                          ;

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
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
        return list1;
    }

    /**
     * 수강 페이지 제어 여부
     *
     * @param box receive from the form object and session
     * @return String pageChkYn
     */
    public String subjSeqPageChk(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        String result = "";
        String p_userid = box.getSession("userid");
        String p_subj = box.getSession("s_subj");
        String p_year = box.getSession("s_year");
        String p_subjseq = box.getSession("s_subjseq");
        String p_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();

            sql.append(" /* com.credu.contents.EduStartBean() subjSeqPageChk (수강 페이지 제어 여부) */ \n");
            sql.append(" select page_chk_yn \n");
            sql.append("   from tz_subjseq  \n");
            sql.append("  where subj    = '").append(p_subj).append("'      \n");
            sql.append("    and subjseq = '").append(p_subjseq).append("'   \n");
            sql.append("    and year    = '").append(p_year).append("'      \n");
            sql.append("    and grcode  = '").append(p_grcode).append("'    \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                result = ls.getString("page_chk_yn");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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
     * 수강 차시 완료 여부
     *
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectLessonCompleteChk(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        ArrayList<DataBox> list1 = new ArrayList<DataBox>();

        String p_userid = box.getSession("userid");
        String p_subj = box.getSession("s_subj");
        String p_year = box.getSession("s_year");
        String p_subjseq = box.getSession("s_subjseq");
        String p_grcode = box.getSession("tem_grcode");
        String p_lesson = box.getString("p_lesson");

        try {
            connMgr = new DBConnectionManager();

            sql += "\n /* com.credu.contents.EduStartBean() selectLessonCompleteChk (수강 차시 완료 여부) */       ";
            sql += "\n    select m.subj                                                                         ";
            sql += "\n         , m.lesson                                                                       ";
            sql += "\n         , m.sdesc                                                                        ";
            sql += "\n         , m.finalYn                                                                      ";
            sql += "\n      from (                                                                              ";
            sql += "\n              select x.subj                                                               ";
            sql += "\n                   , x.lesson                                                             ";
            sql += "\n                   , x.finalYn                                                            ";
            sql += "\n                   , x.sdesc                                                              ";
            sql += "\n                   , row_number() over (partition by x.subj order by x.lesson) rnum       ";
            sql += "\n                from (                                                                    ";
            sql += "\n                        select a.subj                                                     ";
            sql += "\n                             , a.lesson                                                   ";
            sql += "\n                             , a.sdesc                                                    ";
            sql += "\n                             , case when count(b.subj) > 0 then 'Y' else 'N' end  finalYn ";
            sql += "\n                          from tz_subjlesson a                                            ";
            sql += "\n                          left join tz_subj_play_chk b                                    ";
            sql += "\n                                 on a.subj    = b.subj                                    ";
            sql += "\n                                and a.lesson  = b.chapter_no                              ";
            sql += "\n                                and b.userid  = " + SQLString.Format(p_userid)             ;
            sql += "\n                                and b.grcode  = " + SQLString.Format(p_grcode)             ;
            sql += "\n                                and b.subjseq = " + SQLString.Format(p_subjseq)            ;
            sql += "\n                                and b.year    = " + SQLString.Format(p_year)               ;
            sql += "\n                                and b.c_page  = t_page                                    ";
            sql += "\n                                and b.c_time  = b.t_time                                  ";
            sql += "\n                         where a.subj   = " + SQLString.Format(p_subj)                     ;
            sql += "\n                           and a.lesson < " + SQLString.Format(p_lesson)                   ;
            sql += "\n                         group by a.subj                                                  ";
            sql += "\n                             , a.lesson                                                   ";
            sql += "\n                             , a.sdesc                                                    ";
            sql += "\n                     ) x                                                                  ";
            sql += "\n                 where x.finalYn = 'N'                                                    ";
            sql += "\n                 order by x.lesson                                                        ";
            sql += "\n           ) m                                                                            ";
            sql += "\n       where m.rnum = 1                                                                   ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
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
        return list1;
    }

    /**
     * B2C 과정 페이지 수강 이력 저장
     *
     * @param box receive from the form object and session
     * @return is_Ok 1 : login ok 2 : login fail
     * @throws Exception
     */
    public int subjseqPageSearchInput(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ListSet ls = null;
        int is_Ok = 0;
        int cTime = 0;

        String p_userid = box.getSession("userid");
        String p_subj = box.getSession("s_subj");
        String p_year = box.getSession("s_year");
        String p_subjseq = box.getSession("s_subjseq");
        String p_grcode = box.getSession("tem_grcode");
        String p_currentTime = box.getString("ct");
        String p_totalTime = box.getString("tt");
        String p_currentChapter = box.getString("cc");
        String p_currentPage = box.getString("cp");
        String p_totalPage = box.getString("tp");
        String p_nextPage = box.getString("np");
        String saveChk = "N";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "\n  /* com.credu.contents.EduStartBean() subjseqPageSearchInput (B2C 과정 페이지 수강 정보 조회) */ ";
            sql += "\n select c_time, t_time   ";
            sql += "\n   from tz_subj_play_chk ";
            sql += "\n  where userid     = " + StringManager.makeSQL(p_userid);
            sql += "\n    and grcode     = " + StringManager.makeSQL(p_grcode);
            sql += "\n    and subj       = " + StringManager.makeSQL(p_subj);
            sql += "\n    and year       = " + StringManager.makeSQL(p_year);
            sql += "\n    and subjseq    = " + StringManager.makeSQL(p_subjseq);
            sql += "\n    and chapter_no = " + StringManager.makeSQL(p_currentChapter);
            sql += "\n    and c_page     = " + StringManager.makeSQL(p_currentPage);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                cTime = ls.getInt("c_time");
            } else {
                saveChk = "Y";

                sql = "\n  /* com.credu.contents.EduStartBean() subjseqPageSearchInput (B2C 과정 페이지 수강 정보 저장) */ ";
                sql += "\n  insert into tz_subj_play_chk		     ";
                sql += "\n  (    userid                              ";
                sql += "\n  ,    grcode                              ";
                sql += "\n  ,    subj                                ";
                sql += "\n  ,    year                                ";
                sql += "\n  ,    subjseq                             ";
                sql += "\n  ,    chapter_no                          ";
                sql += "\n  ,    c_page                              ";
                sql += "\n  ,    t_page                              ";
                sql += "\n  ,    n_page                              ";
                sql += "\n  ,    c_time                              ";
                sql += "\n  ,    t_time                              ";
                sql += "\n  ,    c_date                              ";
                sql += "\n  )		    					         ";
                sql += "\n  values   						         ";
                sql += "\n  (    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    to_char(sysdate,'YYYYMMDDHH24MISS') ";
                sql += "\n  )										 ";

                pstmt = connMgr.prepareStatement(sql);

                pstmt.setString(1, p_userid);
                pstmt.setString(2, p_grcode);
                pstmt.setString(3, p_subj);
                pstmt.setString(4, p_year);
                pstmt.setString(5, p_subjseq);
                pstmt.setString(6, p_currentChapter);
                pstmt.setString(7, p_currentPage);
                pstmt.setString(8, p_totalPage);
                pstmt.setString(9, p_nextPage);
                pstmt.setString(10, p_currentTime);
                pstmt.setString(11, p_totalTime);
            }

            if ("Y".equals(saveChk)) {
                is_Ok = pstmt.executeUpdate();
            } else {
                is_Ok = 1;
            }

            ls.close();

            if (is_Ok == 1) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return cTime;
    }

    /**
     * B2C 과정 페이지 수강 시간 저장
     *
     * @param box receive from the form object and session
     * @return is_Ok 1 : login ok 2 : login fail
     * @throws Exception
     */
    public int subjseqPageUpdate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ListSet ls = null;
        int is_Ok = 0;

        String p_userid = box.getSession("userid");
        String p_subj = box.getSession("s_subj");
        String p_year = box.getSession("s_year");
        String p_subjseq = box.getSession("s_subjseq");
        String p_grcode = box.getSession("tem_grcode");
        String p_currentTime = box.getString("ct");
        String p_totalTime = box.getString("tt");
        String p_currentChapter = box.getString("cc");
        String p_currentPage = box.getString("cp");
        String p_totalPage = box.getString("tp");
        String p_nextPage = box.getString("np");
        String saveChk = "N";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "\n  /* com.credu.contents.EduStartBean() saveSubjseqPageClassInfo (과정 페이지 수강 정보 조회) */ ";
            sql += "\n select c_time, t_time   ";
            sql += "\n   from tz_subj_play_chk ";
            sql += "\n  where userid     = " + StringManager.makeSQL(p_userid);
            sql += "\n    and grcode     = " + StringManager.makeSQL(p_grcode);
            sql += "\n    and subj       = " + StringManager.makeSQL(p_subj);
            sql += "\n    and year       = " + StringManager.makeSQL(p_year);
            sql += "\n    and subjseq    = " + StringManager.makeSQL(p_subjseq);
            sql += "\n    and chapter_no = " + StringManager.makeSQL(p_currentChapter);
            sql += "\n    and c_page     = " + StringManager.makeSQL(p_currentPage);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                if (ls.getDouble("c_time") < ls.getDouble("t_time")) {
                    saveChk = "Y";

                    sql = "\n  /* com.credu.contents.EduStartBean() saveSubjseqPageClassInfo (과정 페이지 수강 이력 수정) */ ";
                    sql += "\n update tz_subj_play_chk                                      ";
                    sql += "\n    set c_time     = ?                                        ";
                    sql += "\n      , u_date     = to_char(sysdate,'YYYYMMDDHH24MISS')      ";
                    sql += "\n  where userid     = ?                                        ";
                    sql += "\n    and grcode     = ?                                        ";
                    sql += "\n    and subj       = ?                                        ";
                    sql += "\n    and year       = ?                                        ";
                    sql += "\n    and subjseq    = ?                                        ";
                    sql += "\n    and chapter_no = ?                                        ";
                    sql += "\n    and c_page     = ?                                        ";

                    pstmt = connMgr.prepareStatement(sql);

                    pstmt.setString(1, p_currentTime);
                    pstmt.setString(2, p_userid);
                    pstmt.setString(3, p_grcode);
                    pstmt.setString(4, p_subj);
                    pstmt.setString(5, p_year);
                    pstmt.setString(6, p_subjseq);
                    pstmt.setString(7, p_currentChapter);
                    pstmt.setString(8, p_currentPage);
                }
            } else {
                saveChk = "Y";

                sql = "\n  /* com.credu.contents.EduStartBean() saveSubjseqPageClassInfo (과정 페이지 수강 정보 저장) */ ";
                sql += "\n  insert into tz_subj_play_chk		     ";
                sql += "\n  (    userid                              ";
                sql += "\n  ,    grcode                              ";
                sql += "\n  ,    subj                                ";
                sql += "\n  ,    year                                ";
                sql += "\n  ,    subjseq                             ";
                sql += "\n  ,    chapter_no                          ";
                sql += "\n  ,    c_page                              ";
                sql += "\n  ,    t_page                              ";
                sql += "\n  ,    n_page                              ";
                sql += "\n  ,    c_time                              ";
                sql += "\n  ,    t_time                              ";
                sql += "\n  ,    c_date                              ";
                sql += "\n  )		    					         ";
                sql += "\n  values   						         ";
                sql += "\n  (    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    ?                                   ";
                sql += "\n  ,    to_char(sysdate,'YYYYMMDDHH24MISS') ";
                sql += "\n  )										 ";

                pstmt = connMgr.prepareStatement(sql);

                pstmt.setString(1, p_userid);
                pstmt.setString(2, p_grcode);
                pstmt.setString(3, p_subj);
                pstmt.setString(4, p_year);
                pstmt.setString(5, p_subjseq);
                pstmt.setString(6, p_currentChapter);
                pstmt.setString(7, p_currentPage);
                pstmt.setString(8, p_totalPage);
                pstmt.setString(9, p_nextPage);
                pstmt.setString(10, p_currentTime);
                pstmt.setString(11, p_totalTime);
            }

            if ("Y".equals(saveChk)) {
                is_Ok = pstmt.executeUpdate();
            } else {
                is_Ok = 1;
            }

            ls.close();

            if (is_Ok == 1) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return is_Ok;
    }
    
}
