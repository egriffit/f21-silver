//adviceapi/getAdvice/?adviceType=
const getAdvice = require('./get-advice');
//adviceapi/addAdvice
const addAdvice = require('./add-advice');
//adviceapi/addSource
const addSource = require('./add-source');
//adviceapi/source
const getSource = require('./get-source');
//adviceapi/login
const validateLogin = require('./validate-login');
//adviceapi/createUser
const createUser = require('./create-user');

module.exports = {
    paths:{
        '/adviceapi/getAdvice':{
            ...getAdvice,
        },
        'adviceapi/addAdvice':{
            ...addAdvice,
        },
        'adviceapi/addSource':{
            ...addSource,
        },
        'adviceapi/source':{
            ...getSource,
        },
        'adviceapi/login':{
            ...validateLogin,
        },
        'adviceapi/createUser':{
            ...createUser,
        },
    }
}