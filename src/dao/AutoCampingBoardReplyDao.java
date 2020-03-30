package dao;

import java.util.List;
import java.util.Map;

public interface AutoCampingBoardReplyDao {

	public int insertReply(Map<String, Object> param);
	public List<Map<String, Object>> getReplyList(int ac_num);
	public int deleteReply(Map<String, Object> param);
}
