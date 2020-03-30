package service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.BackCampingBoardReplyDao;
import model.BackCamping_board_reply;

@Service
public class BackCampingReplyServiceImp implements BackCampingReplyService{
	
	@Autowired
	BackCampingBoardReplyDao backCampingReplyDao;
	
	
	@Override
	public boolean insertReply(BackCamping_board_reply backCampingReply) {
		
		if(backCampingReplyDao.insertReply(backCampingReply)==1) {
			return true;
		}else {
			return false;		
		}
		
	}


	@Override
	public List<Map<String, Object>> getReplyList(int bc_num) {
		List<Map<String, Object>> param = backCampingReplyDao.getReplyList(bc_num);
		
		return param;
	}

	@Override
	public boolean deleteReply(BackCamping_board_reply backCampingReply) {
		if(backCampingReplyDao.deleteReply(backCampingReply)==1) {
			return true;
		}else {
			return false;			
		}
	}

}
