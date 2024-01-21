package webToon.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToonUserVO {

	private String user_id; 		//회원 아이디 
	private String user_pw; 		//회원 비밀번호
	private String user_name; 		//회원 이름	
	private Date user_birthDate; 	//회원 생일	
	private int user_points; 		//회원 포인트
	
	//회원가입시 필요한 생성자
	public ToonUserVO(String user_id, String user_pw, String user_name, Date user_birthDate) {
		super();
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.user_name = user_name;
		this.user_birthDate = user_birthDate;
	}

	@Override
	public String toString() {
		return "id=" + user_id + ", pw=" + user_pw + ", name=" + user_name
				+ ", birthday=" + user_birthDate + ", myPoints=" + user_points;
	}
}
