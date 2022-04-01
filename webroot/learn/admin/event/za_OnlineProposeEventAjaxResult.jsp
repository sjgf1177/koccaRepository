<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.DBConnectionManager" %>
<%@ page import="com.credu.library.ListSet" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="org.json.simple.JSONObject" %>

<%
    JSONObject jsonObj = new JSONObject();
    String actType = request.getParameter("act_type");
    String level = request.getParameter("level");

    int winnerCount = Integer.parseInt( (String) request.getParameter("winner_cnt") );
    int remainCount = Integer.parseInt( (String) request.getParameter("remain_cnt") );

    ArrayList<DataBox> prizeWinnerList = new ArrayList<DataBox>();
    HashMap<String, String> resultMap = new HashMap<String, String>();

    try {

        if ( actType.equals("1") ) {
            resultMap = this.doLuckyDraw(level, winnerCount, remainCount) ;

            if (resultMap == null ) {
                jsonObj.put("result", "null");
            } else {
                jsonObj.put("result", resultMap);
            }

            System.out.println(jsonObj.toJSONString().replace("\\\\", "/"));

        } else if ( actType.equals("2") ) {
            prizeWinnerList = this.selectPrizeWinnersList(level);

            if (prizeWinnerList == null && prizeWinnerList.size() > 0 ) {
                jsonObj.put("result", "null");
            } else {
                jsonObj.put("prizeWinnerList", prizeWinnerList);
            }

            System.out.println(jsonObj.toJSONString().replace("\\\\", "/"));
        }
    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e);
    }


    out.println( jsonObj.toJSONString().replace("\\\\", "/") );
%>

<%!
    /**
     * 당첨자 명단 목록을 조회
     */
    private ArrayList<DataBox> selectPrizeWinnersList(String level) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuilder sql = new StringBuilder();

        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        try {

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();
            sql.setLength(0);
            sql.append("SELECT  USERID              \n");
            sql.append("    ,   USERNM              \n");
            sql.append("    ,   HANDPHONE           \n");
            sql.append("    ,   EMAIL               \n");
            sql.append("    ,   WIN_LEVEL           \n");
            sql.append("  FROM  TZ_EVENT_APPLICANT  \n");
            sql.append(" WHERE  WIN_YN = 'Y'        \n");
            sql.append("   AND  SEQ = 1             \n");
            sql.append("   AND  WIN_LEVEL = ").append(level).append("   \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch(Exception e) {
            System.out.println("Exception occured while select prize winner list : " + e);
        } finally {
            if ( ls != null ) {
                ls.close();
                ls = null;
            }
            if ( connMgr != null ) {
                connMgr.freeConnection();
                connMgr = null;
            }
        }

        return list;
    }

    /**
     * 각 레벨별 추첨 기능 및 당첨 인원 조회
     *
     */
    private HashMap<String, String> doLuckyDraw(String level, int winnerCount, int remainCount) throws Exception {

        int sleepTime = 1000 / winnerCount;
        int[] randomNum = new int[winnerCount]; // 난수 저장공간
        String[] winnerNum = new String[winnerCount];
        String[] winnerUserId = new String[winnerCount];
        String[] userInfo = new String[2];

        int temp = 0;

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        StringBuilder sql = new StringBuilder();

        ArrayList<DataBox> list = null;
        HashMap<String, String> userMap = new HashMap<String, String>();
        HashMap<String, String> result = new HashMap<String, String>();

        ArrayList<DataBox> userList = null;
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.setLength(0);
            sql.append("SELECT  NUM                                 \n");
            sql.append("    ,   USERID                              \n");
            sql.append("    ,   RANK() OVER( ORDER BY NUM ) AS RNK  \n");
            sql.append("  FROM  TZ_EVENT_APPLICANT                  \n");
            sql.append(" WHERE  WIN_YN IS NULL      \n");
            sql.append("   AND  SEQ = 1             \n");
            sql.append("   AND  WIN_LEVEL IS NULL   \n");

            // ls = connMgr.executeQuery(sql.toString());

            pstmt = connMgr.prepareStatement(sql.toString());
            ls = new ListSet(pstmt);

            userList = new ArrayList<DataBox>();
            while ( ls.next() ) {

                dbox = ls.getDataBox();

                userList.add(dbox);
            }

            pstmt.close();
            pstmt = null;


            dbox = null;

            Randomer r = new Randomer(0, remainCount);

            for (int i = 0; i < winnerCount; i++) {
                Thread.sleep(sleepTime);
                temp = r.next();
                randomNum[i] = temp;

                dbox = (DataBox)userList.get(temp);

                winnerNum[i] = dbox.getString("d_num");
                winnerUserId[i] = dbox.getString("d_userid");

            }

            connMgr.setAutoCommit(false);
            sql.setLength(0);
            sql.append("UPDATE  TZ_EVENT_APPLICANT  \n");
            sql.append("   SET  WIN_YN = 'Y'    \n");
            sql.append("  ,     WIN_LEVEL = ?   \n");
            sql.append(" WHERE  SEQ = 1         \n");
            sql.append("   AND  NUM = ?         \n");
            sql.append("   AND  USERID = ?      \n");

            pstmt2 = connMgr.prepareStatement(sql.toString());
            for( int k = 0; k < winnerNum.length; k++ ) {
                pstmt2.setString(1, level);
                pstmt2.setString(2, winnerNum[k]);
                pstmt2.setString(3, winnerUserId[k]);

                pstmt2.addBatch();
            }

            int[] resultCnt = pstmt2.executeBatch();

            if ( resultCnt.length > 0 ) {
                connMgr.commit();
                result.put("result", "success");
            } else {
                result.put("result", "fail");
                connMgr.rollback();
            }

        } catch (Exception e) {
            System.out.println("Exception occured while get random number : " + e);
        } finally {
            if ( ls != null ) {
                ls.close();
                ls = null;
            }

            if ( pstmt != null ) {
                pstmt.close();
                pstmt = null;
            }

            if ( pstmt2 != null ) {
                pstmt2.close();
                pstmt2 = null;
            }

            if ( connMgr != null ) {
                connMgr.freeConnection();
                connMgr = null;
            }
        }

        return result;
    }

    class Randomer {
        int min;
        int max;
        int gap;
        boolean flags[];

        public Randomer(int max) {
            this(0, max);
        }

        public Randomer(int min, int max) {
            this.min = min;
            this.max = max;
            this.gap = (max - min);
            flags = new boolean[gap];
        }

        public int next() {
            Random random = new Random();
            int r;
            do {
                r = random.nextInt(gap);
            } while (flags[r]);
            flags[r] = true;
            return r + min;
        }
    }
%>