import request from "../utils/axios"

export const categoryList = ()=>{
    return request({
        method:"get",
        url:"/category/list"
    })
}


export const categoryPage = (page,size,keyword)=>{
    return request({
        method:"get",
        url:"/category/page",
        params:{
            'page':page,
            'size':size,
            'keyword':keyword
        }
    })
}


export const categoryDetail = (id)=>{
    return request({
        method:"get",
        url:`/category/${id}`
    })
}


//对象 = {}
export const categoryAdd = (category)=>{
    return request({
        method:"post",
        url:"/category",
        data:category
    })
}


export const categoryUpdate = (category) =>{
    return request(
        {
            method:"put",
            url:"/category",
            data:category
        }
    )
}

export const categoryDelete = (id)=>{
    return request({
        method:"delete",
        url:`/category/${id}`
    });
}