package service;

import java.util.List;
import java.util.Map;

import model.AutoCampingBoard;

public interface AutoCampingService {
	//주소검색
	public List<Map<String, Object>> SearchAutoCampingInformation(String keyword, int pageNum);
	//게시글 등록
	public boolean insertAutoCampingBoard(AutoCampingBoard autoCampingBoard,List<String> fileList);
	//파일 태그 분류
	public List<String> fileNameList (String text);
	//게시글 리스트
	public Map<String, Object> selectList(Map<String, Object> serchParam,int pageNum, String filter);
	//게시글 상세보기 폼
	public Map<String, Object> autoCampingBoardDetailView(int ac_num,int m_num);
	//게시글 수정 폼 
	public Map<String, Object> selectOne(int ac_num);
	//게시글 수정
	public boolean autoCampingBoardModify(AutoCampingBoard autoCampingBoard,List<String> fileList);
	//게시글 삭제
	public boolean deleteAutoCampingBoard(int ac_num);
	//메인 화면 리스트
	public List<String> mainViewList(Map<String, Object> param);
	//게시글 조회수 업데이트
	public boolean readCountUpdate(int ac_num);
}
