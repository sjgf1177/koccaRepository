package com.credu.library;

import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 프로젝트명 : kocca_java 패키지명 : com.credu.library 파일명 : DatabaseExecute.java 작성날짜 :
 * 2011. 9. 26. 처리업무 : 수정내용 :
 * 
 * Copyright by CREDU.Co., LTD. ALL RIGHTS RESERVED.
 */
@SuppressWarnings("unchecked")
public class DatabaseExecute {
    protected RequestBox box;
    protected DBConnectionManager connMgr;
    private Hashtable h_sql;
    private Hashtable h_isCommit;
    // private ConfigSet config;
    // private boolean isWeblogic = false;

    /**
     * 생성자 함수
     */
    public DatabaseExecute() {
        try {
            // config = new ConfigSet();
            // isWeblogic = config.getBoolean("was.isweblogic"); //       서버가 웹로직인지 여부 확인
        } catch (Exception e) {
            System.out.println("ExecuteMulti.ExecuteMulti(): DB Error" + e);
        }
    }

    /**
     * 생성자 함수
     * 
     * @param RequestBox box
     */
    public DatabaseExecute(RequestBox box) {
        try {
            this.box = box;
            // config = new ConfigSet();
            // isWeblogic = config.getBoolean("was.isweblogic"); //       서버가 웹로직인지 여부 확인
        } catch (Exception e) {
            System.out.println("ExecuteMulti.ExecuteMulti(): DB Error" + e);
        }
    }

    /**
     * Table Metadata 를 기본으로 Insert, Update, Delete SQL 실행
     * 
     * @parameter String sql
     * @return boolean doCommit commit 여부 반환
     */
    public boolean executeUpdate() throws Exception {
        boolean doCommit = true;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        String v_tableName = "";
        int columnCount = 0;
        String[] columnName = null;
        String[] columnType = null;
        // String[] value = null;

        try {
            if (connMgr == null)
                this.connMgr = new DBConnectionManager();

            Vector tables = box.getVector("p_table");

            if (tables.size() > 1)
                connMgr.setAutoCommit(false); //      다수 테이블 실행할 때 트랜젝션 처리

            h_sql = new Hashtable();
            h_isCommit = new Hashtable();

            for (int i = 0; i < tables.size(); i++) {
                v_tableName = (String) tables.elementAt(i); //      테이블명 뽑는다.

                //ls = connMgr.executeQuery("select * from " + v_tableName + " where rownum=1");
                ls = connMgr.executeQuery("select top 1 * from " + v_tableName);

                ResultSetMetaData meta = ls.getMetaData();

                columnCount = meta.getColumnCount();
                columnName = new String[columnCount];
                columnType = new String[columnCount];
                // value = new String[columnCount];

                for (int j = 1; j <= columnCount; j++) { //      테이블정보 가져온다.
                    columnName[j - 1] = meta.getColumnName(j).toLowerCase();
                    columnType[j - 1] = meta.getColumnTypeName(j).toLowerCase();
                }
                this.startSQL(v_tableName, columnName, columnType);
            }

            if (!box.getBoolean("notTransaction")) {
                doCommit = this.isCommit();
            }

            if (doCommit && tables.size() > 1) {
                connMgr.commit();
            }

            if (!doCommit) {
                connMgr.rollback();
            }
        } catch (Exception e) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            e.printStackTrace();
            throw new Exception("DatabaseExecute.executeUpdate()\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
            connMgr = null;
        }
        return doCommit;
    }

    private void startSQL(String p_tableName, String[] p_columnName, String[] p_columnType) throws Exception {
        PreparedStatement pstmt = null;
        String command = "";
        Vector columnNames = null;
        Vector columnTypes = null;
        String sql = "";
        String pstmtSet = "";
        String v_executeColumn = "";
        int isOk = 0;
        int beforeIsOk = 0;

        try {
            command = box.getString(p_tableName);
            columnNames = new Vector();
            columnTypes = new Vector();

            //-------------------------------------------------------------------     INSERT   ---------------------------------------------------------------------------------------------------------------
            if (command.equals("insert")) {
                sql = "insert into " + p_tableName + " (";

                for (int i = 0; i < p_columnName.length; i++) {
                    v_executeColumn = this.getJustColumn(p_tableName, p_columnName[i]); //      실행해야할 컬럼명 뽑는다.

                    if (!v_executeColumn.equals("")) {
                        columnNames.add(v_executeColumn); //      실제 실행할 컬럼명들을 만든다.
                        columnTypes.add(p_columnType[i]); //      실제 실행할 컬럼들의 타입을 만든다.
                    }
                }
                for (int i = 0; i < columnNames.size(); i++) {
                    if (i < columnNames.size() - 1) {
                        sql += (String) columnNames.elementAt(i) + ", ";
                    } else {
                        sql += (String) columnNames.elementAt(i) + ") values (";
                    }
                }
                for (int i = 0; i < columnNames.size(); i++) {
                    if (i < columnNames.size() - 1) {
                        sql += "?, ";
                    } else {
                        sql += "?) ";
                    }
                }//System.out.println("sql " + sql);
            }
            //-------------------------------------------------------------------     UPDATE   ---------------------------------------------------------------------------------------------------------------
            else if (command.equals("update")) {
                sql = "update " + p_tableName + " set ";

                for (int i = 0; i < p_columnName.length; i++) {
                    v_executeColumn = this.getJustColumn(p_tableName, p_columnName[i]); //      실행해야할 컬럼명 뽑는다.

                    if (!v_executeColumn.equals("")) {
                        columnNames.add(v_executeColumn); //      실제 실행할 컬럼명들을 만든다.
                        columnTypes.add(p_columnType[i]); //      실제 실행할 컬럼들의 타입을 만든다.
                    }
                }
                for (int i = 0; i < columnNames.size(); i++) {
                    if (i < columnNames.size() - 1) {
                        sql += (String) columnNames.elementAt(i) + " = ?, ";
                    } else {
                        sql += (String) columnNames.elementAt(i) + " = ? where ";
                    }
                }

                for (int i = 0; i < p_columnName.length; i++) {
                    String v_whereColumn = this.getWhereColumn(p_tableName, p_columnName[i]); //      실행해야할 컬럼명 뽑는다.

                    if (!v_whereColumn.equals("")) {
                        columnNames.add(v_whereColumn); //      실제 조건절  컬럼명들을 만든다.
                        columnTypes.add(p_columnType[i]); //      실제 조건절 컬럼들의 타입을 만든다.
                    }
                }

                int getWhereColumnCount = box.getVector(p_tableName + "_where").size(); //      where 조건의 컬럼수 뽑는다.

                for (int i = 0; i < getWhereColumnCount; i++) {
                    if (i < getWhereColumnCount - 1) {
                        sql += (String) columnNames.elementAt(columnNames.size() - getWhereColumnCount + i) + " = ? and ";
                    } else {
                        sql += (String) columnNames.elementAt(columnNames.size() - getWhereColumnCount + i) + " = ? ";
                    }
                }//    System.out.println("sql " + sql);
            }
            //-------------------------------------------------------------------     DELETE   ---------------------------------------------------------------------------------------------------------------
            else if (command.equals("delete")) {
                sql = "delete from " + p_tableName + " where ";

                for (int i = 0; i < p_columnName.length; i++) {
                    String v_whereColumn = this.getWhereColumn(p_tableName, p_columnName[i]); //      실행해야할 컬럼명 뽑는다.

                    if (!v_whereColumn.equals("")) {
                        columnNames.add(v_whereColumn); //      실제 조건절  컬럼명들을 만든다.
                        columnTypes.add(p_columnType[i]); //      실제 조건절 컬럼들의 타입을 만든다.
                    }
                }

                int getWhereColumnCount = box.getVector(p_tableName + "_where").size(); //      where 조건의 컬럼수 뽑는다.

                for (int i = 0; i < getWhereColumnCount; i++) {
                    if (i < getWhereColumnCount - 1) {
                        sql += (String) columnNames.elementAt(columnNames.size() - getWhereColumnCount + i) + " = ? and ";
                    } else {
                        sql += (String) columnNames.elementAt(columnNames.size() - getWhereColumnCount + i) + " = ? ";
                    }
                } //System.out.println("sql " + sql);
            }

            String v_vectorColumnName = this.getVectorColumn(p_tableName); //      vector형(다수 record 실행)의 컬럼명 뽑는다.
            int rowCount = (v_vectorColumnName.equals("") ? 1 : box.getVector("p_" + v_vectorColumnName).size()); //      vector형(다수 row 실행)의 실행수

            pstmt = connMgr.prepareStatement(sql);

            //--------------------  다수의 row 에 적용하기 위해서 vector 의 size 만큼 실행한다.
            for (int m = 0; m < rowCount; m++) {
                Vector v_values = null;
                for (int n = 0; n < columnNames.size(); n++) {
                    String str = "";

                    String v_name = (String) columnNames.elementAt(n); //System.out.println("v_name " + v_name);
                    String v_type = (String) columnTypes.elementAt(n); //System.out.println("v_name " + v_name);
                    v_values = box.getVector("p_" + v_name);

                    String v_value = (v_values.size() > 1 ? (String) v_values.elementAt(m) : (String) v_values.elementAt(0));

                    str = this.setData(pstmt, n + 1, v_type, v_value);
                    pstmtSet += str + " \r\n";
                    //    System.out.println("pstmt.set(" + (n+1) + ", " + v_value + ")");
                }
                beforeIsOk = isOk;

                isOk = pstmt.executeUpdate();

                isOk = isOk + beforeIsOk;
            }
        } catch (Exception e) {
            //            e.printStackTrace();
            throw new Exception("DatabaseExecute.startSQL()\r\n" + e.getMessage());
        } finally {
            h_sql.put(sql, pstmtSet);
            h_isCommit.put(sql, new Integer(isOk));
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Table Metadata 를 기본으로 Insert, Update, Delete SQL 실행
     * 
     * @parameter RequestBox box
     * @return boolean doCommit commit 여부 반환
     */
    public boolean executeUpdate(RequestBox box) throws Exception {
        this.box = box;
        return this.executeUpdate();
    }

    /**
     * 파라미터인 SQL 에 따라 실행됨, Clob 필드에도 적용됨
     * 
     * @parameter PreparedBox pbox PreparedStatement 실행시 치환될 변수
     * @parameter String p_sql
     * @return boolean doCommit commit 여부 반환
     */
    public boolean executeUpdate(PreparedBox pbox, String p_sql) throws Exception {
        PreparedStatement pstmt = null;
        boolean doCommit = true;
        int vectorSize = 1;
        String pstmtSet = "";
        int isOk = 0;

        try {
            if (connMgr == null)
                this.connMgr = new DBConnectionManager();

            pstmt = connMgr.prepareStatement(p_sql);

            h_sql = new Hashtable();
            h_isCommit = new Hashtable();

            Collection c = pbox.values();
            Object[] obj = c.toArray();
            //System.out.println("obj.length " + obj.length);
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] instanceof Vector) {
                    vectorSize = ((Vector) obj[i]).size();
                    break;
                }
            }
            //System.out.println("vectorSize " +vectorSize);System.out.println("pbox.size() " +pbox.size());
            for (int m = 0; m < vectorSize; m++) {
                for (int n = 1; n <= pbox.size(); n++) {
                    Object o = pbox.get(new Integer(n));
                    String v_value = "";
                    String str = "";

                    if (o instanceof Integer) {
                        v_value = ((Integer) o).toString();
                        str = this.setData(pstmt, n, "number", v_value);
                    } else if (o instanceof Float) {
                        v_value = ((Float) o).toString();
                        str = this.setData(pstmt, n, "float", v_value);
                    } else if (o instanceof Double) {
                        v_value = ((Double) o).toString();
                        str = this.setData(pstmt, n, "double", v_value);
                    } else if (o instanceof String) {
                        v_value = (String) o;
                        str = this.setData(pstmt, n, "varchar2", v_value);
                    } else if (o instanceof Vector) {
                        //System.out.println("value " +((Vector)o).elementAt(m));
                        Log.info.println("value " + ((Vector) o).elementAt(m));
                        Object obj2 = ((Vector) o).elementAt(m);

                        if (obj2 instanceof Integer) {
                            v_value = ((Integer) obj2).toString();
                            str = this.setData(pstmt, n, "number", v_value);
                        } else if (obj2 instanceof Float) {
                            v_value = ((Float) obj2).toString();
                            str = this.setData(pstmt, n, "float", v_value);
                        } else if (obj2 instanceof Double) {
                            v_value = ((Double) obj2).toString();
                            str = this.setData(pstmt, n, "double", v_value);
                        } else if (obj2 instanceof String) {
                            v_value = (String) obj2;
                            str = this.setData(pstmt, n, "varchar2", v_value);
                        }
                    }
                    Log.info.println(str);
                    pstmtSet += str + " \r\n";
                }
                isOk = pstmt.executeUpdate();
                if (isOk > 0)
                    doCommit = true;
            }
            //-------------  clob insert  ---------------------------------------------------------------
            Enumeration e = pbox.keys();

            for (int i = 0; e.hasMoreElements(); i++) {
                String key = e.nextElement().toString();
                if (key.indexOf("select") > -1) {
                    // Object o_value = pbox.get(key);
                    // if(isWeblogic) connMgr.setWeblogicCLOB(key, (String)o_value);
                    // else connMgr.setOracleCLOB(key, (String)o_value);//System.out.println("key " +key);System.out.println("o_value " +(String)o_value);
                }
            }

            h_sql.put(p_sql, pstmtSet);
            h_isCommit.put(p_sql, new Integer(isOk));
            //------------------------------------------------------------------------------------------------
        } catch (Exception e) {//e.printStackTrace();
            throw new Exception("DatabaseExecute.executeQuery()\r\n" + e.getMessage());
        } finally {
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
            connMgr = null;
        }
        return doCommit;
    }

    /**
     * 다수실행 SQL 에 적용, Clob 필드에도 적용됨
     * 
     * @parameter PreparedBox [] pbox PreparedStatement 실행시 치환될 변수
     * @parameter String [] p_sql
     * @return boolean doCommit commit 여부 반환
     */
    public boolean executeUpdate(PreparedBox[] pbox, String[] p_sql) throws Exception {
        boolean doCommit = true;
        PreparedStatement pstmt = null;
        // int isCommit = 0;
        int vectorSize = 1;
        int isOk = 0;

        try {
            if (connMgr == null)
                this.connMgr = new DBConnectionManager();

            if (pbox.length > 1)
                connMgr.setAutoCommit(false); //      다수 테이블 실행할 때 트랜젝션 처리

            h_sql = new Hashtable();
            h_isCommit = new Hashtable();

            for (int k = 0; k < pbox.length; k++) {
                int beforeIsOk = 0;
                isOk = 0;
                String pstmtSet = "";

                pstmt = connMgr.prepareStatement(p_sql[k]);//System.out.println("sql " +p_sql [k]);

                Collection c = pbox[k].values();
                Object[] obj = c.toArray();

                for (int i = 0; i < obj.length; i++) {
                    if (obj[i] instanceof Vector) {
                        vectorSize = ((Vector) obj[i]).size();//System.out.println("vectorSize " +vectorSize);
                        break;
                    }
                }

                for (int m = 0; m < vectorSize; m++) {
                    for (int n = 1; n <= pbox[k].size(); n++) {
                        Object o = pbox[k].get(new Integer(n));
                        String v_value = "";
                        String str = "";

                        if (o instanceof Integer) {
                            v_value = ((Integer) o).toString();
                            str = this.setData(pstmt, n, "number", v_value);
                        } else if (o instanceof Float) {
                            v_value = ((Float) o).toString();
                            str = this.setData(pstmt, n, "float", v_value);
                        } else if (o instanceof Double) {
                            v_value = ((Double) o).toString();
                            str = this.setData(pstmt, n, "double", v_value);
                        } else if (o instanceof String) {
                            v_value = (String) o;
                            str = this.setData(pstmt, n, "varchar2", v_value);
                        } else if (o instanceof Vector) {//System.out.println("value " +((Vector)o).elementAt(m));
                            Object obj2 = ((Vector) o).elementAt(m);

                            if (obj2 instanceof Integer) {
                                v_value = ((Integer) obj2).toString();
                                str = this.setData(pstmt, n, "number", v_value);
                            } else if (obj2 instanceof Float) {
                                v_value = ((Float) obj2).toString();
                                str = this.setData(pstmt, n, "float", v_value);
                            } else if (obj2 instanceof Double) {
                                v_value = ((Double) obj2).toString();
                                str = this.setData(pstmt, n, "double", v_value);
                            } else if (obj2 instanceof String) {
                                v_value = (String) obj2;
                                str = this.setData(pstmt, n, "varchar2", v_value);
                            }
                        }
                        //System.out.println(str);
                        pstmtSet += str + " \r\n";
                    }
                    beforeIsOk = isOk;

                    isOk = pstmt.executeUpdate();

                    isOk = isOk + beforeIsOk;
                }
                //-------------  clob insert  ---------------------------------------------------------------
                Enumeration e = pbox[k].keys();
                for (int i = 0; e.hasMoreElements(); i++) {
                    String key = e.nextElement().toString();

                    if (key.indexOf("select") > -1) {
                        // Object o_value = pbox[k].get(key);// System.out.println("key ::::::::::::::::::::" +key);System.out.println("o_value::::::::::::::"+o_value);
                        // if(isWeblogic) connMgr.setWeblogicCLOB(key, (String)o_value);
                        // else connMgr.setOracleCLOB(key, (String)o_value);
                    }
                }
                //------------------------------------------------------------------------------------------------
                h_sql.put(p_sql[k], pstmtSet);
                h_isCommit.put(p_sql[k], new Integer(isOk));
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (Exception e1) {
                    }
                }
            }
            System.out.println("notTransaction:" + box.getBoolean("notTransaction"));

            if (!box.getBoolean("notTransaction"))
                doCommit = this.isCommit();

            //System.out.println("doCommit:::excuteUpdate([],[]):::" + doCommit);

            //            if(doCommit) connMgr.commit();
            if (doCommit && pbox.length > 1)
                connMgr.commit(); //transaction 사용할 경우
            if (!doCommit && pbox.length > 1)
                connMgr.rollback();
        } catch (Exception e) {
            e.printStackTrace();
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            throw new Exception("DatabaseExecute.executeQuery()\r\n" + e.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
            connMgr = null;
        }
        return doCommit;
    }

    /**
     * Select SQL을 통하여 다수의 rows 를 반환
     * 
     * @parameter String sql
     * @return ArrayList list 반환
     */
    public ArrayList executeQueryList(String sql) throws Exception {
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;

        int total_page_count = 0;
        int total_row_count = 0;

        int v_pageno = box.getInt("p_pageno");
        int row = box.getInt("p_row");

        try {
            if (connMgr == null)
                this.connMgr = new DBConnectionManager();

            list = new ArrayList();

            ls = connMgr.executeQuery(sql);

            if (box.getBoolean("p_isPage")) {
                ls.setPageSize(row); //  페이지당 row 갯수를 세팅한다
                ls.setCurrentPage(v_pageno); //     현재페이지번호를 세팅한다.
                total_page_count = ls.getTotalPage(); //     전체 페이지 수를 반환한다
                total_row_count = ls.getTotalCount(); //     전체 row 수를 반환한다
            }

            while (ls.next()) {
                dbox = ls.getDataBox();

                if (box.getBoolean("p_isPage")) {
                    dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                    dbox.put("d_totalpage", new Integer(total_page_count));
                    dbox.put("d_rowcount", new Integer(row));
                }
                list.add(dbox);
            }

        } catch (Exception e) {
            //            e.printStackTrace();
            throw new Exception("DatabaseExecute.executeQueryList()\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
            connMgr = null;
        }
        return list;
    }

    /**
     * Select SQL을 통하여 다수의 rows 를 반환
     * 
     * @parameter RequestBox box
     * @parameter String sql
     * @return ArrayList list 반환
     */
    public ArrayList executeQueryList(RequestBox box, String sql) throws Exception {
        this.box = box;
        return this.executeQueryList(sql);
    }

    /**
     * Select SQL을 통하여 하나의 row 를 반환
     * 
     * @parameter String sql
     * @return DataBox dbox 반환
     */
    public DataBox executeQuery(String sql) throws Exception {
        ListSet ls = null;
        DataBox dbox = null;

        try {
            if (connMgr == null)
                this.connMgr = new DBConnectionManager();

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("DatabaseExecute.executeQuery()\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
            connMgr = null;
        }
        return dbox;
    }

    /**
     * Select SQL을 통하여 하나의 row 를 반환
     * 
     * @parameter RequestBox box
     * @parameter String sql
     * @return DataBox dbox 반환
     */
    public DataBox executeQuery(RequestBox box, String sql) throws Exception {
        this.box = box;
        return this.executeQuery(sql);
    }

    /**
     * Select SQL을 통하여 다수의 rows 를 반환, 컬럼명 key, 다수의 컬럼별 데이터 Vector values
     * 
     * @parameter String sql
     * @return Hashtable hash 반환
     */
    public Hashtable executeQueryHash(String sql) throws Exception {
        ListSet ls = null;
        Hashtable hash = null;

        try {
            if (connMgr == null)
                this.connMgr = new DBConnectionManager();

            hash = new Hashtable();

            ls = connMgr.executeQuery(sql);

            ResultSetMetaData meta = ls.getMetaData();
            int columnCount = meta.getColumnCount();

            Vector[] v = new Vector[columnCount];
            String[] columnName = new String[columnCount];
            String[] columnType = new String[columnCount];

            for (int i = 1; i <= columnCount; i++) {
                columnName[i - 1] = meta.getColumnName(i).toLowerCase();
                columnType[i - 1] = meta.getColumnTypeName(i).toLowerCase();

                v[i - 1] = new Vector();
            }

            while (ls.next()) {
                for (int i = 0; i < columnCount; i++) {
                    v[i].addElement(ls.getData(columnType[i], columnName[i], meta, columnCount));
                }
            }

            for (int i = 0; i < columnCount; i++) {
                hash.put("h_" + columnName[i], v[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("DatabaseExecute.executeQueryList()\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
            connMgr = null;
        }
        return hash;
    }

    /**
     * SQL 과 pstmt.set() 데이터를 문자열로 반환
     * 
     * @return String result 반환
     */
    public String getSQLString() throws Exception {
        StringBuffer result = new StringBuffer();

        try {

            Enumeration e = h_sql.keys();
            while (e.hasMoreElements()) {
                String sql = (String) e.nextElement();

                result.append(sql + "\r\n");
                result.append((String) h_sql.get(sql) + "\r\n");
                //     System.out.println(sql);
                //     System.out.println((String)h_sql.get(sql));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("DatabaseExecute.getSQLString()\r\n" + e.getMessage());
        }
        return result.toString();
    }

    /**
     * SQL 과 pstmt.set() 데이터를 Hashtable로 반환
     * 
     * @return String result 반환
     */
    public Hashtable getSQL() throws Exception {
        return this.h_sql;
    }

    /**
     * 각 SQL 의 실행 후 commit 갯수를 문자열로 반환
     * 
     * @return String result 반환
     */
    public String getIsCommitString() throws Exception {
        StringBuffer result = new StringBuffer();
        try {
            Enumeration e = h_isCommit.keys();

            while (e.hasMoreElements()) {
                String sql = (String) e.nextElement();

                result.append(sql + "\r\n");
                result.append(h_isCommit.get(sql).toString() + "\r\n");

                //     System.out.println(sql);
                //     System.out.println(h_isCommit.get(sql).toString());
            }
        } catch (Exception e) {
            //            e.printStackTrace();
            throw new Exception("DatabaseExecute.getIsCommitString()\r\n" + e.getMessage());
        }
        return result.toString();
    }

    /**
     * 각 SQL 의 실행 후 SQL과 commit 갯수를 Hashtable로 반환
     * 
     * @return Hashtable key : sql, value : commit 갯수 반환
     */
    public Hashtable getIsCommitSQL() throws Exception {
        return this.h_isCommit;
    }

    /**
     * 각 SQL 의 실행 후 테이블명과 commit 갯수를 Hashtable로 반환
     * 
     * @return Hashtable key : 테이블명, value : commit 갯수 반환
     */
    public Hashtable getIsCommitTable() throws Exception {
        Hashtable h = new Hashtable();

        Enumeration e = this.h_isCommit.keys();
        while (e.hasMoreElements()) {
            String sql = (String) e.nextElement();

            Object o_value = h_isCommit.get(sql);

            String v_table = "";
            if (sql.length() > 0) {
                if (sql.indexOf("insert") > -1) {
                    StringTokenizer st = new StringTokenizer(sql);
                    for (int i = 1; st.hasMoreTokens(); i++) {
                        if (i == 3) {
                            v_table = st.nextToken();
                            break;
                        }
                        st.nextToken();
                    }
                } else if (sql.indexOf("update") > -1) {
                    StringTokenizer st = new StringTokenizer(sql);
                    for (int i = 1; st.hasMoreTokens(); i++) {
                        if (i == 2) {
                            v_table = st.nextToken();
                            break;
                        }
                        st.nextToken();
                    }
                } else if (sql.indexOf("delete") > -1) {
                    StringTokenizer st = new StringTokenizer(sql);
                    for (int i = 1; st.hasMoreTokens(); i++) {
                        String str = "";
                        if (i == 2) {
                            str = st.nextToken();
                            if (!st.nextToken().equals("from")) {
                                v_table = str;
                                break;
                            }
                        }
                        v_table = st.nextToken();
                    }
                }
            }
            h.put(v_table, o_value);
        }
        return h;
    }

    private boolean isCommit() throws Exception {
        boolean result = true;
        try {
            Enumeration e = h_isCommit.keys();

            while (e.hasMoreElements()) {
                // String sql = (String) e.nextElement();

                //     if((sql.indexOf("delete") < 0) && h_isCommit.containsValue(new Integer(0)))             //      delete 를 제외한 실행시 isOk = 0 인 경우 rollback
                if (h_isCommit.containsValue(new Integer(0)))
                    return result = false;
            }
        } catch (Exception e) {
            //            e.printStackTrace();
            throw new Exception("DatabaseExecute.isCommit()\r\n" + e.getMessage());
        }
        //        System.out.println("result" + result);
        return result;
    }

    private String getJustColumn(String p_tableName, String p_columnName) throws Exception {
        String result = p_columnName;

        try {
            Vector not = box.getVector(p_tableName + "_not");
            for (int i = 0; i < not.size(); i++) { //      실행하지말아야할 컬럼명 찾는다.
                if (((String) not.elementAt(i)).equals(p_columnName)) {
                    result = "";
                }
            }

            Vector where = box.getVector(p_tableName + "_where");
            for (int i = 0; i < where.size(); i++) { //      where 조건의 컬럼명 찾는다.
                if (((String) where.elementAt(i)).equals(p_columnName)) {
                    result = "";
                }
            }
            if (!box.containsKey("p_" + result))
                result = "";
        } catch (Exception e) {
            //            e.printStackTrace();
            throw new Exception("DatabaseExecute.getJustColumn()\r\n" + e.getMessage());
        }
        return result;
    }

    private String getWhereColumn(String p_tableName, String p_columnName) throws Exception {
        String result = "";

        try {
            Vector where = box.getVector(p_tableName + "_where");
            for (int i = 0; i < where.size(); i++) {
                if (((String) where.elementAt(i)).equals(p_columnName)) {
                    result = p_columnName; //      where 조건의 컬럼명 뽑는다.
                    break;
                } else {
                    result = "";
                }
            }
        } catch (Exception e) {
            //            e.printStackTrace();
            throw new Exception("DatabaseExecute.getWhereColumn()\r\n" + e.getMessage());
        }
        return result;
    }

    /*
    private int isVectorColumn(String p_tableName) throws Exception {
        Vector vec = box.getVector(p_tableName + "_vector");

        return vec.size();
    }
    */

    private String getVectorColumn(String p_tableName) throws Exception {
        String result = "";
        try {
            Vector vec = box.getVector(p_tableName + "_vector");

            if (vec.size() > 0)
                result = (String) vec.elementAt(0);
            else
                result = "";
        } catch (Exception e) {
            //            e.printStackTrace();
            throw new Exception("DatabaseExecute.getVectorColumn()\r\n" + e.getMessage());
        }
        return result;
    }

    private String setData(PreparedStatement pstmt, int i, String ls_type, String ls_value) throws java.sql.SQLException, Exception {
        String result = "";

        //		if (ls_type.equals("number")) {
        if (ls_type.equals("number") || ls_type.equals("numeric") || ls_type.equals("int")) {
            if (ls_value == null || ls_value.equals(""))
                ls_value = "0";
            pstmt.setInt(i, Integer.parseInt(ls_value));
            result = "pstmt.setInt(" + i + ", " + ls_value + ")";
        } else if (ls_type.equals("float")) {
            if (ls_value == null || ls_value.equals(""))
                ls_value = "0";
            pstmt.setFloat(i, Float.parseFloat(ls_value));
            result = "pstmt.setFloat(" + i + ", " + ls_value + ")";
        } else if (ls_type.equals("double")) {
            if (ls_value == null || ls_value.equals(""))
                ls_value = "0";
            pstmt.setDouble(i, Double.parseDouble(ls_value));
            result = "pstmt.setDouble(" + i + ", " + ls_value + ")";
        }
        //        else if (ls_type.equals("char")) {
        else if (ls_type.equals("char") || ls_type.equals("nchar")) {
            if (ls_value == null)
                ls_value = "";
            pstmt.setString(i, ls_value);
            result = "pstmt.setString(" + i + ", " + ls_value + ")";
        }
        //        else if (ls_type.equals("long") || ls_type.equals("varchar") || ls_type.equals("varchar2")) {
        else if (ls_type.equals("long") || ls_type.equals("varchar") || ls_type.equals("varchar2") || ls_type.equals("nvarchar") || ls_type.equals("ntext")) {
            pstmt.setString(i, ls_value);
            result = "pstmt.setString(" + i + ", " + ls_value + ")";
        }
        /*
         * if (ls_value.length() > 60) { pstmt.setCharacterStream(i, new
         * BufferedReader(new StringReader(ls_value)), ls_value.length()); }
         * else { pstmt.setString(i,ls_value); } }
         */
        /*
         * else if (ls_type.equals("DATE")) { if (ls_value==null ||
         * ls_value.equals("")) ls_value="1900/01/01/00/00"; java.sql.Timestamp
         * ts_value = DateTime.getRandomdate(ls_value);
         * pstmt.setTimestamp(i,ts_value); }
         */
        return result;
    }
}
