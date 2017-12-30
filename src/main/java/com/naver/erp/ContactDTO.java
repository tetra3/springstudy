


package com.naver.erp;
import java.util.List;

public class ContactDTO{
	//*****************************************************
	// 속성변수 선언
	//*****************************************************
	private int contact_no;             // 연락처 번호 저장
	private String contact_name;        // 연락처명 저장
	private String phone;               // 전화번호 저장
	private String reg_date;            // 등록일 저장	
	private List<Integer> saup_field;	// 연락처 사업분야 저장


	//*****************************************************
	// 메소드 선언
	//*****************************************************
	public int getContact_no() {
		return contact_no;
	}
	public void setContact_no(int contact_no) {
		this.contact_no = contact_no;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		System.out.println("\n<전화>"+ phone);
		this.phone = phone;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public List<Integer> getSaup_field() {
		return saup_field;
	}
	public void setSaup_field(List<Integer> saup_field) {
		System.out.println("\n<사업분야>"+saup_field);
		this.saup_field = saup_field;
	}	
	
	//*****************************************************
	// BoardDTO 클래스 안의 속성변수값을 도스창에 출력하는 메소드선언.
	//*****************************************************
	public void print_info(){
		System.out.println("\n---------------------------------");
		System.out.println("ContactDTO 속성변수 데이터");
		System.out.println("---------------------------------");
		System.out.println("contact_no=>"+ contact_no);
		System.out.println("phone=>"+phone);
		System.out.println("reg_date=>"+reg_date);
		if(saup_field!=null){
			String str = "";
			for( int i=0 ; i<saup_field.size(); i++){
				str = str + saup_field.get(i) + " ";
			}
			System.out.println("saup_field=>" + str);
		}else{
			System.out.println("saup_field=>");
		}
		System.out.println("---------------------------------");
	}	
}
