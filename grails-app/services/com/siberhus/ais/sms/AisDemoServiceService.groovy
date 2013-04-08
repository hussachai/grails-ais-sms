package com.siberhus.ais.sms

class AisDemoServiceService implements SmsReceiver{

	static transactional = false

	void receiveSms(InSms inSms) {
		log.info("AIS Demo Service: Receive ->\n"+inSms)
	}

	void receiveSmsReport(RptSms rptSms) {
		log.info("AIS Demo Service: Receive Report->\n"+rptSms)
	}


}
