package com.dunet.common.taglib;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class KoccaSelectTaglib extends TagSupport {
	private static final long serialVersionUID = 3182390987123239369L;
	String name = null;
	String param = null;
	String param1 = null ;
	String param2 = null ;
	String param3 = null ;
	String param4 = null ;
	String param5 = null ;
	String param6 = null ;
	String param7 = null ;
	String param8 = null ;
	String param9 = null ;
	String param10 = null ;
	String selectedValue = null;
	String sqlNum = null;
	String isLoad = null;
	String onChange = null;
	String attr = null;
	String all = null;
	String afterScript = null;
	String isCheckBox = null;
	String type = "1";
	String styleClass = "";
	String nextEventname = null;
	@Override
	public int doEndTag() throws JspTagException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspTagException {
		StringBuffer html = new StringBuffer();
		try{
			
			type = type == null ? "1" : type;
			
			//html.append("<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>\n");
			if(type.equals("3")) {
				html.append("<input id='"+this.name+"' type='hidden' name='"+this.name+"' value='"+selectedValue+"'>\n");
				html.append("<script type='text/javascript'>\n");
				html.append("	function select"+this.name+"(selectedValue) {\n");
				html.append("		$('#"+this.name+"').val(  get"+this.name+"SelectCode(selectedValue)  );\n");
				html.append(onChange);
				html.append(";\n}\n");
				html.append("</script>\n");
				html.append("<link rel='stylesheet' type='text/css' href='/css/jquery_menu.css' />\n");
				html.append("<script type='text/javascript' src='/script/jquery.menu.min.js'></script>\n");
			}
			html.append("<span id=\""+this.name+"Obj\" style='background:;padding:0 0 0 0;'></span>\n");
			html.append("<script id=\""+this.name+"_empty\"></script>\n");
			html.append("<script language='javascript'>\n	");
			html.append("function change"+this.name+getParamList());
			html.append(" {\n");
			/** script의 변수 선언	*/
			html.append(getDefaultVar());
			// html.append("		this.name.innerHtml = \""+getLoading()+"\";\n		");
			html.append(		this.name+"_empty.src = \""+getSrcLink()+"\";\n	");

			html.append("}\n	");
			if(type.equals("3")) {
				html.append("function "+this.name+"_start() {\n		");
				/** script의 변수 선언	*/
				html.append(getDefaultVar());
				html.append("var options = {minWidth: 120, arrowSrc: '/images/portal/btn/btn_select_down.gif', copyClassAttr: true, onClick: function(e, menuItem){select"+name+"($(this).text());}};\n			$('#"+name+">img').menu(options, "+this.name+"_var);\n		}\n	");
			}

			html.append("function "+this.name+"_insert() {\n		");
			/** script의 변수 선언	*/
			html.append(getDefaultVar());
			html.append(this.name+"Obj.innerHTML = ");

			if(!"YES".equals(isCheckBox)&&type.equals("1")) {
				html.append(getSelectBox());
			} else if ("YES".equals(isCheckBox)||type.equals("2")||type.equals("2.2")) {
				html.append(this.name+"_var;\n	}");
			}
			else if ("3".equals(type)) {
				html.append("\"<table "+styleClass+" ><thead><tr><td>"+attr+"</td><td><span id='"+name+"'>&nbsp;<img src='/images/portal/btn/btn_select_down.gif' style='CURSOR:hand' alt='"+(selectedValue!=null&&selectedValue.trim().length()>0&&!selectedValue.trim().equals("ALL") ?"필터링동작중("+selectedValue+")":"")+"'/></span></td></tr></thead></table>\";\n	}");
			}

			if ((isLoad != null) &&
					(isLoad.trim().toUpperCase().equals("TRUE") ||
							isLoad.trim().toUpperCase().equals("YES"))) {

				html.append("change");
				html.append(this.name);
				html.append("('");
				html.append(this.param);
				if(param1!=null){	html.append("','"+this.param1);}
				if(param2!=null){	html.append("','"+this.param2);}
				if(param3!=null){	html.append("','"+this.param3);}
				if(param4!=null){	html.append("','"+this.param4);}
				if(param5!=null){	html.append("','"+this.param5);}
				if(param6!=null){	html.append("','"+this.param6);}
				if(param7!=null){	html.append("','"+this.param7);}
				if(param8!=null){	html.append("','"+this.param8);}
				if(param9!=null){	html.append("','"+this.param9);}
				if(param10!=null){html.append("','"+this.param10);}
				html.append("');\n");
			}

			html.append("</script>\n");
			//		System.out.println(html);	
		}catch(Exception e){
			e.printStackTrace();
		}
		

		try {
			JspWriter out = pageContext.getOut();
			out.write(html.toString());
			out.flush();
			html = null;
			release();
		} catch (Exception e) {
			throw new JspTagException("JspWriter not there : "+e);
		}
		return SKIP_BODY;
	}

	public String getLoading() {
		StringBuffer load = new StringBuffer();
		if("1".equals(type)&&!"YES".equals(isCheckBox)) {
			load.append("<select id='"+this.name+"' name='"+this.name+"' "+styleClass+"><option>Loding......</option></select>");
		} else {
			load.append("생성중입니다..잠시만 기다려 주십시요.");
		}

		return load.toString();
	}

	public String getSelectBox() {
		StringBuffer html = new StringBuffer();
		String temp = "";

		if ((this.attr != null) && (this.attr.trim().length() > 0)) {
			temp = this.attr;
		}


		html.append("\"<select id='"+this.name+"' name='"+this.name+"' "+styleClass);
		if (onChange != null && onChange.length()>0) {
			html.append(" onChange=\\\""+onChange+"\\\"");
		}
		html.append(temp);
		if (all!=null && ( all.trim().toUpperCase().equals("TRUE") || all.trim().toUpperCase().equals("YES"))) {
			html.append("><option value='ALL'>-- 전체 --</option>\"+");
		} else if(all!=null && all.trim().toUpperCase().equals("NONE")) {
			html.append(">\"+");
		} else if(all!=null && all.equals("tutor")) {
			html.append("><option value='..........'>-- 선택 --</option>\"+");
		} else {
			html.append("><option value=''>-- 선택 --</option>\"+");
		}

		html.append(this.name+"_var");
		html.append("+\"</select>\"\n	");
		html.append("}\n	");
		return html.toString();
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	@Override
	public void release() {
		this.name = null;
		this.selectedValue = null;
		this.param = null;
		this.sqlNum = null;
		this.isLoad = null;
		this.attr = null;
		this.all = null;
		this.param1 = null ;
		this.param2 = null ;
		this.param3 = null ;
		this.param4 = null ;
		this.param5 = null ;
		this.param6 = null ;
		this.param7 = null ;
		this.param8 = null ;
		this.param9 = null ;
		this.param10 = null ;
		this.onChange = null;
		this.afterScript = null;
		this.isCheckBox = null;
		this.type = null;
		this.styleClass = null;
		this.nextEventname = null;
	}

	public void setAfterScript(String string) {afterScript = string;}

	public void setAll(String string) {all = string;}

	public void setAttr(String string) {
		if (string == null) {
			string = "";
		}

		attr = string;
	}

	public void setIsCheckBox(String string) {
		if (string != null && string.length()>0 && !("N".equals(string.toUpperCase())  || "NO".equals(string.toUpperCase()) || "FALSE".equals(string.toUpperCase()))) {
			isCheckBox = "YES";
		} else {
			isCheckBox = "NO";
		}
	}

	public void setIsLoad(String string) {
		if (string == null) {
			string = "";
		}

		isLoad = string;
	}

	public void setName(String string) {
		if (string == null) {
			string = "";
		}

		name = string;
	}
	public void setOnChange(String string) {

		onChange = string;
	}
	public void setParam(String string) {
		if (string == null) {
			string = "";
		}

		param = string;
	}
	public void setParam1(String string) {param1 = string;}
	public void setParam10(String string) {param10 = string;}
	public void setParam2(String string) {param2 = string;}
	public void setParam3(String string) {param3 = string;}
	public void setParam4(String string) {param4 = string;}
	public void setParam5(String string) {param5 = string;}
	public void setParam6(String string) {param6 = string;}
	public void setParam7(String string) {param7 = string;}

	public void setParam8(String string) {param8 = string;}
	public void setParam9(String string) {param9 = string;}
	public void setNextEventname(String string) { nextEventname = string;		}

	public void setSelectedValue(String string) {
		if (string == null) {
			string = "";
		}

		selectedValue = string;
	}

	public void setSqlNum(String string) {
		if (string == null) {
			string = "";
		}

		sqlNum = string;
	}
	public void setStyleClass(String string) {
		if (string != null && string.length()>0) {
			if(selectedValue!=null&&selectedValue.trim().length()>0&&!selectedValue.trim().equals("ALL") ) {
				styleClass = " class='"+string+"_onSelected'";
			} else {
				styleClass = " class='"+string+"'";
			}
		} else {
			styleClass="";
		}
	}

	public void setType(String string) {
		if (string == null || string.trim().length()==0) {
			type = "1";
		} else {
			type = string;
		}
	}
	private String getParamList(){
		StringBuffer html = new StringBuffer();
		html.append("(param");

		if(this.param1!=null) {
			html.append(",param1");
		}
		if(this.param2!=null) {
			html.append(",param2");
		}
		if(this.param3!=null) {
			html.append(",param3");
		}
		if(this.param4!=null) {
			html.append(",param4");
		}
		if(this.param5!=null) {
			html.append(",param5");
		}
		if(this.param6!=null) {
			html.append(",param6");
		}
		if(this.param7!=null) {
			html.append(",param7");
		}
		if(this.param8!=null) {
			html.append(",param8");
		}
		if(this.param9!=null) {
			html.append(",param9");
		}
		if(this.param10!=null) {
			html.append(",param10");
		}
		html.append(")");
		return html.toString();
	}
	private String getSrcLink() {
		StringBuffer html = new StringBuffer();

		html.append("/servlet/controller.common.TagServlet?");
		html.append("sqlNum="+this.sqlNum);
		html.append("&dfv="+selectedValue);
		html.append("&type="+type+"&isCheckBox="+isCheckBox+"&objNm="+this.name);
		html.append("&param=\"+param+\"");
		if(this.all!=null) {
			html.append("&isall="+all+"");
		}
		if(this.param1!=null) {
			html.append("&param1=\"+param1+\"");
		}
		if(this.param2!=null) {
			html.append("&param2=\"+param2+\"");
		}
		if(this.param3!=null) {
			html.append("&param3=\"+param3+\"");
		}
		if(this.param4!=null) {
			html.append("&param4=\"+param4+\"");
		}
		if(this.param5!=null) {
			html.append("&param5=\"+param5+\"");
		}
		if(this.param6!=null) {
			html.append("&param6=\"+param6+\"");
		}
		if(this.param7!=null) {
			html.append("&param7=\"+param7+\"");
		}
		if(this.param8!=null) {
			html.append("&param8=\"+param8+\"");
		}
		if(this.param9!=null) {
			html.append("&param9=\"+param9+\"");
		}
		if(this.param10!=null) {
			html.append("&param10=\"+param10+\"");
		}
		if(this.afterScript!=null) {
			html.append("&afterScript="+this.afterScript);
		}
		if (onChange != null && onChange.length() > 0 && onChange.indexOf("(")==-1){
			html.append("&onChange="+onChange);
		}
		return html.toString();
	}

	/**
	 * 기본 변수들을 생성한다. - javascript기준
	 * @return
	 */
	private String getDefaultVar(){
		StringBuffer html = new StringBuffer();
		html.append(" var "+this.name+"Obj=document.getElementById('"+this.name+"Obj');\n");
		html.append(" var "+this.name+"_empty=document.getElementById('"+this.name+"_empty');\n");
		return html.toString();
	}
}

