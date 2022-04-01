<%  SelectParam param = new SelectParam("start", "", true, 24, box.getHttpSession());
    Vector  return_vec = null; 
    if (incparam.getSelectiontype() >= SelectionUtil.GRCODE) { %>
				                교육그룹 <% param.setSelectname(incparam.getNameprefix()+"grcode"); param.setOnchange(incparam.getGrcode_change()); %><% return_vec=SelectionUtil2.getGrcodeR_I(incparam.getGrcode(), param); incparam.setGrcode((String)return_vec.get(1)); %><%=(String)return_vec.get(0)%>
<%  }
    if (incparam.getSelectiontype() >= SelectionUtil.GYEAR) { %>
                        년도 <% param.setSelectname(incparam.getNameprefix()+"gyear"); param.setOnchange(incparam.getGyear_change()); param.setAll(false); %><% return_vec=SelectionUtil2.getGyearR_I(incparam.getGrcode(), incparam.getGyear(), param); incparam.setGyear((String)return_vec.get(1)); %><%=(String)return_vec.get(0)%>
<%  }
    if (incparam.getSelectiontype() >= SelectionUtil.GRSEQ) { %>
                        교육차수 <% param.setSelectname(incparam.getNameprefix()+"grseq"); param.setOnchange(incparam.getGrseq_change()); param.setAll(true); %><% return_vec=SelectionUtil2.getGrseqR_I(incparam.getGrcode(), incparam.getGyear(), incparam.getGrseq(), param); incparam.setGrseq((String)return_vec.get(1)); %><%=(String)return_vec.get(0)%>
<%  }
    if (incparam.getSelectiontype() >= SelectionUtil.UPPERCLASS) { %>
                        분류 <% param.setSelectname(incparam.getNameprefix()+"uclass"); param.setOnchange(incparam.getUclass_change()); %><% return_vec=SelectionUtil2.getUpperClassR_I(incparam.getGrcode(), incparam.getGyear(), incparam.getGrseq(), incparam.getUclass(), param); incparam.setUclass((String)return_vec.get(1)); %><%=(String)return_vec.get(0)%>
<%  }
    if (incparam.getSelectiontype() >= SelectionUtil.SUBJCOURSE) { %>
                        과정/코스 <% param.setSelectname(incparam.getNameprefix()+"subjcourse"); param.setOnchange(incparam.getSubjcourse_change()); %><% return_vec=SelectionUtil2.getSubjCourseR_I(incparam.getGrcode(), incparam.getGyear(), incparam.getGrseq(), incparam.getUclass(), incparam.getSubjcourse(), param); incparam.setSubjcourse((String)return_vec.get(1)); %><%=(String)return_vec.get(0)%>
<%  }
    if (incparam.getSelectiontype() >= SelectionUtil.SUBJSEQ) { %>
                        차수 <% param.setSelectname(incparam.getNameprefix()+"subjseq"); param.setOnchange(incparam.getSubjseq_change()); %><% return_vec=SelectionUtil2.getSubjSeqR_I(incparam.getGrcode(), incparam.getGyear(), incparam.getGrseq(), incparam.getUclass(), incparam.getSubjcourse(), incparam.getSubjseq(), param); incparam.setSubjseq((String)return_vec.get(1)); %><%=(String)return_vec.get(0)%>
<%  }
    if (incparam.getSelectiontype() >= SelectionUtil.COMP) { %>
                        회사 <% param.setSelectname(incparam.getNameprefix()+"company"); param.setOnchange(incparam.getCompany_change()); %><% return_vec=SelectionUtil2.getGrcompR_I(incparam.getGrcode(), incparam.getGyear(), incparam.getGrseq(), incparam.getUclass(), incparam.getSubjcourse(), incparam.getSubjseq(), incparam.getCompany(), param); incparam.setCompany((String)return_vec.get(1)); %><%=(String)return_vec.get(0)%>
<%  } %>
