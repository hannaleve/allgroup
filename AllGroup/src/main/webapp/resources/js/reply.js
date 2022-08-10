console.log("Reply Module........");

var replyService = (function() {

 		function add(reply, callback, error) { //댓글등록
 			console.log("add reply...............");
 		    console.log(`data: ${JSON.stringify(reply)}`);

 	    $.ajaxSettings.traditional = true;
 			$.ajax({
 				type : 'POST',
 				url : "/replies/new",
 				data: JSON.stringify ({
 				NOT_NO:NOT_NO, reply: modalInputReply.val(), replyer:modalInputReplyer.val()
 				}),
 				contentType: "application/json; charset=UTF-8",
 				dataType : "json",
 				success : function(result,status, xhr) {
 					if (callback) {
 						callback(result);
 						
 					}
 					
 				},
 				error : function(xhr, status, er) {
 					if (error) {
 						error(er);
 					}
 				}
 			});
 		}
 	
	function getList(param, callback, error) { //댓글목록

	    var NOT_NO = param.NOT_NO;
	    var page = param.page || 1;
	    
	    console.log("게시글번호:" + NOT_NO);
	    
	    $.getJSON("/replies/pages/" + NOT_NO + "/" + page + ".json",
	        function(data) {
	    	
	          if (callback) {
	            //callback(data); // 댓글 목록만 가져오는 경우 
	            callback(data.replyCnt, data.list); //댓글 숫자와 목록을 가져오는 경우 
	          }
	        }).fail(function(xhr, status, err) {
	      if (error) {
	        error();
	      }
	    });
	  }

	
	function remove(rno, callback, error) { //댓글삭제
	
	console.log("--------------------------------------");  
		console.log(JSON.stringify({rno:rno, replyer:replyer}));  
		
		
		$.ajax({
			type : 'delete',
			url : '/replies/' + rno,
			
			 data:  JSON.stringify({rno:rno, replyer:replyer}),
	      
	         contentType: "application/json; charset=utf-8",
	      
			success : function(deleteResult, status, xhr) {
				if (callback) {
					callback(deleteResult);
				}
			},
			error : function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		});
	}

	function update(reply, callback, error) { //댓글수정

		console.log("RNO: " + reply.rno);

		$.ajax({
			type : 'put',
			url : '/replies/' + reply.rno,
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr) {
				if (callback) {
					callback(result);
				}
			},
			error : function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		});
	}

	function get(rno, callback, error) { //댓글조회

		$.get("/replies/" + rno + ".json", function(result) {

			if (callback) {
				callback(result);
			}

		}).fail(function(xhr, status, err) {
			if (error) {
				error();
			}
		});
	}

	function displayTime(timeValue) { //날짜포맷

		var today = new Date();

		var gap = today.getTime() - timeValue;

		var dateObj = new Date(timeValue);
		var str = "";

		if (gap < (1000 * 60 * 60 * 24)) {

			var hh = dateObj.getHours();
			var mi = dateObj.getMinutes();
			var ss = dateObj.getSeconds();

			return [ (hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi,
					':', (ss > 9 ? '' : '0') + ss ].join('');

		} else {
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth() + 1; // getMonth() is zero-based
			var dd = dateObj.getDate();

			return [ yy, '/', (mm > 9 ? '' : '0') + mm, '/',
					(dd > 9 ? '' : '0') + dd ].join('');
		}
	};

	return {
		add : add,
		get : get,
		getList : getList,
		remove : remove,
		update : update,
		displayTime : displayTime
	};

})();
