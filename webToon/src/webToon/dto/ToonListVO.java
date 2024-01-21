package webToon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToonListVO {

	private String toon_no; 			//웹툰 no : ex) 연재중(S001), 완결(C001)
	private String toon_name; 			//웹툰 이름	
	private String toon_writer; 		//웹툰 작가	
	private String toon_genre; 			//웹툰 장르
	private String toon_serial_date; 	//연재일 : 월,화,수,목,금,토,일
	private String toon_serial_type; 	//연재타입 : ex) 연재중, 완결
	private int toon_likes; 			//좋아요 수	
	
	@Override
	public String toString() {
		return toon_no + ", 제목:" + toon_name + ", 작가명:" + toon_writer
				+ ", 장르:" + toon_genre + ", 연재일:" + toon_serial_date + ", 연재타입:"
				+ toon_serial_type + ", 좋아요 수:" + toon_likes;
	}
	
	
	
}
