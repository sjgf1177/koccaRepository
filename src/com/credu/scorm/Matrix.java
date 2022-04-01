package com.credu.scorm;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * ���̺��� ������ ���� ���� ���� ���� ������ �����͸� ��µ� ���Ǵ� Matrix Ŭ�����Դϴ�.
 * FlexMatrix�� ����� �ܼ�ȭ�Ͽ� ���� �޸𸮸� �����ϰ� ���� ������ �����մϴ�.
 * <li>���� : new Matrix("�÷���1,�÷���2,�÷���3") �Ǵ� new Matrix(�÷����� ���� String[] �迭)
 * <li>�� ���� : addRow(List addedRow)
 * <li>�� ���� : getRow(int rowIndex)
 * <li>�� ���� : removeRow(int rowIndex)
 * <li>�� ���� : get(int rowIndex, String columnName), get(int rowIndex, String columnIndex)
 * <li>��� ���� : getRowSize(), getColumnSize()
 * <li>���ȣ ã�� : getRowIndex(String columnName �Ǵ� int columnIndex, Object obj)
 * <li>���� ���� : toString() �Ǵ� toHtmlString() �޽�带 �̿��ϸ� �˴ϴ�.
 * �ۼ��� : 2001.10.31 �÷����� ��Ʈ ��� ����. �����ڿ� ';'�̿ܿ� ','�� ��밡���ϰ� ����. ������ ���̿� ' ' ������ �ִ� ��� trim()��Ŵ.
 * @author �ѻ�� 
 * ���� :2001.11.22 ���ö : getRowMap,addRowMap
 */
public class Matrix implements Serializable {

	public static void main(String[] args) {
		String[] columnNames = { "name", "age" };
		
		Matrix fm = new Matrix(columnNames);

		List row1 = new ArrayList();
		row1.add("��");
		row1.add(new Integer("3"));
		fm.addRow(row1);
		
		List row2 = new ArrayList();
		row2.add("��");
		row2.add(new Integer("5"));
		fm.addRow(row2);
		
		List row3 = new ArrayList();
		row3.add("��");
		row3.add(new Integer("1"));
		fm.addRow(row3);
		
		System.out.println(fm);
		
		fm.sort("age", true);
		System.out.println(fm);
		
		fm.sort("age", false);
		System.out.println(fm);
		
		fm.sort("name", false);
		System.out.println(fm);
		
	}

	/**
	* �÷��� �̸��� ���ϴ�.
	*/
	public List columnNameList;

	/**
	* �� ���� ����Ʈ�� ���� �� ����Ʈ�� ���Ҵ� ����Ʈ�� �Ǿ� �ֽ��ϴ�.
	*/
	public List rowList;

	/**
	 * rowSize=0 �̰� columnSize=0 �� Matrix�� ������
	 */
	public Matrix() {
		columnNameList = new ArrayList();
		rowList = new ArrayList();
	}
	
	/**
	 * String �迭�� �־����� columnNames�� �÷����� ���ϴ� Matrix�� ������
	 */
	public Matrix(String[] columnNames) {
		columnNameList = new ArrayList( Arrays.asList( columnNames ) );
		rowList = new ArrayList();
	}
	
	/**
	 * String���� �־����� columnNames�� �÷����� ���ϴ� Matrix�� ������
	 */
	public Matrix( String columnNames ) {
		columnNameList = this.toList(columnNames, ";,");
		rowList = new ArrayList();
	}

	/**
	 * ���� �Ʒ��� �� �� �߰�
	 * @return ���������� �߰��Ǹ� true �ƴϸ� false
	 */
	public boolean addRow( List inputRow ) {
		if( inputRow.size() != this.getColumnSize() )
			return false;
		rowList.add( inputRow );
		return true;
	}

	/**
	 * Ư���� ��ġ�� �� �� �߰�
	 * @return ���������� �߰��Ǹ� true �ƴϸ� false
	 */
	public boolean addRow( int rowIndex, List inputRow ) {
		if( inputRow.size() != this.getColumnSize() )
			return false;
		rowList.add( rowIndex, inputRow );
		return true;
	}

	/**
	 * ���� �Ʒ��� ������ �� �� �� �߰�
	 * @return ���������� �߰��Ǹ� true �ƴϸ� false
	 */
	public boolean addRowMap( Map inputRowMap ) {
		if( inputRowMap.size() != this.getColumnSize() )
			return false;
		List inputRow = new ArrayList();
		List columnList = this.getColumnNameList();
		for (int i=0; i<inputRowMap.size(); i++) 
			inputRow.add(inputRowMap.get((String)columnNameList.get(i)));
		rowList.add( inputRow );
		return true;
	}

	/**
	 * Ư����ġ�� ������ �� �� �� �߰�
	 * @return ���������� �߰��Ǹ� true �ƴϸ� false
	 */
	public boolean addRowMap( int rowIndex, Map inputRowMap ) {
		if( inputRowMap.size() != this.getColumnSize() )
			return false;
		List inputRow = new ArrayList();
		List columnList = this.getColumnNameList();
		for (int i=0; i<inputRowMap.size(); i++) 
			inputRow.add((String)inputRowMap.get((String)columnNameList.get(i)));
		rowList.add( rowIndex, inputRow );
		return true;
	}

	/**
	 * ������ ��ġ�� �ִ� �� ���� ����
	 */
	public void removeRow( int rowIndex ) {
		rowList.remove( rowIndex );	
	}

	/**
	 * (���ȣ, ����ȣ)�� �ִ� Object�� ������
	 */
	public Object get( int rowIndex, int columnIndex ) {
		return ((List)rowList.get(rowIndex)).get(columnIndex);
	}

	/**
	 * (���ȣ, ����ȣ)�� ���� ������
	 */
	public void set( int rowIndex, int columnIndex, String str_columnValue ) {
		((List)rowList.get(rowIndex)).set(columnIndex, str_columnValue);
	}
	public void set( int rowIndex, String columnName, String str_columnValue ) {
		int columnIndex = this.getColumnNameIndex(columnName);
		((List)rowList.get(rowIndex)).set(columnIndex, str_columnValue);
	}

	/**
	 * (���ȣ, ���̸�)�� �ִ� Object�� ������
	 */
	public Object get( int rowIndex, String columnName ) {
		int columnIndex = this.getColumnNameIndex(columnName);
		return ((List)rowList.get(rowIndex)).get(columnIndex);
	}

	/**
	 * (���ȣ, ����ȣ)�� �ִ� Object�� ������
	 */
	public String getString( int rowIndex, int columnIndex ) {
		return (String)((List)rowList.get(rowIndex)).get(columnIndex);
	}

	/**
	 * (���ȣ, ���̸�)�� �ִ� Object�� ������
	 */
	public String getString( int rowIndex, String columnName ) {
		int columnIndex = this.getColumnNameIndex(columnName);
		return (String)((List)rowList.get(rowIndex)).get(columnIndex);
	}

	/**
	 * (���ȣ, ���̸�)�� �ִ� Object�� ������
	 */
	public String getString( int rowIndex, String columnName, String defaultValue ) {
		int columnIndex = this.getColumnNameIndex(columnName);
		if((String)((List)rowList.get(rowIndex)).get(columnIndex) == null)
			return defaultValue;
		else
			return (String)((List)rowList.get(rowIndex)).get(columnIndex);
	}

	/**
	 * (���ȣ, ����ȣ)�� �ִ� Object�� ������
	 */
	public int getInt( int rowIndex, int columnIndex ) {
		try {
			return Integer.parseInt((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getInt() �޽�带 �̿��ؼ� ĳ������ �ϱ� ���ؼ� String Ÿ�� �Ǵ� Integer Ÿ������ ����� �������̾� �մϴ�.");
		}
	}

	/**
	 * (���ȣ, ���̸�)�� �ִ� Object�� ������
	 */
	public int getInt(int rowIndex, String columnName) {
		try {
			int columnIndex = this.getColumnNameIndex(columnName);
			return Integer.parseInt((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getInt() �޽�带 �̿��ؼ� ĳ������ �ϱ� ���ؼ� String Ÿ������ ����� �������̾� �մϴ�.");
		}
	}

	/**
	 * (���ȣ, ���̸�)�� �ִ� Object�� ������
	 */
	public int getInt(int rowIndex, String columnName, int defaultValue) {
		try {
			int columnIndex = this.getColumnNameIndex(columnName);
			if((String)((List)rowList.get(rowIndex)).get(columnIndex) == null)
				return defaultValue;
			else
				return Integer.parseInt((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getInt() �޽�带 �̿��ؼ� ĳ������ �ϱ� ���ؼ� String Ÿ������ ����� �������̾� �մϴ�.");
		}
	}

	/**
	 * (���ȣ, ����ȣ)�� �ִ� Object�� ������
	 */
	public long getLong( int rowIndex, int columnIndex ) {
		try {
			return Long.parseLong((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getLong() �޽�带 �̿��ؼ� ĳ������ �ϱ� ���ؼ� String Ÿ������ ����� �������̾� �մϴ�.");
		}
	}

	/**
	 * (���ȣ, ���̸�)�� �ִ� Object�� ������
	 */
	public long getLong( int rowIndex, String columnName ) {
		try {
			int columnIndex = this.getColumnNameIndex(columnName);
			return Long.parseLong((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getLong() �޽�带 �̿��ؼ� ĳ������ �ϱ� ���ؼ� String Ÿ������ ����� �������̾� �մϴ�.");
		}
	}
	
	/**
	 * (���ȣ, ����ȣ)�� �ִ� Object�� ������
	 */
	public float getFloat( int rowIndex, int columnIndex ) {
		try {
			return Float.parseFloat((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getFloat(() �޽�带 �̿��ؼ� ĳ������ �ϱ� ���ؼ� String Ÿ������ ����� �������̾� �մϴ�.");
		}
	}

	/**
	 * (���ȣ, ���̸�)�� �ִ� Object�� ������
	 */
	public float getFloat( int rowIndex, String columnName ) {
		try {
			int columnIndex = this.getColumnNameIndex(columnName);
			return Float.parseFloat((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getFloat(() �޽�带 �̿��ؼ� ĳ������ �ϱ� ���ؼ� String Ÿ������ ����� �������̾� �մϴ�.");
		}
	}
	
	/**
	 * (���ȣ, ����ȣ)�� �ִ� Object�� ������
	 */
	public double getDouble( int rowIndex, int columnIndex ) {
		try {
			return Double.parseDouble((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getDouble() �޽�带 �̿��ؼ� ĳ������ �ϱ� ���ؼ� String Ÿ������ ����� �������̾� �մϴ�.");
		}
	}

	/**
	 * (���ȣ, ���̸�)�� �ִ� Object�� ������
	 */
	public double getDouble( int rowIndex, String columnName ) {
		try {
			int columnIndex = this.getColumnNameIndex(columnName);
			return Double.parseDouble((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getDouble() �޽�带 �̿��ؼ� ĳ������ �ϱ� ���ؼ� String Ÿ������ ����� �������̾� �մϴ�.");
		}
	}


	/**
	 * ���ȣ�� �־��� Ư�� �࿡ �ִ� Object�� List�� ������
	 */
	public List getRow( int rowIndex ) {
		return (List) rowList.get(rowIndex);
	}
	
	/**
	 * ���ȣ�� �־��� Ư�� �࿡ �ִ� Object�� Map���� ������
	 */
	public Map getRowMap( int rowIndex ) {
		Map row = new HashMap();
		List rowData = (List) rowList.get(rowIndex);
		for (int i = 0; i < columnNameList.size(); i++) {
			row.put((String) columnNameList.get(i), rowData.get(i));
		}
		return row;
	}
	
	/**
	 * ���� ũ�⸦ ����
	 */
	public int getRowSize() {
		return rowList.size();
	}

	/**
	 * ���� ũ�⸦ ����
	 */
	public int getColumnSize() {
		return columnNameList.size();
	}
	
	/**
	 * Matrix�� HTML ���̺� �������� ����մϴ�.
	 */
	public String toHtmlString() {
		String ret = "";
		ret = ret + "<table border=1 cellpadding=1 cellspacing=0 >";
		ret = ret + "<tr>";
		for( Iterator i = columnNameList.iterator(); i.hasNext(); ) {
			ret = ret + "<td align=center ><font size=2>" + (String)i.next() + "</font></td>";
		}
		ret = ret + "</tr>";
		
		for( int i=0; i<rowList.size(); i++ ) {
			List thisRow = (List)rowList.get(i);
			ret = ret + "<tr>";
			for( Iterator j = thisRow.iterator(); j.hasNext(); ) {
				ret = ret + "<td align=center ><font size=2>" + (String)j.next() + "</font></td>";
			}
			ret = ret + "</tr>";
		}
		ret = ret + "</table>";
		return ret;
	}


	public String toHtmlString(String str_TableStyle, String str_TRStyle, String str_TDStyle) {
		StringBuffer htmlList	= new StringBuffer();
		htmlList.append("<table "+ str_TableStyle +">\n");
		htmlList.append("<tr "+ str_TRStyle +">\n");
		for( Iterator i = columnNameList.iterator(); i.hasNext(); ) {
			htmlList.append("<td "+ str_TDStyle +">" + (String)i.next() + "</td>\n");
		}
		htmlList.append("</tr>\n");
		
		for( int i=0; i<rowList.size(); i++ ) {
			List thisRow = (List)rowList.get(i);
			htmlList.append("<tr "+ str_TRStyle +">\n");
			for( Iterator j = thisRow.iterator(); j.hasNext(); ) {
				htmlList.append("<td "+ str_TDStyle +">" + (String)j.next() + "</td>\n");
			}
			htmlList.append("</tr>\n");
		}
		htmlList.append("</table>\n");

		return htmlList.toString();
	}

	public String toHtmlString(String str_TRStyle, String str_TDStyle) {
		StringBuffer htmlList	= new StringBuffer();
	
		for( int i=0; i<rowList.size(); i++ ) {
			List thisRow = (List)rowList.get(i);
			htmlList.append("<tr "+ str_TRStyle +">\n");
			for( Iterator j = thisRow.iterator(); j.hasNext(); ) {
				htmlList.append("<td "+ str_TDStyle +">" + (String)j.next() + "</td>\n");
			}
			htmlList.append("</tr>\n");
		}

		return htmlList.toString();
	}

	/**
	 * Matrix�� �Ϲ����� ����Ʈ�� toString()���·� ����մϴ�.
	 */	
	public String toString() {
		return rowList.toString();
	}
	
	/**
	 * �̸����� ���� �ε����� ����
	 */
	public int getColumnNameIndex( String columnName ) {
		/* ������ �޼����� �����. �ڵ��� ���Ǹ� ���ؼ� Exception�� �������� ���� */
		int index = columnNameList.indexOf( columnName );
		
		if (index < 0) {
			String msg = "[ERROR] Matrix Error : '" + columnName + "' not found in current column names " + columnNameList.toString();
			throw new NoSuchElementException(msg);
		}
		return index;
	}
	
	/**
	 * �Է��� Object�� Ư�� ���� ���° �࿡ �ִ����� index ���� �����մϴ�.
	 */
	public int getRowIndex( int columnIndex, Object obj ) {
		List thisRow;
		for( int i=0; i<rowList.size(); i++ ) {
			thisRow = (List)rowList.get(i);
			if( obj.equals(thisRow.get(columnIndex)) ) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * �Է��� Object�� Ư�� ���� ���° �࿡ �ִ����� index ���� �����մϴ�.
	 */
	public int getRowIndex( String columnName, Object obj ) {
		List thisRow;
		int columnIndex = this.getColumnNameIndex( columnName );
		for( int i=0; i<rowList.size(); i++ ) {
			thisRow = (List)rowList.get(i);
			if( obj.equals(thisRow.get(columnIndex)) ) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * �Է��� Object�� Ư�� ���� ���° ���� �ִ����� index ���� �����մϴ�.
	 */
	public int getColumnIndex( int rowIndex, Object obj ) {
		List thisRow = (List)rowList.get(rowIndex);
		return thisRow.indexOf( obj );
	}

	/**
	 * �÷� �ε����� �÷� �̸��� �����մϴ�.
	 */
	public String getColumnName(int index) {
		return (String)columnNameList.get(index);
	}

	/**
	 * �÷� �̸��� ����Ʈ�� �����մϴ�.
	 */
	public List getColumnNameList() {
		return columnNameList;
	}

	/**
	 * �÷� �̸��� �迭�� �����մϴ�.
	 */
	public String[] getColumnNameArray() {
		int columnSize = columnNameList.size();
		String[] columnNameArray = new String[columnSize];
		for( int i=0; i<columnSize; i++ ) {
			columnNameArray[i] = (String)columnNameList.get(i);
		}
		return columnNameArray;
	}

	/**
	 * Matrix�� ���Ҹ� Object[][]�� ������ �迭�� �����մϴ�.
	 */
	public Object[][] getDataArray() {
		int rowSize = this.getRowSize();
		int columnSize = this.getColumnSize();
		Object[][] elements = new Object[rowSize][columnSize];
		for(int rowIndex=0; rowIndex<rowSize; rowIndex++) {
			for(int columnIndex=0; columnIndex<columnSize; columnIndex++) {
				elements[rowIndex][columnIndex] = this.get(rowIndex, columnIndex);
			}
        	}
        	return elements;
	}
	
	/**
	 * ��Ʈ�� ������ �Ǵ� columnName�� �÷��� �̸��� �ְ�  
	 * isAscending ���� �ø������̸� true ���������̸� false�� �ִ´�.
	 */
	public void sort(String columnName, boolean isAscending) {
		int columnIndex = this.getColumnNameIndex(columnName);
		MatrixComparator comparator = new MatrixComparator(columnIndex, isAscending);
		Collections.sort(rowList, comparator);
	}

	public static List toList(String input, String delim){
		List ret = new ArrayList();
		StringTokenizer st = new StringTokenizer(input, delim);
		while (st.hasMoreTokens()) {
			ret.add(st.nextToken().trim());
		}
		return ret;
	}
}

