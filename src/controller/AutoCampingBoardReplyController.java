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

import service.AutoCampingReplyService;

@Controller
@RequestMapping("/autoCampingReply")
public class AutoCampingBoardReplyController {

	@Autowired
	AutoCampingReplyService autoCampingReplyService;
	
	@ResponseBody
	@PostMapping(value="/replyInsert")
	public String replyInsert(@RequestParam Map<String, Object> param) {
		if(autoCampingReplyService.insertReply(param)) {
			return "{ \"result\" : true}";	
		}else {
			return "{ \"result\" : false}";	
		}
		
	}
	@ResponseBody
	@GetMapping(value="/getReplyList")
	public ResponseEntity<List<Map<String, Object>>> getReplyList(@RequestParam int ac_num){
		List<Map<String, Object>> replyList =  autoCampingReplyService.getReplyList(ac_num);
		
		// ObjectMapper mapper = new ObjectMapper();
		 ResponseEntity<List<Map<String, Object>>> entity = null; 
		 
		 try {
			entity = new ResponseEntity<List<Map<String, Object>>>(replyList,HttpStatus.OK);
		 }catch(Exception e){
			 entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        
		 }
		return entity;
	}
	
	@ResponseBody
	@PostMapping(value="/deleteReply")
	public String deleteReply(@RequestParam Map<String, Object> param) {
		if(autoCampingReplyService.deleteReply(param)) {
			return "{ \"result\" : true}";
		}else {
			return "{ \"result\" : false}";
		}
		
	}
	
}
