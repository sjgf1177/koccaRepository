//**********************************************************
//  1. 제      목: Annotation Operation Data
//  2. 프로그램명: AnnotationData.java
//  3. 개      요: Annotation관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.30
//  7. 수      정: 박미복 2004. 11.30
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class AnnotationData {

	private	int	annotation_idx		=0;
	private	String 	person			="";
	private	String 	annotation_date	="";
	private	String 	description		="";
	private	int	metadata_idx		=0;
	
	public AnnotationData() {};
		
	/**
	 * @return
	 */
	public int getAnnotation_idx() {
		return annotation_idx;
	}
	
	/**
	 * @param i
	 */
	public void setAnnotation_idx(int i) {
		annotation_idx = i;
	}
		
	/**
	 * @return
	 */
	public String getPerson() {
		return person;
	}
	
	/**
	 * @param string
	 */
	public void setPerson(String string) {
		person = string;
	}
		
	/**
	 * @return
	 */
	public String getAnnotation_date() {
		return annotation_date;
	}
	
	/**
	 * @param string
	 */
	public void setAnnotation_date(String string) {
		annotation_date = string;
	}
		
	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
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