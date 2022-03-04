<!DOCTYPE html>
<html>
    <head>
        <title>登录日志管理</title>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" >
            <blockquote class="layui-elem-quote quoteBox">
                <form class="layui-form">
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="loginName" class="layui-input searchVal" placeholder="登录名称" />
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="loginIp" class="layui-input searchVal" placeholder="登录IP" />
                        </div>
                        <div class="layui-input-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="startTime" id="startTime" class="layui-input searchVal" placeholder="开始时间">
                            </div>
                        </div>
                        <div class="layui-input-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="endTime" id="endTime" class="layui-input searchVal" placeholder="结束时间">
                            </div>
                        </div>
                        <a class="layui-btn search_btn" data-type="reload">
                            <i class="layui-icon">&#xe615;</i>
                            搜索
                        </a>
                    </div>
                </form>
            </blockquote>

            <table id="loginList" class="layui-table"  lay-filter="logins"></table>

            <script type="text/html" id="toolbarDemo">
                <div class="layui-btn-container">
                    <a class="layui-btn layui-btn-normal delNews_btn" lay-event="del">
                        <i class="layui-icon">&#xe608;</i>
                        批量删除日志
                    </a>
                </div>
            </script>

            <!--操作-->
            <script id="loginListBar" type="text/html">
                <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
            </script>
        </form>

    <script type="text/javascript" src="${ctx}/js/system/loginfo.js"></script>
    </body>
</html>