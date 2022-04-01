package com.credu.library;

  /**
 * <p>����: jsp ����Ʈȭ�� �ϴܿ� ��µǴ� paging���� ���̺귯��</p>
 * <p>����: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author ������
 *@date 2003. 12
 *@version 1.0
 */
public class PageList {

    private int m_pageCount=0;
    private int m_pageNum=0;
    private int m_pageBlockSize=0;
    
    private boolean m_previous = false;
    private boolean m_next = false;
    
    private int m_previousBlockStartNumber=0;
    private int m_nextBlockStartNumber=0;
    
    private int m_blockStartNumber=0;
    private int m_blockEndNumber=0;
    
    public PageList() {}
    
    public PageList(int pageCount, int pageNum, int pageBlockSize) {
        m_pageCount = pageCount;
        m_pageNum = pageNum;
//        m_pageBlockSize = pageBlockSize;
        if(pageBlockSize == 15){
        	m_pageBlockSize = 15;
        }else{
        	m_pageBlockSize = 10;                 // �� 10���� ����
        }
        
        this.init();
    }
	
    private void init() {
        //��ü block ����
        int block_count = ((m_pageCount - 1) / m_pageBlockSize) + 1;
        
        //���� block ��ġ
        int current_block = ((m_pageNum - 1) / m_pageBlockSize) + 1;
        
        //���� block ����
        m_previous = (current_block > 1) ? true : false;
        
        //���� block�� ���� ������
        if (m_previous) {
            m_previousBlockStartNumber = ((current_block - 2) * m_pageBlockSize) + 1;
        }
        
        //���� block ����
        m_next = (current_block < block_count) ? true : false;
        
        //���� block�� ���� ������
        if (m_next) {
            m_nextBlockStartNumber = (current_block * m_pageBlockSize) + 1;
        }
        
        //���� block�� ���� ������ �� �� ������
        if (current_block < block_count) {
            m_blockStartNumber = ((current_block - 1) * m_pageBlockSize) + 1;
            m_blockEndNumber = current_block * m_pageBlockSize;
        }else {
            m_blockStartNumber = ((current_block - 1) * m_pageBlockSize) + 1;
            m_blockEndNumber = m_pageCount;
        }
    }

    public boolean previous() {
        return m_previous;
    }
    
    public boolean next() {
        return m_next;
    }
    
    public int getStartPage() {
        return m_blockStartNumber;
    }
    
    public int getEndPage() {
        return m_blockEndNumber;
    }
    
    public int getPreviousStartPage() {
        return m_previousBlockStartNumber;
    }
    
    public int getNextStartPage() {
        return m_nextBlockStartNumber;
    }	
}
