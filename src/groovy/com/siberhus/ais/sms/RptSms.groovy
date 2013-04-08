package com.siberhus.ais.sms

/**
 *
 * We don't need to validate attribute in this class because all attributes is taken from AIS service
 * 
 * Created by IntelliJ IDEA.
 * User: hussachai
 * Date: Aug 24, 2010
 * Time: 12:35:02 AM
 * To change this template use File | Settings | File Templates.
 */
class RptSms {
	/* CMD */
	static final String COMMAND = 'DLVRREP'

	public RptSms(def params){
		if(params['CMD']!=COMMAND){
			//Make sure that AIS set the right parameter and conform to the spec version 2.0.x
			throw new IllegalArgumentException("Invalid command: ${params['CMD']}, expect "+COMMAND)
		}
		msisdnType = params['NTYPE']
		msisdn = params['FROM']
		smid = params['SMID']
		status = params['STATUS']
		error = params['DETAIL']
	}
	/* NTYPE */
	String msisdnType //MSISDN Type(ONE2CALL)
	/* FROM */
	String msisdn //From MSISDN (6618353334)
	/* SMID */
	String smid // Short Message ID (This value matches SMID value in response XML of DLVRMSG) 
	/* STATUS */
	String status //(OK|ERR)
	/* DETAIL */
	String error //Error detail

	String toString(){
		return "RptSms(msisdnType:${msisdnType},msisdn:${msisdn},smid:${smid},status:${status},error:${error})"
	}

}
