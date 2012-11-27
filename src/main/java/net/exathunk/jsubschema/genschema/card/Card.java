package net.exathunk.jsubschema.genschema.card;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import net.exathunk.jsubschema.genschema.address.Address;
import net.exathunk.jsubschema.genschema.address.AddressLike;
import net.exathunk.jsubschema.genschema.card.declarations.propertiesemail.PropertiesEmail;
import net.exathunk.jsubschema.genschema.card.declarations.propertiesemail.PropertiesEmailLike;
import net.exathunk.jsubschema.genschema.card.declarations.propertiesorg.PropertiesOrg;
import net.exathunk.jsubschema.genschema.card.declarations.propertiesorg.PropertiesOrgLike;
import net.exathunk.jsubschema.genschema.card.declarations.propertiestel.PropertiesTel;
import net.exathunk.jsubschema.genschema.card.declarations.propertiestel.PropertiesTelLike;
import net.exathunk.jsubschema.genschema.geo.Geo;
import net.exathunk.jsubschema.genschema.geo.GeoLike;

public class Card implements Cloneable, Serializable, CardLike {

    private List<String> additionalName;

    private AddressLike adr;

    private String bday;

    private PropertiesEmailLike email;

    private String familyName;

    private String fn;

    private GeoLike geo;

    private String givenName;

    private List<String> honorificPrefix;

    private List<String> honorificSuffix;

    private String logo;

    private String nickname;

    private PropertiesOrgLike org;

    private String photo;

    private String role;

    private String sound;

    private PropertiesTelLike tel;

    private String title;

    private String tz;

    private String url;

    @Override
    public boolean hasAdditionalName() {
        return null != additionalName;
    }

    @Override
    public List<String> getAdditionalName() {
        return additionalName;
    }

    @Override
    public void setAdditionalName(List<String> additionalName) {
        this.additionalName = additionalName;
    }

    @Override
    public boolean hasAdr() {
        return null != adr;
    }

    @Override
    public AddressLike getAdr() {
        return adr;
    }

    @Override
    public void setAdr(AddressLike adr) {
        this.adr = adr;
    }

    @Override
    public boolean hasBday() {
        return null != bday;
    }

    @Override
    public String getBday() {
        return bday;
    }

    @Override
    public void setBday(String bday) {
        this.bday = bday;
    }

    @Override
    public boolean hasEmail() {
        return null != email;
    }

    @Override
    public PropertiesEmailLike getEmail() {
        return email;
    }

    @Override
    public void setEmail(PropertiesEmailLike email) {
        this.email = email;
    }

    @Override
    public boolean hasFamilyName() {
        return null != familyName;
    }

    @Override
    public String getFamilyName() {
        return familyName;
    }

    @Override
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Override
    public boolean hasFn() {
        return null != fn;
    }

    @Override
    public String getFn() {
        return fn;
    }

    @Override
    public void setFn(String fn) {
        this.fn = fn;
    }

    @Override
    public boolean hasGeo() {
        return null != geo;
    }

    @Override
    public GeoLike getGeo() {
        return geo;
    }

    @Override
    public void setGeo(GeoLike geo) {
        this.geo = geo;
    }

    @Override
    public boolean hasGivenName() {
        return null != givenName;
    }

    @Override
    public String getGivenName() {
        return givenName;
    }

    @Override
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    @Override
    public boolean hasHonorificPrefix() {
        return null != honorificPrefix;
    }

    @Override
    public List<String> getHonorificPrefix() {
        return honorificPrefix;
    }

    @Override
    public void setHonorificPrefix(List<String> honorificPrefix) {
        this.honorificPrefix = honorificPrefix;
    }

    @Override
    public boolean hasHonorificSuffix() {
        return null != honorificSuffix;
    }

    @Override
    public List<String> getHonorificSuffix() {
        return honorificSuffix;
    }

    @Override
    public void setHonorificSuffix(List<String> honorificSuffix) {
        this.honorificSuffix = honorificSuffix;
    }

    @Override
    public boolean hasLogo() {
        return null != logo;
    }

    @Override
    public String getLogo() {
        return logo;
    }

    @Override
    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public boolean hasNickname() {
        return null != nickname;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean hasOrg() {
        return null != org;
    }

    @Override
    public PropertiesOrgLike getOrg() {
        return org;
    }

    @Override
    public void setOrg(PropertiesOrgLike org) {
        this.org = org;
    }

    @Override
    public boolean hasPhoto() {
        return null != photo;
    }

    @Override
    public String getPhoto() {
        return photo;
    }

    @Override
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean hasRole() {
        return null != role;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean hasSound() {
        return null != sound;
    }

    @Override
    public String getSound() {
        return sound;
    }

    @Override
    public void setSound(String sound) {
        this.sound = sound;
    }

    @Override
    public boolean hasTel() {
        return null != tel;
    }

    @Override
    public PropertiesTelLike getTel() {
        return tel;
    }

    @Override
    public void setTel(PropertiesTelLike tel) {
        this.tel = tel;
    }

    @Override
    public boolean hasTitle() {
        return null != title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean hasTz() {
        return null != tz;
    }

    @Override
    public String getTz() {
        return tz;
    }

    @Override
    public void setTz(String tz) {
        this.tz = tz;
    }

    @Override
    public boolean hasUrl() {
        return null != url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Card{ ");
        if (additionalName != null) sb.append("additionalName='").append(additionalName).append("', ");
        if (adr != null) sb.append("adr='").append(adr).append("', ");
        if (bday != null) sb.append("bday='").append(bday).append("', ");
        if (email != null) sb.append("email='").append(email).append("', ");
        if (familyName != null) sb.append("familyName='").append(familyName).append("', ");
        if (fn != null) sb.append("fn='").append(fn).append("', ");
        if (geo != null) sb.append("geo='").append(geo).append("', ");
        if (givenName != null) sb.append("givenName='").append(givenName).append("', ");
        if (honorificPrefix != null) sb.append("honorificPrefix='").append(honorificPrefix).append("', ");
        if (honorificSuffix != null) sb.append("honorificSuffix='").append(honorificSuffix).append("', ");
        if (logo != null) sb.append("logo='").append(logo).append("', ");
        if (nickname != null) sb.append("nickname='").append(nickname).append("', ");
        if (org != null) sb.append("org='").append(org).append("', ");
        if (photo != null) sb.append("photo='").append(photo).append("', ");
        if (role != null) sb.append("role='").append(role).append("', ");
        if (sound != null) sb.append("sound='").append(sound).append("', ");
        if (tel != null) sb.append("tel='").append(tel).append("', ");
        if (title != null) sb.append("title='").append(title).append("', ");
        if (tz != null) sb.append("tz='").append(tz).append("', ");
        if (url != null) sb.append("url='").append(url).append("', ");
        return sb.append("}").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof CardLike) {
            CardLike other = (CardLike) o;
            if (additionalName == null) { if (other.hasAdditionalName()) { return false; } }
            else if (!additionalName.equals(other.getAdditionalName())) { return false; }
            if (adr == null) { if (other.hasAdr()) { return false; } }
            else if (!adr.equals(other.getAdr())) { return false; }
            if (bday == null) { if (other.hasBday()) { return false; } }
            else if (!bday.equals(other.getBday())) { return false; }
            if (email == null) { if (other.hasEmail()) { return false; } }
            else if (!email.equals(other.getEmail())) { return false; }
            if (familyName == null) { if (other.hasFamilyName()) { return false; } }
            else if (!familyName.equals(other.getFamilyName())) { return false; }
            if (fn == null) { if (other.hasFn()) { return false; } }
            else if (!fn.equals(other.getFn())) { return false; }
            if (geo == null) { if (other.hasGeo()) { return false; } }
            else if (!geo.equals(other.getGeo())) { return false; }
            if (givenName == null) { if (other.hasGivenName()) { return false; } }
            else if (!givenName.equals(other.getGivenName())) { return false; }
            if (honorificPrefix == null) { if (other.hasHonorificPrefix()) { return false; } }
            else if (!honorificPrefix.equals(other.getHonorificPrefix())) { return false; }
            if (honorificSuffix == null) { if (other.hasHonorificSuffix()) { return false; } }
            else if (!honorificSuffix.equals(other.getHonorificSuffix())) { return false; }
            if (logo == null) { if (other.hasLogo()) { return false; } }
            else if (!logo.equals(other.getLogo())) { return false; }
            if (nickname == null) { if (other.hasNickname()) { return false; } }
            else if (!nickname.equals(other.getNickname())) { return false; }
            if (org == null) { if (other.hasOrg()) { return false; } }
            else if (!org.equals(other.getOrg())) { return false; }
            if (photo == null) { if (other.hasPhoto()) { return false; } }
            else if (!photo.equals(other.getPhoto())) { return false; }
            if (role == null) { if (other.hasRole()) { return false; } }
            else if (!role.equals(other.getRole())) { return false; }
            if (sound == null) { if (other.hasSound()) { return false; } }
            else if (!sound.equals(other.getSound())) { return false; }
            if (tel == null) { if (other.hasTel()) { return false; } }
            else if (!tel.equals(other.getTel())) { return false; }
            if (title == null) { if (other.hasTitle()) { return false; } }
            else if (!title.equals(other.getTitle())) { return false; }
            if (tz == null) { if (other.hasTz()) { return false; } }
            else if (!tz.equals(other.getTz())) { return false; }
            if (url == null) { if (other.hasUrl()) { return false; } }
            else if (!url.equals(other.getUrl())) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (additionalName == null ? 0 : additionalName.hashCode());
        result = 31 * result + (adr == null ? 0 : adr.hashCode());
        result = 31 * result + (bday == null ? 0 : bday.hashCode());
        result = 31 * result + (email == null ? 0 : email.hashCode());
        result = 31 * result + (familyName == null ? 0 : familyName.hashCode());
        result = 31 * result + (fn == null ? 0 : fn.hashCode());
        result = 31 * result + (geo == null ? 0 : geo.hashCode());
        result = 31 * result + (givenName == null ? 0 : givenName.hashCode());
        result = 31 * result + (honorificPrefix == null ? 0 : honorificPrefix.hashCode());
        result = 31 * result + (honorificSuffix == null ? 0 : honorificSuffix.hashCode());
        result = 31 * result + (logo == null ? 0 : logo.hashCode());
        result = 31 * result + (nickname == null ? 0 : nickname.hashCode());
        result = 31 * result + (org == null ? 0 : org.hashCode());
        result = 31 * result + (photo == null ? 0 : photo.hashCode());
        result = 31 * result + (role == null ? 0 : role.hashCode());
        result = 31 * result + (sound == null ? 0 : sound.hashCode());
        result = 31 * result + (tel == null ? 0 : tel.hashCode());
        result = 31 * result + (title == null ? 0 : title.hashCode());
        result = 31 * result + (tz == null ? 0 : tz.hashCode());
        result = 31 * result + (url == null ? 0 : url.hashCode());
        return result;
    }

    public Set<String> diff(CardLike other) {
            Set<String> s = new TreeSet<String>();
            if (additionalName == null) { if (other == null || other.hasAdditionalName()) { s.add("additionalName"); } }
            else if (!additionalName.equals(other.getAdditionalName())) { s.add("additionalName"); }
            if (adr == null) { if (other == null || other.hasAdr()) { s.add("adr"); } }
            else if (!adr.equals(other.getAdr())) { s.add("adr"); }
            if (bday == null) { if (other == null || other.hasBday()) { s.add("bday"); } }
            else if (!bday.equals(other.getBday())) { s.add("bday"); }
            if (email == null) { if (other == null || other.hasEmail()) { s.add("email"); } }
            else if (!email.equals(other.getEmail())) { s.add("email"); }
            if (familyName == null) { if (other == null || other.hasFamilyName()) { s.add("familyName"); } }
            else if (!familyName.equals(other.getFamilyName())) { s.add("familyName"); }
            if (fn == null) { if (other == null || other.hasFn()) { s.add("fn"); } }
            else if (!fn.equals(other.getFn())) { s.add("fn"); }
            if (geo == null) { if (other == null || other.hasGeo()) { s.add("geo"); } }
            else if (!geo.equals(other.getGeo())) { s.add("geo"); }
            if (givenName == null) { if (other == null || other.hasGivenName()) { s.add("givenName"); } }
            else if (!givenName.equals(other.getGivenName())) { s.add("givenName"); }
            if (honorificPrefix == null) { if (other == null || other.hasHonorificPrefix()) { s.add("honorificPrefix"); } }
            else if (!honorificPrefix.equals(other.getHonorificPrefix())) { s.add("honorificPrefix"); }
            if (honorificSuffix == null) { if (other == null || other.hasHonorificSuffix()) { s.add("honorificSuffix"); } }
            else if (!honorificSuffix.equals(other.getHonorificSuffix())) { s.add("honorificSuffix"); }
            if (logo == null) { if (other == null || other.hasLogo()) { s.add("logo"); } }
            else if (!logo.equals(other.getLogo())) { s.add("logo"); }
            if (nickname == null) { if (other == null || other.hasNickname()) { s.add("nickname"); } }
            else if (!nickname.equals(other.getNickname())) { s.add("nickname"); }
            if (org == null) { if (other == null || other.hasOrg()) { s.add("org"); } }
            else if (!org.equals(other.getOrg())) { s.add("org"); }
            if (photo == null) { if (other == null || other.hasPhoto()) { s.add("photo"); } }
            else if (!photo.equals(other.getPhoto())) { s.add("photo"); }
            if (role == null) { if (other == null || other.hasRole()) { s.add("role"); } }
            else if (!role.equals(other.getRole())) { s.add("role"); }
            if (sound == null) { if (other == null || other.hasSound()) { s.add("sound"); } }
            else if (!sound.equals(other.getSound())) { s.add("sound"); }
            if (tel == null) { if (other == null || other.hasTel()) { s.add("tel"); } }
            else if (!tel.equals(other.getTel())) { s.add("tel"); }
            if (title == null) { if (other == null || other.hasTitle()) { s.add("title"); } }
            else if (!title.equals(other.getTitle())) { s.add("title"); }
            if (tz == null) { if (other == null || other.hasTz()) { s.add("tz"); } }
            else if (!tz.equals(other.getTz())) { s.add("tz"); }
            if (url == null) { if (other == null || other.hasUrl()) { s.add("url"); } }
            else if (!url.equals(other.getUrl())) { s.add("url"); }
            return s;
    }

}
