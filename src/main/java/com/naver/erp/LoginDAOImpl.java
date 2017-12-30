package com.naver.erp;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDAOImpl implements LoginDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public int getAdminCnt(Map<String, String> admin_id_pwd) {

		int adminCnt = 0;

		try {
			adminCnt = this.sqlSession.selectOne("com.naver.erp.LoginDAO.getAdminCnt", admin_id_pwd);

		} catch (Exception ex) {
			adminCnt = -1;
			System.out.println("\n LoginDAOImpl.getAdminCnt 메소드 에러발생");
		}

		return adminCnt;

	}
}
