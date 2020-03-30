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

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Glamping_board_reply;
import service.GlampingReplyService;

@Controller
@RequestMapping("/glampingReply")
public class GlampingBoardReplyController {

	@Autowired
	GlampingReplyService glampingReplyService;
	private Glamping_board_reply glampingBoardReply = new Glamping_board_reply();
	
	@ResponseBody
	@PostMapping(value="/replyInsert")
	public String replyInsert(@RequestParam Map<String, Object> params) {
		String content = (String) params.get("replyContent");
	
		int m_num = Integer.parseInt((String) params.get("m_num"));
		int gl_num = Integer.parseInt((String)params.get("gl_num"));
		glampingBoardReply.setContent(content);
		glampingBoardReply.setGl_num(gl_num);
		glampingBoardReply.setM_num(m_num);
		if(glampingReplyService.insertReply(glampingBoardReply)) {
			return "{ \"result\" : true}";
		}else {
			
			return "{ \"result\" : false}";
		}

		
	}
	
	@ResponseBody
	@GetMapping(value="/getReplyList")
	public ResponseEntity<List<Map<String, Object>>> getReplyList(@RequestParam int gl_num) {
		List<Map<String, Object>> replyList =  glampingReplyService.selectReplyList(gl_num);
		
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
	public String deleteReply(@RequestParam Map<String, Object> params) {
		int gl_num = Integer.parseInt((String)params.get("gl_num"));
		int m_num = Integer.parseInt((String)params.get("m_num"));
		int gbr_num = Integer.parseInt((String)params.get("gbr_num"));
		glampingBoardReply.setGbr_num(gbr_num);
		glampingBoardReply.setGl_num(gl_num);
		glampingBoardReply.setM_num(m_num);
		
		if(glampingReplyService.deleteReply(glampingBoardReply)) {
			return "{ \"result\" : true}";
		}else {
			return "{ \"result\" : false}";
		}
	}
	
	
	
}
