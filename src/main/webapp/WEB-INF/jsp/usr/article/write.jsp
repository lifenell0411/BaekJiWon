<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE WRITE" />
<%@ include file="../common/head.jspf"%>
<%@ include file="../common/toastUiEditorLib.jspf"%>
<hr />

<!-- Article modify 관련 -->
<script type="text/javascript">
	let ArticleWrite__submitFormDone = false;

	function ArticleWrite__submit(form) {
		if (ArticleWrite__submitFormDone) {
			return;
		}

		form.title.value = form.title.value.trim();
		if (form.title.value == 0) {
			alert('제목을 입력해주세요');
			return;
		}

		const editor = $(form).find('.toast-ui-editor').data(
				'data-toast-editor');
		const markdown = editor.getMarkdown().trim();

		if (markdown.length == 0) {
			alert('내용 써라');
			editor.focus();
			return;
		}

		form.body.value = markdown;

		ArticleWrite__submitFormDone = true;
		form.submit();

	}
	
	
	
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<form action="../article/doWrite" method="POST" onsubmit="ArticleWrite__submit(this); return false;"
				enctype="multipart/form-data">
				<input type="hidden" name="body">
				<table>
					<colgroup>
						<col width="200" />
					</colgroup>

					<tbody>
						<tr>
							<th>작성자</th>
							<td>
								<div>${rq.loginedMember.nickname }</div>
							</td>
						</tr>
						<tr>
							<th>게시판</th>
							<td>
								<select class="select select-ghost w-full max-w-xs" name="boardId">
									<!-- 									<option selected="selected" disabled>게시판을 선택해주세요</option> -->
									<option value="5">타투문의</option>
									<option value="6">자유</option>
									<option value="7">QNA</option>
									<option value="8">타투후기</option>


								</select>
							</td>
						</tr>
						<tr>
							<th>제목</th>
							<td>
								<input class="input input-bordered w-full max-w-xs" type="text" name="title" placeholder="제목을 입력해주세요" />
							</td>
						</tr>
						
						<tr>
							<th>첨부 이미지</th>
							<td>
							<input name="file__article__0__extra__Img__1" placeholder="이미지를 선택해주세요" type="file" />
							</td>
						</tr>
						<tr>
							<th>내용</th>
							<td>
								<%-- 								<textarea class="input input-bordered w-full max-w-xs" type="text" name="body" placeholder="내용을 입력해주세요" />${article.body }</textarea> --%>
								<div class="toast-ui-editor">
									<script type="text/x-template">
      </script>
								</div>
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<button type="submit" value="작성" />
								작성
								</button>
							</td>
						</tr>
					</tbody>

				</table>
			</form>
		</div>
		<div class="btns">
			<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>


		</div>
	</div>
	
 
</section>

<script>const form = document.getElementById('uploadForm');
form.addEventListener('submit', (e) => {
    e.preventDefault();

    const formData = new FormData(form);

    fetch('/photos/upload', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())  // JSON 형식으로 응답 받음
    .then(data => {
        // 이미지 URL을 받아와서 처리하는 로직 구현
        const imageUrl = data.imageUrl;  // 수정: imageUrl 필드를 사용

        // 이미지를 표시할 <img> 태그 생성
        const imageElement = document.createElement('img');
        imageElement.src = imageUrl;

        // 이미지를 표시할 컨테이너 요소에 이미지 추가
        const imageContainer = document.getElementById('imageContainer');
        imageContainer.appendChild(imageElement);
    })
    .catch(error => {
        // 오류 처리
        console.error('Upload error:', error);
    });
});

</script>

<%@ include file="../common/foot.jspf"%>