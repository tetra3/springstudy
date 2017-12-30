package com.naver.erp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

	@Autowired
	private ContactService contactService;

	// [ContactController 클래스] 내부의 @RequestMapping(~) 이 붙은 메소드가 호출되기 전에 자동으로 호출되는 메소드 선언 - 재사용위한, 중복코드를 제거
	@ModelAttribute("saup_fieldList")
	public List<Map<String, String>> getSaup_fieldList(){
		
		List<Map<String, String>> saup_fieldList = this.contactService.getSaup_fieldList();
		
		System.out.println("==========================================");
		System.out.println(saup_fieldList);
		//[{saup_field_code=1, saup_field_name=IT}, {saup_field_code=2, saup_field_name=통신}, {saup_field_code=3, saup_field_name=금융}, {saup_field_code=4, saup_field_name=기타}]
		System.out.println("==========================================");
		
		return saup_fieldList;	
	}
	
	@ModelAttribute("saup_fieldMap")
	public Map<String, String> getSaup_fieldMap(){
		
		List<Map<String, String>> saup_fieldList = getSaup_fieldList();
		
		Map<String, String> saup_fieldMap = new HashMap<String, String>();
		
		for(Map<String, String> tmp : saup_fieldList){
			saup_fieldMap.put(tmp.get("saup_field_code"), tmp.get("saup_field_name"));
		}
		return saup_fieldMap;
	}

	@RequestMapping(value = "/contactSearchForm3.do")
	public ModelAndView contactSearchForm3(@ModelAttribute("contactSearchDTO") ContactSearchDTO contactSearchDTO) {
		
		
		System.out.println("hello contactSearchForm3");
		// 객체 생성
		ModelAndView mav = new ModelAndView();
		// 객체 호출 JSP 페이지명 지정
		mav.setViewName("contactSearchForm3");

		try {

			// 검색 총 개수
			int contactSearchListCnt = this.contactService.getContactSearchListCnt(contactSearchDTO);

			// 총 개수에 따라 달라지는 선택한 페이지 번호 보정
			// 선택한 페이지 번호에 따른 시작행 번호 구하기
			contactSearchDTO.updateDTO(contactSearchListCnt);

			// 객체 메소드 호출로 검색 연락처 목록 얻기
			List<Map<String, String>> contactSearchList = this.contactService.getContactSearchList(contactSearchDTO);

			// 검색 총 개수 저장
			mav.addObject("contactSearchListCnt", contactSearchListCnt);
			// 검색 연락처 결과물 저장
			mav.addObject("contactSearchList", contactSearchList);

			// 개발자를 위해 ContactSearchDTO 객체의 속성변수에 저장된 데이터를 도스창에 출력하기
			// 선택 페이지를 번호를 보정 한 후 출력해야 하므로 서두에 안나온다.

			// contactSearchDTO.print_info();

		} catch (Exception e) {

			System.out.println("ContactController.contactSearchForm3 메소드 호출 시 에러발생");
		}

		return mav;
	}
	@RequestMapping(value="/contactRegForm.do")
	public String contactRegForm() {
		return "contactRegForm";
	}
	
	
	@RequestMapping(
			value="/contactRegProc.do",
			method=RequestMethod.POST,
			produces="application/json; charset=UTF-8"
	) 
	@ResponseBody
	public int insertContact(@ModelAttribute("contactDTO") ContactDTO contactDTO) {
		
		System.out.println("hello insertContact");
		contactDTO.print_info();
		int contactRegCnt = 0;
		try {
			
			contactRegCnt = this.contactService.insertContact(contactDTO);
			
		}catch(Exception e) {
			e.printStackTrace();
			
			System.out.println("ContactController.inserContact(~) 메소드 예외 발생!");
			contactRegCnt = -1;
		}
		
		
		return contactRegCnt;
	
	}
	

	//*****************************************************
	// /erp/setOpenedContact.do 로 접근시 호출되는 메소드 선언
	//*****************************************************
	@RequestMapping(                       
		value = "/setOpenedContact.do"                //=>클의 접속 URL 설정
		, method=RequestMethod.POST                   //=>클이 파라미터를 보내는 방법은 post로 설정. 즉 post 방식으로 보낸 데이터만 받겠다는 의미
		, produces="application/json;charset=UTF-8"   //=>클이 응답받을 수 있는 데이터 형식과 문자섹 지정. 
	)
	@ResponseBody                                     //=>메소드의 리턴값을 JSON 으로 변경하여 클에게 전송하는 어노테이션 설정
	public int setOpenedContact( @RequestParam(value="contact_no") String contact_no, HttpSession session){
		ArrayList<String> contact_noList = (ArrayList<String>)session.getAttribute("contact_noList");  
		if(contact_noList==null){
			contact_noList = new ArrayList<String>();
		}
		for( int i=0 ; i<contact_noList.size(); i++ ){
			if( contact_noList.get(i).equals(contact_no) ){ return 1; }
		}
		contact_noList.add(contact_no);
		session.setAttribute( "contact_noList",contact_noList );
		System.out.println( "session 객체의 contact_noList 키값에 데이터 저장 성공..." );
		return 1;	
	}

	//*****************************************************
	// /erp/getOpenedContact.do 로 접근시 호출되는 메소드 선언
	//*****************************************************
	@RequestMapping(                       
		value = "/getOpenedContact.do"                //=>클의 접속 URL 설정
		, method=RequestMethod.POST                   //=>클이 파라미터를 보내는 방법은 post로 설정. 즉 post 방식으로 보낸 데이터만 받겠다는 의미
		, produces="application/json;charset=UTF-8"   //=>클이 응답받을 수 있는 데이터 형식과 문자섹 지정. 
	)
	@ResponseBody                                     //=>메소드의 리턴값을 JSON 으로 변경하여 클에게 전송하는 어노테이션 설정
	public List<String> getOpenedContact( HttpSession session){
		List<String> contact_noList = (List<String>)session.getAttribute("contact_noList");
		try{
			if(contact_noList!=null ) { return contact_noList; }
			else                      { return null; }			
		}catch(Exception ex){
			return null;
		}
	}
	

	//*****************************************************
	// /erp/deleteOpenedContact.do 로 접근시 호출되는 메소드 선언
	//*****************************************************
	@RequestMapping(                       
		value = "/deleteOpenedContact.do"             //=>클의 접속 URL 설정
		, method=RequestMethod.POST                   //=>클이 파라미터를 보내는 방법은 post로 설정. 즉 post 방식으로 보낸 데이터만 받겠다는 의미
		, produces="application/json;charset=UTF-8"   //=>클이 응답받을 수 있는 데이터 형식과 문자섹 지정. 
	)
	@ResponseBody                                     //=>메소드의 리턴값을 JSON 으로 변경하여 클에게 전송하는 어노테이션 설정
	public int deleteOpenedContact( @RequestParam(value="contact_no") String contact_no, HttpSession session){
		List<String> contact_noList = (List<String>)session.getAttribute("contact_noList" );  
		if(contact_noList!=null){
			for( int i=0 ; i<contact_noList.size();i++){
				String tmp = contact_noList.get(i);
				if(tmp.equals(contact_no)){ contact_noList.remove(i--);  }
			}
			session.setAttribute( "contact_noList",contact_noList );
			System.out.println( "session 객체의 contact_noList 키값에 데이터 삭제 성공..." );
		}
		return 1;
	}

	

}
