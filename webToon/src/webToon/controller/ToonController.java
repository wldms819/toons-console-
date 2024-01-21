package webToon.controller;

import java.util.List;
import java.util.Scanner;

import webToon.dto.MyFavoriteToonsVO;
import webToon.dto.ToonListVO;
import webToon.dto.ToonUserVO;
import webToon.model.ToonService;
import webToon.util.DateUtil;
import webToon.view.ToonView;

public class ToonController {
	public static String user_id = null;
	private static ToonService service = new ToonService();
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		boolean isRun = true;

		aa: while (isRun) {
			System.out.println("1.로그인 2.회원가입 3.프로그램 종료");
			System.out.print("작업 선택>> ");
			int job = sc.nextInt();
			if (job == 3) {
				ToonView.print("프로그램 종료");
				break;
			}
			switch (job) {
			// 로그인
			case 1: {
				if (login(sc) == 0)
					continue;
				else
					break;
			}
			// 회원가입
			case 2: {
				join(sc);
				continue;
			}
			default: {
				System.out.println("작업 번호를 다시 선택해주세요.");
				continue aa;
			}
			}

			bb: while (isRun) {
				System.out.println("1.웹툰검색 2.내 보관함 3.마이페이지 4.로그아웃");
				System.out.print("작업 선택>> ");
				job = sc.nextInt();
				// 4.로그아웃
				if (job == 4) {
					user_id = null;
					System.out.println("로그아웃 성공");
					break;
				}
				// 1.웹툰검색
				if (job == 1) {
					System.out.println("[웹툰검색]");
					System.out.println("1.제목으로 검색 2.작가명으로 검색 3.장르 검색 4.연재웹툰조회 5.완결웹툰조회 6.전체인기순 7.연재일별 인기순 8.home");
					System.out.print("작업 선택>> ");
					job = sc.nextInt();
					switch (job) {
					// 1.제목으로 검색
					case 1: {
						System.out.print("제목검색>> ");
						String toon_name = sc.next();
						List<ToonListVO> tList = service.selectByToonName(toon_name);
						ToonView.print(tList);
						if (tList.size() == 0) {
							System.out.println("올바른 제목으로 검색해주세요.");
						}
						break;
					}
					// 2.작가명으로 검색
					case 2: {
						System.out.print("작가명검색>> ");
						String toon_writer = sc.next();
						List<ToonListVO> tList = service.selectByToonWriter(toon_writer);
						ToonView.print(tList);
						if (tList.size() == 0) {
							System.out.println("올바른 작가명으로 검색해주세요.");
						}
						break;
					}
					// 3.장르 검색
					case 3: {
						System.out.print("장르검색>> ");
						String toon_genre = sc.next();
						List<ToonListVO> tList = service.selectByToonGenre(toon_genre);
						ToonView.print(tList);
						if (tList.size() == 0) {
							System.out.println("해당 장르는 없습니다.");
						}
						break;
					}
					// 4.연재웹툰조회
					case 4: {
						System.out.println("연재웹툰조회");
						List<ToonListVO> tList = service.selectByInSeriesToonList();
						ToonView.print(tList);
						break;
					}
					// 5.완결웹툰조회
					case 5: {
						System.out.println("완결웹툰조회");
						List<ToonListVO> tList = service.selectByCompletedToonList();
						ToonView.print(tList);
						break;
					}
					// 6.전체인기순
					case 6: {
						System.out.println("전체인기순");
						List<ToonListVO> tList = service.selectByMostLikes();
						ToonView.print(tList);
						break;
					}
					// 7.연재일별 인기순
					case 7: {
						System.out.println("연재일별 인기순");
						System.out.print("연재일>> ");
						String toon_serial_date = sc.next();
						List<ToonListVO> tList = service.selectBySerialDateMostLikes(toon_serial_date);
						ToonView.print(tList);
						if (tList.size() == 0) {
							System.out.println("연재일을 올바르게 입력해주세요.");
						}
						break;
					}
					// 8.home
					case 8: {
						System.out.println("home으로");
						break;
					}
					default: {
						System.out.println("작업 번호를 다시 선택해주세요.");
						break;
					}
					}
				}

				// 2.내 보관함
				else if (job == 2) {
					System.out.println("[내 보관함]");
					System.out.println("1.내 웹툰 목록 조회 2.내 웹툰 목록 추가 3.내 웹툰 목록 삭제 4.home");
					System.out.print("작업 선택>> ");
					job = sc.nextInt();
					switch (job) {
					// 1.내웹툰목록조회
					case 1: {
						System.out.println("내 웹툰 목록 조회");
						System.out.print("id>> ");
						String user_id = sc.next();
						List<ToonListVO> tList = service.selectMyToonList(user_id);
						// ToonView.print(tList);
						// System.out.println(tList.size()); //0
						if (service.selectUserId(user_id) <= 0) {
							ToonView.print("올바르지 않은 아이디 입니다. 아이디를 다시 입력해주세요.");
						} else {
							ToonView.print(tList);
							if (tList.size() == 0) {
								System.out.println("내 웹툰 목록을 추가해주세요.");
							}
						}

						break;
					}
					// 2.내웹툰목록추가
					case 2: {
						System.out.println("내 웹툰 목록 추가");
						System.out.print("id>> ");
						String user_id = sc.next();

						if (service.selectUserId(user_id) <= 0) {
							ToonView.print("올바르지 않은 아이디 입니다. 아이디를 다시 입력해주세요.");
						} else {

							int user_points = service.getMyToonPoints(user_id);
							// System.out.println("나의 포인트 : " + user_points);
							if (user_points < 1000) {
								System.out.println("포인트가 부족합니다. 충전을 해주세요.");
								continue bb;
							}
							
							System.out.print("toon_no>> ");
							String toon_no = sc.next();
							MyFavoriteToonsVO toon = new MyFavoriteToonsVO(user_id, toon_no);
							int count = service.insertMyToonList(toon);
							ToonView.print(count > 0 ? "insert성공" : "insert실패. 올바른 웹툰번호를 입력해주세요.");
						}
						break;
					}
					// 3.내웹툰목록삭제
					case 3: {
						System.out.println("내 웹툰 목록 삭제");
						System.out.print("id>> ");
						String user_id = sc.next();
						
						if (service.selectUserId(user_id) <= 0) {
							ToonView.print("올바르지 않은 아이디 입니다. 아이디를 다시 입력해주세요.");
						}
						else {
							System.out.print("toon_no>> ");
							String toon_no = sc.next();
							MyFavoriteToonsVO toon = new MyFavoriteToonsVO(user_id, toon_no);
							int count = service.deleteMyToonList(toon);
							ToonView.print(count > 0 ? "delete성공" : "delete실패. 올바른 웹툰번호를 입력해주세요.");
						}
						break;
					}
					// 4.home
					case 4: {
						System.out.println("home으로");
						break;
					}
					default: {
						System.out.println("작업 번호를 다시 선택해주세요.");
						break;
					}
					}
				}

				// 3.마이페이지
				else if (job == 3) {
					System.out.println("[마이페이지]");
					System.out.println("1.내 정보 조회 2.내 정보 수정 3.내 포인트 충전 4.회원탈퇴 5.home");
					System.out.print("작업 선택>> ");
					job = sc.nextInt();
					switch (job) {
					// 1.내 정보 조회
					case 1: {
						System.out.println("내 정보 조회");
						System.out.print("id>> ");
						String user_id = sc.next();
						List<ToonUserVO> tList = service.selectMyInfo(user_id);
						ToonView.print2(tList);
						if (tList.size() == 0) {
							System.out.println("id를 올바르게 입력해주세요.");
						}
						break;
					}
					// 2.내 정보 수정
					case 2: {
						System.out.println("내 정보 수정");
						System.out.print("id>> ");
						String user_id = sc.next();
						
						if (service.selectUserId(user_id) <= 0) {
							ToonView.print("올바르지 않은 아이디 입니다. 아이디를 다시 입력해주세요.");
						}
						else {
							System.out.print("pw번경>> ");
							String user_pw = sc.next();
							int count = service.updateMyInfo(user_id, user_pw);
							ToonView.print(count > 0 ? "내 정보 수정 성공" : "내 정보 수정 실패");
						}
						break;
					}
					// 3.내 포인트 충전
					case 3: {
						System.out.println("내 포인트 충전");
						System.out.print("id>> ");
						String user_id = sc.next();
						
						if (service.selectUserId(user_id) <= 0) {
							ToonView.print("올바르지 않은 아이디 입니다. 아이디를 다시 입력해주세요.");
						}
						else {
							System.out.print("충전할 포인트>> ");
							int charge_points = sc.nextInt();
							int count = service.insertMyPoints(user_id, charge_points);
							ToonView.print(count > 0 ? "내 포인트 충전 성공" : "내 포인트 충전 실패");
						}
						break;
					}
					// 4.회원탈퇴
					case 4: {
						System.out.println("회원을 탈퇴하시겠습니까?(Y/N)");
						String answer = sc.next();
						int count = 0;
						if (answer.equals("Y")) {
							System.out.print("id>> ");
							String user_id = sc.next();
							System.out.print("pw>> ");
							String user_pw = sc.next();
							count = service.deleteMyInfo(user_id, user_pw);
						}
						if (count > 0) {
							System.out.println("회원탈퇴 성공");
							continue aa;
						} else {
							System.out.println("회원탈퇴 실패. 회원탈퇴 시 올바른 아이디와 비밀번호를 입력해주세요.");
						}
						break;
					}
					// 5.home
					case 5: {
						System.out.println("home으로");
						break;
					}
					default: {
						System.out.println("작업 번호를 다시 선택해주세요.");
						break;
					}
					}
				}

				else {
					System.out.println("작업 번호를 다시 선택해주세요.");
				}
			}
		}
	}

	private static void join(Scanner sc) {
		System.out.print("id>> ");
		String user_id = sc.next();

		if (service.selectUserId(user_id) > 0) {
			ToonView.print("이미 가입되어 있습니다. 로그인하세요.");
			return;
		}

		System.out.print("pw>> ");
		String user_pw = sc.next();

		System.out.print("이름>> ");
		String user_name = sc.next();

		System.out.print("생년월일 ex)YYYY/MM/DD>> ");
		String user_birthDate = sc.next();

		ToonUserVO user = new ToonUserVO(user_id, user_pw, user_name, DateUtil.convertStringToDate(user_birthDate));
		int count = service.insertUser(user);
		ToonView.print(count > 0 ? "회원가입 성공" : "회원가입 실패");

	}

	private static int login(Scanner sc) {
		System.out.print("id>> ");
		String user_id = sc.next();

		if (service.selectUserId(user_id) <= 0) {
			ToonView.print("등록되지 않은 아이디 입니다. 회원가입 먼저 하세요.");
			return 0;
		}

		System.out.print("pw>> ");
		String user_pw = sc.next();

		if (service.login(user_id, user_pw) > 0) {
			ToonView.print("로그인 성공");
			return 1;
		} else {
			ToonView.print("로그인 실패");
			return 0;
		}
	}
}
