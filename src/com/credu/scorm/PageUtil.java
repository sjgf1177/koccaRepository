package com.credu.scorm;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Vector;
//import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package alexit.lib.util:
//            FormatUtil, FileUtil

public class PageUtil
{

    public PageUtil()
    {
        linePerPage = 10;
        tabPerPage = 10;
        linkURI = "";
        linkParam = "";
        totalPage = 0;
        totalCount = 0;
        currentPage = 0;
        left_img = "<img src='/images/l1.gif' border='0' align='absbottom'>";
        right_img = "<img src='/images/r1.gif' border='0' align='absbottom'>";
        startTab = 0;
        endTab = 0;
        startPos = 0;
        endPos = 0;
        SPACE = " ";
    }

    public PageUtil(int pTotalCount, int pCurrentPage, String pLinkURI, String pLinkParam)
    {
        linePerPage = 10;
        tabPerPage = 10;
        linkURI = "";
        linkParam = "";
        totalPage = 0;
        totalCount = 0;
        currentPage = 0;
        left_img = "<img src='/images/l1.gif' border='0' align='absbottom'>";
        right_img = "<img src='/images/r1.gif' border='0' align='absbottom'>";
        startTab = 0;
        endTab = 0;
        startPos = 0;
        endPos = 0;
        SPACE = " ";
        init(pTotalCount, pCurrentPage, pLinkURI, pLinkParam);
    }

    public PageUtil(int pTotalCount, HttpServletRequest req)
    {
        linePerPage = 10;
        tabPerPage = 10;
        linkURI = "";
        linkParam = "";
        totalPage = 0;
        totalCount = 0;
        currentPage = 0;
        left_img = "<img src='/images/l1.gif' border='0' align='absbottom'>";
        right_img = "<img src='/images/r1.gif' border='0' align='absbottom'>";
        startTab = 0;
        endTab = 0;
        startPos = 0;
        endPos = 0;
        SPACE = " ";
        int pCurrentPage = Integer.parseInt(FormatUtil.isnull2(req.getParameter("currentPage"), "1"));
        String pLinkURI = FileUtil.getFileName(req.getRequestURI());
        init(pTotalCount, pCurrentPage, pLinkURI, "&" + getParam(req));
    }

    public PageUtil(int pTotalCount, HttpServletRequest req, String left_img, String right_img)
    {
        linePerPage = 10;
        tabPerPage = 10;
        linkURI = "";
        linkParam = "";
        totalPage = 0;
        totalCount = 0;
        currentPage = 0;
        this.left_img = "<img src='/images/l1.gif' border='0' align='absbottom'>";
        this.right_img = "<img src='/images/r1.gif' border='0' align='absbottom'>";
        startTab = 0;
        endTab = 0;
        startPos = 0;
        endPos = 0;
        SPACE = " ";
        int pCurrentPage = Integer.parseInt(FormatUtil.isnull2(req.getParameter("currentPage"), "1"));
        String pLinkURI = FileUtil.getFileName(req.getRequestURI());
        set_lr_img(left_img, right_img);
        init(pTotalCount, pCurrentPage, pLinkURI, "&" + getParam(req));
    }

    public PageUtil(int pTotalCount, HttpServletRequest req, String left_img, String right_img, int linePerPage)
    {
        this.linePerPage = 10;
        tabPerPage = 10;
        linkURI = "";
        linkParam = "";
        totalPage = 0;
        totalCount = 0;
        currentPage = 0;
        this.left_img = "<img src='/images/l1.gif' border='0' align='absbottom'>";
        this.right_img = "<img src='/images/r1.gif' border='0' align='absbottom'>";
        startTab = 0;
        endTab = 0;
        startPos = 0;
        endPos = 0;
        SPACE = " ";
        int pCurrentPage = Integer.parseInt(FormatUtil.isnull2(req.getParameter("currentPage"), "1"));
        String pLinkURI = FileUtil.getFileName(req.getRequestURI());
        set_lr_img(left_img, right_img);
        set_linePerPage(linePerPage);
        init(pTotalCount, pCurrentPage, pLinkURI, "&" + getParam(req));
    }

    public PageUtil(int pTotalCount, int currentPage, HttpServletRequest req)
    {
        linePerPage = 10;
        tabPerPage = 10;
        linkURI = "";
        linkParam = "";
        totalPage = 0;
        totalCount = 0;
        this.currentPage = 0;
        left_img = "<img src='/images/l1.gif' border='0' align='absbottom'>";
        right_img = "<img src='/images/r1.gif' border='0' align='absbottom'>";
        startTab = 0;
        endTab = 0;
        startPos = 0;
        endPos = 0;
        SPACE = " ";
        int pCurrentPage = currentPage;
        String pLinkURI = FileUtil.getFileName(req.getRequestURI());
        init(pTotalCount, pCurrentPage, pLinkURI, "&" + getParam(req));
    }

    public PageUtil(int pTotalCount, HttpServletRequest req, int linePerPage, int tabPerPage)
    {
        this.linePerPage = 10;
        this.tabPerPage = 10;
        linkURI = "";
        linkParam = "";
        totalPage = 0;
        totalCount = 0;
        currentPage = 0;
        left_img = "<img src='/images/l1.gif' border='0' align='absbottom'>";
        right_img = "<img src='/images/r1.gif' border='0' align='absbottom'>";
        startTab = 0;
        endTab = 0;
        startPos = 0;
        endPos = 0;
        SPACE = " ";
        this.linePerPage = linePerPage;
        this.tabPerPage = tabPerPage;
        int pCurrentPage = Integer.parseInt(FormatUtil.isnull2(req.getParameter("currentPage"), "1"));
        String pLinkURI = FileUtil.getFileName(req.getRequestURI());
        init(pTotalCount, pCurrentPage, pLinkURI, "&" + getParam(req));
    }

    public void set_lr_img(String left_img, String right_img)
    {
        this.left_img = left_img;
        this.right_img = right_img;
    }

    public void set_linePerPage(int linePerPage)
    {
        this.linePerPage = linePerPage;
    }

    protected static String getParam(HttpServletRequest req)
    {
        String exceptParam = "currentPage,max_rowid,min_rowid,direction";
        String param = "";
        StringBuffer qstring = new StringBuffer();
        for(Enumeration e = req.getParameterNames(); e != null && e.hasMoreElements();)
        {
            param = ((String)e.nextElement()).trim();
            if(exceptParam.indexOf(param) < 0)
                qstring.append(param).append("=").append(URLEncoder.encode(req.getParameter(param))).append("&");
        }

        return qstring.toString();
    }

    protected void init(int pTotalCount, int pCurrentPage, String pLinkURI, String pLinkParam)
    {
        totalCount = pTotalCount;
        currentPage = pCurrentPage;
        linkURI = pLinkURI;
        linkParam = pLinkParam;
        startPos = (currentPage * linePerPage - linePerPage) + 1;
        endPos = (startPos + linePerPage) - 1;
        if(endPos > totalCount)
            endPos = totalCount;
        totalPage = totalCount / linePerPage;
        if(totalCount % linePerPage > 0)
            totalPage++;
        totalPage = totalPage != 0 ? totalPage : 1;
        startTab = ((currentPage - 1) / tabPerPage) * tabPerPage + 1;
        endTab = (startTab + tabPerPage) - 1;
        if(endTab > totalPage)
            endTab = totalPage;
    }

    protected String getPrev()
    {
        String resultVal = "";
        if(startTab > 1)
            resultVal = "<a href='" + linkURI + "?currentPage=" + (startTab - 1) + linkParam + "'>" + left_img + "</a>" + SPACE;
        return resultVal;
    }

    protected String getNext()
    {
        String resultVal = "";
        if(totalPage > endTab)
            resultVal = "<a href='" + linkURI + "?currentPage=" + (endTab + 1) + linkParam + "'>" + right_img + "</a>";
        return resultVal;
    }

    protected Enumeration getPageTab()
    {
        Vector v = new Vector();
        for(int i = startTab; i <= endTab; i++)
        {
            String tabStr = "";
            if(i == currentPage)
                tabStr = SPACE + i + SPACE;
            else
                tabStr = "<a href='" + linkURI + "?currentPage=" + i + linkParam + "'>" + i + "</a>" + SPACE;
            v.add(tabStr);
        }

        return v.elements();
    }

    public String getPageLine()
    {
        String resultStr = "";
        int count = 0;
        resultStr = SPACE + getPrev() + SPACE;
        for(Enumeration en = getPageTab(); en.hasMoreElements();)
        {
            resultStr = resultStr + en.nextElement();
            count++;
        }

        if(count == 0)
            resultStr = SPACE + 1 + SPACE;
        resultStr = resultStr + SPACE + getNext() + SPACE;
        return resultStr;
    }

    protected String getPrevJS()
    {
        String resultVal = "";
        if(startTab > 1)
            resultVal = "<a href=\"javascript:goPage('" + (startTab - 1) + "')\">" + left_img + "</a>";
        return resultVal;
    }

    protected String getNextJS()
    {
        String resultVal = "";
        if(totalPage > endTab)
            resultVal = "<a href=\"javascript:goPage('" + (endTab + 1) + "')\">" + right_img + "</a>";
        return resultVal;
    }

    protected Enumeration getPageTabJS()
    {
        Vector v = new Vector();
        for(int i = startTab; i <= endTab; i++)
        {
            String tabStr = "";
            if(i == currentPage)
                tabStr = SPACE + i + SPACE;
            else
                tabStr = "<a href=\"javascript:goPage('" + i + "')\">" + i + "</a>" + SPACE;
            v.add(tabStr);
        }

        return v.elements();
    }

    public String getPageLineJS()
    {
        String resultStr = "";
        int count = 0;
        resultStr = SPACE + getPrevJS() + SPACE;
        for(Enumeration en = getPageTabJS(); en.hasMoreElements();)
        {
            resultStr = resultStr + en.nextElement();
            count++;
        }

        if(count == 0)
            resultStr = SPACE + 1 + SPACE;
        else
            resultStr = resultStr + SPACE + getNextJS() + SPACE;
        return resultStr;
    }

    protected Enumeration getPageSelectTab()
    {
        Vector v = new Vector();
        for(int i = startTab; i <= endTab; i++)
        {
            String tabStr = "";
            if(i == currentPage)
                tabStr = "<option selected>" + i + "</option>";
            else
                tabStr = "<option value='" + i + "'>" + i + "</option>";
            v.add(tabStr);
        }

        return v.elements();
    }

    protected String getPageSelectLine()
    {
        String attachScript = "<script>function goSelectPage(page){location='" + linkURI + "?currentPage='+page+'" + linkParam + "';}</script>";
        String resultStr = "<select OnChange='javascript:goSelectPage(this.value);'>";
        int count = 0;
        for(Enumeration en = getPageSelectTab(); en.hasMoreElements();)
        {
            resultStr = resultStr + en.nextElement();
            count++;
        }

        if(count == 0)
            resultStr = resultStr + "<option value='1'>" + 1 + "</option>";
        return attachScript + resultStr + "</select>";
    }

    protected int linePerPage;
    protected int tabPerPage;
    protected String linkURI;
    protected String linkParam;
    protected int totalPage;
    protected int totalCount;
    protected int currentPage;
    protected String left_img;
    protected String right_img;
    protected int startTab;
    protected int endTab;
    protected int startPos;
    protected int endPos;
    protected String SPACE;
}
