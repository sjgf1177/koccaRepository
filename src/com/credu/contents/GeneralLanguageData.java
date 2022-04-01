//**********************************************************
//  1. 제      목: GeneralLanguage Operation Data
//  2. 프로그램명: GeneralLanguageData.java
//  3. 개      요: Language관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.22
//  7. 수      정: 박미복 2004. 11.22
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class GeneralLanguageData {

	private	int	general_language_idx	=0;
	private	String 	language			="";
	private	int	metadata_idx			=0;
	
	public GeneralLanguageData() {};
		
	/**
	 * @return
	 */
	public int getGeneral_language_idx() {
		return general_language_idx;
	}
	
	/**
	 * @param i
	 */
	public void setGeneral_language_idx(int i) {
		general_language_idx = i;
	}
		
	/**
	 * @return
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * @param string
	 */
	public void setLanguage(String string) {
		language = string;
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