import React from 'react'
import { connect } from 'react-redux'
import { filterTodo, updateSnack } from '../actions/DefaultAction'
import RegisterArt from '../components/RegisterArt'
import {
    withRouter,
} from 'react-router-dom'
import axios from 'axios'

const mapStateToProps = (state) => {
    return {
        status: state.notif
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onUpdateSnack: (open, message) => {
            dispatch(updateSnack({
                open: open,
                message: message
            }))
        },
        onRegister: (self, data, history) => {
            axios.post('/api/user', {
                data
            })
            .then(function (response) {
                let data = response.data
                dispatch(updateSnack({
                    open: true,
                    message: data.message
                }))
            })
            .catch(function (error) {
                dispatch(updateSnack({
                    open: open,
                    message: error
                }))
                self.setState({ isValid : false })
            })
        },
        getPlace: (self, type, lvl = 0) => 
        {
            let dataPlace = [];
            axios.get('/api/place/search/parent/equal/' + lvl)
            .then(function (response) {
                let data = response.data
                if (data.status !== 201) {
                    dispatch(updateSnack({
                        open: true,
                        message: data.message
                    }))
                }
                else {
                    dataPlace = data.data
                }
                self.setState({ [type]: dataPlace })
            })
            .catch(function (error) {
                dispatch(updateSnack({
                    open: open,
                    message: error
                }))
                self.setState({ [type]: dataPlace })
            })
        }
    }
}

const RegisterArtContainer = withRouter(connect(
    mapStateToProps,
    mapDispatchToProps
)(({ history, onUpdateSnack, onRegister, getPlace, status }) => (
    <div className="container">
        <RegisterArt onRegister={ (self, data) => onRegister(self, data, history) }
            getPlace={ getPlace }
            onUpdateSnack={ onUpdateSnack }
            status={ status } />
    </div>
)))

export default RegisterArtContainer
