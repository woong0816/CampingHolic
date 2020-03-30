package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.GlampingBoardDao;
import dao.GlampingBoardLikeDao;
import dao.GlampingFileDao;
import model.GlampingBoard;
import model.Glamping_ImgFile;
import model.Glamping_board_like;

@Service
public class GlampingServiceImp implements GlampingService{

	private static final int NUM_OF_NAVI_PAGE = 5;
	private static final int NUM_OF_MESSAGE_PER_PAGE = 12;
	private Glamping_ImgFile glamping_imgFile = new Glamping_ImgFile();
	private Glamping_board_like glampingBoardLike = new Glamping_board_like();
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	GlampingBoardLikeDao glampingBoardLikeDao;
	@Autowired
	GlampingBoardDao glampingBoardDao;
	@Autowired
	GlampingFileDao glampingfileDao;
	private GlampingBoard glampingboard = new GlampingBoard();
	
	//게시글 작성
	@Override
	public boolean insertGlampingBoard(GlampingBoard glampingBoard, List<String> fileList) {
		
		if(glampingBoardDao.insertGlBoard(glampingBoard)>0) {
			for(int i=0; i<fileList.size(); i++) {
				String fileName = fileList.get(i);
				glamping_imgFile.setGli_imgFileName(fileName);
				glamping_imgFile.setGl_num(glampingBoard.getGl_num());
				glamping_imgFile.setGli_subnume(i+1);
				glampingfileDao.insertglampingImgFile(glamping_imgFile);
			}
			
			return true;
		}
		
		
		return false;
	}
	
	@Override
	public Map<String, Object> selectList(Map<String, Object>param , int pageNum,String filter){
		List<String> glampingBoardList = new ArrayList<String>();
		Map<String, Object> ViewData = new HashMap<String, Object>();
		Map<String, Object> searchParam = new HashMap<String, Object>();
		
		//게시글 정보랑 페이징 정보를 넘겨 줘야 함
		
		int type = (int)param.get("type");
		String keyword = (String)param.get("keyword");
		
		if(type==1) {
				searchParam.put("GL_TITLE", keyword);
		}else if(type==2) {
			searchParam.put("NICKNAME", keyword);
		}else if(type==3) {
			searchParam.put("GL_ADDRESS", keyword);
		}else if(type==4) {
			searchParam.put("GL_CONTENT", keyword);
		}
		int totalPage = glampingBoardDao.selectBoardCount(searchParam);
		
		
		
		int firstRow = getFirstRow(pageNum);
		int endRow = getEndRow(pageNum);
		
		
		searchParam.put("firstRow", firstRow);
		searchParam.put("endRow", endRow);
		
		if(filter==null){
			glampingBoardList = glampingBoardDao.selectList(searchParam);
		}else if(filter.equals("like")) {
			glampingBoardList = glampingBoardDao.selectLikeCount(searchParam);
		}else if(filter.equals("readCount")) {
			glampingBoardList = glampingBoardDao.selectReadCount(searchParam);
		}
		
		
		
		
		
		ViewData.put("viewList", glampingBoardList);
		ViewData.put("currentPage", pageNum);
		//현재페이지
		ViewData.put("pageTotalCount", calPageTotalCount(totalPage));
		//총 페이지수
		ViewData.put("startPage", getStartPage(pageNum));
		//시작 페이지
		ViewData.put("endPage",getEndPage(pageNum));
		//끝 페이지
		
		return ViewData;
	}
	
	//주소 검색
	@Override
	public List<Map<String, Object>> SearchGlampingInformation(String keyword,int pageNum) {
		
		   List<Map<String, Object>> foodInformation = new ArrayList<Map<String,Object>>();
	        try {
	            String text = URLEncoder.encode(keyword, "UTF-8");
	            String apiURL = "https://openapi.naver.com/v1/search/local.json?query="+text+"&display=10&start="+pageNum;
	            URL url = new URL(apiURL);
	            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
	            con.setRequestMethod("GET");
	            con.setRequestProperty("X-Naver-Client-Id", "0Rm4wPNOTRpPEhig7_ol");
	            con.setRequestProperty("X-Naver-Client-Secret", "rUW38Oin7R");
	           
	            int responseCode = con.getResponseCode();
	            BufferedReader br;
	            
	            if(responseCode==200) { // 정상 호출
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	                
	            } else {  // 에러 발생
	                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            }
	            
	            String inputLine;
	            System.out.println("스트링 빌더로 바꿈");
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }
	            br.close();
	      
	            
	            JSONObject jsonObject = new JSONObject(response.toString());
	            
	            //System.out.println("총 페이징 처리해야할 페이징"+jsonObject.getInt("total"));
	            //int firstRow = getFirstRow(jsonObject.getInt("start"));
	            //첫번째 페이지를 가져옴
	          //  int endRow = getEndRow(jsonObject.getInt("start"));
	            int totalCount	= jsonObject.getInt("total");

	            JSONArray items = jsonObject.getJSONArray("items");
	            	Map<String, Object> pageMap = new HashMap<String, Object>();
	            
	    		//System.out.println(items);
	    		for(int i =0;i<items.length();i++) {
	    			//json array(책 여러권 정보) 반복하면서 한권의 데이터 꺼내오기
	    			Map<String, Object> tempfoodInfor = new HashMap<String, Object>();
	    			JSONObject item = items.getJSONObject(i);
	    			tempfoodInfor.put("title",item.getString("title"));
	    			tempfoodInfor.put("category",item.getString("category"));
	    			tempfoodInfor.put("description",item.getString("description"));
	    			tempfoodInfor.put("telephone",item.getString("telephone"));
	    			tempfoodInfor.put("address",item.getString("address"));
	    			tempfoodInfor.put("roadAddress",item.getString("roadAddress"));
	    			tempfoodInfor.put("mapx",item.getString("mapx"));
	    			tempfoodInfor.put("mapy",item.getString("mapy"));
	    			//System.out.println(item.getInt("total"));
	    			
	    			tempfoodInfor.put("currentPage", pageNum);
	    			tempfoodInfor.put("pageTotalCount", calPageTotalCount(totalCount));
	    			tempfoodInfor.put("startPage", getStartPage(pageNum));
	    			tempfoodInfor.put("endPage", getEndPage(pageNum));
	    			foodInformation.add(tempfoodInfor);
	    		}
	    		
	    		//foodInformation.add(pageMap);
	    		
	    		for(Map<String,Object> m : foodInformation) {
	    			//System.out.println(m);
	    		}
	    		
//	    		Search11(keyword);
//	    		Search22(keyword);
	    		
	        } catch (Exception e) {
	            System.out.println(e);

	        }
		
		
		return foodInformation;
	}
	
	//페이징 처리 구현
	
		public int calPageTotalCount(int totalPage) {
		
			int pageTotalCount = 0;
			if(totalPage!=0) {
				pageTotalCount = (int)Math.ceil(
						((double)totalPage / NUM_OF_MESSAGE_PER_PAGE));
				
			}
			return pageTotalCount;
		}
		public int getStartPage(int pageNum) {
			
			int startPage = ((pageNum-1)/NUM_OF_NAVI_PAGE)*NUM_OF_NAVI_PAGE+1;
			return startPage;
		}

		public int getEndPage(int pageNum) {
			
			int endPage 
			= (((pageNum-1)/NUM_OF_NAVI_PAGE)+1)
			* NUM_OF_NAVI_PAGE;
			
		
			return endPage;
		}
		private int getFirstRow(int currentPage) {
			return (currentPage - 1) * NUM_OF_MESSAGE_PER_PAGE + 1;
		}

		private int getEndRow(int currentPage) {
			return currentPage * NUM_OF_MESSAGE_PER_PAGE;
		}

		//게시글 이미지 테그 추출
		@Override
		public List<String> fileNameList(String text) {
			//System.out.println("서비스로 넘어온 이미지 파일 리스트"  + text);
			Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>"); //img 태그 src 추출 정규표현식
	        Matcher matcher = pattern.matcher(text);
	        List<String> imgList = new ArrayList<String>();
	        List<String> imgNameList = new ArrayList<String>();
	        
	        while(matcher.find()){
	        	imgList.add(matcher.group(1));
	        }
	        
	        for(int i=0;i<imgList.size();i++) {
	        	imgNameList.add(imgList.get(i).replace(servletContext.getContextPath(), ""));
	        }
	        
			return imgNameList;
	
		
		}

		//게시글 리스트
	

		//게시글 수정 폼
		@Override
		public Map<String, Object> selectOne(int gl_num) {

			return glampingBoardDao.selectOne(gl_num);
		}
		
		//게시글 수정
		@Override
		public boolean modifyGlampingBoard(GlampingBoard glampingBoard,List<String> fileList) {
		//	System.out.println("서비스에서 받은 값"+glampingBoard);
			if(glampingBoardDao.updateGlBoard(glampingBoard)>0) {
				if(glampingfileDao.deleteglampingImgFIle(glampingBoard.getGl_num())) {
					for(int i=0; i<fileList.size(); i++) {
						glamping_imgFile.setGli_imgFileName(fileList.get(i));
						glamping_imgFile.setGli_subnume(i+1);
						glamping_imgFile.setGl_num(glampingBoard.getGl_num());
						glampingfileDao.insertglampingImgFile(glamping_imgFile);
					}
					return true;
				}
				
			}
			
			
			return false;
		}

		//게시글 삭제 
		@Override
		public boolean deleteGlampinBoard(int gl_num) {
				if(glampingBoardDao.deleteGlBoard(gl_num)>0) {
					return true;
				}

			return false;
		}

		//썸네일에 대한 이미지파일 경로
		@Override
		public String selectThumbnail(int gl_num) {
			return glampingfileDao.selectThumbnail(gl_num);
		}

		//게시글 상세보기 게시글 정보 , 좋아요 여부 , 게시글 이미지 리스트까지
		@Override
		public Map<String, Object> GlampingBoardDetailView(int m_num, int gl_num) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("m_num", m_num);
			params.put("gl_num", gl_num);
			List<String> imgList = glampingfileDao.selectImgList(gl_num);
			
			Map<String, Object> glampingBoardDetail = new HashMap<String, Object>();
			glampingBoardDetail.put("glampingBoard", glampingBoardDao.selectOne(gl_num));
			System.out.println(glampingBoardDao.selectOne(gl_num));
			glampingBoardDetail.put("imgList", imgList);
			
			glampingBoardLike= glampingBoardLikeDao.selectOneLike(params);
			
			int likeTo = 0 ;
			if(glampingBoardLike!=null) {
				likeTo = glampingBoardLike.getLiketo();
			}
			glampingBoardDetail.put("liketo", likeTo);
			int likeCount = glampingBoardLikeDao.selectLikeCount(gl_num);
			glampingBoardDetail.put("likeCount", likeCount);
			
			//System.out.println(glampingBoardDetail);
			return glampingBoardDetail;
		}

		@Override
		public List<String> mainViewList(Map<String, Object> param) {
		
			return glampingBoardDao.selectList(param);
		}

		@Override
		public boolean readCountUpdate(int gl_num) {
			if(glampingBoardDao.readCountUpdate(gl_num)>0) {
				return true;
			}else {
				return false;				
			}
		}	
		
}
