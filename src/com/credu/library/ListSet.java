//**********************************************************
//  1. 제	  목: ResultSet  객체 제어
//  2. 프로그램명: ListSet.java
//  3. 개	  요: ResultSet  객체 제어하여 페이지 나누기를 관리한다
//  4. 환	  경: JDK 1.3
//  5. 버	  젼: 1.0
//  6. 작	  성: 이정한 2003. 4. 26
//  7. 수	  정: 이정한 2003. 4. 26
//**********************************************************

package com.credu.library;

import java.io.Reader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

/*
 import oracle.sql.*;
 import oracle.jdbc.driver.*;
 */
/**
 * 데이타베이스의 ResultSet 객체를 제어하는 라이브러리
 * 
 * @date : 2003. 4. 9
 * @author : j.h. lee
 */
@SuppressWarnings( { "unused" })
public class ListSet {
    private boolean b_bulletin = false; // 게시판쿼리인지 여부

    private int block_size = 10; // 한 블럭당 페이지갯수
    private int page_size = 10; // 한페이지당 보여질 row 갯수
    private int total_row_count = 0; // 쿼리후 전체 row 갯수
    private int total_page_count = 0; // 퀴리후 전체 페이지 갯수
    private int total_block_count = 0;// 쿼리후 전체 블럭갯수
    private int current_page_num = 0; // 현재 페이지 번호
    private int current_block_num = 0; // 현재 블럭 번호
    private int current_page_last_row_num = 0; // 현재 페이지에 보여질 최종 row 번호
    private int current_row_num = 0; // 현재 보여질 row 번호

    private PreparedStatement pstmt = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    private String sQuery = null;

    /**
     * Connection 객체에서 Statement 객체를 생성한다
     * 
     * @parameter conn Connection 객체를 인자로 받는다
     */
    /*-------------------------------------------------------------*/
    /*
     * 2004.03.02 juwanpapa MS_SQL의 서브쿼리분석에 의한 문제점 야기로 인하여 생성자 오버로딩 시킴 ex)
     * connMgr.executeQuery(sql); connMgr.executeQuery(sql,"ae18"); /*
     * /*-------------------------------------------------------------
     */
    public ListSet(Connection conn) throws Exception {
        try {
            //stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //stmt = conn.createStatement();
        } catch (Exception ex) {
            this.close();
            throw new Exception("ListSet.ListSet(conn)\r\n" + ex.getMessage());
        }
    }

    public ListSet(Connection conn, int sensitive, int read) throws Exception {
        try {
            //stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt = conn.createStatement(sensitive, read);
            //stmt = conn.createStatement();
        } catch (Exception ex) {
            this.close();
            throw new Exception("ListSet.ListSet(conn)\r\n" + ex.getMessage());
        }
    }

    /**
     * Connection 객체에서 Statement 객체를 생성한다
     * 
     * @parameter conn Connection 객체를 인자로 받는다
     */
    public ListSet(Connection conn, String param) throws Exception {
        try {
            /* 2004.03.02 juwanpapa MS-SQL성 */
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            //stmt = conn.createStatement();//기본설정으로 바꿈
        } catch (Exception ex) {
            this.close();
            throw new Exception("ListSet.ListSet(conn,param)\r\n" + ex.getMessage());
        }
    }

    /**
     * Connection 으로부터 생성된 PreparedStatement 객체와 sql 쿼리를 지정한다
     * 
     * @parameter p PreparedStatement 객체를 인자로 받는다
     * @parameter sql 쿼리를 인자로 받는다
     */
    public ListSet(PreparedStatement p) throws Exception {
        pstmt = p;
        try {
            this.executeQuery();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("ListSet()" + ex.getMessage());
        }
    }

    /**
     * Statement 객체에 Oracle의 Long Row형 Field(사진)을 읽을때 사용한다.(Only Oracle)
     * 
     * @parameter column 컬럼명을 인자로 받는다
     * @parameter name 파일명
     * @parameter terms 파일 확장자명
     */
    /*
     * public String getBlob(String column) throws Exception { BLOB blob = null;
     * InputStream input = null; BufferedInputStream bis = null; byte [] buf =
     * null; // Blob buffer String data = ""; try { blob =
     * ((OracleResultSet)rs).getBLOB(column); buf = new byte[1024];
     * 
     * input = blob.getBinaryStream(); bis = new BufferedInputStream(input);
     * 
     * int length = 0;
     * 
     * bis.read(buf);
     * 
     * input.close(); bis.close(); } catch (Exception ex) { throw new
     * Exception("ListSet.getBlob"+ex.getMessage()); } return data; }
     */
    /**
     * Statement 객체에 Oracle의 Long Row형 Field(사진)을 읽을때 사용한다.(Only Oracle)
     * 
     * @parameter column 컬럼명을 인자로 받는다
     * @parameter name 파일명
     * @parameter terms 파일 확장자명
     */
    /*
     * public String getClob(String column) throws Exception { CLOB clob = null;
     * Reader rd = null; char [] cbuf = null; // Blob buffer String data = "";
     * StringBuffer sb = null; try { clob =
     * ((OracleResultSet)rs).getCLOB(column); cbuf = new char[1024];
     * 
     * rd = clob.getCharacterStream();
     * 
     * int readcnt; sb = new StringBuffer(); while ((readcnt = rd.read(cbuf, 0,
     * 1024)) != -1) { sb.append(cbuf, 0, readcnt); } rd.close(); } catch
     * (Exception ex) { throw new Exception("ListSet.getClob"+ex.getMessage());
     * } return sb.toString(); }
     */
    /**
     * Statement 객체에 Oracle의 Long Row형 Field(사진)을 읽을때 사용한다.(Only Oracle)
     * 
     * @parameter column 컬럼명을 인자로 받는다
     * @parameter name 파일명
     * @parameter terms 파일 확장자명
     */
    /*
     * public String getBinaryFileStream(String column,String name,String terms)
     * throws Exception { ConfigSet conf = new ConfigSet(); InputStream gifdata
     * = null; String fullName = null; String v_updir = null; try { v_updir =
     * conf.getProperty("dir.upload");
     * 
     * fullName = v_updir + name + terms; gifdata = rs.getBinaryStream(column);
     * // gifdata = ((OracleResultSet)rs).getBinaryStream(column); // (Oracle
     * Jdbc driver 인 경우)
     * 
     * //System.out.println("fullName======>"+fullName); File gifFile = new
     * File(fullName); // create new file
     * 
     * // Write the byte array into a local file FileOutputStream file= new
     * FileOutputStream(gifFile);
     * 
     * int chunk=0; // write to the local file until image (LONGRAW) data is
     * found while( (chunk = gifdata.read()) != -1) { file.write(chunk); } //
     * flush the data file.flush(); file.close(); // close the file } catch
     * (Exception ex) { throw new
     * Exception("ListSet.getBinaryStream"+ex.getMessage()); } return
     * conf.getProperty("url.upload")+ name + terms; }
     */
    public String chkNull(String sql) {
        if (sql == null) {
            return "";
        } else {
            return sql.trim();
        }
    }

    /**
     * ResultSet, Statement, PreparedStatement 객체를 닫는다
     */
    public void close() {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * PreparedStatement 객체에 SQL 쿼리를 실행해 ResultSet 객체를 생성한다
     * 
     * @parameter sql 쿼리를 인자로 받는다
     */
    public void executeQuery() throws Exception {
        b_bulletin = false;
        try {
            rs = pstmt.executeQuery();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.close();
            throw new Exception("ListSet.executeQuery()" + ex.getMessage());
        }
    }

    public ArrayList<DataBox> getAllDataList() throws Exception {
        ArrayList<DataBox> result = new ArrayList<DataBox>();
        while (rs.next()) {
            result.add(getDataBox());
        }
        return result;
    }

    public ArrayList<DataBox> getAllDataList(DBConnectionManager connMgr, String sql, RequestBox box, boolean countYn) throws Exception {
        ArrayList<DataBox> result = new ArrayList<DataBox>();
        DataBox dbox1 = null;
        int total_row_count = BoardPaging.getTotalRow(connMgr, sql, true); //     전체 row 수를 반환한다
        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");
        setPageSize(v_pagesize); //  페이지당 row 갯수를 세팅한다
        setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.

        while (next()) {
            dbox1 = getDataBox();
            dbox1.put("d_dispnum", new Integer(total_row_count - getRowNum() + 1));
            dbox1.put("d_totalpage", new Integer(getTotalPage()));
            dbox1.put("d_rowcount", new Integer(v_pagesize));
            dbox1.put("d_totalrowcount", new Integer(total_row_count));
            result.add(dbox1);
        }
        return result;
    }

    public ArrayList<DataBox> getAllDataListNoPage(DBConnectionManager connMgr, String sql, RequestBox box, boolean countYn) throws Exception {
        ArrayList<DataBox> result = new ArrayList<DataBox>();
        DataBox dbox1 = null;

        while (next()) {
            dbox1 = getDataBox();
            result.add(dbox1);
        }
        return result;
    }

    /**
     * 결과물이 Field1, Field2 일경우 이면서 Field1이 중복되지 않을 경우 사용할 수 있다. 모든 조회결과를 Map에
     * Field1을 Key로 Field2를 데이터로 넣어준다
     * 
     * @return Map<String, String>
     * @throws Exception
     */
    public DataBox getAllDataMap() throws Exception {
        DataBox result = new DataBox("responsebox");
        final int ONE = 1, TWO = 2;
        while (rs.next()) {
            result.put(rs.getString(ONE), rs.getString(TWO));
        }
        return result;
    }

    /**
     *ResultSet 객체에서 clob형 data 를 얻는다.(Oracle 9i or Weblogic 인 경우)
     * 
     * @param column String형 컬럼명을 인자로 받는다
     *@return 컬럼명에 해당되는 data 를 반환한다
     */
    public String getCharacterStream(String column) throws Exception {
        Reader reader = null;
        StringWriter swriter = new StringWriter();
        String data = "";
        int ch = 0;
        int isNull = 0;

        try {
            reader = rs.getCharacterStream(column);
            if (reader != null) {
                while ((ch = reader.read()) != -1) {
                    swriter.write(ch);
                }
                data = swriter.toString();
            } else {
                isNull = 1;
            }
        } catch (Exception ex) {
            Log.sys.println(this, ex, "Happen to ListSet.getCharacterStream(" + column + "\")");
            throw new Exception("ListSet.getCharacterStream(\"" + column + "\")" + ex.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
            if (swriter != null) {
                try {
                    swriter.close();
                } catch (Exception e1) {
                }
            }
        }
        if (isNull == 1) {
            return "";
        } else {
            return this.chkNull(data);
        }
    }

    /**
     * Statement 객체에 Oracle의 Long Row형 Field(사진)을 읽을때 사용한다.(Only Oracle)
     * 
     * @parameter column 컬럼명을 인자로 받는다
     * @parameter name 파일명
     * @parameter terms 파일 확장자명
     */
    public String getClob(String column) throws Exception {
        Reader rd = null;
        char[] cbuf = null; // Blob buffer
        StringBuffer sb = new StringBuffer();
        try {

            int readcnt;
            cbuf = new char[1024];
            rd = rs.getCharacterStream(column);
            if (rd != null) {
                while ((readcnt = rd.read(cbuf, 0, 1024)) != -1) {
                    sb.append(cbuf, 0, readcnt);
                }
                rd.close();
            }
        } catch (Exception ex) {
            throw new Exception("ListSet.getClob : " + column + ", " + ex.getMessage());
        }
        return sb.toString();
    }

    /**
     * 현재 페이지 번호를 반환한다
     * 
     * @return current_page_num 현재 페이지 번호를 반환한다
     */
    public int getCurrentPage() {
        return current_page_num;
    }

    public Object getData(String type, String name, ResultSetMetaData meta, int columnCount) throws Exception {
        Object o = null;
        try {
            //System.out.println("name===============================>>>>>>"+name);
            //System.out.println("type===============================>>>>>>"+type);
            if (type.equals("varchar2")) {
                o = this.getString(name);
            } else if (type.equals("varchar")) {
                o = this.getString(name);
            } else if (type.equals("char")) {
                o = this.getString(name);
            } else if (type.equals("number")) {
                if (meta.getScale(columnCount) > 0) {
                    o = new Float(this.getFloat(name));
                } else {
                    o = new Integer(this.getInt(name));
                }
            } else if (type.equals("int")) {
                if (meta.getScale(columnCount) > 0) {
                    o = new Float(this.getFloat(name));
                } else {
                    o = new Integer(this.getInt(name));
                }
            } else if (type.equals("numeric")) {
                if (meta.getScale(columnCount) > 0) {
                    o = new Float(this.getFloat(name));
                } else {
                    o = new Integer(this.getInt(name));
                }
            } else if (type.equals("decimal")) {
                if (meta.getScale(columnCount) > 0) {
                    o = new Float(this.getFloat(name));
                } else {
                    o = new Integer(this.getInt(name));
                }
            } else if (type.equals("text")) {
                o = this.getString(name);
            } else if (type.equals("uniqueidentifier")) {
                o = this.getString(name);
            } else if (type.equals("clob")) {
                o = getClob(name);
            } else if (type.equals("date")) {
                o = this.getString(name);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("ListSet.getData()\r\n\"" + ex.getMessage());
        }
        return o;
    }

    /**
     *Select SQL 에서 컬럼마다 뽑은 데이터를 Hashtable 인 DataBox 에 담아 리턴한다.
     * 
     * @return dbox DataBox 객체를 반환한다
     */
    public DataBox getDataBox() throws Exception {
        DataBox dbox = null;
        int columnCount = 0;
        try {
            dbox = new DataBox("responsebox");

            ResultSetMetaData meta = this.getMetaData();
            columnCount = meta.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = meta.getColumnName(i).toLowerCase();
                String columnType = meta.getColumnTypeName(i).toLowerCase();

                Object data = this.getData(columnType, columnName, meta, i);

                dbox.put("d_" + columnName, data);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("ListSet.getDataBox()\r\n\"" + ex.getMessage());
        }
        return dbox;
    }

    /**
     * ResultSet 객체에서 double형 data 를 얻는다
     * 
     * @parameter n SQL쿼리로 호출된 컬럼순서를 인자로 받는다
     * @return 컬럼순서에 해당되는 data 를 반환한다
     */
    public double getDouble(int n) throws Exception {
        double data = 0.0;

        try {
            data = rs.getDouble(n);
        } catch (Exception ex) {
            throw new Exception("ListSet.getDouble(" + n + ")\r\n\"" + sQuery + "\"\r\n" + ex.getMessage());
        }
        return data;
    }

    /**
     * ResultSet 객체에서 double형 data 를 얻는다
     * 
     * @parameter column String형 컬럼명을 인자로 받는다
     * @return 컬럼명에 해당되는 data 를 반환한다
     */
    public double getDouble(String column) throws Exception {
        double data = 0.0;

        try {
            data = rs.getDouble(column);
        } catch (Exception ex) {
            throw new Exception("ListSet.getDouble(\"" + column + "\")\r\n\"" + sQuery + "\"\r\n" + ex.getMessage());
        }
        return data;
    }

    /**
     * ResultSet 객체에서 float형 data 를 얻는다
     * 
     * @parameter n SQL쿼리로 호출된 컬럼순서를 인자로 받는다
     * @return 컬럼순서에 해당되는 data 를 반환한다
     */
    public float getFloat(int n) throws Exception {
        float data = (float) 0.0;

        try {
            data = rs.getFloat(n);
        } catch (Exception ex) {
            throw new Exception("ListSet.getFloat(" + n + ")\r\n\"" + sQuery + "\"\r\n" + ex.getMessage());
        }
        return data;
    }

    /**
     * ResultSet 객체에서 float형 data 를 얻는다
     * 
     * @parameter column String형 컬럼명을 인자로 받는다
     * @return 컬럼명에 해당되는 data 를 반환한다
     */
    public float getFloat(String column) throws Exception {
        float data = (float) 0.0;

        try {
            data = rs.getFloat(column);
        } catch (Exception ex) {
            throw new Exception("ListSet.getFloat(\"" + column + "\")\r\n\"" + sQuery + "\"\r\n" + ex.getMessage());
        }
        return data;
    }

    /**
     * ResultSet 객체에서 int형 data 를 얻는다
     * 
     * @parameter n SQL쿼리로 호출된 컬럼순서를 인자로 받는다
     * @return 컬럼순서에 해당되는 data 를 반환한다
     */
    public int getInt(int n) throws Exception {
        int data = 0;
        try {
            data = rs.getInt(n);
            //			System.out.println("getInt:"+data);
        } catch (Exception ex) {
            throw new Exception("ListSet.getInt(" + n + ")\r\n\"" + sQuery + "\"\r\n" + ex.getMessage());
        }
        return data;
    }

    /**
     * ResultSet 객체에서 int형 data 를 얻는다
     * 
     * @parameter column String형 컬럼명을 인자로 받는다
     * @return 컬럼명에 해당되는 data 를 반환한다
     */
    public int getInt(String column) throws Exception {
        int data = 0;
        try {
            data = rs.getInt(column);
            //			System.out.println("getInt:"+data);
        } catch (Exception ex) {
            throw new Exception("ListSet.getInt(\"" + column + "\")\r\n\"" + sQuery + "\"\r\n" + ex.getMessage());
        }
        return data;
    }

    /**
     * ResultSet 객체에서 long형 data 를 얻는다
     * 
     * @parameter n SQL쿼리로 호출된 컬럼순서를 인자로 받는다
     * @return 컬럼순서에 해당되는 data 를 반환한다
     */
    public long getLong(int n) throws Exception {
        long data = 0;

        try {
            data = rs.getLong(n);
        } catch (Exception ex) {
            throw new Exception("ListSet.getLong(" + n + ")\r\n\"" + sQuery + "\"\r\n" + ex.getMessage());
        }
        return data;
    }

    /**
     * ResultSet 객체에서 long형 data 를 얻는다
     * 
     * @parameter column String형 컬럼명을 인자로 받는다
     * @return 컬럼명에 해당되는 data 를 반환한다
     */
    public long getLong(String column) throws Exception {
        long data = 0;

        try {
            data = rs.getLong(column);
        } catch (Exception ex) {
            throw new Exception("ListSet.getLong(\"" + column + "\")\r\n\"" + sQuery + "\"\r\n" + ex.getMessage());
        }
        return data;
    }

    /**
     * ResultSetMetaData 객체를 반환한다
     * 
     * @return md ResultSetMetaData 객체를 반환한다
     */
    public ResultSetMetaData getMetaData() throws Exception {
        ResultSetMetaData md = null;
        try {
            md = rs.getMetaData();
        } catch (Exception ex) {
            this.close();
            throw new Exception("ListSet.getMetaData()\r\n\"" + sQuery + "\"\r\n" + ex.getMessage());
        }
        return md;
    }

    /**
     * 전체 row 수를 반환한다
     * 
     * @return total_row_count 전체 row 수를 반환한다
     */
    public int getRowNum() {
        return current_row_num;
    }

    /**
     * ResultSet 객체에서 int형 data 를 얻는다
     * 
     * @parameter n SQL쿼리로 호출된 컬럼순서를 인자로 받는다
     * @return 컬럼순서에 해당되는 data 를 반환한다
     */
    public String getString(int n) throws Exception {
        String data = "";
        try {
            data = rs.getString(n);
        } catch (Exception ex) {
            throw new Exception("ListSet.getString(" + n + ")\r\n\"" + sQuery + "\"\r\n" + ex.getMessage());
        }
        return this.chkNull(data);
    }

    /**
     * ResultSet 객체에서 String형 data 를 얻는다
     * 
     * @parameter column String형 컬럼명을 인자로 받는다
     * @return 컬럼명에 해당되는 data 를 반환한다
     */
    public String getString(String column) throws Exception {
        String data = "";
        try {
            data = rs.getString(column);

            //System.out.println("column====>"+column);
            //System.out.println(data);
        } catch (Exception ex) {
            throw new Exception("ListSet.getString(\"" + column + "\")\r\n\"" + sQuery + "\"\r\n" + ex.getMessage());
        }
        return this.chkNull(data);
    }

    /**
     * 전체 row 수를 반환한다
     * 
     * @return total_row_count 전체 row 수를 반환한다
     */
    public int getTotalCount() {
        return total_row_count;
    }

    /**
     * 전체 페이지 수를 반환한다
     * 
     * @return total_page_count 전체 페이지 수를 반환한다
     */
    public int getTotalPage() {
        return total_page_count;
    }

    /**
     * 가장 처음 row 로 이동한다
     */
    public void moveFirst() throws Exception {
        try {
            rs.beforeFirst();
            current_row_num = 0;
        } catch (Exception ex) {
            this.close();
            throw new Exception("ListSet.moveFirst()\r\n\"" + sQuery + "\"\r\n" + ex.getMessage());
        }
    }

    /**
     * 커서를 다음 row 로 이동시킨다
     * 
     * @return b 다음 row 로 이동할수있는지 여부를 반환한다
     */
    public boolean next() throws Exception {
        boolean b = false;

        try {
            current_row_num++;

            if (b_bulletin) {
                b = (rs.next() && (current_row_num <= current_page_last_row_num));
            } else {
                b = rs.next();
            }
        } catch (Exception ex) {
            this.close();
            throw new Exception("ListSet.next()\r\n\"" + sQuery + "\"\r\n" + ex.getMessage());
        }
        return b;
    }

    /**
     * 페이지 나누기 로직을 실행한다
     */
    private void prepareBulletinQuery() throws Exception {
        try {
            // 데이터의 전체갯수 계산
            rs.last();
            total_row_count = rs.getRow();
            rs.beforeFirst();

            if (page_size == 0) {
                total_page_count = 1;
            } else {
                total_page_count = (total_row_count + page_size - 1) / page_size; // 전체페이지 갯수 계산
            }

            total_block_count = (total_page_count + block_size - 1) / block_size; // 전체 블럭갯수

            current_block_num = (current_page_num + block_size - 1) / block_size; // 현재 블럭번호

            if (page_size == 0) {
                current_page_last_row_num = total_row_count;
            } else {
                current_page_last_row_num = current_page_num * page_size; // 현재 페이지에서 출력될 마지막 데이터의 일련번호
            }

            if (current_page_num > 1) {
                int n_skip_size = (current_page_num - 1) * page_size;

                rs.absolute(n_skip_size);
                current_row_num = n_skip_size;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.close();
            throw new Exception("ListSet.prepareBulletinQuery()\r\n\"" + sQuery + "\"\r\n" + ex.getMessage());
        }
    }

    /**
     * 페이지 나누기 로직을 실행한다
     */
    private void prepareBulletinQuery(int p_total_row_count) throws Exception {
        try {
            // 데이터의 전체갯수 계산
            total_row_count = p_total_row_count;

            if (page_size == 0) {
                total_row_count = 1;
            } else {
                total_page_count = (total_row_count + page_size - 1) / page_size; // 전체페이지 갯수 계산
            }

            total_block_count = (total_page_count + block_size - 1) / block_size; // 전체 블럭갯수

            current_block_num = (current_page_num + block_size - 1) / block_size; // 현재 블럭번호

            if (page_size == 0) {
                current_page_last_row_num = total_row_count;
            } else {
                current_page_last_row_num = current_page_num * page_size; // 현재 페이지에서 출력될 마지막 데이터의 일련번호
            }

            if (current_page_num > 1) {
                int n_skip_size = (current_page_num - 1) * page_size;
                rs.absolute(n_skip_size);
                current_row_num = n_skip_size;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.close();
            throw new Exception("ListSet.prepareBulletinQuery()\r\n\"" + sQuery + "\"\r\n" + ex.getMessage());
        }
    }

    /**
     * Statement 객체에 SQL 쿼리를 실행해 ResultSet 객체를 생성한다
     * 
     * @parameter sql 쿼리를 인자로 받는다
     */
    public void run(String sql) throws Exception {
        sQuery = sql;
        b_bulletin = false;
        try {
            long startTime = System.currentTimeMillis();
            rs = stmt.executeQuery(sQuery);
            long runTime = (System.currentTimeMillis() - startTime);
            if (runTime > 2000) {
                Log.sys.println("****** 2초이상 SQL Start ******");
                Log.sys.println(sQuery);
                Log.sys.println(" # 조회시간(mills)  : " + runTime);
                Log.sys.println("****** SQL End ******");
            }
        } catch (Exception ex) {
            this.close();
            throw new Exception("ListSet.run(\"" + sQuery + "\")" + ex.getMessage());
        }
    }

    /**
     * 블럭당 page 수를 세팅한다
     * 
     * @parameter cnt 블럭당 page 수
     */
    public void setBlockSize(int cnt) {
        block_size = cnt;
    }

    /**
     * 현재페이지번호를 세팅한다.
     * 
     * @parameter number 현재 페이지번호
     */
    public void setCurrentPage(int number) throws Exception {
        b_bulletin = true;
        if (number == 0) {
            number = 1; //	  현재페이지번호가 0 일수없으므로 1 로 세팅한다
        }
        current_page_num = number;

        this.prepareBulletinQuery();
    }

    /**
     * 현재페이지번호를 세팅한다.
     * 
     * @parameter number 현재 페이지번호
     */
    public void setCurrentPage(int number, int totalrow) throws Exception {
        b_bulletin = true;
        if (number == 0) {
            number = 1; //	  현재페이지번호가 0 일수없으므로 1 로 세팅한다
        }
        current_page_num = number;

        this.prepareBulletinQuery(totalrow);
    }

    /**
     * 페이지당 row 갯수를 세팅한다
     * 
     * @parameter cnt 페이지당 row 수
     */
    public void setPageSize(int cnt) { // 페이지 사이즈가 0 이면 한화면에 전부를 출력한다.
        page_size = cnt;
    }
}
