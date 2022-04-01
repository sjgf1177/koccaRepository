<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%
    String s_gadmin = box.getSession("gadmin");
    String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
    String sql = "course.0001"+v_gadmin;
    String s_userid = box.getSession("userid");
    String v_tem_grcode = box.getSession("tem_grcode");
 %>
 
	교육그룹 <kocca_select:select name="s_grcode" sqlNum="<%= sql %>"  param="<%= s_userid %>" param2="<%= s_gadmin %>"
	onChange="" attr=" " selectedValue="<%= ss_grcode %>" isLoad="true" all="true" />
	대분류 <kocca_select:select name="s_upperclass" sqlNum="course.0002"  param="<%= v_gadmin %>" param2="<%= v_tem_grcode %>"
		onChange="changes_middleclass(this.value);" attr=" " selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
	중분류 <kocca_select:select name="s_middleclass" sqlNum="course.0003"  param="<%= ss_upperclass %>"
		onChange="changes_lowerclass(s_upperclass.value, this.value);" attr=" " selectedValue="<%= ss_middleclass %>" isLoad="true" all="true" />
	소분류 <kocca_select:select name="s_lowerclass" sqlNum="course.0004"  param="<%= ss_upperclass %>"  param2="<%= ss_middleclass %>"
		onChange="whenSelection('go')" attr=" " selectedValue="<%= ss_lowerclass %>" isLoad="true" all="true" />
