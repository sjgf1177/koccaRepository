package com.credu.library;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;
import oracle.sql.CLOB;

/**
 * <p>
 * ����: DB Connection ���� ���̺귯��
 * </p>
 * <p>
 * ����:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Credu
 * </p>
 * 
 * @author ������
 *@date 2003. 12
 *@version 1.0
 */

public class DBConnectionManager {
    private String default_pool_name = null;
    private Connection conn = null;
    private DataSource source = null;
    private static int count = 0;
    private InitialContext initCtx = null;

    // private String start = "";

    /**
     * ������ �Լ� ȣ��� pool ���� Connection ��ü�� ��´�
     */
    public DBConnectionManager() throws Exception {
        this.setDefaultPoolName();
       // 		System.out.println("default_pool_name= " + default_pool_name);
        this.initialize(default_pool_name);
    }

    /**
     * ������ �Լ� ȣ��� pool ���� Connection ��ü�� ��´�
     */
    public DBConnectionManager(String poolName) throws Exception {
        default_pool_name = poolName;
        this.initialize(default_pool_name);
    }

    /**
     * SQL ���๮�� commit �Ѵ�.
     */
    public void commit() throws Exception {
        try {
            conn.commit();
        } catch (SQLException ex) {
            throw new Exception("DBConnectionManager.commit()\r\n" + ex.getMessage());
        }
    }

    /**
     * Statement ��ü�� �����ϰ� ��ȯ�Ѵ�
     */
    public Statement createStatement() throws Exception {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (Exception ex) {
            //Log.sys.println(this, ex, "Happen to DBConnectionManager.createStatement()");
            throw new Exception("DBConnectionManager.createStatement()\r\n" + ex.getMessage());
        }
        return stmt;
    }

    /**
     * ������ ��ȸ�� Statement, ResultSet ��ü�� �����ϰ� �����ϴ� ListSet ��ü�� ��ȯ�Ѵ�
     * 
     * @param sql ������ ���ڷ� �޴´�
     * @return ls ListSet ��ü�� ��ȯ�Ѵ�
     */
    public ListSet executeQuery(String sql) throws Exception {
        ListSet ls = null;
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");

        try {
            ConfigSet cs = new ConfigSet();
            if (cs.getBoolean("sql.excuteQuery.debugView")) {
                System.out.println("\n[" + sdf.format(dt).toString() + "]---- Excute Query Start ----\n" + sql + "\n---- Excute Query End ----\n");
            }
            ls = new ListSet(conn); // Statement ��ü�� ����
            ls.run(sql); //  ResultSet ��ü�� ����
        } catch (Exception ex) {
            //Log.sys.println(this, ex, "Happen to DBConnectionManager.executeQuery(String sql)" + sql);
            throw new Exception("DBConnectionManager.executeQuery()\r\n\"" + sql + "\"\r\n" + ex.getMessage());
        }
        return ls;
    }

    /**
     * ������ ��ȸ�� Statement, ResultSet ��ü�� �����ϰ� �����ϴ� ListSet ��ü�� ��ȯ�Ѵ�
     * 
     * @param sql ������ ���ڷ� �޴´�
     * @return ls ListSet ��ü�� ��ȯ�Ѵ�
     */
    public ListSet executeQueryByInsecitive(String sql) throws Exception {
        ListSet ls = null;
        try {
            ConfigSet cs = new ConfigSet();
            if (cs.getBoolean("sql.excuteQuery.debugView")) {
                System.out.println("\n---- Excute Query Start ----\n" + sql + "\n---- Excute Query End ----\n");
            }
            ls = new ListSet(conn, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // Statement ��ü�� ����
            ls.run(sql); //  ResultSet ��ü�� ����
        } catch (Exception ex) {
            //Log.sys.println(this, ex, "Happen to DBConnectionManager.executeQuery(String sql)" + sql);
            throw new Exception("DBConnectionManager.executeQuery()\r\n\"" + sql + "\"\r\n" + ex.getMessage());
        }
        return ls;
    }

    public ListSet executeQuery(String sql, boolean logyn) throws Exception {
        ListSet ls = null;
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");
        try {
            if (logyn) {
                System.out
                        .println("\n[" + sdf.format(dt).toString() + "]---- Excute Query Start ---- \n" + sql + "\n---- Excute Query End ---- \n");
            }
            ls = new ListSet(conn); // Statement ��ü�� ����
            ls.run(sql); //  ResultSet ��ü�� ����
        } catch (Exception ex) {
            //Log.sys.println(this, ex, "Happen to DBConnectionManager.executeQuery(String sql)" + sql);
            throw new Exception("DBConnectionManager.executeQuery()\r\n\"" + sql + "\"\r\n" + ex.getMessage());
        }
        return ls;
    }

    /**
     * ������ ��ȸ�� Statement, ResultSet ��ü�� �����ϰ� �����ϴ� ListSet ��ü�� ��ȯ�Ѵ�
     * 
     * @param sql ������ ���ڷ� �޴´�
     * @return ls ListSet ��ü�� ��ȯ�Ѵ�
     */
    public ListSet executeQuery(String sql, RequestBox box) throws Exception {
        boolean cotYn = sql.indexOf("':p_") != -1 || sql.indexOf("':s_") != -1;
        return executeQuery(sql, box, cotYn);
    }

    /**
     * ������ ��ȸ�� Statement, ResultSet ��ü�� �����ϰ� �����ϴ� ListSet ��ü�� ��ȯ�Ѵ�
     * 
     * @param sql ������ ���ڷ� �޴´�
     * @return ls ListSet ��ü�� ��ȯ�Ѵ�
     */
    public ListSet executeQuery(String sql, RequestBox box, boolean cotYn) throws Exception {
        ListSet ls = null;
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");
        try {
            sql = replaceParam(sql, box, cotYn);
            ls = new ListSet(conn); // Statement ��ü�� ����
            ConfigSet cs = new ConfigSet();
            if (cs.getBoolean("sql.excuteQuery.debugView")) {
                System.out.println("\n[" + sdf.format(dt).toString() + "]---- Excute Query Start ----\n" + sql + "\n---- Excute Query End ----\n");
            }
            ls.run(sql); //  ResultSet ��ü�� ����
        } catch (Exception ex) {
            throw new Exception("DBConnectionManager.executeQuery()\r\n\"" + sql + "\"\r\n" + ex.getMessage());
        }
        return ls;
    }

    public ListSet executeQuery(String sql, String string) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("\n\n\n\n\n\n ����� ����~~~ executeQuery(String sql, String string)  ����� �Ͱ��ּ�~~~\n\n\n\n\n\n");
        return this.executeQuery(sql);
    }

    /**
     * Statement ��ü�� �����ϰ� �־��� ���������� �Է�, ����, ������ �����ϰ� row ���� ��ȯ�Ѵ�. Statement ��ü��
     * �ݴ´�
     * 
     * @param sql ������ ���ڷ� �޴´�
     * @return result int�� DB�� ����� row ���� ��ȯ�Ѵ�
     */
    public int executeUpdate(String sql) throws Exception {
        int result = 0;
        Statement stmt = null;
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");

        try {
            System.out.println("\n[" + sdf.format(dt).toString() + "]---- Excute Query Start ----\n" + sql + "\n---- Excute Query End ----\n");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            result = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            //Log.sys.println(this, ex, "Happen to DBConnectionManager.executeUpdate()" + sql);
            throw new Exception("DBConnectionManager.executeUpdate()\r\n\"" + sql + "\"\r\n" + ex.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                }
            }
        }
        return result;
    }

    /**
     * ���ؼ�Ǯ�� Connection ��ü�� �����ش�
     */
    public void freeConnection() throws Exception {
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (Exception ex) {
            }
        }
        count--;
        //		System.out.println("freeConn : " + count + " | " + FormatDate.getDate("yyyy/MM/dd HH:mm:ss") + " | " + FormatDate.getSecDifference(start, FormatDate.getDate("yyyyMMddHHmmss")));
        conn = null;
    }

    public Connection getConnection() {
        return this.conn;
    }

    /**
     * DatabaseMetaData ��ü�� ��ȯ�Ѵ�
     * 
     * @return md DatabaseMetaData ��ü�� ��ȯ�Ѵ�
     */
    public DatabaseMetaData getMetaData() throws Exception {
        DatabaseMetaData md = null;
        try {
            md = conn.getMetaData();
        } catch (SQLException ex) {
            throw new Exception("DBConnectionManager.getMetaData()\r\n" + ex.getMessage());
        }
        return md;
    }

    private void initialize(String poolName) throws Exception {
        try {
            //------------- resin -----------------------------------------------------------------------
            //env=(Context) new InitialContext().lookup("java:comp/env");
            //source=(DataSource)env.lookup("jdbc/"+poolName);

            //-------------- weblogic ---------------------------------------------------------------------
            //initCtx = new InitialContext();
            //			System.out.println("poolName = " + poolName);
            //source = (DataSource) initCtx.lookup(poolName);
        	//conn = source.getConnection();
            //----------------------------------------------------------------------------------------------------

        	Context initCtx = new InitialContext();
        	Context envCtx = (Context)initCtx.lookup("java:comp/env");
        	DataSource ds = (DataSource)envCtx.lookup("jdbc/"+poolName);
        	//System.out.println("poolName : " +poolName);
            conn = ds.getConnection();
            
            count++;
            // start = FormatDate.getDate("yyyyMMddHHmmss");
            // System.out.println("getConn : " + count);
        } catch (Exception ex) {
            //Log.sys.println(this, ex, "Happen to DBConnectionManager.initialize(\"" + poolName + "\")");
            ex.printStackTrace();
        }
    }

    /**
     * PreparedStatement ��ü�� �����ϰ� ��ȯ�Ѵ�
     * 
     * @param sql ������ ���ڷ� �޴´�
     */
    public PreparedStatement prepareStatement(String sql) throws Exception {
        PreparedStatement pstmt = null;
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");

        try {
            ConfigSet cs = new ConfigSet();
            if (cs.getBoolean("sql.excuteQuery.debugView")) {
                System.out.println("\n[" + sdf.format(dt).toString() + "]---- prepareStatement Start ----\n" + sql
                        + "\n---- prepareStatement End ----\n");
            }
            pstmt = conn.prepareStatement(sql);
        } catch (Exception ex) {
            //Log.sys.println(this, ex, "Happen to DBConnectionManager.prepareStatement(String sql)" + sql);
            throw new Exception("DBConnectionManager.prepareStatement(\"" + sql + "\")\r\n" + ex.getMessage());
        }
        return pstmt;
    }

    /**
     * PreparedStatement ��ü�� �����ϰ� ��ȯ�Ѵ�
     * 
     * @param sql ������ ���ڷ� �޴´�
     */
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws Exception {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
        } catch (Exception ex) {
            //Log.sys.println(this, ex, "Happen to DBConnectionManager.prepareStatement(String sql)" + sql);
            throw new Exception("DBConnectionManager.prepareStatement(\"" + sql + "\")\r\n" + ex.getMessage());
        }
        return pstmt;
    }

    public PreparedStatement prepareStatement(String sql, RequestBox box) throws Exception {
        PreparedStatement pstmt = null;
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");

        try {

            sql = replaceParam(sql, box);
            ConfigSet cs = new ConfigSet();
            if (cs.getBoolean("sql.excuteQuery.debugView")) {
                System.out.println("\n[" + sdf.format(dt).toString() + "]---- prepareStatement Start ----\n" + sql
                        + "\n---- prepareStatement End ----\n");
            }
            pstmt = conn.prepareStatement(sql);
        } catch (Exception ex) {
            //Log.sys.println(this, ex, "Happen to DBConnectionManager.prepareStatement(String sql)" + sql);
            throw new Exception("DBConnectionManager.prepareStatement(\"" + sql + "\")\r\n" + ex.getMessage());
        }
        return pstmt;
    }

    public String replaceParam(String sql, RequestBox box) throws Exception {
        boolean cotYn = sql.indexOf("':p_") != -1 || sql.indexOf("':s_") != -1;
        return replaceParam(sql, box, cotYn);
    }

    public String replaceParam(String sql, RequestBox box, boolean cotYn) throws Exception {
        String p1 = null;
        String p2 = null;
        if (cotYn) {
            p1 = "[']:";
            p2 = "[']";
        } else {
            p1 = ":";
            p2 = "";
        }
        return replaceParam(sql, box, p1, p2);
    }

    public String replaceParam(String sql, RequestBox box, String p) throws Exception {
        return replaceParam(sql, box, "[" + p + "]:", "[" + p + "]");
    }

    @SuppressWarnings("unchecked")
    public String replaceParam(String sql, RequestBox box, String p1, String p2) throws Exception {
        box.put("s_userid", box.getSession("userid"));
        Object[] param = box.keySet().toArray();

        for (Object element : param) {
            //		  		System.out.println(p1 + param[i] + p2 + " ------> " +  box.getQueryString( (String)param[i]));
            sql = sql.replaceAll(p1 + element + p2, box.getQueryString((String) element));
            //		  		System.out.println(sql);
        }
        return sql;
    }

    public String replaceParam(StringBuffer sql, RequestBox box) throws Exception {
        return replaceParam(sql.toString(), box);
    }

    /**
     * PreparedStatement�� ���ÿϷ�� ���� ����� �˾ƺ��� ���� ����� ����̴�.(�ʼ� �ƴ�) - ���� �⺻ ���븦 �����ϴ�
     * �κ�.
     * 
     * @param sql
     * @return
     */
    public String debugSqlMake(String sql) {
        sql = "\n---- debugSqlMake Start ----\n" + sql + "\n---- debugSqlMake End ----\n";
        return sql;
    }

    /**
     * PreparedStatement�� ���ÿϷ�� ���� ����� �˾ƺ��� ���� ����� ����̴�.(�ʼ� �ƴ�) - ���� �������
     * 
     * @param pstmt : �ش� sql�� ������ PreparedStatement
     * @param sql : debugSqlMake(sql)�� ������ sql
     * @param value : pstmt.setString�� ���� ������ sql���� ?���� ġȯ�� ��.
     * @param index : pstmt.setString�� ���� �ε���.
     * @return
     * @throws Exception
     */
    public String debugSql(PreparedStatement pstmt, String sql, String value, int index) throws Exception {

        pstmt.setString(index, value);
        sql = sql.replaceFirst("[?]", "'" + value + "'");

        return sql;
    }

    /**
     * SQL ���๮�� rollback �Ѵ�.
     */
    public void rollback() throws Exception {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            throw new Exception("DBConnectionManager.rollback()\r\n" + ex.getMessage());
        }
    }

    /**
     * DB Ʈ������� AutoCommit ���θ� �����Ѵ�.
     * 
     * @param autoCommit AutoCommit ���θ� ����
     */
    public void setAutoCommit(boolean autoCommit) throws Exception {
        try {
            conn.setAutoCommit(autoCommit);
        } catch (SQLException ex) {
            throw new Exception("DBConnectionManager.setAutoCommit()\r\n" + ex.getMessage());
        }
    }

    /**
     * PreparedStatement ��ü�� Oracle�� blob�� Field�� �����Ҷ� ����Ѵ�.(Oracle 9i or
     * Weblogic 6.1 �� ���)
     * 
     * @param p_pstmt PreparedStatement ��ü�� ���ڷ� �޴´�
     * @param index index �� ���ڷ� �޴´�
     * @param data String�� data �� ���ڷ� �޴´�
     */
    public void setBinaryStream(PreparedStatement p_pstmt, int index, String data) throws Exception {
        byte[] buf = data.getBytes();//System.out.println("buf : " + buf.length);
        ByteArrayInputStream bais = new ByteArrayInputStream(buf);//System.out.println("bais : " + bais);
        try {
            p_pstmt.setBinaryStream(index, bais, buf.length);
        } catch (Exception ex) {
            //Log.sys.println(this, ex, "Happen to DBConnectionManager.setBinaryStream("+index+",\""+data+"\")");
            throw new Exception("DBConnectionManager.setBinaryStream(" + index + ",\"" + data + "\")\r\n" + ex.getMessage());
        } finally {
            if (bais != null) {
                try {
                    bais.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * PreparedStatement ��ü�� Oracle�� clob�� Field�� �����Ҷ� ����Ѵ�.(Oracle 9i or
     * Weblogic 6.1 �� ���)
     * 
     * @param p_pstmt PreparedStatement ��ü�� ���ڷ� �޴´�
     * @param index index �� ���ڷ� �޴´�
     * @param data String�� data �� ���ڷ� �޴´�
     */
    public void setCharacterStream(PreparedStatement p_pstmt, int index, String data) throws Exception {
        StringReader sreader = new StringReader(data);
        try {
            p_pstmt.setCharacterStream(index, sreader, data.length());
        } catch (Exception ex) {
            //Log.sys.println(this, ex, "Happen to DBConnectionManager.setCharacterStream("+index+",\""+data+"\")");
            throw new Exception("DBConnectionManager.setCharacterStream(" + index + ",\"" + data + "\")\r\n" + ex.getMessage());
        } finally {
            if (sreader != null) {
                try {
                    sreader.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public void setDefaultPoolName() throws Exception {
        ConfigSet conf = new ConfigSet();
        try {
            if (default_pool_name == null) {
                default_pool_name = conf.getProperty("pool.name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * PreparedStatement ��ü�� Oracle�� blob�� Field�� �����Ҷ� ����Ѵ�.(Oracle Jdbc driver
     * �� ���, Weblogic ����)
     * 
     * @param sql SQL��
     * @param p_content String�� data �� ���ڷ� �޴´�
     */
    public void setOracleBLOB(String sql, String p_content) throws Exception { //  (Oracle Jdbc driver �� ���)
        Statement stmt = null;
        ResultSet rset = null;
        BLOB blob = null;
        ByteArrayInputStream instream = null;
        OutputStream outstream = null;

        try {
            stmt = conn.createStatement();

            rset = stmt.executeQuery(sql);
            rset.next();

            //Get the BLOB locator.
            blob = ((OracleResultSet) rset).getBLOB(1);

            byte[] buf = p_content.getBytes();
            instream = new ByteArrayInputStream(buf);

            //Insert to the BLOB from an output stream.
            outstream = blob.getBinaryOutputStream();

            //Read the input stream and write the output stream by chunks.
            byte[] chunk = new byte[blob.getChunkSize()];
            int i = -1;

            while ((i = instream.read(chunk)) != -1) {
                outstream.write(chunk, 0, i);
            }
        } catch (Exception ex) {
            //Log.sys.println(this, ex, "Happen to DBConnectionManager.setOracleBLOB()" + sql);
            throw new Exception("DBConnectionManager.setOracleBLOB()\r\n" + ex.getMessage());
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception e) {
                }
            }
            if (outstream != null) {
                try {
                    outstream.close();
                } catch (Exception e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * PreparedStatement ��ü�� Oracle�� clob�� Field�� �����Ҷ� ����Ѵ�.(Oracle Jdbc driver��
     * ���, Weblogic ����)
     * 
     * @param sql SQL��
     * @param p_content String�� data �� ���ڷ� �޴´�
     */
    public void setOracleCLOB(String sql, String p_content) throws Exception { // (Oracle Jdbc driver �� ���)
        Statement stmt = null;
        ResultSet rset = null;
        CLOB clob = null;
        StringReader sreader = null;
        Writer writer = null;

        try {
            stmt = conn.createStatement();

            rset = stmt.executeQuery(sql);
            rset.next();

            //Get the CLOB locator.
            clob = ((OracleResultSet) rset).getCLOB(1);

            sreader = new StringReader(p_content);

            writer = clob.getCharacterOutputStream();

            char[] chunk = new char[clob.getChunkSize()];
            int i = -1;
            while ((i = sreader.read(chunk)) != -1) {
                writer.write(chunk, 0, i);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //Log.sys.println(this, ex, "Happen to DBConnectionManager.setOracleCLOB()" + sql);
            throw new Exception("DBConnectionManager.setOracleCLOB()\r\n" + ex.getMessage());
        } finally {
            if (sreader != null) {
                try {
                    sreader.close();
                } catch (Exception e) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * PreparedStatement ��ü�� Oracle�� clob�� Field�� �����Ҷ� ����Ѵ�.(Weblogic Jdbc
     * driver �� ���)
     * 
     * @param sql SQL��
     * @param p_content String�� data �� ���ڷ� �޴´�
     */
    @SuppressWarnings("null")
    public void setWeblogicCLOB(String sql, String p_content) throws Exception { // (Weblogic Jdbc driver �� ���)
        Statement stmt = null;
        ResultSet rset = null;
        // Object clob = null;
        StringReader sreader = null;
        Writer writer = null;

        try {
            stmt = conn.createStatement();

            rset = stmt.executeQuery(sql);
            rset.next();

            // clob = rset.getClob(1);
            //			System.out.println(clob.getClass().getName());

            /*
             * sreader = new StringReader(p_content);
             * 
             * writer = clob.getCharacterOutputStream();
             * 
             * char [] buffer = new char [1024]; int read = 0; while ((read =
             * sreader.read(buffer)) != -1) { writer.write(buffer, 0, read); }
             * sreader.close(); writer.close();
             */

            /**
             * ���������� ���������� �����Ƿ� 10.3 ���������� �Ұ����ϴ�. ó���������
             * pstmt1.setCharacterStream(5, new StringReader(v_contents),
             * v_contents.length()); �� ���� long type�� ���� ������� ó���Ѵ�.
             * */
            //			writer = ((weblogic.jdbc.common.OracleClob)clob).getCharacterOutputStream();
            //			writer = ((OracleClob)clob).getCharacterOutputStream();

            char[] b = p_content.toCharArray(); // converts 's' to a character array

            writer.write(b);
            writer.flush();

            writer.close();
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
            //Log.sys.println(this, ex, "Happen to DBConnectionManager.setWeblogicCLOB()" + sql);
            throw new Exception("DBConnectionManager.setWeblogicCLOB()\r\n" + ex.getMessage());
        } finally {
            if (sreader != null) {
                try {
                    sreader.close();
                } catch (Exception e) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Connection �� ��ȿ���θ� ��ȯ�Ѵ�
     * 
     * @return conn Connection ��ü�� ��ȯ�Ѵ�
     */
    public boolean valid() {
        return (conn != null);
    }
}
