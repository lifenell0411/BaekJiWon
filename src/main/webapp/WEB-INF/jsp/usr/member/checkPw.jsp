<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MEMBER CheckPw" />
<%@ include file="../common/head.jspf"%>
<hr />


<section>
  <div class="container">
    <div class="table-box-type-1">
      <div class="card">
        <div class="card-header">
          <h4 class="card-title">비밀번호 확인</h4>
        </div>
        <div class="card-body">
          <form action="../member/doCheckPw" method="POST">
            <input type="hidden" name="replaceUri" value="${param.replaceUri }" />
            <div class="form-group">
              <label for="loginPw" class="form-label">비밀번호</label>
              <input required="required" class="form-control" autocomplete="off" type="text"
                placeholder="비밀번호를 입력해주세요" name="loginPw" />
            </div>
            <button type="submit" class="btn-custom">확인</button>
          </form>
        </div>
        <div class="card-footer text-muted">
          <button class="btn-custom" type="button" onclick="history.back();">뒤로가기</button>
        </div>
      </div>
    </div>
  </div>
</section>


<style>
body {
  overflow: hidden;
}
.btn-custom {
  background-color: #917FB3;
  color: white;
  border: 2px solid #917FB3;
  border-radius: 4px;
  padding: 6px 12px;
}

.btn-custom:hover{
}

.card-header{
 background-color: #917FB3;}
 
 .card-footer{
  background-color: #917FB3;
 }
.card-title{
color: white;}
     .table-box-type-1 {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
     
    }

</style>
<%@ include file="../common/foot.jspf"%>