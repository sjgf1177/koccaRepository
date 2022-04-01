//**********************************************************
//  1. ��	  ��: ResultSet  ��ü ����
//  2. ���α׷���: ListSet.java
//  3. ��	  ��: ResultSet  ��ü �����Ͽ� ������ �����⸦ �����Ѵ�
//  4. ȯ	  ��: JDK 1.3
//  5. ��	  ��: 1.0
//  6. ��	  ��: ������ 2003. 4. 26
//  7. ��	  ��: ������ 2003. 4. 26
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
 * ����Ÿ���̽��� ResultSet ��ü�� �����ϴ� ���̺귯��
 * 
 * @date : 2003. 4. 9
 * @author : j.h. lee
 */
@SuppressWarnings( { "unused" })
public class ListSet {
    private boolean b_bulletin = false; // �Խ����������� ����

    private int block_size = 10; // �� ���� ����������
    private int page_size = 10; // ���������� ������ row ����
    private int total_row_count = 0; // ������ ��ü row ����
    private int total_page_count = 0; // ������ ��ü ������ ����
    private int total_block_count = 0;// ������ ��ü ������
    private int current_page_num = 0; // ���� ������ ��ȣ
    private int current_block_num = 0; // ���� �� ��ȣ
    private int current_page_last_row_num = 0; // ���� �������� ������ ���� row ��ȣ
    private int current_row_num = 0; // ���� ������ row ��ȣ

    private PreparedStatement pstmt = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    private String sQuery = null;

    /**
     * Connection ��ü���� Statement ��ü�� �����Ѵ�
     * 
     * @parameter conn Connection ��ü�� ���ڷ� �޴´�
     */
    /*-------------------------------------------------------------*/
    /*
     * 2004.03.02 juwanpapa MS_SQL�� ���������м��� ���� ������ �߱�� ���Ͽ� ������ �����ε� ��Ŵ ex)
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
     * Connection ��ü���� Statement ��ü�� �����Ѵ�
     * 
     * @parameter conn Connection ��ü�� ���ڷ� �޴´�
     */
    public ListSet(Connection conn, String param) throws Exception {
        try {
            /* 2004.03.02 juwanpapa MS-SQL�� */
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            //stmt = conn.createStatement();//�⺻�������� �ٲ�
        } catch (Exception ex) {
            this.close();
            throw new Exception("ListSet.ListSet(conn,param)\r\n" + ex.getMessage());
        }
    }

    /**
     * Connection ���κ��� ������ PreparedStatement ��ü�� sql ������ �����Ѵ�
     * 
     * @parameter p PreparedStatement ��ü�� ���ڷ� �޴´�
     * @parameter sql ������ ���ڷ� �޴´�
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
     * Statement ��ü�� Oracle�� Long Row�� Field(����)�� ������ ����Ѵ�.(Only Oracle)
     * 
     * @parameter column �÷����� ���ڷ� �޴´�
     * @parameter name ���ϸ�
     * @parameter terms ���� Ȯ���ڸ�
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
     * Statement ��ü�� Oracle�� Long Row�� Field(����)�� ������ ����Ѵ�.(Only Oracle)
     * 
     * @parameter column �÷����� ���ڷ� �޴´�
     * @parameter name ���ϸ�
     * @parameter terms ���� Ȯ���ڸ�
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
     * Statement ��ü�� Oracle�� Long Row�� Field(����)�� ������ ����Ѵ�.(Only Oracle)
     * 
     * @parameter column �÷����� ���ڷ� �޴´�
     * @parameter name ���ϸ�
     * @parameter terms ���� Ȯ���ڸ�
     */
    /*
     * public String getBinaryFileStream(String column,String name,String terms)
     * throws Exception { ConfigSet conf = new ConfigSet(); InputStream gifdata
     * = null; String fullName = null; String v_updir = null; try { v_updir =
     * conf.getProperty("dir.upload");
     * 
     * fullName = v_updir + name + terms; gifdata = rs.getBinaryStream(column);
     * // gifdata = ((OracleResultSet)rs).getBinaryStream(column); // (Oracle
     * Jdbc driver �� ���)
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
     * ResultSet, Statement, PreparedStatement ��ü�� �ݴ´�
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
     * PreparedStatement ��ü�� SQL ������ ������ ResultSet ��ü�� �����Ѵ�
     * 
     * @parameter sql ������ ���ڷ� �޴´�
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
        int total_row_count = BoardPaging.getTotalRow(connMgr, sql, true); //     ��ü row ���� ��ȯ�Ѵ�
        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");
        setPageSize(v_pagesize); //  �������� row ������ �����Ѵ�
        setCurrentPage(v_pageno, total_row_count); //     ������������ȣ�� �����Ѵ�.

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
     * ������� Field1, Field2 �ϰ�� �̸鼭 Field1�� �ߺ����� ���� ��� ����� �� �ִ�. ��� ��ȸ����� Map��
     * Field1�� Key�� Field2�� �����ͷ� �־��ش�
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
     *ResultSet ��ü���� clob�� data �� ��´�.(Oracle 9i or Weblogic �� ���)
     * 
     * @param column String�� �÷����� ���ڷ� �޴´�
     *@return �÷��� �ش�Ǵ� data �� ��ȯ�Ѵ�
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
     * Statement ��ü�� Oracle�� Long Row�� Field(����)�� ������ ����Ѵ�.(Only Oracle)
     * 
     * @parameter column �÷����� ���ڷ� �޴´�
     * @parameter name ���ϸ�
     * @parameter terms ���� Ȯ���ڸ�
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
     * ���� ������ ��ȣ�� ��ȯ�Ѵ�
     * 
     * @return current_page_num ���� ������ ��ȣ�� ��ȯ�Ѵ�
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
     *Select SQL ���� �÷����� ���� �����͸� Hashtable �� DataBox �� ��� �����Ѵ�.
     * 
     * @return dbox DataBox ��ü�� ��ȯ�Ѵ�
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
     * ResultSet ��ü���� double�� data �� ��´�
     * 
     * @parameter n SQL������ ȣ��� �÷������� ���ڷ� �޴´�
     * @return �÷������� �ش�Ǵ� data �� ��ȯ�Ѵ�
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
     * ResultSet ��ü���� double�� data �� ��´�
     * 
     * @parameter column String�� �÷����� ���ڷ� �޴´�
     * @return �÷��� �ش�Ǵ� data �� ��ȯ�Ѵ�
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
     * ResultSet ��ü���� float�� data �� ��´�
     * 
     * @parameter n SQL������ ȣ��� �÷������� ���ڷ� �޴´�
     * @return �÷������� �ش�Ǵ� data �� ��ȯ�Ѵ�
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
     * ResultSet ��ü���� float�� data �� ��´�
     * 
     * @parameter column String�� �÷����� ���ڷ� �޴´�
     * @return �÷��� �ش�Ǵ� data �� ��ȯ�Ѵ�
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
     * ResultSet ��ü���� int�� data �� ��´�
     * 
     * @parameter n SQL������ ȣ��� �÷������� ���ڷ� �޴´�
     * @return �÷������� �ش�Ǵ� data �� ��ȯ�Ѵ�
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
     * ResultSet ��ü���� int�� data �� ��´�
     * 
     * @parameter column String�� �÷����� ���ڷ� �޴´�
     * @return �÷��� �ش�Ǵ� data �� ��ȯ�Ѵ�
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
     * ResultSet ��ü���� long�� data �� ��´�
     * 
     * @parameter n SQL������ ȣ��� �÷������� ���ڷ� �޴´�
     * @return �÷������� �ش�Ǵ� data �� ��ȯ�Ѵ�
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
     * ResultSet ��ü���� long�� data �� ��´�
     * 
     * @parameter column String�� �÷����� ���ڷ� �޴´�
     * @return �÷��� �ش�Ǵ� data �� ��ȯ�Ѵ�
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
     * ResultSetMetaData ��ü�� ��ȯ�Ѵ�
     * 
     * @return md ResultSetMetaData ��ü�� ��ȯ�Ѵ�
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
     * ��ü row ���� ��ȯ�Ѵ�
     * 
     * @return total_row_count ��ü row ���� ��ȯ�Ѵ�
     */
    public int getRowNum() {
        return current_row_num;
    }

    /**
     * ResultSet ��ü���� int�� data �� ��´�
     * 
     * @parameter n SQL������ ȣ��� �÷������� ���ڷ� �޴´�
     * @return �÷������� �ش�Ǵ� data �� ��ȯ�Ѵ�
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
     * ResultSet ��ü���� String�� data �� ��´�
     * 
     * @parameter column String�� �÷����� ���ڷ� �޴´�
     * @return �÷��� �ش�Ǵ� data �� ��ȯ�Ѵ�
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
     * ��ü row ���� ��ȯ�Ѵ�
     * 
     * @return total_row_count ��ü row ���� ��ȯ�Ѵ�
     */
    public int getTotalCount() {
        return total_row_count;
    }

    /**
     * ��ü ������ ���� ��ȯ�Ѵ�
     * 
     * @return total_page_count ��ü ������ ���� ��ȯ�Ѵ�
     */
    public int getTotalPage() {
        return total_page_count;
    }

    /**
     * ���� ó�� row �� �̵��Ѵ�
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
     * Ŀ���� ���� row �� �̵���Ų��
     * 
     * @return b ���� row �� �̵��Ҽ��ִ��� ���θ� ��ȯ�Ѵ�
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
     * ������ ������ ������ �����Ѵ�
     */
    private void prepareBulletinQuery() throws Exception {
        try {
            // �������� ��ü���� ���
            rs.last();
            total_row_count = rs.getRow();
            rs.beforeFirst();

            if (page_size == 0) {
                total_page_count = 1;
            } else {
                total_page_count = (total_row_count + page_size - 1) / page_size; // ��ü������ ���� ���
            }

            total_block_count = (total_page_count + block_size - 1) / block_size; // ��ü ������

            current_block_num = (current_page_num + block_size - 1) / block_size; // ���� ����ȣ

            if (page_size == 0) {
                current_page_last_row_num = total_row_count;
            } else {
                current_page_last_row_num = current_page_num * page_size; // ���� ���������� ��µ� ������ �������� �Ϸù�ȣ
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
     * ������ ������ ������ �����Ѵ�
     */
    private void prepareBulletinQuery(int p_total_row_count) throws Exception {
        try {
            // �������� ��ü���� ���
            total_row_count = p_total_row_count;

            if (page_size == 0) {
                total_row_count = 1;
            } else {
                total_page_count = (total_row_count + page_size - 1) / page_size; // ��ü������ ���� ���
            }

            total_block_count = (total_page_count + block_size - 1) / block_size; // ��ü ������

            current_block_num = (current_page_num + block_size - 1) / block_size; // ���� ����ȣ

            if (page_size == 0) {
                current_page_last_row_num = total_row_count;
            } else {
                current_page_last_row_num = current_page_num * page_size; // ���� ���������� ��µ� ������ �������� �Ϸù�ȣ
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
     * Statement ��ü�� SQL ������ ������ ResultSet ��ü�� �����Ѵ�
     * 
     * @parameter sql ������ ���ڷ� �޴´�
     */
    public void run(String sql) throws Exception {
        sQuery = sql;
        b_bulletin = false;
        try {
            long startTime = System.currentTimeMillis();
            rs = stmt.executeQuery(sQuery);
            long runTime = (System.currentTimeMillis() - startTime);
            if (runTime > 2000) {
                Log.sys.println("****** 2���̻� SQL Start ******");
                Log.sys.println(sQuery);
                Log.sys.println(" # ��ȸ�ð�(mills)  : " + runTime);
                Log.sys.println("****** SQL End ******");
            }
        } catch (Exception ex) {
            this.close();
            throw new Exception("ListSet.run(\"" + sQuery + "\")" + ex.getMessage());
        }
    }

    /**
     * ���� page ���� �����Ѵ�
     * 
     * @parameter cnt ���� page ��
     */
    public void setBlockSize(int cnt) {
        block_size = cnt;
    }

    /**
     * ������������ȣ�� �����Ѵ�.
     * 
     * @parameter number ���� ��������ȣ
     */
    public void setCurrentPage(int number) throws Exception {
        b_bulletin = true;
        if (number == 0) {
            number = 1; //	  ������������ȣ�� 0 �ϼ������Ƿ� 1 �� �����Ѵ�
        }
        current_page_num = number;

        this.prepareBulletinQuery();
    }

    /**
     * ������������ȣ�� �����Ѵ�.
     * 
     * @parameter number ���� ��������ȣ
     */
    public void setCurrentPage(int number, int totalrow) throws Exception {
        b_bulletin = true;
        if (number == 0) {
            number = 1; //	  ������������ȣ�� 0 �ϼ������Ƿ� 1 �� �����Ѵ�
        }
        current_page_num = number;

        this.prepareBulletinQuery(totalrow);
    }

    /**
     * �������� row ������ �����Ѵ�
     * 
     * @parameter cnt �������� row ��
     */
    public void setPageSize(int cnt) { // ������ ����� 0 �̸� ��ȭ�鿡 ���θ� ����Ѵ�.
        page_size = cnt;
    }
}
