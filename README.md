# JKSTACK-DSM

## 代码规范
#### 命名规则 (尽量遵循阿里巴巴代码规范)
1. 实体类必须以Entity结尾, POJO + Entity，如UserEntity.
2. 接口实现类以Impl结尾，如UserServiceImpl.
3. Action/Controller类以Controller结尾，如UserController.
4. feign接口，方便多模块调用使用，以Feign结尾（POJO+ControllerFeign）,如UserControllerFeign
5. 数据库索引命名规则，idx_字段名称，如：idx_user_id

