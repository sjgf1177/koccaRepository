//**********************************************************
//  1. ��      ��: ������ Data
//  2. ���α׷��� : DicData.java
//  3. ��      ��: ������ data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 22
//  7. ��      ��:
//**********************************************************
package com.credu.course;
import com.credu.library.*;

/**
 * file name : FaqData.java
 * date      : 2003/5/28
 * programmer: ������ (puggjsj@hanmail.net)
 * function  : FAQ DATA
 */
public class DicData
{

    private String gubun;          // ���� (01:����, 02:��Ÿ)
    private String subj;           // �����ڵ�
    private String subjnm;         // ����Ϸù�ȣ
    private String words;          // ���
    private String descs;          // ��� ����
    private String groups;         // ��� �з� (��,��..A,B..)
    private String imgurl;         // ���� �̹��� URL
    private String luserid;        // ����������
    private String ldate;          // ����������

    private int seq;                // �ø����ȣ


    public DicData() {}

    public void   setGubun(String gubun)     { this.gubun = gubun;      }
    public String getGubun()                 { return gubun;            }
    public void   setSubj(String subj)       { this.subj = subj;        }
    public String getSubj()                  { return subj;             }
    public void   setWords(String words)     { this.words = words;      }
    public String getWords()                 { return words;            }
    public void   setSubjnm(String subjnm)   { this.subjnm = subjnm;    }
    public String getSubjnm()                { return subjnm;           }
    public void   setDescs(String descs)     { this.descs = descs;      }
    public String getDescs()                 { return descs;            }
    public void   setGroups(String groups)   { this.groups = groups;    }
    public String getGroups()                { return groups;           }
    public void   setImgurl(String imgurl)   { this.imgurl = imgurl;    }
    public String getImgurl()                { return imgurl;           }
    public void   setLuserid(String luserid) { this.luserid = luserid;  }
    public String getLuserid()               { return luserid;          }
    public void   setLdate(String ldate)     { this.ldate = ldate;      }
    public String getLdate()                 { return ldate;            }

    public void   setSeq(int seq)   { this.seq = seq;  }
    public int    getSeq()          { return seq;      }

}
