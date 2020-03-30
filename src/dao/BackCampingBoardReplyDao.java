package dao;

import java.util.List;
import java.util.Map;

import model.BackCamping_board_reply;

public interface BackCampingBoardReplyDao {
	//댓글 등록
	public int insertReply(BackCamping_board_reply backCampingBoardreply);
	//댓글 리스트
	public List<Map<String, Object>> getReplyList(int bc_num); 
	//댓글 삭제
	public int deleteReply(BackCamping_board_reply backCampingBoardreply);
	
}
