//**********************************************************
//  1. 제      목: MetaMetaContributeCentit Operation Data
//  2. 프로그램명: MetaMetaContributeCentitData.java
//  3. 개      요: MetaMetaContributeCentit관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.25
//  7. 수      정: 박미복 2004. 11.25
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