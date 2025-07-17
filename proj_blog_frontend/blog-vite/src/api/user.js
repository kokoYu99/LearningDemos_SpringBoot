import request from "../utils/axios"



export const userLogin = (user)=>{
    return request({
        method:"post",
        url:`/user/login`,
        data:user
    })
}


//对象 = {}
export const userDetail = (id)=>{
    return request({
        method:"get",
        url:`/user/${id}`
    })
}


export const userUpdate = (user) =>{
    return request(
        {
            method:"put",
            url:"/user",
            data:user
        }
    )
}
