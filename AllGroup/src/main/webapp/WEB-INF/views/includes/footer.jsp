<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
</div>
<!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
<style>
#wrapper{
  height: auto;
  min-height: 100%;
  padding-bottom: 100px;
}
.footers{
  position:fixed; 
  height: 100px;
  position : relative;
  transform : translateY(-99%);
  background-color: currentColor;
}

</style>

<div id='wwrappper'>
<footer class="footers">
	<p style="line-height: 2; color: white; text-align: center;">©
		ALL-GROUP(주)</p>
	<p style="color: white; text-align: center;">대표: HANNA 사업자등록번호:
		000-00-0000 TEL: +02)1234-5678 FAX: +02)1234-5678</p>
	<p style="color: white; text-align: center;">
		<strong style="color: white; text-align: center;">Copyright
			&copy; <a href="https://startbootstrap.com/theme/sb-admin-2">SB
				Admin 2</a>.
		</strong>All Rights Reserved.
</footer>
</div>

<!-- Bootstrap Core JavaScript -->
<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="/resources/vendor/metisMenu/metisMenu.min.js"></script>

<!-- DataTables JavaScript -->
<script src="/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script
	src="/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
<script
	src="/resources/vendor/datatables-responsive/dataTables.responsive.js"></script>

<!-- Custom Theme JavaScript -->
<script src="/resources/dist/js/sb-admin-2.js"></script>

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
 
    $(".sidebar-nav")
    .attr("class","sidebar-nav navbar-collapse collapse")
    .attr("aria-expanded",'false')
    .attr("style","height:1px");
    
    });
    </script>
    
    

</body>

</html>
