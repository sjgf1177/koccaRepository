<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    ArrayList list = null;

    int     v_seq      = box.getInt("p_seq");     
    String  v_process  = box.getString("p_process");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    
    int     v_num       = 0;
    int     v_checkpoin = 0;
    String  v_regid     = "";
    String  v_name      = "";
    String  v_cont      = "";
    String  v_regdt     = "";
    
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_rowcount  = 0;
    int v_upfilecnt = 0;
    int v_pageno    = box.getInt("p_pageno");

    list = (ArrayList)request.getAttribute("replyList");
    
    String s_userid   = box.getSession("userid");
   
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>의견달기 및 추천하기</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/js/portal/common.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
        document.nform1.p_pageno.value = index;
        document.nform1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
        document.nform1.p_process.value = "insertReplyPage";
        document.nform1.target="_self";
        document.nform1.submit();
    }

    //페이지 변경
    function goPage(pageNum) {
        document.nform1.p_pageno.value = pageNum;
        document.nform1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
        document.nform1.p_process.value = "insertReplyPage";
        document.nform1.target="_self";
        document.nform1.submit();
    }

    // 의견 등록
    function insertReply(){

        var bRtn = confirm("의견을 등록하시겠습니까?");

        if(bRtn){
	        document.nform1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
	        document.nform1.p_process.value = "insertReply";
	        document.nform1.target="_self";
	        document.nform1.submit();
        }
    }

    // 의견 삭제
    function deleteReply(num) {
    	var bRtn = confirm("삭제하시겠습니까?");

        if(bRtn){
	    	document.nform1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
	    	document.nform1.p_num.value = num;
	        document.nform1.p_process.value = "deleteReply";
	        document.nform1.target="_self";
	        document.nform1.submit();
        }
    }


//-->
</SCRIPT>
</head>
<body id="popup" onload="self.focus();"><!-- popup size : 637*530 -->
<form name="nform1" method="post" onsubmit="javascript:select();">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "p_pageno"  value="<%=v_pageno%>" /> 
    <input type = "hidden" name = "p_seq"     value="<%=v_seq %>" />
    <input type = "hidden" name = "p_num"     value="" />
    
    
    
    <div id="pop_header">
        <h1><img src="/images/portal/information/pop_h1_tit1.gif" alt="의견달기 및 추천하기" /></h1>
    </div>
    <div id="pop_container">
        
        <div id="contentwrap" class="message_top">
            <ul class="information_opinion">
                <li><input name="p_point" type="radio" value ="5" checked class="first"/><img src="/images/portal/ico/ico_star5.gif" alt="" /><input name="p_point" type="radio" value ="4" /><img src="/images/portal/ico/ico_star4.gif" alt="" /><input name="p_point" type="radio" value="3" /><img src="/images/portal/ico/ico_star3.gif" alt="" /><input name="p_point" type="radio" value="2" /><img src="/images/portal/ico/ico_star2.gif" alt="" /><input name="p_point" type="radio" value="1" /><img src="/images/portal/ico/ico_star1.gif" alt="" /></li>
                <li><textarea name="p_comment" class="inbox" style="width:478px; height:30px;"></textarea><a href="javascript:insertReply();" class="board_btn"><span>의견등록</span></a></li>
            </ul>

            <div class="scroll_information_opinion">
            <table class="board_list" style="width:557px">
            <colgroup><col width="73px" /><col width="307px" /><col width="85px" /><col width="70px" /></colgroup>
            <thead>
            <tr class="gray">
                <th><p>작성자</p></th>
                <th><p>의견</p></th>
                <th><p>선호도</p></th>
                <th class="end"><p>등록일</p></th>
            </tr>
            </thead>
            <tbody>
		<%
		
		if(list.size() != 0){
		  for( int i = 0; i < list.size(); i++) {
		
		    DataBox dbox = (DataBox)list.get(i);
		    
		    v_seq       = dbox.getInt("d_seq");
		    v_num       = dbox.getInt("d_num");;
		    v_checkpoin = dbox.getInt("d_checkpoin");;
		    v_regid     = dbox.getString("d_regid");
		    v_name      = dbox.getString("d_name");
		    v_cont      = dbox.getString("d_cont");
		    v_regdt     = dbox.getString("d_regdt");
		    v_dispnum   = dbox.getInt("d_dispnum");
		    v_totalpage = dbox.getInt("d_totalpage");
		    v_rowcount  = dbox.getInt("d_rowcount");
		%>  
		    <tr onmouseover="className='over'" onmouseout="className=' '">
                <td>
                    <%=v_name%>
                    <% if(s_userid.equals(v_regid)) {%>
                    <br/>
                    <a href="javascript:deleteReply('<%=v_num %>');" class="board_btn"><span>삭제</span></a>
                    <% } %>
                </td>
                <td class="ta_l"><%=v_cont%></td>
                <td><img src="/images/portal/ico/ico_star<%=v_checkpoin%>.gif" alt="" /></td>
                <td class="num"><%=FormatDate.getFormatDate(v_regdt, "yyyy.MM.dd")%></td>
            </tr>
		<%      }
		    }else{%>
		      <tr> 
		        <td colspan ='4'>등록된 내용이 없습니다.</td>
		      </tr>
		<%  }   %>
            </tbody>
            </table>
            
        <!-- 페이징 영역 종료 -->
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
        <!-- 페이징 영역 종료 -->
            </div>
            
        </div>
    </div>
    <div id="pop_footer">
        <p class="f_btn"><a href="javascript:self.close();" class="btn_gr"><span>닫기</span></a></p>
    </div>
</form>
</body>
</html>          