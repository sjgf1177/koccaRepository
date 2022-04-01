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
    String isAll = request.getParameter("isall");
    String type = request.getParameter("type");
    String onChange = request.getParameter("onChange");
    final String NULL = "";

    if (onChange != null && onChange.length() > 0 && !onChange.equals("null")) {
        if(onChange.indexOf('(') == -1) {
            onChange = " onClick='" +onChange+"()'";
        } else {
            onChange = " onClick='" +onChange+"'";
        }
    } else {
        onChange = NULL;
    }

    String _selected = null;
    String tmpcp = "";

    try {
        if(list != null && list.size() > 0) {
            for(int i = 0;  i < list.size(); i ++) {
                _selected = NULL;
                DataBox _temp = (DataBox)list.get(i);

                if (type.equals("1")&&!"YES".equals(isCheckBox)) {
                    if(defaultValue != null && defaultValue.trim().length() > 0 && _temp.get("d_code").equals(defaultValue)) {
                            _selected = " selected";
                    }
                    if(isLabel==null) {
                        scriptSrc.append("<option value='"+_temp.get("d_code")+"'"+_selected+">"+_temp.get("d_codenm")+"</option>");
                    }else if(isLabel!=null && (isLabel.equals("yes")||isLabel.equals("true") )){
                        scriptSrc.append("<option value='"+_temp.get("d_code")+"'"+_selected+" label='"+_temp.get("d_codenm")+"' >"+_temp.get("d_codenm")+"</option>");
                    }

                } else if (type.equals("2")||"YES".equals(isCheckBox)) {
                    if(defaultValue != null && i < defaultValue.trim().length() && defaultValue.trim().length() <= list.size() && '1'==defaultValue.charAt(i)) {
                        _selected = " checked";
                    }
                    if(isLabel==null){
                        scriptSrc.append("<input type='checkbox' id='"+objName+"' name='"+objName+"' class='"+objName+"Class' value='"+_temp.get("d_code")+"' "
                                +onChange+_selected+">"+_temp.get("d_codenm")+"&nbsp;&nbsp;");
                    }else if(isLabel!=null && (isLabel.equals("yes")||isLabel.equals("true") )){
                        scriptSrc.append("<input type='checkbox' id='"+objName+"' name=\\\""+objName+"\\\" class='"+objName+"Class' value='"+_temp.get("d_code")+"' "
                                +onChange+_selected+" label='"+_temp.get("d_codenm")+"'>"+_temp.get("d_codenm")+"&nbsp;&nbsp;");
                    }

                } else if (type.equals("2.2")) {
                    if(defaultValue != null && i < defaultValue.trim().length() && defaultValue.trim().length() <= list.size() && '1'==defaultValue.charAt(i)) {
                        _selected = " checked";
                    }
                    else if(_temp.get("d_code").equals(defaultValue)) {
                        _selected = " checked";
                    }
                    if(isLabel==null){
                        scriptSrc.append("<input type='radio' id='"+objName+"' name='"+objName+"' class='"+objName+"Class' value='"+_temp.get("d_code")+"' "
                                +onChange+_selected+" >"+_temp.get("d_codenm")+"&nbsp;&nbsp;");
                    }else if(isLabel!=null && (isLabel.equals("yes")||isLabel.equals("true") )){
                        scriptSrc.append("<input type='radio' id='"+objName+"' name='"+objName+"' class='"+objName+"Class' value='"+_temp.get("d_code")+"' "
                                +onChange+_selected+" label='"+_temp.get("d_codenm")+"'>"+_temp.get("d_codenm")+"&nbsp;&nbsp;");
                    }

                } else if (type.equals("3")) {
                    // scriptSrc.append(",{src: '"+_temp.get("d_codenm")+"("+_temp.get("d_code")+")', code:'"+_temp.get("d_code")+"', value:'"+_temp.get("d_code")+"'}");
                    scriptSrc.append(",{src: '"+_temp.get("d_codenm") + "', code:'"+_temp.get("d_code")+"', value:'"+_temp.get("d_code")+"'}");

                } else if (type.equals("4")) {
                    scriptSrc.append("<div id='"+objName+"Div' class='"+objName+"Class'>"+_temp.get("d_codenm")+"("+_temp.get("d_code")+")"
                            +"<img alt='위로' style='CURSOR:hand' src='/images/admin/templet/b_atop.gif' align='absMiddle' vspace='10' border='0' onclick=\"if($('div."+objName+"Class').index($(this).parent())>0){"
                            +       "$($('div."+objName+"Class').get($('div."+objName+"Class').index($(this).parent())-1)).appendTo($(this).parent());};\"/>"
                            +"<img alt='아래로' style='CURSOR:hand' src='/images/admin/templet/b_adown.gif' align='absMiddle' vspace='10' border='0' onclick=\"if($('div."+objName+"Class').index($(this).parent())<$('#"+objName+"Cnt').val()){"
                            +       "$($(this).parent()).appendTo($($('div."+objName+"Class').get($('div."+objName+"Class').index($(this).parent())+1)));};\"/>"
                            +"<img alt='삭제' style='CURSOR:hand' src='/images/admin/button/btn_del.gif' align='absMiddle' vspace='10' border='0' id="+objName+"Delete onclick=\"$('#"+objName+"Cnt').val($('#"+objName+"Cnt').val()-1);$(this).parent().remove();\"/><input type='hidden' name='"+objName+"' value='"+_temp.get("d_code")+"'></div>");

                } else if (type.equals("5")) {
                    scriptSrc.append(request.getParameter("rerurnFunction") + "('"+_temp.get("d_code")+"', '" +_temp.get("d_codenm")+ "');\n" );

                } else if (type.equals("6")) {
                    scriptSrc.append(request.getParameter("rerurnFunction") + "(" + _temp.getAll()+");\n");

                } else if (type.equals("7")||type.equals("8")) {

                    if (_temp.get("d_rank").equals("1")) {
                        if (i !=0 ) scriptSrc.append("</optgroup\">");
                        scriptSrc.append("<optgroup label=\'"+_temp.get("d_gubunnm") +"'\">");
                        //tmpcp = _temp.get("d_gubun");
                    }
                    scriptSrc.append("<option value='"+_temp.get("d_code")+"'"+_selected+">"+_temp.get("d_codenm")+"</option>");

                } else if (type.equals("9") || type.equals("11")) {
                    scriptSrc.append("<option value='"+_temp.get("d_code")+"'"+_selected+">"+_temp.get("d_codenm")+"</option>");

                } else if (type.equals("10")){
                    scriptSrc.append("<li><a><span class='num'>"+(i+1)+"</span>"+_temp.get("d_codenm")+"</a></li>");
                }
            }
        } else {
            if (type.equals("3")||type.equals("5")||type.equals("6")) {
            } else if (!"YES".equals(isCheckBox)) {
                scriptSrc.append("<option value=' '>결과없음</option>");
            } else {
                scriptSrc.append("결과없음");
            }
        }

    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e);
    }

    if (type.equals("5")||type.equals("6")) {
        out.println("<script type='text/javascript'><!--\n");
        out.println(scriptSrc);
        out.println("--></script>");

    } else if (type.equals("4")) {
        if ("yes".equals(request.getParameter("test"))) out.println("<script type='text/javascript' src='/7001/script/jquery-1.3.2.min.js'></script>");
        out.println("<input type='hidden' name='"+objName+"Cnt' id='"+objName+"Cnt' value='"+list.size()+"'>"+scriptSrc);

    } else if (type.equals("3")) {
        if("none".equals(isAll)) {
            out.println(objName+"_var = [" + scriptSrc.deleteCharAt(0) + "];\n");
        } else {
            out.println(objName+"_var = [{src: '전체', code:'ALL', value:'%'}" + scriptSrc + "];\n");
        }

        out.println(objName+"_insert();\n");
        out.println(objName+"_start();\n\tfunction get"+objName+"SelectCode(inValue) {\n\t\tfor ("
                        +objName+"_index = 0;"+objName+"_index < "+objName+"_var.length ;"+objName+"_index++ ) {\n\t\t\tif ("
                        +objName+"_var["+objName+"_index].src == inValue) {return "+objName+"_var["+objName+"_index].code;}\n\t\t}\n\t}\n");
    } else if (type.equals("7") || type.equals("8") ) {

        out.println("<select name=\"ss_subjseq\" multiple=\"multiple\" id=\"ss_subjseq\" style=\"width:450px\">");
        out.println(scriptSrc);
        out.println("</optgroup>");
        out.println("</select>");

    } else if (type.equals("9") || type.equals("11") ) {
        out.println("<select name=\"ss_subjcourse\" multiple=\"multiple\" id=\"ss_subjcourse\" style=\"width:550px\">");
        out.println(scriptSrc);
        out.println("</select>");

    } else if (type.equals("10")){
        out.println("<div class=\"topWrap\"><p class=\"title\"><img src=\"/images/2013/online_edu/chasi_title.gif\" alt=\"차시리스트\" /> \n");
        out.println("<span>"+list.size()+"회차</span></p> \n");
        out.println("<a href=\"javascript:lyr_close();\" class=\"closeLy\"><img src=\"/images/2013/online_edu/btn_close.gif\" alt=\"차시리스트 닫기\" /></a></div> \n");
        out.println("<div class=\"cont\"><ul> \n");
        out.println(scriptSrc);
        out.println("</ul></div>");

    } else {
        out.println(objName+"_var = \"" + scriptSrc + "\"\n");
        out.println(objName+"_insert();\n");
    }

    //  out.println("document.all.source.value=\""+afterScript+"\";");
    if (afterScript != null && afterScript.trim().length() > 0) out.println(afterScript+"();\n");
%>
