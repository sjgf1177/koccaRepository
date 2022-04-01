package com.credu.scorm;

import java.io.*;
import java.util.*; 
import jxl.*; 
import jxl.write.*; 
import jxl.format.*;
//import alexit.lib.util.StringUtil;


public class ExcelUtil{
	private String str_ExcelPath		= new String();
	private String str_ExcelName		= new String();
	private String str_ExcelFull		= new String();
	private String str_ErrorMessage	= new String();
	private Workbook workbook		= null;
	private Sheet sheet			= null;
	private int totalRows;			// 총 행수
	private int totalCols;			// 총 열수
	private int totalRecord;			// 총 레코드 행수
	private int int_SheetNo;		// 기본 Sheet 번호 (0~)
	private int int_ColsCNT;			// 필수 열 수

	public ExcelUtil(){
		this.str_ExcelPath		= null;
		this.str_ExcelName	= null;
		this.str_ExcelFull		= null;
		this.workbook		= null;
		this.int_SheetNo		= 0;
		this.int_ColsCNT		= 0;
	}

	public ExcelUtil(String str_FilePath, String str_FileName){
		this.setWorkbook(str_FilePath, str_FileName);
	}

	public ExcelUtil(String str_FilePath, String str_FileName, int int_SheetNo, int int_ColsCNT){
		this.setWorkbook(str_FilePath, str_FileName, int_SheetNo, int_ColsCNT);
	}

	public void setWorkbook(String str_FilePath, String str_FileName){
		try{
			String str_TempPath	= str_FilePath.substring(str_FilePath.length()-1);
			if(str_TempPath.equals("/") || str_TempPath.equals("\\")){
				str_FilePath		= str_FilePath.substring(0, str_FilePath.length()-1);
			}
			str_FilePath			= StringUtil.replace(str_FilePath, "/", "\\");


			this.str_ExcelPath		= str_FilePath;
			this.str_ExcelName	= str_FileName;
			this.int_SheetNo		= 0;
			this.int_ColsCNT		= 0;

			if((str_ExcelPath!=null && !str_ExcelPath.equals("")) && (str_ExcelName!=null && !str_ExcelName.equals(""))){
				this.str_ExcelFull	= str_ExcelPath + "\\" + str_ExcelName;
System.out.println("str_ExcelFull="+str_ExcelFull);
				if(excelExist() == 1){
System.out.println("Exist");
					this.workbook	= Workbook.getWorkbook(new File(str_ExcelFull));
				}else{
System.out.println("NotExist");
					this.workbook	= null;
				}
			}else{
				this.workbook		= null;
			}
		}catch(Exception ex){
			System.out.println("Open Fail [1] !!"+ex.getMessage());
		}
	}

	public void setWorkbook(String str_FilePath, String str_FileName, int int_SheetNo, int int_ColsCNT) {
		try{
			this.setWorkbook(str_FilePath, str_FileName);
			this.setSheet(int_SheetNo, int_ColsCNT);
		}catch(Exception ex){
			System.out.println("Open Fail [2] !!"+ex.getMessage());
		}
	}

	public void setSheet(int int_SheetNo, int int_ColsCNT){
		try{
			if(int_SheetNo < 0){
				this.int_SheetNo	= 0;
				this.sheet		= null;

				this.totalRows	= -1;
				this.totalCols	= -1;
				this.int_ColsCNT	= 0;
			}else{
				this.int_SheetNo	= int_SheetNo;
				if(workbook != null){
					this.sheet		= workbook.getSheet(int_SheetNo); 
					this.totalRows	= sheet.getRows();		// 현 sheet의 총 행수
					this.totalCols	= sheet.getColumns();	// 현 sheet의 총 열수
System.out.println("totalRows="+totalRows);
System.out.println("totalCols="+totalCols);
					this.int_ColsCNT	= int_ColsCNT;
				}else{
					this.sheet		= null;

					this.totalRows	= -1;
					this.totalCols	= -1;
					this.int_ColsCNT= 0;
					System.out.println("[Fail] setSheet() : Sheet를 얻지 못했음, workbook 없음");
				}
			}
		}catch(Exception ex){
			System.out.println("Set Sheet Fail !!"+ex.getMessage());
		}
	}

	public Workbook getWorkbook(){
		return this.workbook;
	}

	public Sheet getSheet(){
		return this.sheet;
	}

	public int getRows(){
		return this.totalRows;
	}

	public int getCols(){
		return this.totalCols;
	}

	public int excelExist(){
		int exist_flag	= 0;
		File td		=  new File(this.str_ExcelPath);

		if (td.exists()) {
			File files[] = td.listFiles();
			exist_flag = 0;

			for (int i=0; i<files.length; i++) {
				String _file = files[i].getName();

				if (_file.equals(str_ExcelName)) {		//존재여부 체크  (삭제용도 아님)
					exist_flag	= 1;
				}
			}	
		}else{
			exist_flag = 0;
		}

		return exist_flag;
	}

	public String[][] toArray(){
		String[][] arr_Excel	= null;

		try{
			if(this.totalRows > 0 && this.totalCols >= this.int_ColsCNT){			// 기본 행&열 수 체크------------------------------ 1열에 타이틀 미포함일경우 (0), 포함할경우 1부터 시작
				int idx_x, idx_y, idx_m;
				arr_Excel	= new String[this.totalRows-1][this.totalCols];


				// 배열로 옮기기
				idx_m	= 0;
				for(idx_x = 1; idx_x < this.totalRows; idx_x++){		// 1행 타이틀 제외------------------------------ 1열에 타이틀 미포함일경우 (0), 포함할경우 1부터 시작
					Cell arrInfo[]	= this.sheet.getRow(idx_x);
					if(arrInfo[0].getContents() != null && !arrInfo[0].getContents().equals("")){
						for(idx_y = 0; idx_y < int_ColsCNT; idx_y++){	// 지정한 열 갯수만큼
							arr_Excel[idx_m][idx_y]	= arrInfo[idx_y].getContents();
						}
					}
					idx_m++;
				}
			}else{
				arr_Excel	= null;
				System.out.println("[Fail] ExcelUtil.java -- toArray() : 파일이 조건에 맞지 않아 배열로 변환할 수 없습니다.");
			}
		}catch(Exception ex){
			arr_Excel	= null;
			System.out.println("[Error] ExcelUtil.java -- toArray() : "+ex.getMessage());
		}

		return arr_Excel;
	}


	public Matrix toMatrix(String str_FieldNames){
		String arr_Excel[][]	= this.toArray();
		return this.toMatrix(arr_Excel, str_FieldNames);
	}
	public Matrix toMatrix(String arr_Excel[][], String str_FieldNames){
		try{
			return MatrixUtil.toMatrix(arr_Excel, str_FieldNames);
		}catch(Exception ex){
			System.out.println("[Error] ExcelUtil.java -- toMatrix() : "+ex.getMessage());
			return null;
		}
	}

}