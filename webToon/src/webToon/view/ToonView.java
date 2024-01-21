package webToon.view;

import java.util.List;

import webToon.dto.ToonListVO;
import webToon.dto.ToonUserVO;


public class ToonView {

	public static void print(String message) {
		System.out.println(message);
	}
	
	public static void print(List<ToonListVO> toonList) {
		System.out.println("----------웹툰 목록----------");
		toonList.stream().forEach(listInfo->{
			System.out.println(listInfo);
		});
		System.out.println("---------------------------");
	}

	public static void print2(List<ToonUserVO> toonUser) {
		System.out.println("----------내 정보----------");
		toonUser.stream().forEach(userInfo->{
			System.out.println(userInfo);
		});
		System.out.println("---------------------------");
		
	}
}
