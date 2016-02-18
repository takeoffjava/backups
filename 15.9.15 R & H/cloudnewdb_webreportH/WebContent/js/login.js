var username="";
function login(){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'login';
	username=document.getElementById("username").value;
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
	if((username=="Apollo" && password=="Apollo12") || (username=="apollo" && password=="apollo12")){
		username="admin";
		password="admin";
	var method = "POST";
	objectForPost = {				
			Username : username,
			Password : password
	};
	jQAjaxCallForAccountJSON(servletPath, method, contentType,
			objectForPost, 'loginCallback');
}
else{
	noty( {"text":"Invalid Credentials","layout":"bottomRight","type":"error"});
	return false;
}
}
function loginCallback(resultobject){
		localStorage.setItem("loginsession",1);
		localStorage.setItem("username","Apollo");
		window.location.href="home";
}
function topname(){
	document.getElementById("username").innerHTML ="Hi "+localStorage.getItem("username");
}