layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 加载数据表格
     */
    var tableIns = table.render({
        id:'providerTable'
        // 容器元素的ID属性值
        ,elem: '#providerList'
        // 容器的高度 full-差值
        ,height: 'full-125'
        // 单元格最小的宽度
        ,cellMinWidth:95
        // 访问数据的URL（后台的数据接口）
        ,url: ctx + '/provider/list'
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
            {type:'checkbox', fixed:'center'}
            ,{field: 'id', title: '编号',  sort: true, fixed: 'left', width:100}
            ,{field: 'pno', title: '供应商编号', align:'center'}
            ,{field: 'providerName', title: '供应商姓名', align:'center'}
            ,{field: 'sex', title: '性别', align:'center', width:100,templet: function (d) {
                if (d.sex == 1){return '男'} else if(d.sex == 0){return '女'} else {return ''}
            }}
            ,{field: 'phone', title: '供应商号码', align:'center'}
            ,{field: 'address', title: '详细地址', align:'center'}
            ,{field: 'foodName', title: '供应食品名称', align:'center'}
            ,{field: 'price', title: '食品单价', align:'center', width:100}
            ,{field: 'createDate', title: '创建时间', align:'center'}
            ,{field: 'updateDate', title: '修改时间', align:'center'}
            ,{title:'操作',templet:'#providerListBar', fixed: 'right', align:'center', minWidth:150}
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
                pno: $("[name='pno']").val() // 供应商编号
                ,providerName: $("[name='providerName']").val() // 供应商名称
                ,foodName:$("[name='foodName']").val() // 供应食品名称
            }
            ,page: {
                curr: 1 // 重新从第 1 页开始
            }
        });
    });

    /**
     * 监听头部工具栏
     */
    table.on('toolbar(providers)', function (data) {

        if (data.event == "add") { // 添加供应商
            // 打开添加/修改供应商的对话框
            openAddOrUpdateProviderDialog();
        } else if (data.event == "del") { // 删除供应商
            // 获取被选中的数据的信息
            var checkStatus = table.checkStatus(data.config.id);
            // console.log(checkStatus);
            // 删除多个客户记录
            deleteProviders(checkStatus.data);
        }
    });
    
    /**
     * 删除多条供应商记录
     * @param providerData
     */
    function deleteProviders(providerData) {
        // 判断用户是否选择了要删除的记录
        if (providerData.length == 0) {
            layer.msg("请选择要删除的供应商！", {icon:5});
            return;
        }
        // 询问用户是否确认删除
        layer.confirm('您确定要删除选中的供应商吗？',{icon:3, title:'供应商信息管理'}, function (index) {
            // 关闭确认框
            layer.close(index);
            // 传递的参数是数组   ids=1&ids=2&ids=3
            var ids = "ids=";
            // 循环选中的行记录的数据
            for(var i = 0; i < providerData.length; i++) {
                if(i < providerData.length -1) {
                    ids = ids + providerData[i].id + "&ids="
                } else {
                    ids = ids + providerData[i].id;
                }
            }
            // console.log(ids);

            // 发送ajax请求，执行删除供应商
            $.ajax({
                type:"post",
                url:ctx + "/provider/delete",
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
    table.on('tool(providers)', function (data) {

        if (data.event == "edit") { // 更新供应商
            // 打开添加/修改供应商的对话框
            openAddOrUpdateProviderDialog(data.data.id);
        } else if (data.event == "del") { // 删除供应商
            // 删除单条供应商记录
            deleteProvider(data.data.id);
        }
    });

    /**
     * 删除单条供应商记录
     * @param id
     */
    function deleteProvider(id) {
        // 弹出确认框，询问用户是否确认删除
        layer.confirm('确定要删除该供应商吗？',{icon:3, title:"供应商信息管理"}, function (index) {
            // 关闭确认框
            layer.close(index);

            // 发送ajax请求，删除供应商
            $.ajax({
                type:"post",
                url:ctx + "/provider/delete",
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
     * 打开添加/修改供应商的对话框
     */
    function openAddOrUpdateProviderDialog(id) {
        var title = "<h3>供应商信息管理 - 添加供应商</h3>";
        var url = ctx + "/provider/toAddOrUpdateProviderPage";

        // 判断id是否为空；如果为空，则为添加操作，否则是修改操作
        if (id != null && id != '') {
            title = "<h3>供应商信息管理 - 更新供应商</h3>";
            url+= "?id="+id; // 传递主键，查询数据
        }

        // iframe层
        layui.layer.open({
            // 类型
            type: 2,
            // 标题
            title: title,
            // 宽高
            area: ['650px', '560px'],
            // url地址
            content: url,
            // 可以最大化与最小化
            maxmin:true
        });
    }

});