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
<code>Base64</code> class�� base64 ���ڵ�, ���ڵ��� �����Ѵ�.
<p>
<pre>
    Base64�� 65���� US-ASCII character�� ����ϴµ� ���ĺ� ��ҹ��� 52�� ���� 10�� �׸���, + / = ��ȣ�� ����Ѵ�.

    Base64������ character�� 6-bit�� ����ϴµ�, ������ �빮�� 'A' �� �⺻ ���� �ȴ�.
    ���ĺ� �빮�� 'A' ���� 0�� ���� ���� �ҹ���, ���� 0 ~ 9, �׸��� + / = �� ��ȣ���ʷ� ���� Ű���� ���´�.

    ���� ��� ������ �ҹ��� 'm' �� 2���� 100110 ���� ǥ���� �Ǹ� �̰��� 38�̶�� Base64 ���� ���µ�
    Base64 Encoding �� �� ����� �� ���� ������ ������ 65������ ���� ���� ������ �̷�� ���ڵ���
    �����̶�� �� �� �ִ�. ������, �ܼ��� �ѹ��ھ� ��Ī�� �Ǵ°��� �ƴϴ�. 8��Ʈ������ ������ ����
    3���� 4���� 6��Ʈ ���ڷ� ��ȯ�ϴ� ���̴�.

    "kim" �̶�� ���ڿ��� Base64 encoding �ϴ� ���� ���ڴ�.

    US-ASCII ������ ���ڸ�
       "k" ��  107
       "i" �� 105
       "m" �� 109
     �� ���� ���� ���´�.
     ex) System.out.println((int)'k'); �غ��� ���� �˼� �������̴�.

    ���� int ���� ���� 2������ ��ȯ�ϸ�
        k   01101011
        i    01101001
        m  01101101
     �� ���� ���´�.
     ex) System.out.println(Integer.toBinaryString((int)('k'))); �غ��� ���� �˼� �������̴�.

    8��Ʈ�� ������ 3���� ���ڸ� ������� �����ϸ� 24��Ʈ�� ������ ���� �迭�� ���� �� �ִµ�.

                   011010110110100101101101

    �� 24��Ʈ�� 4���� 6��Ʈ������ �������

                   011010 110110 100101 101101

    ���� 4���� 2������ 10������ �ٲٸ�,
                     26     54     37     45
    �� �ɰ��̰�, ���⿡ ��Ī�� �Ǵ� Base64 ���ڴ� ������ ����.
                     a      2       l     t

    �̷��� encoding �۾��� 3���� ���ڸ� 4���� ���ڷ� �����ϴ� ��, Ư�� ���ڿ���
    �ݵ�� 3���� ������� ������ ����. 1�� �Ǵ� 2�� ������ ���ڿ��� �󸶵��� �����ϴ�.
    �̷��� ������ ���ڿ��� ���ؼ� '=' ��ȣ�� ��ü�Ͽ� ����Ѵ�.

    ���� ��� "ki" �� encoding �Ѵٰ� ��������

    	01101011 01101001 : 16���� 2���� ����
    	0110101101101001

    	011010 110110 1001 : 6��Ʈ������ ������ 3���� ���� �ۿ� �ȳ��´�.
    	�׷���, �켱 ����° ������ �����ʿ��� ���� 6��Ʈ���� ä���ִ´�.
    	�׸���, 111111 �� �߰����� : ���⼭ 111111 �� Base64Ű���� ���� ū��
    	�� '=' ��ȣ�� ���� ���̴�.
        ���� 011010 110110 100100 111111 �� ���� ����� ���̴�.
        �� ������ ���� ����

        26   54   36   64

        a      2      k     =
        �� ���� ���̴�.

        "k" �� encoding �Ҷ��� ���

        01101011
        011010 11 : ���⼭ �ι��� ������ 6��Ʈ�� ���� ä������ '=' �� �ش��ϴ�
        111111 �� �ι� �ݺ����ش�.

        011010 110000 111111 111111
        �� ������ ���� ����

        26   48   64   64

        a      w      =     =
        �� ���� ���̴�.
</pre>
 <p>
 <strong>Code Sample:</strong>
 <blockquote>
 <pre>
   String s1 = Base64.encodeToString("Hi  ���ѹα�! ¦¦¦ ¦¦!");
   String s2 = Base64.decodeToString(s1);
   System.out.println(s1);
   System.out.println(s2);
 </pre>
 </blockquote>
 �� �����<br>
 <blockquote>
 <pre>
 SGkgILTrx9G5zrG5ISDCpsKmwqYgwqbCpiE= 
 Hi ���ѹα�! ¦¦¦ ¦¦! 
 </pre>
 </blockquote>
*/

public class Base64
{

    private Base64()
    {
        //Base64 �⺻������
    }

    /**
     * String s�� Base64�� decoding ��Ŵ.
     * @param s decoding ��ų String
     * @return Base64 decoding�� byte �迭
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
     * byte �迭 abyte0�� Base64�� decoding ��Ŵ.
     * @param s decoding ��ų byte �迭
     * @return Base64 decoding�� byte �迭
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
     * String s�� decoding ��Ŵ.
     * @param s decoding ��ų String
     * @return Base64 decoding�� String
 	 */
    public static final String decodeToString(String s)
    {
        if(s == null)
            return null;

        return new String(decodeToByteArray(s));
    }

    /**
     * byte �迭 abyte0�� decoding ��Ŵ.
     * @param s decoding ��ų byte �迭
     * @return Base64 decode�� String
 	 */
    public static final String decodeToString(byte abyte0[])
    {
        return new String(decodeToByteArray(abyte0));
    }

    /**
     * String s�� Base64�� encoding ��Ŵ.
     * @param s encoding ��ų String
     * @return Base64 encoding�� byte �迭
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
     * byte �迭 abyte0�� Base64�� encoding ��Ŵ.
     * @param s encoding ��ų byte �迭
     * @return Base64 encoding�� byte �迭
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
     * String s�� encoding ��Ŵ.
     * @param s encoding ��ų String
     * @return Base64 encoding�� String
 	 */
    public static final String encodeToString(String s)
    {
        if(s == null)
            return null;

        return new String(encodeToByteArray(s));
    }

    /**
     * byte �迭 abyte0�� encoding ��Ŵ.
     * @param s encoding ��ų byte �迭
     * @return Base64 decode�� String
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
