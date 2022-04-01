package com.credu.library;

import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import com.credu.scorm.StringUtil;

/**
 * ����: Box ����: request ���� �Ѿ���� �Ķ���Ϳ� session ��ü�� Hashtable �� box ��ü�� ��� �����Ѵ�
 * Copyright: Copyright (c) 2004 Company: Credu
 * 
 * @author ������
 * @date 2003. 12
 * @version 1.0
 */
@SuppressWarnings( { "unchecked", "serial" })
public class RequestBox extends java.util.Hashtable {
    private static String removeComma(String s) {
        if (s == null) {
            return null;
        }
        if (s.indexOf(",") != -1) {
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c != ',') {
                    buf.append(c);
                }
            }
            return buf.toString();
        }
        return s;
    }

    protected String name = null;

    /**
     *Hashtable ���� �����Ѵ�.
     */
    public RequestBox(String name) {
        super();
        this.name = name;
    }

    /**
     * box ��ü�� ��� param value �� String Ÿ���� ��´�.
     * 
     * @param key param key
     * @return String value �� ��ȯ�Ѵ�.
     */
    public String get(String key) {
        //System.out.println("â�ƾ�3="+key);
        return getString(key);
    }

    public String getCommaInt(String key) {
        return StringUtil.getComma(get(key));
    }

    /**
     * box ��ü�� ��� param value('YYYYMMDDHH')�� 'YYYY/MM/DD' �κи� ��ȯ�Ѵ�.
     * 
     * @param key
     * @return
     */
    public String getDate(String key) {
        return getDate(key, '/');
    }

    /**
     * box ��ü�� ��� param value('YYYYMMDDHH')�� 'YYYY'+div+'MM'+div+'DD' �κи� ��ȯ�Ѵ�.
     * 
     * @param key
     * @return
     */
    public String getDate(String key, Object div) {
        String result = null;
        try {
            if (get(key) != null && get(key).length() > 7) {
                result = get(key).substring(0, 4) + div + get(key).substring(4, 6) + div + get(key).substring(6, 8);
            } else {
                result = "";
            }
        } catch (Exception e) {
            //			System.out.println(e);
            result = "";
        }
        return result;
    }

    /**
     * box ��ü�� ��� param value �� String Ÿ���� ��´�.
     * 
     * @param key param key
     * @return String value �� ��ȯ�Ѵ�.
     */
    public String getArrayToString(String key) {
        String value = null;
        try {
            Object o = super.get(key);
            Class c = o.getClass();
            if (o == null) {
                value = "";
            } else if (c.isArray()) {
                value = "";
                String[] temp = (String[]) o;
                for (String element : temp) {
                    value = value + element;
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
     * box ��ü�� ��� param value �� boolean Ÿ���� ��´�.
     * 
     * @param key param key
     * @return boolean value �� ��ȯ�Ѵ�.
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
     * box ��ü�� ��� param value �� double Ÿ���� ��´�.
     * 
     * @param key param key
     * @return double value �� ��ȯ�Ѵ�.
     */
    public double getDouble(String key) {
        String value = removeComma(getString(key));
        if (value.equals("")) {
            return 0;
        }
        double num = 0;
        try {
            num = Double.valueOf(value).doubleValue();
        } catch (Exception e) {
            num = 0;
        }
        return num;
    }

    /**
     * box ��ü�� ��� param value �� float Ÿ���� ��´�.
     * 
     * @param key param key
     * @return float value �� ��ȯ�Ѵ�.
     */
    public float getFloat(String key) {
        return (float) getDouble(key);
    }

    /**
     * box ��ü�� ��� session ��ü�� ��ȯ�Ѵ�.
     * 
     * @return session
     */
    public HttpSession getHttpSession() {
        HttpSession session = null;
        try {
            session = (HttpSession) super.get("session");
        } catch (Exception e) {
        }
        return session;
    }

    /**
     * box ��ü�� ��� param value �� int Ÿ���� ��´�.
     * 
     * @param key param key
     * @return int value �� ��ȯ�Ѵ�.
     */
    public int getInt(String key) {
        double value = getDouble(key);
        return (int) value;
    }

    /**
     * box ��ü�� ��� param value �� long Ÿ���� ��´�.
     * 
     * @param key param key
     * @return long value �� ��ȯ�Ѵ�.
     */
    public long getLong(String key) {
        String value = removeComma(getString(key));
        if (value.equals("")) {
            return 0L;
        }

        long lvalue = 0L;
        try {
            lvalue = Long.valueOf(value).longValue();
        } catch (Exception e) {
            lvalue = 0L;
        }
        return lvalue;
    }

    /**
     * box ��ü�� ��� �ϵ忡 ����Ǵ� ���ο� ���ε�� ���ϸ��� ��ȯ�Ѵ�. (�ܼ�)
     * 
     * @param key param key
     * @return String ���ε�� ������ ���ο� �̸��� ��ȯ�Ѵ�.
     */
    public String getNewFileName(String key) {
        String newname = "";
        Vector v = (Vector) getObject(key + "_new");
        if (v != null) {
            for (int i = 0; i < v.size(); i++) {
                String tmp = (String) v.elementAt(i);
                if (tmp != null) {
                    int idx = tmp.indexOf("|");
                    String name = tmp.substring(0, idx);
                    String filename = tmp.substring(idx + 1);
                    if (key.equals(name)) {
                        newname = filename;
                    }
                }
            }
        }
        //return newname;
        //2009.11.27(����) �������� �÷��� ��� ��� �߰�( new : \\upload\\���ϸ�)

        if (!newname.equals("")) {
            return this.getString("p_updir") + newname;
        }
        return newname;
    }

    /**
     * box ��ü�� ��� �ϵ忡 ����Ǵ� ���ο� ���ε�� ���ϸ���� ��ȯ�Ѵ�. (����)
     * 
     * @param key param key
     * @return Vector ���ε�� ������ ���ο� �̸����� ��ȯ�Ѵ�.
     */
    public Vector getNewFileNames(String key) {
        Vector newVector = new Vector();
        Vector v = (Vector) getObject(key + "_new");
        if (v != null) {
            for (int i = 0; i < v.size(); i++) {
                String tmp = (String) v.elementAt(i);
                if (tmp != null) {
                    int idx = tmp.indexOf("|");
                    String name = tmp.substring(0, idx);
                    String filename = tmp.substring(idx + 1);
                    if (key.equals(name)) {

                        // newVector.addElement(filename);
                        // 2009.11.27(����) �������� �÷��� ��� ��� �߰�( new : \\upload\\���ϸ�)

                        if (!filename.equals("")) {
                            newVector.addElement(this.getString("p_updir") + filename);
                        }
                        newVector.addElement(filename);
                    }
                }
            }
        }
        return newVector;
    }

    /**
     * box ��ü�� ��� param value �� String Ÿ���� ��´�.
     * 
     * @param key param key
     * @return String value �� ��ȯ�Ѵ�.
     */
    public Object getObject(String key) {
        Object value = null;
        try {
            //System.out.println("â�ƾ�="+key);
            value = super.get(key);
            //System.out.println("â�ƾ�2="+value);
        } catch (Exception e) {
            value = null;
        }
        return value;
    }

    /**
     * Query���� ����� data���� '�� ������ �� �յڷ� '�� �ٿ��� �ٷ� ����� �� �ֵ��� ��ȯ�Ѵ�.
     * 
     * @param key
     * @return
     * @author swchoi
     */
    public String getQueryString(String key) {
        String result = getString(key);
        if (result != null) {
            if (result.equals("undefined")) {
                result = "''";
            }
        }
        result = "'" + getString(key).trim().replaceAll("[']", "") + "'";
        return result;
    }

    /**
     * Query���� ����� data�ε� '�� ������ �� �յڷ� '�� �ٿ��� �������� �����͸� �Ѱ��� ��Ʈ������ �����. where in
     * ( + getQueryArray + ) �������� ���ȴ�.
     * 
     * @param key
     * @return
     */
    public String getQueryArray(String key) {
        Object o = getObject(key);
        String result = "''";
        if (o != null) {
            if (o.getClass().isArray()) {
                String[] temp = (String[]) o;
                for (String t : temp) {
                    if (t.equals("undefined")) {
                        result += ", ''";
                    } else
                        result += ",'" + t.replaceAll("[']", "") + "'";
                }
            } else {
                String t = (String) o;
                if (t.equals("undefined")) {
                    result += ", ''";
                } else
                    result += ",'" + t.replaceAll("[']", "") + "'";
            }
        } else
            result = "''";
        return result;
    }

    /**
     * box ��ü�� ��� ���ε�� �����ϸ��� ��ȯ�Ѵ�. (�ܼ�)
     * 
     * @param key param key
     * @return String ���ε�� ������ ���� �̸��� ��ȯ�Ѵ�.
     */
    public String getRealFileName(String key) {
        String realname = "";
        Vector v = (Vector) getObject(key + "_real");
        if (v != null) {
            for (int i = 0; i < v.size(); i++) {
                String tmp = (String) v.elementAt(i);
                if (tmp != null) {
                    int idx = tmp.indexOf("|");
                    String name = tmp.substring(0, idx);
                    String filename = tmp.substring(idx + 1);
                    if (key.equals(name)) {
                        realname = filename;
                    }
                }
            }
        }
        return realname;
    }

    /**
     * box ��ü�� ��� ���ε�� �����ϸ���� ��ȯ�Ѵ�. (����)
     * 
     * @param key param key
     * @return Vector ���ε�� ������ ���� �̸����� ��ȯ�Ѵ�.
     */
    public Vector getRealFileNames(String key) {

        Vector realVector = new Vector();
        Vector v = (Vector) getObject(key + "_real");
        if (v != null) {
            for (int i = 0; i < v.size(); i++) {
                String tmp = (String) v.elementAt(i);
                if (tmp != null) {
                    int idx = tmp.indexOf("|");
                    String name = tmp.substring(0, idx);
                    String filename = tmp.substring(idx + 1);
                    if (key.equals(name)) {
                        realVector.addElement(filename);
                    }
                }
            }
        }
        return realVector;
    }

    /**
     * String Ÿ���� ���Ǻ����� ������´�.
     * 
     * @param key String
     * @return s_value String
     */
    public String getSession(String key) {
        HttpSession session = this.getHttpSession();//System.out.println("getSession " + session);
        String s_value = "";

        //		Enumeration e2 = session.getAttributeNames();
        //		while(e2.hasMoreElements()){
        //			String name = (String)e2.nextElement();//System.out.println("Sessionkey " + name);
        //		}

        if (session != null) {
            Object obj = session.getAttribute(key); //  System.out.println("getSession obj" + obj);
            if (obj != null) {
                s_value = obj.toString();
            }
        }
        return s_value;
    }

    /**
     * int Ÿ���� ���Ǻ����� ������´�.(�ش簪�� ������ default �� ������ ���� �Ķ���ͷ� �Ѱܹ޾ƾ� �Ѵ�.)
     * 
     * @param key String
     * @param defaultValue int
     * @return s_value int
     */
    public int getSession(String key, int defaultValue) {
        int i_value = defaultValue;

        String s_value = this.getSession(key);

        if (!s_value.equals("")) {
            try {
                i_value = Integer.parseInt(s_value);
            } catch (Exception ex) {
                i_value = defaultValue;
            }
        }
        return i_value;
    }

    /**
     * ����id �� ��´�.
     * 
     * @return sessionId String
     */
    public String getSessionId() {
        HttpSession session = this.getHttpSession();
        String sessionId = "";

        if (session != null) {
            sessionId = session.getId();
        }
        return sessionId;
    }

    /**
     * box ��ü�� ��� param value �� String Ÿ���� ��´�.
     * 
     * @param key param key
     * @return String value �� ��ȯ�Ѵ�.
     */
    public String getString(String key) {
        String value = null;
        try {
            Object o = super.get(key);
            Class c = o.getClass();
            if (o == null) {
                value = "";
            } else if (c.isArray()) {
                int length = Array.getLength(o);
                if (length == 0) {
                    value = "";
                } else {
                    Object item = Array.get(o, 0);
                    if (item == null) {
                        value = "";
                    } else {
                        value = item.toString();
                    }
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
     * ������ Array�� �ƴ� ��쵵 Array�� ��ȯ�Ͽ� ������ �ּ�ȭ �Ѵ�.
     * 
     * @param key
     * @return
     */
    public String[] getStringArray(String key) {
        String[] result = null;
        try {
            result = (String[]) getObject(key);
        } catch (Exception e) {
            result = new String[1];
            result[0] = get(key);
            return result;
        }
        return result;
    }

    public String[] getStringArray(String key, String div) {
        String[] result = null;
        try {
            result = get(key).split(div);
        } catch (Exception e) {
            result = new String[1];
            result[0] = get(key);
            return result;
        }
        return result;
    }

    /**
     * box ��ü�� ��� param value �� String Ÿ���� ��´�. key�� �ش��ϴ� ���� ������ defstr�� ��ȯ�Ѵ�.
     * 
     * @param key param key
     * @return String value �� ��ȯ�Ѵ�.
     */
    public String getStringDefault(String key, String defstr) {
        return (getString(key).trim().equals("") ? defstr : getString(key));
    }

    /**
     * checkbox�� ���� ������ key �� value �� ������ �����Ͽ� �ѱ� ���, �� ���õ� value�� list�� Vector��
     * ��� ��ȯ�Ѵ�.
     * 
     * @param key param key
     * @return vector param values �� ���� Vector �� ��ȯ�Ѵ�.
     */
    public Vector<String> getVector(String key) {
        Vector vector = new Vector();
        try {
            Object o = super.get(key);
            Class c = o.getClass();
            if (o != null) {
                if (c.isArray()) {
                    int length = Array.getLength(o);
                    if (length != 0) {
                        for (int i = 0; i < length; i++) {
                            Object tiem = Array.get(o, i);
                            if (tiem == null) {
                                vector.addElement("");
                            } else {
                                vector.addElement(tiem.toString());
                            }
                        }
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
     * ���� ����ڰ� ������ ������ �ִ��� ����. (���Ǻ����� userid �� ������ �ƴ��� ���η� �Ǵ�.. )
     * 
     * @param key String userid
     * @return boolean userid �� ������ ������ true �� ��ȯ�Ѵ�.
     */
    public boolean hasSession(String key) {
        return !getSession(key).equals("");
    }

    /**
     * int Ÿ���� ���Ǻ����� �����Ѵ�.
     * 
     * @param key String
     * @param s_value int
     */
    public void setSession(String key, int i_value) {
        HttpSession session = this.getHttpSession();
        if (session != null) {
            session.setAttribute(key, new Integer(i_value));
        }
    }

    /**
     * String Ÿ���� ���Ǻ����� �����Ѵ�.
     * 
     * @param key String
     * @param s_value String
     */
    public void setSession(String key, String s_value) {
        HttpSession session = this.getHttpSession();//System.out.println("setSession " + session);
        if (session != null) {
            session.setAttribute(key, s_value);
        }
    }
    
    /**
     * box ���� �ʱ�ȭ
     */
    public void sessionInvalidate(){
    	HttpSession session = this.getHttpSession();
    	if (session != null) {
            session.invalidate();
        }
    }

    /**
     * box ��ü�� ������ִ� ��� key, value �� String Ÿ������ ��ȯ�Ѵ�.
     * 
     * @return String ��� key, value �� String Ÿ������ ��ȯ�Ѵ�.
     */
    @Override
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
                        if (item == null) {
                            value = "";
                        } else {
                            value = item.toString();
                        }
                    } else {
                        StringBuffer valueBuf = new StringBuffer();
                        valueBuf.append("[");
                        for (int j = 0; j < length; j++) {
                            Object item = Array.get(o, j);
                            if (item != null) {
                                valueBuf.append(item.toString());
                            }
                            if (j < length - 1) {
                                valueBuf.append(",");
                            }
                        }
                        valueBuf.append("]");
                        value = valueBuf.toString();
                    }
                } else {
                    value = o.toString();
                }
            }
            buf.append(key + "=" + value);
            if (i < max) {
                buf.append(", ");
            }
        }
        buf.append("}");

        return "RequestBox[" + name + "]=" + buf.toString();
    }

    /**
     * �ʱⰪ�� ������ �� ����Ѵ�. ex) key�� �ش��ϴ� ���� null���̸� ""�� ���õǰ�, ���� ���� �������� �� ���� �����ϰ�
     * �ȴ�.
     * 
     * @param key
     */
    public void sync(String[] key) {
        for (String keyTemp : key) {
            sync(keyTemp);
        }
    }

    /**
     * �ʱⰪ�� ������ �� ����Ѵ�. ex) key�� �ش��ϴ� ���� null���̸� ""�� ���õǰ�, ���� ���� �������� �� ���� �����ϰ�
     * �ȴ�.
     * 
     * @param key
     */
    public void sync(String key) {
        put(key, get(key));
    }

    /**
     * �ʱⰪ�� ������ �� ����Ѵ�. ex) key�� �ش��ϴ� ���� null���̸� defKey�� �ش�Ǵ� ���� ����ǰ�, ���� ����
     * �������� �� ���� �����ϰ� �ȴ�.
     * 
     * @param key
     */
    public void sync(String key, String defKey) {
        if (get(key).length() == 0)
            put(key, get(defKey));
        else
            put(key, get(key));
    }
}
