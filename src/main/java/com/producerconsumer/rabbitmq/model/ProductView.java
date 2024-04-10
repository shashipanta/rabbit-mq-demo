package com.producerconsumer.rabbitmq.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/7/24 6:09 AM
 */
@Entity
@Getter
@Builder
@Setter
@Table(name = "product_views", uniqueConstraints = {
        @UniqueConstraint(name = "pk_product_views_name", columnNames = "name")
})
@NoArgsConstructor
@AllArgsConstructor
public class ProductView {

    @Id
    @SequenceGenerator(name = "product_views_seq_gen", sequenceName = "product_views_seq", allocationSize = 1)
    @GeneratedValue(generator = "product_views_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "manufacturer_name")
    private String manufacturerName;

    @Column(name = "price")
    private Long price;
}
