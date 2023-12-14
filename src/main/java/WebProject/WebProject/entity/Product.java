package WebProject.WebProject.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter// lombok giúp generate các hàm constructor, get, set v.v.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
@Builder
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "product_Name", columnDefinition = "nvarchar(1111)")
	private String product_Name;

	@Column(name = "description", columnDefinition = "nvarchar(11111)")
	private String description;

	@Column(name = "sold")
	private int sold;

	@Column(name = "is_Active")
	private int is_Active;

	@Column(name = "is_Selling")
	private int is_Selling;

	@Column(name = "created_At")
	private Date created_At;

	@Column(name = "price")
	private Long price;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "book_size")
	private String bookSize;

	@Column(name = "page_number")
	private String pageNumber;

	@Column(name = "year_publication")
	private int yearPublication;

	@Column(name = "language")
	private String language;

	@ManyToOne
	@JoinColumn(name = "category_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Category category;

	@ManyToOne
	@JoinColumn(name = "author_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Author author;

	@ManyToOne
	@JoinColumn(name = "producer_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Producer producer;

	@ManyToOne
	@JoinColumn(name = "bookcover_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private BookCover bookCover;

	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductImage> productImage;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Order_Item> order_Item;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Cart> cart;


}
