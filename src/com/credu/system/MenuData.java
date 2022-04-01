//**********************************************************
//  1. 제      목: 메뉴 Data
//  2. 프로그램명 : MenuData.java
//  3. 개      요: 메뉴 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 22
//  7. 수      정:
//**********************************************************
package com.credu.system;
import com.credu.library.*;

/**
 * file name : MenuData.java
 * date      : 2003/7/9
 * programmer: 정상진 (puggjsj@hanmail.net)
 * function  : Menu DATA
 */

//TZ_MENU
//grcode,menu,menunm,upper,parent,pgm,isDisplay,para1,para2,para3,para4,para5,para6,para7,para8,created,luserid,ldate,levels,orders

public class MenuData
{
    private String grcode;            // 교육그룹 ID
    private String menu;              // 메뉴 ID
    private String menunm;            // 메뉴명
    private String upper;             // 상위코드 ID
    private String parent;            // 최상위코드 ID
    private String pgm;               // 프로그램 URL
    private String isdisplay;         // 관리자화면표시여부
    private String para1;             // 파라미터1
    private String para2;             // 파라미터2
    private String para3;             // 파라미터3
    private String para4;             // 파라미터4
    private String para5;             // 파라미터5
    private String para6;             // 파라미터6
    private String para7;             // 파라미터7
    private String para8;             // 파라미터8
    private String para9;             // 파라미터5
    private String para10;             // 파라미터6
    private String para11;             // 파라미터7
    private String para12;             // 파라미터8
    private String created;           // 생성일
    private String luserid;           // 최종수정자
    private String ldate;             // 최종수정일
    private String systemgubun;		  // 시스템구분

    private int levels;              // LEVELS
    private int orders;              // 순서
    private int rowspannum=0;
    private int cnt;

    public MenuData() {}

    public void   setGrcode(String grcode)        { this.grcode = grcode;      }
    public String getGrcode()                     { return grcode;             }
    public void   setMenu(String menu)            { this.menu = menu;          }
    public String getMenu()                       { return menu;               }
    public void   setMenunm(String menunm)        { this.menunm = menunm;      }
    public String getMenunm()                     { return menunm;             }
    public void   setUpper(String upper)          { this.upper = upper;        }
    public String getUpper()                      { return upper;              }
    public void   setParent(String parent)        { this.parent = parent;      }
    public String getParent()                     { return parent;             }
    public void   setPgm(String pgm)              { this.pgm = pgm;            }
    public String getPgm()                        { return pgm;                }
    public void   setIsdisplay (String isdisplay) { this.isdisplay= isdisplay; }
    public String getIsdisplay()                  { return isdisplay;          }
    public void   setPara1(String para1)          { this.para1 = para1;        }
    public String getPara1()                      { return para1;              }
    public void   setPara2(String para2)          { this.para2 = para2;        }
    public String getPara2()                      { return para2;              }
    public void   setPara3(String para3)          { this.para3 = para3;        }
    public String getPara3()                      { return para3;              }
    public void   setPara4(String para4)          { this.para4 = para4;        }
    public String getPara4()                      { return para4 ;             }
    public void   setPara5(String para5)          { this.para5 = para5;        }
    public String getPara5()                      { return para5 ;             }
    public void   setPara6(String para6)          { this.para6 = para6;        }
    public String getPara6()                      { return para6 ;             }
    public void   setPara7(String para7)          { this.para7 = para7;        }
    public String getPara7()                      { return para7 ;             }
    public void   setPara8(String para8)          { this.para8 = para8;        }
    public String getPara8()                      { return para8 ;             }
    public void   setCreated(String created)      { this.created = created;    }
    public String getCreated()                    { return created;            }
    public void   setLuserid(String luserid)      { this.luserid= luserid;     }
    public String getLuserid()                    { return luserid;            }
    public void   setLdate(String ldate)          { this.ldate = ldate;        }
    public String getLdate()                      { return ldate;              }
    public void   setSystemgubun(String systemgubun) { this.systemgubun = systemgubun; }
    public String getSystemgubun()                   { return systemgubun;             }

    public void   setLevels(int levels) { this.levels = levels;  }
    public int    getLevels()           { return levels;         }
    public void   setOrders(int orders) { this.orders = orders;  }
    public int    getOrders()           { return orders;         }

    public void setRowspannum(int rowspannum) { this.rowspannum = rowspannum; }
    public int  getRowspannum()               { return rowspannum;            }
    public void setCnt(int cnt) { this.cnt = cnt; }
    public int  getCnt()        { return cnt;            }

	/**
	 * @return
	 */
	public String getPara10() {
		return para10;
	}

	/**
	 * @return
	 */
	public String getPara11() {
		return para11;
	}

	/**
	 * @return
	 */
	public String getPara12() {
		return para12;
	}

	/**
	 * @return
	 */
	public String getPara9() {
		return para9;
	}

	/**
	 * @param string
	 */
	public void setPara10(String string) {
		para10 = string;
	}

	/**
	 * @param string
	 */
	public void setPara11(String string) {
		para11 = string;
	}

	/**
	 * @param string
	 */
	public void setPara12(String string) {
		para12 = string;
	}

	/**
	 * @param string
	 */
	public void setPara9(String string) {
		para9 = string;
	}

}
