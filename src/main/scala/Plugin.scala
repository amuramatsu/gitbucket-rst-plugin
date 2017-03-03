import scala.util.Try

import org.slf4j.LoggerFactory

import gitbucket.core.plugin.PluginRegistry
import gitbucket.core.service.SystemSettingsService.SystemSettings
import io.github.gitbucket.solidbase.model.Version
import javax.servlet.ServletContext
import amuramatsu.gitbucket.rst.RstRenderer

class Plugin extends gitbucket.core.plugin.Plugin {

  private[this] val log = LoggerFactory.getLogger(classOf[Plugin])

  override val pluginId: String = "rst"
  override val pluginName: String = "ReSTructured text Plugin"
  override val description: String = "Provides ReSTructured text rendering for GitBucket."
  override val versions: List[Version] = List(new Version("0.2.0"))

  private[this] var renderer: Option[RstRenderer] = None

  override def initialize(registry: PluginRegistry, context: ServletContext, settings: SystemSettings): Unit = {

    log.info("About to initialize Rst")

    val test = Try { new RstRenderer() }
    log.info("Result: " + test)
    val rst = test.get

    log.info("Registering RstRenderer for various extensions")
    registry.addRenderer("rst", rst)

    renderer = Option(rst)
  }

  override def shutdown(registry: PluginRegistry, context: ServletContext, settings: SystemSettings): Unit = {
    renderer.map(r => r.shutdown())
  }

}
