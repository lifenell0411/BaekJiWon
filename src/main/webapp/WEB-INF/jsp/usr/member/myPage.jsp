<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MEMBER MYPAGE" />
<%@ include file="../common/head.jspf"%>
c
<hr />
<section class="mt-8 text-xl">
    <div class="container" >
        <div class="table-box-type-1" style="max-width: 800px;">
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
                    <th>이름</th>
                    <td>${rq.loginedMember.name }</td>
                </tr>
                <tr>
                    <th>닉네임</th>
                    <td>${rq.loginedMember.nickname }</td>
                </tr>
                <tr>
                    <th>전화번호</th>
                    <td>${rq.loginedMember.cellphoneNum }</td>
                </tr>
             
                <tr>
                    <th></th>
                    <td>
                        <a href="../member/checkPw?replaceUri=${Ut.getEncodedUri('../member/modify') }" class="btn btn-active btn-ghost">회원정보 수정</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
 
</section>
 
<style>
body {
  overflow: visible;
}

.table1{

width: 500px;}

table {
  border-spacing: 0;
  width: 100%;
  border: none;
   margin-left: 200px;
  margin-top: 100px;
}
.like{

width: 100px;
height: 100px;
background-color: red;
border : 3px solid black;
margin-left: 300px;}
th,
td {
  border: none;
  padding: 10px;
  text-align: center;
}

th {
  background-color: #5C5470;
  font-weight: bold;
  color: white;
}

tr:nth-child(even) {
  background-color: white;
}

tr:nth-child(odd) {
  background-color: white;
}
</style>
<%@ include file="../common/foot.jspf"%>