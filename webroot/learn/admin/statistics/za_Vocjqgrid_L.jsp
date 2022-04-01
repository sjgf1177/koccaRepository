<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
         <title>JQGrid Special</title>                  
         
        <!-- <link rel="stylesheet" type="text/css" href="/css/jqueryui/1.9.2/themes/redmond/jquery-ui.css" /> -->
		<!-- <script type="text/javascript" src="/js/jquery/1.8.3/jquery.js"></script> -->
    	<!-- <script type="text/javascript" src="/js/jqueryui/1.9.2/jquery-ui.js"></script>
 -->
         <script src="/js/assets/gridJs/grid.locale-en.js"  type="text/javascript"></script>  
         
         <script type="text/javascript">
	        $.jgrid.no_legacy_api = true;
	        $.jgrid.useJSON = true;
	     </script>
	    
	    
         <script src="/js/assets/gridJs/jquery.jqGrid.min.js" type="text/javascript"></script>  
         <script type="text/javascript" src="http://www.ok-soft-gmbh.com/jqGrid/jquery.jqGrid-4.4.1/js/jquery.jqGrid.src.js"></script>
         <link href="/js/assets/gridJs/ui.jqgrid.css" rel="stylesheet" type="text/css" />  
         
         
    
        <script type="text/javascript">
    //<![CDATA[
        /*global $ */
        $(function () {
            "use strict";
            var mydata = [
                    {id: "12345", name: "Desktop Computer", note: "note",      stock: "Yes", ship: "FE", sdate: "2007-12-03"},
                    {id: "23456", name: "Laptop",           note: "Long text", stock: "Yes", ship: "IN", sdate: "2007-12-03"},
                    {id: "34567", name: "LCD Monitor",      note: "note3",     stock: "Yes", ship: "TN", sdate: "2007-12-03"},
                    {id: "45678", name: "Speakers",         note: "note",      stock: "No",  ship: "AR", sdate: "2007-12-03"},
                    {id: "56789", name: "Laser Printer",    note: "note2",     stock: "Yes", ship: "FE", sdate: "2007-12-03"},
                    {id: "67890", name: "Play Station",     note: "note3",     stock: "No",  ship: "FE", sdate: "2007-12-03"},
                    {id: "76543", name: "MobileTelephone",  note: "note",      stock: "Yes", ship: "AR", sdate: "2007-12-03"},
                    {id: "87654", name: "Server",           note: "note2",     stock: "Yes", ship: "TN", sdate: "2007-12-03"},
                    {id: "98765", name: "Matrix Printer",   note: "note3",     stock: "No",  ship: "FE", sdate: "2007-12-03"}
                ],
                $grid = $("#rowed6"),
                initDate = function (elem) {
                    $(elem).datepicker({
                        dateFormat: "yy-mm-dd",
                        //autoSize: true,
                        defaultDate: new Date(2007, 11, 3),
                        changeYear: true,
                        changeMonth: true,
                        showButtonPanel: true,
                        showWeek: true,
                        onSelect: function () {
                            var $grid, grid;
                            if (typeof (elem.id) === "string" && elem.id.substr(0, 3) === "gs_") {
                                $grid = $(elem).closest("div.ui-jqgrid-hdiv")
                                            .next("div.ui-jqgrid-bdiv")
                                            .find("table.ui-jqgrid-btable:first");
                                if ($grid.length > 0) {
                                    grid = $grid[0];
                                    if ($.isFunction(grid.triggerToolbar)) {
                                        grid.triggerToolbar();
                                    }
                                }
                            } else {
                                // to refresh the filter
                                $(elem).trigger("change");
                            }
                        }
                    });
                },
                dateTemplate = {align: "center", sorttype: "date", width: 94,
                    editrules: { date: true }, editoptions: { dataInit: initDate },
                    searchoptions: {
                        sopt: ["eq", "ne", "lt", "le", "gt", "ge"],
                        attr: { maxlength: 10, size: 11 }, // for searching toolbar
                        maxlength: 10, size: 11, // for searching dialog
                        dataInit: initDate
                    },
                    formatter: "date", formatoptions: { srcformat: "ISO8601Short", newformat: "Y-m-d" }},
                lastsel3;

            $grid.jqGrid({
                datatype: "local",
                data: mydata,
                colNames: ["Last Sales", "Name", "Stock", "Ship via", "Notes"],
                colModel: [
                    { name: "sdate", template: dateTemplate },
                    { name: "name", width: 115, editoptions: {maxlength: "30"} },
                    { name: "stock", width: 70, align: "center", editable: true, formatter: "checkbox",
                        edittype: "checkbox", editoptions: {value: "Yes:No", defaultValue: "Yes"},
                        stype: "select", searchoptions: { sopt: ["eq", "ne"], value: ":Any;true:Yes;false:No" } },
                    { name: "ship", width: 105, align: "center", editable: true, formatter: "select",
                        edittype: "select", editoptions: { value: "FE:FedEx;IN:InTime;TN:TNT;AR:ARAMEX", defaultValue: "AR" },
                        stype: "select", searchoptions: { sopt: ["eq", "ne"],
                        value: ":Any;FE:FedEx;IN:InTime;TN:TNT;AR:ARAMEX" } },
                    { name: "note", width: 60, sortable: false, editable: true, edittype: "textarea" }
                ],
                cmTemplate: {editable: true},
                rowNum: 10,
                rowList: [5, 10, 100],
                pager: "#pager",
                gridview: true,
                rownumbers: true,
                autoencode: true,
                ignoreCase: false,
                viewrecords: true,
                sortname: "sdate",
                sortorder: "desc",
                height: "100%",
                onSelectRow: function (id) {
                    if (id && id !== lastsel3) {
                        $(this).jqGrid("restoreRow", lastsel3);
                        $(this).jqGrid("editRow", id, true);
                        lastsel3 = id;
                    }
                },
                editurl: "/MyServletNameHere",
                caption: "Date Picker Integration"
            });
            $.extend($.jgrid.search, {
                recreateFilter: true,
                multipleSearch: true,
                multipleGroup: true,
                closeOnEscape: true,
                closeAfterSearch: true,
                overlay: 0,
                searchOnEnter: true
            });

            $grid.jqGrid("navGrid", "#pager", {add: false, edit: false, del: false});
            //$grid.jqGrid("filterToolbar", {defaultSearch: "cn", stringResult: true});
        });
    //]]>
    </script>
</head>
<body>
    <table id="rowed6"><tr><td></td></tr></table>
    <div id="pager"></div>
<%@ include file = "/learn/library/getJspName.jsp" %>    
</body>
</html>