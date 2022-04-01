//**********************************************************
//  1. 제      목: GeneralLanguage Operation Bean
//  2. 프로그램명: GeneralLanguageBean.java
//  3. 개      요: GeneralLanguage관리에 관련된 Bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.22
//  7. 수      정: 박미복 2004. 11.22
//**********************************************************

package com.credu.contents;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.contents.*;
import com.credu.common.*;

public class GeneralLanguageBean {

    public GeneralLanguageBean() {}

   /**
    get GeneralLanguageData by oid
    @param metadata_idx int
    @param box          receive from the form object and session
    @return ArrayList
    */  
    public ArrayList selectGeneralLanguageData(int metadata_idx, RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = new ArrayList();
        String sql = "";
		GeneralLanguageData data = null;
		
        try {
            connMgr = new DBConnectionManager();

            sql = "select GENERAL_LANGUAGE_IDX, LANGUAGE, METADATA_IDX "
                + " from TZ_GEN_LAN "
            	+ " where metadata_idx = " + metadata_idx;
//System.out.println("========>sql : " + sql);                    
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
            	data = new GeneralLanguageData();
                data.setGeneral_language_idx		(ls.getInt("general_language_idx"));
                data.setLanguage					(ls.getString("language"));
                data.setMetadata_idx				(ls.getInt("metadata_idx"));
                
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