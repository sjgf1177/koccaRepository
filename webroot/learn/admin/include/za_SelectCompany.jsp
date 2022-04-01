<%    
        String tmp_gadmin = box.getSession("gadmin");
        SelectCompanyBean selectComany = new SelectCompanyBean();      
%>
회사
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

<%  if( !ss_company.equals("ALL") && !ss_company.equals("") && !ss_company.equals("----")) {  %>
    구분
    <select name = "s_selgubun" onChange = "javascript:whenSelection('change')" class="inputpsearch">
    <%   if(conf.getBoolean("selgubun.gpm")) {  %>             
        <option value = "GPM" <% if (ss_selgubun.equals("GPM")) out.print("selected"); %>>사업본부별 </option>
    <%   }
            if(conf.getBoolean("selgubun.jikwi") && !tmp_gadmin.equals("K6") && !tmp_gadmin.equals("K7")) {  %>             
        <option value = "JIKWI" <% if (ss_selgubun.equals("JIKWI")) out.print("selected"); %>>직위별 </option>
    <%   }
            if(conf.getBoolean("selgubun.jikup") && !tmp_gadmin.equals("K6") && !tmp_gadmin.equals("K7")) {  %>             
        <option value = "JIKUP" <% if (ss_selgubun.equals("JIKUP")) out.print("selected"); %>>직급별 </option>
    <%   }
            if(conf.getBoolean("selgubun.jikun") && !tmp_gadmin.equals("K6") && !tmp_gadmin.equals("K7")) {  %>             
        <option value = "JIKUN" <% if (ss_selgubun.equals("JIKUN")) out.print("selected"); %>>직군별 </option>
    <%   }  %>
    </select>

        <select name = "s_seltext" onChange = "javascript:whenSelection('change')" class="inputpsearch">
        <%
            ArrayList seltextList = null;    
            
            if (ss_selgubun.equals("GPM") || ss_selgubun.equals("")) {
                seltextList = selectComany.getGpm(box);
                for(int k = 0; k < seltextList.size(); k++) {
                    DataBox selectbox = (DataBox)seltextList.get(k);  
                %>             
            <option value = "<%= selectbox.getString("d_comp")%>" <% if (ss_seltext.equals(selectbox.getString("d_comp"))) out.print("selected"); %>>
                <%= selectbox.getString("d_gpmnm")%></option>
        <%   }
            }
            else if(ss_selgubun.equals("JIKWI")) {    
                seltextList = selectComany.getJikwi(box);
                for(int k = 0; k < seltextList.size(); k++) {
                    DataBox selectbox = (DataBox)seltextList.get(k);  
                %>             
            <option value = "<%= selectbox.getString("d_jikwi")%>" <% if (ss_seltext.equals(selectbox.getString("d_jikwi"))) out.print("selected"); %>>
                <%= selectbox.getString("d_jikwinm")%></option>
        <%   }
            }
            else if(ss_selgubun.equals("JIKUP")) { 
                seltextList = selectComany.getJikup(box);
                for(int k = 0; k < seltextList.size(); k++) {
                    DataBox selectbox = (DataBox)seltextList.get(k);  
                %>             
            <option value = "<%= selectbox.getString("d_jikup")%>" <% if (ss_seltext.equals(selectbox.getString("d_jikup"))) out.print("selected"); %>>
                <%= selectbox.getString("d_jikupnm")%></option>
        <%   }
            }
            else if(ss_selgubun.equals("JIKUN")) { 
                seltextList = selectComany.getJikun(box);
                for(int k = 0; k < seltextList.size(); k++) {
                    DataBox selectbox = (DataBox)seltextList.get(k);  
                %>             
            <option value = "<%= selectbox.getString("d_jikun")%>" <% if (ss_seltext.equals(selectbox.getString("d_jikun"))) out.print("selected"); %>>
                <%= selectbox.getString("d_jikunnm")%></option>
        <%   }
            }   %>
        </select>
        
	  <%  if (ss_selgubun.equals("GPM") || ss_selgubun.equals("")) {	%>
        <select name = "s_seldept" onChange = "javascript:whenSelection('change')" class="inputpsearch">            
      <%	ArrayList deptList = selectComany.getDept(box);
                for(int k = 0; k < deptList.size(); k++) {
                    DataBox selectbox = (DataBox)deptList.get(k);  
                %>             
            <option value = "<%= selectbox.getString("d_comp")%>" <% if (ss_seldept.equals(selectbox.getString("d_comp"))) out.print("selected"); %>>
                <%= selectbox.getString("d_deptnm")%></option>
        <%   }	%>
		</select>
      <%	}	%>
<%  }   %>
