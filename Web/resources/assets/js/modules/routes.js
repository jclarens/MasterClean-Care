import React from 'react'
import { Switch, Route, IndexRoute } from 'react-router'
import { simpleAuthentication } from '../containers/LoginContainer'
import App from '../components/App'
import LoginContainer from '../containers/LoginContainer'
import RegisterContainer from '../containers/RegisterContainer'
import UserProfileContainer from '../containers/UserProfileContainer'

const routes = (store) => {
    const requireAuth = (store) => {
        return (nextState, replace) => {
            const state = store.getState()

            console.log(state)

            // if (state) {
            //     replace({
            //         pathname: '/login',
            //         state: { nextPathname: nextState.location.pathname }
            //     })
            // }
        }
    }

    return (
        <Switch>
            <Route exact path="/" component={ App } />
            <Route path="/login" component={ LoginContainer }/>
            <Route path="/register" component={ RegisterContainer }/>
            <Route path="/user" component={ UserProfileContainer } onEnter={ requireAuth(store) } />
        </Switch>
    )
}

export default routes