//**********************************************************
//1. 제      목: 
//2. 프로그램명: SelectionUtil.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-07-23
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.system;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("unchecked")
public class SelectionUtil {
	public final static int NONE  		= 0;
	public final static int GRCODE  	= 1;
	public final static int GYEAR   	= 2;
	public final static int GRSEQ   	= 3;
	public final static int UPPERCLASS  = 4;
	public final static int SUBJCOURSE  = 5;
	public final static int SUBJSEQ   	= 6;
	public final static int COMP		= 7;
	
	public final static String 	ONOFF_GUBUN = "0004";  
	public final static String 	SEL_GUBUN   = "0006";
	public final static String 	JIKUN       = "JIKUN";
	public final static String 	JIKUP       = "JIKUP";
	public final static String 	GPM         = "ZGMP";
    
	public static void SetBoxDefault(RequestBox box, SelectParam p_param) {
		box.put("p_selectname", p_param.getSelectname());
		box.put("p_onchange", p_param.getOnchange());
		box.put("p_nterm",    String.valueOf(p_param.getNterm()));
		box.put("p_all", String.valueOf(p_param.isAll()));
		box.put("session", p_param.getSession());
	}

///////////////////////////////////////////////////////////////////////////////
	// 교육그룹 Select 문자열을 만든다.
	public static String getGrcode(String p_grcode, SelectParam p_param) throws Exception {
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getGrcode");

		box.put(p_param.getSelectname(), p_grcode);
		
		SetBoxDefault(box, p_param);
				
		return getSelectionString(box);
	}
   
	// 교육연도 Select 문자열을 만든다.
	public static String getGyear(String p_grcode, String p_gyear, SelectParam p_param) throws Exception {
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getGyear");

		box.put("p_grcode",   p_grcode);
		box.put(p_param.getSelectname(), p_gyear);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	
	// 교육차수 Select 문자열을 만든다.
	public static String getGrseq(String p_grcode, String p_gyear, String p_grseq, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getGrseq");
		
		box.put("p_grcode",   p_grcode);
		box.put("p_gyear" ,   p_gyear );
		box.put(p_param.getSelectname(), p_grseq );

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}

	// 교육그룹,연도,차수에 해당하는 과정대분류 Select 문자열을 만든다.(OnChange 이벤트 포함)
	public static String getUpperClass(String p_grcode, String p_gyear, String p_grseq, String p_uclass, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getUpperClass");

		box.put("p_grcode",   p_grcode);
		box.put("p_gyear" ,   p_gyear );
		box.put("p_grseq" ,   p_grseq );
		box.put(p_param.getSelectname(), p_uclass);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}

	// 교육그룹,연도,차수,과정대분류에 해당하는 코스/코스 Select 문자열을 만든다.(OnChange 이벤트 포함)
	public static String getSubjCourse(String p_grcode, String p_gyear, String p_grseq, 
										 String p_uclass, String p_subjcourse, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getSubjCourse");

		box.put("p_grcode",    p_grcode);
		box.put("p_gyear",     p_gyear);
		box.put("p_grseq",     p_grseq);
		box.put("p_uclass",    p_uclass);
		box.put(p_param.getSelectname(),p_subjcourse);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	
	// 교육그룹,연도,차수,과정대분류,과정/코스에 해당하는 과정/코스 차수 Select 문자열을 만든다.(OnChange 이벤트 포함)
	public static String getSubjSeq(String p_grcode, String p_gyear, String p_grseq, 
									String p_uclass, String p_subjcourse, String p_subjseq, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getSubjSeq");

		box.put("p_grcode",    p_grcode);
		box.put("p_gyear",     p_gyear);
		box.put("p_grseq",     p_grseq);
		box.put("p_uclass",    p_uclass);
		box.put("p_subjcourse",p_subjcourse);
		box.put(p_param.getSelectname(), p_subjseq);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	
	// 교육그룹,연도,차수,과정대분류,과정/코스에 해당하는 과정/코스 차수 Select 문자열을 만든다.(OnChange 이벤트 포함)
	public static String getGrcomp(String p_grcode, String p_gyear, String p_grseq, 
								   String p_uclass, String p_subjcourse, String p_subjseq, 
								   String p_comp,			   			   
								   SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getGrcomp");

		box.put("p_grcode",    p_grcode);
		box.put("p_gyear",     p_gyear);
		box.put("p_grseq",     p_grseq);
		box.put("p_uclass",    p_uclass);
		box.put("p_subjcourse",p_subjcourse);
		box.put("p_comp",      p_comp);
		box.put(p_param.getSelectname(), p_comp);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
///////////////////////////////////////////////////////////////////////////////
  // 교육연도 Select 문자열을 만든다.
  public static String getGyear(String p_gyear, SelectParam p_param) throws Exception {
	  RequestBox box = new RequestBox("parambox");
	  box.put("p_callmethod", "getGyear");
		
	  box.put(p_param.getSelectname(), p_gyear);

	  SetBoxDefault(box, p_param);

	  return getSelectionString(box);
  }	

	
	// 과정대분류  Select 문자열을 만든다.(OnChange 이벤트 포함)
	public static String getUpperClass(String p_upperclass, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getUpperClass2");

		box.put(p_param.getSelectname(), p_upperclass);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}

	// 과정대분류  Select 문자열을 만든다.(OnChange 이벤트 포함)
	public static String getOffUpperClass(String p_upperclass, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getOffUpperClass2");

		box.put(p_param.getSelectname(), p_upperclass);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}

	// 과정대분류  Select 문자열을 만든다.(OnChange 이벤트 포함)
	public static String getUpperClass2(String p_upperclass, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getUpperClass2");

		box.put("p_not_course",  "Y");
		box.put(p_param.getSelectname(), p_upperclass);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}

	// 과정중분류  Select 문자열을 만든다.(OnChange 이벤트 포함)
	public static String getMiddleClass(String p_upperclass, String p_middleclass, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getMiddleClass");

		box.put("p_upperclass", p_upperclass);
		box.put(p_param.getSelectname(), p_middleclass);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	public static String getOffMiddleClass(String p_upperclass, String p_middleclass, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getOffMiddleClass");

		box.put("p_upperclass", p_upperclass);
		box.put(p_param.getSelectname(), p_middleclass);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}

	// 과정소분류  Select 문자열을 만든다.(OnChange 이벤트 포함)
	public static String getLowerClass(String p_upperclass, String p_middleclass, String p_lowerclass, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getLowerClass");

		box.put("p_upperclass", p_upperclass);
		box.put("p_middleclass", p_middleclass);
		box.put(p_param.getSelectname(), p_lowerclass);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	
	// 
	public static String getLevel1Code(String p_gubun, String p_code, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getLevel1Code");

		box.put("p_gubun", p_gubun);
		box.put(p_param.getSelectname(), p_code);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	
	public static String getUpperClassSubjCode(String p_upperclass, String p_subj, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getUpperClassSubjCode");

		box.put("p_upperclass", p_upperclass);
		box.put(p_param.getSelectname(), p_subj);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	
	public static String getYesNoCode(String p_yesno, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getYesNoCode");

		box.put(p_param.getSelectname(), p_yesno);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}

	// 과정차수별 코스 Select 문자열을 만든다.
	public static String getSubjClass(String p_subj, String p_year, String p_subjseq, String p_class, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getSubjClass");
		
		box.put("p_subj",    p_subj);
		box.put("p_year",    p_year);
		box.put("p_subjseq", p_subjseq);
		box.put("p_class",   p_class);
		box.put(p_param.getSelectname(), p_class);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	
	// 과정차수별 강사 Select 문자열을 만든다.
	public static String getSubjTutor(String p_subj, String p_year, String p_subjseq, String p_class, String p_mtutorid, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getSubjTutor");
		
		box.put("p_subj",     p_subj);
		box.put("p_year",     p_year);
		box.put("p_subjseq",  p_subjseq);
		box.put("p_class",    p_class);
		box.put("p_mtutorid", p_mtutorid);
		box.put(p_param.getSelectname(), p_mtutorid);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	
	// [직군별|직급별|사업부]
	public static String getSelgubun(String p_selgubun, SelectParam p_param) throws Exception {
		return getLevel1Code(SelectionUtil.SEL_GUBUN, p_selgubun, p_param);
	}
	
	// [직군별|직급별|사업부]
	public static String getSelText(String p_grpcomp, String p_selgubun, String p_seltext, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getSelText");
		
		box.put("p_grpcomp",  p_grpcomp);
		box.put("p_selgubun", p_selgubun);
		box.put("p_seltext",  p_seltext);
		box.put(p_param.getSelectname(), p_seltext);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	
	// [부서조회]
	public static String getSelDept(String p_grpcomp, String p_selgubun, String p_selgpm, String p_seldept, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getSelDept");
		
		box.put("p_grpcomp",  p_grpcomp);
		box.put("p_selgubun", p_selgubun);
		box.put("p_selgpm",   p_selgpm);
		box.put("p_seldept",  p_seldept);
		box.put(p_param.getSelectname(), p_seldept);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
		
	public static String getClass(String p_subj,String p_subjseq,String p_class,SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getClass");
		box.put(p_param.getSelectname(), p_class);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	
	public static String getGrpcomp(String p_grcode, String p_grpcomp, SelectParam p_param) throws Exception {	
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getGrpcomp");

		box.put("p_grcode",  p_grcode);
		box.put(p_param.getSelectname(), p_grpcomp);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	
	public static String getSulpapernum(String p_grcode, String p_gyear, String p_grseq, String p_subj, String p_sulyear, int p_sulpapernum, SelectParam p_param) throws Exception {
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getSulpapernum");
		
		box.put("p_grcode", p_grcode);
		box.put("p_gyear",  p_gyear);
		box.put("p_grseq",  p_grseq);
		box.put("p_subj",   p_subj);
		box.put("p_sulyear",   p_sulyear);
		
		box.put("p_sulpaernum", String.valueOf(p_sulpapernum));
		box.put(p_param.getSelectname(), String.valueOf(p_sulpapernum));

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	
	public static String getSubjlesson(String p_subj, String p_lesson, SelectParam p_param) throws Exception {
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getSubjlesson");
		
		box.put("p_subj",    p_subj);

		box.put("p_lesson",  p_lesson);
		box.put(p_param.getSelectname(), p_lesson);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}

	public static String getSubjlesson2(String p_subj, String p_lesson, SelectParam p_param) throws Exception {
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getSubjlesson");
		
		box.put("p_subj",    p_subj);

		box.put("p_lesson",  p_lesson);
		box.put(p_param.getSelectname(), p_lesson);

		SetBoxDefault(box, p_param);

        Vector v_veccode = new Vector();
        Vector v_vecname = new Vector();

		v_veccode.addElement("ALL");
		v_veccode.addElement("00");
		v_vecname.addElement("ALL");
		v_vecname.addElement("00  시스템관련 및 기타질문");

		box.put("p_allcode", v_veccode);
		box.put("p_allname", v_vecname);

		return getSelectionString2(box);
	}

	public static String getBeforePaper(String p_subj, String p_lesson, String p_ptype, SelectParam p_param) throws Exception {
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getBeforePaper");
		
		box.put("p_subj",    p_subj);

		box.put("p_lesson",  p_lesson);
		box.put("p_ptype",   p_ptype);
		box.put(p_param.getSelectname(), "");

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	
	public static String getOTBeforePaper(String p_subj, SelectParam p_param) throws Exception {
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getOTBeforePaper");
		
		box.put("p_subj",    p_subj);

		box.put(p_param.getSelectname(), "");

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	
	public static String getOffSubj(String p_grcode, String p_gyear, String p_grseq, String p_offsubj, SelectParam p_param) throws Exception {
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getOffSubj");
		
		box.put("p_grcode",    p_grcode);
		box.put("p_gyear",     p_gyear);
		box.put("p_grseq",     p_grseq);
		box.put(p_param.getSelectname(), p_offsubj);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
	
	public static String getOffSubjseq(String p_grcode, String p_gyear, String p_grseq, String p_offsubj, String p_offsubjseq, SelectParam p_param) throws Exception {
		RequestBox box = new RequestBox("parambox");
		box.put("p_callmethod", "getOffSubjseq");
		
		box.put("p_grcode",    p_grcode);
		box.put("p_gyear",     p_gyear);
		box.put("p_grseq",     p_grseq);
		box.put("p_offsubj",   p_offsubj);
		box.put(p_param.getSelectname(), p_offsubjseq);

		SetBoxDefault(box, p_param);

		return getSelectionString(box);
	}
///////////////////////////////////////////////////////////////////////////////
	public static String getSelectionString(RequestBox box) throws Exception {	
		String v_callmethod = box.getString("p_callmethod");

		String v_selectname = box.getString("p_selectname");
		String v_onchange = box.getString("p_onchange");
		int    v_nterm    = box.getInt("p_nterm");
		String v_all      = box.getString("p_all");
	
		String v_strterm = "";
		for (int i=0; i<v_nterm; i++) {
			v_strterm += " ";				
		} 
		
		String str = "";
		///////////////////////////////////////////////////////////////////////
		str = "\r\n" + v_strterm + "<select name=\"" + v_selectname + "\"";
		if (!v_onchange.equals("")) {
			str += " onChange=\"" + v_onchange + "\"";
		} 
		str += ">\r\n";
		///////////////////////////////////////////////////////////////////////
		SelectionBean bean = new SelectionBean();
		ArrayList     list = bean.getSelectionList(box);
		
		String   v_fmtstr    = 	"{0}  <option value=\"{1}\"{2}>{3}</option>\r\n";
		Object[] v_arguments = {"","","",""};
		String 	 v_selected  = "";
//		String   v_selectstring = "";
		///////////////////////////////////////////////////////////////////////
		if (v_all.equals("true")) {
			v_arguments[0] = v_strterm;
			v_arguments[1] = "ALL";
			v_arguments[2] = ("ALL".equals(box.getString(v_selectname)) ? " selected" :  " ");
			v_arguments[3] = "ALL";
			str += MessageFormat.format(v_fmtstr, v_arguments);
		}
		///////////////////////////////////////////////////////////////////////
		if (v_callmethod.equals("getGyear")) {
			if (list.size() < 1) {
				v_arguments[0] = v_strterm;
				v_arguments[1] = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
				v_arguments[2] =  " selected";
				v_arguments[3] = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
				str += MessageFormat.format(v_fmtstr, v_arguments);
			}
		}
		///////////////////////////////////////////////////////////////////////
		for (int i = 0; i < list.size(); i++) {
			SelectionData data = (SelectionData)list.get(i);
			if (data.getCode().equals(box.getString(v_selectname)))
				v_selected = " selected";
			else
				v_selected = "";

			v_arguments[0] = v_strterm;
			v_arguments[1] = data.getCode();
			v_arguments[2] = v_selected;
			v_arguments[3] = data.getName();

			str += MessageFormat.format(v_fmtstr, v_arguments);
		}
		///////////////////////////////////////////////////////////////////////
		str += v_strterm + "</select>";
		///////////////////////////////////////////////////////////////////////
		return str;
	}
	
	public static String getSelectionString2(RequestBox box) throws Exception {	
//      getSelectionString2() 호출하기 전에 vector를 put해줍니다. 
//		box.put("p_allcode", Vector);
//		box.put("p_allname", Vector);
		
		String v_callmethod = box.getString("p_callmethod");

		String v_selectname = box.getString("p_selectname");
		String v_onchange = box.getString("p_onchange");
		int    v_nterm    = box.getInt("p_nterm");
		String v_all      = box.getString("p_all");

		Vector v_allcode  = (Vector)box.getObject("p_allcode");
		Vector v_allname  = (Vector)box.getObject("p_allname");
		String v_temp     = "";

		String v_strterm = "";
		for (int i=0; i<v_nterm; i++) {
			v_strterm += " ";				
		} 
		
		String str = "";
		///////////////////////////////////////////////////////////////////////
		str = "\r\n" + v_strterm + "<select name=\"" + v_selectname + "\"";
		if (!v_onchange.equals("")) {
			str += " onChange=\"" + v_onchange + "\"";
		} 
		str += ">\r\n";
		///////////////////////////////////////////////////////////////////////
		SelectionBean bean = new SelectionBean();
		ArrayList     list = bean.getSelectionList(box);
		
		String   v_fmtstr    = 	"{0}  <option value=\"{1}\"{2}>{3}</option>\r\n";
		Object[] v_arguments = {"","","",""};
		String 	 v_selected  = "";
//		String   v_selectstring = "";
		///////////////////////////////////////////////////////////////////////
		if (v_all.equals("true") && v_allcode != null) {
			for (int i=0; i<v_allcode.size() && i<v_allname.size(); i++) {
				v_arguments[0] = v_strterm;
				v_temp = (String)v_allcode.get(i);
				v_arguments[1] = v_temp; 
				v_arguments[2] = (v_temp.equals(box.getString(v_selectname)) ? " selected" :  " ");
				v_arguments[3] = (String)v_allname.get(i);
				str += MessageFormat.format(v_fmtstr, v_arguments);
			}
		}
		///////////////////////////////////////////////////////////////////////
		if (v_callmethod.equals("getGyear")) {
			if (list.size() < 1) {
				v_arguments[0] = v_strterm;
				v_arguments[1] = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
				v_arguments[2] =  " selected";
				v_arguments[3] = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
				str += MessageFormat.format(v_fmtstr, v_arguments);
			}
		}
		///////////////////////////////////////////////////////////////////////
		for (int i = 0; i < list.size(); i++) {
			SelectionData data = (SelectionData)list.get(i);
			if (data.getCode().equals(box.getString(v_selectname)))
				v_selected = " selected";
			else
				v_selected = "";

			v_arguments[0] = v_strterm;
			v_arguments[1] = data.getCode();
			v_arguments[2] = v_selected;
			v_arguments[3] = data.getName();

			str += MessageFormat.format(v_fmtstr, v_arguments);
		}
		///////////////////////////////////////////////////////////////////////
		str += v_strterm + "</select>";
		///////////////////////////////////////////////////////////////////////
		return str;
	}

	public static int  getSeq(Hashtable data) throws Exception {
		int v_maxno = 0;               
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		String colname = ""; 
		String colval  = ""; 
		try {
			sql = "select max(" + (String)data.get("seqcolumn") + ") maxno" ;  
			sql+= "  from " + (String)data.get("seqtable");
			for (int i=0; i < Integer.parseInt((String)data.get("paramcnt")); i++) {
				if (i == 0) {
					sql+= " where "; 				
				} else {
					sql+= " and ";			
				}
				colname = (String)data.get("param" + String.valueOf(i));
				colval  = (String)data.get(colname);
				sql+= colname + " = " + colval; 
			}
			connMgr = new DBConnectionManager();
			ls = connMgr.executeQuery(sql);
            
			while (ls.next()) {
				v_maxno = ls.getInt("maxno");
			}
		}            
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return v_maxno+1;
	}
}
