import React from 'react'
import { render } from 'react-dom'
import { Provider } from 'react-redux'
import { createStore } from 'redux'
import { BrowserRouter as Router, Route, browserHistory } from "react-router-dom"
import configureStore from './store/configureStore'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider'
import App from './js/components/App'
import LoginContainer from './js/containers/LoginContainer'
import injectTapEventPlugin from 'react-tap-event-plugin'

injectTapEventPlugin()

const store = configureStore();

const requireAuth = (nextState, replace) => {
    if (!userExists()) {
        replace({
            pathname: '/login',
            state: { nextPathname: nextState.location.pathname }
        })
    }
}

render(
    <MuiThemeProvider>
        <Provider store={store}>
            <Router history={browserHistory} >
                <div>
                    <Route path="/" component={App} onEnter={requireAuth} />
                    <Route path="/login" component={LoginContainer} />
                </div>
            </Router>
        </Provider>
    </MuiThemeProvider>,
    document.getElementById('root')
)
