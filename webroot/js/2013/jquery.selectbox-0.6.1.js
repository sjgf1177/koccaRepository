/**
 * jQuery custom selectboxes
 *
 * Copyright (c) 2008 Krzysztof Suszyński (suszynski.org)
 * Licensed under the MIT License:
 * http://www.opensource.org/licenses/mit-license.php
 *
 * @version 0.6.1
 * @category visual
 * @package jquery
 * @subpakage ui.selectbox
 * @author Krzysztof Suszyński <k.suszynski@wit.edu.pl>
**/
jQuery.fn.selectbox = function(options){
	/* Default settings */
	var settings = {
		className: "selectbox",
		animationSpeedDown: 0,
		animationSpeedUp: 0,
		listboxMaxSize: 10,
		replaceInvisible: false,
		direction: "down",
		lineHeight: 18,
		addWidth: 0
	};
	var commonClass = 'jquery-custom-selectboxes-replaced';
	var listOpen = false;
	var showList = function(listObj) {
		var selectbox = listObj.parents('.' + settings.className + '');
		listObj.slideDown(settings.animationSpeedDown, function(){
			listOpen = true;
		});
		selectbox.addClass('selecthover');
		jQuery(document).bind('click', onBlurList);
		return listObj;
	}
	var hideList = function(listObj) {
		var selectbox = listObj.parents('.' + settings.className + '');
		listObj.slideUp(settings.animationSpeedUp, function(){
			listOpen = false;
			jQuery(this).parents('.' + settings.className + '').removeClass('selecthover');
		});
		jQuery(document).unbind('click', onBlurList);
		return listObj;
	}
	var onBlurList = function(e) {
		var trgt = e.target;
		var currentListElements = jQuery('.' + settings.className + '-list:visible').parent().find('*').andSelf();
		if(jQuery.inArray(trgt, currentListElements)<0 && listOpen) {
			hideList( jQuery('.' + commonClass + '-list') );
		}
		return false;
	}

	/* Processing settings */
	settings = jQuery.extend(settings, options || {});
	/* Wrapping all passed elements */
	return this.each(function() {
		var optImgWth = 0;
		var _this = jQuery(this);
		if(_this.filter(':visible').length == 0 && !settings.replaceInvisible)
			return;
		var replacement = jQuery(
			'<div class="' + settings.className + ' ' + commonClass + '">' +
				'<span class="' + settings.className + '-currentItem" />' +
				'<a href="#" class="' + settings.className + '-moreButton" title="옵션 보기" />' +
				'<div class="' + settings.className + '-list ' + commonClass + '-list" />' +
			'</div>'
		);
		jQuery('option', _this).each(function(k,v){
			var v = jQuery(v);
			var optImg = v.attr("src");
			if(optImg != undefined){
				optImg = "<img src=\"" + optImg + "\" alt=\"\" />";
			}else{
				optImg = "";
			}
			var listElement =  jQuery('<a href="#" class="' + settings.className + '-item value-'+v.val()+' item-'+k+'">' + optImg + v.text() + '</a>');
			listElement.click(function(){
				var thisListElement = jQuery(this);
				var thisReplacment = thisListElement.parents('.'+settings.className);
				var thisIndex = thisListElement[0].className.split(' ');
				for( k1 in thisIndex ) {
					if(/^item-[0-9]+$/.test(thisIndex[k1])) {
						thisIndex = parseInt(thisIndex[k1].replace('item-',''), 10);
						break;
					}
				};
				var thisValue = thisListElement[0].className.split(' ');
				for( k1 in thisValue ) {
					if(/^value-.+$/.test(thisValue[k1])) {
						thisValue = thisValue[k1].replace('value-','');
						break;
					}
				};
				thisReplacment
					.find('.' + settings.className + '-currentItem')
					.html(thisListElement.html());
				thisReplacment
					.find('select')
					.val(thisValue)
					.triggerHandler('change');
				var thisSublist = thisReplacment.find('.' + settings.className + '-list');
				if(thisSublist.filter(":visible").length > 0) {
					hideList( thisSublist );
				}else{
					showList( thisSublist );
				}
			}).bind('mouseenter',function(){
				jQuery(this).addClass('listelementhover');
			}).bind('mouseleave',function(){
				jQuery(this).removeClass('listelementhover');
			});
			jQuery('.' + settings.className + '-list', replacement).append(listElement);
			if(v.filter(':selected').length > 0) {
				jQuery('.'+settings.className + '-currentItem', replacement).html(optImg + v.text());
			}
		});
		replacement.find('.' + settings.className + '-currentItem,.' + settings.className + '-moreButton').click(function(){
			var thisMoreButton = jQuery(this);
			var otherLists = jQuery('.' + settings.className + '-list')
				.not(thisMoreButton.siblings('.' + settings.className + '-list'));
			hideList( otherLists );
			var thisList = thisMoreButton.siblings('.' + settings.className + '-list');
			if(thisList.filter(":visible").length > 0) {
				hideList( thisList );
			}else{
				showList( thisList );
			}
		}).bind('mouseenter',function(){
			jQuery(this).addClass('morebuttonhover');
		}).bind('mouseleave',function(){
			jQuery(this).removeClass('morebuttonhover');
		});
		_this.hide().replaceWith(replacement).appendTo(replacement);
		var thisListBox = replacement.find('.' + settings.className + '-list');
		var thisListBoxSize = thisListBox.find('.' + settings.className + '-item').length;
		if(thisListBoxSize > settings.listboxMaxSize)
			thisListBoxSize = settings.listboxMaxSize;
		if(thisListBoxSize == 0)
			thisListBoxSize = 1;
		var widthPlus = jQuery.browser.safari ? 20 : 0;
		var thisListBoxWidth = Math.round(_this.width() + widthPlus + settings.addWidth);
		if(jQuery.browser.safari)
			thisListBoxWidth = thisListBoxWidth * 0.93;
		replacement.css('width', thisListBoxWidth + 'px');
		thisListBox.css({
			width: Math.round(thisListBoxWidth) + 'px',
			height: thisListBoxSize *settings.lineHeight + 'px'
		});
		if(settings.direction == "up"){
			thisListBox.css({
				top: -(thisListBoxSize *settings.lineHeight) -9+ 'px'
			});
		}
	});
}
jQuery.fn.unselectbox = function(){
	var commonClass = 'jquery-custom-selectboxes-replaced';
	return this.each(function() {
		var selectToRemove = jQuery(this).filter('.' + commonClass);
		selectToRemove.replaceWith(selectToRemove.find('select').show());
	});
}