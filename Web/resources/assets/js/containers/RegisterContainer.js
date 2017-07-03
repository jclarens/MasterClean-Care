import { connect } from 'react-redux'
import { filterTodo, updateSnack } from '../actions/DefaultAction'
import Register from '../components/Register'
import axios from 'axios'

const REGISTER_TYPE = {
    REGISTER_MEMBER: 'REGISTER_MEMBER',
    REGISTER_ART: 'REGISTER_ART'
}

const mapStateToProps = (state) => {
    return {
        state,
        REGISTER_TYPE
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onSelectRegister: (registerType, history) => {
            switch(registerType) {
                case REGISTER_TYPE.REGISTER_MEMBER:
                    history.push('register/member')
                    break;
                case REGISTER_TYPE.REGISTER_ART:
                    history.push('register/art')
                    break;
                default:
                    break;
            }
        }
    }
}

const RegisterContainer = connect(
    mapStateToProps,
    mapDispatchToProps    
)(Register)

export default RegisterContainer
