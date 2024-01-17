# Mywust

武科大教务处网站以及其它的网站服务接口封装库（Java）

对一些常用的服务接口进行了底层的封装，主要包括请求的生成以及页面解析，调用时只需传入必要参数即可进行接口的调用以及响应页面的解析

核心代码来自武科大助手后端爬虫模块，在此基础上进行部分修改以适用于各种平台，因此不会使用重量级的框架，尽量保证仅使用原生java或jvm兼容的语言即可使用，是一个比较~~轻量~~的库

由于处在早期阶段，API尚未处于稳定阶段，因此项目结构以及外部接口随时可能发生巨大变化，在正式版出来前请勿重度依赖，当前仅作为算法以及处理过程参考使用

后续可能会根据需要新增其他语言的实现以提供给其他语言和平台的使用

现已实现的接口：
<details> 
<summary>展开</summary>

- 教务处（本科生）
  - 登录
  - 学生信息获取
  - 成绩查询
  - 课表查询
  - 培养方案获取
  - 缓考申请信息查询
  - 教室课表查询
  - 教师课表查询
- 研究生
  - 登录
  - 学生信息获取
  - 课表查询
  - 成绩查询
  - 培养计划获取
- 物理实验预约系统
  - 登录
  - 实验课表安排查询
  - 成绩查询
- 图书馆
  - 登录
  - 图书详情查询
  - 图书封面Url查询
  - 图书馆藏信息查询
  - 当前借阅、历史借阅、即将逾期借阅信息查询
  - 图书搜索

...and more...
</details>

如有其他接口需求欢迎在issue提出，如果您有能力提交pr那就再好不过了

---

> *为了避免引起不必要的麻烦，本项目不会涉及写入性质的接口操作（如修改密码和用户信息，课程退改选等操作），如有相关的需求请fork项目自行研究，由此引发的问题与本项目无关*

---

## 环境要求

JDK版本：1.8及以上，推荐JDK 11及以上

## 使用说明

### 1. 在项目中引用mywust-core

在`pom.xml`中引用核心即可：

```xml
<dependency>
  <groupId>cn.wustlinghang.mywust</groupId>
  <artifactId>mywust-core</artifactId>
  <version>${mywust.version}</version>
</dependency>
```

引入okhttp请求器以实现网络请求功能：
```xml
<dependency>
    <groupId>cn.wustlinghang.mywust</groupId>
    <artifactId>mywust-network-okhttp</artifactId>
    <version>${mywust.version}</version>
</dependency>
```

请求器也可以通过自己实现Requester接口传入核心使用。

### 2. 快速使用

使用mywust很简单，只需几行即可（以本科生登录和课表获取为例）：

```java

import cn.wustlinghang.mywust.core.parser.undergraduate.UndergradCourseTableParser;
import cn.wustlinghang.mywust.core.request.service.auth.UndergraduateLogin;
import cn.wustlinghang.mywust.core.request.service.undergraduate.UndergradCourseTableApiService;
import cn.wustlinghang.mywust.data.common.Course;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.network.RequestClientOption;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.okhttp.SimpleOkhttpRequester;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestMain {

  public static void main(String[] args) throws IOException, ApiException, ParseException {
    RequestClientOption.Proxy proxy = RequestClientOption.Proxy.builder()
            .address("127.0.0.1")
            .port(8080)
            .build();

//        设置请求选项
    RequestClientOption requestClientOption = new RequestClientOption();
    requestClientOption.setProxy(proxy);
    requestClientOption.setTimeout(10);
    requestClientOption.setFollowUrlRedirect(false);
    requestClientOption.setRetryable(true);
    requestClientOption.setMaxRetryTimes(3);
    requestClientOption.setIgnoreSSLError(true);

//        实例化一个请求器，实际使用中只实例化一次即可
    Requester requester = new SimpleOkhttpRequester();

//        初始化登录服务
    UndergraduateLogin login = new UndergraduateLogin(requester);

//        登录并获取cookie
    String cookie = login.getLoginCookie("学号", "密码", requestClientOption);

//        初始化api服务和解析器，实际使用中只实例化一次即可
    UndergradCourseTableApiService courseTableApi = new UndergradCourseTableApiService(requester);
    UndergradCourseTableParser parser = new UndergradCourseTableParser();

//        获取页面
//        不指定请求选项的调用方式
//        String page = courseTableApi.getPage("2021-2022-1", cookie);
//        指定请求选项的调用方式
    String page = courseTableApi.getPage("学期", cookie, requestClientOption);

//        解析上文获取的课表页面
    List<Course> courseList = parser.parse(page);
    System.out.println(Arrays.toString(courseList.toArray()));
  }
}
```

目前可用的api服务与解析器：

api服务：

```text
├─auth
│      GraduateLogin.java
│      LibraryLogin.java
│      PhysicsLogin.java
│      UndergraduateLogin.java
│      UnionLogin.java
│
├─captcha
│  └─solver
│          CaptchaSolver.java
│          DdddOcrCaptchaSolver.java
│
├─graduate
│      GraduateApiServiceBase.java
│      GraduateCourseTableApiService.java
│      GraduateScoreApiService.java
│      GraduateStudentInfoApiService.java
│      GraduateTrainingPlanApiService.java
│
├─library
│      BaseLibraryApiService.java
│      BookCoverImageUrlApiService.java
│      BookDetailApiService.java
│      BookHoldingApiService.java
│      CurrentLoanApiService.java
│      LoanHistoryApiService.java
│      OverdueSoonApiService.java
│      SearchApiService.java
│
├─physics
│      PhysicsApiServiceBase.java
│      PhysicsCourseApiService.java
│      PhysicsScoreApiService.java
│
└─undergraduate
    │  UndergradApiServiceBase.java
    │  UndergradCourseTableApiService.java
    │  UndergradCreditStatusApiService.java
    │  UndergradExamDelayApiService.java
    │  UndergradScoreApiService.java
    │  UndergradSingleWeekCourseApiService.java
    │  UndergradStudentInfoApiService.java
    │  UndergradTrainingPlanApiService.java
    │
    └─school
            package-info.java
            UndergradAllCourseScheduleApiService.java
            UndergradBuildingIdApiService.java
            UndergradClassroomCourseApiService.java
            UndergradTeacherCourseApiService.java
```

解析器：

```text
│  HuangjiahuClassroomNameParser.java
│  Parser.java
│
├─graduate
│      GraduateCourseTableParser.java
│      GraduateScoreParser.java
│      GraduateStudentInfoPageParser.java
│      GraduateTrainingPlanPageParser.java
│
├─physics
│      PhysicsCoursePageParser.java
│      PhysicsIndexPageParser.java
│      PhysicsScoreListPageParser.java
│      PhysicsScorePageParser.java
│
└─undergraduate
    │  UndergradCourseTableParser.java
    │  UndergradCreditStatusIndexParser.java
    │  UndergradCreditStatusParser.java
    │  UndergradExamDelayParser.java
    │  UndergradScoreParser.java
    │  UndergradSingleWeekCourseParser.java
    │  UndergradStudentInfoPageParser.java
    │  UndergradTrainingPlanPageParser.java
    │
    └─school
            CourseTableParserBase.java
            PlaceNameParser.java
            UndergradAllCourseScheduleParser.java
            UndergradClassroomParser.java
            UndergradTeacherCourseParser.java
```

其他部分具体用法大同小异，根据doc传参即可

## 目录规范

> 待补充，先留个坑

## 版本日志

> 待补充，先留个坑

## 使用到的开源库

- HTML解析：[jsoup](https://jsoup.org/license) (MIT License)
- Json解析：[jackson](https://github.com/FasterXML) (Apache License 2.0)
- 代码简化：[lombok](https://projectlombok.org/) (MIT License)
- 日志输出：[slf4j](https://www.slf4j.org/) (MIT License)
- 各种工具：[guava](https://guava.dev/) (Apache License 2.0)
- 编解码库：[apache commons codec](https://commons.apache.org/proper/commons-codec/) (Apache License 2.0)

## 开源协议

本项目使用[MIT许可证](https://mit-license.org/)开源，希望您在使用代码时也能够遵守该协议

```plain text
The MIT License (MIT)
Copyright © 2022 <wustlinghang studio>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the “Software”), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or
substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH 
THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```