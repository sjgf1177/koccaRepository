<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    String tem_grcode        = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

		int size = 3;
		box.put("s_grcode",tem_grcode);
		box.put("s_isuse","Y");
		LinkSiteBean linkBean = new LinkSiteBean();
		ArrayList list3 = (ArrayList)linkBean.SelectList(box);
		if(list3.size() < 3) size = list3.size();
		
		int i = 0;
        String v_grcode     = "";
        String v_seq		= "";
        String v_sitenm     = "";
        String v_url		= "";
		String v_banner		= "";
        String v_isuse      = "";
        String v_islogin	= "";
		String v_dir		= conf.getProperty("dir.linksitepath");
%> 


				  <!-- 관련사이트찾기 -->
                  <table width="158" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="/images/user/homepage/site_tit.gif"></td>
                    </tr>
<form name="linkform" method="post">

<%
if(list3.size()>0){
	for(i = 0; i < size; i++) {
		DataBox dbox3 = (DataBox)list3.get(i);
		v_grcode		= dbox3.getString("d_grcode");
		v_seq			= dbox3.getString("d_seq");
		v_sitenm		= dbox3.getString("d_sitenm");
		v_url			= dbox3.getString("d_url");
		v_banner		= dbox3.getString("d_banner");
		v_islogin		= dbox3.getString("d_islogin");
		if(!v_banner.equals("")) v_banner = v_dir+v_banner;
%>
                    <tr> 
                      <td valign="bottom"><img src="<%=v_banner%>" width="158" height="29" border="0" alt="<%=v_sitenm%>" onClick="goSiteBanner('<%=v_grcode%>','<%=v_seq%>','<%=v_islogin%>')" style="cursor:hand"></td>
                    </tr>
<%	}%>							
										<tr><td height="5"></td></tr>
                    <tr> 
                      <td align="center"><select name="link" onChange="goSiteSelect()" style='border-style:solid;border-width: 1px 1px 1px 1px;border-color:cccccc;color:333333;font-size:9pt;background-color:none;width : 158px;height:19px;font-size:9pt;'>
                          <option selected>:: 관련사이트 :: </option>
<%
	if(size==3){
		for(i = 3; i < list3.size(); i++) {
			DataBox dbox3 = (DataBox)list3.get(i);
				v_grcode		= dbox3.getString("d_grcode");
				v_seq			= dbox3.getString("d_seq");
				v_islogin		= dbox3.getString("d_islogin");
			out.println("<option value='"+v_grcode+":"+v_seq+":"+v_islogin+"'>"+v_sitenm+"</option>");
		}
	}
%>
                        </select></td>
                    </tr>
<%	}else{%>
                    <tr> 
                      <td><img src="/images/user/homepage/site_blank.gif"></td>
                    </tr>
<%	}%>
                    <tr> 
                      <td height="5"></td>
                    </tr>
</form>
                  </table>
                  <!-- 관련사이트찾기 -->

