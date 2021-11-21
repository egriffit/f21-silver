module.exports = {
    components: {
      schemas: {
        //success
         success: {
            type: "object",
            properties: {
                success: {
                    type: "Boolean",
                    description: "status of operation",
                    example: "True"
                },
                message: {
                    type: "String",
                    description: "Response message",
                    example: "Source sucessfully added"
                }
            }
        },
        // advice model
        advice: {
            type: "object",
            properties: {
                adviceType: {
                    type: "String",
                    description: "a goal (Gain Strength, Gain Mass, Lose Weight",
                    example: "gain strength"
                },
                advice: {
                    type: "String",
                    description: "Fitness advice",
                    example: "Strive to consume between 1.6 to 2.2g/kg of protein per day"
                },
                sourceId: {
                    type: "String",
                    description: "id for a source",
                    example: '618d815b74721b96f3159277'
                }
            }
        },
        // user model
        user: {
            type: "object",
            properties: {
                username: {
                    type: "String",
                    description: "email for user used as a username",
                    example: "johnsmith@gmail.com"
                },
                password: {
                    type: "String",
                    description: "User's password",
                    example: "myPassword!"
                }
            }
        },
        //source nodel
        source: {
            type: "object",
            properties: {
                sourceType: {
                    type: "String",
                    description: "source type (Article, Book, Webpage)",
                    example: "article"
                },
                title: {
                    type: "String",
                    description: "Title for article, chapter or webpage",
                    example: "How much protein can the body use in a single meal for muscle building? Implications for daily protein distribution"
                },
                authors: {
                    type: "[String]",
                    description: "an array of author names",
                    example: "\[\"Brad Jon Schoenfeld\"\,\"Alan Albert Aragan\"\]"
                },
                year: {
                    type: "Number",
                    description: "Year published",
                    example: "2018"
                },
                journal: {
                    type: "String",
                    description: "name of journal",
                    example: "Journal of International Society of Sports Nutrition"
                },
                issue: {
                    type: "String",
                    description: "Issue in Journal",
                    example: "10"
                },
                editors: {
                    type: "[String]",
                    description: "an array of editor names",
                    example: "\[\"Brad Jon Schoenfeld\"\,\"Alan Albert Aragan\"\]"
                },
                bookTitle: {
                    type: "String",
                    description: "Title of Book or Collection",
                    example: "Collected Works of William Shakespeare"
                },
                publisher: {
                    type: "String",
                    description: "Name of publisher",
                    example: "Penguin Classics"
                },
                publisherLocation: {
                    type: "String",
                    description: "location of publisher",
                    example: "London"
                },
                webpage: {
                    type: "String",
                    description: "URI for webpage",
                    example: "wwww.wikipedia.org"
                },
            }
        },
      },
    },
  };