//**********************************************************
//  1. 제      목: MetaMetaContribute Operation Bean
//  2. 프로그램명: MetaMetaContributeBean.java
//  3. 개      요: MetaMetaContribute관리에 관련된 Bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.25
//  7. 수      정: 박미복 2004. 11.25
//**********************************************************

package com.credu.contents;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.contents.*;
import com.credu.common.*;

public class MetaMetaContributeBean {

    public MetaMetaContributeBean() {}

   /**
    get MetaMetaContributeData by oid
    @param metadata_idx int
    @param box          receive from the form object and session
    @return ArrayList
    */  
    public ArrayList selectMetaMetaContributeData(int metadata_idx, RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = new ArrayList();
        String sql = "";
		MetaMetaContributeData data = null;
		
        try {
            connMgr = new DBConnectionManager();

            sql = "select METAMETA_CONTRIBUTE_IDX, META_CONTR_ROLE, META_CONTR_DATE, METADATA_IDX "
                + " from TZ_MET_CON "
            	+ " where metadata_idx = " + metadata_idx;
//System.out.println("========>sql : " + sql);                    
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
            	data = new MetaMetaContributeData();
                data.setMetameta_contribute_idx			(ls.getInt("metameta_contribute_idx"));
                data.setMeta_contr_role					(ls.getString("meta_contr_role"));
                data.setMeta_contr_date					(ls.getString("meta_contr_date"));
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