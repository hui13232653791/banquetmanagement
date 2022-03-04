<!DOCTYPE html>
<html>
    <head>
        <title>供应商信息管理</title>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" >
            <blockquote class="layui-elem-quote quoteBox">
                <form class="layui-form">
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="pno" class="layui-input searchVal" placeholder="供应商编号" />
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="providerName" class="layui-input searchVal" placeholder="供应商姓名" />
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="foodName" class="layui-input searchVal" placeholder="供应食品名称" />
                        </div>
                        <a class="layui-btn search_btn" data-type="reload">
                            <i class="layui-icon">&#xe615;</i>
                            搜索
                        </a>
                    </div>
                </form>
            </blockquote>

            <table id="providerList" class="layui-table"  lay-filter="providers"></table>

            <script type="text/html" id="toolbarDemo">
                <div class="layui-btn-container">
                    <a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
                        <i class="layui-icon">&#xe608;</i>
                        添加供应商
                    </a>
                    <a class="layui-btn layui-btn-normal delNews_btn" lay-event="del">
                        <i class="layui-icon">&#xe608;</i>
                        批量删除供应商
                    </a>
                </div>
            </script>

            <!--操作-->
            <script id="providerListBar" type="text/html">
                <a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">编辑</a>
                <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
            </script>
        </form>

    <script type="text/javascript" src="${ctx}/js/provider/provider.js"></script>
    </body>
</html>