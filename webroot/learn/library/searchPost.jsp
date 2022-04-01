<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");  
    String v_process    =  box.getString("p_process");
    String v_zipcode    = "";
    String v_sido       = "";
    String v_gugun      = "";
    String v_dong       = "";
    String v_bunji      = "";
    String v_post1      = "";
    String v_post2      = "";
    String v_addr       = "";
    int    idx          =  0;
    ArrayList list          =  null;
    
	String v_postgubun = box.getString("p_postgubun");

    if(v_process.equals("SearchPostAtOpenWin")){
        list = (ArrayList)request.getAttribute("SearchPost");    
    }    
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>우편번호검색</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script language="JavaScript">
<!--
    function text_enter(e)
    {
        if (e.keyCode =='13'){	whenInsetDong();  }
    } 
    function whenInsetDong(){
        if (document.form1.p_dong.value =="") {
            document.form1.p_process.value ="SearchPostOpenPage";
            alert("검색어를 입력해 주세요!");
        } else {
            document.form1.target = "_self";
            document.form1.action="/servlet/controller.library.PostSearchServlet";
            document.form1.p_process.value = "SearchPostAtOpenWin";
            document.form1.submit();
        }
    }

    /*function whenSelectZipcode(post1,post2,addr,gubun){
		if( gubun == "1" ){
			opener.document.mform1.p_post1.value = post1;
			opener.document.mform1.p_post2.value = post2;
			opener.document.mform1.p_home_addr1.value = addr;
			opener.document.mform1.p_home_addr2.focus();
			self.close();
		}else if( gubun == "2" ){
			opener.document.mform1.p_comp_post1.value = post1;
			opener.document.mform1.p_comp_post2.value = post2;
			opener.document.mform1.p_comp_addr1.value = addr;
			opener.document.mform1.p_comp_addr2.focus();
			self.close();
		}else{alert('우편번호를 선택하세요');}
    }*/ 
	
	 function selectPost(post1, post2, addr, postgubun) {
        opener.receivePost(post1, post2, addr, postgubun);
        self.close();
    }



    
    function dong_focus() {
      form1.p_dong.focus();
    }    
//-->
</script>    
</head>
<body id="popup"><!-- popup size : 480* -->

<form name = "form1" method = "post">
  <input type="hidden" name="p_process">
  <input type="hidden" name="p_zipcode">
  <input type="hidden" name="p_postgubun" value="<%=v_postgubun%>">
    <div id="pop_header">
        <h1><img src="/images/portal/information/pop_h1_tit5.gif" alt="우편번호 검색" /></h1>
    </div>
    <div id="pop_container">
        <div id="contentwrap" class="message_top">
            <p class="">찾고자하는 동/읍/면 이름을 입력하세요.  예)백석동,역삼</p>
            <div class="searchbox"><div class="left"><p>
                <strong>우편번호 검색</strong>
                <input name="p_dong" type="text" class="inbox" style="width:230px;" onkeypress="javascript:fnKeyPressEnter(event, 'whenInsetDong');"/>
                <input type="image" src="/images/portal/btn/btn_serbox_search.gif" class="va_m mg_r12" alt="검색" onclick="whenInsetDong()"/>
            </p></div></div>

            <table class="list">
            <colgroup><col width="20%" /><col width="80%" /></colgroup>
            <thead>
            <tr>
                <th class="end">우편번호</th>
                <th class="end">주소</th>
            </tr>
            </thead>
            <tbody>
			<%
			if(list != null && list.size()>0) {
			    for(int i = 0; i < list.size(); i++) {
	                PostSearchData data    = (PostSearchData)list.get(i);	
	                v_zipcode           =  data.getZipcode();
	                v_sido              =  data.getSido();
	                v_gugun             =  data.getGugun();
	                v_dong              =  data.getDong();
	                v_bunji             =  data.getBunji();      
	                
	                idx = v_zipcode.indexOf("-");
	                v_post1             = v_zipcode.substring(0, idx);
	                v_post2             = v_zipcode.substring(idx+1);
	                v_addr              = v_sido+" "+v_gugun+" "+v_dong;
			%>
			<tr>
                <td class="end"><a href="javascript:selectPost('<%=v_post1%>','<%=v_post2%>','<%=v_addr%>','<%=v_postgubun%>')"><%=v_zipcode%></a></td>
                <td class="ta_l end"><%=v_sido%>&nbsp;<%=v_gugun%>&nbsp;<%=v_dong%>&nbsp;<%=v_bunji%></td>
            </tr>
            <%  } 
            } else { %>
            <tr>
                <td colspan=2>검색된 주소가 없습니다.</td>
            </tr>
            <%} %>
            </tbody>
            </table>
        </div>
    </div>
    <div id="pop_footer">
        <p class="f_btn"><a href="javascript:self.close();" class="btn_gr"><span>닫기</span></a></p>
    </div>
</body>
</html>