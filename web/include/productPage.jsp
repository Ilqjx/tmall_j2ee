<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<title>模仿天猫官网@Ilqix ${product.name}</title>

<script>
	$(function () {
		var initBigImg = false;
		var stock = ${product.stock};
		$("img.smallImage").mouseenter(function () {
		    var bigImageURL = $(this).attr("bigImageURL");
		    $("img.bigImg").attr("src", bigImageURL);
		})
		
		// 预加载 大图片加载后调用这个函数
		$("img.bigImg").on(function () {
		    if (initBigImg) {
		        return;
		    }
		    $("img.smallImage").each(function () {
		        var bigImageURL = $(this).attr("bigImageURL");
		        var img = new Image();
		        img.src = bigImageURL;
		        // 图像加载完成后
		        img.onload(function () {
		            $("div.img4load").append($(img));
		        })
		    })
		    initBigImg = true;
		})
		
		$("input.productNumberSetting").keyup(function () {
	        var num = $(this).val();
	        num = parseInt(num);
	        if (isNaN(num)) {
	            $(this).val("");
	            return;
	        }
	        if (num < 1) {
	            num = 1;
	        }
	        if (num > stock) {
	            num = stock;
	        }
	        $(this).val(num);
	    })
	    
	    $("a.increaseNumber").on("click", function () {
	        var num = $("input.productNumberSetting").val();
	        num = parseInt(num);
	        if (isNaN(num)) {
	            num = 1;
	        } else {
	            num++;
	        }
	        if (num > stock) {
	            num = stock;
	        }
	        $("input.productNumberSetting").val(num);
	    })
	    
	    $("a.decreaseNumber").on("click", function () {
	        var num = $("input.productNumberSetting").val();
	        num = parseInt(num);
	        if (isNaN(num)) {
	            num = 1;
	        } else {
	            num--;
	        }
	        if (num < 1) {
	            num = 1;
	        }
	        $("input.productNumberSetting").val(num);
	    })
	    
		$("div.productReviewDiv").hide();
		
	    $("a.productDetailTopReviewLink").click(function() {
	        $("div.productDetailDiv").hide();
	        $("div.productReviewDiv").show();
	    })
	    
	    $("a.productReviewTopPartSelectedLink").click(function() {
	        $("div.productDetailDiv").show();
	        $("div.productReviewDiv").hide();
	    })
		
		// 设置价格
// 		$("div.originalDiv span.originalPrice").html(formatMoney(${product.originalPrice}));
// 		$("div.promotionDiv span.promotionPrice").html(formatMoney(${product.promotePrice}));
	
		$("button.addCartButton").click(function () {
			var page = "forecheckLogin";
			$.get(
				page,
				function (result) {
					if (result == "success") {
						var pid = ${product.id};
						var num = $("input.productNumberSetting").val();
						var url = "foreaddCart";
						$.get(
							url,
							{"pid" : pid, "num" : num},
							function (res) {
								if (res == "success") {
									$("button.addCartButton").html("已加入购物车");
									$("button.addCartButton").attr("disabled", "disabled");
									$("button.addCartButton").css("background-color", "#d3d3d3");
									$("button.addCartButton").css("border", "1px solid #d3d3d3");
									$("button.addCartButton").css("color", "#000");
									var cartNumber = parseInt(${cartTotalItemNumber}) + parseInt(num);
									$("strong").html(cartNumber);
								}
							}
						)
					} else {
						$("div#loginModal").modal('show');
					}
				}
			)
			// 阻止浏览器对默认事件的处理
			return false;
		})
		
		$("button.buyButton").click(function () {
			var url = "forecheckLogin";
			$.get(
				url,
				function (res) {
					if (res == "success") {
						var num = $("input.productNumberSetting").val();
						// 本页面的跳转
						location.href = $("a.buyLink").attr("href") + "&num=" + num;
					} else {
						$("div#loginModal").modal('show');
					}
				}
			)
			return false;
		})
	})
</script>

<link href="css/fore/productPage.css" rel="stylesheet">

<div class="categoryPictureInProductPageDiv">
	<img src="img/category/${product.category.id}.jpg">
</div>
<div class="productPageDiv">
	<div class="imgAndInfo">
		<div class="imgInimgAndInfo">
            <img class="bigImg" src="img/productSingle/${product.firstProductImage.id}.jpg">
            <div class="smallImageDiv">
            	<c:forEach items="${product.productSingleImages}" var="pi" varStatus="st">
            		<c:if test="${st.count <= 5}">
            			<img class="smallImage" src="img/productSingle_Small/${pi.id}.jpg"
                    		bigImageURL="img/productSingle/${pi.id}.jpg">
            		</c:if>
            	</c:forEach>
            </div>
            <div class="img4load hidden"></div>
        </div>
        <!-- basicInfo -->
        <div class="infoInimgAndInfo">
            <div class="productTitle">
                ${product.name}
            </div>
            <div class="productSubTitle">
                <c:forEach items="${fn:split(product.subTitle, ' ')}" var="title">
                	<span>${title}</span>
                </c:forEach>
            </div>
            <div class="productPrice">
                <div class="juhuasuan">
                    <span class="juhuasuanBig">聚划算</span>
                    <span>此商品即将参加聚划算，<span class="juhuasuanTime">1天19小时</span>后开始</span>
                </div>
                <div class="productPriceDiv">
                    <div class="gouwujuanDiv">
                        <img style="height: 16px;" src="img/site/gouwujuan.png">
                        <span>全天猫实物商品通用</span>
                    </div>
                    <div class="originalDiv">
                        <span class="originalPriceDesc">价格</span>
                        <span class="originalPriceYuan">¥</span>
                        <span class="originalPrice">
                        	<fmt:formatNumber value="${product.originalPrice}" minFractionDigits="2" />
                        </span>
                    </div>
                    <div class="promotionDiv">
                        <span class="promotionPriceDesc">促销价</span>
                        <span class="promotionPriceYuan">¥</span>
                        <span class="promotionPrice">
                        	<fmt:formatNumber value="${product.promotePrice}" minFractionDigits="2" />
                        </span>
                    </div>
                </div>
            </div>
            <div class="productSaleAndReviewNumber">
                <div id="firstDiv">销量 <span class="redColor boldWord">${product.saleCount}</span></div>
                <div>累计评价 <span class="redColor boldWord">${product.reviewCount}</span></div>
            </div>
            <div class="productNumber">
                <span>数量</span>
                <span>
                    <span class="productNumberSettingSpan">
                        <input class="productNumberSetting" type="text" value="1">
                    </span>
                    <span class="arrow">
                        <a class="increaseNumber" href="#nowhere">
                            <span class="updown">
                                <img src="img/site/increase.png">
                            </span>
                        </a>
                        <span class="updownMiddle"></span>
                        <a class="decreaseNumber" href="#nowhere">
                            <span class="updown">
                                <img src="img/site/decrease.png">
                            </span>
                        </a>
                    </span>
					件
				</span>
                <span>库存${product.stock}件</span>
            </div>
            <div class="serviceCommitment">
                <span class="serviceCommitmentDesc">服务承诺</span>
                <span class="serviceCommitmentLink">
                    <a href="#nowhere">正品保证</a>
                    <a href="#nowhere">极速退款</a>
                    <a href="#nowhere">赠运费险</a>
                    <a href="#nowhere">七天无理由退换</a>
                </span>
            </div>
            <div class="buyDiv">
                <a class="buyLink" href="forebuyone?pid=${product.id}">
                	<button class="buyButton">立即购买</button>
                </a>
                <a class="addCartLink" href="#nowhere">
                	<button class="addCartButton">
                		<span class="glyphicon glyphicon-shopping-cart"></span>加入购物车
                	</button>
                </a>
            </div>
        </div>
        <div style="clear: both;"></div>
    </div>
</div>
<!-- InfoDetail -->
<div class="productDetailDiv">
    <div class="productDetailTopPart">
        <a class="productDetailTopPartSelectedLink selected" href="#nowhere">商品详情</a>
        <a class="productDetailTopReviewLink" href="#nowhere">累计评价
            <span class="productDetailTopReviewLinkNumber">${product.reviewCount}</span>
		</a>
    </div>
    <div class="productParamterPart">
        <div class="productParamter">产品参数：</div>
        <div class="productParamterList">
        	<c:forEach items="${pvs}" var="pv">
        		<span>${pv.property.name}: ${fn:substring(pv.value, 0, 10)}</span>
        	</c:forEach>
        </div>
        <div style="clear: both"></div>
    </div>
    <div class="productDetailImagesPart">
    	<c:forEach items="${product.productDetailImages}" var="pi">
    		<img src="img/productDetail/${pi.id}.jpg">
    	</c:forEach>
    </div>
</div>
<!-- AddComment -->
<div class="productPageDiv">
    <div class="productReviewDiv">
        <div class="productReviewTopPart">
            <a class="productReviewTopPartSelectedLink" href="#nowhere">商品详情</a>
            <a class="selected" href="#bowhere">累计评价
                <span class="productReviewTopReviewLinkNumber">${product.reviewCount}</span>
			</a>
        </div>
        <div class="productReviewContentPart">
        	<c:forEach items="${reviews}" var="review">
        		<div class="productReviewItem">
        			<div class="productReviewItemDesc">
        				<div class="productReviewItemContent">
        					${review.content}
        				</div>
        				<div class="productReviewItemDate">
        					<fmt:formatDate value="${review.createDate}" pattern="yyyy-MM-dd" />
        				</div>
        			</div>
        			<div class="productReviewItemUserInfo">
                    	${review.user.anonymousName}<span class="userInfoGrayPart">（匿名）</span>
                	</div>
                	<div style="clear: both"></div>
        		</div>
        	</c:forEach>
        </div>
    </div>
</div>
