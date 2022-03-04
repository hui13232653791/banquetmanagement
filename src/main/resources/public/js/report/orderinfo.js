layui.use(['layer','echarts'], function () {
    var $ = layui.jquery,
        echarts = layui.echarts;

    //没有搜索，直接显示当前年份月销售额
    var year = new Date().getFullYear();
    $('#yearTime').val(year);
    selectcountOrderMake(year);

    /**
     * 搜索按钮的点击事件
     */
    $(".search_btn").click(function () {
        var yearTime = $("[name='yearTime']").val();
        // console.log(yearTime);
        selectcountOrderMake(yearTime);
    });


    function selectcountOrderMake(yearTime) {
        /**
         * 发送ajax请求，获取折线图所需要的数据
         */
        $.ajax({
            type:"get",
            url:ctx + "/order/countOrderMake",
            data:{
                yearTime:yearTime
            },
            dataType:"json",
            success:function (data) {
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('make'));

                // 指定图表的配置项和数据
                var option = {
                    // 标题
                    title: {
                        text: '订单月销售额统计',
                        left: 'center',
                        // 主标题文本样式设置
                        textStyle: {
                            fontSize: 28,
                            fontWeight: 'bolder',
                        }
                    },
                    // 提示框
                    tooltip: {
                        trigger: 'item',
                        formatter: function (params) {
                            var color = params.color;//图例颜色
                            var htmlStr ='<div>';
                            htmlStr += params.name + '月<br/>';//x轴的名称

                            //为了保证和原来的效果一样，这里自己实现了一个点的效果
                            htmlStr += '<span style="margin-right:5px;display:inline-block;width:10px;height:10px;border-radius:5px;background-color:'+color+';"></span>';

                            //添加一个汉字，这里你可以格式你的数字或者自定义文本内容
                            htmlStr += '销售额：'+params.value + '元';

                            htmlStr += '</div>';

                            return htmlStr;
                        }
                    },
                    // X轴
                    xAxis: {
                        name: '月份',
                        type: 'category',
                        data: data.data1
                    },
                    // Y轴
                    yAxis: {
                        name: '销售额',
                        type: 'value'
                    },
                    // 系列
                    series: [{
                        // 数据
                        data: data.data2,
                        // 折线图
                        type: 'line',
                        itemStyle: {
                            normal:{
                                label:{
                                    show:true
                                },
                                lineStyle:{
                                    width:3 //折线宽度
                                }
                            }
                        }
                    }]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        });
    }

});

layui.use('laydate', function(){
    var laydate = layui.laydate;
    //初始化时间选择器
    laydate.render({
        elem: "#yearTime",
        type: "year"
    });
});