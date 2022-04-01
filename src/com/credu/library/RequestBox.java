package com.credu.library;

import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import com.credu.scorm.StringUtil;

/**
 * 제목: Box 설명: request 에서 넘어오는 파라미터와 session 객체를 Hashtable 인 box 객체에 담아 관리한다
 * Copyright: Copyright (c) 2004 Company: Credu
 * 
 * @author 이정한
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
     *Hashtable 명을 설정한다.
     */
    public RequestBox(String name) {
        super();
        this.name = name;
    }

    /**
     * box 객체에 담긴 param value 의 String 타입을 얻는다.
     * 
     * @param key param key
     * @return String value 를 반환한다.
     */
    public String get(String key) {
        //System.out.println("창훈아3="+key);
        return getString(key);
    }

    public String getCommaInt(String key) {
        return StringUtil.getComma(get(key));
    }

    /**
     * box 객체에 담긴 param value('YYYYMMDDHH')중 'YYYY/MM/DD' 부분만 반환한다.
     * 
     * @param key
     * @return
     */
    public String getDate(String key) {
        return getDate(key, '/');
    }

    /**
     * box 객체에 담긴 param value('YYYYMMDDHH')을 'YYYY'+div+'MM'+div+'DD' 부분만 반환한다.
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
     * box 객체에 담긴 param value 의 String 타입을 얻는다.
     * 
     * @param key param key
     * @return String value 를 반환한다.
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
     * box 객체에 담긴 param value 의 boolean 타입을 얻는다.
     * 
     * @param key param key
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
     * box 객체에 담긴 param value 의 double 타입을 얻는다.
     * 
     * @param key param key
     * @return double value 를 반환한다.
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
     * box 객체에 담긴 param value 의 float 타입을 얻는다.
     * 
     * @param key param key
     * @return float value 를 반환한다.
     */
    public float getFloat(String key) {
        return (float) getDouble(key);
    }

    /**
     * box 객체에 담긴 session 객체를 반환한다.
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
     * box 객체에 담긴 param value 의 int 타입을 얻는다.
     * 
     * @param key param key
     * @return int value 를 반환한다.
     */
    public int getInt(String key) {
        double value = getDouble(key);
        return (int) value;
    }

    /**
     * box 객체에 담긴 param value 의 long 타입을 얻는다.
     * 
     * @param key param key
     * @return long value 를 반환한다.
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
     * box 객체에 담겨 하드에 저장되는 새로운 업로드된 파일명을 반환한다. (단수)
     * 
     * @param key param key
     * @return String 업로드된 파일의 새로운 이름을 반환한다.
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
        //2009.11.27(수정) 저장파일 컬럼에 상대 경로 추가( new : \\upload\\파일명)

        if (!newname.equals("")) {
            return this.getString("p_updir") + newname;
        }
        return newname;
    }

    /**
     * box 객체에 담겨 하드에 저장되는 새로운 업로드된 파일명들을 반환한다. (복수)
     * 
     * @param key param key
     * @return Vector 업로드된 파일의 새로운 이름들을 반환한다.
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
                        // 2009.11.27(수정) 저장파일 컬럼에 상대 경로 추가( new : \\upload\\파일명)

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
     * box 객체에 담긴 param value 의 String 타입을 얻는다.
     * 
     * @param key param key
     * @return String value 를 반환한다.
     */
    public Object getObject(String key) {
        Object value = null;
        try {
            //System.out.println("창훈아="+key);
            value = super.get(key);
            //System.out.println("창훈아2="+value);
        } catch (Exception e) {
            value = null;
        }
        return value;
    }

    /**
     * Query에서 사용할 data에서 '를 제거한 후 앞뒤로 '를 붙여서 바로 사용할 수 있도록 반환한다.
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
     * Query에서 사용할 data인데 '를 제거한 후 앞뒤로 '를 붙여서 여러개의 데이터를 한개의 스트링으로 만든다. where in
     * ( + getQueryArray + ) 형식으로 사용된다.
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
     * box 객체에 담긴 업로드된 원파일명을 반환한다. (단수)
     * 
     * @param key param key
     * @return String 업로드된 파일의 원래 이름을 반환한다.
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
     * box 객체에 담긴 업로드된 원파일명들을 반환한다. (복수)
     * 
     * @param key param key
     * @return Vector 업로드된 파일의 원래 이름들을 반환한다.
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
     * String 타입의 세션변수을 가지고온다.
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
     * int 타입의 세션변수을 가지고온다.(해당값이 없을때 default 로 돌려줄 값을 파라메터로 넘겨받아야 한다.)
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
     * 세션id 를 얻는다.
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
     * box 객체에 담긴 param value 의 String 타입을 얻는다.
     * 
     * @param key param key
     * @return String value 를 반환한다.
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
     * 강제로 Array가 아닌 경우도 Array로 변환하여 로직을 최소화 한다.
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
     * box 객체에 담긴 param value 의 String 타입을 얻는다. key에 해당하는 값이 없으면 defstr을 반환한다.
     * 
     * @param key param key
     * @return String value 를 반환한다.
     */
    public String getStringDefault(String key, String defstr) {
        return (getString(key).trim().equals("") ? defstr : getString(key));
    }

    /**
     * checkbox와 같이 동일한 key 에 value 를 여러개 선택하여 넘길 경우, 각 선택된 value의 list를 Vector에
     * 담아 반환한다.
     * 
     * @param key param key
     * @return vector param values 를 담은 Vector 를 반환한다.
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
     * 현재 사용자가 세션을 가지고 있는지 여부. (세션변수에 userid 가 공백이 아닌지 여부로 판단.. )
     * 
     * @param key String userid
     * @return boolean userid 를 가지고 있으면 true 를 반환한다.
     */
    public boolean hasSession(String key) {
        return !getSession(key).equals("");
    }

    /**
     * int 타입의 세션변수을 저장한다.
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
     * String 타입의 세션변수을 저장한다.
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
     * box 세션 초기화
     */
    public void sessionInvalidate(){
    	HttpSession session = this.getHttpSession();
    	if (session != null) {
            session.invalidate();
        }
    }

    /**
     * box 객체에 담겨져있는 모든 key, value 를 String 타입으로 반환한다.
     * 
     * @return String 모든 key, value 를 String 타입으로 반환한다.
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
     * 초기값을 세팅할 때 사용한다. ex) key에 해당하는 값이 null등이면 ""이 세팅되고, 무언가 값을 가졌으면 그 값을 세팅하게
     * 된다.
     * 
     * @param key
     */
    public void sync(String[] key) {
        for (String keyTemp : key) {
            sync(keyTemp);
        }
    }

    /**
     * 초기값을 세팅할 때 사용한다. ex) key에 해당하는 값이 null등이면 ""이 세팅되고, 무언가 값을 가졌으면 그 값을 세팅하게
     * 된다.
     * 
     * @param key
     */
    public void sync(String key) {
        put(key, get(key));
    }

    /**
     * 초기값을 세팅할 때 사용한다. ex) key에 해당하는 값이 null등이면 defKey에 해당되는 값이 복사되고, 무언가 값을
     * 가졌으면 그 값을 세팅하게 된다.
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
