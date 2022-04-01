<%
//**********************************************************
//  1. 제      목: 사외 TUTOR UPDATE PAGE
//  2. 프로그램명: za_SaoiTutor_U.jsp
//  3. 개      요: 사외 강사 수정 page
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 18
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.tutor.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");    
    String	v_process		= box.getString("p_process"); 
	String v_search     	= box.getString("p_search");	    //전문분야 검색
	String v_select			= box.getString("p_select");		//검색항목(과정명1,강사명2)
	String v_selectvalue	= box.getString("p_selectvalue");	//검색어    
    String  p_process   =  "TutorUpdate";
	String v_userid			= "";
	String v_name			= "";
	String v_gender			= "";
	String v_post1			= "";
	String v_post2			= "";
	String v_add1			= "";
	String v_phone			= "";
	String v_phone1	    	= "";
	String v_phone2	 	    = "";
	String v_phone3 		= "";
	String v_handphone		= "";
	String v_handphone1		= "";
	String v_handphone2		= "";
	String v_handphone3		= "";
	String v_fax			= "";
	String v_fax1			= "";
	String v_fax2			= "";
	String v_fax3			= "";
	String v_email			= "";
	String v_comp			= "";
	String v_dept			= "";
	String v_jik			= "";
	String v_academic		= "";
	String v_major			= "";
	String v_isadd			= "";
	String v_iscyber		= "";
	String v_isgubun		= "";
	String v_isgubuntype	= "";
	String v_isstatus		= "";
	String v_istutor		= "";	 
	String v_license		= "";
	String v_career			= "";
	String v_book			= "";
	String v_grcode			= "";
	String v_charge			= "";
	String v_isinfo			= "";
	String v_professional   = "";
	String v_etc            = "";
	String v_subj           = "";
	String v_subjnm         = "";
	int	v_careeryear		= 0;	    
    int v_index1            = 0;	    
    int v_index2            = 0;	    
    String v_manager	    = "";
	String v_fmon		    = "";
	String v_tmon		    = "";

    DataBox dbox  = (DataBox)request.getAttribute("tutorSelect");
	v_userid				= dbox.getString("d_userid");
	v_name					= dbox.getString("d_name");
	v_gender				= dbox.getString("d_gender");  
	v_post1					= dbox.getString("d_post1");
	v_post2					= dbox.getString("d_post2");
	v_add1					= dbox.getString("d_add1");
	v_phone					= dbox.getString("d_phone");
	v_handphone				= dbox.getString("d_handphone");
	v_fax					= dbox.getString("d_fax");
	v_email					= dbox.getString("d_email");
	v_comp					= dbox.getString("d_comp");
	v_dept					= dbox.getString("d_dept");
	v_jik					= dbox.getString("d_jik");
	v_academic				= dbox.getString("d_academic");
	v_major					= dbox.getString("d_major");
	v_isadd					= dbox.getString("d_isadd");
	v_iscyber				= dbox.getString("d_iscyber");
	v_isgubun				= dbox.getString("d_isgubun");
	v_isgubuntype			= dbox.getString("d_isgubuntype");
	v_isstatus				= dbox.getString("d_isstatus");
	v_istutor				= dbox.getString("d_istutor");
	v_careeryear			= dbox.getInt("d_careeryear");
	v_license				= dbox.getString("d_license");
	v_career				= dbox.getString("d_career");
	v_book					= dbox.getString("d_book");
	v_grcode				= dbox.getString("d_grcode");
	v_professional			= dbox.getString("d_professional");
	v_charge				= dbox.getString("d_charge");
	v_isinfo				= dbox.getString("d_isinfo");
	v_etc                   = dbox.getString("d_etc");   
	
	v_manager				= dbox.getString("d_managerchk");
	v_fmon					= dbox.getString("d_fmon");
	v_tmon					= dbox.getString("d_tmon");
	
	String v_rphoto          = dbox.getString("d_realphoto");
	String v_photo          = dbox.getString("d_photo");
	

	//전화번호 substring
	/*v_index2                = v_phone.lastIndexOf("-");
	v_phone1                = v_phone.substring(0,v_index2);
    v_index1                = v_phone1.lastIndexOf("-");
    v_phone1                = v_phone.substring(0,v_index1);
    v_phone2                = v_phone.substring(v_index1+1,v_index2);
    v_phone3                = v_phone.substring(v_index2+1);
    */

    //핸드폰번호 substring
	v_index2                = v_handphone.lastIndexOf("-");
	v_handphone1            = v_handphone.substring(0,v_index2);
    v_index1                = v_handphone1.lastIndexOf("-");
    v_handphone1            = v_handphone.substring(0,v_index1);
    v_handphone2            = v_handphone.substring(v_index1+1,v_index2);
    v_handphone3            = v_handphone.substring(v_index2+1);

	//팩스번호 substring
	v_index2                = v_fax.lastIndexOf("-");
	v_fax1                  = v_fax.substring(0,v_index2);
    v_index1                = v_fax1.lastIndexOf("-");
    v_fax1                  = v_fax.substring(0,v_index1);
    v_fax2                  = v_fax.substring(v_index1+1,v_index2);
    v_fax3                  = v_fax.substring(v_index2+1);
    //DEFINED class&variable END

	ArrayList list = (ArrayList)request.getAttribute("tutorSubjList"); 
%>
<html>
<head>
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
		$("#p_firstmon").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
		$("#p_endmon").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	});
	<!--
				
        function open_window(name, url, left, top, width, height, toolbar, menubar, statusbar, scrollbar, resizable)
        {
            toolbar_str = toolbar ? 'yes' : 'no';
            menubar_str = menubar ? 'yes' : 'no';
            statusbar_str = statusbar ? 'yes' : 'no';
            scrollbar_str = scrollbar ? 'yes' : 'no';
            resizable_str = resizable ? 'yes' : 'no';
            window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar='+toolbar_str+',menubar='+menubar_str+',status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
        }
				
        function searchPost() {
            window.self.name = "personalInform";
            open_window("openPost","","100","100","367","500","","","","yes","");
            document.form1.target = "openPost";
			document.form1.action='/servlet/controller.library.PostSearchServlet';
            document.form1.p_process.value = 'SearchPostOpenPage';
            document.form1.submit();
        }

	    function cancel() {
	        document.form1.target = "_self";
            document.form1.action = "/servlet/controller.tutor.TutorAdminServlet";
            document.form1.p_process.value = "TutorList";      
            document.form1.submit();	
        }       
                
        function searchSubj() {
            var url = "/servlet/controller.library.SearchServlet?p_process=subj&p_gubun=subjnm&p_key1=&p_key2=";
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
        
        function delSubj(){
            for(var i = 0 ;i<document.form1.p_subj.length;i++){
                if(document.form1.p_subj.options[i].selected==true)
                	document.form1.p_subj.options[i] = null;
                }
        }
        
        function u_update() { 
            if (blankCheck(document.form1.p_name.value)) {
                alert("성명을 입력하세요!");
                document.form1.p_name.focus();
                return;
            }  
            if (blankCheck(document.form1.p_careeryear.value)) {
                document.form1.p_careeryear.value = "0";
            }      
            if(document.form1.p_file1.value.length > 0 ){
	        	if(chkFile() == false){
	        		return;
	        	}
	        }   
	        
	        if(!isValidEmail(form1.p_email)) {
            	alert("올바른 이메일 형식이 아닙니다.");
            	document.form1.p_email.focus();
            	return;
            }
 

			for(i=0;i<document.form1.p_subj.length;i++) {
				document.form1.p_subj[i].selected = true;
			}             			
			document.form1.p_fmon.value = make_date(document.form1.p_firstmon.value);
            document.form1.p_tmon.value = make_date(document.form1.p_endmon.value);
            
			document.form1.target = "_self";
            document.form1.action = "/servlet/controller.tutor.TutorAdminServlet?p_file=1";                   
            document.form1.p_process.value = "TutorUpdate";   
            document.form1.submit();
        }  
         function chkFile(){
		   	extArray = new Array(".jpg", ".gif"); // 업로드 할 수 있는 파일 확장자를 설정 합니다
			allowSubmit = false;
			
			var file = document.form1.p_file1.value;
			if (!file) return true;
			
				var first = file.lastIndexOf(".") ;
				ext = file.substring(first, file.length).toLowerCase() ;
				
				for (var i = 0; i < extArray.length; i++) {
					if (extArray[i] == ext) { allowSubmit = true; break; }
				}
			
			
			if (allowSubmit){ return true;
			}else{
				alert("업로드 가능한 파일은  " + (extArray.join("  ")) + "   입니다\n\n다시 선택 해 주세요");
				return false;
			}
	   }                 
    -->        
	</script>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post" enctype = "multipart/form-data">
<!--<form name = "form1" method = "post"> -->
<input type="hidden" name="p_process">
<input type="hidden" name="isgubun" value="<%=v_isgubun%>">
<input type="hidden" name="p_userid" value="<%=v_userid%>">
<input type="hidden" name="p_search"	value="<%=v_search%>">
<input type="hidden" name="p_select"	value="<%=v_select%>">
<input type="hidden" name="p_selectvalue" value="<%=v_selectvalue%>">
<input type="hidden" name="p_fmon" value="">
<input type="hidden" name="p_tmon" value="">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 

       <!----------------- title 시작 ----------------->
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="40"><img src="/images/admin/common/lms_titlebg01.gif" width="40" height="37"></td>
          <td width="170" background="/images/admin/common/lms_titlebg02.gif" style="padding-top:5" class="head_title">
         튜터정보수정
          </td>
          <td background="/images/admin/common/lms_titlebg03.gif">&nbsp;</td>
        </tr>
      </table>
      <!----------------- title 끝 --------------- -->


      <br>

        <!----------------- form 시작 ----------------->
        <table class="form_table_out" cellspacing="0" cellpadding="3">
          <tr> 
            <td bgcolor="#C6C6C6" align="center"> 

              <table cellspacing="0" cellpadding="0" class="form_table_bg">
                <tr> 
                  <td height="7" width="99%" colspan="2"></td>
                </tr>
                <tr> 
                  <td width="15%" valign="middle"> &nbsp;구분 : 
                        <% if(v_isgubuntype.equals("1")){ %> 사내강사 <%} %>
                        <% if(v_isgubuntype.equals("2")){ %> 사외강사 <%} %>
                  </td>
                  <td valign="middle"> 
                  </td>                  
                </tr>
                <tr>
                  <td height="7" width="99%" colspan="2"></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
        <!----------------- form 끝 ----------------->
        <br>
        <br>
        <!----------------- 강사등록 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="3" class="table_top_line"></td>
          </tr>
          <tr>          
            <td class="table_title" width="18%"><b>강사권한 사용여부</b></td>
            <td class="table_02_2">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    <select name="p_manager">
                    	<option value = "N" <% if(v_manager.equals("2")){ out.println("selected "); }%>>N</option>
                    	<option value = "Y" <% if(v_manager.equals("1")){ out.println("selected "); }%>>Y</option>                    	
                    </select>                    
                    사용기간 : <input name="p_firstmon" id="p_firstmon" type="text" class="datepicker_input1" size="10" value="<%=FormatDate.getFormatDate(v_fmon,"yyyy-MM-dd")%>">
                     ~ <input name="p_endmon" id="p_endmon" type="text" class="datepicker_input1" size="10" value="<%=FormatDate.getFormatDate(v_tmon,"yyyy-MM-dd")%>">
                    
                  </td>
                </tr>
              </table>
            </td>
          </tr>                 
          <tr>
            <td class="table_title" width="18%"><b>성명</b></td>
            <td class="table_02_2"> 
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td><input type="text" name="p_name" class="input" size="20" value=<%=v_name%> maxlength=10>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr> 
            <td class="table_title"><strong>성별</strong></td>
            <td class="table_02_2"> 
              <input type="radio" name="p_gender" value="1" <% if(v_gender.equals("1")){ %> checked <% } %>>
              남자 
              <input type="radio" name="p_gender" value="2" <% if(v_gender.equals("2")){ %> checked <% } %>>
              여자 </td>
          </tr>
          <tr> 
            <td class="table_title"><strong>아이디</strong></td>
            <td class="table_02_2"><%=v_userid%>
            </td>
          </tr>
          <!--tr> 
           <td class="table_title" rowspan='2'> <b>지역</b></td>
           <td class="table_02_2">
				<input type="text" name="p_post1" size="3" value="<%=v_post1%>" maxlength="3" class="input" readonly>
				  -
				<input type="text" name="p_post2" size="3" value="<%=v_post2%>"  maxlength="3" class="input" readonly>
				&nbsp;<a href="javascript:searchPost()"><img src="/images/user/study/study_zip_butt1.gif" border="0"></a>
           </td>
          </tr>
          <tr>
           <td class="table_02_2">
                <input name="p_addr" type="text" class="input" size="50" value="<%=v_add1%>"> 
           </td>
          </tr-->
          <tr> 
            <td class="table_title"><b>전화번호</b></td>
            <td class="table_02_2" colspan="2"> 
              <input name="p_phone1" value="<%=v_phone%>" type="text" class="input" size="20" maxlength="30">              
            </td>
          </tr>
      

          <tr> 
            <td class="table_title"><b>휴대폰번호</b></td>
            <td class="table_02_2" colspan="2"> 
              <input name="p_handphone1" value="<%=v_handphone1%>" type="text" class="input" size="5" maxlength="3">
              - 
              <input name="p_handphone2" value="<%=v_handphone2%>" type="text" class="input" size="5" maxlength="4">
              - 
              <input name="p_handphone3" value="<%=v_handphone3%>" type="text" class="input" size="5" maxlength="4">
            </td>
          </tr>

          <tr> 
            <td class="table_title"><b>E-mail</b></td>
            <td class="table_02_2" colspan="2"> 
              <input name="p_email" value="<%=v_email%>" type="text" class="input" size="50" maxlength="50">
            </td>
          </tr>

          <tr> 
            <td class="table_title"><b>소속기관</b></td>
            <td class="table_02_2" colspan="2"> 
              <input name="p_comp" value="<%=v_comp%>" type="text" class="input" size="40">
            </td>
          </tr>

          <tr> 
            <td class="table_title"><b>부서</b></td>
            <td class="table_02_2" colspan="2"> 
              <input name="p_dept" value="<%=v_dept%>" type="text" class="input" size="40">
            </td>
          </tr>


          <tr> 
            <td class="table_title"><b>직책</b></td>
            <td class="table_02_2" colspan="2"> 
              <input name="p_jik" value="<%=v_jik%>" type="text" class="input" size="20">
            </td>
          </tr>

          <tr> 
            <td class="table_title"><b>활동여부</b></td>
            <td class="table_02_2" colspan="2"> 
              <input type="radio" name="p_isstatus" value="Y" <% if(v_isstatus.equals("Y")){ %> checked <% } %>>
              Y 
              <input type="radio" name="p_isstatus" value="N" <% if(v_isstatus.equals("N")){ %> checked <% } %>>
              N
             </td>
          </tr>


          <tr> 
            <td class="table_title"><b>강사구분</b></td>
            <td class="table_02_2" colspan="2"> 
              <input type="radio" name="p_isgubuntype" value="1" <% if(v_isgubuntype.equals("1")){ %> checked <% } %>>
              사내 
              <input type="radio" name="p_isgubuntype" value="2" <% if(v_isgubuntype.equals("2")){ %> checked <% } %>>
              사외
             </td>
          </tr>


          <tr> 
            <td class="table_title"><b>강의과정</b></td>
            <td class="table_02_2" colspan="2">
 
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td> 
                    <select name="p_subj" size=4  multiple>               
                      <%                                                                                                                                                     
                        for(int i = 0; i < list.size(); i++) {
                            DataBox dbox1  = (DataBox)list.get(i);       
                            v_subj           = dbox1.getString("d_subj");  		              
                            v_subjnm         = dbox1.getString("d_subjnm");	                             
                       %>       
                       <option value="<%=v_subj%>"><%=v_subjnm%></option>
                       <% } %>                                       
                    </select>                    
                    <a href="javascript:searchSubj()"><img src="/images/admin/tutor/search3_butt.gif" width="58" height="18" border="0"></a>
                    <a href="javascript:delSubj()"><img src="/images/admin/tutor/course_del_butt.gif" width="58" height="18" border="0"></a>
                    </td>
                </tr>
              </table>

            </td>
          </tr>


          <tr> 
            <td class="table_title"><b>전문분야</b></td>
            <td class="table_02_2" colspan="2"> 
			<input type="text" class="input" size="30" name="p_professional" value="<%=v_professional%>">
            <!--%=CodeConfigBean.getCodeSelect("tutorProfessional","p_professional",v_professional)%-->  
            </td>
          </tr>


          <tr> 
            <td class="table_title"><b>최종학력</b></td>
            <td class="table_02_2" colspan="2"> 
              <input type="text" class="input" size="30" name="p_academic" value="<%=v_academic%>">
            </td>
          </tr>


          <tr> 
            <td class="table_title"><b>전공</b></td>
            <td class="table_02_2" colspan="2"> 
              <input type="text" class="input" size="30" name="p_major" value="<%=v_major%>">
            </td>
          </tr>

<!--
          <tr> 
            <td class="table_title"><b>구분</b></td>
            <td colspan="2" class="table_02_2"> 
              <input type="checkbox" name="p_iscyber" value="1" <% if(v_iscyber.equals("1")||v_iscyber.equals("3")){ %> checked <% } %>>
              집합교육강사
              <input type="checkbox" name="p_iscyber" value="2" <% if(v_iscyber.equals("2")||v_iscyber.equals("3")){ %> checked <% } %>>
              사이버교육 튜터</td>
          </tr>
-->
           <input type="hidden" name="p_iscyber" value="2">

          <tr> 
            <td class="table_title"><b>강사경력</b></td>
            <td class="table_02_2" colspan="2"> 
              <input type="text" class="input" size="5" name="p_careeryear" value="<%=v_careeryear%>" maxlength="2"> 년
            </td>
          </tr>


          <tr> 
            <td class="table_title"><b>자격증</b></td>
            <td class="table_02_2" colspan="2"> 
              <input type="text" class="input" size="40" name="p_license" value="<%=v_license%>">
            </td>
          </tr>


          <tr> 
            <td class="table_title"><b>경력사항</b></td>
            <td class="table_02_2" colspan="2"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td height="25"> ◈ 경 력 : <br>
                    <textarea name="p_career" cols="110"><%=v_career%></textarea>
                  </td>
                </tr>
                <tr><td height="8"></td></tr>
                <tr> 
                  <td height="25">◈ 저 서 :<br>
                    <textarea name="p_book" cols="110"><%=v_book%></textarea>
                  </td>
                </tr>
                <tr><td height="8"></td></tr>
                <tr> 
                  <td height="25">◈ 기 타 : <br>
                    <textarea name="p_etc" cols="110"><%=v_etc%></textarea>
                  </td>
                </tr>
              </table>
            </td>
          </tr>


      
<!--
          <tr> 
            <td class="table_title"><b>강사진소개여부</b></td>
            <td class="table_02_2" height="16" colspan="2"> 
              <input type="radio" name="p_isinfo" value="Y" <% if(v_isinfo.equals("Y")){ %> checked <% } %>>
              Yes 
              <input type="radio" name="p_isinfo" value="N" <% if(v_isinfo.equals("N")){ %> checked <% } %>>
              No</td>
          </tr>

-->
          <tr> 
            <td class="table_title"><b>사진파일첨부</b></td>
            <td class="table_02_2" colspan="2"> 

              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="100%"> 
                  <% if(v_photo.length() > 0) { %>
                       <input type="hidden" name="p_upfile1" value = "<%= v_rphoto %>">
                  	[이전파일]<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_rphoto %>&p_realfile=<%= v_photo %>'><%=v_photo%></a>
                  	<br>
                  <% }%>
                    <input type="file" class="input" size="18" name="p_file1">
                  </td>                 
                </tr>
              </table>

            </td>
          </tr>



        </table>
        <!----------------- 강사등록 끝 ----------------->
        <br>
        <!----------------- 등록, 취소 버튼 시작----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center" class="ms"><a href="javascript:u_update()"><img src="/images/admin/tutor/save_butt.gif" width="37" height="18" border="0"></a></td>
            <td align="center" class="ms"><a href="javascript:cancel()"><img src="/images/admin/tutor/cancel2_butt.gif" width="37" height="18" border="0"></a></td>
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
</html>
