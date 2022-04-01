//**********************************************************
//  1. 제      목: TechnicalRequirement Operation Data
//  2. 프로그램명: TechnicalRequirementData.java
//  3. 개      요: TechnicalRequirement관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.26
//  7. 수      정: 박미복 2004. 11.26
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class TechnicalRequirementData {

	private	int	technical_requirement_idx	=0;
	private	String 	requirement_type		="";
	private	String 	requirement_name		="";
	private	String 	minimum_version			="";
	private	String 	maximum_version			="";
	private	int	metadata_idx				=0;
	
	public TechnicalRequirementData() {};
		
	/**
	 * @return
	 */
	public int getTechnical_requirement_idx() {
		return technical_requirement_idx;
	}
	
	/**
	 * @param i
	 */
	public void setTechnical_requirement_idx(int i) {
		technical_requirement_idx = i;
	}
		
	/**
	 * @return
	 */
	public String getRequirement_type() {
		return requirement_type;
	}
	
	/**
	 * @param string
	 */
	public void setRequirement_type(String string) {
		requirement_type = string;
	}
		
	/**
	 * @return
	 */
	public String getRequirement_name() {
		return requirement_name;
	}
	
	/**
	 * @param string
	 */
	public void setRequirement_name(String string) {
		requirement_name = string;
	}
		
	/**
	 * @return
	 */
	public String getMinimum_version() {
		return minimum_version;
	}
	
	/**
	 * @param string
	 */
	public void setMinimum_version(String string) {
		minimum_version = string;
	}
		
	/**
	 * @return
	 */
	public String getMaximum_version() {
		return maximum_version;
	}
	
	/**
	 * @param string
	 */
	public void setMaximum_version(String string) {
		maximum_version = string;
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