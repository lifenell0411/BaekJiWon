<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


<!-- 유용한 링크 -->
<!-- cdnsj : https://cdnjs.com/ -->
<!-- 폰트어썸 아이콘 리스트 : https://fontawesome.com/icons?d=gallery&m=free -->

<!-- 구글 폰트 불러오기 -->
<!-- rotobo(400/700/900), notosanskr(400/600/900) -->
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700;900&family=Roboto:wght@400;700;900&display=swap" rel="stylesheet">

<!-- 폰트어썸 불러오기 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css">

<!-- 제이쿼리 불러오기 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>

<div class="top-bar">
  <div class="con height-100p flex flex-jc-sa">
    <a href="#" class="logo flex flex-ai-c">
      <img src="https://kakaopaysec.com/resources/images/bi.png" alt="">
    </a>

    <nav class="menu-box-1">
      <div class="bg"></div>
      <ul class="height-100p flex">
        <li>
          <a href="#" class="flex flex-ai-c height-100p">HOME</a>
           
        </li>
        <li>
          <a href="#" class="flex flex-ai-c height-100p">ABOUT</a>
         
        </li>
        <li>
          <a href="#" class="flex flex-ai-c height-100p">타투도안</a>
          <ul>
            <li>	<a href="/usr/article/list?boardId=1">레터링</a>
						</li>
						<li class="hover:underline">
							<a href="/usr/article/list?boardId=2">미니타투</a>
						</li>
						<li class="hover:underline">
							<a href="/usr/article/list?boardId=3">블랙워크</a>
						</li>
						<li class="hover:underline">
							<a href="/usr/article/list?boardId=3">ETC</a>
						</li>
          </ul>
        </li>
        <li>
          <a href="/usr/article/list" class="flex flex-ai-c height-100p">게시판</a>
          <ul>
            <li><a href="#">소비자보호포털</a></li>
            <li><a href="#">계좌개설안내</a></li>
            <li><a href="#">예탁금이용안내</a></li>
            <li><a href="#">수수료안내</a></li>
            <li><a href="#">상속업무안내</a></li>
            <li><a href="#">신용공여 및 매매 관련 안내</a></li>
            <li><a href="#">기타업무안내</a></li>
            <li><a href="#">고객유의사항</a></li>
            <li><a href="#">약관/서식</a></li>
            <li><a href="#">주식공지</a></li>
            <li><a href="#">상품공지</a></li>
            <li><a href="#">공지사항</a></li>
            <li><a href="#">FAQ</a></li>
            <li><a href="#">이해상충 및 정보교류차단 주요내용</a></li>
            <li><a href="#">해외주식 시세 관련 유의사항</a></li>
          </ul>
        </li>
        <li>
          <a href="#" class="flex flex-ai-c height-100p">채용정보</a>
          <ul>
            <li><a href="#">채용공고</a></li>
          </ul>
        </li>
      </ul>
    </nav>
  </div>
</div>

<div class="page-1">
  <div class="img-box">
    <img src="https://kakaopaysec.com/resources/images/main-visual01.jpg" alt="">
  </div>
</div>
</body>
</html>