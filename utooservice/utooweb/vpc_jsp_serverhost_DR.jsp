<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List,
                 java.util.ArrayList,
                 java.util.Collections,
                 java.util.Iterator,
                 java.util.Enumeration,
                 java.security.MessageDigest,
                 java.util.Map,
                 java.net.URLEncoder,
                 java.util.HashMap"%>
<%@ page import="org.strobs.utoo.web.services.controllers.cAdmin" %>
   
<%!    // This is an array for creating hex chars
static final String SECURE_SECRET = "";
String vpc_transcation_ID="";boolean is_updated=false;String result = "",vResponseCodeglob="";
    static final char[] HEX_TABLE = new char[] {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    cAdmin Ocadmin=new cAdmin();    
    String hashAllFields(Map fields) {
        
        // create a list and sort it
        List fieldNames = new ArrayList(fields.keySet());
        Collections.sort(fieldNames);
        
        // create a buffer for the md5 input and add the secure secret first
        StringBuffer buf = new StringBuffer();
        buf.append(SECURE_SECRET);
        
        // iterate through the list and add the remaining field values
        Iterator itr = fieldNames.iterator();
        
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                buf.append(fieldValue);
            }
        }
             
        MessageDigest md5 = null;
        byte[] ba = null;
        
        // create the md5 hash and UTF-8 encode it
        try {
            md5 = MessageDigest.getInstance("MD5");
            ba = md5.digest(buf.toString().getBytes("UTF-8"));
         } catch (Exception e) {} // wont happen
       
       return hex(ba);
    
    } // end hashAllFields()

//  ----------------------------------------------------------------------------

    /*
    * This method takes a byte array and returns a string of its contents
    *
    * @param input - byte array containing the input data
    * @return String containing the output String
    */
    static String hex(byte[] input) {
        // create a StringBuffer 2x the size of the hash array
        StringBuffer sb = new StringBuffer(input.length * 2);

        // retrieve the byte array data, convert it to hex
        // and add it to the StringBuffer
        for (int i = 0; i < input.length; i++) {
            sb.append(HEX_TABLE[(input[i] >> 4) & 0xf]);
            sb.append(HEX_TABLE[input[i] & 0xf]);
        }
        return sb.toString();
    }
    private static String null2unknown(String in) {
        if (in == null || in.length() == 0) {
            return "No Value Returned";
        } else {
            return in;
        }
    } // null2unknown()
     
    String getResponseDescription(String vResponseCode) {

        
        
        // check if a single digit response code
        /* if (vResponseCode.length() != 1) {
        
            // Java cannot switch on a string so turn everything to a char
             */
             char input = vResponseCode.charAt(0);
             vResponseCodeglob=vResponseCode;
             
            switch (input){
                case '0' : result = "Transaction Successful"; break;
                case '1' : result = "Unknown Error"; break;
                case '2' : result = "Bank Declined Transaction"; break;
                case '3' : result = "No Reply from Bank"; break;
                case '4' : result = "Expired Card"; break;
                case '5' : result = "Insufficient Funds"; break;
                case '6' : result = "Error Communicating with Bank"; break;
                case '7' : result = "Payment Server System Error"; break;
                case '8' : result = "Transaction Type Not Supported"; break;
                case '9' : result = "Bank declined transaction (Do not contact Bank)"; break;
                case 'A' : result = "Transaction Aborted"; break;
                case 'C' : result = "Transaction Cancelled"; break;
                case 'D' : result = "Deferred transaction has been received and is awaiting processing"; break;
                case 'F' : result = "3D Secure Authentication failed"; break;
                case 'I' : result = "Card Security Code verification failed"; break;
                case 'L' : result = "Shopping Transaction Locked (Please try the transaction again later)"; break;
                case 'N' : result = "Cardholder is not enrolled in Authentication Scheme"; break;
                case 'P' : result = "Transaction has been received by the Payment Adaptor and is being processed"; break;
                case 'R' : result = "Transaction was not processed - Reached limit of retry attempts allowed"; break;
                case 'S' : result = "Duplicate SessionID (OrderInfo)"; break;
                case 'T' : result = "Address Verification Failed"; break;
                case 'U' : result = "Card Security Code Failed"; break;
                case 'V' : result = "Address Verification and Card Security Code Failed"; break;
                case '?' : result = "Transaction status is unknown"; break;
                default  : result = "Unable to be determined";
             } 
           
            is_updated =Ocadmin.payment_Response(vResponseCode,result,vpc_transcation_ID);
            return result;
       /*  } else {
            return "No Value Returned";
        } */
    } // getResponseDescription()

    private String displayAVSResponse(String vAVSResultCode) {

        String result = "";
        if (vAVSResultCode != null || vAVSResultCode.length() == 0) {

            if (vAVSResultCode.equalsIgnoreCase("Unsupported") || vAVSResultCode.equalsIgnoreCase("No Value Returned")) {
                result = "AVS not supported or there was no AVS data provided";
            } else {
                // Java cannot switch on a string so turn everything to a char
                char input = vAVSResultCode.charAt(0);

                switch (input){
                    case 'X' : result = "Exact match - address and 9 digit ZIP/postal code"; break;
                    case 'Y' : result = "Exact match - address and 5 digit ZIP/postal code"; break;
                    case 'S' : result = "Service not supported or address not verified (international transaction)"; break;
                    case 'G' : result = "Issuer does not participate in AVS (international transaction)"; break;
                    case 'A' : result = "Address match only"; break;
                    case 'W' : result = "9 digit ZIP/postal code matched, Address not Matched"; break;
                    case 'Z' : result = "5 digit ZIP/postal code matched, Address not Matched"; break;
                    case 'R' : result = "Issuer system is unavailable"; break;
                    case 'U' : result = "Address unavailable or not verified"; break;
                    case 'E' : result = "Address and ZIP/postal code not provided"; break;
                    case 'N' : result = "Address and ZIP/postal code not matched"; break;
                    case '0' : result = "AVS not requested"; break;
                    default  : result = "Unable to be determined";
                }
            }
        } else {
            result = "null response";
        }
        return result;
    }

    private String displayCSCResponse(String vCSCResultCode) {

        String result = "";
        if (vCSCResultCode != null || vCSCResultCode.length() == 0) {

            if (vCSCResultCode.equalsIgnoreCase("Unsupported")  || vCSCResultCode.equalsIgnoreCase("No Value Returned")) {
                result = "CSC not supported or there was no CSC data provided";
            } else {
                // Java cannot switch on a string so turn everything to a char
                char input = vCSCResultCode.charAt(0);

                switch (input){
                    case 'M' : result = "Exact code match"; break;
                    case 'S' : result = "Merchant has indicated that CSC is not present on the card (MOTO situation)"; break;
                    case 'P' : result = "Code not processed"; break;
                    case 'U' : result = "Card issuer is not registered and/or certified"; break;
                    case 'N' : result = "Code invalid or not matched"; break;
                    default  : result = "Unable to be determined";
                }
            }

        } else {
            result = "null response";
        }
        return result;
    }

    private String getStatusDescription(String vStatus) {

        String result = "";
        if (vStatus != null && !vStatus.equals("")) {

            if (vStatus.equalsIgnoreCase("Unsupported")  || vStatus.equals("No Value Returned")) {
                result = "3DS not supported or there was no 3DS data provided";
            } else {
            
                // Java cannot switch on a string so turn everything to a character
                char input = vStatus.charAt(0);

                switch (input){
                    case 'Y'  : result = "The cardholder was successfully authenticated."; break;
                    case 'E'  : result = "The cardholder is not enrolled."; break;
                    case 'N'  : result = "The cardholder was not verified."; break;
                    case 'U'  : result = "The cardholder's Issuer was unable to authenticate due to some system error at the Issuer."; break;
                    case 'F'  : result = "There was an error in the format of the request from the merchant."; break;
                    case 'A'  : result = "Authentication of your Merchant ID and Password to the ACS Directory Failed."; break;
                    case 'D'  : result = "Error communicating with the Directory Server."; break;
                    case 'C'  : result = "The card type is not supported for authentication."; break;
                    case 'S'  : result = "The signature on the response received from the Issuer could not be validated."; break;
                    case 'P'  : result = "Error parsing input from Issuer."; break;
                    case 'I'  : result = "Internal Payment Server system error."; break;
                    default   : result = "Unable to be determined"; break;
                }
            }
        } else {
            result = "null response";
        }
        return result;
    }

//  ----------------------------------------------------------------------------
%>
<%

    // *******************************************
    // START OF MAIN PROGRAM
    // *******************************************

    // The Page does a display to a browser

    // retrieve all the incoming parameters into a hash map
    Map fields = new HashMap();
    for (Enumeration enuma = request.getParameterNames(); enuma.hasMoreElements();) {
        String fieldName = (String) enuma.nextElement();
        String fieldValue = request.getParameter(fieldName);
        if ((fieldValue != null) && (fieldValue.length() > 0)) {
            fields.put(fieldName, fieldValue);
        }
    }

    String vpc_Txn_Secure_Hash = null2unknown((String) fields.remove("vpc_SecureHash"));
    String hashValidated = null;

    // defines if error message should be output
    boolean errorExists = false;
    
    if (SECURE_SECRET != null && SECURE_SECRET.length() > 0 && 
        (fields.get("vpc_TxnResponseCode") != null || fields.get("vpc_TxnResponseCode") != "No Value Returned")) {
        
        // create secure hash and append it to the hash map if it was created
        // remember if SECURE_SECRET = "" it wil not be created
        String secureHash = hashAllFields(fields);
    
        // Validate the Secure Hash (remember MD5 hashes are not case sensitive)
        if (vpc_Txn_Secure_Hash.equalsIgnoreCase(secureHash)) {
            // Secure Hash validation succeeded, add a data field to be 
            // displayed later.
            hashValidated = "<font color='#00AA00'><strong>CORRECT</strong></font>";
        } else {
            // Secure Hash validation failed, add a data field to be
            // displayed later.
            errorExists = true;
            hashValidated = "<font color='#FF0066'><strong>INVALID HASH</strong></font>";
        }
    } else {
        // Secure Hash was not validated, 
        hashValidated = "<font color='orange'><strong>Not Calculated - No 'SECURE_SECRET' present.</strong></font>";
    }

    // Extract the available receipt fields from the VPC Response
    // If not present then let the value be equal to 'Unknown'
    // Standard Receipt Data
    String title           = null2unknown((String)fields.get("Title"));
    String againLink       = null2unknown((String)fields.get("AgainLink"));
    String amount          = null2unknown((String)fields.get("vpc_Amount"));
    String locale          = null2unknown((String)fields.get("vpc_Locale"));
    String batchNo         = null2unknown((String)fields.get("vpc_BatchNo"));
    String command         = null2unknown((String)fields.get("vpc_Command"));
    String message         = null2unknown((String)fields.get("vpc_Message"));
    String version         = null2unknown((String)fields.get("vpc_Version"));
    String cardType        = null2unknown((String)fields.get("vpc_Card"));
    String orderInfo       = null2unknown((String)fields.get("vpc_OrderInfo"));
    String receiptNo       = null2unknown((String)fields.get("vpc_ReceiptNo"));
    String merchantID      = null2unknown((String)fields.get("vpc_Merchant"));
    String merchTxnRef     = null2unknown((String)fields.get("vpc_MerchTxnRef"));
    String authorizeID     = null2unknown((String)fields.get("vpc_AuthorizeId"));
    String transactionNo   = null2unknown((String)fields.get("vpc_TransactionNo"));
    String acqResponseCode = null2unknown((String)fields.get("vpc_AcqResponseCode"));
    String txnResponseCode = null2unknown((String)fields.get("vpc_TxnResponseCode"));

    vpc_transcation_ID=null2unknown((String)fields.get("unique_ID"));
    // CSC Receipt Data
    String vCSCResultCode  = null2unknown((String)fields.get("vpc_CSCResultCode"));
    String vCSCRequestCode = null2unknown((String)fields.get("vpc_CSCRequestCode"));
    String vACQCSCRespCode = null2unknown((String)fields.get("vpc_AcqCSCRespCode"));
    
    // AVS Receipt Data
    String vAVS_City       = null2unknown((String)fields.get("vpc_AVS_City"));
    String vAVS_Country    = null2unknown((String)fields.get("vpc_AVS_Country"));
    String vAVS_Street01   = null2unknown((String)fields.get("vpc_AVS_Street01"));
    String vAVS_PostCode   = null2unknown((String)fields.get("vpc_AVS_PostCode"));
    String vAVS_StateProv  = null2unknown((String)fields.get("vpc_AVS_StateProv"));
    String vAVSResultCode  = null2unknown((String)fields.get("vpc_AVSResultCode"));
    String vAVSRequestCode = null2unknown((String)fields.get("vpc_AVSRequestCode"));
    String vACQAVSRespCode = null2unknown((String)fields.get("vpc_AcqAVSRespCode"));

    // 3-D Secure Data
    String transType3DS       = null2unknown((String)fields.get("vpc_VerType"));
    String verStatus3DS       = null2unknown((String)fields.get("vpc_VerStatus"));
    String token3DS           = null2unknown((String)fields.get("vpc_VerToken"));
    String secureLevel3DS  = null2unknown((String)fields.get("vpc_VerSecurityLevel"));
    String enrolled3DS       = null2unknown((String)fields.get("vpc_3DSenrolled"));
    String xid3DS           = null2unknown((String)fields.get("vpc_3DSXID"));
    String eci3DS           = null2unknown((String)fields.get("vpc_3DSECI"));
    String status3DS       = null2unknown((String)fields.get("vpc_3DSstatus"));
    
    String error = "";
    // Show this page as an error page if error condition
    if (txnResponseCode.equals("7") || txnResponseCode.equals("No Value Returned") || errorExists) {
        error = "Error ";
    }
        
    // FINISH TRANSACTION - Process the VPC Response Data
    // =====================================================
    // For the purposes of demonstration, we simply display the Result fields on a
    // web page.
%>  <!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>
    <html>
    <head><title><%=title%> - VPC Response <%=error%>Page</title>
        <meta http-equiv='Content-Type' content='text/html, charset=iso-8859-1'>
        <style type='text/css'>
            <!--
            h1       { font-family:Arial,sans-serif; font-size:20pt; font-weight:600; margin-bottom:0.1em; color:#08185A;}
            h2       { font-family:Arial,sans-serif; font-size:14pt; font-weight:100; margin-top:0.1em; color:#08185A;}
            h2.co    { font-family:Arial,sans-serif; font-size:24pt; font-weight:100; margin-top:0.1em; margin-bottom:0.1em; color:#08185A}
            h3       { font-family:Arial,sans-serif; font-size:16pt; font-weight:100; margin-top:0.1em; margin-bottom:0.1em; color:#08185A}
            h3.co    { font-family:Arial,sans-serif; font-size:16pt; font-weight:100; margin-top:0.1em; margin-bottom:0.1em; color:#FFFFFF}
            body     { font-family:Verdana,Arial,sans-serif; font-size:10pt; background-color:#FFFFFF; color:#08185A}
            th       { font-family:Verdana,Arial,sans-serif; font-size:8pt; font-weight:bold; background-color:#E1E1E1; padding-top:0.5em; padding-bottom:0.5em;  color:#08185A}
            tr       { height:25px; }
            .shade   { height:25px; background-color:#E1E1E1 }
            .title   { height:25px; background-color:#C1C1C1 }
            td       { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#08185A }
            td.red   { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#FF0066 }
            td.green { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#008800 }
            p        { font-family:Verdana,Arial,sans-serif; font-size:10pt; color:#FFFFFF }
            p.blue   { font-family:Verdana,Arial,sans-serif; font-size:7pt;  color:#08185A }
            p.red    { font-family:Verdana,Arial,sans-serif; font-size:7pt;  color:#FF0066 }
            p.green  { font-family:Verdana,Arial,sans-serif; font-size:7pt;  color:#008800 }
            div.bl   { font-family:Verdana,Arial,sans-serif; font-size:7pt;  color:#C1C1C1 }
            div.red  { font-family:Verdana,Arial,sans-serif; font-size:7pt;  color:#FF0066 }
            li       { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#FF0066 }
            input    { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#08185A; background-color:#E1E1E1; font-weight:bold }
            select   { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#08185A; background-color:#E1E1E1; font-weight:bold; }
            textarea { font-family:Verdana,Arial,sans-serif; font-size:8pt;  color:#08185A; background-color:#E1E1E1; font-weight:normal; scrollbar-arrow-color:#08185A; scrollbar-base-color:#E1E1E1 }
            -->
        </style>
    </head>
    <body>
    
	<!-- Start Branding Table -->
	<table width="100%" border="2" cellpadding="2" class="title">
		<tr>
			<td class="shade" width="90%"><h2 class="co">&nbsp;Virtual Payment Client Example</h2></td>
			<td class="title" align="center"><h3 class="co">MIGS</h3></td>
		</tr>
	</table>
	<!-- End Branding Table -->
    
    <center><h1><%=title%> <%=error%>Response Page</H1></center>
    
    <table width="85%" align='center' cellpadding='5' border='0'>
      
        <tr class='title'>
            <td colspan="2" height="25"><p><strong>&nbsp;Standard Transaction Fields</strong></p></td>
        </tr>
        <tr>
            <td align='right' width='50%'><strong><i>VPC API Version: </i></strong></td>
            <td width='50%'><%=version%></td>
        </tr>
        <tr class='shade'>                  
            <td align='right'><strong><i>Command: </i></strong></td>
            <td><%=command%></td>
        </tr>
        <tr>
            <td align='right'><strong><i>Merchant Transaction Reference: </i></strong></td>
            <td><%=merchTxnRef%></td>
        </tr>
        <tr class='shade'>
            <td align='right'><strong><i>Merchant ID: </i></strong></td>
            <td><%=merchantID%></td>
        </tr>
        <tr>                  
            <td align='right'><strong><i>Order Information: </i></strong></td>
            <td><%=orderInfo%></td>
        </tr>
        <tr class='shade'>
            <td align='right'><strong><i>Transaction Amount: </i></strong></td>
            <td><%=amount%></td>
        </tr>
        <tr>                  
            <td align='right'><strong><i>Locale: </i></strong></td>
            <td><%=locale%></td>
        </tr>
      
        <tr>
            <td colspan='2' align='center'><font color='#C1C1C1'>Fields above are the primary request values.<br/></font><hr/>
            </td>
        </tr>

        <tr class='shade'>                  
            <td align='right'><strong><i>VPC Transaction Response Code: </i></strong></td>
            <td><%=txnResponseCode%></td>
        </tr>
        <tr>
            <td align='right'><strong><i>Transaction Response Code Description: </i></strong></td>
            <td><%=getResponseDescription(txnResponseCode)%></td>
        </tr>
        <tr class='shade'>                  
            <td align='right'><strong><i>Message: </i></strong></td>
            <td><%=message%></td>
        </tr>
<%
// only display the following fields if not an error condition
if (!txnResponseCode.equals("7") && !txnResponseCode.equals("No Value Returned")) { 
%>
        <tr>
            <td align='right'><strong><i>Receipt Number: </i></strong></td>
            <td><%=receiptNo%></td>
        </tr>
        <tr class='shade'>                  
            <td align='right'><strong><i>Transaction Number: </i></strong></td>
            <td><%=transactionNo%></td>
        </tr>
        <tr>
            <td align='right'><strong><i>Acquirer Response Code: </i></strong></td>
            <td><%=acqResponseCode%></td>
        </tr>
        <tr class='shade'>                  
            <td align='right'><strong><i>Bank Authorization ID: </i></strong></td>
            <td><%=authorizeID%></td>
        </tr>
        <tr>
            <td align='right'><strong><i>Batch Number: </i></strong></td>
            <td><%=batchNo%></td>
        </tr>
        <tr class='shade'>                  
            <td align='right'><strong><i>Card Type: </i></strong></td>
            <td><%=cardType%></td>
        </tr>
      
        <tr>
            <td colspan='2' align='center'><font color='#C1C1C1'>Fields above are for a standard transaction.<br/><hr/>
                Fields below are additional fields for extra functionality.</font><br/></td>
        </tr>

        <tr class='title'>
            <td colspan="2" height="25"><p><strong>&nbsp;Card Security Code Fields</strong></p></td>
        </tr>
        <tr>                    
            <td align='right'><strong><i>CSC Request Code: </i></strong></td>
            <td><%=vCSCRequestCode%></td>
        </tr>
        <tr class='shade'>
            <td align='right'><strong><i>CSC Acquirer Response Code: </i></strong></td>
            <td><%=vACQCSCRespCode%></td>
        </tr>
        <tr>                    
            <td align='right'><strong><i>CSC QSI Result Code: </i></strong></td>
            <td><%=vCSCResultCode%></td>
        </tr>
        <tr class='shade'>
            <td align='right'><strong><i>CSC Result Description: </i></strong></td>
            <td><%=displayCSCResponse(vCSCResultCode)%></td>
        </tr>
      
        <tr><td colspan = '2'><hr/></td></tr>
      
        <tr class='title'>
            <td colspan="2" height="25"><p><strong>&nbsp;Address Verification Service Fields</strong></p></td>
        </tr>
        <tr>
            <td align='right'><strong><i>AVS Street/Postal Address: </i></strong></td>
            <td><%=vAVS_Street01%></td>
        </tr>
        <tr class='shade'>                    
            <td align='right'><strong><i>AVS City/Town/Suburb: </i></strong></td>
            <td><%=vAVS_City%></td>
        </tr>
        <tr>
            <td align='right'><strong><i>AVS State/Province: </i></strong></td>
            <td><%=vAVS_StateProv%></td>
        </tr>
        <tr class='shade'>                    
            <td align='right'><strong><i>AVS Postal/Zip Code: </i></strong></td>
            <td><%=vAVS_PostCode%></td>
        </tr>
        <tr>
            <td align='right'><strong><i>AVS Country Code: </i></strong></td>
            <td><%=vAVS_Country%></td>
        </tr>
        <tr class='shade'>                    
            <td align='right'><strong><i>AVS Request Code: </i></strong></td>
            <td><%=vAVSRequestCode%></td>
        </tr>
        <tr>
            <td align='right'><strong><i>AVS Acquirer Response Code: </i></strong></td>
            <td><%=vACQAVSRespCode%></td>
        </tr>
        <tr class='shade'>                    
            <td align='right'><strong><i>AVS QSI Result Code: </i></strong></td>
            <td><%=vAVSResultCode%></td>
        </tr>
        <tr>
            <td align='right'><strong><i>AVS Result Description: </i></strong></td>
            <td><%=displayAVSResponse(vAVSResultCode)%></td>
        </tr>

        <tr>
            <td colspan = '2'><hr/></td>
        </tr>
      
        <tr class='title'>
            <td colspan="2" height="25"><p><strong>&nbsp;3-D Secure Authentication Fields</strong></p></td>
        </tr>
        <tr>
            <td align='right'><i><strong>Authentication Version</strong><br/>(3DS - Visa or MasterCard, SPA - MasterCard Only): </i></td>
            <td class='red'><%=transType3DS%></td>
        </tr>
        <tr class='shade'>
            <td align='right'><strong><i>Authentication Status: </i></strong></td>
            <td class='red'><%=verStatus3DS%></td>
        </tr>
        <tr>
            <td align='right'><strong><i>Authentication Token: </i></strong></td>
            <td class='red'><%=token3DS%></td>
        </tr>
        <tr class='shade'>
            <td align='right'><strong><i>Authentication XID: </i></strong></td>
            <td class='red'><%=xid3DS%></td>
        </tr>
        <tr>
            <td align='right'><strong><i>Authentication ECI: </i></strong></td>
            <td class='red'><%=eci3DS%></td>
        </tr>
        <tr class='shade'>
            <td align='right'><strong><i>Authentication Enrolled: </i></strong></td>
            <td class='red'><%=enrolled3DS%></td>
        </tr>
        <tr>
            <td align='right'><strong><i>Authentication 3DS Status: </i></strong></td>
            <td class='red'><%=status3DS%></td>
        </tr>
        <tr class='shade'>
            <td align='right'><strong><i>Authentication Security Level: </i></strong></td>
            <td class='red'><%=secureLevel3DS%></td>
        </tr>
        <tr>    
            <td align='right'><strong><i>Payment Server 3DS Authentication Status Code: </i></strong></td>
            <td class='green'><%=verStatus3DS%></td>
        </tr>
        <tr class='shade'>    
            <td align='right'><i><strong>3DS Authentication Status Code Description: </strong></i></td>
            <td class='green'><%=getStatusDescription(verStatus3DS)%></td>
        </tr>
        <tr>
            <td colspan='2' align='center'><font color='#FF0066'><br/>The 3-D Secure values shown in red are those values that are important values to store in case of future transaction repudiation.</font></td>
        </tr>
        <tr>
            <td colspan='2' align='center'><font color='#00AA00'>The 3-D Secure values shown in green are for informartion only and are not required to be stored.</font></td>
        </tr>
        
        <tr>
            <td colspan = '2'><hr/></td>
        </tr>
      
        <tr class='title'>
            <td colspan="2" height="25"><p><strong>&nbsp;Hash Validation</strong></p></td>
        </tr>
        <tr>
            <td align="right"><strong><i>Hash Validated Correctly: </i></strong></td>
            <td><%=hashValidated%></td>
        </tr>

<%
}%></table><br/>
    
    <center><P><A HREF='<%=againLink%>'>New Transaction</A></P></center>
    <input type="text" id="responsecode" value="<%=vResponseCodeglob%>" style="display:none;"/>
    <input type="text" id="responsemessage" value="<%=result%>" style="display:none;"/>
    </body>
    </html>
<script>
var responsecode = "<%=vResponseCodeglob%>";
var message = "<%=result%>";
  alert(message); 
  console.log("Response Code: "+responsecode+"\nMessage: "+message); 
  
  function getpayment_response(){
	  
	 var response="{\"responsecode\" : \""+responsecode+"\",\"responsemessage\" : \""+message+"\"}";
	 
	  return response;
  }
</script>