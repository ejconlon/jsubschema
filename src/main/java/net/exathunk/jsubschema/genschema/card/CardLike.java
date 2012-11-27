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
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = Card.class)
public interface CardLike {

    boolean hasAdditionalName();

    @JsonProperty("additionalName")
    List<String> getAdditionalName();

    @JsonProperty("additionalName")
    void setAdditionalName(List<String> additionalName);

    boolean hasAdr();

    @JsonProperty("adr")
    AddressLike getAdr();

    @JsonProperty("adr")
    @JsonDeserialize(as = Address.class)
    void setAdr(AddressLike adr);

    boolean hasBday();

    @JsonProperty("bday")
    String getBday();

    @JsonProperty("bday")
    void setBday(String bday);

    boolean hasEmail();

    @JsonProperty("email")
    PropertiesEmailLike getEmail();

    @JsonProperty("email")
    @JsonDeserialize(as = PropertiesEmail.class)
    void setEmail(PropertiesEmailLike email);

    boolean hasFamilyName();

    @JsonProperty("familyName")
    String getFamilyName();

    @JsonProperty("familyName")
    void setFamilyName(String familyName);

    boolean hasFn();

    @JsonProperty("fn")
    String getFn();

    @JsonProperty("fn")
    void setFn(String fn);

    boolean hasGeo();

    @JsonProperty("geo")
    GeoLike getGeo();

    @JsonProperty("geo")
    @JsonDeserialize(as = Geo.class)
    void setGeo(GeoLike geo);

    boolean hasGivenName();

    @JsonProperty("givenName")
    String getGivenName();

    @JsonProperty("givenName")
    void setGivenName(String givenName);

    boolean hasHonorificPrefix();

    @JsonProperty("honorificPrefix")
    List<String> getHonorificPrefix();

    @JsonProperty("honorificPrefix")
    void setHonorificPrefix(List<String> honorificPrefix);

    boolean hasHonorificSuffix();

    @JsonProperty("honorificSuffix")
    List<String> getHonorificSuffix();

    @JsonProperty("honorificSuffix")
    void setHonorificSuffix(List<String> honorificSuffix);

    boolean hasLogo();

    @JsonProperty("logo")
    String getLogo();

    @JsonProperty("logo")
    void setLogo(String logo);

    boolean hasNickname();

    @JsonProperty("nickname")
    String getNickname();

    @JsonProperty("nickname")
    void setNickname(String nickname);

    boolean hasOrg();

    @JsonProperty("org")
    PropertiesOrgLike getOrg();

    @JsonProperty("org")
    @JsonDeserialize(as = PropertiesOrg.class)
    void setOrg(PropertiesOrgLike org);

    boolean hasPhoto();

    @JsonProperty("photo")
    String getPhoto();

    @JsonProperty("photo")
    void setPhoto(String photo);

    boolean hasRole();

    @JsonProperty("role")
    String getRole();

    @JsonProperty("role")
    void setRole(String role);

    boolean hasSound();

    @JsonProperty("sound")
    String getSound();

    @JsonProperty("sound")
    void setSound(String sound);

    boolean hasTel();

    @JsonProperty("tel")
    PropertiesTelLike getTel();

    @JsonProperty("tel")
    @JsonDeserialize(as = PropertiesTel.class)
    void setTel(PropertiesTelLike tel);

    boolean hasTitle();

    @JsonProperty("title")
    String getTitle();

    @JsonProperty("title")
    void setTitle(String title);

    boolean hasTz();

    @JsonProperty("tz")
    String getTz();

    @JsonProperty("tz")
    void setTz(String tz);

    boolean hasUrl();

    @JsonProperty("url")
    String getUrl();

    @JsonProperty("url")
    void setUrl(String url);

}
