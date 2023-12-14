package WebProject.WebProject.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`order`")
@Builder
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "total")
	private long total;

	@Column(name = "booking_Date")
	private String booking_Date;

	@Column(name = "payment_Method", columnDefinition = "nvarchar(1111)")
	private String payment_Method;

	@Column(name = "status", columnDefinition = "nvarchar(1111)")
	private String status;

	@Column(name = "fullname", columnDefinition = "nvarchar(1111)")
	private String fullname;

	@Column(name = "country", columnDefinition = "nvarchar(1111)")
	private String country;

	@Column(name = "address", columnDefinition = "nvarchar(1111)")
	private String address;

	@Column(name = "wards")
	private String wards;

	@Column(name = "total_ship")
	private int totalShip;

	@Column(name = "phone", columnDefinition = "nvarchar(1111)")
	private String phone;

	@Column(name = "email", columnDefinition = "nvarchar(1111)")
	private String email;

	@Column(name = "note", columnDefinition = "nvarchar(1111)")
	private String note;

	@Column(name = "confirm_date")
	private Date confirmDate;

	@Column(name = "pickup_date")
	private Date pickupDate;

	@Column(name = "delivery_date")
	private Date deliveryDate;

	@Column(name = "delivery_succesful_date")
	private Date deliverySuccesfulDate;

	@Column(name = "delivery_failed_date")
	private String deliveryFailedDate;

	@OneToMany(mappedBy = "order")
	private List<Order_Item> order_Item;

	@OneToMany(mappedBy = "order")
	private List<Statistic> statistic;

	@OneToMany(mappedBy = "order")
	private List<Order> order ;

	@Column(name = "total_discount")
	private Float totalDiscount;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private User user;

	@ManyToOne
	@JoinColumn(name = "promotion_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Promotion promotion;

	@ManyToOne
	@JoinColumn(name = "active_order_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private ActiveOrder activeOrder;


	@OneToMany(mappedBy = "order")
	private List<Returns> returns ;
}
