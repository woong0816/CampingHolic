package service;

import java.util.List;
import java.util.Map;

import model.GlampingBoard;

public interface GlampingService {

	public List<Map<String, Object>> SearchGlampingInformation(String keyword,int pageNum);
	//주소 찾기
	public boolean insertGlampingBoard(GlampingBoard glampingBoard,List<String> fileList);
	//글램핑 게시글 작성
	public List<String> fileNameList (String text);
	//파일리스트 태그 제거 반환
	public Map<String, Object> selectList(Map<String, Object>param ,int pageNum,String filter);
	//글램핑 게시글 리스트
	public Map<String, Object> selectOne(int gl_num);
	//글램핑 게시글 수정폼
	public Map<String, Object> GlampingBoardDetailView(int m_num,int gl_num);
	//글램핑 게시글 수정 
	public boolean modifyGlampingBoard(GlampingBoard glampingBoard,List<String> fileList);
	//글램핑 게시글 삭제
	public boolean deleteGlampinBoard(int gl_num);
	//게시글 이미지 썸네일 가져오기
	public String selectThumbnail(int gl_num);
	//메인화면 리스트
	public List<String> mainViewList(Map<String, Object> param);
	//조회수 업데이트
	public boolean readCountUpdate(int gl_num);
}