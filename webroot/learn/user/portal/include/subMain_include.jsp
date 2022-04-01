<SCRIPT LANGUAGE="JavaScript">
    // 공지사항 더보기
    function moreNotice() {
        document.form1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.form1.p_process.value = "List";
        document.form1.menuid.value = "07";
        document.form1.gubun.value = "4";
        document.form1.target = "_self";
        document.form1.submit();
    }

    // 공지사항 상세보기
    function viewNotice(seq) {
       document.form1.action = "/servlet/controller.homepage.HomeNoticeServlet";
       document.form1.p_process.value = "selectView";
       document.form1.menuid.value = "07";
       document.form1.gubun.value = "4";
       document.form1.p_seq.value= seq;
       document.form1.target = "_self";
       document.form1.submit();
    }

  //이벤트 더보기
    function moreEvent() {
        document.form1.menuid.value= "05";
        document.form1.gubun.value = "5";
        document.form1.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.form1.p_process.value = "selectList";
        document.form1.target="_self";
        document.form1.submit();
    }

    //이벤트 상세보기
    function viewEvent(seq) {
        document.form1.menuid.value= "05";
        document.form1.gubun.value = "5";
        document.form1.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.form1.p_process.value = "selectView";
        document.form1.p_seq.value= seq;
        document.form1.target="_self";
        document.form1.submit();
    }

    // 뉴스레터 더보기
    function moreLetter() {
        document.form1.action = "/servlet/controller.homepage.HomeLetterServlet";
        document.form1.p_process.value = "List";
        document.form1.menuid.value = "08";
        document.form1.gubun.value = "4";
        document.form1.target = "_self";
        document.form1.submit();
    }

    // 뉴스레터 상세보기
    function viewLetter(seq) {
        document.form1.action = "/servlet/controller.homepage.HomeLetterServlet";
        document.form1.p_process.value = "selectView";
        document.form1.menuid.value = "08";
        document.form1.gubun.value = "4";
        document.form1.p_seq.value= seq;
        document.form1.target = "_self";
        document.form1.submit();
    }

  //워크샵 더보기
    function moreSeminar() {
        document.form1.menuid.value= "04";
        document.form1.gubun.value = "5";
        document.form1.action = "/servlet/controller.homepage.SeminarHomePageServlet";
        document.form1.p_process.value = "selectList";
        document.form1.target="_self";
        document.form1.submit();
    }

    //워크샵 상세보기
    function viewSeminar(seq) {
        document.form1.menuid.value= "04";
        document.form1.gubun.value = "5";
        document.form1.action = "/servlet/controller.homepage.SeminarHomePageServlet";
        document.form1.p_process.value = "selectView";
        document.form1.p_seq.value= seq;
        document.form1.submit();
    }

    function changeTab(idx) {
        var tabMenu = document.getElementsByName("tabMenu");
        var moreTab = document.getElementById("moreTab");
        var tabImg  = document.getElementsByName("tabImg");

        var len = tabMenu.length;

        for(var i = 0 ; i < len ; i++) {
            if( i == parseInt(idx) ) {
                tabMenu[i].style.display  = "block";
                imgover(tabImg[i]);
                tabImg[i].onmouseover = function(){return;}
                tabImg[i].onmouseout  = function(){return;}
            } else {
                tabMenu[i].style.display  = "none";
                imgout(tabImg[i]);
                tabImg[i].onmouseover = function(){imgover(this);}
                tabImg[i].onmouseout  = function(){imgout(this);}
            }
        }

        switch(idx){
            case "0" :
                moreTab.href = "javascript:moreNotice();";    
                break;
            case "1" :  
                moreTab.href = "javascript:moreEvent();";
                break;
            case "2" :  
                moreTab.href = "javascript:moreSeminar();";
                break;
            case "3" :  
                moreTab.href = "javascript:moreLetter();";
                break;
        } 
    }

</script>

                <div class="offline_box wd_600">
                    <ul class="tab">
                        <li><a href="javascript:changeTab('0');"><img id="tabImg" name="tabImg" src="/images/portal/offline/main_tab1_on.gif" alt="공지사항" /></a></li>
                        <li><a href="javascript:changeTab('1');"><img id="tabImg" name="tabImg" src="/images/portal/offline/main_tab2_off.gif" alt="이벤트" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a></li>
                        <li><a href="javascript:changeTab('2');"><img id="tabImg" name="tabImg" src="/images/portal/offline/main_tab3_off.gif" alt="워크샵" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a></li>
                        <li><a href="javascript:changeTab('3');"><img id="tabImg" name="tabImg" src="/images/portal/offline/main_tab4_off.gif" alt="뉴스레터" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a></li>
                        <li class="more"><a id="moreTab" href="javascript:moreNotice();"><img src="/images/portal/btn/btn_txt_more.gif" alt="더보기" /></a></li>
                    </ul>

                    <ul id="tabMenu" name="tabMenu" class="list">
                    <%  if(noticeList != null && noticeList.size() > 0) {
                            for(int i = 0 ; i < noticeList.size(); i ++) {
                                DataBox dbox = (DataBox)noticeList.get(i); %>
                        <li>
                            <a href="javascript:viewNotice(<%=dbox.getString("d_seq") %>);"><%=StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_adtitle")), 30) %></a> 
                            <span class="data"><%=FormatDate.getFormatDate(dbox.getString("d_addate"), "yyyy.MM.dd")%></span>
                        </li>
                    <%      }  
                    }%>
                    </ul>
                    
                    <ul id="tabMenu" name="tabMenu" class="list" style="display:none;">
                    <%  if(eventList != null && eventList.size() > 0) {
                            for(int i = 0 ; i < eventList.size(); i ++) {
                            DataBox dbox = eventList.get(i); %>
                        <li>
                            <a href="javascript:viewEvent(<%=dbox.getString("d_seq") %>);"><%=StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_title")), 25) %></a> 
                            <span class="data"><%=FormatDate.getFormatDate(dbox.getString("d_indate"), "yyyy.MM.dd")%></span>
                        </li>                            
                    <%      }                
                        }
                    %>
                    </ul>
                    
                    <ul id="tabMenu" name="tabMenu" class="list" style="display:none;">
                    <%
                        if(seminarList != null && seminarList.size() > 0) {
                            for(int i = 0 ; i < seminarList.size(); i ++) {
                                DataBox dbox =  seminarList.get(i); %>         
                        <li>
                            <a href="javascript:viewSeminar(<%=dbox.getString("d_seq") %>);"><%=StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_seminarnm")), 25) %></a> 
                            <span class="data"><%=FormatDate.getFormatDate(dbox.getString("d_indate"), "yyyy.MM.dd")%></span>
                        </li>                    
                    <%     }                
                        }%>
                    </ul>
                    
                    <ul id="tabMenu" name="tabMenu" class="list" style="display:none;">
                    <%  if(letterList != null && letterList.size() > 0) {
                            for(int i = 0 ; i < letterList.size(); i ++) {
                                DataBox dbox = (DataBox)letterList.get(i); %>      
                        <li>
                            <a href="javascript:viewLetter(<%=dbox.getString("d_seq") %>);"><%=StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_adtitle")), 25) %></a> 
                            <span class="data"><%=FormatDate.getFormatDate(dbox.getString("d_addate"), "yyyy.MM.dd")%></span>
                        </li>                       
                  <%        }                
                        } %>
                    </ul>
                </div>