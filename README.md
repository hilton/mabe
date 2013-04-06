# MABE - Markdown Blog Engine

A minimal Markdown blog engine implemented as Play-flavour interpretation of
[Jekyll](https://github.com/mojombo/jekyll).

* Posts are rendered dynamically, instead of generating a static site.
* Each post defines properties in a ‘front-matter’ block that uses Typesafe Config format - the same format as `application.conf` - instead of YAML.
* The page properties are all more or less optional, depending on what you do in the templates.
* Post URLs are defined using normal Play routes.
* Templates are normal Play templates.

If you wanted to use this seriously, you’d probably want to cache the list of posts that `controllers.Application.posts` returns, and possibly the generated Markdown body of each post.

It might be useful to make `date` a required post property, so that it can be split into date parts and used to define URL paths like `/:year/:month/:day/:slug`.

The two example posts are from the [Lunatech Blog](http://blog.lunatech.com/), used with permission.
