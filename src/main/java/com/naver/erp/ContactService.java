package com.naver.erp;

import java.util.List;
import java.util.Map;

public interface ContactService {

	// 컴파일시 public이 없으면 자동으로 들어간다.

	List<Map<String, String>> getSaup_fieldList();

	int getContactSearchListCnt(ContactSearchDTO contactSearchDTO);

	List<Map<String, String>> getContactSearchList(ContactSearchDTO contactSearchDTO);

	int getContactPhoneCnt(ContactDTO contactDTO);
	
	int insertContact(ContactDTO contactDTO);
	
	

}
