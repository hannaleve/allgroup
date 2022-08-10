<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@include file="../includes/header.jsp"%>

<div class="row">
	<div class="col-lg-12">
		<h1 class="panel-header">공지사항</h1>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">공지사항 등록</div>
			<!-- /.panel-heading -->
			<div class="panel-body">

				<form role="form" action="/notice/register" method="post"
					id="formBtn">
					 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> <!-- post방식 시 반드시 csrf토큰 사용 -->
					<div class="form-group">
						<label>제목</label> <input class="form-control" name='NOT_TITLE'
							id='NOT_TITLE'>
					</div>
					<!-- 로그인한 사용자의 이름 -->
					<div class="form-group">
						<label>작성자</label> <input class="form-control" name='USER_NAME'
							id='USER_NAME' value='<sec:authentication property="principal.username"/>' readonly="readonly">
					</div>
					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" rows="30" name='NOT_CONTENT'
							id='NOT_CONTENT'></textarea>
					</div>
					<ul class='uploadedList'>
					</ul>


					<button type="submit" class="btn btn-default" id="submitBtn">저장</button>
					<button type="reset" class="btn btn-default">취소</button>
				</form>
			</div>
		</div>
	</div>
</div>

<%@include file="../includes/footer.jsp"%>

<script type="text/javascript">

	//naver smarteditor

	var oEditors = [];
	console.log("Naver SmartEditor")
	nhn.husky.EZCreator.createInIFrame({
		oAppRef : oEditors,
		elPlaceHolder : "NOT_CONTENT",
		sSkinURI : "/resources/smarteditor/SmartEditor2Skin.html",
		htParams : {
			bUseToolbar : true, // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true, // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : true, // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			fOnBeforeUnload : function() {
			}
		},
		fCreator : "createSEditor2"
	})

	function pasteHTML() {
		var sHTML = "<span style='color:#FF0000;'>이미지도 같은 방식으로 삽입합니다.<\/span>";
		oEditors.getById["NOT_CONTENT"].exec("PASTE_HTML", [ sHTML ]);
	}

	function showHTML() {
		var sHTML = oEditors.getById["NOT_CONTENT"].getIR();
		alert(sHTML);
	}

	function submitContents(elClickedObj) {

		oEditors.getById["NOT_CONTENT"].exec("UPDATE_CONTENTS_FIELD", []); // 에디터의 내용이 textarea에 적용됩니다.

		try {
			elClickedObj.form.submit();
		} catch (e) {
		}
	}

	function setDefaultFont() {
		var sDefaultFont = '궁서';
		var nFontSize = 24;
		oEditors.getById["NOT_CONTENT"].setDefaultFont(sDefaultFont, nFontSize);
	}

	function replys() {
		var content = document.getElementById("NOT_CONTENT").value;

		content.replace(/(<([^>]+)>)/ig, "");

		console.log(content)
	}
</script>



<script>
	var uploadedList = $(".uploadedList"); //업로드 부분 (나오는 곳)
	const form = document.querySelector("#formBtn") //등록 form 부분 

	function cb(obj) {
		console.log("register callback");
		console.log(obj);

		var target = $(obj);

		var srcStr = target.attr('src').split("/")[2];

		target.attr("id", srcStr);

		if (confirm("해당 이미지를 삭제하시겠습니까?")) {

			$.post("/deleteEditorFile", {
				fileName : srcStr
			}, function(result) {

				console.log("삭제...");

				target.remove();

			});

			return;
		}

	}

	$(document).ready(function() {

		$("#NOT_CONTENT").on('input propertychange', function() {

			console.log()
		});

		$('#submitBtn').click(function(event) { //저장버튼 눌렀을 시 발생하는 이벤트 

			event.preventDefault(); //기본동작 막음 

			// 에디터의 내용이 textarea에 적용된다.
			oEditors.getById["NOT_CONTENT"].exec("UPDATE_CONTENTS_FIELD", []);

			// 에디터의 내용에 대한 값 검증
			var content = document.getElementById("NOT_CONTENT").value;
			//var ss = content.replace(/(<([^>]+)>)/ig,"")
			//content.value = ss;
			//console.log(content);

			//console.log("CONTENT: " + content);

			formBtn.submit(); //저장완료
		});

	});
</script>

