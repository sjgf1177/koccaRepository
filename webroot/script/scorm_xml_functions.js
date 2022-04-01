/** The xml_functions.js file is made up of:  ***
***	formFunctions.js and outputFunctions.js   **/

/******************************************************************************
**
** Filename:  formFunctions.js
**
** File Description: The formFunctions file contains a library of functions
**    used by the Metadata Generator HTML form pages.
**
** Author:  ADLI Project
**
** Company Name: Concurrent Technologies Corporation (CTC)
**
** Module/Package Name:
** Module/Package Description:
**
** Design Issues:
**
** Implementation Issues:
** Known Problems:
** Side Effects:
**
*******************************************************************************
**
** Concurrent Technologies Corporation (CTC) grants you ("Licensee") a non-
** exclusive, royalty free, license to use, modify and redistribute this
** software in source and binary code form, provided that i) this copyright
** notice and license appear on all copies of the software; and ii) Licensee
** does not utilize the software in a manner which is disparaging to CTC.
**
** This software is provided "AS IS," without a warranty of any kind.  ALL
** EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
** IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-
** INFRINGEMENT, ARE HEREBY EXCLUDED.  CTC AND ITS LICENSORS SHALL NOT BE LIABLE
** FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
** DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES.  IN NO EVENT WILL CTC  OR ITS
** LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
** INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
** CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
** OR INABILITY TO USE SOFTWARE, EVEN IF CTC  HAS BEEN ADVISED OF THE
** POSSIBILITY OF SUCH DAMAGES.
**
*******************************************************************************
**
** Date Changed   Author of Change  Reason for Changes
** ------------   ----------------  -------------------------------------------
** 4/12/01		   Tom Hopkins		isValidDate was incorrectly checking dates.
**
******************************************************************************/


/*************************************************************************
**
** Method:  isEmpty()
** Input:   string
** Output:  none
**
** Description:
**    Checks to see if the string is null or it's length is 0.
**
*************************************************************************/
function isEmpty( s )
{
   return ( ( s == null ) || ( s.length == 0 ) );
}

/*************************************************************************
**
** Method:  isFieldBlank)
** Input:   string
** Output:  none
**
** Description:
**    Checks to see if the fields length is 0.
**
*************************************************************************/
function isFieldBlank( theField )
{
   if ( theField.length == 0 )
   {
      return true;
   }
   else
   {
      return false;
   }
}

/*************************************************************************
**
** Method:  isDigit()
** Input:   character
** Output:  none
**
** Description:
**    Checks to see if the character is integer between 0 and 9.
**
*************************************************************************/
function isDigit( c )
{
   return ( ( c >= "0" ) && ( c <= "9" ) );
}

/*************************************************************************
**
** Method:  isInteger()
** Input:   string
** Output:  none
**
** Description:
**    Checks to see if the string is an integer.
**
*************************************************************************/
function isInteger( s )
{
   var i;
   
   for ( i = 0; i < s.length; i++ )
   {   
      var c = s.charAt(i);
      
      if ( ! isDigit(c) )
      {
         return false;
      }
   }
   return true;
}

/*************************************************************************
**
** Method:  isValidDate()
** Input:   string
** Output:  none
**
** Description:
**    Checks to see if the string is a valid date format which follows:
**    yyyy-mm-dd.
**
*************************************************************************/
function isValidDate( field )
// yyyy-mm-dd
{
   var vDate = field;
   var isValid = true;
   
   if ( vDate.length < 10 )
   {
      isValid = false;
   }
   else
   {
      isValid = ( vDate.charAt(4) == '-' ) && ( vDate.charAt(7) == '-' );
      if ( isValid )
      {
         var vMonth = vDate.substring(5,7);
         var vDay = vDate.substring(8,10);
         var vYear = vDate.substring(0,4);
         if ( isInteger(vMonth) && isInteger(vDay) && isInteger(vYear) )
         {
            if ( vMonth.charAt(0) == '0' )
            {
               isValid = isValid && ( vMonth.charAt(1) != '0' );
               if ( vDay.charAt(0) == '0' )
               {
                  isValid = isValid && ( vDay.charAt(1) != '0' );
               }
               else
               {
                  isValid = isValid && ( 0 < parseInt(vDay) ) &&
                     ( parseInt(vDay) < 32 );
               }
            }
            else
            {
               isValid = isValid && ( 0 < parseInt(vMonth) ) &&
                  ( parseInt(vMonth) < 13 );
               
               if ( vDay.charAt(0) == '0' )
               {
                  isValid = isValid && ( vDay.charAt(1) != '0' );
               }
               else
               {
                  isValid = isValid && ( 0 < parseInt(vDay) ) &&
                     ( parseInt(vDay) < 32 );
               }
               
               isValid = isValid  && ( 999 < parseInt(vYear) );
            }
         }
         else
         {
            isValid = false;
         }
      }// if (isValid)
   }// if (vDate.length)...
   
   return isValid;
} // isValidDate


/*************************************************************************
**
** Method:  isValidDuration()
** Input:   string
** Output:  none
**
** Description:
**    Checks to see if the string is a valid duration format which follows:
**    hh:mm:ss.
**
*************************************************************************/
function isValidDuration( field )
// hh:mm:ss
{
   var vDuration = field;
   var isValid = true;
   
   if ( vDuration.length < 8 )
   {
      isValid = false;
   }
   else
   {
      isValid = ( vDuration.charAt(2) == ':' ) && ( vDuration.charAt(5) == ':' );
      if ( isValid )
      {
         var vHH = vDuration.substring(0,2);
         var vMM = vDuration.substring(3,5);
         var vSS = vDuration.substring(6,8);
         
         if ( isInteger(vHH) && isInteger(vMM) && isInteger(vSS) )
         {
            isValid = isValid && ( 0 <= parseInt(vHH) ) &&
               ( parseInt(vHH) < 100 );
            isValid = isValid && ( 0 <= parseInt(vMM) ) &&
               ( parseInt(vMM) < 60 );
            isValid = isValid && ( 0 <= parseInt(vSS) ) &&
               ( parseInt(vSS) < 60 );
         }
      }
      else
      {
         isValid = false;
      }
   }
   
   return isValid;
} // isValidDuration

/*************************************************************************
**
**	The following functions are for output.
**
*************************************************************************/



/*************************************************************************
**
** Method:  getParams()
** Input:   none
** Output:  none
**
** Description:
**    Uses the location bar to get the posted information from the form HTML
**    page and creates an array of strings with this information
**
*************************************************************************/
function getParams()
{
   var urlString = window.location.href;
   var params = new Array();
   {
      var urlDataArr = urlString.substring(urlString).split('?');
      
      var dataArr = urlDataArr[1].substring(urlDataArr[1]).split('&');
      
      for (var i=0; i<dataArr.length; i++)
      {
         nameValArr = dataArr[i].split('=');
         params[nameValArr[0]] = nameValArr[1];
         
         var parts = nameValArr[1].split('+');
         if (parts.length > 0)
         {
            var newStr = parts[0];
            
            for (var k=1; k<parts.length; k++)
            {
               newStr += " " + parts[k];
            }
            
            params[nameValArr[0]] = newStr;
         }
         
         params[nameValArr[0]] = unescape( params[nameValArr[0]]);
         
      }
   }

   return params;
}

/*************************************************************************
**
** Method:  trim_string()
** Input:   string
** Output:  none
**
** Description:
**    Strips the string from white space before and after it.
**
*************************************************************************/
function  trim_string( the_string )
{
   var is_found = false;
   var f = 0;
   var l = the_string.length;
   
   for ( i = 0; ( ( i < the_string.length - 1 ) && ( ! is_found ) ); i++ )
   {
      if ( the_string.charAt(i) != ' ' )
      {
         f = i;
         is_found = true;
      }
   }
   is_found = false; 

   for ( i = the_string.length - 1; ( ( i > 0 ) && ( ! is_found ) ); i-- )
   {
      if ( the_string.charAt(i) != ' ' )
      {
         var l = i + 1;
         
         is_found = true;
      }
   }
   var new_string = the_string.substring( f, l );
   
   return new_string;
}

/*************************************************************************
**
** Method:  indent()
** Input:   int
** Output:  none
**
** Description:
**    Returns a string of "&nbsp;" based on the number desired.
**
*************************************************************************/
function indent( spaces )
{  
   spaceChar = "&nbsp;"
   var msg = "";
   
   for ( i = 0; i < spaces; i++)
   {
      msg += spaceChar;
   }
   
   return msg;
}

/*************************************************************************
**
** Method:  tab()
** Input:   int
** Output:  none
**
** Description:
**    Returns a string of "&nbsp;" based on the number of indentions desired.
**
*************************************************************************/
function tab( num )
{
   var tabSpace = 3;
   var msg = "";
   
   for ( j = 0; j < num; j++)
   {
      msg += indent(tabSpace);
   }
   
   return msg;
}

/*************************************************************************
**
** Method:  tag()
** Input:   int
** Output:  none
**
** Description:
**    Returns a the input string with a "&lt;" before and a "&gt;" after it.
**
*************************************************************************/
function tag( name )
{
   var openTag = "&lt;";
   var closeTag = "&gt;";
   
   return openTag + name + closeTag;
}

/*************************************************************************
**
** Method:  tag2()
** Input:   int
** Output:  none
**
** Description:
**    Returns a the input string with a "&lt;" before and a "&gt;" after it.
**
*************************************************************************/
function tag2( name )
{
   var openTag = "<";
   var closeTag = ">";
   
   return openTag + name + closeTag;
}