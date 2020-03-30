package service;

import java.util.List;
import java.util.Map;

import model.Glamping_board_reply;

public interface GlampingReplyService {

	public boolean insertReply(Glamping_board_reply glampingBoardReply);
	public List<Map<String, Object>> selectReplyList(int gl_num);
	public boolean deleteReply(Glamping_board_reply glampingBoardReply);
}
