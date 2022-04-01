package com.credu.library;

  /**
 * <p>제목: jsp 리스트화면 하단에 출력되는 paging관련 라이브러리</p>
 * <p>설명: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author 이정한
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
        	m_pageBlockSize = 10;                 // 블럭 10개로 세팅
        }
        
        this.init();
    }
	
    private void init() {
        //전체 block 갯수
        int block_count = ((m_pageCount - 1) / m_pageBlockSize) + 1;
        
        //현재 block 위치
        int current_block = ((m_pageNum - 1) / m_pageBlockSize) + 1;
        
        //이전 block 유뮤
        m_previous = (current_block > 1) ? true : false;
        
        //이전 block의 시작 페이지
        if (m_previous) {
            m_previousBlockStartNumber = ((current_block - 2) * m_pageBlockSize) + 1;
        }
        
        //다음 block 유뮤
        m_next = (current_block < block_count) ? true : false;
        
        //다음 block의 시작 페이지
        if (m_next) {
            m_nextBlockStartNumber = (current_block * m_pageBlockSize) + 1;
        }
        
        //현재 block의 시작 페이지 및 끝 페이지
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
