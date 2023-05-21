<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="JOIN" />
<%@ include file="../common/head.jspf"%>
<!-- 제이쿼리 불러오기 -->

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script>
	let submitJoinFormDone = false;
 
	let validEmail = "";

	function submitJoinForm(form) {
		if (submitJoinFormDone) {
			alert('처리중입니다');
			return;
		}
		form.email.value = form.email.value.trim();
		if (form.email.value == 0) {
			alert('이메일을 입력해주세요');
			return;
		}

		if (form.loginId.value != validEmail) {
			alert('사용할 수 없는 아이디입니다');
			form.email.focus();
			return;
		}

		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value == 0) {
			alert('비밀번호를 입력해주세요');
			return;
		}
		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
		if (form.loginPwConfirm.value == 0) {
			alert('비밀번호 확인을 입력해주세요');
			return;
		}
		if (form.loginPwConfirm.value != form.loginPw.value) {
			alert('비밀번호가 일치하지 않습니다');
			form.loginPw.focus();
			return;
		}
		form.name.value = form.name.value.trim();
		if (form.name.value == 0) {
			alert('이름을 입력해주세요');
			return;
		}
		form.nickname.value = form.nickname.value.trim();
		if (form.nickname.value == 0) {
			alert('닉네임을 입력해주세요');
			return;
		}
		
		form.cellphoneNum.value = form.cellphoneNum.value.trim();
		if (form.cellphoneNum.value == 0) {
			alert('전화번호를 입력해주세요');
			return;
		}
		submitJoinFormDone = true;
		form.submit();
	}

	 
	function getLoginEmailDup(el) {
		$('.checkDup-msg1').empty();
		const form = $(el).closest('form').get(0);

		if (form.email.value.length === 0) {
			$('.checkDup-msg1')
					.html('<div class="mt-2">이메일을 입력해주세요!</div>');
			validEmail = '';
			return;
		}

		// 이메일 형식이 유효한지 확인하는 함수
		function isValidEmail(email) {
			const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
			return emailPattern.test(email);
		}

		if (!isValidEmail(form.email.value)) {
			$('.checkDup-msg1').html(
					'<div class="mt-2">올바른 이메일 형식을 입력해주세요.</div>');
			validEmail = '';
			return;
		}

		$.get('../member/getLoginEmailDup', {
			isAjax : 'Y',
			email : form.email.value
		}, function(data) {
			$('.checkDup-msg1').html(
					'<div class="mt-2">' + data.msg + '</div>')
			if (data.success) {
				validEmail = data.data1;
			} else {
				validEmail = '';
			}
		}, 'json');
	}
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
	  <div class="table-container">
		<form class="table-box-type-1" method="POST" action="../member/doJoin" onsubmit="submitJoinForm(this); return false;">
			<input type="hidden" name="afterLoginUri" value="${param.afterLoginUri}" />
			<table class="table table-zebra w-full">
				<colgroup>
					<col width="200" />
				</colgroup>
				<tbody>
						<tr>
						<th style="background-color: #5C5470;">이메일</th>
						<td>
							<input onblur="getLoginEmailDup(this);" name="email" class="w-full input input-bordered  max-w-xs"
								placeholder="이메일을 입력해주세요" autocomplete="off" />
							<div class="checkDup-msg1"></div>
						</td>
					</tr>
					<tr>
						<th style="background-color: #5C5470;">비밀번호</th>
						<td style="background-color: white;">
							<input name="loginPw" class="w-full input input-bordered max-w-xs" placeholder="비밀번호를 입력해주세요" />
						</td>
					</tr>
					<tr>
						<th style="background-color: #5C5470;">비밀번호 확인</th>
						<td style="background-color: white;">
							<input name="loginPwConfirm" class="w-full input input-bordered max-w-xs" placeholder="비밀번호 확인을 입력해주세요" />
						</td>
					</tr>
					<tr>
						<th style="background-color: #5C5470;">이름</th>
						<td style="background-color: white;">
							<input name="name" class="w-full input input-bordered max-w-xs" placeholder="이름을 입력해주세요" />
						</td>
					</tr>
					<tr>
						<th style="background-color: #5C5470;">닉네임</th>
						<td>
							<input name="nickname" class="w-full input input-bordered max-w-xs" placeholder="닉네임을 입력해주세요" />
						</td>
					</tr>
					<tr>
						<th style="background-color: #5C5470;">전화번호</th>
						<td style="background-color: white;">
							<input name="cellphoneNum" class="w-full input input-bordered max-w-xs" placeholder="전화번호를 입력해주세요" />
						</td>
					</tr>
				
					<tr>
						<th style="background-color: #5C5470;"></th>
						<td style="background-color: white;">
							<button class="btn btn-active btn-ghost" type="submit" value="회원가입">회원가입</button>
						</td>
					</tr>
				</tbody>
			</table>

		</form>
		</div>
	</div>




</section>
<%@ include file="../common/foot.jspf"%>

	<style>
 
 

.table-container {
  display: flex;
  align-items: center;
  justify-content: flex-start; /* 변경된 부분 */
  min-height: 100vh;
  padding: 100px 0;
  box-sizing: border-box;
}

.table-box-type-1 {
  margin: 0 auto; /* 중앙 정렬을 위해 추가 */
  width: 500px;
}

.table-box-type-1 table {
  border-collapse: collapse;
  width: 100%;
}

.table-box-type-1 th, .table-box-type-1 td {
	border: none;
	padding: 0.5rem;
	text-align: left;
}

th {
	background-color: #917FB3;
	color: #fff;
	font-weight: bold;
	text-align: center;
}

.table-box-type-1 input {
	display: block;
	width: 100%;
	padding: 0.5rem;
	border: 1px solid #ccc;
	border-radius: 0.25rem;
	margin-bottom: 0.5rem;
}

.checkDup-msg {
	margin-top: 0.5rem;
	font-size: 0.875rem;
	color: #d64040;
}

.btn {
	display: inline-block;
	border: 1px solid #ccc;
	padding: 0.5rem 1rem;
	border-radius: 0.25rem;
	text-align: center;
	font-size: 1rem;
	line-height: 1.5;
	cursor: pointer;
	transition: all 0.2s ease;
}

.btn:hover {
	background-color: #f2f2f2;
}

.btn-active {
	background-color: #917FB3;
	color: #fff;
	border-color: #917FB3;
}

.btn-ghost {
	background-color: transparent;
	color: #917FB3;
}

.btn-ghost:hover {
	background-color: #f2f2f2;
}
</style>