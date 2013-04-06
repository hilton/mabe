package models

import com.typesafe.config.Config
import java.util.Date
import org.joda.time.format.DateTimeFormat
import org.joda.time.LocalDate

/**
 * Typesafe Config format ‘front-matter properties’ for a post, identified by slug.
 */
case class Post(slug: String, properties: Config) {

  /**
   * Return the post’s date as a `java.util.Date`; `date` is not a required post property,
   * so default to today’s date.
   */
  def date: Date = {
    property("date").map { date =>
      DateTimeFormat.forPattern("YYYY-MM-dd").parseLocalDate(date).toDate
    }.getOrElse(new LocalDate().toDate)
  }

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
