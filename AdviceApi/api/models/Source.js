'use strict';

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var sourceSchema = new Schema({
    sourceType: {
        type: String,
        required: 'Type the source type (Article, Book, Webpage)'
    },
    title: {
        type: String,
        required: 'Type the arrticle, webpage or chapter title'
    },
    authors: [{
        type: String,
        required: 'Type the name of the author'
    }],
    year: {
        type: Number,
        required: 'Year the source was published'
    },
    journal: {
        type: String,
        required: "Type the name of the journal"
    },
    volume: {
        type: String,
        required: "Type the name of the volume"
    },
    issue: {
        type: String,
        required: "Type the name of the issue"
    },
    editors: [{
        type: String,
        required: 'Type the name of the editors'
    }],
    bookTitle: {
        type: String,
        required: 'Type the book or collection title'
    },
    publisher: {
        type: String,
        required: 'Type the name of the publisher'
    },
    publisherLocation: {
        type: String,
        required: 'Type the name of the publisher\'s location'
    },
    webpage: {
        type: String,
        required: 'Type the url of the webpage'
    }
});

module.exports = mongoose.model('source', sourceSchema);