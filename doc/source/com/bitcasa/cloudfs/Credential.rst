Credential
==========

.. java:package:: com.bitcasa.cloudfs
   :noindex:

.. java:type:: public class Credential

   The Credential class provides accessibility to CloudFS Credential.

Constructors
------------
Credential
^^^^^^^^^^

.. java:constructor:: public Credential(String endpoint)
   :outertype: Credential

   Initializes an instance of the Credential.

   :param endpoint: The CloudFS API endpoint.

Methods
-------
getAccessToken
^^^^^^^^^^^^^^

.. java:method:: public String getAccessToken()
   :outertype: Credential

   Gets the CloudFS API access token.

   :return: The CloudFS API access token.

getEndPoint
^^^^^^^^^^^

.. java:method:: public String getEndPoint()
   :outertype: Credential

   Gets the CloudFS API endpoint.

   :return: The CloudFS API endpoint.

getTokenType
^^^^^^^^^^^^

.. java:method:: public String getTokenType()
   :outertype: Credential

   Gets the CloudFS API token type.

   :return: The CloudFS API token type.

setAccessToken
^^^^^^^^^^^^^^

.. java:method:: public void setAccessToken(String accessToken)
   :outertype: Credential

   Sets the CloudFS API access token.

   :param accessToken: The CloudFS API access token.

setEndPoint
^^^^^^^^^^^

.. java:method:: public void setEndPoint(String endpoint)
   :outertype: Credential

   Sets the CloudFS API endpoint.

   :param endpoint: The CloudFS API token type.

setTokenType
^^^^^^^^^^^^

.. java:method:: public void setTokenType(String tokenType)
   :outertype: Credential

   Sets the CloudFS API token type.

   :param tokenType: The CloudFS API token type.

