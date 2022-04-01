package com.credu.mobile.study;

import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DatabaseExecute;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * com.credu.mobile.study
 * MyClassBean.java
 * 
 * @author drsin
 * @Date 2011. 11. 1.
 * @Version 
 * 
 */
public class MobileMyClassBean 
{
	private StringBuffer strQuery = null;
    // private int x = 1;
    // private String tem_grcode = "";
    @SuppressWarnings("unused")
    private int row;
    private ConfigSet config;
	
	public MobileMyClassBean()
	{
		try 
    	{ 
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); // 이 모듈의 페이지당 row 수를 셋팅한다
        } 
    	catch( Exception e ) 
    	{ 
            e.printStackTrace();
        }
	}
	
	/**
	 * 수강중인 과정 차시별 정보
	 * @param box
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public ArrayList getOnLineStudyLessonList(RequestBox box) throws Exception
	{ 
    	DatabaseExecute connMgr         = null;
	        
        ArrayList list = null;
        strQuery = new StringBuffer();
	        
        try
        {
        	
        	connMgr = new DatabaseExecute();
        	String s_userid = box.getSession("userid");
        	String v_subj = box.getString("p_subj");
        	String v_year = box.getString("p_year");
        	String v_subjseq = box.getString("p_subjseq");
        	
        	strQuery= new StringBuffer();

        	strQuery.append("select \n");
        	strQuery.append("    a.subj, a.year, a.subjseq, c.lesson, c.sdesc, c.mobile_url, c.epub, d.subjnm, \n");
        	strQuery.append("    b.edustart, b.eduend, \n");
        	strQuery.append("    decode \n");
        	strQuery.append("    ( \n");
        	strQuery.append("        ( \n");
        	strQuery.append("            select \n");
        	strQuery.append("                count(0) as cnt \n");
        	strQuery.append("            from \n");
        	strQuery.append("                tz_progress s1 \n");
        	strQuery.append("            where \n");
        	strQuery.append("                a.subj = s1.subj \n");
        	strQuery.append("                and a.year = s1.year \n");
        	strQuery.append("                and a.subjseq = s1.subjseq \n");
        	strQuery.append("                and a.userid = s1.userid \n");
        	strQuery.append("                and c.lesson = s1.lesson \n");
        	strQuery.append("                and rownum = 1 \n");
        	strQuery.append("        ), 0, 'N', 'Y' \n");	
        	strQuery.append("    ) as processyn \n");
        	strQuery.append("from \n");
        	strQuery.append("    tz_student a, tz_subjseq b, tz_subjlesson c, tz_subj d \n");
        	strQuery.append("where \n");
        	strQuery.append("    a.subj = b.subj \n");
        	strQuery.append("    and a.year = b.year \n");
        	strQuery.append("    and a.subjseq = b.subjseq \n");
        	strQuery.append("    and a.subj = c.subj \n");
        	strQuery.append("    and a.subj = d.subj \n");
        	strQuery.append("    and a.subj = " + StringManager.makeSQL(v_subj) + " \n");
        	strQuery.append("    and a.year = " + StringManager.makeSQL(v_year) + " \n");
        	strQuery.append("    and a.subjseq  = " + StringManager.makeSQL(v_subjseq) + " \n");
        	strQuery.append("    and a.userid = " + StringManager.makeSQL(s_userid) + " \n");
        	strQuery.append("    and b.eduend >= to_char(sysdate,'yyyymmddhh24')||'23' \n");
        	strQuery.append("order by c.lesson \n");
        	
        	list = connMgr.executeQueryList(box, strQuery.toString());

        }
        catch (Exception ex) 
        {
            ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
            throw new Exception("sql = " + strQuery + "\r\n" + ex.getMessage());
        }
    	
    	return list;
    }
}
