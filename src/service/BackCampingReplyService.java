package service;

import java.util.List;
import java.util.Map;

import model.BackCamping_board_reply;

public interface BackCampingReplyService {
	//댓글 등록
	public boolean insertReply(BackCamping_board_reply backCampingReply);
	//댓글 리스트
	public List<Map<String, Object>> getReplyList(int bc_num);
	//댓글 삭제
	public boolean deleteReply (BackCamping_board_reply backCampingReply);
}
