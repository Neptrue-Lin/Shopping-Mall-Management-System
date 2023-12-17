package org.neptrueworks.ordermanagement.data.entitizing;

import java.util.Date;

public interface IDataAuditingEntitizable<TIdentifier extends Comparable<TIdentifier>>
        extends IDataEntitizable<TIdentifier>{
    void setCreatedAt(Date createdTime);
    Date getCreatedAt();
    void setCreatedBy(String createdAuthor);
    String getCreatedBy();

    void setLastModifiedAt(Date lastModifiedTime);
    Date getLastModifiedAt();
    void setLastModifiedBy(String lastModifiedAuthor);
    String getLastModifiedBy();
}
