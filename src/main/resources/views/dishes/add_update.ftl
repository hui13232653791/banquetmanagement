<!DOCTYPE html>
<html>
    <head>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" style="width:80%;">
            <#-- 菜品ID -->
            <input name="id" type="hidden" value="${(dishesInfo.id)!}"/>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">菜品名称</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input packageName"
                           lay-verify="required" name="dishesName" id="dishesName"  value="${(dishesInfo.dishesName)!}" placeholder="请输入菜品名称">
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">菜品价格</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input packagePrice"
                           lay-verify="required" name="dishesPrice" id="dishesPrice"  value="${(dishesInfo.dishesPrice)!}" placeholder="请输入菜品价格">
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">菜品类别</label>
                <div class="layui-input-block">
                    <select name="dishesType" xm-select="selectDishesType" xm-select-radio="">
                        <option value="">请选择菜品类别</option>
                    </select>
                </div>
            </div>
            <br/>
            <div class="layui-form-item layui-row layui-col-xs12">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-lg" lay-submit=""
                            lay-filter="addOrUpdateDishes">确认
                    </button>
                    <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
                </div>
            </div>
        </form>

    <script type="text/javascript" src="${ctx}/js/dishes/add.update.js"></script>
    </body>
</html>