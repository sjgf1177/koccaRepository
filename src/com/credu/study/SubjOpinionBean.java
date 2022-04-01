//**********************************************************
//  1. 제      목: 과정 콘텐츠 의견달기 DATA
//  2. 프로그램명: SubjOpinionBean.java
//  3. 개      요: 과정 콘텐츠 의견달기 bean
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2011.02.28
//  7. 수      정:
//**********************************************************
package com.credu.study;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */

public class SubjOpinionBean {
    private ConfigSet config;
    private int row;

    public SubjOpinionBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 학습창 의견달기 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSubjMyOpinionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;

        // 수정일 : 05.11.22 수정자 : 이나연 _ totalcount 하기위한 쿼리
        String sql1 = "";
        String head_sql1 = "";
        String body_sql1 = "";
        // String group_sql1 = "";
        String order_sql1 = "";
        String count_sql1 = "";

        DataBox dbox = null;

        String v_grcode = box.getSession("tem_grcode");
        String v_userid = box.getSession("userid");

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getString("p_lesson");
        String v_pageno = box.getString("p_pageno");
        String v_opinseq = box.getString("p_opinseq");
        // String v_question = box.getString("p_question");

        int v_viewpageno = box.getInt("p_viewpageno");
        if (v_viewpageno == 0)
            v_viewpageno = 1;

        head_sql1 = " select b.grcode, b.subj, b.year, b.subjseq, b.lesson, b.userid, a.name, b.pageno, b.opinseq, b.question, b.contents, b.cnt, b.indate, b.ldate ";
        body_sql1 += "   from TZ_MEMBER a inner join TZ_OPINION b  ";
        body_sql1 += "     on a.grcode=b.grcode and a.userid = b.userid ";
        body_sql1 += "  where b.subj = '" + v_subj + "' and b.year = '" + v_year + "' and b.subjseq='" + v_subjseq + "' ";
        body_sql1 += "    and b.lesson = '" + v_lesson + "' and b.userid = '" + v_userid + "' and b.pageno='" + v_pageno + "' and b.opinseq='" + v_opinseq + "' ";
        body_sql1 += "    and b.grcode = '" + v_grcode + "' ";

        sql1 = head_sql1 + body_sql1 + order_sql1;

        count_sql1 = " select count(*) " + body_sql1;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            ls1 = connMgr.executeQuery(sql1);

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1); //     전체 row 수를 반환한다

            ls1.setPageSize(10); //  페이지당 row 갯수를 세팅한다
            ls1.setCurrentPage(v_viewpageno, total_row_count); //     현재페이지번호를 세팅한다.
            // int totalpagecount = ls1.getTotalPage(); //     전체 페이지 수를 반환한다

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                list1.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
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
     * 학습창 의견달기 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSubjOpinionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;

        // 수정일 : 05.11.22 수정자 : 이나연 _ totalcount 하기위한 쿼리
        String sql1 = "";
        String head_sql1 = "";
        String body_sql1 = "";
        String group_sql1 = "";
        String order_sql1 = "";
        String count_sql1 = "";

        DataBox dbox = null;

        String v_grcode = box.getSession("tem_grcode");
        String v_userid = box.getSession("userid");

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getString("p_lesson");
        String v_pageno = box.getString("p_pageno");
        String v_opinseq = box.getString("p_opinseq");

        int v_viewpageno = box.getInt("p_viewpageno");
        if (v_viewpageno == 0)
            v_viewpageno = 1;

        head_sql1 = " select b.grcode, b.subj, b.year, b.subjseq, b.lesson, b.userid, a.name, b.pageno, b.opinseq, b.question, b.contents, b.cnt, b.indate, b.ldate ";
        body_sql1 += "   from TZ_MEMBER a inner join TZ_OPINION b                                                  ";
        body_sql1 += "     on a.grcode=b.grcode and a.userid = b.userid ";
        body_sql1 += "  where b.subj = '" + v_subj + "' and b.year = '" + v_year + "' and b.subjseq='" + v_subjseq + "' ";
        body_sql1 += "    and b.lesson = '" + v_lesson + "' and b.pageno='" + v_pageno + "' and b.opinseq='" + v_opinseq + "' ";
        body_sql1 += "    and b.grcode = '" + v_grcode + "' ";
        body_sql1 += "    and b.userid <> '" + v_userid + "' ";
        order_sql1 += "  order by b.indate desc, a.name ";
        sql1 = head_sql1 + body_sql1 + group_sql1 + order_sql1;

        count_sql1 = " select count(*) " + body_sql1;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            ls1 = connMgr.executeQuery(sql1);

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1); //     전체 row 수를 반환한다

            ls1.setPageSize(row); //  페이지당 row 갯수를 세팅한다
            ls1.setCurrentPage(v_viewpageno, total_row_count); //     현재페이지번호를 세팅한다.
            int totalpagecount = ls1.getTotalPage(); //     전체 페이지 수를 반환한다

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(totalpagecount));
                dbox.put("d_rowcount", new Integer(10));

                list1.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
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
     * 질문 상세조회 답변도 나옴
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSubjOpinionDetail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        DataBox dbox = null;
        String v_upcnt = "Y";

        String sql1 = "";
        String v_grcode = box.getSession("tem_grcode");
        String v_userid = box.getString("p_userid");

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getString("p_lesson");
        String v_pageno = box.getString("p_pageno");
        String v_opinseq = box.getString("p_opinseq");

        if (v_lesson.equals(""))
            v_lesson = "000";

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = "select b.grcode, b.subj, b.year, b.subjseq, b.lesson, b.userid, a.name, b.pageno, b.opinseq, b.question, b.contents, b.cnt, b.indate, b.ldate ";
            sql1 += " from TZ_MEMBER a inner join TZ_OPINION b ";
            sql1 += "   on a.grcode=b.grcode and a.userid = b.userid ";
            sql1 += " where b.subj = '" + v_subj + "' and b.year = '" + v_year + "' and b.subjseq='" + v_subjseq + "' ";
            sql1 += "    and b.lesson = '" + v_lesson + "' and b.pageno='" + v_pageno + "' and b.opinseq='" + v_opinseq + "' ";
            sql1 += "    and b.grcode = '" + v_grcode + "' ";
            sql1 += "    and b.userid = '" + v_userid + "' ";
            sql1 += "  order by b.indate desc, a.name ";

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }

            if (!v_upcnt.equals("N")) {
                String updateSql = " update TZ_OPINION set cnt = cnt+1 where grcode = " + StringManager.makeSQL(v_grcode) + " and subj= " + StringManager.makeSQL(v_subj) + " and year= " + StringManager.makeSQL(v_year) + " and subjseq= "
                        + StringManager.makeSQL(v_subjseq);
                updateSql += " and lesson= " + StringManager.makeSQL(v_lesson) + " and userid= " + StringManager.makeSQL(v_userid) + " and pageno= " + StringManager.makeSQL(v_pageno) + " and opinseq = " + StringManager.makeSQL(v_opinseq);

                connMgr.executeUpdate(updateSql);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
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
     * 학습자의견보기 상세조회(수정시)
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public DataBox selectSubjOpinionDetail2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        DataBox dbox = null;
        ArrayList<DataBox> list1 = null;

        String v_grcode = box.getSession("tem_grcode");
        String v_userid = box.getSession("userid");

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getString("p_lesson");
        String v_pageno = box.getString("p_pageno");
        String v_opinseq = box.getString("p_opinseq");

        sql1 = "select b.grcode, b.subj, b.year, b.subjseq, b.lesson, b.userid, a.name, b.pageno, b.opinseq, b.question, b.contents, b.cnt, b.indate, b.ldate ";
        sql1 += " from TZ_MEMBER a inner join TZ_OPINION b ";
        sql1 += "   on a.grcode=b.grcode and a.userid = b.userid ";
        sql1 += " where b.subj = '" + v_subj + "' and b.year = '" + v_year + "' and b.subjseq='" + v_subjseq + "' ";
        sql1 += "    and b.lesson = '" + v_lesson + "' and b.pageno='" + v_pageno + "' and b.opinseq='" + v_opinseq + "' ";
        sql1 += "    and b.grcode = '" + v_grcode + "' ";
        sql1 += "    and b.userid = '" + v_userid + "' ";

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {

                dbox = ls1.getDataBox();
                list1.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
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

        return dbox;
    }

    /**
     * 학습자의견 등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int opinionInsert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        int isOk = 0;

        String v_grcode = box.getSession("tem_grcode");
        String v_userid = box.getSession("userid");

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getString("p_lesson");
        String v_pageno = box.getString("p_pageno");
        String v_opinseq = box.getString("p_opinseq");
        String v_question = box.getString("p_question");
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));

        int i = 1;

        try {
            connMgr = new DBConnectionManager();

            sql1 = "select count(*) cnt";
            sql1 += " from TZ_MEMBER a inner join TZ_OPINION b ";
            sql1 += "   on a.grcode=b.grcode and a.userid = b.userid ";
            sql1 += " where b.subj = '" + v_subj + "' and b.year = '" + v_year + "' and b.subjseq='" + v_subjseq + "' ";
            sql1 += "    and b.lesson = '" + v_lesson + "' and b.pageno='" + v_pageno + "' and b.opinseq='" + v_opinseq + "' ";
            sql1 += "    and b.grcode = '" + v_grcode + "' ";
            sql1 += "    and b.userid = '" + v_userid + "' ";

            ls = connMgr.executeQuery(sql1);

            if (ls.next()) {
                if (ls.getInt(1) == 1)
                    isOk = -1;
            }

            if (isOk == 0) {

                sql1 = " insert into TZ_OPINION (grcode, subj, year,subjseq,lesson, userid, pageno, opinseq, question, contents, cnt, indate, ldate ) ";
                sql1 += " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, to_char(sysdate, 'YYYYMMDDHH24MISS'), to_char(sysdate, 'YYYYMMDDHH24MISS') )";
                pstmt2 = connMgr.prepareStatement(sql1);

                pstmt2.setString(i++, v_grcode);
                pstmt2.setString(i++, v_subj);
                pstmt2.setString(i++, v_year);
                pstmt2.setString(i++, v_subjseq);
                pstmt2.setString(i++, v_lesson);
                pstmt2.setString(i++, v_userid);
                pstmt2.setString(i++, v_pageno);
                pstmt2.setString(i++, v_opinseq);
                pstmt2.setString(i++, v_question);
                pstmt2.setString(i++, v_contents);

                isOk = pstmt2.executeUpdate();
            }
        } catch (Exception ex) {

            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
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
     * Opinion 수정
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int updateOpinion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        int isOk = 0;

        String v_grcode = box.getSession("tem_grcode");
        String v_userid = box.getSession("userid");

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getStringDefault("p_lesson", "000");
        String v_pageno = box.getString("p_pageno");
        String v_opinseq = box.getString("p_opinseq");
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));

        int i = 1;

        try {
            connMgr = new DBConnectionManager();

            sql1 = " update TZ_OPINION set contents=?, ldate=to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql1 += " where grcode=? and subj=? and year=? and subjseq=? and lesson=? and userid=? and pageno=? and opinseq=? ";

            pstmt2 = connMgr.prepareStatement(sql1);

            pstmt2.setString(i++, v_contents);
            pstmt2.setString(i++, v_grcode);
            pstmt2.setString(i++, v_subj);
            pstmt2.setString(i++, v_year);
            pstmt2.setString(i++, v_subjseq);
            pstmt2.setString(i++, v_lesson);
            pstmt2.setString(i++, v_userid);
            pstmt2.setString(i++, v_pageno);
            pstmt2.setString(i++, v_opinseq);

            isOk = pstmt2.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
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
     * 삭제
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int deleteOpinion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";

        int isOk = 0;
        int i = 1;

        String v_grcode = box.getSession("tem_grcode");
        String v_userid = box.getSession("userid");

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getStringDefault("p_lesson", "000");
        String v_pageno = box.getString("p_pageno");
        String v_opinseq = box.getString("p_opinseq");

        try {
            connMgr = new DBConnectionManager();
            sql1 = " delete TZ_OPINION where grcode=? and subj=? and year=? and subjseq=? and lesson=? and userid=? and pageno=? and opinseq=? ";

            pstmt2 = connMgr.prepareStatement(sql1);

            pstmt2.setString(i++, v_grcode);
            pstmt2.setString(i++, v_subj);
            pstmt2.setString(i++, v_year);
            pstmt2.setString(i++, v_subjseq);
            pstmt2.setString(i++, v_lesson);
            pstmt2.setString(i++, v_userid);
            pstmt2.setString(i++, v_pageno);
            pstmt2.setString(i++, v_opinseq);

            isOk = pstmt2.executeUpdate();

        } catch (Exception ex) {
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
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

}
