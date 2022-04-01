

//<!--
// Ư������ üũ
// true:Ư������, false:�Ϲݹ���
function spChar(str){
	var m_Sp = /[$\\@\\\#%\^\&\*\(\)\[\]\+\_\{\}\`\~\=\|]/;
	var m_val = str;
	var strLen = m_val.length;
	var m_char = m_val.charAt((strLen) - 1);
	if(m_char.search(m_Sp) != -1) {
		return true;
	}else {
		return false;
	}
}
//-->


//<!--
// string�� ���� trim �Լ� - ������ ����� ��� string�� ��ȯ
function trim(str){
		var len = str.length;
		for ( i=len; i > 0; i--){
				if (str.charAt(i-1) == ' ')
						str = str.substring(0, i-1);
				else return str;
		}
		return str;
}
//-->

