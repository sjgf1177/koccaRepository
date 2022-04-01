<%
/**
 * file name : ku_InterestList_L.jsp
 * date      : 2005/12/16
 * programmer:	lyh
 * function  : 관심과정 list 출력
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);      
    box.put("leftmenu","08"); 

	String v_userid = box.getSession("userid");
	
    ArrayList list = (ArrayList)request.getAttribute("InterestList");

    
%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topMyclass.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->
<SCRIPT LANGUAGE="JavaScript">
<!--

	function deleteGo( subj, year, subjseq ){

		var ok= confirm("관심과정에서 삭제하시겠습니까?");

		if(ok){

		document.frm3.p_subj.value=subj;
		document.frm3.action ="/servlet/controller.study.KInterestServlet?p_process=InterestDelete";
		document.frm3.submit();

		}else{
			return;	
		}

	}

	function submitGo( subj, year, subjseq ){

		//document.frm3.subj.value=subj;
		//document.frm3.year.value=year;
		//document.frm3.subjseq.value=subjseq;
		//document.frm3.action ="/servlet/controller.study.KInterestServlet?p_process=InterestDelete";
		//document.frm3.submit();
		return;



	}


    // 과정 내용보기
    function whenSubjInfo(subj,sphere){
        document.frm3.p_subj.value        = subj;
        document.frm3.p_sphere.value = sphere;
        document.frm3.p_process.value  = 'SubjectPreviewPage';
        document.frm3.p_rprocess.value = 'SubjectListAlway';
        document.frm3.action='/servlet/controller.course.KCourseIntroServlet';
        document.frm3.target = "_self";
        document.frm3.submit();
    }
	
//-->
</SCRIPT>

	  <table width="680" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
		<tr> 
		  <td width="20">&nbsp;</td>
		  <td valign="top"><table width="680" border="0" cellspacing="0" cellpadding="0">
			  <tr> 
				<td  class="location" > HOME > 마이클래스&gt; 관심과정</td>
		 </tr>
		</table>
		<table width="680" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td><img src="/images/user/kocca/myclass/<%=tem_subimgpath%>/tit_interest.gif" width="680" height="30"></td>
		  </tr>
		  <tr> 
			<td height="20"></td>
		  </tr>
		</table>
		<table width="680" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td><img src="/images/user/kocca/myclass/text_interest.gif" width="680" height="33"></td>
		  </tr>
		  <tr>
			<td height="15"></td>
		  </tr>
		</table>

		<table width="680" border="2" cellspacing="0" 
		 cellpadding="3"   style="border-collapse:collapse;" bordercolor="#DFDFE0"  frame="hsides">
			<form name="frm3" method="post" >
				<INPUT TYPE="hidden" NAME="p_year">
				<INPUT TYPE="hidden" NAME="p_subjseq">
				<input type='hidden' name='p_sphere' >
				<input type='hidden' name='p_subj'    value="">
			    <input type='hidden' name='p_rprocess' value="">
			    <input type='hidden' name='p_process' value="">


		  <tr> 
			<td  height="3" colspan="9" class="linecolor_my"></td>
		  </tr>
		  <tr> 
			<td class="tbl_ptit2" rowspan="2">강좌유형</td>
			<td class="tbl_ptit" colspan="3">강좌명</td>
			<td class="tbl_ptit2" >담당교수</td>
			<td class="tbl_ptit" colspan="2"></td>
			<td class="tbl_ptit2">삭제</td>
		  </tr>
		  <tr> 
			<td width="10%" class="tbl_ptit">차수</td>
			<td width="30%" class="tbl_ptit2">신청기간</td>
			<td width="7%" class="tbl_ptit2">기간</td>
			<td width="13%" class="tbl_ptit">개강일</td>
			<td width="10%"class="tbl_ptit2" >샘플강좌</td>
			<td width="10%" class="tbl_ptit2">수강료</td>
			<td width="10%" class="tbl_ptit2">수강신청</td>
		  </tr>

  <%

	NumberFormat s_fmt = NumberFormat.getInstance();				//통화단위 ,표시
	String v_biyong = "";
	String v_today = "";
	int totalCnt = list.size();
	DataBox dbox = null;

	for(int i=0; i < list.size() ; i++){

	ArrayList subList = (ArrayList)list.get(i);
	
	if( subList.size() != 0){  
		for(int j=0; j < subList.size() ; j++){
			dbox =  (DataBox)subList.get(j);
			v_biyong = s_fmt.format(dbox.getInt("d_biyong"));
			v_today			= FormatDate.getDate("yyyyMMddhh");
			System.out.println("j:"+j);

	// 출력할 row가 과정타이틀인지 차수정보인지 구분하여 찍는다.
	if(dbox.getString("d_isgubun").equals("Y")){		// 과정출력줄이면

%>
	
	  <tr> 
		<td class="tbl_grc"><%=dbox.getString("d_classname")%></td>
		<td class="tbl_gleft" colspan="3"><a href="javascript:whenSubjInfo('<%=dbox.getString("d_subj")%>','<%=dbox.getString("d_sphere")%>')"><%=dbox.getString("d_subjnm")%></a><br>
		</td>
		<td class="tbl_grc">
		<% if(!dbox.getString("d_musernm").equals("")){ %>
			<%=dbox.getString("d_musernm")%> 교수
		<% }%>
		</td>
		<td class="tbl_grc" colspan="2"></td>
		<td class="tbl_grc"><A HREF="javascript:deleteGo('<%=dbox.getString("d_subj")%>','<%=dbox.getString("d_year")%>','<%=dbox.getString("d_subjseq")%>')"><img src="/images/user/kocca/button/b_del.gif"></A></td>
	</tr>

<%
	}else{			//차수정보 출력줄이라면  

			String v_subj = dbox.getString("d_subj");
			String v_subjnm = dbox.getString("d_subjnm");
            String v_statusString = "";
            String v_subjseqgr = "";
            int    v_iedustart = 0;
		    int     v_studentlimit =0;
			String v_subjseq            =  "";
			String v_year               =  "";
			String v_propstart          =  "";
			String v_propend            =  "";
			String v_edustart           =  "";
			String v_eduend             =  "";           
			String v_ispropose          =  ""; 
			int v_propcnt            =  0;
			int k = 0;
			String v_preurl				= "";
			String v_issampleString  = "";
				v_studentlimit       = dbox.getInt("d_studentlimit");
                v_subjseq            = dbox.getString("d_subjseq");
                v_year               = dbox.getString("d_year");
                v_subjseqgr          = dbox.getString("d_subjseqgr");
                v_propstart          = dbox.getString("d_propstart");
                v_propend            = dbox.getString("d_propend");
                v_edustart           = dbox.getString("d_edustart");
                v_eduend             = dbox.getString("d_eduend");
                v_ispropose          = dbox.getString("d_ispropose");
                v_propcnt            = dbox.getInt("d_propcnt");
                v_today = FormatDate.getDate("yyyyMMddhh");
				v_preurl        = dbox.getString("d_preurl");

				if(!v_preurl.equals(""))
				{ 
					v_issampleString = "<a href=\"javascript:whenPreShow('" + v_preurl + "','" + v_subj +"')\" focus=\"this.blur()\"><img src=\"/images/user/game/button/b_samplecourse.gif\" border=\"0\"></a>";
				}else {
					v_issampleString = "-";
				}




                if(v_edustart.equals("")){v_iedustart = 0;}
                else{v_iedustart = Integer.parseInt(v_edustart);}

                if(v_iedustart >= Integer.parseInt(v_today)){
                    v_statusString = "<a href=\"javascript:whenSubjPropose('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"')\" onfocus=this.blur()><img src='/images/user/kocca/button/b_aplly.gif' border=0></a>";

                    //out.println("v_ispropose"+v_ispropose);
                    if(v_propcnt > 0){
                        v_statusString = "";
                    }
                } 

                //날짜 처리
                if(v_propstart.equals("")){v_propstart = "미정";}
                else{v_propstart     = FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd");}

                if(v_propend.equals("")){v_propend = "미정";}
                else{v_propend = FormatDate.getFormatDate(v_propend,"yyyy/MM/dd");}

                if(v_edustart.equals("")){v_edustart = "미정";}
                else{v_edustart     = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");}

                if(v_eduend.equals("")){v_eduend = "미정";}
                else{v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");}

%>

	 <tr>
		<td width="10%"></td>
		<td width="23" bgcolor="#ECF2E3" class="tbl_grc"><%=v_subjseq%></td>
		<td class="tbl_grc"><%=v_propstart%> ~ <%=v_propend%></td>
		<td class="tbl_grc"><%=FormatDate.datediff("date", dbox.getString("d_edustart"), dbox.getString("d_eduend"))/7%>주</td>
		<td class="tbl_grc"><%=v_edustart%></td>
		<td class="tbl_grc"><%=v_issampleString%></td>
		<td class="tbl_grc"><%=v_biyong%></td>
		<td class="tbl_grc"><%=v_statusString%></td>

	  </tr>



<%
	}
  }//for

 }	

}	//for
%>

	  </table>

	  </form>

	  </td>
	  </tr>
	  </table>



<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
