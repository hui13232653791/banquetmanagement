<!DOCTYPE html>
<html>
    <head>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" style="width:80%;">
            <#-- 订单ID -->
            <input name="id" type="hidden" value="${(orderInfo.id)!}"/>

            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">订单编号</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input khno"
                           lay-verify="required" name="dId" id="dId"  value="${(orderInfo.oId)!}" readonly="readonly">
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">客户姓名</label>
                <div class="layui-input-block">
                    <select name="khno" xm-select="selectCustomerName" xm-select-radio="" disabled="disabled">
                    </select>
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">套餐名称</label>
                <div class="layui-input-block">
                    <select name="pId" xm-select="selectPackageName" xm-select-radio="" disabled="disabled">
                    </select>
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">套餐数量</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input packagePrice"
                           lay-verify="required" name="packageNumber" id="packageNumber" value="${(orderInfo.packageNumber)!}" readonly="readonly" placeholder="请输入套餐数量">
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">订单总金额</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input khno"
                           lay-verify="required" name="totalprice" id="totalprice"  value="${(orderInfo.totalprice)!}" readonly="readonly">
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">支付状态</label>
                <div class="layui-input-block">
                    <select name="payState" id="payState" value="${(orderInfo.payState)!}" disabled="disabled">
                        <option value="">请选择支付状态</option>
                        <option value="0" <#if ((orderInfo.payState)!"2") =="0" >selected</#if>>未支付</option>
                        <option value="1" <#if ((orderInfo.payState)!"2") =="1" >selected</#if>>已支付</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">订单状态</label>
                <div class="layui-input-block">
                    <select name="state" id="state" value="${(orderInfo.state)!}">
                        <option value="">请选择订单状态</option>
                        <option value="0" <#if ((orderInfo.state)!2) == 0 >selected</#if>>未完成</option>
                        <option value="1" <#if ((orderInfo.state)!2) == 1 >selected</#if>>已完成</option>
                    </select>
                </div>
            </div>

            <br/>
            <div class="layui-form-item layui-row layui-col-xs12">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-lg" lay-submit=""
                            lay-filter="UpdateEndOrder">确认
                    </button>
                    <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
                </div>
            </div>
        </form>

    <script type="text/javascript" src="${ctx}/js/order/endprderinfo.js"></script>
    </body>
</html>