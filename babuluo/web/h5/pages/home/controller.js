angular.module('AndSell.H5.Main').controller('pages_home_Controller', function (productFactory, $interval, $scope, $state, weUI, modalFactory, shopFactory) {

    modalFactory.setTitle('主页');
    $scope.FILE_SERVER_DOMAIN = FILE_SERVER_DOMAIN;
    modalFactory.setBottom(true);
    $scope.homeList = new Array();
    $scope.limitList = new Array();
    $scope.groupList = new Array();
    $scope.activityList = new Array();
    $scope.recommendList = new Array();
    $scope.recommThreeList = new Array();
    $scope.recommNineList = new Array();
    // $scope.bakingList = new Array();
    // $scope.freezingList = new Array();
    //$scope.BannerListMap=new Map();
    $scope.BannerList=new Array();
    //商品搜索
    $scope.searchPrd = function () {
        $state.go('pages/product/list', {keyword: $scope.prdKeyword});
    }

    $scope.initData = function () {
        var id = getCookie('currentShop');
        if (id == undefined || id.trim() == '') {
            weUI.toast.info('请先选择门店');
            $state.go('pages/shop');
        } else {
            shopFactory.getShopById({'SHOP.SHOP_ID':id}, function (response) {
                $scope.shopInfo = response.data[0];
                if ($scope.shopInfo != undefined) {
                    setCookie('currentShopInfo', JSON.stringify($scope.shopInfo));
                }
            });
        }

        var params = {}
        params['SHOP_PRODUCT.TAG_ID'] = '1035,1036,1037';
        productFactory.getProductByTag(params, function (response) {
            console.log(response);
            if (response.code == 0) {
                $scope.tagProductList ={};
                response.data.forEach(function (ele) {
                    console.log(ele['SHOP_PRODUCT.TAG_ID']);
                    if (ele['SHOP_PRODUCT.TAG_ID'].indexOf('1035') >= 0) {
                        if ($scope.tagProductList['1035'] == undefined) {
                            var ne =  new Array;
                            ne.push(ele);
                            $scope.tagProductList['1035']=ne;
                        } else {
                            var ne = $scope.tagProductList['1035'];
                            ne.push(ele);
                            $scope.tagProductList['1035']=ne;
                        }
                    }
                    if (ele['SHOP_PRODUCT.TAG_ID'].indexOf('1036') >= 0) {
                        if ($scope.tagProductList['1036'] == undefined) {
                            var ne =  new Array;
                            ne.push(ele);
                            $scope.tagProductList['1036']=ne;
                        } else {
                            var ne = $scope.tagProductList['1036'];
                            ne.push(ele);
                            $scope.tagProductList['1036']=ne;
                        }
                    }
                    if (ele['SHOP_PRODUCT.TAG_ID'].indexOf('1037') >= 0) {
                        if ($scope.tagProductList['1037'] == undefined) {
                            var ne =  new Array;
                            ne.push(ele);
                            $scope.tagProductList['1037']=ne;
                        } else {
                            var ne = $scope.tagProductList['1037'];
                            ne.push(ele);
                            $scope.tagProductList['1037']=ne;
                        }
                    }
                });
            }
        });

        // 设置轮播图图片间隔
        $scope.myInterval = 4000;


        shopFactory.getBannerList({}, function (response) {    //横幅列表
            console.log(response.data);
            var dataList = response.data;

            dataList.forEach(function (ele) {
                if (ele['BANNER.POSITION_ID'] =="1012") {   //首页
                    $scope.homeList.push(ele);
                }
                else if (ele['BANNER.POSITION_ID'] == "1013") {        //限时抢购
                    $scope.limitList.push(ele);
                    //获取到限时抢购的开始时间和结束时间
                    var startTime = new Date(ele['BANNER.BEGIN_DATETIME'].split('.')[0]).getTime();    //开始时间
                    var endTime = new Date(ele['BANNER.END_DATETIME'].split('.')[0]).getTime();      //结束时间
                    var currentTime = new Date().getTime();      //当前时间 毫秒

                    $scope.timer = $interval(function () {
                        if (currentTime > startTime && currentTime < endTime) {   //如果当前时间在活动时间内
                            currentTime += 1000;
                            fomtime(endTime - currentTime, ele);
                        } else {
                            $interval.cancel($scope.timer);
                        }
                    }, 1000);   //间隔1秒定时执行

                }
                else if (ele['BANNER.POSITION_ID'] == "1014") {    //  团购
                    $scope.groupList.push(ele);
                }
                else if (ele['BANNER.POSITION_ID'] == "1015") {      //活动专区
                    $scope.activityList.push(ele);
                }
                else if (ele['BANNER.POSITION_ID'] == "1016") {     //今日推荐
                    $scope.recommendList.push(ele);
                }
               /* if (ele['BANNER.POSITION_ID'] == 1017) {     //今日推荐三连排
                    $scope.recommThreeList.push(ele);
                }*/
                else if (ele['BANNER.POSITION_ID'] == "1018") {     //今日推荐九宫格
                    $scope.recommNineList.push(ele);
                }
                else{
                    console.log(ele['BANNER.POSITION_ID']);
                    var flag=false;
                    for(i=0;i<$scope.BannerList.length;i++){
                        if ($scope.BannerList[i].id==ele['BANNER.POSITION_ID']){
                            console.log(1);
                            flag=true;
                            var arrayList=$scope.BannerList[i].content;
                            arrayList.push(ele);
                        }
                    }
                    if(flag==false) {
                        console.log(0);
                        var array = new Array();
                        array.push(ele);

                        listItem = new Object();
                        listItem.id = ele['BANNER.POSITION_ID'];
                        listItem.name = ele['BANNER.NAME'];
                        listItem.content = array;

                        $scope.BannerList.push(listItem);
                    }

                }

                if (ele['BANNER.END_DATETIME']==null&&ele['BANNER.BEGIN_DATETIME']==null){
                    ele['is_show']=true;
                }else if(ele['BANNER.BEGIN_DATETIME']!=null){
                    var startTime = new Date(ele['BANNER.BEGIN_DATETIME'].split('.')[0]).getTime();    //开始时间
                    var currentTime = new Date().getTime();      //当前时间 毫秒
                    if(currentTime>startTime){
                        if(ele['BANNER.BEGIN_DATETIME']==null){
                            ele['is_show']=true;
                        }
                        else{
                            var endTime = new Date(ele['BANNER.END_DATETIME'].split('.')[0]).getTime();      //结束时间
                            if(currentTime<endTime){
                                ele['is_show']=true;
                            }
                        }
                    }
                }



            })



        });
        console.log(789);
      //console.log( $scope.BannerList);
    }

    //以毫秒为单位
    var fomtime = function (date, item) {

        // item.day=parseInt(date/(24*60*60*1000));
        // console.log(item.day);
        item.hour = parseInt(date / (60 * 60 * 1000));
        item.minute = parseInt((date - item.hour * 60 * 60 * 1000) / (60 * 1000));
        item.second = parseInt((date - item.hour * 60 * 60 * 1000 - item.minute * 60 * 1000)
            / 1000);

    }

    $scope.$on('destroy', function () {
        $interval.cancel($scope.timer);
    })

});

