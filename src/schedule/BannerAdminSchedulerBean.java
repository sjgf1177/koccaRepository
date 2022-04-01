package schedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.credu.library.ErrorManager;

public class BannerAdminSchedulerBean {

    public int bannerCnt(String viewPoint) {
        int resultCnt = 0;

        return resultCnt;
    }

    /**
     * 메인 배너리스트 조회
     * 
     * @param box receive from the form object and session
     * @return list ArrayList
     * @throws Exception
     */
    public ArrayList<HashMap<String, String>> selectBannerList() throws Exception {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<HashMap<String, String>> list = null;
        HashMap<String, String> map = null;
        StringBuffer sql = new StringBuffer();

        // String dbURL = "jdbc:oracle:thin:@172.16.80.76:1521:ACADEMY";
        String dbURL = "jdbc:oracle:thin:@211.201.145.100:1521:ACADEMY";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(dbURL, "kocca", "kocca2");

            if ( conn == null ) {
                System.out.println("DB Connection fail !!");
            } else {
                System.out.println("DB Connection success !!");
            }

            list = new ArrayList<HashMap<String, String>>();

            sql.append("/* schedule.BannerAdminSchedulerBean selectBannerList (스케줄러 배너리스트 조회) */ \n");
            sql.append("SELECT  SEQ                                         \n");
            sql.append("    ,   TITLE                                       \n");
            sql.append("    ,   ONOFF_FLAG                                  \n");
            sql.append("    ,   USE_YN                                      \n");
            sql.append("    ,   SORT_ORDER                                  \n");
            sql.append("    ,   SAVE_IMG_NM                                 \n");
            sql.append("    ,   IMG_SIZE                                    \n");
            sql.append("    ,   URL                                         \n");
            sql.append("    ,   URL_TARGET                                  \n");
            sql.append("    ,   EXPLAIN                                     \n");
            sql.append("    ,   START_DT                                    \n");
            sql.append("    ,   END_DT                                      \n");
            sql.append("    ,   TO_CHAR(REG_DT, 'YYYY/MM/DD') AS REG_DT     \n");
            sql.append("    ,   REG_ID                                      \n");
            sql.append("    ,   FIXED_FLAG                                  \n");
            sql.append("  FROM  TZ_BANNER                                   \n");
            sql.append(" WHERE  TO_CHAR(SYSDATE,'YYYYMMDD') BETWEEN START_DT AND END_DT \n");
            sql.append("   AND  USE_YN = 'Y'    \n");
            sql.append(" ORDER  BY SORT_ORDER   \n");
            
            pstmt = conn.prepareStatement(sql.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                map = new HashMap<String, String>();
                map.put("seq", rs.getString("SEQ"));
                map.put("title", rs.getString("TITLE"));
                map.put("onoff_flag", rs.getString("ONOFF_FLAG"));
                map.put("use_yn", rs.getString("USE_YN"));
                map.put("sort_order", rs.getString("SORT_ORDER"));
                map.put("save_img_nm", rs.getString("SAVE_IMG_NM"));
                map.put("img_size", rs.getString("IMG_SIZE"));
                map.put("url", rs.getString("URL"));
                map.put("url_target", rs.getString("URL_TARGET"));
                map.put("explain", rs.getString("EXPLAIN"));
                map.put("start_dt", rs.getString("START_DT"));
                map.put("end_dt", rs.getString("END_DT"));
                map.put("reg_dt", rs.getString("REG_DT"));
                map.put("reg_id", rs.getString("REG_ID"));
                map.put("fixed_flag", rs.getString("FIXED_FLAG"));

                list.add(map);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (Exception e10) {
                }
            }
        }
        return list;

    }
}
