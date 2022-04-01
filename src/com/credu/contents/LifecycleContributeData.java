//**********************************************************
//  1. 제      목: LifecycleContribute Operation Data
//  2. 프로그램명: LifecycleContributeData.java
//  3. 개      요: LifecycleContribute관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.22
//  7. 수      정: 박미복 2004. 11.22
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class LifecycleContributeData {

	private	int	lifecycle_contribute_idx		=0;
	private	String 	lifecycle_contr_role		="";
	private	String 	lifecycle_contr_date		="";
	private	int	metadata_idx					=0;
	
	// added
	private	String 	centity	="";
	
	
	public LifecycleContributeData() {};
		
	/**
	 * @return
	 */
	public int getLifecycle_contribute_idx() {
		return lifecycle_contribute_idx;
	}
	
	/**
	 * @param i
	 */
	public void setLifecycle_contribute_idx(int i) {
		lifecycle_contribute_idx = i;
	}
		
	/**
	 * @return
	 */
	public String getLifecycle_contr_role() {
		return lifecycle_contr_role;
	}
	
	/**
	 * @param string
	 */
	public void setLifecycle_contr_role(String string) {
		lifecycle_contr_role = string;
	}
		
	/**
	 * @return
	 */
	public String getLifecycle_contr_date() {
		return lifecycle_contr_date;
	}
	
	/**
	 * @param string
	 */
	public void setLifecycle_contr_date(String string) {
		lifecycle_contr_date = string;
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
		
	/**
	 * @return
	 */
	public String getCentity() {
		return centity;
	}
	
	/**
	 * @param string
	 */
	public void setCentity(String string) {
		centity = string;
	}
}