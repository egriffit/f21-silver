

var express = require('express'),
app = express(),
port = process.env.PORT || 7000;
const cors = require("cors");
const docs = require('./docs');

mongoose = require('mongoose');
advice = require('./models/Advice');
source = require('./models/Source');
user = require('./models/username');
bodyParser = require('body-parser');
const swaggerUI = require("swagger-ui-express");
const adviceRouter = require('./routes/adviceRoutes');

mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost:27017/AdviceAPI', { useNewUrlParser: true, useUnifiedTopology: true  });

app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());
app.use(cors());
app.use('/api-docs',swaggerUI.serve,swaggerUI.setup(docs));
app.use('/adviceapi',adviceRouter);

app.listen(port)
console.log("Api server started on: " + port);

module.exports = app; // for testing