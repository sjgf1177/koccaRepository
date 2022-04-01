//**********************************************************
//  1. 제      목: TORON BEAN
//  2. 프로그램명: ToronBean.java
//  3. 개      요: 토론방 bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 22
//  7. 수      정:
//**********************************************************
package com.credu.study;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

public class ToronBean {

    public ToronBean() {
    }

    /**
     * 토론주제 등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int insertTopic(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        ListSet ls1 = null;
        int isOk = 0;
        String v_user_id = box.getSession("userid");
        String v_year = box.getString("p_year"); //년도
        String v_subj = box.getString("p_subj"); //과정
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        String v_title = box.getString("p_title"); //토론주제
        String v_adcontent = box.getString("p_adcontent"); //내용
        String v_started = box.getString("p_started") + box.getString("p_stime"); //토론 시작일
        String v_ended = box.getString("p_ended") + box.getString("p_ltime"); //토론 종료일
        String v_tpcode = "";

        try {
            connMgr = new DBConnectionManager();

            //select max(tpcode)
            sql1 = "select max(TO_NUMBER(tpcode)) from TZ_TORONTP";
            ls1 = connMgr.executeQuery(sql1);

            if (ls1.next()) {
                v_tpcode = (StringManager.toInt(ls1.getString(1)) + 1) + "";
            }

            //insert TZ_TORONTP table
            sql2 = "insert into TZ_TORONTP(year,subj,subjseq,tpcode,title,adcontent,aduserid,addate,started,ended,luserid,ldate) ";
            sql2 += "values (?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?,to_char(sysdate,'YYYYMMDDHH24MISS'))";
            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_year);
            pstmt2.setString(2, v_subj);
            pstmt2.setString(3, v_subjseq);
            pstmt2.setString(4, v_tpcode);
            pstmt2.setString(5, v_title);
            //            pstmt2.setString(6, v_adcontent);
            pstmt2.setCharacterStream(6, new StringReader(v_adcontent), v_adcontent.length());
            pstmt2.setString(7, v_user_id);
            pstmt2.setString(8, v_started);
            pstmt2.setString(9, v_ended);
            pstmt2.setString(10, v_user_id);

            isOk = pstmt2.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
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
     * 토론주제 수정
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int updateTopic(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        int isOk = 0;
        // String v_user_id = box.getSession("userid");
        String v_year = box.getString("p_year"); //년도
        String v_subj = box.getString("p_subj"); //과정
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        String v_title = box.getString("p_title"); //토론주제
        String v_adcontent = box.getString("p_adcontent"); //내용
        String v_started = box.getString("p_started") + box.getString("p_stime"); //토론 시작일
        String v_ended = box.getString("p_ended") + box.getString("p_ltime"); //토론 종료일
        String v_tpcode = box.getString("p_tpcode");

        try {
            connMgr = new DBConnectionManager();

            //update TZ_TORONTP table
            sql1 = "update TZ_TORONTP set title=?,adcontent=?,started=?,ended=? ";
            sql1 += "where subj=? and year=? and subjseq=? and tpcode=? ";
            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_title);
            //            pstmt1.setString(2, v_adcontent);
            pstmt1.setCharacterStream(2, new StringReader(v_adcontent), v_adcontent.length());
            pstmt1.setString(3, v_started);
            pstmt1.setString(4, v_ended);
            pstmt1.setString(5, v_subj);
            pstmt1.setString(6, v_year);
            pstmt1.setString(7, v_subjseq);
            pstmt1.setString(8, v_tpcode);

            isOk = pstmt1.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
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
     * 토론주제 삭제
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int deleteTopic(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 0;
        // int isOk2 = 0;
        // String v_user_id = box.getSession("userid");
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        String v_tpcode = box.getString("p_tpcode");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            //delete TZ_TORONTP table
            sql1 = "delete from TZ_TORONTP ";
            sql1 += "where subj=? and year=? and subjseq=? and tpcode=? ";
            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_subj);
            pstmt1.setString(2, v_year);
            pstmt1.setString(3, v_subjseq);
            pstmt1.setString(4, v_tpcode);

            isOk1 = pstmt1.executeUpdate();

            //delete TZ_TORONTP table
            sql2 = "delete from TZ_TORON ";
            sql2 += "where subj=? and year=? and subjseq=? and tpcode=? ";
            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_subj);
            pstmt2.setString(2, v_year);
            pstmt2.setString(3, v_subjseq);
            pstmt2.setString(4, v_tpcode);

            pstmt2.executeUpdate();

            if (isOk1 > 0)
                connMgr.commit();
            else
                connMgr.rollback();
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
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
        return isOk1;
    }

    /**
     * 토론글 등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int insertToron(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        ListSet ls1 = null;
        int isOk = 0;
        String v_user_id = box.getSession("userid");
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        String v_tpcode = box.getString("p_tpcode"); //토론주제코드
        String v_title = box.getString("p_title"); //제목
        String v_adcontent = box.getString("p_adcontent"); //내용
        int v_seq = 0;

        try {
            connMgr = new DBConnectionManager();

            //select max(seq)
            sql1 = "select NVL(max(seq), 0) from TZ_TORON ";
            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                v_seq = ls1.getInt(1) + 1;
            }

            //insert TZ_TORON table
            sql2 = "insert into TZ_TORON(subj,year,subjseq,tpcode,seq,refseq,title,adcontent,aduserid,addate,luserid,ldate,levels,position) ";
            sql2 += "values (?, ?, ?, ?, ?, ?, ?, ?, ?, to_char(sysdate,'YYYYMMDDHH24MISS'), ?, to_char(sysdate,'YYYYMMDDHH24MISS'),1,1)";
            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_subj);
            pstmt2.setString(2, v_year);
            pstmt2.setString(3, v_subjseq);
            pstmt2.setString(4, v_tpcode);
            pstmt2.setInt(5, v_seq);
            pstmt2.setInt(6, v_seq);
            pstmt2.setString(7, v_title);
            //            pstmt2.setString(8, v_adcontent);
            pstmt2.setCharacterStream(8, new StringReader(v_adcontent), v_adcontent.length());
            pstmt2.setString(9, v_user_id);
            pstmt2.setString(10, v_user_id);

            isOk = pstmt2.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
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
     * 토론글 수정
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int updateToron(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        int isOk = 0;
        // String v_user_id = box.getSession("userid");
        String v_year = box.getString("p_year"); //년도
        String v_subj = box.getString("p_subj"); //과정
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        String v_tpcode = box.getString("p_tpcode"); //토론주제코드
        int v_seq = box.getInt("p_seq"); //의견일련번호
        String v_title = box.getString("p_title"); //토론주제
        String v_adcontent = box.getString("p_adcontent"); //내용
        // String v_started = box.getString("p_started") + box.getString("p_stime"); //토론 시작일
        // String v_ended = box.getString("p_ended") + box.getString("p_ltime"); //토론 종료일

        try {
            connMgr = new DBConnectionManager();

            //update TZ_TORONTP table
            sql1 = "update TZ_TORON set title=?,adcontent=? ";
            sql1 += "where subj=? and year=? and subjseq=? and tpcode=? and seq=? ";
            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_title);
            //            pstmt1.setString(2, v_adcontent);
            pstmt1.setCharacterStream(2, new StringReader(v_adcontent), v_adcontent.length());
            pstmt1.setString(3, v_subj);
            pstmt1.setString(4, v_year);
            pstmt1.setString(5, v_subjseq);
            pstmt1.setString(6, v_tpcode);
            pstmt1.setInt(7, v_seq);

            isOk = pstmt1.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
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
     * 토론 답글 등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int insertToronReply(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt3 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        ListSet ls2 = null;
        // int isOk1 = 0;
        int isOk3 = 0;
        String v_user_id = box.getSession("userid");
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        String v_tpcode = box.getString("p_tpcode"); //토론주제코드
        String v_title = box.getString("p_title"); //제목
        String v_adcontent = box.getString("p_adcontent"); //내용
        int v_refseq = box.getInt("p_refseq"); //상위글 번호
        int v_levels = box.getInt("p_levels");
        int v_position = box.getInt("p_position");
        int v_seq = 0;

        try {
            connMgr = new DBConnectionManager();

            // 기존 답변글 위치 한칸밑으로 변경
            sql1 = "update TZ_TORON ";
            sql1 += "   set position = nvl(position,0) + 1 ";
            sql1 += " where refseq   = ? ";
            sql1 += "   and position > ? ";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(1, v_refseq);
            pstmt1.setInt(2, v_position);
            pstmt1.executeUpdate();

            //select max(seq)
            sql2 = "select max(seq) from TZ_TORON";
            ls2 = connMgr.executeQuery(sql2);
            if (ls2.next()) {
                v_seq = ls2.getInt(1) + 1;
            }

            //insert TZ_TORON table
            sql3 = "insert into TZ_TORON(subj,year,subjseq,tpcode,seq,refseq,levels,position,title,adcontent,aduserid,addate,luserid,ldate) ";
            sql3 += "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, to_char(sysdate,'YYYYMMDDHH24MISS'), ?, to_char(sysdate,'YYYYMMDDHH24MISS'))";
            pstmt3 = connMgr.prepareStatement(sql3);

            pstmt3.setString(1, v_subj);
            pstmt3.setString(2, v_year);
            pstmt3.setString(3, v_subjseq);
            pstmt3.setString(4, v_tpcode);
            pstmt3.setInt(5, v_seq);
            pstmt3.setInt(6, v_refseq);
            pstmt3.setInt(7, v_levels + 1);
            pstmt3.setInt(8, v_position + 1);
            pstmt3.setString(9, v_title);
            //            pstmt3.setString(10, v_adcontent);
            pstmt3.setCharacterStream(10, new StringReader(v_adcontent), v_adcontent.length());
            pstmt3.setString(11, v_user_id);
            pstmt3.setString(12, v_user_id);

            isOk3 = pstmt3.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql3);
            throw new Exception("sql = " + sql3 + "\r\n" + ex.getMessage());
        } finally {
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk3;
    }

    /**
     * 토론글 삭제
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int deleteToron(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        int isOk = 0;
        // String v_user_id = box.getSession("userid");
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        String v_tpcode = box.getString("p_tpcode"); //토론주제코드
        int v_seq = box.getInt("p_seq"); //의견일련번호

        try {
            connMgr = new DBConnectionManager();

            // 답변 유무 체크(답변 있을시 삭제불가)
            if (this.selectToronReply(v_seq) == 0) {

                //delete TZ_TORONTP table
                sql1 = "delete from TZ_TORON ";
                sql1 += "where subj=? and year=? and subjseq=? and tpcode=? and seq=?";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setString(1, v_subj);
                pstmt1.setString(2, v_year);
                pstmt1.setString(3, v_subjseq);
                pstmt1.setString(4, v_tpcode);
                pstmt1.setInt(5, v_seq);

                isOk = pstmt1.executeUpdate();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
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
     * 삭제시 하위 답변 유무 체크
     * 
     * @param seq 게시판 번호
     * @return result 0 : 답변 없음, 1 : 답변 있음
     * @throws Exception
     */
    public int selectToronReply(int seq) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        try {
            connMgr = new DBConnectionManager();

            sql = "  select count(*) cnt                 ";
            sql += "  from                                ";
            sql += "    (select refseq, levels, position  ";
            sql += "      from TZ_TORON                   ";
            sql += "     where seq = " + seq;
            sql += "     ) a, TZ_TORON b                  ";
            sql += " where a.refseq = b.refseq            ";
            sql += "   and b.levels = (a.levels+1)        ";
            sql += "   and b.position = (a.position+1)    ";
            //            System.out.println("sql===============>"+sql);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
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
        return result;
    }

    /**
     * 토론방 주제 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ToronData selectTopic(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls2 = null;
        String sql1 = "";
        String sql2 = "";
        // int isOk = 0;
        ToronData data2 = null;
        String v_year = box.getString("p_year"); //년도
        String v_subj = box.getString("p_subj"); //과정
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        String v_tpcode = box.getString("p_tpcode"); //토론주제코드
        try {
            connMgr = new DBConnectionManager();

            //update TZ_TORONTP
            sql1 = "update TZ_TORONTP set cnt=nvl(cnt,0)+1 ";
            sql1 += "where subj=" + SQLString.Format(v_subj) + " and year=" + SQLString.Format(v_year);
            sql1 += " and subjseq=" + SQLString.Format(v_subjseq) + " and tpcode=" + SQLString.Format(v_tpcode);
            connMgr.executeUpdate(sql1);

            //select stated,ended,title,addate,adcontent,aduserid,name,cnt
            sql2 = "select A.started,A.ended,A.title,A.addate,A.adcontent,A.aduserid, ";
            sql2 += "(select name from TZ_MEMBER where userid = A.aduserid) as name,cnt ";
            sql2 += "from TZ_TORONTP A ";
            sql2 += "where subj=" + SQLString.Format(v_subj) + " and year=" + SQLString.Format(v_year);
            sql2 += " and subjseq=" + SQLString.Format(v_subjseq) + " and tpcode=" + SQLString.Format(v_tpcode);
            //            System.out.println("sql2=========>"+sql2);
            ls2 = connMgr.executeQuery(sql2);

            if (ls2.next()) {
                data2 = new ToronData();
                data2.setStarted(ls2.getString("started"));
                data2.setEnded(ls2.getString("ended"));
                data2.setTitle(ls2.getString("title"));
                data2.setAddate(ls2.getString("addate"));
                data2.setAdcontent(ls2.getCharacterStream("adcontent"));
                data2.setAduserid(ls2.getString("aduserid"));
                data2.setName(ls2.getString("name"));
                data2.setCnt(ls2.getInt("cnt"));
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
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
        return data2;
    }

    /**
     * 토론글 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ToronData selectToron(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls2 = null;
        String sql1 = "";
        String sql2 = "";
        ToronData data2 = null;
        String v_year = box.getString("p_year"); //년도
        String v_subj = box.getString("p_subj"); //과정
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        String v_tpcode = box.getString("p_tpcode"); //토론주제코드
        int v_seq = box.getInt("p_seq"); //일련번호
        // int isOk1 = 0;
        // int l = 0;
        try {
            connMgr = new DBConnectionManager();

            //update TZ_TORONTP
            sql1 = "update TZ_TORON set cnt=nvl(cnt,0)+1 ";
            sql1 += "where subj=" + SQLString.Format(v_subj) + " and year=" + SQLString.Format(v_year);
            sql1 += " and subjseq=" + SQLString.Format(v_subjseq) + " and tpcode=" + SQLString.Format(v_tpcode);
            sql1 += " and seq=" + SQLString.Format(v_seq);
            connMgr.executeUpdate(sql1);

            //select seq,refseq,levels,position,title,adcontent,aduserid,cnt,addate,name
            sql2 = "select seq,refseq,levels,position,title,adcontent,aduserid,cnt,addate, ";
            sql2 += "(select name from TZ_MEMBER where userid = A.aduserid) as name ";
            sql2 += " from TZ_TORON A ";
            sql2 += "where subj=" + SQLString.Format(v_subj) + " and year=" + SQLString.Format(v_year);
            sql2 += " and subjseq=" + SQLString.Format(v_subjseq) + " and tpcode=" + SQLString.Format(v_tpcode);
            sql2 += " and seq=" + SQLString.Format(v_seq);
            //            System.out.println("sql2============>"+sql2);
            ls2 = connMgr.executeQuery(sql2);

            if (ls2.next()) {
                data2 = new ToronData();
                data2.setSeq(ls2.getInt("seq"));
                data2.setRefseq(ls2.getInt("refseq"));
                data2.setLevels(ls2.getInt("levels"));
                data2.setPosition(ls2.getInt("position"));
                data2.setTitle(ls2.getString("title"));
                data2.setAdcontent(ls2.getCharacterStream("adcontent"));
                data2.setAduserid(ls2.getString("aduserid"));
                data2.setCnt(ls2.getInt("cnt"));
                data2.setAddate(ls2.getString("addate"));
                data2.setName(ls2.getString("name"));
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
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
        return data2;
    }

    /**
     * 토론글 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<ToronData> selectToronList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<ToronData> list1 = null;
        String sql1 = "";
        ToronData data1 = null;
        String v_year = box.getString("p_year"); //년도
        String v_subj = box.getString("p_subj"); //과정
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        String v_tpcode = box.getString("p_tpcode"); //토론주제코드
        String v_grcode = box.getSession("tem_grcode"); //과정코드
        // int l = 0;
        
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<ToronData>();

            //select seq,title,aduserid,addate,levels,name
            sql1 = "select seq,title,aduserid,addate,levels, ";
            sql1 += "(select name from TZ_MEMBER where userid = A.aduserid and grcode = " + SQLString.Format(v_grcode) + ") as name ";
            sql1 += " from TZ_TORON A ";
            sql1 += "where subj=" + SQLString.Format(v_subj) + " and year=" + SQLString.Format(v_year);
            sql1 += " and subjseq=" + SQLString.Format(v_subjseq) + " and tpcode=" + SQLString.Format(v_tpcode);
            sql1 += " order by A.refseq desc, A.position asc ";
            //            System.out.println("sql1============>"+sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                data1 = new ToronData();
                data1.setSeq(ls1.getInt("seq"));
                data1.setTitle(ls1.getString("title"));
                data1.setAduserid(ls1.getString("aduserid"));
                data1.setAddate(ls1.getString("addate"));
                data1.setLevels(ls1.getInt("levels"));
                data1.setName(ls1.getString("name"));
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
     * 토론방 주제 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<ToronData> selectTopicList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<ToronData> list1 = null;
        String sql1 = "";
        ToronData data1 = null;
        String v_year = box.getString("p_year"); //년도
        String v_subj = box.getString("p_subj"); //과정
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        String v_user_id = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<ToronData>();

            /*
             * sql1 = "select tpcode,title,aduserid,cnt,addate,started,ended, ";
             * sql1+=
             * "(select name from TZ_MEMBER where userid = A.aduserid) as name "
             * ; sql1+= "  from TZ_TORONTP A "; sql1+=
             * "where subj="+SQLString.Format
             * (v_subj)+" and year="+SQLString.Format(v_year); sql1+=
             * " and subjseq="+SQLString.Format(v_subjseq); sql1+=
             * " order by tpcode desc";
             */

            // 2008.09.25
            sql1 = "SELECT TPCODE, TITLE, ADUSERID, CNT,ADDATE, STARTED,ENDED,NAME,LDATE,DECODE(B.LDATE,NULL,'0',B.SCORE) AS SCORE FROM (";
            sql1 += "SELECT tpcode,title,aduserid,cnt,addate,started,ended ";
            sql1 += "    , (select name from tz_member where userid = A.aduserid) as name ";
            sql1 += "    , (select NVL(max(ldate),'') from tz_toron where subj=a.subj and year=a.year and subjseq=a.subjseq and tpcode=a.tpcode and aduserid='" + v_user_id + "') as ldate ";
            sql1 += "    , (select NVL(etc1,'') from tz_student where subj=a.subj and year=a.year and subjseq=a.subjseq and userid='" + v_user_id + "') as score ";
            sql1 += " FROM tz_torontp a ";
            sql1 += " WHERE subj=" + SQLString.Format(v_subj) + " AND year=" + SQLString.Format(v_year) + " AND subjseq=" + SQLString.Format(v_subjseq);
            sql1 += " ORDER BY TO_NUMBER(tpcode) DESC";
            sql1 += ") B";

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                data1 = new ToronData();
                data1.setTpcode(ls1.getString("tpcode"));
                data1.setTitle(ls1.getString("title"));
                data1.setAduserid(ls1.getString("aduserid"));
                data1.setCnt(ls1.getInt("cnt"));
                data1.setAddate(ls1.getString("addate"));
                data1.setStarted(ls1.getString("started"));
                data1.setEnded(ls1.getString("ended"));
                data1.setName(ls1.getString("name"));

                data1.setLdate(ls1.getString("ldate"));
                data1.setPosition(ls1.getInt("score"));

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

}