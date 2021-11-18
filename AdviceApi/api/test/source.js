//During the test the env variable is set to test
process.env.NODE_ENV = 'test';

let mongoose = require("mongoose");
source = require('../models/Source');

//Require the dev-dependencies
let chai = require('chai');
let chaiHttp = require('chai-http');
let server = require('../server');
let should = chai.should();


chai.use(chaiHttp);
//Our parent block
describe('Source', () => {
    beforeEach((done) => { //Before each test we empty the database
        source.remove({}, (err) => {
           done();
        });
    });
/*
//   * Test the /GET route
//   */
//   describe('/GET advice by adviceType', () => {
//       it('it should get all the advice for an adviceType', (done) => {
//         chai.request(server)
//             .get('/adviceapi/addSource')
//             .query({adviceType: 'gain strength'})
//             .end((err, res) => {
//                   res.should.have.status(200);
//                   res.body.should.be.a('array')
//                   //res.body.length.should.be.eql(0);
//               done();
//             });
//       });
//   });


  /*
  * Test the /GET route
  */
  describe('/post create a source', () => {
    it('it should create a source in the source collection', (done) => {
      chai.request(server)
          .post('/adviceapi/addSource')
          .set('content-type', 'application/x-www-form-urlencoded')
          .send({
            "sourceType": "Article",
            "title": "How much protein can the body use in a single meal for muscle building? Implications for daily protein distribution",
            "authors": [
                "Brad Jon Schoenfeld",
            "Alan Albert Aragan"
            ],
            "year": 2018,
            "journal": "Journal of International Society of Sports Nutrition",
            "volume": "15",
            "issue": "10",
            "editors": [],
            "bookTitle": " ",
            "publisher": " ",
            "publisherLocation": " ",
            "webpage": " "
        })
          .end((err, res) => {
                res.should.have.status(200);
                res.body.should.have.property('success', true)
                res.body.should.have.property('response', "Source successfully added")
                //res.body.should.be.a('array');
                //res.body.length.should.be.eql(0);
            done();
          });
    });
});

});
