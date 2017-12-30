package com.naver.erp;

import java.util.*;

import javax.servlet.http.HttpSession;

import com.naver.erp.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
// 가상 URL 주소로 접속하면 호출되는 메소드를 소유한 [컨트롤러 클래스] 선언. 
//		@Controller 를 붙임으로서 [컨트롤러 클래스]임을 지정한다.
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
@Controller
public class LoginController {
	
	//*****************************************************
	// 속성변수 loginService 선언하고,이를 구현한 [LoginServiceImpl 객체]를 생성해 저장	
	//*****************************************************
		// @Autowired 이 붙은 속성변수에는 인터페이스 자료형을 쓰고 
		// 이 인터페이스를 구현한 클래스를 객체화하여 저장한다.
	//*****************************************************
	@Autowired
	private LoginService loginService;
		
	//*****************************************************
	// 가상주소 /erp/loginForm.do 로 접속하면 호출되는 메소드 선언.
	//*****************************************************
		// [컨트롤러 클래스]의 @RequestMapping 이 붙은 메소드의 리턴형이 String 일 경우
		// 리턴하는 문자열이 이동할 JSP 페이지명 이다.
	//*****************************************************
	@RequestMapping(value="/loginForm.do" )
	public String loginForm( HttpSession session ){
		//--------------------------------------
		// HttpSession 객체에 저장된 [로그인 아이디], [수정 연락처 번호] 제거하기
		//--------------------------------------
		session.removeAttribute("admin_id");		
		session.removeAttribute("contact_noList");	
		//session.removeAttribute("selectMenubarNo");
		/*//--------------------------------------
		// HttpSession 객체를 무력화 시켜 getId() 이외의 메소드 호출 불가능으로 만들기
		// <주의> invalidate() 메소드는 HttpSession 객체를 제거하는 것이 아님.
		//--------------------------------------
		session.invalidate();*/
		return "loginForm";
	}
	
/*	
	//*****************************************************
	// 가상주소 /erp/loginProc.do 로 접속하면 호출되는 메소드 선언.
	//*****************************************************
		// 매개변수가 HttpSession 객체일 경우 웹서버가 생성한 HttpSession 객체이다.
		// 매개변수가@RequestParam( "파라미터명" )  String 매개변수 일 경우 
		// 		파라미터명에 해당하는 파라미터값이 매개변수로 들어온다.
	//*****************************************************
	@RequestMapping(value="/loginProc.do",method=RequestMethod.POST )
	public ModelAndView loginProc( 
		//-----------------------------------------------------
		// [HttpSession 객체]가 저장된 매개변수 선언
		//-----------------------------------------------------
		HttpSession session

		//-----------------------------------------------------
		// admin_id 라는 파라미터명의 파라미터값을 저장한 String 형 매개변수 선언
		//-----------------------------------------------------
			//-----------------------------------------------------------------------------------------------------------------------
			// @RequestParam( value="파라미터명", required=true|false, defaultValue="디폴트값" )   자료형  파라미터값저장할매개변수명
			//-----------------------------------------------------------------------------------------------------------------------
				// [파라미터명]의 [파리미터값]을 [파라미터값저장할매개변수명]에 저장한다.
				// required=true           => 파라미터명이 없을 경우 에러발생. 메소드안의 코딩은 한줄도 실행 안된다. 생략 가능.
				// required=false          => 파라미터명이 없을 경우 null 값을 매개변수에 저장. 만약 매개변수 자료형이 기본형이면 에러 발생.
				// defaultValue="디폴트값" => 파라미터값이 없을 경우 디폴트값을 매개변수에 저장. required=false 가 있어야함. 
				//					  		 생략 시 매개변수에는 null 저장됨.
				//-----------------------------------------------------	
		,@RequestParam( value="admin_id" )  String admin_id
		
		//-----------------------------------------------------
		// pwd 라는 파라미터명의 파라미터값을 저장한 String 형 매개변수 선언
		//-----------------------------------------------------
		,@RequestParam( value="pwd" )       String pwd

	){
		//-------------------------------------------------
		// ModelAndView 객체 생성하기
		//-------------------------------------------------
		ModelAndView mav = new ModelAndView();	
		//-------------------------------------------------
		// ModelAndView 객체에 [호출 JSP 페이지명]을 저장하기
		//-------------------------------------------------
		mav.setViewName("loginProc");
		try{
			//-------------------------------------------------
			// HashMap 객체에 [로그인 아이디, 암호] 저장하기
			//-------------------------------------------------
			Map<String, String> admin_id_pwd = new HashMap<String, String>();
			admin_id_pwd.put("admin_id", admin_id);
			admin_id_pwd.put("pwd", pwd);
			//-----------------------------------
			// loginServiceImpl 객체의 getAdminCnt( admin_id_pwd ) 메소드 호출로 [로그인 아이디의 존재 개수]를 얻기
			//-----------------------------------
			int adminCnt = this.loginService.getAdminCnt( admin_id_pwd );
			//-----------------------------------
			// [로그인 아이디의 존재 개수]가 1이면 Session 객체에 로그인 아이디 저장하기
			//-----------------------------------
			if(adminCnt==1 ){
				session.setAttribute( "admin_id", admin_id );
			}
			//-----------------------------------
			// ModelAndView 객체에 호출 JSP 페이지에 반영할 [로그인 아이디의 존재 개수] 저장		
			//-----------------------------------
			mav.addObject( "adminCnt", adminCnt );
			System.out.println( "LoginController.loginProc(~) 실행 완료!" );
		}catch(Exception ex){
			mav.addObject( "adminCnt", -1 );
			System.out.println( "LoginController.loginProc(~) 에서 에러발생" );
		}
		return mav;
	}
*/	
	
	
	//*****************************************************
	// 가상주소 /erp/loginProc.do 로 접속하면 호출되는 메소드 선언.
	//*****************************************************
		// 매개변수가 HttpSession 객체일 경우 웹서버가 생성한 HttpSession 객체이다.
		// 매개변수가@RequestParam( "파라미터명" )  String 매개변수 일 경우 
		// 		파라미터명에 해당하는 파라미터값이 매개변수로 들어온다.
	//*****************************************************
	@RequestMapping(value="/loginProc.do",method=RequestMethod.POST) //, produces="application/json;charset=UTF-8"
	@ResponseBody
	public int loginProc( 
		//-----------------------------------------------------
		// [HttpSession 객체]가 저장된 매개변수 선언
		//-----------------------------------------------------
		HttpSession session

		//-----------------------------------------------------
		// admin_id 라는 파라미터명의 파라미터값을 저장한 String 형 매개변수 선언
		//-----------------------------------------------------
			//-----------------------------------------------------------------------------------------------------------------------
			// @RequestParam( value="파라미터명", required=true|false, defaultValue="디폴트값" )   자료형  파라미터값저장할매개변수명
			//-----------------------------------------------------------------------------------------------------------------------
				// [파라미터명]의 [파리미터값]을 [파라미터값저장할매개변수명]에 저장한다.
				// required=true           => 파라미터명이 없을 경우 에러발생. 메소드안의 코딩은 한줄도 실행 안된다. 생략 가능.
				// required=false          => 파라미터명이 없을 경우 null 값을 매개변수에 저장. 만약 매개변수 자료형이 기본형이면 에러 발생.
				// defaultValue="디폴트값" => 파라미터값이 없을 경우 디폴트값을 매개변수에 저장. required=false 가 있어야함. 
				//					  		 생략 시 매개변수에는 null 저장됨.
				//-----------------------------------------------------	
		,@RequestParam( value="admin_id" )  String admin_id
		
		//-----------------------------------------------------
		// pwd 라는 파라미터명의 파라미터값을 저장한 String 형 매개변수 선언
		//-----------------------------------------------------
		,@RequestParam( value="pwd" )       String pwd
	){
		int adminCnt = 0;
		try{
			//-------------------------------------------------
			// HashMap 객체에 [로그인 아이디, 암호] 저장하기
			//-------------------------------------------------
			Map<String, String> admin_id_pwd = new HashMap<String, String>();
			admin_id_pwd.put("admin_id", admin_id);
			admin_id_pwd.put("pwd", pwd);
			//-----------------------------------
			// loginServiceImpl 객체의 getAdminCnt( admin_id_pwd ) 메소드 호출로 [로그인 아이디의 존재 개수]를 얻기
			//-----------------------------------
			adminCnt = this.loginService.getAdminCnt( admin_id_pwd );
			//-----------------------------------
			// [로그인 아이디의 존재 개수]가 1이면 Session 객체에 로그인 아이디 저장하기
			//-----------------------------------
			if(adminCnt==1 ){
				session.setAttribute( "admin_id", admin_id );
			}
			System.out.println( "LoginController.loginProc(~) 실행 완료!" );
		}catch(Exception ex){
			System.out.println( "LoginController.loginProc(~) 에서 에러발생" );
			adminCnt = -1;
		}
		return adminCnt;
	}
	
	/*
	//****************************************************************** 
	// 위 loginProc(~) 메소드는 아래 처럼도 가능
	//******************************************************************
	@RequestMapping(
		value="/loginProc.do"
		,method=RequestMethod.POST 
		,produces="application/json;charset=UTF-8"
	)
	@ResponseBody
	public int loginProc( 
			HttpSession session
			,@RequestParam Map<String,String> paramsMap
	){
		int adminCnt = 0;
		try{
			int adminCnt = this.loginService.getAdminCnt( paramsMap );
			if(adminCnt==1){
				session.setAttribute( "admin_id", paramsMap.get("admin_id") );
			}
		}catch(Exception ex){
			System.out.println( "LoginController.loginProc(~) 에서 에러발생" );
			adminCnt = -1;
		}
		return adminCnt;
	}	
	
	//****************************************************************** 
	// 위 loginProc(~) 메소드는 아래 처럼도 가능
	//******************************************************************
	@RequestMapping(value="/loginProc.do",method=RequestMethod.POST )
	public ModelAndView loginProc( 
			HttpSession session
			,@RequestParam Map<String,String> paramsMap
	){
		ModelAndView mav = new ModelAndView();	
		mav.setViewName("loginProc");
		try{
			int adminCnt = this.loginService.getAdminCnt( paramsMap );
			if(adminCnt==1 ){
				session.setAttribute( "admin_id", paramsMap.get("admin_id") );
			}
			mav.addObject( "adminCnt", adminCnt );
		}catch(Exception ex){
			mav.addObject( "adminCnt", -1 );
			System.out.println( "LoginController.loginProc(~) 에서 에러발생" );
		}
		return mav;
	}
	//******************************************************************
	 */
	
	/*
	//******************************************************************
	// 위 loginProc(~) 메소드는 아래 처럼도 가능
	//******************************************************************
	// 		웹서버가 html이 아닌 JSON 객체로 응답하려면 다음 처럼 한다. 
	//		다만 이 메소드 호출하는 클라이언트 쪽도 파라미터명과 파라미터값을 JSON 형태의 문자열로 전송해야한다.
	//		특정 패턴에 맞게 설계되어 있어야한다.
	//******************************************************************
	@RequestMapping(value="/loginProc.do",method=RequestMethod.POST )
	public @ResponseBody Map<String,Object> loginProc( 
			@RequestBody Map<String,Object> id_pwd
	){
		System.out.println( "(String)id_pwd.get('admin_id')=>"+(String)id_pwd.get("admin_id") );
		System.out.println( "(String)id_pwd.get('pwd')=>"+(String)id_pwd.get("pwd") );
		Map<String, Object> resultMap = null;
		try{
			Map<String, String> id_pwd2 = new HashMap<String, String>();
			id_pwd2.put("admin_id", (String)id_pwd.get("admin_id"));
			id_pwd2.put("pwd", (String)id_pwd.get("pwd"));
			int adminCnt = this.loginService.getAdminCnt( id_pwd2 );
			resultMap =  new HashMap<String,Object>( );
			resultMap.put("adminCnt", adminCnt+"");
		}catch(Exception ex){
			System.out.println( "loginProc(~) 에서 에러발생" );
		}
		return resultMap;
	} 
	//******************************************************************
	*/	
}





















