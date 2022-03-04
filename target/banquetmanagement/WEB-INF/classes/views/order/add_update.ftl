<!DOCTYPE html>
<html>
    <head>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" style="width:80%;">
            <#-- 订单ID -->
            <input name="id" type="hidden" value="${(orderInfo.id)!}"/>
            <#if (orderInfo.id)??>
                <div class="layui-form-item layui-row layui-col-xs12">
                    <label class="layui-form-label">订单编号</label>
                    <div class="layui-input-block">
                        <input type="text" class="layui-input khno"
                               lay-verify="required" name="dId" id="dId"  value="${(orderInfo.oId)!}" readonly="readonly">
                    </div>
                </div>
            </#if>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">客户姓名</label>
                <div class="layui-input-block">
                    <select name="khno" xm-select="selectCustomerName" xm-select-radio="">
                    </select>
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">套餐名称</label>
                <div class="layui-input-block">
                    <select name="pId" xm-select="selectPackageName" xm-select-radio="">
                    </select>
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">套餐数量</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input packagePrice"
                           lay-verify="required" name="packageNumber" id="packageNumber"  value="${(orderInfo.packageNumber)!}" placeholder="请输入套餐数量">
                </div>
            </div>
            <#if (orderInfo.id)??>
                <div class="layui-form-item layui-row layui-col-xs12">
                    <label class="layui-form-label">订单总金额</label>
                    <div class="layui-input-block">
                        <input type="text" class="layui-input khno"
                               lay-verify="required" name="totalprice" id="totalprice"  value="${(orderInfo.totalprice)!}" readonly="readonly">
                    </div>
                </div>
            </#if>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">支付状态</label>
                <div class="layui-input-block">
                    <select name="payState" id="payState" value="${(orderInfo.payState)!}" >
                        <option value="">请选择支付状态</option>
                        <option value="0" <#if ((orderInfo.payState)!"2") =="0" >selected</#if>>未支付</option>
                        <option value="1" <#if ((orderInfo.payState)!"2") =="1" >selected</#if>>已支付</option>
                    </select>
                </div>
            </div>

            <br/>
            <div class="layui-form-item layui-row layui-col-xs12">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-lg" lay-submit=""
                            lay-filter="addOrUpdateOrder">确认
                    </button>
                    <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
                </div>
            </div>
        </form>

    <script type="text/javascript" src="${ctx}/js/order/add.update.js"></script>
    </body>
</html>