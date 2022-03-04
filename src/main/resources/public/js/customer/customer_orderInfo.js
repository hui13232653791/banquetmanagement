layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 加载计划项数据表格
     */
    var tableIns = table.render({
        id:'customerOrderInfoTable'
        // 容器元素的ID属性值
        ,elem: '#customerOrderInfoList'
        // 容器的高度 full-差值
        ,height: 'full-125'
        // 单元格最小的宽度
        ,cellMinWidth:95
        // 访问数据的URL（后台的数据接口）
        ,url: ctx + '/order/list?khno=' + $("[name='khno']").val()
        // 开启分页
        ,page: true
        // 默认每页显示的数量
        ,limit:10
        // 每页页数的可选项
        ,limits:[10,20,30,40,50]
        // 表头
        ,cols: [[
            // field：要求field属性值与返回的数据中对应的属性字段名一致
            // title：设置列的标题
            // sort：是否允许排序（默认：false）
            // fixed：固定列
            {field: 'id', title: 'ID', align: 'center', width:50},
            {field: 'oId', title: '订单编号', align: 'center'},
            {field: 'khno', title: '客户姓名', align: 'center', width:90},
            {field: 'pId', title: '套餐名称', align: 'center', width:90},
            {field: 'packageNumber', title: '套餐数量/桌', align: 'center', width:110},
            {field: 'totalprice', title: '订单总价', align: 'center', width:90},
            {field: 'payState', title: '支付状态', align: 'center', width:90, templet: function (d) {
                if (d.payState == 0){
                    return "<div style='color: red'>未支付</div>"
                }else{
                    return "<div style='color: green'>已支付</div>"
                }
            }},
            {field: 'state', title: '归档状态', align: 'center', width:90, templet: function (d) {
                    if (d.state == 0){
                        return "<div style='color: red'>未归档</div>"
                    }else{
                        return "<div style='color: green'>已归档</div>"
                    }
                }},
            {field: 'createDate', title: '创建时间', align: 'center'},
            {field: 'updateDate', title: '更新时间', align: 'center'},
        ]]
    });
});
