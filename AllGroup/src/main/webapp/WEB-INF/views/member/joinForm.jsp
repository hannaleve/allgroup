<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<%@include file="../includes/header.jsp"%>


<link rel="stylesheet" href="/resources/css/joinForm.css">
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<style>
* {
	font-family: 'InfinitySans-RegularA1';
}

img {
	width: 100px;
	height: 100px;
	border-radius: 70px;
	overflow: hidden;
	object-fit: cover;
}

select {
	width: 300px;
	font-family: inherit;
	background:
		url(https://farm1.staticflickr.com/379/19928272501_4ef877c265_t.jpg)
		no-repeat 95% 50%;
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;
	border: 1px solid #999;
	border-radius: 0px;
	text-align-last: center;
	margin: 9px;
}

select::-ms-expand { /* for IE 11 */
	display: none;
}

#mail_btn {
	font-size: 10px;
	margin: -40px;
	margin-top: -125px;
	border: 1px solid #5b18ff;
	border-radius: 3px;
	background-color: #fff;
	color: #5b18ff;
}

#mail_btn2 {
	font-size: 10px;
	margin-top: -5px;
	margin: -3px;
	border: 1px solid #5b18ff;
	border-radius: 3px;
	background-color: #fff;
	color: #5b18ff;
}
</style>


<div class="member__modify">
	<div id="member__modify__form">


		<form role="form" action="/member/joinForm" method="post"
			name="subForm" id="subForm">

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

			<table class="insertTable">
				<tr>
					<td colspan="2">
						<h1 id="modify_name">회원가입</h1>
					</td>
				</tr>
				<%-- 			<tr>
					<td><label for="modify_img">프로필 사진</label></td>
					<th style="height: 100px;">

						<div id="image_container">
							<div id="myImgDiv2">
								<img class="UserModifyImg"
									src="${path}/upload/userProfileImg/${loginMember.user_imgRename}"
									class="firstImg"
									style="width: 100px; height: 100px; border-radius: 100px; object-fit: cover;"
									accept=".gif, .jpg, .png">
							</div>
						</div>

					</th>
				</tr> --%>
				<tr>
					<td></td>
					<td id="imgTd">
						<!-- <input type="file" id="modify_img" type="text" name="user_img" value="" style=""> -->
						<input type="file" id="image" accept="image/*" name="user_img"
						onchange="setThumbnail2(event);" />
					</td>
				</tr>
				<tr>
					<td><label for="user_id">아이디</label></td>
					<td><input id="user_id" type="text" name="USER_ID"
						placeholder="아이디를 입력하세요." required></td>
					<td><div class="check_font" id="id_check"></div></td>
				</tr>
				<tr>
					<td><label for="user_pwd">비밀번호</label></td>
					<td><input id="user_pwd" type="text" name="USER_PWD"
						placeholder="비밀번호를 입력하세요." required></td>
					<td><div class="check_font" id="pw_check"></div></td>
				</tr>

				<tr>
					<td><label for="user_pwd2">비밀번호 확인</label></td>
					<td><input id="user_pwd2" type="text" name="user_pwd2"
						placeholder="비밀번호 확인" required></td>
					<td><div class="check_font" id="pw2_check"></div></td>
				</tr>



				<tr>
					<td><label for="user_name">이름</label></td>
					<td><input id="user_name" type="text" name="USER_NAME"
						placeholder="실명을 입력해주세요."></td>
					<td><div class="check_font" id="name_check"></div></td>
				</tr>
				<tr>
					<td><label for="modify_Grade">직급을 선택해주세요.</label></td>

					<td><select class="modify_input" id="USER_RANK"
						name="USER_RANK">
							<option selected>-----</option>
							<option value="인턴">인턴</option>
							<option value="사원">사원</option>
							<option value="선임">선임</option>
							<option value="책임">책임</option>
							<option value="수석">수석</option>
							<option value="이사">이사</option>
					</select></td>

				</tr>

				<tr>
					<td><label for="user_email">이메일</label></td>
					<td><input class="modify_input" name="USER_EMAIL"
						id="user_email" type="text" placeholder="E-mail" required>
					</td>
					<td><div class="check_font" id="email_check"></div></td>
				</tr>


				<tr>
					<td><label for="user_email"></label></td>
					<td><input type="text" style="margin-top: 5px;"
						class="modify_input" name="email_confirm" id="email_confirm"
						placeholder="인증번호 입력" required>

						<button type="button" class="btn btn-outline-info btn-sm px-3"
							id="mail_btn2" onclick="fn_checkCode()">확인</button>

						<button type="button" class="btn btn-outline-danger btn-sm px-3"
							id="mail_btn" onclick="fn_sendEmail_Ajax()">
							<i class="fa fa-envelope"></i>&nbsp;인증번호 전송
						</button> <br /> &nbsp;</td>

				</tr>




				<tr>
					<td><label for="user_phone">휴대전화</label></td>
					<td><input type="text" class="form-control" id="user_phone"
						name="USER_PHONE" placeholder="'-' 구분없이 입력" required>
						<div class="check_font" id="phone_check"></div></td>
				</tr>
				<tr>
					<td><label for="modify_Grade">부서명을 선택해주세요.</label></td>

					<td><select id="DEPT_NAME" name="DEPT_NAME">
							<option selected>-----</option>
							<option value="디자인팀">디자인팀</option>
							<option value="응용솔루션사업팀">응용솔루션사업팀</option>
							<option value="인프라팀">인프라팀</option>
							<option value="연구개발팀">연구개발팀</option>
							<option value="경영지원팀">경영지원팀</option>
							<option value="영업팀">영업팀</option>
					</select></td>

				</tr>




				<tr>
					<td>
						<button class="btn" id="reg_submit_addr" type="button"
							onclick="sample6_execDaumPostcode();">주소찾기</button>
					</td>
					<td><input class="modify_input" name="USER_ADDRES"
						id="kakao_postcode" type="text" value="${arr[0]}"
						readonly="readonly"></td>
				</tr>
				<tr>
					<td></td>
					<td><input class="modify_input" name="USER_ADDRES1"
						id="kakao_address" type="text" value="${arr[1]}"
						readonly="readonly"></td>
				</tr>
				<tr>
					<td></td>
					<td><input class="modify_input" name="USER_ADDRES2"
						id="kakao_detailAddress" type="text" value="${arr[2]}"></td>
				</tr>
				<tr>
					<td></td>
					<td><input class="modify_input" name="USER_ADDRES3"
						id="kakao_extraAddress" type="text" value="${arr[3]}"
						readonly="readonly"></td>
				</tr>
			</table>
			<div id="modify_button">
				<button class="btn" id="reg_submit" type="submit">가입하기</button>
			</div>
		</form>



	</div>
</div>


<script>
	///---------유효성 검사(정규식체크)----------------

	//모든 공백 체크 정규식
	var empJ = /\s/g;
	//아이디 정규식
	var idJ = /^[a-z0-9]{4,12}$/;
	// 비밀번호 정규식
	var pwJ = /^[A-Za-z0-9]{4,12}$/;
	// 이름 정규식
	var nameJ = /^[가-힣]{2,6}$/;
	// 이메일 검사 정규식
	var mailJ = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	// 휴대폰 번호 정규식
	var phoneJ = /^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$/;

	/*
	
	 1. 값 입력 시 유효성검사 실시 (입력 후 포커스 떠났을 시 + 유효성검사 + 정규식 검사)
	   1.1 아이디 입력하고 포커스 떠났을 시 + 아이디 유효성검사 (1 = 중복  /  0 != 중복) 
	   1.2 비밀번호 입력하고 포커스 떠났을 시 + 유효성 검사 + 정규식 검사 
	   1.3 패스워드 일치 확인 (유효성검사)
	   1.4 이름에 특수문자 들어가지 않도록 설정 (이름 입력하고 포커스 떠났을 시 + 유효성 검사 + 정규식 검사 )
	   1.5 직급선택
	   1.6 부서명선택
	   1.7 휴대전화 입력하고 포커스 떠났을 시 + 유효성 검사 + 정규식 검사
	 2. 가입하기 실행 버튼 클릭 시 유효성 검사를 실시
	   2.1 비밀번호가 같은 경우 && 비밀번호 정규식
	   2.2 이름 정규식
	   2.3 이메일 정규식
	   2.4 휴대폰번호 정규식
	
	 */

	         $(document).ready(function() {

// 1. 값 입력 시 유효성검사 실시 (입력 후 포커스 떠났을 시 + 유효성검사 + 정규식 검사)

						// 1.1 아이디 입력하고 포커스 떠났을 시 + 아이디 유효성검사 (1 = 중복  /  0 != 중복) 
						$("#user_id")
								.blur(
										function() {

											var user_id = $('#user_id').val();
											$.ajax({
														url : '/member/idCheck?userId='+ user_id, //userId - @RequestParam value
														type : 'get',
														beforeSend : function(xhr) {
															xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
														},
														success : function(data) {
															console.log("1 = 중복o / 0 = 중복x : "+ data);

															if (data == 1) { //아이디가 중복됐을 시 
																// 1 : 아이디가 중복되는 문구
																$("#id_check").text("사용중인 아이디입니다");
																$("#id_check").css("color","red");
																$("#reg_submit").attr("disabled",true);
															} else { //아이디가 중복되지 않았을 시 

																if (idJ.test(user_id)) { //test() 함수안에 들어가는 값은 user_id의 값 으로 아이디 정규식 체크 
																	// 0 : 아이디 길이 / 문자열 검사
																	$("#id_check").text("");
																	$("#reg_submit").attr("disabled",false);

																} else if (user_id == "") { //정규식 맞지 않는경우 -  아이디 값이 없을 시

																	$('#id_check').text('아이디를 입력해주세요 :)');
																	$('#id_check').css('color','red');
																	$("#reg_submit").attr("disabled",true);

																} else { //정규식 맞지 않는경우 

																	$('#id_check').text("아이디는 소문자와 숫자 4~12자리만 가능합니다.");
																	$('#id_check').css('color','red');
																	$("#reg_submit").attr("disabled",true);
																}
															}
														},
														error : function() {
															console.log("실패");
														}

													}); //ajax

										}); //blur

						// 1.2 비밀번호 입력하고 포커스 떠났을 시 + 유효성 검사 + 정규식 검사 
						$("#user_pw").blur(function() {
							if (pwJ.test($('#user_pw').val())) {
								console.log('true');
								$("#pw_check").text('');
							} else {
								console.log('false');
								$('#pw_check').text('숫자 or 문자로만 4~12자리 입력');
								$('#pw_check').css('color', 'red');
							}
						});

						// 1.3 패스워드 일치 확인 (유효성검사)
						$("#user_pw2").blur(function() {
							if ($('#user_pw').val() != $(this).val()) { //user_pw 값과 현재 입력한 값이 다르다면 
								$('#pw2_check').text('비밀번호가 일치하지 않습니다.');
								$('#pw2_check').css('color', 'red');
							} else {
								$("#pw2_check").text('');
							}
						});

						// 1.4 이름에 특수문자 들어가지 않도록 설정 (이름 입력하고 포커스 떠났을 시 + 유효성 검사 + 정규식 검사 )
						$("#user_name").blur(function() {
							if (nameJ.test($(this).val())) {
								console.log(nameJ.test($(this).val()));
								$("#name_check").text('');
							} else {
								$('#name_check').text('이름을 확인해주세요');
								$('#name_check').css('color', 'red');
							}
						});

						// 1.5 직급선택
						$('#USER_RANK').blur(function() {

							user_rank = $("#USER_RANK option:selected").val();
							//console.log($("select[name=selectCheck1]").val())
							console.log(user_rank)
						});

						// 1.6 부서명선택
						$('#DEPT_NAME').blur(function() {
							dept_name = $("#DEPT_NAME option:selected").val();
							//console.log($("select[name=selectCheck2]").val())
							console.log(dept_name)
						});

						// 1.7  휴대전화 입력하고 포커스 떠났을 시 + 유효성 검사 + 정규식 검사
						$('#user_phone').blur(function() {
							if (phoneJ.test($(this).val())) {
								console.log(nameJ.test($(this).val()));
								$("#phone_check").text('');
							} else {
								$('#phone_check').text('휴대폰번호를 확인해주세요 :)');
								$('#phone_check').css('color', 'red');
							}
						});
						

// 2. 가입하기 실행 버튼 클릭 시 유효성 검사를 실시

						$('form').on('submit',
								function() {

									var inval_Arr = new Array(4).fill(false);

									// 2.1 비밀번호가 같은 경우 && 비밀번호 정규식
									if (($('#user_pw').val() == ($('#user_pw2')
											.val()))
											&& pwJ.test($('#user_pw').val())) {
										inval_Arr[0] = true;
									} else {
										inval_Arr[0] = false;
									}
									// 2.2 이름 정규식
									if (nameJ.test($('#user_name').val())) {
										inval_Arr[1] = true;
									} else {
										inval_Arr[1] = false;
									}
									// 2.3 이메일 정규식
									if (mailJ.test($('#user_email').val())) {
										console.log(mailJ.test($('#user_email')
												.val()));
										inval_Arr[2] = true;
									} else {
										inval_Arr[2] = false;
									}
									// 2.4 휴대폰번호 정규식
									if (phoneJ.test($('#user_phone').val())) {
										console.log(phoneJ
												.test($('#user_phone').val()));
										inval_Arr[3] = true;
									} else {
										inval_Arr[3] = false;
									}

									var validAll = true;

									for (var i = 0; i < inval_Arr.length; i++) {

										if (inval_Arr[i] == false) {
											validAll = false;
										}
									}

									if (validAll) { // 유효성 모두 통과 했을 시 
										alert('회원가입을 축하합니다!');

									} else {
										alert('입력한 정보들을 다시 한번 확인해주세요 :)')

									}
								});

					});
					

	/* 

	 1. 이메일 입력하고 포커스 떠났을 시 + 이메일 중복검사(1 = 중복  /  0 != 중복)
	 2. 이메일 전송 메일 보내기
	 3. 인증코드 확인하기
	
	 */

	 
	var flag_dupl_mail = false;
	var flag_dupl_use_mail = false;

	
// 1. 이메일 입력하고 포커스 떠났을 시 + 이메일 중복검사(1 = 중복  /  0 != 중복)
	$("#user_email").blur(function() {
		var user_email = $('#user_email').val();

		$.ajax({
			url : '/member/emailCheck?user_email=' + user_email, //user_email - @RequestParam value
			type : 'get',
			beforeSend : function(xhr) {
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			},
			success : function(data) {
				console.log("1 = 중복o / 0 = 중복x : " + data);

				if (data == 1) { //이메일이 중복됐을 시 
					console.log("이메일중복!!");
					flag_dupl_use_mail = false;
				} else { //아이디가 중복되지 않았을 시 
					console.log("가입가능~~");
					flag_dupl_use_mail = true;

				}
			},
			error : function() {
				console.log("에러발생.. 이메일체크");
			}
		});

	});


// 2. 이메일 전송 메일 보내기
	var resultCode = null; //인증코드키

	function fn_sendEmail_Ajax() { 
		var user_email = $('#user_email').val(); //인증번호전송 버튼 클릭 시 발생하는 함수 
		console.log("제발 :" + user_email)

		// 메일이 입력 안되면 튕기기
		if (user_email == "" || user_email == null) {
			flag_dupl_mail = false;
			alert("이메일 주소를 입력해주세요.")

			return;
		}
		if (flag_dupl_use_mail == false) {
			flag_dupl_mail = false;
			alert("이미 가입된 이메일입니다.")
			return;
		}

		var form = {
			email : user_email

		}

		$.ajax({
			url : "/member/email",
			data : JSON.stringify(form),
			dataType : "JSON",
			type : "post",
			contentType : "application/json; charset=UTF-8",

			success : function(data) {
				alert("입력하신 이메일 주소에서 발급된 코드를 확인하세요.");

				resultCode = data.joinCode;
				console.log(resultCode)

				$("#email_confirm").show(); //인증번호입력부분 입력할 수 있도록 

			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			},
			error : function() {
				alert("네트워크가 불안정합니다. 다시 시도해 주세요.");
			}
		});

	}
	

// 3. 인증코드확인하기
	function fn_checkCode() { //인증번호 확인버튼을 눌렀을 때
		if ($('#email_confirm').val() != resultCode) { //인증코드가 일치하지 않다면
			alert("인증번호가 일치하지 않습니다.")
			$("#reg_submit").attr("disabled", true); //가입하기 버튼 누르지 못하도록 
		} else {
			$("#mail_btn2").css({
				"background-color" : "white",
				"border" : "white"
			});
			const s = document.getElementById('mail_btn2');
			s.innerText = 완료

		}
	}
	
	

// kakao 주소찾기 api
	function sample6_execDaumPostcode() {
		new daum.Postcode(
				{
					oncomplete : function(data) {
						// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

						// 각 주소의 노출 규칙에 따라 주소를 조합한다.
						// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
						var addr = ''; // 주소 변수
						var extraAddr = ''; // 참고항목 변수

						//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
						if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
							addr = data.roadAddress;
						} else { // 사용자가 지번 주소를 선택했을 경우(J)
							addr = data.jibunAddress;
						}

						// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
						if (data.userSelectedType === 'R') {
							// 법정동명이 있을 경우 추가한다. (법정리는 제외)
							// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
							if (data.bname !== ''
									&& /[동|로|가]$/g.test(data.bname)) {
								extraAddr += data.bname;
							}
							// 건물명이 있고, 공동주택일 경우 추가한다.
							if (data.buildingName !== ''
									&& data.apartment === 'Y') {
								extraAddr += (extraAddr !== '' ? ', '
										+ data.buildingName : data.buildingName);
							}
							// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
							if (extraAddr !== '') {
								extraAddr = ' (' + extraAddr + ')';
							}
							// 조합된 참고항목을 해당 필드에 넣는다.
							document.getElementById("kakao_extraAddress").value = extraAddr;

						} else {
							document.getElementById("kakao_extraAddress").value = '';
						}

						// 우편번호와 주소 정보를 해당 필드에 넣는다.
						document.getElementById('kakao_postcode').value = data.zonecode;
						document.getElementById("kakao_address").value = addr;
						// 커서를 상세주소 필드로 이동한다.
						document.getElementById("kakao_detailAddress").focus();
					}
				}).open();
	}
</script>

<%@include file="../includes/footer.jsp"%>