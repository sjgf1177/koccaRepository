//----------------------------------------------
//FileButton v1.0 Source By Bermann
//dobermann75@gmail.com
//----------------------------------------------

function FileButton(imageswap, imagesrc)
{
	this.imageswap = imageswap;
	this.imagesrc = imagesrc;
	this.swapnode = document.getElementsByTagName("INPUT");
	this.filebox = document.createElement("DIV");
	this.filebox.style.width = "0px";
	this.filebox.style.height = "0px";
	this.filebox.style.position = "relative";
	this.filebox.style.overflow = "hidden";
}

FileButton.prototype =
{
	getBounds: function (tag) {
		var ret = new Object();
		if (tag.getBoundingClientRect) {
			var rect = tag.getBoundingClientRect();
			ret.left = rect.left + (document.documentElement.scrollLeft || document.body.scrollLeft);
			ret.top = rect.top + (document.documentElement.scrollTop || document.body.scrollTop);
			ret.width = rect.right - rect.left;
			ret.height = rect.bottom - rect.top;
		} else if (document.getBoxObjectFor) {
			var box = document.getBoxObjectFor(tag);
			ret.left = box.x;
			ret.top = box.y;
			ret.width = box.width;
			ret.height = box.height;
		} else {
			ret.left = tag.offsetLeft;
			ret.top = tag.offsetTop;
			ret.width = tag.offsetWidth;
			ret.height = tag.offsetHeight;
		}
		if (navigator.appName == "Microsoft Internet Explorer") {
			ret.left -= 2;
			ret.top -= 2;
		}
		return ret;
	},
	swap: function (swapnode) {
		var This = this;
		var filebox = this.filebox.cloneNode(true);
		var image = new Image();
		var imageurl = swapnode.getAttribute(this.imagesrc);
		swapnode.parentNode.insertBefore(filebox, swapnode);
		filebox.appendChild(swapnode);
		filebox.appendChild(image);
		filebox.style.width = this.getBounds(swapnode).width + "px";
		filebox.style.height = this.getBounds(swapnode).height + "px";
		image.onload = function () {
			this.parentNode.style.backgroundImage = "url(" + this.previousSibling.getAttribute(This.imagesrc) + ")";
			this.parentNode.style.backgroundRepeat = "no-repeat";
			this.parentNode.style.width = this.width + "px";
			this.parentNode.style.height = this.height + "px";
			this.previousSibling.style.filter = "alpha(opacity=0)";
			this.previousSibling.style.opacity = 0;
			this.previousSibling.style.fontSize = (Math.max(this.width, this.height) + 30) + "px";
			this.previousSibling.style.position = "relative";
			this.previousSibling.style.height = (this.height + 30) + "px";
			this.previousSibling.style.left = (0 - This.getBounds(this.previousSibling).width) + "px";
			this.previousSibling.style.top = "-10px";
			
			var parentnode = this.parentNode;
			var divForGetHTML = filebox.cloneNode(false);
			divForGetHTML.appendChild(this.previousSibling);
			parentnode.innerHTML = divForGetHTML.innerHTML;

			var fileright = This.getBounds(parentnode.firstChild).left + This.getBounds(parentnode.firstChild).width;
			var boxright = This.getBounds(parentnode).left + This.getBounds(parentnode).width;
			if (fileright < boxright) {
				parentnode.firstChild.style.left = (This.getBounds(parentnode.firstChild).left + boxright) + "px";
			}

			divForGetHTML = null;
			parentnode.firstChild.setAttribute(This.imageswap, "swapped");
		}
		image.src = swapnode.getAttribute(this.imagesrc);
	},
	write: function (source) {
		var spanid = "spanforFileButton" + new Date().getTime();
		document.write('<span id="' + spanid + '">' + source + '</span>');
		var span = document.getElementById(spanid);
		this.swap(span.firstChild);
		span.parentNode.insertBefore(span.firstChild, span);
		span = null;
	},
	run: function () {
		for (var i = 0; i < this.swapnode.length; i++) {
			var swapnode = this.swapnode[i];
			if (swapnode.type == "file" && swapnode.getAttribute(this.imageswap) == "true") {
				this.swap(swapnode);
			}
		}
	}
}