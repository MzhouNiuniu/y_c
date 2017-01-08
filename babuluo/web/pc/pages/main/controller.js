AndSellPCMainModule.controller('PC.MainController', function ($scope, $state, modalFactory, productFactory) {

    //逻辑
    $scope.$on('title', function (event, data) {
        $scope.title = data;
    });

    //nav-Bottom 初始化
    $scope.$on('header-bar', function (event, data) {
        $scope.showMenuBar = data.OnOffState;
        $scope.showControllerBar = data.OnOffState;
        $scope.showGoodsCategoriesBar = data.OnOffState;
    });

    //nav-Bottom 初始化
    $scope.$on('side-bar', function (event, data) {
        $scope.showSideBar = data.OnOffState;
    });



});
