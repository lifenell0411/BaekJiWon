<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>다 문신사랑 해</title>
</head>
<body>
 
<a href="/usr/home/main"">
  <span class="left">다 문신사랑 해</span>
</a>
</body>
</html>
<style>/*--------------------
Body
--------------------*/
*,
*::before,
*::after {
  box-sizing: border-box;
}
a{
   text-decoration: none;
}
body {
  padding: 50px 0;
  min-height: 100vh;
  margin: 0;  
  background: radial-gradient(ellipse farthest-corner at center bottom, #5C5470, #5C5470);
  color: #5C5470;
  font-size: 14px;
  font-family: 'Roboto', sans-serif;
  font-weight: 900;
  font-size: 80px;
  letter-spacing: 0.02em;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  
  @media (max-width: 400px) {
    font-size: 50px;
  }
}


span {
  cursor: pointer;
  -webkit-text-stroke-color: #fff;
  -webkit-text-stroke-width: 0.02em;
  -webkit-text-fill-color: transparent;
  -webkit-background-clip: text;
  background-repeat: no-repeat;
  transition: background-size .5s cubic-bezier(0.67, 0.01, 0.15, 0.98);
}

.top {
   background-image: linear-gradient(180deg, #fff 0%, #fff 50%, transparent 50.1%);
  background-position: 100% 0%;
  background-size: 100% 0%;
 
  &:hover {
    background-size: 100% 200%;
  }
}

.right {
   background-image: linear-gradient(270deg, #fff 0%, #fff 50%, transparent 50.1%);
  background-position: 100% 0%;
  background-size: 0% 100%;
 
  &:hover {
    background-size: 200% 100%;
  }
}

.bottom {
   background-image: linear-gradient(0deg, #fff 0%, #fff 50%, transparent 50.1%);
  background-position: 100% 100%;
  background-size: 100% 0%;
 
  &:hover {
    background-size: 100% 200%;
  }
}

.left {
   background-image: linear-gradient(90deg, #fff 0%, #fff 50%, transparent 50.1%);
  background-size: 0% 100%;
 
  &:hover {
    background-size: 200% 100%;
  }
}

.angle {
   background-image: linear-gradient(135deg, #fff 0%, #fff 50%, transparent 50.1%);
  background-size: 0% 100%;
 
  &:hover {
    background-size: 220% 100%;
  }
}

.radial {
  background-image: radial-gradient(circle farthest-corner at center center, #fff 0%, #fff 50%, transparent 50.1%);
  background-position: 50% 50%;
  background-size: 0% 0%;
  
  &:hover {
    background-size: 180% 400%; // Depends by text size
  }
}</style>