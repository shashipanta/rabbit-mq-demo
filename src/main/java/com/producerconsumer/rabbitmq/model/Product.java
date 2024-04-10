package com.producerconsumer.rabbitmq.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/7/24 5:32 AM
 */

@Entity
@Getter
@Builder
@Setter
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(name = "uk_products_name", columnNames = "name")
})
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @SequenceGenerator(name = "products_seq_gen", sequenceName = "products_seq", allocationSize = 1)
    @GeneratedValue(generator = "products_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "name_np")
    private String nameNp;

    @Column(name = "manufacturer_name", length = 100, nullable = false)
    private String manufacturerName;

    @Column(name = "product_serial_no", length = 150)
    private String productSerialNo;

    @Column(name = "price", nullable = false)
    private Long price;

}
