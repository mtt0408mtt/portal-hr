  'use strict';

// Declare app level module which depends on views, and components
var app =angular.module('pm', [
    'ngRoute',
    'yum.services.http',
    'ngCookies',
    'mdr.user',
    'ngAnimate',
    'mgcrea.ngStrap',

    'pascalprecht.translate',
//    'mdr.group',
//    'mdr.profile',
    'yum.directives.auto_focus'
  ])
  
  var activitiModule = app;
   app.constant('AppConfig', {
        pageSize: 10,
         PORTALIPHOST : 'http://localhost:8080',
         groupId:'',
         agencyId:'',
         departmentId:''
    })
  app.config(['$routeProvider', '$translateProvider', function($routeProvider,$translateProvider) {
    $routeProvider.otherwise({ redirectTo: '/user' });
    // Initialize angular-translate
    $translateProvider.useStaticFilesLoader({
      prefix: 'i18n/idm/',
      suffix: '.json'
    });

    $translateProvider.registerAvailableLanguageKeys(['en'], {
        'en_*': 'en',
        'en-*': 'en'
    });
    var lang = 'cn'
    
    $translateProvider.preferredLanguage(lang)
  }])
  app.controller('MainController', [
    '$rootScope',
    '$cookies',
    'AppConfig',
    'HttpService',
        '$location','$window','$http',
    function($rootScope, $cookies,AppConfig,HttpService,$location,$window,$http) {
    	console.log(AppConfig.PORTALIPHOST)
    	AppConfig.PORTALIPHOST='http://'+$location.host()+':8080'
    	console.log(AppConfig.PORTALIPHOST)
    	$rootScope.menu_flag=false
    	$rootScope.adminInPage={};
//        if( !$cookies.get("token")  ){
//           $window.location.href="/login/to_login";
//        }else{
////            var cookieArea=$cookies.get("area").split("aa")
////            AppConfig.groupId=cookieArea[0]
////            AppConfig.agencyId=cookieArea[1]
////            AppConfig.departmentId=cookieArea[2]
//            var tokenId=$cookies.get("token");
//
//			g_showLoading();
//            $http(
//					{
//						method : 'POST',
//						url : AppConfig.PORTALIPHOST+'/login/get_token',
//						params : {
//
//						}
//					})
//					.success(
//					function(result, status,
//							 headers, config) {
//						layer.closeAll();
//						if (result.code == 0) {
//							$rootScope.adminInPage=result.data;
//						} else {
//							layer.msg(result.msg);
//							$window.location.href="/login/to_login";
//
//						}
//					}).error(
//					function(data, status,
//							 headers, config) {
//						layer.closeAll();
//						layer.msg(status);
//					});	
//            
//            
//        }
        
        
        // Needed for auto-height
        $rootScope.window = {};
        var updateWindowSize = function() {
            $rootScope.window.width = $window.innerWidth;
            $rootScope.window.height  = $window.innerHeight;
        };

        // Window resize hook
        angular.element($window).bind('resize', function() {
            $rootScope.$apply(updateWindowSize());
        });

        $rootScope.$watch('window.forceRefresh', function(newValue) {
            if(newValue) {
                $timeout(function() {
                    updateWindowSize();
                    $rootScope.window.forceRefresh = false;
                });
            }
        });

        updateWindowSize();

        
        
        $rootScope.$on("$routeChangeStart",function(event,next,current){
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
        
        $rootScope.$on("$routeChangeSuccess",function(event,next,current){
            console.log("route change end!");
            //$scope.loading = false;
            //console.log("e $scope.loading=="+$scope.loading);
        });

        //$scope.node_id
        $rootScope.quit=function(){
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










