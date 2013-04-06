package models

import com.typesafe.config.Config

/**
 * Typesafe Config format ‘front-matter properties’ for a post, identified by slug.
 */
case class Post(slug: String, properties: Config) {

  /**
   * Wrapper for getting a property that avoids the need to check for defined properties,
   * because `Config.getString` throws an exception if there is no value for the given path.
   */
  def property(path: String): Option[String] = {
    if (properties.hasPath(path)) {
      Some(properties.getString(path))
    }
    else {
      None
    }
  }
}
