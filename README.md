### 模仿天猫整站 J2EE
---
> 参考how2j.cn上的实践项目，特此感谢。

环境：
1. eclipse
2. mysql5.5

技术栈：
1. 前端：HTML、CSS、JavaScript、JQuery
2. 后端：Tomcat、Servlet、JSP、Filter

项目结构：
- 前端展示
    - 首页
    - 分类页面
    - 产品页面
    - 购物车页面
    - 我的订单页面
    - 结算页面
    - 查询结果页面
    - 支付页面
    - 支付成功页面
    - 确认收货页面
    - 收货成功页面
    - 评价页面
    - 登录页面
    - 注册页面
- 后端管理
    - 分类管理
    - 属性管理
    - 产品管理
    - 产品图片管理
    - 产品属性值设置
    - 用户管理
    - 订单管理
    
开发流程：
1. 需求分析<br/>
首先确定要做哪些功能，需求分析包括前端和后端。<br/>
前端又分为单纯要展示的那些功能-需求分析-展示，以及会提交数据到服务端的那些功能-需求分析-交互。
2. 表结构设计<br/>
表结构设计是围绕功能需求进行，如果表结构设计有问题，那么将会影响功能的实现。
3. 原型<br/>
借助界面原型，可以低成本，高效率的与客户达成需求的一致性。
4. 实体类设计<br/>
接着开始进行实体类的设计与开发，实体类不仅仅是对数据库中的表的一一映射，同时还需要兼顾对业务功能的支持。
5. DAO类设计<br/>
然后是DAO类的设计，除了进行典型的ORM支持功能之外，也需要提供各种业务方法。
6. 后端-分类管理<br/>
接下来开始进行功能开发，按照模块之间的依赖关系，首先进行后端-分类管理功能开发。
7. 后端-其他管理<br/>
然后开发属性管理、产品管理、产品图片管理、产品属性值设置、用户管理、订单管理。
8. 前端-首页<br/>
接下来开始进行前端功能的开发，首先进行前端-首页功能开发。
9. 前端无需登录<br/>
从前端模块之间的依赖性，以及开发顺序的合理性来考虑，把前端功能分为了无需登录即可使用的功能，和需要登录才能访问的功能。 建立在前一步前端-首页的基础之上，开始进行一系列的无需登录功能开发。
10. 前端需要登录<br/>
最后是需要登录的前端功能。这部分功能基本上都是和购物相关的。

项目预览：
![](https://s1.ax1x.com/2020/09/23/wjSLbn.png)

项目核心思想：Filter + Servlet<br/>
当你访问这个 URL(http://127.0.0.1:8080/admin_category_list) 的时候发生了什么呢？<br/>
后端路径的组成：/admin + Servlet 类名 + 此 Servlet 类中的方法名<br/>
1.首先我们会遇到 BackServletFilter 这个过滤器，这个过滤器在 web.xml 中配置了，虽然它会过滤所有的路径，但是这个过滤器是用来处理后端路径的。
```xml
<filter>
    <filter-name>BackServletFilter</filter-name>
    <filter-class>tmall.filter.BackServletFilter</filter-class>
</filter>

<filter-mapping>
    <filter-name>BackServletFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```
2.然后看 BackServletFilter 的 doFilter() 这个方法做了什么？
```java
// 判断是否是访问后端的路径（后端的路径都以 admin_ 开头）
if (uri.startsWith("/admin_")) {
    // uri 就是 admin_category_list
    String servletPath = StringUtils.substringBetween(uri, "_", "_") + "Servlet"; // servletPath: categoryServlet
    String method = StringUtils.substringAfterLast(uri, "_"); // method: list
    // 方便后面使用
    request.setAttribute("method", method);
    // 服务端跳转，跳转到 /categoryServlet 这个路径
    request.getRequestDispatcher("/" + servletPath).forward(request, response);
    return;
}
```
这个路径 /categoryServlet 在 web.xml 中配置了，然后就会执行 CategoryServlet 中的 service() 方法，CategoryServlet 没有重写 service() 方法，所以就会执行其父类 BaseBackServlet 的 service() 方法。
```xml
<servlet>
    <servlet-name>CategoryServlet</servlet-name>
    <servlet-class>tmall.servlet.CategoryServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>CategoryServlet</servlet-name>
    <url-pattern>/categoryServlet</url-pattern>
</servlet-mapping>
```
3.接着看 BaseBackServlet 的 service() 方法做了什么？
```java
// 从 request 中获取 method
String method = (String) request.getAttribute("method");

// 通过反射执行 method 这个方法，也就是执行 CategoryServlet 的 list() 方法
Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,
        javax.servlet.http.HttpServletResponse.class, Page.class);
String redirect = (String) m.invoke(this, request, response, page);

// 根据方法的返回值进行相应的服务端跳转、客户端跳转或者仅仅是输出字符串
if (redirect.startsWith("@")) {
    response.sendRedirect(redirect.substring(1));
} else if (redirect.startsWith("%")) {
    response.getWriter().print(redirect.substring(1));
} else {
    request.getRequestDispatcher(redirect).forward(request, response);
}
```
<br/>
前端路径的组成：/fore + ForeServlet 中的方法名<br/>
访问一个前端路径就更简单了，因为前端只有一个 Servlet，我们只需要确定执行哪个方法就可以了，不需要像后端那样确定是哪一个 Servlet。