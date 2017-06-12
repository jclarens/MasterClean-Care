import React, { Component } from 'react'
import PropTypes from 'prop-types'
import RaisedButton from 'material-ui/RaisedButton'
import TextField from 'material-ui/TextField'
import Paper from 'material-ui/Paper'
import NotificationContainer from '../containers/NotificationContainer'

class Login extends Component {
    constructor(props){
        super(props)
        this.state = {
            username: '',
            password: ''
        }
    }

    loginHandler(e) {
        e.preventDefault()
        let locErrMessage = ''

        if (this.state.username== '' && this.state.password == '') {
            locErrMessage = 'Username & Password cannot be empty!'
        }
        else if (this.state.username == '') {
            locErrMessage = 'Username cannot be empty!'
        }
        else if (this.state.password == '') {
            locErrMessage = 'Password cannot be empty!'
        }
        
        if (locErrMessage != '') {
            this.props.onUpdateSnack(true, locErrMessage)
        }
        else {
            this.props.onLogin({
                username: this.state.username,
                password: this.state.password
            })
        }

    }

    onChangeHandler(e) {
        const target = e.target
        const value = target.value
        const name = target.name

        this.setState({ [name]: value })
    }

    render () {
        return (
            <div className="row">
                <div className="col m3 xl4 hide-on-small-only"></div>
                <Paper className="col s12 m6 xl4" zDepth={1} style={{ margin: "10px auto", padding: "10px" }}>
                    <form onSubmit={(e) => this.loginHandler(e)}>
                        <div className="row">
                            <div className="col s12">
                                <h4 className="center-align">Mini Todo</h4>
                            </div>
                            <div className="col s12">
                                <TextField
                                    hintText="Username"
                                    floatingLabelText="Username"
                                    fullWidth={true}
                                    name="username"
                                    onChange={(e) => this.onChangeHandler(e)}
                                    autoComplete={false}
                                />
                                {/*<input id="username" type="text" className="validate" ref={node => { inputUsername = node }}/>
                                <label htmlFor="username">Username</label>*/}
                            </div>
                            <div className="col s12">
                                <TextField
                                    hintText="Password"
                                    floatingLabelText="Password"
                                    type="password"
                                    fullWidth={true}
                                    name="password"
                                    onChange={(e) => this.onChangeHandler(e)}
                                />
                                {/*<input id="password" type="password" className="validate" ref={node => { inputPassword = node }}/>
                                <label htmlFor="password">Password</label>*/}
                            </div>
                            <div className="col m6 xl8 hide-on-small-only"></div>
                            <div className="input-field col s12 m6 xl4">
                                <RaisedButton label="Login" fullWidth={true} type="submit"/>
                            </div>                      
                        </div>
                    </form>
                </Paper>
                <NotificationContainer />
            </div>
        )
    }
}

Login.propTypes = {
    onLogin: PropTypes.func.isRequired
}

export default Login;