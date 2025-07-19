## 3.1 用户接口分析
### 3.1.1 用户登录查询
功能：进入博客首页页面之前，登录校验，查询账号密码

接口：
```
uri: /user/login
method: post
参数: json {user}
return: 
    {
     "code": 0
     "message": "",
     "data": null,
     "ok": true
    }
```


### 3.1.2 用户详情查询
功能：
后台管理页面：查询用户详情

接口：
```
uri: /user/{id}
method: get
参数: path {id}
return: 
    {
     "code": 0
     "message": "",
     "data": {user}, #注意：要屏蔽密码，set为空
     "ok": true
    }
```


### 3.1.3 修改用户详情
功能：
后台管理页面：修改用户详情

接口：
```
uri: /user
method: post
参数: json {user}
return: 
    {
     "code": 0
     "message": "",
     "data": null,
     "ok": true
    }
```