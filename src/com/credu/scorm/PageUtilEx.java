package com.credu.scorm;

import java.util.Enumeration;
import java.util.Vector;
//import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package alexit.lib.util:
//            PageUtil, FormatUtil, FileUtil

public class PageUtilEx extends PageUtil
{

    private PageUtilEx()
    {
        max_rowid = "";
        min_rowid = "";
        param = "";
    }

    public PageUtilEx(int pTotalCount, String min_rowid, String max_rowid, HttpServletRequest req)
    {
        this.max_rowid = "";
        this.min_rowid = "";
        this.param = "";
        int pCurrentPage = Integer.parseInt(FormatUtil.isnull2(req.getParameter("currentPage"), "1"));
        String pLinkURI = FileUtil.getFileName(req.getRequestURI());
        this.max_rowid = max_rowid;
        this.min_rowid = min_rowid;
        this.param = PageUtil.getParam(req);
        String param = "&min_rowid=" + min_rowid + "&max_rowid=" + max_rowid;
        pTotalCount += ((pCurrentPage - 1) / super.tabPerPage) * super.tabPerPage * super.linePerPage;
        init(pTotalCount, pCurrentPage, pLinkURI, param + "&" + this.param);
    }

    public String getPrev()
    {
        String resultVal = "";
        if(super.startTab > 1)
            resultVal = "<a href='" + super.linkURI + "?direction=-1&currentPage=" + (super.startTab - 1) + super.linkParam + "'><img src='/images/l1.gif' border='0' align='absbottom'></a>" + super.SPACE;
        return resultVal;
    }

    public String getNext()
    {
        String resultVal = "";
        String nextParam = "&min_rowid=" + max_rowid + "&max_rowid=" + min_rowid + "&" + param;
        if(super.totalPage > super.endTab)
            resultVal = "<a href='" + super.linkURI + "?direction=1&currentPage=" + (super.endTab + 1) + nextParam + "'><img src='/images/r1.gif' border='0' align='absbottom'></a>";
        return resultVal;
    }

    public String getPrevJS()
    {
        String resultVal = "";
        if(super.startTab > 1)
            resultVal = "<a href=\"javascript:goPage('-1','" + (super.startTab - 1) + "')\"><img src='/images/l1.gif' border='0'></a>";
        return resultVal;
    }

    public String getNextJS()
    {
        String resultVal = "";
        if(super.totalPage > super.endTab)
            resultVal = "<a href=\"javascript:goPage('1','" + (super.endTab + 1) + "')\"><img src='/images/r1.gif' border='0'></a>";
        return resultVal;
    }

    public Enumeration getPageTab()
    {
        Vector v = new Vector();
        for(int i = super.startTab; i <= super.endTab; i++)
        {
            String tabStr = "";
            if(i == super.currentPage)
                tabStr = "<b>[" + i + "]</b> ";
            else
                tabStr = "<a href='" + super.linkURI + "?direction=1&currentPage=" + i + super.linkParam + "'>" + i + "</a>" + super.SPACE;
            v.add(tabStr);
        }

        return v.elements();
    }

    public Enumeration getPageTabJS()
    {
        Vector v = new Vector();
        for(int i = super.startTab; i <= super.endTab; i++)
        {
            String tabStr = "";
            if(i == super.currentPage)
                tabStr = "<b>[" + i + "]</b> ";
            else
                tabStr = "<a href=\"javascript:goPage('1','" + i + "')\">" + i + "</a>" + super.SPACE;
            v.add(tabStr);
        }

        return v.elements();
    }

    public String getPageLine2()
    {
        String resultStr = "";
        if(super.startTab > 1 && super.currentPage != 1 && super.currentPage % 10 == 1)
            resultStr = "<a href='" + super.linkURI + "?direction=-1&currentPage=" + (super.startTab - 1) + super.linkParam + "'><img src='/images/l1.gif' border='0' align='absbottom'></a>";
        else
        if(super.currentPage > 1)
            resultStr = "<a href='" + super.linkURI + "?direction=1&currentPage=" + (super.currentPage - 1) + super.linkParam + "'><img src='/images/l1.gif' border='0'></a>";
        if(super.totalPage > super.currentPage)
            if(super.currentPage % 10 == 0)
            {
                String nextParam = "&min_rowid=" + max_rowid + "&max_rowid=" + min_rowid + "&" + param;
                resultStr = resultStr + "<a href='" + super.linkURI + "?direction=1&currentPage=" + (super.endTab + 1) + nextParam + "'><img src='/images/r1.gif' border='0' align='absbottom'></a>";
            } else
            {
                resultStr = resultStr + "<a href='" + super.linkURI + "?currentPage=" + (super.currentPage + 1) + super.linkParam + "'><img src='/images/r1.gif' border='0'></a>";
            }
        return resultStr;
    }

    private String max_rowid;
    private String min_rowid;
    private String param;
}
