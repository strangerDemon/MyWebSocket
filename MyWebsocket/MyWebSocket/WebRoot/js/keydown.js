/**
 * 鍵盤事件
 */
 function doKey(e) {
	var ev = e || window.event; //获取event对象   
	var obj = ev.target || ev.srcElement; //获取事件源   
	var t = obj.type || obj.getAttribute('type'); //获取事件源类型   
	if (ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea") {
		return false;
	}
	if (ev.keyCode == 13) {
		if(name==""&&obj.nodeName=="INPUT"){
			chat();
		}else{
			send();
		}
		return false;
	}
}
//禁止后退键 作用于Firefox、Opera   
document.onkeypress = doKey;
//禁止后退键  作用于IE、Chrome   
document.onkeydown = doKey;