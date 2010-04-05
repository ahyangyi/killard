<%@ include file="../includes.jsp" %>
<%--@elvariable id="descriptable" type="com.killard.board.jdo.DescriptableDO"--%>
<fieldset>
    <legend>${descriptable.descriptor.name}</legend>
    <fieldset>
        <legend>Descriptions&nbsp;<a href="#" class="new" options="locale_options">new</a></legend>
        <c:forEach var="descriptor" items="${descriptable.descriptors}">
            <fieldset>
                <div class="field">
                    <label for="locale_${descriptor.locale}">Locale</label>
                    <select id="locale_${descriptor.locale}" name="locales">
                        <option value="${descriptor.locale}">${descriptor.locale}</option>
                    </select>
                </div>
                <div class="field">
                    <label for="name_${descriptor.locale}">Name</label>
                    <input type="text" id="name_${descriptor.locale}" name="names" value="${descriptor.name}"/>
                </div>
                <div class="field">
                    <label for="description_${descriptor.locale}">Description</label>
                    <textarea rows="5" cols="25" id="description_${descriptor.locale}" name="descriptions">${descriptor.description}</textarea>
                </div>
            </fieldset>
        </c:forEach>
    </fieldset>
    <div class="field">
        <input type="submit" value="Save"/>
    </div>
</fieldset>