package service;

import java.util.List;
import java.util.Map;

public interface AutoCampingReplyService {

	public boolean insertReply(Map<String, Object> param);
	public List<Map<String, Object>> getReplyList(int ac_num);
	public boolean deleteReply(Map<String, Object> param);
}
