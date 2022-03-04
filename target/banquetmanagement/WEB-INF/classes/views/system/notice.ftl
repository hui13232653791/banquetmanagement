<!DOCTYPE html>
<html>
    <head>
        <title>待办管理</title>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" >
            <blockquote class="layui-elem-quote quoteBox">
                <form class="layui-form">
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="title" class="layui-input searchVal" placeholder="待办标题" />
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="openName" class="layui-input searchVal" placeholder="发布人" />
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
                        <div class="layui-input-inline">
                            <select name="state" id="state"  >
                                <option value="" >请选择待办状态</option>
                                <option value="0">未完成</option>
                                <option value="1">已完成</option>
                            </select>
                        </div>

                        <a class="layui-btn search_btn" data-type="reload">
                            <i class="layui-icon">&#xe615;</i>
                            搜索
                        </a>
                    </div>
                </form>
            </blockquote>

            <table id="noticeList" class="layui-table"  lay-filter="notices"></table>

            <script type="text/html" id="toolbarDemo">
                <div class="layui-btn-container">
                    <a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
                        <i class="layui-icon">&#xe608;</i>
                        添加待办
                    </a>
                    <a class="layui-btn layui-btn-normal delNews_btn" lay-event="del">
                        <i class="layui-icon">&#xe608;</i>
                        批量删除待办
                    </a>
                </div>
            </script>

            <!--
			  行工具栏
				  详情:state=1
				  编辑:state=0
				  此时链接内容显示由开发结果值控制
			-->
            <script id="noticeListBar" type="text/html" >
                {{# if (d.state == 0) { }}
                <a href="javascript:;" class="layui-btn layui-btn-xs" id="edit" lay-event="edit">编辑</a>
                <a href="javascript:;" class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
                {{# } else { }}
                <a href="javascript:;" class="layui-btn layui-btn-normal layui-btn-xs"  lay-event="info">详情</a>
                <a href="javascript:;" class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
                {{# } }}
            </script>
        </form>

    <script type="text/javascript" src="${ctx}/js/system/notice.js"></script>
    </body>
</html>