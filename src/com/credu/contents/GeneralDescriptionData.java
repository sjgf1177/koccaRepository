//**********************************************************
//  1. 제      목: GeneralDescription Operation Data
//  2. 프로그램명: GeneralDescriptionData.java
//  3. 개      요: Description관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.22
//  7. 수      정: 박미복 2004. 11.22
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class GeneralDescriptionData {

	private	int	general_description_idx		=0;
	private	String 	description				="";
	private	int	metadata_idx				=0;
	
	public GeneralDescriptionData() {};
		
	/**
	 * @return
	 */
	public int getGeneral_description_idx() {
		return general_description_idx;
	}
	
	/**
	 * @param i
	 */
	public void setGeneral_description_idx(int i) {
		general_description_idx = i;
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