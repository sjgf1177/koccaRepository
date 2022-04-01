//**********************************************************
//  1. 제      목: 메뉴 관리(운영자별)
//  2. 프로그램명 : MenuAuthAdminData.java
//  3. 개      요: 메뉴 관리(운영자별)
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 15
//  7. 수      정:
//**********************************************************

package com.credu.system;
import com.credu.library.*;


//TZ_MENUAUTH
//grcode,gadmin,menu,control,luserid,ldate

public class MenuAuthAdminData
{
    private String grcode;            // 교육그룹 ID
    private String gadmin;            // 권한ID
		private String gadminnm;          // 권한ID    
    private String menu;              // 메뉴 ID
    private String menunm;            // 메뉴 명
    private String control;           // 조작범위   R-READ, W:WRITE
    private String luserid;           // 최종수정자
    private String ldate;             // 최종수정일
	private String comp;              // 회사
	private String isdeleted;         // 삭제여부
	private String fmon;              // 
	private String tmon;              // 
	private String commented;         // 비고
	private String cono;              // 사번
	private String name;              // 성명
	private String jikwi;             // 직위
	private String jikwinm;           // 직위명
	private String compnm;            // 회사명
	private String userid;            // ID
		
    private int levels;              // LEVELS
    private int seq;                 // seq


    public MenuAuthAdminData() {}

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
    public void   setComp(String comp)            { this.comp = comp;        }
    public String getComp()                       { return comp;             }
    public void   setIsdeleted(String isdeleted)  { this.isdeleted = isdeleted;}
    public String getIsdeleted()                  { return isdeleted;          }
    public void   setFmon(String fmon)            { this.fmon = fmon;        }
    public String getFmon()                       { return fmon;             }
    public void   setTmon(String tmon)            { this.tmon = tmon;        }
    public String getTmon()                       { return tmon;             }
    public void   setCommented(String commented)  { this.commented = commented;}
    public String getCommented()                  { return commented;          }
    public void   setCono(String cono)            { this.cono = cono;        }
    public String getCono()                       { return cono;             }
    public void   setName(String name)            { this.name = name;        }
    public String getName()                       { return name;             }
    public void   setJikwi(String jikwi)          { this.jikwi = jikwi;        }
    public String getJikwi()                      { return jikwi;              }
    public void   setJikwinm(String jikwinm)      { this.jikwinm = jikwinm;    }
    public String getJikwinm()                    { return jikwinm;            }
    public void   setCompnm(String compnm)        { this.compnm = compnm;      }
    public String getCompnm()                     { return compnm;             }
    public void   setUserid(String userid)        { this.userid = userid;      }
    public String getUserid()                     { return userid;             }

    public void   setLevels(int levels) { this.levels = levels;  }
    public int    getLevels()           { return levels;         }
    public void   setSeq(int seq)       { this.seq = seq;        }
    public int    getSeq()              { return seq;            }

}
