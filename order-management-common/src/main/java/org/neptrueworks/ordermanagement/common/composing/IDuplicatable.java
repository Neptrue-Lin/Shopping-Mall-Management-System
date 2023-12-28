package org.neptrueworks.ordermanagement.common.composing;

public interface IDuplicatable<TPrototype> extends Cloneable {
    TPrototype duplicate();

    /**
     * Determine whether the object has same identity as this.
     *
     * @param prototype The object needed for determining.
     * @return true if they are address-equal; otherwise false.
     */
    default boolean isIdentical(TPrototype prototype) {
        return prototype.equals(this);
    }

    /**
     * Determine whether the object has identical attributes and properties as this in various aspects.
     * <p>
     * Relying on space and time itself, however, the recognition of object's isotropy should not be affected by the audit
     * as long as it has no impact on identity.
     *
     * @param prototype The object needed for determining.
     * @return true if they are isotropic, otherwise false.
     */
    boolean isIsotropic(TPrototype prototype);
}
