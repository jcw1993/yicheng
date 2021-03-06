<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<nav class="navbar navbar-default navbar-fixed-top" style="">
	<div class="container-fluid nav-container">
		<div class="row">
			<div class="col-xs-2">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">江阴怡诚</a>
				</div>
			</div>
			<div class="col-xs-10">
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="ClothMaterialManage">面辅料明细表</a></li>
						<li><a href="ClothPriceManage">报价单</a></li>
						<li><a href="ClothCountManage">采购单</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
				        <li ><a href="../Logout">退出</a></li>
				    </ul>
				</div>
			</div>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>
