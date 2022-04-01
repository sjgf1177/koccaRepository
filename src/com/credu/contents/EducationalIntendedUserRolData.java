//**********************************************************
//  1. 제      목: EducationalIntendedUserRol Operation Data
//  2. 프로그램명: EducationalIntendedUserRolData.java
//  3. 개      요: Educational IntendedUserRol관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.29
//  7. 수      정: 박미복 2004. 11.29
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