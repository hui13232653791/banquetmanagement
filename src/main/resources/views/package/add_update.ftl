<!DOCTYPE html>
<html>
    <head>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" style="width:80%;">
            <#-- 套餐ID -->
            <input name="id" type="hidden" value="${(packageInfo.id)!}"/>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">套餐名称</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input packageName"
                           lay-verify="required" name="packageName" id="packageName"  value="${(packageInfo.packageName)!}" placeholder="请输入套餐名称">
                </div>
            </div>
            <#if (packageInfo.id)??>
                <div class="layui-form-item layui-row layui-col-xs12">
                    <label class="layui-form-label">套餐价格</label>
                    <div class="layui-input-block">
                        <input type="text" class="layui-input packagePrice"
                               lay-verify="required" name="packagePrice" id="packagePrice"  value="${(packageInfo.packagePrice)!}" placeholder="请先分配菜品">
                    </div>
                </div>
                <div class="layui-form-item layui-row layui-col-xs12">
                    <label class="layui-form-label">菜品数量</label>
                    <div class="layui-input-block">
                        <input type="text" class="layui-input dishesNumber" readonly="readonly"
                               lay-verify="required" name="dishesNumber" id="dishesNumber"  value="${(packageInfo.dishesNumber)!}" placeholder="请先分配菜品">
                    </div>
                </div>
            </#if>
            <br/>
            <div class="layui-form-item layui-row layui-col-xs12">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-lg" lay-submit=""
                            lay-filter="addOrUpdatePackage">确认
                    </button>
                    <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
                </div>
            </div>
        </form>

    <script type="text/javascript" src="${ctx}/js/package/add.update.js"></script>
    </body>
</html>