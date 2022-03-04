<!DOCTYPE html>
<html>
    <head>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" style="width:80%;">
            <#-- 客户ID -->
            <input name="id" type="hidden" value="${(customerInfo.id)!}"/>
            <#if (customerInfo.id)??>
                <div class="layui-form-item layui-row layui-col-xs12">
                    <label class="layui-form-label">客户编号</label>
                    <div class="layui-input-block">
                        <input type="text" class="layui-input khno"
                               lay-verify="required" name="khno" id="khno"  value="${(customerInfo.khno)!}" readonly="readonly">
                    </div>
                </div>
            </#if>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">客户姓名</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input customerName"
                           lay-verify="required" name="customerName" id="customerName"  value="${(customerInfo.customerName)!}" placeholder="请输入客户姓名">
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">性别</label>
                    <div class="layui-input-inline">
                        <select name="sex" id="sex" value="${(customerInfo.sex)!}" >
                            <option value="">请选择性别</option>
                            <option value="1" <#if ((customerInfo.sex)!2) ==1 >selected</#if>>男</option>
                            <option value="0" <#if ((customerInfo.sex)!2) ==0 >selected</#if>>女</option>
                        </select>
                    </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">手机号</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input userEmail"
                           lay-verify="phone" name="phone" value="${(customerInfo.phone)!}" id="phone" placeholder="请输入手机号">
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">详细地址</label>
                <div class="layui-input-block">
                    <textarea class="layui-textarea" rows="3" name="address" id="address" placeholder="请输入详细地址" >${(customerInfo.address)!}</textarea>
                </div>
            </div>

            <br/>
            <div class="layui-form-item layui-row layui-col-xs12">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-lg" lay-submit=""
                            lay-filter="addOrUpdateCustomer">确认
                    </button>
                    <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
                </div>
            </div>
        </form>

    <script type="text/javascript" src="${ctx}/js/customer/add.update.js"></script>
    </body>
</html>