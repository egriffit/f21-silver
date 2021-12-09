var mongoose = require('mongoose'),
    user = mongoose.model('username');
    const bcrypt = require("bcrypt");

   // https://www.thepolyglotdeveloper.com/2019/02/hash-password-data-mongodb-mongoose-bcrypt/
    
//get user
exports.getUser = function(req, res){
    user.findOne({"username": req.body.username}, function(err, u){
        if (err) throw err;
        if(u != null){
            u.comparePassword( req.body.password, function(err, isMatch){
                if (err) throw err;
                if(isMatch == true){
                    res.json({success: isMatch,
                        message: "Successfully Logged In"}) 
                }
                else{
                    res.json({success: isMatch,
                        message: "The user and password combination does not match"})
                }
                
            })
        }
    })      
};

//create user
exports.createUser = async function(req, res){
    var new_user = new user(req.body);
    var result = await user.findOne({"username": req.body.username})
    if(!result){
        new_user.save(function(err, task){
            if(err){
                res.send(err);
            }
            else{
                res.json({success: true,
                    message: "User " + new_user.username + " was created"})
            }
        });
    }else{
        res.json({success: false,
            message: "User " + new_user.username + " already exists"})
    }
};

exports.removeAllUsers = function(req, res){
    if(req.body.key === ")qbBKar2?9gY7aeP9S^,Q9!#y)2hC)S;wC)>>=gm")
    {
        user.collection.drop();
        res.json({
            message: "All User Accounts have been deleted"
        })
    }else{
        res.json({message: "You are not allowed to delete the ysers"})
    }

}