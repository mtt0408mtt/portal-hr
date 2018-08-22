(function(angular) {
    'use strict';
    var module = angular.module('pm.portal_root', ['ngRoute', 'yum.services.http', 'ui.bootstrap', 'ngFileUpload','uiSwitch']);
    module.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/portal_root', {
            templateUrl: 'view/portal/portal_root/portal_root.html',
            controller: 'PortalRootController'
        });
    }]);
    module.controller('PortalRootController', ['$scope', 'AppConfig', '$window', 'HttpService', '$http', 'Upload','$location',
        function($scope, AppConfig, $window, HttpService, $http, Upload,$location) {
    	    $(".widget-header").click(function(){
                $(this).next().slideToggle();
            });
    	    
    	    //MTT部分=============================================================

            
            $scope.dict_group = [/*{id:"1",name:"天瑞精准医疗集团"}*/];
            //$scope.dict_agency = [{id:"1",name:"测试机构"},{id:"2",name:"镇江天瑞精准医学检验实验室"}];
            //$scope.dict_department = [{id:"1",name:"生化室",code:"SH001"},{id:"2",name:"镇江生化室",code:"ZJSH001"}];     
            //$scope.dict_duty = [{id:"1",name:"科室内部职级"},{id:"2",name:"主任检验师"}];  
	        $scope.dict_subsys = [{id:"1",name:"Lis系统",path:"/lis"},{id:"2",name:"设备系统",path:"/device"},{id:"3",name:"报告系统",path:"/report_lis"},{id:"4",name:"维护系统",path:"/portal"},{id:"5",name:"试剂耗材",path:"/sjhc"},{id:"6",name:"超级维护",path:"/portal"}];  
          
            $scope.get_dict_group=function(){
            	g_showLoading();
	           	 $http(
	  					{
	  						method : 'POST',
	  						url : AppConfig.PORTALIPHOST+'/portal_c/root/get_dict_group',
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

	        $scope.search={
	        		search_admin_filter:"",
	        	    search_agency_filter:"",
	        	    search_role_filter:"",
	        	    search_res_filter:"",
	        	    search_admin_code:"",
	        	    search_role_name:""
		        };
  	 
	        
	        
	        //agency========================================================
	        $scope.dict_type_agency = [{id:"0",name:"投放合作"},{id:"1",name:"独立自用"},{id:"2",name:"（中心接标）客户"},{id:"3",name:"外送（中心送出）"}];  
			$scope.page_agency = {};
			$scope.page_agency.currentPage = 1;
			$scope.page_agency.rows = AppConfig.pageSize;
			$scope.page_agency.totalCount = 0;
			$scope.page_agency.totalPages = 0;
			
			$scope.go_agency = function(page) {
				if (page >= 1
					&& page <= $scope.page_agency.totalPages)
					$scope.page_agency.currentPage = page;
				$scope.get_agencys();
			};
    	    
    	    
            $scope.ndesc_agency=false;
            $scope.order_agency=function(obj){
            	$scope.title_agency=obj;
            	$scope.ndesc_agency=!$scope.ndesc_agency;
//            	alert($scope.title)
//            	alert($scope.ndesc)
            }
            
            $scope.agency_form_reset=function(){
            	  $scope.agency={};
//            	  $scope.agency.code="";
//            	  $scope.agency.group="";//1,天瑞精准医疗集团
            	  $scope.agency_form.$setPristine();

            }
            
            
            $(".mytab_agency").unbind("click").bind("click",function(){
            	$(this).parent().parent().find('li').removeClass("active");
            	$(this).parent().parent().find('a').removeClass("active");
            	$(this).parent().parent().parent().find('.tab-pane').removeClass("active");
            	$(this).addClass("active");
            	$(this).parent().addClass("active");
            	var activepane=$(this).attr('aria-controls')
            	if(activepane=='tab_agency_list'){
            		$scope.get_agencys();
            	}
            	
            	
            	$('#'+activepane).addClass("active");
            	
            })
            
            $scope.save_agency=function(){
            	g_showLoading();
            	 $http(
      					{
      						method : 'POST',
      						url : AppConfig.PORTALIPHOST+'/portal_c/root/save_agency',
      						params : {
      							name:$scope.agency.name,
      							code:$scope.agency.code,
      							group:$scope.agency.group,
      							operators:'1,admin',
      							
      							
      							desc:$scope.agency.desc,
      							comment:$scope.agency.comment,
      							address:$scope.agency.address,
      							post_code:$scope.agency.postCode,
      							contact_name:$scope.agency.contactName,
      							contact_phone:$scope.agency.contactPhone,
      							fax:$scope.agency.fax,
      							email:$scope.agency.email,
      							type:$scope.agency.type,

      							
      							
      							
      						}
      					})
      					.success(
      					function(result, status,headers, config) {
      						layer.closeAll();
      						if (result.code == 0) {
     	    					 layer.msg("添加成功");
     	    					 $scope.agency_form_reset();
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
            
            
			$scope.agency = {
					group : "",//1,天瑞精准医疗集团
					
			};

  	    
            $scope.get_agencys=function(){
            	g_showLoading();
	           	 $http(
	  					{
	  						method : 'POST',
	  						url : AppConfig.PORTALIPHOST+'/portal_c/root/get_agencys',
	  						params : {
      							pageSize:AppConfig.pageSize,
      							pageIndex:$scope.page_agency.currentPage,
    							'agencySearchName':$scope.search.search_agency_filter
	  						}
	  					})
	  					.success(
	  					function(result, status,headers, config) {
	  						layer.closeAll();
	  						console.log(result)
	  						if (result.code == 0) {
	  							if(result.data.rows[0]){
	 	    						$scope.agency_datas=result.data.rows;
	 	    						//layer.msg("查询成功");
	 	    						

	  							}else{
	  								$scope.agency_datas=[];
	  								layer.msg("没有记录");
	  							}
								$scope.page_agency.totalCount = result.data.total;
								$scope.page_agency.totalPages = Math
									.ceil($scope.page_agency.totalCount
									/ $scope.page_agency.rows);
	  		
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
            
            $scope.get_agencys();
            

            
	        $scope.myAgencyKeyup = function(e){
	            var keycode = window.event?e.keyCode:e.which;
	            if(keycode==13){
	            	$scope.get_agencys();
	            }
	        };
	        
	      //role========================================================
			$scope.page_role = {};
			$scope.page_role.currentPage = 1;
			$scope.page_role.rows = AppConfig.pageSize;
			$scope.page_role.totalCount = 0;
			$scope.page_role.totalPages = 0;
			
			$scope.go_role = function(page) {
				if (page >= 1
					&& page <= $scope.page_role.totalPages)
					$scope.page_role.currentPage = page;
				$scope.get_roles();
			};
    	    
    	    
            $scope.ndesc_role=false;
            $scope.order_role=function(obj){
            	$scope.title_role=obj;
            	$scope.ndesc_role=!$scope.ndesc_role;
//            	alert($scope.title)
//            	alert($scope.ndesc)
            }
            
            $scope.role_form_reset=function(){
            	  $scope.role={};
//            	  $scope.agency.code="";
//            	  $scope.agency.group="";//1,天瑞精准医疗集团
            	  $scope.role_form.$setPristine();

            }
            
            
            $(".mytab_role").unbind("click").bind("click",function(){
            	$(this).parent().parent().find('li').removeClass("active");
            	$(this).parent().parent().find('a').removeClass("active");
            	$(this).parent().parent().parent().find('.tab-pane').removeClass("active");
            	$(this).addClass("active");
            	$(this).parent().addClass("active");
            	var activepane=$(this).attr('aria-controls')
            	if(activepane=='tab_role_list'){
            		$scope.get_roles();
            	}
            	$('#'+activepane).addClass("active");
            	
            })
            
            $scope.save_role=function(){
            	g_showLoading();
            	 $http(
      					{
      						method : 'POST',
      						url : AppConfig.PORTALIPHOST+'/portal_c/root/save_role',
      						params : {
      							name:$scope.role.name,
      							operators:'1,admin',
      							comment:$scope.role.comment,
                                agency:$scope.role.agency.id+","+$scope.role.agency.name,
                                group:$scope.role.group.id+","+$scope.role.group.name,
      							
      							
      							
      						}
      					})
      					.success(
      					function(result, status,headers, config) {
      						layer.closeAll();
      						if (result.code == 0) {
     	    					 layer.msg("添加成功");
     	    					 $scope.role_form_reset();
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
            
            
			$scope.role = {
					
			};

  	    
            $scope.get_roles=function(){
            	g_showLoading();
	           	 $http(
	  					{
	  						method : 'POST',
	  						url : AppConfig.PORTALIPHOST+'/portal_c/root/get_roles',
	  						params : {
      							pageSize:AppConfig.pageSize,
      							pageIndex:$scope.page_role.currentPage,
    							'roleSearchName':$scope.search.search_role_filter
	  						}
	  					})
	  					.success(
	  					function(result, status,headers, config) {
	  						layer.closeAll();
	  						console.log(result)
	  						if (result.code == 0) {
	  							if(result.data.rows[0]){
	 	    						$scope.role_datas=result.data.rows;
	 	    						//layer.msg("查询成功");
	 	    						

	  							}else{
	  								$scope.role_datas=[];
	  								layer.msg("没有记录");
	  							}
								$scope.page_role.totalCount = result.data.total;
								$scope.page_role.totalPages = Math
									.ceil($scope.page_role.totalCount
									/ $scope.page_role.rows);
	  		
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
            
            $scope.get_roles();
            

            
	        $scope.myRoleKeyup = function(e){
	            var keycode = window.event?e.keyCode:e.which;
	            if(keycode==13){
	            	$scope.get_roles();
	            }
	        }; 
	        
	        
		      //res========================================================
			$scope.page_res = {};
			$scope.page_res.currentPage = 1;
			$scope.page_res.rows = AppConfig.pageSize;
			$scope.page_res.totalCount = 0;
			$scope.page_res.totalPages = 0;
			
			$scope.go_res = function(page) {
				if (page >= 1
					&& page <= $scope.page_res.totalPages)
					$scope.page_res.currentPage = page;
				$scope.get_reses();
			};
    	    
    	    
            $scope.ndesc_res=false;
            $scope.order_res=function(obj){
            	$scope.title_res=obj;
            	$scope.ndesc_res=!$scope.ndesc_res;
//            	alert($scope.title)
//            	alert($scope.ndesc)
            }
            
            $scope.res_form_reset=function(){
            	  $scope.res={};
//            	  $scope.agency.code="";
//            	  $scope.agency.group="";//1,天瑞精准医疗集团
            	  $scope.res_form.$setPristine();

            }
            
            
            $(".mytab_res").unbind("click").bind("click",function(){
            	$(this).parent().parent().find('li').removeClass("active");
            	$(this).parent().parent().find('a').removeClass("active");
            	$(this).parent().parent().parent().find('.tab-pane').removeClass("active");
            	$(this).addClass("active");
            	$(this).parent().addClass("active");
            	var activepane=$(this).attr('aria-controls')
            	if(activepane=='tab_res_list'){
            		$scope.get_reses();
            	}
            	$('#'+activepane).addClass("active");
            	
            })
            
            $scope.save_res=function(){
            	g_showLoading();
            	 $http(
      					{
      						method : 'POST',
      						url : AppConfig.PORTALIPHOST+'/portal_c/root/save_res',
      						params : {
      							name:$scope.res.name,
      							operators:'1,admin',
      							comment:$scope.res.comment,
      							res_url:$scope.res.res_url,
      							//subsys:$scope.res.subsys
      							subsys:$scope.res.subsys,
                                agency:$scope.res.agency.id+","+$scope.res.agency.name,
                                group:$scope.res.group.id+","+$scope.res.group.name,
                                depart:$scope.res.depart.id+","+$scope.res.depart.name+","+$scope.res.depart.code,
      						}
      					})
      					.success(
      					function(result, status,headers, config) {
      						layer.closeAll();
      						if (result.code == 0) {
     	    					 layer.msg("添加成功");
     	    					 $scope.res_form_reset();
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
            
            
			$scope.res = {
					
			};

  	    
            $scope.get_reses=function(){
            	g_showLoading();
	           	 $http(
	  					{
	  						method : 'POST',
	  						url : AppConfig.PORTALIPHOST+'/portal_c/root/get_reses',
	  						params : {
      							pageSize:AppConfig.pageSize,
      							pageIndex:$scope.page_res.currentPage,
    							'resSearchName':$scope.search.search_res_filter
	  						}
	  					})
	  					.success(
	  					function(result, status,headers, config) {
	  						layer.closeAll();
	  						console.log(result)
	  						if (result.code == 0) {
	  							if(result.data.rows[0]){
	 	    						$scope.res_datas=result.data.rows;
	 	    						//layer.msg("查询成功");
	 	    						

	  							}else{
	  								$scope.res_datas=[];
	  								layer.msg("没有记录");
	  							}
								$scope.page_res.totalCount = result.data.total;
								$scope.page_res.totalPages = Math
									.ceil($scope.page_res.totalCount
									/ $scope.page_res.rows);
	  		
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
            
            $scope.get_reses();
            

            
	        $scope.myResKeyup = function(e){
	            var keycode = window.event?e.keyCode:e.which;
	            if(keycode==13){
	            	$scope.get_reses();
	            }
	        }; 
	        
	        //权限部分
	        //vr========================================================
	        
	        
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
	  						url : AppConfig.PORTALIPHOST+'/portal_c/root/get_agency_roles',
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
	 	    						
	 	    						//layer.msg("查询成功");
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
 	  						url : AppConfig.PORTALIPHOST+'/portal_c/root/set_agency_roles',
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
              
              //角色配资源=============================================
              

              $scope.ndesc_rr=false;
              $scope.order_rr=function(obj){
              	$scope.title_rr=obj;
              	$scope.ndesc_rr=!$scope.ndesc_rr;
              }
              
  	        $scope.myRrKeyup = function(e){
  	            var keycode = window.event?e.keyCode:e.which;
  	            if(keycode==13){
  	            	 if($scope.search.search_role_name=="" || $scope.search.search_role_name == null || $scope.search.search_role_name == undefined){
  	            		 layer.msg("请输入角色名称");
  	            		 return
  	            	 }
  	            	$scope.get_rrs();
  	            }
  	        }; 


    	    
              $scope.get_rrs=function(){

            	  
            	  
              	g_showLoading();
  	           	 $http(
  	  					{
  	  						method : 'POST',
  	  						url : AppConfig.PORTALIPHOST+'/portal_c/root/get_role_reses',
  	  						params : {
      							'role_name':$scope.search.search_role_name
  	  						}
  	  					})
  	  					.success(
  	  					function(result, status,headers, config) {
  	  						layer.closeAll();
  	  						console.log(result)
  	  						if (result.code == 0) {
  	  							if(result.data){
  	 	    						$scope.rr_datas=result.data
  	 	    						
  	 	    						//layer.msg("查询成功");
  	  							}else{
  	  								$scope.rr_datas=[];
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
              
              
              
              $scope.switch_rr=function(data){
              	g_showLoading();
              	 $http(
   	  					{
   	  						method : 'POST',
   	  						url : AppConfig.PORTALIPHOST+'/portal_c/root/set_role_reses',
   	  						params : {
       							'role_name':$scope.search.search_role_name,
       							'operators':'1,admin',
       							
       							'res_id':data.resId,
       							'role_id':data.roleId,
       							'status':data.rrStatus
       							
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







