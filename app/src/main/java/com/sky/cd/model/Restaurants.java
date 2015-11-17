package com.sky.cd.model;

import java.util.List;

/**
 * Created by skytreasure on 15/11/15.
 */
public class Restaurants {
    public String SubFranchiseID;
    public String OutletID;
    public String OutletName;
    public String BrandID;
    public String Address;
    public String NeighbourhoodID;
    public String CityID;
    public String Email;
    public String Timings;
    public String CityRank;
    public String Latitude;
    public String Longitude;
    public String Pincode;
    public String Landmark;
    public String Streetname;
    public String BrandName;
    public String OutletURL;
    public Integer NumCoupons;
    public String NeighbourhoodName;
    public String PhoneNumber;
    public String CityName;
    public Double Distance;
    public List<Categories> Categories;
    public String LogoURL;
    public String CoverURL;

    public String getSubFranchiseID() {
        return SubFranchiseID;
    }

    public void setSubFranchiseID(String subFranchiseID) {
        SubFranchiseID = subFranchiseID;
    }

    public String getOutletID() {
        return OutletID;
    }

    public void setOutletID(String outletID) {
        OutletID = outletID;
    }

    public String getOutletName() {
        return OutletName;
    }

    public void setOutletName(String outletName) {
        OutletName = outletName;
    }

    public String getBrandID() {
        return BrandID;
    }

    public void setBrandID(String brandID) {
        BrandID = brandID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getNeighbourhoodID() {
        return NeighbourhoodID;
    }

    public void setNeighbourhoodID(String neighbourhoodID) {
        NeighbourhoodID = neighbourhoodID;
    }

    public String getCityID() {
        return CityID;
    }

    public void setCityID(String cityID) {
        CityID = cityID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTimings() {
        return Timings;
    }

    public void setTimings(String timings) {
        Timings = timings;
    }

    public String getCityRank() {
        return CityRank;
    }

    public void setCityRank(String cityRank) {
        CityRank = cityRank;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public String getStreetname() {
        return Streetname;
    }

    public void setStreetname(String streetname) {
        Streetname = streetname;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getOutletURL() {
        return OutletURL;
    }

    public void setOutletURL(String outletURL) {
        OutletURL = outletURL;
    }

    public Integer getNumCoupons() {
        return NumCoupons;
    }

    public void setNumCoupons(Integer numCoupons) {
        NumCoupons = numCoupons;
    }

    public String getNeighbourhoodName() {
        return NeighbourhoodName;
    }

    public void setNeighbourhoodName(String neighbourhoodName) {
        NeighbourhoodName = neighbourhoodName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public Double getDistance() {
        return Distance;
    }

    public void setDistance(Double distance) {
        Distance = distance;
    }

    public List<Restaurants.Categories> getCategories() {
        return Categories;
    }

    public void setCategories(List<Restaurants.Categories> categories) {
        Categories = categories;
    }

    public String getLogoURL() {
        return LogoURL;
    }

    public void setLogoURL(String logoURL) {
        LogoURL = logoURL;
    }

    public String getCoverURL() {
        return CoverURL;
    }

    public void setCoverURL(String coverURL) {
        CoverURL = coverURL;
    }

    public class Categories{
        public String OfflineCategoryID;
        public String Name;
        public String ParentCategoryID;
        public String CategoryType;

        public Categories(){}

        public String getOfflineCategoryID() {
            return OfflineCategoryID;
        }

        public void setOfflineCategoryID(String offlineCategoryID) {
            OfflineCategoryID = offlineCategoryID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getParentCategoryID() {
            return ParentCategoryID;
        }

        public void setParentCategoryID(String parentCategoryID) {
            ParentCategoryID = parentCategoryID;
        }

        public String getCategoryType() {
            return CategoryType;
        }

        public void setCategoryType(String categoryType) {
            CategoryType = categoryType;
        }
    }
}
