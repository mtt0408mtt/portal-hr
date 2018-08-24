  'use strict';

// Declare app level module which depends on views, and components
angular.module('pm', [
    'ngRoute',
    'yum.services.http',
    'ngCookies',
    'pm.portal_index',
    'pm.portal_opt',
    'pm.portal_root',
    'yum.directives.auto_focus'
  ])
   .constant('AppConfig', {
        pageSize: 10,
         PORTALIPHOST : 'http://localhost:8081',
         groupId:'',
         agencyId:'',
         departmentId:''
    })
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider.otherwise({ redirectTo: '/portal_index' });
  }])
  .controller('MainController', [
    '$scope',
    '$cookies',
    'AppConfig',
    'HttpService',
        '$location','$window','$http',
    function($scope, $cookies,AppConfig,HttpService,$location,$window,$http) {
    	console.log(AppConfig.PORTALIPHOST)
    	AppConfig.PORTALIPHOST='http://'+$location.host()+':8081'
    	console.log(AppConfig.PORTALIPHOST)
    	$scope.menu_flag=false
    	$scope.adminInPage={};
        if( !$cookies.get("token_hr")  ){
           $window.location.href="/login/to_login";
        }else{
//            var cookieArea=$cookies.get("area").split("aa")
//            AppConfig.groupId=cookieArea[0]
//            AppConfig.agencyId=cookieArea[1]
//            AppConfig.departmentId=cookieArea[2]
            var tokenId=$cookies.get("token_hr");

			g_showLoading();
            $http(
					{
						method : 'POST',
						url : AppConfig.PORTALIPHOST+'/login/get_token',
						params : {

						}
					})
					.success(
					function(result, status,
							 headers, config) {
						layer.closeAll();
						if (result.code == 0) {
							$scope.adminInPage=result.data;
						} else {
							layer.msg(result.msg);
							$window.location.href="/login/to_login";

						}
					}).error(
					function(data, status,
							 headers, config) {
						layer.closeAll();
						layer.msg(status);
					});	
            
            
        }
        
        
        
        
        
        $scope.$on("$routeChangeStart",function(event,next,current){
            console.log("route change start!");
            console.log("$location.path() "+$location.path())
//            angular.element(document.querySelector("#url_tab")).find("li").removeClass('active'); 
//            var splits=$location.path().split("_")
            console.log($location.path())
            if($location.path().indexOf('portal_index')>-1){
            	 angular.element(document.querySelector("#url_tab_" + "portal_index")).addClass('active');
            }else if ($location.path().indexOf('report_lis_show')>-1){
            	 angular.element(document.querySelector("#url_tab_" + "report_lis_show")).addClass('active');
            }
           
          //  $scope.loading = true;
        });
        
        $scope.$on("$routeChangeSuccess",function(event,next,current){
            console.log("route change end!");
            //$scope.loading = false;
            //console.log("e $scope.loading=="+$scope.loading);
        });

        //$scope.node_id
        $scope.quit=function(){
			g_showLoading();
            $http(
					{
						method : 'GET',
						url : AppConfig.PORTALIPHOST+'/login/do_quit',
						params : {

						}
					})
					.success(
					function(result, status,
							 headers, config) {
						layer.closeAll();
						if (result.code == 0) {
							$window.location.href="/login/to_login";
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

        
  
    }
  ]);










