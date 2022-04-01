//**********************************************************
//  1. ��      ��: �޴� Data
//  2. ���α׷��� : MenuData.java
//  3. ��      ��: �޴� data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 22
//  7. ��      ��:
//**********************************************************
package com.credu.system;
import com.credu.library.*;

/**
 * file name : MenuData.java
 * date      : 2003/7/9
 * programmer: ������ (puggjsj@hanmail.net)
 * function  : Menu DATA
 */

//TZ_MENU
//grcode,menu,menunm,upper,parent,pgm,isDisplay,para1,para2,para3,para4,para5,para6,para7,para8,created,luserid,ldate,levels,orders

public class MenuData
{
    private String grcode;            // �����׷� ID
    private String menu;              // �޴� ID
    private String menunm;            // �޴���
    private String upper;             // �����ڵ� ID
    private String parent;            // �ֻ����ڵ� ID
    private String pgm;               // ���α׷� URL
    private String isdisplay;         // ������ȭ��ǥ�ÿ���
    private String para1;             // �Ķ����1
    private String para2;             // �Ķ����2
    private String para3;             // �Ķ����3
    private String para4;             // �Ķ����4
    private String para5;             // �Ķ����5
    private String para6;             // �Ķ����6
    private String para7;             // �Ķ����7
    private String para8;             // �Ķ����8
    private String para9;             // �Ķ����5
    private String para10;             // �Ķ����6
    private String para11;             // �Ķ����7
    private String para12;             // �Ķ����8
    private String created;           // ������
    private String luserid;           // ����������
    private String ldate;             // ����������
    private String systemgubun;		  // �ý��۱���

    private int levels;              // LEVELS
    private int orders;              // ����
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
