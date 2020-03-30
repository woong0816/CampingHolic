package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.MessageDao;

@Service
public class MessageServiceImp implements MessageService{
	@Autowired
	MessageDao messageDao;
	
	private static final int NUM_OF_BOARD_PER_PAGE = 10;
	private static final int NUM_OF_NAVI_PAGE = 10;
	
	@Override
	public boolean insertMessage(Map<String, Object> param) {
	
	int result = messageDao.insertMessage(param);
		if(result>0) {
			return true;
		}else {			
			return false;
		}
	}
	
	
	@Override
	public Map<String, Object> receiveMsg(int m_num, int page) {
		//여기서 받은 메시지를 돌려보내주라
		Map<String, Object> params = new HashMap<String, Object>();
		
				
		int startPage = getStartPage(page);
		int endPage = getEndPage(page);
		int firstRow = (page - 1) * NUM_OF_BOARD_PER_PAGE + 1;
		int endRow = page * NUM_OF_BOARD_PER_PAGE;
		
		int totalCount = messageDao.receiveCount(m_num);
		int pageTotalcount = calPageTotalCount(totalCount);
		params.put("firstRow", firstRow);
		params.put("endRow", endRow);
		params.put("m_num", m_num);
		
		List<String> messageList = messageDao.myreceiveMessage(params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("messageList", messageList);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", page);
		result.put("pageTotalCount", pageTotalcount);
		
		return result;
	}
	
	

	@Override
	public Map<String, Object> sendMsg(int m_num, int page) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		
		int startPage = getStartPage(page);
		int endPage = getEndPage(page);
		int firstRow = (page - 1) * NUM_OF_BOARD_PER_PAGE + 1;
		int endRow = page * NUM_OF_BOARD_PER_PAGE;
		
		int totalCount = messageDao.sendCount(m_num);
		int pageTotalcount = calPageTotalCount(totalCount);
		params.put("firstRow", firstRow);
		params.put("endRow", endRow);
		params.put("m_num", m_num);
		
		List<String> messageList = messageDao.mySendMessage(params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("messageList", messageList);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", page);
		result.put("pageTotalCount", pageTotalcount);
		
		return result;
		
	
	}

	
	private int calPageTotalCount(int totalCount) {
		int calPage = 0;
		if (totalCount != 0) {
			calPage = (int) Math.ceil((double) totalCount / NUM_OF_BOARD_PER_PAGE);

		}
		return calPage;
	}

	private int getStartPage(int pageNum) {
		int startPage = ((pageNum - 1) / NUM_OF_NAVI_PAGE) * NUM_OF_NAVI_PAGE + 1;

		return startPage;
	}

	private int getEndPage(int pageNum) {
		int endPage = (((pageNum - 1) / NUM_OF_NAVI_PAGE) + 1) * NUM_OF_NAVI_PAGE;
		return endPage;
	}



	@Override
	public boolean readCheck(int msg_num) {
		if(messageDao.readCheck(msg_num)>0) {
			return true;
		}else {
			return false;
		}
	}


	@Override
	public int nickCheck(String nickName) {
		return messageDao.nickCheck(nickName);
	}




		
}
