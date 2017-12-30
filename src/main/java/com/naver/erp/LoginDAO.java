package com.naver.erp;

import java.util.Map;


public interface LoginDAO {

	int getAdminCnt( Map<String, String> admin_id_pwd);
}
