package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class ProductDateComparator implements Comparator<Product> {

	@Override
	public int compare(Product p1, Product p2) {
		// µπ≈≈–Ú
		return p2.getCreateDate().compareTo(p1.getCreateDate());
	}

}
