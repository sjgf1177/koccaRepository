package com.credu.scorm;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import com.credu.scorm.*;

import java.io.*;
import java.util.*;
//import manifest.*;

public class manifestTableBean{
	
	private static String FileName="";
	
	private Vector v_depth;
	private Vector v_identifier;
	private Vector v_parentidentifier;
	private Vector v_title;
	private Vector v_resource_href;
	private Vector v_prerequisites;
	private Vector v_maxtimeallowed;
	private Vector v_timelimitaction;
	private Vector v_masteryscore;
	private Vector v_datafromlms;	
	private Vector v_metadata;
		
	private Document document;
	private Element rootEmt;	
	
	private List firstChildsEmts;
    private List ResourceChildsEmts;
	
	private Namespace default_namespace;
	private Namespace adlcp_namespace;

	private String ContentTitle;

	public manifestTableBean(){
	
		v_depth = new Vector();
		v_identifier = new Vector();
		v_parentidentifier = new Vector();
		v_title = new Vector();
		v_resource_href = new Vector();
		v_prerequisites = new Vector();
		v_maxtimeallowed = new Vector();
		v_timelimitaction = new Vector();
		v_masteryscore = new Vector();
		v_datafromlms = new Vector();
		v_metadata = new Vector();		
	}

	
	public void setXmlFile(String dir,String FileName){
				
		try{			
	        DOMBuilder builder = new DOMBuilder(false);
			
			document = builder.build(new File(dir+"/"+FileName));
			
			rootEmt = document.getRootElement();
			
			default_namespace=rootEmt.getNamespace();
			
			adlcp_namespace = rootEmt.getNamespace("adlcp");
			
		 	firstChildsEmts = rootEmt.getChildren();			
			
			int firstChildsEmt_count = firstChildsEmts.size();
			//System.out.println("firstChildsEmt_count:"+firstChildsEmt_count);
			
			for(int i=0;i< firstChildsEmt_count;i++){				
				displayEmt((Element)firstChildsEmts.get(i),1,false);
			//	System.out.println("firstChildsEmts:"+(Element)firstChildsEmts.get(i));
			}

		}catch(Exception e){
			e.printStackTrace();
		}	
	}

	public  void displayEmt(Object obj,int depth,boolean isSelf){	
		
		String depth_space = "";
		for(int i=0;i < depth ; i++){
			depth_space=depth_space+"\t";
		}			
		
		if(obj instanceof Element){
			Element tempEmt=(Element)obj;		
			
		//	System.out.println(depth_space+"[Element]  "+tempEmt.getName());

			if((tempEmt.getName()).equals("title") && ((tempEmt.getParent().getName()).equals("organization"))){					
					ContentTitle = tempEmt.getText();					
			}
			
			//Vector insert data
			//if((tempEmt.getName()).equals("title") && ((tempEmt.getParent().getName()).equals("item") || (tempEmt.getParent().getName()).equals("organization"))){
			if((tempEmt.getName()).equals("title") && ((tempEmt.getParent().getName()).equals("item"))){

				v_depth.add(String.valueOf(depth+1));
				v_title.add(tempEmt.getText());		
				v_identifier.add(tempEmt.getParent().getAttribute("identifier").getValue());

				//System.out.println("[title]:"+tempEmt.getText()+" [identifier]:"+tempEmt.getParent().getAttribute("identifier").getValue());

				if (tempEmt.getParent().getParent() != null)
				{
					if (tempEmt.getParent().getParent().getName().equals("item"))
					{
						v_parentidentifier.add(tempEmt.getParent().getParent().getAttribute("identifier").getValue());
						//System.out.println("[title]:"+tempEmt.getText()+" [Parent identifier]:"+tempEmt.getParent().getParent().getAttribute("identifier").getValue());
					}else{
						v_parentidentifier.add("");
					}
				}else{
					v_parentidentifier.add("");
				}				
				

				if(tempEmt.getParent() != null){								

					if(tempEmt.getParent().getChild("prerequisites",adlcp_namespace) != null){
						v_prerequisites.add(tempEmt.getParent().getChild("prerequisites",adlcp_namespace).getText());
					//	System.out.println(tempEmt.getParent().getChild("prerequisites",adlcp_namespace).getText());
					}else{
						v_prerequisites.add("");
					//	System.out.println("&nbsp;");
					}
					
					if(tempEmt.getParent().getChild("maxtimeallowed",adlcp_namespace) != null){
						v_maxtimeallowed.add(tempEmt.getParent().getChild("maxtimeallowed",adlcp_namespace).getText());
					//	System.out.println(tempEmt.getParent().getChild("maxtimeallowed",adlcp_namespace).getText());
					}else{
						v_maxtimeallowed.add("");
					//	System.out.println("&nbsp;");
					}
					
					if(tempEmt.getParent().getChild("timelimitaction",adlcp_namespace) != null){
						v_timelimitaction.add(tempEmt.getParent().getChild("timelimitaction",adlcp_namespace).getText());
					//	System.out.println(tempEmt.getParent().getChild("timelimitaction",adlcp_namespace).getText());
					}else{
						v_timelimitaction.add("");
					//	System.out.println("&nbsp;");
					}
					
					if(tempEmt.getParent().getChild("masteryscore",adlcp_namespace) != null){
						v_masteryscore.add(tempEmt.getParent().getChild("masteryscore",adlcp_namespace).getText());
					//	System.out.println(tempEmt.getParent().getChild("masteryscore",adlcp_namespace).getText());
					}else{
						v_masteryscore.add("");
					//	System.out.println("&nbsp;");
					}
					
					if(tempEmt.getParent().getChild("datafromlms",adlcp_namespace) != null){
						v_datafromlms.add(tempEmt.getParent().getChild("datafromlms",adlcp_namespace).getText());
					//	System.out.println(tempEmt.getParent().getChild("datafromlms",adlcp_namespace).getText());
					}else{
						v_datafromlms.add("");
					//	System.out.println("&nbsp;");
					}

					this.getHref(tempEmt);	
					this.getMetadata(tempEmt);
				}				
			}
			
			
			//Attributes name
			List tempAttrs = tempEmt.getAttributes();
			
			int tempAttrs_count = tempAttrs.size();
			
			for(int j=0;j<tempAttrs_count;j++){
			//	System.out.println(depth_space+"[Attribute]  "+ ((Attribute)tempAttrs.get(j)).getName() +"\t[attribute value]  "+((Attribute)tempAttrs.get(j)).getValue());
				
			}
			
			//Text value
			String tempText = tempEmt.getTextTrim();
			
			if(tempText != null){
				if(tempText.trim() != "" ){			
				//	System.out.println(depth_space+"[Element text]  "+ tempText);
				}
			}	
				
			//get Child Element
			List Emts = ((Element)obj).getChildren();
				
			for(int j=0;j<Emts.size();j++){				
				displayEmt((Element)Emts.get(j),depth+1,true);				
			}		
		}
	}	
	
	
	public void getHref(Element emt){
	//	System.out.println( "bbbbbbbbbbbbbbbbbbbbbbbb");
		if(emt.getParent().getName().equals("item") & emt.getParent().getAttribute("identifierref") != null){
			List resourceEmts=null;			
			for(int i=0;i<firstChildsEmts.size();i++){
				if(((Element)firstChildsEmts.get(i)).getName().equals("resources")){					
					resourceEmts = ((Element)firstChildsEmts.get(i)).getChildren();					
				}				
			}			
			Element resource;			
			
			for(int i=0;i<resourceEmts.size();i++){
				resource = (Element)resourceEmts.get(i);
				if(resource.getAttribute("href") != null ){
					if(emt.getParent().getAttribute("identifierref").getValue().equals(resource.getAttribute("identifier").getValue())){
						v_resource_href.add(resource.getAttribute("href").getValue());
					//	System.out.println(resource.getAttribute("href").getValue());
					}
				}
			}			

		}else{
			v_resource_href.add("");		
		}
	}

	public void getMetadata(Element emt){
		
		if(emt.getParent().getName().equals("item") & emt.getParent().getAttribute("identifierref") != null){
			List resourceEmts=null;			
			for(int i=0;i<firstChildsEmts.size();i++){
				if(((Element)firstChildsEmts.get(i)).getName().equals("resources")){					
					resourceEmts = ((Element)firstChildsEmts.get(i)).getChildren();					
				}				
			}			
			Element resource;	
						
			for(int i=0;i<resourceEmts.size();i++){
				resource = (Element)resourceEmts.get(i);
				if(resource.getAttribute("href") != null ){
                 
					if(emt.getParent().getAttribute("identifierref").getValue().equals(resource.getAttribute("identifier").getValue())){
					
						List MetadataEmts=null;
						int ScoCount = 0;
						MetadataEmts = ((Element)resourceEmts.get(i)).getChildren();
							 for(int j=0;j<MetadataEmts.size();j++){
                                if (ScoCount < 1 ) {  // 스쿼 메타데이타는 1나로 설정
                             
								Element tempEmt=((Element)MetadataEmts.get(j));				
							//	System.out.println("[Element]  "+tempEmt.getName());
                                         
											if(((Element)MetadataEmts.get(j)).getName().equals("metadata")){					
												v_metadata.add(((Element)MetadataEmts.get(j)).getChild("location",adlcp_namespace).getText());
											//	System.out.println( ((Element)MetadataEmts.get(j)).getChild("location",adlcp_namespace).getText());															   
												ScoCount++;
											}
								}

								if (ScoCount == 0) { // 메타데이타가 없다면 null을 넣어준다.
								    v_metadata.add("");	
								}										
							 }										
					}
				}
			}			

		}else{
			v_metadata.add("");		
		}
	}
	
	public void saveXml(Document document){				
		try {
			FileOutputStream out = new FileOutputStream("copy.xml");
			XMLOutputter serializer = new XMLOutputter();
			serializer.output(document, out);
			out.flush();
			out.close();
		}catch (IOException e) {
			System.err.println(e);
		}
	}	
	
	public Vector getDepth(){
		return v_depth;
	}
	
	public Vector getIdentifier(){
		return v_identifier;
	}

	public Vector getParentidentifier(){
		return v_parentidentifier;
	}	

	public Vector getTitle(){
		return v_title;
	}

	public Vector getResource_href(){
		return v_resource_href;
	}
	
	public Vector getPrerequisites(){
		return v_prerequisites;
	}
	
	public Vector getMaxtimeallowed(){
		return v_maxtimeallowed;
	}
	
	public Vector getTimelimitaction(){
		return v_timelimitaction;
	}
	
	public Vector getMasteryscore(){
		return v_masteryscore;
	}
	
	public Vector getDatafromlms(){
		return v_datafromlms;
	}

	public String getContentTitle(){
		return ContentTitle;
	}
	
	public String[][] getTableValue(){	
		
		int row_count=v_title.size();
		
		String[][] table=new String[row_count][12];

		System.out.println("row_countrow_countrow_countrow_countrow_count="+row_count);


		for(int i = 0; i< row_count ; i++){		
			
			table[i][0] = (String)v_title.get(i);
			table[i][1] = (String)v_resource_href.get(i);
			table[i][2] = (String)v_prerequisites.get(i);
			table[i][3] = (String)v_maxtimeallowed.get(i);
			table[i][4] = (String)v_timelimitaction.get(i);
			table[i][5] = (String)v_masteryscore.get(i);
			table[i][6] = (String)v_datafromlms.get(i);	
			table[i][7] = (String)v_metadata.get(i);
			table[i][8] = (String)v_identifier.get(i);		            		
			table[i][9] = (String)v_parentidentifier.get(i);		
			table[i][10] = "";
			table[i][11] = (String)v_depth.get(i);	


	///		System.out.println("table[i][0]table[i][0]="+table[i][0]);
	//		System.out.println("table[i][1]table[i][1]="+table[i][1]);
	//		System.out.println("table[i][2]table[i][2]="+table[i][2]);
	//		System.out.println("table[i][3]table[i][3]="+table[i][3]);
	//		System.out.println("table[i][4]table[i][3]="+table[i][4]);
	//		System.out.println("table[i][5]table[i][3]="+table[i][5]);
	//		System.out.println("table[i][6]table[i][3]="+table[i][6]);
	//		System.out.println("table[i][7]table[i][3]="+table[i][7]);
	//		System.out.println("table[i][8]table[i][3]="+table[i][8]);
	//		System.out.println("table[i][9]table[i][3]="+table[i][9]);
	//		System.out.println("table[i][10]table[i][3]="+table[i][10]);
	//		System.out.println("table[i][11]table[i][3]="+table[i][11]);
			
		}		
		return table;		
	}		
}