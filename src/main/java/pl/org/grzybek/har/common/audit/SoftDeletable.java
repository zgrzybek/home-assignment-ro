package pl.org.grzybek.har.common.audit;

/**
 * Represents object that can be soft deleted
 */
public interface SoftDeletable {

    void setDeleted(boolean deleted);

    boolean isDeleted();
}
