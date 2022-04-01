<script type="text/javascript">
	function poplar_search(t){
		$("#topWarp_searchText").val(t);
		totalSubjSearch1();
	}

</script>
<%@ page import= "com.credu.homepage.SearchKeywordBean" %>
<div style="margin-bottom: 55px;"></div>
<div style="padding-left: 3px;">
<h2><img src="/images/2013/common/msw_title2.gif" alt="인기검색어" /></h2>
<ul style="background-image: url('/images/2013/common/msw_bg.gif')">
	<li style="margin-top: -5px;">&nbsp;</li>
<%
	SearchKeywordBean beanppp = new SearchKeywordBean();            
	ArrayList list_popular = beanppp.selectKeywordList(box);
	
	if(list_popular.size() > 0){
        DataBox kdbox = null;
        String Keyword ="";

		for(int pi=0; pi<list_popular.size();pi++){
            kdbox = (DataBox)list_popular.get(pi);
			
			Keyword = kdbox.getString("d_keyword");
%>
    <li style="margin-left: 5px; padding-bottom: 5px;">
        <img src="/images/2013/common/msw_nb<%=pi+1 %>r.gif" alt="<%=pi+1 %>" style="vertical-align: middle;" /> 
        <a href="javascript:poplar_search('<%=Keyword %>');"><strong style="color: e2e2e2"><%=Keyword %></strong></a>
    </li>
	
<%
		}
	} else {
%>
	<li style="margin-left: 5px; margin-bottom: 5px;">
        <img src="/images/2013/common/msw_nb1r.gif" alt="1" style="vertical-align: sub;" /> <a href="javascript:poplar_search('게임');">게임</a>
    </li>
	<li style="margin-left: 5px; margin-bottom: 5px;">
        <img src="/images/2013/common/msw_nb2r.gif" alt="2" style="vertical-align: sub;" /> <a href="javascript:poplar_search('문화');">문화</a>
    </li>
	<li style="margin-left: 5px; margin-bottom: 5px;">
        <img src="/images/2013/common/msw_nb3r.gif" alt="3" style="vertical-align: sub;" /> <a href="javascript:poplar_search('방송');">방송</a>
    </li>
<%
    } 
%>
</ul>
<h2><img src="/images/2013/common/msw_bgdown.gif" alt="인기검색어" style="margin-top: 0px;" /></h2>
</div>