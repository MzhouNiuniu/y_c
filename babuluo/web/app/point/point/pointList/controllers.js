angular.module('AndSell.Main').controller('point_point_pointList_Controller', function ($scope, $stateParams, pointFactory, modalFactory) {

    modalFactory.setTitle('积分管理');
    modalFactory.setBottom(false);

    $scope.bindData = function (response) {
        console.log(response);
        $scope.pointList = response.data;
        $scope.searchList = response.data;
        $scope.userDetailMap = response.extraData.userDetailMap;
    };

    $scope.queryById = function (memberId) {
        $scope.memberDetail = $scope.userDetailMap[memberId];

    }

    //根据登录ID查询财务信息
    $scope.queryPointByLoginId = function (loginId) {
        $scope.pointList = [];
        if (loginId == null || loginId == '') {
            $scope.pointList = $scope.searchList;
        }
        else {
            $scope.roundList = $scope.searchList;
            for (var i = 0; i < $scope.roundList.length; i++) {
                if ($scope.roundList[i]['MEMBER_POINT_LIST.LOGIN_ID'] == loginId) {
                    $scope.pointList.push($scope.roundList[i]);
                }
            }
        }
        return $scope.pointList;
    }

    //动态计算账户余额
    $scope.getDynamicBala = function () {
        if ($scope.changeType == 1) {
            $scope.afterModify = parseInt($scope.memberDetail['MEMBER.POINT']) + parseInt($scope.modifyvalue);
        }
        else if ($scope.changeType == 0) {
            $scope.afterModify = parseInt($scope.memberDetail['MEMBER.POINT']) - parseInt($scope.modifyvalue);
        }
        else {
            modalFactory.showShortAlert("请输入变更类型");
        }
    }

    //保存时更新账户余额
    $scope.saveAccount = function () {

        if (isEmptyObject($scope.memberDetail)) {
            modalFactory.showAlert("未选择客户");
            return;
        }

        if ($scope.afterModify < 0) {
            modalFactory.showAlert("减少积分不能超过用户剩余积分");
            return;
        }

        //修改积分详情表--FINANCE_LIST（添加操作记录）
        $scope.ModifyBalanceInfo = {};
        $scope.ModifyBalanceInfo['MEMBER_POINT_LIST.POINT'] = $scope.memberDetail['MEMBER.POINT'];
        $scope.ModifyBalanceInfo['MEMBER_POINT_LIST.USER_ID'] = $scope.memberDetail['MEMBER.USER_ID'];
        $scope.ModifyBalanceInfo['MEMBER_POINT_LIST.SERVICE_ID'] = $scope.memberDetail['MEMBER.SERVICE_ID'];
        $scope.ModifyBalanceInfo['MEMBER_POINT_LIST.EVENT'] = "后台更改";
        $scope.ModifyBalanceInfo['MEMBER_POINT_LIST.EVENT_INTRO'] = $scope.introduction;
        $scope.ModifyBalanceInfo['MEMBER_POINT_LIST.CHANGE_POINT'] = $scope.modifyvalue;
        $scope.ModifyBalanceInfo['MEMBER_POINT_LIST.SERVICE_ID'] = $scope.memberDetail['MEMBER.SERVICE_ID'];
        if ($scope.changeType == 1) {
            $scope.ModifyBalanceInfo['MEMBER_POINT_LIST.CHANGE_TYPE'] = 'increase';
        }
        else {
            $scope.ModifyBalanceInfo['MEMBER_POINT_LIST.CHANGE_TYPE'] = 'decrease';
        }
        pointFactory.addPointList($scope.ModifyBalanceInfo).get({}, function (response) {
            if (response.code != undefined && (response.code == 4000 || response.code == 400)) {
                modalFactory.showShortAlert(response.msg);
            } else if (response.extraData.state == 'true') {
                modalFactory.showShortAlert("积分调整成功");
                $("#point").modal('hide');
                $scope.$broadcast('pageBar.reload');
            }
        });
    }

    $scope.empty = function () {
        $scope.modifyvalue = null;
        $scope.afterModify = null;
        $scope.introduction = null;
        $scope.memberDetail = null;
        $scope.memberId = null;
        $scope.changeType = null;
    }

    $scope.delete = function () {
        $scope.modifyvalue = null;
        $scope.afterModify = null;
        $scope.introduction = null;
    }
});


