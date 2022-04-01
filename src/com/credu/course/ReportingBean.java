//**********************************************************
//  1. 제      목: 레포팅
//  2. 프로그램명: ReportingBean.java
//  3. 개      요: 레포팅 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0 QnA
//  6. 작      성: 정은년 2005. 9. 1
//  7. 수      정: 이나연 05.11.16 _ decode 수정
//**********************************************************
package com.credu.course;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ReportingBean {
    
    public ReportingBean() {}
    
    // 5점척도
    public ArrayList SelectSulmumList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql  = "";
        DataBox dbox = null;
        String v_grcode = box.getString("s_grcode");
        String v_year   = box.getString("s_gyear");
        String v_subjseqgr = box.getString("s_grseq");
       
        try {                        

            sql = " select  c.usercnt, b.subjnm , a.subj, a.subjseq, a.userid, ";
			// 수정일 : 05.11.16 수정자 : 이나연 _ decode 수정
//			sql+= "     decode(a.distcode1,5,1,4,2,3,3,2,4,1,5,0) dcode1,   ";    // 과정만족도
//          sql+= "     decode(a.distcode2,5,1,4,2,3,3,2,4,1,5,0) dcode2,   ";    // 내용이해도
//          sql+= "     decode(a.distcode3,5,1,4,2,3,3,2,4,1,5,0) dcode3,   ";    // 과정난이도
//          sql+= "     decode(a.distcode4,5,1,4,2,3,3,2,4,1,5,0) dcode4,   ";    // 업무활용도
//          sql+= "     decode(a.distcode5,5,1,4,2,3,3,2,4,1,5,0) dcode5,   ";    // 질문대응
//          sql+= "     decode(a.distcode6,5,1,4,2,3,3,2,4,1,5,0) dcode6    ";    // 장애대응
			
            sql+= "     case a.distcode1	";
			sql+= "     	When  5 	Then 1      	When  4 	Then 2 	";	
			sql+= "     	When  3 	Then 3			When  2 	Then 4 	";	
			sql+= "     	When  1 	Then 5 			Else 0 			 	";	
			sql+= "     End as dcode1,   ";    // 과정만족도
            sql+= "     case a.distcode2	";
			sql+= "     	When  5 	Then 1      	When  4 	Then 2 	";	
			sql+= "     	When  3 	Then 3			When  2 	Then 4 	";	
			sql+= "     	When  1 	Then 5 			Else 0 			 	";	
			sql+= "     End as dcode2,   ";    // 내용이해도
            sql+= "     case a.distcode3	";
			sql+= "     	When  5 	Then 1      	When  4 	Then 2 	";	
			sql+= "     	When  3 	Then 3			When  2 	Then 4 	";	
			sql+= "     	When  1 	Then 5 			Else 0 			 	";	
			sql+= "     End as dcode3,   ";    // 과정난이도
            sql+= "     case a.distcode4	";
			sql+= "     	When  5 	Then 1      	When  4 	Then 2 	";	
			sql+= "     	When  3 	Then 3			When  2 	Then 4 	";	
			sql+= "     	When  1 	Then 5 			Else 0 			 	";	
			sql+= "     End as dcode4,   ";    // 업무활용도
            sql+= "     case a.distcode5	";
			sql+= "     	When  5 	Then 1      	When  4 	Then 2 	";	
			sql+= "     	When  3 	Then 3			When  2 	Then 4 	";	
			sql+= "     	When  1 	Then 5 			Else 0 			 	";	
			sql+= "     End as dcode5,   ";    // 질문대응
            sql+= "     case a.distcode6	";
			sql+= "     	When  5 	Then 1      	When  4 	Then 2 	";	
			sql+= "     	When  3 	Then 3			When  2 	Then 4 	";	
			sql+= "     	When  1 	Then 5 			Else 0 			 	";	
			sql+= "     End as dcode6,   ";    // 장애대응
			
            sql+= " from tz_suleach a, tz_subjseq b, ";
            sql+= "      (select subj, subjseq, count(userid) usercnt from tz_suleach ";
            sql+= "       where subj not in ('CP','TARGET', 'CONTENTS') and grcode!='ALL' ";
            sql+= "       group by subj, subjseq) c    ";
            sql+= " where a.subj=b.subj and a.subjseq=b.subjseq and a.subj=c.subj and a.subjseq=c.subjseq   ";
            sql+= "   and a.subj not in ('CP','TARGET', 'CONTENTS') and a.grcode!='ALL' ";
            if(!v_grcode.equals("ALL")){
                sql+= " and b.grcode='"+v_grcode+"' ";
            }
            if(!v_year.equals("ALL")){
                sql+= " and b.year='"+v_year+"' ";
            }
            if(!v_subjseqgr.equals("ALL")){
                sql+= " and b.grseq='"+v_subjseqgr+"' ";
            }                        

            sql+= " order by a.subj asc, a.subjseq, a.userid   ";
 
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox=ls.getDataBox();
                list1.add(dbox);
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
        return list1;
    }	
    

    // 시간대별학습비율
    public ArrayList SelectSubjTimeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql  = "";
        DataBox dbox = null;
        String v_grcode = box.getString("s_grcode");
        String v_year   = box.getString("s_gyear");
        String v_subjseqgr = box.getString("s_grseq");

       
        try {                         
            sql = "  select date_time, sum(cnt) cnt from TZ_SUBJCOUNT  ";   
			// 수정일 : 05.11.16 수정자 : 이나연 _ || 수정
//          sql+= "  where subj||date_year||subjseq in (           ";
//          sql+= "        select scsubj||scyear||scsubjseq from vz_scsubjseq         ";
			sql+= "  where subj+date_year+subjseq in (           ";
            sql+= "        select scsubj+scyear+scsubjseq from vz_scsubjseq         ";
            sql+= "        where grcode='"+v_grcode+"' and gyear='"+v_year+"'         ";
			
            if(!v_subjseqgr.equals("ALL")){
            sql+= "        and grseq='"+v_subjseqgr+"' ";
        	}
            sql+= "  )  ";
            sql+= "  group by date_time order by date_time         ";    

            connMgr = new DBConnectionManager();
            list1 = new ArrayList();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox=ls.getDataBox();
                list1.add(dbox);
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
        return list1;
    }
}



