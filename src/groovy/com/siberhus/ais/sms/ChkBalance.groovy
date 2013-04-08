package com.siberhus.ais.sms

/**
 * Created by IntelliJ IDEA.
 * User: hussachai
 * Date: Aug 25, 2010
 * Time: 10:50:58 AM
 * To change this template use File | Settings | File Templates.
 */
class ChkBalance {

	//CMD
	public static final COMMAND = 'CHKBALANCE'
	//TRANSID
	String trxId //Transaction ID
	//TO
	String msisdn //Customer mobile number
	//CODE
	String code //Service name
	//CONTENT
	BigDecimal price = 0 //Service price

	String toString(){
		return "ChkBalance(trxId:${trxId},msisdn:${msisdn},code:${code},price:${price})"
	}
	
}
