<%@ page contentType="text/html;charset=euc-kr" %>

<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<%@ page import="com.credu.library.HashCipher" %>
<%@ page import="com.credu.library.ErrorManager" %>
<%@ page import="com.credu.library.DBConnectionManager" %>
<%@ page import="com.credu.library.ListSet" %>
<%@ page import="java.sql.PreparedStatement" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    DBConnectionManager connMgr = null;
    ListSet ls = null;
    PreparedStatement pstmt = null;
    StringBuffer sql = new StringBuffer();
    int isOk = 0;

    String v_grcode = box.getSession("tem_grcode");
    v_grcode = ( v_grcode == null || v_grcode.equals("")) ? "N000001" : v_grcode;
    String v_userid = box.getString("p_userid");
    String v_pwd = box.getString("p_pwd");
    String v_name = box.getStringDefault("p_kor_name", box.getSession("name"));
    String v_eng_name = box.getString("p_eng_name");
    String v_email = box.getString("p_email");
    String v_home_addr1 = box.getString("p_home_addr1");
    String v_hometel = box.getString("p_hometel");
    String v_handphone = box.getString("p_handphone");
    String v_ismailing = box.getStringDefault("p_ismailing", "N");
    String v_islettering = "N";
    String v_isopening = "N";
    String v_membergubun = "P";
    String v_registgubun = box.getStringDefault("p_registgubun", box.getSession("grtype"));
    String v_state = "Y";
    String v_validation = box.getStringDefault("p_validation", "1"); // 실명인증 관련  홈페이지 가입은 == 1
    String v_issms = v_ismailing;
    String v_workfieldcd = box.getString("p_workfieldcd");
    String v_workfieldnm = box.getString("p_workfieldnm");
    String v_dupinfo = box.getString("p_dupinfo");
    String vv_username = box.getStringDefault("pp_username", "");
    String vv_email = box.getStringDefault("pp_email", "");
    String v_gamelicyn = box.getStringDefault("p_gamelicyn", "N");
    String v_sex = box.getStringDefault("p_sex","");
    String v_degree = box.getStringDefault("p_degree", "");
    String v_jikup = box.getString("p_jikup");
    String v_memberyear = box.getString("p_memberyear");
    String v_membermonth = box.getString("p_membermonth");
    String v_memberday = box.getString("p_memberday");
    String v_registerMotive = box.getString("p_registerMotive");
    String v_registerRoute = box.getString("p_registerRoute");
    
    String certiType = box.getString("certiType");

    try {
        v_pwd = HashCipher.createHash(v_pwd);

        connMgr = new DBConnectionManager();
        connMgr.setAutoCommit(false);

        sql.append("/* com.credu.homepage.MainMemberJoinBean insertMember(회원정보등록) */  \n");
        sql.append("INSERT  INTO TZ_MEMBER( \n");
        sql.append("        GRCODE          \n");
        sql.append("    ,   USERID          \n");
        sql.append("    ,   PWD             \n");
        sql.append("    ,   NAME            \n");
        sql.append("    ,   ENG_NAME        \n");
        sql.append("    ,   EMAIL           \n");
        sql.append("    ,   ADDR            \n");
        sql.append("    ,   HOMETEL         \n");
        sql.append("    ,   HANDPHONE       \n");
        sql.append("    ,   ISMAILING       \n");
        sql.append("    ,   ISLETTERING     \n");
        sql.append("    ,   ISOPENING       \n");
        sql.append("    ,   MEMBERGUBUN     \n");
        sql.append("    ,   REGISTGUBUN     \n");
        sql.append("    ,   STATE           \n");
        sql.append("    ,   VALIDATION      \n");
        sql.append("    ,   INDATE          \n");
        sql.append("    ,   LDATE           \n");
        sql.append("    ,   ISSMS           \n");
        sql.append("    ,   WORKFIELDCD     \n");
        sql.append("    ,   WORKFIELDNM     \n");
        sql.append("    ,   SEX             \n");
        sql.append("    ,   DEGREE          \n");
        sql.append("    ,   JIKUP           \n");
        sql.append("    ,   MEMBERYEAR      \n");
        sql.append("    ,   MEMBERMONTH     \n");
        sql.append("    ,   MEMBERDAY       \n");
        sql.append("    ,   REGISTERMOTIVE  \n");
        sql.append("    ,   REGISTERROUTE   \n");
        sql.append("    ,   DUPINFO         \n");
        sql.append("    ,   PARENTNAME      \n");
        sql.append("    ,   PARENTEMAIL     \n");
        sql.append("    ,   GAMELICYN       \n");
        sql.append("    ,   CERTI_TYPE      \n");
        sql.append(") VALUES (                                                              \n");
        sql.append("        ?                                       /* GRCODE           */  \n");
        sql.append("    ,   ?                                       /* USERID           */  \n");
        sql.append("    ,   ?                                       /* PWD              */  \n");
        sql.append("    ,   ?                                       /* NAME             */  \n");
        sql.append("    ,   ?                                       /* ENG_NAME         */  \n");
        sql.append("    ,   CRYPTO.ENC('normal', ?)                 /* EMAIL            */  \n");
        sql.append("    ,   ?                                       /* ADDR             */  \n");
        sql.append("    ,   CRYPTO.ENC('normal',?)                  /* HOMETEL          */  \n");
        sql.append("    ,   CRYPTO.ENC('normal',?)                  /* HANDPHONE        */  \n");
        sql.append("    ,   ?                                       /* ISMAILING        */  \n");
        sql.append("    ,   ?                                       /* ISLETTERING      */  \n");
        sql.append("    ,   ?                                       /* ISOPENING        */  \n");
        sql.append("    ,   ?                                       /* MEMBERGUBUN      */  \n");
        sql.append("    ,   ?                                       /* REGISTGUBUN      */  \n");
        sql.append("    ,   ?                                       /* STATE            */  \n");
        sql.append("    ,   ?                                       /* VALIDATION       */  \n");
        sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    /* INDATE           */  \n");
        sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    /* LDATE            */  \n");
        sql.append("    ,   ?                                       /* ISSMS            */  \n");
        sql.append("    ,   ?                                       /* WORKFIELDCD      */  \n");
        sql.append("    ,   ?                                       /* WORKFIELDNM      */  \n");
        sql.append("    ,   ?                                       /* SEX              */  \n");
        sql.append("    ,   ?                                       /* DEGREE           */  \n");
        sql.append("    ,   ?                                       /* JIKUP            */  \n");
        sql.append("    ,   ?                                       /* MEMBERYEAR       */  \n");
        sql.append("    ,   ?                                       /* MEMBERMONTH      */  \n");
        sql.append("    ,   ?                                       /* MEMBERDAY        */  \n");
        sql.append("    ,   ?                                       /* REGISTERMOTIVE   */  \n");
        sql.append("    ,   ?                                       /* REGISTERROUTE    */  \n");
        sql.append("    ,   ?                                       /* DUPINFO          */  \n");
        sql.append("    ,   ?                                       /* PARENTNAME       */  \n");
        sql.append("    ,   ?                                       /* PARENTEMAIL      */  \n");
        sql.append("    ,   ?                                       /* GAMELICYN        */  \n");
        sql.append("    ,   ?                                       /* CERTI_TYPE       */  \n");
        sql.append(")                                                                       \n");


        pstmt = connMgr.prepareStatement(sql.toString());

        int i = 1;

        pstmt.setString(i++, v_grcode);
        pstmt.setString(i++, v_userid);
        pstmt.setString(i++, v_pwd);
        pstmt.setString(i++, v_name);
        pstmt.setString(i++, v_eng_name);
        pstmt.setString(i++, v_email);
        pstmt.setString(i++, v_home_addr1);
        pstmt.setString(i++, v_hometel);
        pstmt.setString(i++, v_handphone);
        pstmt.setString(i++, v_ismailing);
        pstmt.setString(i++, v_islettering);
        pstmt.setString(i++, v_isopening);
        pstmt.setString(i++, v_membergubun);
        pstmt.setString(i++, v_registgubun);
        pstmt.setString(i++, v_state);
        pstmt.setString(i++, v_validation);
        pstmt.setString(i++, v_issms);

        pstmt.setString(i++, v_workfieldcd);
        pstmt.setString(i++, v_workfieldnm);
        pstmt.setString(i++, v_sex);
        pstmt.setString(i++, v_degree);

        pstmt.setString(i++, v_jikup);
        pstmt.setString(i++, v_memberyear);
        pstmt.setString(i++, v_membermonth);
        pstmt.setString(i++, v_memberday);

        pstmt.setString(i++, v_registerMotive);
        pstmt.setString(i++, v_registerRoute);
        pstmt.setString(i++, v_dupinfo);

        pstmt.setString(i++, vv_username);
        pstmt.setString(i++, vv_email);
        pstmt.setString(i++, v_gamelicyn);
        pstmt.setString(i++, certiType);

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
        ErrorManager.getErrorStackTrace(ex, box, sql.toString());
        throw new Exception("sql ->" + sql.toString() + "\r\n" + ex.getMessage());
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

    response.sendRedirect("/learn/user/2013/portal/member/zu_MemberJoinOK.jsp");
%>
