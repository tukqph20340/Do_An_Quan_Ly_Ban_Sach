package WebProject.WebProject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter// lombok giúp generate các hàm constructor, get, set v.v.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_item")
public class Order_Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "count")
	private int count;

	@ManyToOne
	@JoinColumn(name = "product_id")
//	@EqualsAndHashCode.Exclude
//	@ToString.Exclude
	private Product product;

	@ManyToOne
	@JoinColumn(name = "order_id")
//	@EqualsAndHashCode.Exclude
//	@ToString.Exclude
	private Order order;
}
