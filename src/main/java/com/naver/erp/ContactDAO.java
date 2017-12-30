package com.naver.erp;

import java.util.List;
import java.util.Map;

public interface ContactDAO {

	List<Map<String, String>> getSaup_fieldList();

	int getContactSearchListCnt(ContactSearchDTO contactSearchDTO);

	List<Map<String, String>> getContactSearchList(ContactSearchDTO contactSearchDTO);

	int getContactPhoneCnt(ContactDTO contactDTO);

	int insertContact(ContactDTO contactDTO);
	
	int insertContactSaup_field(ContactDTO contactDTO);


}
