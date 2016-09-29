<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="table-operbar row">
    <div class="table-toolbar">

        <div class="col-md-12 text-right" >
            <a   show-modal id="#addMember" class="btn sbold green" >
                <i class="fa fa-plus"></i> 新增客户来源 </a>
        </div>
    </div>
    <div class="col-md-12">
        <div class="table-scrollable">
            <table class="table table-bordered table-hover table-striped">
                <thead>
                <tr>

                    <th class="col-md-2"> 客户来源编码</th>
                    <th class="col-md-2"> 客户来源名称</th>
                    <th class="col-md-3"> 客户来源介绍</th>

                    <th class="col-md-1 text-center"> 操作</th>
                </tr>
                </thead>
                <tbody ng-cloak>
                <tr ng-repeat="item in MemberSourceList">

                    <td ng-bind="item['MEMBER_CODE_SOURCE.CODE']" ></td>
                    <td ng-bind="item['MEMBER_CODE_SOURCE.NAME']"></td>
                    <td ng-bind="item['MEMBER_CODE_SOURCE.INTRO']"></td>
                    <td class="text-center" >
                            <div ng-show="item['MEMBER_CODE_SOURCE.IS_SYS']==-1">
                        <a type="button" show-modal id="#modifyMember"
                           ng-click="modifyMemberSourceClick(item)">修改</a>
                        <a type="button" ng-click="deleteMember(item['MEMBER_CODE_SOURCE.CODE'])" >
                            删除
                        </a>
                            </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!--BEGIN 新增客户来源-->
<div class="modal fade text-left" id="addMember" tabindex="-1" aria-hidden="true"
     style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true"></button>
                <h4 class="modal-title">添加客户来源</h4>
            </div>
            <div class="modal-body form-body text-right">

                <form class="form-horizontal">


                    <div class="form-group row">
                        <label class="col-md-3 control-label">
                            <span class="required"> * </span> 客户来源编码
                        </label>

                        <div class="col-md-8">
                            <input type="text" class="form-control"
                                   ng-model="add['MEMBER_CODE_SOURCE.CODE']"
                                   ng-init="add['MEMBER_CODE_SOURCE.CODE']=''"
                                   placeholder=""></div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-3 control-label">
                            <span class="required"> * </span> 客户来源名称
                        </label>

                        <div class="col-md-8">
                            <input type="text" class="form-control"
                                   ng-model="add['MEMBER_CODE_SOURCE.NAME']"
                                   ng-init="add['MEMBER_CODE_SOURCE.NAME']=''"
                                   placeholder=""></div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-3 control-label">
                           客户来源介绍
                        </label>

                        <div class="col-md-8">
                            <input type="text" class="form-control"
                                   ng-model="add['MEMBER_CODE_SOURCE.INTRO']"
                                   ng-init="add['MEMBER_CODE_SOURCE.INTRO']=''"
                                   placeholder=""></div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" ng-click="addMemberClass()" class="btn green">确定
                </button>
                <button type="button" class="btn green  btn-outline" data-dismiss="modal">取消
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!--END 新增客户来源-->

<!--BEGIN 修改客户来源-->
<div class="modal fade text-left" id="modifyMember" tabindex="-1" aria-hidden="true"
     style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true"></button>
                <h4 class="modal-title">修改客户来源</h4>
            </div>
            <div class="modal-body form-body text-right">

                <form class="form-horizontal">


                    <div class="form-group row">
                        <label class="col-md-3 control-label">
                             客户来源编码
                        </label>

                        <div class="col-md-8">
                            <input type="text" class="form-control"
                                   ng-model="modify['MEMBER_CODE_SOURCE.CODE']"
                                   disabled></div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-3 control-label">
                            <span class="required"> * </span> 客户来源名称
                        </label>

                        <div class="col-md-8">
                            <input type="text" class="form-control"
                                   ng-model="modify['MEMBER_CODE_SOURCE.NAME']"
                                   placeholder=""></div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-3 control-label">
                            客户来源介绍
                        </label>

                        <div class="col-md-8">
                            <input type="text" class="form-control"
                                   ng-model="modify['MEMBER_CODE_SOURCE.INTRO']"
                                   placeholder=""></div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" ng-click="modifyMemberSource()" class="btn green">确定
                </button>
                <button type="button" class="btn green  btn-outline" data-dismiss="modal">取消
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!--END 修改客户来源-->

