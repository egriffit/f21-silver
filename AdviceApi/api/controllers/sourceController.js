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
exports.createSource = async function(req, res){
    result = await source.findOne({"title": req.body.title, "sourceType": req.body.sourceType, "bookTitle": req.body.bookTitle}) 
    if(!result){
        var new_source = new source(req.body);
        new_source.save(function(err, task){
            if(err){
                res.send(err);
      	    }
            else
            {
                res.json({success: true,
                    response: "Source successfully added"})
            }
        });
    }else{
        res.json({success: false,
            response: "Source '" + req.body.title + "' already exists"})
    }
};

exports.removeAllSources = function(req, res){
    if(req.body.key === ")qbBKar2?9gY7aeP9S^,Q9!#y)2hC)S;wC)>>=gm")
    {
        source.collection.drop();
        res.json({
            message: "All Sources have been deleted"
        })
    }else{
        res.json({message: "You are not allowed to delete the sources"})
    }
}
