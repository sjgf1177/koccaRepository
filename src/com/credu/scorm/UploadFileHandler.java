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
	protected char isUpload			= 'f';	// 'f'	: 실패				(파일명없음)	Fail
								// 's'	: 용량초과				(파일명없음)	SizeOver
								// 'r'	: 확장자제한			(파일명없음)	RejectFile
								// 'n'	: [등록] 업로드 없음		(파일명없음)	No
								// 'y'	: [등록] 업로드 성공		(새파일명)		Yes
								// 'd'	: [수정] 기존파일 삭제		(파일명없음)	Delete
								// 'o'	: [수정] 새파일로 덮어씀	(새파일명)		OverWrite
								// 'h'	: [수정] 기존파일 보존		(기존파일명)	Hold

	public UploadFileHandler(MultipartRequest multi, String str_FileUploadPath, String str_RejectFileList, int int_LimitSize) throws Exception {
		try{
			String formName		= new String();
			String fileName		= new String();
			this.formNames		= multi.getFileNames();				// 폼요소의 이름 반환 (type = file 인 input 의 name 반환)

			if(this.formNames.hasMoreElements()){
				formName	= (String)this.formNames.nextElement();		// 자료가 많을 경우엔 while 문을 사용		  
				fileName	= multi.getFilesystemName(formName);
			}else{
				formName	= "";
				fileName	= "";
			}

			this.isUpload = 'n';
			if(fileName == null || fileName.equals("")) {				// 파일이 업로드 되지 않았을때
				this.isUpload	= 'n';
				this.str_FileName	= "";
			}else if(!fileName.equals("")){							// 파일이 업로드 되었을때
				String fileName2	= fileName.substring(fileName.lastIndexOf("\\")+1);
				this.isUpload	= 'y';
				this.str_FileName	= fileName2;

				str_RejectFileList	= str_RejectFileList.toUpperCase();
				String fileExt = FileUtil.getFileExt(this.str_FileName).toUpperCase();
				if(str_RejectFileList.indexOf(fileExt) > -1){
					this.cancel(str_FileUploadPath, str_FileName);		// 업로드 불가 확장자를 가진 파일이 없로드 된 경우
					this.isUpload	= 'r';	
				}
			}

			if(this.isUpload != 'f' && this.isUpload != 's' && this.isUpload != 'r'){	// 업로드 정상
				this.str_OldFileName	= multi.getParameter("old_filename");
				this.str_OldFileOption	= multi.getParameter("old_fileopt");
				if(this.str_OldFileName==null) this.str_OldFileName = "";
				if(this.str_OldFileOption==null) this.str_OldFileOption = "2";

				if(this.isUpload == 'y' && !this.str_OldFileName.equals("") && this.str_OldFileOption.equals("2")){	// 업로드됐고, 기존 파일있고, OverWrite Option이면 (기존파일 지워버리고 새파일명으로 저장)
					// 기존파일 삭제
					this.deleteFile(str_FileUploadPath, this.str_OldFileName);
					this.isUpload	= 'o';
				}else if( !this.str_OldFileName.equals("") && this.str_OldFileOption.equals("1")){			// 기존 파일 있고, 기존파일 보존 옵션이면 업로드 됐건 말건 기존 파일로 대체)
					str_FileName	= str_OldFileName;
					this.isUpload	= 'h';
				}else if(!this.str_OldFileName.equals("") && this.str_OldFileOption.equals("3")){			// 첨부파일이 있거나 말거나 기존파일 있고 삭제 옵션이면 기존 파일 삭제해버림
					// 기존파일 삭제
					this.deleteFile(str_FileUploadPath, this.str_OldFileName);
					str_FileName	= "";
					this.isUpload	= 'd';
				}else{	// 'n' 또는 'y'

				}
			}else{	// 'f' 또는 's' 또는 'r'

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

	/* 지정 경로에서 파일을 삭제하는 실제 메소드 */
	public void deleteFile(String str_FilePath, String str_FileName){	// 삭제만 별도 가능
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