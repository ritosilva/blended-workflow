package org.blended.common.repository.resttemplate.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecVO {
    private String externalId;
    private String specId;
    private String name;
    private String dataModelExtId;
    private String conditionModelExtId;
    private String goalModelExtId;

    public SpecVO() {
    }

    public SpecVO(String specId, String name) {
        this.specId = specId;
        this.name = name;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataModelExtId() {
        return dataModelExtId;
    }

    public void setDataModelExtId(String dataModelExtId) {
        this.dataModelExtId = dataModelExtId;
    }

    public String getConditionModelExtId() {
        return conditionModelExtId;
    }

    public void setConditionModelExtId(String conditionModelExtId) {
        this.conditionModelExtId = conditionModelExtId;
    }

    public String getGoalModelExtId() {
        return goalModelExtId;
    }

    public void setGoalModelExtId(String goalModelExtId) {
        this.goalModelExtId = goalModelExtId;
    }

}
