'use strict';

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var adviceSchema = new Schema({
    adviceType: {
        type: String,
        required: 'Type a goal (Gain Strength, Gain Mass, Lose Weight) '
    },
    advice: {
        type: String,
        required: 'Type the advice'
    },
    sourceId: {
        type: String,
        required: 'Type the ID for the source'
    }
});

module.exports = mongoose.model('advice', adviceSchema);