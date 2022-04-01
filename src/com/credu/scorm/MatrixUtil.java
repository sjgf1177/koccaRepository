package com.credu.scorm;

import java.util.*;
import java.sql.*;
import com.credu.scorm.*;


public class MatrixUtil{

	public MatrixUtil(){}


	/* 주어진 2차원 배열을 Matrix형으로 변환하여 리턴 */
	public static Matrix toMatrix(String array[][], String str_FieldNames) throws Exception{
		/* 리턴할 Matrix를 생성 */
		Matrix matrix = new Matrix(str_FieldNames);
		List nameList = Matrix.toList(str_FieldNames, ",");
		
		try{
			if(array == null){
				System.out.println("[Fail] MatrixUtil.java : toMatrix = 변환하려는 배열이 Null이어서 변환할 수 없습니다.");
			}else{
				int idx_x, idx_y;
				for(idx_x = 0; idx_x < array.length; idx_x++){
					List newRow = new ArrayList();

					for(idx_y = 0; idx_y < array[idx_x].length; idx_y++){
						newRow.add(array[idx_x][idx_y]);
					}

					/* 새로 만들어진 한줄을 추가 */
					matrix.addRow(newRow);
				}
			}
		}catch(Exception ex){
			System.out.println("[Error] MatrixUtil.java : toMatrix = "+ex.getMessage());
		}

		return matrix;
	}

	/* 주어진 레코드셋을 Matrix형으로 변환하여 리턴 */
	public static Matrix toMatrix(ResultSet rs, String str_FieldNames) throws SQLException {
		/* 리턴할 Matrix를 생성 */
		Matrix matrix = new Matrix(str_FieldNames);

		List nameList = Matrix.toList(str_FieldNames, ",");
		try{
			/* ResultSet으로부터 한줄씩 일어내어 newRow로 만들어 Matrix에 추가 */
			while (rs.next()) {
				List newRow = new ArrayList();

				for (int i = 0; i < nameList.size(); i++) {
					String columnName = ((String) nameList.get(i)).toUpperCase();
					if(!columnName.trim().equals("")){
						newRow.add(rs.getString(columnName));
					}
				}

				/* 새로 만들어진 한줄을 추가 */
				matrix.addRow(newRow);
			}
		} catch(Exception ex){
			System.out.println("[Error] MatrixUtil.java : toMatrix = "+ex.getMessage());
		} finally {
			rs.close();
		}
		
		return matrix;
	}

	/* 주어진 레코드셋에서 지정 필드를 골라 Matrix형으로 변환하여 리턴 */
	public static Matrix toMatrix(ResultSet rs, String str_FieldNames, String str_FieldTypes) throws SQLException {
		/* 리턴할 Matrix를 생성 */
		Matrix matrix = new Matrix(str_FieldNames);

		List nameList = Matrix.toList(str_FieldNames, ",");
		List typeList = Matrix.toList(str_FieldTypes, ",");

		try{
			/* ResultSet으로부터 한줄씩 일어내어 newRow로 만들어 Matrix에 추가 */
			while (rs.next()) {
				List newRow = new ArrayList();

				for (int i = 0; i < nameList.size(); i++) {
					String columnType = ((String) typeList.get(i)).toUpperCase();
					
					/* 타입에 따라서 적절한 오브젝트로 받아서 newRow에 추가함 */
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
				/* 새로 만들어진 한줄을 추가 */
				matrix.addRow(newRow);
			}
		} catch(Exception ex){
			System.out.println("[Error] MatrixUtil.java : toMatrix = "+ex.getMessage());
		}

		
		return matrix;
	}
}