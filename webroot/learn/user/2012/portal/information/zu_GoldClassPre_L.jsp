<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.dunet.common.util.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    int     v_seq       = 0;
    int     i           = 0;
    int     v_viewcnt   = 0;
    float   v_checkpoin = 0;
    
    String v_vodimg     = "";
    String v_lecnm      = "";
    String v_tutornm    = "";
    String v_lectime    = "";
    String v_creator    = "";
    String v_intro      = "";
    String v_width_s    = "";
    String v_height_s   = "";
    
    int     v_pageno    = box.getInt("p_pageno"); 
    int     v_dispnum   = 0;
    int     v_totalpage = 0;
    int     v_rowcount  = 1;
    int		v_totalrowcount = 0;
    int 	v_pagesize = box.getInt("p_pagesize");

    ArrayList list = (ArrayList)request.getAttribute("selectPreGoldClassList");
      
    String  v_searchtext     = box.getString("p_searchtext");   
    String  v_search         = box.getString("p_search");

    String s_grcode          = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
    
    String v_classname1      = "";
    String v_classname2      = "";
                  
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--

//검색내용에 맞는 리스트 페이지로 이동
function selectList() {
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.target="_self";
    document.form1.p_process.value = "selectPreGoldClassList";
    document.form1.submit();
}

//상세화면 페이지로 이동
function viewContent(seq) {
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.target="_self";
    document.form1.p_process.value   = "selectView";
    document.form1.p_seq.value       = seq;
    document.form1.submit();
}

//페이지 이동
function go(index) {
    document.form1.p_pageno.value = index;
    document.form1.target="_self";
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.p_process.value = "selectPreGoldClassList";
    document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
     document.form1.target="_self";
     document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
     document.form1.p_process.value = "selectPreGoldClassList";
     document.form1.submit();
}

function openGoldClass(seq,w,h)
{
    
    if (w.length>0) {
        window.open("about:blank", "ViewVod", "top=0, left=0, width="+w+", height="+h+", status=no, resizable=no");
        
    }else{
        window.open("about:blank", "ViewVod", "top=0, left=0, width=600, height=500, status=no, resizable=no");
    }
    document.form1.action="/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.p_process.value = "popUpVod";
    document.form1.p_seq.value = seq;
    document.form1.target="ViewVod";
    document.form1.submit();

    document.form1.target="_self";
    
}

function goGoldClassMain(){
    document.form1.target="_self";
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.p_process.value = "mainPage";
    document.form1.submit();
}

function goPreGoldClass(){
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.target="_self";
    document.form1.p_process.value = "selectPreGoldClassList";
    document.form1.submit();
    }

function goReply(seq){
	window.open("about:blank", "replyList", "top=0, left=0, width=637, height=530, status=no, resizable=no");

    document.form1.action="/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.p_process.value = "insertReplyPage";
    document.form1.p_seq.value = seq;
    document.form1.target="replyList";
    document.form1.submit();
    document.form1.target="_self";
}
//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->

<form name = "form1" method = "post" action="">
      <input type = "hidden" name = "p_process"     value = "">
      <input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
      <input type = "hidden" name = "p_seq"         value = "">
      <input type = "hidden" name = "p_userid"      value = "">
      
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
        <td><img src="/images/portal/homepage_renewal/library/stitle_02.gif" alt="골드클래스" /></td>
        <td class="h_road">Home &gt; <strong>골드클래스</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><a href="javascript:goGoldClassMain();" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image47','','/images/portal/homepage_renewal/library/tab_on_03.gif',1)"><img src="/images/portal/homepage_renewal/library/tab_on_03.gif" name="Image47" id="Image47" /></a></td>
        <td><a href="javascript:goPreGoldClass();" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image48','','/images/portal/homepage_renewal/library/tab_on_04.gif',1)"><img src="/images/portal/homepage_renewal/library/tab_off_04.gif" name="Image48" id="Image48" /></a></td>
      </tr>
    </table>
    <!--탭부분//-->
    <!--테이블부분-->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <%
            if(list.size() != 0){

                for(i = 0; i < list.size(); i++) {
                   DataBox dbox = (DataBox)list.get(i);  
                   
                   v_seq        = dbox.getInt("d_seq");
                   v_vodimg     = dbox.getString("d_vodimg");
                   v_lecnm      = dbox.getString("d_lecnm");
                   v_tutornm    = dbox.getString("d_tutornm");
                   v_lectime    = dbox.getString("d_lectime");
                   v_creator    = dbox.getString("d_creator");
                   v_intro      = dbox.getString("d_intro");
                   v_width_s    = dbox.getString("d_width_s");
                   v_height_s   = dbox.getString("d_height_s");
                   v_viewcnt    = dbox.getInt("d_viewcnt");
                   v_checkpoin  = dbox.getFloat("d_checkpoin");
                   
                   v_dispnum    = dbox.getInt("d_dispnum");
                   v_totalpage  = dbox.getInt("d_totalpage");
                   v_rowcount   = dbox.getInt("d_rowcount");  
            	   v_totalrowcount = dbox.getInt("d_totalrowcount");

                   v_intro      = StringUtil.getByteString(v_intro, 200);
                   %>
      <tr>
        <td height="2" colspan="3" bgcolor="#7692CB"></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
      <tr>
        <td width="32%"><div align="center">
            <table border="0" cellpadding="0" cellspacing="1" bgcolor="#D9DFEB">
              <tr>
                <td><div align="center">
                    <table width="140" border="0" cellpadding="0" cellspacing="1" bgcolor="eeeeee">
                      <tr>
                        <td><div align="center"><a href="javascript:openGoldClass(<%=v_seq %>, '<%=v_width_s %>','<%=v_height_s %>')" class="btn_violet"><img src="/servlet/controller.library.DownloadServlet?p_savefile=<%=v_vodimg %>" width="152" height="114" alt="" /></div></td>
                      </tr>
                    </table>
                </div></td>
              </tr>
            </table>
        </div></td>
        <td width="68%"><div align="center">
          <table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
            <tr>
              <td width="2%" height="30"><strong class="org">ㆍ</strong></td>
              <td height="20" colspan="3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="65%"><div align="left"><a href="javascript:viewContent( <%=v_seq%>);"><strong><%=v_lecnm %></strong></a></div></td>
                    <td width="17%"><a href="javascript:openGoldClass(<%=v_seq %>, '<%=v_width_s %>','<%=v_height_s %>')" class="btn_violet"><span class="h_road"><img src="/images/portal/homepage_renewal/library/btn_book_view.jpg" width="87" height="25" /></span></a></td>
                    <td width="18%"><a href="javascript:viewContent( <%=v_seq%>);" class="btn_gr"><span class="h_road"><img src="/images/portal/homepage_renewal/library/btn_def_view.jpg" width="87" height="25" /></span></a></td>
                  </tr>
                </table></td>
              </tr>
            <tr>
              <td height="1" colspan="4" bgcolor="eeeeee"></td>
            </tr>
            <tr>
              <td height="30"><strong class="org">ㆍ</strong></td>
              <td width="15%" height="20"><div align="left">강사명 : </div></td>
              <td width="34%" height="20"><div align="left"><%=v_tutornm %></div></td>
              <td width="49%" height="20">&nbsp;</td>
            </tr>
            <tr>
              <td height="1" colspan="4" bgcolor="eeeeee"></td>
            </tr>
            <tr>
              <td height="30"><strong class="org">ㆍ</strong></td>
              <td height="20"><div align="left">강의시간 : </div></td>
              <td height="20"><div align="left"><%=v_lectime %></div></td>
              <td height="20"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="11" height="30"><strong class="org">ㆍ</strong></td>
                  <td width="77"><div align="left">제작 :</div></td>
                  <td width="134"><div align="left"><%=v_creator%></div></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="1" colspan="4" bgcolor="eeeeee"></td>
            </tr>
            <tr>
              <td height="30"><strong class="org">ㆍ</strong></td>
              <td height="80"><div align="left">개요 : </div></td>
              <td height="20" colspan="2"><div align="left"><%=v_intro %></div></td>
              </tr>
            <tr>
              <td height="1" colspan="4" bgcolor="eeeeee"></td>
            </tr>
            <tr>
              <td height="30"><strong class="org">ㆍ</strong></td>
              <td height="20"><div align="left">조회수 :</div></td>
              <td height="20"><div align="left"><%=v_viewcnt %></div></td>
              <td height="20"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="12" height="30"><strong class="org">ㆍ</strong></td>
                  <td width="42"><div align="left">평가 :</div></td>
                  <td width="100"><div align="left"><%=StringUtil.getStarPoint(v_checkpoin) %>
                  <a href="javascript:goReply(<%=v_seq %>)" class="board_btn2 fl_r">
                  	<img src="/images/portal/homepage_renewal/library/btn_reply.jpg" width="75" height="21" />
                  </a>
                  </td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="1" colspan="4" bgcolor="eeeeee"></td>
            </tr>
          </table>
        </div></td>
        <td width="0%"><div align="center"></div></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
      <% } 
    
            }else{%>
                <tr>
                    <td>이달의 골드클래스가 없습니다.</td>
                </tr>
            <%}%>
    </table>
    
    <table width="100%" border="0" cellspacing="0" cellpadding="0">

      <tr>
        <td height="1" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" bgcolor="F7F7F7"></td>
      </tr>
    </table>
    <!--페이징부분--><!--타이틀부분//-->    </td>
    <td width="20">&nbsp;</td>
    		      
		      </tr>		
		</table>
		
		<!-- 페이지 영역 -->
		<%= PageUtil.re_printPageSizeListDiv(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %>
		<!-- 페이지 영역 -->
      
      <%}else{ %>


            <h2><img src="/images/portal/information/h2_tit1.gif" alt="골든클래스" class="fl_l" /><p class="category">Home > 정보광장 > <strong>골드클래스</strong></p></h2>

            <p><img src="/images/portal/information/gold_borimg.gif" alt="골드클래스는 무료로 제공되는 공개강좌로 재미와 깊이가 함께 존재하는 열린 강의입니다. 매월 훌륭한 명사의 새로운 강좌를 만나보실 수 있으며, 누구나 참여할 수 있는 열린 교육공간입니다. " /></p>

            <ul class="tabwrap mg_t30">
                <li><a href="javascript:goGoldClassMain();" class="tab_information"><span>이달의 골드클래스</span></a></li>
                <li><a href="javascript:goPreGoldClass();" class="tab_information tab_information_on"><span>지난 골드클래스</span></a></li>
            </ul>
            
                        <div class="info_photo_list">
            <%
            if(list.size() != 0){

                for(i = 0; i < list.size(); i++) {
                   DataBox dbox = (DataBox)list.get(i);  
                   
                   v_seq        = dbox.getInt("d_seq");
                   v_vodimg     = dbox.getString("d_vodimg");
                   v_lecnm      = dbox.getString("d_lecnm");
                   v_tutornm    = dbox.getString("d_tutornm");
                   v_lectime    = dbox.getString("d_lectime");
                   v_creator    = dbox.getString("d_creator");
                   v_intro      = dbox.getString("d_intro");
                   v_width_s    = dbox.getString("d_width_s");
                   v_height_s   = dbox.getString("d_height_s");
                   v_viewcnt    = dbox.getInt("d_viewcnt");
                   v_checkpoin  = dbox.getFloat("d_checkpoin");
                   
                   v_dispnum    = dbox.getInt("d_dispnum");
                   v_totalpage  = dbox.getInt("d_totalpage");
                   v_rowcount   = dbox.getInt("d_rowcount");  

                   v_intro      = StringUtil.getByteString(v_intro, 200);
                   %>
                       <dl class="photo_list">
                           <dt><img src="/servlet/controller.library.DownloadServlet?p_savefile=<%=v_vodimg %>" width="152" height="114" alt="" /></dt>
                           <dd class="tit_btn"><span class="fl_l"><%=v_lecnm %></span><span class="fl_r"><a href="javascript:openGoldClass(<%=v_seq %>, '<%=v_width_s %>','<%=v_height_s %>')" class="btn_violet"><span>강좌보기</span></a><a href="javascript:viewContent( <%=v_seq%>);" class="btn_gr"><span>상세보기</span></a></span></dd>
                           <dd><span class="tit">강사명</span> <span class="con"><%=v_tutornm %></span></dd>
                           <dd><span class="le"><span class="tit">강의시간</span><span class="con"><%=v_lectime %></span></span><span class="ri"><span class="tit">제 작</span><span class="con">37:18</span></span></dd>
                           <dd class="ex"><span class="tit">개 요</span> <span class="con"><%=v_intro %></span></dd>
                           <dd><span class="le"><span class="tit">조회수 </span> <span class="con"><%=v_viewcnt %></span></span><span class="ri"><span class="tit">평가</span><span class="con"><span class="fl_l"><%=StringUtil.getStarPoint(v_checkpoin) %></span> <a href="javascript:goReply(<%=v_seq %>)" class="board_btn2 fl_r"><span>의견달기</span></a></span></span></dd>
                       </dl>
             <%}
                }else{%>
                <dl class="photo_list">
                    <dd>등록된 골드클래스가 없습니다.</dd>
                </dl>
            <%}%>
            </div>
        <!-- 페이징 영역 종료 -->
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
        <!-- 페이징 영역 종료 -->
        <%} %>
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->