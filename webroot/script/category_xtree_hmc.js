/*----------------------------------------------------------------------------|
|                       Cross Browser Tree Widget 1.12                        |
|-----------------------------------------------------------------------------|
|                          Created by Emil A Eklund                           |
|                  (http://webfx.eae.net/contact.html#emil)                   |
|                      For WebFX (http://webfx.eae.net/)                      |
|-----------------------------------------------------------------------------|
| An object based tree widget,  emulating the one found in microsoft windows, |
| with persistence using cookies. Works in IE 5+, Mozilla and konqueror 3.    |
|-----------------------------------------------------------------------------|
|                   Copyright (c) 1999 - 2002 Emil A Eklund                   |
|-----------------------------------------------------------------------------|
| This software is provided "as is", without warranty of any kind, express or |
| implied, including  but not limited  to the warranties of  merchantability, |
| fitness for a particular purpose and noninfringement. In no event shall the |
| authors or  copyright  holders be  liable for any claim,  damages or  other |
| liability, whether  in an  action of  contract, tort  or otherwise, arising |
| from,  out of  or in  connection with  the software or  the  use  or  other |
| dealings in the software.                                                   |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| This  software is  available under the  three different licenses  mentioned |
| below.  To use this software you must chose, and qualify, for one of those. |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Non-Commercial License          http://webfx.eae.net/license.html |
| Permits  anyone the right to use the  software in a  non-commercial context |
| free of charge.                                                             |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Commercial license           http://webfx.eae.net/commercial.html |
| Permits the  license holder the right to use  the software in a  commercial |
| context. Such license must be specifically obtained, however it's valid for |
| any number of  implementations of the licensed software.                    |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| GPL - The GNU General Public License    http://www.gnu.org/licenses/gpl.txt |
| Permits anyone the right to use and modify the software without limitations |
| as long as proper  credits are given  and the original  and modified source |
| code are included. Requires  that the final product, software derivate from |
| the original  source or any  software  utilizing a GPL  component, such  as |
| this, is also licensed under the GPL license.                               |
|-----------------------------------------------------------------------------|
| 2001-01-10 | Original Version Posted.                                       |
| 2001-03-18 | Added getSelected and get/setBehavior  that can make it behave |
|            | more like windows explorer, check usage for more information.  |
| 2001-09-23 | Version 1.1 - New features included  keyboard  navigation (ie) |
|            | and the ability  to add and  remove nodes dynamically and some |
|            | other small tweaks and fixes.                                  |
| 2002-01-27 | Version 1.11 - Bug fixes and improved mozilla support.         |
| 2002-06-11 | Version 1.12 - Fixed a bug that prevented the indentation line |
|            | from  updating correctly  under some  circumstances.  This bug |
|            | happened when removing the last item in a subtree and items in |
|            | siblings to the remove subtree where not correctly updated.    |
| 2002-06-13 | Fixed a few minor bugs cased by the 1.12 bug-fix.              |
|-----------------------------------------------------------------------------|
| Created 2000-12-11 | All changes are in the log above. | Updated 2002-06-11 |
\----------------------------------------------------------------------------*/

var webFXTreeConfig = {
	rootIcon        : '/images/user/kms/blank.gif',
	openRootIcon    : '/images/user/kms/blank.gif',	
	folderIcon      : '/images/user/kms/tree_space.gif',	
	openFolderIcon  : '/images/user/kms/tree_space.gif',
	fileIcon        : '/images/user/kms/c_new.gif',
	iIcon           : '/images/user/kms/I.gif',
	lIcon           : '/images/user/kms/L.gif',	
	lMinusIcon      : '/images/user/kms/tree_minus_02.gif',
	lPlusIcon       : '/images/user/kms/tree_plus_02.gif',
	tIcon           : '/images/user/kms/T.gif',	
	tMinusIcon      : '/images/user/kms/tree_minus.gif',
	tPlusIcon       : '/images/user/kms/tree_plus.gif',
	blankIcon       : '/images/user/kms/blank_15.gif',		
	defaultText     : 'Tree Item',
	defaultAction   : 'javascript:void(0);',
	defaultBehavior : 'classic',
	admin	:	false
};


var webFXTreeHandler = {
	idCounter : 0,
	idPrefix  : "webfx-tree-object-",
	all       : {},
	behavior  : null,
	selected  : null,
	onSelect  : null, /* should be part of tree, not handler */
	//onSelect  : function() {alert('ddd');},
	getId     : function() { return this.idPrefix + this.idCounter++;},
	//added by ehkim start - node�� �� ����
	maxId	  : function(){ return this.idCounter;},
	//added by ehkim end
	toggle    : function (oItem) { this.all[oItem.id.replace('-plus','')].toggle();},
	//added by ehkim start
	toggle2    : function (oItem) { this.all[oItem.id.replace('-plus','')].toggle2();},
	//added by ehkim end
	select    : function (oItem) { this.all[oItem.id.replace('-icon','')].select();	},
	focus     : function (oItem) { this.all[oItem.id.replace('-anchor','')].focus();},
	blur      : function (oItem) { this.all[oItem.id.replace('-anchor','')].blur();},
	keydown   : function (oItem, e) { return this.all[oItem.id].keydown(e.keyCode);},
	cookies   : new WebFXCookie(),
	insertHTMLBeforeEnd	:	function (oElement, sHTML) {
		if (oElement.insertAdjacentHTML != null) {
			oElement.insertAdjacentHTML("BeforeEnd", sHTML)
			return;
		}
		var df;	// DocumentFragment
		var r = oElement.ownerDocument.createRange();
		r.selectNodeContents(oElement);
		r.collapse(false);
		df = r.createContextualFragment(sHTML);
		oElement.appendChild(df);
	},
	//////////////////////////////////////////////////////////////////////////////////
	//append by dklee 2002.10.22 - ���콺 ������ ��ư ������ 
	checkmouse	: function (oItem) { 
		//this.focus(event.srcElement.nextElement);
		this.select(oItem);
		if ((event.button==2) || (event.button==3) || (event.keyCode == 93))
		{ 
				showMenu();
		}else {
				hideMenu();
		}
	},
	checkmouse2	: function (oItem) { 	
		this.focus(oItem);
		if ((event.button==2) || (event.button==3) || (event.keyCode == 93))
		{ 
				showMenu();
		}else {
				hideMenu();
		}
	}
	/////////////////////////////////////////////////////////////////////////////
};
count=0;
/*
 * WebFXCookie class
 */

function WebFXCookie() {
	if (document.cookie.length) { this.cookies = ' ' + document.cookie; }
}

WebFXCookie.prototype.setCookie = function (key, value) {
	document.cookie = key + "=" + escape(value);
}

WebFXCookie.prototype.getCookie = function (key) {
	if (this.cookies) {
		var start = this.cookies.indexOf(' ' + key + '=');
		if (start == -1) { return null; }
		var end = this.cookies.indexOf(";", start);
		if (end == -1) { end = this.cookies.length; }
		end -= start;
		var cookie = this.cookies.substr(start,end);
		return unescape(cookie.substr(cookie.indexOf('=') + 1, cookie.length - cookie.indexOf('=') + 1));
	}
	else { return null; }
}

/*
 * WebFXTreeAbstractNode class
 */

function WebFXTreeAbstractNode(sText, sAction, ruleid) {
	this.childNodes  = [];
	this.id     = webFXTreeHandler.getId();	
	this.text   = sText || webFXTreeConfig.defaultText;		
	this.action = sAction || webFXTreeConfig.defaultAction;
	this._last  = false;	
	this.categoryID	= ruleid;	
	webFXTreeHandler.all[this.id] = this;	
}

/*
 * To speed thing up if you're adding multiple nodes at once (after load)
 * use the bNoIdent parameter to prevent automatic re-indentation and call
 * the obj.ident() method manually once all nodes has been added.
 */

WebFXTreeAbstractNode.prototype.add = function (node, bNoIdent) {
	

	node.parentNode = this;
	this.childNodes[this.childNodes.length] = node;
	var root = this;
	if (this.childNodes.length >=2) {
		this.childNodes[this.childNodes.length -2]._last = false;
	}
	
	while (root.parentNode) { root = root.parentNode; }
	if (root.rendered) {
		if (this.childNodes.length >= 2) {
			document.getElementById(this.childNodes[this.childNodes.length -2].id + '-plus').src = ((this.childNodes[this.childNodes.length -2].folder)?((this.childNodes[this.childNodes.length -2].open)?webFXTreeConfig.tMinusIcon:webFXTreeConfig.tPlusIcon):webFXTreeConfig.tIcon);
			if (this.childNodes[this.childNodes.length -2].folder) {
				this.childNodes[this.childNodes.length -2].plusIcon = webFXTreeConfig.tPlusIcon;
				this.childNodes[this.childNodes.length -2].minusIcon = webFXTreeConfig.tMinusIcon;
			}
			this.childNodes[this.childNodes.length -2]._last = false;
		}
		this._last = true;
		var foo = this;
		while (foo.parentNode) {
			for (var i = 0; i < foo.parentNode.childNodes.length; i++) {
				if (foo.id == foo.parentNode.childNodes[i].id) { break; }
			}
			if (++i == foo.parentNode.childNodes.length) { foo.parentNode._last = true; }
			else { foo.parentNode._last = false; }			
			foo = foo.parentNode;
		}
		webFXTreeHandler.insertHTMLBeforeEnd(document.getElementById(this.id + '-cont'), node.toString());
		if ((!this.folder) && (!this.openIcon)) {			
			this.icon = webFXTreeConfig.folderIcon;
			this.openIcon = webFXTreeConfig.openFolderIcon;
		}
		if (!this.folder) { this.folder = true; this.collapse(true); }
		if (!bNoIdent) { this.indent(); }
	}
	
	return node;
}


WebFXTreeAbstractNode.prototype.toggle = function() {
	if (this.folder) {
		if (this.open) { this.collapse();}		
		else { this.expand();}
}	}

/* added by ehkim 2004.07.26 start */
WebFXTreeAbstractNode.prototype.toggle2 = function() {
	if (this.folder) {
		this.expand();		
}	}
/* added by ehkim 2004.07.26 end */

WebFXTreeAbstractNode.prototype.select = function() {
	document.getElementById(this.id + '-anchor').focus();
}

WebFXTreeAbstractNode.prototype.deSelect = function() {
	document.getElementById(this.id + '-anchor').className = '';
	webFXTreeHandler.selected = null;
}


WebFXTreeAbstractNode.prototype.focus = function() {
	if ((webFXTreeHandler.selected) && (webFXTreeHandler.selected != this)) { webFXTreeHandler.selected.deSelect(); }
	webFXTreeHandler.selected = this;
	if ((this.openIcon) && (webFXTreeHandler.behavior != 'classic')) { document.getElementById(this.id + '-icon').src = this.openIcon; }
	document.getElementById(this.id + '-anchor').className = 'selected';
	document.getElementById(this.id + '-anchor').focus();
	if (webFXTreeHandler.onSelect) { webFXTreeHandler.onSelect(this); }
}

WebFXTreeAbstractNode.prototype.blur = function() {
	if ((this.openIcon) && (webFXTreeHandler.behavior != 'classic')) { document.getElementById(this.id + '-icon').src = this.icon; }
	document.getElementById(this.id + '-anchor').className = 'selected-inactive';
}


WebFXTreeAbstractNode.prototype.doExpand = function() {

	/* added by ehkim 2004.07.06 start - ���� �������� �Ѱ� ������ ������ ī�װ����� �ڵ����� �ݴ´�.*/
		
	var max = webFXTreeHandler.maxId();
	var idStr = "webfx-tree-object-";
	var idx="";
	var root = this;
	var poo;
	
	for(var loop=3; loop<max; loop++) {		
		idx = idStr + loop; 		
		poo = webFXTreeHandler.all[idx];			
		if(poo == null) continue;
				
		if(poo.id != root.id && poo._level == root._level &&  poo.parentNode.id == root.parentNode.id){			
			if(poo.open){				
				webFXTreeHandler.toggle(poo);
			}
		}
	}
	
	/* added by ehkim 2004.07.06 end*/
		
	if (webFXTreeHandler.behavior == 'classic') { document.getElementById(this.id + '-icon').src = this.openIcon;}	
	if (this.childNodes.length) {  document.getElementById(this.id + '-cont').style.display = 'block'; }
	this.open = true;
	webFXTreeHandler.cookies.setCookie(this.id.substr(18,this.id.length - 18), '1');
}

WebFXTreeAbstractNode.prototype.doCollapse = function() {
	if (webFXTreeHandler.behavior == 'classic') { document.getElementById(this.id + '-icon').src = this.icon; }
	if (this.childNodes.length) { document.getElementById(this.id + '-cont').style.display = 'none'; }
	this.open = false;
	webFXTreeHandler.cookies.setCookie(this.id.substr(18,this.id.length - 18), '0');
}

WebFXTreeAbstractNode.prototype.expandAll = function() {
	this.expandChildren();	
	if ((this.folder) && (!this.open)) { this.expand(); }
}

WebFXTreeAbstractNode.prototype.expandChildren = function() {
	for (var i = 0; i < this.childNodes.length; i++) {
		this.childNodes[i].expandAll();
} }

WebFXTreeAbstractNode.prototype.collapseAll = function() {
	this.collapseChildren();
	if ((this.folder) && (this.open)) { this.collapse(true); }
}

WebFXTreeAbstractNode.prototype.collapseChildren = function() {
	for (var i = 0; i < this.childNodes.length; i++) {
		this.childNodes[i].collapseAll();
} }

WebFXTreeAbstractNode.prototype.indent = function(lvl, del, last, level, nodesLeft) {
	/*
	 * Since we only want to modify items one level below ourself,
	 * and since the rightmost indentation position is occupied by
	 * the plus icon we set this to -2
	 */
	if (lvl == null) { lvl = -2; }
	var state = 0;
	for (var i = this.childNodes.length - 1; i >= 0 ; i--) {
		state = this.childNodes[i].indent(lvl + 1, del, last, level);
		if (state) { return; }
	}
	if (del) {
		if ((level >= this._level) && (document.getElementById(this.id + '-plus'))) {
			if (this.folder) {			
				document.getElementById(this.id + '-plus').src = (this.open)?webFXTreeConfig.lMinusIcon:webFXTreeConfig.lPlusIcon;
				this.plusIcon = webFXTreeConfig.lPlusIcon;
				this.minusIcon = webFXTreeConfig.lMinusIcon;
			}
			else if (nodesLeft) { document.getElementById(this.id + '-plus').src = webFXTreeConfig.lIcon; }
			return 1;
	}	}
	var foo = document.getElementById(this.id + '-indent-' + lvl);
	if (foo) {
		if ((foo._last) || ((del) && (last))) { foo.src =  webFXTreeConfig.blankIcon; }
		else { foo.src =  webFXTreeConfig.iIcon; }
	}
	return 0;
}

/*
 * WebFXTree class
 */

function WebFXTree(sText, sAction, ruleid, sBehavior, sIcon, sOpenIcon) {
	this.base = WebFXTreeAbstractNode;
	this.base(sText, sAction, ruleid);
	this.icon      = sIcon || webFXTreeConfig.rootIcon;
	this.openIcon  = sOpenIcon || webFXTreeConfig.openRootIcon;
	/* Defaults to open */
	this.open      = (webFXTreeHandler.cookies.getCookie(this.id.substr(18,this.id.length - 18)) == '0')?false:true;
	this.folder    = true;
	this.rendered  = false;
	this.onSelect  = null;
	if (!webFXTreeHandler.behavior) {  webFXTreeHandler.behavior = sBehavior || webFXTreeConfig.defaultBehavior; }
}

WebFXTree.prototype = new WebFXTreeAbstractNode;

WebFXTree.prototype.setBehavior = function (sBehavior) {
	webFXTreeHandler.behavior =  sBehavior;
};

WebFXTree.prototype.getBehavior = function (sBehavior) {
	return webFXTreeHandler.behavior;
};

WebFXTree.prototype.getSelected = function() {
	if (webFXTreeHandler.selected) { return webFXTreeHandler.selected; }
	else { return null; }
}

WebFXTree.prototype.remove = function() { }

WebFXTree.prototype.expand = function() {
	this.doExpand();	
}

WebFXTree.prototype.collapse = function(b) {
	if (!b) { this.focus(); }
	this.doCollapse();
}

WebFXTree.prototype.getFirst = function() {
	return null;
}

WebFXTree.prototype.getLast = function() {
	return null;
}

WebFXTree.prototype.getNextSibling = function() {
	return null;
}

WebFXTree.prototype.getPreviousSibling = function() {
	return null;
}

WebFXTree.prototype.keydown = function(key) {
	if (key == 39) {
		if (!this.open) { this.expand(); }
		else if (this.childNodes.length) { this.childNodes[0].select(); }
		return false;
	}
	if (key == 37) { this.collapse(); return false; }
	if ((key == 40) && (this.open) && (this.childNodes.length)) { this.childNodes[0].select(); return false; }
	return true;
}

WebFXTree.prototype.toString = function() {	

	//modifyed by ehkim 2004.08.13
	//var str = "<div id=\"" + this.id + "\" onClick=\"hideMenu();\" ondblclick=\"webFXTreeHandler.toggle(this);\" class=\"webfx-tree-item\" onkeydown=\"return webFXTreeHandler.keydown(this, event)\">";
	var str = "<div id=\"" + this.id + "\" onClick=\"hideMenu();\" class=\"webfx-tree-item\" onkeydown=\"return webFXTreeHandler.keydown(this, event)\">";
	//deleted by ehkim 2004.07.07 start
	//str += "<img id=\"" + this.id + "-icon\" class=\"webfx-tree-icon\" src=\"" + ((webFXTreeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onmousedown=\"webFXTreeHandler.checkmouse(this);\"><a target='' href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" onmousedown=\"webFXTreeHandler.toggle(this.parentElement);\" onblur=\"webFXTreeHandler.blur(this);\">" + this.text + "</a></div>";		
	//deleted by ehkim 2004.07.07 end
	str += "<div id=\"" + this.id + "-cont\" class=\"webfx-tree-container\" style=\"display: " + ((this.open)?'block':'none') + ";\">";
	for (var i = 0; i < this.childNodes.length; i++) {
		str += this.childNodes[i].toString(i, this.childNodes.length);		
	}
	str += "</div>";
	
	this.rendered = true;
	return str;
};

/*
 * WebFXTreeItem class
 */
/* modify dklee add generate RULE ID */
function WebFXTreeItem(sText, sAction, ruleid, eParent, sIcon, sOpenIcon) {
	this.base = WebFXTreeAbstractNode;
	this.base(sText, sAction);
	/* Defaults to close */
	if (ruleid) { this.categoryID = ruleid ; }
	this.open = (webFXTreeHandler.cookies.getCookie(this.id.substr(18,this.id.length - 18)) == '1')?true:false;
	if (sIcon) { this.icon = sIcon; }
	if (sOpenIcon) { this.openIcon = sOpenIcon; }
	if (eParent) { eParent.add(this); }
}

WebFXTreeItem.prototype = new WebFXTreeAbstractNode;

WebFXTreeItem.prototype.remove = function() {
	var iconSrc = document.getElementById(this.id + '-plus').src;
	var parentNode = this.parentNode;
	var prevSibling = this.getPreviousSibling(true);
	var nextSibling = this.getNextSibling(true);
	var folder = this.parentNode.folder;
	var last = ((nextSibling) && (nextSibling.parentNode) && (nextSibling.parentNode.id == parentNode.id))?false:true;
	this.getPreviousSibling().focus();
	this._remove();
	if (parentNode.childNodes.length == 0) {
		document.getElementById(parentNode.id + '-cont').style.display = 'none';
		parentNode.doCollapse();
		parentNode.folder = false;
		parentNode.open = false;
	}
	if (!nextSibling || last) { parentNode.indent(null, true, last, this._level, parentNode.childNodes.length); }
	if(prevSibling.parentNode != null) {	//modify by dklee
		if ((prevSibling == parentNode) && !(parentNode.childNodes.length)) {
			prevSibling.folder = false;
			prevSibling.open = false;
			iconSrc = document.getElementById(prevSibling.id + '-plus').src;
			iconSrc = iconSrc.replace('minus', '').replace('plus', '');
			document.getElementById(prevSibling.id + '-plus').src = iconSrc;
			document.getElementById(prevSibling.id + '-icon').src = webFXTreeConfig.fileIcon;
		}
	}
	if (document.getElementById(prevSibling.id + '-plus')) {
		if (parentNode == prevSibling.parentNode) {
			iconSrc = iconSrc.replace('minus', '').replace('plus', '');
			document.getElementById(prevSibling.id + '-plus').src = iconSrc;
}	}	}

WebFXTreeItem.prototype._remove = function() {
	for (var i = this.childNodes.length - 1; i >= 0; i--) {
		this.childNodes[i]._remove();
 	}
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) {
			for (var j = i; j < this.parentNode.childNodes.length; j++) {
				this.parentNode.childNodes[j] = this.parentNode.childNodes[j+1];
			}
			this.parentNode.childNodes.length -= 1;
			if (i + 1 == this.parentNode.childNodes.length) { this.parentNode._last = true; }
			break;
	}	}
	webFXTreeHandler.all[this.id] = null;
	var tmp = document.getElementById(this.id);
	if (tmp) { tmp.parentNode.removeChild(tmp); }
	tmp = document.getElementById(this.id + '-cont');
	if (tmp) { tmp.parentNode.removeChild(tmp); }
}

WebFXTreeItem.prototype.expand = function() {
	this.doExpand();
	
	document.getElementById(this.id + '-plus').src = this.minusIcon;
}

WebFXTreeItem.prototype.collapse = function(b) {
	if (!b) { this.focus(); }
	this.doCollapse();
	document.getElementById(this.id + '-plus').src = this.plusIcon;
}

WebFXTreeItem.prototype.getFirst = function() {	
	return this.childNodes[0];
}

WebFXTreeItem.prototype.getLast = function() {
	if (this.childNodes[this.childNodes.length - 1].open) { return this.childNodes[this.childNodes.length - 1].getLast(); }
	else { return this.childNodes[this.childNodes.length - 1]; }
}

WebFXTreeItem.prototype.getNextSibling = function() {
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) { break; }
	}
	if (++i == this.parentNode.childNodes.length) { return this.parentNode.getNextSibling(); }
	else { return this.parentNode.childNodes[i]; }
}

WebFXTreeItem.prototype.getPreviousSibling = function(b) {
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) { break; }
	}
	if (i == 0) { return this.parentNode; }
	else {
		if ((this.parentNode.childNodes[--i].open) || (b && this.parentNode.childNodes[i].folder)) { return this.parentNode.childNodes[i].getLast(); }
		else { return this.parentNode.childNodes[i]; }
} }

WebFXTreeItem.prototype.keydown = function(key) {
	if ((key == 39) && (this.folder)) {
		if (!this.open) { this.expand(); }
		else { this.getFirst().select(); }
		return false;
	}
	else if (key == 37) {
		if (this.open) { this.collapse(); }
		else { this.parentNode.select(); }
		return false;
	}
	else if (key == 40) {
		if (this.open) { this.getFirst().select(); }
		else {
			var sib = this.getNextSibling();
			if (sib) { sib.select(); }
		}
		return false;
	}
	else if (key == 38) { this.getPreviousSibling().select(); return false; }
	return true;
}

WebFXTreeItem.prototype.toString = function (nItem, nItemCount) {
	
	var foo = this.parentNode;
	var indent = '';
	
	if (nItem + 1 == nItemCount) { this.parentNode._last = true; }
	var i = 0;
	while (foo.parentNode) {		
		foo = foo.parentNode;
		indent = "<img id=\"" + this.id + "-indent-" + i + "\" src=\"" + ((foo._last)?webFXTreeConfig.blankIcon:webFXTreeConfig.iIcon) + "\">" + indent;
		i++;
	}
	this._level = i;
	
	if (this.childNodes.length) { this.folder = 1; }
	else { this.open = false; }
	if ((this.folder) || (webFXTreeHandler.behavior != 'classic')) {
		if (!this.icon) { this.icon = webFXTreeConfig.folderIcon; }
		if (!this.openIcon) { this.openIcon = webFXTreeConfig.openFolderIcon; }
	}
	else if (!this.icon) { this.icon = webFXTreeConfig.fileIcon; }
	var label = this.text.replace(/</g, '&lt;').replace(/>/g, '&gt;');
	
	//modifyed by ehkim 2004.08.13
	//var str = "<div id=\"" + this.id + "\" onClick=\"hideMenu();\" ondblclick=\"webFXTreeHandler.toggle(this);\" class=\"webfx-tree-item\" onkeydown=\"return webFXTreeHandler.keydown(this, event)\">";
	var str = "<div id=\"" + this.id + "\" onClick=\"hideMenu();\" class=\"webfx-tree-item\" onkeydown=\"return webFXTreeHandler.keydown(this, event)\">";
	str += indent;
	str += "<img id=\"" + this.id + "-plus\" src=\"" + ((this.folder)?((this.open)?((this.parentNode._last)?webFXTreeConfig.lMinusIcon:webFXTreeConfig.tMinusIcon):((this.parentNode._last)?webFXTreeConfig.lPlusIcon:webFXTreeConfig.tPlusIcon)):((this.parentNode._last)?webFXTreeConfig.lIcon:webFXTreeConfig.tIcon)) + "\" onclick=\"webFXTreeHandler.toggle(this);\">"
	/* modifyed by ehkim 2004.07.06 start */
	//str += "<img id=\"" + this.id + "-icon\" class=\"webfx-tree-icon\" src=\"" + ((webFXTreeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onmousedown=\"webFXTreeHandler.checkmouse(this);\"><a target='' href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" onmousedown=\"webFXTreeHandler.toggle(parentElement);\" onblur=\"webFXTreeHandler.blur(this);\">" + label + "</a></div>";		
	//str += "<img id=\"" + this.id + "-icon\" class=\"" + ((this.icon == webFXTreeConfig.folderIcon)?'webfx-tree-icon-s':'webfx-tree-icon') + "\" src=\"" + ((webFXTreeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onmousedown=\"webFXTreeHandler.checkmouse(this);\"><a target='' href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" onmousedown=\"webFXTreeHandler.toggle(parentElement);\" onblur=\"webFXTreeHandler.blur(this);\" class='666666'>" + label + "</a></div>";		
	
	//2004.11.24, modifid by �̿��� 
	//-- ���� ������ ����
	if(this.icon != webFXTreeConfig.fileIcon) {
		str += "<img id=\"" + this.id + "-icon\" class=\"" + ((this.icon == webFXTreeConfig.folderIcon)?'webfx-tree-icon-s':'webfx-tree-icon') + "\" src=\"" + ((webFXTreeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onmousedown=\"webFXTreeHandler.checkmouse(this);\"><a target='' href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" onmousedown=\"webFXTreeHandler.toggle2(parentElement);\" onblur=\"webFXTreeHandler.blur(this);\" class='666666'><font id='"+ this.id+"-font'>" + label + "</font></a></div>";		
	}else {
	str += "<a target='' href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" onmousedown=\"webFXTreeHandler.toggle2(parentElement);\" onblur=\"webFXTreeHandler.blur(this);\" class='666666'><font id='"+ this.id+"-font'>" + label + "</font></a></div>";				
	}	
	
	/* modifyed by ehkim 2004.07.06 end */
	str += "<div id=\"" + this.id + "-cont\" class=\"webfx-tree-container\" style=\"display: " + ((this.open)?'block':'none') + ";\">";
	for (var i = 0; i < this.childNodes.length; i++) {
		str += this.childNodes[i].toString(i,this.childNodes.length);
	}
	str += "</div>";
	
	this.plusIcon = ((this.parentNode._last)?webFXTreeConfig.lPlusIcon:webFXTreeConfig.tPlusIcon);
	this.minusIcon = ((this.parentNode._last)?webFXTreeConfig.lMinusIcon:webFXTreeConfig.tMinusIcon);
		
	return str;
}


/* Is Folder ? */
function isFolder() {
	if( tree )
	{
		if( tree.getSelected() )
		{
			if( tree.getSelected().folder )
				return true;
		}
	}
	return false;
}
/*
 Menu
 */
 
	var el;
	
	function showMenu() {
		
	}
	

	function toggleMenu() {   
	  
	}
	function clickMenu() {
	  
	}
	function hideMenu() {
		
	}

	//�޴� ����
	function subMenuShow() {
		
	}
	function clickSubMenu() {
		
	}
	function subMenuOut() {
		
	}
	
	function toggleMenu2() {   
		
		
	}

	function splitFrameSize(src) {
		
	}