package com.naver.erp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
//import java.util.Map;
//import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.SessionAttribute;

//===========================================================================
// 가상 URL 주소로 접속하면 호출되는 메소드를 소유한 [컨트롤러 클래스] 선언.
// @Controller 를 붙임으로서 [컨트롤러 클래스]임을 지정한다.
//===========================================================================

@Controller
public class LoginController {

	@Autowired // 클래스를 객체화 시키고 변수에 넣어라. (자동화)
	private LoginService loginService;

	// ===========================================================================
	// 가상주소 /erp/loginForm.do 로 접속하면 호출되는 메소드 선언.
	// ===========================================================================
	// [컨트롤러 클래스]의 @RequestMapping 이 붙은 메소드의 리턴형이 String 일경우
	// 리턴하는 문자열이 이동할 JSP 페이지명 이다.

	@RequestMapping(value = "/loginForm.do")
	public String loginForm(HttpSession session) { // 매개변수로 웹서버가 생성한 HttpSession
													// 객체의 메위주를 넘겨준다.
		// ===========================================================================
		// HttpSession 객체에 저장된 로그인 아이디, 수정 연락처 번호 제거하기
		// ===========================================================================
		session.removeAttribute("admin_id");
		session.removeAttribute("contact_noList");

		return "loginForm";

	}
	
//	@RequestMapping(value = "/jdbcConnTest.do", method = RequestMethod.GET)
//	@ResponseBody
//	public String jdbcConnTest(){
//		
//		String result = "1";
//		
//		try {
//            // 1. 드라이버 로딩
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            System.out.println("드라이버 로딩 성공");
//            
//        // forName의 인자로 전달된 주소에 드라이버가 없을 경우
//        } catch (ClassNotFoundException e) {
//            System.out.println("드라이버 로딩 실패");
//        }
//        
//        try {
//            // 오라클DB에 연결
//            Connection conn = DriverManager.getConnection(
//                    "jdbc:oracle:thin:@127.0.0.1:1521:XE", "custom" , "1111");
//            //<property name="url" value="jdbc:oracle:thin:@127.0.0.1:1521:XE" />
//            System.out.println("커넥션 성공");
//            
//            // 실제 사용 코드
//            
//            // 커넥션은 반드시 닫아주어야 한다.
//            conn.close();
//            System.out.println("커넥션 종료");
//        // 오라클 DB에 연결이 실패하였을때
//        } catch (SQLException e) {
//            System.out.println("커넥션 실패");
//        } 
//		
//		
//		return result; 
//	}
	

//	@RequestMapping(value = "/loginProc.do", method = RequestMethod.POST)
//	@ResponseBody // 리턴할 데이터를 다이렉트로 보낸다.
//	// public @ResponseBody int loginProc(){
//	public int loginProc(@RequestParam(value = "admin_id") String admin_id, @RequestParam(value = "pwd") String pwd,
//			HttpSession session) {
//
//		int adminCnt = 0;
//		try {
//			Map<String, String> admin_id_pwd = new HashMap<String, String>();
//			
//			admin_id_pwd.put("admin_id", admin_id);
//			admin_id_pwd.put("pwd", pwd);
//			
//			adminCnt = this.loginService.getAdminCnt(admin_id_pwd);
//			
//			if (adminCnt == 1) {
//				
//				session.setAttribute("admin_id", admin_id);
//				
//			}
//			
//			System.out.println("LoginController.loginProc(~) 실행 완료");
//
//		} catch (Exception ex) {
//			System.out.println("LoginController.loginProc(~) 에서 에러 발생");
//			System.out.println(ex.getMessage());
//			adminCnt = -1;
//		}
//
//		return adminCnt;
//	}

	
	@RequestMapping(value = "/loginProc.do", method = RequestMethod.POST)
	public @ResponseBody int loginProc(@RequestParam Map<String, String> map,
			HttpSession session) {

		int adminCnt = 0;
		
		try {
			
			adminCnt = this.loginService.getAdminCnt(map);
			
			if (adminCnt == 1) {
				
				session.setAttribute("admin_id", map.get("admin_id"));
				
			}

		} catch (Exception ex) {
			System.out.println("LoginController.loginProc(~) 에서 에러 발생");
			System.out.println(ex.getMessage());
			adminCnt = -1;
		}

		return adminCnt;
	}
	
	
	// ===========================================================================
	// @RequestMapping(value="/loginForm.do")
	//
	// public ModelAndView loginForm() {
	//
	//
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("loginForm");
	// return mav;
	//
	// }
	//
	//
	// public String loginForm(Model model) {
	//
	// model.addAttribute("msg"," 언제 집에가죠....");
	//
	// return "loginForm";
	// }
	//
	//
	// @RequestMapping(value="/loginProc.do")
	// public String loginProc(
	// Model model
	// , @RequestParam Map<String, String> map
	// ) {
	//
	// String admin_id = map.get("admin_id");
	// String pwd = map.get("pwd");
	//
	// if(admin_id != null && pwd != null && admin_id.equals("abc") &&
	// pwd.equals("123")) {
	// model.addAttribute("admin_id",admin_id);
	// return "main";
	// } else{
	// return "loginForm";
	// }
	//
	//
	//
	// }

	/*
	 * public String loginProc( Model model , @RequestParam(value="admin_id")
	 * String admin_id , @RequestParam(value="pwd") String pwd ) {
	 * 
	 * if(admin_id != null && pwd != null && admin_id.equals("abc") &&
	 * pwd.equals("123")) { model.addAttribute("admin_id",admin_id); return
	 * "main"; } else{ return "loginForm"; }
	 * 
	 * 
	 * 
	 * }
	 */
	// ===========================================================================
}
