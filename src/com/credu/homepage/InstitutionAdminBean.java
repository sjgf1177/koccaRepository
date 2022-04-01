// **********************************************************
// 1. 제 목: 시설관리 운영자페이지
// 2. 프로그램명: InstitutionAdminBean.java
// 3. 개 요: 시설관리 운영자페이지관리
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0 QnA
// 6. 작 성: 이연정 2005. 08. 03
// 7. 수 정: 이나연 05.11.17 _ totalcount 하기위한 쿼리수정
// **********************************************************
package com.credu.homepage;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import com.credu.contents.FileDelete;
import com.credu.contents.FileUnzip;
import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.FileMove;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.system.CodeConfigBean;

/**
 * To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 * 
 * @author Administrator
 * 
 */
public class InstitutionAdminBean {
    private ConfigSet config;
    private int row;

    // private static final String FILE_TYPE = "p_file"; // 파일업로드되는 tag name
    // private static final int FILE_LIMIT = 1; // 페이지에 세팅된 파일첨부 갯수
    public InstitutionAdminBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); // 이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InstitutionAdminBean(String type) {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); // 이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> selectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // 수정일 : 05.11.11 수정자 : 이나연 _ TotalCount 해주기 위한 쿼리를 담을 변수
        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";

        int v_pageno = box.getInt("p_pageno");
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();
            // seq, types, title, contents, indate, inuserid, upfile, isopen, luserid, ldate
            head_sql += " select no,code,insname,realfile,savefile,url,luserid,ldate ";
            body_sql += "   from TZ_INSTITUTION";
            order_sql += " order by no desc ";
            sql = head_sql + body_sql + group_sql + order_sql;

            ls = connMgr.executeQuery(sql);
            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); // 전체 row 수를 반환한다
            int total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다

            ls.setPageSize(row); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.

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
     * 등록할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */

    public int insert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        int isOk1 = 1;
        int v_no = 0;

        String v_userid = box.getSession("userid");
        String v_insname = box.getString("p_insname");
        String v_realfile = box.getRealFileName("p_file");
        String v_savefile = box.getNewFileName("p_file");
        String v_url = box.getString("p_url");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // ---------------------- 게시판 번호 가져온다 ----------------------------
            sql = "select NVL(max(no), 0) from TZ_INSTITUTION";
            ls = connMgr.executeQuery(sql);
            ls.next();
            v_no = ls.getInt(1) + 1;
            ls.close();

            String v_code = CodeConfigBean.addZero(v_no, 4);

            // FILE MOVE & UNZIP
            this.controlObjectFile("insert", v_code, box);
            System.out.println("인서트리절트");
            // //////////////////////////////// 게시판 table 에 입력 ///////////////////////////////////////////////////////////////////
            sql1 = "insert into TZ_INSTITUTION(no,code,insname,realfile,savefile,url,luserid,ldate)";
            sql1 += "                values (?, ?, ?, ?, ?, ?,?,to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(1, v_no);
            pstmt1.setString(2, v_code);
            pstmt1.setString(3, v_insname);
            pstmt1.setString(4, v_realfile);
            pstmt1.setString(5, v_savefile);
            pstmt1.setString(6, v_url);
            pstmt1.setString(7, v_userid);

            isOk1 = pstmt1.executeUpdate();
            if (isOk1 > 0) {
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
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
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
        return isOk1;
    }

    /**
     * 수정 검색
     * 
     * @param box receive from the form object and session
     * @return ArrayList 리스트
     * @throws Exception
     */
    public DataBox selectUpdate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int v_no = box.getInt("p_no");
        String v_code = box.getString("p_code");
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();

            // seq, types, title, contents, indate, inuserid, upfile, isopen, luserid, ldate
            sql += " select  no,code,insname,realfile,savefile,url,luserid,ldate  ";
            sql += "   from TZ_INSTITUTION where no = " + v_no + " and code = " + v_code;

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

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
        return dbox;
    }

    /**
     * 수정하여 저장할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int update(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";

        int isOk = 0;

        String v_no = box.getString("p_no");
        String v_code = box.getString("p_code");
        String v_insname = box.getString("p_insname");
        String v_url = box.getString("p_url");
        String v_realfile = box.getRealFileName("s_file");
        String v_savefile = box.getNewFileName("s_file");

        try {
            connMgr = new DBConnectionManager();
            // ------------------------------------------------------------------------------------
            sql = " update TZ_INSTITUTION set no=?,code=?,insname=?,realfile=?,savefile=?,url=?, ldate=to_char(sysdate, 'YYYYMMDDHH24MISS')";
            sql += "  where no = ? and code = ? ";
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_no);
            pstmt.setString(2, v_code);
            pstmt.setString(3, v_insname);

            if ("".equals(v_realfile)) {
                pstmt.setString(4, v_realfile);
                pstmt.setString(5, v_savefile);
            } else {
                pstmt.setString(4, v_realfile);
                pstmt.setString(5, v_savefile);
            }
            pstmt.setString(6, v_url);
            pstmt.setString(7, v_no);
            pstmt.setString(8, v_code);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            }
        } catch (Exception ex) {
            connMgr.rollback();
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
     * 삭제할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int delete(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 1;

        int v_no = box.getInt("p_no");
        String v_code = box.getString("p_code");

        try {
            connMgr = new DBConnectionManager();
            // ------------------------------------------------------------------------------------

            sql = " delete from TZ_INSTITUTION    ";
            sql += "  where no = ?  and code = ?";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, v_no);
            pstmt.setString(2, v_code);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            }

        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
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
     * Object ZIP 파일로 Directory 구성
     * 
     * @param String p_job 등록/수정구분(insert/update) p_oid Object ID box RequestBox
     * @return String resuts 결과메세지
     */
    public String controlObjectFile(String p_job, String p_oid, RequestBox box) throws Exception {
        String results = "OK";

        String v_realPath = "";
        String v_tempPath = "";
        boolean move_success = false;
        boolean extract_success = false;
        boolean allDelete_success = false;

        // --------------- 업로드되는 파일의 형식을 알고 코딩해야한다 --------------------

        String v_newFileName = box.getNewFileName("p_file");
        String v_file1 = "";

        if (!v_newFileName.equals("")) {
            v_file1 = v_newFileName;
        }

        // 첨부파일이 있을 경우에만 실행.
        if (!v_file1.equals("")) {
            // Object 폴더결정
            ConfigSet conf = new ConfigSet();
            v_realPath = conf.getProperty("dir.institutionpath") + p_oid; // 실제 Un-zip될 Dir
            v_tempPath = conf.getProperty("dir.upload.bulletin"); // upload된 파일 위치

            results = p_job;
            results += "\\n\\n 0. v_realPath=" + v_realPath;
            results += "\\n\\n 0. v_tempPath=" + v_tempPath;

            try {
                if (p_job.equals("insert")) { // Object 등록시
                    // 1. 폴더 생성
                    File newDir = new File(v_realPath);
                    System.out.println("canWrite : " + newDir.canWrite());
                    boolean b = newDir.mkdir();
                    System.out.println("b : " + b);
                    allDelete_success = true;
                    results += "\\n\\n 1. makeDirecotry OK. ";
                } else if (p_job.equals("update")) { // Object 수정시
                    // 1. 수정할 파일및 폴더 모두 삭제
                    FileDelete fd = new FileDelete();
                    allDelete_success = fd.allDelete(v_realPath);
                    results += "\\n\\n 1. allDelete =  " + (new Boolean(allDelete_success).toString());
                    // 1. 폴더 생성
                    File newDir = new File(v_realPath);
                    newDir.mkdirs();
                    allDelete_success = true;
                    results += "\\n\\n 1. makeDirecotry OK. ";
                }

                // 2. 파일 이동
                if (allDelete_success) {
                    FileMove fc = new FileMove();
                    System.out.println("v_realPath2 : " + v_realPath);
                    System.out.println("v_tempPath : " + v_tempPath);
                    move_success = fc.move(v_realPath, v_tempPath, v_file1);
                    System.out.println("move_success=" + move_success);
                }
                results += "\\n\\n 2. move to [" + v_realPath + "] =  " + (new Boolean(move_success).toString());

                // 3. 압축 풀기
                if (move_success) {
                    FileUnzip unzip = new FileUnzip();
                    extract_success = unzip.extract(v_realPath, v_file1);
                    results += "\\n\\n 3. unzip to [" + v_realPath + "] =  " + (new Boolean(extract_success).toString());
                    if (!extract_success) {
                        FileDelete fd = new FileDelete();
                        fd.allDelete(v_realPath);
                    }
                }
                results += "\\n\\n END of controlObjectFile() ";
                results = "OK";
            } catch (Exception ex) {
                FileDelete fd = new FileDelete();
                fd.allDelete(v_realPath);
                throw new Exception("ERROR results=" + results + "\r\n" + ex.getMessage());
            } finally {
                FileManager.deleteFile(v_realPath + "\\" + v_file1);
            }
        }

        return results;
    }

    /**
     * SELECT BOX 구성
     * 
     * @param boolean isALL 전체여부
     * @param String selname select 이름
     * @param String optionselected 선택
     * @return String resuts SELECT BOX 구성 문자
     */
    public String getSelectIns(boolean isALL, String selname, String optionselected) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        StringBuffer sb = null;

        try {
            connMgr = new DBConnectionManager();
            sb = new StringBuffer();

            sb.append("<select name = \"" + selname + "\">");
            if (isALL) {
                sb.append("<option value = ''>==== 선택 ====</option>\r\n");
            }
            sql = " select  code,insname  ";
            sql += " from TZ_INSTITUTION   ";
            sql += " order by code  ";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            // System.out.println(sql);
            ls = new ListSet(pstmt);
            while (ls.next()) {
                ResultSetMetaData meta = ls.getMetaData();
                int columnCount = meta.getColumnCount();

                sb.append("<option value = \"" + ls.getString(1) + "\"");
                if (optionselected.equals(ls.getString(1)))
                    sb.append(" selected");
                sb.append(">" + ls.getString(columnCount) + "</option>\r\n");
            }
            sb.append("</select>\r\n");
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
        return sb.toString();
    }

    /**
     * 상세 정보
     * 
     * @param place 시설코드
     * @return DataBox 상세정보
     * @throws Exception
     */
    public DataBox getInstitution(String place) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();

            // seq, types, title, contents, indate, inuserid, upfile, isopen, luserid, ldate
            sql = " select  no,code,insname,realfile,savefile,url,luserid,ldate  ";
            sql += "   from TZ_INSTITUTION where code =" + SQLString.Format(place);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
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
        return dbox;
    }
}
