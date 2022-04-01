
package com.credu.library;

import java.io.BufferedReader;
import java.io.FileReader;

  /**
 * <p>����: �����ϰ��� ���̺귯��</p>
 * <p>����: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author ������
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
    *�����Ͽ��� �������ø��� ���� ������ HTML Code �� �о�´�
    @param p_fileName  HTML ���ϸ�
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
    *�����Ͽ��� �������ø��� ���� ������ HTML Code �� �о�´�
    @param p_fileName  HTML ���ϸ�
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
		
    /* �����ϰ��� html�� �о�´�.
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
        //return StringManager.korEncode(contents.toString());         //      Weblogic ������ ����Ǵ� ����
        return contents.toString();         //      Resin ���� ����Ǵ� ����
    }
	
    /* �����ϰ��� html�� �о�´�.
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
        //return StringManager.korEncode(contents.toString());         //      Weblogic ������ ����Ǵ� ����
        return contents.toString();         //      Resin ���� ����Ǵ� ����
    }
		
    /**
    *�����Ͽ��� �������ø��� ���� ������ HTML Code ���� ����ó���Ⱥκ��� ���� ������ ġȯ�Ѵ�.
    @param name  ������
    @param value  ������ ������ ��
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
    *�����Ͽ��� ������ ���� ��µ� HTML Code �� String ���� �����Ѵ�.
    @return String  HTML Code
    */    
    public String getNewMailContent() throws Exception {
        isFirst = true;
        return this.newMailContent;
    }
}
