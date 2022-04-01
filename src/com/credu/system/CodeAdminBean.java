//**********************************************************
//  1. 제      목: 코드 관리
//  2. 프로그램명 : CodeAdminBean.java
//  3. 개      요: 코드 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7.  2
//  7. 수      정:
//**********************************************************

package com.credu.system;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * 코드 관리(ADMIN)
 * 
 * @date : 2003. 7
 * @author : s.j Jung
 */
public class CodeAdminBean {

    public CodeAdminBean() {
    }

    /**
     * 대분류 코드화면 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 대분류 코드 리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectListCode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        CodeData data = null;

        //        String v_searchtext = box.getString("p_searchtext");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql = " select gubun, gubunnm, maxlevel, issystem from TZ_CODEGUBUN   ";
            /*
             * if ( !v_searchtext.equals("")) { // 검색어가 있으면 sql +=
             * " where gubunnm like " + StringManager.makeSQL("%" + v_searchtext
             * + "%"); }
             */
            sql += " order by gubun asc";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CodeData();

                data.setGubun(ls.getString("gubun"));
                data.setGubunnm(ls.getString("gubunnm"));
                data.setMaxlevel(ls.getInt("maxlevel"));
                data.setIssystem(ls.getString("issystem"));

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
     * 대분류 코드화면 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 대분류 코드 리스트
     */
    public static ArrayList<DataBox> selectListCode(String v_gubun) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox data = null;

        //         String v_searchtext = box.getString("p_searchtext");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select code, codenm from TZ_CODE  where gubun = '" + v_gubun + "'";
            sql += " order by code asc";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = ls.getDataBox();
                list.add(data);
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
        return list;
    }

    /**
     * 대분류 코드화면 상세보기
     * 
     * @param box receive from the form object and session
     * @return CodeData 조회한 상세정보
     */
    public CodeData selectViewCode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        CodeData data = null;

        String v_gubun = box.getString("p_gubun");

        try {
            connMgr = new DBConnectionManager();

            sql = " select gubunnm, maxlevel, issystem from TZ_CODEGUBUN        ";
            sql += "  where gubun = " + StringManager.makeSQL(v_gubun);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new CodeData();
                data.setGubunnm(ls.getString("gubunnm"));
                data.setMaxlevel(ls.getInt("maxlevel"));
                data.setIssystem(ls.getString("issystem"));
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
        return data;
    }

    /**
     * 대분류코드 등록할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int insertCode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;
        String v_gubun = "";

        String v_gubunnm = box.getString("p_gubunnm");
        v_gubunnm = StringManager.replace(v_gubunnm, "'", "");

        int v_maxlevel = box.getInt("p_maxlevel");
        String v_issystem = box.getString("p_issystem");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = "select max(gubun) from TZ_CODEGUBUN  ";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_gubun = CodeConfigBean.addZero(StringManager.toInt(ls.getString(1)) + 1, 4);
            } else {
                v_gubun = "0001";
            }

            sql1 = "insert into TZ_CODEGUBUN(gubun, gubunnm, maxlevel, issystem, luserid, ldate)   ";
            sql1 += " values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))                     ";

            pstmt = connMgr.prepareStatement(sql1);

            pstmt.setString(1, v_gubun);
            pstmt.setString(2, v_gubunnm);
            pstmt.setInt(3, v_maxlevel);
            pstmt.setString(4, v_issystem);
            pstmt.setString(5, s_userid);

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
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
     * 대분류코드 수정하여 저장할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     */
    public int updateCode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_gubun = box.getString("p_gubun");
        String v_gubunnm = box.getString("p_gubunnm");
        int v_maxlevel = box.getInt("p_maxlevel");
        String v_issystem = box.getString("p_issystem");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " update TZ_CODEGUBUN set gubunnm = ? , maxlevel = ?, issystem=?, luserid= ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += "  where gubun = ?                                                                                                          ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_gubunnm);
            pstmt.setInt(2, v_maxlevel);
            pstmt.setString(3, v_issystem);
            pstmt.setString(4, s_userid);
            pstmt.setString(5, v_gubun);

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
     * 대분류코드 삭제할때 - 하위 소분류 삭제
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     */
    public int deleteCode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";

        int isOk1 = 0;

        String v_gubun = box.getString("p_gubun");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////  

            sql1 = " delete from TZ_CODEGUBUN  ";
            sql1 += "   where gubun = ?         ";

            sql2 = " delete from TZ_CODE    ";
            sql2 += "   where gubun = ?      ";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setString(1, v_gubun);
            isOk1 = pstmt1.executeUpdate();

            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt2.setString(1, v_gubun);
            pstmt2.executeUpdate();

            //            if(isOk1 > 0 && isOk2 >0) connMgr.commit();         //      2가지 sql 이 꼭 같이 delete 되어야 하는 경우이므로  
            //            else connMgr.rollback();
            if (isOk1 > 0)
                connMgr.commit(); //      하위 분류의 로우가 없을경우 isOk2 가 0 이므로 isOk2 >0 조건 제외
            else
                connMgr.rollback();
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1 + "\r\n" + sql2);
            throw new Exception("sql = " + sql1 + "\r\n" + sql2 + "\r\n" + ex.getMessage());
        } finally {
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
     * 소분류 코드화면 리스트
     */
    /**
     * 소분류 코드화면 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 소분류코드 리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectSubListCode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        CodeData data = null;

        String v_gubun = box.getString("p_gubun");
        int v_levels = box.getInt("p_levels");
        String v_upper = box.getString("p_upper");
        String v_parent = box.getString("p_parent");
        String v_searchtext = box.getString("p_searchtext");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql = " select code, codenm from tz_code            ";
            sql += "  where gubun  = " + StringManager.makeSQL(v_gubun);
            sql += "    and levels = " + v_levels;
            if (!v_parent.equals("")) {
                sql += "    and parent = " + StringManager.makeSQL(v_parent);
            }
            if (!v_upper.equals("")) {
                sql += "    and upper = " + StringManager.makeSQL(v_upper);
            }

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                sql += " and codenm like " + StringManager.makeSQL("%" + v_searchtext + "%");
            }
            sql += " order by code asc";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CodeData();

                data.setCode(ls.getString("code"));
                data.setCodenm(ls.getString("codenm"));

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
     * 소분류 코드화면 상세보기
     * 
     * @param box receive from the form object and session
     * @return CodeData 조회한 상세정보
     */
    public CodeData selectSubViewCode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        CodeData data = null;

        String v_gubun = box.getString("p_gubun");
        int v_levels = box.getInt("p_levels");
        String v_code = box.getString("p_code");

        try {
            connMgr = new DBConnectionManager();

            sql = " select codenm from TZ_CODE        ";
            sql += "  where gubun  = " + StringManager.makeSQL(v_gubun);
            sql += "    and levels = " + v_levels;
            sql += "    and code   = " + StringManager.makeSQL(v_code);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new CodeData();
                data.setCodenm(ls.getString("codenm"));
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
        return data;
    }

    /**
     * 소분류코드 등록할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int insertSubCode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;
        String v_code = "";

        String v_gubun = box.getString("p_gubun");
        int v_levels = box.getInt("p_levels");
        String v_codenm = box.getString("p_codenm");
        String v_upper = box.getString("p_upper");
        String v_parent = box.getString("p_parent");

        String s_userid = box.getSession("userid");

        String v_issystem = box.getString("p_issystem");
        try {
            connMgr = new DBConnectionManager();

            if (v_issystem.equals("Y")) {
                v_code = box.getString("p_code");

                isOk = checkDuplicateCode(box, connMgr);

                if (isOk == 0) {
                    if (connMgr != null) {
                        try {
                            connMgr.freeConnection();
                        } catch (Exception e10) {
                        }
                    }
                    isOk = 3;

                    return isOk;
                }

            } else {
                sql = "select max(code) from TZ_CODE  ";
                sql += " where gubun = " + StringManager.makeSQL(v_gubun);
                //sql += "   and levels = " + v_levels;
                ls = connMgr.executeQuery(sql);
                if (ls.next()) {
                    v_code = CodeConfigBean.addZero(StringManager.toInt(ls.getString(1)) + 1, 20);
                } else {
                    v_code = "00000000000000000001";
                }
            }

            if (v_levels == 1) {
                v_upper = v_code;
                v_parent = v_code;
            }

            sql1 = "insert into TZ_CODE(gubun, levels, code, codenm, upper, parent, luserid, ldate) ";
            sql1 += " values (?, ?, ?, ?, ?, ?, ? ,to_char(sysdate, 'YYYYMMDDHH24MISS'))           ";

            pstmt = connMgr.prepareStatement(sql1);

            pstmt.setString(1, v_gubun);
            pstmt.setInt(2, v_levels);
            pstmt.setString(3, v_code);
            pstmt.setString(4, v_codenm);
            pstmt.setString(5, v_upper);
            pstmt.setString(6, v_parent);
            pstmt.setString(7, s_userid);

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 소분류코드 수정하여 저장할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     */
    public int updateSubCode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_gubun = box.getString("p_gubun");
        int v_levels = box.getInt("p_levels");
        String v_code = box.getString("p_code");
        String v_codenm = box.getString("p_codenm");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " update TZ_CODE set codenm = ? , luserid= ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += "  where gubun = ? and levels = ? and code = ?                                              ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_codenm);
            pstmt.setString(2, s_userid);
            pstmt.setString(3, v_gubun);
            pstmt.setInt(4, v_levels);
            pstmt.setString(5, v_code);

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
     * 소분류코드 삭제할때 - 하위 분류 삭제
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     */
    public int deleteSubCode(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";

        int isOk1 = 0;

        String v_gubun = box.getString("p_gubun");
        int v_levels = box.getInt("p_levels");
        String v_code = box.getString("p_code");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////  

            sql1 = " delete from TZ_CODE                            ";
            sql1 += "   where gubun = ? and levels = ? and code = ?  ";

            sql2 = " delete from TZ_CODE    ";
            sql2 += "   where gubun = ? and (upper = ? or parent = ? ) ";

            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_gubun);
            pstmt1.setInt(2, v_levels);
            pstmt1.setString(3, v_code);
            isOk1 = pstmt1.executeUpdate();

            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt2.setString(1, v_gubun);
            pstmt2.setString(2, v_code);
            pstmt2.setString(3, v_code);

            pstmt2.executeUpdate();

            //            if(isOk1 > 0 && isOk2 >0) connMgr.commit();         //      2가지 sql 이 꼭 같이 delete 되어야 하는 경우이므로  
            //            else connMgr.rollback();
            if (isOk1 > 0)
                connMgr.commit(); //      하위 분류의 로우가 없을경우 isOk2 가 0 이므로 isOk2 >0 조건 제외
            else
                connMgr.rollback();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1 + "\r\n" + sql2);
            throw new Exception("sql = " + sql1 + "\r\n" + sql2 + "\r\n" + ex.getMessage());
        } finally {
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

    /************************************* 공통함수들 ***********************************************************/

    /**
     * 코드구분명 (구분)
     */
    public static String getCodeName(String gubun) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        CodeData data = null;

        try {
            connMgr = new DBConnectionManager();

            sql = " select gubunnm, maxlevel from TZ_CODEGUBUN        ";
            sql += "  where gubun = " + StringManager.makeSQL(gubun);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new CodeData();
                data.setGubunnm(ls.getString("gubunnm"));
                result = data.getGubunnm();
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

        return result;
    }

    /**
     * 하위코드 수동등록 여부
     * 
     * @param gubun 구분코드
     * @return result Y : 수동등록, N : 자동등록
     * @throws Exception
     */
    public static String getCodeIssystem(String gubun) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        CodeData data = null;

        try {
            connMgr = new DBConnectionManager();

            sql = " select issystem from TZ_CODEGUBUN        ";
            sql += "  where gubun = " + StringManager.makeSQL(gubun);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new CodeData();
                data.setIssystem(ls.getString("issystem"));
                result = data.getIssystem();
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

        return result;
    }

    /**
     * 코드명 (구분,코드)
     */
    public static String getCodeName(String gubun, String code) throws Exception {
        return getCodeName(gubun, code, 1);
    }

    /**
     * 코드명 (구분,코드,레벨)
     */
    public static String getCodeName(String gubun, String code, int levels) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        CodeData data = null;

        try {
            connMgr = new DBConnectionManager();

            sql = " select codenm from TZ_CODE        ";
            sql += "  where gubun  = " + StringManager.makeSQL(gubun);
            sql += "    and levels = " + levels;
            sql += "    and code   = " + StringManager.makeSQL(code);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new CodeData();
                data.setCodenm(ls.getString("codenm"));
                result = data.getCodenm();
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

        return result;
    }

    /**
     * 업로드 확장자 코드값
     */
    @SuppressWarnings("unchecked")
    public static ArrayList getUploadCodeName() throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String s_gubun = "0057"; // 업로드확장자허용 - 코드 

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT codenm FROM TZ_CODE WHERE GUBUN='" + s_gubun + "' ORDER BY TO_NUMBER(CODE) ASC  ";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
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
     * 업로드 확장자 코드값
     */
    @SuppressWarnings("unchecked")
    public static ArrayList getUploadCodeNameToList() throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String s_gubun = "0057"; // 업로드확장자허용 - 코드 

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT lower(codenm) as codenm FROM TZ_CODE WHERE GUBUN='" + s_gubun + "' ORDER BY TO_NUMBER(CODE) ASC  ";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox.getString("d_codenm"));
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

    /*
     * 소분류코드 중복 검사
     * 
     * @param box receive from the form object and session
     * 
     * @return isOk 1:insert success,0:insert fail
     */
    public int checkDuplicateCode(RequestBox box, DBConnectionManager connMgr) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;
        String v_code = box.getString("p_code");
        String v_gubun = box.getString("p_gubun");

        try {
            sql = "select code from TZ_CODE  ";
            sql += " where gubun = " + StringManager.makeSQL(v_gubun);
            sql += " and code = " + StringManager.makeSQL(v_code);

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                isOk = v_code.equals(ls.getString(1)) ? 0 : 1;
            } else {
                isOk = 1;
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
        }
        return isOk;
    }

    /*
     * 상위코드 구하기
     * 
     * @param box receive from the form object and session
     * 
     * @return isOk 1:insert success,0:insert fail
     */
    public static DataBox getUpperCode(String gubun, String code) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt = null;
        DBConnectionManager connMgr = null;
        String sql = "";

        DataBox result = null;

        try {
            connMgr = new DBConnectionManager();
            sql = "select upper from TZ_CODE  ";
            sql += " where gubun = " + StringManager.makeSQL(gubun);
            sql += " and code = " + StringManager.makeSQL(code);

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                result = ls.getDataBox();
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
        return result;
    }
}
