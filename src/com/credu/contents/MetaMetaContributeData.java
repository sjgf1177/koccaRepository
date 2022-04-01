//**********************************************************
//  1. ��      ��: MetaMetaContribute Operation Data
//  2. ���α׷���: MetaMetaContributeData.java
//  3. ��      ��: MetaMetaContribute������ ���õ� Data Object
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ڹ̺� 2004. 11.22
//  7. ��      ��: �ڹ̺� 2004. 11.22
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class MetaMetaContributeData {

	private	int	metameta_contribute_idx		=0;
	private	String 	meta_contr_role			="";
	private	String 	meta_contr_date			="";
	private	int	metadata_idx				=0;
	
	// added
	private	String 	centity	="";
	
	public MetaMetaContributeData() {};
		
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
	public String getMeta_contr_role() {
		return meta_contr_role;
	}
	
	/**
	 * @param string
	 */
	public void setMeta_contr_role(String string) {
		meta_contr_role = string;
	}
		
	/**
	 * @return
	 */
	public String getMeta_contr_date() {
		return meta_contr_date;
	}
	
	/**
	 * @param string
	 */
	public void setMeta_contr_date(String string) {
		meta_contr_date = string;
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