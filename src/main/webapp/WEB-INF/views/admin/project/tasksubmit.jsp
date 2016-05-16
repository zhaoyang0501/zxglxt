<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <link rel="shortcut icon" href="favicon.ico">
     <link href="${pageContext.request.contextPath}/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.min.css?v=4.1.0" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
       <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>审计底稿提交</h5>
                        <div class="ibox-tools">
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-12 b-r">
		                           <form  enctype="multipart/form-data" class="form-horizontal" action="${pageContext.request.contextPath}/admin/project/tasksubmit" method="post">
		                           	<input type="hidden" name="id" value="${task.id }"/>
		                           	<table class='table table-bordered'>
		                           		<thead>
		                           		<tr style="text-align: center;" ><td colspan="6" ><h3>审计底稿提交<h3></h3></td></tr>
		                           		</thead>
		                           		<tbody>
		                           			<tr>
		                           				<td>项目名称</td>
		                           				<td>${task.project.name }</td>
		                           			</tr>
		                           			<tr>
		                           				<td>被审计单位</td>
		                           				<td>${task.project.tran.name }</td>
		                           			</tr>
		                           			<tr>
		                           				<td>审计人</td>
		                           				<td>${task.user.username }</td>
		                           			
		                           			<tr>
		                           				<td>一.计划与组织</td>
		                           				<td > 
		                           					${task.key1 }
		                           				</td>
		                           			</tr>
		                           				<td>二.获取与实施</td>
		                           				<td > 
		                           					${task.key2 }
		                           				</td>
		                           			</tr>
		                           			</tr>
		                           				<td>三.交付与支持</td>
		                           				<td > 
		                           					${task.key3 }
		                           				</td>
		                           			</tr>
		                           			</tr>
		                           				<td>发现的问题</td>
		                           						<td > <input name='remark' type="text" class="form-control"></td>
		                           			</tr>
		                           			</tr>
		                           				<td>可能的影响</td>
		                           					<td > <input name='remark1' type="text" class="form-control"></td>
		                           			</tr>
		                           			</tr>
		                           				<td>底稿文件</td>
		                           					<td > <input name='file' type="file" class="form-control"></td>
		                           			</tr>
		                           			<tr>
		                           				<td colspan="6"> 
		                           					 <div class="col-sm-4 col-sm-offset-2">
		                                  			  		<button class="btn btn-primary" type="submit">提交</button>
		                                   				    <button class="btn btn-white" type="submit">取消</button>
		                               				 </div>
		                           				</td>
		                           			</tr>
		                           		</tbody>
		                           	</table> 
		                           	</form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        
   </div>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js?v=2.1.4"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${pageContext.request.contextPath}/js/content.min.js?v=1.0.0"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/toastr/toastr.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/common.js?v=1.0.0"></script>
    
    <script>
		$.common.setContextPath('${pageContext.request.contextPath}');
		<c:if test="${state=='success'}">
	  		  toastr.success('${tip}');
	    </c:if>
		
		function xzqhsq_change(id,obj){
			$.ajax({
    			type : "get",
    			url : $.common.getContextPath() + "/admin/code/xzqhxq?sq="+$(obj).val(),
    			dataType : "json",
    			success : function(json) {
    				if(json.state=='success'){
    					$("select[name='"+id+"']").empty();
    					$("select[name='"+id+"']").append("<option value=''>--请选择地市--</option>");
    					$(  json.data).each(function( index ) {
    						$("select[name='"+id+"']").append("<option value='"+ json.data[index].dm+"'>"+ json.data[index].name+"</option>");
    						  console.log( index + ": " + json.data[index].name);
    						});
    				}else{
    				}
    			}
    		});	
		}
		function init_xzqhsq(){
	    	$.ajax({
    			type : "get",
    			url : $.common.getContextPath() + "/admin/code/xzqhsq",
    			dataType : "json",
    			success : function(json) {
    				if(json.state=='success'){
    					$(  json.data).each(function( index ) {
    						$("select[name='sqdz'] , select[name='sqyydz']").append("<option value='"+ json.data[index].dm+"'>"+ json.data[index].name+"</option>");
    						  console.log( index + ": " + json.data[index].name);
    						});
    				}else{
    				}
    			}
    		});	
	    }
        $(document).ready(function(){
        	$(".input-group.date").datepicker({
        		language:  'zh-CN',
    	        weekStart: 1,
    	        todayBtn:  1,
    	        format:'yyyy-mm-dd',
    			autoclose: 1,
    			todayHighlight: 1,
    			startView: 2,
    			minView: 2,
    			forceParse: 0
        		});
        });
    </script>
</body>

</html>
