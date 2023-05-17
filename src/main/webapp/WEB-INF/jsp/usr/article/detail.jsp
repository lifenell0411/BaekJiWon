<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jspf"%>
<hr />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- <iframe src="http://localhost:8081/usr/article/doIncreaseHitCountRd?id=2" frameborder="0"></iframe> -->
<script>
	const params = {}
	params.id = parseInt('${param.id}');
</script>

<!-- 조회수 관련 -->
<!-- <iframe src="http://localhost:8081/usr/article/doIncreaseHitCountRd?id=2" frameborder="0"></iframe> -->

<script>
	const params = {}
	params.id = parseInt('${param.id}');
</script>
<script>
	function ArticleDetail__increaseHitCount() {
		
		const localStorageKey = 'article__' + params.id + '__alreadyView';
		if (localStorage.getItem(localStorageKey)) {
			return;
		}
		localStorage.setItem(localStorageKey, true);
				
		$.get('../article/doIncreaseHitCountRd', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			$('.article-detail__hit-count').empty().html(data.data1);
		}, 'json');
	}
	$(function() {
		 // 실전코드
		  		ArticleDetail__increaseHitCount();
		 // 연습코드
		  // setTimeout(ArticleDetail__increaseHitCount, 1000);
	})
</script>


<section class="text-xl">
	<div class="container">
		<div class="table-box-type-1">
			<table border="1">
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
					<tr >
						<th class="table-header">번호</th>
						<td class="table-cell">
							<div class="badge">${article.id}</div>
						</td>

					</tr>
					<tr>
						<th class="table-header">작성날짜</th>
						<td class="table-cell">${article.regDate }</td>
					</tr>
					<tr>
						<th class="table-header">수정날짜</th>
						<td class="table-cell">${article.updateDate }</td>
					</tr>
					<tr>
						<th class="table-header">작성자</th>
						<td class="table-cell">${article.extra__writer }</td>
					</tr>
					<tr>
						<th class="table-header">조회수</th>
						<td class="table-cell">
							<span class="article-detail__hit-count">${article.hitCount }</span>
						</td>
					</tr>

					<tr>
						<c:if test="${article.boardId eq 6 || article.boardId eq 7 || article.boardId eq 8}">
							<tr>
								<th class="table-header">추천</th>
								<td class="table-cell">
									<span>&nbsp;좋아요 : ${article.goodReactionPoint }&nbsp;</span>
									<span>&nbsp;싫어요 : ${article.badReactionPoint }&nbsp;</span>
									<c:if test="${actorCanMakeReaction }">
										<div>
											<span>
												<span>&nbsp;</span>
												<a
													href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
													class="btn btn-xs ">좋아요 👍</a>
											</span>
											<span>
												<span>&nbsp;</span>
												<a
													href="/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
													class="btn btn-xs">싫어요 👎</a>
											</span>
										</div>
									</c:if>
									<c:if test="${actorCanCancelGoodReaction }">
										<div>
											<span>
												<span>&nbsp;</span>
												<a
													href="/usr/reactionPoint/doCancelGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
													class="btn btn-xs">좋아요 취소 👍</a>
											</span>
											<span>
												<span>&nbsp;</span>
												<a onclick="alert(this.title); return false;" title="좋아요를 먼저 취소해" class="btn btn-xs">싫어요 👎</a>
											</span>
										</div>
									</c:if>
									<c:if test="${actorCanCancelBadReaction }">
										<div>
											<span>
												<span>&nbsp;</span>
												<a onclick="alert(this.title); return false;" title="싫어요를 먼저 취소해" class="btn btn-xs">좋아요 👍</a>
											</span>
											<span>
												<span>&nbsp;</span>
												<a
													href="/usr/reactionPoint/doCancelBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
													class="btn btn-xs">싫어요 취소 👎</a>
											</span>
										</div>
									</c:if>
								</td>
							</tr>
						</c:if>
					
					
					
					
					
					<c:if test="${article.boardId eq 1 || article.boardId eq 2 || article.boardId eq 3 || article.boardId eq 4}">
							<tr>
								<th class="table-header">찜하기</th>
								<td class="table-cell">
									<span>&nbsp;찜하기 : ${article.likePoint }&nbsp;</span>
								 
									<c:if test="${actorCanMakeLike}">
										<div>
											<span>
												<span>&nbsp;</span>
												<a
													href="/usr/likePoint/doLikePoint?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
													class="btn btn-xs btn-error">찜하기❤</a>
											</span>
											 
										</div>
									</c:if>
									<c:if test="${actorCanCancelLike }">
										<div>
											<span>
												<span>&nbsp;</span>
												<a
													href="/usr/likePoint/doCancelLikePoint?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
													class="btn btn-xs btn-error">찜하기❤ 취소</a>
											</span>
											
										</div>
									</c:if>
									
								</td>
							</tr>
						</c:if>
						
						
						
					<tr>
						<th class="table-header">제목</th>
						<td class="table-cell">${article.title }</td>
					</tr>
					<tr>
						<th class="table-header">내용</th>
						<td class="table-cell">${article.body }</td>
					</tr>
				</tbody>

			</table>
		</div>
		<div class="btns">
			<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>

			<c:if test="${article.actorCanModify }">
				<a class="btn-text-link btn btn-active btn-ghost" href="../article/modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.actorCanDelete }">
				<a class="btn-text-link btn btn-active btn-ghost" onclick="if(confirm('정말 삭제하시겠습니까?')==false) return false;"
					href="../article/doDelete?id=${article.id }">삭제</a>
			</c:if>
		</div>
	</div>
</section>

<!-- 댓글 관련 -->
<script type="text/javascript">
	let ReplyWrite__submitFormDone = false;

	function ReplyWrite__submitForm(form) {
		if (ReplyWrite__submitFormDone) {
			return;
		}
		form.body.value = form.body.value.trim();

		if (form.body.value.length < 3) {
			alert('3글자 이상 입력하세요');
			form.body.focus();
			return;
		}

		ReplyWrite__submitFormDone = true;
		form.submit();

	}
</script>

<c:if test="${article.boardId eq 5 || article.boardId eq 6 || article.boardId eq 7 || article.boardId eq 8}">
<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<c:if test="${rq.logined }">
				<form action="../reply/doWrite" method="POST" onsubmit="ReplyWrite__submitForm(this); return false;">
					<input type="hidden" name="relTypeCode" value="article" />
					<input type="hidden" name="relId" value="${article.id }" />
					<input type="hidden" name="replaceUri" value="${rq.currentUri }" />
					<table>
						<colgroup>
							<col width="200" />
						</colgroup>

						<tbody>
  <tr>
    <th>댓글</th>
    <td>
      <div class="input-group">
        <textarea class="input input-bordered" type="text" name="body" placeholder="내용을 입력해주세요"></textarea>
        <button type="submit" value="작성" class="btn btn-active btn-ghost">
          댓글 작성
        </button>
      </div>
    </td>
  </tr>
</tbody>


					</table>
				</form>
			</c:if>
			<c:if test="${rq.notLogined }">
				<a class="btn-text-link btn btn-active btn-ghost" href="${rq.loginUri }">로그인</a> 하고 해라
			</c:if>
		</div>

	</div>
	
	</c:if>
</section>


<section class="mt-5">
<c:if test="${article.boardId eq 5 || article.boardId eq 6 || article.boardId eq 7 || article.boardId eq 8}">
	<div class="container mx-auto px-3">
		<h1 class="text-3xl">댓글 리스트(${repliesCount })</h1>
		<table class="table table-zebra w-full">
			<colgroup>
				<col width="70" />
				<col width="100" />
				<col width="100" />
				<col width="50" />
				<col width="140" />
				<col width="50" />
				<col width="50" />
			</colgroup>
			<thead>
				<tr>
					<th class ="replyHD">번호</th>
					<th class ="replyHD">날짜</th>
					<th class ="replyHD">작성자</th>
					<th class ="replyHD">추천</th>
					<th class ="replyHD">내용</th>
					<th class ="replyHD">수정</th>
					<th class ="replyHD">삭제</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="reply" items="${replies }">
					<tr class="hover">
						<td>
							<div class="badge">${reply.id}</div>
						</td>
						<td>${reply.getForPrintRegDateType1()}</td>
						<td>${reply.extra__writer}</td>
						<td>${reply.goodReactionPoint}</td>
						<td align="left">${reply.body}</td>
						<td>
							<c:if test="${reply.actorCanModify }">
								<a class="btn-text-link btn btn-active btn-ghost"
									href="../reply/modify?id=${reply.id }&replaceUri=${rq.encodedCurrentUri}">수정</a>
							</c:if>
						</td>
						<td>
							<c:if test="${reply.actorCanDelete }">
								<a class="btn-text-link btn btn-active btn-ghost" onclick="if(confirm('정말 삭제하시겠습니까?')==false) return false;"
									href="../reply/doDelete?id=${reply.id }&replaceUri=${rq.encodedCurrentUri}">삭제</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>

		</table>
	</div>
	</c:if>
</section>


<style>
.container{

margin-top: 200px;
}
 
table {
  border-collapse: collapse;
  width: 100%;
}

.table-header, .table-cell {
 
  padding: 8px;
  text-align: center;
}

 

.table-cell:nth-child(even) {
  background-color: #f2f2f2;
}

th, td {
  border: none;
  padding: 10px;
  text-align: center;
}

.table-header {
  background-color: #917FB3;
  font-weight: bold;
  color: white;
}
.table-cell {
background-color: white;
}

.replyHD {
 background-color: #917FB3;
  font-weight: bold;
  color: white;
}

input[type="text"], textarea {
  width: 100%;
  max-width: 900px; /* 원하는 최대 너비값으로 변경 가능 */
}

.input-group {
  display: flex;
  align-items: center;
}

.input-group .input {
  flex: 1;
  margin-right: 10px;
}

.input-group .btn {
  white-space: nowrap;
}

.btn-error.active {
  background-color: red;
  color: white;
}
</style>


<script>

$(document).ready(function() {
	  // 찜하기 버튼 클릭 이벤트
	  $(".btn-error").click(function() {
	    // 버튼 클릭 시, 효과를 추가
	    $(this).addClass("active");
	    // 1초 후, 효과를 제거
	    setTimeout(function() {
	      $(".btn-error").removeClass("active");
	    }, 1000);
	  });
	});
</script>

<%@ include file="../common/foot.jspf"%>