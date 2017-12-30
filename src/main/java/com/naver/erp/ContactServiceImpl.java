package com.naver.erp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional // 입력, 수정, 삭제 메서드에만 적용한다.
public class ContactServiceImpl implements ContactService {

	@Autowired // 속성변수에 붙은 자료형인 [인터페이스]를 구현한 [클래스]를 객체화하여 저장한다.
	private ContactDAO contactDAO;

	public List<Map<String, String>> getSaup_fieldList() {

		List<Map<String, String>> saup_fieldList = this.contactDAO.getSaup_fieldList();

		return saup_fieldList;
	}

	public int getContactSearchListCnt(ContactSearchDTO contactSearchDTO) {

		int contactListTotCnt = this.contactDAO.getContactSearchListCnt(contactSearchDTO);

		return contactListTotCnt;
	}

	public List<Map<String, String>> getContactSearchList(ContactSearchDTO contactSearchDTO) {

		List<Map<String, String>> contactSearchList = this.contactDAO.getContactSearchList(contactSearchDTO);

		return contactSearchList;
	}

	@Override
	public int getContactPhoneCnt(ContactDTO contactDTO) {
		return contactDAO.getContactPhoneCnt(contactDTO);
	}

	@Override
	public int insertContact(ContactDTO contactDTO) {
		
		int contactPhoneCnt = this.contactDAO.getContactPhoneCnt(contactDTO);
		
		if(contactPhoneCnt > 0) {
			return 0;
		}
		
		int contactRegCnt = this.contactDAO.insertContact(contactDTO);
		if(contactRegCnt != 1) {
			return -1;
		}
		
				
		return contactDAO.insertContactSaup_field(contactDTO);
	}

}
