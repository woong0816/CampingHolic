package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import model.BackCampingBoard;
import service.BackCampingLikeService;
import service.BackCampingService;

@Controller
@RequestMapping("/backCamping")
public class BackCampingController {
	
	
	@Autowired
	BackCampingService backCampingService;
	@
	Autowired
	BackCampingLikeService backCampingLikeService;
	
	private static final String FILE2 = "file";
	private static final String UPLOADIMGPATH = "D:/server/image/data/";

	
	@RequestMapping("/backCamping_main")
	public String backCampingMain(Model model, 
								@RequestParam (value="page", required = false, defaultValue = "1")int pageNum,
								@RequestParam(required = false) String keyword,
								@RequestParam(defaultValue = "0")int type,
								@RequestParam(required = false) String filter
								) {
		
		
		Map<String, Object> param  = new HashMap<String, Object>(); 
		param.put("type", type);
		param.put("keyword", keyword);
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData = backCampingService.selectList(param,pageNum,filter);
		
		model.addAttribute("viewData", viewData);
		
		return "backCamping/backCamping_mainView";
	}
	
	@RequestMapping("/backCamping_writeView")
	public String backCampingWriteView() {
		return "backCamping/backCamping_write";
	}//백캠핑 글쓰기 작성 폼 요청
	
	@PostMapping("/backCampingWrite")
	public String backCampingWrite(@ModelAttribute BackCampingBoard backCampingBoard,Model model, HttpSession session) {
		String content = backCampingBoard.getBc_content();
		int m_num = (int)session.getAttribute("memberNum");
		backCampingBoard.setM_num(m_num);
		List<String> fileList = backCampingService.fileTag(content);
		if(backCampingService.insertBackCampingBoard(backCampingBoard, fileList)) {
			//등록 성공
			model.addAttribute("url", "backCamping_main");
			model.addAttribute("msg", "등록되었습니다.");
		}else {
			model.addAttribute("url", "backCamping_writeView");
			model.addAttribute("msg", "수정실패했습니다.");
		}

		return "result";
	}//게시글 작성
	
	@GetMapping("/backCampingBoardView")
	public String backCampingBoardView(int bc_num, HttpSession session,Model model,HttpServletRequest request, HttpServletResponse response) {
		
		//model.addAttribute("backCampingBoard", backCampingService.selectOne(bc_num));
		int m_num = (int)session.getAttribute("memberNum");
		
		Cookie[] cookies = request.getCookies();
		Cookie viewCookie = null;
		if(cookies != null && cookies.length>0) {
			for(int i=0; i< cookies.length; i++) {
				if(cookies[i].getName().equals("cookie"+bc_num)) {				
					viewCookie = cookies[i];		
				}
			}
		}
		
		if(viewCookie == null) {
			Cookie newCookie = new Cookie("cookie"+bc_num,"|" + bc_num + "|");
			response.addCookie(newCookie);
			backCampingService.readCountUpdate(bc_num);
		}else {
			//쿠기값있음 
		}
		
		
		Map<String, Object> detailView = backCampingService.backCampingBoardDetailView(bc_num, m_num);
		model.addAttribute("detailView", detailView);
		return "backCamping/backCamping_view";
	} //백패킹 게시글 상세보기
	
	@GetMapping("/backCampingModifyForm")
	public String backCamping_ModifyForm(int num,Model model){
		
		model.addAttribute("backCampingModify", backCampingService.selectOne(num));
		return "backCamping/backCamping_modify";
	} //게시글 수정 폼 요청
	
	@PostMapping("/backCampingModify")
	public String backCampingModify(@ModelAttribute BackCampingBoard backCampingBoard,Model model) {
		String content = backCampingBoard.getBc_content();
		List<String>  imgTag = backCampingService.fileTag(content);
		if(backCampingService.updateBackCampingBoard(backCampingBoard, imgTag)) {
			model.addAttribute("url", "backCamping_main");
			model.addAttribute("msg", "수정되었습니다.");
		}else {
			model.addAttribute("url", "backCampingModifyForm");
			model.addAttribute("msg", "수정실패했습니다.");
		}
		
		
		
		//게시글 수정 
		return "result";
	}
	@RequestMapping("/backCampingDelete")
	public String backCampingDelete(int num,Model model) {
		model.addAttribute("url", "backCamping_main");
		if(backCampingService.deleteBackCampingBoard(num)) {
			model.addAttribute("msg", "게시글이 삭제되었습니다.");
		}else {
			model.addAttribute("msg", "삭제에 실패 했습니다.");
		}
		
		return "result";
		
	}//게시글 삭제
	
	
	
	@RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> backCampingImgUpload(MultipartHttpServletRequest request) throws IOException {


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
		List<Map<String, Object>> addressList =  backCampingService.SearchBackCampingInformation(addressVal,pageNum);
		Map<String, List<Map<String, Object>>> mapList = new HashMap<String, List<Map<String, Object>>>();
		mapList.put("addressList", addressList);
		return mapList;
	}
	
	@ResponseBody
	@PostMapping(value="/checkLike")
	public String likeCheck(@RequestParam int bc_num, int m_num) {
		
		if(backCampingLikeService.likeCheck(bc_num, m_num)) {
			return "{\"result\" : true}";
		}else {
			return "{\"result\" : false}";
			
		}
	}
	
	
}
