/*
 * Copyright (c) 2006 SK C&#038;C CO.,LTD.. All rights reserved.
 *
 * This software is the confidential and proprietary information of SK C&#038;C CO.,LTD..
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance wih the terms of the license agreement you entered into
 * with SK C&#038;C CO.,LTD..
 */

package com.credu.util;

//import java.io.PrintStream;

/**
<p>
<code>Base64</code> class는 base64 인코딩, 디코딩을 지원한다.
<p>
<pre>
    Base64는 65개의 US-ASCII character를 사용하는데 알파벳 대소문자 52개 숫자 10개 그리고, + / = 기호를 사용한다.

    Base64에서의 character는 6-bit를 사용하는데, 영문자 대문자 'A' 가 기본 값이 된다.
    알파벳 대문자 'A' 부터 0의 값을 갖고 소문자, 숫자 0 ~ 9, 그리고 + / = 의 기호차례로 정수 키값을 갖는다.

    예를 들어 영문자 소문자 'm' 은 2진수 100110 으로 표현이 되며 이값은 38이라는 Base64 값을 갖는데
    Base64 Encoding 은 이 얻어진 값 으로 위에서 나열한 65가지의 문자 값의 대조를 이루는 문자들의
    집합이라고 할 수 있다. 하지만, 단순히 한문자씩 대칭이 되는것은 아니다. 8비트씩으로 구성된 문자
    3개를 4개의 6비트 문자로 변환하는 것이다.

    "kim" 이라는 문자열을 Base64 encoding 하는 예를 보겠다.

    US-ASCII 값으로 보자면
       "k" 는  107
       "i" 는 105
       "m" 은 109
     의 값을 각각 갖는다.
     ex) System.out.println((int)'k'); 해보면 쉽게 알수 있을것이다.

    위의 int 값을 각각 2진수로 변환하면
        k   01101011
        i    01101001
        m  01101101
     의 값을 갖는다.
     ex) System.out.println(Integer.toBinaryString((int)('k'))); 해보면 쉽게 알수 있을것이다.

    8비트씩 구성된 3개의 문자를 순서대로 조립하면 24비트의 다음과 같은 배열을 얻을 수 있는데.

                   011010110110100101101101

    이 24비트를 4개의 6비트씩으로 나누어보자

                   011010 110110 100101 101101

    이제 4개의 2진수를 10진으로 바꾸면,
                     26     54     37     45
    이 될것이고, 여기에 대칭이 되는 Base64 문자는 다음과 같다.
                     a      2       l     t

    이렇게 encoding 작업은 3개의 문자를 4개의 문자로 생성하는 데, 특정 문자열이
    반드시 3개로 끝나라는 보장은 없다. 1개 또는 2개 길이의 문자열도 얼마든지 가능하다.
    이렇게 부족한 문자열에 대해서 '=' 기호를 대체하여 사용한다.

    예를 들어 "ki" 를 encoding 한다고 가정하자

    	01101011 01101001 : 16개의 2진수 조합
    	0110101101101001

    	011010 110110 1001 : 6비트씩으로 나누면 3개의 단위 밖에 안나온다.
    	그러면, 우선 세번째 단위를 오른쪽에서 부터 6비트까지 채워넣는다.
    	그리고, 111111 을 추가하자 : 여기서 111111 은 Base64키값의 가장 큰값
    	즉 '=' 기호에 대한 값이다.
        이제 011010 110110 100100 111111 의 값을 얻었을 것이다.
        이 각각의 값을 보면

        26   54   36   64

        a      2      k     =
        와 같을 것이다.

        "k" 를 encoding 할때는 어떤가

        01101011
        011010 11 : 여기서 두번재 단위에 6비트를 마저 채워준후 '=' 에 해당하는
        111111 를 두번 반복해준다.

        011010 110000 111111 111111
        이 각각의 값을 보면

        26   48   64   64

        a      w      =     =
        와 같을 것이다.
</pre>
 <p>
 <strong>Code Sample:</strong>
 <blockquote>
 <pre>
   String s1 = Base64.encodeToString("Hi  대한민국! 짝짝짝 짝짝!");
   String s2 = Base64.decodeToString(s1);
   System.out.println(s1);
   System.out.println(s2);
 </pre>
 </blockquote>
 의 결과는<br>
 <blockquote>
 <pre>
 SGkgILTrx9G5zrG5ISDCpsKmwqYgwqbCpiE= 
 Hi 대한민국! 짝짝짝 짝짝! 
 </pre>
 </blockquote>
*/

public class Base64
{

    private Base64()
    {
        //Base64 기본생성자
    }

    /**
     * String s를 Base64로 decoding 시킴.
     * @param s decoding 시킬 String
     * @return Base64 decoding된 byte 배열
 	 */
    public static final byte[] decodeToByteArray(String s)
    {
        if(s == null)
        {
            return null;
        }
        
        byte abyte0[] = s.getBytes();
        return decodeToByteArray(abyte0);
    }

    /**
     * byte 배열 abyte0를 Base64로 decoding 시킴.
     * @param s decoding 시킬 byte 배열
     * @return Base64 decoding된 byte 배열
 	 */
    public static final byte[] decodeToByteArray(byte abyte0[])
    {
        if(abyte0 == null)
            return null;
        int i;
        for(i = abyte0.length; abyte0[i - 1] == 61; i--);
        byte abyte1[] = new byte[i - abyte0.length / 4];
        for(int j = 0; j < abyte0.length; j++)
            abyte0[j] = sBase64DecMap[abyte0[j]];

        int k = 0;
        int l;
        for(l = 0; l < abyte1.length - 2; l += 3)
        {
            abyte1[l] = (byte)(abyte0[k] << 2 & 0xff | abyte0[k + 1] >>> 4 & 0x3);
            abyte1[l + 1] = (byte)(abyte0[k + 1] << 4 & 0xff | abyte0[k + 2] >>> 2 & 0xf);
            abyte1[l + 2] = (byte)(abyte0[k + 2] << 6 & 0xff | abyte0[k + 3] & 0x3f);
            k += 4;
        }

        if(l < abyte1.length)
            abyte1[l] = (byte)(abyte0[k] << 2 & 0xff | abyte0[k + 1] >>> 4 & 0x3);
        if(++l < abyte1.length)
            abyte1[l] = (byte)(abyte0[k + 1] << 4 & 0xff | abyte0[k + 2] >>> 2 & 0xf);
        return abyte1;
    }

    /**
     * String s를 decoding 시킴.
     * @param s decoding 시킬 String
     * @return Base64 decoding된 String
 	 */
    public static final String decodeToString(String s)
    {
        if(s == null)
            return null;

        return new String(decodeToByteArray(s));
    }

    /**
     * byte 배열 abyte0를 decoding 시킴.
     * @param s decoding 시킬 byte 배열
     * @return Base64 decode된 String
 	 */
    public static final String decodeToString(byte abyte0[])
    {
        return new String(decodeToByteArray(abyte0));
    }

    /**
     * String s를 Base64로 encoding 시킴.
     * @param s encoding 시킬 String
     * @return Base64 encoding된 byte 배열
 	 */
    public static final byte[] encodeToByteArray(String s)
    {
        if(s == null)
        {
            return null;
        }

        byte abyte0[] = s.getBytes();
        return encodeToByteArray(abyte0);
    }

    /**
     * byte 배열 abyte0를 Base64로 encoding 시킴.
     * @param s encoding 시킬 byte 배열
     * @return Base64 encoding된 byte 배열
 	 */
    public static final byte[] encodeToByteArray(byte abyte0[])
    {
        if(abyte0 == null)
            return null;
        byte abyte1[] = new byte[((abyte0.length + 2) / 3) * 4];
        int i = 0;
        int j = 0;
        for(; i < abyte0.length - 2; i += 3)
        {
            abyte1[j++] = sBase64EncMap[abyte0[i] >>> 2 & 0x3f];
            abyte1[j++] = sBase64EncMap[abyte0[i + 1] >>> 4 & 0xf | abyte0[i] << 4 & 0x3f];
            abyte1[j++] = sBase64EncMap[abyte0[i + 2] >>> 6 & 0x3 | abyte0[i + 1] << 2 & 0x3f];
            abyte1[j++] = sBase64EncMap[abyte0[i + 2] & 0x3f];
        }

        if(i < abyte0.length)
        {
            abyte1[j++] = sBase64EncMap[abyte0[i] >>> 2 & 0x3f];
            if(i < abyte0.length - 1)
            {
                abyte1[j++] = sBase64EncMap[abyte0[i + 1] >>> 4 & 0xf | abyte0[i] << 4 & 0x3f];
                abyte1[j++] = sBase64EncMap[abyte0[i + 1] << 2 & 0x3f];
            } else
            {
                abyte1[j++] = sBase64EncMap[abyte0[i] << 4 & 0x3f];
            }
        }
        for(; j < abyte1.length; j++)
            abyte1[j] = 61;

        return abyte1;
    }

    /**
     * String s를 encoding 시킴.
     * @param s encoding 시킬 String
     * @return Base64 encoding된 String
 	 */
    public static final String encodeToString(String s)
    {
        if(s == null)
            return null;

        return new String(encodeToByteArray(s));
    }

    /**
     * byte 배열 abyte0를 encoding 시킴.
     * @param s encoding 시킬 byte 배열
     * @return Base64 decode된 String
 	 */
    public static final String encodeToString(byte abyte0[])
    {
        if(abyte0 == null)
            return null;

        return new String(encodeToByteArray(abyte0));
    }

    private static byte sBase64EncMap[];
    private static byte sBase64DecMap[];

    static 
    {
        sBase64EncMap = null;
        sBase64DecMap = null;
        byte abyte0[] = {
            65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 
            75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 
            85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 
            101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 
            111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 
            121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 
            56, 57, 43, 47
        };
        sBase64EncMap = abyte0;
        sBase64DecMap = new byte[128];
        for(int i = 0; i < sBase64EncMap.length; i++)
            sBase64DecMap[sBase64EncMap[i]] = (byte)i;

    }
}
