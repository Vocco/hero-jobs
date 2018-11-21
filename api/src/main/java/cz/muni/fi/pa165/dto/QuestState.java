package cz.muni.fi.pa165.dto;

public enum QuestState {
    /**
     * Quest has been created and is available for heroes
     */
    NEW,
    /**
     * Quest has already started and/or it is not possible to add more heroes (i.e. heroesLimit has been reached)
     */
    ONGOING,
    /**
     * Quest has been completed successfully
     */
    SUCCESSFUL,
    /**
     * Heroes have failed to complete the quest (i.e. all of them died, dropped the quest, etc.)
     */
    FAILED
}
