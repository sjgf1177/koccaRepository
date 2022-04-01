//**********************************************************
//1. 제      목:
//2. 프로그램명: SulmunSubjUserBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: lyh
//**********************************************************

package com.credu.research;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SulmunSubjUserBean {

    public SulmunSubjUserBean() {
    }

    /**
     * 과정 설문 등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int InsertSulmunUserResult(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        ResultSet rs = null;
        String sql = "";
        String sql1 = "";
        int isOk = 2;
        int isOk1 = 0;
        int isOkSt = 0;

        //String v_grcode     = box.getString("p_grcode");
        String v_grcode = box.getSession("tem_grcode");
        String v_subj = box.getString("s_subj");
        String v_gubun = box.getString("p_subj");
        String v_gyear = box.getString("p_gyear");
        String v_subjseq = box.getString("p_subjseq");
        int v_sulpapernum = box.getInt("p_sulpapernum");
        String v_userid = box.getString("p_userid");
        String v_sulnums = box.getString("p_sulnums"); // 문제조합
        String v_answers = box.getString("p_answers"); // 정답조합
        String v_luserid = box.getSession("userid");
        int i_code1 = 0; // 과정만족도
        int i_code2 = 0; // 내용이해도
        int i_code3 = 0; // 과정난이도
        int i_code4 = 0; // 업무활용도
        int i_code5 = 0; // 질문대응
        int i_code6 = 0; // 장애대응
        int i_code7 = 0; // 강사만족도
        double i_code1_avg = 0;
        double i_code2_avg = 0;
        double i_code3_avg = 0;
        double i_code4_avg = 0;
        double i_code5_avg = 0;
        double i_code6_avg = 0;
        double i_code7_avg = 0;
        int i_code1_cnt = 0;
        int i_code2_cnt = 0;
        int i_code3_cnt = 0;
        int i_code4_cnt = 0;
        int i_code5_cnt = 0;
        int i_code6_cnt = 0;
        int i_code7_cnt = 0;
        String s_distcode10 = ""; // 소감

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            //설문기간 확인
            //   isOk = getSulmunGigan(box);

            // 설문 분류별로 척도점수 합계
            String[] arr_sulnums = v_sulnums.split("\\,");
            String[] arr_answers = v_answers.split("\\,");

            for (int i = 0; i < arr_sulnums.length; i++) {
                // 문제당 분류코드
                String ss_distcode = "";
                String ss_sultype = "";
                sql = " select distcode, sultype from TZ_SUL where subj='ALL' AND GRCODE=? and sulnum = ? ";

                pstmt1 = connMgr.prepareStatement(sql);
                pstmt1.setString(1, v_grcode);
                pstmt1.setInt(2, Integer.parseInt(arr_sulnums[i]));
                rs = pstmt1.executeQuery();

                if (rs.next()) {
                    ss_distcode = rs.getString("distcode");
                    ss_sultype = rs.getString("sultype");
                }
                if (pstmt1 != null) {
                    try {
                        pstmt1.close();
                    } catch (Exception e) {
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (Exception e) {
                    }
                }

                // 10.소감
                if (ss_distcode.equals("10")) {
                    s_distcode10 = arr_answers[i];
                }

                //주관식 답  - TZ_SULRESULTP 에  INSERT
                if (ss_sultype.equals(SulmunAllBean.SUBJECT_QUESTION)) {
                    InsertTZ_sulresultp(connMgr, v_subj, v_grcode, v_gyear, v_subjseq, v_sulpapernum, v_userid, arr_sulnums[i], ss_distcode, ss_sultype, arr_answers[i], v_luserid, v_gubun);

                }
 
                // 객관식 답
                int i_arr_answers = 0;
                try {
                    i_arr_answers = Integer.parseInt(arr_answers[i]);
                } catch (Exception e) {
                }

                if (i_arr_answers != 0) {

                    // 문제,보기당 보기점수
                    int ss_selpoint = 0;

                    sql = " select selpoint from TZ_SULSEL where subj='ALL' AND GRCODE=? AND SULNUM=? AND SELNUM=?  ";
                    pstmt1 = connMgr.prepareStatement(sql);
                    pstmt1.setString(1, v_grcode);
                    pstmt1.setInt(2, Integer.parseInt(arr_sulnums[i]));
                    pstmt1.setInt(3, i_arr_answers);
                    rs = pstmt1.executeQuery();

                    if (rs.next()) {
                        ss_selpoint = rs.getInt("selpoint");
                    }
                    if (pstmt1 != null) {
                        try {
                            pstmt1.close();
                        } catch (Exception e) {
                        }
                    }
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (Exception e) {
                        }
                    }

                    //System.out.println("SULNUM = " + Integer.parseInt(arr_sulnums[i]) + " : SELNUM = " + i_arr_answers + " : selpoint = " + ss_selpoint);
                    // 계산
                    if(!ss_sultype.equals(SulmunAllBean.SUBJECT_QUESTION)){
                    	
                    	if (ss_distcode.equals("1")) {
                    		i_code1 += ss_selpoint;
                    		i_code1_cnt++;
                    	}
                    	if (ss_distcode.equals("2")) {
                    		i_code2 += ss_selpoint;
                    		i_code2_cnt++;
                    	}
                    	if (ss_distcode.equals("3")) {
                    		i_code3 += ss_selpoint;
                    		i_code3_cnt++;
                    	}
                    	if (ss_distcode.equals("4")) {
                    		i_code4 += ss_selpoint;
                    		i_code4_cnt++;
                    	}
                    	if (ss_distcode.equals("5")) {
                    		i_code5 += ss_selpoint;
                    		i_code5_cnt++;
                    	}
                    	if (ss_distcode.equals("6")) {
                    		i_code6 += ss_selpoint;
                    		i_code6_cnt++;
                    	}
                    	if (ss_distcode.equals("7")) {
                    		i_code7 += ss_selpoint;
                    		i_code7_cnt++;
                    	}
                    }
                }

            }

            if (i_code1_cnt != 0)
                i_code1_avg = i_code1 / (double) i_code1_cnt;
            if (i_code2_cnt != 0)
                i_code2_avg = i_code2 / (double) i_code2_cnt;
            if (i_code3_cnt != 0)
                i_code3_avg = i_code3 / (double) i_code3_cnt;
            if (i_code4_cnt != 0)
                i_code4_avg = i_code4 / (double) i_code4_cnt;
            if (i_code5_cnt != 0)
                i_code5_avg = i_code5 / (double) i_code5_cnt;
            if (i_code6_cnt != 0)
                i_code6_avg = i_code6 / (double) i_code6_cnt;
            if (i_code7_cnt != 0)
                i_code7_avg = i_code7 / (double) i_code7_cnt;

            //System.out.println("설문 내용 = " + v_subj + " : " + v_grcode + " : " + v_gyear + " : " + v_sulpapernum + " : " + v_userid);
            if (isOk == 2) {

                sql1 = "select userid from TZ_SULEACH";
                sql1 += " where subj = ? and grcode = ? and year = ? and subjseq = ? and sulpapernum = ?  and  userid = ? and gubun = ? ";
                pstmt1 = connMgr.prepareStatement(sql1);

                pstmt1.setString(1, v_subj);
                pstmt1.setString(2, v_grcode);
                pstmt1.setString(3, v_gyear);
                pstmt1.setString(4, v_subjseq);
                pstmt1.setInt(5, v_sulpapernum);
                pstmt1.setString(6, v_userid);
                pstmt1.setString(7, v_gubun);

                try {
                    rs = pstmt1.executeQuery();
                    if (!rs.next()) { //     과거에 등록된 userid 를 확인하고 없을 경우에만 등록
                        isOk1 = InsertTZ_suleach(connMgr, v_subj, v_grcode, v_gyear, v_subjseq, v_sulpapernum, v_userid, v_sulnums, v_answers, v_luserid, v_gubun, i_code1, i_code2, i_code3, i_code4, i_code5, i_code6, i_code7, i_code1_avg, i_code2_avg,
                                i_code3_avg, i_code4_avg, i_code5_avg, i_code6_avg, i_code7_avg, s_distcode10);
                        //System.out.println("점수 = " +i_code1 + " : " + i_code1_avg + " : " + v_sulnums);
                        
                        if("N000241".equals(v_grcode)){
                        	String tmp = box.getString("p_subj");
                        	box.put("p_year", box.getString("p_gyear"));
                        	box.put("p_subj", box.getString("s_subj"));
                        	isOkSt = updateUserGraduated(connMgr, v_subj, v_gyear, v_subjseq, v_userid);
	            			box.put("p_subj", tmp);
                        }
                    }

                } catch (Exception e) {
                } finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (Exception e) {
                        }
                    }
                }

            }

        } catch (Exception ex) {
            isOk1 = 0;
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (isOk1 > 0) {
                connMgr.commit();
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
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

        return isOk * isOk1;
    }

    /**
     * 설문기간 확인
     * 
     * @param box receive from the form object and session
     * @return int
     */
    @SuppressWarnings("deprecation")
    public int getSulmunGigan(RequestBox box) throws Exception {

        SulmunSubjPaperBean bean = null;
        DataBox dbox0 = null;

        int v_update = 0;

        try {
            bean = new SulmunSubjPaperBean();
            dbox0 = bean.getPaperData(box);

            if (dbox0.getInt("d_sulpapernum") > 0) {

                long v_fstart = Long.parseLong(dbox0.getString("d_sulstart"));
                long v_fend = Long.parseLong(dbox0.getString("d_sulend"));

                java.util.Date d_now = new java.util.Date();
                String d_year = String.valueOf(d_now.getYear() + 1900);
                String d_month = String.valueOf(d_now.getMonth() + 1);
                String d_day = String.valueOf(d_now.getDate());

                if (d_month.length() == 1) {
                    d_month = "0" + d_month;
                }
                if (d_day.length() == 1) {
                    d_day = "0" + d_day;
                }
                long v_now = Long.parseLong(d_year + d_month + d_day);

                if (v_fstart > v_now) {
                    v_update = 1; //설문 전
                } else if (v_now > v_fend) {
                    v_update = 3; //설문 완료
                } else if (v_fstart <= v_now && v_now < v_fend) {
                    v_update = 2; //설문 중
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }

        return v_update;

    }

    /**
     * 과정 설문 등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int InsertTZ_suleach(DBConnectionManager connMgr, String p_subj, String p_grcode, String p_gyear, String p_subjseq, int p_sulpapernum, String p_userid, String p_sulnums, String p_answers, String p_luserid, String p_gubun, int i_code1,
            int i_code2, int i_code3, int i_code4, int i_code5, int i_code6, int i_code7, double i_code1_avg, double i_code2_avg, double i_code3_avg, double i_code4_avg, double i_code5_avg, double i_code6_avg, double i_code7_avg, String s_distcode10)
            throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_SULEACH table
            sql = "insert into TZ_SULEACH ";
            sql += " (subj,  grcode,   year,    subjseq, sulpapernum, ";
            sql += "  userid,  sulnums, answers,  luserid, ldate,     gubun, ";
            sql += "  distcode1, distcode2, distcode3, distcode4, distcode5, distcode6, distcode7,  ";
            sql += "  distcode1_avg, distcode2_avg, distcode3_avg , distcode4_avg, distcode5_avg, distcode6_avg , distcode7_avg, distcode10 ";
            sql += " )values( ";
            sql += "  ?,	?,	?,	?,	?, ";
            sql += "  ?,	?,	?,	?,	?,	?,";
            sql += "  ?,	?,	?,	?,	?,	?,	?, ";
            sql += "  ?,	?,	?,	?,	?,	?,	? ,	? ) ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_grcode);
            pstmt.setString(3, p_gyear);
            pstmt.setString(4, p_subjseq);
            pstmt.setInt(5, p_sulpapernum);
            pstmt.setString(6, p_userid);
            pstmt.setString(7, p_sulnums);
            pstmt.setString(8, p_answers);
            pstmt.setString(9, p_luserid);
            pstmt.setString(10, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(11, p_gubun);
            pstmt.setInt(12, i_code1);
            pstmt.setInt(13, i_code2);
            pstmt.setInt(14, i_code3);
            pstmt.setInt(15, i_code4);
            pstmt.setInt(16, i_code5);
            pstmt.setInt(17, i_code6);
            pstmt.setInt(18, i_code7);
            pstmt.setDouble(19, i_code1_avg);
            pstmt.setDouble(20, i_code2_avg);
            pstmt.setDouble(21, i_code3_avg);
            pstmt.setDouble(22, i_code4_avg);
            pstmt.setDouble(23, i_code5_avg);
            pstmt.setDouble(24, i_code6_avg);
            pstmt.setDouble(25, i_code7_avg);
            pstmt.setString(26, s_distcode10);

            isOk = pstmt.executeUpdate();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 과정 설문 등록(주관식)
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int InsertTZ_sulresultp(DBConnectionManager connMgr, String p_subj, String p_grcode, String p_gyear, String p_subjseq, int p_sulpapernum, String p_userid, String p_sulnum, String p_distcode, String p_sultype, String p_answer, String p_luserid,
            String p_gubun) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        
        try {
            //insert TZ_SULEACH table
            sql = "insert into TZ_SULRESULTP (subj, gubun , grcode, year, subjseq, sulpapernum, userid, ";
            sql += "                           sulnum, distcode, sultype, answer,  luserid, ldate)       ";
            sql += "                    values( ?,?,?,?,?,  ?,?,?,?,?, ?,?,? )                           ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_gubun);
            pstmt.setString(3, p_grcode);
            pstmt.setString(4, p_gyear);
            pstmt.setString(5, p_subjseq);
            pstmt.setInt(6, p_sulpapernum);
            pstmt.setString(7, p_userid);
            pstmt.setString(8, p_sulnum);
            pstmt.setString(9, p_distcode);
            pstmt.setString(10, p_sultype);
            pstmt.setString(11, p_answer);
            pstmt.setString(12, p_luserid);
            pstmt.setString(13, FormatDate.getDate("yyyyMMddHHmmss"));

            isOk = pstmt.executeUpdate();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 설문 대상자 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 설문 대상자
     */
    public DataBox SelectUserPaperResult(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";

        String v_grcode = box.getString("p_grcode");
        String v_gubun = box.getStringDefault("p_subj", "TARGET");
        String v_subj = box.getString("s_subj");
        String v_gyear = box.getString("p_gyear");
        String v_subjseq = box.getStringDefault("p_subjseq", "0001");
        String v_userid = box.getString("p_userid");
        int v_sulpapernum = box.getInt("p_sulpapernum");

        try {
            connMgr = new DBConnectionManager();

            sql = "select sulnums, answers    ";
            sql += "  from tz_suleach ";
            sql += " where grcode = " + SQLString.Format(v_grcode);
            sql += "   and subj   = " + SQLString.Format(v_subj);
            sql += "   and gubun   = " + SQLString.Format(v_gubun);
            sql += "   and year   = " + SQLString.Format(v_gyear);
            sql += "   and subjseq   = " + SQLString.Format(v_subjseq);
            sql += "   and sulpapernum   = " + SQLString.Format(v_sulpapernum);
            sql += "   and userid   = " + SQLString.Format(v_userid);
            System.out.println("SelectUserPaperResult.sql = " + sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
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
        return dbox;
    }

    /**
     * 설문 대상자 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 설문 대상자
     */
    public DataBox selectSulmunUser(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";

        String v_userid = box.getString("p_userid");
        try {
            connMgr = new DBConnectionManager();

            sql += "select       b.comp  asgn,  get_compnm(b.comp,2,4)       asgnnm, ";
            sql += "	   	  b.jikup,       get_jikupnm(b.jikup, b.comp) jikupnm, ";
            sql += "	   	  b.cono,     b.name ";
            sql += "  from tz_member   b ";
            sql += "   where b.userid    = " + SQLString.Format(v_userid);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
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
        return dbox;
    }

    /**
     * 교육그룹코드 구하기
     * 
     * @param box receive from the form object and session
     * @return String
     */
    public String getGrcode(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq) throws Exception {
        String v_grcode = "";
        ListSet ls = null;
        String sql = "";
        try {
            sql = "select grcode ";
            sql += "  from tz_subjseq  ";
            sql += " where subj    = " + SQLString.Format(p_subj);
            sql += "   and year    = " + SQLString.Format(p_year);
            sql += "   and subjseq = " + SQLString.Format(p_subjseq);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_grcode = ls.getString("grcode");
            }
            ls.close();
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
        return v_grcode;
    }

    /**
     * 과정 설문 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     **/
    public ArrayList<DataBox> selectEducationSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        DataBox dbox1 = null;
        String v_grcode = box.getSession("tem_grcode");
        String sql1 = "";
        String v_user_id = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = " select (SELECT sa.classname from tz_subjatt sa, tz_subj s where s.upperclass = sa.upperclass and sa.middleclass='000' and s.subj = a.subj) classname, a.subjnm, a.grcode, a.edustart, a.eduend, a.subj, a.year, a.subjseq, b.userid, b.tstep, a.sulpapernum, c.sulpapernm, c.progress, c.sulnums,   \n"
                    + " (select isonoff from tz_subj where subj=a.subj) isonoff,   \n"
                    + " (select count(userid) from tz_suleach where subj=a.subj and year=a.year and subjseq=a.subjseq and sulpapernum = a.sulpapernum and userid='"
                    + v_user_id
                    + "') eachcnt   \n" + " from tz_subjseq a, tz_student b, tz_sulpaper c   \n" + " where a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq and a.sulpapernum = c.sulpapernum and b.userid='" + v_user_id + "'     \n"
                    //+ " and to_char(sysdate,'yyyymmddhh24') between a.edustart and a.eduend "
                    + " 	and c.subj='ALL' " + "	and c.grcode=  " + SQLString.Format(v_grcode) + "	and a.grcode = " + SQLString.Format(v_grcode)
                    //설문기간 제약
                    + "	and (a.sulstartdate1 is null or a.sulstartdate1 = '' or " + "		(a.sulstartdate1 is not null and " + "		 TO_CHAR(sysdate, 'YYYY-MM-DD') between a.sulstartdate1 and a.sulenddate1) " + "	) ";

            sql1 += " union "
                    + "select (SELECT sa.classname from tz_subjatt sa, tz_subj s where s.upperclass = sa.upperclass and sa.middleclass='000' and s.subj = a.subj) classname, a.subjnm, a.grcode, a.edustart, a.eduend, a.subj, a.year, a.subjseq, b.userid, b.tstep, a.sulpapernum2, c.sulpapernm, c.progress, c.sulnums,   \n"
                    + " (select isonoff from tz_subj where subj=a.subj) isonoff,   \n" + " (select count(userid) from tz_suleach where subj=a.subj and year=a.year and subjseq=a.subjseq and sulpapernum = a.sulpapernum2 and userid='" + v_user_id
                    + "') eachcnt   \n" + " from tz_subjseq a, tz_student b, tz_sulpaper c   \n" + " where a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq and a.sulpapernum2 = c.sulpapernum and b.userid='" + v_user_id + "'     \n"
                    + " 	and c.subj='ALL' " + "	and c.grcode=  " + SQLString.Format(v_grcode) + "	and a.grcode = " + SQLString.Format(v_grcode)
                    //설문기간 제약
                    + "    and (a.sulstartdate2 is null or a.sulstartdate2 = '' or " + "		(a.sulstartdate2 is not null and " + "		 TO_CHAR(sysdate,'YYYY-MM-DD') between a.sulstartdate2 and a.sulenddate2) " + "	) "

                    + " order by subj,year,subjseq   \n";

            ls1 = connMgr.executeQuery(sql1);
            System.out.println("SulmunSubjUserBean 과정설문 selectEducationSubjectList:" + sql1);
            //Log.info.println("과정설문............>>>>>>"+sql1);
            while (ls1.next()) {
                dbox1 = ls1.getDataBox();

                list1.add(dbox1);
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
     * 수강중인 과정 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * 
     *         public ArrayList selectEducationSubjectList(RequestBox box)
     *         throws Exception { DBConnectionManager connMgr = null; ListSet
     *         ls1 = null; ArrayList list1 = null; ArrayList list2 = null;
     *         DataBox dbox1 = null; DataBox dbox2 = null; String sql1 = "";
     *         String v_subj = ""; //과정 String v_year = ""; //년도 String
     *         v_subjseq = ""; //과정차수 String v_user_id =
     *         box.getSession("userid");
     * 
     *         try { connMgr = new DBConnectionManager(); list1 = new
     *         ArrayList(); //select
     *         upperclass,isonoff,course,cyear,courseseq,coursenm
     *         ,subj,year,subjseq,subjnm,edustart,
     *         //eduend,eduurl,classnm,subjtarget sql1 ="select A.scupperclass,A.isonoff,A.course,A.cyear,A.courseseq,A.coursenm,A.subj,A.year,A.subjseq,A.subjseqgr, "
     *         ; sql1+=
     *         "A.subjnm,A.edustart,A.eduend,A.eduurl,A.subjtarget, A.isoutsourcing "
     *         ; //sql1+="(select classnm from TZ_CLASS where A.subj=subj and A.year=year and A.subjseq=subjseq) classnm "
     *         ; sql1+= "from VZ_SCSUBJSEQ A,TZ_STUDENT B "; sql1+=
     *         "where A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq and B.userid="
     *         +SQLString.Format(v_user_id); // sql1+=
     *         " and B.isgraduated='N' "; sql1+=
     *         " and to_char(sysdate,'YYYYMMDDHH24') between A.edustart and A.eduend "
     *         ; // sql1+= " and  A.isoutsourcing = 'N'"; sql1+=" order by A.course,A.cyear,A.courseseq,A.subj,A.year,A.subjseq,A.edustart,A.eduend "
     *         ;
     * 
     *         Log.info.println("sql1============>"+sql1); ls1 =
     *         connMgr.executeQuery(sql1);
     * 
     *         while (ls1.next()) { dbox1 = ls1.getDataBox();
     * 
     *         box.put("p_subj", dbox1.getString("d_subj")); box.put("p_year",
     *         dbox1.getString("d_year")); box.put("p_subjseq",
     *         dbox1.getString("d_subjseq")); list2 =
     *         this.SelectUserList(connMgr, box); if (list2.size() > 0) { dbox2
     *         = (DataBox)list2.get(0); } else { dbox2 = new
     *         DataBox("resoponsebox"); } dbox2.put("d_subjnm",
     *         dbox1.getString("d_subjnm")); dbox2.put("d_isonoff",
     *         dbox1.getString("d_isonoff"));
     * 
     *         /* ========== 과정설문 응시여부 ========== int suldata =
     *         this.getUserData(connMgr, box);
     *         dbox2.put("d_suldata",String.valueOf(suldata));
     * 
     *         /* ========== 권장진도율, 자기진도율 시작 ========== SubjGongAdminBean sbean
     *         = new SubjGongAdminBean();
     * 
     *         String promotion = sbean.getPromotion(box);
     *         dbox2.put("p_promotion",promotion); String progress =
     *         sbean.getProgress(box); dbox2.put("p_progress",progress);
     * 
     *         System.out.println("dbox"+dbox2.getInt("d_progress"));
     *         System.out.println("progress"+progress);
     * 
     *         list1.add(dbox2);
     * 
     *         } } catch (Exception ex) { ErrorManager.getErrorStackTrace(ex,
     *         box, sql1); throw new Exception("sql1 = " + sql1 + "\r\n" +
     *         ex.getMessage()); } finally { if(ls1 != null) { try {
     *         ls1.close(); }catch (Exception e) {} } if(connMgr != null) { try
     *         { connMgr.freeConnection(); }catch (Exception e10) {} } } return
     *         list1; }
     */

    /**
     * 사용자 해당과정리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 해당과정리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectUserList(DBConnectionManager connMgr, RequestBox box) throws Exception {

        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;
        String sql = "";
        try {
            //        String s_userid		= box.getSession("userid");
            String v_subj = box.getString("p_subj");
            System.out.println("v_subj" + v_subj);
            String v_year = box.getString("p_year");
            System.out.println("v_year" + v_year);
            String v_subjseq = box.getString("p_subjseq");
            System.out.println("v_subjseq" + v_subjseq);

            list = new ArrayList();

            sql = "select a.grcode,       a.subj,   a.subjseq,      ";
            sql += "       a.sulpapernum,  a.sulpapernm, a.year, ";
            sql += "       a.totcnt,       a.sulnums, a.sulmailing, a.sulstart, a.sulend, a.progress ";
            sql += "  from tz_sulpaper a ";
            sql += "   where a.subj   = " + SQLString.Format(v_subj);
            sql += "   and a.year   = " + SQLString.Format(v_year);
            sql += "   and a.subjseq   = " + SQLString.Format(v_subjseq);
            sql += "   and rownum <= 1 ";
            sql += " order by a.subj, a.sulpapernum";

            ls = connMgr.executeQuery(sql);
            System.out.println(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
            ls.close();
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
        }
        return list;
    }

    /**
     * 마이클래스 과정설문지리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 해당과정리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectUserList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;
        String sql = "";

        try {
            String s_grcode = box.getSession("tem_grcode"); //세션의 교육그룹(기관코드)정보
            String s_userid = box.getSession("userid");
            String v_subj = box.getString("p_subj");
            System.out.println("v_subj=" + v_subj);
            String v_year = box.getString("p_year");
            System.out.println("v_year=" + v_year);
            String v_subjseq = box.getString("p_subjseq");
            System.out.println("v_subjseq=" + v_subjseq);

            connMgr = new DBConnectionManager();

            list = new ArrayList();

            //sql = "select a.grcode,       a.subj,   a.subjseq,      ";
            //sql+= "       a.sulpapernum,  a.sulpapernm, a.year, ";
            //sql+= "       a.totcnt,       a.sulnums, a.sulmailing, a.sulstart, a.sulend, a.progress ";
            //sql+= "  from tz_sulpaper a ";
            //sql+= "   where a.subj   = " + SQLString.Format(v_subj);
            //sql+= "   and a.year   = " + SQLString.Format(v_year);
            //sql+= "   and a.subjseq   = " + SQLString.Format(v_subjseq);
            //sql+= "   and rownum <= 1 ";
            //sql+= " order by a.subj, a.sulpapernum desc ";

            sql = " select  a.subjnm, a.grcode, a.edustart, a.eduend, a.subj, a.year, a.subjseq,   \n";
            sql += " b.userid, b.tstep, a.sulpapernum,c.sulpapernm, c.progress, c.sulnums,          \n";
            sql += "      (select count(userid) from tz_suleach                                     \n";
            sql += "       where subj=a.subj and year=a.year and subjseq=a.subjseq                  \n";
            sql += "       and sulpapernum = a.sulpapernum and userid=b.userid) eachcnt, a.sulstartdate1 sulstartdate, a.sulenddate1 sulenddate,               \n";
            sql += "       CASE WHEN    a.sulstartdate1 IS NULL ";
            sql += "                 OR a.sulstartdate1 = '' ";
            sql += "                 OR (      a.sulstartdate1 IS NOT NULL ";
            sql += "                       AND TO_CHAR (SYSDATE, 'YYYY-MM-DD') BETWEEN a.sulstartdate1 AND a.sulenddate1  ";
            sql += "          ) then 'Y' else 'N' end dispyn ";
            sql += " from TZ_SUBJSEQ a, TZ_STUDENT b, tz_sulpaper c                                 \n";
            sql += " where A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq    \n";
            sql += "   and a.sulpapernum = c.sulpapernum  and B.userid=" + SQLString.Format(s_userid) + "  \n";
            sql += "   and c.subj='ALL' and c.grcode= " + SQLString.Format(s_grcode) + "   \n";
            //sql+= " and to_char(sysdate,'YYYYMMDDHH24') between A.edustart and A.eduend  \n";
            sql += " and a.subj=" + SQLString.Format(v_subj) + " and a.year=" + SQLString.Format(v_year) + " and a.subjseq=" + SQLString.Format(v_subjseq) + " ";

            //설문기간 제약
            //sql+= "    and (a.sulstartdate1 is null or a.sulstartdate1 = '' or ";
            //sql+= "		(a.sulstartdate1 is not null and ";
            //sql+= "		 TO_CHAR(sysdate, 'YYYY-MM-DD') between a.sulstartdate1 and a.sulenddate1) ";
            //sql+= "	) ";

            sql += " union ";

            sql += " select  a.subjnm, a.grcode, a.edustart, a.eduend, a.subj, a.year, a.subjseq,   \n";
            sql += " b.userid, b.tstep, a.sulpapernum2,c.sulpapernm, c.progress, c.sulnums,          \n";
            sql += "      (select count(userid) from tz_suleach                                     \n";
            sql += "       where subj=a.subj and year=a.year and subjseq=a.subjseq                  \n";
            sql += "       and sulpapernum = a.sulpapernum2 and userid=b.userid) eachcnt, a.sulstartdate2 sulstartdate, a.sulenddate2 sulenddate,               \n";
            sql += "       CASE WHEN    a.sulstartdate2 IS NULL ";
            sql += "                 OR a.sulstartdate2 = '' ";
            sql += "                 OR (      a.sulstartdate2 IS NOT NULL ";
            sql += "                       AND TO_CHAR (SYSDATE, 'YYYY-MM-DD') BETWEEN a.sulstartdate2 AND a.sulenddate2  ";
            sql += "          ) then 'Y' else 'N' end dispyn ";
            sql += " from TZ_SUBJSEQ a, TZ_STUDENT b, tz_sulpaper c                                 \n";
            sql += " where A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq    \n";
            sql += "   and a.sulpapernum2 = c.sulpapernum  and B.userid=" + SQLString.Format(s_userid) + "  \n";
            sql += "   and c.subj='ALL' and c.grcode= " + SQLString.Format(s_grcode) + "   \n";
            //sql+= " and to_char(sysdate,'YYYYMMDDHH24') between A.edustart and A.eduend  \n";
            sql += " and a.subj=" + SQLString.Format(v_subj) + " and a.year=" + SQLString.Format(v_year) + " and a.subjseq=" + SQLString.Format(v_subjseq) + " ";

            //설문기간 제약
            //sql+= "    and (a.sulstartdate2 is null or a.sulstartdate2 = '' or ";
            //sql+= "		(a.sulstartdate2 is not null and ";
            //sql+= "		TO_CHAR(sysdate, 'YYYY-MM-DD') between a.sulstartdate2 and a.sulenddate2) ";
            //sql+= "	) ";

            sql += " order by subj,year,subjseq,edustart,eduend     \n";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
            ls.close();
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

    /**
     * 사용자 해당과정리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 해당과정리스트
     */
    public int getUserData(DBConnectionManager connMgr, RequestBox box) throws Exception {

        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        int v_research = 0;

        try {
            String s_userid = box.getSession("userid");
            String v_grcode = box.getSession("tem_grcode");
            String v_subj = box.getString("s_subj");
            String v_gubun = box.getString("p_subj");
            String v_year = box.getString("p_year");
            String v_subjseq = box.getString("p_subjseq");

            sql = "select count(a.answers) researchcnt  ";
            sql += "  from tz_suleach a ";
            sql += "   where a.subj   = " + SQLString.Format(v_subj);
            sql += "   and a.year   = " + SQLString.Format(v_year);
            sql += "   and a.subjseq   = " + SQLString.Format(v_subjseq);
            sql += "   and a.userid   = " + SQLString.Format(s_userid);
            sql += "   and a.gubun   = " + SQLString.Format(v_gubun);
            sql += "   and a.grcode  = " + SQLString.Format(v_grcode);

            //System.out.println("getUserData(parm 2).sql = " + sql);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();

                v_research = dbox.getInt("d_researchcnt");
            }
            ls.close();
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
        }
        return v_research;
    }

    /**
     * 사용자 해당과정리스트 과정 차수별 사전 설문 건수
     * 
     * @param box receive from the form object and session
     * @return ArrayList 해당과정리스트
     */
    public int getUserData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        int v_research = 0;

        try {
            String s_userid = box.getSession("userid");
            String v_grcode = box.getSession("tem_grcode");
            String v_subj = box.getString("s_subj");
            String v_gubun = box.getString("p_subj");
            String v_year = box.getString("p_year");
            String v_subjseq = box.getString("p_subjseq");

            connMgr = new DBConnectionManager();

            sql = "select count(a.answers) researchcnt  ";
            sql += "  from tz_suleach a, tz_subjseq b "; //2009.12.31 tz_subjseq 추가
            sql += "   where a.subj   = " + SQLString.Format(v_subj);
            sql += "   and a.year   = " + SQLString.Format(v_year);
            sql += "   and a.subjseq   = " + SQLString.Format(v_subjseq);
            sql += "   and a.userid   = " + SQLString.Format(s_userid);
            sql += "   and a.gubun   = " + SQLString.Format(v_gubun);
            sql += "   and a.grcode  = " + SQLString.Format(v_grcode);
            sql += "   and a.subj = b.subj ";
            sql += "   and a.year = b.year ";
            sql += "   and a.subjseq = b.subjseq ";
            sql += "   and a.sulpapernum = b.sulpapernum ";

            //System.out.println("getUserData(parm 1).sql = " + sql);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();

                v_research = dbox.getInt("d_researchcnt");
            }
            ls.close();
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
        return v_research;
    }

    /**
     * 해당설문이 있는지 체크.
     * 
     * @param box receive from the form object and session
     * @return ArrayList 해당과정리스트
     */
    public int getSubjSulmunPaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        int v_research = 0;

        try {
            String v_grcode = box.getSession("tem_grcode");
            String v_subj = box.getString("s_subj");
            String v_year = box.getString("p_year");
            String v_subjseq = box.getString("p_subjseq");

            connMgr = new DBConnectionManager();

            sql = "select sulpapernum researchcnt  ";
            sql += "  from tz_subjseq a ";
            sql += "   where a.subj   = " + SQLString.Format(v_subj);
            sql += "   and a.year   = " + SQLString.Format(v_year);
            sql += "   and a.subjseq   = " + SQLString.Format(v_subjseq);
            sql += "   and a.grcode   = " + SQLString.Format(v_grcode);

            //System.out.println("getUserData(parm 1).sql = " + sql);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();

                v_research = dbox.getInt("d_researchcnt");
            }
            ls.close();
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
        return v_research;
    }

    /**
     * 유저 강제 수료처리(한국예술인복지재단 만 해당_N000241) 
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int updateUserGraduated(DBConnectionManager connMgr, String p_subj, String p_gyear, String p_subjseq, String p_userid) throws Exception {
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String v_completecode = "";
        int v_maxcompletecode = 0;
        int v_maxno = 0;
        int isOk = 0;
        
        String currdate = FormatDate.getDate("yyyyMM");

        try {
        	// 수료처리
            sql = "UPDATE TZ_STUDENT SET ISGRADUATED = 'Y' WHERE SUBJ = ? AND SUBJSEQ = ? AND YEAR = ? AND USERID = ?";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_subjseq);
            pstmt.setString(3, p_gyear);
            pstmt.setString(4, p_userid);

            isOk = pstmt.executeUpdate();
            
            // 수료번호
            sql = "select 	'A' a1,count(*) as maxno ";
            sql += "from 	tz_stold ";
            sql += "where	subj = '" + p_subj + "' and ";
            sql += "			year = '" + p_gyear + "' and ";
            sql += "			subjseq = '" + p_subjseq + "' and ";
            sql += "        serno is not null";
            
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_maxcompletecode = ls.getInt("maxno");
            }

            if (v_maxcompletecode == 0) {
                v_completecode = currdate + "-" + p_subj + "-" + p_subjseq.substring(2, 4) + "-" + "0001";
            } else {
                v_maxno = Integer.valueOf(v_maxcompletecode).intValue();
                v_completecode = currdate + "-" + p_subj + "-" + p_subjseq.substring(2, 4) + "-" + new DecimalFormat("0000").format(v_maxno + 1);
            }
            
            // 수료 데이터 저장
            sql = "select \n";
    		sql += "a.SUBJ,a.YEAR,a.SUBJSEQ,b.USERID,c.name,\n";
    		sql += "b.SCORE,b.TSTEP,b.MTEST,b.FTEST,b.REPORT,\n";
    		sql += "b.ACT,b.ETC1,b.ETC2,b.AVTSTEP,b.AVMTEST,\n";
    		sql += "b.AVFTEST,b.AVREPORT,b.AVACT,b.AVETC1,b.AVETC2,\n";
    		sql += "b.ISGRADUATED,'N',b.EDUSTART,b.EDUEND,'','','lee1',to_char(sysdate, 'YYYYMMDDHH24MISS'),nvl(d.userid,'') as stold_name \n";
    		sql += "from tz_subjseq a\n";
    		sql += "left join tz_student b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq and b.userid='" + p_userid + "' \n";
    		sql += "left join tz_member c on b.userid=c.userid \n";
    		sql += "left join tz_stold d on b.subj=d.subj and b.year=d.year and b.subjseq=d.subjseq and b.userid=d.userid \n";
    		sql += "where a.subj='" + p_subj + "' and a.year='" + p_gyear + "' and a.subjseq='" + p_subjseq + "'";
    		
    		ls = connMgr.executeQuery(sql);
    		if (ls.next()) {
    			String stold_Bool = ls.getString("stold_name");
    			if (stold_Bool.equals("")) {
    				sql = "insert into tz_stold values (?,?,?,?,?,?,?,?,?,?, ";
    				sql += "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?,?,?,?,?,?)";
    				pstmt = connMgr.prepareStatement(sql);
    				
    				int index = 1;
    				pstmt.setString(index++, ls.getString("SUBJ"));
    				pstmt.setString(index++, ls.getString("YEAR"));
    				pstmt.setString(index++, ls.getString("SUBJSEQ"));
    				pstmt.setString(index++, ls.getString("USERID"));
    				pstmt.setString(index++, ls.getString("name")); // 5
    				pstmt.setString(index++, "");
    				pstmt.setString(index++, "");
    				pstmt.setString(index++, "");
    				pstmt.setString(index++, "");
    				pstmt.setString(index++, ls.getString("SCORE")); //10
    				pstmt.setString(index++, ls.getString("TSTEP"));
    				pstmt.setString(index++, ls.getString("MTEST"));
    				pstmt.setString(index++, ls.getString("FTEST"));
    				pstmt.setString(index++, ls.getString("REPORT"));
    				pstmt.setString(index++, ls.getString("ACT")); // 15
    				pstmt.setString(index++, ls.getString("ETC1"));
    				pstmt.setString(index++, ls.getString("ETC2"));
    				pstmt.setString(index++, ls.getString("AVTSTEP"));
    				pstmt.setString(index++, ls.getString("AVMTEST"));
    				pstmt.setString(index++, ls.getString("AVFTEST")); //20
    				pstmt.setString(index++, ls.getString("AVREPORT"));
    				pstmt.setString(index++, ls.getString("AVACT"));
    				pstmt.setString(index++, ls.getString("AVETC1"));
    				pstmt.setString(index++, ls.getString("AVETC2"));
    				pstmt.setString(index++, ls.getString("ISGRADUATED")); // 25
    				pstmt.setString(index++, "N");
    				pstmt.setString(index++, ls.getString("EDUSTART"));
    				pstmt.setString(index++, FormatDate.getDate("yyyyMMdd"));
    				pstmt.setString(index++, v_completecode);
    				pstmt.setString(index++, ""); //30
    				pstmt.setString(index++, p_userid);
    				pstmt.setString(index++, "0");
    				pstmt.setString(index++, "0");
    				pstmt.setString(index++, ""); // 35
    				pstmt.setString(index++, "");
    				pstmt.setString(index++, "");
    				pstmt.setString(index++, "N");
    				pstmt.setString(index++, "");
    				pstmt.setString(index++, "N"); //40
    			} else {
    				sql = "update tz_stold set isgraduated = ?, notgraducd='',notgraduetc='', serno = ? where subj=? and year=? and subjseq=? and userid=?";
    				
    				pstmt = connMgr.prepareStatement(sql);
    				pstmt.setString(1, "Y");
    				pstmt.setString(2, v_completecode);
    				pstmt.setString(3, p_subj);
    				pstmt.setString(4, p_gyear);
    				pstmt.setString(5, p_subjseq);
    				pstmt.setString(6, p_userid);
    			}
    		}
    		ls.close();
    		
    		isOk = pstmt.executeUpdate();
    		
    		if (pstmt != null) {
    			try {
    				pstmt.close();
    			} catch (Exception e) {
    			}
    		}
    		
            sql = "update TZ_STUDENT set serno = ? where subj=? and year=? and subjseq=? and userid=?";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_completecode);
            pstmt.setString(2, p_subj);
            pstmt.setString(3, p_gyear);
            pstmt.setString(4, p_subjseq);
            pstmt.setString(5, p_userid);

            isOk = pstmt.executeUpdate();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }
}