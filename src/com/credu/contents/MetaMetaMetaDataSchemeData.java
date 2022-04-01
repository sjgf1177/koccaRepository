//**********************************************************
//  1. ��      ��: MetaMetaMetaDataScheme Operation Data
//  2. ���α׷���: MetaMetaMetaDataSchemeData.java
//  3. ��      ��: MetaMetaMetaDataScheme������ ���õ� Data Object
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ڹ̺� 2004. 11.25
//  7. ��      ��: �ڹ̺� 2004. 11.25
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