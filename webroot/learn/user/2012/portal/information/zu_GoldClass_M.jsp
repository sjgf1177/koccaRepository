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
    
    String v_genre		= box.getString("p_genre"); 
    String v_subnavi = "";
    
    box.put("topmenu","6");
    
    if (v_genre.equals("G01")) {  //방송영상
		box.put("submenu","1");  	
		v_subnavi = "방송영상";
	} else if (v_genre.equals("G02")) {  // 영화
		box.put("submenu","2");		
		v_subnavi = "영화";
	} else if (v_genre.equals("G03")) { // 다큐멘터리
		box.put("submenu","3");		
		v_subnavi = "다큐멘터리";
	}else if (v_genre.equals("G04")) { // 문화예술
		box.put("submenu","4");		
		v_subnavi = "문화예술";
	}else if (v_genre.equals("G05")) { // 기타
		box.put("submenu","5");		
		v_subnavi = "기타";
	}else if (v_genre.equals("G06")) { // 게임
		box.put("submenu","6");		
		v_subnavi = "게임";
	}else { // 디폴트
		box.put("submenu","1");		
	}
    
    
    int[]   v_seq       = new int[200];
    int     i           = 0;
    int		j			= 0;
    int     v_viewcnt   = 0;
    float   v_checkpoin = 0;
    boolean flag = false;
    
    String[] v_vodimg     = new String[200];
    String[] v_lecnm      = new String[200];
    String[] v_tutornm    = new String[200];
    String[] v_lectime    = new String[200];
    String[] v_creator    = new String[200];
    String[] v_intro      = new String[200];
    String[] v_width_s    = new String[200];
    String[] v_height_s   = new String[200];
    String[] v_vodurl     = new String[200];
    String[] v_tutorimg		= new String[200];
    String[] v_tutorcareer		= new String[200];
    String[] v_tutorauthor		= new String[200];
    String[] v_contents  = new String[200];
    
    
    
    int v_seq2			= 0;      
    String v_vodimg2	= "";   
    String v_lecnm2    	= "";
    String v_tutornm2  	= "";
    String v_lectime2  	= "";
    String v_creator2  	= "";
    String v_intro2    	= "";
    String v_width_s2  	= "";
    String v_height_s2 	= "";
    int v_viewcnt2  	= 0;
    float v_checkpoin2	= 0;
    String v_vodurl2   	= "";
    String v_tutorimg2	= "";
    
    //String v_tutorcareer	= "";
    String v_creatyear	= "";
    //String v_contents	= "";
    //String v_tutorauthor	= "";
    String v_openyn	= "";
    
    String v_useyn	= "";
    String v_img_save_file	= "";
    String v_tutor_save_file	= "";
    String v_bannerimg = "";
        
    
    
    ArrayList GoldClassList = (ArrayList)request.getAttribute("GoldClassList");
      
    String s_grcode          = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
    
    String v_classname1      = "";
    String v_classname2      = "";
    
    String s_userid   = box.getSession("userid");
    
    if (v_bannerimg.equals("")) {
    	v_bannerimg = "/images/2012/sub/page5/castimage/sample.gif";
    } else {
    	v_bannerimg = "http://edu.kocca.or.kr/upload/bulletin/"+v_bannerimg;
    }
                  
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--

//검색내용에 맞는 리스트 페이지로 이동
function goPreGoldClass(){
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.target="_self";
    document.form1.p_process.value = "selectPreGoldClassList";
    document.form1.submit();
    }

//상세화면 페이지로 이동
function viewContent(seq) {
	document.form1.p_prePage.value   = "Main";
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.target="_self";
    document.form1.p_process.value   = "selectView";
    document.form1.p_seq.value       = seq;
    document.form1.submit();
}

function viewContentNew(seq) {
	document.form1.p_prePage.value   = "Main";
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.target="_self";
    document.form1.p_process.value   = "mainPage";
    document.form1.p_seq.value       = seq;
    document.form1.submit();
}


function openGoldClass(seq,w,h)
{
    
    if (w.length>0) {
        window.open("", "ViewVod", "top=0, left=0, width="+w+", height="+h+", status=no, resizable=no");
        
    }else{
        window.open("", "ViewVod", "top=0, left=0, width=1008, height=570, status=no, resizable=no");
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

function goReply(seq){

	window.open("about:blank", "replyList", "top=0, left=0, width=637, height=530, status=no, resizable=no");

    document.form1.action="/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.p_process.value = "insertReplyPage";
    document.form1.p_seq.value = seq;
    document.form1.target="replyList";
    document.form1.submit();

    document.form1.target="_self";    
}

function goGoldClassMain(){
    document.form1.target="_self";
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.p_process.value = "mainPage";
    document.form1.submit();
}
//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->

<form name = "form1" method = "post" action="">
      <input type = "hidden" name = "p_process"     value = "">
      <input type = "hidden" name = "p_seq"         value = "">
      <input type = "hidden" name = "p_prePage"     value = "">
      
      <div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 열린강의 > <u><%=v_subnavi %></u></td>
			</tr>
		</table>
		<div class="opencast">
			<dl class="castlist">
		 <%  
		 	int iMok =(GoldClassList.size() / 4);
		 	
      if(GoldClassList.size() != 0){
        for(i = 0; i < GoldClassList.size(); i++) {
					DataBox dbox = (DataBox)GoldClassList.get(i);
					
					v_seq[i]        = dbox.getInt("d_seq");
					v_vodimg[i]     = dbox.getString("d_vodimg");
					v_lecnm[i]      = dbox.getString("d_lecnm");
					v_tutornm[i]    = dbox.getString("d_tutornm");
					v_lectime[i]    = dbox.getString("d_lectime");
					v_creator[i]    = dbox.getString("d_creator");
					v_intro[i]      = dbox.getString("d_intro");
					v_width_s[i]    = dbox.getString("d_width_s");
					v_height_s[i]   = dbox.getString("d_height_s");
					// v_viewcnt[i]    = dbox.getInt("d_viewcnt");
					// v_checkpoin[i]  = dbox.getFloat("d_checkpoin");
					v_vodurl[i]     = dbox.getString("d_vodurl");
					v_tutorimg[i]	= StringManager.replace(dbox.getString("d_tutorimg"),"\\","/");
					v_tutorcareer[i]  = dbox.getString("d_tutorcareer");
					v_tutorauthor[i]       = dbox.getString("d_tutorauthor");
					v_contents[i]       = dbox.getString("d_contents");
          %>            
				<dd><a href="javascript:infoopen('<%=i+1%>')" tabindex="<%=i+200%>"><img src="http://edu.kocca.or.kr<%=StringManager.replace(v_vodimg[i],"\\","/")%>" alt="<%=v_lecnm[i]%>"></a><p><a href="javascript:infoopen('<%=i+1%>')" tabindex="<%=i+200%>" title="<%=v_tutornm[i] %>를 소개합니다"><%=v_tutornm[i] %></a></p></dd>
				<% if (((i+1)%4) == 0){%>  </dl>
				<% for(int k = 0; k < 4; k++) { %>
				<dl id="info<%=i-2+k%>" class="infobox" style="display:none;">
					<dd class="boxtop"></dd>
					<dd class="boxmid">
						<h3><%=v_lecnm[i-3+k] %></h3>
						<table class="infoform" cellpadding="0" cellspacing="0">
							<tr>
								<th><img src="http://edu.kocca.or.kr<%=v_tutorimg[i-3+k]%>" class="photo" alt="<%=v_tutornm[i-3+k] %>">
								<br><a href="javascript:openGoldClass(<%=v_seq[i-3+k] %>, '<%=v_width_s[i-3+k] %>','<%=v_height_s[i-3+k] %>')" tabindex="<%=k+200+i-3%>" title="강의보기"><img src="/images/2012/sub/page5/btn_play.png" align="absmiddle" alt="강의보기"></a>
								</th>
								<td>
									<img src="/images/2012/sub/page5/subject01.gif" alt="약력"><br>
									<%=v_tutorcareer[i-3+k].replace("\r\n","<br>") %>
								</td>
								<td>
									<img src="/images/2012/sub/page5/subject02.gif" alt="주요 수상 및 저서"><br>
									<%=v_tutorauthor[i-3+k].replace("\r\n","<br>")%>
								</td> 
							</tr>
						</table>
						<a href="javascript:infoclose();" tabindex="<%=k+200+i-3%>" title="현재 펼쳐진 강의소개를 닫습니다"><img src="/images/2012/sub/page5/btn_close.gif" class="infoclose" alt="닫기"></a>
					</dd>
					<dd class="boxbtm"></dd>
				</dl>
				<% } %>
				
				 <% if (i != (GoldClassList.size()-1) )  {  %>
				 <dl class="castlist">
				 <% } %>			 
			 
			  <%}%> 
			 
			<% if (i == (GoldClassList.size()-1) &&  ((i+1)%4) != 0 )  { 
			       flag = true;
			%>
			
			</dl> <!--  last one -->
			<% } %>
			 
			<% if ((flag) && (i == GoldClassList.size()-1)){ %>
			<% for(int  k =(GoldClassList.size()%4); k > 0;k--) { %>
			<dl id="info<%=i-k+2%>" class="infobox" style="display:none;">
				<dd class="boxtop"></dd>
				<dd class="boxmid">
					<h3><%=v_lecnm[i-k+1] %></h3>
					<table class="infoform" cellpadding="0" cellspacing="0">
						<tr>
							<th><img src="http://edu.kocca.or.kr<%=v_tutorimg[i-k+1]%>" class="photo" alt="<%=v_tutornm[i-k+1] %>">
							<br><a href="javascript:openGoldClass(<%=v_seq[i-k+1] %>, '<%=v_width_s[i-k+1] %>','<%=v_height_s[i-k+1] %>')" tabindex="<%=i-k+1+200%>" title="강의보기"><img src="/images/2012/sub/page5/btn_play.png" align="absmiddle" alt="강의보기"></a>
							</th>
							<td>
								<img src="/images/2012/sub/page5/subject01.gif" alt="약력"><br>
								<%=v_tutorcareer[i-k+1].replace("\r\n","<br>") %> 
							</td>
							 <td>
								<img src="/images/2012/sub/page5/subject02.gif" alt="주요 수상 및 저서"><br>
								 <%=v_tutorauthor[i-k+1].replace("\r\n","<br>")%> 
							</td>  
						</tr>
					</table>
					<a href="javascript:infoclose();" tabindex="<%=i-k+1+200%>" title="현재 펼쳐진 강의소개를 닫습니다"><img src="/images/2012/sub/page5/btn_close.gif" class="infoclose" alt="닫기"></a>
				</dd>
				<dd class="boxbtm"></dd> 
			</dl>
			<% }} %>
			<% }} %>
		</div>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
