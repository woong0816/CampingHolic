package dao;

import java.util.List;
import java.util.Map;

import model.BackCampingBoard;

public interface BackCampingBoardDao {
	
	public int insertBackCampingBoard(BackCampingBoard backCampingBoard);
	//게시글 작성
	public List<String> selectList(Map<String, Object> searchParam);
	//게시글 리스트
	public Map<String, Object> selectOne(int bc_num);
	//게시글 상세보기
	public boolean updateBackCampingBoard(BackCampingBoard backCampingBoard);
	//게시글 수정
	public boolean deleteBackCampingBoard(int bc_num);
	//게시글 삭제
	public int selectBoardCount(Map<String, Object> param);
	//총 게시글 수
	public List<String> selectReadCount(Map<String, Object> param);
	//조회수 순
	public List<String> selectLikeCount(Map<String, Object> param);
	//좋아요 순
	public int readCountUpdate(int bc_num);
	
}
