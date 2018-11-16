<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<script type="text/javascript">
	$(function(){
		var imgArea = $("#imgArea");
		var pattern ='<img src="imageService.do?selname=%v"/>';
		$("[name='selname']").on("change", function(){
			var filename =$(this).val();
			imgArea.append(pattern.replace("%v", filename));
		});
		
	});

</script>

<form name ="imgForm" action="imageService.do" method="get">
	<select name ="selname">
		<%=request.getAttribute("optionsAttr") %>
	</select>
	<!-- <input type ="submit" value ="전송"/> -->
</form>
<div id="imgArea">
	<%=request.getAttribute("imgTags") %>
</div>
<!-- <script type ="text/javascript">
		var imgArea = document.getElementById("imgArea");
	function changeHandler(event) {
// 		document.imgForm.submit();
		var filename = event.target.value;
		var pattern ='<img src="imageService.do?image=%v"/>';
		imgArea.innerHTML = pattern.replace("%v", filename);
	} -->
</script>	
