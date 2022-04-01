package com.credu.scorm;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * 레이블을 가지는 열과 여러 개의 행을 가지는 데이터를 담는데 사용되는 Matrix 클래스입니다.
 * FlexMatrix의 기능을 단순화하여 적은 메모리를 차지하고 나은 성능을 제공합니다.
 * <li>선언 : new Matrix("컬럼명1,컬럼명2,컬럼명3") 또는 new Matrix(컬럼명을 가진 String[] 배열)
 * <li>행 설정 : addRow(List addedRow)
 * <li>행 얻음 : getRow(int rowIndex)
 * <li>행 제거 : removeRow(int rowIndex)
 * <li>값 설정 : get(int rowIndex, String columnName), get(int rowIndex, String columnIndex)
 * <li>행렬 정보 : getRowSize(), getColumnSize()
 * <li>행번호 찾기 : getRowIndex(String columnName 또는 int columnIndex, Object obj)
 * <li>내용 보기 : toString() 또는 toHtmlString() 메쏘드를 이용하면 됩니다.
 * 작성일 : 2001.10.31 컬럼별로 소트 기능 제공. 구분자에 ';'이외에 ','도 사용가능하게 변경. 구분자 사이에 ' ' 공란이 있는 경우 trim()시킴.
 * @author 한상목 
 * 수정 :2001.11.22 백민철 : getRowMap,addRowMap
 */
public class Matrix implements Serializable {

	public static void main(String[] args) {
		String[] columnNames = { "name", "age" };
		
		Matrix fm = new Matrix(columnNames);

		List row1 = new ArrayList();
		row1.add("가");
		row1.add(new Integer("3"));
		fm.addRow(row1);
		
		List row2 = new ArrayList();
		row2.add("다");
		row2.add(new Integer("5"));
		fm.addRow(row2);
		
		List row3 = new ArrayList();
		row3.add("나");
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
	* 컬럼의 이름이 들어갑니다.
	*/
	public List columnNameList;

	/**
	* 각 행이 리스트로 들어가고 이 리스트의 원소는 리스트로 되어 있습니다.
	*/
	public List rowList;

	/**
	 * rowSize=0 이고 columnSize=0 인 Matrix의 생성자
	 */
	public Matrix() {
		columnNameList = new ArrayList();
		rowList = new ArrayList();
	}
	
	/**
	 * String 배열로 주어지는 columnNames로 컬럼명을 정하는 Matrix의 생성자
	 */
	public Matrix(String[] columnNames) {
		columnNameList = new ArrayList( Arrays.asList( columnNames ) );
		rowList = new ArrayList();
	}
	
	/**
	 * String으로 주어지는 columnNames로 컬럼명을 정하는 Matrix의 생성자
	 */
	public Matrix( String columnNames ) {
		columnNameList = this.toList(columnNames, ";,");
		rowList = new ArrayList();
	}

	/**
	 * 가장 아래에 한 행 추가
	 * @return 성공적으로 추가되면 true 아니면 false
	 */
	public boolean addRow( List inputRow ) {
		if( inputRow.size() != this.getColumnSize() )
			return false;
		rowList.add( inputRow );
		return true;
	}

	/**
	 * 특정한 위치에 한 행 추가
	 * @return 성공적으로 추가되면 true 아니면 false
	 */
	public boolean addRow( int rowIndex, List inputRow ) {
		if( inputRow.size() != this.getColumnSize() )
			return false;
		rowList.add( rowIndex, inputRow );
		return true;
	}

	/**
	 * 가장 아래에 맵으로 된 한 행 추가
	 * @return 성공적으로 추가되면 true 아니면 false
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
	 * 특정위치에 맵으로 된 한 행 추가
	 * @return 성공적으로 추가되면 true 아니면 false
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
	 * 지정한 위치에 있는 한 행을 삭제
	 */
	public void removeRow( int rowIndex ) {
		rowList.remove( rowIndex );	
	}

	/**
	 * (행번호, 열번호)에 있는 Object를 리턴함
	 */
	public Object get( int rowIndex, int columnIndex ) {
		return ((List)rowList.get(rowIndex)).get(columnIndex);
	}

	/**
	 * (행번호, 열번호)에 값을 수정함
	 */
	public void set( int rowIndex, int columnIndex, String str_columnValue ) {
		((List)rowList.get(rowIndex)).set(columnIndex, str_columnValue);
	}
	public void set( int rowIndex, String columnName, String str_columnValue ) {
		int columnIndex = this.getColumnNameIndex(columnName);
		((List)rowList.get(rowIndex)).set(columnIndex, str_columnValue);
	}

	/**
	 * (행번호, 열이름)에 있는 Object를 리턴함
	 */
	public Object get( int rowIndex, String columnName ) {
		int columnIndex = this.getColumnNameIndex(columnName);
		return ((List)rowList.get(rowIndex)).get(columnIndex);
	}

	/**
	 * (행번호, 열번호)에 있는 Object를 리턴함
	 */
	public String getString( int rowIndex, int columnIndex ) {
		return (String)((List)rowList.get(rowIndex)).get(columnIndex);
	}

	/**
	 * (행번호, 열이름)에 있는 Object를 리턴함
	 */
	public String getString( int rowIndex, String columnName ) {
		int columnIndex = this.getColumnNameIndex(columnName);
		return (String)((List)rowList.get(rowIndex)).get(columnIndex);
	}

	/**
	 * (행번호, 열이름)에 있는 Object를 리턴함
	 */
	public String getString( int rowIndex, String columnName, String defaultValue ) {
		int columnIndex = this.getColumnNameIndex(columnName);
		if((String)((List)rowList.get(rowIndex)).get(columnIndex) == null)
			return defaultValue;
		else
			return (String)((List)rowList.get(rowIndex)).get(columnIndex);
	}

	/**
	 * (행번호, 열번호)에 있는 Object를 리턴함
	 */
	public int getInt( int rowIndex, int columnIndex ) {
		try {
			return Integer.parseInt((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getInt() 메쏘드를 이용해서 캐스팅을 하기 위해선 String 타입 또는 Integer 타입으로 저장된 데이터이야 합니다.");
		}
	}

	/**
	 * (행번호, 열이름)에 있는 Object를 리턴함
	 */
	public int getInt(int rowIndex, String columnName) {
		try {
			int columnIndex = this.getColumnNameIndex(columnName);
			return Integer.parseInt((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getInt() 메쏘드를 이용해서 캐스팅을 하기 위해선 String 타입으로 저장된 데이터이야 합니다.");
		}
	}

	/**
	 * (행번호, 열이름)에 있는 Object를 리턴함
	 */
	public int getInt(int rowIndex, String columnName, int defaultValue) {
		try {
			int columnIndex = this.getColumnNameIndex(columnName);
			if((String)((List)rowList.get(rowIndex)).get(columnIndex) == null)
				return defaultValue;
			else
				return Integer.parseInt((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getInt() 메쏘드를 이용해서 캐스팅을 하기 위해선 String 타입으로 저장된 데이터이야 합니다.");
		}
	}

	/**
	 * (행번호, 열번호)에 있는 Object를 리턴함
	 */
	public long getLong( int rowIndex, int columnIndex ) {
		try {
			return Long.parseLong((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getLong() 메쏘드를 이용해서 캐스팅을 하기 위해선 String 타입으로 저장된 데이터이야 합니다.");
		}
	}

	/**
	 * (행번호, 열이름)에 있는 Object를 리턴함
	 */
	public long getLong( int rowIndex, String columnName ) {
		try {
			int columnIndex = this.getColumnNameIndex(columnName);
			return Long.parseLong((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getLong() 메쏘드를 이용해서 캐스팅을 하기 위해선 String 타입으로 저장된 데이터이야 합니다.");
		}
	}
	
	/**
	 * (행번호, 열번호)에 있는 Object를 리턴함
	 */
	public float getFloat( int rowIndex, int columnIndex ) {
		try {
			return Float.parseFloat((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getFloat(() 메쏘드를 이용해서 캐스팅을 하기 위해선 String 타입으로 저장된 데이터이야 합니다.");
		}
	}

	/**
	 * (행번호, 열이름)에 있는 Object를 리턴함
	 */
	public float getFloat( int rowIndex, String columnName ) {
		try {
			int columnIndex = this.getColumnNameIndex(columnName);
			return Float.parseFloat((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getFloat(() 메쏘드를 이용해서 캐스팅을 하기 위해선 String 타입으로 저장된 데이터이야 합니다.");
		}
	}
	
	/**
	 * (행번호, 열번호)에 있는 Object를 리턴함
	 */
	public double getDouble( int rowIndex, int columnIndex ) {
		try {
			return Double.parseDouble((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getDouble() 메쏘드를 이용해서 캐스팅을 하기 위해선 String 타입으로 저장된 데이터이야 합니다.");
		}
	}

	/**
	 * (행번호, 열이름)에 있는 Object를 리턴함
	 */
	public double getDouble( int rowIndex, String columnName ) {
		try {
			int columnIndex = this.getColumnNameIndex(columnName);
			return Double.parseDouble((String) ((List)rowList.get(rowIndex)).get(columnIndex));
		} catch (ClassCastException e) {
			throw new ClassCastException("getDouble() 메쏘드를 이용해서 캐스팅을 하기 위해선 String 타입으로 저장된 데이터이야 합니다.");
		}
	}


	/**
	 * 행번호로 주어진 특정 행에 있는 Object의 List로 리턴함
	 */
	public List getRow( int rowIndex ) {
		return (List) rowList.get(rowIndex);
	}
	
	/**
	 * 행번호로 주어진 특정 행에 있는 Object를 Map으로 리턴함
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
	 * 행의 크기를 구함
	 */
	public int getRowSize() {
		return rowList.size();
	}

	/**
	 * 열의 크기를 구함
	 */
	public int getColumnSize() {
		return columnNameList.size();
	}
	
	/**
	 * Matrix를 HTML 테이블 형식으로 출력합니다.
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
	 * Matrix를 일반적인 리스트의 toString()형태로 출력합니다.
	 */	
	public String toString() {
		return rowList.toString();
	}
	
	/**
	 * 이름으로 열의 인덱스를 리턴
	 */
	public int getColumnNameIndex( String columnName ) {
		/* 에러시 메세지를 출력함. 코딩상 편의를 위해서 Exception은 내보내지 않음 */
		int index = columnNameList.indexOf( columnName );
		
		if (index < 0) {
			String msg = "[ERROR] Matrix Error : '" + columnName + "' not found in current column names " + columnNameList.toString();
			throw new NoSuchElementException(msg);
		}
		return index;
	}
	
	/**
	 * 입력한 Object가 특정 열의 몇번째 행에 있는지를 index 값을 리턴합니다.
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
	 * 입력한 Object가 특정 열의 몇번째 행에 있는지를 index 값을 리턴합니다.
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
	 * 입력한 Object가 특정 행의 몇번째 열에 있는지를 index 값을 리턴합니다.
	 */
	public int getColumnIndex( int rowIndex, Object obj ) {
		List thisRow = (List)rowList.get(rowIndex);
		return thisRow.indexOf( obj );
	}

	/**
	 * 컬럼 인덱스로 컬럼 이름을 리턴합니다.
	 */
	public String getColumnName(int index) {
		return (String)columnNameList.get(index);
	}

	/**
	 * 컬럼 이름의 리스트를 리턴합니다.
	 */
	public List getColumnNameList() {
		return columnNameList;
	}

	/**
	 * 컬럼 이름의 배열을 리턴합니다.
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
	 * Matrix의 원소를 Object[][]로 매핑한 배열을 리턴합니다.
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
	 * 소트의 기준이 되는 columnName에 컬럼의 이름을 주고  
	 * isAscending 값은 올림차순이면 true 내림차순이면 false를 넣는다.
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

