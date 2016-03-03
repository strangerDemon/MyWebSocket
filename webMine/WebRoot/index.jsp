<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="controller.MineAction"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="bootstrap/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="bootstrap/jquery-ui.js"></script>
<link href="bootstrap/bootstrap-combined.min.css" rel="stylesheet"
	media="screen">
<script type="text/javascript" src="bootstrap/bootstrap.min.js"></script>
<script type="text/javascript">
	//判断鼠标点击的按钮
	function whichButton(event,a,b) {
		var btnNum = event.button;
		if (btnNum == 2) {//鼠标右键
			window.location.href="rightClick?x="+a+"&y="+b;
		} else if (btnNum == 0) {//鼠标左键
			window.location.href="leftClick?x="+a+"&y="+b;
		} else if (btnNum == 1) {
			alert("您点击了鼠标中键！");
		} else {
			alert("您点击了" + btnNum + "号键，我不能确定它的名称。");
		}
	}
	//计时
	function countTime(){
		
	}
</script>
</head>
</head>
<title>web 扫雷</title>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-xs-6 span9">
				<div class="navbar">
					<div class="navbar-inner">
						<div class="container-fluid">
							<a data-target=".navbar-responsive-collapse"
								data-toggle="collapse" class="btn btn-navbar"><span
								class="icon-bar"></span><span class="icon-bar"></span><span
								class="icon-bar"></span></a> <a href="#" class="brand">网页版扫雷</a>
							<div class="nav-collapse collapse navbar-responsive-collapse">
								<ul class="nav">
									<li class="active"><a href="#">主页</a></li>
									<li class="dropdown"><a data-toggle="dropdown"
										class="dropdown-toggle" href="#">设置<strong class="caret"></strong></a>
										<ul class="dropdown-menu">
											<li><a href="mineInit?type=1">初级</a></li>
											<li><a href="mineInit?type=2">中级</a></li>
											<li><a href="mineInit?type=3">高级</a></li>
											<li class="divider"></li>
											<li><a href="#">帮助</a></li>
										</ul></li>
									<li><a href="#">记录</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div>
					<table border="1">
						<s:iterator value="nowmines" var="array" status="i">
							<tr>
								<s:iterator value="array" var="str" status="j">
									<b
										onmousedown="whichButton(event,<s:property value='#i.index'/>,<s:property value='#j.index'/>)">
										<img height="29" width="29"
										src="image/<s:property value='#str'/>.gif">
									</b>
								</s:iterator>
							</tr>
							<br />
						</s:iterator>
					</table>
					<img height="29" width="29" src="image/10.gif">
				</div>
			</div>
			<div class="col-xs-6 span3">
				<br /> <br /> <b class="brand" style="font-size: 40px;">进度</b> <br />
				<br />
				<div class="progress">
					<div class="progress-bar" aria-valuemin="0" aria-valuemax="100"
						style="width: <s:property value='percent' />%;">
						<span class="sr-only"><s:property value='percent' />% 完成</span>
					</div>
				</div>
				<br /> <br /> <b class="brand" style="font-size: 40px;">时间</b> <br />
				<br />
			</div>
		</div>
	</div>
</body>
</html>