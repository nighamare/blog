import service from '../service'


export const login=async (params)=>{
    const rs = await service({
        url: '/account/login',
        data:params,
        method: 'post'
    })
    return rs
}
export const register=async (params)=>{
    const rs = await service({
        url: '/account/register',
        params,
        method: 'put'
    })
    return rs
}



export const insert = async (params) => {
    const rs = await service({
        url: '/account/insert',
        method: 'put',
        params
    })
    return rs
}
export const update = async (params) => {
    const rs = await service({
        url: '/account/update',
        method: 'post',
        params
    })
    return rs
}
