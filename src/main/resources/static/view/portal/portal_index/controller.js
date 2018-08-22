(function(angular) {
    'use strict';
    var module = angular.module('pm.portal_index', ['ngRoute', 'yum.services.http', 'ui.bootstrap', 'ngFileUpload']);
    module.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/portal_index', {
            templateUrl: 'view/portal/portal_index/portal_index.html',
            controller: 'PortalIndexController'
        });
    }]);
    module.controller('PortalIndexController', ['$scope', 'AppConfig', '$window', 'HttpService', '$http', 'Upload','$location',
        function($scope, AppConfig, $window, HttpService, $http, Upload,$location) {
    	$scope.sub_datas=[];
    	
        $scope.get_subsys=function(){
        	g_showLoading();
        	 $http(
  					{
  						method : 'POST',
  						url : AppConfig.PORTALIPHOST+'/login/get_subsys',
  						params : {
  							
  						}
  					})
  					.success(
  					function(result, status,headers, config) {
  						layer.closeAll();
  						if (result.code == 0) {
  							
  							$scope.sub_datas=result.data;
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
    	
        //$scope.get_subsys();
    	
    	
    	
    	 
        }
    ]);
})(angular);







