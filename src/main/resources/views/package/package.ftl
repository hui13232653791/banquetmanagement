<!DOCTYPE html>
<html>
    <head>
        <title>套餐信息管理</title>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" >
            <blockquote class="layui-elem-quote quoteBox">
                <form class="layui-form">
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="packageName" class="layui-input searchVal" placeholder="套餐名称" />
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="lpackagePrice" class="layui-input searchVal" placeholder="套餐最低价格" />
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="gpackagePrice" class="layui-input searchVal" placeholder="套餐最高价格" />
                        </div>
                        <a class="layui-btn search_btn" data-type="reload">
                            <i class="layui-icon">&#xe615;</i>
                            搜索
                        </a>
                    </div>
                </form>
            </blockquote>

            <table id="packageList" class="layui-table"  lay-filter="packages"></table>

            <script type="text/html" id="toolbarDemo">
                <div class="layui-btn-container">
                    <a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
                        <i class="layui-icon">&#xe608;</i>
                        添加套餐
                    </a>
                    <a class="layui-btn layui-btn-normal delNews_btn" lay-event="del">
                        <i class="layui-icon">&#xe608;</i>
                        批量删除套餐
                    </a>
                </div>
            </script>

            <!--操作-->
            <script id="packageListBar" type="text/html">
                <a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">编辑</a>
                <a class="layui-btn layui-btn-normal layui-btn-xs"  lay-event="info">分配菜品</a>
                <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
            </script>
        </form>

    <script type="text/javascript" src="${ctx}/js/package/package.js"></script>
    </body>
</html>