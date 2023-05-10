<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MEMBER LOGIN" />
<%@ include file="../common/head.jspf"%>
<hr />

<div class="container">
	<div class="login-box">
		<h2>Login</h2>
		<form action="../member/doLogin" method="POST">
			<input type="hidden" name="afterLoginUri" value="${param.afterLoginUri }" />
			<div class="user-box">
				<input type="text" name="loginId" required="" autocomplete="off">
				<label>LoginId</label>
			</div>
			<div class="user-box">
				<input type="password" name="loginPw" required="" autocomplete="off">
				<label>Password</label>
			</div>
			<button type="submit">Login</button>
		</form>
	</div>
</div>
 
 



<style>
html, body {
  height: 100%;
  margin: 0;
}

body {
  height: 100%;
  margin: 0;
  font-family: "Fira Sans", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background: radial-gradient(ellipse farthest-corner at center bottom, #6beace, #2e9aa4);
  background-attachment: fixed;
}

.container {
  max-width: 960px;
  margin: auto;
  position: relative;
  top: calc(50vh - 150px); /* 로그인박스 높이의 절반인 150px만큼 상위 요소 중앙 위치에서 아래로 이동 */
}

.login-box {

  width: 400px;
  padding: 40px;
  background: rgba(0, 0, 0, 0.5);
  box-sizing: border-box;
  box-shadow: 0 15px 25px rgba(0, 0, 0, 0.6);
  border-radius: 10px;
  position: relative;
  margin: 0 auto; /* 로그인박스 가운데 정렬 */
  margin-top: 200px;
  transform: translate(-10%, -30%);
}


.login-box h2 {
	margin: 0 0 30px;
	padding: 0;
	color: #fff;
	text-align: center;
}

.login-box .user-box {
	position: relative;
}

.login-box .user-box input {
	width: 100%;
	padding: 10px 0;
	font-size: 16px;
	color: #fff;
	margin-bottom: 30px;
	border: none;
	border-bottom: 1px solid #fff;
	outline: none;
	background: transparent;
}

.login-box .user-box label {
	position: absolute;
	top: 0;
	left: 0;
	padding: 10px 0;
	font-size: 16px;
	color: #fff;
	pointer-events: none;
	transition: 0.5s;
}

.login-box .user-box input:focus ~ label, .login-box .user-box input:valid 
	 ~ label {
	top: -20px;
	left: 0;
	color: #03e9f4;
	font-size: 12px;
}

.login-box button {
	position: relative;
	display: inline-block;
	padding: 10px 20px;
	color: #03e9f4;
	font-size: 16px;
	text-decoration: none;
	text-transform: uppercase;
	overflow: hidden;
	transition: 0.5s;
	margin-top: 40px;
	letter-spacing: 4px;
	background: transparent;
	border: 2px solid #03e9f4;
	border-radius: 5px;
	box-shadow: 0 0 5px #03e9f4;
	cursor: pointer;
}

.login-box button:hover {
	background: #03e9f4;
	color: #fff;
	box-shadow: 0 0 5px #03e9f4, 0 0 25px #03e9f4, 0 0 50px #03e9f4, 0 0
		100px #03e9f4;
}

 
 
}
</style>
<%@ include file="../common/foot.jspf"%>