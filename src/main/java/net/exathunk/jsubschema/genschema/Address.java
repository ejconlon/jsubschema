package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;
import java.io.Serializable;

public class Address implements Cloneable, Serializable, AddressLike {

    private String post__dash__office__dash__box;

    private String extended__dash__address;

    private String street__dash__address;

    private String locality;

    private String region;

    private String postal__dash__code;

    private String country__dash__name;

    @Override
    public boolean hasPost__dash__office__dash__box() {
        return null != post__dash__office__dash__box;
    }

    @Override
    public String getPost__dash__office__dash__box() {
        return post__dash__office__dash__box;
    }

    @Override
    public void setPost__dash__office__dash__box(String post__dash__office__dash__box) {
        this.post__dash__office__dash__box = post__dash__office__dash__box;
    }

    @Override
    public boolean hasExtended__dash__address() {
        return null != extended__dash__address;
    }

    @Override
    public String getExtended__dash__address() {
        return extended__dash__address;
    }

    @Override
    public void setExtended__dash__address(String extended__dash__address) {
        this.extended__dash__address = extended__dash__address;
    }

    @Override
    public boolean hasStreet__dash__address() {
        return null != street__dash__address;
    }

    @Override
    public String getStreet__dash__address() {
        return street__dash__address;
    }

    @Override
    public void setStreet__dash__address(String street__dash__address) {
        this.street__dash__address = street__dash__address;
    }

    @Override
    public boolean hasLocality() {
        return null != locality;
    }

    @Override
    public String getLocality() {
        return locality;
    }

    @Override
    public void setLocality(String locality) {
        this.locality = locality;
    }

    @Override
    public boolean hasRegion() {
        return null != region;
    }

    @Override
    public String getRegion() {
        return region;
    }

    @Override
    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean hasPostal__dash__code() {
        return null != postal__dash__code;
    }

    @Override
    public String getPostal__dash__code() {
        return postal__dash__code;
    }

    @Override
    public void setPostal__dash__code(String postal__dash__code) {
        this.postal__dash__code = postal__dash__code;
    }

    @Override
    public boolean hasCountry__dash__name() {
        return null != country__dash__name;
    }

    @Override
    public String getCountry__dash__name() {
        return country__dash__name;
    }

    @Override
    public void setCountry__dash__name(String country__dash__name) {
        this.country__dash__name = country__dash__name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Address{ ");
        if (post__dash__office__dash__box != null) sb.append("post__dash__office__dash__box='").append(post__dash__office__dash__box).append("', ");
        if (extended__dash__address != null) sb.append("extended__dash__address='").append(extended__dash__address).append("', ");
        if (street__dash__address != null) sb.append("street__dash__address='").append(street__dash__address).append("', ");
        if (locality != null) sb.append("locality='").append(locality).append("', ");
        if (region != null) sb.append("region='").append(region).append("', ");
        if (postal__dash__code != null) sb.append("postal__dash__code='").append(postal__dash__code).append("', ");
        if (country__dash__name != null) sb.append("country__dash__name='").append(country__dash__name).append("', ");
        return sb.append("}").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof AddressLike) {
            AddressLike other = (AddressLike) o;
            if (post__dash__office__dash__box == null) { if (other.hasPost__dash__office__dash__box()) return false; }
            else if (!post__dash__office__dash__box.equals(other.getPost__dash__office__dash__box())) { return false; }
            if (extended__dash__address == null) { if (other.hasExtended__dash__address()) return false; }
            else if (!extended__dash__address.equals(other.getExtended__dash__address())) { return false; }
            if (street__dash__address == null) { if (other.hasStreet__dash__address()) return false; }
            else if (!street__dash__address.equals(other.getStreet__dash__address())) { return false; }
            if (locality == null) { if (other.hasLocality()) return false; }
            else if (!locality.equals(other.getLocality())) { return false; }
            if (region == null) { if (other.hasRegion()) return false; }
            else if (!region.equals(other.getRegion())) { return false; }
            if (postal__dash__code == null) { if (other.hasPostal__dash__code()) return false; }
            else if (!postal__dash__code.equals(other.getPostal__dash__code())) { return false; }
            if (country__dash__name == null) { if (other.hasCountry__dash__name()) return false; }
            else if (!country__dash__name.equals(other.getCountry__dash__name())) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (post__dash__office__dash__box == null ? 0 : post__dash__office__dash__box.hashCode());
        result = 31 * result + (extended__dash__address == null ? 0 : extended__dash__address.hashCode());
        result = 31 * result + (street__dash__address == null ? 0 : street__dash__address.hashCode());
        result = 31 * result + (locality == null ? 0 : locality.hashCode());
        result = 31 * result + (region == null ? 0 : region.hashCode());
        result = 31 * result + (postal__dash__code == null ? 0 : postal__dash__code.hashCode());
        result = 31 * result + (country__dash__name == null ? 0 : country__dash__name.hashCode());
        return result;
    }

}
