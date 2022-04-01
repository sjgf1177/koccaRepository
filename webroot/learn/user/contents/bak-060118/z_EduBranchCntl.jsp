<%
/**
 * file name : z_EduBranchCntl.jsp
 * date      : 2003/08/27
 * programmer: LeeSuMin
 * function  : 마스터폼 -분기제어
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }

	String	p_subj, p_year, p_subjseq, p_userid;
	
	String	p_isFromLMS	= box.getString("p_isFromLMS");
	if (p_isFromLMS.equals("Y")){		
		p_subj		= box.getString("p_subj");
		p_year		= box.getString("p_year");
		p_subjseq	= box.getString("p_subjseq");
		p_userid	= box.getString("p_userid");
	}else{
		p_subj		= box.getSession("s_subj");
		p_year		= box.getSession("s_year");
		p_subjseq	= box.getSession("s_subjseq");
		p_userid	= box.getSession("userid");
	}
	
	String  p_gubun     = box.getString	("p_gubun");
	String  p_lesson	= box.getString	("p_lesson");		//p_lesson
	int  	p_branch	= box.getInt	("p_branch");		
	String  p_isfirst	= box.getString	("p_isfirst");		//p_f_first
				 	
	ArrayList  list = (ArrayList)request.getAttribute("BranchList");
    MfBranchData x = null;
    
%>  
<html><head></head>
<script language="javascript">
	function load(){
		var v_text,v_value;
		var arrStr,arrStr1;
		
		top.etop.changeBranchsFromSubwin('<%=EduEtc1Bean.get_mybranch(p_subj,p_year,p_subjseq,p_userid)%>','<%=p_branch%>');
		for(var i=0;i<top.etop.document.sel.p_lesson.length;i++){
			arrStr = top.etop.document.sel.p_lesson.options[i].value.split(",");

			if (arrStr[3]!="N"){					
				for(j=0;j<document.fh1.length;j++){
					if (arrStr[0]==document.fh1.elements[j].name){
						arrStr1 = document.fh1.elements[j].value.split("++++");
						break;
					}
				}

				v_text = arrStr1[0];
				v_value = arrStr[0]+","+arrStr1[1]+","+arrStr[2]+","+arrStr[3];
				top.etop.document.sel.p_lesson.options[i].text=v_text;
				top.etop.document.sel.p_lesson.options[i].value=v_value;
			}
		}
		//'||v_txts||'
		top.etop.jumpTo('<%=p_lesson%>');
		return;
		window.close();
	}
</script><body onload="load()">
<FORM name="fh1">

<%	for (int i=0;i<list.size();i++){	
		x = (MfBranchData)list.get(i);		%>
		<input type="hidden" name="<%=x.getLesson()%>" value="<%=x.getSdesc()%>++++<%=x.getStarting()%>">
<%	}	%>	
</FORM><center>Loading....</center>
<body>
</body>
</html>