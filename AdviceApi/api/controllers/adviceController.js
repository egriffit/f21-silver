var mongoose = require('mongoose'),
    advice = mongoose.model('advice');

//find advice by advice type
exports.getAdviceByType = function(req, res){
    advice.find({"adviceType": req.query.adviceType}, function(err, advice){
        if(err)
        {
            res.send(err);
        }
        res.json(advice);
    });
};

//create advice
exports.createAdvice = function(req, res){
    var new_advice = new advice(req.body);
    new_advice.save(function(err, task){
        if(err){
            res.send(err);
        }else{
            res.json({success: true,
                response: "Advice successfully added"})
        }
    });
};