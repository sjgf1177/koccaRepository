        </div>
<!-- content 끝 -->
    </div>
    <div id="allmenu" style="display:none;"></div>
</div>
<!--// container -->
<hr />
    <!-- Footer Menu 시작 -->
    <%if(mainType.equals("A")){%>
	    <%@ include file="/learn/user/portal/include/newSubFooterA.jsp" %>
    <%}else if(mainType.equals("B")){%>
	    <%@ include file="/learn/user/portal/include/newSubFooterB.jsp" %>
    <%}else{%>
	    <%@ include file="/learn/user/portal/include/footer_include.jsp" %>
    <%}%>
    <!-- Footer Menu 종료 -->

</body>
</html> 