<%
//**********************************************************
//  1. 제      목: Sitemap
//  2. 프로그램명 : za_Sitemap_L.jsp
//  3. 개      요: 사이트맵
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 20
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_gubun     = "main";

    ArrayList list = (ArrayList)request.getAttribute("selectList");

    int     i=0;
    int     v_levels = 0;
    String  v_menunm = "";
    String  v_pgm    = "";
    String  v_menu_view = "";
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">



<form name="form1" method="post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">


  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/system/unite_title13.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
     <!----------------- title 끝 ----------------->
        <br>

        <!----------------- 사이트맵 시작 ----------------->

<%

int v_topmenucnt = 0;
int v_submenucnt = 0;
int j =0;


    for (i=0;i<list.size(); i++){
        MenuData data  = (MenuData)list.get(i);
        v_levels = data.getLevels();
        v_menunm = data.getMenunm();
        v_pgm    = data.getPgm();
        
      if (v_levels==1){ 
            v_menu_view = v_menunm;

            if (v_topmenucnt != 0) {

                for (j=v_submenucnt;j<12;j++) {
%>
                      <tr> 
                        <td align="center"> </td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td height="4"></td>
                      </tr>
<%
                }

%>
                    </table>
                  </td>
                  <td align="center" background="/images/user/homepage/sitemap_bg_dot2.gif"></td>
                </tr>
              </table>

<%
            }

            if ((v_topmenucnt%4)==0 && v_topmenucnt!=0) {
%>
            </td>
          </tr>
        </table>

        <br>

        <table cellpadding="0" cellspacing="0" width="90%">
          <tr> 
            <td height="5"></td>
          </tr>
          <tr> 
            <td align="right" background="/images/admin/system/system_dot1.gif" height="3"></td>
          </tr>
          <tr> 
            <td height="5"></td>
          </tr>
        </table>

        <br>

<%
            } else if ( v_topmenucnt!=0) {
%>

            </td>
            <td width="20"></td>
            <td align="center" valign="top" width="250"> 

<%
            }
            if ((v_topmenucnt%4)==0) {
%>
        <table width="880" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td valign="top" align="center" width="250"> 

<%
            }
%>

              <table cellspacing="1" cellpadding="1" class="s_table">
                <tr> 
                  <td width="12"></td>
                  <td height="32" class="sitemap_title_bg"><%=v_menu_view%></td>
                  <td width="12"></td>
                </tr>
<%
            v_topmenucnt++;
            v_submenucnt = 0;
        } else {
			if (v_menunm.equals("separator")) {
				v_menu_view = "<td width='16' align='center'> </td><td><a href='#'>++++++++++++++++++<br></td>";
			} else {
				v_menu_view = "<td width='16' align='center'><img src='/images/admin/system/sitemap_icon1.gif' border='0'></td><td><a href='"+v_pgm+"'>"+v_menunm+"</a><br></td>";
			}

            if (v_submenucnt==0) {
%>
                <tr> 
                  <td background="/images/user/homepage/sitemap_bg_dot1.gif"></td>
                  <td class="sitemap_box_bg1"> 

                    <table cellspacing="0" cellpadding="0" class="sitemap_box_bg2">
                      <tr> 
                        <td height="6"></td>
                      </tr>
<%
            }
%>
                      <tr>
                      	<%=v_menu_view%>
                      </tr>
                      <tr> 
                        <td height="4"></td>
                      </tr>

<%

            v_submenucnt++;
        }

    }

                for (j=v_submenucnt;j<12;j++) {
%>
                      <tr> 
                        <td align="center"> </td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td height="4"></td>
                      </tr>
<%
                }
%>
                  </table>
                  </td>
                  <td align="center" background="/images/user/homepage/sitemap_bg_dot2.gif"></td>
                </tr>
              </table>




<% 
    for (j=(v_topmenucnt%4);j<4;j++) {
        if ((v_topmenucnt%4)!=0) {
%>
            </td>
            <td width="20"></td>
            <td align="center" valign="top" width="250">&nbsp;
<%
        }
%>


<%
    }

%>

            </td>
          </tr>
        </table>
        <!----------------- 사이트맵 끝 ----------------->


        <br>
        <br>

      </td>
  </tr>
</table>


<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
