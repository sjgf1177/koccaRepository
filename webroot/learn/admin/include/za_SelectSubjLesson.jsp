<%   
        SelectSubjBean selectSubj = new SelectSubjBean();      
%>

<%//---------------------------------------------------------- SubjLesson  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%>
Â÷½Ã
<select name = "s_subjlesson" onChange = "" class="inputpsearch">
<%    
        ArrayList subjLessonList = selectSubj.getSubjLesson(box);
        for(int k = 0; k < subjLessonList.size(); k++) {
            DataBox selectbox = (DataBox)subjLessonList.get(k);  
        %>             
    <option value = "<%= selectbox.getString("d_lesson")%>" <% if ((box.getString("d_lesson").equals(selectbox.getString("d_lesson")))) out.print("selected"); %>>
        <%= selectbox.getString("d_sdesc")%></option>
<%   }  %>
</select>

