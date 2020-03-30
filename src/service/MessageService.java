package service;

import java.util.List;
import java.util.Map;

public interface MessageService {

	public Map<String, Object> receiveMsg(int m_num, int page);
	//받은 메시지 리스트
	public Map<String, Object> sendMsg(int m_num, int page);
	//보낸 메시지 리스트
	public boolean readCheck(int msg_num);
	//읽은 확인
	public boolean insertMessage(Map<String, Object>param);
	//메시지 등록
	public int nickCheck(String nickName);
	//닉네임으로 멤버번호 확인
}
