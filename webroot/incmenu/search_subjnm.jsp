
과정검색&nbsp;<input type="text" name="s_subjsearchkey" size="17" onkeypress="searchSubjnmKeyEvent(event)" value="<%=box.getString("s_subjsearchkey")%>">
<a href="javascript:goSearchSubjnm()" ><img src="/images/admin/button/search3_butt.gif" border="0" align="absmiddle"></a>



<input type="hidden" name="p_returnurlkey" value="<%=request.getRequestURI()%>">
<script>
  document.form1.s_subjsearchkey.focus();
  function searchSubjnmKeyEvent(e){
    if (e.keyCode =='13'){  goSearchSubjnm();  }
  }
  function goSearchSubjnm(){
    if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
        alert("교육그룹을 선택하세요.");
        return;
    }
    document.form1.action='/servlet/controller.study.SearchSubjNameServlet';
    document.form1.p_process.value = 'SearchSubjnmKey';
    document.form1.submit();
  }
</script>
