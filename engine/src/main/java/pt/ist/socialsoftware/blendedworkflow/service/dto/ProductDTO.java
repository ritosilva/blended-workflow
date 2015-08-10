package pt.ist.socialsoftware.blendedworkflow.service.dto;

public class ProductDTO {
    private String productExtId;
    private String type;

    public ProductDTO(String productExtId, String type) {
        this.productExtId = productExtId;
        this.type = type;
    }

    public ProductDTO() {
    }

    public String getProductExtId() {
        return productExtId;
    }

    public void setProductExtId(String productExtId) {
        this.productExtId = productExtId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
