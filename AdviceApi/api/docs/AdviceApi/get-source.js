module.exports = {
    // method of operation
    get: {
      tags: ["Source CRUD operations"], // operation's tag.
      description: "Get Sourcse", // operation's desc.
      operationId: "getSource", // unique operation id.
      parameters: [], // expected params.
      // expected responses
      responses: {
        // response code
        200: {
          description: "returns a json object  with source objects", // response desc.
          content: {
            // content-type
            "application/json": {
              schema: {
                $ref: "#/components/schemas/source", 
              },
            },
          },
        },
      },
    },
  };