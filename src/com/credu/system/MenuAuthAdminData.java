//**********************************************************
//  1. ��      ��: �޴� ����(��ں�)
//  2. ���α׷��� : MenuAuthAdminData.java
//  3. ��      ��: �޴� ����(��ں�)
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2004. 11. 15
//  7. ��      ��:
//**********************************************************

package com.credu.system;
import com.credu.library.*;


//TZ_MENUAUTH
//grcode,gadmin,menu,control,luserid,ldate

public class MenuAuthAdminData
{
    private String grcode;            // �����׷� ID
    private String gadmin;            // ����ID
		private String gadminnm;          // ����ID    
    private String menu;              // �޴� ID
    private String menunm;            // �޴� ��
    private String control;           // ���۹���   R-READ, W:WRITE
    private String luserid;           // ����������
    private String ldate;             // ����������
	private String comp;              // ȸ��
	private String isdeleted;         // ��������
	private String fmon;              // 
	private String tmon;              // 
	private String commented;         // ���
	private String cono;              // ���
	private String name;              // ����
	private String jikwi;             // ����
	private String jikwinm;           // ������
	private String compnm;            // ȸ���
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
