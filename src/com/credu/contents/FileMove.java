//**********************************************************
//1. 제      목: 파일 이동 
//2. 프로그램명: FileMove.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: 2003. 10. 12
//7. 수      정: 
//
//**********************************************************
package com.credu.contents;

import com.credu.library.*;
import java.io.*;
import java.util.*;

public class FileMove {

	public FileMove() {
	}

	public boolean move(String v_realPath, String v_tempPath, String p_fileName) {
		boolean move_success = false;
		boolean delete_success = false;
		int length = 0;

		String system_slash = File.separator;    //   해당 시스템의 분리자를 얻는다

		try {	
			File tempFile = new File(v_tempPath + system_slash + p_fileName);    //   임시폴더에서 이동할 파일명의 File 객체 생성한다
			System.out.println("tempFile : " + tempFile);  
			
			FileInputStream fis = new FileInputStream(tempFile);	       
			//  tempFile에서 내용을 읽기 준비중		
			BufferedInputStream bis = new BufferedInputStream(fis);	
			//  읽은 데이터를 bufferedInputStream 에 임시저장
		        byte [] buf = new byte [1024];
			File realFile = new File(v_realPath + system_slash + p_fileName);    //   임시폴더에서 이동할 파일명의 File 객체 생성한다
                        //realFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(realFile);     
			//  realFile에 내용을 쓰기 준비중
                        BufferedOutputStream bos = new BufferedOutputStream(fos);	
			while((length = bis.read(buf)) > 0) {         
				//  한줄씩 읽기 시작 - 읽을 줄이 있을때까지 무한루프를 돈다
				bos.write(buf, 0, length);   //  buf 만큼 쓴다				
			}
			bos.flush();

			fis.close();
			bis.close();
			fos.close();
			bos.close();
			move_success = true;
			System.out.println("move_success.창훈아 성공했냐?"+move_success);
			if(move_success) {
				boolean b = tempFile.delete();			//  파일이동이 제대로 되었다면 해당 임시파일 삭제한다
			}
		}catch (Exception ie ) {
			move_success = false;
			ie.printStackTrace();
		}
		return move_success;
	}
	
	public boolean move(String v_realPath, String v_tempPath, String p_fileName, String s_fileName) {
		boolean move_success = false;
		boolean delete_success = false;
		int length = 0;

		String system_slash = File.separator;    //   해당 시스템의 분리자를 얻는다

		try {	
			File tempFile = new File(v_tempPath + system_slash + p_fileName);    //   임시폴더에서 이동할 파일명의 File 객체 생성한다
		
			FileInputStream fis = new FileInputStream(tempFile);	       
				//  tempFile에서 내용을 읽기 준비중		
			BufferedInputStream bis = new BufferedInputStream(fis);	
				//  읽은 데이터를 bufferedInputStream 에 임시저장

			byte [] buf = new byte [1024];

			File realFile = new File(v_realPath + system_slash + s_fileName);    //   임시폴더에서 이동할 파일명의 File 객체 생성한다

			FileOutputStream fos = new FileOutputStream(realFile);     
			//  realFile에 내용을 쓰기 준비중
			BufferedOutputStream bos = new BufferedOutputStream(fos);	

			while((length = bis.read(buf)) > 0) {         
				//  한줄씩 읽기 시작 - 읽을 줄이 있을때까지 무한루프를 돈다
				bos.write(buf, 0, length);   //  buf 만큼 쓴다				
			}
			bos.flush();

			fis.close();
			bis.close();
			fos.close();
			bos.close();
			move_success = true;
			if(move_success) {
				boolean b = tempFile.delete();			//  파일이동이 제대로 되었다면 해당 임시파일 삭제한다
			}
		}catch (Exception ie ) {
			move_success = false;
			ie.printStackTrace();
		}
		return move_success;
	}
}