//**********************************************************
//  1. 제      목: GeneralCoverage Operation Data
//  2. 프로그램명: GeneralCoverageData.java
//  3. 개      요: Coverage관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.22
//  7. 수      정: 박미복 2004. 11.22
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class GeneralCoverageData {

	private	int	general_coverage_idx	=0;
	private	String 	coverage			="";
	private	int	metadata_idx			=0;
	
	public GeneralCoverageData() {};
		
	/**
	 * @return
	 */
	public int getGeneral_coverage_idx() {
		return general_coverage_idx;
	}
	
	/**
	 * @param i
	 */
	public void setGeneral_coverage_idx(int i) {
		general_coverage_idx = i;
	}
		
	/**
	 * @return
	 */
	public String getCoverage() {
		return coverage;
	}
	
	/**
	 * @param string
	 */
	public void setCoverage(String string) {
		coverage = string;
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