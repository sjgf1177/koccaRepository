//flash player start
function fl_view(flashurl, w, h) {
    document.write("<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://active.macromedia.com/flash9/cabs/swflash.cab#version=9,0,0,0' width='"+w+"' height='"+h+"'>");
    document.write("<param name='movie' value='"+flashurl+"'>");
    document.write("<param name='wmode' value='transparent'>");
    document.write("<param name='play' value='true'>");
    document.write("<param name='loop' value='true'>");
    document.write("<param name='quality' value='high'>");
    document.write("<embed src='"+flashurl+"' play='true' loop='true' quality='high' wmode='transparent' pluginspage='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' width='"+w+"' height='"+h+"'>");
    document.write("</embed>");
    document.write("</object>");
}
//flash player end


//popup set start
function pop_view(urls,w,h,sc){
    sw = (screen.width);
    sh = (screen.height);
    size = "width="+w+",height="+h;
    sb = "scrollbars="+sc;
    var pop_view = window.open(urls,'',size,sb);
    pop_view.focus();
}
//popup set end


function txtlink(se,obj) {
    if(event.type == "focus") {
        document.getElementById(se+"txtfocus"+obj).style.textDecoration = "underline";
        document.getElementById(se+"txtfocus"+obj).style.fontWeight = "900";
        document.getElementById(se+"txtfocus"+obj).style.letterSpacing = "-1px";
    } else if (event.type == "blur") {
        document.getElementById(se+"txtfocus"+obj).style.textDecoration = "none";
        document.getElementById(se+"txtfocus"+obj).style.fontWeight = "400";
        document.getElementById(se+"txtfocus"+obj).style.letterSpacing = "0px";
    }
}


function passwordchecktype(obj){
    for(i=1; i<=2; i++){
        if (i == obj) {
            document.getElementById("certtype"+obj).style.display = "block";
            document.getElementById("certmsg"+obj).style.display = "block";
            document.form1.gubun.value = obj;

        } else {
            document.getElementById("certtype"+i).style.display = "none";
            document.getElementById("certmsg"+i).style.display = "none";
            document.form1.gubun.value = obj;
        }
    }
}

function login_main() {

    if (input_chk()){
        document.login.p_process.value   = "login";
        document.login.action            = https_host() + "/servlet/controller.homepage.LoginServlet";
        document.login.submit();
    } else {
        return;
    }
}

function input_chk( formObj )  {
    if (typeof formObj == "undefined" ) {
        formObj = document.login;
    }

    if (formObj.p_userid.value == "" || formObj.p_userid.value == null ) {
        document.login.p_userid.value="";  //폼창에 NULL값 리턴
        alert( "아이디를 입력하십시요.");
        document.login.p_userid.focus();
        return(false);
    }

    if (formObj.p_pwd.value == "" || formObj.p_pwd.value == null ) {
        document.login.p_pwd.value="";  //폼창에 NULL값 리턴
        alert( "비밀번호를 입력하십시요.");
        document.login.p_pwd.focus();
        return(false);
    }
    return(true);
}

function logout() {
    document.form1.p_process.value = "gologout";
    document.form1.action = "/servlet/controller.homepage.LoginServlet";
    document.form1.submit();
}