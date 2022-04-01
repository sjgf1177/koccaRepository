//**********************************************************
//  1. ��      ��: ���� ������ �����Ȳ BEAN
//  2. ���α׷���:  CourseProgressAdminBean.java
//  3. ��      ��: ���� ������ �����Ȳ BEAN
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 8. 13
//  7. ��      ��:
//**********************************************************
package com.credu.course;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.system.*;

public class CourseProgressAdminBean {

    public CourseProgressAdminBean() {}

    /**
    ���� ������ �����Ȳ  ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   ���� ������ �����Ȳ  ����Ʈ
    */
     public ArrayList selectListCourseProgress(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1             = null;       
        ListSet ls2             = null;
        ArrayList list1         = null;
        ArrayList list2			= null;
        DataBox	dbox			= null;
		
        String sql1             = "";
        String sql2             = "";
        
        String  v_Bcourse    = ""; //�����ڽ�
        String  v_course     = ""; //�����ڽ�
        String  v_Bcourseseq = ""; //�����ڽ�����
        String  v_courseseq  = ""; //�����ڽ�����     
        String  v_completion = ""; //����������         
              
        String  ss_grcode    = box.getStringDefault("s_grcode","ALL");        //�����׷�
        String  ss_gyear     = box.getStringDefault("s_gyear","ALL");         //�⵵
        String  ss_grseq     = box.getStringDefault("s_grseq","ALL");         //��������
        String  ss_uclass    = box.getStringDefault("s_uclass","ALL");        //�����з�
        String  ss_subjcourse= box.getStringDefault("s_subjcourse","ALL");    //����&�ڽ�
        String  ss_subjseq   = box.getStringDefault("s_subjseq","ALL");       //���� ����
		String  ss_isclosed  = box.getStringDefault("s_isclosed","ALL");       //�������  
		
		String  v_orderColumn   = box.getString("p_orderColumn");           	//������ �÷���
        String  v_orderType     = box.getString("p_orderType");           		//������ ����      
        
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();          
            list2 = new ArrayList();        
            
            sql1  = "  select ";
			// 2005.11.05_�ϰ��� : decode -> case & When ����. 
			sql1 += " 	case a.isgoyong";
			sql1 += " 		When 'Y' Then '���' ";
			sql1 += " 		Else '����' ";
			sql1 += " 	end as isgoyong, ";
			//sql1 += " decode(a.isgoyong, 'Y', '���', '����') isgoyong, ";
			sql1 += " a.grcode grcode, b.grcodenm grcodenm, a.gyear gyear, a.grseq grseq, a.course course, a.cyear cyear,          ";
            sql1 += "         a.courseseq courseseq, a.coursenm coursenm, a.subj subj, a.year year, a.subjseq subjseq,a.subjseqgr, a.subjnm subjnm,    ";
            sql1 += "         a.isonoff isonoff, a.propstart, a.propend, a.edustart edustart, a.eduend eduend, A.isbelongcourse,A.subjcnt, ";
            sql1 += "         (select count(*) from  TZ_PROPOSE                                                                            ";
            sql1 += "           where subj = A.subj and year = A.year and subjseq = A.subjseq ) as cnt_propose,                            ";
            sql1 += "         (select count(*) from TZ_PROPOSE                                                                             ";
            sql1 += "           where subj = A.subj and year = A.year and subjseq = A.subjseq and CHKFIRST = 'Y') as cnt_chkfirst,       ";
            sql1 += "         (select count(*) from TZ_PROPOSE z                                                                            ";
            sql1 +="            left join TZ_STUDENT y on z.subj=y.subj and z.year=y.year and z.subjseq=y.subjseq and z.userid=y.userid ";
            sql1 += "           where z.subj = A.subj and z.year = A.year and z.subjseq=A.subjseq and chkfinal = 'Y' and ISGRADUATED='Y') as cnt_chkfinal,           ";
            sql1 += "         (select count(*) from TZ_STUDENT                                                                             ";
            sql1 += "                   where subj = A.subj and year = A.year and subjseq = A.subjseq) as cnt_student,                     ";
            sql1 += "         (select count(*) from TZ_STOLD                                                                             ";
            sql1 += "                   where subj = A.subj and year = A.year and subjseq = A.subjseq and isgraduated = 'Y' ) as cnt_pre,  ";
            sql1 += "         (select count(*) from TZ_STOLD                                                                               ";
            sql1 += "                   where subj = A.subj and year = A.year and subjseq = A.subjseq ) as cnt_after                       ";
            sql1 += "    from VZ_SCSUBJSEQ a, TZ_GRCODE b where a.grcode = b.grcode                                                        ";

            if (!ss_grcode.equals("ALL")) {
                sql1+= " and a.grcode = '" + ss_grcode + "'";
            }
            if (!ss_gyear.equals("ALL")) {
                sql1+= " and a.gyear = '" + ss_gyear + "'";
            } 
            if (!ss_grseq.equals("ALL")) {
                sql1+= " and a.grseq = '" + ss_grseq + "'";
            }
            if (!ss_uclass.equals("ALL")) {
                sql1+= " and a.scupperclass = '" + ss_uclass + "'";
            }
            if (!ss_subjcourse.equals("ALL")) {
                sql1+= " and a.scsubj = '" + ss_subjcourse + "'";
            }
            if (!ss_subjseq.equals("ALL")) {
                sql1+= " and a.scsubjseq = '" + ss_subjseq + "'";
            }
			if (!ss_isclosed.equals("ALL")) {
				 sql1+= " and a.isclosed = '" + ss_isclosed + "'";
			 }            
			 
			 if(v_orderColumn.equals("")) {
            	sql1+= " order by a.course, a.cyear, a.courseseq, a.subj, a.year, a.subjseq ";
			} else {
			    sql1+= " order by a.course," + v_orderColumn + v_orderType;
			}

            ls1 = connMgr.executeQuery(sql1);
            
                while (ls1.next()) {
					dbox = ls1.getDataBox();
			        list1.add(dbox);
                }
                               
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;        
    }
}