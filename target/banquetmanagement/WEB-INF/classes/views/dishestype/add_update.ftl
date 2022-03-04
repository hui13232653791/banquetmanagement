<!DOCTYPE html>
<html>
    <head>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" style="width:80%;">
            <#-- 菜品类别ID -->
            <input name="id" type="hidden" value="${(dishesTypeInfo.id)!}"/>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">菜品名称</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input packageName"
                           lay-verify="required" name="dishesType" id="dishesType"  value="${(dishesTypeInfo.dishesType)!}" placeholder="请输入菜品类别名称">
                </div>
            </div>
            <br/>
            <div class="layui-form-item layui-row layui-col-xs12">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-lg" lay-submit=""
                            lay-filter="addOrUpdateDishesType">确认
                    </button>
                    <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
                </div>
            </div>
        </form>

    <script type="text/javascript" src="${ctx}/js/dishestype/add.update.js"></script>
    </body>
</html>