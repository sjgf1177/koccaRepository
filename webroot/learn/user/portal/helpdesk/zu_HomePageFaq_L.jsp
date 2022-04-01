<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  v_process     = box.getString("p_process");
    String  v_searchtxt   = box.getString("p_searchtxt");
    String  v_faqcategory = "";
    v_faqcategory = box.getString("faqcategory");

    int     v_fnum       = 0, v_totalpage = 0, v_rowcount = 1;
    int     v_pageno     = box.getInt("p_pageno");
    String  v_title      = "";
    String  v_contents   = "";
    String  v_indate     = "";
	String  v_searchtext = box.getString("p_searchtxt");
    ArrayList list       = (ArrayList)request.getAttribute("selectList");
    String faqcategory   ="";

%>

<%@ include file="/learn/user/portal/include/top.jsp"%>


<!-- 스크립트영역 -->

<script language="JavaScript" type="text/JavaScript"><!--


//카테고리에 맞는 리스트 검색
function changeCategory(aa) {
        document.form1.faqcategory.value=aa;
        $("#p_searchtxt").val("");
        document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
        document.form1.p_process.value    = "selectList";
        document.form1.submit();
}

//검색내용에 맞는 리스트 페이지로 이동
function searchTxt() {
	if(document.form1.p_searchtxt.value ==""){
		alert("내용을 입력하세요");
		return;

	}
		document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
		document.form1.p_process.value = "selectList";
		document.form1.submit();
}

//

    //function clickHandler() {
    //    var  srcElement;
    //
    //    srcElement = window.event.srcElement;
    //
    //    if (srcElement.id == "faq_dl") {
	//        var targetElements = document.getElementsByName("faq_dl");
	//        var dtElements       = document.getElmentsByName("faq_dt");
	//        var targetLen    = targetElements.length;
    //
	//        for(var i = 0 ; i < targetLen ; i++) {
	//	        var dlElement  = document.getElementById("Out"+i+"details");
    //
	//	        if(targetElements[i] == srcElement){
	//		        targetElements[i].className = "faq_fir";
	//		        dtElements[i].className = "on";
	//		        dlElement.style.display = "block";
	//	        } else {
	//	        	targetElements[i].className = "faq";
    //                dtElements[i].className = "";
    //                dlElement.style.display = "none"; 
	//	        }
	//        }
	//    } else if (srcElement.id == "spanAllView") {
	//    	var divDetails 	= document.getElementsByName("faq_dt");
	//    	var divLen		= divDetails.length;
    //
	//    	for(var i = 0 ; i < divLen ; i++) {
	//    	    var outObj = document.getElementById("Out"+i+"details");
	//    	    outObj.style.display = "block";
	//    	}
	//    } else if (srcElement.id == "spanAllClose") {
	//    	var divDetails 	= document.getElementsByName("faq_dt");
	//    	var divLen		= divDetails.length;
    //
	//    	for(var i = 0 ; i < divLen ; i++) {
    //            var outObj = document.getElementById("Out"+i+"details");
	//            outObj.style.display = "none";
	//        }
    //    }					
    //}
    //
    //document.onclick = clickHandler;
    
    function viewFaq(idx){
        var objDl   = document.getElementsByName("objDl");
        var objDt   = document.getElementsByName("objDt");
        var objDd   = document.getElementsByName("objDd");

        var objLen  = objDl.length;

        for(var i = 0 ; i < objLen ; i++) {
            if(i == idx && objDd[i].style.display  == "none") {
                objDl[i].className      = "faq_fir";
                objDt[i].className      = "on";
                objDd[i].style.display  = "block";
            } else if (i == idx && objDd[i].style.display  == "block"){
            	objDl[i].className      = "faq";
                objDt[i].className      = "";
                objDd[i].style.display  = "none";
            }
        }
    }  

    function viewFaqAll(action) {
    	var objDl   = document.getElementsByName("objDl");
        var objDt   = document.getElementsByName("objDt");
        var objDd   = document.getElementsByName("objDd");
        var objLen  = objDl.length;

        for(var i = 0 ; i < objLen ; i++) {
            if(action == "open") {
                objDl[i].className      = "faq_fir";
                objDt[i].className      = "on";
                objDd[i].style.display  = "block";
            } else {
                objDl[i].className      = "faq";
                objDt[i].className      = "";
                objDd[i].style.display  = "none";
            }
        }
    }      

//
--></script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작-->
<form name = "form1" method = "post">
	  <input type = "hidden" name = "p_process"   value = "">
	  <input type = "hidden" name = "p_pageno"    value = "">
	  <input type = "hidden" name = "p_seq"       value = "">
	  <input type = "hidden" name = "p_userid"    value = "">
	  <input type = "hidden" name = "faqcategory" value = "<%=v_faqcategory%>">
	  
	  <%if( box.getSession("tem_grcode").equals("N000001")) { %>
	  
	  <table>
	  	<tr>
	  		<td>
	  
	  	<!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/support/stitle_03.gif" alt="FAQ" /></td>
        <td class="h_road">Home &gt; 학습지원 안내  &gt; <strong>FAQ</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><a href="javascript:changeCategory('ALL')" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image44','','/images/portal/homepage_renewal/support/tab_on_01.gif',1)"><img src="/images/portal/homepage_renewal/support/tab_on_01.gif" name="Image44" width="84" height="39" border="0" id="Image44" /></a></td>
        <td><a href="javascript:changeCategory('0025')" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image45','','/images/portal/homepage_renewal/support/tab_on_02.gif',1)"><img src="/images/portal/homepage_renewal/support/tab_off_02.gif" name="Image45" width="116" height="39" border="0" id="Image45" /></a></td>
        <td><a href="javascript:changeCategory('0034')" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image46','','/images/portal/homepage_renewal/support/tab_on_03.gif',1)"><img src="/images/portal/homepage_renewal/support/tab_off_03.gif" name="Image46" width="98" height="39" border="0" id="Image46" /></a></td>
        <td><a href="javascript:changeCategory('0035')" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image49','','/images/portal/homepage_renewal/support/tab_on_04.gif',1)"><img src="/images/portal/homepage_renewal/support/tab_off_04.gif" name="Image49" width="81" height="39" border="0" id="Image49" /></a></td>
        <td><a href="javascript:changeCategory('0036')" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image50','','/images/portal/homepage_renewal/support/tab_on_05.gif',1)"><img src="/images/portal/homepage_renewal/support/tab_off_05.gif" name="Image50" width="81" height="39" border="0" id="Image50" /></a></td>
        <td><a href="javascript:changeCategory('0037')" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image51','','/images/portal/homepage_renewal/support/tab_on_06.gif',1)"><img src="/images/portal/homepage_renewal/support/tab_off_06.gif" name="Image51" width="110" height="39" border="0" id="Image51" /></a></td>
        <td><a href="javascript:changeCategory('0072')" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image52','','/images/portal/homepage_renewal/support/tab_on_07.gif',1)"><img src="/images/portal/homepage_renewal/support/tab_off_07.gif" name="Image52" width="102" height="39" border="0" id="Image52" /></a></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="2" colspan="5" bgcolor="#7692CB"></td>
      </tr>
      <tr>
        <td height="10" colspan="5">&nbsp;</td>
      </tr>
      <%if(list.size() != 0){%>
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
        <td width="40"><img src="/images/portal/homepage_renewal/support/sstitle_007.gif"/></td>
        <td width="292">(전체 <%=list.size() %>개)</td>
        <td width="207" class="h_road" ><a href="javascript:viewFaqAll('open');"><img src="/images/portal/homepage_renewal/support/btn_alldown.jpg" width="127" height="21" /></a></td>
        <td width="113" class="h_road" ><a href="javascript:viewFaqAll('close');"><img src="/images/portal/homepage_renewal/support/btn_allup.jpg" width="113" height="21" /></a></td>
      </tr>
      <%} %>
      <tr>
        <td height="1" colspan="5" bgcolor="#EEEEEE"></td>
        </tr>
    </table>
    <%if(list.size() != 0){%>
    <%
				int i =0;
				for( i = 0; i < list.size(); i++) {

					DataBox dbox = (DataBox)list.get(i);

					v_title     = dbox.getString("d_title");
					v_contents  = dbox.getString("d_contents");
					v_totalpage = dbox.getInt("d_totalpage");
					v_rowcount  = dbox.getInt("d_rowcount");

					if (!v_searchtext.equals("")) {
						v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
						v_contents = StringManager.replace(v_contents, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
					}
			%>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <!--<tr id="objDt">
        <td width="5%" height="30"><img src="/images/portal/homepage_renewal/support/icon_q.jpg" width="16" height="15" i/></td>
        <td width="95%" height="20"><a href="javascript:viewFaq(<%=i %>);"><%=v_title%></a></td>
      </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="eeeeee"></td>
      </tr>
      <tr id="objDd" style="display:none;">
        <td width="5%" height="30"><img src="/images/portal/homepage_renewal/support/icon_a.jpg" width="16" height="15" /></td>
        <td width="95%" bgcolor="f7f7f7"><table width="100%" border="0" cellspacing="0" cellpadding="10">
            <tr>
              <td><%=v_contents%></td>
            </tr>
          </table>
          </td>
      </tr>
      -->
      <dl id="objDl" name="objDl" class="faq" >
               <dt id="objDt" name="objDt" style="padding:10px 0px 7px 56px; background: url(/images/portal/homepage_renewal/support/icon_q.jpg) no-repeat 20px 11px;" ><a href="javascript:viewFaq(<%=i %>);"><%=v_title%></a></dt>
               <dd id="objDd" name="objDd" style="padding:10px 0px 7px 56px; background: url(/images/portal/homepage_renewal/support/icon_a.jpg) no-repeat 20px 11px; display:none;" > 
                   <%=v_contents%>
               </dd>
      </dl>
    </table>
    <%} %>
    <%} %>
    
    <!--타이틀부분//-->
    </td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
	  
	  <%}else{ %>

            <h2><img src="/images/portal/studying/h2_tit2.gif" alt="FAQ" class="fl_l" /><p class="category">Home > 학습지원센터 > <strong>FAQ</strong></p></h2>
 
            <p class="faq_search">
                <span class="faq_search">
                <img src="/images/portal/studying/faq_searchtit.gif" alt="faq검색" class="mg_r4" />
                <input type="text" name="p_searchtxt" id="p_searchtxt" title="faq검색어" class="inbox" style="width:285px;" value="<%=v_searchtxt %>" onkeypress="javascript:fnKeyPressEnter(event, 'searchTxt');"/>
                <a href="javascript:searchTxt();">
                <input type="image" class="btn" src="/images/portal/btn/btn_serbox_search.gif" alt="검색" onclick="searchTxt();"/>
                </a>
                </span>
            </p>
            
            <!-- 카테고리 테이블 시작 -->
            <%=HomePageFAQBean.gameFaqCategoryTable(box)%>
            <!-- 카테고리 테이블 종료 -->


                        <!-- 질문1 -->
			<%if(list.size() != 0){%>
			<h3>
			<span class="fl_l"><img src="/images/portal/common/stit_whole.gif" alt="전체" class="fl_l" />
			<span class="stit">(전체 <%=list.size() %>개)</span></span>
			<p class="btn"><a href="javascript:viewFaqAll('open');" class="btn_down"><span>전체내용펼치기</span></a><a href="javascript:viewFaqAll('close');" class="btn_up"><span>전체내용닫기</span></a></p>
			</h3>
			<%
				int i =0;
				for( i = 0; i < list.size(); i++) {

					DataBox dbox = (DataBox)list.get(i);

					v_title     = dbox.getString("d_title");
					v_contents  = dbox.getString("d_contents");
					v_totalpage = dbox.getInt("d_totalpage");
					v_rowcount  = dbox.getInt("d_rowcount");

					if (!v_searchtext.equals("")) {
						v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
						v_contents = StringManager.replace(v_contents, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
					}
			%>
			<dl id="objDl" name="objDl" class="faq">
                <dt id="objDt" name="objDt"><a href="javascript:viewFaq(<%=i %>);"><%=v_title%></a></dt>
                <dd id="objDd" name="objDd" style="display:none;" > 
                    <%=v_contents%>
                </dd>
            </dl>
            <%  }
			} else { %>
			<dl class="faq">
                <dd> 
                                               검색된 FAQ가 없습니다.
                </dd>
            </dl>
            <%}%>
		<%} %>
</form>
<!-- form 끝 -->


<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->
