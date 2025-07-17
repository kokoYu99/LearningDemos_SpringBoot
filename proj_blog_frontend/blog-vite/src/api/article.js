import request from "../utils/axios"



/**
 * 前端[vue页面] -> 方法 -> axios -> {参数} -> {后台接口} 
 * 
 * method: 后台 -> @GetMapping get @PostMapping post 
 * url: 后台 -> 类 + 方法 @RequestMapping(path)  ""+字符串  有路径参数 `${key}`
 * 参数: 
 *      params:{} ->  转化 ->  get  => url? 属性名=属性值&
 *      data:{}  -> 不转化 -> data -> 请求体 json -> 后台
 * 
 */
export const articleList = (cid)=>{
    return request({
        method:"get",
        url:"/article/list",
        params:{
            'cid':cid
        }
    })
}



export const articleDetail = (id)=>{
    return request({
        method:"get",
        url:`/article/${id}`
    })
}


//对象 = {}
export const articleAdd = (article)=>{
    return request({
        method:"post",
        url:"/article",
        data:article
    })
}


export const articleUpdate = (article) =>{
    return request(
        {
            method:"put",
            url:"/article",
            data:article
        }
    )
}

export const articleDelete = (id)=>{
    return request({
        method:"delete",
        url:`/article/${id}`
    });
}