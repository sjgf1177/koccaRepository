<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process    = box.getStringDefault("p_process","gomsmemberPop");
    String  v_static_cmuno = box.getString("p_static_cmuno");  
    String  v_cmuno = box.getString("p_cmuno");
    String  v_pgm = box.getString("p_pgm");
    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
    String  v_cbxflag    = box.getStringDefault("p_cbxflag","Y");
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");


    String  content      = box.getString("content");
    String  width = "600";
    String  height = "200";
    int v_dispnum    = 0;
    int v_totalpage  = 0;
    int v_rowcount   = 1;
    int v_upfilecnt  = 0;
    int v_pageno             = box.getInt("p_pageno");
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row")); 

    ArrayList list       = (ArrayList)request.getAttribute("list");
    
    String sInputTag    = "";
    String sClassName   = "";
    
    if(v_cbxflag.equals("Y")){
    	sInputTag = "checkbox";
    	sClassName = "va_up1";
    } else if(v_cbxflag.equals("R")){
    	sInputTag = "radio";
        sClassName = "";
    }

%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>회원찾기</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
<% if ("Y".equals(v_cbxflag)){%>
//페이지이동
function goPage(pageNum) {
    document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.community.CommunityPopUpServlet";
    document.form1.p_process.value = "gomsmemberPop";
    document.form1.submit();
}

//조회
function uf_searchOK(pageNum) {
    if(document.form1.p_searchtext.value ==''){
       alert('검색어를 입력하여야합니다.');document.form1.p_searchtext.focus();return;
    }

    document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.community.CommunityPopUpServlet";
    document.form1.p_process.value = "gomsmemberPop";
    document.form1.submit();
}


//확인
function uf_sendOK() {
   var rowcnt = <%=list.size()%>;
   var userid="";
   var kor_name="";
   var email="";
   var cnt=0;

   if(rowcnt>0){
          if(rowcnt ==1){
             if(document.form1.p_list_userid.checked==true){
                 userid   +=document.form1.p_list1_userid.value+"/";
                 kor_name +=document.form1.p_list1_kor_name.value+"/";
                 email    +=document.form1.p_list1_email.value+"/";
                 cnt++;
             }
          } else {
             for(var i=0;i<rowcnt;i++){
                 if(document.form1.p_list_userid[i].checked==true){
                     userid   +=document.form1.p_list1_userid[i].value+"/";
                     kor_name +=document.form1.p_list1_kor_name[i].value+"/";
                     email    +=document.form1.p_list1_email[i].value+"/";
                     cnt++;
                 }
             }  
          }
   }

    if(cnt<1){
        alert("선택된 회원이 없습니다.");return;
    }
    opener.document.form1.p_tmp_userid.value=userid;
    opener.document.form1.p_kor_name.value=kor_name;
    opener.document.form1.p_email.value=email;
    this.close();
}
//전체선택체크
function uf_allCheck(){
   var rowcnt = <%=list.size()%>;
   if(rowcnt>0){
      if(document.form1.allcheck.checked == true){
          if(rowcnt ==1){
             document.form1.p_list_userid.checked=true;
          } else {
             for(var i=0;i<rowcnt;i++){
                document.form1.p_list_userid[i].checked=true;
             }  
          }
      }else {
          if(rowcnt ==1){
             document.form1.p_list_userid.checked=false;
          } else {
             for(var i=0;i<rowcnt;i++){
                document.form1.p_list_userid[i].checked=false;
             }  
          }

      }
   }
}
<%} else {%>
//페이지이동
function goPage(pageNum) {
    document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.community.CommunityPopUpServlet";
    document.form1.p_process.value = "gomsmembernonePop";
    document.form1.submit();
}

//조회
function uf_searchOK() {
    if(document.form1.p_searchtext.value ==''){
       alert('검색어를 입력하여야합니다.');document.form1.p_searchtext.focus();return;
    }

    document.form1.p_pageno.value = 1;
    document.form1.action = "/servlet/controller.community.CommunityPopUpServlet";
    document.form1.p_process.value = "gomsmembernonePop";
    document.form1.submit();
}

function uf_sendOK() {
	<% if(sInputTag.equals("radio")){ %>
    var inputAry = document.getElementsByName("p_list_userid");
    var useridAry = document.getElementsByName("p_list1_userid");
    var usernmAry = document.getElementsByName("p_list1_kor_name");
    var emailAry = document.getElementsByName("p_list1_email");

    var userid = "";
    var usernm = "";
    var email  = "";

    var aryLen = inputAry.length;
    var cnt = 0;
    for(var i = 0 ; i < aryLen ; i++) {
    	if(inputAry[i].checked){
        	userid = useridAry[i].value;
        	usernm = usernmAry[i].value;
            email  = emailAry[i].value;
    	}
    }

    opener.document.form1.p_tmp_userid.value=userid;
    opener.document.form1.p_kor_name.value=usernm;
    opener.document.form1.p_email.value=email;
    this.close();
    
    <% } %>
}


<%}%>
//-->
</script>
</head>
<body id="popup" onload="self.focus();"><!-- popup size : 750* -->
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process"     value=""/>
<input type = "hidden" name = "p_userid"      value = ""/>
<input type = "hidden" name = "p_static_cmuno"      value = "<%=v_static_cmuno%>"/>
<input type = "hidden" name = "p_cmuno"      value = "<%=v_cmuno%>"/>
<input type = "hidden" name = "p_pageno"      value = ""/>
<input type = "hidden" name = "p_pgm"      value = "<%=v_pgm%>"/>
<input type = "hidden" name = "p_cbxflag"      value = "<%=v_cbxflag%>"/>

    <div id="pop_header">
        <h1><img src="/images/portal/community/pop_h1_tit4.gif" alt="커뮤니티" /></h1>
    </div>
    <div id="pop_container">
        <div id="contentwrap" class="message_top">
            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_memsearch.gif" alt="회원찾기" class="fl_l" /> </h3>
            <p class="searchbox" >
                <select name="p_select">
                    <option value="userid"   <%if("userid".equals(v_select)) out.print(" selected");%>>아이디</option>
                    <option value="kor_name" <%if("kor_name".equals(v_select)) out.print(" selected");%>>성명</option>
                </select>
                <input name="p_searchtext" type="text" class="inbox" style="width:285px;" value="<%=v_searchtext%>" onkeypress="javascript:fnKeyPressEnter(event, 'uf_searchOK');"/>
                <input type="image" src="/images/portal/btn/btn_serbox_search.gif" class="va_m mg_r12" alt="검색" onclick="uf_searchOK();"/>
            </p>

            <table class="list">
            <!--[if ie]><colgroup><col width="50px" /><col width="122px" /><col width="175px" /><col width="127px" /><col width="140" /></colgroup><![endif]-->
            <colgroup><col width="50px" /><col width="122px" /><col width="155px" /><col width="127px" /><col width="140" /></colgroup>
            <thead>
            <tr>
                <th class="end">선택</th>
                <th class="end">성명</th>
                <th class="end">이메일</th>
                <th class="end">등급</th>
                <th class="end">가입일</th>
            </tr>
            </thead>
            <tbody>
            <%  
            if(list.size() != 0){
                for(int i = 0; i < list.size(); i++) {
                	DataBox dbox = (DataBox)list.get(i);
                	v_totalpage = dbox.getInt("d_totalpage");
                    String v_title=dbox.getString("d_title");
                    String v_name=dbox.getString("d_kor_name");
                    
            %>
			<input type=hidden name=p_list1_userid value="<%=dbox.getString("d_userid")%>">
			<input type=hidden name=p_list1_kor_name value="<%=dbox.getString("d_kor_name")%>">
			<input type=hidden name=p_list1_email value="<%=dbox.getString("d_email")%>">
            <tr>
                <td class="end"><input type="<%=sInputTag %>" name="p_list_userid" class="<%=sClassName %>" value="<%=dbox.getString("d_userid")%>"/></td>
                <td class="end"><%=v_name%></td>
                <td class="ta_l end"><%=dbox.getString("d_email")%></td>
                <td class="end"><%=dbox.getString("d_grade_kor_nm")%></td>
                <td class="num end"><%=FormatDate.getFormatDate(dbox.getString("d_license_dte"), "yyyy.MM.dd")%></td>
            </tr>
            <%    }
                } else {%>
            <tr> 
              <td colspan=5>등록 된 회원이 존재하지 않습니다..</td>
            </tr>
            <% }%>
            </tbody>
            </table>
            
            <p class="list_btn"><a href="javascript:uf_sendOK();" class="btn_gr"><span>추가</span></a></p>
            
        <!-- 페이징 영역 종료 -->
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 5, v_rowcount) %>
        <!-- 페이징 영역 종료 -->
        </div>
    </div>
    <div id="pop_footer">
        <p class="f_btn"><a href="javascript:self.close();" class="btn_gr"><span>닫기</span></a></p>
    </div>
</form>
</body>
</html>
