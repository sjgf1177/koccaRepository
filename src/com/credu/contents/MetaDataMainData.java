//**********************************************************
//  1. 제      목: MetaDataMain Operation Data
//  2. 프로그램명: MetaDataMainData.java
//  3. 개      요: MetaDataMain관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.22
//  7. 수      정: 박미복 2004. 11.22
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;


public class MetaDataMainData {

	private	int	metadata_idx						=0;
	private	String 	oid								="";
	private	String 	general_title					="";
	private	String 	general_structure				="";
	private	String 	general_aggregationlevel		="";
	private	String 	lifecycle_version				="";
	private	String 	lifecycle_status				="";
	private	String 	metameta_language				="";
	private	String 	technical_size					="";
	private	String 	technical_installationremarks	="";
	private	String 	technical_otherrequirements		="";
	private	String 	technical_duration				="";
	private	String 	educational_interactivitytype	="";
	private	String 	educational_interactivitylevel	="";
	private	String 	educational_semanticdensity		="";
	private	String 	educational_difficulty			="";
	private	String 	educational_learningtime		="";
	private	String 	educational_description			="";
	private	String 	rights_cost						="";
	private	String 	rights_copyrightrestrictions	="";
	private	String 	rights_description				="";
	private	String 	t_format						="";
	private	String 	t_location						="";
	private	String 	t_locationtype					="";
	private	String 	e_learningtype					="";
	private	String 	e_context						="";
	private	String 	e_typicalagerange				="";
	
	public MetaDataMainData() {};
		
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
	public String getOid() {
		return oid;
	}
	
	/**
	 * @param string
	 */
	public void setOid(String string) {
		oid = string;
	}
	
	/**
	 * @return
	 */
	public String getGeneral_title() {
		return general_title;
	}
	
	/**
	 * @param string
	 */
	public void setGeneral_title(String string) {
		general_title = string;
	}
	
	/**
	 * @return
	 */
	public String getGeneral_structure() {
		return general_structure;
	}
	
	/**
	 * @param string
	 */
	public void setGeneral_structure(String string) {
		general_structure = string;
	}
	
	/**
	 * @return
	 */
	public String getGeneral_aggregationlevel() {
		return general_aggregationlevel;
	}
	
	/**
	 * @param string
	 */
	public void setGeneral_aggregationlevel(String string) {
		general_aggregationlevel = string;
	}
	
	/**
	 * @return
	 */
	public String getLifecycle_version() {
		return lifecycle_version;
	}
	
	/**
	 * @param string
	 */
	public void setLifecycle_version(String string) {
		lifecycle_version = string;
	}
	
	/**
	 * @return
	 */
	public String getLifecycle_status() {
		return lifecycle_status;
	}
	
	/**
	 * @param string
	 */
	public void setLifecycle_status(String string) {
		lifecycle_status = string;
	}
	
	/**
	 * @return
	 */
	public String getMetameta_language() {
		return metameta_language;
	}
	
	/**
	 * @param string
	 */
	public void setMetameta_language(String string) {
		metameta_language = string;
	}
	
	/**
	 * @return
	 */
	public String getTechnical_size() {
		return technical_size;
	}
	
	/**
	 * @param string
	 */
	public void setTechnical_size(String string) {
		technical_size = string;
	}
	
	/**
	 * @return
	 */
	public String getTechnical_installationremarks() {
		return technical_installationremarks;
	}
	
	/**
	 * @param string
	 */
	public void setTechnical_installationremarks(String string) {
		technical_installationremarks = string;
	}
	
	/**
	 * @return
	 */
	public String getTechnical_otherrequirements() {
		return technical_otherrequirements;
	}
	
	/**
	 * @param string
	 */
	public void setTechnical_otherrequirements(String string) {
		technical_otherrequirements = string;
	}
	
	/**
	 * @return
	 */
	public String getTechnical_duration() {
		return technical_duration;
	}
	
	/**
	 * @param string
	 */
	public void setTechnical_duration(String string) {
		technical_duration = string;
	}
	
	/**
	 * @return
	 */
	public String getEducational_interactivitytype() {
		return educational_interactivitytype;
	}
	
	/**
	 * @param string
	 */
	public void setEducational_interactivitytype(String string) {
		educational_interactivitytype = string;
	}
	
	/**
	 * @return
	 */
	public String getEducational_interactivitylevel() {
		return educational_interactivitylevel;
	}
	
	/**
	 * @param string
	 */
	public void setEducational_interactivitylevel(String string) {
		educational_interactivitylevel = string;
	}
	
	/**
	 * @return
	 */
	public String getEducational_semanticdensity() {
		return educational_semanticdensity;
	}
	
	/**
	 * @param string
	 */
	public void setEducational_semanticdensity(String string) {
		educational_semanticdensity = string;
	}
	
	/**
	 * @return
	 */
	public String getEducational_difficulty() {
		return educational_difficulty;
	}
	
	/**
	 * @param string
	 */
	public void setEducational_difficulty(String string) {
		educational_difficulty = string;
	}
	
	/**
	 * @return
	 */
	public String getEducational_learningtime() {
		return educational_learningtime;
	}
	
	/**
	 * @param string
	 */
	public void setEducational_learningtime(String string) {
		educational_learningtime = string;
	}
	
	/**
	 * @return
	 */
	public String getEducational_description() {
		return educational_description;
	}
	
	/**
	 * @param string
	 */
	public void setEducational_description(String string) {
		educational_description = string;
	}
	
	/**
	 * @return
	 */
	public String getRights_cost() {
		return rights_cost;
	}
	
	/**
	 * @param string
	 */
	public void setRights_cost(String string) {
		rights_cost = string;
	}
	
	/**
	 * @return
	 */
	public String getRights_copyrightrestrictions() {
		return rights_copyrightrestrictions;
	}
	
	/**
	 * @param string
	 */
	public void setRights_copyrightrestrictions(String string) {
		rights_copyrightrestrictions = string;
	}
	
	/**
	 * @return
	 */
	public String getRights_description() {
		return rights_description;
	}
	
	/**
	 * @param string
	 */
	public void setRights_description(String string) {
		rights_description = string;
	}
	
	/**
	 * @return
	 */
	public String getT_format() {
		return t_format;
	}
	
	/**
	 * @param string
	 */
	public void setT_format(String string) {
		t_format = string;
	}
	
	/**
	 * @return
	 */
	public String getT_location() {
		return t_location;
	}
	
	/**
	 * @param string
	 */
	public void setT_location(String string) {
		t_location = string;
	}
	
	/**
	 * @return
	 */
	public String getT_locationtype() {
		return t_locationtype;
	}
	
	/**
	 * @param string
	 */
	public void setT_locationtype(String string) {
		t_locationtype = string;
	}
	
	/**
	 * @return
	 */
	public String getE_learningtype() {
		return e_learningtype;
	}
	
	/**
	 * @param string
	 */
	public void setE_learningtype(String string) {
		e_learningtype = string;
	}
	
	/**
	 * @return
	 */
	public String getE_context() {
		return e_context;
	}
	
	/**
	 * @param string
	 */
	public void setE_context(String string) {
		e_context = string;
	}
	
	/**
	 * @return
	 */
	public String getE_typicalagerange() {
		return e_typicalagerange;
	}
	
	/**
	 * @param string
	 */
	public void setE_typicalagerange(String string) {
		e_typicalagerange = string;
	}
}