<!DOCTYPE html>
<html>
    <head>
        <title>客户信息管理</title>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" >
            <blockquote class="layui-elem-quote quoteBox">
                <form class="layui-form">
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="khno" class="layui-input searchVal" placeholder="客户编号" />
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="customerName" class="layui-input searchVal" placeholder="客户姓名" />
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="phone" class="layui-input searchVal" placeholder="手机号" />
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="address" class="layui-input searchVal" placeholder="详细地址" />
                        </div>
                        <a class="layui-btn search_btn" data-type="reload">
                            <i class="layui-icon">&#xe615;</i>
                            搜索
                        </a>
                    </div>
                </form>
            </blockquote>

            <table id="customerList" class="layui-table"  lay-filter="customers"></table>

            <script type="text/html" id="toolbarDemo">
                <div class="layui-btn-container">
                    <a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
                        <i class="layui-icon">&#xe608;</i>
                        添加客户
                    </a>
                    <a class="layui-btn layui-btn-normal delNews_btn" lay-event="del">
                        <i class="layui-icon">&#xe608;</i>
                        批量删除客户
                    </a>
                </div>
            </script>

            <!--操作-->
            <script id="customerListBar" type="text/html">
                <a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">编辑</a>
                <a class="layui-btn layui-btn-xs layui-btn-normal" id="orderInfo" lay-event="orderInfo">订单信息</a>
                <a class="layui-btn layui-btn-xs layui-btn-danger" id="del" lay-event="del">删除</a>
            </script>
        </form>

    <script type="text/javascript" src="${ctx}/js/customer/customer.js"></script>
    </body>
</html>