import React from 'react'
import { connect } from 'react-redux'
import Login from '../components/Login'
import {
    Route, 
    Redirect,
    withRouter
} from 'react-router-dom'
import {
    updateSnack
} from '../actions/DefaultAction'
import axios from 'axios'

const allowedUser = {
    username: 'admin',
    password: 'admin'
}

const authenticateLogin = (data, history) => {
    if (data.username == allowedUser.username
            && data.password == allowedUser.password) {
        localStorage.setItem('authState', 
            JSON.stringify({
                isAuthenticated : true,
                username: data.username
            }));
        return undefined
    }
    else {
        return {
            open: true,
            message: 'Invalid Login!'
        }
    }
}

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
        return authenticateLogin(e, history)
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
            axios.post('/api/login', {
                email: data.email,
                password: data.password
            })
            .then(function (response) {
                console.log(JSON.parse(response.data.data))
                return
                let status = simpleAuthentication.authenticate(JSON.parse(response.data.data), history)
                if (status === undefined) {
                    history.push('/')
                }
                else {
                    dispatch(updateSnack(status))
                }
            })
            .catch(function (error) {
                dispatch(updateSnack({
                    open: open,
                    message: error
                }))
            });
            
        }
    }
}

const LoginContainer = withRouter(connect(
    mapStateToProps,
    mapDispatchToProps
)(({ history, onUpdateSnack, onLogin, status }) => (
     <Route render={props => (
        simpleAuthentication.isAuthenticated() ? (
            <Redirect to={{
                pathname: '/',
                state: { from: props.location }
            }}/>
        ) : (
            <div className="container">
                <Login onLogin={(data) => onLogin(data, history)}
                    onUpdateSnack={onUpdateSnack}
                    status={status} />
            </div>
        )
    )}/>
)))

export default LoginContainer