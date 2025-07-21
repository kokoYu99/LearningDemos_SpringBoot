import request from "../utils/axios.js";

/* 理解：这里的所有api方法都返回promise对象，在vue组件中调用，用于获取异步请求结果 */

/* 总结
前端vue组件 -> 调用api方法 -> api方法封装了axios请求 -> axios的参数 -> 由后端接口决定

axios参数编写：
    method: 后端 @GetMapping @PostMapping ...
    url: 后端 @RequestMapping(path)
        有路径参数：`url/${key}` 模板字符串
        无路径参数：'url'
    参数：params和data都是对象，区别在于是否转换
        params:{key:value, key:value, ...} -> 转换-> get请求，url?key=value -> 传给后端
        data:{key:value} 或 data: 对象名 -> 不转换，直接放入请求体 -> json -> 传给后端

*/

/* 
### 1.1.1 文章列表查询
功能：
1. 博客首页页面：全部查询、类别查询、默认类别查询
2. 后台管理页面：全部查询

接口：
```
uri: /article/list
method: get
参数: param  cId=id值，可能为null(使用动态sql)
return: 
    {
     "code": 0
     "message": "",
     "data": 
        [
          {article},
          {article},
          ...
        ],
     "ok": true
    }
```
*/
/* 文章列表查询 */
const articleList = (cid) => {
    return request({
        method: "get",
        url: "/article/list",
        params: {
            cId: cid,
        },
    });
};

/* ### 1.1.2 文章详情查询
功能：
1. 后台管理页面：查询单个文章的详情信息

接口：
```
uri: /article/{id}
method: get
参数: path {id}
return: 
    {
    "code": 0
    "message": "",
    "data": {article},
    "ok": true
    }
```
*/
/* 文章详情查询 */
const articleDetails = (id) => {
    return request({
        method: "get",
        url: `/article/${id}`,
    });
};

/* ### 1.1.3 文章新增
功能：
1. 后台管理页面：文章的新增
2. 注意：需要在业务层生成create_time, update_time

接口：
```
1. uri: /article
2. method: post
3. 参数: json {article} 不含id
4. return: 
    {
     "code": 0
     "message": "",
     "data": null,
     "ok": true
    }
``` */
const articleInsert = (article) => {
    return request({
        method: "post",
        url: "/article",
        data: article,
    });
};

/* ### 1.1.4 文章修改
功能：
1. 后台管理页面：文章的修改
2. 注意：
   1. 业务层需要修改update_time
   2. Mapper层需要修改title、content、cid；修改cid时，要set-if校验是否为null

接口：
```
1. uri: /article
2. method: put
3. 参数: json {article} 含id
4. return: 
    {
     "code": 0
     "message": "",
     "data": null,
     "ok": true
    }
``` */
const articleUpdate = (article) => {
    return request({
        method: "put",
        url: "/article",
        data: article,
    });
};

/* ### 1.1.5 文章删除
功能：
1. 后台管理页面：文章的删除

接口：
```
1. uri: /article/{id}
2. method: delete
3. 参数: path {id}
4. return: 
    {
     "code": 0
     "message": "",
     "data": null,
     "ok": true
    }
``` */
const articleDelete = (id) => {
    return request({
        method: "delete",
        url: `/article/${id}`,
    });
};

export {
    articleList,
    articleDetails,
    articleInsert,
    articleUpdate,
    articleDelete,
};
