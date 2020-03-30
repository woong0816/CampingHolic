package service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.AutoCampingBoardReplyDao;

@Service
public class AutoCampingReplyServiceImp implements AutoCampingReplyService{

	@Autowired
	AutoCampingBoardReplyDao autoCampingReplyDao;
	
	@Override
	public boolean insertReply(Map<String, Object> param) {
			if(autoCampingReplyDao.insertReply(param)>0) {
				return true;
			}else {
				return false;
			}
		
	}

	@Override
	public List<Map<String, Object>> getReplyList(int ac_num) {
		List<Map<String, Object>> replyList = autoCampingReplyDao.getReplyList(ac_num);
		return replyList;
	}

	@Override
	public boolean deleteReply(Map<String, Object> param) {
		if(autoCampingReplyDao.deleteReply(param)>0) {
			return true ;
		}else {			
			return false;
		}
	}

}
