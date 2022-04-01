//**********************************************************
//  1. 제      목: Relation Operation Data
//  2. 프로그램명: RelationData.java
//  3. 개      요: Relation관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.30
//  7. 수      정: 박미복 2004. 11.30
//**********************************************************

package com.credu.beta;

import java.util.*;
import com.credu.library.*;


public class BetaRelationData {

	private	int	relation_idx				=0;
	private	String 	relation_kind			="";
	private	String 	relation_resource		="";
	private	String 	relation_description	="";
	private	int	metadata_idx				=0;
	
	public BetaRelationData() {};
		
	/**
	 * @return
	 */
	public int getRelation_idx() {
		return relation_idx;
	}
	
	/**
	 * @param i
	 */
	public void setRelation_idx(int i) {
		relation_idx = i;
	}
		
	/**
	 * @return
	 */
	public String getRelation_kind() {
		return relation_kind;
	}
	
	/**
	 * @param string
	 */
	public void setRelation_kind(String string) {
		relation_kind = string;
	}
		
	/**
	 * @return
	 */
	public String getRelation_resource() {
		return relation_resource;
	}
	
	/**
	 * @param string
	 */
	public void setRelation_resource(String string) {
		relation_resource = string;
	}
		
	/**
	 * @return
	 */
	public String getRelation_description() {
		return relation_description;
	}
	
	/**
	 * @param string
	 */
	public void setRelation_description(String string) {
		relation_description = string;
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