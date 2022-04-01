package com.credu.scorm;

import java.io.*;
import java.util.*;
import java.text.*;

/**
 * Matrix�� �� ���� �񱳸� ���ؼ� ���Ǵ� Collator
 * �����ڷ� �񱳵Ǵ� �÷��� �̸��� �޴´�.
 * �񱳵Ǵ� �÷��� Numeric �Ǵ� String�̾�� �Ѵ�.
 */
public class MatrixComparator implements Comparator {
	
	int comparedColumnIndex = 0;
	boolean isAscending = true;
	
	/**
	 * �����Ǵ� MatrixComparator�� �񱳿� ����� �÷���  �־ ����
	 * isAscending�� true�� ��� �ø������̰� false�� ��� ��������
	 */
	public MatrixComparator(int comparedColumnIndex, boolean isAscending) {
		this.comparedColumnIndex = comparedColumnIndex;
		this.isAscending = isAscending;
	}
	
	/**
	 * Comparator�� �����ϴ� compare �޽��
	 */
	public int compare(Object firstRow, Object secondRow) {

		String className = ((List) firstRow).get(comparedColumnIndex).getClass().getName();
		
		
		if ("java.lang.String".equals(className)) {
			
			/* �ѱ��� ������ ������ �� */
			Collator krCollator = Collator.getInstance(Locale.KOREA);
			
			/* ��ҹ��� �����ϰ� ��Ʈ��  */
			krCollator.setStrength(Collator.PRIMARY);
			
			/* ���� ������ */
			String firstValue = (String) ((List) firstRow).get(comparedColumnIndex);
			String secondValue = (String) ((List) secondRow).get(comparedColumnIndex);
			
			/* ���������� �ø����� ó�� */
			if (isAscending) {
				return krCollator.compare(firstValue, secondValue); 
			}
			return -1 * krCollator.compare(firstValue, secondValue); 
		}
		else if ("java.lang.Integer".equals(className)) {
			Integer firstValue = (Integer) ((List) firstRow).get(comparedColumnIndex);
			Integer secondValue = (Integer) ((List) secondRow).get(comparedColumnIndex);
			
			/* ���������� �ø����� ó�� */
			if (isAscending) {
				return firstValue.compareTo(secondValue); 
			}
			return -1 * firstValue.compareTo(secondValue); 
		}
		else if ("java.lang.Long".equals(className)) {
			Long firstValue = (Long) ((List) firstRow).get(comparedColumnIndex);
			Long secondValue = (Long) ((List) secondRow).get(comparedColumnIndex);
			
			/* ���������� �ø����� ó�� */
			if (isAscending) {
				return firstValue.compareTo(secondValue); 
			}
			return -1 * firstValue.compareTo(secondValue); 
		}
		else if ("java.lang.Double".equals(className)) {
			Double firstValue = (Double) ((List) firstRow).get(comparedColumnIndex);
			Double secondValue = (Double) ((List) secondRow).get(comparedColumnIndex);
			
			/* ���������� �ø����� ó�� */
			if (isAscending) {
				return firstValue.compareTo(secondValue); 
			}
			return -1 * firstValue.compareTo(secondValue); 
		}
		else if ("java.lang.Float".equals(className)) {
			Float firstValue = (Float) ((List) firstRow).get(comparedColumnIndex);
			Float secondValue = (Float) ((List) secondRow).get(comparedColumnIndex);
			
			/* ���������� �ø����� ó�� */
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