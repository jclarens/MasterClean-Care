import React, { Component } from 'react'
import PropTypes from 'prop-types'
import RaisedButton from 'material-ui/RaisedButton'

const style = {
    margin: 12,
}

const Register = (onSelectRegister, REGISTER_TYPE) => {
    return (
        <div>
            <RaisedButton
                label="Member"
                style={style}
                onClick={() => onSelectRegister(REGISTER_TYPE.REGISTER_MEMBER)}
            />
            <RaisedButton
                label="ART"
                style={style}
                onClick={() => onSelectRegister(REGISTER_TYPE.REGISTER_ART)}
            />
        </div>
    )
}

Register.propTypes = {
    onSelectRegister: PropTypes.func.isRequired,
    REGISTER_TYPE: PropTypes.object.isRequired
}

export default Register