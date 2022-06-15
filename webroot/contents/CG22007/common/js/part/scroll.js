function setScroll(target, wrap, list, type){
  this.$scroll = target;
  this.$bar = null;
  this.$wrap = wrap;
  this.$list = list;
  this.$type = type;
  this.$bar = $('<div class="bar"></div>');
  this.$scroll.append(this.$bar);
  // this.fnScrollInit();
}



setScroll.prototype.fnScrollInit = function(){
  var _this = this;

  this.$bar.css('top',"0px");
  this.$list.css('top',"0px");

  var barHeight =  this.$wrap.css("height").split("px")[0] *  _this.$scroll.css("height").split("px")[0] / this.$list.css("height").split("px")[0];
  _this.$bar.css("height",barHeight)

  _this.$bar.draggable({axis:'y',containment:'parent'});
  _this.$bar.on("drag",function(event, ui){
    _this.fnScrollMove();
  });

  if(_this.$bar.css("height").split("px")[0] *1 >= _this.$scroll.css("height").split("px")[0] *1  ) _this.$scroll.hide();
  else _this.$scroll.show();


  this.$wrap.on('mousewheel DOMMouseScroll', function(e) {
    var totalHeight = _this.$wrap.css("height").split("px")[0] *1;
    if(_this.$list.css("height").split("px")[0] *1 <= totalHeight) return;
     var E = e.originalEvent;
     delta = 0;
     if(E.detail) delta = E.detail * -4;
     else delta = E.wheelDelta / 10;


     delta = _this.$list.css("top").split("px")[0] *1 + delta;
      console.log(delta)
     var min = 0;
     var max = _this.$wrap.css("height").split("px")[0] *1 - _this.$list.css("height").split("px")[0] *1;
     if(delta >= min) delta = min;
     if(delta <= max) delta = max;
     var scrollHeight = _this.$scroll.css("height").split("px")[0] *1;
     var barHeight = _this.$bar.css("height").split("px")[0] *1;
     _this.$list.css("top",delta);
     _this.$bar.css("top",-delta / (scrollHeight / barHeight));
  });

}


setScroll.prototype.fnScrollMove = function(){

  var _this = this;

  var scrollHeight = this.$scroll.css("height").split("px")[0] *1;
  var barTop = this.$bar.css("top").split("px")[0] *1;
  var barHeight = this.$bar.css("height").split("px")[0] *1;
  var totalHeight = this.$wrap.css("height").split("px")[0] *1;
  var pos = totalHeight  * barTop / -scrollHeight * (scrollHeight / barHeight);

  this.$list.css("top",pos);



}
