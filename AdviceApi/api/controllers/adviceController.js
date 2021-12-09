var mongoose = require('mongoose'),
    advice = mongoose.model('advice');

//find advice by advice type
exports.getAdviceByType = function(req, res){
    advice.find({"adviceType": req.query.adviceType.toLowerCase().trim()}, function(err, advice){
        if(err)
        {
            res.send(err);
        }
        res.json(advice);
    });
};

//create advice
exports.createAdvice = async function(req, res){
    result = await advice.findOne({"advice": req.body.advice.toLowerCase().trim()}) 
    if(!result){
        var new_advice = new advice({adviceType: req.body.advice.toLowerCase().trim(), advice: req.body.advice.toLowerCase().trim(), sourceId: req.body.sourceId});
        new_advice.save(function(err, task){
            if(err){
                res.send(err);
            }else{
                res.json({success: true,
                    response: "Advice successfully added"})
            }
        });
    }else{
        res.json({success: false,
            response: "Advice '" + req.body.advice + "' Already Exists"})
    }
};

exports.removeAllAdvice = function(req, res){
    if(req.body.key === ")qbBKar2?9gY7aeP9S^,Q9!#y)2hC)S;wC)>>=gm")
    {
        advice.collection.drop();
        res.json({
            message: "All advice has been deleted"
        })
    }else{
        res.json({message: "You are not allowed to delete the advice"})
    }
}
