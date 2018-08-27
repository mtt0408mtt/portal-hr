(function(angular) {
	'use strict';
	var module = angular.module('pm.hr_apply', [ 'ngRoute',
			'yum.services.http', 'ui.bootstrap', 'ngFileUpload' ]);
	module.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/hr_apply', {
			templateUrl : 'view/hr/apply/apply.html',
			controller : 'HrApplyController'
		});
	} ]);

	module
			.controller(
					'HrApplyController',
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
								
					            $(".mytab_change").unbind("click").bind("click",function(){
					            	$(this).parent().parent().find('li').removeClass("active");
					            	$(this).parent().parent().find('a').removeClass("active");
					            	$(this).parent().parent().parent().find('.tab-pane').removeClass("active");
					            	$(this).addClass("active");
					            	$(this).parent().addClass("active");
					            	var activepane=$(this).attr('aria-controls')
					            	$('#'+activepane).addClass("active");
					            	
					            })

								$scope.add = {}
								
								
								 $scope.dict_group = [
										{"name" : "天瑞精准医疗集团" ,"code":"1",
										  "agency" : [
										      {
										          "name" : "厂区1" ,
										          "id" : "1",
										          "department" : [{"name":"部门1","id":"1"},{"name":"部门2","id":"2"}]
										      },
										      {
										          "name" : "厂区2" ,
										          "id" : "2",
										          "department" : [{"name":"部门1","id":"1"},{"name":"部门2","id":"2"}]
										      }
										  ] 
										  
										}
                                ];
								
                               $scope.dict_duty=[{id:"1",name:"高级操作员工"},{id:"2",name:"中级操作员工"}]
                               $scope.dict_department=[{id:"1",name:"部门1"},{id:"2",name:"部门2"}]
                               $scope.dict_agency=[{id:"1",name:"厂区1"},{id:"2",name:"厂区2"}]	
                               
                               
                               $scope.doChange=function(){
									$('#alert1').modal('show');	
                            	   
                               }
                               
                               $scope.change={};
                               $scope.changes=[{date:"17-06-30",type:"升职",department:"厂区1部门1->厂区1部门1",duty:"领班-主管"},{date:"18-06-30",type:"调动",department:"厂区1部门1->厂区2部门2",duty:"主管->主管"}];
                               $scope.append=function(){
                            	   $scope.changes.push( $scope.change) 
                            	   
                            	   
                               }
                               




							} ]);

	module.directive('card', function() {
		return {
			require : 'ngModel',
			link : function(scope, elm, attrs, HrApplyController) {
			
				HrApplyController.$parsers.push(function(viewValue) {
					if (idCardNoUtil.checkIdCardNo(viewValue)) {
						HrApplyController.$setValidity('card', true);
						scope.add.sex = Getsex(viewValue);
						scope.add.age = GetAge(viewValue);
						$('#birthday').val(GetBirthday(viewValue))

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
						HrApplyController.$setValidity('card', false);
					}
					return viewValue

				})

			}

		}

	})

})(angular);

function isInArray3(arr, value) {
	if (arr.indexOf && typeof (arr.indexOf) == 'function') {
		var index = arr.indexOf(value);
		if (index >= 0) {
			return true;
		}
	}
	return false;
}

// https://www.cnblogs.com/papi/p/8629286.html
String.prototype.signMix = function() {
	if (arguments.length === 0)
		return this;
	var param = arguments[0], str = this;
	if (typeof (param) === 'object') {
		for ( var key in param)
			str = str.replace(new RegExp("\\{" + key + "\\}", "g"), param[key]);
		return str;
	} else {
		for (var i = 0; i < arguments.length; i++)
			str = str.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
		return str;
	}
}

function GetBirthday(psidno) {
	var birthdayno, birthdaytemp
	if (psidno.length == 18) {
		birthdayno = psidno.substring(6, 14)
	} else if (psidno.length == 15) {
		birthdaytemp = psidno.substring(6, 12)
		birthdayno = "19" + birthdaytemp
	} else {
		alert("错误的身份证号码，请核对！")
		return false
	}
	var birthday = birthdayno.substring(0, 4) + "-"
			+ birthdayno.substring(4, 6) + "-" + birthdayno.substring(6, 8)

	return birthday
}

function GetAge(identityCard) {
	var len = (identityCard + "").length;
	if (len == 0) {
		return 0;
	} else {
		if ((len != 15) && (len != 18))// 身份证号码只能为15位或18位其它不合法
		{
			return 0;
		}
	}
	var strBirthday = "";
	if (len == 18)// 处理18位的身份证号码从号码中得到生日和性别代码
	{
		strBirthday = identityCard.substr(6, 4) + "/"
				+ identityCard.substr(10, 2) + "/" + identityCard.substr(12, 2);
	}
	if (len == 15) {
		strBirthday = "19" + identityCard.substr(6, 2) + "/"
				+ identityCard.substr(8, 2) + "/" + identityCard.substr(10, 2);
	}
	// 时间字符串里，必须是“/”
	var birthDate = new Date(strBirthday);
	var nowDateTime = new Date();
	var age = nowDateTime.getFullYear() - birthDate.getFullYear();
	// 再考虑月、天的因素;.getMonth()获取的是从0开始的，这里进行比较，不需要加1
	if (nowDateTime.getMonth() < birthDate.getMonth()
			|| (nowDateTime.getMonth() == birthDate.getMonth() && nowDateTime
					.getDate() < birthDate.getDate())) {
		age--;
	}
	return age;
}

function CheckIdCard(idcard) {
	var Errors = new Array("验证通过!", "身份证号码位数不对!", "身份证号码出生日期超出范围或含有非法字符!",
			"身份证号码校验错误!", "身份证地区非法!");
	var area = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江",
		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏",
		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "新疆",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外"
	}

	var idcard, Y, JYM;
	var S, M;
	var idcard_array = new Array();
	idcard_array = idcard.split(""); // 地区检验

	if (area[parseInt(idcard.substr(0, 2))] == null)
		return Errors[4]; // 身份号码位数及格式检验

	switch (idcard.length) {
	case 15:

		if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0
				|| ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard
						.substr(6, 2)) + 1900) % 4 == 0)) {
			ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; // 测试出生日期的合法性

		} else {
			ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; // 测试出生日期的合法性

		}
		if (ereg.test(idcard))
			return Errors[0];
		else
			return Errors[2];

		break;
	case 18:
		// 18位身份号码检测
		// 出生日期的合法性检查
		// 闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
		// 平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))

		if (parseInt(idcard.substr(6, 4)) % 4 == 0
				|| (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard
						.substr(6, 4)) % 4 == 0)) {
			ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/; // 闰年出生日期的合法性正则表达式

		} else {
			ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/; // 平年出生日期的合法性正则表达式

		}
		if (ereg.test(idcard)) { // 测试出生日期的合法性
			// 计算校验位
			S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
					+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11]))
					* 9
					+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12]))
					* 10
					+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13]))
					* 5
					+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14]))
					* 8
					+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15]))
					* 4
					+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16]))
					* 2 + parseInt(idcard_array[7]) * 1
					+ parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9])
					* 3;
			Y = S % 11;
			M = "F";
			JYM = "10X98765432";
			M = JYM.substr(Y, 1); // 判断校验位

			if (M == idcard_array[17])
				return Errors[0]; // 检测ID的校验位

			else
				return Errors[3];
		} else
			return Errors[2];
		break;
	default:

		return Errors[1];
		break;
	}

}

// ----------------------------------------------------------
// 功能：根据身份证号获得性别
// 参数：身份证号 psidno
// 返回值：
// 性别
// ----------------------------------------------------------
function Getsex(psidno) {
	var sexno, sex
	if (psidno.length == 18) {
		sexno = psidno.substring(16, 17)
	} else if (psidno.length == 15) {
		sexno = psidno.substring(14, 15)
	} else {
		alert("错误的身份证号码，请核对！")
		return false
	}
	var tempid = sexno % 2;
	if (tempid == 0) {
		sex = '2'
	} else {
		sex = '1'
	}
	return sex
}

function sleep(numberMillis) {
	// 记录当前时间
	var now = new Date();
	// 设置未来的某个时间
	var exitTime = now.getTime() + numberMillis;
	while (true) {
		// 获取当前时间
		now = new Date();
		// 检查是否到了设置好的未来时间
		if (now.getTime() > exitTime)
			return;
	}
}

// 获取下一天
function getNextDate(dayStr) {
	var dd = new Date(dayStr);
	dd.setDate(dd.getDate() + 1);
	var y = dd.getFullYear();
	var m = (dd.getMonth() + 1) < 10 ? "0" + (dd.getMonth() + 1) : (dd
			.getMonth() + 1);// 获取当前月份的日期
	var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();
	return y + "-" + m + "-" + d;
};

function getJsonObj(jsonObj, key, value) {
	for (var i = 0; i < jsonObj.length; i++) {
		if (jsonObj[i][key] == value) {
			return jsonObj[i];
		}
	}
	return null;
}

Date.prototype.Format = function(formatStr) {
	var str = formatStr;
	var Week = [ '日', '一', '二', '三', '四', '五', '六' ];

	str = str.replace(/yyyy|YYYY/, this.getFullYear());
	str = str.replace(/yy|YY/,
			(this.getYear() % 100) > 9 ? (this.getYear() % 100).toString()
					: '0' + (this.getYear() % 100));

	str = str.replace(/MM/, this.getMonth() > 9 ? (this.getMonth() + 1)
			.toString() : '0' + (this.getMonth() + 1));
	str = str.replace(/M/g, (this.getMonth() + 1));

	str = str.replace(/w|W/g, Week[this.getDay()]);

	str = str.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString()
			: '0' + this.getDate());
	str = str.replace(/d|D/g, this.getDate());

	str = str.replace(/hh|HH/, this.getHours() > 9 ? this.getHours().toString()
			: '0' + this.getHours());
	str = str.replace(/h|H/g, this.getHours());
	str = str.replace(/mm/, this.getMinutes() > 9 ? this.getMinutes()
			.toString() : '0' + this.getMinutes());
	str = str.replace(/m/g, this.getMinutes());

	str = str.replace(/ss|SS/, this.getSeconds() > 9 ? this.getSeconds()
			.toString() : '0' + this.getSeconds());
	str = str.replace(/s|S/g, this.getSeconds());

	return str;
}

function returnDate() {
	now = new Date();
	nowYear = now.getFullYear(); // 年
	nowMonth = now.getMonth() + 1 < 10 ? "0" + (now.getMonth() + 1) : (now
			.getMonth() + 1); // 月
	nowDay = now.getDate() < 10 ? "0" + now.getDate() : now.getDate(); // 日期
	nowEndDay = (now.getDate() + 1) < 10 ? "0" + (now.getDate() + 1) : (now
			.getDate() + 1)
	nowHour = now.getHours() < 10 ? "0" + now.getHours() : now.getHours(); // 时
	nowMinute = now.getMinutes() < 10 ? "0" + now.getMinutes() : now
			.getMinutes(); // 分
	nowDate = nowYear + "-" + nowMonth + "-" + nowDay;
	nowEndDate = nowYear + "-" + nowMonth + "-" + nowEndDay;
	nowTime = nowHour + ":" + nowMinute;

	return {
		nowDate : nowDate,
		nowTime : nowTime

	}

}

function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == ""){
        return true;
    }else{
        return false;
    }
}
