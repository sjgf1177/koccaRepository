<%   
        int classdepth = conf.getInt("subj.class.depth");
        SelectSubjBean selectSubj = new SelectSubjBean();      
%>

<%//----------------------------------------------------------  UpperClass   -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%>
대분류
<select name = "s_upperclass" onChange = "javascript:whenSelection('change')" class="inputpsearch">
<%    
        ArrayList upperList = selectSubj.getUpperClass(box);
        for(int k = 0; k < upperList.size(); k++) {
            DataBox selectbox = (DataBox)upperList.get(k);  
        %>             
    <option value = "<%= selectbox.getString("d_upperclass")%>" <% if ((box.getString("s_upperclass").equals(selectbox.getString("d_upperclass")))) out.print("selected"); %>>
        <%= selectbox.getString("d_classname")%></option>
<%   }  %>
</select>

<%//----------------------------------------------------------  MiddleClass   -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    if(classdepth > 1) {
%>
중분류
<select name = "s_middleclass" onChange = "javascript:whenSelection('change')" class="inputpsearch">
<%    
        ArrayList middleList = selectSubj.getMiddleClass(box);
        for(int k = 0; k < middleList.size(); k++) {
            DataBox selectbox = (DataBox)middleList.get(k);  
        %>             
    <option value = "<%= selectbox.getString("d_middleclass")%>" <% if ((box.getString("s_middleclass").equals(selectbox.getString("d_middleclass")))) out.print("selected"); %>>
        <%= selectbox.getString("d_classname")%></option>
<%   }  %>
</select>

<%//----------------------------------------------------------  LowerClass   -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    }
    if(classdepth > 2) {
%>
소분류
<select name = "s_lowerclass" onChange = "javascript:whenSelection('change')" class="inputpsearch">
<%    
        ArrayList lowerList = selectSubj.getLowerClass(box);
        for(int k = 0; k < lowerList.size(); k++) {
            DataBox selectbox = (DataBox)lowerList.get(k);  
        %>             
    <option value = "<%= selectbox.getString("d_lowerclass")%>" <% if ((box.getString("s_lowerclass").equals(selectbox.getString("d_lowerclass")))) out.print("selected"); %>>
        <%= selectbox.getString("d_classname")%></option>
<%   }  %>
</select>
<%
    }    
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
%>

과정
<select name = "s_subjcourse" onChange = "javascript:whenSelection('change')" class="inputpsearch">
<%    
        ArrayList subjList = selectSubj.getSubj(box);
        for(int k = 0; k < subjList.size(); k++) {
            DataBox selectbox = (DataBox)subjList.get(k);  
        %>             
    <option value = "<%= selectbox.getString("d_subj")%>" <% if ((box.getString("s_subjcourse").equals(selectbox.getString("d_subj")))) out.print("selected"); %>>
        <%= selectbox.getString("d_subjnm")%></option>
<%   }  %>
</select>

차수
<select name = "s_subjseq" onChange = "javascript:whenSelection('change')" class="inputpsearch" >
<%    
        ArrayList subjseqList = selectSubj.getSubjseq(box);
        for(int k = 0; k < subjseqList .size(); k++) {
            DataBox selectbox = (DataBox)subjseqList .get(k);  
        %>             
    <option value = "<%= selectbox.getString("d_subjseq")%>" <% if ((box.getString("s_subjseq").equals(selectbox.getString("d_subjseq")))) out.print("selected"); %>>
        <%= selectbox.getString("d_subjseq")%></option>
<%   }  %>
</select>


<%// ----------------- s_class-----------------------------%>
클래스
<select name = "s_class"  class="inputpsearch">
<%    
        ArrayList classList = selectSubj.getSubjClass(box);
        for(int k = 0; k < classList.size(); k++) {
            DataBox selectbox = (DataBox)classList.get(k);  
        %>             
    <option value = "<%= selectbox.getString("d_class")%>" <% if ((box.getString("s_class").equals(selectbox.getString("d_class")))) out.print("selected"); %>>
        <%= selectbox.getString("d_classnm")%></option>
<%   }  %>
</select>