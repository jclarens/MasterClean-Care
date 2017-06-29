import React from 'react'
import { render } from 'react-dom'
import { Provider } from 'react-redux'
import { createStore } from 'redux'
import { BrowserRouter as Router, Route } from "react-router-dom"
import routes from './js/modules/routes'
import history from './js/modules/history'
import configureStore from './js/stores/configureStore'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider'
import injectTapEventPlugin from 'react-tap-event-plugin'
import "./css/less/materialize.less"
import "./css/less/default.less"

injectTapEventPlugin()

const store = configureStore()

render(
    <MuiThemeProvider>
        <Provider store={ store }>
            <Router history={ history } >
                { routes() }
            </Router>
        </Provider>
    </MuiThemeProvider>,
    document.getElementById('root')
)


{/*<div>
        <routes />
        <Route path="/" component={App} onEnter={requireAuth} />
        <Route path="/login" component={LoginContainer} />
    </div>
</Router>*/}