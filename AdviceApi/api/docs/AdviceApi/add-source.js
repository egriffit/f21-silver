module.exports = {
    // method of operation
    post: {
      tags: ["Source CRUD operations"], // operation's tag.
      description: "Create Source", // operation's desc.
      operationId: "createSource", // unique operation id.
      parameters: [{
          in: "post",
          name: "sourceType",
          schema: {
              type: "String"
            },
            "description": "Type of Source (article, webpage, book)"
      },
      {
          in: "post",
          name: "title",
          schema: {
              type: "String"
            },
            "description": "Title of article, chapter or webpage"
      },
      {
          in: "post",
          name: "authors",
          schema: {
              type: "[String]"
            },
            "description": "Array of author names"
      },
      {
        in: "post",
        name: "year",
        schema: {
            type: "Number"
          },
          "description": "Year source was published"
    },
    {
        in: "post",
        name: "journal",
        schema: {
            type: "String"
          },
          "description": "name of journal"
    },
    {
        in: "post",
        name: "volume",
        schema: {
            type: "String"
          },
          "description": "volume number"
    },
    {
        in: "post",
        name: "issue",
        schema: {
            type: "String"
          },
          "description": "issue number"
    },
    {
        in: "post",
        name: "editors",
        schema: {
            type: "[String["
          },
          "description": "Array of editor names"
    },
    {
        in: "post",
        name: "bookTitle",
        schema: {
            type: "String"
          },
          "description": "Title of book or collection"
    },
    {
        in: "post",
        name: "publisher",
        schema: {
            type: "String"
          },
          "description": "Name of publisher"
    },
    {
        in: "post",
        name: "publisherLocation",
        schema: {
            type: "String"
          },
          "description": "Location of publisher"
    },
    {
        in: "post",
        name: "webpage",
        schema: {
            type: "String"
          },
          "description": "URI of webpage"
    },
  ], // expected params.
      // expected responses
      responses: {
        // response code
        200: {
          description: "returns a json object with success status and message", // response desc.
          content: {
            // content-type
            "application/json": {
              schema: {
                $ref: "#/components/schemas/success", 
              },
            },
          },
        },
      },
    },
  };