// **********************************************************
// 1. 제 목:
// 2. 프로그램명: TextComBean.java
// 3. 개 요:
// 4. 환 경: JDK 1.3
// 5. 버 젼: 0.1
// 6. 작 성: 이창훈 2005. 07. 16
// 7. 수 정:
// **********************************************************
package com.credu.common;


public class TextComBean {

    public TextComBean() {
    }

    public static String getApprovalTxt(String p_statusval) throws Exception {
        return getApprovalTxt(p_statusval, "", "");
    }

    public static String getApprovalTxt(String p_statusval, String p_txtgubun) throws Exception {
        return getApprovalTxt(p_statusval, p_txtgubun, "");
    }

    public static String getApprovalTxt(String p_statusval, String p_txtgubun, String p_Bgubun) throws Exception {
        String v_textstr = "";
        // String v_fcolor = "";

        if (p_txtgubun.equals("")) {
            if (p_statusval.equals("L")) {
                v_textstr = "-";
            } else if (p_statusval.equals("Y")) {
                v_textstr = "승인";
            } else if (p_statusval.equals("N")) {
                v_textstr = "반려";
            } else if (p_statusval.equals("B")) {

                if (p_Bgubun.equals("1")) {
                    v_textstr = "미처리";
                } else if (p_Bgubun.equals("2")) {
                    v_textstr = "상신중";
                } else if (p_Bgubun.equals("3")) {
                    v_textstr = "미결";
                } else if (p_Bgubun.equals("4")) {
                    v_textstr = "신청중";
                } else {
                    v_textstr = p_Bgubun;
                }
            } else if (p_statusval.equals("M")) {
                v_textstr = "-"; // 승인대기/혹은 승인대기
            } else if (p_statusval.equals("")) {
                v_textstr = "-";
            }
        } else if (p_txtgubun.equals("cpsubj")) {

            if (p_statusval.equals("Y")) {
                v_textstr = "승인";
            } else if (p_statusval.equals("N")) {
                v_textstr = "반려";
            } else if (p_statusval.equals("B")) {
                v_textstr = "상신중";
            } else if (p_statusval.equals("C")) {
                v_textstr = "승인취소";
            } else if (p_statusval.equals("")) {
                v_textstr = "미입력";
            }
        }
        return v_textstr;
    }

    public static String getProstatusTxt(String prostatusvalue) throws Exception {
        // ListSet ls = null;
        String v_textstr = "";
        // String sql = "";

        if (prostatusvalue.equals("00")) {
            v_textstr = "기한만료";
        } else if (prostatusvalue.equals("01")) {
            v_textstr = "미신청";
        } else if (prostatusvalue.equals("02")) {
            v_textstr = "신청중";
        } else if (prostatusvalue.equals("03")) {
            v_textstr = "승인";
        } else if (prostatusvalue.equals("04")) {
            v_textstr = "반려";
        } else if (prostatusvalue.equals("05")) {
            v_textstr = "교육중";
        } else if (prostatusvalue.equals("06")) {
            v_textstr = "교육완료";
        } else if (prostatusvalue.equals("07")) {
            v_textstr = "미수료";
        } else if (prostatusvalue.equals("08")) {
            v_textstr = "수료";
        } else if (prostatusvalue.equals("99")) {
            v_textstr = "수강신청기간"; // 수강신청기간중 미신청상태
        } else if (prostatusvalue.equals("")) {
            v_textstr = "-";
        }

        return v_textstr;
    }

    public static String getIsOnOffTxt(String isonoffvalue) throws Exception {

        String v_isonofftxt = "";
        if (isonoffvalue.equals("ON"))
            v_isonofftxt = "온라인";
        else if (isonoffvalue.equals("OFF"))
            v_isonofftxt = "오프라인";
        else
            v_isonofftxt = "-";

        return v_isonofftxt;
    }

    public static String getEdutermTxt(String edutermvalue) throws Exception {
        // ListSet ls = null;
        String v_textstr = "";
        // String sql = "";

        if (edutermvalue.equals("0")) {
            v_textstr = "교육예정";
        } else if (edutermvalue.equals("1")) {
            v_textstr = "교육예정";
        } else if (edutermvalue.equals("2")) {
            v_textstr = "교육예정";
        } else if (edutermvalue.equals("3")) {
            v_textstr = "교육예정";
        } else if (edutermvalue.equals("4")) {
            v_textstr = "교육중";
        } else if (edutermvalue.equals("5")) {
            v_textstr = "교육종료";
        }
        return v_textstr;
    }

    public static String getProstatusClass(String prostatusvalue) throws Exception {
        // ListSet ls = null;
        String v_textstr = "";
        // String sql = "";

        if (prostatusvalue.equals("00")) {
            v_textstr = "td_applyok";
        } else if (prostatusvalue.equals("01")) {
            v_textstr = "td_applyok";
        } else if (prostatusvalue.equals("02")) {
            v_textstr = "td_applyok";
        } else if (prostatusvalue.equals("03")) {
            v_textstr = "td_applyok";
        } else if (prostatusvalue.equals("04")) {
            v_textstr = "td_applyok";
        } else if (prostatusvalue.equals("05")) {
            v_textstr = "td_eduing";
        } else if (prostatusvalue.equals("06")) {
            v_textstr = "td_eduing";
        } else if (prostatusvalue.equals("07")) {
            v_textstr = "td_edufinish";
        } else if (prostatusvalue.equals("08")) {
            v_textstr = "td_edufinish";
        } else if (prostatusvalue.equals("99")) {
            v_textstr = "td_applyok"; // 수강신청기간중 미신청상태
        } else if (prostatusvalue.equals("")) {
            v_textstr = "td_applyok";
        }

        return v_textstr;
    }

    /**
     * 에러 메세지
     * 
     * @param String 에러값
     * @return String Return 에러 메세지
     */
    public String isGetErrtxt(String errvalue) throws Exception {

        String errtxt = "";

        if (errvalue.equals("0")) {
            errtxt = "정상입력.";
        }

        else if (errvalue.equals("1")) {
            errtxt = "인사DB에 존재하지 않습니다.";
        } else if (errvalue.equals("2")) {
            errtxt = "퇴직자입니다."; //
        } else if (errvalue.equals("3")) {
            errtxt = "휴직자입니다."; //
        } else if (errvalue.equals("4")) {
            errtxt = "정직자입니다.";
        } else if (errvalue.equals("5")) {
            errtxt = "미개설차수 입니다.";
        } else if (errvalue.equals("6")) {
            errtxt = "중복되는 집합과정이 있습니다..";
        } else if (errvalue.equals("7")) {
            errtxt = "신청정보에 이미 존재합니다.";
        } else if (errvalue.equals("8")) {
            errtxt = "학습자가 아닙니다";
        } else if (errvalue.equals("9")) {
            errtxt = "이미 수료처리 되었습니다.";
        } else if (errvalue.equals("9")) {
            errtxt = "강사정보가 존재하지 않습니다.";
        } else if (errvalue.equals("10")) {
            errtxt = "이미 수강제약 리스트에 등록 되어있습니다.";
        } else if (errvalue.equals("12")) {
            errtxt = "과정코드가 존재하지 않습니다.";
        } else if (errvalue.equals("13")) {
            errtxt = "엑셀데이터에 이미 존재합니다.";
        } else if (errvalue.equals("14")) {
            errtxt = "학습자정보에 존재하지 않습니다.";
        } else if (errvalue.equals("22")) {
            errtxt = "수료처리가 완료된 과정입니다.";
        } else if (errvalue.equals("23")) {
            errtxt = "이미 등록이 완료된 과정입니다.";
        } else if (errvalue.equals("24")) {
            errtxt = "수료여부 오류입니다.";
        } else if (errvalue.equals("25")) {
            errtxt = "총점계산 오류입니다.";
        } else if (errvalue.equals("31")) {
            errtxt = "수강생수가 일치하지 않습니다.";
        } else if (errvalue.equals("32")) {
            errtxt = "이미 수료처리가 완료되었습니다.";
        } else if (errvalue.equals("33")) {
            errtxt = "이미 결과등록이 완료되었습니다.";
        } else if (errvalue.equals("34")) {
            errtxt = "현재 교육기간이 아닙니다.";
        } else if (errvalue.equals("35")) {
            errtxt = "교육이 아직종료되지 않았습니다.";
        } else if (errvalue.equals("36")) {
            errtxt = "이미 교육대상자로 선정되어 있습니다.";
        } else if (errvalue.equals("999")) {
            errtxt = "DB처리 중 에러가 발생하였습니다.";
        } else {
            errtxt = "DB처리 중 에러가 발생하였습니다.";
        }

        return errtxt;
    }

    /**
     * 설문 에러 메세지
     * 
     * @param String 에러값
     * @return String Return 에러 메세지
     */
    public String isSulmunGetErrtxt(String errvalue) throws Exception {

        String errtxt = "";

        if (errvalue.equals("S1")) {
            errtxt = "설문지에 존재 하는 문제입니다.";
        } else if (errvalue.equals("S2")) {
            errtxt = "응시자가 있는 문제입니다.";
        } else if (errvalue.equals("S3")) {
            errtxt = "응시자가 있는 설문지입니다.";
        }
        return errtxt;
    }

    /**
     * 설문 에러 메세지
     * 
     * @param String 에러값
     * @return String Return 에러 메세지
     */
    public String getScriptMsg(String value) throws Exception {

        String Message = "";

        if (value.equals("delete.fail")) {
            Message = "삭제할수 없습니다.";
        } else if (value.equals("update.fail")) {
            Message = "수정할수 없습니다.";
        } else if (value.equals("save.fail")) {
            Message = "등록할수 없습니다.";
        } else if (value.equals("move.fail")) {
            Message = "이동할수 없습니다.";
        } else if (value.equals("chooseQuestion.fail")) {
            Message = "문제를 추가할수 없습니다.";
        } else if (value.equals("deleteQuestion.fail")) {
            Message = "문항을 삭제할수 없습니다.";
        }

        return Message;
    }

}
