<%
/**
 * file name : za_Metadata_U.jsp
 * date      : 2004/11/23
 * programmer: Park Mibok
 * function  : Metadata Frame
 */
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.scorm.*" %>

<%@ page import = "java.io.File" %>
<%@ page import = "java.util.Date" %>
<%@ page import = "jxl.*" %>
<%
/*	Workbook workbook = Workbook.getWorkbook(new File("C:/Documents and Settings/moonmb/¹ÙÅÁ È­¸é/±¤¸í/±âÅ¸/myfile.xls"));
	Sheet sheet = workbook.getSheet(0);
	int c = sheet.getColumns();
	int r = sheet.getRows();
	System.out.println("column count:"+c+",row count:"+r);
	Cell[] cell = new Cell[r];
	String[] str = new String[r];

	cell = sheet.getColumn(3);
	for ( int i=0;i<r;i++ ) {
		str[i] = cell[i].getContents();
		System.out.println("str["+i+"]:"+str[i]);
	}
*/

    ArrayList metaDataAll = (ArrayList)request.getAttribute("MetaDataAll");
    MetaDataMainData data = (MetaDataMainData)metaDataAll.get(0);

	ArrayList generalCatalogEntryList	= (ArrayList)metaDataAll.get(1);
	ArrayList generalLanguageList		= (ArrayList)metaDataAll.get(2);
	ArrayList generalDescriptionList	= (ArrayList)metaDataAll.get(3);
	ArrayList generalKeywordList		= (ArrayList)metaDataAll.get(4);
	ArrayList generalCoverageList		= (ArrayList)metaDataAll.get(5);

	ArrayList lifecycleContributeList	= (ArrayList)metaDataAll.get(6);
	ArrayList lifecycleContributeCentitList	= (ArrayList)metaDataAll.get(7);

	ArrayList metaMetaCatalogEntryList	= (ArrayList)metaDataAll.get(8);
	ArrayList metaMetaContributeList	= (ArrayList)metaDataAll.get(9);
	ArrayList metaMetaContributeCentitList	= (ArrayList)metaDataAll.get(10);
	ArrayList metaMetaMetaDataSchemeList	= (ArrayList)metaDataAll.get(11);

	ArrayList technicalRequirementList 		= (ArrayList)metaDataAll.get(12);
	ArrayList educationalIntendedUserRolList = (ArrayList)metaDataAll.get(13);
	ArrayList educationalLanguageList 		= (ArrayList)metaDataAll.get(14);
	ArrayList relationList 					= (ArrayList)metaDataAll.get(15);
	ArrayList annotationList				= (ArrayList)metaDataAll.get(16);
	ArrayList classificationList 			= (ArrayList)metaDataAll.get(17);

	GeneralCatalogEntryData generalCatalogEntryData = null;
	GeneralLanguageData generalLanguageData = null;
	GeneralDescriptionData generalDescriptionData = null;
	GeneralKeywordData generalKeywordData = null;
	GeneralCoverageData generalCoverageData = null;

	LifecycleContributeData lifecycleContributeData = null;
	LifecycleContributeCentitData lifecycleContributeCentitData = null;

	MetaMetaCatalogEntryData metaMetaCatalogEntryData = null;
	MetaMetaContributeData metaMetaContributeData = null;
	MetaMetaContributeCentitData metaMetaContributeCentitData = null;
	MetaMetaMetaDataSchemeData metaMetaMetaDataSchemeData = null;

	TechnicalRequirementData technicalRequirementData = null;

	EducationalIntendedUserRolData educationalIntendedUserRolData = null;
	EducationalLanguageData educationalLanguageData = null;

	RelationData relationData = null;
	AnnotationData annotationData = null;
	ClassificationData classificationData = null;

	String s1_catalogue = "";
	String s1_entry = "";
	String s1_language = "";
	String s1_description = "";
	String s1_keyword = "";
	String s1_coverage = "";

	String s2_role = "";
	String s2_date = "";
	String s2_centity = "";

	String s3_catalogue = "";
	String s3_entry = "";
	String s3_role = "";
	String s3_date = "";
	String s3_centity = "";
	String s3_scheme = "";

	String s4_type = "";
	String s4_name = "";
	String s4_min = "";
	String s4_max = "";

	String s5_role = "";
	String s5_language = "";
	String[] s5_roleArray = null;
	String check1 = "";
	String check2 = "";
	String check3 = "";
	String check4 = "";

	String s7_kind = "";
	String s7_description = "";

	String s8_centity_all = "";
	String s8_centity = "";
	String s8_date = "";
	String s8_date_description = "";
	String s8_description = "";

	String s9_purpose = "";
	String s9_description = "";
	String s9_keyword = "";

	for ( int i=0; i<generalCatalogEntryList.size(); i++ ) {
	    generalCatalogEntryData = (GeneralCatalogEntryData)generalCatalogEntryList.get(i);
	    s1_catalogue += generalCatalogEntryData.getCatalog()+"|";
	    s1_entry += generalCatalogEntryData.getEntry()+"|";
	    //System.out.println("s1_catalogue : " + s1_catalogue + ", s1_entry : " + s1_entry);
	}

	for ( int i=0; i<generalLanguageList.size(); i++ ) {
	    generalLanguageData = (GeneralLanguageData)generalLanguageList.get(i);
	    if ( i != 0 ) s1_language += ",";
	    s1_language += generalLanguageData.getLanguage();
	    //System.out.println("s1_language : " + s1_language);
	}

	for ( int i=0; i<generalDescriptionList.size(); i++ ) {
	    generalDescriptionData = (GeneralDescriptionData)generalDescriptionList.get(i);
	    if ( i == 0 ) s1_description = generalDescriptionData.getDescription();		// 1 or more => 1 and only 1 À¸·Î º¯°æ
	    //s1_description += generalDescriptionData.getDescription()+"|";
	    //System.out.println("s1_description : " + s1_description);
	}

	for ( int i=0; i<generalKeywordList.size(); i++ ) {
	    generalKeywordData = (GeneralKeywordData)generalKeywordList.get(i);
	    if ( i != 0 ) s1_keyword += "|";
	    s1_keyword += generalKeywordData.getKeyword();
	    //System.out.println("s1_keyword : " + s1_keyword);
	}

	for ( int i=0; i<generalCoverageList.size(); i++ ) {
	    generalCoverageData = (GeneralCoverageData)generalCoverageList.get(i);
	    if ( i != 0 ) s1_coverage += "|";
	    s1_coverage += generalCoverageData.getCoverage();
	    //System.out.println("s1_coverage : " + s1_coverage);
	}

	for ( int i=0; i<lifecycleContributeList.size(); i++ ) {
	    lifecycleContributeData = (LifecycleContributeData)lifecycleContributeList.get(i);
	    s2_role += lifecycleContributeData.getLifecycle_contr_role()+"|";
	    s2_date += lifecycleContributeData.getLifecycle_contr_date()+"|";

	    for ( int j=0; j<lifecycleContributeCentitList.size(); j++ ) {
		    lifecycleContributeCentitData = (LifecycleContributeCentitData)lifecycleContributeCentitList.get(j);
			if (lifecycleContributeCentitData.getLifecycle_contribute_idx() == lifecycleContributeData.getLifecycle_contribute_idx() ) {
			    String centity = lifecycleContributeCentitData.getCentity();
			    s2_centity += StringUtil.centity(centity);
			}
	    }
	    s2_centity += "|";
	}
    //System.out.println("s2_role : " + s2_role + ",s2_date : " + s2_date + ",s2_centity : " + s2_centity);

	for ( int i=0; i<metaMetaCatalogEntryList.size(); i++ ) {
	    metaMetaCatalogEntryData = (MetaMetaCatalogEntryData)metaMetaCatalogEntryList.get(i);
	    s3_catalogue += metaMetaCatalogEntryData.getCatalog()+"|";
	    s3_entry += metaMetaCatalogEntryData.getEntry()+"|";
	    //System.out.println("s3_catalogue : " + s3_catalogue + ", s3_entry : " + s3_entry);
	}

	for ( int i=0; i<metaMetaContributeList.size(); i++ ) {
	    metaMetaContributeData = (MetaMetaContributeData)metaMetaContributeList.get(i);
	    s3_role += metaMetaContributeData.getMeta_contr_role()+"|";
	    s3_date += metaMetaContributeData.getMeta_contr_date()+"|";

	    for ( int j=0; j<metaMetaContributeCentitList.size(); j++ ) {
		    metaMetaContributeCentitData = (MetaMetaContributeCentitData)metaMetaContributeCentitList.get(j);
			if (metaMetaContributeCentitData.getMetameta_contribute_idx() == metaMetaContributeData.getMetameta_contribute_idx() ) {
			    String centity = metaMetaContributeCentitData.getCentity();
			    s3_centity += StringUtil.centity(centity);
			}
	    }
	    s3_centity += "|";
	}
    //System.out.println("s3_role : " + s3_role + ",s3_date : " + s3_date + ",s3_centity : " + s3_centity);

	for ( int i=0; i<metaMetaMetaDataSchemeList.size(); i++ ) {
	    metaMetaMetaDataSchemeData = (MetaMetaMetaDataSchemeData)metaMetaMetaDataSchemeList.get(i);
	    //if ( i != 0 ) s3_scheme += "|";
	    //s3_scheme += metaMetaMetaDataSchemeData.getMetadata_scheme();
	    //System.out.println("s3_scheme : " + s3_scheme);
	    if ( i == 0 ) s3_scheme = metaMetaMetaDataSchemeData.getMetadata_scheme();		// 1 or more => 1 and only 1 À¸·Î º¯°æ
	}

	for ( int i=0; i<technicalRequirementList.size(); i++ ) {
	    technicalRequirementData = (TechnicalRequirementData)technicalRequirementList.get(i);
	    s4_type += technicalRequirementData.getRequirement_type()+"|";
	    s4_name += technicalRequirementData.getRequirement_name()+"|";
	    s4_min += technicalRequirementData.getMinimum_version()+"|";
	    s4_max += technicalRequirementData.getMaximum_version()+"|";
	    //System.out.println("s4_type: " + s4_type + ", s4_name : " + s4_name + ", s4_min : " + s4_min + ", s4_max : " + s4_max);
	}

    int rolsize = educationalIntendedUserRolList.size();
	if ( rolsize > 0 ) s5_roleArray = new String[rolsize];
	for ( int i=0; i<educationalIntendedUserRolList.size(); i++ ) {
	    educationalIntendedUserRolData = (EducationalIntendedUserRolData)educationalIntendedUserRolList.get(i);
	    s5_role += (i+1) + ")  " + educationalIntendedUserRolData.getIntendedenduserrole()+"\r\n";
	    //System.out.println("s5_role: " + s5_role);
		s5_roleArray[i] = educationalIntendedUserRolData.getIntendedenduserrole();
	    //System.out.println("s5_roleArray["+i+"]: " + s5_roleArray[i]);
	}
	if ( rolsize > 0 ) {
		for ( int i=0; i<rolsize;i++ ) {
			if ( "Teacher".equals(s5_roleArray[i]) ) check1 = "checked";
			if ( "Author".equals(s5_roleArray[i]) ) check2 = "checked";
			if ( "Learner".equals(s5_roleArray[i]) ) check3 = "checked";
			if ( "Manager".equals(s5_roleArray[i]) ) check4 = "checked";
		}
	}

	for ( int i=0; i<educationalLanguageList.size(); i++ ) {
	    educationalLanguageData = (EducationalLanguageData)educationalLanguageList.get(i);
	    if ( i != 0 ) s5_language += ",";
	    s5_language += educationalLanguageData.getLanguage();
	    //System.out.println("s5_language : " + s5_language);
	}

	for ( int i=0; i<relationList.size(); i++ ) {
	    relationData = (RelationData)relationList.get(i);
	    s7_kind += relationData.getRelation_kind()+"|";
	    s7_description += relationData.getRelation_description()+"|";
	    //System.out.println("s7_kind : " + s7_kind + ",s7_description : " + s7_description);
	}

	for ( int i=0; i<annotationList.size(); i++ ) {
	    annotationData = (AnnotationData)annotationList.get(i);
	    s8_centity_all = annotationData.getPerson();
	    s8_centity += StringUtil.centity(s8_centity_all)+"|";
	    
	    s8_date += annotationData.getAnnotation_date()+"|";
	    s8_date_description += "|";
	    s8_description += annotationData.getDescription()+"|";
	    //System.out.println("s8_centity_all : " + s8_centity_all+",s8_centity : " + s8_centity+",s8_date : " + s8_date+",s8_date_description : " + s8_date_description+",s8_description : " + s8_description);
	}

	for ( int i=0; i<classificationList.size(); i++ ) {
	    classificationData = (ClassificationData)classificationList.get(i);
	    s9_purpose += classificationData.getPurpose()+"|";
	    s9_description += classificationData.getDescription()+"|";
	    s9_keyword += classificationData.getKeyword()+"|";
	    //System.out.println("s9_purpose : " + s9_purpose + ",s9_description : " + s9_description + ",s9_keyword : " + s9_keyword);
	}
%>
<html>
<head>
<title>SCORM MDG - Content</title>

<script language="javascript" src="/script/scorm_xml_functions.js"></script>
<script language="javascript" src="/script/scorm_lib.js"></script>
<script type="text/javascript">
	
function db_popup(){
	url="/learn/html/admin/metadata/loading.html"
	window.open(url,"loading","toolbar=no,menubar=no,scrollbars=yes,resizable=no,width=400,height=200,left=100,top=100");
}

function splitLength(str,split,leng,msg)
{
	var strArray = str.split(split);
	for ( var i=0;i<strArray.length;i++) {
		if ( getByteValue(strArray[i]) > leng ) {
			alert(msg +" '"+ strArray[i]+"' is "+getByteValue(strArray[i])+" bytes.(max : "+leng+" bytes)");
			return false;
		}
	}
	return true;
}

function MetaMake(){
//		alert("MetaMake");
/*		if(trim(document.ourform.Title.value)=="")
		{
			alert("1.2 Title Required Element");
			document.ourform.Title.focus();
		} else if(trim(document.ourform.CatalogueEntry.value)=="")
		{
			alert("1.3 Catalog Entry Required Element");
			document.ourform.CatalogueEntry.focus();
		}
//		else if(trim(document.ourform.LanguageGeneral.value)=="")
//		{
//			alert("1.4 Language Required Element");
//			document.ourform.LanguageGeneral.focus();
//		} else if(trim(document.ourform.DescriptionGeneral.value)=="")
		{
			alert("1.5 Description Required Element");
			document.ourform.DescriptionGeneral.focus();
		} else if(trim(document.ourform.KeywordsGeneral.value)=="")
		{
			alert("1.6 Keywords Required Element");
			document.ourform.KeywordsGeneral.focus();
		}
//		else if(trim(document.ourform.Coverage.value)=="")
//		{
//			alert("1.7 Coverage Required Element");
//			document.ourform.Coverage.focus();
//		}
//		else if(trim(document.ourform.Structure.value)=="")
//		{
//			alert("1.8 Structure Required Element");
//			document.ourform.Structure.focus();
//		} else if(trim(document.ourform.Version.value)=="")
		{
			alert("2.1 Version Required Element");
			document.ourform.Version.focus();
		} else if(trim(document.ourform.Status.value)=="")
		{
			alert("2.2 Status Required Element");
			document.ourform.Status.focus();
		}
//		else if(trim(document.ourform.CatalogueEntryMetametadata.value)=="")
//		{
//			alert("3.2 Catalogue Entry Required Element");
//			document.ourform.CatalogueEntryMetametadata.focus();
//		} else if(trim(document.ourform.Metadatascheme.value)=="")
		{
			alert("3.4 Meta-data scheme Required Element");
			document.ourform.Metadatascheme.focus();
		} else if(trim(document.ourform.Format.value)=="")
		{
			alert("4.1 Format Required Element");
			document.ourform.Format.focus();
		}

//		else if(trim(document.ourform.Size.value)=="")
//		{
//			alert("4.2 Size Required Element");
//			document.ourform.Size.focus();
//		} else if(trim(document.ourform.Location.value)=="")
		{
			alert("4.3 Location Required Element");
			document.ourform.Location.focus();
		} else if(trim(document.ourform.Interactivitytype.value)=="")
		{
			alert("5.1 Interactivity Type  Required Element");
			document.ourform.Interactivitytype.focus();
		} else if(trim(document.ourform.Learningresourcetype.value)=="")
		{
			alert("5.2 Learning Resource Type Required Element");
			document.ourform.Learningresourcetype.focus();
		} else if(trim(document.ourform.Difficulty.value)=="")
		{
			alert("5.8 Difficulty Required Element");
			document.ourform.Difficulty.focus();
		} else if(trim(document.ourform.LanguageEducational.value)=="")
		{
			alert("5.11 Language Required Element");
			document.ourform.LanguageEducational.focus();
		} else if(trim(document.ourform.PurposeClassification.value)=="")
		{
			alert("9.1 PurposeClassification Required Element");
			document.ourform.PurposeClassification.focus();
		} else if(trim(document.ourform.DescriptionClassification.value)=="")
		{
			alert("9.2 DescriptionClassification Required Element");
			document.ourform.DescriptionClassification.focus();
		} else if(trim(document.ourform.KeywordsClassification.value)=="")
		{
			alert("9.3 KeywordsClassification Required Element");
			document.ourform.KeywordsClassification.focus();
		} else*/ if (getByteLength(document.ourform.Title) > 200 )
		{
			alert("1.2 Title is "+getByteLength(document.ourform.Title)+" bytes.(max:200 bytes)");
			document.ourform.Title.focus();			
		} else if (getByteLength(document.ourform.DescriptionGeneral) > 255 )
		{
			alert("1.5 Description is "+getByteLength(document.ourform.DescriptionGeneral)+" bytes.(max:255 bytes)");
			document.ourform.DescriptionGeneral.focus();			
		} else if (getByteLength(document.ourform.Version) > 200 )
		{
			alert("2.1 Version is "+getByteLength(document.ourform.Version)+" bytes.(max:200 bytes)");
			document.ourform.Version.focus();			
		} else if (getByteLength(document.ourform.Metadatascheme) > 300 )
		{
			alert("3.4 Meta-data scheme is "+getByteLength(document.ourform.Metadatascheme)+" bytes.(max:300 bytes)");
			document.ourform.Metadatascheme.focus();			
		} else if (getByteLength(document.ourform.LanguageMetametadata) > 10 )
		{
			alert("3.5 Language is "+getByteLength(document.ourform.LanguageMetametadata)+" bytes.(max:10 bytes)");
			document.ourform.LanguageMetametadata.focus();			
		} else if (getByteLength(document.ourform.Format) > 300 )
		{
			alert("4.1 Format is "+getByteLength(document.ourform.Format)+" bytes.(max:300 bytes)");
			document.ourform.Format.focus();			
		}		else if (getByteLength(document.ourform.Size) > 20 )
		{
			alert("4.2 Size is "+getByteLength(document.ourform.Size)+" bytes.(max:20 bytes)");
			document.ourform.Size.focus();			
		} else if (getByteLength(document.ourform.Location) > 300 )
		{
			alert("4.3 Location is "+getByteLength(document.ourform.Location)+" bytes.(max:300 bytes)");
			document.ourform.Location.focus();			
		} else if (getByteLength(document.ourform.Installationremarks) > 2000 )
		{
			alert("4.5 Installation Remarks is "+getByteLength(document.ourform.Installationremarks)+" bytes.(max:2000 bytes)");
			document.ourform.Installationremarks.focus();			
		} else if (getByteLength(document.ourform.Otherplatformrequirements) > 3000 )
		{
			alert("4.6 Other Platform Requirements is "+getByteLength(document.ourform.Otherplatformrequirements)+" bytes.(max:3000 bytes)");
			document.ourform.Otherplatformrequirements.focus();			
		} else if (getByteLength(document.ourform.Duration) > 20 )
		{
			alert("4.7 Duration is "+getByteLength(document.ourform.Duration)+" bytes.(max:20 bytes)");
			document.ourform.Duration.focus();			
		} else if (getByteLength(document.ourform.Learningresourcetype) > 300 )
		{
			alert("5.2 Learning Resource Type is "+getByteLength(document.ourform.Learningresourcetype)+" bytes.(max:300 bytes)");
			document.ourform.Learningresourcetype.focus();			
		} else if (getByteLength(document.ourform.Learningcontext) > 300 )
		{
			alert("5.6 Learning Context is "+getByteLength(document.ourform.Learningcontext)+" bytes.(max:300 bytes)");
			document.ourform.Learningcontext.focus();			
		} else if (getByteLength(document.ourform.Typicalagerange) > 300 )
		{
			alert("5.7 Typical Age Range is "+getByteLength(document.ourform.Typicalagerange)+" bytes.(max:300 bytes)");
			document.ourform.Typicalagerange.focus();			
		} else if (getByteLength(document.ourform.Typicallearningtime) > 20 )
		{
			alert("5.9 Typical Learning Time is "+getByteLength(document.ourform.Typicallearningtime)+" bytes.(max:20 bytes)");
			document.ourform.Typicallearningtime.focus();			
		} else if (getByteLength(document.ourform.DescriptionEducational) > 3000 )
		{
			alert("5.10 Description is "+getByteLength(document.ourform.DescriptionEducational)+" bytes.(max:3000 bytes)");
			document.ourform.DescriptionEducational.focus();			
		} else if (getByteLength(document.ourform.DescriptionRights) > 3000 )
		{
			alert("6.3 Description is "+getByteLength(document.ourform.DescriptionRights)+" bytes.(max:3000 bytes)");
			document.ourform.DescriptionRights.focus();			
		} else if (splitLength(trim(document.ourform.LanguageGeneral.value),",",10,"1.4 Language") == false ) {
			document.ourform.LanguageGeneral.focus();
		} else if (splitLength(trim(document.ourform.KeywordsGeneral.value),"|",255,"1.6 Keywords") == false ) {
			document.ourform.KeywordsGeneral.focus();
		} else if (splitLength(trim(document.ourform.Coverage.value),"|",255,"1.7 Coverage") == false ) {
			document.ourform.Coverage.focus();
		} else if (splitLength(trim(document.ourform.LanguageEducational.value),",",20,"5.11 Language") == false ) {
			document.ourform.LanguageEducational.focus();
		} else
		{
			//alert("p_process : " + document.ourform.p_process.value);
			db_popup();
			document.ourform.submit();
		}
	}

 /*** Global Variable list ****/
 var descriptionList4 = "";
 var keywordsList = "";
 var catalogueList = "";
 var entryList = "";
 var catalogueList2 = "";
 var entryList2 = "";
 var roleList = "";
 var centityList = "";
 var dateList ="";
 var roleList2 = "";
 var centityList2 = "";
 var dateList2 ="";
 var langcount = 0;
 var langcount2 = 0;
 var typeList = "";
 var nameList = "";
 var minverList = "";
 var maxverList = "";
 var intendcount = 0;
 var kindList = "";
 var resourceList = "";
 var idList = "";
 var descriptionList = "";
 var centityList3 = "";
 var dateList3 = "";
 var descriptionList2 = "";
 var purposeList = "";
 var sourceList = "";
 var descriptionList3 = "";
 var keywordsList2 = "";
 var languageList = "";
 var languageList2 = "";
 var intendedList = "";
 var sourceArray = new Array();
 var id_ClassArray = new Array();
 var entry_ClassArray = new Array();
 var info_pass = -1;
 var type_of_metadata = "content";
 var mult_check = "";
 var temp_created = false;
 var datedescriptionList = "";
 var datedescriptionList2 = "";
 var datedescriptionList3 = "";

 function trim(str)
 {
    var count = str.length;
    var len = count;                
    var st = 0;
                
    while ((st < len) && (str.charAt(st) <= ' '))
	{
         st++;
    }
    
	while ((st < len) && (str.charAt(len - 1) <= ' '))
	{
         len--;
    }                 
	 return ((st > 0) || (len < count)) ? str.substring(st, len) : str ;   
 }


 /*************************************************************************
 ** Multiplicity Handling Code
 **
 ** Purpose: To populate the main form with the information collected from
 **          the multiple elements.
 **
 *************************************************************************/

/************************************************************************
** Function: LanguageGeneral_onchange()
**
** Variables: none
**
** Description:
**       This function is called every time someone clicks on one of the
**    languages in the 1.4 Language selectbox.  It stores the list of
**    languages in a list and delimits them with a |.
*************************************************************************/
 function LanguageGeneral_onchange()
 {
// alert("LanguageGeneral_onchange");
   languageList = languageList + ourform.LanguageGeneral.value + "|";
   ourform.language_list.value = ourform.language_list.value + (langcount + 1) + ".  " + ourform.LanguageGeneral.value + "\r\n";
   // langcount: Keep track of the number of selected languages
   langcount++;
   if(langcount == 8)
   {
    ourform.language_list.value = ourform.language_list.value + "---------------------------------" + "\r\n";
    alert("Only the first 8 language you select will be supported.");
   }
 }

 /************************************************************************
** Function: clear_langList()
**
** Variables: none
**
** Description:
**    This function clears the language list, resets the language_list
**    textbox and resets the langcount.
*************************************************************************/
function clear_langList()
{
//alert("clear_langList");
  languageList = "";
  ourform.language_list.value = "";
  langcount = 0;
}

/************************************************************************
** Function: displayCatalogue(catalogues,entries)
**
** Variables: catalogues - The list of catalogues.
**         entries - The list of entires.
**
**
** Description:
**       This function pulls in the various arrays and lists (see above).
**    It then prints out the information stored within them and displays
**     them into various textareas.
*************************************************************************/
function displayCatalogue(catalogues,entries)
 {
// alert("displayCatalogue");
// alert(catalogues);
// alert(entries);
    // Variables used in this function
   catalogueList = catalogues;
   entryList = entries;
   // .split : Takes the elements delimited by a | and puts them into an array
   var catalogueArray = catalogues.split("|");
   var entryArray = entries.split("|");

   myOptions = new Array();
   myOptions2 = new Array();

   for (var i=0;i<catalogueArray.length-1;i++)
    {
       myOptions[i] = catalogueArray[i];
    }

    for (var i=0;i<entryArray.length-1;i++)
    {
      myOptions2[i] = entryArray[i];
    }

   // The following loop prints out the information to the assigned textarea.
   ourform.CatalogueEntry.value = "";
   for (var i=0;i<myOptions.length;i++)
   {
      // j : used as a number counter for display.
      var j = "";
      j = i + 1;
      ourform.CatalogueEntry.value = ourform.CatalogueEntry.value + j + ")  Catalogue: " + myOptions[i] + "    ~    Entry: " + myOptions2[i] + "\r\n";
   }
 }
 
 /***************************** General CatalogEntry ********************************/
 function modifyGeneral_CatalogEntry()
 {
 //alert("modifyGeneral_CatalogEntry");
	var catalogEntryList = ourform.CatalogueEntry.value;
	catalogueList = "";
	entryList = "";
	var Find = "\n";
	
	if(catalogEntryList.indexOf(Find)==-1) 
	{
		parent.info.location = "/learn/html/admin/metadata/1.3CatEntry.htm";
	}
	else
	{
		var FindIdx = catalogEntryList.indexOf(Find);
		var count = 0;
		var catalogueArray = catalogEntryList.split("\n");
		for(var i=0; i<catalogueArray.length-1;i++)
		{
			a = catalogueArray[i].indexOf(":")+1;
			b = catalogueArray[i].indexOf("~",a)-1;
			c = catalogueArray[i].indexOf(":",b)+1;
			realCatalogue = trim(catalogueArray[i].substring(a,b));
			realEntry = trim(catalogueArray[i].substring(c));
			catalogueList = catalogueList + realCatalogue + "|";
			entryList = entryList + realEntry + "|";
		}
		catalogueList = catalogueList.substring(0, catalogueList.length-1);
		entryList = entryList.substring(0, entryList.length-1);
		parent.info.location = "/learn/html/admin/metadata/1.3CatEntry.htm";
	}
 }
 /***************************** General CatalogEntry ********************************/


 /************************************************************************
** Function: displayDescriptions(descriptions)
**
** Variables: descriptions - The list of descriptions.
**
**
** Description:
**       This function pulls in the various arrays and lists (see above).
**    It then prints out the information stored within them and displays
**     them into various textareas.
*************************************************************************/
function displayDescription(descriptions)
 {
// alert("displayDescription");
// alert(descriptions);
    // Variables used in this function
   descriptionList4 = descriptions;

   // .split : Takes the elements delimited by a | and puts them into an array
   var descriptionArray = descriptions.split("|");

   myOptions = new Array();

   for (var i=0;i<descriptionArray.length-1;i++)
    {
       myOptions[i] = descriptionArray[i];
    }

   // The following loop prints out the information to the assigned textarea.
   ourform.DescriptionGeneral.value = "";
   for (var i=0;i<myOptions.length;i++)
   {
      // j : used as a number counter for display.
      var j = "";
      j = i + 1;
      ourform.DescriptionGeneral.value = ourform.DescriptionGeneral.value + j + ")  Description: " + myOptions[i] + "\r\n";
   }
 }

 /******************* General Description  ********************/
 function modifyGeneral_Description()
 {
 //alert("modifyGeneral_Description");
	var DescriptionGeneralList = ourform.DescriptionGeneral.value;
	descriptionList4 = "";
	var Find = "\n";
	
	if(DescriptionGeneralList.indexOf(Find)==-1)
	{
		parent.info.location = "/learn/html/admin/metadata/1.5Description.htm";
	}
	else
	{
		var FindIdx = DescriptionGeneralList.indexOf(Find);
		var count = 0;
		var DescriptionGeneralArray = DescriptionGeneralList.split("\n");

		for(var i=0; i<DescriptionGeneralArray.length-1;i++)
		{
			a = DescriptionGeneralArray[i].indexOf(":")+1;
			realDescription = trim(DescriptionGeneralArray[i].substring(a));
			descriptionList4 = descriptionList4 + realDescription + "|";
		}
		descriptionList4 =  descriptionList4.substring(0, descriptionList4.length-1);
		parent.info.location = "/learn/html/admin/metadata/1.5Description.htm";
	}
 }
/************************************************************************
** Function: displayContribute(role,centity,date,description)
**
** Variables: role - The list of roles.
**         centity - The list of centities.
**         date - The list of dates.
**		   description - The list of descriptions.
**
** Description:
**       This function pulls in the various arrays and lists (see above).
**    It then prints out the information stored within them and displays
**    them into various textareas.
*************************************************************************/
function displayContribute(role,centity,date)
 {
// alert("displayContribute");
// alert("role : " + role);
// alert("centity : " + centity);
// alert("date : " + date);
    // Variables used in this function
   // .split : Takes the elements delimited by a | and puts them into an array
   roleList = role;
   centityList = centity;
   dateList = date;

   var roleArray = role.split("|");
   var centityArray = centity.split("|");
   var dateArray = date.split("|");

   myOptions = new Array();
   myOptions2 = new Array();
   myOptions3 = new Array();

   for (var i=0;i<roleArray.length-1;i++)
    {
       myOptions[i] = roleArray[i];
    }

   for (var i=0;i<centityArray.length-1;i++)
    {
      myOptions2[i] = centityArray[i];
    }

   for (var i=0;i<dateArray.length-1;i++)
    {
      myOptions3[i] = dateArray[i];
    }

   // The following loop prints out the information to the assigned textarea.
   ourform.Contribute.value = "";
   for (var i=0;i<myOptions.length;i++)
   {
      // j: used a display counter
      var j = "";
      j = i + 1;
      ourform.Contribute.value = ourform.Contribute.value + j + ")  Role: " + myOptions[i] + "    ~    Centity: " + myOptions2[i] + "    ~    Date: " + myOptions3[i] + "\r\n";
   }
 }

 /***************************** LifeCycle Contribute  ********************************/
 function modifyLifeCycle_Contribute()
 {
// alert("modifyLifeCycle_Contribute");
	var ContributeList = ourform.Contribute.value;
	roleList = "";
	centityList = "";
	dateList = "";
	var Find = "\n";
	
	if(ContributeList.indexOf(Find)==-1)
	{
		parent.info.location = "/learn/html/admin/metadata/2.3Contribute.htm";
	}
	else
	{
		var FindIdx = ContributeList.indexOf(Find);
		var count = 0;
		var ContributeArray = ContributeList.split("\n");
		for(var i=0; i<ContributeArray.length-1;i++)
		{
			a = ContributeArray[i].indexOf(":")+1;
			b = ContributeArray[i].indexOf("~",a)-1;
			realRole = trim(ContributeArray[i].substring(a,b));
			//alert("Role : "+realRole);
			c = ContributeArray[i].indexOf(":",b)+1;
			d = ContributeArray[i].indexOf("~",c)-1;
			realCentity = trim(ContributeArray[i].substring(c,d));
			e = ContributeArray[i].indexOf(":",d)+1;
			realDate = trim(ContributeArray[i].substring(e));
			
			roleList = roleList + realRole + "|";
			centityList = centityList + realCentity + "|";
			dateList = dateList + realDate + "|";
		}
		roleList = roleList.substring(0, roleList.length-1);
		centityList = centityList.substring(0, centityList.length-1);
		dateList = dateList.substring(0, dateList.length-1);
		parent.info.location = "/learn/html/admin/metadata/2.3Contribute.htm";
	}
 }

 /***************************** LifeCycle Contribute  ********************************/

/************************************************************************
** Function: displayCatalogue2(catalogues,entries)
**
** Variables: catalogues - The list of catalogues.
**         entries - The list of entries.
**
** Description:
**       This function pulls in the various arrays and lists (see above).
**    It then prints out the information stored within them and displays
**     them into various textareas.
*************************************************************************/
 function displayCatalogue2(catalogues,entries)
 {
 //alert("displayCatalogue2");
    // Variables used in this function
   // .split : Takes the elements delimited by a | and puts them into an array
    catalogueList2 = catalogues;
   entryList2 = entries;
   var catalogueArray = catalogues.split("|");
   var entryArray = entries.split("|");

   myOptions = new Array();
   myOptions2 = new Array();

   for (var i=0;i<catalogueArray.length-1;i++)
    {
       myOptions[i] = catalogueArray[i];
    }

    for (var i=0;i<entryArray.length-1;i++)
    {
      myOptions2[i] = entryArray[i];
    }

   // The following loop prints out the information to the assigned textarea.
   ourform.CatalogueEntryMetametadata.value = "";
   for (var i=0;i<myOptions.length;i++)
   {
      var j = "";
      j = i + 1;
      ourform.CatalogueEntryMetametadata.value = ourform.CatalogueEntryMetametadata.value + j + ")  Catalogue: " + myOptions[i] + "    ~    Entry: " + myOptions2[i] + "\r\n";
    }
 }


 /***************************** Metameta CatalogEntry  ********************************/
 function modifyMetametaCatalogEntry()
 {
// alert("modifyMetametaCatalogEntry");
	var catalogEntryList = ourform.CatalogueEntryMetametadata.value;
	catalogueList2 = "";
	entryList2 = "";
	var Find = "\n";
	
	if(catalogEntryList.indexOf(Find)==-1)
	{
		parent.info.location = "/learn/html/admin/metadata/3.2CatEntry.htm";
	}
	else
	{
		var FindIdx = catalogEntryList.indexOf(Find);
		var count = 0;
		var catalogueArray = catalogEntryList.split("\n");
		for(var i=0; i<catalogueArray.length-1;i++)
		{
			a = catalogueArray[i].indexOf(":")+1;
			b = catalogueArray[i].indexOf("~",a)-1;
			c = catalogueArray[i].indexOf(":",b)+1;
			realCatalogue = trim(catalogueArray[i].substring(a,b));
			realEntry = trim(catalogueArray[i].substring(c));
			
			if(realCatalogue=="" || realCatalogue==null)
			{
				catalogueList2 = catalogueList2;
			}
			else
			{
				catalogueList2 = catalogueList2 + realCatalogue + "|";
			}
			
			if(realEntry == null || realEntry == "")
			{
				entryList2 = entryList2;
			}
			else
			{
				entryList2 = entryList2 + realEntry + "|";
			}
		}
		//catalogueList2 = catalogueList2.substring(0, catalogueList2.length-1);
		//alert("catalogueList : "+catalogueList2+".");
		entryList2 = entryList2.substring(0, entryList2.length-1);
		//alert("entryList2 : "+entryList2+".");
		parent.info.location = "/learn/html/admin/metadata/3.2CatEntry.htm";
	}
 }
 /***************************** Metameta CatalogEntry  ********************************/


/************************************************************************
** Function: displayContribute2(role,centity,date,des)
**
** Variables: role - The list of roles.
**         date - The list of dates.
**         centity - The list of centities.
**		   des - The list of descriptions.
**
** Description:
**       This function pulls in the various arrays and lists (see above).
**    It then prints out the information stored within them and displays
**    them into various textareas.
*************************************************************************/
 function displayContribute2(role,centity,date)
 {
 //alert("displayContribute2");
    // Variables used in this function
   // .split : Takes the elements delimited by a | and puts them into an array
   roleList2 = role;
   centityList2 = centity;
   dateList2 = date;

   var roleArray = role.split("|");
   var centityArray = centity.split("|");
   var dateArray = date.split("|");

   myOptions = new Array();
   myOptions2 = new Array();
   myOptions3 = new Array();

   for (var i=0;i<roleArray.length-1;i++)
    {
       myOptions[i] = roleArray[i];
    }

   for (var i=0;i<centityArray.length-1;i++)
    {
      myOptions2[i] = centityArray[i];
    }

   for (var i=0;i<dateArray.length-1;i++)
    {
      myOptions3[i] = dateArray[i];
    }

   // The following loop prints out the information to the textarea.
   ourform.ContributeMetametadata.value = "";
   for (var i=0;i<myOptions.length;i++)
   {
      // j: used as a display counter
      var j = "";
      j = i + 1;
      ourform.ContributeMetametadata.value = ourform.ContributeMetametadata.value + j + ")  Role: " + myOptions[i] + "    ~    Centity: " + myOptions2[i] + "    ~    Date: " + myOptions3[i] + "\r\n";
   }
 }

 function modifyMetametaContribute()
 {
// alert("modifyMetametaContribute");
	var ContributeList = ourform.ContributeMetametadata.value;
	roleList2 = "";
	centityList2 = "";
	dateList2 = "";
	var Find = "\n";
	
	if(ContributeList.indexOf(Find)==-1)
	{
		parent.info.location = "/learn/html/admin/metadata/3.3Contribute.htm";
	}
	else
	{
		var FindIdx = ContributeList.indexOf(Find);
		var count = 0;
		var ContributeArray = ContributeList.split("\n");
		for(var i=0; i<ContributeArray.length-1;i++)
		{
			a = ContributeArray[i].indexOf(":")+1;
			b = ContributeArray[i].indexOf("~",a)-1;
			realRole = trim(ContributeArray[i].substring(a,b));
			//alert("Role : "+realRole);
			c = ContributeArray[i].indexOf(":",b)+1;
			d = ContributeArray[i].indexOf("~",c)-1;
			realCentity = trim(ContributeArray[i].substring(c,d));
			e = ContributeArray[i].indexOf(":",d)+1;
			realDate = trim(ContributeArray[i].substring(e));
			
			
			if(realRole=="" || realRole==null)
			{
				roleList2 = roleList2;
			}
			else
			{
				roleList2 = roleList2 + realRole + "|";
			}
			
			if(realCentity==null || realCentity=="")
			{
				centityList2 = centityList2;
			}
			else
			{
				centityList2 = centityList2 + realCentity + "|";
			}

			
			if(realDate==null || realDate=="")
			{
				dateList2 = dateList2;
			}
			else
			{
				dateList2 = dateList2 + realDate + "|";
			}
			
			//alert("roleList2 : "+roleList2);
			//alert("centityList2 : "+centityList2);
			//alert("dateList2 : "+dateList2);
			//alert("descriptionList2 : "+descriptionList2);
		}
		
		roleList2 = roleList2.substring(0, roleList2.length-1);
		centityList2 = centityList2.substring(0, centityList2.length-1);
		dateList2 = dateList2.substring(0, dateList2.length-1);
		parent.info.location = "/learn/html/admin/metadata/3.3Contribute.htm";
	}
 }


/************************************************************************
** Function: displayRequirements(type,name,minver,maxver)
**
** Variables: type - The list of types.
**         name - The list of names.
**         minver - The list of minimum versions.
**         maxver - The list of maximum version.
**
** Description:
**       This function pulls in the various arrays and lists (see above).
**    It then prints out the information stored within them and displays
**     them into various textareas.
*************************************************************************/
 function displayRequirements(type,name,minver,maxver)
 {
// alert("displayRequirements");
// alert("type : " + type);
// alert("name : " + name);
// alert("minver : " + minver);
// alert("maxver : " + maxver);
    // Variables used in this function
   // .split : Takes the elements delimited by a | and puts them into an array
   typeList = type;
   nameList = name;
   minverList = minver;
   maxverList = maxver;

   var typeArray = type.split("|");
   var nameArray = name.split("|");
   var minverArray = minver.split("|");
   var maxverArray = maxver.split("|");

   myOptions = new Array();
   myOptions2 = new Array();
   myOptions3 = new Array();
   myOptions4 = new Array();

   for (var i=0;i<typeArray.length-1;i++)
    {
       myOptions[i] = typeArray[i];
    }

    for (var i=0;i<nameArray.length-1;i++)
    {
      myOptions2[i] = nameArray[i];
    }

   for (var i=0;i<minverArray.length-1;i++)
    {
      myOptions3[i] = minverArray[i];
    }

   for (var i=0;i<maxverArray.length-1;i++)
    {
      myOptions4[i] = maxverArray[i];
    }

   // The following loop prints out the information to the assigned textarea.
   ourform.Requirements.value = "";
   for (var i=0;i<myOptions.length;i++)
   {
      var j = "";
      j = i + 1;
      ourform.Requirements.value = ourform.Requirements.value + j + ")  Type: " + myOptions[i] + "    ~    Name: " + myOptions2[i] + "    ~    Minimum Version: " + myOptions3[i] + "    ~    Maximum Version: " + myOptions4[i] + "\r\n";
    }
 }

/**************************** Requirements  *****************************/
 function modifyRequirements()
 {
// alert("modifyRequirements");
	var RequirmentsList = ourform.Requirements.value;
	typeList = "";
	nameList = "";
	minverList = "";
	maxverList = "";
	var Find = "\n";
	
	if(RequirmentsList.indexOf(Find)==-1)
	{
		parent.info.location = "/learn/html/admin/metadata/4.4Requirements.htm";
	}
	else
	{
		var FindIdx = RequirmentsList.indexOf(Find);
		var count = 0;
		var RequirementArray = RequirmentsList.split("\n");
		for(var i=0; i<RequirementArray.length-1;i++)
		{		
			a = RequirementArray[i].indexOf(":")+1;
			b = RequirementArray[i].indexOf("~",a)-1;
			c = RequirementArray[i].indexOf(":",b)+1;
			d = RequirementArray[i].indexOf("~",c)-1;
			e = RequirementArray[i].indexOf(":",d)+1;
			f = RequirementArray[i].indexOf("~",e)-1;
			g = RequirementArray[i].indexOf(":",f)+1;
			realType = trim(RequirementArray[i].substring(a,b));
			realName = trim(RequirementArray[i].substring(c,d));
			realMinimum = trim(RequirementArray[i].substring(e,f));
			realMaximum = trim(RequirementArray[i].substring(g));

			typeList = typeList + realType + "|";
			nameList = nameList + realName + "|";
			minverList = minverList + realMinimum + "|";
			maxverList = maxverList + realMaximum + "|";
		}
		
		typeList = typeList.substring(0, typeList.length-1);
		nameList = nameList.substring(0, nameList.length-1);
		minverList = minverList.substring(0, minverList.length-1);
		maxverList = maxverList.substring(0, maxverList.length-1);
		parent.info.location = "/learn/html/admin/metadata/4.4Requirements.htm";
	}
 }
/**************************** Requirements *****************************/


/************************************************************************
** Function: IntendedEnd_onchange()
**
** Variables: none
**
** Description:
**    This function is called every time someone clicks on one of the
**    Intended End User Roles in the 5.5 Intended End User Role
**    selectbox.  It stores the list of roles in a list and delimits
**    them with a |.
*************************************************************************/
 function IntendedEnd_onchange()
 {
// alert("IntendedEnd_onchange");
   intendedList = intendedList + ourform.Intendedenduserrole.value + "|";
   ourform.intend_list.value = ourform.intend_list.value + (intendcount + 1) + ")  " + ourform.Intendedenduserrole.value + "\r\n"
   // intendcount : keeps track of the number of Intended End Users selected
   intendcount++;
   if (intendcount == 4)
   {
    //ourform.intend_list.value = ourform.intend_list.value + "---------------------------------" + "\r\n";
    alert("Only the first 4 intended end user roles you select will be supported.");
   }
 }

/************************************************************************
** Function: clear_intendList()
**
** Variables: none
**
** Description:
**    This function clears the intend list, resets the intend_list
**    textbox and resets the intendcount.
*************************************************************************/
 function clear_intendList()
 {
// alert("clear_intendList");
   intendedList  = "";
   ourform.intend_list.value = "";
   intendcount = 0;
 }

/************************************************************************
** Function: LanguageEducational_onchange()
**
** Variables: none
**
** Description:
**       This function is called every time someone clicks on one of the
**    languages in the 5.11 Language selectbox.  It stores the list of
**    languages in a list and delimits them with a |.
*************************************************************************/
function LanguageEducational_onchange()
 {
// alert("LanguageEducational_onchange");
   languageList2 = languageList2 + ourform.LanguageEducational.value + "|";
   ourform.language_list2.value = ourform.language_list2.value + (langcount2 + 1) + ")  " + ourform.LanguageEducational.value + "\r\n";
   // langcount2 : keeps track of the number of Languages selected
   langcount2++;
   if (langcount2 == 8)
   {
    ourform.language_list2.value = ourform.language_list2.value + "---------------------------------" + "\r\n";
    alert("Only the first 8 language you select will be supported.");
   }
 }

/************************************************************************
** Function: clear_langList2()
**
** Variables: none
**
** Description:
**    This function clears the language list #2, resets the language_list2
**    textbox and resets the langcount2.
*************************************************************************/
 function clear_langList2()
 {
// alert("clear_langList2");
   languageList2 = "";
   ourform.language_list2.value = "";
   langcount2 = 0;
 }


/************************************************************************
** Function: displayRelation(kind,description)
**
** Variables: kind - The list of kinds.
**         description - The list of descriptions.
**
** Description:
**       This function pulls in the various arrays and lists (see above).
**    It then prints out the information stored within them and displays
**     them into various textareas.
*************************************************************************/
 function displayRelation(kind,description)
 {
// alert("displayRelation");
// alert("kind : " + kind);
// alert("description : " + description);
    // Variables used in this function
   // .split : Takes the elements delimited by a | and puts them into an array
   kindList = kind;
   descriptionList = description;
   var kindArray = kind.split("|");
   var descriptionArray = description.split("|");

   myOptions = new Array();
   myOptions2 = new Array();
   myOptions3 = new Array();
   myOptions4 = new Array();

   for (var i=0;i<kindArray.length-1;i++)
    {
       myOptions[i] = kindArray[i];
    }

    for (var i=0;i<descriptionArray.length-1;i++)
    {
      myOptions4[i] = descriptionArray[i];
    }

   // The following loop prints out the information to the assinged textarea.
   ourform.Relation.value = "";
   for (var i=0;i<myOptions.length;i++)
   {
      // j: used as a display counter
      var j = "";
      j = i + 1;
      ourform.Relation.value = ourform.Relation.value + j + ")  Kind: " + myOptions[i] + "    ~    Relation: " + myOptions4[i] + "\r\n";
   }
 }

 function modifyRelation()
 {
// alert("modifyRelation");
	var RelationList = ourform.Relation.value;
	kindList = "";
	descriptionList = "";
	var Find = "\n";
	
	if(RelationList.indexOf(Find)==-1)
	{
		parent.info.location = "/learn/html/admin/metadata/7Relation.htm";
	}
	else
	{
		var FindIdx = RelationList.indexOf(Find);
		var count = 0;
		var RelationArray = RelationList.split("\n");
		
		for(var i=0; i<RelationArray.length-1;i++)
		{		
			a = RelationArray[i].indexOf(":")+1;
			b = RelationArray[i].indexOf("~",a)-1;
			c = RelationArray[i].indexOf(":",b)+1;
			realKind = trim(RelationArray[i].substring(a,b));
			realDescription = trim(RelationArray[i].substring(c));

			kindList = kindList + realKind + "|";
			descriptionList = descriptionList + realDescription + "|";
		}
		
		kindList = kindList.substring(0, kindList.length-1);
		descriptionList = descriptionList.substring(0, descriptionList.length-1);

		parent.info.location = "/learn/html/admin/metadata/7Relation.htm";
	}
 }

/************************************************************************
** Function: displayAnnotation(centity,date,description,datedes)
**
** Variables: centity - The list of centities.
**         date - The list of dates.
**         description - The list of descriptions.
**		   datedes - The list of date descriptions.
**
** Description:
**       This function pulls in the various arrays and lists (see above).
**    It then prints out the information stored within them and displays
**    them into various textareas.
*************************************************************************/
 function displayAnnotation(centity,date,description)
 {
// alert("displayAnnotation");
// alert("centity : " + centity);
// alert("date : " + date);
// alert("date : " + date);
// alert("datedes : " + datedes);
   // Variables used in this function
   // .split : Takes the elements delimited by a | and puts them into an array
   centityList3 = centity;
   dateList3 = date;
   descriptionList2 = description;

   var centityArray = centity.split("|");
   var dateArray = date.split("|");
   var descriptionArray = description.split("|");

   myOptions = new Array();
   myOptions2 = new Array();
   myOptions3 = new Array();

   for (var i=0;i<centityArray.length-1;i++)
    {
       myOptions[i] = centityArray[i];
    }

    for (var i=0;i<dateArray.length-1;i++)
    {
      myOptions2[i] = dateArray[i];
    }

   for (var i=0;i<descriptionArray.length-1;i++)
    {
      myOptions3[i] = descriptionArray[i];
    }



   // The following loop prints out the information to the assigned textarea.
   ourform.Annotation.value = "";
   for (var i=0;i<myOptions.length;i++)
   {
      var j = "";
      j = i + 1;
      ourform.Annotation.value = ourform.Annotation.value + j + ")  Centity: " + myOptions[i] + "    ~    Date: " + myOptions2[i] + "    ~    Description: " + myOptions3[i] + "\r\n";
   }
 }

function modifyAnnotation()
 {
// alert("modifyAnnotation");
	var AnnotationList = ourform.Annotation.value;
	
	centityList3 = "";
	dateList3 = "";
	descriptionList3 = "";
//	datedescriptionList3 = "";
	
	var Find = "\n";
	
	if(AnnotationList.indexOf(Find)==-1)
	{
		parent.info.location = "/learn/html/admin/metadata/8Annotation.htm";
	}
	else
	{
		var FindIdx = AnnotationList.indexOf(Find);
		var count = 0;
		var AnnotationArray = AnnotationList.split("\n");
		
		for(var i=0; i<AnnotationArray.length-1;i++)
		{		
			a = AnnotationArray[i].indexOf(":")+1;
			b = AnnotationArray[i].indexOf("~",a)-1;
			realCentity = trim(AnnotationArray[i].substring(a,b));
			c = AnnotationArray[i].indexOf(":",b)+1;
			d = AnnotationArray[i].indexOf("~",c)-1;
			realDate = trim(AnnotationArray[i].substring(c,d));
			e = AnnotationArray[i].indexOf(":",d)+1;
			//f = AnnotationArray[i].indexOf("~",e)-1;
			//realDateDescription = trim(AnnotationArray[i].substring(e));
			//g = AnnotationArray[i].indexOf(":",f)+1;
			realDescription = trim(AnnotationArray[i].substring(e));
			
			centityList3 = centityList3 + realCentity +"|";
			dateList3 = dateList3 + realDate +"|";
//			datedescriptionList3 = datedescriptionList3 + realDateDescription +"|";
			descriptionList3 = descriptionList3 + realDescription +"|";
		}
		
		centityList3 = centityList3.substring(0, centityList3.length-1);
		dateList3 = dateList3.substring(0, dateList3.length-1);
		descriptionList3 = descriptionList3.substring(0, descriptionList3.length-1);
//		datedescriptionList3 = datedescriptionList3.substring(0, datedescriptionList3.length-1);

		parent.info.location = "/learn/html/admin/metadata/8Annotation.htm";
	}
 }

/************************************************************************
** Function: displayClassification(purpose,source,id,entry,description,keywords)
**
** Variables: purpose - The list of purposes.
**         source - The array of the list of sources.
**         id - The array of arrays of lists of ids.
**         entry - The array of arrays of lists of entries.
**         description - The list of descriptions.
**         keywords - The list of keywords.
**
** Description:
**       This function pulls in the various arrays and lists (see above).
**    It then prints out the information stored within them and displays
**     them into various textareas.
*************************************************************************/
 function displayClassification(purpose,description,keywords)
 {
// alert("displayClassification");
// alert("purpose : " + purpose);
// alert("description : " + description);
// alert("keywords : " + keywords);
   // Variables used in this function
   // .split : Takes the elements delimited by a | and puts them into an array
   info_pass = 0;
   purposeList = purpose;
  //sourceList = source;
   descriptionList3 = description;
   keywordsList2 = keywords;
  //id_ClassArray = id;
  //entry_ClassArray = entry;
   var purposeArray = purpose.split("|");
   var descriptionArray = description.split("|");
   var keywordsArray = keywords.split("|");

   // Must split up the different sections of the source delimited by |
   // to different parts of the sourceArray.
  /* for (var i=1;i<source.length;i++)
   {
      sourceArray[i-1] = source[i].split("|");
   }*/

   // All temporary arrays used to temporarily store information to be
   // printed out.
   myOptions = new Array();
   myOptions2 = new Array();
   myOptions3 = new Array();
  // id_tempArray = new Array();
  // entry_tempArray = new Array();

   for (var i=0;i<purposeArray.length-1;i++)
    {
       myOptions[i] = purposeArray[i];
    }

   for (var i=0;i<descriptionArray.length-1;i++)
    {
      myOptions2[i] = descriptionArray[i];

    }

   for (var i=0;i<keywordsArray.length-1;i++)
    {
      myOptions3[i] = keywordsArray[i];

    }

   // The following loop prints out the information to the assigned textarea.
   // 9.1 Purpose
   ourform.PurposeClassification.value = "";
   for (var i=0;i<myOptions.length;i++)
   {
      // j: used as a display counter
      var j = "";
      j = i + 1;
      ourform.PurposeClassification.value = ourform.PurposeClassification.value + j + ")  Purpose: " + myOptions[i] + "\r\n";
   }

   // The following loops print out the information to the assigned textarea.
   // 9.2 TaxonPath
   //ourform.TaxonPath.value = "";
   // This first loop loops through the different number of source sections
   /*
   for (var i=0;i<sourceArray.length;i++)
   {
      // j: used as a display counter
      var j = "";
      j = i + 1;
      // This second loop loops throught he different number of sources housed
      // in each source section.
      for (var k=0;k<sourceArray[i].length-1;k++)
      {
        // l: used as a display counter
        var l = "";
        l = k + 1;
        ourform.TaxonPath.value = ourform.TaxonPath.value + j + "." + l + "  Source: " + sourceArray[i][k] + "\r\n";
      }
    }*/

   // The following loops print out the information to the assigned textareas.
   // 9.2.2 Taxon
   /*
   ourform.Taxon.value = "";
   // This first loop loops through the first numbering
   for (var i=1;i<sourceList.length;i++)
   {
      // Temporary counting variables.
      // counter/counter2: used tot array numbering
      // temp_length: used a length storage variable
      var counter = 0;
      var counter2 = 1;
      var temp_length = id[i].length;

      // This second loop loops through the number of id/entry arrays.
      for (var j=1;j<temp_length;j++)
      {
         // These temporary arrays are used to store the .split
         // information for both the ids and entriess.
            id_tempArray[counter] = id[i][j].split("|");
         entry_tempArray[counter] = entry[i][j].split("|");

         // This if statement checks to see if there is only 1 element or not
         // If there is one element the length check should not be decreased.
         if (id_tempArray[counter].length == 2)
         {
            for(var m=0;m<id[i].length-1;m++)
            {
              ourform.Taxon.value = ourform.Taxon.value + i + "." + counter2 + "." + (m+1) + "  Id: " + id_tempArray[counter][m] + "     ~    Entry: " + entry_tempArray[counter][m] + "\r\n";
            }
         }
         // If there is more than 1 element the loop needs to be decreased
         // or it will loop through too many times.
         else
         {
            for(var m=0;m<id[i].length-1;m++)
            {
              ourform.Taxon.value = ourform.Taxon.value + i + "." + counter2 + "." + (m+1) + "  Id: " + id_tempArray[counter][m] + "     ~    Entry: " + entry_tempArray[counter][m] + "\r\n";
            }
         }
         counter2++;
      }
    counter++;
   }*/

   // The following loop prints out the information to the assigned textarea.
   // 9.3 Description
   ourform.DescriptionClassification.value = "";
   for (var i=0;i<myOptions2.length;i++)
   {
      // j: used as a display counter
      var j = "";
      j = i + 1;
      ourform.DescriptionClassification.value = ourform.DescriptionClassification.value + j + ")  Description: " + myOptions2[i] + "\r\n";
   }

   // The following loop prints out the information to the assigned textarea.
   // 9.4 Keywords
   ourform.KeywordsClassification.value = "";
   for (var i=0;i<myOptions3.length;i++)
   {
      // j: used as a display counter
      var j = "";
      j = i + 1;
      ourform.KeywordsClassification.value = ourform.KeywordsClassification.value +j + ")  Keywords: " + myOptions3[i] + "\r\n";
   }
 }

 function modifyClassification()
 {
// alert("modifyClassification");
	var PurposeClassificationList = ourform.PurposeClassification.value;
	var DescriptionClassificationList = ourform.DescriptionClassification.value;
	var KeywordsClassificationList = ourform.KeywordsClassification.value;

	purposeList = "";
	descriptionList4 = "";
	keywordsList2 = "";
	
	var Find = "\n";
	
	if(PurposeClassificationList.indexOf(Find)==-1)
	{
		parent.info.location = "/learn/html/admin/metadata/9Classification.htm";
	}
	else
	{
		
		/*----------- Purpose ------------*/
		var FindIdx = PurposeClassificationList.indexOf(Find);
		var count = 0;
		var PurposeClassificationListArray = PurposeClassificationList.split("\n");
		
		for(var i=0; i<PurposeClassificationListArray.length-1;i++)
		{		
			a = PurposeClassificationListArray[i].indexOf(":")+1;
			realPurpose = trim(PurposeClassificationListArray[i].substring(a));
			
			if(realPurpose == null || realPurpose=="")
			{
				purposeList = purposeList;
			}
			else
			{				
				purposeList = purposeList + realPurpose + "|";
			}
		}
		purposeList = purposeList.substring(0, purposeList.length-1);
		/*----------- Purpose ------------*/

		/*----------- Description ------------*/
		var FindIdx = DescriptionClassificationList.indexOf(Find);
		var count = 0;
		var DescriptionClassificationListArray = DescriptionClassificationList.split("\n");
		
		for(var i=0; i<DescriptionClassificationListArray.length-1;i++)
		{		
			a = DescriptionClassificationListArray[i].indexOf(":")+1;
			realDescription = trim(DescriptionClassificationListArray[i].substring(a));
			
			if(realDescription == null || realDescription=="")
			{
				descriptionList4 = descriptionList4;
			}
			else
			{
				descriptionList4 = descriptionList4 + realDescription + "|";
			}
		}
		descriptionList4 = descriptionList4.substring(0, descriptionList4.length-1);
		//alert("descriptionList4 : "+descriptionList4);
		/*----------- Description ------------*/

		/*----------- Keywords ------------*/
		var FindIdx = KeywordsClassificationList.indexOf(Find);
		var count = 0;
		var KeywordsClassificationListArray = KeywordsClassificationList.split("\n");
		
		for(var i=0; i<KeywordsClassificationListArray.length-1;i++)
		{		
			a = KeywordsClassificationListArray[i].indexOf(":")+1;
			realKeywords = trim(KeywordsClassificationListArray[i].substring(a));
			
			if(realKeywords == null || realKeywords=="")
			{
				keywordsList2 = keywordsList2;
			}
			else
			{
				keywordsList2 = keywordsList2 + realKeywords + "|";
			}
			
		}
		keywordsList2 = keywordsList2.substring(0, keywordsList2.length-1);
		//alert("keywordsList2 : "+keywordsList2);
		/*----------- Keywords ------------*/
		
		parent.info.location = "/learn/html/admin/metadata/9Classification.htm";
	}
 }

/** End of the code used to write out the multiple elements to the textareas **/

/**************************************************************************************
** Error checking code
**
**  Purpose: To check to see if all the required elements are filled in.
**        Also checks that the fields that are filled in are in the correct format.
**************************************************************************************/

/************************************************************************
** Function: doSubmit(form)
**
** Variables: form - the contents of the form
**
** Description:
**    This function initializes the testing procedures.  It determines
**    the appropriate browser and sets to variables accordingly.
**    If a supported browser is used the function begins the test.
*************************************************************************/
function doSubmit(form)
{
   // foundError: used to tell if there is an error or not.
   foundError = false;
   ErrorMsg = "\n";

   // Checks section 1.2 Title
   if ( isFieldBlank(form.Title.value))
   {
      ErrorMsg = ErrorMsg + " \n1.2 Title is required";
      foundError = true;
   }

   // Checks section 1.3 CatalogEntry - Both 1.3.1 and 1.3.2 are required
   // Checks section 1.3.1 Catalogue
   if (catalogueList == "")
   {
        ErrorMsg = ErrorMsg + " \n1.3.1 Catalogue is required";
     foundError = true;
   }
   // Checks section 1.3.2 Entry
   if (entryList == "")
   {
        ErrorMsg = ErrorMsg + " \n1.3.2 Entry is required";
     foundError = true;
   }

   // Checks section 1.5 Description
   if ( descriptionList4 == "")
   {
      ErrorMsg = ErrorMsg + " \n1.5 Description is required.";
      foundError = true;
   }

   // Checks section 1.6 Keywords
   if ( form.KeywordsGeneral.value == "")
   {
        ErrorMsg = ErrorMsg + " \n1.6 Keywords is required";
     foundError = true;
   }

   // Checks length in section 1.6
   if ( form.KeywordsGeneral.value != "")
   {
      var key_checkArray = form.KeywordsGeneral.value.split("|");
      for (var i=0;i<key_checkArray.length;i++)
      {
         if (key_checkArray[i].length > 2024)
      {
         ErrorMsg = ErrorMsg + " \n1.6 Keywords - The following keyword exceeds the maximum # of characters" + key_checkArray[i];
         foundError = true;
      }
      }
   }

   // Checks length in section 1.7
   if ( form.Coverage.value != "")
   {
      var coverage_checkArray = form.Coverage.value.split("|");
      for (var i=0;i<coverage_checkArray.length;i++)
      {
         if (coverage_checkArray[i].length > 1024)
      {
         ErrorMsg = ErrorMsg + " \n1.7 Coverage - The following coverage exceeds the maximum # of characters" + coverage_checkArray[i];
         foundError = true;
      }
      }
   }

   // Checks section 2.1 Version
   if ( isFieldBlank(form.Version.value))



   {
        ErrorMsg = ErrorMsg + " \n2.1 Version is required";
     foundError = true;
   }

   // Checks section 2.2 Status
   if ( form.Status.selectedIndex == 0)
   {
        ErrorMsg = ErrorMsg + " \n2.2 Status is required";
     foundError = true;
   }

   //Checks section 2.3.3 for a Valid Date
   if (dateList != "")
   {
      var date_checkArray = dateList.split("|");
   for (var i=0;i<date_checkArray.length-1;i++)
   {
    if (! isValidDate(date_checkArray[i]))
    {
      ErrorMsg = ErrorMsg + " \n2.3.3 Date - The following Date is not ISO compliant " + date_checkArray[i];
      foundError = true;
    }
   }
   }

   //Checks section 3.3.3 for a Valid Date
   if (dateList2 != "")
   {
      var date_checkArray2 = dateList2.split("|");
   for (var i=0;i<date_checkArray2.length-1;i++)
   {
    if (! isValidDate(date_checkArray2[i]))
    {
      ErrorMsg = ErrorMsg + " \n3.3.3 Date - The following Date is not ISO compliant " + date_checkArray[i];
      foundError = true;
    }
   }
   }

   // Checks section 3.4 Meta-data Sceheme
   if ( isFieldBlank(form.Metadatascheme.value) )
   {
      ErrorMsg = ErrorMsg + " \n3.4 Meta-data Scheme is required";
      foundError = true;
   }

   // Checks length in section 3.4
   if (form.Metadatascheme.value != "")
   {
      var scheme_checkArray = form.Metadatascheme.value.split("|");
      for (var i=0;i<scheme_checkArray.length;i++)
      {
         if (scheme_checkArray[i].length > 32)
      {
         ErrorMsg = ErrorMsg + " \n3.4 Meta-data Scheme - The following Meta-data Scheme exceeds the maximum # of characters" + scheme_checkArray[i];
         foundError = true;
      }
      }
   }

   // Checks section 4.1 Format
   if ( isFieldBlank(form.Format.value))
   {
      ErrorMsg = ErrorMsg + " \n4.1 Format is required";
      foundError = true;
   }

   // Checks section 4.3 Location
   if ( isFieldBlank(form.Location.value) )
   {
      ErrorMsg = ErrorMsg + " \n4.3 Location is required";
      foundError = true;
   }

   // Checks section 4.7 Duration - Also checks to see if the information
   // is in the correct format.
   if( (form.Duration.value != "") && (isValidDuration(form.Duration.value) != true ))
   {
    ErrorMsg = ErrorMsg + "\n4.7 Duration - The duration you have entered is an invalid format.  Please use hh:mm:ss format.";
    foundError = true;
   }

   // Checks length in section 5.2
   if (form.Learningresourcetype.value != "")
   {
      var lrt_checkArray = form.Learningresourcetype.value.split("|");
      for (var i=0;i<lrt_checkArray.length;i++)
      {
         if (lrt_checkArray[i].length > 1024)
      {
         ErrorMsg = ErrorMsg + " \n5.2 Learning Resource Type - The following Learning Resource Type exceeds the maximum # of characters" + lrt_checkArray[i];
         foundError = true;
      }
      }
   }

   // Checks length in section 5.6
   if (form.Learningcontext.value != "")
   {
      var context_checkArray = form.Learningcontext.value.split("|");
      for (var i=0;i<context_checkArray.length;i++)
      {
         if (context_checkArray[i].length > 128)
      {
         ErrorMsg = ErrorMsg + " \n5.6 Learning Context - The following Learning Context exceeds the maximum # of characters" + context_checkArray[i];
         foundError = true;
      }
      }
   }

   // Checks length in section 5.7
   if (form.Typicalagerange.value != "")
   {
      var tar_checkArray = form.Typicalagerange.value.split("|");
      for (var i=0;i<tar_checkArray.length;i++)
      {
         if (tar_checkArray[i].length > 1024)
      {
         ErrorMsg = ErrorMsg + " \n5.7 Typical Age Range - The following Typical Age Range the maximum # of characters" + tar_checkArray[i];
         foundError = true;
      }
      }
   }

   // Checks section 5.9 Typical Learning Time - Also checks to see if the
   // information is in the correct format.
   if( (form.Typicallearningtime.value != "") && (isValidDuration(form.Typicallearningtime.value) != true ))
   {
    ErrorMsg = ErrorMsg + "\n5.9 Typical Learning Time - The duration you have entered is an invalid format.  Please use hh:mm:ss format.";
    foundError = true;
   }

   // Checks section 6.1 Cost
   if ( form.Cost.selectedIndex == 0 )
   {
      ErrorMsg = ErrorMsg + " \n6.1 Cost is required";
      foundError = true;
   }

   // Checks section 6.2 Copyright and Other Restrictions.
   else if ( form.Copyrightandotherrestrictions.selectedIndex == 0 )
   {
      ErrorMsg = ErrorMsg + " \n6.2 Copyright and Other Restrictions " +
         "is required";
      foundError = true;
   }

   //Checks section 8.2 for a Valid Date
   if (dateList3 != "")
   {
      var date_checkArray3 = dateList3.split("|");
   for (var i=0;i<date_checkArray3.length-1;i++)
   {
    if (! isValidDate(date_checkArray3[i]))
    {
      ErrorMsg = ErrorMsg + " \n8.2 Date - The following Date is not ISO compliant " + date_checkArray[i];
      foundError = true;
    }
   }
   }

   // Checks section 9.1 Purpose
   if (purposeList == "")
   {
        ErrorMsg = ErrorMsg + " \n9.1 Purpose is required";
     foundError = true;
   }

   // Checks section 9.3 Description
   if (descriptionList3 == "")
   {
        ErrorMsg = ErrorMsg + " \n9.3 Description is required";
     foundError = true;
   }

   // Checks section 9.4 Keywords
   if (keywordsList2 == "")
   {
        ErrorMsg = ErrorMsg + " \n9.4 Keywords is required";
     foundError = true;
   }


   // Returns -1 to let the program know not to proceed to the next step.
   if ( foundError == false )
   {
   return -1;
   }

   // This section runs if there was an error found.  It prints out the
   // error for the user and has an Ok button on it to close the
   // pop up window.
   else
   {
      alert( "You have not supplied all required information.  \n" +
         "Please correct the following:" + ErrorMsg +
         "\n\nSelect OK to return to the form and enter this information.");
   }
}

/************************************************************************************
** XML Generation Code (View)
**
** Purpose:  This code is generated for the view function.  This allows the user
**        to see what the saved code will look like.
************************************************************************************/

   /* need to see if these newly created lists are used anymore
   var catalogueList = "";
   var entryList = "";
   var languageList = ""; */

   // Variable used for checking length of arrays
   var length = 0;

   // Variables used to indicate which browser is being used
    var isNav4 = false;
   var isNav5 = false;
    var isIE4 = false;

    // Variable used to hold the contents of the Log for Netscape browsers
    var navTempText = "";

    // variable to hold the html content of the log
    var htmlcontent = "";

   // variable used to store the xml content for the save function.
   var xmlcontent = "";

   // variable used to store the xml content for the Test Suite
   var tscontent = "";

   //date scripting
    var currentTime = new Date();
    var hour = currentTime.getHours();
    var min = currentTime.getMinutes();
    var month = currentTime.getMonth();

    // adds one to the Month - JavaScript Months start 0 - Jan, 1 - Feb.
    month = month + 1;
    var date = currentTime.getDate()

    //************************************************************************
    // Function: initializeTest()
    //
    // Description:
    //    This function initializes the testing procedures.  It determines
    //    the appropriate browser and sets to variables accordingly.
    //    If a supported browser is used the function begins the test.
    //************************************************************************
    function initializeTest()
    {
      // Checks to make sure the browser used is at least version 4.
       if (navigator.appVersion.charAt(0) == "4")
       {
        // There is currently no netscape support
         /*if (navigator.appName == "Netscape")
          {
             isNav4 = true;
            // setUp();
          }
        */
          if (navigator.appVersion.indexOf("MSIE") != -1)
          {
             isIE4 = true;
          }
          else
          {
             alert("Browser not supported");
          }
       }

       else
       {
          alert("Browser not supported");
       }
      create();
    }

    /*************************************************************************
    ** Function: view()
    **
    ** Description:
    **    This function changes the style of the div tag called logText.
   **   This allows the user to view the XML code.
    *************************************************************************/
      function view()
      {
      logText.style.visibility = "visible";
      }

   /*************************************************************************
   ** Function: create()
   **
   ** Description:
   **       This function takes all the information from the form and writes
   **    it out to the logText layer.  This is done so that the user may
   **    view the code before choosing to save it.  This created code
   **    is not the code that is saved by the save function.
   *************************************************************************/
   function create()
   {
      // Parse and format all of the form data when this page is loaded.
      // Basic XML Header Information
      logText.insertAdjacentHTML("BeforeEnd", tag("?xml version=\"1.0\"&nbsp;encoding=\"UTF-8\"?") +
         "<br>" );
      logText.insertAdjacentHTML("BeforeEnd", tag("!DOCTYPE record SYSTEM \"IMS_METADATAv1p1.dtd\"") +
         "<br><br>" );
      logText.insertAdjacentHTML("BeforeEnd",tag("record&nbsp;xmlns=\"http:\/\/www.imsproject.org\/metadata\/\"") +
         "<br><br>" );

   // Begin Section 3 //
      // 3. MetaMetaData (Required)
      logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("metametadata") + "<br>" );

     // 3.2 Catalog Entry (Optional)
     if ((catalogueList2 != "") || (entryList2 != ""))
     {
      logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("catalogentry") + "<br>" );

        var temp_CatalogueArray2 = catalogueList2.split("|");
        var temp_EntryArray2 = entryList2.split("|");

        /* Length Check */
        length = temp_CatalogueArray2.length;
        if (temp_EntryArray2.length > length)
        {
         length = temp_EntryArray2.length;
        }
        /* End Length Check */

        // 3.2.1 Catalogue (Optional)
        for (var i=0;i<temp_CatalogueArray2.length;i++)
        {
          if (temp_CatalogueArray2[i] != "")
         {
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("catalogue") );
            logText.insertAdjacentHTML("BeforeEnd", temp_CatalogueArray2[i]);
            logText.insertAdjacentHTML("BeforeEnd", tag("/catalogue") + "<br>" );
         }
         // 3.2.2 Entry (Optional)
         if (temp_EntryArray2[i] != "")
         {
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("entry") + "<br>");
            logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("langstring") );
            logText.insertAdjacentHTML("BeforeEnd", temp_EntryArray2[i]);
            logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("/entry") + "<br>" );
         }
        }
      logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/catalogentry") + "<br>" );
     }

          //3.3 Contribute (Optional)
     if (ourform.ContributeMetametadata.value != "")
     {
        var temp_roleArray2 = roleList2.split("|");
        var temp_centityArray2 = centityList2.split("|");
        var temp_dateArray2 = dateList2.split("|");
		var temp_desArray2 = datedescriptionList2.split("|");

        /* Length Check */
        length = temp_roleArray2.length;
        if (temp_centityArray2.length > length)
        {
         length = temp_centityArray2.length;
        }
        if (temp_dateArray2.length > length)
        {
         length = temp_dateArray2.length;
        }
        /* End Length Check */

        // 3.3.1 Role - List (Optional)
        length--;
        for (var i=0;i<length;i++)
        {

         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("contribute") + "<br>" );
         if (temp_roleArray2[i] != "")
         {
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("role") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("langstring") );
            logText.insertAdjacentHTML("BeforeEnd", temp_roleArray2[i]);
            logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("/role") + "<br>" );
         }

         // 3.3.2 Centity (Optional)
         if (temp_centityArray2[i] != "")
         {
           var temp_cent2 = temp_centityArray2[i];
           var if_comma3 = false;
           for (var k=0;k<temp_cent2.length;k++)
              {
             if (temp_cent2.charAt(k) == "~")
            {
              if_comma3 = true;
            }
           }
           if (if_comma3)
           {
            var temp_centArray2 = temp_cent2.split("~");
            var length_cent2 = temp_centArray2.length;
            for(var j=0;j<length_cent2;j++)
            {
               var arr_temp = temp_centArray2[j];
               logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("centity") + "<br>" );
               logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("vcard") + "<br>" );
               logText.insertAdjacentHTML("BeforeEnd", tab(5) + "begin:vcard" + "<br>");
               logText.insertAdjacentHTML("BeforeEnd", arr_temp + "<br>");
               logText.insertAdjacentHTML("BeforeEnd", tab(5) + "end:vcard" + "<br>");
               logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("/vcard") + "<br>" );
               logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("/centity") + "<br>" );
            }
           }
           else
           {
               logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("centity") + "<br>" );
               logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("vcard") + "<br>" );
               logText.insertAdjacentHTML("BeforeEnd", tab(5) + "begin:vcard" + "<br>");
               logText.insertAdjacentHTML("BeforeEnd", temp_cent2 + "<br>");
               logText.insertAdjacentHTML("BeforeEnd", tab(5) + "end:vcard" + "<br>");
               logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("/vcard") + "<br>" );
               logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("/centity") + "<br>" );
           }
         }

         // 3.3.3 Date (Optional)
         if (temp_dateArray2[i] != "")
         {
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("date") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("datetime") );
            logText.insertAdjacentHTML("BeforeEnd", temp_dateArray2[i]);
	        logText.insertAdjacentHTML("BeforeEnd", tag("/datetime") + "<br>" );
			if (temp_desArray2[i] != "")
			{
			    logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("description") );
    	        logText.insertAdjacentHTML("BeforeEnd", temp_desArray2[i]);
	            logText.insertAdjacentHTML("BeforeEnd", tag("/description") + "<br>" );
			}
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("/date") + "<br>" );
         }
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/contribute") + "<br>" );
        }
     }



     // 3.4 Meta-data Scheme (Required)
     var SchemeArray = ourform.Metadatascheme.value.split("|");
     for (var i=0;i<SchemeArray.length;i++)
     {
        logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("metadatascheme") );
        logText.insertAdjacentHTML("BeforeEnd", SchemeArray[i]);
         logText.insertAdjacentHTML("BeforeEnd", tag("/metadatascheme") + "<br>" );
     }

     // 3.5 Language (Optional)
     var MetadataLanguage = ourform.LanguageMetametadata.value;
     if (MetadataLanguage != "")
     {
        logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("language") );
        logText.insertAdjacentHTML("BeforeEnd", MetadataLanguage);
         logText.insertAdjacentHTML("BeforeEnd", tag("/language") + "<br>" );
     }

      logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("/metametadata") + "<br><br>" );
   // End Section 3 //

   // Begin Section 1 //
      // 1. General (Required)
      logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("general") + "<br>" );

      // 1.2 Title (Required)
      logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("title") + "<br>" );
      logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
      logText.insertAdjacentHTML("BeforeEnd", ourform.Title.value);
      logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
      logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/title") + "<br>" );


      // 1.3 Catalog Entry (Required)
     var temp_catalogueArray = catalogueList.split("|");
     var temp_entryArray = entryList.split("|");

     /* Length Check */
     length = temp_catalogueArray.length;
     if (temp_entryArray.length > length)
     {
      length = temp_entryArray.length;
     }
     /* End Length Check */

     for (var i=0;i<length-1;i++)
     {
        logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("catalogentry") + "<br>" );

      // 1.3.1 Catalog  (Required)
      logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("catalogue") );
       logText.insertAdjacentHTML("BeforeEnd", temp_catalogueArray[i] );
         logText.insertAdjacentHTML("BeforeEnd", tag("/catalogue") + "<br>" );

       // 1.3.2 Entry (Required)
      logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("entry") + "<br>" );
       logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("langstring") );
       logText.insertAdjacentHTML("BeforeEnd", temp_entryArray[i] );
       logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
         logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("/entry") + "<br>" );

       logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/catalogentry") + "<br>" );
     }


      // 1.4 Language (Optional)
     var langGen = ourform.LanguageGeneral.value;
     if (langGen != "")
     {
       var temp_langArray = langGen.split(",");
      for (var i=0;i<temp_langArray.length;i++)
      {
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("language") );
         logText.insertAdjacentHTML("BeforeEnd", temp_langArray[i]);
         logText.insertAdjacentHTML("BeforeEnd", tag("/language") + "<br>" );
      }
     }


      // 1.5 Description (Required)
     var desc = descriptionList4;
     var temp_descArray = desc.split("|");
     var desc_temp_length = temp_descArray.length;
     desc_temp_length--;
     for (var i=0;i<desc_temp_length;i++)
     {
          logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("description") + "<br>" );
        logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
       logText.insertAdjacentHTML("BeforeEnd", temp_descArray[i]);
          logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
          logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/description") + "<br>" );
     }

      // 1.6 Keywords (Required)
     var temp_keywordsArray = ourform.KeywordsGeneral.value.split("|");
     for (var i=0;i<temp_keywordsArray.length;i++)
     {
      logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("keywords") + "<br>" );
      logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
      logText.insertAdjacentHTML("BeforeEnd", temp_keywordsArray[i]);
      logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
      logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/keywords") + "<br>" );
     }

     // 1.7 Coverage (Optional)
     if (ourform.Coverage.value != "")
     {
        var temp_coverageArray = ourform.Coverage.value.split("|");

        for (var i=0;i<temp_coverageArray.length;i++)
        {
           logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("coverage") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
            logText.insertAdjacentHTML("BeforeEnd", temp_coverageArray[i]);
            logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
           logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/coverage") + "<br>" );
        }
      }

      // 1.8 Structure (Optional)
     if (ourform.Structure.selectedIndex != 0)
     {
        logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("structure") + "<br>" );
         logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
         logText.insertAdjacentHTML("BeforeEnd", ourform.Structure.value);
         logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
        logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/structure") + "<br>" );
     }

      // 1.9 Aggregation Level (Optional)
     if ( ourform.Aggregationlevel.selectedIndex != 0)
     {
     logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("aggregationlevel") );
     logText.insertAdjacentHTML("BeforeEnd", ourform.Aggregationlevel.value );
     logText.insertAdjacentHTML("BeforeEnd", tag("/aggregationlevel") + "<br>" );
     }

    logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("/general") + "<br><br>" );
   // End Section 1 //
   // Begin Section 2 //

      // 2. LifeCycle (Required)
        logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("lifecycle") + "<br>" );

      // 2.1 Version (Required)
     logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("version") + "<br>" );
      logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
      logText.insertAdjacentHTML("BeforeEnd", ourform.Version.value);
      logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
      logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/version") + "<br>" );

     // 2.2 Status (Required)
     logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("status") + "<br>" );
      logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
        logText.insertAdjacentHTML("BeforeEnd", ourform.Status.value);
      logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
        logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/status") + "<br>" );

     // 2.3 Contribute (Optional)
      if ((roleList != "") || (centityList != "") || (dateList != ""))
      {
        var temp_roleArray = roleList.split("|");
        var temp_centityArray = centityList.split("|");
        var temp_dateArray = dateList.split("|");
		var temp_datedesArray = datedescriptionList.split("|");

        /* check for largest length */
         length = temp_roleArray.length;
         if (temp_centityArray.length > length)
         {
           length = temp_roleArray.length;
         }
         if (temp_dateArray.length > length)
         {
           length = temp_dateArray.length;
         }
        /* end check */
        length--;
        for (var i=0;i<length;i++)
        {
        logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("contribute") + "<br>" );
         var temp_date = temp_dateArray[i];
		 var temp_des = temp_datedesArray[i];

          // 2.3.1 Role - List (Optional)
         if (temp_roleArray[i] != "")
         {
              logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("role") + "<br>" );
             logText.insertAdjacentHTML("BeforeEnd", tab(5) + tag("langstring") );
             logText.insertAdjacentHTML("BeforeEnd", temp_roleArray[i]);
             logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
             logText.insertAdjacentHTML("BeforeEnd", tab(3) +tag("/role") + "<br>" );
         }

          // 2.3.2 Entity (Optional)
          // Uses CEntity_Type to write either ORG: or FN:
           // Assumes this is correct.
         if (temp_centityArray[i] != "")
         {
              var temp_cent = temp_centityArray[i];
           var if_comma2 = false;
           for (var k=0;k<temp_cent.length;k++)
           {
             if (temp_cent.charAt(k) == "~")
            {
              if_comma2 = true;
            }
           }
           if (if_comma2)
           {
            var temp_centArray = temp_cent.split("~");
            var length_cent = temp_centArray.length;
            for(var j=0;j<length_cent;j++)
            {
              var arr_temp = temp_centArray[j];
                logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("centity") + "<br>" );
                 logText.insertAdjacentHTML("BeforeEnd", tab(4)+ tag("vcard") + "<br>" );
                 logText.insertAdjacentHTML("BeforeEnd", tab(5) + "BEGIN:vCard<br>" );
                 logText.insertAdjacentHTML("BeforeEnd", tab(5) + arr_temp + "<br>");
                 logText.insertAdjacentHTML("BeforeEnd", tab(5) + "END:vCard<br>" );
                 logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("/vcard") + "<br>" );
                 logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("/centity") + "<br>" );
            }
           }
           else
           {
                logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("centity") + "<br>" );
                 logText.insertAdjacentHTML("BeforeEnd", tab(4)+ tag("vcard") + "<br>" );
                 logText.insertAdjacentHTML("BeforeEnd", tab(5) + "BEGIN:vCard<br>" );
                 logText.insertAdjacentHTML("BeforeEnd", tab(5) + temp_cent + "<br>");
                 logText.insertAdjacentHTML("BeforeEnd", tab(5) + "END:vCard<br>" );
                 logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("/vcard") + "<br>" );
                 logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("/centity") + "<br>" );
           }
         }

         // 2.3.3 Date (Optional)
         if (temp_date != "")
         {
             logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("date") + "<br>" );
             logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("datetime") );
             logText.insertAdjacentHTML("BeforeEnd", temp_date);
             logText.insertAdjacentHTML("BeforeEnd", tag("/datetime") + "<br>" );
			 if (temp_des != "")
			 {
				 logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("description") );
	             logText.insertAdjacentHTML("BeforeEnd", temp_des);
	             logText.insertAdjacentHTML("BeforeEnd", tag("/description") + "<br>" );
			 }
             logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("/date") + "<br>" );
         }
           logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/contribute") + "<br>" );
        }
      }
       logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("/lifecycle") + "<br><br>" );
   // End Section 2 //
   // Begin Section 4 //

      // 4. Technical (Required)
      logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("technical") + "<br>" );

      // 4.1 Format (Required)
      logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("format") + "<br>" );
      logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
      logText.insertAdjacentHTML("BeforeEnd", ourform.Format.value);
      logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
      logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/format") + "<br>" );

      // 4.2 Size (Optional)
      if ( ourform.Size.value != "" )
      {
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("size") );
         logText.insertAdjacentHTML("BeforeEnd", ourform.Size.value);
         logText.insertAdjacentHTML("BeforeEnd", tag("/size") + "<br>" );
      }

      // 4.3 Location (Required)
     var temp_locArray = ourform.Location.value.split(",");
     for (var i=0;i<temp_locArray.length;i++)
     {
        logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("location") );
        logText.insertAdjacentHTML("BeforeEnd", temp_locArray[i]);
        logText.insertAdjacentHTML("BeforeEnd", tag("/location") + "<br>" );
     }

      // 4.4 Requirements (Optional)
     if ((typeList != "") || (nameList != "") || (minverList != "") || (maxverList != ""))
     {
      var temp_typeArray = typeList.split("|");
      var temp_nameArray = nameList.split("|");
      var temp_minverArray = minverList.split("|");
      var temp_maxverArray = maxverList.split("|");

      /* Length Check */
      length = temp_typeArray.length;
      if (temp_nameArray.length > length)
      {
        length = temp_nameArray.length;
      }
      if (temp_minverArray.length > length)
      {
        length = temp_minverArray.length;
      }
      if (temp_maxverArray.length > length)
      {
        length = temp_maxverArray.length;
      }
      /* End Length Check */

      for (var i=0;i<length;i++)
      {
        logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("requirements") + "<br>" );

          // 4.4.1 Type - List (Optional)
        if (temp_typeArray[i] != "")
        {
           logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("type") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("langstring") );
            logText.insertAdjacentHTML("BeforeEnd", temp_typeArray[i]);
            logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("/type") + "<br>" );
        }

          // 4.4.2 Name (Optional)
        if (temp_nameArray[i] != "")
        {
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("name") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("langstring") );
            logText.insertAdjacentHTML("BeforeEnd", temp_nameArray[i]);
            logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("/name") + "<br>" );
        }

          // 4.4.3 Minimum Version (Optional)
        if (temp_minverArray[i] != "")
        {
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("minimumversion") );
            logText.insertAdjacentHTML("BeforeEnd", temp_minverArray[i]);
            logText.insertAdjacentHTML("BeforeEnd", tag("/minimumversion") + "<br>" );
        }

        // 4.4.4 Maximum Version (Optional)
        if (temp_maxverArray[i] != "")
        {
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("maximumversion") );
            logText.insertAdjacentHTML("BeforeEnd", temp_maxverArray[i]);
            logText.insertAdjacentHTML("BeforeEnd", tag("/maximumversion") + "<br>" );
        }
          logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/requirements") + "<br>" );
      }
     }

      // 4.5 Installation Remarks (Optional)
      if ( ourform.Installationremarks.value != "" )
      {
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("installationremarks") + "<br>" );
         logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
         logText.insertAdjacentHTML("BeforeEnd", ourform.Installationremarks.value);
         logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/installationremarks") + "<br>" );
      }

      // 4.6 Other Platform Requirements (Optional)
      if ( ourform.Otherplatformrequirements.value != "" )
      {
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("otherplatformrequirements") + "<br>" );
         logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
         logText.insertAdjacentHTML("BeforeEnd", ourform.Otherplatformrequirements.value);
         logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/otherplatformrequirements") + "<br>" );
      }

      // 4.7 Duration (Optional)
      if ( ourform.Duration.value != "" )
      {
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("duration") + "<br>" );
         logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("datetime"));
         logText.insertAdjacentHTML("BeforeEnd", ourform.Duration.value);
         logText.insertAdjacentHTML("BeforeEnd", tag("/datetime") + "<br>" );
		 if ( outform.DurationDescription.value != "")
		 {
	         logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("description"));
	         logText.insertAdjacentHTML("BeforeEnd", ourform.DurationDescription.value);
	         logText.insertAdjacentHTML("BeforeEnd", tag("/description") + "<br>" );
		 }
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/duration") + "<br>" );
      }
      logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("/technical") + "<br><br>" );
   // End Section 4 //
   // Begin Section 5 //

      // <!-- 5. Educational (Optional) -->
     if ((ourform.Interactivitytype.selectedIndex != 0) || (ourform.Learningresourcetype.value != "") || (ourform.Interactivitylevel.selectedIndex != 0) || (ourform.Semanticdensity.selectedIndex != 0) || (intendedList != "") || (ourform.Learningcontext.value != "") || (ourform.Typicalagerange.value != "") || (ourform.Difficulty.selectedIndex != 0) || (ourform.Typicallearningtime.value != "") || (ourform.DescriptionEducational.value != "") || (ourform.LanguageEducational.value != ""))
     {
        logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("educational") + "<br>" );

       // 5.1 Interactivity Type (Optional)
       if ( ourform.Interactivitytype.selectedIndex != 0)
       {
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("interactivitytype") + "<br>" );
         logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
         logText.insertAdjacentHTML("BeforeEnd", ourform.Interactivitytype.value );
         logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/interactivitytype") + "<br>" );
       }

       //  5.2 Learning Resource Type - List (Optional)
         if ( ourform.Learningresourcetype.value != "" )
       {
           var temp_lrt = ourform.Learningresourcetype.value;
           var if_comma3 = false;
           for (var k=0;k<temp_lrt.length;k++)
           {
             if (temp_lrt.charAt(k) == "|")
            {
              if_comma3 = true;
            }
           }
           if (if_comma3)
           {
            var temp_lrtArray = temp_lrt.split("|");
            var length_lrt = temp_lrtArray.length;
            for(var j=0;j<length_lrt;j++)
            {
                  logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("learningresourcetype") + "<br>" );
                  logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
                 logText.insertAdjacentHTML("BeforeEnd", temp_lrtArray[j]);
                logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
                  logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/learningresourcetype") + "<br>" );
            }
           }
           else
           {
            logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("learningresourcetype") + "<br>" );
               logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
              logText.insertAdjacentHTML("BeforeEnd", ourform.Learningresourcetype.value);
             logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
               logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/learningresourcetype") + "<br>" );
           }
         }

       // 5.3 Interactivity Level (Optional)
       if ( ourform.Interactivitylevel.selectedIndex != 0)
       {
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("interactivitylevel") );
         logText.insertAdjacentHTML("BeforeEnd", ourform.Interactivitylevel.value);
         logText.insertAdjacentHTML("BeforeEnd", tag("/interactivitylevel") + "<br>" );
       }

       // 5.4 Semantic Density (Optional)
         if ( ourform.Semanticdensity.selectedIndex != 0)
       {
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("semanticdensity") );
         logText.insertAdjacentHTML("BeforeEnd", ourform.Semanticdensity.value);
         logText.insertAdjacentHTML("BeforeEnd", tag("/semanticdensity") + "<br>" );
       }

       // 5.5 Intended End User Role (Optional)
       if ( intendedList != "" )
       {
         var temp_intendedArray = intendedList.split("|");
         for (var i=0;i<temp_intendedArray.length-1;i++)
         {
            logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("intendedenduserrole") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
            logText.insertAdjacentHTML("BeforeEnd", temp_intendedArray[i]);
            logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/intendedenduserrole") + "<br>" );
         }
       }

       // 5.6 Learning Context (Optional)
       if ( ourform.Learningcontext.value != "")
       {
           var temp_lc = ourform.Learningcontext.value;
           var if_comma4 = false;
           for (var k=0;k<temp_lc.length;k++)
           {
             if (temp_lc.charAt(k) == "|")
            {
              if_comma4 = true;
            }
           }
           if (if_comma4)
           {
            var temp_lcArray = temp_lc.split("|");
            var length_lc = temp_lcArray.length;
            for(var j=0;j<length_lc;j++)
            {
               logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("learningcontext") + "<br>" );
               logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
               logText.insertAdjacentHTML("BeforeEnd", temp_lcArray[j]);
               logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
               logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/learningcontext") + "<br>" );
            }
           }
           else
           {
            logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("learningcontext") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
            logText.insertAdjacentHTML("BeforeEnd", ourform.Learningcontext.value);
            logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/learningcontext") + "<br>" );
            }
       }

       // 5.7 Typical Age Range (Optional)
       if ( ourform.Typicalagerange.value != "")
       {
           var temp_tar = ourform.Typicalagerange.value;
           var if_comma5 = false;
           for (var k=0;k<temp_tar.length;k++)
           {
             if (temp_tar.charAt(k) == "|")
            {
              if_comma5 = true;
            }
           }
           if (if_comma5)
           {
            var temp_tarArray = temp_tar.split("|");
            var length_tar = temp_tarArray.length;
            for(var j=0;j<length_tar;j++)
            {
               logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("typicalagerange") + "<br>" );
               logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
               logText.insertAdjacentHTML("BeforeEnd", temp_tarArray[j]);
               logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
               logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/typicalagerange")+ "<br>" );
            }
           }
           else
           {
            logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("typicalagerange") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
            logText.insertAdjacentHTML("BeforeEnd", ourform.Typicalagerange.value);
            logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/typicalagerange")+ "<br>" );
            }
       }

       // 5.8 Difficulty (Optional)
       if ( ourform.Difficulty.selectedIndex != 0)
       {
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("difficulty") );
         logText.insertAdjacentHTML("BeforeEnd", ourform.Difficulty.value);
         logText.insertAdjacentHTML("BeforeEnd", tag("/difficulty") + "<br>" );
       }

       // 5.9 Typical Learning Time (Optional)
         if ( ourform.Typicallearningtime.value != "" )
         {
            logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("typicallearningtime") + "<br>" );
	        logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("datetime"));
            logText.insertAdjacentHTML("BeforeEnd", ourform.Typicallearningtime.value);
            logText.insertAdjacentHTML("BeforeEnd", tag("/datetime") + "<br>" );
			if ( ourform.TLTDescription.value != "")
			{
	            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("description"));
	            logText.insertAdjacentHTML("BeforeEnd", ourform.TLTDescription.value);
	            logText.insertAdjacentHTML("BeforeEnd", tag("/description") + "<br>" );
			}

            logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/typicallearningtime") + "<br>" );
         }

       // 5.10 Description (Optional)
       if ( ourform.DescriptionEducational.value != "")
       {
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("description") + "<br>" );
         logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
         logText.insertAdjacentHTML("BeforeEnd", ourform.DescriptionEducational.value);
         logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/description") + "<br>" );
       }

       // 5.11 Language (Optional)
       var langEdu = ourform.LanguageEducational.value;
       if ( langEdu != "")
       {
        var temp_langArray2 = langEdu.split(",");
         for(var i=0;i<temp_langArray2.length;i++)
         {
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("language") );
         logText.insertAdjacentHTML("BeforeEnd", temp_langArray2[i]);
         logText.insertAdjacentHTML("BeforeEnd", tag("/language") + "<br>" );
         }
       }


         logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("/educational") + "<br><br>" );
     }
   // End Section 5 //
   // Begin Section 6 //

      // 6. Rights (Required)
      logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("rights") + "<br>" );

      // 6.1 Cost (Required)
      logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("cost") + "<br>" );
      logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
      logText.insertAdjacentHTML("BeforeEnd", ourform.Cost.value);
      logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
      logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/cost") + "<br>" );

      // 6.2 Copyright and Other Restrictions (Required)
      logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("copyrightandotherrestrictions") + "<br>" );
      logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
      logText.insertAdjacentHTML("BeforeEnd", ourform.Copyrightandotherrestrictions.value);
      logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
      logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/copyrightandotherrestrictions") + "<br>" );

      // 6.3 Description (Optional)
      if ( ourform.DescriptionRights.value != "" )
      {
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("description") + "<br>" );
         logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
         logText.insertAdjacentHTML("BeforeEnd", ourform.DescriptionRights.value);
         logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
         logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/description") + "<br>" );
      }
      logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("/rights") + "<br><br>" );
   // End Section 6 //
   // Begin Section 7 //

     // <!-- 7. Relation (Optional) -->
     if ((kindList != "") || (descriptionList != ""))
     {
      var temp_kindArray = kindList.split("|");
       var temp_desArray = descriptionList.split("|");
      /* Length Check */
      var length_relation = 0;
      length_relation = temp_kindArray.length;
      if (temp_desArray.length > length_relation)
      {
         length_relation = temp_desArray.length;
      }
      /* End Length Check */
       length_relation--;

      for (var i=0;i<length_relation;i++)
      {
         var des = temp_desArray[i];
         var kin = temp_kindArray[i];

          logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("relation") + "<br>" );
         // 7.1 Kind - List (Optional)
         if ( kin != "" )
         {
              logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("kind") + "<br>" );
           logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
           logText.insertAdjacentHTML("BeforeEnd", kin);
           logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
           logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/kind") + "<br>");
         }

         // 7.2 Resource & 7.2.2 Description (Optional)
         if ( des != "")
         {
            logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("resource") + "<br>" );

            // 7.2.2 Description
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("description") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(4) + tag("langstring") );
            logText.insertAdjacentHTML("BeforeEnd", des);
            logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("/description") + "<br>" );

            logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/resource") + "<br>" );
         }
         logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("/relation") + "<br><br>" );
      }
     }
   // End Section 7 //
   // Begin Section 8 //

     // <!-- 8. Annotation (Optional) -->
     if ((centityList3 != "")||(dateList3 != "")||(descriptionList2 != ""))
     {
      var temp_cent3Array = centityList3.split("|");
      var temp_date3Array = dateList3.split("|");
      var temp_desc2Array = descriptionList2.split("|");
      var temp_datedesArray = datedescriptionList3.split("|");

      /* Length Check */
      var anno_length = temp_cent3Array.length;
      if (temp_date3Array.length > anno_length)
      {
         anno_length = temp_date3Array.length;
      }
      if (temp_desc2Array.length > anno_length)
      {
         anno_length = temp_desc2Array.length;
      }
      anno_length--;
      /* End Length Check */
      for (var i=0;i<anno_length;i++)
      {
         var cent3 = temp_cent3Array[i];
         var date3 = temp_date3Array[i];
         var desc2 = temp_desc2Array[i];
		 var datedes = temp_datedesArray[i];

            logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("annotation") + "<br>" );
         // 8.1 Centity (Optional)
         if ( cent3 != "")
         {
           logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("centity") + "<br>" );
           logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("vcard") + "<br>" );
           logText.insertAdjacentHTML("BeforeEnd", tab(4) + "begin : vcard" + "<br>" );
           logText.insertAdjacentHTML("BeforeEnd", tab(4) + "org :" + cent3 + "<br>" );
           logText.insertAdjacentHTML("BeforeEnd", tab(4) + "end : vcard" + "<br>" );
           logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("/vcard") + "<br>" );
           logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/centity") + "<br>" );
         }

         // 8.2 Date (Optional)
         if ( date3 != "")
         {
           logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("date") + "<br>" );
           logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("datetime") );
           logText.insertAdjacentHTML("BeforeEnd", date3);
           logText.insertAdjacentHTML("BeforeEnd", tag("/datetime") + "<br>" );
		   if ( datedes != "")
		   {
	           logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("description") );
	           logText.insertAdjacentHTML("BeforeEnd", datedes);
	           logText.insertAdjacentHTML("BeforeEnd", tag("/description") + "<br>" );
           }
           logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/date") + "<br>" );
         }

         // 8.3 Description (Optional)
         if ( desc2 != "")
         {
           logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("description") + "<br>" );
           logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
           logText.insertAdjacentHTML("BeforeEnd", desc2);
           logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
           logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/description") + "<br>" );
         }
         logText.insertAdjacentHTML("BeforeEnd",tab(1) + tag("/annotation") + "<br><br>" );
      }
     }

   // End Section 8 //

   // Begin Section 9 //
      // 9. Classification (Required)
      var temp_purposeArray = purposeList.split("|");
      var temp_desc3Array = descriptionList3.split("|");
      var temp_key2Array = keywordsList2.split("|");

      /* length check */
      var length_class = temp_purposeArray.length;
      if (temp_desc3Array.length > length_class)
      {
         length_class = temp_desc3Array.length;
      }
      if (temp_key2Array.length > length_class)
      {
         length_class = temp_key2Array.length;
      }
      length_class--;
      /* end Length Check */

      for (var i=0;i<length_class;i++)
      {
         var pur = temp_purposeArray[i];
         var desc3 = temp_desc3Array[i];
         var key2 = temp_key2Array[i];

         logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("classification") + "<br>" );

             // 9.1 Purpose - List (Required)
            logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("purpose") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
            logText.insertAdjacentHTML("BeforeEnd", pur);
            logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/purpose") + "<br>" );


             // 9.3 Description (Required)
            logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("description") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
            logText.insertAdjacentHTML("BeforeEnd", desc3);
            logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
            logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/description") + "<br>" );

            // 9.4 Keywords (Required)
              var temp_keyword = key2;
           var if_comma5 = false;
           for (var k=0;k<temp_keyword.length;k++)
           {
             if (temp_keyword.charAt(k) == "~")
            {
              if_comma5 = true;
            }
           }
           if (if_comma5)
           {
            var temp_key_2Array = temp_keyword.split("~");
            var length_key = temp_key_2Array.length;
            for(var j=0;j<length_key;j++)
            {
                  logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("keywords") + "<br>" );
                  logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
                  logText.insertAdjacentHTML("BeforeEnd", temp_key_2Array[j]);
                  logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
                  logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/keywords") + "<br>" );
              }
           }
           else
           {
                  logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("keywords") + "<br>" );
                  logText.insertAdjacentHTML("BeforeEnd", tab(3) + tag("langstring") );
                  logText.insertAdjacentHTML("BeforeEnd", key2);
                  logText.insertAdjacentHTML("BeforeEnd", tag("/langstring") + "<br>" );
                  logText.insertAdjacentHTML("BeforeEnd", tab(2) + tag("/keywords") + "<br>" );
            }
         logText.insertAdjacentHTML("BeforeEnd", tab(1) + tag("/classification") + "<br><br>" );
      }
   // End Section 9 //
      logText.insertAdjacentHTML("BeforeEnd", tag("/record") );
   }


    /*************************************************************************
    ** Function: create2()
    **
    ** Description:
    **   This function takes all the information from the form and writes
    **      it out to the xmlcontent variable.  This variable is used by the
   **    save function to save the xml in the correct format.
    *************************************************************************/
   function create2()
      {
         // Basic XML Header Information
         xmlcontent = xmlcontent + tag2("?xml version=\"1.0\" encoding=\"UTF-8\"?")+"\n";

         xmlcontent = xmlcontent + tag2("!DOCTYPE record SYSTEM \"IMS_METADATAv1p1.dtd\"")+"\n";

         xmlcontent = xmlcontent + tag2("record xmlns=\"http:\/\/www.imsproject.org\/metadata\/\"")+"\n";

      // Begin Section 3 //
         // 3. MetaMetaData (Required)
         xmlcontent = xmlcontent + tag2("metametadata")

        // 3.2 Catalog Entry (Optional)
        if ((catalogueList2 != "") || (entryList2 != ""))
        {
        xmlcontent = xmlcontent + tag2("catalogentry")+"\n";
           var temp_CatalogueArray2 = catalogueList2.split("|");
           var temp_EntryArray2 = entryList2.split("|");

           /* Length Check */
           length = temp_CatalogueArray2.length;
           if (temp_EntryArray2.length > length)
           {
            length = temp_EntryArray2.length;
           }
           /* End Length Check */

           // 3.2.1 Catalogue (Optional)
           for (var i=0;i<temp_CatalogueArray2.length;i++)
           {
             if (temp_CatalogueArray2[i] != "")
            {
               xmlcontent = xmlcontent + tag2("catalogue")+"\n";
               xmlcontent = xmlcontent + temp_CatalogueArray2[i]+"\n";
               xmlcontent = xmlcontent + tag2("/catalogue")+"\n";
            }
            // 3.2.2 Entry (Optional)
            if (temp_EntryArray2[i] != "")
            {
               xmlcontent = xmlcontent + tag2("entry")+"\n";
               xmlcontent = xmlcontent + tag2("langstring")+"\n";
               xmlcontent = xmlcontent + temp_EntryArray2[i]+"\n";
               xmlcontent = xmlcontent + tag2("/langstring")+"\n";
               xmlcontent = xmlcontent + tag2("/entry")+"\n";
            }
           }
           xmlcontent = xmlcontent + tag2("/catalogentry")+"\n";
        }

        //3.3 Contribute (Optional)
        if (ourform.ContributeMetametadata.value != "")
        {
           var temp_roleArray2 = roleList2.split("|");
           var temp_centityArray2 = centityList2.split("|");
           var temp_dateArray2 = dateList2.split("|");
		   var temp_desArray2 = datedescriptionList2.split("|");

           /* Length Check */
           length = temp_roleArray2.length;
           if (temp_centityArray2.length > length)
           {
            length = temp_centityArray2.length;
           }
           if (temp_dateArray2.length > length)
           {
            length = temp_dateArray2.length;
           }
           /* End Length Check */

           // 3.3.1 Role - List (Optional)
           length--;
           for (var i=0;i<length;i++)
           {
            xmlcontent = xmlcontent + tag2("contribute")+"\n";
            if (temp_roleArray2[i] != "")
            {
               xmlcontent = xmlcontent + tag2("role")+"\n";
               xmlcontent = xmlcontent + tag2("langstring")+"\n";
               xmlcontent = xmlcontent + temp_roleArray2[i]+"\n";
               xmlcontent = xmlcontent + tag2("/langstring")+"\n";
               xmlcontent = xmlcontent + tag2("/role")+"\n";
            }

            // 3.3.2 Centity (Optional)
            if (temp_centityArray2[i] != "")
            {
                 var temp_cent2 = temp_centityArray2[i];
              var if_comma3 = false;
              for (var k=0;k<temp_cent2.length;k++)
                 {
                if (temp_cent2.charAt(k) == "~")
               {
                 if_comma3 = true;
               }
              }
              if (if_comma3)
              {
               var temp_centArray2 = temp_cent2.split("~");
               var length_cent2 = temp_centArray2.length;
               for(var j=0;j<length_cent2;j++)
               {
                  var arr_temp = temp_centArray2[j];
                  xmlcontent = xmlcontent + tag2("centity")+"\n";
                  xmlcontent = xmlcontent + tag2("vcard")+"\n";
                  xmlcontent = xmlcontent + "begin:vcard"+"\n";
                  xmlcontent = xmlcontent + arr_temp+"\n";
                  xmlcontent = xmlcontent + "end:vcard"+"\n";
                  xmlcontent = xmlcontent + tag2("/vcard")+"\n";
                  xmlcontent = xmlcontent + tag2("/centity")+"\n";
               }
              }
              else
              {
                  xmlcontent = xmlcontent + tag2("centity")+"\n";
                  xmlcontent = xmlcontent + tag2("vcard")+"\n";
                  xmlcontent = xmlcontent + "begin:vcard"+"\n";
                  xmlcontent = xmlcontent + temp_cent2+"\n";
                  xmlcontent = xmlcontent + "end:vcard"+"\n";
                  xmlcontent = xmlcontent + tag2("/vcard")+"\n";
                  xmlcontent = xmlcontent + tag2("/centity")+"\n";
              }
            }

            // 3.3.3 Date (Optional)
            if (temp_dateArray2[i] != "")
            {
               xmlcontent = xmlcontent + tag2("date")+"\n";
               xmlcontent = xmlcontent + tag2("datetime") +"\n";
               xmlcontent = xmlcontent + temp_dateArray2[i]+"\n";
               xmlcontent = xmlcontent + tag2("/datetime")+"\n";
			   if (temp_desArray2[i] != "")
			   {
	               xmlcontent = xmlcontent + tag2("description") +"\n";
	               xmlcontent = xmlcontent + temp_desArray2[i]+"\n";
	               xmlcontent = xmlcontent + tag2("/description")+"\n";
			   }
               xmlcontent = xmlcontent + tag2("/date")+"\n";
            }
            xmlcontent = xmlcontent + tag2("/contribute")+"\n";
           }
        }


        // 3.4 Meta-data Scheme (Required)
        var SchemeArray = ourform.Metadatascheme.value.split("|");
        for (var i=0;i<SchemeArray.length;i++)
        {
           xmlcontent = xmlcontent + tag2("metadatascheme")+"\n";
           xmlcontent = xmlcontent + SchemeArray[i]+"\n";
            xmlcontent = xmlcontent + tag2("/metadatascheme")+"\n";
        }

        // 3.5 Language (Optional)
        var MetadataLanguage = ourform.LanguageMetametadata.value;
        if (MetadataLanguage != "")
        {
           xmlcontent = xmlcontent + tag2("language")+"\n";
           xmlcontent = xmlcontent + MetadataLanguage +"\n";
            xmlcontent = xmlcontent + tag2("/language")+"\n";
        }

         xmlcontent = xmlcontent + tag2("/metametadata")+"\n";
      // End Section 3 //

      // Begin Section 1 //
         // 1. General (Required)
         xmlcontent = xmlcontent + tag2("general")+"\n";

         // 1.2 Title (Required)
         xmlcontent = xmlcontent + tag2("title")+"\n";
         xmlcontent = xmlcontent + tag2("langstring")+"\n";
         xmlcontent = xmlcontent + ourform.Title.value+"\n";
         xmlcontent = xmlcontent + tag2("/langstring")+"\n";
         xmlcontent = xmlcontent + tag2("/title")+"\n";


         // 1.3 Catalog Entry (Required)
        var temp_catalogueArray = catalogueList.split("|");
        var temp_entryArray = entryList.split("|");

        /* Length Check */
        length = temp_catalogueArray.length;
        if (temp_entryArray.length > length)
        {
         length = temp_entryArray.length;
        }
        /* End Length Check */

        for (var i=0;i<length-1;i++)
        {
           xmlcontent = xmlcontent + tag2("catalogentry")+"\n";

         // 1.3.1 Catalog  (Required)
         xmlcontent = xmlcontent + tag2("catalogue")+"\n";
          xmlcontent = xmlcontent + temp_catalogueArray[i]+"\n";
         xmlcontent = xmlcontent + tag2("/catalogue")+"\n";

          // 1.3.2 Entry (Required)
         xmlcontent = xmlcontent + tag2("entry")+"\n";
          xmlcontent = xmlcontent + tag2("langstring")+"\n";
          xmlcontent = xmlcontent + temp_entryArray[i]+"\n";
          xmlcontent = xmlcontent + tag2("/langstring")+"\n";
         xmlcontent = xmlcontent + tag2("/entry")+"\n";

          xmlcontent = xmlcontent + tag2("/catalogentry")+"\n";
        }


         // 1.4 Language (Optional)
        var langGen = ourform.LanguageGeneral.value;
        if (langGen != "")
        {
          var temp_langArray = langGen.split(",");
         for (var i=0;i<temp_langArray.length;i++)
         {
            xmlcontent = xmlcontent + tag2("language")+"\n";
            xmlcontent = xmlcontent + temp_langArray[i]+"\n";
            xmlcontent = xmlcontent + tag2("/language")+"\n";
         }
        }

         // 1.5 Description (Required)
        var desc = descriptionList4;
        var temp_descArray = desc.split("|");
        var desc_temp_length2 = temp_descArray.length;
        desc_temp_length2--;
        for (var i=0;i<desc_temp_length2;i++)
        {
            xmlcontent = xmlcontent + tag2("description")+"\n";
          xmlcontent = xmlcontent + tag2("langstring")+"\n";
         xmlcontent = xmlcontent + temp_descArray[i]+"\n";
            xmlcontent = xmlcontent + tag2("/langstring")+"\n";
            xmlcontent = xmlcontent + tag2("/description")+"\n";
        }

         // 1.6 Keywords (Required)
        var temp_keywordsArray = ourform.KeywordsGeneral.value.split("|");
        for (var i=0;i<temp_keywordsArray.length;i++)
        {
            xmlcontent = xmlcontent + tag2("keywords")+"\n";
            xmlcontent = xmlcontent + tag2("langstring")+"\n";
           xmlcontent = xmlcontent + temp_keywordsArray[i]+"\n";
            xmlcontent = xmlcontent + tag2("/langstring")+"\n";
            xmlcontent = xmlcontent + tag2("/keywords")+"\n";
        }

        // 1.7 Coverage (Optional)
        if (ourform.Coverage.value != "")
        {
           var temp_coverageArray = ourform.Coverage.value.split("|");
           for (var i=0;i<temp_coverageArray.length;i++)
           {
              xmlcontent = xmlcontent + tag2("coverage")+"\n";
               xmlcontent = xmlcontent + tag2("langstring")+"\n";
               xmlcontent = xmlcontent + temp_coverageArray[i]+"\n";
               xmlcontent = xmlcontent + tag2("/langstring")+"\n";
              xmlcontent = xmlcontent + tag2("/coverage")+"\n";
           }
         }

         // 1.8 Structure (Optional)
        if (ourform.Structure.selectedIndex != 0)
        {
           xmlcontent = xmlcontent + tag2("structure")+"\n";
            xmlcontent = xmlcontent + tag2("langstring")+"\n";
            xmlcontent = xmlcontent + ourform.Structure.value+"\n";
            xmlcontent = xmlcontent + tag2("/langstring")+"\n";
           xmlcontent = xmlcontent + tag2("/structure")+"\n";
        }

         // 1.9 Aggregation Level (Optional)
        if ( ourform.Aggregationlevel.selectedIndex != 0)
        {
        xmlcontent = xmlcontent + tag2("aggregationlevel")+"\n";
        xmlcontent = xmlcontent + ourform.Aggregationlevel.value+"\n";
        xmlcontent = xmlcontent + tag2("/aggregationlevel")+"\n";
        }

       xmlcontent = xmlcontent + tag2("/general")+"\n";
      // End Section 1 //
      // Begin Section 2 //

         // 2. LifeCycle (Required)
         xmlcontent = xmlcontent + tag2("lifecycle")+"\n";

         // 2.1 Version (Required)
        xmlcontent = xmlcontent + tag2("version")+"\n";
         xmlcontent = xmlcontent + tag2("langstring")+"\n";
         xmlcontent = xmlcontent + ourform.Version.value+"\n";
         xmlcontent = xmlcontent + tag2("/langstring")+"\n";
         xmlcontent = xmlcontent + tag2("/version")+"\n";

         // 2.2 Status (Required)
        xmlcontent = xmlcontent + tag2("status")+"\n";
         xmlcontent = xmlcontent + tag2("langstring")+"\n";
         xmlcontent = xmlcontent + ourform.Status.value+"\n";
         xmlcontent = xmlcontent + tag2("/langstring")+"\n";
         xmlcontent = xmlcontent + tag2("/status")+"\n";

          // 2.3 Contribute (Optional)
         if ((roleList != "") || (centityList != "") || (dateList != ""))
         {
           var temp_roleArray = roleList.split("|");
           var temp_centityArray = centityList.split("|");
           var temp_dateArray = dateList.split("|");
		   var temp_desArray = datedescriptionList.split("|");

           /* check for largest length */
            length = temp_roleArray.length;
            if (temp_centityArray.length > length)
            {
              length = temp_roleArray.length;
            }
            if (temp_dateArray.length > length)
            {
              length = temp_dateArray.length;
            }
           /* end check */
           length--;
           for (var i=0;i<length;i++)
           {
           xmlcontent = xmlcontent + tag2("contribute")+"\n";
            var temp_date = temp_dateArray[i];
			var temp_des = temp_desArray[i];

             // 2.3.1 Role - List (Optional)
            if (temp_roleArray[i] != "")
            {
                xmlcontent = xmlcontent + tag2("role")+"\n";
                xmlcontent = xmlcontent + tag2("langstring")+"\n";
                xmlcontent = xmlcontent + temp_roleArray[i]+"\n";
                xmlcontent = xmlcontent + tag2("/langstring")+"\n";
                xmlcontent = xmlcontent +tag2("/role")+"\n";
            }

             // 2.3.2 Entity (Optional)
             // Uses CEntity_Type to write either ORG: or FN:
              // Assumes this is correct.
            if (temp_centityArray[i] != "")
            {
                 var temp_cent = temp_centityArray[i];
              var if_comma2 = false;
              for (var k=0;k<temp_cent.length;k++)
              {
                if (temp_cent.charAt(k) == "~")
               {
                 if_comma2 = true;
               }
              }
              if (if_comma2)
              {
               var temp_centArray = temp_cent.split("~");
               var length_cent = temp_centArray.length;
               for(var j=0;j<length_cent;j++)
               {
                 var arr_temp = temp_centArray[j];
                   xmlcontent = xmlcontent + tag2("centity")+"\n";
                    xmlcontent = xmlcontent+ tag2("vcard")+"\n";
                    xmlcontent = xmlcontent + "BEGIN:vCard"+"\n";
                    xmlcontent = xmlcontent + arr_temp+"\n";
                    xmlcontent = xmlcontent + "END:vCard"+"\n";
                    xmlcontent = xmlcontent + tag2("/vcard")+"\n";
                    xmlcontent = xmlcontent + tag2("/centity")+"\n";
               }
              }
              else
              {
                   xmlcontent = xmlcontent + tag2("centity")+"\n";
                   xmlcontent = xmlcontent+ tag2("vcard")+"\n";
                   xmlcontent = xmlcontent + "BEGIN:vCard"+"\n";
                   xmlcontent = xmlcontent + temp_cent+"\n";
                   xmlcontent = xmlcontent + "END:vCard"+"\n";
                   xmlcontent = xmlcontent + tag2("/vcard")+"\n";
                   xmlcontent = xmlcontent + tag2("/centity")+"\n";
              }
            }

               // 2.3.3 Date (Optional)
            if (temp_date != "")
            {
                xmlcontent = xmlcontent + tag2("date")+"\n";
                xmlcontent = xmlcontent + tag2("datetime")+"\n";
                xmlcontent = xmlcontent + temp_date+"\n";
                xmlcontent = xmlcontent + tag2("/datetime")+"\n";
				if (temp_des != "")
				{
					xmlcontent = xmlcontent + tag2("description")+"\n";
	                xmlcontent = xmlcontent + temp_des+"\n";
	                xmlcontent = xmlcontent + tag2("/description")+"\n";
				}
                xmlcontent = xmlcontent + tag2("/date")+"\n";
            }
              xmlcontent = xmlcontent + tag2("/contribute")+"\n";
           }
         }
          xmlcontent = xmlcontent + tag2("/lifecycle")+"\n";
      // End Section 2 //
      // Begin Section 4 //

         // 4. Technical (Required)
         xmlcontent = xmlcontent + tag2("technical")+"\n";

         // 4.1 Format (Required)
         xmlcontent = xmlcontent + tag2("format")+"\n";
         xmlcontent = xmlcontent + tag2("langstring")+"\n";
         xmlcontent = xmlcontent + ourform.Format.value+"\n";
         xmlcontent = xmlcontent + tag2("/langstring")+"\n";
         xmlcontent = xmlcontent + tag2("/format")+"\n";

         // 4.2 Size (Optional)
         if ( ourform.Size.value != "" )
         {
            xmlcontent = xmlcontent + tag2("size")+"\n";
            xmlcontent = xmlcontent + ourform.Size.value+"\n";
            xmlcontent = xmlcontent + tag2("/size")+"\n";
         }

         // 4.3 Location (Required)
        var temp_locArray = ourform.Location.value.split(",");
        for (var i=0;i<temp_locArray.length;i++)
        {
           xmlcontent = xmlcontent + tag2("location")+"\n";
           xmlcontent = xmlcontent + temp_locArray[i]+"\n";
           xmlcontent = xmlcontent + tag2("/location")+"\n";
        }

         // 4.4 Requirements (Optional)
        if ((typeList != "") || (nameList != "") || (minverList != "") || (maxverList != ""))
        {
         var temp_typeArray = typeList.split("|");
         var temp_nameArray = nameList.split("|");
         var temp_minverArray = minverList.split("|");
         var temp_maxverArray = maxverList.split("|");

         /* Length Check */
         length = temp_typeArray.length;
         if (temp_nameArray.length > length)
         {
           length = temp_nameArray.length;
         }
         if (temp_minverArray.length > length)
         {
           length = temp_minverArray.length;
         }
         if (temp_maxverArray.length > length)
         {
           length = temp_maxverArray.length;
         }
         /* End Length Check */

         for (var i=0;i<length;i++)
         {
           xmlcontent = xmlcontent + tag2("requirements");

             // 4.4.1 Type - List (Optional)
           if (temp_typeArray[i] != "")
           {
              xmlcontent = xmlcontent + tag2("type")+"\n";
               xmlcontent = xmlcontent + tag2("langstring")+"\n";
               xmlcontent = xmlcontent + temp_typeArray[i]+"\n";
               xmlcontent = xmlcontent + tag2("/langstring")+"\n";
               xmlcontent = xmlcontent + tag2("/type")+"\n";
           }

             // 4.4.2 Name (Optional)
           if (temp_nameArray[i] != "")
           {
               xmlcontent = xmlcontent + tag2("name")+"\n";
               xmlcontent = xmlcontent + tag2("langstring")+"\n";
               xmlcontent = xmlcontent + temp_nameArray[i]+"\n";
               xmlcontent = xmlcontent + tag2("/langstring")+"\n";
               xmlcontent = xmlcontent + tag2("/name")+"\n";
           }

             // 4.4.3 Minimum Version (Optional)
           if (temp_minverArray[i] != "")
           {
               xmlcontent = xmlcontent + tag2("minimumversion")+"\n";
               xmlcontent = xmlcontent + temp_minverArray[i]+"\n";
               xmlcontent = xmlcontent + tag2("/minimumversion")+"\n";
           }

           // 4.4.4 Maximum Version (Optional)
           if (temp_maxverArray[i] != "")
           {
               xmlcontent = xmlcontent + tag2("maximumversion")+"\n";
               xmlcontent = xmlcontent + temp_maxverArray[i]+"\n";
               xmlcontent = xmlcontent + tag2("/maximumversion")+"\n";
           }
             xmlcontent = xmlcontent + tag2("/requirements")+"\n";
         }
        }


         // 4.5 Installation Remarks (Optional)
         if ( ourform.Installationremarks.value != "" )
         {
            xmlcontent = xmlcontent + tag2("installationremarks")+"\n";
            xmlcontent = xmlcontent + tag2("langstring")+"\n";
            xmlcontent = xmlcontent + ourform.Installationremarks.value+"\n";
            xmlcontent = xmlcontent + tag2("/langstring")+"\n";
            xmlcontent = xmlcontent + tag2("/installationremarks")+"\n";
         }

         // 4.6 Other Platform Requirements (Optional)
         if ( ourform.Otherplatformrequirements.value != "" )
         {
            xmlcontent = xmlcontent + tag2("otherplatformrequirements")+"\n";
            xmlcontent = xmlcontent + tag2("langstring")+"\n";
            xmlcontent = xmlcontent + ourform.Otherplatformrequirements.value+"\n";
            xmlcontent = xmlcontent + tag2("/langstring")+"\n";
            xmlcontent = xmlcontent + tag2("/otherplatformrequirements")+"\n";
         }

         // 4.7 Duration (Optional)
         if ( ourform.Duration.value != "" )
         {
            xmlcontent = xmlcontent + tag2("duration")+"\n";
            xmlcontent = xmlcontent + tag2("datetime")+"\n";
            xmlcontent = xmlcontent + ourform.Duration.value+"\n";
            xmlcontent = xmlcontent + tag2("/datetime")+"\n";
			if ( ourform.DurationDescription.value != "")
			{
		        xmlcontent = xmlcontent + tag2("description")+"\n";
	            xmlcontent = xmlcontent + ourform.DurationDescription.value+"\n";
	            xmlcontent = xmlcontent + tag2("/description")+"\n";
			}
            xmlcontent = xmlcontent + tag2("/duration")+"\n";
         }
         xmlcontent = xmlcontent + tag2("/technical")+"\n";
      // End Section 4 //
      // Begin Section 5 //

         // <!-- 5. Educational (Optional) -->
        if ((ourform.Interactivitytype.selectedIndex != 0) || (ourform.Learningresourcetype.value != "") || (ourform.Interactivitylevel.selectedIndex != 0) || (ourform.Semanticdensity.selectedIndex != 0) || (intendedList != "") || (ourform.Learningcontext.value != "") || (ourform.Typicalagerange.value != "") || (ourform.Difficulty.selectedIndex != 0) || (ourform.Typicallearningtime.value != "") || (ourform.DescriptionEducational.value != "") || (ourform.LanguageEducational.value != ""))
        {
           xmlcontent = xmlcontent + tag2("educational")+"\n";

          // 5.1 Interactivity Type (Optional)
          if ( ourform.Interactivitytype.selectedIndex != 0)
          {
            xmlcontent = xmlcontent + tag2("interactivitytype")+"\n";
            xmlcontent = xmlcontent + tag2("langstring")+"\n";
            xmlcontent = xmlcontent + ourform.Interactivitytype.value+"\n";
            xmlcontent = xmlcontent + tag2("/langstring")+"\n";
            xmlcontent = xmlcontent + tag2("/interactivitytype")+"\n";
          }

          //  5.2 Learning Resource Type - List (Optional)
            if ( ourform.Learningresourcetype.value != "" )
          {
              var temp_lrt = ourform.Learningresourcetype.value;
              var if_comma3 = false;
              for (var k=0;k<temp_lrt.length;k++)
              {
                if (temp_lrt.charAt(k) == "|")
               {
                 if_comma3 = true;
               }
              }
              if (if_comma3)
              {
               var temp_lrtArray = temp_lrt.split("|");
               var length_lrt = temp_lrtArray.length;
               for(var j=0;j<length_lrt;j++)
               {
                     xmlcontent = xmlcontent + tag2("learningresourcetype")+"\n";
                     xmlcontent = xmlcontent + tag2("langstring")+"\n";
                    xmlcontent = xmlcontent + temp_lrtArray[j]+"\n";
                   xmlcontent = xmlcontent + tag2("/langstring")+"\n";
                     xmlcontent = xmlcontent + tag2("/learningresourcetype")+"\n";
               }
              }
              else
              {
               xmlcontent = xmlcontent + tag2("learningresourcetype")+"\n";
                  xmlcontent = xmlcontent + tag2("langstring")+"\n";
                 xmlcontent = xmlcontent + ourform.Learningresourcetype.value+"\n";
                xmlcontent = xmlcontent + tag2("/langstring")+"\n";
                  xmlcontent = xmlcontent + tag2("/learningresourcetype")+"\n";
              }
            }

          // 5.3 Interactivity Level (Optional)
          if ( ourform.Interactivitylevel.selectedIndex != 0)
          {
            xmlcontent = xmlcontent + tag2("interactivitylevel")+"\n";
            xmlcontent = xmlcontent + ourform.Interactivitylevel.value+"\n";
            xmlcontent = xmlcontent + tag2("/interactivitylevel")+"\n";
          }

          // 5.4 Semantic Density (Optional)
            if ( ourform.Semanticdensity.selectedIndex != 0)
          {
            xmlcontent = xmlcontent + tag2("semanticdensity")+"\n";
            xmlcontent = xmlcontent + ourform.Semanticdensity.value+"\n";
            xmlcontent = xmlcontent + tag2("/semanticdensity")+"\n";
          }

          // 5.5 Intended End User Role (Optional)
          if ( intendedList != "" )
          {
            var temp_intendedArray = intendedList.split("|");
            for (var i=0;i<temp_intendedArray.length-1;i++)
            {
               xmlcontent = xmlcontent + tag2("intendedenduserrole")+"\n";
               xmlcontent = xmlcontent + tag2("langstring")+"\n";
               xmlcontent = xmlcontent + temp_intendedArray[i]+"\n";
               xmlcontent = xmlcontent + tag2("/langstring")+"\n";
               xmlcontent = xmlcontent + tag2("/intendedenduserrole")+"\n";
            }
          }

          // 5.6 Learning Context (Optional)
          if ( ourform.Learningcontext.value != "")
          {
              var temp_lc = ourform.Learningcontext.value;
              var if_comma4 = false;
              for (var k=0;k<temp_lc.length;k++)
              {
                if (temp_lc.charAt(k) == "|")
               {
                 if_comma4 = true;
               }
              }
              if (if_comma4)
              {
               var temp_lcArray = temp_lc.split("|");
               var length_lc = temp_lcArray.length;
               for(var j=0;j<length_lc;j++)
               {
                  xmlcontent = xmlcontent + tag2("learningcontext")+"\n";
                  xmlcontent = xmlcontent + tag2("langstring")+"\n";
                  xmlcontent = xmlcontent + temp_lcArray[j]+"\n";
                  xmlcontent = xmlcontent + tag2("/langstring")+"\n";
                  xmlcontent = xmlcontent + tag2("/learningcontext")+"\n";
               }
              }
              else
              {
               xmlcontent = xmlcontent + tag2("learningcontext")+"\n";
               xmlcontent = xmlcontent + tag2("langstring")+"\n";
               xmlcontent = xmlcontent + ourform.Learningcontext.value+"\n";
               xmlcontent = xmlcontent + tag2("/langstring")+"\n";
               xmlcontent = xmlcontent + tag2("/learningcontext")+"\n";
               }
          }

          // 5.7 Typical Age Range (Optional)
          if ( ourform.Typicalagerange.value != "")
          {
              var temp_tar = ourform.Typicalagerange.value;
              var if_comma5 = false;
              for (var k=0;k<temp_tar.length;k++)
              {
                if (temp_tar.charAt(k) == "|")
               {
                 if_comma5 = true;
               }
              }
              if (if_comma5)
              {
               var temp_tarArray = temp_tar.split("|");
               var length_tar = temp_tarArray.length;
               for(var j=0;j<length_tar;j++)
               {
                  xmlcontent = xmlcontent + tag2("typicalagerange")+"\n";
                  xmlcontent = xmlcontent + tag2("langstring")+"\n";
                  xmlcontent = xmlcontent + temp_tarArray[j]+"\n";
                  xmlcontent = xmlcontent + tag2("/langstring")+"\n";
                  xmlcontent = xmlcontent + tag2("/typicalagerange")+"\n";
               }
              }
              else
              {
               xmlcontent = xmlcontent + tag2("typicalagerange")+"\n";
               xmlcontent = xmlcontent + tag2("langstring")+"\n";
               xmlcontent = xmlcontent + ourform.Typicalagerange.value+"\n";
               xmlcontent = xmlcontent + tag2("/langstring")+"\n";
               xmlcontent = xmlcontent + tag2("/typicalagerange")+"\n";
               }
          }

          // 5.8 Difficulty (Optional)
          if ( ourform.Difficulty.selectedIndex != 0)
          {
            xmlcontent = xmlcontent + tag2("difficulty")+"\n";
            xmlcontent = xmlcontent + ourform.Difficulty.value+"\n";
            xmlcontent = xmlcontent + tag2("/difficulty")+"\n";
          }

          // 5.9 Typical Learning Time (Optional)
            if ( ourform.Typicallearningtime.value != "" )
            {
               xmlcontent = xmlcontent + tag2("typicallearningtime")+"\n";
               xmlcontent = xmlcontent + tag2("datetime")+"\n";
               xmlcontent = xmlcontent + ourform.Typicallearningtime.value+"\n";
               xmlcontent = xmlcontent + tag2("/datetime")+"\n";
			   if ( ourform.TLTDescription.value != "")
			   {
	               xmlcontent = xmlcontent + tag2("description")+"\n";
	               xmlcontent = xmlcontent + ourform.TLTDescription.value+"\n";
	               xmlcontent = xmlcontent + tag2("/description")+"\n";
			   }
               xmlcontent = xmlcontent + tag2("/typicallearningtime")+"\n";
            }

          // 5.10 Description (Optional)
          if ( ourform.DescriptionEducational.value != "")
          {
            xmlcontent = xmlcontent + tag2("description")+"\n";
            xmlcontent = xmlcontent + tag2("langstring")+"\n";
            xmlcontent = xmlcontent + ourform.DescriptionEducational.value+"\n";
            xmlcontent = xmlcontent + tag2("/langstring")+"\n";
            xmlcontent = xmlcontent + tag2("/description")+"\n";
          }

          // 5.11 Language (Optional)
          var langEdu = ourform.LanguageEducational.value;
          if ( langEdu != "")
          {
           var temp_langArray2 = langEdu.split(",");
            for(var i=0;i<temp_langArray2.length;i++)
            {
            xmlcontent = xmlcontent + tag2("language")+"\n";
            xmlcontent = xmlcontent + temp_langArray2[i]+"\n";
            xmlcontent = xmlcontent + tag2("/language")+"\n";
            }
          }


            xmlcontent = xmlcontent + tag2("/educational")+"\n";
        }
      // End Section 5 //
      // Begin Section 6 //

         // 6. Rights (Required)
         xmlcontent = xmlcontent + tag2("rights")+"\n";

         // 6.1 Cost (Required)
         xmlcontent = xmlcontent + tag2("cost")+"\n";
         xmlcontent = xmlcontent + tag2("langstring")+"\n";
         xmlcontent = xmlcontent + ourform.Cost.value+"\n";
         xmlcontent = xmlcontent + tag2("/langstring")+"\n";
         xmlcontent = xmlcontent + tag2("/cost")+"\n";

         // 6.2 Copyright and Other Restrictions (Required)
         xmlcontent = xmlcontent + tag2("copyrightandotherrestrictions")+"\n";
         xmlcontent = xmlcontent + tag2("langstring")+"\n";
         xmlcontent = xmlcontent + ourform.Copyrightandotherrestrictions.value+"\n";
         xmlcontent = xmlcontent + tag2("/langstring")+"\n";
         xmlcontent = xmlcontent + tag2("/copyrightandotherrestrictions")+"\n";

         // 6.3 Description (Optional)
         if ( ourform.DescriptionRights.value != "" )
         {
            xmlcontent = xmlcontent + tag2("description")+"\n";
            xmlcontent = xmlcontent + tag2("langstring")+"\n";
            xmlcontent = xmlcontent + ourform.DescriptionRights.value+"\n";
            xmlcontent = xmlcontent + tag2("/langstring")+"\n";
            xmlcontent = xmlcontent + tag2("/description")+"\n";
         }
         xmlcontent = xmlcontent + tag2("/rights")+"\n";
      // End Section 6 //
      // Begin Section 7 //

        // <!-- 7. Relation (Optional) -->
        if ((kindList != "") || (descriptionList != ""))
        {
         var temp_kindArray = kindList.split("|");
          var temp_desArray = descriptionList.split("|");
         /* Length Check */
         var length_relation = 0;
         length_relation = temp_kindArray.length;
         if (temp_desArray.length > length_relation)
         {
            length_relation = temp_desArray.length;
         }
         /* End Length Check */
          length_relation--;

         for (var i=0;i<length_relation;i++)
         {
            var des = temp_desArray[i];
            var kin = temp_kindArray[i];

             xmlcontent = xmlcontent + tag2("relation")+"\n";
            // 7.1 Kind - List (Optional)
            if ( kin != "" )
            {
                 xmlcontent = xmlcontent + tag2("kind")+"\n";
              xmlcontent = xmlcontent + tag2("langstring")+"\n";
              xmlcontent = xmlcontent + kin+"\n";
              xmlcontent = xmlcontent + tag2("/langstring")+"\n";
              xmlcontent = xmlcontent + tag2("/kind")+"\n";
            }

            // 7.2 Resource & 7.2.2 Description (Optional)
            if ( des != "")
            {
               xmlcontent = xmlcontent + tag2("resource")+"\n";

               // 7.2.2 Description
               xmlcontent = xmlcontent + tag2("description")+"\n";
               xmlcontent = xmlcontent + tag2("langstring")+"\n";
               xmlcontent = xmlcontent + des+"\n";
               xmlcontent = xmlcontent + tag2("/langstring")+"\n";
               xmlcontent = xmlcontent + tag2("/description")+"\n";

               xmlcontent = xmlcontent + tag2("/resource")+"\n";
            }
            xmlcontent = xmlcontent + tag2("/relation")+"\n";
         }
        }
      // End Section 7 //
      // Begin Section 8 //

        // <!-- 8. Annotation (Optional) -->
        if ((centityList3 != "")||(dateList3 != "")||(descriptionList2 != ""))
        {
         var temp_cent3Array = centityList3.split("|");
         var temp_date3Array = dateList3.split("|");
         var temp_desc2Array = descriptionList2.split("|");
         var temp_datedesArray = datedescriptionList3.split("|");

         /* Length Check */
         var anno_length = temp_cent3Array.length;
         if (temp_date3Array.length > anno_length)
         {
            anno_length = temp_date3Array.length;
         }
         if (temp_desc2Array.length > anno_length)
         {
            anno_length = temp_desc2Array.length;
         }
         anno_length--;
         /* End Length Check */
         for (var i=0;i<anno_length;i++)
         {
            var cent3 = temp_cent3Array[i];
            var date3 = temp_date3Array[i];
            var desc2 = temp_desc2Array[i];
			var datedes = temp_datedesArray[i];

               xmlcontent = xmlcontent + tag2("annotation")+"\n";
            // 8.1 Centity (Optional)
            if ( cent3 != "")
            {
              xmlcontent = xmlcontent + tag2("centity")+"\n";
              xmlcontent = xmlcontent + tag2("vcard")+"\n";
              xmlcontent = xmlcontent + "begin : vcard"+"\n";
              xmlcontent = xmlcontent + "org :" + cent3+"\n";
              xmlcontent = xmlcontent + "end : vcard"+"\n";
              xmlcontent = xmlcontent + tag2("/vcard")+"\n";
              xmlcontent = xmlcontent + tag2("/centity")+"\n";
            }

            // 8.2 Date (Optional)
            if ( date3 != "")
            {
              xmlcontent = xmlcontent + tag2("date")+"\n";
              xmlcontent = xmlcontent + tag2("datetime")+"\n";
              xmlcontent = xmlcontent + date3+"\n";
              xmlcontent = xmlcontent + tag2("/datetime")+"\n";
			  if (datedes != "")
			  {
				  xmlcontent = xmlcontent + tag2("description")+"\n";
	              xmlcontent = xmlcontent + datedes+"\n";
	              xmlcontent = xmlcontent + tag2("/description")+"\n";
              }
              xmlcontent = xmlcontent + tag2("/date")+"\n";
            }

            // 8.3 Description (Optional)
            if ( desc2 != "")
            {
              xmlcontent = xmlcontent + tag2("description")+"\n";
              xmlcontent = xmlcontent + tag2("langstring")+"\n";
              xmlcontent = xmlcontent + desc2+"\n";
              xmlcontent = xmlcontent + tag2("/langstring")+"\n";
              xmlcontent = xmlcontent + tag2("/description")+"\n";
            }
            xmlcontent = xmlcontent + tag2("/annotation")+"\n";
         }
        }

      // End Section 8 //

      // Begin Section 9 //
         // 9. Classification (Required)
         var temp_purposeArray = purposeList.split("|");
         var temp_desc3Array = descriptionList3.split("|");
         var temp_key2Array = keywordsList2.split("|");

         /* length check */
         var length_class = temp_purposeArray.length;
         if (temp_desc3Array.length > length_class)
         {
            length_class = temp_desc3Array.length;
         }
         if (temp_key2Array.length > length_class)
         {
            length_class = temp_key2Array.length;
         }
         length_class--;
         /* end Length Check */

         for (var i=0;i<length_class;i++)
         {
            var pur = temp_purposeArray[i];
            var desc3 = temp_desc3Array[i];
            var key2 = temp_key2Array[i];

            xmlcontent = xmlcontent + tag2("classification")+"\n";

              // 9.1 Purpose - List (Required)
               xmlcontent = xmlcontent + tag2("purpose")+"\n";
               xmlcontent = xmlcontent + tag2("langstring")+"\n";
               xmlcontent = xmlcontent + pur+"\n";
               xmlcontent = xmlcontent + tag2("/langstring")+"\n";
               xmlcontent = xmlcontent + tag2("/purpose")+"\n";



              // 9.3 Description (Required)
               xmlcontent = xmlcontent + tag2("description")+"\n";
               xmlcontent = xmlcontent + tag2("langstring")+"\n";
               xmlcontent = xmlcontent + desc3+"\n";
               xmlcontent = xmlcontent + tag2("/langstring")+"\n";
               xmlcontent = xmlcontent + tag2("/description")+"\n";

               // 9.4 Keywords (Required)
                 var temp_keyword = key2;
              var if_comma5 = false;
              for (var k=0;k<temp_keyword.length;k++)
              {
                if (temp_keyword.charAt(k) == "|")
               {
                 if_comma5 = true;
               }
              }
              if (if_comma5)
              {
               var temp_key_2Array = temp_keyword.split("~");
               var length_key = temp_key_2Array.length;
               for(var j=0;j<length_key;j++)
               {
                     xmlcontent = xmlcontent + tag2("keywords")+"\n";
                     xmlcontent = xmlcontent + tag2("langstring")+"\n";
                     xmlcontent = xmlcontent + temp_key_2Array[j]+"\n";
                     xmlcontent = xmlcontent + tag2("/langstring")+"\n";
                     xmlcontent = xmlcontent + tag2("/keywords")+"\n";
                 }
              }
              else
              {
                     xmlcontent = xmlcontent + tag2("keywords")+"\n";
                     xmlcontent = xmlcontent + tag2("langstring")+"\n";
                     xmlcontent = xmlcontent + key2+"\n";
                     xmlcontent = xmlcontent + tag2("/langstring")+"\n";
                     xmlcontent = xmlcontent + tag2("/keywords")+"\n";
               }
            xmlcontent = xmlcontent + tag2("/classification")+"\n";
         }
      // End Section 9 //
         xmlcontent = xmlcontent + tag2("/record")+"\n";
      }



/***********************************************************************************
** The following functions are used by the xml_layer.
**
** printXml()
** saveXml()
***********************************************************************************/


   //************************************************************************
    // Function: printLog()
    //
    // Description:  Function used to print the Log contents
    //************************************************************************
    function printXml()
    {
       print();
    }

    //************************************************************************
    // Function: saveLog()
    //
    // Description:  Function used to save the Log contents
    //************************************************************************
    function saveXml()
    {
      create2();
       // Build Default file name: Example - TestLog_7_12_1059.htm
       var time = "TestLog_" + month + "_" + date + "_" + hour + min
       if ( isIE4 )
       {
          document.xmloutput.saveLogContent(xmlcontent,time,"xml");
       }
       else
       {
          navTempText += "</BODY>\n</HTML>"
          document.xmloutput.saveLogContent(navTempText,time,"xml");
       }
    }

/***********************************************************************************
** The following functions are from the layer_functions.js file.  They are used to
** manipulate layers.
**
** showLayer()
** hideLayer()
***********************************************************************************/

   // variable used to test if the browser is netscape or IE.
   var f_ns = (document.all == null);

   /************************************************************************
    ** Function: showLayer(lyrID,oFrame)
    **
   ** Variables: lyrID - The name of the layer to show.
   **         oFrame - The name of the frame object.
   **
    ** Description:  This function reads the name of the lyrID and shows that
   **           layer.
    *************************************************************************/
   function showLayer(lyrID,oFrame)
   {
      // Checks to see if there are 2 arguments, and therefore a
      // frame object to associate with the layer.
      if (showLayer.arguments.length == 2)
      {
         objDoc = oFrame.document;
      }
      else
      {
         objDoc = document;
      }

      // Checks to see if the browser is Netscape or IE.
      if (f_ns)
      {
         objDoc.layers[lyrID].visibility = "show";
      }
      else
      {
         objDoc.all[lyrID].style.visibility = "visible";
      }
   }

   /************************************************************************
    ** Function: hideLayer(lyrID,oFrame)
    **
   ** Variables: lyrID - The name of the layer to hide.
   **         oFrame - The name of the frame object.
   **
    ** Description:  This function reads the name of the lyrID and hides that
   **           layer.
    *************************************************************************/
   function hideLayer(lyrID,oFrame)
   {
      // frame object to associate with the layer.
      if (hideLayer.arguments.length == 2)
      {
         objDoc = oFrame.document;
      }
      else
      {
         objDoc = document;
      }

      // Checks to see if the browser is Netscape or IE.
      if (f_ns)
      {
         objDoc.layers[lyrID].visibility = "hide";
      }
      else
      {
         objDoc.all[lyrID].style.visibility = "hidden";
      }
   }


/*********************************************************************************
**  The following functions were created for use with the form.
**
** Layers(show,form)
** reset_vars()
*********************************************************************************/

     /************************************************************************
      ** Function: Layers(show,form)
      **
     ** Variables: show - The number of the frame to show.
     **          form - The contents of the current form.
     **
      ** Description:  This functions is passed a number, this number determines
     **            which layers to show/hide and what function calls to make.
      *************************************************************************/
     function Layers(show,form)
     {
      // If show = 1 then the xml_layer should be hidden, and the
      // form_layer should be shown.  It also resets the contents of
      // xml_layer, those used for viewing and saving.
      if (show == 1)
      {
        // This section deletes the current test.xml document
        if (temp_created)
        {
         fso = new ActiveXObject("Scripting.FileSystemObject");
         file_del = fso.GetFile("c:\\test.xml");
         file_del.Delete();
        }
         hideLayer('xml_layer');
         showLayer('form_layer');
         parent.info.location.href = "../information/info.htm";
         logText.innerHTML = "";
         xmlcontent = "";
      }
      // If show = 2, the form_layer is hidden, the xml_layer is shown, the
      // layer called logText is hidden from the user's view, the page is
      // scrolled to the top so the user can see the view/save options and
      // the function called initializeTest is called.
      else
      {
        if (doSubmit(form) == -1)
        {
         hideLayer('form_layer');
         showLayer('xml_layer');
         logText.style.visibility = "hidden";
         xml_layer.scrollIntoView(true);
         parent.info.location.href = "../include/savehelp.htm";
         initializeTest();
        }
      }
     }

   /************************************************************************
    ** Function: reset_vars()
    **
   ** Variables: show - none
   **
    ** Description:  This functions resets the variables used by the 1.4,
   **           5.5, and 5.11 selectboxes to keep track of what is
   **           selected.
    *************************************************************************/
    function reset_vars()
   {
     langcount = 0;
     langcount2 = 0;
     intendcount = 0;

     /** resets all variables **/
        var descriptionList4 = "";
       var keywordsList = "";
       var catalogueList = "";
       var entryList = "";
       var catalogueList2 = "";
       var entryList2 = "";
       var roleList = "";
       var centityList = "";
       var dateList ="";
       var roleList2 = "";
       var centityList2 = "";
       var dateList2 ="";
       var langcount = 0;
       var langcount2 = 0;
       var typeList = "";
       var nameList = "";
       var minverList = "";
       var maxverList = "";
       var intendcount = 0;
       var kindList = "";
       var resourceList = "";
       var idList = "";
       var descriptionList = "";
       var centityList3 = "";
       var dateList3 = "";
       var descriptionList2 = "";
       var purposeList = "";
       var sourceList = "";
       var descriptionList3 = "";
       var keywordsList2 = "";
       var languageList = "";
       var languageList2 = "";
       var intendedList = "";
       var sourceArray = new Array();
       var id_ClassArray = new Array();
       var entry_ClassArray = new Array();
       var info_pass = -1;
       var type_of_metadata = "raw";
       var mult_check = "";
       var temp_created = false;
   }

   /************************************************************************
    ** Function: CreateFile()
    **
   ** Variables: none
   **
    ** Description:  This functions creates the temporary file that is used
   **    by the TestSuite to check the conformance of the XML.
   **
   **  Currently Disabled
    *************************************************************************/
   function CreateFile()
   {
         var fso, tf;
      fso = new ActiveXObject("Scripting.FileSystemObject");
      tf = fso.CreateTextFile("c:\\test.xml", true);
      create_xml();
      tf.Write (tscontent);
      tf.Close();
      parent.info.location.href = "TestSuite/ts_log.htm";
      tscontent = "";
   }

    /*************************************************************************
    ** Function: create_xml()
    **
    ** Description:
    **   This function takes all the information from the form and writes
    **      it out to the tscontent variable.  This variable is used by the
   **    Test Suite to check the xml file for conformance.
   **
   **  Currently Disabled
    *************************************************************************/
   function create_xml()
      {
           // Sets temp_created variable
        temp_created = true;

         // Basic XML Header Information
         tscontent = tscontent + tag2("?xml version=\"1.0\" encoding=\"UTF-8\"?");

         tscontent = tscontent + tag2("!DOCTYPE record SYSTEM \"IMS_METADATAv1p1.dtd\"");

         tscontent = tscontent + tag2("record xmlns=\"http:\/\/www.imsproject.org\/metadata\/\"");

      // Begin Section 3 //
         // 3. MetaMetaData (Required)
         tscontent = tscontent + tag2("metametadata")

        // 3.2 Catalog Entry (Optional)
        if ((catalogueList2 != "") || (entryList2 != ""))
        {
         tscontent = tscontent + tag2("catalogentry");
           var temp_CatalogueArray2 = catalogueList2.split("|");
           var temp_EntryArray2 = entryList2.split("|");

           /* Length Check */
           length = temp_CatalogueArray2.length;
           if (temp_EntryArray2.length > length)
           {
            length = temp_EntryArray2.length;
           }
           /* End Length Check */

           // 3.2.1 Catalogue (Optional)
           for (var i=0;i<temp_CatalogueArray2.length;i++)
           {
             if (temp_CatalogueArray2[i] != "")
            {
               tscontent = tscontent + tag2("catalogue");
               tscontent = tscontent + temp_CatalogueArray2[i];
               tscontent = tscontent + tag2("/catalogue");
            }
            // 3.2.2 Entry (Optional)
            if (temp_EntryArray2[i] != "")
            {
               tscontent = tscontent + tag2("entry");
               tscontent = tscontent + tag2("langstring");
               tscontent = tscontent + temp_EntryArray2[i];
               tscontent = tscontent + tag2("/langstring");
               tscontent = tscontent + tag2("/entry");
            }
           }
           tscontent = tscontent + tag2("catalogentry");
        }

        //3.3 Contribute (Optional)
        if (ourform.ContributeMetametadata.value != "")
        {
           var temp_roleArray2 = roleList2.split("|");
           var temp_centityArray2 = centityList2.split("|");
           var temp_dateArray2 = dateList2.split("|");

           /* Length Check */
           length = temp_roleArray2.length;
           if (temp_centityArray2.length > length)
           {
            length = temp_centityArray2.length;
           }
           if (temp_dateArray2.length > length)
           {
            length = temp_dateArray2.length;
           }
           /* End Length Check */

           // 3.3.1 Role - List (Optional)
           for (var i=0;i<length;i++)
           {
            if (temp_roleArray2[i] != "")
            {
               tscontent = tscontent + tag2("role");
               tscontent = tscontent + tag2("langstring");
               tscontent = tscontent + temp_roleArray2[i];
               tscontent = tscontent + tag2("/langstring");
               tscontent = tscontent + tag2("/role");
            }

            // 3.3.2 Centity (Optional)
            if (temp_centityArray2[i] != "")
            {
                 var temp_cent2 = temp_centityArray2[i];
              var if_comma3 = false;
              for (var k=0;k<temp_cent2.length;k++)
                 {
                if (temp_cent2.charAt(k) == "~")
               {
                 if_comma3 = true;
               0}
              }
              if (if_comma3)
              {
               var temp_centArray2 = temp_cent2.split("~");
               var length_cent2 = temp_centArray2.length;
               for(var j=0;j<length_cent2;j++)
               {
                  var arr_temp = temp_centArray2[j];
                  tscontent = tscontent + tag2("centity");
                  tscontent = tscontent + tag2("vcard");
                  tscontent = tscontent + "begin:vcard";
                  tscontent = tscontent + arr_temp;
                  tscontent = tscontent + "end:vcard";
                  tscontent = tscontent + tag2("/vcard");
                  tscontent = tscontent + tag2("/centity");
               }
              }
              else
              {
                  tscontent = tscontent + tag2("centity");
                  tscontent = tscontent + tag2("vcard");
                  tscontent = tscontent + "begin:vcard";
                  tscontent = tscontent + temp_cent2;
                  tscontent = tscontent + "end:vcard";
                  tscontent = tscontent + tag2("/vcard");
                  tscontent = tscontent + tag2("/centity");
              }
            }

            // 3.3.3 Date (Optional)
            if (temp_dateArray2[i] != "")
            {
               tscontent = tscontent + tag2("date");
               tscontent = tscontent + tag2("datetime") ;
               tscontent = tscontent + temp_dateArray2[i];
               tscontent = tscontent + tag2("/datetime");
               tscontent = tscontent + tag2("description") ;
               tscontent = tscontent + "None";
               tscontent = tscontent + tag2("/description");
               tscontent = tscontent + tag2("/date");
            }
           }
        }


        // 3.4 Meta-data Scheme (Required)
        var SchemeArray = ourform.Metadatascheme.value.split("|");
        for (var i=0;i<SchemeArray.length;i++)
        {
           tscontent = tscontent + tag2("metadatascheme") ;
           tscontent = tscontent + SchemeArray[i];
            tscontent = tscontent + tag2("/metadatascheme");
        }

        // 3.5 Language (Optional)
        var MetadataLanguage = ourform.LanguageMetametadata.value;
        if (MetadataLanguage != "")
        {
           tscontent = tscontent + tag2("language");
           tscontent = tscontent + MetadataLanguage;
            tscontent = tscontent + tag2("/language");
        }

         tscontent = tscontent + tag2("/metametadata");
      // End Section 3 //

      // Begin Section 1 //
         // 1. General (Required)
         tscontent = tscontent + tag2("general");

         // 1.2 Title (Required)
         tscontent = tscontent + tag2("title");
         tscontent = tscontent + tag2("langstring");
         tscontent = tscontent + ourform.Title.value;
         tscontent = tscontent + tag2("/langstring");
         tscontent = tscontent + tag2("/title");


         // 1.3 Catalog Entry (Required)
           var temp_catalogueArray = catalogueList.split("|");
           var temp_entryArray = entryList.split("|");

           /* Length Check */
           length = temp_catalogueArray.length;
           if (temp_entryArray.length > length)
           {
            length = temp_entryArray.length;
           }
           /* End Length Check */

           for (var i=0;i<length-1;i++)
           {
              tscontent = tscontent + tag2("catalogentry");

            // 1.3.1 Catalog  (Required)
               tscontent = tscontent + tag2("catalogue");
                tscontent = tscontent + temp_catalogueArray[i];
               tscontent = tscontent + tag2("/catalogue");

             // 1.3.2 Entry (Required)
               tscontent = tscontent + tag2("entry");
                tscontent = tscontent + tag2("langstring");
                tscontent = tscontent + temp_entryArray[i];
                tscontent = tscontent + tag2("/langstring");
               tscontent = tscontent + tag2("/entry");

             tscontent = tscontent + tag2("/catalogentry");
           }


         // 1.4 Language (Optional)
        var langGen = ourform.LanguageGeneral.value;
        if (langGen != "")
        {
          var temp_langArray = langGen.split(",");
         for (var i=0;i<temp_langArray.length;i++)
         {
            tscontent = tscontent + tag2("language");
            tscontent = tscontent + temp_langArray[i];
            tscontent = tscontent + tag2("/language");
         }
        }

         // 1.5 Description (Required)
        var desc = descriptionList4;
        var temp_descArray = desc.split("|");
        var desc_temp_length3 = temp_descArray.length;
        desc_temp_length3--;
        for (var i=0;i<desc_temp_length3;i++)
        {
               tscontent = tscontent + tag2("description");
             tscontent = tscontent + tag2("langstring");
            tscontent = tscontent + temp_descArray[i];
               tscontent = tscontent + tag2("/langstring");
               tscontent = tscontent + tag2("/description");
        }

         // 1.6 Keywords (Requried)
        var temp_keywordsArray = ourform.KeywordsGeneral.value.split("|");
        for (var i=0;i<temp_keywordsArray.length;i++)
        {
            tscontent = tscontent + tag2("keywords");
            tscontent = tscontent + tag2("langstring");
            tscontent = tscontent + temp_keywordsArray[i];
            tscontent = tscontent + tag2("/langstring");
            tscontent = tscontent + tag2("/keywords");
        }

        // 1.7 Coverage (Optional)
        if (ourform.Coverage.value != "")
        {
           var temp_coverageArray = ourform.Coverage.value.split("|");
           for (var i=0;i<temp_coverageArray.length;i++)
           {
              tscontent = tscontent + tag2("coverage");
               tscontent = tscontent + tag2("langstring");
               tscontent = tscontent + temp_coverageArray[i];
               tscontent = tscontent + tag2("/langstring");
              tscontent = tscontent + tag2("/coverage");
           }
         }

         // 1.8 Structure (Optional)
        if (ourform.Structure.selectedIndex != 0)
        {
           tscontent = tscontent + tag2("structure");
            tscontent = tscontent + tag2("langstring");
            tscontent = tscontent + ourform.Structure.value;
            tscontent = tscontent + tag2("/langstring");
           tscontent = tscontent + tag2("/structure");
        }

         // 1.9 Aggregation Level (Optional)
        if ( ourform.Aggregationlevel.selectedIndex != 0)
        {
        tscontent = tscontent + tag2("aggregationlevel");
        tscontent = tscontent + ourform.Aggregationlevel.value;
        tscontent = tscontent + tag2("/aggregationlevel");
        }

       tscontent = tscontent + tag2("/general");
      // End Section 1 //
      // Begin Section 2 //

         // 2. LifeCycle (Required)
         tscontent = tscontent + tag2("lifecycle");

          // 2.1 Version (Required)
        tscontent = tscontent + tag2("version");
         tscontent = tscontent + tag2("langstring");
         tscontent = tscontent + ourform.Version.value;
         tscontent = tscontent + tag2("/langstring");
         tscontent = tscontent + tag2("/version");

         // 2.2 Status (Required)
        tscontent = tscontent + tag2("status");
         tscontent = tscontent + tag2("langstring");
         tscontent = tscontent + ourform.Status.value;
         tscontent = tscontent + tag2("/langstring");
         tscontent = tscontent + tag2("/status");

          // 2.3 Contribute (Optional)
         if ((roleList != "") || (centityList != "") || (dateList != ""))
         {
           var temp_roleArray = roleList.split("|");
           var temp_centityArray = centityList.split("|");
           var temp_dateArray = dateList.split("|");

           /* check for largest length */
            length = temp_roleArray.length;
            if (temp_centityArray.length > length)
            {
              length = temp_roleArray.length;
            }
            if (temp_dateArray.length > length)
            {
              length = temp_dateArray.length;
            }
           /* end check */
           length--;
           for (var i=0;i<length;i++)
           {
           tscontent = tscontent + tag2("contribute");
            var temp_date = temp_dateArray[i];

             // 2.3.1 Role - List (Optional)
            if (temp_roleArray[i] != "")
            {
                 tscontent = tscontent + tag2("role");
                tscontent = tscontent + tag2("langstring");
                tscontent = tscontent + temp_roleArray[i];
                tscontent = tscontent + tag2("/langstring");
                tscontent = tscontent +tag2("/role");
            }

             // 2.3.2 Entity (Optional)
             // Uses CEntity_Type to write either ORG: or FN:
              // Assumes this is correct.
            if (temp_centityArray[i] != "")
            {
                 var temp_cent = temp_centityArray[i];
              var if_comma2 = false;
              for (var k=0;k<temp_cent.length;k++)
              {
                if (temp_cent.charAt(k) == "~")
               {
                 if_comma2 = true;
               }
              }
              if (if_comma2)
              {
               var temp_centArray = temp_cent.split("~");
               var length_cent = temp_centArray.length;
               for(var j=0;j<length_cent;j++)
               {
                 var arr_temp = temp_centArray[j];
                   tscontent = tscontent + tag2("centity");
                    tscontent = tscontent+ tag2("vcard");
                    tscontent = tscontent + "BEGIN:vCard";
                    tscontent = tscontent + arr_temp;
                    tscontent = tscontent + "END:vCard";
                    tscontent = tscontent + tag2("/vcard");
                    tscontent = tscontent + tag2("/centity");
               }
              }
              else
              {
                   tscontent = tscontent + tag2("centity");
                    tscontent = tscontent+ tag2("vcard");
                    tscontent = tscontent + "BEGIN:vCard";
                    tscontent = tscontent + temp_cent;
                    tscontent = tscontent + "END:vCard";
                    tscontent = tscontent + tag2("/vcard");
                    tscontent = tscontent + tag2("/centity");
              }
            }

               // 2.3.3 Date (Optional)
            if (temp_date != "")
            {
                tscontent = tscontent + tag2("date");
                tscontent = tscontent + tag2("datetime");
                tscontent = tscontent + temp_date;
                tscontent = tscontent + tag2("/datetime");
                 tscontent = tscontent + tag2("/date");
            }
              tscontent = tscontent + tag2("/contribute");
           }
         }
          tscontent = tscontent + tag2("/lifecycle");
      // End Section 2 //
      // Begin Section 4 //

         // 4. Technical (Required)
         tscontent = tscontent + tag2("technical");

         // 4.1 Format (Required)
         tscontent = tscontent + tag2("format");
         tscontent = tscontent + tag2("langstring") ;
         tscontent = tscontent + ourform.Format.value;
         tscontent = tscontent + tag2("/langstring");
         tscontent = tscontent + tag2("/format");

         // 4.2 Size (Optional)
         if ( ourform.Size.value != "" )
         {
            tscontent = tscontent + tag2("size");
            tscontent = tscontent + ourform.Size.value;
            tscontent = tscontent + tag2("/size");
         }

         // 4.3 Location (Required)
        var temp_locArray = ourform.Location.value.split(",");
        for (var i=0;i<temp_locArray.length;i++)
        {
           tscontent = tscontent + tag2("location");
           tscontent = tscontent + temp_locArray[i];
           tscontent = tscontent + tag2("/location");
        }

         // 4.4 Requirements (Optional)
        if ((typeList != "") || (nameList != "") || (minverList != "") || (maxverList != ""))
        {
         var temp_typeArray = typeList.split("|");
         var temp_nameArray = nameList.split("|");
         var temp_minverArray = minverList.split("|");
         var temp_maxverArray = maxverList.split("|");

         /* Length Check */
         length = temp_typeArray.length;
         if (temp_nameArray.length > length)
         {
           length = temp_nameArray.length;
         }
         if (temp_minverArray.length > length)
         {
           length = temp_minverArray.length;
         }
         if (temp_maxverArray.length > length)
         {
           length = temp_maxverArray.length;
         }
         /* End Length Check */

         for (var i=0;i<length;i++)
         {
           tscontent = tscontent + tag2("requirements");

             // 4.4.1 Type - List (Optional)
           if (temp_typeArray[i] != "")
           {
              tscontent = tscontent + tag2("type");
               tscontent = tscontent + tag2("langstring");
               tscontent = tscontent + temp_typeArray[i];
               tscontent = tscontent + tag2("/langstring");
               tscontent = tscontent + tag2("/type");
           }

             // 4.4.2 Name (Optional)
           if (temp_nameArray[i] != "")
           {
               tscontent = tscontent + tag2("name");
               tscontent = tscontent + tag2("langstring");
               tscontent = tscontent + temp_nameArray[i];
               tscontent = tscontent + tag2("/langstring");
               tscontent = tscontent + tag2("/name");
           }

             // 4.4.3 Minimum Version (Optional)
           if (temp_minverArray[i] != "")
           {
               tscontent = tscontent + tag2("minimumversion");
               tscontent = tscontent + temp_minverArray[i];
               tscontent = tscontent + tag2("/minimumversion");
           }

           // 4.4.4 Maximum Version (Optional)
           if (temp_maxverArray[i] != "")
           {
               tscontent = tscontent + tag2("maximumversion");
               tscontent = tscontent + temp_maxverArray[i];
               tscontent = tscontent + tag2("/maximumversion");
           }
             tscontent = tscontent + tag2("/requirements");
         }
        }


         // 4.5 Installation Remarks (Optional)
         if ( ourform.Installationremarks.value != "" )
         {
            tscontent = tscontent + tag2("installationremarks");
            tscontent = tscontent + tag2("langstring");
            tscontent = tscontent + ourform.Installationremarks.value;
            tscontent = tscontent + tag2("/langstring");
            tscontent = tscontent + tag2("/installationremarks");
         }

         // 4.6 Other Platform Requirements (Optional)
         if ( ourform.Otherplatformrequirements.value != "" )
         {
            tscontent = tscontent + tag2("otherplatformrequirements");
            tscontent = tscontent + tag2("langstring");
            tscontent = tscontent + ourform.Otherplatformrequirements.value;
            tscontent = tscontent + tag2("/langstring");
            tscontent = tscontent + tag2("/otherplatformrequirements");
         }

         // 4.7 Duration (Optional)
         if ( ourform.Duration.value != "" )
         {
            tscontent = tscontent + tag2("duration");
            tscontent = tscontent + tag2("datetime") + "0000-00-00T";
            tscontent = tscontent + ourform.Duration.value;
            tscontent = tscontent + tag2("/datetime");
            tscontent = tscontent + tag2("/duration");
         }
         tscontent = tscontent + tag2("/technical");
      // End Section 4 //
      // Begin Section 5 //

         // <!-- 5. Educational (Optional) -->
        if ((ourform.Interactivitytype.selectedIndex != 0) || (ourform.Learningresourcetype.value != "") || (ourform.Interactivitylevel.selectedIndex != 0) || (ourform.Semanticdensity.selectedIndex != 0) || (intendedList != "") || (ourform.Learningcontext.value != "") || (ourform.Typicalagerange.value != "") || (ourform.Difficulty.selectedIndex != 0) || (ourform.Typicallearningtime.value != "") || (ourform.DescriptionEducational.value != "") || (ourform.LanguageEducational.value != ""))
        {
           tscontent = tscontent + tag2("educational");

          // 5.1 Interactivity Type (Optional)
          if ( ourform.Interactivitytype.selectedIndex != 0)
          {
            tscontent = tscontent + tag2("interactivitytype");
            tscontent = tscontent + tag2("langstring");
            tscontent = tscontent + ourform.Interactivitytype.value;
            tscontent = tscontent + tag2("/langstring");
            tscontent = tscontent + tag2("/interactivitytype");
          }

          //  5.2 Learning Resource Type - List (Optional)
            if ( ourform.Learningresourcetype.value != "" )
          {
              var temp_lrt = ourform.Learningresourcetype.value;
              var if_comma3 = false;
              for (var k=0;k<temp_lrt.length;k++)
              {
                if (temp_lrt.charAt(k) == "|")
               {
                 if_comma3 = true;
               }
              }
              if (if_comma3)
              {
               var temp_lrtArray = temp_lrt.split("|");
               var length_lrt = temp_lrtArray.length;
               for(var j=0;j<length_lrt;j++)
               {
                     tscontent = tscontent + tag2("learningresourcetype");
                     tscontent = tscontent + tag2("langstring");
                    tscontent = tscontent + temp_lrtArray[j];
                   tscontent = tscontent + tag2("/langstring");
                     tscontent = tscontent + tag2("/learningresourcetype");
               }
              }
              else
              {
               tscontent = tscontent + tag2("learningresourcetype");
                  tscontent = tscontent + tag2("langstring");
                 tscontent = tscontent + ourform.Learningresourcetype.value;
                tscontent = tscontent + tag2("/langstring");
                  tscontent = tscontent + tag2("/learningresourcetype");
              }
            }

          // 5.3 Interactivity Level (Optional)
          if ( ourform.Interactivitylevel.selectedIndex != 0)
          {
            tscontent = tscontent + tag2("interactivitylevel");
            tscontent = tscontent + ourform.Interactivitylevel.value;
            tscontent = tscontent + tag2("/interactivitylevel");
          }

          // 5.4 Semantic Density (Optional)
            if ( ourform.Semanticdensity.selectedIndex != 0)
          {
            tscontent = tscontent + tag2("semanticdensity");
            tscontent = tscontent + ourform.Semanticdensity.value;
            tscontent = tscontent + tag2("/semanticdensity");
          }

          // 5.5 Intended End User Role (Optional)
          if ( intendedList != "" )
          {
            var temp_intendedArray = intendedList.split("|");
            for (var i=0;i<temp_intendedArray.length-1;i++)
            {
               tscontent = tscontent + tag2("intendedenduserrole");
               tscontent = tscontent + tag2("langstring");
               tscontent = tscontent + temp_intendedArray[i];
               tscontent = tscontent + tag2("/langstring");
               tscontent = tscontent + tag2("/intendedenduserrole");
            }
          }

          // 5.6 Learning Context (Optional)
          if ( ourform.Learningcontext.value != "")
          {
              var temp_lc = ourform.Learningcontext.value;
              var if_comma4 = false;
              for (var k=0;k<temp_lc.length;k++)
              {
                if (temp_lc.charAt(k) == "|")
               {
                 if_comma4 = true;
               }
              }
              if (if_comma4)
              {
               var temp_lcArray = temp_lc.split(",");
               var length_lc = temp_lcArray.length;
               for(var j=0;j<length_lc;j++)
               {
                  tscontent = tscontent + tag2("learningcontext");
                  tscontent = tscontent + tag2("langstring");
                  tscontent = tscontent + temp_lcArray[j];
                  tscontent = tscontent + tag2("/langstring");
                  tscontent = tscontent + tag2("/learningcontext");
               }
              }
              else
              {
               tscontent = tscontent + tag2("learningcontext");
               tscontent = tscontent + tag2("langstring");
               tscontent = tscontent + ourform.Learningcontext.value;
               tscontent = tscontent + tag2("/langstring");
               tscontent = tscontent + tag2("/learningcontext");
               }
          }

          // 5.7 Typical Age Range (Optional)
          if ( ourform.Typicalagerange.value != "")
          {
              var temp_tar = ourform.Typicalagerange.value;
              var if_comma5 = false;
              for (var k=0;k<temp_tar.length;k++)
              {
                if (temp_tar.charAt(k) == "|")
               {
                 if_comma5 = true;
               }
              }
              if (if_comma5)
              {
               var temp_tarArray = temp_tar.split(",");
               var length_tar = temp_tarArray.length;
               for(var j=0;j<length_tar;j++)
               {
                  tscontent = tscontent + tag2("typicalagerange");
                  tscontent = tscontent + tag2("langstring");
                  tscontent = tscontent + temp_tarArray[j];
                  tscontent = tscontent + tag2("/langstring");
                  tscontent = tscontent + tag2("/typicalagerange");
               }
              }
              else
              {
               tscontent = tscontent + tag2("typicalagerange");
               tscontent = tscontent + tag2("langstring");
               tscontent = tscontent + ourform.Typicalagerange.value;
               tscontent = tscontent + tag2("/langstring");
               tscontent = tscontent + tag2("/typicalagerange");
               }
          }

          // 5.8 Difficulty (Optional)
          if ( ourform.Difficulty.selectedIndex != 0)
          {
            tscontent = tscontent + tag2("difficulty");
            tscontent = tscontent + ourform.Difficulty.value;
            tscontent = tscontent + tag2("/difficulty");
          }

          // 5.9 Typical Learning Time (Optional)
            if ( ourform.Typicallearningtime.value != "" )
            {
               tscontent = tscontent + tag2("typicallearningtime");
               tscontent = tscontent + tag2("datetime") + "0000-00-00T";
               tscontent = tscontent + ourform.Typicallearningtime.value;
               tscontent = tscontent + tag2("/datetime");
               tscontent = tscontent + tag2("/typicallearningtime");
            }

          // 5.10 Description (Optional)
          if ( ourform.DescriptionEducational.value != "")
          {
            tscontent = tscontent + tag2("description");
            tscontent = tscontent + tag2("langstring");
            tscontent = tscontent + ourform.DescriptionEducational.value;
            tscontent = tscontent + tag2("/langstring");
            tscontent = tscontent + tag2("/description");
          }

          // 5.11 Language (Optional)
          var langEdu = ourform.LanguageEducational.value;
          if ( langEdu != "")
          {
           var temp_langArray2 = langEdu.split(",");
            for(var i=0;i<temp_langArray2.length;i++)
            {
            tscontent = tscontent + tag2("language");
            tscontent = tscontent + temp_langArray2[i];
            tscontent = tscontent + tag2("/language");
            }
          }


            tscontent = tscontent + tag2("/educational");
        }
      // End Section 5 //
      // Begin Section 6 //

         // 6. Rights (Required)
         tscontent = tscontent + tag2("rights");

         // 6.1 Cost (Required)
         tscontent = tscontent + tag2("cost");
         tscontent = tscontent + tag2("langstring");
         tscontent = tscontent + ourform.Cost.value;
         tscontent = tscontent + tag2("/langstring");
         tscontent = tscontent + tag2("/cost");

         // 6.2 Copyright and Other Restrictions (Required)
         tscontent = tscontent + tag2("copyrightandotherrestrictions");
         tscontent = tscontent + tag2("langstring");
         tscontent = tscontent + ourform.Copyrightandotherrestrictions.value;
         tscontent = tscontent + tag2("/langstring");
         tscontent = tscontent + tag2("/copyrightandotherrestrictions");

         // 6.3 Description (Optional)
         if ( ourform.DescriptionRights.value != "" )
         {
            tscontent = tscontent + tag2("description");
            tscontent = tscontent + tag2("langstring");
            tscontent = tscontent + ourform.DescriptionRights.value;
            tscontent = tscontent + tag2("/langstring");
            tscontent = tscontent + tag2("/description");
         }
         tscontent = tscontent + tag2("/rights");
      // End Section 6 //
      // Begin Section 7 //

        // <!-- 7. Relation (Optional) -->
        if ((kindList != "") || (descriptionList != ""))
        {
         var temp_kindArray = kindList.split("|");
          var temp_desArray = descriptionList.split("|");
         /* Length Check */
         var length_relation = 0;
         length_relation = temp_kindArray.length;
         if (temp_desArray.length > length_relation)
         {
            length_relation = temp_desArray.length;
         }
         /* End Length Check */
          length_relation--;

         for (var i=0;i<length_relation;i++)
         {
            var des = temp_desArray[i];
            var kin = temp_kindArray[i];

             tscontent = tscontent + tag2("relation");
            // 7.1 Kind - List (Optional)
            if ( kin != "" )
            {
                 tscontent = tscontent + tag2("kind");
              tscontent = tscontent + tag2("langstring");
              tscontent = tscontent + kin;
              tscontent = tscontent + tag2("/langstring");
              tscontent = tscontent + tag2("/kind");
            }

            // 7.2 Resource & 7.2.2 Description (Optional)
            if ( des != "")
            {
               tscontent = tscontent + tag2("resource");

               // 7.2.2 Description
               tscontent = tscontent + tag2("description");
               tscontent = tscontent + tag2("langstring");
               tscontent = tscontent + des;
               tscontent = tscontent + tag2("/langstring");
               tscontent = tscontent + tag2("/description");

               tscontent = tscontent + tag2("/resource");
            }
            tscontent = tscontent + tag2("/relation");
         }
        }
      // End Section 7 //
      // Begin Section 8 //

        // <!-- 8. Annotation (Optional) -->
        if ((centityList3 != "")||(dateList3 != "")||(descriptionList2 != ""))
        {
          var temp_cent3Array = centityList3.split("|");
         var temp_date3Array = dateList3.split("|");
         var temp_desc2Array = descriptionList2.split("|");

         /* Length Check */
         var anno_length = temp_cent3Array.length;
         if (temp_date3Array.length > anno_length)
         {
            anno_length = temp_date3Array.length;
         }
         if (temp_desc2Array.length > anno_length)
         {
            anno_length = temp_desc2Array.length;
         }
         anno_length--;
         /* End Length Check */
         for (var i=0;i<anno_length;i++)
         {
            var cent3 = temp_cent3Array[i];
            var date3 = temp_date3Array[i];
            var desc2 = temp_desc2Array[i];

               tscontent = tscontent + tag2("annotation");
            // 8.1 Centity (Optional)
            if ( cent3 != "")
            {
              tscontent = tscontent + tag2("centity");
              tscontent = tscontent + tag2("vcard");
              tscontent = tscontent + "begin : vcard";
              tscontent = tscontent + "org :" + cent3;
              tscontent = tscontent + "end : vcard";
              tscontent = tscontent + tag2("/vcard");
              tscontent = tscontent + tag2("/centity");
            }

            // 8.2 Date (Optional)
            if ( date3 != "")
            {
              tscontent = tscontent + tag2("date");
              tscontent = tscontent + tag2("datetime");
              tscontent = tscontent + date3;
              tscontent = tscontent + tag2("/datetime");
              tscontent = tscontent + tag2("description");
              tscontent = tscontent + "Description for the date.";
              tscontent = tscontent + tag2("/description");
              tscontent = tscontent + tag2("/date");
            }

            // 8.3 Description (Optional)
            if ( desc2 != "")
            {
              tscontent = tscontent + tag2("description");
              tscontent = tscontent + tag2("langstring");
              tscontent = tscontent + desc2;
              tscontent = tscontent + tag2("/langstring");
              tscontent = tscontent + tag2("/description");
            }
            tscontent = tscontent + tag2("/annotation");
         }
        }

      // End Section 8 //

      // Begin Section 9 //
         // 9. Classification (Required)
         var temp_purposeArray = purposeList.split("|");
         var temp_desc3Array = descriptionList3.split("|");
         var temp_key2Array = keywordsList2.split("|");

         /* length check */
         var length_class = temp_purposeArray.length;
         if (temp_desc3Array.length > length_class)
         {
            length_class = temp_desc3Array.length;
         }
         if (temp_key2Array.length > length_class)
         {
            length_class = temp_key2Array.length;
         }
         length_class--;
         /* end Length Check */

         for (var i=0;i<length_class;i++)
         {
            var pur = temp_purposeArray[i];
            var desc3 = temp_desc3Array[i];
            var key2 = temp_key2Array[i];

            tscontent = tscontent + tag2("classification");

              // 9.1 Purpose - List (Required)
               tscontent = tscontent + tag2("purpose");
               tscontent = tscontent + tag2("langstring");
               tscontent = tscontent + pur;
               tscontent = tscontent + tag2("/langstring");
               tscontent = tscontent + tag2("/purpose");

              //
              // 9.3 Description (Required)
               tscontent = tscontent + tag2("description");
               tscontent = tscontent + tag2("langstring");
               tscontent = tscontent + desc3;
               tscontent = tscontent + tag2("/langstring");
               tscontent = tscontent + tag2("/description");

               // 9.4 Keywords (Required)
                 var temp_keyword = key2;
              var if_comma5 = false;
              for (var k=0;k<temp_keyword.length;k++)
              {
                if (temp_keyword.charAt(k) == "~")
               {
                 if_comma5 = true;
               }
              }
              if (if_comma5)
              {
               var temp_key_2Array = temp_keyword.split("~");
               var length_key = temp_key_2Array.length;
               for(var j=0;j<length_key;j++)
               {
                     tscontent = tscontent + tag2("keywords");
                     tscontent = tscontent + tag2("langstring");
                     tscontent = tscontent + temp_key_2Array[j];
                     tscontent = tscontent + tag2("/langstring");
                     tscontent = tscontent + tag2("/keywords");
                 }
              }
              else
              {
                     tscontent = tscontent + tag2("keywords");
                     tscontent = tscontent + tag2("langstring");
                     tscontent = tscontent + key2;
                     tscontent = tscontent + tag2("/langstring");
                     tscontent = tscontent + tag2("/keywords");
               }
            tscontent = tscontent + tag2("/classification");
         }
        tscontent = tscontent + tag2("/record");
        }
      // End Section 9 //
       //  tscontent = tscontent + tag2("/record");


</SCRIPT>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
//-->

function XmlLoad()
{
	if(document.XmlLoadForm.file.value=="") {
		//alert("ÆÄ");
        document.XmlLoadForm.file.focus();
		return false;	}
		document.XmlLoadForm.action="servlet/MetadataBeans.XmlUpload";
		document.XmlLoadForm.encoding="multipart/form-data"
		document.XmlLoadForm.submit();
}
</script>
<style type="text/css">
table { font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size: 9pt ; line-height:12pt;color:#343434 ;>r-spacing:0px;border-padding:0px} 
td { font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size: 9pt ; line-height:12pt;color:#343434 ;>r-spacing:0px;border-padding:0px} 

a:active, a:link, a:visited {  color: #1A9EBF; text-decoration: none}
a:hover {  color: #DD8E24; text-decoration: none}

body{	background-image:url("hrdi_img/right_bg.gif");
	background-repeat:repeat-y;



                scrollbar-face-color: ffffff; 
                scrollbar-shadow-color: #cccccc; 
                scrollbar-highlight-color: #cccccc; 
                scrollbar-3dlight-color: #efefef; 
                scrollbar-darkshadow-color: #efefef; 
                scrollbar-track-color : #efefef; 
                scrollbar-arrow-color: #B9B9B9; 
}
input{	
		background-color:#ffffff; border:1 solid #838383;  font-family:gulim; font-size:9pt;color:#545454;
}
textarea{	
		background-color:#ffffff; border:1 solid #838383;  font-family:gulim; font-size:9pt;color:#545454;
}
.btn	{background-color:#E6EDF9; BORDER-TOP-WIDTH: 1px solid #CADEFF; BORDER-RIGHT: 1px solid #2845A6; BORDER-LEFT-WIDTH: 1px solid #CADEFF; BORDER-BOTTOM:1px solid #0C1E72; font-family:gulim; font-size:9pt;color:#0C1E72; cursor:hand}
</style>
</head>
<body onload="displayCatalogue('<%= s1_catalogue %>','<%= s1_entry %>');displayContribute('<%= s2_role %>','<%= s2_centity %>','<%= s2_date %>');displayCatalogue2('<%= s3_catalogue %>','<%= s3_entry %>');displayContribute2('<%= s3_role %>','<%= s3_centity %>','<%= s3_date %>');displayRequirements('<%= s4_type %>','<%= s4_name %>','<%= s4_min %>','<%= s4_max %>');displayRelation('<%= s7_kind %>','<%= s7_description %>');displayAnnotation('<%= s8_centity %>','<%= s8_date %>','<%= s8_description %>');displayClassification('<%= s9_purpose %>','<%= s9_description %>','<%= s9_keyword %>')">
<!-- form action="" method="post" name="XmlLoadForm" target="_self">
<table width="51%" border="1" align="center" cellpadding="3" cellspacing="0">
    <tr> 
      <td width="29%"><div align="center"><strong><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>XML Upload</font></strong></div></td>
      <td width="71%"><input name="file" type="file" size="30"></td>
    </tr>
    <tr> 
      <td colspan="2">
	  <div align="center">
          <input type="button" name="Submit" value="::::: ¿Ã¸®±â :::::">
      </div></td>
    </tr>
  </table>
  <div align="center"></div>
</form -->
<form action="/servlet/controller.contents.MetaDataServlet" method="post" name="ourform" target="_self" id="ourform">
  <input type="hidden" name="p_process"  value="updateSave">
  <input type="hidden" name="p_metadata_idx"  value="<%= data.getMetadata_idx() %>">
  <table width="98%" cellspacing="0" cellpaddin="0" border="0" align="center">
   <tr>
      <td bgcolor="#333333" colspan="2"></td>
   </tr>
</table>
  <table width="98%" cellspacing="0" cellpadding="1" align="center">
    <tr> 
      <td Class="Form" colspan="2"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;><br>
        &nbsp;<strong><font color="#800000">Section 1: General</font></strong><br>
        <input name="object_id" type="hidden" id="object_id" value="">
        </font></td>
    </tr>
    <TR> 
      <td Class="Restricted" colspan="2"><p><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> &nbsp;&nbsp;
      1.1 Identifier<br>
          </font></p></td>
    </TR>
    <tr> 
      <td Class="Required"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      1.2 <strong><font color="#24309B">Title</font></strong></font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= data.getGeneral_title() %>" size="40" maxlength="200" name="Title">
        </font></td>
    </tr>
    <tr> 
      <td height="92" valign="top" Class="Required"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      1.3 <strong><font color="#24309B">Catalog Entry</font></strong>&nbsp;&nbsp;&nbsp;<a href="javascript:modifyGeneral_CatalogEntry()">¢ºedit</a> 
        </font></td>
      <td bgcolor="ffffff" class="Unrequired"> <font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <textarea rows="3" name="CatalogueEntry" wrap=OFF cols="45" readonly>
      </textarea>
        <!--a href="help/multexplan.htm" target="info"> ?</a--></font></td>
    </tr>
    <tr> 
      <td colspan="2" height="15"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
    </tr>
    <tr> 
      <td height="20" bgcolor="ffffff" Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      1.4 Language&nbsp;&nbsp;</font></td>
      <td bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" size="40" name="LanguageGeneral" value="<%= s1_language %>">
        &nbsp;&nbsp;<!--a href="../help/multexplan2a.htm" target="info">?</a--></font></td>
    </tr>
    <tr> 
      <td height="35"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
      <td Class="comment"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> &quot;<strong><font color="#FF0000">,</font></strong>&quot;·Î 
        ±¸ºÐÀÚ¸¦ ÁÙ¼ö ÀÖ½À´Ï´Ù.<br>
        (<strong><font color="#FF0000">ex.</font></strong> kr, fr, en-US, en-UK)</font></td>
    </tr>
    <tr> 
      <td Class="Required" bgcolor="ffffff" valign="top"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      1.5 <strong><font color="#24309B">Description</font></strong>&nbsp;&nbsp;&nbsp;<!-- a href="javascript:modifyGeneral_Description()">¢ºedit</a --></font></td>
      <td class="Unrequired" bgcolor="ffffff"> <font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <textarea rows="3" name="DescriptionGeneral" WRAP=OFF cols="45"><%= s1_description %></textarea>
        &nbsp;&nbsp;<!--a href="../help/multexplan.htm" target="info">?</a> </font--></td>
    </tr>
    <tr> 
      <td colspan="2" height="15"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
    </tr>
    <tr> 
      <td bgcolor="ffffff" Class="Required"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      1.6<strong><font color="#24309B"> Keywords</font></strong>&nbsp;</font></td>
      <td bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" name="KeywordsGeneral" value="<%= s1_keyword %>" size="40">
        &nbsp;&nbsp;<!--a href="../help/multexplan2.htm" target="info">?</a--></font></td>
    </tr>
    <tr> 
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
      <td Class="comment"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&quot;<strong><font color="#FF0000">|</font></strong>&quot; 
        ·Î ±¸ºÐÀÚ¸¦ ÁÙ¼ö ÀÖ½À´Ï´Ù.<br>
        (<strong><font color="#FF0000">ex.</font></strong> maritime navigation 
        | sailing conditions) </font></td>
    </tr>
    <tr> 
      <td bgcolor="ffffff" Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      1.7 Coverage&nbsp;<a href="help/1.7Coverage.htm" target="info"> 
        </a></font></td>
      <td bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= s1_coverage %>" size="40" name="Coverage" bgcolor="000000">
        &nbsp;&nbsp;<!--a href="../help/multexplan2.htm" target="info">?</a--></font></td>
    </tr>
    <tr> 
      <td height="32"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
      <td Class="comment"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> &quot;<strong><font color="#FF0000">|</font></strong>&quot; 
        ·Î ±¸ºÐÀÚ¸¦ ÁÙ¼ö ÀÖ½À´Ï´Ù.<br>
        (<strong><font color="#FF0000">ex.</font></strong> 1990-2000 | 1999-2001) 
        </font></td>
    </tr>
    <tr> 
      <td Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      1.8 Structure&nbsp;&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <select name="Structure" Class="Unrequired">
          <option value=""></option>
          <option <%= data.getGeneral_structure().equals("Collection")?"selected":"" %> value="Collection">Collection</option>
          <option <%= data.getGeneral_structure().equals("Mixed")?"selected":"" %> value="Mixed">Mixed</option>
          <option <%= data.getGeneral_structure().equals("Linear")?"selected":"" %> value="Linear">Linear</option>
          <option <%= data.getGeneral_structure().equals("Hierarchical")?"selected":"" %> value="Hierarchical">Hierarchical</option>
          <option <%= data.getGeneral_structure().equals("Networked")?"selected":"" %> value="Networked">Networked</option>
          <option <%= data.getGeneral_structure().equals("Branched")?"selected":"" %> value="Branched">Branched</option>
          <option <%= data.getGeneral_structure().equals("Parceled")?"selected":"" %> value="Parceled">Parceled</option>
          <option <%= data.getGeneral_structure().equals("Atomic")?"selected":"" %> value="Atomic">Atomic</option>
        </select>
        </font></td>
    </tr>
    <tr> 
      <td Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      1.9 Aggregration Level&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <select name="Aggregationlevel" Class="Unrequired">
          <option value=""> 
          <option <%= data.getGeneral_aggregationlevel().equals("0")?"selected":"" %> value="0">0 - smallest level of aggregation</option>
          <option <%= data.getGeneral_aggregationlevel().equals("1")?"selected":"" %> value="1">1 - collection of atoms</option>
          <option <%= data.getGeneral_aggregationlevel().equals("2")?"selected":"" %> value="2">2 - collection of level 1 resources</option>
          <option <%= data.getGeneral_aggregationlevel().equals("3")?"selected":"" %> value="3">3 - largest level of granularity</option>
        </select>
        </font></td>
    </tr>
<tr><td height=1 background='/images/metadata/dot2.gif' colspan="2"></td></tr>
    <tr> 
      <td Class="Form" colspan="2"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;><br>
        &nbsp;<strong><font color="#800000">Section 2: Life Cycle</font></strong><br>
        </font></td>
    </tr>
    <tr> 
      <td Class="Required"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      2.1 <strong><font color="#24309B">Version</font></strong>&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= data.getLifecycle_version() %>" size="40" maxlength="200" name="Version">
        </font></td>
    </tr>
    <tr> 
      <td Class="Required"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      2.2 <font color="#24309B"><strong>Status</strong></font>&nbsp;&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <select class="Unrequired" name="Status">
          <option value=""></option>
          <option <%= data.getLifecycle_status().equals("Draft")?"selected":"" %> value="Draft">Draft</option>
          <option <%= data.getLifecycle_status().equals("Final")?"selected":"" %> value="Final">Final</option>
          <option <%= data.getLifecycle_status().equals("Revised")?"selected":"" %> value="Revised">Revised</option>
          <option <%= data.getLifecycle_status().equals("Unavailable")?"selected":"" %> value="Unavailable">Unavailable</option>
        </select>
        </font></td>
    </tr>
    <tr> 
      <td Class="Unrequired" valign="top" bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;<b>
      2.3 Contribute&nbsp;</b>&nbsp;&nbsp;<a href="javascript:modifyLifeCycle_Contribute()">¢ºedit</a></font></td>
      <td class="Unrequired" bgcolor="ffffff"> <font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <textarea rows="3" name="Contribute" WRAP=OFF cols="45" readonly>
         </textarea>
        &nbsp;&nbsp;<!--a href="help/multexplan.htm" target="info">?</a> </font--></td>
    </tr>
    <tr> 
      <td colspan="2" height="15"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
    </tr>
<tr><td height=1 background='/images/metadata/dot2.gif' colspan="2"></td></tr>
    <tr> 
      <td Class="Form" colspan="2"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;><br>
        <strong><font color="#800000">Section 3: Meta Meta-data</font></strong>&nbsp;<br>
        </font></td>
    </tr>
    <tr> 
      <td Class="Restricted" colspan="2"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;><br>
        &nbsp;&nbsp;
        3.1 Identifier&nbsp;<br>
        </font></td>
    </tr>
    <tr> 
      <td Class="Unrequired" bgcolor="ffffff" valign="top"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;<b>
      3.2 Catalogue Entry&nbsp;&nbsp;</b>&nbsp;<a href="javascript:modifyMetametaCatalogEntry()">¢ºedit</a></font></td>
      <td class="Unrequired" bgcolor="ffffff"> <font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <textarea rows="3" name="CatalogueEntryMetametadata" WRAP=OFF cols="45" readonly>
         </textarea>
        &nbsp;&nbsp;<!--a href="../help/multexplan.htm" target="info">?</a> </font--></td>
    </tr>
    <tr> 
      <td colspan="2" height="15"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
    </tr>
    <tr> 
      <td Class="Unrequired" bgcolor="ffffff" valign="top"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;<b>
      3.3 Contribute&nbsp;&nbsp;</b>&nbsp;<a href="javascript:modifyMetametaContribute()">¢ºedit</a></font></td>
      <td class="Unrequired" bgcolor="ffffff"> <font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <textarea rows="3" name="ContributeMetametadata" WRAP=OFF cols="45" readonly>
         </textarea>
        &nbsp;&nbsp;<!--a href="help/multexplan.htm" target="info">?</a> </font--></td>
    </tr>
    <tr> 
      <td colspan="2" height="15"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
    </tr>
    <tr> 
      <td Class="Required" bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      3.4<strong><font color="#24309B"> Meta-data Scheme</font></strong>&nbsp;</font></td>
      <td bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= s3_scheme %>" size="40"  maxlength="300" name="Metadatascheme">
        &nbsp;&nbsp;<!--a href="help/multexplan2.htm" target="info">?</a--></font></td>
    </tr>
    <!-- tr> 
      <td height="29"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
      <td Class="comment"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> &quot;<strong><font color="#FF0000">|</font></strong>&quot; 
        ·Î ±¸ºÐÀÚ¸¦ ÁÙ¼ö ÀÖ½À´Ï´Ù.<br>
        (<strong><font color="#FF0000">ex.</font></strong> ADL SCORM 1.1 | ADL 
        SCORM 1.2) </font></td>
    </tr -->
    <tr> 
      <td height="25" Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      3.5 Language&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= data.getMetameta_language() %>" size="40" name="LanguageMetametadata" maxlength="10">
        </font></td>
    </tr>
    <tr> 
      <td height="23"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
      <td Class="comment"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> (<strong><font color="#FF0000">ex.</font></strong> 
        en-US) </font></td>
    </tr>
<tr><td height=1 background='/images/metadata/dot2.gif' colspan="2"></td></tr>
    <tr> 
      <td Class="Form" colspan="2"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;><br>
        &nbsp;<strong><font color="#800000">Section 4: Technical</font></strong><br>
        </font></td>
    </tr>
    <tr> 
      <td Class="Required"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      4.1<strong><font color="#24309B"> Format</font></strong>&nbsp;&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= data.getT_format() %>" size="40" maxlength="300" name="Format">
        </font></td>
    </tr>
    <tr> 
      <td height="22" Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      4.2 Size&nbsp;&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= data.getTechnical_size() %>" size="40" maxlength="20" name="Size">
        </font></td>
    </tr>
    <tr> 
      <td Class="Required" bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      4.3<font color="#24309B"><strong> Location&nbsp;</strong></font>&nbsp;</font></td>
      <td bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= data.getT_location() %>" size="47" maxlength="300" name="Location">
        <!--a href="help/multexplan2.htm" target="info">?</a--></font></td>
    </tr>
    <!-- tr> 
      <td height="33"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
      <td height="33"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&quot;<strong><font color="#FF0000">|</font></strong>&quot; 
        ·Î ±¸ºÐÀÚ¸¦ ÁÙ¼ö ÀÖ½À´Ï´Ù.<br>
        (<strong><font color="#FF0000">ex.</font></strong> C:/Metadata/sco001 
        | C:/Metadata/sco002 ) </font></td>
    </tr -->
    <tr> 
      <td Class="Unrequired" bgcolor="ffffff" valign="top"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;<b>
      4.4 Requirements&nbsp;&nbsp;</b>&nbsp;<a href="javascript:modifyRequirements()">¢ºedit</a></font></td>
      <td class="Unrequired" bgcolor="ffffff"> <font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <textarea rows="3" name="Requirements" WRAP=OFF cols="45" readonly>
         </textarea>
        &nbsp;&nbsp;<!--a href="help/multexplan.htm" target="info">?</a> </font--></td>
    </tr>
    <tr> 
      <td Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      4.5 Installation Remarks&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= data.getTechnical_installationremarks() %>" size="40" maxlength="2000" name="Installationremarks">
        </font></td>
    </tr>
    <tr> 
      <td Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      4.6 Other Platform Requirements&nbsp;&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= data.getTechnical_otherrequirements() %>" size="40" maxlength="3000" name="Otherplatformrequirements">
        </font></td>
    </tr>
    <tr> 
      <td height="21" Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      4.7 Duration&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= data.getTechnical_duration() %>" size="40" maxlength="8" name="Duration">
        </font></td>
    </tr>
    <tr> 
      <td height="23"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
      <td Class="comment"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>(<strong><font color="#FF0000">ex.</font></strong> 
        hh:mm:ss )</font></td>
    </tr>
<tr><td height=1 background='/images/metadata/dot2.gif' colspan="2"></td></tr>
    <tr> 
      <td Class="Form" colspan="2"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;><br>
        &nbsp;<b><font color="#800000">Section 5: Educational</font></b>&nbsp;&nbsp;<br>
        </font></td>
    </tr>
    <tr> 
      <td Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      5.1 Interactivity Type&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <select name="Interactivitytype" Class="Unrequired">
          <option value=""></option>
          <option <%= data.getEducational_interactivitytype().equals("Active")?"selected":"" %> value="Active">Active</option>
          <option <%= data.getEducational_interactivitytype().equals("Expositive")?"selected":"" %> value="Expositive">Expositive</option>
          <option <%= data.getEducational_interactivitytype().equals("Mixed")?"selected":"" %> value="Mixed">Mixed</option>
          <option <%= data.getEducational_interactivitytype().equals("Undefined")?"selected":"" %> value="Undefined">Undefined</option>
        </select>
        </font></td>
    </tr>
    <tr> 
      <td Class="Unrequired" bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      5.2 Learning Resource Type&nbsp;</font></td>
      <td bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= data.getE_learningtype() %>" size="40"  maxlength="300" name="Learningresourcetype">
        &nbsp;&nbsp;<!--a href="../help/multexplan2.htm" target="info">?</a--></font></td>
    </tr>
    <!-- tr> 
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
      <td Class="comment"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> &quot;<strong><font color="#FF0000">|</font></strong>&quot; 
        ·Î ±¸ºÐÀÚ¸¦ ÁÙ¼ö ÀÖ½À´Ï´Ù.<br>
        (<strong><font color="#FF0000">ex.</font></strong> Exercise | Simulation 
        | Exam) </font></td>
    </tr -->
    <tr> 
      <td Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      5.3 Interactivity Level&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <select name="Interactivitylevel" Class="Unrequired">
          <option value=""></option>
          <option <%= data.getEducational_interactivitylevel().equals("very low")?"selected":"" %> value="very low">Very Low</option>
          <option <%= data.getEducational_interactivitylevel().equals("low")?"selected":"" %> value="low">Low</option>
          <option <%= data.getEducational_interactivitylevel().equals("medium")?"selected":"" %> value="medium">Medium</option>
          <option <%= data.getEducational_interactivitylevel().equals("high")?"selected":"" %> value="high">High</option>
          <option <%= data.getEducational_interactivitylevel().equals("very high")?"selected":"" %> value="very high">Very High</option>
        </select>
        </font></td>
    </tr>
    <tr> 
      <td Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      5.4 Semantic Density&nbsp;&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <select name="Semanticdensity" class="Unrequired">
          <option value=""></option>
          <option <%= data.getEducational_semanticdensity().equals("very low")?"selected":"" %> value="very low">Very Low</option>
          <option <%= data.getEducational_semanticdensity().equals("low")?"selected":"" %> value="low">Low</option>
          <option <%= data.getEducational_semanticdensity().equals("medium")?"selected":"" %> value="medium">Medium</option>
          <option <%= data.getEducational_semanticdensity().equals("high")?"selected":"" %> value="high">High</option>
          <option <%= data.getEducational_semanticdensity().equals("very high")?"selected":"" %> value="very high">Very High</option>
        </select>
        </font></td>
    </tr>
    <!-- tr> 
      <td Class="Unrequired" bgcolor="ffffff" valign="top"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;5.5 
        Intended End User Role&nbsp;&nbsp;</font></td>
      <td bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <select class="Unrequired" name="Intendedenduserrole" multiple onChange="IntendedEnd_onchange()">
          <option value="Teacher">Teacher</option>
          <option value="Author">Author</option>
          <option value="Learner">Learner</option>
          <option value="Manager">Manager</option>
        </select>
        &nbsp;&nbsp;</td>
    </tr>
    <tr> 
      <td Class="Unrequired" valign="top" bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;5.5 
        Intended End User Role&nbsp;&nbsp;&nbsp;<a href="javascript:clear_intendList()">clear 
        list</a></font></td>
      <td bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <textarea cols="45" rows="3" name="intend_list" WRAP-OFF readonly><%= s5_role %></textarea>
        </font></td>
    </tr>
    <tr> 
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
      <td Class="comment"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;><br>
        <br>
        </font></td>
    </tr -->
    <tr> 
      <td Class="Unrequired" valign="top" bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      5.5 Intended End User Role</font></td>
      <td bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
		<input type=checkbox <%= check1 %> name="intend_list" value="Teacher">Teacher&nbsp;
		<input type=checkbox <%= check2 %> name="intend_list" value="Author">Author&nbsp;
		<input type=checkbox <%= check3 %> name="intend_list" value="Learner">Learner&nbsp;
		<input type=checkbox <%= check4 %> name="intend_list" value="Manager">Manager&nbsp;
        </font></td>
    </tr>
    <tr> 
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
      <td Class="comment"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;><br>
        <br>
        </font></td>
    </tr>
    <tr> 
      <td Class="Unrequired" bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      5.6 Learning Context&nbsp;</font></td>
      <td bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= data.getE_context() %>" size="40"  maxlength="300" name="Learningcontext">
        &nbsp;&nbsp;<!--a href="../help/multexplan2.htm" target="info">?</a--></font></td>
    </tr>
    <!-- tr> 
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
      <td Class="comment"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&quot;<strong><font color="#FF0000">|</font></strong>&quot; 
        ·Î ±¸ºÐÀÚ¸¦ ÁÙ¼ö ÀÖ½À´Ï´Ù.</font><br>
        (<strong><font color="#FF0000">ex.</font></strong><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>ÃÊµî ÇÐ±³ 
        | ÁßÇÐ±³</font>)</tr>
    <tr --> 
      <td Class="Unrequired" bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      5.7 Typical Age Range&nbsp;</font></td>
      <td bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= data.getE_typicalagerange() %>" size="40" maxlength="300" name="Typicalagerange">
        &nbsp;&nbsp;<!--a href="../help/multexplan2.htm" target="info">?</a--></font></td>
    </tr>
    <!-- tr> 
      <td height="31"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
      <td Class="comment"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> &quot;<strong><font color="#FF0000">|</font></strong>&quot;·Î 
        ±¸ºÐÀÚ¸¦ ÁÙ¼ö ÀÖ½À´Ï´Ù.<br>
        (<strong><font color="#FF0000">ex.</font></strong> 20-25 | 50-60)</font></td>
    </tr -->
    <tr> 
      <td Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      5.8 Difficulty&nbsp;&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <select name="Difficulty" class="Unrequired">
          <option value=""></option>
          <option <%= data.getEducational_difficulty().equals("very easy")?"selected":"" %> value="very easy">Very Easy</option>
          <option <%= data.getEducational_difficulty().equals("easy")?"selected":"" %> value="easy">Easy</option>
          <option <%= data.getEducational_difficulty().equals("medium")?"selected":"" %> value="medium">Medium</option>
          <option <%= data.getEducational_difficulty().equals("difficult")?"selected":"" %> value="difficult">Difficult</option>
          <option <%= data.getEducational_difficulty().equals("very difficult")?"selected":"" %> value="very difficult">Very Difficult</option>
        </select>
        </font></td>
    </tr>
    <tr> 
      <td height="24" Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      5.9 Typical Learning Time&nbsp;&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= data.getEducational_learningtime() %>" size="40" maxlength="8" name="Typicallearningtime">
        </font></td>
    </tr>
    <tr> 
      <td height="20"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
      <td Class="comment"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> (<strong><font color="#FF0000">ex.</font></strong> 
        hh:mm:ss )</font></td>
    </tr>
    <tr> 
      <td height="16" Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp; </font></td>
    </tr>
    <tr> 
      <td Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      5.10 Description&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= data.getEducational_description() %>" size="40" maxlength="3000" name="DescriptionEducational">
        </font></td>
    </tr>
    <tr> 
      <td Class="Unrequired" bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      5.11 Language&nbsp;</font></td>
      <td bgcolor="ffffff"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= s5_language %>" size="40" name="LanguageEducational">
        &nbsp;&nbsp;<!--a href="../help/multexplan2a.htm" target="info">?</a--></font></td>
    </tr>
    <tr> 
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
      <td Class="comment"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> &quot;<strong><font color="#FF0000">,</font></strong>&quot; 
        ·Î ±¸ºÐÀÚ¸¦ ÁÙ¼ö ÀÖ½À´Ï´Ù.<br>
        (<strong><font color="#FF0000">ex.</font></strong> kr, fr, en-US, en-UK)</font></td>
    </tr>
<tr><td height=1 background='/images/metadata/dot2.gif' colspan="2"></td></tr>
    <tr> 
      <td Class="Form" colspan="2"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;><br>
        &nbsp;<strong><font color="#800000">Section 6: Rights</font></strong>&nbsp;&nbsp;<br>
        </font></td>
    </tr>
    <tr> 
      <td Class="Required"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      6.1 <font color="#24309B"><strong>Cost</strong></font>&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <select name="Cost" Class="Unrequired">
          <option <%= data.getRights_cost().equals("yes")?"selected":"" %> value="yes">yes</option>
          <option <%= data.getRights_cost().equals("no")?"selected":"" %> value="no">no</option>
        </select>
        </font></td>
    </tr>
    <tr> 
      <td Class="Required"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      6.2 <strong><font color="#24309B">Copyright and Other Restrictions</font></strong>&nbsp;&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <select name="Copyrightandotherrestrictions" Class="Unrequired">
          <option <%= data.getRights_copyrightrestrictions().equals("yes")?"selected":"" %> value="yes">yes</option>
          <option <%= data.getRights_copyrightrestrictions().equals("no")?"selected":"" %> value="no">no</option>
        </select>
        </font></td>
    </tr>
    <tr> 
      <td Class="Unrequired"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;
      6.3 Description&nbsp;&nbsp;</font></td>
      <td><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <input type="text" value="<%= data.getRights_description() %>" size="40" maxlength="3000" name="DescriptionRights">
        </font></td>
    </tr>
<tr><td height=1 background='/images/metadata/dot2.gif' colspan="2"></td></tr>
    <tr> 
      <td Class="Form" bgcolor="ffffff" valign="top"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;><br>
        &nbsp;<strong><font color="#800000">Section 7: Relation</font></strong>&nbsp;&nbsp;<a href="javascript:modifyRelation()">¢ºedit</a></font></td>
      <td class="Unrequired" bgcolor="ffffff"> <font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <textarea rows="3" name="Relation" WRAP=OFF cols="45" readonly>
         </textarea>
        &nbsp;&nbsp;<!--a href="../help/multexplan.htm" target="info">?</a--></font></td>
    </tr>
    <tr> 
      <td colspan="2" height="15"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
    </tr>
<tr><td height=1 background='/images/metadata/dot2.gif' colspan="2"></td></tr>
    <tr> 
      <td Class="Form" bgcolor="ffffff" valign="top"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;><br>
        <strong><font color="#800000">Section 8: Annotation</font></strong>&nbsp;&nbsp;<a href="javascript:modifyAnnotation()">¢ºedit</a><br>
        </font></td>
      <td class="Unrequired" bgcolor="ffffff"> <font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <textarea rows="3" name="Annotation" WRAP=OFF cols="45" readonly>
         </textarea>
        &nbsp;&nbsp;<!--a href="../help/multexplan.htm" target="info">?</a--></font></td>
    </tr>
    <tr> 
      <td colspan="2" height="15"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
    </tr>
<tr><td height=1 background='/images/metadata/dot2.gif' colspan="2"></td></tr>
    <tr> 
      <td Class="Form" bgcolor="ffffff" colspan="2"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;><br>
        <strong><font color="#800000">Section 9: Classification</font></strong>&nbsp;<a href="javascript:modifyClassification()">¢ºedit</a><br>
        </font></td>
    </tr>
    <tr> 
      <td Class="Required" bgcolor="ffffff" valign="top"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;&nbsp;<strong><font color="#24309B">
      9.1 Purpose</font></strong>&nbsp;&nbsp;</font></td>
      <td bgcolor="ffffff"> <font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <textarea rows="3" name="PurposeClassification" WRAP=OFF cols="45" readonly>
         </textarea>
        &nbsp;&nbsp;<!--a href="../help/multexplan.htm" target="info">?</a--></font></td>
    </tr>
    <tr> 
      <td colspan="2" height="5"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
    </tr>
    <tr> 
      <td Class="Required" bgcolor="ffffff" valign="top"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;<font color="#24309B"><strong>&nbsp;
      9.2 Description</strong></font>&nbsp;&nbsp;</font></td>
      <td bgcolor="ffffff"> <font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;> 
        <textarea rows="3" name="DescriptionClassification" WRAP=OFF cols="45" readonly>
         </textarea>
        </font></td>
    </tr>
    <tr> 
      <td colspan="2" height="5"><font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size:9pt ; line-height:12pt;color:#343434 ;>&nbsp;</font></td>
    </tr>
    <tr> 
      <td Class="Required" bgcolor="ffffff" valign="top"><font color="#24309B" size="2"><strong>&nbsp;&nbsp;
      9.3 Keywords</strong></font></td>
      <td bgcolor="ffffff"> <font style="font-family: "Vernada","±¼¸²Ã¼", "±¼¸²", "µ¸¿òÃ¼"; font-size: 8pt ; line-height:12pt;color:#343434 ; border-width:0px;bord >
        <textarea rows="3" name="KeywordsClassification" WRAP=OFF cols="45" readonly>
         </textarea>
        </font></td>
    </tr>
   <tr>
      <td bgcolor="#333333" colspan="2"></td>
   </tr>
    <tr> 
      <td colspan="2" align='center' height='40'><input type="button" value="Generate XML Meta-data" onClick="MetaMake()" class="btn"> <input type="reset" value="Reset Form" onclick="reset_vars()" class="btn"> 
      </td>
    </tr>
  </table>
</form>
</body>
</html>