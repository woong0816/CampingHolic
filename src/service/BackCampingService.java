package service;

import java.util.List;
import java.util.Map;

import model.BackCampingBoard;

public interface BackCampingService {

	public List<Map<String, Object>> SearchBackCampingInformation(String keyword,int pageNum);
	//다음 주소 검색
	public List<String> fileTag(String text);
	//이미지 리스트 추출
	public boolean insertBackCampingBoard(BackCampingBoard backCampingBoard, List<String>fileList);
	//게시글 작성
	public Map<String, Object> selectList(Map<String, Object> serchParam,int pageNum, String filter);
	//게시글 리스트
	public Map<String, Object> selectOne(int bc_num);
	//게시글 수정폼 
	public boolean updateBackCampingBoard(BackCampingBoard backCampingBoard, List<String>fileList);
	//게시글 수정
	public boolean deleteBackCampingBoard(int bc_num);
	//게시글 상세보기
	public Map<String, Object> backCampingBoardDetailView(int bc_num, int m_num);
	//메인화면 리스트
	public List<String> mainViewList(Map<String, Object> param);
	//조회수 증가
	public boolean readCountUpdate(int bc_num);
	
}
