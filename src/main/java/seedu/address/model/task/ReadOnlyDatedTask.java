package seedu.address.model.task;

/**
 * A read-only immutable interface for a DatedTask in the taskbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyDatedTask extends ReadOnlyTask {
    
    DateTime getDateTime();
    DateTime getDateTimeEnd();
    Length getLength();
    Recurrance getRecurrance();
    
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    @Override
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.isDated()
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPriority().equals(this.getPriority())
                && other.getInformation().equals(this.getInformation())
                && other.getDoneFlag().equals(this.getDoneFlag())
                && ((ReadOnlyDatedTask) other).getDateTime().equals(this.getDateTime())
                && ((ReadOnlyDatedTask) other).getLength().equals(this.getLength())
                && ((ReadOnlyDatedTask) other).getRecurrance().equals(this.getRecurrance()));
    }
    
    /**
     * Formats the task as text, showing all contact details.
     */
    @Override
    default String getAsText(){
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Time: ")
                .append(getDateTime())
                .append(" Length: ")
                .append(getLength())
                .append(" Recurrance: ")
                .append(getRecurrance())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Information: ")
                .append(getInformation())
                .append(" Done: ")
                .append(getDoneFlag())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
    
    @Override
    default boolean isDated(){
        return true;
    }
    boolean hasValidLength();
}
