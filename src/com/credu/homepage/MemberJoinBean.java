//**********************************************************
//1. 제      목: 회원가입
//2. 프로그램명: MemberJoinBean.java
//3. 개      요: 회원가입
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 이나연 05.12.16
//7. 수      정:
//**********************************************************
package com.credu.homepage;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormMail;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.MailSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.credu.system.MemberData;
import com.dunet.common.util.StringUtil;

//import com.daumsoft.mileage.*;

public class MemberJoinBean {

    public MemberJoinBean() {
    }

    /**
     * 회원등록
     * 
     * @param box
     *            receive from the form object and session
     * @return String
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int insertMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        // String sql = "";
        String sql1 = "";
        int isOk = 0;
        // int v_seq = 0;

        String v_handphone = box.getString("p_handphone");
        String v_resno = box.getStringDefault("p_resno", box.getSession("p_resno"));
        String v_userid = box.getString("p_userid");
        String v_pwd = box.getString("p_pwd");
        String v_name = box.getStringDefault("p_kor_name", box.getSession("name"));
        String v_eng_name = box.getString("p_eng_name");
        String v_post1 = box.getString("p_post1");
        String v_post2 = box.getString("p_post2");
        String v_home_addr1 = box.getString("p_home_addr1");
        String v_home_addr2 = box.getString("p_home_addr2");
        String v_comp_post1 = box.getString("p_comp_post1");
        String v_comp_post2 = box.getString("p_comp_post2");
        String v_comp_addr1 = box.getString("p_comp_addr1");
        String v_comp_addr2 = box.getString("p_comp_addr2");
        String v_email = box.getString("p_email");
        String v_comptext = box.getString("p_comptext");
        String v_jikup = box.getString("p_jikup");
        String v_degree = box.getString("p_degree");
        String v_hometel = box.getString("p_hometel");
        String v_comptel = box.getString("p_comptel");
        String v_ismailing = box.getString("p_ismailing");
        String v_islettering = box.getString("p_islettering");
        String v_isopening = box.getString("p_isopening");
        String v_registgubun = box.getStringDefault("p_registgubun", box.getSession("grtype"));
        String v_validation = box.getStringDefault("p_validation", "1"); // 실명인증 관련 홈페이지 가입은 == 1

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            sql1 = " insert into TZ_MEMBER(               \n";
            sql1 += " resno,			userid,			pwd,		name, 		\n";
            sql1 += " eng_name,  	post1, 			post2, 		addr, 		\n";
            sql1 += " addr2, 		comp_post1, 	comp_post2, comp_addr1, \n";
            sql1 += " comp_addr2,   	email, 			comptext, 	jikup, 		\n";
            sql1 += " degree,		handphone, 		hometel, 	comptel,	\n";
            sql1 += " ismailing,     islettering, 	isopening, 	indate,		\n";
            sql1 += " ldate , 		registgubun,	state , 	validation     ) ";
            sql1 += " values (";
            sql1 += " ?,            ?,            ?,            ?, ";
            sql1 += " ?,            ?,            ?, 		   ?, ";
            sql1 += " ?,            ?,            ?,            ?, ";
            sql1 += " ?,            ?,            ?,            ?, ";
            sql1 += " ?,            ?,            ?,            ?, ";
            sql1 += " ?,            ?,            ?,				  ";
            sql1 += " to_char(sysdate, 'YYYYMMDDHH24MISS'), to_char(sysdate, 'YYYYMMDDHH24MISS'), ? , 'Y', ?) ";

            pstmt = connMgr.prepareStatement(sql1);

            pstmt.setString(1, v_resno);
            pstmt.setString(2, v_userid);
            pstmt.setString(3, v_pwd);
            pstmt.setString(4, v_name);
            pstmt.setString(5, v_eng_name);
            pstmt.setString(6, v_post1);
            pstmt.setString(7, v_post2);
            pstmt.setString(8, v_home_addr1);
            pstmt.setString(9, v_home_addr2);
            pstmt.setString(10, v_comp_post1);
            pstmt.setString(11, v_comp_post2);
            pstmt.setString(12, v_comp_addr1);
            pstmt.setString(13, v_comp_addr2);
            pstmt.setString(14, v_email);
            pstmt.setString(15, v_comptext);
            pstmt.setString(16, v_jikup);
            pstmt.setString(17, v_degree);
            pstmt.setString(18, v_handphone);
            pstmt.setString(19, v_hometel);
            pstmt.setString(20, v_comptel);
            pstmt.setString(21, v_ismailing);
            pstmt.setString(22, v_islettering);
            pstmt.setString(23, v_isopening);
            pstmt.setString(24, v_registgubun);
            pstmt.setString(25, v_validation);

            isOk = pstmt.executeUpdate();

            if (isOk != 0) {
                box.put("isOk", String.valueOf(isOk));
                box.setSession("userid", v_userid);
                box.setSession("name", v_name);
            }

            if (isOk == 1) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql ->" + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 회원가입시 가입확인여부
     * 
     * @param box
     *            receive from the form object and session
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int checkResno(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;
        String v_resno = box.getString("p_resno");
        String v_name = box.getString("p_kor_name");
        // System.out.println(" Join >>> resno : "+ v_resno +"// name : "+ v_name);

        try {
            connMgr = new DBConnectionManager();

            sql = " select count(*) cnt \n";
            sql += " from TZ_MEMBER a                    \n";
            sql += " where a.resno = " + StringManager.makeSQL(v_resno);
            sql += " 		and state = 'Y' ";

            // System.out.println(" sql Join ==>"+ sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                is_Ok = ls.getInt(1);
                box.put("p_resno", v_resno);
                box.put("p_kor_name", v_name);
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
     * 아이디 존재 여부 검색
     * 
     * @param box
     *            receive from the form object and session
     * @return String 아이디 존재 여부 검색
     * @throws Exception
     */

    @SuppressWarnings("unchecked")
    public int checkID(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        // ArrayList list = null;
        ListSet ls = null;
        int is_Ok = 0;
        String sql = "";
        String v_userid = box.getString("p_userid");
        // System.out.println(" Bean > > >"+v_userid );

        try {
            connMgr = new DBConnectionManager();

            sql = " select count(*) cnt from TZ_MEMBER where USERID = " + StringManager.makeSQL(v_userid);

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                is_Ok = ls.getInt(1);
                box.put("p_userid", v_userid);
            }

            // System.out.println("return is_Ok = " + is_Ok);

            return is_Ok;

        } catch (Exception ex) {
            // System.out.println("return is_Ok # 1 ");
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
            // System.out.println("return is_Ok # 2 ");

        }
    }

    /**
     * 로그인 연동(세션 세팅)
     * 
     * @param box
     *            receive from the form object and session
     * @return is_Ok 1 : login ok 2 : login fail
     * @throws Exception
     */
    public int ssologin(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String sql1 = "";
        // String sql2 = "";
        // String sql3 = "";
        MemberData data = null;
        int is_Ok = 0;
        // int is_Ok2 = 0;
        String v_userid = box.getString("p_userid");
        String v_userip = box.getString("p_userip");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            // userid,resno,name,email, comp, jikup, cono
            sql1 = " select userid, resno, name, email,pwd, office_gbn, comp, jikup, cono, comptel,                               ";
            sql1 += "        jikwi, get_jikwinm(jikwi,comp) jikwinm, (deptnam,'') deptnm, get_compnm(comp,2,2) companynm, ";
            sql1 += "        NVL((select * from ( select rownum rnum,  compgubun from tz_compclass where comp=tz_member.comp ) where rnum < 2),'') compgubun         ";
            sql1 += "        jikwi, get_jikwinm(jikwi,comp) jikwinm, get_deptnm(deptnam,'') deptnm, get_compnm(comp,2,2) companynm, ";
            sql1 += "        NVL((select * from ( select rownum rnum,  compgubun from tz_compclass where comp=tz_member.comp ) where rnum < 2),'') compgubun         ";
            sql1 += "        jikwi, get_jikwinm(jikwi,comp) jikwinm, get_deptnm(deptnam,'') deptnm, get_compnm(comp,2,2) companynm, ";
            sql1 += "        nvl((select * from ( select rownum rnum,  compgubun from tz_compclass where comp=tz_member.comp ) where rnum < 2),'') compgubun         ";
            sql1 += " from TZ_MEMBER                     ";
            sql1 += " where userid = " + StringManager.makeSQL(v_userid);
            // System.out.println(" login sql1 ==>"+ sql1);
            ls = connMgr.executeQuery(sql1);

            if (ls.next()) {
                data = new MemberData();
                data.setUserid(ls.getString("userid"));
                data.setResno(ls.getString("resno"));
                data.setName(ls.getString("name"));
                data.setEmail(ls.getString("email"));
                data.setComp(ls.getString("comp"));
                data.setComptel(ls.getString("comptel"));
                data.setJikup(ls.getString("jikup"));
                data.setJikwi(ls.getString("jikwi"));
                data.setCono(ls.getString("cono"));
                data.setCompanynm(ls.getString("companynm"));
                data.setCompgubun(ls.getString("compgubun"));
                data.setOffice_gbn(ls.getString("office_gbn"));
                data.setJikwinm(ls.getString("jikwinm"));
                data.setDeptnm(ls.getString("deptnm"));

                is_Ok = 1;
            }
            ls.close();

            if (is_Ok != 0) {
                box.setSession("userid", data.getUserid());
                box.setSession("resno", data.getResno());
                box.setSession("name", data.getName());
                box.setSession("email", data.getEmail());
                box.setSession("comp", data.getComp());
                box.setSession("comptel", data.getComptel());
                box.setSession("jikup", data.getJikup());
                box.setSession("jikwi", data.getJikwi());
                box.setSession("cono", data.getCono());
                box.setSession("usergubun", data.getUsergubun());
                box.setSession("companynm", data.getCompanynm());
                box.setSession("compgubun", data.getCompgubun());
                box.setSession("jikwinm", data.getJikwinm());
                box.setSession("deptnm", data.getDeptnm());

                box.setSession("userip", v_userip);

                box.setSession("gadmin", "ZZ");
                /*
                 * // 권한 체크 sql3 = " select gadmin         "; sql3 += "   from TZ_MANAGER     "; sql3 += "  where upper(userid) = upper("+
                 * StringManager.makeSQL(v_userid) + " )"; sql3 += "    and isdeleted = 'N' "; sql3 +=
                 * "    and to_char(sysdate,'yyyymmdd') between fmon and tmon "; sql3 += "  order by gadmin asc   "; ls =
                 * connMgr.executeQuery(sql3); if (ls.next()) { box.setSession("gadmin",ls.getString("gadmin")); } else {
                 * box.setSession("gadmin","ZZ"); }
                 */
                // LOGIN DATA UPDATE
                updateLoginData(connMgr, v_userid, v_userip);

            }

            if (is_Ok != 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
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
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return is_Ok;
    }

    /**
     * 외부강의실 로그인 연동(세션 세팅)
     * 
     * @param box
     *            receive from the form object and session
     * @return is_Ok 1 : login ok 2 : login fail
     * @throws Exception
     */
    public int ssologin2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String sql1 = "";
        // String sql2 = "";
        // String sql3 = "";
        MemberData data = null;
        int is_Ok = 0;
        // int is_Ok2 = 0;
        String resno = box.getSession("resno");
        String comp = box.getSession("comp");

        try {
            connMgr = new DBConnectionManager();
            // userid,resno,name,email, comp, jikup, cono
            sql1 = " select userid, resno, name, email,pwd, office_gbn, comp, jikup, cono, comptel,                               ";
            sql1 += "        jikwi, get_jikwinm(jikwi,comp) jikwinm, get_deptnm(deptnam,'') deptnm, get_compnm(comp,2,2) companynm, ";
            sql1 += "        NVL((select * from ( select rownum rnum,  compgubun from tz_compclass where comp=tz_member.comp ) where rnum < 2),'') compgubun         ";
            sql1 += "        jikwi, get_jikwinm(jikwi,comp) jikwinm, get_deptnm(deptnam,'') deptnm, get_compnm(comp,2,2) companynm, ";
            sql1 += "        NVL((select * from ( select rownum rnum,  compgubun from tz_compclass where comp=tz_member.comp ) where rnum < 2),'') compgubun         ";
            sql1 += "        jikwi, get_jikwinm(jikwi,comp) jikwinm, get_deptnm(deptnam,'') deptnm, get_compnm(comp,2,2) companynm, ";
            sql1 += "        NVL((select * from ( select rownum rnum,  compgubun from tz_compclass where comp=tz_member.comp ) where rnum < 2 ),'') compgubun         ";

            sql1 += " from TZ_MEMBER                     ";
            sql1 += " where resno = " + StringManager.makeSQL(resno);
            sql1 += "   and comp  = " + StringManager.makeSQL(comp);
            // System.out.println(" login sql2 ==>"+ sql1);
            ls = connMgr.executeQuery(sql1);

            if (ls.next()) {
                data = new MemberData();
                data.setUserid(ls.getString("userid"));
                data.setResno(ls.getString("resno"));
                data.setName(ls.getString("name"));
                data.setEmail(ls.getString("email"));
                data.setComp(ls.getString("comp"));
                data.setComptel(ls.getString("comptel"));
                data.setJikup(ls.getString("jikup"));
                data.setJikwi(ls.getString("jikwi"));
                data.setCono(ls.getString("cono"));
                data.setCompanynm(ls.getString("companynm"));
                data.setCompgubun(ls.getString("compgubun"));
                data.setOffice_gbn(ls.getString("office_gbn"));
                data.setJikwinm(ls.getString("jikwinm"));
                data.setDeptnm(ls.getString("deptnm"));

                is_Ok = 1;
            }
            ls.close();

            if (is_Ok != 0) {
                box.setSession("userid", data.getUserid());
                box.setSession("resno", data.getResno());
                box.setSession("name", data.getName());
                box.setSession("email", data.getEmail());
                box.setSession("comp", data.getComp());
                box.setSession("comptel", data.getComptel());
                box.setSession("jikup", data.getJikup());
                box.setSession("jikwi", data.getJikwi());
                box.setSession("cono", data.getCono());
                box.setSession("usergubun", data.getUsergubun());
                box.setSession("companynm", data.getCompanynm());
                box.setSession("compgubun", data.getCompgubun());
                box.setSession("jikwinm", data.getJikwinm());
                box.setSession("deptnm", data.getDeptnm());

                // box.setSession("userip", v_userip);

                box.setSession("gadmin", "ZZ");

                // System.out.println("userid=============="+box.getSession("userid"));
                // System.out.println("resno=============="+box.getSession("resno"));
                // System.out.println("name=============="+box.getSession("name"));
                // System.out.println("email=============="+box.getSession("email"));
                // System.out.println("comp=============="+box.getSession("comp"));

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
     * Login 정보 변경 (lgcnt:로그인횟수, lglast:최종로그인시간, lgip:로그인ip
     * 
     * @param box
     *            receive from the form object and session
     * @return is_Ok 1 : success 2 : fail
     */
    public int updateLoginData(String p_userid, String p_userip) throws Exception {
        DBConnectionManager connMgr = null;
        int is_Ok = 0;
        String v_userid = p_userid;
        String v_userip = p_userip;

        try {
            connMgr = new DBConnectionManager();

            is_Ok = updateLoginData(connMgr, v_userid, v_userip);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
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
     * Login 정보 변경 (lgcnt:로그인횟수, lglast:최종로그인시간, lgip:로그인ip, lgfirst : 최초로그인
     * 
     * @param box
     *            receive from the form object and session
     * @return is_Ok 1 : success 2 : fail
     */
    public int updateLoginData(DBConnectionManager connMgr, String p_userid, String p_userip) throws Exception {
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;
        String v_userid = p_userid;
        String v_userip = p_userip;
        int cnt = 0;
        // PreparedStatement pstmt = null;

        try {

            // 최초 로그인 체크
            sql = " select count(*) cnt from tz_member   ";
            sql += " where userid = lower(" + StringManager.makeSQL(v_userid) + ")";
            sql += "   and lgfirst is null                ";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                cnt = ls.getInt("cnt");
            }

            // 정보 업데이트
            sql = " update TZ_MEMBER                       ";
            sql += " set lgcnt=lgcnt+1, lglast= to_char(sysdate, 'YYYYMMDDHH24MISS'), lgip=" + StringManager.makeSQL(v_userip);
            if (cnt > 0) {
                sql += " , lgfirst= to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            }
            sql += " where userid = lower(" + StringManager.makeSQL(v_userid) + ")";
            // System.out.println("Loindata sql ==>"+ sql);

            is_Ok = connMgr.executeUpdate(sql);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("에러메소드(LoginBean.updateLoginData) \r\n sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }

        return is_Ok;
    }

    /**
     * 비밀번호 오류 회수 증가
     * 
     * @param box
     *            receive from the form object and session
     * @return is_Ok 1 : success 2 : fail
     */
    public int updateLoginFail(String p_userid) throws Exception {
        DBConnectionManager connMgr = null;
        int is_Ok = 0;
        String v_userid = p_userid;

        try {
            connMgr = new DBConnectionManager();
            is_Ok = updateLoginFail(connMgr, v_userid);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
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
     * 비밀번호 오류 회수 증가
     * 
     * @param box
     *            receive from the form object and session
     * @return is_Ok 1 : success 2 : fail
     */
    public int updateLoginFail(DBConnectionManager connMgr, String p_userid) throws Exception {
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;
        String v_userid = p_userid;

        try {

            sql = " update TZ_MEMBER                       ";
            sql += " set lgfail = lgfail + 1 ";
            sql += " where userid = lower(" + StringManager.makeSQL(v_userid) + ")";

            // System.out.println("updateLoginFail sql ==>"+ sql);
            is_Ok = connMgr.executeUpdate(sql);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return is_Ok;
    }

    /**
     * 정보동의 체크
     * 
     * @param box
     *            receive from the form object and session
     * @return is_Ok 0 : 정보동의필요 1 : 정보동의함
     * @throws Exception
     */
    public int firstCheck(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;
        String v_userid = box.getSession("userid");
        // String v_pwd = box.getString("p_pwd");

        try {
            connMgr = new DBConnectionManager();

            sql = " select NVL(validation,'0') validation  ";
            sql += " from TZ_MEMBER                       ";
            sql += " where userid = " + StringManager.makeSQL(v_userid);
            // sql += "   and pwd    = "+ StringManager.makeSQL(v_pwd);
            ls = connMgr.executeQuery(sql);

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
     * 이메일 불러오기
     * 
     * @param box
     *            receive from the form object and session
     * @return return
     * @throws Exception
     */
    public String emailOpen(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String v_email = "";
        String v_userid = box.getString("p_userid");
        // String v_pwd = box.getString("p_pwd");

        try {
            connMgr = new DBConnectionManager();

            sql = " select email  ";
            sql += " from TZ_MEMBER                       ";
            sql += " where userid = lower(" + StringManager.makeSQL(v_userid) + ")";
            // sql += "   and pwd    = "+ StringManager.makeSQL(v_pwd);

            // System.out.println("email+++++++++++"+sql);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                v_email = ls.getString("email");
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

        return v_email;
    }

    /**
     * 정보보호 확인
     * 
     * @param box
     *            receive from the form object and session
     * @return is_Ok 1 : login ok 2 : login fail
     * @throws Exception
     */
    public int firstLogin(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int is_Ok = 0;

        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " update TZ_MEMBER set validation = ? ";
            sql += "  where userid = ?                   ";
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, "1");
            pstmt.setString(2, v_userid);
            is_Ok = pstmt.executeUpdate();
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return is_Ok;
    }

    public WholeSulData checkResearch(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls1 = null;
        String sql = "";
        String sql1 = "";
        WholeSulData data = null;

        String v_userid = box.getSession("userid");
        String v_today = FormatDate.getDate("yyyyMMdd");

        if (!v_userid.equals("")) {
            String v_comp = box.getSession("comp");
            String v_grpcomp = "";
            String v_gpm = "";
            String v_dept = "";
            if (v_comp.length() == 10) {
                v_grpcomp = StringManager.substring(v_comp, 0, 4);
                v_gpm = StringManager.substring(v_comp, 4, 6);
                v_dept = StringManager.substring(v_comp, 6, 8);
            }
            String v_jikup = box.getSession("jikup");

            try {
                connMgr = new DBConnectionManager();

                sql = " select grcode, subj, year, subjseq, sulpapernum ";
                sql += "   from  TZ_SULPAPER_WHOLE                       ";
                sql += "  where decode(grpcomp,'0000'," + StringManager.makeSQL(v_grpcomp) + ",grpcomp) =" + StringManager.makeSQL(v_grpcomp);
                sql += "    and decode(gpm,'00'," + StringManager.makeSQL(v_gpm) + ",gpm) =" + StringManager.makeSQL(v_gpm);
                sql += "    and decode(dept,'00'," + StringManager.makeSQL(v_dept) + ",dept) =" + StringManager.makeSQL(v_dept);
                sql += "    and decode(jikup,'00'," + StringManager.makeSQL(v_jikup) + ",jikup) =" + StringManager.makeSQL(v_jikup);
                sql += "    and startdate <= " + StringManager.makeSQL(v_today);
                sql += "    and enddate   >= " + StringManager.makeSQL(v_today);
                sql += "   order by startdate desc, sulpapernum desc       ";
                ls = connMgr.executeQuery(sql);

                if (ls.next()) {
                    data = new WholeSulData();

                    data.setGrcode(ls.getString("grcode"));
                    data.setSubj(ls.getString("subj"));
                    data.setYear(ls.getString("year"));
                    data.setSubjseq(ls.getString("subjseq"));
                    data.setSulpapernum(ls.getInt("sulpapernum"));

                    sql1 = " select count(*) cnt from tz_suleach ";
                    sql1 += "  where subj        =  " + StringManager.makeSQL(data.getSubj());
                    sql1 += "    and year        =  " + StringManager.makeSQL(data.getYear());
                    sql1 += "    and subjseq     =  " + StringManager.makeSQL(data.getSubjseq());
                    sql1 += "    and sulpapernum =  " + data.getSulpapernum();
                    sql1 += "    and userid      =  " + StringManager.makeSQL(v_userid);
                    sql1 += "    and f_gubun     =  'W'         ";

                    ls1 = connMgr.executeQuery(sql1);
                    if (ls1.next()) {
                        if (ls1.getInt("cnt") > 0)
                            data = null;
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
        }

        return data;
    }

    /**
     * 비밀번호 분실 폼메일 발송
     * 
     * @param box
     *            receive from the form object and session
     * @return is_Ok 1 : send ok 2 : send fail
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int sendFormMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // Connection conn = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0; // 메일발송 성공 여부

        String v_userid = box.getString("p_userid");
        String v_resno = box.getString("p_resno1") + box.getString("p_resno2");

        try {
            connMgr = new DBConnectionManager();

            sql = " select name, email, pwd, cono,ismailing,office_gbn, ";
            sql += "            decode(usergubun,'H', (select codenm from tz_code where gubun = '0038' and code = 'HMAIL') ,";
            sql += "                                           'K', (select codenm from tz_code where gubun = '0038' and code = 'KMAIL') , ";
            sql += "                                           'RH', (select codenm from tz_code where gubun = '0038' and code = 'HMAIL') , ";
            sql += "                                           'RK', (select codenm from tz_code where gubun = '0038' and code = 'KMAIL') , ";
            sql += "                                                 (select codenm from tz_code where gubun = '0038' and code = 'HMAIL') ) fromemail,";
            sql += "             decode(usergubun,'H', (select codenm from tz_code where gubun = '0038' and code = 'HNAME') ,";
            sql += "                                          'K', (select codenm from tz_code where gubun = '0038' and code = 'KNAME') , ";
            sql += "                                          'RH', (select codenm from tz_code where gubun = '0038' and code = 'HNAME') , ";
            sql += "                                          'RK', (select codenm from tz_code where gubun = '0038' and code = 'KNAME') , ";
            sql += "                                               (select codenm from tz_code where gubun = '0038' and code = 'HNAME') ) fromname ";
            sql += " from TZ_MEMBER                          ";
            sql += " where userid = " + StringManager.makeSQL(v_userid);
            sql += "   and resno  = " + StringManager.makeSQL(v_resno);
            // System.out.println("--sendFormMail-- "+sql);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {

                // //////////////////폼메일 발송
                // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                String v_sendhtml = "LossPwd.html";

                // 보내는 사람 세팅
                box.put("p_fromEmail", ls.getString("fromemail"));
                box.put("p_fromName", ls.getString("fromname"));
                box.put("p_comptel", "");

                FormMail fmail = new FormMail(v_sendhtml); // 폼메일발송인 경우

                // p_fromEmail, p_fromName, p_fromTel

                MailSet mset = new MailSet(box); // 메일 세팅 및 발송

                String v_mailTitle = "현대기아학습센터 비밀번호 안내 메일입니다.";
                // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                // String v_isMailing = ls.getString("ismailing");
                // if (v_isMailing.equals("3") || v_isMailing.equals("")) v_isMailing = "1";
                String v_isMailing = "1"; // email로만 전송

                String v_name = ls.getString("name");
                String v_pwd = ls.getString("pwd");
                String v_toEmail = ls.getString("email");
                String v_toCono = ls.getString("cono");
                String v_office_gbn = ls.getString("office_gbn");

                box.put("v_name", v_name);
                box.put("v_pwd", v_pwd);
                box.put("v_toCono", v_toCono);
                box.put("v_toEmail", v_toEmail);

                if (v_toEmail.equals("")) {
                    is_Ok = -2; // 메일주소 없음
                } else if (!v_office_gbn.equals("Y")) {
                    is_Ok = -3; // 재직자가 아님
                } else {

                    mset.setSender(fmail); // 메일보내는 사람 세팅

                    fmail.setVariable("userid", v_userid);
                    fmail.setVariable("name", ls.getString("name"));
                    fmail.setVariable("pwd", ls.getString("pwd"));

                    // System.out.println("받는 사람은 v_toEmail" + v_toEmail);

                    String v_mailContent = fmail.getNewMailContent();

                    boolean isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, v_isMailing, v_sendhtml);

                    if (isMailed)
                        is_Ok = 1; // 메일발송에 성공하면
                }
            } else {
                is_Ok = -1; // 사용자가 없음
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
     * 권한 셀렉트 박스
     * 
     * @param userid
     *            유저아이디
     * @param name
     *            셀렉트박스명
     * @param selected
     *            선택값
     * @param event
     *            이벤트명
     * @return
     * @throws Exception
     */
    public static String getAuthSelect(String userid, String name, String selected, String event) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        int cnt = 0;

        result = "  <SELECT name='"
                + name
                + "' "
                + event
                + "  style='border-style:solid;border-width: 1px 1px 1px 1px;border-color:cccccc;color:333333;font-size:9pt;background-color:none;width : 120px;height:19px;font-size:9pt;'> \n";

        try {
            connMgr = new DBConnectionManager();

            sql = " select b.gadmin gadmin, b.gadminnm gadminnm    ";
            sql += "   from tz_manager a, tz_gadmin b               ";
            sql += "  where a.gadmin    = b.gadmin                  ";
            sql += "    and a.userid    = " + StringManager.makeSQL(userid);
            sql += "    and a.isdeleted = 'N'                       ";
            sql += "    and to_char(sysdate,'yyyymmdd') between a.fmon and a.tmon ";
            sql += " order by b.gadmin asc";
            // System.out.println("sql==>" + sql);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                result += " <option value=" + ls.getString("gadmin");
                if (selected.equals(ls.getString("gadmin"))) {
                    result += " selected ";
                }
                result += ">" + ls.getString("gadminnm") + "</option> \n";
                cnt++;
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

        result += "  <option value='ZZ'";

        if (selected.equals("ZZ") || selected.equals("")) {
            result += " selected ";
        }

        result += ">학습자</option>";
        result += "  </SELECT> \n";

        if (cnt == 0)
            result = "";
        return result;
    }

    /**
     * SSO 로그인 (세션 세팅)
     * 
     * @param box
     *            receive from the form object and session
     * @return is_Ok 1 : login ok 2 : login fail
     * @throws Exception
     */
    public int loginSSO(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        // String sql3 = "";
        MemberData data = null;
        int is_Ok = 0;
        // int is_Ok2 = 0;
        String v_userid = box.getString("p_userid");
        String v_userip = box.getString("p_userip");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql1 = " select userid, resno, name, email, ";
            sql1 += "        comp, jikup,  cono,comptel, jikwi, replace(get_compnm(comp,2,2),'/','') companynm , ";
            sql2 += "        case NVL((select * from ( select rownum rnum,  deptmresno from tz_comp where deptmresno=tz_member.userid ) where rnum < 2),'') ";
            sql2 += "        		When  '' 	Then 'N' ";
            sql2 += "        		Else 'N' ";
            sql2 += "        End as isdeptmnger, ";
            sql1 += "        comp, jikup,  cono,comptel, jikwi, replace(get_compnm(comp,2,2),'/','') companynm , ";
            sql2 += "        case NVL((select * from ( select rownum rnum, deptmresno from tz_comp where deptmresno=tz_member.userid ) where rnum < 2),'') ";
            sql2 += "        		When  '' 	Then 'N' ";
            sql2 += "        		Else 'N' ";
            sql2 += "        End as isdeptmnger, ";
            sql1 += " from TZ_MEMBER                     ";
            sql1 += " where office_gbn = 'Y' and userid = " + StringManager.makeSQL(v_userid);

            ls = connMgr.executeQuery(sql1);

            if (ls.next()) {
                data = new MemberData();
                data.setUserid(ls.getString("userid"));
                data.setResno(ls.getString("resno"));
                data.setName(ls.getString("name"));
                data.setEmail(ls.getString("email"));
                data.setComp(ls.getString("comp"));
                data.setComptel(ls.getString("comptel"));
                data.setJikup(ls.getString("jikup"));
                data.setJikwi(ls.getString("jikwi"));
                data.setCono(ls.getString("cono"));
                data.setCompanynm(ls.getString("companynm"));
                data.setIsdeptmnger(ls.getString("isdeptmnger"));

                is_Ok = 1;
            }
            ls.close();

            // USERID 에 없을경우 CONO에서 체크

            if (is_Ok == 0) {
                sql2 = " select userid, resno, name, email, ";
                sql2 += "        comp, jikup,  cono, comptel, jikwi, replace(get_compnm(comp,2,2),'/',''),     ";
                sql2 += "        case NVL((select deptmresno from ( select rownum rnum, deptmresno from tz_comp where deptmresno=tz_member.userid ) where rnum < 2),'') ";
                sql2 += "        		When  '' 	Then 'N' ";
                sql2 += "        		Else 'N' ";
                sql2 += "        End as isdeptmnger, ";
                sql2 += "        comp, jikup,  cono, comptel, jikwi, replace(get_compnm(comp,2,2),'/',''),     ";
                sql2 += "        case NVL((select deptmresno from ( select rownum rnum,  deptmresno from tz_comp where deptmresno=tz_member.userid ) where rnum < 2),'')	";
                sql2 += "        		When  '' 	Then 'N' ";
                sql2 += "        		Else 'N' ";
                sql2 += "        End as isdeptmnger, ";
                sql2 += " from TZ_MEMBER                     ";
                sql2 += " where office_gbn = 'Y' and lower(cono)   = lower(" + StringManager.makeSQL(v_userid) + " )";

                ls = connMgr.executeQuery(sql2);

                if (ls.next()) {
                    data = new MemberData();
                    data.setUserid(ls.getString("userid"));
                    data.setResno(ls.getString("resno"));
                    data.setName(ls.getString("name"));
                    data.setEmail(ls.getString("email"));
                    data.setComp(ls.getString("comp"));
                    data.setComptel(ls.getString("comptel"));
                    data.setJikup(ls.getString("jikup"));
                    data.setJikwi(ls.getString("jikwi"));
                    data.setCono(ls.getString("cono"));
                    data.setCompanynm(ls.getString("companynm"));
                    data.setIsdeptmnger(ls.getString("isdeptmnger"));

                    is_Ok = 1;

                } else {
                    is_Ok = -1; // 정보 없음
                }
                ls.close();

            }

            if (is_Ok == 1) {
                box.setSession("userid", data.getUserid());
                box.setSession("resno", data.getResno());
                box.setSession("name", data.getName());
                box.setSession("email", data.getEmail());
                box.setSession("comp", data.getComp());
                box.setSession("comptel", data.getComptel());
                box.setSession("jikup", data.getJikup());
                box.setSession("jikwi", data.getJikwi());
                box.setSession("cono", data.getCono());
                box.setSession("usergubun", data.getUsergubun());
                box.setSession("companynm", data.getCompanynm());
                box.setSession("isdeptmnger", data.getIsdeptmnger());

                box.setSession("gadmin", "ZZ");

                // box.getHttpSession().setAttribute("binding.SessionListener", new SessionListener(box));

                updateLoginData(v_userid, v_userip);

                this.loginMileage(connMgr, v_userid, "ELN_LOGIN");
                connMgr.commit();

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

    public boolean loginMileage(DBConnectionManager connMgr, String v_userid, String v_mileageType) throws Exception {
        ListSet ls = null;
        String sql = "";

        // int v_point = 0;
        // String v_date = FormatDate.getDate("yyyyMM");

        // System.out.println("v_date = " + v_date);
        boolean success = false;
        try {
            // 마일리지 점수 정보
            if (v_mileageType.equals("ELN_LOGIN")) {
                sql = "select point from site_pointitem where code = 1 ";
            } else if (v_mileageType.equals("ELN_REG_REPLY")) {
                sql = "select point from tz_pointitem where code = 2 ";
            } else {
                sql = "select 0 from dual;";
            }

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                // v_point = ls.getInt(1);
            }
            ls.close();

            // MileageManager mileManager = MileageManager.getInstance();

            // 마일리지 점수 정보
            if (v_mileageType.equals("ELN_LOGIN")) {
                // success = mileManager.updateMileage(connMgr.getConnection(), v_userid, v_date , IMileageItemCode.ELN_LOGIN,v_point);
            } else if (v_mileageType.equals("ELN_REG_REPLY")) {
                // success = mileManager.updateMileage(connMgr.getConnection(), v_userid, v_date , IMileageItemCode.ELN_REG_REPLY,v_point);
            }

            // System.out.println("마일리지 success = " + success );
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
        return success;
    }

    /**
     * 유저메일전송 (TZ_USERMAIL 에 등록)
     * 
     * @param box
     *            receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertUserMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs1 = null;
        Statement stmt1 = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        // String sql2 = "";
        int isOk1 = 1;

        int v_seq = 0;
        String v_fuserid = box.getString("p_fuserid");
        String v_fusername = box.getString("p_fusername");
        String v_fuseremail = box.getString("p_fuseremail");
        String v_tuserid = box.getString("p_tuserid");
        String v_tusername = box.getString("p_tusername");
        String v_tuseremail = box.getString("p_tuseremail");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));

        // String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            stmt1 = connMgr.createStatement();

            // ---------------------- 최대값 구한다. ----------------------------
            sql = "select NVL(max(seq),0) from TZ_USERMAIL ";
            rs1 = stmt1.executeQuery(sql);
            if (rs1.next()) {
                v_seq = rs1.getInt(1) + 1;
            }
            rs1.close();

            // -------------------------------------------------------------------------
            sql1 = " insert into TZ_USERMAIL(seq, title, fuserid, fusername, fuseremail, tuserid,  ";
            sql1 += "                         tusername, tuseremail,contents, retmailing, ldate)     ";
            // sql1 += "                values (?, ?, ?, ?, ?, ?, ?, ?, empty_clob(),'N', to_char(sysdate, 'YYYYMMDDHH24MISS'))  ";
            sql1 += "                values (?, ?, ?, ?, ?, ?, ?, ?, ?,'N', to_char(sysdate, 'YYYYMMDDHH24MISS'))  ";

            int index = 1;
            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(index++, v_seq);
            pstmt1.setString(index++, v_title);
            pstmt1.setString(index++, v_fuserid);
            pstmt1.setString(index++, v_fusername);
            pstmt1.setString(index++, v_fuseremail);
            pstmt1.setString(index++, v_tuserid);
            pstmt1.setString(index++, v_tusername);
            pstmt1.setString(index++, v_tuseremail);
            pstmt1.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
            // pstmt1.setString(index++, v_contents);
            isOk1 = pstmt1.executeUpdate();
            /* 05.11.15 이나연 */
            // sql2 = "select contents from TZ_USERMAIL where seq = " + v_seq;
            // connMgr.setOracleCLOB(sql2, v_contents); // (기타 서버 경우)

            if (isOk1 > 0)
                connMgr.commit();
            else
                connMgr.rollback();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e) {
                }
            }
            if (stmt1 != null) {
                try {
                    stmt1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
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
        return isOk1;
    }

    // 북마크 했을때 처리 방안
    public static String getCompanyUrl(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String v_hostname = box.getString("p_hostname");
        ListSet ls = null;
        String sql = "";
        String v_grcode = "";
        // int cnt = 0;

        try {
            connMgr = new DBConnectionManager();
            sql = "select grcode from tz_grcode where domain like 	'%" + v_hostname + "%'";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return v_grcode;
    }
}
