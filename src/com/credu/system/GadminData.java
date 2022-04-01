//**********************************************************
//  1. ��      ��: ���� Data
//  2. ���α׷��� : GadminData.java
//  3. ��      ��: ���� data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 22
//  7. ��      ��:
//**********************************************************

package com.credu.system;


//TZ_Gadmin
//gadmin,control,gadminnm,comments,isneedcomp

public class GadminData
{
	private String gadmin;                // ����ID
	private String padmin;				  // ��ӱ���ID
	private String control;               // ���۹���   R-READ, W:WRITE
	private String gadminnm;              // ���Ѹ�
	private String comments;              // ���Ѽ���
	private String isneedgrcode;          // �����׷��ڵ��ʿ俩��
	private String isneedsubj;            // �����ڵ��ʿ俩��
	private String isneedcomp;            // ȸ���ڵ��ʿ俩��
	private String isneeddept;            // �μ��ڵ��ʿ俩��
	private String isoutcomp;             // ���־�üȸ���ڵ��ʿ俩��

	private int dispnum;            // �ѰԽù���
	private int total_page_count;   // �Խù�����������

	public GadminData() {}

	public void   setGadmin(String gadmin)             { this.gadmin = gadmin;             }
	public String getGadmin()                          { return gadmin;                    }
	public void   setPadmin(String padmin)			   { this.padmin = padmin;			   }
	public String getPadmin()						   { return padmin;					   }
	public void   setControl(String control)           { this.control = control;           }
	public String getControl()                         { return control;                   }
	public void   setGadminnm(String gadminnm)         { this.gadminnm = gadminnm;         }
	public String getGadminnm()                        { return gadminnm;                  }
	public void   setComments(String comments)         { this.comments = comments;         }
	public String getComments()                        { return comments;                  }
	public void   setIsneedgrcode(String isneedgrcode) { this.isneedgrcode = isneedgrcode; }
	public String getIsneedgrcode()                    { return isneedgrcode;              }
	public void   setIsneedsubj(String isneedsubj)     { this.isneedsubj = isneedsubj;     }
	public String getIsneedsubj()                      { return isneedsubj;                }
	public void   setIsneedcomp(String isneedcomp)     { this.isneedcomp = isneedcomp;     }
	public String getIsneedcomp()                      { return isneedcomp;                }
	public void   setIsneeddept(String isneeddept)     { this.isneeddept = isneeddept;     }
	public String getIsneeddept()                      { return isneeddept;                }
	public void   setIsneedoutcomp(String isoutcomp)     { this.isoutcomp = isoutcomp;     }
	public String getIsneedoutcomp()                      { return isoutcomp;              }

	public void   setDispnum(int dispnum) { this.dispnum = dispnum; }
	public int    getDispnum()            { return dispnum;         }
	public void   setTotalPageCount(int total_page_count) { this.total_page_count = total_page_count; }
	public int    getTotalpagecount()                     { return total_page_count;                  }

}
