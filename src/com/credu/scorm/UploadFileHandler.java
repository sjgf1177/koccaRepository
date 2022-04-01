package com.credu.scorm;

//import java.net.*;
import java.util.*;
import java.util.Enumeration.*;
import java.io.*;
import com.credu.scorm.*;

public class UploadFileHandler{
	protected Enumeration formNames	= null;
	protected String formName		= new String();
	protected String fileName		= new String();
	protected String str_FileName		= new String();
	protected String str_OldFileName	= new String();
	protected String str_OldFileOption	= new String();
	protected char isUpload			= 'f';	// 'f'	: ����				(���ϸ����)	Fail
								// 's'	: �뷮�ʰ�				(���ϸ����)	SizeOver
								// 'r'	: Ȯ��������			(���ϸ����)	RejectFile
								// 'n'	: [���] ���ε� ����		(���ϸ����)	No
								// 'y'	: [���] ���ε� ����		(�����ϸ�)		Yes
								// 'd'	: [����] �������� ����		(���ϸ����)	Delete
								// 'o'	: [����] �����Ϸ� ���	(�����ϸ�)		OverWrite
								// 'h'	: [����] �������� ����		(�������ϸ�)	Hold

	public UploadFileHandler(MultipartRequest multi, String str_FileUploadPath, String str_RejectFileList, int int_LimitSize) throws Exception {
		try{
			String formName		= new String();
			String fileName		= new String();
			this.formNames		= multi.getFileNames();				// ������� �̸� ��ȯ (type = file �� input �� name ��ȯ)

			if(this.formNames.hasMoreElements()){
				formName	= (String)this.formNames.nextElement();		// �ڷᰡ ���� ��쿣 while ���� ���		  
				fileName	= multi.getFilesystemName(formName);
			}else{
				formName	= "";
				fileName	= "";
			}

			this.isUpload = 'n';
			if(fileName == null || fileName.equals("")) {				// ������ ���ε� ���� �ʾ�����
				this.isUpload	= 'n';
				this.str_FileName	= "";
			}else if(!fileName.equals("")){							// ������ ���ε� �Ǿ�����
				String fileName2	= fileName.substring(fileName.lastIndexOf("\\")+1);
				this.isUpload	= 'y';
				this.str_FileName	= fileName2;

				str_RejectFileList	= str_RejectFileList.toUpperCase();
				String fileExt = FileUtil.getFileExt(this.str_FileName).toUpperCase();
				if(str_RejectFileList.indexOf(fileExt) > -1){
					this.cancel(str_FileUploadPath, str_FileName);		// ���ε� �Ұ� Ȯ���ڸ� ���� ������ ���ε� �� ���
					this.isUpload	= 'r';	
				}
			}

			if(this.isUpload != 'f' && this.isUpload != 's' && this.isUpload != 'r'){	// ���ε� ����
				this.str_OldFileName	= multi.getParameter("old_filename");
				this.str_OldFileOption	= multi.getParameter("old_fileopt");
				if(this.str_OldFileName==null) this.str_OldFileName = "";
				if(this.str_OldFileOption==null) this.str_OldFileOption = "2";

				if(this.isUpload == 'y' && !this.str_OldFileName.equals("") && this.str_OldFileOption.equals("2")){	// ���ε�ư�, ���� �����ְ�, OverWrite Option�̸� (�������� ���������� �����ϸ����� ����)
					// �������� ����
					this.deleteFile(str_FileUploadPath, this.str_OldFileName);
					this.isUpload	= 'o';
				}else if( !this.str_OldFileName.equals("") && this.str_OldFileOption.equals("1")){			// ���� ���� �ְ�, �������� ���� �ɼ��̸� ���ε� �ư� ���� ���� ���Ϸ� ��ü)
					str_FileName	= str_OldFileName;
					this.isUpload	= 'h';
				}else if(!this.str_OldFileName.equals("") && this.str_OldFileOption.equals("3")){			// ÷�������� �ְų� ���ų� �������� �ְ� ���� �ɼ��̸� ���� ���� �����ع���
					// �������� ����
					this.deleteFile(str_FileUploadPath, this.str_OldFileName);
					str_FileName	= "";
					this.isUpload	= 'd';
				}else{	// 'n' �Ǵ� 'y'

				}
			}else{	// 'f' �Ǵ� 's' �Ǵ� 'r'

			}
		}catch(Exception ex){
			System.out.println("[Error] UploadFileHandler.java : UploadFileHandler = "+ex.getMessage());
			throw ex;
		}
	}

	public char getIsUpload(){
		return this.isUpload;
	}

	public String getUploadFileName(){
		return this.str_FileName;
	}

	public void cancel(String str_FilePath, String str_FileName){
		this.deleteFile(str_FilePath, str_FileName);
	}

	/* ���� ��ο��� ������ �����ϴ� ���� �޼ҵ� */
	public void deleteFile(String str_FilePath, String str_FileName){	// ������ ���� ����
		try{
			String str_FileFullPath	= new String();
			if(str_FilePath != null && !str_FilePath.equals("") && str_FileName != null && !str_FileName.equals("")){
				str_FileFullPath	= str_FilePath+ "\\" + str_FileName;
				FileUtil.doDeleteFile(str_FileFullPath);
			}
		}catch(Exception ex){
			System.out.println("[Error] UploadFileHandler.java : deleteFile = "+ex.getMessage());
		}
	}
}