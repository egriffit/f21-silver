lows:

module.exports = {
  // method of operation
  get: {
    tags: ["Advice CRUD operations"], // operation's tag.
    description: "Get Advice by advice Type", // operation's desc.
    operationId: "getAdvice", // unique operation id.
    parameters: [{
        in: "query",
        name: "adviceType",
        schema: {
            type: "String"
          },
          "description": "The adviceType (e.g. gain strength)"
    }], // expected params.
    // expected responses
    responses: {
      // response code
      200: {
        description: "An array of advice objects", // response desc.
        content: {
          // content-type
          "application/json": {
            schema: {
              $ref: "#/components/schemas/advice",
            },
          },
        },
      },
    },
  },
};
