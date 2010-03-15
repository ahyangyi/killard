<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="../../includes.jsp" %>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<head>
    <link type="text/css" href="/css/layout.css" rel="stylesheet"/>
    <link type="text/css" href="/css/menu.css" rel="stylesheet"/>
    <link type="text/css" href="/css/form.css" rel="stylesheet"/>
    <link type="text/css" href="/css/ui/card.css" rel="stylesheet"/>
    <script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.7.2.min.js"></script>
    <script type="text/javascript" src="/js/jquery.layout-latest.js"></script>
    <script type="text/javascript" src="/js/jquery.validate.1.7.js"></script>
    <script type="text/javascript" src="/js/ui/browser.js"></script>
    <script type="text/javascript" src="/js/ui/card.js"></script>
    <title>Killard Games - Game Settings</title>
</head>
<body>
<%@ include file="../../topbar.jsp" %>
<form action="" method="POST" class="horizontal" id="card-form">
    <div class="container">
        <div class="ui-layout-west">
            <div class="ui-layout-content">
                <div class="vmenu">
                    <ul>
                        <li><a href="#">All Games</a></li>
                    </ul>
                    <ul>
                        <li><a href="#">My Games</a></li>
                    </ul>
                    <ul class="active">
                        <li><a href="#">${package.descriptor.name}</a></li>
                    </ul>
                    <ul>
                        <c:forEach var="elementSchool" items="${package.elementSchools}">
                            <li>
                                <a href="<c:url value="/games/${package.bundleKey.name}/${elementSchool.name}"/>">
                                ${elementSchool.descriptor.name}
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <div class="ui-layout-center">
            <div class="ui-layout-content">
                <fieldset>
                    <legend>${package.descriptor.name}</legend>
                    <fieldset>
                        <legend>Clone</legend>
                        <ul class="fields">
                            <li>
                                <label><input type="radio" class="radio" name="field4"/> Public </label>
                            </li>
                            <li>
                                <label><input type="radio" class="radio" name="field4"/> Private </label>
                            </li>
                        </ul>
                    </fieldset>
                    <div class="field">
                        <label for="rule">Game Rule</label>
                        <textarea cols="32" rows="6" id="rule" name="rule">${package.rule}</textarea>
                    </div>
                </fieldset>
            </div>
        </div>
        <div class="ui-layout-east">
            <div class="ui-layout-content">
                <div style="text-align:center;">
                    <img src="/image/index.png"/>
                </div>
                <hr/>
                <div class="field">
                    <label for="card-image">Upload Image</label>
                    <input type="file" name="image" id="card-image"/>
                </div>
            </div>
        </div>
        <div class="ui-layout-south">
            <div class="ui-layout-content">
                <div style="text-align:center;" class="buttons">
                    <input type="reset" class="button" value="Reset" style="float:left;width:20%;"/>
                    <input type="submit" value="Save" style="float:left;width:80%;"/>
                </div>
            </div>
        </div>
    </div>
</form>
<%@ include file="../../bottombar.jsp" %>
</body>
</html>