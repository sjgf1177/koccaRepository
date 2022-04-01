//**********************************************************
//  1. 제      목: LifecycleContribute Operation Bean
//  2. 프로그램명: LifecycleContributeBean.java
//  3. 개      요: LifecycleContribute관리에 관련된 Bean
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

public class LifecycleContributeBean {

    public LifecycleContributeBean() {}

   /**
    get LifecycleContributeData by oid
    @param metadata_idx int
    @param box          receive from the form object and session
    @return ArrayList
    */  
    public ArrayList selectLifecycleContributeData(int metadata_idx, RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = new ArrayList();
        String sql = "";
		LifecycleContributeData data = null;
		
        try {
            connMgr = new DBConnectionManager();

            sql = "select LIFECYCLE_CONTRIBUTE_IDX, LIFECYCLE_CONTR_ROLE, LIFECYCLE_CONTR_DATE, METADATA_IDX "
                + " from TZ_LIF_CON "
            	+ " where metadata_idx = " + metadata_idx;
//System.out.println("========>sql : " + sql);                    
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
            	data = new LifecycleContributeData();
                data.setLifecycle_contribute_idx			(ls.getInt("lifecycle_contribute_idx"));
                data.setLifecycle_contr_role				(ls.getString("lifecycle_contr_role"));
                data.setLifecycle_contr_date				(ls.getString("lifecycle_contr_date"));
                data.setMetadata_idx						(ls.getInt("metadata_idx"));
                
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