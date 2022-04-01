<%  SelectParam param2 = new SelectParam("start", "", false, 24, box.getHttpSession());
    
    Vector  return_bar2_vec = null;
   	
   	param2.setSelectname(incparam2.getNameprefix()+"selgubun"); param2.setOnchange(incparam2.getSelgubun_change());

    return_bar2_vec = SelectionUtil2.getSelgubunR_I(incparam2.getSelgubun(), param2);
    
	incparam2.setSelgubun((String)return_bar2_vec.get(1)); %>
	
	<%=(String)return_bar2_vec.get(0)%>
    
<%  param2.setSelectname(incparam2.getNameprefix()+"seltext"); 
    if (!incparam2.getSelgubun().equals(SelectionUtil.GPM)) { 
        param2.setOnchange(""); 
    } else { 
        param2.setOnchange(incparam2.getSeltext_change()); 
    } 
    param2.setAll(true);
    
    return_bar2_vec = SelectionUtil2.getSelTextR_I(incparam2.getGrpcomp(), incparam2.getSelgubun(), incparam2.getSeltext(), param2);
    
    incparam2.setSeltext((String)return_bar2_vec.get(1)); %>
    
	<%=(String)return_bar2_vec.get(0)%>

<%  if (incparam2.getSelgubun().equals(SelectionUtil.GPM)) {  
		param2.setSelectname(incparam2.getNameprefix()+"seldept");  
		param2.setOnchange(incparam2.getSeldept_change()); 
		param2.setAll(true); 
		
		return_bar2_vec = SelectionUtil2.getSelDeptR_I(incparam2.getGrpcomp(), incparam2.getSelgubun(), incparam2.getSeltext(), incparam2.getSeldept(), param2);
		
	    incparam2.setSeldept((String)return_bar2_vec.get(1)); %>
    
		<%=(String)return_bar2_vec.get(0)%>
		
<%  } %>  