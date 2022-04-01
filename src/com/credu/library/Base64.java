/**
* ������Ʈ�� : kocca_java
* ��Ű���� : com.credu.library
* ���ϸ� : Base64.java
* �ۼ���¥ : 2011. 10. 1.
* ó������ :
* �������� :
 
* Copyright by CREDU.Co., LTD. ALL RIGHTS RESERVED.   
*/

package com.credu.library;

public class Base64 
{
	public Base64()
	{ 
	}

    protected static final char[] enc_table = 
    {
	    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 
	    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
	    'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
	    't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
	    '8', '9', '+', '/'
    };

    protected static final byte[] dec_table = 
    {
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 
	    -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, 
	    -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
	    10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 
	    25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 
	    34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 
	    49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1
    };
    
    /**
     * String�� encode�� �Ѵ�.
     * @param String1
     * @return str
     * */
    public String encode(String String1)throws IllegalArgumentException
    {
	    String str = new String( encodeAsByteArray( String1.getBytes()) );
	    return str;
    }

    public static byte[] encodeAsByteArray(byte[] byte_1darray1) 
    {
	    if( byte_1darray1 == null || byte_1darray1.length == 0 )
	    {
		    throw new IllegalArgumentException( "parameter was null or had length null" );
	    } 
        else 
        {
     	   int int2 = 0;
     	   int int3 = 0;
     	   int int4 = byte_1darray1.length;
     	   int int5 = int4 % 3;
     	   int int6 = (int4 + 2) / 3 * 4 + ((int4 == 0) ? 2 : 0);
     	   byte[] byte_1darray7 = new byte[int6];
     	   byte byte8;
     	   byte byte9;
     	   int int11;

     	   for( int11 = int4 / 3; int11 > 0; --int11 ) 
     	   {
     		   byte byte10;

     		   byte8  = byte_1darray1[int2++];
     		   byte9  = byte_1darray1[int2++];
     		   byte10 = byte_1darray1[int2++];

     		   byte_1darray7[int3++] = (byte) enc_table[byte8 >>> 0x2 & 0x3F];
     		   byte_1darray7[int3++] = (byte) enc_table[(byte8 << 0x4 & 0x30) + (byte9 >>> 0x4 & 0xF)];
     		   byte_1darray7[int3++] = (byte) enc_table[(byte9 << 0x2 & 0x3C) + (byte10 >>> 0x6 & 0x3)];
     		   byte_1darray7[int3++] = (byte) enc_table[byte10 & 0x3F];
     	   }
     	   if( int5 == 1 ) 
     	   {
     		   byte8 = byte_1darray1[int2++];
     		   byte_1darray7[int3++] = (byte) enc_table[byte8 >>> 0x2 & 0x3F];
     		   byte_1darray7[int3++] = (byte) enc_table[byte8 << 0x4 & 0x30];
     		   byte_1darray7[int3++] = (byte) 61;
     		   byte_1darray7[int3++] = (byte) 61;
     	   }
     	   else if( int5 == 2 ) 
     	   {
     		   byte8 = byte_1darray1[int2++];
     		   byte9 = byte_1darray1[int2++];
     		   byte_1darray7[int3++] = (byte) enc_table[byte8 >>> 0x2 & 0x3F];
     		   byte_1darray7[int3++] = (byte) enc_table[(byte8 << 0x4 & 0x30) + (byte9 >>> 0x4 & 0xF)];
     		   byte_1darray7[int3++] = (byte) enc_table[byte9 << 0x2 & 0x3C];
     		   byte_1darray7[int3++] = (byte) 61;
            }
            if( int3 != int6 )
          	  throw new IllegalArgumentException( "Bug in Base64.java: incorrect length calculated for base64 output" );
            else
          	  return byte_1darray7;
           }
    }

    public static byte[] decode(byte[] byte_1darray1)
    {
	    int int2 = 0;
	    int int4 = byte_1darray1.length;
	    int int5 = 0;
	    int int3;

	    for( int3 = int4 - 1; int3 >= 0; --int3 ) 
	    {
		    if( byte_1darray1[int3] > 32 )
			    ++int5;
	         if( byte_1darray1[int3] == 61 )
	     	    ++int2;
	    }
	    if( int5 % 4 != 0 )
	    {
		    throw new IllegalArgumentException( "Length not a multiple of 4" );
	    } 
	    else 
	    {
		    int int6 = int5 / 4 * 3 - int2;
		    byte[] byte_1darray7 = new byte[int6];
		    byte[] byte_1darray8;
		    int int9;
		    int int10;

	         int3 = 0;
	         byte_1darray8 = new byte[4];
	         int9 = 0;
	         int10 = 0;
	         byte_1darray8[0] = byte_1darray8[1] = byte_1darray8[2] = byte_1darray8[3] = (byte) 61;

	         while( int3 < int4 )
	         {
	     	    byte byte11 = byte_1darray1[int3++];
	     	    if( byte11 > 32 )
	     		    	byte_1darray8[int10++] = byte11;
	     	    if( int10 != 4 )
	     		    continue;
	     	    int9 += decode( byte_1darray7, int9, byte_1darray8[0], byte_1darray8[1], byte_1darray8[2], byte_1darray8[3] );
	     	    int10 = 0;
	     	    byte_1darray8[0] = byte_1darray8[1] = byte_1darray8[2] = byte_1darray8[3] = (byte) 61;
	         }

	         if( int10 > 0 )
	     	    	decode( byte_1darray7, int9, byte_1darray8[0], byte_1darray8[1], byte_1darray8[2], byte_1darray8[3] );
	         		return byte_1darray7;
	    }
    }
    
    /**
     * String�� decode�� �Ѵ�.
     * @param String
     * @return str
     * */
    public static String decode(String String1)throws IllegalArgumentException
    {
	    String str = new String(decode( String1.getBytes()));
	    return str;
    }
    
    private static int decode(byte[] byte_1darray1, int int2, byte byte3, byte byte4, byte byte5, byte byte6)
    {
	    byte byte7 = dec_table[byte3];
	    byte byte8 = dec_table[byte4];
	    byte byte9 = dec_table[byte5];
	    byte byte10 = dec_table[byte6];
	    if( byte7 == -1 || byte8 == -1 || byte9 == -1 && byte5 != 61 || byte10 == -1 && byte6 != 61 )
	    {
		    throw new IllegalArgumentException( "Invalid character [" + (byte3 & 0xFF) + ", " + (byte4 & 0xFF) + ", " + (byte5 & 0xFF) + ", " + (byte6 & 0xFF) + "]" );
	    }
	    else
	    {
		    byte_1darray1[int2++] = (byte) (byte7 << 0x2 | byte8 >>> 0x4);
		    if( byte5 == 61 )
			    return 1;
		    else
		    {
			    byte_1darray1[int2++] = (byte) (byte8 << 0x4 | byte9 >>> 0x2);
			    if( byte6 == 61 )
				    return 2;
			    else
	              {
				    byte_1darray1[int2++] = (byte) (byte9 << 0x6 | byte10);
	                   return 3;
	              }
			}
	     }
    }
}
