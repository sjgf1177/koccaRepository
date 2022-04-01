package com.credu.library;

/**
 * ��ȣȭ���� �� ����� ���߱� ���� ���Ǵ� Padding�� �߻�ȭ �� Interface
 * 
 * @author jinuk jung, junducki@naver.com
 * @version 1.0, 2008. 03. 11
 */
/*
 * History -2008. 03. 11, Create, jinuk jung
 */

public interface CryptoPadding {

    /**
     * ��û�� Block Size�� ���߱� ���� Padding�� �߰��Ѵ�.
     * 
     * @param source
     *            byte[] �е��� �߰��� bytes
     * @param blockSize
     *            int block size
     * @return byte[] �е��� �߰� �� ��� bytes
     */
    public byte[] addPadding(byte[] source, int blockSize);

    /**
     * ��û�� Block Size�� ���߱� ���� �߰� �� Padding�� �����Ѵ�.
     * 
     * @param source
     *            byte[] �е��� ������ bytes
     * @param blockSize
     *            int block size
     * @return byte[] �е��� ���� �� ��� bytes
     */
    public byte[] removePadding(byte[] source, int blockSize);

}
