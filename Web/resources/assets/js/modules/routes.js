import React from 'react'
import { Switch, Route, IndexRoute } from 'react-router'
import { simpleAuthentication } from '../containers/LoginContainer'
import App from '../components/App'
import LoginContainer from '../containers/LoginContainer'

const requireAuth = (nextState, replace) => {
    console.log(simpleAuthentication.isAuthenticated())
    if (!simpleAuthentication.isAuthenticated()) {
        replace({
            pathname: '/login',
            state: { nextPathname: nextState.location.pathname }
        })
    }
}

const routes = () => {
    return (
        <Switch>
            <Route exact path="/" component={ App } />
            <Route path="/login" component={ LoginContainer }/>
        </Switch>
    )
}

export default routes