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
							'$translate','$timeout',
							function($scope, AppConfig, $window, HttpService,
									$http, Upload, $location, $modal,
									$translate,$timeout) {

								$scope.model={
									pendingFilterText:"",
						            sorts: [
						                    {id: 'id_ Asc', name: $translate.instant('IDM.USER-MGMT.FILTERS.SORT-ID-A')},
						                    {id: 'id_ Desc', name: $translate.instant('IDM.USER-MGMT.FILTERS.SORT-ID-Z')},
						                    {id: 'email_ Asc', name: $translate.instant('IDM.USER-MGMT.FILTERS.SORT-EMAIL-A')},
						                    {id: 'email_ Desc', name: $translate.instant('IDM.USER-MGMT.FILTERS.SORT-EMAIL-Z')}
						                ],
						            waiting: false,
						            delayed: false,
						            selectedUsers: {},
						            selectedUserCount: 0,
						            start: 0
										
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
						            if($scope.model.selectedUsers[user.id_]) {
						                delete $scope.model.selectedUsers[user.id_];
						                $scope.model.selectedUserCount -= 1;
						            }  else {
						                $scope.model.selectedUsers[user.id_] = true;
						                $scope.model.selectedUserCount +=1;
						            }

						        };
						        
						        $scope.getSelectedUsers = function() {
						            var selected = [];
						            for(var i = 0; i<$scope.model.users.size; i++) {
						                var user = $scope.model.users.data[i];
						                if(user) {
						                    for(var prop in $scope.model.selectedUsers) {
						                        if(user.id_ == prop) {
						                            selected.push(user);
						                            break;
						                        }
						                    }
						                }
						            }

						            return selected;
						        };
						        
						        $scope.loadUsers = function() {
						        	$scope.clearSelectedUsers();
						        	g_showLoading();
						        	
						        	 $http(
						   					{
						   						method : 'POST',
						   						url : AppConfig.PORTALIPHOST+'/idm/get_users',
						   						params : {
						   			                sort: $scope.model.activeSort.id,
						   			                start: $scope.model.start,
					      							pageSize:AppConfig.pageSize,
					      							pageIndex:$scope.page.currentPage,
					      							filter:$scope.model.pendingFilterText
						   						}
						   					})
						   					.success(
						   					function(result, status,headers, config) {
						   						layer.closeAll();
						   						if (result.code == 0) {
						  							if(result.data.rows[0]){
						 	    						$scope.model.users = result.data.rows;;
						 	    						

						  							}else{
						  								$scope.model.users=[];
						  								layer.msg("没有记录");
						  							}
													$scope.page.totalCount = result.data.total;
													$scope.page.totalPages = Math
														.ceil($scope.page.totalCount
														/ $scope.page.rows);
						   						} else {
						   							layer.msg(result.msg);
						   						}
						   						
					

						   					}).error(
						   					function(data, status,
						   							 headers, config) {

						   						layer.closeAll();
						   						layer.msg(status);

						   					});
						         	
						         	
						         }
						        	
						        	
						        $scope.refreshDelayed = function() {
						            // If already waiting, another wait-cycle will be done
						            // after the current wait is over
						            if($scope.model.waiting) {
						                $scope.model.delayed = true;
						            } else {
						                $scope.scheduleDelayedRefresh();
						            }
						        };
						        
						        $scope.scheduleDelayedRefresh = function() {
						            $scope.model.waiting = true;
						            $timeout(function() {
						                $scope.model.waiting = false;
						                if( $scope.model.delayed) {
						                    $scope.model.delayed = false;
						                    // Delay again
						                    $scope.scheduleDelayedRefresh();
						                } else {
						                    // Actually do the refresh-call, after resetting start
						                    $scope.model.start = 0;
						                    $scope.loadUsers();
						                }
						            }, 100);
						        };

								$scope.addUser = function() {
									$scope.model.errorMessage = undefined;
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

						            _internalCreateModal({
						                scope: $scope,
						                template: 'views/popup/idm-user-type-edit.html',
						                show: true
						            }, $modal, $scope);

						        };

						        $scope.editUserDetails = function() {

						            $scope.model.user = undefined;
						            $scope.model.mode = 'edit';
						            var selectedUsers = $scope.getSelectedUsers();
						            if (selectedUsers && selectedUsers.length == 1) {
						                $scope.model.user = selectedUsers[0];
						            }

						            $scope.model.errorMessage = undefined;
						            _internalCreateModal({
						                scope: $scope,
						                template: 'views/popup/idm-user-create.html?version=' + new Date().getTime(),
						                show: true
						            }, $modal, $scope);
						        };

						        $scope.editUserPassword = function() {

						            $scope.model.mode = 'password';

						            _internalCreateModal({
						                scope: $scope,
						                template: 'views/popup/idm-user-password-change.html',
						                show: true
						            }, $modal, $scope);

						        };

						        $scope.deleteUsers = function() {
						            $scope.model.loading = true;
						            $scope.getSelectedUsers().forEach(function(selectedUser) {
						                $http({method: 'DELETE', url: ACTIVITI.CONFIG.contextRoot + '/app/rest/admin/users/' + selectedUser.id}).
						                    success(function (data, status, headers, config) {

						                        $rootScope.addAlert('User deleted', 'info');
						                        $scope.loadUsers();

						                        $scope.model.loading = false;
						                    }).
						                    error(function (data, status, headers, config) {
						                        $scope.model.loading = false;
						                        if (data && data.message) {
						                            $rootScope.addAlert(data.message, 'error');
						                        } else {
						                            $rootScope.addAlert('Error while deleting user', 'error');
						                        }
						                    });
						            });
						        };
								
								
								
								
								 $scope.loadUsers();

							} ]);
	module
			.controller(
					'IdmCreateUserPopupController',
					[
							'$rootScope',
							'$scope',
							'$http',
							function($rootScope, $scope, $http) {
								
								
								
								
								
								
							} ]);

})(angular);
