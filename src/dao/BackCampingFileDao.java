package dao;

import java.util.List;

import model.BackCamping_imgFile;

public interface BackCampingFileDao {

	//백캠핑 이미지 파일 등록
	public int insertBackCampingFile(BackCamping_imgFile backCamping_imgFile);
	//백캥핌 이미지 파일 삭제
	public boolean deleteBackCampingFile(int bc_num);
	//백패킹 이미지파일 리스트
	public List<String> selectImgList(int bc_num);
} 
