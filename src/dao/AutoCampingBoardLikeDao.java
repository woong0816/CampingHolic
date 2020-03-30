package dao;

import java.util.Map;

public interface AutoCampingBoardLikeDao {
	//좋아요를 누르지 않은 사용자의 좋아요 여부
	public int selectOneLiketo(Map<String, Object> param);
	//좋아요 체크
	public int likeCheck(Map<String, Object> param);
	//좋아요 누른 사용자의 체크 여부
	public int likeNo(Map<String, Object> param);
	//사용자 좋아요 해제
	public int liketoUpdata(Map<String, Object> param);
	//게시글 총 좋아요 수
	public int likeCount(int ac_num);
}
