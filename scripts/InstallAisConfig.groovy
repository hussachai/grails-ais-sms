
// Install the configuration
ant.copy(
        file: "${aisSmsPluginDir}/grails-app/conf/DefaultAisSmsConfig.groovy",
        tofile: "grails-app/conf/AisSmsConfig.groovy")