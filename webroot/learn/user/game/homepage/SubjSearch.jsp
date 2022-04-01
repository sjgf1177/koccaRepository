<%@ page contentType = "text/html;charset=euc-kr" %>
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 과정검색 엔터처리
    function search_enter(e)  {
        if (e.keyCode =='13'){  subjSearch();}
    }

    // 과정검색
    function subjSearch() {
        if (document.subjSearch.p_lsearchtext.value=="") {
            alert("검색어를 입력해주세요");
            return;
        }

        document.subjSearch.action = "/servlet/controller.propose.MainSubjSearchServlet";
        document.subjSearch.p_process.value = "SubjSearch";
        document.subjSearch.submit();
    }
//-->
</SCRIPT>
			<!-- 과정검색 시작 -->
    <form name="subjSearch" method="post" action="javascript:return;">
      <input type='hidden' name='p_process'>
      <input type='hidden' name='p_lsearch' value="subjnm">
			<table cellpadding="0" cellspacing="0" width="190">
			  <tr>
				<td valign="top" style="border-bottom:1px solid #d7d7d7;height:25px;">
				  <img src="/images/user/game/homepage/type1/search_img01.gif" align="absmiddle">
				  <input name="p_lsearchtext" type="text" style="width:100px;height:18px;border:1px solid #c4cad1;background-color:#e1e1e1;">
				  <a href="javascript:subjSearch()"><img src="/images/user/game/homepage/type1/search_go.gif" align="absmiddle"></a><br>
			  </tr>
			  <tr>
				<td height="10"></td>
			  </tr>
			</table>
            <!--table cellpadding="0" cellspacing="0" width="190">
            <tr>
            <td style="border-bottom:1px solid #d7d7d7;">
                <img src="/images/user/game/homepage/type1/search_img01.gif" align="absmiddle"><input type="text" style="width:104px;height:19px;border:1px solid #c4cad1;background-color:#e1e1e1;"  name="p_lsearchtext" value="" onkeypress="search_enter(event)">
                <a href="javascript:subjSearch()"><img src="/images/user/game/homepage/type1/search_go.gif" align="absmiddle"></a><br>
            </tr>
            <tr height="10"><td></td></tr>
            </table-->
    </form>
			<!-- 과정검색 끝 -->