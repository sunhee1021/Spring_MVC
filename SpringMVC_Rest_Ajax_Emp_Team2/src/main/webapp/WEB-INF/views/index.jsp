<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.10/css/all.css" integrity="sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOKmrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/rest.css">
	<%-- <script src="${pageContext.request.contextPath}/resources/rest.js"></script>      --%> 
	<script type="text/javascript">
	$(document).ready(function(){
		$('#restconBtn').click(function(){
			 $.ajax(
					 {  
						type : "GET",
						url  : "emps",
						success : function(data){ 
	
							createTable(data);
						} 
					 } 
			       )    
		});
	}); //end dom
	
	function createTable(data){
		$('#menuView').empty();
		var opr="<table id='fresh-table' class='table'><thead><tr>"+
		    "<th>EMPNO</th>"+
        	"<th>ENAME</th>"+
        	"<th>JOB</th>"+
        	"<th>MGR</th>"+
        	"<th>HIREDATE</th>"+
        	"<th>SAL</th>"+
        	"<th>COMM</th>"+
        	"<th>DEPTNO</th>"+
        	"<th>EDIT</th><th>DELETE</th></tr></thead><tbody>";
		$.each(data,function(index,emp){
			opr += "<tr><td>"+emp.empno+
			"</td><td>"+emp.ename+
			"</td><td>"+emp.job+
			"</td><td>"+emp.mgr+
			"</td><td>"+emp.hiredate+
			"</td><td>"+emp.sal+
			"</td><td>"+emp.comm+
			"</td><td>"+emp.deptno+
			"</td><td><input type='button' onclick='empupdate(this)' value='수정' class ='update' value2="+emp.empno+
			"></td><td><input type='button' value='삭제' value2="+emp.empno+" onclick='empdelete(this)'></td></tr>";
		});
		opr+="<tr><td colspan='10'><input type='button' onclick='createinput(this)' value='추가'></td></tr></tbody></table>";
		$('#menuView').append(opr);
	};
	
	
	function empdelete(me){
		var tr = $(me).closest('tr')
		var datas = {empno:tr.children().html()};
		var ajaxurl = "emps/" + tr.children().html();
		// tr.children().html() == EMPNO

		$.ajax({
			type : "DELETE",
			url:ajaxurl, 
			success : function(data) {
				alert(data);
				$.ajax(
						 {  
							type : "GET",
							url  : "emps",
							success : function(data){ 
		
								createTable(data);
							} 
						 } 
				       ) 
			}
		})
	}
	
	function empupdate(me){
		var tr = $(me).closest('tr')
		var datas = {empno:tr.children().html()};
		var ajaxurl = "emps/" + tr.children().html() + "/update";
		tr.empty();
		console.log(tr.children().html());
		$.ajax({
			type : "get",
			url:ajaxurl, 
			success : function(data) {
				console.log(data);
			 	var td = "<td><input type='text' value='"+data.empno +"' disabled></td>";
					td +="<td><input type='text' value='"+data.ename +"' disabled></td>";
					td +="<td><input type='text' value='"+data.job +"'></td>";
					td +="<td><input type='text' value='"+data.mgr +"'></td>";
					td +="<td><input type='text' value='"+data.hiredate +"' readonly></td>";
					td +="<td><input type='text' value='"+data.sal +"'></td>";
					td +="<td><input type='text' value='"+data.comm +"'></td>";
					td +="<td><input type='text' value='"+data.deptno +"'></td>";
					td +="<td colspan='2'><input type='button'onclick='empupdateconfirm(this)' value='완료' value2="+data.empno+"  /></td>";
					$(tr).append(td); 
			}
		})
	}
	
	function empupdateconfirm(me){			
		empupdateok(me);
	}
	
	
	function empupdateok(me){
        var tr = $(me).closest('tr');

        var empinfo = new Object();

        var tr = $(me).closest('tr')
        var datas = {empno:tr.children().html()};
        console.log(tr.find("td:eq(0)").children().val());
        
        
        var uri = "emps/" +tr.find("td:eq(0)").children().val();
     	// tr.children().html() == EMPNO
     	
        empinfo.empno = tr.find("td:eq(0)").children().val();
        empinfo.ename = tr.find("td:eq(1)").children().val();
        empinfo.job = tr.find("td:eq(2)").children().val();
        empinfo.mgr = tr.find("td:eq(3)").children().val();
        empinfo.hiredate = tr.find("td:eq(4)").children().val();
        empinfo.sal = tr.find("td:eq(5)").children().val();
        empinfo.comm = tr.find("td:eq(6)").children().val();
        empinfo.deptno = tr.find("td:eq(7)").children().val();
        
        var jsoninfo = JSON.stringify(empinfo);
        console.log(jsoninfo);
        
        $.ajax({
            type : "PUT",
            url: uri,
            data : jsoninfo,
            contentType: 'application/json;charset=UTF-8',
            success : function(result){
            	alert(result);
				$.ajax(
						 {  
							type : "GET",
							url  : "emps",
							success : function(data){ 
		
								createTable(data);
							} 
						 } 
				       ) 
            }
        });
    }
	
	function empinsert(me){
		var tr = $(me).closest('tr');
		var empinfo = new Object();
		
		empinfo.empno = tr.find("td:eq(0)").children().val();
        empinfo.ename = tr.find("td:eq(1)").children().val();
        empinfo.job = tr.find("td:eq(2)").children().val();
        empinfo.mgr = tr.find("td:eq(3)").children().val();
        empinfo.hiredate = tr.find("td:eq(4)").children().val();
        empinfo.sal = tr.find("td:eq(5)").children().val();
        empinfo.comm = tr.find("td:eq(6)").children().val();
        empinfo.deptno = tr.find("td:eq(7)").children().val();
        
        var jsoninfo = JSON.stringify(empinfo);
        console.log(jsoninfo);
        
		$.ajax({
			type : "post",
			url:"emps/new",
			data:jsoninfo,
            contentType: 'application/json;charset=UTF-8',
			success : function(data){  
				alert(data);
				$.ajax(
						 {  
							type : "GET",
							url  : "emps",
							success : function(data){ 
		
								createTable(data);
							} 
						 } 
				       ) 
			} 
		})
	}
	
	function createinput(me){
		var tr = $(me).closest('tr');
		tr.empty();
		var td = "<td><input type='text'></td>";
		td +="<td><input type='text'></td>";
		td +="<td><input type='text'></td>";
		td +="<td><input type='text'></td>";
		td +="<td><input type='text' disabled></td>";
		td +="<td><input type='text'></td>";
		td +="<td><input type='text'></td>";
		td +="<td><input type='text'></td>";
		td +="<td><input type='button'onclick='empinsert(this)' value='완료'/></td>";
		td +="<td><input type='button'onclick='cancel(this)' value='취소'/></td>";
		$(tr).append(td); 
	
	}
	

	</script>
</head>
<body>
<div class="wrapper">
    <div class="fresh-table toolbar-color-azure full-screen-table" style="align-content: center;">
<div class="container-fluid">
<div class="row">
<div class="col-sm-6">
<fieldset>
	<legend>JSON</legend>
	<input type="button" value="RestController" id="restconBtn">
</fieldset>
</div>
</div>

	<hr>
	<div id="exp"></div>
		<span id="menuView"></span>
	</div>
	</div>
</div>
</body>
</html>