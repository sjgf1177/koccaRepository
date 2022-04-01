//**********************************************************
//  1. ��      ��: GeneralDescription Operation Data
//  2. ���α׷���: GeneralDescriptionData.java
//  3. ��      ��: Description������ ���õ� Data Object
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ڹ̺� 2004. 11.22
//  7. ��      ��: �ڹ̺� 2004. 11.22
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