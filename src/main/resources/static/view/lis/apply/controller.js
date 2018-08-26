(function(angular) {
	'use strict';
	var module = angular.module('pm.lis_apply', [ 'ngRoute',
			'yum.services.http', 'ui.bootstrap', 'ngFileUpload' ]);
	module.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/lis_apply', {
			templateUrl : 'view/lis/apply/apply.html',
			controller : 'LisApplyController'
		});
	} ]);

	module
			.controller(
					'LisApplyController',
					[
							'$scope',
							'AppConfig',
							'$window',
							'HttpService',
							'$http',
							'Upload',
							'$location',
							'$timeout',
							function($scope, AppConfig, $window, HttpService,
									$http, Upload, $location, $timeout) {

								$scope.addGroup = function() {
									if(!$scope.addGroupBtn){
										return
									}
									$('#alert1').modal('show');	
								};
								$(".itemClose").click(function() {
									$(this).parent().remove();
								})

								var Bind = function(object, fun) {
									return function() {
										return fun.apply(object, arguments);
									}
								}

								function AutoComplete(obj, autoObj, arr) {
									this.obj = document.getElementById(obj); // 输入框
									this.autoObj = document
											.getElementById(autoObj); // DIV的根节点
									this.value_arr = arr; // 不要包含重复值
									this.index = -1; // 当前选中的DIV的索引
									this.search_value = ""; // 保存当前搜索的字符
								}
								AutoComplete.prototype = {
									// 初始化DIV的位置
									init : function() {
										this.autoObj.style.left = this.obj.offsetLeft
												+ "px";
										this.autoObj.style.top = this.obj.offsetTop
												+ this.obj.offsetHeight + "px";
										this.autoObj.style.width = this.obj.offsetWidth
												- 2 + "px"; // 减去边框的长度2px
									},
									// 删除自动完成需要的所有DIV
									deleteDIV : function() {
										while (this.autoObj.hasChildNodes()) {
											this.autoObj
													.removeChild(this.autoObj.firstChild);
										}
										this.autoObj.className = "auto_hidden";
									},
									// 设置值
									setValue : function(_this) {
										return function() {
											_this.obj.value = this.seq;
											_this.autoObj.className = "auto_hidden";
										}
									},
									// 模拟鼠标移动至DIV时，DIV高亮
									autoOnmouseover : function(_this,
											_div_index) {
										return function() {
											_this.index = _div_index;
											var length = _this.autoObj.children.length;
											for (var j = 0; j < length; j++) {
												if (j != _this.index) {
													_this.autoObj.childNodes[j].className = 'auto_onmouseout';
												} else {
													_this.autoObj.childNodes[j].className = 'auto_onmouseover';
												}
											}
										}
									},
									// 更改classname
									changeClassname : function(length) {
										for (var i = 0; i < length; i++) {
											if (i != this.index) {
												this.autoObj.childNodes[i].className = 'auto_onmouseout';
											} else {
												this.autoObj.childNodes[i].className = 'auto_onmouseover';
												this.obj.value = this.autoObj.childNodes[i].seq;
											}
										}
									},
									// 响应键盘
									pressKey : function(event) {
										var length = this.autoObj.children.length;
										// 光标键"↓"
										if (event.keyCode == 40) {
											++this.index;
											if (this.index > length) {
												this.index = 0;
											} else if (this.index == length) {
												this.obj.value = this.search_value;
											}
											this.changeClassname(length);
										}
										// 光标键"↑"
										else if (event.keyCode == 38) {
											this.index--;
											if (this.index < -1) {
												this.index = length - 1;
											} else if (this.index == -1) {
												this.obj.value = this.search_value;
											}
											this.changeClassname(length);
										}
										// 回车键
										else if (event.keyCode == 13) {
											this.autoObj.className = "auto_hidden";
											this.index = -1;
										} else {
											this.index = -1;
										}
									},
									initHtml : function() {
										this.init();
										var div_index = 0; // 记录创建的DIV的索引
										var valueArr = this.value_arr;
										valueArr.sort();
										this.autoObj.innerHTML = "";
										for (var i = 0; i < valueArr.length; i++) {
											var div = document
													.createElement("div");
											div.className = "auto_onmouseout";
											div.seq = valueArr[i];
											div.onclick = this.setValue(this);
											div.onmouseover = this
													.autoOnmouseover(this,
															div_index);
											div.innerHTML = valueArr[i]; // 搜索到的字符粗体显示
											this.autoObj.appendChild(div);
											this.autoObj.className = "auto_show";
											div_index++;
										}
										// this.autoObj.onmouseleave =
										// function(){
										// $(".toSelect").blur(function(){
										// $('.auto_show').attr("class","auto_hidden");
										// // alert(11)
										// });
										// }
									},
									// 程序入口
									start : function(event) {
										this.initHtml();
										if (event.keyCode != 13
												&& event.keyCode != 38
												&& event.keyCode != 40) {
											this.init();
											// this.deleteDIV();
											this.search_value = this.obj.value;
											var valueArr = this.value_arr;
											valueArr.sort();
											if (this.obj.value.replace(
													/(^\s*)|(\s*$)/g, '') == "") {
												return;
											} // 值为空，退出
											try {
												var reg = new RegExp("("
														+ this.obj.value + ")",
														"i");
											} catch (e) {
												return;
											}
											var div_index = 0; // 记录创建的DIV的索引
											this.autoObj.innerHTML = "";
											for (var i = 0; i < valueArr.length; i++) {
												if (reg.test(valueArr[i])) {
													var div = document
															.createElement("div");
													div.className = "auto_onmouseout";
													div.seq = valueArr[i];
													div.onclick = this
															.setValue(this);
													div.onmouseover = this
															.autoOnmouseover(
																	this,
																	div_index);
													div.innerHTML = "";
													div.innerHTML = valueArr[i]
															.replace(reg,
																	"<strong>$1</strong>"); // 搜索到的字符粗体显示
													this.autoObj
															.appendChild(div);
													this.autoObj.className = "auto_show";
													div_index++;
												}
											}
											// this.autoObj.onmouseleave =
											// function(){
											// $(".toSelect").blur(function(){
											// $('.auto_show').attr("class","auto_hidden");
											// alert(11)
											// });
											// }
										}
										this.pressKey(event);
										window.onresize = Bind(this,
												function() {
													this.init();
												});
									}
								}
								$scope.autoComplete = new AutoComplete(
										'select1', 'auto', [ 'b0', 'b12',
												'b22', 'b3', 'b4', 'b5', 'b6',
												'b7', 'b8', 'b2', 'abd', 'ab',
												'acd', 'accd', 'b1', 'cd',
												'ccd', 'cbcv', 'cxf' ]);

								// $scope.clear = function() {
								// $('.auto_show').attr("class","auto_hidden");
								// };
								$(".auto_show").blur(function() {
									alert(11);
									// $('.auto_show').attr("class","auto_hidden");
								});

								// 页面部分
								// 初始化时间控件

								$scope.apply = {}
								

								

								// 字典
								$scope.dict_send_unit = [];

								$http(
										{
											method : 'POST',
											url : AppConfig.PORTALIPHOST
													+ '/lis_c/dic/get_dic_sendUnit',
											params : {
												'agency' : '32110010',
												'group' : '1'
											}
										})
										.success(
												function(result, status,
														headers, config) {
													layer.closeAll();
													if (result.code == 0) {
														$scope.dict_send_unit = result.data;
													} else {
														layer.msg(result.msg);
													}

												}).error(
												function(data, status, headers,
														config) {
													layer.closeAll();
													layer.msg(status);
												});

								$scope.dict_patient_type = [];

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

								$scope.dict_apply_type = [];

								$http(
										{
											method : 'POST',
											url : AppConfig.PORTALIPHOST
													+ '/lis_c/dic/get_apply_type',
											params : {
												dmlb : 14
											}
										})
										.success(
												function(result, status,
														headers, config) {
													layer.closeAll();
													if (result.code == 0) {
														$scope.dict_apply_type = result.data;
													} else {
														layer.msg(result.msg);
													}

												}).error(
												function(data, status, headers,
														config) {
													layer.closeAll();
													layer.msg(status);
												});

								$scope.dict_sample_status = [];

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

								$scope.dict_sample_type = [];

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
														// $scope.autoComplete2
														// = new
														// AutoComplete('select2',
														// 'auto2',
														// $scope.dict_sample_type);
													} else {
														layer.msg(result.msg);
													}

												}).error(
												function(data, status, headers,
														config) {
													layer.closeAll();
													layer.msg(status);
												});

								// 组合
								$scope.addComb = function(data) {
									var combStr = data.zhid + "," + data.zhmc
									if (!isInArray3($scope.apply.zcombs,
											combStr)
											&& !isInArray3($scope.apply.combs,
													combStr)) {
										$scope.apply.combs.push(combStr)
									} else {
										layer.msg("该项已存在");
									}
								}
								$scope.removeComb = function(comb) {
									var index = $scope.apply.combs
											.indexOf(comb);
									if (index > -1) {
										$scope.apply.combs.splice(index, 1);
									}

								}

								$scope.removeZComb = function(comb) {
									if(!$scope.addGroupBtn){
										return
									}
									
									var index = $scope.apply.zcombs
											.indexOf(comb);
									if (index > -1) {
										$scope.apply.zcombs.splice(index, 1);
									}

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
									$scope.get();
								};
								$scope.dict_combination = [];
								$scope.search = {
									comb_search_name : ""
								};

								$scope.get_combination = function() {
									$http(
											{
												method : 'POST',
												url : AppConfig.PORTALIPHOST
														+ '/lis_c/dic/get_combination',
												params : {
													// 'agency':'32110010',
													// 'group':'1',
													'combSearchName' : $scope.search.comb_search_name,
													'condition' : $scope.apply.comb_condition,
													'comb_category_id' : $scope.apply.comb_category,
													pageSize : AppConfig.pageSize,
													pageIndex : $scope.page.currentPage
												}
											})
											.success(
													function(result, status,
															headers, config) {
														layer.closeAll();
														if (result.code == 0) {
															if (result.data.rows[0]) {
																$scope.dict_combination = result.data.rows;
																// $scope.autoComplete
																// = new
																// AutoComplete('select1',
																// 'auto',
																// $scope.dict_combination);
															} else {
																$scope.dict_combination = [];
																layer
																		.msg("没有记录");
															}
															$scope.page.totalCount = result.data.total;
															$scope.page.totalPages = Math
																	.ceil($scope.page.totalCount
																			/ $scope.page.rows);
														} else {
															layer
																	.msg(result.msg);
														}

													}).error(
													function(data, status,
															headers, config) {
														layer.closeAll();
														layer.msg(status);
													});

								}

								$scope.go = function(page) {
									if (page >= 1
											&& page <= $scope.page.totalPages)
										$scope.page.currentPage = page;
									$scope.get_combination();
								};

								$scope.reset_comb = function() {
									$scope.search.comb_search_name = "";
									$scope.apply.combs = [];
								}

								$scope.append = function() {
									for (var i = 0; i < $scope.apply.combs.length; i++) {
										$scope.apply.zcombs
												.push($scope.apply.combs[i])
									}
									$scope.search.comb_search_name = "";
									$scope.apply.combs = [];
									$("#alert1").modal('hide');
								}

								$scope.myCombKeyup = function(e) {
									var keycode = window.event ? e.keyCode
											: e.which;
									if (keycode == 13) {
										$scope.get_combination();
									}
								};

								$scope.dict_comb_category = [];
								$http(
										{
											method : 'POST',
											url : AppConfig.PORTALIPHOST
													+ '/lis_c/dic/get_comb_category',
											params : {
												'group' : '1'
											}
										})
										.success(
												function(result, status,
														headers, config) {
													layer.closeAll();
													if (result.code == 0) {
														$scope.dict_comb_category = result.data;
													} else {
														layer.msg(result.msg);
													}

												}).error(
												function(data, status, headers,
														config) {
													layer.closeAll();
													layer.msg(status);
												});

								$scope.change_category = function() {
									$scope.get_combination()
								}
								// 按钮操作

								$scope.updateStatus = function(status) {
									g_showLoading();
									$http(
											{
												method : 'POST',
												url : AppConfig.PORTALIPHOST
														+ '/lis_c/apply/update_status',
												params : {
													sqid : $scope.apply.sqid,
													sqdzt : status,
													autodone:$scope.autodone,
													splitRule:$scope.splitRule,
												}
											})
											.success(
													function(result, status,
															headers, config) {
														layer.closeAll();
														if (result.code == 0) {
															$scope.search.send_unit = $scope.apply.send_unit
															$scope.init()
															// $timeout(function
															// () {
															// $scope.init();
															// },1000)

														} else {
															layer
																	.msg(result.msg);
														}
													}).error(
													function(data, status,
															headers, config) {
														layer.closeAll();
														layer.msg(status);
													});

								}

								$scope.addObj = function() {
									g_showLoading();

									if ($scope.apply.zcombs.length < 1) {
										layer.msg("请添加组合");
										return;
									}

									// 日期的正则表达式
//									var reg = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
//									var regExp = new RegExp(reg);
//									if (!regExp.test($("#nowSqdDate").val())) {
//										layer.msg("申请时间格式不正确，正确格式为：2018-01-01");
//										return;
//									}
//									if ($("#csrqDate").val()) {
//										var reg = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
//										var regExp = new RegExp(reg);
//										if (!regExp.test($("#csrqDate").val())) {
//											layer
//													.msg("出生日期格式不正确，正确格式为：2018-01-01");
//											return;
//										}
//										$scope.apply.csrq = "{0}".signMix($(
//												"#csrqDate").val());
//
//									}

									$http(
											{
												method : 'POST',
												url : AppConfig.PORTALIPHOST
														+ '/lis_c/apply/add',
												params : {
													sqid : $scope.apply.sqid,
													send_unit : $scope.apply.send_unit, // Y
													wltm : $scope.apply.wltm,
													yntm : $scope.apply.yntm,
													patient_type : $scope.apply.patient_type,// Y
													yepb : $scope.apply.yepb,
													mzhm : $scope.apply.mzhm,
													brid : $scope.apply.brid,
													brxm : $scope.apply.brxm,// Y
													sfid : $scope.apply.sfid,
													sex_type : $scope.apply.sex_type,
													age : $scope.apply.age,
													age_unit : $scope.apply.age_unit,
													csrq : $scope.apply.csrq,
													apply_type : $scope.apply.apply_type,// Y
													sample_type : $scope.apply.sample_type,// Y
													sample_status : $scope.apply.sample_status,// Y
													sqks : $scope.apply.sqks,
													sqbq : $scope.apply.sqbq,
													bedNo : $scope.apply.bedNo,
													sqys : $scope.apply.sqys,
													ynzd : $scope.apply.ynzd,
													sqsj : $scope.apply.sqsj,
													comment : $scope.apply.comment,
													cellphone : $scope.apply.cellphone,

													zcombs : $scope.apply.zcombs

												}
											})
											.success(
													function(result, status,
															headers, config) {
														layer.closeAll();
														if (result.code == 0) {
															// layer.msg("保存成功");
															// sleep(500000)
															// location.reload();
															// $scope.init();
															$scope.search.send_unit = $scope.apply.send_unit
															$scope.initForm();
															$scope.queryObj(true);
															
															// $timeout(function
															// () {
															// $scope.init();
															// },1000)

														} else {
															layer
																	.msg(result.msg);
														}
													}).error(
													function(data, status,
															headers, config) {
														layer.closeAll();
														layer.msg(status);
													});

								}
								$scope.newObj = function() {
									$scope.init();
								}

								// search 搜索

								$scope.search = {

								}
								$scope.getObj = function(item, i) {
									$scope.focus = i

									// 改样本性状
//									var obj_sample_status = getJsonObj(
//											$scope.dict_sample_status, "id",
//											item.ybxz)
//									$scope.apply.sample_status = obj_sample_status.id;
//									$scope.apply.sample_status_name = obj_sample_status.name
//									+ "," + obj_sample_status.id									
									
									$scope.apply.sample_status_name = item.ybxzName+","+item.ybxz;
//									$('#select2-chosen-4').html(
//											$scope.apply.sample_status_name);
									// alert($('#select2-chosen-4').html())
									
									
									

									// 改样本类型
//									var obj_sample_type = getJsonObj(
//											$scope.dict_sample_type, "id",
//											item.yblx)
//									$scope.apply.sample_type = obj_sample_type.id;
//									$scope.apply.sample_type_name = obj_sample_type.name
//											+ "," + obj_sample_type.id
											
									$scope.apply.sample_type_name = item.yblxName+","+item.yblx;		
//									$('#select2-chosen-2').html(
//											$scope.apply.sample_type_name);
									$(".inputName:contains('样本性状')").parent().find(".select2-chosen").html($scope.apply.sample_status_name);
									$(".inputName:contains('样本类型')").parent().find(".select2-chosen").html($scope.apply.sample_type_name);	

									$scope.apply.sqid = item.sqid;
									$scope.apply.send_unit = item.sjdw + ","
											+ item.sjdwmc
									$scope.apply.wltm = item.wltm
									$scope.apply.yntm = item.yntm
									$scope.apply.patient_type = "" + item.brlx
									if (item.yepb == "1") {
										$scope.apply.yepb = true
									} else {
										$scope.apply.yepb = false
									}
									$scope.apply.mzhm = item.mzhm
									$scope.apply.brid = item.brid
									$scope.apply.brxm = item.brxm
									$scope.apply.sfid = item.sfid
									$scope.apply.sex_type = "" + item.sex
									$scope.apply.age = item.age
									$scope.apply.age_unit = "" + item.nldw
									
									$scope.apply.csrq =new Date(item.csrq)
									$scope.apply.apply_type = "" + item.sqms
									$scope.apply.sqks = item.sqks
									$scope.apply.sqbq = item.sqbq
									$scope.apply.bedNo = item.bedNo
									$scope.apply.sqys = item.sqys
									$scope.apply.ynzd = item.ynzd


									$scope.apply.comment = item.bz
									$scope.apply.cellphone = item.cellphone
									$scope.apply.sqsj = new Date(item.sqsj)
									$scope.apply.zcombs = item.sqdmx
									$scope.apply.sqdzt = item.sqdzt
									
									// $scope.search.apply_sqdzt=$scope.apply.sqdzt;
									
									$scope.btnRule();
									

								}
                                $scope.search.search_list=[]
                                
                                
								$scope.queryObj = function(flag) {
									if(!flag){
										$scope.focus = "-1"
									}
									
									g_showLoading();

									// 日期的正则表达式
//									var reg = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
//									var regExp = new RegExp(reg);
//									if (!regExp.test($("#startDate").val())) {
//										layer.msg("日期格式1不正确，正确格式为：2018-01-01");
//										return;
//									}
//									if (!regExp.test($("#endDate").val())) {
//										layer.msg("日期格式2不正确，正确格式为：2018-01-01");
//										return;
//									}

//									$scope.search.strDStart = "{0}".signMix($(
//											"#startDate").val());
//									$scope.search.strDEnd = "{0}".signMix($(
//											"#endDate").val());

									$http(
											{
												method : 'POST',
												url : AppConfig.PORTALIPHOST
														+ '/lis_c/apply/query_apply',
												params : {
													strDStart : $scope.search.strDStart,
													strDEnd : $scope.search.strDEnd,
													sqdzt : $scope.search.apply_sqdzt,
													sjdw : $scope.search.send_unit,
//													condition : $scope.search.condition,
//													conditionValue : $scope.search.conditionValue,
												}
											})
											.success(
													function(result, status,
															headers, config) {
														layer.closeAll();
														console.log(result)
														if (result.code == 0) {
															if (result.data[0]) {
																$scope.search.search_list = result.data;
																// layer.msg("查询成功");

															} else {
																$scope.search.search_list = [];
																layer
																		.msg("没有记录");
															}

														} else {
															layer
																	.msg(result.msg);
														}

													}).error(
													function(data, status,
															headers, config) {

														layer.closeAll();
														layer.msg(status);

													});
								}
                                
								$scope.queryApplyKeyup = function(e) {
									var keycode = window.event ? e.keyCode
											: e.which;
									if (keycode == 13) {
										$scope.queryObj2();
									}
								};
								
								$scope.queryObj2 = function() {
//									if(isEmpty($scope.search.conditionValue)){
//										layer.msg("过滤条件不能为空");
//										return false;
//									}
									$scope.focus = "-1"
									g_showLoading();
									


									$http(
											{
												method : 'POST',
												url : AppConfig.PORTALIPHOST
														+ '/lis_c/apply/query_apply2',
												params : {
//													strDStart : $scope.search.strDStart,
//													strDEnd : $scope.search.strDEnd,
//													sqdzt : $scope.search.apply_sqdzt,
//													sjdw : $scope.search.send_unit,
													condition : $scope.search.condition,
													conditionValue : $scope.search.conditionValue,
												}
											})
											.success(
													function(result, status,
															headers, config) {
														layer.closeAll();
														console.log(result)
														if (result.code == 0) {
															if (result.data[0]) {
																$scope.search.search_list = result.data;
																// layer.msg("查询成功");
														
															} else {
																$scope.search.search_list = [];
																layer
																		.msg("没有记录");
															}

														} else {
															layer
																	.msg(result.msg);
														}

													}).error(
													function(data, status,
															headers, config) {

														layer.closeAll();
														layer.msg(status);

													});
								}								

								$scope.reset = function() {
									$scope.search.apply_sqdzt="";
									$scope.search.send_unit="";
									$scope.search.apply_sqdzt = "0";
									$scope.search.condition = "0";
									$scope.search.conditionValue = "";
									var curDate = new Date();
									var preDate = new Date(curDate.getTime() - 24*60*60*1000); 
									var nextDate = new Date(curDate.getTime() + 24*60*60*1000); 
									$scope.search.strDStart=curDate
									$scope.search.strDEnd=nextDate



								}

								// 初始化
								$scope.autodone=false;
								$scope.splitRule=false;
								$scope.printCode=false;
								$scope.setBtn = function() {
									$('#savebtn').removeClass('itemClick')
											.addClass('btn').attr("disabled",
													true);
									$('#btn1').removeClass('itemClick')
											.addClass('btn').attr("disabled",
													true);
									$('#btn2').removeClass('itemClick')
											.addClass('btn').attr("disabled",
													true);
									$('#btn3').removeClass('itemClick')
											.addClass('btn').attr("disabled",
													true);
									$('#cancelbtn1').removeClass('itemClick')
											.addClass('btn').attr("disabled",
													true);
									$('#cancelbtn2').removeClass('itemClick')
											.addClass('btn').attr("disabled",
													true);
									$('#cancelbtn3').removeClass('itemClick')
											.addClass('btn').attr("disabled",
													true);

								}
								$scope.apply.send_unit = ""
								$scope.addGroupBtn=true
								$scope.initForm = function() {

									// 初始化时间控件
									
									var curDate = new Date();


									$("#nowSqdDate").val(curDate)
									
									var curDate = new Date();
									var preDate = new Date(curDate.getTime() - 24*60*60*1000); //前一天
									var nextDate = new Date(curDate.getTime() + 24*60*60*1000); //后一天
									
									
									$scope.apply = {
										combs : [],
										zcombs : [],
										send_unit : $scope.apply.send_unit,
										// send_unit : "32110002,镇江市第四人民医院",//
										// 32110008
										// 查不到会有空
										patient_type : "1",
										sex_type : "",
										age_unit : "岁",
										apply_type : "1",
										sample_status : "1",
										sample_status_name : "正常,1",
										sample_type : "67",
										sample_type_name : "血清,67",
										comb_category : "999",
										comb_condition : "0",
										sqsj:curDate,

										sqid : "",

									}
									

									// 记忆送检单位
									// alert($scope.apply.send_unit)
									if(!isEmpty($scope.apply.send_unit)){
										$scope.search.send_unit = $scope.apply.send_unit
									}
									
						            $('#inputDiv').find('input,textarea,select').attr('readonly',false).attr('disabled',false)
                                    $scope.addGroupBtn=true;
									

								}

								$scope.initSearch = function() {
									var curDate = new Date();
									var preDate = new Date(curDate.getTime() - 24*60*60*1000); //前一天
									var nextDate = new Date(curDate.getTime() + 24*60*60*1000); //后一天
									$scope.search = {
										send_unit : "",
										apply_sqdzt : "0",
										condition : "0",
										conditionValue : "",
										strDStart : curDate,
										strDEnd : nextDate,
										search_list : ""
									}

								}

								$scope.init = function() {

									$scope.initSearch()
									$scope.initForm();
									$scope.queryObj();
									$scope.get_combination();

									// //两种方法设置disabled属性
									// $('#areaSelect').attr("disabled",true);
									// $('#areaSelect').attr("disabled","disabled");
									//
									// //三种方法移除disabled属性
									// $('#areaSelect').attr("disabled",false);
									// $('#areaSelect').removeAttr("disabled");
									// $('#areaSelect').attr("disabled","");
									$scope.setBtn()

								}
								$scope.init();
// $('form').find('input,textarea,select').not('这里代表需要改的元素的查找').attr('readonly',true)
								

							    
							    
								
								$scope.btn2Rule=function(){
									if ($scope.search.apply_sqdzt == "0" ) {
										if (!$scope.apply_form.$invalid) {
											if ($scope.apply.sqid != "") {
												$('#btn1').removeClass(
														'btn').addClass(
														'itemClick').attr(
														"disabled", false)
												} else {
													$('#btn1').removeClass(
															'itemClick')
															.addClass('btn')
															.attr("disabled",
																	true);
												}
											$('#savebtn').removeClass(
											'btn').addClass(
											'itemClick').attr("disabled",
											false);
											
										}else{
											
											$('#savebtn').removeClass(
											'itemClick').addClass(
											'btn').attr("disabled",
											true);
											$('#btn1').removeClass(
													'itemClick').addClass(
													'btn').attr("disabled",
													true);
											
											
										}
										
									}
									
									
									if ($scope.search.apply_sqdzt == "-1" ) {
										if (!$scope.apply_form.$invalid) {
											$('#savebtn').removeClass(
											'btn').addClass(
											'itemClick').attr("disabled",
											false);
										}else{
											
											$('#savebtn').removeClass(
											'itemClick').addClass(
											'btn').attr("disabled",
											true);
											
										}
										
									}									
									
								}
								
								$scope.btnRule=function(){
									$scope.setBtn()

									
									if ($scope.apply.sqdzt == "0" || $scope.apply.sqdzt == "-1" ) {
							            $('#inputDiv').find('input,textarea,select').attr('readonly',false).attr('disabled',false)
                                        $scope.addGroupBtn=true;
									}else{
							            $('#inputDiv').find('input,textarea,select').attr('readonly',true).attr('disabled','disabled')
                                        $scope.addGroupBtn=false;
									}
									


									if ($scope.apply.sqdzt == "0") {

										if ($scope.apply_form.$invalid) {
											$('#savebtn')
													.removeClass(
															'itemClick')
													.addClass(
															'btn')
													.attr(
															"disabled",
															true);
											$('#btn1')
													.removeClass(
															'itemClick')
													.addClass(
															'btn')
													.attr(
															"disabled",
															true);
										} else {
											$('#savebtn')
													.removeClass(
															'btn')
													.addClass(
															'itemClick')
													.attr(
															"disabled",
															false)
											if ($scope.apply.sqid != "") {
												$('#btn1')
														.removeClass(
																'btn')
														.addClass(
																'itemClick')
														.attr(
																"disabled",
																false)
											} else {
												$('#btn1')
														.removeClass(
																'itemClick')
														.addClass(
																'btn')
														.attr(
																"disabled",
																true);
											}
										}

									} else if ($scope.apply.sqdzt == "1") {

										$('#btn3')
												.removeClass(
														'btn')
												.addClass(
														'itemClick')
												.attr(
														"disabled",
														false);
										$('#cancelbtn1')
												.removeClass(
														'btn')
												.addClass(
														'itemClick')
												.attr(
														"disabled",
														false);
										
										$('#btn2')
											.removeClass(
													'btn')
											.addClass(
													'itemClick')
											.attr(
													"disabled",
													false);
									} else if ($scope.apply.sqdzt == "-1") {
//                                        必须到保存
// $('#savebtn')
// .removeClass(
// 'btn')
// .addClass(
// 'itemClick')
// .attr(
// "disabled",
// true);
										
										
									} else if ($scope.apply.sqdzt == "2") {
										$('#cancelbtn2')
												.removeClass(
														'btn')
												.addClass(
														'itemClick')
												.attr(
														"disabled",
														false);
									} else if ($scope.apply.sqdzt == "-2") {
	
												$('#btn1')
														.removeClass(
																'btn')
														.addClass(
																'itemClick')
														.attr(
																"disabled",
																false)
									} else if ($scope.apply.sqdzt == "3") {
										$('#cancelbtn3')
												.removeClass(
														'btn')
												.addClass(
														'itemClick')
												.attr(
														"disabled",
														false)
									} else if ($scope.apply.sqdzt == "-3") {
										
										
										$('#btn3')
												.removeClass(
														'btn')
												.addClass(
														'itemClick')
												.attr(
														"disabled",
														false)
														
										$('#cancelbtn1')
												.removeClass(
														'btn')
												.addClass(
														'itemClick')
												.attr(
														"disabled",
														false)				
									}
									
									
								}
								
//								$scope.queryObj3=function(){
//									$scope.initForm();
//									$scope.queryObj();
//								}
								
								$scope.batchProcess=function(status){
									$scope.batchProcessStatus=status;
									$('#confirmModel2').modal('show');	
								}
								
								$scope.urlSubmit=function(){
									var ids=[]
	                                for(var i=0;i<$scope.search.search_list.length;i++){
	                                	ids.push($scope.search.search_list[i].sqid)
	                                	
	                                }
	                                g_showLoading();
									$http(
											{
												method : 'POST',
												url : AppConfig.PORTALIPHOST
														+ '/lis_c/apply/batch_process',
												params : {
													ids:ids,
													sqdzt:$scope.batchProcessStatus,
													splitRule:$scope.splitRule,
												}
											})
											.success(
													function(result, status,
															headers, config) {
														layer.closeAll();
														console.log(result)
														if (result.code == 0) {
															$scope.init()

														} else {
															layer
																	.msg(result.msg);
														}

													}).error(
													function(data, status,
															headers, config) {

														layer.closeAll();
														layer.msg(status);

													});									
									
									
								}

								
								$scope.$watch('apply_form.$invalid',
										function() {

										$scope.btn2Rule();
											
										})

								$scope.$watch('apply.sqid', function() {
									$scope.btn2Rule();
								})


												

								// websocket
								var stompClient = null;
								$scope.listen_disable = false;
								$scope.connect = function() {

									$scope.listen_disable = true;
									var socket = new SockJS(
											'/endpoint-websocket'); // 连接上端点(基站)
									stompClient = Stomp.over(socket); // 用stom进行包装，规范协议
									stompClient
											.connect(
													{},
													function(frame) {

														console
																.log('Connected: '
																		+ frame);
														stompClient
																.subscribe(
																		'/topic/apply_chat',
																		function(
																				result) {
																			console
																					.info("获得消息")
																			console
																					.info(result)
																			alert(JSON
																					.parse(result.body));
																		});

														// 点对点
														// var from =
														// $("#from").val();
														// stompClient.subscribe('/chat/single/'+from,
														// function (result) {
														// showContent(JSON.parse(result.body));
														// });

													});

								}

								$scope.disconnect = function() {
									if (stompClient !== null) {
										stompClient.disconnect();
									}
									$scope.listen_disable = false;
									console.log("Disconnected");

								}

								$scope.send = function() {
									stompClient.send("/app/apply/chat", {},
											'123');

								}

							} ]);

	module.directive('card', function() {
		return {
			require : 'ngModel',
			link : function(scope, elm, attrs, LisApplyController) {
				LisApplyController.$parsers.push(function(viewValue) {
					if (idCardNoUtil.checkIdCardNo(viewValue)) {
						LisApplyController.$setValidity('card', true);
						scope.apply.sex_type = Getsex(viewValue);
						scope.apply.age = GetAge(viewValue);
						scope.apply.age_unit = "岁";
						scope.apply.csrq=GetBirthdayDate(viewValue);

						// var temp=""
						// var temp2=""
						// var objlist=scope.zuhe_list
						// for(var v in objlist ){
						// var obj=objlist[v]
						// console.log(sex)
						// if(sex=='1'){
						// if(obj.mhdbh==1){
						// temp=temp+"+"+obj['zhmc'];
						// temp2=temp2+"+"+obj['zhid']+":"+obj['zhmc'];
						// }
						// }else if(sex=='2'){
						// if(obj.fhdbh==1){
						// temp=temp+"+"+obj['zhmc'];
						// temp2=temp2+"+"+obj['zhid']+":"+obj['zhmc'];
						// }
						// }else{
						// console.log(11111111)
						// temp=temp+"+"+obj['zhmc'];
						// temp2=temp2+"+"+obj['zhid']+":"+obj['zhmc'];
						// }
						//	
						// }
						// temp=temp.substr(1)
						// temp2=temp2.substr(1)
						// scope.zuhe_str=temp
						// scope.zhid=temp2
					} else {
						// scope.zuhe_str=scope.default_zuhe
						// scope.zhid=scope.default_zhid
						LisApplyController.$setValidity('card', false);
					}
					return viewValue

				})

			}

		}

	})

})(angular);


