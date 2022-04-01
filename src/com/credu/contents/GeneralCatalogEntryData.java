//**********************************************************
//  1. 제      목: GeneralCatalogEntry Operation Data
//  2. 프로그램명: GeneralCatalogEntryData.java
//  3. 개      요: CatalogEntry관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.22
//  7. 수      정: 박미복 2004. 11.22
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class GeneralCatalogEntryData {

	private	int	general_catalog_idx		=0;
	private	String 	catalog				="";
	private	String 	entry				="";
	private	int	metadata_idx			=0;
	
	public GeneralCatalogEntryData() {};
		
	/**
	 * @return
	 */
	public int getGeneral_catalog_idx() {
		return general_catalog_idx;
	}
	
	/**
	 * @param i
	 */
	public void setGeneral_catalog_idx(int i) {
		general_catalog_idx = i;
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