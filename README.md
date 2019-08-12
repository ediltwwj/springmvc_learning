## SpringMvc的学习笔记 
### 目录  
[toc]后续生成目录  
### 1、SpringMvc的入门  
#### 创建WEB工程，导入坐标
```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <!-- 版本锁定 -->
    <spring.version>5.0.2.RELEASE</spring.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>${spring.version}</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
      <version>${spring.version}</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>${spring.version}</version>
    </dependency>

    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>servlet-api</artifactId>
      <version>2.5</version>
      <scope>provided</scope>
    </dependency>

    <dependency>
      <groupId>javax.servlet.jsp</groupId>
      <artifactId>jsp-api</artifactId>
      <version>2.0</version>
      <scope>provided</scope>
    </dependency>
  </dependencies>
```
#### 配置核心的控制器(配置DispatcherServlet)
  + 在web.xml配置文件中核心控制器DispatcherServlet  
  ```xml
    <servlet>
      <servlet-name>dispatcherServlet</servlet-name>
      <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
      <!-- 配置Servlet的初始化参数，读取springmvc的配置文件，创建spring容器 -->
      <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:springmvc.xml</param-value>
      </init-param>
      <!-- 在项目启动的时候加载 -->
      <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
      <servlet-name>dispatcherServlet</servlet-name>
      <url-pattern>/</url-pattern>
    </servlet-mapping>
  ```
#### 编写springmvc.xml的配置文件  
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 开启注解扫描 -->
    <context:component-scan base-package="com.springmvc"></context:component-scan>

    <!-- 视图解析器配置 -->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/pages/"/>  <!-- 要跳转的目录 -->
        <property name="suffix" value=".jsp"/>  <!-- 文件后缀 -->
    </bean>

    <!-- 开启SpringMvc框架注解的支持 -->
    <!-- 自动加载处理映射器和处理适配器 -->
    <mvc:annotation-driven/>
</beans>
```
#### 编写控制器类
```java
// HelloController.java
@Controller
@RequestMapping(path="/springmvc") // 相当于一级路径
public class HelloController {

    /**
     * @RequestMapping(path="/requestMapping")
     * @RequestMapping(value="/requestMapping")
     * @RequestMapping("/requestMapping")
     * 上面三种等价
     * @return
     */
    @RequestMapping(path="/requestMapping")
    public String testRequestMapping(){
        System.out.println("测试RequestMapping注解...");
        return "success";
    }
}
```
#### 入门案例的执行流程 
  1、当启动Tomcat服务器时，因配置了load-on-startup标签，所以会创建DispatcherServlet对象
    就会加载springmvc.xml配置文件   
  2、开启了注解扫描，那么HelloController对象就会被创建  
  3、从index.jsp发送请求，请求会先到达DispatcherServlet核心控制器，根据配置@RequestMapping
  注解找到具体的执行方法       
  4、根据方法的返回值，再根据配置的视图解析器，去指定的目录下查找指定名称的文件的JSP文件    
  5、Tomcat服务器渲染页面，做出响应     
#### 入门案例中的组件分析  
  1、前端控制器(DispatcherServlet)  
  2、处理器映射器(HandlerMapping)  
  3、处理器(Handler)  
  4、处理器适配器(HandlAdapter)   
  5、视图解析器(View Resolver)  
  6、视图(View)  
  ![avatar](/springmvc01.jpg)     
#### RequestMapping注解    
  1、RequestMapping注解的作用是建立请求URL和处理方法之间的对应关系  
  2、RequestMapping注解可以作用在方法和类上  
    + 作用在类上: 第一级的访问目录  
    + 作用在方法上: 第二级的访问目录  
    + 细节: 路径可以不写/表示应用的根目录开始  
    + ${ pageContext.request.contextPath }也可以省略不写，但是路径上不能写 /  
  3、RequestMapping的属性   
    + path: 指定请求路径的url   
    + value: value属性和path属性是一样的  
    + method: 指定该方法的请求方式(method = RequestMethod.POST)   
    + params: 指定限制请求参数的条件(params = {"username","password=123"})   
    + headers: 发生的请求中必须包含的请求头(headers = {"Cookie"})    

  
  