//**********************************************************
//1. 제      목: 파일 삭제 
//2. 프로그램명: FileDelete.java
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

public class BetaFileDelete {

	public BetaFileDelete() {
	}

	public boolean allDelete(String v_realPath) {
		boolean delAllDir_success = false;
		boolean delFile_success = false;
		boolean delDir_success = false;
		boolean temp_success = false;
		File [] dirsAndFiles = null;
		int idx_point = 0;

		try {	
			File delAllDir = new File(v_realPath);    //   삭제할 폴더(하부폴더 및 파일을 포함한)의 File 객체 생성한다

			dirsAndFiles = delAllDir.listFiles();
//			System.out.println( "dirsAndFiles.length " + dirsAndFiles.length);   //  해당 path에 포함되는 폴더와 파일 배열

			for(int i = 0; i < dirsAndFiles.length; i++) {
				String dirAndFile = dirsAndFiles [i].toString();
				idx_point = dirAndFile.indexOf(".");			//		각 경로마다 제일 뒤의 .을 찾는다 (파일여부)

				if(idx_point != -1) {      //   파일이 존재한다면 먼저 파일 삭제
					delFile_success = dirsAndFiles [i].delete();   //  해당 경로의 파일 삭제
				}
				else {     //   폴더 인 경우
					temp_success = this.allDelete(dirAndFile);    //  하위에 폴더 및 파일 존재 여부 확인 후 삭제
					delDir_success = dirsAndFiles [i].delete();   //  해당 경로의 폴더 삭제
				}
			}
	//		delAllDir.delete();     //   마지막에 지정된 폴더까지 삭제
			delAllDir_success = true;
		}catch (Exception ie ) {
			delAllDir_success = false;
			ie.printStackTrace();
		}
		return delAllDir_success;
	}
}