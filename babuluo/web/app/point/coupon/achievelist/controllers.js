angular.module('AndSell.Main').controller('point_coupon_achievelist_Controller', function (http,$scope,pointFactory,modalFactory) {
	//设置页面Title
    modalFactory.setTitle('年货大集入场券兑换');

    modalFactory.setBottom(false);
    
    $scope.TITLE = ['优惠券','姓名','电话','优惠券有效期','领取时间','兑换状态','操作'];
    
    $('#startDate').datetimepicker({
    	language:  'zh-CN',
        //minView: "month",
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose: true,
        todayBtn:'linked'
    }).on('changeDate',function(){
    	
    });
    
    $('#endDate').datetimepicker({
    	language:  'zh-CN',
        //minView: "month",
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose: true,
        todayBtn:'linked'
    });
   
    
    $scope.coupon = {};

    $scope.bindData = function (response) {
        $scope.offlineCouponList = response.data;
        $scope.querySize = response.extraData.page.querySize;
        console.log(response);
    };

    $scope.search = function () {
        $scope.filter['MEMBER_COUPON.QUERY_CONTENT'] = $scope.queryContent;
    };

   $scope.changeState = function(id){
	   modalFactory.showAlert("确认兑换吗?", function () {
	   pointFactory.getOfflineCoupon({'MEMBER_COUPON.ID': id}, function (res) {
	           modalFactory.showShortAlert("兑换成功");
	           location.reload();
	       }, function (response) {
	           modalFactory.showShortAlert(response.msg);
	       });
       });
	  
   }
    
    
    $scope.outPutQuery = function(){
    	var url = "../../outputQuery";
        $scope.outputList = {};
        $scope.outputList['type'] = "orderlist";
        $scope.outputList['param'] = JSON.stringify($scope.filter);
        modalFactory.showShortAlert("正在导出 请稍等");
        http.post(url, $scope.outputList, function (response) {
            if(response != "failure"){
            	 location.href = "/ticket" + response;
            }
        },function(response){
        	modalFactory.showShortAlert("导出失败!");
        });
    }

});
