package service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.BackCampingBoardLikeDao;

@Service
public class BackCampingLikeServiceImp implements BackCampingLikeService{

	@Autowired
	BackCampingBoardLikeDao backCampingLikeDao;
	
	
	@Override
	public boolean likeCheck(int bc_num, int m_num) {
		Map<String, Object> likeInfo = new HashMap<String, Object>();
		likeInfo.put("bc_num", bc_num);
		likeInfo.put("m_num", m_num);
		int tmpLiketo = backCampingLikeDao.selecOneLike(likeInfo);
		
		if(tmpLiketo==0) {
			backCampingLikeDao.likeCheck(likeInfo);
			return true;
		}else {
			if(backCampingLikeDao.LikeNo(likeInfo)==0) {
				likeInfo.put("LIKETO", 0);
				backCampingLikeDao.updataLike(likeInfo);
				return true;
			}else {
				likeInfo.put("LIKETO", 1);
				backCampingLikeDao.updataLike(likeInfo);
				
			}
		}
		return false;
	}

	
}
