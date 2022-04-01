<%    
        SelectCompanyBean selectComany = new SelectCompanyBean();      
%>
È¸»ç
<select name = "s_company" onChange = "javascript:whenSelection('change')" class="inputpsearch">
<%    
        ArrayList companyList = selectComany.getCompany(box);
        for(int k = 0; k < companyList.size(); k++) {
            DataBox selectbox = (DataBox)companyList.get(k);  
        %>             
    <option value = "<%= selectbox.getString("d_comp")%>" <% if (ss_company.equals(selectbox.getString("d_comp"))) out.print("selected"); %>>
        <%= selectbox.getString("d_companynm")%></option>
<%   }  %>
</select>
