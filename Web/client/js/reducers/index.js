import { combineReducers } from 'redux'
import UserReducer from './UserReducer'
import NotificationReducer from './NotificationReducer'

const mcc = combineReducers({
    UserReducer,
    NotificationReducer,
})

export default mcc