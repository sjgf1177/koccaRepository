//**********************************************************
//  1. ��      ��: MetaMetaContributeCentit Operation Data
//  2. ���α׷���: MetaMetaContributeCentitData.java
//  3. ��      ��: MetaMetaContributeCentit������ ���õ� Data Object
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ڹ̺� 2004. 11.25
//  7. ��      ��: �ڹ̺� 2004. 11.25
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class MetaMetaContributeCentitData {

	private	int	metameta_contr_centity_idx		=0;
	private	int	metameta_contribute_idx			=0;
	private	String 	centity						="";
	private	int	metadata_idx					=0;
	
	public MetaMetaContributeCentitData() {};
		
	/**
	 * @return
	 */
	public int getMetameta_contr_centity_idx() {
		return metameta_contr_centity_idx;
	}
	
	/**
	 * @param i
	 */
	public void setMetameta_contr_centity_idx(int i) {
		metameta_contr_centity_idx = i;
	}
		
	/**
	 * @return
	 */
	public int getMetameta_contribute_idx() {
		return metameta_contribute_idx;
	}
	
	/**
	 * @param i
	 */
	public void setMetameta_contribute_idx(int i) {
		metameta_contribute_idx = i;
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