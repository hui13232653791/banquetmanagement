<!DOCTYPE html>
<html>
    <head>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" style="width:80%;">
            <#-- 待办ID -->
            <input name="id" type="hidden" value="${(noticeInfo.id)!}"/>
            <div class="layui-form-item">
                <label class="layui-form-label">待办标题</label>
                <div class="layui-input-block">
                    <input type="text" name="title" id="title" value="${(noticeInfo.title)!}" lay-verify="required" autocomplete="off" class="layui-input input-radius" placeholder="请输入待办标题">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">待办内容</label>
                <div class="layui-input-block">
                    <textarea class="layui-textarea" rows="10" name="content" id="content" placeholder="请输入待办内容" >${(noticeInfo.content)!}</textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">待办状态</label>
                <div class="layui-input-block">
                    <select name="state" id="state" value="${(noticeInfo.state)!}">
                        <option value="0">未完成</option>
                        <option value="1">已完成</option>
                    </select>
                </div>
            </div>

            <br/>
            <div class="layui-form-item layui-row layui-col-xs12">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-lg" lay-submit=""
                            lay-filter="addOrUpdateNotice">确认
                    </button>
                    <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
                </div>
            </div>
        </form>

    <script type="text/javascript" src="${ctx}/js/system/add.update.js"></script>
    </body>
</html>