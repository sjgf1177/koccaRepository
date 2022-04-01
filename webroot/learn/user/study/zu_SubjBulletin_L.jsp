<%
//**********************************************************
//  1. 제      목: SUBJ BULLETIN LIST
//  2. 프로그램명: zu_SubjBulletin_L.jsp
//  3. 개      요: 과정 게시판 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2006. 05. 16
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    String  v_gadminYn = box.getString("p_gadminYn");    //관리자여부
    String  v_subj     = box.getString("p_subj");        //과정코드
    String  v_year     = box.getString("p_year");        //년도
    String  v_subjseq  = box.getString("p_subjseq");     //과정 차수
    String  v_gubun   = box.getString("p_gubun");
    int     v_upfilecnt = 0;
    String  v_title="", v_jikwinm="",v_cono="", v_inuserid="", v_userid="",v_inusernm="";
    String  v_indate="";
    String  v_isopen="", v_isopen_value="";
    int   v_seq = 0, v_anscnt = 0 ;
    int  v_readcnt = 0;
    int  v_qcnt     = 0;
    int  v_nanscnt	=0;
    int     i            = 0;

    v_userid= box.getSession("userid");

    // 페이징관련
    int v_totalpage = 0;
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");   
	if (v_pageno == 0) v_pageno = 1;
	
    //list1 = (ArrayList)request.getAttribute("SubjBulletinList");SubjBulletinList
    ArrayList list1  = (ArrayList)request.getAttribute("SubjBulletinList");
%>
<html>
<head>
<title>::: 사이버 문화콘텐츠 아카데미 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">    
	td {font-size :12px; font-family : 굴림; color : #757575; line-height:135%}
	  :link		{  text-decoration : none; color: #757575 }
	  :active	{  text-decoration : none; color: #ee6600 }
	  :visited  {  text-decoration : none; color: #999999 }
	  :hover 	{  text-decoration : none; color: #ff6600 }

</style>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 등록
    function whenInsert() {
  		document.ff.target = "_self";
        document.ff.p_process.value = 'SubjBulletinInsertPage';
        document.ff.submit();
    }

    // 상세보기
    function whenSelect(seq) {
            whenRead(seq);
    }
    
    // 상세보기
    function whenRead(seq) {
        document.ff.target = "_self";
        document.ff.p_process.value = 'SubjBulletinDetail';
        document.ff.p_seq.value = seq;
        document.ff.submit();
    }
    
    // 검색 
    function qna_search() {
        document.ff.target = "_self";
        document.ff.p_process.value = 'SubjBulletinList';
        document.ff.submit();
    }

    //페이지 이동
    function goPage(pageNum) {
    
    	document.ff.p_pageno.value = pageNum;
    	document.ff.action = "/servlet/controller.study.SubjBulletinServlet";
    	document.ff.p_process.value = "SubjBulletinList";
    	document.ff.submit();
    }

-->
</SCRIPT>
</head>


<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('/contents/sws/sws002/01/img/close2.gif')">
<form name="ff" method="post" action="/servlet/controller.study.SubjBulletinServlet">
		<input type="hidden" name="p_process"  value="SubjBulletinDetail">
		<input type="hidden" name="p_subj"     value="<%=v_subj%>">
		<input type="hidden" name="p_year"     value="<%=v_year%>">
		<input type="hidden" name="p_subjseq"  value="<%=v_subjseq%>">
		<input type="hidden" name="p_gubun"   value="<%=v_gubun%>">
		<input type="hidden" name="p_seq"      value="">
        <input type = "hidden" name = "p_pageno"  value = "<%=v_pageno%>">

<table width="800" height="600" border="0" cellpadding="0" cellspacing="0">

  <!-- 타이틀 시작 -->
  <tr> 
    <td colspan="2">
    <% if (v_gubun.equals("1")) { %>
    	<img src="/contents/sws/sws002/01/img/board.jpg" width="800" height="100">
    <% } else if (v_gubun.equals("2")) { %>
    	<img src="/contents/sws/sws002/01/img/idea.jpg" width="800" height="100">
    <% } %>
    
    </td>
  </tr>
  <tr valign="top"> 
    <td height="55" colspan="2"><div align="right"></div>
      </td>
  </tr>
  <!-- 타이틀 끝 -->
  <tr valign="top"> 
    <td height="400" colspan="2"><div align="center"> 
    
        <!-- 본문내용 시작 -->
        <table width="700" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td height="35" colspan="6"><table width="700" height="35" border="0" cellpadding="0" cellspacing="0" background="/contents/sws/sws002/01/img/bar01.gif">
                <tr> 
                  <td width="70"><div align="center"><strong>번호</strong></div></td>
                  <td width="300"><div align="center"><strong>제목</strong></div></td>
                  <td width="60"><div align="center"><strong>파일</strong></div></td>
                  <td width="120"><div align="center"><strong>작성자</strong></div></td>
                  <td width="90"><div align="center"><strong>작성일</strong></div></td>
                  <td width="60"><div align="center"><strong>조회</strong></div></td>
                </tr>
              </table></td>
          </tr>
          
            <%
              for(i = 0; i < list1.size(); i++) {
    			  DataBox dbox = (DataBox)list1.get(i);              
        	      v_totalpage = dbox.getInt("d_totalpage");
        	      //v_rowcount  = dbox.getInt("d_rowcount");
    			
                  v_seq     = dbox.getInt("d_seq");
                  v_title   = dbox.getString("d_title");
                  v_inuserid= dbox.getString("d_inuserid");
                  v_inusernm= dbox.getString("d_name");
                  v_indate  = FormatDate.getFormatDate(dbox.getString("d_indate"),"yyyy/MM/dd");
                  v_anscnt  = dbox.getInt("d_anscnt");
                  v_upfilecnt = dbox.getInt("d_upfilecnt");
                  v_readcnt = dbox.getInt("d_readcnt");
            %>
            
            <tr bgcolor="#FAFAFA"> 
            <td width="70" height="30"><div align="center"><%= list1.size() - i %></div></td>
            <td width="300"><a href="javascript:whenSelect(<%=v_seq%>);"><%= v_title %><%if (FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(dbox.getString("d_indate"), "yyyyMMdd"))){%>
								<IMG src="/images/user/support/ico_new.gif" alt="New" border="0">
							<% } %>
							</td>
            <td width="60"><% if( v_upfilecnt>0){ %>
							<img src="/images/user/support/ico_file.gif" alt="file" width="13" height="12">
							<%}%> </td>
            <td width="120"><div align="center"><%= v_inusernm %></div></td>
            <td width="90"><div align="center"><%= v_indate %></div></td>
            <td width="60"><div align="center"><%=v_readcnt%></div></td>
          </tr>
          <tr> 
            <td height="1" colspan="6" bgcolor="#F0F0F0"></td>
          </tr>
          

            <%
    		    }
            %>

<%    if(i == 0){   %>  
			<tr> 
            	<td colspan="6" align="center">등록된 내용이 없습니다.</td>
          	</tr>  
<%    } %>            

		        </table>
		        
		  <!-- 버튼 시작 -->
		  <table width="700" border="0" cellpadding="0" cellspacing="0">
          <tr> 
            <td height="30"><div align="center"></div></td>
            <td colspan="3" align=right><a href = "javascript:whenInsert();"><img src="/images/user/button/btn_write.gif" alt="작성" border="0"></a>
            </td>
            <td colspan="2"><div align="center"></div></td>
          </tr>
          </table>
          <!-- 버튼 끝 -->
          
          <!-- 페이지 시작 -->
		
		  <table width="700" border="0" cellpadding="0" cellspacing="0">
          <tr> 
            <td height="30"><div align="center"></div></td>
            <td colspan="3"><div align="center"><%= HomePageQnaBean.printPageList(v_totalpage, v_pageno, row) %></td>
            <td colspan="2"><div align="center"></div></td>
          </tr>
          </table>
          <!-- 페이지 끝 -->
          

        
            <!-- 본문내용 끝 -->
      </div></td>
  </tr>
  <tr> 
    <td height="20" colspan="2">&nbsp;</td>
  </tr>
  <tr> 
    <td width="749" height="25" bgcolor="#999999"><div align="left"><font color="#333333"><strong>KOCCA 
        </strong><font size="-2">- KOREA CULTURE &amp; CONTNENTS ASENCY</font></font></div></td>
    <td width="52" bgcolor="#999999">
    <div align="right"><a href="javascript:self.close()" onFocus="this.blur();" onMouseOut="MM_swapimgRestore()" onMouseOver="MM_swapImage('close','','/contents/img/close1.gif',1)"><img src="/contents/img/close1.gif" width="50" height="16" border="0"></a></div></td>
  </tr>
</table>
</form>
</body>
</html>

