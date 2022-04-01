// **********************************************************
// 1. �� ��:
// 2. ���α׷���: TextComBean.java
// 3. �� ��:
// 4. ȯ ��: JDK 1.3
// 5. �� ��: 0.1
// 6. �� ��: ��â�� 2005. 07. 16
// 7. �� ��:
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
                v_textstr = "����";
            } else if (p_statusval.equals("N")) {
                v_textstr = "�ݷ�";
            } else if (p_statusval.equals("B")) {

                if (p_Bgubun.equals("1")) {
                    v_textstr = "��ó��";
                } else if (p_Bgubun.equals("2")) {
                    v_textstr = "�����";
                } else if (p_Bgubun.equals("3")) {
                    v_textstr = "�̰�";
                } else if (p_Bgubun.equals("4")) {
                    v_textstr = "��û��";
                } else {
                    v_textstr = p_Bgubun;
                }
            } else if (p_statusval.equals("M")) {
                v_textstr = "-"; // ���δ��/Ȥ�� ���δ��
            } else if (p_statusval.equals("")) {
                v_textstr = "-";
            }
        } else if (p_txtgubun.equals("cpsubj")) {

            if (p_statusval.equals("Y")) {
                v_textstr = "����";
            } else if (p_statusval.equals("N")) {
                v_textstr = "�ݷ�";
            } else if (p_statusval.equals("B")) {
                v_textstr = "�����";
            } else if (p_statusval.equals("C")) {
                v_textstr = "�������";
            } else if (p_statusval.equals("")) {
                v_textstr = "���Է�";
            }
        }
        return v_textstr;
    }

    public static String getProstatusTxt(String prostatusvalue) throws Exception {
        // ListSet ls = null;
        String v_textstr = "";
        // String sql = "";

        if (prostatusvalue.equals("00")) {
            v_textstr = "���Ѹ���";
        } else if (prostatusvalue.equals("01")) {
            v_textstr = "�̽�û";
        } else if (prostatusvalue.equals("02")) {
            v_textstr = "��û��";
        } else if (prostatusvalue.equals("03")) {
            v_textstr = "����";
        } else if (prostatusvalue.equals("04")) {
            v_textstr = "�ݷ�";
        } else if (prostatusvalue.equals("05")) {
            v_textstr = "������";
        } else if (prostatusvalue.equals("06")) {
            v_textstr = "�����Ϸ�";
        } else if (prostatusvalue.equals("07")) {
            v_textstr = "�̼���";
        } else if (prostatusvalue.equals("08")) {
            v_textstr = "����";
        } else if (prostatusvalue.equals("99")) {
            v_textstr = "������û�Ⱓ"; // ������û�Ⱓ�� �̽�û����
        } else if (prostatusvalue.equals("")) {
            v_textstr = "-";
        }

        return v_textstr;
    }

    public static String getIsOnOffTxt(String isonoffvalue) throws Exception {

        String v_isonofftxt = "";
        if (isonoffvalue.equals("ON"))
            v_isonofftxt = "�¶���";
        else if (isonoffvalue.equals("OFF"))
            v_isonofftxt = "��������";
        else
            v_isonofftxt = "-";

        return v_isonofftxt;
    }

    public static String getEdutermTxt(String edutermvalue) throws Exception {
        // ListSet ls = null;
        String v_textstr = "";
        // String sql = "";

        if (edutermvalue.equals("0")) {
            v_textstr = "��������";
        } else if (edutermvalue.equals("1")) {
            v_textstr = "��������";
        } else if (edutermvalue.equals("2")) {
            v_textstr = "��������";
        } else if (edutermvalue.equals("3")) {
            v_textstr = "��������";
        } else if (edutermvalue.equals("4")) {
            v_textstr = "������";
        } else if (edutermvalue.equals("5")) {
            v_textstr = "��������";
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
            v_textstr = "td_applyok"; // ������û�Ⱓ�� �̽�û����
        } else if (prostatusvalue.equals("")) {
            v_textstr = "td_applyok";
        }

        return v_textstr;
    }

    /**
     * ���� �޼���
     * 
     * @param String ������
     * @return String Return ���� �޼���
     */
    public String isGetErrtxt(String errvalue) throws Exception {

        String errtxt = "";

        if (errvalue.equals("0")) {
            errtxt = "�����Է�.";
        }

        else if (errvalue.equals("1")) {
            errtxt = "�λ�DB�� �������� �ʽ��ϴ�.";
        } else if (errvalue.equals("2")) {
            errtxt = "�������Դϴ�."; //
        } else if (errvalue.equals("3")) {
            errtxt = "�������Դϴ�."; //
        } else if (errvalue.equals("4")) {
            errtxt = "�������Դϴ�.";
        } else if (errvalue.equals("5")) {
            errtxt = "�̰������� �Դϴ�.";
        } else if (errvalue.equals("6")) {
            errtxt = "�ߺ��Ǵ� ���հ����� �ֽ��ϴ�..";
        } else if (errvalue.equals("7")) {
            errtxt = "��û������ �̹� �����մϴ�.";
        } else if (errvalue.equals("8")) {
            errtxt = "�н��ڰ� �ƴմϴ�";
        } else if (errvalue.equals("9")) {
            errtxt = "�̹� ����ó�� �Ǿ����ϴ�.";
        } else if (errvalue.equals("9")) {
            errtxt = "���������� �������� �ʽ��ϴ�.";
        } else if (errvalue.equals("10")) {
            errtxt = "�̹� �������� ����Ʈ�� ��� �Ǿ��ֽ��ϴ�.";
        } else if (errvalue.equals("12")) {
            errtxt = "�����ڵ尡 �������� �ʽ��ϴ�.";
        } else if (errvalue.equals("13")) {
            errtxt = "���������Ϳ� �̹� �����մϴ�.";
        } else if (errvalue.equals("14")) {
            errtxt = "�н��������� �������� �ʽ��ϴ�.";
        } else if (errvalue.equals("22")) {
            errtxt = "����ó���� �Ϸ�� �����Դϴ�.";
        } else if (errvalue.equals("23")) {
            errtxt = "�̹� ����� �Ϸ�� �����Դϴ�.";
        } else if (errvalue.equals("24")) {
            errtxt = "���Ῡ�� �����Դϴ�.";
        } else if (errvalue.equals("25")) {
            errtxt = "������� �����Դϴ�.";
        } else if (errvalue.equals("31")) {
            errtxt = "���������� ��ġ���� �ʽ��ϴ�.";
        } else if (errvalue.equals("32")) {
            errtxt = "�̹� ����ó���� �Ϸ�Ǿ����ϴ�.";
        } else if (errvalue.equals("33")) {
            errtxt = "�̹� �������� �Ϸ�Ǿ����ϴ�.";
        } else if (errvalue.equals("34")) {
            errtxt = "���� �����Ⱓ�� �ƴմϴ�.";
        } else if (errvalue.equals("35")) {
            errtxt = "������ ����������� �ʾҽ��ϴ�.";
        } else if (errvalue.equals("36")) {
            errtxt = "�̹� ��������ڷ� �����Ǿ� �ֽ��ϴ�.";
        } else if (errvalue.equals("999")) {
            errtxt = "DBó�� �� ������ �߻��Ͽ����ϴ�.";
        } else {
            errtxt = "DBó�� �� ������ �߻��Ͽ����ϴ�.";
        }

        return errtxt;
    }

    /**
     * ���� ���� �޼���
     * 
     * @param String ������
     * @return String Return ���� �޼���
     */
    public String isSulmunGetErrtxt(String errvalue) throws Exception {

        String errtxt = "";

        if (errvalue.equals("S1")) {
            errtxt = "�������� ���� �ϴ� �����Դϴ�.";
        } else if (errvalue.equals("S2")) {
            errtxt = "�����ڰ� �ִ� �����Դϴ�.";
        } else if (errvalue.equals("S3")) {
            errtxt = "�����ڰ� �ִ� �������Դϴ�.";
        }
        return errtxt;
    }

    /**
     * ���� ���� �޼���
     * 
     * @param String ������
     * @return String Return ���� �޼���
     */
    public String getScriptMsg(String value) throws Exception {

        String Message = "";

        if (value.equals("delete.fail")) {
            Message = "�����Ҽ� �����ϴ�.";
        } else if (value.equals("update.fail")) {
            Message = "�����Ҽ� �����ϴ�.";
        } else if (value.equals("save.fail")) {
            Message = "����Ҽ� �����ϴ�.";
        } else if (value.equals("move.fail")) {
            Message = "�̵��Ҽ� �����ϴ�.";
        } else if (value.equals("chooseQuestion.fail")) {
            Message = "������ �߰��Ҽ� �����ϴ�.";
        } else if (value.equals("deleteQuestion.fail")) {
            Message = "������ �����Ҽ� �����ϴ�.";
        }

        return Message;
    }

}
