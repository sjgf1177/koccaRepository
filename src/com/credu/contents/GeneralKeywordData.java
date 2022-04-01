//**********************************************************
//  1. 제      목: GeneralKeyword Operation Data
//  2. 프로그램명: GeneralKeywordData.java
//  3. 개      요: Keyword관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.22
//  7. 수      정: 박미복 2004. 11.22
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class GeneralKeywordData {

	private	int	general_keyword_idx		=0;
	private	String 	keyword				="";
	private	int	metadata_idx			=0;
	
	public GeneralKeywordData() {};
		
	/**
	 * @return
	 */
	public int getGeneral_keyword_idx() {
		return general_keyword_idx;
	}
	
	/**
	 * @param i
	 */
	public void setGeneral_keyword_idx(int i) {
		general_keyword_idx = i;
	}
		
	/**
	 * @return
	 */
	public String getKeyword() {
		return keyword;
	}
	
	/**
	 * @param string
	 */
	public void setKeyword(String string) {
		keyword = string;
	}
		
	/**
	 * @return
	 */
	public int getMetadata_idx() {
		return metadata_idx;
	}
	
	/**
	 * @param i
	 */
	public void setMetadata_idx(int i) {
		metadata_idx = i;
	}
}