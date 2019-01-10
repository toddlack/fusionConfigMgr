package com.sas.itq.search.configManager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.file.Paths;
import java.util.Map;

/**
 * Super class for index and query profiles. These are
 * simple name-value json pairs. The format is:
 * <pre>profile-name : pipeline-id</pre>
 *
 * Profiles belong to a collection. The actual json looks like:
 * <pre>
 *    "queryProfiles" : {
 *       "intranet" : {
 *         "default" : "intranet",
 *         "qp2" : "intra_date_boost4"
 *       }
 *     }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class FusionProfile extends JsonBased implements IdentifiableString{

    protected static final String COLLECTION = "collection";
    protected static final String ID = "id";
    protected static final String QUERY_PROFILE = "queryProfile";
    protected static final String INDEX_PROFILE = "indexProfile";
    protected static final String SEARCH_HANDLER = "searchHandler";
    public String typeName;

    public FusionProfile() {
        super();
    }

    public FusionProfile(String typeVal) {
        this();
        typeName=typeVal;
    }
    public FusionProfile(Map<String,String> initVals,String typeVal) {
        this();
        typeName=typeVal;
        this.setCollection(initVals.get(COLLECTION));
        this.setId(initVals.get(ID));
        this.setSearchHandler(initVals.get(SEARCH_HANDLER));
        this.setPipeline(initVals.get(typeName));
    }

    @JsonProperty
    public String getCollection() {
        return String.valueOf(properties.get(COLLECTION));
    }

    public void setCollection(String val) {
        set(COLLECTION,val);
    }

    @JsonProperty
    public String getId() {
        return String.valueOf(properties.get(ID));
    }

    public void setId(String id) {
        set(ID,id);
    }

    @JsonProperty
    public String getPipeline() {
        return String.valueOf(properties.get(typeName));
    }
    public void setPipeline(String val) {
        set(typeName,val);
    }
    @JsonProperty
    public String getSearchHandler() {
        return String.valueOf(properties.get(SEARCH_HANDLER));
    }
    public void setSearchHandler(String val) {
        set(SEARCH_HANDLER,val);
    }
    @Override
    public String generateFileName() {

        String pathname = Paths.get(getPathSegmentName()+JSON).toString();
        return pathname;
    }
    @Override
    public String generateFileName(String parentDir) {
        String fname = Paths.get(parentDir,generateFileName()).toString();
        return fname;
    }

    /** Constant value that matches the string key when retrieving profiles from
     * an Objects API call.
     * @return
     */
    public  abstract String getProfileType();

    /**
     * Return a string that is the PATH segment for a get or PUT. Sometimes it is ID , sometimes name
     */
    @Override
    public String getPathSegmentName() {
        return getId();
    }
}
