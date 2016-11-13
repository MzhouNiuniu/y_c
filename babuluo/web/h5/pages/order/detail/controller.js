angular.module('AndSell.H5.Main').controller('pages_order_detail_Controller', function ($scope, $state, $stateParams, weUI, productFactory, orderFactory, modalFactory) {

    modalFactory.setTitle('订单详情');
    modalFactory.setBottom(false);

    $scope.FILE_SERVER_DOMAIN = FILE_SERVER_DOMAIN;

    $scope.initData = function () {
        $scope.getOrder($stateParams.ORDER_ID);
        $scope.shop = getCookie('currentShopInfo');
    }

    $scope.getOrder = function (id) {
        orderFactory.getOrderById({'SHOP_ORDER.ID': id}, function (response) {
            response.data[0]['SHOP_ORDER.DATETIME_ADD'] = getDate(response.data[0]['SHOP_ORDER.DATETIME_ADD']);
            $scope.orderDetailList = JSON.parse(response.data[0]['SHOP_ORDER.ORDER_INFO']);
            $scope.order = response.data[0];
            $scope.orderDetailList.forEach(function (ele) {
                setContentsInfoForOrder(ele);
            });
        });
    }

    //取消订单
    $scope.cancelOrder = function () {
        weUI.dialog.alert("提示", "确认取消该订单", function () {
            orderFactory.cancelOrder({'SHOP_ORDER.ID': $scope.order['SHOP_ORDER.ID']}, function (response) {
                weUI.toast.ok('取消订单成功');
                $scope.getOrder($scope.order['SHOP_ORDER.ID']);
            }, function (response) {
                weUI.toast.error(response.msg);
            });
        });
    }

    //确认提货
    $scope.getPrdNow = function () {
        weUI.dialog.alert("提示", "确认提货", function () {
            orderFactory.acceptOrder({'SHOP_ORDER.ID': $scope.order['SHOP_ORDER.ID']}, function (response) {
                if (response.code == 0) {
                    weUI.toast.ok('收货成功');
                    $scope.getOrder($scope.order['SHOP_ORDER.ID']);
                } else {
                    weUI.toast.error(response.msg);
                }
            });
        });
    }

    //立即支付
    $scope.payNow = function () {
        weUI.dialog.alert("提示", "确认支付该订单", function () {
            orderFactory.payOrder({'SHOP_ORDER.ID': $scope.order['SHOP_ORDER.ID']}, function (response) {
                weUI.toast.ok('支付成功');
                $scope.getOrder($scope.order['SHOP_ORDER.ID']);
            }), function (response) {
                weUI.toast.error(response.msg);
            };
        });
    }

    //评价订单
    $scope.commentOrder = function () {

    }

})