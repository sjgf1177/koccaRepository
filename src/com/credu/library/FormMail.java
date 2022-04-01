
package com.credu.library;

import java.io.BufferedReader;
import java.io.FileReader;

  /**
 * <p>제목: 폼메일관련 라이브러리</p>
 * <p>설명: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author 이정한
 *@date 2003. 12
 *@version 1.0
 */
public class FormMail {    
    private ConfigSet conf;
    private String originalMailContent;
    private String newMailContent;
    private boolean isFirst;
    
    public FormMail() {
    }
    
    /**
    *폼메일에서 메일템플릿에 따른 원래의 HTML Code 를 읽어온다
    @param p_fileName  HTML 파일명
    */
    public FormMail(String p_filename) {
        try{
            conf = new ConfigSet();
            originalMailContent = this.getOriginalMailContent(p_filename);
            isFirst = true;
        }
        catch(Exception ex) {
            ex.printStackTrace();
            Log.sys.println(this, ex, "Happen to FormMail(\"" + p_filename + "\")");     
        }
    }
    
    /**
    *폼메일에서 메일템플릿에 따른 원래의 HTML Code 를 읽어온다
    @param p_fileName  HTML 파일명
    */
    public FormMail(String p_filename, String p_grtype) {
        try{
            conf = new ConfigSet();
            originalMailContent = this.getOriginalMailContent(p_filename, p_grtype);
            isFirst = true;
        }
        catch(Exception ex) {
            ex.printStackTrace();
            Log.sys.println(this, ex, "Happen to FormMail(\"" + p_filename + "\")");     
        }
    }
		
    /* 폼메일관련 html을 읽어온다.
    */
    public String getOriginalMailContent(String p_filename) throws Exception {
        String str = "";
        StringBuffer contents = new StringBuffer();
        BufferedReader br = null;
        
        if (p_filename.equals("")) return "";
                
        try {
            br = new BufferedReader(new FileReader(conf.getProperty("mail.dir.html") + p_filename));

            System.out.println(conf.getProperty("mail.dir.html") + p_filename);

            while ((str = br.readLine()) != null) {
                contents.append(str + " \r\n");
            }
        }
        catch (Exception ex) {
            Log.sys.println(this, ex, "Happen to FormMail.getOriginalMailContent(\"" + p_filename + "\")");     
            throw new Exception("FormMail.getOriginalMailContent(\""+p_filename+"\")\r\n"+ex.getMessage());
        }
        finally {
            if ( br!= null ) try { br.close(); } catch(Exception e1) {}
        }
        //return StringManager.korEncode(contents.toString());         //      Weblogic 에서만 적용되는 사항
        return contents.toString();         //      Resin 에서 적용되는 사항
    }
	
    /* 폼메일관련 html을 읽어온다.
	    */
    public String getOriginalMailContent(String p_filename, String p_grtype) throws Exception {
        String str = "";
        StringBuffer contents = new StringBuffer();
        BufferedReader br = null;
        
        if (p_filename.equals("")) return "";
                
        try {
            br = new BufferedReader(new FileReader(conf.getProperty("mail.dir.html") + p_grtype + "\\" + p_filename));

            System.out.println(conf.getProperty("mail.dir.html") + p_grtype + "\\" + p_filename);

            while ((str = br.readLine()) != null) {
                contents.append(str + " \r\n");
            }
        }
        catch (Exception ex) {
            Log.sys.println(this, ex, "Happen to FormMail.getOriginalMailContent(\"" + p_filename + "\")");     
            throw new Exception("FormMail.getOriginalMailContent(\""+p_filename+"\")\r\n"+ex.getMessage());
        }
        finally {
            if ( br!= null ) try { br.close(); } catch(Exception e1) {}
        }
        //return StringManager.korEncode(contents.toString());         //      Weblogic 에서만 적용되는 사항
        return contents.toString();         //      Resin 에서 적용되는 사항
    }
		
    /**
    *폼메일에서 메일템플릿에 따른 원래의 HTML Code 에서 변수처리된부분을 실제 값으로 치환한다.
    @param name  변수명
    @param value  변수에 들어가야할 값
    */
    public void setVariable(String name, String value) throws Exception {
//System.out.println("---------------------------------------"+ name);
//System.out.println("---"+ conf.getProperty("mail." + name));
//System.out.println("---------------------------------------");

        try {
            if(isFirst) {
                newMailContent = StringManager.replace(originalMailContent, "%" + conf.getProperty("mail." + name) + "%", value);           
            }
            else {
                newMailContent = StringManager.replace(newMailContent, "%" + conf.getProperty("mail." + name) + "%", value);
            }
            isFirst = false;
        }
        catch (Exception ex) {
            Log.sys.println(this, ex, "Happen to FormMail.setVariable(\"" + name + ", " + value + "\")");     
            throw new Exception("FormMail.setVariable(\"" + name + ", " + value + "\")\r\n"+ex.getMessage());
        }
    }

    /**
    *폼메일에서 변수에 값이 출력된 HTML Code 를 String 으로 리턴한다.
    @return String  HTML Code
    */    
    public String getNewMailContent() throws Exception {
        isFirst = true;
        return this.newMailContent;
    }
}
