//**********************************************************
//  1. ��      ��: TechnicalRequirement Operation Data
//  2. ���α׷���: TechnicalRequirementData.java
//  3. ��      ��: TechnicalRequirement������ ���õ� Data Object
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ڹ̺� 2004. 11.26
//  7. ��      ��: �ڹ̺� 2004. 11.26
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