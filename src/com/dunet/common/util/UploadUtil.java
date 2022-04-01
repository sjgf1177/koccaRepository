/**
 * Package Name		: com.dunet.common.util.UploadUtil
 * Description		: ���� ���ε� ���� ������� ���Ϲ޴� ��ü
 * Make Date		: 2010.01.12
 * Author			: dunet
 * Version			: J_3.0.00
 * Last Amend Date	: 2010.01.12
 * Last Amender		: dunet
 */

package com.dunet.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.ConfigSet;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;

public class UploadUtil
{
	public UploadUtil() {
	}


	/**
	 * ���� ���ε� ��� ������ �ӽ� ���ε� ���ϸ��� �����մϴ�.
	 * @param servletName  ������ ���� �̸�
	 * @param paramName    INPUT FILE NAME
	 * @param realFileName �������ϸ�
	 * @param k            ���Ϲ�ȣ(������ ��)
	 * @param userid	        �������̵�
	 * @return
	 */
	public String getSaveNewFileName(String servletName, String paramName, String realFileName, int k, String userid) {
		String newFileName = "";
		servletName = servletName.substring(0, servletName.lastIndexOf("Servlet"));
		try{
			String fileExtension  = "";
			String v_currentDate = FormatDate.getDate("yyyyMMddHHmmss");

			int index = realFileName.lastIndexOf('.');

			if ( index >= 0) {      // Ȯ���ڰ� ������
				fileExtension = realFileName.substring(index+1);    // Ȯ���ڸ� ����
			}

			if (!fileExtension.equals("")) {
				newFileName = servletName + "_" + paramName.substring(2) + "_" +v_currentDate + k + "_" + userid + "." + fileExtension;       // ���ο� ���ϸ��� �����
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return newFileName;
	}

	/**
	 * ������ ÷������ ���, ���� �ϰ�ó��(DB ���� X )
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public static int fnRegisterAttachFile(RequestBox box) throws Exception
    {

		int isOk = 1;

		//box.getBoolean(key)

		String sServletnm 	= box.getString("p_servletnm"); // �ӽ� Ȯ�ο� ������
		String sRelativeDir = box.getString("p_relativedir");

		Vector aryStatus   	= box.getVector("p_status");
		Vector arySaveFile 	= box.getVector("p_savefile");
		Vector aryRealFile 	= box.getVector("p_realfile");
		Vector aryFileValue = box.getVector("p_filevalue");	// �ӽ� Ȯ�ο� ������
		Vector aryTempPath  = box.getVector("p_temppath");

		Vector aryDelFileSeq   	= box.getVector("p_del_fileseq");   // �ӽ� Ȯ�ο�
		Vector aryDelSaveFile 	= box.getVector("p_del_realfile");  // �ӽ� Ȯ�ο�

		ArrayList<String> aryNewSaveFile = new ArrayList<String>();
		ArrayList<String> aryRealSaveFile = new ArrayList<String>();

        ConfigSet conf = new ConfigSet();
        String rootPath = conf.getProperty("dir.home");

		//Vector aryNewSaveFile = new Vector();
		//Vector aryRealSaveFile = new Vector();

		//System.out.println("���� ��� ���� �� : "+aryDelFileSeq.size());

		try{
			// ������ ���� ����� �ִ��� üũ�Ѵ�.   <== DAO Update �޼ҵ忡�� �����մϴ�.
			//if ( aryDelFileSeq !=null && aryDelFileSeq.size() >0)
			//{
			//	int		len				= aryDelFileSeq.size();
			//
			//	for (int i = 0; i < len; i++)
			//	{System.out.println("���� : "+i);
			//		FileManager.deleteFile(aryDelSaveFile);
			//	}
			//}

			if ( aryStatus !=null && aryStatus.size() >0)
			{
				for(int i = 0 ; i < aryStatus.size() ; i++ ) {
					String v_status = (String)aryStatus.get(i);
					// ���� �߰��� ���ϸ� ���ε�
					if(v_status.equals("new")){
						String v_saveFile = (String)arySaveFile.get(i);
						String v_realFile = (String)aryRealFile.get(i);
						String v_tempPath = (String)aryTempPath.get(i);


						String i_sSourceFilePath	= v_tempPath.replace("//","/");
						String i_sTargetFilePath	= (getUploadDir(box, sServletnm) + v_saveFile).replace("\\\\","\\");
System.out.println("========================= i_sSourceFilePath : " + i_sSourceFilePath);
System.out.println("========================= i_sTargetFilePath : " + i_sTargetFilePath);
 						fileMove(i_sSourceFilePath, i_sTargetFilePath);

						// DB�Է� �κ�(���� - Bean Ŭ�������� ���� ��Ĵ�� ��)

						// InputFile..Method ���� �ϰ�ó����
						//aryNewSaveFile.add(sRe lativeDir+v_saveFile);
                        aryNewSaveFile.add(i_sTargetFilePath.replace(rootPath,""));
						aryRealSaveFile.add(v_realFile);
					}
				}
			}
			box.put("arySaveFileName", aryNewSaveFile);
			box.put("aryRealFileName", aryRealSaveFile);
		} catch(Exception e) {
			System.out.println("=========================================");
			System.out.println("# UploadUtil.fnRegisterAttachFile Error #");
			System.out.println(e.toString());
			System.out.println("=========================================");
			isOk = 0 ;
		}
		return isOk;
	}

	/**
	 * �ܼ��� ÷������ ���, ���� �ϰ�ó��(DB ���� X )
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public static int fnRegisterAttachFileForSingular(RequestBox box){

		int isOk = 1;

		Vector aryInputFile = box.getVector("p_inputFileParamName");   // �� �ܼ��� ÷�������� �Ķ���͸� �迭

		String v_inputFileParamName = "";		// ÷������ �Ķ���͸�
		String v_status				= "";		// ÷������ ����
		String v_saveFile 			= "";		// ÷������ �������ϸ�
		String v_realFile 			= "";		// ÷������ �������ϸ�
		String v_tempPath 			= "";		// ÷������ �ӽ�������
		String sRelativeDir			= "";		// ÷������ ������(/upload/.../)
		String v_filesize			= "";       // ÷������ ������

		try{
			if ( aryInputFile !=null && aryInputFile.size() >0)
			{
				for(int i = 0 ; i < aryInputFile.size() ; i++ ) {
					v_inputFileParamName = (String)aryInputFile.get(i);

					v_status = box.getString(v_inputFileParamName+"_status");

					// ���� �߰��� ���ϸ� ���ε�
					if(v_status.equals("new")){
						v_saveFile 	= box.getString(v_inputFileParamName+"_savefile");
						v_realFile 	= box.getString(v_inputFileParamName+"_realfile");
						v_tempPath 	= box.getString(v_inputFileParamName+"_temppath");
						sRelativeDir 	= box.getString(v_inputFileParamName+"_relativedir");
						v_filesize   	= box.getString(v_inputFileParamName+"_filesize");

						String i_sSourceFilePath	= v_tempPath;
						String i_sTargetFilePath	= getUploadDir(box, v_inputFileParamName) + v_saveFile;

						fileMove(i_sSourceFilePath, i_sTargetFilePath);

						// Insert �޼ҵ� ���� ������ �Ķ���Ͱ�
						box.put(v_inputFileParamName+"_savefile", sRelativeDir+"\\"+v_saveFile);  // �������ϸ�
						box.put(v_inputFileParamName+"_realfile", v_realFile);  // �������ϸ�

					}
				}
			}
		} catch(Exception e) {
			System.out.println("=========================================");
			System.out.println("# UploadUtil.fnRegisterAttachFile Error #");
			System.out.println(e.toString());
			System.out.println("=========================================");
			isOk = 0 ;
		}
		return isOk;
	}

	public String getTempUploadDir(String sServletName, String sYear, String sSubj) throws Exception{
		ConfigSet conf = new ConfigSet();

		String  sDirKey = conf.getDir(conf.getProperty("dir.upload"), sServletName);
		String  sRelativePath   = conf.getProperty("dir.upload." + sDirKey);
		String  sUploadTempPath = conf.getProperty("dir.home") + "upload" + File.separator + "tempUploadFile" + File.separator + FormatDate.getDate("yyyy-MM-dd") + sRelativePath;

		String v_subj = sSubj;
		String v_year = sYear;

		if(!v_subj.equals("") && !v_year.equals("")) {     		// ����Ʈ ���� ��츸 �ش��
			sUploadTempPath = sUploadTempPath + v_year + File.separator + v_subj+"\\";                	// ����Ʈ ����� ������ ����Ǵ� ���
		}

		return sUploadTempPath;
	}

	public String getTempUploadDir(String sServletName) throws Exception{
		return getTempUploadDir(sServletName, "", "");
	}

	public String getWebTempUploadDir(String sServletName, String sYear, String sSubj) throws Exception{
		ConfigSet conf = new ConfigSet();

		String  sDirKey = conf.getDir(conf.getProperty("dir.upload"), sServletName);
		String  sRelativePath   = conf.getProperty("dir.upload." + sDirKey);
		String  sWebUploadTempPath = "/tempUploadFile/"+FormatDate.getDate("yyyy-MM-dd")+ sRelativePath+"/";

		String v_subj = sSubj;
		String v_year = sYear;

		if(!v_subj.equals("") && !v_year.equals("")) {     		// ����Ʈ ���� ��츸 �ش��
			sWebUploadTempPath = sWebUploadTempPath + v_year + File.separator + v_subj+"\\";                	// ����Ʈ ����� ������ ����Ǵ� ���
		}

		return sWebUploadTempPath;
	}

	public String getWebTempUploadDir(String sServletName) throws Exception{
		return getWebTempUploadDir(sServletName, "", "");
	}

	public String getRelativePath(String sServletName, String sYear, String sSubj) throws Exception{
		ConfigSet conf = new ConfigSet();

		String  sDirKey = conf.getDir(conf.getProperty("dir.upload"), sServletName);
		String  sRelativePath   = conf.getProperty("dir.upload." + sDirKey);

		String v_subj = sSubj;
		String v_year = sYear;

		if(!v_subj.equals("") && !v_year.equals("")) {     		// ����Ʈ ���� ��츸 �ش��
			sRelativePath = sRelativePath + v_year + File.separator + v_subj+"\\";                	// ����Ʈ ����� ������ ����Ǵ� ���
		}

		return sRelativePath;
	}

	public String getRelativePath(String sServletName) throws Exception{
		return getRelativePath(sServletName, "", "");
	}

	public static String getUploadDir(RequestBox box, String sInputFileParamName) throws Exception{

		String sServletName = box.getString(sInputFileParamName+"_servletnm");
		
		ConfigSet conf = new ConfigSet();

		String  sDirKey = conf.getDir(conf.getProperty("dir.upload"), sServletName);
		String  sRelativePath   = conf.getProperty("dir.upload." + sDirKey);
		String  sUploadTempPath = conf.getProperty("dir.home") + sRelativePath;

		String v_subj = box.getString("g_subj");
		if(v_subj.equals("")) v_subj = box.getString("p_subj");  // �ӽ�...g_... �� ���̴°��� ã�ƾ���...

		String v_year = box.getString("g_year");
		if(v_year.equals("")) v_year = box.getString("p_year");

		if(!v_subj.equals("") && !v_year.equals("")) {     		// ����Ʈ ���� ��츸 �ش��
			sUploadTempPath = sUploadTempPath + v_year + File.separator + v_subj+"\\";                	// ����Ʈ ����� ������ ����Ǵ� ���
			//v_relativePath = v_relativePath + v_year + File.separator + v_subj+ File.separator;
		}

		return sUploadTempPath;
	}

	// ���� ����
	public static boolean fileCopy(String i_sSourceFilePath, String i_sTargetFilePath) {
		boolean	result	= true;

		FileInputStream 	inputStream 	= null;
		FileOutputStream 	outputStream 	= null;
		File				targetfile		= null;
		FileChannel 		fcin			= null;
		FileChannel 		fcout			= null;
		long 				size			= 0l;

		File    file    = new File(i_sTargetFilePath);
		if (!file.exists()) {
			file.mkdirs();
		}

		try {
			targetfile		= new File(i_sTargetFilePath);

			if (targetfile.exists())
			{
				fileDelete(i_sTargetFilePath);
			}

			inputStream 	= new FileInputStream(i_sSourceFilePath);
			outputStream 	= new FileOutputStream(i_sTargetFilePath);
			fcin			= inputStream.getChannel();
			fcout			= outputStream.getChannel();
			size			= fcin.size();

			fcin.transferTo(0, size, fcout);
			fcout.close();
			fcin.close();
			outputStream.close();
			inputStream.close();

		} catch (Exception e) {
			result		= false;
			e.printStackTrace();
		}

		return result;
	}

	// ���� ����
	public static boolean fileDelete(String i_sSourceFilePath)
	{
		boolean	result	= true;

		File file	= new File( i_sSourceFilePath );

		if (file.exists())
		{
			try {
				file.delete();
			} catch (Exception e) {
				result		= false;
				System.out.println("### UploadUtil error ###");
				System.out.println("### UploadUtil.fileDelete( " + i_sSourceFilePath + " ) ###");
				System.out.println("");
				e.printStackTrace();
			}
		}
		return result;
	}

	public  static boolean fileMove(String i_sSourceFilePath, String i_sTargetFilePath) {
		boolean	result	= true;

		result		= fileCopy(i_sSourceFilePath, i_sTargetFilePath);

		if (result)
		{
			result		= fileDelete(i_sSourceFilePath);
		}
		return result;
	}

}
