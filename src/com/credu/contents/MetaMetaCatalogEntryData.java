//**********************************************************
//  1. ��      ��: MetaMetaCatalogEntry Operation Data
//  2. ���α׷���: MetaMetaCatalogEntryData.java
//  3. ��      ��: CatalogEntry������ ���õ� Data Object
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ڹ̺� 2004. 11.22
//  7. ��      ��: �ڹ̺� 2004. 11.22
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class MetaMetaCatalogEntryData {

	private	int	metameta_catalog_idx	=0;
	private	String 	catalog				="";
	private	String 	entry				="";
	private	int	metadata_idx			=0;
	
	public MetaMetaCatalogEntryData() {};
		
	/**
	 * @return
	 */
	public int getMetameta_catalog_idx() {
		return metameta_catalog_idx;
	}
	
	/**
	 * @param i
	 */
	public void setMetameta_catalog_idx(int i) {
		metameta_catalog_idx = i;
	}
		
	/**
	 * @return
	 */
	public String getCatalog() {
		return catalog;
	}
	
	/**
	 * @param string
	 */
	public void setCatalog(String string) {
		catalog = string;
	}
		
	/**
	 * @return
	 */
	public String getEntry() {
		return entry;
	}
	
	/**
	 * @param string
	 */
	public void setEntry(String string) {
		entry = string;
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