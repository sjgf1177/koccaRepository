<%
//**********************************************************
//  1. 제      목: 오프라인 갤러리
//  2. 프로그램명 : gu_Gallery_I.jsp
//  3. 개      요: 오프라인 갤러리 등록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.19
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.system.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","03");                       // 메뉴 아이디 세팅

    String s_userid  = box.getSession("userid");
    String s_name    = box.getSession("name");
    String s_gadmin  = box.getSession("gadmin");
    String v_process =  box.getString("p_process");
    String content   = "";
    String width     = "650";
    String height    = "200";

    int v_seq = 0;

    v_process = "insertGallery";
    box.put("p_process",v_process);

%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topService.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<SCRIPT LANGUAGE="JavaScript">
<!--

    function listView(){
        document.formGallery.action = "/servlet/controller.homepage.HomeGalleryServlet";
        document.formGallery.p_process.value = "List";
        document.formGallery.submit();
    }

    function insert(){

        if(document.all.use_editor[0].checked) {        
            formGallery.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
        }else {
            formGallery.content.value = document.all.txtDetail.value;
        }

        if (blankCheck(document.all.p_title.value)) {
            alert("제목을 입력하세요!");
            document.all.p_title.focus();
            return;
        }   
        if (realsize(document.formGallery.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.formGallery.p_title.focus();
            return;
        }

    var islimit1 = true;
    var islimit2 = true;
    var islimit3 = true;
    var islimit4 = true;
    var islimit5 = true;
    var file1 = document.formGallery.p_file1.value;
    var file2 = document.formGallery.p_file2.value;
    var file3 = document.formGallery.p_file3.value;

    
    if(file1!="") {islimit1 = limitFile(file1);}
    if(!islimit1){  return; }
    
    if(file2!="") {islimit1 = limitFile(file2);}
    if(!islimit2){  return; }
    
    if(file3!="") {islimit1 = limitFile(file3);}
    if(!islimit3){  return; }

	<%if( s_gadmin.equals("A1") || (s_gadmin.equals("H1") && (1==1)) || s_gadmin.equals("B2")){ %>
        if( document.formGallery.p_useyn.checked == true){
            document.formGallery.p_useyn.value = "Y";
        }
    <% } %>

        var url = "/servlet/controller.homepage.HomeGalleryServlet";
        document.formGallery.p_process.value="insertGallery";
        document.formGallery.action = url;
        document.formGallery.submit();  
    }


//빈내용 체크
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;
    
    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);
    
    return (false);
}

//글자수 체크
function realsize( value ) {
 var len = 0;
 if ( value == null ) return 0;
 for(var i=0;i<value.length;i++){
     var c = escape(value.charAt(i));
     if ( c.length == 1 ) len ++;
     else if ( c.indexOf("%u") != -1 ) len += 2;
     else if ( c.indexOf("%") != -1 ) len += c.length/3;
 }
 return len;
}

//-->
</SCRIPT>

<!-- /////////////////////////////////////////////////////////-->
<!--
  파일확장자체크 INCLUDE
  
  1. page import = "com.credu.system.*" 추가
  2. 자바스크립트 호출부분 추가
    예제)
    //파일 확장자 필터링  
    var islimit = true;
    var file = document.form1.p_file1.value;  
    if(file!="") {
        islimit = limitFile(file);    //return값 true/false
    }
    
    if(islimit) {
        document.form1.submit();
    }else{
        return;
    }

-->
<%@ include file = "/learn/library/fileFilter.jsp" %>
<!-- /////////////////////////////////////////////////////////-->

<form name="formGallery" method="post" enctype ="multipart/form-data">
    <input type = "hidden" name = "p_process" value="">
    <input type = "hidden" name = "p_seq"     value="<%=v_seq%>">


<!-- title -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/service/<%=tem_subimgpath%>/tit_off_gallery.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 서비스 > Off Line 학생갤러리</td>
  </tr>
  <tr> 
    <td height="20">
      <!-- page sub -->
    </td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="81" height="2" class="linecolor_ser5"></td>
    <td width="639" height="2" class="linecolor_ser3"></td>
  </tr>
</table>
<table width="720" border="2" cellspacing="0" 
    cellpadding="0"  style="border-collapse:collapse;" bordercolor="#ECE1D0" frame="hsides" rules="rows" >
  <tr> 
    <td width="69" class="tbl_grc"><img src="/images/user/game/service/text_writer02.gif"></td>
    <td width="1" valign="bottom" ><img src="/images/user/game/service/vline.gif"></td>
    <td width="273" class="tbl_gleft"><%=s_name%></td>
    <td width="76" class="tbl_grc"><img src="/images/user/game/service/text_write_day02.gif"></td>
    <td width="1" valign="bottom" ><img src="/images/user/game/service/vline.gif"></td>
    <td width="255" class="tbl_gleft"><%= FormatDate.getDate("yyyy-MM-dd") %></td>
  </tr>
  <tr> 
    <td class="tbl_grc"><img src="/images/user/game/service/text_subject02.gif"></td>
    <td valign="bottom"><img src="/images/user/game/service/vline.gif"></td>
    <td colspan="4" class="font_specialroom_list"><input name="p_title" type="text" class="input2" size="80"></td>
  </tr>
  <tr align="center"> 
    <td height="63" colspan="6" class="tbl_grc"><img src="/images/user/game/service/text_page.gif" width="37" height="11"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="2" class="linecolor_ser3"></td>
  </tr>
  <tr> 
    <td height="2"></td>
  </tr>
  <tr>
    <td height="156" valign="top">&nbsp;
        <!-- DHTML Editor  -->
        <%@ include file="/include/DhtmlEditor.jsp" %>
        <!-- DHTML Editor  -->
        <br>
    </td>
  </tr>
  
  <tr> 
    <td height="2" colspan="4" class="linecolor_ser3"></td>
  </tr>
   <tr> 
    <td height="4"></td>
  </tr>
  <tr> 
    <td valign="top">
    <table width="680" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/user/game/service/i_add.gif"> <img src="/images/user/game/service/text_t_largeimages.gif" width="47" height="11"></td>
          <td class="tbl_bleft"><input name="p_file1" type="FILE" class="input2" size="60">
            <a href="#"><!--img src="/images/user/game/button/btn_find_long.gif" border="0" align="absmiddle"--></a> <b>(322 X 217) </b>
          </td>
        </tr>
        <tr>
          <td><img src="/images/user/game/service/i_add.gif"> <img src="/images/user/game/service/text_t_smallimages.gif" width="47" height="11"></td>
          <td class="tbl_bleft"><input name="p_file2" type="FILE" class="input2" size="60">
            <a href="#"><!--img src="/images/user/game/button/btn_find_long.gif" border="0" align="absmiddle"--></a> <b>(102 X  62) </b>
          </td>
        </tr>
        <tr>
          <td width="83"><img src="/images/user/game/service/i_add.gif"> <img src="/images/user/game/service/text_t_movie.gif"></td>
          <td width="597" class="tbl_bleft"><input name="p_file3" type="FILE" class="input2" size="60">
            <a href="#"><!--img src="/images/user/game/button/btn_find_long.gif" border="0" align="absmiddle"--></a> <b>(30M 미만)</b>  
          </td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td height="4"></td>
  </tr>
</table>
<table width="720" height="1" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td class="linecolor_ser3"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="2" colspan="6" class="linecolor_ser3"></td>
  </tr>
  <tr> 
    <td height="5" colspan="6"></td>
  </tr>
  <tr> 
    <td width="10" align="center" valign="bottom">&nbsp;</td>
    <td width="303" align="right" valign="bottom">&nbsp;</td>
    <td width="164" align="right" valign="bottom">&nbsp;</td>

    <%if( s_gadmin.equals("A1") || (s_gadmin.equals("H1") && (1==1)) || s_gadmin.equals("B2")){ %>
    <td width="141" valign="bottom">
        <input type="checkbox" name="p_useyn" value="checkbox">
      메인에서 사용
    </td>
    <% } else { %>
     	<input type="hidden" name="p_useyn" value="N">
    <% } %>
    <td width="48" align="right" valign="bottom">
        <a href="javascript:insert()"><img src="/images/user/game/button/btn_register.gif" width="48" height="21"></td>
    <td width="54" align="right" valign="bottom">
        <a href="javascript:listView()"><img src="/images/user/game/button/btn_list.gif" border="0"></a></td>
  </tr>
  <tr> 
    <td height="5" colspan="6" ></td>
  </tr>
  <tr> 
    <td height="2" colspan="6" class="linecolor_ser3"></td>
  </tr>
</table>

</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
