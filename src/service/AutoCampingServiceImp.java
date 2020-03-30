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

import dao.AutoCampingBoardDao;
import dao.AutoCampingBoardLikeDao;
import dao.AutoCampingFileDao;
import model.AutoCampingBoard;
import model.AutoCamping_imgFile;

@Service
public class AutoCampingServiceImp implements AutoCampingService{

	@Autowired
	ServletContext servletContext;
	@Autowired
	AutoCampingBoardDao autoCampingBoardDao;
	@Autowired
	AutoCampingFileDao autoCampingFileDao;
	@Autowired
	AutoCampingBoardLikeDao autoCampingBoardLikeDao;
	
	private AutoCamping_imgFile autoCamping_imgFile = new AutoCamping_imgFile();
	private static final int NUM_OF_NAVI_PAGE = 5;
	private static final int NUM_OF_MESSAGE_PER_PAGE = 10;
	
	@Override
	public boolean insertAutoCampingBoard(AutoCampingBoard autoCampingBoard,List<String> fileList) {
		if(autoCampingBoardDao.insertAutoCampingBoard(autoCampingBoard)>0) {
			for (int i = 0; i < fileList.size(); i++) {
				String fileName = fileList.get(i);
				autoCamping_imgFile.setAci_imgFileName(fileName);
				autoCamping_imgFile.setAci_subNume(i+1);
				autoCamping_imgFile.setAc_num(autoCampingBoard.getAc_num());
				autoCampingFileDao.insertAutoCampingFile(autoCamping_imgFile);
			}
			return true;
		}else {
			
			return false;
		}
	}//게시글 작성
	
	@Override
	public Map<String, Object> selectList(Map<String, Object> serchParam,int pageNum, String filter) {
		Map<String, Object> param  = new HashMap<String, Object>();
		List<String> autoCampingBoardList = new ArrayList<String>();
		Map<String, Object> viewData = new HashMap<String, Object>();
		int type = (int)serchParam.get("type");
		String keyword = (String)serchParam.get("keyword");
		if(type==1) {
			param.put("AC_TITLE", keyword);
		}else if(type==2) {
			param.put("NICKNAME", keyword);
		}else if(type==3) {
			param.put("AC_ADDRESS", keyword);
		}else if(type==4) {
			param.put("AC_CONTENT", keyword);
		}
		int totalPage = autoCampingBoardDao.selectBoardCount(param);
		int firstRow = getFirstRow(pageNum);
		int endRow = getEndRow(pageNum);
		
		param.put("firstRow", firstRow);
		param.put("endRow", endRow);
		
		if(filter==null) {
			autoCampingBoardList = autoCampingBoardDao.selectList(param);
		}else if(filter.equals("like")) {
			autoCampingBoardList = autoCampingBoardDao.selectLikeCount(param);
		}else if(filter.equals("readCount")) {
			autoCampingBoardList =autoCampingBoardDao.selectReadCount(param);
		}
		viewData.put("viewList", autoCampingBoardList);
		viewData.put("currentPage", pageNum);
		viewData.put("pageTotalCount", calPageTotalCount(totalPage));
		viewData.put("startPage", getStartPage(pageNum));
		viewData.put("endPage", getEndPage(pageNum));
		
		return viewData;
	} //게시글 리스트
	
	@Override
	public Map<String, Object> autoCampingBoardDetailView(int ac_num,int m_num) {
		
		Map<String, Object> detailView = new HashMap<String, Object>();
		Map<String, Object> likeCheck = new HashMap<String, Object>();
		int liketo = 0;
		likeCheck.put("ac_num", ac_num);
		likeCheck.put("m_num", m_num);
		if(autoCampingBoardLikeDao.selectOneLiketo(likeCheck)==0) {
			
		}else if(autoCampingBoardLikeDao.likeNo(likeCheck)==1) {
			liketo = 1 ;
		}
		detailView.put("likeCount", autoCampingBoardLikeDao.likeCount(ac_num));
		detailView.put("liketo", liketo);
		detailView.put("autoCamping_fileList", autoCampingFileDao.selectImgList(ac_num));
		detailView.put("autoCampingBoard", autoCampingBoardDao.selectOne(ac_num));
		return detailView;
	}//게시글 상세보기
	
	@Override
	public Map<String, Object> selectOne(int ac_num) {
		
		return autoCampingBoardDao.selectOne(ac_num);
	}//게시글 수정 폼
	
	@Override
	public boolean autoCampingBoardModify(AutoCampingBoard autoCampingBoard,List<String> fileList) {
		
		if(autoCampingBoardDao.updateAutoCampingBoard(autoCampingBoard)>0) {
			if(autoCampingFileDao.deleteAutoCampingFile(autoCampingBoard.getAc_num())) {
				for(int i=0; i<fileList.size(); i++) {
					autoCamping_imgFile.setAci_imgFileName(fileList.get(i));
					autoCamping_imgFile.setAci_subNume(i+1);
					autoCamping_imgFile.setAc_num(autoCampingBoard.getAc_num());
					autoCampingFileDao.insertAutoCampingFile(autoCamping_imgFile);	
				}
			}
			
			return true;
		}else {			
			return false;
		}
		
		
	}//게시글 수정
	
	
	@Override
	public List<Map<String, Object>> SearchAutoCampingInformation(String keyword, int pageNum) {
		List<Map<String, Object>> foodInformation = new ArrayList<Map<String,Object>>();
        try {
        	System.out.println("시작 페이지" + pageNum);
            String text = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/local.json?query="+text+"&display=10&start=1";
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
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
      
            
            JSONObject jsonObject = new JSONObject(response.toString());
            
            System.out.println("디스플레이" + jsonObject.getInt("display"));
			/*
			 * int totalPage = calPageTotalCount(jsonObject.getInt("total")); int startPage
			 * = getStartPage(jsonObject.getInt("start")); System.out.println("받아오는 시작페이지" +
			 * startPage); int endPage = getEndPage(pageNum);
			 */
			
            
            
            JSONArray items = jsonObject.getJSONArray("items");
   	
    		for(int i =0;i<items.length();i++) {
    		
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
				/*
				 * tempfoodInfor.put("totalPage", totalPage); tempfoodInfor.put("startPage",
				 * startPage); tempfoodInfor.put("endPage", endPage);
				 */
    			
				
    			foodInformation.add(tempfoodInfor);
    			
    		}

    		for(Map<String,Object> m : foodInformation) {
    			//System.out.println(m);
    		}
    		

    		
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
	public List<String> fileNameList(String text) {
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
	public boolean deleteAutoCampingBoard(int ac_num) {
		if(autoCampingBoardDao.deleteAutoCampingBoard(ac_num)) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> mainViewList(Map<String, Object> param) {
		
		return autoCampingBoardDao.selectList(param);
	}

	@Override
	public boolean readCountUpdate(int ac_num) {
		if(autoCampingBoardDao.readCountUpdate(ac_num)==1) {
			return true;
		}else {
			return false;
			
		}
	}	
}
