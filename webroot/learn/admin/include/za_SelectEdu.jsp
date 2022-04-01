<%   
        SelectEduBean selectEdu = new SelectEduBean();      
%>
±³À°±×·ì
<select name = "s_grcode" onChange = "javascript:whenSelection('change')">
<%    
        ArrayList grcodeList = selectEdu.getGrcode(box);
        for(int k = 0; k < grcodeList.size(); k++) {
            DataBox selectbox = (DataBox)grcodeList.get(k);  
        %>             
    <option value = "<%= selectbox.getString("d_grcode")%>" <% if (ss_grcode.equals(selectbox.getString("d_grcode"))) out.print("selected"); %>>
        <%= selectbox.getString("d_grcodenm")%></option>
<%   }  %>
</select>

³âµµ
<select name = "s_gyear" onChange = "javascript:whenSelection('change')">
<%    
        ArrayList gyearList = selectEdu.getGyear(box);
        for(int k = 0; k < gyearList.size(); k++) {
            DataBox selectbox = (DataBox)gyearList.get(k);  
        %>
    <option value = "<%= selectbox.getString("d_gyear")%>" <% if (ss_gyear.equals(selectbox.getString("d_gyear"))) out.print("selected"); %>>
        <%= selectbox.getString("d_gyear")%></option>
<%   }  %>
</select>

<!--±³À°Â÷¼ö
<select name = "s_grseq" onChange = "javascript:whenSelection('change')">
<%    
        ArrayList grseqList = selectEdu.getGrseq(box);
        for(int k = 0; k < grseqList.size(); k++) {//System.out.println("grseqList.size() : " + grseqList.size());
            DataBox selectbox = (DataBox)grseqList.get(k);  
        %>             
    <option value = "<%= selectbox.getString("d_grseq")%>" <% if (ss_grseq.equals(selectbox.getString("d_grseq"))) out.print("selected"); %>>
        <%= selectbox.getString("d_grseqnm")%></option>
<%   }  %>
</select-->
<input type = "hidden" name="s_grseq" value="0001">