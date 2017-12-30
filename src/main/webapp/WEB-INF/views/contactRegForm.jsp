<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common.jsp"%>


<script>
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// HTML 입력양식의 유효성 체크하는 자바스크립트 함수 선언
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++
	function contactRegFormCheck( ){
		var cRFObj = "[name=contactRegForm]";		
		try{

			/* //---------------------------------------
			// 연락처 이미지의 크기 검사하기
			//---------------------------------------
			var contact_img = $(cRFObj+" [name=contact_img]").val( ); 
			var img = new Image();
			img.dynsrc = contact_img;
			alert( img.fileSize ); 
			 return
			 */
			
			
			//---------------------------------------
			// 연락처명 유효성 체크
			//---------------------------------------
			var contact_name = $(cRFObj+" [name=contact_name]").val( ); // 입력한 연락처명 가져오기
			// 만약 연락처명이 한글 또는 영어가 아니면 경고하고, 지우고 ,함수 멈추기
			if( new RegExp(/^[가-힣a-zA-Z]+$/).test(contact_name)==false  ){
			   alert( "연락처명는 공백 없이 영대소문자 또는 한글만 입력돼야 합니다.");
			   $(cRFObj+" [name=contact_name]").val( "" );
			   return;
			}
			//---------------------------------------
			// 전화번호 유효성 체크
			//---------------------------------------
			var phone = $(cRFObj+" [name=phone]").val( );   // 입력한 전화번호 가져오기
			// 만약 연락처명이 한글 또는 영어가 아니면 경고하고, 지우고 ,함수 멈추기
			if( new RegExp( /^[0-9]+$/ ).test(phone)==false ){
			   alert("전화번호는 - 없이 숫자만 입력 요망!");
			   $(cRFObj+" [name=phone]").val( "" );
			   return;
			}
			//---------------------------------------
			// 사업분야 유효성 체크
			//---------------------------------------
			// 사업분야 체크개수 가져오기
			var saup_fieldCnt = $(cRFObj+" [name=saup_field]").filter(":checked").length;
			// 사업분야 체크개수가 0개면 경고하고, 함수 멈추기
			if( saup_fieldCnt==0 ){
			   alert( "연락처 사업분야는 반드시 1개 이상 체크해야합니다.");
			   return;
			}
			if(confirm("정말 등록할까요")==false ){ return; }
			//alert( $(cRFObj).serialize( ) ); return;
			
			
			//**********************************************
			// 현재 화면에서 페이지 이동 없이 서버쪽 "/erp/contactRegProc.do"  을 호출하여 
			// [연락처 입력 행 적용 개수]가 있는 html 소스를 받는다.
			//**********************************************
			$.ajax({
				// ----------------------------
				// 호출할 서버쪽 URL 주소 설정
				// ----------------------------
				url : "/erp/contactRegProc.do"    
				// ---------------------------- 
				// 전송 방법 설정
				// ----------------------------
				,type : "post"
				//---------------------------------
				// 서버에 보낼 파라미터명과 파라미터값을 설정
				//---------------------------------
				,data : $(cRFObj).serialize( )
				//---------------------------------
				// 서버의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정.
				// 익명함수의 매개변수 data 에는 contactRegProc.jsp 의 실행 결과물인 html 소스가 문자열로 들어옴.
				//---------------------------------
				,success : function( data ){
					// 매개변수 data 에 저장된 HTML 소스의 화면 출력 결과물을 꺼내기
					//alert(data)
					var contactRegCnt = data;  //$(data).text( ); 
					
					// 전화번호, 연락처명의 중복 개수가 0 개면
					if( contactRegCnt==1 ){ 
					    alert("연락처 입력 성공!");
						$(cRFObj).find("input,select,textarea").not(":checkbox,:radio,[type=button]").val("");
						$(cRFObj).find(":checked").click( );						
						$("[name=contactSearchForm] [name=is_show_contactRegFormDiv]").val("yes");					    
					    document.contactSearchForm.submit( );
					}
					// 전화번호, 연락처명의 중복 개수가 1 개 이상이면
					else if( contactRegCnt==0 ){ 
					    alert( "[연락처명]과 [전화번호]가 이미 존재함다.\n연락처명 또는 전화전호를 바꾸세요");
					    $(cRFObj+" [name=contact_name],[name=phone]").val("");
					}
					else{ 
					    //alert( data );
					    alert( "관리자에게 문의 바람!" );
					}
				}
				//---------------------------------
				// 서버의 응답을 못 받았을 경우 실행할 익명함수 설정.
				//---------------------------------
				,error : function(  ){	 
					alert("서버접속 실패! 관리자에게 문의 바람!");
				}
			});
		}catch(e){
			alert( "contactRegFormCheck( ) 함수 예외 발생!" );
		}
	}
	//***********************************************************
	// class=contactRegFormDiv 가진 div 태그 안보이게 하는 함수선언
	//***********************************************************
	function  hideContactRegFormDiv(  ){	
		if( $("[name=is_open_contactRegFormDiv]").length==1 ){ 
			$("[name=is_open_contactRegFormDiv]").val(0);
		}		
		//document.contactRegForm,reset();
		$(".contactRegFormDiv").hide();
	}	
</script>

<center>
<!--########################################################  enctype="multipart/form-data"-->
<form name="contactRegForm" method="post"  action="/erp/contactRegProc.do">
<!--########################################################-->
<b>[연락처 등록]</b>
<table class="tbcss1" cellpadding="5" bordercolor="gray">
	<tr> 
		<th>연락처명
		<td><input type="text" name="contact_name" size="15">
	<tr> 
		<th>전화
		<td><input type="text" name="phone" size="15">
	<tr> 
		<th>사업분야
		<td>
		   <!----------------------------------------->
		   <!--HttpServletRequest 객체에 saup_fieldList 라는 킷값으로 저장된 List<Map> 객체에 저장된 -->
		   <!--[사업분야번호]와 [사업분야이름]을 꺼내어 checkbox 양식에 name 속성값과 value 속성값으로 표현해 출력하기-->
		   <!----------------------------------------->
		   <c:forEach var="saup_field" items="${requestScope.saup_fieldList}">
				<input type="checkbox" name="saup_field"  value="${saup_field.saup_field_code}">${saup_field.saup_field_name}
		   </c:forEach>
	<tr>
		<th>이미지
		<td><input type="file" name="contact_img" size="7" 
			onChange="$('.contact_img').attr(  'src', $('[name=img_name]').val( )  );">
	     	<img src="" class="contact_img"  height="30">
	</tr>
</table>
<table><tr height=2><td></table>

<input type="button" value="   등록   " onClick="contactRegFormCheck( )">
<span style="cursor:pointer" onclick="hideContactRegFormDiv();">[닫기]</span>
<span style="cursor:pointer" class="logout" onclick="logout(  )">[로그아웃]</span>
<!--########################################################-->
</form>
<!--########################################################-->
</center>
<script>	
	/* $("[name=contactRegForm] [name=contact_name]").val("vvv");
	$("[name=contactRegForm] [name=phone]").val("1234566");
	$("[name=contactRegForm] [name=saup_field]:eq(0)").click(); */
</script>
