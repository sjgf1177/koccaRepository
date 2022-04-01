//**********************************************************
//1. 제	  목: SUBJECT INFORMATION USER BEAN
//2. 프로그램명: ProposeCourseBean.java
//3. 개	  요: 과정안내 사용자 bean
//4. 환	  경: JDK 1.3
//5. 버	  젼: 1.0
//6. 작	  성: 2004.01.14
//7. 수	  정:
//**********************************************************
package com.credu.off;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Random;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.dunet.common.util.UploadUtil;

@SuppressWarnings("unchecked")
public class ProposeOffBean {

    public ProposeOffBean() {
    }

    /**
     * 수강신청 1. 수강신청이 가능한 상태여야 한다. 2. 블랙리스트에 등록되어 있지 아니하여야 한다. 3. 수강신청 취소 후 재
     * 신청시.
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int insertSubjectEduPropose(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt3 = null;

        StringBuffer sb = new StringBuffer();
        String sql3 = null;

        int isOk = 0;

        int v_jeyak = 0; // 제약조건 결과값

        String v_jeyak_msg = "";

        box.sync("p_comptext");

        try {
            box.put("s_userid", box.getSession("userid"));
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql3 = null;
            /**
             * 1. 수강신청이 가능한가 체크. [1. 신청인원/모집인원, 2. 이미 신청하였는가, 3. 이미 수료한 것인가.]
             */
            v_jeyak = jeyakCheck(connMgr, box);

            if (v_jeyak < 0) {
                v_jeyak_msg = jeyakMsg(v_jeyak);
                box.put("err_msg", v_jeyak_msg);
                return v_jeyak;
            } else {
                sb.delete(0, sb.length());
                //				sb.append("INSERT INTO TZ_OFFPROPOSE (\n");
                //				sb.append("	SUBJ,YEAR,SUBJSEQ,SEQ,\n");
                //				sb.append("	/*	과정코드, 연도, 차수, 일련번호	*/\n");
                //				sb.append("	USERID,COMP,JIK,\n");
                //				sb.append("	/*	아이디, 회사코드, 직급코드	*/\n");
                //				sb.append("	APPDATE,ISDINSERT,CHKFIRST,CHKFINAL,PRICE,REALPAYMENT,\n");
                //				sb.append("	/*	신청일, 강제입력여부, 1차승인여부, 신청금액, 실결제금액	*/\n");
                //				sb.append("	LUSERID,LDATE, COMPTEXT \n");
                //				sb.append("	/*	최종수정자, 최종수정일, 직장(학교)명	*/\n");
                //				sb.append(") SELECT\n");
                //				sb.append("	':p_subj' SUBJ, ':p_year' YEAR,':p_subjseq' SUBJSEQ,':p_seq' SEQ,\n");
                //				sb.append("	/*	과정코드, 연도, 차수, 일련번호	*/\n");
                //				sb.append("	M.USERID USERID,M.COMP COMP, M.JIKUP JIK,\n");
                //				sb.append("	/*	아이디, 회사코드, 직급코드	*/\n");
                //				sb.append("	TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') APPDATE, 'N' ISDINSERT, 'U' CHKFIRST, 'U' CHKFINAL,':p_price' PRICE, ':p_realpayment' REALPAYMENT,\n");
                //				sb.append("	/*	신청일, 강제입력여부, 1차승인여부, 신청금액, 실결제금액	*/\n");
                //				sb.append("	USERID LUSERID, TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') LDATE, nvl(':p_comptext', COMPTEXT) COMPTEXT \n");
                //				sb.append("	/*	최종수정자, 최종수정일, 직장(학교)명	*/\n");
                //				sb.append("FROM TZ_MEMBER M\n");
                //				sb.append("WHERE USERID = '"+ box.getSession("userid")+"' AND GRCODE = '" + box.getSession("tem_grcode") + "' \n");

                sb.append("/* ProposeOffBean insertSubjectEduPropose (오프라인과정 수강신청 저장) */\n");
                sb.append("INSERT  INTO    TZ_OFFPROPOSE (                              \n");
                sb.append("        SUBJ        /*   과정코드 */                         \n");
                sb.append("    ,   YEAR        /*   연도 */                             \n");
                sb.append("    ,   SUBJSEQ     /*   차수 */                             \n");
                sb.append("    ,   SEQ         /*   일련번호 */                         \n");
                sb.append("    ,   USERID      /*   아이디 */                           \n");
                sb.append("    ,   COMP        /*   회사코드 */                         \n");
                sb.append("    ,   JIK         /*   직급코드 */                         \n");
                sb.append("    ,   APPDATE     /*   신청일 */                           \n");
                sb.append("    ,   ISDINSERT   /*  강제입력여부 */                      \n");
                sb.append("    ,   CHKFIRST    /*  1차승인여부 */                       \n");
                sb.append("    ,   CHKFINAL    /*  최종승인여부 */                      \n");
                sb.append("    ,   PRICE       /*  신청금액 */                          \n");
                sb.append("    ,   REALPAYMENT /*  실결제금액   */                      \n");
                sb.append("    ,   LUSERID     /*  최종수정자 */                        \n");
                sb.append("    ,   LDATE       /*  최종수정일 */                        \n");
                sb.append("    ,   COMPTEXT    /*  직장(학교)명 */                      \n");
                sb.append("    ,   APPLY_NAME          /*  참가정보 이름 */             \n");
                sb.append("    ,   APPLY_SESSION       /*  참가정보 세션 */             \n");
                sb.append("    ,   APPLY_BELONG_TITLE  /*  참가정보 소속 및 직함 */     \n");
                sb.append("    ,   NICKNAME  /* 개인 온라인 채널 닉네임 */              \n");
                sb.append("    ,   PRIVATE_SNS  /* 개인 온라인 채널 SNS */              \n");
                sb.append("    )                                                        \n");
                sb.append("    SELECT                                                   \n");
                sb.append("            ':p_subj' SUBJ                                   \n");
                sb.append("        ,   ':p_year' YEAR                                   \n");
                sb.append("        ,   ':p_subjseq' SUBJSEQ                             \n");
                sb.append("        ,   ':p_seq' SEQ                                     \n");
                sb.append("        ,   M.USERID USERID                                  \n");
                sb.append("        ,   M.COMP COMP                                      \n");
                sb.append("        ,   M.JIKUP JIK                                      \n");
                sb.append("        ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') APPDATE     \n");
                sb.append("        ,   'N' ISDINSERT                                    \n");
                sb.append("        ,   'U' CHKFIRST                                     \n");
                sb.append("        ,   'U' CHKFINAL                                     \n");
                sb.append("        ,   ':p_price' PRICE                                 \n");
                sb.append("        ,   ':p_realpayment' REALPAYMENT                     \n");
                sb.append("        ,   USERID LUSERID                                   \n");
                sb.append("        ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') LDATE       \n");
                sb.append("        ,   nvl(':p_comptext', COMPTEXT) COMPTEXT            \n");
                sb.append("        ,   ':p_apply_name' APPLY_NAME                       \n");
                sb.append("        ,   ':p_apply_session' APPLY_SESSION                 \n");
                sb.append("        ,   ':p_apply_belong_title' APPLY_BELONG_TITLE       \n");
                sb.append("        ,   ':p_nickname' NICKNAME                           \n");
                sb.append("        ,   ':p_private_sns' PRIVATE_SNS                     \n");
                sb.append("      FROM  TZ_MEMBER M                                      \n");
                sb.append("     WHERE  USERID = '").append(box.getSession("userid")).append("' \n");
                sb.append("       AND   GRCODE = '").append(box.getSession("tem_grcode")).append("' \n");

                sql3 = connMgr.replaceParam(sb, box);
                pstmt3 = connMgr.prepareStatement(sql3);
                isOk = pstmt3.executeUpdate();

                String currYear = FormatDate.getDate("yyyy") + "\\";
                
                // 프로필 사진과 첨부파일 모두 등록 할 경우
                if (!box.getString("p_picfile_new").equals("") && !box.getString("p_attfile_new").equals("")) {
                    String[] fileUpload = box.getString("p_picfile_new").replace("[p_picfile|", "").replace("]", "").replace(" p_attfile|", "").split(",");
                    String[] org_name = box.getString("p_picfile_real").replace("[p_picfile|", "").replace("]", "").replace(" p_attfile|", "").split(",");

                    ConfigSet conf = new ConfigSet();
                    String sServletName = box.getString(fileUpload[0] + "_servletnm");
                    String sDirKey = conf.getDir(conf.getProperty("dir.upload"), sServletName);
                    String sRelativePath = conf.getProperty("dir.upload." + sDirKey);
                    String sUploadTempPath = conf.getProperty("dir.home") + sRelativePath;
                    String tar = sUploadTempPath.replace("bulletin", "private_pic") + fileUpload[0]; //2015년도 폴더를 생성하여 임시 파일을 저장했음.. 기존에 수정 했는데 ㅇ
                    boolean result = UploadUtil.fileCopy(sUploadTempPath + currYear+ fileUpload[0], tar);

                    if (result)
                        result = UploadUtil.fileDelete(sUploadTempPath + currYear + fileUpload[0]);

                    String sql = "update tz_member set img_path='\\upload\\private_pic\\" + fileUpload[0] + "' where userid='" + box.getSession("userid") + "' and grcode='" + box.getSession("tem_grcode") + "'";
                    pstmt3 = connMgr.prepareStatement(sql);
                    isOk = pstmt3.executeUpdate();

                    sServletName = box.getString(org_name[1] + "_servletnm");
                    sDirKey = conf.getDir(conf.getProperty("dir.upload"), sServletName);
                    sRelativePath = conf.getProperty("dir.upload." + sDirKey);
                    sUploadTempPath = conf.getProperty("dir.home") + sRelativePath;

                    tar = sUploadTempPath.replace("bulletin", "off_attfile") + fileUpload[1];
                    result = UploadUtil.fileCopy(sUploadTempPath + fileUpload[1], tar);

                    if (result)
                        result = UploadUtil.fileDelete(sUploadTempPath + currYear+ fileUpload[1]);

                    sql = " INSERT INTO TZ_OFFPROPOSEFILE \n";
                    sql += "(SUBJ, YEAR, SUBJSEQ, SEQ, USERID, REALFILENM, SAVEFILENM, LDATE) \n";
                    sql += " VALUES \n";
                    sql += "('" + box.getString("p_subj") + "', \n";
                    sql += "'" + box.getString("p_year") + "', \n";
                    sql += "'" + box.getString("p_subjseq") + "', \n";
                    sql += box.getString("p_seq") + ", \n";
                    sql += "'" + box.getSession("userid") + "', \n";
                    sql += "'\\upload\\off_attfile\\" + fileUpload[1] + "', \n";
                    sql += "'" + org_name[1] + "', \n";
                    sql += "TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'))";
                    pstmt3 = connMgr.prepareStatement(sql);
                    isOk = pstmt3.executeUpdate();
                }
                // 프로필 사진만 등록 할 경우
                else if (!box.getString("p_picfile_new").equals("") && box.getString("p_attfile_new").equals("")) {
                    String fileUpload = box.getString("p_picfile_new").replace("[p_picfile|", "").replace("]", "");

                    ConfigSet conf = new ConfigSet();
                    String sServletName = box.getString(fileUpload + "_servletnm");
                    String sDirKey = conf.getDir(conf.getProperty("dir.upload"), sServletName);
                    String sRelativePath = conf.getProperty("dir.upload." + sDirKey);
                    String sUploadTempPath = conf.getProperty("dir.home") + sRelativePath;

                    String tar = sUploadTempPath.replace("bulletin", "private_pic") + fileUpload;
                    boolean result = UploadUtil.fileCopy(sUploadTempPath + currYear + fileUpload, tar);

                    if (result)
                        result = UploadUtil.fileDelete(sUploadTempPath + currYear + fileUpload);

                    String sql = "update tz_member set img_path='\\upload\\private_pic\\" + fileUpload + "' where userid='" + box.getSession("userid") + "' and grcode='" + box.getSession("tem_grcode") + "'";
                    pstmt3 = connMgr.prepareStatement(sql);
                    isOk = pstmt3.executeUpdate();
                }
                // 첨부 파일만 등록 할 경우
                else if (box.getString("p_picfile_new").equals("") && !box.getString("p_attfile_new").equals("")) {
                    String fileUpload = box.getString("p_attfile_new").replace("[p_attfile|", "").replace("]", "");
                    String org_name = box.getString("p_attfile_new").replace("[p_attfile|", "").replace("]", "");

                    ConfigSet conf = new ConfigSet();
                    String sServletName = box.getString(org_name + "_servletnm");
                    String sDirKey = conf.getDir(conf.getProperty("dir.upload"), sServletName);
                    String sRelativePath = conf.getProperty("dir.upload." + sDirKey);
                    String sUploadTempPath = conf.getProperty("dir.home") + sRelativePath;

                    String tar = sUploadTempPath.replace("bulletin", "off_attfile") + org_name;
                    boolean result = UploadUtil.fileCopy(sUploadTempPath + fileUpload, tar);

                    if (result)
                        result = UploadUtil.fileDelete(sUploadTempPath + fileUpload);

                    String sql = "";
                    sql = " INSERT INTO TZ_OFFPROPOSEFILE \n";
                    sql += "(SUBJ, YEAR, SUBJSEQ, SEQ, USERID, REALFILENM, SAVEFILENM, LDATE) \n";
                    sql += " VALUES \n";
                    sql += "('" + box.getString("p_subj") + "', \n";
                    sql += "'" + box.getString("p_year") + "', \n";
                    sql += "'" + box.getString("p_subjseq") + "', \n";
                    sql += box.getString("p_seq") + ", \n";
                    sql += "'" + box.getSession("userid") + "', \n";
                    sql += "'\\upload\\off_attfile\\" + fileUpload + "', \n";
                    sql += "'" + org_name + "', \n";
                    sql += "TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'))";

                    pstmt3 = connMgr.prepareStatement(sql);
                    isOk = pstmt3.executeUpdate();
                }

                if (isOk > 0) {
                    if (connMgr != null) {
                        try {
                            connMgr.commit();
                        } catch (Exception e10) {
                        }
                    }
                } else {
                    if (connMgr != null) {
                        try {
                            connMgr.rollback();
                        } catch (Exception e10) {
                        }
                    }
                }
            }
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql3);
            throw new Exception("sql1 = " + sql3 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
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

    public int updateSubjectEduPropose(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt3 = null;

        StringBuffer sb = new StringBuffer();
        String sql3 = null;

        int isOk = 0;
        
        String applyName = box.getString("p_apply_name");
        String nickname = box.getString("p_nickname");
        String privateSns = box.getString("p_private_sns");

        box.sync("p_comptext");

        try {
            box.put("s_userid", box.getSession("userid"));
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            
            if ( (applyName != null && !applyName.equals("") ) || (nickname != null && !nickname.equals("")) ) {
                sb.append("UPDATE   TZ_OFFPROPOSE   \n");
                sb.append("   SET   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') \n");
                if ( applyName != null && !applyName.equals("") ) {
                    sb.append("     ,   APPLY_NAME = '").append(box.getString("p_apply_name")).append("'\n");
                    sb.append("     ,   APPLY_SESSION = '").append(box.getString("p_apply_session")).append("'\n");
                    sb.append("     ,   APPLY_BELONG_TITLE = '").append(box.getString("p_apply_belong_title")).append("'\n");
                }
                if ( nickname != null && !nickname.equals("") ) {
                    sb.append("     ,   NICKNAME = '").append(nickname).append("'\n");
                    sb.append("     ,   PRIVATE_SNS = '").append(privateSns).append("'\n");
                }
                sb.append(" WHERE   SUBJ='").append(box.getString("p_subj")).append("'\n");
                sb.append("   AND   YEAR='").append(box.getString("p_year")).append("'\n");
                sb.append("   AND   SUBJSEQ='").append(box.getString("p_subjseq")).append("'\n");
                sb.append("   AND   SEQ='").append(box.getStringDefault("p_seq", "1")).append("'\n");
                sb.append("   AND   USERID='").append(box.getSession("userid")).append("'\n");
                
                pstmt3 = connMgr.prepareStatement(sb.toString());
                isOk = pstmt3.executeUpdate();
                
                pstmt3.close();
                pstmt3 = null;
            }

            sql3 = null;

            /*
             * if(!box.getString("p_picfile_new").equals("")) { SimpleDateFormat
             * format = new SimpleDateFormat("yyyyMMddHHmmddss"); Date today1 =
             * new Date(); String today=format.format(today1);
             * 
             * String
             * fileUpload=box.getString("p_picfile_new").replace("[p_picfile|"
             * ,"").replace("]",""); String
             * org_name=today+box.getString("p_picfile_real"
             * ).replace("[p_picfile|","").replace("]","");
             * 
             * ConfigSet conf = new ConfigSet(); String sServletName =
             * box.getString(org_name+"_servletnm"); String sDirKey =
             * conf.getDir(conf.getProperty("dir.upload"), sServletName); String
             * sRelativePath = conf.getProperty("dir.upload." + sDirKey); String
             * sUploadTempPath = conf.getProperty("dir.home") + sRelativePath;
             * 
             * String tar=
             * sUploadTempPath.replace("bulletin","private_pic")+org_name;
             * boolean result =
             * UploadUtil.fileCopy(sUploadTempPath+fileUpload,tar);
             * 
             * if (result) result =
             * UploadUtil.fileDelete(sUploadTempPath+fileUpload);
             * 
             * String
             * sql="update tz_member set img_path='\\upload\\private_pic\\"
             * +org_name
             * +"' where userid='"+box.getSession("userid")+"' and grcode='"
             * +box.getSession("tem_grcode")+"'"; pstmt3 =
             * connMgr.prepareStatement(sql); isOk = pstmt3.executeUpdate(); }
             */
            // 프로필 사진과 첨부파일 모두 등록 할 경우
            if (!box.getString("p_picfile_new").equals("") && !box.getString("p_attfile_new").equals("")) {
                String[] fileUpload = box.getString("p_picfile_new").replace("[p_picfile|", "").replace("]", "").replace(" p_attfile|", "").split(",");
                String[] org_name = box.getString("p_picfile_real").replace("[p_picfile|", "").replace("]", "").replace(" p_attfile|", "").split(",");

                ConfigSet conf = new ConfigSet();
                String sServletName = box.getString(fileUpload[0] + "_servletnm");
                String sDirKey = conf.getDir(conf.getProperty("dir.upload"), sServletName);
                String sRelativePath = conf.getProperty("dir.upload." + sDirKey);
                String sUploadTempPath = conf.getProperty("dir.home") + sRelativePath;
                String tar = sUploadTempPath.replace("bulletin", "private_pic") + fileUpload[0];
                boolean result = UploadUtil.fileCopy(sUploadTempPath + fileUpload[0], tar);

                if (result)
                    result = UploadUtil.fileDelete(sUploadTempPath + fileUpload[0]);

                String sql = "update tz_member set img_path='\\upload\\private_pic\\" + fileUpload[0] + "' where userid='" + box.getSession("userid") + "' and grcode='" + box.getSession("tem_grcode") + "'";
                pstmt3 = connMgr.prepareStatement(sql);
                isOk = pstmt3.executeUpdate();

                sServletName = box.getString(org_name[1] + "_servletnm");
                sDirKey = conf.getDir(conf.getProperty("dir.upload"), sServletName);
                sRelativePath = conf.getProperty("dir.upload." + sDirKey);
                sUploadTempPath = conf.getProperty("dir.home") + sRelativePath;

                tar = sUploadTempPath.replace("bulletin", "off_attfile") + fileUpload[1];
                result = UploadUtil.fileCopy(sUploadTempPath + fileUpload[1], tar);

                if (result)
                    result = UploadUtil.fileDelete(sUploadTempPath + fileUpload[1]);

                sql = "UPDATE TZ_OFFPROPOSEFILE \n";
                sql += "   SET REALFILENM = '\\upload\\off_attfile\\" + fileUpload[1] + "', \n";
                sql += "	   SAVEFILENM = '" + org_name[1] + "', \n";
                sql += "	   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') \n";
                sql += " WHERE SUBJ='" + box.getString("p_subj") + "'\n";
                sql += " 	AND YEAR='" + box.getString("p_year") + "'\n";
                sql += " 	AND SUBJSEQ='" + box.getString("p_subjseq") + "'\n";
                sql += " 	AND SEQ='" + box.getStringDefault("p_seq", "1") + "'\n";
                sql += " 	AND USERID='" + box.getSession("userid") + "'\n";
                pstmt3 = connMgr.prepareStatement(sql);
                isOk = pstmt3.executeUpdate();
            }
            // 프로필 사진만 등록 할 경우
            else if (!box.getString("p_picfile_new").equals("") && box.getString("p_attfile_new").equals("")) {
                String fileUpload = box.getString("p_picfile_new").replace("[p_picfile|", "").replace("]", "");

                ConfigSet conf = new ConfigSet();
                String sServletName = box.getString(fileUpload + "_servletnm");
                String sDirKey = conf.getDir(conf.getProperty("dir.upload"), sServletName);
                String sRelativePath = conf.getProperty("dir.upload." + sDirKey);
                String sUploadTempPath = conf.getProperty("dir.home") + sRelativePath;

                String tar = sUploadTempPath.replace("bulletin", "private_pic") + fileUpload;
                boolean result = UploadUtil.fileCopy(sUploadTempPath + fileUpload, tar);

                if (result)
                    result = UploadUtil.fileDelete(sUploadTempPath + fileUpload);

                String sql = "update tz_member set img_path='\\upload\\private_pic\\" + fileUpload + "' where userid='" + box.getSession("userid") + "' and grcode='" + box.getSession("tem_grcode") + "'";
                pstmt3 = connMgr.prepareStatement(sql);
                isOk = pstmt3.executeUpdate();
            }
            // 첨부 파일만 등록 할 경우
            else if (box.getString("p_picfile_new").equals("") && !box.getString("p_attfile_new").equals("")) {
                String fileUpload = box.getString("p_attfile_new").replace("[p_attfile|", "").replace("]", "");
                String org_name = box.getString("p_attfile_real").replace("[p_attfile|", "").replace("]", "");

                ConfigSet conf = new ConfigSet();
                String sServletName = box.getString(org_name + "_servletnm");
                String sDirKey = conf.getDir(conf.getProperty("dir.upload"), sServletName);
                String sRelativePath = conf.getProperty("dir.upload." + sDirKey);
                String sUploadTempPath = conf.getProperty("dir.home") + sRelativePath;

                String tar = sUploadTempPath.replace("bulletin", "off_attfile") + fileUpload;
                boolean result = UploadUtil.fileCopy(sUploadTempPath + fileUpload, tar);

                if (result)
                    result = UploadUtil.fileDelete(sUploadTempPath + fileUpload);

                String sql = "";
                sql = "UPDATE TZ_OFFPROPOSEFILE \n";
                sql += "   SET REALFILENM = '\\upload\\off_attfile\\" + fileUpload + "', \n";
                sql += "	   SAVEFILENM = '" + org_name + "', \n";
                sql += "	   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') \n";
                sql += " WHERE SUBJ='" + box.getString("p_subj") + "'\n";
                sql += " 	AND YEAR='" + box.getString("p_year") + "'\n";
                sql += " 	AND SUBJSEQ='" + box.getString("p_subjseq") + "'\n";
                sql += " 	AND SEQ='" + box.getStringDefault("p_seq", "1") + "'\n";
                sql += " 	AND USERID='" + box.getSession("userid") + "'\n";
                pstmt3 = connMgr.prepareStatement(sql);
                isOk = pstmt3.executeUpdate();
            }
            if (isOk >= 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
            }

        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql3);
            throw new Exception("sql1 = " + sql3 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
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
     * 제약조건 체크
     * 
     * @param connMgr DBConnectionManager
     * @return result 제약조건결과 코드
     */
    public int jeyakCheck(DBConnectionManager connMgr, RequestBox box) throws Exception {
        ListSet ls = null;
        int result = 0;
        try {
            // 정원초과  - 1
            result = jeyakStudentlimit(connMgr, box);
            if (result < 0) {
                return result;
            }

            //이미신청한과정  - 2
            result = jeyakPropose(connMgr, box);
            if (result < 0) {
                return result;
            }

            //수료한과정  - 3
            result = jeyakStold(connMgr, box);
            if (result < 0) {
                return result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }

        return result;
    }

    /**
     * 제약메세지
     * 
     * @param p_jeyak 제약코드
     * @return result 메세지
     */
    public String jeyakMsg(int p_jeyak) {
        String msg = "";

        if (p_jeyak == -1) {
            msg = "정원초과입니다"; // 정원초과  - 1
        } else if (p_jeyak == -2) {
            msg = "이미신청한과정입니다"; // 이미신청한과정  - 2
        } else if (p_jeyak == -3) {
            msg = "수료한과정입니다"; // 수료한과정  - 3
        } else if (p_jeyak == -6) {
            msg = "이미 수강신청하신 과정이 있습니다."; //msg ="수강신청 제약입니다";				// 회사별 수강신청 제약설정
        }

        return msg;
    }

    /**
     * 이미신청한과정
     * 
     * @param connMgr DBConnectionManager
     */
    public int jeyakPropose(DBConnectionManager connMgr, RequestBox box) throws Exception {
        ListSet ls = null;
        String sql = "";
        int result = 0;

        try {
            sql = " select count(*) cnt \n";
            sql += " from TZ_OFFPROPOSE	A \n";
            sql += " where a.subj	= ':p_subj' \n";
            sql += "	and a.year	= ':p_year' \n";
            sql += "	and a.subjseq = ':p_subjseq' \n";
            sql += "	and a.seq = ':p_seq' \n";
            //			sql+= " and chkfinal != 'N' \n";
            sql += " and a.userid = ':s_userid' ";

            sql = connMgr.replaceParam(sql, box);

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                if (ls.getInt("cnt") > 0) {
                    result = -2;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    /**
     * 수료한과정 - 경고만 나오고 수강신청이 되야한다.
     * 
     * @param connMgr DBConnectionManager
     */
    public int jeyakStold(DBConnectionManager connMgr, RequestBox box) throws Exception {
        ListSet ls = null;
        String sql = "";
        int result = 0;

        try {
            sql = " select count(*) cnt \n";
            sql += " from TZ_OFFSTUDENT	A   \n";
            sql += " where a.subj	= ':p_subj'";
            sql += "	and a.year	= ':p_year'";
            sql += "	and a.subjseq = ':p_subjseq'";
            sql += "	and userid = ':s_userid'";
            sql = connMgr.replaceParam(sql, box);

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                if (ls.getInt("cnt") > 0) {
                    result = -3;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    /**
     * 정원초과 - 과정에 등록된 인원수는 대기자 포함 인원으로 처리하고, 차수의 인원수는 실제 수용가능 인원으로 변경한다.
     * 
     * @param connMgr DBConnectionManager
     */
    public int jeyakStudentlimit(DBConnectionManager connMgr, RequestBox box) throws Exception {
        ListSet ls = null;
        String sql = "";
        int result = 0;

        try {
            sql = " select (case STUDENTLIMIT  when 0  then  1000000  else (STUDENTLIMIT+STUDENTWAIT) end) as limit ,";
            sql += "	(select count(userid) from TZ_OFFPROPOSE where subj = a.subj and subjseq = ':p_subjseq' and year = ':p_year' and chkfirst != 'Y' and chkfinal != 'N' and seq = ':p_seq') as usernum   \n";
            sql += " from   TZ_OFFSUBJSEQ a  \n";
            sql += " where a.subj	= ':p_subj'";
            sql += " and a.subjseq	= ':p_subjseq'";

            sql = connMgr.replaceParam(sql, box);
            System.out.println("jeyakStudentlimit.sql" + sql);

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                if (ls.getInt("limit") < ls.getInt("usernum")) {
                    result = -1;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    /**
     * 교수목록
     * 
     * @param box receive from the form object and session
     * @return ArrayList 교수목록
     */
    public ArrayList selectTutorList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;
        ListSet ls = null;
        StringBuffer sb = new StringBuffer();
        String sql = null;
        try {
            connMgr = new DBConnectionManager();

            sb.append(" SELECT\n	B.NAME,B.majorbook,B.intro, b.career, \n");
            sb.append("	decode(nvl(trim(b.phototerms), 1), b.phototerms, '/upload/bulletin/' || b.phototerms, '/images/portal/common/nophoto.gif') phototerms, b.comp ");
            sb.append(" FROM TZ_OFFSUBJTUTOR A, TZ_TUTOR B\n");
            sb.append(" WHERE a.USERID = B.USERID");
            sb.append(" AND A.SUBJ = ':p_subj'\n");

            sql = connMgr.replaceParam(sb, box);
            ls = connMgr.executeQuery(sql, box);
            list = ls.getAllDataList();
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
     * 수강과정 리스트 (Off라인) -- 수정완료
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList selectSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList list1 = null;
        StringBuffer sql = new StringBuffer();
        String runSql = null;

        String p_type = box.getString("p_upperclass");
        String p_area = box.getString("p_area");
        //		String p_type = box.getStringDefault("p_upperclass", box.get("type"));
        box.sync("s_subjnm");
        box.sync("s_specials");
        box.sync("s_subjnm");
        box.sync("s_mclassnm");
        box.sync("s_sclowerclass");
        box.sync("p_area");
        box.put("type", p_type);
        box.put("p_upperclass", p_type);

        String v_searchtext = box.getStringDefault("p_searchtext", "ALL");
        String v_search = box.getStringDefault("p_search", "NAME");

        // 사이트의 GRCODE 로 과정 리스트
        //		String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            // 수강신청 가능한 과정
            sql.append("SELECT\n");
            sql.append("B.UPPERCLASS, B.UPPCLASSNM,B.MIDDLECLASS,B.MIDDLECLASSNM,B.LOWERCLASS,B.LOWERCLASSNM,\n");
            sql.append("A.SUBJ,A.YEAR,A.SUBJSEQ,A.SEQ,\n");
            sql.append("A.PROPSTART,A.PROPEND,A.EDUSTART,A.EDUEND,A.BILLBEGINDT,A.BILLENDDT,A.ISCLOSED,A.STARTCANCELDATE,A.ENDCANCELDATE,\n");
            sql.append("A.SUBJNM,A.ISVISIBLE,\n");
            sql.append("A.STUDENTLIMIT,A.BIYONG,A.ISGOYONG,\n");
            sql.append("A.GRADSCORE,A.GRADSTEP,A.GRADREPORT,A.GRADEXAM,A.GRADFTEST,A.GRADHTEST,\n");
            sql.append("A.WSTEP,A.WMTEST,A.WFTEST,A.WHTEST,A.WREPORT,A.WETC1,A.WETC2,\n");
            sql.append("A.EDUDAYS,A.EDUDAYSTYPE,\n");
            sql.append("A.PLACE,A.CHARGER,A.SPECIALS,A.TUTORID,A.TERMTOTAL,A.ISUSE,\n");
            sql.append("	case when A.SPECIALS like 'Y__' then 'Y' else 'N' end ishit,		\n");
            sql.append("	case when A.SPECIALS like '_Y_' then 'Y' else 'N' end isnew,		\n");
            sql.append("	case when A.SPECIALS like '__Y' then 'Y' else 'N' end isrecom,		\n");
            sql.append("	case when SYSDATE BETWEEN TO_DATE(A.PROPSTART, 'YYYYMMDDHH24') AND TO_DATE(A.PROPEND, 'YYYYMMDDHH24') then (case when A.STUDENTLIMIT+NVL(A.STUDENTWAIT,0)=0 then 'Y' else \n");
            sql.append("            (case when A.STUDENTLIMIT+NVL(A.STUDENTWAIT,0)> nvl(offpropose_count,0) then 'Y' else 'N' end) end) else 'N' end isprop,		\n");
            //            sql.append("	case when SYSDATE BETWEEN TO_DATE(A.PROPSTART, 'YYYYMMDDHH24') AND TO_DATE(A.PROPEND, 'YYYYMMDDHH24') then 'Y' else 'N' end isprop,		\n");
            sql.append("A.ISINTRODUCTION,A.GOYONGPRICEMAJOR,\n");
            sql.append("A.GOYONGPRICEMINOR,A.SUBJTARGET,A.INTRO,A.EXPLAIN2,A.NEEDINPUT,A.EXPLAIN, B.AREA, (SELECT CODENM FROM TZ_CODE WHERE GUBUN='0101' AND CODE=B.AREA) AREANAME, \n");
            //  sql.append("(case when d.subj is null or substr(a.EDUEND,5,2)!=to_char(sysdate,'yyyymm') then 'Y' else 'N' end) as me_sukang_yn \n");
            sql.append("(case when d.subj is not null then 'Y' else 'N' end) as me_sukang_yn \n");
            sql.append("FROM TZ_OFFSUBJSEQ A\n");
            sql.append("left join VZ_OFFSUBJCLASS B on A.SUBJ = B.SUBJ \n");
            //			sql.append("WHERE SYSDATE BETWEEN TO_DATE(A.PROPSTART, 'YYYYMMDDHH24') AND TO_DATE(A.PROPEND, 'YYYYMMDDHH24')\n");
            sql.append("left join (select subj,year,subjseq,count(*) as offpropose_count from TZ_OFFPROPOSE where CHKFINAL='Y' group by subj,year,subjseq) c on a.subj=c.subj and a.year=c.year and a.subjseq=c.subjseq\n");
            sql.append("left join TZ_OFFPROPOSE d on a.subj=d.subj and a.year=d.year and a.subjseq=d.subjseq and d.userid='" + box.getSession("userid") + "' \n");
            //sql.append("WHERE SUBSTR(A.PROPSTART, 1, 4) = TO_CHAR(SYSDATE, 'YYYY') \n");
            //sql.append("AND B.UPPERCLASS LIKE ");
            sql.append("WHERE B.UPPERCLASS LIKE ");
            sql.append(SQLString.Format(p_type));
            sql.append("||'%'\nAND A.ISUSE = 'Y'\n");
            sql.append("AND A.ISVISIBLE = 'Y'\n");
            sql.append("AND B.MIDDLECLASS LIKE ");
            sql.append(SQLString.Format(p_area));
            sql.append("||'%'");

            if (!v_searchtext.equals("ALL")) {
                if (v_search.equals("NAME")) {
                    sql.append(" \n and upper(a.subjnm) like upper('%" + v_searchtext + "%')");
                } else if (v_search.equals("CONT")) {
                    sql.append(" \n and upper(a.subjnm) like upper('%" + v_searchtext + "%')");
                }
            }
            /**
             * 검색조건 sql.append("\n\t and a.specials LIKE DECODE(nvl(':s_specials', 'ALL'), 'ALL', '%', ':s_specials')\n"
             * ); sql.append("\n\t and ( (upper(a.subjnm) LIKE '%' || nvl(upper(':s_subjnm'), upper(a.subjnm)) || '%') or (upper(b.search_nm) LIKE '%' || nvl(upper(':s_subjnm'), upper(b.search_nm)) || '%') )\n"
             * );
             * 
             * /** 필터링 영역
             */
            sql.append("\n\t and B.MIDDLECLASS LIKE DECODE(nvl(':s_mclassnm', 'ALL'), 'ALL', '%', ':s_mclassnm')\n");
            sql.append("\n\t and B.LOWERCLASS LIKE DECODE(nvl(':s_sclowerclass', 'ALL'), 'ALL', '%', ':s_sclowerclass')\n");
            //			sql.append("\n\t and NVL(b.area, nvl(':p_area', 'ALL')) LIKE DECODE(nvl(':p_area', 'ALL'), 'ALL', '%', ':p_area')");

            sql.append("\norder by isprop desc,A.PROPSTART DESC, SUBJNM\n");
            runSql = connMgr.replaceParam(sql, box);
            ls1 = connMgr.executeQuery(runSql);

            list1 = ls1.getAllDataList(connMgr, runSql, box, true);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, runSql);
            throw new Exception("sql1 = " + runSql + "\r\n" + ex.getMessage());
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
     * 전체 과정 리스트 (Off라인)
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList selectTotalSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList list1 = null;
        StringBuffer sql = new StringBuffer();
        String runSql = null;
        //String p_type = box.get("type") + "01";
        box.sync("s_subjnm");
        box.sync("s_specials");
        box.sync("s_subjnm");
        box.sync("s_lclassnm");
        box.sync("s_mclassnm");
        box.sync("s_sclowerclass");

        // 이트의 GRCODE 로 과정 리스트
        //		String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            // 수강신청 가능한 과정
            sql.append("SELECT\n");
            sql.append("B.UPPERCLASS, B.UPPCLASSNM,B.MIDDLECLASS,B.MIDDLECLASSNM,B.LOWERCLASS,B.LOWERCLASSNM,\n");
            sql.append("A.SUBJ,A.YEAR,A.SUBJSEQ,A.SEQ,\n");
            sql.append("A.PROPSTART,A.PROPEND,A.EDUSTART,A.EDUEND,A.BILLBEGINDT,A.BILLENDDT,A.ISCLOSED,A.STARTCANCELDATE,A.ENDCANCELDATE,\n");
            sql.append("A.SUBJNM,A.ISVISIBLE,\n");
            sql.append("A.STUDENTLIMIT,A.BIYONG,A.ISGOYONG,\n");
            sql.append("A.GRADSCORE,A.GRADSTEP,A.GRADREPORT,A.GRADEXAM,A.GRADFTEST,A.GRADHTEST,\n");
            sql.append("A.WSTEP,A.WMTEST,A.WFTEST,A.WHTEST,A.WREPORT,A.WETC1,A.WETC2,\n");
            sql.append("A.EDUDAYS,A.EDUDAYSTYPE,\n");
            sql.append("A.PLACE,A.CHARGER,A.SPECIALS,A.TUTORID,A.TERMTOTAL,A.ISUSE,\n");
            sql.append("	case when A.SPECIALS like 'Y__' then 'Y' else 'N' end ishit,		\n");
            sql.append("	case when A.SPECIALS like '_Y_' then 'Y' else 'N' end isnew,		\n");
            sql.append("	case when A.SPECIALS like '__Y' then 'Y' else 'N' end isrecom,		\n");
            sql.append("A.ISINTRODUCTION,A.GOYONGPRICEMAJOR,\n");
            sql.append("A.GOYONGPRICEMINOR,A.SUBJTARGET,A.INTRO,A.EXPLAIN2,A.NEEDINPUT,A.EXPLAIN, B.AREA, (SELECT CODENM FROM TZ_CODE WHERE GUBUN='0101' AND CODE=B.AREA) AREANAME, \n");
            sql.append("case when SYSDATE BETWEEN TO_DATE(A.PROPSTART, 'YYYYMMDDHH24') AND TO_DATE(A.PROPEND, 'YYYYMMDDHH24') then 'Y' else 'N' end isprop, \n");
            sql.append("(case when d.subj is not null  then 'Y' else 'N' end) as me_sukang_yn \n");
            sql.append("FROM TZ_OFFSUBJSEQ A inner join VZ_OFFSUBJCLASS B \n");
            sql.append("on A.SUBJ = B.SUBJ \n");
            sql.append("left join TZ_OFFPROPOSE d on a.subj=d.subj and a.year=d.year and a.subjseq=d.subjseq and d.userid='" + box.getSession("userid") + "' \n");
            sql.append("WHERE to_char(sysdate,'YYYYMM') between replace(substr(a.propstart,1,6),'.','') and replace(substr(a.propend,1,6),'.','')\n");
            sql.append("AND A.SUBJ = B.SUBJ\n");
            //sql.append("AND B.UPPERCLASS = ");
            //sql.append(SQLString.Format(p_type));
            sql.append("\nAND A.ISUSE = 'Y'\n");
            sql.append("AND A.ISVISIBLE = 'Y'\n");

            /**
             * 검색조건
             */
            sql.append("\n\t and a.specials LIKE DECODE(nvl(':s_specials', 'ALL'), 'ALL', '%', ':s_specials')\n");
            sql.append("\n\t and ( (upper(a.subjnm) LIKE '%' || upper(nvl(':s_subjnm', a.subjnm)) || '%') or (upper(b.search_nm) LIKE '%' || upper(nvl(':s_subjnm', b.search_nm)) || '%') )\n");

            /**
             * 필터링 영역
             */
            sql.append("\n\t and B.UPPERCLASS LIKE DECODE(nvl(':s_lclassnm', 'ALL'), 'ALL', '%', ':s_lclassnm')\n");
            sql.append("\n\t and B.MIDDLECLASS LIKE DECODE(nvl(':s_mclassnm', 'ALL'), 'ALL', '%', ':s_mclassnm')\n");

            //			sql.append("\norder by MIDDLECLASSNM, subjnm\n");
            runSql = connMgr.replaceParam(sql, box);
            ls1 = connMgr.executeQuery(runSql);

            list1 = ls1.getAllDataList(connMgr, runSql, box, true);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, runSql);
            throw new Exception("sql1 = " + runSql + "\r\n" + ex.getMessage());
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
     * 과정상세보기 - 수정완료
     * 
     * @param box receive from the form object and session
     * @return ProposeCourseData
     */
    public DataBox selectSubjectPreview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls1 = null;
        StringBuffer sql = new StringBuffer();
        String sqlResult = null;

        box.sync("p_upperclass");
        box.sync("menuid");

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT\n");
            sql.append("A.SUBJNM,B.MIDDLECLASSNM,B.LOWERCLASSNM,A.SUBJTARGET,A.INTRO,A.EXPLAIN,\n");
            sql.append("/*	과정명, 분야, 난이도, 교육대상, 교육목적, 교육내용	*/\n");
            sql.append("A.PROPSTART,A.PROPEND,A.EDUSTART,A.EDUEND,A.TERMTOTAL,A.STUDENTLIMIT,A.BIYONG,suryo_hideshow_yn,img_path,\n");
            sql.append("/*	수강신청시작, 종료, 교육 시작, 종료, 총학기수,정원, 수강료	*/\n");
            sql.append("A.PLACE,A.CHARGER,C.NAME,C.TEL_LINE,C.EMAIL,\n");
            sql.append("/*	교육장소, 교육문의 - 담당자, 전화번호, EMAIL	*/\n");
            sql.append("A.GRADSCORE,A.GRADSTEP,A.GRADREPORT,A.GRADEXAM,A.GRADFTEST,A.GRADHTEST,\n");
            sql.append("/*	고용보험적용여부, 고용보험환급금액-대기업, 고용보험환급금액-중소기업	*/\n");
            sql.append("decode(A.ISGOYONG, 'Y', 'true', 'false') ISGOYONG,A.GOYONGPRICEMAJOR,A.GOYONGPRICEMINOR,\n");
            sql.append("	case when A.SPECIALS like 'Y__' then 'Y' else 'N' end ishit,\n");
            sql.append("	case when A.SPECIALS like '_Y_' then 'Y' else 'N' end isnew,\n");
            sql.append("	case when A.SPECIALS like '__Y' then 'Y' else 'N' end isrecom,\n");
            sql.append("    CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN PROPSTART AND PROPEND THEN  \n");
            sql.append("		'Y'\n");
            sql.append("		ELSE 'N'\n");
            sql.append("	END AS FLAGYN,\n");
            sql.append("/*	수료(이수)기준 : 총점, 진도율, 레포트, 중간평가, 최종평가, 형성평가	*/\n");
            sql.append("A.WSTEP,A.WMTEST,A.WFTEST,A.WHTEST,A.WREPORT,A.WETC1,A.WETC2, B.AREA, (SELECT CODENM FROM TZ_CODE WHERE GUBUN='0101' AND CODE=B.AREA) AREANAME\n");
            sql.append("/*	가중치 : 진도율, 중간평가, 최종평가, 형성평가, 레포트, 참여도, 기타	*/\n");
            sql.append("FROM TZ_OFFSUBJSEQ A, VZ_OFFSUBJCLASS B, TZ_MEMBER C\n");
            sql.append("WHERE A.SUBJ = b.subj\n");
            sql.append("AND A.CHARGER = c.userid(+)\n");
            sql.append("AND A.SUBJ = ':p_subj'\n");
            sql.append("AND A.SUBJSEQ = ':p_subjseq'\n");
            sql.append("AND A.YEAR = ':p_year'\n");
            sql.append("AND A.SEQ = ':p_seq'");
            sqlResult = connMgr.replaceParam(sql, box);

            ls1 = connMgr.executeQuery(sqlResult);

            if (ls1.next()) {
                dbox = ls1.getDataBox();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sqlResult);
            throw new Exception("sql = " + sqlResult + "\r\n" + ex.getMessage());
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
     * 수강과정 리스트 (오프라인 메인)
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectMainSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList list = null;
        String sql = "";
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String orderSql = "";
        String countSql = "";
        DataBox dbox = null;

        // 사이트의 GRCODE 로 과정 리스트

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            // 수강신청 가능한 과정
            headSql.append("SELECT\n");
            headSql.append("rownum rowno, B.UPPERCLASS, B.UPPCLASSNM,B.MIDDLECLASS,B.MIDDLECLASSNM,B.LOWERCLASS,B.LOWERCLASSNM,\n");
            headSql.append("A.SUBJ,A.YEAR,A.SUBJSEQ,A.SEQ,\n");
            headSql.append("A.PROPSTART,A.PROPEND,A.EDUSTART,A.EDUEND,A.BILLBEGINDT,A.BILLENDDT,A.ISCLOSED,A.STARTCANCELDATE,A.ENDCANCELDATE,\n");
            headSql.append("A.SUBJNM,A.ISVISIBLE,\n");
            headSql.append("A.STUDENTLIMIT,A.BIYONG,A.ISGOYONG,\n");
            headSql.append("A.GRADSCORE,A.GRADSTEP,A.GRADREPORT,A.GRADEXAM,A.GRADFTEST,A.GRADHTEST,\n");
            headSql.append("A.WSTEP,A.WMTEST,A.WFTEST,A.WHTEST,A.WREPORT,A.WETC1,A.WETC2,\n");
            headSql.append("A.EDUDAYS,A.EDUDAYSTYPE,\n");
            headSql.append("A.PLACE,A.CHARGER,A.SPECIALS,A.TUTORID,A.TERMTOTAL,A.ISUSE,\n");
            headSql.append("	case when A.SPECIALS like 'Y__' then 'Y' else 'N' end ishit,		\n");
            headSql.append("	case when A.SPECIALS like '_Y_' then 'Y' else 'N' end isnew,		\n");
            headSql.append("	case when A.SPECIALS like '__Y' then 'Y' else 'N' end isrecom,		\n");
            headSql.append("A.ISINTRODUCTION,A.GOYONGPRICEMAJOR,\n");
            headSql.append("A.GOYONGPRICEMINOR,A.SUBJTARGET,A.INTRO,A.EXPLAIN2,A.NEEDINPUT,A.EXPLAIN, \n");
            headSql.append("(SELECT SUBMAINFILENAMENEW FROM TZ_OFFSUBJ WHERE SUBJ = A.SUBJ) SUBMAINFILENAMENEW \n");
            bodySql.append("FROM TZ_OFFSUBJSEQ A,VZ_OFFSUBJCLASS B\n");
            bodySql.append("WHERE SYSDATE BETWEEN TO_DATE(A.PROPSTART, 'YYYYMMDDHH24') AND TO_DATE(A.PROPEND, 'YYYYMMDDHH24')\n");
            bodySql.append("AND A.SUBJ = B.SUBJ\n");
            bodySql.append("AND A.ISUSE = 'Y'\n");
            bodySql.append("AND A.ISVISIBLE = 'Y'\n");

            orderSql += "\norder by a.course, a.scupperclass, a.scmiddleclass, a.subjnm";

            countSql = "select count(*) " + bodySql.toString();

            int total_row_count = BoardPaging.getTotalRow(connMgr, countSql); //     전체 row 수를 반환한다

            Random ran = new Random();

            StringBuffer bodySql2 = new StringBuffer();
            //			bodySql2.append("\n\t where rowno in (12 , 4 , 1 , 3 )");
            bodySql2.append("\n\t where rowno in (");

            int iLen = total_row_count > 4 ? 4 : total_row_count;
            int iCnt = 0;

            for (int i = 0; i < iLen; i++) {
                int ranCnt = 0;
                if (ran.nextInt(total_row_count) == 0)
                    ranCnt = 1;
                else
                    ranCnt = ran.nextInt(total_row_count);

                if (ranCnt != 0) {
                    if (iCnt != 0)
                        bodySql2.append(" , ");
                    bodySql2.append(ranCnt);
                    iCnt++;
                }
            }
            bodySql2.append(" )");

            if (total_row_count > 0) {
                sql = "select * from ( " + headSql.toString() + bodySql.toString() + ") " + bodySql2.toString();
                ls1 = connMgr.executeQuery(sql);

                while (ls1.next()) {
                    dbox = ls1.getDataBox();
                    list.add(dbox);
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql1 = " + sql.toString() + "\r\n" + ex.getMessage());
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
        return list;
    }

    /**
     * 메인화면에서 오프라인 수강과정 리스트 5개만 가져오는 루틴
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList selectMainFiveSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList list1 = null;
        String sql1 = "";
        String runSql = null;

        String p_type = box.getString("p_upperclass");
        box.sync("s_subjnm");
        box.sync("s_specials");
        box.sync("s_subjnm");
        box.sync("s_mclassnm");
        box.sync("s_sclowerclass");
        box.sync("p_area");
        box.put("type", p_type);
        box.put("p_upperclass", p_type);

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql1 = " select * from (select \n";
            sql1 += "  subj,year,subjseq,subjnm,biyong,studentlimit,intro,area_nm,isonoff,scupperclass,uclassnm,scyear,seq,HITNUMBER,edustart,rownum as a1 \n";
            sql1 += "from (  \n";
            sql1 += "    select\n";
            sql1 += "            a.subj,a.year,a.subjseq,a.seq,b.subjnm,a.biyong,a.studentlimit,b.intro,b.HITNUMBER,a.edustart,\n";
            sql1 += "            (case when b.area='B0' then '방송' \n";
            sql1 += "                  when b.area='G0' then '게임' else '문콘' end) as area_nm,\n";
            sql1 += "             (case when b.area='B0' then '01' \n";
            sql1 += "                   when b.area='G0' then '02' else '03' end) as scupperclass,\n";
            sql1 += "            'OFF' as isonoff,'' as uclassnm,A.YEAR as scyear \n";
            sql1 += "    from tz_offsubjseq a\n";
            sql1 += "    left join tz_offsubj b on a.subj=b.subj\n";
            sql1 += "    where substr(a.PROPSTART,1,8)<=to_char(sysdate,'yyyymmdd') and substr(a.PROPEND,1,8)>=to_char(sysdate,'yyyymmdd') and \n";
            sql1 += "      b.isuse		= 'Y'";
            sql1 += "    order by HITNUMBER desc \n";
            sql1 += ")x)u  \n";
            sql1 += "where a1<6  \n";

            sql1 = connMgr.replaceParam(sql1, box);
            ls1 = connMgr.executeQuery(sql1);

            list1 = ls1.getAllDataList(connMgr, sql1, box, true);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, runSql);
            throw new Exception("sql1 = " + runSql + "\r\n" + ex.getMessage());
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
     * 골드 클래스 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 골드 클래스 리스트
     * @throws Exception
     */
    public ArrayList selectMainGoldClassList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;
        // String p_genre = box.getString("p_genre");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();
            sql.append(" SELECT  A.SEQ, A.VODIMG, A.LECNM, A.TUTORNM         \n ");
            sql.append("         , A.LECTIME, A.CREATOR, A.INTRO             \n ");
            sql.append("         , A.WIDTH_S, A.HEIGHT_S, A.VIEWCNT          \n ");
            sql.append("         , B.CHECKPOIN, A.VODURL, A.GENRE, A.TUTORIMG 			 \n ");
            sql.append(" FROM    TZ_GOLDCLASS A                              \n ");
            sql.append("         , (                                         \n ");
            sql.append("             SELECT  SEQ, AVG(CHECKPOIN) CHECKPOIN   \n ");
            sql.append("             FROM    TZ_GOLDCLASSREPL                \n ");
            sql.append("             GROUP BY SEQ                            \n ");
            sql.append("         ) B                                         \n ");
            sql.append(" WHERE   A.SEQ       = B.SEQ(+)                      \n ");
            sql.append(" AND     A.USEYN     = 'Y'                           \n ");
            sql.append(" ORDER BY LDATE DESC                                 \n ");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
     * 수강신청 여부 확인
     */
    public String selectCheckedPropose(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "N";

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_seq = box.getString("p_seq");
        String v_userid = box.getString("s_userid");

        try {
            connMgr = new DBConnectionManager();

            sql = "SELECT *								\n ";
            sql += "  FROM TZ_OFFPROPOSE A					\n ";
            sql += " WHERE A.SUBJ = '" + v_subj + "'			\n ";
            sql += "   AND A.YEAR = '" + v_year + "'			\n ";
            sql += "   AND A.SUBJSEQ = '" + v_subjseq + "'		\n ";
            sql += "   AND A.SEQ = '" + v_seq + "'				\n ";
            sql += "   AND A.USERID = '" + v_userid + "'		\n ";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = "Y";
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
}