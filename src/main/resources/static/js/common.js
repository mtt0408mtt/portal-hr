//展示loading
function g_showLoading(){
    console.log("show...")
	var idx = layer.msg('处理中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: '0px', time:100000}) ;  
	return idx;
}
//salt
var g_passsword_salt="4q3w2e1r"
// 获取url参数
function g_getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) return unescape(r[2]);
	return null;
};
//设定时间格式化函数，使用new Date().format("yyyyMMddhhmmss");  
Date.prototype.format = function (format) {  
    var args = {  
        "M+": this.getMonth() + 1,  
        "d+": this.getDate(),  
        "h+": this.getHours(),  
        "m+": this.getMinutes(),  
        "s+": this.getSeconds(),  
    };  
    if (/(y+)/.test(format))  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    for (var i in args) {  
        var n = args[i];  
        if (new RegExp("(" + i + ")").test(format))  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));  
    }  
    return format;  
};

//删除列表的某一列
function deleteRow(tableId,rowsId) {
    var table = document.getElementById(tableId);
    var tableRows = table.rows;
    for(var i=0;i<tableRows.length;i++){
        tableRows[i].deleteCell(rowsId);
    }
}


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

function GetBirthdayDate(psidno) {
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
			+ birthdayno.substring(4, 5)+(parseInt(birthdayno.substring(5, 6))-1) + "-" + birthdayno.substring(6, 8)
	
	return new Date(birthdayno.substring(0, 4), birthdayno.substring(4, 5)+(parseInt(birthdayno.substring(5, 6))-1),birthdayno.substring(6, 8))
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


function getNextDay2(d) {
	var dd = new Date();
	dd.setDate(d + 1);
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
		nowTime : nowTime,
		now :now
	}

}

function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == ""){
        return true;
    }else{
        return false;
    }
}



function pad(num, n) {
	 return Array(n>num.length?(n-(''+num).length+1):0).join(0)+num;
	}


