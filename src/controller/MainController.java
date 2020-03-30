package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import service.AutoCampingService;
import service.BackCampingService;
import service.GlampingService;
import service.MemberService;



@Controller
@RequestMapping("/main")
public class MainController {

	@Autowired
	MemberService memberService;
	@Autowired
	AutoCampingService autoCampingService;
	@Autowired
	GlampingService glampingService;
	@Autowired
	BackCampingService backCampingService;
	
	
	@RequestMapping("/mainView")
	public String MainView(HttpSession session,Model model) {
		Map<String, Object> searchParam = new HashMap<String, Object>();
		searchParam.put("firstRow", 1);
		searchParam.put("endRow",8);
		
		int memberNum = 0;
		if(session.getAttribute("nickName")==null) {
			session.setAttribute("memberNum", memberNum);
			session.setAttribute("nickName", "Guest");
		}
		model.addAttribute("autoData", autoCampingService.mainViewList(searchParam));
		model.addAttribute("glampingData", glampingService.mainViewList(searchParam));
		model.addAttribute("backData", backCampingService.mainViewList(searchParam));
		

		return "Camping_Main";
	}

	

}
