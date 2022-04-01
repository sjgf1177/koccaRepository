//**********************************************************
//  1. 제      목: MetaDataMain Operation Bean
//  2. 프로그램명: MetaDataMainBean.java
//  3. 개      요: MetaDataMain관리에 관련된 Bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.22
//  7. 수      정: 박미복 2004. 11.22
//**********************************************************

package com.credu.contents;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.credu.scorm.StringUtil;

public class MetaDataMainBean {

    public MetaDataMainBean() {}

    /**
    METADATA 등록
    @param box 								receive from the form object and session
    @param MetaDataMainData					MetaDataMainData
    @param GeneralCatalogEntryData			generalCatalogEntryData[]
    @param GeneralLanguageData				generalLanguageData[]
    @param GeneralDescriptionData			generalDescriptionData[]
    @param GeneralKeywordData				generalKeywordData[]
    @param GeneralCoverageData				generalCoverageData[]
    @param LifecycleContributeData			lifecycleContributeData[]
    @param LifecycleContributeCentitData	lifecycleContributeCentitData[]
    @param MetaMetaCatalogEntryData		 	metaMetaCatalogEntryData[]
    @param MetaMetaContributeData			metaMetaContributeData[]
    @param MetaMetaContributeCentitData		metaMetaContributeCentitData[]
    @param MetaMetaMetaDataSchemeData		metaMetaMetaDataSchemeData[]
    @param TechnicalRequirementData			technicalRequirementData[]
    @param EducationalIntendedUserRolData	educationalIntendedUserRolData[]
    @param EducationalLanguageData			educationalLanguageData[]
    @param RelationData						relationData[]
    @param AnnotationData					annotationData[]
    @param ClassificationData				classificationData[]
    @return isOk    1:update success,0:update fail
    **/
    public int insertObject(RequestBox box, MetaDataMainData data, GeneralCatalogEntryData[] generalCatalogEntryData, 
			GeneralLanguageData[] generalLanguageData, GeneralDescriptionData[] generalDescriptionData, 
			GeneralKeywordData[] generalKeywordData, GeneralCoverageData[] generalCoverageData, 
			LifecycleContributeData[] lifecycleContributeData, LifecycleContributeCentitData[] lifecycleContributeCentitData, 
			MetaMetaCatalogEntryData[] metaMetaCatalogEntryData, MetaMetaContributeData[] metaMetaContributeData, 
			MetaMetaContributeCentitData[] metaMetaContributeCentitData, 
			MetaMetaMetaDataSchemeData[] metaMetaMetaDataSchemeData, TechnicalRequirementData[] technicalRequirementData, 
			EducationalIntendedUserRolData[] educationalIntendedUserRolData, 
			EducationalLanguageData[] educationalLanguageData, RelationData[] relationData, AnnotationData[] annotationData, 
			ClassificationData[] classificationData) 
		throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        int isOk = 0;
        String results = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "insert into tz_met_m "
                + "(metadata_idx, oid, general_title, general_structure, general_aggregationlevel, lifecycle_version, "
                + " lifecycle_status, metameta_language, technical_size, technical_installationremarks, "
                + " technical_otherrequirements, technical_duration, educational_interactivitytype, " 
                + " educational_interactivitylevel, educational_semanticdensity, educational_difficulty, "
                + " educational_learningtime, educational_description, rights_cost, rights_copyrightrestrictions, "
                + " rights_description, "
                + "t_format, t_location, t_locationtype, "
                + "e_learningtype, "
                + "e_context, e_typicalagerange) "
                + " values "
                + "(" + data.getMetadata_idx()
                + "," + StringManager.makeSQL(box.getString("p_oid"))
                + "," + StringManager.makeSQL(data.getGeneral_title())
                + "," + StringManager.makeSQL(data.getGeneral_structure())
                + "," + StringManager.makeSQL(data.getGeneral_aggregationlevel())
                + "," + StringManager.makeSQL(data.getLifecycle_version())
                + "," + StringManager.makeSQL(data.getLifecycle_status())
                + "," + StringManager.makeSQL(data.getMetameta_language())
                + "," + StringManager.makeSQL(data.getTechnical_size())
                + "," + StringManager.makeSQL(data.getTechnical_installationremarks())
                + "," + StringManager.makeSQL(data.getTechnical_otherrequirements())
                + "," + StringManager.makeSQL(data.getTechnical_duration())
                + "," + StringManager.makeSQL(data.getEducational_interactivitytype())
                + "," + StringManager.makeSQL(data.getEducational_interactivitylevel())
                + "," + StringManager.makeSQL(data.getEducational_semanticdensity())
                + "," + StringManager.makeSQL(data.getEducational_difficulty())
                + "," + StringManager.makeSQL(data.getEducational_learningtime())
                + "," + StringManager.makeSQL(data.getEducational_description())
                + "," + StringManager.makeSQL(data.getRights_cost())
                + "," + StringManager.makeSQL(data.getRights_copyrightrestrictions())
                + "," + StringManager.makeSQL(data.getRights_description())
                + "," + StringManager.makeSQL(data.getT_format())
                + "," + StringManager.makeSQL(data.getT_location())
                + "," + StringManager.makeSQL(data.getT_locationtype())
                + "," + StringManager.makeSQL(data.getE_learningtype())
                + "," + StringManager.makeSQL(data.getE_context())
                + "," + StringManager.makeSQL(data.getE_typicalagerange())
                + ")";
//System.out.println("=======================> sql : " + sql);
            isOk = connMgr.executeUpdate(sql);

            if ( isOk != 1 ) {
              	connMgr.rollback();
				results = "TZ_MET_M에 입력하는 중 에러가 발생하였습니다.";
				throw new Exception();
			}

			if ( generalCatalogEntryData != null ) {
				for ( int i=0; i<generalCatalogEntryData.length; i++ ) {
					if ( !"".equals(generalCatalogEntryData[i].getEntry()) && !"".equals(generalCatalogEntryData[i].getCatalog()) ) {
						sql = "insert into tz_gen_cat "
	        		    	    + "(general_catalog_idx, catalog, entry, metadata_idx)"
		        	        	+ " values "
			    	            + "(GEN_CAT_SEQ.NEXTVAL"
	        			        + "," + StringManager.makeSQL(generalCatalogEntryData[i].getCatalog())
	            		    	+ "," + StringManager.makeSQL(generalCatalogEntryData[i].getEntry())
		        	    	    + "," + data.getMetadata_idx()
	        		        	+ ")";
			    	    isOk = connMgr.executeUpdate(sql);

			    	   	if ( isOk != 1 ) {
	            	   		connMgr.rollback();
							results = "TZ_GEN_CAT에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
					}
				}
			}

			if ( generalLanguageData != null ) {
				for ( int i=0; i<generalLanguageData.length; i++ ) {
					if ( !"".equals(generalLanguageData[i].getLanguage()) ) {
						sql = "insert into tz_gen_lan "
		        	        + "(general_language_idx, language, metadata_idx)"
		                	+ " values "
				            + "(GEN_LAN_SEQ.NEXTVAL"
		    	    	    + "," + StringManager.makeSQL(generalLanguageData[i].getLanguage())
			    	        + "," + data.getMetadata_idx()
		        		    + ")";
			        	isOk = connMgr.executeUpdate(sql);

			       		if ( isOk != 1 ) {
		               		connMgr.rollback();
							results = "TZ_GEN_LAN에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
					}
				}
			}

			if ( generalDescriptionData != null ) {
				for ( int i=0; i<generalDescriptionData.length; i++ ) {
					if ( !"".equals(generalDescriptionData[i].getDescription()) ) {
						sql = "insert into tz_gen_des "
	        	    	    + "(general_description_idx, description, metadata_idx)"
	                		+ " values "
			                + "(GEN_DES_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(generalDescriptionData[i].getDescription())
		    	            + "," + data.getMetadata_idx()
	        		        + ")";
			        	isOk = connMgr.executeUpdate(sql);

				       	if ( isOk != 1 ) {
		               		connMgr.rollback();
							results = "TZ_GEN_DES에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
					}
				}
			}

			if ( generalKeywordData != null ) {
				for ( int i=0; i<generalKeywordData.length; i++ ) {
					if ( !"".equals(generalKeywordData[i].getKeyword()) ) {
						sql = "insert into tz_gen_key "
	        	    	    + "(general_keyword_idx, keyword, metadata_idx)"
	                		+ " values "
			                + "(GEN_KEY_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(generalKeywordData[i].getKeyword())
		    	            + "," + data.getMetadata_idx()
	        		        + ")";
			        	isOk = connMgr.executeUpdate(sql);

				       	if ( isOk != 1 ) {
	        	       		connMgr.rollback();
							results = "TZ_GEN_KEY에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
					}
				}
			}

			if ( generalCoverageData != null ) {
				for ( int i=0; i<generalCoverageData.length; i++ ) {
					if ( !"".equals(generalCoverageData[i].getCoverage()) ) {
						sql = "insert into tz_gen_cov "
	        	    	    + "(general_coverage_idx, coverage, metadata_idx)"
	                		+ " values "
			                + "(GEN_COV_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(generalCoverageData[i].getCoverage())
		    	            + "," + data.getMetadata_idx()
	        		        + ")";
			        	isOk = connMgr.executeUpdate(sql);

				       	if ( isOk != 1 ) {
	        	       		connMgr.rollback();
							results = "TZ_GEN_COV에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
					}
				}
			}

			if ( lifecycleContributeData != null ) {
			    int idx = 0;
			    int first_idx = 0;
			    String[] foreign_idx = new String[lifecycleContributeData.length];
				for ( int i=0; i<lifecycleContributeData.length; i++ ) {
	  				idx = getNewLifecycleContributeIdx(box);
	  				if ( i == 0 ) first_idx = idx;
	  				foreign_idx[i] = ""+idx;
					sql = "insert into tz_lif_con "
		        	        + "(lifecycle_contribute_idx, lifecycle_contr_role, lifecycle_contr_date, metadata_idx)"
        		        	+ " values "
	            		    + "(" + idx
		        	        + "," + StringManager.makeSQL(lifecycleContributeData[i].getLifecycle_contr_role())
        			        + "," + StringManager.makeSQL(lifecycleContributeData[i].getLifecycle_contr_date())
	            		    + "," + data.getMetadata_idx()
        			        + ")";
		       		isOk = connMgr.executeUpdate(sql);

		   			if ( isOk != 1 ) {
	               		connMgr.rollback();
						results = "TZ_LIF_CON에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				}

				if ( lifecycleContributeCentitData != null ) {
					for ( int j=0; j<lifecycleContributeCentitData.length; j++ ) {
						sql = "insert into tz_lif_cen "
	        			        + "(lifecycle_contr_centity_idx, centity, lifecycle_contribute_idx, metadata_idx)"
	               				+ " values "
				               	+ "(LIF_CEN_SEQ.NEXTVAL"
		    	    	        + "," + StringManager.makeSQL(lifecycleContributeCentitData[j].getCentity())
		                		+ "," + foreign_idx[lifecycleContributeCentitData[j].getLifecycle_contribute_idx()]
		                		+ "," + data.getMetadata_idx()
				       	        + ")";
				        isOk = connMgr.executeUpdate(sql);

			   		   	if ( isOk != 1 ) {
	           				connMgr.rollback();
							results = "TZ_LIF_CEN에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
				    }
				}
			}

			if ( metaMetaCatalogEntryData != null ) {
				for ( int i=0; i<metaMetaCatalogEntryData.length; i++ ) {
					sql = "insert into tz_met_cat "
	        	    	    + "(metameta_catalog_idx, catalog, entry, metadata_idx)"
		                	+ " values "
			                + "(MET_CAT_SEQ.NEXTVAL"
	        		        + "," + StringManager.makeSQL(metaMetaCatalogEntryData[i].getCatalog())
	            	    	+ "," + StringManager.makeSQL(metaMetaCatalogEntryData[i].getEntry())
		            	    + "," + data.getMetadata_idx()
	        	        	+ ")";
			        isOk = connMgr.executeUpdate(sql);

			       	if ( isOk != 1 ) {
	               		connMgr.rollback();
						results = "TZ_MET_CAT에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				}
			}

			if ( metaMetaContributeData != null ) {
			    int idx = 0;
			    int first_idx = 0;
			    String[] foreign_idx = new String[metaMetaContributeData.length];
				for ( int i=0; i<metaMetaContributeData.length; i++ ) {
	  				idx = getNewMetaMetaContributeIdx(box);
	  				if ( i == 0 ) first_idx = idx;
	  				foreign_idx[i] = ""+idx;
					sql = "insert into tz_met_con "
		        	        + "(metameta_contribute_idx, meta_contr_role, meta_contr_date, metadata_idx)"
        		        	+ " values "
	            		    + "(" + idx
		        	        + "," + StringManager.makeSQL(metaMetaContributeData[i].getMeta_contr_role())
        			        + "," + StringManager.makeSQL(metaMetaContributeData[i].getMeta_contr_date())
	            		    + "," + data.getMetadata_idx()
        			        + ")";
		       		isOk = connMgr.executeUpdate(sql);

		   			if ( isOk != 1 ) {
	               		connMgr.rollback();
						results = "TZ_MET_CON에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				}

				if ( metaMetaContributeCentitData != null ) {
					for ( int j=0; j<metaMetaContributeCentitData.length; j++ ) {
						sql = "insert into tz_met_cen "
	        			        + "(metameta_contr_centity_idx, metameta_contribute_idx, centity, metadata_idx)"
	               				+ " values "
				               	+ "(MET_CEN_SEQ.NEXTVAL"
	                    		+ "," + foreign_idx[metaMetaContributeCentitData[j].getMetameta_contribute_idx()]
		    	    	        + "," + StringManager.makeSQL(metaMetaContributeCentitData[j].getCentity())
		                		+ "," + data.getMetadata_idx()
				       	        + ")";
				        isOk = connMgr.executeUpdate(sql);

			   		   	if ( isOk != 1 ) {
	           				connMgr.rollback();
							results = "TZ_MET_CON_CENTIT에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
				    }
				}
			}

			if ( metaMetaMetaDataSchemeData != null ) {
				for ( int i=0; i<metaMetaMetaDataSchemeData.length; i++ ) {
					if ( !"".equals(metaMetaMetaDataSchemeData[i].getMetadata_scheme()) ) {
						sql = "insert into tz_met_sch "
	        	    	    + "(metadata_scheme_idx, metadata_scheme, metadata_idx)"
	                		+ " values "
			                + "(MET_SCH_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(metaMetaMetaDataSchemeData[i].getMetadata_scheme())
		    	            + "," + data.getMetadata_idx()
	        		        + ")";
			        	isOk = connMgr.executeUpdate(sql);

			       		if ( isOk != 1 ) {
	        	       		connMgr.rollback();
							results = "TZ_MET_SCH에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
					}
				}
			}

			if ( technicalRequirementData != null ) {
				for ( int i=0; i<technicalRequirementData.length; i++ ) {
					sql = "insert into tz_tec_req "
	        	    	    + "(technical_requirement_idx, requirement_type, requirement_name, minimum_version, maximum_version, metadata_idx)"
	                		+ " values "
			                + "(TEC_REQ_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(technicalRequirementData[i].getRequirement_type())
	    	    	        + "," + StringManager.makeSQL(technicalRequirementData[i].getRequirement_name())
	    	    	        + "," + StringManager.makeSQL(technicalRequirementData[i].getMinimum_version())
	    	    	        + "," + StringManager.makeSQL(technicalRequirementData[i].getMaximum_version())
		    	            + "," + data.getMetadata_idx()
	        		        + ")";
		        	isOk = connMgr.executeUpdate(sql);

		       		if ( isOk != 1 ) {
	               		connMgr.rollback();
						results = "TZ_TEC_REQ에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				}
			}

			if ( educationalIntendedUserRolData != null ) {
				for ( int i=0; i<educationalIntendedUserRolData.length; i++ ) {
					if ( !"".equals(educationalIntendedUserRolData[i].getIntendedenduserrole()) ) {
						sql = "insert into tz_edu_rol "
	        	    	    + "(educatinal_intenduserrole_idx, intendedenduserrole, metadata_idx)"
	                		+ " values "
			                + "(EDU_ROL_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(educationalIntendedUserRolData[i].getIntendedenduserrole())
		    	            + "," + data.getMetadata_idx()
	        		        + ")";
			        	isOk = connMgr.executeUpdate(sql);

			       		if ( isOk != 1 ) {
	        	       		connMgr.rollback();
							results = "TZ_EDU_ROL에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
					}
				}
			}

			if ( educationalLanguageData != null ) {
				for ( int i=0; i<educationalLanguageData.length; i++ ) {
					if ( !"".equals(educationalLanguageData[i].getLanguage()) ) {
						sql = "insert into tz_edu_lan "
	        	    	    + "(educational_language_idx, language, metadata_idx)"
	                		+ " values "
			                + "(EDU_LAN_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(educationalLanguageData[i].getLanguage())
		    	            + "," + data.getMetadata_idx()
	        		        + ")";
			        	isOk = connMgr.executeUpdate(sql);

			       		if ( isOk != 1 ) {
	        	       		connMgr.rollback();
							results = "TZ_EDU_LAN에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
					}
				}
			}

			if ( relationData != null ) {
				for ( int i=0; i<relationData.length; i++ ) {
					sql = "insert into tz_relat "
	        	    	    + "(relation_idx, relation_kind, relation_resource, relation_description, metadata_idx)"
	                		+ " values "
			                + "(RELAT_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(relationData[i].getRelation_kind())
	    	    	        + "," + StringManager.makeSQL(relationData[i].getRelation_resource())
	    	    	        + "," + StringManager.makeSQL(relationData[i].getRelation_description())
		    	            + "," + data.getMetadata_idx()
	        		        + ")";
		        	isOk = connMgr.executeUpdate(sql);

		       		if ( isOk != 1 ) {
	               		connMgr.rollback();
						results = "TZ_RELAT에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				}
			}

			if ( annotationData != null ) {
				for ( int i=0; i<annotationData.length; i++ ) {
					sql = "insert into tz_annot "
	        	    	    + "(annotation_idx, person, annotation_date, description, metadata_idx)"
	                		+ " values "
			                + "(ANNOT_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(annotationData[i].getPerson())
	    	    	        + "," + StringManager.makeSQL(annotationData[i].getAnnotation_date())
	    	    	        + "," + StringManager.makeSQL(annotationData[i].getDescription())
		    	            + "," + data.getMetadata_idx()
	        		        + ")";
		        	isOk = connMgr.executeUpdate(sql);

		       		if ( isOk != 1 ) {
	               		connMgr.rollback();
						results = "TZ_ANNOT에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				}
			}

			if ( classificationData != null ) {
				for ( int i=0; i<classificationData.length; i++ ) {
					sql = "insert into tz_classif "
	        	    	    + "(classification_idx, purpose, description, keyword, metadata_idx)"
	                		+ " values "
			                + "(CLASSIF_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(classificationData[i].getPurpose())
	    	    	        + "," + StringManager.makeSQL(classificationData[i].getDescription())
	    	    	        + "," + StringManager.makeSQL(classificationData[i].getKeyword())
		    	            + "," + data.getMetadata_idx()
	        		        + ")";
		        	isOk = connMgr.executeUpdate(sql);

		       		if ( isOk != 1 ) {
	               		connMgr.rollback();
						results = "TZ_CLASSIF에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				}
			}
			if (isOk > 0) {connMgr.commit();}
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("results = " + results + "\r\n" +"sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    /**
    empty METADATA 등록
    @param box 		receive from the form object and session
    @return isOk    1:update success,0:update fail
    **/
    public int insertEmptyObject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        int isOk = 0;
        String results = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "insert into tz_met_m "
                + "(metadata_idx, oid) "
                + " values "
                + "(" + getNewIdx(box)
                + "," + StringManager.makeSQL(box.getString("p_oid"))
                + ")";
//System.out.println("=======================> sql : " + sql);
            isOk = connMgr.executeUpdate(sql);

            if ( isOk != 1 ) {
              	connMgr.rollback();
				results = "TZ_MET_M에 입력하는 중 에러가 발생하였습니다.";
				throw new Exception();
			}if (isOk > 0) {connMgr.commit();}
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("results = " + results + "\r\n" +"sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

   /**
    get New Idx
    @param box    receive from the form object and session
    @return int   new idx
    */  
    public int getNewIdx(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
		int newidx = 0;

        try {
            connMgr = new DBConnectionManager();

            sql = "select MET_M_SEQ.NEXTVAL nextval from dual";

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
				newidx = ls.getInt("nextval");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return newidx;
    }

   /**
    get New Lifecycle_Contribute Idx
    @param box          receive from the form object and session
    @return int   new idx
    */  
    public int getNewLifecycleContributeIdx(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
		int newidx = 0;

        try {
            connMgr = new DBConnectionManager();

            sql = "select LIF_CON_SEQ.NEXTVAL nextval from dual";

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
				newidx = ls.getInt("nextval");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return newidx;
    }

   /**
    get New LifecycleContribute Idx
    @param box          receive from the form object and session
    @return boolean   exist or not exist
    */  
    public boolean getMetaDataByOid(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
		boolean exist = false;
		int count = 0;
		String p_oid = box.getString("p_oid");

        try {
            connMgr = new DBConnectionManager();

            sql = "select count(oid) count from tz_met_m where oid = '"+p_oid+"'";
System.out.println("========>sql : " + sql);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
				count = ls.getInt("count");
            }
            if ( count > 0 ) exist = true;

			System.out.println("========>exist : " + exist);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return exist;
    }

   /**
    get MetaDataMainData by oid
    @param box receive from the form object and session
    @return MetaDataMainData
    */  
    public MetaDataMainData getMetaDataMainDataByOid(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
		MetaDataMainData data = null;
		String p_oid = box.getString("p_oid");
		
        try {
            connMgr = new DBConnectionManager();

            sql = "select METADATA_IDX, OID, GENERAL_TITLE, GENERAL_STRUCTURE, GENERAL_AGGREGATIONLEVEL, "
				+ " LIFECYCLE_VERSION, LIFECYCLE_STATUS, METAMETA_LANGUAGE, TECHNICAL_SIZE, TECHNICAL_INSTALLATIONREMARKS," 
				+ " TECHNICAL_OTHERREQUIREMENTS, TECHNICAL_DURATION, EDUCATIONAL_INTERACTIVITYTYPE, " 
				+ " EDUCATIONAL_INTERACTIVITYLEVEL, EDUCATIONAL_SEMANTICDENSITY, EDUCATIONAL_DIFFICULTY, "
				+ " EDUCATIONAL_LEARNINGTIME, EDUCATIONAL_DESCRIPTION, RIGHTS_COST, RIGHTS_COPYRIGHTRESTRICTIONS, "
				+ " RIGHTS_DESCRIPTION, T_FORMAT, T_LOCATION, T_LOCATIONTYPE, E_LEARNINGTYPE, E_CONTEXT, E_TYPICALAGERANGE "
            	+ " from tz_met_m "
            	+ " where oid = '" + p_oid + "'";
//System.out.println("========>sql : " + sql);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
            	data = new MetaDataMainData();
                data.setMetadata_idx						(ls.getInt("metadata_idx"));
                data.setOid									(ls.getString("oid"));
                data.setGeneral_title						(ls.getString("general_title"));
                data.setGeneral_structure					(ls.getString("general_structure"));
                data.setGeneral_aggregationlevel			(ls.getString("general_aggregationlevel"));
                data.setLifecycle_version					(ls.getString("lifecycle_version"));
                data.setLifecycle_status					(ls.getString("lifecycle_status"));
                data.setMetameta_language					(ls.getString("metameta_language"));
                data.setTechnical_size						(ls.getString("technical_size"));
                data.setTechnical_installationremarks		(ls.getString("technical_installationremarks"));
                data.setTechnical_otherrequirements			(ls.getString("technical_otherrequirements"));
                data.setTechnical_duration					(ls.getString("technical_duration"));
                data.setEducational_interactivitytype		(ls.getString("educational_interactivitytype"));
                data.setEducational_interactivitylevel		(ls.getString("educational_interactivitylevel"));
                data.setEducational_semanticdensity			(ls.getString("educational_semanticdensity"));
                data.setEducational_difficulty				(ls.getString("educational_difficulty"));
                data.setEducational_learningtime			(ls.getString("educational_learningtime"));
                data.setEducational_description				(ls.getString("educational_description"));
                data.setRights_cost							(ls.getString("rights_cost"));
                data.setRights_copyrightrestrictions		(ls.getString("rights_copyrightrestrictions"));
                data.setRights_description					(ls.getString("rights_description"));
                data.setT_format							(ls.getString("t_format"));
                data.setT_location							(ls.getString("t_location"));
                data.setT_locationtype						(ls.getString("t_locationtype"));
                data.setE_learningtype						(ls.getString("e_learningtype"));
                data.setE_context							(ls.getString("e_context"));
                data.setE_typicalagerange					(ls.getString("e_typicalagerange"));
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return data;
    }

   /**
    get MetaDataMainData by idx
    @param box receive from the form object and session
    @return MetaDataMainData
    */
    public MetaDataMainData getMetaDataMainData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
		MetaDataMainData data = null;
		String p_metadata_idx = box.getString("p_metadata_idx");
		
        try {
            connMgr = new DBConnectionManager();

            sql = "select METADATA_IDX, OID, GENERAL_TITLE, GENERAL_STRUCTURE, GENERAL_AGGREGATIONLEVEL, LIFECYCLE_VERSION, LIFECYCLE_STATUS, METAMETA_LANGUAGE, TECHNICAL_SIZE, TECHNICAL_INSTALLATIONREMARKS, TECHNICAL_OTHERREQUIREMENTS, TECHNICAL_DURATION, EDUCATIONAL_INTERACTIVITYTYPE, EDUCATIONAL_INTERACTIVITYLEVEL, EDUCATIONAL_SEMANTICDENSITY, EDUCATIONAL_DIFFICULTY, EDUCATIONAL_LEARNINGTIME, EDUCATIONAL_DESCRIPTION, RIGHTS_COST, RIGHTS_COPYRIGHTRESTRICTIONS, RIGHTS_DESCRIPTION, T_FORMAT, T_LOCATION, T_LOCATIONTYPE, E_LEARNINGTYPE, E_CONTEXT, E_TYPICALAGERANGE "
            	+ " from tz_met_m "
            	+ " where metadata_idx = " + p_metadata_idx;
//System.out.println("========>sql : " + sql);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
            	data = new MetaDataMainData();
                data.setMetadata_idx						(ls.getInt("metadata_idx"));
                data.setOid									(ls.getString("oid"));
                data.setGeneral_title						(ls.getString("general_title"));
                data.setGeneral_structure					(ls.getString("general_structure"));
                data.setGeneral_aggregationlevel			(ls.getString("general_aggregationlevel"));
                data.setLifecycle_version					(ls.getString("lifecycle_version"));
                data.setLifecycle_status					(ls.getString("lifecycle_status"));
                data.setMetameta_language					(ls.getString("metameta_language"));
                data.setTechnical_size						(ls.getString("technical_size"));
                data.setTechnical_installationremarks		(ls.getString("technical_installationremarks"));
                data.setTechnical_otherrequirements			(ls.getString("technical_otherrequirements"));
                data.setTechnical_duration					(ls.getString("technical_duration"));
                data.setEducational_interactivitytype		(ls.getString("educational_interactivitytype"));
                data.setEducational_interactivitylevel		(ls.getString("educational_interactivitylevel"));
                data.setEducational_semanticdensity			(ls.getString("educational_semanticdensity"));
                data.setEducational_difficulty				(ls.getString("educational_difficulty"));
                data.setEducational_learningtime			(ls.getString("educational_learningtime"));
                data.setEducational_description				(ls.getString("educational_description"));
                data.setRights_cost							(ls.getString("rights_cost"));
                data.setRights_copyrightrestrictions		(ls.getString("rights_copyrightrestrictions"));
                data.setRights_description					(ls.getString("rights_description"));
                data.setT_format							(ls.getString("t_format"));
                data.setT_location							(ls.getString("t_location"));
                data.setT_locationtype						(ls.getString("t_locationtype"));
                data.setE_learningtype						(ls.getString("e_learningtype"));
                data.setE_context							(ls.getString("e_context"));
                data.setE_typicalagerange					(ls.getString("e_typicalagerange"));
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return data;
    }

   /**
    get New MetaMeta_Contribute Idx
    @param box receive from the form object and session
    @return int   new idx
    */  
    public int getNewMetaMetaContributeIdx(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
		int newidx = 0;

        try {
            connMgr = new DBConnectionManager();

            sql = "select MET_CON_SEQ.NEXTVAL nextval from dual";

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
				newidx = ls.getInt("nextval");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return newidx;
    }

	/**
	XML Parsing ==> read XML write DB
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void readXMLwriteDB(RequestBox box, PrintWriter out) throws Exception {
		try {
			// xml파일
			String v_metalocation = box.getString("p_metalocation");
			String v_scolocate = box.getString("p_scolocate");
			ConfigSet conf = new ConfigSet();
			String store = "";
			String store1 = "";
			String store2 = "";
			String store3 = "";

			// 저장해야할 데이타들
			MetaDataMainData mainData = new MetaDataMainData();

			DOMBuilder builder = new DOMBuilder(false);
			Document document = builder.build(new File(conf.getProperty("dir.scoobjectpath")+v_scolocate+"/"+v_metalocation));
			Element rootEmt = document.getRootElement();
			if ( rootEmt == null ) {
				throw new Exception("XML문서에 문제가 있습니다.");
			}

			// 9개의 스키마 - 1 level 시작
			List firstChildsEmts = rootEmt.getChildren();
			Element general = null;				// 1 and only 1
			Element lifecycle = null;			// 0 or 1
			Element metametadata = null;		// 1 and only 1
			Element technical = null;			// 1 and only 1
			Element educational = null;			// 0 or 1
			Element rights = null;				// 1 and only 1
			Element[] relation = null;			// 0 or more
			Element[] annotation = null;		// 0 or more
			Element[] classification = null;	// 0 or more

			int r = 0;
			int a = 0;
			int c = 0;
			int rr = 0;
			int aa = 0;
			int cc = 0;

			for (int i=0;i<firstChildsEmts.size();i++) {
				if ( ((Element)firstChildsEmts.get(i)).getName().equals("relation") ) {
					r++;
				} else if ( ((Element)firstChildsEmts.get(i)).getName().equals("annotation") ) {
					a++;
				} else if ( ((Element)firstChildsEmts.get(i)).getName().equals("classification") ) {
					c++;
				}
			}

			if ( r > 0 ) relation = new Element[r];
			if ( a > 0 ) annotation = new Element[a];
			if ( c > 0 ) classification = new Element[c];

			for (int i=0;i<firstChildsEmts.size();i++) {
				//System.out.println("firstChildsEmts.get("+i+") : "+firstChildsEmts.get(i));
				if ( ((Element)firstChildsEmts.get(i)).getName().equals("general") ) {
				    general = (Element)firstChildsEmts.get(i);
				} else if ( ((Element)firstChildsEmts.get(i)).getName().equals("lifecycle") ) {
				    lifecycle = (Element)firstChildsEmts.get(i);
				} else if ( ((Element)firstChildsEmts.get(i)).getName().equals("metametadata") ) {
				    metametadata = (Element)firstChildsEmts.get(i);
				} else if ( ((Element)firstChildsEmts.get(i)).getName().equals("technical") ) {
				    technical = (Element)firstChildsEmts.get(i);
				} else if ( ((Element)firstChildsEmts.get(i)).getName().equals("educational") ) {
				    educational = (Element)firstChildsEmts.get(i);
				} else if ( ((Element)firstChildsEmts.get(i)).getName().equals("rights") ) {
				    rights = (Element)firstChildsEmts.get(i);
				} else if ( ((Element)firstChildsEmts.get(i)).getName().equals("relation") ) {
				    relation[rr] = (Element)firstChildsEmts.get(i);
				    rr++;
				} else if ( ((Element)firstChildsEmts.get(i)).getName().equals("annotation") ) {
				    annotation[aa] = (Element)firstChildsEmts.get(i);
				    aa++;
				} else if ( ((Element)firstChildsEmts.get(i)).getName().equals("classification") ) {
				    classification[cc] = (Element)firstChildsEmts.get(i);
				    cc++;
				}
			}
			// 9개의 스키마 - 1 level 끝

			// 1. general - 2 level 시작
/*			if ( general == null ) {
				throw new Exception("xml문서에 general section이 존재하지 않습니다. 1 and only 1");
			}
*/
			GeneralCatalogEntryData[] generalCatalogEntryData = null;
			GeneralLanguageData[] generalLanguageData = null;
			GeneralDescriptionData[] generalDescriptionData = null;
			GeneralKeywordData[] generalKeywordData = null;
			GeneralCoverageData[] generalCoverageData = null;

			if ( general != null ) {
				List generalElements = general.getChildren();

				Element identifier = null;			// reserved
				Element title = null;				// 1 and only 1
				Element[] catalogentry = null;		// 0 or more
				Element[] language = null;			// 0 or more
				Element[] description = null;		// 1 or more
				Element[] keyword = null;			// 0 or more
				Element[] coverage = null;			// 0 or more
				Element structure = null;			// 0 or 1
				Element aggregationlevel = null;	// 0 or 1

				int c1 = 0;
				int l = 0;
				int d = 0;
				int k = 0;
				int c2 = 0;
				int cc1 = 0;
				int ll = 0;
				int dd = 0;
				int kk = 0;
				int cc2 = 0;

				for (int i=0;i<generalElements.size();i++) {
					if ( ((Element)generalElements.get(i)).getName().equals("catalogentry") ) {
						c1++;
					} else if ( ((Element)generalElements.get(i)).getName().equals("language") ) {
						l++;
					} else if ( ((Element)generalElements.get(i)).getName().equals("description") ) {
						d++;
					} else if ( ((Element)generalElements.get(i)).getName().equals("keyword") ) {
						k++;
					} else if ( ((Element)generalElements.get(i)).getName().equals("coverage") ) {
						c2++;
					}
				}

				if ( c1 > 0 ) catalogentry = new Element[c1];
				if ( l > 0 ) language = new Element[l];
				if ( d > 0 ) description = new Element[d];
				if ( k > 0 ) keyword = new Element[k];
				if ( c2 > 0 ) coverage = new Element[c2];

				for (int i=0;i<generalElements.size();i++) {
					//System.out.println("generalElements.get("+i+") : "+generalElements.get(i));
					if ( ((Element)generalElements.get(i)).getName().equals("identifier") ) {
					    identifier = (Element)generalElements.get(i);
					} else if ( ((Element)generalElements.get(i)).getName().equals("title") ) {
					    title = (Element)generalElements.get(i);
					} else if ( ((Element)generalElements.get(i)).getName().equals("catalogentry") ) {
					    catalogentry[cc1] = (Element)generalElements.get(i);
					    cc1++;
					} else if ( ((Element)generalElements.get(i)).getName().equals("language") ) {
					    language[ll] = (Element)generalElements.get(i);
					    ll++;
					} else if ( ((Element)generalElements.get(i)).getName().equals("description") ) {
					    description[dd] = (Element)generalElements.get(i);
					    dd++;
					} else if ( ((Element)generalElements.get(i)).getName().equals("keyword") ) {
					    keyword[kk] = (Element)generalElements.get(i);
					    kk++;
					} else if ( ((Element)generalElements.get(i)).getName().equals("coverage") ) {
					    coverage[cc2] = (Element)generalElements.get(i);
					    cc2++;
					} else if ( ((Element)generalElements.get(i)).getName().equals("structure") ) {
					    structure = (Element)generalElements.get(i);
					} else if ( ((Element)generalElements.get(i)).getName().equals("aggregationlevel") ) {
					    aggregationlevel = (Element)generalElements.get(i);
					}
				}

	            // 1.2 general - title ( 1 and only 1 )
	            if ( title != null ) {
	            	store = getLangstring(title);
	            	if ( StringUtil.getBytesCount(store) <= 200 ) {
						mainData.setGeneral_title(store);
						//System.out.println("1.2 general title : " + mainData.getGeneral_title());
					}
				}

				// 1.3 general - catalog entry ( 0 or more )
				if ( catalogentry != null ) {
					generalCatalogEntryData = new GeneralCatalogEntryData[catalogentry.length];

					for ( int i=0 ; i < catalogentry.length; i++ ) {
						generalCatalogEntryData[i] = new GeneralCatalogEntryData();
						List catalogentryElements = null;

						if ( catalogentry[i] != null ) {
							catalogentryElements = catalogentry[i].getChildren();
							Element catalog = null;
							Element entry = null;
							for (int j=0;j<catalogentryElements.size();j++) {
								if ( ((Element)catalogentryElements.get(j)).getName().equals("catalog") ) {
								    catalog = (Element)catalogentryElements.get(j);
					            	store = catalog.getText();
	   	        					if ( StringUtil.getBytesCount(store) <= 255 ) {
								    	generalCatalogEntryData[i].setCatalog(store);
								    	//System.out.println("generalCatalogEntryData["+i+"].catalog() : " + generalCatalogEntryData[i].getCatalog());
								    }
								} else if ( ((Element)catalogentryElements.get(j)).getName().equals("entry") ) {
								    entry = (Element)catalogentryElements.get(j);
					            	store = getLangstring(entry);
	   	        					if ( StringUtil.getBytesCount(store) <= 255 ) {
									    generalCatalogEntryData[i].setEntry(store);
									    //System.out.println("generalCatalogEntryData["+i+"].entry() : " + generalCatalogEntryData[i].getEntry());
									}
								}
							}
						}
						//System.out.println("1.3 general catalogentry{catalog:" + generalCatalogEntryData[i].getCatalog() +", entry:"+generalCatalogEntryData[i].getEntry()+"}");
					}
				}

				// 1.4 language ( 0 or more )
				if ( language != null ) {
					generalLanguageData = new GeneralLanguageData[language.length];

					for ( int i=0 ; i < language.length; i++ ) {
						generalLanguageData[i] = new GeneralLanguageData();

						if ( language[i] != null ) {
			            	store = language[i].getText();
	       					if ( StringUtil.getBytesCount(store) <= 10 ) {
								generalLanguageData[i].setLanguage(store);
								//System.out.println("1.4 general language : " + generalLanguageData[i].getLanguage());
							}
						}
					}
				}

				// 1.5 description ( 1 or more )
				if ( description != null ) {
					generalDescriptionData = new GeneralDescriptionData[description.length];

					for ( int i=0 ; i < description.length; i++ ) {
						generalDescriptionData[i] = new GeneralDescriptionData();

						if ( description[i] != null ) {
			            	store = getLangstring(description[i]);
	       					if ( StringUtil.getBytesCount(store) <= 255 ) {
								generalDescriptionData[i].setDescription(store);
							}
						}
						//System.out.println("1.5 general description : " + generalDescriptionData[i].getDescription());
					}
				}

				// 1.6 keyword ( 0 or more )
				if ( keyword != null ) {
					generalKeywordData = new GeneralKeywordData[keyword.length];

					for ( int i=0 ; i < keyword.length; i++ ) {
						generalKeywordData[i] = new GeneralKeywordData();

						if ( keyword[i] != null ) {
			            	store = getLangstring(keyword[i]);
	       					if ( StringUtil.getBytesCount(store) <= 255 ) {
								generalKeywordData[i].setKeyword(store);
							}
						}
						//System.out.println("1.6 general keyword : " + generalKeywordData[i].getKeyword());
					}
				}

				// 1.7 coverage ( 0 or more )
				if ( coverage != null ) {
					generalCoverageData = new GeneralCoverageData[coverage.length];

					for ( int i=0 ; i < coverage.length; i++ ) {
						generalCoverageData[i] = new GeneralCoverageData();

						if ( coverage[i] != null ) {
			            	store = getLangstring(coverage[i]);
	       					if ( StringUtil.getBytesCount(store) <= 255 ) {
								generalCoverageData[i].setCoverage(store);
							}
						}
						//System.out.println("1.7 general coverage : " + generalCoverageData[i].getCoverage());
					}
				}

				// 1.8 general - structure ( 0 or 1 )
				if ( structure != null ) {
	            	store = getValue(structure);
	            	if ( StringUtil.getBytesCount(store) <= 20 ) {
						mainData.setGeneral_structure(store);
						//System.out.println("1.8 general structure : " + mainData.getGeneral_structure());
					}
				}

				// 1.9 general - aggregationlevel ( 0 or 1 )
				if ( aggregationlevel != null ) {
	            	store = getValue(aggregationlevel);
	            	if ( StringUtil.getBytesCount(store) <= 2 ) {
						mainData.setGeneral_aggregationlevel(store);
						//System.out.println("1.9 general aggregationlevel : " + mainData.getGeneral_aggregationlevel());
					}
				}
			}

			//2. lifecycle - 2 level 시작
			LifecycleContributeData[] lifecycleContributeData = null;
			LifecycleContributeCentitData[] lifecycleContributeCentitData = null;
			int z=0;
			int zz=0;
			int yy=0;

			if ( lifecycle != null ) {
			    List lifecycleElements = lifecycle.getChildren();
			    Element version = null;			// 0 or 1
			    Element status = null;			// 0 or 1
			    Element[] contribute = null;	// 0 or more

			    int c3 = 0;
			    int cc3 = 0;

			    for (int i=0;i<lifecycleElements.size();i++) {
					if ( ((Element)lifecycleElements.get(i)).getName().equals("contribute") )
				    	c3++;
			    }

			    if ( c3 > 0 ) contribute = new Element[c3];

			    for (int i=0;i<lifecycleElements.size();i++) {
					if ( ((Element)lifecycleElements.get(i)).getName().equals("version") ) {
		    			version = (Element)lifecycleElements.get(i);
					} else if ( ((Element)lifecycleElements.get(i)).getName().equals("status") ) {
		    			status = (Element)lifecycleElements.get(i);
					} else if ( ((Element)lifecycleElements.get(i)).getName().equals("contribute") ) {
					    contribute[cc3] = (Element)lifecycleElements.get(i);
					    cc3++;
					}
			    }

			    // 2.1 lifecycle - version ( 0 or 1 )
			    if ( version != null ) {
	            	store = getLangstring(version);
    	        	if ( StringUtil.getBytesCount(store) <= 200 ) {
					    mainData.setLifecycle_version(store);
					    //System.out.println("2.1 lifecycle version : " + mainData.getLifecycle_version());
					}
				}

			    // 2.2 lifecycle - status ( 0 or 1 )
			    if ( status != null ) {
	            	store = getValue(status);
    	        	if ( StringUtil.getBytesCount(store) <= 15 ) {
					    mainData.setLifecycle_status(store);
					    //System.out.println("2.2 lifecycle status : " + mainData.getLifecycle_status());
					}
				}

			    // 2.3.1 lifecycle - contribute ( 0 or more )
			    lifecycleContributeData = new LifecycleContributeData[c3];
			    if ( contribute != null ) {
					for ( int i=0; i<contribute.length; i++ ) {
				    	lifecycleContributeData[i] = new LifecycleContributeData();
				    	List contributeElements = contribute[i].getChildren();
				    	Element role = null;
			    		Element date = null;

				    	for (int j=0;j<contributeElements.size();j++) {
					    	if ( ((Element)contributeElements.get(j)).getName().equals("centity") ) {
					        	z++;
					        }
			    	    }

			        	for (int j=0;j<contributeElements.size();j++) {
					    	if ( ((Element)contributeElements.get(j)).getName().equals("role") ) {
					        	role = (Element)contributeElements.get(j);
				            	store = getValue(role);
       							if ( StringUtil.getBytesCount(store) <= 50 ) {
					        		lifecycleContributeData[i].setLifecycle_contr_role(store);
					        	}
						    } else if ( ((Element)contributeElements.get(j)).getName().equals("date") ) {
   						        date = (Element)contributeElements.get(j);
				            	store = getDatetime(date);
       							if ( StringUtil.getBytesCount(store) <= 50 ) {
	   				    		    lifecycleContributeData[i].setLifecycle_contr_date(store);
	   				    		}
						    }
			        	}
				        //System.out.println("2.3 lifecycle contribute {role:" + lifecycleContributeData[i].getLifecycle_contr_role() +", date:"+lifecycleContributeData[i].getLifecycle_contr_date()+"}");
		    		}

					if ( z > 0 ) {
				    	lifecycleContributeCentitData = new LifecycleContributeCentitData[z];

						for ( int i=0; i<contribute.length; i++ ) {
							List contributeElements = contribute[i].getChildren();
					    	Element[] centity = null;
					    	int c4 = 0;
					    	int cc4 = 0;

				        	for (int j=0;j<contributeElements.size();j++) {
								if ( ((Element)contributeElements.get(j)).getName().equals("centity") ) {
							        lifecycleContributeCentitData[zz] = new LifecycleContributeCentitData();
							        zz++; c4++;
							    }
				        	}

				        	if ( c4 > 0 ) centity = new Element[c4];

				        	for ( int j=0;j<contributeElements.size();j++) {
									if ( ((Element)contributeElements.get(j)).getName().equals("centity") ) {
							        centity[cc4] = (Element)contributeElements.get(j);
					        		cc4++;
							    }
				        	}

				        	if ( centity != null ) {
			    		        for ( int j=0; j<centity.length; j++ ) {
				    	        	List centityElements = centity[j].getChildren();
				            		Element[] vcard = null;
				            		int v = 0;
				    	        	int vv = 0;

				        	    	for ( int m=0; m<centityElements.size(); m++) {
				            		    if ( ((Element)centityElements.get(m)).getName().equals("vcard") )
				            		        v++;
					            	}

				    	        	if ( v > 0 ) vcard = new Element[v];
					        	    for ( int m=0; m<centityElements.size(); m++ ) {
				            		    if ( ((Element)centityElements.get(m)).getName().equals("vcard") ) {
			    	        			    vcard[vv] = (Element)centityElements.get(m);
							            	store = vcard[vv].getText();
       										if ( StringUtil.getBytesCount(store) <= 3000 ) {
			        		    	    	    lifecycleContributeCentitData[yy].setCentity(store);
			        		    	    	}
			        	    	    	    lifecycleContributeCentitData[yy].setLifecycle_contribute_idx(i);
				            		        //System.out.println("2.3.2 lifecycleContributeCentitData["+yy+"]=>" + lifecycleContributeCentitData[yy].getCentity());

//System.out.println("lifecycleContributeCentitData["+yy+"].getLifecycle_contribute_idx=>" + lifecycleContributeCentitData[yy].getLifecycle_contribute_idx());
				            		        vv++;yy++;
				            	    	}
					            	}
				    	        }
				        	}
			    		}
					}
				}
			}

			// 3. metametadata - 2 level 시작
/*			if ( metametadata == null ) {
				throw new Exception("xml문서에 metametadata section이 존재하지 않습니다. 1 and only 1");
			}
*/
			MetaMetaContributeData[] metaMetaContributeData = null;
			MetaMetaContributeCentitData[] metaMetaContributeCentitData = null;
			MetaMetaCatalogEntryData[] metaMetaCatalogEntryData = null;
			MetaMetaMetaDataSchemeData[] metaMetaMetaDataSchemeData = null;

			if ( metametadata != null ) {
				int s3_z=0;
				int s3_zz=0;
				int s3_yy=0;

				List metametadataElements = metametadata.getChildren();

				Element[] s3_catalogentry = null;		// 0 or more
			    Element[] contribute = null;			// 0 or more
			    Element[] metadatascheme = null;		// 1 or more
				Element s3_language = null;				// 0 or more

				int s3_c1 = 0;
				int s3_cc1 = 0;
				int s3_c3 = 0;
				int s3_cc3 = 0;
				int s3_s = 0;
				int s3_ss = 0;

				for (int i=0;i<metametadataElements.size();i++) {
					if ( ((Element)metametadataElements.get(i)).getName().equals("contribute") ) {
					    s3_c3++;
					} else if ( ((Element)metametadataElements.get(i)).getName().equals("metadatascheme") ) {
						s3_s++;
					}
				}
				if ( s3_c3 > 0 ) contribute = new Element[s3_c3];
				if ( s3_s > 0 ) metadatascheme = new Element[s3_s];

				for (int i=0;i<metametadataElements.size();i++) {
					if ( ((Element)metametadataElements.get(i)).getName().equals("catalogentry") ) {
						s3_c1++;
					} else if ( ((Element)metametadataElements.get(i)).getName().equals("contribute") ) {
					    contribute[s3_cc3] = (Element)metametadataElements.get(i);
					    s3_cc3++;
					} else if ( ((Element)metametadataElements.get(i)).getName().equals("metadatascheme") ) {
					    metadatascheme[s3_ss] = (Element)metametadataElements.get(i);
					    s3_ss++;
					}
				}

				if ( s3_c1 > 0 ) s3_catalogentry = new Element[s3_c1];

				for (int i=0;i<metametadataElements.size();i++) {
					//System.out.println("metametadataElements.get("+i+") : "+metametadataElements.get(i));
					if ( ((Element)metametadataElements.get(i)).getName().equals("catalogentry") ) {
					    s3_catalogentry[s3_cc1] = (Element)metametadataElements.get(i);
					    s3_cc1++;
					} else if ( ((Element)metametadataElements.get(i)).getName().equals("language") ) {
					    s3_language = (Element)metametadataElements.get(i);
					}
				}

				// 3.1 metametadata - identifier reserved
	    	    // 3.2 metametadata - catalog entry ( 0 or more )
				if ( s3_catalogentry != null ) {
					metaMetaCatalogEntryData = new MetaMetaCatalogEntryData[s3_catalogentry.length];

					for ( int i=0 ; i < s3_catalogentry.length; i++ ) {
						metaMetaCatalogEntryData[i] = new MetaMetaCatalogEntryData();
						List catalogentryElements = null;

						if ( s3_catalogentry[i] != null ) {
							catalogentryElements = s3_catalogentry[i].getChildren();
							Element catalog = null;
							Element entry = null;
							for (int j=0;j<catalogentryElements.size();j++) {
								if ( ((Element)catalogentryElements.get(j)).getName().equals("catalog") ) {
								    catalog = (Element)catalogentryElements.get(j);
					            	store = catalog.getText();
	   	        					if ( StringUtil.getBytesCount(store) <= 255 ) {
									    metaMetaCatalogEntryData[i].setCatalog(store);
									    //System.out.println("metaMetaCatalogEntryData["+i+"].catalog() : " + metaMetaCatalogEntryData[i].getCatalog());
									}
								} else if ( ((Element)catalogentryElements.get(j)).getName().equals("entry") ) {
								    entry = (Element)catalogentryElements.get(j);
					            	store = getLangstring(entry);
	   	        					if ( StringUtil.getBytesCount(store) <= 255 ) {
									    metaMetaCatalogEntryData[i].setEntry(store);
									    //System.out.println("metaMetaCatalogEntryData["+i+"].entry() : " + metaMetaCatalogEntryData[i].getEntry());
									}
								}
							}
						}
						//System.out.println("3.2 metametadata catalogentry{catalog:" + metaMetaCatalogEntryData[i].getCatalog() +", entry:"+metaMetaCatalogEntryData[i].getEntry()+"}");
					}

				    // 3.3.1 metametadata - contribute ( 0 or more )
				    if ( s3_c3 > 0 ) {
				    	metaMetaContributeData = new MetaMetaContributeData[s3_c3];
						if ( contribute != null ) {
							for ( int i=0; i<contribute.length; i++ ) {
						    	metaMetaContributeData[i] = new MetaMetaContributeData();
						    	List contributeElements = contribute[i].getChildren();
						    	Element role = null;
				    			Element date = null;
				    		
						    	for (int j=0;j<contributeElements.size();j++) {
							    	if ( ((Element)contributeElements.get(j)).getName().equals("centity") ) {
							        	s3_z++;
							        }
				    		    }

				    	    	for (int j=0;j<contributeElements.size();j++) {
							    	if ( ((Element)contributeElements.get(j)).getName().equals("role") ) {
							        	role = (Element)contributeElements.get(j);
						            	store = getValue(role);
	    	   							if ( StringUtil.getBytesCount(store) <= 20 ) {
							        		metaMetaContributeData[i].setMeta_contr_role(store);
							        	}
								    } else if ( ((Element)contributeElements.get(j)).getName().equals("date") ) {
	   							        date = (Element)contributeElements.get(j);
						            	store = getDatetime(date);
	    	   							if ( StringUtil.getBytesCount(store) <= 30 ) {
	   						    		    metaMetaContributeData[i].setMeta_contr_date(store);
	   						    		}
								    }
				    	    	}
						        //System.out.println("3.3 metameta contribute {role:" + metaMetaContributeData[i].getMeta_contr_role() +", date:"+metaMetaContributeData[i].getMeta_contr_date()+"}");
		    				}

		    				if ( s3_z > 0 ) {
				    			metaMetaContributeCentitData = new MetaMetaContributeCentitData[s3_z];

								for ( int i=0; i<contribute.length; i++ ) {
									List contributeElements = contribute[i].getChildren();
						    		Element[] centity = null;
						    		int c4 = 0;
						    		int cc4 = 0;

				    	    		for (int j=0;j<contributeElements.size();j++) {
										if ( ((Element)contributeElements.get(j)).getName().equals("centity") ) {
									        metaMetaContributeCentitData[s3_zz] = new MetaMetaContributeCentitData();
									        s3_zz++; c4++;
									    }
				    	    		}

				    	    		if ( c4 > 0 ) centity = new Element[c4];

				    	    		for ( int j=0;j<contributeElements.size();j++) {
											if ( ((Element)contributeElements.get(j)).getName().equals("centity") ) {
									        centity[cc4] = (Element)contributeElements.get(j);
						    	    		cc4++;
									    }
				    	    		}

				    	    		if ( centity != null ) {
		    					        for ( int j=0; j<centity.length; j++ ) {
				    			        	List centityElements = centity[j].getChildren();
				    	    	    		Element[] vcard = null;
				    	    	    		int v = 0;
				    			        	int vv = 0;

				    	    		    	for ( int m=0; m<centityElements.size(); m++) {
				    	    	    		    if ( ((Element)centityElements.get(m)).getName().equals("vcard") )
				    	    	    		        v++;
						    	        	}

				    			        	if ( v > 0 ) vcard = new Element[v];
						    	    	    for ( int m=0; m<centityElements.size(); m++ ) {
				    	    	    		    if ( ((Element)centityElements.get(m)).getName().equals("vcard") ) {
		    			    	    			    vcard[vv] = (Element)centityElements.get(m);
									            	store = vcard[vv].getText();
	    	   										if ( StringUtil.getBytesCount(store) <= 3000 ) {
		    		    			    	    	    metaMetaContributeCentitData[s3_yy].setCentity(store);
		    		    			    	    	}
		    	    			    	    	    metaMetaContributeCentitData[s3_yy].setMetameta_contribute_idx(i);
				    	    	    		        //System.out.println("3.3.2 metaMetaContributeCentitData["+s3_yy+"]=>" + metaMetaContributeCentitData[s3_yy].getCentity());

//System.out.println("metaMetaContributeCentitData["+s3_yy+"].getMetameta_contribute_idx=>" + metaMetaContributeCentitData[s3_yy].getMetameta_contribute_idx());
				    	    	    		        vv++;s3_yy++;
				    	    	    	    	}
						    	        	}
				    			        }
				    	    		}
		    					}
		    				}
						}
					}
				}

				// 3.4 metametadata - metadata scheme ( 1 or more )
				if ( metadatascheme != null ) {
					metaMetaMetaDataSchemeData = new MetaMetaMetaDataSchemeData[metadatascheme.length];

					for ( int i=0 ; i < metadatascheme.length; i++ ) {
						metaMetaMetaDataSchemeData[i] = new MetaMetaMetaDataSchemeData();
						List metaMetaMetaDataSchemeElements = null;

						if ( metadatascheme[i] != null ) {
			            	store = metadatascheme[i].getText();
							if ( StringUtil.getBytesCount(store) <= 255 ) {
								metaMetaMetaDataSchemeData[i].setMetadata_scheme(store);
								//System.out.println("3.4 metametadata scheme : " + metaMetaMetaDataSchemeData[i].getMetadata_scheme());
							}
						}
					}
				}

	       	    // 3.5 metametadata - language ( 0 or 1 )
	       	    if ( s3_language != null ) {
	            	store = s3_language.getText();
	   	        	if ( StringUtil.getBytesCount(store) <= 10 ) {
						mainData.setMetameta_language(store);
						//System.out.println("3.5 metametadata language : " + mainData.getMetameta_language());
					}
				}
			}

			// 4. technical - 2 level 시작
/*			if ( technical == null ) {
				throw new Exception("xml문서에 technical section이 존재하지 않습니다. 1 and only 1");
			}
*/
			TechnicalRequirementData[] technicalRequirementData = null;
			if ( technical != null ) {
				List technicalElements = technical.getChildren();

				Element format = null;						// 1 and only 1
				Element size = null;						// 0 or 1
				Element location = null;					// 1 and only 1
				Element[] requirement = null;				// 0 or more
				Element installationremarks = null;			// 0 or 1
				Element otherplatformrequirements = null;	// 0 or 1
				Element duration = null;					// 0 or 1

				int re = 0;
				int rere = 0;

				for (int i=0;i<technicalElements.size();i++) {
					if ( ((Element)technicalElements.get(i)).getName().equals("requirement") ) {
						re++;
					}
				}

				if ( re > 0 ) requirement = new Element[re];

				for (int i=0;i<technicalElements.size();i++) {
					//System.out.println("technicalElements.get("+i+") : "+technicalElements.get(i));
					if ( ((Element)technicalElements.get(i)).getName().equals("format") ) {
					    format = (Element)technicalElements.get(i);
					} else if ( ((Element)technicalElements.get(i)).getName().equals("size") ) {
					    size = (Element)technicalElements.get(i);
					} else if ( ((Element)technicalElements.get(i)).getName().equals("location") ) {
					    location = (Element)technicalElements.get(i);
					} else if ( ((Element)technicalElements.get(i)).getName().equals("requirement") ) {
					    requirement[rere] = (Element)technicalElements.get(i);
					    rere++;
					} else if ( ((Element)technicalElements.get(i)).getName().equals("installationremarks") ) {
					    installationremarks = (Element)technicalElements.get(i);
					} else if ( ((Element)technicalElements.get(i)).getName().equals("otherplatformrequirements") ) {
					    otherplatformrequirements = (Element)technicalElements.get(i);
					} else if ( ((Element)technicalElements.get(i)).getName().equals("duration") ) {
					    duration = (Element)technicalElements.get(i);
					}
				}

				// 4.1 technical - format ( 1 and only 1 )
				if ( format != null ) {
	            	store = format.getText();
	   	        	if ( StringUtil.getBytesCount(store) <= 50 ) {
						mainData.setT_format(store);
						//System.out.println("4.1 technical format : " + mainData.getT_format());
					}
	        	}

				// 4.2 technical - size ( 0 or 1 )
				if ( size != null ) {
	            	store = size.getText();
	   	        	if ( StringUtil.getBytesCount(store) <= 20 ) {
						mainData.setTechnical_size(store);
						//System.out.println("4.2 technical size : " + mainData.getTechnical_size());
					}
				}

				// 4.3 technical - location ( 1 and only 1 )
				if ( location != null ) {	
	            	store = location.getText();
	            	store1 = location.getAttributeValue("type");
	   	        	if ( StringUtil.getBytesCount(store) <= 300 ) {
						if ( StringUtil.getBytesCount(store1) <= 20 ) mainData.setT_locationtype(store1);
						mainData.setT_location(store);
						//System.out.println("4.3 technical location {location:" + mainData.getT_location() +", type:"+mainData.getT_locationtype()+"}");
					}
				}

				// 4.4 technical - requirement ( 0 or more )
				if ( requirement != null ) {
					technicalRequirementData = new TechnicalRequirementData[requirement.length];

					for ( int i=0 ; i < requirement.length; i++ ) {
						technicalRequirementData[i] = new TechnicalRequirementData();
						List requirementElements = null;

						if ( requirement[i] != null ) {
							requirementElements = requirement[i].getChildren();
							Element type = null;
							Element name = null;
							Element maximumversion = null;
							Element minimumversion = null;
							for (int j=0;j<requirementElements.size();j++) {
								if ( ((Element)requirementElements.get(j)).getName().equals("type") ) {
								    type = (Element)requirementElements.get(j);
					            	store = getValue(type);
									if ( StringUtil.getBytesCount(store) <= 30 ) {
									    technicalRequirementData[i].setRequirement_type(store);
									    //System.out.println("technicalRequirementData["+i+"].type() : " + technicalRequirementData[i].getRequirement_type());
									}
								} else if ( ((Element)requirementElements.get(j)).getName().equals("name") ) {
								    name = (Element)requirementElements.get(j);
					            	store = getValue(name);
									if ( StringUtil.getBytesCount(store) <= 255 ) {
									    technicalRequirementData[i].setRequirement_name(store);
									    //System.out.println("technicalRequirementData["+i+"].name() : " + technicalRequirementData[i].getRequirement_name());
									}
								} else if ( ((Element)requirementElements.get(j)).getName().equals("minimumversion") ) {
								    minimumversion = (Element)requirementElements.get(j);
					            	store = minimumversion.getText();
									if ( StringUtil.getBytesCount(store) <= 30 ) {
									    technicalRequirementData[i].setMinimum_version(minimumversion.getText());
									    //System.out.println("technicalRequirementData["+i+"].minimumversion() : " + technicalRequirementData[i].getMinimum_version());
									}
								} else if ( ((Element)requirementElements.get(j)).getName().equals("maximumversion") ) {
								    maximumversion = (Element)requirementElements.get(j);
					            	store = maximumversion.getText();
									if ( StringUtil.getBytesCount(store) <= 30 ) {
									    technicalRequirementData[i].setMaximum_version(store);
									    //System.out.println("technicalRequirementData["+i+"].maximumversion() : " + technicalRequirementData[i].getMaximum_version());
									}
								}
							}
						}
						//System.out.println("4.4 technical requirement{type:" + technicalRequirementData[i].getRequirement_type() +", name:"+technicalRequirementData[i].getRequirement_name() +", maximumversion:"+technicalRequirementData[i].getMaximum_version() +", minimumversion:"+technicalRequirementData[i].getMinimum_version()+"}");
					}
				}

				// 4.5 technical - installationremarks ( 0 or 1 )
				if ( installationremarks != null ) {
	            	store = getLangstring(installationremarks);
	   	        	if ( StringUtil.getBytesCount(store) <= 2000 ) {
						mainData.setTechnical_installationremarks(store);
						//System.out.println("4.5 technical installationremarks : " + mainData.getTechnical_installationremarks());
					}
	        	}

				// 4.6 technical - otherplatformrequirements ( 0 or 1 )
				if ( otherplatformrequirements != null ) {
	            	store = getLangstring(otherplatformrequirements);
	   	        	if ( StringUtil.getBytesCount(store) <= 3000 ) {
						mainData.setTechnical_otherrequirements(store);
						//System.out.println("4.6 technical otherplatformrequirements : " + mainData.getTechnical_otherrequirements());
					}
	        	}

				// 4.7 technical - duration ( 0 or 1 )
				if ( duration != null ) {
	            	store = getDatetime(duration);
	   	        	if ( StringUtil.getBytesCount(store) <= 20 ) {
						mainData.setTechnical_duration(store);
						//System.out.println("4.7 technical duration : " + mainData.getTechnical_duration());
					}
				}
			}

			//5. educational - 2 level 시작
			EducationalIntendedUserRolData[] educationalIntendedUserRolData = null;
			EducationalLanguageData[] educationalLanguageData = null;

			if ( educational != null ) {
			    List educationalElements = educational.getChildren();
			    Element interactivitytype = null;			// 0 or 1
			    Element learningresourcetype = null;		// 0 or 1
			    Element interactivitylevel = null;			// 0 or 1
			    Element semanticdensity = null;				// 0 or 1
			    Element[] intendedenduserrole = null;		// 0 or more
			    Element context = null;						// 0 or 1
			    Element typicalagerange = null;				// 0 or 1
			    Element difficulty = null;					// 0 or 1
			    Element typicallearningtime = null;			// 0 or 1
			    Element description5 = null;				// 0 or 1
			    Element[] language5 = null;					// 0 or more

			    int ro = 0;
			    int roro = 0;
			    int l5 = 0;
			    int ll5 = 0;

			    for (int i=0;i<educationalElements.size();i++) {
					if ( ((Element)educationalElements.get(i)).getName().equals("intendedenduserrole") )
				    	ro++;
				    else if ( ((Element)educationalElements.get(i)).getName().equals("language") )
				    	l5++;
			    }

			    if ( ro > 0 ) intendedenduserrole = new Element[ro];
			    if ( l5 > 0 ) language5 = new Element[l5];

			    for (int i=0;i<educationalElements.size();i++) {
					//System.out.println("educationalElements.get("+i+") : "+educationalElements.get(i));
					if ( ((Element)educationalElements.get(i)).getName().equals("interactivitytype") ) {
		    			interactivitytype = (Element)educationalElements.get(i);
					} else if ( ((Element)educationalElements.get(i)).getName().equals("learningresourcetype") ) {
		    			learningresourcetype = (Element)educationalElements.get(i);
					} else if ( ((Element)educationalElements.get(i)).getName().equals("interactivitylevel") ) {
		    			interactivitylevel = (Element)educationalElements.get(i);
					} else if ( ((Element)educationalElements.get(i)).getName().equals("semanticdensity") ) {
		    			semanticdensity = (Element)educationalElements.get(i);
					} else if ( ((Element)educationalElements.get(i)).getName().equals("intendedenduserrole") ) {
					    intendedenduserrole[roro] = (Element)educationalElements.get(i);
					    roro++;
					} else if ( ((Element)educationalElements.get(i)).getName().equals("context") ) {
		    			context = (Element)educationalElements.get(i);
					} else if ( ((Element)educationalElements.get(i)).getName().equals("typicalagerange") ) {
		    			typicalagerange = (Element)educationalElements.get(i);
					} else if ( ((Element)educationalElements.get(i)).getName().equals("difficulty") ) {
		    			difficulty = (Element)educationalElements.get(i);
					} else if ( ((Element)educationalElements.get(i)).getName().equals("typicallearningtime") ) {
		    			typicallearningtime = (Element)educationalElements.get(i);
					} else if ( ((Element)educationalElements.get(i)).getName().equals("description") ) {
		    			description5 = (Element)educationalElements.get(i);
					} else if ( ((Element)educationalElements.get(i)).getName().equals("language") ) {
					    language5[ll5] = (Element)educationalElements.get(i);
					    ll5++;
					}
			    }

			    // 5.1 educational - interactivitytype ( 0 or 1 )     
			    if ( interactivitytype != null ) {
	            	store = getValue(interactivitytype);
   		        	if ( StringUtil.getBytesCount(store) <= 20 ) {
					    mainData.setEducational_interactivitytype(store);
					    //System.out.println("5.1 educational interactivitytype : " + mainData.getEducational_interactivitytype());
					}
        		}

				// 5.2 educational - learningresourcetype ( 0 or 1 )
				if ( learningresourcetype != null ) {
	            	store = getValue(learningresourcetype);
   		        	if ( StringUtil.getBytesCount(store) <= 300 ) {
					    mainData.setE_learningtype(store);
	    		    	//System.out.println("5.2 educational learningresourcetype : " + mainData.getE_learningtype());
	    		    }
        		}

			    // 5.3 educational - interactivitylevel ( 0 or 1 )
			    if ( interactivitylevel != null ) {
	            	store = getValue(interactivitylevel);
   		        	if ( StringUtil.getBytesCount(store) <= 20 ) {
					    mainData.setEducational_interactivitylevel(store);
					    //System.out.println("5.3 educational interactivitylevel : " + mainData.getEducational_interactivitylevel());
					}
				}

			    // 5.4 educational - semanticdensity ( 0 or 1 )
			    if ( semanticdensity != null ) {
	            	store = getValue(semanticdensity);
   		        	if ( StringUtil.getBytesCount(store) <= 20 ) {
					    mainData.setEducational_semanticdensity(store);
					    //System.out.println("5.4 educational semanticdensity : " + mainData.getEducational_semanticdensity());
					}
				}

				// 5.5 educational - intendedenduserrole ( 0 or more )
				if ( intendedenduserrole != null ) {
					educationalIntendedUserRolData = new EducationalIntendedUserRolData[intendedenduserrole.length];
	    	
					for ( int i=0 ; i < intendedenduserrole.length; i++ ) {
						educationalIntendedUserRolData[i] = new EducationalIntendedUserRolData();
						List intendedenduserroleElements = null;
        	
						if ( intendedenduserrole[i] != null ) {
			            	store = getValue(intendedenduserrole[i]);
							if ( StringUtil.getBytesCount(store) <= 15 ) {
								educationalIntendedUserRolData[i].setIntendedenduserrole(store);
								//System.out.println("5.5 educational intendedenduserrole : " + educationalIntendedUserRolData[i].getIntendedenduserrole());
							}
						}
					}
				}

				// 5.6 educational - context ( 0 or 1 )
				if ( context != null ) {
	            	store = getValue(context);
   		        	if ( StringUtil.getBytesCount(store) <= 300 ) {
					    mainData.setE_context(store);
						//System.out.println("5.6 educational context : " + mainData.getE_context());
					}
        		}

				// 5.7 educational - typicalagerange ( 0 or 1 )
				if ( typicalagerange != null ) {
	            	store = getLangstring(typicalagerange);
   		        	if ( StringUtil.getBytesCount(store) <= 300 ) {
					    mainData.setE_typicalagerange(store);
	    		    	//System.out.println("5.7 educational typicalagerange : " + mainData.getE_typicalagerange());
	    		    }
        		}

			    // 5.8 educational - difficulty ( 0 or 1 )
			    if ( difficulty != null ) {
	            	store = getValue(difficulty);
   		        	if ( StringUtil.getBytesCount(store) <= 20 ) {
					    mainData.setEducational_difficulty(store);
					    //System.out.println("5.8 educational difficulty : " + mainData.getEducational_difficulty());
					}
				}

			    // 5.9 educational - typicallearningtime ( 0 or 1 )
			    if ( typicallearningtime != null ) {
	            	store = getDatetime(typicallearningtime);
   		        	if ( StringUtil.getBytesCount(store) <= 20 ) {
					    mainData.setEducational_learningtime(store);
					    //System.out.println("5.9 educational typicallearningtime : " + mainData.getEducational_learningtime());
					}
				}

			    // 5.10 educational - description ( 0 or 1 )
			    if ( description5 != null ) {
	            	store = getLangstring(description5);
   		        	if ( StringUtil.getBytesCount(store) <= 3000 ) {
					    mainData.setEducational_description(store);
					    //System.out.println("5.10 educational description : " + mainData.getEducational_description());
					}
				}

				// 5.11 language ( 0 or more )
				if ( language5 != null ) {
					educationalLanguageData = new EducationalLanguageData[language5.length];

					for ( int i=0 ; i < language5.length; i++ ) {
						educationalLanguageData[i] = new EducationalLanguageData();
						List languageElements = null;

						if ( language5[i] != null ) {
			            	store = language5[i].getText();
							if ( StringUtil.getBytesCount(store) <= 20 ) {
								educationalLanguageData[i].setLanguage(store);
								//System.out.println("5.11 educational language : " + educationalLanguageData[i].getLanguage());
							}
						}
					}
				}
			}

			// 6. rights - 2 level 시작
/*			if ( rights == null ) {
				throw new Exception("xml문서에 rights section이 존재하지 않습니다. 1 and only 1");
			}
*/
			if ( rights != null ) {
				List rightsElements = rights.getChildren();

				Element cost = null;									// 1 and only 1
				Element copyrightandotherrestrictions = null;			// 1 and only 1
				Element description6 = null;							// 0 or 1

				for (int i=0;i<rightsElements.size();i++) {
					//System.out.println("rightsElements.get("+i+") : "+rightsElements.get(i));
					if ( ((Element)rightsElements.get(i)).getName().equals("cost") ) {
					    cost = (Element)rightsElements.get(i);
					} else if ( ((Element)rightsElements.get(i)).getName().equals("copyrightandotherrestrictions") ) {
					    copyrightandotherrestrictions = (Element)rightsElements.get(i);
					} else if ( ((Element)rightsElements.get(i)).getName().equals("description") ) {
					    description6 = (Element)rightsElements.get(i);
					}
				}

				// 6.1 rights - cost ( 1 and only 1 )
				if ( cost != null ) {
	            	store = getValue(cost);
	   	        	if ( StringUtil.getBytesCount(store) <= 5 ) {
						mainData.setRights_cost(store);
						//System.out.println("6.1 rights cost : " + mainData.getRights_cost());
					}
	        	}

				// 6.2 rights - copyrightandotherrestrictions ( 1 and only 1 )
				if ( copyrightandotherrestrictions != null ) {
	            	store = getValue(copyrightandotherrestrictions);
	   	        	if ( StringUtil.getBytesCount(store) <= 5 ) {
						mainData.setRights_copyrightrestrictions(store);
						//System.out.println("6.2 rights copyrightandotherrestrictions : " + mainData.getRights_copyrightrestrictions());
					}
	        	}

				// 6.3 rights - description ( 0 or 1 )
				if ( description6 != null ) {
	            	store = getLangstring(description6);
	   	        	if ( StringUtil.getBytesCount(store) <= 3000 ) {
						mainData.setRights_description(store);
						//System.out.println("6.3 rights description : " + mainData.getRights_description());
					}
	        	}
	        }

			//7. relation - 2 level 시작
			RelationData[] relationData = null;

			if ( relation != null ) {
				relationData = new RelationData[r];
				for ( int i=0; i<relation.length; i++ ) {
					relationData[i] = new RelationData();
				    List relationElements = relation[i].getChildren();
				    Element kind = null;			// 0 or 1
				    Element resource = null;		// 0 or 1
				    Element description7 = null;	// 0 or 1

				    for (int j=0;j<relationElements.size();j++) {
						//System.out.println("relationElements["+i+"].get("+j+") : "+relationElements.get(j));
						if ( ((Element)relationElements.get(j)).getName().equals("kind") ) {
			    			kind = (Element)relationElements.get(j);
						} else if ( ((Element)relationElements.get(j)).getName().equals("resource") ) {
			    			resource = (Element)relationElements.get(j);
						}
				    }

				    // 7.1 relation - kind ( 0 or 1 )
				    if ( kind != null ) {
		            	store = getValue(kind);
						if ( StringUtil.getBytesCount(store) <= 30 ) {
						    relationData[i].setRelation_kind(store);
						    //System.out.println("7.1 relation["+i+"] kind : " + relationData[i].getRelation_kind());
						}
        			}

					// 7.2 relation - resource ( 0 or 1 )
					if ( resource != null ) {
						List list = resource.getChildren();
						for ( int q=0;q<list.size();q++ ) {
							if ( ((Element)list.get(q)).getName().equals("description") ) {
								description7 = (Element)list.get(q);
				            	store = getLangstring(description7);
								if ( StringUtil.getBytesCount(store) <= 3000 ) {
									relationData[i].setRelation_description(store);
								    //System.out.println("7.2 relation["+i+"] resource description : " + relationData[i].getRelation_description());
								}
							}
						}
					}
					//System.out.println("7. relation["+i+"] {kind:" + relationData[i].getRelation_kind() + ",res-description:"+relationData[i].getRelation_description()+"}");
				}
			}

			//8. annotation - 2 level 시작
			AnnotationData[] annotationData = null;

			if ( annotation != null ) {
				annotationData = new AnnotationData[a];
				for ( int i=0; i<annotation.length; i++ ) {
					annotationData[i] = new AnnotationData();
				    List annotationElements = annotation[i].getChildren();
				    Element person = null;			// 0 or 1
				    Element date = null;			// 0 or 1
				    Element description8 = null;	// 0 or 1

				    for (int j=0;j<annotationElements.size();j++) {
						//System.out.println("annotationElements["+i+"].get("+j+") : "+annotationElements.get(j));
						if ( ((Element)annotationElements.get(j)).getName().equals("person") ) {
			    			person = (Element)annotationElements.get(j);
						} else if ( ((Element)annotationElements.get(j)).getName().equals("date") ) {
			    			date = (Element)annotationElements.get(j);
						} else if ( ((Element)annotationElements.get(j)).getName().equals("description") ) {
			    			description8 = (Element)annotationElements.get(j);
						}
				    }

				    // 8.1 annotation - person ( 0 or 1 )
				    if ( person != null ) {
		            	store = getVcard(person);
						if ( StringUtil.getBytesCount(store) <= 500 ) {
						    annotationData[i].setPerson(store);
						    //System.out.println("8.1 annotation["+i+"] person : " + annotationData[i].getPerson());
						}
					}

				    // 8.2 annotation - date ( 0 or 1 )
				    if ( date != null ) {
		            	store = getDatetime(date);
						if ( StringUtil.getBytesCount(store) <= 30 ) {
							annotationData[i].setAnnotation_date(store);
							//System.out.println("8.2 annotation["+i+"] date : " + annotationData[i].getAnnotation_date());
						}
					}

				    // 8.3 annotation - description ( 0 or 1 )
				    if ( description8 != null ) {
		            	store = getLangstring(description8);
						if ( StringUtil.getBytesCount(store) <= 3000 ) {
							annotationData[i].setDescription(store);
							//System.out.println("8.3 annotation["+i+"] description : " + annotationData[i].getDescription());
						}
					}
					//System.out.println("8. annotation["+i+"] {person:" + annotationData[i].getPerson() + ",date:"+annotationData[i].getAnnotation_date() + ",description:"+annotationData[i].getDescription()+"}");
				}
			}

			//9. classification - 2 level 시작
			ClassificationData[] classificationData = null;

			if ( classification != null ) {
				classificationData = new ClassificationData[c];
				for ( int i=0; i<classification.length; i++ ) {
					classificationData[i] = new ClassificationData();
				    List classificationElements = classification[i].getChildren();
				    Element purpose = null;			// 0 or 1
				    Element description9 = null;	// 1 and only 1
				    Element keyword9 = null;		// 1 and only 1

				    for (int j=0;j<classificationElements.size();j++) {
						//System.out.println("classificationElements["+i+"].get("+j+") : "+classificationElements.get(j));
						if ( ((Element)classificationElements.get(j)).getName().equals("purpose") ) {
			    			purpose = (Element)classificationElements.get(j);
						} else if ( ((Element)classificationElements.get(j)).getName().equals("description") ) {
			    			description9 = (Element)classificationElements.get(j);
						} else if ( ((Element)classificationElements.get(j)).getName().equals("keyword") ) {
			    			keyword9 = (Element)classificationElements.get(j);
						}
				    }

				    // 9.1 classification - purpose ( 0 or 1 )
				    if ( purpose != null ) {
		            	store = getValue(purpose);
						if ( StringUtil.getBytesCount(store) <= 50 ) {
						    classificationData[i].setPurpose(store);
						    //System.out.println("9.1 classification["+i+"] purpose : " + classificationData[i].getPurpose());
						}
					}

				    // 9.2 classification - description ( 0 or 1 )
				    if ( description9 != null ) {
		            	store = getLangstring(description9);
						if ( StringUtil.getBytesCount(store) <= 255 ) {
							classificationData[i].setDescription(store);
							//System.out.println("9.2 classification["+i+"] description : " + classificationData[i].getDescription());
						}
					}

				    // 9.3 classification - keyword ( 0 or 1 )
				    if ( keyword9 != null ) {
		            	store = getLangstring(keyword9);
						if ( StringUtil.getBytesCount(store) <= 300 ) {
							classificationData[i].setKeyword(store);
							//System.out.println("9.3 classification["+i+"] keyword : " + classificationData[i].getKeyword());
						}
        			}
					//System.out.println("9. classification["+i+"] {purpose:" + classificationData[i].getPurpose() + ",description:"+classificationData[i].getDescription()+",keyword:"+classificationData[i].getKeyword() + "}");
				}
			}

			int metadata_idx = getNewIdx(box);

			mainData.setMetadata_idx(metadata_idx);
			int isOk = insertObject(box, mainData, generalCatalogEntryData, generalLanguageData, generalDescriptionData, generalKeywordData, generalCoverageData, lifecycleContributeData, lifecycleContributeCentitData, metaMetaCatalogEntryData, metaMetaContributeData, metaMetaContributeCentitData, metaMetaMetaDataSchemeData,
				technicalRequirementData,
				educationalIntendedUserRolData,
				educationalLanguageData, relationData, annotationData, classificationData);

			if ( isOk != 1 ) throw new Exception("METADATA 관련 테이블을 생성하던 중 에러가 발생하였습니다.");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("readXMLwriteDB()\r\n" + ex.getMessage());
		}
	}

   /**
    get Langstring Element
    @param Element  XML Element
    @return String
    */
	private String getLangstring(Element parent) throws Exception {
		List list = null;
		Element langstring = null;
		String str = "";

		if ( parent != null ) {
			list = parent.getChildren();
			if ( list != null ) {
				for (int i=0;i<list.size();i++) {
					if ( ((Element)list.get(i)).getName().equals("langstring") ) {
					    langstring = (Element)list.get(i);
					    str = langstring.getText();
					}
				}
			}
		}
		return str;
	}

   /**
    get Value Element
    @param Element  XML Element
    @return String
    */
	private String getValue(Element parent) throws Exception {
		List list = null; 
		Element source = null;
		Element value = null;
		String str = "";

		if ( parent != null ) {
			list = parent.getChildren();
			if ( list != null ) {
				for (int i=0;i<list.size();i++) {
					if ( ((Element)list.get(i)).getName().equals("source") ) {
					    source = (Element)list.get(i);
					} else if ( ((Element)list.get(i)).getName().equals("value") ) {
					    value = (Element)list.get(i);
					    str = getLangstring(value);
					}
				}
			}
		}
		return str;
	}

   /**
    get Datetime Element
    @param Element  XML Element
    @return String
    */
	private String getDatetime(Element parent) throws Exception {
		List list = null; 
		Element datetime = null;
		String str = "";

		if ( parent != null ) {
			list = parent.getChildren();
			if ( list != null ) {
				for (int i=0;i<list.size();i++) {
					if ( ((Element)list.get(i)).getName().equals("datetime") ) {
					    datetime = (Element)list.get(i);
					    str = datetime.getText();
					}
				}
			}
		}
		return str;
	}

   /**
    get vcard Element
    @param Element  XML Element
    @return String
    */
	private String getVcard(Element parent) throws Exception {
		List list = null; 
		Element vcard = null;
		String str = "";

		if ( parent != null ) {
			list = parent.getChildren();
			if ( list != null ) {
				for (int i=0;i<list.size();i++) {
					if ( ((Element)list.get(i)).getName().equals("vcard") ) {
					    vcard = (Element)list.get(i);
					    str = vcard.getText();
					}
				}
			}
		}
		return str;
	}

   /**
    get MetaDataMainData by oid
    @param box          receive from the form object and session
	@param out      printwriter object
    @return ArrayList
    */  
    public ArrayList getMetaDataAllByOid(RequestBox box, PrintWriter out) throws Exception {
		MetaDataMainData data = null;
		ArrayList metaDataAll  = new ArrayList();

        try {
			data = getMetaDataMainData(box);
			metaDataAll.add(data);

			// general
			GeneralCatalogEntryBean generalCatalogEntryBean = new GeneralCatalogEntryBean();
			ArrayList generalCatalogEntryList = generalCatalogEntryBean.selectGeneralCatalogEntryData(data.getMetadata_idx(), box);
			metaDataAll.add(generalCatalogEntryList);

			GeneralLanguageBean generalLanguageBean = new GeneralLanguageBean();
			ArrayList generalLanguageList = generalLanguageBean.selectGeneralLanguageData(data.getMetadata_idx(), box);
			metaDataAll.add(generalLanguageList);

			GeneralDescriptionBean generalDescriptionBean = new GeneralDescriptionBean();
			ArrayList generalDescriptionList = generalDescriptionBean.selectGeneralDescriptionData(data.getMetadata_idx(), box);
			metaDataAll.add(generalDescriptionList);

			GeneralKeywordBean generalKeywordBean = new GeneralKeywordBean();
			ArrayList generalKeywordList = generalKeywordBean.selectGeneralKeywordData(data.getMetadata_idx(), box);
			metaDataAll.add(generalKeywordList);

			GeneralCoverageBean generalCoverageBean = new GeneralCoverageBean();
			ArrayList generalCoverageList = generalCoverageBean.selectGeneralCoverageData(data.getMetadata_idx(), box);
			metaDataAll.add(generalCoverageList);

			// lifecycle
			LifecycleContributeBean lifecycleContributeBean = new LifecycleContributeBean();
			ArrayList lifecycleContributeList = lifecycleContributeBean.selectLifecycleContributeData(data.getMetadata_idx(), box);
			metaDataAll.add(lifecycleContributeList);

			LifecycleContributeCentitBean lifecycleContributeCentitBean = new LifecycleContributeCentitBean();
			ArrayList lifecycleContributeCentitList = lifecycleContributeCentitBean.selectLifecycleContributeCentitData(data.getMetadata_idx(), box);
			metaDataAll.add(lifecycleContributeCentitList);

			// metametadata
			MetaMetaCatalogEntryBean metaMetaCatalogEntryBean = new MetaMetaCatalogEntryBean();
			ArrayList metaMetaCatalogEntryList = metaMetaCatalogEntryBean.selectMetaMetaCatalogEntryData(data.getMetadata_idx(), box);
			metaDataAll.add(metaMetaCatalogEntryList);

			MetaMetaContributeBean metaMetaContributeBean = new MetaMetaContributeBean();
			ArrayList metaMetaContributeList = metaMetaContributeBean.selectMetaMetaContributeData(data.getMetadata_idx(), box);
			metaDataAll.add(metaMetaContributeList);

			MetaMetaContributeCentitBean metaMetaContributeCentitBean = new MetaMetaContributeCentitBean();
			ArrayList metaMetaContributeCentitList = metaMetaContributeCentitBean.selectMetaMetaContributeCentitData(data.getMetadata_idx(), box);
			metaDataAll.add(metaMetaContributeCentitList);

			MetaMetaMetaDataSchemeBean metaMetaMetaDataSchemeBean = new MetaMetaMetaDataSchemeBean();
			ArrayList metaMetaMetaDataSchemeList = metaMetaMetaDataSchemeBean.selectMetaMetaMetaDataSchemeData(data.getMetadata_idx(), box);
			metaDataAll.add(metaMetaMetaDataSchemeList);

			TechnicalRequirementBean technicalRequirementBean = new TechnicalRequirementBean();
			ArrayList technicalRequirementList = technicalRequirementBean.selectTechnicalRequirementData(data.getMetadata_idx(), box);
			metaDataAll.add(technicalRequirementList);

			EducationalIntendedUserRolBean educationalIntendedUserRolBean = new EducationalIntendedUserRolBean();
			ArrayList educationalIntendedUserRolList = educationalIntendedUserRolBean.selectEducationalIntendedUserRolData(data.getMetadata_idx(), box);
			metaDataAll.add(educationalIntendedUserRolList);

			EducationalLanguageBean educationalLanguageBean = new EducationalLanguageBean();
			ArrayList educationalLanguageList = educationalLanguageBean.selectEducationalLanguageData(data.getMetadata_idx(), box);
			metaDataAll.add(educationalLanguageList);

			RelationBean relationBean = new RelationBean();
			ArrayList relationList = relationBean.selectRelationData(data.getMetadata_idx(), box);
			metaDataAll.add(relationList);

			AnnotationBean annotationBean = new AnnotationBean();
			ArrayList annotationList = annotationBean.selectAnnotationData(data.getMetadata_idx(), box);
			metaDataAll.add(annotationList);

			ClassificationBean classificationBean = new ClassificationBean();
			ArrayList classificationList = classificationBean.selectClassificationData(data.getMetadata_idx(), box);
			metaDataAll.add(classificationList);
        }
        catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("getMetaDataAllByOid()\r\n" + ex.getMessage());
        }
        return metaDataAll;
    }

    public String updateObject(RequestBox box, PrintWriter out) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        int isOk = 0;
        String results = "";
        String metadata_idx = box.getString("p_metadata_idx");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //tz_met_m update
            sql = "update tz_met_m set "
                + "general_title = '" 						+ box.getString("Title")+"', "
                + "general_structure = '" 					+ box.getString("Structure")+"', "
                + "general_aggregationlevel = '" 			+ box.getString("Aggregationlevel")+"', "
                + "lifecycle_version = '" 					+ box.getString("Version")+"', "
                + "lifecycle_status = '" 					+ box.getString("Status")+"', "
                + "metameta_language = '" 					+ box.getString("LanguageMetametadata")+"', "
                + "technical_size = '" 						+ box.getString("Size")+"', "
                + "technical_installationremarks = '" 		+ box.getString("Installationremarks")+"', "
                + "technical_otherrequirements = '" 		+ box.getString("Otherplatformrequirements")+"', "
                + "technical_duration = '" 					+ box.getString("Duration")+"', "
                + "educational_interactivitytype = '" 		+ box.getString("Interactivitytype")+"', "
                + "educational_interactivitylevel = '" 		+ box.getString("Interactivitylevel")+"', "
                + "educational_semanticdensity = '" 		+ box.getString("Semanticdensity")+"', "
                + "educational_difficulty = '" 				+ box.getString("Difficulty")+"', "
                + "educational_learningtime = '" 			+ box.getString("Typicallearningtime")+"', "
                + "educational_description = '" 			+ box.getString("DescriptionEducational")+"', "
                + "rights_cost = '" 						+ box.getString("Cost")+"', "
                + "rights_copyrightrestrictions = '" 		+ box.getString("Copyrightandotherrestrictions")+"', "
                + "rights_description = '" 					+ box.getString("DescriptionRights")+"', "
                + "t_format = '" 							+ box.getString("Format")+"', "
                + "t_location = '" 							+ box.getString("Location")+"', "
                + "e_learningtype = '" 						+ box.getString("Learningresourcetype")+"', "
                + "e_context = '" 							+ box.getString("Learningcontext")+"', "
                + "e_typicalagerange = '" 					+ box.getString("Typicalagerange")+"' "
                + "where metadata_idx = "+metadata_idx;

//System.out.println("=======================> sql : " + sql);
            isOk = connMgr.executeUpdate(sql);

            if ( isOk != 1 ) {
              	connMgr.rollback();
				results = "TZ_MET_M을 수정하는 중 에러가 발생하였습니다.";
				throw new Exception();
			}

			// delete all data
            connMgr.executeUpdate("delete from tz_classif where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_annot where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_relat where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_edu_lan where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_edu_rol where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_tec_req where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_met_sch where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_met_cen where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_met_con where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_met_cat where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_lif_cen where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_lif_con where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_gen_cat where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_gen_lan where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_gen_des where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_gen_key where metadata_idx = "+metadata_idx);
            connMgr.executeUpdate("delete from tz_gen_cov where metadata_idx = "+metadata_idx);

			// generate & insert GeneralCatalogEntryData
		    GeneralCatalogEntryData[] generalCatalogEntryData = getGeneralCatalogEntryData(box);
			if ( generalCatalogEntryData != null ) {
				for ( int i=0; i<generalCatalogEntryData.length; i++ ) {
					if ( !"".equals(generalCatalogEntryData[i].getEntry()) && !"".equals(generalCatalogEntryData[i].getCatalog()) ) {
						sql = "insert into tz_gen_cat "
	        	    	    + "(general_catalog_idx, catalog, entry, metadata_idx)"
		                	+ " values "
			                + "(GEN_CAT_SEQ.NEXTVAL"
	        		        + "," + StringManager.makeSQL(generalCatalogEntryData[i].getCatalog())
	            	    	+ "," + StringManager.makeSQL(generalCatalogEntryData[i].getEntry())
		            	    + "," + metadata_idx
	        	        	+ ")";
				        isOk = connMgr.executeUpdate(sql);

				       	if ( isOk != 1 ) {
	        	       		connMgr.rollback();
							results = "TZ_GEN_CAT에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
					}
				}
			}

			// generate & insert GeneralLanguageData
		    GeneralLanguageData[] generalLanguageData = getGeneralLanguageData(box);
			if ( generalLanguageData != null ) {
				for ( int i=0; i<generalLanguageData.length; i++ ) {
					if ( !"".equals(generalLanguageData[i].getLanguage()) ) {
						sql = "insert into tz_gen_lan "
	        	    	    + "(general_language_idx, language, metadata_idx)"
	                		+ " values "
			                + "(GEN_LAN_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(generalLanguageData[i].getLanguage())
		            	    + "," + metadata_idx
	        		        + ")";
			        	isOk = connMgr.executeUpdate(sql);

		    	   		if ( isOk != 1 ) {
	            	   		connMgr.rollback();
							results = "TZ_GEN_LAN에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
					}
				}
			}

			// generate & insert GeneralDescriptionData
			String description1 = box.getString("DescriptionGeneral");
			if ( !"".equals(description1) ) {
				//for ( int i=0; i<generalDescriptionData.length; i++ ) {
					sql = "insert into tz_gen_des "
	        	    	    + "(general_description_idx, description, metadata_idx)"
	                		+ " values "
			                + "(GEN_DES_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(description1)
		            	    + "," + metadata_idx
	        		        + ")";
		        	isOk = connMgr.executeUpdate(sql);

			       	if ( isOk != 1 ) {
	               		connMgr.rollback();
						results = "TZ_GEN_DES에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				//}
			}

			// generate & insert GeneralKeywordData
		    GeneralKeywordData[] generalKeywordData = getGeneralKeywordData(box);
			if ( generalKeywordData != null ) {
				for ( int i=0; i<generalKeywordData.length; i++ ) {
					if ( !"".equals(generalKeywordData[i].getKeyword()) ) {
						sql = "insert into tz_gen_key "
	        	    	    + "(general_keyword_idx, keyword, metadata_idx)"
	                		+ " values "
			                + "(GEN_KEY_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(generalKeywordData[i].getKeyword())
		            	    + "," + metadata_idx
	        		        + ")";
			        	isOk = connMgr.executeUpdate(sql);

				       	if ( isOk != 1 ) {
	    	           		connMgr.rollback();
							results = "TZ_GEN_KEY에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
					}
				}
			}

			// generate & insert GeneralCoverageData
		    GeneralCoverageData[] generalCoverageData = getGeneralCoverageData(box);
			if ( generalCoverageData != null ) {			
				for ( int i=0; i<generalCoverageData.length; i++ ) {
					if ( !"".equals(generalCoverageData[i].getCoverage()) ) {
						sql = "insert into tz_gen_cov "
	        	    	    + "(general_coverage_idx, coverage, metadata_idx)"
	                		+ " values "
			                + "(GEN_COV_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(generalCoverageData[i].getCoverage())
		            	    + "," + metadata_idx
	        		        + ")";
		        		isOk = connMgr.executeUpdate(sql);

			    	   	if ( isOk != 1 ) {
	        	       		connMgr.rollback();
							results = "TZ_GEN_COV에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
					}
				}
			}

			// generate & insert LifecycleContributeData
		    LifecycleContributeData[] lifecycleContributeData = getLifecycleContributeData(box);
			if ( lifecycleContributeData != null ) {
			    int idx = 0;
			    int first_idx = 0;
			    String[] foreign_idx = new String[lifecycleContributeData.length];
				for ( int i=0; i<lifecycleContributeData.length; i++ ) {
	  				idx = getNewLifecycleContributeIdx(box);
	  				lifecycleContributeData[i].setLifecycle_contribute_idx(idx);
	  				if ( i == 0 ) first_idx = idx;
	  				foreign_idx[i] = ""+idx;
					sql = "insert into tz_lif_con "
		        	        + "(lifecycle_contribute_idx, lifecycle_contr_role, lifecycle_contr_date, metadata_idx)"
        		        	+ " values "
	            		    + "(" + idx
		        	        + "," + StringManager.makeSQL(lifecycleContributeData[i].getLifecycle_contr_role())
        			        + "," + StringManager.makeSQL(lifecycleContributeData[i].getLifecycle_contr_date())
		            	    + "," + metadata_idx
        			        + ")";
		       		isOk = connMgr.executeUpdate(sql);

		   			if ( isOk != 1 ) {
	               		connMgr.rollback();
						results = "TZ_LIF_CON에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				}

				//if ( lifecycleContributeCentitData != null ) {			
					for ( int j=0; j<lifecycleContributeData.length; j++ ) {
						sql = "insert into tz_lif_cen "
	        			        + "(lifecycle_contr_centity_idx, centity, lifecycle_contribute_idx, metadata_idx)"
	               				+ " values "
				               	+ "(LIF_CEN_SEQ.NEXTVAL"
		    	    	        + "," + StringManager.makeSQL(lifecycleContributeData[j].getCentity())
		    	    	        + "," + lifecycleContributeData[j].getLifecycle_contribute_idx()
		                		//+ "," + foreign_idx[lifecycleContributeCentitData[j].getLifecycle_contribute_idx()]
		            		    + "," + metadata_idx
				       	        + ")";
				        isOk = connMgr.executeUpdate(sql);

			   		   	if ( isOk != 1 ) {
	           				connMgr.rollback();
							results = "TZ_LIF_CEN에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
				    }
				//}
			}

			// generate & insert MetaMetaCatalogEntryData
		    MetaMetaCatalogEntryData[] metaMetaCatalogEntryData = getMetaMetaCatalogEntryData(box);
			if ( metaMetaCatalogEntryData != null ) {			
				for ( int i=0; i<metaMetaCatalogEntryData.length; i++ ) {
					sql = "insert into tz_met_cat "
	        	    	    + "(metameta_catalog_idx, catalog, entry, metadata_idx)"
		                	+ " values "
			                + "(MET_CAT_SEQ.NEXTVAL"
	        		        + "," + StringManager.makeSQL(metaMetaCatalogEntryData[i].getCatalog())
	            	    	+ "," + StringManager.makeSQL(metaMetaCatalogEntryData[i].getEntry())
		            	    + "," + metadata_idx
	        	        	+ ")";
			        isOk = connMgr.executeUpdate(sql);

			       	if ( isOk != 1 ) {
	               		connMgr.rollback();
						results = "TZ_MET_CAT에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				}
			}

			// generate & insert MetaMetaContributeData
		    MetaMetaContributeData[] metaMetaContributeData = getMetaMetaContributeData(box);
			if ( metaMetaContributeData != null ) {
			    int idx = 0;
			    int first_idx = 0;
			    String[] foreign_idx = new String[metaMetaContributeData.length];
				for ( int i=0; i<metaMetaContributeData.length; i++ ) {
	  				idx = getNewMetaMetaContributeIdx(box);
	  				metaMetaContributeData[i].setMetameta_contribute_idx(idx);
	  				if ( i == 0 ) first_idx = idx;
	  				foreign_idx[i] = ""+idx;
					sql = "insert into tz_met_con "
		        	        + "(metameta_contribute_idx, meta_contr_role, meta_contr_date, metadata_idx)"
        		        	+ " values "
	            		    + "(" + idx
		        	        + "," + StringManager.makeSQL(metaMetaContributeData[i].getMeta_contr_role())
        			        + "," + StringManager.makeSQL(metaMetaContributeData[i].getMeta_contr_date())
		            	    + "," + metadata_idx
        			        + ")";
		       		isOk = connMgr.executeUpdate(sql);

		   			if ( isOk != 1 ) {
	               		connMgr.rollback();
						results = "TZ_MET_CON에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				}

				//if ( metaMetaContributeCentitData != null ) {				
					for ( int j=0; j<metaMetaContributeData.length; j++ ) {
			  	
						sql = "insert into tz_met_cen "
	        			        + "(metameta_contr_centity_idx, metameta_contribute_idx, centity, metadata_idx)"
	               				+ " values "
				               	+ "(MET_CEN_SEQ.NEXTVAL"
		    	    	        + "," + metaMetaContributeData[j].getMetameta_contribute_idx()
	                    		//+ "," + foreign_idx[metaMetaContributeData[j].getMetameta_contribute_idx()]
		    	    	        + "," + StringManager.makeSQL(metaMetaContributeData[j].getCentity())
		            		    + "," + metadata_idx
				       	        + ")";
				        isOk = connMgr.executeUpdate(sql);

			   		   	if ( isOk != 1 ) {
	           				connMgr.rollback();
							results = "TZ_MET_CON_CENTIT에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
				    }
				//}
			}

			// generate & insert MetaMetaMetaDataSchemeData
			String scheme3 = box.getString("Metadatascheme");
			if ( !"".equals(scheme3) ) {			
				//for ( int i=0; i<metaMetaMetaDataSchemeData.length; i++ ) {
					sql = "insert into tz_met_sch "
	        	    	    + "(metadata_scheme_idx, metadata_scheme, metadata_idx)"
	                		+ " values "
			                + "(MET_SCH_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(scheme3)
		            	    + "," + metadata_idx
	        		        + ")";
	        		isOk = connMgr.executeUpdate(sql);

	       			if ( isOk != 1 ) {
               			connMgr.rollback();
						results = "TZ_MET_SCH에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				//}
			}

			// generate & insert TechnicalRequirementData
		    TechnicalRequirementData[] technicalRequirementData = getTechnicalRequirementData(box);
			if ( technicalRequirementData != null ) {			
				for ( int i=0; i<technicalRequirementData.length; i++ ) {
					sql = "insert into tz_tec_req "
	        	    	    + "(technical_requirement_idx, requirement_type, requirement_name, minimum_version, maximum_version, metadata_idx)"
	                		+ " values "
			                + "(TEC_REQ_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(technicalRequirementData[i].getRequirement_type())
	    	    	        + "," + StringManager.makeSQL(technicalRequirementData[i].getRequirement_name())
	    	    	        + "," + StringManager.makeSQL(technicalRequirementData[i].getMinimum_version())
	    	    	        + "," + StringManager.makeSQL(technicalRequirementData[i].getMaximum_version())
		            	    + "," + metadata_idx
	        		        + ")";
		        	isOk = connMgr.executeUpdate(sql);

		       		if ( isOk != 1 ) {
	               		connMgr.rollback();
						results = "TZ_TEC_REQ에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				}
			}

			// generate & insert EducationalIntendedUserRolData
			EducationalIntendedUserRolData[] educationalIntendedUserRolData = getEducationalIntendedUserRolData(box);
			if ( educationalIntendedUserRolData != null ) {			
				for ( int i=0; i<educationalIntendedUserRolData.length; i++ ) {
					if ( !"".equals(educationalIntendedUserRolData[i].getIntendedenduserrole()) ) {
						sql = "insert into tz_edu_rol "
	        	    	    + "(educatinal_intenduserrole_idx, intendedenduserrole, metadata_idx)"
	                		+ " values "
			                + "(EDU_ROL_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(educationalIntendedUserRolData[i].getIntendedenduserrole())
		            	    + "," + metadata_idx
	        		        + ")";
			        	isOk = connMgr.executeUpdate(sql);

			       		if ( isOk != 1 ) {
	    	           		connMgr.rollback();
							results = "TZ_EDU_ROL에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
					}
				}
			}

			// generate & insert EducationalLanguageData
		    EducationalLanguageData[] educationalLanguageData = getEducationalLanguageData(box);
			if ( educationalLanguageData != null ) {
				for ( int i=0; i<educationalLanguageData.length; i++ ) {
					if ( !"".equals(educationalLanguageData[i].getLanguage()) ) {
						sql = "insert into tz_edu_lan "
	        	    	    + "(educational_language_idx, language, metadata_idx)"
	                		+ " values "
			                + "(EDU_LAN_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(educationalLanguageData[i].getLanguage())
		            	    + "," + metadata_idx
	        		        + ")";
			        	isOk = connMgr.executeUpdate(sql);

			       		if ( isOk != 1 ) {
	        	       		connMgr.rollback();
							results = "TZ_EDU_LAN에 입력하는 중 에러가 발생하였습니다.";
							throw new Exception();
						}
					}
				}
			}

			// generate & insert RelationData
		    RelationData[] relationData = getRelationData(box);
			if ( relationData != null ) {
				for ( int i=0; i<relationData.length; i++ ) {
					sql = "insert into tz_relat "
	        	    	    + "(relation_idx, relation_kind, relation_resource, relation_description, metadata_idx)"
	                		+ " values "
			                + "(RELAT_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(relationData[i].getRelation_kind())
	    	    	        + "," + StringManager.makeSQL(relationData[i].getRelation_resource())
	    	    	        + "," + StringManager.makeSQL(relationData[i].getRelation_description())
		            	    + "," + metadata_idx
	        		        + ")";
		        	isOk = connMgr.executeUpdate(sql);

		       		if ( isOk != 1 ) {
	               		connMgr.rollback();
						results = "TZ_RELAT에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				}
			}

			// generate & insert AnnotationData
		    AnnotationData[] annotationData = getAnnotationData(box);
			if ( annotationData != null ) {
				for ( int i=0; i<annotationData.length; i++ ) {
					sql = "insert into tz_annot "
	        	    	    + "(annotation_idx, person, annotation_date, description, metadata_idx)"
	                		+ " values "
			                + "(ANNOT_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(annotationData[i].getPerson())
	    	    	        + "," + StringManager.makeSQL(annotationData[i].getAnnotation_date())
	    	    	        + "," + StringManager.makeSQL(annotationData[i].getDescription())
		            	    + "," + metadata_idx
	        		        + ")";
		        	isOk = connMgr.executeUpdate(sql);

		       		if ( isOk != 1 ) {
	               		connMgr.rollback();
						results = "TZ_ANNOT에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				}
			}

			// generate & insert ClassificationData
		    ClassificationData[] classificationData = getClassificationData(box);
			if ( classificationData != null ) {
				for ( int i=0; i<classificationData.length; i++ ) {
					sql = "insert into tz_classif "
	        	    	    + "(classification_idx, purpose, description, keyword, metadata_idx)"
	                		+ " values "
			                + "(CLASSIF_SEQ.NEXTVAL"
	    	    	        + "," + StringManager.makeSQL(classificationData[i].getPurpose())
	    	    	        + "," + StringManager.makeSQL(classificationData[i].getDescription())
	    	    	        + "," + StringManager.makeSQL(classificationData[i].getKeyword())
		            	    + "," + metadata_idx
	        		        + ")";
		        	isOk = connMgr.executeUpdate(sql);

		       		if ( isOk != 1 ) {
	               		connMgr.rollback();
						results = "TZ_CLASSIF에 입력하는 중 에러가 발생하였습니다.";
						throw new Exception();
					}
				}
			}

			results = "OK";
			if (isOk > 0) {connMgr.commit();}
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("results = " + results + "\r\n" +"sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return results;
    }

    private int getDataCount(String alldata, String split, int arraySize) {
		int[] index = new int[arraySize];
        int count = 0;

       	for ( int i=0;i<arraySize;i++ ) {
           	if ( i == 0 ) index[i] = alldata.indexOf(split);
           	else index[i]= alldata.indexOf(split, index[i-1]+split.length());
           	if ( index[i] == -1 ) { count = i; break; }
        }
        return count;
    }
    
    private GeneralCatalogEntryData[] getGeneralCatalogEntryData(RequestBox box) {
		String catalogentry = box.getString("CatalogueEntry");
		String catalog_title = ")  Catalogue:";
		String entry_title = "~    Entry:";
		int catalogentryCount = getDataCount(catalogentry, catalog_title, 10);
		GeneralCatalogEntryData[] generalCatalogEntryData = new GeneralCatalogEntryData[catalogentryCount];
		int[] c_indexof = new int[catalogentryCount+1];
		int[] e_indexof = new int[catalogentryCount];
		String catalog = "";
		String entry = "";

		for ( int i=0;i<catalogentryCount;i++ ) {
			if ( i == 0 ) {
				c_indexof[i] = catalogentry.indexOf(catalog_title);
				e_indexof[i] = catalogentry.indexOf(entry_title);
			} else {
				c_indexof[i] = catalogentry.indexOf(catalog_title, catalog_title.length() + c_indexof[i-1]);
				e_indexof[i] = catalogentry.indexOf(entry_title, entry_title.length() + e_indexof[i-1]);
			}
			c_indexof[catalogentryCount] = catalogentry.length()+1;
		}
		for ( int i=0;i<catalogentryCount;i++ ) {
			generalCatalogEntryData[i] = new GeneralCatalogEntryData();
			catalog = catalogentry.substring(c_indexof[i]+catalog_title.length(), e_indexof[i]);
			entry = catalogentry.substring(e_indexof[i]+entry_title.length(), c_indexof[i+1]-3);
			generalCatalogEntryData[i].setCatalog(catalog.trim());
			generalCatalogEntryData[i].setEntry(entry.trim());
			//System.out.println("generalCatalogEntryData["+i+"]{catalog:" + generalCatalogEntryData[i].getCatalog()+",entry:" + generalCatalogEntryData[i].getEntry()+"}");
		}
		return generalCatalogEntryData;
    }

    private GeneralLanguageData[] getGeneralLanguageData(RequestBox box) {
		String language = box.getString("LanguageGeneral");
		String language_split = ",";
		int languageCount = getDataCount(language, language_split, 10)+1;
		GeneralLanguageData[] generalLanguageData = new GeneralLanguageData[languageCount];
		int[] c_indexof = new int[languageCount+1];
		String str = "";

		if ( languageCount == 1 ) {
			generalLanguageData[0] = new GeneralLanguageData();
			generalLanguageData[0].setLanguage(language.trim());
		} else if ( languageCount > 1 ) {
			for ( int i=0;i<languageCount;i++ ) {
				if ( i == 0 ) c_indexof[i] = 0;
				else c_indexof[i] = language.indexOf(language_split, language_split.length() + c_indexof[i-1]);
				c_indexof[languageCount] = language.length()+1;
			}
			for ( int i=0;i<languageCount;i++ ) {
				generalLanguageData[i] = new GeneralLanguageData();
				if ( i==0 ) str = language.substring(c_indexof[i], c_indexof[i+1]);
				else if ( i == languageCount-1 ) str = language.substring(c_indexof[i]+language_split.length());
				else str = language.substring(c_indexof[i]+language_split.length(), c_indexof[i+1]);
				generalLanguageData[i].setLanguage(str.trim());
				//System.out.println("generalLanguageData["+i+"].getLanguage():" + generalLanguageData[i].getLanguage());
			}
		}
		return generalLanguageData;
    }

    private GeneralKeywordData[] getGeneralKeywordData(RequestBox box) {
		String keyword = box.getString("KeywordsGeneral");
		String keyword_split = "|";
		int keywordCount = getDataCount(keyword, keyword_split, 10)+1;
		GeneralKeywordData[] generalKeywordData = new GeneralKeywordData[keywordCount];
		int[] c_indexof = new int[keywordCount+1];
		String str = "";

		if ( keywordCount == 1 ) {
			generalKeywordData[0] = new GeneralKeywordData();
			generalKeywordData[0].setKeyword(keyword.trim());
		} else if ( keywordCount > 1 ) {
			for ( int i=0;i<keywordCount;i++ ) {
				if ( i == 0 ) c_indexof[i] = 0;
				else c_indexof[i] = keyword.indexOf(keyword_split, keyword_split.length() + c_indexof[i-1]);
				c_indexof[keywordCount] = keyword.length()+1;
			}
			for ( int i=0;i<keywordCount;i++ ) {
				generalKeywordData[i] = new GeneralKeywordData();
				if ( i==0 ) str = keyword.substring(c_indexof[i], c_indexof[i+1]);
				else if ( i == keywordCount-1 ) str = keyword.substring(c_indexof[i]+keyword_split.length());
				else str = keyword.substring(c_indexof[i]+keyword_split.length(), c_indexof[i+1]);
				generalKeywordData[i].setKeyword(str.trim());
				//System.out.println("generalKeywordData["+i+"].getKeyword():" + generalKeywordData[i].getKeyword());
			}
		}
		return generalKeywordData;
    }

    private GeneralCoverageData[] getGeneralCoverageData(RequestBox box) {
		String coverage = box.getString("Coverage");
		String coverage_split = "|";
		int coverageCount = getDataCount(coverage, coverage_split, 10)+1;
		GeneralCoverageData[] generalCoverageData = new GeneralCoverageData[coverageCount];
		int[] c_indexof = new int[coverageCount+1];
		String str = "";

		if ( coverageCount == 1 ) {
			generalCoverageData[0] = new GeneralCoverageData();
			generalCoverageData[0].setCoverage(coverage.trim());
		} else if ( coverageCount > 1 ) {
			for ( int i=0;i<coverageCount;i++ ) {
				if ( i == 0 ) c_indexof[i] = 0;
				else c_indexof[i] = coverage.indexOf(coverage_split, coverage_split.length() + c_indexof[i-1]);
				c_indexof[coverageCount] = coverage.length()+1;
			}
			for ( int i=0;i<coverageCount;i++ ) {
				generalCoverageData[i] = new GeneralCoverageData();
				if ( i==0 ) str = coverage.substring(c_indexof[i], c_indexof[i+1]);
				else if ( i == coverageCount-1 ) str = coverage.substring(c_indexof[i]+coverage_split.length());
				else str = coverage.substring(c_indexof[i]+coverage_split.length(), c_indexof[i+1]);
				generalCoverageData[i].setCoverage(str.trim());
				//System.out.println("generalCoverageData["+i+"].getCoverage():" + generalCoverageData[i].getCoverage());
			}
		}
		return generalCoverageData;
    }

    private LifecycleContributeData[] getLifecycleContributeData(RequestBox box) {
		String contribute = box.getString("Contribute");
		String role_title = ")  Role:";
		String centity_title = "~    Centity:";
		String date_title = "~    Date:";
		int contributeCount = getDataCount(contribute, role_title, 30);
		LifecycleContributeData[] lifecycleContributeData = new LifecycleContributeData[contributeCount];
		int[] r_indexof = new int[contributeCount+1];
		int[] c_indexof = new int[contributeCount];
		int[] d_indexof = new int[contributeCount];
		String role = "";
		String centity = "";
		String date = "";

		for ( int i=0;i<contributeCount;i++ ) {
			if ( i == 0 ) {
				r_indexof[i] = contribute.indexOf(role_title);
				c_indexof[i] = contribute.indexOf(centity_title);
				d_indexof[i] = contribute.indexOf(date_title);
			} else {
				r_indexof[i] = contribute.indexOf(role_title, role_title.length() + r_indexof[i-1]);
				c_indexof[i] = contribute.indexOf(centity_title, centity_title.length() + c_indexof[i-1]);
				d_indexof[i] = contribute.indexOf(date_title, date_title.length() + d_indexof[i-1]);
			}
			r_indexof[contributeCount] = contribute.length()+1;
		}
		for ( int i=0;i<contributeCount;i++ ) {
			lifecycleContributeData[i] = new LifecycleContributeData();
			role = contribute.substring(r_indexof[i]+role_title.length(), c_indexof[i]);
			centity = contribute.substring(c_indexof[i]+centity_title.length(), d_indexof[i]);
			date = contribute.substring(d_indexof[i]+date_title.length(), r_indexof[i+1]-3);
			lifecycleContributeData[i].setLifecycle_contr_role(role.trim());
			lifecycleContributeData[i].setLifecycle_contr_date(date.trim());
			lifecycleContributeData[i].setCentity(getCentity(centity));
			//System.out.println("lifecycleContributeData["+i+"]{role:" + lifecycleContributeData[i].getLifecycle_contr_role()+",date:" + lifecycleContributeData[i].getLifecycle_contr_date()+"}");
			//System.out.println("lifecycleContributeData["+i+"]{centity:" + lifecycleContributeData[i].getCentity());
		}
		return lifecycleContributeData;
    }

    private String getCentity(String centity) {
		String centity_split = ",";
		int centityCount = 4;
		String str = "";
		int[] c_indexof = new int[centityCount+1];

		for ( int i=0;i<centityCount;i++ ) {
			if ( i == 0 ) c_indexof[i] = 0;
			else c_indexof[i] = centity.indexOf(centity_split, centity_split.length() + c_indexof[i-1]);
			c_indexof[centityCount] = centity.length()+1;
		}
		for ( int i=0;i<centityCount;i++ ) {
			if ( i==0 ) str += "NAME : "+centity.substring(c_indexof[i], c_indexof[i+1]).trim();
			else if ( i == 1 ) str += "\r\nORGANIZATION : "+centity.substring(c_indexof[i]+centity_split.length(), c_indexof[i+1]).trim();
			else if ( i == 2 ) str += "\r\nADDRESS : "+centity.substring(c_indexof[i]+centity_split.length(), c_indexof[i+1]).trim();
			else if ( i == 3 ) str += "\r\nEMAIL : "+centity.substring(c_indexof[i]+centity_split.length()).trim();
		}
		return str;
    }

    private MetaMetaCatalogEntryData[] getMetaMetaCatalogEntryData(RequestBox box) {
		String catalogentry = box.getString("CatalogueEntryMetametadata");
		String catalog_title = ")  Catalogue:";
		String entry_title = "~    Entry:";
		int catalogentryCount = getDataCount(catalogentry, catalog_title, 10);
		MetaMetaCatalogEntryData[] metaMetaCatalogEntryData = new MetaMetaCatalogEntryData[catalogentryCount];
		int[] c_indexof = new int[catalogentryCount+1];
		int[] e_indexof = new int[catalogentryCount];
		String catalog = "";
		String entry = "";

		for ( int i=0;i<catalogentryCount;i++ ) {
			if ( i == 0 ) {
				c_indexof[i] = catalogentry.indexOf(catalog_title);
				e_indexof[i] = catalogentry.indexOf(entry_title);
			} else {
				c_indexof[i] = catalogentry.indexOf(catalog_title, catalog_title.length() + c_indexof[i-1]);
				e_indexof[i] = catalogentry.indexOf(entry_title, entry_title.length() + e_indexof[i-1]);
			}
			c_indexof[catalogentryCount] = catalogentry.length()+1;
		}
		for ( int i=0;i<catalogentryCount;i++ ) {
			metaMetaCatalogEntryData[i] = new MetaMetaCatalogEntryData();
			catalog = catalogentry.substring(c_indexof[i]+catalog_title.length(), e_indexof[i]);
			entry = catalogentry.substring(e_indexof[i]+entry_title.length(), c_indexof[i+1]-3);
			metaMetaCatalogEntryData[i].setCatalog(catalog.trim());
			metaMetaCatalogEntryData[i].setEntry(entry.trim());
			//System.out.println("metaMetaCatalogEntryData["+i+"]{catalog:" + metaMetaCatalogEntryData[i].getCatalog()+",entry:" + metaMetaCatalogEntryData[i].getEntry()+"}");
		}
		return metaMetaCatalogEntryData;
    }

    private MetaMetaContributeData[] getMetaMetaContributeData(RequestBox box) {
		String contribute = box.getString("ContributeMetametadata");
		String role_title = ")  Role:";
		String centity_title = "~    Centity:";
		String date_title = "~    Date:";
		int contributeCount = getDataCount(contribute, role_title, 10);
		MetaMetaContributeData[] metaMetaContributeData = new MetaMetaContributeData[contributeCount];
		int[] r_indexof = new int[contributeCount+1];
		int[] c_indexof = new int[contributeCount];
		int[] d_indexof = new int[contributeCount];
		String role = "";
		String centity = "";
		String date = "";

		for ( int i=0;i<contributeCount;i++ ) {
			if ( i == 0 ) {
				r_indexof[i] = contribute.indexOf(role_title);
				c_indexof[i] = contribute.indexOf(centity_title);
				d_indexof[i] = contribute.indexOf(date_title);
			} else {
				r_indexof[i] = contribute.indexOf(role_title, role_title.length() + r_indexof[i-1]);
				c_indexof[i] = contribute.indexOf(centity_title, centity_title.length() + c_indexof[i-1]);
				d_indexof[i] = contribute.indexOf(date_title, date_title.length() + d_indexof[i-1]);
			}
			r_indexof[contributeCount] = contribute.length()+1;
		}
		for ( int i=0;i<contributeCount;i++ ) {
			metaMetaContributeData[i] = new MetaMetaContributeData();
			role = contribute.substring(r_indexof[i]+role_title.length(), c_indexof[i]);
			centity = contribute.substring(c_indexof[i]+centity_title.length(), d_indexof[i]);
			date = contribute.substring(d_indexof[i]+date_title.length(), r_indexof[i+1]-3);
			metaMetaContributeData[i].setMeta_contr_role(role.trim());
			metaMetaContributeData[i].setMeta_contr_date(date.trim());
			metaMetaContributeData[i].setCentity(getCentity(centity));
			//System.out.println("metaMetaContributeData["+i+"]{role:" + metaMetaContributeData[i].getMeta_contr_role()+",date:" + metaMetaContributeData[i].getMeta_contr_date()+"}");
			//System.out.println("metaMetaContributeData["+i+"]{centity:" + metaMetaContributeData[i].getCentity());
		}
		return metaMetaContributeData;
    }

    private TechnicalRequirementData[] getTechnicalRequirementData(RequestBox box) {
		String requirement = box.getString("Requirements");
		String type_title = ")  Type:";
		String name_title = "~    Name:";
		String min_title = "~    Minimum Version:";
		String max_title = "~    Maximum Version:";
		int requirementCount = getDataCount(requirement, type_title, 40);
		TechnicalRequirementData[] technicalRequirementData = new TechnicalRequirementData[requirementCount];
		int[] t_indexof = new int[requirementCount+1];
		int[] n_indexof = new int[requirementCount];
		int[] m_indexof = new int[requirementCount];
		int[] x_indexof = new int[requirementCount];
		String type = "";
		String name = "";
		String min = "";
		String max = "";

		for ( int i=0;i<requirementCount;i++ ) {
			if ( i == 0 ) {
				t_indexof[i] = requirement.indexOf(type_title);
				n_indexof[i] = requirement.indexOf(name_title);
				m_indexof[i] = requirement.indexOf(min_title);
				x_indexof[i] = requirement.indexOf(max_title);
			} else {
				t_indexof[i] = requirement.indexOf(type_title, type_title.length() + t_indexof[i-1]);
				n_indexof[i] = requirement.indexOf(name_title, name_title.length() + n_indexof[i-1]);
				m_indexof[i] = requirement.indexOf(min_title, min_title.length() + m_indexof[i-1]);
				x_indexof[i] = requirement.indexOf(max_title, max_title.length() + x_indexof[i-1]);
			}
			t_indexof[requirementCount] = requirement.length()+1;
		}
		for ( int i=0;i<requirementCount;i++ ) {
			technicalRequirementData[i] = new TechnicalRequirementData();
			type = requirement.substring(t_indexof[i]+type_title.length(), n_indexof[i]);
			name = requirement.substring(n_indexof[i]+name_title.length(), m_indexof[i]);
			min = requirement.substring(m_indexof[i]+min_title.length(), x_indexof[i]);
			max = requirement.substring(x_indexof[i]+max_title.length(), t_indexof[i+1]-3);
			technicalRequirementData[i].setRequirement_type(type.trim());
			technicalRequirementData[i].setRequirement_name(name.trim());
			technicalRequirementData[i].setMinimum_version(min.trim());
			technicalRequirementData[i].setMaximum_version(max.trim());
			//System.out.println("technicalRequirementData["+i+"]{type:" + technicalRequirementData[i].getRequirement_type()+",name:" + technicalRequirementData[i].getRequirement_name()+",min:" + technicalRequirementData[i].getMinimum_version()+",max:" + technicalRequirementData[i].getMaximum_version()+"}");
		}
		return technicalRequirementData;
    }

    private EducationalIntendedUserRolData[] getEducationalIntendedUserRolData(RequestBox box) {
		Vector vect = new Vector();
		vect = box.getVector("intend_list");
		int len = vect.size();
		EducationalIntendedUserRolData[] educationalIntendedUserRolData = new EducationalIntendedUserRolData[len];

		String[] str = null;
		if ( len > 0 ) {
			str = new String[len];
			for ( int i=0;i<len;i++ ) {
				str[i] = (String)vect.elementAt(i);
				educationalIntendedUserRolData[i] = new EducationalIntendedUserRolData();
				educationalIntendedUserRolData[i].setIntendedenduserrole(str[i].trim());
				//System.out.println("educationalIntendedUserRolData["+i+"].role[" + educationalIntendedUserRolData[i].getIntendedenduserrole()+"]");
			}
		}
		return educationalIntendedUserRolData;
	}

    private EducationalLanguageData[] getEducationalLanguageData(RequestBox box) {
		String language = box.getString("LanguageEducational");
		String language_split = ",";
		int languageCount = getDataCount(language, language_split, 10)+1;
		EducationalLanguageData[] educationalLanguageData = new EducationalLanguageData[languageCount];
		int[] c_indexof = new int[languageCount+1];
		String str = "";

		if ( languageCount == 1 ) {
			educationalLanguageData[0] = new EducationalLanguageData();
			educationalLanguageData[0].setLanguage(language.trim());
		} else if ( languageCount > 1 ) {
			for ( int i=0;i<languageCount;i++ ) {
				if ( i == 0 ) c_indexof[i] = 0;
				else c_indexof[i] = language.indexOf(language_split, language_split.length() + c_indexof[i-1]);
				c_indexof[languageCount] = language.length()+1;
			}
			for ( int i=0;i<languageCount;i++ ) {
				educationalLanguageData[i] = new EducationalLanguageData();
				if ( i==0 ) str = language.substring(c_indexof[i], c_indexof[i+1]);
				else if ( i == languageCount-1 ) str = language.substring(c_indexof[i]+language_split.length());
				else str = language.substring(c_indexof[i]+language_split.length(), c_indexof[i+1]);
				educationalLanguageData[i].setLanguage(str.trim());
				//System.out.println("educationalLanguageData["+i+"].getLanguage():" + educationalLanguageData[i].getLanguage());
			}
		}
		return educationalLanguageData;
    }

    private RelationData[] getRelationData(RequestBox box) {
		String relation = box.getString("Relation");
		String kind_title = ")  Kind:";
		String rel_title = "~    Relation:";
		int relationCount = getDataCount(relation, kind_title, 100);
		RelationData[] relationData = new RelationData[relationCount];
		int[] c_indexof = new int[relationCount+1];
		int[] e_indexof = new int[relationCount];
		String kind = "";
		String rel = "";

		for ( int i=0;i<relationCount;i++ ) {
			if ( i == 0 ) {
				c_indexof[i] = relation.indexOf(kind_title);
				e_indexof[i] = relation.indexOf(rel_title);
			} else {
				c_indexof[i] = relation.indexOf(kind_title, kind_title.length() + c_indexof[i-1]);
				e_indexof[i] = relation.indexOf(rel_title, rel_title.length() + e_indexof[i-1]);
			}
			c_indexof[relationCount] = relation.length()+1;
		}
		for ( int i=0;i<relationCount;i++ ) {
			relationData[i] = new RelationData();
			kind = relation.substring(c_indexof[i]+kind_title.length(), e_indexof[i]);
			rel = relation.substring(e_indexof[i]+rel_title.length(), c_indexof[i+1]-3);
			relationData[i].setRelation_kind(kind.trim());
			relationData[i].setRelation_description(rel.trim());
			//System.out.println("relationData["+i+"]{kind:" + relationData[i].getRelation_kind()+",rel:" + relationData[i].getRelation_description()+"}");
		}
		return relationData;
    }

    private AnnotationData[] getAnnotationData(RequestBox box) {
		String annotation = box.getString("Annotation");
		String centity_title = ")  Centity:";
		String date_title = "~    Date:";
		String desc_title = "~    Description:";
		int annotationCount = getDataCount(annotation, centity_title, 30);
		AnnotationData[] annotationData = new AnnotationData[annotationCount];
		int[] c_indexof = new int[annotationCount+1];
		int[] d_indexof = new int[annotationCount];
		int[] s_indexof = new int[annotationCount];
		String centity = "";
		String date = "";
		String desc = "";

		for ( int i=0;i<annotationCount;i++ ) {
			if ( i == 0 ) {
				c_indexof[i] = annotation.indexOf(centity_title);
				d_indexof[i] = annotation.indexOf(date_title);
				s_indexof[i] = annotation.indexOf(desc_title);
			} else {
				c_indexof[i] = annotation.indexOf(centity_title, centity_title.length() + c_indexof[i-1]);
				d_indexof[i] = annotation.indexOf(date_title, date_title.length() + d_indexof[i-1]);
				s_indexof[i] = annotation.indexOf(desc_title, desc_title.length() + s_indexof[i-1]);
			}
			c_indexof[annotationCount] = annotation.length()+1;
		}
		for ( int i=0;i<annotationCount;i++ ) {
			annotationData[i] = new AnnotationData();
			centity = annotation.substring(c_indexof[i]+centity_title.length(), d_indexof[i]);
			date = annotation.substring(d_indexof[i]+date_title.length(), s_indexof[i]);
			desc = annotation.substring(s_indexof[i]+desc_title.length(), c_indexof[i+1]-3);
			annotationData[i].setPerson(getCentity(centity));
			annotationData[i].setAnnotation_date(date.trim());
			annotationData[i].setDescription(desc.trim());
			//System.out.println("annotationData["+i+"]{date:" + annotationData[i].getAnnotation_date()+",desc:" + annotationData[i].getDescription()+"}");
			//System.out.println("annotationData["+i+"].centity:" + annotationData[i].getPerson());
		}
		return annotationData;
    }

    private ClassificationData[] getClassificationData(RequestBox box) {
		String purpose = box.getString("PurposeClassification");
		String description = box.getString("DescriptionClassification");
		String keyword = box.getString("KeywordsClassification");
		String purpose_title = ")  Purpose:";
		String description_title = ")  Description:";
		String keyword_title = ")  Keywords:";
		int classificationCount = getDataCount(purpose, purpose_title, 40);
		ClassificationData[] classificationData = new ClassificationData[classificationCount];
		int[] p_indexof = new int[classificationCount+1];
		int[] d_indexof = new int[classificationCount+1];
		int[] k_indexof = new int[classificationCount+1];
		String pur = "";
		String des = "";
		String key = "";

		for ( int i=0;i<classificationCount;i++ ) {
			if ( i == 0 ) {
				p_indexof[i] = purpose.indexOf(purpose_title);
				d_indexof[i] = description.indexOf(description_title);
				k_indexof[i] = keyword.indexOf(keyword_title);
			} else {
				p_indexof[i] = purpose.indexOf(purpose_title, purpose_title.length() + p_indexof[i-1]);
				d_indexof[i] = description.indexOf(description_title, description_title.length() + d_indexof[i-1]);
				k_indexof[i] = keyword.indexOf(keyword_title, keyword_title.length() + k_indexof[i-1]);
			}
			p_indexof[classificationCount] = purpose.length()+1;
			d_indexof[classificationCount] = description.length()+1;
			k_indexof[classificationCount] = keyword.length()+1;
		}
		for ( int i=0;i<classificationCount;i++ ) {
			classificationData[i] = new ClassificationData();
			pur = purpose.substring(p_indexof[i]+purpose_title.length(), p_indexof[i+1]-3);
			des = description.substring(d_indexof[i]+description_title.length(), d_indexof[i+1]-3);
			key = keyword.substring(k_indexof[i]+keyword_title.length(), k_indexof[i+1]-3);
			classificationData[i].setPurpose(pur.trim());
			classificationData[i].setDescription(des.trim());
			classificationData[i].setKeyword(key.trim());
			//System.out.println("classificationData["+i+"]{purpose:" + classificationData[i].getPurpose()+",description:" + classificationData[i].getDescription()+",keyword:" + classificationData[i].getKeyword()+"}");
		}
		return classificationData;
    }
}