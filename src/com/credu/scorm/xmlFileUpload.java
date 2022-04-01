package com.credu.scorm;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.credu.scorm.*;
import com.credu.library.*;
import com.credu.contents.*;
import com.credu.scorm.multi.*;
import com.credu.scorm.multi.multipart.*;

public class xmlFileUpload extends HttpServlet{	
	
	public void service(HttpServletRequest request,HttpServletResponse response){		
		boolean debug_mode = true;
	    	
		SCOBean bean = new SCOBean();
        
		String str_NextPageURL		= request.getParameter("NextPage_URL");
        String p_content_sync = request.getParameter("p_content_sync");
		String p_cpseq = request.getParameter("p_cpseq");
		
		int sizeLimit = 500 * 1024 * 1024 ; // 500메가까지 제한 넘어서면 예외발생

		ServletContext context = this.getServletContext();
		RequestDispatcher dispatcher;

		try{
			    ConfigSet conf = new ConfigSet();			
				String savePath = conf.getProperty("dir.scoobjectpath");

				System.out.println(savePath);

			    String v_scolocateno = bean.SelectScoLocate();
				String v_scolocateno_1 = v_scolocateno;
				
				v_scolocateno	= "0000000000" + v_scolocateno;			
				v_scolocateno	= v_scolocateno.substring(v_scolocateno.length()-10);

				File dir = new File(savePath+v_scolocateno);

				if (dir.exists()) {	// 만들려는 폴더가 존재하면 해당 폴더 먼저 지워버림
					FileUtilAdd fua = new FileUtilAdd();
					fua.deleteDir(dir);
				}

				//저장할 경로를 만들어준다. [과목코드] / [객체코드]
				savePath = savePath+v_scolocateno;
				if(new File(savePath).isDirectory()==false){
					new File(savePath).mkdirs();	 				
				}
			
				if (p_content_sync.equals("1")) {					
					com.credu.scorm.multi.MultipartRequest multi=new com.credu.scorm.multi.MultipartRequest(request, savePath, sizeLimit, "euc-kr", new DefaultFileRenamePolicy()); 
					
					Enumeration formNames		= multi.getFileNames();				// 폼의 이름 반환
					String formName			= (String)formNames.nextElement();		// 자료가 많을 경우엔 while 문을 사용
					String filename			= multi.getFilesystemName(formName);	// 파일의 이름 얻기

					savePath = savePath.replace('/', File.separatorChar) + File.separatorChar;
					File zipFile = new File(savePath + filename);
					UnZipHandler UnzipH = new UnZipHandler(zipFile, savePath);
					File manifestName = new File( savePath + "imsmanifest.xml");    

					request.setAttribute("NextPage_URL",str_NextPageURL);
					request.setAttribute("UNZIP_FLAG","1");   
					request.setAttribute("v_scolocateno_1",v_scolocateno_1); 
					request.setAttribute("v_unzip_filename",filename);	
					request.setAttribute("p_scolocate",v_scolocateno); 
					request.setAttribute("p_content_sync",p_content_sync); 
					request.setAttribute("p_cpseq",p_cpseq);

				} else {					
					com.credu.scorm.multi.MultipartRequest multi=new com.credu.scorm.multi.MultipartRequest(request, savePath, sizeLimit, "euc-kr", new DefaultFileRenamePolicy()); 										

					Enumeration formNames		= multi.getFileNames();				// 폼의 이름 반환
					String formName			= (String)formNames.nextElement();		// 자료가 많을 경우엔 while 문을 사용
					String filename			= multi.getFilesystemName(formName);	// 파일의 이름 얻기

					request.setAttribute("NextPage_URL",str_NextPageURL);
					request.setAttribute("UNZIP_FLAG","1");   
					request.setAttribute("v_scolocateno_1",v_scolocateno_1); 
					request.setAttribute("v_unzip_filename",filename);	
					request.setAttribute("p_scolocate",v_scolocateno);
					request.setAttribute("p_content_sync",p_content_sync); 
					request.setAttribute("p_cpseq",p_cpseq);
			    }	
			  
				dispatcher = context.getRequestDispatcher("/learn/admin/contents/za_SCOUnzipReturn.jsp");
				dispatcher.forward(request,response);

		} catch(Exception e) {
			try{

				request.setAttribute("UNZIP_FLAG","0");
				request.setAttribute("Error_MSG","파일 업로드가 되지 않았습니다.");
				request.setAttribute("NextPage_URL",str_NextPageURL);
				dispatcher = context.getRequestDispatcher("/learn/admin/contents/za_SCOUnzipReturn.jsp");
				dispatcher.forward(request,response);

			}catch(Exception er){
				er.printStackTrace();
			}			
		  }		       		       
	}
}
