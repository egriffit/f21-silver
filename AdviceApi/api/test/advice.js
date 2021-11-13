//During the test the env variable is set to test
process.env.NODE_ENV = 'test';

let mongoose = require("mongoose");
advice = require('../models/Advice');

//Require the dev-dependencies
let chai = require('chai');
let chaiHttp = require('chai-http');
let server = require('../server');
let should = chai.should();


chai.use(chaiHttp);
//Our parent block
describe('Advice', () => {
    beforeEach((done) => { //Before each test we empty the database
        advice.remove({}, (err) => {
           done();
        });
    });
/*
  * Test the /GET route
  */
  describe('/GET advice by adviceType', () => {
      it('it should get all the advice for an adviceType', (done) => {
        chai.request(server)
            .get('/adviceapi/getAdvice')
            .query({adviceType: 'gain strength'})
            .end((err, res) => {
                  res.should.have.status(200);
                  res.body.should.be.a('array')
                  //res.body.length.should.be.eql(0);
              done();
            });
      });
  });


  /*
  * Test the /GET route
  */
  describe('/post advice by adviceType', () => {
    it('it should create advice in the advice collection', (done) => {
      chai.request(server)
          .post('/adviceapi/addAdvice')
          .set('content-type', 'application/x-www-form-urlencoded')
          .send({
            "adviceType": "gain strength",
            "advice": "Strive to consume between 1.6 to 2.2g/kg of protein per day",
            "sourceId": "618d815b74721b96f3159277"
          })
          .end((err, res) => {
                res.should.have.status(200);
                res.body.should.have.property('success', true)
                res.body.should.have.property('response', "Advice successfully added")
                //res.body.should.be.a('array');
                //res.body.length.should.be.eql(0);
            done();
          });
    });
});

});


/*
  
*/