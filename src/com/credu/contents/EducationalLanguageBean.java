//**********************************************************
//  1. 제      목: EducationalLanguage Operation Bean
//  2. 프로그램명: EducationalLanguageBean.java
//  3. 개      요: Educational Language관리에 관련된 Bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.29
//  7. 수      정: 박미복 2004. 11.29
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