<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.code}" />
<%@ include file="../common/head.jspf"%>
<hr />
  
<section class="text-xl">
	<div class="container ">
		<div class="table-box-type-1">
			<div class="flex mb-4">
				<div>
					게시물 갯수 :
					<span class="badge">${articlesCount }</span>
					개
				</div>
				<div class="flex-grow"></div>
				<form action="">
					<input type="hidden" name="boardId" value="${param.boardId }" />
					<select data-value="${param.searchKeywordTypeCode }" name="searchKeywordTypeCode" class="select select-ghost">
						<option value="title">제목</option>
						<option value="body">내용</option>
						<option value="title,body">제목 + 내용</option>
					</select>
					<input value="${param.searchKeyword }" maxlength="20" name="searchKeyword" class="input input-bordered" type="text"
						placeholder="검색어를 입력해주세요" />
					<button class="btn btn-ghost" type=submit>검색</button>
				</form>
			</div>
			<div class="table-wrapper">
				<table class="table table-zebra">
					<colgroup>
						<col width="140" />
						<col width="140" />
						<col width="140" />
						<col width="140" />
						<col width="140" />
						<col width="140" />
						<col width="140" />
					</colgroup>
					<thead>
						<tr>
							<th>번호</th>
							<th>날짜</th>
							<th>제목</th>
							<th>작성자</th>
							<th>조회수</th>
							<th>좋아요</th>
							<th>싫어요</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="article" items="${articles }">
							<tr class="hover">
								<td>
									<div class="badge">${article.id}</div>
								</td>
								<td>${article.regDate.substring(2,16)}</td>
								<td>
									<a class="hover:underline" href="${rq.getArticleDetailUriFromArticleList(article) }">${article.title}</a>
								</td>
								<td>${article.extra__writer}</td>
								<td>${article.hitCount}</td>
								<td>${article.goodReactionPoint}</td>
								<td>${article.badReactionPoint}</td>
							</tr>
						</c:forEach>
					</tbody>

				</table>
			</div>
		</div>
	</div>
</section>

		<div class="pagination flex justify-center mt-3">
			<div class="btn-group ">

				<c:set var="paginationLen" value="4" />
				<c:set var="startPage" value="${page - paginationLen >= 1 ? page - paginationLen : 1}" />
				<c:set var="endPage" value="${page + paginationLen <= pagesCount ? page + paginationLen : pagesCount}" />

				<c:set var="baseUri" value="?boardId=${boardId }" />
				<c:set var="baseUri" value="${baseUri }&searchKeywordTypeCode=${searchKeywordTypeCode}" />
				<c:set var="baseUri" value="${baseUri }&searchKeyword=${searchKeyword}" />

				<c:if test="${startPage > 1 }">
					<a class="btn" href="${baseUri }&page=1">1</a>
					<button class="btn btn-disabled ">...</button>
				</c:if>

				<c:forEach begin="${startPage }" end="${endPage }" var="i">
					<a class="btn ${page == i ? 'btn-active' : '' }" href="${baseUri }&page=${i}">${i }</a>
				</c:forEach>

				<c:if test="${endPage < pagesCount }">
					<button class="btn btn-disabled">...</button>
					<a class="btn" href="${baseUri }&page=${pagesCount}">${pagesCount }</a>
				</c:if>
			</div>
		</div>
	</div>
</section>


<style>
.container{
margin-top: 200px;
}

 body {
   background-color: #FDE2F3;
    }
    
 html  {
      height: 100%;
       
    }
 body {
      margin: 0;
      padding: 0;
      overflow: auto;  
       background-color: #FDE2F3;
       height: 100%; 
    }
html, body {
  height: 100%;
  min-height: 100%;
}

</style>
 
<%@ include file="../common/foot.jspf"%>