package controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import model.BackCamping_board_reply;
import service.BackCampingReplyServiceImp;

@Controller
@RequestMapping("/backCampingReply")
public class BackCampingBoardReplyController {

	@Autowired
	BackCampingReplyServiceImp backCampingReplyService;
	
	@ResponseBody
	@PostMapping(value="/replyInsert")
	public String replyInsert(@RequestParam Map<String, Object> param) {
		BackCamping_board_reply backCampingReply = new BackCamping_board_reply();
		System.out.println(param.get("bc_num"));
		
		 backCampingReply.setBc_num(Integer.parseInt((String)param.get("bc_num")));
		 backCampingReply.setContent(((String)param.get("replyContent")));
		 backCampingReply.setM_num(Integer.parseInt((String)param.get("m_num")));
		if( backCampingReplyService.insertReply(backCampingReply)) {
			return "{ \"result\" : true}";	
		}else {

			return "{ \"result\" : false}";
		}
		
	}
	
	@ResponseBody
	@GetMapping(value="/getReplyList")
	public ResponseEntity<List<Map<String, Object>>> getReplyList(@RequestParam int bc_num){
		List<Map<String, Object>> replyList = backCampingReplyService.getReplyList(bc_num);
		ResponseEntity<List<Map<String, Object>>> entity = null;
		entity = new ResponseEntity<List<Map<String,Object>>>(replyList,HttpStatus.OK);
		
		return entity;
	}
	@ResponseBody
	@PostMapping(value="/deleteReply")
	public String deleteReply(@RequestParam int bbr_num, int m_num,int bc_num) {
		BackCamping_board_reply backCampingReply = new BackCamping_board_reply();
		backCampingReply.setBbr_num(bbr_num);
		backCampingReply.setBc_num(bc_num);
		backCampingReply.setM_num(m_num);
		if(backCampingReplyService.deleteReply(backCampingReply)) {
			return "{ \"result\" : true}";	
		}else {
			
			return "{ \"result\" : false}";	
		}
		
	}
	
	
}
