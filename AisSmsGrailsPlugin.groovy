import grails.util.GrailsUtil;
import groovy.util.ConfigObject;

import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.springframework.util.ClassUtils;

class AisSmsGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.3 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def author = "Your name"
    def authorEmail = ""
    def title = "Plugin summary/headline"
    def description = '''\\
Brief description of the plugin.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/ais-sms"

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before 
    }

    def doWithSpring = {
        loadAisSmsConfig()
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

	private ConfigObject loadAisSmsConfig(){
		def config = ConfigurationHolder.config
		GroovyClassLoader classLoader = new GroovyClassLoader(getClass().classLoader)
		// merging default Quartz config into main application config
		config.merge(new ConfigSlurper(GrailsUtil.environment).parse(classLoader.loadClass('DefaultAisSmsConfig')))

		// merging user-defined Quartz config into main application config if provided
		try {
			config.merge(new ConfigSlurper(GrailsUtil.environment).parse(classLoader.loadClass('AisSmsConfig')))
		} catch (Exception ignored) {
			// ignore, just use the defaults
		}
		return config
	}

}
