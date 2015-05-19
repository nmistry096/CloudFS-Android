.. java:import:: android.net Uri

.. java:import:: android.util Base64

.. java:import:: android.util Log

.. java:import:: com.bitcasa.cloudfs BitcasaError

.. java:import:: com.bitcasa.cloudfs Credential

.. java:import:: com.bitcasa.cloudfs Session

.. java:import:: com.bitcasa.cloudfs Utils.BitcasaRESTConstants

.. java:import:: com.bitcasa.cloudfs.model BitcasaResponse

.. java:import:: com.bitcasa.cloudfs.model ResponseError

.. java:import:: com.google.gson Gson

.. java:import:: com.google.gson JsonElement

.. java:import:: java.io BufferedReader

.. java:import:: java.io IOException

.. java:import:: java.io InputStream

.. java:import:: java.io InputStreamReader

.. java:import:: java.io UnsupportedEncodingException

.. java:import:: java.net HttpURLConnection

.. java:import:: java.security InvalidKeyException

.. java:import:: java.security Key

.. java:import:: java.security NoSuchAlgorithmException

.. java:import:: java.util Map

.. java:import:: java.util Set

.. java:import:: javax.crypto Mac

.. java:import:: javax.crypto.spec SecretKeySpec

.. java:import:: javax.net.ssl HttpsURLConnection

BitcasaRESTUtility
==================

.. java:package:: com.bitcasa.cloudfs.api
   :noindex:

.. java:type:: public class BitcasaRESTUtility

   The BitcasaRESTUtility class provides utility methods to communicate to the CloudFS Rest Api.

Fields
------
TAG
^^^

.. java:field:: public static final String TAG
   :outertype: BitcasaRESTUtility

   Class name constant used for logging.

Methods
-------
checkRequestResponse
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public BitcasaError checkRequestResponse(HttpsURLConnection connection) throws IOException
   :outertype: BitcasaRESTUtility

   Creates a BitcasaError if server responds with an error code.

   :param connection: The HttpsURLConnection which contains the status code and error.
   :throws IOException: Occurs if the server response can not be processed.

generateAdminAuthorizationValue
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public String generateAdminAuthorizationValue(Session session, String uri, String params, String date) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException
   :outertype: BitcasaRESTUtility

   Generate admin authorization value

   :param session: Session object
   :param params: String of parameters
   :param date: String of date
   :throws UnsupportedEncodingException: If encoding not supported
   :throws InvalidKeyException: If the key provided is invalid
   :throws NoSuchAlgorithmException: If the algorithm does not exist
   :return: String of the authorization value

generateAuthorizationValue
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public String generateAuthorizationValue(Session session, String uri, String params, String date) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException
   :outertype: BitcasaRESTUtility

   Generates the authorization value

   :param session: The current session object.
   :param params: The uri value.
   :param date: The date and time.
   :throws UnsupportedEncodingException: If encoding not supported.
   :throws InvalidKeyException: If the key provided is invalid.
   :throws NoSuchAlgorithmException: If the algorithm does not exist.
   :return: String of the authorization value

generateParamsString
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public String generateParamsString(Map<String, ?> params)
   :outertype: BitcasaRESTUtility

   Generate parameter string from a map, encoding need to be done before calling generateParamsString.

   :param params: Parameter Strings.
   :return: The generated parameters.

getRequestUrl
^^^^^^^^^^^^^

.. java:method:: public String getRequestUrl(Credential credential, String request, String method, Map<String, String> queryParams)
   :outertype: BitcasaRESTUtility

   Get the Request URL

   :param credential: Application Credentials.
   :param request: The request information.
   :param method: The request method.
   :param queryParams: Query parameters.
   :return: The requested url.

getResponseFromInputStream
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public String getResponseFromInputStream(InputStream inputStream) throws IOException
   :outertype: BitcasaRESTUtility

   Gets the JSON response from a given input stream.

   :param inputStream: The input stream to be read.
   :throws IOException: If a network error occurs.
   :return: The processed JSON string.

replaceSpaceWithPlus
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public String replaceSpaceWithPlus(String s)
   :outertype: BitcasaRESTUtility

setRequestHeaders
^^^^^^^^^^^^^^^^^

.. java:method:: public void setRequestHeaders(Credential credential, HttpURLConnection connection, Map<String, String> headers)
   :outertype: BitcasaRESTUtility

   Sets the request Headers

   :param credential: Application Credentials.
   :param connection: HttpURLConnection object.
   :param headers: Map of headers.

sha1
^^^^

.. java:method:: public String sha1(String s, String keyString) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException
   :outertype: BitcasaRESTUtility

   Creates a sha1 encoding.

   :param s: The encoding value.
   :param keyString: The encoding key.
   :throws UnsupportedEncodingException: If encoding not supported.
   :throws NoSuchAlgorithmException: If the algorithm does not exist.
   :throws InvalidKeyException: If the key provided is invalid.
   :return: The encoded value.

