<%
    String notSelected = "";
    String selected = "";

    for(int top_i = 0 ; top_i <  mainMenuList.size() ; top_i++){
        DataBox mainBbox = (DataBox)mainMenuList.get(top_i);
        mMenuGubun = mainBbox.getString("d_gubun");

        notSelected = "\" onmouseover=\"imgover(this);\" onmouseout=\"imgout(this);";
        selected = "";

        if(box.getSession("s_gubun").equals(mMenuGubun)) {
            DataBox subMainBox = templetBeans.getSubMain(box);
            mMenuImgPath = StringUtil.replaceAll(subMainBox.getString("d_imgpath"), '\\', '/');
            mMenuName = mainBbox.getString("d_menuname");
            mMenuUrl = mainBbox.getString("d_menuurl");
            mMenuSubMenuHomeImg = mainBbox.getString("d_menuhomeimg");
            mSubImg = mainBbox.getString("d_subimg");
            mSubOverImg = mainBbox.getString("d_suboverimg");

            notSelected = mMenuImgPath + mSubImg + notSelected;
            selected = mMenuImgPath + mSubOverImg;

            break;
        }
    }
%>
        <div id="snb">
            <dl>
<%
    if( box.getSession("tem_grcode").equals("N000001")) {
%>
                <dt><a href="javascript:menuMainForward('<%=mMenuGubun %>', '<%=mMenuUrl %>');"><img src="<%=mMenuImgPath+"re_"+mMenuSubMenuHomeImg %>" alt="<%=mMenuName %>" /></a></dt>
<%
    } else {
%>
                <dt><a href="javascript:menuMainForward('<%=mMenuGubun %>', '<%=mMenuUrl %>');"><img src="<%=mMenuImgPath + mMenuSubMenuHomeImg %>" alt="<%=mMenuName %>" /></a></dt>
<%
    }

    for(int left_j = 0 ; left_j < subMenuList.size(); left_j++) {
        DataBox subBox = (DataBox)subMenuList.get(left_j);

        sMenuGubun = subBox.getString("d_gubun");
        sMenuId = subBox.getString("d_menuid");
        sMenuName = subBox.getString("d_menuname");
        sMenuUrl = subBox.getString("d_menuurl");

        if( box.getSession("tem_grcode").equals("N000001")) {
            sMenuImg = "re_"+subBox.getString("d_menuimg");
            sMenuOverImg = "re_"+subBox.getString("d_menuoverimg");
        }else{
            sMenuImg = subBox.getString("d_menuimg");
            sMenuOverImg = subBox.getString("d_menuoverimg");
        }

        sMenuImgPath = StringUtil.replaceAll(subBox.getString("d_imgpath"), '\\', '/');

        if(sMenuGubun.equals(box.getSession("s_gubun"))) {
           notSelected = "\" onmouseover=\"imgover(this);\" onmouseout=\"imgout(this);";
           selected = "";

           notSelected = sMenuImgPath + sMenuImg + notSelected;
           selected = sMenuImgPath + sMenuOverImg;
%>
                <!-- <dd><a href="javascript:menuForward('<%=sMenuGubun %>', '<%=sMenuId %>');"><img src="<%= (box.getSession("s_menuid")).equals(sMenuId) ? selected : notSelected %>" alt="<%=sMenuName %>" onblur="imgout(this);" /></a></dd> //-->
                <dd style="width:120px; line-height:12pt; padding-left:4px; font-weight:bold; font-size:10pt;"><a href="javascript:menuForward('<%=sMenuGubun %>', '<%=sMenuId %>');"><%=sMenuName %></a></dd>

<%
        }

    }
%>
            </dl>

			<!--서브하단배너부분-->
			<table width="200" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="23">&nbsp;</td>
                </tr>
                <tr>
                	<td>
                		<table width="200" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="2"><img src="/images/portal/homepage_renewal/common/txt_study_info.jpg" alt="학습지원안내" width="200" height="28" /></td>
							</tr>
							<tr>
								<td><a href="javascript:menuForward(4,'01');" ><img src="/images/portal/homepage_renewal/common/icon_01.jpg" alt="FAQ" /></a></td>
								<td><a href="javascript:menuForward(4,'02');" ><img src="/images/portal/homepage_renewal/common/icon_02.jpg" alt="Q&amp;A" /></a></td>
							</tr>
							<tr>
								<td><a href="javascript:menuForward(4,'03');" ><img src="/images/portal/homepage_renewal/common/icon_03.jpg" alt="S/W다운로드" /></a></td>
								<td><a href="javascript:menuForward(4,'05');" ><img src="/images/portal/homepage_renewal/common/icon_04.jpg" alt="학습환경도우미" /></a></td>
							</tr>
						</table>
                	</td>
                </tr>
                <tr>
                  <td><img src="/images/portal/homepage_renewal/common/bn_ask.jpg" /></td>
                </tr>
            </table>
			<!--서브하단배너부분//-->

        </div>
        <div id="snb2">
        </div>
