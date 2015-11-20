# chandra for Clojure

This project will be based on the command line or Web service method, called the establishment of dynamic HTTP data. Fast HTTP back-end models, while providing a unified front end code generation.


此项目将基于命令行或web service方式，动态的建立http的数据调用。可快速实现http后台的建模，同时提供统一化的前端界面代码生成。


## Features:

Both command line and Web service;
Supports multiple data sources;
Support the rapid establishment of affinity models;
Support for dynamic front-end code generation;
Plug-in that supports third-party language;

特点：

同时基于命令行和Web service；
支持多种数据源；
支持快速建立亲和度的模型；
支持动态统一前端代码生成；
支持第三方语言的插件；

## Usage

# repl 方式

```
chandra.core=> (-main)

# help
{:error nil, :value "HELP!"}
# model create user -f email:string -b password:md5 -p user
# model create blog -f title:string content:text -b images:upload user
{:error nil,
 :value {},
 :status "ok"}
# model list blog
{:error nil,
 :value ({})}

```

# web 方式

```
chandra.core=> (-main)

# server start 8088

```

In browser, 


```
 POST url : http://localhost:8088/c/model/
 POST data : {command: "create",
              model: "blog",
              fields: "title:string content:text",
              binding: "images:upload user"}
 Require: {:error nil,
           :value ("blog table created","images table create"),
           :status "ok"}

 GET url : http://localhost:8088/blog/list/
 Require: {:error nil,
           :value ({:id 1, :title "title", :content "content", :images ({})),
           :status "ok"}
           
```


## License

Copyright © 2015 Eclipse Public License
