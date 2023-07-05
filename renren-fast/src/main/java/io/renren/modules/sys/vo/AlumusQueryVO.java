package io.renren.modules.sys.vo;

public class AlumusQueryVO {
    /*
    * 依次定义以上变量，类型为string*/
    private String feignToken;

    public String getFeignToken() {
        return feignToken;
    }

    public void setFeignToken(String feignToken) {
        this.feignToken = feignToken;
    }

    private String query;

    private String  page;
    private String  limit;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    //    private String aluName;
//    private Integer aluId;
    private Integer gender;
    private String city;
    private Integer idCard;
    private String clazz;
    private String major;
    private String nativePlace;
    private Integer degreeStage;
    private String nationality;
//    private String workUnit;
//    private String jobTitle;
    private String enterpriseProperty;
    private String graduationTime;
    private String politicalStatus;

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getIdCard() {
        return idCard;
    }

    public void setIdCard(Integer idCard) {
        this.idCard = idCard;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Integer getDegreeStage() {
        return degreeStage;
    }

    public void setDegreeStage(Integer degreeStage) {
        this.degreeStage = degreeStage;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }


    public String getEnterpriseProperty() {
        return enterpriseProperty;
    }

    public void setEnterpriseProperty(String enterpriseProperty) {
        this.enterpriseProperty = enterpriseProperty;
    }

    public String getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(String graduationTime) {
        this.graduationTime = graduationTime;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }
}
