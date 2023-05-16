<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jspf"%>
<hr />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>



<div class="container">
	<div class="card card0">

		<div class="border">
			<h2>M_Nu</h2>
		</div>
		<div class="text">2020년 7월 강북구 수유에서 타투를 처음 시작하고... 리얼리즘과 감성타투....위주 작업 상담을 통해 최대한 의뢰인의 생각과 가깝고 감각적인 디자인을 뽑아내는
			작업을 하길 선호....그렇기 때문에 넉넉하게 시간을 잡고 상담을하기원함 하실말씀 사연이 있거나 스토리가 있는 타투도 좋지만 타투를 받는 그 자체가 의미가 생기는 일이라고 생각합니다..</div>
	</div>
	<div class="card card1">
		<div class="border">
			<h2>사화</h2>
		</div>
		<div class="text1">안녕하세요 사화입니다.</div>
	</div>
</div>
<style>
.container {
	height: 100vh;
	width: 100vw;
	max-height: 800px;
	max-width: 1280px;
	min-height: 600px;
	min-width: 1000px;
	margin-top: 600px;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: flex-start;
	 
	position: relative;
}

.con {height 300px;background-color red;width 500px;
	display: none;
}

.text {
	display: none;
	position: absolute;
	top: 50%;
	left: 350px;
	transform: translateY(-50%);
	padding: 10px;
	width: 400px;
	color: black;
	border-radius: 5px;
	transition: width 1s;
}

.border {
	height: 369px;
	width: 290px;
	background: transparent;
	border-radius: 10px;
	transition: border 1s;
	position: relative;
}

.border:hover {
	border: 1px solid white;
}

.card {
	height: 379px;
	width: 300px;
	background: grey;
	border-radius: 10px;
	transition: background 0.8s;
	overflow: visible;
	background: black;
	box-shadow: 0 70px 63px -60px #000000;
	display: flex;
	justify-content: center;
	align-items: center;
	position: relative;
	margin-bottom: 40px;
}

.card0 {
	background:
		url('https://github.com/lifenell0411/23_05_project/assets/113515172/ba15c05d-d3ae-4005-a058-fc76c444b355')
		center center no-repeat;
	background-size: 400px;
}

.card0:hover {
	background:
		url('https://github.com/lifenell0411/23_05_project/assets/113515172/ba15c05d-d3ae-4005-a058-fc76c444b355')
		left center no-repeat;
	background-size: 600px;
}

.card0:hover h2, .card0:hover .fa {
	opacity: 1;
}

.card:hover .text {
	display: block;
	color: black;
}

.card1 {
	background:
		url('https://i.pinimg.com/originals/28/d2/e6/28d2e684e7859a0dd17fbd0cea00f8a9.jpg')
		center center no-repeat;
	background-size: 300px;
}

.card1:hover {
	background:
		url('https://i.pinimg.com/originals/28/d2/e6/28d2e684e7859a0dd17fbd0cea00f8a9.jpg')
		left center no-repeat;
	background-size: 600px;
}

.card1:hover h2, .card1:hover .fa {
	opacity: 1;
}

h2 {
	font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
	color: white;
	margin: 20px;
	opacity: 0;
	transition: opacity 1s;
}

.fa {
	opacity: 0;
	transition: opacity 1s;
}

.card:hover .con {width 500px;background-color red;
	
}
</style>


<%@ include file="../common/foot.jspf"%>