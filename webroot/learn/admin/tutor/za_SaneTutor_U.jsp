<%
//**********************************************************
//  1. 제      목: 사내 TUTOR UPDATE PAGE
//  2. 프로그램명: za_SaneTutor_U.jsp
//  3. 개      요: 사내 강사 수정 page
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 12. 11
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.tutor.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_process        = box.getString("p_process");
    String v_search         = box.getString("p_search");        //전문분야 검색
    String v_select         = box.getString("p_select");        //검색항목(과정명1,강사명2)
    String v_selectvalue    = box.getString("p_selectvalue");   //검색어
    String p_process        =  "TutorUpdate";
    String v_userid         = "";
    String v_resno          = "";
    String v_pwd            = "";
    String v_name           = "";
    String v_sex            = "";
    String v_add1           = "";
    String v_add2           = "";
    String v_phone          = "";
    String v_handphone      = "";
    String v_email          = "";
    String v_comp           = "";
    String v_dept           = "";
    String v_jik            = "";
    String v_academic       = "";
    String v_major          = "";
    String v_isadd          = "";
    String v_iscyber        = "";
    String v_isgubun        = "";
    String v_isgubuntype    = "";
    String v_isstatus       = "";
    String v_istutor        = "";
    String v_license        = "";
    String v_intro          = "";
    String v_career         = "";
    String v_book           = "";
    String v_grcode         = "";
    String v_charge         = "";
    String v_isinfo         = "";
    String v_professional   = "";
    String v_etc            = "";
    String v_subj           = "";
    String v_subjnm         = "";
    String v_fmon           = "";
    String v_tmon           = "";
    String v_compcd         = "";
    String v_subjclass      = "";
    String v_ismanager      = "";
    String v_post1          = "";
    String v_post2          = "";
    String v_loginid        = "";
    String v_loginpw        = "";
    String v_edustart		= "";
    String v_eduend		= "";
    int v_managerchk        = 0;
    int v_index1            = 0;
    int v_index2            = 0;
    String v_year="";
    String v_subjseq="";

    Vector v_photoVector = null;          //      원파일명 배열
    Vector v_newphotoVector = null;          //      서버에 저장되있는 파일명 배열
    
    DataBox dbox = (DataBox)request.getAttribute("tutorSelect");
    
    v_userid                = dbox.getString("d_userid");
    v_resno                 = dbox.getString("d_resno");    
    v_pwd                   = dbox.getString("d_pwd");    
    v_name                  = dbox.getString("d_name");
    v_post1                 = dbox.getString("d_post1");
    v_post2                 = dbox.getString("d_post2");
    v_add1                  = dbox.getString("d_add1");
    v_add2                  = dbox.getString("d_add2");
    v_phone                 = dbox.getString("d_phone");
    v_handphone             = dbox.getString("d_handphone");
    v_email                 = dbox.getString("d_email");
    v_comp                  = dbox.getString("d_comp");
    v_dept                  = dbox.getString("d_dept");
    v_jik                   = dbox.getString("d_jik");
    v_academic              = dbox.getString("d_academic");
    v_major                 = dbox.getString("d_major");
    v_isadd                 = dbox.getString("d_isadd");
    v_iscyber               = dbox.getString("d_iscyber");
    v_isgubun               = dbox.getString("d_isgubun");
    v_isgubuntype           = dbox.getString("d_isgubuntype");
    v_isstatus              = dbox.getString("d_isstatus");
    v_istutor               = dbox.getString("d_istutor");
    v_career                = dbox.getString("d_career");
    v_book                  = dbox.getString("d_book");
    v_professional          = dbox.getString("d_professional");
    v_charge                = dbox.getString("d_charge");
    v_isinfo                = dbox.getString("d_isinfo");
    v_etc                   = dbox.getString("d_etc");
    v_fmon                  = dbox.getString("d_fmon");        
    v_tmon                  = dbox.getString("d_tmon");   
    v_managerchk            = dbox.getInt("d_managerchk");
    v_subjclass             = dbox.getString("d_subjclass");
    v_intro                 = dbox.getString("d_intro");
    v_ismanager             = dbox.getString("d_ismanager");        

    if(v_managerchk == 1){
        if(v_fmon.length() > 0) v_fmon  = FormatDate.getFormatDate(v_fmon,"yyyy/MM/dd");
        if(v_tmon.length() > 0) v_tmon  = FormatDate.getFormatDate(v_tmon,"yyyy/MM/dd");
    }else{
        v_fmon = "";
        v_tmon = "";
    }
System.out.println(" v_photoVector "+dbox.getObject("d_photo"));
        v_photoVector = (Vector)dbox.getObject("d_photo");
        v_newphotoVector = (Vector)dbox.getObject("d_newphoto");
    
    ArrayList list = (ArrayList)request.getAttribute("tutorSubjList");    

%>

<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "javascript">
$(document).ready(function(){
	$("#p_sdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_ldate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
    v_isgubun = <%=v_isgubun%>;
    
    function changedOn(x) {
/*
            //alert("changedOn");
        if (x == 1) {
            //alert("sane :  "+x);
            do_sane();
        } 
        else if (x == 2) {
            //alert("saoi :  "+x);
            do_saoi();
        }
*/
        do_sane();
    }
    
    function searchPost() {
        window.self.name = "personalInform";
        open_window("openPost","","100","100","367","500","","","","yes","");
        document.form1.target = "openPost";
        document.form1.action='/servlet/controller.library.PostSearchServlet';
        document.form1.p_process.value = 'SearchPostOpenPage';
        document.form1.submit();
    }
    
    function do_sane() {
        //사내강사
        //성명 검색 버튼 Disable
        //ID/PW Enabled
        //ReadOnly Disable
        srchnm.style.display ='';
        addr.style.display ='none';
        chk_readOnly(true);
        document.all.span_userid.innerHTML = "<strong>아이디</strong>";
        document.all.span_resno.innerHTML = "";
        
        auth();
        value_ini();
    
    }
    
    function do_saoi() {
        //사외강사
         srchnm.style.display ='none';
         addr.style.display ='';
         chk_readOnly(false);
         document.all.span_userid.innerHTML = "<strong>아이디</strong>";
         document.all.span_resno.innerHTML = "(-없이)";
         
         auth();
         value_ini();
    
    }
    
    function chk_readOnly(gubun) {
        if (gubun) {
            //사내강사는 입력할 수 없도록 설정
            document.form1.p_userid.readOnly = true;
            document.form1.p_phone.readOnly = true;
            document.form1.p_handphone.readOnly = true;
            document.form1.p_comp.readOnly = true;
            document.form1.p_email.readOnly = true;
            //document.form1.p_jik.readOnly = true;
        } else {
            //사외강사는 입력할 수 있도록 설정
            document.form1.p_userid.readOnly = false;
            document.form1.p_phone.readOnly = false;
            document.form1.p_handphone.readOnly = false;
            document.form1.p_comp.readOnly = false;
            document.form1.p_email.readOnly = false;
            //document.form1.p_jik.readOnly = false;
        }
    }
    
    function value_ini() {
    
        document.form1.p_userid.value = "";
        //document.form1.p_loginid.value = "";
        //document.form1.p_loginpw.value = "";
        document.form1.p_idchk.value = "";
        document.form1.p_name.value = "";
        document.form1.p_phone.value = "";
        document.form1.p_handphone.value = "";
        document.form1.p_comp.value = "";
        document.form1.p_compcd.value = "";
        //document.form1.p_jik.value = "";
    }
    
    
    function auth() {
        ff = document.form1;
        <%  if (StringManager.substring(box.getSession("gadmin"),0,1).equals("A") || StringManager.substring(box.getSession("gadmin"),0,1).equals("B")) { %>
            if (ff.p_manager[0].checked) { //권한부여
                term.style.display = '';
                subj.style.display = '';
                
            } else {//권한 미부여
                term.style.display ='none';
                subj.style.display ='none';
                
            }
        <% }%>
        
    }
    
    
    function open_window(name, url, left, top, width, height, toolbar, menubar, statusbar, scrollbar, resizable)
    {
        toolbar_str = toolbar ? 'yes' : 'no';
        menubar_str = menubar ? 'yes' : 'no';
        statusbar_str = statusbar ? 'yes' : 'no';
        scrollbar_str = scrollbar ? 'yes' : 'no';
        resizable_str = resizable ? 'yes' : 'no';
        window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar='+toolbar_str+',menubar='+menubar_str+',status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
    }

    function cancel() {
        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.tutor.TutorAdminServlet";
        document.form1.p_process.value = "TutorList";
        document.form1.submit();
    }

    function searchSubj() {
        var url = "/servlet/controller.library.SearchServlet?p_process=subjseq&p_gubun=subjnm&p_key1=&p_key2=";
        open_window("",url,"0","0","100","100");
    }

    function receiveSubj(subj,subjnm){
        var j = document.form1.p_subj.length;
        var f_exist = "";
        for(var i=0;i<j;i++){
            if(document.form1.p_subj.options[i].value==subj){
                f_exist = "Y";
                alert(subjnm+"은 이미 선정되어 있습니다");
            }
        }
        if (f_exist != "Y"){
            document.form1.p_subj.options[j] = new Option(subjnm,subj);
            document.form1.p_subj.options[j].selected = true;
        }
    }

    function receiveSubjseq(subj,subjnm,key,year,subjseq){
        var j = document.form1.p_subj.length;
        var f_exist = "";
        var tmp=subj+"/"+year+"/"+subjseq;

        for(var i=0;i<j;i++){
            if(document.form1.p_subj.options[i].value==tmp){
                f_exist = "Y";
                alert(subjnm+"은 이미 선정되어 있습니다");
            }
        }
        if (f_exist != "Y"){
            document.form1.p_subj.options[j] = new Option(subjnm,tmp);
            document.form1.p_subj.options[j].selected = true;
        }
    }

    function delSubj(){
        for(var i = 0 ;i<document.form1.p_subj.length;i++){
            if(document.form1.p_subj.options[i].selected==true)
                document.form1.p_subj.options[i] = null;
            }
    }

     function u_update() {
        ff = document.form1;
/*      
        공통 체크
        if (ff.p_subjclass.value=="") {
            alert("강의분야를 선택하세요.");
            return;
        }

        if (blankCheck(document.form1.p_name.value)) {
            alert("성명을 입력하세요!");
            document.form1.p_name.focus();
            return;
        }  

        if (blankCheck(document.form1.p_userid.value)) {
            if(ff.p_isgubun.value=="0") {
                alert("검색버튼을 클릭하여 사내강사를 선택하세요.");
            } else {
                alert("아이디를 입력하세요.");
            }
            document.form1.p_userid.focus();
            return;
        }
*/

        
        var st_date = make_date(document.form1.p_sdate.value);
        var ed_date = make_date(document.form1.p_ldate.value); 
        document.form1.p_fmon.value = st_date;
        document.form1.p_tmon.value = ed_date;
        
    
<%  if (StringManager.substring(box.getSession("gadmin"),0,1).equals("A") || StringManager.substring(box.getSession("gadmin"),0,1).equals("B")) { %>
        // 권한부여시에만 과정체크
        if(ff.p_manager[0].checked) {
            if (ff.p_sdate.value=="") {
                alert("권한사용기간을 입력하세요.");
                return;
            }
            
            if (ff.p_ldate.value=="") {
                alert("권한사용기간을 입력하세요.");
                return;
            }
        
        
            //if ( document.form1.p_subj.length == 0 ) {
            //  alert("강의과정을 지정해주십시오. ");
            //  return;
            //}
            
            /*
            if (v_isgubun=="2") {
                
                if (ff.p_loginpw.value=="") {
                    alert("비밀번호를 입력하세요.")
                    ff.p_loginpw.focus();
                    return;
                }
                
                if (ff.p_loginpw2.value=="") {
                    alert("비밀번호를 입력하세요.")
                    ff.p_loginpw2.focus();
                    return;
                }
                
                if (ff.p_loginpw.value!=ff.p_loginpw2.value) {
                    alert("비밀번호를 확인하세요.")
                    ff.p_loginpw.focus();
                    return;
                }
            }
            */
        }
<% } %>         
        
        //사외강사는 경력,학력,소속입력 필수
        //사외강사는 전화번호,이동전화중 하나는 입력 필수
        if(ff.p_isgubun.value=="2") {
/*
            if (ff.p_phone.value=='' && ff.p_handphone.value=='') {
                alert('전화번호 또는 휴대폰번호를 입력하세요.');
                return;
            }
            
            if (ff.p_comp.value=='') {
                alert('현재소속을 입력하세요.');
                ff.p_comp.focus();
                return;
            }
            
            if (ff.p_academic.value=='') {
                alert('학력을 입력하세요.');
                ff.p_academic.focus();
                return;
            }
            
            if (ff.p_career.value=='') {
                alert('경력을 입력하세요.');
                ff.p_career.focus();
                return;
            }
*/
        }

        var islimit1 = true;
        var file1 = document.form1.p_file1.value;


        if(file1!="") {islimit1 = limitFile(file1);}
        if(!islimit1){  return; }
        

        for(i=0;i<document.form1.p_subj.length;i++) {
            document.form1.p_subj[i].selected = true;
        }
        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.tutor.TutorAdminServlet";
        document.form1.p_process.value = "TutorUpdate";
        document.form1.submit();
    }
    
    function whenSelection() {
        document.form1.p_process.value = 'SaneTutorUpdatePage';
        //대분류 선택시 중분류를 ALL로 초기화
        //document.form1.s_middleclass.value = '';
        document.form1.submit();
    }
//-->
</script>

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


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name = "form1" method = "post" enctype ="multipart/form-data">
<input type="hidden" name="p_process">
<input type="hidden" name="p_isgubun" value="<%=v_isgubun%>">     <!--isgubun 1 : 교수, isgubun 2 : 강사 -->
<input type="hidden" name="p_search"    value="<%=v_search%>">
<input type="hidden" name="p_select"    value="<%=v_select%>">
<input type="hidden" name="p_selectvalue" value="<%=v_selectvalue%>">
<input type="hidden" name="p_fmon"  value="">
<input type="hidden" name="p_tmon"  value="">
<input type="hidden" name="p_compcd" value="<%=v_compcd%>">
<input type="hidden" name="p_isinfo" value="N">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

       <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/tutor/tit_new_01.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
 <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>강사수정</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 

      
    <table class="form_table_out" cellspacing="1" cellpadding="0">
     <tr> 
       <td> 
         <table cellspacing="1" cellpadding="1" class="form_table_bg">
        <%//권한부여는 총괄관리자만 %>
        <%  if (StringManager.substring(box.getSession("gadmin"),0,1).equals("A") || StringManager.substring(box.getSession("gadmin"),0,1).equals("B")) { %>
           <tr> 
             <td width="10%" class="table_title"><b>강사권한여부</b></td>
             <td colspan="5" class="table_02_2"> <input type="radio" name="p_manager" value="Y" <% if(v_ismanager.equals("Y")){ %> checked <% } %> onClick="auth();">
              Yes 
              <input type="radio" name="p_manager" value="N" <% if(v_ismanager.equals("N")){ %> checked <% } %> onClick="auth();">
              No</td>
           </tr>
          <% } else { %>
            <input type="hidden" name="p_manager" value="N">
          <% } %>
         </table>
        </td>
      </tr>
    </table>
      <br>
      
        <!----------------- 강사등록 시작 ----------------->
		<table width = "970" border = "0" cellpadding = "0" cellspacing = "1" class = "table_out">
			<tr>
				<td colspan = "4" class = "table_top_line"></td>
			</tr>
			
			<tr>
				<td width = "10%" class = "table_title" align="center"><b>강사구분</b></td>
				<td class = "table_02_2" colspan = "3">
					<select name = "p_isgubun" style = "width:80" onchange = "changedOn(this.value);">
						<option <%if(v_isgubun.equals("1")){ out.println("selected");}%> value = "1">교수</option>
						<option <%if(v_isgubun.equals("2")){ out.println("selected");}%> value = "2">강사</option>
					</select>
            	</td>
			</tr>
          
			<tr id=term style="display: <% if (v_ismanager.equals("N")) out.print("none");%>"> 
				<td width="10%" class="table_title" align="center"><strong>권한사용기간</strong></td>
				<td colspan="3" class="table_02_2">
            		<input name="p_sdate" id="p_sdate" class="datepicker_input1" type="text" size="10" value='<%=v_fmon%>'>
						~ 
					<input name="p_ldate" id="p_ldate" class="datepicker_input1" type="text" size="10" value='<%=v_tmon%>'> 
            	</td>
			</tr>
          
			<tr> 
				<td width="10%" class="table_title" align="center"><b>강사명(ID)</b></td>
            	<td colspan="3" class="table_02_2"><%=v_name%>(<%=v_userid%>)
					<input name="p_name" type="hidden"  value="<%=v_name%>">
					<input name="p_userid" type="hidden" value="<%=v_userid%>">
            	</td>
			</tr>
			
			<tr id="pwd" style="display: none"> 
            	<td width="10%" class="table_title" align="center"><strong>* 비밀번호</strong></td>
            	<td width="40%" class="table_02_2"> <input name="p_loginpw" type="password" class="input" maxlength="20"></td>
            	<td width="10%" class="table_title" align="center"><strong>* 비밀번호 확인</strong></td>
            	<td width="40%" class="table_02_2"><input name="p_loginpw2" type="password" class="input" maxlength="20"></td>
          	</tr>
          	
			<tr>
            	<td width="10%" class="table_title" align="center"><strong>주민등록번호</strong></td>
            	<td width="40%" class="table_02_2">
            		<input name="p_resno1" type="text" class="input" readonly value="<%=v_resno.length()<6?"":v_resno.substring(0,6)+" - *******"%>">
					<input name="p_resno" type="hidden" value="<%=v_resno%>">
				</td>
            	<td width="10%" class="table_title" align="center"><b>E-mail</b></td>
            	<td class="table_02_2"><input name="p_email" type="text" class="input" maxlength="40" value="<%=v_email%>"></td>     
			</tr> 

			<tr> 
            	<td width="10%" class="table_title" align="center"><b>주소</b></td>
            	<td width="40%" class="table_02_2" colspan="3">
              		<table width="90%" border="0" cellspacing="0" cellpadding="0">
                		<tr> 
                   			<td width="76">
                   				<input type="text" name="p_post1" size="3" maxlength="3" class="input" readonly value="<%=v_post1%>">
                   				 -  
								<input type="text" name="p_post2" size="3" maxlength="3" class="input" readonly value="<%=v_post2%>"> 
							</td>
							<td id=addr style="display: none"><a href="javascript:searchPost()"><img src="/images/admin/button/btn_address.gif" border="0"></a></td>
						</tr>
						<tr> 
							<td colspan=2>
								<input name="p_addr" type="text" class="input" size="50" maxlength="50" value="<%=v_add1%>">
								<input name="p_addr2" type="text" class="input" size="50" maxlength="50" value="<%=v_add2%>">
							</td>
						</tr>
					</table>  
				</td>
			</tr>
			
			<tr> 
	            <td width="10%" class="table_title" align="center"><b>전화번호</b></td>
    	        <td class="table_02_2"><input name="p_phone" type="text" class="input" maxlength="20" readonly value="<%=v_phone%>"></td>
        	    <td class="table_title"><b>휴대폰</b></td>
            	<td class="table_02_2"><input name="p_handphone" type="text" class="input" maxlength="13" readonly value="<%=v_handphone%>"> </td>
			</tr>
			
			<tr> 
            	<td width="10%" class="table_title" align="center"><b>현재소속</b></td>
            	<td class="table_02_2" colspan="3"><input name="p_comp" type="text" class="input" maxlength="40" readonly value="<%=v_comp%>"></td>
			</tr>
			
			<tr id=subj style="display: <% if (v_ismanager.equals("N")) out.print("none");%>">
            	<td class="table_title" align="center"><strong>강의과정</strong></td>
            	<td colspan="3" class="table_02_2">
            		<table width="100%" border="0" cellpadding="0" cellspacing="0">
            			<tr>
            				<td width="90%">
<!--            					 | NO. | 교육년도 | 과정차수 | 과정명-->
		                    	<select name="p_subj" size=5  multiple style="width: 100%;">
								<%
									for(int i = 0; i < list.size(); i++) {
			                            TutorData data2  = (TutorData)list.get(i);
			                            v_subj           = data2.getSubj();
			                            v_subjnm         = data2.getSubjnm();
			                            v_year			 = data2.getYear();
			                            v_subjseq		 = data2.getSubjseq();
			                            v_edustart		 = data2.getEdustart();
			                            v_eduend		 = data2.getEduend();
			                            if(v_year.length()==0)
			                                v_year="0000";
			                            if(v_subjseq.length()==0)
			                                v_subjseq="0000";
			                            if(v_edustart.length()==0)
			                            	v_edustart="00000000";
			                            if(v_eduend.length()==0)
			                            	v_eduend="00000000";
			                            String v_value=v_subj+"/"+v_year+"/"+v_subjseq;
			                            String tmp=v_year+" | "+v_subjseq+" ("+v_edustart+"~"+v_eduend+") | ["+v_subj+"] "+v_subjnm;
								%>
										<option value="<%=v_value%>">
												<%=tmp %>
										<!--
												|&nbsp;&nbsp;&nbsp;<%=i+1%>&nbsp;&nbsp;&nbsp;
												|&nbsp;&nbsp;&nbsp;<%=v_year%>&nbsp;&nbsp;&nbsp;
												|&nbsp;&nbsp;&nbsp;<%=v_subjseq%>&nbsp;&nbsp;&nbsp;
												|&nbsp;&nbsp;&nbsp;<%=v_subjnm%>
										-->
										</option>
								<%}%>
								</select>
							</td>
							<td width="10%" align="center" valign="middle">
                    			<a href="javascript:searchSubj()"><img src="/images/admin/button/btn_csearch.gif" border="0"></a><br/>
                    			<a href="javascript:delSubj()"><img src="/images/admin/button/btn_cdelete.gif" border="0"></a>
                    		</td>
                    	</tr>
					</table>
				</td>
			</tr>
			
			<tr> 
            	<td width="10%" class="table_title" align="center"><b>강사소개말</b></td>
            	<td class="table_02_2" colspan="3"> <textarea name="p_intro" cols="100" style="height:80"><%=v_intro%></textarea></td>
			</tr>
			
			<tr> 
            	<td width="10%" class="table_title" align="center"><b>학력사항</b></td>
            	<td colspan="3" class="table_02_2"> <textarea name="p_academic" cols="100" style="height:80"><%=v_academic%></textarea></td>
			</tr>
			
			<tr> 
            	<td width="10%" class="table_title" align="center"><b>경력사항</b></td>
            	<td colspan="3" class="table_02_2"> <textarea name="p_career" cols="100" style="height:80"><%=v_career%></textarea></td>
			</tr>
			
			<tr> 
            	<td width="10%" class="table_title" align="center"><b>저서</b></td>
            	<td colspan="3" class="table_02_2"><textarea name="p_book" cols="100" style="height:80"><%=v_book%></textarea></td>
			</tr>
			
			<tr>
            	<td width="10%" class="table_title" align="center"><b>사진파일</b></td>
            	<td width="90%" colspan="3" class="table_02_2" >
              		<input name="p_file1" type="file" class="input" size="96" maxlength="100" tabindex=65><br/>
              		<font color="blue">권장크기[넓이(Width) : 85px, 높이(Height) : 113px]</font>
					<%
					if( v_photoVector != null ) { 
						for(int i = 0; i < v_photoVector.size(); i++) {      //     첨부파일 갯수에 따라
							String v_photo = (String)v_photoVector.elementAt(i);
							String v_newphoto = (String)v_newphotoVector.elementAt(i);  

							if(v_photo != null && !v_photo.equals("")) {%>
							<br>첨부된 파일 : &nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_newphoto%>&p_realfile=<%= v_photo%>'>
							<%= v_photo%> </a>
							<%}%>
						<%}%>
					<%}%>
				</td>
			</tr>
		</table>
        <!----------------- 강사등록 끝 ----------------->

        <br>
        <!----------------- 등록, 취소 버튼 시작----------------->
        <table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center" class="ms"><a href="javascript:u_update()"><img src="/images/admin/button/btn_modify.gif" border="0"></a></td>
            <td width=8></td>
            <td align="center" class="ms"><a href="javascript:cancel()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 등록, 취소 버튼 끝----------------->
        <br>
        <br>
      </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>

</body>

