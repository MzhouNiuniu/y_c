AndSellH5MainModule.controller('H5.PrdListController', function ($scope, $state, $stateParams, productFactory, modalFactory) {

    modalFactory.setTitle('商品列表');
    modalFactory.setBottom(true);

    $scope.initData = function () {
        $scope.filter = {
            PAGE_SIZE: 10, PN: 1, 'SHOP_PRODUCT.PRD_NAME': $stateParams.keyword,'STOCK_REALTIME.STORE_ID' : 1050
        };
        $scope.hasNextPage = true;
        $scope.loading = false;  //状态标记
        $scope.prdList = new Array;
        $scope.storeId = 1050 ;
        $scope.getPrd();
        $scope.getCartInfoInCookie();
    }

    //获取商品列表
    $scope.getPrd = function () {
        productFactory.getProduct($scope.filter).get({}, function (response) {
            console.log(response);
            Array.prototype.push.apply($scope.prdList, response.data);//数组合并
            $scope.classList = response.extraData.classList;
            $scope.page = response.extraData.page;
            if ($scope.page.querySize > $scope.page.pageIndex * $scope.page.pageSize) {
                $scope.hasNextPage = true;
            } else {
                $scope.hasNextPage = false;
            }
            $scope.loading = false;
        });
    }

    //查询商品
    $scope.searchPrd = function () {
        $scope.prdList = new Array;
        $scope.getPrd();
    }

    //跳转至详情页
    $scope.toDetail = function (id) {
        $state.go('prd-detail', {PRD_ID: id});
    }

    //跳转至详情页
    $scope.filterClass = function (classId) {
        $scope.prdList = new Array;
        $scope.filter['SHOP_PRODUCT.CLASS_ID'] = classId;
        $scope.getPrd();
    }

    //跳出弹出框选择sku
    $scope.selectSKU = function (id) {
        $scope.cartPrdId = id;
        $scope.cartModalShow = true;
    }

    //更新购物车价格
    $scope.updateCartPrice = function () {
        var price = 0;
        var size = 0;
        $scope.skuList.forEach(function (ele) {
            price += ele['SHOP_PRODUCT_SKU.REAL_PRICES'] * ele['SHOP_PRODUCT_SKU.SIZE'];
            size += ele['SHOP_PRODUCT_SKU.SIZE'];
        });
        $scope.totalPrice = price;
        $scope.totalSize = size;
    }

    //得到购物车信息
    $scope.getCartInfoInCookie = function () {

        $scope.skuList = new Array;
        var cartInfo = getCookie('cartInfo');
        var cartSize = getCookie('cartSize');
        if (cartInfo == '') {
            cartInfo = new Array;
            cartSize = {};
        } else {
            cartInfo = JSON.parse(cartInfo);
            cartSize = JSON.parse(cartSize);
        }
        if (cartInfo.length > 0) {
            var params = {};
            params['SHOP_PRODUCT_SKU.SKU_IDS'] = cartInfo.toString();
            productFactory.getProductSkuBySkuIds(params).get({}, function (response) {
                console.log(response);
                $scope.skuList = response.data;
                $scope.skuList.forEach(function (ele) {
                    ele['SHOP_PRODUCT_SKU.SIZE'] = cartSize[ele['SHOP_PRODUCT_SKU.SKU_ID']];
                });
                $scope.updateCartPrice();
            })
        } else {
            $scope.updateCartPrice();
        }

    }

    //结算
    $scope.addOrder = function () {
        var list = new Array;
        $scope.skuList.forEach(function (ele) {
            list.push(ele['SHOP_PRODUCT_SKU.SKU_ID']);
        });
        if ($scope.skuList.length > 0) {
            $state.go('order-add', {SKU_IDS: list.toString()});
        } else {
            alert('至少选择一件商品');
        }
    }

    //数量减
    $scope.lessSize = function (item) {
        if (item['SHOP_PRODUCT_SKU.SIZE'] > 1) {
            item['SHOP_PRODUCT_SKU.SIZE'] = item['SHOP_PRODUCT_SKU.SIZE'] - 1;
        }
        //修改cookie
        var cartSize = getCookie('cartSize');
        if (cartSize == '') {
            cartSize = {};
        } else {
            cartSize = JSON.parse(cartSize);
        }
        cartSize[item['SHOP_PRODUCT_SKU.SKU_ID']] -= 1;
        setCookie('cartSize', JSON.stringify(cartSize));
        $scope.updateCartPrice();
    }

    //数量加
    $scope.moreSize = function (item) {
        item['SHOP_PRODUCT_SKU.SIZE'] = item['SHOP_PRODUCT_SKU.SIZE'] + 1;
        //修改cookie
        var cartSize = getCookie('cartSize');
        if (cartSize == '') {
            cartSize = {};
        } else {
            cartSize = JSON.parse(cartSize);
        }
        cartSize[item['SHOP_PRODUCT_SKU.SKU_ID']] += 1;
        setCookie('cartSize', JSON.stringify(cartSize));
        $scope.updateCartPrice();
    }

    //购物车添加成功
    $scope.addToCartSuccess = function () {
        $scope.getCartInfoInCookie();
    }

    //下拉更多商品
    $scope.getMorePrd = function () {
        if ($scope.page != undefined) {
            $scope.filter.PN = $scope.page.pageIndex + 1;
        }
        $scope.getPrd();
    };

    //下拉监听器
    $(document.body).infinite().on("infinite", function () {
        if ($scope.loading) return;
        $scope.loading = true;
        if ($scope.hasNextPage) {
            $scope.getMorePrd();
        }
    });

});
