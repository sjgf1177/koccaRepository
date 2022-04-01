//**********************************************************
//1. ��      ��: ���� �̵� 
//2. ���α׷���: FileMove.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: 2003. 10. 12
//7. ��      ��: 
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

		String system_slash = File.separator;    //   �ش� �ý����� �и��ڸ� ��´�

		try {	
			File tempFile = new File(v_tempPath + system_slash + p_fileName);    //   �ӽ��������� �̵��� ���ϸ��� File ��ü �����Ѵ�
			System.out.println("tempFile : " + tempFile);  
			
			FileInputStream fis = new FileInputStream(tempFile);	       
			//  tempFile���� ������ �б� �غ���		
			BufferedInputStream bis = new BufferedInputStream(fis);	
			//  ���� �����͸� bufferedInputStream �� �ӽ�����
		        byte [] buf = new byte [1024];
			File realFile = new File(v_realPath + system_slash + p_fileName);    //   �ӽ��������� �̵��� ���ϸ��� File ��ü �����Ѵ�
                        //realFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(realFile);     
			//  realFile�� ������ ���� �غ���
                        BufferedOutputStream bos = new BufferedOutputStream(fos);	
			while((length = bis.read(buf)) > 0) {         
				//  ���پ� �б� ���� - ���� ���� ���������� ���ѷ����� ����
				bos.write(buf, 0, length);   //  buf ��ŭ ����				
			}
			bos.flush();

			fis.close();
			bis.close();
			fos.close();
			bos.close();
			move_success = true;
			System.out.println("move_success.â�ƾ� �����߳�?"+move_success);
			if(move_success) {
				boolean b = tempFile.delete();			//  �����̵��� ����� �Ǿ��ٸ� �ش� �ӽ����� �����Ѵ�
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

		String system_slash = File.separator;    //   �ش� �ý����� �и��ڸ� ��´�

		try {	
			File tempFile = new File(v_tempPath + system_slash + p_fileName);    //   �ӽ��������� �̵��� ���ϸ��� File ��ü �����Ѵ�
		
			FileInputStream fis = new FileInputStream(tempFile);	       
				//  tempFile���� ������ �б� �غ���		
			BufferedInputStream bis = new BufferedInputStream(fis);	
				//  ���� �����͸� bufferedInputStream �� �ӽ�����

			byte [] buf = new byte [1024];

			File realFile = new File(v_realPath + system_slash + s_fileName);    //   �ӽ��������� �̵��� ���ϸ��� File ��ü �����Ѵ�

			FileOutputStream fos = new FileOutputStream(realFile);     
			//  realFile�� ������ ���� �غ���
			BufferedOutputStream bos = new BufferedOutputStream(fos);	

			while((length = bis.read(buf)) > 0) {         
				//  ���پ� �б� ���� - ���� ���� ���������� ���ѷ����� ����
				bos.write(buf, 0, length);   //  buf ��ŭ ����				
			}
			bos.flush();

			fis.close();
			bis.close();
			fos.close();
			bos.close();
			move_success = true;
			if(move_success) {
				boolean b = tempFile.delete();			//  �����̵��� ����� �Ǿ��ٸ� �ش� �ӽ����� �����Ѵ�
			}
		}catch (Exception ie ) {
			move_success = false;
			ie.printStackTrace();
		}
		return move_success;
	}
}