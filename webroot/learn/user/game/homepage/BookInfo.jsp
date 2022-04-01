
<%
//**********************************************************
//  1. 제      목: 홈페이지 추천교재
//  2. 프로그램명 : BookInfo.jsp
//  3. 개      요: 홈페이지 추천교재
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.21
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.book.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);


    String v_bookcode      = "";          // 교재코드
    String v_bookname      = "";          // 교재명
    String v_intro         = "";          // 교재소개
    String v_writer        = "";          // 저자소개
    String v_content       = "";          // 목차
    String v_review        = "";          // 리뷰
    String v_realfilename1 = "";          // 큰이미지   실제파일명
    String v_newfilename1  = "";          // 큰이미지   저장파일명
    String v_realfilename2 = "";          // 작은이미지 실제파일명
    String v_newfilename2  = "";          // 작은이미지 저장파일명
    String v_image_view1   = "";
    String v_image_view2   = "";
    String upload_url = conf.getProperty("url.upload");

    BookBean bookbean = new BookBean();
    //DataBox dbox = bookbean.selectRecomBook(box);
    
    ArrayList list = bookbean.selectRecomBookList(box);


%>

<SCRIPT LANGUAGE="JavaScript">
<!--
//상세보기
function bookView(bookcode) {
    document.frmbook.p_bookcode.value = bookcode;
    document.frmbook.action = "/servlet/controller.book.BookServlet";
    document.frmbook.target = "_self";
    document.frmbook.p_process.value = "selectPage";
    document.frmbook.submit();
}

//-->
</SCRIPT>
<form name="frmbook" method="post">
    <input type = "hidden" name="p_process"    value = "">
    <input type = "hidden" name="p_bookcode"   value = "">
</form>


          <table cellpadding="0" cellspacing="0" width="190">
            <tr><td><img src="/images/user/game/homepage/type1/textbook_top.gif"></td></tr>
            <tr><td background="/images/user/game/homepage/type1/textbook_mid.gif" align="center"><a href="javascript:menuForward('6', '01', '1')"><img src="/images/user/game/homepage/type1/tit_textbook.gif" border="0" vspace="5"></a></td></tr>            
            <tr valign="top">
            <td align="center" background="/images/user/game/homepage/type1/textbook_mid.gif">
<%!
	public int getRANDOM(int v_from,int v_to) {
		//int num = (int)(Math.random() * 10);
		int num = (int)(Math.random() * v_to);
		
		loop: while(true) {
			if (num<v_from) num = (int)(Math.random() * v_to); else break loop;
		}
		
		loop: while(true) {
			if (num>v_to) num = (int)(Math.random() * v_to); else break loop;
		}
		
		return num;
	}
	


%>
<%
		//설정값 시작
	    int v_from = 0; //시작
	    int v_to = list.size(); //끝
	    int rndcnt = 2; //배열갯수
		int[] seq = new int[3]; //리스트 갯수   
		for (int xx=0;xx<3;xx++) {
			seq[xx]=999;
		} 
	    
	    //변수 시작
		int cnt = 0;	//채워진 갯수
		int errchkcnt = 0; //루한루프 방지
		
		//rndcnt까지 채워질때까지 반복
		while(cnt<=rndcnt) {
			//랜덤 값 가져오기
			int rnd = getRANDOM(v_from, v_to);
			
			//기존에 있는지 검사
			boolean v_exists = false;

			for(int rnd_i=0;rnd_i<=rndcnt;rnd_i++) {
				if (seq[rnd_i]==rnd) {
					v_exists = true;
				}
				errchkcnt++;
				
				if (errchkcnt>1000) break;
			}
			
			if (v_exists==false) {
				seq[cnt]=rnd;
				cnt++;
			}
			
			errchkcnt++;
			if (errchkcnt>1000) break;
			
		}
		
		
		//for (int xx=0;xx<3;xx++) {
		//	out.println("seq[] = " + seq[xx] + "<br>");
		//}

%>     
<% if (list.size()>0) {
    for(int i = 0; i < list.size() && i < 3; i++) {
    	//RANDOM
    	DataBox dbox = null;
    	if (seq[i] > list.size()) {
        	dbox  = (DataBox)list.get(i);
        } else {
        	dbox  = (DataBox)list.get(seq[i]);
        }
        
        v_bookcode      = dbox.getString("d_bookcode");
        v_bookname      = dbox.getString("d_bookname");
        v_intro         = dbox.getString("d_intro");
        v_writer        = dbox.getString("d_writer");
        v_content       = dbox.getString("d_content");
        v_review        = dbox.getString("d_review");
        v_realfilename1 = dbox.getString("d_realfilename1");
        v_newfilename1  = dbox.getString("d_newfilename1");
        v_realfilename2 = dbox.getString("d_realfilename2");
        v_newfilename2  = dbox.getString("d_newfilename2");

        if (v_newfilename1.equals("")) v_image_view1 = "/images/user/game/book/blank_book1.gif";
        else                           v_image_view1 = upload_url + "bulletin/" + v_newfilename1;
        if (v_newfilename2.equals("")) v_image_view2 = "/images/user/game/book/blank_book2.gif";
        else                           v_image_view2 = upload_url + "bulletin/" + v_newfilename2;
%>

            	<!-- 추천교재 시작 -->
                <table cellpadding="0" cellspacing="0" width="178">
                <tr>
                <td width="61" align="center">
                    <a href="javascript:bookView('<%=v_bookcode%>')"><img src="<%=v_image_view2%>" width="53" height="79" border="0"></a><br>
                </td>
                <td width="117">
                    <div style="width:117px;height:19px;background-color:#ededee;font-size:12px;">
                    <strong><a href="javascript:bookView('<%=v_bookcode%>')"><%=v_bookname%></a></strong>
                    </div>
                    <div style="width:117px;font-size:11px;padding:10px 0px 0px 5px;">
                    <a href="javascript:bookView('<%=v_bookcode%>')"><%=StringManager.formatTitle(v_intro, 30)%></a>
                    </div>
                </td>
                </tr>
                <tr>
                	<td height="7"></td>
                </tr>
                </table>
                
                <% if (i+1 < list.size()) { %>
                <table>
                  <tr>
                  	<td width=10>
                  	</td>
                  </tr>
                </table>
                <% } %>
<%		}
    } else {

%>
<table cellpadding="0" cellspacing="0" width="178">
                <tr>
                <td width="61" align="center"><img src="/images/user/game/book/blank_book2.gif" width="53" height="79" border="0"></a><br>
                </td>
                <td width="117">
				<!--
                    <div style="width:117px;height:19px;background-color:#ededee;font-size:12px;">
                    &nbsp;
                    </div>
				--->
                    <div style="width:117px;font-size:11px;padding:10px 0px 0px 5px;">
                    추천도서가 없습니다.<br><br>
                    </div>
                </td>
                </tr>
                </table>
<%
    }
%>
                
                <!-- 추천교재 끝 -->
            </td>
            </tr>
            <tr><td><img src="/images/user/game/homepage/type1/textbook_bot.gif"></td></tr>
            <tr height="21"><td></td></tr>
          </table>


