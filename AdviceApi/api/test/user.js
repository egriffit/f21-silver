//During the test the env variable is set to test
process.env.NODE_ENV = 'test';

let mongoose = require("mongoose");
advice = require('../models/username');

//Require the dev-dependencies
let chai = require('chai');
let chaiHttp = require('chai-http');
let server = require('../server');
let should = chai.should();


chai.use(chaiHttp);
//Our parent block
describe('User', () => {
    // beforeEach((done) => { //Before each test we empty the database
    //     user.remove({}, (err) => {
    //        done();
    //     });
    // });
/*
  * Test the /Post createUser route
  */
  describe('/POST create a user', () => {
      it('it should create a user', (done) => {
        chai.request(server)
            .post('/adviceapi/createUser')
            .set('content-type', 'application/x-www-form-urlencoded')
            .send({
                "username": "test",
                "password": "myPassword"
            })            
            .end((err, res) => {
                  res.should.have.status(200);
                  res.body.should.have.property('success', true)
                  res.body.should.have.property('message', "User test was created")
                  //res.body.length.should.be.eql(0);
              done();
            });
      });
  });
/*
  * Test the /Post validate user route
  */
describe('/POST validate a user', () => {
    it('it should compare the username and password provided with the username and hashed password in database', (done) => {
      chai.request(server)
          .post('/adviceapi/login')
          .set('content-type', 'application/x-www-form-urlencoded')
          .send({
              "username": "test",
              "password": "myPassword"
          })            
          .end((err, res) => {
                res.should.have.status(200);
                res.body.should.have.property('success', true)
                res.body.should.have.property('message', "Successfully Logged In")
                //res.body.length.should.be.eql(0);
            done();
          });
    });
});

});


/*
  
*/