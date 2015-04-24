Holds
=====

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class Holds

   Model class for account holds.

Constructors
------------
Holds
^^^^^

.. java:constructor:: public Holds(Boolean deleted, Boolean otl, Boolean suspended)
   :outertype: Holds

   Initializes a new instance of account holds.

   :param deleted: States whether the account is deleted.
   :param otl: States whether the account is over the limit.
   :param suspended: States whether the account is suspended.

Methods
-------
getDeleted
^^^^^^^^^^

.. java:method:: public final Boolean getDeleted()
   :outertype: Holds

   Gets whether the account is deleted.

   :return: Boolean stating whether the account is deleted.

getOtl
^^^^^^

.. java:method:: public final Boolean getOtl()
   :outertype: Holds

   Gets whether the account is over the limit.

   :return: Boolean stating whether the account is over the limit.

getSuspended
^^^^^^^^^^^^

.. java:method:: public final Boolean getSuspended()
   :outertype: Holds

   Gets whether the account is suspended.

   :return: Boolean stating whether the account is suspended.

setDeleted
^^^^^^^^^^

.. java:method:: public final void setDeleted(Boolean deleted)
   :outertype: Holds

   Sets whether the account is deleted.

   :param deleted: Boolean stating whether the account is deleted.

setOtl
^^^^^^

.. java:method:: public final void setOtl(Boolean otl)
   :outertype: Holds

   Sets whether the account is over the limit.

   :param otl: Boolean stating whether the account is over the limit.

setSuspended
^^^^^^^^^^^^

.. java:method:: public final void setSuspended(Boolean suspended)
   :outertype: Holds

   Sets whether the account is suspended.

   :param suspended: Boolean stating whether the account is suspended.

