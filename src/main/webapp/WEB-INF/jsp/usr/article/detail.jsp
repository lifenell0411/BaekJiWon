<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="${article.title }" />
<%@ include file="../common/head.jspf"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<style>
.container {
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
	background-color: #5C5470;
	font-weight: bold;
	color: white;
}

.table-cell {
	background-color: white;
}

.replyHD {
	background-color: #5C5470;
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
<!-- 리액션 실행 코드 -->
<script>
	$(function() {
		ArticleDetail__increaseHitCount();
	});
	
	$(function() {
		checkAddRpBefore();
		});
</script>



 
<!-- 변수 생성 -->
<script>
	const params = {};
	params.id = parseInt('${param.id}');
	params.memberId = parseInt('${loginedMemberId}');
	
	var isAlreadyAddGoodRp = ${isAlreadyAddGoodRp};
	var isAlreadyAddBadRp = ${isAlreadyAddBadRp};
	var isAlreadyAddLikeRp =${isAlreadyAddLikeRp};
</script>

<!-- 조회수 관련 -->
<!-- <iframe src="http://localhost:8081/usr/article/doIncreaseHitCountRd?id=2" frameborder="0"></iframe> -->


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


<!-- 변수 값에 따라 각 id가 부여된 버튼에 클래스 추가(이미 눌려있다는 색상 표시) -->
<script>
	function checkAddRpBefore() {
		if (isAlreadyAddGoodRp == true) {
			$('#likeButton').removeClass('btn-outline').addClass('btn-danger');
		} else if (isAlreadyAddBadRp == true) {
			$('#DislikeButton').removeClass('btn-outline').addClass('btn-danger');
		} else {
			return;
		}
		if (isAlreadyAddLikeRp == true) {
			$('#likeButton').removeClass('btn-outline').addClass('btn-danger');
		}
	};
</script>



<!-- 좋아요, 싫어요 관련 -->
<script>
	function doGoodReaction(articleId) {
		$.ajax({
            url: '/usr/reactionPoint/doGoodReaction',
            type: 'POST',
            data: {relTypeCode: 'article', relId: articleId},
            dataType: 'json',
            success: function(data) {
                if (data.resultCode.startsWith('S-')) {
                    var likeButton = $('#likeButton');
                    var likeCount = $('#likeCount');
                    var DislikeButton = $('#DislikeButton');
                    var DislikeCount = $('#DislikeCount');

                    if (data.resultCode == 'S-1') {
                        likeButton.removeClass('btn-danger').addClass('btn-outline');
                        likeCount.text(parseInt(likeCount.text()) - 1);
                    } 
                    else if (data.resultCode == 'S-2') {
                    	DislikeButton.removeClass('btn-danger').addClass('btn-outline');
                        DislikeCount.text(parseInt(DislikeCount.text()) - 1);
                        likeButton.removeClass('btn-outline').addClass('btn-danger');
                        likeCount.text(parseInt(likeCount.text()) + 1);
                    }
                    else {
                        likeButton.removeClass('btn-outline').addClass('btn-danger');
                        likeCount.text(parseInt(likeCount.text()) + 1);
                    }
                  
                } 
                else {
                    alert(data.msg);
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('오류가 발생했습니다: ' + textStatus);
            }
        });
	}
	
	function doBadReaction(articleId) {
		$.ajax({
            url: '/usr/reactionPoint/doBadReaction',
            type: 'POST',
            data: {relTypeCode: 'article', relId: articleId},
            dataType: 'json',
            success: function(data) {
                if (data.resultCode.startsWith('S-')) {
                	var likeButton = $('#likeButton');
                    var likeCount = $('#likeCount');                	
                    var DislikeButton = $('#DislikeButton');
                    var DislikeCount = $('#DislikeCount');

                    if (data.resultCode == 'S-1') {
                    	DislikeButton.removeClass('btn-danger').addClass('btn-outline');
                    	DislikeCount.text(parseInt(DislikeCount.text()) - 1);
                    } else if (data.resultCode == 'S-2') {
                    	likeButton.removeClass('btn-danger').addClass('btn-outline');
                    	likeCount.text(parseInt(likeCount.text()) - 1);
                    	DislikeButton.removeClass('btn-outline').addClass('btn-danger');
                        DislikeCount.text(parseInt(DislikeCount.text()) + 1);
                    } else {
                    	DislikeButton.removeClass('btn-outline').addClass('btn-danger');
                        DislikeCount.text(parseInt(DislikeCount.text()) + 1);
                    }
                } 
                else {
                    alert(data.msg);
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('오류가 발생했습니다: ' + textStatus);
            }
        });
	}
	
	
	function doLikePoint(articleId) {
		$.ajax({
            url: '/usr/likePoint/doLikePoint',
            type: 'POST',
            data: {relTypeCode: 'article', relId: articleId},
            dataType: 'json',
            success: function(data) {
                if (data.resultCode.startsWith('S-')) {
                    var loveButton = $('#loveButton');
                    var loveCount = $('#loveCount');
                
                    if (data.resultCode == 'S-1') {
                    	loveButton.removeClass('btn-danger').addClass('btn-outline');
                    	loveCount.text(parseInt(loveCount.text()) - 1);
                    } 
                    else if (data.resultCode == 'S-3') {
                    	loveButton.removeClass('btn-outline').addClass('btn-danger');
                    	loveCount.text(parseInt(loveCount.text()) + 1);
                
                    	
                    }
                    else {
                    	loveButton.removeClass('btn-outline').addClass('btn-danger');
                        loveCount.text(parseInt(loveCount.text()) + 1);
                    }
                  
                } 
                else {
                    alert(data.msg);
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('오류가 발생했습니다: ' + textStatus);
            }
        });
	}
	
	
	
	
</script>
 


 



<section class="text-xl">
	<div class="container">
		<div class="table-box-type-1">



			<table class="table">
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
					<tr>
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
						<th class="table-header">첨부 이미지</th>
						<td>
							<img class="w-full rounded-xl" src="${rq.getImgUri(article.id)}"
								onerror="${rq.profileFallbackImgOnErrorHtml}" alt="" />
							<div>${rq.getImgUri(article.id)}</div>
						</td>
					</tr>
					<tr>
					 
						<c:if test="${article.boardId eq 5 || article.boardId eq 6 || article.boardId eq 7 || article.boardId eq 8}">
							<tr>
								<th class="table-header">추천</th>
								<td class="table-cell">



									<div class="btn_box">
										<button id="likeButton" class="btn btn-ghost" " type="button" onclick="doGoodReaction(${param.id})">
											좋아요 👍
											<span id="likeCount">${article.goodReactionPoint}</span>
										</button>

										<button id="DislikeButton" class="btn btn-ghost" " type="button" onclick="doBadReaction(${param.id})">
											싫어요 👎
											<span id="DislikeCount">${article.badReactionPoint}</span>
										</button>
									</div>
						</c:if>
					 
						<c:if test="${article.boardId eq 1 || article.boardId eq 2 || article.boardId eq 3 || article.boardId eq 4}">
							<tr>
								<th class="table-header">찜</th>
								<td class="table-cell">
								 

									<c:if test="${actorCanMakeLike}">
										<div>
										 
												<button id="loveButton" class="btn btn-ghost" " type="button" onclick="doLikePoint(${param.id})">
													❤
													<span id="loveCount">${article.likePoint}</span>
												</button>
											 
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


		<br />
		<button class="custom-btn btn-7" type="button" onclick="history.back();">뒤로가기</button>
		<c:if test="${rq.getLoginedMemberId()==article.memberId }">
		<a class="custom-btn btn-7" href="../article/modify?id=${article.id }">수정</a>
		<a class="custom-btn btn-7" onclick="if(confirm('정말 삭제하시겠습니까?')==false) return false;"
			href="doDelete?id=${article.id }">삭제</a>
	</c:if>
	</div>

	 


</section>

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
									<textarea class="input input-bordered w-full max-w-xs" type="text" name="body" placeholder="내용을 입력해주세요" /></textarea>
								</td>
							</tr>
							<tr>
								<th></th>
								<td>
									<button type="submit" value="작성" />
									댓글 작성
									</button>
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
</section>
<section class="mt-5">
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
					<th>번호</th>
					<th>날짜</th>
					<th>작성자</th>
					<th>추천</th>
					<th>내용</th>
					<th>수정</th>
					<th>삭제</th>
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
</section>


<style>
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
</style>

<%@ include file="../common/foot.jspf"%>