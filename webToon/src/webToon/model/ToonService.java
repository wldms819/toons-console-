package webToon.model;

import java.util.List;

import webToon.dto.MyFavoriteToonsVO;
import webToon.dto.ToonListVO;
import webToon.dto.ToonUserVO;

public class ToonService {

	ToonDAO dao = new ToonDAO();

	// toonUser 테이블에 해당 user_id가 있는지 조회
	// user_id가 있으면 로그인
	// user_id가 없으면 회원가입
	public int selectUserId(String user_id) {
		return dao.selectUserId(user_id);
	}

	// 회원가입
	public int insertUser(ToonUserVO user) {
		return dao.insertToonUser(user);
	}

	// 로그인
	public int login(String user_id, String user_pw) {
		return dao.login(user_id, user_pw);
	}

	// [웹툰검색]
	// 1.제목으로 웹툰 검색
	public List<ToonListVO> selectByToonName(String toon_name) {
		return dao.selectByToonName(toon_name);
	}

	// 2.작가명으로 검색
	public List<ToonListVO> selectByToonWriter(String toon_writer) {
		return dao.selectByToonWriter(toon_writer);
	}

	// 3.장르 검색
	public List<ToonListVO> selectByToonGenre(String toon_genre) {
		return dao.selectByToonGenre(toon_genre);
	}

	// 4.연재웹툰조회
	public List<ToonListVO> selectByInSeriesToonList() {
		return dao.selectByInSeriesToonList();

	}

	// 5.완결웹툰조회
	public List<ToonListVO> selectByCompletedToonList() {
		return dao.selectByCompletedToonList();

	}

	// 6.전체인기순
	public List<ToonListVO> selectByMostLikes() {
		return dao.selectByMostLikes();
	}

	// 7.연재일별 인기순
	public List<ToonListVO> selectBySerialDateMostLikes(String toon_serial_date) {
		return dao.selectBySerialDateMostLikes(toon_serial_date);
	}

	// [내 보관함]
	// 1.내 웹툰 목록 조회
	public List<ToonListVO> selectMyToonList(String user_id) {
		return dao.selectMyToonList(user_id);
	}

	// 2.내보관함에 웹툰 추가
	public int insertMyToonList(MyFavoriteToonsVO toon) {
		return dao.insertMyToonList(toon);
	}

	// 3.내보관함에 웹툰 삭제
	public int deleteMyToonList(MyFavoriteToonsVO toon) {
		return dao.deleteMyToonList(toon);
	}

	// [마이페이지]
	// 1.내 정보 조회
	public List<ToonUserVO> selectMyInfo(String user_id) {
		return dao.selectMyInfo(user_id);
	}

	// 2.내 정보 수정
	public int updateMyInfo(String user_id, String user_pw) {
		return dao.updateMyInfo(user_id, user_pw);
	}

	// 3.내 포인트 충전
	public int insertMyPoints(String user_id, int charge_points) {
		return dao.insertMyPoints(user_id, charge_points);
	}

	// 4.회원탈퇴
	public int deleteMyInfo(String user_id, String user_pw) {
		return dao.deleteMyInfo(user_id, user_pw);
	}

	public int getMyToonPoints(String user_id) {
		return dao.getMyToonPoints(user_id);
	}
}
