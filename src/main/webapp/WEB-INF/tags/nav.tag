<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="sidebar-collapse">
        <ul side-navigation class="nav" id="side-menu">
            <li>

             <div class="dropdown profile-element">
                    <!-- Picture of user -->
                    <!--<img alt="image" class="img-circle" src="img/profile_small.jpg"/>-->

                    <div align="center">
                        <div class="row" style="height:30px;"></div>
                        <div><img src="/resources/images/main/ileco.png"></div>
                        <div class="row" style="height:30px;"></div>
                    </div>
                    <ul class="dropdown-menu animated fadeInRight m-t-xs">

                        <li><a href="">Logout</a></li>
                    </ul>
                </div>
                <div class="logo-element">
                    Firefly
                </div>

            </li>
            <li  ui-sref-active="active">
                <a ui-sref="dashboard"><i class="fa fa-laptop"></i> <span class="nav-label">Dashboard</span> </a>
            </li>

            <c:forEach begin="0" end="${fn:length(menus)}" varStatus="loop">
                <c:if test="${menus[loop.index].state != null && fn:length(menus[loop.index].state) > 0}">

                    <c:set var="parentMenuId">${menus[loop.index].parentMenu.id}</c:set>
                    <c:set var="subMenusCount">${fn:length(menus[loop.index].subMenus)}</c:set>
                    <c:choose>
                        <c:when test='${subMenusCount > 0}'>
                            <li>
                                <a><i class="${menus[loop.index].iconClass}"></i> <span class="nav-label">${menus[loop.index].title}</span> <span class="fa arrow"></span></a>

                                <ul class="nav nav-second-level">
                                    <c:forEach items="${menus[loop.index].subMenus}" var="subMenu">
                                        <c:if test="${subMenu.state != null && fn:length(subMenu.state) > 0}">
                                            <li ui-sref-active="active">
                                            <a ui-sref="${subMenu.state}"><i class="${subMenu.iconClass}"></i><span class="nav-label">${subMenu.title}</span></a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </li>

                        </c:when>
                        <c:when test='${parentMenuId == ""}'>
                            <li ui-sref-active="active">
                                <a ui-sref="${menus[loop.index].state}"><i class="${menus[loop.index].iconClass}"></i><span class="nav-label">${menus[loop.index].title}</span></a>
                            </li>
                        </c:when>
                    </c:choose>
                </c:if>
            </c:forEach>

            <li  ui-sref-active="active">
                <a ui-sref="profile"><i class="fa fa-user"></i> <span class="nav-label">Profile</span> </a>
            </li>
        </ul>
    </div>
</nav>


