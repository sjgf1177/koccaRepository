//**********************************************************
//1. ��      ��: ���� ���� ���� 
//2. ���α׷���: FileUnzip.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: 2003. 10. 12
//7. ��      ��: 
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
		String system_slash = File.separator;    //   �ش� �ý����� �и��ڸ� ��´�
		
		File zipFileName = new File(v_realPath + system_slash + p_zipFileName);    //   �������ϸ��� File ��ü �����Ѵ�

		try {	
			ZipFile zipFile = new ZipFile(zipFileName);		//	�ϴ� zip���� ��ü�� �ϳ� �����
		
			Enumeration e = zipFile.entries();       // zip ���� �ȿ� �ִ� ��� ������ ����Ǯ�� ���ؼ� Enumeration�� �ִ´�

			while(e.hasMoreElements()) {
				byte [] data = null;                                  // ����Ǯ�� ������ ����� ����Ʈ Array
				int length = 0;
				String v_path = "";       //   ����� ���Ͼ��� �����
				String v_file_name = "";    //    ����� ���Ͼ��� ���ϸ�
				
				String currentEntry = ((ZipEntry)e.nextElement()).getName();       // ���ϸ��� ��´�

				File entry = new File(currentEntry);
				String entry_path = entry.getPath();      //   �������� ���� �� ��Ʈ�� path ���

				int idx_point = entry_path.indexOf(".");			//		���� ���� .�� ã�´� (���Ͽ���)

				if(idx_point != -1) {    //   �ش� ��Ʈ���� ������ �ִٸ�
					int idx_slash = entry_path.lastIndexOf(File.separator);     //    ���� ���� �и��� ã��
					v_path = entry_path.substring(0, idx_slash+1);
					v_file_name = entry_path.substring(idx_slash+1);		//	���ϸ� ���´�	

					File filePath = new File(v_realPath + system_slash + v_path);			//  ���(����)�� �ش�Ǵ� File ��ü ����

					boolean b = filePath.mkdirs();				//   ��ο� �ش�Ǵ� ���� ������ �����Ѵ�

					byte buffer [] = new byte[1024*64];

					InputStream is = zipFile.getInputStream(zipFile.getEntry(currentEntry));

					File fileName = new File(filePath, v_file_name);	   

					FileOutputStream fos = new FileOutputStream(fileName);     //  fileName �� output ��Ʈ�� ����				

					BufferedOutputStream bos = new BufferedOutputStream(fos);			

					while(( length = is.read( buffer )) > 0)		//  input ��Ʈ�� �����ؼ� ������ �д´�
						bos.write(buffer, 0, length);		//  BufferedOutputStream �� buffer  ��ŭ ���� ����

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
				boolean b = zipFileName.delete();			//  ����� ������ Ǯ���ٸ� �ش� �������� �����Ѵ�
			}
		}catch ( IOException ie ) {
			success = false;
			ie.printStackTrace();
		}
		return success;
	}
}
