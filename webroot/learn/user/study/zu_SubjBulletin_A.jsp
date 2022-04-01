<%
//**********************************************************
//  1. 제      목: SUBJ QNA UPDATE
//  2. 프로그램명 : zu_SubjBulletin_A.jsp
//  3. 개      요: 과정 질문 답변
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 11
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
    String  v_subj       = box.getString("p_subj");        //과정코드
    String  v_year       = box.getString("p_year");         //년도
    String  v_subjseq    = box.getString("p_subjseq");       //과정 차수
    String  v_gubun     = box.getString("p_gubun");
    int     v_seq        = box.getInt("p_seq");
    int     v_ptypes     = box.getInt("p_types");

    String  v_user_id   = box.getSession("userid");
    String  v_user_name = box.getSession("name");

    String  v_title  ="", v_contents     ="",  v_inuserid="",v_inusernm="";
    String  v_indate ="", v_replygubun   ="";
    String  v_isopen ="", v_isopen_value ="";;
    int  v_types = 0;
    int  v_kind  = 0;    
    int  v_nanscnt  =0;
    int  i          = 0;
    
    // editor
    String content      = "";
    String width        = "600";
    String height       = "300";
        
    ArrayList list1 = (ArrayList)request.getAttribute("SubjBulletinDetail2");
    DataBox dbox = (DataBox)list1.get(0); 
    
%>
<!------- 파일확장자체크 INCLUDE ------------>       
<%@ include file = "/learn/library/fileFilter.jsp" %>
<html>
<head>
<title>::: 사이버 문화콘텐츠 아카데미 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
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
            document.ff.p_contents.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    	}else {
		    document.ff.p_contents.value = document.all.txtDetail.value;
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
            document.ff.p_process.value = 'SubjBulletinReply';        
            document.ff.submit();
	    }else{
	        return;
	    }	        
    }

    function detail_bullitin() {
        document.ff.action = "/servlet/controller.study.SubjBulletinServlet";
        document.ff.p_process.value = "SubjBulletinList";
        document.ff.submit();
    }
-->
</SCRIPT>
</head>




<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('/contents/sws/sws002/01/img/close2.gif')">
<form name="ff" method="post" action="/servlet/controller.study.SubjBulletinServlet" enctype = "multipart/form-data">
        <input type="hidden" name="p_process"    value="BulletinDetail">
        <input type="hidden" name="p_search"     value = "<%=v_search %>">
        <input type="hidden" name="p_searchtext" value = "<%=v_searchtext %>">
        <input type="hidden" name="p_subj"       value="<%=v_subj%>">
        <input type="hidden" name="p_year"       value="<%=v_year%>">
        <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">
        <input type="hidden" name="p_gubun"     value="<%=v_gubun%>">
        <input type="hidden" name="p_seq"        value="<%=v_seq%>">
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
    
        <!-- 본문내용 시작 -->
       


<%
                //v_kind       = qdata.getKind();
                //v_seq        = qdata.getSeq();
                //v_title      = qdata.getTitle();
                //v_inuserid   = qdata.getInuserid();
                //v_inusernm   = qdata.getInusernm();
                //v_contents   = qdata.getContents();
                //v_indate     = FormatDate.getFormatDate(dbox.getString("d_indate"),"yyyy/MM/dd");
                //v_replygubun = qdata.getReplygubun();
                
                v_kind       = dbox.getInt("d_kind");
                v_seq        = dbox.getInt("d_seq");
                v_title      = dbox.getString("d_title");
                v_inuserid   = dbox.getString("d_inuserid");
                v_inusernm   = dbox.getString("d_inusernm");
                v_contents   = dbox.getString("d_contents");
                v_indate     = FormatDate.getFormatDate(dbox.getString("d_indate"),"yyyy/MM/dd");
                v_replygubun = dbox.getString("d_replygubun");

                //v_contents   = StringManager.replace(StringManager.replace(v_contents,"\r\n","<br>")," ","&nbsp;");

%>  
            <!----------------- 질문방 내용 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
                <tr> 
                    <td colspan="2" height="25" align="left"><b>질문</b></td>
                </tr>
            </table>    
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
              <tr> 
                <td class="board_color_line"></td>
              </tr>
            </table>
            <table cellspacing="0" cellpadding="0" class="open_board_table_out2" align="center">
              <tr> 
                <td> 
                  <table cellspacing="1" cellpadding="3" class="table2">
                    <tr> 
                      <td class="board_title_bg1" width="15%">제 목</td>
                      <td class="board_title_bg3" width="28%"><%= v_title %></td>
                      <td class="board_title_bg1" width="15%"></td>
                      <td class="board_title_bg3" width="27%"></td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg1">등록일</td>
                      <td class="board_title_bg3"><%=v_indate%></td>
                      <td class="board_title_bg1">작성자</td>
                      <td class="board_title_bg3"><%=v_inusernm%></td>
                    </tr>
                    <tr> 
                      <td colspan="4" class="board_text1" align="center"> 
                        <table width="98%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td><%=v_contents%></td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>

            <!----------------- 질문방 내용 끝 ---------------->
            <br>

                        
            <!----------------- 답변 내용 시작 ----------------->

            <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
                <tr> 
                    <td colspan="2" height="25" align="left"><b>답변</b></td>
                </tr>
            </table>    
            <table cellspacing="0" cellpadding="0" class="open_board_vertical_table_out" align="center">
              <tr>
                <td width="3" class="board_color_line"></td>
                <td>
                  <table cellspacing="1" cellpadding="1" class="table2">
                    <tr>
                      <td class="board_title_bg2" width="150">제 목</td>
                      <td class="table_text3">
                        <input size="70" maxlength="30" name="p_title" class="input">
                      </td>
                    </tr>
                    <tr>
                      <td class="board_title_bg2" width="150">작성자</td>
                      <td class="table_text3"><%=v_user_name%></td>
                    </tr>
                    <tr>
                      <td class="board_title_bg2">내 용</td>
                      <td class="table_text3">
                        <!--<textarea name="p_contents" cols="70" rows="15" class="input"></textarea>-->
						<!-- DHTML Editor  -->
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

            <!----------------- 저장, 목록 버튼 시작 ----------------->            
            <table width="120" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td align="center"><a href="javascript:bulletin_insert();"><img src="/images/user/button/btn_save.gif" alt="저장" border="0"></a></td>
                <td align="center"><a href="javascript:detail_bullitin()"><img src="/images/user/button/btn_list.gif"  alt="목록" border="0"></a></td>
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
