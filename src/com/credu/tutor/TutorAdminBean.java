//**********************************************************
//  1. 제      목: TUTOR ADMIN BEAN
//  2. 프로그램명: TutorAdminBean.java
//  3. 개      요: 강사관리 관리자 bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정: Total_row_count 를 위해 쿼리 패턴 수정.
//**********************************************************
package com.credu.tutor;

import java.io.File;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.FileMove;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

public class TutorAdminBean {
    //	@SuppressWarnings("unused")
    private ConfigSet config;
    private int row;

    private static final String FILE_TYPE = "p_file"; //      파일업로드되는 tag name
    private static final int FILE_LIMIT = 3; //    페이지에 세팅된 파일첨부 갯수

    public TutorAdminBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.manage.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
            //row = 100;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 강사 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectTutorList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        /* 2005.11.10_하경태 : TotalCount 해주기 위한 쿼리를 담을 변수 " */
        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        DataBox dbox = null;

        String v_gubun = box.getString("p_gubun"); //강사구분 검색
        //        String v_select         = box.getString("p_select");        //검색항목(과정명1,강사명2)
        String v_selectvalue = box.getString("p_selectvalue"); //검색어
        //        String v_subjclass      = box.getString("p_subjclass");    //강의분야

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            head_sql = "";
            body_sql = "";

            head_sql += "SELECT	A.USERID AS USERID, \n";
            head_sql += "	A.NAME NAME, \n";
            head_sql += "	A.COMP, \n";
            head_sql += "	A.DEPT, \n";
            head_sql += "	A.EMAIL, \n";
            head_sql += "	A.ISGUBUN AS ISGUBUN, \n";
            head_sql += "	A.ISMANAGER, \n";
            head_sql += "	CASE A.HANDPHONE \n";
            head_sql += "		WHEN NULL THEN A.PHONE \n";
            head_sql += "		WHEN '' THEN A.PHONE \n";
            head_sql += "		ELSE A.HANDPHONE \n";
            head_sql += "	END AS HANDPHONE, \n";
            head_sql += "	CASE NVL (C.COMP, '') \n";
            head_sql += "		WHEN '' THEN '' \n";
            head_sql += "		ELSE GET_COMPNM (C.COMP,2,2) \n";
            head_sql += "	END AS COMPNM, \n";
            head_sql += "	CASE C.HANDPHONE \n";
            head_sql += "		WHEN NULL THEN CRYPTO.DEC('normal', C.COMPTEL) \n";
            head_sql += "		WHEN '' THEN CRYPTO.DEC('normal', C.COMPTEL) \n";
            head_sql += "		ELSE CRYPTO.DEC('normal', C.HANDPHONE) \n";
            head_sql += "	END AS MEMHANDPHONE, \n";
            head_sql += "	CRYPTO.DEC('normal', C.EMAIL) AS MEMEMAIL, \n";
            head_sql += "	NVL (D.FMON, '') AS FMON, \n";
            head_sql += "	NVL (D.TMON, '') AS TMON, \n";
            head_sql += "	C.PWD AS PWD \n";

            body_sql += "FROM \n";
            body_sql += "	TZ_TUTOR A, \n";
            body_sql += "	TZ_MEMBER C, \n";
            body_sql += "	(SELECT USERID, FMON, TMON FROM TZ_MANAGER WHERE GADMIN = 'P1') D \n";
            body_sql += "WHERE \n";
            body_sql += "	A.USERID = C.USERID(+) \n";
            body_sql += "	AND A.USERID = D.USERID(+) \n";
            body_sql += "	AND A.GRCODE = C.GRCODE \n";

            if (!v_selectvalue.equals("")) {
                body_sql += " and upper(A.name) like upper('%" + v_selectvalue + "%') ";
            }

            if (!v_gubun.equals("")) {
                body_sql += " and A.isgubun='" + v_gubun + "' ";
            }

            if (v_orderColumn.equals("")) {
                order_sql += " order by A.name ";
            } else {
                order_sql += " order by " + v_orderColumn + v_orderType;
            }

            sql = head_sql + body_sql + group_sql + order_sql;

            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);

            //System.out.println("sql======================>"+sql);
            System.out.println("row: " + row);
            ls.setCurrentPage(v_pageno, total_row_count); //현재페이지번호를 세팅한다.
            int totalpagecount = ls.getTotalPage(); //전체 페이지 수를 반환한다
            int totalrowcount = ls.getTotalCount(); //전체 row수를 반환한다

            ls.setPageSize(v_pagesize); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpagecount", new Integer(totalpagecount));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(total_row_count));

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
        // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);
        return list;
    }

    /**
     * 강사 과정운영 정보
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectTutorSubjHistoryList(RequestBox box, String v_tutorid) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;
        int v_pay = 0;
        int v_price = 0;
        int v_addprice = 0;
        int v_grayncnt = 0;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " select c.subj,c.year,c.subjnm,c.subjseq, c.edustart, c.eduend, c.isclosed, ";
            sql += "        b.tutorgubun, d.price, d.addprice ";
            sql += "        , (select count(userid) from TZ_STUDENT where subj=a.subj and year=a.year and subjseq=a.subjseq and class=a.class) stucnt";
            sql += "        , (select count(userid) from TZ_STUDENT where subj=a.subj and year=a.year and subjseq=a.subjseq and class=a.class and ISGRADUATED='Y') grayncnt";
            sql += "  from TZ_CLASSTUTOR a,TZ_SUBJSEQ c, TZ_TUTOR b, TZ_TUTORPAY d                                        ";
            sql += "  where a.tuserid=b.userid and a.subj=c.subj and a.year=c.year and a.subjseq=c.subjseq and b.tutorgubun = d.tutorcode ";
            sql += "  and   a.tuserid = '" + v_tutorid + "'  \n";
            sql += "order by edustart desc \n";
            //			System.out.println(sql);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

                v_price = ls.getInt("price");
                v_addprice = ls.getInt("addprice");
                v_grayncnt = ls.getInt("grayncnt");
                v_pay = (v_price * v_grayncnt) + v_addprice;
                dbox.put("d_pay", v_pay + "");

                list.add(dbox);
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
        return list;
    }

    /**
     * 강사 조회
     * 
     * @param box receive from the form object and session
     * @return TutorData
     */
    public DataBox selectTutor(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ListSet ls3 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        //        TutorData data      = null;
        DataBox dbox = null;

        Vector<String> photoVector = new Vector<String>();
        Vector<String> newphotoVector = new Vector<String>();

        String v_userid = box.getString("p_userid");
        String v_grcode = "";
        //        String v_phototerms = "";
        //        String v_resno      = "";
        //        String v_sex        = "";
        try {
            connMgr = new DBConnectionManager();

            //select * from tz_tutor
            sql1 = " select a.grcode, a.userid, a.name, b.pwd, a.post1, a.post2, a.add1, a.add2, \n";
            //			 수정일 : 05.11.03 수정자 : 이나연
            //          sql1 += "		 decode(a.tutorgubun,'I',b.comptel,a.phone) phone, ";
            sql1 += "		 case a.tutorgubun ";
            sql1 += "				When 'I' 	Then b.comptel ";
            sql1 += "				Else a.phone ";
            sql1 += "		 End as phone, \n";
            //          sql1 += "		 decode(a.tutorgubun,'I',b.handphone,a.handphone) handphone, ";
            sql1 += "		 case a.tutorgubun ";
            sql1 += "				When 'I' 	Then b.handphone ";
            sql1 += "				Else a.handphone ";
            sql1 += "		 End as handphone, \n";
            sql1 += "		 a.fax, \n";
            //          sql1 += "		 decode(a.tutorgubun,'I',b.email,a.email ) email, \n";
            sql1 += "		 case a.tutorgubun ";
            sql1 += "				When 'I' 	Then b.email ";
            sql1 += "				Else a.email ";
            sql1 += "		 End as email, \n ";
            sql1 += "        a.compcd, \n";
            //          sql1 += "		 decode(a.tutorgubun,'I',replace(get_compnm(b.comp,2,2),'/','') ,a.comp ) comp, \n";
            sql1 += "		 case a.tutorgubun ";
            sql1 += "				When 'I' 	Then replace(get_compnm(b.comp,2,2),'/','') ";
            sql1 += "				Else a.comp ";
            sql1 += "		 End as comp, \n ";
            //          sql1 += "		 decode(a.tutorgubun,'I',get_jikwinm(b.jikwi,b.comp),a.jik ) jik, \n";
            sql1 += "		 case a.tutorgubun ";
            sql1 += "				When 'I' 	Then get_jikwinm(b.jikwi,b.comp) ";
            sql1 += "				Else a.jik ";
            sql1 += "		 End as jik, \n ";
            sql1 += "		 a.dept, a.academic, a.major, a.isadd, a.iscyber, a.isgubun, a.isgubuntype,       \n";
            sql1 += "        a.isstatus, a.istutor, a.careeryear, a.license, a.career, a.book, a.professional, a.charge, a.isinfo,     \n";
            sql1 += "        a.etc, a.phone,\n";
            sql1 += "        a.photo, a.newphoto, \n";
            //          sql1 += "		 decode(a.tutorgubun,'I',b.resno,a.resno) resno, \n";
            sql1 += "		 case a.tutorgubun ";
            sql1 += "				When 'I' 	Then b.resno ";
            sql1 += "				Else a.resno ";
            sql1 += "		 End as resno, \n ";
            sql1 += "		 a.intro,ismanager,  \n";
            sql1 += "        a.subjclass, \n";
            sql1 += "    	  (select codenm from tz_code where gubun = '0039' and code = a.subjclass) subjclassnm \n";
            sql1 += "   from TZ_TUTOR a, TZ_MEMBER b \n";
            //          sql1 += "  where a.userid = b.userid(+) and a.userid = '"+v_userid+"' \n";
            sql1 += "  where a.userid = b.userid(+) and a.userid = '" + v_userid + "' \n";
            sql1 += "  and a.grcode = b.grcode(+)  \n";
            // 수정일 : 05.11.03 수정자 : 이나연 _ 여기까지 decode 수정

            ls1 = connMgr.executeQuery(sql1);

            System.out.println("selectTutor : " + sql1);

            if (ls1.next()) {
                //                data=new TutorData();
                dbox = ls1.getDataBox();

                v_grcode = ls1.getString("grcode");
                dbox.put("grcode", ls1.getString("grcode"));

                //교육그룹명 select
                sql2 = "select grcodenm from TZ_GRCODE where grcode = '" + v_grcode + "'";
                ls2 = connMgr.executeQuery(sql2);
                if (ls2.next()) {
                    dbox.put("grcodenm", ls2.getString("grcodenm"));

                } else {
                    dbox.put("grcodenm", "전체");
                }
                ls2.close();

                //강사권한 select
                sql3 = "select fmon,tmon from TZ_MANAGER where userid = '" + v_userid + "' and gadmin='P1'";
                ls3 = connMgr.executeQuery(sql3);

                if (ls3.next()) {
                    dbox.put("d_managerchk", new Integer(1));//data.setManagerchk(1);
                    dbox.put("d_fmon", ls3.getString("fmon"));//data.setFmon();
                    dbox.put("d_tmon", ls3.getString("tmon"));//data.setTmon();
                } else {
                    dbox.put("managerchk", new Integer(0));
                }

                ls3.close();

                dbox.put("userid", ls1.getString("userid"));
                dbox.put("name", ls1.getString("name"));
                dbox.put("d_resno", ls1.getString("resno"));
                dbox.put("d_pwd", ls1.getString("pwd"));
                dbox.put("post1", ls1.getString("post1"));
                dbox.put("post2", ls1.getString("post2"));
                dbox.put("add1", ls1.getString("add1"));
                dbox.put("add2", ls1.getString("add2"));
                dbox.put("phone", ls1.getString("phone"));
                dbox.put("handphone", ls1.getString("handphone"));
                dbox.put("email", ls1.getString("email"));
                dbox.put("comp", ls1.getString("comp"));
                dbox.put("dept", ls1.getString("dept"));
                dbox.put("jik", ls1.getString("jik"));

                dbox.put("compcd", ls1.getString("compcd"));
                dbox.put("fax", ls1.getString("fax"));
                dbox.put("academic", ls1.getString("academic"));
                dbox.put("major", ls1.getString("major"));
                dbox.put("isadd", ls1.getString("isadd"));
                dbox.put("iscyber", ls1.getString("iscyber"));
                dbox.put("isgubun", ls1.getString("isgubun"));
                dbox.put("isstatus", ls1.getString("isstatus"));
                dbox.put("istutor", ls1.getString("istutor"));
                dbox.put("career", ls1.getString("career"));
                dbox.put("book", ls1.getString("book"));
                dbox.put("professional", ls1.getString("professional"));
                dbox.put("charge", ls1.getString("charge"));
                dbox.put("isinfo", ls1.getString("isinfo"));
                dbox.put("intro", ls1.getString("intro"));
                dbox.put("ismanager", ls1.getString("ismanager"));
                dbox.put("subjclass", ls1.getString("subjclass"));
                dbox.put("d_subjclassnm", ls1.getString("subjclassnm"));

                //				dbox.put("d_photo",ls1.getString("photo"));
                //				dbox.put("d_newphoto",ls1.getString("newphoto"));

                photoVector.addElement(dbox.getString("d_photo"));
                newphotoVector.addElement(dbox.getString("d_newphoto"));

            }

            if (photoVector != null)
                dbox.put("d_photo", photoVector);
            if (newphotoVector != null)
                dbox.put("d_newphoto", newphotoVector);

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
            if (ls3 != null) {
                try {
                    ls3.close();
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
     * 강사등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int insertTutor(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        StringBuffer sql = new StringBuffer();
        int isOk1 = 0;
        //        int isOk2           = 0;
        //		int isOK3			= 0;
        int v_result = 0;
        String v_user_id = box.getSession("userid");

        String v_manager = box.getString("p_manager");
        String v_userid = box.getString("p_userid"); //사용자ID
        String v_name = box.getString("p_name"); //이름
        String v_subjclass = box.getString("p_subjclass"); //강의 분야

        //        String v_sdate      = box.getString("p_sdate");		//권한사용시작일
        //        String v_ldate      = box.getString("p_ldate");		//권한사용종료일

        String v_post1 = box.getString("p_post1"); //우편번호
        String v_post2 = box.getString("p_post2");
        String v_add1 = box.getString("p_addr"); //주소
        String v_add2 = box.getString("p_addr2"); //주소

        String v_phone = box.getString("p_phone");
        String v_handphone = box.getString("p_handphone");

        String v_email = box.getString("p_email");
        String v_comp = box.getString("p_comp");

        String v_dept = box.getString("p_dept");
        String v_jik = box.getString("p_jik");
        String v_intro = box.getString("p_intro");
        String v_academic = box.getString("p_academic");
        String v_major = box.getString("p_major");
        String v_isGubun = box.getString("p_isgubun");

        String v_grcode = box.getString("p_grcode");

        String v_tutorgubun = "";

        if (v_isGubun.equals("1")) {
            v_tutorgubun = "I"; //사내강사
        } else if (v_isGubun.equals("2")) {
            v_tutorgubun = "O"; //사외강사
        } else if (v_isGubun.equals("3")) {
            v_tutorgubun = "G"; //그룹사강사
        }

        String v_career = box.getString("p_career");
        String v_book = box.getString("p_book");
        //        String v_professional=box.getString("p_professional");
        String v_charge = box.getString("p_charge");
        String v_isinfo = box.getString("p_isinfo");
        String v_compcd = box.getString("p_compcd");

        //사외강사중 권한부여일 경우 TZ_MEMBER 테이블에 Insert한다.
        //		String v_loginid	= box.getString("p_userid");
        //		String v_loginpw	= box.getString("p_loginpw");

        String v_target_id = v_userid;
        String v_resno = box.getString("p_resno");
        //		String v_newphoto   = box.getString("");

        try {

            //강사의 중복여부 조회
            v_result = overlapping(box, v_userid);
            if (v_result == 1) {
                return 0;
            }

            /*
             * 사외 강사일때는 권한이 있으면 p_userid는 resno로 들어간다. if
             * (v_isGubun.equals("2")) { if (v_manager.equals("Y")) {
             * v_target_id = v_loginid; v_resno = v_userid; } }
             */
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //insert TZ_TUTOR table
            sql.append("INSERT INTO TZ_TUTOR(	 			");
            sql.append("	USERID,	NAME, SEX, 				");
            sql.append("	POST1, POST2, ADD1, 			");
            sql.append("	ADD2, PHONE, HANDPHONE, 		");
            sql.append("	FAX, EMAIL, COMP, 				");
            sql.append("	DEPT, JIK, ACADEMIC, 			");
            sql.append("	MAJOR, ISCYBER, ISGUBUN, 		");
            sql.append("	ISGUBUNTYPE, LICENSE, CAREER, 	");
            sql.append("	BOOK, GRCODE, PROFESSIONAL, 	");
            sql.append("	CHARGE, ISINFO, ETC, 			");
            sql.append("	PHOTO, INDATE, LUSERID, 		");
            sql.append("	LDATE, PHOTOTERMS, COMPCD, 		");
            sql.append("	TUTORGUBUN, INTRO, SUBJCLASS, 	");
            sql.append("	ISMANAGER, RESNO) 				");
            sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 								");
            sql.append("	?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, null, 										");
            sql.append("    TO_CHAR(SYSDATE, 'YYYYMMDD'), ?, TO_CHAR(SYSDATE, 'YYYYMMDD'),?,?,?,?,?,?,?) 	");
            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(1, v_target_id);
            pstmt.setString(2, v_name);
            pstmt.setString(3, ""); //v_sex
            pstmt.setString(4, v_post1);
            pstmt.setString(5, v_post2);
            pstmt.setString(6, v_add1);
            pstmt.setString(7, v_add2);
            pstmt.setString(8, v_phone);
            pstmt.setString(9, v_handphone);
            pstmt.setString(10, ""); //v_fax
            pstmt.setString(11, v_email);
            pstmt.setString(12, v_comp);
            pstmt.setString(13, v_dept);
            pstmt.setString(14, v_jik);
            pstmt.setString(15, v_academic);
            pstmt.setString(16, v_major);
            pstmt.setString(17, "");//v_isCyber
            pstmt.setString(18, v_isGubun);
            pstmt.setString(19, ""); //v_isGubuntype
            pstmt.setString(20, ""); //v_license
            pstmt.setString(21, v_career);
            pstmt.setString(22, v_book);
            pstmt.setString(23, v_grcode); //v_grcode
            pstmt.setString(24, ""); //v_professional
            pstmt.setString(25, v_charge);
            pstmt.setString(26, v_isinfo);
            pstmt.setString(27, ""); //v_etc

            /*
             * 현재 사진 파일 들어가는 부분은 null로 무시했음, 추후 추가 바람. (060111)
             * pstmt.setString(28,""); //file pstmt.setString(29, v_user_id);
             * pstmt.setString(30, ""); //v_phototerms pstmt.setString(31,
             * v_compcd); pstmt.setString(32, v_tutorgubun); pstmt.setString(33,
             * v_intro); pstmt.setString(34, v_subjclass); pstmt.setString(35,
             * v_manager); pstmt.setString(36, v_resno);
             */

            pstmt.setString(28, v_user_id);
            pstmt.setString(29, ""); //v_phototerms
            pstmt.setString(30, v_compcd);
            pstmt.setString(31, v_tutorgubun);
            pstmt.setString(32, v_intro);
            pstmt.setString(33, v_subjclass);
            pstmt.setString(34, v_manager);
            pstmt.setString(35, v_resno);
            //			pstmt.setString(36, v_newphoto);

            isOk1 = pstmt.executeUpdate(); //TZ_TUTOR 등록

            //            isOk2 =
            insertTutorSubj(connMgr, box, v_target_id); //TZ_SUBJMAN 등록

            //			isOK3 =
            insertUpFile(connMgr, box, v_target_id); //사진파일 업로드

            // 강사권한 부여시 권한 등록
            if (v_manager.equals("Y")) {
                this.insertManager(connMgr, box, v_target_id); //TZ_MANAGER 등록
            }

            // 사외강사, 그룹강사일 경우 회원 등록
            if (v_isGubun.equals("2") || v_isGubun.equals("3")) {
                //Member TABLE에 Insert
                //this.insertMember(connMgr,box,v_target_id);             //TZ_MEMBER 등록
            }

            if (isOk1 > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        //        return isOk1 * isOk2;
        return isOk1;
    }

    /**
     * 갤러리 새로운 자료파일 등록
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */

    public int insertUpFile(DBConnectionManager connMgr, RequestBox box, String v_target_id) throws Exception {
        ListSet ls1 = null;
        // DataBox dbox = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        int isOk1 = 1;
        //			String v_realfile1  = null;
        //			String v_savefile1  = null;

        //----------------------   업로드되는 파일의 형식을	알고 코딩해야한다  --------------------------------

        String[] v_realFileName = new String[FILE_LIMIT];
        String[] v_newFileName = new String[FILE_LIMIT];

        for (int i = 0; i < FILE_LIMIT; i++) {
            v_realFileName[i] = box.getRealFileName(FILE_TYPE + (i + 1));
            v_newFileName[i] = box.getNewFileName(FILE_TYPE + (i + 1));
        }

        //----------------------------------------------------------------------------------------------------------------------------

        try {
            //////////////////////////////////	 파일 table	에 입력	 ///////////////////////////////////////////////////////////////////
            sql1 = "select newphoto from TZ_TUTOR where userid='" + box.get("p_userid") + "'";
            ls1 = connMgr.executeQuery(sql1);
            String v_newphoto = "";

            if (ls1.next()) {
                // dbox = ls1.getDataBox();

                v_newphoto = ls1.getString("newphoto");
                ls1.close();
            }

            sql1 = "update	TZ_TUTOR set photo = ?, newphoto = ? where userid = ? ";
            pstmt1 = connMgr.prepareStatement(sql1);

            for (int i = 0; i < FILE_LIMIT; i++) {
                if (!v_realFileName[i].equals("")) {

                    if (!v_realFileName[0].equals("")) { //		실제 업로드	되는 파일만	체크해서 db에 입력한다
                        ConfigSet conf = new ConfigSet();

                        String v_tempPath = conf.getProperty("dir.home") + conf.getProperty("dir.upload.instructor").replace("\\\\", "\\"); //upload된 파일 위치
                        // boolean b = false;

                        File newDir = new File(v_tempPath);
                        if (!newDir.exists()) {
                            newDir.mkdir();
                        }

                        String deleteFile = conf.getProperty("dir.home") + v_newphoto;
                        File fd = new File(deleteFile.replace("\\\\", "\\"));
                        if (fd.exists())
                            fd.delete();
                        fd = null;

                        StringBuffer sb1 = new StringBuffer(v_newFileName[i]);
                        sb1.reverse();
                        int point = sb1.indexOf("\\");
                        String revFilename = sb1.substring(0, point);
                        sb1.delete(0, sb1.length());
                        revFilename = sb1.append(revFilename).reverse().toString();

                        String sourcePath = conf.getProperty("dir.home") + v_newFileName[i].substring(0, v_newFileName[i].indexOf(revFilename) - 1).replace("\\\\", "\\");
                        FileMove fc = new FileMove();
                        boolean move_success = fc.move(v_tempPath, sourcePath, revFilename);
                        if (move_success) {
                            pstmt1.setString(1, v_realFileName[0]);
                            pstmt1.setString(2, conf.getProperty("dir.upload.instructor") + revFilename);
                            pstmt1.setString(3, v_target_id);
                            isOk1 = pstmt1.executeUpdate();
                        }
                    } // 큰 이미지 등록
                }
            }
        } catch (Exception ex) {
            FileManager.deleteFile(v_newFileName, FILE_LIMIT); //	일반파일, 첨부파일 있으면 삭제..
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
        }
        return isOk1;
    }

    /**
     * 입력한 강사의 중복여부 조회
     * 
     * @param v_userid 강사 아이디
     * @return v_result 1:중복됨 ,0:중복되지 않음
     */
    public int overlapping(RequestBox box, String v_userid) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int v_result = 0;
        try {
            connMgr = new DBConnectionManager();
            sql = "select name from TZ_TUTOR where userid ='" + v_userid + "'";
            ls = connMgr.executeQuery(sql);
            //중복된 경우 1을 return한다
            if (ls.next()) {
                v_result = 1;
            }
        } catch (Exception ex) {
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
        return v_result;
    }

    /**
     * 운영자권한 테이블에 등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int insertManager(DBConnectionManager connMgr, RequestBox box, String v_userid) throws Exception {
        String sql = "";
        String v_user_id = box.getSession("userid");

        //String v_userid             = box.getString("p_userid");
        String v_fmon = box.getString("p_fmon");
        String v_tmon = box.getString("p_tmon");
        //        String v_compcd             = box.getString("p_compcd");
        //		String v_comp             = box.getString("p_comp");
        int isOk = 0;
        try {
            //insert TZ_MANAGER table
            //            sql =  "insert into TZ_MANAGER(userid,gadmin,comp,isdeleted,fmon,tmon,luserid,ldate) ";
            //            sql+=  "values('"+v_userid+"','P1','"+v_comp+"','N','"+v_fmon+"','"+v_tmon+"','"+v_user_id+"', to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            sql = "insert into TZ_MANAGER(userid,gadmin,isdeleted,fmon,tmon,luserid,ldate) ";
            sql += "values('" + v_userid + "','P1','N','" + v_fmon + "','" + v_tmon + "','" + v_user_id + "', to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            //			System.out.println("운영자권한 테이블에 등록 : " + sql);

            isOk = connMgr.executeUpdate(sql);
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        return isOk;
    }

    /**
     * 운영자권한 테이블에 수정
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int updateManager(DBConnectionManager connMgr, RequestBox box, String v_userid) throws Exception {
        String sql1 = "";
        String sql2 = "";
        String v_user_id = box.getSession("userid");
        //String v_userid             = box.getString("p_userid");
        String v_manager = box.getString("p_manager");
        String v_fmon = box.getString("p_fmon");
        String v_tmon = box.getString("p_tmon");
        //        String v_compcd             = box.getString("p_compcd");
        int isOk = 0;
        try {
            //delete TZ_MANAGER table
            sql1 = "delete from TZ_MANAGER where userid='" + v_userid + "' and gadmin='P1'";
            isOk = connMgr.executeUpdate(sql1);

            if (v_manager.equals("Y")) {
                sql2 = "insert into TZ_MANAGER(userid,gadmin,isdeleted,fmon,tmon,luserid,ldate) ";
                sql2 += "values('" + v_userid + "','P1','N','" + v_fmon + "','" + v_tmon + "','" + v_user_id + "', to_char(sysdate, 'YYYYMMDDHH24MISS'))";
                isOk = connMgr.executeUpdate(sql2);
            }
        } catch (Exception ex) {
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        return isOk;
    }

    /**
     * 사용자 테이블에 등록(사외강사중 권한이 있을 경우에만)
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int insertMember(DBConnectionManager connMgr, RequestBox box, String v_userid) throws Exception {
        String sql = "";
        //        String v_user_id            = box.getSession("userid");

        String v_loginpw = box.getString("p_loginpw");
        String v_resno = box.getString("p_resno"); //사외강사중 권한이 있을 경우에는 p_userid를 주민번호로 사용함
        String v_name = box.getString("p_name");
        String v_email = box.getString("p_email");
        String v_post1 = box.getString("p_post1");
        String v_post2 = box.getString("p_post2");

        String v_add1 = box.getString("p_addr"); //주소
        String v_add2 = box.getString("p_addr2"); //주소

        String v_phone = box.getString("p_phone");
        String v_handphone = box.getString("p_handphone");
        //        String v_comp  				= box.getString("p_comp");

        //        String v_fmon               = box.getString("p_fmon");
        //        String v_tmon               = box.getString("p_tmon");

        String v_officegbn = "Y";

        //        String v_membercomp = box.getSession("comp").substring(0,4) + "000000";

        int isOk = 0;
        try {
            //insert TZ_MEMBER table
            sql = "insert into tz_member(userid,resno,pwd,name,email,post1,post2,addr,addr2,comptel,handphone,office_gbn,indate, lgcnt, lgfail) ";
            sql += "values('" + v_userid + "','" + v_resno + "','" + v_loginpw + "','" + v_name + "','" + v_email + "','" + v_post1 + "',";
            sql += "'" + v_post2 + "','" + v_add1 + "','" + v_add2 + "', '" + v_phone + "','" + v_handphone + "','" + v_officegbn + "',to_char(sysdate, 'YYYYMMDDHH24MISS'), 0, 0) ";

            isOk = connMgr.executeUpdate(sql);
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        return isOk;
    }

    /**
     * 사용자 테이블에 수정(사외강사중 권한이 있을 경우에만)
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int updateMember(DBConnectionManager connMgr, RequestBox box, String v_userid) throws Exception {
        // String sql = "";
        StringBuilder sql = new StringBuilder();
        // String v_user_id = box.getSession("userid");

        String v_loginpw = box.getString("p_loginpw");

        // String v_name = box.getString("p_name");
        String v_email = box.getString("p_email");
        String v_post1 = box.getString("p_post1");
        String v_post2 = box.getString("p_post2");

        String v_add1 = box.getString("p_addr");
        String v_add2 = box.getString("p_addr2");

        String v_phone = box.getString("p_phone");
        String v_handphone = box.getString("p_handphone");
        // String v_comp = box.getString("p_comp");

        // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);
        // v_email = encryptUtil.encrypt(v_email);
        // v_phone = encryptUtil.encrypt(v_phone);
        // v_handphone = encryptUtil.encrypt(v_handphone);
        // v_add2 = encryptUtil.encrypt(v_add2);

        int isOk = 0;

        try {
            //insert TZ_MEMBER table
            sql.append(" UPDATE TZ_MEMBER SET   \n");
            if (!v_loginpw.equals("")) {
                sql.append("		PWD = '").append(v_loginpw).append("',  \n");
            }
            sql.append("        EMAIL = crypto.enc('normal', '").append(v_email).append("'),   \n");
            sql.append("        POST1 = '").append(v_post1).append("',  \n");
            sql.append("        POST2 = '").append(v_post2).append("',  \n");
            sql.append("        ADDR = '").append(v_add1).append("',    \n");
            sql.append("        ADDR2 = '").append(v_add2).append("',   \n");
            sql.append("        COMPTEL = crypto.enc('normal', '").append(v_phone).append("'), \n");
            sql.append("        HANDPHONE = crypto.enc('normal', '").append(v_handphone).append("')   \n");
            sql.append(" WHERE  USERID = '").append(v_userid).append("'   \n");

            isOk = connMgr.executeUpdate(sql.toString());
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        return isOk;
    }

    /**
     * 강사수정
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int updateTutor(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        //        String sql2 = "";
        int isOk1 = 0;
        //        int isOk2   = 0;
        //		int isOk3	= 0;
        //		int isOk4	= 0;
        //		int isOK5   = 0;

        ListSet ls = null;

        String v_user_id = box.getSession("userid");
        String v_userid = box.getString("p_userid");
        String v_manager = box.getString("p_manager");
        String v_isgubun = box.getString("p_isgubun");
        String v_name = box.getString("p_name");
        //        String v_sex        = box.getString("p_sex");
        String v_post1 = box.getString("p_post1");
        String v_post2 = box.getString("p_post2");
        String v_add1 = box.getString("p_addr");
        String v_add2 = box.getString("p_addr2");
        String v_phone = box.getString("p_phone");
        String v_handphone = box.getString("p_handphone");
        String v_email = box.getString("p_email");
        String v_comp = box.getString("p_comp");
        String v_dept = box.getString("p_dept");
        String v_jik = box.getString("p_jik");
        String v_book = box.getString("p_book");
        String v_intro = box.getString("p_intro");
        String v_academic = box.getString("p_academic");
        String v_major = box.getString("p_major");

        //        String v_isAdd      = box.getString("p_isadd");
        String v_career = box.getString("p_career");
        String v_charge = box.getString("p_charge");
        String v_isInfo = box.getString("p_isinfo");
        //        String v_subjclass  = box.getString("p_subjclass");
        String v_subjclass = "";

        //        String v_sdate      = box.getString("p_sdate");		//권한사용시작일
        //        String v_ldate      = box.getString("p_ldate");		//권한사용종료일

        //사외강사중 권한부여일 경우 TZ_MEMBER 테이블에 Insert한다.
        //		String v_loginid	= box.getString("p_userid");
        //		String v_loginpw	= box.getString("p_loginpw");

        //사외 강사일때는 권한이 있으면 p_userid는 resno로 들어간다.
        String v_target_id = v_userid;
        //		String v_resno	= box.getString("p_resno");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            if (v_isgubun.equals("2")) {
                if (v_manager.equals("Y")) {
                    v_target_id = v_userid;
                }
            }

            //update TZ_TUTOR table
            sql = "update TZ_TUTOR ";
            sql += "set 	name=?, ";
            sql += "		post1=?,";
            sql += "		post2=?, ";
            sql += "		add1=?, ";
            sql += "		add2=?, ";
            sql += "		phone=?, ";
            sql += "		handphone=?, ";
            sql += "		email=?, ";
            sql += "		comp=?, ";
            sql += "		dept=?, ";
            sql += "		jik=?, ";
            sql += "		intro=?, ";
            sql += "		academic=?,";
            sql += "		major=?, ";
            sql += "		career=?, ";
            sql += "		book=?, ";
            sql += "		charge=?, ";
            sql += "		isinfo=?, ";
            sql += "		luserid=?,";
            sql += "		subjclass=?, ";
            sql += "		ismanager=?, ";
            sql += "	ldate=to_char(sysdate, 'YYYYMMDD') where userid = '" + v_target_id + "'";
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_name);
            pstmt.setString(2, v_post1);
            pstmt.setString(3, v_post2);
            pstmt.setString(4, v_add1);
            pstmt.setString(5, v_add2);
            pstmt.setString(6, v_phone);
            pstmt.setString(7, v_handphone);
            pstmt.setString(8, v_email);
            pstmt.setString(9, v_comp);
            pstmt.setString(10, v_dept);
            pstmt.setString(11, v_jik);
            pstmt.setString(12, v_intro);
            pstmt.setString(13, v_academic);
            pstmt.setString(14, v_major);
            pstmt.setString(15, v_career);
            pstmt.setString(16, v_book);
            pstmt.setString(17, v_charge);
            pstmt.setString(18, v_isInfo);
            pstmt.setString(19, v_user_id);
            pstmt.setString(20, v_subjclass);
            pstmt.setString(21, v_manager);

            isOk1 = pstmt.executeUpdate(); //TZ_TUTOR 수정

            //            isOk2 =
            updateTutorSubj(connMgr, box, v_target_id); //TZ_SUBJMAN 수정

            //			isOk3 =
            updateMember(connMgr, box, v_target_id);

            //            isOk4 =
            updateManager(connMgr, box, v_target_id); //TZ_MANAGER 수정

            //			isOK5 =
            insertUpFile(connMgr, box, v_target_id); //사진파일 업로드

            if (isOk1 > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
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
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        //        return isOk1*isOk2;
        return isOk1;
    }

    /**
     * 강사삭제
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int deleteTutor(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        //        String sql4         = "";
        int isOk1 = 0;
        //        int isOk2           = 0;
        //        int isOk3           = 0;
        //        int isOk4           = 0;
        String v_userid = box.getString("p_userid");
        try {
            connMgr = new DBConnectionManager();

            //delete TZ_TUTOR table
            sql1 = "delete from TZ_TUTOR where userid='" + v_userid + "'";
            isOk1 = connMgr.executeUpdate(sql1);

            //delete TZ_SUBJMAN table
            sql2 = "delete from TZ_SUBJMAN where userid='" + v_userid + "' and gadmin='P1'";
            //            isOk2   =
            connMgr.executeUpdate(sql2);

            //delete TZ_MANAGER table
            sql3 = "delete from TZ_MANAGER where userid='" + v_userid + "' and gadmin='P1'";
            //            isOk3   =
            connMgr.executeUpdate(sql3);

            //delete TZ_MANAGER table
            //            sql4    =  "delete from tz_member where userid='"+v_userid+"' ";
            //            isOk4   = connMgr.executeUpdate(sql4);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
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
     * 사내 강사 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSearchTutor(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        String sql = "";
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String orderSql = "";
        String countSql = "";

        String v_search = box.getString("p_mode1");
        String v_searchtext = box.getString("p_mode2");
        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            headSql.append("SELECT \n");
            headSql.append("	A.MEMBERGUBUN, \n");
            headSql.append("	A.USERID, \n");
            headSql.append("	A.RESNO, \n");
            headSql.append("	A.NAME, \n");
            headSql.append("	CRYPTO.DEC('normal', A.EMAIL) AS EMAIL, \n");
            headSql.append("	CRYPTO.DEC('normal', A.HOMETEL) AS HOMETEL, \n");
            headSql.append("	CRYPTO.DEC('normal', A.HANDPHONE) AS HANDPHONE, \n");
            headSql.append("	CRYPTO.DEC('normal', A.COMPTEL) AS COMPTEL, \n");
            headSql.append("	A.COMP, \n");
            headSql.append("	A.COMPTEXT, \n");
            headSql.append("	A.POST1, \n");
            headSql.append("	A.POST2, \n");
            headSql.append("	A.ADDR, \n");
            headSql.append("	A.ADDR2, \n");
            headSql.append("	B.CODENM AS MEMBERGUBUNNM, \n");
            headSql.append("	CASE \n");
            headSql.append("		WHEN A.MEMBERGUBUN = 'P' THEN '개인' \n");
            headSql.append("		WHEN A.MEMBERGUBUN = 'C' THEN '기업' \n");
            headSql.append("		WHEN A.MEMBERGUBUN = 'U' THEN '대학교' \n");
            headSql.append("		ELSE '-' \n");
            headSql.append("	END AS MEMBERGUBUNNM2, \n");
            headSql.append("	A.GRCODE, \n");
            headSql.append("	(SELECT GRCODENM FROM TZ_GRCODE C WHERE C.GRCODE = A.GRCODE) AS GRCODENM \n");
            bodySql.append("FROM TZ_MEMBER A, TZ_CODE B \n");
            bodySql.append("WHERE 1=1 AND B.GUBUN='0029' AND CODE=A.MEMBERGUBUN \n");
            //bodySql.append(" and A.GRCODE =  'N000001' \n");

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                if (v_search.equals("id")) { //    ID로 검색할때
                    bodySql.append(" and userid like   " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                } else if (v_search.equals("name")) { //    이름으로 검색할때
                    bodySql.append(" and name like " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                }
            }
            orderSql = "order by comp asc, name asc";

            sql = headSql.toString() + bodySql.toString() + orderSql;
            System.out.println("sql sql =-===>>> " + sql);
            ls = connMgr.executeQuery(sql);

            countSql = "select count(*) " + bodySql;

            int total_row_count = BoardPaging.getTotalRow(connMgr, countSql); //     전체 row 수를 반환한다

            //			int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            ls.setPageSize(10); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(total_row_count));
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
     * 강의과정 조회
     * 
     * @param box receive from the form object and session
     * @return TUtorData
     */
    public ArrayList<TutorData> selectTutorSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<TutorData> list = null;
        String sql = "";
        TutorData data = null;
        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<TutorData>();

            sql = "";
            sql += "SELECT \n";
            sql += "	A.SUBJ, \n";
            sql += "	B.SUBJNM, \n";
            sql += "	NVL(A.YEAR,'0000') AS YEAR, \n";
            sql += "	NVL(A.SUBJSEQ,'0000') AS SUBJSEQ, \n";
            sql += "	SUBSTR(C.EDUSTART,1,8) AS EDUSTART, \n";
            sql += "	SUBSTR(C.EDUEND,1,8) AS EDUEND \n";
            sql += "FROM \n";
            sql += "	TZ_SUBJMAN A, \n";
            sql += "	TZ_SUBJ B, \n";
            sql += "	TZ_SUBJSEQ C \n";
            sql += "WHERE \n";
            sql += "	A.USERID = '" + v_userid + "' \n";
            sql += "	AND A.GADMIN = 'P1' \n";
            sql += "	AND A.SUBJ = B.SUBJ \n";
            sql += "	AND A.SUBJ = C.SUBJ(+) \n";
            sql += "	AND A.SUBJSEQ = C.SUBJSEQ(+) \n";
            sql += "	AND A.YEAR = C.YEAR(+) \n";
            sql += "ORDER BY A.YEAR DESC, A.SUBJSEQ DESC \n";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new TutorData();

                data.setSubj(ls.getString("subj"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setYear(ls.getString("year"));
                data.setSubjseq(ls.getString("subjseq"));
                data.setEdustart(ls.getString("edustart"));
                data.setEduend(ls.getString("eduend"));

                list.add(data);
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
     * 강사에 해당하는 강의과정등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int insertTutorSubj(DBConnectionManager connMgr, RequestBox box, String v_userid) throws Exception {
        String sql = "";
        String v_user_id = box.getSession("userid");
        // String v_userid = box.getString("p_userid");
        // p_subj로 넘어온 다수의 value를 처리하기 위해 vector로 구현
        Vector v_subj = new Vector();
        v_subj = box.getVector("p_subj");
        Enumeration em = v_subj.elements();
        String v_eachSubj = ""; //실제 넘어온 각각의 과정코드
        int isOk = 0;
        try {
            while (em.hasMoreElements()) {
                v_eachSubj = (String) em.nextElement();
                //insert TZ_SUBJMAN table
                String[] tmp = v_eachSubj.split("/");
                if (tmp[1].equals("@"))
                    tmp[1] = "";
                if (tmp[2].equals("@"))
                    tmp[2] = "";

                //insert TZ_SUBJMAN table
                sql = "insert into TZ_SUBJMAN(userid,gadmin,subj,luserid,ldate) ";
                sql += "values('" + v_userid + "','P1','" + tmp[0] + "','" + v_user_id + "', to_char(sysdate, 'YYYYMMDDHH24MISS'))";
                isOk = connMgr.executeUpdate(sql);
            }
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        return isOk;
    }

    /**
     * 강사에 해당하는 강의과정수정
     * 
     * @param box receive from the form object and session
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int updateTutorSubj(DBConnectionManager connMgr, RequestBox box, String v_userid) throws Exception {
        String sql1 = "";
        String sql2 = "";
        String v_user_id = box.getSession("userid");
        //String v_userid             = box.getString("p_userid");
        //p_subj로 넘어온 다수의 value를 처리하기 위해 vector로 구현
        Vector v_subj = new Vector();
        v_subj = box.getVector("p_subj");
        Enumeration em = v_subj.elements();
        String v_eachSubj = ""; //실제 넘어온 각각의 과정코드
        int isOk = 0;
        try {
            //delete TZ_SUBJMAN table
            sql1 = "delete from TZ_SUBJMAN where userid='" + v_userid + "' and gadmin='P1'";
            isOk = connMgr.executeUpdate(sql1);
            while (em.hasMoreElements()) {
                v_eachSubj = (String) em.nextElement();
                //insert TZ_SUBJMAN table
                String[] tmp = v_eachSubj.split("/");
                if (tmp[1].equals("@"))
                    tmp[1] = "";
                if (tmp[2].equals("@"))
                    tmp[2] = "";
                sql2 = "insert into TZ_SUBJMAN(userid,gadmin,subj,luserid,ldate,year,subjseq) ";
                sql2 += "values('" + v_userid + "','P1','" + tmp[0] + "','" + v_user_id + "',to_char(sysdate, 'YYYYMMDDHH24MISS'),'" + tmp[1] + "','" + tmp[2] + "')";
                isOk = connMgr.executeUpdate(sql2);
            }
        } catch (Exception ex) {
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        return isOk;
    }

    /**
     * 교육그룹 select box
     * 
     * @param selectname select box name
     * @param selected selected valiable
     * @param allcheck all check Y(1),all check N(0)
     * @return int
     */
    public static String getGrcodeSelect(RequestBox box, String selectname, String selected, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";

        result = "  <SELECT name=" + selectname + " > \n";

        if (allcheck == 1) {
            result += " <option value=''>=== 전체 ===</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = "select grcode, grcodenm from tz_grcode  ";
            sql += " order by grcodenm";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                result += " <option value=" + ls.getString("grcode");
                if (selected.equals(ls.getString("grcode"))) {
                    result += " selected ";
                }

                result += ">" + ls.getString("grcodenm") + "</option> \n";
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

        result += "  </SELECT> \n";
        return result;
    }

    /**
     * 과정조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<TutorData> selectSearchSubj(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<TutorData> list = null;
        String sql = "";
        TutorData data = null;
        String v_open_select = box.getString("p_open_select");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<TutorData>();

            //select subj,subjnm
            sql = "select subj,subjnm from TZ_SUBJ ";
            sql += "where subjnm like '%" + v_open_select + "%'";
            ls = connMgr.executeQuery(sql);

            ls.setPageSize(10); //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno); //현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); //전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount(); //전체 row 수를 반환한다
            while (ls.next()) {
                data = new TutorData();
                data.setSubj(ls.getString("subj"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setDispnum(total_row_count - ls.getRowNum() + 1);
                data.setTotalpagecount(total_page_count);
                list.add(data);
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
     * 강사 이력 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<TutorData> selectTutorHistoryList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<TutorData> list = null;
        String sql = "";
        TutorData data = null;
        //        String v_search         = box.getString("p_search");        //전문분야 검색
        String v_select = box.getString("p_select"); //검색항목(과정명1,강사명2)
        String v_selectvalue = box.getString("p_selectvalue"); //검색어
        String v_gyear = box.getString("p_gyear"); //년도
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<TutorData>();

            //select userid,name,comp,dept,handphone,email,isGubun,subj,subjnm,lecture,sdesc,lectlevel
            sql = "select A.userid,A.name,A.comp,A.dept,A.handphone,A.email,A.isGubun,B.subj,B.subjnm, ";
            sql += "C.lecture,C.sdesc,C.lectlevel ";
            sql += "from TZ_TUTOR A,TZ_SUBJ B,TZ_OFFSUBJLECTURE C ";
            sql += "where A.userid = C.tutorid and B.subj = C.subj  ";
            /*
             * if ( !v_search.equals("")) { //전문분야가 있는 경우 sql +=
             * "and A.professional like '%"+v_search+"%' "; }
             */
            if (v_select.equals("1")) { //검색항목이 과정명인경우
                sql += "and B.subjnm like '%" + v_selectvalue + "%' ";
            } else if (v_select.equals("2")) { //검색항목이 강사명인경우
                sql += "and A.name like '%" + v_selectvalue + "%' ";
            }
            if (!v_gyear.equals("")) { //년도가 있는 경우
                sql += "and C.year =" + SQLString.Format(v_gyear);
            }
            sql += "order by A.name,B.subj,C.subjseq,C.lecture ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new TutorData();
                data.setUserid(ls.getString("userid"));
                data.setName(ls.getString("name"));
                data.setComp(ls.getString("comp"));
                data.setDept(ls.getString("dept"));
                data.setHandphone(ls.getString("handphone"));
                data.setEmail(ls.getString("email"));
                data.setIsgubun(ls.getString("isgubun"));
                data.setSubj(ls.getString("subj"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setLecture(ls.getInt("lecture"));
                data.setSdesc(ls.getString("sdesc"));
                data.setLectlevel(ls.getString("lectlevel"));
                list.add(data);
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
     * 강사 이력평가 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<TutorData> selectTutorLectureList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<TutorData> list = null;
        String sql = "";
        TutorData data = null;
        String v_userid = box.getString("p_userid"); //강사아이디
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<TutorData>();

            //select subj,subjnm,year,subjseq,lecture,sdesc,lectdate,lectsttime,lecttime,lectscore,lectlevel
            sql = "select B.subj,B.subjnm,C.year,C.subjseq,C.lecture,C.sdesc,C.lectdate,C.lectsttime,C.lecttime, ";
            sql += "C.lectscore,C.lectlevel ";
            sql += "from TZ_TUTOR A,TZ_SUBJ B,TZ_OFFSUBJLECTURE C ";
            sql += "where A.userid = C.tutorid and B.subj = C.subj and A.userid=" + SQLString.Format(v_userid);
            sql += "order by B.subj,C.subjseq,C.lecture";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new TutorData();
                data.setSubj(ls.getString("subj"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setYear(ls.getString("year"));
                data.setSubjseq(ls.getString("subjseq"));
                data.setLecture(ls.getInt("lecture"));
                data.setSdesc(ls.getString("sdesc"));
                data.setLectdate(ls.getString("lectdate"));
                data.setLectsttime(ls.getString("lectsttime"));
                data.setLecttime(ls.getString("lecttime"));
                data.setLectscore(ls.getInt("lectscore"));
                data.setLectlevel(ls.getString("lectlevel"));
                list.add(data);
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
     * 강사 평가 저장
     * 
     * @param box receive from the form object and session
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int updateTutorScore(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        int isOk = 0;
        //        String v_user_id    = box.getSession("userid");
        //        String v_userid     = box.getString("p_userid");        //강사아이디
        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        String v_lecture = "";
        //String v_lectscore  = "";
        String v_lectlevel = "";
        int v_lectscore = 0;
        Vector v_vec1 = new Vector();
        Vector v_vec2 = new Vector();
        Vector v_vec3 = new Vector();
        Vector v_vec4 = new Vector();
        Vector v_vec5 = new Vector();
        v_vec1 = box.getVector("p_subj");
        v_vec2 = box.getVector("p_year");
        v_vec3 = box.getVector("p_subjseq");
        v_vec4 = box.getVector("p_lecture");
        v_vec5 = box.getVector("p_lectscore");
        Enumeration em1 = v_vec1.elements();
        Enumeration em2 = v_vec2.elements();
        Enumeration em3 = v_vec3.elements();
        Enumeration em4 = v_vec4.elements();
        Enumeration em5 = v_vec5.elements();

        try {
            connMgr = new DBConnectionManager();

            //update TZ_OFFSUBJLECTURE table
            sql1 = "update TZ_OFFSUBJLECTURE set lectscore=?,lectlevel=? ";
            sql1 += "where subj=? and year=? and subjseq=? and lecture=? ";
            pstmt1 = connMgr.prepareStatement(sql1);

            while (em1.hasMoreElements()) {
                v_subj = (String) em1.nextElement();
                v_year = (String) em2.nextElement();
                v_subjseq = (String) em3.nextElement();
                v_lecture = (String) em4.nextElement();
                v_lectscore = Integer.parseInt((String) em5.nextElement());
                if (v_lectscore >= 90) {
                    v_lectlevel = "A";
                } else if (v_lectscore >= 80) {
                    v_lectlevel = "B";
                } else if (v_lectscore >= 70) {
                    v_lectlevel = "C";
                } else if (v_lectscore >= 60) {
                    v_lectlevel = "D";
                } else if (v_lectscore >= 50) {
                    v_lectlevel = "E";
                } else {
                    v_lectlevel = "F";
                }

                pstmt1.setInt(1, v_lectscore);
                pstmt1.setString(2, v_lectlevel);
                pstmt1.setString(3, v_subj);
                pstmt1.setString(4, v_year);
                pstmt1.setString(5, v_subjseq);
                pstmt1.setInt(6, Integer.parseInt(v_lecture));
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
     * 용역업체 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectOutCompList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_outcompnmsrch = box.getString("p_outcompnmsrch"); //용역업체명
        String v_subjclass = box.getString("p_subjclass"); //강의분야

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = "select 	a.busino, a.compnm, a.represntnm, ";
            sql += "    	  (select codenm from tz_code where gubun = '0039' and code = a.subjclass) subjclassnm,";
            sql += "			tel,";
            sql += "			(select name from tz_member where userid=a.luserid) lusernm, get_compnm(comp, 2, 4) lcompnm ";
            sql += "from tz_tcomp a ";
            sql += "where 1=1 ";

            //회사명으로 검색
            if (!v_outcompnmsrch.equals("")) {
                sql += "       and a.compnm like '%" + v_outcompnmsrch + "%' ";
            }

            //분류
            if (!v_subjclass.equals("")) {
                sql += " and a.subjclass = " + SQLString.Format(v_subjclass);
            }

            if (v_orderColumn.equals("")) {
                sql += "order by compnm";
            } else {
                sql += " order by " + v_orderColumn + v_orderType;
            }

            ls = connMgr.executeQuery(sql);

            ls.setPageSize(row); //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno); //현재페이지번호를 세팅한다.
            int totalpagecount = ls.getTotalPage(); //전체 페이지 수를 반환한다
            int totalrowcount = ls.getTotalCount(); //전체 row수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("busino", ls.getString("busino"));
                dbox.put("compnm", ls.getString("compnm"));
                dbox.put("represntnm", ls.getString("represntnm"));
                dbox.put("d_subjclassnm", ls.getString("subjclassnm"));
                dbox.put("d_dispnum", new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpagecount", new Integer(totalpagecount));
                dbox.put("d_rowcount", new Integer(row));

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
     * 교육기관 등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int insertOutComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        int v_result = 0;

        String v_user_id = box.getSession("userid"); //등록자 사번
        String v_user_comp = box.getSession("comp"); //등록자 소속

        String v_busino = box.getString("p_busino"); //사업자번호(-없이)
        String v_compnm = box.getString("p_compnm"); //상호명
        String v_represntnm = box.getString("p_represntnm"); //대표자
        String v_subjclass = box.getString("p_subjclass"); //강의분야
        String v_busistatus = box.getString("p_busistatus"); //업태
        String v_busiitem = box.getString("p_busiitem"); //업종
        String v_tel = box.getString("p_tel"); //전화번호
        String v_fax = box.getString("p_fax"); //fax
        String v_post1 = box.getString("p_post1"); //우편번호1
        String v_post2 = box.getString("p_post2"); //우편번호2
        String v_addr = box.getString("p_addr2"); //상세주소
        String v_post = "";

        try {
            //교육기관 중복여부 조회
            v_result = overlappingOutComp(box, v_busino);
            if (v_result == 1) {
                return 0;
            }

            connMgr = new DBConnectionManager();

            sql = "insert into tz_tcomp (busino, compnm, represntnm, subjclass,  ";
            sql += "		busistatus, busiitem, tel,fax, ";
            sql += "zipcode, addr, comp, luserid, ldate) ";
            sql += "		values(?,?,?,?,?,?,?,?,?,?,?,?,to_char(sysdate, 'YYYYMMDD')) ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_busino);
            pstmt.setString(2, v_compnm);
            pstmt.setString(3, v_represntnm);
            pstmt.setString(4, v_subjclass);
            pstmt.setString(5, v_busistatus);
            pstmt.setString(6, v_busiitem);
            pstmt.setString(7, v_tel);
            pstmt.setString(8, v_fax);
            if (!v_post1.equals("") && !v_post2.equals("")) {
                v_post = v_post1 + "-" + v_post2;
            }
            pstmt.setString(9, v_post);
            pstmt.setString(10, v_addr);
            pstmt.setString(11, v_user_comp);
            pstmt.setString(12, v_user_id);

            isOk = pstmt.executeUpdate(); //TZ_TCOMP 등록
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
     * 입력한 교육기관 중복여부 조회
     * 
     * @param v_userid 교육기관 아이디
     * @return v_result 1:중복됨 ,0:중복되지 않음
     */
    public int overlappingOutComp(RequestBox box, String v_busino) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int v_result = 0;

        try {
            connMgr = new DBConnectionManager();
            sql = "select compnm from TZ_TCOMP where busino ='" + v_busino + "'";
            ls = connMgr.executeQuery(sql);
            //중복된 경우 1을 return한다
            if (ls.next()) {
                v_result = 1;
            }
        } catch (Exception ex) {
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
        return v_result;
    }

    /**
     * 교육기관 조회
     * 
     * @param box receive from the form object and session
     * @return TutorData
     */
    public DataBox selectOutComp(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_busino = box.getString("p_busino");

        try {
            connMgr = new DBConnectionManager();

            sql = " select 	busino, compnm, represntnm, subjclass, ";
            sql += "    	  (select codenm from tz_code where gubun = '0039' and code = tz_tcomp.subjclass) subjclassnm, ";
            sql += "			busistatus, busiitem, tel, fax,  zipcode,";
            sql += "	    	(select  sido || ' ' || gugun || ' ' || dong  from tz_zipcode ";
            sql += " 		 where 	zipcode = tz_tcomp.zipcode and rownum=1)  as addr1,";
            sql += "	    	addr addr2, comp, luserid, ldate ,";
            sql += "	   		(select name from tz_member where userid=tz_tcomp.luserid) lusernm,";
            sql += "	   		get_compnm(comp,2,2) lusercompnm ";
            sql += "from 	tz_tcomp  ";
            sql += "where	busino = '" + v_busino + "'";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("busino", ls.getString("busino"));
                dbox.put("compnm", ls.getString("compnm"));
                dbox.put("represntnm", ls.getString("represntnm"));
                dbox.put("subjclass", ls.getString("subjclass"));
                dbox.put("d_subjclassnm", ls.getString("subjclassnm"));
                dbox.put("busistatus", ls.getString("busistatus"));
                dbox.put("busiitem", ls.getString("busiitem"));
                dbox.put("tel", ls.getString("tel"));
                dbox.put("fax", ls.getString("fax"));
                dbox.put("zipcode", ls.getString("zipcode"));
                dbox.put("addr1", ls.getString("addr1"));
                dbox.put("addr2", ls.getString("addr2"));
                dbox.put("comp", ls.getString("comp"));
                dbox.put("luserid", ls.getString("luserid"));
                dbox.put("ldate", ls.getString("ldate"));
                dbox.put("lusernm", ls.getString("lusernm"));
                dbox.put("lusercompnm", ls.getString("lusercompnm"));

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
     * 교육기관 수정
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int updateOutComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_busino = box.getString("p_busino"); //사업자번호(-없이)
        String v_compnm = box.getString("p_compnm"); //상호명
        String v_represntnm = box.getString("p_represntnm"); //대표자
        String v_subjclass = box.getString("p_subjclass"); //강의분야
        String v_busistatus = box.getString("p_busistatus"); //업태
        String v_busiitem = box.getString("p_busiitem"); //업종
        String v_tel = box.getString("p_tel"); //전화번호
        String v_fax = box.getString("p_fax"); //fax
        String v_post1 = box.getString("p_post1"); //우편번호1
        String v_post2 = box.getString("p_post2"); //우편번호2
        String v_addr = box.getString("p_addr2"); //상세주소
        String v_post = "";

        try {
            connMgr = new DBConnectionManager();

            //update TZ_TCOMP table
            sql = "update TZ_TCOMP ";
            sql += "set 	compnm = ?,";
            sql += "       represntnm = ?,";
            sql += "       subjclass = ?,";
            sql += "       busistatus = ?,";
            sql += "       busiitem = ?,";
            sql += "       tel = ?,";
            sql += "       fax = ?,";
            sql += "       zipcode = ?,";
            sql += "       addr = ?";
            sql += "where	busino = ? ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_compnm);
            pstmt.setString(2, v_represntnm);
            pstmt.setString(3, v_subjclass);
            pstmt.setString(4, v_busistatus);
            pstmt.setString(5, v_busiitem);
            pstmt.setString(6, v_tel);
            pstmt.setString(7, v_fax);

            if (!v_post1.equals("") && !v_post2.equals("")) {
                v_post = v_post1 + "-" + v_post2;
            }
            pstmt.setString(8, v_post);

            pstmt.setString(9, v_addr);
            pstmt.setString(10, v_busino);

            isOk = pstmt.executeUpdate(); //TZ_TCOMP 수정

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
     * 교육기관 삭제
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int deleteOutComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        //		ArrayList list  = null;
        //        DataBox dbox 		= null;
        String sql1 = "";
        String sql2 = "";

        //        int isOk1           = 0;
        int isOk2 = 0;

        String v_busino = box.getString("p_busino");
        try {
            connMgr = new DBConnectionManager();
            //            list = new ArrayList();

            //계약정보가 있을 경우 삭제 불가
            sql1 = "select count(busino) cnt from tz_tcomp where busino='" + v_busino + "'";

            ls = connMgr.executeQuery(sql1);

            while (ls.next()) {
            }

            //delete TZ_TCOMP table
            sql2 = "delete from TZ_TCOMP where busino='" + v_busino + "'";
            isOk2 = connMgr.executeUpdate(sql2);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk2;
    }

    /**
     * 강사분반현황 2005.08.20 jungkyoungjin
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectTutorClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        DataBox dbox1 = null;

        String v_searchday = (!box.getString("p_searchday").equals("")) ? (box.getString("p_searchday")).replaceAll("-", "") + "" : ""; //학습시작일
        String ss_action = box.getString("s_action");

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        try {

            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList<DataBox>();

                //select a.subj, a.year, a.subjseq, a.class, a.tuserid, get_name(a.tuserid), count(b.userid)
                //from TZ_CLASSTUTOR a, TZ_STUDENT b, TZ_SUBJSEQ c
                //where a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq and a.class=b.class
                //and a.subj=c.subj and //a.year=c.year and a.subjseq=c.subjseq
                //and '20050820' between c.edustart and c.eduend
                //group by a.subj, a.year, a.subjseq, a.class, a.tuserid

                sql1 = "select a.tuserid, get_name(a.tuserid) tusernm, count(b.userid) stucnt ";
                sql1 += " from TZ_CLASSTUTOR a, TZ_STUDENT b, TZ_SUBJSEQ c";
                sql1 += " where b.subj=a.subj and b.year=a.year and b.subjseq=a.subjseq and b.class=a.class";
                sql1 += " and a.subj=c.subj and a.year=c.year and a.subjseq=c.subjseq";
                sql1 += " and " + SQLString.Format(v_searchday) + " between c.edustart and c.eduend ";
                sql1 += " and isclosed = 'N'";
                sql1 += " group by a.tuserid";

                if (v_orderColumn.equals("")) {
                    sql1 += " order by a.tuserid desc ";
                } else {
                    sql1 += " order by " + v_orderColumn + v_orderType;
                }

                ls1 = connMgr.executeQuery(sql1);

                while (ls1.next()) {
                    dbox1 = ls1.getDataBox();
                    list1.add(dbox1);

                } // END while
            } // END if

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
     * 강사입과현황 2005.08.20 jungkyoungjin
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectTutorSubj(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        DataBox dbox1 = null;

        String ss_edustart = (!box.getString("p_edustart").equals("")) ? (box.getString("p_edustart")).replaceAll("-", "") + "" : ""; //학습시작일
        String ss_eduend = (!box.getString("p_eduend").equals("")) ? (box.getString("p_eduend")).replaceAll("-", "") + "" : ""; //학습종료일

        String ss_action = box.getString("s_action");

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        try {

            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList<DataBox>();

                sql1 += " select  														\n";
                sql1 += " subj, subjnm, year, subjseq, class, tuserid, tusernm, stucnt  \n";
                sql1 += " from 															\n";
                sql1 += "(                                                              \n";
                sql1 += "select                             ";
                sql1 += " a.subj,                           ";
                sql1 += " c.subjnm,                         ";
                sql1 += " a.year,                           ";
                sql1 += " a.subjseq,                        ";
                sql1 += " a.class,                          ";
                sql1 += " a.tuserid,                        ";
                sql1 += " get_name(a.tuserid) tusernm,      ";
                sql1 += " count(b.userid) stucnt            ";
                sql1 += " from TZ_CLASSTUTOR a, TZ_STUDENT b, TZ_SUBJSEQ c";
                sql1 += " where b.subj=a.subj and b.year=a.year and b.subjseq=a.subjseq and b.class=a.class";
                sql1 += " and a.subj=c.subj and a.year=c.year and a.subjseq=c.subjseq";
                //sql1 += " and "+SQLString.Format(ss_edustart)+" = SUBSTR(c.edustart,0,8)";
                //sql1 += " and "+SQLString.Format(ss_eduend)+" = SUBSTR(c.eduend,0,8)";
                sql1 += " and SUBSTR(c.edustart,0,8) between " + SQLString.Format(ss_edustart) + " and " + SQLString.Format(ss_eduend);
                sql1 += " and SUBSTR(c.eduend,0,8) between " + SQLString.Format(ss_edustart) + " and " + SQLString.Format(ss_eduend);

                //				sql1 += " and isclosed = 'N'";
                sql1 += " group by a.subj, c.subjnm, a.year, a.subjseq, a.class, a.tuserid";
                sql1 += " ) tutorstu ";
                if (v_orderColumn.equals("")) {
                    sql1 += " order by tusernm asc ";
                } else {
                    sql1 += " order by tusernm, " + v_orderColumn + v_orderType;
                }

                ls1 = connMgr.executeQuery(sql1);

                while (ls1.next()) {
                    dbox1 = ls1.getDataBox();
                    list1.add(dbox1);

                } // END while
            } // END if

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
     * 강사분반학생목록 2005.08.20
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectTutorClassStudent(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        DataBox dbox1 = null;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_class = box.getString("p_class");

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        try {

            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = " select a.tuserid, get_name(a.tuserid) tusernm, b.userid, get_name(b.userid) usernm, ";
            sql1 += "  get_compnm(c.comp,2,2) compnm, get_deptnm(c.deptnam, c.userid) deptnm, get_jikwinm(c.jikwi,c.comp) jikwinm ";
            sql1 += " from TZ_CLASSTUTOR a, TZ_STUDENT b, TZ_MEMBER c";
            sql1 += " where b.subj=a.subj and b.year=a.year and b.subjseq=a.subjseq and b.class=a.class";
            sql1 += "  and b.userid = c.userid";
            sql1 += "  and a.subj		= " + SQLString.Format(v_subj);
            sql1 += "  and a.year		= " + SQLString.Format(v_year);
            sql1 += "  and a.subjseq	= " + SQLString.Format(v_subjseq);
            sql1 += "  and a.class	= " + SQLString.Format(v_class);

            if (v_orderColumn.equals("")) {
                sql1 += " order by tusernm desc ";
            } else {
                sql1 += " order by " + v_orderColumn + v_orderType;
            }

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox1 = ls1.getDataBox();
                list1.add(dbox1);

            } // END while

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

    /*
     * 강사료 가져오기 2005.07.01
     * 
     * @param box receive from the form object and session
     * 
     * @return ArrayList
     */
    public ArrayList<DataBox> getPay(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        String sql = "";

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = "select tutorcode, get_codenm('0027', tutorcode) tutorcodenm, price, addprice from TZ_TUTORPAY";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("tutorcode", ls.getString("tutorcode"));
                dbox.put("tutorcodenm", ls.getString("tutorcodenm"));
                dbox.put("price", ls.getString("price"));
                dbox.put("addprice", ls.getString("addprice"));

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
     * 강사료 수정
     * 
     * @param box receive from the form object and session
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int updatePay(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        String sql1 = "";
        Vector v_tutorcode = box.getVector("p_tutorcode");
        Vector v_price = box.getVector("p_price");
        Vector v_addprice = box.getVector("p_addprice");

        String v_tutorcode1 = "", v_price1 = "", v_addprice1 = "";

        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();

            for (int i = 0; i < v_tutorcode.size(); i++) {
                v_tutorcode1 = (String) v_tutorcode.elementAt(i);
                v_price1 = (String) v_price.elementAt(i);
                v_addprice1 = (String) v_addprice.elementAt(i);

                sql1 = "update TZ_TUTORPAY set price = " + SQLString.Format(v_price1) + ", addprice = " + SQLString.Format(v_addprice1);
                sql1 += " where tutorcode =" + SQLString.Format(v_tutorcode1);
                isOk = connMgr.executeUpdate(sql1);
            }

        } catch (Exception ex) {
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
        return isOk;
    }

    /**
     * 강사평가 명단 리스트 2005.01.28 kimsujin
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectTutorGradeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";//, sql2="";
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String orderSql = "";
        String countSql = "";

        DataBox dbox1 = null;
        //        int v_pageno = box.getInt("p_pageno");
        //        String v_subj = "", v_year="", v_subjseq="";
        //        int v_stucnt = 0, v_sulsum=0;
        //        double v_okrate = 0;

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //과정 차수
        String ss_action = box.getString("s_action");
        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        if (v_orderColumn.equals(""))
            v_orderColumn = "userid";
        v_orderColumn = "b." + v_orderColumn;

        try {
            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList<DataBox>();

                headSql.append(" select c.subj,c.year,c.subjnm,c.subjseq,c.isclosed,a.class,a.tuserid,b.name,b.resno,b.email \n");
                headSql.append("        , (select count(tuserid) from TZ_CLASSTUTOR where subj=a.subj and year=a.year and subjseq=a.subjseq) ctutorcnt \n");
                headSql.append("        , (select count(userid) from TZ_STUDENT where subj=a.subj and year=a.year and subjseq=a.subjseq and class=a.class) stucnt \n");
                headSql.append("        , (select count(userid) from TZ_STUDENT where subj=a.subj and year=a.year and subjseq=a.subjseq and class=a.class and isgraduated='Y') grayncnt \n");
                //                sql1+= "        , (select count(ordseq) from TZ_PROJORD where subj=a.subj and year=a.year and subjseq=a.subjseq ) reportcnt";
                //                sql1+= "        , (select count(userid) from TZ_DATABOARD where subj=a.subj and year=a.year and subjseq=a.subjseq and userid=a.tuserid) datacnt";
                //                sql1+= "        , (select count(luserid) from TZ_TORONTP where subj=a.subj and year=a.year and subjseq=a.subjseq and luserid=a.tuserid) torontpcnt";
                //                sql1+= "        , (select count(luserid) from TZ_TORON where subj=a.subj and year=a.year and subjseq=a.subjseq and luserid=a.tuserid) toroncnt";
                //                sql1+= "        , (select count(inuserid) from TZ_QNA where subj=a.subj and year=a.year and subjseq=a.subjseq and  seq > 0 and inuserid=a.tuserid) qnacnt";
                //                sql1+= "        , (select count(userid) from TZ_GONG where subj=a.subj and year=a.year and subjseq=a.subjseq and luserid=a.tuserid) gongcnt";
                headSql.append("        ,d.malevel,d.sapoint,d.jigubfee \n");

                bodySql.append("  from TZ_CLASSTUTOR a,VZ_SCSUBJSEQ c, TZ_TUTOR b, TZ_TUTORGRADE d  \n");
                bodySql.append("  where a.tuserid=b.userid and a.subj=c.subj and a.year=c.year and a.subjseq=c.subjseq   \n");
                bodySql.append("    and d.subj = a.subj(+) and d.year = a.year(+) and d.subjseq = a.subjseq(+) and d.userid = a.tuserid(+) \n");

                if (!ss_grcode.equals("ALL") && !ss_grcode.equals("----"))
                    bodySql.append(" and c.grcode       = " + SQLString.Format(ss_grcode) + " \n");
                if (!ss_gyear.equals("ALL"))
                    bodySql.append(" and c.gyear        = " + SQLString.Format(ss_gyear) + " \n");
                if (!ss_grseq.equals("ALL"))
                    bodySql.append(" and c.grseq        = " + SQLString.Format(ss_grseq) + " \n");
                if (!ss_uclass.equals("ALL"))
                    bodySql.append(" and c.scupperclass = " + SQLString.Format(ss_uclass) + " \n");
                if (!ss_mclass.equals("ALL"))
                    bodySql.append(" and c.scmiddleclass = " + SQLString.Format(ss_mclass) + " \n");
                if (!ss_lclass.equals("ALL"))
                    bodySql.append(" and c.sclowerclass = " + SQLString.Format(ss_lclass) + " \n");
                if (!ss_subjcourse.equals("ALL"))
                    bodySql.append(" and c.scsubj       = " + SQLString.Format(ss_subjcourse) + " \n");
                if (!ss_subjseq.equals("ALL"))
                    bodySql.append(" and c.scsubjseq    = " + SQLString.Format(ss_subjseq) + " \n");

                orderSql = " order by c.subj,c.year,c.subjseq," + v_orderColumn;

                sql1 = headSql.toString() + bodySql.toString() + orderSql;

                ls1 = connMgr.executeQuery(sql1);

                countSql = "select count(*) " + bodySql;

                int total_row_count = BoardPaging.getTotalRow(connMgr, countSql); //     전체 row 수를 반환한다

                //				int total_page_count = ls1.getTotalPage();       //     전체 페이지 수를 반환한다
                ls1.setPageSize(v_pagesize); //  페이지당 row 갯수를 세팅한다
                ls1.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.

                while (ls1.next()) {
                    dbox1 = ls1.getDataBox();
                    dbox1.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                    dbox1.put("d_totalpage", new Integer(ls1.getTotalPage()));
                    dbox1.put("d_rowcount", new Integer(row));
                    dbox1.put("d_totalrowcount", new Integer(total_row_count));
                    list1.add(dbox1);
                } // END while
            } // END if

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
        return list1;
    }

    /**
     * 강사활동 상세보기: 활동카운트 2005.02.01 kimsujin
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     **/
    public DataBox selectTutorActcnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";//, sql2="";
        DataBox dbox1 = null;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();

            sql1 = "select tuserid";
            sql1 += "        , (select count(luserid) from TZ_TORONTP where subj=a.subj and year=a.year and subjseq=a.subjseq and luserid=a.tuserid) torontpcnt";
            sql1 += "        , (select count(luserid) from TZ_TORON where subj=a.subj and year=a.year and subjseq=a.subjseq and luserid=a.tuserid) toroncnt";
            sql1 += "        , (select count(inuserid) from TZ_QNA where subj=a.subj and year=a.year and subjseq=a.subjseq and  seq > 0 and inuserid=a.tuserid) qnacnt";
            sql1 += "        , (select count(luserid) from TZ_GONG where subj=a.subj and year=a.year and subjseq=a.subjseq and luserid=a.tuserid) gongcnt";
            sql1 += "        , (select malevel from TZ_TUTORGRADE where subj=a.subj and year=a.year and subjseq=a.subjseq and userid=a.tuserid) malevel";
            sql1 += "        , (select count(tuserid) from TZ_TUTORLOG where tuserid=a.tuserid  and SUBSTR(login,1,8) between SUBSTR(b.edustart,1,8) and SUBSTR(b.eduend,1,8) ) logincnt ";
            sql1 += "        , (select count(userid) from TZ_DATABOARD where subj=a.subj and year=a.year and subjseq=a.subjseq and userid=a.tuserid) datacnt";
            sql1 += " from tz_classtutor a , TZ_SUBJSEQ b ";
            sql1 += " where a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq ";
            sql1 += "  and a.subj=" + SQLString.Format(v_subj) + " and  a.year=" + SQLString.Format(v_year) + " ";
            sql1 += "  and a.subjseq= " + SQLString.Format(v_subjseq) + " and tuserid=" + SQLString.Format(v_userid) + " and rownum=1";

            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                dbox1 = ls1.getDataBox();
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
        return dbox1;
    }

    /**
     * 강사활동 상세보기: 활동리스트 2005.02.01 kimsujin
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     **/
    public ArrayList<DataBox> selectTutorActList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";//, sql2="";
        DataBox dbox1 = null;
        ArrayList<DataBox> list1 = null;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = "select 'gong' gubun, '공지' gubunnm, userid, title, addate, 0 cnt, seq ,'' lesson, '0' types ,'' tpcode";
            sql1 += " from TZ_GONG ";
            sql1 += " where subj=" + SQLString.Format(v_subj) + " and  year=" + SQLString.Format(v_year) + " ";
            sql1 += "  and subjseq= " + SQLString.Format(v_subjseq) + " and luserid=" + SQLString.Format(v_userid) + " ";

            sql1 += " union ";

            sql1 += "select 'data' gubun, '자료실' gubunnm, userid, title, indate addate, cnt,  seq ,'' lesson, '0' types ,'' tpcode";
            sql1 += " from TZ_DATABOARD ";
            sql1 += " where subj=" + SQLString.Format(v_subj) + " and  year=" + SQLString.Format(v_year) + " ";
            sql1 += "  and subjseq= " + SQLString.Format(v_subjseq) + " and userid=" + SQLString.Format(v_userid) + " ";

            sql1 += " union ";

            sql1 += "select 'qna' gubun, 'Q'||'&'||'A' gubunnm, inuserid userid, title, indate addate , 0 cnt,  seq , lesson, kind types , '' tpcode";
            sql1 += " from TZ_QNA ";
            sql1 += " where  seq > 0  and subj=" + SQLString.Format(v_subj) + " and  year=" + SQLString.Format(v_year) + " ";
            sql1 += "  and subjseq= " + SQLString.Format(v_subjseq) + " and inuserid=" + SQLString.Format(v_userid) + " ";

            sql1 += " union ";

            sql1 += "select 'torontp' gubun, '토론실' gubunnm, luserid, title, addate , cnt,  0 seq , '' lesson, '0' types , tpcode  ";
            sql1 += " from TZ_TORONTP ";
            sql1 += " where subj=" + SQLString.Format(v_subj) + " and  year=" + SQLString.Format(v_year) + " ";
            sql1 += "  and subjseq= " + SQLString.Format(v_subjseq) + " and luserid=" + SQLString.Format(v_userid) + " ";

            sql1 += " union ";

            sql1 += "select 'toron' gubun, '토론실' gubunnm, luserid, title, addate , cnt , seq ,'' lesson, '0' types ,tpcode ";
            sql1 += " from TZ_TORON ";
            sql1 += " where subj=" + SQLString.Format(v_subj) + " and  year=" + SQLString.Format(v_year) + " ";
            sql1 += "  and subjseq= " + SQLString.Format(v_subjseq) + " and luserid=" + SQLString.Format(v_userid) + " ";

            ls1 = connMgr.executeQuery(sql1);
            while (ls1.next()) {
                dbox1 = ls1.getDataBox();
                list1.add(dbox1);
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
     * 강사활동 상세보기: 로그인 리스트 2005.02.01 kimsujin
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     **/
    public ArrayList<DataBox> selectTutorLoginList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";//, sql2="";
        DataBox dbox1 = null;
        ArrayList<DataBox> list1 = null;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = "select serno,login,logout,loginip,dtime ";
            sql1 += " from TZ_TUTORLOG a, TZ_SUBJSEQ b";
            sql1 += " where b.subj=" + SQLString.Format(v_subj) + " and  b.year=" + SQLString.Format(v_year) + " ";
            sql1 += "  and b.subjseq= " + SQLString.Format(v_subjseq) + " and a.tuserid=" + SQLString.Format(v_userid) + " ";
            sql1 += "  and SUBSTR(a.login,1,8) between b.edustart and b.eduend order by serno desc";

            ls1 = connMgr.executeQuery(sql1);
            while (ls1.next()) {
                dbox1 = ls1.getDataBox();
                list1.add(dbox1);
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
     * 강사운영자평가 저장 2005.01.28 kimsujin
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public int calcTutorGrade(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        String sql1 = "", sql2 = "", sql3 = "";
        int isOk = 0;
        int i = 0;
        int //v_stucnt = 0,
        v_grayncnt = 0, v_sulsum = 0;
        int v_price = 0, v_addprice = 0, v_jigubfee = 0;
        String v_class = "";

        String s_userid = box.getSession("userid");

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_userid = box.getString("p_userid");
        String v_malevel = box.getString("p_malevel");

        try {
            connMgr = new DBConnectionManager();

            sql1 = " select subj, year, subjseq, class, b.isgubun, d.price, d.addprice ";
            sql1 += "        , (select count(userid) from TZ_TUTORGRADE where subj=a.subj and year=a.year and subjseq=a.subjseq and userid=a.tuserid) gradecnt";
            sql1 += "        , (select count(userid) from TZ_STUDENT where subj=a.subj and year=a.year and subjseq=a.subjseq and class=a.class) stucnt";
            sql1 += "        , (select count(userid) from TZ_STUDENT where subj=a.subj and year=a.year and subjseq=a.subjseq and class=a.class and isgraduated='Y') grayncnt";
            sql1 += "  from TZ_CLASSTUTOR a , TZ_TUTOR b, TZ_TUTORPAY d ";
            sql1 += "  where a.tuserid = b.userid and b.tutorgubun = d.tutorcode and subj=" + SQLString.Format(v_subj) + " and  year=" + SQLString.Format(v_year) + " ";
            sql1 += "        and subjseq= " + SQLString.Format(v_subjseq) + " and a.tuserid=" + SQLString.Format(v_userid);

            ls1 = connMgr.executeQuery(sql1);

            if (ls1.next()) {

                v_class = ls1.getString("class");

                //                    v_stucnt = ls1.getInt("stucnt");
                v_grayncnt = ls1.getInt("grayncnt");

                v_price = ls1.getInt("price");
                v_addprice = ls1.getInt("addprice");

                v_jigubfee = (v_grayncnt * v_price) + v_addprice;

                //==만족도 점수가져오기 : 설문 ============================================

                sql2 = " select avg(distcode3_avg) sulsum from TZ_SULEACH a, TZ_STUDENT b ";
                sql2 += " where a.subj=a.subj and a.year=b.year and a.subjseq=b.subjseq and a.userid = b.userid";
                sql2 += "        and a.subj=" + SQLString.Format(v_subj) + " and  a.year=" + SQLString.Format(v_year);
                sql2 += "        and a.subjseq= " + SQLString.Format(v_subjseq) + " and b.class=" + SQLString.Format(v_class) + "";
                sql2 += " group by a.subj, a.year, a.subjseq";

                ls2 = connMgr.executeQuery(sql2);

                if (ls2.next()) {
                    v_sulsum = ls2.getInt("sulsum");
                }
                ls2.close();

                //==만족도 점수가져오기 : 설문 ============================================

                // 구한 값들로 업데이트 한다 tz_tutorgrade
                if (ls1.getInt("gradecnt") > 0) {
                    sql3 = "update TZ_TUTORGRADE set inwon = ?, malevel = ?, sapoint = ?, jigubfee = ? , luserid = ?, ldate=to_char(sysdate,'yyyymmddhh24miss') ";
                    sql3 += "  where subj=" + SQLString.Format(v_subj) + " and  year=" + SQLString.Format(v_year) + " ";
                    sql3 += "        and subjseq= " + SQLString.Format(v_subjseq) + " and userid=" + SQLString.Format(v_userid) + " and rownum=1";
                } else {
                    sql3 = "insert into TZ_TUTORGRADE (subj, year, subjseq, userid, ldate, ";
                    sql3 += " inwon, malevel, sapoint, jigubfee, luserid ) ";
                    sql3 += "values(" + SQLString.Format(v_subj) + "," + SQLString.Format(v_year) + "," + SQLString.Format(v_subjseq) + ", ";
                    sql3 += " " + SQLString.Format(v_userid) + ", to_char(sysdate,'yyyymmddhh24miss'),  ";
                    sql3 += " ?,?,?,?,?)";
                }
                pstmt = connMgr.prepareStatement(sql3);
                i = 1;
                pstmt.setInt(i++, v_grayncnt);
                pstmt.setString(i++, v_malevel);
                pstmt.setInt(i++, v_sulsum);
                pstmt.setInt(i++, v_jigubfee);
                pstmt.setString(i++, s_userid);
                isOk = pstmt.executeUpdate();

            } // END while

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql3);
            throw new Exception("sql3 = " + sql3 + "\r\n" + ex.getMessage());
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
            if (pstmt != null) {
                try {
                    pstmt.close();
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
     * 강사운영자평가 저장 2005.01.28 kimsujin
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * 
     *         public int calcTutorGrade(RequestBox box) throws Exception {
     *         DBConnectionManager connMgr = null; PreparedStatement pstmt =
     *         null; ListSet ls1 = null; ListSet ls2 = null; String sql1 = "",
     *         sql2="", sql3=""; int isOk = 0; int i = 0; int v_stucnt = 0,
     *         v_sulsum=0;
     * 
     * 
     *         int v_grayncnt = 0, v_ctutorcnt=0; int v_inwon=0; int v_grcnt1
     *         =0; int v_grcnt2 =0; int v_grcnt3 =0; int v_grcnt4 =0; int
     *         v_grcnt5 =0; int v_inwongrade = 0;
     * 
     *         double v_sapoint = 0; int
     *         v_okrate1=0,v_okrate2=0,v_okrate3=0,v_okrate4=0,v_okrate5=0; int
     *         v_sagrade = 0;
     * 
     *         int v_joinpoint = 0, v_joingrade = 0; int
     *         v_actrate1=0,v_actrate2=0,v_actrate3=0,v_actrate4=0,v_actrate5=0;
     * 
     *         String
     *         v_manrate1="",v_manrate2="",v_manrate3="",v_manrate4="",v_manrate5
     *         =""; int v_magrade = 0;
     * 
     *         int v_total = 0; double v_jigub1=0, v_jigub2=0, v_fjigub = 0;
     * 
     *         int v_basepay = 0 ; double v_realfee = 0 , v_tax=0 ,
     *         v_jigubfee=0; String v_isgubuntype="";
     * 
     *         String s_userid = box.getSession("userid");
     * 
     *         String v_subj = box.getString("p_subj"); String v_year =
     *         box.getString("p_year"); String v_subjseq =
     *         box.getString("p_subjseq"); String v_userid =
     *         box.getString("p_userid"); String v_malevel =
     *         box.getString("p_malevel");
     * 
     *         try { connMgr = new DBConnectionManager(); DataBox dbox =
     *         getMeasure(box);
     * 
     *         sql1 = " select b.isgubuntype "; sql1+="        , (select count(userid) from TZ_TUTORGRADE where subj=a.subj and year=a.year and subjseq=a.subjseq and userid=a.tuserid) gradecnt"
     *         ; sql1+="        , (select count(tuserid) from TZ_CLASSTUTOR where subj=a.subj and year=a.year and subjseq=a.subjseq) ctutorcnt"
     *         ; sql1+="        , (select count(userid) from TZ_STUDENT where subj=a.subj and year=a.year and subjseq=a.subjseq) stucnt"
     *         ; sql1+="        , (select count(userid) from TZ_STOLD where subj=a.subj and year=a.year and subjseq=a.subjseq and isgraduated='Y') grayncnt"
     *         ; sql1+="        , (select count(ordseq) from TZ_PROJORD where subj=a.subj and year=a.year and subjseq=a.subjseq ) reportcnt"
     *         ; sql1+="        , (select count(userid) from TZ_DATABOARD where subj=a.subj and year=a.year and subjseq=a.subjseq and userid=a.tuserid) datacnt"
     *         ; sql1+="        , (select count(userid) from TZ_TORONTP where subj=a.subj and year=a.year and subjseq=a.subjseq and userid=a.tuserid) torontpcnt"
     *         ; sql1+="        , (select count(userid) from TZ_TORON where subj=a.subj and year=a.year and subjseq=a.subjseq and userid=a.tuserid) toroncnt"
     *         ; sql1+="        , (select count(inuserid) from TZ_QNA where subj=a.subj and year=a.year and subjseq=a.subjseq and  seq > 0 and inuserid=a.tuserid) qnacnt"
     *         ; sql1+="        , (select count(userid) from TZ_GONG where subj=a.subj and year=a.year and subjseq=a.subjseq and luserid=a.tuserid) gongcnt"
     *         ; sql1+= "  from TZ_CLASSTUTOR a , TZ_TUTOR b        "; sql1+=
     *         "  where a.tuserid = b.userid and subj="
     *         +SQLString.Format(v_subj)+
     *         " and  year="+SQLString.Format(v_year)+" "; sql1+=
     *         "        and subjseq= "
     *         +SQLString.Format(v_subjseq)+" and a.tuserid="
     *         +SQLString.Format(v_userid)+" and rownum=1";
     * 
     * 
     *         ls1 = connMgr.executeQuery(sql1);
     * 
     *         if (ls1.next()) {
     * 
     *         //==만족도 점수가져오기 : 설문 ============================================
     *         // 화면변경 // SulmunResultBean sulbean = new SulmunResultBean(); //
     *         int sum = sulbean.getTutorResult(connMgr, v_subj, v_year,
     *         v_subjseq); int sum = 0;
     * 
     *         v_stucnt = ls1.getInt("stucnt"); v_sapoint = sum;
     * 
     *         v_okrate1 = dbox.getInt("c_okrate1"); v_okrate2 =
     *         dbox.getInt("c_okrate2"); v_okrate3 = dbox.getInt("c_okrate3");
     *         v_okrate4 = dbox.getInt("c_okrate4"); v_okrate5 =
     *         dbox.getInt("c_okrate5");
     * 
     *         if(v_sapoint >= v_okrate1){ v_sagrade =
     *         dbox.getInt("va_okrate1"); }else if(v_sapoint < v_okrate1 &&
     *         v_sapoint >= v_okrate2){ v_sagrade = dbox.getInt("va_okrate2");
     *         }else if(v_sapoint < v_okrate2 && v_sapoint >= v_okrate3){
     *         v_sagrade = dbox.getInt("va_okrate3"); }else if(v_sapoint <
     *         v_okrate3 && v_sapoint >= v_okrate4){ v_sagrade =
     *         dbox.getInt("va_okrate4"); }else if(v_sapoint < v_okrate5){
     *         v_sagrade = dbox.getInt("va_okrate5"); }
     * 
     *         //인원 점수 매기기기 : 수료인원*(과제유무에 따라 *1.5) 20050317 kimsujin v_grayncnt
     *         = ls1.getInt("grayncnt"); v_ctutorcnt = ls1.getInt("ctutorcnt");
     * 
     *         if(ls1.getInt("reportcnt") > 0){ v_inwon =
     *         (int)Math.round((double)(v_grayncnt*1.5)); }else{ v_inwon =
     *         v_grayncnt; }
     * 
     *         v_grcnt1 = dbox.getInt("c_grcnt1"); v_grcnt2 =
     *         dbox.getInt("c_grcnt2"); v_grcnt3 = dbox.getInt("c_grcnt3");
     *         v_grcnt4 = dbox.getInt("c_grcnt4"); v_grcnt5 =
     *         dbox.getInt("c_grcnt5");
     * 
     *         if(v_inwon >= v_grcnt1){ v_inwongrade = dbox.getInt("va_grcnt1");
     *         }else if(v_inwon < v_grcnt1 && v_inwon >= v_grcnt2){ v_inwongrade
     *         = dbox.getInt("va_grcnt2"); }else if(v_inwon < v_grcnt2 &&
     *         v_inwon >= v_grcnt3){ v_inwongrade = dbox.getInt("va_grcnt3");
     *         }else if(v_inwon < v_grcnt3 && v_inwon >= v_grcnt4){ v_inwongrade
     *         = dbox.getInt("va_grcnt4"); }else if(v_inwon < v_grcnt5){
     *         v_inwongrade = dbox.getInt("va_grcnt5"); }
     * 
     *         //참여도 점수 매기기 v_joinpoint = ls1.getInt("datacnt") +
     *         ls1.getInt("toroncnt") + ls1.getInt("torontpcnt") +
     *         ls1.getInt("qnacnt") + ls1.getInt("gongcnt") ;
     * 
     *         v_actrate1 = dbox.getInt("c_actrate1"); v_actrate2 =
     *         dbox.getInt("c_actrate2"); v_actrate3 =
     *         dbox.getInt("c_actrate3"); v_actrate4 =
     *         dbox.getInt("c_actrate4"); v_actrate5 =
     *         dbox.getInt("c_actrate5");
     * 
     *         if(v_joinpoint >= v_actrate1){ v_joingrade =
     *         dbox.getInt("va_actrate1"); }else if(v_joinpoint < v_actrate1 &&
     *         v_joinpoint >= v_actrate2){ v_joingrade =
     *         dbox.getInt("va_actrate2"); }else if(v_joinpoint < v_actrate2 &&
     *         v_joinpoint >= v_actrate3){ v_joingrade =
     *         dbox.getInt("va_actrate3"); }else if(v_joinpoint < v_actrate3 &&
     *         v_joinpoint >= v_actrate4){ v_joingrade =
     *         dbox.getInt("va_actrate4"); }else if(v_joinpoint < v_actrate5){
     *         v_joingrade = dbox.getInt("va_actrate5"); }
     * 
     *         //강사 평가점수 v_manrate1 = dbox.getString("c_manrate1"); v_manrate2 =
     *         dbox.getString("c_manrate2"); v_manrate3 =
     *         dbox.getString("c_manrate3"); v_manrate4 =
     *         dbox.getString("c_manrate4"); v_manrate5 =
     *         dbox.getString("c_manrate5");
     * 
     *         if(v_malevel.equals(v_manrate1)){ v_magrade =
     *         dbox.getInt("va_manrate1"); }else
     *         if(v_malevel.equals(v_manrate2)){ v_magrade =
     *         dbox.getInt("va_manrate2"); }else
     *         if(v_malevel.equals(v_manrate3)){ v_magrade =
     *         dbox.getInt("va_manrate3"); }else
     *         if(v_malevel.equals(v_manrate4)){ v_magrade =
     *         dbox.getInt("va_manrate4"); }else
     *         if(v_malevel.equals(v_manrate5)){ v_magrade =
     *         dbox.getInt("va_manrate5"); }
     * 
     * 
     *         // 1차 지급율: 리포트가 있으면 // v_jigub1 : 합산점수 // v_jigub2 : 2차 지급율 - 리포트
     *         포인트적용 잇으면 *1.5 // v_fjigub : 최종 지급율 - 참여도가 0점이면 0
     * 
     *         v_jigub1 = (double)Math.round((double)(v_inwongrade + v_sagrade +
     *         v_joingrade + v_magrade)/100*100)/100 ;
     * 
     *         v_jigub2 = v_jigub1;
     * 
     *         //과제가 없고 총점이 100을 넘을경우에는 점수가 100이상이 될수 없다
     *         if(ls1.getInt("reportcnt") == 0 && v_jigub1 > 1){ v_jigub2 = 1; }
     * 
     *         // 2차 지급율 v_fjigub = v_jigub2; if(v_joingrade == 0){ //참여도 점수가
     *         0이면 지급율도 0이다 v_fjigub = 0; }
     * 
     *         //지급금액 : 기준금액 - (기준금액*tax + (기준금액*tax)*0.1) v_basepay =
     *         dbox.getInt("va_basepay"); v_isgubuntype =
     *         ls1.getString("isgubuntype");
     * 
     *         if(v_isgubuntype.equals(dbox.getString("c_tax1"))){ v_tax =
     *         dbox.getDouble("va_tax1"); }else
     *         if(v_isgubuntype.equals(dbox.getString("c_tax2"))){ v_tax =
     *         dbox.getDouble("va_tax2"); }else
     *         if(v_isgubuntype.equals(dbox.getString("c_tax3"))){ v_tax =
     *         dbox.getDouble("va_tax3"); }
     * 
     *         v_jigubfee = v_basepay * v_fjigub; v_realfee = v_jigubfee +
     *         (int)(v_jigubfee/0.78*(v_tax/100)) +
     *         (int)(v_jigubfee/0.78*(v_tax/100)*0.1);
     * 
     * 
     *         // 구한 값들로 업데이트 한다 tz_tutorgrade if(ls1.getInt("gradecnt") > 0 ){
     *         sql3 ="update TZ_TUTORGRADE set inwon=? ,inwongrade =? ,sapoint =? ,sagrade =? ,joinpoint =? ,joingrade  =?,malevel =?  "
     *         ; sql3 +=" , magrade =? ,jigub1  =? ,jigub2  =? ,fjigub  =? ,jigubfee=? ,realfee=?, luserid =? ,ldate=to_char(sysdate,'yyyymmddhh24miss') "
     *         ; sql3 +=
     *         "  where subj="+SQLString.Format(v_subj)+" and  year="+SQLString
     *         .Format(v_year)+" "; sql3 +=
     *         "        and subjseq= "+SQLString.Format
     *         (v_subjseq)+" and userid="
     *         +SQLString.Format(v_userid)+" and rownum=1"; }else{ sql3 =
     *         "insert into TZ_TUTORGRADE (subj, year, subjseq,userid, ldate, ";
     *         sql3+=
     *         " inwon, inwongrade, sapoint, sagrade, joinpoint,joingrade, malevel,  "
     *         ; sql3+=
     *         " magrade, jigub1, jigub2, fjigub, jigubfee, realfee,luserid )  "
     *         ; sql3+=
     *         " values("+SQLString.Format(v_subj)+","+SQLString.Format(
     *         v_year)+","+SQLString.Format(v_subjseq)+" "; sql3+=
     *         " , "+SQLString
     *         .Format(v_userid)+", to_char(sysdate,'yyyymmddhh24miss')  ";
     *         sql3+= "   , ?,?,?,?,?,?,?,?,?,?,?,?,?,? )"; } pstmt =
     *         connMgr.prepareStatement(sql3); i = 1; pstmt.setInt(i++,v_inwon);
     *         pstmt.setInt(i++,v_inwongrade); pstmt.setDouble(i++,v_sapoint);
     *         pstmt.setInt(i++,v_sagrade); pstmt.setInt(i++,v_joinpoint);
     *         pstmt.setInt(i++,v_joingrade); pstmt.setString(i++,v_malevel);
     *         pstmt.setInt(i++,v_magrade); pstmt.setDouble(i++,v_jigub1);
     *         pstmt.setDouble(i++,v_jigub2); pstmt.setDouble(i++,v_fjigub);
     *         pstmt.setInt(i++,(int)v_jigubfee);
     *         pstmt.setInt(i++,(int)v_realfee); pstmt.setString(i++,s_userid);
     * 
     *         isOk = pstmt.executeUpdate();
     * 
     *         } // END while
     * 
     *         //로그 // LogDB.insertLog(box, "insert", "TZ_TUTORGRADE",
     *         v_subj+","+v_year+","+v_subjseq+","+v_userid, "강사평가저장");
     * 
     * 
     * 
     *         } catch (Exception ex) { ErrorManager.getErrorStackTrace(ex, box,
     *         sql3); throw new Exception("sql3 = " + sql3 + "\r\n" +
     *         ex.getMessage()); } finally { if(ls1 != null) { try {
     *         ls1.close(); }catch (Exception e) {} } if(ls2 != null) { try {
     *         ls2.close(); }catch (Exception e) {} } if(connMgr != null) { try
     *         { connMgr.freeConnection(); }catch (Exception e10) {} } } return
     *         isOk; }
     */

    /**
     * 강사활동관리 리스트 2005.02.01 kimsujin
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectTutorActionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "", sql2 = "", sql3 = "";
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String orderSql = "";
        String countSql = "";

        DataBox dbox1 = null;
        //        int v_pageno = box.getInt("p_pageno");
        String v_subj = "", v_year = "", v_subjseq = "";
        //        int v_grayncnt = 0, v_sulsum=0;
        int v_anscnt = 0//, v_noanscnt=0
        , v_examcnt = 0, v_nograde = 0;
        //				int v_price=0,v_addprice=0,v_pay=0;
        int v_repcnt = 0, v_noscorecnt = 0;

        String v_process = box.getString("p_process");
        String v_user_id = box.getSession("userid"); // 로그인 사용자
        String v_onoffline = box.getString("p_onoffline");
        String v_idname = box.getString("p_idname");
        String v_textbox = box.getString("p_textbox");

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        String ss_edustart = (!box.getString("p_edustart").equals("")) ? (box.getString("p_edustart")).replaceAll("-", "") + "00" : ""; //학습시작일
        String ss_eduend = (!box.getString("p_eduend").equals("")) ? (box.getString("p_eduend")).replaceAll("-", "") + "24" : ""; //학습종료일

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        if (v_onoffline.equals("off"))
            ss_grcode = "";
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        if (v_onoffline.equals("off"))
            ss_gyear = box.getStringDefault("s_year", ""); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        if (v_onoffline.equals("off"))
            ss_grseq = ""; //교육차수
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//과정&코스
        if (v_onoffline.equals("off"))
            ss_subjcourse = box.getStringDefault("s_subjcode", "ALL");
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //과정 차수
        String ss_action = box.getString("s_action");
        String v_isclosed = box.getString("p_isclosed");

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        try {
            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList<DataBox>();

                //				headSql.append(" SELECT  C.GRCODE, C.GRSEQ, C.SUBJ, C.YEAR, C.SUBJNM, C.SUBJSEQ, C.EDUSTART                    \n");
                //				headSql.append("         , C.EDUEND, C.ISCLOSED, E.LDATE LDATE, A.CLASS, A.TUSERID, B.NAME                     \n");
                //				headSql.append("         , B.RESNO, B.EMAIL, B.TUTORGUBUN, D.PRICE, D.ADDPRICE                                 \n");
                //				headSql.append("         , X.STUCNT, X.GRAYNCNT, Y.CTUTORCNT, Y.REPCNT, Y.NOSCORECNT, Y.REPORTCNT              \n");
                //				headSql.append("         , Y.QCNT, Z.DATACNT, Z.GONGCNT, Y.NOANSCNT                                            \n");
                //				headSql.append("         , (                                                                                   \n");
                //				headSql.append("             SELECT  SUM(EXAMCNT)                                                              \n");
                //				headSql.append("             FROM    TZ_EXAMMASTER                                                             \n");
                //				headSql.append("             WHERE   SUBJ=A.SUBJ                                                               \n");
                //				headSql.append("             AND     EXAMTYPE='T'                                                              \n");
                //				headSql.append("         ) EXAM_DESCNT                                                                         \n");
                //				headSql.append("         , (                                                                                   \n");
                //				headSql.append("             SELECT  COUNT(SERNO)                                                              \n");
                //				headSql.append("             FROM    TZ_TUTORLOG                                                               \n");
                //				headSql.append("             WHERE   TUSERID = A.TUSERID                                                       \n");
                //				headSql.append("             AND     SUBSTR(LOGIN,1,8) BETWEEN C.EDUSTART AND C.EDUEND                         \n");
                //				headSql.append("         ) LOGINCNT                                                                            \n");
                //				headSql.append("         , (                                                                                   \n");
                //				headSql.append("             SELECT  ROUND(AVG(SCORE),3) SCORE                                                 \n");
                //				headSql.append("             FROM    TZ_PROJREP                                                                \n");
                //				headSql.append("             WHERE   SCORE !=0                                                                 \n");
                //				headSql.append("             AND     SUBJ=A.SUBJ                                                               \n");
                //				headSql.append("             GROUP BY SUBJ                                                                     \n");
                //				headSql.append("         ) SUBJREPAVG                                                                          \n");
                //				bodySql.append(" FROM    TZ_CLASSTUTOR A, VZ_SCSUBJSEQ C                                                        \n");
                //				bodySql.append("         , TZ_TUTOR B, TZ_TUTORPAY D,TZ_SUBJSEQ  E                                             \n");
                //				bodySql.append("         , (                                                                                   \n");
                //				bodySql.append("             SELECT  SUBJ, YEAR, SUBJSEQ, CLASS                                                \n");
                //				bodySql.append("                     , SUM(STUCNT) STUCNT                                                      \n");
                //				bodySql.append("                     , SUM(GRAYNCNT) GRAYNCNT                                                  \n");
                //				bodySql.append("             FROM    (                                                                         \n");
                //				bodySql.append("                     SELECT  SUBJ, YEAR, SUBJSEQ, CLASS                                        \n");
                //				bodySql.append("                             , 1 STUCNT                                                        \n");
                //				bodySql.append("                             , CASE WHEN ISGRADUATED = 'Y' THEN 1                              \n");
                //				bodySql.append("                                    ELSE 0                                                     \n");
                //				bodySql.append("                             END GRAYNCNT                                                      \n");
                //				bodySql.append("                     FROM    TZ_STUDENT                                                        \n");
                //				bodySql.append("                     )                                                                         \n");
                //				bodySql.append("             GROUP BY SUBJ, YEAR, SUBJSEQ, CLASS                                               \n");
                //				bodySql.append("         ) X                                                                                   \n");
                //				bodySql.append("         , (                                                                                   \n");
                //				bodySql.append("             SELECT  SUM(CTUTORCNT) CTUTORCNT                                                  \n");
                //				bodySql.append("                     , SUM(REPCNT) REPCNT                                                      \n");
                //				bodySql.append("                     , SUM(NOSCORECNT) NOSCORECNT                                              \n");
                //				bodySql.append("                     , SUM(REPORTCNT) REPORTCNT                                                \n");
                //				bodySql.append("                     , SUM(QCNT) QCNT                                                          \n");
                //				bodySql.append("                     , SUM(NOANSCNT) NOANSCNT                                                  \n");
                //				bodySql.append("                     , SUBJ, YEAR, SUBJSEQ                                                     \n");
                //				bodySql.append("             FROM    (                                                                         \n");
                //				bodySql.append("                     SELECT  1 CTUTORCNT, 0 REPCNT, 0 NOSCORECNT, 0 REPORTCNT                  \n");
                //				bodySql.append("                             , 0 QCNT, 0 NOANSCNT, SUBJ, YEAR, SUBJSEQ                         \n");
                //				bodySql.append("                     FROM    TZ_CLASSTUTOR                                                     \n");
                //				bodySql.append("                     UNION ALL                                                                 \n");
                //				bodySql.append("                     SELECT  0 CTUTORCNT, 1 REPCNT                                             \n");
                //				bodySql.append("                             , CASE WHEN ISFINAL = 'N' THEN 1                                  \n");
                //				bodySql.append("                                    ELSE 0                                                     \n");
                //				bodySql.append("                             END NOSCORECNT                                                    \n");
                //				bodySql.append("                             , 0 REPORTCNT, 0 QCNT, 0 NOANSCNT, SUBJ, YEAR, SUBJSEQ            \n");
                //				bodySql.append("                     FROM    TZ_PROJREP                                                        \n");
                //				bodySql.append("                     UNION ALL                                                                 \n");
                //				bodySql.append("                     SELECT  0 CTUTORCNT, 0 REPCNT, 0 NOSCORECNT, 1 REPORTCNT                  \n");
                //				bodySql.append("                             , 0 QCNT, 0 NOANSCNT, SUBJ, YEAR, SUBJSEQ                         \n");
                //				bodySql.append("                     FROM    TZ_PROJORD                                                        \n");
                //				bodySql.append("                     UNION ALL                                                                 \n");
                //				bodySql.append("                     SELECT  0 CTUTORCNT, 0 REPCNT, 0 NOSCORECNT, 0 REPORTCNT                  \n");
                //				bodySql.append("                             , 1 QCNT                                                          \n");
                //				bodySql.append("                             , CASE WHEN REPLYDATE IS NULL THEN 1                              \n");
                //				bodySql.append("                                    ELSE 0                                                     \n");
                //				bodySql.append("                             END NOANSCNT                                                      \n");
                //				bodySql.append("                             , SUBJ, YEAR, SUBJSEQ                                             \n");
                //				bodySql.append("                     FROM    TZ_QNA                                                            \n");
                //				bodySql.append("                     WHERE   KIND='0'                                                          \n");
                //				bodySql.append("                     AND     TOGUBUN='2'                                                       \n");
                //				bodySql.append("                     )                                                                         \n");
                //				bodySql.append("             GROUP BY SUBJ, YEAR, SUBJSEQ                                                      \n");
                //				bodySql.append("         ) Y                                                                                   \n");
                //				bodySql.append("         , (                                                                                   \n");
                //				bodySql.append("             SELECT  SUM(DATACNT) DATACNT                                                      \n");
                //				bodySql.append("                     , SUM(GONGCNT) GONGCNT                                                    \n");
                //				bodySql.append("                     , SUBJ, YEAR, SUBJSEQ, USERID                                             \n");
                //				bodySql.append("             FROM    (                                                                         \n");
                //				bodySql.append("                     SELECT  1 DATACNT, 0 GONGCNT                                              \n");
                //				bodySql.append("                             , SUBJ, YEAR, SUBJSEQ, USERID                                     \n");
                //				bodySql.append("                     FROM    TZ_DATABOARD                                                      \n");
                //				bodySql.append("                     UNION ALL                                                                 \n");
                //				bodySql.append("                     SELECT  0 DATACNT, 1 GONGCNT                                              \n");
                //				bodySql.append("                             , SUBJ, YEAR, SUBJSEQ, LUSERID                                    \n");
                //				bodySql.append("                     FROM    TZ_GONG                                                           \n");
                //				bodySql.append("                     )                                                                         \n");
                //				bodySql.append("             GROUP BY SUBJ, YEAR, SUBJSEQ, USERID                                              \n");
                //				bodySql.append("         ) Z                                                                                   \n");
                //				bodySql.append(" WHERE   A.TUSERID       = B.USERID                                                         \n");
                //				bodySql.append(" AND     A.SUBJ          = C.SUBJ                                                           \n");
                //				bodySql.append(" AND     A.YEAR          = C.YEAR                                                           \n");
                //				bodySql.append(" AND     A.SUBJSEQ       = C.SUBJSEQ                                                        \n");
                //				bodySql.append(" AND     B.TUTORGUBUN    = D.TUTORCODE                                                      \n");
                //				bodySql.append(" AND     C.SCSUBJ        = E.SUBJ                                                           \n");
                //				bodySql.append(" AND     C.SCYEAR        = E.YEAR                                                           \n");
                //				bodySql.append(" AND     C.SCSUBJSEQ     = E.SUBJSEQ                                                        \n");
                //
                //				bodySql.append(" AND     A.SUBJ          = X.SUBJ(+)                                                           \n");
                //				bodySql.append(" AND     A.YEAR          = X.YEAR(+)                                                           \n");
                //				bodySql.append(" AND     A.SUBJSEQ       = X.SUBJSEQ(+)                                                        \n");
                //				bodySql.append(" AND     A.CLASS         = X.CLASS(+)                                                          \n");
                //				bodySql.append(" AND     A.SUBJ          = Y.SUBJ(+)                                                           \n");
                //				bodySql.append(" AND     A.YEAR          = Y.YEAR(+)                                                           \n");
                //				bodySql.append(" AND     A.SUBJSEQ       = Y.SUBJSEQ(+)                                                        \n");
                //				bodySql.append(" AND     A.SUBJ          = Z.SUBJ(+)                                                           \n");
                //				bodySql.append(" AND     A.YEAR          = Z.YEAR(+)                                                           \n");
                //				bodySql.append(" AND     A.SUBJSEQ       = Z.SUBJSEQ(+)                                                        \n");
                //				bodySql.append(" AND     A.TUSERID       = Z.USERID(+)                                                         \n");

                if (v_onoffline.equals("on")) {
                    headSql.append("select a.grcode,a.subj,a.subjnm,a.year,a.subjseq,b.userid,c.name,a.isclosed,\n");
                    headSql.append("(select count(*) from tz_propose z where a.subj=z.subj and a.year=z.year and a.subjseq=z.subjseq) as stucnt,\n");
                    headSql.append("(select count(*) from TZ_STUDENT z where ISGRADUATED='Y' and a.subj=z.subj and a.year=z.year and a.subjseq=z.subjseq) as grayncnt,\n");
                    headSql.append("(select count(*) from TZ_subjman z where a.subj=z.subj and a.year=z.year and a.subjseq=z.subjseq) as grayncnt,\n");
                    headSql.append("(SELECT COUNT(SERNO) FROM TZ_TUTORLOG z WHERE z.TUSERID = b.userid AND SUBSTR(z.LOGIN,1,8) BETWEEN a.EDUSTART AND a.EDUEND) LOGINCNT,\n");
                    headSql.append("(select count(luserid) from TZ_GONG z where z.subj=b.subj and z.year=b.year and z.subjseq=b.subjseq and z.luserid=b.userid) as gongcnt,\n");
                    headSql.append("(select count(inuserid) from TZ_QNA z where z.subj=b.subj and z.year=b.year and z.subjseq=b.subjseq and  seq > 0 and z.inuserid=b.userid) as qnacnt,\n");
                    headSql.append("(select count(luserid) from TZ_TORON z where z.subj=b.subj and z.year=b.year and z.subjseq=b.subjseq and z.luserid=b.userid) as toroncnt,\n");
                    headSql.append("(SELECT  SUM(EXAMCNT) FROM    TZ_EXAMMASTER WHERE   SUBJ=A.SUBJ AND     EXAMTYPE='T') AS EXAM_DESCNT, \n");
                    headSql.append("CTUTORCNT,REPCNT,NOSCORECNT,REPORTCNT,QCNT,NOANSCNT \n");
                    bodySql.append("from tz_subjseq a \n");
                    bodySql.append("left join TZ_subjman b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq \n");
                    bodySql.append("left join TZ_TUTOR c on b.userid=c.userid \n");
                } else {
                    headSql.append("select '오프라인' as grcode,a.subj,a.subjnm,a.year,a.subjseq,b.userid,c.name,a.isclosed,\n");
                    headSql.append("(select count(*) from tz_propose z where a.subj=z.subj and a.year=z.year and a.subjseq=z.subjseq) as stucnt,\n");
                    headSql.append("(select count(*) from TZ_STUDENT z where ISGRADUATED='Y' and a.subj=z.subj and a.year=z.year and a.subjseq=z.subjseq) as grayncnt,\n");
                    headSql.append("(select count(*) from TZ_subjman z where a.subj=z.subj and a.year=z.year and a.subjseq=z.subjseq) as grayncnt,\n");
                    headSql.append("(SELECT COUNT(SERNO) FROM TZ_TUTORLOG z WHERE z.TUSERID = b.userid AND SUBSTR(z.LOGIN,1,8) BETWEEN a.EDUSTART AND a.EDUEND) LOGINCNT,\n");
                    headSql.append("(select count(luserid) from TZ_GONG z where z.subj=b.subj and z.year=b.year and z.subjseq=b.subjseq and z.luserid=b.userid) as gongcnt,\n");
                    headSql.append("(select count(inuserid) from TZ_QNA z where z.subj=b.subj and z.year=b.year and z.subjseq=b.subjseq and  seq > 0 and z.inuserid=b.userid) as qnacnt,\n");
                    headSql.append("(select count(luserid) from TZ_TORON z where z.subj=b.subj and z.year=b.year and z.subjseq=b.subjseq and z.luserid=b.userid) as toroncnt,\n");
                    headSql.append("(SELECT  SUM(EXAMCNT) FROM    TZ_EXAMMASTER WHERE   SUBJ=A.SUBJ AND     EXAMTYPE='T') AS EXAM_DESCNT, \n");
                    headSql.append("CTUTORCNT,REPCNT,NOSCORECNT,REPORTCNT,QCNT,NOANSCNT \n");
                    bodySql.append("from tz_offsubjseq a \n");
                    bodySql.append("left join TZ_subjman b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq \n");
                    bodySql.append("left join TZ_TUTOR c on b.userid=c.userid \n");
                    bodySql.append("left join TZ_offsubj d on a.subj=d.subj \n");
                }

                bodySql.append("left join (SELECT  SUM(CTUTORCNT) CTUTORCNT, SUM(REPCNT) REPCNT, SUM(NOSCORECNT) NOSCORECNT, SUM(REPORTCNT) REPORTCNT, SUM(QCNT) QCNT,SUM(NOANSCNT) NOANSCNT, SUBJ, YEAR, SUBJSEQ \n");
                bodySql.append("FROM    ( \n");
                bodySql.append("SELECT  1 CTUTORCNT, 0 REPCNT, 0 NOSCORECNT, 0 REPORTCNT \n");
                bodySql.append(", 0 QCNT, 0 NOANSCNT, SUBJ, YEAR, SUBJSEQ \n");
                bodySql.append("FROM    TZ_CLASSTUTOR \n");
                bodySql.append("UNION ALL \n ");
                bodySql.append("SELECT  0 CTUTORCNT, 1 REPCNT \n  ");
                bodySql.append(", CASE WHEN ISFINAL = 'N' THEN 1 ELSE 0 END NOSCORECNT \n");
                bodySql.append(", 0 REPORTCNT, 0 QCNT, 0 NOANSCNT, SUBJ, YEAR, SUBJSEQ \n ");
                bodySql.append("FROM    TZ_PROJREP \n");
                bodySql.append("UNION ALL \n");
                bodySql.append("SELECT  0 CTUTORCNT, 0 REPCNT, 0 NOSCORECNT, 1 REPORTCNT, 0 QCNT, 0 NOANSCNT, SUBJ, YEAR, SUBJSEQ \n");
                bodySql.append("FROM    TZ_PROJORD \n");
                bodySql.append("UNION ALL \n");
                bodySql.append("SELECT  0 CTUTORCNT, 0 REPCNT, 0 NOSCORECNT, 0 REPORTCNT , 1 QCNT \n");
                bodySql.append(", CASE WHEN REPLYDATE IS NULL THEN 1 ELSE 0 END NOANSCNT, SUBJ, YEAR, SUBJSEQ \n");
                bodySql.append("FROM    TZ_QNA \n");
                bodySql.append("WHERE   KIND='0' AND     TOGUBUN='2') \n");
                bodySql.append("GROUP BY SUBJ, YEAR, SUBJSEQ \n");
                bodySql.append(") g on b.subj=g.subj and b.year=g.year and b.subjseq=g.subjseq \n");
                bodySql.append("where 1=1 \n");

                if (v_process.equals("listPage2"))
                    bodySql.append(" and b.userid = " + SQLString.Format(v_user_id) + "\n"); // v_user_id(강사 로그인 아이디)
                if (!v_isclosed.equals(""))
                    bodySql.append(" and a.isclosed = " + SQLString.Format(v_isclosed) + "\n"); // v_user_id(강사 로그인 아이디)

                if (v_onoffline.equals("on")) {
                    if (!ss_grcode.equals("ALL") && !ss_grcode.equals("----") && !ss_grcode.equals(""))
                        bodySql.append(" and a.grcode       = " + SQLString.Format(ss_grcode) + "\n");
                    if (!ss_gyear.equals("ALL") && !ss_gyear.equals(""))
                        bodySql.append(" and a.gyear        = " + SQLString.Format(ss_gyear) + "\n");
                    if (!ss_grseq.equals("ALL") && !ss_grseq.equals(""))
                        bodySql.append(" and a.grseq        = " + SQLString.Format(ss_grseq) + "\n");
                    if (!ss_uclass.equals("ALL") && !ss_uclass.equals(""))
                        bodySql.append(" and a.scupperclass = " + SQLString.Format(ss_uclass) + "\n");
                    if (!ss_mclass.equals("ALL") && !ss_mclass.equals(""))
                        bodySql.append(" and a.scmiddleclass = " + SQLString.Format(ss_mclass) + "\n");
                    if (!ss_lclass.equals("ALL") && !ss_lclass.equals(""))
                        bodySql.append(" and a.sclowerclass = " + SQLString.Format(ss_lclass) + "\n");
                } else {
                    if (!ss_gyear.equals("ALL") && !ss_gyear.equals(""))
                        bodySql.append(" and a.year        = " + SQLString.Format(ss_gyear) + "\n");
                    if (!ss_uclass.equals("ALL") && !ss_uclass.equals(""))
                        bodySql.append(" and c.UPPERCLASS = " + SQLString.Format(ss_uclass) + "\n");
                    if (!ss_mclass.equals("ALL") && !ss_mclass.equals(""))
                        bodySql.append(" and c.MIDDLECLASS = " + SQLString.Format(ss_mclass) + "\n");
                    if (!ss_lclass.equals("ALL") && !ss_lclass.equals(""))
                        bodySql.append(" and c.LOWERCLASS = " + SQLString.Format(ss_lclass) + "\n");
                }

                if (!ss_subjcourse.equals("ALL") && !ss_subjcourse.equals(""))
                    bodySql.append(" and a.subj       = " + SQLString.Format(ss_subjcourse) + "\n");
                if (!ss_subjseq.equals("ALL") && !ss_subjseq.equals(""))
                    bodySql.append(" and a.subjseq    = " + SQLString.Format(ss_subjseq) + "\n");

                if (!ss_edustart.equals(""))
                    bodySql.append(" and a.edustart >= " + SQLString.Format(ss_edustart) + "\n");
                if (!ss_eduend.equals(""))
                    bodySql.append(" and a.eduend <= " + SQLString.Format(ss_eduend) + "\n");

                if (!v_textbox.equals("")) {
                    if (v_idname.equals("name"))
                        bodySql.append(" and c.name  like  '%" + v_textbox + "%' \n");
                    else
                        bodySql.append(" and c.userid  like  '%" + v_textbox + "%' \n");
                }

                if (v_orderColumn.equals("")) {
                    orderSql += " order by a.edustart desc ";
                } else {
                    orderSql += " order by " + v_orderColumn + v_orderType;
                }

                //				System.out.println("강사활동관리 리스트:"+sql1);
                sql1 = headSql.toString() + bodySql.toString() + orderSql;
                ls1 = connMgr.executeQuery(sql1);

                countSql = "select count(*) " + bodySql;

                int total_row_count = BoardPaging.getTotalRow(connMgr, countSql); //     전체 row 수를 반환한다
                //				int total_page_count = ls1.getTotalPage();       //     전체 페이지 수를 반환한다
                ls1.setPageSize(v_pagesize); //  페이지당 row 갯수를 세팅한다
                ls1.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.

                while (ls1.next()) {
                    dbox1 = ls1.getDataBox();

                    dbox1.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                    dbox1.put("d_totalpage", new Integer(ls1.getTotalPage()));
                    dbox1.put("d_rowcount", new Integer(row));
                    dbox1.put("d_totalrowcount", new Integer(total_row_count));

                    v_subj = ls1.getString("subj");
                    v_year = ls1.getString("year");
                    v_subjseq = ls1.getString("subjseq");
                    //                    v_price = ls1.getInt("price");
                    //                    v_addprice = ls1.getInt("addprice");
                    //                    v_grayncnt = ls1.getInt("grayncnt");

                    //					v_pay	= (v_price*v_grayncnt)+v_addprice;
                    //                    dbox1.put("d_pay", v_pay+"");

                    //==미응답 질문 가져오기============================================
                    /*
                     * sql2="select count(seq) cnt "; sql2+=" from TZ_QNA a,";
                     * sql2+="      TZ_CLASSTUTOR b";
                     * sql2+=" where a.subj=b.subj";
                     * sql2+="   and a.year=b.year";
                     * sql2+="   and a.subjseq=b.subjseq";
                     * sql2+="   and a.kind = 0 ";
                     * sql2+="   and a.replydate is null";
                     * sql2+="		and a.togubun='2'";
                     * sql2+="   and b.tuserid="+SQLString.Format(v_user_id);
                     * sql2+="   and a.subj="+SQLString.Format(v_subj);
                     * sql2+="   and a.year="+SQLString.Format(v_year);
                     * sql2+="   and a.subjseq= "+SQLString.Format(v_subjseq);
                     */

                    try {
                        /*
                         * ls2 = connMgr.executeQuery(sql2); if(ls2.next()){
                         * v_anscnt = ls2.getInt("cnt"); } ls2.close();
                         */
                        sql2 = "select count(a.projid) repcnt ";
                        sql2 += "  from TZ_PROJREP a,";
                        sql2 += "       TZ_STUDENT b,";
                        sql2 += "       TZ_CLASSTUTOR c";
                        sql2 += " where a.subj=b.subj ";
                        sql2 += "   and a.year=b.year ";
                        sql2 += "   and a.subjseq=b.subjseq ";
                        sql2 += "   and a.projid=b.userid ";
                        sql2 += "   and a.subj=c.subj ";
                        sql2 += "   and a.year=c.year ";
                        sql2 += "   and a.subjseq=c.subjseq ";
                        sql2 += "   and b.class=c.class ";
                        sql2 += "   and a.subj=" + SQLString.Format(v_subj);
                        sql2 += "   and a.year=" + SQLString.Format(v_year);
                        sql2 += "   and a.subjseq=" + SQLString.Format(v_subjseq);
                        sql2 += "   and c.tuserid=" + SQLString.Format(v_user_id);
                        //System.out.println("레포트 테스트==============="+sql2);
                        //System.out.println("정말 레포트 테스트===============");
                        ls2 = connMgr.executeQuery(sql2);
                        if (ls2.next()) {
                            v_repcnt = ls2.getInt("repcnt");
                        }
                        ls2.close();

                        sql2 = "select count(a.projid) noscorecnt ";
                        sql2 += "  from TZ_PROJREP a,";
                        sql2 += "       TZ_STUDENT b,";
                        sql2 += "       TZ_CLASSTUTOR c";
                        sql2 += " where a.subj=b.subj ";
                        sql2 += "   and a.year=b.year ";
                        sql2 += "   and a.subjseq=b.subjseq ";
                        sql2 += "   and a.projid=b.userid ";
                        sql2 += "   and b.class=c.class ";
                        sql2 += "   and a.isfinal='N'";
                        sql2 += "   and a.subj=" + SQLString.Format(v_subj);
                        sql2 += "   and a.year=" + SQLString.Format(v_year);
                        sql2 += "   and a.subjseq=" + SQLString.Format(v_subjseq);
                        sql2 += "   and c.tuserid=" + SQLString.Format(v_user_id);

                        ls2 = connMgr.executeQuery(sql2);
                        if (ls2.next()) {
                            v_noscorecnt = ls2.getInt("noscorecnt");
                        }
                        ls2.close();

                    } catch (Exception e) {
                    } finally {
                        if (ls2 != null) {
                            try {
                                ls2.close();
                            } catch (Exception e) {
                            }
                        }
                    }

                    //v_noanscnt = ls1.getInt("qcnt") - v_anscnt;  //미응답질문수
                    //if(v_noanscnt < 0 ) { v_noanscnt = 0 ;}
                    dbox1.put("d_noanscnt", String.valueOf(v_anscnt));
                    dbox1.put("d_repcnt", String.valueOf(v_repcnt));
                    dbox1.put("d_noscorecnt", String.valueOf(v_noscorecnt));

                    //서술형이 있으면
                    v_examcnt = 0;
                    v_nograde = 0;

                    if (ls1.getInt("exam_descnt") > 0) {

                        sql3 = "select count(*) cnt, ";
                        // 수정일 : 05.11.03 수정자 : 이나연
                        //						sql3+= "sum(decode(scoreok,'N',1,0)) nograde from TZ_EXAMRESULT ";
                        sql3 += " sum(case scoreok ";
                        sql3 += "			When 'N' 	Then 1 ";
                        sql3 += "			Else 0	 END ) nograde from TZ_EXAMRESULT ";
                        // 수정일 : 05.11.03 수정자 : 이나연 _ 여기까지
                        sql3 += " where subj=" + SQLString.Format(v_subj) + " and  year=" + SQLString.Format(v_year) + " ";
                        sql3 += "       and subjseq= " + SQLString.Format(v_subjseq) + " ";

                        try {
                            ls2 = connMgr.executeQuery(sql3);
                            if (ls2.next()) {
                                v_examcnt = ls2.getInt("cnt");
                                v_nograde = ls2.getInt("nograde"); //미채점
                            }
                        } catch (Exception e) {
                        } finally {
                            if (ls2 != null) {
                                try {
                                    ls2.close();
                                } catch (Exception e) {
                                }
                            }
                        }
                    }

                    dbox1.put("d_examcnt", String.valueOf(v_examcnt));
                    dbox1.put("d_nograde", String.valueOf(v_nograde));

                    list1.add(dbox1);
                } // END while
            } // END if

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
        return list1;
    }

    /**
     * 강사메인(미응답Q/A) 리스트 2005.06.22 jungkyoungjin
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectTutorQnaList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        DataBox dbox1 = null;
        //        int v_seq = 0;
        int anscnt = 0;

        String v_user_id = box.getSession("userid"); // 로그인 아이디

        String ss_edustart = (!box.getString("p_edustart").equals("")) ? (box.getString("p_edustart")).replaceAll("-", "") + "00" : ""; //학습시작일
        String ss_eduend = (!box.getString("p_eduend").equals("")) ? (box.getString("p_eduend")).replaceAll("-", "") + "24" : ""; //학습종료일

        String ss_action = box.getString("p_action");

        try {

            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList<DataBox>();

                sql1 = "select c.subj, c.subjnm, c.year, c.subjseq, d.seq, d.title, get_name(d.inuserid) inuserid, d.indate,";
                sql1 += "(select count(*) from TZ_QNA where subj=d.subj and year=d.year and subjseq=d.subjseq and lesson=d.lesson and seq=d.seq and kind>0) anscnt";
                sql1 += " from TZ_CLASSTUTOR a, VZ_SCSUBJSEQ c, TZ_QNA d";
                sql1 += " where";
                sql1 += " a.tuserid='" + v_user_id + "'"; // v_user_id(강사 로그인 아이디)
                sql1 += " and c.isclosed = 'N'"; // 교육중
                sql1 += " and d.kind = 0"; // 답글이 아닌 것
                //				sql1 += " and togubun = '2'";
                //				sql1 += " and anscnt = 0 ";		// 답변구분이 학습자(1) 인 것
                sql1 += " and a.subj=c.subj and a.year=c.year and a.subjseq=c.subjseq and a.subj=d.subj and a.year=d.year and a.subjseq=d.subjseq";

                if (!ss_edustart.equals(""))
                    sql1 += " and c.edustart <= " + SQLString.Format(ss_edustart);
                if (!ss_eduend.equals(""))
                    sql1 += " and c.eduend >= " + SQLString.Format(ss_eduend);

                sql1 += " order by c.subj,c.year,c.subjseq,d.inuserid";

                ls1 = connMgr.executeQuery(sql1);
                while (ls1.next()) {
                    anscnt = ls1.getInt("anscnt"); // 답글갯수
                    if (anscnt == 0) {
                        dbox1 = ls1.getDataBox();

                        list1.add(dbox1);
                    }
                } // END while
            } // END if
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
     * 강사메인(리포트) 리스트 2005.06.22 jungkyoungjin
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectTutorReportList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        DataBox dbox1 = null;
        // String v_subjnm = "", v_subj = "", v_year = "", v_subjseq = "", v_edustart = "", v_eduend = "";
        // int v_wreport = 0, v_score = 0, v_reportcnt = 0, v_noscorecnt = 0;
        String v_user_id = box.getSession("userid"); // 로그인 사용자

        String ss_edustart = (!box.getString("p_edustart").equals("")) ? (box.getString("p_edustart")).replaceAll("-", "") + "00" : ""; //학습시작일
        String ss_eduend = (!box.getString("p_eduend").equals("")) ? (box.getString("p_eduend")).replaceAll("-", "") + "24" : ""; //학습종료일

        String ss_action = box.getString("p_action");

        try {

            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList<DataBox>();

                // 수정일 : 05.11.03 수정자 : 이나연
                sql1 = "select c.subj, c.subjnm, c.year, c.subjseq, c.edustart, c.eduend, c.wreport, a.stucnt, a.reportcnt,noscorecnt,   \n";
                //				sql1 += " (select round(avg(score)) score from TZ_PROJREP where subj=c.subj group by subj) totavg";
                sql1 += " (select round(avg(score),3) score from TZ_PROJREP where subj=c.subj group by subj) totavg 	\n";
                sql1 += " from 	\n";
                //				sql1 += "(select x.subj, x.year, x.subjseq, count(distinct y.userid) stucnt, count(z.projid) reportcnt, sum(NVL(decode(z.isfinal,'N',1),0)) noscorecnt, round(avg(z.score)) myavg ";
                sql1 += "(select x.subj, x.year, x.subjseq, count(distinct y.userid) stucnt, count(z.projid) reportcnt, 	\n";
                sql1 += "sum(NVL((case z.isfinal 	\n";
                sql1 += "					When 'N' 	Then 1  END ),0)) noscorecnt, 	\n";
                sql1 += "round(avg(z.score),3) myavg 	\n";
                sql1 += " from TZ_CLASSTUTOR x, TZ_STUDENT y, TZ_PROJREP z	\n";
                sql1 += " where	\n";
                sql1 += " x.tuserid=" + SQLString.Format(v_user_id);
                // 수정일 : 05.11.07 수정자 : 이나연 _(+)  수정
                //				sql1 += " and x.subj=y.subj(+) and x.year=y.year(+) and x.subjseq=y.subjseq(+) and x.class=y.class(+)";
                // 수정일 : 05.11.07 수정자 : 이나연 _ y, z 외부관계 조인 수정
                //				sql1 += " and y.subj=z.subj(+) and y.year=z.year(+) and y.subjseq=z.subjseq(+) and y.userid=z.projid(+)";
                sql1 += " and x.subj = y.subj(+) and x.year = y.year(+) and x.subjseq = y.subjseq(+) and x.class = y.class(+)	\n";
                sql1 += " group by x.subj, x.year, x.subjseq) a, 	\n";
                sql1 += " VZ_SCSUBJSEQ c	\n";
                sql1 += " where 	\n";
                sql1 += " c.isclosed = 'N'	\n"; // 교육중
                sql1 += " and a.subj=c.subj and a.year=c.year and a.subjseq=c.subjseq	\n";

                if (!ss_edustart.equals(""))
                    sql1 += " and c.edustart <= " + SQLString.Format(ss_edustart);
                if (!ss_eduend.equals(""))
                    sql1 += " and c.eduend >= " + SQLString.Format(ss_eduend);

                sql1 += " order by noscorecnt desc, eduend  ";

                //				System.out.println(" 강사메인(리포트) 리스트 : " + sql1);
                ls1 = connMgr.executeQuery(sql1);

                while (ls1.next()) {

                    dbox1 = ls1.getDataBox();
                    list1.add(dbox1);

                } // END while
            } // END if

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
     * 강사메인(강사료) 리스트 2005.06.22 jungkyoungjin
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectTutorPay(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList list1 = null;
        String sql1 = "";
        DataBox dbox1 = null;
        //        String v_subjnm = "", v_subj = "", v_year = "", v_subjseq = "", v_edustart = "", v_eduend = "";
        int v_stucnt = 0, v_price = 0, v_addprice = 0, v_pay = 0;
        String v_user_id = box.getSession("userid"); // 로그인 사용자

        String ss_edustart = (!box.getString("p_edustart").equals("")) ? (box.getString("p_edustart")).replaceAll("-", "") + "00" : ""; //학습시작일
        String ss_eduend = (!box.getString("p_eduend").equals("")) ? (box.getString("p_eduend")).replaceAll("-", "") + "24" : ""; //학습종료일

        String ss_action = box.getString("p_action");

        try {

            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList();

                sql1 = "select c.subj, c.subjnm, c.edustart, c.eduend,";
                sql1 += "(select count(userid) from TZ_STUDENT where subj=a.subj and year=a.year and subjseq=a.subjseq and class=a.class and a.tuserid='" + v_user_id + "') stucnt,";
                sql1 += "(select count(userid) from TZ_STUDENT where subj=a.subj and year=a.year and subjseq=a.subjseq and class=a.class and a.tuserid='" + v_user_id + "' and ISGRADUATED='Y') grayncnt, ";
                sql1 += " d.price, d.addprice";
                sql1 += " from TZ_CLASSTUTOR a, TZ_TUTOR b, VZ_SCSUBJSEQ c, TZ_TUTORPAY d";
                sql1 += " where ";
                sql1 += " a.tuserid='" + v_user_id + "'"; // v_user_id(강사 로그인 아이디)
                sql1 += " and c.isclosed = 'N'"; // 교육중
                sql1 += " and a.tuserid = b.userid and a.subj=c.subj and a.year=c.year and a.subjseq=c.subjseq and  b.tutorgubun=d.tutorcode";

                if (!ss_edustart.equals(""))
                    sql1 += " and c.edustart <= " + SQLString.Format(ss_edustart);
                if (!ss_eduend.equals(""))
                    sql1 += " and c.eduend >= " + SQLString.Format(ss_eduend);

                sql1 += " order by edustart asc";

                ls1 = connMgr.executeQuery(sql1);

                while (ls1.next()) {

                    dbox1 = ls1.getDataBox();

                    v_pay = (v_price * v_stucnt) + v_addprice;
                    dbox1.put("pay", v_pay + "");

                    list1.add(dbox1);

                } // END while
            } // END if

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

    // 컨트롤러에 사용되어 오류로 인하여 임시 생성
    // 2005. 11. 18.
    public DataBox getMeasure(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        String sql1 = "";

        try {
            connMgr = new DBConnectionManager();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return dbox;
    }

    // 컨트롤러에 사용되어 오류로 인하여 임시 생성
    // 2005. 11. 18.
    public int updateMeasure(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql1 = "";
        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();

        } catch (Exception ex) {
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    public DataBox selectCareerPrint(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        DataBox dbox1 = null;

        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();

            sql1 = "select a.*";
            sql1 += " from TZ_TUTOR a";
            //            sql1+=" left join tz_member b on a.userid=b.userid ";
            sql1 += " where a.userid=" + SQLString.Format(v_userid);

            ls1 = connMgr.executeQuery(sql1);
            while (ls1.next()) {
                dbox1 = ls1.getDataBox();
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
        return dbox1;
    }

    public ArrayList<DataBox> selectCareerList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        DataBox dbox1 = null;
        ArrayList<DataBox> list1 = null;

        String v_userid = box.getString("p_userid");
        String ss_onoffline = box.getString("p_onoffline");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            if (ss_onoffline.equals("off")) {
                sql1 = "select \n";
                sql1 += "a.subj,a.year,a.subjseq,row_count, min_dt,max_dt, \n";
                sql1 += "    sub_title,sub_content,(sub_time1/60) as time1  \n";
                sql1 += "from ( \n";
                sql1 += "    select a.subj,a.year,a.subjseq,min(sub_dt) as min_dt,max(sub_dt) as max_dt,count(*) as row_count,sum(sub_time) as sub_time1 \n";
                sql1 += "    from TZ_subjman a \n";
                sql1 += "    left join TZ_subjseq_detail b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq \n";
                sql1 += "    where a.userid='" + v_userid + "' \n";
                sql1 += "    group by a.subj,a.year,a.subjseq \n";
                sql1 += ")a \n";
                sql1 += "left join tz_subjseq_detail b on  a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq \n";
            } else {
                sql1 = "select a.subj,a.year,a.subjseq,edustart as min_dt,eduend as max_dt,0 as time1 , subjnm as sub_title,'' as sub_content \n";
                sql1 += "from TZ_subjman a \n";
                sql1 += "left join TZ_subjseq b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq \n";
                sql1 += "where a.userid='" + v_userid + "' ";
            }

            ls1 = connMgr.executeQuery(sql1);
            while (ls1.next()) {
                dbox1 = ls1.getDataBox();
                list1.add(dbox1);
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

    public ArrayList<DataBox> selectSubjHistory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        DataBox dbox1 = null;
        ArrayList<DataBox> list1 = null;

        String v_userid = box.getString("p_userid");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = "select subjnm,a.year,a.subjseq,sub_title,b.edustart,b.eduend,SUB_DT,SUB_TIME,SUB_TARGET \n";
            sql1 += "from tz_subjman a  \n";
            sql1 += "left join tz_subjseq b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq \n";
            sql1 += "left join tz_subjseq_detail c on a.subj=c.subj and a.year=c.year and a.subjseq=c.subjseq \n";
            sql1 += " where 1=1 \n";

            if (!v_userid.equals(""))
                sql1 += " and a.userid='" + v_userid + "' \n";
            if (!v_subj.equals(""))
                sql1 += " and a.subj='" + v_subj + "' \n";
            if (!v_year.equals(""))
                sql1 += " and a.year='" + v_year + "' \n";
            if (!v_subjseq.equals(""))
                sql1 += " and a.subjseq='" + v_subjseq + "' \n";

            ls1 = connMgr.executeQuery(sql1);
            while (ls1.next()) {
                dbox1 = ls1.getDataBox();
                list1.add(dbox1);
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