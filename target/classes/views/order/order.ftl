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

            <table id="orderList" class="layui-table"  lay-filter="orders"></table>

            <script type="text/html" id="toolbarDemo">
                <div class="layui-btn-container">
                    <a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
                        <i class="layui-icon">&#xe608;</i>
                        添加订单
                    </a>
                    <a class="layui-btn layui-btn-normal delNews_btn" lay-event="del">
                        <i class="layui-icon">&#xe608;</i>
                        批量删除订单
                    </a>
                </div>
            </script>

            <!--操作-->
            <script id="orderListBar" type="text/html">
                <a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">编辑</a>
                <a class="layui-btn layui-btn-xs layui-btn-normal" id="info" lay-event="info">归档订单</a>
                <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
            </script>
        </form>

    <script type="text/javascript" src="${ctx}/js/order/order.js"></script>
    </body>
</html>