// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package awsauthentication.actions;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.SimpleTimeZone;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.MendixRuntimeException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.thirdparty.org.json.JSONObject;
import com.mendix.webui.CustomJavaAction;
import awsauthentication.impl.MxLogger;
import awsauthentication.impl.Utils;
import awsauthentication.proxies.AssumeRoleRequest;

/**
 * This action is used to generate credentials for AWS call by assuming AWS role using IAM Roles Anywhere.
 * 
 * Input parameters:
 * >Region: AWS Region
 * >Role ARN: Arn of the AWS role to assume
 * >Profile ARN: Arn of the Profile created at IAM RolesAnywhere
 * >Trust Anchor ARN: Arn of the Trust Anchor created at IAM RolesAnywhere
 * >Client Certificate Identifier: Identifier mentioned (as Pin) in the Outgoing Certificates in Runtime tab of Mendix Cloud Environment.
 * >Duration: Duration for which the session token should be valid.
 * >Session Name: An identifier for the assumed role session.
 */
public class CreateAssumeRoleRequest extends CustomJavaAction<IMendixObject>
{
	private final awsauthentication.proxies.ENUM_Region Region;
	private final java.lang.String RoleARN;
	private final java.lang.String ProfileARN;
	private final java.lang.String TrustAnchorARN;
	private final java.lang.String ClientCertificateID;
	private final java.lang.Long Duration;
	private final java.lang.String SessionName;

	public CreateAssumeRoleRequest(
		IContext context,
		java.lang.String _region,
		java.lang.String _roleARN,
		java.lang.String _profileARN,
		java.lang.String _trustAnchorARN,
		java.lang.String _clientCertificateID,
		java.lang.Long _duration,
		java.lang.String _sessionName
	)
	{
		super(context);
		this.Region = _region == null ? null : awsauthentication.proxies.ENUM_Region.valueOf(_region);
		this.RoleARN = _roleARN;
		this.ProfileARN = _profileARN;
		this.TrustAnchorARN = _trustAnchorARN;
		this.ClientCertificateID = _clientCertificateID;
		this.Duration = _duration;
		this.SessionName = _sessionName;
	}

	@java.lang.Override
	public IMendixObject executeAction() throws Exception
	{
		// BEGIN USER CODE
		
		// Input validation
		validate();
			
		// Call Assume Role, if exiting token not present
		LOGGER.trace("Getting new temporary Credentials by Assuming Role");		
		
		String passPhrase = null;
		KeyStore store;		
		try {
			// Get Client Certificate from Environment variables
			JSONObject cert = Utils.getClientCertificateDetails(ClientCertificateID);
			if(nonNull(cert)) {
				LOGGER.debug("Certificate found from Enviroment variables using ClientCertificate Id :: "+ClientCertificateID);
				String pfxCert = cert.getString(PFX_KEY);
				passPhrase = cert.getString(PASSWORD_KEY);
				// Load certificate into stream
				try (InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(pfxCert.getBytes()))){
					// Only pfx format is supported.
			        store = KeyStore.getInstance(KEY_STORE_TYPE);	       
			        store.load(stream, passPhrase.toCharArray());					
				}				
			}else {
				// Running locally form studio-pro, certificate from configuration.
				LOGGER.debug("Certificate not found from Enviroment variables, searching in runtime configuration");
				
				int certIndex = getCertificateID();
				// validates if the certificateID is not less than 0. As the input is 1 based but the retrieve is 0 based, the certIndex is check but the ClientCertificateID is logged
				// Also, this check cannot be performed within the validation function, as the client certificate id can be any string in cloud environments
				// TODO check if this can be moved to getCertificateID
				if (certIndex < 0) {
					LOGGER.error("The client certifcate id must be a number of 1 or greater. Instead value was set to: " + ClientCertificateID);
					throw new IllegalArgumentException("The client certifcate id must be a number of 1 or greater. Instead value was set to: " + ClientCertificateID);
				}
				LOGGER.debug("Searching certificate at index :: "+ClientCertificateID);
				try(InputStream stream = Core.getConfiguration().getClientCertificates().get(certIndex)){
					passPhrase = Core.getConfiguration().getClientCertificatePasswords().get(certIndex);
					// Only pfx format is supported.
			        store = KeyStore.getInstance(KEY_STORE_TYPE);	       
			        store.load(stream, passPhrase.toCharArray());
				}
			}
		}
		catch(Exception e) {
			LOGGER.error("Failed reading and loading keystore");
			throw new MendixRuntimeException(e);
		}
		try {
		
	        String alias = store.aliases().nextElement();
	        
	        PrivateKey privateKey =  (PrivateKey)store.getKey(alias, passPhrase.toCharArray());
	        
	        // Get the encryption algorithm
	        String keyAlgorithm = privateKey.getAlgorithm();
	        String algorithmName = Utils.getAlgorithmName(keyAlgorithm);
	        String algorithmHeader = Utils.getAlgorithmHeaderString(keyAlgorithm);
	
	        // Load public certificate
	        X509Certificate public_certificate = (X509Certificate)store.getCertificate(alias);
	        String amz_x509 = new String(Base64.getEncoder().encode(public_certificate.getEncoded()));
	        
	        // Public certificate serial number in decimal
	        BigInteger serial_number_dec = public_certificate.getSerialNumber();
	
	        
	        // Create a date for headers and the credential string
	        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_FORMAT + TIME_FORMAT); 
	        dateTimeFormat.setTimeZone(new SimpleTimeZone(0, UTC_TIMEZONE));
	        SimpleDateFormat dateStampFormat = new SimpleDateFormat(DATE_FORMAT);
	        dateStampFormat.setTimeZone(new SimpleTimeZone(0, UTC_TIMEZONE));
	        Date currentDateTime = new Date();
	        String amzDate = dateTimeFormat.format(currentDateTime);
	        String dateStamp = dateStampFormat.format(currentDateTime);
	        
	        // Request parameters for CreateSession--passed in a JSON block.
	        JSONObject requestJson = createRequestParametersJson();
	        String requestParameters = requestJson.toString();
	        //String signed_headers = "content-type;host;x-amz-date;x-amz-x509";
	        String signedHeaders = String.format("%s;%s;%s;%s", 
	        		CONTENT_TYPE_HEADER, 
	        		HOST_HEADER, 
	        		X_AMZ_DATE_HEADER, 
	        		X_AMZ_X509_HEADER);
	        
	        String awsRegionString = Utils.convertAWSRegion(Region).toString();
	        
	        String host = String.format("%s.%s.%s", SERVICE, awsRegionString, URL_SUFFIX);
	        
	        String credentialScope = String.format("%s/%s/%s/aws4_request",
	                dateStamp,
	                awsRegionString,
	                SERVICE);
	        
	        // create the autherization header based on the steps found here: http://docs.aws.amazon.com/general/latest/gr/sigv4-create-canonical-request.html
	        String authorization_header = createAutherizationHeader(privateKey, algorithmName, algorithmHeader, amz_x509,
					serial_number_dec, amzDate, requestParameters, signedHeaders, host, credentialScope);
	
	        String endpoint = HTTPS + host;
	        // Create RestCallRequest object
	        AssumeRoleRequest assumeRoleRequest = new AssumeRoleRequest(getContext());
	        assumeRoleRequest.setUrl(endpoint + CANONICAL_URI);
	        assumeRoleRequest.setHeaderContentType(CONTENT_TYPE);
	        assumeRoleRequest.setHeaderHost(host);
	        assumeRoleRequest.setHeaderXAmzDate(amzDate);
	        assumeRoleRequest.setHeaderXAmzX509(amz_x509);
	        assumeRoleRequest.setHeaderAuthorization(authorization_header);
	        assumeRoleRequest.setRequestBody(requestParameters);
	        
	        return assumeRoleRequest.getMendixObject();
		}
		catch(Exception e) {
			LOGGER.error("Failed fetching client certificate from keystore");
			throw new MendixRuntimeException(e);
		}
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "CreateAssumeRoleRequest";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(CreateAssumeRoleRequest.class);
	
	private static final int ONE_TO_ZERO_INDEX_CORRECTION = 1; // used becauset certifications are stored in 0 based index, but the documentation calls for the location in a 1 based index
	private static final String PASSWORD_KEY = "password";
	private static final String PFX_KEY = "pfx";
	private static final String SERVICE = "rolesanywhere";
	private static final String URL_SUFFIX = "amazonaws.com";
	private static final String CONTENT_TYPE = "application/json";
	private static final String HTTPS = "https://";
	private static final String KEY_STORE_TYPE = "PKCS12";
	private static final String UTC_TIMEZONE = "UTC";
	private static final String HTTP_METHOD = "POST";
	private static final String CANONICAL_URI = "/sessions";
	private static final String CANONICAL_QUERY_STRING = "";
	private static final String CONTENT_TYPE_HEADER = "content-type";
	private static final String HOST_HEADER = "host";
	private static final String X_AMZ_DATE_HEADER = "x-amz-date";
	private static final String X_AMZ_X509_HEADER = "x-amz-x509";
	private static final String DATE_FORMAT = "yyyyMMdd";
	private static final String TIME_FORMAT = "'T'HHmmss'Z'"; // 'T' is a denotion to prevent ambiguity with the date format. Then the time format combined with zero offset indicator 'Z'
	// check if 'Z' is correct, this may need to be just Z
	
	private void validate() throws Exception {
		String validationError = "";
		if (isNull(Region))
			validationError = validationError + "[Region cannot be empty]";
		if (isNull(RoleARN) || RoleARN.isEmpty())
			validationError = validationError + "[RoleARN cannot be empty]";
		if (isNull(ProfileARN) || ProfileARN.isEmpty())
			validationError = validationError + "[ProfileARN cannot be empty]";
		if (isNull(TrustAnchorARN) || TrustAnchorARN.isEmpty()) 
			validationError = validationError + "[TrustAnchorARN cannot be empty]";
		if (isNull(ClientCertificateID) || ClientCertificateID.isEmpty())
			validationError = validationError + "[ClientCertificateID cannot be empty]";
		if(!validationError.isEmpty()) 
			throw new Exception(validationError);
	}
	
	private String createAutherizationHeader(PrivateKey privateKey, String algorithmName, String algorithmHeader,
			String amz_x509, BigInteger serial_number_dec, String amzDate, String requestParameters,
			String signedHeaders, String host, String credentialScope) {
		
		
		// The following steps follow the documentation found below
        // http://docs.aws.amazon.com/general/latest/gr/sigv4-create-canonical-request.html
        
        // ************* TASK 1: CREATE A CANONICAL REQUEST *************
		
		String canonicalRequest = createCanonicalRequest(amz_x509, amzDate, requestParameters, signedHeaders, host);

        // ************* TASK 2: CREATE THE STRING TO SIGN*************
        String hashedCanonicalRequest = toHex(hash(canonicalRequest));
        
        // ************* TASK 3: CREATE THE STRING TO SIGN*************
        String signature_hex = createStringToSign(privateKey, algorithmName, algorithmHeader, amzDate, credentialScope,
				hashedCanonicalRequest);
        
        // ************* TASK 4: ADD SIGNING INFORMATION TO THE REQUEST *************
        // Put the signature information in a header named Authorization.
        String authorization_header = String.format(algorithmHeader+" Credential=%s/%s, SignedHeaders=%s, Signature=%s",
                serial_number_dec,
                credentialScope,
                signedHeaders,
                signature_hex);
		return authorization_header;
	}
	
//	private AssumeRoleRequest createAssumeRoleRequest() {
//		AssumeRoleRequest assumeRoleRequest = new AssumeRoleRequest(getContext());
//        assumeRoleRequest.setProfileARN(ProfileARN);
//        assumeRoleRequest.setRoleARN(RoleARN);
//        assumeRoleRequest.setTrustAnchorARN(TrustAnchorARN);
//		return assumeRoleRequest;
//	}
	
	// sets the variables passed into the JA to the appropriate field in the JSON
	private JSONObject createRequestParametersJson() {
		JSONObject requestJson = new JSONObject();
        requestJson.put("durationSeconds", Duration);
        requestJson.put("profileArn", ProfileARN);
        requestJson.put("roleArn", RoleARN);
        requestJson.put("sessionName", SessionName);
        requestJson.put("trustAnchorArn", TrustAnchorARN);
		return requestJson;
	}
	
	private int getCertificateID() {
		try {
			int certIndex = Integer.valueOf(ClientCertificateID) - ONE_TO_ZERO_INDEX_CORRECTION;
			return certIndex;
		} catch (NumberFormatException e) {
			LOGGER.error("The client certifcate id must be a number of 1 or greater. Instead value was set to: " + ClientCertificateID);
			throw e;
		}
	}
	
	private String createCanonicalRequest(String amz_x509, String amzDate, String requestParameters,
			String signedHeaders, String host) {
		// ************* TASK 1: CREATE A CANONICAL REQUEST *************
        
        // Step 1 is to define the verb (GET, POST, etc.).
	    // String method = "POST";

        // Step 2: Create canonical URI--the part of the URI from domain to query string (use '/' if no path)
        //String canonical_uri = "/sessions";

        // Step 3: Create the canonical query string. In this example, request parameters are passed in the body of the request and the query string is blank.
        //String canonical_querystring = "";
        
        
        // Step 4: Create the canonical headers. Header names must be trimmed and lowercase, and sorted in code point order from low to high.
        // Note that there is a trailing \n.
        String canonicalHeaders = String.format("%s\n%s\n%s\n%s\n",
                CONTENT_TYPE_HEADER + ":" + CONTENT_TYPE,
                HOST_HEADER + ":" + host,
                X_AMZ_DATE_HEADER + ":" + amzDate,
                X_AMZ_X509_HEADER + ":" + amz_x509);

        // Step 5: Create the list of signed headers. This lists the headers in the canonical_headers list, delimited with ";" and in alpha order.
        // Note: The request can include any headers; canonical_headers and signed_headers include those that you want to be included in the hash of the request. "Host" and "x-amz-date" are always required.
        // For Roles Anywhere, content-type and x-amz-x509 are also required.
        

        // Step 6: Create payload hash. In this example, the payload (body of the request) contains the request parameters.
        LOGGER.debug("RolesAnywhere Call Request Body ["+requestParameters+"]");
        String hashedPayload = toHex(hash(requestParameters));

        // Step 7: Combine elements to create canonical request
        String canonical_request = String.format("%s\n%s\n%s\n%s\n%s\n%s",
                HTTP_METHOD,
                CANONICAL_URI,
                CANONICAL_QUERY_STRING,
                canonicalHeaders,
                signedHeaders,
                hashedPayload);
        
		return canonical_request;
	}
	
	private String createStringToSign(PrivateKey privateKey, String algorithmName, String algorithmHeader,
			String amzDate, String credentialScope, String hashedCanonical_request) {
        // ************* TASK 3: CREATE THE STRING TO SIGN*************
		// Match the algorithm to the hashing algorithm you use, SHA-256
        String string_to_sign = String.format("%s\n%s\n%s\n%s",
        		algorithmHeader,
                amzDate,
                credentialScope,
                hashedCanonical_request);

        byte[] signature = sign(string_to_sign, privateKey, algorithmName);
        String signature_hex = toHex(signature);
		return signature_hex;
	}
	
//	private AssumeRoleResponse consumeRolesAnywhere(AssumeRoleRequest assumeRoleRequest, SigV4Request sigV4Request) throws Exception {
//		try {
//			// Call Assume Role from a microflow to make use of a REST call
//			AssumeRoleResponse assumeRoleResponse = Microflows.consumeRolesAnywhere(getContext(), sigV4Request,
//					assumeRoleRequest);
//        	LOGGER.debug("Assume Role call successful");
//			return assumeRoleResponse;
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage());
//			throw e;
//		}
//	}
	
//	private SessionCredentials getMxSessionCredentials(AssumeRoleResponse assumeRoleResponse) throws CoreException {
//		// Get the CredentialDetails attached to the AssumeRoleResponse
//		CredentialDetails credDetails = getCredentialDetails(assumeRoleResponse);
//		
//		// Create a SessionToken object
//		
//		// Create the Credentials object with the retrieved values from CredentialDetails 
//		SessionCredentials newCreds = new SessionCredentials(getContext());
//
//		newCreds.setToken(credDetails.getSessionToken());
//		newCreds.setAccessKeyId(credDetails.getAccessKeyId());
//		newCreds.setSecretAccessKey(credDetails.getSecretAccessKey());
//		return newCreds;
//	}
	
	private static byte[] hash(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            return md.digest();
        } catch (Exception e) {
            throw new RuntimeException("Unable to compute hash while signing request: " + e.getMessage(), e);
        }
    }

    private static byte[] sign(String stringData, PrivateKey key, String algorithmName) {
        try {
            Signature sig = Signature.getInstance(algorithmName);
            sig.initSign(key);
            sig.update(stringData.getBytes());
            return sig.sign();
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            throw new RuntimeException("Unable to compute hash while signing request: " + e.getMessage(), e);
        }
    }

    private static String toHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte datum : data) {
            String hex = Integer.toHexString(datum);
            if (hex.length() == 1) {
                sb.append("0");
            } else if (hex.length() == 8) {
                hex = hex.substring(6);
            }
            sb.append(hex);
        }
        return sb.toString().toLowerCase();
    }
    
//	private CredentialDetails getCredentialDetails(AssumeRoleResponse assumeRoleResponse) throws CoreException {
//		CredentialSet credSet = assumeRoleResponse.getAssumeRoleResponse_CredentialSet();
//        
//        IMendixObject credSetProp =  Core.retrieveByPath(getContext(), 
//        		credSet.getMendixObject(), 
//        		CredentialSetProperties.MemberNames.CredentialSetProperties_CredentialSet.toString())
//        		.get(0);
//        
//        CredentialSetProperties credSetPropProxy = CredentialSetProperties.initialize(getContext(), credSetProp);
//        CredentialDetails credDetails = credSetPropProxy.getCredentialSetProperties_CredentialDetails();
//        return credDetails;
//	}
    
	// END EXTRA CODE
}