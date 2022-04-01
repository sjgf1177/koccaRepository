//**********************************************************
//  1. ��      ��: EducationalLanguage Operation Bean
//  2. ���α׷���: EducationalLanguageBean.java
//  3. ��      ��: Educational Language������ ���õ� Bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ڹ̺� 2004. 11.29
//  7. ��      ��: �ڹ̺� 2004. 11.29
//**********************************************************

package com.credu.contents;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.contents.*;
import com.credu.common.*;

public class EducationalLanguageBean {

    public EducationalLanguageBean() {}

   /**
    get EducationalLanguageData by oid
    @param metadata_idx int
    @param box          receive from the form object and session
    @return ArrayList
    */  
    public ArrayList selectEducationalLanguageData(int metadata_idx, RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = new ArrayList();
        String sql = "";
		EducationalLanguageData data = null;
		
        try {
            connMgr = new DBConnectionManager();

            sql = "select EDUCATIONAL_LANGUAGE_IDX, LANGUAGE, METADATA_IDX "
                + " from TZ_EDU_LAN "
            	+ " where metadata_idx = " + metadata_idx;
//System.out.println("========>sql : " + sql);                    
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
            	data = new EducationalLanguageData();
                data.setEducational_language_idx		(ls.getInt("educational_language_idx"));
                data.setLanguage						(ls.getString("language"));
                data.setMetadata_idx					(ls.getInt("metadata_idx"));
                
                list.add(data);
            }
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }
}