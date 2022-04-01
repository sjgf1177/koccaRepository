<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import="com.credu.homepage.LoginBean" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process  = box.getString("p_process");

    String topWarp_userid = box.getSession("userid");
    String topWarp_usernm = box.getSession("name");
    String topWarp_gadmin = box.getSession("gadmin");
    String topWarp_gadmin1 = box.getSession("gadmin1");

    if (topWarp_gadmin.equals("")) {
        box.setSession("gadmin", "ZZ");
        topWarp_gadmin = box.getSession("gadmin");
    }
    else if(!(topWarp_gadmin.equals("") || topWarp_gadmin.equals("ZZ")))
    {
        box.setSession("gadmin1", box.getSession("gadmin"));
        topWarp_gadmin1 = box.getSession("gadmin1");
    }

    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");
    String gubun = box.getString("gubun");

    ArrayList<DataBox> goldclassList   = (ArrayList)request.getAttribute("goldclassList");
    ArrayList<DataBox> noticeList        = (ArrayList)request.getAttribute("noticeList");

 // 팝업 공지사항
    ArrayList<DataBox> noticeListPopup = (ArrayList) request.getAttribute("noticePopup");

    String ser_nm = (String) request.getParameter("ser_nm");
    String rejectResult = (String) request.getParameter("rejectResult");

    boolean isReject = rejectResult.equals("1") ? true : false;
%>
<script type="text/javascript">
    document.onload=popupView();

    function ASP_login()
    {
        var r=$("[name='p_id']").val();
        if(r=="")
        {
            alert("아이디를 입력 하여 주십시오.");
            $("[name='p_id']").focus();
            return;
        }

        var r1=$("[name='p_pw']").val();
        if(r1=="")
        {
            alert("암호를 입력 하여 주십시오.");
            $("[name='p_pw']").focus();
            return;
        }

        mainmenu("5");
    }

    function open_window(name, url, left, top, width, height, toolbar, menubar, statusbar, scrollbar, resizable) {
        toolbar_str = toolbar ? 'yes' : 'no';
        menubar_str = menubar ? 'yes' : 'no';
        statusbar_str = statusbar ? 'yes' : 'no';
        scrollbar_str = scrollbar ? 'yes' : 'no';
        resizable_str = resizable ? 'yes' : 'no';
        window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar=no,menubar=no,status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
    }

    function popupView() /* 팝업 관련 스크립트 */
    {
    var  width  = 0;
    var  height = 0;
    var  left   = 0;
    var  top    = 0;

    <%  for(int i=0; i < noticeListPopup.size(); i++){
            DataBox dbox =    noticeListPopup.get(i);  %>
      width  = <%=dbox.getString("d_popwidth")%>;
      height = <%=dbox.getString("d_popheight")%>;
      left   = <%=dbox.getString("d_popxpos")%>;
      top    = <%=dbox.getString("d_popypos")%>;
      url    = "/servlet/controller.homepage.MainServlet?p_process=popupview&p_seq=<%=dbox.getString("d_seq")%>&p_useframe=<%=dbox.getString("d_useframe")%>&p_popUpPreview=popUpPreview<%=dbox.getString("d_seq")%>";

        if ( notice_getCookie( "popUpPreview<%=dbox.getString("d_seq")%>" ) != "done" ){
          open_window("popUpPreview<%=dbox.getString("d_seq")%>",url,left,top,width,height, "no","no","no","yes","no");
        }
    <%  }  %>
        }

        function trhidden(obj,nowTrName,sw,classStr,allHideTr)
        {
            var obj_parents=$("."+allHideTr);
            $(".hiddenYesNo",obj_parents).hide();
//            $("[name='"+nowTrName+"']").attr("class","");

            if(sw=="t")
            {
                $(".hiddenYesNo",obj).show();
//                $(obj).attr("class",classStr);
            }
        }
</script>

<script type="text/javascript">
jQuery(function($){
	var i_text = $('.item>.i_label').next('.i_text');
	$('.item>.i_label').css('position','absolute');
	i_text
		.focus(function(){
			$(this).prev('.i_label').css('visibility','hidden');
		})
		.blur(function(){
			if($(this).val() == ''){
				$(this).prev('.i_label').css('visibility','visible');
			} else {
				$(this).prev('.i_label').css('visibility','hidden');
			}
		})
		.change(function(){
			if($(this).val() == ''){
				$(this).prev('.i_label').css('visibility','visible');
			} else {
				$(this).prev('.i_label').css('visibility','hidden');
			}
		})
		.blur();
});
</script>


    <div id="cnt">
    	<div class="cnt_left">
           
            <!--공지사항 게시판 -->
            <div class="cnt_left_board">
            	<div class="title"><span style="float:left;font-size:16px;font-weight:600;color:#111;">공지사항</span><span style="float:right;"><!--더보기 --><a href="javascript:menuForward('4', '07');"><img src="/images/portal/asp/typeB/main/btn_more.png" /></a></span></div>
                <ul>
                	<%
				    if(noticeList!=null && noticeList.size()!=0) {
				        for(int i=0;i<noticeList.size();i++) {
				            if(i >= 5){
				            	continue;
				            }
				            DataBox dbox = noticeList.get(i);
				            out.print("<li>");
				            out.print("<span style=\"float:left\"><a href='javascript:viewNotice("+dbox.getString("d_seq")+")'>"+StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_adtitle")), 15)+"</a></span>");
				            String dt=dbox.getString("d_ADDATE").substring(0,4)+"/"+dbox.getString("d_ADDATE").substring(4,6)+"/"+dbox.getString("d_ADDATE").substring(6,8);
				            out.print("<span class=\"date\">"+dt+"</span>");
				            out.print("</li>");
				        }
				    }
					%>
                </ul>
            </div>
            <div class="shadow"></div>
            <!--//공지사항 게시판 -->
             
            <!--열린강좌 게시판 -->
            <div class="cnt_left_board2">
            	<div><img src="/images/portal/asp/typeB/main/pic_s.png" /></div>
            	<div class="title"><span style="float:left;font-size:16px;font-weight:600;color:#111;">열린강좌</span><span style="float:right;"><!--더보기 --><a href="javascript:menuForward('5', '01');"><img src="/images/portal/asp/typeB/main/btn_more.png" /></a></span></div>                
                <ul>
                	<%
				    if(goldclassList!=null && goldclassList.size()!=0) {
				        for(int i=0;i<goldclassList.size();i++) {
				            if(i >= 3){
				                break;
				            }
				            DataBox dbox =  goldclassList.get(i);
				            out.print("<li>");
				            out.print("<span style=\"float:left\"><a href=\"javascript:viewContent('"+dbox.getString("d_seq")+"', '"+dbox.getString("d_lecture_cls")+"')\">"+StringManager.getByteString(StringManager.removeHTML(dbox.getString("d_lecnm")), 36)+"</a></span>");
				            out.print("</li>");
				        }
				    }
					%>
                </ul>
            </div>
            <!--//열린강좌 게시판 -->
          
        </div>
        
        <div class="cnt_right">
        	<!--로그인 박스 -->
        	<%if(s_userid.equals("")){%>
	        	<div class="cnt_right_login">
	            	<div class="login_title">회원 로그인</div>
	            	<!--아이디입력 -->
	            	<div class="item">  
						<label style="position:absolute; visibility:visible" class="i_label" for="uid">아이디</label>  
						<input id="p_id" name="p_id" class="i_text uid" title="아이디" accesskey="L" style="-ms-ime-mode: disabled;">
					</div>  
	                <!--패스워드 -->
					<div class="item" style="margin-top:5px;">  
						<label style="position:absolute; visibility:visible" class="i_label" for="upw">패스워드</label>  
						<input id="p_pw" name="p_pw" class="i_text upw" value="" type="password" title="패스워드" onkeypress="javascript:fnKeyPressEnter(event, 'ASP_login');">
					</div>
	            	
<!-- 					<div class="input_box"> -->
<!-- 						<label for="p_id" id="label_id" class="lbl_in">아이디</label> -->
<!-- 						<input type="text" id="p_id" name="p_id" maxlength="41" title="아이디" placeholder="아이디" class="int"> -->
<!-- 					</div> -->
<!-- 					<div class="input_box" style="margin-top:5px;"> -->
<!-- 						<label for="p_pw" id="label_pw" class="lbl_in">패스워드</label> -->
<!-- 						<input type="password" id="p_pw" name="p_pw" maxlength="41" title="패스워드" placeholder="패스워드" class="int" onkeypress="javascript:fnKeyPressEnter(event, 'ASP_login');"> -->
<!-- 					</div> -->
	                <!--로그인 버튼 --><input type="button" value="로그인" class="login_btn" onclick="javascript:ASP_login()"/>
	                <ul>
	                <%
	               	if ( isReject ) {
	            		if (ser_nm.equals("mcst.kocca.or.kr") || ser_nm.equals("mcst.edukocca.or.kr") ) {
					%>
							<li class="w48"><a href="javascript:alert('아이나루를 통하여 접속해 주세요');" >회원가입</a></li>
							<li class="w130"><a href="javascript:mainmenu('2');" >아이디/패스워드 찾기</a></li>
					<%
	           			}
	        		} else {
					%>
							<li class="w48"><a href="javascript:mainmenu('1');" >회원가입</a></li>
							<li class="w130"><a href="javascript:mainmenu('2');" >아이디/패스워드 찾기</a></li>
					<%
	        		}
					%>
	                </ul>
	            </div>
	            <div class="shadow"></div>
            <%}else{%>
	        	<div class="cnt_right_login">
	            	<div class="login_title">회원 로그인</div>
	                <div style="clear:both;padding: 15px;font-size: 13px;border-top: 1px solid #e9e9e9;border-bottom: 1px solid #e9e9e9;"><span style="font-weight:800;"><%=s_username%></span>님, 환영합니다.</div>
					<ul class="login_member_go">
						<li style="float:left;background-image:none;padding-left:0px;">
							<%if(!(topWarp_gadmin1.equals("ZZ") || topWarp_gadmin1.equals(""))){%>
								<%=LoginBean.getAuthSelect(topWarp_userid, "p_topAuth", topWarp_gadmin, "onChange = 'javascript:topAuthChange();' style='color:#626262;font-size:12px;width:138px;height:27px;'") %>
							<%}%>
						</li>
						<%if((!(topWarp_gadmin.equals("ZZ") || topWarp_gadmin.equals("")))){%>
							<li style="float:left;background-image:none;padding-left:3px;"><input type="button" value="GO" class="login_btn" style="width:50px;margin-left:5px;" onClick='topAdminOpenPage()'/></li>
						<%}%>
					</ul>
	                <ul>
						<li class="w48"><a href="javascript:mainmenu('3');" >로그아웃</a></li>
						<li class="w95"><a href="javascript:mainmenu('4');" >개인정보변경</a></li>
	                </ul>
	            </div>
	            <div class="shadow"></div>
            <%}%>
            <!--//로그인 박스 -->
        	<div style="margin-top:8px;"><!--나의 강의실 --><a href="javascript:mainmenu('61');"><img src="/images/portal/asp/typeB/main/box01.png" /></a></div>
            <div style="margin-top:8px;"><!--교육과정소개 --><a href="javascript:mainmenu('110');"><img src="/images/portal/asp/typeB/main/box02.png" /></a></div>
        </div>
        
        
        <div class="cnt_bottom">
        	<!--학습지원센터 링크 -->
          	<div class="cnt_bottom_study">
            	<div style="padding:15px;text-align:left;color:#fff;font-size:16px;font-weight:600;">학습지원센터</div>
                <p>
                	<a href="javascript:mainmenu('72');"><img src="/images/portal/asp/typeB/main/study01.png" /></a>
                	<a href="javascript:mainmenu('73');"><img src="/images/portal/asp/typeB/main/study02.png" /></a>
                	<a href="javascript:mainmenu('75');"><img src="/images/portal/asp/typeB/main/study03.png" /></a>
                	<a href="javascript:mainmenu('76');"><img src="/images/portal/asp/typeB/main/study04.png" /></a>
                </p>
            </div>
            <!--//학습지원센터 링크 -->
            
            <div class="cnt_bottom_tel"><img src="/images/portal/asp/typeB/main/tel.png" /></div>
         </div>
         
    </div>