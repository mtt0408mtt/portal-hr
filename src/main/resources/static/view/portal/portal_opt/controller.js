(function(angular) {
    'use strict';
    var module = angular.module('pm.portal_opt', ['ngRoute', 'yum.services.http', 'ui.bootstrap', 'ngFileUpload','uiSwitch']);
    module.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/portal_opt', {
            templateUrl: 'view/portal/portal_opt/portal_opt.html',
            controller: 'PortalOptController'
        });
    }]);
    module.controller('PortalOptController', ['$scope', 'AppConfig', '$window', 'HttpService', '$http', 'Upload','$location',
        function($scope, AppConfig, $window, HttpService, $http, Upload,$location) {
    	    $(".widget-header").click(function(){
                $(this).next().slideToggle();
            });
    	    
    	    // MTT部分=============================================================
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
    	    
    	    
            $scope.ndesc=false;
            $scope.order=function(obj){
            	$scope.title=obj;
            	$scope.ndesc=!$scope.ndesc;
// alert($scope.title)
// alert($scope.ndesc)
            }
            
            $scope.admin_form_reset=function(){
            	  $scope.admin={};
            	  $scope.admin_form.$setPristine();
            	  $scope.admin_default();
            }
            

            
            
            $(".mytab_admin").unbind("click").bind("click",function(){
            	$(this).parent().parent().find('li').removeClass("active");
            	$(this).parent().parent().find('a').removeClass("active");
            	$(this).parent().parent().parent().find('.tab-pane').removeClass("active");
            	$(this).addClass("active");
            	$(this).parent().addClass("active");
            	var activepane=$(this).attr('aria-controls')
            	if(activepane=='tab_admin_list'){
            		$scope.get_admins();
            	}
            	
            	$('#'+activepane).addClass("active");
            	
            })
            
            $scope.save_admin=function(){
            	g_showLoading();
            	 $http(
      					{
      						method : 'POST',
      						url : AppConfig.PORTALIPHOST+'/portal_c/opt/save_admin',
      						params : {
      							name:$scope.admin.name,
      							code:$scope.admin.code,
      							group:$scope.admin.group.id+","+$scope.admin.group.name,
      							agency:$scope.admin.agency.id+","+$scope.admin.agency.name,
      							department:$scope.admin.department.id+","+$scope.admin.department.name,
      							duty:$scope.admin.duty.id+","+$scope.admin.duty.name,
      							operators:'1,admin',

      							
      							
      							
      						}
      					})
      					.success(
      					function(result, status,headers, config) {
      						layer.closeAll();
      						if (result.code == 0) {
     	    					 layer.msg("添加成功");
     	    					 $scope.admin_form_reset()
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
            
            $scope.dict_group = [/* {id:"1",name:"天瑞精准医疗集团1"} */];
           // $scope.dict_agency = [{id:"1",name:"测试机构"},{id:"2",name:"镇江天瑞精准医学检验实验室"}];
           // $scope.dict_department = [{id:"1",name:"生化室",code:"SH001"},{id:"2",name:"镇江生化室",code:"ZJSH001"}];     
           // $scope.dict_duty = [{id:"1",name:"科室内部职级"},{id:"2",name:"主任检验师"}];  
	        $scope.dict_subsys = [{id:"1",name:"Lis系统",path:"/lis"},{id:"2",name:"设备系统",path:"/device"},{id:"3",name:"报告系统",path:"/report_lis"},{id:"4",name:"维护系统",path:"/portal"},{id:"5",name:"试剂耗材",path:"/sjhc"},{id:"6",name:"超级维护",path:"/portal"}];  
          
	        
	        
            $scope.get_dict_group=function(){
            	g_showLoading();
	           	 $http(
	  					{
	  						method : 'POST',
	  						url : AppConfig.PORTALIPHOST+'/portal_c/opt/get_dict_group',
	  						params : {

	  						}
	  					})
	  					.success(
	  					function(result, status,headers, config) {
	  						layer.closeAll();
	  						console.log(result)
	  						if (result.code == 0) {
	 	    						$scope.dict_group=result.data;

	  		
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
            
            $scope.get_dict_group();
	        
	        
            $scope.admin_default=function(){
    			$scope.admin = {
    					group : "",// 1,天瑞精准医疗集团
    					agency:"",// 1,测试机构
    					department:"",// 1,生化室
    					duty:""// 1,科室内部职级
    					
    			};
            	
            }
            $scope.admin_default()

	        $scope.search={
	        		search_admin_filter:"",
	        	    search_agency_filter:"",
	        	    search_role_filter:"",
	        	    search_res_filter:"",
	        	    search_admin_code:"",
	        	    search_role_name:""
		        };
  	    
            $scope.get_admins=function(){
            	g_showLoading();
	           	 $http(
	  					{
	  						method : 'POST',
	  						url : AppConfig.PORTALIPHOST+'/portal_c/opt/get_admins',
	  						params : {
      							pageSize:AppConfig.pageSize,
      							pageIndex:$scope.page.currentPage,
    							'adminSearchName':$scope.search.search_admin_filter
	  						}
	  					})
	  					.success(
	  					function(result, status,headers, config) {
	  						layer.closeAll();
	  						console.log(result)
	  						if (result.code == 0) {
	  							if(result.data.rows[0]){
	 	    						$scope.admin_datas=result.data.rows;
	 	    						// layer.msg("查询成功");
	 	    						

	  							}else{
	  								$scope.admin_datas=[];
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
            
            $scope.get_admins();
            

            
	        $scope.myAdminKeyup = function(e){
	            var keycode = window.event?e.keyCode:e.which;
	            if(keycode==13){
	            	$scope.get_admins();
	            }
	        };
	        
	        
	     
	        
	        // 权限部分
	        // vr========================================================
	        
	        
            $scope.ndesc_vr=false;
            $scope.order_vr=function(obj){
            	$scope.title_vr=obj;
            	$scope.ndesc_vr=!$scope.ndesc_vr;
            }
            
            
	        $scope.myVrKeyup = function(e){
	            var keycode = window.event?e.keyCode:e.which;
	            if(keycode==13){
 	            	 if($scope.search.search_admin_code=="" || $scope.search.search_admin_code == null || $scope.search.search_admin_code == undefined){
  	            		 layer.msg("请输入角色名称");
  	            		 return
  	            	 }
	            	$scope.get_vrs();
	            }
	        }; 


  	    
            $scope.get_vrs=function(){
            	g_showLoading();
	           	 $http(
	  					{
	  						method : 'POST',
	  						url : AppConfig.PORTALIPHOST+'/portal_c/opt/get_agency_roles',
	  						params : {
    							'admin_code':$scope.search.search_admin_code
	  						}
	  					})
	  					.success(
	  					function(result, status,headers, config) {
	  						layer.closeAll();
	  						console.log(result)
	  						if (result.code == 0) {
	  							if(result.data){
	 	    						$scope.vr_datas=result.data
	 	    						
	 	    						// layer.msg("查询成功");
	  							}else{
	  								$scope.vr_datas=[];
	  								layer.msg("没有记录");
	  							}
	  		
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
            
            
            
            $scope.switch_vr=function(data){
            	g_showLoading();
            	 $http(
 	  					{
 	  						method : 'POST',
 	  						url : AppConfig.PORTALIPHOST+'/portal_c/opt/set_agency_roles',
 	  						params : {
     							'admin_code':$scope.search.search_admin_code,
     							'operators':'1,admin',
     							'role_id':data.id,
     							'status':data.arStatus
     							
 	  						}
 	  					})
 	  					.success(
 	  					function(result, status,headers, config) {
 	  						layer.closeAll();
 	  						console.log(result)
 	  						if (result.code == 0) {
 	 	    					layer.msg("操作成功");

 	  						} else {
 	  							layer.msg(result.msg);
 	  						}
 	
 	  					}).error(
 	  					function(data, status,
 	  							 headers, config) {
 	
 	  						layer.closeAll();
 	  						layer.msg(status);
 	
 	  					});

              };
              
      
            
	        
	        
        }
    ]);
})(angular);







