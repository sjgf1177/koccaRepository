//**********************************************************
//  1. ��      ��: LifecycleContribute Operation Data
//  2. ���α׷���: LifecycleContributeData.java
//  3. ��      ��: LifecycleContribute������ ���õ� Data Object
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ڹ̺� 2004. 11.22
//  7. ��      ��: �ڹ̺� 2004. 11.22
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