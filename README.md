# Mywust

武科大教务处网站以及其它的网站服务接口封装库（Java）

对一些常用的服务接口进行了底层的封装，主要包括请求的生成以及页面解析，调用时只需传入必要参数即可进行接口的调用以及响应页面的解析

核心代码来自武科大助手后端爬虫模块，在此基础上进行部分修改以适用于各种平台，因此不会使用重量级的框架，尽量保证仅使用原生java或jvm兼容的语言即可使用，是一个比较~~轻量~~的库

由于处在早期阶段，因此项目结构随时可能发生巨大变化，在正式版出来前请勿重度依赖

~~（说白了就是一个爬虫库，只不过泛用性高，在任何jvm平台上都能使用，而不仅限于spring体系）~~

后续可能会根据需要新增其他语言的实现以提供给其他语言和平台的使用

现已实现的接口：
<details> 
<summary>展开</summary>

- 教务处
  - 登录
  - 学生信息获取
  - 成绩查询
  - 课表查询
  - 培养方案获取
- 研究生
  - 登录
  - 学生信息获取
  - 课表查询
  - 成绩查询
  - 培养计划获取
- 物理实验预约系统
  - 登录
  - 实验课表安排查询
- 图书馆
  - 登录

...and more...
</details>

如有其他接口需求欢迎在issue提出，如果您有能力提交pr那就再好不过了

---

> *为了避免引起不必要的麻烦，本项目不会涉及写入性质的接口操作（如修改密码和用户信息，课程退改选等操作），如有相关的需求请fork项目自行研究，由此引发的问题与本项目无关*

---

## 环境要求

JDK版本：1.8及以上，推荐JDK 11及以上

## 使用说明

> 待补充，先留个坑

## 目录规范

> 待补充，先留个坑

## 版本日志

> 待补充，先留个坑

## 使用到的开源库

> 待补充，先留个坑

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