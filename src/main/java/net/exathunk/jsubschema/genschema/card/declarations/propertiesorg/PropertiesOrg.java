package net.exathunk.jsubschema.genschema.card.declarations.propertiesorg;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class PropertiesOrg implements Cloneable, Serializable, PropertiesOrgLike {

    private String organizationName;

    private String organizationUnit;

    @Override
    public boolean hasOrganizationName() {
        return null != organizationName;
    }

    @Override
    public String getOrganizationName() {
        return organizationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @Override
    public boolean hasOrganizationUnit() {
        return null != organizationUnit;
    }

    @Override
    public String getOrganizationUnit() {
        return organizationUnit;
    }

    @Override
    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PropertiesOrg{ ");
        if (organizationName != null) sb.append("organizationName='").append(organizationName).append("', ");
        if (organizationUnit != null) sb.append("organizationUnit='").append(organizationUnit).append("', ");
        return sb.append("}").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof PropertiesOrgLike) {
            PropertiesOrgLike other = (PropertiesOrgLike) o;
            if (organizationName == null) { if (other.hasOrganizationName()) { return false; } }
            else if (!organizationName.equals(other.getOrganizationName())) { return false; }
            if (organizationUnit == null) { if (other.hasOrganizationUnit()) { return false; } }
            else if (!organizationUnit.equals(other.getOrganizationUnit())) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (organizationName == null ? 0 : organizationName.hashCode());
        result = 31 * result + (organizationUnit == null ? 0 : organizationUnit.hashCode());
        return result;
    }

    public Set<String> diff(PropertiesOrgLike other) {
            Set<String> s = new TreeSet<String>();
            if (organizationName == null) { if (other == null || other.hasOrganizationName()) { s.add("organizationName"); } }
            else if (!organizationName.equals(other.getOrganizationName())) { s.add("organizationName"); }
            if (organizationUnit == null) { if (other == null || other.hasOrganizationUnit()) { s.add("organizationUnit"); } }
            else if (!organizationUnit.equals(other.getOrganizationUnit())) { s.add("organizationUnit"); }
            return s;
    }

}
