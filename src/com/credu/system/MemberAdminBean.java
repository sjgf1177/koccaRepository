// **********************************************************
// 1. 제 목: 회원관리 관리
// 2. 프로그램명 : MemberAdminBean.java
// 3. 개 요: 회원관리 관리
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 정상진 2006. 1. 12
// 7. 수 정:
// *****************************************
package com.credu.system;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import jxl.Sheet;
import jxl.Workbook;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.HashCipher;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * 회원관리 관리(ADMIN)
 * 
 * @date : 2003. 7
 * @author : S.W Kang
 */
public class MemberAdminBean {

    public MemberAdminBean() {
    }

    /**
     * 회원이름 조회
     * 
     * @param box receive from the form object and session
     * @return result USER NAME
     */
    public static String getUserName(String userid) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        // ArrayList list = null;
        String sql = "";
        String result = "";

        try {
            connMgr = new DBConnectionManager();

            // list = new ArrayList();

            sql = "  select name from TZ_MEMBER  ";
            sql += "   where userid = " + StringManager.makeSQL(userid.toLowerCase()) + "";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getString("name");
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
        return result;
    }

    /**
     * 회원이름 조회
     * 
     * @param box receive from the form object and session
     * @return result USER NAME
     */
    public String getUserInfo(String userid) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        // ArrayList list = null;
        String sql = "";
        String result = "";
        String v_userid = userid.toLowerCase();

        try {
            connMgr = new DBConnectionManager();

            // list = new ArrayList();

            sql = "  select email, name, comptel from TZ_MEMBER  ";
            sql += "   where userid = " + StringManager.makeSQL(v_userid) + "";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = "<a href='mailto:" + ls.getString("email") + "'>" + ls.getString("name") + "</a>  (☎ : " + ls.getString("comptel") + ")";
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
        return result;
    }

    /**
     * 회원 등록
     * 
     * @param box receive from the form object and session
     * @return int 정상등록여부(1 : 정상, 0 : 오류)
     */
    @SuppressWarnings("unchecked")
    public Hashtable insertMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();
        int isOk = 1;
        int index = 1;

        boolean v_CreateConnManager = false;

        Hashtable outputdata = new Hashtable();
        outputdata.put("p_errorcode", "1");

        String userid = box.getString("userid");
        String name = box.getString("name");
        String pwd = box.getString("pwd");
        String handphone = box.getString("handphone");
        String email = box.getString("email");
        String birth = box.getString("birth");
        String sex = box.getString("sex");
        String deptNm = box.getString("deptNm");
        String isMailing = box.getString("isMailing");
        String memberGubun = box.getString("memberGubun");
        String grcode = box.getString("grcode");
        String mobileUserid = box.getString("mobileUserid");

        String memberYear = "";
        String memberMonth = "";
        String memberDay = "";

        if (!birth.equals("")) {
            memberYear = birth.substring(0, 4).toString();
            memberMonth = birth.substring(4, 6).toString();
            memberDay = birth.substring(6, 8).toString();
        }

        try {

            connMgr = (DBConnectionManager) box.getObject("connMgr");

            if (connMgr == null) {
                System.out.println("new connection");
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }

            // connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);// //

            sql.append("INSERT  INTO  TZ_MEMBER  (  \n");
            sql.append("        GRCODE  \n");
            sql.append("    ,   USERID  \n");
            sql.append("    ,   NAME    \n");
            sql.append("    ,   EMAIL   \n");
            sql.append("    ,   PWD     \n");
            sql.append("    ,   HOMETEL \n");
            sql.append("    ,   HANDPHONE   \n");
            sql.append("    ,   ISMAILING   \n");
            sql.append("    ,   ISLETTERING \n");
            sql.append("    ,   ISOPENING   \n");
            sql.append("    ,   ISSMS   \n");
            sql.append("    ,   PRIVATE_YESNO   \n");
            sql.append("    ,   MEMBERGUBUN \n");
            sql.append("    ,   STATE   \n");
            sql.append("    ,   VALIDATION  \n");
            sql.append("    ,   INDATE  \n");
            sql.append("    ,   LDATE   \n");
            sql.append("    ,   SEX     \n");
            sql.append("    ,   MEMBERYEAR  \n");
            sql.append("    ,   MEMBERMONTH \n");
            sql.append("    ,   MEMBERDAY   \n");
            sql.append("    ,   DEPTNAM \n");
            sql.append("    ,   MOBILE_USERID   \n");
            sql.append("    ,   ISAGRE_CHK   \n");
            sql.append(" ) VALUES ( \n");
            sql.append("        ?   /* GRCODE */  \n");
            sql.append("    ,   ?   /* USERID */  \n");
            sql.append("    ,   ?   /* NAME */  \n");
            sql.append("    ,   CRYPTO.ENC('normal', ?) /* EMAIL */  \n");
            sql.append("    ,   ?   /* PWD */  \n");
            sql.append("    ,   CRYPTO.ENC('normal', '00-0000-0000') /* HOMETEL */  \n");
            sql.append("    ,   CRYPTO.ENC('normal', ?) /* HANDPHONE */  \n");
            sql.append("    ,   ?   /* ISMAILING */  \n");
            sql.append("    ,   'N' /* ISLETTERING */  \n");
            sql.append("    ,   'N' /* ISOPENING */  \n");
            sql.append("    ,   'N' /* ISSMS */ \n");
            sql.append("    ,   'N' /* PRIVATE_YESNO */ \n");
            sql.append("    ,   ?   /* MEMBERGUBUN */  \n");
            sql.append("    ,   'Y' /* STATE */  \n");
            sql.append("    ,   0   /* VALIDATION */  \n");
            sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') /* INDATE */  \n");
            sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') /* LDATE */  \n");
            sql.append("    ,   ?   /* SEX */  \n");
            sql.append("    ,   ?   /* MEMBERYEAR */  \n");
            sql.append("    ,   ?   /* MEMBERMONTH */  \n");
            sql.append("    ,   ?   /* MEMBERDAY */  \n");
            sql.append("    ,   ?   /* DEPTNAM */  \n");
            sql.append("    ,   ?   /* MOBILE_USERID */  \n");
            sql.append("    ,   'N' /* ISAGRE_CHK */  \n");
            sql.append(")   \n");

            System.out.println(sql);

            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(index++, grcode);
            pstmt.setString(index++, userid);
            pstmt.setString(index++, name);
            pstmt.setString(index++, email);
            pstmt.setString(index++, pwd);
            pstmt.setString(index++, handphone);
            pstmt.setString(index++, isMailing);
            pstmt.setString(index++, memberGubun);
            pstmt.setString(index++, sex);
            pstmt.setString(index++, memberYear);
            pstmt.setString(index++, memberMonth);
            pstmt.setString(index++, memberDay);
            pstmt.setString(index++, deptNm);
            pstmt.setString(index++, mobileUserid);

            isOk = pstmt.executeUpdate();

            System.out.println("isOk_" + userid + "===>>>>" + isOk);

            connMgr.commit();
        } catch (Exception ex) {
            outputdata.put("p_errorcode", "0");
            outputdata.put("p_exception", ex);
            // ErrorManager.getErrorStackTrace(ex, null, sql);
            // throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (v_CreateConnManager) {
                if (connMgr != null) {
                    try {
                        connMgr.freeConnection();
                    } catch (Exception e10) {
                    }
                }
            }
        }
        return outputdata;
    }

    /**
     * 회원 등록
     * 
     * @param box receive from the form object and session
     * @return int 정상등록여부(1 : 정상, 0 : 오류)
     */
    public int insertNewMember(RequestBox box) throws Exception {

        boolean v_CreateConnManager = false;
        // boolean v_CreatePreparedStatement = false;

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String sql1 = "";
        int isOk = 1;
        int isOk1 = 1;
        int success = 0;
        String v_userid = "";

        // String v_comp = box.getString("comp");

        try {

            connMgr = (DBConnectionManager) box.getObject("connMgr");

            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }

            connMgr.setAutoCommit(false);

            sql = "select a.userid from tz_membertemp a where a.userid not in (select userid from tz_member)";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                v_userid = ls.getString("userid");

                sql1 = "insert into \"TZ_MEMBER\" ";
                sql1 += " (                                                                    \n";
                sql1 += " USERID        ,   RESNO         ,   PWD           ,   NAME          ,\n";
                sql1 += " EMAIL         ,   CONO          ,   POST1         ,   POST2         ,\n";
                sql1 += " ADDR          ,   HOMETEL       ,   HANDPHONE     ,   COMPTEL       ,\n";
                sql1 += " TEL_LINE      ,   COMP          ,   INDATE        ,   LGCNT         ,\n";
                sql1 += " LGLAST        ,   LGIP          ,   JIKUP         ,   JIKUPNM       ,\n";
                sql1 += " JIKWI         ,   JIKWINM       ,   OFFICE_GBN    ,   OFFICE_GBNNM  ,\n";
                sql1 += " WORK_PLC      ,   WORK_PLCNM    ,   DEPTCOD       ,   DEPTNAM       ,\n";
                sql1 += " LDATE         ,   LGFIRST       ,   ISMAILING     ,   ADDR2         ,\n";
                sql1 += " ADDTXT        ,   AESID         ,   VALIDATION    )       \n";
                // 아래values
                sql1 += "select \n";
                sql1 += " USERID        ,   RESNO         ,   PWD           ,   NAME          ,\n";
                sql1 += " EMAIL         ,   CONO          ,   POST1         ,   POST2         ,\n";
                sql1 += " ADDR          ,   HOMETEL       ,   HANDPHONE     ,   COMPTEL       ,\n";
                sql1 += " TEL_LINE      ,   COMP          ,   INDATE        ,   LGCNT         ,\n";
                sql1 += " LGLAST        ,   LGIP          ,   JIKUP         ,   JIKUPNM       ,\n";
                sql1 += " JIKWI         ,   JIKWINM       ,   OFFICE_GBN    ,   'Y'           ,\n";
                sql1 += " WORK_PLC      ,   WORK_PLCNM    ,   DEPTCOD       ,   DEPTNAM       ,\n";
                sql1 += " LDATE         ,   LGFIRST       ,   ISMAILING     ,   ADDR2         ,\n";
                sql1 += " ADDTXT        ,   AESID         ,   VALIDATION      \n";
                sql1 += " from \"TZ_MEMBERTEMP\" where userid = '" + v_userid.toLowerCase() + "'";

                pstmt = connMgr.prepareStatement(sql1);
                // v_CreatePreparedStatement = true;

                isOk1 = pstmt.executeUpdate();
                isOk = isOk * isOk1;
                // if(isOk1==1)
                success++;
            }

            isOk = isOk * success;

            connMgr.commit();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, null, sql);
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
                } catch (Exception e) {
                }
            }
            if (v_CreateConnManager) {
                if (connMgr != null) {
                    try {
                        connMgr.freeConnection();
                    } catch (Exception e10) {
                    }
                }
            }
        }
        return isOk;
    }

    /**
     * 회원 변경 (기존회원 : 회사관련정보 변경)
     * 
     * @param box receive from the form object and session
     * @return int 정상등록여부(1 : 정상, 0 : 오류)
     */
    public int updateOldMember(RequestBox box) throws Exception {

        boolean v_CreateConnManager = false;
        // boolean v_CreatePreparedStatement = false;

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String sql1 = "";
        int isOk = 1;
        int isOk1 = 1;
        int success = 0;
        String v_userid = "";

        // String v_comp = box.getString("comp");

        try {
            connMgr = (DBConnectionManager) box.getObject("connMgr");

            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }

            // connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);// //

            // sql = "select a.comp, a.userid, deptnam, work_plcnm, jikwi, jikupnm from TZ_MEMBER a, ";
            // sql += "( select lower(userid) from \"TZ_MEMBER\" where office_gbn='Y'";
            // sql += "  INTERSECT ";
            // sql += "  select lower(userid) from \"TZ_MEMBERTEMP\" ) b ";
            // sql += " where lower(a.userid) = lower(b.userid)";

            sql = "select \n";
            sql += "  b.comp, b.userid, b.deptnam, b.work_plcnm, \n";
            sql += "  b.jikwi, get_jikwinm(b.jikwi,b.comp) jikwinm, b.jikupnm, \n";
            sql += "  b.email, b.resno, b.pwd,  b.post1, b.post2, \n";
            sql += "  b.addr, b.addr2, b.hometel, b.handphone, b.comptel, b.office_gbn \n";
            sql += "from TZ_MEMBER a, TZ_MEMBERTEMP b \n";
            sql += "where a.userid=b.userid \n";
            // sql += "  and a.office_gbn='Y' \n";
            System.out.println("업데이트 쿼리문이다.=" + sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                // sql1 = "update \"TZ_MEMBER\" set ";
                // sql1 += "  deptnam = ?, \n";
                // sql1 += "  work_plcnm = ?, \n";
                // sql1 += "  jikwi = ?,  \n";
                // sql1 += "  jikwinm = get_jikwinm(?,?) , \n";
                // sql1 += "  jikupnm = ?, \n";
                // sql1 += "  ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
                // sql1 += "where \n";
                // sql1 += "  userid = ? ";
                //
                // pstmt = connMgr.prepareStatement(sql1);
                // v_CreatePreparedStatement = true;
                //    
                v_userid = ls.getString("userid");
                //
                // pstmt.setString(1, ls.getString("deptnam"));
                // pstmt.setString(2, ls.getString("work_plcnm"));
                // pstmt.setString(3, ls.getString("jikwi"));
                // pstmt.setString(4, ls.getString("jikwi"));
                // pstmt.setString(5, ls.getString("comp"));
                // pstmt.setString(6, ls.getString("jikupnm"));
                // pstmt.setString(7, v_userid.toLowerCase());
                //
                // isOk1 = pstmt.executeUpdate();
                // isOk = isOk * isOk1;
                // //if(isOk1==1)
                // success++;

                sql1 = "update TZ_MEMBER set \n";
                sql1 += " deptnam           = "
                        + ((ls.getString("deptnam") == null || ls.getString("deptnam") == "") ? "deptnam" : SQLString.Format(ls.getString("deptnam")))
                        + ",\n";
                sql1 += " work_plcnm    =   "
                        + ((ls.getString("work_plcnm") == null || ls.getString("work_plcnm") == "") ? "work_plcnm" : SQLString.Format(ls
                                .getString("work_plcnm"))) + ",\n";
                sql1 += " jikwi             = "
                        + ((ls.getString("jikwi") == null || ls.getString("jikwi") == "") ? "jikwi" : SQLString.Format(ls.getString("jikwi"))) + ",\n";
                sql1 += " jikupnm           = "
                        + ((ls.getString("jikupnm") == null || ls.getString("jikupnm") == "") ? "jikupnm" : SQLString.Format(ls.getString("jikupnm")))
                        + ",\n";
                sql1 += " email             = "
                        + ((ls.getString("email") == null || ls.getString("email") == "") ? "email" : SQLString.Format(ls.getString("email"))) + ",\n";
                sql1 += " resno             = "
                        + ((ls.getString("resno") == null || ls.getString("resno") == "") ? "resno" : SQLString.Format(ls.getString("resno"))) + ",\n";
                sql1 += " pwd               = "
                        + ((ls.getString("pwd") == null || ls.getString("pwd") == "") ? "pwd" : SQLString.Format(ls.getString("pwd"))) + ",\n";
                sql1 += " post1             = "
                        + ((ls.getString("post1") == null || ls.getString("post1") == "") ? "post1" : SQLString.Format(ls.getString("post1"))) + ",\n";
                sql1 += " post2             = "
                        + ((ls.getString("post2") == null || ls.getString("post2") == "") ? "post2" : SQLString.Format(ls.getString("post2"))) + ",\n";
                sql1 += " addr              = "
                        + ((ls.getString("addr") == null || ls.getString("addr") == "") ? "addr" : SQLString.Format(ls.getString("addr"))) + ",\n";
                sql1 += " addr2             = "
                        + ((ls.getString("addr2") == null || ls.getString("addr2") == "") ? "addr2" : SQLString.Format(ls.getString("addr2"))) + ",\n";
                sql1 += " hometel           = "
                        + ((ls.getString("hometel") == null || ls.getString("hometel") == "") ? "hometel" : SQLString.Format(ls.getString("hometel")))
                        + ",\n";
                sql1 += " handphone         = "
                        + ((ls.getString("handphone") == null || ls.getString("handphone") == "") ? "handphone" : SQLString.Format(ls
                                .getString("handphone"))) + ",\n";
                sql1 += " comptel           = "
                        + ((ls.getString("comptel") == null || ls.getString("comptel") == "") ? "comptel" : SQLString.Format(ls.getString("comptel")))
                        + ",\n";
                sql1 += " office_gbn        = "
                        + ((ls.getString("office_gbn") == null || ls.getString("office_gbn") == "") ? "office_gbn" : SQLString.Format(ls
                                .getString("office_gbn"))) + ",\n";
                sql1 += " ldate             = to_char(sysdate, 'YYYYMMDDHH24MISS')";
                sql1 += " where userid = " + SQLString.Format(v_userid.toLowerCase());

                System.out.println("sql1 이야....=" + sql1);
                isOk1 = connMgr.executeUpdate(sql1);
                System.out.println("isOk1 이야....=" + isOk1);
                isOk = isOk * isOk1;
                if (isOk1 == 1)
                    success++;
            }

            connMgr.commit();

            isOk = isOk * success;
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, null, sql);
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
                } catch (Exception e) {
                }
            }
            if (v_CreateConnManager) {
                if (connMgr != null) {
                    try {
                        connMgr.freeConnection();
                    } catch (Exception e10) {
                    }
                }
            }
        }
        return isOk;
    }

    /**
     * Member 존재 여부 체크
     * 
     * @param userid USER ID
     * @return int Return
     */
    public int isExitMember(String userid) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;

        String sql = "";
        int result = 0;
        // String v_userid = userid.toLowerCase();

        try {
            connMgr = new DBConnectionManager();
            sql += "select count(*) cnt  from tz_member where userid = '" + userid + "' \n";
            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();

            if (rs1.next()) {
                result = rs1.getInt("cnt");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e) {
                }
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

        return result;
    }

    /**
     * Member 존재 여부 체크
     * 
     * @param userid USER ID
     * @return int Return
     */
    public int isExitMember2(DBConnectionManager connMgr, String userid, String grcode) throws Exception {
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;

        String sql = "";
        int result = 0;
        // String v_userid = userid.toLowerCase();
        // String v_grcode = grcode;

        try {

            // sql+="select count(*) cnt  from tz_member where userid = '"+userid+"'  and grcode='"+v_grcode+"' \n";
            sql += "select count(*) cnt  from tz_member where userid = '" + userid + "' \n";
            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();

            if (rs1.next()) {
                result = rs1.getInt("cnt");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }

        }

        return result;
    }

    /**
     * Member 주민등록 번호 존재 여부 체크
     * 
     * @param userid USER ID
     * @return int Return
     */
    public int isExitMember3(DBConnectionManager connMgr, String resno) throws Exception {
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;

        String sql = "";
        int result = 0;

        try {

            sql += "select count(*) cnt  from tz_member where resno = '" + resno + "' \n";
            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();

            if (rs1.next()) {
                result = rs1.getInt("cnt");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }

        }

        return result;
    }

    /**
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> updateMemberPassword(RequestBox box) throws Exception {
        DBConnectionManager connMgr = new DBConnectionManager();
        PreparedStatement pstmt = null;
        MemberAdminBean bean = new MemberAdminBean();
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        DataBox dbox = new DataBox("updateMemberPassword");

        Sheet sheet = null;
        Workbook workbook = null;
        ConfigSet conf = new ConfigSet();

        String newFileName = box.getNewFileName("p_file");
        // String realFileName = box.getRealFileName("p_file");

        String grCode = "";
        String userId = "";
        String password = "";

        int memberExistYN = 0;
        int updateResult = 0;
        int okCnt = 0, failCnt = 0, notUserCnt = 0;
        int k = 0;

        StringBuilder sb = new StringBuilder();

        try {

            connMgr.setAutoCommit(false);

            sb.append("UPDATE   TZ_MEMBER   \n");
            sb.append("   SET   PWD = ?     \n");
            sb.append(" WHERE   GRCODE = ?  \n");
            sb.append("   AND   USERID = ?  \n");

            pstmt = connMgr.prepareStatement(sb.toString());

            workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.home") + newFileName));
            sheet = workbook.getSheet(0);

            grCode = box.getString("p_grcode");

            for (int i = 1; i < sheet.getRows(); i++) {
                userId = sheet.getCell(0, i).getContents();
                password = sheet.getCell(1, i).getContents();

                if (!userId.equals("")) {
                    // if ("N000001".equals(grCode)) {
                    // password = HashCipher.createHash(password);
                    // } else {
                    // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);
                    // password = encryptUtil.encrypt(password);
                    // }

                    password = HashCipher.createHash(password);

                    memberExistYN = bean.isExitMember2(connMgr, userId, grCode);
                    dbox = new DataBox("updateMemberPassword");
                    dbox.put("userId", userId);

                    if (memberExistYN != 0) {
                        k = 0;
                        pstmt.setString(++k, password);
                        pstmt.setString(++k, grCode);
                        pstmt.setString(++k, userId);

                        updateResult = pstmt.executeUpdate();

                        if (updateResult > 0) {
                            okCnt++;
                            dbox.put("msg", "Y");
                        } else {
                            failCnt++;
                            dbox.put("msg", "F");
                        }

                    } else {
                        notUserCnt++;
                        dbox.put("msg", "N");
                    }

                    list.add(i - 1, dbox);

                }
            }

            if (okCnt == sheet.getRows() - 1) {
                connMgr.commit();
                // connMgr.rollback();
            } else {
                connMgr.rollback();
            }
            dbox = new DataBox("updateMemberPassword");
            dbox.put("okCnt", okCnt);
            dbox.put("failCnt", failCnt);
            dbox.put("notUserCnt", notUserCnt);
            list.add(list.size(), dbox);

            for (int i = 0; i < list.size(); i++) {
                dbox = list.get(i);
            }

        } catch (Exception ex) {
            Log.err.println("exception : " + ex.getMessage());
            ErrorManager.getErrorStackTrace(ex, null, sb.toString());
            throw new Exception("query = " + sb.toString() + "\r\n" + ex.getMessage());

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (connMgr != null) {
                connMgr.freeConnection();
            }
            FileManager.deleteFile(newFileName);
        }

        return list;
    }

    /**
     * 교육그룹별 아이디 접두어 검색
     * 
     * @param box receive from the form object and session
     * @return result USER NAME
     */
    public String getidPrefix(String grcode) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        String result = "";

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT  GRCODE  \n");
            sql.append("    ,   GRCODENM    \n");
            sql.append("    ,   GR_PREFIX   \n");
            sql.append("  FROM  TZ_GRCODE   \n");
            sql.append(" WHERE  GRCODE = '").append(grcode).append("'   \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                result = ls.getString("gr_prefix");
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
        System.out.println(result);
        return result;
    }
}