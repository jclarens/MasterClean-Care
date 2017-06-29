import { LOGIN } from '../actions/DefaultAction'

const allowedUser = {
    username: 'admin',
    password: 'admin'
}

const UserReducer = (state = '', action) => {
    switch (action.type) {
        case LOGIN:
            if (action.data.username == allowedUser.username
                && action.data.password == allowedUser.password) {
                    return allowedUser
                }
            return state
        default:
            return state
    }
}

export default UserReducer