package com.naver.erp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService{

	@Autowired
	private LoginDAO loginDAO;
	
	public int getAdminCnt( Map<String, String> admin_id_pwd){
		int adminCnt = 0;
		
//		if(admin_id_pwd.get("admin_id").equals("abc") && admin_id_pwd.get("pwd").equals("123")){
//			adminCnt = 1;
//		}
		
		try{
			adminCnt = this.loginDAO.getAdminCnt(admin_id_pwd);
			
		}catch(Exception ex){
			adminCnt = -1;
			System.out.println("\n LoginServiceImpl.getAdminCnt 메소드 에러발생");
		}
		
		return adminCnt;
	}
	
	
}
