// **********************************************************
// 1. 제 목: Sample 자료실
// 2. 프로그램명: BulletinBeanjava
// 3. 개 요: Sample 자료실
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 이정한 2003. 4. 26
// 7. 수 정: 이정한 2003. 4. 26
// **********************************************************

package com.credu.common;

import com.credu.library.ConfigSet;
import com.credu.library.RequestBox;

/**
 * 자료실(HomePage) 관련 Sample Class
 * 
 * @date : 2003. 5
 * @author : j.h. lee
 */
public class SubjClassBean {

    private ConfigSet config;
    private int classdepth;

    public SubjClassBean() {
        try {
            config = new ConfigSet();
            classdepth = config.getInt("subj.class.depth");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSubjClass(RequestBox box) throws Exception {
        String ss_subjclass = "";
        try {
            if (classdepth > 1) { // MiddleClass
                ss_subjclass = box.getString("s_upperclass") + box.getString("s_middleclass") + "000";
            } else if (classdepth > 2) { // LowerClass
                ss_subjclass = box.getString("s_upperclass") + box.getString("s_middleclass") + box.getString("s_lowerclass");
            } else { // UpperClass
                ss_subjclass = box.getString("s_upperclass") + "000000";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("SubjClassBean.getSubjClass)\r\n\"" + ex.getMessage());
        }
        return ss_subjclass;
    }
}
