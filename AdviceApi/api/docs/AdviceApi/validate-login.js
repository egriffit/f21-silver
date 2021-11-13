module.exports = {
    // method of operation
    post: {
      tags: ["User CRUD operations"], // operation's tag.
      description: "Validate User Login", // operation's desc.
      operationId: "validateLogin", // unique operation id.
      parameters: [
        {
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
          description: "returns a success json object with a success status and response message", // response desc.
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