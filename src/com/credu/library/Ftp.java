
package com.credu.library;

import com.enterprisedt.net.ftp.*;
import java.io.*;

  /**
 * <p>����: Ftp ���� ���̺귯��</p>
 * <p>����: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author ������
 *@date 2003. 12
 *@version 1.0
 */
public class Ftp {
    private FTPClient ftp;
    private String defaultServerDir;
    private String defaultClientDir;

    /**
    *Ftp ������ ������ IP, ID, PW ���� cresys.properties ���� �о ������ �����Ѵ�.
    @param p_fileName  HTML ���ϸ�
    */    
    public Ftp() throws Exception { 
        try{
            ConfigSet conf = new ConfigSet();  
            ftp = new FTPClient(conf.getProperty("ftp.server.ip"));        //   Ftp ������ ����
            ftp.login(conf.getProperty("ftp.server.id"), conf.getProperty("ftp.server.pw"));        
            ftp.setType(FTPTransferType.BINARY);        //       binary type ���� ����
            
            defaultServerDir = conf.getProperty("ftp.server.dir");
            defaultClientDir = conf.getProperty("ftp.client.dir");
        }catch(Exception ex) {
            ex.printStackTrace();
            Log.sys.println(this, ex, "Happen to Ftp()");     
            throw new Exception(ex.getMessage());
        }               
    }

    /**
    *Ftp �������� ������ �ٿ�ε��Ѵ�.
    @param p_serverDir  ���� ���ϰ��
    @param p_clientDir  Ŭ���̾�Ʈ ���ϰ��
    @param p_filename  ���ϸ�
    */    
    public int download(String p_serverDir, String p_clientDir, String p_filename) throws Exception {
        try{
            if(p_filename.equals("")) return -2;
            
         /*   FTPFile [] ftpFile = ftp.dirDetails(p_serverDir);
            
            for(int i = 0; i < ftpFile.length; i++) {
                if(!ftpFile [i].equals(p_filename)) return -1;
            }*/
        
            byte [] buf = ftp.get(p_serverDir + File.separator + p_filename);       //      Ftp �������� ������ ������ �غ� �Ѵ�
            
            File downFile = new File(p_clientDir, p_filename);    //   Ftp Ŭ���̾�Ʈ���� �ٿ�ε� ���ϰ�ü ����

            FileOutputStream fos = new FileOutputStream(downFile);     
            BufferedOutputStream bos = new BufferedOutputStream(fos);	

            bos.write(buf);   //  Ftp Ŭ���̾�Ʈ�� �ٿ�ε�		
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
    *Ftp �������� ������ �ٿ�ε��Ѵ�.(default ���)
    @param p_filename  ���ϸ�
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
    *Ftp ������ ������ ���ε��Ѵ�.
    @param p_serverDir  ���� ���ϰ��
    @param p_clientDir  Ŭ���̾�Ʈ ���ϰ��
    @param p_filename  ���ϸ�
    */            
    public void upload(String p_serverDir, String p_clientDir, String p_filename) throws Exception {
        
        try{
            if(p_filename.equals("")) return;
            
            File upFile = new File(p_clientDir, p_filename);    //   Ftp Ŭ���̾�Ʈ�� ���ε��� ���ϰ�ü ����
//            System.out.println(upFile);
            FileInputStream fis = new FileInputStream(upFile); 
            BufferedInputStream bis = new BufferedInputStream(fis);	
            
            ftp.put(bis, p_serverDir + File.separator + p_filename, true);      //  Ftp ������ ���ε�		
            
            fis.close();
            bis.close();
        }catch(Exception ex) { 
            ex.printStackTrace();
            Log.sys.println(this, ex, "Happen to upload(" + p_serverDir + ",\"" + p_clientDir + ",\"" + p_filename + "\")");     
            throw new Exception(ex.getMessage());
        }
    }

    /**
    *Ftp ������ ������ ���ε��Ѵ�.(default ���)
    @param p_filename  ���ϸ�
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
    *Ftp �������� �ش� ���� �����Ѵ�.
    @param p_directory  ���� ���
    @param p_filename  ���ϸ�
    */     
    public void delete(String p_directory, String p_filename) throws Exception {
        try{            
            if(p_filename.equals("")) return;
                        
            ftp.delete(p_directory + File.separator + p_filename);      //  Ftp �������� �ش� ���� ����		

        }catch(Exception ex) { 
            ex.printStackTrace();
            Log.sys.println(this, ex, "Happen to delete(" + p_directory + ",\"" + p_filename + "\")");     
            throw new Exception(ex.getMessage());
        }
    }
 
     /**
    Ftp �������� �ش� ���� �����Ѵ�.(default ���)
    @param p_filename  ���ϸ�
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
