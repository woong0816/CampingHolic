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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import model.AutoCampingBoard;
import service.AutoCampingLikeService;
import service.AutoCampingService;

@Controller
@RequestMapping("/autoCamping")
public class AutoCampingBoardController {
	private static final String FILE2 = "file";
	private static final String UPLOADIMGPATH = "/home/ubuntu/download/";
	
	@Autowired
	AutoCampingService autoCampingService;
	@Autowired
	AutoCampingLikeService autoCampingLikeService;
	
	@RequestMapping(value="/autoCamping_main")
	public String autoCampingMain(Model model,
									@RequestParam(value="page", required = false, defaultValue = "1")int pageNum,
									@RequestParam(required = false)String keyword,
									@RequestParam(defaultValue = "0")int type,
									@RequestParam(required = false)String filter
								) {
		
		Map<String, Object> searchParam = new HashMap<String, Object>();
		
		searchParam.put("type", type);
		searchParam.put("keyword", keyword);
		Map<String, Object>  viewData = autoCampingService.selectList(searchParam,pageNum,filter);
		
		model.addAttribute("viewData", viewData);
		
		return "autoCamping/autoCamping_mainView";
	}//오토캠핑 메인
	
	@RequestMapping("/autoCamping_writeView")
	public String autoCampingWriteView() {
		return "autoCamping/autoCamping_write";
	}//오토캠핑 글쓰기 폼요청
	
	@PostMapping(value="/autoCampingWrite")
	public String autoCampingWrite(@ModelAttribute AutoCampingBoard autoCampingBoard,HttpSession session,Model model) {
		List<String> fileList = autoCampingService.fileNameList(autoCampingBoard.getAc_content());
		autoCampingBoard.setM_num((int)session.getAttribute("memberNum"));
		
		if(autoCampingService.insertAutoCampingBoard(autoCampingBoard,fileList)) {
			model.addAttribute("url", "autoCamping_main");
			model.addAttribute("msg", "게시글이 등록되었습니다.");
			
		}else {
			model.addAttribute("url", "autoCamping_writeView");
			model.addAttribute("msg", "게시글 등록에 실패했습니다.");
				
		}
			return "result";
	}
	
	
	@GetMapping(value="/autoCampingView")
	public String autoCampingBoardView(int ac_num,Model model,HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		int m_num = (int)session.getAttribute("memberNum");
		System.out.println(m_num);
		Cookie[] cookies = request.getCookies();
		Cookie viewCookie = null;
		if(cookies != null && cookies.length>0) {
			for(int i=0; i< cookies.length; i++) {
				if(cookies[i].getName().equals("cookie"+ac_num)) {
					viewCookie = cookies[i];
				}
			}
		}
		
		if(viewCookie == null) {
			Cookie newCookie = new Cookie("cookie"+ac_num,"|" + ac_num + "|");
			response.addCookie(newCookie);
			System.out.println("123"+newCookie);
			autoCampingService.readCountUpdate(ac_num);
		}else {
			//쿠기값있음 
		}
			Map<String, Object> detailView =  autoCampingService.autoCampingBoardDetailView(ac_num,m_num);
			model.addAttribute("detailView", detailView);
			
			return "autoCamping/autoCamping_view";
		
		//게시글 상세보기
	}
	
	@GetMapping(value="/autoCampingModifyForm")
	public String autoCampingModifyForm(int ac_num,Model model) {
		model.addAttribute("autoCampingModify", autoCampingService.selectOne(ac_num));
		return "autoCamping/autoCamping_modify";
	}
	
	@PostMapping(value="/autoCampingModify")
	public String autoCampingBoardModify(@ModelAttribute AutoCampingBoard autoCampingBoard, Model model) {
		
		
		List<String> fileList = autoCampingService.fileNameList(autoCampingBoard.getAc_content());
		
		if(autoCampingService.autoCampingBoardModify(autoCampingBoard,fileList)) {
			model.addAttribute("url", "autoCampingView?ac_num="+autoCampingBoard.getAc_num());
			model.addAttribute("msg", "게시글이 수정되었습니다.");
		}else {
			model.addAttribute("url", "autoCampingModifyForm");
			model.addAttribute("msg", "게시글 수정 실패하였습니다.");
		}

		return "result";
	}
	
	@GetMapping(value="/autoCampingDelete")
	public String autoCampingDelete(int ac_num,Model model) {
		
		if(autoCampingService.deleteAutoCampingBoard(ac_num)) {
			model.addAttribute("url", "autoCamping_main");
			model.addAttribute("msg", "게시글이 삭제되었습니다.");
		}else {
			model.addAttribute("url", "autoCampingView?ac_num"+ac_num);
			model.addAttribute("msg", "게시글 삭제 실패했습니다.");
		}
		return "result";
	}
	
	
	@PostMapping(value="/imgUpload")
	@ResponseBody
	public Map<String, Object> autoCampingImgUpload(MultipartHttpServletRequest request) throws IOException {

		MultiValueMap<String, MultipartFile> multiFileMap = request.getMultiFileMap();
		List<MultipartFile> list = multiFileMap.get(FILE2); 
		MultipartFile multipartFile = list.get(0);

		UUID uuid = UUID.randomUUID();
		// uuid 생성
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss"); // 날짜형식 지정
		String today = formatter.format(new Date()); // 오늘 일자 및 시간 세팅
		String fileName = today + "-" + uuid + "_" + multipartFile.getOriginalFilename();
		String fullName = UPLOADIMGPATH + fileName;

		//System.out.println(fullName);

		File file = new File(fullName); // System.out.println(webappRoot + fullName);

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

		System.out.println(resultMap);
			
		return resultMap;

	}
	@ResponseBody
	@PostMapping(value="/addressSearch")
	public Map<String, List<Map<String, Object>>> addressSearch(String addressVal,@RequestParam(defaultValue = "1")int pageNum){
		
		List<Map<String, Object>> addressList =  autoCampingService.SearchAutoCampingInformation(addressVal,pageNum);
	
		Map<String, List<Map<String, Object>>> mapList = new HashMap<String, List<Map<String, Object>>>();
		
		mapList.put("addressList", addressList);
		
		return mapList;
	}
	@ResponseBody
	@PostMapping(value="/checkLike")
	public String likeCheck(@RequestParam int ac_num, int m_num) {

		if(autoCampingLikeService.LikeCheck(ac_num, m_num)) {
			return "{\"result\" : true}";
		}else {
			return "{\"result\" : false}";
		}
	}

}
