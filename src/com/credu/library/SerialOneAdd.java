package com.credu.library;


 /**
 * <p>����: ȸ���ڵ� �߰� ���� ���̺귯��</p>
 * <p>����: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author
 *@date 
 *@version 
 */


public class SerialOneAdd {
    private static String [] alphabet 
    = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
    
    public static String oneAddKey(String code) {
        char c1 = '\u0000';
        char c2 = '\u0000';
        String str1 = "";
        String str2 = "";
        int num1 = 0;
        int num2 = 0;
        String result = "";
        
        if(code != null) {
            c1 = code.charAt(0);
            c2 = code.charAt(1);
        }      
        
        num1 = Character.getNumericValue(c1);
        num2 = Character.getNumericValue(c2);
        
        str1 = String.valueOf(c1);
        str2 = String.valueOf(c2);
        
        if(num1 >= 0 && num1 <= 9) {     //      ù��° char �� ������ ���  
            if(str2.equals("9")) {      //      9�� ��� �ø��Ѵ�.
                str2 = "A";    
            }            
            else if(str2.equals("Z")) {
                str2 = "0";               
                if(str1.equals("9")) {
                    result = "A0";
                    return result;
                }
                str1 = (num1 + 1) + "";
            }
            else if(num2 >= 0 && num2 < 9) {
                str2 = (num2 + 1) + "";
            }
            else if(num2 >= 10 && num2 < 35) {
                str2 = alphabetOneAdd(str2);
            }
            result = str1 + str2;
        }
        else if(num1 >= 10 && num1 <= 35) {     //      ù��° char �� ���ĺ��� ���  
            if(str2.equals("9")) {      //      9�� ��� �ø��Ѵ�.
                str2 = "A";    
            }            
            else if(str2.equals("Z")) {
                str2 = "0";
                if(str1.equals("Z")) {
                    result = "ZZ";
                    return result;
                }
                str1 = alphabetOneAdd(str1);
            }
            else if(num2 >= 0 && num2 < 9) {
                str2 = (num2 + 1) + "";
            }
            else if(num2 >= 10 && num2 < 35) {
                str2 = alphabetOneAdd(str2);
            }
            result = str1 + str2;
        }
        return result;
    }

    public static String alphabetOneAdd(String alpha) {
        String result = "";
        for(int i = 0; i < alphabet.length-1; i++) {
            if(alphabet [i].equals(alpha)) {
                result = alphabet [i+1];
            }
        }
        return result;
    }
    
}