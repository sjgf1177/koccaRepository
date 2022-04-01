<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "com.credu.library.*,com.credu.library.RequestBox" %>
<%@ page import = "com.credu.common.*" %>
	  <!----------------- 조건검색 시작 ----------------->
	  <table cellspacing="0" cellpadding="1" class="form_table_out">
		<tr>
		  <td bgcolor="#C6C6C6" align="center">

			<table cellspacing="0" cellpadding="0" class="form_table_bg" >
			  <tr>
				<td height="7"></td>
			  </tr>
			  <tr>
				<td align="center">

				  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
					<tr>
					  <td  align="left" valign="middle">
<script type="text/javascript">
var startup = 0;
function Main_s_grseq(){
	if (startup > 0)
		changes_grseq(document.form1.s_grcode.value, document.form1.s_gyear.value);
}
function Main_subjcourse(){
	var subjSearchKey = '';
	if(startup > 2) {
		if (document.form1.s_subjsearchkey == 'undefine')  subjSearchKey = '';
		else subjSearchKey = document.form1.s_subjsearchkey.value;
		changes_subjcourse(document.form1.s_grcode.value
										, document.form1.s_upperclass.value
										, document.form1.s_middleclass.value
										, document.form1.s_lowerclass.value
										, document.form1.s_grseq.value
										, subjSearchKey );
//		alert(document.form1.s_grcode.value+ " , " + document.form1.s_upperclass.value+ " , " + document.form1.s_middleclass.value+ " , " + document.form1.s_lowerclass.value+ " , " + document.form1.s_subjseq.value+ " , " +'' );
	}
	startup++;
}
</script>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<font color="red">★</font>
	교육그룹
</td><td>
	<kocca_select:select name="s_grcode" sqlNum="course.0001"  param=" "
	onChange="changes_gyear(this.value);" attr=" " selectedValue="<%= ss_grcode %>" isLoad="true" all="false" />
					  </td>
					  <td>
<font color="red">★</font>
	연도
</td><td>
	<kocca_select:select name="s_gyear" sqlNum="course.year"  param="<%= ss_grcode %>"
		onChange="changes_grseq(s_grcode.value, this.value);" afterScript="Main_s_grseq" attr=" " selectedValue="<%= ss_gyear %>" isLoad="true" all="false" />
</td><td>
<font color="red">★</font>	교육차수
</td><td colspan='2'>
	<kocca_select:select name="s_grseq" sqlNum="course.subjseq"
		param="<%=ss_grcode%>" param2="<%=ss_gyear%>"
		attr="" onChange="Main_subjcourse()" afterScript="Main_subjcourse" selectedValue="<%= ss_grseq %>" isLoad="true" all="true" />
</td></tr><tr><td>
&nbsp;&nbsp;&nbsp; 대분류
</td><td>
 <kocca_select:select name="s_upperclass" sqlNum="course.0002"  param=" "
		onChange="changes_middleclass(this.value);" attr=" " selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
</td><td>
	중분류
</td><td>
	 <kocca_select:select name="s_middleclass" sqlNum="course.0003"  param="<%= ss_upperclass %>"
		onChange="changes_lowerclass(s_upperclass.value, this.value);" afterScript="Main_s_grseq" attr=" " selectedValue="<%= ss_middleclass %>" isLoad="true" all="true" />
</td><td>
	소분류
</td><td colspan=2>
	 <kocca_select:select name="s_lowerclass" sqlNum="course.0004"  param="<%= ss_upperclass %>"  param2="<%= ss_middleclass %>"
		onChange="" afterScript="Main_s_grseq" attr=" " selectedValue="<%= ss_lowerclass %>" isLoad="true" all="true" />
					  </td>
					</tr>
					<tr>
					  <td align="left">
<%	 String s_subjsearchkey = box.getString("s_subjsearchkey");  %>
&nbsp;&nbsp;&nbsp; 과정검색
</td><td>
&nbsp;<input type="text" name="s_subjsearchkey" size="17" onkeypress="if(event.keyCode=='13') Main_subjcourse();" value="<%=s_subjsearchkey%>">
<a href="javascript:Main_subjcourse()" ><img src="/images/admin/button/search3_butt.gif" border="0"></a>
					  </td>
					  <td>
과정
</td><td colspan=2>
 <kocca_select:select name="s_subjcourse" sqlNum="subjlist1|subjlist2|subjlist3|subjlist4|subjlist5|subjlist6|subjlist7|subjlist8|subjlist9"
				param="<%=ss_grcode%>"param4="<%=ss_upperclass%>"param5="<%=ss_middleclass%>"param6="<%=ss_lowerclass%>"
				param7="<%=ss_grseq%>" param9="<%= s_subjsearchkey %>"
				onChange="changes_subjseq(s_grcode.value, s_grseq.value, s_subjcourse.value, s_gyear.value);"
				 attr="" selectedValue="<%= ss_subjcourse %>" isLoad="true" all='<%= box.getSession("isSubjAll") %>' />
</td><td>
	과정차수
</td><td>
	 <kocca_select:select name="s_subjseq" sqlNum="subjectSeq1|subjectSeq2|subjectSeq3|subjectSeq4"
				param="<%=ss_grcode%>"param2="<%=ss_grseq%>"param3="<%=ss_subjcourse%>"param4="<%=ss_gyear%>"
				onChange="" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="true" />
</td>
					</tr>
				  </table>
				</td>
				 <td  width="5%" align="center">
					 <%@ include file="/learn/admin/include/za_GoButton.jsp" %><!-- whenSelection('go') -->
				 </td>
			  </tr>
			  <tr>
				<td height="9"></td>
			  </tr>
			</table>
		  </td>
		</tr>
	  </table>
	  <!----------------- 조건검색 끝 ----------------->
