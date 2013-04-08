package com.siberhus.ais.sms

class FakeSmsGatewayController {

	static allowedMethods = [idnex: "POST"]

    def index = {
		String trxId = params['TRANSID']
		String command = params['CMD']
		String serviceNo = params['FROM']
		String msisdn = params['TO']
		String report = params['REPORT']
		String charge = params['CHARGE']
		String code = params['CODE']
		String contentType = params['CTYPE']
		String content = params['CONTENT']

		String status = Constants.RESULT_STATUS_OK
		String errorDetail
		String smid
		String xmlOutput = "<XML><STATUS>$status</STATUS><DETAIL>$errorDetail</DETAIL><SMID>$smid</SMID></XML>"
		
		render(text:xmlOutput, contentType:'text/xml')
	}
	
}
