import React from 'react'
import { Route, Redirect } from 'react-router-dom'
import FilterBar from '../containers/FilterBar'
import TodoLink from './TodoLink'
import { simpleAuthentication } from '../containers/LoginContainer'
import NotificationContainer from '../containers/NotificationContainer'

const App = () => (
    <Route render={props => (
        simpleAuthentication.isAuthenticated() ? (
            <div>
                <TodoLink history={props.history}/>
                <NotificationContainer />
            </div>
        ) : (
            <Redirect to={{
                pathname: '/login',
                state: { from: props.location }
            }}/>
        )
    )}/>
)

export default App