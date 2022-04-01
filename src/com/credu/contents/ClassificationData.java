//**********************************************************
//  1. 제      목: Classification Operation Data
//  2. 프로그램명: ClassificationData.java
//  3. 개      요: Classification관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.30
//  7. 수      정: 박미복 2004. 11.30
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