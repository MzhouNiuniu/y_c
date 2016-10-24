AndSellMainModule.controller('couponListController', function ($scope, $stateParams, couponFactory, modalFactory) {

    modalFactory.setTitle('优惠券管理');

    $scope.bindData = function (response) {
        console.log(123456);
        console.log(response);
        console.log(response.data);
        $scope.couponList = {};
        $scope.couponList = response.data;
        $scope.couponList.forEach(function (item) {
            // console.log('面值为：'+item['COUPON.RULE_ID']);//['COUPON_RULE.FACE_VALUE']
            //{"COUPON_RULE.TYPE":"1","COUPON_RULE.FACE_VALUE":"4500","COUPON_RULE.STATE":"1","COUPON_RULE.MAX_PRICE_LIMIT":"0","COUPON_RULE.ADD_DATIME":"2016-10-20 02:28:42.0","COUPON_RULE.IS_DEL":"-1","COUPON_RULE.NAME":"会员专享","COUPON_RULE.EACH_MEMBER_LIMIT":"4","COUPON_RULE.CONDITION_PRICE":"54000","COUPON_RULE.INTRO":"会员专享","COUPON_RULE.ID":"1025"}
            console.log('对象为：' + item['COUPON.TARGET_OBJ_ID']);//['COUPON_RULE.FACE_VALUE']
        })
        $scope.ruleList = response.extraData.ruleList;
        $scope.ruleMap = response.extraData.ruleMap;


    };

    /*$scope.parseJson=function (data) {
     data=JSON.parse(data);

     return data;
     }*/
    $scope.weekData = [
        {id: '1', name: '星期一'},
        {id: '2', name: '星期二'},
        {id: '3', name: '星期三'},
        {id: '4', name: '星期四'},
        {id: '5', name: '星期五'},
        {id: '6', name: '星期六'},
        {id: '7', name: '星期日'},
    ];
    /* $scope.parseArray=function (data) {
     var array=data.split(',');

     return array;
     }*/
    var targetObjArray = new Array();
    //方法名可以随便写 参数必须为data
    $scope.classSwitch = function (data) {
        targetObjArray=new Array();
        console.log('class switch');
        console.log(data);
        data.forEach(function (ele) {
            console.log(ele['SHOP_PRODUCT_CLASS.CLASS_ID'])
            targetObjArray.push(ele['SHOP_PRODUCT_CLASS.CLASS_ID']);
        });


    }

    $scope.tagSwitch = function (data) {
        targetObjArray=new Array();
        console.log('tag switch');
        console.log(data);
        data.forEach(function (ele) {
            console.log(ele['SHOP_TAG.TAG_ID'])
            targetObjArray.push(ele['SHOP_TAG.TAG_ID']);
        });


    }

    $scope.prdSwitch = function (data) {
        targetObjArray=new Array();
        console.log('prd switch');
        console.log(data);
        data.forEach(function (ele) {
            console.log(ele['SHOP_PRODUCT_SKU.PRD_ID'])
            targetObjArray.push(ele['SHOP_PRODUCT_SKU.PRD_ID']);
        });


    }

    $scope.detailClick = function (item) {
        $scope.detail = item;
    }

    $scope.addCoupon = function () {

        $scope.add['COUPON.TARGET_OBJ_ID'] = targetObjArray;   //数据库中会以逗号隔开
        couponFactory.addCouponInfo($scope.add).get({}, function (response) {

            if (response.code == 400) {
                modalFactory.showShortAlert(response.msg);

            } else if (response.extraData.state == 'true') {
                modalFactory.showShortAlert('新增成功');
                $scope.add = {};

                $("#addCoupon").modal('hide');
                $scope.$broadcast('pageBar.reload');
            }
        });
    };

    $scope.modCouponClick = function (item) {

        $scope.mod = clone(item);
        console.log('----' + $scope.mod);


    };

    $scope.modCoupon = function () {


        $scope.mod['COUPON.TARGET_OBJ_ID'] = targetObjArray;   //数据库中会以逗号隔开
        couponFactory.modifyCoupon($scope.mod).get({}, function (response) {
            if (response.code == 400) {
                modalFactory.showShortAlert(response.msg);
            } else if (response.extraData.state == 'true') {
                $("#couponMod").modal('hide');
                modalFactory.showShortAlert("修改成功");
                $("#modCoupon").modal('hide');
                $scope.$broadcast('pageBar.reload');

            }
        });
    };

    $scope.stopCoupon = function (item) {

        if (item['COUPON_RULE.STATE'] == 1) {
            modalFactory.showAlert("确认停用吗?", function () {
                item['COUPON_RULE.STATE'] = -1;
                couponFactory.stopSouponById(item).get({}, function (res) {
                    if (res.extraData.state = 'true') {
                        modalFactory.showShortAlert("停用成功");
                        $scope.$broadcast('pageBar.reload');
                    }
                });
            });
        } else {
            item['COUPON_RULE.STATE'] = 1;
            couponFactory.stopSouponById(item).get({}, function (res) {
                if (res.extraData.state = 'true') {
                    modalFactory.showShortAlert("启用成功");
                    $scope.$broadcast('pageBar.reload');
                }
            });
        }


    }; //delCoupon
    $scope.delCoupon = function (item) {

        modalFactory.showAlert("确认删除该优惠券吗?", function () {
            couponFactory.deleteCoupon(item).get({}, function (res) {
                if (res.extraData.state = 'true') {
                    modalFactory.showShortAlert("删除成功");
                    $scope.$broadcast('pageBar.reload');
                }
            });
        });

    }

});