package com.dunet.common.taglib;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class KoccaListTaglib extends TagSupport {
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
	String onChange = null;
	String attr = null;
	public void release() {
		this.name = null;
		this.selectedValue = null;
		this.param = null;
		this.sqlNum = null;
		this.attr = null;
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
	}

	public int doStartTag() throws JspTagException {
		StringBuffer html = new StringBuffer();
		html.append("<span id=\"" + this.name + "Obj\">");
		html.append("</span>\n");
		html.append("<script id=\"" + this.name + "_empty\"></script>\n");
		html.append("<script language='javascript'>\n\t");
		html.append("function change");
		html.append(this.name);
		html.append("(param");

		if(this.param1!=null) html.append(",param1");
		if(this.param2!=null) html.append(",param2");
		if(this.param3!=null) html.append(",param3");
		if(this.param4!=null) html.append(",param4");
		if(this.param5!=null) html.append(",param5");
		if(this.param6!=null) html.append(",param6");
		if(this.param7!=null) html.append(",param7");
		if(this.param8!=null) html.append(",param8");
		if(this.param9!=null) html.append(",param9");
		if(this.param10!=null) html.append(",param10");

		html.append(") {\n\t\t");
		html.append(this.name + "Obj.innerHTML = \"" + getLoading() + "\";\n\t\t");

		html.append(this.name+"_empty.src = \"/servlet/controller.common.TagServlet?");
		html.append("sqlNum=" + this.sqlNum);
		html.append("&dfv=" + selectedValue);
		html.append("&param=\"+param+\"");
		if(this.param1!=null) html.append("&param1=\"+param1+\"");
		if(this.param2!=null) html.append("&param2=\"+param2+\"");
		if(this.param3!=null) html.append("&param3=\"+param3+\"");
		if(this.param4!=null) html.append("&param4=\"+param4+\"");
		if(this.param5!=null) html.append("&param5=\"+param5+\"");
		if(this.param6!=null) html.append("&param6=\"+param6+\"");
		if(this.param7!=null) html.append("&param7=\"+param7+\"");
		if(this.param8!=null) html.append("&param8=\"+param8+\"");
		if(this.param9!=null) html.append("&param9=\"+param9+\"");
		if(this.param10!=null) html.append("&param10=\"+param10+\"");

		html.append("\";\n\t");
//		html.append("source.value += '^n' + "+this.name+ "_empty.src");
		html.append("}\n\t");

		html.append("function " + this.name + "_insert() {\n\t\t");
		html.append(this.name + "Obj.innerHTML = ");



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

		html.append("</script>\n");

		try {
			JspWriter out = pageContext.getOut();
			out.write(html.toString());
			out.flush();
			html = null;
			release();
		} catch (Exception e) {
			throw new JspTagException("JspWriter not there : " + e);
		}
		return SKIP_BODY;
	}

	public int doEndTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public String getLoading() {
		StringBuffer load = new StringBuffer();
		load.append("****조회중입니다..잠시만 기다려 주십시요.****");

		return load.toString();
	}

	public void setName(String string) {
		if (string == null) {
			string = "";
		}

		name = string;
	}

	public void setParam(String string) {
		if (string == null) {
			string = "";
		}

		param = string;
	}

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

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setAttr(String string) {
		if (string == null) {
			string = "";
		}

		attr = string;
	}

	public void setParam1(String string) {param1 = string;}
	public void setParam2(String string) {param2 = string;}
	public void setParam3(String string) {param3 = string;}
	public void setParam4(String string) {param4 = string;}
	public void setParam5(String string) {param5 = string;}
	public void setParam6(String string) {param6 = string;}
	public void setParam7(String string) {param7 = string;}
	public void setParam8(String string) {param8 = string;}
	public void setParam9(String string) {param9 = string;}
	public void setParam10(String string) {param10 = string;}

	public void setOnChange(String string) {

		onChange = string;
	}

}

