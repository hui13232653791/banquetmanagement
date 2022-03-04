layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 加载数据表格
     */
    var tableIns = table.render({
        id:'orderTable'
        // 容器元素的ID属性值
        ,elem: '#orderList'
        // 容器的高度 full-差值
        ,height: 'full-125'
        // 单元格最小的宽度
        ,cellMinWidth:95
        // 访问数据的URL（后台的数据接口）
        ,url: ctx + '/order/list?state=0'
        // 开启分页
        ,page: true
        // 默认每页显示的数量
        ,limit:10
        // 每页页数的可选项
        ,limits:[10,20,30,40,50]
        // 开启头部工具栏
        ,toolbar:'#toolbarDemo'
        // 表头
        ,cols: [[
            // field：要求field属性值与返回的数据中对应的属性字段名一致
            // title：设置列的标题
            // sort：是否允许排序（默认：false）
            // fixed：固定列
            {type:'checkbox', fixed:'center'},
            {field: 'id', title: 'ID', align: 'center', width:100},
            {field: 'oId', title: '订单编号', align: 'center'},
            {field: 'khno', title: '客户姓名', align: 'center'},
            {field: 'pId', title: '套餐名称', align: 'center'},
            {field: 'packageNumber', title: '套餐数量/桌', align: 'center'},
            {field: 'totalprice', title: '订单总价', align: 'center'},
            {field: 'payState', title: '支付状态', align: 'center', width:100, templet: function (d) {
                    if (d.payState == 0){
                        return "<div style='color: red'>未支付</div>"
                    }else{
                        return "<div style='color: green'>已支付</div>"
                    }
            }},
            {field: 'createDate', title: '创建时间', align: 'center'},
            {field: 'updateDate', title: '更新时间', align: 'center'},
            {title:'操作',templet:'#orderListBar', fixed: 'right', align:'center', minWidth:180}
        ]]
    });


    /**
     * 搜索按钮的点击事件
     */
    $(".search_btn").click(function () {
        /**
         * 表格重载
         *    多条件查询
         */
        tableIns.reload({
            // 设置需要传递给后端的参数
            where: { //设定异步数据接口的额外参数，任意设
                // 通过文本框，设置传递的参数
                oId: $("[name='oId']").val() // 订单编号
                ,khno: $("[name='khno']").val() // 客户姓名
                ,payState:$("[name='payState']").val()  // 支付状态
                ,startTime:$("[name='startTime']").val() // 开始时间
                ,endTime:$("[name='endTime']").val() // 结束时间
            }
            ,page: {
                curr: 1 // 重新从第 1 页开始
            }
        });
    });

    /**
     * 监听头部工具栏
     */
    table.on('toolbar(orders)', function (data) {

        if (data.event == "add") { // 添加订单
            // 打开添加/修改订单的对话框
            openAddOrUpdateOrderDialog();
        } else if (data.event == "del") { // 删除订单
            // 获取被选中的数据的信息
            var checkStatus = table.checkStatus(data.config.id);
            // console.log(checkStatus);
            // 删除多个订单记录
            deleteOrders(checkStatus.data);
        }
    });
    
    /**
     * 删除多条订单记录
     * @param orderData
     */
    function deleteOrders(orderData) {
        // 判断用户是否选择了要删除的订单
        if (orderData.length == 0) {
            layer.msg("请选择要删除的订单！", {icon:5});
            return;
        }
        // 询问用户是否确认删除
        layer.confirm('您确定要删除选中的订单吗？',{icon:3, title:'订单信息管理'}, function (index) {
            // 关闭确认框
            layer.close(index);
            // 传递的参数是数组   ids=1&ids=2&ids=3
            var ids = "ids=";
            // 循环选中的行记录的数据
            for(var i = 0; i < orderData.length; i++) {
                if(i < orderData.length -1) {
                    ids = ids + orderData[i].id + "&ids="
                } else {
                    ids = ids + orderData[i].id;
                }
            }
            // console.log(ids);

            // 发送ajax请求，执行删除订单
            $.ajax({
                type:"post",
                url:ctx + "/order/delete",
                data:ids, // 传递的参数是数组 ids=1&ids=2&ids=3
                success:function (result) {
                    // 判断删除结果
                    if (result.code == 200) {
                        // 提示成功
                        layer.msg("删除成功！",{icon:6});
                        // 刷新表格
                        tableIns.reload();
                    } else {
                        // 提示失败
                        layer.msg(result.msg, {icon:5});
                    }
                }
            });
        });
    }


    /**
     * 监听行工具栏
     */
    table.on('tool(orders)', function (data) {

        if (data.event == "edit") { // 更新订单
            // 打开添加/修改待办的对话框
            openAddOrUpdateOrderDialog(data.data.id);
        } else if (data.event == "del") { // 删除订单
            // 删除单条订单记录
            deleteOrder(data.data.id);
        } else if (data.event == "info") { // 归档订单
            // 结束订单状态
            endOrder(data.data.id,data.data.payState);
        }
    });

    /**
     * 删除单条订单记录
     * @param id
     */
    function deleteOrder(id) {
        // 弹出确认框，询问用户是否确认删除
        layer.confirm('确定要删除该订单吗？',{icon:3, title:"订单信息管理"}, function (index) {
            // 关闭确认框
            layer.close(index);

            // 发送ajax请求，删除记录
            $.ajax({
                type:"post",
                url:ctx + "/order/delete",
                data:{
                    ids:id
                },
                success:function (result) {
                    // 判断删除结果
                    if (result.code == 200) {
                        // 提示成功
                        layer.msg("删除成功！",{icon:6});
                        // 刷新表格
                        tableIns.reload();
                    } else {
                        // 提示失败
                        layer.msg(result.msg, {icon:5});
                    }
                }
            });
        });
    }


    /**
     * 打开添加/修改订单的对话框
     */
    function openAddOrUpdateOrderDialog(id) {
        var title = "<h3>订单信息管理 - 添加订单</h3>";
        var url = ctx + "/order/toAddOrUpdateOrderPage";

        // 判断id是否为空；如果为空，则为添加操作，否则是修改操作
        if (id != null && id != '') {
            title = "<h3>订单信息管理 - 更新订单</h3>";
            url+= "?id="+id; // 传递主键，查询数据
        }

        // iframe层
        layui.layer.open({
            // 类型
            type: 2,
            // 标题
            title: title,
            // 宽高
            area: ['650px', '500px'],
            // url地址
            content: url,
            // 可以最大化与最小化
            maxmin:true
        });
    }

    /**
     * 归档订单
     * @param id
     */
    function endOrder(id,payState) {
        if (payState==0){
            // 弹出确认框，询问用户是否确认归档订单
            layer.confirm('该订单未支付，确定要归档吗？',{icon:3, title:"订单信息管理"}, function (index) {
                // 关闭确认框
                layer.close(index);

                // 发送ajax请求，归档记录
                $.ajax({
                    type:"post",
                    url:ctx + "/order/endOrder",
                    data:{
                        id:id
                    },
                    success:function (result) {
                        // 判断结束结果
                        if (result.code == 200) {
                            // 提示成功
                            layer.msg("归档订单成功！",{icon:6});
                            // 刷新表格
                            tableIns.reload();
                        } else {
                            // 提示失败
                            layer.msg(result.msg, {icon:5});
                        }
                    }
                });
            });
        }else{
            // 弹出确认框，询问用户是否确认归档订单
            layer.confirm('确定要归档该订单吗？',{icon:3, title:"订单信息管理"}, function (index) {
                // 关闭确认框
                layer.close(index);

                // 发送ajax请求，归档记录
                $.ajax({
                    type:"post",
                    url:ctx + "/order/endOrder",
                    data:{
                        id:id
                    },
                    success:function (result) {
                        // 判断结束结果
                        if (result.code == 200) {
                            // 提示成功
                            layer.msg("归档订单成功！",{icon:6});
                            // 刷新表格
                            tableIns.reload();
                        } else {
                            // 提示失败
                            layer.msg(result.msg, {icon:5});
                        }
                    }
                });
            });
        }
    }

});

layui.use('laydate', function(){
    var laydate = layui.laydate;
    //初始化时间选择器
    laydate.render({
        elem: "#startTime",
        type: "datetime"
    });
    laydate.render({
        elem: "#endTime",
        type: "datetime"
    });
});