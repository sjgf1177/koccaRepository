<%
//**********************************************************
//  1. 제      목: 지식공유
//  2. 프로그램명 : gu_KnowCategory_L.jsp
//  3. 개      요: 지식공유 카테고리 선택
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 06.01.03
//  7. 수      정: 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_cmuno      = box.getString("p_cmuno");//마스터 관련페이지는 무조건 들어가야함.
    String  v_brd_fg      = box.getStringDefault("p_brd_fg","2");//자료실을 디폴트로한다.

    String   v_process   = box.getString("p_process");
    String   v_scriptAry = "";
    ArrayList list  = (ArrayList)request.getAttribute("CategoryList");

    for (int i=0;i<list.size();i++){
        DataBox dbox = (DataBox)list.get(i);
        String v_tmpdir = "false";
        int subcnt = dbox.getInt("d_subcnt");
        if (subcnt>0) v_tmpdir="true";
        v_scriptAry += "addMemory( "+v_tmpdir
                      +"          , '"+dbox.getString("d_subjclass")+"'"
                      +"          , 'MAIN'"            
                      +"          , '"+dbox.getString("d_classname")+"'"
                      +"          , '"+dbox.getString("d_root")+"'"
                      +"          , '"+dbox.getString("d_parent")+"'"
                      +"          , '"+dbox.getInt("d_levels")+"'"
                      +"          , '"+subcnt+"'"
                      +"          , '"+dbox.getString("d_viewname")+"'"                      
                      +" ); \n";
    }  

%>  
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../../css/user_style_community2.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
   var IMG_PATH = "/images/user/myclass/tree/";
   var EXT_PATH = "/images/community/tree/ext/";
   var IMG_WIDTH   = "21";
   var IMG_HEIGHT  = "19";

   var BLANK       = "BLANK.gif";
   var BLANK_LINE  = "BLANK_LINE.gif";
   var CLOSE       = "CLOSE.gif";
   var CLOSE_END   = "CLOSE_END.gif";
   var DIR         = "DIR.gif";
   var DIR_OPEN    = "DIR_OPEN.gif";
   var OPEN        = "OPEN.gif";
   var OPEN_END    = "OPEN_END.gif";
   //var NODE        = "PAPER.gif";
    var NODE        = "DIR.gif";
    
   var MEMORY     = new Array();
   var ITEM       = new Array();
   var LEVEL      = new Array();
   var IS_FIRST   = true;
   var PREV_LEVEL = 1;


   function initTree(){
     if ( IS_FIRST ){
        <%=v_scriptAry%>
        IS_FIRST = false;
     }
     PREV_LEVEL = 0;
     LEVEL    = new Array();
     ITEM     = new Array();
     ITEM     = extractItem();
     var text = makeTree();
     document.all.TREE.innerHTML = text;
   }

    //dir           :디렉토리여부[true/false] 
    //menuno        :코드 subjclass
    //target        :대상프레임
    //desc          :코드명 classname
    //root          :ROOT코드
    //parent        :PARENT코드
    //level         :트리위치(0부터)
    //subcnt        :자식갯수
    //viewname      :카테고리선택 뷰
    function addMemory( dir , menuno , target , desc , root, parent, level, subcnt, viewname )
    {
       var index = MEMORY.length;
       MEMORY[index] = new Object;
       MEMORY[index].dir            = dir;
       MEMORY[index].menuno         = menuno;
       MEMORY[index].target         = target;
       MEMORY[index].desc   = desc;       
       MEMORY[index].root           = root          ;
       MEMORY[index].parent         = parent        ;
       MEMORY[index].level          = level;
       MEMORY[index].subcnt         = subcnt;
       MEMORY[index].viewname       = viewname;       
       if( MEMORY[index].dir == false )
            MEMORY[index].open = true;
       else
            MEMORY[index].open = false;

   }
  // 보여줘야 하는 Item 추출
  function extractItem(){
    var buff            = new Array();
    var close_top_level = null;
    for (var i = 0 ; i < MEMORY.length ; i++ ){
       if (close_top_level == null ){
          if (MEMORY[i].open == false ){
             var cnt   = buff.length;
             buff[cnt] = MEMORY[i];
             buff[cnt].index = i;
             close_top_level = MEMORY[i].level;
          }  else {
             var cnt = buff.length;
             buff[cnt] = MEMORY[i];
             buff[cnt].index = i;
          }
       } else if (close_top_level < MEMORY[i].level ){
                 continue;
       } else if (close_top_level >= MEMORY[i].level ){
                 var cnt = buff.length;
                 buff[cnt] = MEMORY[i];
                 buff[cnt].index = i;
                 if (MEMORY[i].open == false ){
                     close_top_level = MEMORY[i].level;
                 } else {
                    close_top_level = null;
                    continue;
                 }
       }
    }
    return buff;
  }


    // HTML 생성
    function makeTree() {
      var text = "";
      //text +="   <A href='JavaScript:setInit();'><IMG border='0' src='/images/community/tree/ext/BackGroundScreen.gif' alt='초기화'></a>"
      text +="   "
      text += "<table border=0 cellspacing=0 cellpadding=0>";
      for (var i = 0 ; i < ITEM.length ; i++ ){
          if (true ){
              text += "<tr>"
                   + "<td>"
                   + "  <table border=0 cellspacing=0 cellpadding=0 style>"
                   + "   <tr>"
                   + "    <td>";
              text += getBlankImage(i);
              text += getLinkerImage(i);
              text += getDirImage(i);
              text += "</td>"
                   + "<td align=left valign=bottom>";
              //text += "<A href=\"javascript:uf_MoveCode('"+ITEM[i].menuno+"','"+ITEM[i].desc+"','"+ITEM[i].root+"','"+ITEM[i].parent+"','"+ITEM[i].level+"','"+ITEM[i].subcnt+");\">"+ITEM[i].desc+"</A>";
              text += "<A href=\"javascript:MoveCategory('"+ITEM[i].menuno+"','"+ITEM[i].viewname+"');\">"+ITEM[i].desc+"</A>";
              text += "</td>"
                   + "</tr>"
                   + "</table>"
                   + "</td>"
                   + "</tr>";
          }
      }
      text += "</table>";
      return text;
    }
function fst(){

  alert('dd');
}  
    // BLANK/LINE_BLANK 이미지 삽입
    function getBlankImage(index){
       var temp = "";
       var image = "";
       if( PREV_LEVEL > ITEM[index].level ) delLEVEL(ITEM[index].level);
       for (var i = 0 ; i < ITEM[index].level ; i++ ) {
           if (LEVEL[i] != null ) {
              if (LEVEL[i] == true ) {
                 image = BLANK_LINE;
              } else {
                 image = BLANK;
              }
          } else {
            if (isLine(index , i)  ){
                image = BLANK_LINE;
                LEVEL[i] = true;
            } else {
                image = BLANK;
                LEVEL[i] = false;
           }
         }
         temp += "<IMG border=0 src=" + IMG_PATH + image + " width=" + IMG_WIDTH + " height=" + IMG_HEIGHT + ">";
       }
       PREV_LEVEL = ITEM[index].level
       return temp;
    }

    // LEVEL 의 부정확한 값 삭제.
    function delLEVEL(present_level){
       if (LEVEL != null ){
          for (var i = 0 ; i < LEVEL.length ; i++ ){
               if( i < present_level ) continue;
               LEVEL[i] = null;
          }
       }
    }

    // OPEN/CLOSE 이미지 삽입.
    function getLinkerImage(index){
       var temp = "";
       var image = "";
       if (ITEM[index].open == false ){
          image = CLOSE;
       } else {
          image = OPEN;
       }

       if (ITEM[index].dir == true ){
          temp += "<A href='JavaScript:toggle(" + ITEM[index].index + ");'>"
       }
       if (isEndNode(index) ){
          if (ITEM[index].open == false ){
             temp += "<IMG border=0 src=" + IMG_PATH + CLOSE + " alt=열기 width=" + IMG_WIDTH + " height=" + IMG_HEIGHT + ">";
          } else {
             temp += "<IMG border=0 src=" + IMG_PATH + OPEN  + " alt=닫기 width=" + IMG_WIDTH + " height=" + IMG_HEIGHT + ">";
          }
       } else {
         if (ITEM[index].open == false ){
             temp += "<IMG border=0 src=" + IMG_PATH + CLOSE_END + " alt=열기 width=" + IMG_WIDTH + " height=" + IMG_HEIGHT + ">";
         } else {
             temp += "<IMG border=0 src=" + IMG_PATH + OPEN_END  + " alt=닫기 width=" + IMG_WIDTH + " height=" + IMG_HEIGHT + ">";
         }
       }
       if( ITEM[index].dir == true ) temp += "</A>"
       return temp;
    }


    // 디렉토리 이미지 삽입
    function getDirImage(index){
       var temp = "";
       if( ITEM[index].dir == true ){
          temp += "<A href='JavaScript:toggle(" + ITEM[index].index + ");'>"
       }

       if (ITEM[index].dir == true ){
          if (ITEM[index].open == true ){
              temp += "<IMG border=0 src=" + IMG_PATH + DIR_OPEN + " width=" + IMG_WIDTH + " height=" + IMG_HEIGHT + ">";
          } else {
              temp += "<IMG border=0 src=" + IMG_PATH + DIR + " width=" + IMG_WIDTH + " height=" + IMG_HEIGHT + ">";
          }
       } else {
          if (ITEM[index].ext_image == null ){
              temp += "<IMG border=0 src=" + IMG_PATH + NODE + " width=" + IMG_WIDTH + " height=" + IMG_HEIGHT + ">";
          } else {
              temp += "<IMG border=0 src=" + IMG_PATH + NODE + " width=" + IMG_WIDTH + " height=" + IMG_HEIGHT + ">";
          }
       }
       if( ITEM[index].dir == true ) temp += "</A>"
       return temp;
    }

    // 마지막 노드인테 체크.
    function isEndNode(index){
       var result = false;
       for (var i = (index + 1) ; i < ITEM.length ; i++ ){
           if (ITEM[i].level == ITEM[index].level ){
               result = true;
               break;
           } else if (ITEM[i].level < ITEM[index].level ) {
               result = false;
               break;
           }
       }
       return result;
    }

    // 점선 이미지 Drawing 여부 체크.
    function isLine(index, level){
       var result = false;

       for (var i = index ; i < ITEM.length ; i++ ){
           if (ITEM[i].level < level ){
               result = false;
               break;
           }else if( ITEM[i].level == level ) {
               result = true;
               break;
           }
       }
       return result;
    }

   // 클릭시
   //"+ITEM[i].menuno+"','"+ITEM[i].desc+"','"+ITEM[i].root+"','"+ITEM[i].parent+"','"+ITEM[i].level+"','"+ITEM[i].subcnt+"
   function uf_MoveCode(menuno, desc , root,parent,level,subcnt){
    /*document.form1.p_menuno.value=menuno;
    document.form1.p_title.value=desc;
    document.form1.p_read_cd.value=read_cd;
    document.form1.p_write_cd.value=write_cd;
    document.form1.p_arrange.value=arrange;
    document.form1.p_fileadd_fg.checked=true;
    document.form1.p_filecnt.value=filecnt;
    document.form1.p_directory_fg.value=directory_fg;
    document.form1.p_brd_fg.value=brd_fg;
    document.form1.p_root.value=root;
    document.form1.p_parent.value=parent;
    document.form1.p_lv.value=eval(level)+1;
    document.form1.p_position.value=position;
    document.form1.p_limit_list.value=limit_list;
    document.form1.p_search_fg.value="Y";
    document.form1.p_subcnt.value=subcnt;*/
    
    alert(menuno);

  }
  
  function MoveCategory(menuno,viewname){
      opener.form1.p_catecd.value = menuno;
      opener.form1.p_catenm.value = viewname;
      window.self.close();
  }


   function setInit(){
    document.form1.p_menuno.value='';
    document.form1.p_title.value='';

    document.form1.p_fileadd_fg.checked=true;
    document.form1.p_filecnt.value=0;
    document.form1.p_root.value='';
    document.form1.p_parent.value='';
    document.form1.p_lv.value=0;
    document.form1.p_position.value=0;
    document.form1.p_limit_list.value='';
    document.form1.p_search_fg.value="N";    
     document.form1.p_subcnt.value=0;    
     MEMORY = new Array();
     IS_FIRST = true;
     initTree();
   }

   function toggle( index ){
     if ( MEMORY[index].open == false ){
          MEMORY[index].open = true;
     } else{
          MEMORY[index].open = false;
     }
     initTree();
   }

function uf_updateOK() {
    if(document.form1.p_search_fg.value =='N'){
       alert('조회가 선행되어야합니다.');return;
    }

    document.form1.action          = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "updatemenuData";
    document.form1.submit();
}

function uf_deleteOK() {
    if(document.form1.p_search_fg.value =='N'){
       alert('조회가 선행되어야합니다.');return;
    }
    if(document.form1.p_subcnt.value >0){
       alert('하위 정보 삭제가 선행되어야합니다.');document.form1.p_ins_title.focus();return;  
    }
    document.form1.action          = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "deletemenuData";
    document.form1.submit();
}

function uf_addOK() {
    if(document.form1.p_directory_fg.value =='file'){
       alert('더이상 서브메뉴를 추가할수 없습니다.');return;
    }

    if(document.form1.p_ins_title.value ==''){
       alert('명칭을 입력하여야합니다.');document.form1.p_ins_title.focus();return;
    }
    if(document.form1.p_dupchk.value =='N'){
       alert('커뮤니티 중복체크를 하여야합니다.');document.form1.p_ins_title.focus();return;
    }
    if(document.form1.p_dupchk.value =='N'){
       alert('커뮤니티 중복체크를 하여야합니다.');document.form1.p_ins_title.focus();return;
    }

    document.form1.action          = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "insertmenuData";
    document.form1.submit();
}
function uf_tabOK(p_brd_fg) {
    document.form1.p_brd_fg.value=p_brd_fg;
    document.form1.action          = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "movemenuPage";
    document.form1.submit();
}


function uf_listOK() {
    document.form1.action          = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "selectmsmainPage";
    document.form1.submit();
}

function uf_dupcheck() {
    if(document.form1.p_ins_title.value ==''){
       alert('명칭을 입력하여야합니다.');document.form1.p_ins_title.focus();return;
    }
   open_window('cmuDupCheck', '/servlet/controller.community.CommunityPopUpServlet?p_process=boarddupchkPage&p_cmuno='+document.form1.p_cmuno.value+'&p_title='+document.form1.p_ins_title.value+'&p_brd_fg='+document.form1.p_brd_fg.value, 100, 100, 300, 220, 'no', 'no', 'no', 'no', 'no');
  
}
function uf_msPopMember() {
   open_window('msPopMember', '/servlet/controller.community.CommunityPopUpServlet?p_process=menumemPage&p_static_cmuno=<%=v_cmuno%>&p_cmuno=<%=v_cmuno%>&p_cbxflag=Y', 100, 100, 610, 480, 'no', 'no', 'no', 'no', 'no');
  
}

function uf_authorityOK() {
document.form1.p_limit_list.value='';
}

//-->
</script>
</head>

<!--<body leftmargin="20" topmargin="20" bottommargin="20" marginwidth="0" marginheight="0" onload=initTree();>
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process"           value="">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr><td class="list_menuma"  valign=top><div id="TREE"  ></td></tr>
</table>
</form>
</body>
-->



<body bgcolor="#FFFFFF" topmargin="0" leftmargin="0" marginheight="0" marginwidth="0" onLoad='initTree()'>
<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="370">
    <tr>
        <td height="380">
            <table cellpadding="0" cellspacing="1" bgcolor="#bebebe" class="table2" height="100%">
        <tr> 
          <td align="center" valign="top" bgcolor="#FFFFFF" class="body_color"> 
            <form name = "form1"    method = "post">
              <input type = "hidden" name = "p_process"           value="">
              <table width="350" border="0" cellspacing="0" cellpadding="0" height="100%">
                <tr> 
                  <td height="3"> <table cellspacing="0" cellpadding="0" class="table2">
                      <tr> 
                        <td background="/images/user/study/postsearch_top_line2.gif" height="8" width="27"><img src="/images/user/study/postsearch_top_line1.gif" width="27" height="8" border="0"></td>
                        <td background="/images/user/study/postsearch_top_line2.gif" width="291"></td>
                        <td align="right" background="/images/user/study/postsearch_top_line2.gif" width="32"><img src="/images/user/study/postsearch_top_line3.gif" width="32" height="8" border="0"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr> 
                  <td height="88"  valign="top" ><img src="/images/user/myclass/tit_category_img.jpg" ></td>
                </tr>
                <tr> 
                  <td align="center" valign="top" class="body_color"> 
                    <br>
                    <!-----------------  시작 ----------------->
                    <table width="334" border="0" cellspacing="1" cellpadding="2">
                      <tr> 
                        <td align="left"><div id="TREE"  ></td>
                      </tr>
                      <tr> 
                      </tr>
                    </table>
                    <!----------------  끝 ----------------->
                    <br>   </td>
                </tr>
			 </table>
            </form>
                
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
