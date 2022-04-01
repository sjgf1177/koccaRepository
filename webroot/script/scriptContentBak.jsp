<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import="java.util.List,com.credu.library.DataBox" %>
<%
	StringBuffer scriptSrc = new StringBuffer();
	List list = (List)request.getAttribute("list");
	String objName = request.getParameter("objNm");
	String defaultValue = request.getParameter("dfv");
	String isLabel = request.getParameter("isLabel");
	String afterScript = request.getParameter("afterScript");
	String isCheckBox = request.getParameter("isCheckBox");
	String type = request.getParameter("type");
	String onChange = request.getParameter("onChange");
	final String NULL = ""; 
	String _selected = null;
try {
	if(list != null && list.size() > 0) {
		for(int i = 0;  i < list.size(); i ++) {
			_selected = NULL;
			DataBox _temp = (DataBox)list.get(i);
			if (type.equals("1")&&!"YES".equals(isCheckBox)) {
				if(defaultValue != null && defaultValue.trim().length() > 0 && _temp.get("d_code").equals(defaultValue)) {
						_selected = " selected";
				}
				if(isLabel==null){
					scriptSrc.append("<option value='"+_temp.get("d_code")+"'"+_selected+">"+_temp.get("d_codenm")+"</option>");
				}else if(isLabel!=null && (isLabel.equals("yes")||isLabel.equals("true") )){
					scriptSrc.append("<option value='"+_temp.get("d_code")+"'"+_selected+" label='"+_temp.get("d_codenm")+"' >"+_temp.get("d_codenm")+"</option>");
				}
			}
			else if (type.equals("2")||"YES".equals(isCheckBox)) {
				if(defaultValue != null && i < defaultValue.trim().length() && defaultValue.trim().length() <= list.size() && '1'==defaultValue.charAt(i)) {
					_selected = " checked";
				} 
				if(isLabel==null){
					scriptSrc.append("<input type='checkbox' name=\\\""+objName+"\\\" value='"+_temp.get("d_code")+"'"
							+_selected+">"+_temp.get("d_codenm")+"&nbsp;&nbsp;");
				}else if(isLabel!=null && (isLabel.equals("yes")||isLabel.equals("true") )){
					scriptSrc.append("<input type='checkbox' name=\\\""+objName+"\\\" value='"+_temp.get("d_code")+"'"
							+_selected+" label='"+_temp.get("d_codenm")+"'>"+_temp.get("d_codenm")+"&nbsp;&nbsp;");
				}
			}
			else if (type.equals("3")) {
				scriptSrc.append("<li code='"+_temp.get("d_code")+"'>"+_temp.get("d_codenm")+"</li>");
			}
		}
	}else {
		if (!"YES".equals(isCheckBox)) {
			scriptSrc.append("<option>결과없음</option>");
		}
		else scriptSrc.append("결과없음");
	}

}
catch (Exception e) {
	System.out.println("!!!!!!!!!!!" + e);
}

	out.println(objName+"_var = \"" + scriptSrc + "\"\n");
	out.println(objName+"_insert();\n");
	if (type.equals("3"))out.println(objName+"_start();\n");
//	out.println("document.all.source.value=\""+afterScript+"\";");
	if (afterScript != null && afterScript.trim().length() > 0) out.println(afterScript+"();\n");
%>

