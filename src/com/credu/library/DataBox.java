
package com.credu.library;

import java.lang.reflect.Array;
import java.util.Enumeration;

import com.credu.scorm.StringUtil;

/**
 * <p>����: DataBox</p>
 * <p>����: select sql �� ���� ��µǴ� data ��  DataBox hashtable ���� �����Ѵ�</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author ������
 *@date 2003. 12
 *@version 1.0
 */
@SuppressWarnings({ "unchecked", "serial" })
public class DataBox extends java.util.Hashtable {
	private static String removeComma(String s) {
		if ( s == null ) {
			return null;
		}
		if ( s.indexOf(",") != -1 ) {
			StringBuffer buf = new StringBuffer();
			for(int i=0;i<s.length();i++){
				char c = s.charAt(i);
				if ( c != ',') {
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
	public DataBox(String name) {
		super();
		this.name = name;
	}

	/**
	 * box ��ü�� ��� param value �� String Ÿ���� ��´�.
    @param key param key
    @return String value �� ��ȯ�Ѵ�.
	 */
	public String get(String key) {
		return getString(key);
	}

	/**
	 * ��� key�� �ҹ��ڷ� �̷������.[��ҹ��ڱ��о���]
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) {
		super.put(key.toLowerCase(), value);
	}
	public String getCommaInt(String key) {
		return StringUtil.getComma(get(key));
	}
	public String getCommaInt(String key, String max) {
		String result = StringUtil.getComma(get(key));
		if (result.equals("0")) result = max;
		return result;
	}

	public String get(String key, String temp) {
		if (getString(key).length()>0) {
			return getString(key);
		} else {
			return temp;
		}
	}

	public String getAll() {
		StringBuffer buf = new StringBuffer();
		String col = ":\"";
		char end = '\"';
		char com = ',';
		buf.append("{start:1");
		Object[] param = keySet().toArray();

		for (Object element : param) {
			buf.append(com);
			buf.append(element);
			buf.append(col);
			buf.append(getString((String)element));
			buf.append(end);
		}

		buf.append("}");

		return buf.toString();
	}

	/**
	 * box ��ü�� ��� param value �� boolean Ÿ���� ��´�.
    @param key param key
    @return boolean value �� ��ȯ�Ѵ�.
	 */
	public boolean getBoolean(String key) {
		String value = getString(key).toLowerCase();
		boolean isTrue = false;
		try {
			if (value.equals("y") || value.equals("yes")) value = "true";
			isTrue = (new Boolean(value)).booleanValue();
		}
		catch(Exception e){}
		return isTrue;
	}

	/**
	 * box ��ü�� ��� param value('YYYYMMDDHH')�� 'YYYY/MM/DD' �κи� ��ȯ�Ѵ�.
	 * @param key
	 * @return
	 */
	public String getDate(String key) {
		return getDate(key, '/');
	}

	/**
	 * box ��ü�� ��� param value('YYYYMMDDHH')�� 'YYYY'+div+'MM'+div+'DD' �κи� ��ȯ�Ѵ�.
	 * @param key
	 * @return
	 */
	public String getDate(String key, Object div) {
		String result = null;
		try {
			if (get(key)!=null && get(key).length()>7 ){
				result = get(key).substring(0, 4) + div + get(key).substring(4, 6) + div+ get(key).substring(6, 8);
			} else {
				result = "";
			}
		}
		catch (Exception e) {
			//			System.out.println(e);
			result = "";
		}
		return result;
	}

	public String getOnlyHourMinute(String key) {
		String result = null;

		try {

			if ( get(key)!=null && get(key).length() > 3 ) {
				result = get(key).substring(0,2) + ":" + get(key).substring(2,4);
			} else {
				result = "";
			}

		} catch( Exception e) {
			result = "";
		}

		return result;
	}

	/**
	 * box ��ü�� ��� param value �� double Ÿ���� ��´�.
    @param key param key
    @return double value �� ��ȯ�Ѵ�.
	 */
	public double getDouble(String key) {
		String value = removeComma(getString(key));
		if ( value.equals("") ) {
			return 0;
		}
		double num = 0;
		try {
			num = Double.valueOf(value).doubleValue();
		}
		catch(Exception e) {
			num = 0;
		}
		return num;
	}
	/**
	 * ������ Array�� �ƴ� ��쵵 Array�� ��ȯ�Ͽ� ������ �ּ�ȭ �Ѵ�.
	 * @param key
	 * @return
	 */
	public String[] getStringArray(String key) {
		String [] result = null;
		try {
			result = (String[])getObject(key);
		}
		catch(Exception e) {
			result = new String[1];
			result[0] = get(key);
			return result;
		}
		return result;
	}
	public String[] getStringArray(String key, String div) {
		String [] result = null;
		try {
			result = get(key).split(div);
		}
		catch(Exception e) {
			result = new String[1];
			result[0] = get(key);
			return result;
		}
		return result;
	}

	/**
	 * box ��ü�� ��� param value �� float Ÿ���� ��´�.
    @param key param key
    @return float value �� ��ȯ�Ѵ�.
	 */
	public float getFloat(String key) {
		return (float)getDouble(key);
	}
	/**
	 * box ��ü�� ��� param value('YYYYMMDDHH')�� 'HH' �κи� ��ȯ�Ѵ�.
	 * @param key
	 * @return
	 */
	public String getHour(String key) {
		return getHour(key, "0");
	}
	public String getHour(String key, String temp) {
		String result = null;
		try {
			if (get(key)!=null && get(key).length()>9 ){
				result = get(key).substring(8, 10);
			} else {
				result = temp;
			}
		}
		catch (Exception e) {
			result = "";
		}
		return result;
	}

	/**
	 * box ��ü�� ��� param value('YYYYMMDDHH')�� 'HH' �κи� ��ȯ�Ѵ�.
	 * @param key
	 * @return
	 */
	public String getMinute(String key) {
		return getHour(key, "0");
	}
	public String getMinute(String key, String temp) {
		String result = null;
		try {
			if (get(key)!=null && get(key).length()>11 ){
				result = get(key).substring(10, 12);
			} else {
				result = temp;
			}
		}
		catch (Exception e) {
			result = "";
		}
		return result;
	}

	public String getHtmlString(String key) {
		return StringManager.convertHtmlchars(getString(key));
	}
	
	public String getBrString(String key) {
		return StringManager.removeHTML(getString(key));
	}

	/**
	 * box ��ü�� ��� param value �� int Ÿ���� ��´�.
    @param key param key
    @return int value �� ��ȯ�Ѵ�.
	 */
	public int getInt(String key) {
		double value = getDouble(key);
		return (int)value;
	}

	/**
	 * box ��ü�� ��� param value �� long Ÿ���� ��´�.
    @param key param key
    @return long value �� ��ȯ�Ѵ�.
	 */
	public long getLong(String key) {
		String value = removeComma(getString(key));
		if ( value.equals("") ) {
			return 0L;
		}

		long lvalue = 0L;
		try {
			lvalue = Long.valueOf(value).longValue();
		}
		catch(Exception e) {
			lvalue = 0L;
		}
		return lvalue;
	}
	/**
	 * box ��ü�� ��� param value �� String Ÿ���� ��´�.
    @param key param key
    @return String value �� ��ȯ�Ѵ�.
	 */
	public Object getObject(String key) {
		Object value = null;
		try {
			value = super.get(key.toLowerCase());
		}
		catch(Exception e) {
			value = null;
		}
		return value;
	}
	/**
	 * box ��ü�� ��� param value �� String Ÿ���� ��´�.
    @param key param key
    @return String value �� ��ȯ�Ѵ�.
	 */
	public String getString(String key) {
		String value = null;
		try {
			Object o = super.get(key.toLowerCase());
			Class c = o.getClass();
			if ( o == null ) {
				value = "";
			}
			else if( c.isArray() ) {
				int length = Array.getLength(o);
				if ( length == 0 ) {
					value = "";
				} else {
					Object item = Array.get(o, 0);
					if ( item == null ) {
						value = "";
					} else {
						value = item.toString();
						//						value = value.replaceAll("[\n]", "<br>");
					}
				}
			}
			else {
				value = o.toString();
				//				value = value.replaceAll("[\n]", "<br>");
			}
		}
		catch(Exception e) {
			value = "";
		}
		return value;
	}
	public String getStringDefault(String key, String defaultString) {
		String result = getString(key);
		if (result.length()>0) {
			return result;
		} else {
			return defaultString;
		}
	}
	/**
	 * box ��ü�� ������ִ� ��� key, value �� String Ÿ������ ��ȯ�Ѵ�.
    @return String ��� key, value �� String Ÿ������ ��ȯ�Ѵ�.
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
			if ( o == null ) {
				value = "";
			}else {
				Class  c = o.getClass();
				if( c.isArray() ) {
					int length = Array.getLength(o);

					if ( length == 0 ) 	{
						value = "";
					}
					else if ( length == 1 ) {
						Object item = Array.get(o, 0);
						if ( item == null ) {
							value = "";
						} else {
							value = item.toString();
						}
					}
					else {
						StringBuffer valueBuf = new StringBuffer();
						valueBuf.append("[");
						for ( int j=0;j<length;j++) {
							Object item = Array.get(o, j);
							if ( item != null ) {
								valueBuf.append(item.toString());
							}
							if ( j<length-1) {
								valueBuf.append(",");
							}
						}
						valueBuf.append("]");
						value = valueBuf.toString();
					}
				}
				else {
					value = o.toString();
				}
			}
			buf.append(key + "=" + value);
			if (i < max) {
				buf.append(", ");
			}
		}
		buf.append("}");

		return "DataBox["+name+"]=" + buf.toString();
	}

	public String getSelected(String key) {
		String value = null;
		try {
			Object o = super.get(key.toLowerCase());
			Class c = o.getClass();
			if ( o == null ) {
				value = "";
			}
			else if( c.isArray() ) {
				int length = Array.getLength(o);
				if ( length == 0 ) {
					value = "";
				} else {
					Object item = Array.get(o, 0);
					if ( item == null ) {
						value = "";
					} else {
						if (item.equals("Y"))
							value = " selected ";
					}
				}
			}
			else {
				if (o.equals("Y")) value = " selected ";
				else value = "";
			}
		}
		catch(Exception e) {
			value = "";
		}
		return value;
	}

	/**
	 * ���� ���ϰ� ������
	 * @param key
	 * @return
	 */
	public String getFromDBTypeString(String key) {
		String result = null;
		result = get(key);
		if(result.length()==0 && key.indexOf("d_")==-1) result = get("d_"+key);
		return result;
	}
}
