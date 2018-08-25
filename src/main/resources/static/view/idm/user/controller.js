(function(angular) {
	'use strict';
	var module = angular.module('mdr.user', [ 'ngRoute', 'yum.services.http',
			'ui.bootstrap', 'ngFileUpload' ]);
	module.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/user', {
			templateUrl : 'view/idm/user/user.html',
			controller : 'UserController'
		});
	} ]);
	module
			.controller(
					'UserController',
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
									loading : false,
									pendingFilterText : "",
									sorts : [
											{
												id : 'id_ Asc',
												name : $translate
														.instant('IDM.USER-MGMT.FILTERS.SORT-ID-A')
											},
											{
												id : 'id_ Desc',
												name : $translate
														.instant('IDM.USER-MGMT.FILTERS.SORT-ID-Z')
											},
											{
												id : 'email_ Asc',
												name : $translate
														.instant('IDM.USER-MGMT.FILTERS.SORT-EMAIL-A')
											},
											{
												id : 'email_ Desc',
												name : $translate
														.instant('IDM.USER-MGMT.FILTERS.SORT-EMAIL-Z')
											} ],
									waiting : false,
									delayed : false,
									selectedUsers : {},
									selectedUserCount : 0,
									start : 0

								}

								$scope.page = {};
								$scope.page.currentPage = 1;
								$scope.page.rows = AppConfig.pageSize;
								$scope.page.totalCount = 0;
								$scope.page.totalPages = 0;

								$scope.go = function(page) {
									if (page >= 1
											&& page <= $scope.page.totalPages)
										$scope.page.currentPage = page;
									$scope.get_admins();
								};

								$scope.activateSort = function(sort) {
									$scope.model.activeSort = sort;
									$scope.model.start = 0;
									$scope.loadUsers();
								};

								$scope.model.activeSort = $scope.model.sorts[0];

								$scope.clearSelectedUsers = function() {
									$scope.model.selectedUsers = {};
									$scope.model.selectedUserCount = 0;
								};

								$scope.toggleUserSelection = function(user) {
									if ($scope.model.selectedUsers[user.id_]) {
										delete $scope.model.selectedUsers[user.id_];
										$scope.model.selectedUserCount -= 1;
									} else {
										$scope.model.selectedUsers[user.id_] = true;
										$scope.model.selectedUserCount += 1;
									}
									console.log($scope.model.selectedUsers)

								};

								$scope.getSelectedUsers = function() {
									var selected = [];
									for (var i = 0; i < $scope.model.users.length - 1; i++) {
										var user = $scope.model.users[i];
										if (user) {
											for ( var prop in $scope.model.selectedUsers) {
												if (user.id_ == prop) {
													selected.push(user);
													break;
												}
											}
										}
									}

									return selected;
								};

								$scope.loadUsers = function(msg) {
									$scope.clearSelectedUsers();
									$scope.model.loading = true;
									g_showLoading();

									$http(
											{
												method : 'POST',
												url : AppConfig.PORTALIPHOST
														+ '/idm/get_users',
												params : {
													sort : $scope.model.activeSort.id,
													start : $scope.model.start,
													pageSize : AppConfig.pageSize,
													pageIndex : $scope.page.currentPage,
													filter : $scope.model.pendingFilterText
												}
											})
											.success(
													function(result, status,
															headers, config) {
														layer.closeAll();
														$scope.model.loading = false
														if (result.code == 0) {
															if (result.data.rows[0]) {
																$scope.model.users = result.data.rows;
																
																if(msg){
																	layer.msg(msg);	
																}

															} else {
																$scope.model.users = [];
																layer
																		.msg("没有记录");
															}
															$scope.page.totalCount = result.data.total;
															$scope.page.totalPages = Math
																	.ceil($scope.page.totalCount
																			/ $scope.page.rows);
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

								}

								$scope.refreshDelayed = function() {
									// If already waiting, another wait-cycle
									// will be done
									// after the current wait is over
									if ($scope.model.waiting) {
										$scope.model.delayed = true;
									} else {
										$scope.scheduleDelayedRefresh();
									}
								};

								$scope.scheduleDelayedRefresh = function() {
									$scope.model.waiting = true;
									$timeout(function() {
										$scope.model.waiting = false;
										if ($scope.model.delayed) {
											$scope.model.delayed = false;
											// Delay again
											$scope.scheduleDelayedRefresh();
										} else {
											// Actually do the refresh-call,
											// after resetting start
											$scope.model.start = 0;
											$scope.loadUsers();
										}
									}, 100);
								};

								$scope.addUser = function() {

									$scope.model.user = undefined;
									$scope.model.mode = 'create';
									_internalCreateModal(
											{
												scope : $scope,
												template : 'view/popup/idm-user-create.html?version='
														+ new Date().getTime(),
												show : true
											}, $modal, $scope);
								};

								$scope.editUserAccountType = function() {

									$scope.model.mode = 'type';

									_internalCreateModal(
											{
												scope : $scope,
												template : 'view/popup/idm-user-type-edit.html',
												show : true
											}, $modal, $scope);

								};

								$scope.editUserDetails = function() {

									$scope.model.user = undefined;
									$scope.model.mode = 'edit';
									var selectedUsers = $scope
											.getSelectedUsers();
									if (selectedUsers
											&& selectedUsers.length == 1) {
										$scope.model.user = selectedUsers[0];
										console.log($scope.model.user)
									}

									_internalCreateModal(
											{
												scope : $scope,
												template : 'view/popup/idm-user-create.html?version='
														+ new Date().getTime(),
												show : true
											}, $modal, $scope);
								};

								$scope.editUserPassword = function() {

									$scope.model.mode = 'password';

									_internalCreateModal(
											{
												scope : $scope,
												template : 'view/popup/idm-user-password-change.html',
												show : true
											}, $modal, $scope);

								};

								$scope.deleteUsers = function() {
									$scope.model.loading = true;
									$scope
											.getSelectedUsers()
											.forEach(
													function(selectedUser) {
														g_showLoading();
														$http(
																{
																	method : 'POST',
																	url : AppConfig.PORTALIPHOST
																			+ '/idm/delete_user',
																	params : {
																		id_ : selectedUser.id_,
																	}
																})
																.success(
																		function(result, status,
																				headers, config) {
																			layer.closeAll();
																			$scope.model.loading = false
																			if (result.code == 0) {
																				//layer.msg("删除成功");
																				$scope.loadUsers("删除成功");
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
														
													
													});
								};

								$scope.loadUsers();

							} ]);
	module.controller('IdmUserBulkUpdatePopupController', [
			'$rootScope',
			'$scope',
			'$http','AppConfig',
			function($rootScope, $scope, $http,AppConfig) {

//				if (!$scope.hasAdminCapability()) {
//					$scope.backToLanding();
//				}

				if ($scope.model.mode == 'password') {
					$scope.model.updateUsers = {
						password : ''
					};
				}

				$scope.updateUsers = function() {
					$scope.model.loading = true;
					var users = $scope.getSelectedUsers();
					var userIds = [];
					for (var i = 0; i < users.length; i++) {
						var user = users[i];
						if (user && user.id_) {
							userIds.push(user.id_);
						}
					}

//					var data = {
//						users : userIds
//					};
//
//					if ($scope.model.mode == 'password') {
//						data.password = $scope.model.updateUsers.password;
//					}
					g_showLoading();
					$http(
							{
								method : 'POST',
								url : AppConfig.PORTALIPHOST
										+ '/idm/update_users_password',
								params : {
									users : userIds,
									pwd_ : $scope.model.updateUsers.password
								}
							})
							.success(
									function(result, status,
											headers, config) {
										layer.closeAll();
										$scope.model.loading = false
										if (result.code == 0) {
											$scope.$hide();
											$scope.loadUsers("更新成功");
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

				$scope.setStatus = function(newStatus) {
					$scope.model.updateUsers.status = newStatus;
				};

				$scope.setType = function(newType) {
					$scope.model.updateUsers.type = newType;
				};

				$scope.cancel = function() {
					if (!$scope.model.loading) {
						$scope.$hide();
					}
				};

			} ]);

	module.controller('IdmCreateUserPopupController', [
			'$scope',
			'$http',
			'AppConfig',
			function($scope, $http, AppConfig) {

				if ($scope.model.user === null
						|| $scope.model.user === undefined) {
					$scope.model.user = {};
				}

				$scope.createNewUser = function() {

					if (!$scope.model.user.id_) {
						return;
					}
					g_showLoading();
					var model = $scope.model;
					model.loading = true;
					$http({
						method : 'POST',
						url : AppConfig.PORTALIPHOST + '/idm/add_user',
						params : {
							id : model.user.id_,
							email : model.user.email_,
							firstName : model.user.first_,
							lastName : model.user.last_,
							password : model.user.pwd_
						}
					}).success(function(result, status, headers, config) {
						layer.closeAll();
						if (result.code == 0) {
							//layer.msg("添加成功");
							$scope.$hide();

							$scope.loadUsers("添加成功");
						} else {
							layer.msg(result.msg);
						}

					}).error(function(data, status, headers, config) {

						layer.closeAll();
						layer.msg(status);

					});

				};

				$scope.editUserDetails = function() {

					if (!$scope.model.user.id_) {
						return;
					}

					g_showLoading();
					var model = $scope.model;
					model.loading = true;
					$http({
						method : 'POST',
						url : AppConfig.PORTALIPHOST + '/idm/update_user',
						params : {
							id_ : model.user.id_,
							email_ : model.user.email_,
							first_ : model.user.first_,
							last_ : model.user.last_,
							pwd_ : model.user.pwd_
						}
					}).success(function(result, status, headers, config) {
						layer.closeAll();
						if (result.code == 0) {
							//layer.msg("更新成功");
							$scope.$hide();

							$scope.loadUsers("更新成功");
						} else {
							layer.msg(result.msg);
						}

					}).error(function(data, status, headers, config) {

						layer.closeAll();
						layer.msg(status);

					});

				};

				$scope.cancel = function() {
					if (!$scope.model.loading) {
						$scope.$hide();
					}
				};

			} ]);

})(angular);
