package com.md.s3mol.Interfaces;

public interface State {
    /**
     *
     * @return
     */
    String getCurrentScreenName ();

    /**
     * To get xxx
     * @return
     */
    String getParentScreenId  ();

    /**
     * Activity state class implement this function and set the payload data.
     * @return To get the payload data from the respective state (activity).
     */
    String getPayLoad  ();

    /**
     * Get Sync Data Id for the session.
     * @return
     */
    String getSyncDataId();

}
