//**********************************************************
//  1. 제      목: 용어사전 Data
//  2. 프로그램명 : DicData.java
//  3. 개      요: 용어사전 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 22
//  7. 수      정:
//**********************************************************
package com.credu.course;
import com.credu.library.*;

/**
 * file name : FaqData.java
 * date      : 2003/5/28
 * programmer: 정상진 (puggjsj@hanmail.net)
 * function  : FAQ DATA
 */
public class DicData
{

    private String gubun;          // 구분 (01:과정, 02:기타)
    private String subj;           // 과정코드
    private String subjnm;         // 용어일련번호
    private String words;          // 용어
    private String descs;          // 용어 설명
    private String groups;         // 용어 분류 (가,나..A,B..)
    private String imgurl;         // 참고 이미지 URL
    private String luserid;        // 최종수정자
    private String ldate;          // 최종수정일

    private int seq;                // 시리얼번호


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
