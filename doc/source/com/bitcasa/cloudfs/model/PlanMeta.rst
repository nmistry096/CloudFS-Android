.. java:import:: com.google.gson.annotations SerializedName

PlanMeta
========

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public class PlanMeta

   Model class for plan.

Constructors
------------
PlanMeta
^^^^^^^^

.. java:constructor:: public PlanMeta(String displayName, String id, Long limit)
   :outertype: PlanMeta

   Initializes a new instance of the account plan model.

   :param displayName: Display name of the account plan.
   :param id: Id of the account plan.
   :param limit: Account plan limit.

Methods
-------
getDisplayName
^^^^^^^^^^^^^^

.. java:method:: public String getDisplayName()
   :outertype: PlanMeta

   Gets the display name of the account plan.

   :return: The account plan display name.

getId
^^^^^

.. java:method:: public String getId()
   :outertype: PlanMeta

   Gets the id of the account plan.

   :return: The account plan id.

getLimit
^^^^^^^^

.. java:method:: public Long getLimit()
   :outertype: PlanMeta

   Gets the limit of the account plan.

   :return: The account plan limit.

