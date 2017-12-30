<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>
<script>
	//===================================================================
	// body 태그 안의 소스를 모두 실핼한 후에 실행할 자바스크립트 코드 설정
	//===================================================================

	$(document).ready(function() {

		$("[name=loginForm] .login").click(function() {

			checkLoginForm();

		});

	});

	function checkLoginForm() {
		
		var admin_id = $("[name=admin_id]").val();
		var pwd = $("[name=pwd]").val();

		if (admin_id.split(" ").join() == "") {
			alert("관리자 아이디 입력 요망");
			return;
		}

		$.ajax({

			url : "/erp/loginProc.do"
			, type : "post"
			, data : {
				'admin_id' : admin_id
				,'pwd' : pwd
			} // $("[name=loginForm]").serializer() form 데이터 전부 보낼때
			, success : function(data) {

				if (data == "1") {
					//alert("로그인 성공");
					location.replace("/erp/contactSearchForm3.do");
				} else {
					alert("로그인 실패");
				}

			}
			, error : function(error) {
		        alert("Error!");
		    }
			, complete : function() {
		        //alert("complete!");    
		    }

		});

	}
</script>
</head>
<body>
	<center>


		<form name="loginForm" method="post" action="/erp/loginProc.do">
			<b>[관리자 로그인]</b>
			<table border=1 cellpadding=5 cellspacing=0>
				<tr>
					<th bgcolor="#E1E1E1" align=center>아이디
					<td><input type="text" name="admin_id" class="admin_id"
						size="20"></td>
				</tr>
				<tr>
					<th bgcolor="#E1E1E1" align=center>암 호
					<td><input type="text" name="pwd" class="pwd" size="21"></td>
				</tr>
			</table>
			<input type="button" value="로그인" class="login">
		</form>




	</center>



	<!-- 
	<center>
	${msg}<br>
	</center>
	
	<form name="loginForm" method="post" action="/erp/loginProc.do">
		<b>[관리자 로그인]</b>
		<table border=1 cellpadding=5 cellspacing=0>
			<tr>
				<th bgcolor="#E1E1E1" align=center>아이디
				<td><input type="text" name="admin_id" class="admin_id" size="20"></td>
			</tr>
			<tr>
				<th bgcolor="#E1E1E1" align=center>암  호
				<td><input type="text" name="pwd" class="pwd" size="21"></td>
			</tr>
		</table>
		<input type="button" value="로그인" class="login" onclick="document.loginForm.submit()">
	</form>
	 -->


</body>
</html>