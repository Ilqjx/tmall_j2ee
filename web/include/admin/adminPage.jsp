<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<script>
	// 按钮不可点击
	// disabled 虽然显示禁用但是还可以点击
	$(function () {
		$("ul.pagination li.disabled a").click(function () {
			return false;
		})
	})
</script>

<nav>
	<ul class="pagination">
		<li <c:if test="${!page.hasPrevious}">class="disabled"</c:if> >
			<a href="?page.start=0${page.param}">
				<span>«</span>
			</a>
		</li>
		<li <c:if test="${!page.hasPrevious}">class="disabled"</c:if> >
			<a href="?page.start=${page.start-page.count}${page.param}">
				<span>‹</span>
			</a>
		</li>
		
		<c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
			<c:if test="${status.count>=(page.start/page.count+1) && status.count<=(page.start/page.count+5)}">
<!-- 				当前页面不可点击 -->
				<li <c:if test="${status.index*page.count==page.start}">class="disabled"</c:if> >
					<a href="?page.start=${status.index*page.count}${page.param}"
							<c:if test="${status.index*page.count==page.start}">class="current"</c:if> >
						${status.count}
					</a>
				</li>
			</c:if>
		</c:forEach>
		
		<li <c:if test="${!page.hasNext}">class="disabled"</c:if> >
			<a href="?page.start=${page.start+page.count}${page.param}">
				<span>›</span>
			</a>
		</li>
		<li <c:if test="${!page.hasNext}">class="disabled"</c:if> >
			<a href="?page.start=${page.last}${page.param}">
				<span>»</span>
			</a>
		</li>
	</ul>
</nav>
