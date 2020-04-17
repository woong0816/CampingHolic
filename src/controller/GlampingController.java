package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import model.GlampingBoard;
import service.GlampingLikeService;
import service.GlampingService;

@Controller
@RequestMapping("/glamping") 
public class GlampingController {
	
	@Autowired
	GlampingService glampingService;
	@Autowired
	GlampingLikeService glampingLikeServie;
	
	private static final String FILE2 = "file";
	private static final String UPLOADIMGPATH = "/home/ubuntu/download/imgdata/";

	@RequestMapping(value = "/glamping_main")	 
	public String glampingMain(Model model, @RequestParam(value="page" , required=false, defaultValue="1")int pageNum,
															@RequestParam(required = false)String keyword,
															@RequestParam(defaultValue = "0")int type,
															@RequestParam(required= false)String filter
														 ) {
		
		
		Map<String, Object> serchType = new HashMap<String, Object>();
		serchType.put("keyword", keyword);
		serchType.put("type", type);
	
		//키워드는 검색한 키워드
		//타입은 조회수 좋아요 최신순
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData = glampingService.selectList(serchType,pageNum,filter);
		
		model.addAttribute("viewData", viewData);
		
		return "glamping/glamping_mainView";
	}

	@RequestMapping(value = "/glamping_writeView")
	public String glampingWriteView() {
		return "glamping/glamping_write";
	}
	// 글랭핑 게시글 작성 폼 요청

	@PostMapping(value = "/glampingWrite")
	public String glampingWrite(@ModelAttribute GlampingBoard glampingBoard, Model model,HttpSession session) {
		
		String content = glampingBoard.getGl_content();
		int m_num = (int)session.getAttribute("memberNum");
		glampingBoard.setM_num(m_num);
		List<String> imgTag = glampingService.fileNameList(content);

		if(glampingService.insertGlampingBoard(glampingBoard, imgTag)) {
			//insert success
			model.addAttribute("msg", "등록완료");
			model.addAttribute("url", "../glamping/glamping_main");
		}else {
			model.addAttribute("msg", "등록실패");
			model.addAttribute("url", "../glamping/glamping_writeView");	
		}
		
		return "result";
	}
	
	//글램핑 게시글 상세보기
	@GetMapping(value="/glampingBoardView")
	public String glampingView(Model model, int gl_num,HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		int m_num = (int)session.getAttribute("memberNum");
		
		Cookie[] cookies = request.getCookies();
		Cookie viewCookie = null;
		if(cookies != null && cookies.length>0) {
			for(int i=0; i< cookies.length; i++) {
				if(cookies[i].getName().equals("cookie"+gl_num)) {				
					viewCookie = cookies[i];		
				}
			}
		}
		
		if(viewCookie == null) {
			Cookie newCookie = new Cookie("cookie"+gl_num,"|" + gl_num + "|");
			response.addCookie(newCookie);
			glampingService.readCountUpdate(gl_num);
		}else {
			//쿠기값있음 
		}
		

		model.addAttribute("glampingView", glampingService.GlampingBoardDetailView(m_num, gl_num));
		
		return "glamping/glamping_view";
	}
	//글램핑 게시글 수정폼 요청
	@GetMapping(value="/glampingModifyForm")
	public String glampingModifyForm(int num, Model model,HttpSession session) {
		model.addAttribute("glampingModifyView", glampingService.selectOne(num));

		return "glamping/glamping_modify";
	}
	//글램핑 게시글 수정 
	@PostMapping(value="/glampingModify")
	public String glampingModify(@ModelAttribute GlampingBoard glampingBoard,Model model) {
		String content = glampingBoard.getGl_content();
		
		List<String> imgTag = glampingService.fileNameList(content);
		
		if(glampingService.modifyGlampingBoard(glampingBoard,imgTag)) {
			model.addAttribute("url", "glamping_main");
			model.addAttribute("msg", "수정되었습니다");
			
		}else {
			model.addAttribute("msg", "수정에 실패 했습니다.");
			model.addAttribute("url", "glampingModifyForm");
		}

		return "result";
	}
	
	
	@GetMapping(value="/glampingDelete")
	public String glampingDelete(int num,Model model) {
			model.addAttribute("url", "glamping_main");
			if(glampingService.deleteGlampinBoard(num)) {
				model.addAttribute("msg", "삭제되었습니다");
			}
		return "result";
	}
	

	@RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> glampingImgUpload(MultipartHttpServletRequest request) throws IOException {


		MultiValueMap<String, MultipartFile> multiFileMap = request.getMultiFileMap();
		List<MultipartFile> list = multiFileMap.get(FILE2);
		MultipartFile multipartFile = list.get(0);

		UUID uuid = UUID.randomUUID();
		// uuid 생성
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss"); // 날짜형식 지정
		String today = formatter.format(new Date()); // 오늘 일자 및 시간 세팅
		String fileName = today + "-" + uuid + "_" + multipartFile.getOriginalFilename();
		String fullName = UPLOADIMGPATH + fileName;
		
		File file = new File(fullName);

		multipartFile.transferTo(file);
		BufferedImage bi = ImageIO.read(file);

		byte[] data = multipartFile.getBytes();
		FileOutputStream fos = new FileOutputStream(fullName);
		// 03. 맵 반환
		fos.write(data);
		fos.close();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("fileName", fileName);
		resultMap.put("imgWidth", bi.getWidth());
		resultMap.put("imgHeight", bi.getHeight());

		return resultMap;

	}

	@ResponseBody
	@PostMapping(value="/addressSearch")
	public Map<String, List<Map<String, Object>>> addressSearch(String addressVal,@RequestParam(defaultValue = "1")int pageNum){
		List<Map<String, Object>> addressList =  glampingService.SearchGlampingInformation(addressVal,pageNum);
		Map<String, List<Map<String, Object>>> mapList = new HashMap<String, List<Map<String, Object>>>();
		mapList.put("addressList", addressList);
		return mapList;
	}
	@ResponseBody
	@PostMapping(value="/checkLike")
	public String checkLike(@RequestParam int m_num , int gl_num) {
		//좋아요 처리
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("m_num", m_num);
		params.put("gl_num", gl_num);
		if(glampingLikeServie.LikeCheck(params)) {
			return "{\"result\" : true}";
		}else {
			return "{\"result\" : false}";
		}
	
	}
	
	
	
	
}
