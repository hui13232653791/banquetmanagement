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
                    <input type="text" name="title" id="title" value="${(noticeInfo.title)!}" lay-verify="required" autocomplete="off" class="layui-input input-radius" readonly="readonly">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">待办内容</label>
                <div class="layui-input-block">
                    <textarea class="layui-textarea" rows="10" name="content" id="content" readonly="readonly" >${(noticeInfo.content)!}</textarea>
                </div>
            </div>

            <br/>
        </form>
    </body>
</html>