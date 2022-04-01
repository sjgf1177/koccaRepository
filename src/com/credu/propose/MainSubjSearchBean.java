//**********************************************************
//1. ��      ��: SUBJECT INFORMATION USER BEAN
//2. ���α׷���:  MainSubjSearchBean.java
//3. ��      ��: �����ȳ� ����� bean
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: ������ 2004.02
//7. ��      ��:
//**********************************************************
package com.credu.propose;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.credu.common.SubjComBean;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;

public class MainSubjSearchBean {

    public MainSubjSearchBean() {
    }

    /**
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> selectSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        StringBuilder sb = new StringBuilder();

        String searchText = box.getString("p_lsearchtext");
        String detlSubjFlag = box.getStringDefault("detlSubjFlag", "");
        String grcode = box.getSession("tem_grcode");
        grcode = (grcode.equals("")) ? "N000001" : grcode;

        System.out.println("detlSubjFlag : " + detlSubjFlag);

        String[] searchTextArr = null;

        try {
            connMgr = new DBConnectionManager();

            // �˻��� �α� �ױ�
            createSearchWordLog(connMgr, box);

            searchTextArr = searchText.split("\\s");

            sb.append(" SELECT  /* MainSubjSearchBean.selectSubjList (���հ˻�) */  \n");
            sb.append("         *                                                   \n");
            sb.append("   FROM  (                                                   \n");
            sb.append("         SELECT  COUNT(*) OVER() AS TOT_CNT                  \n");
            sb.append("             ,   SUBJ_FLAG                                   \n");
            sb.append(this.makeRankQuery(searchTextArr));
            sb.append("             ,   UPPERCLASS                                \n");
            sb.append("             ,   (SELECT CLASSNAME FROM TZ_SUBJATT WHERE V.UPPERCLASS || '000000' = SUBJCLASS) AS UPPERCLASSNM \n");
            sb.append("             ,   MIDDLECLASS                               \n");
            sb.append("             ,   LOWERCLASS                                \n");
            sb.append("             ,   SUBJCLASS                                 \n");
            sb.append("             ,   SUBJ                                        \n");
            sb.append("             ,   YEAR                                        \n");
            sb.append("             ,   SUBJSEQ                                     \n");
            sb.append("             ,   ISONOFF                                     \n");
            sb.append("             ,   SUBJNM                                      \n");
            sb.append("             ,   REPLACE(SUBJFILENAMENEW, '\', '/') AS SUBJFILENAMENEW   \n");
            sb.append("             ,   REPLACE(SUBJFILENAMEREAL, '\', '/') AS SUBJFILENAMEREAL \n");
            sb.append("             ,   ISUSE                                       \n");
            sb.append("             ,   CONTENTTYPE                                 \n");
            sb.append("             ,   SPECIALS                                    \n");
            sb.append("             ,   AREA                                        \n");
            sb.append("             ,   AREANM                                      \n");
            sb.append("             ,   SEARCH_NM                                   \n");
            sb.append("             ,   INTRO                                       \n");
            sb.append("             ,   EXPLAIN                                     \n");
            sb.append("             ,   TUTOR_NM                                    \n");
            sb.append("             ,   PREURL                                      \n");
            sb.append("             ,   WIDTH                                       \n");
            sb.append("             ,   HEIGHT                                      \n");
            sb.append("             ,   LECTURE_CLS                                 \n");
            sb.append("           FROM  VZ_SUBJINFO V                               \n");
            sb.append("          WHERE  GRCODE = '").append(grcode).append("'       \n");
            sb.append("            AND  (" + this.makeSearhConditionQuery("SUBJNM", searchTextArr, searchText));
            sb.append("             OR " + this.makeSearhConditionQuery("INTRO", searchTextArr, searchText));
            // sb.append("             OR " + this.makeSearhConditionQuery("EXPLAIN", searchTextArr, searchText));
            sb.append("             OR " + this.makeSearhConditionQuery("SEARCH_NM", searchTextArr, searchText));
            sb.append("             OR " + this.makeSearhConditionQuery("TUTOR_NM", searchTextArr, searchText));

            sb.append("     )                                                       \n");
            if (!detlSubjFlag.equals("")) {
                sb.append("            AND  SUBJ_FLAG = '").append(detlSubjFlag).append("'                \n");
            }
            sb.append("     )                                                       \n");
            sb.append("  ORDER  BY  SUBJ_FLAG, ( \n");

            for (int i = 0; i < searchTextArr.length; i++) {
                if (i < searchTextArr.length - 1) {
                    sb.append("  RNK").append((i + 1)).append(" || ");
                } else {
                    sb.append("  RNK").append((i + 1));
                }
            }
            sb.append("  ) ASC, SUBJNM                                    \n");

            System.out.println("\n\n" + sb.toString() + "\n\n");

            DataBox dbox = null;

            ls = connMgr.executeQuery(sb.toString());

            if (!detlSubjFlag.equals("")) { // �� �˻��� ���
                int totalRowCnt = 0;
                int totalPage = 0;
                int pageSize = box.getInt("pageSize") == 0 ? 10 : box.getInt("pageSize");
                int pageNo = box.getInt("pageNo") == 0 ? 1 : box.getInt("pageNo");

                if (ls.next()) {
                    totalRowCnt = ls.getInt("TOT_CNT");
                    ls.moveFirst();
                }

                ls.setPageSize(pageSize); // �������� row ������ �����Ѵ�
                ls.setCurrentPage(pageNo, totalRowCnt); // ������������ȣ�� �����Ѵ�.
                totalPage = ls.getTotalPage(); // ��ü ������ ���� ��ȯ�Ѵ�

                box.put("pageNo", pageNo);
                box.put("pageSize", pageSize);

                while (ls.next()) {
                    dbox = ls.getDataBox();
                    dbox.put("d_dispnum", new Integer(totalRowCnt - ls.getRowNum() + 1));
                    dbox.put("totalPage", new Integer(totalPage));
                    dbox.put("pageSize", new Integer(pageSize));
                    dbox.put("totalRowCnt", new Integer(totalRowCnt));
                    list.add(dbox);
                }

            } else { // ��ü �˻��� ���
                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("sql1 = " + sb.toString() + "\r\n" + ex.getMessage());
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

    private String makeRankQuery(String[] strArr) throws ArrayIndexOutOfBoundsException {
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < strArr.length; i++) {
                sb.append("             ,   CASE WHEN INSTR(SUBJNM, '").append(strArr[i]).append("') > 0 THEN 1  \n");
                sb.append("                      WHEN INSTR(SUBJNM, '").append(strArr[i]).append("') = 0 AND INSTR(INTRO, '").append(strArr[i]).append("') > 0  THEN 2  \n");
                sb.append("                      WHEN INSTR(SUBJNM, '").append(strArr[i]).append("') = 0 AND INSTR(INTRO, '").append(strArr[i]).append("') = 0 AND INSTR(EXPLAIN, '").append(strArr[i]).append("') > 0 THEN 3 \n");
                sb.append("                      WHEN INSTR(SUBJNM, '").append(strArr[i]).append("') = 0 AND INSTR(INTRO, '").append(strArr[i]).append("') = 0 AND INSTR(EXPLAIN, '").append(strArr[i]).append("') = 0 AND INSTR(TUTOR_NM, '")
                        .append(strArr[i]).append("') > 0 THEN 4  \n");
                sb.append("                      ELSE 5     \n");
                sb.append("                 END AS RNK").append((i + 1)).append(" \n");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ErrorManager.getErrorStackTrace(ex);
        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e);
        }
        return sb.toString();
    }

    /**
     * ���� �˻��ÿ� ���� �˻�� �Էµ� ��� ���� �������� �����ؼ� �����Ѵ�.
     * 
     * @param colName
     * @param strArr
     * @return
     * @throws ArrayIndexOutOfBoundsException
     */
    private String makeSearhConditionQuery(String colName, String[] strArr, String fullWord) throws ArrayIndexOutOfBoundsException {
        StringBuilder sb = new StringBuilder();
        try {
            colName = colName.toUpperCase();
            if (strArr.length == 1) {
                sb.append(" ( UPPER(").append(colName).append(") LIKE UPPER('%' || '").append(strArr[0]).append("' || '%') )\n");
            } else {
                sb.append("(");
                for (int i = 0; i < strArr.length; i++) {
                    if (i == 0) {
                        sb.append(" UPPER(").append(colName).append(") LIKE UPPER('%' || '").append(strArr[i]).append("' || '%') \n");
                    } else {
                        sb.append(" OR   UPPER(").append(colName).append(") LIKE UPPER('%' || '").append(strArr[i]).append("' || '%') \n");
                    }
                }
                // sb.append(" OR   " + colName + " LIKE '%' || '" + fullWord + "' || '%' \n");
                sb.append(" )\n");
            }

        } catch (ArrayIndexOutOfBoundsException ex) {
            ErrorManager.getErrorStackTrace(ex);
        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e);
        }
        return sb.toString();

    }

    /**
     * ����ڰ� �Է��� �˻��� �α� ����
     * 
     * @param connMgr DBConnectionManager
     * @param box RequestBox
     * @return result Int
     * @throws Exception
     */
    public int createSearchWordLog(DBConnectionManager connMgr, RequestBox box) throws Exception {
        PreparedStatement pstmt = null;
        StringBuilder sb = new StringBuilder();
        int result = 0, i = 0;

        try {
            String userId = box.getSession("userid");
            sb.append("INSERT   INTO    TZ_LOG_SEARCH_WORD \n");
            sb.append("         (               \n");
            sb.append("             SEARCH_WORD \n");
            sb.append("         ,   SEARCH_DT   \n");

            if (userId != null && !userId.equals("")) {
                sb.append("         ,   USERID      \n");
            }
            sb.append("         ,   USER_IP     \n");
            sb.append("         ,   PLATFORM    \n");
            sb.append("         )   VALUES  (   \n");
            sb.append("             ?           \n");
            sb.append("         ,   SYSDATE     \n");

            if (userId != null && !userId.equals("")) {
                sb.append("         ,   ?       \n");
            }

            sb.append("         ,   ?       \n");
            sb.append("         ,   'PC-WEB'\n");
            sb.append("         )               \n");

            pstmt = connMgr.prepareStatement(sb.toString());

            pstmt.setString(++i, box.getString("p_lsearchtext"));

            if (userId != null && !userId.equals("")) {
                pstmt.setString(++i, userId);
            }

            pstmt.setString(++i, box.getString("userip"));

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e, box, sb.toString());
            throw new Exception("" + e.getMessage());
        } finally {
        }
        return result;
    }

    /**
     * �����˻� - �¶��� ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList �˻� ��� ����Ʈ
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectSubjSearch(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList list1 = null;
        String sql1 = "";

        String v_lsearchtext = box.getString("p_lsearchtext");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            // �˻��� �α� �ױ�
            createSearchWordLog(connMgr, box);

            // ������û ������ ����
            sql1 = " select distinct a.subj, a.introducefilenamenew, a.subjseq, a.subjnm, a.scyear, a.isonoff, a.scupperclass, a.scmiddleclass, a.sclowerclass, usebook, a.prewidth, a.preheight,a.propstart as ps,a.wj_classkey,\n";
            sql1 += " (SELECT INTRO FROM TZ_SUBJ WHERE SUBJ=a.subj ) INTRO,\n";
            sql1 += "		TO_CHAR(TO_DATE(a.propstart, 'YYYYMMDDHH24'), 'YYYY.MM.DD') propstart, TO_CHAR(TO_DATE(a.propend, 'YYYYMMDDHH24'), 'YYYY.MM.DD') propend, TO_CHAR(TO_DATE(a.edustart, 'YYYYMMDDHH24'), 'YYYY.MM.DD') edustart, TO_CHAR(TO_DATE(a.eduend, 'YYYYMMDDHH24'), 'YYYY.MM.DD') eduend, a.scbiyong, a.biyong, a.preurl, a.isunit, A.STUDENTLIMIT,\n";
            sql1 += "	substring(a.specials,1,1) isnew, substring(a.specials,2,1) ishit, substring(a.specials,3,1) isrecom,\n";
            sql1 += "	(select classname from tz_subjatt x\n";
            sql1 += "	where x.upperclass = a.scupperclass and x.middleclass  = '000' and x.lowerclass = '000' ) uclassnm,\n";
            sql1 += "	(select classname from tz_subjatt x\n";
            sql1 += "	where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.lowerclass = '000' ) mclassnm,\n";
            sql1 += "    (select classname from tz_subjatt x\n";
            sql1 += "    where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.LOWERCLASS = a.SCLOWERCLASS ) lclassnm,\n";
            sql1 += "	a.course, a.courseseq, a.coursenm, a.isbelongcourse, a.subjcnt,a.area, a.areaname,\n";
            sql1 += "	(Select round( NVL(sum(distcode1_avg) / count(*), 0), 1) From TZ_SULEACH	Where subj = a.subj and grcode = a.grcode) as sul_avg,\n";
            sql1 += "	case when to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend	then 'Y' else 'N' end propyn\n";
            sql1 += "   from (select * from VZ_SCSUBJSEQIMG where propstart not in (' ') and to_char(sysdate,'YYYYMM') between replace(substr(propstart,1,6),'.','') and replace(substr(propend,1,6),'.','')) a\n";
            // ����Ʈ��������
            sql1 += "  where a.grcode ='N000001' ";
            sql1 += "\n\t and a.isuse		= 'Y'";
            sql1 += "\n\t and to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend";
            sql1 += "\n\t and upper(a.subjnm) LIKE upper('%" + v_lsearchtext + "%') and rownum < 20"; // �˻�����
            // (search
            // keyword)
            /*
             * 
             * // ������û ������ ���� sql1 =
             * " select * from tz_subj where subjnm like '%" + v_lsearchtext +
             * "%'\n"; sql1 += " or intro like  '%" + v_lsearchtext + "%'\n";
             * sql1+= "\n\t and isuse		= 'Y'";
             */

            ls1 = connMgr.executeQuery(sql1);

            list1 = ls1.getAllDataList(connMgr, sql1, box, true);

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
        /*
         * DBConnectionManager connMgr = null; ListSet ls1 = null; ArrayList
         * list1 = null; // ArrayList list2 = null; DataBox dbox= null; String
         * sql = "";
         * 
         * // String v_user_id = box.getSession("userid");
         * 
         * // ����Ʈ�� GRCODE �� ���� ����Ʈ String v_grcode =
         * box.getSession("tem_grcode");
         * 
         * String v_lsearch = box.getString("p_lsearch"); String v_lsearchtext =
         * box.getString("p_lsearchtext");
         * 
         * try { list1 = new ArrayList();
         * 
         * sql =
         * " select a.subj, a.upperclass, a.middleclass,                        "
         * ; sql +=
         * "        a.isonoff, c.classname, a.subjnm,                           "
         * ; sql +=
         * "        (select round( NVL(sum(distcode1_avg) / count(*), 0), 1) ";
         * sql +=
         * "           from TZ_SULEACH	                                        "
         * ; sql +=
         * "          where subj = a.subj and grcode = b.grcode) as sul_avg,    "
         * ; sql +=
         * "        (select count(*) from tz_postscript where subj=a.subj) as ptsct_cnt    "
         * ; sql +=
         * "   from TZ_SUBJ a, TZ_GRSUBJ b,                                     "
         * ; sql +=
         * "       (select upperclass, middleclass, classname from tz_subjatt   "
         * ; sql +=
         * "         where middleclass <> '000' and lowerclass = '000' ) c      "
         * ; sql +=
         * "  where a.subj      = b.subjcourse                                  "
         * ; sql +=
         * "    and a.upperclass = c.upperclass                                 "
         * ; sql +=
         * "    and a.middleclass = c.middleclass                               "
         * ; sql += "    and b.grcode    = " + SQLString.Format(v_grcode); sql
         * +=
         * "    and a.isuse     = 'Y'                                           "
         * ; sql +=
         * "    and a.isvisible = 'Y'                                           "
         * ;
         * 
         * if (!v_lsearchtext.equals("")) { // �¿��� ���� if
         * (v_lsearch.equals("isonoff")) { if (v_lsearchtext.equals("���̹�")){ sql
         * += "    and a.isonoff = 'ON'"; } else if (v_lsearchtext.equals("����"))
         * { sql += "    and a.isonoff = 'OFF'"; } else { sql +=
         * "    and a.isonoff like '%"+v_lsearchtext+"%'"; } } else if
         * (v_lsearch.equals("middleclass")) { // �з� sql +=
         * "    and upper(c.classname) like upper('%"+v_lsearchtext+"%')"; }
         * else if (v_lsearch.equals("subjnm")) { // ������ sql +=
         * "    and upper(a.subjnm) like upper('%"+v_lsearchtext+"%')"; } }
         * 
         * sql +=
         * "  order by a.isonoff desc, a.upperclass, a.middleclass, a.subjnm";
         * System.out.println("sql_subj="+sql);
         * 
         * connMgr = new DBConnectionManager(); ls1 = connMgr.executeQuery(sql);
         * while (ls1.next()) { dbox = ls1.getDataBox(); list1.add(dbox); } }
         * catch (Exception ex) { ErrorManager.getErrorStackTrace(ex, box, sql);
         * throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage()); }
         * finally { if(ls1 != null) { try { ls1.close(); }catch (Exception e)
         * {} } if(connMgr != null) { try { connMgr.freeConnection(); }catch
         * (Exception e10) {} } } return list1;
         */
    }

    /**
     * �����˻� - ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList �˻� ��� ����Ʈ
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectOffSubjSearch(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList list1 = null;
        String sql = "";

        String v_lsearchtext = box.getString("p_lsearchtext");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            // ������û ������ ����
            sql = "SELECT\n";
            sql += "B.UPPERCLASS, B.UPPCLASSNM,B.MIDDLECLASS,B.MIDDLECLASSNM,B.LOWERCLASS,B.LOWERCLASSNM,\n";
            sql += "A.SUBJ,A.YEAR,A.SUBJSEQ,A.SEQ,\n";
            sql += "A.PROPSTART,A.PROPEND,A.EDUSTART,A.EDUEND,A.BILLBEGINDT,A.BILLENDDT,A.ISCLOSED,A.STARTCANCELDATE,A.ENDCANCELDATE,\n";
            sql += "A.SUBJNM,A.ISVISIBLE,\n";
            sql += "A.STUDENTLIMIT,A.BIYONG,A.ISGOYONG,\n";
            sql += "A.GRADSCORE,A.GRADSTEP,A.GRADREPORT,A.GRADEXAM,A.GRADFTEST,A.GRADHTEST,\n";
            sql += "A.WSTEP,A.WMTEST,A.WFTEST,A.WHTEST,A.WREPORT,A.WETC1,A.WETC2,\n";
            sql += "A.EDUDAYS,A.EDUDAYSTYPE,\n";
            sql += "A.PLACE,A.CHARGER,A.SPECIALS,A.TUTORID,A.TERMTOTAL,A.ISUSE,\n";
            sql += "	case when A.SPECIALS like 'Y__' then 'Y' else 'N' end ishit,		\n";
            sql += "	case when A.SPECIALS like '_Y_' then 'Y' else 'N' end isnew,		\n";
            sql += "	case when A.SPECIALS like '__Y' then 'Y' else 'N' end isrecom,		\n";
            sql += "A.ISINTRODUCTION,A.GOYONGPRICEMAJOR,\n";
            sql += "A.GOYONGPRICEMINOR,A.SUBJTARGET,A.INTRO,A.EXPLAIN2,A.NEEDINPUT,A.EXPLAIN, B.AREA, (SELECT CODENM FROM TZ_CODE WHERE GUBUN='0101' AND CODE=B.AREA) AREANAME, \n";
            sql += "case when SYSDATE BETWEEN TO_DATE(A.PROPSTART, 'YYYYMMDDHH24') AND TO_DATE(A.PROPEND, 'YYYYMMDDHH24') then 'Y' else 'N' end isprop \n";

            sql += "FROM TZ_OFFSUBJSEQ A inner join VZ_OFFSUBJCLASS B \n";
            sql += "on A.SUBJ = B.SUBJ \n";

            // sql +=
            // "WHERE to_char(sysdate,'YYYYMM') between replace(substr(a.propstart,1,6),'.','') and replace(substr(a.propend,1,6),'.','')\n";
            // sql +=
            // "WHERE  to_char(sysdate,'YYYYMM') between replace(substr(a.propstart,1,6),'.','') and replace(substr(a.propend,1,6),'.','') and A.SUBJ = B.SUBJ\n";
            sql += "WHERE A.SUBJ = B.SUBJ\n";
            // sql.append("AND B.UPPERCLASS = ");
            // sql.append(SQLString.Format(p_type));
            sql += "\nAND A.ISUSE = 'Y'\n";
            sql += "AND A.ISVISIBLE = 'Y'\n";
            sql += "\n\t and upper(a.subjnm) LIKE upper('%" + v_lsearchtext + "%') and rownum < 20";
            sql += "ORDER BY YEAR DESC, SUBJ DESC, SUBJSEQ DESC";

            /**
             * �˻�����
             */
            // sql+=
            // " and ( (upper(a.subjnm) LIKE '%' || upper(nvl(':s_subjnm', a.subjnm)) || '%') or (upper(b.search_nm) LIKE '%' || upper(nvl(':s_subjnm', b.search_nm)) || '%') )\n";

            ls1 = connMgr.executeQuery(sql);

            list1 = ls1.getAllDataList(connMgr, sql, box, true);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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
     * �����˻� - ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList �˻� ��� ����Ʈ
     */
    public ArrayList<DataBox> selectSubjSearchK(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        // ArrayList list2 = null;
        DataBox dbox = null;
        String sql = "";

        // ����Ʈ�� GRCODE �� ���� ����Ʈ
        String v_grcode = box.getSession("tem_grcode");

        String v_lsearch = box.getString("p_lsearch");
        String v_lsearchtext = box.getString("p_lsearchtext");

        try {
            list1 = new ArrayList<DataBox>();

            sql = " select a.subj, a.subjnm, a.sphere, a.isordinary, a.isworkshop, a.isunit,    ";
            sql += "        (select NVL(avg(distcode1_avg),0.0) from tz_suleach               ";
            sql += "         where subj= a.subj and grcode <> 'ALL'          ) distcode1_avg,    ";
            sql += "        (select NVL(avg(distcode_avg),0.0) from tz_suleach                ";
            sql += "          where subj= a.subj and grcode = 'ALL'          ) distcode_avg      ";
            sql += "   from TZ_SUBJ a, TZ_GRSUBJ b                                               ";
            sql += "  where a.subj = b.subjcourse                                                ";
            sql += "    and b.grcode = " + SQLString.Format(v_grcode);
            sql += "    and a.isuse = 'Y'                                                        ";
            sql += "    and a.isvisible = 'Y'                                                    ";

            if (!v_lsearch.equals("")) {
                sql += "    and " + v_lsearch;
            }

            if (!v_lsearchtext.equals("")) { // ������
                sql += "    and upper(a.subjnm) like upper('%" + v_lsearchtext + "%')";
            }

            sql += "  order by subj, subjnm";
            System.out.println("sql_subj=" + sql);

            connMgr = new DBConnectionManager();
            ls1 = connMgr.executeQuery(sql);
            while (ls1.next()) {
                dbox = ls1.getDataBox();
                dbox.put("distcode1_avg", new Double(ls1.getDouble("distcode1_avg")));
                dbox.put("distcode_avg", new Double(ls1.getDouble("distcode_avg")));
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
     * ��õ���� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��õ���� ����Ʈ
     */
    public ArrayList<DataBox> selectSubjRecomList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        // ArrayList list2 = null;
        DataBox dbox = null;
        String sql = "";

        // String v_user_id = box.getSession("userid");
        // ����Ʈ�� GRCODE �� ���� ����Ʈ
        String v_grcode = box.getSession("tem_grcode");
        String v_process = box.getString("p_process");

        // int l = 0;
        // int v_propstart = 0;
        // int v_propend = 0;

        // boolean ispossible = false;

        try {
            list1 = new ArrayList<DataBox>();

            if (v_process.equals("main"))
                sql = " select * from (select rownum rnum,   "; // ������ ��õ����
            // select
            else
                sql = " select ";

            sql += "		a.sphere, a.subj, a.subjnm, a.upperclass, a.middleclass, a.isonoff, ";

            if (!v_process.equals("main")) {
                sql += "        c.classname classname,                         		  ";
            }
            sql += "        a.intro, a.introducefilenamereal, a.introducefilenamenew,  ";
            sql += "        (select round( NVL(sum(distcode1_avg) / count(*), 0), 1) From TZ_SULEACH	";
            sql += "          where subj = a.subj and grcode = b.grcode) as sul_avg,    ";
            sql += "        (select count(*) from tz_postscript where subj=a.subj) as ptsct_cnt    ";
            sql += "   from tz_subj a,				                                  ";
            sql += "        tz_grrecom b, ( select upperclass, middleclass, classname from TZ_SUBJATT ";
            sql += "         where middleclass <> '000' and lowerclass ='000' ) c ";
            sql += "  where a.subj = b.subjcourse                                     ";

            sql += "    and a.upperclass = c.upperclass                           ";
            sql += "    and a.middleclass = c.middleclass                         ";
            sql += "    and b.grcode = " + SQLString.Format(v_grcode);
            sql += "    and a.isuse = 'Y'                                             ";
            sql += "    and a.isvisible = 'Y'                                         ";

            if (v_process.equals("main")) {
                sql += "  order by a.subjnm, a.isonoff desc, a.upperclass, a.middleclass, a.subj ) where rnum < 4   ";
            } else {
                sql += "  order by a.isonoff desc, a.upperclass, a.middleclass, a.subj    ";
            }

            connMgr = new DBConnectionManager();
            ls1 = connMgr.executeQuery(sql);
            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
     * �ű԰��� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList �ű԰��� ����Ʈ
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectSubjNewList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList list1 = null;
        // ArrayList list2 = null;
        DataBox dbox = null;
        String sql = "";

        // String v_user_id = box.getSession("userid");
        // ����Ʈ�� GRCODE �� ���� ����Ʈ
        String v_grcode = box.getSession("tem_grcode");
        String v_process = box.getString("p_process");

        // int l = 0;
        // int v_propstart = 0;
        // int v_propend = 0;

        // boolean ispossible = false;

        try {
            list1 = new ArrayList();

            if (v_process.equals("main"))
                sql = " select * from (select rownum rnum,   "; // ������ �ű԰���
            // select
            else
                sql = " select ";

            sql += "		a.sphere, a.subj, a.subjnm, a.upperclass, a.middleclass, a.isonoff, ";

            if (!v_process.equals("main")) {
                sql += "        c.classname classname,                         		  ";
            }
            sql += "        a.intro, a.introducefilenamereal, a.introducefilenamenew,  ";
            sql += "        (select round( NVL(sum(distcode1_avg) / count(*), 0), 1) From TZ_SULEACH	";
            sql += "          where subj = a.subj and grcode = b.grcode) as sul_avg 				    ";
            sql += "   from tz_subj a,				                                  ";
            sql += "        (select distinct grcode, subj from tz_subjseq where grcode = 'N000002') b, ";
            sql += "		( select upperclass, middleclass, classname from TZ_SUBJATT ";
            sql += "         where middleclass <> '000' and lowerclass ='000' ) c ";
            sql += "  where a.subj = b.subj                                     ";
            sql += "    and a.upperclass = c.upperclass                           ";
            sql += "    and a.middleclass = c.middleclass                         ";
            sql += "    and b.grcode = " + SQLString.Format(v_grcode);
            sql += "    and a.isuse = 'Y'                                             ";
            sql += "    and a.isvisible = 'Y'                                         ";
            sql += "	and left(a.specials,1) = 'Y'								";

            if (v_process.equals("main")) {
                sql += "  order by a.subjnm, a.isonoff desc, a.upperclass, a.middleclass, a.subj  ) where rnum < 4  ";
            } else {
                sql += "  order by a.isonoff desc, a.upperclass, a.middleclass, a.subj    ";
            }

            System.out.println("new sql = " + sql);

            connMgr = new DBConnectionManager();
            ls1 = connMgr.executeQuery(sql);
            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
     * �ʱ�ȭ�� ��õ���� ����Ʈ ���� 4��
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��õ ���� ����Ʈ
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectSubjTopRecomList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;
        String sql = "";
        String v_grcode = box.getSession("tem_grcode");

        try {
            list = new ArrayList();

            sql = " select x.*,";
            sql += "       (Select round( NVL(sum(distcode1_avg) / count(*), 0), 1) ";
            sql += "          From TZ_SULEACH	                                       ";
            sql += "         Where subj = x.subj and grcode = x.grcode) as sul_avg     ";
            sql += "from (select rownum rnum,  a.subj, a.subjnm, a.isonoff, grcode";
            sql += "   from tz_subj a, tz_grrecom b                                    ";
            sql += "  where a.subj = b.subjcourse                                      ";
            sql += "    and b.grcode =  " + SQLString.Format(v_grcode);
            sql += "    and a.isuse = 'Y'                                              ";
            sql += "    and a.isvisible = 'Y'      ) x where rnum < 5                                    ";

            // System.out.println("sql_recom="+sql);
            connMgr = new DBConnectionManager();

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
     * �ʱ�ȭ�� ��õ���� ����Ʈ ���� 4��
     * 
     * @param box receive from the form object and session
     * @return ArrayList �ű� ���� ����Ʈ
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectSubjTopNewList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;
        String sql = "";
        String v_grcode = box.getSession("tem_grcode");

        try {
            list = new ArrayList();

            sql = " select * from (select rownum rnum, a.subj, a.subjnm, a.isonoff,a.ldate, ";
            sql += "       	(Select round( NVL(sum(distcode1_avg) / count(*), 0), 1)  ";
            sql += "          From TZ_SULEACH	                                        ";
            sql += "          Where subj = a.subj and grcode = b.grcode) as sul_avg     ";
            sql += "   from (select subj, subjnm, isonoff, isuse, isvisible, ldate from tz_subj where left(specials,1) = 'Y') a, ";
            sql += "	 (select distinct grcode, subj from tz_subjseq where grcode='" + v_grcode + "') b                           ";
            sql += "  where a.subj = b.subj                                      ";
            sql += "    and a.isuse = 'Y'                                        ";
            sql += "    and a.isvisible = 'Y'                                    ";
            sql += "order by a.ldate desc ) where rnum < 4";

            connMgr = new DBConnectionManager();

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
     * ��������
     * 
     * @param box receive from the form object and session
     * @return ArrayList �������� ����Ʈ
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectEducationList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;
        String sql = "";

        String v_year = box.getStringDefault("s_year", FormatDate.getDate("yyyy"));
        String v_grcode = box.getSession("tem_grcode");

        try {
            list = new ArrayList();

            sql = " select distinct a.scsubj, a.scyear, a.scsubjseq, a.scsubjnm, a.isonoff, a.scupperclass,      ";
            sql += "		 a.scmiddleclass, a.sclowerclass, b.classname, a.scpropstart, a.scpropend, a.scbiyong, ";
            sql += "        CASE WHEN a.course = '000000' THEN a.subjseqgr ELSE a.scsubjseq END as subjseqgr,     ";
            sql += "        CASE WHEN a.course = '000000' THEN a.eduperiod ELSE 0 END as eduperiod , a.reviewdays, a.reviewtype  ";
            sql += "   from vz_scsubjseq a,                                                                       ";
            sql += "        ( select upperclass, middleclass, classname from TZ_SUBJATT                           ";
            sql += "           where middleclass <> '000' and lowerclass ='000' ) b                               ";
            sql += "  where a.scupperclass = b.upperclass                                                         ";
            sql += "    and a.scmiddleclass = b.middleclass                                                       ";
            sql += "    and a.grcode =  " + SQLString.Format(v_grcode);
            sql += "    and a.isuse = 'Y'                                                                         ";
            sql += "    and a.subjvisible = 'Y'                                                                   ";
            sql += "    and a.gyear = " + SQLString.Format(v_year);
            sql += "   order by a.isonoff desc, a.scupperclass, a.scmiddleclass, a.sclowerclass, a.scsubj, a.scyear, a.scsubjseq ";

            System.out.println("selectEducationList=" + sql);
            connMgr = new DBConnectionManager();

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
     * ��������(����)
     * 
     * @param box receive from the form object and session
     * @return ArrayList �������� ����Ʈ
     */
    public ArrayList<DataBox> selectEducationListK(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        String sql = "";

        String v_year = box.getStringDefault("s_year", FormatDate.getDate("yyyy"));
        String v_grcode = box.getSession("tem_grcode");
        String v_subjtype = box.getStringDefault("p_subjtype", "alway");
        String v_searchtext = "";
        if (v_subjtype.equals("alway")) {
            v_searchtext = "    and a.isordinary = 'Y' and a.course = '000000' ";
        } else if (v_subjtype.equals("sometime")) {
            v_searchtext = "    and a.isordinary = 'N' and a.course = '000000'  ";
        } else if (v_subjtype.equals("prof")) {
            v_searchtext = "    and a.course <> '000000'";
        } else if (v_subjtype.equals("workshop")) {
            v_searchtext = "    and a.isworkshop = 'Y' and a.course = '000000'  ";
        } else if (v_subjtype.equals("recognition")) {
            v_searchtext = "    and a.isunit = 'Y' and a.course = '000000'      ";
        }

        try {
            list = new ArrayList<DataBox>();

            sql = " select a.scsubj, a.scyear, a.scsubjseq, a.subjseqgr,  a.scsubjnm, a.isonoff, a.scupperclass, ";
            sql += "        a.scmiddleclass, a.sclowerclass, b.classname, a.propstart, a.propend, a.scbiyong,     ";
            sql += "        a.eduperiod, a.isordinary, a.isworkshop, a.isunit, a.course, a.subjnm, a.subjcnt,     ";
            sql += "        a.scpropstart, a.scpropend                                                            ";
            sql += "   from vz_scsubjseq a,                                                                       ";
            sql += "        ( select upperclass, middleclass, classname from TZ_SUBJATT                           ";
            sql += "           where middleclass <> '000' and lowerclass ='000' ) b                               ";
            sql += "  where a.scupperclass = b.upperclass                                                         ";
            sql += "    and a.scmiddleclass = b.middleclass                                                       ";
            sql += "    and a.grcode =  " + SQLString.Format(v_grcode);
            sql += "    and a.isuse = 'Y'                                              ";
            sql += "    and a.subjvisible = 'Y'                                        ";
            sql += "    and a.gyear = " + SQLString.Format(v_year);
            sql += v_searchtext;
            sql += "   order by a.isonoff desc, a.scupperclass, a.scmiddleclass, a.sclowerclass, a.scsubj, a.scyear, a.scsubjseq ";

            System.out.println("selectEducationListK=" + sql);
            connMgr = new DBConnectionManager();

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
     * �����ı�(����)
     * 
     * @param box receive from the form object and session
     * @return ArrayList �������� ����Ʈ
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectFeedbackList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;
        String sql = "";

        String v_year = FormatDate.getDate("yyyy");
        String v_grcode = box.getSession("tem_grcode");
        String v_subj = box.getString("p_subj");
        int v_pageno = box.getInt("p_pageno");
        int row = 10;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select userid, get_name(userid) usernm,  answer, ldate,      ";
            sql += "        grcode, subj, year, subjseq, sulpapernum, sulnum, sultype ";
            sql += "   from tz_sulresultp                                             ";
            sql += "  where gubun = 'ALL'                                             ";
            sql += "    and subj  = " + SQLString.Format(v_subj);
            sql += "    and grcode =  " + SQLString.Format(v_grcode);
            sql += "    and year = " + SQLString.Format(v_year);
            sql += "    and distcode = '10'                                           ";
            sql += "   order by ldate desc                                            ";
            // System.out.println("selectFeedbackList="+sql);

            ls = connMgr.executeQuery(sql);

            ls.setPageSize(row); // �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno); // ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage(); // ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls.getTotalCount(); // ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
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
     * ������û �������� count
     * 
     * @param box receive from the form object and session
     * @return int ��û�������� Return
     */
    public String possibleSeqCount(RequestBox box, String subj, String gyear, String userid, String comp) throws Exception {
        DBConnectionManager connMgr = null;
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;

        String sql = "";
        // String v_user_id = userid;
        String v_comp = comp;
        String v_gyear = gyear;

        // System.out.println(subj);
        // System.out.println(v_user_id);
        // System.out.println(v_comp);
        // System.out.println(v_gyear);

        String cnt = "";

        v_comp = v_comp.substring(0, 4);

        try {
            connMgr = new DBConnectionManager();

            // sql1 = " select count(*) cnt from TZ_POLLSEL  ";

            sql += " select  \n";
            sql += "   count(subjseq)  cnt\n";
            sql += " from  \n";
            sql += "   VZ_SCSUBJSEQ a \n";
            sql += " where \n";
            sql += "   grcode in (select grcode from TZ_GRCOMP where comp like '" + v_comp + "%') \n";
            sql += "   and a.gyear = '" + v_gyear + "' \n";
            sql += "   and a.scsubj = '" + subj + "' \n";
            sql += "   and  case a.studentlimit  when 0 then 1000000  else a.studentlimit end    > (select count(userid) from tz_propose where subj = a.subj and subjseq = a.subjseq and year = a.year and (isproposeapproval !='N'and chkfinal != 'N')) \n";
            sql += "   and to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend \n";
            // //�̹̽�û�Ѱ����� �ٽ� ������û�Ҽ� ����.

            // ������ : 05.11.17 ������ : �̳��� _ || ����
            // sql+= "   and (subj||subjseq||year) not in ( \n";
            sql += "   and (subj+subjseq+year) not in ( \n";
            sql += "   select\n";
            // sql+= "     x.subj||x.subjseq||x.year\n";
            sql += "     x.subj+x.subjseq+x.year\n";
            sql += "   from\n";
            sql += "     tz_propose x \n";
            sql += "   where\n";
            sql += "     (x.userid = '" + userid + "' and x.subj = '" + subj + "' and x.chkfinal = 'Y')\n";
            sql += "     or  (select isgraduated from tz_stold where userid = '2657659' and subj = '1028') = 'Y'\n";
            sql += ") \n";

            // System.out.println("��û��������="+sql);

            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {
                cnt = rs1.getString("cnt");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return cnt;
    }

    /**
     * ������û ���� ����
     * 
     * 2005 . 02. 26�� �ۼ��� : ��â�� email : leech@credu.com ������û ���� ���� ���ϴ�
     * Method�Դϴ�.
     * 
     * @param box receive from the form object and session
     * @return boolean ��û�������� Return
     */
    public boolean possibleSeqCheck(RequestBox box, String subj, String gyear, String userid, String comp) throws Exception {
        DBConnectionManager connMgr = null;
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        // ResultSet rs1 = null;
        ListSet ls1 = null;

        String sql = "";
        String v_user_id = userid;
        String v_comp = comp;
        String v_gyear = gyear;

        String v_subj = "";
        String v_subjseq = "";
        String v_year = "";
        String v_edustart = "";
        String v_eduend = "";
        String v_isonoff = "";
        String v_isedutarget = "";
        String v_mastercd = "";
        // String v_proposetype = "";

        // System.out.println(subj);
        // System.out.println(v_user_id);
        // System.out.println(v_comp);
        // System.out.println(v_gyear);

        int cnt = 0;
        boolean chkseq = false;
        // boolean ismastersubj = false;
        boolean isedutarget = false;
        boolean ismasterpropose = false;

        v_comp = v_comp.substring(0, 4);

        SubjComBean csBean = new SubjComBean();

        try {
            connMgr = new DBConnectionManager();

            // sql1 = " select count(*) cnt from TZ_POLLSEL  ";

            sql += " select  \n";
            sql += "   a.edustart,a.eduend, a.year, a.subjseq, a.subjnm, a.isonoff , b.mastercd, b.isedutarget, b.proposetype \n";
            sql += " from  \n";
            sql += "   VZ_SCSUBJSEQ a, VZ_MASTERSUBJSEQ b \n";
            sql += " where \n";
            sql += "   a.grcode in (select grcode from TZ_GRCOMP where comp like '" + v_comp + "%') \n";
            sql += "   and a.gyear = '" + v_gyear + "' \n";
            sql += "   and a.scsubj = '" + subj + "' \n";
            sql += "   and a.seqvisible = 'Y' \n";
            // ����: lyh ����: 2005-11-16 ����:(+) �� *����..
            // sql+= "   and a.scsubj = b.subj(+)      \n";
            // sql+= "   and a.scsubjseq = b.subjseq(+) \n";
            // sql+= "   and a.year = b.year(+) \n"; ///���������� ����
            sql += "   and a.scsubj* = b.subj      \n";
            sql += "   and a.scsubjseq* = b.subjseq \n";
            sql += "   and a.year* = b.year \n"; // /���������� ����
            // /������û�Ⱓ ��������
            sql += "   and  case a.studentlimit  when 0 then 1000000  else a.studentlimit end  > (select count(userid) from tz_propose where subj = a.subj and subjseq = a.subjseq and year = a.year and (isproposeapproval !='N'and chkfinal != 'N')) \n";
            sql += "   and to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend \n";

            // ////////////////////////////////////////////
            // // 1
            // //�̹̽�û�Ѱ����� �ٽ� ������û�Ҽ� ����.
            // ////////////////////////////////////////////
            sql += "   and (a.subj) not in ( \n";
            sql += "   select\n";
            sql += "     x.subj\n";
            sql += "   from\n";
            sql += "     tz_propose x \n";
            sql += "   where\n";
            sql += "     (x.userid = '" + v_user_id + "' and x.subj = '" + subj + "' and (x.chkfinal != 'N' and x.isproposeapproval != 'N') )\n";
            // sql+=
            // "     or  (select isgraduated from tz_stold where userid = '"+userid+"' and subj = '"+subj+"') = 'Y'\n";
            sql += ") \n";

            // ////////////////////////////////////////////
            // // 2
            // //�����Ѱ����� �ٽ� ������û�Ҽ� ����.
            // ////////////////////////////////////////////
            sql += "   and (a.subj) not in ( \n";
            sql += "   select\n";
            sql += "     x.subj\n";
            sql += "   from\n";
            sql += "     tz_stold x \n";
            sql += "   where\n";
            sql += "     (x.userid = '" + v_user_id + "' and x.subj = '" + subj + "' and x.isgraduated = 'Y')\n";
            sql += ") \n";

            System.out.println("��û�������� �⺻ ���� 1=" + sql);

            ls1 = connMgr.executeQuery(sql);

            while (ls1.next()) {

                chkseq = true;

                v_subj = subj;
                v_subjseq = ls1.getString("subjseq");
                v_year = ls1.getString("year");
                v_edustart = ls1.getString("edustart");
                v_eduend = ls1.getString("eduend");
                v_isonoff = ls1.getString("isonoff");
                v_isedutarget = ls1.getString("isedutarget"); // ����ڸ� ������û �Ҽ�
                // �ִ���
                v_mastercd = ls1.getString("mastercd"); // �����Ͱ��� �ڵ�� ������ �Ǿ� �ִ���.
                // v_proposetype = ls1.getString("proposetype");

                // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // 2. �ش������ off line�����϶� �н����������� �ߺ��Ⱓ üũ,
                // �� ������û��İ� �������ù�Ŀ����� üũ���� �ʴ´�.
                // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                // if(v_isonoff.equals("OFF")
                // &&!(v_proposetype.equals("3")||v_proposetype.equals("2"))){
                if (v_isonoff.equals("OFF")) {
                    chkseq = overLapEduterm(v_edustart, v_eduend, v_user_id);
                }

                // /////////////////////////////////////////////////////////////
                // 3. �ش������ �����Ͱ����϶� ��û����/����ڿ��� üũ
                // /////////////////////////////////////////////////////////////
                if (chkseq && !v_mastercd.equals("")) {
                    ismasterpropose = csBean.IsMasterPropose(v_mastercd, v_user_id);

                    if (ismasterpropose) { // ��û�� ���� �ʾҴ�

                        isedutarget = csBean.IsEduTarget(v_subj, v_year, v_subjseq, v_user_id); // ����ڷ�
                        // ������
                        // �Ǿ��ִ�
                        // ������
                        System.out.println("������ΰ�???????" + v_subj + "::" + isedutarget);

                        if (v_isedutarget.equals("Y")) { // �ش縶���Ͱ����� ����� ����
                            // �������̸�

                            if (isedutarget) { // ������ �����Ͱ����� ���ε� ����ڶ��
                                chkseq = true;
                            } else {
                                chkseq = false; // ����ڰ� �ƴ϶��
                            }
                        } else { // ����� ���������� �ƴϸ� ��û�����ϴ�.
                            chkseq = true;
                        }

                    } else { // ��û�� �ߴ�
                        chkseq = false;
                    }

                }

                // ��û������ �����̸�
                if (chkseq) {
                    cnt++;
                }

            }

            if (cnt > 0) {
                chkseq = true;
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
        return chkseq;
    }

    /**
     * ������� ���� 2005 . 02. 26�� �ۼ��� : ��â�� email : leech@credu.com ������û ���� ���� ���ϴ�
     * Method�Դϴ�.
     * 
     * @param box receive from the form object and session
     * @return boolean ��û�������� Return
     */
    public boolean LimitExe(String upperclass, String gyear, String comp, String userid, String v_propstart, String v_propend, double v_budget, int v_maxsubjcnt) throws Exception {
        DBConnectionManager connMgr = null;
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        // ListSet ls1 = null;

        String sql = "";
        String v_user_id = userid;
        String v_comp = comp;
        // String v_gyear = gyear;
        String v_upperclass = upperclass;

        // String v_subj = "";
        // String v_subjseq = "";
        // String v_year= "";
        // String v_edustart= "";
        // String v_eduend= "";
        // String v_isonoff= "";
        // String v_isedutarget ="";
        // String v_mastercd = "";

        double totbiyong = 0;
        int v_propcnt = 0;

        boolean chkupperclass = true;

        v_comp = v_comp.substring(0, 4);

        try {
            connMgr = new DBConnectionManager();

            sql += " select  \n";
            sql += "   sum(c.biyong) totbiyong \n";
            sql += " from \n";
            sql += "   tz_propose a, \n";
            sql += "   tz_member b, \n";
            sql += "   vz_scsubjseq c\n";
            sql += " where\n";
            sql += "   a.userid = b.userid\n";
            sql += "   and substring(b.comp,1,4)  = '" + v_comp + "'\n";
            sql += "   and a.subj = c.subj\n";
            sql += "   and a.year = c.year\n";
            sql += "   and a.subjseq = c.subjseq\n";
            sql += "   and c.scupperclass = '" + v_upperclass + "'\n";
            sql += "   and  \n";
            sql += "( \n";
            sql += "  (to_char(sysdate, 'yyyymmddhh') between '" + v_propstart + "' and '" + v_propend + "') \n";
            sql += "   and \n";
            sql += "  (appdate between '" + v_propstart + "' and '" + v_propend + "') \n";
            sql += ") \n";

            // sql+=
            // "     (to_char(sysdate, 'yyyymmddhh') between '"+v_propstart+"' and '"+v_propend+"')\n";
            // sql+= "   and \n";
            // sql+= "     ( \n";
            // sql+=
            // "   (edustart <= '"+v_propend+"' and eduend >= '"+v_propend+"') or \n";
            // sql+=
            // "     (edustart <= '"+v_propstart+"' and eduend >= '"+v_propstart+"') or\n";
            // sql+=
            // "     (edustart >= '"+v_propstart+"' and eduend <= '"+v_propend+"')\n";
            // sql+= "     ) \n";
            // sql+= "   )   \n";
            // System.out.println(sql);
            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {
                totbiyong = rs1.getDouble("totbiyong");
            }

            if (totbiyong >= v_budget) {
                chkupperclass = false;
            }

            System.out.println("chkupperclass???????????" + chkupperclass);
            System.out.println("�Ѻ�� �հ�???????????" + totbiyong);

            sql = " select \n";
            sql += "   count(*) propcnt  \n";
            sql += " from \n";
            sql += "   tz_propose a, vz_scsubjseq b\n";
            sql += " where \n";
            sql += "   a.subj = b.subj\n";
            sql += "   and a.subjseq = b.subjseq\n";
            sql += "   and a.year = b.year\n";
            sql += "   and b.scupperclass = '" + v_upperclass + "'\n";
            sql += "   and a.userid = '" + v_user_id + "'\n";
            sql += "   and a.chkfinal != 'N'\n";
            sql += "   and to_char(sysdate, 'yyyymmddhh') <= b.eduend\n";

            System.out.println("sql_propcnt===>" + sql);

            // pstmt1 = connMgr.prepareStatement(sql);
            pstmt2 = connMgr.prepareStatement(sql);

            rs2 = pstmt2.executeQuery();
            if (rs2.next()) {
                v_propcnt = rs2.getInt("propcnt");
            }

            if (v_propcnt >= v_maxsubjcnt) {
                chkupperclass = false;
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
            if (rs2 != null) {
                try {
                    rs2.close();
                } catch (Exception e) {
                }
            }
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
        return chkupperclass;
    }

    /**
     * offline �����Ⱓ �ߺ� üũ
     * 
     * @param box receive from the form object and session
     * @return boolean ��û�������� Return @ ��û�����ϸ� true (�ߺ��ȵ�) @ ��û�Ұ����ϸ� false(�ߺ���)
     */
    public boolean overLapEduterm(String edustart, String eduend, String userid) throws Exception {
        DBConnectionManager connMgr = null;
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        // ResultSet rs1 = null;
        ResultSet rs1 = null;

        String sql = "";

        int cnt = 0;
        boolean chkseq = true;

        try {
            connMgr = new DBConnectionManager();

            sql += " select \n";
            sql += "   count(subj) cnt \n";
            sql += " from \n";
            sql += "   tz_student a\n";
            sql += " where \n";
            sql += "   userid = '" + userid + "'\n";
            sql += "   and (select isonoff from tz_subj where subj = a.subj and subjseq = a.subjseq and year = a.year ) = 'OFF'\n";
            sql += "   and to_char(sysdate, 'yyyymmddhh') <= eduend \n";
            sql += "   and chkfinal = 'Y'\n";
            sql += "   and (\n";
            sql += "       (edustart <= '" + eduend + "' and eduend >= '" + eduend + "') or \n";
            sql += "    (edustart <= '" + edustart + "' and eduend >= '" + edustart + "') or\n";
            sql += "    (edustart >= '" + edustart + "' and eduend <= '" + eduend + "')\n";
            sql += "    )\n";

            // System.out.println("OFF�ߺ��Ⱓ2="+sql);

            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {
                cnt = rs1.getInt("cnt");
                if (cnt > 0) {
                    chkseq = false;
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        }

        finally {
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

        return chkseq;
    }

    /**
     * offline �����Ⱓ �ߺ� üũ
     * 
     * @param box receive from the form object and session
     * @return boolean ��û�������� Return @ ��û�����ϸ� true (�ߺ��ȵ�) @ ��û�Ұ����ϸ� false(�ߺ���)
     */
    public String ziphapOverLapEduterm(String edustart, String eduend, String userid) throws Exception {
        DBConnectionManager connMgr = null;
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        // ResultSet rs1 = null;
        ResultSet rs1 = null;

        String sql = "";

        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "0";

        try {
            connMgr = new DBConnectionManager();

            sql += " select \n";
            sql += "   count(subj) cnt \n";
            sql += " from \n";
            sql += "   vz_student a\n";
            sql += " where \n";
            sql += "   userid = '" + userid + "'\n";
            sql += "   and (select isonoff from tz_subj where subj = a.subj and subjseq = a.subjseq and year = a.year ) = 'OFF'\n";
            sql += "   and to_char(sysdate, 'yyyymmddhh') <= eduend \n";
            sql += "   and chkfinal = 'Y'\n";
            sql += "   and (\n";
            sql += "       (edustart <= '" + eduend + "' and eduend >= '" + eduend + "') or \n";
            sql += "    (edustart <= '" + edustart + "' and eduend >= '" + edustart + "') or\n";
            sql += "    (edustart >= '" + edustart + "' and eduend <= '" + eduend + "')\n";
            sql += "    )\n";

            // System.out.println("OFF�ߺ��Ⱓ2="+sql);

            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {
                cnt = rs1.getInt("cnt");
                if (cnt > 0) {
                    errvalue = "6";
                } else {
                    errvalue = "0";
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        }

        finally {
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

        return errvalue;
    }

    /**
     * ������û �������� ����
     * 
     * @param box receive from the form object and session
     * @return int ��û�������� ���� Return Return : boolean true : ���� false : �Ұ���
     */
    public boolean possibleSeqStatus(RequestBox box, String subj, String subjseq, String gyear, String userid, String comp) throws Exception {
        DBConnectionManager connMgr = null;
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        // ResultSet rs1 = null;
        ListSet ls1 = null;

        String sql = "";
        String v_user_id = userid;
        String v_comp = comp;
        String v_gyear = gyear;

        String v_subj = "";
        String v_subjseq = "";
        String v_year = "";
        String v_edustart = "";
        String v_eduend = "";
        String v_isonoff = "";
        String v_isedutarget = "";
        String v_mastercd = "";
        String v_proposetype = "";

        // System.out.println(subj);
        // System.out.println(v_user_id);
        // System.out.println(v_comp);
        // System.out.println(v_gyear);

        int cnt = 0;
        boolean chkseq = false;
        // boolean ismastersubj = false;
        boolean isedutarget = false;
        boolean ismasterpropose = false;

        v_comp = v_comp.substring(0, 4);

        SubjComBean csBean = new SubjComBean();

        try {
            connMgr = new DBConnectionManager();

            // sql1 = " select count(*) cnt from TZ_POLLSEL  ";

            sql += " select  \n";
            sql += "   a.edustart,a.eduend, a.year, a.subjseq, a.subjnm, a.isonoff , b.mastercd, b.isedutarget, b.proposetype \n";
            sql += " from  \n";
            sql += "   VZ_SCSUBJSEQ a, VZ_MASTERSUBJSEQ b \n";
            sql += " where \n";
            sql += "   a.grcode in (select grcode from TZ_GRCOMP where comp like '" + v_comp + "%') \n";
            sql += "   and a.gyear = '" + v_gyear + "' \n";
            sql += "   and a.scsubj = '" + subj + "' \n";
            sql += "   and a.scsubjseq = '" + subjseq + "' \n";
            sql += "   and a.seqvisible = 'Y' \n";
            sql += "   and a.scsubj = b.subj*      \n";
            sql += "   and a.scsubjseq = b.subjseq* \n";
            sql += "   and a.year = b.year* \n"; // /���������� ����

            // /���������� ����
            // /������û�Ⱓ ��������
            sql += "   and  case a.studentlimit  when 0 then 1000000  else a.studentlimit end  > (select count(userid) from tz_propose where subj = a.subj and subjseq = a.subjseq and year = a.year and (isproposeapproval !='N'and chkfinal != 'N')) \n";
            sql += "   and to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend \n";

            // ////////////////////////////////////////////
            // // 1
            // //�̹̽�û�Ѱ����� �ٽ� ������û�Ҽ� ����.
            // ////////////////////////////////////////////
            sql += "   and (a.subj) not in ( \n";
            sql += "   select\n";
            sql += "     x.subj\n";
            sql += "   from\n";
            sql += "     tz_propose x \n";
            sql += "   where\n";
            sql += "     (x.userid = '" + v_user_id + "' and x.subj = '" + subj + "' and (x.chkfinal != 'N' and x.isproposeapproval != 'N') )\n";
            // sql+=
            // "     or  (select isgraduated from tz_stold where userid = '"+userid+"' and subj = '"+subj+"') = 'Y'\n";
            sql += ") \n";

            // ////////////////////////////////////////////
            // // 2
            // //�����Ѱ����� �ٽ� ������û�Ҽ� ����.
            // ////////////////////////////////////////////
            sql += "   and (a.subj) not in ( \n";
            sql += "   select\n";
            sql += "     x.subj\n";
            sql += "   from\n";
            sql += "     tz_stold x \n";
            sql += "   where\n";
            sql += "     (x.userid = '" + v_user_id + "' and x.subj = '" + subj + "' and x.isgraduated = 'Y')\n";
            sql += ") \n";

            System.out.println("��û�������� ���� �⺻ ���� 1=" + subjseq + "::::" + sql);

            ls1 = connMgr.executeQuery(sql);

            while (ls1.next()) {

                chkseq = true;

                v_subj = subj;
                v_subjseq = ls1.getString("subjseq");
                v_year = ls1.getString("year");
                v_edustart = ls1.getString("edustart");
                v_eduend = ls1.getString("eduend");
                v_isonoff = ls1.getString("isonoff");
                v_isedutarget = ls1.getString("isedutarget"); // ����ڸ� ������û �Ҽ�
                // �ִ���
                v_mastercd = ls1.getString("mastercd"); // �����Ͱ��� �ڵ�� ������ �Ǿ� �ִ���.
                v_proposetype = ls1.getString("proposetype");

                // /////////////////////////////////////////////////////////////
                // 2. �ش������ off line�����϶� �н������� �ߺ��Ⱓ üũ
                // /////////////////////////////////////////////////////////////
                if (v_isonoff.equals("OFF") && !(v_proposetype.equals("3") || v_proposetype.equals("2"))) {
                    chkseq = overLapEduterm(v_edustart, v_eduend, v_user_id);
                }

                // /////////////////////////////////////////////////////////////
                // 3. �ش������ �����Ͱ����϶� ��û����/����ڿ��� üũ
                // /////////////////////////////////////////////////////////////
                if (chkseq && !v_mastercd.equals("")) {
                    ismasterpropose = csBean.IsMasterPropose(v_mastercd, v_user_id);

                    if (ismasterpropose) { // ��û�� ���� �ʾҴ�

                        isedutarget = csBean.IsEduTarget(v_subj, v_year, v_subjseq, v_user_id); // ����ڷ�
                        // ������
                        // �Ǿ��ִ�
                        // ������

                        if (v_isedutarget.equals("Y")) { // �ش縶���Ͱ����� ����� ����
                            // �������̸�

                            if (isedutarget) { // ������ �����Ͱ����� ���ε� ����ڶ��
                                chkseq = true;
                            } else {
                                chkseq = false; // ����ڰ� �ƴ϶��
                            }
                        } else { // ����� ���������� �ƴϸ� ��û�����ϴ�.
                            chkseq = true;
                        }

                    } else { // ��û�� �ߴ�
                        chkseq = false;
                    }

                }
                System.out.println("������û����???????====>" + chkseq);
                // ��û������ �����̸�
                if (chkseq) {
                    cnt++;
                }
            }

            if (cnt > 0) {
                chkseq = true;
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
        return chkseq;
    }

    /**
     * ������û �������� ����
     * 
     * @param box receive from the form object and session
     * @return int ��û�������� ���� Return Return : first + second + third first :
     *         ������û���ɿ��� 0 : �Ұ��� , 1 : ���� second : ����ڰ������� 0 : ������ , 1 : �Ϲݰ���
     *         third : ����ڿ���
     */
    public String possibleSeqStatus_old(RequestBox box, String subj, String subjseq, String gyear, String userid, String comp) throws Exception {
        DBConnectionManager connMgr = null;
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;

        String sql = "";
        // String sql1 = "";
        String v_user_id = userid;
        String v_comp = comp;
        String v_gyear = gyear;
        String cnt = "";
        // String ispropose = "";

        v_comp = v_comp.substring(0, 4);

        if (v_user_id.equals("")) {
            v_user_id = box.getSession("userid");
        }

        if (v_comp.equals("")) {
            v_comp = box.getSession("comp");
            if (!v_comp.equals("")) {
                v_comp = v_comp.substring(0, 4);
            }
        }

        try {
            connMgr = new DBConnectionManager();

            sql += " select  ";
            sql += "   count(subjseq)  cnt ";
            sql += " from  ";
            sql += "   VZ_SCSUBJSEQ a ";
            sql += " where ";
            sql += "   grcode in (select grcode from TZ_GRCOMP where comp like '" + v_comp + "%') ";
            sql += "   and a.gyear = '" + v_gyear + "' ";
            sql += "   and a.scsubjseq = '" + subjseq + "' ";
            sql += "   and a.scsubj = '" + subj + "' ";
            // sql+=
            // "   and a.studentlimit >= (select count(userid) from tz_propose where a.subj = subj and a.subjseq = subjseq and a.year = year and (isproposeapproval !='N'or chkfinal != 'N') and userid= '"+userid+"') ";
            sql += "   and  case a.studentlimit  when 0 then 1000000  else a.studentlimit end  > (select count(userid) from tz_propose where subj = a.subj and subjseq = a.subjseq and year = a.year and (isproposeapproval !='N'and chkfinal != 'N')) ";
            sql += "   and to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend ";

            // //�̹̽�û�Ѱ����� �ٽ� ������û�Ҽ� ����.
            sql += "   and (subj,subjseq,year) not in ( ";
            sql += "     select  ";
            sql += "       subj, subjseq, year  ";
            sql += "     from  ";
            sql += "       tz_propose  ";
            sql += "     where  ";
            sql += "       userid = '" + userid + "'  ";
            sql += "       and subj = a.scsubj  ";
            // sql+= "       and subjseq = a.scsubjseq  ";
            // sql+= "       and year = a.year  ) ";
            // sql+= " and year = a.year
            sql += "     ) ";

            // //������ΰ�� ��û�� �������� (�����Ͱ����� ���ε� ��� ������ �Ѱ����� ��û�ϸ� ��� ������ �ٽý�û�Ҽ� ����.)
            sql += "   and (subj,subjseq,year)not in (        ";
            sql += "     select          ";
            sql += "       b.subj,          ";
            sql += "       b.subjseq,        ";
            sql += "       b.year        ";
            sql += "     from           ";
            sql += "       tz_edutarget a,           ";
            sql += "       vz_mastersubjseq b        ";
            sql += "     where           ";
            sql += "       a.userid = '" + userid + "' ";
            sql += "       and b.isedutarget = 'Y' ";
            sql += "       and a.mastercd = b.mastercd ";
            sql += "       and b.grcode in (select grcode from TZ_GRCOMP where comp like '" + v_comp + "%')      ";
            sql += "       and (b.mastercd) in(       ";
            sql += "         select               ";
            sql += "           y.mastercd           ";
            sql += "         from               ";
            sql += "           tz_propose x, vz_mastersubjseq y            ";
            sql += "         where              ";
            sql += "           x.userid='" + userid + "' ";
            sql += "           and y.grcode in (select grcode from TZ_GRCOMP where comp like '" + v_comp + "%')           ";
            sql += "           and x.subj = y.subj            ";
            sql += "           and x.subjseq = y.subjseq   ";
            sql += "           and x.year = y.year ";
            sql += "           and (x.isproposeapproval != 'N'  and y.propend >= '" + FormatDate.getDate("yyyyMMddHH") + "') ";
            sql += ")            ) ";

            // //����ڰ� ���� �����Ͱ��� ���� ��û�� ���
            sql += "   and (subj,subjseq,year)not in ( ";
            sql += "     select             ";
            sql += "     subj,           ";
            sql += "     subjseq,         ";
            sql += "     year          ";
            sql += "   from            ";
            sql += "     vz_mastersubjseq          ";
            sql += "   where ";
            sql += "     grcode in (select grcode from TZ_GRCOMP where comp like '" + v_comp + "%') ";
            sql += "     and gyear = a.gyear  ";
            sql += "     and (mastercd) in ";
            sql += "     (select              ";
            sql += "        y.mastercd         ";
            sql += "      from              ";
            sql += "        tz_propose x, vz_mastersubjseq y           ";
            sql += "      where              ";
            sql += "        x.userid='" + userid + "' ";
            sql += "        and y.grcode in (select grcode from TZ_GRCOMP where comp like '" + v_comp + "%')              ";
            sql += "        and x.subj = y.subj              ";
            sql += "        and x.subjseq = y.subjseq          ";
            sql += "        and x.year = y.year             ";
            // sql+= "        and x.cancelkind = 'P' or            ";
            sql += "        and (x.isproposeapproval != 'N'  and y.propend >= '" + FormatDate.getDate("yyyyMMddHH") + "') ";
            sql += ")            ) ";

            // �����Ͱ����� ����ڸ����̵Ǿ� ���� ���� ��� �����Ͱ����� ���ε� �������� ������û�Ҽ� ����.
            sql += "   and (subj,subjseq,year) not in(  ";
            sql += "   select  ";
            sql += "     subj,subjseq,year ";
            sql += "   from  ";
            sql += "     vz_mastersubjseq   ";
            sql += "   where ";
            sql += "     subj = a.subj ";
            sql += "     and subjseq = a.subjseq ";
            sql += "     and year = a.year ";
            sql += "     and isedutarget = 'Y' ";
            sql += "     and mastercd not in(select mastercd from tz_edutarget where userid = '" + userid + "') ";
            sql += "   ) ";
            // System.out.println(sql);

            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {
                cnt = rs1.getString("cnt");
                if (cnt.equals("1")) {
                    cnt = "Y";
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return cnt;
    }

    /**
     * ������û���� ��������(������û ���� --- ����������������)
     * 
     * @param box receive from the form object and session
     * @param returnvalue ==>1:������û���� 2: ������û����+����������翩�� 3 :
     *        ������û����+����������翩��+�������ο���
     * @return String
     */
    public String getPropseStatus(RequestBox box, String subj, String subjseq, String year, String userid, String returnvalue) throws Exception {
        DBConnectionManager connMgr = null;
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        ListSet ls1 = null;

        String sql = "";
        String v_user_id = userid;
        String return_value = returnvalue;
        String ispropose = "";
        String chkfinal = "";

        if (v_user_id.equals("")) {
            v_user_id = box.getSession("userid");
        }

        try {
            connMgr = new DBConnectionManager();
            // sql =
            // " select decode(userid,'"+v_user_id+"', 'Y', 'N') ispropose, isproposeapproval, chkfinal from TZ_PROPOSE ";
            sql = " select case userid  when '" + v_user_id + "'  then  'Y'  else   'N'  end  as  ispropose, chkfinal from TZ_PROPOSE ";
            sql += " where subj = '" + subj + "' and subjseq = '" + subjseq + "' and year = '" + year + "' and userid = '" + v_user_id + "'";
            System.out.println("sql=" + sql);
            // pstmt1 = connMgr.prepareStatement(sql);
            // rs1 = pstmt1.executeQuery();

            ls1 = connMgr.executeQuery(sql);

            if (ls1.next()) {
                ispropose = ls1.getString("ispropose");
                chkfinal = ls1.getString("chkfinal");
            }

            if (ispropose.equals("")) {
                ispropose = "N";
            }
            if (chkfinal.equals("")) {
                chkfinal = "0";
            }

            if (return_value.equals("1")) {
                return_value = ispropose;
            } else if (return_value.equals("3")) {
                return_value = ispropose + chkfinal;
            } else {
                return_value = ispropose;
            }

            // System.out.println("return_value="+return_value);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
        return return_value;
    }

    /**
     * ���� ���� ����
     * 
     * @param box receive from the form object and session
     * @return DataBox
     */
    public DataBox SubjseqInfo(String subj, String grcode, String subjseq, String year) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            dbox = SubjseqInfo(connMgr, subj, grcode, subjseq, year);

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
        return dbox;
    }

    /**
     * ���� ���� ����
     * 
     * @param box receive from the form object and session
     * @return DataBox
     */
    public DataBox SubjseqInfo(DBConnectionManager connMgr, String subj, String grcode, String subjseq, String year) throws Exception {
        ListSet ls1 = null;
        DataBox dbox = null;

        String sql1 = "";

        String v_eduterm = "";
        // String v_grdappstatus = ""; //������λ���

        SubjComBean scbean = new SubjComBean();
        // ChiefApprovalBean caBean = new ChiefApprovalBean();

        try {
            // if(ss_action.equals("go")){
            sql1 += " select            \n";
            sql1 += "    A.isonoff,     \n";
            sql1 += "    A.subjseqgr,   \n";
            sql1 += "    A.isclosed,    \n";
            sql1 += "    A.edustart,    \n";
            sql1 += "    A.eduend,      \n";
            sql1 += "     case a.studentlimit  when 0 then 1000000  else a.studentlimit end   as  studentlimit, \n";
            sql1 += "    (select count(userid) from tz_propose where a.subj = subj and a.subjseq = subjseq and a.year = year) propcnt,\n";
            sql1 += "    a.iscpflag     \n";
            sql1 += " from              \n";
            sql1 += "  VZ_SCSUBJSEQ A   \n";
            sql1 += " where             \n";
            sql1 += "  A.subj = '" + subj + "' \n";
            sql1 += "  and A.grcode = " + SQLString.Format(grcode) + " \n";
            sql1 += "  and A.subjseq = " + SQLString.Format(subjseq) + " \n";
            sql1 += "  and A.year    = " + SQLString.Format(year) + " \n";
            sql1 += "  order by subjseq";

            System.out.println(sql1);

            ls1 = connMgr.executeQuery(sql1);

            v_eduterm = scbean.getEduTerm(subj, subjseq, year);
            // v_grdappstatus = caBean.getApprovalStatus(subj,year,subjseq,"4");

            if (ls1.next()) {
                dbox = ls1.getDataBox();
                dbox.put("d_eduterm", v_eduterm); // ���� �����Ⱓ
                // dbox.put("d_grdappstatus", v_grdappstatus); //����ó�� �������
            }
            // }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
        }
        return dbox;
    }

    /**
     * subj ���� ���� üũ
     * 
     * @param subj
     * @return String Return
     */
    public String isExitSubj(String subj) throws Exception {
        DBConnectionManager connMgr = null;
        String errvalue = "";

        try {
            connMgr = new DBConnectionManager();
            errvalue = isExitSubj(connMgr, subj);
        }

        catch (Exception ex) {
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

        return errvalue;
    }

    /**
     * subj ���� ���� üũ
     * 
     * @param subj
     * @return String Return
     */
    public String isExitSubj(DBConnectionManager connMgr, String subj) throws Exception {
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;

        String sql = "";

        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {
            sql += "select count(*) CNT from tz_subj where subj = '" + subj + "' \n";
            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();

            if (rs1.next()) {
                cnt = rs1.getInt("CNT");
                if (cnt > 0) {
                    errvalue = "0"; // ����
                } else {
                    errvalue = "12"; // �λ�DB�� �������� �ʽ��ϴ�.
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        }

        finally {
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

        return errvalue;
    }

    /**
     * ���� ���� ���� üũ
     * 
     * @param box receive from the form object and session
     * @return String Return
     */
    public String isExitSeq(String grcode, String gyear, String subj, String subjseq) throws Exception {
        DBConnectionManager connMgr = null;
        String errvalue = "";

        try {
            connMgr = new DBConnectionManager();
            errvalue = isExitSeq(connMgr, grcode, gyear, subj, subjseq);
        }

        catch (Exception ex) {
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

        return errvalue;
    }

    /**
     * ���� ���� ���� üũ
     * 
     * @param box receive from the form object and session
     * @return String Return
     */
    public String isExitSeq(DBConnectionManager connMgr, String grcode, String gyear, String subj, String subjseq) throws Exception {
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        // ResultSet rs1 = null;
        ResultSet rs1 = null;

        String sql = "";

        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {

            sql += "select count(scsubjseq) CNT  from vz_scsubjseq where grcode = '" + grcode + "' and gyear = '" + gyear + "' and subj = '" + subj + "' and subjseq = '" + subjseq + "' \n";
            System.out.println("sql_seqchk==" + sql);

            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {
                cnt = rs1.getInt("CNT");
                if (cnt > 0) {
                    errvalue = "0"; // ����.
                } else {
                    errvalue = "5"; // ����������
                }

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        }

        finally {
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

        return errvalue;
    }

    /**
     * ���� ���� ���� üũ
     * 
     * @param box receive from the form object and session
     * @return String Return
     */
    public String isStudent(String grcode, String gyear, String subj, String subjseq) throws Exception {
        DBConnectionManager connMgr = null;
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        // ResultSet rs1 = null;
        ResultSet rs1 = null;

        String sql = "";

        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {
            connMgr = new DBConnectionManager();

            sql += "select count(userid) CNT  from tz_pr where grcode = '" + grcode + "' and gyear = '" + gyear + "' and subj = '" + subj + "' and subjseq = '" + subjseq + "' \n";
            // System.out.println("sql_seqchk=="+sql);

            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {
                cnt = rs1.getInt("CNT");
                if (cnt > 0) {
                    errvalue = "0"; // ����.
                } else {
                    errvalue = "5"; // ����������
                }

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        }

        finally {
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

        return errvalue;
    }

    /**
     * ������û ���� üũ
     * 
     * @param box receive from the form object and session
     * @return String Return
     */
    public String isProposeExit(String userid, String subj, String year, String subjseq) throws Exception {

        DBConnectionManager connMgr = null;
        String errvalue = "";

        try {
            connMgr = new DBConnectionManager();
            errvalue = isProposeExit(connMgr, userid, subj, year, subjseq);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }

        finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return errvalue;
    }

    /**
     * ������û ���� üũ
     * 
     * @param box receive from the form object and session
     * @return String Return
     */
    public String isProposeExit(DBConnectionManager connMgr, String userid, String subj, String year, String subjseq) throws Exception {
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;

        String sql = "";

        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {

            sql += "select count(subjseq) CNT  from tz_propose where subj = '" + subj + "' and year = '" + year + "' and userid = '" + userid + "'and subjseq = '" + subjseq + "' \n";
            // System.out.println("sql_propchk=="+sql);

            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {

                cnt = rs1.getInt("CNT");
                if (cnt > 0) {
                    errvalue = "0"; // ����
                } else {
                    errvalue = "14"; // ������û��
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        }

        finally {
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

        return errvalue;
    }

    /**
     * ������û ���� üũ
     * 
     * @param box receive from the form object and session
     * @return String Return
     */
    public String isPropose(String userid, String subj, String year, String subjseq) throws Exception {

        DBConnectionManager connMgr = null;
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;

        String sql = "";

        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {
            connMgr = new DBConnectionManager();

            sql += "select count(subjseq) CNT  from tz_propose where subj = '" + subj + "' and year = '" + year + "' and userid = '" + userid + "'and subjseq = '" + subjseq + "' \n";
            // System.out.println("sql_propchk=="+sql);

            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {

                cnt = rs1.getInt("CNT");
                if (cnt > 0) {
                    errvalue = "7"; // ������û��
                } else {
                    errvalue = "0"; // ����
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        }

        finally {
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

        return errvalue;
    }

    /**
     * �н��� ���� üũ
     * 
     * @param box receive from the form object and session
     * @return String Return
     */
    public String isExitStudent(String userid, String subj, String year, String subjseq) throws Exception {
        DBConnectionManager connMgr = null;
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;
        String sql = "";

        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {
            connMgr = new DBConnectionManager();

            sql += "select count(subjseq) CNT  from tz_student where subj = '" + subj + "' and year = '" + year + "' and userid = '" + userid + "'and subjseq = '" + subjseq + "' \n";
            // System.out.println("sql_propchk=="+sql);

            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();

            if (rs1.next()) {
                cnt = rs1.getInt("CNT");
                if (cnt > 0) {
                    errvalue = "0"; // ����
                } else {
                    errvalue = "8"; // �н��ڰ� �ƴմϴ�
                }
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

        return errvalue;
    }

    /**
     * ���� ���� ����
     * 
     * @param box receive from the form object and session
     * @return String Return
     */
    public String isStold(String userid, String subj, String year, String subjseq) throws Exception {

        DBConnectionManager connMgr = null;
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;

        String sql = "";

        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {
            connMgr = new DBConnectionManager();

            sql += "select count(subjseq) CNT  from tz_stold where subj = '" + subj + "' and year = '" + year + "' and userid = '" + userid + "' and isgraduated = 'Y' \n";
            // System.out.println("sql_stoldchk=="+sql);

            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {
                cnt = rs1.getInt("CNT");
                if (cnt > 0) {
                    errvalue = "9"; // ����ó���� ����
                } else {
                    errvalue = "0"; // ����
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        }

        finally {
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

        return errvalue;
    }

    /**
     * Member ���� ���� üũ
     * 
     * @param box receive from the form object and session
     * @return String Return @ ��û�����ϸ� true (�ߺ��ȵ�) @ ��û�Ұ����ϸ� false(�ߺ���)
     */
    public String isExitMember(String userid, String isretired, String isemtpty, String isstoped) throws Exception {
        DBConnectionManager connMgr = null;
        // DataBox dbox= null;
        ListSet ls1 = null;

        String sql = "";

        // int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {
            connMgr = new DBConnectionManager();

            sql += "select state from tz_member where userid = '" + userid + "' ";
            // System.out.println(sql);

            ls1 = connMgr.executeQuery(sql);

            if (ls1.next()) {
                if (ls1.getString("state").equals("N")) {
                    errvalue = "2"; // Ż�����Դϴ�.
                } else {
                    errvalue = "0"; // ����
                }
            } else {
                errvalue = "1"; // ȸ��DB�� �������� �ʽ��ϴ�.
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        }

        finally {
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

        return errvalue;
    }

    /**
     * Member ���� ���� üũ
     * 
     * @param userid USER ID
     * @return String Return @ ��û�����ϸ� true (�ߺ��ȵ�) @ ��û�Ұ����ϸ� false(�ߺ���)
     */
    public String isExitMember(String userid) throws Exception {
        DBConnectionManager connMgr = null;
        String errvalue = "";
        try {
            connMgr = new DBConnectionManager();
            errvalue = isExitMember(connMgr, userid);
        } catch (Exception ex) {
        }

        finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return errvalue;
    }

    /**
     * Member ���� ���� üũ
     * 
     * @param userid USER ID
     * @return String Return @ ��û�����ϸ� true (�ߺ��ȵ�) @ ��û�Ұ����ϸ� false(�ߺ���)
     */
    public String isExitMember(DBConnectionManager connMgr, String userid) throws Exception {
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;
        String sql = "";
        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {
            sql += "select count(*) CNT from tz_member where userid = '" + userid + "' \n";
            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();

            if (rs1.next()) {
                cnt = rs1.getInt("CNT");
                if (cnt > 0) {
                    errvalue = "0"; // ����
                } else {
                    errvalue = "1"; // �λ�DB�� �������� �ʽ��ϴ�.
                }
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
        return errvalue;
    }

    /**
     * Ʃ�� ���� ���� üũ
     * 
     * @param box receive from the form object and session
     * @return String Return
     */
    public String isExitTutor(String tutorid) throws Exception {
        DBConnectionManager connMgr = null;
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;

        String sql = "";

        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {
            connMgr = new DBConnectionManager();

            sql += "select count(*) CNT from tz_tutor where userid = '" + tutorid + "' ";
            // System.out.println(sql);

            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {
                cnt = rs1.getInt("CNT");
                if (cnt > 0) {
                    errvalue = "0"; // ����
                } else {
                    errvalue = "11"; // ���������� �������� �ʽ��ϴ�.
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        }

        finally {
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

        return errvalue;
    }

    /**
     * �����������ߺ����翩��
     * 
     * @param box receive from the form object and session
     * @return String Return
     */
    public String isExitDupli(String inputtime, String userid, String subj, String year, String subjseq) throws Exception {
        DBConnectionManager connMgr = null;
        String errvalue = "";

        try {
            connMgr = new DBConnectionManager();
            errvalue = isExitDupli(connMgr, inputtime, userid, subj, year, subjseq);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }

        finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return errvalue;
    }

    /**
     * �����������ߺ����翩��
     * 
     * @param box receive from the form object and session
     * @return String Return
     */
    public String isExitDupli(DBConnectionManager connMgr, String inputtime, String userid, String subj, String year, String subjseq) throws Exception {
        // DataBox dbox= null;
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;

        String sql = "";

        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {

            sql += "select count(*) CNT from tz_cpexceltemp where ";
            sql += " inputtime = '" + inputtime + "' ";
            sql += " and subj    = '" + subj + "' ";
            sql += " and subjseq = '" + subjseq + "' ";
            sql += " and year    = '" + year + "' ";
            sql += " and lower(userid)  = lower('" + userid + "') ";
            System.out.println(sql);

            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {
                cnt = rs1.getInt("CNT");
                System.out.println("cnt===>>>" + cnt);
                if (cnt > 0) {
                    errvalue = "13"; // ���������Ϳ� �̹� �����մϴ�.
                } else {
                    errvalue = "0"; // ����
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        }

        finally {
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

        return errvalue;
    }

    /**
     * ���� �޼���
     * 
     * @param String ������
     * @return String Return ���� �޼���
     */
    public String isGetErrtxt(String errvalue) throws Exception {

        String errtxt = "";

        if (errvalue.equals("0")) {
            errtxt = "�����Է�.";
        }

        else if (errvalue.equals("1")) {
            errtxt = "ȸ��DB�� �������� �ʽ��ϴ�.";
        } else if (errvalue.equals("2")) {
            errtxt = "Ż�����Դϴ�."; //
        } else if (errvalue.equals("3")) {
            errtxt = "�������Դϴ�."; //
        } else if (errvalue.equals("4")) {
            errtxt = "�������Դϴ�.";
        } else if (errvalue.equals("5")) {
            errtxt = "�̰������� �Դϴ�.";
        } else if (errvalue.equals("6")) {
            errtxt = "�ߺ��Ǵ� ���հ����� �ֽ��ϴ�..";
        } else if (errvalue.equals("7")) {
            errtxt = "��û������ �̹� �����մϴ�.";
        } else if (errvalue.equals("8")) {
            errtxt = "�н��ڰ� �ƴմϴ�";
        } else if (errvalue.equals("9")) {
            errtxt = "�̹� ����ó�� �Ǿ����ϴ�.";
        } else if (errvalue.equals("9")) {
            errtxt = "���������� �������� �ʽ��ϴ�.";
        } else if (errvalue.equals("10")) {
            errtxt = "�̹� �������� ����Ʈ�� ��� �Ǿ��ֽ��ϴ�.";
        } else if (errvalue.equals("12")) {
            errtxt = "�����ڵ尡 �������� �ʽ��ϴ�.";
        } else if (errvalue.equals("13")) {
            errtxt = "���������Ϳ� �̹� �����մϴ�.";
        } else if (errvalue.equals("14")) {
            errtxt = "�н��������� �������� �ʽ��ϴ�.";
        } else if (errvalue.equals("22")) {
            errtxt = "����ó���� �Ϸ�� �����Դϴ�.";
        } else if (errvalue.equals("23")) {
            errtxt = "�̹� ����� �Ϸ�� �����Դϴ�.";
        } else if (errvalue.equals("24")) {
            errtxt = "���Ῡ�� �����Դϴ�.";
        } else if (errvalue.equals("25")) {
            errtxt = "������� �����Դϴ�.";
        } else if (errvalue.equals("31")) {
            errtxt = "���������� ��ġ���� �ʽ��ϴ�.";
        } else if (errvalue.equals("32")) {
            errtxt = "�̹� ����ó���� �Ϸ�Ǿ����ϴ�.";
        } else if (errvalue.equals("33")) {
            errtxt = "�̹� �������� �Ϸ�Ǿ����ϴ�.";
        } else if (errvalue.equals("34")) {
            errtxt = "���� �����Ⱓ�� �ƴմϴ�.";
        } else if (errvalue.equals("35")) {
            errtxt = "������ ����������� �ʾҽ��ϴ�.";
        } else if (errvalue.equals("999")) {
            errtxt = "DBó�� �� ������ �߻��Ͽ����ϴ�.";
        } else {
            errtxt = "DBó�� �� ������ �߻��Ͽ����ϴ�.";
        }

        return errtxt;
    }

    /**
     * ���������� �α�˻��� �ڵ�
     */
    @SuppressWarnings("unchecked")
    public ArrayList Selectpopular(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = "  select code from tz_code            ";
            sql += "   where gubun  = '0109'";
            sql += "order by ldate";

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
     * 
     * ���������� �α�˻��� ���س�¥
     */
    public String Selectppldate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        connMgr = new DBConnectionManager();

        ListSet ls = null;
        String ppldate = "";

        String sql = "";

        try {
            sql = "select max(ldate) as ldate from tz_code where gubun = '0109'";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                ppldate = ls.getString("ldate");
            }
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return FormatDate.getFormatDate(ppldate, "yyyy.MM.dd");
    }

}