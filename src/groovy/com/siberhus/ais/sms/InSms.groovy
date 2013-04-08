package com.siberhus.ais.sms

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import java.net.URLDecoder
import com.siberhus.sms.commons.EnglishThaiUnicodeCodec

/**
 *
 *
 * We don't need to validate attribute in this class because all attributes is taken from AIS service
 * 
 * Created by IntelliJ IDEA.
 * User: hussachai
 * Date: Aug 24, 2010
 * Time: 12:20:29 AM
 * To change this template use File | Settings | File Templates.
 */
class InSms {
	/* CMD */
	static final String COMMAND = 'DLVRMSG'
	
	InSms(def params){
		trxId = params['TRANSID']
		if(params['CMD']!=COMMAND){
			//Make sure that AIS set the right parameter and conform to the spec version 2.0.x
			throw new IllegalArgumentException("Invalid command: ${params['CMD']}, expect "+COMMAND)
		}
		fet = params['FET']
		msisdnType = params['NTYPE']
		msisdn = params['FROM']
		contentProvider = params['TO']
		code = params['CODE']
		contentType = params['CTYPE']
		content = params['CONTENT']
		def urlDecode = CH.config.aisSms.content.urlDecode
		if(urlDecode){
			def encoding = CH.config.aisSms.content.encoding
			content = URLDecoder.decode(content, encoding)
		}
		if(contentType=='UNICODE'){
			content = EnglishThaiUnicodeCodec.decode(content)
		}

	}

	/* TRANSID */
	String trxId  //Transaction Id  (00037652056697184)
	/* FET */
	String fet //Front End Type (SMS/IVR)
	/* NTYPE */
	String msisdnType //MSISDN Type(ONE2CALL)
	/* FROM */
	String msisdn //From MSISDN (6618353334)
	/* TO */
   	String contentProvider //Content  provider
	/* CODE */
	String code //Operation name (REGISTER)
	/* CTYPE */
	String contentType //Content Type (TEXT, UNICODE, NOKIA_BINARY)
	/* CONTENT */
	String content

	String toString(){
		return "InSms(trxId:${trxId},fet:${fet},msisdnType:${msisdnType},contentProvider:${contentProvider},"+
			"code:${code},contentType:${contentType},content:${content}"
	}
}
