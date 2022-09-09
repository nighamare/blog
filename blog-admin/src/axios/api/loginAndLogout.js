import service from '../service'


export const login=async (params)=>{
    const rs = await service({
        url: '/account/userLogin',
        params,
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

export const getDirectTrainList = async (params) => {
    const rs = await service({
        url: '/account/queryDirectTrip',
        params,
        method: 'get',
        headers: {
            "token": localStorage.getItem('token')
        }
    })
    return rs
}
export const getTransferTrainList = async (params) => {
    const rs = await service({
        url: '/account/queryTransferTrip',
        params,
        method: 'get',
        headers: {
            "token": localStorage.getItem('token')
        }
    })
    return rs
}
export const deleteByIds = async (...ids) => {
    const rs = await service({
        url: '/account/delete?' + ids.map(id => 'ids=' + id).join('&'),
        method: 'delete',
        headers: {
            "token": localStorage.getItem('token')
        }
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
