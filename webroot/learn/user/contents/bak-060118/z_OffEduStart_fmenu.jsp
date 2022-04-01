<%@ page contentType = "text/html;charset=MS949" %>
<%@ page import = "com.credu.library.*" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  p_process   = box.getString("p_process");
    String  p_subj      = box.getString("p_subj");
    String  p_year      = box.getString("p_year");
    String  p_subjseq   = box.getString("p_subjseq");
%>
<html>
<head><title></title>
<meta http-equiv="Content-Type" content="text/html; arset=euc-kr">
<script language="JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


//-->
</script>
</head>
<body topmargin="0" leftmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('img/m1_1.gif','img/m2_1.gif','img/m3_1.gif')">

<table cellpadding="0" cellspacing="0" border="0" width="800" height="68">
     <tr>
        <td width="390" height="68"><a href="/servlet/controller.study.SubjGongStudyServlet?p_process=Offselect&p_subj=<%=p_subj%>&p_year=<%=p_year%>&p_subjseq=<%=p_subjseq%>" target='ebody'><img src="/images/user/contents/a.gif" border="0"  ></td>
        <td width="94"><a href="/servlet/controller.research.SulmunServlet?p_process=UserResultListPage&p_f_gubun=C" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image6','','/images/user/contents/b_1.gif',1)" target='ebody'><img name="Image6" border="0" src="/images/user/contents/b.gif"></a></td>
        <td width="88"><a href="/servlet/controller.study.ProjectServlet?p_process=choicePage" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image7','','/images/user/contents/c_1.gif',1)" target='ebody'><img name="Image7" border="0" src="/images/user/contents/c.gif"></a></td>
        <td width="95"><a href="/servlet/controller.study.StudyDataServlet" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','/images/user/contents/d_1.gif',1)" target='ebody'><img name="Image8" border="0" src="/images/user/contents/d.gif"></a></td>
        <td width="133"><img src="/images/user/contents/e.gif"></td>
    </tr>   
</table>
</form>

</body>
</html>

