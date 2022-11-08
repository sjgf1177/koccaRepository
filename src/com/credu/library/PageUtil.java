package com.credu.library;

/**
 * <p>
 * 제목: HTML tag 표현과 직접적 관련이 있는 라이브러리
 * </p>
 * <p>
 * 설명:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Credu
 * </p>
 * 
 * @author 이정한
 * @date 2003. 12
 * @version 1.0
 */
public class PageUtil {

    // 리스트 화면 하단의 페이지 정보를 표시한다
    public static String getPageList(int totalPage, int currentPage, int blockSize) throws Exception {
        PageList pagelist = new PageList(totalPage, currentPage, blockSize);
        String str = "";

        //이전 10개
        if (pagelist.previous()) {
            str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "');\"><b><<이전10개</b></a>&nbsp;&nbsp;";
        }

        for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
            if (i == currentPage) {
                str += i + "&nbsp";
            } else {
                str += "<a href=\"javascript:goPage('" + i + "');\">" + i + "</a>&nbsp";
            }
        }

        //다음 10개
        if (pagelist.next()) {
            str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "');\">&nbsp;<b>다음10개>></b></a>";
        }

        if (str.equals("")) {
            str += "자료가 없습니다.";
        }
        return str;
    }

    // 위의 넘과 하는일은 같으나, 보여주는 모양새가 다르다. 우측에 리스트박스가 생겨 페이지 바로가기가 가능함.
    public static String printPageList(int totalPage, int currPage, int blockSize) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str = "";
        if (totalPage > 0) {
            PageList pagelist = new PageList(totalPage, currPage, blockSize);

            str += "<table border='0' width='100%' align='center'>";
            str += "<tr>";
            str += "    <td width='100%' align='center'>";

            //이전 10개
            if (pagelist.previous()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\"><b><<이전10개</b></a>&nbsp;&nbsp;";
                str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "');\"><IMG src='/images/admin/common/prev.gif' border='0' align='absmiddle'></a>";
            } else {
                str += "<IMG src='/images/admin/common/prev.gif' border='0' align='absmiddle'>	";
            }

            for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += "<b>" + i + "</b>&nbsp;";
                } else {
                    str += "<a href=\"javascript:goPage('" + i + "');\">" + i + "</a>&nbsp;";
                }
            }

            //다음 10개
            if (pagelist.next()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\">&nbsp;<b>다음10개>></b></a>";
                str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "');\"><IMG src='/images/admin/common/next.gif' border='0' align='absmiddle'></a>";
            } else {
                str += "<IMG src='/images/admin/common/next.gif' border='0' align='absmiddle'>	";
            }

            if (str.equals("")) {
                str += "자료가 없습니다.";
            }

            str += "    </td>";
            str += "    <td width='15%' align='center'>";

            //if (totalPage > 0) {
            //    str += "<select  onChange='go(this.selectedIndex+1)'>";
            //    for (int k = 1; k <= totalPage; k++) {
            //        if (k == currPage) {
            //            str += "<option selected>" + k;
            //        }
            //        else {
            //            str += "<option>" + k;
            //        }
            //    }
            //    str += "</select>";
            //}

            str += "    </td>";
            str += "</tr>";
            str += "</table>";
        }
        return str;
    }

    // 위의 넘과 하는일은 같으나, 보여주는 모양새가 다르다. 우측에 리스트박스가 생겨 페이지 바로가기가 가능함. (HKMC 강사소개에서 사용)
    public static String printPageList1(int totalPage, int currPage, int blockSize) throws Exception { //사용자쪽화면 페이지나누기

        currPage = (currPage == 0) ? 1 : currPage;
        String str = "";
        if (totalPage > 0) {
            PageList pagelist = new PageList(totalPage, currPage, blockSize);

            str += "<table border='0' width='100%' align='center'>";
            str += "<tr>";
            str += "    <td width='100%' align='center'>";

            if (pagelist.previous()) {
                str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "');\"><b><<이전10개</b></a>&nbsp;&nbsp;";
            }

            for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += i + "&nbsp;";
                } else {
                    str += "<a href=\"javascript:goPage('" + i + "');\">[" + i + "]</a>&nbsp;";
                }
            }

            if (pagelist.next()) {
                str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "');\">&nbsp;<b>다음10개>></b></a>";
            }

            if (str.equals("")) {
                str += "자료가 없습니다.";
            }

            str += "    </td>";
            str += "    <td width='15%' align='center'>";

            if (totalPage > 0) {
                str += "<select  onChange='go(this.selectedIndex+1)' style='border-style:solid;border-width: 1px 1px 1px 1px;border-color:cccccc;color:333333;font-size:9pt;background-color:none;width:40px;height:19px;font-size:9pt;'>";
                for (int k = 1; k <= totalPage; k++) {
                    if (k == currPage) {
                        str += "<option selected>" + k;
                    } else {
                        str += "<option>" + k;
                    }
                }
                str += "</select>";
            }

            str += "    </td>";
            str += "</tr>";
            str += "</table>";
        }
        return str;
    }

    public static String printPageListGame(int totalPage, int currPage, int blockSize) throws Exception {
        currPage = (currPage == 0) ? 1 : currPage;
        String str = "";
        if (totalPage > 0) {
            PageList pagelist = new PageList(totalPage, currPage, blockSize);

            str += "<table border='0' width='100%' align='center'>";
            str += "<tr>";
            str += "    <td width='100%' align='center'>";

            //이전 10개
            if (pagelist.previous()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\"><b><<이전10개</b></a>&nbsp;&nbsp;";
                str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "');\"><IMG src='/images/user/game/button/btn_pre.gif' border='0' align='absmiddle'></a>";
            } else {
                str += "<IMG src='/images/user/game/button/btn_pre.gif' border='0' align='absmiddle'>	";
            }

            for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += "<b>" + i + "</b>&nbsp;";
                } else {
                    str += "<a href=\"javascript:goPage('" + i + "');\">" + i + "</a>&nbsp;";
                }
            }

            //다음 10개
            if (pagelist.next()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\">&nbsp;<b>다음10개>></b></a>";
                str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "');\"><IMG src='/images/user/game/button/btn_next.gif' border='0' align='absmiddle'></a>";
            } else {
                str += "<IMG src='/images/user/game/button/btn_next.gif' border='0' align='absmiddle'>	";
            }

            if (str.equals("")) {
                str += "자료가 없습니다.";
            }

            str += "    </td>";
            str += "    <td width='15%' align='center'>";

            //if (totalPage > 0) {
            //    str += "<select  onChange='go(this.selectedIndex+1)'>";
            //    for (int k = 1; k <= totalPage; k++) {
            //        if (k == currPage) {
            //            str += "<option selected>" + k;
            //        }
            //        else {
            //            str += "<option>" + k;
            //        }
            //    }
            //    str += "</select>";
            //}

            str += "    </td>";
            str += "</tr>";
            str += "</table>";
        }
        //System.out.println("ㄱㄴㄷㄹ"+str);
        return str;
    }

    public static String printPageListKocca(int totalPage, int currPage, int blockSize) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str = "";
        if (totalPage > 0) {
            PageList pagelist = new PageList(totalPage, currPage, blockSize);

            str += "<table border='0' width='100%' align='center'>";
            str += "<tr>";
            str += "    <td width='100%' align='center'>";

            //이전 10개
            if (pagelist.previous()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\"><b><<이전10개</b></a>&nbsp;&nbsp;";
                str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "');\"><IMG src='/images/user/kocca/button/b_pre.gif' border='0' align='absmiddle'></a>";
            } else {
                str += "<IMG src='/images/user/kocca/button/b_pre.gif' border='0' align='absmiddle'>	";
            }

            for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += "<b>" + i + "</b>&nbsp;";
                } else {
                    str += "<a href=\"javascript:goPage('" + i + "');\">" + i + "</a>&nbsp;";
                }
            }

            //다음 10개
            if (pagelist.next()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\">&nbsp;<b>다음10개>></b></a>";
                str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "');\"><IMG src='/images/user/kocca/button/b_next.gif' border='0' align='absmiddle'></a>";
            } else {
                str += "<IMG src='/images/user/kocca/button/b_next.gif' border='0' align='absmiddle'>	";
            }

            if (str.equals("")) {
                str += "자료가 없습니다.";
            }

            str += "    </td>";
            str += "    <td width='15%' align='center'>";

            //if (totalPage > 0) {
            //    str += "<select  onChange='go(this.selectedIndex+1)'>";
            //    for (int k = 1; k <= totalPage; k++) {
            //        if (k == currPage) {
            //            str += "<option selected>" + k;
            //        }
            //        else {
            //            str += "<option>" + k;
            //        }
            //    }
            //    str += "</select>";
            //}

            str += "    </td>";
            str += "</tr>";
            str += "</table>";
        }
        return str;
    }

    // 2009.10.27 한페이지에 보여주는 레코드수를 선택하는 select box 표시
    public static String printPageSizeList(int totalPage, int currPage, int blockSize, int pageSize, int totalRowcount) throws Exception {
        return printPageSizeList(totalPage, currPage, blockSize, pageSize, totalRowcount, false);
    }

    public static String printPageSizeList(int totalPage, int currPage, int blockSize, int pageSize, int totalRowcount, boolean totallist) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str = "";
        if (totalPage > 0) {
            PageList pagelist = new PageList(totalPage, currPage, blockSize);

            str += "<table border='0' width='100%' align='center'>";
            str += "<tr>";
            str += "    <td width='20%'/>";
            str += "    <td width='60%' align='center'>";

            //이전 10개
            if (pagelist.previous()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\"><b><<이전10개</b></a>&nbsp;&nbsp;";
                str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "');\"><IMG src='/images/admin/common/prev.gif' border='0' align='absmiddle'></a>";
            } else {
                str += "<IMG src='/images/admin/common/prev.gif' border='0' align='absmiddle'>	";
            }

            for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += "<b>" + i + "</b>&nbsp;";
                } else {
                    str += "<a href=\"javascript:goPage('" + i + "');\">" + i + "</a>&nbsp;";
                }
            }

            //다음 10개
            if (pagelist.next()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\">&nbsp;<b>다음10개>></b></a>";
                str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "');\"><IMG src='/images/admin/common/next.gif' border='0' align='absmiddle'></a>";
            } else {
                str += "<IMG src='/images/admin/common/next.gif' border='0' align='absmiddle'>	";
            }

            if (str.equals("")) {
                str += "자료가 없습니다.";
            }

            str += "    </td>";
        } else {
            str += "<table border='0' width='100%' align='center'>";
            str += "<tr>";
            str += "    <td width='20%'/>";
            str += "    <td width='60%' align='center'></td>";
        }

        str += "    <td width='20%' align='right'>";
        str += "총 " + totalRowcount + "건&nbsp;&nbsp;";

        //if (totalPage > 0) {
        str += "<select onChange='pagesize(this.value)'>";

        if (totallist) {
            str += "<option value='9999999999999'>전체보기</option>";
        }

        for (int k = 10; k <= 100; k += 10) {
            if (k == pageSize) {
                str += "<option value=" + k + " selected>" + k + " 개씩 보기</option>";
            } else {
                str += "<option value=" + k + ">" + k + " 개씩 보기</option>";
            }
        }
        str += "</select>";
        //}

        str += "    </td>";
        str += "</tr>";
        str += "</table>";

        return str;
    }

    // 2009.10.27 한페이지에 보여주는 레코드수를 선택하는 select box 표시
    public static String printPageSizeList2(int totalPage, int currPage, int blockSize, int pageSize, int totalRowcount, int for1, int for2, int for3) throws Exception {
        return printPageSizeList2(totalPage, currPage, blockSize, pageSize, totalRowcount, false, for1, for2, for3);
    }

    public static String printPageSizeList2(int totalPage, int currPage, int blockSize, int pageSize, int totalRowcount, boolean totallist, int for1, int for2, int for3) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str = "";
        if (totalPage > 0) {
            PageList pagelist = new PageList(totalPage, currPage, blockSize);

            str += "<table border='0' width='100%' align='center'>";
            str += "<tr>";
            str += "    <td width='20%'/>";
            str += "    <td width='60%' align='center'>";

            //이전 10개
            if (pagelist.previous()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\"><b><<이전10개</b></a>&nbsp;&nbsp;";
                str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "');\"><IMG src='/images/admin/common/prev.gif' border='0' align='absmiddle'></a>";
            } else {
                str += "<IMG src='/images/admin/common/prev.gif' border='0' align='absmiddle'>	";
            }

            for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += "<b>" + i + "</b>&nbsp;";
                } else {
                    str += "<a href=\"javascript:goPage('" + i + "');\">" + i + "</a>&nbsp;";
                }
            }

            //다음 10개
            if (pagelist.next()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\">&nbsp;<b>다음10개>></b></a>";
                str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "');\"><IMG src='/images/admin/common/next.gif' border='0' align='absmiddle'></a>";
            } else {
                str += "<IMG src='/images/admin/common/next.gif' border='0' align='absmiddle'>	";
            }

            if (str.equals("")) {
                str += "자료가 없습니다.";
            }

            str += "    </td>";
        } else {
            str += "<table border='0' width='100%' align='center'>";
            str += "<tr>";
            str += "    <td width='20%'/>";
            str += "    <td width='60%' align='center'></td>";
        }

        str += "    <td width='20%' align='right'>";
        str += "총 " + totalRowcount + "건&nbsp;&nbsp;";

        //if (totalPage > 0) {
        str += "<select onChange='pagesize(this.value)'>";

        if (totallist) {
            str += "<option value='9999999999999'>전체보기</option>";
        }

        for (int k = for1; k <= for2; k += for3) {
            if (k == pageSize) {
                str += "<option value=" + k + " selected>" + k + " 개씩 보기</option>";
            } else {
                str += "<option value=" + k + ">" + k + " 개씩 보기</option>";
            }
        }
        str += "</select>";
        //}

        str += "    </td>";
        str += "</tr>";
        str += "</table>";

        return str;
    }

    public static String printPageSizeListDiv(int totalPage, int currPage, int blockSize, int pageSize, int totalRowcount) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str = "";
        if (totalPage > 0) {
            PageList pagelist = new PageList(totalPage, currPage, blockSize);

            str += "<div class=\"pagination\">";

            //이전 10개
            if (pagelist.previous()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\"><b><<이전10개</b></a>&nbsp;&nbsp;";
                str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "');\" class=\"prev\">이전</a>";
            } else {
                str += "이전	";
            }

            for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += "<strong>" + i + "</strong>&nbsp;";
                } else {
                    str += "<a href=\"javascript:goPage('" + i + "');\">" + i + "</a>&nbsp;";
                }
            }

            //다음 10개
            if (pagelist.next()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\">&nbsp;<b>다음10개>></b></a>";
                str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "');\" class=\"next\">다음</a>";
            } else {
                str += "다음	";
            }

            if (str.equals("")) {
                str += "자료가 없습니다.";
            }

            str += "</div>   <p class=\"bo_warp\">";
            str += "총 " + totalRowcount + "건&nbsp;&nbsp;";

            if (totalPage > 0) {
                str += "<select onChange='pagesize(this.value)' class=\"fl_r\">";
                for (int k = 10; k <= 100; k += 10) {
                    if (k == pageSize) {
                        str += "<option value=" + k + " selected>" + k + " 개씩 보기";
                    } else {
                        str += "<option value=" + k + ">" + k + " 개씩 보기";
                    }
                }
                str += "</select>";
            }

            str += "</p>";
        }
        return str;
    }

    public static String printPageSizeListDivNoPagesize(int totalPage, int currPage, int blockSize, int pageSize, int totalRowcount) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str = "";
        if (totalPage > 0) {
            PageList pagelist = new PageList(totalPage, currPage, blockSize);

            str += "<div class=\"pagination\">";

            //이전 10개
            if (pagelist.previous()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\"><b><<이전10개</b></a>&nbsp;&nbsp;";
                str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "');\" class=\"prev\">이전</a>";
            } else {
                str += "이전	";
            }

            for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += "<strong>" + i + "</strong>&nbsp;";
                } else {
                    str += "<a href=\"javascript:goPage('" + i + "');\">" + i + "</a>&nbsp;";
                }
            }

            //다음 10개
            if (pagelist.next()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\">&nbsp;<b>다음10개>></b></a>";
                str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "');\" class=\"next\">다음</a>";
            } else {
                str += "다음	";
            }

            if (str.equals("")) {
                str += "자료가 없습니다.";
            }

            str += "</div>   <p class=\"bo_warp\">";
            str += "</p>";
        }
        return str;
    }

    public static String re_printPageSizeListDiv(int totalPage, int currPage, int blockSize, int pageSize, int totalRowcount) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str = "";
        if (totalPage > 0) {
            PageList pagelist = new PageList(totalPage, currPage, blockSize);

            str += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td>";
            if (totalPage > 0) {
                str += "<table border=\"0\" align=\"right\" cellpadding=\"2\" cellspacing=\"0\"><select  onChange='pagesize(this.value)' class=\"fl_r\">";
                for (int k = 10; k <= 100; k += 10) {
                    if (k == pageSize) {
                        str += "<option value=" + k + " selected>" + k + " 개씩 보기";
                    } else {
                        str += "<option value=" + k + ">" + k + " 개씩 보기";
                    }
                }
                str += "</select></table></td></tr>";
            }
            str += "<tr><td><table border=\"0\" align=\"center\" cellpadding=\"2\" cellspacing=\"0\"><tr>";

            //이전 10개
            if (pagelist.previous()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\"><b><<이전10개</b></a>&nbsp;&nbsp;";
                //str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\" class=\"prev\">이전</a>";
                str += "<td><a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "');\" class=\"prev\"><img src=\"/images/portal/homepage_renewal/common/btn_pre.gif\" alt=\"이전\"/></a></td>";
            } else {
                //str += "이전	";
                str += "<td><img src=\"/images/portal/homepage_renewal/common/btn_pre.gif\" alt=\"맨앞페이지로\"/></td>";
            }

            for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == 1) {
                    str += "<td>&nbsp";
                } else {
                    str += "<td>|&nbsp";
                }
                if (i == currPage) {
                    str += "<strong>" + i + "</strong>&nbsp;";
                } else {

                    str += "<a href=\"javascript:goPage('" + i + "');\">" + i + "</a>&nbsp;";
                }
                str += "</td>";
            }

            //다음 10개
            if (pagelist.next()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\">&nbsp;<b>다음10개>></b></a>";
                //str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\" class=\"next\">다음</a>";
                str += "<td><a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "');\" class=\"next\"><img src=\"/images/portal/homepage_renewal/common/btn_next.gif\" alt=\"다음\"/></a></td>";
            } else {
                str += "<td><img src=\"/images/portal/homepage_renewal/common/btn_next.gif\" alt=\"다음\"/></td>";
            }

            if (str.equals("")) {
                str += "자료가 없습니다.";
            }

            str += "</tr></table></td></tr></table>";

        }
        return str;
    }

    public static String re_printPageListDiv(int totalPage, int currPage, int blockSize, int pageSize, int totalRowcount) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str = "";
        if (totalPage > 0) {
            PageList pagelist = new PageList(totalPage, currPage, blockSize);

            str += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td>";

            str += "<tr><td><table border=\"0\" align=\"center\" cellpadding=\"2\" cellspacing=\"0\"><tr>";

            //이전 10개
            if (pagelist.previous()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\"><b><<이전10개</b></a>&nbsp;&nbsp;";
                //str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\" class=\"prev\">이전</a>";
                str += "<td><a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "');\" class=\"prev\"><img src=\"/images/portal/homepage_renewal/common/btn_pre.gif\" alt=\"이전\"/></a></td>";
            } else {
                //str += "이전	";
                str += "<td><img src=\"/images/portal/homepage_renewal/common/btn_pre.gif\" alt=\"맨앞페이지로\"/></td>";
            }

            for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == 1) {
                    str += "<td>&nbsp";
                } else {
                    str += "<td>|&nbsp";
                }
                if (i == currPage) {
                    str += "<strong>" + i + "</strong>&nbsp;";
                } else {

                    str += "<a href=\"javascript:goPage('" + i + "');\">" + i + "</a>&nbsp;";
                }
                str += "</td>";
            }

            //다음 10개
            if (pagelist.next()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\">&nbsp;<b>다음10개>></b></a>";
                //str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\" class=\"next\">다음</a>";
                str += "<td><a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "');\" class=\"next\"><img src=\"/images/portal/homepage_renewal/common/btn_next.gif\" alt=\"다음\"/></a></td>";
            } else {
                str += "<td><img src=\"/images/portal/homepage_renewal/common/btn_next.gif\" alt=\"다음\"/></td>";
            }

            if (str.equals("")) {
                str += "자료가 없습니다.";
            }

            str += "</tr></table></td></tr></table>";

        }
        return str;
    }

    public static String re2012_printPageListDiv(int totalPage, int currPage, int blockSize, int pageSize, int totalRowcount) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str = "";
        if (totalPage > 0) {
            PageList pagelist = new PageList(totalPage, currPage, blockSize);
            str += "<table class=\"numberset\" cellspacing=\"0\" cellpadding=\"0\">";
            str += "<tr>";

            //이전 10개
            if (pagelist.previous()) {
                str += "<td><a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "');\" tabindex=\"401\" title=\"이전 10페이지로 이동합니다.\"><img src=\"/images/2012/sub/page1/btn_prevpage.gif\" align=\"absmiddle\" alt=\"이전 10페이지로\"></a></td>";
            } else {
                //str += "이전	";
                str += "<td><img src=\"/images/2012/sub/page1/btn_prevpage.gif\" align=\"middle\" alt=\"이전 10페이지로\"></td>";
            }

            for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == 1) {
                    str += "<td class=\"listbottom\">&nbsp";
                } else {
                    str += "<td class=\"listbottom\" id=\"aboardfocus" + (i - 2) + "\">&nbsp";
                }
                if (i == currPage) {
                    str += "<strong>" + i + "</strong>&nbsp;";
                } else {

                    str += "<a href=\"javascript:goPage('" + i + "');\" tabindex=\"41" + (i - 1) + "\" onfocus=\"boardfocus('a','" + (i - 2) + "')\" onblur=\"boardfocus('a','" + (i - 2) + "')\" title=\"페이지 " + i + "로 이동합니다\">" + i + "</a>&nbsp;";
                }
                str += "</td>";
            }
            //다음 10개
            if (pagelist.next()) {
                //str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\">&nbsp;<b>다음10개>></b></a>";
                //str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\" class=\"next\">다음</a>";
                str += "<td><a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "');\" tabindex=\"421\" class=\"next\"><img src=\"/images/2012/sub/page1/btn_nextpage.gif\" alt=\"다음\"/></a></td>";
            } else {
                str += "<td><img src=\"/images/2012/sub/page1/btn_nextpage.gif\" align=\"middle\" alt=\"다음 10페이지로\"></td>";
            }

            if (str.equals("")) {
                str += "자료가 없습니다.";
            }

            str += "</tr></table>";

        }
        return str;
    }

    /**
     * 
     * @param totalPage
     * @param currPage
     * @param blockSize
     * @param pageSize
     * @param totalRowcount
     * @return
     * @throws Exception
     */
    public static String re2013_printPageListDiv(int totalPage, int currPage, int blockSize, int pageSize, int totalRowcount) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str = "";
        if (totalPage > 0) {
            PageList pagelist = new PageList(totalPage, currPage, blockSize);

            str += "<span>";
            //이전 10개
            if (pagelist.previous()) {
                str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "');\"><img src=\"/images/2013/common/btn_pre.jpg\" alt=\"이전페이지\" /></a>";
            } else {
                str += "<a href=\"javascript:void(0);\"><img src=\"/images/2013/common/btn_pre.jpg\" alt=\"이전페이지\" /></a>";
            }


            for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += "<strong>" + i + "</strong>";
                } else {
                    str += "<a href=\"javascript:goPage('" + i + "');\">" + i + "</a>";
                }
            }


            //다음 10개
            if (pagelist.next()) {
                str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "');\"><img src=\"/images/2013/common/btn_next.jpg\" alt=\"다음페이지\" /></a>";
            } else {
                str += "<a href=\"javascript:void(0);\"><img src=\"/images/2013/common/btn_next.jpg\" alt=\"다음페이지\" /></a>";
            }

            if (str.equals("")) {
                str += "자료가 없습니다.";
            }
            str += "</span>";

            // str += "</tr></table>";

        }
        return str;
    }

    /**
     * 모바일 페이지 번호
     * 
     * @param i
     * @param j
     * @param k
     * @param imgurl
     * @param pageText
     * @param pageOn
     * @return
     * @throws Exception
     */
    public static String printPageMobileList(int i, int j, int k, String imgurl) throws Exception {
        String v_scrit = "goPage";
        return printPageMobileList(i, j, k, imgurl, v_scrit);
    }

    /**
     * 모바일 페이지 번호
     * 
     * @param i
     * @param j
     * @param k
     * @param imgurl
     * @param pageText
     * @param pageOn
     * @param v_scrit
     * @return
     * @throws Exception
     */
    public static String printPageMobileList(int i, int j, int k, String imgurl, String v_scrit) throws Exception {
        j = j != 0 ? j : 1;
        String s = "";
        if (i > 0) {
            // PageList pagelist = new PageList(i, j, 5);            

            if (k > 0 && (i == j || j == 1)) {
                s = s + "<button id=\"pg_btn_prev3\" class=\"pg_btn_prev\" onclick=\"" + v_scrit + "('1');\" type=\"button\">이전 페이지</button>";
            } else if (k > 0) {
                s = s + "<button id=\"pg_btn_prev3\" class=\"pg_btn_prev\" onclick=\"" + v_scrit + "('" + (j - 1) + "');\" type=\"button\">이전 페이지</button>";
            }

            s = s + "\r\n<em class=\"pg_num_on2\" title=\"현재 페이지\">" + j + "</em>/" + i + "</span>";

            if (k > 0 && i == j) {
                s = s + "\r\n<button id=\"pg_btn_next3\" class=\"pg_btn_next\" onclick=\"" + v_scrit + "('" + (j) + "');\" type=\"button\">다음 페이지</button>";
            } else if (k > 0) {
                s = s + "\r\n<button id=\"pg_btn_next3\" class=\"pg_btn_next\" onclick=\"" + v_scrit + "('" + (j + 1) + "');\" type=\"button\">다음 페이지</button>";
            }

        }
        return s;
    }
    
    public static String typeB_printPageListDiv(int totalPage, int currPage, int blockSize) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str = "";
        if (totalPage > 0) {
            PageList pagelist = new PageList(totalPage, currPage, blockSize);

            str += "<div class=\"paging\">";
            str += "<div class=\"paginationSet\">";
            str += "<ul class=\"pagination pagination-centered\" id=\"paging\">";
            //이전 10개
            if (pagelist.previous()) {
            	str += "<li class=\"prev\" onclick=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "');\"><a href=\"javascript:void(0);\"><span></span></a></li>";
            } else {
            	str += "<li class=\"prev\"><a href=\"javascript:void(0);\"><span></span></a></li>";
            }


            for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += "<li class=\"active\"><span>"+i+"</span></li>";
                } else {
                	str += "<li><a href=\"javascript:goPage('" + i + "');\"><span>"+i+"</span></a></li>";
                }
            }


            //다음 10개
            if (pagelist.next()) {
            	str += "<li class=\"next\" onclick=\"javascript:goPage('" + pagelist.getNextStartPage() + "');\"><a href=\"javascript:void(0);\"></a></li>";
            } else {
            	str += "<li class=\"next\"><a href=\"javascript:void(0);\"></a></li>";
            }

            str += "</ul>";
            str += "</div>";
            str += "</div>";
        }
        return str;
    }
}
