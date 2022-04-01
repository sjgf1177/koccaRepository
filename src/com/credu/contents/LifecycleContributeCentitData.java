//**********************************************************
//  1. 제      목: LifecycleContributeCentit Operation Data
//  2. 프로그램명: LifecycleContributeCentitData.java
//  3. 개      요: LifecycleContributeCentit관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.22
//  7. 수      정: 박미복 2004. 11.22
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class LifecycleContributeCentitData {

	private	int	lifecycle_contr_centity_idx		=0;
	private	String 	centity						="";
	private	int	lifecycle_contribute_idx		=0;
	private	int	metadata_idx					=0;
	
	public LifecycleContributeCentitData() {};
		
	/**
	 * @return
	 */
	public int getLifecycle_contr_centity_idx() {
		return lifecycle_contr_centity_idx;
	}
	
	/**
	 * @param i
	 */
	public void setLifecycle_contr_centity_idx(int i) {
		lifecycle_contr_centity_idx = i;
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