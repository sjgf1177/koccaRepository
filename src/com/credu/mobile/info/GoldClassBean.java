/**
 * 프로젝트명 : kocca_java
 * 패키지명 : com.credu.mobile.info
 * 파일명 : GoldClassBean.java
 * 작성날짜 : 2011. 10. 16.
 * 처리업무 :
 * 수정내용 :
 
 * Copyright by CREDU.Co., LTD. ALL RIGHTS RESERVED.   
 */

package com.credu.mobile.info;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.DatabaseExecute;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

public class GoldClassBean {
    private StringBuffer strQuery = null;
    private int x = 1;
    // private String tem_grcode = "";
    private int row;
    private ConfigSet config;

    public GoldClassBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); // 이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 이달의 골드 클래스 목록
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList getGoldClassList(RequestBox box) throws Exception {
        DatabaseExecute connMgr = null;

        ArrayList list = null;
        strQuery = new StringBuffer();

        try {
            String v_gugun = box.getStringDefault("p_gubun", "nowMonth");
            String v_genre = box.getString("p_genre");
            connMgr = new DatabaseExecute();

            strQuery.append("select \n");
            strQuery.append("    a.seq, a.lecnm, a.tutornm, a.mobile_url \n");
            strQuery.append("from \n");
            strQuery.append("    tz_goldclass a \n");
            strQuery.append("where   \n");
            strQuery.append("    a.useyn = 'Y' \n");
            if (!"".equals(v_genre)) {
                strQuery.append("    and a.genre = " + StringManager.makeSQL(v_genre) + " \n");
            }
            strQuery.append("order by ldate desc \n");

            strQuery.setLength(0);
            strQuery.append("SELECT  A.SEQ                  \n");
            strQuery.append("   ,    A.LECNM                \n");
            strQuery.append("   ,    A.TUTORNM              \n");
            strQuery.append("   ,    A.MOBILE_URL           \n");
            strQuery.append("  FROM  TZ_GOLDCLASS A         \n");
            strQuery.append("   ,    TZ_GOLDCLASSMOBILE B   \n");
            strQuery.append(" WHERE  A.USEYN = 'Y'          \n");
            strQuery.append("   AND  A.SEQ = B.SEQ          \n");
            strQuery.append(" GROUP  BY A.SEQ               \n");
            strQuery.append("   ,    A.LECNM                \n");
            strQuery.append("   ,    A.TUTORNM              \n");
            strQuery.append("   ,    A.MOBILE_URL           \n");
            
            if (!"".equals(v_genre)) {
                strQuery.append("   AND  A.GENRE = '").append( v_genre ).append("' \n");
            }
            
            strQuery.append(" ORDER  BY A.SEQ DESC          \n");


            if ("oldMonth".equals(v_gugun)) {
                box.put("p_isPage", new Boolean(true));
                box.put("p_row", new Integer(row));
            }

            list = connMgr.executeQueryList(box, strQuery.toString());

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
            throw new Exception("sql = " + strQuery + "\r\n" + ex.getMessage());
        }

        return list;
    }

    /**
     * 전문가 특강 클래스 상세 보기
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public DataBox getGoldClassData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        DataBox dbox = null;

        strQuery = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            int v_seq = box.getInt("p_seq");

            strQuery.append("select \n");
            strQuery.append("    a.seq, a.lecnm, a.tutornm, a.lectime, a.creator, a.intro, a.width_s, a.height_s, a.viewcnt, a.vodurl, a.indate, a.tutorimg, a.mobile_url, \n");
            strQuery.append("    nvl(b.checkpoin, 0) as checkpoin \n");
            strQuery.append("from \n");
            strQuery.append("    tz_goldclass a, (select s1.seq, avg(checkpoin) as checkpoin from tz_goldclassrepl s1 where s1.seq = ? group by s1.seq) b \n");
            strQuery.append("where \n");
            strQuery.append("    a.seq = b.seq(+) \n");
            strQuery.append("    and a.seq = ? \n");

            x = 1;
            pstmt = connMgr.prepareStatement(strQuery.toString());
            pstmt.setInt(x++, v_seq);
            pstmt.setInt(x++, v_seq);

            ls = new ListSet(pstmt);
            ls.executeQuery();

            if (ls.next())
                dbox = ls.getDataBox();

        } catch (SQLException e) {
            ErrorManager.getErrorStackTrace(e, box, strQuery.toString());
            throw new Exception("\n SQL : [\n" + strQuery.toString() + "]\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } catch (Exception e) {

            ErrorManager.getErrorStackTrace(e);
            throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    pstmt = null;
                } catch (Exception e) {
                }
            }
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e) {
                }
            }

        }

        return dbox;
    }

    /**
     * 전문가 특강 클래스 모바일 차시 보기
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> getGoldClassMobileList(RequestBox box) throws Exception {
        DatabaseExecute connMgr = null;
        ArrayList list = null;

        strQuery = new StringBuffer();

        try {
            connMgr = new DatabaseExecute();

            int v_seq = box.getInt("p_seq");

            strQuery.append("select \n");
            strQuery.append("    num, mobileurl, content \n");
            strQuery.append("    from tz_goldclassmobile \n");
            strQuery.append("    where \n");
            strQuery.append("    seq =  " + SQLString.Format(v_seq) + " \n");

            list = connMgr.executeQueryList(box, strQuery.toString());

            /*
             * ls = connMgr.executeQuery(strQuery.toString());
             * 
             * if(ls.next()) { dbox = ls.getDataBox(); list.add(dbox); }
             */

        } catch (SQLException e) {
            ErrorManager.getErrorStackTrace(e, box, strQuery.toString());
            throw new Exception("\n SQL : [\n" + strQuery.toString() + "]\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } catch (Exception e) {

            ErrorManager.getErrorStackTrace(e);
            throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        }

        return list;
    }
}
