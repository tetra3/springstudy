<!--********************************************************-->
<!--JSP 페이지 처리 방식 선언-->
<!--********************************************************-->
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common.jsp"%>

<!--********************************************************-->
<c:set var="menubarStyle" value="height:20;cursor:pointer;" />
<!--********************************************************-->

<!--********************************************************-->
<!--[메뉴바] 관련 배경색, 글자색 저장 자바 변수를 EL로 선언->
<!--********************************************************-->
<!--#8F8F8F #787878 black  white-->

<!----------------------------->
<c:set var="menubarBgC_mOut" value="#2C5885" />    
<c:set var="menubarBgC_mOver" value="#4684C1" />
<c:set var="menubarFontC" value="#FCFDFE" /> 
<!----------------------------->
<c:set var="menubarBgC_mOut" value="#A1A1A1" />    
<c:set var="menubarBgC_mOver" value="#646464" />
<c:set var="menubarFontC" value="white" /> 
<!----------------------------->
<c:set var="menubarBgC_mOut" value="black" /> 
<c:set var="menubarBgC_mOver" value="#8F8F8F" />
<c:set var="menubarFontC" value="white" />
<!----------------------------->
<c:set var="menubarBgC_mOut" value="#EA4D00" />    
<c:set var="menubarBgC_mOver" value="#993200" />
<c:set var="menubarFontC" value="#E1EBF4" /> 
<!----------------------------->
<c:set var="menubarBgC_mOut" value="#004040" />    
<c:set var="menubarBgC_mOver" value="#008080" />
<c:set var="menubarFontC" value="#E1EBF4" /> 


<!--********************************************************-->
<!--[메뉴바] 출력->
<!--********************************************************-->
<%-- <br><br>
<table width=100% bgcolor="${menubarBgC_mOut}" cellpadding="0" cellspacing="0">
	<tr><td>
		<table class=menubar bgcolor="${menubarBgC_mOut}" align="center" cellpadding="3">
			<tr>
				<!---------------------------------------------------------->
				<th style="cursor:pointer;" onclick="location.href='/erp/contactSearchForm1.do';">
					<b style="color:${menubarFontC}">&nbsp;&nbsp;&nbsp;연락처&nbsp;&nbsp;&nbsp;</b>
				<!---------------------------------------------------------->
				<th style="cursor:pointer;" onclick="location.href='/erp/boardListForm.do';">
					<b style="color:${menubarFontC}">&nbsp;&nbsp;&nbsp;게시판&nbsp;&nbsp;&nbsp;</b>
				<!---------------------------------------------------------->
				<th style="cursor:pointer;" onclick="logout(  );"> 
					<b style="color:${menubarFontC}">&nbsp;&nbsp;&nbsp;로그아웃&nbsp;&nbsp;&nbsp;</b>
				<!---------------------------------------------------------->
		</table>
</table> --%>

<!--********************************************************-->
<!--[메뉴바] 관련 자바스크립트 함수 선언-->
<!--********************************************************-->
<script>		
	//***********************************************************************
	// 로그 아웃 함수 선언
	//***********************************************************************
	function logout(  ){
		if( confirm("정말로그 아웃 하시겠습니까?") ){
			location.replace('/erp/loginForm.do');
		}
	}

	//***********************************************************************
	// 메뉴바 출력 함수 선언
	//***********************************************************************
	function printMenubar(  
		menunbarBgColor 
		,mouseoverBgColor
		,mouseoverFontColor
		,mouseoutBgColor
		,mouseoutFontColor
		,menuArr
	){
		//-------------------------------------	
		// 메뉴바 출력하기
		//-------------------------------------	
		var menubars = [];
		/* menubars.push("<style>");
		menubars.push(".tbcss0, .tbcss0 td, .tbcss0 th{ ");
		menubars.push("	border-collapse: collapse; ");
		menubars.push("	border:0px solid gray;");
		menubars.push("	font-size: 9pt;
		menubars.push("	font-family: tahoma,굴림,돋움,verdana; ");
		menubars.push(" }");
		menubars.push("</style>"); */
		
		menubars.push("<table border=0 width='100%' bgcolor='"+menunbarBgColor+"' cellpadding=0 cellspacing=0><tr><td>");
		//---
		menubars.push("<table border=0 class='menubar' bgcolor='"+mouseoutBgColor+"' align='center' cellpadding=3");
		menubars.push("<tr>");		
		for( var i=0 ; i<menuArr.length; i++ ){
			menubars.push("<th style='cursor:pointer;' onclick=\"location.href='"+menuArr[i][0]+"';\">");
			menubars.push(   "&nbsp;&nbsp;&nbsp;"+menuArr[i][1]+"&nbsp;&nbsp;&nbsp;");
		}
		menubars.push("</table></table>");
		document.write( menubars.join("") );
		//-------------------------------------	
		// 현재 선택된 메뉴바의 인덱스 번호 얻기
		//-------------------------------------	
		var choicedURL = location.href;
		var choicedMenunarIdx = 0;
		//---
		var menubarThObjs = $(".menubar th");
		menubarThObjs.each(function(i){
			var onclickV = $(this).attr("onclick");
			var url = onclickV.split("\'")[1];
			//alert(url);
			if( choicedURL.lastIndexOf(url)>0 ){
				choicedMenunarIdx = i;
			}
		});	
		//-------------------------------------	
		// 메뉴바 글씨에 마우스 올릴 때와 뺄 때의 실행 구문 설정
		//-------------------------------------	
		/* menubarThObjs.hover(
			function(){
				//alert($(this).html())
				$(this).css("background-color",mouseoverBgColor); 
				$(this).siblings().css("background-color",mouseoutBgColor);
			}
			,function(){	
				menubarThObjs.css("background-color",mouseoutBgColor); 
				menubarThObjs.eq(choicedMenunarIdx).css("background-color",mouseoverBgColor);
			}
		); */
		//-------------------------------------	
		// 선택된 메뉴바에 [마우스 오버], [마우스 아웃] 시 실행할 코딩 설정하기
		//-------------------------------------	
		menubarThObjs.hover(
			function(){
				var obj = $(this);
				// [마우스 오버] 한 메뉴바의 배경색 지정
			    obj.css("background-color",mouseoverBgColor);
				// [마우스 오버] 한 메뉴바의 글자색 지정
				obj.html( "<font color="+mouseoverFontColor+"><b>" + obj.text() +"</b></font>" );
				//----
				// [마우스 오버] 한 메뉴바 형재 메뉴바의 배경색 지정
				obj.siblings().css("background-color",mouseoutBgColor);   
				// [마우스 오버] 한 메뉴바 형재 메뉴바의 글자색 지정                                               
				obj.siblings().html( "<font color="+mouseoutFontColor+"><b>" + obj.siblings().text() +"</b></font>" );
			}
			,function(){	
				var obj = $(this);
				// [마우스 아웃] 한 메뉴바의 배경색 지정
				obj.css("background-color",mouseoutBgColor); 
				// [마우스 아웃] 한 메뉴바의 글자색 지정
				obj.html( "<font color="+mouseoutFontColor+"><b>" + obj.text() +"</b></font>" );
				// 선택된 메뉴바를 코딩으로 [마우스 오버]하기
				menubarThObjs.eq( choicedMenunarIdx ).mouseover();
			}
		);
		//-------------------------------------	
		// 선택된 메뉴바를 코딩으로 [마우스 오버]하기
		//-------------------------------------	
		menubarThObjs.eq( choicedMenunarIdx ).mouseover();
	}
	
	/* printMenubar(  
			"black"    //menunbarBgColor 
			,"red"    //,mouseoverBgColor
			,"white"    //,mouseoverFontColor
			,"black"   //,mouseoutBgColor
			,"white"   //,mouseoutFontColor
			,[
				['/erp/contactSearchForm1.do','연락처']
				,['/erp/boardListForm.do','게시판']
				//,['logout()','로그아웃']
			]
	); */
	printMenubar(  
			"black"    //menunbarBgColor 
			,"gray"    //,mouseoverBgColor
			,"white"    //,mouseoverFontColor
			,"black"      //,mouseoutBgColor
			,"white"   //,mouseoutFontColor
			,[
				['/erp/contactSearchForm3.do','연락처']
				,['/erp/boardListForm.do','게시판']
				//,['logout()','로그아웃']
			]
	);
</script><br>





















































