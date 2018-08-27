  'use strict';

// Declare app level module which depends on views, and components
angular.module('pm', [
    'ngRoute',
    'yum.services.http',
    'ngCookies',
    'pm.hr_apply',
    'pm.report_show',
  ])
   .constant('AppConfig', {
        pageSize: 10,
        //PORTALIPHOST : 'http://192.168.1.245:8081',
       //PORTALIPHOST : 'http://192.168.1.134:8081',
        PORTALIPHOST : 'http://localhost:8081',
    })
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider.otherwise({ redirectTo: '/hr_apply' });
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
        if( !$cookies.get("token") || !$cookies.get("area") ){
   //         $window.location.href="/login/to_login";
         }else{
             var cookieArea=$cookies.get("area").split("aa")
             AppConfig.groupId=cookieArea[0]
             AppConfig.agencyId=cookieArea[1]
             AppConfig.departmentId=cookieArea[2]
             
         }
        $scope.$on("$routeChangeStart",function(event,next,current){
            console.log("route change start!");
            console.log("$location.path() "+$location.path())
            angular.element(document.querySelector("#url_tab")).find("li").removeClass('active'); 
            var splits=$location.path().split("_")
            console.log($location.path())
            angular.element(document.querySelector("#url_tab_" + $location.path().replace("/",""))).addClass('active');
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
  ])

.directive('select2', function () {
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
//	                if(attrs.query) {
//	                    scope.config = select2Query[attrs.query]();
//	                }

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







