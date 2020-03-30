package dao;

import java.util.List;

import model.Glamping_ImgFile;

public interface GlampingFileDao {

	public int insertglampingImgFile(Glamping_ImgFile glamping_imgFile);
	//게시글 작성시에 파일위치 이름 저장
	public boolean deleteglampingImgFIle(int gl_num);
	//게시글 수정시에 다른 이미지 정보를 저장
	public String selectThumbnail(int gl_num);
	//게시글에 대한 이미지 리스트
	public List<String> selectImgList(int gl_num);
}
