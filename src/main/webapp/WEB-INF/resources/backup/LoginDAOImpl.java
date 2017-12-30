package com.naver.erp;

import java.util.*;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
//[DAO 클래스]인 [LoginDAOImpl 클래스] 선언.
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// @Repository 를 붙임으로서 [DAO 클래스] 임를 지정하게되고, bean 태그로 자동 등록된다.
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
@Repository
public class LoginDAOImpl implements LoginDAO{

	//***************************************************
	// SqlSessionTemplate 객체를 생성해 속변 contactService에 저장
	//***************************************************
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	//***************************************************
	// [로그인 아이디, 암호의 존재 개수]를 리턴하는 메소드 선언
	//***************************************************
	public int getAdminCnt( Map<String, String> admin_id_pwd ){
		int adminCnt = 0;
		try{
			adminCnt = this.sqlSession.selectOne(    
				"com.naver.erp.dao.LoginDAO.getAdminCnt"
				, admin_id_pwd  
			);
		}catch(Exception ex){
			adminCnt = -1;
			System.out.println( "\n LoginDAOImpl.getAdminCnt 메소드 에러발생 ");
		}
		return  adminCnt;
	}
}
