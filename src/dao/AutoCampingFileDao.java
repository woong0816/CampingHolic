package dao;

import java.util.List;

import model.AutoCamping_imgFile;

public interface AutoCampingFileDao {
	//오토캠핑 이미지 등록
	public int insertAutoCampingFile(AutoCamping_imgFile autoCamping_imgFile);
	//오토캠핑 이미지 삭제
	public boolean deleteAutoCampingFile(int ac_num);
	//오토캠핑 이미지 리스트
	public List<String> selectImgList(int ac_num);
}
