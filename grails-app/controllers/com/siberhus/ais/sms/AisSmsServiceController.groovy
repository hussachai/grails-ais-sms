package com.siberhus.ais.sms

import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes as GA
import org.springframework.context.ApplicationContext

class AisSmsServiceController {

	static allowedMethods = [receiveSms: "POST", receiveSmsReport: "POST"]

	ApplicationContext applicationContext

	public AisSmsServiceController(){
		applicationContext = SCH.servletContext.getAttribute(GA.APPLICATION_CONTEXT)
	}

	def receiveSms = {
		String status
		String errorDetail
		try{
			log.debug("SMS Receive Params: "+params)
			InSms inSms = new InSms(params)
			//call service here
			def receiverService = CH.config.aisSms.receiverService
			def service = applicationContext.getBean(receiverService)
			if(service){
				if(service instanceof SmsReceiver){
					service.receiveSms(inSms)
				}else{
					throw new Exception("Service does not implement SmsReceiver interface")
				}
			}else{
				throw new Exception("Service '${receiverService}' not found")
			}
			status = Constants.RESULT_STATUS_OK
		}catch(Exception e){
			status = Constants.RESULT_STATUS_ERR
			errorDetail = e.getMessage()
		}
		String xmlOutput = "<XML><STATUS>$status</STATUS><DETAIL>$errorDetail</DETAIL></XML>"
		log.debug("XML Output >> "+xmlOutput)
		render(text:xmlOutput, contentType:'text/xml')
	}
	
	def receiveSmsReport = {
		String status
		String errorDetail
		try{
			log.debug("SMS Report Params: "+params)
			RptSms rptSms = new RptSms(params)
			//call service here
			def receiverService = CH.config.aisSms.receiverService
			def service = applicationContext.getBean(receiverService)
			if(service){
				if(service instanceof SmsReceiver){
					service.receiveSmsReport(rptSms)
				}else{
					throw new Exception("Service does not implement SmsReceiver interface")
				}
			}else{
				throw new Exception("Service '${receiverService}' not found")
			}
			status = Constants.RESULT_STATUS_OK
		}catch(Exception e){
			status = Constants.RESULT_STATUS_ERR
			errorDetail = e.getMessage()
		}
		String xmlOutput = "<XML><STATUS>$status</STATUS><DETAIL>$errorDetail</DETAIL></XML>"
		log.debug("XML Output >> "+xmlOutput)
		render(text:xmlOutput, contentType:'text/xml')
	}


	
}
