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
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.common.*" %>

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }

	String	s_gadmin	= box.getSession("gadmin");
    
	MasterFormData	da	=(MasterFormData)request.getAttribute("MasterFormData");
	String	v_imgURL	= "/images/user/contents/";	//image Base URL

%>

<html><head><title></title>
	<!-- CopyRight. Credu.Corp.  written by LeeSuMin -->
	<STYLE type="text/css">
	<!--
	.input {border:solid 0;border-color:0; background:url(http://<%=GetCodenm.get_config("eduDomain")%><%=v_imgURL%>object/otree/bg.gif);}
	-->
	</STYLE>

<%	if (s_gadmin.equals("A1")){ %>
		<script language="javascript" src="/Contents/Object/common/script/control.js"></script>
<%	} 	%>	

	</head>
	<body bgcolor="<%=da.getOtbgcolor()%>"  marginwidth="0" marginheight="0" topmargin="0">
	<script language="javascript">
		var f_job = false;

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
//alert("^^"+f_job);
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
			alert("v_tmp="+v_tmp);
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

				v_curr = v_curr + 1;
				v_preStr = v_preStr+"/"+v_curr + ".html";
				v_mypn++;
				if (v_mypn > v_topn){
					//alert("GO NEXT()");
					top.etree_fset.etree.goNext();
					return;

				}
				top.etree_fset.ebott.fobj.p_mypage.value=v_mypn;
				top.etree_fset.ebody.location=v_preStr;
				//if(v_mypn==v_topn){
				//	alert("eduChk() from fbott");
				//	top.etree_fset.etree.eduChk();
				//}
			}

			document.fobj.p_curpage.value=v_preStr;

		}
	</script>
	
	<table border=0 align=right valign=top height=42 cellpadding=0 cellspacing=0>
		<form name="fobj">
		<tr><td align=center valign=middle>
				<input type=hidden name="p_oid"  	value="TEST1_2">
				<input type=hidden name="p_dates" 	value="01">
				<input type=hidden name="p_oidb" 	value="TEST1">
				<input type=hidden name="p_curpage" value="">
				<a href="javascript:gWhere(-1)"><img src="<%=v_imgURL%>back.gif" border=0 align="absmiddle"></a>
				<input type=text name="p_mypage" value="0" size=3 maxlength=0 readOnly  style='text-align:center' class="input">
				/<input type=text name="p_pages" value="0" size=3 maxlength=0 readOnly  style='text-align:center' class="input">
				<a href="javascript:gWhere(1)"><img src="<%=v_imgURL%>next.gif" border=0 align="absmiddle"></a>
			</td>
		</tr>
		</form>
	</table>

</body></html>
