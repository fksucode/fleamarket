<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h:wrapper>
	<jsp:attribute name="head">
		<link href="${pageContext.request.contextPath}/resources/css/blitzer/jquery-ui-1.10.1.custom.min.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/resources/css/jquery.jWizard.min.css" rel="stylesheet" type="text/css">
    	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.10.1.custom.js"></script>
    	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.jWizard.min.js"></script>
    	<script type="text/javascript">
    	 	$(function() {
    	 		// expand with EL
    	 		var currentUser = "${pageContext.request.userPrincipal.name}"; 
    	 		var itemId = parseInt("${item.itemId}");
    	 		var initialPrice = parseFloat("${item.initialPrice}");
    	 		
    	 		var tips = $(".dialogTips");
				function updateTips(t) {
    	 			tips.text(t).addClass("ui-state-highlight");
    	 			setTimeout(function() {
    	 				tips.removeClass("ui-state-highlight", 1500);
    	 			}, 500 );
    	 		}
    	 		function checkRegexp(o, regexp, n) {
    	 			if (!(regexp.test(o.val()))) {
    	 				o.addClass("ui-state-error");
    	 				updateTips(n);
    	 				return false;
    	 			} else {
    	 				return true;
    	 			}
    	 		}
    	 		function checkBidSize(o, minimum) {
    	 			if (o.val() < minimum) {
    	 				o.addClass("ui-state-error");
    	 				updateTips("Bid must be at least " + minimum);
    	 				return false;
    	 			} else {
    	 				return true;
    	 			}
    	 		}
    	 		
    	 		$("#dialog-confirm").dialog({
    	 			autoOpen: false,
    		 		resizable: false,
    		 		height: 150,
    		 		modal: true,
    		 		buttons: {
    		 			"Delete": function() {
    		 				// navigate to the item list
    		 				$(this).dialog("close");
    		 				window.location.replace("${pageContext.request.contextPath}/actions/delete-item?itemId=" + itemId);
    		 			},
    		 			Cancel: function() {
    		 				$(this).dialog("close");
    		 			}
    		 		}
    		 	});
    	 		$("#dialog-form").dialog({
    	 			autoOpen: false,
    	 			height: 300,
    	 			width: 450,
    	 			modal: true,
    	 			buttons: {
    	 				"Add": function() {
	    	 				var bid = $("#bid");
    	 					bid.removeClass("ui-state-error");
			    	 		var valid = checkRegexp(bid, /^\d+(\.\d{1,2})?$/, "Only positive numbers with two decimal digits are accepted");
			    	 		valid = valid && checkBidSize(bid, initialPrice);
			    	 		if (valid) {
			    	 			$.getJSON("${pageContext.request.contextPath}/actions/add-bid",
			    	 				{
			    	 					bidderId: currentUser,
			    	 					itemId: itemId, 
			    	 					price: bid.val()
			    	 				}, 
			    	 				function (response) {
			    	 					bid.val("");
			    	 					updateTips("Sucessfully added bid with id " + response.bidId);
			    	 					console.log("bid added");
			    	 				}
			    	 			).fail(function(jqXHR, textStatus, errorThrown) {
			    	 				updateTips("Error occurred while adding bid!" + textStatus + " " + errorThrown);
			    	 			});
	    	 				};
    	 				},
 			 			Close: function() {
    	 					$(this).dialog("close");
    	 				}
    	 			},
    	 			close: function() {
    	 				$("#bid").val("").removeClass("ui-state-error");
    	 				tips.text("");
    	 			}
    	 		});
    	 		$("#wizard").jWizard({
    	 			title: false,
    	 			menu: true    	 			
    	 		});
    	 		
    	 		$("#wizard fieldset").bind("stephide", function(event) {
    	 			var stepData = {};
    	 			var stepId = $(event.target).attr("id");
    	 			$(":input", event.target).each(function() {
   	 					stepData[this.name] = $(this).val();
    	 			});
	    	 		$.post("${pageContext.request.contextPath}/actions/order-item", {
	    	 			async: false,
	    	 			step: stepId,
	    	 			stepData: JSON.stringify(stepData)
	    	 		}, function() {/*ignore*/});
    	 		});
    	 		
    	 		$("#wizard").bind("wizardcancel", function() {
    	 			$("#dialog-wizard").dialog("close");
    	 		}); 
    	 		
    	 		$("#wizard").bind("wizardfinish", function() {
    	 			window.location.replace("${pageContext.request.contextPath}/actions/order-item?step=finish");
    	 			return false;
    	 		});    	 		
    	 		
    	 		$("#dialog-wizard").dialog({
    	 			autoOpen: false,
    	 			height: 450,
    	 			width: 700,
    	 			modal: true
    	 		});
    	 		
    	 		$("#delete-button").click(function() {
     	 			$("#dialog-confirm").dialog("open");
    	 		});
    	 		
    	 		$("#bid-button").click(function() {
    	 			$("#dialog-form").dialog("open");
    	 		});
    	 		
    	 		$("#order-button").click(function() {
    	 			$("#dialog-wizard :input:not([readonly])").each(function() {
   	 					$(this).val("");
    	 			});
    	 			$("#dialog-wizard").dialog("open");
    	 		});
    		});
		</script>
  	</jsp:attribute>

	<jsp:body>
			<h2>Item ${item.itemId}</h2>
			<fieldset class="labels-left">
				<legend>Item information</legend>
				<label for="title">Title</label>
				<span id="title">${item.title}</span>
				<label for="bidEndDate">Bid end date</label> 
				<span id="bidEndDate">${item.bidEndDate}</span>
				<label for="initialPrice">Initial price</label> 
				<span id="initialPrice">${item.initialPrice}</span>
				<label for="description">Description</label>
				<textarea id="description" name="description" rows="2" maxlength="500" readonly="readonly">${item.description}</textarea>
			</fieldset>
			<div>
				<c:if test="${canBid and isCurrentUserBidder}">
					<input type="button" id="bid-button" value="Bid"/>
				</c:if>
				<c:if test="${canOrder}">
					<input type="button" id="order-button" value="Order"/>
				</c:if>
				<c:if test="${canDelete}">
					<input type="button" id="delete-button" value="Delete"/> 
				</c:if>
			</div>
						
			<div id="dialogs" style="display: none;">
				<div id="dialog-confirm" title="Delete this item?" >
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>Are you sure?</p>
				</div>
				<div id="dialog-form" title="Add bid" >
					<form>
						<fieldset>
							<label for="bid">Your bid:</label>
							<input type="number" name="bid" id="bid" class="text ui-widget-content ui-corner-all" placeholder="minimum value ${item.initialPrice}"/>
						</fieldset>
					</form>
					<p class="dialogTips"></p>
				</div>
				
				<div id="dialog-wizard" title="Order">
					<p>Provide the following information:</p>
					<form id="wizard" action="${pageContext.request.contextPath}/actions/order-item?step=finish">
						<fieldset id="item" class="labels-left">
							<legend>Item information</legend>
							<label for="order-itemId">Item id</label>
							<input type="text" id="order-itemId" name="itemId" readonly="readonly" value="${item.itemId}">
							<label for="order-itemTitle">Item title</label>
							<input type="text" id="order-itemTitle" name="itemTitle" readonly="readonly" value="${item.title}"/>
							<label for="order-description">Item title</label>
							<textarea id="order-description" name="description" rows="2" maxlength="500" readonly="readonly">${item.description}</textarea>
						</fieldset>
			 			<fieldset id="billing" class="labels-left">
							<legend>Billing information</legend>
							<label for="accountNumber">Account </label>
							<input type="text" id="accountNumber" name="accountNumber" required="required" maxlength="10" placeholder="max 10 characters"/>
							<label for="secretCode">Secret code</label>
							<input type="password" id="secretCode" name="secretCode" required="required" maxlength="4" placeholder="4 characters"/>
							<label for="expiryDate">Expiry date</label> 
							<input type="date" id="expiryDate" name="expiryDate" required="required" placeholder="format 'yyyy-MM-dd'" pattern="yyyy-MM-dd"/>
						</fieldset>
						<fieldset id="shipping" class="labels-left">
							<legend>Shipping information</legend>
							<label for="streetName">Street name</label> 
							<input type="text" id="streetName" name="streetName" />
							<label for="city">City</label> 
							<input type="text" id="city" name="city" />
							<label for="zipCode">Zip code</label> 
							<input type="number" id="zipCode" name="zipCode" />
							<label for="country">Country</label> 
							<input type="text" id="country" name="country" />
						</fieldset>
						<fieldset id="confirm" class="labels-left">
							<legend>Confirm</legend>
							If you have provided all necessary information for the order of item '${item.title}', complete the wizard.
						</fieldset>
					</form>
				</div>
			</div>

	</jsp:body>
</h:wrapper>