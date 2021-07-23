<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>index</title>
<link href="join.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<!-- 공통헤더 include  	<jsp:include page="/WEB-INF/views/inc/header.jsp" />-->
	<jsp:include page="../inc/header.jsp" />
	<!-- visual include  -->
	<jsp:include page="inc/visual.jsp" />
	<div id="main">
		<div class="top-wrapper clear">
			<div id="content">
				<form method="post">
					<h2>회원정보수정</h2>
					<h3 class="hidden">방문페이지 로그</h3>
					<h3 class="hidden">상세정보</h3>
					<div id="join-form" class="join-form margin-large">
						<dl class="join-form-row">
							<dt class="join-form-title">아이디</dt>
							<dd class="join-form-data">
								<input type="text" value="${member.userid}" name="userid"
									readonly>
							</dd>
						</dl>
						<dl class="join-form-row">
							<dt class="join-form-title">비밀번호</dt>
							<dd class="join-form-data">
								<input type="password" value="" name="pwd">
							</dd>
						</dl>
						<dl class="join-form-row">
							<dt class="join-form-title">이름</dt>
							<dd class="join-form-data">
								<input type="text" value="${member.name}" name="name">
							</dd>
						</dl>
						<dl class="join-form-row">
							<dt class="join-form-title">성별</dt>
							<dd class="join-form-data">
								<input type="text" value="${member.gender}" name="gender"
									readonly>
							</dd>
						</dl>
						<dl class="join-form-row">
							<dt class="join-form-title">핸드폰 번호</dt>
							<dd class="join-form-data">
								<input type="text" value="${member.cphone}" name="cphone">
							</dd>
						</dl>
						<dl class="join-form-row">
							<dt class="join-form-title">이메일</dt>
							<dd class="join-form-data">
								<input type="text" value="${member.email}" name="email">
							</dd>
						</dl>
					</div>
					<div id="buttonLine">
						<input class="btn-okay button" type="submit" value="수정" />
					</div>
					<%-- <table border="1">
		<tr>
			<th>아이디</th>
			<th>비밀번호</th>
			<th>이름</th>
			<th>성별</th>
			<th>전화번호</th>
			<th>email</th>
		</tr>
		<tr>
			<td><input type="text" value="${member.userid}" name="userid" readonly></td>
			<td><input type="password" value="" name="pwd"></td>
			<td><input type="text" value="${member.name}" name="name"></td>
			<td><input type="text" value="${member.gender}" name="gender" readonly></td>
			<td><input type="text" value="${member.cPhone}" name="cPhone"></td>
			<td><input type="text" value="${member.email}" name="email"></td>
		</tr>
		<tr>
			<td colspan="5"><input type="submit" value="수정"></td>
		</tr>
	</table> --%>
				</form>
			</div>
			<!--  aside include  -->
			<jsp:include page="./inc/aside.jsp" />
		</div>
	</div>
	<!--공통푸터  include  -->
	<jsp:include page="../inc/footer.jsp" />
</body>
</html>
