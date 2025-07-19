## 2.1. 类别接口分析
### 2.1.1 查询全部
功能：
1. 博客首页页面：查询全部类别

接口：
```
uri: /category/list
method: get
参数: null
return: 
    {
     "code": 0
     "message": "",
     "data": 
        [
          {category},
          {category},
          ...
        ],
     "ok": true
    }
```


### 2.1.2 分页+关键字模糊查询
功能：
1. 后台管理页面
   1. 最多传3个参数：page pageSize keyword
   2. 最少传0个参数。默认值：page=1，pageSize=5

接口：
```
uri: /category/page
method: get
参数: param, page pageSize keyword
return: 
    {
     "code": 0
     "message": "",
     "data": 
        [
          total: 总条数,
          list: [
            {category}
          ]
        ],
     "ok": true
    }
```


### 2.1.3 查询详情
功能：
1. 后台管理页面：查询类别详情

接口：
```
uri: /category/{id}
method: get
参数: path {id}
return: 
    {
     "code": 0
     "message": "",
     "data": 
        {category},
     "ok": true
    }
```


### 2.1.4 新增类别
功能：
1. 后台管理页面：类别新增

接口：
```
uri: /category
method: post
参数: json {category} 不含cid
return: 
    {
     "code": 0
     "message": "",
     "data": null,
     "ok": true
    }
```


### 2.1.5 修改类别
功能：
1. 后台管理页面：类别修改

接口：
```
uri: /category
method: put
参数: json {category} 含cid
return: 
    {
     "code": 0
     "message": "",
     "data": null,
     "ok": true
    }
```


### 2.1.6 删除类别
功能：
1. 后台管理页面：类别删除

接口：
```
uri: /category/{id}
method: delete
参数: path {id}
return: 
    {
     "code": 0
     "message": "",
     "data": null,
     "ok": true
    }
```



