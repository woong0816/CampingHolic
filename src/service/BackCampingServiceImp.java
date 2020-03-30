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

import dao.BackCampingBoardDao;
import dao.BackCampingBoardLikeDao;
import dao.BackCampingFileDao;
import model.BackCampingBoard;
import model.BackCamping_imgFile;


@Service
public class BackCampingServiceImp implements BackCampingService{

	@Autowired
	ServletContext servletContext;
	@Autowired
	BackCampingBoardDao backCampingBoardDao;
	@Autowired
	BackCampingFileDao backCampingFileDao;
	@Autowired
	BackCampingBoardLikeDao backCampingBoardLikeDao;
	
	private BackCamping_imgFile backCamping_ImgFile = new BackCamping_imgFile();
	private static final int NUM_OF_NAVI_PAGE = 5;
	private static final int NUM_OF_MESSAGE_PER_PAGE = 10;
	
	
	@Override
	public boolean insertBackCampingBoard(BackCampingBoard backCampingBoard, List<String> fileList) {
		if(backCampingBoardDao.insertBackCampingBoard(backCampingBoard)>0) {
			for(int i=0; i<fileList.size(); i++) {
				String fileName = fileList.get(i);
				backCamping_ImgFile.setBci_imgFileName(fileName);
				backCamping_ImgFile.setBc_num(backCampingBoard.getBc_num());
				backCamping_ImgFile.setBci_subnum(i+1);
				backCampingFileDao.insertBackCampingFile(backCamping_ImgFile);
			}
			
			return true;
		}
		
		
		return false;
	}
	@Override
	public Map<String, Object> selectList(Map<String, Object> searchParam,int pageNum, String filter) {
		//게시글 리스트	
		Map<String, Object> param = new HashMap<String, Object>();
		
		int type = (int)searchParam.get("type");
		String keyword  = (String)searchParam.get("keyword");
		if(type==1) {
			param.put("BC_TITLE", keyword);
		}else if(type==2) {
			param.put("NICKNAME", keyword);
		}else if(type==3) {
			param.put("BC_ADDRESS", keyword);
		}else if(type==4) {
			param.put("BC_CONTENT", keyword);
		}
		
		int totalPage = backCampingBoardDao.selectBoardCount(param);
		
		int firstRow = getFirstRow(pageNum);
		int endRow = getEndRow(pageNum);
		param.put("firstRow", firstRow);
		param.put("endRow", endRow);
		
		List<String> backCampingBoardList=  new ArrayList<String>();
		if(filter!=null) {
			switch (filter) {
			case "like": backCampingBoardList= backCampingBoardDao.selectLikeCount(param);
				break;
			case "readCount" : backCampingBoardList = backCampingBoardDao.selectReadCount(param); 
				break;
			default:  backCampingBoardList = backCampingBoardDao.selectList(param);
				break;
			}
		}else {
			backCampingBoardList = backCampingBoardDao.selectList(param);
		}
		
		Map<String, Object> viewData = new HashMap<String, Object>();
		
		viewData.put("viewList", backCampingBoardList);
		viewData.put("currentPage", pageNum);
		viewData.put("pageTotalCount", calPageTotalCount(totalPage));
		viewData.put("startPage", getStartPage(pageNum));
		viewData.put("endPage", getEndPage(pageNum));
		
		return viewData;
	}

	
	@Override
	public Map<String, Object> selectOne(int bc_num) {
		// 게시글 상세보기
	
		return backCampingBoardDao.selectOne(bc_num);
	}
	
	@Override
	public boolean updateBackCampingBoard(BackCampingBoard backCampingBoard, List<String> fileList) {
		//게시글 수정
		//여기서 게시글 정보만 받아와서 게시글 업데이트 해주고 파일삭제후 다시 등록
		if(backCampingBoardDao.updateBackCampingBoard(backCampingBoard)) {
			if(backCampingFileDao.deleteBackCampingFile(backCampingBoard.getBc_num())) {
				for(int i=0; i<fileList.size(); i++) {
					String fileName = fileList.get(i);
					backCamping_ImgFile.setBc_num(backCampingBoard.getBc_num());
					backCamping_ImgFile.setBci_imgFileName(fileName);
					backCamping_ImgFile.setBci_subnum(i+1);
					backCampingFileDao.insertBackCampingFile(backCamping_ImgFile);
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean deleteBackCampingBoard(int bc_num) {
	//게시글 삭제
		if(backCampingBoardDao.deleteBackCampingBoard(bc_num)) {
			return true;
		}else {
			return false;	
		}
	}
	
	

	@Override
	public List<Map<String, Object>> SearchBackCampingInformation(String keyword, int pageNum) {


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





	@Override
	public List<String> fileTag(String text) {
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
	@Override
	public Map<String, Object> backCampingBoardDetailView(int bc_num, int m_num) {
		Map<String, Object> likeCheck = new HashMap<String, Object>();
		likeCheck.put("bc_num", bc_num);
		likeCheck.put("m_num", m_num);
		int liketo=0;
		
		//int tmpLiketo = backCampingBoardLikeDao.selecOneLike(likeCheck);
		/*
		 * String saasd = String.valueOf(tmpLiketo.get("LIKETO"));
		 * System.out.println(saasd);
		 */
		if(backCampingBoardLikeDao.selecOneLike(likeCheck)==0) {			
		}else if(backCampingBoardLikeDao.LikeNo(likeCheck)==1){
			liketo = 1;
		}
		
		int likeCount = backCampingBoardLikeDao.selectLikeCount(bc_num);
		
		
		Map<String, Object> detailView = new HashMap<String, Object>();
		detailView.put("backCampingBoard", backCampingBoardDao.selectOne(bc_num));
		detailView.put("liketo", liketo);
		detailView.put("likeCount", likeCount);
		detailView.put("backCamping_fileList", backCampingFileDao.selectImgList(bc_num));
		return detailView;
	}
	@Override
	public List<String> mainViewList(Map<String, Object> param) {
	
		return backCampingBoardDao.selectList(param);
	}
	@Override
	public boolean readCountUpdate(int bc_num) {
		if(backCampingBoardDao.readCountUpdate(bc_num)>0) {
			return true;
		}else {
			return false;			
		}
	}
	
	
	
	
}
