<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<%--@elvariable id="card" type="com.killard.board.jdo.board.CardDO"--%>
<form action="<c:url value="/game/${package.name}/element/${element.name}/card/${card.name}/delete"/>" method="POST"
      class="horizontal">
    <fieldset>
        <legend>${card.descriptor.name}</legend>
        <fieldset>
            <legend>Delete Card?</legend>
            <div class="field">
                <input type="submit" value="Confirm"/>
            </div>
        </fieldset>
    </fieldset>
</form>