JSubSchema
==========

Validation and code-generation for a semi-subset of JSON-Schema.  Who says XML should have all the fun?

After working on web apps for a while, I wanted to explore ways to easily integrate living types, docs, and specs with existing APIs.
JSON-Schema (json-schema.org) is a good start, but I found it to be a bit too cumbersome and all-encompassing for my needs, so I just took what I wanted from it.
JSubSchema is my dialect, with a few differences (the greatest being lack of support for union types - that's a pretty strong code smell to me).

This repo contains a JSubSchema parser/validator/code-generator/more in Java.  It's bootstrapped to use code generated from the models with which it generates its code...

Here is a simple schema:

    {
      "id": "http://exathunk.net/schemas/githubprofile",
      "type": "object",
      "description": "A representation of a Github profile",
      "required": ["email"],
      "properties": {
        "email": {
          "type": "string",
          "format": "email"
        },
        "projects": {
          "type": "array",
          "items": {
            "type": "object",
            "description": "A project",
            "required": ["title", "url"],
            "properties": {
              "title": {
                "type": "string"
              },
              "url": {
                "type": "string",
                "format": "uri"
              },
              "numCommits": {
                "type": "integer"
              },
              "lastCommit": {
                "type": "string",
                "format": "date"
              }
            }
          }
        }
      }
    }

This schema will validate JSON documents like this:

    { "email": "ejconlon@gmail.com", "projects": [{"title": "jsubschema", "url": "https://github.com/ejconlon/jsubschema"}] }

But will object if the types are wrong or required attributes are missing, etc.



