//**********************************************************
//  1. 제      목: TechnicalRequirement Operation Bean
//  2. 프로그램명: TechnicalRequirementBean.java
//  3. 개      요: TechnicalRequirement관리에 관련된 Bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.26
//  7. 수      정: 박미복 2004. 11.26
//**********************************************************

package com.credu.contents;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.contents.*;
import com.credu.common.*;

public class TechnicalRequirementBean {

    public TechnicalRequirementBean() {}

   /**
    get TechnicalRequirementData by oid
    @param metadata_idx int
    @param box          receive from the form object and session
    @return ArrayList
    */  
    public ArrayList selectTechnicalRequirementData(int metadata_idx, RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = new ArrayList();
        String sql = "";
		TechnicalRequirementData data = null;
		
        try {
            connMgr = new DBConnectionManager();

            sql = "select TECHNICAL_REQUIREMENT_IDX, REQUIREMENT_TYPE, REQUIREMENT_NAME, MINIMUM_VERSION, MAXIMUM_VERSION, METADATA_IDX "
                + " from TZ_TEC_REQ "
            	+ " where metadata_idx = " + metadata_idx;
//System.out.println("========>sql : " + sql);                    
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
            	data = new TechnicalRequirementData();
                data.setTechnical_requirement_idx			(ls.getInt("technical_requirement_idx"));
                data.setRequirement_type					(ls.getString("requirement_type"));
                data.setRequirement_name					(ls.getString("requirement_name"));
                data.setMinimum_version						(ls.getString("minimum_version"));
                data.setMaximum_version						(ls.getString("maximum_version"));
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