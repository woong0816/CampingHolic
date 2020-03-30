package dao;

import java.util.List;
import java.util.Map;

import model.Glamping_board_reply;

public interface GlampingBoardReplyDao {

	public int insertReply(Glamping_board_reply glampingBoardReply);
	public List<Map<String, Object>> selectReplyList(int gl_num);
	public int deleteReply(Glamping_board_reply glampingBoardReply);
}

