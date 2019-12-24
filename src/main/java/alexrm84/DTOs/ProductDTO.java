package alexrm84.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 126693183965541496L;
    private Long id;
    private String title;
    private BigDecimal price;
    private String imagePath;
    private String category;

}
