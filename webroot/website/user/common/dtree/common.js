function openWinC(p_url,p_target,p_option)
{
	var v_arr ;
	var vwidth = 400;
	var vheight = 300;

	if (p_option.indexOf("width=") > -1)
	{
		v_arr = p_option.split(",");
		for (i = 0; i < v_arr.length; i++)
		{
			if (v_arr[i].indexOf("width=") > -1)
			{
				vwidth = v_arr[i].substring(v_arr[i].indexOf("=") +1, v_arr[i].length);
				break;
			}
		}
	}

	if (p_option.indexOf("height=") > -1)
	{
		v_arr = p_option.split(",");
		for (i = 0; i < v_arr.length; i++)
		{
			if (v_arr[i].indexOf("height=") > -1)
			{
				vheight = v_arr[i].substring(v_arr[i].indexOf("=") +1, v_arr[i].length);
				break;
			}
		}
	}

	var winl = (screen.width - vwidth) / 2;
	var wint = (screen.height - vheight) / 2;
	window.open(p_url,p_target,p_option +",top="+ wint +",left="+ winl);

	p_url = null;
	p_option = null;
	p_target = null;
}

