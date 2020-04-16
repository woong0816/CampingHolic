package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import model.Member;
import model.memberAuth;
import service.MemberService;

@RequestMapping("/member")
@Controller
public class MemberController {

	@Autowired
	MemberService memberService;
	
	private memberAuth memberAuth = new memberAuth();
	private Member member = new Member();
	
	@PostMapping(value="/joinAction")
	public String joinAction(@ModelAttribute Member member,Model model) {
		
		model.addAttribute("url", "../main/mainView");
		model.addAttribute("msg", "이메일 인증후 로그인 가능합니다.");
		int result = memberService.insertMember(member);

		if(result==1) {
		}
		return "result";
	}

	@PostMapping(value="/loginAction")
	public String loginAction(@ModelAttribute Member member,HttpSession session,Model model) {
		if(memberService.loginMember(member.getEmail(), member.getPassword())) {
			member = memberService.selectOneMember(member.getEmail());
			
			session.setAttribute("memberNum", member.getM_num());
			session.setAttribute("nickName", member.getNickName());
			model.addAttribute("msg", "로그인 되었습니다");
		} else {
				model.addAttribute("msg", "등록된 회원이 아니거나 인증여부를 확인");
		}
		model.addAttribute("url", "../main/mainView");
		
		return "result";
	}	//로그인 처리

	@GetMapping(value="/emailConfirm")
	public String emailConfirm(@RequestParam Map<String, Object> params,Model model) {
		int m_num = Integer.parseInt((String) params.get("m_num"));
		System.out.println(m_num);
		memberAuth =  memberService.authSelectOne(m_num);
		
		String memberAuthKey = (String)params.get("memberAuthKey");
		System.out.println("요기까찌");
		
		if(memberAuth.getAuthKey().equals(memberAuthKey)) {
			memberService.updateAuth(memberAuth);
			
			model.addAttribute("msg", "인증이 완료 되었습니다");
			model.addAttribute("url", "../main/mainView");
		}else {
			model.addAttribute("msg", "인증에 실패 했습니다");
			model.addAttribute("url", "../main/mainView");
		}
		return "result";
	} //이메일 인증
	
	@GetMapping(value="/logoutAction")
	public String logoutAction(HttpSession session,Model model) {
		System.out.println("로그아웃");
		session.removeAttribute("nickName");
		session.removeAttribute("memberName");
		model.addAttribute("msg", "로그아웃 되었습니다");
		model.addAttribute("url", "../main/mainView");
		return "result";
	} //로그아웃
	
	@ResponseBody
	@RequestMapping(value="/checkEmail")
	public String checkEmail(@RequestParam String email) {
		  if(memberService.checkEmail(email)) {
			  return "{\"result\" : true}";
		  };
		 
		return "{\"result\" : false}";
	} //이메일 중복체크
	
	@ResponseBody
	@RequestMapping(value="/nickcheck")
	public String nickcheck(@RequestParam String nickName) {
		if(memberService.nickCheck(nickName)) {
			 return "{\"result\" : true}";
		};
		return "{\"result\" : false}";
	}// 닉네임 중복체크
	
	@RequestMapping(value="/passwordDup")
	@ResponseBody
	public String passwordDup(@RequestParam Map<String, Object> params) {
		if(params.get("password").equals(params.get("password2"))) {
			 return "{\"result\" : true}";
		}
		return "{\"result\" : false}";
	} 

	@RequestMapping(value="/memberInfo")
	public String memberModified() {
		return "myPage/memberinfo";
	}
	
	@PostMapping(value="/pwCheck")
	@ResponseBody
	public String memberPwCheck(@RequestParam String m_num, String my_pass) {
		if(memberService.selectOne(m_num,my_pass)) {
			 return "{\"result\" : true}";
		}else {
			return "{\"result\" : false}";	
		}
		
	}
	@RequestMapping("/mypage")
	public String myPageForm(HttpSession session, Model model) {
		String nickName = (String)session.getAttribute("nickName");
		Map<String, Object> member = memberService.memberInfo(nickName);
		//System.out.println("1");
			 model.addAttribute("member", member);
		return "myPage/memberModify";
	} 
	
	@PostMapping(value="/memberModify")
	public void ModifyMember(@RequestParam String password,HttpSession session,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		int m_num = (int)session.getAttribute("memberNum");	
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("m_num", m_num);
		param.put("password", password);
		PrintWriter out = response.getWriter();
			if(memberService.memberModify(param)) {
				out.print("<script>alert('비밀번호가 변경되었습니다.'); location.href='../member/memberInfo'</script>");
				out.close();
			}else {
				out.print("<script>alert('비밀번호를 확인해주세요.'); location.href='../member/memberModify'</script>");
				out.close();
			} //회원 비밀번호 변경
			
	}
}