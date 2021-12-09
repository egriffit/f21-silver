'use strict';
    const express = require("express");
    const router = express.Router();

    var adviceControllers = require('../controllers/adviceController');
    var sourceControllers = require('../controllers/sourceController');
    var userController = require('../controllers/userController');
    
    //adviceapi/getAdvice/?adviceType=
    router.get('/getAdvice', adviceControllers.getAdviceByType)
    // /adviceapi/addAdvice
    router.post('/addAdvice', adviceControllers.createAdvice)
    // /removeAllAdvice
    router.post('/removeAllAdvice', adviceControllers.removeAllAdvice)
    // /adviceapi/addSource
    router.post('/addSource', sourceControllers.createSource)
    // /adviceapi/sources
    router.get('/sources', sourceControllers.getSources)
    // /removeAllSources
    router.post('/removeAllSources', sourceControllers.removeAllSources)
    // /adviceapi/login
    router.post('/login', userController.getUser)
    // /adviceapi/createUser
    router.post('/createUser', userController.createUser)
    // /removeAllUsers
    router.post('/removeAllUsers', userController.removeAllUsers)

    //invalid route
    // app.use(function(req, res) {
    //     res.status(404).send({url: req.originalUrl + ' not found'})
    // });
    module.exports = router;