//**********************************************************
//  1. ��      ��: MetaMetaMetaDataScheme Operation Bean
//  2. ���α׷���: MetaMetaMetaDataSchemeBean.java
//  3. ��      ��: MetaMetaMetaDataScheme������ ���õ� Bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ڹ̺� 2004. 11.25
//  7. ��      ��: �ڹ̺� 2004. 11.25
//**********************************************************

package com.credu.contents;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.contents.*;
import com.credu.common.*;

public class MetaMetaMetaDataSchemeBean {

    public MetaMetaMetaDataSchemeBean() {}

   /**
    get MetaMetaMetaDataSchemeData by oid
    @param metadata_idx int
    @param box          receive from the form object and session
    @return ArrayList
    */  
    public ArrayList selectMetaMetaMetaDataSchemeData(int metadata_idx, RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = new ArrayList();
        String sql = "";
		MetaMetaMetaDataSchemeData data = null;
		
        try {
            connMgr = new DBConnectionManager();

            sql = "select METADATA_SCHEME_IDX, METADATA_SCHEME, METADATA_IDX "
                + " from TZ_MET_SCH "
            	+ " where metadata_idx = " + metadata_idx;
//System.out.println("========>sql : " + sql);                    
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
            	data = new MetaMetaMetaDataSchemeData();
                data.setMetadata_scheme_idx			(ls.getInt("metadata_scheme_idx"));
                data.setMetadata_scheme				(ls.getString("metadata_scheme"));
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