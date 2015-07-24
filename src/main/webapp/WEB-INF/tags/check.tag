<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>

    .check {
        font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
        font-size: 11px;
    }
    .date {
        position: absolute;
        margin-left: ${checkConfig.dateX}px;
        margin-top: ${checkConfig.dateY}px;
    }

    .payee {
        position: absolute;
        margin-left: ${checkConfig.payeeX}px;
        margin-top: ${checkConfig.payeeY}px;
        width: ${checkConfig.payeeW}px;
    }

    .numericAmount {
        position: absolute;
        margin-left: ${checkConfig.numericAmountX}px;
        margin-top: ${checkConfig.numericAmountY}px;
}

    .alphaAmount {
        position: absolute;
        margin-left: ${checkConfig.alphaAmountX}px;
        margin-top: ${checkConfig.alphaAmountY}px;
        width: ${checkConfig.alphaAmountW}px;
    }
    .sig1 {
        position: absolute;
        text-align: center;
        margin-left: ${checkConfig.sig1X}px;
        margin-top: ${checkConfig.sig1Y}px;
        border-top: 1px solid #003399;
        min-width: 100px;
    }

    .sig2 {
        position: absolute;
        text-align: center;
        margin-left: ${checkConfig.sig2X}px;
        margin-top: ${checkConfig.sig2Y}px;
        border-top: 1px solid #003399;
        min-width: 100px;
    }

    .checkNo {
        position: absolute;
        margin-left: ${checkConfig.checkNoX}px;
        margin-top: ${checkConfig.checkNoY}px;
    }

</style>


<div class="check">
    <span class="checkNo">${checkConfig.checkNoPrefix} ${check.checkNumber}</span>
    <span class="date">${check.date}</span>
    <span class="payee">${check.payee}</span>
    <span class="numericAmount">${check.numericAmount}</span>
    <span class="alphaAmount">${check.alphaAmount}</span>
    <c:if test="${checkConfig.withSigner}">
        <c:if test="${check.sig1 != null && check.sig1.length() > 0}">
            <span class="sig1">${check.sig1}</span>
        </c:if>
        <c:if test="${check.sig2 != null && check.sig2.length() > 0}">
            <span class="sig2">${check.sig2}</span>
        </c:if>
    </c:if>
</div>