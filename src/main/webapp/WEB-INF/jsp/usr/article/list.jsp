<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.code}" />
<%@ include file="../common/head.jspf"%>
<hr />
  <div class="background"></div>
  <style>
		body {
				height: 100%;
				margin: 0;
			 
				font-family: "Fira Sans", sans-serif;
				-webkit-font-smoothing: antialiased;
				-moz-osx-font-smoothing: grayscale;
				background: radial-gradient(ellipse farthest-corner at center bottom, #6beace, #2e9aa4);
			}
.container {
  max-width: 960px;
  margin: auto;
  margin-top: calc(50vh + 150px);
  transform: translateY(-50%);
}
 
		</style>
<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
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
			<table class="table table-zebra w-full">
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

 
<%@ include file="../common/foot.jspf"%>