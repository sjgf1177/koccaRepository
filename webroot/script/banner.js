/**
*@ 중요한부분은 div 부분의 높이(height) 값을 확실히 불러와야하고
*위치(top,left) 부분을 잘 맞춰야 함..
*@설명 : div트릭..  div안에 div을 넣어서 위로 위치조정해줌..appendchild로 없어진 부분을 맨끝에 추가..
*            계속 반복하게 됨.. 결국 롤링하는 효과.
*RollingBanner=function("div객체","배너 태그","배너 height","롤링 방향","속도","딜레이");
*당연히 딜레이가 속도보다 커야..표시가 남..
*2007년 1월 11일 목요일 ....by Dohyup
*/
RollingBanner=function(obj,tag,hei,wid,spd,rtype)
{
	this.ban_obj = document.getElementById(obj); //div객체
	this.ban_tag = this.ban_obj.getElementsByTagName(tag);
	this.ban_height = hei; //배너 or text 높이
	this.ban_width = wid; //가로
//	this.ban_direction = drt; // 방향을 결정함(div태그이므로 top,left)
	this.ban_speed = spd; //속도
	this.ban_delay = 2000; //딜레이는 고정시켜놨음
	this.ban_cnt = 0; //배너의 top,left 위치 를 결정  (0은 최상위,가장왼쪽)
	this.ban_timer;
	this.ban_rolling_type = rtype;
}

/*배너의 세로로 롤링함수*/
RollingBanner.prototype.doRolling=function()
{
	if(this.ban_rolling_type == "up" || this.ban_rolling_type == "down"){
		this.rollingY();
	}else if(this.ban_rolling_type == "left" || this.ban_rolling_type == "right") {
		this.rollingX();
	}
}

/*배너의 가로 롤링함수*/
RollingBanner.prototype.rollingX=function()
{
	obj=this;
	this.rollingTypeChecker(this.ban_rolling_type);
	if(this.ban_width > tmpcnt) {
		this.ban_obj.style.left = this.ban_cnt;
		this.ban_timer = setTimeout("obj.rollingX()",this.ban_speed);
	} else {
		this.ban_obj.appendChild(this.ban_tag[0]);
		this.ban_obj.style.left = 0;
		this.ban_timer = setTimeout("obj.rollingX()",2000);
		this.ban_cnt = 0;
	}
}

/*배너의 세로 롤링함수*/
RollingBanner.prototype.rollingY=function()
{
	obj = this; //객체참조
	this.rollingTypeChecker(this.ban_rolling_type);
	if(this.ban_height > tmpcnt) 	{
		this.ban_obj.style.top = this.ban_cnt;
		this.ban_timer = setTimeout("obj.rollingY()",this.ban_speed);
	} else	 {
		this.ban_obj.appendChild(this.ban_tag[0]);
		this.ban_obj.style.top = this.ban_cnt;
		this.ban_timer = setTimeout("obj.rollingY()",this.ban_delay);
		this.ban_cnt = 0;
	}
}

RollingBanner.prototype.stopRolling=function()
{
	clearTimeout(this.ban_timer);
}

/*배너의 방향을 결정하는 함수(left -> right, left <- right , top -> bottom, top <- bottom)*/
RollingBanner.prototype.rollingTypeChecker=function(rtype)
{
	if(this.ban_rolling_type == "right" || this.ban_rolling_type == "down") {
		this.ban_cnt++;
		tmpcnt = this.ban_cnt;
	}else if(this.ban_rolling_type == "left" || this.ban_rolling_type == "up") {
		this.ban_cnt--;
		tmpcnt = -this.ban_cnt;
	}else { //default
		this.ban_cnt++;
		tmpcnt = this.ban_cnt;
	}
}
