package webToon.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import webToon.dto.MyFavoriteToonsVO;
import webToon.dto.ToonListVO;
import webToon.dto.ToonUserVO;
import webToon.util.DBUtil;

public class ToonDAO {

	Connection conn;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
	int resultCount;

	// toonUser 테이블에 해당 user_id가 있는지 조회
	public int selectUserId(String user_id) {
		String sql = "select * from toonuser where user_id =? ";
		conn = DBUtil.getConnection();

		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, user_id);
			resultCount = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}

		return resultCount;
	}

	// 로그인
	public int login(String userId, String userPw) {
		String sql = "select user_pw from toonuser where user_id =? ";
		conn = DBUtil.getConnection();

		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, userId);
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getString("user_pw").equals(userPw)) {
					resultCount = 1;
				} else {
					resultCount = 0;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return resultCount;
	}

	// 회원가입
	public int insertToonUser(ToonUserVO user) {
		String sql = "insert into toonuser(user_id, user_pw, user_name, user_birthdate) values(?, ?, ?, ?)";
		int count = 0;
		conn = DBUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUser_id());
			pst.setString(2, user.getUser_pw());
			pst.setString(3, user.getUser_name());
			pst.setDate(4, user.getUser_birthDate());
			count = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	// [웹툰검색]
	// 1.제목으로 웹툰 검색
	public List<ToonListVO> selectByToonName(String toon_name) {
		List<ToonListVO> toonList = new ArrayList<>();
		String sql = "select * from toonlist where toon_name like '%'||?||'%' "; // toon_name이 가변으로 ?에 온다.
		conn = DBUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, toon_name);
			rs = pst.executeQuery();
			while (rs.next()) {
				ToonListVO toon = makeToonList(rs); // resultset에서 읽어서 VO 만들기
				toonList.add(toon); // 그리고 ArrayList에 담기
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}

		return toonList;
	}

	// 2.작가명으로 검색
	public List<ToonListVO> selectByToonWriter(String toon_writer) {
		List<ToonListVO> toonList = new ArrayList<>();
		String sql = "select * from toonlist where toon_writer like '%'||?||'%' "; // toon_writer가 가변으로 ?에 온다.
		conn = DBUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, toon_writer);
			rs = pst.executeQuery();
			while (rs.next()) {
				ToonListVO toon = makeToonList(rs); // resultset에서 읽어서 VO 만들기
				toonList.add(toon); // 그리고 ArrayList에 담기
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return toonList;
	}

	// 3.장르 검색
	public List<ToonListVO> selectByToonGenre(String toon_genre) {
		List<ToonListVO> toonList = new ArrayList<>();
		String sql = "select * from toonlist where toon_genre like '%'||?||'%' "; // toon_genre가 가변으로 ?에 온다.
		conn = DBUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, toon_genre);
			rs = pst.executeQuery();
			while (rs.next()) {
				ToonListVO toon = makeToonList(rs); // resultset에서 읽어서 VO 만들기
				toonList.add(toon); // 그리고 ArrayList에 담기
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return toonList;
	}

	// 4.연재웹툰조회
	public List<ToonListVO> selectByInSeriesToonList() {
		List<ToonListVO> toonList = new ArrayList<>();
		String sql = "select * from toonlist where toon_no like 'S%'";
		conn = DBUtil.getConnection();

		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				ToonListVO toon = makeToonList(rs);
				toonList.add(toon);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}

		return toonList;
	}

	// 5.완결웹툰조회
	public List<ToonListVO> selectByCompletedToonList() {
		List<ToonListVO> toonList = new ArrayList<>();
		String sql = "select * from toonlist where toon_no like 'C%'";
		conn = DBUtil.getConnection();

		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				ToonListVO toon = makeToonList(rs);
				toonList.add(toon);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}

		return toonList;
	}

	// 6.전체인기순
	public List<ToonListVO> selectByMostLikes() {
		List<ToonListVO> toonList = new ArrayList<>();
		String sql = "select * from toonlist order by toon_likes desc";
		conn = DBUtil.getConnection();

		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				ToonListVO toon = makeToonList(rs);
				toonList.add(toon);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}

		return toonList;
	}

	// 7.연재일별 인기순
	public List<ToonListVO> selectBySerialDateMostLikes(String toon_serial_date) {
		List<ToonListVO> toonList = new ArrayList<>();
		String sql = "select * "
				+ "from toonlist "
				+ "where toon_serial_date = ? "
				+ "and toon_no like 'S%' "
				+ "order by toon_likes desc";
		conn = DBUtil.getConnection();

		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, toon_serial_date);
			rs = pst.executeQuery();
			while (rs.next()) {
				ToonListVO toon = makeToonList(rs);
				toonList.add(toon);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}

		return toonList;
	}

	// [내 보관함]
	// 1.내 웹툰 목록 조회
	public List<ToonListVO> selectMyToonList(String user_id) {
		List<ToonListVO> MyToonList = new ArrayList<>();
		String sql = "select distinct myfavoritetoons.user_id, toonlist.toon_no, toonlist.toon_name, toonlist.toon_writer, toonlist.toon_genre, toonlist.toon_serial_date, toonlist.toon_serial_type, toonlist.toon_likes "
				+ "from toonlist, myfavoritetoons " 
				+ "where toonlist.toon_no = myfavoritetoons.toon_no "
				+ "and myfavoritetoons.user_id = ? " 
				+ "order by toonlist.toon_no";
		conn = DBUtil.getConnection();

		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, user_id);
			rs = pst.executeQuery();
			while (rs.next()) {
				ToonListVO toon = makeToonList(rs);
				MyToonList.add(toon);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return MyToonList;
	}

	// 2.내보관함에 웹툰 추가
	public int insertMyToonList(MyFavoriteToonsVO toon) {
		String sql = "insert into myfavoritetoons values(?, ?)";
		int count = 0;
		conn = DBUtil.getConnection();

		try {
			pst = conn.prepareStatement(sql); // SQL문 준비
			pst.setString(1, toon.getUser_id());
			pst.setString(2, toon.getToon_no());
			count = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return count;
	}

	// 3.내보관함에 웹툰 삭제
	public int deleteMyToonList(MyFavoriteToonsVO toon) {
		String sql = "delete from myfavoritetoons where user_id = ? and toon_no = ?"; // 지웠으면 1, 못지웠으면 0
		int count = 0;
		conn = DBUtil.getConnection();

		try {
			pst = conn.prepareStatement(sql); // SQL문 준비
			pst.setString(1, toon.getUser_id()); // ?에 값을 채운다.
			pst.setString(2, toon.getToon_no());
			count = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return count;
	}

	// [마이페이지]
	// 1.내 정보 조회
	public List<ToonUserVO> selectMyInfo(String user_id) {
		List<ToonUserVO> toonUserInfo = new ArrayList<>();
		String sql = "select * from toonuser where user_id = ?";
		conn = DBUtil.getConnection();

		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, user_id);
			rs = pst.executeQuery();
			while (rs.next()) {
				ToonUserVO userInfo = makeUser(rs);
				toonUserInfo.add(userInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}

		return toonUserInfo;
	}

	// 2.내 정보 수정
	public int updateMyInfo(String user_id, String user_pw) {
		String sql = "update toonuser set user_pw = ? where user_id = ?";
		int count = 0;
		conn = DBUtil.getConnection();

		try {
			pst = conn.prepareStatement(sql); // SQL문 준비
			pst.setString(1, user_pw); // ?에 값을 채운다.
			pst.setString(2, user_id);
			count = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return count;
	}

	// 3.내 포인트 충전
	public int insertMyPoints(String user_id, int charge_points) {
		String sql = "insert into ChargeToonPoints values(?, ?)";
		int count = 0;
		conn = DBUtil.getConnection();

		try {
			pst = conn.prepareStatement(sql); // SQL문 준비
			pst.setString(1, user_id); // ?에 값을 채운다.
			pst.setInt(2, charge_points);
			count = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return count;
	}

	// 4.회원탈퇴
	public int deleteMyInfo(String user_id, String user_pw) {
		String sql = "delete from toonuser where user_id = ? and user_pw = ? "; // 지웠으면 1, 못지웠으면 0
		int count = 0;
		conn = DBUtil.getConnection();

		try {
			pst = conn.prepareStatement(sql); // SQL문 준비
			pst.setString(1, user_id); // ?에 값을 채운다.
			pst.setString(2, user_pw);
			count = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return count;
	}

	// 이미 위에서 SQLException 처리했으니까 던지면 됨
	private ToonListVO makeToonList(ResultSet rs) throws SQLException {
		ToonListVO toonInfo = new ToonListVO();
		toonInfo.setToon_no(rs.getString("toon_no"));
		toonInfo.setToon_name(rs.getString("toon_name"));
		toonInfo.setToon_writer(rs.getString("toon_writer"));
		toonInfo.setToon_genre(rs.getString("toon_genre"));
		toonInfo.setToon_serial_date(rs.getString("toon_serial_date"));
		toonInfo.setToon_serial_type(rs.getString("toon_serial_type"));
		toonInfo.setToon_likes(rs.getInt("toon_likes"));
		return toonInfo;
	}

	private ToonUserVO makeUser(ResultSet rs) throws SQLException {
		ToonUserVO toonUserInfo = new ToonUserVO();
		toonUserInfo.setUser_id(rs.getString("user_id"));
		toonUserInfo.setUser_pw(rs.getString("user_pw"));
		toonUserInfo.setUser_name(rs.getString("user_name"));
		toonUserInfo.setUser_birthDate(rs.getDate("user_birthDate"));
		toonUserInfo.setUser_points(rs.getInt("user_points"));
		return toonUserInfo;
	}
	
	private ToonUserVO getUserPoints(ResultSet rs) throws SQLException {
		ToonUserVO toonUserInfo = new ToonUserVO();
		toonUserInfo.setUser_points(rs.getInt("user_points"));
		return toonUserInfo;
	}

	//회원이 가진 포인트 가져오기 
	public int getMyToonPoints(String user_id) {
		List<ToonUserVO> toonUserInfo = new ArrayList<>();
		String sql = "select user_points from toonuser where user_id = ?";
		conn = DBUtil.getConnection();

		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, user_id);
			rs = pst.executeQuery();
			while (rs.next()) {
				ToonUserVO userInfo = getUserPoints(rs);
				toonUserInfo.add(userInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}

		return toonUserInfo.get(0).getUser_points();
	}
	
	
}
