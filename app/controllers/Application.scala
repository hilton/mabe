package controllers

import play.api._
import play.api.Play.current
import play.api.mvc._
import play.api.templates.Html
import play.api.libs.Files
import org.pegdown.PegDownProcessor
import models.Post

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
      val html = new PegDownProcessor().markdownToHtml(Files.readFile(file))
      Ok(views.html.page(Html(html)))
    }
    else {
      NotFound("No source for post %s" format slug)
    }
  }

  /**
   * A list of this site’s posts in reverse chronological order.
   */
  private def posts: Seq[Post] = {
    val posts = Play.getFile(postsDirectory).listFiles().map(_.getName)
    val slugs = posts.map(_.split('.').dropRight(1)).flatten
    slugs.map(Post(_, Set.empty))
  }
}