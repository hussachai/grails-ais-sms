package com.siberhus.ais.sms

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.protocol.HttpContext
import org.apache.http.protocol.BasicHttpContext
import org.apache.http.HttpResponse
import org.apache.http.StatusLine
import org.springframework.beans.factory.InitializingBean

class AisSmsService implements InitializingBean{

	static transactional = false

	private def gatewayUrl
	private def contentProvider
	private def gatewayEncoding
	
	void afterPropertiesSet() {
		gatewayUrl = CH.config.aisSms.smsGateway.url
		if(!gatewayUrl){
			def protocol = CH.config.aisSms.smsGateway.protocol
			def serverName = CH.config.aisSms.smsGateway.serverName
			def port = CH.config.aisSms.smsGateway.port
			def uri = CH.config.aisSms.smsGateway.uri
			gatewayUrl = "${protocol}://${serverName}:${port}${uri}"
		}
		contentProvider = CH.config.aisSms.contentProvider
		gatewayEncoding = CH.config.aisSms.smsGateway.encoding?:'UTF-8'
		log.info("AIS Gateway URL -> $gatewayUrl")
	}
	
	def sendSms(OutSms outSms) {

		log.debug(outSms)
		
		def formParams = []
		formParams << new BasicNameValuePair('TRANSID', outSms.trxId) //Transaction Id (00037652056697184 (or PUSH or BULK))
		formParams << new BasicNameValuePair('CMD', OutSms.COMMAND) //Command name (SENDMSG)
		formParams << new BasicNameValuePair('FROM', contentProvider) //Application name or number (9009000)
		formParams << new BasicNameValuePair('TO', outSms.msisdn) //MSISDN (6618353334)
		formParams << new BasicNameValuePair('REPORT', outSms.report?'Y':'N') //Need report? (Y|N)
		formParams << new BasicNameValuePair('CHARGE', outSms.chargeCustomer?'Y':'N') //Charge fee from customer (Y|N)
		formParams << new BasicNameValuePair('CODE', outSms.code) //Operation name
		formParams << new BasicNameValuePair('CTYPE', outSms.contentType) //Content Type
		formParams << new BasicNameValuePair('CONTENT', outSms.content) //Message
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, gatewayEncoding)
		
		HttpPost httpPost = new HttpPost(gatewayUrl)
		httpPost.setEntity(entity)

		DefaultHttpClient httpClient = new DefaultHttpClient()
		HttpContext localContext = new BasicHttpContext()
		HttpResponse response = httpClient.execute(httpPost, localContext)
		StatusLine statusLine = response.getStatusLine()
		statusLine.getStatusCode()
		statusLine.getReasonPhrase()
		InputStream content = response.getEntity().getContent()
		def result = new XmlSlurper().parse(content)
		OutSmsResponse smsResp = new OutSmsResponse()
		smsResp.status = result.STATUS
		smsResp.detail = result.DETAIL
		smsResp.smid = result.SMID
		log.debug("Send SMS Response (STATUS:${smsResp.status},DETAIL:${smsResp.detail},SMID:${smsResp.smid}")
		return smsResp
	}

	/**
	 * Check Customer’s Credit
	 * @param trxId
	 * @param serviceName
	 * @return
	 */
	boolean hasEnoughBalance(ChkBalance chkBal){

		log.debug(chkBal)
		
		def formParams = []
		formParams << new BasicNameValuePair('TRANSID',chkBal.trxId) //Transaction Id (00037652056697184 (or PUSH or BULK))
		formParams << new BasicNameValuePair('CMD', ChkBalance.COMMAND)
		formParams << new BasicNameValuePair('FROM', contentProvider) //ContentProvider
		formParams << new BasicNameValuePair('TO', chkBal.msisdn) //MSISDN
		formParams << new BasicNameValuePair('CODE', chkBal.code) //Service Name (RINGTONE)
		formParams << new BasicNameValuePair('CONTENT', String.valueOf(chkBal.price)) //Service Price

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, gatewayEncoding)
		HttpPost httpPost = new HttpPost(gatewayUrl)
		httpPost.setEntity(entity)

		DefaultHttpClient httpClient = new DefaultHttpClient()
		HttpContext localContext = new BasicHttpContext()
		HttpResponse response = httpClient.execute(httpPost, localContext)
		StatusLine statusLine = response.getStatusLine()
		statusLine.getStatusCode()
		statusLine.getReasonPhrase()
		InputStream content = response.getEntity().getContent()
		def result = new XmlSlurper().parse(content)
		if(result.STATUS==Constants.RESULT_STATUS_OK){
			return true
		}
		log.debug("Check balance error detail for MSISDN ${chkBal.msisdn} is: ${result.DETAIL}")
		return false
	}

	public static void main(String[] args){
		OutSms outSms = new OutSms()
		outSms.trxId = '00037652056697184'
		outSms.msisdn = '6618353334'
		outSms.report = true
		outSms.chargeCustomer = true
		outSms.contentType = 'TEXT'
		outSms.content = 'Hello'
		new AisSmsService().sendSms(outSms)
	}
	
}
