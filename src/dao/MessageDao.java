package dao;

import java.util.List;
import java.util.Map;

import model.Message;

public interface MessageDao {

	public int insertMessage(Map<String, Object> param);
	//메시지 보내기
	public List<String> myreceiveMessage(Map<String, Object> params);
	//받은메시지 리스트
 	public int receiveCount(int m_num);
	//총 받은 메시지 카운트
	public int sendCount(int m_num);
	//총 보낸 메시지 카운트
	public List<String> mySendMessage(Map<String, Object> params);
	//보낸메시지 리스트
	public int readCheck(int msg_num);
	//읽은 확인
	public int nickCheck(String nickName);
	//보낼 멤버 번호 확인
	
}
