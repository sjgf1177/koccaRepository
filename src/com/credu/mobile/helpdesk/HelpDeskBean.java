package com.credu.mobile.helpdesk;

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
import com.credu.library.StringManager;

/**
 * 프로젝트명 : kocca_java 패키지명 : com.credu.mobile.helpdesk 파일명 : HelpDeskBean.java
 * 작성날짜 : 2011. 9. 24. 처리업무 : 수정내용 :
 * 
 * Copyright by CREDU.Co., LTD. ALL RIGHTS RESERVED.
 */

public class HelpDeskBean {
    private StringBuffer strQuery = null;
    private int x = 1;
    private String tem_grcode = "";
    private int row;
    private ConfigSet config;

    public HelpDeskBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); // 이 모듈의 페이지당 row 수를 셋팅한다

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 모바일 메인 공지사항
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public DataBox getMainNoticeData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        DataBox dbox = null;

        strQuery = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            String s_userid = box.getString("userid");
            tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

            strQuery.append("select \n");
            strQuery.append("    a.tabseq, a.seq, a.addate, a.adtitle, a.adname, a.cnt, a.luserid, a.ldate, a.onoffgubun, (case when onoffgubun = 'C' then '[온라인]' when onoffgubun = 'C' then '[오프라인]' else '' end) as gubunnm  \n");
            strQuery.append("from \n");
            strQuery.append("    ( \n");
            strQuery.append("        select  \n");
            strQuery.append("            a.tabseq, a.seq, a.addate, a.adtitle, a.adname, a.cnt, a.luserid, a.ldate, a.onoffgubun \n");
            strQuery.append("        from \n");
            strQuery.append("            tz_notice a \n");
            strQuery.append("        where \n");
            strQuery.append("            a.tabseq = ? \n");
            strQuery.append("            and a.useyn= 'Y' \n");
            strQuery.append("            and (a.popup = 'N' or (a.popup = 'Y' and a.uselist='Y') )  \n");
            strQuery.append("            and a.isall='Y' \n");
            if ("".equals(s_userid))
                strQuery.append("            and ( ( a.loginyn = 'AL' or a.loginyn = 'N' ) and a.grcodecd like " + StringManager.makeSQL("%" + tem_grcode + "%") + ") \n");
            else
                strQuery.append("            and ( ( a.loginyn = 'AL' or a.loginyn = 'Y' ) and a.grcodecd like " + StringManager.makeSQL("%" + tem_grcode + "%") + ") \n");
            strQuery.append("        order by a.seq desc \n");
            strQuery.append("    ) a \n");
            strQuery.append("where \n");
            strQuery.append("    rownum = 1 \n");

            x = 1;
            pstmt = connMgr.prepareStatement(strQuery.toString());
            pstmt.setString(x++, box.getString("p_tabseq"));
            ls = new ListSet(pstmt);
            ls.executeQuery();

            if (ls.next())
                dbox = ls.getDataBox();

        } catch (SQLException e) {
            ErrorManager.getErrorStackTrace(e, box, strQuery.toString());
            throw new Exception("\n SQL : [\n" + strQuery.toString() + "]\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } catch (Exception e) {

            ErrorManager.getErrorStackTrace(e);
            //throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
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
     * 모바일 이벤트 내용
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public DataBox getMainEventData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        DataBox dbox = null;

        strQuery = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

            strQuery.append("select \n");
            strQuery.append("    * \n");
            strQuery.append("from \n");
            strQuery.append("( \n");
            strQuery.append("    select \n");
            strQuery.append("        a.seq, a.gubun, a.title, a.indate, a.strdate \n");
            strQuery.append("    from \n");
            strQuery.append("        tz_event a, tz_member b \n");
            strQuery.append("    where \n");
            strQuery.append("        a.userid = b.userid(+) \n");
            strQuery.append("        and a.useyn='Y' \n");
            strQuery.append("        and substr(a.strdate, 1, 8) <= to_char(sysdate, 'yyyymmdd') \n");
            strQuery.append("        and b.grcode = " + StringManager.makeSQL(tem_grcode) + " \n");
            strQuery.append("    order by a.isall desc, a.seq desc \n");
            strQuery.append(") a \n");
            strQuery.append("where \n");
            strQuery.append("    rownum = 1 \n");

            x = 1;
            pstmt = connMgr.prepareStatement(strQuery.toString());

            ls = new ListSet(pstmt);
            ls.executeQuery();

            if (ls.next())
                dbox = ls.getDataBox();

        } catch (SQLException e) {
            ErrorManager.getErrorStackTrace(e, box, strQuery.toString());
            throw new Exception("\n SQL : [\n" + strQuery.toString() + "]\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } catch (Exception e) {

            ErrorManager.getErrorStackTrace(e);
            //throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
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
     * 모바일 공지사항 목록
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList getNoticeList(RequestBox box) throws Exception {
        DatabaseExecute connMgr = null;

        ArrayList list = null;
        strQuery = new StringBuffer();

        try {
            String v_tabseq = box.getString("p_tabseq");

            connMgr = new DatabaseExecute();

            String s_userid = box.getString("userid");
            tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
            strQuery.append("select \n");
            strQuery.append("    a.tabseq, a.seq, a.addate, a.adtitle, a.adname, a.cnt, a.luserid, a.ldate, a.onoffgubun, a.isall,  \n");
            strQuery.append("    ( \n");
            strQuery.append("        case when onoffgubun = 'C' then  \n");
            strQuery.append("        '[온라인]'  \n");
            strQuery.append("        when onoffgubun = 'C' then  \n");
            strQuery.append("            '[오프라인]'  \n");
            strQuery.append("        else  \n");
            strQuery.append("            ''  \n");
            strQuery.append("        end \n");
            strQuery.append("   ) as gubunnm \n");
            strQuery.append("from \n");
            strQuery.append("    tz_notice a \n");
            strQuery.append("where \n");
            strQuery.append("    a.tabseq = " + StringManager.makeSQL(v_tabseq) + " \n");
            strQuery.append("    and a.useyn = 'Y' \n");
            strQuery.append("    and (a.popup = 'N' or (a.popup = 'Y' and a.uselist='Y') ) \n");
            strQuery.append("    and a.isall='Y' \n");
            if ("".equals(s_userid))
                strQuery.append("    and ( ( a.loginyn = 'AL' or a.loginyn = 'N' ) and a.grcodecd like " + StringManager.makeSQL("%" + tem_grcode + "%") + ") \n");
            else
                strQuery.append("    and ( ( a.loginyn = 'AL' or a.loginyn = 'Y' ) and a.grcodecd like " + StringManager.makeSQL("%" + tem_grcode + "%") + ") \n");
            strQuery.append("order by a.seq desc \n");

            box.put("p_isPage", new Boolean(true));
            box.put("p_row", new Integer(row));

            list = connMgr.executeQueryList(box, strQuery.toString());

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
            throw new Exception("sql = " + strQuery + "\r\n" + ex.getMessage());
        }

        return list;
    }

    /**
     * 모바일 공지사항 상세 내용
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public DataBox getNoticeView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        DataBox dbox = null;

        strQuery = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            strQuery.append("select \n");
            strQuery.append("    a.seq, a.gubun, a.startdate, a.enddate, a.addate, a.adtitle, a.adname, \n");
            strQuery.append("    a.adcontent, a.cnt, a.luserid, a.ldate, a.isall \n");
            strQuery.append("from  \n");
            strQuery.append("    tz_notice a  \n");
            strQuery.append("where  \n");
            strQuery.append("    a.tabseq    = ? \n");
            strQuery.append("    and a.seq = ? \n");

            x = 1;
            pstmt = connMgr.prepareStatement(strQuery.toString());
            pstmt.setString(x++, box.getString("p_tabseq"));
            pstmt.setString(x++, box.getString("p_seq"));

            ls = new ListSet(pstmt);
            ls.executeQuery();

            if (ls.next())
                dbox = ls.getDataBox();

        } catch (SQLException e) {
            ErrorManager.getErrorStackTrace(e, box, strQuery.toString());
            throw new Exception("\n SQL : [\n" + strQuery.toString() + "]\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } catch (Exception e) {

            ErrorManager.getErrorStackTrace(e);
            //throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
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
     * 모바일 공지사항 조회수 증가
     * 
     * @param box
     * @throws Exception
     */
    public void updateCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        strQuery = new StringBuffer();

        try {

            connMgr = new DBConnectionManager();

            strQuery.append("update tz_notice set \n");
            strQuery.append("    cnt = nvl(cnt, 0) + 1 \n");
            strQuery.append("where \n");
            strQuery.append("    tabseq = ? \n");
            strQuery.append("    and seq = ? \n");

            x = 1;
            pstmt = connMgr.prepareStatement(strQuery.toString());
            pstmt.setString(x++, box.getString("p_tabseq"));
            pstmt.setInt(x++, box.getInt("p_seq"));
            pstmt.executeUpdate();

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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e) {
                }
            }

        }
    }

    /**
     * 모바일 이벤트 목록
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList getEventList(RequestBox box) throws Exception {
        DatabaseExecute connMgr = null;

        ArrayList list = null;
        strQuery = new StringBuffer();

        try {

            connMgr = new DatabaseExecute();

            tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

            strQuery.append("select \n");
            strQuery.append("    a.seq, a.gubun, a.title, a.indate, a.strdate, a.enddate, \n");
            strQuery.append("    ( \n");
            strQuery.append("        case when a.winneryn = 'Y' then \n");
            strQuery.append("            'Y' \n");
            strQuery.append("        when a.winneryn = 'N' then \n");
            strQuery.append("         \n");
            strQuery.append("            case when to_char(sysdate, 'yyyymmddhh24') between a.strdate and a.enddate then \n");
            strQuery.append("                'A' \n");
            strQuery.append("            when a.strdate > to_char(sysdate, 'yyyymmddhh24') then \n");
            strQuery.append("                'B' \n");
            strQuery.append("            when a.enddate > to_char(sysdate, 'yyyymmddhh24') then \n");
            strQuery.append("                'C' \n");
            strQuery.append("            else \n");
            strQuery.append("                a.winneryn \n");
            strQuery.append("            end \n");
            strQuery.append("        end \n");
            strQuery.append("    ) as winneryn \n");
            strQuery.append("from \n");
            strQuery.append("    tz_event a, tz_member b \n");
            strQuery.append("where \n");
            strQuery.append("    a.userid = b.userid(+) \n");
            strQuery.append("    and a.useyn='Y' \n");
            strQuery.append("    and substr(a.strdate, 1, 8) <= to_char(sysdate, 'yyyymmdd') \n");
            strQuery.append("    and b.grcode = " + StringManager.makeSQL(tem_grcode) + " \n");
            strQuery.append("order by a.isall desc, a.seq desc \n");

            box.put("p_isPage", new Boolean(true));
            box.put("p_row", new Integer(row));

            list = connMgr.executeQueryList(box, strQuery.toString());

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
            throw new Exception("sql = " + strQuery + "\r\n" + ex.getMessage());
        }

        return list;
    }

    /**
     * 이벤트 내용 및 당첨자 보기 페
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public DataBox getEventData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        DataBox dbox = null;

        strQuery = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

            strQuery.append("select \n");
            strQuery.append("    a.seq, a.gubun, a.title, a.indate, a.strdate, a.enddate, a.content, \n");
            strQuery.append("    ( \n");
            strQuery.append("        case when a.winneryn = 'Y' then \n");
            strQuery.append("            'Y' \n");
            strQuery.append("        when a.winneryn = 'N' then \n");
            strQuery.append("         \n");
            strQuery.append("            case when to_char(sysdate, 'yyyymmddhh24') between a.strdate and a.enddate then \n");
            strQuery.append("                'A' \n");
            strQuery.append("            when a.strdate > to_char(sysdate, 'yyyymmddhh24') then \n");
            strQuery.append("                'B' \n");
            strQuery.append("            when a.enddate > to_char(sysdate, 'yyyymmddhh24') then \n");
            strQuery.append("                'C' \n");
            strQuery.append("            else \n");
            strQuery.append("                a.winneryn \n");
            strQuery.append("            end \n");
            strQuery.append("        end \n");
            strQuery.append("    ) as winneryn, a.winners, a.cnt, b.name \n");
            strQuery.append("from \n");
            strQuery.append("    tz_event a, tz_member b \n");
            strQuery.append("where \n");
            strQuery.append("    a.userid = b.userid(+) \n");
            strQuery.append("    and a.useyn='Y' \n");
            strQuery.append("    and a.seq = ? \n");
            strQuery.append("    and b.grcode = " + StringManager.makeSQL(tem_grcode) + " \n");

            x = 1;
            pstmt = connMgr.prepareStatement(strQuery.toString());
            pstmt.setString(x++, box.getString("p_seq"));

            ls = new ListSet(pstmt);
            ls.executeQuery();

            if (ls.next())
                dbox = ls.getDataBox();

        } catch (SQLException e) {
            ErrorManager.getErrorStackTrace(e, box, strQuery.toString());
            throw new Exception("\n SQL : [\n" + strQuery.toString() + "]\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } catch (Exception e) {

            ErrorManager.getErrorStackTrace(e);
            //throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
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
     * 이벤트 조회수 증가
     * 
     * @param box
     * @throws Exception
     */
    public void updateEventCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        strQuery = new StringBuffer();

        try {

            connMgr = new DBConnectionManager();

            strQuery.append("update tz_notice set \n");
            strQuery.append("    cnt = nvl(cnt, 0) + 1 \n");
            strQuery.append("where \n");
            strQuery.append("    seq = ? \n");

            x = 1;
            pstmt = connMgr.prepareStatement(strQuery.toString());
            pstmt.setInt(x++, box.getInt("p_seq"));
            pstmt.executeUpdate();

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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e) {
                }
            }

        }
    }
}
