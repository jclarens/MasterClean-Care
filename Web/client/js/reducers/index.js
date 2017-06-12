import { combineReducers } from 'redux'
import userReducer from './RdcUser'

const mcc = combineReducers({
    userReducer,
})

export default mcc