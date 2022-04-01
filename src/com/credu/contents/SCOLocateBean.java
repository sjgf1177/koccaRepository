//**********************************************************
//  1. 제      목: SCO Locate Operation Bean
//  2. 프로그램명: SCOLocateBean.java
//  3. 개      요: SCO Locate관리에 관련된 Bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.17
//  7. 수      정: 박미복 2004. 11.17
//**********************************************************

package com.credu.contents;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.contents.*;
import com.credu.common.*;

public class SCOLocateBean {

    public SCOLocateBean() {}

    /**
    SCO 리스트 조회
    @param box          receive from the form object and session
    @return ArrayList   Object리스트
    */
    public int SelectNextSCONumber(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql  = "";
        int next_num = 0;
        
        try {
            connMgr = new DBConnectionManager();

            sql = "select max(sconumber) max_sco from tz_scolocate";

            System.out.println(sql);
            
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
            	next_num = ls.getInt("max_sco");
            }
            next_num++;
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return next_num;
    }
       
}