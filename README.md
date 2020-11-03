# JKSTACK-DSM

## 代码规范
### 命名规则 (尽量遵循阿里巴巴代码规范)

1.实体类必须以Entity结尾, POJO + Entity，如UserEntity.
2.接口实现类以Impl结尾，如UserServiceImpl.
3.Action/Controller类以Controller结尾，如UserController.
4.feign接口，方便多模块调用使用，以Feign结尾（POJO+ControllerFeign）,如UserControllerFeign
5.数据库索引命名规则，idx_字段名称，如：idx_user_id
6.抽象类命名使用 Abstract 或 Base 开头；异常类命名使用 Exception 结尾； 测试类命名以它要测试的类的名称开始，以 Test 结尾。
7.在常量与变量的命名时，表示类型的名词放在词尾，以提升辨识度。

   正例： startTime / workQueue / nameList / TERMINATED_THREAD_COUNT
   反例： startedAt / QueueOfWork / listName / COUNT_TERMINATED_THREAD
   
8.禁止使用魔鬼数字，应该使用常量  
9.禁止使用JSONObject和JSONArray作为方法的入参数和出参数，应该尽量使用实体类去完成，使用JSONObject和JSONArray代码阅读极差

```
     JSONObject jsonObject = JSONObject.parseObject(response);
     int code = jsonObject.getInteger("code");
```
         
10.禁止使用，for循环方式批量插入数据，应该使用批量插入语句

11.循环体内，字符串的连接方式，使用 StringBuilder 的 append 方法进行扩展。说明：下例中，反编译出的字节码文件显示每次循环都会 new 出一个 StringBuilder 对象，然后进行 append操作，最后通过 toString 方法返回 String 对象，造成内存资源浪费。
   
   反例：

```

   String str = "start";   

    for (int i = 0; i < 100; i++) {       
        str = str + "hello";    
    }   
    
```    
   
## 目录结构

- jkstack-dsm-common : 公共层，存放项目的工具，禁止在该包里面使用@value等一些框架的东西.

- jkstack-dsm-gateway: 网关.

- jkstack-dsm-sheet  : 工单服务，自定义流程、自定义表单等功能.

- jkstack-dsm-user   : 用户服务.


## Git提交日志规范

关键字：

- add:XXX       增加
- update:XXX    更新
- delete:XXX    删除
- fix:XXX       修复BUG
- test:XX       增加测试
- doc:XX        文档

例子：

- add:XXX       开放平台验证接口
- update:XXX    Token类实现修改为使用子类继承
- delete:XXX    Timer类已经被TimerManager替换
- fix:XXX       验证码短信文字乱码问题
- test:XX       增加测试
- doc:XX        更新README.md

##  日志打印规范


记录日志时要思考：日志有人看吗？看到这个日志我能做什么？能不能给问题排查带来好处


###  每种日志级别的作用


error: 级别只记录系统逻辑出错，异常等重要信息

debug: 记录调试信息，生产环境禁止开启

info:  业务日志，尽量精简打印


### 打印规范
 
1.调用外部系统API都需要打印日志格式如下：

```
    log.info("URL : " + 接口URL);
    log.info("ARGS : " + 接口参数列表);
```

2.业务日志打印规范

例如：

```
    try {
        logger.info("执行XXXX{}开始 req：{}", req.getrId(), JSON.toJSONString(req));
        // 业务流程
        logger.info("执行XXXX{}完成 res：{}", req.getrId(), "业务流程，必要的结果信息");
        return Result.buildSuccess();
    } catch (Exception e) {
        logger.error("执行XXXX{}失败 req：{}", req.getrId(), JSON.toJSONString(req), e);
        return Result.buildError(e);
    }

```
那么现在这样改成这样打日志，就可以非常方便的查询问题，例如搜索；执行XXXX100098921，那么它的一整串关于这次调用的信息就可以都搜索出来了，方便排查问题。
在异常中打印入参是为了更加方便的定位问题，不需要比对上下文。

### 代码模版使用方法

（待定）
