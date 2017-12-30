package com.naver.erp;

import java.util.List;

// 연락처 검색 화면에서 검색조건 데이터를 저장할 자바빈 선언
public class ContactSearchDTO {

	private List<Integer> saup_field; // 연락처사업 분야 저장
	private String min_reg_year; // 등록일 최소 연도 저장
	private String min_reg_month; // 등록일 최소 월 저장
	private String max_reg_year; // 등록일 최소 연도 저장
	private String max_reg_month; // 등록일 최소 월 저장
	private String keyword1; // 1번째 키워드 저장
	// ==========================================================
	// [검색 결과저장 DTO] 공통 속성변수 선언
	// ==========================================================
	// 한 화면에 보여지는 검색 결과 최대행 개수 저장, <주의> 반드시 초기값 입력할 것
	private int rowCntPerPage = 15;
	// 현재 선택된 페이지 번호 저장, <주의> 반드시 초기값 입력할 것
	private int selectPageNo = 1;
	// 정렬 데이터 저장, <참고> 필요에 따라 초기값 입력 가능
	private String sort;
	private int startRowNo = 1;

	public List<Integer> getSaup_field() {
		return saup_field;
	}

	public void setSaup_field(List<Integer> saup_field) {
		this.saup_field = saup_field;
	}

	public int getStartRowNo() {
		return startRowNo;
	}

	public void setStartRowNo(int startRowNo) {
		this.startRowNo = startRowNo;
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

	public int getRowCntPerPage() {
		return rowCntPerPage;
	}

	public void setRowCntPerPage(int rowCntPerPage) {
		this.rowCntPerPage = rowCntPerPage;
	}

	public int getSelectPageNo() {
		return selectPageNo;
	}

	public void setSelectPageNo(int selectPageNo) {
		this.selectPageNo = selectPageNo;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	// 검색 총 개수에 따른 속성변수 selectPageNo, startRowNo 안의 데이터 보정하기 메소드선언
	public void updateDTO(int searchListCnt) {

		if (searchListCnt > 0) {

			int lastPageNo = searchListCnt / this.rowCntPerPage;
			if (searchListCnt % this.rowCntPerPage > 0) {
				lastPageNo++;
			}
			if (lastPageNo < this.selectPageNo) {
				this.selectPageNo = 1;
			}

			this.startRowNo = this.selectPageNo * this.rowCntPerPage - this.rowCntPerPage + 1;

		}

	}

}
