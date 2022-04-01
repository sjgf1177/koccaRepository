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
    String	v_process		= box.getString("p_process"); 
	String v_search     	= box.getString("p_search");	    //전문분야 검색
	String v_select			= box.getString("p_select");		//검색항목(과정명1,강사명2)
	String v_selectvalue	= box.getString("p_selectvalue");	//검색어    
    String  p_process   =  "TutorUpdate";
	String v_userid			= "";
	String v_name			= "";
	String v_sex			= "";
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
    
    //TutorData data  = (TutorData)request.getAttribute("tutorSelect");
    DataBox dbox = (DataBox)request.getAttribute("tutorSelect");
	//v_userid				= data.getUserid();
	//v_name					= data.getName();
	//v_sex					= data.getSex();
	//v_post1					= data.getPost1();
	//v_post2					= data.getPost2();
	//v_add1					= data.getAdd1();
	//v_phone					= data.getPhone();
	//v_handphone				= data.getHandphone();
	//v_fax					= data.getFax();
	//v_email					= data.getEmail();
	//v_comp					= data.getComp();
	//v_dept					= data.getDept();
	//v_jik					= data.getJik();
	//v_academic				= data.getAcademic();
	//v_major					= data.getMajor();
	//v_isadd					= data.getIsadd();
	//v_iscyber				= data.getIscyber();
	//v_isgubun				= data.getIsgubun();
	//v_isgubuntype			= data.getIsgubuntype();
	//v_isstatus				= data.getIsstatus();
	//v_istutor				= data.getIstutor();
	//v_careeryear			= data.getCareeryear();
	//v_license				= data.getLicense();
	//v_career				= data.getCareer();
	//v_book					= data.getBook();
	//v_grcode				= data.getGrcode();
	//v_professional			= data.getProfessional();
	//v_charge				= data.getCharge();
	//v_isinfo				= data.getIsinfo();
	//v_etc                   = data.getEtc();  
	
	v_userid                = dbox.getString("d_userid");
    v_name                  = dbox.getString("d_name");
    v_sex                   = dbox.getString("d_sex");
    v_post1                 = dbox.getString("d_post1");
    v_post2                 = dbox.getString("d_post2");
    v_add1                  = dbox.getString("d_add1");
    v_phone                 = dbox.getString("d_phone");
    v_handphone             = dbox.getString("d_handphone");
    v_fax                   = dbox.getString("d_fax");
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
    v_careeryear            = dbox.getInt("d_careeryear");
    v_license               = dbox.getString("d_license");
    v_career                = dbox.getString("d_career");
    v_book                  = dbox.getString("d_book");
    //v_grcode                = dbox.getString("d_grcodenm");
    v_grcode                = dbox.getString("d_grcode");
    v_professional          = dbox.getString("d_professional");
    v_charge                = dbox.getString("d_charge");
    v_isinfo                = dbox.getString("d_isinfo");
    v_etc                   = dbox.getString("d_etc");
    //v_photo                 = dbox.getString("d_photo");
    //v_fmon                  = dbox.getString("d_fmon");        
    //v_tmon                  = dbox.getString("d_tmon");   
    //v_managerchk            = dbox.getInt("d_managerchk");
	
	
	
	
	 
    
	//전화번호 substring
	v_index2                = v_phone.lastIndexOf("-");
	v_phone1                = v_phone.substring(0,v_index2);
    v_index1                = v_phone1.lastIndexOf("-");
    v_phone1                = v_phone.substring(0,v_index1);
    v_phone2                = v_phone.substring(v_index1+1,v_index2);
    v_phone3                = v_phone.substring(v_index2+1);
    
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
    <script language = "javascript" src = "/script/cresys_lib.js"></script>
	<script language = "javascript">
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
/*			
			if ( document.form1.p_subj.length == 0 ) {
				alert("강의과정을 지정해주십시오. ");				
				return;
			}
*/
			for(i=0;i<document.form1.p_subj.length;i++) {
				document.form1.p_subj[i].selected = true;
			}             			
			document.form1.target = "_self";
            document.form1.action = "/servlet/controller.tutor.TutorAdminServlet?p_file=1";                   
            document.form1.p_process.value = "TutorUpdate";   
            document.form1.submit();
        }     
		
		function whenSelection() {
		    document.form1.p_process.value = 'SaoiTutorUpdatePage';
			//대분류 선택시 중분류를 ALL로 초기화
			//document.form1.s_middleclass.value = '';
		    document.form1.submit();
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
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/tutor/t_title01.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->

      <br>

        <!----------------- form 시작 ----------------->
        <table class="form_table_out" cellspacing="0" cellpadding="1">
          <tr> 
            <td bgcolor="#C6C6C6" align="center"> 

              <table cellspacing="0" cellpadding="0" class="form_table_bg">
                <tr> 
                  <td height="7" width="99%" colspan="2"></td>
                </tr>
                <tr> 
                  <td width="15%" valign="middle"> &nbsp;강사구분 : 
                        <% if(v_isgubun.equals("1")){ %> 사내강사 <%} %>
                        <% if(v_isgubun.equals("2")){ %> 사외강사 <%} %>
                  </td>
                  <td valign="middle"> <!--교육주관 :--> <%//= TutorAdminBean.getGrcodeSelect("p_grcode",v_grcode,1) %>
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
            <td class="table_title"><b>강의분야</b></td>
            <td class="table_02_2"><%= SelectSubjBean.getUpperClass(box, true, true, false)%> 
              <!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
              <%= SelectSubjBean.getMiddleClass(box, false, true, false)%> 
              <!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
            </td>
          </tr>
          <tr> 
            <td class="table_title"><b>강사권한여부</b></td>
            <td class="table_02_2" height="16"> <input type="radio" name="p_manager" value="Y" <% if(v_managerchk == 1){ %> checked <% } %>>
              Yes 
              <input type="radio" name="p_manager" value="N" <% if(v_managerchk == 0){ %> checked <% } %>>
              No</td>
          </tr>
          <tr> 
            <td class="table_title" width="18%"><b>성명</b></td>
            <td class="table_02_2"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td><input type="text" name="p_name" class="input" size="20" value=<%=v_name%> maxlength=10> 
                  </td>
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td class="table_title"><strong>주민등록번호</strong></td>
            <td class="table_02_2"></td>
          </tr>
          <tr> 
            <td class="table_title" rowspan='2'> <b>주소</b></td>
            <td class="table_02_2"> <input type="text" name="p_post1" size="3" value="<%=v_post1%>" maxlength="3" class="input" readonly>
              - 
              <input type="text" name="p_post2" size="3" value="<%=v_post2%>"  maxlength="3" class="input" readonly> 
              &nbsp;<a href="javascript:searchPost()"><img src="/images/admin/button/btn_address.gif" border="0"></a> 
            </td>
          </tr>
          <tr> 
            <td class="table_02_2"> <input name="p_addr" type="text" class="input" size="50" value="<%=v_add1%>"> 
            </td>
          </tr>
          <tr> 
            <td class="table_title"><b>전화번호</b></td>
            <td class="table_02_2" colspan="2"> <input name="p_phone" value="<%=v_phone%>" type="text" class="input" size="20" maxlength="20">
            </td>
          </tr>
          <tr> 
            <td class="table_title"><b>휴대폰번호</b></td>
            <td class="table_02_2" colspan="2"> <input name="p_handphone" value="<%=v_handphone%>" type="text" class="input" size="20" maxlength="20">
            </td>
          </tr>
          <tr> 
            <td class="table_title"><b>E-mail</b></td>
            <td class="table_02_2" colspan="2"> <input name="p_email" value="<%=v_email%>" type="text" class="input" size="50" maxlength="50"> 
            </td>
          </tr>
          <tr> 
            <td class="table_title"><b>소속</b></td>
            <td class="table_02_2" colspan="2"> <input name="p_comp" value="<%=v_comp%>" type="text" class="input" size="40"> 
            </td>
          </tr>
          <tr> 
            <td class="table_title"><b>부서</b></td>
            <td class="table_02_2" colspan="2"> <input name="p_dept" value="<%=v_dept%>" type="text" class="input" size="40"> 
            </td>
          </tr>
          <tr> 
            <td class="table_title"><b>직위</b></td>
            <td class="table_02_2" colspan="2"> <input name="p_jik" value="<%=v_jik%>" type="text" class="input" size="20"> 
            </td>
          </tr>
          <tr> 
            <td class="table_title"><b>강의과정</b></td>
            <td class="table_02_2" colspan="2"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td> <select name="p_subj" size=4  multiple>
                      <%                                                                                                                                                     
                        for(int i = 0; i < list.size(); i++) {
                            TutorData data2  = (TutorData)list.get(i);    
                            v_subj           = data2.getSubj();  		              
                            v_subjnm         = data2.getSubjnm();  		                             
                       %>
                      <option value="<%=v_subj%>"><%=v_subjnm%></option>
                      <% } %>
                    </select> <a href="javascript:searchSubj()"><img src="/images/admin/button/btn_csearch.gif" border="0"></a> 
                    <a href="javascript:delSubj()"><img src="/images/admin/button/btn_cdelete.gif" border="0"></a> 
                  </td>
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td class="table_title"><b>강사소개말</b></td>
            <td class="table_02_2" colspan="2"> <textarea name="p_intro" cols="110" style="height:80"></textarea> 
            </td>
          </tr>
          <tr> 
            <td class="table_title"><b>학력사항</b></td>
            <td class="table_02_2" colspan="2"> <textarea name="p_academic" cols="110" style="height:80"></textarea> 
            </td>
          </tr>
          <tr> 
            <td class="table_title"><b>경력사항</b></td>
            <td class="table_02_2" colspan="2"> <textarea name="p_career" cols="110" style="height:80"></textarea> 
            </td>
          </tr>
          <tr> 
            <td class="table_title"><b>저서</b></td>
            <td class="table_02_2" colspan="2"> <textarea name="p_book" cols="110" style="height:80"></textarea> 
            </td>
          </tr>
          <tr> 
            <td class="table_title"><b>강사료</b></td>
            <td class="table_02_2" colspan="2"> <input type="text" class="input" size="10" name="p_charge" value="<%=v_charge%>">
              원</td>
          </tr>
          <tr> 
            <td class="table_title"><b>강사소개여부</b></td>
            <td class="table_02_2" height="16" colspan="2"> <input type="radio" name="p_isinfo" value="Y">
              Yes 
              <input type="radio" name="p_isinfo" value="N" checked>
              No (체크시 홈페이지 강사소개에 나타납니다.)</td>
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


          <tr> 
            <td class="table_title"><b>사진파일첨부</b></td>
            <td class="table_02_2" colspan="2"> 

              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="100%"> 
                    <input type="file" class="input" size="18" name="p_file1">
                  </td>                 
                </tr>
              </table>

            </td>
          </tr>
-->
        </table>
        <!----------------- 강사등록 끝 ----------------->
        <br>
        <!----------------- 등록, 취소 버튼 시작----------------->
        <table border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td><a href="javascript:u_update()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
			<td width=8></td>
            <td><a href="javascript:cancel()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
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
