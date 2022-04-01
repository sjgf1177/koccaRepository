package com.credu.library;

import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Vector;

/**
 * 프로젝트명 : kocca_java 패키지명 : com.credu.library 파일명 : PreparedBox.java 작성날짜 :
 * 2011. 9. 26. 처리업무 : 수정내용 :
 * 
 * Copyright by CREDU.Co., LTD. ALL RIGHTS RESERVED.
 */

@SuppressWarnings({ "serial", "unchecked" })
public class PreparedBox extends java.util.Hashtable {
    protected String name = null;

    /**
     *Hashtable 명을 설정한다.
     */
    public PreparedBox(String name) {
        super();
        this.name = name;
    }

    /**
     * box 객체에 담긴 parameter value 의 String 타입을 얻는다.
     * 
     * @param key parameter key
     * @return String value 를 반환한다.
     */
    public String get(String key) {
        return getString(key);
    }

    /**
     * box 객체에 담긴 parameter value 의 boolean 타입을 얻는다.
     * 
     * @param key parameter key
     * @return boolean value 를 반환한다.
     */
    public boolean getBoolean(String key) {
        String value = getString(key);
        boolean isTrue = false;
        try {
            isTrue = (new Boolean(value)).booleanValue();
        } catch (Exception e) {
        }
        return isTrue;
    }

    /**
     * box 객체에 담긴 parameter value 의 double 타입을 얻는다.
     * 
     * @param key parameter key
     * @return double value 를 반환한다.
     */
    public double getDouble(String key) {
        String value = removeComma(getString(key));
        if (value.equals(""))
            return 0;
        double num = 0;
        try {
            num = Double.valueOf(value).doubleValue();
        } catch (Exception e) {
            num = 0;
        }
        return num;
    }

    /**
     * box 객체에 담긴 parameter value 의 float 타입을 얻는다.
     * 
     * @param key parameter key
     * @return float value 를 반환한다.
     */
    public float getFloat(String key) {
        return (float) getDouble(key);
    }

    /**
     * box 객체에 담긴 parameter value 의 int 타입을 얻는다.
     * 
     * @param key parameter key
     * @return int value 를 반환한다.
     */
    public int getInt(String key) {
        double value = getDouble(key);
        return (int) value;
    }

    /**
     * box 객체에 담긴 parameter value 의 long 타입을 얻는다.
     * 
     * @param key parameter key
     * @return long value 를 반환한다.
     */
    public long getLong(String key) {
        String value = removeComma(getString(key));
        if (value.equals(""))
            return 0L;

        long lvalue = 0L;
        try {
            lvalue = Long.valueOf(value).longValue();
        } catch (Exception e) {
            lvalue = 0L;
        }
        return lvalue;
    }

    /**
     * box 객체에 담긴 parameter value 의 String 타입을 얻는다.
     * 
     * @param key parameter key
     * @return String value 를 반환한다.
     */
    public String getString(String key) {
        String value = null;
        try {
            Object o = (Object) super.get(key);
            Class c = o.getClass();
            if (o == null) {
                value = "";
            } else if (c.isArray()) {
                int length = Array.getLength(o);
                if (length == 0)
                    value = "";
                else {
                    Object item = Array.get(o, 0);
                    if (item == null)
                        value = "";
                    else
                        value = item.toString();
                }
            } else {
                value = o.toString();
            }
        } catch (Exception e) {
            value = "";
        }
        return value;
    }

    /**
     * box 객체에 담긴 parameter value 의 String 타입을 얻는다.
     * 
     * @param key parameter key
     * @return String value 를 반환한다.
     */
    public Object getObject(String key) {
        Object value = null;
        try {
            value = (Object) super.get(key);
        } catch (Exception e) {
            value = null;
        }
        return value;
    }

    /**
     * checkbox와 같이 동일한 key 에 value 를 여러개 선택하여 넘길 경우, 각 선택된 value의 list를 Vector에
     * 담아 반환한다.
     * 
     * @param key parameter key
     * @return vector parameter values 를 담은 Vector 를 반환한다.
     */
    public Vector getVector(String key) {
        Vector vector = new Vector();
        try {
            Object o = (Object) super.get(key);
            Class c = o.getClass();
            if (o != null) {
                if (c.isArray()) {
                    int length = Array.getLength(o);
                    if (length != 0) {
                        for (int i = 0; i < length; i++) {
                            Object tiem = Array.get(o, i);
                            if (tiem == null)
                                vector.addElement("");
                            else
                                vector.addElement(tiem.toString());
                        }
                    }
                } else if (o instanceof Vector) {
                    Vector v = (Vector) o;
                    for (int i = 0; i < v.size(); i++) {
                        vector.addElement((String) v.elementAt(i));
                    }
                } else {
                    vector.addElement(o.toString());
                }
            }
        } catch (Exception e) {
        }
        return vector;
    }

    /**
     * box 객체에 담긴 parameter value 의 String 타입을 얻는다. key에 해당하는 값이 없으면 defstr을
     * 반환한다.
     * 
     * @param key parameter key
     * @return String value 를 반환한다.
     */
    public String getStringDefault(String key, String defstr) {
        return (getString(key).equals("") ? defstr : getString(key));
    }

    private static String removeComma(String s) {
        if (s == null)
            return null;
        if (s.indexOf(",") != -1) {
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c != ',')
                    buf.append(c);
            }
            return buf.toString();
        }
        return s;
    }

    /**
     * box 객체에 담겨져있는 모든 key, value 를 String 타입으로 반환한다.
     * 
     * @return String 모든 key, value 를 String 타입으로 반환한다.
     */
    public synchronized String toString() {
        int max = size() - 1;
        StringBuffer buf = new StringBuffer();
        Enumeration keys = keys();
        Enumeration objects = elements();
        buf.append("{");

        for (int i = 0; i <= max; i++) {
            String key = keys.nextElement().toString();
            String value = null;
            Object o = objects.nextElement();
            if (o == null) {
                value = "";
            } else {
                Class c = o.getClass();
                if (c.isArray()) {
                    int length = Array.getLength(o);

                    if (length == 0) {
                        value = "";
                    } else if (length == 1) {
                        Object item = Array.get(o, 0);
                        if (item == null)
                            value = "";
                        else
                            value = item.toString();
                    } else {
                        StringBuffer valueBuf = new StringBuffer();
                        valueBuf.append("[");
                        for (int j = 0; j < length; j++) {
                            Object item = Array.get(o, j);
                            if (item != null)
                                valueBuf.append(item.toString());
                            if (j < length - 1)
                                valueBuf.append(",");
                        }
                        valueBuf.append("]");
                        value = valueBuf.toString();
                    }
                } else {
                    value = o.toString();
                }
            }
            buf.append(key + "=" + value);
            if (i < max)
                buf.append(", ");
        }
        buf.append("}");

        return "DataBox[" + name + "]=" + buf.toString();
    }

    /**
     * Hashtable에 Integer 형식으로 set
     * 
     * @param int key
     * @param int value
     */
    public void setInt(int key, int value) {
        super.put(new Integer(key), new Integer(value));
    }

    /**
     * Hashtable에 Integer 형식으로 set
     * 
     * @param int key
     * @param String value
     */
    public void setString(int key, String value) {
        super.put(new Integer(key), new String(value));
    }

    /**
     * Hashtable에 Float 형식으로 set
     * 
     * @param int key
     * @param float value
     */
    public void setFloat(int key, float value) {
        super.put(new Integer(key), new Float(value));
    }

    /**
     * Hashtable에 Double 형식으로 set
     * 
     * @param int key
     * @param double value
     */
    public void setDouble(int key, double value) {
        super.put(new Integer(key), new Double(value));
    }

    /**
     * Hashtable에 Long 형식으로 set
     * 
     * @param int key
     * @param long value
     */
    public void setLong(int key, long value) {
        super.put(new Integer(key), new Long(value));
    }

    /**
     * Hashtable에 Voctor 형식으로 set
     * 
     * @param int key
     * @param Vector value
     */
    public void setVector(int key, Vector value) {
        super.put(new Integer(key), value);
    }

    /**
     * Hashtable에 Clob 형식으로 set
     * 
     * @param String sql
     * @param String value
     */
    public void setClob(String sql, String value) {
        super.put(new String(sql), new String(value));
    }
}
