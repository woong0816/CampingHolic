package dao;

import java.util.Map;

import model.Glamping_board_like;

public interface GlampingBoardLikeDao {
	
	public Glamping_board_like selectOneLike(Map<String, Object> params);
	public int selectLikeCount(int gl_num);
	public int updateLike(Map<String, Object> params);
	public int insertLike(Map<String, Object> params);
	
}
