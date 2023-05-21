<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MEMBER MODIFY" />
<%@ include file="../common/head.jspf"%>
<hr />

<!-- Member modify 관련 -->
<script type="text/javascript">
	let MemberModify__submitFormDone = false;

	function MemberModify__submit(form) {
		if (MemberModify__submitFormDone) {
			return;
		}
		form.loginPw.value = form.loginPw.value.trim();

		if (form.loginPw.value.length > 0) {
			form.loginPwConfirm.value = form.loginPwConfirm.value.trim();

			if (form.loginPwConfirm.value.length == 0) {
				alert('비번 확인 써라');
				form.loginPwConfirm.focus();
				return;

			}

			if (form.loginPw.value != form.loginPwConfirm.value) {
				alert('비번 불일치');
				form.loginPw.focus();
				return;
			}
		}

		form.name.value = form.name.value.trim();
		form.nickname.value = form.nickname.value.trim();
		form.cellphoneNum.value = form.cellphoneNum.value.trim();
		form.email.value = form.email.value.trim();

		if (form.name.value.length == 0) {
			alert('이름 써라');
			form.name.focus();
		}

		if (form.nickname.value.length == 0) {
			alert('nickname 써라');
			form.nickname.focus();
		}

		if (form.cellphoneNum.value.length == 0) {
			alert('cellphoneNum 써라');
			form.cellphoneNum.focus();
		}

		if (form.email.value.length == 0) {
			alert('email 써라');
			form.email.focus();
		}

		MemberModify__submitFormDone = true;
		form.submit();

	}
</script>

<section>
	<div class="container">
		<div class="table-box-type-1">
			<form action="../member/doModify" method="POST" onsubmit="MemberModify__submit(this); return false;">
				<table border="1">
					<colgroup>
						<col width="200" />
					</colgroup>

					<tbody>
						<tr>
							<th>가입일</th>
							<td>${rq.loginedMember.regDate }</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td>${rq.loginedMember.email }</td>
						</tr>
						<tr>
							<th>새 비밀번호</th>
							<td>
								<input name="loginPw" class="input input-bordered w-full max-w-xs" placeholder="새 비밀번호를 입력해주세요" type="text" />
							</td>
						</tr>
						<tr>
							<th>새 비밀번호 확인</th>
							<td>
								<input name="loginPwConfirm" class="input input-bordered w-full max-w-xs" placeholder="새 비밀번호 확인을 입력해주세요"
									type="text" />
							</td>
						</tr>
						<tr>
							<th>이름</th>
							<td>
								<input name="name" value="${rq.loginedMember.name }" class="input input-bordered w-full max-w-xs"
									placeholder="이름을 입력해주세요" type="text" />
							</td>
						</tr>
						<tr>
							<th>닉네임</th>
							<td>
								<input name="nickname" value="${rq.loginedMember.nickname }" class="input input-bordered w-full max-w-xs"
									placeholder="닉네임을 입력해주세요" type="text" />
							</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td>
								<input name="cellphoneNum" value="${rq.loginedMember.cellphoneNum }"
									class="input input-bordered w-full max-w-xs" placeholder="전화번호를 입력해주세요" type="text" />
							</td>
						</tr>
						
						<tr>
							<th></th>
							<td>
								<button class =" custom-btn btn-7" type="submit" value="수정" />
								수정
								</button>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div class="btns">
			<button class="btns custom-btn btn-7" type="button" onclick="history.back();">뒤로가기</button>


		</div>
	</div>
</section>


<style>
/* 테이블 전체 스타일 */

body {
  overflow: hidden;
}

table {


  border-collapse: collapse;
  width: 100%;
  max-width: 800px; /* 테이블의 최대 너비를 지정합니다 */
  margin: 0 auto; /* 가운데 정렬 */
  
  margin-top: 100px;
}

/* 테이블 헤더 스타일 */
th {
  background-color: #5C5470; /* 배경색 추가 */
  font-weight: bold; /* 굵은 글씨체로 설정 */
  text-align: center; /* 가운데 정렬 */
  padding: 10px; /* 셀 안의 내용과 경계선 사이의 간격을 지정합니다 */
    color: white;
}

/* 테이블 셀 스타일 */
td {
  text-align: center; /* 가운데 정렬 */
  padding: 10px; /* 셀 안의 내용과 경계선 사이의 간격을 지정합니다 */
}

/* 짝수 번째 행의 배경색을 변경합니다 */
tr:nth-child(even) {
  background-color: white;
}

 

 

  button {
    margin: 20px;
    outline: none;
      border-radius: 10px;
  }
  
  .custom-btn {
    width: 130px;
    height: 40px;
    padding: 10px 25px;
    border: 2px solid #000;
    font-family: 'SUITE-Regular', sans-serif;
    font-weight: 500;
    background: transparent;
    cursor: pointer;
    transition: all 0.3s ease;
    position: relative;
    display: inline-block;
    text-align: center; /* 가운데 정렬을 위한 속성 추가 */
        text-decoration: none; /* 밑줄 제거 */
        border-radius: 10px;
  }
  
  .btn-7 {
    background: #5C5470;
    color: #fff;
    line-height: 42px;
    padding: 0;
    border: none;
    z-index: 1;
    -webkit-transition: all 0.3s linear;
    transition: all 0.3s linear;
      border-radius: 10px;
  }
  
  .btn-7:hover {
    background: transparent;
    color: #000;
        text-decoration: none; /* 밑줄 제거 */
          border-radius: 10px;
  }
  
  .btn-7:before,
  .btn-7:after {
    position: absolute;
    content: "";
    left: 0;
    width: 100%;
    height: 50%;
    right: 0;
    z-index: -1;
    background:  #5C5470;
    transition: all 0.3s ease;
      border-radius: 10px;
  }
  
  .btn-7:before {
    top: 0;
  }
  
  .btn-7:after {
    bottom: 0;
  }
  
  .btn-7:hover:before,
  .btn-7:hover:after {
    height: 0;
    background-color: #000;
  }
table tr:nth-child(odd) {
  background-color: white;
}
 .btns {
 
 left: 138px;}

</style>
<%@ include file="../common/foot.jspf"%>