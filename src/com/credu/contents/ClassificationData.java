//**********************************************************
//  1. ��      ��: Classification Operation Data
//  2. ���α׷���: ClassificationData.java
//  3. ��      ��: Classification������ ���õ� Data Object
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ڹ̺� 2004. 11.30
//  7. ��      ��: �ڹ̺� 2004. 11.30
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class ClassificationData {

	private	int	classification_idx		=0;
	private	String 	purpose				="";
	private	String 	description			="";
	private	String 	keyword				="";
	private	int	metadata_idx			=0;
	
	public ClassificationData() {};
		
	/**
	 * @return
	 */
	public int getClassification_idx() {
		return classification_idx;
	}
	
	/**
	 * @param i
	 */
	public void setClassification_idx(int i) {
		classification_idx = i;
	}
		
	/**
	 * @return
	 */
	public String getPurpose() {
		return purpose;
	}
	
	/**
	 * @param string
	 */
	public void setPurpose(String string) {
		purpose = string;
	}
		
	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
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