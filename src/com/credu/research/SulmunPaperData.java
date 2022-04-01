//**********************************************************
//1. 제      목: 
//2. 프로그램명: SulmunPaperData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-19
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.research;

import java.util.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SulmunPaperData {
	private String 	grcode;                              
	private String 	subj;                             
	private String  subjnm;

	private Hashtable PaperSubDataList;
	
	public SulmunPaperData() {
		PaperSubDataList = new Hashtable();
	}

	/**
	 * @return
	 */
	public String getGrcode() {
		return grcode;
	}

	/**
	 * @return
	 */
	public String getSubj() {
		return subj;
	}

	/**
	 * @return
	 */
	public String getSubjnm() {
		return subjnm;
	}

	/**
	 * @param string
	 */
	public void setGrcode(String string) {
		grcode = string;
	}

	/**
	 * @param string
	 */
	public void setSubj(String string) {
		subj = string;
	}

	/**
	 * @param string
	 */
	public void setSubjnm(String string) {
		subjnm = string;
	}

	public SulmunPaperSubData get(int index) {
		return (SulmunPaperSubData)PaperSubDataList.get(String.valueOf(index));
	}
	
	public void add(SulmunPaperSubData papersubdata) {
		//PaperSubDataList.put(String.valueOf(papersubdata.getSulpapername()), papersubdata);
		PaperSubDataList.put(String.valueOf(PaperSubDataList.size()), papersubdata);
	}

	public void remove(int index) {
		PaperSubDataList.remove(String.valueOf(index));
	}
	
	public void clear() {
		PaperSubDataList.clear();
	}
	
	public int size() {
		return PaperSubDataList.size();
	}

	public int getSulpapernum(int index) {
		int i = 0;
		SulmunPaperSubData papersubdata = get(index);
		if (papersubdata != null) {
			i = papersubdata.getSulpapernum();
		}
		return i;
	}

	public String getSulpapername(int index) {
		String string = "";
		SulmunPaperSubData papersubdata = get(index);
		if (papersubdata != null) {
			string = papersubdata.getSulpapername();
		}
		return string;
	}
	
	public int getTotcnt(int index) {
		int i = 0;
		SulmunPaperSubData papersubdata = get(index);
		if (papersubdata != null) {
			i = papersubdata.getTotcnt();
		}
		return i;
	}	
	
	public String getSulnums(int index) {
		String string = "";
		SulmunPaperSubData papersubdata = get(index);
		if (papersubdata != null) {
			string = papersubdata.getSulnums();
		}
		return string;
	}	

	public String getSulmailing(int index) {
		String string = "";
		SulmunPaperSubData papersubdata = get(index);
		if (papersubdata != null) {
			string = papersubdata.getSulmailing();
		}
		return string;
	}	

	public String getSulstart(int index) { 
		String string = "";
		SulmunPaperSubData papersubdata = get(index);
		if (papersubdata != null) {
			string = papersubdata.getSulstart();
		}
		return string;
	}

	public String getSulend(int index) { 
		String string = "";
		SulmunPaperSubData papersubdata = get(index);
		if (papersubdata != null) {
			string = papersubdata.getSulend();
		}
		return string;
	}	
	
	public void setSulpapernum(int index, int i) {
		SulmunPaperSubData papersubdata = get(index);
		if (papersubdata != null) {
			papersubdata.setSulpapernum(i);
		}
	}
	
	public void setSulpapername(int index, String string) {
		SulmunPaperSubData papersubdata = get(index);
		if (papersubdata != null) {
			papersubdata.setSulpapername(string);
		}
	}
	
	public void setTotcnt(int index, int i) {
		SulmunPaperSubData papersubdata = get(index);
		if (papersubdata != null) {
			papersubdata.setTotcnt(i);
		}
	}	
	
	public void setSulnums(int index, String string) {
		SulmunPaperSubData papersubdata = get(index);
		if (papersubdata != null) {
			papersubdata.setSulnums(string);
		}
	}	

	public void setSulmailing(int index, String string) {
		SulmunPaperSubData papersubdata = get(index);
		if (papersubdata != null) {
			papersubdata.setSulmailing(string);
		}
	}	

	public void setSulstart(int index, String string) { 
		SulmunPaperSubData papersubdata = get(index);
		if (papersubdata != null) {
			papersubdata.setSulstart(string);
		}
	}

	public void setSulend(int index, String string) { 
		SulmunPaperSubData papersubdata = get(index);
		if (papersubdata != null) {
			papersubdata.setSulend(string);
		}
	}		
}
