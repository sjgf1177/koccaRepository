package com.credu.scorm;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.credu.scorm.*;
import com.credu.library.*;

public class getTableValue extends HttpServlet{	
	public void service(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		
		String filepath;
		String filename;
		String course_code;				
		String[][] table=null;
		String savePath;

		
				
		response.setContentType("application/octet-stream");
		ObjectInputStream objin = null;
		ObjectOutputStream objout = null;
		
		try{

			ConfigSet conf = new ConfigSet();			
		    savePath = conf.getProperty("dir.scoobjectpath");

			objin = new ObjectInputStream(request.getInputStream());
			objout = new ObjectOutputStream(response.getOutputStream());
			
		    
		    String tmpObject = (String)objin.readObject();  //애플릿으로 부터 값을 넘겨받음
		    
		    filename = tmpObject.substring(0,tmpObject.indexOf(":"));
			course_code = tmpObject.substring(tmpObject.indexOf(":")+1, tmpObject.length());
		
			if(course_code == null) course_code = "0";			
			course_code	= "0000000000" + course_code;			
			course_code	= course_code.substring(course_code.length()-10);

		    objin.close();
		

			if(filename != null){							
				// 저장할 디렉토리 (절대경로)				
			//    filepath = PathSetting.getRootPath() + course_code;
			    filepath = savePath + course_code;
				System.out.println("파싱을 하고 있습니다1");						   
			    manifestTableBean tablebean = new manifestTableBean();
				System.out.println("파싱을 하고 있습니다1");	
		    	tablebean.setXmlFile(filepath,filename);
		    	table = tablebean.getTableValue();	    		    			    	
		    	System.out.println("파싱이 끝났습니다.");
		    	System.out.println("값을 보내고 있습니다");
		    	objout.writeObject(table);
		    	objout.close();	
		    	System.out.println("값을 넘겼슴");	    	
		    }else{
		    	System.out.println("값이 없음");
		    	objout.close();
		    }

		}catch(Exception e){
			System.out.println("error");
			e.printStackTrace();
		}finally{			
			if(objout!=null)objout.close();
			if(objin != null)objin.close();
		}    
	}
}