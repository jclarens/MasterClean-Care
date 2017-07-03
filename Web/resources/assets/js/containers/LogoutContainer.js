import React from 'react'
import { simpleAuthentication } from './LoginContainer'
import Logout from '../components/Logout'
import {
    Route, 
    Redirect,
    withRouter,
    Link
} from 'react-router-dom'

const LogoutContainer = withRouter(({ history }) => (
     <Route render={props => (
        simpleAuthentication.isAuthenticated() ? (
            <Logout onClick={() => simpleAuthentication.signout(history)} />
        ) : (
            <Redirect to={{
                pathname: '/login',
                state: { from: props.location }
            }}/>
        )
    )}/>
))

export default LogoutContainer