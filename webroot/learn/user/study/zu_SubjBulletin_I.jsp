<%
//**********************************************************
//  1. 제      목: SUBJ QNA INSERT
//  2. 프로그램명: zu_SubjBulletin_I.jsp
//  3. 개      요: 과정 질문 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2003. 9. 8
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
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    String  v_subj    = box.getString("p_subj");        //과정코드
    String  v_year    = box.getString("p_year");        //년도
    String  v_subjseq = box.getString("p_subjseq");     //과정 차수
    String  v_gubun  = box.getString("p_gubun");
    int     v_seq     = box.getInt("p_seq");
    String  v_title="", v_contents="",  v_inuserid="",v_inusernm="";
    String  v_indate="";
    String  v_isopen="";
    int  v_types = 0;
    int  v_nanscnt  =0;
    int     i            = 0;
    ArrayList list1      = null;

    String  v_user_id   = box.getSession("userid");
    String  v_user_name = box.getSession("name");
    v_inuserid= box.getSession("userid");
    v_inusernm= box.getSession("name");

    // editor
    String content      = "";
    String width        = "600";
    String height       = "300";
%>
<!------- 파일확장자체크 INCLUDE ------------>       
<%@ include file = "/learn/library/fileFilter.jsp" %>
<html>
<head>
<title>::: 사이버 문화콘텐츠 아카데미 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
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
  // 등록하기
  function qna_insert() {
 
        if (blankCheck(document.all.p_title.value)) {
            alert("제목을 입력하세요!");
            document.all.p_title.focus();
            return;
        }                
        if (realsize(document.all.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.all.p_title.focus();
            return;
    	}
    	
        /*if (blankCheck(document.form1.p_contents.value)) {
            alert("내용을 입력하세요!");
            document.form1.p_contents.focus();
            return;
        }*/   	

    	if(document.all.use_editor[0].checked) {        
            document.form1.p_contents.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    	}else {
		    document.form1.p_contents.value = document.all.txtDetail.value;
	    }
	        

	    //파일 확장자 필터링  
	    var islimit = true;	    
	    for(var i=1; i<=1; i++){
	        var file = eval("document.form1.p_file"+i+".value");

    	    if(file!="") {
    	        islimit = limitFile(file);
    	        
    	        if(!islimit) break;
    	    }	        
	    }
 	    	    	    	    
	    if(islimit) {
          document.form1.target = "_self";
          document.form1.p_search.value     = "";
          document.form1.p_searchtext.value = "";
        	document.form1.p_process.value = "SubjBulletinInsert";
          document.form1.submit();
	    }else{
	        return;
	    }	      
    }

    function goQnaDetailPage() {
      document.form1.submit();
    }

    // 목록으로이동
    function qna_list() {
        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.study.SubjBulletinServlet";
        document.form1.p_process.value = "SubjBulletinList";
        document.form1.submit();
    }
-->
</SCRIPT>
</head>


<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('/contents/sws/sws002/01/img/close2.gif')">
<form name="form1" method="post" action="/servlet/controller.study.SubjBulletinServlet" enctype = "multipart/form-data">
    <input type="hidden" name="p_process"    value="<%=v_process%>">
    <input type="hidden" name="p_search"     value="<%=v_search %>">
    <input type="hidden" name="p_searchtext" value="<%=v_searchtext %>">
    <input type="hidden" name="p_subj"       value="<%=v_subj%>">
    <input type="hidden" name="p_year"       value="<%=v_year%>">
    <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">
    <input type="hidden" name="p_gubun"     value="<%=v_gubun%>">
    <input type="hidden" name="p_seq"        value="<%=v_seq%>">
    <input type="hidden" name="p_contents">

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
    
    <table cellspacing="0" cellpadding="0" class="open_board_vertical_table_out" align="center">
      <tr>
        <td width="3" class="board_color_line"></td>
        <td>
          <table cellspacing="1" cellpadding="1" class="table2">
            <tr>
              <td class="board_title_bg2" width="150">제 목</td>
              <td class="table_text3">
                <input size="70" maxlength="20" name="p_title" class="input">
              </td>
            </tr>
            <tr>
              <td class="board_title_bg2" width="150">작성자</td>
              <td class="table_text3"><%=v_inusernm%></td>
            </tr>
            <tr>
              <td class="board_title_bg2">내 용</td>
              <td class="table_text3">
                <!--<textarea name="p_contents" cols="70" rows="15" class="input"></textarea>     -->
						<!-- DHTML Editor  -->
			            <%//@ include file="/portal/include/DhtmlEditor.jsp" %>
    		            <%@ include file="/include/DhtmlEditor.jsp" %>
                        <!-- DHTML Editor  -->                       
              </td>
            </tr>
			  <tr>
                <td class="board_title_bg2">첨부파일</td>
				<td class="table_text3">
				  <input type="FILE" name="p_file1" size="45" class="input"><br>
				  <!--input type="FILE" name="p_file2" size="45" class="input"><br>
				  <input type="FILE" name="p_file3" size="45" class="input"><br>
				  <input type="FILE" name="p_file4" size="45" class="input"><br>
				  <input type="FILE" name="p_file5" size="45" class="input"><br-->
				</td>
			  </tr>            
          </table>
        </td>
      </tr>
    </table>
    <br>
    <!-- 버튼 시작 -->
	<table width="700" border="0" cellpadding="0" cellspacing="0">
     <tr> 
       <td align=center>
       <a href = "javascript:insert();"><a href="javascript:qna_insert()" ><img src="/images/user/button/btn_save.gif" alt="저장" border="0"></a> &nbsp;&nbsp;
       <a href="javascript:qna_list()" ><img src="/images/user/button/btn_list.gif" alt="목록" border="0"></a>
       </td>
     </tr>
    </table>
    <!-- 버튼 끝 -->
          
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