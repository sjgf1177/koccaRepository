/**
 * FileName : KoccaTag.java
 * Comment :
 * @version : 1.0
 * @author : kocca
 * @date : 2015. 1. 29.
 */
package common;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import com.credu.common.KoccaTagBean;
import com.credu.library.DataBox;

/**
 * @author kocca
 * 
 */
public class KoccaTag implements Tag {
    private PageContext pageContext;
    private Tag parentTag;

    String name = null;
    String id = null;
    String codeValue = null;
    String optionTitle = "";
    String optionTitleValue = "";
    String selectedValue = null;

    boolean isLoad = false;
    String type = null;
    String sqlID = null;
    String param = null;

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();

            StringBuffer sb = new StringBuffer();

            ArrayList<DataBox> list = new ArrayList<DataBox>();

            if (this.isLoad) {
                if (this.type != null && this.type.equals("sqlID")) {
                    if (this.param != null) {
                        list = KoccaTagBean.getSelectBoxListBySqlID(this.sqlID, this.param);
                    } else {
                        list = KoccaTagBean.getSelectBoxListBySqlID(this.sqlID);
                    }

                } else {
                    list = KoccaTagBean.getSelectBoxListByCode(this.codeValue);
                }
            }

            DataBox dbox = null;

            sb.append("<select id=\"").append(this.id).append("\" name=\"").append(this.name).append("\">\n");
            if (this.optionTitle != null && !this.optionTitle.equals("")) {
                sb.append("    <option value=\"" + this.optionTitleValue + "\">").append(this.optionTitle).append("</option>\n");
            }

            for (int i = 0; i < list.size(); i++) {
                dbox = (DataBox) list.get(i);
                if (this.selectedValue != null && !this.selectedValue.equals("") && this.selectedValue.equals(dbox.getString("d_code"))) {
                    sb.append("    <option value=\"").append(dbox.getString("d_code")).append("\" selected=\"selected\">").append(dbox.getString("d_codenm")).append("</option>\n");
                } else {
                    sb.append("    <option value=\"").append(dbox.getString("d_code")).append("\">").append(dbox.getString("d_codenm")).append("</option>\n");
                }
            }

            sb.append("</select>");
            out.print(sb.toString());

        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.Tag#getParent()
     */
    public Tag getParent() {
        return this.parentTag;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.jsp.tagext.Tag#setPageContext(javax.servlet.jsp.PageContext
     * )
     */
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.Tag#setParent(javax.servlet.jsp.tagext.Tag)
     */
    public void setParent(Tag parentTag) {
        this.parentTag = parentTag;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSqlID(String sqlID) {
        this.sqlID = sqlID;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setIsLoad(boolean isLoad) {
        this.isLoad = isLoad;
    }
    
    public void setOptionTitleValue(String optionTitleValue) {
        this.optionTitleValue = optionTitleValue;
    }
}
