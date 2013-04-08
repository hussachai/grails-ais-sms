package com.siberhus.ais.sms

import java.util.regex.Pattern
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.apache.commons.lang.StringUtils
import com.siberhus.sms.commons.EnglishThaiUnicodeCodec

/**
 * Created by IntelliJ IDEA.
 * User: hussachai
 * Date: Aug 24, 2010
 * Time: 12:28:55 AM
 * To change this template use File | Settings | File Templates.
 */
class OutSms {

	//English alphabet, number, whitespace and special characters !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
	//private static final Pattern TEXT_PATTERN = Pattern.compile('[a-zA-Z0-9\\s\\p{Punct}]*')
	private static final Pattern MSISDN_PATTERN = Pattern.compile('66[^0][0-9]{7}')

	/* CMD */
	public static final String COMMAND = 'SENDMSG'

	/* TRANSID */
	String trxId  //Transaction Id (00037652056697184 (or PUSH or BULK))
	/* TO */
	String msisdn //To MSISDN (6618353334)
	/* FROM */
//	String contentProvider //From Application or Number (9009000) //Use CH.config.aisSms.contentProvider instead
	/* REPORT */
	boolean report = true//Requires report? (Y|N)
	/* CHARGE */
	boolean chargeCustomer = true //Charge fee from customer (Y|N)
	/* CODE */
	String code //Operation name
	/* CTYPE */
	String contentType //Content Type (TEXT, UNICODE, NOKIA_BINARY)
	/* CONTENT */
	String content

	String toString(){
		return "OutSMS(trxId:${trxId},msisdn:${msisdn},report:${report},charge:${chargeCustomer},"+
			"code:${code},contentType:${contentType},content:${content})"
	}

	void setMsisdn(String msisdn){
		if(StringUtils.isBlank(msisdn)){
			throw new IllegalArgumentException("MSISDN cannot be blank")
		}
		def reformat = CH.config.aisSms.msisdn.reformat
		if(reformat){
			if(msisdn.startsWith('08')){
				msisdn = '66'+msisdn.substring(2)
			}
		}
		def validate = CH.config.aisSms.msisdn.validate
		if(validate){
			if( !MSISDN_PATTERN.matcher(msisdn).matches() ){
				throw new IllegalArgumentException("Invalid MSISDN format")
			}
		}
		this.msisdn = msisdn
	}
	
	void setContentType(String contentType){
		if( !(contentType in ['TEXT','UNICODE','NOKIA_BINARY']) ){
			throw new IllegalArgumentException("Unknown contentType: $contentType")
		}
		this.contentType = contentType
	}

	void setContent(String content){
		if(!contentType){
			if(EnglishThaiUnicodeCodec.containsThaiChar(content)){
				this.contentType = 'UNICODE'
				content = EnglishThaiUnicodeCodec.encode(content)
			}else{
				this.contentType = 'TEXT'
			}
		}
		this.content = content
	}
}
