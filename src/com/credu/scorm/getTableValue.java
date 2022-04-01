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
			
		    
		    String tmpObject = (String)objin.readObject();  //���ø����� ���� ���� �Ѱܹ���
		    
		    filename = tmpObject.substring(0,tmpObject.indexOf(":"));
			course_code = tmpObject.substring(tmpObject.indexOf(":")+1, tmpObject.length());
		
			if(course_code == null) course_code = "0";			
			course_code	= "0000000000" + course_code;			
			course_code	= course_code.substring(course_code.length()-10);

		    objin.close();
		

			if(filename != null){							
				// ������ ���丮 (������)				
			//    filepath = PathSetting.getRootPath() + course_code;
			    filepath = savePath + course_code;
				System.out.println("�Ľ��� �ϰ� �ֽ��ϴ�1");						   
			    manifestTableBean tablebean = new manifestTableBean();
				System.out.println("�Ľ��� �ϰ� �ֽ��ϴ�1");	
		    	tablebean.setXmlFile(filepath,filename);
		    	table = tablebean.getTableValue();	    		    			    	
		    	System.out.println("�Ľ��� �������ϴ�.");
		    	System.out.println("���� ������ �ֽ��ϴ�");
		    	objout.writeObject(table);
		    	objout.close();	
		    	System.out.println("���� �Ѱ彿");	    	
		    }else{
		    	System.out.println("���� ����");
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