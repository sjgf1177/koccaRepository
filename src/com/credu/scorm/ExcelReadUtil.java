package com.credu.scorm;

import java.io.*;
import java.util.*; 
import jxl.*; 
import jxl.write.*; 
import jxl.format.*;
//import alexit.lib.util.StringUtil;


public class ExcelReadUtil{
	private String str_ExcelPath		= new String();
	private String str_ExcelName	= new String();
	private String str_ExcelFull		= new String();
	private String str_ErrorMessage	= new String();
	private Workbook workbook	= null;
	private Sheet sheet				= null;
	private int totalRows;		// �� ���
	private int totalCols;			// �� ����
	private int totalRecord;		// �� ���ڵ� ���
	private int int_SheetNo;		// �⺻ Sheet ��ȣ (0~)
	private int int_ColsCNT;		// �ʼ� �� ��

	public ExcelReadUtil(){
		str_ExcelPath		= null;
		str_ExcelName		= null;
		str_ExcelFull		= null;
		workbook			= null;
		int_SheetNo		= 0;
	}

	public void setWorkbook(String str_FilePath, String str_FileName){
		try{
			str_ExcelPath		= str_FilePath;
			str_ExcelName		= str_FileName;

			if((str_ExcelPath!=null && !str_ExcelPath.equals("")) && (str_ExcelName!=null && !str_ExcelName.equals(""))){
				str_ExcelFull		= str_ExcelPath + "\\" + str_ExcelName;

				if(excelExist() == 1){
					workbook	= Workbook.getWorkbook(new File(str_ExcelFull));
				}else{
					workbook	= null;
				}
			}else{
				workbook		= null;
			}
		}catch(Exception ex){
			System.out.println("Open Fail [1] !!"+ex.getMessage());
		}
	}

	public void setWorkbook(String str_FilePath, String str_FileName, int int_SheetNo, int int_ColsCNT) {
		try{
			setWorkbook(str_FilePath, str_FileName);
			setSheet(int_SheetNo, int_ColsCNT);
		}catch(Exception ex){
			System.out.println("Open Fail [2] !!"+ex.getMessage());
		}
	}

	public void setSheet(int int_SheetNo, int int_ColsCNT){
		try{
			if(int_SheetNo < 0){
				this.int_SheetNo	= 0;
				sheet				= null;

				totalRows			= -1;
				totalCols			= -1;
				this.int_ColsCNT	= 0;
			}else{
				this.int_SheetNo	= int_SheetNo;
				if(workbook != null){
					sheet			= workbook.getSheet(int_SheetNo); 

					totalRows		= sheet.getRows();		// �� sheet�� �� ���
					totalCols		= sheet.getColumns();	// �� sheet�� �� ����

					this.int_ColsCNT= int_ColsCNT;
				}else{
					sheet			= null;

					totalRows		= -1;
					totalCols		= -1;
					this.int_ColsCNT= 0;
				}
			}
		}catch(Exception ex){
			System.out.println("Set Sheet Fail !!"+ex.getMessage());
		}
	}

	public Workbook getWorkbook(){
		return workbook;
	}

	public Sheet getSheet(){
		return sheet;
	}

	public int getRows(){
		return totalRows;
	}

	public int getCols(){
		return totalCols;
	}

	public int excelExist(){
		int exist_flag	= 0;
		File td			=  new File(str_ExcelPath);

		if (td.exists()) {
			File files[] = td.listFiles();
			exist_flag = 0;

			for (int i=0; i<files.length; i++) {
				String _file = files[i].getName();

				if (_file.equals(str_ExcelName)) {		//���翩�� üũ  (�����뵵 �ƴ�)
					exist_flag	= 1;
				}
			}	
		}else{
			exist_flag = 0;
		}

		return exist_flag;
	}

	public String[][] getExcel(){
		int totRecord		= 0;
		String arr_List[][]	= new String[totalRows-1][totalCols+4];	// 4�� �߰�
		int idx_x, idx_y, idx_m;

		try{
			if(totalRows > 0 && totalCols == int_ColsCNT){	// �⺻ ��&�� �� üũ------------------------------ 1���� Ÿ��Ʋ �������ϰ�� (0), �����Ұ�� 1���� ����
				// �迭�� �ű��
				idx_m	= 0;
				for(idx_x = 1; idx_x < totalRows; idx_x++){		// 1�� Ÿ��Ʋ ����------------------------------ 1���� Ÿ��Ʋ �������ϰ�� (0), �����Ұ�� 1���� ����
					Cell arrInfo[]				= sheet.getRow(idx_x);
					if(arrInfo[0].getContents() != null && !arrInfo[0].getContents().equals("")){
						for(idx_y = 0; idx_y < totalCols; idx_y++){
							arr_List[idx_m][idx_y]	= arrInfo[idx_y].getContents();
						}
						arr_List[idx_m][totalCols]	= "-1";
						arr_List[idx_m][totalCols+1]	= "-1";
						arr_List[idx_m][totalCols+2]	= "-1";
						arr_List[idx_m][totalCols+3]	= "-1";

						totRecord++;
					}
					idx_m++;
				}
			}
		}catch(Exception ex){
			for(idx_x = 1; idx_x < arr_List.length; idx_x++){
				for(idx_y = 0; idx_y < arr_List[0].length; idx_y++){
					arr_List[idx_x][idx_y]	= "";
				}
			}
			totRecord	= 0;

			System.out.println("Get Array Fail !!"+ex.getMessage());
		}
		totalRecord	 = totRecord;

		return arr_List;
	}

	public String printExcel(){
		String arr_List[][];
		arr_List					= getExcel();
		String str_RecordList	= new String();
		int idx_x, idx_y, idx_m;

		try{
			// �迭 ��� ���
			String temp_align	= new String();
			int temp_padding	= 10;
			int temp_depth		= 0;
			int counter			= 0;

			for(idx_x = 0; idx_x < totalRecord; idx_x++){
				str_RecordList			+=	"<tr height='25' class='listcontent'>\n";
				temp_depth				= getDepth(arr_List[idx_x][0]);

				for(idx_y = 0; idx_y < totalCols; idx_y++){
					temp_align			= "left";
					temp_padding		= 10;

					if(idx_y == 0){
						temp_padding	+= temp_depth * 10;
					}else{
						if(idx_y >= 2){
							temp_padding		= 0;
							temp_align			= "center";
						}
					}
					str_RecordList		+=	"	<td align='"+temp_align+"' style='padding-left:"+temp_padding+"'>"+ arr_List[idx_x][idx_y] +"</td>\n";
				}
				str_RecordList			+=	"</tr>\n";
			}
		}catch(Exception ex){
			System.out.println("Print Excel Fail !!"+ex.getMessage());

			if(totalRecord == 0){
				if(str_ExcelPath == null || str_ExcelPath.equals("")){
					str_RecordList		= "<tr height='25' class='listcontent'><td colspan='4' align='center'></td></tr>\n";
				}else{
					str_RecordList		= "<tr height='25' class='listcontent'><td colspan='4' align='center'>������ ã�� ���߽��ϴ�.</td></tr>\n";
				}
				for(idx_x = 1; idx_x < 10; idx_x++){
					str_RecordList		+= "<tr height='25' class='listcontent'><td colspan='4' align='center'></td></tr>\n";
				}
			}else{
				str_RecordList			= "<tr height='25' class='listcontent'><td colspan='4' align='center'>������ ���� ���߽��ϴ�.!!</td></tr>\n";
			}
		}

		return str_RecordList;
	}

	public String printArray(){
		String arr_List[][];
		arr_List					= getArray();
		String str_RecordList	= new String();
		int idx_x, idx_y, idx_m;

		try{
			// �迭 ��� ���
			String temp_align	= new String();
			int temp_padding	= 10;
			int counter			= 0;
			int temp_cols		= 0;

			for(idx_x = 0; idx_x < totalRecord; idx_x++){
				temp_cols					= arr_List[0].length;
				str_RecordList				+=	"<tr height='25' class='listcontent'>\n";
				arr_List[idx_x][totalCols+2]	= ++counter+"";

				for(idx_y = 0; idx_y < temp_cols-1; idx_y++){
					temp_align				= "left";
					temp_padding			= 10;
					if(idx_y == 0){
						if(arr_List[idx_x][(temp_cols-1)] != null && !arr_List[idx_x][(temp_cols-1)].equals(""))
							temp_padding	+= Integer.parseInt(arr_List[idx_x][(temp_cols-1)]) * 10;
					}else{
						if(idx_y >= 2){
							temp_padding	= 0;
							temp_align		= "center";
						}
					}
					str_RecordList			+=	"	<td align='"+temp_align+"' style='padding-left:"+temp_padding+"'>"+ arr_List[idx_x][idx_y] +"</td>\n";
				}
				str_RecordList				+=	"</tr>\n";
			}
		}catch(Exception ex){
			System.out.println("Print Array Fail !!"+ex.getMessage());

			if(totalRecord == 0){
				if(str_ExcelPath == null || str_ExcelPath.equals("")){
					str_RecordList		= "<tr height='25' class='listcontent'><td colspan='4' align='center'></td></tr>\n";
				}else{
					str_RecordList		= "<tr height='25' class='listcontent'><td colspan='4' align='center'>������ ã�� ���߽��ϴ�.</td></tr>\n";
				}
				for(idx_x = 1; idx_x < 10; idx_x++){
					str_RecordList		+= "<tr height='25' class='listcontent'><td colspan='4' align='center'></td></tr>\n";
				}
			}else{
				str_RecordList			= "<tr height='25' class='listcontent'><td colspan='7' align='center'>�������� �Ľ� ����!!</td></tr>\n";
			}
		}

		return str_RecordList;
	}
	public String[][] getArray(){	 // �Ľ�
		String arr_List[][];
		int totRecord		= 0;
		arr_List				= getExcel();

		try{
			// depth�� ���� �����ڵ�, �����ڵ�, ���ļ��� ���ϱ�
			String temp_content	= new String();
			int temp_depth		= 0;
			int max_code		= 0;
			int up_code			= 0;
			int order_num		= 0;
			int check_depth		= 0;
			int check_count		= 1;
			int idx_x, idx_y, idx_m;

			max_code			= getMaxCode();

			while(check_count > 0){		// ���� depth�� row ������ 1�̻��� ��� ���� depth ã��
				check_count	= 0;		// ã�� row �� �ʱ�ȭ

				for(idx_m = 0; idx_m < totalRecord; idx_m++){			// �� row �� ��ŭ �ݺ�
					temp_content			= arr_List[idx_m][0];		// ���� row�� ���谪

					// depth ���ϱ�
					temp_depth				= 0;
					temp_depth				= getDepth(temp_content);

					if(temp_depth == check_depth - 1){								// ���� üũ ���(depth)�� ���� depth�� row�� ��� �����ڵ带 �����ڵ�� ����ϱ� ���� ����
						if(Integer.parseInt(arr_List[idx_m][totalCols]) != up_code){		// �ش� ���� depth�� ���� depth�� ó���� ��� order_num = 0
							up_code	= Integer.parseInt(arr_List[idx_m][totalCols]);
							order_num	= 0;
						}
					}else if(temp_depth == check_depth){					// ���� üũ ���(depth)�� row�� ��� 
						arr_List[idx_m][totalCols]	= ++max_code+"";		// �����ڵ�
						arr_List[idx_m][totalCols+1]	= up_code+"";			// �����ڵ�
						arr_List[idx_m][totalCols+2]	= ++order_num+"";		// ���ļ���
						arr_List[idx_m][totalCols+3]	= temp_depth+"";		// depth

						check_count++;										// ���� depth�� �ش��ϴ� ���ڵ� ����
					}
				}

				check_depth++;

				if(check_depth > 1000) break;						// ���ѷ��� ���ϱ� ����.. (�ӽ�)
			}
		}catch(Exception ex){
			System.out.println("Get Array Fail !!"+ex.getMessage());
		}

		return arr_List;
	}

	public int getDepth(String str_IndexNo){
		int depth_no			= 0;
		int index_length			= 0;

		// depth ���ϱ�
		if(str_IndexNo != null){
			index_length		= str_IndexNo.trim().length();		// ���谪 �� ����

			if(str_IndexNo.charAt(index_length-1) == '.'){			// �Ǹ������� ���ʿ��� ������(.) �����ϱ�
				str_IndexNo		= str_IndexNo.substring(0, index_length-2);
				index_length	= str_IndexNo.trim().length();		// ���谪 �� ���� �ٽ� ���ϱ�
			}
//			depth_no = index_length - StringUtil.replace(str_IndexNo, ".", "").length();
//System.out.println("depth: "+depth_no);/*
			for(int idx=0; idx<index_length; idx++){				// ���� �� ���̿��� ������(.) ���� ���ϱ� (���� depth)
				if(str_IndexNo.charAt(idx) == '.') depth_no++;
			}
		}

		return depth_no;
	}

	public int getMaxCode(){
		return 10;
	}
}