//**********************************************************
//  1. ��      ��: LifecycleContributeCentit Operation Data
//  2. ���α׷���: LifecycleContributeCentitData.java
//  3. ��      ��: LifecycleContributeCentit������ ���õ� Data Object
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ڹ̺� 2004. 11.22
//  7. ��      ��: �ڹ̺� 2004. 11.22
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