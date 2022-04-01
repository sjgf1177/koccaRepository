//**********************************************************
//  1. ��      ��: EducationalLanguage Operation Data
//  2. ���α׷���: EducationalLanguageData.java
//  3. ��      ��: Educational Language������ ���õ� Data Object
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ڹ̺� 2004. 11.29
//  7. ��      ��: �ڹ̺� 2004. 11.29
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class EducationalLanguageData {

	private	int	educational_language_idx	=0;
	private	String 	language				="";
	private	int	metadata_idx				=0;
	
	public EducationalLanguageData() {};
		
	/**
	 * @return
	 */
	public int getEducational_language_idx() {
		return educational_language_idx;
	}
	
	/**
	 * @param i
	 */
	public void setEducational_language_idx(int i) {
		educational_language_idx = i;
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