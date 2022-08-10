<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<%@include file="../includes/header.jsp"%>

<!--댓글 버튼 CSS-->
<style>
.w-btn-neon2 {
	float: right;
	position: inherit;
	border: none;
	background: linear-gradient(90deg, rgba(129, 230, 217, 1) 0%,
		rgba(79, 209, 197, 1) 100%);
	border-radius: 1000px;
	color: darkslategray;
	cursor: pointer;
	font-weight: 700;
	transition: 0.3s;
}

.w-btn-neon2:hover {
	transform: scale(1.2);
}

.w-btn-neon2:hover::after {
	content: "";
	width: 30px;
	height: 30px;
	border-radius: 100%;
	border: 6px solid #00ffcb;
	position: absolute;
	z-index: -1;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	animation: ring 1.5s infinite;
}

#operForm {
	margin: 20px;
}

#reCol {
	height: 80px;
	border-bottom: inset;
}

#reCol2 {
	line-height: 3;
}

#Inboxs1 {
	height: 80px;
	background: white;
	border: none;
	border-right: 0px;
	border-top: 0px;
	boder-left: 0px;
	boder-bottom: 0px;
}

#Inboxs1, #Inboxs2:focus {
	outline: none;
} /* outline 테두리 없애기 */
#Inboxs2 {
	background: white;
	border: none;
	border-right: 0px;
	border-top: 0px;
	boder-left: 0px;
	boder-bottom: 0px;
}
</style>

<div class="row" id='reCol'>
	<div class="col-lg-12">
		<h1 class="panel-header">공지사항</h1>
	</div>
</div>

<div class="row" id='reCol2'>
	<div class="col-lg-12">

		<div>
			<div class="form-group">
				<b><input class="form-control" name='NOT_TITLE' id='Inboxs1'
					value='<c:out value="${notice.NOT_TITLE}"/>' readonly="readonly"></b>

				<input class="form-control" name='USER_NAME' id='Inboxs2'
					value='작성자 : <c:out value="${notice.USER_NAME}"/>'
					readonly="readonly"> <input class="form-control"
					name='NOT_REGDATE' id='Inboxs2'
					value='작성날짜 : <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${notice.NOT_REGDATE}" />'
					readonly="readonly">

				<div class="form-control" rows="15" name='NOT_CONTENT' id='Inboxs2'
					style="height: 1000px" disabled>
					<c:out value="${notice.NOT_CONTENT}" escapeXml="false"></c:out>
				</div>
			</div>


			<sec:authentication property="principal" var="pinfo" />

			<sec:authorize access="isAuthenticated()">

				<c:if test="${pinfo.username eq notice.USER_NAME}">

					<button data-oper='modify' class="btn btn-default"
						onclick="location.href='/notice/modify?NOT_NO=<c:out value="${notice.NOT_NO}" />'">수정</button>

				</c:if>
			</sec:authorize>

			<button data-oper='list' class="btn btn-info"
				onclick="location.href='/notice/list'">목록</button>

		</div>
		<form id='operForm' action="/notice/modify" method="get">
			<input type='hidden' id='NOT_NO' name='NOT_NO'
				value='<c:out value="${notice.NOT_NO}"/>'> <input
				type='hidden' name='pageNum'
				value='<c:out value="${criteria.pageNum}"/>'> <input
				type='hidden' name='amount'
				value='<c:out value="${criteria.amount}"/>'> <input
				type='hidden' name='keyword'
				value='<c:out value="${criteria.keyword}"/>'> <input
				type='hidden' name='type' value='<c:out value="${criteria.type}"/>'>
		</form>

	</div>
</div>

<!-- 댓글 -->
<div class='row'>

	<div class="col-lg-12">

		<div class="panel panel-default">
			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i> 댓글
					<button id='addReplyBtn' class="w-btn-neon2" type="button">댓글추가</button>
				<!-- 댓글추가 -->
			</div>

			<div class="panel-body">

				<ul class="chat">
					<!-- 댓글내용나오는곳 -->
				</ul>
			</div>
			<div class="panel-footer">
				<!-- 댓글페이징부분 나오는곳-->
			</div>

		</div>
	</div>
	<!-- ./ end row -->
</div>


<!-- 댓글추가모달 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">댓글</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<input type='hidden' id='NOT_NO' name='NOT_NO'
						value='<c:out value="${notice.NOT_NO}"/>'>
				</div>
				<div class="form-group">
					<label>작성자</label> <input class="form-control" name='REPLYER'>
				</div>
				<div class="form-group">
					<label>댓글</label> <input class="form-control" name='REPLY'>
				</div>
				<div class="form-group">
					<label>작성날짜</label> <input class="form-control" name='REPLYDATE'>
				</div>

			</div>


			<div class="modal-footer">
				<button id='modalModBtn' type="button" class="btn btn-warning">수정</button>
				<button id='modalRemoveBtn' type="button" class="btn btn-danger">삭제</button>
				<button id='modalRegisterBtn' type="button" class="btn btn-primary">등록</button>
				<button id='modalCloseBtn' type="button" class="btn btn-default">닫기</button>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->



<script type="text/javascript" src="/resources/js/reply.js"></script>

<script>
	//버튼(수정,목록)
	$(document).ready(function() {

		var operation = $("#operForm");

		$("button[data-oper='modify']").on("click", function() { //수정버튼을 눌렀을 경우 NOT_NO값을 같이 전달하여 저장 submit
			operForm.attr("action", "/notice/modify").submit();
		});

		$("button[data-oper='list']").on("click", function() { //만일 사용자가 list로 이동하는 경우 아직 아무런 데이터가 필요하지 않으므로 NOT_NO값을 form태그안에서 지우고 submit통해서 list페이지롱 이동
			operForm.attr("#NOT_NO").remove();
			operForm.attr("action", "/notice/list").submit();
		});
	})
</script>

<script>
	//댓글처리
	var NOT_NO = '<c:out value="${notice.NOT_NO}"/>';
	console.log("게시물번호좀나와라 : " + NOT_NO); //게시물번호

	var replyUL = $(".chat"); //댓글부분

	showList(1); //자동으로 호출 

	function showList(page) { //UI 화면 상 댓글목록보여주는 부분
		console.log("show list " + page);

		replyService
				.getList(
						{
							NOT_NO : NOT_NO,
							page : page || 1
						},
						function(replyCnt, list) {//파라미터로 페이지번호받음, 만약 파라미터(페이지번호)가 없다면 기본 1페이지로 

							console.log("replyCnt : " + replyCnt);
							console.log("list : " + list);
							console.log(list);

							if (page == -1) { //마지막페이지
								pageNum = Math.ceil(replyCnt / 10.0);
								showList(pageNum);
								return;
							}

							var str = "";
							if (list == null || list.length == 0) {
								replyUL.html("");

								return;
							}

							for (var i = 0, len = list.length || 0; i < len; i++) {
								str += "<li class='left clearfix' data-rno='"+list[i].rno+"'>";
								str += "  <div><div class='header'><strong class='primary-font'>"
										/* + list[i].rno */
										+ list[i].replyer + "</strong>";
								str += "    <small class='pull-right text-muted'>"
										+ replyService
												.displayTime(list[i].replydate)
										+ "</small></div>";
								str += "    <p>" + list[i].reply
										+ "</p></div></li>";
							}
							replyUL.html(str);

							showReplyPage(replyCnt);

						});//end function

	}//end showList

	//댓글페이징처리
	var pageNum = 1;
	var replyPageFooter = $(".panel-footer");

	function showReplyPage(replyCnt) {

		var endNum = Math.ceil(pageNum / 10.0) * 10;
		var startNum = endNum - 9;

		var prev = startNum != 1;
		var next = false;

		if (endNum * 10 >= replyCnt) {
			endNum = Math.ceil(replyCnt / 10.0);
		}

		if (endNum * 10 < replyCnt) {
			next = true;
		}

		var str = "<ul class='pagination pull-right'>";

		if (prev) {
			str += "<li class='page-item'><a class='page-link' href='"
					+ (startNum - 1) + "'>Previous</a></li>";
		}

		for (var i = startNum; i <= endNum; i++) {

			var active = pageNum == i ? "active" : "";

			str += "<li class='page-item "+active+" '><a class='page-link' href='"+i+"'>"
					+ i + "</a></li>";
		}

		if (next) {
			str += "<li class='page-item'><a class='page-link' href='"
					+ (endNum + 1) + "'>Next</a></li>";
		}

		str += "</ul></div>";

		console.log(str);

		replyPageFooter.html(str);
	}

	replyPageFooter.on("click", "li a", function(e) {
		e.preventDefault();
		console.log("page click");

		var targetPageNum = $(this).attr("href");

		console.log("targetPageNum: " + targetPageNum);

		pageNum = targetPageNum; //클릭한 페이지번호로 변경

		showList(pageNum); //목록보여줌
	});

	//댓글추가모달(변수로 빼두기)
	var modal = $(".modal");
	var modalInputReply = modal.find("input[name='REPLY']");
	var modalInputReplyer = modal.find("input[name='REPLYER']")
	var modalInputReplyDate = modal.find("input[name='REPLYDATE']");
	var modalModBtn = $("#modalModBtn");
	var modalRemoveBtn = $("#modalRemoveBtn");
	var modalRegisterBtn = $("#modalRegisterBtn");
	
	
	//로그인한 사용자가 댓글작성자가 되도록 로그인한 사용자 시큐리티로 얻어옴 + csrf토큰값 같이 전달위해서 값 얻어옴 
	var replyer = null;
    
    <sec:authorize access="isAuthenticated()">
    
    replyer = '<sec:authentication property="principal.username"/>';   
    
     </sec:authorize>
 
    var csrfHeaderName ="${_csrf.headerName}"; 
    var csrfTokenValue="${_csrf.token}";

	
	

	$("#addReplyBtn").on("click", function(e) { //댓글추가버튼

		modal.find("input").val("");
		modal.find("input[name='REPLYER']").val(replyer); //댓글 추가할때 로그인한 사용자가 댓글작성자 값으로 나오도록 함
		modalInputReplyDate.closest("div").hide();
		modal.find("button[id !='modalCloseBtn']").hide();

		modalRegisterBtn.show();

		$(".modal").modal("show"); //모달보여줌

	});
	
	//ajax로 csrf토큰을 같이 전송하도록 
	$(document).ajaxSend(function(e, xhr, options) { 
        xhr.setRequestHeader(csrfHeaderName, csrfTokenValue); 
      }); 

	
	//댓글클릭 시 ->  조회  이벤트 처리 (댓글눌렀을 경우 모달로 댓글내용보여준다.)
	$(".chat").on(
			"click",
			"li",
			function(e) {

				var rno = $(this).data("rno");

				console.log(rno);

				replyService.get(rno, function(reply) {

					console.log(reply)

					modalInputReply.val(reply.reply);
					modalInputReplyer.val(reply.replyer);
					modalInputReplyDate.val(
							replyService.displayTime(reply.replydate)).attr(
							"readonly", "readonly"); //바꿀 수 없도록 readonly
					modal.data("rno", reply.rno);

					modal.find("button[id !='modalCloseBtn']").hide();
					modalModBtn.show();
					modalRemoveBtn.show();

					$(".modal").modal("show");

				});
			});

	//******모달에서 보이는 UI*****

	//댓글 닫기 버튼 시
	$("#modalCloseBtn").on("click", function(e) { //댓글닫기

		modal.modal('hide');
	});

	//댓글 등록 버튼 클릭 시
	modalRegisterBtn.on("click", function(e) { //댓글등록
		e.preventDefault();

		//json으로 Requestbody 보내기위함.
		var replyObj = {
			"not_no" : NOT_NO,
			"reply" : modalInputReply.val(),
			"replyer" : modalInputReplyer.val()
		};

		replyService.add(replyObj, function(result) { //댓글추가함수

			alert(result); //정상적으로 등록 성공시 경고창발생
			modal.find("input").val(""); //등록한 내용으로 다시 등록할 수 없도록 입력항목 비워주기
			modal.modal("hide"); //모달닫기
			//showList(1);
			showList(-1); //새로운 댓글 추가 시 댓글 전체 수 파악 후 다시 마지막페이지 호출 

		});

	});

	//댓글 수정 버튼 클릭 시
	modalModBtn.on("click", function(e) {
		
		var originalReplyer = modalInputReplyer.val(); //댓글작성자
		
		
		//댓글 수정시엔 댓글번호와 댓글내용 json으로 보내주기
		//댓글수정은 댓글내용만 가능, 작성자인경우에는 시큐리티로 로그인한 사용자만 변경될수있도록
		var reply = {
			"rno" : modal.data("rno"),
			"reply" : modalInputReply.val(),
			"replyer": originalReplyer
		};
		
		if(!replyer){ //로그인하지 않았다면
			 alert("로그인후 수정이 가능합니다.");
			 modal.modal("hide");
			 return;
		}

		console.log("Original Replyer: " + originalReplyer);
		
		if(replyer  != originalReplyer){ //로그인한 사용자와 댓글작성자가 다르다면 삭제제한
		 
			 alert("자신이 작성한 댓글만 수정이 가능합니다.");
			 modal.modal("hide");
			 return;
		 
		}

		replyService.update(reply, function(result) {

			alert(rno + "번 댓글 수정되었습니다.");
			modal.modal("hide");
			showList(pageNum); //수정 후 댓글목록 보여줌

		});

	});

	//댓글 삭제 버튼 클릭 시
	modalRemoveBtn.on("click", function(e) {

		var rno = modal.data("rno");
	

		  if(!replyer){
	   		  alert("로그인후 삭제가 가능합니다.");
	   		  modal.modal("hide");
	   		  return;
	   	  }
	   	  
	   	  var originalReplyer = modalInputReplyer.val();
	   	  
	   	  console.log("Original Replyer: " + originalReplyer); //댓글의 원래 작성자 
	   	  
	   	  if(replyer  != originalReplyer){ //로그인한 사용자와 댓글작성자가 다르다면 삭제제한
	   		  
	   		  alert("자신이 작성한 댓글만 삭제가 가능합니다.");
	   		  modal.modal("hide");
	   		  return;
	   		  
	   	  }
	   	  
		replyService.remove(rno, function(result) {

			alert(rno + "번 댓글 삭제되었습니다.");
			modal.modal("hide");
			showList(pageNum); //삭제 후 댓글목록 보여줌

		});

	});
</script>

<%@include file="../includes/footer.jsp"%>
