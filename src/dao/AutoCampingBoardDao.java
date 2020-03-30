package dao;

import java.util.List;
import java.util.Map;

import model.AutoCampingBoard;

public interface AutoCampingBoardDao {

	public int insertAutoCampingBoard(AutoCampingBoard autoCampingBoard);	
	//게시글 등록
	public List<String> selectList(Map<String, Object> param);
	//게시글 상세보기 폼
	public Map<String, Object> selectOne(int ac_num);
	//게시글 수정
	public int updateAutoCampingBoard(AutoCampingBoard autoCampingBoard);
	//게시글 삭제
	public boolean deleteAutoCampingBoard(int ac_num);
	//게시글 총 개수
	public int selectBoardCount(Map<String, Object> param);
	//조회수 정렬
	public List<String> selectReadCount(Map<String, Object> param);
	//좋아요 정렬
	public List<String> selectLikeCount(Map<String, Object> param);
	//조회수 업데이트
	public int readCountUpdate(int ac_num);
}	
