//**********************************************************
//  1. 제      목: 메뉴 관리
//  2. 프로그램명 : MenuAuthData.java
//  3. 개      요: 메뉴 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 22
//  7. 수      정:
//**********************************************************

package com.credu.system;
import com.credu.library.*;


//TZ_MENUAUTH
//grcode,gadmin,menu,control,luserid,ldate

public class MenuAuthData
{
    private String grcode;            // 교육그룹 ID
    private String gadmin;            // 권한ID
	private String gadminnm;          // 권한ID    
    private String menu;              // 메뉴 ID
    private String menunm;            // 메뉴 명
    private String control;           // 조작범위   R-READ, W:WRITE
    private String luserid;           // 최종수정자
    private String ldate;             // 최종수정일
    private String systemgubun;		  // 시스템구분

    private int levels;              // LEVELS
    private int seq;                 // seq


    public MenuAuthData() {}

    public void   setGrcode(String grcode)        { this.grcode = grcode;      }
    public String getGrcode()                     { return grcode;             }
    public void   setGadmin(String gadmin)        { this.gadmin = gadmin;      }
    public String getGadmin()                     { return gadmin;             }
	public void   setGadminnm(String gadminnm)    { this.gadminnm = gadminnm;  }
	public String getGadminnm()                   { return gadminnm;           }
    public void   setMenu(String menu)            { this.menu = menu;          }
    public String getMenu()                       { return menu;               }
    public void   setMenunm(String menunm)        { this.menunm = menunm;      }
    public String getMenunm()                     { return menunm;             }
    public void   setControl(String control)      { this.control = control;    }
    public String getControl()                    { return control;            }
    public void   setLuserid(String luserid)      { this.luserid= luserid;     }
    public String getLuserid()                    { return luserid;            }
    public void   setLdate(String ldate)          { this.ldate = ldate;        }
    public String getLdate()                      { return ldate;              }
    public void   setSystemgubun(String systemgubun)    { this.systemgubun = systemgubun;  }
    public String getSystemgubun()                      { return systemgubun;              }

    public void   setLevels(int levels) { this.levels = levels;  }
    public int    getLevels()           { return levels;         }
    public void   setSeq(int seq)       { this.seq = seq;        }
    public int    getSeq()              { return seq;            }

}
