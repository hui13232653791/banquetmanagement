<!DOCTYPE html>
<html>
    <head>
        <title>订单信息管理</title>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" >
            <blockquote class="layui-elem-quote quoteBox">
                <form class="layui-form">
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="oId" class="layui-input searchVal" placeholder="订单编号" />
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="khno" class="layui-input searchVal" placeholder="客户姓名" />
                        </div>

                        <div class="layui-input-inline">
                            <select name="payState" id="payState"  >
                                <option value="" >请选择支付状态</option>
                                <option value="0">未支付</option>
                                <option value="1">已支付</option>
                            </select>
                        </div>

                        <div class="layui-input-inline">
                            <input type="text" name="startTime" id="startTime" class="layui-input searchVal" placeholder="开始时间">
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="endTime" id="endTime" class="layui-input searchVal" placeholder="结束时间">
                        </div>

                        <a class="layui-btn search_btn" data-type="reload">
                            <i class="layui-icon">&#xe615;</i>
                            搜索
                        </a>
                    </div>
                </form>
            </blockquote>

            <table id="endOrderList" class="layui-table"  lay-filter="endOrders"></table>

            <!--操作-->
            <script id="orderListBar" type="text/html">
                <a class="layui-btn layui-btn-xs layui-btn-normal" id="info" lay-event="info">订单详情</a>
            </script>
        </form>

    <script type="text/javascript" src="${ctx}/js/order/endorder.js"></script>
    </body>
</html>