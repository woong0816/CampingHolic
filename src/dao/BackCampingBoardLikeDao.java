package dao;

import java.util.Map;

import model.BackCamping_Board_like;

public interface BackCampingBoardLikeDao {
	//사용자의 좋아요 체크여부
	public int selecOneLike(Map<String, Object> param); 
	//게시글의 총 좋아요 수 
	public int selectLikeCount(int bc_num);
	//사용자 좋아요 체크
	public int likeCheck(Map<String, Object>param);
	//사용자 좋아요 해제
	public int updataLike(Map<String, Object> param);
	//사용자 좋아요 체크값
	public int LikeNo(Map<String, Object> param);
}
