package alexrm84.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products_images")
@Data
@NoArgsConstructor
public class ProductImage implements Serializable {
    private static final long serialVersionUID = -7838989013257763044L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    @JsonIgnore
//    private Product product;

    @Column(name = "path")
    private String path;
}
