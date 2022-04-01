//**********************************************************
//  1. 제      목: Relation Operation Bean
//  2. 프로그램명: RelationBean.java
//  3. 개      요: Relation관리에 관련된 Bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.30
//  7. 수      정: 박미복 2004. 11.30
//**********************************************************

package com.credu.beta;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.beta.*;
import com.credu.common.*;

public class BetaRelationBean {

    public BetaRelationBean() {}

   /**
    get RelationData by oid
    @param metadata_idx int
    @param box          receive from the form object and session
    @return ArrayList
    */  
    public ArrayList selectRelationData(int metadata_idx, RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = new ArrayList();
        String sql = "";
		BetaRelationData data = null;
		
        try {
            connMgr = new DBConnectionManager();

            sql = "select RELATION_IDX, RELATION_KIND, RELATION_RESOURCE, RELATION_DESCRIPTION, METADATA_IDX "
                + " from TZ_RELATION "
            	+ " where metadata_idx = " + metadata_idx;
//System.out.println("========>sql : " + sql);                    
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
            	data = new BetaRelationData();
                data.setRelation_idx			(ls.getInt("relation_idx"));
                data.setRelation_kind			(ls.getString("relation_kind"));
                data.setRelation_resource		(ls.getString("relation_resource"));
                data.setRelation_description	(ls.getString("relation_description"));
                data.setMetadata_idx			(ls.getInt("metadata_idx"));
                
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