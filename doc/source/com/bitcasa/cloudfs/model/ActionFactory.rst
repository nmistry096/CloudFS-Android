.. java:import:: android.util Pair

.. java:import:: com.bitcasa.cloudfs HistoryActions

.. java:import:: java.lang.reflect Constructor

.. java:import:: java.lang.reflect InvocationTargetException

ActionFactory
=============

.. java:package:: com.bitcasa.cloudfs.model
   :noindex:

.. java:type:: public final class ActionFactory

   Factory that generates Action objects.

Methods
-------
getAction
^^^^^^^^^

.. java:method:: public static BaseAction getAction(BaseAction baseAction)
   :outertype: ActionFactory

   Gets the correct action object for the provided base action.

   :param baseAction: The base action.
   :return: Generated action.

