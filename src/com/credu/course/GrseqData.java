//**********************************************************
//  1. ��      ��: ���������ڵ� DATA
//  2. ���α׷���: GrseqData.java
//  3. ��      ��:
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ���� 2004. 11. 14
//  7. ��      ��:
//**********************************************************
package com.credu.course;

public class GrseqData {
    private String   grcode ;
    private String   gyear  ;
    private String   grseq  ;
    private String   grseqnm;
    private String   props  ;
    private String   prope  ;
    private String   comp  ;
    private String   luserid ;
    private String   ldate  ;
    private String	homepageyn ;

    public GrseqData() {};



    /**
     * @return
     */
    public String getGrcode() {
        return grcode;
    }

    /**
     * @return
     */
    public String getGrseq() {
        return grseq;
    }

    /**
     * @return
     */
    public String getGrseqnm() {
        return grseqnm;
    }

    /**
     * @return
     */
    public String getGyear() {
        return gyear;
    }

    /**
     * @return
     */
    public String getLdate() {
        return ldate;
    }

    /**
     * @return
     */
    public String getLuserid() {
        return luserid;
    }

    /**
     * @return
     */
    public String getPrope() {
        return prope;
    }

    /**
     * @return
     */
    public String getProps() {
        return props;
    }

    /**
     * @return
     */
    public String getComp() {
        return comp;
    }

    /**
     * @param string
     */
    public void setGrcode(String string) {
        grcode = string;
    }

    /**
     * @param string
     */
    public void setGrseq(String string) {
        grseq = string;
    }

    /**
     * @param string
     */
    public void setGrseqnm(String string) {
        grseqnm = string;
    }

    /**
     * @param string
     */
    public void setGyear(String string) {
        gyear = string;
    }

    /**
     * @param string
     */
    public void setLdate(String string) {
        ldate = string;
    }

    /**
     * @param string
     */
    public void setLuserid(String string) {
        luserid = string;
    }

    /**
     * @param string
     */
    public void setPrope(String string) {
        prope = string;
    }

    /**
     * @param string
     */
    public void setProps(String string) {
        props = string;
    }

    /**
     * @param string
     */
    public void setComp(String string) {
        comp = string;
    }



	public String getHomepageyn() {
		return homepageyn;
	}



	public void setHomepageyn(String homepageyn) {
		this.homepageyn = homepageyn;
	}
    
    
}
