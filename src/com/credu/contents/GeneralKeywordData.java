//**********************************************************
//  1. ��      ��: GeneralKeyword Operation Data
//  2. ���α׷���: GeneralKeywordData.java
//  3. ��      ��: Keyword������ ���õ� Data Object
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ڹ̺� 2004. 11.22
//  7. ��      ��: �ڹ̺� 2004. 11.22
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