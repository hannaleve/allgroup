<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<%@include file="../includes/header.jsp"%>

<div class="row">
	<div class="col-lg-12">
		<h1 class="panel-header">공지사항</h1>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">공지사항 수정</div>
			<!-- /.panel-heading -->
			<div class="panel-body">

				<form role="form" id="form1" action="/notice/modify" method="post">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />

					<!-- 페이징,검색부분 같이 보내기위함 -->
					<input type='hidden' name='pageNum'
						value='<c:out value="${criteria.pageNum }"/>'> <input
						type='hidden' name='amount'
						value='<c:out value="${criteria.amount }"/>'> <input
						type='hidden' name='keyword'
						value='<c:out value="${criteria.keyword}"/>'> <input
						type='hidden' name='type'
						value='<c:out value="${criteria.type}"/>'>

					<div class="form-group">
						<label>제목</label> <input class="form-control" name='NOT_TITLE'
							value='<c:out value="${notice.NOT_TITLE}"/>'>
					</div>
					<div class="form-group">
						<label>작성자</label> <input class="form-control" name='USER_NAME'
							value='<c:out value="${notice.USER_NAME}"/>' readonly="readonly">
					</div>
					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" rows="5" name='NOT_CONTENT'
							id='NOT_CONTENT'><c:out value="${notice.NOT_CONTENT}" /></textarea>
					</div>
					<ul class='uploadedList'>
					</ul>

					<div class="form-group">
						<input type='hidden' class="form-control" id="NOT_NO"
							name='NOT_NO' value='<c:out value="${notice.NOT_NO}"/>'
							readonly="readonly">
					</div>

					<div class="form-group">
						<input type='hidden' class="form-control" name='NOT_UPDATE'
							value='<fmt:formatDate pattern = "yyyy/MM/dd" value="${notice.NOT_UPDATE}"/>'
							readonly="readonly">
					</div>

					<sec:authentication property="principal" var="pinfo" />

					<sec:authorize access="isAuthenticated()">

						<c:if test="${pinfo.username eq notice.USER_NAME}"> <!-- 로그인한사용자와 글작성자가 같은경우만 -->

							<button type="submit" data-oper='modify' class="btn btn-default">수정</button>
							<button type="submit" data-oper='remove' class="btn btn-danger">삭제</button>
						</c:if>
					</sec:authorize>

				
					<button type="submit" data-oper='list' class="btn btn-info"
						id="list">목록</button>


				</form>

			</div>
		</div>
	</div>
</div>

<%@include file="../includes/footer.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {

		$("#NOT_CONTENT").on('input propertychange', function() {

			console.log()
		});

		var formObj = $("form");
		var list = document.getElementById("list");

		$('button').on("click", function(e) {

			e.preventDefault(); //기본동작 일단 막음 
			//button의 data-oper
			var operation = $(this).data("oper");

			console.log(operation);

			if (operation === 'remove') {//삭제버튼 
				formObj.attr("action", "/notice/remove");

			} else if (operation === list) {//목록버튼 

				//move to list
				formObj.attr("action", "/notice/list").attr("method", "get");

				//사용지가 목록 버튼 클릭 시 form태그에서 필요한 부분만 잠시 복사(clone)해서 보관해두고,
				var pageNumTag = $("input[name='pageNum']").clone();
				var amountTag = $("input[name='amount']").clone();
				var keywordTag = $("input[name='keyword']").clone();
				var typeTag = $("input[name='type']").clone();

				//form태그내에서 모든 내용은 지워버린다.(empty)
				formObj.empty();//list이동은 아무런 파라미터가 없기 때문에 form태그 안에 있는 모든내용은 삭제 후 submit, 이후 코드가 실행되지 않도록 return  

				//이후에 다시 필요한 태그들만 추가해서 list호출한다.
				formObj.append(pageNumTag);
				formObj.append(amountTag);
				formObj.append(keywordTag);
				formObj.append(typeTag);
			}

			//수정버튼

			// 에디터의 내용이 textarea에 적용된다.
			oEditors.getById["NOT_CONTENT"].exec("UPDATE_CONTENTS_FIELD", []);

			formObj.submit(); //수정완료.

		});
	});

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
	var uploadedList = $(".uploadedList");
	const form = document.querySelector("#formBtn")

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
</script>