(function(angular) {
	'use strict';
	var module = angular.module('mdr.group', [ 'ngRoute', 'yum.services.http',
			'ui.bootstrap', 'ngFileUpload' ]);
	module.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/group', {
			templateUrl : 'view/idm/group/group.html',
			controller : 'GroupController'
		});
	} ]);
	module
			.controller(
					'GroupController',
					[
							'$scope',
							'AppConfig',
							'$window',
							'HttpService',
							'$http',
							'Upload',
							'$location',
							'$modal',
							'$translate',
							'$timeout',
							function($scope, AppConfig, $window, HttpService,
									$http, Upload, $location, $modal,
									$translate, $timeout) {
								
						        $scope.model = {
						                loading: true,
						                expanded: {}
						            };
						        
						        
						        
						        var validUser = true;
						        
						        $scope.showCreateGroupPopup = function() {
						            $scope.model.editedGroup  = {};
						            $scope.model.mode = 'create';
						            _internalCreateModal({
						                scope: $scope,
						                template: 'view/popup/idm-group-create.html',
						                show: true
						            }, $modal, $scope);
						        };
						        
						        
						        $scope.showDeleteGroupModal = function() {
						            _internalCreateModal({
						                scope: $scope,
						                template: 'view/popup/idm-delete-group.html',
						                show: true
						            }, $modal, $scope);
						        };
						        
						        $scope.showRemoveMemberModal = function(user) {
						            $scope.model.userToRemove = user;
						            _internalCreateModal({
						                scope: $scope,
						                template: 'view/popup/idm-group-member-delete.html',
						                show: true
						            }, $modal, $scope);
						        };
						        
						        $scope.deleteGroupMember = function() {
						        	  g_showLoading();

										$http(
												{
													method : 'POST',
													url : AppConfig.PORTALIPHOST
															+ '/idm/delete_groupmember',
													params : {
														id_:$scope.model.selectedGroup.id_,
														user_id_:$scope.model.userToRemove.id_
													}
												})
												.success(
														function(result, status,
																headers, config) {
															layer.closeAll();
															$scope.model.loading = false
															if (result.code == 0) {
																delete $scope.model.userToRemove;
												                $scope.model.loading = false;
												                $scope.fetchGroups($scope.model.selectedGroup.id_);
																
															} else {
																layer
																		.msg(result.msg);
															}

														}).error(
														function(data, status,
																headers, config) {

															layer.closeAll();
															layer.msg(status);

														});
						        	
						        	
						        	
						        	

						        };
						        
						        $scope.deleteGroup = function() {
						            $scope.model.loading = true;
						            g_showLoading();

									$http(
											{
												method : 'POST',
												url : AppConfig.PORTALIPHOST
														+ '/idm/delete_group',
												params : {
													id_:$scope.model.selectedGroup.id_,
												}
											})
											.success(
													function(result, status,
															headers, config) {
														layer.closeAll();
														$scope.model.loading = false
														if (result.code == 0) {
															
											                $scope.model.loading = false;
											                clearSelection();
											                $scope.fetchGroups();
//															layer
//															.msg("删除成功")
															
														} else {
															layer
																	.msg(result.msg);
														}

													}).error(
													function(data, status,
															headers, config) {

														layer.closeAll();
														layer.msg(status);

													});
						            
//						            IdmService.deleteGroup($scope.model.selectedGroup.id).then(function() {
//						                $scope.model.loading = false;
//						                clearSelection();
//						                $scope.fetchGroups();
//						            });
						        };
						        
						        $scope.updateGroup = function() {
						            $scope.model.loadingGroup = true;
						            
						            g_showLoading();

									$http(
											{
												method : 'POST',
												url : AppConfig.PORTALIPHOST
														+ '/idm/update_group',
												params : {
													id_:$scope.model.editedGroup.id_,
													name_:$scope.model.editedGroup.name_,
												}
											})
											.success(
													function(result, status,
															headers, config) {
														layer.closeAll();
														$scope.model.loading = false
														if (result.code == 0) {
															
											                for (var i=0; i<$scope.model.groups.length; i++){
											                    if ($scope.model.groups[i].id_ === $scope.model.editedGroup.id_) {
											                        $scope.model.groups[i].name_ = $scope.model.editedGroup.name_;
											                    }
											                }
											                $scope.model.loadingGroup = false;
//															layer
//															.msg("更新成功")
															
														} else {
															layer
																	.msg(result.msg);
														}

													}).error(
													function(data, status,
															headers, config) {

														layer.closeAll();
														layer.msg(status);

													});
						            
						            


						        };
						        
						        $scope.createGroup = function() {
						            $scope.model.loading = true;
						            
						            g_showLoading();

									$http(
											{
												method : 'POST',
												url : AppConfig.PORTALIPHOST
														+ '/idm/create_group',
												params : {
													id_:$scope.model.editedGroup.id_,
													name_:$scope.model.editedGroup.name_,
												}
											})
											.success(
													function(result, status,
															headers, config) {
														layer.closeAll();
														$scope.model.loading = false
														if (result.code == 0) {
															$scope.fetchGroups($scope.model.editedGroup.id_);
															$scope.model.loading = false;
//															layer
//															.msg("创建成功")
														} else {
															layer
																	.msg(result.msg);
														}

													}).error(
													function(data, status,
															headers, config) {

														layer.closeAll();
														layer.msg(status);

													});

						        };
						        
						        // Clear any selected tenant
						        var clearSelection = function() {
						            delete $scope.model.groups;
						            delete $scope.model.selectedGroup;
						        };
						        
						        $scope.showEditGroupModal = function() {
						            $scope.model.editedGroup  = $scope.model.selectedGroup;
						            $scope.model.mode = 'edit';
						            _internalCreateModal({
						                scope: $scope,
						                template: 'view/popup/idm-group-create.html',
						                show: true
						            }, $modal, $scope);
						        };

						        
						        $scope.selectGroup = function(groupId) {
						            $scope.model.loadingGroup = true;
						            
						            g_showLoading();

									$http(
											{
												method : 'POST',
												url : AppConfig.PORTALIPHOST
														+ '/idm/get_group',
												params : {
													groupId:groupId,
//													pageSize : 50, //暂时不分页
//													pageIndex : 1,
												}
											})
											.success(
													function(result, status,
															headers, config) {
														layer.closeAll();
														$scope.model.loading = false
														if (result.code == 0) {
															$scope.model.selectedGroup = result.data;
															$scope.model.loadingGroup = false;
											                $scope.model.users = result.data.users;
											                $scope.model.moreUsers = $scope.model.selectedGroup.userCount > (($scope.model.userPage+1 ) * $scope.model.pageSize);
														} else {
															layer
																	.msg(result.msg);
														}

													}).error(
													function(data, status,
															headers, config) {

														layer.closeAll();
														layer.msg(status);

													});
						            
//						            IdmService.getGroup(groupId).then(function (data) {
//						                $scope.model.selectedGroup = data;
//
//						                $scope.model.userPage = 0;
//						                $scope.model.pageSize = 50;
//						                fetchUserPage();
//
//						                $scope.model.loadingGroup = false;
//						            });
						        };
						        
						        
						        // Load the groups
						        $scope.fetchGroups = function(groupIdToSelect) {
						            $scope.model.loading = true;
						            clearSelection();
						            
									g_showLoading();

									$http(
											{
												method : 'POST',
												url : AppConfig.PORTALIPHOST
														+ '/idm/get_groups',
												params : {

												}
											})
											.success(
													function(result, status,
															headers, config) {
														layer.closeAll();
														$scope.model.loading = false
														if (result.code == 0) {
											                $scope.model.groups = result.data;

											                var groupIndex;
											                $scope.model.expanded = {};

											                // Select a group
											                if (groupIdToSelect) {
											                    $scope.selectGroup(groupIdToSelect);
											                }

											                // By default, open first level of groups
											                for (groupIndex = 0; groupIndex < result.data.length; groupIndex++) {
											                    $scope.model.expanded[result.data[groupIndex].id_] = true;
											                }

											                $scope.model.loading = false;
														} else {
															layer
																	.msg(result.msg);
														}

													}).error(
													function(data, status,
															headers, config) {

														layer.closeAll();
														layer.msg(status);

													});

						            


						        };

						        if(validUser) {
						            $scope.fetchGroups();
						        }
								
								
							} ]);
	
	
	
	
	

})(angular);
