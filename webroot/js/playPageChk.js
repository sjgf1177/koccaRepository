isPageControl = false;
isFinishControl = false;

function playPageSave(ct, tt, cc, cp, tp, np) {
    isPageControl = false;

    $.ajax({
        type : "post"
        ,   url : "/servlet/controller.contents.EduStart"
        ,   data : {
                ct : ct,
                tt : tt,
                cc : cc,
                cp : cp,
                tp : tp,
                np : np,
                p_process : "subjseqPageClassInfo"
        }
        ,   success : function(ajaxData) {
                isPageControl = true;
                if(cp == tp){
                    opener.lastPageProgress();
                }
        }
        ,   error :  function(arg1, arg2) {
                alert("오류가 발생하여 플레이 할 수 없습니다.");
        }
    });
}

function pageChk(chap, cp){
    isPageControl = false;
    isFinishControl = false;

    $.ajax({
        type : "post"
        ,   url : "/servlet/controller.contents.EduStart"
        ,   dataType : "json"
        ,   data : {
                chap : chap,
                cp : cp,
                p_process : "pageControlChk"
        }
        ,   success : function(data) {
            var res = "";

            if(data.resList.length > 0){
                res = data.resList[0];

                if(res.pageChkYn == 'Y'){
                    isPageControl = true;
                }

                if(res.finishPageYn == 'Y'){
                    isFinishControl = true;
                }
            }
        }
        ,   error :  function(arg1, arg2) {
                alert("오류가 발생하였습니다.");
        }
    });
}