(function(angular) {
    'use strict';
    var module = angular.module('pm.report_show', ['ngRoute', 'yum.services.http', 'ui.bootstrap', 'ngFileUpload']);
    module.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/report_show', {
            templateUrl: 'view/hr/report_show/report_show.html',
            controller: 'ReportShowController'
        });
    }]);
    module.controller('ReportShowController', ['$scope', 'AppConfig', '$window', 'HttpService', '$http', 'Upload','$location',
        function($scope, AppConfig, $window, HttpService, $http, Upload,$location) {
    	 
        }
    ]);
})(angular);







