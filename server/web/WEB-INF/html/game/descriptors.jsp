<%@ include file="../includes.jsp" %>
<%--@elvariable id="descriptable" type="com.killard.board.jdo.DescriptableDO"--%>
<fieldset>
    <legend>${descriptable.descriptor.name}</legend>
    <fieldset>
        <legend>Descriptions <a href="#" class="new">new</a></legend>
        <fieldset style="display:none;">
            <hr/>
            <div class="field">
                <label for="locale">Locale</label>
                <select id="locale">
                    <option>en_US</option>
                    <option>zh_CN</option>
                </select>
                <a href="#" class="delete">delete</a>
            </div>
            <div class="field">
                <label for="name">Name</label>
                <input type="text" id="name" name="name"/>
            </div>
            <div class="field">
                <label for="description">Description</label>
                <textarea rows="5" cols="25" id="description" name="descriptin"></textarea>
            </div>
        </fieldset>
        <c:forEach var="descriptor" items="${descriptable.descriptors}">
            <fieldset>
                <div class="field">
                    <label for="locale_${descriptor.locale}">Locale</label>
                    <span id="locale_${descriptor.locale}">${descriptor.locale}</span>
                    <a href="#" class="delete">delete</a>
                </div>
                <div class="field">
                    <label for="name_${descriptor.locale}">Name</label>
                    <input type="text" id="name_${descriptor.locale}" name="name"/>
                </div>
                <div class="field">
                    <label for="description_${descriptor.locale}">Description</label>
                    <textarea rows="5" cols="25" id="description_${descriptor.locale}" name="description"></textarea>
                </div>
            </fieldset>
        </c:forEach>
        <div class="field">
            <input type="submit" value="Save"/>
        </div>
    </fieldset>
</fieldset>