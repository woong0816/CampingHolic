package dao;

import java.util.Map;

import model.Member;

public interface  MemberDao {

	public int insertMember(Member member);
	public Member loginMember(String email);
	public Member selectOneMember(String email);
	public Member checkEmail(String email); 
	public Member nickCheck(String nickName);
	public Member naverIdCheck(String email);
	public Member selectOne(String m_num);
	public Map<String, Object> selectMemberInfo(String nickName);
	public int pwModify (Map<String, Object> param);
}
