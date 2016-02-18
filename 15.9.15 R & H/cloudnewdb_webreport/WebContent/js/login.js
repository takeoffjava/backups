function login(){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'login';
	var username=document.getElementById("username").value;
	var password=document.getElementById("password").value;
	if(username=="")
		{
		noty( {"text":"Username should not be empty... ","layout":"bottomRight","type":"error"});
		//document.getElementById("errortext").innerHTML="<span style='color:red'>Username should not be empty...</span>";
		return false;
		}else if(password==""){
			noty( {"text":"Password should not be empty...","layout":"bottomRight","type":"error"});
		//	document.getElementById("errortext").innerHTML="<span style='color:red'>Password should not be empty...</span>";
			return false;
		}
	var method = "POST";
		objectForPost = {				
				Username : username,
				Password : password
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'loginCallback');
}
function loginCallback(resultobject){
	if(resultobject.status===0){
		noty( {"text":"Invalid Credentials","layout":"bottomRight","type":"error"});
		return false;
	}else{
		localStorage.setItem("loginsession",1);
		localStorage.setItem("username",resultobject.response[0].username);
		localStorage.setItem("resultobject", JSON.stringify(resultobject));
		//setuserid();
		window.location.href="home";
		
	}
}
function topname(){
	document.getElementById("username").innerHTML ="Hi "+localStorage.getItem("username");
}
function setuserid(userid){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'getUserIDForSession/'+userid;	
	var method = "POST";
		objectForPost = {};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'setuseridCallback');
}
function setuseridCallback(resultobject){
}