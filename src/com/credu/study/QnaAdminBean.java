//**********************************************************
//  1. 제      목: QNA DATA
//  2. 프로그램명: QnaAdminBean.java
//  3. 개      요: 질문 admin bean
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 26
//  7. 수      정: 
//**********************************************************

package com.credu.study;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.homepage.LoginBean;
import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.system.MemberAdminBean;
import com.dunet.common.util.StringUtil;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QnaAdminBean {
    public QnaAdminBean() {
    }

    /**
     * 과정별 q&a 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<QnaData> selectQnaSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ArrayList<QnaData> list1 = null;
        ArrayList<QnaData> list2 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        QnaData data1 = null;
        QnaData data2 = null;
        String v_Bcourse = ""; //이전코스
        String v_course = ""; //현재코스
        String v_Bcourseseq = ""; //이전코스차수
        String v_courseseq = ""; //현재코스차수
        String v_subj = ""; //미응답갯수 계산위한 변수
        String v_year = "";
        String v_subjseq = "";
        // String v_dates = "";
        int v_noans = 0;
        int v_cnt = 0;
        // int l = 0;
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        String ss_uclass = box.getStringDefault("s_uclass", "ALL"); //과정분류
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL"); //과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //과정 차수

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        try {
            System.out.println("s_action" + box.getString("s_action"));
            if (box.getString("s_action").equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList<QnaData>();
                list2 = new ArrayList<QnaData>();

                sql1 = "select course,cyear,courseseq,coursenm,subj,year,subjseq,subjseqgr,subjnm,isclosed,isonoff, ";
                sql1 += "(select count(*) from TZ_QNA where subj = A.subj and subjseq=A.subjseq and kind = 0) as qcnt,";
                sql1 += "(select count(*) from TZ_QNA where subj = A.subj and subjseq=A.subjseq and kind > 0) as anscnt, ";

                sql1 += "((select count(*) from TZ_QNA where subj = A.subj and subjseq=A.subjseq and kind = 0) - ";
                sql1 += "(select count(*) from TZ_QNA where subj = A.subj and subjseq=A.subjseq and kind > 0)) as nanscnt ";

                sql1 += "from VZ_SCSUBJSEQ A where 1 = 1 ";
                if (!ss_grcode.equals("ALL")) {
                    sql1 += " and grcode = '" + ss_grcode + "'";
                }
                if (!ss_gyear.equals("ALL")) {
                    sql1 += " and gyear = '" + ss_gyear + "'";
                }
                if (!ss_grseq.equals("ALL")) {
                    sql1 += " and grseq = '" + ss_grseq + "'";
                }
                if (!ss_uclass.equals("ALL")) {
                    sql1 += " and scupperclass = '" + ss_uclass + "'";
                }
                if (!ss_subjcourse.equals("ALL")) {
                    sql1 += " and scsubj = '" + ss_subjcourse + "'";
                }
                if (!ss_subjseq.equals("ALL")) {
                    sql1 += " and scsubjseq = '" + ss_subjseq + "'";
                }

                if (v_orderColumn.equals("")) {
                    sql1 += " order by course, cyear, courseseq, subj, year, subjseq ";
                } else {
                    sql1 += " order by " + v_orderColumn + v_orderType;
                }

                ls1 = connMgr.executeQuery(sql1);

                while (ls1.next()) {
                    data1 = new QnaData();
                    data1.setCourse(ls1.getString("course"));
                    data1.setCyear(ls1.getString("cyear"));
                    data1.setCourseseq(ls1.getString("courseseq"));
                    data1.setCoursenm(ls1.getString("coursenm"));
                    data1.setSubj(ls1.getString("subj"));
                    data1.setYear(ls1.getString("year"));
                    data1.setSubjseq(ls1.getString("subjseq"));
                    data1.setSubjseqgr(ls1.getString("subjseqgr"));
                    data1.setSubjnm(ls1.getString("subjnm"));
                    data1.setQcnt(ls1.getInt("qcnt"));
                    data1.setAnscnt(ls1.getInt("anscnt"));
                    data1.setIsclosed(ls1.getString("isclosed"));

                    v_subj = ls1.getString("subj");
                    v_year = ls1.getString("year");
                    v_subjseq = ls1.getString("subjseq");

                    sql3 = "select lesson, seq, count(*) cnt from tz_qna where subj='" + v_subj + "' and year='" + v_year + "'";
                    sql3 += "\r\n  and subjseq='" + v_subjseq + "'  group by lesson, seq";
                    ls2 = connMgr.executeQuery(sql3);
                    v_noans = 0;
                    while (ls2.next()) {
                        v_cnt = ls2.getInt("cnt");
                        if (v_cnt == 1) {
                            v_noans += 1;
                        }
                    }
                    ls2.close();

                    data1.setNoanscnt(v_noans);

                    list1.add(data1);
                }
                ls1.close();

                for (int i = 0; i < list1.size(); i++) {
                    data2 = (QnaData) list1.get(i);
                    v_course = data2.getCourse();
                    v_courseseq = data2.getCourseseq();
                    if (!v_course.equals("000000") && !(v_Bcourse.equals(v_course) && v_Bcourseseq.equals(v_courseseq))) {
                        sql2 = "select count(subj) cnt from TZ_SUBJSEQ ";
                        sql2 += "where course = '" + v_course + "' and courseseq = '" + v_courseseq + "' ";
                        if (!ss_grcode.equals("ALL")) {
                            sql2 += " and grcode = '" + ss_grcode + "'";
                        }
                        if (!ss_gyear.equals("ALL")) {
                            sql2 += " and gyear = '" + ss_gyear + "'";
                        }
                        if (!ss_grseq.equals("ALL")) {
                            sql2 += " and grseq = '" + ss_grseq + "'";
                        }
                        //                        System.out.println("sql2============>"+sql2);
                        ls2 = connMgr.executeQuery(sql2);
                        if (ls2.next()) {
                            data2.setRowspan(ls2.getInt("cnt"));
                            data2.setIsnewcourse("Y");
                        }
                    } else {
                        data2.setRowspan(0);
                        data2.setIsnewcourse("N");
                    }
                    v_Bcourse = v_course;
                    v_Bcourseseq = v_courseseq;
                    list2.add(data2);
                    if (ls2 != null) {
                        try {
                            ls2.close();
                        } catch (Exception e) {
                        }
                    }
                }
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
        return list2;
    }

    /**
     * 과정차시별 q&a 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<QnaData> selectQnaSubjseqList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ArrayList<QnaData> list1 = null;
        String sql1 = "";
        String sql2 = "";
        QnaData data1 = null;
        String v_lesson = "";
        int v_noans = 0;
        int v_cnt = 0;
        // int l = 0;
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<QnaData>();
            // 시스템관련및 기타질문 데이터 가져오기 start
            data1 = new QnaData();
            data1.setLesson("00");
            data1.setLessonnm("시스템관련 및 기타질문");
            sql1 = "select count(*) cnt from TZ_QNA where subj = '" + v_subj + "' and subjseq='" + v_subjseq + "' and lesson='00' and kind = 0";
            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                data1.setQcnt(ls1.getInt("cnt"));
            }
            ls1.close();

            sql1 = "select seq, count(*) cnt from tz_qna where subj='" + v_subj + "' and year='" + v_year + "'";
            sql1 += "\r\n  and subjseq='" + v_subjseq + "' and  lesson = '00' group by seq";

            ls1 = connMgr.executeQuery(sql1);
            while (ls1.next()) {
                v_cnt = ls1.getInt("cnt");
                if (v_cnt == 1) {
                    v_noans += 1;
                }
            }
            ls1.close();
            data1.setNoanscnt(v_noans);
            list1.add(data1);
            //          시스템관련및 기타질문 데이터 가져오기 end

            sql1 = "select lesson, sdesc, ";
            sql1 += "(select count(*) from TZ_QNA where subj = '" + v_subj + "' and subjseq='" + v_subjseq + "' and lesson=A.lesson and kind = 0) as qcnt ";
            sql1 += "from TZ_SUBJLESSON A where A.subj = '" + v_subj + "' ";
            System.out.println("sql1============>" + sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                data1 = new QnaData();
                data1.setLesson(ls1.getString("lesson"));
                data1.setLessonnm(ls1.getString("sdesc"));
                data1.setQcnt(ls1.getInt("qcnt"));
                v_lesson = ls1.getString("lesson");

                sql2 = "select seq, count(*) cnt from tz_qna where subj='" + v_subj + "' and year='" + v_year + "'";
                sql2 += "\r\n  and subjseq='" + v_subjseq + "' and  lesson = '" + v_lesson + "' group by seq";
                ls2 = connMgr.executeQuery(sql2);
                v_noans = 0;
                while (ls2.next()) {
                    v_cnt = ls2.getInt("cnt");
                    if (v_cnt == 1) {
                        v_noans += 1;
                    }
                }
                ls2.close();

                data1.setNoanscnt(v_noans);
                list1.add(data1);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql2 = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list1;
    }

    /**
     * 일차별 질문 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<QnaData> selectQnaList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<QnaData> list1 = null;
        String sql1 = "";
        // String sql2 = "";
        QnaData data1 = null;

        // String v_subj = box.getString("p_subj");
        // String v_year = box.getString("p_year");
        // String v_subjseq = box.getString("p_subjseq");
        // String v_lesson = box.getString("p_lesson");

        //sql1 = "	select 	B.seq, B.title,  B.contents, B.inuserid, B.indate, b.jikwinm, b.compnm, b.cono,b.anscnt,b.replycono,b.replyindate,";
        //sql1+= "			(select name from tz_member where userid = b.replycono) replyusernm,";
        //sql1+= "			get_compnm((select comp from tz_member where userid = b.replycono),2,4) replyasgnnm, ";
        //sql1+= "			get_jikwinm((select jikwi from tz_member where userid = b.replycono), (select comp from tz_member where userid = b.replycono)) jikwi ";
        //sql1+= "  from 	( ";
        //sql1+= "			select 	B.seq, B.title,  B.contents, B.inuserid, B.indate, get_jikwinm(A.jikwi,A.comp) jikwinm, get_compnm(A.comp,2,4) compnm, A.cono, ";
        //sql1+= "					(select count(*) from TZ_QNA where subj=B.subj and year=B.year and subjseq=B.subjseq and lesson=B.lesson and seq=B.seq and kind>0) anscnt, ";
        //sql1+= " 					(select inuserid from tz_qna where subj=b.subj and year=b.year and subjseq=b.subjseq and lesson=b.lesson and seq=b.seq and ";
        //sql1+= "		    				kind = (select min(kind) from tz_qna where subj=b.subj and year=b.year and subjseq=b.subjseq and lesson=b.lesson and seq=b.seq and kind>0 )) replycono, ";
        //sql1+= "					(select indate from tz_qna where subj=b.subj and year=b.year and subjseq=b.subjseq and lesson=b.lesson and seq=b.seq and ";
        //sql1+= "							kind = (select min(kind) from tz_qna where subj=b.subj and year=b.year and subjseq=b.subjseq and lesson=b.lesson and seq=b.seq and kind>0 )) replyindate ";
        //sql1+= "			from TZ_MEMBER A, TZ_QNA B where B.subj = '"+v_subj+"' and year = '"+v_year+"' and subjseq='"+v_subjseq+"' ";
        // //sql1+= "				and lesson = '"+v_lesson+"' ";
        //sql1+=" and B.inuserid = A.userid and B.kind=0 order by B.indate desc ";
        //sql1+= " ) b ";

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<QnaData>();
            System.out.println("sql1-------------------" + sql1);
            ls1 = connMgr.executeQuery(sql1);
            while (ls1.next()) {
                data1 = new QnaData();
                data1.setSeq(ls1.getInt("seq"));
                data1.setTitle(ls1.getString("title"));
                data1.setContents(ls1.getCharacterStream("contents"));
                data1.setInuserid(ls1.getString("inuserid"));
                data1.setIndate(ls1.getString("indate"));
                data1.setJikwinm(ls1.getString("jikwinm"));
                data1.setAsgnnm(ls1.getString("compnm"));
                data1.setCono(ls1.getString("cono"));
                data1.setAnscnt(ls1.getInt("anscnt"));
                data1.setInusernm(MemberAdminBean.getUserName(ls1.getString("inuserid")));
                data1.setReplycono(ls1.getString("replycono")); //최초 답변자 ID
                data1.setReplyusernm(ls1.getString("replyusernm")); //최초 답변자 이름
                data1.setReplyasgnnm(ls1.getString("replyasgnnm")); //최초 답변자 이름
                data1.setReplyindate(ls1.getString("replyindate"));
                data1.setDelayday(FormatDate.datediff("date", ls1.getString("indate"), ls1.getString("replyindate")));
                list1.add(data1);
            }
            ls1.close();

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
     * 일차별 질문 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<QnaData> selectQnaList2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<QnaData> list1 = null;
        String sql1 = "";
        // String sql2 = "";
        QnaData data1 = null;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        // String v_lesson = box.getString("p_lesson");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<QnaData>();

            sql1 = "	select a.seq, a.title, a.inuserid,                                        ";
            sql1 += "		   case a.replygubun when 'Y' then '응답' else '미응답' end replygubun, ";
            sql1 += "	       b.name, a.indate,a.replyuserid, a.replyusernm, a.replydate         ";
            sql1 += "   from tz_qna a, tz_member b                                              ";
            sql1 += "	 where a.inuserid=b.userid                                                ";
            sql1 += "	   and a.subj='" + v_subj + "'                                                ";
            sql1 += "	   and a.year='" + v_year + "'                                                ";
            sql1 += "	   and a.subjseq='" + v_subjseq + "'                                          ";
            //	       sql1 +="	   and a.togubun='2'                             ";
            sql1 += "	   and a.kind ='0'                                                        ";
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                data1 = new QnaData();
                data1.setSeq(ls1.getInt("seq"));
                data1.setTitle(ls1.getString("title"));
                data1.setReplygubun(ls1.getString("replygubun"));
                data1.setInuserid(ls1.getString("inuserid"));
                data1.setName(ls1.getString("name"));
                data1.setIndate(ls1.getString("indate"));
                data1.setReplyuserid(ls1.getString("replyuserid"));
                data1.setReplyusernm(ls1.getString("replyusernm"));
                data1.setReplydate(ls1.getString("replydate"));
                data1.setDelayday(FormatDate.datediff("date", data1.getIndate(), data1.getReplydate()));
                list1.add(data1);
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
    public ArrayList<QnaData> selectQnaDetail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<QnaData> list1 = null;
        String sql1 = "";
        QnaData data1 = null;
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        // String v_lesson = box.getString("p_lesson");
        String v_seq = box.getString("p_seq");

        sql1 = " select B.seq, B.kind, B.title, B.contents, B.inuserid, B.indate, B.replygubun, A.comp ";
        sql1 += "   from TZ_MEMBER A, TZ_QNA B where B.subj = '" + v_subj + "' and year = '" + v_year + "' and subjseq='" + v_subjseq + "' ";
        //       sql1 +=" and lesson = '"+v_lesson+"'";
        sql1 += "and B.seq =" + v_seq + " and B.inuserid = A.userid order by B.kind ";
        System.out.println(sql1);
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<QnaData>();

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                data1 = new QnaData();
                data1.setSeq(ls1.getInt("seq"));
                //data1.setTypes(ls1.getInt("types"));
                data1.setKind(ls1.getInt("kind"));
                data1.setTitle(ls1.getString("title"));
                //data1.setContents(ls1.getString("contents"));
                data1.setContents(ls1.getCharacterStream("contents"));
                data1.setInuserid(ls1.getString("inuserid"));
                data1.setIndate(ls1.getString("indate"));
                data1.setReplygubun(ls1.getString("replygubun"));
                data1.setInusernm(MemberAdminBean.getUserName(data1.getIndate()));

                list1.add(data1);
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

    public int insertQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        int isOk = 0;
        String v_user_id = box.getSession("userid");
        String v_user_nm = box.getSession("name");
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        int v_kind = box.getInt("p_kind");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));
        String v_lesson = box.getString("p_lesson");
        int v_seq = box.getInt("p_seq");
        int v_max = 0;
        int v_inseq = 0;
        int v_intype = 0;
        // int i = 1;
        int v_cnt = 0;

        //String v_replygubun = getReplyGubun(box);
        String v_replygubun = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////////

            if (v_kind == 0) { // 질문일경우
                sql1 = " select max(seq)+1 maxseq from TZ_QNA ";
                sql1 += "  where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "'";
                sql1 += "    and kind='0' ";
            } else { //답변일경우
                sql1 = "select max(kind)+1 maxseq from TZ_QNA ";
                sql1 += " where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "'";
                sql1 += "  and seq=" + v_seq + " ";
            }

            ls = connMgr.executeQuery(sql1);
            System.out.println("sql1==========>" + sql1);

            if (ls.next()) {
                v_max = ls.getInt(1);
            }

            if (v_kind == 0) { // 질문일경우
                v_inseq = v_max;
                v_intype = 0;
            } else { //답변일경우
                v_inseq = v_seq;
                v_intype = v_max;
            }

            sql2 = "insert into TZ_QNA(subj,year,subjseq,lesson,seq,kind,title,contents,";
            sql2 += "indate,inuserid,replygubun,luserid,ldate,grcode) ";
            sql2 += "values(?,?,?,?,?,?,?,'" + v_contents + "',to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N00001')";

            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_subj);
            pstmt2.setString(2, v_year);
            pstmt2.setString(3, v_subjseq);
            pstmt2.setString(4, "000");
            pstmt2.setInt(5, v_inseq);
            pstmt2.setInt(6, v_intype);
            pstmt2.setString(7, v_title);
            pstmt2.setString(8, v_user_id);
            pstmt2.setString(9, v_replygubun);
            pstmt2.setString(10, v_user_id);

            isOk = pstmt2.executeUpdate();

            //CLOB 적용
            sql3 = "select 	contents ";
            sql3 += "from 	TZ_QNA ";
            sql3 += "where 	subj = '" + v_subj + "' and year = '" + v_year + "' and subjseq = '" + v_subjseq + "' and ";
            sql3 += "  		lesson = '" + v_lesson + "' and  seq  = " + v_inseq + " and kind = '" + v_intype + "'	";

            if (isOk > 0) {
                sql1 = "select count(*) cnt ";
                sql1 += "  from TZ_QNA ";
                sql1 += " where subj='" + v_subj + "'";
                sql1 += "   and year='" + v_year + "'";
                sql1 += "   and subjseq='" + v_subjseq + "'";
                sql1 += "   and seq=" + v_seq;
                sql1 += "   and kind=0";
                sql1 += "   and replydate=''";

                ls = connMgr.executeQuery(sql1);
                if (ls.next()) {
                    v_cnt = ls.getInt(1);
                }
                ls.close();

                if (v_cnt == 0) {
                    sql1 = "update TZ_QNA set replyuserid=?,replyusernm=?,replydate=to_char(sysdate, 'YYYYMMDDHH24MISS') where subj=? and year=? and subjseq=? and seq=? and kind=0";
                    pstmt3 = connMgr.prepareStatement(sql1);

                    pstmt3.setString(1, v_user_id);
                    pstmt3.setString(2, v_user_nm);
                    pstmt3.setString(3, v_subj);
                    pstmt3.setString(4, v_year);
                    pstmt3.setString(5, v_subjseq);
                    pstmt3.setInt(6, v_seq);
                    isOk = pstmt3.executeUpdate();
                }

                if (v_kind != 0) {
                    //답변자에게 포인트 점수 부여
                    LoginBean bean = new LoginBean();
                    bean.loginMileage(connMgr, v_user_id, "ELN_REG_REPLY");
                }

                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            connMgr.rollback();
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
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
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
        return isOk;
    }

    /**
     * 수정
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int updateQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;
        String v_user_id = box.getSession("userid");
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        // String v_lesson = box.getString("p_lesson");
        int v_seq = box.getInt("p_seq");
        int v_kind = box.getInt("p_kind");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));
        // int i = 1;

        //String v_replygubun = getReplyGubun(box);
        String v_replygubun = "";

        try {
            connMgr = new DBConnectionManager();
            sql2 = "update TZ_QNA set title=?,contents=?,inuserid=?,replygubun=?,luserid=?,ldate=to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql2 += "where subj=? and year=? and subjseq=? and seq=? and kind=? ";
            pstmt2 = connMgr.prepareStatement(sql2);
            //          System.out.println("sql2==========>"+sql2);

            pstmt2.setString(1, v_title);
            //          pstmt2.setString(i++,v_contents);
            pstmt2.setCharacterStream(2, new StringReader(v_contents), v_contents.length());
            pstmt2.setString(3, v_user_id);
            pstmt2.setString(4, v_replygubun);
            pstmt2.setString(5, v_user_id);
            pstmt2.setString(6, v_subj);
            pstmt2.setString(7, v_year);
            pstmt2.setString(8, v_subjseq);
            //          pstmt2.setString(i++,v_lesson);
            pstmt2.setInt(9, v_seq);
            pstmt2.setInt(10, v_kind);
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
    public int deleteQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "", sql = "";
        int isOk = 0;
        // String v_user_id = box.getSession("userid");
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        // String v_lesson = box.getString("p_lesson");
        int v_seq = box.getInt("p_seq");
        int v_kind = box.getInt("p_kind");

        int i = 1;
        int v_cnt = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////
            sql1 = "delete TZ_QNA where subj=? and year=? and subjseq=? and seq=? and kind=? ";
            pstmt2 = connMgr.prepareStatement(sql1);

            pstmt2.setString(i++, v_subj);
            pstmt2.setString(i++, v_year);
            pstmt2.setString(i++, v_subjseq);
            pstmt2.setInt(i++, v_seq);
            pstmt2.setInt(i++, v_kind);
            isOk = pstmt2.executeUpdate();

            sql = " select count(*) ";
            sql += "   from tz_qna ";
            sql += "   where subj='" + v_subj + "'";
            sql += "     and year='" + v_year + "'";
            sql += "     and subjseq='" + v_subjseq + "'";
            sql += "     and seq=" + v_seq;
            sql += "     and kind='1'";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                v_cnt = ls.getInt(1);
            }
            ls.close();

            if (v_cnt == 0) {
                sql = "update TZ_QNA";
                sql += "   set replyuserid='',";
                sql += "       replyusernm='',";
                sql += "       replydate=''";
                sql += " where subj=" + SQLString.Format(v_subj);
                sql += "   and year=" + SQLString.Format(v_year);
                sql += "   and subjseq=" + SQLString.Format(v_subjseq);
                sql += "   and seq=" + v_seq;
                sql += "   and kind='0'";
                isOk = connMgr.executeUpdate(sql);
            } else {
                isOk = 1;
            }

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            connMgr.rollback();
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
        return isOk;
    }

    /**
     * 답변자구분 구하기(1:학습자, 2:강사, 3:운영자)
     * 
     * @param box receive from the form object and session
     * @return result 답변자 구분값
     */
    public String getReplyGubun(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        String result = null;
        int cnt = 0;
        String v_subj = box.getString("p_subj"); //과정

        String s_user_id = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");
        String v_gadmin_gubun = StringManager.substring(s_gadmin, 0, 1);

        // 1 : 학습자, 2 : 강사,  3 : 운영자
        if (v_gadmin_gubun.equals("A") || v_gadmin_gubun.equals("H") || v_gadmin_gubun.equals("K")) {
            result = "3";
        } else if (v_gadmin_gubun.equals("Z")) {
            result = "1";
        } else if (v_gadmin_gubun.equals("F") || v_gadmin_gubun.equals("P")) { // 관리과정인지 체크
            try {
                connMgr = new DBConnectionManager();

                sql1 = " select count(*) cnt from TZ_GADMIN  ";
                sql1 += "  where userid = " + StringManager.makeSQL(s_user_id);
                sql1 += "    and gadmin = " + StringManager.makeSQL(s_gadmin);
                sql1 += "    and subj   = " + StringManager.makeSQL(v_subj);

                ls1 = connMgr.executeQuery(sql1);
                if (ls1.next())
                    cnt = ls1.getInt("cnt");

                if (cnt > 0) { // 관리과정일경우
                    if (v_gadmin_gubun.equals("H"))
                        result = "3"; // 과정관리자일경우 = 3
                    else
                        result = "2"; // 강사일경우         = 2
                } else {
                    result = "1";
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
        }

        return result;
    }

    /**
     * 질의응답 전체 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<QnaData> selectQnaAllList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<QnaData> list1 = null;
        String sql1 = "";
        String sql2 = "";
        QnaData data1 = null;

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<QnaData>();

            if (!ss_grcode.equals("ALL"))
                sql2 = " and b.grcode='" + ss_grcode + "' ";
            sql1 = "	select a.seq, a.title, a.kind, a.inuserid, a.indate, b.scsubjnm, a.subj, a.year, a.subjseq, a.lesson ";
            sql1 += "	from TZ_QNA a, VZ_SCSUBJSEQ b ";
            sql1 += "	where a.subj=b.scsubj  " + sql2;
            sql1 += "    order by a.subj, a.year, a.subjseq, a.lesson, a.seq, a.kind ";

            ls1 = connMgr.executeQuery(sql1);
            while (ls1.next()) {
                data1 = new QnaData();
                data1.setSeq(ls1.getInt("seq"));
                data1.setTitle(ls1.getString("title"));
                data1.setKind(ls1.getInt("kind"));
                data1.setInuserid(ls1.getString("inuserid"));
                data1.setIndate(ls1.getString("indate"));
                data1.setSubjnm(ls1.getString("scsubjnm"));
                data1.setSubj(ls1.getString("subj"));
                data1.setYear(ls1.getString("year"));
                data1.setSubjseq(ls1.getString("subjseq"));
                data1.setLesson(ls1.getString("lesson"));
                list1.add(data1);
            }
            ls1.close();

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
}
