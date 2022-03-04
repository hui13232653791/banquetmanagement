<!DOCTYPE html>
<html>
    <head>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" style="width:80%;">
            <#-- 供应商ID -->
            <input name="id" type="hidden" value="${(providerInfo.id)!}"/>
            <#if (providerInfo.id)??>
                <div class="layui-form-item layui-row layui-col-xs12">
                    <label class="layui-form-label">供应商编号</label>
                    <div class="layui-input-block">
                        <input type="text" class="layui-input khno"
                               lay-verify="required" name="pno" id="pno" value="${(providerInfo.pno)!}" readonly="readonly">
                    </div>
                </div>
            </#if>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">供应商姓名</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input customerName"
                           lay-verify="required" name="providerName" id="providerName"  value="${(providerInfo.providerName)!}" placeholder="请输入姓名姓名">
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">性别</label>
                    <div class="layui-input-inline">
                        <select name="sex" id="sex" value="${(providerInfo.sex)!}" >
                            <option value="">请选择性别</option>
                            <option value="1" <#if ((providerInfo.sex)!2) ==1 >selected</#if>>男</option>
                            <option value="0" <#if ((providerInfo.sex)!2) ==0 >selected</#if>>女</option>
                        </select>
                    </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">手机号</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input userEmail"
                           lay-verify="phone" name="phone" value="${(providerInfo.phone)!}" id="phone" placeholder="请输入手机号">
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">详细地址</label>
                <div class="layui-input-block">
                    <textarea class="layui-textarea" rows="3" name="address" id="address" placeholder="请输入详细地址" >${(providerInfo.address)!}</textarea>
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">食品名称</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input foodName"
                           lay-verify="foodName" name="foodName" value="${(providerInfo.foodName)!}" id="foodName" placeholder="请输入供应食品">
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">食品单价</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input price"
                           lay-verify="price" name="price" value="${(providerInfo.price)!}" id="price" placeholder="请输入食品单价">
                </div>
            </div>


            <br/>
            <div class="layui-form-item layui-row layui-col-xs12">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-lg" lay-submit=""
                            lay-filter="addOrUpdateProvider">确认
                    </button>
                    <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
                </div>
            </div>
        </form>

    <script type="text/javascript" src="${ctx}/js/provider/add.update.js"></script>
    </body>
</html>