package com.saraob.car;

import java.math.BigDecimal;
import java.util.UUID;

public class CarDao {

    private static final Car cars[];

    static {
        cars = new Car[]{
                new Car(UUID.fromString("a7d91f3c-2b84-4e65-9a12-cf8301d6b742"), "TES-001", new BigDecimal("95.00"), Brand.TESLA, true),
                new Car(UUID.fromString("3c5e8a91-f240-47bd-b6e9-15a72c804df3"), "AUD-002", new BigDecimal("75.00"), Brand.AUDI, false),
                new Car(UUID.fromString("e18b42d7-6c95-4a31-8f27-b903de51a684"), "MER-003", new BigDecimal("85.00"), Brand.MERCEDES, false),
                new Car(UUID.fromString("5f2a9c68-d173-4eb0-a451-7d86c329f10e"), "TOY-004", new BigDecimal("55.00"), Brand.TOYOTA, false),
                new Car(UUID.fromString("b64e71a2-8d39-46fc-93b5-e20a7c18f564"), "TES-005", new BigDecimal("110.00"), Brand.TESLA, true)
        };
    }

    public Car getCarById(UUID id) {
        if (!id.equals(null)) {
            for (int i=0 ; i< cars.length ; i++) {
                if (cars[i].getId().equals(id)){
                    return cars[i];
                }
            }
        }
        return null;
    }

}
