	/* 112. ÁÖ¹Îµî·Ï¹øÈ£ Ã¼Å© */
	function inuserinfo(v_inusercompnm, v_subjgubun, v_subj) {
  
	window.self.name = "winInuserInfo";
    farwindow = window.open("", "openWinInuserInfo", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 520, height = 300, top=0, left=0");
    document.form2.target = "openWinInuserInfo";
    document.form2.action = "/servlet/controller.budget.BudgetAdminServlet";
    document.form2.p_process.value="inuserInfo";
    document.form2.p_inusercompnm.value = v_inusercompnm;
    document.form2.p_subjgubun.value = v_subjgubun;
    document.form2.p_subj.value = v_subj;
    document.form2.submit();
    
    farwindow.window.focus();
    document.form2.target = window.self.name;

	}

	function validPersono(fpersono1, fpersono2, fname) {
		var str1 = fpersono1;
		var str2 = fpersono2;
		var len1 = bytelength(str1);
		var len2 = bytelength(str2);
		if (!fname)		fname = "ÁÖ¹Îµî·Ï¹øÈ£";
		
		var str = String(str1) + String(str2);
		var len = bytelength(str);

        var sex = str2.substring(0,1);

        if (!digitstr(str1) || !digitstr(str2) || !digitstr(str)) {
            alert(fname+"´Â ¼ýÀÚ¸¸À¸·Î ±¸¼ºµÇ¾î¾ß ÇÕ´Ï´Ù. "+fname+"¸¦ È®ÀÎÇÏ½ÅÈÄ ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù");
            return false;
        }
        
        if (sex == "9" || sex == "0") {
            alert(fname+" ¼ºº°ºÎºÐÀ» Àß¸ø ÀÔ·ÂÇÏ¿´½À´Ï´Ù. "+fname+"¸¦ È®ÀÎÇÏ½ÅÈÄ ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù");
            return false;
        }
        
        if (str1 == "570908" && str2 == "1009010")
        	return true;
        
        if (sex == "1" || sex == "2" || sex == "3" || sex == "4")
        {
			var chk = 0 ;
			total = 0;
			temp = new Array(13);

			for(i = 1; i <= 6; i++) {
				temp[i] = str1.charAt(i-1);
			}

			for(i = 7; i < 13; i++) {
				temp[i] = str2.charAt(i-7);
			}

			for(i = 1; i <= 12; i++ ) {
				k = i + 1;
				if( k >= 10 ) {
					k = k % 10 + 2;
				}
				total = total + temp[i] * k;
			}

			mm = temp[3] + temp[4];
			dd = temp[5] + temp[6];
			temp[13] = str2.charAt(6);

			totalmod = total % 11;
			chd = (11 - totalmod) % 10;

			if (chd == temp[13] && mm < 13 && dd < 32 &&
				(temp[7]==1 || temp[7] == 2 || temp[7] == 3 || temp[7] == 4)) {
				return true;
			}
			alert("À¯È¿ÇÏÁö ¾ÊÀº "+fname+"ÀÔ´Ï´Ù. "+fname+"¸¦ È®ÀÎÇÏ½Ã°í ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù");
			return false;
		}
		else
		{
			var sum = 0;
			var odd = 0;
			var reg_no = str1 + str2;

			buf = new Array(13);
			for (i = 0; i < 13; i++) buf[i] = parseInt(reg_no.charAt(i));

		    odd = buf[7]*10 + buf[8];
    
		    if (odd%2 != 0) {
				alert("À¯È¿ÇÏÁö ¾ÊÀº "+fname+"ÀÔ´Ï´Ù. "+fname+"¸¦ È®ÀÎÇÏ½Ã°í ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù");
				return false;
			}

			if ((buf[11] != 6)&&(buf[11] != 7)&&(buf[11] != 8)&&(buf[11] != 9)) {
				return false;
			}

			multipliers = [2,3,4,5,6,7,8,9,2,3,4,5];
			for (i = 0, sum = 0; i < 12; i++) sum += (buf[i] *= multipliers[i]);

			sum=11-(sum%11);

			if (sum>=10) sum-=10;

	    	sum += 2;

		    if (sum>=10) sum-=10;

		    if ( sum != buf[12]) {
				alert("À¯È¿ÇÏÁö ¾ÊÀº "+fname+"ÀÔ´Ï´Ù. "+fname+"¸¦ È®ÀÎÇÏ½Ã°í ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù");
				return false;
			} else {
				return true;
			}
		}
	}
	
	
	
	
	
	/* 1. ¹®ÀÚ¿­ÀÇ ¾çÂÊ(¿ÞÂÊ, ¿À¸¥ÂÊ) °ø¹é Á¦°Å */ 
	function trim(pstr) {
		var search = 0
		while (pstr.charAt(search) == " ") {
			search = search + 1
		}
		pstr = pstr.substring(search, (pstr.length))
		search = pstr.length - 1
		while (pstr.charAt(search) ==" ")
		{
			search = search - 1
		}
		return pstr.substring(0, search + 1)         
	}

	/* 2. ¹®ÀÚ¿­ÀÇ BYTE ±æÀÌ ±¸ÇÏ±â */ 
	function bytelength(pstr) {
		var i, ch;
		len = pstr.length;
		for (i = 0; i < pstr.length; i++) {
			ch = pstr.substr(i,1).charCodeAt(0);
			if (ch > 127) { len++; }
		}
		return len;
	}

	/* 3. ÇÑ±Û ½ºÆ®¸µ Ã¼Å© */
	function hanstr(pstr) {
		var i, ch;
		for (i = 0; i < pstr.length; i++) {
			ch = escape(pstr.charAt(i));        //ISO-Latin-1 ¹®ÀÚ¼ÂÀ¸·Î º¯°æ
			//°¡ ==> %uAC00
			//Èþ ==> %uD79D
			//ÆR ==> %uD7A3
			if (strCharByte(ch) != 2) {
				return false;
			}
		}
		return true;
	}

	/* 3-1. ÇÑ±Û byte Ã¼Å© */
	function strCharByte(chStr) {

		if (chStr.substring(0, 2) == '%u') {
			
			/* alert(chStr.substring(2,6)); */
			if (chStr.substring(2,6) >= "AC00" && chStr.substring(2,6) <= "D7A3") {
				return 2;			/* ÇÑ±Û */
			} else {
				return 1;
			}

			/* ±¸¹öÀü */
			/*
			if (chStr.substring(2,4) == '00')
				return 1;
			else
				return 2;
			*/

		} else if (chStr.substring(0,1) == '%') {
			if (parseInt(chStr.substring(1,3), 16) > 127)
				return 2;			/* ÇÑ±Û */
			else
				return 1;
		} else {
			return 1;
		}

	}

	/* 4. ¼ýÀÚ ½ºÆ®¸µ Ã¼Å© */
	function digitstr(pstr) {
		var valid = "0123456789";
		return checkstr(pstr, valid, 0);
	}

	/* 5. ¿µ¹®ÀÚ,¼ýÀÚ ½ºÆ®¸µ Ã¼Å© */
	function alphadigitstr(pstr) {
		var valid = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		return checkstr(pstr, valid, 0);
	}

	/* 6. ¿µ¹®ÀÚ,¼ýÀÚ,Æ¯¼ö¹®ÀÚ ½ºÆ®¸µ Ã¼Å© */
	function charstr(pstr) {
		var valid = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~!@#$%^*()_+`-={}|[]\\:\";'<>?,./&";
		return checkstr(pstr, valid, 0);
	}
	
	/* 7. ·Î±×ÀÎ */
	function emailidstr(pstr) {
		var valid = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
		return checkstr(pstr, valid, 0);
	}

	/* 8. ´Ð³×ÀÓ(º°¸í) ½ºÆ®¸µ Ã¼Å© */
	function nickstr(pstr) {
		var valid = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-.^/* ";
		return checkstr(pstr, valid, 1);
	}

	/* 9. »óÈ£ ½ºÆ®¸µ Ã¼Å© */
	function corpstr(pstr) {
		var valid = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~!@#$%^*()_+`-={}|[]\\:\";'<>?,./& ";
		return checkstr(pstr, valid, 1);
	}	
	
	/* 10. ÇØ¿Ü°ÅÁÖ °¡ÀÔÀÚ ÀÌ¸§ Ã¼Å© */
	function fuserstr(pstr) {
		var valid = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz., ";
		return checkstr(pstr, valid, 1);
	}

	function checkstr(pstr, pvalid, han) {
		var valid = pvalid;
		var tmp;
		var flag = true;

		for (var i = 0; i < pstr.length; i++) {
			flag = true;
			tmp = "" + pstr.substring(i, i+1);
			
			if (han != 1) {
				if (valid.indexOf(tmp) == "-1") {
					return false;
				}
			} else {
				ch = escape(pstr.charAt(i));        //ISO-Latin-1 ¹®ÀÚ¼ÂÀ¸·Î º¯°æ
				if (valid.indexOf(tmp) == "-1" && 
					strCharByte(ch) != 2) {
					return false;
				}
			}
		}
		return true;;
	}

	/* 4. Select Form °ª °¡Á®¿À±â */
	function get_select_value(sset) {
		for (var i = 0; i < sset.length; i++ ) {
			if ( sset.options[i].selected ) {
				return (sset[i].value);
			}
		}
		return "";
	}

	/* 5. Radio Form °ª °¡Á®¿À±â */
	function get_radio_value(rset) {
		if (rset.length) {
			for (var i = 0; i < rset.length; i++ ) {
				if ( rset[i].checked ) {
					return (rset[i].value);
				}
			}
			return ""; 
		} else {
			if (rset.checked) {
				return rset.value;
			} else {
				return "";
			} 
		}
	}

	/* 6. Checkbox Form °ª °¡Á®¿À±â */
	function get_checkbox_value(cset) {
		if (cset.length) {
			for (var i = 0; i < cset.length; i++ ) {
				if ( cset[i].checked ) {
					return (cset[i].value);
				}
			}
			return "";
		} else {
			if (cset.checked) {
				return cset.value;
			} else {
				return "";
			}
		}
	}



	function emailstr(emailStr) {

		/* The following variable tells the rest of the function whether or not
		   to verify that the address ends in a two-letter country or well-known
		   TLD.  1 means check it, 0 means don't. */

		var checkTLD = 1;

		/* The following is the list of known TLDs that an e-mail address must end with. */

		var knownDomsPat=/^(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum)$/;

		/* The following pattern is used to check if the entered e-mail address
		   fits the user@domain format.  It also is used to separate the username
		   from the domain. */

		var emailPat = /^(.+)@(.+)$/;

		/* The following string represents the pattern for matching all special
		   characters.  We don't want to allow special characters in the address. 
		   These characters include ( ) < > @ , ; : \ " . [ ] */

		var specialChars="\\(\\)><@,;:\\\\\\\"\\.\\[\\]";

		/* The following string represents the range of characters allowed in a 
		   username or domainname.  It really states which chars aren't allowed.*/

		var validChars="\[^\\s" + specialChars + "\]";

		/* The following pattern applies if the "user" is a quoted string (in
		   which case, there are no rules about which characters are allowed
		   and which aren't; anything goes).  E.g. "jiminy cricket"@disney.com
		   is a legal e-mail address. */

		var quotedUser="(\"[^\"]*\")";

		/* The following pattern applies for domains that are IP addresses,
		   rather than symbolic names.  E.g. joe@[123.124.233.4] is a legal
		   e-mail address. NOTE: The square brackets are required. */

		var ipDomainPat=/^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/;

		/* The following string represents an atom (basically a series of non-special characters.) */

		var atom = validChars + '+';

		/* The following string represents one word in the typical username.
		   For example, in john.doe@somewhere.com, john and doe are words.
		   Basically, a word is either an atom or quoted string. */

		var word="(" + atom + "|" + quotedUser + ")";

		// The following pattern describes the structure of the user

		var userPat=new RegExp("^" + word + "(\\." + word + ")*$");

		/* The following pattern describes the structure of a normal symbolic domain,
		   as opposed to ipDomainPat, shown above. */

		var domainPat=new RegExp("^" + atom + "(\\." + atom +")*$");

		/* Finally, let's start trying to figure out if the supplied address is valid. */

		/* Begin with the coarse pattern to simply break up user@domain into
		   different pieces that are easy to analyze. */

		var matchArray=emailStr.match(emailPat);

		if (matchArray == null) {

		/* Too many/few @'s or something; basically, this address doesn't
		   even fit the general mould of a valid e-mail address. */

			// alert("Email address seems incorrect (check @ and .'s)");
			return false;
		}

		var user=matchArray[1];
		var domain=matchArray[2];

		// Start by checking that only basic ASCII characters are in the strings (0-127).

		for (i=0; i<user.length; i++) {
			if (user.charCodeAt(i)>127) {
				// alert("Ths username contains invalid characters.");
				return false;
			}
		}

		for (i=0; i<domain.length; i++) {
			if (domain.charCodeAt(i)>127) {
				// alert("Ths domain name contains invalid characters.");
				return false;
			}
		}

		// See if "user" is valid 
		if (user.match(userPat) == null) {

			// user is not valid

			// alert("The username doesn't seem to be valid.");
			return false;
		}

		/* if the e-mail address is at an IP address (as opposed to a symbolic
		   host name) make sure the IP address is valid. */

		var IPArray = domain.match(ipDomainPat);
		if (IPArray != null) {

			// this is an IP address
			for (var i = 1; i <= 4; i++) {
				if (IPArray[i] > 255) {
					// alert("Destination IP address is invalid!");
					return false;
				}
			}
			return true;
		}

		// Domain is symbolic name.  Check if it's valid.

		var atomPat = new RegExp("^" + atom + "$");
		var domArr = domain.split(".");
		var len = domArr.length;
		for (i = 0; i < len; i++) {
			if (domArr[i].search(atomPat)==-1) {
				// alert("The domain name does not seem to be valid.");
				return false;
			}
		}

		/* domain name seems valid, but now make sure that it ends in a
		   known top-level domain (like com, edu, gov) or a two-letter word,
		   representing country (uk, nl), and that there's a hostname preceding 
		   the domain or country. */

		if (checkTLD && domArr[domArr.length-1].length!=2 && 
			domArr[domArr.length-1].search(knownDomsPat)==-1) {
			// alert("The address must end in a well-known domain or two letter " + "country.");
			return false;
		}

		// Make sure there's a host name preceding the domain.
		if (len<2) {
			// alert("This address is missing a hostname!");
			return false;
		}

		// If we've gotten this far, everything's valid!
		return true;
	}

	/* 7. ÀÚµ¿ Tab */
	var isNN = (navigator.appName.indexOf("Netscape")!=-1);
	function autoTab(input,len, e) {
		var keyCode = (isNN) ? e.which : e.keyCode;
        var filter = (isNN) ? [0,8,9] : [0,8,9,16,17,18,37,38,39,40,46];
        if(input.value.length >= len && !containsElement(filter,keyCode)) {
            input.value = input.value.slice(0, len);
			input.form[(getIndex(input)+1) % input.form.length].focus();
		}
		
		function containsElement(arr, ele) {
			var found = false, index = 0;
            while(!found && index < arr.length) {
                if(arr[index] == ele)
                    found = true; 
                else 
                    index++;
			}
            return found;
        }   

		function getIndex(input) {
			var index = -1, i = 0, found = false;
			while (i < input.form.length && index == -1) {
                if (input.form[i] == input)
					index = i;
				else
					i++;
			}
			return index;
		}
		return true;
	}
	/* ÇÔ¼ö ³¡ */

/* ³Ö´Â ¹æ¹ý
	<input onKeyUp="return autoTab(this, 6, event);" size="7" maxlength="6"> -
	<input onKeyUp="return autoTab(this, 7, event);" size="8" maxlength="7">
*/

	/* 8. ÀÚµ¿ Focus */
	function autoFocus(frm) {
		for (i = 0; i < frm.length; i++) {
			if (frm[i].type == "text" || frm[i].type == "password") {
				frm[i].focus();
				break;
			}
		}
	}

	function getCookie( name ) {
		var nameOfCookie = name + "=";
		var x = 0;
		while ( x <= document.cookie.length ) {

			var y = (x+nameOfCookie.length);
			if ( document.cookie.substring( x, y ) == nameOfCookie ) {
				if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 ) 
					endOfCookie = document.cookie.length;

				return unescape( document.cookie.substring( y, endOfCookie ) );
			}
			x = document.cookie.indexOf( " ", x ) + 1;
			if ( x == 0 )
				break;  
		}
		return "";
	}

	function setCookie (name, value) {
		var argv = setJSCookie.arguments;
		var argc = setJSCookie.arguments.length;
		var expires = (2 < argc)? argv[2] : null;
		var path = (3 < argc)	? argv[3] : null;
		var domain = (4 < argc)	? argv[4] : null;
		var secure = (5 < argc)	? argv[5] : false;
		document.cookie = name + "=" + escape (value) + ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + ((path == null) ? "" : ("; path=" + path)) + ((domain == null) ? "" : ("; domain=" + domain)) + ((secure == true) ? "; secure" : "");
	}

	/* 101. ¾ÆÀÌµð Ã¼Å© */
	function validUserid(fuserid) {
		var str = fuserid.value;
		var len = bytelength(str);

		if (str == "" || len == 0) { 
			alert("¾ÆÀÌµð´Â ¹Ýµå½Ã ÀÔ·ÂÇØ¾ß ÇÕ´Ï´Ù. ¾ÆÀÌµð¸¦ ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
			return false;
		}

        if (len < 4 || len > 12) {
            alert("¾ÆÀÌµð´Â 4±ÛÀÚ ÀÌ»ó 12±ÛÀÚ ÀÌÇÏÀÌ¾î¾ß ÇÕ´Ï´Ù. ¾ÆÀÌµð¸¦ ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
            return false;
        }

		if (str.indexOf(" ") != -1) {
			alert("¾ÆÀÌµð¿¡´Â °ø¹éÀ» ³ÖÀ» ¼ö ¾ø½À´Ï´Ù. ¾ÆÀÌµð¸¦ ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
            return false;		
		}

        if (!alphadigitstr(str)) {
            alert("¾ÆÀÌµð´Â ¿µ¹®ÀÚ¿Í ¼ýÀÚ·Î¸¸ ÀÌ·ç¾îÁ®¾ß ÇÕ´Ï´Ù. ¾ÆÀÌµð¸¦ ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
            return false;
        }

        return true;
	}
	
	/* 102. ±âÁ¸ ¾ÆÀÌµð Ã¼Å© */
	function validOUserid(fuserid, fname) {
		var str = fuserid.value;
		var len = bytelength(str);
		if (!fname)		fname = "¾ÆÀÌµð";

		if (str == "" || len == 0) { 
			alert(fname+"´Â ¹Ýµå½Ã ÀÔ·ÂÇØ¾ß ÇÕ´Ï´Ù. "+fname+"¸¦ ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
			return false;
		}
	
		if (str.indexOf(" ") != -1) {
			alert(fname+"¿¡´Â °ø¹éÀ» ³ÖÀ» ¼ö ¾ø½À´Ï´Ù. "+fname+"¸¦ ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
			return false;		
		}
		
		if (hanstr(str)) {
			alert("ÆÄ¶õ¿¡¼­´Â ¿µ¹® ¾ÆÀÌµð¸¸ °¡´ÉÇÕ´Ï´Ù. ¿µ¹® ¾ÆÀÌµð¸¦ ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
			return false;
		}

        if (!emailidstr(str)) {
            alert(fname+"´Â ¿µ¹®ÀÚ¿Í ¼ýÀÚ, ±×¸®°í ÀÏºÎ±âÈ£(_,-) ·Î¸¸ ÀÌ·ç¾îÁ®¾ß ÇÕ´Ï´Ù. "+fname+"¸¦ ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
            return false;
        }

        return true;
	}
	
	/* 103. ºñ¹Ð¹øÈ£ Ã¼Å© */
	function validPasswd(fpasswd, fname) {
		var str = fpasswd.value;
		var len = bytelength(str);
		if (!fname)		fname = "ºñ¹Ð¹øÈ£";

		if (str == "" || len == 0) { 
			alert(fname+"´Â ¹Ýµå½Ã ÀÔ·ÂÇØ¾ß ÇÕ´Ï´Ù. "+fname+"¸¦ ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
			return false;
		}
		
		if (len < 6 || len > 12) {
			alert(fname+"´Â 6±ÛÀÚ ÀÌ»ó 12±ÛÀÚ ÀÌÇÏÀÌ¾î¾ß ÇÕ´Ï´Ù. "+fname+"¸¦ ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
			return false;
		}
		
		if (str.indexOf(" ") != -1) {
			alert(fname+"¿¡´Â °ø¹éÀ» ³ÖÀ» ¼ö ¾ø½À´Ï´Ù. "+fname+"¸¦ ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
			return false;		
		}		

        if (!charstr(str)) {
            alert(fname+"´Â ¿µ¹®, ¼ýÀÚ, Æ¯¼ö¹®ÀÚ·Î¸¸ ÀÌ·ç¾îÁ®¾ß ÇÕ´Ï´Ù. "+fname+"¸¦ ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
            return false;
        }

        return true;
    }
    
	/* 104. ±âÁ¸ ºñ¹Ð¹øÈ£ Ã¼Å© */
	function validOPasswd(fpasswd, fname) {
		var str = trim(fpasswd.value);
		var len = bytelength(str);
		if (!fname)		fname = "ºñ¹Ð¹øÈ£";

		if (str == "" || len == 0) { 
			alert(fname+"´Â ¹Ýµå½Ã ÀÔ·ÂÇØ¾ß ÇÕ´Ï´Ù. "+fname+"¸¦ ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
			return false;
		}
		
		if (len < 4 || len > 12) {
			alert(fname+"´Â 4±ÛÀÚ ÀÌ»ó 12±ÛÀÚ ÀÌÇÏÀÌ¾î¾ß ÇÕ´Ï´Ù. "+fname+"¸¦ ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
			return false;
		}

		if (str.indexOf(" ") != -1) {
			alert(fname+"¿¡´Â °ø¹éÀ» ³ÖÀ» ¼ö ¾ø½À´Ï´Ù. "+fname+"¸¦ ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
			return false;		
		}

        if (!charstr(str)) {
            alert(fname+"´Â ¿µ¹®, ¼ýÀÚ, Æ¯¼ö¹®ÀÚ·Î¸¸ ÀÌ·ç¾îÁ®¾ß ÇÕ´Ï´Ù. "+fname+"¸¦ ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
            return false;
        }

        return true;
	}
	
	/* 105. ºñ¹Ð¹øÈ£È®ÀÎ Ã¼Å© */
    function validCPasswd(fpasswdchk, fpasswd, fnamechk, fname) {
		var str1 = trim(fpasswdchk.value);
		var str2 = trim(fpasswd.value);
		var len1 = bytelength(str1);
		var len2 = bytelength(str2);
		if (!fnamechk)	fnamechk = "ºñ¹Ð¹øÈ£È®ÀÎ";
		if (!fname)		fname = "ºñ¹Ð¹øÈ£";

		if (str1 == "" || len1 == 0) { 
			alert(fnamechk+"Àº ¹Ýµå½Ã ÀÔ·ÂÇØ¾ß ÇÕ´Ï´Ù. "+fnamechk+"À» ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
			return false;
		}

		if (len1 < 6 || len1 > 12) {
			alert(fnamechk+"Àº 6±ÛÀÚ ÀÌ»ó 12±ÛÀÚ ÀÌÇÏÀÌ¾î¾ß ÇÕ´Ï´Ù. "+fnamechk+"À» ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
			return false;
		}		

		if (str1.indexOf(" ") != -1) {
			alert(fnamechk+"¿¡´Â °ø¹éÀ» ³ÖÀ» ¼ö ¾ø½À´Ï´Ù. "+fnamechk+"À» ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
			return false;		
		}

		if (!charstr(str1)) {
            alert(fnamechk+"Àº ¿µ¹®, ¼ýÀÚ, Æ¯¼ö¹®ÀÚ·Î¸¸ ÀÌ·ç¾îÁ®¾ß ÇÕ´Ï´Ù. "+fnamechk+"À» ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
            return false;
        }

        if (str1 != str2) {
            alert(fname+"¿Í "+fnamechk+"ÀÌ ÀÏÄ¡ÇÏÁö ¾Ê½À´Ï´Ù. "+fname+"¸¦ ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
            return false;
        }

        return true;
    }

	/* 151. ·Î±×ÀÎ ¾ÆÀÌµð Ã¼Å© */
	function validLoginid(fuserid, fdomain) {

		if (get_select_value(fdomain) == "") {
			if (!emailstr(fuserid.value)) {
				alert("¿Ã¹Ù¸¥ ¾ÆÀÌµð Çü½ÄÀÌ ¾Æ´Õ´Ï´Ù. ¾ÆÀÌµð¸¦ ´Ù½Ã ÀÔ·ÂÇÏ½Ã±â ¹Ù¶ø´Ï´Ù.");
				return false;
			} else {
				return true;
			}
		} else {
			return validOUserid(fuserid);
		}
	}

	/* 152. ·Î±×ÀÎ ºñ¹Ð¹øÈ£ Ã¼Å© */
	function validLoginpasswd(fpasswd) {
		return validOPasswd(fpasswd);
	}

	/* 201. ·Î±×ÀÎ Æû Ã¼Å© */
	function checkLoginfm(fuserid, fdomain, fpasswd, fsaveid, fsecure) {
		if (!validLoginid(fuserid, fdomain)) {
			fuserid.focus();
			fuserid.select();
			return false;
		}

		if (!validLoginpasswd(fpasswd)) {
			fpasswd.focus();
			fpasswd.select();
			return false;
		}
		
		if (fsecure.checked == true) {
			fuserid.form.action = "https://main.paran.com/mainAction.do?method=paranMainLogin";
		} else {
			fuserid.form.action = "http://main.paran.com/mainAction.do?method=paranMainLogin";
		}

		return true;
	}

	/* 201. µµ¸ÞÀÎ Select Item »ý¼º */
	var domainNameArr = new Array("paran.com", "hitel.net", "hanmir.com", "tiniwini.com", "kttown.com", "Á÷Á¢ÀÔ·Â");
	var domainValueArr = new Array("paran.com", "hitel.net", "hanmir.com", "tiniwini.com", "kttown.com", "");

	function displayLoginfm(fuserid, fdomain, fpasswd, fsaveid, fsecure, fdlt) {
		printUserid(fuserid);
		printDomain(fdomain, fdlt);
		if (fsaveid)
			printSaveID(fsaveid);
		if (fsecure)
			printSecure(fsecure);

		if (getFMCookie("LID") && getFMCookie("LDM")) {
			if (fpasswd)
				fpasswd.focus();
		} else {
			fuserid.focus();
		}
	}

	function printUserid(fuserid) {
		if (getFMCookie("LID")) {
			fuserid.value = getFMCookie("LID");
		}
	}

	function printDomain(fdomain, fdlt) {

		var j = 0;
		var eflag = false;
		var logindomain = getFMCookie("LDM");
		
		/* µî·ÏµÇ¾î ÀÖÁö ¾ÊÀº µµ¸ÞÀÎÀÌ ÄíÅ°¿¡ ¼¼ÆÃµÇ¾î ÀÖ´Â °æ¿ì */
		if (logindomain) {
			for (i = 0; i < domainValueArr.length; i++) {
				if (domainValueArr[i] == logindomain) {
					eflag = true;
				}
			}
			if (eflag == false) {
				fdomain.options[j] = new Option(logindomain);
				fdomain.options[j].value = logindomain; 
				fdomain.options[j].selected = true
				j++;
			}
		}

		for (i = 0; i < domainValueArr.length; i++) {
			fdomain.options[j] = new Option(domainNameArr[i]);
			fdomain.options[j].value = domainValueArr[i]; 
			if (domainValueArr[i] == logindomain && domainValueArr[i] != "") {
				fdomain.options[j].selected = true
			}
			j++;
		}
		
		if (fdlt) {
			if (fdomain.options[0].value != "") {
				fdlt.value = "@";
			} else {
				fdlt.value = "";
			}
		}
	}

	function printSaveID(fsaveid) {
		if (getFMCookie("LID") && getFMCookie("LDM")) {
			fsaveid.checked = true;
		}
	}

	function printSecure(fsecure) {
		if (getFMCookie("LSL") == "1") {
			fsecure.checked = true;
		}
	}
	
	function changeDomain(fuserid, fdomain, fdlt, fpasswd) {
		if (get_select_value(fdomain) == "") {
			if (fdlt) {
				fdlt.value = "";
			}
		} else {
			if (fdlt) {
				fdlt.value = "@";
			}
		}
//		fuserid.focus();
//		fuserid.select();
		if (fpasswd) {
			fpasswd.focus();
			fpasswd.select();
		}
	}
	
	function getFMCookie(field)
	{
		var fmcookies = getCookie("FM")
		if (!fmcookies)
		{
			return "";
		}
		
		var ckArr = fmcookies.split(":");
		var len = ckArr.length;
		if (len != 3)
		{
			return "";
		}
		
		if (field == 'LID')
			return ckArr[0];
		else if (field == 'LDM')
			return ckArr[1];
		else if (field == 'LSL')
			return ckArr[2];
		else
			return "";			
	}

	/* ¾ÆÀÌµð ÀúÀå - »ç¿ëÇÏÁö ¾ÊÀ½ */
	function setSaveID(fsaveid, fuserid, fdomain) {

		/* 1³â µ¿¾È ÀúÀå */
		var todayDate = new Date();
		todayDate.setYear( todayDate.getYear() + 1);

		if (fsaveid.checked) {
			setCookie("LID", fuserid.value, todayDate, "/", ".paran.com");
			setCookie("LDM", fdomain.value, todayDate, "/", ".paran.com");
		} else {
			setCookie("LID", fuserid.value, "", "/", ".paran.com");
			setCookie("LDM", fdomain.value, "", "/", ".paran.com");
		}
	}

	/* º¸¾ÈÁ¢¼Ó Ã¼Å© - »ç¿ëÇÏÁö ¾ÊÀ½ */
	function setSecure(fsecure) {
		/* 30ÀÏ µ¿¾È ÀúÀå */
		var todayDate = new Date();
		todayDate.setYear( todayDate.getYear() + 1);

		if (fsaveid.checked) {
			setCookie("LSL", "on", todayDate, "/", ".paran.com");
		} else {
			setCookie("LSL", "off", "", "/", ".paran.com");
		}
	}
	
	function getCenterWinStr(width, height)
	{
		var str = "";
		str = "height=" + height + ",innerHeight=" + height;
		str += ",width=" + width + ",innerWidth=" + width;

		if (window.screen) {
			var ah = screen.availHeight - 30;
			var aw = screen.availWidth - 10;

			var xc = (aw - width) / 2;
			var yc = (ah - height) / 2;

			str += ",left=" + xc + ",screenX=" + xc;
			str += ",top=" + yc + ",screenY=" + yc;
		}

		return str;
	}
	
	

	/* 201. Áßº¹¾ÆÀÌµð Ã¼Å© ÆË¾÷ */
	function popupCheckDupID(fuserid, domain, url) {

		if (!validUserid(fuserid)) {
			fuserid.focus();
            fuserid.select();
            return;
        }

        if (!domain) domain = 'paran.com';

//		var popupurl = "http://user.paran.com/commonAction.do?method=checkDupid&wbUserid="+fuserid.value+"&wbDomain="+domain+"&wbUrl="+url; 
		var popupurl = "http://user.paran.com/commonAction.do?method=checkDuplicateID&wbUserid="+fuserid.value+"&wbDomain="+domain+"&wbUrl="+url; 
		var param = getCenterWinStr(480, 305);

		var checkDupIDWin = window.open(popupurl, "checkDupID", param+",menubar=no,scrollbars=no");
		checkDupIDWin.focus();
    }
    
    
	function popupSearchAddr(url) {

		var popupurl = "http://user.paran.com/commonAction.do?method=searchAddrFind&wbUrl="+url; 
		var param = getCenterWinStr(480, 360);

		var searchAddrWin = window.open(popupurl, "searchAddr", param+",menubar=no,scrollbars=no");
		searchAddrWin.focus();
    }

    function popupSetMailaccept(mailaccept, url, cmd) {

//      var popupurl = "http://user.paran.com/common/jsp/set_mailaccept_form.jsp?wbMailaccept="+mailaccept+"&wbUrl="+url;
        var popupurl = "http://user.paran.com/commonAction.do?method=mailacceptForm&wbMailaccept="+mailaccept+"&wbUrl="+url+"&wbCmd="+cmd;
        var param = getCenterWinStr(496, 600);

		var setMailacceptWin = window.open(popupurl, "setMailaccept",param+",menubar=no,scrollbars=yes");
		setMailacceptWin.focus();
    }

    function popupInputPayinfo(surl, furl) {

		var popupurl = "http://user.paran.com/commonAction.do?method=inputPayinfoForm&wbSurl="+surl+"&wbFurl="+furl;
		var param = getCenterWinStr(480, 400);

		var inputPayinfoWin = window.open(popupurl, "inputPayinfo", param+",menubar=no,scrollbars=no");
		inputPayinfoWin.focus();
    }
    
    function popupAgreeChildbill(url) {

        var popupurl = "http://user.paran.com/common/jsp/input_payinfo_form.jsp?wbUrl="+url;
        var param = getCenterWinStr(480, 540);

		var inputPayinfoWin = window.open(popupurl, "inputPayinfo", param+",menubar=no,scrollbars=no");
		inputPayinfoWin.focus();
    }
    
    function redirectAgreeChildbill(url) {

        var popupurl = "http://user.paran.com/common/jsp/input_payinfo_form.jsp?wbUrl="+url;
        var param = getCenterWinStr(480, 540);

		var inputPayinfoWin = window.open(popupurl, "inputPayinfo", param+",menubar=no,scrollbars=no");
		inputPayinfoWin.focus();
    }
    
     function popupInputUserinfo(surl, furl, type) {

		var popupurl = "http://user.paran.com/commonAction.do?method=inputUserinfoForm&wbSurl="+surl+"&wbFurl="+furl+"&wbType="+type;
        var param = getCenterWinStr(650,455);

		var inputUserinfoWin = window.open(popupurl, "inputUserinfo", param+",menubar=no,scrollbars=no");
		inputUserinfoWin.focus();
    }
    
	function popupPimsUserinfo() {

		var popupurl = "http://user.paran.com/userAction.do?method=paranChangeBasicInfoForm&reqType=popup";
        var param = getCenterWinStr(780, 700);

		var registerCPTestWin = window.open(popupurl, "registerCPTest", param+",menubar=no,scrollbars=yes,statusbar=yes");
		registerCPTestWin.focus();
    }    
    
	function redirectInputUserinfo(surl, furl) {

		var popupurl = "http://user.paran.com/common/jsp/input_payinfo_form.jsp?wbUrl="+url;
		var param = getCenterWinStr(643,425);

		var inputPayinfoWin = window.open(popupurl, "inputPayinfo", param+",menubar=no,scrollbars=no");
		inputPayinfoWin.focus();
	}
	
	function popupRegisterCPTest(url, param) {

		var popupurl = "http://user.paran.com/common/jsp/register_cptest_form.jsp?wbUrl="+url+"&wbParam="+param;
        var param = getCenterWinStr(480, 583);

		var registerCPTestWin = window.open(popupurl, "registerCPTest", param+",menubar=no,scrollbars=no");
		registerCPTestWin.focus();
    }
    
	function popupCheckAdult(surl, furl, type) {

		if (!type)
		{
			type = '';
		}
		var popupurl = "http://user.paran.com/common/jsp/input_adult.jsp?wbSurl="+surl+"&wbFurl="+furl;
		var param = getCenterWinStr(480, 345);

		var checkAdultWin = window.open(popupurl, "checkAdult", param+",menubar=no,scrollbars=no");
		checkAdultWin.focus();
    }
    
	function popupAuthRedirect(url) {

		var popupurl = "http://user.paran.com/commonAction.do?method=checkLogin&wbUrl="+escape(url);

		var param = getCenterWinStr(480, 345);

		var authRedirectWin = window.open(popupurl, "authRedirect", param+",menubar=no,scrollbars=no");
		authRedirectWin.focus();
    }
    
    function popupInputKeyword() {
		var popupurl = "http://hompy.paran.com/hompySec/keyword/keyword_reg.asp";

		var param = getCenterWinStr(466, 525);

		var inputKeywordWin = window.open(popupurl, "inputKeyword", param+",menubar=no,scrollbars=no");
		inputKeywordWin.focus();
    }

	function formatNumber1(s) {
		if (s.substring(0,1) != "-") {
			s=s.replace(/\D/g,"");
			rl=s.length;
			l=s.length-3;
			while(l > 0) {
				if(s.substring(rl-1,1) == "."){ //¸¶Áö¸·¿¡ µé¾î¿Â ¹®ÀÚ°¡ ¼Ò¼öÁ¡ÀÌ¸é ±×³É ³»º¸³½´Ù. 
					return s;
				}
				else { 
					s=s.substr(0,l)+","+s.substr(l);
					l-=3;
				}
			}
			return s;
		}else {
			if (s.length > 2) {
				f=s.substring(1)
				f=f.replace(/\D/g,""); 
				l=f.length-3;
				while(l > 0) {
					f=f.substr(0,l)+","+f.substr(l);
					l-=3;
				}
				s = "-"+f 
			}
			return s;
		}
	}

	

	function formatTextfield(tx) {
		var oldv="";
		
		if (event.keyCode == 9) return;
		if(oldv==tx.value) return;
		oldv=tx.value;
		tx.value=formatNumber1(oldv);
	}
