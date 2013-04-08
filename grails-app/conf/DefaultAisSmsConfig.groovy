
/**
 * Created by IntelliJ IDEA.
 * User: hussachai
 * Date: Aug 24, 2010
 * Time: 1:43:35 PM
 * To change this template use File | Settings | File Templates.
 */
aisSms {

	contentProvider = 'ChangeMe'

	receiverService = 'aisDemoService'
	

	msisdn{
		reformat = true
		validate = true 
	}
	
	content{
		urlDecode = true
		encoding = 'UTF-8'
	}
	smsGateway {
		//url = 'http://localhost:8080/ais-sms/aisSmsService/test'
		protocol = 'http' //http,https
		serverName = 'localhost'
		port = 8080
		uri = '/ais-sms/aisSmsService/test'
		encoding = 'UTF-8'
	}
}