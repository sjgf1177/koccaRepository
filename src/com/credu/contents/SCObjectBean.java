//**********************************************************
//  1. ��      ��: Object OPERATION BEAN
//  2. ���α׷���: SCObjectBean.java
//  3. ��      ��:
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 0.1
//  6. ��      ��: S.W.Kang 2004. 12. 03
//  7. ��      ��: S.W.Kang 2004. 12. 03
//**********************************************************
package com.credu.contents;

import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.contents.*;
import com.credu.common.*;

import java.io.*;

public class SCObjectBean {

    private static final String FILE_TYPE = "p_file";           //      ���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 1;                    //    �������� ���õ� ����÷�� ����
    
    public SCObjectBean() {}

    /**
    Object����Ʈ ��ȸ
    @param box          receive from the form object and session
    @return ArrayList   Object����Ʈ
    @���� : Object�������� ȣ���Ҷ��� OTYPE�� OBC�ΰ͸��� SELECT�ϰ� Sco�������� ȣ���Ҷ��� OTYPE�� SCO�ΰ͸��� SELECT�Ѵ�.
    */      
    public ArrayList SelectObjectList(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null,ls2=null;
        ArrayList list1 = null, list2=null;
        String sql  = "", sql2="";
        SCObjectData data = null;
        
        String s_subj = box.getString("s_subj");
        
        String s_gubun = box.getString("s_gubun");
        if(s_gubun==null)   s_gubun="";
        
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "select distinct a.oid , a.otype, a.filetype, a.npage, a.sdesc,a.sdesc2,a.master "
                + " , get_name(a.master) mastername "
                + " ,a.starting,a.server,a.subj,a.parameterstring,a.datafromlms "
                + " ,a.identifier,a.prerequisites,a.masteryscore,a.maxtimeallowed "
                + " ,a.timelimitaction,a.sequence,a.thelevel,a.luserid,a.ldate ";

            if (s_subj.equals("ALL")){
                sql = sql + " from tz_object a  where  ";
            }else if(s_subj.equals("NO-SUBJ")){
                sql = sql + " from tz_object a"
                    + " where a.otype='OBC' and a.oid not in (select distinct oid from tz_subjobj) and ";
            }else{
                sql = sql + " from tz_object a, tz_subjobj b "
                    + " where a.otype='OBC' and a.oid=b.oid and b.subj="+StringManager.makeSQL(s_subj)+" and ";
            }

            sql += " upper(a.sdesc) like '%"+s_gubun.toUpperCase()+"%'  order by a.oid desc";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data=new SCObjectData(); 
                data.setOid             (ls.getString("oid"));
                data.setOtype           (ls.getString("otype"));
                data.setFiletype        (ls.getString("filetype"));
                data.setNpage           (ls.getInt("npage"));
                data.setSdesc           (ls.getString("sdesc"));
                data.setSdesc2           (ls.getString("sdesc2"));
                data.setMaster          (ls.getString("master"));
                data.setMastername      (ls.getString("mastername"));
                data.setStarting        (ls.getString("starting"));
                data.setServer          (ls.getString("server"));
                data.setSubj            (ls.getString("subj"));
                data.setParameterstring (ls.getString("parameterstring"));
                data.setdatafromlms     (ls.getString("datafromlms"));
                data.setIdentifier      (ls.getString("identifier"));
                data.setPrerequisites   (ls.getString("prerequisites"));
                data.setMasteryscore    (ls.getInt("masteryscore"));
                data.setMaxtimeallowed  (ls.getString("maxtimeallowed"));
                data.setTimelimitaction (ls.getString("timelimitaction"));
                data.setSequence        (ls.getInt("sequence"));
                data.setThelevel        (ls.getInt("thelevel"));
                data.setLuserid         (ls.getString("luserid"));
                data.setLdate           (ls.getString("ldate"));

                // Object�� ���� ��������Ʈ ����
                if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
                sql = "select distinct a.subj, b.subjnm from tz_subjobj a, tz_subj b "
                    + " where a.subj=b.subj and a.oid="+StringManager.makeSQL(data.getOid());
                ls2 = connMgr.executeQuery(sql);
//System.out.println(sql);
                while(ls2.next()){
                    data.makeSub(ls2.getString("subj"),ls2.getString("subjnm"));                                
                }
                //���� ��������..
                data.setCntUsed(ls2.getTotalCount());

//System.out.println(data.getOid()+":"+data.getLdate());                
                list1.add(data);    
            }
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }
    /**
    Object���� ��ȸ
    @param box          receive from the form object and session
    @return SCObjectData   Object ����
    */  
    public SCObjectData SelectObjectData(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null,ls2=null;
        ArrayList list1 = null, list2=null;
        String sql  = "", sql2="";
        SCObjectData data = null;
        
        String p_oid = box.getString("p_oid");
        String p_process = box.getString("p_process");
        
        
        try {
            connMgr = new DBConnectionManager();

            sql = "select oid , otype, filetype, npage, sdesc, sdesc2, master "
                + " ,starting,server,subj,parameterstring,datafromlms "
                + " ,identifier,prerequisites,masteryscore,maxtimeallowed "
                + " ,timelimitaction,sequence,thelevel,luserid,ldate "
                + "  from tz_object where oid="+StringManager.makeSQL(p_oid);
                    
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data=new SCObjectData(); 
                data.setOid             (ls.getString("oid"));
                data.setOtype           (ls.getString("otype"));
                data.setFiletype        (ls.getString("filetype"));
                data.setNpage           (ls.getInt("npage"));
                data.setSdesc           (ls.getString("sdesc"));
                data.setSdesc2          (ls.getString("sdesc2"));
                data.setMaster          (ls.getString("master"));
                data.setStarting        (ls.getString("starting"));
                data.setServer          (ls.getString("server"));
                data.setSubj            (ls.getString("subj"));
                data.setParameterstring (ls.getString("parameterstring"));
                data.setdatafromlms     (ls.getString("datafromlms"));
                data.setIdentifier      (ls.getString("identifier"));
                data.setPrerequisites   (ls.getString("prerequisites"));
                data.setMasteryscore    (ls.getInt("masteryscore"));
                data.setMaxtimeallowed  (ls.getString("maxtimeallowed"));
                data.setTimelimitaction (ls.getString("timelimitaction"));
                data.setSequence        (ls.getInt("sequence"));
                data.setThelevel        (ls.getInt("thelevel"));
                data.setLuserid         (ls.getString("luserid"));
                data.setLdate           (ls.getString("ldate"));
                
                // Object�� ���� ��������Ʈ ����
                if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
                sql = "select distinct a.subj, b.subjnm from tz_subjobj a, tz_subj b "
                    + " where a.subj=b.subj and a.oid="+StringManager.makeSQL(p_oid);
                ls2 = connMgr.executeQuery(sql);
                while(ls2.next()){
                    data.makeSub(ls2.getString("subj"),ls2.getString("subjnm"));                                
                }
                //���� ��������..
                data.setCntUsed(ls2.getTotalCount());
                
                // Master �̸�
                if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
                sql = "select name from tz_member "
                    + " where userid="+StringManager.makeSQL(data.getMaster());
                ls2 = connMgr.executeQuery(sql);
                if(ls2.next()){
                    data.setMastername(ls2.getString("name"));                              
                }
            }
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return data;
    }

    /**
    Object���
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail    
    **/
     public String InsertObject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;        
        String sql = "";
        int isOk = 0, j=0;   
        
        String  v_luserid   = box.getSession("userid");
        String  v_oid       = "";
        String  results     ="";

        try {
            connMgr = new DBConnectionManager();
            
            // get Object-ID
            sql = "select ltrim(to_char( to_number(isnull(max(oid),'1000000000'))+1, '0000000000'  )) oid from tz_object where otype='OBC'";
            //System.out.println(sql);
            
            try{
                ls = connMgr.executeQuery(sql);
                ls.next();
                v_oid = ls.getString("oid");
            }catch(Exception se){
                v_oid = "1000000001";
            }
            
            //FILE MOVE & UNZIP
            results = this.controlObjectFile("insert",v_oid, box);

            if(results.equals("OK")){                       
            
                // insert Object-Information into DB Table 'TZ_OBJECT' 
                if(ls != null) { try { ls.close(); }catch (Exception e) {} }
                sql = "insert into tz_object "
                + "(oid , otype, filetype, npage, sdesc,sdesc2,master "
                + " ,starting,server,subj,parameterstring,datafromlms "
                + " ,identifier,prerequisites,masteryscore,maxtimeallowed "
                + " ,timelimitaction,sequence,thelevel,luserid,ldate)"     
                + " values "
                + "("+ StringManager.makeSQL(v_oid)
                + ","+ StringManager.makeSQL("OBC")
                + ","+ StringManager.makeSQL("HTML")
                + ","+ box.getInt("p_npage")
                + ","+ StringManager.makeSQL(box.getString("p_sdesc"))
                + ","+ StringManager.makeSQL(box.getString("p_sdesc2"))
                + ","+ StringManager.makeSQL(box.getString("p_master"))
                //+ ","+ StringManager.makeSQL("http://"+GetCodenm.get_config("eduDomain")+GetCodenm.get_config("object_locate")+v_oid+"/1001.html")
                + ","+ StringManager.makeSQL(GetCodenm.get_config("object_locate")+v_oid+"/1001.html")
                + ","+ StringManager.makeSQL(box.getString("p_server"))
                + ","+ StringManager.makeSQL(box.getString("p_subj")) 
                + ","+ StringManager.makeSQL(box.getString("p_parameterstring"))
                + ","+ StringManager.makeSQL(box.getString("p_datafromlms"))
                + ","+ StringManager.makeSQL(box.getString("p_identifier"))
                + ","+ StringManager.makeSQL(box.getString("p_prerequisites"))  
                + ","+ box.getInt("p_masteryscore")
                + ","+ StringManager.makeSQL(box.getString("p_maxtimeallowed")) 
                + ","+ StringManager.makeSQL(box.getString("p_timelimitaction"))
                + ","+ box.getInt("p_sequence")
                + ","+ box.getInt("p_thelevel")
                + ","+ StringManager.makeSQL("v_luserid")        
                + ", to_char(sysdate,'YYYYMMDDHH24MISS') "
                + ")";                                
                isOk = connMgr.executeUpdate(sql);
            }
            
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("results = " + results + "\r\n" +"sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (isOk > 0) {connMgr.commit();}
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return results;
    }   
 
 
    /**
    Object����
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail    
    **/
     public String UpdateObject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;        

        String sql = "";
        int isOk = 0, j=0;   
        
        String results = "";
        String v_luserid    = box.getSession("userid");
        String v_oid        = box.getString("p_oid");

        try{
            //FILE MOVE & UNZIP
            results = this.controlObjectFile("update",v_oid, box);
        } catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("results = " + results + "\r\n" + ex.getMessage());
        }
            
        try {
            connMgr = new DBConnectionManager();                             
            //if(results.equals("OK")){                     
            
                // update Object-Information 'TZ_OBJECT' 
System.out.println("HEYYYYY"+v_oid);
                sql = "update tz_object set "
                    //+ "        otype              = "+ StringManager.makeSQL(box.getString("p_otype"))
                    //+ "       ,filetype           = "+ StringManager.makeSQL(box.getString("p_filetype"))
                    + "     npage           = "+ box.getInt("p_npage")
                    + "     ,sdesc              = "+ StringManager.makeSQL(box.getString("p_sdesc"))
                    + "     ,sdesc2              = "+ StringManager.makeSQL(box.getString("p_sdesc2"))
                    + "     ,master             = "+ StringManager.makeSQL(box.getString("p_master"))
                    //+ "       ,starting           = "+ StringManager.makeSQL("http://"+GetCodenm.get_config("eduDomain")+GetCodenm.get_config("object_locate")+v_oid+"/1001.html")
                    + "     ,server             = "+ StringManager.makeSQL(box.getString("p_server"))
                    + "     ,subj               = "+ StringManager.makeSQL(box.getString("p_subj")) 
                    + "     ,parameterstring    = "+ StringManager.makeSQL(box.getString("p_parameterstring"))
                    + "     ,datafromlms        = "+ StringManager.makeSQL(box.getString("p_datafromlms"))
                    + "     ,identifier         = "+ StringManager.makeSQL(box.getString("p_identifier"))
                    + "     ,prerequisites      = "+ StringManager.makeSQL(box.getString("p_prerequisites"))  
                    + "     ,masteryscore       = "+ box.getInt("p_masteryscore")
                    + "     ,maxtimeallowed     = "+ StringManager.makeSQL(box.getString("p_maxtimeallowed")) 
                    + "     ,timelimitaction    = "+ StringManager.makeSQL(box.getString("p_timelimitaction"))
                    + "     ,sequence           = "+ box.getInt("p_sequence")
                    + "     ,thelevel           = "+ box.getInt("p_thelevel")
                    + "     ,luserid            = "+ StringManager.makeSQL(v_luserid)        
                    + "     ,ldate              = to_char(sysdate,'YYYYMMDDHH24MISS') "
                    + " where oid="+ StringManager.makeSQL(v_oid);
System.out.println(sql);
                isOk = connMgr.executeUpdate(sql);
            //}
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            connMgr.setAutoCommit(true); 
            if (isOk > 0) {connMgr.commit();}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return results;
    }   
    /**
    Object ����
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail    
    **/
     public int DeleteObject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;        

        PreparedStatement pstmt = null;        
        String sql = "";
        int isOk = 0, j=0;   
        
        String p_subj       = box.getString("p_subj");
        String p_mftype     = box.getString("p_mftype");
        int    p_width      = box.getInt("p_width");
        int    p_height     = box.getInt("p_height");
        String p_ismfbranch = box.getString("p_ismfbranch");
        String p_iscentered = box.getString("p_iscentered");
        String p_menus      = box.getString("p_menus");
        
        //String v_luserid      = box.getSession("userid");
        String v_luserid      = "icarus";

        try {
            connMgr = new DBConnectionManager();                             
            connMgr.setAutoCommit(false);  
                        
            //insert TZ_Grseq table
            sql = "update TZ_subj set "
                + " mftype=?, width=?, height=?, ismfbranch=?, iscentered=?,"
                + " Luserid     =?, LDATE      =to_char(sysdate,'YYYYMMDDHH24MISS') "
                + " where subj=?";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, box.getString("p_mftype"));
            pstmt.setInt   (2, box.getInt   ("p_width"));    
            pstmt.setInt   (3, box.getInt   ("p_height"));   
            pstmt.setString(4, box.getString("p_ismfbranch"));    
            pstmt.setString(5, box.getString("p_iscentered"));   
            pstmt.setString(6, v_luserid);   
            pstmt.setString(7, p_subj);
            isOk = pstmt.executeUpdate();
            connMgr.commit();
            
            if(isOk==1){
                if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
                sql = "delete from tz_mfsubj where subj=?";
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, p_subj);
                isOk = pstmt.executeUpdate();
                
                if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
                String  v_menu = "";
                sql = "insert into tz_mfsubj (subj, menu, menunm, "
                    + " pgm, pgmtype, pgram1, pgram2, pgram3, pgram4, pgram5, orders, luserid, ldate) "
                    + " select ?,menu,menunm,pgm,pgmtype,pgram1,pgram2,pgram3,pgram4,pgram5,?,"
                    + "        ?, to_char(sysdate,'YYYYMMDDHH24MISS') "
                    + "   from tz_mfmenu where menu=?";  
                pstmt = connMgr.prepareStatement(sql);
                
                for(int i=0;i<(p_menus.length()/2);i++){
                    j++;
                    v_menu = p_menus.substring(i*2,i*2+2);
                    pstmt.setString(1, p_subj);
                    pstmt.setInt   (2, j);
                    pstmt.setString(3, v_luserid);
                    pstmt.setString(4, v_menu);
                    isOk = pstmt.executeUpdate();
                }
                
            }if (isOk > 0) {connMgr.commit();}
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            connMgr.setAutoCommit(true); 
            
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }   
            
    /**
    OBC �������� List�����
    @param String   
    @return String 
    */
    public static String makeObcSubjSelect(String p_selsubj, String p_onchange) throws Exception{
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql  = "";
        String results = "<select name=\"s_subj\" ";
        if (!p_onchange.equals("")) results += " onChange=\""+p_onchange+"\" ";
        results += ">";
        
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "select subj,subjnm from tz_subj where isonoff='ON' and contenttype='O' order by subjnm ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                results +="<option value='"+ls.getString("subj")+"' ";
                if (p_selsubj.equals(ls.getString("subj"))) results +=" selected";
                results +=" >"+ls.getString("subjnm")+"</option>";
            }
        }            
        catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        results +="</select>";
        
        return  results;
    }
    
    /**
    Object ZIP ���Ϸ� Directory ����
    @param String   p_job   ���/��������(insert/update)
                    p_oid   Object ID
                    box     RequestBox
    @return String resuts   ����޼���
    */    
    public String   controlObjectFile(String p_job, String p_oid, RequestBox box) throws Exception{
        String  results="OK";
        String sql = "";   
        int isOk = 1;
        
        String  v_realPath="";
        String  v_tempPath="";
        boolean insert_success = false;
        boolean move_success = false;
        boolean update_success = false;
        boolean extract_success = false;
        boolean allDelete_success = false;

/*
        //----------------------   ���ε�Ǵ� ������ ������ �˰� �ڵ��ؾ��Ѵ�  --------------------------------
        String v_realFileName = box.getRealFileName("p_file");      //�� ���ϸ�
        String v_newFileName  = box.getNewFileName("p_file");       //���� upload�� ���ϸ�
        //----------------------------------------------------------------------------------------------------------------------------
*/
        //---------------   ���ε�Ǵ� ������ ������ �˰� �ڵ��ؾ��Ѵ�  --------------------

        String [] v_realFileName = new String [FILE_LIMIT];
        String [] v_newFileName = new String [FILE_LIMIT];
        String v_file1 ="";

        for(int i = 0; i < FILE_LIMIT; i++) {
            v_realFileName [i] = box.getRealFileName(FILE_TYPE + (i+1));
            v_newFileName [i] = box.getNewFileName(FILE_TYPE + (i+1));
        }

        if (!v_newFileName[0].equals("")) {
            v_file1=   v_newFileName[0];
        }

        //÷�������� ���� ��쿡�� ����.
        if(!v_file1.equals("")){
            // Object ��������
            ConfigSet conf = new ConfigSet();
            //v_realPath = GetCodenm.get_config("object_locate") + p_oid;       //���� Un-zip�� Dir        
            v_realPath = conf.getProperty("dir.objectupload") + p_oid;      //���� Un-zip�� Dir
            v_tempPath = conf.getProperty("dir.upload.object");                    //upload�� ���� ��ġ
//System.out.println("v_realPath1 : " + v_realPath);    
            results = p_job;
            results+= "\\n\\n 0. v_realPath="+v_realPath;
            results+= "\\n\\n 0. v_tempPath="+v_tempPath;
            
            try {
                if(p_job.equals("insert")){                 // Object ��Ͻ� 
                    // 1. ���� ����
                    File newDir = new File(v_realPath);
                    System.out.println("canWrite : " + newDir.canWrite());
                    boolean b = newDir.mkdir();System.out.println("b : " + b);    
                    allDelete_success=true; 
                    results+= "\\n\\n 1. makeDirecotry OK. ";               
                }else if(p_job.equals("update")){           // Object ������
                    //1. ������ ���Ϲ� ���� ��� ����
                    FileDelete fd = new FileDelete();
                    allDelete_success = fd.allDelete(v_realPath);
                    results+= "\\n\\n 1. allDelete =  "+(new Boolean(allDelete_success).toString());
                    // 1. ���� ����
                    File newDir = new File(v_realPath);
                    newDir.mkdirs();
                    allDelete_success=true;
                    results+= "\\n\\n 1. makeDirecotry OK. ";
                }   
                
                // 2. ���� �̵�
                if(allDelete_success) {
                    FileMove fc = new FileMove();System.out.println("v_realPath2 : " + v_realPath);    
                    System.out.println("v_tempPath : " + v_tempPath);    
                    move_success = fc.move(v_realPath, v_tempPath, v_file1);
                    System.out.println("move_success="+move_success);                    
                }
                results+= "\\n\\n 2. move to ["+v_realPath+"] =  "+(new Boolean(move_success).toString());
                
                // 3. ���� Ǯ��
                if(move_success){
                    FileUnzip unzip = new FileUnzip();
                    extract_success = unzip.extract(v_realPath, v_file1);
                    results+= "\\n\\n 3. unzip to ["+v_realPath+"] =  "+(new Boolean(extract_success).toString());              
                    if(!extract_success){
                        FileDelete fd = new FileDelete();
                        fd.allDelete(v_realPath);
                    }
                }
                results+= "\\n\\n END of controlObjectFile() ";
                results = "OK";
            }
            catch (Exception ex) {
                FileDelete fd = new FileDelete();
                fd.allDelete(v_realPath);
                throw new Exception("ERROR results=" + results + "\r\n" + ex.getMessage());
            }
            finally {
                FileManager.deleteFile(v_realPath+"\\"+v_file1);
            }
        }
        
        return results;
    }
        
}
