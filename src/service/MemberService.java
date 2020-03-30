package service;


import java.util.Map;

import model.Member;
import model.memberAuth;

public interface MemberService {

	public int insertMember(Member member);
	public memberAuth authSelectOne(int m_num);
	public int updateAuth (memberAuth memberAuth);
	public boolean loginMember(String email,String password);
	public Member selectOneMember(String email);
	public boolean checkEmail(String email);
	public boolean nickCheck(String nickName);
	public int snsInsert(Member member,String id);
	public Member naverSelectId(String email);
	public boolean selectOne(String m_num, String my_pass);
	public Map<String, Object> memberInfo(String nickName);
	public boolean memberModify(Map<String, Object> param);
}
