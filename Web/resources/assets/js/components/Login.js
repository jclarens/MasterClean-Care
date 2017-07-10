import React, { Component } from 'react'
import PropTypes from 'prop-types'
import FlatButton from 'material-ui/FlatButton'
import RaisedButton from 'material-ui/RaisedButton'
import TextField from 'material-ui/TextField'
import Paper from 'material-ui/Paper'
import NotificationContainer from '../containers/NotificationContainer'
import LoadingSpinContainer from '../containers/LoadingSpinContainer'
import history from '../modules/history'
import RegisterOption from './RegisterOption'

class Login extends Component {
    constructor(props){
        super(props)
        this.state = {
            email: '',
            password: '',
        }
    }

    loginHandler(e) {
        e.preventDefault()
        let locErrMessage = ''

        if (this.state.email == '' && this.state.password == '') {
            locErrMessage = 'Username & Password cannot be empty!'
        }
        else if (this.state.email == '') {
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
                email: this.state.email,
                password: this.state.password,
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
            <div>
                <div className="row">
                    <div className="col m3 xl4 hide-on-small-only"></div>
                    <Paper className="col s12 m6 xl4" zDepth={1} style={{ margin: "10px auto", padding: "10px" }}>
                        <form onSubmit={(e) => this.loginHandler(e)}>
                            <div className="row">
                                <div className="col s12">
                                    <h4 className="center-align">Login</h4>
                                </div>
                                <div className="col s12">
                                    <TextField
                                        hintText="Email"
                                        floatingLabelText="Email"
                                        fullWidth={true}
                                        name="email"
                                        onChange={(e) => this.onChangeHandler(e)}
                                        autoComplete={false}
                                    />
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
                                </div>
                                <div className="input-field col s12 m6">
                                    <RegisterOption />
                                </div>
                                <div className="input-field col s12 m6">
                                    <RaisedButton 
                                        label="Login" 
                                        fullWidth={true} 
                                        type="submit"/>
                                </div>                      
                            </div>
                        </form>
                    </Paper>
                </div>
                <NotificationContainer />
                <LoadingSpinContainer />
            </div>
        )
    }
}

Login.propTypes = {
    onLogin: PropTypes.func.isRequired,
}

export default Login;