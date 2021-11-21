module.exports = {
  // method of operation
  post: {
    tags: ["Advice CRUD operations"], // operation's tag.
    description: "Create Advice", // operation's desc.
    operationId: "addAdvice", // unique operation id.
    parameters: [{
        in: "post",
        name: "adviceType",
        schema: {
            type: "String"
          },
          "description": "The adviceType (e.g. gain strength)"
    },
    {
        in: "post",
        name: "advice",
        schema: {
            type: "String"
          },
          "description": "Fitness advice"
    },
    {
        in: "post",
        name: "sourceID",
        schema: {
            type: "String"
          },
          "description": "ID for Source"
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