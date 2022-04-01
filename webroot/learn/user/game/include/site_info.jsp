<%@ page import = "com.credu.templet.*" %>
<%
    String onLoad = box.getString("onLoad");  // onLoad ?? ????

    String tem_grcode        = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
    String tem_type          = "";
    String tem_imgpath       = "";
    String tem_mainflash     = "";
    String tem_menuflash     = "";
    String tem_mainbg        = "";
    String tem_toplogo       = "";
    String tem_topbg         = "";

    String tem_foottopimg    = "";
    String tem_footbg        = "";
    String tem_footbgcolor1  = "";
    String tem_footbgcolor2  = "";
    String tem_footcopyright = "";
    String tem_footleftimg   = "";

    String tem_subbg1        = "";
    String tem_subtopimg1    = "";
    String tem_subbg2        = "";
    String tem_subtopimg2    = "";
    String tem_subbg3        = "";
    String tem_subtopimg3    = "";
    String tem_subbg4        = "";
    String tem_subtopimg4    = "";
    String tem_subbg5        = "";
    String tem_subtopimg5    = "";
    String tem_subbg6        = "";
    String tem_subtopimg6    = "";
    String tem_subbg7        = "";
    String tem_subtopimg7    = "";
    String tem_subbg8        = "";
    String tem_subtopimg8    = "";

    String tem_subimgpath    = "";



    DataBox tem_box = TempletBean.getTemplet(tem_grcode);
    if (tem_box != null) {
//        tem_grcode        = tem_box.getString("d_grcode");
        tem_type          = tem_box.getString("d_type");
        tem_imgpath       = tem_box.getString("d_imgpath");
        tem_mainflash     = tem_box.getString("d_mainflash");
        tem_menuflash     = tem_box.getString("d_menuflash");
        tem_mainbg        = tem_box.getString("d_mainbg");
        tem_toplogo       = tem_box.getString("d_toplogo");
        tem_topbg         = tem_box.getString("d_topbg");

        tem_foottopimg    = tem_box.getString("d_foottopimg");
        tem_footbg        = tem_box.getString("d_footbg");
        tem_footbgcolor1  = tem_box.getString("d_footbgcolor1");
        tem_footbgcolor2  = tem_box.getString("d_footbgcolor2");
        tem_footcopyright = tem_box.getString("d_footcopyright");
        tem_footleftimg   = tem_box.getString("d_footleftimg");

        tem_subbg1        = tem_box.getString("d_subbg1");
        tem_subtopimg1    = tem_box.getString("d_subtopimg1");
        tem_subbg2        = tem_box.getString("d_subbg2");
        tem_subtopimg2    = tem_box.getString("d_subtopimg2");
        tem_subbg3        = tem_box.getString("d_subbg3");
        tem_subtopimg3    = tem_box.getString("d_subtopimg3");
        tem_subbg4        = tem_box.getString("d_subbg4");
        tem_subtopimg4    = tem_box.getString("d_subtopimg4");
        tem_subbg5        = tem_box.getString("d_subbg5");
        tem_subtopimg5    = tem_box.getString("d_subtopimg5");
        tem_subbg6        = tem_box.getString("d_subbg6");
        tem_subtopimg6    = tem_box.getString("d_subtopimg6");
        tem_subbg7        = tem_box.getString("d_subbg7");
        tem_subtopimg7    = tem_box.getString("d_subtopimg7");
        tem_subbg8        = tem_box.getString("d_subbg8");
        tem_subtopimg8    = tem_box.getString("d_subtopimg8");

        if (tem_type.equals("GA"))      tem_subimgpath = "type1";
        else if (tem_type.equals("GB")) tem_subimgpath = "type2";

        box.setSession("tem_imgpath", tem_imgpath);
    } else {
        // ??? ??? ????. ??? ??)
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
    alert("잘못된 접근입니다.")
    document.location = "/";
//-->
</SCRIPT>
<%
    }
%>

