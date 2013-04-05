package models

/**
 * A YAML front-matter block property.
 */
case class PostProperty(key: String, value: String)

/**
 * YAML front-matter properties for a post identified by slug.
 */
case class Post(slug: String, properties: Set[PostProperty])
