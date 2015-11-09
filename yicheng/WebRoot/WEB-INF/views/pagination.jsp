<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<ul class="pagination">
    <li id="previous-page" >
      <a href="#" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
<!--     <li><a href="#">1</a></li>
    <li><a href="#">2</a></li>
    <li><a href="#">3</a></li>
    <li><a href="#">4</a></li>
    <li><a href="#">5</a></li> -->
    <li id="next-page" >
      <a href="#" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
</ul>

<script type="text/javascript">
	// set basic variables
	var ITEMS_PER_PAGE = "${model.itemsPerPage}";
	var baseUrl = "${model.baseUrl}";
	var itemCount = "${model.itemCount}" / 1;
	var pageCount = Math.ceil(itemCount / ITEMS_PER_PAGE);
	var pageIndex = "${model.pageIndex}" / 1;

	console.log("itemsPerPage: " + ITEMS_PER_PAGE);
	console.log("itemCount: " + itemCount);
	console.log("pageCount: " + pageCount);
	console.log("pageIndex: " + pageIndex);
	// set start and end index 
	var startIndex = pageIndex - 2;
	if(startIndex < 0) {
		startIndex = 0;
	}
	var endIndex = startIndex + 4;
	if(endIndex > pageCount - 1) {
		endIndex = pageCount - 1;
	}
	startIndex = endIndex - 4;
	if(startIndex < 0) {
		startIndex = 0;
	}

	console.log("startIndex: " + startIndex);
	console.log("endIndex: " + endIndex);

	var $previousPage = $("#previous-page");
	var $nextPage = $("#next-page");
	// set pages
	for(var i = startIndex; i <= endIndex; i++) {
		var $li;
		var $pageLink = $("<a href='" + baseUrl + "?pageIndex=" + i + "'>" + (i + 1) + "</a>");
		if(i == pageIndex) {
			$li = $("<li class='active'>")
		}else {	
			$li = $("<li>")
		}
		$li.append($pageLink);
		$nextPage.before($li);

		$previousPage.find("a").attr("href", baseUrl + "?pageIndex=0");
		$nextPage.find("a").attr("href", baseUrl + "?pageIndex=" + endIndex);
	}

</script>	