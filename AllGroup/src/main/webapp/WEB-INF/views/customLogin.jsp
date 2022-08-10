<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">


<!-- Bootstrap Core CSS -->
<link href="/resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="/resources/vendor/metisMenu/metisMenu.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="/resources/vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<style>
.modal-container {
	display: none;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
}

.modal-container.show-modal {
	display: block;
}

.modal {
	background: white;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, .3);
	position: absolute;
	overflow: hidden;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	max-width: 100%;
	width: 400px;
	animation-name: modalopen;
	animation-duration: var(- -modal-duration);
}

@import
	url("//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css")
	;

.login-block {
	background: #DE6262; /* fallback for old browsers */
	background: -webkit-linear-gradient(to bottom, #FFB88C, #DE6262);
	/* Chrome 10-25, Safari 5.1-6 */
	background: linear-gradient(to bottom, #FFB88C, #DE6262);
	/* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
	float: left;
	width: 100%;
	padding: 300px 0;
}

.banner-sec {
	background: url(https://static.pexels.com/photos/33972/pexels-photo.jpg)
		no-repeat left bottom;
	background-size: cover;
	min-height: 500px;
	border-radius: 0 10px 10px 0;
	padding: 0;
}

.container {
	background: #fff;
	border-radius: 10px;
	box-shadow: 15px 20px 0px rgba(0, 0, 0, 0.1);
}

.carousel-inner {
	border-radius: 0 10px 10px 0;
}

.carousel-caption {
	text-align: left;
	left: 5%;
}

.login-sec {
	padding: 50px 30px;
	position: relative;
}

.login-sec .copy-text {
	position: absolute;
	width: 80%;
	bottom: 20px;
	font-size: 13px;
	text-align: center;
}

.login-sec .copy-text i {
	color: #FEB58A;
}

.login-sec .copy-text a {
	color: #E36262;
}

.login-sec h2 {
	margin-bottom: 30px;
	font-weight: 800;
	font-size: 30px;
	color: #DE6262;
}

.login-sec h2:after {
	content: " ";
	width: 100px;
	height: 5px;
	background: #FEB58A;
	display: block;
	margin-top: 20px;
	border-radius: 3px;
	margin-left: auto;
	margin-right: auto
}

.btn-login {
	background: #DE6262;
	color: #fff;
	font-weight: 600;
}
</style>
<body>

	<div id="myModal" class="modal-container" id="modal">
		<div class="modal-dialog" id="modal">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">
					<p style="text-align: center;">로그아웃 되었습니다.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">확인</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->




	<!------ Include the above in your HEAD tag ---------->

	<section class="login-block">
		<div class="container">
			<div class="row">
				<div class="col-md login-sec">
					<h2 class="text-center">ALL-GROUP</h2>


					<form action="/login" method="POST" class="login-form">
						<fieldset>
							<div class="form-group">
								<label for="exampleInputEmail1" class="text-uppercase">ID</label>
								<input class="form-control" placeholder="아이디를 입력하세요."
									name="username" type="text" autofocus>
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1" class="text-uppercase">Password</label>
								<input class="form-control" placeholder="패스워드를 입력하세요."
									name="password" type="password" value="">
							</div>
							<div class="form-check checkbox">
								<label class="form-check-label"> <input
									class="form-check-input" name="remember-me" type="checkbox">
									<small>Remember Me</small>
								</label>
							</div>

							<a href="index.html" class="btn btn-lg btn-success btn-block">Login</a>

							<br />

							<div style="text-align: center;">
								<button type="submit" class="btn btn-default"
									style="background-color: transparent; boarder: 0; outline: 0;">아이디/비번찾기</button>
									<button type="submit" class="btn btn-default"
									style="background-color: transparent; boarder: 0; outline: 0;">이메일로찾기</button>
								<button type="submit" class="btn btn-default" onclick="location.href='/member/joinForm'"
									style="background-color: transparent; boarder: 0; outline: 0;">회원가입</button>
							</div>
						</fieldset>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>


				</div>
	</section>




	<!-- jQuery -->
	<script src="/resources/vendor/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="/resources/vendor/metisMenu/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="/resources/dist/js/sb-admin-2.js"></script>





	<script>
		var modal = $("#myModal");

		$(".btn-success").on("click", function(e) {

			e.preventDefault();
			$("form").submit();

		});
	</script>

	<c:if test="${param.logout != null}">
		<script>
			$(document).ready(function() {
				//alert("로그아웃하였습니다.");
				$('#myModal').modal('show');
			});
		</script>
	</c:if>

</body>

</html>




