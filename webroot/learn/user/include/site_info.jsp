<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "com.credu.templet.*" %>
<%
    String onLoad = box.getString("onLoad");  // onLoad 함수 지정할때

    String tem_grcode        = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
    String tem_type          = "";
    String tem_imgpath       = "";
    String tem_mainflash     = "";
    String tem_mainbg        = "";
    String tem_subbg         = "";
    String tem_toplogo       = "";
    String tem_topbg         = "";
    String tem_footbg        = "";
    String tem_footcopyright = "";
    String tem_footleftimg   = "";
    String tem_subtopbg      = "";
    String tem_subtopimg     = "";
    String tem_subimgpath    = "";
    

    DataBox tem_box = TempletBean.getTemplet(tem_grcode);
    if (tem_box != null) {
//        tem_grcode        = tem_box.getString("d_grcode");
        tem_type          = tem_box.getString("d_type");
        tem_imgpath       = tem_box.getString("d_imgpath");
        tem_mainflash     = tem_box.getString("d_mainflash");
        tem_mainbg        = tem_box.getString("d_mainbg");
        tem_subbg         = tem_box.getString("d_subbg");
        tem_toplogo       = tem_box.getString("d_toplogo");
        tem_topbg         = tem_box.getString("d_topbg");
        tem_footbg        = tem_box.getString("d_footbg");
        tem_footcopyright = tem_box.getString("d_footcopyright");
        tem_footleftimg   = tem_box.getString("d_footleftimg");
        tem_subtopbg      = tem_box.getString("d_subtopbg");
        tem_subtopimg     = tem_box.getString("d_subtopimg");

        if (tem_type.equals("A")) {
        	tem_subimgpath = "type1";
        }else if (tem_type.equals("B")) {
        	response.sendRedirect("/");
        	tem_subimgpath = "type2";
        }else if (tem_type.equals("C")) {
        	tem_subimgpath = "type3";
        }

        box.setSession("tem_imgpath", tem_imgpath);
    } else {
        // 템플릿 정보가 없읍니다. 홈으로 이동)
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
    alert("비정상적인 접근입니다.")
    document.location = "/";
//-->
</SCRIPT>
<%
    }

%>

