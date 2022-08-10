<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@include file="../includes/header.jsp"%>

<style>
#pagingCss {
	text-align: -webkit-center;
}

#regBtn {
	margin-bottom: 20px;
	border-radius: 20px;
}

#searchForm {
	display: inline;
}

#keywordCss {
	height: 20px;
}

.btn-sm {
	background-color: white;
}

.table-responsive {
	text-align: center;
}
.table-responsive th{
text-align: center;}
</style>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">공지사항</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<!-- 검색 -->
				<div class='row'>
					<div class="col-lg-12">
						<form id='searchForm' action="/notice/list" method='get'>
							<select name='type'>
								<option value=""
									<c:out value="${pageMaker.criteria.type == null?'selected':''}"/>>--</option>
								<option value="T"
									<c:out value="${pageMaker.criteria.type eq 'T'?'selected':''}"/>>제목</option>
								<option value="C"
									<c:out value="${pageMaker.criteria.type eq 'C'?'selected':''}"/>>내용</option>
								<option value="W"
									<c:out value="${pageMaker.criteria.type eq 'W'?'selected':''}"/>>작성자</option>
								<option value="TC"
									<c:out value="${pageMaker.criteria.type eq 'TC'?'selected':''}"/>>제목
									or 내용</option>
								<option value="TW"
									<c:out value="${pageMaker.criteria.type eq 'TW'?'selected':''}"/>>제목
									or 작성자</option>
								<option value="TWC"
									<c:out value="${pageMaker.criteria.type eq 'TWC'?'selected':''}"/>>제목
									or 내용 or 작성자</option>
							</select> <input type='text' name='keyword' id="keywordCss"
								value='<c:out value="${pageMaker.criteria.keyword}"/>' /> <input
								type='hidden' name='pageNum'
								value='<c:out value="${pageMaker.criteria.pageNum}"/>' /> <input
								type='hidden' name='amount'
								value='<c:out value="${pageMaker.criteria.amount}"/>' />

							<button class="btn btn-secondary btn-sm" type="button">
								<i class="fa fa-search" aria-hidden="true"></i>
							</button>

						</form>
						<button id='regBtn' type="button" class="btn btn-xs pull-right">글쓰기</button>
					</div>
				</div>





				<div class="table-responsive">
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<th>No</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일자</th>
								<th>조회수</th>
								<th>댓글수</th>
							</tr>
						</thead>

						<c:forEach items="${list}" var="notice">
							<tr>
								<td><c:out value="${notice.NOT_NO}" /></td>
								<td><a class='move'
									href='<c:out value="${notice.NOT_NO}"/>'> <c:out
											value="${notice.NOT_TITLE}" /></a></td>
								<td><c:out value="${notice.USER_NAME}" /></td>
								<td><fmt:formatDate pattern="yyyy-MM-dd"
										value="${notice.NOT_REGDATE}" /></td>
								<td><c:out value="${notice.NOT_COUNT}" /></td>
								<td><c:out value="${notice.REPLYCNT}" /></td>
						</c:forEach>

					</table>



					<!-- 페이징 -->
					<div id="pagingCss">
						<ul class="pagination">
							<c:if test="${pageMaker.prev}">
								<li class="page-item disabled"><a class="page-link"
									href="${pageMaker.startPage -1}" tabindex="-1">Previous</a></li>
							</c:if>

							<c:forEach var="num" begin="${pageMaker.startPage}"
								end="${pageMaker.endPage}">
								<li
									class="paginate_button ${pageMaker.criteria.pageNum == num?'active':''}">
									<a href="${num}">${num}</a>
								</li>
							</c:forEach>

							<c:if test="${pageMaker.next}">
								<li class="page-item next"><a class="page-link"
									href="${pageMaker.endPage +1 }" tabindex="-1">Next</a></li>
							</c:if>

						</ul>
					</div>


					<form id='actionForm' action="/notice/list" method='get'>
						<input type='hidden' name='pageNum'
							value='${pageMaker.criteria.pageNum}'> <input
							type='hidden' name='amount' value='${pageMaker.criteria.amount}'>

						<input type='hidden' name='type'
							value='<c:out value="${ pageMaker.criteria.type }"/>'> <input
							type='hidden' name='keyword'
							value='<c:out value="${ pageMaker.criteria.keyword }"/>'>
					</form>


					<!-- 모달추가(등록,수정,삭제시 나오는 모달 -->

					<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">처리가 완료되었습니다.</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
								</div>
							</div>
							<!-- /.modal-content -->
						</div>
						<!-- /.modal-dialog -->
					</div>
					<!-- /.modal -->
				</div>
				<!-- /.table-responsive -->
			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->



<%@include file="../includes/footer.jsp"%>

<script type="text/javascript">
	$(document)
			.ready(
					function() { //게시글 등록 시 모달

						var result = '<c:out value="${result}"/>'; //등록하고 난 게시글 번호 반환한 것 가져오기 addFlashAttribute

						checkModal(result);

						history.replaceState({}, null, null);

						function checkModal(result) {
							if (result === '' || history.state) { //뒤로가기 버튼 클릭 시 모달창 다시 안나오게끔 history에 쌓이게끔 두면 자바스크립트 처리 완료 후 모달창 다시 보여주지 않는다.
								return;
							}

							if (parseInt(result) > 0) {
								$(".modal-body").html(
										"게시글  " + parseInt(result)
												+ " 번이 등록되었습니다.")
							}

							$("#myModal").modal("show");
						}

						$("#regBtn").on("click", function() { //글쓰기 버튼 
							self.location = "/notice/register";
						});

						var actionForm = $("#actionForm");

						$(".paginate_button a").on(
								"click",
								function(e) {

									e.preventDefault();//a태그 클릭해도 페이지 이동없도록 함. 기본동작막음 

									console.log('click');
									//form태그안에 pageNum값을 href값으로 변경  - 페이지번호 클릭 시 페이지번호 바뀌도록 하기 위함 
									actionForm.find("input[name='pageNum']")
											.val($(this).attr("href"));
									actionForm.submit();
								});

						$(".move")
								.on(
										"click",
										function(e) {

											e.preventDefault();
											actionForm
													.append("<input type='hidden' name='NOT_NO' value='"
															+ $(this).attr(
																	"href")
															+ "'>");
											actionForm.attr("action",
													"/notice/read");
											actionForm.submit();

										});

						//검색버튼 클릭하면 무조건 1페이지로
						//화면에 검색키워드와 조건이 보이도록 구현
						var searchForm = $("#searchForm");

						//검색버튼 클릭하면 <form> 태그의 전송을 막고 페이지 번호는 1페이지로
						//검색키워드가 없다면 검색못하도록 제어
						$("#searchForm button").on(
								"click",
								function(e) {

									if (!searchForm.find("option:selected")
											.val()) {
										alert("검색종류를 선택하세요");
										return false;
									}

									if (!searchForm.find(
											"input[name='keyword']").val()) {
										alert("키워드를 입력하세요");
										return false;
									}

									searchForm.find("input[name='pageNum']")
											.val("1");
									e.preventDefault();

									searchForm.submit();

								});

					});
</script>


