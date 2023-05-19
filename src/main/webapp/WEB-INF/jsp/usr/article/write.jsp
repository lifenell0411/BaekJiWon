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
								<button class="custom-btn btn-7" type="submit" value="작성" />
								작성
								</button>
							</td>
						</tr>
					</tbody>

				</table>
			</form>
		</div>
		
	</div>
	
 <div class="btns">
			<button class="custom-btn btn-7" type="button" onclick="history.back();">뒤로가기</button>


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

<style>


.container {margin-top: 300px;
border : 2px solid #5C5470;
padding: 40px 40px;
   border-radius: 10px;
}

 .btns {
  margin-top: 50px;
 margin-left: 1500px;
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
</style>

<%@ include file="../common/foot.jspf"%>