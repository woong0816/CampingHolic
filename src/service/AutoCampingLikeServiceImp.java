package service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.AutoCampingBoardLikeDao;

@Service
public class AutoCampingLikeServiceImp implements AutoCampingLikeService{

	@Autowired
	AutoCampingBoardLikeDao autoCampingBoardLikeDao;
	
	@Override
	public boolean LikeCheck(int ac_num, int m_num) {
		//사용자의 좋아요 체크 여부
		Map<String, Object> likeInfo = new HashMap<String, Object>();
		likeInfo.put("ac_num", ac_num);
		likeInfo.put("m_num", m_num);
		
		int liketoNo = autoCampingBoardLikeDao.selectOneLiketo(likeInfo); 
		if(liketoNo==0) {
			autoCampingBoardLikeDao.likeCheck(likeInfo);
			return true;
		}else {
				if(autoCampingBoardLikeDao.likeNo(likeInfo)==1) {
					likeInfo.put("LIKETO", 1);
					autoCampingBoardLikeDao.liketoUpdata(likeInfo);
				}else {
					likeInfo.put("LIKETO", 0);
					autoCampingBoardLikeDao.liketoUpdata(likeInfo);
					return true;
				}
		}
		return false;
	}

}
