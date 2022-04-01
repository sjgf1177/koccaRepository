package com.credu.propose;

import java.io.File;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

import jxl.Sheet;
import jxl.Workbook;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.HashCipher;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * 
 * @author kocca
 * 
 */
public class ExternalEduBean {

    /**
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public DataBox registerExternalEdu(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        Sheet sheet = null;
        Workbook workbook = null;
        ConfigSet conf = new ConfigSet();

        String newFileName = box.getNewFileName("p_eduFile");
        String realFileName = box.getRealFileName("p_eduFile");

        System.out.println("newFileName : " + newFileName);
        System.out.println("newFileName : " + conf.getProperty("dir.home") + newFileName);
        System.out.println("realFileName : " + realFileName);

        HashMap<String, String> subjInfoMap = null;
        HashMap<String, String> memberInfoMap = null;
        ArrayList<HashMap<String, String>> memberList = null;
        DataBox resultBox = null;

        String grcode = box.getString("grcode");
        String gyear = box.getString("gyear");
        String grseq = box.getString("grseq");

        String grPrefix = "";
        String userid = "";
        String subj = "";
        String sex = "";
        String isGraduated = "";

        long initMillis = System.currentTimeMillis();
        int totalRow = 0;

        try {

            connMgr = new DBConnectionManager();

            sql.setLength(0);
            sql.append("SELECT  GR_PREFIX   \n");
            sql.append("  FROM  TZ_GRCODE   \n");
            sql.append(" WHERE  GRCODE = '").append(grcode).append("' \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                grPrefix = ls.getString("gr_prefix");
            }

            ls.close();
            ls = null;

            sql.setLength(0);
            sql.append("SELECT  SUBJ        \n");
            sql.append("    ,   SUBJSEQ     \n");
            sql.append("    ,   GRADSCORE   \n");
            sql.append("  FROM  TZ_SUBJSEQ  \n");
            sql.append(" WHERE  GRCODE = '").append(grcode).append("'  \n");
            sql.append("   AND  GYEAR = '").append(gyear).append("' \n");
            sql.append("   AND  GRSEQ = '").append(grseq).append("' \n");

            ls = connMgr.executeQuery(sql.toString());

            subjInfoMap = new HashMap<String, String>();
            while (ls.next()) {
                subjInfoMap.put(ls.getString("subj"), ls.getString("subjseq") + "_" + ls.getString("gradscore"));
            }

            ls.close();
            ls = null;

            workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.home") + newFileName));
            sheet = workbook.getSheet(0);

            memberList = new ArrayList<HashMap<String, String>>();
            for (int i = 1; i < sheet.getRows(); i++) {
                userid = grPrefix + (initMillis + i);
                sex = sheet.getCell(0, i).getContents();
                subj = sheet.getCell(1, i).getContents();
                isGraduated = sheet.getCell(2, i).getContents();

                memberInfoMap = new HashMap<String, String>();
                memberInfoMap.put("grcode", grcode);
                memberInfoMap.put("gyear", gyear);
                memberInfoMap.put("grseq", grseq);
                memberInfoMap.put("userid", userid);
                memberInfoMap.put("sex", sex);
                memberInfoMap.put("subj", subj);
                memberInfoMap.put("subjInfo", (String) subjInfoMap.get(subj));
                memberInfoMap.put("isGraduated", isGraduated);

                memberList.add(memberInfoMap);
                totalRow++;
            }
            System.out.println("totalRow : " + totalRow);

            connMgr.setAutoCommit(false);
            int memberRegCount = this.registerMember(connMgr, memberList);
            int memberProposeRegCount = this.registerPropose(connMgr, memberList, box);
            int memberStudentRegCount = this.registerStudent(connMgr, memberList, box);

            resultBox = new DataBox("resultBox");
            if (totalRow == memberRegCount && totalRow == memberProposeRegCount && totalRow == memberStudentRegCount) {
                connMgr.commit();
                resultBox.put("msg", "success");
                resultBox.put("count", totalRow);
            } else {
                connMgr.rollback();
                resultBox.put("msg", "fail");
                resultBox.put("count", 0);
            }

        } catch (Exception ex) {
            connMgr.rollback();
            resultBox.put("msg", "fail");
            resultBox.put("count", 0);
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                    pstmt = null;
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }

            FileManager.deleteFile(newFileName); // 업로드 된 파일 삭제
        }

        return resultBox;
    }

    /**
     * 학습 테이블에 정보를 등록한다.
     * 
     * @param connMgr
     * @param memberList
     * @param box
     * @return
     * @throws Exception
     */
    private int registerStudent(DBConnectionManager connMgr, ArrayList<HashMap<String, String>> memberList, RequestBox box) throws Exception {
        PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        HashMap<String, String> memberInfoMap = null;
        String isGraduated = "";
        String subjInfo = "";
        String[] subjInfoArr = new String[2];

        int score = 0;
        int index = 1;

        int[] result = null;

        try {

            sql.setLength(0);
            sql.append("/* com.credu.propose.ExternalEduBean registerStudent (학습 정보 등록) */  \n");
            sql.append("INSERT  INTO    TZ_STUDENT  (   \n");
            sql.append("        SUBJ        \n");
            sql.append("    ,   YEAR        \n");
            sql.append("    ,   SUBJSEQ     \n");
            sql.append("    ,   USERID      \n");
            sql.append("    ,   COMP        \n");
            sql.append("    ,   ISDINSERT   \n");
            sql.append("    ,   SCORE       \n");
            sql.append("    ,   TSTEP       \n");
            sql.append("    ,   MTEST       \n");
            sql.append("    ,   FTEST       \n");
            sql.append("    ,   REPORT      \n");
            sql.append("    ,   ACT         \n");
            sql.append("    ,   ETC1        \n");
            sql.append("    ,   ETC2        \n");
            sql.append("    ,   AVTSTEP     \n");
            sql.append("    ,   AVMTEST     \n");
            sql.append("    ,   AVFTEST     \n");
            sql.append("    ,   AVREPORT    \n");
            sql.append("    ,   AVACT       \n");
            sql.append("    ,   AVETC1      \n");
            sql.append("    ,   AVETC2      \n");
            sql.append("    ,   ISGRADUATED \n");
            sql.append("    ,   ISRESTUDY   \n");
            sql.append("    ,   ISB2C       \n");
            sql.append("    ,   BRANCH      \n");
            sql.append("    ,   EDUNO       \n");
            sql.append("    ,   LUSERID     \n");
            sql.append("    ,   LDATE       \n");
            sql.append("    ,   HTEST       \n");
            sql.append("    ,   AVHTEST     \n");
            sql.append("    ,   STUSTATUS   \n");
            sql.append(") VALUES (  \n");
            sql.append("        ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   'Y' \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   'N' \n");
            sql.append("    ,   'N' \n");
            sql.append("    ,   99  \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') -- \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   'Y' \n");
            sql.append(")   \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            for (int i = 0; i < memberList.size(); i++) {
                // memberInfoMap = new HashMap<String, String>();
                memberInfoMap = memberList.get(i);
                index = 1;

                isGraduated = (String) memberInfoMap.get("isGraduated");
                subjInfo = (String) memberInfoMap.get("subjInfo");
                subjInfoArr = subjInfo.split("_");

                score = (isGraduated.equals("Y")) ? Integer.parseInt(subjInfoArr[1]) : Integer.parseInt(subjInfoArr[1]) - 1;

                pstmt.setString(index++, (String) memberInfoMap.get("subj"));
                pstmt.setString(index++, (String) memberInfoMap.get("gyear"));
                pstmt.setString(index++, subjInfoArr[0]);
                pstmt.setString(index++, (String) memberInfoMap.get("userid"));
                pstmt.setString(index++, (String) memberInfoMap.get("grcode"));
                pstmt.setInt(index++, score);
                pstmt.setInt(index++, score);
                pstmt.setInt(index++, score);
                pstmt.setString(index++, isGraduated);
                pstmt.setString(index++, box.getSession("userid"));

                pstmt.addBatch();
            }

            result = pstmt.executeBatch();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                    pstmt = null;
                } catch (Exception e) {
                }
            }
        }
        return result.length;
    }

    /**
     * 수강신청 정보를 등록한다.
     * 
     * @param connMgr
     * @param memberList
     * @return
     */
    private int registerPropose(DBConnectionManager connMgr, ArrayList<HashMap<String, String>> memberList, RequestBox box) throws Exception {
        PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        HashMap<String, String> memberInfoMap = null;
        String subjInfo = "";
        String[] subjInfoArr = new String[2];

        int index = 1;

        int[] result = null;

        try {

            sql.setLength(0);
            sql.append("/* com.credu.propose.ExternalEduBean registerStudent (수강신청 정보 등록) */  \n");
            sql.append("INSERT  INTO    TZ_PROPOSE  (    \n");
            sql.append("        SUBJ        \n");
            sql.append("    ,   YEAR        \n");
            sql.append("    ,   SUBJSEQ     \n");
            sql.append("    ,   USERID      \n");
            sql.append("    ,   COMP        \n");
            sql.append("    ,   APPDATE     \n");
            sql.append("    ,   ISDINSERT   \n");
            sql.append("    ,   ISB2C       \n");
            sql.append("    ,   ISCHKFIRST  \n");
            sql.append("    ,   CHKFIRST    \n");
            sql.append("    ,   CHKFINAL    \n");
            sql.append("    ,   LUSERID     \n");
            sql.append("    ,   LDATE       \n");
            sql.append("    ,   ASP_GUBUN   \n");
            sql.append(") VALUES (  \n");
            sql.append("        ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
            sql.append("    ,   'Y' \n");
            sql.append("    ,   'N' \n");
            sql.append("    ,   'N' \n");
            sql.append("    ,   'Y' \n");
            sql.append("    ,   'Y' \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
            sql.append("    ,   ?   \n");
            sql.append(")   \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            for (int i = 0; i < memberList.size(); i++) {
                // memberInfoMap = new HashMap<String, String>();
                memberInfoMap = memberList.get(i);
                index = 1;

                subjInfo = (String) memberInfoMap.get("subjInfo");
                subjInfoArr = subjInfo.split("_");

                pstmt.setString(index++, (String) memberInfoMap.get("subj"));
                pstmt.setString(index++, (String) memberInfoMap.get("gyear"));
                pstmt.setString(index++, subjInfoArr[0]);
                pstmt.setString(index++, (String) memberInfoMap.get("userid"));
                pstmt.setString(index++, (String) memberInfoMap.get("grcode"));
                pstmt.setString(index++, box.getSession("userid"));
                pstmt.setString(index++, (String) memberInfoMap.get("grcode"));

                pstmt.addBatch();
            }

            result = pstmt.executeBatch();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                    pstmt = null;
                } catch (Exception e) {
                }
            }
        }
        return result.length;
    }

    /**
     * 회원 정보를 등록한다.
     * 
     * @param connMgr
     * @param memberList
     * @return
     * @throws Exception
     */
    private int registerMember(DBConnectionManager connMgr, ArrayList<HashMap<String, String>> memberList) throws Exception {
        PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        HashMap<String, String> memberInfoMap = null;

        int index = 1;

        int[] result = null;

        String pwd = "";

        try {

            sql.setLength(0);
            sql.append("/* com.credu.propose.ExternalEduBean registerMember (회원 정보 등록) */  \n");
            sql.append("INSERT  INTO    TZ_MEMBER   (   \n");
            sql.append("        GRCODE          \n");
            sql.append("    ,   USERID          \n");
            sql.append("    ,   NAME            \n");
            sql.append("    ,   PWD             \n");
            sql.append("    ,   ISMAILING       \n");
            sql.append("    ,   ISLETTERING     \n");
            sql.append("    ,   ISOPENING       \n");
            sql.append("    ,   MEMBERGUBUN     \n");
            sql.append("    ,   STATE           \n");
            sql.append("    ,   VALIDATION      \n");
            sql.append("    ,   INDATE          \n");
            sql.append("    ,   LDATE           \n");
            sql.append("    ,   ISSMS           \n");
            sql.append("    ,   PRIVATE_YESNO   \n");
            sql.append("    ,   SEX             \n");
            sql.append("    ,   MOBILE_USERID   \n");
            sql.append("    ,   RESNO           \n");
            sql.append(") VALUES (  \n");
            sql.append("        ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   'N' \n");
            sql.append("    ,   'N' \n");
            sql.append("    ,   'N' \n");
            sql.append("    ,   'C' \n");
            sql.append("    ,   'Y' \n");
            sql.append("    ,   0   \n");
            sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
            sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
            sql.append("    ,   'N' \n");
            sql.append("    ,   'N' \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   'EXTERNAL_EDU_MEMBER'   \n"); // 외부 교육 일괄 입과 처리로 등록된 회원
            sql.append(")   \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            for (int i = 0; i < memberList.size(); i++) {
                // memberInfoMap = new HashMap<String, String>();
                memberInfoMap = memberList.get(i);
                index = 1;

                pwd = HashCipher.createHash((String) memberInfoMap.get("userid"));

                pstmt.setString(index++, (String) memberInfoMap.get("grcode"));
                pstmt.setString(index++, (String) memberInfoMap.get("userid"));
                pstmt.setString(index++, (String) memberInfoMap.get("userid"));
                pstmt.setString(index++, pwd);
                pstmt.setString(index++, (String) memberInfoMap.get("sex"));
                pstmt.setString(index++, (String) memberInfoMap.get("userid"));

                pstmt.addBatch();
            }

            result = pstmt.executeBatch();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                    pstmt = null;
                } catch (Exception e) {
                }
            }
        }
        return result.length;
    }
}
