function login(){
	$("#loginForm").validate({
        submitHandler:function(form){
             doLogin();
        }    
    });
}
function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == ""){
        return true;
    }else{
        return false;
    }
}

function doLogin(){
	
	
	var inputPass = $("#password").val();
	var salt = g_passsword_salt;
	var str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
	var password = md5(str);
/*	
	var group;
	var agency;
	var department;
	
    group=$("#one").val()
    agency=$("#two").val()
    department=$("#three").val()
	
    if(isEmpty(group) && isEmpty(agency) && isEmpty(department) ){
    	var area=$.cookie("area")
        if(isEmpty(area)){
        	layer.msg("请输入组织,机构,科室");
        	return;
        }
    	areas=area.split("aa")
       
        
        group=areas[0].replace('bb',',');
        agency=areas[1].replace('bb',',');
        department=areas[2].replace('bb',',');
        
        

    } */
    g_showLoading();
	
	$.ajax({
		url: "/login/do_login",
	    type: "POST",
	    data:{
	    	code:$("#code").val(),
	    	password: password,
//	    	group:group,
//	    	agency:agency,
//	    	department:department
	    },
	    success:function(data){
	    	layer.closeAll();
	    	if(data.code == 0){
	    		//layer.msg("成功");
	    		window.location.href="/index.htm";
	    	}else{
	    		layer.msg(data.msg);
	    	}
	    },
	    error:function(){
	    	layer.closeAll();
	    }
	});
}
/*
$(function(){
	

	 var group = [
//                  {"name" : "天瑞精准医疗集团" ,"code":"1",
//                      "agency" : [
//                          {
//                              "name" : "测试机构" ,
//                              "code" : "32110001",
//                              "department" : [{"name":"生化室","code":"SH001"}]
//                          }
//                      ]
//                  }
              ];
	 
	 $.ajax({
			url: "/login/group",
		    type: "POST",
		    data:{

		    },
		    success:function(result){
		    	layer.closeAll();
		    	result=JSON.parse(result)
		    	if(result.code == 0){
		    		group=result.data;
		    		console.log(group)
		    		init();
		    	}else{
		    		layer.msg(result.msg);
		    	}
		    	 
		    },
		    error:function(){
		    	layer.closeAll();
		    }
		});
	 
	 
           
              // 二级联动
              $("#two").change(function(){
                  var one_index = $("#one option:selected").index();
                  var two_index = $("#two option:selected").index();
                  var three = $("#three");
                  three.empty().append("<option value=''>请选择科室</option>");
           
                  if(two_index > 0){
                      var department = group[one_index-1].agency[two_index-1].department;
                      for(var i = 0 ; i < department.length ; i++){
                    	three.append("<option value="+department[i].id+","+department[i].code+">"+department[i].name+"</option>");  
                      }
                  }
              });
           
              // 一级联动
              $("#one").change(function(){
                  var one_index = $("#one option:selected").index();
                  var two = $("#two");
                      console.log(one_index)
                  two.empty().append("<option value=''>请选择机构</option>");
                  $("#three").empty().append("<option value=''>请选择科室</option>");//清除
           
                  if(one_index > 0){
                      var agency = group[one_index-1].agency;
                      console.log(group[one_index-1].agency)
                      for(var i = 0 ; i < agency.length ; i++){
                         two.append("<option value="+agency[i].id+","+agency[i].code+">"+agency[i].name+"</option>"); 
                      }
                  }
              });
              

               
             
              function init(){
                  for(var i = 0 ; i < group.length ; i++){
                	 $("#one").append("<option  value="+group[i].id+","+group[i].code+">"+group[i].name+"</option>");  
                  }
              }
              

                
                
});*/