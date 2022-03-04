layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;


    /**
     * 加载数据表格
     */
    var tableIns = table.render({
        id:'packageTable'
        // 容器元素的ID属性值
        ,elem: '#packageList'
        // 容器的高度 full-差值
        ,height: 'full-125'
        // 单元格最小的宽度
        ,cellMinWidth:95
        // 访问数据的URL（后台的数据接口）
        ,url: ctx + '/package/list'
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
            ,{field: 'id', title: '编号',  sort: true, fixed: 'left'}
            ,{field: 'packageName', title: '套餐名称', align:'center'}
            ,{field: 'packagePrice', title: '套餐价格', align:'center'}
            ,{field: 'dishesNumber', title: '菜品数量', align:'center'}
            ,{field: 'createDate', title: '创建时间', align:'center'}
            ,{field: 'updateDate', title: '修改时间', align:'center'}
            ,{title:'操作',templet:'#packageListBar', fixed: 'right', align:'center', minWidth:150}
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
                packageName: $("[name='providerName']").val() // 套餐名称
                ,lpackagePrice: $("[name='lpackagePrice']").val() // 套餐最低价格
                ,gpackagePrice: $("[name='gpackagePrice']").val() // 套餐最高价格
            }
            ,page: {
                curr: 1 // 重新从第 1 页开始
            }
        });
    });

    /**
     * 监听头部工具栏
     */
    table.on('toolbar(packages)', function (data) {

        if (data.event == "add") { // 添加套餐
            // 打开添加/修改套餐的对话框
            openAddOrUpdatePackageDialog();
        } else if (data.event == "del") { // 删除套餐
            // 获取被选中的数据的信息
            var checkStatus = table.checkStatus(data.config.id);
            // console.log(checkStatus);
            // 删除多个套餐记录
            deletepackages(checkStatus.data);
        }
    });
    
    /**
     * 删除多条套餐记录
     * @param packageData
     */
    function deletepackages(packageData) {
        // 判断用户是否选择了要删除的记录
        if (packageData.length == 0) {
            layer.msg("请选择要删除的套餐！", {icon:5});
            return;
        }
        // 询问用户是否确认删除
        layer.confirm('您确定要删除选中的套餐吗？',{icon:3, title:'套餐信息管理'}, function (index) {
            // 关闭确认框
            layer.close(index);
            // 传递的参数是数组   ids=1&ids=2&ids=3
            var ids = "ids=";
            // 循环选中的行记录的数据
            for(var i = 0; i < packageData.length; i++) {
                if(i < packageData.length -1) {
                    ids = ids + packageData[i].id + "&ids="
                } else {
                    ids = ids + packageData[i].id;
                }
            }
            // console.log(ids);

            // 发送ajax请求，执行删除套餐
            $.ajax({
                type:"post",
                url:ctx + "/package/delete",
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
    table.on('tool(packages)', function (data) {

        if (data.event == "edit") { // 更新套餐
            // 打开添加/修改套餐的对话框
            openAddOrUpdatePackageDialog(data.data.id);
        } else if (data.event == "del") { // 删除套餐
            // 删除单条套餐记录
            deletePackage(data.data.id);
        }else if (data.event == "info"){ // 分配菜品
            // 打开分配菜品的对话框
            openAddGrantDialog(data.data.id);
        }
    });

    /**
     * 删除单条套餐记录
     * @param id
     */
    function deletePackage(id) {
        // 弹出确认框，询问用户是否确认删除
        layer.confirm('确定要删除该套餐吗？',{icon:3, title:"套餐信息管理"}, function (index) {
            // 关闭确认框
            layer.close(index);

            // 发送ajax请求，删除套餐
            $.ajax({
                type:"post",
                url:ctx + "/package/delete",
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
     * 打开添加/修改套餐的对话框
     */
    function openAddOrUpdatePackageDialog(id) {
        var title = "<h3>套餐信息管理 - 添加套餐</h3>";
        var url = ctx + "/package/toAddOrUpdatePackagePage";

        // 判断id是否为空；如果为空，则为添加操作，否则是修改操作
        if (id != null && id != '') {
            title = "<h3>套餐信息管理 - 更新套餐</h3>";
            url+= "?id="+id; // 传递主键，查询数据
        }

        // iframe层
        layui.layer.open({
            // 类型
            type: 2,
            // 标题
            title: title,
            // 宽高
            area: ['600px', '300px'],
            // url地址
            content: url,
            // 可以最大化与最小化
            maxmin:true
        });
    }

    /**
     *
     * 打开分配菜品的对话框
     * @param id
     */
    function openAddGrantDialog(id) {
        var url = ctx + "/package/toAddGrantPage?packageId="+id;
        var title = "<h3>套餐信息管理 - 分配菜品</h3>";
        layui.layer.open({
            title:title,
            content:url,
            type:2,
            area:["500px","650px"],
            maxmin: true,
            end:function () {
                // 刷新表格
                tableIns.reload();
            }
        });
    }

});