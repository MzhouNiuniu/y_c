AndSellMainModule.controller('MemberAddressController', function ($scope, $state, $stateParams, memberFactory, memberSourceFactory, modalFactory, $q) {

    //设置页面Title
    modalFactory.setTitle('客户详情');

    modalFactory.setBottom(true);

    $scope.memberId = $stateParams.id;
    console.log("这是客户的id：" + $scope.memberId);

    $scope.initLoad = function () {

    };

    $scope.initLoad();

});

