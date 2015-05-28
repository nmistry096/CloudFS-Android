.. java:import:: android.os Parcel

.. java:import:: android.os Parcelable

.. java:import:: com.bitcasa.cloudfs.api RESTAdapter

.. java:import:: com.google.gson.annotations SerializedName

Plan
====

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class Plan implements Parcelable

   Model class for plan.

Fields
------
CREATOR
^^^^^^^

.. java:field:: public static final Parcelable.Creator<Plan> CREATOR
   :outertype: Plan

Constructors
------------
Plan
^^^^

.. java:constructor:: public Plan(String displayName, String id, Long limit)
   :outertype: Plan

   Initializes a new instance of the account plan.

   :param displayName: Display name of the account plan.
   :param id: Id of the account plan.
   :param limit: Account plan limit.

Plan
^^^^

.. java:constructor:: public Plan(Parcel in)
   :outertype: Plan

   Initializes the Account instance using a Parcel.

   :param in: The parcel object.

Methods
-------
describeContents
^^^^^^^^^^^^^^^^

.. java:method:: @Override public int describeContents()
   :outertype: Plan

   Describe the kinds of special objects contained in this Parcelable's marshalled representation

   :return: a bitmask indicating the set of special object types marshalled by the Parcelable

getDisplayName
^^^^^^^^^^^^^^

.. java:method:: public String getDisplayName()
   :outertype: Plan

   Gets the display name of the account plan.

   :return: The account plan display name.

getId
^^^^^

.. java:method:: public String getId()
   :outertype: Plan

   Gets the id of the account plan.

   :return: The account plan id.

getLimit
^^^^^^^^

.. java:method:: public Long getLimit()
   :outertype: Plan

   Gets the limit of the account plan.

   :return: The account plan limit.

setDisplayName
^^^^^^^^^^^^^^

.. java:method:: public void setDisplayName(String displayName)
   :outertype: Plan

   Sets the display name of the account plan.

   :param displayName: The display name to be set.

setId
^^^^^

.. java:method:: public void setId(String id)
   :outertype: Plan

   Sets the id of the account plan.

   :param id: The plan id to be set.

setLimit
^^^^^^^^

.. java:method:: public void setLimit(long limit)
   :outertype: Plan

   Sets the limit of the account plan.

   :param limit: The plan limit to be set.

writeToParcel
^^^^^^^^^^^^^

.. java:method:: @Override public void writeToParcel(Parcel out, int flags)
   :outertype: Plan

   Flatten this object in to a Parcel.

   :param out: The Parcel in which the object should be written.
   :param flags: Additional flags about how the object should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE

