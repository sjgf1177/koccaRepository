package com.credu.scorm;

import java.util.*;
import java.sql.*;
import com.credu.scorm.*;


public class MatrixUtil{

	public MatrixUtil(){}


	/* �־��� 2���� �迭�� Matrix������ ��ȯ�Ͽ� ���� */
	public static Matrix toMatrix(String array[][], String str_FieldNames) throws Exception{
		/* ������ Matrix�� ���� */
		Matrix matrix = new Matrix(str_FieldNames);
		List nameList = Matrix.toList(str_FieldNames, ",");
		
		try{
			if(array == null){
				System.out.println("[Fail] MatrixUtil.java : toMatrix = ��ȯ�Ϸ��� �迭�� Null�̾ ��ȯ�� �� �����ϴ�.");
			}else{
				int idx_x, idx_y;
				for(idx_x = 0; idx_x < array.length; idx_x++){
					List newRow = new ArrayList();

					for(idx_y = 0; idx_y < array[idx_x].length; idx_y++){
						newRow.add(array[idx_x][idx_y]);
					}

					/* ���� ������� ������ �߰� */
					matrix.addRow(newRow);
				}
			}
		}catch(Exception ex){
			System.out.println("[Error] MatrixUtil.java : toMatrix = "+ex.getMessage());
		}

		return matrix;
	}

	/* �־��� ���ڵ���� Matrix������ ��ȯ�Ͽ� ���� */
	public static Matrix toMatrix(ResultSet rs, String str_FieldNames) throws SQLException {
		/* ������ Matrix�� ���� */
		Matrix matrix = new Matrix(str_FieldNames);

		List nameList = Matrix.toList(str_FieldNames, ",");
		try{
			/* ResultSet���κ��� ���پ� �Ͼ�� newRow�� ����� Matrix�� �߰� */
			while (rs.next()) {
				List newRow = new ArrayList();

				for (int i = 0; i < nameList.size(); i++) {
					String columnName = ((String) nameList.get(i)).toUpperCase();
					if(!columnName.trim().equals("")){
						newRow.add(rs.getString(columnName));
					}
				}

				/* ���� ������� ������ �߰� */
				matrix.addRow(newRow);
			}
		} catch(Exception ex){
			System.out.println("[Error] MatrixUtil.java : toMatrix = "+ex.getMessage());
		} finally {
			rs.close();
		}
		
		return matrix;
	}

	/* �־��� ���ڵ�¿��� ���� �ʵ带 ��� Matrix������ ��ȯ�Ͽ� ���� */
	public static Matrix toMatrix(ResultSet rs, String str_FieldNames, String str_FieldTypes) throws SQLException {
		/* ������ Matrix�� ���� */
		Matrix matrix = new Matrix(str_FieldNames);

		List nameList = Matrix.toList(str_FieldNames, ",");
		List typeList = Matrix.toList(str_FieldTypes, ",");

		try{
			/* ResultSet���κ��� ���پ� �Ͼ�� newRow�� ����� Matrix�� �߰� */
			while (rs.next()) {
				List newRow = new ArrayList();

				for (int i = 0; i < nameList.size(); i++) {
					String columnType = ((String) typeList.get(i)).toUpperCase();
					
					/* Ÿ�Կ� ���� ������ ������Ʈ�� �޾Ƽ� newRow�� �߰��� */
					if ("STRING".equals(columnType)) {
						newRow.add(rs.getString(i+1));
					}
					else if ("NUMBER".equals(columnType)) {
						newRow.add(rs.getString(i+1));
					}
					else {
						System.out.println("[Error] MatrixUtil : Unknown Column Type = " + columnType);
						newRow.add(rs.getString(i+1));
					}
				}
				/* ���� ������� ������ �߰� */
				matrix.addRow(newRow);
			}
		} catch(Exception ex){
			System.out.println("[Error] MatrixUtil.java : toMatrix = "+ex.getMessage());
		}

		
		return matrix;
	}
}