//**********************************************************
//  1. 제      목: MetaMetaMetaDataScheme Operation Data
//  2. 프로그램명: MetaMetaMetaDataSchemeData.java
//  3. 개      요: MetaMetaMetaDataScheme관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.25
//  7. 수      정: 박미복 2004. 11.25
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class MetaMetaMetaDataSchemeData {

	private	int	metadata_scheme_idx			=0;
	private	String 	metadata_scheme			="";
	private	int	metadata_idx				=0;
	
	public MetaMetaMetaDataSchemeData() {};
		
	/**
	 * @return
	 */
	public int getMetadata_scheme_idx() {
		return metadata_scheme_idx;
	}
	
	/**
	 * @param i
	 */
	public void setMetadata_scheme_idx(int i) {
		metadata_scheme_idx = i;
	}
		
	/**
	 * @return
	 */
	public String getMetadata_scheme() {
		return metadata_scheme;
	}
	
	/**
	 * @param string
	 */
	public void setMetadata_scheme(String string) {
		metadata_scheme = string;
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