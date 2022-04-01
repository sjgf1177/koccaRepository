package controller.temp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.mozilla.universalchardet.UniversalDetector;

import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.temp.CreateFolderBean;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.temp.CreateFolder")
public class CreateFolder extends javax.servlet.http.HttpServlet implements Serializable {

	/**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * doPost
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            
            if(box.getString("p_type").equals("encoding")){
            	this.performEncoding(request, response, box, out);
            }else{
            	this.performCreate(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }
    
    public void subDirList(String source){
    	File dir = new File(source); 
    	File[] fileList = dir.listFiles(); 
    	try{
    		for(int i = 0 ; i < fileList.length ; i++){
    			File file = fileList[i]; 
    			if(file.isFile()){
    				// 파일이 있다면 파일 이름 출력
    				System.out.println("\t 파일 이름 = " + file.getName());
    			}else if(file.isDirectory()){
    				System.out.println("디렉토리 이름 = " + file.getName());
    				// 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색
    				subDirList(file.getCanonicalPath().toString()); 
    			}
    		}
    	}catch(IOException e){
    		
    	}
    }
    
    private void performCreate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	
    	FTPClient ftp = null;
    	
    	String readStr = "";
    	BufferedReader reader = null;
		StringBuffer sourceTag = null;
    	String filePath = "";
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            
            File sampleDir = new File("/data2/contents2");
			File[] dirs = sampleDir.listFiles();
			for(int i=0; i<dirs.length; i++){
				String dir = dirs[i].getPath();
				System.out.println("dir :: " + dir);
				File subDir = new File(dirs[i].getPath());
				if(subDir.isFile()){
					File[] contents = subDir.listFiles();
					for(int j=0; j<contents.length; j++){
						System.out.println("contents1 :: " + contents[j].getPath());
					}
				}else{
					File[] contents = subDir.listFiles();
					for(int j=0; j<contents.length; j++){
						File contentDir = new File(contents[j].getPath());
						if(contentDir.listFiles() == null){
							
							filePath = contents[j].getPath();
							String encoding =  readEncoding(contents[j]);
							sourceTag = new StringBuffer(1024);
							
							if(contents[j].getName().matches("^.*\\.((?i)js)$")){
								//파일 내용 읽기
								reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));
								while((readStr = reader.readLine()) != null) {
									sourceTag.append(readStr);
									sourceTag.append("\r\n");
								}
								reader.close();
								String source = sourceTag.toString();
								//
	//							source = source.replaceAll("rtmp://hunetfms.hyosungcdn.com/hunetfms/kocca", "/kocca/flv");
	//							source = source.replaceAll("http://onexpert.hvod.skcdn.com", "");
	//							source = source.replaceAll("/kocca/flv", "http://onexpert.hvod.skcdn.com/kocca/flv");
								source = source.replaceAll("rtmp://onexpert.fvod.skcdn.com/xpert/_definst_/flv:xpert/kocca/flv", "/kocca/flv");
								source = source.replaceAll("rtmp://onexpert.fvod.skcdn.com/xpert/_definst_/flv:xpert", "");
								source = source.replaceAll("/kocca/flv", "http://vod.kbrainc.com/kbrain/");
								
								System.out.println("source : " + source);
								
								FileWriter fw = new FileWriter(filePath); 
								BufferedWriter bw = new BufferedWriter(fw);
								bw.write(source);
								bw.close();
							}
							
						}else{
							
							File[] contentFiles = contentDir.listFiles();
							System.out.println("contentFiles :" + contentFiles);
							for(int k=0; k<contentFiles.length; k++){
//							System.out.println("contents2 :: " + contentFiles[k].getPath());
								
								filePath = contentFiles[k].getPath();
								String encoding =  readEncoding(contentFiles[k]);
								sourceTag = new StringBuffer(1024);
								
								if(contentFiles[k].getName().matches("^.*\\.((?i)js)$")){
									//파일 내용 읽기
									reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));
									while((readStr = reader.readLine()) != null) {
										sourceTag.append(readStr);
										sourceTag.append("\r\n");
									}
									reader.close();
									String source = sourceTag.toString();
	//								source = source.replaceAll("rtmp://hunetfms.hyosungcdn.com/hunetfms/kocca", "/kocca/flv");
	//								source = source.replaceAll("http://onexpert.hvod.skcdn.com", "");
	//								source = source.replaceAll("/kocca/flv", "http://onexpert.hvod.skcdn.com/kocca/flv");
									
									source = source.replaceAll("rtmp://onexpert.fvod.skcdn.com/xpert/_definst_/flv:xpert/kocca/flv", "/kocca/flv");
									source = source.replaceAll("rtmp://onexpert.fvod.skcdn.com/xpert/_definst_/flv:xpert", "");
									source = source.replaceAll("/kocca/flv", "http://vod.kbrainc.com/kbrain/");
									
									System.out.println("source : " + source);
									
									FileWriter fw = new FileWriter(filePath); 
									BufferedWriter bw = new BufferedWriter(fw);
									bw.write(source);
									bw.close();
								}
							}
						}
						
					}
					
					//File[] dirs = sampleDir.listFiles();
				}
				
			}
			/*
            */
			
			
            
            
            CreateFolderBean bean = new CreateFolderBean();
            ArrayList list = bean.selectList(box);
            
            
            /*
            if(list != null){
            	for(int i=0; i<list.size(); i++){
            		DataBox dbox = (DataBox) list.get(i);
            		System.out.println(dbox.getString("d_dirpath"));
            		File dir = new File("D:/contents/upload/openclass"+dbox.getString("d_dirpath"));
            		if(!dir.isDirectory()){
            			dir.mkdirs();
            		}
            	}
            }
            */
            

            

        } catch (Exception ex) {
        	ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        } finally {
        	if(ftp != null && ftp.isConnected()){
        		try{
        			ftp.disconnect();
        		}catch(Exception e){}
        	}
        }
    }
    
    
    private void performEncoding(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    		// 프로젝트 폴더 경로
    	System.out.println("encoding");
    		decodingProjectSources(new File("D:\\contents\\upload\\openclass"));
    	
    }
    public static  void decodingProjectSources(File file) throws IOException {
		// 자바 파일만 UTF-8로 디코딩
		if(file.isFile() && file.getName().matches("^.*\\.((?i)js)$")) {
			String encoding =  readEncoding(file);
			if(encoding != null && !encoding.equals("UTF-8")) {
				System.out.println("file : " + file.getPath());
				decodingFile(file, encoding);
			}
		} else if(file.isDirectory()) {
			File[] list = file.listFiles();
			for(File childFile : list) {
				decodingProjectSources(childFile);
			}
		}
	}
    
    public static void decodingFile(File file, String encoding) throws IOException {
    	System.out.println("decodingFile~~~" + file.getPath());
		Charset charset = Charset.forName(encoding);
		FileInputStream fis = new FileInputStream(file);
		ByteOutputStream fbs = new ByteOutputStream();
		
		byte[] buffer = new byte[4096];
		int n = 0;
		while((n = fis.read(buffer, 0, buffer.length)) > 0) {
			fbs.write(buffer, 0, n);
		}
		String source = fbs.toString();
		
		source = source.replaceAll("rtmp://onexpert.fvod.skcdn.com/xpert/_definst_/flv:xpert", "/kocca/flv");
		source = source.replaceAll("rtmp://onexpert.fvod.skcdn.com/xpert/_definst_/flv:xpert", "");
		source = source.replaceAll("/kocca/flv", "replace");
		
		System.out.println("source : " + source);
		
		
		CharBuffer charBuffer = charset.decode(ByteBuffer.wrap(source.getBytes()));
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.append(charBuffer);
		bw.close();
	}

	public static String readEncoding(File file) throws IOException {
		byte[] buf = new byte[4096];
		java.io.FileInputStream fis = new java.io.FileInputStream(file);
		UniversalDetector detector = new UniversalDetector(null);
		int nread;
		while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}
		detector.dataEnd();
		String encoding = detector.getDetectedCharset();
		detector.reset();
		buf = null;
		fis.close();
		return encoding == null?"UTF-8":encoding;
	}
}
