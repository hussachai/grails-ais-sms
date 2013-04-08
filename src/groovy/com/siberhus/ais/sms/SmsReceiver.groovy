package com.siberhus.ais.sms

/**
 * Created by IntelliJ IDEA.
 * User: hussachai
 * Date: Aug 25, 2010
 * Time: 11:27:50 AM
 * To change this template use File | Settings | File Templates.
 */
interface SmsReceiver {

	public void receiveSms(InSms inSms)

	public void receiveSmsReport(RptSms rptSms)
}
