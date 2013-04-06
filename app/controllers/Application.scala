package controllers

import play.api._
import play.api.Play.current
import play.api.mvc._
import play.api.libs.Files
import models.Post
import com.typesafe.config.{Config, ConfigFactory}
import java.io.File
import views.Helpers

object Application extends Controller {

  val postsDirectory = "posts"

  /**
   * Output an index page - a list of posts.
   */
  def index = Action {
    Ok(views.html.index(posts))
  }

  /**
   * Render a post as HTML from Markdown source.
   */
  def post(slug: String) = Action {
    val path = "%s/%s.md" format (postsDirectory, slug)
    val file = Play.getFile(path)
    if (file.exists()) {
      val content = Files.readFile(file)
      val body = content.split("---\n").last
      Ok(views.html.page(Post(slug, properties(file)), Helpers.markdown(body)))
    }
    else {
      NotFound("No source for post %s" format slug)
    }
  }

  /**
   * A list of this site’s posts in reverse chronological order.
   */
  private def posts: Seq[Post] = {
    val postFiles = Play.getFile(postsDirectory).listFiles()
    val posts = postFiles.toList.map { file =>
      if (file.getName.endsWith(".md") || file.getName.endsWith(".markdown")) {
        val slug = file.getName.split('.').dropRight(1)(0)
        Post(slug, properties(file))
      }
      else {
        null
      }
    }
    posts.sortBy(_.date).reverse
  }

  /**
   * Read post ‘properties’ from a post’s ‘front-matter’ in Typesafe Config format.
   */
  private def properties(post: File): Config = {
    val content = Files.readFile(post)
    val sections = content.split("---\n")
    if (sections.length > 1) {
      ConfigFactory.parseString(sections(1))
    }
    else {
      ConfigFactory.empty
    }
  }
}