//**********************************************************
//  1. ��      ��: GeneralCoverage Operation Bean
//  2. ���α׷���: GeneralCoverageBean.java
//  3. ��      ��: GeneralCoverage������ ���õ� Bean
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

public class GeneralCoverageBean {

    public GeneralCoverageBean() {}

   /**
    get GeneralCoverageData by oid
    @param metadata_idx int
    @param box          receive from the form object and session
    @return ArrayList
    */  
    public ArrayList selectGeneralCoverageData(int metadata_idx, RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = new ArrayList();
        String sql = "";
		GeneralCoverageData data = null;
		
        try {
            connMgr = new DBConnectionManager();

            sql = "select GENERAL_COVERAGE_IDX, COVERAGE, METADATA_IDX "
                + " from TZ_GEN_COV "
            	+ " where metadata_idx = " + metadata_idx;
//System.out.println("========>sql : " + sql);                    
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
            	data = new GeneralCoverageData();
                data.setGeneral_coverage_idx		(ls.getInt("general_coverage_idx"));
                data.setCoverage					(ls.getString("coverage"));
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