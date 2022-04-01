//**********************************************************
//  1. ��      ��: �޴� ����
//  2. ���α׷��� : MenuAuthData.java
//  3. ��      ��: �޴� ����
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 22
//  7. ��      ��:
//**********************************************************

package com.credu.system;
import com.credu.library.*;


//TZ_MENUAUTH
//grcode,gadmin,menu,control,luserid,ldate

public class MenuAuthData
{
    private String grcode;            // �����׷� ID
    private String gadmin;            // ����ID
	private String gadminnm;          // ����ID    
    private String menu;              // �޴� ID
    private String menunm;            // �޴� ��
    private String control;           // ���۹���   R-READ, W:WRITE
    private String luserid;           // ����������
    private String ldate;             // ����������
    private String systemgubun;		  // �ý��۱���

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
