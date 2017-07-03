import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import PropTypes from 'prop-types'
import MenuItem from 'material-ui/MenuItem'
import FontIcon from 'material-ui/FontIcon'
import FlatButton from 'material-ui/FlatButton'
import RaisedButton from 'material-ui/RaisedButton'
import Dialog from 'material-ui/Dialog'

const style = {
    marginLeft: '10px'
}

class RegisterOption extends Component {
    constructor(props) {
        super(props)

        this.state = {
            openModal: false
        }
    }

    handleOpen() {
        this.setState({openModal: true});
    }

    handleClose() {
        this.setState({openModal: false});
    }

    render() {
        const actions = [
            <FlatButton
                label="Batal"
                style={style}
                onTouchTap={() => this.handleClose()}
            />,
            <RaisedButton
                label="Member"
                style={style}
                containerElement={<Link to="/register/member" />}
            />,
            <RaisedButton
                label="ART"
                style={style}
                containerElement={<Link to="/register/art" />}
            />
        ]

        return (
            <div>
                <FlatButton 
                    label="Mendaftar" 
                    fullWidth={true}
                    onClick={ () => this.handleOpen() }/>
                <Dialog
                    title="Pilihan Pendaftaran"
                    actions={ actions }
                    modal={ true }
                    open={ this.state.openModal }
                    >
                    Pilih mode pengguna
                </Dialog>
            </div>
        )

    }
}

{/*<a href="#" className="waves-effect waves-light btn red" onClick={(e) => {
        e.preventDefault()
        onClick()
    }}>Logout
    </a>*/}

export default RegisterOption