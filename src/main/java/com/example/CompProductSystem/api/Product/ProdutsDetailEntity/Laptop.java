package com.example.CompProductSystem.api.Product.ProdutsDetailEntity;

import com.example.CompProductSystem.api.Product.Product;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@DiscriminatorValue("Laptop")
public class Laptop extends Product {
    private double monitorSize;
//    private String manufacture; todo embedded를 통한 제조사 구별을 해야할지, 데이터 설계에 대한 고민이 필요하다.

    public void setMonitorSize(double monitorSize){
        this.monitorSize = monitorSize;
    }


}
