package service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import dao.GlampingBoardLikeDao;
import model.Glamping_board_like;

@Service
public class GlampingLikeServiceImp implements GlampingLikeService{

	
	@Autowired
	GlampingBoardLikeDao glampingBoardLikeDao;
	private Glamping_board_like glampingBoardLikeVo = new Glamping_board_like();
	
	@Override
	public boolean LikeCheck(Map<String, Object> params) {
		glampingBoardLikeVo = glampingBoardLikeDao.selectOneLike(params);
	//	System.out.println(glampingBoardLikeVo);
		// 자 여기서 좋아요를 했는지 안했는지 구분해서 없으면 insert로
		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("gl_num", params.get("gl_num"));
		updateMap.put("m_num", params.get("m_num"));
		
		
		if(ObjectUtils.isEmpty(glampingBoardLikeVo)) {
			glampingBoardLikeDao.insertLike(updateMap);
			//좋아요를 누르지 않았으면 누르고 likeTo 값은 1로 insert
			return true;
		}else {
			//좋아요를 해제 했다면 1에서 0으로 업데이트
			if(glampingBoardLikeVo.getLiketo()==1) {		
				updateMap.put("LIKETO", 1);
				glampingBoardLikeDao.updateLike(updateMap);	
				return false;
			
			}else {
					updateMap.put("LIKETO", 0);
					glampingBoardLikeDao.updateLike(updateMap);
					
			}
			
		}

		return true;
	}
	

	
	
	
}
