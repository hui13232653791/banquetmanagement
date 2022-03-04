<!DOCTYPE html>
<html>
	<head>
		<title>客户订单信息</title>
		<#include "../common.ftl">
	</head>
	<body class="childrenBody">
	<div class="layui-col-md12">
		<div class="layui-card">
			<div class="layui-card-body">
				<form class="layui-form" >
					<#-- 客户姓名 -->
					<input name="khno" type="hidden" value="${(customerOrderInfo.customerName)!}"/>

					<div class="layui-form-item layui-row">
						<div class="layui-col-xs6">
							<label class="layui-form-label">客户编号</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input"
									   name="khno" id="khno" value="${(customerOrderInfo.khno)!}" readonly="readonly">
							</div>
						</div>
						<div class="layui-col-xs6">
							<label class="layui-form-label">客户姓名</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input"
									   name="customerName" id="customerName" value="${(customerOrderInfo.customerName)!}" readonly="readonly">
							</div>
						</div>
					</div>

					<div class="layui-form-item layui-row">
						<div class="layui-col-xs6">
							<label class="layui-form-label">订单总数量</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input"
									   name="orderNumber_1"  id="orderNumber_1"  value="${(customerOrderInfo.orderNumber)!}" readonly="readonly">
							</div>
						</div>
						<div class="layui-col-xs6">
							<label class="layui-form-label">订单总金额</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input"
									   name="orderTotalprice_1" id="orderTotalprice_1" value="${(customerOrderInfo.orderTotalprice)!}" readonly="readonly">
							</div>
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>

	<div class="layui-col-md12">
		<table id="customerOrderInfoList" class="layui-table"  lay-filter="customerOrderInfos"></table>
	</div>

	<script type="text/javascript" src="${ctx}/js/customer/customer_orderInfo.js"></script>
	</body>
</html>