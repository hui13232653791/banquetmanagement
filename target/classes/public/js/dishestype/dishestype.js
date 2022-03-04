layui.use(['table','layer','formSelects'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    var formSelects = layui.formSelects;

    /**
     * 加载数据表格
     */
    var tableIns = table.render({
        id:'dishesTypeTable'
        // 容器元素的ID属性值
        ,elem: '#dishesTypeList'
        // 容器的高度 full-差值
        ,height: 'full-125'
        // 单元格最小的宽度
        ,cellMinWidth:95
        // 访问数据的URL（后台的数据接口）
        ,url: ctx + '/dishestype/list'
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
            ,{field: 'id', title: '菜品类别编号',  sort: true, fixed: 'left'}
            ,{field: 'dishesType', title: '菜品类别名称', align:'center'}
            ,{title:'操作',templet:'#dishesTypeListBar', fixed: 'right', align:'center', minWidth:150}
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
                dishesType: $("[name='dishesType']").val() // 菜品类别名称
            }
            ,page: {
                curr: 1 // 重新从第 1 页开始
            }
        });
    });

    /**
     * 监听头部工具栏
     */
    table.on('toolbar(dishesTypes)', function (data) {

        if (data.event == "add") { // 添加菜品类别
            // 打开添加/修改菜品类别的对话框
            openAddOrUpdateDishesTypeDialog();
        } else if (data.event == "del") { // 删除菜品类别
            // 获取被选中的数据的信息
            var checkStatus = table.checkStatus(data.config.id);
            // console.log(checkStatus);
            // 删除多个菜品类别记录
            deleteDishesTypes(checkStatus.data);
        }
    });
    
    /**
     * 删除多条菜品类别记录
     * @param dishesTypeData
     */
    function deleteDishesTypes(dishesTypeData) {
        // 判断用户是否选择了要删除的记录
        if (dishesTypeData.length == 0) {
            layer.msg("请选择要删除的菜品类别！", {icon:5});
            return;
        }
        // 询问用户是否确认删除
        layer.confirm('您确定要删除选中的菜品类别吗？',{icon:3, title:'菜品类别管理'}, function (index) {
            // 关闭确认框
            layer.close(index);
            // 传递的参数是数组   ids=1&ids=2&ids=3
            var ids = "ids=";
            // 循环选中的行记录的数据
            for(var i = 0; i < dishesTypeData.length; i++) {
                if(i < dishesTypeData.length -1) {
                    ids = ids + dishesTypeData[i].id + "&ids="
                } else {
                    ids = ids + dishesTypeData[i].id;
                }
            }
            // console.log(ids);

            // 发送ajax请求，执行删除菜品
            $.ajax({
                type:"post",
                url:ctx + "/dishestype/delete",
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
    table.on('tool(dishesTypes)', function (data) {

        if (data.event == "edit") { // 更新菜品类别
            // 打开添加/修改菜品类别的对话框
            openAddOrUpdateDishesTypeDialog(data.data.id);
        } else if (data.event == "del") { // 删除菜品类别
            // 删除单条菜品类别记录
            deleteDishesType(data.data.id);
        }
    });

    /**
     * 删除单条菜品类别记录
     * @param id
     */
    function deleteDishesType(id) {
        // 弹出确认框，询问用户是否确认删除
        layer.confirm('确定要删除该菜品类别吗？',{icon:3, title:"菜品类别管理"}, function (index) {
            // 关闭确认框
            layer.close(index);

            // 发送ajax请求，删除菜品类别
            $.ajax({
                type:"post",
                url:ctx + "/dishestype/delete",
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
     * 打开添加/修改菜品的对话框
     */
    function openAddOrUpdateDishesTypeDialog(id) {
        var title = "<h3>菜品类别管理 - 添加菜品类别</h3>";
        var url = ctx + "/dishestype/toAddOrUpdateDishesTypePage";

        // 判断id是否为空；如果为空，则为添加操作，否则是修改操作
        if (id != null && id != '') {
            title = "<h3>菜品类别管理 - 更新菜品类别</h3>";
            url+= "?id="+id; // 传递主键，查询数据
        }

        // iframe层
        layui.layer.open({
            // 类型
            type: 2,
            // 标题
            title: title,
            // 宽高
            area: ['600px', '200px'],
            // url地址
            content: url,
            // 可以最大化与最小化
            maxmin:true
        });
    }


});