//**********************************************************
//1. 제      목: 
//2. 프로그램명: SelectionBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-07-23
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.system;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import com.credu.common.GetCodenm;
import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("unchecked")
public class SelectionBean {
	public SelectionBean() {}

	/**
	Selection코드 리스트
	@param box        receive from the form object and session
	@return ArrayList Selection코드 리스트
	*/
	public ArrayList getSelectionList(RequestBox box) throws Exception {
		String v_callmethod = box.getString("p_callmethod");
		ArrayList  list = null;
		SelectionData data = null;
		if (v_callmethod.equals("getYesNoCode")) {
			list = new ArrayList();
			data = new SelectionData();
			data.setCode("Y");
			data.setName("Y");
			list.add(data);
			data = new SelectionData();
			data.setCode("N");
			data.setName("N");
			list.add(data);
		} else {
			list = getSelectionList(getQueryString(box));
		}
		return list;
	}

	/**
	Selection코드 리스트
	@param sql          Query String
	@return ArrayList   Selection코드 리스트
	*/
	public ArrayList getSelectionList(String sql) throws Exception {
		ArrayList     list = null;
		SelectionData data = null;
		list = new ArrayList();
		data = new SelectionData();
        
		if (!sql.equals("ALL")) {
			DBConnectionManager connMgr = null;
			ListSet   ls    = null;
			try {
				connMgr = new DBConnectionManager();
				ls = connMgr.executeQuery(sql);
				while (ls.next()) {
					data=new SelectionData();
					data.setCode(ls.getString("code"));
					data.setName(ls.getString("name"));
					list.add(data);
				}
			}
			catch (Exception ex) {
				ErrorManager.getErrorStackTrace(ex, null, sql);
				throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
			}
			finally {
				if(ls != null) { try { ls.close(); }catch (Exception e) {} }
				if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
			}
		}
		return list;
	}

	/**
	Query String
	@param box        receive from the form object and session
	@return String    Query String
	A1	ultra
	A2	super
	F1	과정관리
	H1	교육그룹
	K8	부서관리
	K9	부서장
	P1	강사
	회사정보 tz_grcompman
	교육그룹 tz_grcodeman
	과정정보 tz_subjman

	*/
	public String  getQueryString(RequestBox box) throws Exception {
		String sql = "";
		String s_gadmin = box.getSession("gadmin");
		String s_userid = box.getSession("userid");
		String v_opt	= "";
		if (s_gadmin.length() > 0) {  
			v_opt = s_gadmin.substring(0,1);
		}
		String v_comp	= "";
		
		String v_callmethod = box.getString("p_callmethod");
		if (v_callmethod.equals("getGrcode")) {
			sql = "select distinct a.grcode code, a.grcodenm name"
				+ "  from TZ_GRCODE a";
			if (v_opt.equals("H")){
				sql+= "  , tz_grcodeman b"
					+ " where a.grcode=b.grcode and b.userid="+SQLString.Format(s_userid);
			}else if (v_opt.equals("K")){
				v_comp = GetCodenm.get_compval(GetCodenm.get_comp_userid(s_userid));
				sql+= "  , tz_compman b, tz_grcomp c"
					+ " where a.grcode=c.grcode"// and b.userid=d.userid "
					+ "   and c.comp like "+SQLString.Format(v_comp) + "   and b.comp like "+SQLString.Format(v_comp)
					+ "   and b.userid="+SQLString.Format(s_userid);
			}else if (v_opt.equals("P")){
				sql+= "  , tz_subjseq b, tz_classtutor c"
					+ " where a.grcode=b.grcode and b.subj=c.subj and b.year=c.year and b.subjseq=c.subjseq "
					+ "   and c.userid="+SQLString.Format(s_userid);
			}		

			sql+= " order by a.grcode ";System.out.println("sql : " + sql);
		} else if (v_callmethod.equals("getGyear")) {
			sql = "select gyear code, gyear name";
			sql+= "  from TZ_GRSEQ ";
			if (!box.getString("p_grcode").equals("ALL") && !box.getString("p_grcode").equals("")) {	        
				sql+= " where grcode = " + SQLString.Format(box.getString("p_grcode"));
			}	            
			sql+= " group by gyear ";
			sql+= " order by gyear desc";
		} else if (v_callmethod.equals("getGrseq")) {
			if (box.getString("p_grcode").equals("ALL") || box.getString("p_gyear").equals("ALL")) {
				sql = "ALL";
			} else {
				sql = "select grseq code, grseqnm name";
				sql+= "  from TZ_GRSEQ ";
				sql+= " where grcode = " + SQLString.Format(box.getString("p_grcode"));
				sql+= "   and gyear  = " + SQLString.Format(box.getString("p_gyear"));
				sql+= " order by grseq ";
			}	           
		} else if (v_callmethod.equals("getUpperClass")) {
			sql = "select c.upperclass code, c.classname name ";
			sql+= "  from vz_subjcourseseq  a, ";
			sql+= "      (select subj, upperclass ";
			sql+= "         from TZ_SUBJ ";
			sql+= "        union all ";
			sql+= "       select course, upperclass ";
			sql+= "         from TZ_COURSE) b, ";
			sql+= "       TZ_SUBJATT    c  ";
			sql+= " where a.subj = b.subj ";
			sql+= "   and b.upperclass = c.upperclass ";
			if (!box.getString("p_grcode").equals("ALL")) {
				sql+= "   and a.grcode = " + SQLString.Format(box.getString("p_grcode"));
			}			    	
			if (!box.getString("p_gyear").equals("ALL")) {
				sql+= "   and a.gyear  = " + SQLString.Format(box.getString("p_gyear"));
			}				    
			if (!box.getString("p_grseq").equals("ALL")) {
				sql+= "   and a.grseq  = " + SQLString.Format(box.getString("p_grseq"));
			}				    
			sql+= "   and c.middleclass = '000' ";
			sql+= "   and c.lowerclass  = '000' ";
			sql+= " group by c.upperclass, c.classname";
		} else if (v_callmethod.equals("getSubjCourse")) {
			sql = "select b.subj code, b.subjnm name ";
			sql+= "  from vz_subjcourseseq  a, ";
			sql+= "      (select subj, upperclass, subjnm ";
			sql+= "         from TZ_SUBJ ";
			sql+= "        union all ";
			sql+= "       select course, upperclass, coursenm ";
			sql+= "         from TZ_COURSE) b, ";
			sql+= "       TZ_SUBJATT    c  ";
			sql+= " where a.subj = b.subj ";
			sql+= "   and b.upperclass    = c.upperclass ";
			if (!box.getString("p_grcode").equals("ALL")) {
				sql+= "   and a.grcode = " + SQLString.Format(box.getString("p_grcode"));
			}else{
				if (v_opt.equals("H")){
					sql+= "  and a.grcode in (select distinct grcode from tz_grcodeman "
						+ " where grcode="+SQLString.Format(box.getString("p_grcode"))
						+"    and userid="+SQLString.Format(s_userid) + ")";
				}else if (v_opt.equals("K")){
					v_comp = GetCodenm.get_compval(GetCodenm.get_comp_userid(s_userid));
					sql+= "  and a.grcode in (select distinct c.grcode  tz_compman bb, tz_grcomp cc "
						+ " where cc.grcode="+SQLString.Format(box.getString("p_grcode"))
						+"    and bb.userid="+SQLString.Format(s_userid)
						+ "   and cc.comp like "+SQLString.Format(v_comp)
						+ "   and bb.comp like "+SQLString.Format(v_comp)+") ";
				}else if (v_opt.equals("P")){
					sql+= "  and a.grcode in (select distinct grcode from tz_subjseq bb, tz_classtutor cc"
						+ " where bb.grcode="+SQLString.Format(box.getString("p_grcode"))
						+ "   and bb.subj=cc.subj and bb.year=cc.year and bb.subjseq=cc.subjseq "
						+ "   and cc.userid="+SQLString.Format(s_userid) + ")";
				}							    	
			}
			if (!box.getString("p_gyear").equals("ALL")) {
				sql+= "   and a.gyear  = " + SQLString.Format(box.getString("p_gyear"));
			}				    
			if (!box.getString("p_grseq").equals("ALL")) {
				sql+= "   and a.grseq  = " + SQLString.Format(box.getString("p_grseq"));
			}				    
			if (!box.getString("p_uclass").equals("ALL")) {
				sql+= "   and b.upperclass  = " + SQLString.Format(box.getString("p_uclass"));
			}				    
			sql+= "   and c.middleclass = '000' ";
			sql+= "   and c.lowerclass  = '000' ";
			sql+= " group by b.subj, b.subjnm ";
		} else if (v_callmethod.equals("getSubjSeq")) {
			if (box.getString("p_gyear").equals("ALL") || box.getString("p_subjcourse").equals("ALL")) {
				sql = "ALL";
			} else {
				sql = "select a.subjseq code, a.subjseq name ";
				sql+= "  from vz_subjcourseseq  a, ";
				sql+= "      (select subj, upperclass ";
				sql+= "         from TZ_SUBJ ";
				sql+= "        union all ";
				sql+= "       select course, upperclass ";
				sql+= "         from TZ_COURSE) b, ";
				sql+= "       TZ_SUBJATT    c  ";
				sql+= " where a.subj       = b.subj ";
				sql+= "   and b.upperclass = c.upperclass ";
				if (!box.getString("p_grcode").equals("ALL")) {
					sql+= "   and a.grcode     = " + SQLString.Format(box.getString("p_grcode"));
				}
				sql+= "   and a.gyear      = " + SQLString.Format(box.getString("p_gyear"));
				if (!box.getString("p_grseq").equals("ALL")) {
					sql+= "   and a.grseq      = " + SQLString.Format(box.getString("p_grseq"));
				}
				if (!box.getString("p_uclass").equals("ALL")) {
					sql+= "   and b.upperclass     = " + SQLString.Format(box.getString("p_uclass"));
				}
				sql+= "   and c.middleclass = '000' ";
				sql+= "   and c.lowerclass  = '000' ";
				sql+= "   and a.subj = " + SQLString.Format(box.getString("p_subjcourse"));
			}
		} else if (v_callmethod.equals("getGrcomp")) {
			if (box.getString("p_grcode").equals("ALL")) {
				sql = "select a.comp code, a.companynm name ";
				sql+= "  from tz_comp   a  ";
				sql+= " where comptype = 2 ";				
			} else {
				/*
				sql = "select distinct a.comp code, a.companynm name ";
				sql+= "  from tz_comp   a,  ";
				sql+= "       tz_grcomp b   ";
				sql+= " where b.grcode     = " + SQLString.Format(box.getString("p_grcode"));
				sql+= "   and a.comp like decode(////////////////////) ";
				*/
				v_comp = GetCodenm.get_compval(GetCodenm.get_comp_userid(s_userid));System.out.println("GetCodenm.get_compval : " + v_comp);
				sql = "select distinct comp code, compnm name from tz_comp a " 
					+ " where ( groups in ( select groups from tz_comp aa, tz_grcomp b where aa.comp=b.comp and aa.comptype=1) " 
					+ "        or groups + company in (select groups + company from tz_comp aa, tz_grcomp b where aa.comp=b.comp and aa.comptype=2))"
					+ "   and  a.comp like "+SQLString.Format(v_comp);
			}
			sql+= "  order by a.comp  ";
		} else if (v_callmethod.equals("getUpperClass2")) {
			sql = "select upperclass code, classname name ";
			sql+= "  from TZ_SUBJATT  ";
			sql+= " where middleclass = '000' ";
			sql+= "   and lowerclass  = '000' ";
			if (box.getString("p_not_course").equals("Y")) {
				sql+= "   and upperclass  != 'COUR' ";
			}
			sql+= " order by upperclass";
			System.out.println("getUpperClass2 selectbox sql ==> " + sql );
		} else if (v_callmethod.equals("getOffUpperClass2")) {
			sql = "select upperclass code, classname name ";
			sql+= "  from TZ_OFFSUBJATT  ";
			sql+= " where middleclass = '000' ";
			sql+= "   and lowerclass  = '000' ";
			if (box.getString("p_not_course").equals("Y")) {
				sql+= "   and upperclass  != 'COUR' ";
			}
			sql+= " order by upperclass";
			System.out.println("getOffUpperClass2 selectbox sql ==> " + sql );
		} else if (v_callmethod.equals("getMiddleClass")) {
			sql = "select middleclass code, classname name ";
			sql+= "  from TZ_SUBJATT  ";
			sql+= " where upperclass = " + SQLString.Format(box.getString("p_upperclass"));
			sql+= "   and middleclass != '000' "; 
			sql+= "   and lowerclass  = '000' ";
			sql+= " order by middleclass";
		} else if (v_callmethod.equals("getOffMiddleClass")) {
			sql = "select middleclass code, classname name ";
			sql+= "  from TZ_OFFSUBJATT  ";
			sql+= " where upperclass = " + SQLString.Format(box.getString("p_upperclass"));
			sql+= "   and middleclass != '000' "; 
			sql+= "   and lowerclass  = '000' ";
			sql+= " order by middleclass";
		} else if (v_callmethod.equals("getLowerClass")) {
			sql = "select lowerclass code, classname name ";
			sql+= "  from TZ_SUBJATT  ";
			sql+= " where upperclass  = " + SQLString.Format(box.getString("p_upperclass"));
			sql+= "   and middleclass = " + SQLString.Format(box.getString("p_middleclass"));
			sql+= " order by lowerclass";
		} else if (v_callmethod.equals("getLevel1Code")) {
			sql = "select code code, codenm name ";
			sql+= "  from TZ_CODE  ";
			sql+= " where gubun  = " + SQLString.Format(box.getString("p_gubun"));
			sql+= " order by code";
		} else if (v_callmethod.equals("getUpperClassSubjCode")) {
			sql = "select subj code, subjnm name ";
			sql+= "  from TZ_SUBJ  ";
//			if (!box.getString("p_upperclass").equals("ALL")) {
				sql+= " where upperclass = " + SQLString.Format(box.getString("p_upperclass")); 				
//			}	
			if (v_opt.equals("F") || v_opt.equals("P")) {  // 과정관리자, 강사
				sql+= "   and subj in ( select subj from tz_subjman ";
				sql+= "                    where userid = "+SQLString.Format(s_userid); 
				sql+= "                      and gadmin = "+SQLString.Format(s_gadmin);   
				sql+= "                   union all " ;  
				sql+= "                   select b.course from tz_subjman a, tz_coursesubj b ";
				sql+= "                    where a.userid = "+SQLString.Format(s_userid);
				sql+= "                      and a.gadmin = "+SQLString.Format(s_gadmin) + ")";  
				
			} else if (v_opt.equals("H")) {  // 교육그룹관리자
				sql+= "   and subj in (select subjcourse from tz_grsubj "; 
				sql+= "                   where grcode in (select grcode from tz_grcodeman ";
				sql+= "                                     where userid = "+SQLString.Format(s_userid);
				sql+= "                                       and gadmin = "+SQLString.Format(s_gadmin) + "))";
				
			} 

			sql+= " order by code";
		} else if (v_callmethod.equals("getOTUpperClassSubjCode")) {
			sql = "select subj code, subjnm name ";
			sql+= "  from TZ_OTSUBJ  ";
			if (!box.getString("p_upperclass").equals("ALL")) {
				sql+= " where upperclass = " + SQLString.Format(box.getString("p_upperclass")); 				
			}	
			sql+= " order by code";
		} else if (v_callmethod.equals("getSubjClass")) {
			sql = "select class code, classnm name";
			sql+= "  from tz_class " ;
			sql+= " where subj    = " + SQLString.Format(box.getString("p_subj"));
			sql+= "   and year    = " + SQLString.Format(box.getString("p_year"));
			sql+= "   and subjseq = " + SQLString.Format(box.getString("p_subjseq"));
			sql+= " order by class ";
		} else if (v_callmethod.equals("getSubjTutor")) {
			sql = "select b.userid code, b.name name ";
			sql+= "  from tz_subjman  a, ";
			sql+= "       tz_tutor    b, ";
			sql+= "       (select subj, year, subjseq, class, tuserid  ";
			sql+= "          from tz_classtutor  ";
			sql+= "         where subj    = " + SQLString.Format(box.getString("p_subj")); 
			sql+= "           and year    = " + SQLString.Format(box.getString("p_year"));
			sql+= "           and subjseq = " + SQLString.Format(box.getString("p_subjseq"));
			sql+= "           and class   = " + SQLString.Format(box.getStringDefault("p_class","0000001")); 
			sql+= "           and ttype   = 'M') c ";
			sql+= " where a.userid = b.userid  ";
			sql+= "   and a.gadmin = 'P1' ";
			// 2005.11.02 : 하경태
			//sql+= "   and a.userid = c.tuserid(+) ";
			//sql+= "   and a.subj   = c.subj(+) ";
			sql+= "   and a.userid = c.tuserid(+) ";
			sql+= "   and a.subj   = c.subj(+) ";
			sql+= "   and a.subj   = " + SQLString.Format(box.getString("p_subj"));
			sql+= " order by b.userid, b.name ";
		} else if (v_callmethod.equals("getSelText")) {
			if (box.getString("p_grpcomp").length() >= 4) { 
				if (box.getString("p_selgubun").equals(SelectionUtil.JIKUN)) {
					sql = "select jikun code, jikunnm name";
					sql+= "  from tz_jikun " ;
					sql+= " where grpcomp = " + SQLString.Format(box.getString("p_grpcomp").substring(0,4));
					sql+= " order by jikun ";
				} else if (box.getString("p_selgubun").equals(SelectionUtil.JIKUP)) {
					sql = "select jikup code, jikupnm name";
					sql+= "  from tz_jikup " ;
					sql+= " where grpcomp = " + SQLString.Format(box.getString("p_grpcomp").substring(0,4));
					sql+= " order by jikup ";
				} else if (box.getString("p_selgubun").equals(SelectionUtil.GPM)) {





					sql = "select gpm code, gpmnm name";
					sql+= "  from tz_comp " ;
					sql+= " where groups   = " + SQLString.Format(box.getString("p_grpcomp").substring(0,2));
					sql+= "   and company  = " + SQLString.Format(box.getString("p_grpcomp").substring(2,4));
					sql+= "   and comptype = 3 ";
					sql+= " order by gpm ";





				} else {
					sql = "ALL"; 
				}
			} else {
				sql = "ALL"; 
			}
			
		} else if (v_callmethod.equals("getSelDept")) {
			if (box.getString("p_grpcomp").length() >= 4) { 
				if (box.getString("p_selgubun").equals(SelectionUtil.GPM)) {
					
					
					
					if (box.getString("p_selgpm").equals("ALL")) {
						sql = "ALL"; 
					} else {
						sql = "select dept code, deptnm name";
						sql+= "  from tz_comp " ;
						sql+= " where groups   = " + SQLString.Format(box.getString("p_grpcomp").substring(0,2));
						sql+= "   and company  = " + SQLString.Format(box.getString("p_grpcomp").substring(2,4));
						sql+= "   and gpm      = " + SQLString.Format(box.getString("p_selgpm"));
						sql+= "   and comptype = 4 ";
						sql+= " order by dept ";
					}
					
					
					
					
				} else {
					sql = "ALL"; 
				}
			} else {
				sql = "ALL"; 
			}
		} else if (v_callmethod.equals("getGrpcomp")) {
			if (box.getString("p_grcode").equals("ALL")) {
				sql = "select a.groups || a.company code, a.companynm name ";
				sql+= "  from tz_comp   a  ";
				sql+= " where comptype = 2 ";
				if (s_gadmin.equals("K2")) {
                    sql+= " and SUBSTR(comp,0,4) = (select SUBSTR(comp,0,4) from tz_compman where gadmin = 'K2' and userid = " + SQLString.Format(s_userid)+")";
				}
			} else {
				sql = "select a.groups || a.company code, a.companynm name ";
				sql+= "  from tz_comp   a,  ";
				sql+= "       tz_grcomp b   ";
				sql+= " where a.comp = b.comp  ";
				sql+= "   and b.grcode     = " + SQLString.Format(box.getString("p_grcode"));
				sql+= "   and a.comptype = 2 ";
			}
			sql+= "  order by a.comp  ";
		} else if (v_callmethod.equals("getSulpapernum")) {
			if (box.getString("p_subj").equals("COMMON")) {
				sql = "select to_char(sulpapernum) code, sulpapername name ";
				sql+= "  from tz_sulpaper ";
//				sql+= " where grcode  = " + SQLString.Format(box.getString("p_grcode")); 
				sql+= " where 1 = 1 "; 
				sql+= "   and subj    = " + SQLString.Format(box.getString("p_subj"));
				sql+= " order by  sulpapernum ";
			} else if (box.getString("p_subj").equals("WHOLE")) {
				sql = "select to_char(a.sulpapernum) code, a.sulpapername name ";
				sql+= "  from tz_sulpaper  a, ";
				sql+= "       tz_sulpaper_whole  b ";
				sql+= " where a.grcode = b.grcode ";
				sql+= "   and a.subj   = b.subj   ";
				sql+= "   and a.sulpapernum = b.sulpapernum "; 
				sql+= "   and a.grcode = " + SQLString.Format(box.getString("p_grcode"));
				sql+= "   and SUBSTR(b.startdate,1,4) = " + SQLString.Format(box.getString("p_sulyear"));
				sql+= " order by a.sulpapernum ";
			} else {
				sql = "select to_char(sulpapernum) code, sulpapername name ";
				sql+= "  from tz_sulpaper ";
//				sql+= " where grcode  = " + SQLString.Format(box.getString("p_grcode"));
				sql+= " where 1 = 1 ";
				sql+= "   and subj in ( select subj ";
				sql+= "  				  from tz_subjseq ";
				sql+= "                  where subj = " + SQLString.Format(box.getString("p_subj"));
				if (!box.getString("p_grcode").equals("ALL")) {
					sql+= "  			   and grcode  = " + SQLString.Format(box.getString("p_grcode"));
				}
				if (!box.getString("p_gyear").equals("ALL")) {
					sql+= " 		   	   and gyear   = " + SQLString.Format(box.getString("p_gyear"));
				}
				if (!box.getString("p_grseq").equals("ALL")) {
					sql+= "  			   and grseq   = " + SQLString.Format(box.getString("p_grseq"));
				}
				sql+= "  			    group by subj )";
			}
		} else if (v_callmethod.equals("getSulmunSubj")) {
			sql = "select b.subj code, b.subjnm name ";
			sql+= "    from tz_subj    a, ";
			sql+= "  	    tz_subjseq b  ";
			sql+= "   where a.subj = b.subj ";
			if (!box.getString("p_grcode").equals("ALL")) {
				sql+= "   and b.grcode = " + SQLString.Format(box.getString("p_grcode"));
			}			    	
			if (!box.getString("p_gyear").equals("ALL")) {
				sql+= "   and b.gyear  = " + SQLString.Format(box.getString("p_gyear"));
			}				    
			if (!box.getString("p_grseq").equals("ALL")) {
				sql+= "   and b.grseq  = " + SQLString.Format(box.getString("p_grseq"));
			}				    
			if (!box.getString("p_uclass").equals("ALL")) {
				sql+= "   and a.upperclass  = " + SQLString.Format(box.getString("p_uclass"));
			}				    
			sql+= " group by b.subj, b.subjnm ";
		} else if (v_callmethod.equals("getWholeSulyear")) {
			sql = "select SUBSTR(b.startdate,1,4) code, SUBSTR(b.startdate,1,4) name";
			sql+= "  from tz_sulpaper        a, ";
			sql+= "       tz_sulpaper_whole  b ";
			sql+= " where a.grcode = b.grcode ";
			sql+= "   and a.subj   = b.subj   ";
			sql+= "   and a.sulpapernum = b.sulpapernum "; 
			sql+= "   and a.grcode = " + SQLString.Format(box.getString("p_grcode"));
			sql+= "   and a.subj   = 'WHOLE' ";
			sql+= " group by SUBSTR(b.startdate,1,4) ";
		} else if (v_callmethod.equals("getSubjlesson")) {
			sql = "select lesson code, lesson || '  ' || sdesc name";
			sql+= "  from tz_subjlesson  ";
			sql+= " where subj = " + SQLString.Format(box.getString("p_subj"));
			sql+= " order by lesson ";
		} else if (v_callmethod.equals("getBeforePaper")) {
			sql = "select a.subj || ':' || a.year || ':' || a.subjseq || ':' || a.lesson || ':' || a.ptype || ':' || a.branch || ':' || a.papernum  code, ";
			sql+= "		  a.year || '-' || a.subjseq name ";
			sql+= "  from tz_exampaper  a ";
			sql+= " where a.subj   = " + SQLString.Format(box.getString("p_subj"));
			sql+= "   and a.lesson = " + SQLString.Format(box.getString("p_lesson"));
			sql+= "   and a.ptype  = " + SQLString.Format(box.getString("p_ptype"));
			sql+= " order by a.year, a.subjseq ";
		} else if (v_callmethod.equals("getOTBeforePaper")) {
			sql = "select a.subj || ':' || a.year || ':' || a.subjseq || ':' || a.papernum  code, ";
			sql+= "		  a.year || '-' || a.subjseq || '-' || a.papernum   name ";
			sql+= "  from tz_exampaper  a ";
			sql+= " where a.subj   = " + SQLString.Format(box.getString("p_subj"));
			sql+= " order by a.year, a.year, a.subjseq ";
		} else if (v_callmethod.equals("getSubjYear")) {
			sql = "select b.year code, b.year name";
			sql+= "  from tz_subj     a, ";
			sql+= "       tz_subjseq  b  ";
			sql+= " where a.subj = b.subj  ";
			if (!box.getString("p_upperclass").equals("ALL")) {
				sql+= "   and a.upperclass = " + SQLString.Format(box.getString("p_upperclass"));
			}
			if (!box.getString("p_subj").equals("ALL")) {
				sql+= "   and a.subj = " + SQLString.Format(box.getString("p_subj"));
			}
			sql+= " group by b.year ";
		} else if (v_callmethod.equals("getSubjseq")) {
			sql = "select b.subjseq code, b.subjseq name";
			sql+= "  from tz_subj     a, ";
			sql+= "       tz_subjseq  b  ";
			sql+= " where a.subj = b.subj  ";
			if (!box.getString("p_upperclass").equals("ALL")) {
				sql+= "   and a.upperclass = " + SQLString.Format(box.getString("p_upperclass"));
			}
			if (!box.getString("p_subj").equals("ALL")) {
				sql+= "   and a.subj = " + SQLString.Format(box.getString("p_subj"));
			}
			if (!box.getString("p_year").equals("ALL")) {
				sql+= "   and b.year = " + SQLString.Format(box.getString("p_year"));
			}
			sql+= " group by b.subjseq ";
		} else if (v_callmethod.equals("getOTSubjYear")) {
			sql = "select b.year code, b.year name";
			sql+= "  from tz_otsubj      a, ";
			sql+= "       tz_exammaster  b  ";
			sql+= " where a.subj = b.subj  ";
			if (!box.getString("p_upperclass").equals("ALL")) {
				sql+= "   and a.upperclass = " + SQLString.Format(box.getString("p_upperclass"));
			}
			if (!box.getString("p_subj").equals("ALL")) {
				sql+= "   and a.subj = " + SQLString.Format(box.getString("p_subj"));
			}
			sql+= " group by b.year ";
		} else if (v_callmethod.equals("getOTSubjseq")) {
			sql = "select b.subjseq code, b.subjseq name";
			sql+= "  from tz_otsubj      a, ";
			sql+= "       tz_exammaster  b  ";
			sql+= " where a.subj = b.subj  ";
			if (!box.getString("p_upperclass").equals("ALL")) {
				sql+= "   and a.upperclass = " + SQLString.Format(box.getString("p_upperclass"));
			}
			if (!box.getString("p_subj").equals("ALL")) {
				sql+= "   and a.subj = " + SQLString.Format(box.getString("p_subj"));
			}
			if (!box.getString("p_year").equals("ALL")) {
				sql+= "   and b.year = " + SQLString.Format(box.getString("p_year"));
			}
			sql+= " group by b.subjseq ";
		} else if (v_callmethod.equals("getOffSubj")) {
			sql = "select b.subj code, b.subjnm name";
			sql+= "  from tz_subj     a, ";
			sql+= "       tz_subjseq  b  ";
			sql+= " where a.subj = b.subj  ";
			if (!box.getString("p_grcode").equals("ALL")) {
				sql+= "   and b.grcode = " + SQLString.Format(box.getString("p_grcode"));
			}
			if (!box.getString("p_gyear").equals("ALL")) {
				sql+= "   and b.gyear  = " + SQLString.Format(box.getString("p_gyear"));
			}
			if (!box.getString("p_grseq").equals("ALL")) {
				sql+= "   and b.grseq  = " + SQLString.Format(box.getString("p_grseq"));
			}
			sql+= "   and a.isonoff = 'OFF' ";
			sql+= " group by b.subj, b.subjnm ";
		} else if (v_callmethod.equals("getOffSubjseq")) {
			sql = "select b.subjseq code, b.subjseq name";
			sql+= "  from tz_subj     a, ";
			sql+= "       tz_subjseq  b  ";
			sql+= " where a.subj = b.subj  ";
			if (!box.getString("p_grcode").equals("ALL")) {
				sql+= "   and b.grcode = " + SQLString.Format(box.getString("p_grcode"));
			}
			if (!box.getString("p_gyear").equals("ALL")) {
				sql+= "   and b.gyear  = " + SQLString.Format(box.getString("p_gyear"));
			}
			if (!box.getString("p_grseq").equals("ALL")) {
				sql+= "   and b.grseq  = " + SQLString.Format(box.getString("p_grseq"));
			}
			if (!box.getString("p_grseq").equals("ALL")) {
				sql+= "   and b.grseq  = " + SQLString.Format(box.getString("p_grseq"));
			}
			if (!box.getString("p_offsubj").equals("ALL")) {
				sql+= "   and b.subj  = " + SQLString.Format(box.getString("p_offsubj"));
			}
			sql+= "   and a.isonoff = 'OFF' ";
			sql+= " group by b.subjseq  ";
		}
		return sql;
	}

	/**
	Selection코드 리스트
	@param box        receive from the form object and session
	@return ArrayList Selection코드 리스트
	*/
	public ArrayList getSelectionList2(RequestBox box) throws Exception {
		
		ArrayList list = generateSelectionList(box);   
		return list;
	}

	/**
	Selection코드 리스트
	@param sql          Query String
	@return ArrayList   Selection코드 리스트
	*/
	public ArrayList getSelectionList2(String sql, String all) throws Exception {
		ArrayList     list = null;
		SelectionData data = null;
		list = new ArrayList();
		data = new SelectionData();
        
		if (all.equals("ALL") || sql.equals("ALL")) {
			data=new SelectionData();
			data.setCode("ALL");
			data.setName("ALL");
			list.add(data);
		}

		if (!sql.equals("ALL")) {
			DBConnectionManager connMgr = null;
			ListSet   ls    = null;
			try {

				connMgr = new DBConnectionManager();
				ls = connMgr.executeQuery(sql);
				while (ls.next()) {
					data=new SelectionData();
					data.setCode(ls.getString("code"));
					data.setName(ls.getString("name"));
					list.add(data);
				}
			}
			catch (Exception ex) {
				ErrorManager.getErrorStackTrace(ex, null, sql);
				throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
			}
			finally {
				if(ls != null) { try { ls.close(); }catch (Exception e) {} }
				if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
			}
		}
		return list;
	}

	/**
	Query String
	@param box        receive from the form object and session
	@return String    Query String
	A1	ultra
	A2	super
	F1	과정관리
	H1	교육그룹
	K8	부서관리
	K9	부서장
	P1	강사
	회사정보 tz_grcompman
	교육그룹 tz_grcodeman
	과정정보 tz_subjman

	K1-그룹담당자, 
	K2-회사담당자, 
	K4-GPM담당자,
	K6-부서담당자,
	K7-부서장, 
	K8-파트담당자
	여기에 쓰는건 K2 , K6,K7 이 되겟네요

	A -  
	F - 과정관리자
	H - 교육그룹 
	K - 회사담당
	P - 강사 
	*/
	public ArrayList generateSelectionList(RequestBox box) throws Exception {
		ArrayList list = null; 

		String sql = "";
		String all = "";
		
		String s_gadmin = box.getSession("gadmin");
		String s_userid = box.getSession("userid");
		String v_opt	= "";
		if (s_gadmin.length() > 0) {  
			v_opt = s_gadmin.substring(0,1);
		}
//		String v_comp	= "";
		
		String v_callmethod = box.getString("p_callmethod");//System.out.println("v_callmethod : " + v_callmethod);
		if (v_callmethod.equals("getGrcode")) {
			if (v_opt.equals("A")) {
				sql = "select a.grcode code, a.grcodenm name"
					+ "  from TZ_GRCODE  a"
				    + " order by a.grcode ";

				all = "ALL";
				list = getSelectionList2(sql, all);
			} else if (v_opt.equals("H")) {
				sql = "select distinct a.grcode code, a.grcodenm name"  
				    + "  from tz_grcode    a, " 
				    + "       tz_grcodeman b " 
				    + " where a.grcode = b.grcode " 
				    + "   and b.userid = "+SQLString.Format(s_userid) 
				    + "   and b.gadmin = "+SQLString.Format(s_gadmin)
				    + " order by a.grcode ";
					
				all = "";
				list = getSelectionList2(sql, all);
			} else if (v_opt.equals("K")) {
				list = getGrcodeCompman(s_userid, s_gadmin);
			} else if (v_opt.equals("P")){
				sql+= " select distinct a.grcode code,  a.grcodenm name "
				    + "   from tz_grcode     a, " 
				    + "        tz_subjseq    b, "
				    + "        tz_classtutor c  "
				    + "  where a.grcode  = b.grcode " 
				    + "    and b.subj    = c.subj " 
			        + "    and b.year    = c.year "
			        + "    and b.subjseq = c.subjseq "
				    + "    and c.tuserid = " + SQLString.Format(s_userid)
				    + "  order by a.grcode ";

				all = "";
				list = getSelectionList2(sql, all);
			} else if (v_opt.equals("F")) {
				sql+= " select distinct a.grcode code, a.grcodenm name " 
				    + "   from tz_grcode  a, "
				    + "        tz_grsubj  b, "
				    + "        (select course  subjcourse, subj "
				    + "   		  from tz_coursesubj "
				    + "   		union "
				    + "   		select subj subjcourse, subj "
				    + "   		  from tz_subj) c, "
				    + "   	   tz_subjman d "
				    + "  where a.grcode     = b.grcode "
				    + "    and b.subjcourse = c.subjcourse "
				    + "    and c.subj       = d.subj "
				    + "    and d.userid     = " + SQLString.Format(s_userid)
				    + "    and d.gadmin     = " + SQLString.Format(s_gadmin)
				    + "  order by a.grcode ";
				
				all = "";
				list = getSelectionList2(sql, all);
			}
		} else if (v_callmethod.equals("getGyear")) {
			sql = "select gyear code, gyear name";
			sql+= "  from TZ_GRSEQ ";
			if (!box.getString("p_grcode").equals("ALL")) {	        
				sql+= " where grcode = " + SQLString.Format(box.getString("p_grcode"));
			}	            
			sql+= " group by gyear ";
			sql+= " order by gyear desc";

			all = "";

			list = getSelectionList2(sql, all);
		} else if (v_callmethod.equals("getGrseq")) {
			if (box.getString("p_grcode").equals("ALL") || box.getString("p_gyear").equals("ALL")) {
				sql = "ALL";
			} else {
				sql = "select grseq code, grseqnm name";
				sql+= "  from TZ_GRSEQ ";
				sql+= " where grcode = " + SQLString.Format(box.getString("p_grcode"));
				sql+= "   and gyear  = " + SQLString.Format(box.getString("p_gyear"));
				sql+= " order by grseq ";
			}	           

			all = "ALL";

			list = getSelectionList2(sql, all);
		} else if (v_callmethod.equals("getUpperClass")) {
			sql = "select c.upperclass code, c.classname name ";
			sql+= "  from vz_subjcourseseq  a, ";
			sql+= "      (select subj, upperclass ";
			sql+= "         from TZ_SUBJ ";
			sql+= "        union all ";
			sql+= "       select course, upperclass ";
			sql+= "         from TZ_COURSE) b, ";
			sql+= "       TZ_SUBJATT    c  ";
			sql+= " where a.subj = b.subj ";
			sql+= "   and b.upperclass = c.upperclass ";
			if (!box.getString("p_grcode").equals("ALL")) {
				sql+= "   and a.grcode = " + SQLString.Format(box.getString("p_grcode"));
			}			    	
			if (!box.getString("p_gyear").equals("ALL")) {
				sql+= "   and a.gyear  = " + SQLString.Format(box.getString("p_gyear"));
			}				    
			if (!box.getString("p_grseq").equals("ALL")) {
				sql+= "   and a.grseq  = " + SQLString.Format(box.getString("p_grseq"));
			}				    
			sql+= "   and c.middleclass = '000' ";
			sql+= "   and c.lowerclass  = '000' ";
			sql+= " group by c.upperclass, c.classname";

			all = "ALL";

			list = getSelectionList2(sql, all);
		} else if (v_callmethod.equals("getSubjCourse")) {
			sql = "select b.subj code, b.subjnm name ";
			sql+= "  from vz_subjcourseseq  a, ";
			sql+= "      (select subj, upperclass, subjnm ";
			sql+= "         from TZ_SUBJ ";
			sql+= "        union all ";
			sql+= "       select course, upperclass, coursenm ";
			sql+= "         from TZ_COURSE) b, ";
			sql+= "       TZ_SUBJATT    c  ";
			sql+= " where a.subj = b.subj ";
			sql+= "   and b.upperclass    = c.upperclass ";
			if (!box.getString("p_grcode").equals("ALL")) {
				sql+= "   and a.grcode = " + SQLString.Format(box.getString("p_grcode"));
			}
			if (!box.getString("p_gyear").equals("ALL")) {
				sql+= "   and a.gyear  = " + SQLString.Format(box.getString("p_gyear"));
			}				    
			if (!box.getString("p_grseq").equals("ALL")) {
				sql+= "   and a.grseq  = " + SQLString.Format(box.getString("p_grseq"));
			}				    
			if (!box.getString("p_uclass").equals("ALL")) {
				sql+= "   and b.upperclass  = " + SQLString.Format(box.getString("p_uclass"));
			}
			sql+= "   and c.middleclass = '000' ";
			sql+= "   and c.lowerclass  = '000' ";
                        all = "ALL";
			if (v_opt.equals("F") || v_opt.equals("P")) {  // 과정관리자, 강사
				sql+= "   and b.subj in ( select subj from tz_subjman ";
				sql+= "                    where userid = "+SQLString.Format(s_userid); 
				sql+= "                      and gadmin = "+SQLString.Format(s_gadmin);   
				sql+= "                   union all " ;  
				sql+= "                   select b.course from tz_subjman a, tz_coursesubj b ";
				sql+= "                    where a.userid = "+SQLString.Format(s_userid);
				sql+= "                      and a.gadmin = "+SQLString.Format(s_gadmin) + ")";  
				
				all = "";
			} else if (v_opt.equals("H")) {  // 교육그룹관리자
				sql+= "   and b.subj in (select subjcourse from tz_grsubj "; 
				sql+= "                   where grcode in (select grcode from tz_grcodeman ";
				sql+= "                                     where userid = "+SQLString.Format(s_userid);
				sql+= "                                       and gadmin = "+SQLString.Format(s_gadmin) + "))";
				
				all = "ALL";
			} else if (v_opt.equals("K")) {  // 회사관리자
				String     v_grcodes  = "";
				ArrayList  grcodelist = null;
				SelectionData    data = null;
				grcodelist = getGrcodeCompman(s_userid, s_gadmin);
				for (int i=0; i<grcodelist.size(); i++) {
					data = (SelectionData)grcodelist.get(i);
					v_grcodes+=SQLString.Format(data.getCode());
					if (i != (grcodelist.size()-1)) {
						v_grcodes+=",";	
					}
				}
				sql+= "   and b.subj in (select subjcourse from tz_grsubj where grcode in (" + v_grcodes + ")) ";
				
				all = "ALL";
			}
			sql+= " group by b.subj, b.subjnm ";
/*			
				if (v_opt.equals("H")){
					sql+= "  and a.grcode in (select distinct grcode from tz_grcodeman "
						+ " where grcode="+SQLString.Format(box.getString("p_grcode"))
						+"    and userid="+SQLString.Format(s_userid) + ")";
				}else if (v_opt.equals("K")){
					v_comp = GetCodenm.get_compval(GetCodenm.get_comp_userid(s_userid));
					sql+= "  and a.grcode in (select distinct c.grcode  tz_compman bb, tz_grcomp cc "
						+ " where cc.grcode="+SQLString.Format(box.getString("p_grcode"))
						+"    and bb.userid="+SQLString.Format(s_userid)
						+ "   and cc.comp like "+SQLString.Format(v_comp)
						+ "   and bb.comp like "+SQLString.Format(v_comp)+") ";
				}else if (v_opt.equals("P")){
					sql+= "  and a.grcode in (select distinct grcode from tz_subjseq bb, tz_classtutor cc"
						+ " where bb.grcode="+SQLString.Format(box.getString("p_grcode"))
						+ "   and bb.subj=cc.subj and bb.year=cc.year and bb.subjseq=cc.subjseq "
						+ "   and cc.userid="+SQLString.Format(s_userid) + ")";
				}							    	
*/

		//	all = "ALL";
//System.out.println("sql : " + sql);
			list = getSelectionList2(sql, all);
			
		} else if (v_callmethod.equals("getSubjSeq")) {
			if (box.getString("p_gyear").equals("ALL") || box.getString("p_subjcourse").equals("ALL")) {
				sql = "ALL";
			} else {
				sql = "select a.subjseq code, a.subjseq name ";
				sql+= "  from vz_subjcourseseq  a, ";
				sql+= "      (select subj, upperclass ";
				sql+= "         from TZ_SUBJ ";
				sql+= "        union all ";
				sql+= "       select course, upperclass ";
				sql+= "         from TZ_COURSE) b, ";
				sql+= "       TZ_SUBJATT    c  ";
				sql+= " where a.subj       = b.subj ";
				sql+= "   and b.upperclass = c.upperclass ";
				if (!box.getString("p_grcode").equals("ALL")) {
					sql+= "   and a.grcode     = " + SQLString.Format(box.getString("p_grcode"));
				}
				sql+= "   and a.gyear      = " + SQLString.Format(box.getString("p_gyear"));
				if (!box.getString("p_grseq").equals("ALL")) {
					sql+= "   and a.grseq      = " + SQLString.Format(box.getString("p_grseq"));
				}
				if (!box.getString("p_uclass").equals("ALL")) {
					sql+= "   and b.upperclass     = " + SQLString.Format(box.getString("p_uclass"));
				}
				sql+= "   and c.middleclass = '000' ";
				sql+= "   and c.lowerclass  = '000' ";
				sql+= "   and a.subj = " + SQLString.Format(box.getString("p_subjcourse"));
			}

			all = "ALL";

			list = getSelectionList2(sql, all);
		} else if (v_callmethod.equals("getGrcomp")) {
			/*
			K2-회사담당자, 
			K6-부서담당자,
			K7-부서장, 
			여기에 쓰는건 K2 , K6, K7 이 되겟네요
			*/
			if (v_opt.equals("K")) {
				sql = "select a.groups || a.company || '000000' code,  a.companynm name ";
				sql+= "  from tz_comp    a, ";
				sql+= "	      tz_compman b  ";
				sql+= " where a.comp   = b.comp  ";
				sql+= "   and b.userid = " + SQLString.Format(s_userid);
				sql+= "   and b.gadmin = " + SQLString.Format(s_gadmin);
			//	sql+= "   and a.comptype = '4'";
       				
				all = "";
//System.out.println("sql : " + sql); 
				list = getSelectionList2(sql, all);
			} else {
				if (box.getString("p_grcode").equals("ALL")) {
					sql = "select a.comp code, a.companynm name ";
					sql+= "  from tz_comp   a  ";
					sql+= " where a.comptype = '2' ";				
				} else {
					sql = "select distinct a.comp code, a.companynm name ";
					sql+= "  from tz_comp   a,  ";
					sql+= "       tz_grcomp b   ";
					sql+= " where b.grcode     = " + SQLString.Format(box.getString("p_grcode"));
					sql+= "   and a.comptype = '2' ";				
				}
				sql+= "  order by a.comp  ";

				all = "ALL";

				list = getSelectionList2(sql, all);
			}
		} else if (v_callmethod.equals("getSelText")) {
			if (box.getString("p_grpcomp").length() >= 4) { 
				if (box.getString("p_selgubun").equals(SelectionUtil.JIKUN)) {
					sql = "select jikun code, jikunnm name";
					sql+= "  from tz_jikun " ;
					sql+= " where grpcomp = " + SQLString.Format(box.getString("p_grpcomp").substring(0,4));
					sql+= " order by jikun ";

					all = "ALL";
				} else if (box.getString("p_selgubun").equals(SelectionUtil.JIKUP)) {
					sql = "select jikup code, jikupnm name";
					sql+= "  from tz_jikup " ;
					sql+= " where grpcomp = " + SQLString.Format(box.getString("p_grpcomp").substring(0,4));
					sql+= " order by jikup ";

					all = "ALL";
				} else if (box.getString("p_selgubun").equals(SelectionUtil.GPM)) {
					/*
					K2-회사담당자, 
					K6-부서담당자,
					K7-부서장, 
					여기에 쓰는건 K2 , K6, K7 이 되겟네요
					*/
					if (s_gadmin.equals("K6") || s_gadmin.equals("K7")) {
						sql = "select a.gpm code,  a.gpmnm name ";
						sql+= "  from tz_comp    a, ";
						sql+= "	      tz_compman b  ";
						sql+= " where a.comp   = b.comp  ";
						sql+= "   and b.userid = " + SQLString.Format(s_userid);
						sql+= "   and b.gadmin = " + SQLString.Format(s_gadmin);
					//	sql+= "   and a.comptype = '3'";
       				
						all = "";
					} else {
						sql = "select distinct gpm code, gpmnm name";
						sql+= "  from tz_comp " ;
						sql+= " where groups   = " + SQLString.Format(box.getString("p_grpcomp").substring(0,2));
						sql+= "   and company  = " + SQLString.Format(box.getString("p_grpcomp").substring(2,4));
						sql+= "   and comptype = '3' ";
						sql+= " order by gpm ";
					
						all = "ALL";
					}
				
				} else {
					sql = "ALL"; 
				}
			} else {
				sql = "ALL"; 
			}
			
			list = getSelectionList2(sql, all);
			
		} else if (v_callmethod.equals("getSelDept")) {
			if (box.getString("p_grpcomp").length() >= 4) { 
				if (box.getString("p_selgubun").equals(SelectionUtil.GPM)) {
					if (box.getString("p_selgpm").equals("ALL")) {
						sql = "ALL"; 
					} else {
						/*
						K2-회사담당자, 
						K6-부서담당자,
						K7-부서장, 
						여기에 쓰는건 K2 , K6, K7 이 되겟네요
						*/
						if (s_gadmin.equals("K6") || s_gadmin.equals("K7")) {
							sql = "select a.dept code,  a.deptnm name ";
							sql+= "  from tz_comp    a, ";
							sql+= "	      tz_compman b  ";
							sql+= " where a.comp   = b.comp  ";
							sql+= "   and b.userid = " + SQLString.Format(s_userid);
							sql+= "   and b.gadmin = " + SQLString.Format(s_gadmin);
						//	sql+= "   and a.comptype = '4' ";
						
							all = "";
						} else {						
							sql = "select dept code, deptnm name";
							sql+= "  from tz_comp " ;
							sql+= " where groups   = " + SQLString.Format(box.getString("p_grpcomp").substring(0,2));
							sql+= "   and company  = " + SQLString.Format(box.getString("p_grpcomp").substring(2,4));
							sql+= "   and gpm      = " + SQLString.Format(box.getString("p_selgpm"));
							sql+= "   and comptype = '4' ";
							sql+= " order by dept ";

							all = "ALL";
						}
					}
				} else {
					sql = "ALL"; 
				}
			} else {
				sql = "ALL"; 
			}
			
			list = getSelectionList2(sql, all);
		}

		return list;
	}
	
	/**
	Selection코드 리스트
	@param sql          Query String
	@return ArrayList   Selection코드 리스트
	*/
	public ArrayList getGrcodeCompman(String p_userid, String p_gadmin) throws Exception {
		Hashtable     hash = new Hashtable();
		ArrayList     list = new ArrayList();
		SelectionData data = null;
        
		DBConnectionManager connMgr = null;
		ListSet   ls    = null;
		PreparedStatement pstmt = null;
		String    sql1   = ""; 
		String    sql2   = ""; 
		ResultSet rs = null;
		
		String    v_comp = "";
		String    v_code = "";
		String    v_name = "";
		
		try {
			connMgr = new DBConnectionManager();
			
			sql1 = "select comp ";
			sql1+= "  from tz_compman ";
			sql1+= " where userid = " + SQLString.Format(p_userid);
			sql1+= "   and gadmin = " + SQLString.Format(p_gadmin);

			sql2 = "select distinct a.grcode code, a.grcodenm name "; 
			sql2+= "  from tz_grcode a, "; 
			sql2+= "	   tz_grcomp b  ";
			sql2+= " where a.grcode = b.grcode "; 
			sql2+= "   and ? like (select SUBSTR(c.comp,1,(comptype*2)) || '%' ";  
			sql2+= " 			     from tz_comp c ";
            sql2+= "				where c.comp = b.comp) ";

			pstmt = connMgr.prepareStatement(sql2);
			
			ls = connMgr.executeQuery(sql1);
			while (ls.next()) {
				v_comp = ls.getString("comp");
				
				pstmt.setString(1, v_comp);
				
				if(rs != null) { try { rs.close(); }catch (Exception e) {} }
				rs =  pstmt.executeQuery();
				while(rs.next()) {
					v_code = rs.getString("code");
					v_name = rs.getString("name");
					if ((String)hash.get(v_code) == null) {
						hash.put(v_code, v_name);
					}
				}
			}

			Enumeration e = hash.keys();
			while(e.hasMoreElements()) {
				v_code = (String) e.nextElement();
				v_name = (String) hash.get(v_code);
				
				data=new SelectionData();

				data.setCode(v_code);
				data.setName(v_name);
				list.add(data);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql1);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); }catch (Exception e) {} }
			if(rs != null) { try { rs.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}

		return list;
	}
}
