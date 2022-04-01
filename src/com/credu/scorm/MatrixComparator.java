package com.credu.scorm;

import java.io.*;
import java.util.*;
import java.text.*;

/**
 * Matrix의 각 행의 비교를 위해서 사용되는 Collator
 * 생성자로 비교되는 컬럼의 이름을 받는다.
 * 비교되는 컬럼은 Numeric 또는 String이어야 한다.
 */
public class MatrixComparator implements Comparator {
	
	int comparedColumnIndex = 0;
	boolean isAscending = true;
	
	/**
	 * 생성되는 MatrixComparator가 비교에 사용할 컬럼을  주어서 생성
	 * isAscending이 true인 경우 올림차순이고 false인 경우 내림차순
	 */
	public MatrixComparator(int comparedColumnIndex, boolean isAscending) {
		this.comparedColumnIndex = comparedColumnIndex;
		this.isAscending = isAscending;
	}
	
	/**
	 * Comparator가 구현하는 compare 메쏘드
	 */
	public int compare(Object firstRow, Object secondRow) {

		String className = ((List) firstRow).get(comparedColumnIndex).getClass().getName();
		
		
		if ("java.lang.String".equals(className)) {
			
			/* 한국의 가나다 순으로 비교 */
			Collator krCollator = Collator.getInstance(Locale.KOREA);
			
			/* 대소문자 무시하고 소트함  */
			krCollator.setStrength(Collator.PRIMARY);
			
			/* 값을 가져옴 */
			String firstValue = (String) ((List) firstRow).get(comparedColumnIndex);
			String secondValue = (String) ((List) secondRow).get(comparedColumnIndex);
			
			/* 내림차순과 올림차순 처리 */
			if (isAscending) {
				return krCollator.compare(firstValue, secondValue); 
			}
			return -1 * krCollator.compare(firstValue, secondValue); 
		}
		else if ("java.lang.Integer".equals(className)) {
			Integer firstValue = (Integer) ((List) firstRow).get(comparedColumnIndex);
			Integer secondValue = (Integer) ((List) secondRow).get(comparedColumnIndex);
			
			/* 내림차순과 올림차순 처리 */
			if (isAscending) {
				return firstValue.compareTo(secondValue); 
			}
			return -1 * firstValue.compareTo(secondValue); 
		}
		else if ("java.lang.Long".equals(className)) {
			Long firstValue = (Long) ((List) firstRow).get(comparedColumnIndex);
			Long secondValue = (Long) ((List) secondRow).get(comparedColumnIndex);
			
			/* 내림차순과 올림차순 처리 */
			if (isAscending) {
				return firstValue.compareTo(secondValue); 
			}
			return -1 * firstValue.compareTo(secondValue); 
		}
		else if ("java.lang.Double".equals(className)) {
			Double firstValue = (Double) ((List) firstRow).get(comparedColumnIndex);
			Double secondValue = (Double) ((List) secondRow).get(comparedColumnIndex);
			
			/* 내림차순과 올림차순 처리 */
			if (isAscending) {
				return firstValue.compareTo(secondValue); 
			}
			return -1 * firstValue.compareTo(secondValue); 
		}
		else if ("java.lang.Float".equals(className)) {
			Float firstValue = (Float) ((List) firstRow).get(comparedColumnIndex);
			Float secondValue = (Float) ((List) secondRow).get(comparedColumnIndex);
			
			/* 내림차순과 올림차순 처리 */
			if (isAscending) {
				return firstValue.compareTo(secondValue); 
			}
			return -1 * firstValue.compareTo(secondValue); 
		}
		else {
			String msg = "[ERROR] MatrixComparator can compare String, Integer, Long, Float, Double. Unsupported class type is given : " + className;
			throw new ClassCastException(msg);
		}
	}
}