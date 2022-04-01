//**********************************************************
//  1. ��      ��: GeneralKeyword Operation Bean
//  2. ���α׷���: GeneralKeywordBean.java
//  3. ��      ��: GeneralKeyword������ ���õ� Bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ڹ̺� 2004. 11.22
//  7. ��      ��: �ڹ̺� 2004. 11.22
//**********************************************************

package com.credu.contents;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.contents.*;
import com.credu.common.*;

public class GeneralKeywordBean {

    public GeneralKeywordBean() {}

   /**
    get GeneralKeywordData by oid
    @param metadata_idx int
    @param box          receive from the form object and session
    @return ArrayList
    */  
    public ArrayList selectGeneralKeywordData(int metadata_idx, RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = new ArrayList();
        String sql = "";
		GeneralKeywordData data = null;
		
        try {
            connMgr = new DBConnectionManager();

            sql = "select GENERAL_KEYWORD_IDX, KEYWORD, METADATA_IDX "
                + " from TZ_GEN_KEY "
            	+ " where metadata_idx = " + metadata_idx;
//System.out.println("========>sql : " + sql);                    
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
            	data = new GeneralKeywordData();
                data.setGeneral_keyword_idx			(ls.getInt("general_keyword_idx"));
                data.setKeyword						(ls.getString("keyword"));
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