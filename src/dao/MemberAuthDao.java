package dao;

import model.memberAuth;

public interface MemberAuthDao {

	public int insertMemberAuth(memberAuth memberAuth);
	public memberAuth selectOne(int m_num);
	public int updateMemberAuth(memberAuth memberAuth);
}
