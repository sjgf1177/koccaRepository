//**********************************************************
//  1. 제      목: 교육그룹OPERATION BEAN
//  2. 프로그램명: EduGroupBean.java
//  3. 개      요:
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: LeeSuMin 2003. 07. 16
//  7. 수      정:
//**********************************************************
package com.dunet.common.taglib;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

public class KoccaSelectBean {

	public final static String GRTYPE_CODE        = "0060";

	public KoccaSelectBean() {}

	/**
    코드리스트 조회
    @param box          receive from the form object and session
    @return ArrayList   코드목록
	 */
	@SuppressWarnings("unchecked")
	public ArrayList list(RequestBox box) throws Exception {
		
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList result = null;
		String sql  = "";
		DataBox dbox = null;

		try {
			String s_gadmin = box.getSession("gadmin");
			String v_gadmin = null;
			if (s_gadmin==null || s_gadmin.length() == 0) v_gadmin = "";
			else v_gadmin = StringManager.substring(s_gadmin, 0, 1);
			String s_userid = box.getSession("userid");
			String v_tem_grcode = box.getSession("tem_grcode");

			ConfigSet cs = new ConfigSet();
			box.put("s_gadmin", s_gadmin);
			box.put("v_gadmin", v_gadmin);
			box.put("s_userid", s_userid);
			box.put("v_tem_grcode", v_tem_grcode);
			box.put("course.use", cs.getProperty("course.use"));
			box.put("param00", box.get("param"));
			box.sync("param1");
			box.sync("param2");
			box.sync("param3");
			box.sync("param4");
			box.sync("param5");
			box.sync("param6");
			box.sync("param7");
			box.sync("param8");
			box.sync("param9");
			box.remove("param");
			StringTokenizer st = new StringTokenizer(box.get("sqlNum"), "|");
			while(st.hasMoreTokens()) {
				sql += cs.getProperty("code.list." + st.nextToken());
			}
			//            sql = cs.getProperty("code.list." + box.get("sqlNum"));
			sql = replaceparam(sql, box, cs);

			if(cs.getBoolean("sql.debugView")) {
				//System.out.println("\n\nsqlNum : " + box.get("sqlNum"));
				System.out.println("\n-------------------- Select Tag SQL["+box.get("sqlNum")+"] Start -------------------- \n"+sql+"\n-------------------- Select Tag SQL End -------------------- \n");
			}
			connMgr = new DBConnectionManager();
			result = new ArrayList();
			ls = connMgr.executeQuery(sql, false);
			
			if (box.getQueryString("type").equals("'7'")||box.getQueryString("type").equals("'8'")) {
				while (ls.next()) {
					dbox = ls.getDataBox();
					dbox.put("d_code", ls.getString(1));
					dbox.put("d_codenm", ls.getString(2));
					dbox.put("d_gubun", ls.getString(3));
					dbox.put("d_gubunnm", ls.getString(4));
					dbox.put("d_rank", ls.getString(5));
	
					result.add(dbox);
				}
				
			} else {

				while (ls.next()) {
					dbox = ls.getDataBox();
					dbox.put("d_code", ls.getString(1));
					dbox.put("d_codenm", ls.getString(2));
	
					result.add(dbox);
				}
			}
		}
		catch (Exception ex) {
			//        	System.out.println(ex);
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}
	private String replaceparam(String inValue, RequestBox box,ConfigSet cs) {
		Object[] param = box.keySet().toArray();
		
		for (Object element : param) {
			if(cs.getBoolean("sql.debugView")) {System.out.print(element + ", ");}
			inValue = inValue.replaceAll(":" + element, box.getQueryString( (String)element));
			
			//System.out.println("element="+element + "                    box.getQueryString( (String)element)="+box.getQueryString( (String)element));
			//System.out.println("inValue="+ inValue +"\n");
			
		}
		inValue = inValue.replaceAll(":param", box.getQueryString("param00"));
		if(cs.getBoolean("sql.debugView")) {System.out.println();}
		
		//System.out.println("type="+box.getQueryString("type"));
		if (box.getQueryString("type").equals("'7'")) {
			if (box.getQueryString("param1") != null) {
				String tmpYear = "";
				String tmpMonth = "";
				String tmpOutput = "";
				String tmpCate = "";
				int i = 0;
				
				tmpYear = box.getQueryString("param00").replaceAll("'","");
				tmpMonth = box.getQueryString("param1");
				tmpCate = box.getQueryString("param2").replaceAll("'","");
				
				System.out.println("tmpYear"+tmpYear + "     tmpMonth="+ tmpMonth);
				
				StringTokenizer st = new StringTokenizer(tmpYear, ",");
				StringTokenizer st2 = new StringTokenizer(tmpCate, ",");
				StringTokenizer st3 = new StringTokenizer(tmpYear, ",");
			
				inValue += " and  c.grcode in (select grcode from tz_grcode where ";
				while (st2.hasMoreTokens()) {
					String temp = st2.nextToken();
					if ( i != 0 ) inValue += " or";
					inValue += " gubun = '" + temp + "'";
					i++;
				}
				inValue += " )";    
				
				i = 0;
				
				String tk1, tk2 = "";
				inValue += " and ( ";
				while(st.hasMoreTokens()) {
					//System.out.println(st.nextToken());
					tk1 = st.nextToken();
					StringTokenizer st1 = new StringTokenizer(tmpMonth, ",");
					if (st1.hasMoreTokens()) {						
						while(st1.hasMoreTokens()) {
							tk2 = st1.nextToken();
							tk2 = tk2.replaceAll("'", "");
							//System.out.println("i="+i + "st.nextToken()="+ tk1 + "     st1.nextToken()="+ tk2);
							
							tmpOutput = tk1 + tk2 ;
							if ( i != 0 ) inValue += " or";
							inValue += " substr(b.eduend,0,6) ='" + tmpOutput +"'";
							i++;
						}		
					} else {
						tmpOutput = tk1 + tk2 ;
						//System.out.println("i="+i + "    st.nextToken()="+ tk1 + "      st11.nextToken()="+ tk2);
						//tmpMonth += tk1 + st1;
						if ( i != 0 ) inValue += " or1";
						inValue += " substr(b.eduend,0,6) = '" + tmpOutput +"'";
						i++;
					}
				}
				inValue += " ) ";
				
				/*i = 0;
				inValue += " and (";
				while (st3.hasMoreTokens()) {
					String temp = st3.nextToken();
					if ( i != 0 ) inValue += " or";
					inValue += " a.gyear = '" + temp + "'";
					i++;
				}
				inValue += " )";   */
				
				inValue += " and c.gubun is not null and a.stat ='Y' order by grseq asc ) group by grseq, grseqnm, gubun";    
				//tmpOutput = tmpYear.replaceAll("'","") + box.getQueryString("param1").replaceAll("'","");
				
				
			}
		} else if (box.getQueryString("type").equals("'8'")) {
			//System.out.println("=======================");
			//System.out.println("해당");
			//System.out.println(box.getQueryString("param1"));
			if (box.getQueryString("param1") != null) {
				String tmpYear = "";
				String tmpOutput = "";
				int i = 0;
				
				tmpYear = box.getQueryString("param00").replaceAll("'","");
				
			//	System.out.println("tmpYear"+tmpYear );
				
				StringTokenizer st = new StringTokenizer(tmpYear, ",");
				
			//	System.out.println("Start");
			
				inValue += " and (";
				while (st.hasMoreTokens()) {
					String temp = st.nextToken();
					if ( i != 0 ) inValue += " or";
					inValue += " a.gyear = '" + temp + "'";
					i++;
				}
				inValue += ") and c.gubun is not null and a.stat ='Y' order by grseq asc )group by grseq, grseqnm, gubun ";    
				
				i = 0;
				
				//System.out.println("End");
				//inValue += " )";    
				//tmpOutput = tmpYear.replaceAll("'","") + box.getQueryString("param1").replaceAll("'","");
				
				
			}
		} else if (box.getQueryString("type").equals("'9'")) {
			//System.out.println("=======================");
			//System.out.println("해당");
			//System.out.println(box.getQueryString("param1"));
			if (box.getQueryString("param1") != null) {
				String tmpSeq = "";
				String tmpCate = "";
				String tmpOutput = "";
				int i = 0;
				
				tmpSeq = box.getQueryString("param00").replaceAll("'","");
				tmpCate = box.getQueryString("param1").replaceAll("'","");
				
				//System.out.println("tmpSeq"+tmpSeq );
				
				StringTokenizer st = new StringTokenizer(tmpSeq, ",");				
				StringTokenizer st2 = new StringTokenizer(tmpCate, ",");
				
				inValue += " and (";
				while (st2.hasMoreTokens()) {
					String temp = st2.nextToken();
					if ( i != 0 ) inValue += " or";
					inValue += " a.area = '" + temp + "'";
					i++;
				}
				inValue += " )";    
				
				
				//System.out.println("Start");
				i = 0;
				inValue += " and (";
				while (st.hasMoreTokens()) {
					String temp = st.nextToken();
					if ( i != 0 ) inValue += " or";
					inValue += " b.year||b.grcode||b.grseq = '" + temp + "'";
					i++;
				}
				inValue += ") order by a.subjnm asc";    
				
				//i = 0;
				
				//System.out.println("End");
				//inValue += " )";    
				//tmpOutput = tmpYear.replaceAll("'","") + box.getQueryString("param1").replaceAll("'","");
			}  
					
		}else if (box.getQueryString("type").equals("'11'")) {
			if (box.getQueryString("param") != null) {
				String tmpYear = "";
				String tmpCate = "";
				int i = 0;
				
				tmpYear = box.getQueryString("param00").replaceAll("'","");
				tmpCate = box.getQueryString("param1").replaceAll("'","");
				
				StringTokenizer st = new StringTokenizer(tmpYear, ",");				
				StringTokenizer st2 = new StringTokenizer(tmpCate, ",");
				
				inValue += " and (";
				while (st2.hasMoreTokens()) {
					String temp = st2.nextToken();
					if ( i != 0 ) inValue += " or";
					
					inValue += " a.upperclass||a.middleclass = '" + temp + "'";
					i++;
				}
				inValue += " )";    
				
				i = 0;
				inValue += " and (";
				while (st.hasMoreTokens()) {
					String temp = st.nextToken();
					if ( i != 0 ) inValue += " or";
					inValue += " b.year = '" + temp + "'";
					i++;
				}
				inValue += ") order by b.subjnm asc";   
			}
		}
		
		return inValue;
	}
}
