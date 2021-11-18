module.exports = {
    // method of operation
    post: {
      tags: ["User CRUD operations"], // operation's tag.
      description: "Create User", // operation's desc.
      operationId: "createUser", // unique operation id.
      parameters: [{
          in: "post",
          name: "username",
          schema: {
              type: "String"
            },
            "description": "Username"
      },
      {
          in: "post",
          name: "password",
          schema: {
              type: "String"
            },
            "description": "password"
      }
  ], // expected params.
      // expected responses
      responses: {
        // response code
        200: {
          description: "Returns a json object with the success status and a response message", // response desc.
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