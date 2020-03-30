package dao;

import java.util.List;
import java.util.Map;

import model.GlampingBoard;

public interface GlampingBoardDao {

	public int insertGlBoard(GlampingBoard glampingBoard);
	//게시글 등록
	public List<String> selectList(Map<String, Object> params);
	//게시글 리스트 & 최신순
	public Map<String, Object> selectOne(int gl_num);
	//게시글 상세보기
	public int updateGlBoard(GlampingBoard glampingBoard);
	//게시글 수정
	public int deleteGlBoard(int gl_num);
	//게시글 삭제
	public Map<String, Object> selectDatailOne (int gl_num , int m_num);
	//게시글 수정폼
	public int selectBoardCount(Map<String, Object>param);
	//전체 게시글 수
	public List<String> selectReadCount(Map<String, Object> param);
	//조회수 순
	public List<String> selectLikeCount(Map<String, Object> params);
	//좋아요 순
	public int readCountUpdate(int gl_num);
}
