import service from '../service'

export const $get = async (url, params) => {
    const rs = await service({
        url,
        params,
        method: 'get',
        headers: {
            "token": localStorage.getItem('token')
        }
    })
    return rs
}

export const $delete = async (url, params) => {
    const rs = await service({
        url,
        params,
        method: 'delete',
        headers: {
            "token": localStorage.getItem('token')
        }
    })
    return rs
}
export const $put = async (url, params) => {
    const rs = await service({
        url,
        method: 'put',
        data: params,
        headers: {
            "token": sessionStorage.getItem('token'),
            "Content-Type": "application/json"
        }
    })
    return rs
}
export const $post = async (url, params) => {
    const rs = await service({
        url,
        method: 'post',
        data: params,
        headers: {
            "token": sessionStorage.getItem('token'),
            "Content-Type": "application/json"
        }
    })
    return rs
}

export const $upload = async (url, params) => {
    const rs = await service({
        url,
        method: 'post',
        data: params,
        headers: {
            'Content-Type': 'multipart/form-data'
        },
    })
    return rs
}


