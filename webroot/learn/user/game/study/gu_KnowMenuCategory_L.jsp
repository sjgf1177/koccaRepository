<%
//**********************************************************
//  1. 제      목: 지식공유
//  2. 프로그램명 :gu_KnowMenuCategory_L.jsp
//  3. 개      요: 지식공유 메뉴 카테고리 선택
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%
    String   v_scriptAry = "";

    ArrayList knowlist = KnowBoardUserBean.SelectMenuCategoryTreeList(box);              

    String   v_returncatecd   = box.getString("p_categorycd");    
                    
    if(v_returncatecd.equals("")){
    
        for (int k=0; k<knowlist.size(); k++){
            DataBox kdbox = (DataBox)knowlist.get(k);
            String v_tmpdir = "false";
            int subcnt = kdbox.getInt("d_subcnt");
            if (subcnt>0) v_tmpdir="true";
            v_scriptAry += "addMemory( "+v_tmpdir
                          +"          , '"+kdbox.getString("d_subjclass")+"'"
                          +"          , 'MAIN'"            
                          +"          , '"+kdbox.getString("d_classname")+"'"
                          +"          , '"+kdbox.getString("d_root")+"'"
                          +"          , '"+kdbox.getString("d_parent")+"'"
                          +"          , '"+kdbox.getInt("d_levels")+"'"
                          +"          , '"+subcnt+"'"
                          +"          , '"+kdbox.getString("d_viewname")+"'"                      
                          +"          , '0'"    // 토글 닫힘 상태                               
                          +" ); \n";
        }  
    }else{

        for (int k=0; k<knowlist.size(); k++){
            DataBox kdbox = (DataBox)knowlist.get(k);
            String v_tmpdir = "false";
            int subcnt = kdbox.getInt("d_subcnt");
            if (subcnt>0) v_tmpdir="true";
            
            String subjclass = kdbox.getString("d_subjclass");
            String classname = kdbox.getString("d_classname");
            String isopen = "0";
            String isparent = "";
            String isroot = "";
           
            if(v_returncatecd.equals(subjclass)){
                classname = "<b><font-size=10pt color=2F7C70 >"+kdbox.getString("d_classname")+"</font></b>";
            }

            if(v_returncatecd.equals(subjclass))  isopen = "1";

            if((!v_returncatecd.substring(3,6).equals("000"))&&(v_returncatecd.substring(6,9).equals("000"))){ // 중분류 토글      
                if(subjclass.equals(v_returncatecd.substring(0,3)+"000000"))  isopen = "1";
            }else{ // 소분류 토글
                if(subjclass.equals(v_returncatecd.substring(0,3)+"000000"))  isopen = "1";
                if(subjclass.equals(v_returncatecd.substring(0,6)+"000"))  isopen = "1";
            }

            v_scriptAry += "addMemory( "+v_tmpdir
                          +"          , '"+subjclass+"'"
                          +"          , 'MAIN'"            
                          +"          , '"+classname+"'"
                          +"          , '"+kdbox.getString("d_root")+"'"
                          +"          , '"+kdbox.getString("d_parent")+"'"
                          +"          , '"+kdbox.getInt("d_levels")+"'"
                          +"          , '"+subcnt+"'"
                          +"          , '"+kdbox.getString("d_viewname")+"'"                      
                          +"          , '"+isopen+"'"    // 토글 열림                                        
                          +" ); \n";
        }      
    
    }
    
%>  
<!--link href="/css/user_style_community2.css" rel="stylesheet" type="text/css"-->
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
   var IMG_PATH = "/images/user/game/mystudy/type1/tree/";
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
   //var NODE      = "PAPER.gif";
    var NODE       = "DIR.gif";
    
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
    function addMemory( dir , menuno , target , desc , root, parent, level, subcnt, viewname, isopen )
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
            
       //if( MEMORY[index].dir == false ){
       //     MEMORY[index].open = true;
       //}else{
       //     MEMORY[index].open = false;
       //}


       if( isopen == "1" ){
            MEMORY[index].open = true;
       }else{
            MEMORY[index].open = false;
       }       

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
      text += "<table align=center border=0 cellspacing=0 cellpadding=0>";
      for (var i = 0 ; i < ITEM.length ; i++ ){
          if (true ){
              text += "<tr>"
                   + "<td>"
                   + "  <table border=0 cellspacing=0 cellpadding=0>"
                   + "   <tr>"
                   + "    <td><span style='font-size:8pt;color:2F7C70'>";
              text += getBlankImage(i);
              text += getLinkerImage(i);
              //text += getDirImage(i);
              text += "</span></td>"
                   + "<td align=left valign=bottom>";
              //text += "<A href=\"javascript:uf_MoveCode('"+ITEM[i].menuno+"','"+ITEM[i].desc+"','"+ITEM[i].root+"','"+ITEM[i].parent+"','"+ITEM[i].level+"','"+ITEM[i].subcnt+");\">"+ITEM[i].desc+"</A>";
			  if( ITEM[i].level == "0" && ITEM[i].subcnt > 0 ){
				  text += "<span style='font-size:8pt;color:2F7C70'>"+ITEM[i].desc+"</span>";
			  }else{
					text += "<A href=\"javascript:MoveCategory('"+ITEM[i].menuno+"','"+ITEM[i].viewname+"','"+ITEM[i].level+"');\"><span style='font-size:8pt;color:2F7C70'>"+ITEM[i].desc+"</span></A>";
			  }
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
                 if (ITEM[index].dir == true ){ // 하위가 있으면
                     temp += "<IMG border=0 src=" + IMG_PATH + CLOSE + " alt=열기 width=" + IMG_WIDTH + " height=" + IMG_HEIGHT + ">";
                 }else{
					 temp += "<IMG border=0 src=" + IMG_PATH + OPEN_END + "  width=" + IMG_WIDTH + " height=" + IMG_HEIGHT + ">";
                 }
             
          } else {
             temp += "<IMG border=0 src=" + IMG_PATH + OPEN  + " alt=닫기 width=" + IMG_WIDTH + " height=" + IMG_HEIGHT + ">";
          }
      // } else {
     //    if (ITEM[index].open == true ){//
         
     //            if (ITEM[index].dir == true ){ // 하위가 있으면
          //           temp += "<IMG border=0 src=" + IMG_PATH + CLOSE_END + " alt=열기 width=" + IMG_WIDTH + " height=" + //IMG_HEIGHT + ">";
          //       }else{
          //           temp += "<IMG border=0 src=" + IMG_PATH + OPEN_END + "  width=" + IMG_WIDTH + " height=" + IMG_HEIGHT + ">";
          //       }
                          
             
         } else {
             temp += "<IMG border=0 src=" + IMG_PATH + OPEN_END  + " alt=닫기 width=" + IMG_WIDTH + " height=" + IMG_HEIGHT + ">";
         }
      // }
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
    
    //alert(menuno);

  }
  
  function MoveCategory(menuno,viewname,level){
      //alert('해당 카테고리별 리스트로 이동합니다.');
      document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd="+menuno;
      document.form1.submit();
      //opener.form1.p_catecd.value = menuno;
      //opener.form1.p_catenm.value = viewname;
      //window.self.close();
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


function uf_authorityOK() {
document.form1.p_limit_list.value='';
}

//-->
</script>


<table width="140" border="0" cellspacing="1" cellpadding="2">
  <tr>
    <td valign=top align="left"><div id="TREE"></div></td>
  </tr>
</table>

<script language="JavaScript" type="text/JavaScript">
<!--
initTree();

//-->
</script>
