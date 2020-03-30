package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import service.MessageService;

@Controller
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	MessageService messageService;
	
	
	@RequestMapping(value="/receive")
	public String receive(HttpSession session, @RequestParam(value="page", defaultValue="1")int page ,Model model) {
		System.out.println("받은 메시지");
		int m_num = (int)session.getAttribute("memberNum");
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData = messageService.receiveMsg(m_num,page);
		model.addAttribute("viewData", viewData);
		return "message/myReceiveMessage";
	}
	
	@RequestMapping(value="/send")
	public String send(HttpSession session, @RequestParam(value="page", defaultValue = "1")int page, Model model) {
		System.out.println("보낸 메시지 ");
		int m_num = (int)session.getAttribute("memberNum");
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData = messageService.sendMsg(m_num, page);

		model.addAttribute("viewData", viewData);
		return "message/mySendMessage";
	}
	@ResponseBody
	@RequestMapping(value="/readCheck")
	public String readCheck(@RequestParam int msg_num) {
		if(messageService.readCheck(msg_num)) {
			return "{\"result\": true}";
		}else {
			
			return "{\"result\": false}";
		}
	}
	@RequestMapping(value="/sendmsgForm")
	public String sendmsgForm() {
		return "message/sendMessage";
	}
	
	@CrossOrigin
	@MessageMapping("/greeting/{memberid}/{targetid}")
	@SendTo("/message/topic/message/{targetid}")
	public String stompTest(String message, @DestinationVariable(value="memberid")String memberid
			, @DestinationVariable(value="targetid")String targetid) {
		
		Map<String, Object> insertMessage = new HashMap<String, Object>();
		System.out.println("message : "+message);
		System.out.println("targetid : "+targetid);
		//타겟 아이디는 닉네임 
		int recv_M_num = messageService.nickCheck(targetid);
		System.out.println("memberid : "+memberid);
		
		JSONParser parser = new JSONParser();
		JSONObject jobject;
		
		try {
			jobject = (JSONObject) parser.parse(message);
			System.out.println(jobject.get("sendUser"));
			System.out.println(jobject.get("recvUser"));
			System.out.println(jobject.get("title"));
			System.out.println(jobject.get("content"));
			
			insertMessage.put("send_M_num", jobject.get("sendUser"));
			insertMessage.put("recv_M_num", recv_M_num);
			insertMessage.put("MSG_title", jobject.get("title"));
			insertMessage.put("MSG_content", jobject.get("content"));
			if(messageService.insertMessage(insertMessage)) {
				System.out.println("메시지 추가");
				return message;
			}else {
				
			}
			
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}
}
