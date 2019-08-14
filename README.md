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
  1、当启动Tomcat服务器时，因配置了load-on-startup标签，所以会创建DispatcherServlet对象就会加载springmvc.xml配置文件   
  2、开启了注解扫描，那么HelloController对象就会被创建   
  3、从index.jsp发送请求，请求会先到达DispatcherServlet核心控制器，根据配置@RequestMapping注解找到具体的执行方法          
  4、根据方法的返回值，再根据配置的视图解析器，去指定的目录下查找指定名称的文件的JSP文件     
  5、Tomcat服务器渲染页面，做出响应     
#### 入门案例中的组件分析   
  + 前端控制器(DispatcherServlet)  
  + 处理器映射器(HandlerMapping)  
  + 处理器(Handler)  
  + 处理器适配器(HandlAdapter)   
  + 视图解析器(View Resolver)  
  + 视图(View)  
  ![avatar](/springmvc01.jpg)   
#### RequestMapping注解    
  + RequestMapping注解的作用是建立请求URL和处理方法之间的对应关系   
  + RequestMapping注解可以作用在方法和类上   
    - 作用在类上: 第一级的访问目录   
    - 作用在方法上: 第二级的访问目录   
    - 细节: 路径可以不写/表示应用的根目录开始    
    - ${ pageContext.request.contextPath }也可以省略不写，但是路径上不能写/   
  + RequestMapping的属性   
    - path: 指定请求路径的url   
    - value: value属性和path属性是一样的   
    - method: 指定该方法的请求方式(method = RequestMethod.POST)   
    - params: 指定限制请求参数的条件(params = {"username","password=123"})   
    - headers: 发生的请求中必须包含的请求头(headers = {"Cookie"})     
     
### 请求参数的绑定  
#### 基本数据类型和字符串类型   
**提交表单的name值和方法参数的名称是相同的(区分大小写)**        
```java
    @RequestMapping("/param1")
    public String testParam1(String username, String password){

        System.out.println("UserName: " + username + ", Password: " + password);
        return "success";
    }
```
```html
    <h3>请求参数绑定基本类型</h3>
    <form action="param1">
        username:<input name="username" type="text"/><br/>
        password:<input name="password" type="password"/><br/>
        <input type="submit" value="提交"/>
    </form>
```
#### 单个实体类型   
**提交表单的name和JavaBean中的属性名称需要一致**     
```java
public class Account implements Serializable {

    private String username;
    private String password;
    private Double money;
    
    // 省略getter，setter，toString方法
}
```
```java
    @RequestMapping("/param2")
    public String testParam2(Account account){

        System.out.println(account);
        return "success";
    }
```
```html
    <h3>请求参数绑定单个实体类型</h3>
    <form action="param2" method="post">
        UserName:<input type="text" name="username" /><br/>
        Password:<input type="text" name="password" /><br/>
        Money:<input type="text" name="money" /><br/>
        <input type="submit" value="提交"/>
    </form>
```  
#### 单个实体类包含其他引用类型  
**表单属性需要编写成对象.属性，例如account.money**    
```java
public class Employee implements Serializable {

    private String employeeName;
    private Account account;
    
    // 省略getter,setter,toString方法
}
``` 
```java
    @RequestMapping("/param3")
    public String testParam3(Employee employee){

        System.out.println(employee);
        return "success";
    }
```
```html
    <h3>请求参数绑定实体类型中含有实体类型</h3>
    <form action="param3" method="post">
        UserName:<input type="text" name="account.username" /><br/>
        Password:<input type="text" name="account.password" /><br/>
        Money:<input type="text" name="account.money" /><br/>
        EmployeeName:<input type="text" name="employeeName"><br/>
        <input type="submit" value="提交"/>
    </form>
```  
#### 集合类型  
**主要是list和map，jsp编写方式为list[0]或者map[0].name**    
```java
public class Bank implements Serializable {

    private String bankName;
    private List<Account> list;
    private Map<Integer, Account> map;
    
    // 省略getter,setter,toString方法
}
``` 
```java
    @RequestMapping("/param4")
    public String testParam4(Bank bank){

        System.out.println(bank);
        return "success";
    }
```
```html
    <h3>请求参数绑定集合类型</h3>
    <form action="param4" method="post">
        BankName:<input type="text" name="bankName"/><br/><hr/>
        <!-- list -->
        UserName1L:<input type="text" name="list[0].username"/><br/>
        Password1L:<input type="text" name="list[0].password"/><br/>
        Money1L:<input type="text" name="list[0].money"/><br/>
        <hr/>
        UserName2L:<input type="text" name="list[1].username"/><br/>
        Password2L:<input type="text" name="list[1].password"/><br/>
        Money2L:<input type="text" name="list[1].money"/><br/>
        <hr/>
        <!-- Map -->
        UserName1M:<input type="text" name="map[0].username"/><br/>
        Password1M:<input type="text" name="map[0].password"/><br/>
        Money1M:<input type="text" name="map[0].money"/><br/>
        <hr/>
        UserName2M:<input type="text" name="map['1'].username"/><br/>
        Password2M:<input type="text" name="map['1'].password"/><br/>
        Money2M:<input type="text" name="map['1'].money"/><br/>
        <input type="submit" value="提交"/>
    </form>
```
#### 自定义类型转换器  
  + **实现Converter接口**    
  ```java
    import org.springframework.core.convert.converter.Converter;

    // 字符串转换为日期
    public class StringToDateConverter implements Converter<String, Date> {
    
        /**
         * @param source 传入的字符串
         * @return
         */
        @Override
        public Date convert(String source) {
            if(source == null){
                throw new RuntimeException("请您传入数据");
            }
            // 会导致原来yyyy/MM/dd的格式无法使用
            try{
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                return df.parse(source);
            }catch (Exception e){
                throw new RuntimeException("数据类型转换出现错误");
            }
        }
    }
  ```
  + **注册自定义类型转换器，在springmvc.xml配置文件中编写配置**    
  ```xml
    <!-- 配置自定义类型转换器 -->
    <bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
        <property name="converters">
            <set>
                <bean class="com.springmvc.utils.StringToDateConverter"/>
            </set>
        </property>
    </bean>
    
    <!-- 开启类型转换服务支持 -->
    <mvc:annotation-driven conversion-service="conversionService"/>    
  ```
#### 请求参数中文乱码的解决  
**在web.xml中配置Spring提供的过滤器**
```xml
  <!-- 配置解决中文乱码的过滤器 -->
  <filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```  
### 常用的注解  
#### RequestParam注解  
  + 作用: 把请求参数中的指定名称参数传递给控制器中的形参赋值  
  + 属性:  
    - value: 请求参数中的名称  
    - required: 请求参数中是否必须提供此参数，默认值是true，必须提供  
```html
    <a href="requestParam?name=Mike">RequestParam</a>
```  
```java
    @RequestMapping("/requestParam")
    public String testRequestParam(@RequestParam(value = "name", required = false) String username){

        System.out.println("Name:" + username);
        return "success";
    }
```    
#### RequestBody注解  
  + 作用: 用于获取请求体的内容  
  + 属性:
    - required: 是否必须有请求体，默认是true，get请求不可以用，写false，则get的body为null  
```html
    <form action="requestBody" method="post">
        姓名:<input type="text" name="username"/><br/>
        年龄:<input type="text" name="age"/><br/>
        <input type="submit" value="提交"/>
    </form>
```
```java
    @RequestMapping("/requestBody")
    public String testRequestBody(@RequestBody String body){

        System.out.println("Body: " + body);
        return "success";
    }
```
#### PathVariable注解  
  + 作用: 绑定url中的占位符，例如url中有/deleted/{id}，{id}就是占位符  
  + 属性: 
    - value: 指定url中占位符名称，等同于name属性  
```html
<a href="/pathVariable/10">PathVariable</a>
```
```java
    @RequestMapping("/pathVariable/{sid}")
    public String testPathVariable(@PathVariable(value = "sid") String id){

        System.out.println("ID: " + id);
        return "success";
    }
```
#### RequestHeader注解
  + 作用: 获取指定请求头的值  
  + 属性:
    - value: 请求头的名称  
```html
<a href="requestHeader">RequestHeader</a>
```
```java
    @RequestMapping("/requestHeader")
    public String testRequestHeader(@PRequestHeader(value = "Accept") String header){

        System.out.println("Header: " + header);
        return "success";
    }
```
#### CookieValue注解  
  + 作用: 用于把指定cookie名称的值传入控制器方法参数  
  + 属性:
    - value: 指定cookie的名称  
    - required: 是否必须有次cookie  
```html
<a href="cookieValue">CookieValue</a>
```
```java
    @RequestMapping("/cookieValue")
    public String testCookievalue(@CookieValue(value = "JSESSIONID") String cookieValue){

        System.out.println("CookieValue: " + cookieValue);
        return "success";
    }
```
#### ModelAttribute注解  
  + 作用:
    - 出现在方法上: 表示当前方法会在控制器方法执行之前执行  
    - 出现在参数上: 获取指定的数据给参数赋值  
  - 应用场景:
    - 当提交表单数据不是完整的实体数据时，保证没有提交的字段使用数据库原来的数据  
  + 代码  
    - **修饰的方法有返回值**    
    ```html
        <form action="modelAttribute1" method="post">
            姓名:<input type="text" name="username"/><br/>
            年龄:<input type="text" name="password"/><br/>
            <input type="submit" value="提交"/>
        </form>
    ```
    ```java
    @RequestMapping("/modelAttribute1")
    public String testModelAttribute(Account account){

        System.out.println("ModelAttribute控制器方法执行...");
        System.out.println(account);
        return "success";
    }
       
    @ModelAttribute
    public Account showAccount(String username, String password){
        System.out.println("有返回值...");
        // 模拟查询数据库，设置money，返回Account对象给控制器
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setMoney(200.0);

        return account;
    }
    ```
    - **修饰的方法没有返回值**  
    ```html
    <form action="modelAttribute2" method="post">
        姓名:<input type="text" name="username"/><br/>
        年龄:<input type="text" name="password"/><br/>
        <input type="submit" value="提交"/>
    </form>
    ```  
    ```java
    @RequestMapping("/modelAttribute2")
    public String testModelAttribute(@ModelAttribute("1") Account account){

        System.out.println("ModelAttribute控制器方法执行...");
        System.out.println(account);
        return "success";
    }

    @ModelAttribute
    public void showAccount(String username, String password, Map<String, Account> map){
        System.out.println("无返回值...");
        // 模拟查询数据库，设置money,将Account放进Map
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setMoney(200.0);
        map.put("1", account);
    }
    ```
#### SessionAttributes注解  
  + 作用: 用于多次执行控制器方法间的参数共享  
  + 属性:  
    - value: 指定存入属性的名称  
```html
<a href="sessionAttributes">SessionAttributes</a>
```     
```java
@Controller
@SessionAttributes(value = {"msg"})  // 把msg=该存啥呢存入到session域对中
public class AnnoController {
    
    @RequestMapping("/sessionAttributes")
    public String testSessionAttributes(Model model){
        System.out.println("SessionAttributes...");
        // 底层会存到request域对象中
        model.addAttribute("msg","存啥好呢？");
        return "success";
    }    
}
```
    
