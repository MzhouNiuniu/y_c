AndSellH5MainModule.config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
        .state("home", {
            url: "/home",
            templateUrl: "/AndSell/h5/pages/home/index.html",
            controller: "H5.HomeController"
        })
        .state("prd-detail", {
            url: "/PrdDetail",
            params: {PRD_ID: 1242},
            templateUrl: "/AndSell/h5/pages/product/detail/index.html",
            controller: "H5.PrdDetailController"
        })
        .state("cart", {
            url: "/cart",
            templateUrl: "/AndSell/h5/pages/cart/index.html",
            controller: "H5.CartController"
        })
        .state("order-add", {
            url: "/orderAdd",
            params: {SKU_IDS: '1060'},
            templateUrl: "/AndSell/h5/pages/order/add/index.html",
            controller: "H5.OrderAddController"
        })
        .state("order-success", {
            url: "/orderSuccess",
            params: {ORDER_ID: '1023'},
            templateUrl: "/AndSell/h5/pages/payment/check_out.html",
            controller: "H5.OrderSuccessController"
        })
        .state("pay-success", {
            url: "/paySuccess",
            templateUrl: "/AndSell/h5/pages/payment/check_out_success.html",
            controller: "H5.PaySuccessController"
        })
        .state("order-detail", {
            url: "/orderDetail",
            params: {ORDER_ID: '1030'},
            templateUrl: "/AndSell/h5/pages/order/detail/index.html",
            controller: "H5.OrderDetailController"
        })
        .state("prd-List", {
            url: "/prdList",
            templateUrl: "/AndSell/h5/pages/product/list/index.html",
            controller: "H5.PrdListController"
        })
        .state("order-List", {
            url: "/orderList",
            templateUrl: "/AndSell/h5/pages/order/list/index.html",
            controller: "H5.OrderListController"
        })

});