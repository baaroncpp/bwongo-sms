package com.bwongo.core.base.model.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_address", schema = "core")
public class TAddress extends AuditEntity{
    private String street;
    private String addressDescription;
    private String townOrVillage;
    private TCountry country;
    private Double latitudeCoordinate;
    private Double longitudeCoordinate;

    @Column(name = "street")
    public String getStreet() {
        return street;
    }

    @Column(name = "address_description")
    public String getAddressDescription() {
        return addressDescription;
    }

    @Column(name = "town_or_village")
    public String getTownOrVillage() {
        return townOrVillage;
    }

    @JoinColumn(name = "country_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TCountry getCountry() {
        return country;
    }

    @Column(name = "latitude_coordinate")
    public Double getLatitudeCoordinate() {
        return latitudeCoordinate;
    }

    @Column(name = "longitude_coordinate")
    public Double getLongitudeCoordinate() {
        return longitudeCoordinate;
    }
}
