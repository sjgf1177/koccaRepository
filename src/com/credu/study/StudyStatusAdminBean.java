// **********************************************************
// 1. 제 목: STUDY STATUS ADMIN BEAN
// 2. 프로그램명: StudyStatusAdminBean.java
// 3. 개 요: 학습 현황 관리자 bean
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: lyh
// **********************************************************
package com.credu.study;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import com.credu.common.GetCodenm;
import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormMail;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.MailSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.system.ManagerAdminBean;
import com.credu.system.MemberData;

/**
 * 
 * @author kocca
 * 
 */
public class StudyStatusAdminBean {
    private ConfigSet config;
    private int row;

    public StudyStatusAdminBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); // 이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 성명검색시 개인이력 동명이인 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<MemberData> selectPersonalNameList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        MemberData data = null;
        ArrayList<MemberData> list = null;
        ListSet ls = null;
        String sql = "";

        String s_userid = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");

        String v_search = box.getString("p_search");
        String v_searchtext = box.getString("p_searchtext");
        String v_memberChoice = box.getString("p_memberChoice");

        if (v_search.equals("")) {
            v_search = "name";
        }

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<MemberData>();

            if (v_memberChoice.equals("general")) { // 일반회원인 경우 이메일, 핸드폰 번호를 암호화해서 검색을 한다.
                if (v_search.equals("email") || v_search.equals("handphone")) {
                    // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY,Constants.APP_IV);
                    // v_searchtext = encryptUtil.encrypt(v_searchtext);
                    v_search = "crypto.dec('normal'," + v_search + ")";

                }

            }

            sql = " select userid, name,grcode,\n";
            sql += " case when  membergubun = 'P' then  '개인' 	\n";
            sql += " when  membergubun = 'C' then  '기업'\n";
            sql += " when  membergubun = 'U' then  '대학교'\n";
            sql += " else '-' end   as membergubunnm\n";
            sql += "   from TZ_MEMBER\n";
            sql += "  where upper(" + v_search + ") like upper('%" + v_searchtext + "%')\n";

            if (StringManager.substring(s_gadmin, 0, 1).equals("H")) { // 교육그룹관리자일경우
                sql += this.selectManagerGrcode(s_userid, s_gadmin);
            } else if (s_gadmin.equals("K2")) { // 회사관리자일경우
                sql += this.selectManagerComp(s_userid, s_gadmin);
            } else if (s_gadmin.equals("K6") || s_gadmin.equals("K7")) { // 부서관리자일경우
                sql += this.selectManagerDept(s_userid, s_gadmin);
            }

            sql += "order by name asc ";
            // System.out.println("개인별학습현황 이름검색:"+sql);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new MemberData();
                data.setUserid(ls.getString("userid"));
                data.setName(ls.getString("name"));
                data.setMembergubunnm(ls.getString("membergubunnm"));
                data.setGrcode(ls.getString("grcode"));
                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r" + ex.getMessage());
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
     * 아이디 검색시 개인이력 상세조회
     * 
     * @param box receive from the form object and session
     * @return MemberData
     */
    public MemberData selectPersonal(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        MemberData data = null;
        String v_userid = "";
        ListSet ls = null;
        String sql = "";

        String s_userid = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");
        // String v_grcode = box.getSession("tem_grcode");

        String v_search = box.getStringDefault("p_search", "userid");
        String v_searchtext = box.getString("p_searchtext");

        try {
            connMgr = new DBConnectionManager();

            sql = " select a.grcode, a.userid, a.name, a.pwd, a.eng_name, substr( a.resno, 1, 6 ) || '-' || substr( a.resno, 7, 13 ) resno, resno1, resno2 , \n";
            sql += " 		crypto.dec('normal',a.email) email, a.post1, a.post2,  a.addr, a.addr2,  crypto.dec('normal',a.hometel) hometel, a.comptel,  crypto.dec('normal',a.handphone) handphone, a.comptext,\n";
            sql += "		case when  a.membergubun = 'P' then  '개인'  when  membergubun = 'C' then  '기업'\n";
            sql += "	 	when  membergubun = 'U' then  '대학교' else '-' end   as membergubunnm,  a.state,\n";
            sql += " 		a.jikup, a.degree,  a.ismailing, a.islettering, a.isopening ,\n";
            sql += " 		a.comp_post1, a.comp_post2, a.comp_addr1, a.comp_addr2, ";
            sql += "		case a.registgubun when 'KOCCA' then '문화콘텐츠' when 'KGDI' then '게임아카데미' else '' end as registgubunnm,\n";
            sql += "		a.indate, a.ldate,	a.lgcnt, a.lglast, a.lgip, a.workfieldcd,\n"
                    + "  (SELECT CODENM FROM TZ_CODE WHERE GUBUN='0091' AND CODE=A.JOB) jobnm, job,\n"
                    + "  (SELECT CODENM FROM TZ_CODE WHERE GUBUN='0091' AND CODE=job_culture) job_culturenm, job_culture,\n";
            sql += "		case a.validation when 1 then '인증' else '미인증' end as validation, \n";
            sql += "		(SELECT CODENM FROM TZ_CODE WHERE GUBUN='0105' AND CODE = A.REGISTERROUTE) AS REGISTERROUTENM, \n";
            sql += "	(SELECT GRCODENM FROM TZ_GRCODE WHERE GRCODE = A.GRCODE) AS GRCODENM";
            sql += "    , mobile_userid";
            sql += " from TZ_MEMBER a\n";
            sql += " where 1 = 1\n";

            if (StringManager.substring(s_gadmin, 0, 1).equals("H")) { // 교육그룹관리자일경우
                sql += this.selectManagerGrcode(s_userid, s_gadmin);
            } else if (s_gadmin.equals("K2")) { // 회사관리자일경우
                sql += this.selectManagerComp(s_userid, s_gadmin);
            } else if (s_gadmin.equals("K6") || s_gadmin.equals("K7")) { // 부서관리자일경우
                sql += this.selectManagerDept(s_userid, s_gadmin);
            }

            if (v_search.equals("userid")) { // ID로 검색할때
                if (v_searchtext.indexOf("N000") != -1)
                    sql += " and upper(grcode)||upper(userid) = upper('" + v_searchtext + "')\n";
                else
                    sql += " and upper(userid) = upper('" + v_searchtext + "')\n";
            }

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {

                data = new MemberData();
                v_userid = ls.getString("userid");
                data.setUserid(v_userid);
                data.setGrcode(ls.getString("grcode"));
                data.setResno(ls.getString("resno"));
                data.setResno1(ls.getString("resno1"));
                data.setResno2(ls.getString("resno2"));
                data.setPwd(ls.getString("pwd"));
                data.setName(ls.getString("name"));
                data.setEng_name(ls.getString("eng_name"));
                data.setMembergubunnm(ls.getString("membergubunnm"));
                data.setRegistgubunnm(ls.getString("registgubunnm"));
                data.setEmail(ls.getString("email"));
                data.setPost1(ls.getString("post1"));
                data.setPost2(ls.getString("post2"));
                data.setAddr(ls.getString("addr"));
                data.setAddr2(ls.getString("addr2"));
                data.setHometel(ls.getString("hometel"));
                data.setHandphone(ls.getString("handphone"));
                data.setComptel(ls.getString("comptel"));
                data.setComptext(ls.getString("comptext"));
                data.setJikup(ls.getString("jikup"));
                data.setDegree(ls.getString("degree"));
                data.setComp_post1(ls.getString("comp_post1"));
                data.setComp_post2(ls.getString("comp_post2"));
                data.setComp_addr1(ls.getString("comp_addr1"));
                data.setComp_addr2(ls.getString("comp_addr2"));
                data.setIndate(ls.getString("indate"));
                data.setLdate(ls.getString("ldate"));
                data.setLglast(ls.getString("lglast"));
                data.setLgip(ls.getString("lgip"));
                data.setLgcnt(ls.getInt("lgcnt"));
                data.setIsmailing(ls.getString("ismailing"));
                data.setIsopening(ls.getString("isopening"));
                data.setIslettering(ls.getString("islettering"));
                data.setState(ls.getString("state"));
                data.setValidation(ls.getString("validation"));
                data.setJob(ls.getString("Job"));
                data.setJob_culture(ls.getString("Job_culture"));
                data.setJobnm(ls.getString("Jobnm"));
                data.setJob_culturenm(ls.getString("Job_culturenm"));
                data.setWorkfieldcd(ls.getString("workfieldcd"));
                data.setRegisterroutenm(ls.getString("registerroutenm"));
                data.setGrcodenm(ls.getString("grcodenm"));
                data.setMobileUserId(ls.getString("mobile_userid"));

            } else { // NullPointerException error를 막기위함
                data = new MemberData();
                v_userid = "";
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r" + ex.getMessage());
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
        return data;
    }

    /**
     * 교육그룹관리자 관리그룹해당 회사 조건쿼리
     * 
     * @param box receive from the form object and session
     * @return String 조건쿼리
     */
    public String selectManagerGrcode(String v_userid, String v_gadmin) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        int i = 0;

        try {
            connMgr = new DBConnectionManager();

            sql = " select substr(comp,0,4) comp from TZ_GRCOMP         ";
            sql += "  where grcode in (                                  ";
            sql += "                   select grcode from TZ_GRCODEMAN   ";
            sql += "                    where userid = " + StringManager.makeSQL(v_userid);
            sql += "                      and gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += "                  )                                  ";
            sql += " order by comp asc                                   ";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                if (i == 0)
                    result = "   and substr(comp,0,4) in ( ";
                else
                    result += ", ";

                result += StringManager.makeSQL(ls.getString("comp"));
                i++;
            }
            if (i > 0)
                result += " ) ";
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
        return result;
    }

    /**
     * 회사관리자 관리회사조건쿼리
     * 
     * @param box receive from the form object and session
     * @return String 조건쿼리
     */
    public String selectManagerComp(String v_userid, String v_gadmin) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        int i = 0;

        try {
            connMgr = new DBConnectionManager();

            sql = " select substr(comp,0,4) comp from TZ_COMPMAN   ";
            sql += "  where userid = " + StringManager.makeSQL(v_userid);
            sql += "    and gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by comp asc                              ";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                if (i == 0)
                    result = "   and substr(comp,0,4) in ( ";
                else
                    result += ", ";

                result += StringManager.makeSQL(ls.getString("comp"));
                i++;
            }
            if (i > 0)
                result += " ) ";
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
        return result;
    }

    /**
     * 부서관리자 관리부사조건쿼리
     * 
     * @param box receive from the form object and session
     * @return String 조건쿼리
     */
    public String selectManagerDept(String v_userid, String v_gadmin) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        int i = 0;

        try {
            connMgr = new DBConnectionManager();

            sql = " select substr(comp,0,8) comp from TZ_COMPMAN  ";
            sql += "  where userid = " + StringManager.makeSQL(v_userid);
            sql += "    and gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by comp asc            ";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                if (i == 0)
                    result = "   and substr(comp,0,8) in ( ";
                else
                    result += ", ";

                result += StringManager.makeSQL(ls.getString("comp"));
                i++;
            }
            if (i > 0)
                result += " ) ";
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
        return result;
    }

    /**
     * 개인별 연수번호 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectYeunsunoList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        DataBox dbox = null;
        String sql1 = "";
        String v_userid = box.getString("p_userid"); // 아이디

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = "select \n";
            sql1 += "     a.subj,a.year,a.subjseq,a.userid,c.subjnm,A.YEUNSUNO,\n";
            sql1 += "     A.YEONSUNM,substr(b.EDUSTART,1,8) as EDUSTART,substr(b.EDUEND,1,8) as EDUEND \n";
            sql1 += "from TZ_PROPOSE_ADDINFO a  \n";
            sql1 += "left join tz_subjseq b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq  \n";
            sql1 += "left join tz_subj c on a.subj=c.subj  \n";
            sql1 += "where a.userid='" + v_userid + "' and a.YEUNSUNO is not null  \n";
            sql1 += "order by  b.EDUSTART ";

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {

                dbox = ls1.getDataBox();
                list1.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r" + ex.getMessage());
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
     * 개인별 신청 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectProposeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        DataBox dbox = null;
        String sql1 = "";
        String v_userid = box.getString("p_userid"); // 아이디

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            // select grcode,grcodenm,gyear,grseq,course,cyear,courseseq,coursenm,subj,subjnm,subjseq,isonoff
            // ,edustart,eduend,appdate,chkfirst,chkfinal,isonoff
            sql1 = "select A.grcode,A.gyear,A.grseq,A.course,A.cyear,A.courseseq,A.coursenm,A.subj,               \n";
            sql1 += "A.subjnm,A.subjseq, A.subjseqgr, A.isonoff,A.edustart,nvl(d.eduend,a.eduend) eduend,B.appdate,B.chkfirst,B.chkfinal,         \n";
            sql1 += "(select grcodenm from TZ_GRCODE where grcode=A.grcode) as grcodenm,A.isonoff,                           \n";
            sql1 += "(select grseqnm from TZ_GRSEQ where grcode = A.grcode and gyear=A.gyear and grseq=A.grseq) as grseqnm,  \n";
            sql1 += "C.classname upperclassname,                                                                             \n";
            sql1 += " isproposeapproval                                                                                      \n";
            sql1 += "from VZ_SCSUBJSEQ A,TZ_PROPOSE B,TZ_SUBJATT C   , TZ_STUDENT D                                          \n";
            sql1 += "where B.userid = " + SQLString.Format(v_userid) + " and A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq and A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq \n";
            sql1 += "   and A.subj=D.subj and A.year=D.year and A.subjseq=D.subjseq and b.userid = d.userid                          \n";
            sql1 += "   and b.cancelkind  is null                                                                                    \n";
            sql1 += "   and a.scupperclass = c.upperclass                                                                            \n";
            sql1 += "   and c.middleclass  = '000'                                                                                   \n";
            sql1 += "   and c.lowerclass   = '000'                                                                                   \n";
            sql1 += "order by a.edustart desc, A.course,A.cyear,A.courseseq,A.subj,A.year,A.subjseq ";

            // System.out.println("sql1============>"+sql1);

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {

                dbox = ls1.getDataBox();
                list1.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r" + ex.getMessage());
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
     * 개인별 수강 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<StudyStatusData> selectEducationList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<StudyStatusData> list1 = null;
        String sql1 = "";
        StudyStatusData data1 = null;
        String v_userid = box.getString("p_userid"); // 아이디

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<StudyStatusData>();

            // select grcode,grcodenm,gyear,grseq,course,cyear,courseseq,coursenm,subj,subjnm,year,subjseq,isonoff,edustart
            // ,eduend,tstep,avtstep,avmtest,avftest,score,point,isonoff
            sql1 = "select A.grcode,A.gyear,A.grseq,A.course,A.cyear,A.courseseq,A.coursenm,A.subj,A.year,\n";
            sql1 += "A.subjnm,A.subjseq,A.isonoff,A.edustart,nvl(d.eduend,a.eduend) eduend,A.point,\n";
            // if(GetCodenm.get_config("score_disp").equals("WS")){ //가중치적용
            // sql1+= "B.avmtest mtest,B.avftest ftest, \n";
            // }else{ //가중치비적용
            // sql1+= "B.mtest,B.ftest, \n";
            // }
            // 2009.11.10 모든 평가항목 보이기 수정
            sql1 += "B.tstep,B.avtstep,\n";
            sql1 += "B.mtest,B.avmtest,\n";
            sql1 += "B.ftest,B.avftest,\n";
            sql1 += "B.htest,B.avhtest,\n";
            sql1 += "B.report,B.avreport,\n";
            sql1 += "B.etc1,B.avetc1,\n";
            sql1 += "B.etc2,B.avetc2,\n";
            sql1 += "B.score,\n";
            sql1 += "(select grcodenm from TZ_GRCODE where grcode=A.grcode) as grcodenm,A.isonoff,  \n";
            sql1 += "(select grseqnm from TZ_GRSEQ where grcode = A.grcode and gyear=A.gyear and grseq=A.grseq) as grseqnm, \n";
            sql1 += " C.classname upperclassname, \n";
            sql1 += " B.isgraduated                                                                                         \n";
            sql1 += "from VZ_SCSUBJSEQ A,TZ_STUDENT B,TZ_SUBJATT C  , TZ_STUDENT D \n";
            sql1 += "where B.userid = " + SQLString.Format(v_userid) + " and A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq \n";
            sql1 += "  and A.subj=D.subj and A.year=D.year and A.subjseq=D.subjseq and b.userid = d.userid  \n";
            sql1 += "  and a.scupperclass = c.upperclass \n";
            sql1 += "  and c.middleclass  = '000'\n";
            sql1 += "  and c.lowerclass   = '000'\n";
            // sql1+= "and B.isgraduated='N' \n";
            sql1 += "order by a.edustart desc, A.course,A.cyear,A.courseseq,A.subj,A.year,A.subjseq \n";
            // System.out.println("sql1============>"+sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                data1 = new StudyStatusData();
                data1.setGrcode(ls1.getString("grcode"));
                data1.setGrcodenm(ls1.getString("grcodenm"));
                data1.setGrseqnm(ls1.getString("grseqnm"));
                data1.setGyear(ls1.getString("gyear"));
                data1.setGrseq(ls1.getString("grseq"));
                data1.setCourse(ls1.getString("course"));
                data1.setCyear(ls1.getString("cyear"));
                data1.setCourseseq(ls1.getString("courseseq"));
                data1.setCoursenm(ls1.getString("coursenm"));
                data1.setSubj(ls1.getString("subj"));
                data1.setYear(ls1.getString("year"));
                data1.setSubjnm(ls1.getString("subjnm"));
                data1.setSubjseq(ls1.getString("subjseq"));
                data1.setIsonoff(ls1.getString("isonoff"));
                data1.setEdustart(ls1.getString("edustart"));
                data1.setEduend(ls1.getString("eduend"));
                data1.setTstep(ls1.getInt("tstep"));
                data1.setMtest(ls1.getInt("mtest"));
                data1.setFtest(ls1.getInt("ftest"));
                data1.setHtest(ls1.getInt("htest"));
                data1.setReport(ls1.getInt("report"));
                data1.setEtc1(ls1.getInt("etc1"));
                data1.setEtc2(ls1.getInt("etc2"));
                data1.setAvtstep(ls1.getInt("avtstep"));
                data1.setAvmtest(ls1.getInt("avmtest"));
                data1.setAvftest(ls1.getInt("avftest"));
                data1.setAvhtest(ls1.getInt("avhtest"));
                data1.setAvreport(ls1.getInt("avreport"));
                data1.setAvetc1(ls1.getInt("avetc1"));
                data1.setAvetc2(ls1.getInt("avetc2"));
                data1.setScore(ls1.getInt("score"));
                data1.setPoint(ls1.getInt("point"));
                data1.setIsonoff(ls1.getString("isonoff"));
                data1.setIsgraduated(ls1.getString("isgraduated"));
                data1.setUpperclassname(ls1.getString("upperclassname"));
                list1.add(data1);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r" + ex.getMessage());
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
     * 개인별 수료 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectGraduationList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        DataBox dbox = null;
        String sql1 = "";
        String v_userid = box.getString("p_userid"); // 아이디

        // ProposeBean probean = new ProposeBean();
        // Hashtable outdata = new Hashtable();

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            // outdata = probean.getMeberInfo(v_userid);

            sql1 = "  (select '1' kind, A.grcode , A.gyear , A.grseq , A.course , A.cyear, A.courseseq , A.coursenm,          ";
            sql1 += "          A.subj , A.year , A.subjseq, A.subjseqgr, A.subjnm, A.isonoff, ";
            sql1 += "          A.edustart edustart , A.eduend , A.point ,";
            // if(GetCodenm.get_config("score_disp").equals("WS")){ //가중치적용
            // sql1+="    B.avmtest mtest, B.avftest ftest,";
            // }else{ //가중치비적용
            // sql1+="    B.mtest mtest, B.ftest ftest,";
            // }
            // 2009.11.10 모든 평가항목 보이기 수정
            sql1 += "    B.tstep, B.avtstep,";
            sql1 += "    B.mtest, B.avmtest,";
            sql1 += "    B.ftest, B.avftest,";
            sql1 += "    B.htest, B.avhtest,";
            sql1 += "    B.report, B.avreport,";
            sql1 += "    B.etc1, B.avetc1,";
            sql1 += "    B.etc2, B.avetc2,";
            sql1 += "    B.score,";
            sql1 += "    (select grcodenm from TZ_GRCODE where grcode=A.grcode) as grcodenm,";
            sql1 += "    (select grseqnm from TZ_GRSEQ where grcode = A.grcode and gyear=A.gyear and grseq=A.grseq) as grseqnm, ";
            sql1 += "    B.credit ,  B.creditexam , C.classname upperclassname,";
            // 수정: lyh 일자: 2005-11.22 내용: decode -> case when
            // sql1+= "   decode(B.isgraduated, 'Y', '이수', '미이수') graduatxt ";
            sql1 += "	case  B.isgraduated when 'Y' then '이수'  else '미이수'  end as graduatxt\n";
            sql1 += "  from VZ_SCSUBJSEQ A,TZ_STOLD B, TZ_SUBJATT C";
            sql1 += "    where B.userid = '" + v_userid + "' ";
            sql1 += "    and A.subj=B.subj ";
            sql1 += "    and A.year=B.year ";
            sql1 += "    and A.subjseq=B.subjseq ";
            sql1 += "    and A.scupperclass=C.upperclass";
            sql1 += "    and C.middleclass='000'";
            sql1 += "    and C.lowerclass='000'";
            // sql1+= "    and A.grcode='"+v_grcode + "'"; //2009.11.06 회원선택시 그룹을 선택하지 않으므로 그룹코드는 주석처리
            // sql1+="    and B.isgraduated='Y'   ";
            sql1 += " )";

            sql1 += " union ";
            sql1 += "  (select '2' kind, 'N000001' grcode , year gyear , '' grseq , '' course , year cyear, '' courseseq , '' coursenm,          ";
            sql1 += "          A.subj , year , subjseq, subjseq subjseqgr, A.subjnm, 'ON' isonoff, ";
            sql1 += "          edustart , eduend , 0 point ,";
            // if(GetCodenm.get_config("score_disp").equals("WS")){ //가중치적용
            // sql1+="    0 mtest, 0 ftest,";
            // }else{ //가중치비적용
            // sql1+="    0 mtest, 0 ftest,";
            // }
            // 2009.11.10 모든 평가항목 보이기 수정
            sql1 += "    0 tstep, 0 avtstep,";
            sql1 += "    0 mtest, 0 avmtest,";
            sql1 += "    0 ftest, 0 avftest,";
            sql1 += "    0 htest, 0 avhtest,";
            sql1 += "    0 report, 0 avreport,";
            sql1 += "    0 etc1, 0 avetc1,";
            sql1 += "    0 etc2, 0 avetc2,";
            sql1 += "    A.score,";
            sql1 += "    (select grcodenm from TZ_GRCODE where grcode=a.grcode) as grcodenm,";
            sql1 += "    '' grseqnm, ";
            sql1 += "    0 credit ,  0 creditexam , C.classname upperclassname,";
            // 수정: lyh 일자: 2005-11.22 내용: decode -> case when
            // sql1+= "   decode(B.isgraduated, 'Y', '이수', '미이수') graduatxt ";
            sql1 += "	case  isgraduated when 'Y' then '이수'  else '미이수'  end as graduatxt\n";
            sql1 += "  from TZ_STOLDHST A, TZ_SUBJ B, TZ_SUBJATT C ";
            sql1 += "    where userid = '" + v_userid + "' ";
            // sql1+= "    and grcode='"+v_grcode + "'"; //2009.11.06 회원선택시 그룹을 선택하지 않으므로 그룹코드는 주석처리

            // sql1+="    and B.isgraduated='Y'   ";
            sql1 += "      and a.subj=b.subj";
            sql1 += "      and b.upperclass=c.upperclass";
            sql1 += "      and c.middleclass='000'";
            sql1 += "      and c.lowerclass='000'";
            sql1 += " )";

            // 주석처리: lyh 일자: 2005-11-23 내용: 지난시스템 교육이력부분 union - 데이타마이그레이션한 table 생성필요
            /*
             * sql1+= " union "; sql1+= " (select '' grcode, year gyear, '0000' grseq, '' course, '' cyear, '' courseseq, '' coursenm, " ; sql1+=
             * "         '' subj, '' year, '' subjseq, '' subjseqgr, subjnm, isonoff, " ; sql1+=
             * "         edustart, eduend, 0 tstep, processpoint avtstep, score score, 100 point," ; sql1+=
             * "         testpoint mtest, finaltestpoint ftest, "; sql1+= "         '' grcodenm, '' grseqnm,"; sql1+= "         0 credit, 0 creditexam,"; //
             * 수정: lyh 일자: 2005-11.22 내용: decode -> case when //sql1+= "         decode(isgraduated, 'Y', '이수', '미이수') graduatxt"; sql1+=
             * "   case  isgraduated when  'Y' then  '이수' else '미이수 ' end  as  graduatxt " ; sql1+= "   from  tz_aesstold "; sql1+=
             * "  where resno = '"+v_resno+"'"; sql1+= " )";
             */
            sql1 += "  order by      edustart desc         ";

            System.out.println("selectGraduationList => " + sql1);

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {

                dbox = ls1.getDataBox();
                dbox.put("d_hakjeum", new Double(ls1.getDouble("credit") + ls1.getDouble("creditexam")));
                list1.add(dbox);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r" + ex.getMessage());
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
     * 개인별 학점이수현황 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectScoreCompleteList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        DataBox dbox = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        String v_userid = box.getString("p_userid"); // 아이디

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = " select get_jikwinm(a.jikwi,b.comp) jikwinm, duty_yn, ";
            sql1 += "        required_cnt, required_score, choice_score    ";
            sql1 += "   from tz_scorecompleteresult a, tz_member b         ";
            sql1 += " where a.userid = b.userid                            ";
            sql1 += "   and a.userid = " + StringManager.makeSQL(v_userid);
            sql1 += " order by a.jikwi                                     ";

            // System.out.println("sql1============>"+sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r" + ex.getMessage());
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
     * 개인별 오프라인 수료과정 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectOffGraduationList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";

        String head_sql1 = "";
        String body_sql1 = "";
        String order_sql1 = "";
        String count_sql1 = "";
        int v_pageno = box.getInt("p_pageno");

        String v_user_id = box.getString("p_userid");

        String v_upperclass = box.getStringDefault("p_upperclass", "ALL");

        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            head_sql1 = " SELECT b.subj, b.YEAR, b.subjseq, a.upperclass,\n";
            head_sql1 += "       (SELECT classname\n";
            head_sql1 += "          FROM tz_offsubjatt\n";
            head_sql1 += "         WHERE upperclass = a.upperclass AND middleclass = '000')\n";
            head_sql1 += "                                                                 upperclassnm,\n";
            head_sql1 += "       a.subjnm, b.edustart, b.eduend, a.isterm, c.stustatus,\n";
            head_sql1 += "       (SELECT codenm\n";
            head_sql1 += "          FROM tz_code\n";
            head_sql1 += "         WHERE gubun = '0089' AND code = c.stustatus) stustatusnm,\n";
            head_sql1 += "       (SELECT score\n";
            head_sql1 += "          FROM tz_offtermstudent\n";
            head_sql1 += "         WHERE ROWNUM < 2\n";
            head_sql1 += "           AND subj = b.subj\n";
            head_sql1 += "           AND YEAR = b.YEAR\n";
            head_sql1 += "           AND subjseq = b.subjseq\n";
            head_sql1 += "           AND userid = c.userid) score,\n";
            head_sql1 += "       c.isgraduated\n";
            body_sql1 = "  FROM tz_offsubj a, tz_offsubjseq b, tz_offstudent c\n";
            body_sql1 += " WHERE a.subj = b.subj\n";
            body_sql1 += "   AND a.subj = c.subj\n";
            body_sql1 += "   AND b.YEAR = c.YEAR\n";
            body_sql1 += "   AND b.subjseq = c.subjseq\n";
            body_sql1 += "   AND b.seq = '1'\n";
            body_sql1 += "   AND TO_CHAR (SYSDATE, 'YYYYMMDDHH24') >= b.eduend\n";
            order_sql1 += " order by edustart desc, upperclass, subj, subjseq\n";

            if (!v_upperclass.equals("ALL"))
                body_sql1 += " and a.upperclass = " + SQLString.Format(v_upperclass);

            body_sql1 += "   AND c.userid = " + SQLString.Format(v_user_id);

            sql1 = head_sql1 + body_sql1 + order_sql1;

            System.out.println("PersonalOffGraduationList.sql = " + sql1);

            ls1 = connMgr.executeQuery(sql1);

            count_sql1 = "select count(*) " + body_sql1;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1); // 전체 row 수를 반환한다

            ls1.setPageSize(row); // 페이지당 row 갯수를 세팅한다
            ls1.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
            int total_page_count = ls1.getTotalPage(); // 전체 페이지 수를 반환한다

            while (ls1.next()) {
                dbox = ls1.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

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
     * 개인별 오프라인 신청 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectOffProposeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String v_userid = box.getString("p_userid");
        String v_upperclass = box.getStringDefault("p_upperclass", "ALL");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT   tid, subj, YEAR, subjseq, subjnm, edustart, eduend, appdate, seq,";
            sql += "          (SELECT sa.classname\n";
            sql += "             FROM tz_offsubj sb, tz_offsubjatt sa\n";
            sql += "            WHERE sb.upperclass = sa.upperclass\n";
            sql += "              AND sa.middleclass = '000'\n";
            sql += "              AND sa.lowerclass = '000'\n";
            sql += "              AND sb.subj = a.subj) classname,\n";
            sql += "          refundabledate, refundableyn, refundyn, canceldate, paymethod, cancelableyn, refunddate,";
            sql += "          chkfirst, chkfinal, RANK,\n";
            sql += "          CASE\n";
            sql += "             WHEN RANK = 1\n";
            sql += "                THEN COUNT (*) OVER (PARTITION BY tid)\n";
            sql += "             ELSE 0\n";
            sql += "          END AS rowspan, BIYONG\n";
            sql += "     FROM (SELECT a.tid, b.subj, b.YEAR, b.subjseq, d.subjnm, b.edustart,\n";
            sql += "                  b.eduend, a.appdate, a.chkfirst, a.chkfinal, a.seq,\n";
            sql += "                  CASE WHEN REFUNDDATE IS NOT NULL\n";
            sql += "                       THEN 'Y'\n";
            sql += "                       ELSE 'N'\n";
            sql += "                  END refundyn,\n";
            sql += "                  a.canceldate, c.paymethod, d.upperclass, '' refunddate,";
            sql += "                  endcanceldate AS refundabledate,\n";
            // sql += "                  CASE WHEN (    TO_CHAR(TO_DATE(substr(startcanceldate,1,8))) <= TO_CHAR (SYSDATE, 'YYYYMMDD')\n";
            // sql += "                             AND TO_CHAR(TO_DATE(substr(endcanceldate,1,8))) >= TO_CHAR (SYSDATE, 'YYYYMMDD') )\n";
            sql += "                  CASE WHEN (    SUBSTR (startcanceldate, 1, 8) <= TO_CHAR (SYSDATE, 'YYYYMMDD')\n";
            sql += "                             AND SUBSTR (endcanceldate, 1, 8) >= TO_CHAR (SYSDATE, 'YYYYMMDD') )\n";
            sql += "                       THEN 'Y'\n";
            sql += "                       ELSE 'N'\n";
            sql += "                  END refundableyn,\n";
            sql += "                  CASE WHEN (    SUBSTR (startcanceldate, 1, 8) <= TO_CHAR (SYSDATE, 'YYYYMMDD')\n";
            sql += "                             AND SUBSTR (endcanceldate, 1, 8) >= TO_CHAR (SYSDATE, 'YYYYMMDD') )\n";
            sql += "                       THEN 'Y'\n";
            sql += "                       ELSE 'N'\n";
            sql += "                  END cancelableyn,\n";
            // sql += "                  CASE WHEN  SUBSTR (edustart, 1, 8) > TO_CHAR (SYSDATE, 'YYYYMMDD')\n";
            // sql += "                       THEN 'Y'\n";
            // sql += "                       ELSE 'N'\n";
            // sql += "                  END cancelableyn,\n";
            sql += "                  RANK () OVER (PARTITION BY a.tid ORDER BY a.tid,\n";
            sql += "                  b.subj) RANK, B.BIYONG\n";
            sql += "             FROM tz_offpropose a, tz_offsubjseq b, tz_offbillinfo c, tz_offsubj d\n";
            sql += "            WHERE (1 = 1)\n";
            sql += "              AND a.tid = c.tid(+)\n";
            sql += "              AND a.userid = " + SQLString.Format(v_userid);
            sql += "              AND a.subj = b.subj\n";
            sql += "              AND a.YEAR = b.YEAR\n";
            sql += "              AND a.subjseq = b.subjseq\n";
            sql += "              AND b.seq = '1'\n";

            if (!v_upperclass.equals("ALL"))
                sql += " and d.upperclass = " + SQLString.Format(v_upperclass) + "\n";

            sql += "              AND a.subj = d.subj\n";
            sql += "            UNION ALL\n";
            sql += "           SELECT a.tid, b.subj, b.YEAR, b.subjseq, d.subjnm, b.edustart,\n";
            sql += "                  b.eduend, a.appdate, '' chkfirst, '' chkfinal, a.seq,\n";
            sql += "                  CASE WHEN REFUNDDATE IS NOT NULL\n";
            sql += "                       THEN 'Y'\n";
            sql += "                       ELSE 'N'\n";
            sql += "                  END refundyn,\n";
            sql += "                  a.canceldate, c.paymethod, d.upperclass, a.refunddate,";
            sql += "                  endcanceldate AS refundabledate,\n";
            // sql += "                  CASE WHEN (    TO_CHAR(TO_DATE(substr(startcanceldate,1,8))) <= TO_CHAR (SYSDATE, 'YYYYMMDD')\n";
            // sql += "                             AND TO_CHAR(TO_DATE(substr(endcanceldate,1,8))) >= TO_CHAR (SYSDATE, 'YYYYMMDD') )\n";
            sql += "                  'N' refundableyn, 'N' cancelableyn,\n";
            sql += "                  RANK () OVER (PARTITION BY a.tid ORDER BY a.tid,\n";
            sql += "                  b.subj) RANK, B.BIYONG\n";
            sql += "             FROM tz_offcancel a, tz_offsubjseq b, tz_offbillinfo c, tz_offsubj d\n";
            sql += "            WHERE (1 = 1)\n";
            sql += "              AND a.tid = c.tid(+)\n";
            sql += "              AND a.userid = " + SQLString.Format(v_userid);
            sql += "              AND a.subj = b.subj\n";
            sql += "              AND a.YEAR = b.YEAR\n";
            sql += "              AND a.subjseq = b.subjseq\n";
            sql += "              AND b.seq = '1'\n";

            if (!v_upperclass.equals("ALL"))
                sql += " and d.upperclass = " + SQLString.Format(v_upperclass) + "\n";

            sql += "              AND a.subj = d.subj) a\n";
            sql += " ORDER BY appdate DESC, tid, subj\n";

            System.out.println("sql_proposeoffhostorylist============>" + sql);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
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

    /**
     * 수강과정의 일차별 학습현황 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<StudyStatusData> selectSubjectLessonList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<StudyStatusData> list = null;
        String sql = "";
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_isonoff = box.getString("p_isonoff");
        String v_userid = box.getString("p_userid");
        StudyStatusData data = null;
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<StudyStatusData>();

            if (v_isonoff.equals("ON")) {
                // select classnm,lesson,sdesc,first_edu,ldate,proj_score,act_score
                sql = "select (select classnm from TZ_CLASS where subj=B.subj and year=B.year and subjseq=B.subjseq and class=B.class),";
                sql += "A.lesson,A.sdesc,C.first_edu,C.ldate,";
                // 수정: lyh 일자: 2005-11.22 내용: decode -> case when
                /*
                 * sql+= "decode((select reptype from TZ_PROJORD where subj=B.subj and year=B.year and subjseq=B.subjseq and lesson=A.lesson),\'R\'," ;
                 * sql+= "(select score from TZ_PROJREP where subj=B.subj and year=B.year and subjseq=B.subjseq and projgrp=B.userid and max(ordseq))," ;
                 * sql+= "(select score from TZ_PROJREP where subj=B.subj and year=B.year and subjseq=B.subjseq and max(ordseq) and projgrp in " ; sql+=
                 * "(select userid from TZ_PROJGRP where subj=B.subj and year=B.year and subjseq=B.subjseq))) proj_score, "
                 */
                sql += " case (select reptype from TZ_PROJORD where subj=B.subj and year=B.year and subjseq=B.subjseq and lesson=A.lesson) ";
                sql += " when \'R\' then (select score from TZ_PROJREP where subj=B.subj and year=B.year and subjseq=B.subjseq and projgrp=B.userid and max(ordseq)) ";
                sql += " else (select score from TZ_PROJREP where subj=B.subj and year=B.year and subjseq=B.subjseq and max(ordseq) and projgrp in ";
                sql += "(select userid from TZ_PROJGRP where subj=B.subj and year=B.year and subjseq=B.subjseq)) end  as  proj_score, ";
                sql += "(select score from TZ_ACTIVITY_ANS where subj=B.subj and year=B.year and subjseq=B.subjseq and lesson=A.lesson and userid=B.userid ";
                sql += "and max(seq)) act_score ";
                sql += "from TZ_SUBJLESSON A,TZ_STUDENT B,TZ_PROGRESS C ";
                sql += "A.subj=" + SQLString.Format(v_subj) + " and A.subj=B.subj and B.year=" + SQLString.Format(v_year);
                sql += " and B.subjseq=" + SQLString.Format(v_subjseq) + " and B.userid=" + SQLString.Format(v_userid);
                sql += " and C.subj=B.subj and C.year=B.year and C.subjseq=B.subjseq and C.lesson=A.lesson";
            }
            // System.out.println("sql============>"+sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new StudyStatusData();
                data.setClassnm(ls.getString("classnm"));
                data.setLesson(ls.getString("lesson"));
                data.setSdesc(ls.getString("sdesc"));
                data.setFirstedu(ls.getString("first_edu"));
                data.setLdate(ls.getString("ldate"));
                data.setProjscore(ls.getString("projscore"));
                data.setActscore(ls.getString("actscore"));
                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r" + ex.getMessage());
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
     * 클래스별 학습현황 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<StudyStatusData> selectClassLearningList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<StudyStatusData> list = null;
        String sql = "";
        StudyStatusData data = null;
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); // 교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); // 년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); // 교육차수

        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); // 과정분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); // 과정분류
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); // 과정분류

        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");// 과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); // 과정 차수
        String ss_company = box.getStringDefault("s_company", "ALL"); // 회사
        String ss_class = box.getStringDefault("s_class", "ALL"); // 클래스
        String ss_action = box.getString("s_action");
        String v_orderColumn = box.getString("p_orderColumn"); // 정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); // 정렬할 순서

        try {
            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list = new ArrayList<StudyStatusData>();

                // select compnm,jikwi,jikwinm,userid,cono,name,tstep,avtstep,
                // report,act,mtest,ftest,score,point,comptel,email
                sql = "select A.isgraduated , get_compnm(B.comp,2,2) companynm, get_deptnm(B.deptnam, B.userid) compnm, B.jikwi, get_jikwinm(B.jikwi,B.comp) jikwinm,   ";
                sql += "       B.jikup, get_jikupnm(B.jikup,B.comp, jikupnm) jikupnm, B.userid,B.cono,B.name, B.comptel,B.email, B.work_plcnm, C.subjnm,C.subj, C.year, C.subjseq, ";
                sql += "       d.sdate, e.tuserid , e.tname,                              ";
                sql += "       avg(A.tstep) tstep,avg(A.avtstep) avtstep,            ";
                if (GetCodenm.get_config("score_disp").equals("WS")) { // 가중치적용
                    sql += "avg(A.avreport) report,avg(A.avact) act,avg(A.avmtest) mtest,avg(A.avftest) ftest, ";
                } else { // 가중치비적용
                    sql += "avg(A.report) report,avg(A.act) act,avg(A.mtest) mtest,avg(A.ftest) ftest, ";
                }
                sql += "      avg(A.score) score, avg(C.point) point  ";
                sql += " from TZ_STUDENT A, TZ_MEMBER B, VZ_SCSUBJSEQ C, ";
                sql += "      ( select userid, subj, year, subjseq, max(sdate) sdate from TZ_SANGDAM group by userid, subj, year, subjseq ) d,  ";
                sql += "      ( select x.subj, x.year, x.subjseq, x.class, x.ttype, x.tuserid, y.name tname from TZ_CLASSTUTOR x, TZ_TUTOR y    ";
                sql += "        where x.tuserid = y.userid and x.ttype = 'M') E         ";
                sql += " where A.userid = B.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq ";
                sql += "   and a.userid = d.userid(+) and a.subj = d.subj(+) and a.year = d.year(+) and a.subjseq = d.subjseq(+)             ";
                sql += "   and a.subj = e.subj(+) and a.year = e.year(+) and a.subjseq = e.subjseq(+) and a.class = e.class(+)     ";
                /*
                 * sql+= " where A.userid = B.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq " ; sql+=
                 * "   and a.userid = d.userid(+) and a.subj=d.subj(+) and a.year=d.year(+) and a.subjseq=d.subjseq(+) " ; sql+=
                 * "   and a.subj = e.subj(+) and a.year = e.year(+) and a.subjseq = e.subjseq(+) and a.class = e.class(+) " ;
                 */

                if (!ss_grcode.equals("ALL"))
                    sql += " and C.grcode = " + SQLString.Format(ss_grcode);
                if (!ss_gyear.equals("ALL"))
                    sql += " and C.gyear = " + SQLString.Format(ss_gyear);
                if (!ss_grseq.equals("ALL"))
                    sql += " and C.grseq = " + SQLString.Format(ss_grseq);
                if (!ss_uclass.equals("ALL"))
                    sql += " and C.scupperclass = " + SQLString.Format(ss_uclass);
                if (!ss_mclass.equals("ALL"))
                    sql += " and C.scmiddleclass = " + SQLString.Format(ss_mclass);
                if (!ss_lclass.equals("ALL"))
                    sql += " and C.sclowerclass = " + SQLString.Format(ss_lclass);
                if (!ss_subjcourse.equals("ALL"))
                    sql += " and C.scsubj = " + SQLString.Format(ss_subjcourse);
                if (!ss_subjseq.equals("ALL"))
                    sql += " and C.scsubjseq = " + SQLString.Format(ss_subjseq);

                if (!ss_class.equals("ALL")) {
                    // sql+= " and A.subj in (select subj from TZ_CLASS where class = "+SQLString.Format(ss_class)+" and subj=C.subj ";
                    // sql+= " and year=C.year and subjseq=C.subjseq) ";
                    sql += " and a.class = " + SQLString.Format(ss_class);
                }
                if (!ss_company.equals("ALL")) {
                    sql += " and substr(B.comp, 0 ,4) = '" + ss_company.substring(0, 4) + "'";
                }

                sql += " group by A.isgraduated, get_compnm(B.comp,2,2), get_deptnm(B.deptnam, B.userid), B.jikwi, get_jikwinm(B.jikwi,B.comp), d.sdate,  e.tuserid , e.tname,       ";
                sql += "          B.jikup, get_jikupnm(B.jikup,B.comp,B.jikupnm), B.userid,B.cono,B.name,B.comptel,B.email,B.comp, B.work_plcnm, C.subjnm,C.subj, C.year, C.subjseq ";

                if (v_orderColumn.equals("subj"))
                    v_orderColumn = "c.subj";
                if (v_orderColumn.equals("userid"))
                    v_orderColumn = "b.userid";
                if (v_orderColumn.equals("name"))
                    v_orderColumn = "b.name";
                if (v_orderColumn.equals("compnm1"))
                    v_orderColumn = "get_compnm(b.comp,2,2)";
                if (v_orderColumn.equals("compnm2"))
                    v_orderColumn = "get_deptnm(b.deptnam, b.userid)";
                if (v_orderColumn.equals("jiknm"))
                    v_orderColumn = "get_jikwinm(b.jikwi,b.comp)";

                if (v_orderColumn.equals("")) {
                    sql += " order by C.subj, C.year, C.subjseq";
                } else {
                    sql += " order by " + v_orderColumn + v_orderType;
                }

                // System.out.println("sql============>"+sql);
                ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    data = new StudyStatusData();
                    data.setCompanynm(ls.getString("companynm"));
                    data.setCompnm(ls.getString("compnm"));
                    data.setJikwi(ls.getString("jikwi"));
                    data.setJikwinm(ls.getString("jikwinm"));
                    data.setJikup(ls.getString("jikup"));
                    data.setJikupnm(ls.getString("jikupnm"));
                    data.setUserid(ls.getString("userid"));
                    data.setCono(ls.getString("cono"));
                    data.setName(ls.getString("name"));
                    data.setTuserid(ls.getString("tuserid"));
                    data.setTname(ls.getString("tname"));
                    data.setTstep(ls.getInt("tstep"));
                    data.setAvtstep(ls.getInt("avtstep"));
                    data.setReport(ls.getInt("report"));
                    data.setAct(ls.getInt("act"));
                    data.setMtest(ls.getInt("mtest"));
                    data.setFtest(ls.getInt("ftest"));
                    data.setScore(ls.getInt("score"));
                    data.setPoint(ls.getInt("point"));
                    data.setComptel(ls.getString("comptel"));
                    data.setEmail(ls.getString("email"));
                    data.setSubjnm(ls.getString("subjnm"));
                    data.setSubj(ls.getString("subj"));
                    data.setYear(ls.getString("year"));
                    data.setSubjseq(ls.getString("subjseq"));
                    data.setWork_plcnm(ls.getString("work_plcnm"));
                    data.setSdate(ls.getString("sdate"));
                    data.setIsgraduated(ls.getString("isgraduated"));
                    list.add(data);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r" + ex.getMessage());
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
     * 성적별 학습현황 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<StudyStatusData> selectScoreLearningList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<StudyStatusData> list = null;
        String sql = "";
        StudyStatusData data = null;
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); // 교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); // 년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); // 교육차수

        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); // 과정분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); // 과정분류
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); // 과정분류

        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");// 과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); // 과정 차수
        String ss_company = box.getStringDefault("s_company", "ALL"); // 회사
        String ss_selGubun = box.getString("s_selGubun"); // 진도율 1,취득점수 2
        String ss_selStart = box.getStringDefault("s_selStart", "0"); // ~부터
        String ss_selEnd = box.getStringDefault("s_selEnd", "10000"); // ~까지
        String ss_action = box.getString("s_action");
        String v_orderColumn = box.getString("p_orderColumn"); // 정렬할 컬럼명

        ManagerAdminBean bean = null;
        String v_sql_add = "";
        String v_userid = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");

        try {
            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list = new ArrayList<StudyStatusData>();

                // select compnm,jikwi,jikwinm,userid,cono,name,tstep,avtstep,
                // report,act,mtest,ftest,score,point,comptel,email
                sql = "select get_compnm(B.comp,2,4) compnm, B.jikwi,get_jikwinm(B.jikwi,B.comp) jikwinm,   ";
                sql += "B.jikup, get_jikupnm(B.jikup,B.comp) jikupnm, B.userid, B.cono, B.name,               ";
                sql += "avg(A.tstep) tstep, avg(A.avtstep) avtstep,                                          ";
                if (GetCodenm.get_config("score_disp").equals("WS")) { // 가중치적용
                    sql += "avg(A.avreport) report,avg(A.avact) act,avg(A.avmtest) mtest,avg(A.avftest) ftest, ";
                } else { // 가중치비적용
                    sql += "avg(A.report) report,avg(A.act) act,avg(A.mtest) mtest,avg(A.ftest) ftest, ";
                }
                sql += "avg(A.score) score, B.comptel, B.email, avg(C.point) point ";
                sql += "from TZ_STUDENT A,TZ_MEMBER B,VZ_SCSUBJSEQ C where 1=1 ";

                if (!ss_grcode.equals("ALL"))
                    sql += " and C.grcode = " + SQLString.Format(ss_grcode);
                if (!ss_gyear.equals("ALL"))
                    sql += " and C.gyear = " + SQLString.Format(ss_gyear);
                if (!ss_grseq.equals("ALL"))
                    sql += " and C.grseq = " + SQLString.Format(ss_grseq);
                if (!ss_uclass.equals("ALL"))
                    sql += " and C.scupperclass = " + SQLString.Format(ss_uclass);
                if (!ss_mclass.equals("ALL"))
                    sql += " and C.scmiddleclass = " + SQLString.Format(ss_mclass);
                if (!ss_lclass.equals("ALL"))
                    sql += " and C.sclowerclass = " + SQLString.Format(ss_lclass);
                if (!ss_subjcourse.equals("ALL"))
                    sql += " and C.scsubj = " + SQLString.Format(ss_subjcourse);
                if (!ss_subjseq.equals("ALL"))
                    sql += " and C.scsubjseq = " + SQLString.Format(ss_subjseq);

                if (!ss_company.equals("ALL")) {
                    sql += " and substr(B.comp, 0 ,4) = '" + ss_company.substring(0, 4) + "'";
                }
                if (ss_selGubun.equals("1")) { // 진도율
                    sql += " and A.tstep >= " + SQLString.Format(ss_selStart) + " and A.tstep <= " + SQLString.Format(ss_selEnd);
                } else if (ss_selGubun.equals("2")) { // 취득점수
                    sql += " and A.score >= " + SQLString.Format(ss_selStart) + " and A.score <= " + SQLString.Format(ss_selEnd);
                }

                // 부서장일경우
                if (s_gadmin.equals("K6") || s_gadmin.equals("K7")) {
                    bean = new ManagerAdminBean();
                    v_sql_add = bean.getManagerDept(v_userid, s_gadmin);
                    if (!v_sql_add.equals(""))
                        sql += " and B.comp in " + v_sql_add; // 관리부서검색조건쿼리
                }

                sql += " and A.userid = B.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq           ";
                sql += " group by get_compnm(B.comp,2,4), B.jikwi, get_jikwinm(B.jikwi,B.comp),                      ";
                sql += "          B.jikup, get_jikupnm(B.jikup,B.comp,B.jikupnm),  B.userid, B.cono, B.name, B.comptel,B.email,B.comp      ";
                if (!v_orderColumn.equals("")) {
                    v_orderColumn = "B." + v_orderColumn;
                    sql += " order by " + v_orderColumn;
                } else {
                    sql += " order by B.comp,B.jikup,B.userid ";
                }
                System.out.println("sql============>" + sql);
                ls = connMgr.executeQuery(sql);
                // System.out.println(sql);

                while (ls.next()) {
                    data = new StudyStatusData();
                    data.setCompnm(ls.getString("compnm"));
                    data.setJikwi(ls.getString("jikwi"));
                    data.setJikwinm(ls.getString("jikwinm"));
                    data.setJikup(ls.getString("jikup"));
                    data.setJikupnm(ls.getString("jikupnm"));
                    data.setUserid(ls.getString("userid"));
                    data.setCono(ls.getString("cono"));
                    data.setName(ls.getString("name"));
                    data.setTstep(ls.getInt("tstep"));
                    data.setAvtstep(ls.getInt("avtstep"));
                    data.setReport(ls.getInt("report"));
                    data.setAct(ls.getInt("act"));
                    data.setMtest(ls.getInt("mtest"));
                    data.setFtest(ls.getInt("ftest"));
                    data.setScore(ls.getInt("score"));
                    data.setPoint(ls.getInt("point"));
                    data.setComptel(ls.getString("comptel"));
                    data.setEmail(ls.getString("email"));
                    list.add(data);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r" + ex.getMessage());
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
     * 종합 학습현황 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectTotalScoreStatusList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        PreparedStatement pstmt1 = null;

        ResultSet rs1 = null;

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); // 교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); // 년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); // 교육차수

        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); // 과정분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); // 과정분류
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); // 과정분류

        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");// 과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); // 과정 차수
        String ss_selGubun = box.getString("s_selGubun"); // 진도율 1,취득점수 2
        String ss_selStart = box.getStringDefault("s_selStart", "0"); // ~부터
        String ss_selEnd = box.getStringDefault("s_selEnd", "10000"); // ~까지
        String ss_samtotal = box.getStringDefault("s_samtotal", "ALL"); // 삼진아웃
        String ss_goyong = box.getStringDefault("s_goyong", "ALL"); // 고용보험

        String ss_action = box.getString("s_action");
        String v_orderColumn = box.getString("p_orderColumn"); // 정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); // 정렬할 순서

        String ss_selExam = box.getStringDefault("s_selExam", "ALL"); // 시험응시
        String ss_selProj = box.getStringDefault("s_selProj", "ALL"); // 과제제출

        String ss_area = box.getString("s_area"); // 문콘,게임,방송
        String ss_idorname = box.getString("s_idorname"); // 사용자ID,사용자명 구분
        String ss_idornameinput = box.getString("s_idornameinput"); // 사용자ID,사용자명 입력받는 변수
        String ss_biyong = box.getString("s_biyong"); // 전체,무료,유료

        try {
            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list = new ArrayList<DataBox>();

                // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

                sql = " select * from\n";
                sql += " (select\n";
                sql += "   subj,  course, cyear, grseq, courseseq, coursenm,  area, year,   subjseq,   subjseqgr,\n";
                sql += "   subjnm,   isonoff,   userid,  name, memberyear, sex, addr, resno1, resno2, resno,    crypto.dec('normal',email) email, crypto.dec('normal',handphone) handphone,\n";
                sql += "   samtotal,    tstep,   avtstep, report, avreport, act, avact, mtest, avmtest, ftest, avftest,\n";
                sql += "   htest, avhtest, etc1, avetc1, etc2, avetc2,\n";
                sql += "   score,   point ,   isgraduated, biyong_name, compnm, deptnam \n";
                sql += "    ,   ( select count(lesson) from tz_subjlesson where subj = totalstatus.subj ) as lesson_cnt \n";
                sql += " from\n";
                sql += " (\n";
                sql += " select C.subj, C.course, C.cyear, c.grseq, C.courseseq, C.coursenm, c.area, C.year,  C.subjseq, C.subjseqgr, c.subjnm, c.isonoff, B.userid, B.name, B.memberyear, B.sex, B.addr, B.resno1, B.resno2, B.resno,\n";
                sql += "   (case when c.biyong=0 then '무료' else '유료' end ) as biyong_name,  b.compnm, b.deptnam, \n";
                sql += "        B.email, b.handphone,   a.samtotal,\n";
                sql += "        CEIL (AVG (a.tstep) * 10) / 10 tstep,\n";
                sql += "        CEIL (AVG (a.avtstep) * 10) / 10 avtstep,\n";
                sql += "        CEIL (AVG (a.mtest) * 10) / 10 mtest,\n";
                sql += "        CEIL (AVG (a.avmtest) * 10) / 10 avmtest,\n";
                sql += "        CEIL (AVG (a.ftest) * 10) / 10 ftest,\n";
                sql += "        CEIL (AVG (a.avftest) * 10) / 10 avftest,\n";
                sql += "        CEIL (AVG (a.htest) * 10) / 10 htest,\n";
                sql += "        CEIL (AVG (a.avhtest) * 10) / 10 avhtest,\n";
                sql += "        CEIL (AVG (a.report) * 10) / 10 report,\n";
                sql += "        CEIL (AVG (a.avreport) * 10) / 10 avreport,\n";
                sql += "        CEIL (AVG (a.act) * 10) / 10 act,\n";
                sql += "        CEIL (AVG (a.avact) * 10) / 10 avact,\n";
                sql += "        CEIL (AVG (a.etc1) * 10) / 10 etc1,\n";
                sql += "        CEIL (AVG (a.avetc1) * 10) / 10 avetc1,\n";
                sql += "        CEIL (AVG (a.etc2) * 10) / 10 etc2,\n";
                sql += "        CEIL (AVG (a.avetc2) * 10) / 10 avetc2,\n";
                sql += "        CEIL (AVG (a.score) * 10) / 10 score,\n";
                sql += "        AVG (c.point) point,\n";
                sql += "        a.isgraduated, \n";
                sql += " 		 (select count(userid) smsnum\n";
                sql += "           from TZ_HUMANTOUCH\n";
                sql += "           where subj = C.subj and year = C.year and subjseq = C.subjseq\n";
                sql += "             and userid = B.userid  and ismail = '2'  ) smsnum,\n";
                sql += "         (select count(userid) mailnum\n";
                sql += "           from TZ_HUMANTOUCH\n";
                sql += "           where subj = C.subj and year = C.year and subjseq = C.subjseq\n";
                sql += "             and userid = B.userid  and ismail = '1'  ) mailnum\n";

                sql += " from  VZ_SCSUBJSEQ C \n";
                sql += " left join tz_propose f on f.subj=C.subj and f.year=C.year and f.subjseq=C.subjseq\n";
                sql += " left join TZ_STUDENT A on  A.subj = f.subj and A.year = f.year and A.subjseq = f.subjseq and A.userid = f.userid\n";
                sql += " left join TZ_MEMBER B on f.userid=B.userid  and b.grcode=c.grcode\n";
                sql += " where f.CHKFINAL='Y' \n";
                // sql += "        from  TZ_STUDENT A, TZ_MEMBER B, VZ_SCSUBJSEQ C, (SELECT * FROM tz_progress WHERE LESSON <> '000') D,tz_propose f,\n";
                // sql += "             ( select userid, subj, year, subjseq, max(sdate) sdate from TZ_SANGDAM\n";
                // sql += "               group by userid, subj, year, subjseq ) E\n";
                // sql += "        where A.userid=B.userid  and b.grcode=c.grcode\n";
                // sql += "          and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq\n";
                // sql += "          and A.subj = D.subj(+) and A.year = D.year(+) and A.subjseq = D.subjseq(+) and A.userid = D.userid(+)\n";
                // sql += "          and A.subj = E.subj(+) and A.year = E.year(+) and A.subjseq = E.subjseq(+) and A.userid = E.userid(+)\n";
                // sql += "          and A.subj = f.subj(+) and A.year = f.year(+) and A.subjseq = f.subjseq(+) and A.userid = f.userid(+)\n";

                if (!ss_grcode.equals("ALL"))
                    sql += " and C.grcode        = " + SQLString.Format(ss_grcode);
                if (!ss_gyear.equals("ALL"))
                    sql += " and C.gyear         = " + SQLString.Format(ss_gyear);
                if (!ss_grseq.equals("ALL"))
                    sql += " and C.grseq         = " + SQLString.Format(ss_grseq);
                if (!ss_uclass.equals("ALL"))
                    sql += " and C.scupperclass  = " + SQLString.Format(ss_uclass);
                if (!ss_mclass.equals("ALL"))
                    sql += " and C.scmiddleclass = " + SQLString.Format(ss_mclass);
                if (!ss_lclass.equals("ALL"))
                    sql += " and C.sclowerclass  = " + SQLString.Format(ss_lclass);
                if (!ss_subjcourse.equals("ALL"))
                    sql += " and C.scsubj        = " + SQLString.Format(ss_subjcourse);
                if (!ss_subjseq.equals("ALL"))
                    sql += " and C.scsubjseq     = " + SQLString.Format(ss_subjseq);
                if (ss_biyong.equals("Z"))
                    sql += " and C.BIYONG        = 0";
                else if (ss_biyong.equals("P"))
                    sql += " and C.BIYONG        != 0";

                if (ss_selGubun.equals("1")) { // 진도율
                    sql += " and A.tstep >= " + SQLString.Format(ss_selStart) + " and A.tstep <= " + SQLString.Format(ss_selEnd);
                } else if (ss_selGubun.equals("2")) { // 취득점수
                    sql += " and A.score >= " + SQLString.Format(ss_selStart) + " and A.score <= " + SQLString.Format(ss_selEnd);
                }
                if (!ss_samtotal.equals("ALL"))
                    sql += " and A.samtotal = " + SQLString.Format(ss_samtotal);
                if (!ss_goyong.equals("ALL"))
                    sql += " and A.isgoyong = " + SQLString.Format(ss_goyong);

                if (!ss_area.equals("T"))
                    sql += " and c.area=" + SQLString.Format(ss_area);

                if (ss_idorname.equals("id"))
                    sql += " and f.userid like '%" + ss_idornameinput + "%'";
                else if (ss_idorname.equals("name"))
                    sql += " and b.name like '%" + ss_idornameinput + "%'";

                sql += " group by C.subj, C.course, C.cyear,  c.grseq, C.courseseq, C.coursenm, c.area, C.year, C.subjseq, C.subjseqgr, c.subjnm, c.isonoff, B.userid,  B.name,B.memberyear,B.sex,B.addr, B.resno1, B.resno2,  B.email, b.handphone, a.samtotal, B.membergubun,\n";
                sql += "      A.isgraduated, C.isbelongcourse,  C.subjcnt,c.biyong,B.resno, b.compnm, b.deptnam \n";
                sql += " ) totalstatus   ) ts\n";

                sql += " where 1=1\n";

                if (!ss_selExam.equals("ALL") && ss_selExam.equals("1")) { // 시험응시
                    sql += " and totexamcnt > '0' and repexamcnt > '0'\n";
                } else if (ss_selExam.equals("0")) { // 시험 미응시
                    sql += " and totexamcnt > '0' and remexamcnt = totexamcnt\n";
                }
                if (!ss_selProj.equals("ALL") && ss_selProj.equals("1")) { // 과제제출
                    sql += " and totprojcnt > '0' and repprojcnt > '0'\n";
                } else if (ss_selProj.equals("0")) { // 과제 미제출
                    sql += " and totprojcnt > '0' and remprojcnt = totprojcnt\n";
                }

                // 출력과 관련이 되어있으므로 정렬주의
                if (v_orderColumn.equals("")) {
                    sql += " order by course, courseseq , name, subj, subjseq\n";
                } else {
                    sql += " order by course, courseseq , name, subj, subjseq,  " + v_orderColumn + v_orderType + "\n";
                }

                Log.info.println("종합 학습현황 = " + sql);

                ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    dbox = ls.getDataBox();

                    dbox.put("d_tstep", new Double(ls.getDouble("tstep")));
                    dbox.put("d_report", new Double(ls.getDouble("report")));
                    dbox.put("d_act", new Double(ls.getDouble("act")));
                    dbox.put("d_mtest", new Double(ls.getDouble("mtest")));
                    dbox.put("d_ftest", new Double(ls.getDouble("ftest")));
                    dbox.put("d_htest", new Double(ls.getDouble("htest")));
                    dbox.put("d_etc1", new Double(ls.getDouble("etc1")));
                    dbox.put("d_etc2", new Double(ls.getDouble("etc2")));
                    dbox.put("d_avtstep", new Double(ls.getDouble("avtstep")));
                    dbox.put("d_avreport", new Double(ls.getDouble("avreport")));
                    dbox.put("d_avact", new Double(ls.getDouble("avact")));
                    dbox.put("d_avmtest", new Double(ls.getDouble("avmtest")));
                    dbox.put("d_avftest", new Double(ls.getDouble("avftest")));
                    dbox.put("d_avhtest", new Double(ls.getDouble("avhtest")));
                    dbox.put("d_avetc1", new Double(ls.getDouble("avetc1")));
                    dbox.put("d_avetc2", new Double(ls.getDouble("avetc2")));
                    dbox.put("d_score", new Double(ls.getDouble("score")));

                    // if (ss_grcode.equals("N000001")) {
                    // ====================================================
                    // 개인정보 복호화
                    /*
                     * SeedCipher seed = new SeedCipher(); if (!dbox.getString("d_resno2").equals("")) dbox.put("d_resno2"
                     * ,seed.decryptAsString(Base64.decode(dbox .getString("d_resno2")), seed.key.getBytes(), "UTF-8")); if
                     * (!dbox.getString("d_email").equals("")) dbox.put("d_email" ,seed.decryptAsString(Base64.decode(dbox .getString("d_email")),
                     * seed.key.getBytes(), "UTF-8")); if (!dbox.getString("d_hometel").equals("")) dbox.put("d_hometel"
                     * ,seed.decryptAsString(Base64.decode(dbox .getString("d_hometel")), seed.key.getBytes(), "UTF-8")); if
                     * (!dbox.getString("d_handphone").equals("")) dbox.put("d_handphone" ,seed.decryptAsString(Base64.decode(
                     * dbox.getString("d_handphone")), seed.key.getBytes(), "UTF-8")); if (!dbox.getString("d_addr2").equals("")) dbox
                     * .put("d_addr2",seed.decryptAsString(Base64.decode(dbox .getString("d_addr2")), seed.key.getBytes(), "UTF-8"));
                     */

                    /*
                     * if (!dbox.getString("d_resno2").equals("")) dbox.put("d_resno2", encryptUtil.decrypt(dbox.getString("d_resno2"))); if
                     * (!dbox.getString("d_email").equals("")) dbox.put("d_email", encryptUtil.decrypt(dbox.getString("d_email"))); if
                     * (!dbox.getString("d_hometel").equals("")) dbox.put("d_hometel", encryptUtil.decrypt(dbox.getString("d_hometel"))); if
                     * (!dbox.getString("d_handphone").equals("")) dbox.put("d_handphone", encryptUtil.decrypt(dbox.getString("d_handphone"))); if
                     * (!dbox.getString("d_addr2").equals("")) dbox.put("d_addr2", encryptUtil.decrypt(dbox.getString("d_addr2")));
                     */
                    // ====================================================
                    // }

                    list.add(dbox);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r" + ex.getMessage() + dbox.getString("d_userid") + ":" + dbox.getString("d_email") + ":"
                    + dbox.getString("d_hometel") + ":" + dbox.getString("d_handphone") + ":" + dbox.getString("d_addr2"));
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
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e10) {
                }
            }
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * 소속별 학습현황 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<StudyStatusData> selectAssignmentLearningList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<StudyStatusData> list = null;
        String sql = "";
        StudyStatusData data = null;
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); // 교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); // 년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); // 교육차수

        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); // 과정분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); // 과정분류
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); // 과정분류

        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");// 과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); // 과정 차수
        String ss_company = box.getStringDefault("s_company", "ALL"); // 회사
        String ss_selgubun = box.getString("s_selgubun"); // 직군별:JIKUN,직급별:JIKUP,사업부별:GPM
        String ss_seltext = box.getStringDefault("s_seltext", "ALL"); // 검색분류별 검색내용
        String ss_seldept = box.getStringDefault("s_seldept", "ALL"); // 사업부별 부서 검색내용
        String ss_action = box.getString("s_action");
        String v_orderColumn = box.getString("p_orderColumn"); // 정렬할 컬럼명

        ManagerAdminBean bean = null;
        String v_sql_add = "";
        String v_userid = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");

        try {
            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list = new ArrayList<StudyStatusData>();

                // select compnm,jikwi,jikwinm,userid,cono,name,tstep,avtstep,
                // report,act,mtest,ftest,score,point,comptel,email
                sql = "select get_compnm(B.comp,2,4) compnm, B.jikwi,get_jikwinm(B.jikwi,B.comp) jikwinm,   ";
                sql += "B.jikup, get_jikupnm(B.jikup, B.comp) jikupnm , B.userid,B.cono,B.name,              ";
                sql += "avg(A.tstep) tstep,avg(A.avtstep) avtstep,                                           ";
                if (GetCodenm.get_config("score_disp").equals("WS")) { // 가중치적용
                    sql += "avg(A.avreport) report,avg(A.avact) act,avg(A.avmtest) mtest,avg(A.avftest) ftest, ";
                } else { // 가중치비적용
                    sql += "avg(A.report) report,avg(A.act) act,avg(A.mtest) mtest,avg(A.ftest) ftest, ";
                }
                sql += "avg(A.score) score,B.comptel,B.email,avg(C.point) point  ";
                sql += "from TZ_STUDENT A,TZ_MEMBER B,VZ_SCSUBJSEQ C,TZ_COMP D where 1=1 ";

                if (!ss_grcode.equals("ALL")) {
                    sql += " and C.grcode = " + SQLString.Format(ss_grcode);
                }
                if (!ss_gyear.equals("ALL")) {
                    sql += " and C.gyear = " + SQLString.Format(ss_gyear);
                }
                if (!ss_grseq.equals("ALL")) {
                    sql += " and C.grseq = " + SQLString.Format(ss_grseq);
                }
                if (!ss_uclass.equals("ALL")) {
                    sql += " and C.scupperclass = " + SQLString.Format(ss_uclass);
                }
                if (!ss_mclass.equals("ALL")) {
                    sql += " and C.scmiddleclass = " + SQLString.Format(ss_mclass);
                }

                if (!ss_lclass.equals("ALL")) {
                    sql += " and C.sclowerclass = " + SQLString.Format(ss_lclass);
                }

                if (!ss_subjcourse.equals("ALL")) {
                    sql += " and C.scsubj = " + SQLString.Format(ss_subjcourse);
                }

                if (!ss_subjseq.equals("ALL")) {
                    sql += " and C.scsubjseq = " + SQLString.Format(ss_subjseq);
                }

                if (!ss_company.equals("ALL")) {
                    sql += " and substr(B.comp, 0 ,4) = '" + ss_company.substring(0, 4) + "'";
                }

                // 부서장일경우
                if (s_gadmin.equals("K6") || s_gadmin.equals("K7")) {
                    bean = new ManagerAdminBean();
                    v_sql_add = bean.getManagerDept(v_userid, s_gadmin);
                    if (!v_sql_add.equals(""))
                        sql += " and B.comp in " + v_sql_add; // 관리부서검색조건쿼리
                }

                if (ss_selgubun.equals("JIKUN") && !ss_seltext.equals("ALL")) { // 직군별
                    sql += " and B.jikun = " + SQLString.Format(ss_seltext);
                } else if (ss_selgubun.equals("JIKUP") && !ss_seltext.equals("ALL")) { // 직급별
                    sql += " and B.jikup = " + SQLString.Format(ss_seltext);
                } else if (ss_selgubun.equals("GPM") && !ss_seltext.equals("ALL")) { // 사업부별
                    sql += " and B.comp like " + SQLString.Format(GetCodenm.get_compval(ss_seltext));
                    if (!ss_seldept.equals("ALL")) {
                        sql += " and D.dept = " + SQLString.Format(ss_seldept);
                    }
                }
                sql += " and B.comp=D.comp and A.userid=B.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq ";
                sql += " group by get_compnm(B.comp,2,4),B.jikwi, get_jikwinm(B.jikwi,B.comp),B.jikup, get_jikupnm(B.jikup,B.comp),B.userid,B.cono,B.name,B.comptel,B.email,B.comp";
                if (!v_orderColumn.equals("")) {
                    v_orderColumn = "B." + v_orderColumn;
                    sql += " order by " + v_orderColumn;
                } else {
                    sql += " order by B.comp,B.jikup,B.userid ";
                }
                // System.out.println("sql============>"+sql);
                ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    data = new StudyStatusData();
                    data.setCompnm(ls.getString("compnm"));
                    data.setJikwi(ls.getString("jikwi"));
                    data.setJikwinm(ls.getString("jikwinm"));
                    data.setJikup(ls.getString("jikup"));
                    data.setJikupnm(ls.getString("jikupnm"));
                    data.setUserid(ls.getString("userid"));
                    data.setCono(ls.getString("cono"));
                    data.setName(ls.getString("name"));
                    data.setTstep(ls.getInt("tstep"));
                    data.setAvtstep(ls.getInt("avtstep"));
                    data.setReport(ls.getInt("report"));
                    data.setAct(ls.getInt("act"));
                    data.setMtest(ls.getInt("mtest"));
                    data.setFtest(ls.getInt("ftest"));
                    data.setScore(ls.getInt("score"));
                    data.setPoint(ls.getInt("point"));
                    data.setComptel(ls.getString("comptel"));
                    data.setEmail(ls.getString("email"));
                    list.add(data);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r" + ex.getMessage());
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
     * 교육그룹별 학습현황 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<StudyStatusData> selectGrcodeLearningList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ResultSet rs2 = null;
        ListSet ls3 = null;
        ArrayList<StudyStatusData> list1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        StudyStatusData data1 = null;
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); // 교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); // 년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); // 교육차수

        String v_orderColumn = box.getString("p_orderColumn");
        String v_orderType = box.getString("p_orderType"); // 정렬할 순서

        String ss_action = box.getString("s_action");

        try {
            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList<StudyStatusData>();

                sql1 = "select  C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.subjseq,C.subjnm, c.subjseqgr,\n";
                sql1 += "        C.isonoff,count(A.subj) as educnt,C.year,\n";
                sql1 += "       avg(A.tstep) tstep, avg(avtstep) avtstep,\n";
                sql1 += "       avg(A.mtest) mtest, avg(avmtest) avmtest,\n";
                sql1 += "       avg(A.ftest) ftest, avg(avftest) avftest,\n";
                sql1 += "       avg(A.htest) htest, avg(avhtest) avhtest,\n";
                sql1 += "       avg(A.report) report, avg(avreport) avreport,\n";
                sql1 += "       avg(A.etc1) etc1, avg(avetc1) avetc1,\n";
                sql1 += "       avg(A.etc2) etc2, avg(avetc2) avetc2,\n";
                sql1 += "       avg(A.act) act, avg(act) act,\n";
                sql1 += "       avg(A.score) score,C.isonoff , c.grseq , D.grseqnm, C.isbelongcourse , C.subjcnt\n";
                sql1 += "  from TZ_STUDENT A,VZ_SCSUBJSEQ C, tz_grseq D\n";
                sql1 += " where A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq\n";
                sql1 += "   and c.grcode=d.grcode and c.gyear=d.gyear and c.grseq=d.grseq\n";

                if (!ss_grcode.equals("ALL"))
                    sql1 += " and C.grcode = " + SQLString.Format(ss_grcode);
                if (!ss_gyear.equals("ALL"))
                    sql1 += " and C.gyear = " + SQLString.Format(ss_gyear);
                if (!ss_grseq.equals("ALL"))
                    sql1 += " and C.grseq = " + SQLString.Format(ss_grseq);

                sql1 += "\n group by C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.subjseq,C.subjnm, c.subjseqgr,\n";
                sql1 += " C.isonoff,C.year, c.grseq,  D.grseqnm , C.isbelongcourse, C.subjcnt\n";

                if (v_orderColumn.equals("subj"))
                    v_orderColumn = "c.subj";
                if (v_orderColumn.equals("grseq"))
                    v_orderColumn = "c.grseq";
                if (v_orderColumn.equals("subjseq"))
                    v_orderColumn = " c.subjseqgr ";
                if (v_orderColumn.equals("isonoff"))
                    v_orderColumn = " C.isonoff ";

                if (v_orderColumn.equals("")) {
                    sql1 += " order by C.course, C.subj,C.year,C.subjseq, subjnm ";
                } else {
                    sql1 += " order by C.course, " + v_orderColumn + v_orderType;
                }

                ls1 = connMgr.executeQuery(sql1);

                // select gradcnt
                sql2 = "select count(isgraduated) gradcnt\n";
                sql2 += "from TZ_STUDENT\n";
                sql2 += "where subj=? and year=? and subjseq=? and isgraduated='Y'\n";
                pstmt2 = connMgr.prepareStatement(sql2);

                while (ls1.next()) {
                    data1 = new StudyStatusData();

                    pstmt2.setString(1, ls1.getString("subj"));
                    pstmt2.setString(2, ls1.getString("year"));
                    pstmt2.setString(3, ls1.getString("subjseq"));
                    rs2 = pstmt2.executeQuery();
                    if (rs2.next()) {
                        data1.setGradcnt(rs2.getInt("gradcnt"));
                    }

                    data1.setGrseq(ls1.getString("grseq"));
                    data1.setGrseqnm(ls1.getString("grseqnm"));
                    data1.setCourse(ls1.getString("course"));
                    data1.setCyear(ls1.getString("cyear"));
                    data1.setCoursenm(ls1.getString("coursenm"));
                    data1.setCourseseq(ls1.getString("courseseq"));
                    data1.setSubj(ls1.getString("subj"));
                    data1.setYear(ls1.getString("year"));
                    data1.setSubjnm(ls1.getString("subjnm"));
                    data1.setSubjseq(ls1.getString("subjseq"));
                    data1.setSubjseqgr(ls1.getString("subjseqgr"));
                    data1.setIsonoff(ls1.getString("isonoff"));
                    data1.setEducnt(ls1.getInt("educnt"));
                    data1.setTstep(ls1.getInt("tstep"));
                    data1.setMtest(ls1.getInt("mtest"));
                    data1.setFtest(ls1.getInt("ftest"));
                    data1.setHtest(ls1.getInt("htest"));
                    data1.setReport(ls1.getInt("report"));
                    data1.setEtc1(ls1.getInt("etc1"));
                    data1.setEtc2(ls1.getInt("etc2"));
                    data1.setAvtstep(ls1.getInt("avtstep"));
                    data1.setAvmtest(ls1.getInt("avmtest"));
                    data1.setAvftest(ls1.getInt("avftest"));
                    data1.setAvhtest(ls1.getInt("avhtest"));
                    data1.setAvreport(ls1.getInt("avreport"));
                    data1.setAvetc1(ls1.getInt("avetc1"));
                    data1.setAvetc2(ls1.getInt("avetc2"));
                    data1.setScore(ls1.getInt("score"));
                    data1.setAct(ls1.getInt("act"));
                    data1.setIsonoff(ls1.getString("isonoff"));
                    data1.setIsbelongcourse(ls1.getString("isbelongcourse"));
                    data1.setSubjcnt(ls1.getInt("subjcnt"));
                    list1.add(data1);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (rs2 != null) {
                try {
                    rs2.close();
                } catch (Exception e) {
                }
            }
            if (ls3 != null) {
                try {
                    ls3.close();
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
        return list1;
    }

    /**
     * 학습시간 조회 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectLearningTimeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); // 교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); // 년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); // 교육차수
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); // 과정분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); // 과정분류
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); // 과정분류
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");// 과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); // 과정 차수
        String ss_action = box.getString("s_action");
        String v_orderColumn = box.getString("p_orderColumn"); // 정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); // 정렬할 순서

        // 2009.11.02 페이징
        int v_pagesize = box.getInt("p_pagesize");
        int v_pageno = box.getInt("p_pageno");
        if (v_pageno == 0)
            v_pageno = 1;

        DataBox dbox = null;

        try {
            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList<DataBox>();

                sql1 = "select B.course,B.cyear,B.courseseq,B.coursenm,B.subj,B.year,B.subjnm,B.subjseq,B.subjseqgr, c.userid,C.name,\n";
                sql1 += " min(A.first_edu) first_edu, max(A.ldate) ldate_end, min(A.ldate) ldate_start,\n";
                sql1 += " case when  C.membergubun = 'P' then  '개인'\n ";
                sql1 += " when  C.membergubun = 'C' then  '기업'\n";
                sql1 += " when  C.membergubun = 'U' then  '대학교'\n";
                sql1 += " when  C.membergubun = 'U' then  '대학교'\n";
                sql1 += " else '-' end   as membergubunnm,\n";

                sql1 += " CEIL( ( sum( to_number(substr(A.total_time, 1, INSTR(A.total_time, ':')-1))) *60*60 + sum( to_number(substr(A.total_time, INSTR(A.total_time, ':')+1, 2)))*60 + sum( to_number(substr(A.total_time, INSTR(A.total_time, ':')+4, 2))) ) / (60*60)) total_time,\n";
                sql1 += " CEIL(MOD((sum( to_number(substr(A.total_time, 1, INSTR(A.total_time, ':')-1))*60*60) + sum(  to_number(substr(A.total_time, INSTR(A.total_time, ':')+1, 2))*60) + sum( to_number(substr(A.total_time, INSTR(A.total_time, ':')+4, 2))) )/60,60)) total_minute,\n";
                sql1 += " MOD( sum( to_number(substr(A.total_time, 1, INSTR(A.total_time, ':')-1))*60*60 +  to_number(substr(A.total_time, INSTR(A.total_time, ':')+1, 2))*60 +  to_number(substr(A.total_time, INSTR(A.total_time, ':')+4, 2))) ,60) total_sec, ";

                sql1 += " B.isonoff , B.isbelongcourse , B.subjcnt\n";
                sql1 += "from TZ_PROGRESS A,VZ_SCSUBJSEQ B,TZ_MEMBER C , tz_student d where 1 = 1\n";

                if (!ss_grcode.equals("ALL"))
                    sql1 += " and B.grcode = " + SQLString.Format(ss_grcode);
                if (!ss_gyear.equals("ALL"))
                    sql1 += " and B.gyear = " + SQLString.Format(ss_gyear);
                if (!ss_grseq.equals("ALL"))
                    sql1 += " and B.grseq = " + SQLString.Format(ss_grseq);
                if (!ss_uclass.equals("ALL"))
                    sql1 += " and B.scupperclass = " + SQLString.Format(ss_uclass);
                if (!ss_mclass.equals("ALL"))
                    sql1 += " and B.scmiddleclass = " + SQLString.Format(ss_mclass);
                if (!ss_lclass.equals("ALL"))
                    sql1 += " and B.sclowerclass = " + SQLString.Format(ss_lclass);
                if (!ss_subjcourse.equals("ALL"))
                    sql1 += " and B.scsubj = " + SQLString.Format(ss_subjcourse);
                if (!ss_subjseq.equals("ALL"))
                    sql1 += " and B.scsubjseq = " + SQLString.Format(ss_subjseq);

                // sql1+= " and A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq and A.userid=C.userid ";
                sql1 += " and D.userid  = C.userid\n";
                sql1 += " and D.subj    = B.subj\n";
                sql1 += " and D.year    = B.year	\n";
                sql1 += " and D.subjseq = B.subjseq\n";

                sql1 += " and D.subj    = A.subj(+)\n";
                sql1 += " and D.subjseq = A.subjseq(+)\n";
                sql1 += " and D.year    = A.year(+)\n";
                sql1 += " and D.userid  = A.userid(+)\n";

                sql1 += " group by B.course,B.cyear,B.courseseq,B.coursenm,B.subj,B.year,B.subjnm,B.subjseq,B.subjseqgr, c.userid,C.name,B.isonoff, C.membergubun , B.isbelongcourse , B.subjcnt\n";

                if (v_orderColumn.equals("subj"))
                    v_orderColumn = " b.subj";
                if (v_orderColumn.equals("userid"))
                    v_orderColumn = " c.userid";
                if (v_orderColumn.equals("name"))
                    v_orderColumn = " c.name";
                if (v_orderColumn.equals("subjseq"))
                    v_orderColumn = " b.subjseqgr ";
                if (v_orderColumn.equals("isonoff"))
                    v_orderColumn = " b.isonoff ";

                if (v_orderColumn.equals("")) {
                    sql1 += " order by B.course,  B.courseseq, C.name ,  B.subj,  B.subjseq ";
                } else {
                    sql1 += " order by B.course,  B.courseseq, C.name ," + v_orderColumn + v_orderType;
                }

                // sql1+= " order by B.course,B.cyear,B.courseseq,B.subj,B.year,B.subjseq,A.userid ";

                Log.info.println("학습시간조회:" + sql1);

                ls1 = connMgr.executeQuery(sql1);

                ls1.setPageSize(v_pagesize); // 페이지당 row 갯수를 세팅한다
                ls1.setCurrentPage(v_pageno); // 현재페이지번호를 세팅한다.
                int totalpagecount = ls1.getTotalPage(); // 전체 페이지 수를 반환한다
                int totalrowcount = ls1.getTotalCount(); // 전체 row 수를 반환한다

                while (ls1.next()) {
                    // data1=new StudyStatusData();
                    // data1.setCourse(ls1.getString("course"));
                    // data1.setCyear(ls1.getString("cyear"));
                    // data1.setCourseseq(ls1.getString("courseseq"));
                    // data1.setCoursenm(ls1.getString("coursenm"));
                    // data1.setSubj(ls1.getString("subj"));
                    // data1.setYear(ls1.getString("year"));
                    // data1.setSubjseq(ls1.getString("subjseq"));
                    // data1.setSubjseqgr(ls1.getString("subjseqgr"));
                    // data1.setSubjnm(ls1.getString("subjnm"));
                    // data1.setUserid(ls1.getString("userid"));
                    // data1.setName(ls1.getString("name"));
                    // data1.setMembergubunnm(ls1.getString("membergubunnm"));
                    // data1.setFirstedu(ls1.getString("first_edu"));
                    // data1.setTotaltime(ls1.getString("total_time"));
                    // data1.setTotalminute(ls1.getString("total_minute"));
                    // data1.setTotalsec(ls1.getString("total_sec"));
                    // data1.setLdatestart(ls1.getString("ldate_start"));
                    // data1.setLdateend(ls1.getString("ldate_end"));
                    // data1.setIsonoff(ls1.getString("isonoff"));
                    // data1.setIsbelongcourse(ls1.getString("isbelongcourse"));
                    // data1.setSubjcnt(ls1.getInt("subjcnt"));
                    // list1.add(data1);

                    dbox = ls1.getDataBox();

                    // dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                    dbox.put("d_dispnum", new Integer(totalrowcount - ls1.getRowNum() + 1));
                    dbox.put("d_totalpage", new Integer(totalpagecount));
                    dbox.put("d_totalrowcount", new Integer(totalrowcount));
                    dbox.put("d_rowcount", new Integer(row));

                    list1.add(dbox);

                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r" + ex.getMessage());
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
     * 폼메일 발송
     * 
     * @param box receive from the form object and session
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int sendFormMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int cnt = 0; // 메일발송이 성공한 사람수
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); // 교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); // 년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); // 교육차수
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); // 과정분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); // 과정분류
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); // 과정분류
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");// 과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); // 과정 차수
        // p_checks로 넘어온 다수의 value를 처리하기 위해 vector로 구현
        Vector v_checks = new Vector();
        v_checks = box.getVector("p_checks");
        Enumeration em = v_checks.elements();
        String v_userid = "";

        try {
            connMgr = new DBConnectionManager();

            // ////////////////// 폼메일 발송
            // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            String v_sendhtml = "mail3.html";
            FormMail fmail = new FormMail(v_sendhtml); // 폼메일발송인 경우
            MailSet mset = new MailSet(box); // 메일 세팅 및 발송
            String v_mailTitle = "안녕하세요? 사이버러닝센터 운영자입니다.(진도율안내)";
            // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            while (em.hasMoreElements()) {
                v_userid = (String) em.nextElement();

                // select subjnm,passday,tstep,gradstep,gradscore,name,ismailing,cono,email
                sql = "select  B.subjnm,A.tstep,B.gradstep,B.sgradscore gradscore,D.name,D.ismailing,D.cono,D.email,";
                sql += "        to_char(sysdate, 'YYYYMMDD') nowday, substr(B.edustart,1,8) eduday                 ";
                sql += " from TZ_STUDENT A,VZ_SCSUBJSEQ B,TZ_MEMBER D ";
                sql += " where A.userid = " + SQLString.Format(v_userid);
                if (!ss_grcode.equals("ALL")) {
                    sql += " and B.grcode = " + SQLString.Format(ss_grcode);
                }
                if (!ss_gyear.equals("ALL")) {
                    sql += " and B.gyear = " + SQLString.Format(ss_gyear);
                }
                if (!ss_grseq.equals("ALL")) {
                    sql += " and B.grseq = " + SQLString.Format(ss_grseq);
                }
                if (!ss_uclass.equals("ALL")) {
                    sql += " and B.scupperclass = " + SQLString.Format(ss_uclass);
                }
                if (!ss_mclass.equals("ALL")) {
                    sql += " and B.scmiddleclass = " + SQLString.Format(ss_mclass);
                }
                if (!ss_lclass.equals("ALL")) {
                    sql += " and B.sclowerclass = " + SQLString.Format(ss_lclass);
                }

                if (!ss_subjcourse.equals("ALL")) {
                    sql += " and B.scsubj = " + SQLString.Format(ss_subjcourse);
                }
                if (!ss_subjseq.equals("ALL")) {
                    sql += " and B.scsubjseq = " + SQLString.Format(ss_subjseq);
                }
                sql += " and A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq and A.userid=D.userid ";
                sql += " group by B.subjnm,A.tstep,B.gradstep,B.sgradscore,D.name,D.ismailing,D.cono,D.email,B.edustart,B.eduend ";
                ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    String v_toEmail = ls.getString("email");
                    String v_toCono = ls.getString("cono");
                    String v_ismailing = ls.getString("ismailing");

                    int passday = FormatDate.datediff("d", ls.getString("eduday"), ls.getString("nowday"));
                    // String v_toEmail = "jj1004@dreamwiz.com";

                    mset.setSender(fmail); // 메일보내는 사람 세팅

                    fmail.setVariable("tstep", ls.getString("tstep"));
                    fmail.setVariable("subjnm", ls.getString("subjnm"));
                    fmail.setVariable("passday", String.valueOf(passday));
                    fmail.setVariable("tstep", ls.getString("tstep"));
                    fmail.setVariable("gradstep", ls.getString("gradstep"));
                    fmail.setVariable("gradscore", ls.getString("gradscore"));
                    fmail.setVariable("toname", ls.getString("name"));

                    String v_mailContent = fmail.getNewMailContent();
                    // System.out.println("ismailing" + ls.getString("ismailing"));

                    boolean isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, v_ismailing, v_sendhtml);
                    if (isMailed)
                        cnt++; // 메일발송에 성공하면
                }

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r" + ex.getMessage());
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
     * 유저정보 수정하여 저장할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     */
    public int ChangeUserInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 0;

        String v_searchtext = box.getString("p_searchtext");
        String v_eng_name = box.getString("p_eng_name");
        String v_email = box.getString("p_email");
        String v_pwd = box.getString("p_pwd");
        String v_hometel = box.getString("p_hometel");
        String v_comptel = box.getString("p_comptel");
        String v_handphone = box.getString("p_handphone");
        String v_post1 = box.getString("p_post1");
        String v_post2 = box.getString("p_post2");
        String v_addr = box.getString("p_addr");
        String v_addr2 = box.getString("p_addr2");
        String v_comp_post1 = box.getString("p_comp_post1");
        String v_comp_post2 = box.getString("p_comp_post2");
        String v_comp_addr1 = box.getString("p_comp_addr1");
        String v_comp_addr2 = box.getString("p_comp_addr2");
        String v_comptext = box.getString("p_comptext");
        String v_jikup = box.getString("p_jikup");
        String v_degree = box.getString("p_degree");
        String v_ismailing = box.getString("p_ismailing");
        String v_islettering = box.getString("p_islettering");
        String v_isopening = box.getString("p_isopening");
        String v_state = box.getString("p_state");

        // 튜터 데이터 (암호화하지않음)
        String vv_email = v_email;
        String vv_addr2 = v_addr2;
        String vv_handphone = v_handphone;

        try {
            connMgr = new DBConnectionManager();

            sql1 = " update TZ_MEMBER set email = ?, pwd = ?, comptel = ?, hometel = ?, handphone = ?,   ";
            sql1 += "			comptext = ?, post1 = ?, post2 = ?, addr = ?, addr2 = ?,  ";
            sql1 += "			comp_post1 = ?, comp_post2 = ?, comp_addr1 = ?, comp_addr2 = ?,  ";
            sql1 += "			jikup = ?,  degree= ? , ismailing = ? , islettering = ? , isopening= ? , ";
            sql1 += "			eng_name = ? , state = ?    ";
            sql1 += "  where userid = ?              ";

            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_email);
            pstmt1.setString(2, v_pwd);
            pstmt1.setString(3, v_comptel);
            pstmt1.setString(4, v_hometel);
            pstmt1.setString(5, v_handphone);
            pstmt1.setString(6, v_comptext);
            pstmt1.setString(7, v_post1);
            pstmt1.setString(8, v_post2);
            pstmt1.setString(9, v_addr);
            pstmt1.setString(10, v_addr2);
            pstmt1.setString(11, v_comp_post1);
            pstmt1.setString(12, v_comp_post2);
            pstmt1.setString(13, v_comp_addr1);
            pstmt1.setString(14, v_comp_addr2);
            pstmt1.setString(15, v_jikup);
            pstmt1.setString(16, v_degree);
            pstmt1.setString(17, v_ismailing);
            pstmt1.setString(18, v_islettering);
            pstmt1.setString(19, v_isopening);
            pstmt1.setString(20, v_eng_name);
            pstmt1.setString(21, v_state);
            pstmt1.setString(22, v_searchtext);

            isOk1 = pstmt1.executeUpdate();

            // 튜터테이블도 반영( 있을경우만)
            if (isOk1 > 0) {
                sql2 = " update TZ_TUTOR set email = ?, phone = ?, handphone= ?,      ";
                sql2 += " 			post1 = ?, post2 = ?, add1 = ?, add2 = ? ";
                sql2 += "  where userid = ?                                            ";
                pstmt2 = connMgr.prepareStatement(sql2);

                pstmt2.setString(1, vv_email);
                pstmt2.setString(2, v_comptel);
                pstmt2.setString(3, vv_handphone);
                pstmt2.setString(4, v_post1);
                pstmt2.setString(5, v_post2);
                pstmt2.setString(6, v_addr);
                pstmt2.setString(7, vv_addr2);
                pstmt2.setString(8, v_searchtext);
                pstmt2.executeUpdate();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
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
        return isOk1;
    }

    public int YeunsunoDelete(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        int isOk1 = 0;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_userid = box.getString("pp_userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql1 = " delete from TZ_PROPOSE_ADDINFO      ";
            sql1 += " where subj = ? and  year = ? and subjseq = ? and userid = ? ";
            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_subj);
            pstmt1.setString(2, v_year);
            pstmt1.setString(3, v_subjseq);
            pstmt1.setString(4, v_userid);
            isOk1 = pstmt1.executeUpdate();

            if (isOk1 > 0)
                connMgr.commit();
            else
                connMgr.rollback();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r" + ex.getMessage());
        } finally {
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
        return isOk1;
    }
}