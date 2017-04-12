package com.github.amuramatsu.gitbucket.rst

import java.io.{PrintWriter, StringWriter}
import laika.api.Transform
import laika.parse.rst.{ReStructuredText, ExtendedHTML}
import laika.render.HTML
import org.htmlcleaner.HtmlCleaner
import org.htmlcleaner.HtmlNode
import org.htmlcleaner.SimpleHtmlSerializer
import org.htmlcleaner.TagNode
import org.htmlcleaner.TagNodeVisitor
import org.slf4j.LoggerFactory

import gitbucket.core.controller.Context
import gitbucket.core.plugin.RenderRequest
import gitbucket.core.plugin.Renderer
import gitbucket.core.service.RepositoryService.RepositoryInfo
import gitbucket.core.view.helpers
import gitbucket.core.util.StringUtil
import play.twirl.api.Html

class RstRenderer extends Renderer {

  private[this] val log = LoggerFactory.getLogger(classOf[RstRenderer])
  private val EXCEPTION_LINES = 5
  private val EXCEPTION_RENDER_STYLE = "color:black;background-color:#ffcaca"

  def render(request: RenderRequest): Html = {
    import request._
    Html(toHtml(filePath, fileContent, branch,
      repository,
      enableWikiLink, enableRefsLink)(context))
  }

  def shutdown(): Unit = {
  }

  def toHtml(filePath: List[String], rst: String, branch: String,
    repository: RepositoryInfo,
    enableWikiLink: Boolean, enableRefsLink: Boolean)
      (implicit context: Context): String = {

    log.info("About to render ReSTructured text")

    val rendered = try {
      Transform from ReStructuredText to HTML rendering ExtendedHTML fromString rst toString
    } catch {
      case e: Exception => {
        val sw = new StringWriter()
        e.printStackTrace(new PrintWriter(sw))
        val stackTrace = sw.toString.lines take EXCEPTION_LINES mkString "\n"
        s"""|<h2 style="$EXCEPTION_RENDER_STYLE">Internal Error is occured</h2>
            |<pre style="$EXCEPTION_RENDER_STYLE">
            |${ StringUtil.escapeHtml(stackTrace) }
            |  ...
            |</pre>
            |<hr>
            |<pre>
            |${ StringUtil.escapeHtml(rst) }
            |</pre>""".stripMargin
      }
    }

    val path = filePath.reverse.tail.reverse match {
      case Nil => ""
      case p => p.mkString("", "/", "/")
    }
    val relativeUrlPrefix =
      s"${helpers.url(repository)}/blob/${branch}/${path}"
    val relativeImageUrlPrefix =
      s"${helpers.url(repository)}/raw/${branch}/${path}"
    prefixRelativeUrls(rendered, relativeUrlPrefix, relativeImageUrlPrefix)
  }

  private[this] val exceptionPrefixes = Seq("#", "/", "http://", "https://")
  def prefixRelativeUrls(html: String,
    urlPrefix: String, imageUrlPrefix: String): String = {

    val cleaner = new HtmlCleaner()
    val node = cleaner.clean(html)
    node.traverse(new TagNodeVisitor() {
      override def visit(tagNode: TagNode, htmlNode: HtmlNode): Boolean = {
        htmlNode match {
          case tag: TagNode if tag.getName == "a" =>
            Option(tag.getAttributeByName("href")) foreach { href =>
              if (exceptionPrefixes.forall(p => !href.startsWith(p))) {
                tag.addAttribute("href", s"${urlPrefix}${href}")
              }
            }
          case tag: TagNode if tag.getName == "img" =>
            Option(tag.getAttributeByName("src")) foreach { src =>
              if (exceptionPrefixes.forall(p => !src.startsWith(p))) {
                tag.addAttribute("src", s"${imageUrlPrefix}${src}")
              }
            }
          case _ =>
        }
        // continue traversal
        true
      }
    })
    new SimpleHtmlSerializer(cleaner.getProperties()).getAsString(node)
  }
}
