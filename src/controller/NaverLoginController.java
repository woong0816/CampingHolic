package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scribejava.core.model.OAuth2AccessToken;

import model.Member;
import service.MemberService;
import service.NaverLoginService;

@Controller
@RequestMapping("/naverLogin")
public class NaverLoginController {

	private NaverLoginService naverLoginService;
	private String apiResult = null;
	
	
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	private void setNaverLoginService(NaverLoginService naverLoginService) {
		this.naverLoginService = naverLoginService;
	}

	@ResponseBody
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(Model model, HttpSession session) {
		/* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginService클래스의 getAuthorizationUrl메소드 호출 */
		String naverAuthUrl = naverLoginService.getAuthorizationUrl(session);
		// https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************&
		// redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125
		//System.out.println("네이버:" + naverAuthUrl);
		String decodedString = "";
		try {
			decodedString = URLDecoder.decode(naverAuthUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		
		// 네이버	
		return decodedString;	
	}

	// 네이버 로그인 성공시 callback호출 메소드
	@RequestMapping(value = "/callback", method = { RequestMethod.GET, RequestMethod.POST })
	public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session)
			throws IOException, ParseException {
		System.out.println("여기는 callback");
		OAuth2AccessToken oauthToken;
		oauthToken = naverLoginService.getAccessToken(session, code, state);
		// 1. 로그인 사용자 정보를 읽어온다.
		apiResult = naverLoginService.getUserProfile(oauthToken); // String형식의 json데이터
	//	System.out.println(apiResult);
		/**
		 * apiResult json 구조 {"resultcode":"00", "message":"success",
		 * "response":{"id":"33666449","nickname":"shinn****","age":"20-29","gender":"M","email":"sh@naver.com","name":"\uc2e0\ubc94\ud638"}}
		 **/
		// 2. String형식인 apiResult를 json형태로 바꿈
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(apiResult);
		JSONObject jsonObj = (JSONObject) obj;
		// 3. 데이터 파싱
		// Top레벨 단계 _response 파싱
		JSONObject response_obj = (JSONObject) jsonObj.get("response");
		// response의 nickname값 파싱
		Member member = new Member();
		String nickname = (String)response_obj.get("nickname");
		
		String id = (String)response_obj.get("id");
		if(memberService.naverSelectId((String)response_obj.get("email"))!=null) {
			
		}else {
			member.setEmail((String)response_obj.get("email"));
			member.setBith((String)response_obj.get("birthday"));
			member.setGender((String)response_obj.get("gender"));
			member.setName((String)response_obj.get("name"));
			member.setNickName((String)response_obj.get("nickname"));
			member.setNickName((String)response_obj.get("nickname"));
			member.setPassword("1");
		}
		session.setAttribute("nickName", nickname); // 세션 생성
		session.setAttribute("memberNum", memberService.naverSelectId((String)response_obj.get("email")).getM_num());
		model.addAttribute("result", apiResult);
		model.addAttribute("url", "../main/mainView");
		model.addAttribute("msg", "네이버 아이디로 로그인합니다");
		return "result";
	}

	// 로그아웃
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpSession session) throws IOException {
		System.out.println("여기는 logout");
		session.invalidate();
		return "redirect:Camping_Main";
	}

}
