<!--**************************************************-->
<!--JSP 페이지 처리 방식 선언-->
<!--**************************************************-->
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--**************************************************-->
<!--현재 페이지에 common.jsp 파일 내의 소스 삽입-->
<!--**************************************************-->
<%@include file="common2.jsp"%>


<html><head><title>연락처</title></head>
<script>

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// body 태그를 모두 실행한 후에 실행하고 싶은 자스 코드 설정
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	$(document).ready(function(){
		
			// 연락처 검색 결과물 출력 시 헤더를 빼고 짝수행,홀수행,마우스온했을때의 배경색 설정하는 함수 호출
			setTableTrBgColor("contactSearchResult", "white", "#EFEFEF", "#8F8F8F"); //#FFFFCE		
			//--------------------------------------------------
			// name=contactSearchForm 을 가진 form 태그를 관리하는 JQuery 객체 구하기
			//--------------------------------------------------
			var cSFObj=$("[name=contactSearchForm]");
			//-----------------------------------------------------
			// 연락처 등록일 날짜 검색 조건 입력 양식에서 onChange 이벤트가 발생하면 실행할 코드 설정
			// name 속성값이 min_reg_ 또는 max_reg_ 로 시작하는 select 태그 4개에서 change 이밴트가 발생하면 실행할 코드 설정
			//-----------------------------------------------------
			cSFObj.find("[name^=min_reg_],[name^=max_reg_]").change(function(){
				check_reg_date( );
			});

			//-----------------------------------------------------
			// [이번달로] 글씨를 클릭하면 실행할 구문 설정
			//-----------------------------------------------------
			cSFObj.find(".to_today1").click(function(){   // 오른쪽도 가능 cSFObj.find("[name=min_reg_month]").next().click(function(){
				var today=new Date();
				var year=today.getFullYear();
				var month=today.getMonth()+1;  if(month<10){month="0"+month}
				cSFObj.find("[name=min_reg_year]").val(year);
				cSFObj.find("[name=min_reg_month]").val(month);
				check_reg_date( );
			});
			cSFObj.find(".to_today2").click(function(){    // 오르쪽도 가능 cSFObj.find("[name=max_reg_month]").next().click(function(){
				var today=new Date();
				var year=today.getFullYear();
				var month=today.getMonth()+1;  if(month<10){month="0"+month}
				cSFObj.find("[name=max_reg_year]").val(year);
				cSFObj.find("[name=max_reg_month]").val(month);
				check_reg_date( );
			});
						
			//-----------------------------------------------------
			// [비움], [모두비움] 글씨를 클릭하면 실행할 구문 설정
			//-----------------------------------------------------
			cSFObj.find(".empty1").click(function(){   // 오른쪽도 가능  cSFObj.find("[name=min_reg_month]").next().next().click(function(){
				cSFObj.find("[name^=min_reg_]").val('');
			}); 
			cSFObj.find(".empty2").click(function(){   // 오른쪽도 가능  cSFObj.find("[name=max_reg_month]").next().next().click(function(){
				cSFObj.find("[name^=max_reg_]").val('');
			});

			cSFObj.find(".emptyAll").click(function(){ 
				cSFObj.find("[name^=min_reg_],[name^=max_reg_]").val('');
			});
			
			//-----------------------------------------------------
			// [검색] 버튼에 클릭 실행할 구문 설정하기
			//-----------------------------------------------------
			cSFObj.find(".searchContact").click(function(){   
				//오른쪽도 가능  cSFObj.find("[value*=검색]").not("[value*=모두검색]").click(function(){
				/* alert(
						"saup_field 선택 개수=>"+$("[name=saup_field]:checked").length +"\n" 
						+ "min_reg_year=>"+$("[name=min_reg_year]").val() +"\n" 
						 +"min_reg_month=>"+$("[name=min_reg_month]").val() +"\n" 
						 +"max_reg_year=>"+$("[name=max_reg_year]").val() +"\n"
						 +"max_reg_month=>"+$("[name=max_reg_month]").val() +"\n" 
						 +"sort=>"+$("[name=sort]").val() +"\n" 
						 +"selectPageNo=>"+$("[name=selectPageNo]").val() +"\n" 
				); */
				//cSFObj.find("[name=contactRegFormDiv_top],[name=contactRegFormDiv_left]").val('');	
				document.contactSearchForm.submit( );
			});
												
			//-----------------------------------------------------
			// [모두검색] 버튼에 클릭 이벤트 시 실행할 구문 설정하기
			//-----------------------------------------------------
			cSFObj.find(".searchContactAll").click(function(){ //오른쪽도 가능  cSFObj.find("[value*=모두검색]").click(function(){
				// input,select,textarea 양식 초기화 하기. 
				// 선택자  :checkbox,:radio,[type=button],[name=selectPageNo],[name=rowCntPerPage]에 해당하는 태그는 제외.
					//------------------------------------------------
					//<초 특급 주의> 
					//------------------------------------------------
					// [선택한 페이지 번호]와 [보여줄 행의 개수] 는 값을 비우지 않는다.
					// 컨트롤러 클래스의 메소드의 매개변수로 DTO 객체가 설정되면 
					// 파라미터명과 동일한 속성변수로 파라미터값이 저장되는데 파라미터값이 없으면 null 값이 저장된다.
					// 이때 속성변수 자료형이 int 일 경우 null 값이 저장될 수 없어 에러가 발생하기 떄문이다..
					//------------------------------------------------	
				cSFObj.find("input,select,textarea").not(
						":checkbox,:radio,[type=button],[name=selectPageNo],[name=rowCntPerPage]"
				).val('');	
				// 체크된 입력 양식 체크 풀기. 
				cSFObj.find(":checkbox,:radio").prop("checked", false);				
				// [검색] 버튼을 코딩으로 클릭하기
				cSFObj.find(".searchContact").click( );  
					// 오른쪽도 가능 =>   cSFObj.find("[value*=검색]").not("[value*=모두검색]").click()
					// 오른쪽도 가능 =>   document.contactSearchForm.submit( );
			});

			//-----------------------------------------------------
			// [행보기] 목록 상자에서 change 이벤트 시 실행할 구문 설정하기
			//-----------------------------------------------------
			cSFObj.find("[name=rowCntPerPage]").change(function(){			
				// [검색] 버튼을 코딩으로 클릭하기
				cSFObj.find(".searchContact").click( );
					// 오른쪽도 가능 =>   cSFObj.find("[value*=검색]").not("[value*=모두검색]").click()
					// 오른쪽도 가능 =>   document.contactSearchForm.submit( );
			});			

			//-----------------------------------------------------
			// 파라미터값을 입력 양식에 삽입하여 [검색 조건] 및 기타 입력 양식에 이전 화면에서의 데이터 흔적 남기기.
			//-----------------------------------------------------
			cSFObj.find("[name=min_reg_year]").val( '${contactSearchDTO.min_reg_year}' );
			cSFObj.find("[name=min_reg_month]").val( '${contactSearchDTO.min_reg_month}' );
			cSFObj.find("[name=max_reg_year]").val( '${contactSearchDTO.max_reg_year}' );
			cSFObj.find("[name=max_reg_month]").val( '${contactSearchDTO.max_reg_month}' );	
			//-----------
			cSFObj.find("[name=sort]").val( '${contactSearchDTO.sort}' );			
			//cSFObj.find("[name=selectPageNo]").val( '${(empty contactSearchDTO.selectPageNo)?1:contactSearchDTO.selectPageNo}' );
			//cSFObj.find("[name=rowCntPerPage]").val( '${(empty contactSearchDTO.rowCntPerPage)?15:contactSearchDTO.rowCntPerPage}' );
			cSFObj.find("[name=selectPageNo]").val( '${contactSearchDTO.selectPageNo}' );
			cSFObj.find("[name=rowCntPerPage]").val( '${contactSearchDTO.rowCntPerPage}' );
			//-----------
			cSFObj.find("[name=contactRegFormDiv_top]").val( '${param.contactRegFormDiv_top}' );
			cSFObj.find("[name=contactRegFormDiv_left]").val( '${param.contactRegFormDiv_left}' );
				//------------------------------------------------
				//<초 특급 주의> 
				//------------------------------------------------
				// [선택한 페이지 번호]와 [보여줄 행의 개수] 는 반드시 무조건 디폴트 값을 설정한다.
				// 컨트롤러 클래스의 메소드의 매개변수로 DTO 객체가 설정되면 
				// 파라미터명과 동일한 속성변수로 파라미터값이 저장되는데 파라미터값이 없으면 null 값이 저장된다.
				// 이때 속성변수 자료형이 int 일 경우 null 값이 저장될 수 없어 에러가 발생하기 떄문이다..
				//------------------------------------------------	
			
			clickOpenedContact();
			//--------------------------------------------------
			// 이전 페이지에서 연락처 입력 화면이 열려 있었으면 현대 페이지에서도 열기
			//--------------------------------------------------
			<c:if test="${param.is_open_contactRegFormDiv=='1'}">
				$(".showContactRegForm").click();
			</c:if>				
	});
	//***********************************************************
	
	//***********************************************************
	// 연락처 등록일 검색 조건 관련 태그에서 change 이멘트 발생 시 실행할 코드 설정
	//***********************************************************
	function check_reg_date(){
		var cSFObj=$("[name=contactSearchForm]");   
		//----------------------------------
		var min_reg_year = cSFObj.find("[name=min_reg_year]").val( );
		var min_reg_month = cSFObj.find("[name=min_reg_month]").val( );
		var max_reg_year = cSFObj.find("[name=max_reg_year]").val( );
		var max_reg_month = cSFObj.find("[name=max_reg_month]").val( );
		//----------------------------------
		if( (min_reg_year==null || min_reg_year.length==0) && (min_reg_month!=null && min_reg_month.length>0) ){
			alert("왼쪽 년도를 먼저 선택해야 합니다."); 
			cSFObj.find("[name=min_reg_month]").val( "" );
			return;
		}
		if( (max_reg_year==null || max_reg_year.length==0) && (max_reg_month!=null && max_reg_month.length>0) ){
			alert("왼쪽 년도를 먼저 선택해야 합니다."); 
			cSFObj.find("[name=max_reg_month]").val( "" );
			return;
		}
		//----------------------------------
		if( (min_reg_year!=null && min_reg_year.length>0) && (min_reg_month!=null && min_reg_month.length>0) 
			&& (max_reg_year!=null && max_reg_year.length>0) &&  (max_reg_month!=null && max_reg_month.length>0) ){
					
			var max_date = cSFObj.find("[name=max_reg_year]").val(  )+cSFObj.find("[name=max_reg_month]").val(  );
			var min_date = cSFObj.find("[name=min_reg_year]").val(  )+cSFObj.find("[name=min_reg_month]").val(  );
			if( parseInt(max_date,10) < parseInt(min_date,10) ) {
				alert("[최소 년월]이 [최대 년월] 보다 큽니다. 재 선택 바람!"); 
				cSFObj.find("[name^=min_reg_]").val("");
				cSFObj.find("[name^=max_reg_]").val("");
				return;
			}
		}
		//----------------------------------
		if((min_reg_year!=null && min_reg_year.length>0)  && (min_reg_month==null || min_reg_month.length==0) ){
			cSFObj.find("[name=min_reg_month]").val( "01" );
		}
		if((max_reg_year!=null && max_reg_year.length>0)  && (max_reg_month==null || max_reg_month.length==0) ){
			cSFObj.find("[name=max_reg_month]").val( "12" );
		}
	}
			
	
	//***********************************************************
	// [연락처 등록 화면] 보이게 하는 함수선언
	// [연락처 등록] 버튼 클릭할 경우 호출되는 함수이다.
	//***********************************************************
	function  showContactRegForm() {
		var left = event.clientX; var top = event.clientY+10;			
		$('.regFormDiv').remove( );
		$.ajax({
			url : "/erp/contactRegForm.do" 
			, type : "post"
			//, data : {"contact_no":contact_no}
			, datatype : "html"
			, success : function(data) {
				//-------------------------------------
				$("body").append(
					"<div class='contactRegFormDiv' " +
						"style='display:block;padding:10;position:absolute;top:0;left:0;z-index:5; background-color:#F6F6F6;border:1px solid gray'>"
					 + data +
					"</div>"
				);		 	
				//alert( $(".contactRegFormDiv2").length ); 
				$(".contactRegFormDiv").css({'top':top,'left':left});
				$(".contactRegFormDiv").show();
				$("[name=is_open_contactRegFormDiv]").val(1);
				//-------------------------------------
			}
			, error : function() { 
				alert("서버 접속 실패!"); 
			}
		});	
	}	
	
	
	//***********************************************************
	// [연락처 수정/삭제]를 위해 추가된 tr 태그를 삭제하는 함수 선언
	//***********************************************************
	function removeAddedTr( contact_no ){
		$(".addedTr"+contact_no).remove();
		setTableTrBgColor("contactList", "white", "#EFEFEF", "#FFFFCE");
		$(".contactList tr").mouseover().mouseout();
	}
	//***********************************************************
	// [연락처 수정/삭제 화면] 보이게 하는 함수 선언
	//***********************************************************
	function  showContactUpDelForm( contact_no ){  
		//var left = event.clientX; var top = event.clientY+15;	
		//==============================================
		// 기존에 열려 있는 다른 [연락처 수정/삭제 화면] 닫기
		//==============================================
		//$(".addedTr").not(".addedTr"+contact_no).remove();
		//==============================================
		// 원하는 [연락처 수정/삭제 화면]이 이미 열려 있으면 닫고 함수 종료
		//==============================================
		if( $(".addedTr"+contact_no).length>=1 ) {
			$(".addedTr"+contact_no).remove();
			deleteOpenedContact( contact_no );
			$(".contactList tr").mouseover().mouseout();
			return;
		}
		//==============================================
		// 현재 화면에서 페이지 이동 없이 서버쪽 "/erp/contactUpDelForm.do"  을 호출하여 
		// [연락처 수정/삭제 화면] html 소스를 받아 클릭한 행의 다음 행에 [연락처 수정/삭제 화면] 추가해 보이기
		//==============================================
		$.ajax({
			url : "/erp/contactUpDelForm.do" 
			, type : "post"
			, data : {"contact_no":contact_no}
			, datatype : "html"
			, success : function(data) {
				$(".contactTr"+contact_no).after("<tr class='addedTr addedTr"+contact_no+"'><td align=center colspan=6>");
				$(".addedTr"+contact_no).find("td:eq(0)").html(data);	
				
				/* var upDelFormNameVal = $(".addedTr"+contact_no).find("td:eq(0)").find("form:eq(0)").attr("name");
				$(".addedTr"+contact_no).find("td:eq(0)").find("form:eq(0)").attr(
					"name"
					, upDelFormNameVal+contact_no
				);	 */
				
				setTableTrBgColor("contactList", "white", "#EFEFEF", "#FFFFCE");
				$(".contactList tr").mouseover().mouseout();
				setOpenedContact( contact_no );
			}
			, error : function() { 
				alert("서버 접속 실패!"); 
			}
		});	
	}
	//***********************************************************
	// 현재 보이는 [수정/삭제 연락처]의 연락처 번호를 [HttpSession 객체]에 저장
	//***********************************************************
	function  setOpenedContact( contact_no ){ 
		$.ajax({
			url : "/erp/setOpenedContact.do" 
			, type : "post"
			, data : {"contact_no":contact_no}
			, success : function(data) {}
			, error : function() { 
				alert("서버 접속 실패!"); 
			}
		});			
	}
	//***********************************************************
	// 안보이게할  [수정/삭제 연락처]의 연락처 번호를 [HttpSession 객체]에서 삭제 저장
	//***********************************************************
	function  deleteOpenedContact( contact_no ){  
		$.ajax({
			url : "/erp/deleteOpenedContact.do" 
			, type : "post"
			, data : {"contact_no":contact_no}
			, success : function(data) {}
			, error : function() { 
				alert("서버 접속 실패!"); 
			}
		});			
	}
	//***********************************************************
	// [HttpSession 객체]에 저장된 [수정/삭제 연락처]의 연락처 번호를 가져와 보이게 하기
	//***********************************************************
	function  clickOpenedContact( ){  
		$.ajax({
			url : "/erp/getOpenedContact.do" 
			, type : "post"
			//, data : {"contact_no":contact_no}
			, success : function(contact_noList) {
				if(contact_noList!=null){
					for( var i=0 ; i<contact_noList.length ; i++ ){
						var cnt = $(".addedTr"+contact_noList[i]).length;
						$(".contactTr"+contact_noList[i]).click();
					}
				}
			}
			, error : function() { 
				alert("/erp/getOpenedContact.do 호출 시 서버 접속 실패!"); 
			}
		})
	}
</script>


<body><center><br>
<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
<form:form  name="contactSearchForm" commandName="contactSearchDTO" action="/erp/contactSearchForm3.do" method="post">
<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
	<table class=tbCss1 bordercolor=gray border=3 cellpadding=15><tr><th bgcolor=#FAFAFA>   	
	<b>[연락처 검색]</b> 
	<table class="tbcss1" bordercolor=gray cellpadding=5>
		<tr>
			<th bgcolor=#D8D8D8>사업분야</th>			
			<td><form:checkboxes  path="saup_field" items="${saup_fieldMap}" />
		<tr>
			<th bgcolor=#D8D8D8>등록일
			<td>
				<select name="min_reg_year">
					<option value="">
					<script>
					   for( var i=2000 ; i<new Date( ).getFullYear( )+2 ; i++){
					       document.write( "<option value="+i+">"+i );
					   }
					</script>
				</select>년				
				<select name="min_reg_month">
					<option value="">
					<script>
					   for( var i=1 ; i<=12 ; i++){
					       if(i<10){ document.write( "<option value='0"+i+"'>0"+i ); }
					       else   { document.write( "<option value='"+i+"'>"+i ); }
					   }
					</script>
				</select>월 
				<span style="cursor:pointer" class="to_today1">[이번달로]</span>  
				<span style="cursor:pointer" class="empty1">[비움]</span>             
				~
				<select name="max_reg_year">
					<option value="">	
					<script>
					   for( var i=2000 ; i<new Date( ).getFullYear( )+2 ; i++){
					       document.write( "<option value="+i+">"+i );
					   }
					</script>
				</select>년
				<select name="max_reg_month">
					<option value="">
					<script>
					   for( var i=1 ; i<=12 ; i++){
					       if(i<10){ document.write( "<option value='0"+i+"'>0"+i ); }
					       else   { document.write( "<option value='"+i+"'>"+i ); }
					   }
					</script>
				</select>월
				<span style="cursor:pointer" class="to_today2">[이번달로]</span>  
				<span style="cursor:pointer" class="empty2">[비움]</span>  
				<span style="cursor:pointer" class="emptyAll">[모두비움]</span> 
		<tr>
			<th bgcolor=#D8D8D8>키워드</th>
			<td><form:input  path="keyword1"/>
	</table>
	
	<div style="height:6"></div>
	
	<!--**************************************************-->
	<!--[검색 버튼], [모두검색 버튼], [연락처 등록 링크], [로그아웃 링크] 출력--->
	<!--**************************************************-->
	<input type=button value="                 검색                 " class="searchContact">  
	<input type=button value="모두검색" class="searchContactAll">  
	<span style="cursor:pointer" class="showContactRegForm" onclick="showContactRegForm();">[연락처 등록]</span>	
	<span style="cursor:pointer" class="logout" onclick="logout(  )">[로그아웃]</span>
		
	   </table>
		
	<div style="height:6"></div>
		
	<!--**************************************************-->
	<!--[검색 수] 출력--->
	<!--**************************************************-->
	<font size="2"><b>[검색 개수 : ${contactSearchListCnt}개]</b></font>	
	<table><tr><td height=1 width=650 bgcolor=white></table>
	
	<!--**************************************************-->
	<!-- 자바스크립트 함수 호출로 [페이징 번호] 출력. [페이지당 보여줄 행의 개수] 출력-->
	<!--**************************************************-->
	<table border=0 class="pagingNos">
		<tr>
			<td width=500 align=center>
				<script>
				 document.write(
					printPagingHtml2(
						'${contactSearchListCnt}'               // 검색 결과 총 행 개수
						, '${contactSearchDTO.selectPageNo}'    // 선택된 현재 페이지 번호
						, '${contactSearchDTO.rowCntPerPage}'   // 페이지 당 출력행의 개수
						, '15'                                  // 페이지 당 출력번호 개수
						//, "document.contactSearchForm.submit( );"// 페이지 번호 클릭후 실행할 자스 코드
						, "$('.searchContact').click();"   // 페이지 번호 클릭후 실행할 자스 코드
					)
				);
				</script>
			<td width=90 align=right>
				<select name="rowCntPerPage">
					<option value="10">10
					<option value="15">15
					<option value="20">20
					<option value="25">25
					<option value="30">30
					<option value="35">35
					<option value="40">40
				</select> 행보기
	</table>
	
	<!--**************************************************-->
	<!-- [hidden 입력 양식] 선언. 
	<!--**************************************************-->
	<!--	[정렬값 저장 입력 hidden 양식], [선택한 페이지 번호 저장 hidden 양식], 
	<!--	[연락처 등록 div 태그의 y 좌표 hidden 양식], [연락처 등록 div 태그의 x 좌표 hidden 양식] -->
	<!--**************************************************-->
	<input type="hidden" name="sort">
	<input type="hidden" name="selectPageNo">
	<input type="hidden" name="is_open_contactRegFormDiv" value="0">
	
	<input type="hidden" name="contactRegFormDiv_top"  class="contactRegFormDiv_top" >
	<input type="hidden" name="contactRegFormDiv_left"  class="contactRegFormDiv_left">
<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
</form:form>
<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->

<!--**************************************************-->
<!--검색 결과물 출력하는 table 태그 선언-->
<!--**************************************************-->
<table border=1 cellpadding=5 cellspacing=0 bordercolor=gray width=600  class="tbcss2 contactSearchResult"> 
	<!------------------------------------------>
	<!--검색 결과의 헤더 행 출력하기. 클릭하면 원하는 정렬 데이터를 hidden 태그에 담고 자기 자신으로 접속하기---->
	<!------------------------------------------>
	<tr bgcolor=#C6C6C6>
		<th>번호
		<!------------------------------------->
		<c:choose>
			<c:when test="${param.sort=='contact_name desc'}">
				<th style="cursor:pointer" 
					onclick="$('[name=sort]').val( 'contact_name asc' );document.contactSearchForm.submit( );">▼연락처명
			</c:when>
			<c:when test="${param.sort=='contact_name asc'}">
				<th style="cursor:pointer" 
					onclick="$('[name=sort]').val('contact_name desc');document.contactSearchForm.submit( );">▲연락처명
			</c:when>
			<c:otherwise>
				<th style="cursor:pointer" 
					onclick="$('[name=sort]').val('contact_name asc');document.contactSearchForm.submit( );">연락처명
			</c:otherwise>
		</c:choose>			
		<!------------------------------------->
		<c:choose>
			<c:when test="${param.sort=='phone desc'}">
				<th style="cursor:pointer" 
					onclick="$('[name=sort]').val('phone asc');document.contactSearchForm.submit( );">▼전화
			</c:when>
			<c:when test="${param.sort=='phone asc'}">
				<th style="cursor:pointer" 
					onclick="$('[name=sort]').val('phone desc');document.contactSearchForm.submit( );">▲전화
			</c:when>
			<c:otherwise>
				<th style="cursor:pointer" 
					onclick="$('[name=sort]').val('phone asc');document.contactSearchForm.submit( );">전화
			</c:otherwise>
		</c:choose>
		<!------------------------------------->
		
		<c:choose>
			<c:when test="${param.sort=='4 desc'}">
				<th style="cursor:pointer" 
					onclick="$('[name=sort]').val('4 asc');document.contactSearchForm.submit( );">▼사업분야
			</c:when>
			<c:when test="${param.sort=='4 asc'}">
				<th style="cursor:pointer" 
					onclick="$('[name=sort]').val('4 desc');document.contactSearchForm.submit( );">▲사업분야
			</c:when>
			<c:otherwise>
				<th style="cursor:pointer" 
					onclick="$('[name=sort]').val('4 asc');document.contactSearchForm.submit( );">사업분야
			</c:otherwise>
		</c:choose>
		<!------------------------------------->
		<c:choose>
			<c:when test="${param.sort=='reg_date desc'}">
				<th style="cursor:pointer" 
					onclick="$('[name=sort]').val('reg_date asc');document.contactSearchForm.submit( );">▼등록일
			</c:when>
			<c:when test="${param.sort=='reg_date asc'}">
				<th style="cursor:pointer" 
					onclick="$('[name=sort]').val('reg_date desc');document.contactSearchForm.submit( );">▲등록일
			</c:when>
			<c:otherwise>
				<th style="cursor:pointer" 
					onclick="$('[name=sort]').val('reg_date asc');document.contactSearchForm.submit( );">등록일
			</c:otherwise>
		</c:choose>
		<!------------------------------------->
		<!--검색 결과물 출력-->
		<!------------------------------------->
		<c:forEach var="contact" items="${contactSearchList}" varStatus="loopTagStatus">
			<tr align="center"  style="cursor:pointer"  class="contactTr${contact.contact_no}"
					onClick="showContactUpDelForm(${contact.contact_no})">		
					<%-- onClick="addTr(${loopTagStatus.index});showContactUpDelForm(${contact.contact_no})">	 --%>		
								
				<td>${contactSearchListCnt-(contactSearchDTO.startRowNo+loopTagStatus.index)+1} <!-- 단순 내림 순서 번호 출력-->
				<td>${contact.contact_name}</td>       <!-- 연락처명 출력-->
				<td>${contact.phone}                   <!-- 전화번호 출력-->
				<td>${contact.saup_field}              <!-- 연락처 사업분야를 모아 컴마를 중간에 넣어 출력-->
				<td>${contact.reg_date}                <!-- 연락처 등록일 출력-->
				
		</c:forEach>
</table><table><tr><td height=4></table>
<!--**************************************************-->

<c:if test="${empty contactSearchList}">
	<b>검색 조건에 맞는 결과가 없습니다.</b><br>
</c:if>
<!--**************************************************-->



</body>
</html>
