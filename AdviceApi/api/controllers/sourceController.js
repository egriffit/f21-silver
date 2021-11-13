var mongoose = require('mongoose'),
    source = mongoose.model('source');

//get sources
exports.getSources = function(req, res){
    source.find({}, function(err, source){
        if(err)
            res.send(err);
        res.json(source);
    });
};

//create source
exports.createSource = function(req, res){
    var new_source = new source(req.body);
    new_source.save(function(err, task){
        if(err){
            //res.send(err);
            res.send(err)
        }
        else
        {
            res.json({success: true,
                response: "Source successfully added"})
        }
    });
};