# SpringMvc的学习笔记 
## 目录  
[toc]后续生成目录  
## 1、SpringMvc的入门  
### 创建WEB工程，导入坐标
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
### 配置核心的控制器(配置DispatcherServlet)
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
### 编写springmvc.xml的配置文件  
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
### 编写控制器类
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
### 入门案例的执行流程  
  1、当启动Tomcat服务器时，因配置了load-on-startup标签，所以会创建DispatcherServlet对象就会加载springmvc.xml配置文件   
  2、开启了注解扫描，那么HelloController对象就会被创建   
  3、从index.jsp发送请求，请求会先到达DispatcherServlet核心控制器，根据配置@RequestMapping注解找到具体的执行方法          
  4、根据方法的返回值，再根据配置的视图解析器，去指定的目录下查找指定名称的文件的JSP文件     
  5、Tomcat服务器渲染页面，做出响应     
### 入门案例中的组件分析   
  + 前端控制器(DispatcherServlet)  
  + 处理器映射器(HandlerMapping)  
  + 处理器(Handler)  
  + 处理器适配器(HandlAdapter)   
  + 视图解析器(View Resolver)  
  + 视图(View)  
  ![avatar](/springmvc01.jpg)   
### RequestMapping注解    
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
     
## 2、请求参数的绑定  
### 基本数据类型和字符串类型   
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
### 单个实体类型   
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
### 单个实体类包含其他引用类型  
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
### 集合类型  
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
### 自定义类型转换器  
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
### 请求参数中文乱码的解决  
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
## 3、常用的注解  
### RequestParam注解  
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
### RequestBody注解  
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
### PathVariable注解  
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
### RequestHeader注解
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
### CookieValue注解  
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
### ModelAttribute注解  
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
### SessionAttributes注解  
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
## 4、响应数据和结果视图
## 返回值分类
### 返回值是字符串
Controller方法返回字符串可以指定逻辑视图的名称，根据视图解析器为物理视图的地址  
```java
    @RequestMapping("/string")
    public String testString(Model model){

        System.out.println("执行了testString控制器方法...");
        // 模拟从数据库中查询出User对象
        User user = new User();
        user.setUsername("Coder");
        user.setPassword("123456");
        user.setAge(22);
        // model对象
        model.addAttribute("user1", user);
        return "success";
    }
``` 
### 返回值是Void
+ **如果控制器的方法返回值写成void，执行程序报404异常，默认查找JSP没有找到(void.jsp)**   
+ **可以使用请求转发或者是重定向跳转到指定的页面**  
+ **直接响应数据**   
```java
    @RequestMapping("/void")
    public void testVoid(HttpServletRequest request, HttpServletResponse response) throws Exception{

        System.out.println("执行了testVoid控制器方法...");
        // 不写返回值，默认跳转到void.jsp

        // 编写请求转发程序,转发是一次请求，不用写项目名称,且转发不会执行视图解析器
        request.getRequestDispatcher("/WEB-INF/pages/success1.jsp").forward(request, response);

        // 重定向，执行新的请求，需要完整路径，尝试好几个都到不了success1.jsp，见鬼了
        response.sendRedirect(request.getContextPath() + "/index.jsp");

        // 设置中文乱码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 直接进行响应
        response.getWriter().print("也不知道要返回什么内容...");
        return;
    }
```
### 返回值是ModelAndView对象  
ModelAndView对象是Spring提供的一个对象，可以用来调整具体的JSP视图  
```java
    @RequestMapping("/modelandview")
    public ModelAndView testModelAndView(){

        System.out.println("执行了testModelAndView控制器方法...");
        ModelAndView mv = new ModelAndView();

        User user = new User();
        user.setUsername("Coder");
        user.setPassword("123456");
        user.setAge(22);

        // 把user对象存储到mv中，也会把user对象存到request对象中
        mv.addObject("user2", user);
        // 跳转到哪个页面
        mv.setViewName("success1");
        return mv;
    }
```
### 请求转发和重定向  
```java
    @RequestMapping("/forwardOrRedirect")
    public String testForwardOrRedirect(){

        System.out.println("执行了testForwardOrRedirect控制器方法...");

        // 请求的转发
        // return "forward:/WEB-INF/pages/success1.jsp";

        // 重定向
        return "redirect:/index.jsp";
    }
```
## 响应JSON数据  
### 过滤静态资源
DispatcherServlet会拦截到所有的静态资源，导致一个问题就是静态资源(css,js,image)也会被拦截到，
从而不能被使用。解决问题就是需要配置静态资源不进行拦截，在springmvc.xml中添加配置如下:    
```java
    <!-- 前端控制器，哪些静态资源不拦截 -->
    <mvc:resources mapping="/js/**" location="/js/"/>
```
location: 表示webapp目录下的包(js)的所有文件   
mapping: 表示/js开头的所有请求路径  
### 发送ajax请求并响应json数据
**导入相关依赖**
```xml
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.9.2</version>
    </dependency>

    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-core</artifactId>
      <version>2.9.2</version>
    </dependency>

    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-annotations</artifactId>
      <version>2.9.2</version>
    </dependency>
  </dependencies>
```
**发送ajax请求**  
```js
    <script src="js/jquery-3.1.1.min.js"></script>
    <script>
        // 页面加载，绑定单击事件
        $(function () {
            $("#btn").click(function () {
                // 发送ajax请求
                $.ajax({
                    // 编写json格式，设置属性和值
                    url: "return/json",
                    contentType: "application/json;charset=UTF-8",
                    data: '{"username":"MaGrady", "password":"123456", "age":18}', // 发送给服务器的数据
                    dataType: "json",
                    type: "post",

                    success:function (data) {
                        // data是服务器响应的json数据，进行解析
                        alert(data);
                        alert(data.password);
                    }
                });
            });
        });
    </script>
```
**使用RequestBody注解把json字符串转换为JavaBean对象**      
**使用ResponseBody注解把JavaBean对象转换成json字符串**      
```java
    @RequestMapping("/json")
    public @ResponseBody User testJson(@RequestBody User user){

        System.out.println("执行了testJson控制器方法...");
        // 客户端发送ajax请求，传json字符串，后端导入响应的包会自动封装到User对象
        System.out.println(user);
        // 做响应，模拟查询数据库
        user.setPassword("654321");
        user.setAge(66);

        return user;
    }
```
## 5、文件上传  
### 传统方式上传文件
**导入文件上传的jar包**  
```xml
    <dependency>
      <groupId>commons-fileupload</groupId>
      <artifactId>commons-fileupload</artifactId>
      <version>1.3.1</version>
    </dependency>

    <dependency>
      <groupId>commons-io</groupId>
      <artifactId>commons-io</artifactId>
      <version>2.4</version>
    </dependency>
```
**编写文件上传的JSP页面**  
```html
    <h2>传统方式上传文件</h2>

    <form action="/file/upload1" method="post" enctype="multipart/form-data">
        选择文件: <input type="file" name="upload1"/><br/>
        <input type="submit" value="上传"/>
    </form>
```
**编写文件上传的Controller控制器(fileUpload1)**  
```java
    @RequestMapping("/upload1")
    public String fileUpload1(HttpServletRequest request) throws Exception{

        System.out.println("执行fileUpload1控制器方法...");

        // 使用fileupload组件完成文件上传
        // 上传的位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        // 本机在Tomcat目录下
        System.out.println("PATH:" + path);
        // 判断该路径是否存在
        File file = new File(path);
        if(!file.exists()){
            // 创建文件夹
            file.mkdirs();
        }

        // 解析request对象，获取到上传文件项
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 解析request
        List<FileItem> items = upload.parseRequest(request);
        // 遍历
        for(FileItem item : items){
            // 进行判断，当前item对象是否是一个上传文件项
            if(item.isFormField()){
                // 普通表单项
            }else {
                // 上传文件项
                // 获取到上传文件的名称
                String filename = item.getName();
                // 把文件名称设置为唯一值,uuid
                String uuid = UUID.randomUUID().toString().replace("-","");
                filename = uuid + "_" + filename;
                // 完成文件上传
                item.write(new File(path, filename));
                // 删除临时文件
                item.delete();
            }
        }
        return "success";
    }
```
### SpringMvc传统方式上传文件
**SpringMVC框架提供了MultipartFile对象，该对象表示上传的文件，要求变量名称必须和表单ﬁle标签的name属性名称相同**  
**配置文件解析器对象**      
```xml
    <!-- 配置文件解析器对象,id必须命名为multipartResolver -->
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <property name="maxUploadSize" value="10485760"/>
    </bean>
```
**编写文件上传的Controller控制器(fileUpload2)**    
不需要自己再去解析request对象  
```java
    @RequestMapping("/upload2")
    public String testUpload2(HttpServletRequest request, MultipartFile upload2) throws IOException {

        System.out.println("执行fileUpload2控制器方法...");

        // 使用fileupload组件完成文件上传
        // 上传的位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        // 本机在Tomcat目录下
        System.out.println("PATH:" + path);
        // 判断该路径是否存在
        File file = new File(path);
        if(!file.exists()){
            // 创建文件夹
            file.mkdirs();
        }

        // 获取文件上传的名称
        String filename = upload2.getOriginalFilename();
        // 把文件名称设置为唯一值,uuid
        String uuid = UUID.randomUUID().toString().replace("-","");
        filename = uuid + "_" + filename;
        // 完成文件上传
        upload2.transferTo(new File(path, filename));

        return "success";
    }
}
```
### 跨服务器上传文件  
**导入开发需要的jar包**  
```xml
<dependency>
    <groupId>com.sun.jersey</groupId>
    <artifactId>jersey-core</artifactId>
    <version>1.81.1</version>
</dependency>

<dependency>
    <groupId>com.sun.jersey</groupId>
    <artifactId>jersey-client</artifactId>
    <version>1.81.1</version>
</dependency>
```
**编写文件上传的Controller控制器(fileUpload3)**    
```java
@RequestMapping("/upload3")
public String testUpload3(MultipartFile upload) throws Exception{
    
    System.out.println("执行fileUpload3控制器方法...");
    
    // 记得判断文件夹是否存在
    // 定义图片服务器的请求路径
    String path = "http://localhost:9090/day02_springmvc5_02image/uploads/";
    
    // 获取文件上传的名称
    String filename = upload2.getOriginalFilename();
    // 把文件名称设置为唯一值,uuid
    String uuid = UUID.randomUUID().toString().replace("-","");
    filename = uuid + "_" + filename;
    
    // 创建客户端对象
    Client client = Client.create(); 
    
    // 连接图片服务器
    WebResource webResource = client.resource(path+filename); 
    
    // 上传文件
    webResource.put(upload.getBytes());
    
    return "success";
}
```
## 6、异常处理
### 处理思路
Controller调用Service，service调用dao，异常都是向上抛出的，最终有DispatchServlet
找异常处理器进行异常处理  
  + **自定义异常类**  
  ```java
public class SysException extends Exception{

    // 存储错误提示信息
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SysException(String message) {

        this.message = message;
    }
}
```
  + **自定义异常处理器**    
  ```java
public class SysExceptionResolver implements HandlerExceptionResolver {

    /**
     * 处理异常的业务逻辑
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        // 获取异常对象
        SysException e = null;
        if(ex instanceof SysException){
            e = (SysException)ex;
        }else{
            e = new SysException("系统正在维护....");
        }

        // 创建ModelView对象
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorMsg", e.getMessage());
        mv.setViewName("error");

        return mv;
    }
}
```
  + **配置异常处理器**   
  ```xml
    <!-- 配置异常处理器 -->
    <bean id="sysExceptionResolver" class="com.springmvc.exception.SysExceptionResolver"/>
```

## 7、拦截器
### 拦截器的概述
1、过滤器配置了/*，可以拦截任何资源，而拦截器只会对控制器中的方法进行拦截  
2、想要自定义拦截器，需要实现HandlerInterceptor接口，其中三个方法默认实现  
### HandlerInterceptor接口中的方法  
  + preHandle方法是controller方法执行前拦截的方法  
    - 可以使用request或者response跳转到指定的页面  
    - return true，放行，执行下一个拦截，直到没有，执行controller中的方法  
    - return false，不放行，不会执行controller中的方法  
  + postHandle方法是controller方法执行后的方法，在Jsp视图执行前  
    - 可以使用request或者response跳转到指定页面  
    - 如果指定了跳转的页面，那么controller方法跳转的页面将不会显示  
  + afterCompletion方法是在Jsp执行后执行  
    - request或者response不能再跳转页面了  
    - 通常用于释放资源  
### 自定义拦截器
  + **创建类，实现HandlerInterceptor接口，重写需要的方法**  
  ```java
public class MyInterceptor2 implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("MyInterceptor2执行了预处理...控制器前");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        System.out.println("MyInterceptor2执行了后处理...控制器后");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        System.out.println("MyInterceptor2执行了最后处理...跳转页面之后");
    }
}
```
  + **在springmvc.xml中配置拦截器类**  
  ```xml
    <!-- 配置拦截器-->
    <mvc:interceptors>
        <!-- 配置第一个拦截器 -->
        <mvc:interceptor>
            <!-- 要拦截的具体方法 -->
            <mvc:mapping path="/interceptor/*"/>
            <!-- 不要拦截的具体方法
            <mvc:exclude-mapping path=""/>
            -->
            
            <!-- 配置拦截器对象 -->
            <bean class="com.springmvc.interceptor.MyInterceptor1"/>
        </mvc:interceptor>

        <!-- 配置第二个拦截器 -->
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <bean class="com.springmvc.interceptor.MyInterceptor2"/>
        </mvc:interceptor>
    </mvc:interceptors>
```



