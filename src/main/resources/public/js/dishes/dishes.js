layui.use(['table','layer','formSelects'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    var formSelects = layui.formSelects;

    /**
     * 加载数据表格
     */
    var tableIns = table.render({
        id:'dishesTable'
        // 容器元素的ID属性值
        ,elem: '#dishesList'
        // 容器的高度 full-差值
        ,height: 'full-125'
        // 单元格最小的宽度
        ,cellMinWidth:95
        // 访问数据的URL（后台的数据接口）
        ,url: ctx + '/dishes/list'
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
            ,{field: 'dishesName', title: '菜品名称', align:'center'}
            ,{field: 'dishesPrice', title: '菜品价格', align:'center'}
            ,{field: 'dishesType', title: '菜品类别', align:'center'}
            ,{field: 'createDate', title: '创建时间', align:'center'}
            ,{field: 'updateDate', title: '修改时间', align:'center'}
            ,{title:'操作',templet:'#dishesListBar', fixed: 'right', align:'center', minWidth:150}
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
                dishesName: $("[name='dishesName']").val() // 菜品名称
                ,dishesType: $("[name='dishesType']").val() // 菜品类别
                ,ldishesPrice: $("[name='ldishesPrice']").val() // 菜品最低价格
                ,gdishesPrice: $("[name='gdishesPrice']").val() // 菜品最高价格
            }
            ,page: {
                curr: 1 // 重新从第 1 页开始
            }
        });
    });

    /**
     * 监听头部工具栏
     */
    table.on('toolbar(dishess)', function (data) {

        if (data.event == "add") { // 添加菜品
            // 打开添加/修改菜品的对话框
            openAddOrUpdateDishesDialog();
        } else if (data.event == "del") { // 删除菜品
            // 获取被选中的数据的信息
            var checkStatus = table.checkStatus(data.config.id);
            // console.log(checkStatus);
            // 删除多个菜品记录
            deleteDishess(checkStatus.data);
        }
    });
    
    /**
     * 删除多条菜品记录
     * @param dishesData
     */
    function deleteDishess(dishesData) {
        // 判断用户是否选择了要删除的记录
        if (dishesData.length == 0) {
            layer.msg("请选择要删除的菜品！", {icon:5});
            return;
        }
        // 询问用户是否确认删除
        layer.confirm('您确定要删除选中的菜品吗？',{icon:3, title:'菜品信息管理'}, function (index) {
            // 关闭确认框
            layer.close(index);
            // 传递的参数是数组   ids=1&ids=2&ids=3
            var ids = "ids=";
            // 循环选中的行记录的数据
            for(var i = 0; i < dishesData.length; i++) {
                if(i < dishesData.length -1) {
                    ids = ids + dishesData[i].id + "&ids="
                } else {
                    ids = ids + dishesData[i].id;
                }
            }
            // console.log(ids);

            // 发送ajax请求，执行删除菜品
            $.ajax({
                type:"post",
                url:ctx + "/dishes/delete",
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
    table.on('tool(dishess)', function (data) {

        if (data.event == "edit") { // 更新菜品
            // 打开添加/修改套餐的对话框
            openAddOrUpdateDishesDialog(data.data.id);
        } else if (data.event == "del") { // 删除菜品
            // 删除单条菜品记录
            deleteDishes(data.data.id);
        }
    });

    /**
     * 删除单条菜品记录
     * @param id
     */
    function deleteDishes(id) {
        // 弹出确认框，询问用户是否确认删除
        layer.confirm('确定要删除该菜品吗？',{icon:3, title:"菜品信息管理"}, function (index) {
            // 关闭确认框
            layer.close(index);

            // 发送ajax请求，删除菜品
            $.ajax({
                type:"post",
                url:ctx + "/dishes/delete",
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
    function openAddOrUpdateDishesDialog(id) {
        var title = "<h3>菜品信息管理 - 添加菜品</h3>";
        var url = ctx + "/dishes/toAddOrUpdateDishesPage";

        // 判断id是否为空；如果为空，则为添加操作，否则是修改操作
        if (id != null && id != '') {
            title = "<h3>菜品信息管理 - 更新菜品</h3>";
            url+= "?id="+id; // 传递主键，查询数据
        }

        // iframe层
        layui.layer.open({
            // 类型
            type: 2,
            // 标题
            title: title,
            // 宽高
            area: ['600px', '400px'],
            // url地址
            content: url,
            // 可以最大化与最小化
            maxmin:true
        });
    }

    /**
     * 加载菜品类别下拉框
     *
     * 配置远程搜索, 请求头, 请求参数, 请求类型等
     *
     * formSelects.config(ID, Options, isJson);
     *
     * @param ID        xm-select的值
     * @param Options   配置项
     * @param isJson    是否传输json数据, true将添加请求头 Content-Type: application/json; charset=UTF-8
     */
    formSelects.config("selectDishesType",{
        type:"post", // 请求方式
        searchUrl: ctx+"/dishestype/queryAllDishesType",  // 请求地址
        keyName: 'dishes_type',  // 下拉框中的文本内容，要与返回的数据中对应key一致
        keyVal: 'dishes_type'
    }, true);

});