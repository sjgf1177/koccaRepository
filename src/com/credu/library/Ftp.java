
package com.credu.library;

import com.enterprisedt.net.ftp.*;
import java.io.*;

  /**
 * <p>제목: Ftp 관련 라이브러리</p>
 * <p>설명: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author 이정한
 *@date 2003. 12
 *@version 1.0
 */
public class Ftp {
    private FTPClient ftp;
    private String defaultServerDir;
    private String defaultClientDir;

    /**
    *Ftp 서버에 접속할 IP, ID, PW 등을 cresys.properties 에서 읽어서 서버에 접속한다.
    @param p_fileName  HTML 파일명
    */    
    public Ftp() throws Exception { 
        try{
            ConfigSet conf = new ConfigSet();  
            ftp = new FTPClient(conf.getProperty("ftp.server.ip"));        //   Ftp 서버에 접속
            ftp.login(conf.getProperty("ftp.server.id"), conf.getProperty("ftp.server.pw"));        
            ftp.setType(FTPTransferType.BINARY);        //       binary type 으로 세팅
            
            defaultServerDir = conf.getProperty("ftp.server.dir");
            defaultClientDir = conf.getProperty("ftp.client.dir");
        }catch(Exception ex) {
            ex.printStackTrace();
            Log.sys.println(this, ex, "Happen to Ftp()");     
            throw new Exception(ex.getMessage());
        }               
    }

    /**
    *Ftp 서버에서 파일을 다운로드한다.
    @param p_serverDir  서버 파일경로
    @param p_clientDir  클라이언트 파일경로
    @param p_filename  파일명
    */    
    public int download(String p_serverDir, String p_clientDir, String p_filename) throws Exception {
        try{
            if(p_filename.equals("")) return -2;
            
         /*   FTPFile [] ftpFile = ftp.dirDetails(p_serverDir);
            
            for(int i = 0; i < ftpFile.length; i++) {
                if(!ftpFile [i].equals(p_filename)) return -1;
            }*/
        
            byte [] buf = ftp.get(p_serverDir + File.separator + p_filename);       //      Ftp 서버에서 파일을 가져올 준비를 한다
            
            File downFile = new File(p_clientDir, p_filename);    //   Ftp 클라이언트에서 다운로드 파일객체 생성

            FileOutputStream fos = new FileOutputStream(downFile);     
            BufferedOutputStream bos = new BufferedOutputStream(fos);	

            bos.write(buf);   //  Ftp 클라이언트에 다운로드		
            bos.flush();

            fos.close();
            bos.close();
        }
        catch(Exception ex) { 
            ex.printStackTrace();
            Log.sys.println(this, ex, "Happen to download(" + p_serverDir + ",\"" + p_clientDir + ",\"" + p_filename + "\")");     
            throw new Exception(ex.getMessage());
        }
        return 1;
    }

    /**
    *Ftp 서버에서 파일을 다운로드한다.(default 경로)
    @param p_filename  파일명
    */     
    public int download(String p_filename) throws Exception {
        int i = 0;
        try {
            i = this.download(defaultServerDir, defaultClientDir, p_filename);
        }
        catch(Exception ex) { 
            ex.printStackTrace();
            Log.sys.println(this, ex, "Happen to download(" + p_filename + "\")");     
            throw new Exception(ex.getMessage());
        }
        return i;
    }

    /**
    *Ftp 서버로 파일을 업로드한다.
    @param p_serverDir  서버 파일경로
    @param p_clientDir  클라이언트 파일경로
    @param p_filename  파일명
    */            
    public void upload(String p_serverDir, String p_clientDir, String p_filename) throws Exception {
        
        try{
            if(p_filename.equals("")) return;
            
            File upFile = new File(p_clientDir, p_filename);    //   Ftp 클라이언트에 업로드할 파일객체 생성
//            System.out.println(upFile);
            FileInputStream fis = new FileInputStream(upFile); 
            BufferedInputStream bis = new BufferedInputStream(fis);	
            
            ftp.put(bis, p_serverDir + File.separator + p_filename, true);      //  Ftp 서버로 업로드		
            
            fis.close();
            bis.close();
        }catch(Exception ex) { 
            ex.printStackTrace();
            Log.sys.println(this, ex, "Happen to upload(" + p_serverDir + ",\"" + p_clientDir + ",\"" + p_filename + "\")");     
            throw new Exception(ex.getMessage());
        }
    }

    /**
    *Ftp 서버로 파일을 업로드한다.(default 경로)
    @param p_filename  파일명
    */      
    public void upload(String p_filename) throws Exception {
        try {
            this.upload(defaultServerDir, defaultClientDir, p_filename);
        }
        catch(Exception ex) { 
            ex.printStackTrace();
            Log.sys.println(this, ex, "Happen to upload(" + p_filename + "\")");     
            throw new Exception(ex.getMessage());
        }
    }    

    /**
    *Ftp 서버에서 해당 파일 삭제한다.
    @param p_directory  파일 경로
    @param p_filename  파일명
    */     
    public void delete(String p_directory, String p_filename) throws Exception {
        try{            
            if(p_filename.equals("")) return;
                        
            ftp.delete(p_directory + File.separator + p_filename);      //  Ftp 서버에서 해당 파일 삭제		

        }catch(Exception ex) { 
            ex.printStackTrace();
            Log.sys.println(this, ex, "Happen to delete(" + p_directory + ",\"" + p_filename + "\")");     
            throw new Exception(ex.getMessage());
        }
    }
 
     /**
    Ftp 서버에서 해당 파일 삭제한다.(default 경로)
    @param p_filename  파일명
    */    
    public void delete(String p_filename) throws Exception {
        try{         
            this.delete(defaultServerDir, p_filename);
        }
        catch(Exception ex) { 
            ex.printStackTrace();
            Log.sys.println(this, ex, "Happen to delete(" + p_filename + "\")");     
            throw new Exception(ex.getMessage());
        }
    }
}
