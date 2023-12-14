package WebProject.WebProject.entity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // lombok giúp generate các hàm constructor, get, set v.v.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
	@Id()
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(name = "login_Type", columnDefinition = "nvarchar(1111)")
	private String login_Type;

	@Column(name = "role", columnDefinition = "nvarchar(1111)")
	private String role;

	@Column(name = "password",columnDefinition = "nvarchar(1111)")
	private String password;

	@Column(name = "user_Name", columnDefinition = "nvarchar(1111)")
	private String user_Name;

	@Column(name = "avatar", columnDefinition = "nvarchar(1111)")
	private String avatar;

	@Column(name = "email", columnDefinition = "nvarchar(1111)")
	private String email;

	@Column(name = "phone_Number", columnDefinition = "nvarchar(1111)")
	private String phone_Number;

	@Column(name = "province")
	private String province;

	@Column(name = "districts")
	private String districts;

	@Column(name = "wards")
	private String wards;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Order> order;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Cart> cart;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Statistic> statistic;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Wallet> wallets;
}
