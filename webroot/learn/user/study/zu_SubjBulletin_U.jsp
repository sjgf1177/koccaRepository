<%
//**********************************************************
//  1. 제      목: SUBJ QNA UPDATE
//  2. 프로그램명: zu_SubjBulletin_U.jsp
//  3. 개      요: 과정 질문 업데이트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 김영만 2003. 9. 8
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
    String  v_process = box.getString("p_process");    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    String  v_gadminYn = box.getString("p_gadminYn");    //관리자여부
    String  v_subj    = box.getString("p_subj");        //과정코드
    String  v_year    = box.getString("p_year");         //년도
    String  v_subjseq = box.getString("p_subjseq");       //과정 차수
    String  v_gubun  = box.getString("p_gubun");
    int     v_seq     = box.getInt("p_seq");
    int     v_ptypes  = box.getInt("p_types");
    int     v_kind    = box.getInt("p_kind");

    String  v_title="", v_contents="",  v_inuserid="",v_inusernm="";
	
	String  v_user_id   = box.getSession("userid");
    String  v_user_name = box.getSession("username");
    v_inuserid= box.getSession("userid");
    v_inusernm= box.getSession("name");


    String  v_indate="";
    String  v_isopen="";
    int  v_types = 0;
    int  v_nanscnt  =0;
    int     i            = 0;
    // editor
    String content      = "";
    String width        = "600";
    String height       = "300"; 
        
    ArrayList qnalist1      = null;
    qnalist1 = (ArrayList)request.getAttribute("SubjBulletinDetail2");
    DataBox data1 = null;


    data1 = (DataBox)qnalist1.get(0); 
    v_seq       = data1.getInt("d_seq");             
    v_title     = data1.getString("d_title");                    
    content     = StringManager.replace(data1.getString("d_contents"),"\n;","<br>");   
    v_inuserid  = data1.getString("d_inuserid");
    v_inusernm  = data1.getString("d_name");
    v_indate    = FormatDate.getFormatDate(data1.getString("d_indate"),"yyyy/MM/dd");
    String vs_kind    = data1.getString("d_kind");  
    String v_realfile = data1.getString("d_realfile");                    

%>
<!------- 파일확장자체크 INCLUDE ------------>       
<%@ include file = "/learn/library/fileFilter.jsp" %>
<html>
<head>
<title>::: 사이버 문화콘텐츠 아카데미 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
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
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
  function bulletin_insert() {     
        if(!confirm("저장하시겠습니까?")) {
                 return;
            }
        if (document.ff.p_title.value == "") {
            alert("제목을 입력하세요");
            document.ff.p_title.focus();
            return;
        }

        if (realsize(document.ff.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.ff.p_title.focus();
            return;
        }

    	if(document.all.use_editor[0].checked) {        
    	    ff.p_contents.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    	}else {
    		ff.p_contents.value = document.all.txtDetail.value;
    	}


	    //파일 확장자 필터링  
	    var islimit = true;	    
	    for(var i=1; i<=1; i++){
	        var file = eval("document.ff.p_file"+i+".value");

    	    if(file!="") {
    	        islimit = limitFile(file);
    	        
    	        if(!islimit) break;
    	    }	        
	    }
 	    	    	    	    
	    if(islimit) {
            document.ff.target = "_self";
            document.ff.p_search.value     = "";
            document.ff.p_searchtext.value = "";
            document.ff.p_process.value = 'SubjBulletinUpdate';        
            document.ff.submit();
	    }else{
	        return;
	    }
	            
    }

    function goQnaDetailPage() {
      document.ff.submit();
    }
    
    function detail_bulletin() {
        document.ff.action = "/servlet/controller.study.SubjBulletinServlet";
        document.ff.p_process.value = "SubjBulletinList";
        document.ff.submit();
    }
-->
</SCRIPT>
</head>




<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('/contents/sws/sws002/01/img/close2.gif')">
<form name="ff" method="post" action="/servlet/controller.study.SubjBulletinServlet" enctype = "multipart/form-data">
        <input type="hidden" name="p_process"    value="SubjBulletinDetail">
        <input type="hidden" name="p_search"     value = "<%=v_search %>">
        <input type="hidden" name="p_searchtext" value = "<%=v_searchtext %>">
        <input type="hidden" name="p_subj"       value="<%=v_subj%>">
        <input type="hidden" name="p_year"       value="<%=v_year%>">
        <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">
        <input type="hidden" name="p_gubun"     value="<%=v_gubun%>">
        <input type="hidden" name="p_seq"        value="<%=v_seq%>"> <!--글번호key-->
        <input type="hidden" name="p_kind"        value="<%=v_kind%>"><!--질문분류번호key-->
        <input type="hidden" name="p_types"      value="<%=v_ptypes%>">
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
    

            <!----------------- 질문 수정 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_vertical_table_out" align="center">
              <tr> 
                <td width="3" class="board_color_line"></td>
                <td> 
                  <table cellspacing="1" cellpadding="1" class="table2">
                    <tr> 
                      <td class="board_title_bg2" width="100">제목</td>
                      <td class="table_text3"> 
                        <input size="85" maxlength="90" name="p_title" type="text" class="input" value=`<%=v_title%>`>
                      </td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg2" width="100">작성자</td>
                      <td class="table_text3"><%=v_inusernm %></td>
                    </tr>

                    <tr> 
                      <td class="board_title_bg2" width="100">내용</td>
                      <td class="table_text3"> 
						<!-- DHTML Editor  -->
    		            <%@ include file="/include/DhtmlEditor.jsp" %>
                        <!-- DHTML Editor  -->                        
                      </td>
                    </tr>
                    

    			    <tr>
                        <td class="board_title_bg2">첨부파일</td>
                        <td class="table_text3">                         
                        <% if(!v_realfile.equals("")){ %>
                        <input type = "hidden" name = "p_upfilecnt"  value = "<%=qnalist1.size()%>"><!--첨부파일 갯수-->
                        <%   for(int j=0; j<qnalist1.size(); j++){         
                             DataBox dbox = (DataBox)qnalist1.get(j);  
                                           
                        %>     
						     <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%=dbox.getString("d_savefile")%>&p_realfile=<%=dbox.getString("d_realfile")%>'>					
                             <%=dbox.getString("d_realfile")%></a> &nbsp;
			
			       			<input type = "checkbox"  name = "p_fileseq<%=j%>" value = "<%=dbox.getInt("d_fileseq")%>"> (삭제시 체크)<br>
					    	<input type = "hidden" name = "p_realfile"  value = <%=dbox.getString("d_realfile")%>>
						    <input type = "hidden" name = "p_savefile<%=j%>"  value ="<%=dbox.getString("d_savefile")%>">
						    <%}%>
                        <%}%>
						  <input type="FILE" name="p_file1" size="70" class="input"><br>
						  <!--input type="FILE" name="p_file2" size="80" class="input"><br>
						  <input type="FILE" name="p_file3" size="80" class="input"><br>
						  <input type="FILE" name="p_file4" size="80" class="input"><br>
						  <input type="FILE" name="p_file5" size="80" class="input"><br-->
						</td>
					  </tr>
                  </table>
                </td>
              </tr>
            </table>
            <!----------------- 질문 수정 끝 ---------------->
            <br>
            <!----------------- 저장, 목록 버튼 시작 ----------------->            
            <table width="120" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td align="center"><a href="javascript:bulletin_insert();" ><img src="/images/user/button/btn_save.gif"  alt="저장"  border="0"></a></td>
                <td align="center"><a href="javascript:detail_bulletin()" ><img src="/images/user/button/btn_list.gif"  alt="목록"  border="0"></a></td>
              </tr>
            </table>
            <!----------------- 저장, 목록 버튼 끝 ----------------->
            <br>



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
