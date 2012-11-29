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

But will object if the types are wrong or required attributes are missing, etc. (And provide maybe-helpful error messages!)

In order to generate usable Java code from this schema, we have to normalize.  For this reason I've added a "declarations" property to the
meta schema definition, and use JSON-Schema-style "$ref" links to internally reference declared types.  The above schema is normalized like this:

     {
       "id": "http://exathunk.net/schemas/githubprofile",
       "type": "object",
       "description": "A representation of a Github profile",

       "declarations": {
         "/properties/projects/items": {
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
         },
         "/properties/projects": {
           "type": "array",
           "items": {
             "$ref": "#/declarations/~1properties~1projects~1items"
           }
         }
       },

       "required": ["email"],
       "properties": {
         "email": {
           "type": "string",
           "format": "email"
         },
         "projects": {
           "$ref": "#/declarations/~1properties~1projects"
         }
       }
     }

(The "~1" bits are the JSON-Pointer escape sequences for "/".)  Generating code is easy from this point:

    @JsonDeserialize(as = Githubprofile.class)
    public interface GithubprofileLike {

        boolean hasEmail();

        @JsonProperty("email")
        String getEmail();

        @JsonProperty("email")
        void setEmail(String email);

        boolean hasProjects();

        @JsonProperty("projects")
        PropertiesProjectsLike getProjects();

        @JsonProperty("projects")
        @JsonDeserialize(as = PropertiesProjects.class)
        void setProjects(PropertiesProjectsLike projects);

    }

(With appropriate definitions for PropertiesProjects, etc.)  The annotations are from the Jackson library and are placed on
interfaces so the data can be backed by any source - a database, for example.  The generated concrete classes
are simple beans with useful toString(), equals(), and hashCode() defined, and are accompanied by factory classes for metaprogramming.

So how about some cool applications?  Have you ever been stuck in template hell, tweaking your models and controllers endlessly to
satisfy changed or ill-exercised view templates?  Why not generate a schema from the template to start, or guard against regressions
by checking that a schema satisfies a template?  Take this Mustache partial template:

    <h1>Github Profile: {{email}}</h1>
    <p> Projects: </p>
    <ul>
    {{#projects}}
      <li><a href="{{url}}">{{title}}</a></li>
    {{/projects}}
    </ul>

It is easy to see that this can be successfully filled by our "githubprofile" JSON document, and the "crustache" module in JSubSchema agrees.
As you would expect, the minimal schema it generates is much like the hand-written one:

    {
      "id": "http://exathunk.net/schemas/githubprofile",
      "properties": {
        "email": {
          "type": "string"
        },
        "projects": {
          "items": {
            "properties": {
              "title": {
                "type": "string"
              },
              "url": {
                "type": "string"
              }
            },
            "required": [
              "url",
              "title"
            ],
            "type": "object"
          },
          "type": "array"
        }
      },
      "required": [
        "email"
      ],
      "type": "object"
    }

Other applications?  Data integrity proxies, fuzzers + test case generators, multi-language code generation, and so on.

Now, let's be serious.  There are some truly puzzling corners in this code base - hobby-hacking is dirty work :)
I was figuring out a lot of things on the fly.
However, there are a tests that cover the core aspects, and in general things are pretty modular and functional, so there is a good foundation.
Please talk to me if you are interested in exploring more!

Eric Conlon

ejconlon@gmail.com