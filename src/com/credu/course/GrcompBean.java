//**********************************************************
//  1. 제      목: 교육그룹대상 회사OPERATION BEAN
//  2. 프로그램명: GrcompBean.java
//  3. 개      요:
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: LeeSuMin 2003. 07. 16
//  7. 수      정:
//**********************************************************
package com.credu.course;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;

public class GrcompBean {
	public String  compTxt = null;
	
	public GrcompBean() {}

	/**
	교육그룹리스트 조회
	@param box          receive from the form object and session
	@return ArrayList   교육그룹리스트
	*/      
	public ArrayList SelectGrcompList(RequestBox box) throws Exception {               
		String v_grcode = box.getString("p_grcode");            
		return SelectGrcompList(box,v_grcode);
	}
	public ArrayList SelectGrcompList(RequestBox box, String p_grcode) throws Exception {               
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list1 = null;
		String sql  = "";
		GrcompData data = null;

		try {
			sql = "select grcode,a.comp,a.indate,a.luserid,a.ldate,compnm,groups,company,gpm,dept,part,"
			    + "       groupsnm,companynm,gpmnm,deptnm,partnm,comptype"
			    + "  from tz_grcomp a, tz_comp b where a.comp=b.comp and grcode=isnull('"+p_grcode+"','')";        

			connMgr = new DBConnectionManager();
			list1 = new ArrayList();
			ls = connMgr.executeQuery(sql);
            
			while (ls.next()) {
				data=new GrcompData();                   
				data.setGrcode(ls.getString("grcode"));             
				data.setComp(ls.getString("comp"));             
				data.setIndate(ls.getString("indate"));             
				data.setLuserid(ls.getString("luserid"));             
				data.setLdate(ls.getString("ldate"));
				data.setCompnm(ls.getString("compnm"));
				data.setGroups(ls.getString("groups"));
				data.setCompany(ls.getString("company"));
				data.setGpm(ls.getString("gpm"));
				data.setDept(ls.getString("dept"));
				data.setPart(ls.getString("part"));
				data.setGroupsnm(ls.getString("groupsnm"));
				data.setCompanynm(ls.getString("companynm"));
				data.setGpmnm(ls.getString("gpmnm"));
				data.setDeptnm(ls.getString("deptnm"));
				data.setPartnm(ls.getString("partnm"));
				data.setComptype(ls.getInt("comptype"));
				
				list1.add(data);
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
	public String getCompTxt(ArrayList lists, String p_grcode){
		GrcompData gd = null;
		compTxt = "";
		int	v_comptype = 0;
		
		for(int i=0;i<lists.size();i++){
			gd = (GrcompData)lists.get(i);
			v_comptype = gd.getComptype();
			compTxt = compTxt+"<br>";
			if (v_comptype==1)			compTxt += gd.getGroupsnm()+"그룹";
			else if (v_comptype==2)	compTxt += gd.getCompanynm();
			else if (v_comptype==3)	compTxt += gd.getCompanynm()+"-"+gd.getGpmnm();
			else if (v_comptype==4)	compTxt += gd.getCompanynm()+"-"+gd.getGpmnm()+"-"+gd.getDeptnm();
			else if (v_comptype==5)	compTxt += gd.getCompanynm()+"-"+gd.getGpmnm()+"-"+gd.getDeptnm()+gd.getPartnm();
			else	 compTxt += "??";
		}
		return compTxt;
	}
}
