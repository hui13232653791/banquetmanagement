<!--
   订单月销售额页面
-->
<!DOCTYPE html>
<html>
<head>
    <title>订单月销售额统计</title>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
    <div class="layui-card">

        <form class="layui-form" >
            <blockquote class="layui-elem-quote quoteBox">
                <form class="layui-form">
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="yearTime" id="yearTime" class="layui-input searchVal" placeholder="选择查询的年份">
                        </div>

                        <a class="layui-btn search_btn" data-type="reload">
                            <i class="layui-icon">&#xe615;</i>
                            搜索
                        </a>
                    </div>
                </form>
            </blockquote>
        </form>

        <div class="layui-card-body" id="make" style="width: 100%;min-height:700px"></div>

    </div>

    <script type="text/javascript" src="${ctx}/js/report/orderinfo.js"></script>
</body>
</html>