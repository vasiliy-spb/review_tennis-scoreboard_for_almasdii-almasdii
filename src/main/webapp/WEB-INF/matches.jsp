<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</head>

<body>

<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="${pageContext.request.contextPath}/images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>

        <div>
            <nav class="nav-links">
                <a class="nav-link" href="${pageContext.request.contextPath}">Home</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/matches">Matches</a>
            </nav>
        </div>
    </section>
</header>

<main>
    <div class="container">

        <h1>Matches</h1>

        <div class="input-container">
            <form method="get" action="${pageContext.request.contextPath}/matches">
                <input type="hidden" name="page" value="0"/>

                <label>
                    <input
                            class="input-filter"
                            placeholder="Filter by name"
                            type="text"
                            name="filter_by_player_name"
                            value="${param.filter_by_player_name}"
                    />
                </label>

                <button type="submit" class="btn-filter">Search</button>

                <a href="${pageContext.request.contextPath}/matches?page=0">
                    <button type="button" class="btn-filter">Reset Filter</button>
                </a>
            </form>

            <c:if test="${requestScope.error != null}">
                <h3 class="error-message" style="white-space: pre-line;">
                        ${requestScope.error}
                </h3>
            </c:if>
        </div>

        <table class="table-matches">
            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>

            <c:forEach var="match" items="${requestScope.paginationDto.currentPageMatches()}">
                <tr>
                    <th>${match.firstPlayerName()}</th>
                    <th>${match.secondPlayerName()}</th>
                    <th>${match.winnerName()}</th>
                </tr>
            </c:forEach>
        </table>

        <!-- Цикл от 0 до numberOfPages отображает сразу все существующие страницы.
                Лучше сделать окно пагинации ограниченным текущей страницей +-2 вокруг неё -->
        <c:if test="${requestScope.paginationDto.numberOfPages() > 0}">

            <c:choose>

                <c:when test="${requestScope.filterName != null && !requestScope.filterName.isEmpty()}">

                    <div class="pagination">

                        <c:if test="${requestScope.paginationDto.currentPage() > 0}">
                            <a class="prev"
                               href="${pageContext.request.contextPath}/matches?page=${requestScope.paginationDto.currentPage()-1}&filter_by_player_name=${requestScope.filterName}">
                                <
                            </a>
                        </c:if>

                        <c:forEach var="index" begin="0" end="${requestScope.paginationDto.numberOfPages()-1}">
                            <a class="num-page ${index == requestScope.paginationDto.currentPage() ? 'current' : ''}"
                               href="${pageContext.request.contextPath}/matches?page=${index}&filter_by_player_name=${requestScope.filterName}">
                                    ${index+1}
                            </a>
                        </c:forEach>

                        <c:if test="${requestScope.paginationDto.currentPage() < requestScope.paginationDto.numberOfPages()-1}">
                            <a class="next"
                               href="${pageContext.request.contextPath}/matches?page=${requestScope.paginationDto.currentPage()+1}&filter_by_player_name=${requestScope.filterName}">
                                >
                            </a>
                        </c:if>

                    </div>

                </c:when>

                <c:otherwise>

                    <div class="pagination">

                        <c:if test="${requestScope.paginationDto.currentPage() > 0}">
                            <a class="prev"
                               href="${pageContext.request.contextPath}/matches?page=${requestScope.paginationDto.currentPage()-1}">
                                <
                            </a>
                        </c:if>

                        <c:forEach var="index" begin="0" end="${requestScope.paginationDto.numberOfPages()-1}">
                            <a class="num-page ${index == requestScope.paginationDto.currentPage() ? 'current' : ''}"
                               href="${pageContext.request.contextPath}/matches?page=${index}">
                                    ${index+1}
                            </a>
                        </c:forEach>

                        <c:if test="${requestScope.paginationDto.currentPage() < requestScope.paginationDto.numberOfPages()-1}">
                            <a class="next"
                               href="${pageContext.request.contextPath}/matches?page=${requestScope.paginationDto.currentPage()+1}">
                                >
                            </a>
                        </c:if>

                    </div>

                </c:otherwise>

            </c:choose>

        </c:if>

    </div>
</main>

<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from
            <a href="https://zhukovsd.github.io/java-backend-learning-course/">
                zhukovsd/java-backend-learning-course
            </a>
            roadmap.
        </p>
    </div>
</footer>

</body>
</html>






<%--<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>--%>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <title>Tennis Scoreboard | Finished Matches</title>--%>
<%--    <link rel="preconnect" href="https://fonts.googleapis.com">--%>
<%--    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>--%>
<%--    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">--%>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>

<%--    <script src="${pageContext.request.contextPath}/js/app.js"></script>--%>
<%--</head>--%>

<%--<body>--%>
<%--<header class="header">--%>
<%--    <section class="nav-header">--%>
<%--        <div class="brand">--%>
<%--            <div class="nav-toggle">--%>
<%--                <img src="${pageContext.request.contextPath}/images/menu.png" alt="Logo" class="logo">--%>
<%--            </div>--%>
<%--            <span class="logo-text">TennisScoreboard</span>--%>
<%--        </div>--%>
<%--        <div>--%>
<%--            <nav class="nav-links">--%>
<%--                <a class="nav-link" href="${pageContext.request.contextPath}">Home</a>--%>
<%--                <a class="nav-link" href="${pageContext.request.contextPath}/matches">Matches</a>--%>
<%--            </nav>--%>
<%--        </div>--%>
<%--    </section>--%>
<%--</header>--%>
<%--<main>--%>
<%--    <div class="container">--%>
<%--        <h1>Matches</h1>--%>
<%--        <div class="input-container">--%>
<%--            <form method="get" action="${pageContext.request.contextPath}/matches">--%>
<%--                <input type="hidden" name="page" value="0"/>--%>
<%--                <label>--%>
<%--                    <input--%>
<%--                            class="input-filter"--%>
<%--                            placeholder="Filter by name"--%>
<%--                            type="text"--%>
<%--                            name="filter_by_player_name"--%>
<%--                            value="${param.filter_by_player_name}"--%>
<%--                    />--%>
<%--                </label>--%>

<%--                <button type="submit" class="btn-filter">Search</button>--%>

<%--                <a href="${pageContext.request.contextPath}/matches?page=0">--%>
<%--                    <button type="button" class="btn-filter">Reset Filter</button>--%>
<%--                </a>--%>
<%--            </form>--%>
<%--            <c:if test="${requestScope.error != null}">--%>
<%--                <h3 class="error-message" style="white-space: pre-line;">${requestScope.error}</h3>--%>
<%--            </c:if>--%>
<%--        </div>--%>

<%--        <table class="table-matches">--%>
<%--            <tr>--%>
<%--                <th>Player One</th>--%>
<%--                <th>Player Two</th>--%>
<%--                <th>Winner</th>--%>
<%--            </tr>--%>
<%--            <c:forEach var="match" items="${requestScope.paginationDto.currentPageMatches()}">--%>
<%--                <tr>--%>
<%--                    <th>${match.firstPlayerName()}</th>--%>
<%--                    <th>${match.secondPlayerName()}</th>--%>
<%--                    <th>${match.winnerName()}</th>--%>
<%--                </tr>--%>
<%--            </c:forEach>--%>
<%--        </table>--%>

<%--        <c:if test="${requestScope.paginationDto.numberOfPages() > 0}">--%>
<%--            <div class="pagination">--%>

<%--                <c:if test="${requestScope.paginationDto.currentPage() > 0}">--%>
<%--                    <a class="prev"--%>
<%--                       href="${pageContext.request.contextPath}/matches?page=${requestScope.paginationDto.currentPage()-1}&filter_by_player_name=${requestScope.playerName}">--%>
<%--                        < </a>--%>
<%--                </c:if>--%>
<%--                <c:forEach var="index" begin="0" end="${requestScope.paginationDto.numberOfPages()-1}">--%>
<%--                    <a class="num-page ${index == requestScope.paginationDto.currentPage() ? 'current' : ''}"--%>
<%--                       href="${pageContext.request.contextPath}/matches?page=${index}&filter_by_player_name=${requestScope.playerName}">${index+1}</a>--%>
<%--                </c:forEach>--%>
<%--                <c:if test="${requestScope.paginationDto.currentPage() < requestScope.paginationDto.numberOfPages()-1}">--%>
<%--                    <a class="next"--%>
<%--                       href="${pageContext.request.contextPath}/matches?page=${requestScope.paginationDto.currentPage()+1}&filter_by_player_name=${requestScope.playerName}">--%>
<%--                        > </a>--%>
<%--                </c:if>--%>
<%--            </div>--%>
<%--        </c:if>--%>
<%--    </div>--%>
<%--</main>--%>
<%--<footer>--%>
<%--    <div class="footer">--%>
<%--        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>--%>
<%--            roadmap.</p>--%>
<%--    </div>--%>
<%--</footer>--%>
<%--</body>--%>
<%--</html>--%>
