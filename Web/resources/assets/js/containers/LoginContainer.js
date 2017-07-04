import React from 'react'
import { connect } from 'react-redux'
import Login from '../components/Login'
import {
    Route, 
    Redirect,
    withRouter,
} from 'react-router-dom'
import {
    updateSnack,
    updateLoadingSpin,
    resetLoadingSpin,
} from '../actions/DefaultAction'
import axios from 'axios'

export const simpleAuthentication = {
    isAuthenticated() {
        if (localStorage.getItem('authState') !== undefined) {
            let authState = JSON.parse(localStorage.getItem('authState'))
            if (authState !== undefined && authState !== null) {
                return authState.isAuthenticated === true
            }
            else {
                return false
            }
        }
        else {
            return false
        }
    },
    authenticate(e, history) {
        return
    },
    signout(history) {
        localStorage.removeItem('authState')
        history.push('/login')        
    },
    getUsername() {
        if (localStorage.getItem('authState') !== undefined) {
            let authState = JSON.parse(localStorage.getItem('authState'))
            if (authState !== undefined && authState !== null) {
                return authState.username
            }
            else {
                return 'Username'
            }
        }
        else {
            return 'Username'
        }
    }
}

const mapStateToProps = (state) => {
    return {
        status: state.notif
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        onUpdateSnack: (open, message) => {
            dispatch(updateSnack({
                open: open,
                message: message
            }))
        },
        onLogin: (data, history) => {
            dispatch(updateLoadingSpin({
                show: true,
            }))
            setTimeout(function() { 

            axios.post('/api/check_login', {
                email: data.email,
                password: data.password
            })
            .then(function (response) {
                dispatch(resetLoadingSpin())
                let data = response.data
                if (data.status === 200) {
                    history.push('/')
                }
                else {
                    dispatch(updateSnack({
                        open: true,
                        message: data.message
                    }))
                }
            })
            .catch(function (error) {
                dispatch(resetLoadingSpin())
                dispatch(updateSnack({
                    open: open,
                    message: error
                }))
            })
            }, 3000);            
        }
    }
}

const LoginContainer = withRouter(connect(
    mapStateToProps,
    mapDispatchToProps
)(({ history, onUpdateSnack, onLogin, status }) => (
    <div className="container">
        <Login onLogin={ (data) => onLogin(data, history) }
            onUpdateSnack={onUpdateSnack}
            status={ status } />
    </div>
)))

export default LoginContainer