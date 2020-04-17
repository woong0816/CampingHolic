	package service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import controller.MailHandler;
import controller.tempKey;
import dao.MemberAuthDao;
import dao.MemberDao;
import model.Member;
import model.memberAuth;

@Service
public class MemberServiceImp implements MemberService{

	@Autowired
	MemberDao memberDao;
	@Autowired
	MemberAuthDao memberAuthDao;
	@Autowired
	JavaMailSender mailSender;
	
	private int result;
	private memberAuth memberAuth = new memberAuth();
	private Member member = new Member();
	
	
	@Override
	public int insertMember(Member member) {
		
		result = memberDao.insertMember(member);
		System.out.println("////////////회원가입 완료///////////////");
		String key = new tempKey().getKey(50, "N");
		System.out.println(key);
		memberAuth.setm_num((member.getM_num()));
		memberAuth.setAuthKey(key);
		memberAuthDao.insertMemberAuth(memberAuth);
		
		System.out.println("인증키 DB저장");
		try {
			MailHandler sendMail = new MailHandler(mailSender);
			sendMail.setSubject("Camping Holic 이메일 인증입니다.");
			sendMail.setText(new StringBuffer().append("<h1>CampingHolic 회원가입을 계속 하시려면 인증을 해주세요<h1>")
					.append("<a href='http://54.180.98.183:8080/CampingHolic/member/emailConfirm?userEmail=")
					.append(member.getEmail())
					.append("&memberAuthKey=")
					.append(key)
					.append("&m_num=")
					.append(member.getM_num())
					.append("'target='_blank'>이메일 인증 확인</a>").toString());
			sendMail.setFrom("misterhong0816@gmail.com", "CampingHolic 인증메일 ");
			sendMail.setTo(member.getEmail());
			sendMail.send();
			System.out.println("이메일 전송");
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return result;
	}



	@Override
	public memberAuth authSelectOne(int m_num) {
		
		memberAuth =  memberAuthDao.selectOne(m_num);
		return memberAuth;
	}



	@Override
	public int updateAuth(memberAuth memberAuth) {
		
		memberAuth.setAuthYN("Y");
		result = memberAuthDao.updateMemberAuth(memberAuth);		
		
		
		return result;
	}

	@Override
	public boolean loginMember(String email,String password) {
	
		  member = memberDao.loginMember(email);
		  	if(member!=null) {
				  if(member.getPassword().equals(password)) {
					  //비밀번호 일치여부
					  if(member.getAuthYN().equals("Y")) {
						  return true;
						  //이메일 인증 여부
					  }
				  } 
			  }else {
				  return false;		  
			  }

		return false;
	}

	@Override
	public Member selectOneMember(String email) {
		return memberDao.selectOneMember(email);
	}
	
	@Override
	public boolean checkEmail(String email) {
		member = memberDao.checkEmail(email);
		if(member !=null && member.getEmail().equals(email)) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean nickCheck(String nickName) {
		member = memberDao.nickCheck(nickName);
		if(member!=null && member.getNickName().equals(nickName)) {
			return true;
		}
		return false;
	}



	@Override
	public int snsInsert(Member member) {
		//여기서 소셜 로그인을 해야 함
		if(memberDao.insertMember(member)>1) {
			return member.getM_num();
		}else {
			return member.getM_num();
		}
	}



	@Override
	public Member naverSelectId(String email) {
		return memberDao.naverIdCheck(email);
	
	}



	@Override
	public boolean selectOne(String m_num, String my_pass) {
		member = memberDao.selectOne(m_num);
			if(member.getPassword().equals(my_pass)) {
				return true;
			}else {
				return false;
			}
	}



	@Override
	public Map<String, Object> memberInfo(String nickName) {
		Map<String, Object> member = new HashMap<String, Object>(); 
				member = memberDao.selectMemberInfo(nickName);

		return member;
	}

	@Override
	public boolean memberModify(Map<String, Object> param) {
		if(memberDao.pwModify(param)==1) {
		return true;
	}else {
		return false;
	}

}

}