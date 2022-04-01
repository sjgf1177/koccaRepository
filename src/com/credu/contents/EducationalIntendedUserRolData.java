//**********************************************************
//  1. ��      ��: EducationalIntendedUserRol Operation Data
//  2. ���α׷���: EducationalIntendedUserRolData.java
//  3. ��      ��: Educational IntendedUserRol������ ���õ� Data Object
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ڹ̺� 2004. 11.29
//  7. ��      ��: �ڹ̺� 2004. 11.29
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class EducationalIntendedUserRolData {

	private	int	educational_intenduserrole_idx		=0;
	private	String 	intendedenduserrole				="";
	private	int	metadata_idx						=0;
	
	public EducationalIntendedUserRolData() {};
		
	/**
	 * @return
	 */
	public int getEducational_intenduserrole_idx() {
		return educational_intenduserrole_idx;
	}
	
	/**
	 * @param i
	 */
	public void setEducational_intenduserrole_idx(int i) {
		educational_intenduserrole_idx = i;
	}
		
	/**
	 * @return
	 */
	public String getIntendedenduserrole() {
		return intendedenduserrole;
	}
	
	/**
	 * @param string
	 */
	public void setIntendedenduserrole(String string) {
		intendedenduserrole = string;
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