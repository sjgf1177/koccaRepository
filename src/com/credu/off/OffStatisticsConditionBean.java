package com.credu.off;

import com.credu.library.*;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010. 6. 17
 * Time: 오후 3:16:18
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("unchecked")
public class OffStatisticsConditionBean {

	public ArrayList listPage(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		String t = null;
        DataBox dbox = null;
        ArrayList list     = null;
        
		try {
			connMgr = new DBConnectionManager();

			box.sync("s_subjcode");
			box.sync("s_year");
			box.sync("s_subjseq");
			box.sync("s_term");

            String s_subj=box.getString("s_subjcode");
            String s_year=box.getString("s_year");
            String s_subjseq=box.getString("s_subjseq");
            String s_middleclass=box.getString("s_middleclass");
            String s_lowerclass=box.getString("s_lowerclass");
            String s_upperclass=box.getString("s_upperclass");
            String s_search=box.getString("s_subjsearchkey");    // 찾을 과정명

			switch(box.getString("p_gubun").charAt(0))
            {
                case '0':  //교육일정표
                    sql.append("select \n");
                    sql.append("     subjnm,dt,betweendt,to_char(sum(REALPAYMENT),'99,999,999,999') as REALPAYMENT,sum(man_count) as man_count,sum(woman_count) as woman_count, \n");
                    sql.append("     sum(a1) as a1,sum(a2) as a2,sum(a3) as a3,sum(a4) as a4,sum(a5) as a5,sum(a6) as a6\n");
                    sql.append("from ( \n");
                    sql.append("      select\n");
                    sql.append("           b.subjnm,substr(b.edustart,5,2)||'.'||substr(b.edustart,7,2)||'~'||substr(b.eduend,5,2)||'.'||substr(b.eduend,7,2) as dt,\n");
                    sql.append("           to_char(to_date(substr(b.eduend,0,8),'YYYYMMDD')-to_date(substr(b.edustart,0,8),'YYYYMMDD')+1) as betweendt,a.REALPAYMENT,\n");
                    sql.append("           (case when substr(c.resno,7,1)='1' then 1 else 0 end) as man_count,\n");
                    sql.append("           (case when substr(c.resno,7,1)='2' then 1 else 0 end) as woman_count,\n");
                    sql.append("           (case when TRUNC((to_number(to_char(sysdate,'yyyy'))-to_number('19'||substr(c.resno,0,2)))/10,0)='1' then 1 else 0 end) as a1,\n");
                    sql.append("           (case when TRUNC((to_number(to_char(sysdate,'yyyy'))-to_number('19'||substr(c.resno,0,2)))/10,0)='2' then 1 else 0 end) as a2,\n");
                    sql.append("           (case when TRUNC((to_number(to_char(sysdate,'yyyy'))-to_number('19'||substr(c.resno,0,2)))/10,0)='3' then 1 else 0 end) as a3,\n");
                    sql.append("           (case when TRUNC((to_number(to_char(sysdate,'yyyy'))-to_number('19'||substr(c.resno,0,2)))/10,0)='4' then 1 else 0 end) as a4,\n");
                    sql.append("           (case when TRUNC((to_number(to_char(sysdate,'yyyy'))-to_number('19'||substr(c.resno,0,2)))/10,0)='5' then 1 else 0 end) as a5,\n");
                    sql.append("           (case when TRUNC((to_number(to_char(sysdate,'yyyy'))-to_number('19'||substr(c.resno,0,2)))/10,0)='6' then 1 else 0 end) as a6\n");
                    sql.append("       from TZ_OFFPROPOSE a\n");
                    sql.append("       left join TZ_OFFSUBJSEQ b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq \n");
                    sql.append("       left join tz_member c on a.userid=c.userid\n");
                    sql.append("       left join TZ_OFFSTUDENT d on a.subj=d.subj and a.year=d.year and a.subjseq=d.subjseq and a.userid=d.userid \n");
                    sql.append("       left join tz_offsubj e on a.subj=e.subj\n");
                    sql.append("       where A.CHKFINAL='Y' and D.ISGRADUATED='Y' \n");

                    if(!s_subj.equals(""))
                        sql.append("     and a.subj='"+s_subj+"'\n");
                    if(!s_year.equals(""))
                        sql.append("     and a.year='"+s_year+"'\n");
                    if(!s_subjseq.equals("ALL"))
                        sql.append("     and a.subjseq='"+s_subjseq+"'\n");
                    if(!s_middleclass.equals("ALL"))
                        sql.append("     and e.MIDDLECLASS='"+s_middleclass+"'\n");
                    if(!s_lowerclass.equals("ALL"))
                        sql.append("     and e.LOWERCLASS='"+s_lowerclass+"'\n");
                    if(!s_upperclass.equals("ALL"))
                        sql.append("     and e.UPPERCLASS='"+s_upperclass+"'\n");
                    if(!s_search.equals(""))
                        sql.append("     and b.subjnm like '%"+s_search+"%'\n");

                    sql.append(")x \n");
                    sql.append("group by subjnm,dt,betweendt \n");
                    sql.append("order by dt \n");
                    break;
                case '1':
                    break;
                case '2':
                    break;
                case '3':
                    break;
            }

			t = connMgr.replaceParam(sql.toString(), box);
			ls = connMgr.executeQuery(t);

            list = new ArrayList();
            
            while (ls.next()) {
				dbox = ls.getDataBox();
				list.add(dbox);

			}
			sql.delete(0, sql.length());

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, t);
			throw new Exception("sql = " + t + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) {try {ls.close();} catch(Exception e){}}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}
	
	/**
     * 수강생 현황 - New 
     * @param box          receive from the form object and session
     * @return ArrayList         
     * @throws Exception
     */
     public ArrayList selectOffCourseStatList(RequestBox box) throws Exception {      
    
	    	DBConnectionManager connMgr = null;
			DataBox dbox                = null;
			ListSet ls                  = null;
			ArrayList list              = null;
			String sql                  = "";				
			String	ss_year		    = box.getString("param");     //차수
	  		String	ss_subj			= box.getString("param1");    //과정
	  		String  temp 			= "";
	
			try {
				connMgr = new DBConnectionManager();
				list = new ArrayList();
				sql = " select subjnm,subjseq,edustart,eduend,sum(user_cnt) user_cnt,sum(grad_cnt)";
				sql += " grad_cnt,sum(ngrad_cnt) ngrad_cnt, sum(man_cnt) man_cnt, sum(woman_cnt) woman_cnt, sum(job_cnt) job_cnt ";

				sql += "  FROM ( ";
				sql += "         select a.subjnm,a.subjseq,a.edustart,a.eduend,  "; 
				sql += "           decode(b.userid,NULL,0,1) user_cnt, ";
				sql += "           decode(b.isgraduated,'Y',1,0) grad_cnt, ";				
				sql += "           decode(b.isgraduated,'Y',0,1) ngrad_cnt, ";
				sql += "           decode(c.sex,1,1,0) man_cnt, ";
				sql += "           decode(c.sex,2,1,0) woman_cnt, ";
				sql += "           decode(d.seq,1,1,0) job_cnt ";
				sql += "           from ";
				sql += "           tz_offsubjseq a , tz_offstudent b,  ";
				sql += "           tz_member c, tz_offcarrier d   ";
				sql += "           where a.subj = b.subj and c.userid = b.userid and b.subjseq = a.subjseq and d.userid(+) = b.userid and d.year(+) = b.year and d.subj(+) = b.subj";				
				
				int i = 0;
	  			StringTokenizer st = new StringTokenizer(ss_year, ",");
	  			
	  			sql += " and (";
			    while(st.hasMoreElements()) {		    	
			    	temp = st.nextToken();
			    	
			    	if (i != 0 ) sql += " or ";
			    	sql += "a.year ='"+ temp +"'";
					i++;
				}
			    sql += " ) "; 	  		
			    
			    i=0;
	  			StringTokenizer st2 = new StringTokenizer(ss_subj, ",");
	  			sql += " and (";
			    while(st2.hasMoreElements()) {
			    	
			    	temp = st2.nextToken();
			    	if (i != 0 ) sql += " or ";
					sql += " a.subj = '"+  temp  + "'";		
					i++;
				}
			    sql += " ) ";
		    
				sql += ") group by subjnm,subjseq,edustart,eduend ";
			
				ls = connMgr.executeQuery(sql);
				while (ls.next()) {
				    dbox = ls.getDataBox();
				    list.add(dbox);
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
     
     /**
      * 연령별  통계 - New 
      * @param box          receive from the form object and session
      * @return ArrayList         
      * @throws Exception
      */
      public ArrayList selectAgeList(RequestBox box) throws Exception {      
     
	    	DBConnectionManager connMgr = null;
			DataBox dbox                = null;
			ListSet ls                  = null;
			ArrayList list              = null;
			String sql                  = "";	
			
			String	ss_year		    = box.getString("param");     //차수
	  		String	ss_subj			= box.getString("param1");    //과정
	  		String  temp 			= "";
	
			try {
				connMgr = new DBConnectionManager();
				list = new ArrayList();
				
				sql = " select subjnm,subjseq,edustart,eduend, sum(tot) tot,";
				sql += " sum(toddler) toddler, sum(teens) teens,sum(twenty) twenty,sum(thirty) thirty, ";
				sql += " sum(fourty) fourty,sum(fifty) fifty,sum(sixty) sixty, ";
				sql += " sum(seventy) seventy, sum(johndoe) johndoe ";
				sql += " from ( ";
				sql += " select a.subjnm,a.subjseq,a.edustart,a.eduend,";
		        sql += " decode(c.userid,c.userid,1,0) tot, ";
		        sql += " (CASE WHEN memberyear BETWEEN '2004' AND '2013' THEN 1 END) toddler, ";
		        sql += " (CASE WHEN memberyear BETWEEN '1994' AND '2003' THEN 1 END) teens, ";
		        sql += " (CASE WHEN memberyear BETWEEN '1984' AND '1993' THEN 1 END) twenty, ";
		        sql += " (CASE WHEN memberyear BETWEEN '1974' AND '1983' THEN 1 END) thirty, ";
		        sql += " (CASE WHEN memberyear BETWEEN '1964' AND '1973' THEN 1 END) fourty,";
		        sql += " (CASE WHEN memberyear BETWEEN '1954' AND '1963' THEN 1 END) fifty, ";
		        sql += " (CASE WHEN memberyear BETWEEN '1944' AND '1953' THEN 1 END) sixty, ";
		        sql += " (CASE WHEN memberyear BETWEEN '1900' AND '1943' THEN 1 END) seventy, ";
		        sql += " decode(memberyear,NULL,1) johndoe ";
		        
		        sql += "  from ";  
		        sql += "  tz_offsubjseq a , tz_offstudent b, tz_member c  ";                    
		        sql += "  where a.subj = b.subj and a.subjseq = b.subjseq and c.userid = b.userid  ";  
		        
		        int i = 0;
	  			StringTokenizer st = new StringTokenizer(ss_year, ",");
	  			
	  			sql += " and (";
			    while(st.hasMoreElements()) {		    	
			    	temp = st.nextToken();
			    	
			    	if (i != 0 ) sql += " or ";
			    	sql += "a.year ='"+ temp +"'";
					i++;
				}
			    sql += " ) "; 	  		
			    
			    i=0;
	  			StringTokenizer st2 = new StringTokenizer(ss_subj, ",");
	  			sql += " and (";
			    while(st2.hasMoreElements()) {
			    	
			    	temp = st2.nextToken();
			    	if (i != 0 ) sql += " or ";
					sql += " a.subj = '"+  temp  + "'";		
					i++;
				}
			    sql += " ) ";
		    
				sql += ") group by subjnm,subjseq,edustart,eduend ";
	       
				ls = connMgr.executeQuery(sql);
				while (ls.next()) {
				    dbox = ls.getDataBox();
				    list.add(dbox);
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
      
      /**
       * 오프라인 통계 상세보기 Grid
       * @param box          receive from the form object and session
       * @return ArrayList         
       * @throws Exception
       */
       public ArrayList selectDetailList(RequestBox box) throws Exception {      
      
 	    	DBConnectionManager connMgr = null;
 			DataBox dbox                = null;
 			ListSet ls                  = null;
 			ArrayList list              = null;
 			String sql                  = "";	
 			
 			String	ss_year		    = box.getString("param");     //차수
 	  		String	ss_subj			= box.getString("param1");    //과정
 	  		String  temp 			= "";
 	
 			try {
 				connMgr = new DBConnectionManager();
 				list = new ArrayList();
 				
 				sql = " select subjnm, a.subjseq, a.edustart,a.eduend,c.userid,c.name, b.userid, b.year, b.subj, ";
 				sql += " decode(c.sex,1,'남',2,'여') gender, decode(b.isgraduated,'Y','수료','미수료') isgraduated , d.compnm ";
 				sql += " from tz_offsubjseq a , ";
 				sql += " tz_offstudent b, tz_member c, tz_offcarrier d ";
 				sql += " where a.subj = b.subj and c.userid = b.userid and a.subjseq = b.subjseq and d.userid(+) = b.userid and d.year(+) = b.year and d.subj(+) = b.subj ";
 				//sql += " and  (a.year ='2013' ) and ( a.subj = 'SB13005' ) ";
 		        
 		        int i = 0;
 	  			StringTokenizer st = new StringTokenizer(ss_year, ",");
 	  			
 	  			sql += " and (";
 			    while(st.hasMoreElements()) {		    	
 			    	temp = st.nextToken();
 			    	
 			    	if (i != 0 ) sql += " or ";
 			    	sql += "a.year ='"+ temp +"'";
 					i++;
 				}
 			    sql += " ) "; 	  		
 			    
 			    i=0;
 	  			StringTokenizer st2 = new StringTokenizer(ss_subj, ",");
 	  			sql += " and (";
 			    while(st2.hasMoreElements()) {
 			    	
 			    	temp = st2.nextToken();
 			    	if (i != 0 ) sql += " or ";
 					sql += " a.subj = '"+  temp  + "'";		
 					i++;
 				}
 			    sql += " ) ";
 		    
 				sql += " order by subjnm,b.subjseq asc, c.name ";
 				
 				ls = connMgr.executeQuery(sql);
 				while (ls.next()) {
 				    dbox = ls.getDataBox();
 				    list.add(dbox);
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
