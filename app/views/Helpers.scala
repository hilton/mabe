package views

import play.api.templates.Html
import org.pegdown.PegDownProcessor

/**
 * View template helpers a.k.a. tags.
 */
object Helpers {

  def markdown(markdown: String): Html = {
    Html(new PegDownProcessor().markdownToHtml(markdown))
  }
}