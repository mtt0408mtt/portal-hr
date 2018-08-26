(function(angular) {
    'use strict';
    var module = angular.module('pm.hostBusiness', ['ngRoute',
        'yum.services.http', 'ui.bootstrap', 'ngFileUpload'
    ]);
    module.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/hostBusiness', {
            templateUrl: 'view/lis/hostBusiness/hostBusiness.html',
            controller: 'hostBusinessController'
        });
    }]);
    module.controller('hostBusinessController', [
        '$scope',
        'AppConfig',
        '$window',
        'HttpService',
        '$http',
        'Upload',
        '$location','focus',
        function($scope, AppConfig, $window, HttpService, $http, Upload,$location,focus) {
        	
        	
            $scope.addGroup = function() {
                $('#alert1').modal('show');
            };

            var divShow = false;
            $("#navImg").click(function() {
                var thisObj = $("#rightNav");
                if (divShow) {
                    thisObj.each(function() {
                        $(this).animate({ left: "-50%" }, 300);
                    });
                    divShow = false;
                } else {
                    thisObj.each(function() {
                        $(this).animate({ left: "0" }, 300);
                    });
                    divShow = true;
                }
            });


            // 字典
            g_showLoading();
			$scope.dict_instruments= [];
			$scope.dict_sample_type = [];
			$scope.dict_sample_status = [];
			$scope.dict_patient_type = [];
			
			
			$scope.dict=function(){
				g_showLoading();
				
				$http(
						{
							method : 'POST',
							url : AppConfig.PORTALIPHOST
									+ '/lis_c/dic/get_instrument',
							params : {
							}
						})
						.success(
								function(result, status,
										headers, config) {
									layer.closeAll();
									if (result.code == 0) {
										$scope.dict_instruments = result.data;
									} else {
										layer.msg(result.msg);
									}

								}).error(
								function(data, status, headers,
										config) {
									layer.closeAll();
									layer.msg(status);
								});
				
				$http(
						{
							method : 'POST',
							url : AppConfig.PORTALIPHOST
									+ '/lis_c/dic/get_sample_type',
							params : {
								lxdm : 67
							}
						})
						.success(
								function(result, status,
										headers, config) {
									layer.closeAll();
									if (result.code == 0) {
										$scope.dict_sample_type = result.data;
									} else {
										layer.msg(result.msg);
									}

								}).error(
								function(data, status, headers,
										config) {
									layer.closeAll();
									layer.msg(status);
								});
				$http(
						{
							method : 'POST',
							url : AppConfig.PORTALIPHOST
									+ '/lis_c/dic/get_dic_dmlb',
							params : {
								dmlb : 10
							}
						})
						.success(
								function(result, status,
										headers, config) {
									layer.closeAll();
									if (result.code == 0) {
										$scope.dict_sample_status = result.data;
									} else {
										layer.msg(result.msg);
									}

								}).error(
								function(data, status, headers,
										config) {
									layer.closeAll();
									layer.msg(status);
								});	
				
				$http(
						{
							method : 'POST',
							url : AppConfig.PORTALIPHOST
									+ '/lis_c/dic/get_patient_type',
							params : {
								dmlb : 11
							}
						})
						.success(
								function(result, status,
										headers, config) {
									layer.closeAll();
									if (result.code == 0) {
										$scope.dict_patient_type = result.data;
									} else {
										layer.msg(result.msg);
									}

								}).error(
								function(data, status, headers,
										config) {
									layer.closeAll();
									layer.msg(status);
								});
				
			}
			$scope.dict();

			
			


			
			
			
			

			// 页面部分
			$scope.hb = {}
			
//			var preDate = new Date(curDate.getTime() - 24*60*60*1000); 
//			var nextDate = new Date(curDate.getTime() + 24*60*60*1000); 
			
			$scope.to_today = function() {
				var curDate = new Date();
				if ($scope.hb.today_checkbox) {
					$scope.hb.headDate=curDate;
				}
			}


			
			$scope.to_padZero=function(e){

				var keycode = window.event ? e.keyCode
						: e.which;
				if (keycode == 13) {
					$scope.ybhSqeRule($scope.hb.ybh_seq,true);
					
				}					
				
				
			}
			
			$scope.ybhSqeRule=function(obj,flag){
				if($scope.hb.ybhObj){
					return;
				}
				if(obj==0){
					//layer.msg("样本号请输入非0值");
					$scope.hb.tmhObj=true;
					return;
				}
				var reg=/^[0-9]*$/;
				if(!reg.test(obj)){
					layer.msg("样本号请输入数字");
					$scope.hb.tmhObj=true;
					return;
				}
				
				if(7<obj.length){
					//layer.msg("样本号请输入后7位数字");
					$scope.hb.tmhObj=true;
					return;
				}
				
				if(flag){
					var num=parseInt($scope.hb.ybh_seq)
					if(num>$scope.tmxxList.length){
						$scope.hb.tmhObj=false;
						$scope.hb.ybh_seq=pad(obj,7)
						focus('tmh');
					
					}else{
						$scope.getObjByYBH(num)
					}
					

					
					
					
				}
					

				
			}
			
			$scope.change_headDate=function(){
				$scope.headDateRule($scope.hb.headDate);

				
			}
			
			$scope.$watchGroup(['hb.headDate'], function(newVal,oldVal) {
				var headDate=newVal[0];
				$scope.headDateRule(headDate);
				
			})
			
 			$scope.$watchGroup(['hb.ybh_seq'], function(newVal,oldVal) {
				var ybh_seq=newVal[0];
				$scope.ybhSqeRule(ybh_seq);
				
			})           
			

			
			$scope.headDateRule=function(obj){
				var curDate = new Date();
				if(!isEmpty(obj)&&  obj.Format("YYYY-MM-DD")==curDate.Format("YYYY-MM-DD")){
					$scope.hb.today_checkbox=true;
				}else{
					$scope.hb.today_checkbox=false;
				}
				$scope.hb.ybhObj=true;
				$scope.hb.tmhObj=true;
				$scope.hb.instrumentObj=true;
				$scope.hb.ybh_seq="";
				$scope.hb.tmh=""
				$scope.hb.dic_instrument=""
				var reg = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
				var regExp = new RegExp(reg);
				if (regExp.test(obj instanceof Date && obj.Format("YYYY-MM-DD"))) {
					$scope.hb.instrumentObj=false;
				}else{
					$scope.hb.instrumentObj=true;
				}
				
			}
			

			
			

			
			
			$scope.selectInstrument=function(){
				
				var code=$scope.hb.dic_instrument.split(",")[1];
				if(isEmpty(code)){
					layer.msg("仪器选择不正确");
					return;
				}
				if(isEmpty($scope.hb.headDate)){
					layer.msg("请选择日期");
					return;	
				}
				g_showLoading();
				
				$http(
						{
							method : 'POST',
							url : AppConfig.PORTALIPHOST
									+ '/lis_c/opt/select_instrument',
							params : {
								ybh_prefix:$scope.hb.headDate.Format("YYYYMMDD")+code,
								instrument_id:$scope.hb.dic_instrument.split(",")[0]
								
							}
						})
						.success(
								function(result, status,
										headers, config) {
									layer.closeAll();
									if (result.code == 0) {
										$scope.clear()
										$scope.tmxxList = result.data;
										if($scope.tmxxList.length>0){
											var num=parseInt($scope.tmxxList.length)+1
											$scope.hb.ybh_seq=pad(num+"",7)
											$scope.hb.ybhObj=false;
											$scope.hb.tmhObj=false;
											focus('tmh');
										}else{
											$scope.hb.ybh_seq="0000001";
											$scope.hb.ybhObj=false;
											$scope.hb.tmhObj=false;
											focus('tmh');
											
										}
										
									} else {
										layer.msg(result.msg);
									}

								}).error(
								function(data, status, headers,
										config) {
									layer.closeAll();
									layer.msg(status);
								});
				
			}
			
			$scope.getObjByYBH=function(num){
				g_showLoading();
				var code=$scope.hb.dic_instrument.split(",")[1];
				$http(
						{
							method : 'POST',
							url : AppConfig.PORTALIPHOST
									+ '/lis_c/opt/getobjbyybh',
							params : {
								ybh_prefix:$scope.hb.headDate.Format("YYYYMMDD")+code,
								ybh_seq:$scope.hb.ybh_seq,
								instrument_id:$scope.hb.dic_instrument.split(",")[0]
								
							}
						})
						.success(
								function(result, status,
										headers, config) {
									layer.closeAll();
									if (result.code == 0) {
										$scope.getObj(result.data,num-1)
										
									} else {
										layer.msg(result.msg);
									}

								}).error(
								function(data, status, headers,
										config) {
									layer.closeAll();
									layer.msg(status);
								});
				
				
				
			}
			
			$scope.clear=function(){
				$scope.hb.tmh="";
				$scope.hb.mzhm="";
				$scope.hb.brxm="";
				$scope.hb.sex="";
				$scope.hb.age="";
				$scope.hb.nldw="";
				$scope.hb.yepb="";
				$scope.hb.sqks="";
				$scope.hb.bedNo="";
				$scope.hb.patient_type="";
				$scope.hb.sample_status="";
				$scope.hb.sample_type="";
				$scope.hb.ynzd="";
				$scope.hb.bz="";
				$scope.hb.sjdwmc="";
				$(".inputName:contains('样本性状')").parent().find(".select2-chosen").html("");
				$(".inputName:contains('样本类型')").parent().find(".select2-chosen").html("");	
				$scope.hb.sample_type=""
				$scope.hb.sample_status=""
			    $scope.hb.sample_status_name=""
			    $scope.hb.sample_type_name=""
				$scope.hb.zhmcStr="";
				$scope.hb.zxsj="";
				$scope.hb.jssj="";
				$scope.hb.csr="";
				$scope.hb.fsr="";
				$scope.hb.cssj="";
				$scope.hb.fssj="";
				$scope.hb.jyr="";
			}
			
			$scope.getObj = function(item, i) {

				$scope.focus = i
				$scope.hb.sample_status_name = item.ybxzName+","+item.ybxz;
				
//				$('#select2-chosen-6').html(
//						$scope.hb.sample_status_name);

				$scope.hb.sample_type_name = item.lxdmName+","+item.lxdm;		
//				$('#select2-chosen-8').html(
//						$scope.hb.sample_type_name);
				
				$(".inputName:contains('样本性状')").parent().find(".select2-chosen").html($scope.hb.sample_status_name);
				$(".inputName:contains('样本类型')").parent().find(".select2-chosen").html($scope.hb.sample_type_name);				
				
				$scope.hb.sample_type=item.ybxz
				$scope.hb.sample_status=item.lxdm
				$scope.hb.ybh_seq=item.ybhmEnd
				$scope.hb.tmh=item.tmh
				$scope.hb.sjdwmc=item.sjdwmc
				$scope.hb.mzhm=item.mzhm
				$scope.hb.brxm=item.brxm
				$scope.hb.sex=item.sex
				$scope.hb.age=item.age
				$scope.hb.nldw=item.nldw
				$scope.hb.yepb=item.yepb
				
				
				if (item.yepb == "1") {
					$scope.hb.yepb = true
				} else {
					$scope.hb.yepb = false
				}
				
				$scope.hb.sqks=item.sqks
				$scope.hb.bedNo=item.bedNo
			    $scope.hb.patient_type=item.brlx
				$scope.hb.ynzd=item.ynzd
				$scope.hb.bz=item.bz
				$scope.hb.zhmcStr=item.zhmcStr
				$scope.hb.zxsj=new Date(item.zxsj)
				$scope.hb.jssj=new Date(item.jssj)
				
				$scope.hb.csr=item.csr
				$scope.hb.fsr=item.fsr
				$scope.hb.cssj=new Date(item.cssj)
				$scope.hb.fssj=new Date(item.fssj)
				$scope.hb.jyr=item.jyr
			}
			
			$scope.updateObj=function(){
				
				g_showLoading();
				var code=$scope.hb.dic_instrument.split(",")[1];
				$http(
						{
							method : 'POST',
							url : AppConfig.PORTALIPHOST
									+ '/lis_c/opt/update_obj',
							params : {
								ybh_prefix:$scope.hb.headDate.Format("YYYYMMDD")+code,
								ybh_seq:$scope.hb.ybh_seq,
								//instrument_id:$scope.hb.dic_instrument.split(",")[0],
								//tmh:$scope.hb.tmh,
								
								
								mzhm:$scope.hb.mzhm,
								brxm:$scope.hb.brxm,
								sex:$scope.hb.sex,
								age:$scope.hb.age,
								nldw:$scope.hb.nldw,
								yepb:$scope.hb.yepb,
								sqks:$scope.hb.sqks,
								bedNo:$scope.hb.bedNo,
								patient_type:$scope.hb.patient_type,
								sample_status:$scope.hb.sample_status,
								sample_type:$scope.hb.sample_type,
								ynzd:$scope.hb.ynzd,
								bz:$scope.hb.bz,

								
							}
						})
						.success(
								function(result, status,
										headers, config) {
									layer.closeAll();
									if (result.code == 0) {
										$scope.clear()
										//$scope.initForm();
										layer.msg("更新成功");
									} else {
										layer.msg(result.msg);
									}

								}).error(
								function(data, status, headers,
										config) {
									layer.closeAll();
									layer.msg(status);
								});				
				
				
			}
			
			
			//初始化
			$scope.initForm = function() {
				// 初始化时间控件
				var curDate = new Date();
				var preDate = new Date(curDate.getTime() - 24*60*60*1000); 
				var nextDate = new Date(curDate.getTime() + 24*60*60*1000); 

				$scope.hb = {
					headDate : curDate,
					dic_instrument:"",
					ybhObj:true,
					tmhObj:true,
					instrumentObj:false,
					ybh_seq:"",
			        tmh:"",
				    dic_instrument:"",
				    today_checkbox:true,
					
					
				}
			}

			$scope.init = function() {
				$scope.initForm();
			}

			$scope.init();

        }
    ]);
})(angular);