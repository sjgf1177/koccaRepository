<%
//**********************************************************
//  1. 제      목: OBC MasterForm Bottom Frame
//  2. 프로그램명: z_EduStart_fbott_OBC.jsp
//  3. 개      요: OBC MasterForm Bottom Frame
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 18
//  7. 수      정:
//*********************************************************** 

%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/website/common/error.jsp" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }

	String	s_gadmin	= box.getSession("gadmin");
    
	MasterFormData	da	=(MasterFormData)request.getAttribute("MasterFormData");
	String	v_imgURL	= "/images/user/contents";	//image Base URL

%>

<html><head><title></title>
	<!-- CopyRight. Credu.Corp.  written by LeeSuMin -->
	<STYLE type="text/css">
	<!--
	.input {border:solid 0;border-color:0;}
	-->
	</STYLE>
	<script language="javascript" src="/website/user/common/control.js"></script>
	</head>
	<body marginwidth="0" marginheight="0" topmargin="0" bottommargin="5px">
	<script language="javascript">
		var f_job = false;
		var isCompleted = true;

		function setCompleted( bool ){
			isCompleted = bool;
		};
		
		function changeObj(obj,npage){
			f_job = false;
			
			v_otype = obj.substring(0,1);
			if (v_otype=="TM" || v_otype=="TT"){
				document.fobj.p_pages.value = 0;
			}else{
				document.fobj.p_pages.value = npage;
				document.fobj.p_mypage.value = 1;
				//if (npage==1)
				//	top.etree_fset.etree.eduChk();
			}
		}

		function beginCheckControl(){
			f_job = true;
		}

		function changeUrl(urls){
			document.fobj.p_curpage.value=urls;
		}
		
		function gWhere(dir){
			v_mypn = parseInt(document.fobj.p_mypage.value,10);
			v_topn = parseInt(document.fobj.p_pages.value,10);

			if (v_topn == 0){		// when exam goto next object.
				alert("본문내의 버튼을 사용하여 주십시오");
				return;
			}

			v_tmp  = document.fobj.p_curpage.value;

			v_arr1 = v_tmp.split("/");
			v_fname = v_arr1[v_arr1.length -1].split(".");
			v_curr = parseInt(v_fname[0],10);
			v_preStr = "";

			for(var i=0; i<v_arr1.length-1;i++){
				if (i > 0 )		v_preStr = v_preStr + "/"+v_arr1[i];
				else			v_preStr = v_arr1[i];
			}

			v_term = v_curr - 1000;
			if (v_term != v_mypn){
				return;
			}

			if (dir == -1){
				f_job = false;
				if(v_curr <= 1001 || v_mypn <= 1){
					alert("좌측의 메뉴에서 학습할 내용을 선택하여 주십시오.");
					return;
				}
				v_curr = v_curr - 1;
				v_preStr = v_preStr+"/"+v_curr + ".html";
				top.etree_fset.ebott.fobj.p_mypage.value=v_mypn - 1;
				top.etree_fset.ebody.location=v_preStr;
			}else{
				if(f_job){
					if (top.etree_fset.ebody.checkInContents()){
						f_job = false;
					}else{
						return;
					}
				}
				
				if(!isCompleted){
					alert("학습할 내용을 Loading중입니다. 자동으로 이동되오니 잠시만 기다려 주세요.");
					return;
				}

				v_curr = v_curr + 1;
				v_preStr = v_preStr+"/"+v_curr + ".html";
				v_mypn++;
				if (v_mypn > v_topn){
					setCompleted( false );
					top.etree_fset.etree.goNext();
					return;
				}
				document.fobj.p_mypage.value=v_mypn;
				top.etree_fset.ebody.location=v_preStr;

				if(v_mypn==v_topn){
					top.etree_fset.etree.eduChk();
				}
			}

			document.fobj.p_curpage.value=v_preStr;

		}
	</script>
	<form name="fobj">
	<table border="0" align="right">
		<tr>
		  <td align=center valign=middle>
			<input type=hidden name="p_oid"  	value="TEST1_2">
			<input type=hidden name="p_dates" 	value="01">
			<input type=hidden name="p_oidb" 	value="TEST1">
			<input type=hidden name="p_curpage" value="">
		    <table border="0" cellspacing=0 cellpadding=0 height="40">
		      <tr>
		        <td width="28">
		        <a onmouseover="document.prevButton.src='<%=v_imgURL%>/back.gif'" onmouseout="document.prevButton.src='<%=v_imgURL%>/back.gif'" href="#" onclick="javascript:gWhere(-1);">
				<img name="prevButton" src="<%=v_imgURL%>/back.gif" border="0" style="margin-bottom:0px"></a></td>
		        <td width="74" align="center">
		        <input type="text" name="p_mypage" value="0" size="3" readOnly  style="text-align:center" class="input">
				/<input type="text" name="p_pages" value="0" size="3" readOnly  style="text-align:center" class="input"></td>
		        <td width="28">
		        <a onmouseover="document.nextButton.src='<%=v_imgURL%>/next.gif'" onmouseout="document.nextButton.src='<%=v_imgURL%>/next.gif'" href="#" onclick="javascript:gWhere(1);">
				<img name="nextButton" src="<%=v_imgURL%>/next.gif" border="0" style="margin-bottom:0px"></a></td>
		        <td width="14"></td>
		      </tr>
		    </table>
			</td>
		</tr>
	</table>
	</form>
</body></html>
