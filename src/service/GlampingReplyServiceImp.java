package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.GlampingBoardReplyDao;
import model.Glamping_board_reply;

@Service
public class GlampingReplyServiceImp implements GlampingReplyService{

	@Autowired
	GlampingBoardReplyDao glampingBoardReplyDao;
	
	
	
	@Override
	public boolean insertReply(Glamping_board_reply glampingBoardReply) {
		if(glampingBoardReplyDao.insertReply(glampingBoardReply)>0) {
			
			return true;
		}else {
			
			return false;
		}
		
	}


	@Override
	public List<Map<String, Object>> selectReplyList(int gl_num) {
		List<Map<String, Object>> replyList = new ArrayList<Map<String,Object>>();
		replyList = glampingBoardReplyDao.selectReplyList(gl_num);
		
		return replyList;
	}


	@Override
	public boolean deleteReply(Glamping_board_reply glampingBoardReply) {
		if(glampingBoardReplyDao.deleteReply(glampingBoardReply)>0) {
			return true;
		}else {
			return false;
			
		}
		
		
	}

}
