package com.naver.erp;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
// 연락처 검색 화면에서 입력한 데이터를 저장할 자바빈 선언 
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//----------------------------------------------
	// [검색 화면] 관련 자바빈 선언 시 주의 사항
	//----------------------------------------------
		//<1>[검색 화면] 구현에 반드시 있어야할 데이터가 저장되는 속변에는 디폴트 값을 저장한다. 예>선택페이지번호, 화면당 보일 검색 결과행의 개수
		//    검색 화면 처음 접속 시 자바빈의 속변에 데이터가없을 경우 [검색 화면] 구현 시 에러가 발생하기 때문.
		//<2>검색 SQL 구문에 반드시 삽입되는 속변에는 디폴트 값을 저장한다. <예>order by 구문에 삽입될 속변 등
	//----------------------------------------------
public class ContactSearchDTO{
	//*****************************************************
	// 속성변수 선언
	//*****************************************************
	private List<Integer> saup_field;// 연락처사업 분야 저장
	private String min_reg_year;     // 등록일 최소 연도 저장
	private String min_reg_month;    // 등록일 최소 월 저장
	private String max_reg_year;     // 등록일 최소 연도 저장
	private String max_reg_month;    // 등록일 최소 월 저장
	private String keyword1;         // 1번째 키워드 저장
	//-------------------------------
	// [검색 결과저장 DTO] 공통 속성변수 선언
	//-------------------------------
	private int rowCntPerPage=15;    // 한 화면에 보여지는 검색 결과 최대행 개수 저장. <주의>반드시 초기값 입력할 것.
	private int selectPageNo=1;      // 현재 선택된 페이지 번호 저장.  <주의>반드시 초기값 입력할 것.
	//-------------------------------
	private String sort;          // 정렬 데이터 저장. <참고>필요에 따라 초기값 입력 가능.   
	private int startRowNo=1;

	//*****************************************************
	// 공통 [DTO] 메소드 선언
	//*****************************************************	
	public List<Integer> getSaup_field() {
		return saup_field;
	}
	public void setSaup_field(List<Integer> saup_field) {
		this.saup_field = saup_field; 
	}
	public String getMin_reg_year() {
		return min_reg_year;
	}
	public void setMin_reg_year(String min_reg_year) {
		this.min_reg_year = min_reg_year; 
	}
	public String getMin_reg_month() {
		return min_reg_month;
	}
	public void setMin_reg_month(String min_reg_month) {
		this.min_reg_month = min_reg_month; 
	}
	public String getMax_reg_year() {
		return max_reg_year;
	}
	public void setMax_reg_year(String max_reg_year) {
		this.max_reg_year = max_reg_year;
	}
	public String getMax_reg_month() {
		return max_reg_month;
	}
	public void setMax_reg_month(String max_reg_month) {
		this.max_reg_month = max_reg_month;
	}
	public String getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}
	//-------------------------------
	// [검색 결과저장 DTO] 공통 setter/getter 메소드 선언
	//-------------------------------
	public int getRowCntPerPage() {
		return rowCntPerPage;
	}
	public void setRowCntPerPage(int rowCntPerPage) {
		this.rowCntPerPage = rowCntPerPage;
	}
	//---------
	public int getSelectPageNo() {
		return selectPageNo;
	}
	public void setSelectPageNo(int selectPageNo) {
		this.selectPageNo = selectPageNo;
	}
	//---------
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		//this.sort = sort.toUpperCase();
		this.sort = sort;
	}
	//---------
	public int getStartRowNo() {
		return startRowNo;
	}
	public void setStartRowNo(int startRowNo) {
		this.startRowNo = startRowNo;
	}
	
	//*****************************************************
	// [검색 총 개수]에 따른 속성변수 selectPageNo, startRowNo 안의 데이터 보정하기 메소드선언.
	//*****************************************************
	public void updateDTO( int searchListCnt ) {
		if(searchListCnt>0) {
			int lastPageNo = searchListCnt/this.rowCntPerPage;
			if( searchListCnt%this.rowCntPerPage>0 ){ lastPageNo++; }
			if( lastPageNo<this.selectPageNo ){
				this.selectPageNo = 1;
			}
			this.startRowNo = this.selectPageNo*this.rowCntPerPage-this.rowCntPerPage+1;
		}
	}
	
	//*****************************************************
	// ContactSearchDTO 클래스 안의 속성변수값을 도스창에 출력 메소드선언.
	//*****************************************************
	public void print_info( ){
		//Date date = new Date();  //.toString() )
		Calendar calender = Calendar.getInstance();
		String date = calender.get(Calendar.YEAR)+"-"+(calender.get(Calendar.MONTH)+1)+"-"+calender.get(Calendar.DATE)
						+"__"+ calender.get(Calendar.HOUR_OF_DAY)+":"+ calender.get(Calendar.MINUTE)+":"+ calender.get(Calendar.SECOND);
		System.out.println("\n---------------------------------");
		System.out.println(date);
		System.out.println("ContactSearchDTO 속성변수 데이터");
		System.out.println("---------------------------------");
		
		if(saup_field!=null){
			String str = "";
			for( int i=0 ; i<saup_field.size(); i++){
				str = str + saup_field.get(i) + " ";
			}
			System.out.println("saup_field=>" + str);
		}else{
			System.out.println("saup_field=>");
		}	
		System.out.println("min_reg_year=>"+min_reg_year);
		System.out.println("min_reg_month=>"+min_reg_month);
		System.out.println("max_reg_month=>"+max_reg_month);
		System.out.println("keyword1=>"+keyword1);
		System.out.println("selectPageNo=>"+selectPageNo);
		System.out.println("rowCntPerPage=>"+rowCntPerPage);
		System.out.println("시작행=>"+(selectPageNo*rowCntPerPage-rowCntPerPage+1) );
		System.out.println("sort=>"+sort);
		System.out.println("---------------------------------");
	}
	//---------------------------------------------	
}