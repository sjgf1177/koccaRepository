//**********************************************************
//1. 제      목: 파일 압축 해제 
//2. 프로그램명: FileUnzip.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: 2003. 10. 12
//7. 수      정: 
//                 
//**********************************************************
package com.credu.beta;

import com.credu.library.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;

public class FileUnzip {

	public FileUnzip() {
	}

	public boolean extract(String v_realPath, String p_zipFileName) {
		boolean success = false;
		String system_slash = File.separator;    //   해당 시스템의 분리자를 얻는다
		
		File zipFileName = new File(v_realPath + system_slash + p_zipFileName);    //   압축파일명의 File 객체 생성한다

		try {	
			ZipFile zipFile = new ZipFile(zipFileName);		//	일단 zip파일 객체를 하나 만든다
		
			Enumeration e = zipFile.entries();       // zip 파일 안에 있는 모든 파일을 압축풀기 위해서 Enumeration에 넣는다

			while(e.hasMoreElements()) {
				byte [] data = null;                                  // 압축풀린 파일이 저장될 바이트 Array
				int length = 0;
				String v_path = "";       //   압축된 파일안의 상대경로
				String v_file_name = "";    //    압축된 파일안의 파일명
				
				String currentEntry = ((ZipEntry)e.nextElement()).getName();       // 파일명을 얻는다

				File entry = new File(currentEntry);
				String entry_path = entry.getPath();      //   압축파일 안의 각 엔트리 path 얻기

				int idx_point = entry_path.indexOf(".");			//		제일 뒤의 .을 찾는다 (파일여부)

				if(idx_point != -1) {    //   해당 엔트리에 파일이 있다면
					int idx_slash = entry_path.lastIndexOf(File.separator);     //    제일 뒤의 분리자 찾기
					v_path = entry_path.substring(0, idx_slash+1);
					v_file_name = entry_path.substring(idx_slash+1);		//	파일명 나온다	

					File filePath = new File(v_realPath + system_slash + v_path);			//  경로(폴더)에 해당되는 File 객체 생성

					boolean b = filePath.mkdirs();				//   경로에 해당되는 폴더 없으면 생성한다

					byte buffer [] = new byte[1024*64];

					InputStream is = zipFile.getInputStream(zipFile.getEntry(currentEntry));

					File fileName = new File(filePath, v_file_name);	   

					FileOutputStream fos = new FileOutputStream(fileName);     //  fileName 에 output 스트림 연결				

					BufferedOutputStream bos = new BufferedOutputStream(fos);			

					while(( length = is.read( buffer )) > 0)		//  input 스트림 연결해서 끝까지 읽는다
						bos.write(buffer, 0, length);		//  BufferedOutputStream 에 buffer  만큼 쓰기 시작

					bos.flush();

					is.close();
					fos.close();
					bos.close();

			//		System.out.println( "# " + currentEntry + " extract success" );
				}
			}
			zipFile.close();
			success = true;
			if(success) {
				boolean b = zipFileName.delete();			//  제대로 압축이 풀린다면 해당 압축파일 삭제한다
			}
		}catch ( IOException ie ) {
			success = false;
			ie.printStackTrace();
		}
		return success;
	}
}
