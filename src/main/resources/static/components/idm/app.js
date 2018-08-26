  'use strict';

// Declare app level module which depends on views, and components
var app =angular.module('pm', [
    'ngRoute',
    'yum.services.http',
    'ngCookies',
    'mdr.user',
    'mdr.group',
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
        if( !$cookies.get("token_hr")  ){
         //  $window.location.href="/login/to_login";
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
							$rootScope.adminInPage=result.data;
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
            angular.element(document.querySelector("#url_tab")).find("li").removeClass('active'); 
//            var splits=$location.path().split("_")
            console.log($location.path())
            if($location.path().indexOf('user')>-1){
            	 angular.element(document.querySelector("#url_tab_" + "user")).addClass('active');
            }else if ($location.path().indexOf('group')>-1){
            	 angular.element(document.querySelector("#url_tab_" + "group")).addClass('active');
            }else if ($location.path().indexOf('profile')>-1){
            	 angular.element(document.querySelector("#url_tab_" + "profile")).addClass('active');
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

app.directive('select2', function () {
    return {
        restrict: 'A',
        scope: {
            config: '=',
            ngModel: '=',
            select2Model: '='
        },
        link: function (scope, element, attrs) {
            // 初始化
            var tagName = element[0].tagName,
                config = {
                    allowClear: true,
                    multiple: !!attrs.multiple,
                    placeholder: attrs.placeholder || ' '   // 修复不出现删除按钮的情况
                };

            // 生成select
            if(tagName === 'SELECT') {
                // 初始化
                var $element = $(element);
                delete config.multiple;

                $element
                    .prepend('<option value=""></option>')
                    .val('')
                    .select2(config);

                // model - view
                scope.$watch('ngModel', function (newVal) {
                    setTimeout(function () {
                        $element.find('[value^="?"]').remove();    // 清除错误的数据
                        $element.select2('val', newVal);
                    },0);
                }, true);
                return false;
            }

            // 处理input
            if(tagName === 'INPUT') {
                // 初始化
                var $element = $(element);

                // 获取内置配置
//                if(attrs.query) {
//                    scope.config = select2Query[attrs.query]();
//                }

                // 动态生成select2
                scope.$watch('config', function () {
                    angular.extend(config, scope.config);
                    $element.select2('destroy').select2(config);
                }, true);

                // view - model
                $element.on('change', function () {
                    scope.$apply(function () {
                        scope.select2Model = $element.select2('data');
                    });
                });

                // model - view
                scope.$watch('select2Model', function (newVal) {
                    $element.select2('data', newVal);
                }, true);

                // model - view
                scope.$watch('ngModel', function (newVal) {
                    // 跳过ajax方式以及多选情况
                    if(config.ajax || config.multiple) { return false }

                    $element.select2('val', newVal);
                }, true);
            }
        }
    }
});








