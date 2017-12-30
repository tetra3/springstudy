package com.naver.erp;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ContactDAOImpl implements ContactDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public List<Map<String, String>> getSaup_fieldList() {

		List<Map<String, String>> saup_fieldList = sqlSession.selectList("com.naver.erp.ContactDAO.getSaup_fieldList");

		return saup_fieldList;
	}

	public int getContactSearchListCnt(ContactSearchDTO contactSearchDTO) {

		// 마이바티스 SQL 구문 설정파일 (mapper_contact.xml)에서 네임스페이스 태그 내부 1행 데이터를 얻어와 리턴
		int contactListTotCnt = sqlSession.selectOne("com.naver.erp.ContactDAO.getContactSearchListCnt",
				contactSearchDTO);

		return contactListTotCnt;
	}

	public List<Map<String, String>> getContactSearchList(ContactSearchDTO contactSearchDTO) {

		List<Map<String, String>> contactList = sqlSession.selectList("com.naver.erp.ContactDAO.getContactSearchList",
				contactSearchDTO);

		return contactList;
	}
}
